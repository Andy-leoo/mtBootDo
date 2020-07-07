package com.bootdo.train.controller.portal;

import com.bootdo.blog.service.ContentService;
import com.bootdo.common.annotation.Log;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.bootdo.system.service.UserService;
import com.bootdo.system.vo.UserVO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.*;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/train")
public class IndexController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private TrainInfoService trainInfoService;
    @Autowired
    private TrainFilesService trainFilesService;
    @Autowired
    private TrainNewsService trainNewsService;
    @Autowired
    private TrainFilesUserService trainFilesUserService;
    @Autowired
    private TrainInfoUserService trainInfoUserService;
    @Autowired
    private TrainNewsUserService trainNewsUserService;
    @Autowired
    private DeptModuleService deptModuleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private LeaderSpeechService leaderSpeechService;
    @Autowired
    private LeaderSpeechUserService leaderSpeechUserService;

    @Autowired
    private TrainNotificationService trainNotificationService;
    @Autowired
    private TrainNotificationUserService trainNotificationUserService;

    @Autowired
    private TrainTelegramService trainTelegramService;
    @Autowired
    private TrainTelegramUserService trainTelegramUserService;

    @Autowired
    private TrainEwarningService trainEwarningService;
    @Autowired
    private TrainEwarningUserService trainEwarningUserService;

    @RequestMapping("")
    public String login(){
        return "portal/login";
    }

    @Log("首页登入")
    @PostMapping("/login")
    @ResponseBody
    public R ajaxLogin(String username , String password, HttpSession session){
        return userService.loginUser(username,password,session);
    }

    @RequestMapping("/index")
    public String index(ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return "redirect:/train";
        }
        boolean check_train_files = false;
        boolean check_train_info = false;
        boolean check_train_news = false;
        boolean check_leader_speech = false;//
        boolean check_train_notification = false;//
        boolean check_train_telegram = false;//
        boolean check_train_ewarning = false;//

        String modules = this.checkModules(user.getDeptId());
        if (modules.contains(Const.TRAIN_FILE_NUM)){
            check_train_files = true;
        }
        if (modules.contains(Const.TRAIN_NEWS_NUM)){
            check_train_news = true;
        }
        if (modules.contains(Const.TRAIN_INFO_NUM)){
            check_train_info = true;
        }
        if (modules.contains(Const.LEADER_SPEECH_NUM)){
            check_leader_speech = true;
        }
        if (modules.contains(Const.TRAIN_NOTIFICATION_NUM)){
            check_train_notification = true;
        }
        if (modules.contains(Const.TRAIN_TELEGRAM_NUM)){
            check_train_telegram = true;
        }
        if (modules.contains(Const.TRAIN_EWARNING_NUM)){
            check_train_ewarning = true;
        }
        /*
            办公管理
         */
        List<Office> offices = officeService.officeList();
        modelMap.put("officeList",offices);
         /*
            段发文件
         */
         if(check_train_files){
             List<TrainFilesUser> trainFilesUsers = trainFilesUserService.selectByUserId(user.getUserId());
             List<TrainFiles> trainFiles = new ArrayList<>();
             for (TrainFilesUser fileUser: trainFilesUsers) {
                 TrainFiles trainFile = trainFilesService.selectByIdAndUserId(Integer.valueOf(fileUser.getFilesId().toString()),fileUser.getUserId());
                 trainFiles.add(trainFile);
             }
             modelMap.put("trainFiles" , trainFiles);
         }

        /*
            段发新闻
         */
        if(check_train_news){
            List<TrainNewsUser> trainNewsUsers = trainNewsUserService.selectByUserId(user.getUserId());
            List<TrainNews> trainNews = new ArrayList<>();
            int i = 0;
            for (TrainNewsUser newsUser:trainNewsUsers) {
                TrainNews trainNew = trainNewsService.selectOneById(Integer.valueOf(newsUser.getNewsId().toString()));
                if(i==0){//富文本提取纯文本
                    trainNew.setDetail(RegEx_util.splitAndFilterString(trainNew.getDetail(),100));
                }
                trainNews.add(trainNew);
                i++;
            }
            modelMap.put("trainNews" , trainNews);
        }

        /*
            京车信息
         */
        if(check_train_info){
            List<TrainInfoUser> trainInfoUsers = trainInfoUserService.selectByUserId(user.getUserId());
            List<TrainInfo> trainInfos = new ArrayList<>();
            for (TrainInfoUser infoUser: trainInfoUsers) {
                TrainInfo trainInfo = trainInfoService.selectById(Integer.valueOf(infoUser.getInfoId().toString()));
                trainInfos.add(trainInfo);
            }
            modelMap.put("trainInfos",trainInfos);
        }
        /*
        * 领导讲话
        * */
        if(check_leader_speech){
            List<LeaderSpeechUser> leaderSpeechUsers = leaderSpeechUserService.selectByUserId(user.getUserId());
            List<LeaderSpeech> leaderSpeechs = new ArrayList<>();
            for (LeaderSpeechUser leaderSpeechUser:leaderSpeechUsers){
                LeaderSpeech leaderSpeech = leaderSpeechService.selectById(Integer.valueOf(leaderSpeechUser.getLeaderId().toString()));
                leaderSpeechs.add(leaderSpeech);
            }
            modelMap.put("leaderSpeechs" , leaderSpeechs);
        }

        /*
         * 通知通报
         * */
        if(check_train_notification){
            List<TrainNotificationUser> trainNotificationUsers = trainNotificationUserService.selectByUserId(user.getUserId());
            List<TrainNotification> trainNotifications = new ArrayList<>();
            for (TrainNotificationUser trainNotificationUser:trainNotificationUsers){
                TrainNotification trainNotification = trainNotificationService.selectOneById(Integer.valueOf(trainNotificationUser.getNotificationId().toString()));
                trainNotifications.add(trainNotification);
            }
            modelMap.put("trainNotifications" , trainNotifications);
        }
        /*
         * 转发电报
         * */
        if(check_train_telegram){
            List<TrainTelegramUser> trainTelegramUsers = trainTelegramUserService.selectByUserId(user.getUserId());
            List<TrainTelegram> trainTelegrams = new ArrayList<>();
            for (TrainTelegramUser trainTelegramUser:trainTelegramUsers){
                TrainTelegram trainTelegram = trainTelegramService.selectOneById(Integer.valueOf(trainTelegramUser.getTelegramId().toString()));
                trainTelegrams.add(trainTelegram);
            }
            modelMap.put("trainTelegrams" , trainTelegrams);
        }

        /*
         * 安全预警
         * */
        if(check_train_ewarning){
            List<TrainEwarningUser> trainEwarningUsers = trainEwarningUserService.selectByUserId(user.getUserId());
            List<TrainEwarning> trainEwarnings = new ArrayList<>();
            for (TrainEwarningUser trainEwarningUser:trainEwarningUsers){
                TrainEwarning trainEwarning = trainEwarningService.selectOneById(Integer.valueOf(trainEwarningUser.getEwarningId().toString()));
                trainEwarnings.add(trainEwarning);
            }
            modelMap.put("trainEwarnings" , trainEwarnings);
        }

        modelMap.put("check_file",check_train_files);
        modelMap.put("check_news",check_train_news);
        modelMap.put("check_info",check_train_info);
        modelMap.put("check_speech",check_leader_speech);
        modelMap.put("check_notification",check_train_notification);
        modelMap.put("check_telegram",check_train_telegram);
        modelMap.put("check_ewarning",check_train_ewarning);
        return "/portal/index";
    }

    private String checkModules(Long deptId) {
        int i = 0;
        Long dept_id = null;
        DeptModule deptModule = null;
        Long moduleId = null;
        DeptDO deptDO = null;
        while (true){
            if (i == 0){
                dept_id = deptId;
            }
            deptModule = deptModuleService.queryByDeptId(dept_id);
            if (deptModule == null){
                deptDO = deptService.get(dept_id);
                dept_id = deptDO.getParentId();
                i++;
            }
            if (deptModule !=null || dept_id == 0){
                break;
            }
        }
        if (dept_id==0){
            moduleId = 0L;//默认id为0
        }else {
            moduleId = deptModule.getModuleId();
        }
        Module module = moduleService.queryById(moduleId);
        String modules = module.getModules();
        return modules;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        // 再将session进行注销
        session.invalidate();//todo
        return "redirect:/train";
    }

    @GetMapping("/resetPwd")
    public String resetPwd(HttpSession session, Model model) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        model.addAttribute("user", user);
        return  "/portal/resetPwd";
    }

    @PostMapping("/resetPwdSave")
    @ResponseBody
    public R resetPwd(UserVO userVO, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        try{
            if (user == null){
                return R.error(1,"请重新登入");
            }
            //根据当前登入用户 去取库中旧密码
            String oldPwd = userService.takeOldPwd(user.getUserId());
            return userService.resetPwd(userVO, user, oldPwd);
        }catch (Exception e){
            return R.error(1,e.getMessage());
        }
    }


    public static void main(String[] args) {
            
    }

}
