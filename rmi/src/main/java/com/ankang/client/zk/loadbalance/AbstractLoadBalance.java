package com.ankang.client.zk.loadbalance;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    public String selectHost(List<String> repos) {
        if(null == repos || repos.size() == 0){
            return null;
        }
        if(repos.size() == 1){
            return repos.get(0);
        }
        return doSelect(repos);
    }

    public abstract String doSelect(List<String> repos);
}
