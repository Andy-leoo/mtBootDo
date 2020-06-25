package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.TrainInfoService;
import com.bootdo.train.service.TrainInfoUserService;
import com.bootdo.train.service.UserService;
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

@Controller
@RequestMapping("/train/info")
public class TrainInfoManageController {

    private String prefix ="train/info";
    @Autowired
    private TrainInfoService trainInfoService;
    @Autowired
    private TrainInfoUserService trainInfoUserService;
    @Autowired
    private UserService userService;


    @RequestMapping()
    public String index(){
        return prefix+"/info";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<TrainInfo> trainInfos = trainInfoService.selectAll(query);
        for (TrainInfo trainInfo: trainInfos) {
            String splitDetail = RegEx_util.splitAndFilterString(trainInfo.getDetail(), 15);
            trainInfo.setDetail(splitDetail);
        }
        int total = trainInfoService.count(query);
        PageUtils pageUtil = new PageUtils(trainInfos, total);
        return pageUtil;
    }


    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        return prefix+"/add";
    }



    @RequestMapping("/save")
    @ResponseBody
    public R save(TrainInfo params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空");
        }
        if (trainInfoService.save(params,user)>0) {
            return  R.ok("新增成功");
        }
        return R.error();
    }

    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        if (trainInfoService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }


    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, ModelMap modelMap){
        TrainInfo trainInfo = trainInfoService.selectById(id);
        modelMap.put("trainInfo",trainInfo);
        List<TrainInfoUser> trainInfoUsers = trainInfoUserService.selectByInfoId(Long.valueOf(id));
        modelMap.put("infoUsers",trainInfoUsers);
        return prefix+"/detail";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")int id, ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        TrainInfo trainInfo = trainInfoService.selectById(id);
        modelMap.put("trainInfo",trainInfo);
        List<TrainInfoUser> trainInfoUsers = trainInfoUserService.selectByInfoId(Long.valueOf(id));
        StringBuffer userIds = new StringBuffer();
        for (TrainInfoUser trainInfoUser :trainInfoUsers){
            userIds.append(trainInfoUser.getUserId()).append(",");
        }
        modelMap.put("userIds",userIds.toString());
        return prefix+"/edit";
    }


    @RequestMapping("/update")
    @ResponseBody
    public R update(TrainInfo params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        if (trainInfoService.update(params,user)>0) {
            return  R.ok("更新成功");
        }
        return R.error();
    }


}
