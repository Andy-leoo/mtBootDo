package com.bootdo.train.controller.portal;

import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.TrainNotificationService;
import com.bootdo.train.service.TrainNotificationUserService;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/train/notification")
public class TrainNotificationController {

    private String prefix ="portal/notification";
    @Autowired
    private TrainNotificationService trainNotificationService;
    @Autowired
    private TrainNotificationUserService trainNotificationUserService;

    @RequestMapping("/notificationDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        TrainNotification trainNotification = trainNotificationService.selectOneById(id);
        modelMap.put("trainNotification" , trainNotification);
        /*
            更改状态
         */
        trainNotificationUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/notificationDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreNotification")
    public String moreFile() {
        return prefix+"/moreNotification";
    }

    @GetMapping("/notificationList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainNotificationUser> trainNotificationUsers = trainNotificationUserService.queryMoreNotificationId(query);
        int total = trainNotificationUserService.countQueryMoreNotificationByUserId(query);
        PageUtils pageUtil = new PageUtils(trainNotificationUsers, total);
        return pageUtil;
    }


}
