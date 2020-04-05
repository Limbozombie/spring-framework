package Chapter02.bean;

import java.time.LocalDate;

/**
 * @author 271636872@qq.com
 * @since 2020/4/5 23:27
 */
public class NextDayDateProvider {
	/*
	NextDayDateProvider所声明的依赖dateOfNextDay的类型为LocalDate，而不是NextDayDateFactoryBean。
			也就是说FactoryBean类型的bean定义，
			通过正常的id引用，容器返回的是FactoryBean所“生产”的对象类型
			而非FactoryBean实现本身。
	如果一定要取得FactoryBean本身的话，可以通过在bean定义的id之前加前缀&来达到目的。*/
	private LocalDate dateOfNextDay;

	public LocalDate getDateOfNextDay() {
		return dateOfNextDay;
	}

	public void setDateOfNextDay(LocalDate dateOfNextDay) {
		this.dateOfNextDay = dateOfNextDay;
	}
}
