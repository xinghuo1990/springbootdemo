package com;

class StaticTest {
    static int a = 1;
    public static void main(String[] args) {
//        Person person = new Man();
        StaticTest staticTest = new StaticTest(5);
    }
    public StaticTest(){
        System.out.println("StaticTest构造函数");
    }

    public StaticTest(int a ){
        System.out.println("StaticTest带参构造函数"+a);
    }

    static {
        System.out.println("StaticTest静态代码块");
    }

    {
        System.out.println("StaticTest非静态代码块");
    }
}

class Person extends StaticTest{
//    public static void main(String[] args) {
//        System.out.println("main函数");
//        new Man();
//    }
    public Person(){
        super.a = 2;
        System.out.println("Person构造函数");
    }
//    static {
//        System.out.println("Person静态代码块");
//    }
//    {
//        System.out.println("Person代码块");
//    }
}

class Man extends Person{

    public Man(){
        System.out.println(super.a);
//        System.out.println("Man构造函数");
    }
//    static {
//        System.out.println("Man静态代码块");
//    }
//    {
//        System.out.println("Man代码块");
//    }
}
