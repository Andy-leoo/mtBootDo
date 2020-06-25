package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.DeptModuleService;
import com.bootdo.train.service.ModuleService;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/deptModule")
public class DeptModuleManageController {

    private String prefix = "/manage/deptModule";

    @Autowired
    private DeptModuleService deptModuleService;
    @Autowired
    private ModuleService moduleService;
    /*
        部门列表页
     */
    @RequestMapping("")
    public String module(){
        return prefix+"/deptModule";
    }

    /*
        列表数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<DeptModule> deptModules = deptModuleService.list(query);
        int total = deptModuleService.count(query);
        PageUtils pageUtil = new PageUtils(deptModules, total);
        return pageUtil;
    }

    /*
        跳转增加页面
     */
    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        List<Module> modules = moduleService.list(new HashMap<>());
        modelMap.put("modules",modules);
        return prefix+"/add";
    }

    /*
        执行增加操作
     */
    @RequestMapping("/save")
    @ResponseBody()
    public R save(DeptModule module, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        int flag = 0;
        DeptModule deptModule = deptModuleService.queryByDeptId(module.getDeptId());
        if (deptModule !=null){
            deptModuleService.update(module, user);
            flag++;
        }else {
            deptModuleService.save(module,user);
            flag++;
        }
        if ( flag > 0) {
            return R.ok();
        } else {
            return R.error(1, "保存失败");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap model) {
        DeptModule deptModule = deptModuleService.queryById(id);
        model.put("deptModule",deptModule);
        List<Module> modules = moduleService.list(new HashMap<>());
        model.put("modules",modules);
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody()
    public R update(DeptModule module, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (deptModuleService.update(module,user) > 0) {
            return R.ok();
        } else {
            return R.error(1, "修改失败");
        }
    }

    @PostMapping("/remove")
    @ResponseBody()
    public R save(Long id) {
        if (deptModuleService.remove(id) > 0) {
            return R.ok();
        } else {
            return R.error(1, "删除失败");
        }
    }



}
