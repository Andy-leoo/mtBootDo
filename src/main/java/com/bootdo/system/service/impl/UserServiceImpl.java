package com.bootdo.system.service.impl;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.*;
import com.bootdo.system.dao.DeptDao;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.dao.UserRoleDao;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.domain.UserRoleDO;
import com.bootdo.system.service.UserService;
import com.bootdo.system.vo.UserVO;
import com.bootdo.train.commons.Const;
import com.bootdo.train.dao.DeptModuleDao;
import com.bootdo.train.pojo.DeptModule;
import com.bootdo.train.pojo.Module;
import com.bootdo.train.service.ModuleService;
import com.bootdo.train.utils.MD5Util;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;
    @Autowired
    DeptDao deptMapper;
    @Autowired
    private FileService sysFileService;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DeptModuleDao deptModuleDao;

    @Override
//    @Cacheable(value = "user",key = "#id")
    public UserDO get(Long id) {
        List<Long> roleIds = userRoleMapper.listRoleId(id);
        UserDO user = userMapper.get(id);
        user.setDeptName(deptMapper.get(user.getDeptId()).getName());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }

    @Transactional
    @Override
    public int save(UserDO user) {
        user.setRole(checkRoleAdmin(user));
        int count = userMapper.save(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return count;
    }

    @Override
    public int update(UserDO user) {
        user.setRole(checkRoleAdmin(user));
        int r = userMapper.update(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return r;
    }
    private int checkRoleAdmin(UserDO userDO){
        String roleName = userRoleMapper.checkRoleAdmin(userDO.getUserId());
        if (StringUtils.isNotBlank(roleName)){
            if (roleName.contains(Const.CHECK_ROLE_ADMIN)) {
                return Const.Role.ROLE_ADMIN;
            }
        }
        return Const.Role.ROLE_CUSTOMER;
    }
//    @CacheEvict(value = "user")
    @Override
    public int remove(Long userId) {
        userRoleMapper.removeByUserId(userId);
        return userMapper.remove(userId);
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        boolean exit;
        exit = userMapper.list(params).size() > 0;
        return exit;
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserVO userVO, UserDO userDO) throws Exception {
        if (Objects.equals(userVO.getUserDO().getUserId(), userDO.getUserId())) {
            if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
                return userMapper.update(userDO);
            } else {
                throw new Exception("输入的旧密码有误！");
            }
        } else {
            throw new Exception("你修改的不是你登录的账号！");
        }
    }

    @Override
    public R resetPwd(UserVO userVO, UserDO userDO, String oldPwd){
        if (Objects.equals(userVO.getUserDO().getUserId(), userDO.getUserId())) {
            if (Objects.equals(MD5Util.MD5EncodeUtf8(userVO.getPwdOld()), oldPwd)) {
                if (!Objects.equals(MD5Util.MD5EncodeUtf8(userVO.getPwdNew()), oldPwd)) {
                    userDO.setPassword(MD5Util.MD5EncodeUtf8(userVO.getPwdNew()));
                    userMapper.update(userDO);
                    return R.ok("更新密码成功");
                }else {
                    return R.error("输入的新密码与旧密码相同！");
                }
            } else {
                return R.error("输入的旧密码有误！");
            }
        } else {
            return R.error("你修改的不是你登录的账号！");
        }
    }

    @Override
    public int adminResetPwd(UserVO userVO) throws Exception {
        UserDO userDO = get(userVO.getUserDO().getUserId());
        if ("admin".equals(userDO.getUsername())) {
            throw new Exception("超级管理员的账号不允许直接重置！");
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return userMapper.update(userDO);


    }

    @Transactional
    @Override
    public int batchremove(Long[] userIds) {
        int count = userMapper.batchRemove(userIds);
        userRoleMapper.batchRemoveByUserId(userIds);
        return count;
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
        Long[] pDepts = deptMapper.listParentDept();
        Long[] uDepts = userMapper.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getUserId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public int updatePersonal(UserDO userDO) {
        return userMapper.update(userDO);
    }

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
        //获取图片后缀
        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
        String[] str = avatar_data.split(",");
        //获取截取的x坐标
        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
        //获取截取的y坐标
        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
        //获取截取的高度
        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
        //获取截取的宽度
        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
        //获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(rotateImage, prefix, out);
            //转换后存入数据库
            byte[] b = out.toByteArray();
            FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
        } catch (Exception e) {
            throw new Exception("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        if (sysFileService.save(sysFile) > 0) {
            UserDO userDO = new UserDO();
            userDO.setUserId(userId);
            userDO.setPicId(sysFile.getId());
            if (userMapper.update(userDO) > 0) {
                result.put("url", sysFile.getUrl());
            }
        }
        return result;
    }

    @Override
    public R loginUser(String username, String password, HttpSession session) {
        if (StringUtils.isBlank(username)){
            return R.error("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)){
            return R.error("密码不能为空！");
        }
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0 ){
            return R.error("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        UserDO user = userMapper.selectLoginUser(username,md5Password);
        if (user == null){
            return R.error("密码错误");
        }
        //滞空密码
        user.setPassword(StringUtils.EMPTY);
        //放入session
        session.setAttribute(Const.CURRENT_USER ,user);
        return R.ok();
    }

    @Transactional
    @Override
    public void batchInsert(List<UserDO> userList) {
        userMapper.barchInsert(userList);
    }

    @Override
    public String takeOldPwd(Long userId) {
        return userMapper.takeOldPwd(userId);
    }

    /*
        根据添加的 模块 id 进行查询人员
     */
    public List<UserDO> selectUserByMenuId(Long menuId){
        /*
            根据 菜单 获取 有哪些模块包含
         */
        List<Module> modulelist = moduleService.selectModulesByMenuId(menuId);
        if (modulelist.size()==0){
            return null;
        }
        List<String> moduleIds = new ArrayList<>();
        /*
            获取每个模块的 moduleID
         */
        for (Module module: modulelist) {
            Long id = module.getId();
            moduleIds.add(id.toString());
        }
        List<DeptModule> deptModules = deptModuleDao.selectByModuleIds(moduleIds);
        if (deptModules.size()==0){
            return null;
        }
        List<String> deptIds = new ArrayList<>();
        /*
            根据moduleId 获取 deptId
         */
        for (DeptModule deptModule: deptModules) {
            Long deptId = deptModule.getDeptId();
            deptIds.add(deptId.toString());
        }
        /*
            userService 下  根据deptId获取所有user
         */
        List<UserDO> list = userMapper.selectByDeptIds(deptIds);
        return list;
    }

    public static void main(String[] args) {
//        selectUserByMenuId();
    }

}
