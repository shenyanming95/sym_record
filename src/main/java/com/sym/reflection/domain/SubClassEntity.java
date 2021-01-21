package com.sym.reflection.domain;

/**
 * Java的反射机制:本类是扮演子类
 * 
 * 同样也是用来测试Field、Method、Constructor的若干方法
 *
 */
public final class SubClassEntity extends SuperClassEntity {

	// 被public修饰的子类成员变量
	public String number = "123456";

	// 被private修饰的子类成员变量
	private static String name = "default";

	// 被private修饰的子类成员变量
	protected int mix = 0;

	// public的子类无参构造方法
	public SubClassEntity() {
		
	}

	// private的子类有参构造方法
	private SubClassEntity(String name) {
		this.name = name;
	}

	// set方法
	public void setName(String name) {
		this.name = name;
	}
   //  get方法
	public String getName() {
		return this.name;
	}
	public int getMix() {
		return this.mix;
	}

	// 子类公有方法
	public void print() {
		System.out.println("我是公有的方法==》学生的姓名为" + name);
	}

	// 子类私有方法
	private void say() {
		System.out.println("我是私有的方法");
	}

	// 子类protect方法
	protected int getNum(int a, int b) {
		return a + b;
	}
}
