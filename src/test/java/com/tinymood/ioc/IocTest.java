package com.tinymood.ioc;

public class IocTest {
	private static Container container = new SimpleContainer();
	
	public static void baseTest(){
		container.registerBean(Lol.class);
		// 初始化注入
		container.initWired();
		
		Lol lol = container.getBean(Lol.class);
		lol.work();
	}
	
	public static void iocClassTest() {
		container.registerBean(Lol2.class);
        // 初始化注入
        container.initWired();

        Lol2 lol = container.getBean(Lol2.class);
        lol.work();
	}
	
	public static void iocNameTest(){
        container.registerBean("face", new FaceService2());
        container.registerBean(Lol3.class);
        // 初始化注入
        container.initWired();

        Lol3 lol = container.getBean(Lol3.class);
        lol.work();
    }
	
	public static void main(String[] args) {
		baseTest();
		iocClassTest();
		iocNameTest();
	}
}
