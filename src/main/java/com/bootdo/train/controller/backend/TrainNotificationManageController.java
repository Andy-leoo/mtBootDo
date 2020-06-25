package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.TrainNotificationUserService;
import com.bootdo.train.service.UserService;
import com.bootdo.train.service.impl.TrainNotificationServiceImpl;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import com.bootdo.train.utils.RegEx_util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//通知通告
@Controller
@RequestMapping("/train/notification")
public class TrainNotificationManageController {

    private String prefix ="train/notification";
    @Autowired
    private TrainNotificationServiceImpl trainNotificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainNotificationUserService trainNotificationUserService;


    @RequestMapping()
    public String index(){
        return prefix+"/notification";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<TrainNotification> trainNotifications = trainNotificationService.selectAll(query);
        for (TrainNotification trainNotification: trainNotifications) {
            String splitDetail = RegEx_util.splitAndFilterString(trainNotification.getDetail(), 15);
            trainNotification.setDetail(splitDetail);
        }
        int total = trainNotificationService.count(query);
        PageUtils pageUtil = new PageUtils(trainNotifications, total);
        return pageUtil;
    }


    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        return prefix+"/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")int id, ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        TrainNotification trainNotification = trainNotificationService.selectOneById(id);
        modelMap.put("trainNotification",trainNotification);
        List<TrainNotificationUser> trainNotificationUsers = trainNotificationUserService.selectByNotificationId(Long.valueOf(id));
        StringBuffer userIds = new StringBuffer();
        for (TrainNotificationUser trainNotificationUser :trainNotificationUsers){
            userIds.append(trainNotificationUser.getUserId()).append(",");
        }
        modelMap.put("userIds",userIds.toString());
        return prefix+"/edit";
    }



    @RequestMapping("/saveNotification")
    @ResponseBody
    public R saveNews(TrainNotification params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空");
        }


        if (trainNotificationService.save(params,user)>0) {

            return  R.ok("新增成功");
        }

        return R.error();
    }

    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        if (trainNotificationService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, ModelMap modelMap){
        TrainNotification trainNotification = trainNotificationService.selectOneById(id);
        modelMap.put("trainNotification",trainNotification);
        List<TrainNotificationUser> trainNotificationUsers = trainNotificationUserService.selectByNotificationId(Long.valueOf(id));
        modelMap.put("trainNotificationUsers",trainNotificationUsers);
        return prefix+"/detail";
    }

    @RequestMapping("/update")
    @ResponseBody
    public R update(TrainNotification params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        if (trainNotificationService.update(params,user)>0) {
            return  R.ok("更新成功");
        }
        return R.error();
    }

}
