package com.bootdo.gper.pattern.factory.FactoryMethod;

import com.bootdo.gper.pattern.factory.ICourse;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 16:38 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.gper.pattern.factory.FactoryMethod <br>
 */
public class FactoryMethodTest {

    public static void main(String[] args) {
        // 创建统一的 ICourseFactory 课程工厂， 在分别创建 java python 工厂类实现课程工厂，
        // 如果需要新增html课程，则新增一个HTML工厂即可
        ICourseFactory factory = new PythonCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new JavaCourseFactory();
        course = factory.create();
        course.record();
    }
}
