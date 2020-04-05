package Chapter02.bean;

/**
 * @author 271636872@qq.com
 * @since 2020/4/5 22:42
 */
public class BarInterfaceImpl implements BarInterface {
	private String name;

	public BarInterfaceImpl() {
	}

	public BarInterfaceImpl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
