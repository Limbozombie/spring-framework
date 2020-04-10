package Chapter02.bean;


import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 自定义PropertyEditor
 * 直接继承java.beans.PropertyEditorSupport类以避免实现java.beans.PropertyEditor接口的所有方法
 *
 * @author 271636872@qq.com
 * @since 2020/4/10 23:12
 */
public class DatePropertyEditor extends PropertyEditorSupport {
	private String datePattern;

	public DatePropertyEditor() {
	}

	public DatePropertyEditor(String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public String getAsText() {
		return super.getAsText();
	}


	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(getDatePattern());
		LocalDate localDate = LocalDate.parse(text, dateTimeFormatter);
		setValue(localDate);
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
}
