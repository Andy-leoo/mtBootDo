package com.bootdo.train.controller.backend;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserDO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.Links;
import com.bootdo.train.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/links")
public class LinksManageController {

    private String perfix = "manage/links";
    @Autowired
    private LinksService linksService;

    /**
     * 展示页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return perfix+"/links";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<Links> links = linksService.selectByPage(query);
        int total = linksService.count(query);
        PageUtils pageUtil = new PageUtils(links, total);
        return pageUtil;
    }

    /**
     * 跳转至 添加页
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return perfix+"/add";
    }

    /**
     * 跳转至 修改页
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(ModelMap modelMap, @PathVariable("id") int id){
        Links links = linksService.selectOne(Long.valueOf(id));
        modelMap.addAttribute("links",links);
        return perfix+"/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public R save(Links links, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        links.setCreateTime(new Date());
        links.setCreater(user.getName());
        if (linksService.save(links)>0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/update")
    @ResponseBody()
    public R update(Links links) {
        if (linksService.update(links) > 0) {
            return R.ok();
        } else {
            return R.error(1, "修改失败");
        }
    }
    @RequestMapping("/remove")
    @ResponseBody
    public R remove(Long id){
        if (linksService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }



}
