package com.sym.property.jdk;

public class Test {

    public static void main(String[] args) {
        //读取property文件的两种方式
        try {
            Class res = Class.forName("net.sym.property.Resource");
            Resource resource = (Resource) res.newInstance();
            String username = resource.getUserName();
            String password = resource.getPassword();
            System.out.println("username=" + username + "\t" + "password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
