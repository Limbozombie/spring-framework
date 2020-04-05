package Chapter02.factory;

import Chapter02.bean.BarInterface;
import Chapter02.bean.BarInterfaceImpl;

/**
 * @author 271636872@qq.com
 * @since 2020/4/5 22:42
 */
public class BarInterfaceFactory {

	public BarInterface getInstanceByInstance() {
		return new BarInterfaceImpl();
	}


	public static BarInterface getInstanceByClass() {
		return new BarInterfaceImpl();
	}

	public static BarInterface getInstanceWithPara(String name) {
		return new BarInterfaceImpl(name);
	}

}
