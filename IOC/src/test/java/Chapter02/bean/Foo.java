package Chapter02.bean;


/**
 * @author 271636872@qq.com
 * @since 2020/4/5 22:21
 */
public class Foo
{
	private BarInterface barInstance;
	public Foo()
	{
// barInterface = BarInterfaceFactory.getInstance();
		//或者
// barInterface = new BarInterfaceFactory().getInstance();
	}



	public BarInterface getBarInstance() {
		return barInstance;
	}

	public void setBarInstance(BarInterface barInstance) {
		this.barInstance = barInstance;
	}
}
