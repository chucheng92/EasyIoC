package com.tinymood.ioc.util;

public class ReflectUtil {
	
	// ----------------------------------------------
	/** 新建对象 */
	public static Object newInstance(String className) {
		Object obj = null;
		try {
			Class<?> clazz = Class.forName(className);
			obj = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 创建一个实例对象 clazz对象
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class<?> clazz) {
		Object obj = null;
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
