package Chapter02.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 271636872@qq.com
 * @since 2020/4/12 19:12
 */
public class MethodExecutionEventPublisher implements ApplicationEventPublisherAware {

	private final List<MethodExecutionEventListener> listeners = new ArrayList<>();

	public void methodToMonitor() {
		MethodExecutionEvent event2Publish =
				new MethodExecutionEvent(this, "methodToMonitor");
		publishEvent(MethodExecutionStatus.BEGIN, event2Publish);
		// 执行实际的方法逻辑
		publishEvent(MethodExecutionStatus.END, event2Publish);
		MethodExecutionEvent beginEvt = new
				MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.BEGIN);
		this.eventPublisher.publishEvent(beginEvt);
// 执行实际方法逻辑
		MethodExecutionEvent endEvt = new
				MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.END);
		this.eventPublisher.publishEvent(endEvt);
	}

	protected void publishEvent(MethodExecutionStatus status,
								MethodExecutionEvent methodExecutionEvent) {
//		为了避免事件处理期间事件监听器的注册或移除操作影响处理过程
//		对事件发布时点的监听器列表进行了一个安全复制（safe-copy）
		List<MethodExecutionEventListener> safeCopyListeners =
				new ArrayList<>(listeners);
		safeCopyListeners.forEach(listener -> {
			if (MethodExecutionStatus.BEGIN.equals(status)) {
				listener.onMethodBegin(methodExecutionEvent);
			} else {
				listener.onMethodEnd(methodExecutionEvent);
			}
		});
	}

	public void addMethodExecutionEventListener(MethodExecutionEventListener listener) {
		this.listeners.add(listener);
	}


	//事件监听器的注册和移除相关的方法
// 	如果没有提供remove事件监听器的方法，
// 	那么注册的监听器实例会一直被MethodExecutionEventPublisher引用
//  即使已经过期了或者废弃不用了， 也依然存在于MethodExecutionEventPublisher的监听器列表中。
//  这会导致隐性的内存泄漏，在任何事件监听器的处理上都可能出现这种问题
	public void removeListener(MethodExecutionEventListener listener) {
		if (this.listeners.contains(listener))
			this.listeners.remove(listener);
	}

	public void removeAllListeners() {
		this.listeners.clear();
	}

	private ApplicationEventPublisher eventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		eventPublisher = applicationEventPublisher;
	}
}
