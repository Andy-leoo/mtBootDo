package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.*;
import com.bootdo.train.service.DeptModuleService;
import com.bootdo.train.service.MenuService;
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
@RequestMapping("/manage/module")
public class ModuleManageController {

    private String prefix = "/manage/module";

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DeptModuleService deptModuleService;
    /*
        列表页
     */
    @RequestMapping("")
    public String module(){
        return prefix+"/module";
    }

    /*
        列表数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<Module> deptModules = moduleService.list(query);
        int total = moduleService.count(query);
        PageUtils pageUtil = new PageUtils(deptModules, total);
        return pageUtil;
    }

    /*
        跳转增加页面
     */
    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId", Const.TRAIN_NUM);
        List<Tree<MenuDO>> tree = menuService.getTree(map);
        modelMap.put("menus",tree);
        return prefix+"/add";
    }

    /*
        执行增加操作
     */
    @RequestMapping("/save")
    @ResponseBody()
    public R save(Module module, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (moduleService.save(module,user) > 0) {
            return R.ok();
        } else {
            return R.error(1, "保存失败");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap model) {
        Module module = moduleService.queryById(id);
        model.put("module",module);
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody()
    public R update(Module module, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (moduleService.update(module,user) > 0) {
            return R.ok();
        } else {
            return R.error(1, "修改失败");
        }
    }

    @PostMapping("/remove")
    @ResponseBody()
    public R save(Long id) {
        DeptModule module = deptModuleService.queryByModuleId(id);
        if (module != null){
            R.error(1,"请先剔除部门模块关系！");
        }
        if (moduleService.remove(id) > 0) {
            return R.ok();
        } else {
            return R.error(1, "删除失败");
        }
    }

}
