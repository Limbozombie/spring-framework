package Chapter03.proxy.model;

/**
 * @author gulh
 * @since 2020/4/23 15:48
 */
public class Student implements IPerson {
	@Override
	public void doAction() {
		System.out.println("Student");
	}
}
