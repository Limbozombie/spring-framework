package Chapter02;

import Chapter02.bean.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * @author 271636872@qq.com
 * @since 2020/4/4 17:36
 */
public class Chapter02 {

	private final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();


	@Before
	public void setup() {
		beanFactory.registerScope("thread", new ThreadScope());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
	}


	@Test
	public void first() {
		assertEquals("首先", beanFactory.getBean("first", String.class));
	}


	@Test
	public void small2() {
		Student small2 = beanFactory.getBean("small2", Student.class);
		assertNotNull(small2);
		assertEquals("首先", small2.getName());

	}

	@Test
	public void small3() {
		Student small3 = beanFactory.getBean("small3", Student.class);
		assertNotNull(small3);
		assertEquals("首先", small3.getName());
		assertEquals(Byte.valueOf("23"), small3.getAge());
	}

	@Test
	public void dependOn() {
		String first = beanFactory.getBean("first", String.class);
		assertNotNull(first);
		Student small4 = beanFactory.getBean("small4", Student.class);
		assertNotNull(small4);
		assertEquals(first, small4.getName());
	}

	@Test
	public void singleton() {
		Student singleton = beanFactory.getBean("singleton", Student.class);
		assertNotNull(singleton);
		assertEquals("小明", singleton.getName());
		assertEquals(Byte.valueOf("23"), singleton.getAge());
	}

	@Test
	public void threadScope() {
		final int single = 1;
		assertTrue(listBeansHashCode("threadScopeBean").size() > single);
		assertEquals(single, listBeansHashCode("singleton").size());
	}

	private Set<Integer> listBeansHashCode(String beanName) {
		Function<Integer, Integer> task = e -> beanFactory.getBean(beanName, Student.class).hashCode();
		return new ForkJoinPool(5).submit(
				() ->
						IntStream.rangeClosed(1, 5).boxed().parallel()
								.map(task).collect(Collectors.toSet())
		).join();
	}

	@Test
	public void factory() {
		BarInterfaceImpl barByClass = beanFactory.getBean("barByClass", BarInterfaceImpl.class);
		Foo fooByClass = beanFactory.getBean("fooByClass", Foo.class);
		assertEquals(barByClass, fooByClass.getBarInstance());

		BarInterfaceImpl barWithPara = beanFactory.getBean("barWithPara", BarInterfaceImpl.class);
		Foo fooWithPara = beanFactory.getBean("fooWithPara", Foo.class);
		assertEquals("name", barWithPara.getName());
		assertEquals(barWithPara, fooWithPara.getBarInstance());

		BarInterfaceImpl barByInstance = beanFactory.getBean("barByInstance", BarInterfaceImpl.class);
		Foo fooByInstance = beanFactory.getBean("fooByInstance", Foo.class);
		assertEquals(barByInstance, fooByInstance.getBarInstance());

	}

	@Test
	public void factoryBean() {

		NextDayDateProvider provider = beanFactory.getBean("nextDayDateProvider", NextDayDateProvider.class);

		Object factoryBean = beanFactory.getBean("nextDayDate");
		assertTrue(factoryBean instanceof LocalDate);

		Object bean = beanFactory.getBean("&nextDayDate");
		assertTrue(bean instanceof NextDayDateFactoryBean);

		assertEquals(LocalDate.now().plusDays(1), provider.getDateOfNextDay());

	}

}
