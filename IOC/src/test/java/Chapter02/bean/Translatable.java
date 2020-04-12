package Chapter02.bean;

/**
 * 会翻译
 * 用于区分将要处理的对象实例的标记接口（Marker Interface）
 *
 * @author 271636872@qq.com
 * @since 2020/4/12 10:51
 */
public interface Translatable {

	void setTarget(String source);

	String getSource();
}
