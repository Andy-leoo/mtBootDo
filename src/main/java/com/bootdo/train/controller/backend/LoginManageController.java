package com.bootdo.train.controller.backend;

import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.MenuService;
import com.bootdo.system.service.UserService;
import com.bootdo.train.commons.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/backend")
public class LoginManageController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;


    private static final Logger log = LoggerFactory.getLogger(LoginManageController.class);


    @RequestMapping("")
    public String login(HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (user != null){
            return "redirect:/backend/index";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R ajaxLogin(String username , String password, HttpSession session) {
        log.info("进行登入校验");
        return userService.loginUser(username,password,session);
    }
    // 主页。
    @RequestMapping("/index" )
    public String index(Model model, HttpSession session) {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
//        String username = user.getUsername();
//        boolean isAdmin = false;
        if (user == null){
            return "redirect:/backend";
        }
//        if ("admin".equals(username)){
//           isAdmin = true;
//        }
        //TODO 这里需要加上 判断是否事管理员 判断，不是 就返回错误   没有权限。只能管理员登入。
        List<Tree<MenuDO>> menus = menuService.listMenuTree(user.getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", user.getName());
        //头像
        FileDO fileDO = fileService.get(user.getPicId());
        if(fileDO!=null&&fileDO.getUrl()!=null){
            if(fileService.isExist(fileDO.getUrl())){
                model.addAttribute("picUrl",fileDO.getUrl());
            }else {
                model.addAttribute("picUrl","../static/img/train/logo.jpg");
            }
        }else {
            model.addAttribute("picUrl","../static/img/train/logo.jpg");
        }
        model.addAttribute("username", user.getUsername());
//        model.addAttribute("isAdmin",isAdmin);
        return "index";
    }

    //登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        //2. 再将session进行注销
        session.invalidate();
        return "redirect:/backend";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

}
