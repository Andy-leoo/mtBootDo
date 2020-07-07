package com.bootdo.train.controller.backend;

import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserDO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.ScrollGraph;
import com.bootdo.train.service.RollImgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/roll")
public class RollImgManageController {

    private String prefix ="manage/roll";
    @Autowired
    private RollImgService rollImgService;
    @Autowired
    private FileService fileService;


    @RequestMapping()
    public String index(){
        return prefix+"/images";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<ScrollGraph> list(){
        List<ScrollGraph> scrollGraphs = rollImgService.selectAll();
        return scrollGraphs;
    }


    @RequestMapping("/add")
    public String add(){
        return prefix+"/add";
    }



    @RequestMapping("/save")
//    @ResponseBody
    public R save(@RequestParam("file") MultipartFile file, ScrollGraph params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        R r = fileService.uploadFile(file);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空");
        }
        String url = (String) r.get("url");
        params.setMainImage(url);
        //保存
        if (rollImgService.save(params,user)>0) {
            return  R.ok();
        }
        return R.error();
    }

    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        if (rollImgService.remove(id)>0) {
            return R.ok();
        }
        return R.error();
    }

}
