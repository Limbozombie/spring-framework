package Chapter02.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author 271636872@qq.com
 * @since 2020/4/12 11:09
 */
public class TranslatePostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (bean instanceof Translatable &&
				"translatableStudent".equals(beanName)) {
			Translatable translatable = (Translatable) bean;
			String target = translate(translatable.getSource());
			translatable.setTarget(target);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private String translate(String source) {
		return source + " 的翻译";
	}
}
