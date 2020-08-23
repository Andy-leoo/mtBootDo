package com.bootdo.gper.pattern.factory.FactoryMethod;

import com.bootdo.gper.pattern.factory.ICourse;
import com.bootdo.gper.pattern.factory.SimpleFactory.PythonCourse;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 16:32 <br>
 * @ python 课程 工厂类
 * @see com.bootdo.gper.pattern.factory.FactoryMethod <br>
 */
public class PythonCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new PythonCourse();
    }
}
