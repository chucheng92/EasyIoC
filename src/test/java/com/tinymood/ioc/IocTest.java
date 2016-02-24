package com.tinymood.ioc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * IoC容器测试
 * 
 * @author 哓哓
 *
 */
public class IocTest {
	private static Container container = new SimpleContainer();

	@BeforeClass
	public static void start() {
		System.out.println("测试开始");
	}

	// @Test
	// public void baseTest(){
	// container.registerBean(Lol.class);
	// // 初始化注入
	// container.initWired();
	//
	// Lol lol = container.getBean(Lol.class);
	// lol.work();
	// }

	// @Test
	// public void iocClassTest() {
	// container.registerBean(Lol2.class);
	// // 初始化注入
	// container.initWired();
	//
	// Lol2 lol = container.getBean(Lol2.class);
	// lol.work();
	// }

	@Test
	public void iocNameTest() {
		container.registerBean("face", new FaceService2());
		container.registerBean("Lol3", Lol3.class);
		// container.registerBean(Lol3.class);
		// 初始化注入
		container.initWired();

		// Lol3 lol = container.getBean(Lol3.class);
		Lol3 lol = container.getBeanByName("Lol3");
		// FaceService2 faceService2 = container.getBeanByName("face");
		// faceService2.buy("Lily", 20);
		lol.work();
	}

	@AfterClass
	public static void end() {
		System.out.println("测试结束");
	}
}
