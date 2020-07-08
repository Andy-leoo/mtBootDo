package com.bootdo.learning.com;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/08 15:59 <br>
 * @
 *      lambda表达式 变量捕获
 * @see com.bootdo.learning.com <br>
 */
public class test2 {
    String s1 = "全局变量";

    // 匿名内部类型对于变量的访问
    public void testInnerClass(){
        String s2 = "局部变量";

        new Thread(new Runnable() {
            String s3 = "内部变量";
            @Override
            public void run() {
                // 全局变量 访问，
//                System.out.println(this.s1);// this关键字~表示是当前内部类型的对象
                System.out.println(s1);
                // 局部变量 访问，
                System.out.println(s2);// 局部变量的访问，~不能对局部变量进行数据的修改  ,被认为是[final]类型
//                常规方式的匿名内部类的外部变量捕获
//                  匿名内部类捕获的外部局部变量，在匿名内部类的内部不能对捕获的外部局部变量进行重现赋值，
//                  因为在编译时会将被匿名内部类捕获的外部局部变量增加finall关键字修饰，所以不能被重新赋值
//                s2 = "kskks";

                //内部变量 访问
                System.out.println(s3);
                s3 = "ssss";
                System.out.println(this.s3);

            }
        }).start();
    }

    // 2. lambda表达式变量捕获
    public void testLambda() {
        String s2 = "局部变量lambda";

        new Thread(() -> {
            String s3 = "内部变量lambda";
            // 全局变量 访问，
            //Lambda中的this关键字，表示的是所属方法所在的类型的对象，
            // 即Lambda表达式所在的地方不在创建对象级的作用域
            //Lambda表达式简化了对变量的访问
                System.out.println(this.s1);// this关键字，表示的就是所属方法所在类型的对象
            // 局部变量 访问，
            System.out.println(s2);
//            s2 = "hello";// 不能进行数据修改，默认推导变量的修饰符：final

            //内部变量 访问
            System.out.println(s3);
//            System.out.println(this.s3);

        }).start();
    }

    public static void main(String[] args) {
        test2 app = new test2();
//        app.testInnerClass();
        app.testLambda();

    }
}
