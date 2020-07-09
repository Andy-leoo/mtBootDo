package com.bootdo.learning.com.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/08 22:06 <br>
 * @   lambda表达式类型检查
 * @see com.bootdo.learning.com <br>
 */
public class test3 {

    public static void test(myInterface<String, List> inter){
        List<String> list = inter.startegy("hello", new ArrayList());
        System.out.println(list);
    }

    public static void main(String[] args) {
        test(new myInterface<String, List>() {
            @Override
            public List startegy(String s, List list) {
                list.add(s);
                return list;
            }
        });

        test((x,y)->{
            y.add(x);
            return y;
        });
        /*
            (x,y)->{..} --> test(param) --> param==MyInterface --> lambda表达式-> MyInterface类型
            这个就是对于lambda表达式的类型检查，MyInterface接口就是lambda表达式的目标类型(target typing)

            (x,y)->{..} --> MyInterface.strategy(T r, R r)--> MyInterface<String, List> inter
                --> T==String R==List --> lambda--> (x, y) == strategy(T t , R r)--> x==T==String  y==R==List
                lambda表达式参数的类型检查
         */
    }

}

@FunctionalInterface
interface myInterface<T,R>{
    R startegy(T t,R r);
}