package com.xin.cache.support.proxy.cglib;

import com.xin.cache.api.ISivanCache;
import com.xin.cache.support.proxy.ISivanCacheProxy;
import com.xin.cache.support.proxy.bs.ISivanCacheProxyBsContext;
import com.xin.cache.support.proxy.bs.SivanCacheProxyBs;
import com.xin.cache.support.proxy.bs.SivanCacheProxyBsContext;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 代理类
 * @author sivan
 * date 2019/3/7
 *    
 */
public class CglibProxy implements MethodInterceptor, ISivanCacheProxy {

    /**
     * 被代理的对象
     */
    private final ISivanCache target;

    public CglibProxy(ISivanCache target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        ISivanCacheProxyBsContext context = SivanCacheProxyBsContext.newInstance()
                .method(method).params(params).target(target);

        return SivanCacheProxyBs.newInstance().context(context).execute();
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

}
