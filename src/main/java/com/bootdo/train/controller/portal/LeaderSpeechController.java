package com.bootdo.train.controller.portal;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.system.domain.UserDO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.LeaderSpeech;
import com.bootdo.train.pojo.LeaderSpeechUser;
import com.bootdo.train.service.LeaderSpeechService;
import com.bootdo.train.service.LeaderSpeechUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/train/speech")
public class LeaderSpeechController {

    private String prefix ="portal/leaderSpeech";

    @Autowired
    private LeaderSpeechService leaderSpeechService;
    @Autowired
    private LeaderSpeechUserService leaderSpeechUserService;

    @RequestMapping("/leaderSpeechDetail/{id}")
    public String newsDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        LeaderSpeech leaderSpeech = leaderSpeechService.selectById(id);
        modelMap.put("leaderSpeech" , leaderSpeech);
        /*
            更改状态
         */
        leaderSpeechUserService.updateStatus(Long.valueOf(id),user);
        return prefix+"/leaderSpeechDetail";
    }

    /*
        获取更多新闻 news
     */
    @RequestMapping("/moreleaderSpeechs")
    public String moreFile() {
        return prefix+"/moreLeaderSpeechs";
    }

    @GetMapping("/leaderSpeechsList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<LeaderSpeechUser> leaderSpeechUsers = leaderSpeechUserService.queryMoreSpeechByUserId(query);
        int total = leaderSpeechUserService.countQueryMoreSpeechByUserId(query);
        PageUtils pageUtil = new PageUtils(leaderSpeechUsers, total);
        return pageUtil;
    }


}