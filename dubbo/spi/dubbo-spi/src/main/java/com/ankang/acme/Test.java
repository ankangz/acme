package com.ankang.acme;

import com.alibaba.dubbo.common.Extension;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

public class Test {
    public static void main(String[] args) {
        //Protocol myProtocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("ak");
        /*ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol Protocol = extensionLoader.getAdaptiveExtension();*/
        //System.out.println(myProtocol.getDefaultPort());
        //System.out.println(Protocol.getDefaultPort());

        ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("dubbo");
    }
}
