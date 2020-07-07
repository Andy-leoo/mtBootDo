package com.bootdo.train.controller.backend;

import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.service.MenuService;
import com.bootdo.train.commons.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/train/menu")
@Controller
public class TrainMenuController  {
	String prefix = "/system/menu";
	@Autowired
	private MenuService menuService;

	@GetMapping()
	public String menu(Model model) {
		return prefix+"/menu";
	}

	@RequestMapping("/list")
	@ResponseBody
	public List<MenuDO> list(@RequestParam Map<String, Object> params) {
		List<MenuDO> menus = menuService.list(params);
		return menus;
	}

	@GetMapping("/add/{pId}")
	public String add(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		MenuDO mdo = menuService.get(id);
		Long pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		model.addAttribute("menu", mdo);
		return prefix+"/edit";
	}

	@PostMapping("/save")
	@ResponseBody
	public R save(MenuDO menu) {
		//todo
//		if (Const.DEMO_ACCOUNT.equals("test")) {
//			return ServerResponse.createByErrorCodeMessage(1, "演示系统不允许修改,完整体验请部署程序");
//		}
		if (menuService.save(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@PostMapping("/update")
	@ResponseBody
	public R update(MenuDO menu) {
		//todo
//		if (Const.DEMO_ACCOUNT.equals(getUsername())) {
//			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
//		}
		if (menuService.update(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败");
		}
	}

	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id) {
		//todo
//		if (Const.DEMO_ACCOUNT.equals(getUsername())) {
//			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
//		}
		if (menuService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<MenuDO> tree() {
		Tree<MenuDO>  tree = menuService.getTree();
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	public Tree<MenuDO> tree(@PathVariable("roleId") Long roleId) {
		Tree<MenuDO> tree = menuService.getTree(roleId);
		return tree;
	}
	@GetMapping("/trainTree")
	@ResponseBody
	public List<Tree<MenuDO>> trainTree() {
		Map<String,Object> map = new HashMap<>();
		map.put("parentId", Const.TRAIN_NUM);
		List<Tree<MenuDO>> tree = menuService.getTree(map);
		return tree;
	}

}
