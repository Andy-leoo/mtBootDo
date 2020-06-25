package com.bootdo.train.controller.portal;

import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.TrainEwarningService;
import com.bootdo.train.service.TrainEwarningUserService;
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
@RequestMapping("/train/ewarning")
public class TrainEwarningController {

    private String prefix ="portal/ewarning";
    @Autowired
    private TrainEwarningService trainEwarningService;
    @Autowired
    private TrainEwarningUserService trainEwarningUserService;

    @RequestMapping("/ewarningDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        TrainEwarning trainEwarning = trainEwarningService.selectOneById(id);
        modelMap.put("trainEwarning" , trainEwarning);
        /*
            更改状态
         */
        trainEwarningUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/ewarningDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreEwarning")
    public String moreFile() {
        return prefix+"/moreEwarning";
    }

    @GetMapping("/ewarningList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainEwarningUser> trainEwarningUsers = trainEwarningUserService.queryMoreEwarningByUserId(query);
        int total = trainEwarningUserService.countQueryMoreEwarningByUserId(query);
        PageUtils pageUtil = new PageUtils(trainEwarningUsers, total);
        return pageUtil;
    }


}
