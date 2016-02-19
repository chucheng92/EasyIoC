package com.tinymood.ioc;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tinymood.ioc.annotation.Autowired;
import com.tinymood.ioc.util.ReflectUtil;

public class SimpleContainer implements Container {
	/**
	 * 保存所有bean对象,格式为com.xxx.xxx
	 */
	private Map<String, Object> beans;

	/**
	 * 存储bean和name的关系
	 */
	private Map<String, String> beanKeys;

	public SimpleContainer() {
		this.beans = new ConcurrentHashMap<String, Object>();
		this.beanKeys = new ConcurrentHashMap<String, String>();
	}

	@Override
	public <T> T getBean(Class<T> clazz) {
		String name = clazz.getName();
		Object object = beans.get(name);
		if (null != object) {
			return (T) object;
		}
		return null;
	}

	@Override
	public <T> T getBeanByName(String name) {
		Object object = beans.get(name);
		if (null != object) {
			return (T) object;
		}
		return null;
	}

	@Override
	public Object registerBean(Object bean) {
		String name = bean.getClass().getName();
		beanKeys.put(name, name);
		beans.put(name, bean);

		return bean;
	}

	@Override
	public Object registerBean(Class<?> clazz) {
		String name = clazz.getName();
		beanKeys.put(name, name);
		Object bean = ReflectUtil.newInstance(clazz);
		beans.put(name, bean);
		
		return bean;
	}

	@Override
	public Object registerBean(String name, Object bean) {
		String className = bean.getClass().getName();
		beanKeys.put(name, className);
		beans.put(className, bean);

		return bean;
	}

	@Override
	public void remove(Class<?> clazz) {
		String className = clazz.getName();
		if (null != className && !className.equals("")) {
			beanKeys.remove(className);
			beans.remove(className);
		}
	}

	@Override
	public void removeByName(String name) {
		String className = beanKeys.get(name);
		if (null != className && !className.equals("")) {
			beanKeys.remove(name);
			beans.remove(className);
		}
	}

	@Override
	public Set<String> getBeanNames() {
		return beanKeys.keySet();
	}

	@Override
	public void initWired() {
		Iterator<Map.Entry<String, Object>> it = beans.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			Object object = entry.getValue();
			injection(object);
		}
	}

	/**
	 * 注入对象
	 * 
	 * @param object
	 */
	public void injection(Object object) {
		// 所有字段
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				// 需要注入的字段
				Autowired autowired = field.getAnnotation(Autowired.class);
				if (null != autowired) {
					// 要注入的字段
					Object autoWiredField = null;

					String name = autowired.name();
					if (!name.equals("")) {
						String className = beanKeys.get(name);
						if (null != className && !className.equals("")) {
							autoWiredField = beans.get(className);
						}
						if (null == autoWiredField) {
							throw new RuntimeException("Unable to load " + name);
						}
					} else {
						if (autowired.value() == Class.class) {
							autoWiredField = recursiveAssembly(field.getType());
						} else {
							// 指定装配类
							autoWiredField = this.getBean(autowired.value());
							if (null == autoWiredField) {
								autoWiredField = recursiveAssembly(autowired.value());
							}
						}
					}

					if (null == autoWiredField) {
						throw new RuntimeException("Unable to load " + field.getType().getCanonicalName());
					}

					boolean accessiable = field.isAccessible();
					field.setAccessible(true);
					field.set(object, autoWiredField);
					field.setAccessible(accessiable);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Object recursiveAssembly(Class<?> clazz) {
		if (null != clazz) {
			return this.registerBean(clazz);
		}
		return null;
	}
}
