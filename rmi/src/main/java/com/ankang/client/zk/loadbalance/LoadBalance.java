package com.ankang.client.zk.loadbalance;

import java.util.List;

public interface LoadBalance {
    public String selectHost(List<String> repos);
}
