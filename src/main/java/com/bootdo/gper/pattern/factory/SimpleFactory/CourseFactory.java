package com.bootdo.gper.pattern.factory.SimpleFactory;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 14:32 <br>
 * @  工厂类  ， 课程工厂类
 * @see com.bootdo.gper.pattern.factory.SimpleFactory <br>
 */
public class CourseFactory {

    // 2. 根据传入的 名称来判断 创建返回那种实例
//    public ICourse create(String courseName){
//
//        if (courseName.equals("java")) {
//            return new JavaCourse();
//        }else if (courseName.equals("python")){
//            return new PythonCourse();
//        }
//        return null;
//    }

    // 3. 我们如果新增了 新的课程 还需要去courseFactory 添加逻辑代码。不符合代码的开闭原则
    // 这里我们可以使用反射技术
//    public ICourse create(String courseName){
//
//        try {
//            if (!(null == courseName || "".equals(courseName))) {
//                return (ICourse) Class.forName(courseName).newInstance();
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }
//        return null;
//    }

    //4.
    public ICourse create(Class<? extends ICourse> clazz){

        try {
            if (null != clazz) {
                return clazz.newInstance();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
}
