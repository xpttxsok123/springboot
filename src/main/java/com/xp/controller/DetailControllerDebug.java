package com.xp.controller;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;


//@BTrace注解中要加上unsafe=true，否则运行btrace脚本时会因为安全机制导致报错而无法执行脚本；
@BTrace(unsafe = true)
public class DetailControllerDebug {
    @OnMethod(
            clazz = "com.xp.controller.DetailController",//需要拦截的类
            method = "detail",//需要拦截的方法名
            location = @Location(Kind.RETURN)//函数返回的时候执行，如果不填，则在函数开始的时候执行
    )




    /**
     * @ProbeClassName 要拦截方法类名
     * @ProbeMethodName 要拦截方法名
     * AnyType[] 方法参数
     */
    public void anyRead(@ProbeClassName String pcm, @ProbeMethodName String pmn, AnyType[] args) {
        BTraceUtils.printArray(args);
        BTraceUtils.println(pcm + "," + pmn);
        BTraceUtils.println();
    }
}
