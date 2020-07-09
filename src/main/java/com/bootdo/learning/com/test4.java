package com.bootdo.learning.com;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/09 15:08 <br>
 * 方法重载对于lmabda表达式的影响
 * @see com.bootdo.learning.com <br>
 */
public class test4 {



    interface Param1 {
        void outInfo(String info);
    }

    interface Param2 {
        void outInfo(String info);
    }

    // 定义重载的方法
    public void lambdaMethod(Param1 param) {
        param.outInfo("hello param1 imooc!");
    }
    public void lambdaMethod(Param2 param) {
        param.outInfo("hello param2 imooc");
    }

    public static void main(String[] args) {
        test4 test4 = new test4();

        test4.lambdaMethod(new Param1() {
            @Override
            public void outInfo(String info) {
                System.out.println(info);
            }
        });

        test4.lambdaMethod(new Param2() {
            @Override
            public void outInfo(String info) {
                System.out.println(info);
            }
        });

        /*
        lambda表达式存在类型检查-> 自动推导lambda表达式的目标类型
        lambdaMethod() -> 方法 -> 重载方法
                -> Param1  函数式接口
                -> Param2  函数式接口
                调用方法-> 传递Lambda表达式-> 自动推导->
                    -> Param1 | Param2
        */
//        test4.lambdaMethod((String info) -> {
//            System.out.println(info);
//        });

    }
}
