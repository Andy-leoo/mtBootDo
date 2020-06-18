package com.bootdo.workcode.service.impl;

import com.bootdo.common.domain.InfDO;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.FileUtil;
import com.bootdo.common.utils.JSONUtils;
import com.bootdo.workcode.bean.DataUtils;
import com.bootdo.workcode.bean.IntelligentInfDo;
import com.bootdo.workcode.dao.IntelligentInfDao;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/18 19:52 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.workcode.service.impl <br>
 */
@Service
public class IntelligentInfServiceImpl {

    @Autowired
    private IntelligentInfDao dao;

    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/18 15:18 <br>
     * @desc 根据 传入的 json 文件地址，输入 mysql.
     */
    public String readJsonFileInputDB(String path){
        //读取文件
        String jsonStr = FileUtil.readJsonFile(path);

        List<InfDO> infDOS = JSONUtils.parseArray(jsonStr, InfDO.class);
        List<IntelligentInfDo> intelligentInfDos = Lists.newArrayList();
        for (InfDO infDO : infDOS){
            IntelligentInfDo intelligentInfDo = new IntelligentInfDo();

            intelligentInfDo.setInfId(infDO.getId());
            intelligentInfDo.setAuthor(infDO.getAuthor());
            intelligentInfDo.setCcbContent(infDO.getCcb_content());
            intelligentInfDo.setContent(infDO.getContent());
            intelligentInfDo.setPublishAt(DateUtils.format(infDO.getPublish_at()));
            intelligentInfDo.setSummary(infDO.getSummary());
            intelligentInfDo.setThumbnailType(infDO.getThumbnail_type());
            intelligentInfDo.setTitle(infDO.getTitle());

            intelligentInfDos.add(intelligentInfDo);
        }

        int i = dao.batchSave(intelligentInfDos);

        return "--"+i;
    }

}
