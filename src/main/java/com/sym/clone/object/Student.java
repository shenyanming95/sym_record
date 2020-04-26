package com.sym.clone.object;

import java.io.Serializable;

/**
 * 对象克隆的实体类,主要用于浅克隆,需要实现Cloneable接口
 * 
 * 当其他类有本类的引用时,如果其他类用序列化实现深克隆,本类也要实现Serializable接口
 * 
 * @author ym.shen
 *
 */
public class Student implements Cloneable,Serializable{

	private int id;

	private boolean tag;

	/**
	 * 克隆实现方式,调用Object.clone()并修改方法修饰为public
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isTag() {
		return tag;
	}

	public void setTag(boolean tag) {
		this.tag = tag;
	}

	public Student(int id, boolean tag) {
		this.id = id;
		this.tag = tag;
	}
}
