package Chapter02.event;

import java.util.EventListener;

/**
 * @author 271636872@qq.com
 * @since 2020/4/12 19:07
 */
public interface MethodExecutionEventListener extends EventListener {
	/**
	 * 处理方法开始执行的时候发布的MethodExecutionEvent事件
	 */
	void onMethodBegin(MethodExecutionEvent evt);

	/**
	 * 处理方法执行将结束时候发布的MethodExecutionEvent事件
	 */
	void onMethodEnd(MethodExecutionEvent evt);
}
