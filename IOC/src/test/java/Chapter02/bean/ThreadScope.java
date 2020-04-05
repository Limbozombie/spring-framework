package Chapter02.bean;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义scope
 *
 * @author 271636872@qq.com
 * @since 2020/4/5 18:48
 */
public class ThreadScope implements Scope {
	private final ThreadLocal threadScope = ThreadLocal.withInitial(HashMap::new);

	/**
	 * @param name          the name of the object to retrieve
	 * @param objectFactory the {@link ObjectFactory} to use to create the scoped
	 *                      object if it is not present in the underlying storage mechanism
	 * @return
	 */
	@Override
	public Object get(String name, ObjectFactory objectFactory) {
		System.out.println(threadScope.hashCode());
		System.out.println(Thread.currentThread().hashCode());
		Map scope = (Map) threadScope.get();
		Object object = scope.get(name);
		if (object == null) {
			object = objectFactory.getObject();
			scope.put(name, object);
		}
		return object;
	}

	/**
	 * @param name the name of the object to remove
	 * @return
	 */
	@Override
	public Object remove(String name) {
		Map scope = (Map) threadScope.get();
		return scope.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}

}