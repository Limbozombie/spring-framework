package Chapter03.proxy.cglib;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gulh
 * @since 2020/4/23 16:36
 */
public class ActionCallBack implements MethodInterceptor {

	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
			throws Throwable {
		System.out.println("cglib Proxy start");
		methodProxy.invokeSuper(proxy, args);
		System.out.println("cglib Proxy over");
		return null;
	}
}
