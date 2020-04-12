package Chapter02;

import Chapter02.bean.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

	private final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");


	@Before
	public void setup() {
		beanFactory.registerScope("thread", new ThreadScope());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));

		PropertyPlaceholderConfigurer placeholder = new PropertyPlaceholderConfigurer();
		placeholder.setLocation(new ClassPathResource("jdbc.properties"));
		placeholder.postProcessBeanFactory(beanFactory);

		PropertyOverrideConfigurer override = new PropertyOverrideConfigurer();
		override.setLocation(new ClassPathResource("overrideJdbc.properties"));
		override.postProcessBeanFactory(beanFactory);

		CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
		customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{
				registry -> registry.registerCustomEditor(LocalDate.class, new DatePropertyEditor("yyyy/MM/dd"))
		});
		customEditorConfigurer.postProcessBeanFactory(beanFactory);

		beanFactory.addBeanPostProcessor(new TranslatePostProcessor());
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

	/**
	 * “每次调用都让容器返回新的对象实例”
	 */
	@Test
	public void StudentPersister() {

		StudentPersister persister1 = beanFactory.getBean("studentPersister", StudentPersister.class);
		StudentPersister persister2 = beanFactory.getBean("studentPersister", StudentPersister.class);
		assertEquals(persister1.getStudent().hashCode(), persister2.getStudent().hashCode());

	}

	/**
	 * 方法注入 Method Injection
	 */
	@Test
	public void MethodInjection() {

		StudentPersister lookUpStudentPersister1 = beanFactory.getBean("lookUpStudentPersister", StudentPersister.class);
		StudentPersister lookUpStudentPersister2 = beanFactory.getBean("lookUpStudentPersister", StudentPersister.class);

		assertNotEquals(lookUpStudentPersister1.getStudent().hashCode(), lookUpStudentPersister2.getStudent().hashCode());

	}

	/**
	 * 方法替换  Method Replacement
	 */
	@Test
	public void MethodReplacement() {

		StudentPersister beReplaced = beanFactory.getBean("beReplaced", StudentPersister.class);

		System.out.println(beReplaced.getTime());

	}

	/**
	 * 实现BeanFactoryAware接口
	 */
	@Test
	public void implementsBeanFactoryAware() {
		StudentPersister awareStudentPersister1 = beanFactory.getBean("awareStudentPersister", StudentPersister.class);
		StudentPersister awareStudentPersister2 = beanFactory.getBean("awareStudentPersister", StudentPersister.class);

		assertNotEquals(awareStudentPersister1.getStudentFromInnerFactory().hashCode(), awareStudentPersister2.getStudentFromInnerFactory().hashCode());

	}

	/**
	 * 使用ObjectFactoryCreatingFactoryBean
	 * 也可以使用ServiceLocatorFactoryBean，可以让我们自定义工厂接口，而不用非要使用ObjectFactory
	 */
	@Test
	public void factoryBeanInSpring() {
		StudentPersister objectFactoryStudentPersister1 = beanFactory.getBean("objectFactoryStudentPersister", StudentPersister.class);
		StudentPersister objectFactoryStudentPersister2 = beanFactory.getBean("objectFactoryStudentPersister", StudentPersister.class);

		assertNotEquals(objectFactoryStudentPersister1.getStudentFromObjectFactory().hashCode(), objectFactoryStudentPersister2.getStudentFromObjectFactory().hashCode());


	}

	/**
	 * 使用PropertyPlaceholderConfigurer 替换XML配置中占位符
	 * 拆分业务配置与系统配置信息
	 */
	@Test
	public void propertyPlaceholderConfigurer() {
		DataSource dataSourceFromApplicationContext = applicationContext.getBean("dataSource", DataSource.class);
		DataSource dataSourceFromBeanFactory = beanFactory.getBean("dataSource", DataSource.class);

		assertEquals(dataSourceFromApplicationContext.getUrl(), dataSourceFromBeanFactory.getUrl());
		assertEquals(dataSourceFromApplicationContext.getDriverClassName(), dataSourceFromBeanFactory.getDriverClassName());
		assertEquals(dataSourceFromApplicationContext.getUsername(), dataSourceFromBeanFactory.getUsername());
		assertEquals(dataSourceFromApplicationContext.getPassword(), dataSourceFromBeanFactory.getPassword());

	}


	/**
	 * 使用PropertyOverrideConfigurer 覆盖XML中的配置
	 * 如果有多个，最后一个生效
	 * 文件中的配置的规则 beanName.propertyName=value
	 */
	@Test
	public void propertyOverrideConfigurer() {

		DataSource dataSourceFromApplicationContext = applicationContext.getBean("dataSource", DataSource.class);
		DataSource dataSourceFromBeanFactory = beanFactory.getBean("dataSource", DataSource.class);

		assertEquals("overrideUsername", dataSourceFromApplicationContext.getUsername());
		assertEquals("overridePassword", dataSourceFromApplicationContext.getPassword());

		assertEquals("overrideUsername", dataSourceFromBeanFactory.getUsername());
		assertEquals("overridePassword", dataSourceFromBeanFactory.getPassword());

	}

	@Test
	public void customEditorConfigurer() {
		LocalDateWrapper localDateWrapperFromApplicationContext = applicationContext.getBean("localDateWrapper", LocalDateWrapper.class);
		LocalDateWrapper localDateWrapperFromBeanFactory = beanFactory.getBean("localDateWrapper", LocalDateWrapper.class);
		assertEquals(localDateWrapperFromApplicationContext.getDate(), localDateWrapperFromBeanFactory.getDate());
	}

	/**
	 * 使用BeanWrapper对bean实例操作很方便
	 * 可以免去直接使用Java反射API（Java Reflection API）操作对象实例的烦琐
	 */
	@Test
	public void beanWrapper() {
		Object student = BeanUtils.instantiateClass(Student.class);
		BeanWrapper beanWrapper = new BeanWrapperImpl(student);
		beanWrapper.setPropertyValue("name", "名字");
		assertSame(student, beanWrapper.getWrappedInstance());
		assertEquals("名字", beanWrapper.getPropertyValue("name"));
	}

	@Test
	public void translatableStudent() {
		Student studentFromApplicationContext = applicationContext.getBean("translatableStudent", Student.class);
		Student studentFromBeanFactory = beanFactory.getBean("translatableStudent", Student.class);
		assertEquals(studentFromApplicationContext.getTarget(), studentFromBeanFactory.getTarget());
	}

}
