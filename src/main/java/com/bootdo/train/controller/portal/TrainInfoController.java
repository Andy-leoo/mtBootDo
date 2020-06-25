package com.bootdo.train.controller.portal;


import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.TrainInfoService;
import com.bootdo.train.service.TrainInfoUserService;
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
@RequestMapping("/train/info")
public class TrainInfoController {

    private String prefix ="portal/info";
    @Autowired
    private TrainInfoUserService trainInfoUserService;
    @Autowired
    private TrainInfoService trainInfoService;

    @RequestMapping("/infoDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        TrainInfo trainInfo = trainInfoService.selectById(id);
        modelMap.put("trainInfo" , trainInfo);
        /*
            更改状态
         */
        trainInfoUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/infoDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreInfo")
    public String moreFile() {
        return prefix+"/moreInfo";
    }

    @GetMapping("/infoList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainInfoUser> trainInfoUsers = trainInfoUserService.queryMoreInfoByUserId(query);
        int total = trainInfoUserService.countQueryMoreInfoByUserId(query);
        PageUtils pageUtil = new PageUtils(trainInfoUsers, total);
        return pageUtil;
    }
}
