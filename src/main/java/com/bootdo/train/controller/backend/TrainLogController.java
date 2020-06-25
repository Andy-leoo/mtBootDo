package com.bootdo.train.controller.backend;

import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.LogDO;
import com.bootdo.train.pojo.PageDO;
import com.bootdo.train.service.LogService;
import com.bootdo.train.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/manage/train/log")
@Controller
public class TrainLogController {
	@Autowired
	private LogService logService;
	private String prefix = "/manage/log";

	@GetMapping()
	public String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageDO<LogDO> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageDO<LogDO> page = logService.queryList(query);
		return page;
	}
	
	@ResponseBody
	@PostMapping("/remove")
	public R remove(Long id) {
		if (logService.remove(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/batchRemove")
	public R batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchRemove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
