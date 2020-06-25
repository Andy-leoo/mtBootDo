package com.bootdo.train.controller.portal;

import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.TrainNews;
import com.bootdo.train.pojo.TrainNewsUser;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainNewsService;
import com.bootdo.train.service.TrainNewsUserService;
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
@RequestMapping("/train/news")
public class TrainNewsController {

    private String prefix ="portal/news";
    @Autowired
    private TrainNewsService trainNewsService;
    @Autowired
    private TrainNewsUserService trainNewsUserService;

    @RequestMapping("/newsDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        TrainNews trainNews = trainNewsService.selectOneById(id);
        modelMap.put("trainNew" , trainNews);
        /*
            更改状态
         */
        trainNewsUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/newsDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreNews")
    public String moreFile() {
        return prefix+"/moreNews";
    }

    @GetMapping("/newsList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainNewsUser> trainFilesUsers = trainNewsUserService.queryMoreNewsByUserId(query);
        int total = trainNewsUserService.countQueryMoreNewsByUserId(query);
        PageUtils pageUtil = new PageUtils(trainFilesUsers, total);
        return pageUtil;
    }


}
