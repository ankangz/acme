package com.ankang.acme;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DistributedLock implements Lock, Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/locks";//定义根节点
    private String WAIT_LOCK ;//等待前一个锁
    private String CURRENT_LOCK ;//表示当前锁

    private CountDownLatch countDownLatch;

    public DistributedLock() {

        try {
            zk = new ZooKeeper("127.0.0.1:2181",1000,this);
            //判断根节点是否存在
            Stat stat = zk.exists(ROOT_LOCK,this);
            if(stat == null){
                zk.create(ROOT_LOCK,"0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public void lock() {

        //获得锁成功
        if(this.tryLock()){
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+",获得锁成功!");
            return;
        }
        try {
            //没有获得锁继续等待获得锁
            waitForLock(WAIT_LOCK);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private boolean waitForLock(String waitLock) throws KeeperException, InterruptedException {
        //监听当前节点的上一个节点
        Stat stat = zk.exists(waitLock,true);
        if(stat != null){
            System.out.println(Thread.currentThread().getName()+"->等待锁"+ROOT_LOCK+"/"+waitLock+"释放!");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"->等待锁成功!");
        }

        return true;
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {

        try {
            //创建临时有序节点
            CURRENT_LOCK = zk.create(ROOT_LOCK+"/","0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+",尝试竞争锁!");
            //获取根节点的所有子节点
            List<String> childrens = zk.getChildren(ROOT_LOCK, false);
            //排序
            SortedSet<String> sortedSet = new TreeSet<String>();
            for (String child: childrens
                 ) {
                sortedSet.add(ROOT_LOCK+"/"+child);

            }
            String minLock = sortedSet.first();
            //比当前节点小的所有节点    headSet(ele):返回比ele小的全部元素
            SortedSet<String> lessThenMe = sortedSet.headSet(CURRENT_LOCK);
            if(CURRENT_LOCK.equals(minLock)){
                return true;
            }
            //非空
            if(!lessThenMe.isEmpty()){
                WAIT_LOCK = lessThenMe.last();//获得比当前节点更小的最后一个节点，设置为WAIT_LOCK
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+",释放锁!");

        try {
            zk.delete(CURRENT_LOCK,-1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    public Condition newCondition() {
        return null;
    }

    public void process(WatchedEvent watchedEvent) {

        if(this.countDownLatch != null) {
            this.countDownLatch.countDown();
        }
    }
}
