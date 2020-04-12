package Chapter02.event;

/**
 * @author 271636872@qq.com
 * @since 2020/4/12 19:10
 */
public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {
	@Override
	public void onMethodBegin(MethodExecutionEvent evt) {
		String methodName = evt.getMethodName();
		System.out.println("start to execute the method[" + methodName + "].");
	}

	@Override
	public void onMethodEnd(MethodExecutionEvent evt) {
		String methodName = evt.getMethodName();
		System.out.println("finished to execute the method[" + methodName + "].");
	}
}
