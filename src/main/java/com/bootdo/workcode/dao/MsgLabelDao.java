package com.bootdo.workcode.dao;

import com.bootdo.system.domain.UserDO;
import com.bootdo.workcode.bean.MsgLabel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 398005446@qq.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface MsgLabelDao {

	List<MsgLabel> list();

}
