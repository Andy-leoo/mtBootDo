package com.bootdo.train.controller.backend;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.TrainEwarning;
import com.bootdo.train.pojo.TrainEwarningUser;
import com.bootdo.train.service.TrainEwarningUserService;
import com.bootdo.train.service.impl.TrainEwarningServiceImpl;
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

//安全预警
@Controller
@RequestMapping("/train/ewarning")
public class TrainEwarningManageController {

    private String prefix ="train/ewarning";
    @Autowired
    private TrainEwarningServiceImpl trainEwarningService;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainEwarningUserService trainEwarningUserService;


    @RequestMapping()
    public String index(){
        return prefix+"/ewarning";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<TrainEwarning> trainEwarnings = trainEwarningService.selectAll(query);
        for (TrainEwarning trainEwarning: trainEwarnings) {
            String splitDetail = RegEx_util.splitAndFilterString(trainEwarning.getDetail(), 15);
            trainEwarning.setDetail(splitDetail);
        }
        int total = trainEwarningService.count(query);
        PageUtils pageUtil = new PageUtils(trainEwarnings, total);
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
        TrainEwarning trainEwarning = trainEwarningService.selectOneById(id);
        modelMap.put("trainEwarning",trainEwarning);
        List<TrainEwarningUser> trainEwarningUsers = trainEwarningUserService.selectByEwarningId(Long.valueOf(id));
        StringBuffer userIds = new StringBuffer();
        for (TrainEwarningUser trainEwarningUser :trainEwarningUsers){
            userIds.append(trainEwarningUser.getUserId()).append(",");
        }
        modelMap.put("userIds",userIds.toString());
        return prefix+"/edit";
    }



    @RequestMapping("/saveEwarning")
    @ResponseBody
    public R saveNews(TrainEwarning params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空");
        }


        if (trainEwarningService.save(params,user)>0) {

            return  R.ok("新增成功");
        }

        return R.error();
    }

    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        if (trainEwarningService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, ModelMap modelMap){
        TrainEwarning trainEwarning = trainEwarningService.selectOneById(id);
        modelMap.put("trainEwarning",trainEwarning);
        List<TrainEwarningUser> trainEwarningUsers = trainEwarningUserService.selectByEwarningId(Long.valueOf(id));
        modelMap.put("trainEwarningUsers",trainEwarningUsers);
        return prefix+"/detail";
    }

    @RequestMapping("/update")
    @ResponseBody
    public R update(TrainEwarning params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        if (trainEwarningService.update(params,user)>0) {
            return  R.ok("更新成功");
        }
        return R.error();
    }

}
