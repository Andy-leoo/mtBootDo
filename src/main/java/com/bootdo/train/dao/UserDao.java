package com.bootdo.train.dao;

import com.bootdo.train.pojo.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 */
@Mapper
public interface UserDao {

	UserDO get(Long userId);
	
	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

	int checkUserName(String username);

	UserDO selectLoginUser(@Param("username") String username, @Param("password") String password);

    void barchInsert(List<UserDO> userList);

	String takeOldPwd(Long userId);

	List<UserDO> selectByDeptIds(List<String> deptIds);
}
