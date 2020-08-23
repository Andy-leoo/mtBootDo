package com.bootdo.gper.pattern.factory.AbstractFactory.productFactory;

import com.bootdo.gper.pattern.factory.AbstractFactory.product.INote;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.IVideo;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.PythonNote;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.PythonVideo;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 17:12 <br>
 * @T  py 课程具体工厂
 * @see com.bootdo.gper.pattern.factory.AbstractFactory <br>
 */
public class PythonCourseFactory implements CourseFactory {
    @Override
    public INote createNote() {
        return new PythonNote();
    }

    @Override
    public IVideo createVideo() {
        return new PythonVideo();
    }
}
