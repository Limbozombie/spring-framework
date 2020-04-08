package Chapter02.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @author 271636872@qq.com
 * @since 2020/4/6 13:30
 */
public class StudentPersister implements BeanFactoryAware {
	private Student student;

	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * 实现BeanFactoryAware的setBeanFactory方法，内部保存一个BeanFactory对象
	 * 即使 awareStudentPersister 的scope是singleton
	 * getStudentFromInnerFactory 也会返回新的Student实例
	 * 因为awareStudent 的scope是prototype
	 */
	public Student getStudentFromInnerFactory() {
		return beanFactory.getBean("awareStudent", Student.class);
	}


	private ObjectFactory<Student> objectFactory;

	public Student getStudentFromObjectFactory() {
		return objectFactory.getObject();
	}

	public void setObjectFactory(ObjectFactory<Student> objectFactory) {
		this.objectFactory = objectFactory;
	}


	public String getTime() {
		return String.valueOf(System.currentTimeMillis());
	}

}
