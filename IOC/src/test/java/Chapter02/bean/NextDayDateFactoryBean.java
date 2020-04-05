package Chapter02.bean;

import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDate;

/**
 * @author 271636872@qq.com
 * @since 2020/4/5 23:28
 */
public class NextDayDateFactoryBean implements FactoryBean<LocalDate> {
	public LocalDate getObject() {
		return LocalDate.now().plusDays(1);
	}

	public Class<LocalDate> getObjectType() {
		return LocalDate.class;
	}

	public boolean isSingleton() {
		return false;
	}
}
