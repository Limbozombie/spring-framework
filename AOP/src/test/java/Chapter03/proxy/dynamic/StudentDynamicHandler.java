package Chapter03.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gulh
 * @since 2020/4/23 16:11
 */
public class StudentDynamicHandler implements InvocationHandler {

	private Object target;

	public StudentDynamicHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("dynamic Proxy start");
		method.invoke(target, args);
		System.out.println("dynamic Proxy over");

		return null;
	}
}
