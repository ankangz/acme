package com.ankang.client.zk;

import com.ankang.client.zk.loadbalance.LoadBalance;
import com.ankang.client.zk.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscoverImpl implements IServiceDiscover {

    private String address;
    private CuratorFramework curatorFramework;

    private List<String> repos = new ArrayList<String>();

    public ServiceDiscoverImpl(String address) {
        this.address = address;
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(address).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }

    public String discover(String serviceName) {
        String path = ZkConfig.ZK_REGUSTER_PATH+"/"+serviceName;

        try {
            repos = curatorFramework.getChildren().forPath(path);

        } catch (Exception e) {
            throw new RuntimeException("获取子节点异常");
        }

        registerWatcher(path);

        //负载均衡
        LoadBalance loadBalance = new RandomLoadBalance();
        String sp = loadBalance.selectHost(repos);
        return sp;
    }
    private void registerWatcher(final String path){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,path,true);

        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(listener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            throw new RuntimeException("注册lister异常",e);
        }
    }
}
