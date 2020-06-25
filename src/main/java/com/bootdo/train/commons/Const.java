package com.bootdo.train.commons;

/**
 * 常量类
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String DEFAULT_ERROR_VIEW = "error";

    public static final String CHECK_ROLE_ADMIN = "管理";

    public static final String BASE_PASSWORD="111111";

    public static final Long BASE_DEPT_ID = 20L;

    public static final Long BASE_SEX_MAN = 96L;

    public static final int TRAIN_NUM = 111;

    public static final String TRAIN_FILE_NUM = "112";//段发文件模块

    public static final String TRAIN_NEWS_NUM = "113";//段务新闻模块

    public static final String TRAIN_INFO_NUM = "114";//京车信息模块

    public static final String LEADER_SPEECH_NUM = "127";//领导讲话模块

    public static final String TRAIN_NOTIFICATION_NUM = "140";//通知通报模块 notification

    public static final String TRAIN_TELEGRAM_NUM = "141";//转发电报模块 telegram

    public static final String TRAIN_EWARNING_NUM = "142";//安全预警模块 ewarning

    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;

    public interface  Role{
        int ROLE_ADMIN = 1; //管理员
        int ROLE_CUSTOMER = 0; //普通用户
    }

    public interface FileStatus{
        int FILE_NO_VIEW = 0;//未查看
        int FILE_HAVE_CHECKED = 1;//已查看
        int FILE_SIGN_FOR = 2;//已签收
    }

}