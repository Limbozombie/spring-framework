package Chapter02.bean;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditor;
import java.time.LocalDate;

/**
 * @author 271636872@qq.com
 * @since 2020/4/11 0:10
 */
public class DatePropertyEditorRegistrar implements PropertyEditorRegistrar {

	private PropertyEditor editor;

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(LocalDate.class, getEditor());
	}

	public PropertyEditor getEditor() {
		return editor;
	}

	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}
}
