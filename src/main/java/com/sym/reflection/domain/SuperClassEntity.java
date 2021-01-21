package com.sym.reflection.domain;
/**
 * Java 反射机制:扮演父类的角色
 *
 * 用来验证Field、Method、Constructor的若干方法
 */
public class SuperClassEntity {
	
	  //代表被private修饰的父类成员变量
      private String code = "your father";
      
      //代表被protected修饰的父类成员变量
      protected int age = 27;
      
      //代表被public修饰的父类成员变量
      public boolean sex = true;
      
      //代表public的父类无参构造方法
      public SuperClassEntity(){
      }
      
      //代表public的父类有参构造方法
      public SuperClassEntity(String str){
    	  code = str;
      }
      
      //代表private的父类有参构造方法
      private SuperClassEntity(int i){
    	  age = i;
      }
      
      //父类私有方法
      private void getcode(){
    	  System.out.println("code="+code);
      }
      
      //父类protected方法
      protected void getAge(){
    	  System.out.println("age="+age);
      }
      
      //父类公有方法
      public void getSex(){
    	  System.out.println("sex="+sex);
      }
}
