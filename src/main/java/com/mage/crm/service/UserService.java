package com.mage.crm.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.UserDao;
import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.dto.UserDto;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.Md5Util;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private HttpSession session;


    public UserModel userLogin(String userName, String userPwd){
        UserModel userModel = new UserModel();
        //userDao中查询到用户信息
        User user = userDao.queryUserByName(userName);
        //如果查到的用户信息为空，提示用户不存在
        AssertUtil.isTrue(null==user,"用户名不存在！");
        //判断查到的用户是否被注销,为0表示被注销了
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已被注销！");
        //如果以上都不满足，就表示用户存在，判断密码是否与加密过后的密码一致

        //对用户传过来的密码进行加密
        String pwd = Md5Util.encode(userPwd);
        //判断加密过后的密码是否与数据库中（user.getUserPwd()）获取到的一致
        AssertUtil.isTrue(!pwd.equals(user.getUserPwd()),"密码不正确！");

        //获取用户权限
        List<String> permission = userDao.queryPermissionByUserId(user.getId());
        if(null!=permission&&permission.size()>0){
            session.setAttribute("userPermission",permission);
        }

        return createUserModel(user);
    }

    private UserModel createUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        //对数据库中的id进行加密，并拿到加密过后的id值
        String id = Base64Util.enCode(user.getId());
        userModel.setUserId(id);
        return userModel;
    }

    public void updatePwd(String userId,String oldPassword,String newPassword,String confirmPassword){
        //前台传过来的数据
        AssertUtil.isTrue(StringUtils.isBlank(userId),"id不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码与老密码不能相等");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword),"两次密码输入不一致");

        //将解密过后的id传给数据库进行查找，并返回数据
        User user = userDao.queryUserById(Base64Util.deCode(userId));
        AssertUtil.isTrue(null==user,"用户被冻结，不允许修改密码");
        //对数据表格中的原始密码进行加密
        oldPassword = Md5Util.encode(oldPassword);
        //判断原始密码与数据库中的密码是否一致
        AssertUtil.isTrue(!oldPassword.equals(user.getUserPwd()),"原始密码错误");
        //对新密码进行加密
        newPassword= Md5Util.encode(newPassword);
        AssertUtil.isTrue(userDao.updatePwd(user.getId(),newPassword)<1,"操作失败");
    }

    /**
     * 查询所有用户
     * @param id
     * @return
     */
    public  User queryUserById(String id){
        return  userDao.queryUserById(id);
    }

    /**
     * 查询所有的客户经理
     * @return
     */
    public List<User> queryAllCustomerManager(){
        List<User> users = userDao.queryAllCustomerManager();
        return users;
    }

    public Map<String,Object> queryUsersByParams(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(),userQuery.getRows());
        List<UserDto> users = userDao.queryUsersByParams(userQuery);
        if (users != null && users.size() > 0) {
            for(UserDto userDto:users){
                String roleIdstr = userDto.getRoleIdsStr();
                if (roleIdstr != null) {
                    String[] roleIdArray =  roleIdstr.split(",");
                    for (int i = 0; i <roleIdArray.length ; i++) {
                        List<Integer> roleIds = userDto.getRoleIds();
                        roleIds.add(Integer.parseInt(roleIdArray[i]));
                    }
                }
            }
        }
        PageInfo<UserDto> userPageInfo = new PageInfo<>(users);
        Map<String, Object> map = new HashMap<>();
        map.put("total",userPageInfo.getTotal());
        map.put("rows",userPageInfo.getList());
        return map;
    }


    public void insert(User user) {
        checkParams(user.getUserName(),user.getTrueName(),user.getEmail(),user.getPhone(),user.getRoleIds());
        User user1 = userDao.queryUserByName(user.getUserName());
        AssertUtil.isTrue(user1!=null,"用户已存在");
        user.setUserPwd(Md5Util.encode("123456"));
        user.setIsValid(1);
        user.setUpdateDate(new Date());
        user.setCreateDate(new Date());
        AssertUtil.isTrue(userDao.insert(user)<1,"插入失败");
        String userId = user.getId();
        List<Integer> roleid = user.getRoleIds();
        if(roleid!=null&&roleid.size()>0){//有角色，创建级联
            List<UserRole> userRoles = new ArrayList<>();
            for(Integer roleId:roleid){
                UserRole role = new UserRole();
                role.setCreateDate(new Date());
                role.setIsValid(1);
                role.setUpdateDate(new Date());
                role.setUserId(Integer.parseInt(userId));
                role.setRoleId(roleId);
                userRoles.add(role);
            }
            AssertUtil.isTrue(userDao.insertBatch(userRoles)<userRoles.size(),"用户角色添加失败");
        }
    }
    private void checkParams(String userName, String trueName, String email, String phone, List<Integer> roleIds) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName),"真实名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"电话号码不能为空");
        AssertUtil.isTrue(roleIds==null,"角色不能为空");
    }

    public void update(User user) {
        checkParams(user.getUserName(),user.getTrueName(),user.getEmail(),user.getPhone(),user.getRoleIds());
        User user1 = userDao.queryUserByName(user.getUserName());
        //数据库中查到的用户名不为空，并且前台传过来的id和数据库中的id不相等，（不是同一个用户）
        AssertUtil.isTrue(user1!=null&&!user.getId().equals(user1.getId()),"用户已存在");
        int update = userDao.update(user);
        AssertUtil.isTrue(update<1,"修改失败");
        //修改成功，创建级联，一个用户多个角色，用List将角色封装
        String userId = user.getId();
        userRoleDao.deleteUserRolesByUserId(Integer.parseInt(userId));
        List<Integer> roleid = user.getRoleIds();
        List<UserRole> userRoleList = new ArrayList<>();
        if(roleid!=null&&roleid.size()>0){
            //对获取到的角色遍历
            for(Integer roleId:roleid){
                UserRole userRole = new UserRole();
                userRole.setIsValid(1);
                userRole.setUpdateDate(new Date());
                userRole.setUserId(Integer.parseInt(userId));
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);

            }
            AssertUtil.isTrue(userDao.insertBatch(userRoleList)<userRoleList.size(),"用户角色添加失败");
        }

    }

    public void delete(Integer id) {
        //删除用户表中的数据
        AssertUtil.isTrue(userDao.delete(id)<1,"用户删除失败");
        //获取角色表中的id，在根据id删除角色信息
        int i = userRoleDao.queryUserRoleCountsByUserId(id);
        //说明用户对于的角色存在，根据id去删除这个用户角色
        if(i>0){
            AssertUtil.isTrue(userRoleDao.deleteUserRolesByUserId(id)<1,"删除用户角色失败");

        }


    }
}
