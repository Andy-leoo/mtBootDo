package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.LeaderSpeechService;
import com.bootdo.train.service.LeaderSpeechUserService;
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
@RequestMapping("/manage/leader")
public class LeaderSpeechManageController {

    private String prefix ="manage/leader";
    @Autowired
    private LeaderSpeechService LeaderSpeechService;
    @Autowired
    private UserService userService;
    @Autowired
    private LeaderSpeechUserService leaderSpeechUserService;


    @RequestMapping()
    public String index(){
        return prefix+"/leader";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<LeaderSpeech> LeaderSpeechs = LeaderSpeechService.selectAll(query);
        for (LeaderSpeech leaderSpeech: LeaderSpeechs) {
            String splitDetail = RegEx_util.splitAndFilterString(leaderSpeech.getDetail(), 15);
            leaderSpeech.setDetail(splitDetail);
        }
        int total = LeaderSpeechService.count(query);
        PageUtils pageUtil = new PageUtils(LeaderSpeechs, total);
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
    public R save(LeaderSpeech params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空");
        }

        if (LeaderSpeechService.save(params,user)>0) {

            return  R.ok("新增成功");
        }

        return R.error();
    }

    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        if (LeaderSpeechService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }


    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, ModelMap modelMap){
        LeaderSpeech leaderSpeech = LeaderSpeechService.selectById(id);
        modelMap.put("leaderSpeech",leaderSpeech);
        List<LeaderSpeechUser> leaderSpeechUsers = leaderSpeechUserService.selectByLeaderId(Long.valueOf(id));
        modelMap.put("leaderSpeechUsers",leaderSpeechUsers);
        return prefix+"/detail";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")int id, ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        LeaderSpeech leaderSpeech = LeaderSpeechService.selectById(id);
        modelMap.put("leaderSpeech",leaderSpeech);
        List<LeaderSpeechUser> leaderSpeechUsers = leaderSpeechUserService.selectByLeaderId(Long.valueOf(id));
        StringBuffer userIds = new StringBuffer();
        for (LeaderSpeechUser leaderSpeechUser :leaderSpeechUsers){
            userIds.append(leaderSpeechUser.getUserId()).append(",");
        }
        modelMap.put("userIds",userIds.toString());
        return prefix+"/edit";
    }


    @RequestMapping("/update")
    @ResponseBody
    public R update(LeaderSpeech params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        if (LeaderSpeechService.update(params,user)>0) {
            return  R.ok("更新成功");
        }
        return R.error();
    }

}
