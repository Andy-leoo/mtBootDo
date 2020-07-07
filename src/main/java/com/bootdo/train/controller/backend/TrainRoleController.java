package com.bootdo.train.controller.backend;

import com.bootdo.common.utils.R;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sys/train/role")
@Controller
public class TrainRoleController  {
	String prefix = "system/role";
	@Autowired
	private RoleService roleService;

	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@GetMapping("/list")
	@ResponseBody()
	List<RoleDO> list() {
		List<RoleDO> roles = roleService.list();
		return roles;
	}

	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		RoleDO roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@PostMapping("/save")
	@ResponseBody()
	public R save(RoleDO role) {
		if (roleService.save(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@PostMapping("/update")
	@ResponseBody()
	public R update(RoleDO role) {
		if (roleService.update(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@PostMapping("/remove")
	@ResponseBody()
	public R save(Long id) {
		if (roleService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@PostMapping("/batchRemove")
	@ResponseBody
	public R batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = roleService.batchremove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
