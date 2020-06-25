package com.bootdo.train.service;

import com.bootdo.train.pojo.LogDO;
import com.bootdo.train.pojo.PageDO;
import com.bootdo.train.utils.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
