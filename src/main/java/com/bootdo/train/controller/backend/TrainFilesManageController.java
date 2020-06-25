package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.TrainFiles;
import com.bootdo.train.pojo.TrainFilesUser;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.FileService;
import com.bootdo.train.service.TrainFilesService;
import com.bootdo.train.service.TrainFilesUserService;
import com.bootdo.train.service.UserService;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/train/files")
public class TrainFilesManageController {

    private String prefix ="train/files";
    @Autowired
    private TrainFilesService trainFilesService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainFilesUserService trainFilesUserService;


    @RequestMapping()
    public String index(){
        return prefix+"/files";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String ,Object> params){
        Query query = new Query(params);
        List<TrainFiles> trainFiles = trainFilesService.selectAll(query);
        int total = trainFilesService.count(query);
        PageUtils pageUtil = new PageUtils(trainFiles, total);
        return pageUtil;
    }


    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        return prefix+"/add";
    }

    @RequestMapping("/addnew")
    public String addNew(){
        return prefix+"/addNew";
    }



    @RequestMapping("/save")
    @ResponseBody
    public R save(TrainFiles params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        //1.上传文件，得到文件名称
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        R r = fileService.uploadFile(file);
        if (StringUtils.isBlank(params.getTitle())){
            return R.error("标题不能为空！");
        }
        if (StringUtils.isBlank(params.getDetail())){
            return  R.error("内容不能为空！");
        }
        if (StringUtils.isBlank(params.getUserIds().toString())){
            return R.error("代阅读人员不能为空！");
        }
        String url = (String) r.get("url");
        params.setMainImage(url);
        //保存
        if (trainFilesService.save(params,user)>0) {
            return  R.ok("新增成功");
        }
        return R.error();
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, ModelMap modelMap){
        TrainFiles trainFiles = trainFilesService.selectOneById(id);
        modelMap.put("trainFiles",trainFiles);
        List<TrainFilesUser> trainFilesUsers = trainFilesUserService.selectByFilesId(Long.valueOf(id));
        modelMap.put("filesUsers",trainFilesUsers);
        return prefix+"/detail";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")int id, ModelMap modelMap){
        List<UserDO> list = userService.list(new HashMap<>());
        modelMap.put("userList",list);
        TrainFiles trainFiles = trainFilesService.selectOneById(id);
        modelMap.put("trainFiles",trainFiles);
        List<TrainFilesUser> trainFilesUsers = trainFilesUserService.selectByFilesId(Long.valueOf(id));
        StringBuffer userIds = new StringBuffer();
        for (TrainFilesUser trainFilesUser :trainFilesUsers){
            userIds.append(trainFilesUser.getUserId()).append(",");
        }
        modelMap.put("userIds",userIds.toString());
        return prefix+"/edit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public R update(TrainFiles params, HttpServletRequest request){
        UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
        //1.上传文件，得到文件名称
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (file!=null){
            R r = fileService.uploadFile(file);
            params.setMainImage((String) r.get("url"));
        }
        if (trainFilesService.update(params,user)>0) {
            return  R.ok("修改成功");
        }
        return R.error();
    }

    @Transactional
    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
        int count = trainFilesUserService.batchRemove(Long.valueOf(id));
        if (count > 0 ){
            if (trainFilesService.remove(id)>0) {
                return R.ok();
            }
        }
        return R.error();
    }

}
