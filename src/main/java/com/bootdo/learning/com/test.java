package com.bootdo.learning.com;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/07 22:32 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com <br>
 */
public class test {

    public static void main(String[] args) {


        //匿名内部类 ，实现抽象方法
//        IUserCredential ic2 = new IUserCredential() {
//            @Override
//            public String verifyUser(String username) {
//                return "admin".equals(username)?"管理员":"用户";
//            }
//        };
//
//        System.out.println(ic2.verifyUser("admin"));
//        System.out.println(ic2.verifyUser("user"));

        // 使用 lambda 表达式，针对函数式表达式简单实现
//        IUserCredential ic3 = (String username) -> {
//            return "admin".equals(username)?"lamd管理员":"lamd用户";
//        };
//
//        System.out.println(ic3.verifyUser("admin"));

        // 三、JDK8 提供的常见函数式接口
        // java.util.function 提供了大量函数式接口

        // Predicate 接收参数T ,返回Boolean结果  boolean test(T t);
//        Predicate<String> predicate = (String username) -> {
//            return "admin".equals(username);
//        };
//
//        System.out.println(predicate.test("admin"));
//        System.out.println(predicate.test("user"));

        //Consumer 接收参数T ，无返回值
//        Consumer<String> consumer = (String msg) -> {
//            System.out.println("要发送的消息：" + msg);
//            System.out.println("消息发送完成");
//        };
//        consumer.accept("consumer11");

        //Function 《T,R》 接收参数 T ,返回R 参数
//        Function<String , Integer> fun  = (String o1) -> {
//            //类似前端传输 性别， 返回 数字存库
//            return "male".equals(o1)?1:2;
//        };
//        System.out.println(fun.apply("male"));
//        System.out.println(fun.apply("female"));

        //Supplier 不接受任何参数，直接通过get()获取指定类型的对象
//        Supplier<String> sup = () -> {
//            return UUID.randomUUID().toString().replace("-","");
//        };
//        System.out.println(sup.get());
//        System.out.println(sup.get());
//        System.out.println(sup.get());

        //UnaryOperator 接口参数T对象，执行业务处理后，返回更新后的T对象
//        UnaryOperator<String> uo = (String img) -> {
//            img += "[480,1280]";
//            return img;
//        };
//
//        System.out.println(uo.apply("图片："));

        //BinaryOperator 接口接收两个T对象，执行业务处理后，返回一个T对象

//        BinaryOperator<Integer> bo = (Integer o1,Integer o2) -> {
//            return o1>o2 ? o1:o2;
//        };
//        System.out.println(bo.apply(14,28));


        //四 . lambda表达式的基本语法
        /*
            1)声明：就是和lambda表达式绑定的接口类型
            2)参数：包含在一对圆括号中，和绑定的接口中的抽象方法中的参数个数及顺序一致。
            3)操作符：->
            4)执行代码块：包含在一对大括号中，出现在操作符号的右侧

            [接口声明] = (参数) -> {执行代码块};
         */
        Ilambda1 i1 = () -> {
            System.out.println("hello imooc!");
        };
        i1.test();

        Ilambda2 i2 = (String name,Integer age) ->{
            System.out.println(name + "say: my year's old is " + age);
        };
        i2.test("lisi",18);

        Ilambda2 i22 = (name , age) -> {
            System.out.println(name + "say: my year's old is " + age);
        };
        i22.test("wangwu " ,20);

        Ilambda3 i3 = (Integer x ,Integer y) -> {
          int z = x + y;
          return z;
        };
        System.out.println(i3.test(11, 22));

        Ilambda3 i31 = (x ,y)-> x+y;
        System.out.println(i31.test(1, 22));
    }

    // 没有参数，没有返回值的lambda表达式绑定的接口
    interface Ilambda1{
        void test();
    }

    // 带有参数，没有返回值的lambda表达式
    interface Ilambda2{
        void test(String name,Integer age);
    }

    // 带有参数，带有返回值的lambda表达式
    interface Ilambda3{
        int test(Integer o1 ,Integer o2);
    }
}
