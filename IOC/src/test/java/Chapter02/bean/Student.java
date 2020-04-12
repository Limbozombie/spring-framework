package Chapter02.bean;

import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

/**
 * @author 271636872@qq.com
 * @since 2020/4/5 17:31
 */
public class Student implements Translatable, InitializingBean {

	private String name;
	private Byte age;

	public Student() {
	}

	public Student(String name, Byte age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getAge() {
		return age;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Student student = (Student) o;

		if (!Objects.equals(name, student.name)) return false;
		return Objects.equals(age, student.age);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
//		int result = name != null ? name.hashCode() : 0;
//		result = 31 * result + (age != null ? age.hashCode() : 0);
//		return result;
	}

	private String target;
	private String source;

	@Override
	public void setTarget(String target) {
		this.target = target;
	}


	public String getTarget() {
		return target;
	}

	@Override
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("init");
	}
}
