import Chapter03.proxy.cglib.ActionCallBack;
import Chapter03.proxy.dynamic.StudentDynamicHandler;
import Chapter03.proxy.model.IPerson;
import Chapter03.proxy.model.Student;
import Chapter03.proxy.model.Teacher;
import Chapter03.proxy.stable.StudentStaticProxy;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author gulh
 * @since 2020/4/23 15:47
 */
public class Chapter03 {


	@Test
	public void staticProxy() {
		StudentStaticProxy proxy = new StudentStaticProxy(new Student());
		proxy.doAction();
	}

	@Test
	public void dynamicProxy() {
		StudentDynamicHandler handler = new StudentDynamicHandler(new Student());
		IPerson proxyInstance = (IPerson) Proxy.newProxyInstance(
				IPerson.class.getClassLoader(),
				new Class[]{IPerson.class},
				handler);
		proxyInstance.doAction();
	}

	@Test
	public void CGLIBProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Teacher.class);
		enhancer.setCallback(new ActionCallBack());
		Teacher proxy = (Teacher) enhancer.create();
		proxy.doAction();
	}

}
