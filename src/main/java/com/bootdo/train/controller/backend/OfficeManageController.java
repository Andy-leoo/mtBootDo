package com.bootdo.train.controller.backend;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserDO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.Office;
import com.bootdo.train.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/off")
public class OfficeManageController {

    private String perfix = "manage/off";
    @Autowired
    private OfficeService officeService;

    /**
     * 展示页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return perfix+"/office";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<Office> offices = officeService.selectByPage(query);
        int total = officeService.count(query);
        PageUtils pageUtil = new PageUtils(offices, total);
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
        Office office = officeService.selectOne(Long.valueOf(id));
        modelMap.addAttribute("office",office);
        return perfix+"/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public R save(Office office, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        office.setCreateTime(new Date());
        office.setCreater(user.getUsername());
        if (officeService.save(office)>0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/update")
    @ResponseBody()
    public R update(Office office) {
        if (officeService.update(office) > 0) {
            return R.ok();
        } else {
            return R.error(1, "修改失败");
        }
    }
    @RequestMapping("/remove")
    @ResponseBody
    public R remove(Long id){
        if (officeService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }



}
