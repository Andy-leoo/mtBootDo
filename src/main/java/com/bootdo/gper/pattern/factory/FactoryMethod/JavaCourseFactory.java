package com.bootdo.gper.pattern.factory.FactoryMethod;

import com.bootdo.gper.pattern.factory.ICourse;
import com.bootdo.gper.pattern.factory.SimpleFactory.JavaCourse;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 16:23 <br>
 * @ java 课程工厂
 * @see com.bootdo.gper.pattern.factory.FactoryMethod <br>
 */
public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}
