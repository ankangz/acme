package com.ankang.client.zk;

public interface IServiceDiscover {
    /**
     * 根据请求的服务地址，获得相应的调用地址
     * @param serviceName
     */
    public String discover(String serviceName);
}
