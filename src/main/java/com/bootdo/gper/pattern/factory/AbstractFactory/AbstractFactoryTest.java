package com.bootdo.gper.pattern.factory.AbstractFactory;

import com.bootdo.gper.pattern.factory.AbstractFactory.productFactory.JavaCourseFactory;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 17:15 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.gper.pattern.factory.AbstractFactory <br>
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        JavaCourseFactory factory = new JavaCourseFactory();
        factory.createNote().edit();
        factory.createVideo().record();
    }
}
