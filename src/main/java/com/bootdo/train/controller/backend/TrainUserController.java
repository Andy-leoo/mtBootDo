package com.bootdo.train.controller.backend;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.train.vo.UserVO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.commons.R;
import com.bootdo.train.pojo.DeptDO;
import com.bootdo.train.pojo.RoleDO;
import com.bootdo.train.pojo.Tree;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.*;
import com.bootdo.train.utils.ExcelUtil;
import com.bootdo.train.utils.MD5Util;
import com.bootdo.train.utils.PageUtils;
import com.bootdo.train.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/sys/train/user")
public class TrainUserController {

	private String prefix="/system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictService dictService;
	@Autowired
	FileService fileService;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private ExportService exportService;

	@GetMapping("")
	public String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	public String add(Model model) {
		List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<RoleDO> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@PostMapping("/save")
	@ResponseBody
	public R save(UserDO user) {
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@PostMapping("/update")
	@ResponseBody
	public R update(UserDO user) {
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@PostMapping("/updatePeronal")
	@ResponseBody
	public R updatePeronal(UserDO user) {
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id) {
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/batchRemove")
	@ResponseBody
	public R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	public boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@GetMapping("/resetPwd/{id}")
	public String resetPwd(@PathVariable("id") Long userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@PostMapping("/resetPwd")
	@ResponseBody
	public R resetPwd(UserVO userVO, HttpSession session) {
		try{
			UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
			if (user == null){
				return R.error(1,"请重新登入");
			}
			//根据当前登入用户 去取库中旧密码
			String oldPwd = userService.takeOldPwd(user.getUserId());
			userService.resetPwd(userVO,user,oldPwd);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}
	}
	@PostMapping("/adminResetPwd")
	@ResponseBody
	public R adminResetPwd(UserVO userVO, HttpSession session) {
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}

	@PostMapping("/resPwd")
	public String resPwd() {
		return prefix + "/resPwd";
	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	public String treeView() {
		return  prefix + "/userTree";
	}

	@GetMapping("/personal")
	public String personal(Model model, HttpSession session) {
		UserDO user = (UserDO) session.getAttribute(Const.CURRENT_USER);
		UserDO userDO  = userService.get(user.getUserId());
		model.addAttribute("user",userDO);
		model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
	public R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			UserDO user = (UserDO) request.getSession().getAttribute(Const.CURRENT_USER);
			result = userService.updatePersonalImg(file, avatar_data, user.getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}

	//导入用户页面
	@RequestMapping("/import")
	public String importExcel(){
		return prefix+"/importEx";
	}

	/**
	 * 用户导入！
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/importUpload")
	@ResponseBody
	public R uploadExcel(HttpServletRequest request) throws Exception {
		String uploadPath = bootdoConfig.getUploadPath();
		//1.上传文件，得到文件名称
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("fileName");
		R r = fileService.uploadFile(file);
		File excelFile = new File(r.get("url").toString());
		//2.对文件进行操作，每行数据存一个user,所有user存到list中
		List<UserDO> userList = ExcelUtil.readXls(excelFile,uploadPath);
		//3.批量插入表中
		userService.batchInsert(userList);
		return R.ok();
	}

	@RequestMapping(value = "/exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		//excel标题
		String[] title = {"用户名","手机号","性别"};

		//excel文件名
		String fileName = "用户信息表"+System.currentTimeMillis()+".xls";

		List<UserDO> list = userService.list(new HashMap<>());
		try {
			exportService.exportUserExcel(request,response,list,fileName,title);
		} catch (UnsupportedEncodingException e) {
			System.out.println("导出用户信息出错");
		}
	}

}
