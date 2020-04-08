package Chapter02.bean;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author 271636872@qq.com
 * @since 2020/4/6 15:57
 */
public class MethodReplacerImpl implements MethodReplacer {
	@Override
	public String reimplement(Object target, Method method, Object[] args)
			throws Throwable {

		System.out.println("method: \t" + method.getName());
		System.out.println("target: \t" + target.getClass().getName());
		System.out.println("args: \t" + Arrays.toString(args));
		//执行以下方法报错？？
//		System.out.println("return: \t"+method.invoke(target, args));

		return "这才是返回值，牛逼吧？";

	}
}
