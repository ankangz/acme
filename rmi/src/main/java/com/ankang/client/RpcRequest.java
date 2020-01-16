package com.ankang.client;

import java.io.Serializable;

public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -8686681203405523761L;

    private String className;
    private String MethodName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
