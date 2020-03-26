package com.sym.clone.objectClone;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象克隆的实体类,用于深克隆。 实现对象克隆,需要实现Cloneable接口,如果是序列化方式的深克隆,还需要实现Serializable接口
 * 
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
public class Person implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L; // 最好是显式声明serialVersionUID
	private int id;
	private String name;
	private Student student;// Person对其他类的引用

	/**
	 * 浅克隆方式
	 */
	public Person shallowClone() {
		Person p = null;
		try {
			//浅克隆很简单,在本类中直接super.clone()即可
			p = (Person) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 深克隆实现方式之一,继续使用clone()方法
	 * @return
	 */
	public Person deepClone() {
		Person p = null;
		try {
			//深克隆,还需要对引用的实体对象也进行克隆,这就要求,被引用的实体类也要改写clone()方法
			p = (Person) super.clone();
			p.student = (Student) p.student.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;
	}


	/**
	 * 深克隆实现方式之二,使用Java的序列化
	 * @return
	 */
	public Person serializableClone() {
		try {
			//ByteArrayOutputStream,即字节数组输出流,创建实例对象时,会在内存创建一个字节数组的缓冲区,将java的资源写入到内存数组缓冲区中
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oop = new ObjectOutputStream(bos);
			oop.writeObject(this);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return (Person)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
