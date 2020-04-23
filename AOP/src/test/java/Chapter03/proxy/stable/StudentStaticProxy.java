package Chapter03.proxy.stable;

import Chapter03.proxy.model.IPerson;

/**
 * @author gulh
 * @since 2020/4/23 16:05
 */
public class StudentStaticProxy implements IPerson {


	private IPerson target;

	public StudentStaticProxy(IPerson target) {
		this.target = target;
	}

	@Override
	public void doAction() {

		System.out.println("static Proxy start");
		target.doAction();
		System.out.println("static Proxy over");

	}
}
