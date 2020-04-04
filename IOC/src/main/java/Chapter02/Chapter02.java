package Chapter02;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 271636872@qq.com
 * @since 2020/4/4 17:36
 */
public class Chapter02 {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter02.xml");
		System.out.println(context.getBean("first", String.class));
	}
}
