package com.bootdo.train.controller.portal;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.TrainFiles;
import com.bootdo.train.pojo.TrainFilesUser;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainFilesService;
import com.bootdo.train.service.TrainFilesUserService;
import com.bootdo.train.utils.FileToHtmlUtil;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/train/files")
public class TrainFilesController {

    private String prefix ="portal/files";

    @Autowired
    private TrainFilesService trainFilesService;
    @Autowired
    private TrainFilesUserService trainFilesUserService;
    @Autowired
    private BootdoConfig bootdoConfig;

    /*
        文件详情
     */
    @RequestMapping("/fileDetail/{id}")
    public String fileDetail(@PathVariable("id")int id , ModelMap modelMap, HttpSession session) throws Exception {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        int count = trainFilesUserService.checkFileByFileIdAndUserId(id,user.getUserId());
        if (count < 0){
            throw new Exception("未找到相对应文件，请重新登入后查询！");
        }
        TrainFiles trainFiles = trainFilesService.selectOneById(id);
        modelMap.put("trainFile" , trainFiles);
        /*
            去更改状态
         */
        trainFilesUserService.updateStatus(Long.valueOf(id),user, Const.FileStatus.FILE_HAVE_CHECKED);
        return prefix+"/fileDetail";
    }

    /*
        获取更多文件files
     */
    @RequestMapping("/moreFile")
    public String moreFile()  {
        return prefix+"/moreFiles";
    }

    @GetMapping("/fileList")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        params.put("userId",user.getUserId());
        // 查询列表数据
        Query query = new Query(params);
        List<TrainFilesUser> trainFilesUsers = trainFilesUserService.queryMoreFileByUserId(query);
        int total = trainFilesUserService.countQueryMoreFileByUserId(query);
        PageUtils pageUtil = new PageUtils(trainFilesUsers, total);
        return pageUtil;
    }

    @Transactional
    @RequestMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable("fileId") String fileId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        /*
            更改状态
         */
        trainFilesUserService.updateStatus(Long.valueOf(fileId),user, Const.FileStatus.FILE_HAVE_CHECKED);
        int count = trainFilesUserService.checkFileByFileIdAndUserId(Integer.valueOf(fileId), user.getUserId());
        if (count<0){
            throw new Exception("未找到文件，请刷新！");
        }
        TrainFiles trainFiles = trainFilesService.selectOneById(Integer.valueOf(fileId));
        String mainImage = trainFiles.getMainImage();
        String fileName = mainImage.substring(mainImage.lastIndexOf("/")+1, mainImage.length());
        /*
            下载
         */
        response.setCharacterEncoding("utf-8");//设置编码统一
        response.setContentType("multipart/form-data");//设置multipart
        //响应头部
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        String path = bootdoConfig.getUploadPath()+fileName;
        InputStream inputStream = new FileInputStream(new File(path));//通过zip路径实例化inputStream流
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        os.close();
        inputStream.close();
    }

    @RequestMapping("/onlinePreview/{id}")
    public String onlinePreview(@PathVariable("id") String fileId, ModelMap modelMap){
        TrainFiles trainFiles = trainFilesService.selectOneById(Integer.valueOf(fileId));
        String mainImage = trainFiles.getMainImage();
        String suffix=mainImage.substring(mainImage.lastIndexOf(".")+1);
        String fileName = mainImage.substring(mainImage.lastIndexOf("/")+1, mainImage.length());
        try {
            if ("doc".equals(suffix) || "docx".equals(suffix)){
                String word2Html = FileToHtmlUtil.word2Html(bootdoConfig.getUploadPath() + fileName, bootdoConfig.getUploadPath(), UUID.randomUUID().toString() + ".html");
                modelMap.put("wordToHtml",word2Html);
                return prefix+"/word";
            }
            if ("xls".equals(suffix) || "xlsx".equals(suffix)){
                String excelToHtml = FileToHtmlUtil.readExcelToHtml(fileName, bootdoConfig.getUploadPath(), true);
                modelMap.put("excelToHtml",excelToHtml);
                return prefix+"/excel";
            }
            if ("ppt".equals(suffix) || "pptx".equals(suffix)){
                String pptToHtml = FileToHtmlUtil.pptToHtml(bootdoConfig.getUploadPath() , fileName);
                modelMap.put("pptToHtml",pptToHtml);
                return prefix+"/ppt";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/toSignFor/{id}")
    @ResponseBody
    public R toSignForFile(@PathVariable("id")String fileId, HttpSession session){
        UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
        //更改状态
        int count  = trainFilesUserService.updateStatus(Long.valueOf(fileId),user,Const.FileStatus.FILE_SIGN_FOR);
        if (count>0){
            return R.ok();
        }
        return R.error();
    }

}
