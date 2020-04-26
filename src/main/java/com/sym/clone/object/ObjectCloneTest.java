package com.sym.clone.object;

import org.junit.Test;

/**
 * 对象克隆的测试类
 * @author Administrator
 *
 */
public class ObjectCloneTest {


	@Test
	public void shallowClone() {
		// 准备实体对象
		Student stu = new Student(29, true);
		Person p1 = new Person(110, "警察", stu);
		Person p2 = p1.shallowClone();

		// 测试
		System.out.println("=====比较浅克隆的2个对象基本属性=====");
		System.out.println("person1 = [" + p1.getId() + "\t" + p1.getName() + "]");
		System.out.println("person2 = [" + p2.getId() + "\t" + p2.getName() + "]");
		p1.setId(120);// 改变复制后的对象的属性，看原属性是否会变化
		System.out.println("修改person1的id属性为120");
		System.out.println("person1.id = " + p1.getId());
		System.out.println("person2.id = " + p2.getId());
		System.out.println("=====比较浅克隆的2个对象的引用是否执行同一块内存地址=====");
		System.out.println("true同一个对象,false不同的对象？" + (p1.getStudent() == p2.getStudent()));
		System.out.println();
		System.out.println();
	}

	@Test
	public void deepClone() {
		// 准备实体对象
		Student stu = new Student(29, true);
		Person p1 = new Person(110, "警察", stu);
		Person p2 = p1.deepClone();
		// 测试
		System.out.println("基本属性不比较了,就比较引用是否是同一个对象");
		System.out.println("true则同一个对象，false则不同对象？" + (p1.getStudent() == p2.getStudent()));
		System.out.println();
		System.out.println();
	}

	@Test
	public void serializableClone() {
		// 准备实体对象
		Student stu = new Student(29, true);
		Person p1 = new Person(110, "警察", stu);
		Person p2 = p1.serializableClone();
		// 测试
		System.out.println("基本属性不比较了,就比较引用是否是同一个对象");
		System.out.println("true则同一个对象，false则不同对象？" + (p1.getStudent() == p2.getStudent()));

	}
}
