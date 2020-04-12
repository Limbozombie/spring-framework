package Chapter02.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 271636872@qq.com
 * @since 2020/4/12 19:05
 */
public class MethodExecutionEvent extends ApplicationEvent {
	private static final long serialVersionUID = -71960369269303337L;
	private String methodName;
	private MethodExecutionStatus methodExecutionStatus;

	/**
	 * @param source The object on which the Event initially occurred.
	 */
	public MethodExecutionEvent(Object source) {
		super(source);
	}

	public MethodExecutionEvent(Object source, String methodName, MethodExecutionStatus methodExecutionStatus) {
		super(source);
		this.methodName = methodName;
		this.methodExecutionStatus = methodExecutionStatus;
	}

	public MethodExecutionEvent(Object source, String methodName) {
		super(source);
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public MethodExecutionStatus getMethodExecutionStatus() {
		return methodExecutionStatus;
	}
}
