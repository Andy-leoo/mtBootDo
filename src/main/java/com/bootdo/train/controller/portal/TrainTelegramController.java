package com.bootdo.train.controller.portal;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.system.domain.UserDO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.TrainTelegram;
import com.bootdo.train.pojo.TrainTelegramUser;
import com.bootdo.train.service.TrainTelegramService;
import com.bootdo.train.service.TrainTelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/train/telegram")
public class TrainTelegramController {

    private String prefix ="portal/telegram";
    @Autowired
    private TrainTelegramService trainTelegramService;
    @Autowired
    private TrainTelegramUserService trainTelegramUserService;

    @RequestMapping("/telegramDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        TrainTelegram trainTelegram = trainTelegramService.selectOneById(id);
        modelMap.put("trainTelegram" , trainTelegram);
        /*
            更改状态
         */
        trainTelegramUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/telegramDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreTelegram")
    public String moreFile() {
        return prefix+"/moreTelegram";
    }

    @GetMapping("/telegramList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainTelegramUser> trainTelegramUsers = trainTelegramUserService.queryMoreTelegramByUserId(query);
        int total = trainTelegramUserService.countQueryMoreTelegramByUserId(query);
        PageUtils pageUtil = new PageUtils(trainTelegramUsers, total);
        return pageUtil;
    }


}
