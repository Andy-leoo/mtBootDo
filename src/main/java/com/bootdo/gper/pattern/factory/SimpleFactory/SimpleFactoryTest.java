package com.bootdo.gper.pattern.factory.SimpleFactory;

import com.bootdo.gper.pattern.factory.ICourse;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 14:29 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.gper.pattern.factory.SimpleFactory <br>
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        //1. 正常调用类
//        ICourse javaCourse = new JavaCourse();
//        javaCourse.record();

        // 2. 添加了 courseFactory 工厂类  ，根据传入的参数，来判断返回那种课程实例
//        CourseFactory courseFactory = new CourseFactory();
//        courseFactory.create("java");

        // 3. 我们如果新增了 新的课程 还需要去courseFactory 添加逻辑代码。不符合代码的开闭原则
        // 这里我们可以使用反射技术
//        CourseFactory courseFactory = new CourseFactory();
//        ICourse iCourse = courseFactory.create("com.bootdo.gper.pattern.factory.SimpleFactory.JavaCourse");
//        iCourse.record();

        //4. 使用了 反射就不需要再修改courseFactory中的逻辑代码，但是传参是字符串，可控性有待提高，而且还需要强转
        CourseFactory factory = new CourseFactory();
        ICourse course = factory.create(JavaCourse.class);
        course.record();
    }
}
