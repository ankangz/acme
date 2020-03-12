package com.ankang.server.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }


    public void register(String serviceName, String serviceAddress) {
        //这侧相应的服务
        String servicePath = ZkConfig.ZK_REGUSTER_PATH+"/"+serviceName;

        try {
            //判断registry 下的服务 节点是否存在 不存在则创建
            if(curatorFramework.checkExists().forPath(servicePath) == null){
                curatorFramework.create().creatingParentsIfNeeded().
                        withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());
            }
            //创建服务下的地址 节点
            String addressPath = servicePath + "/" + serviceAddress;
            String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
            System.out.println("服务创建成功" + rsNode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
