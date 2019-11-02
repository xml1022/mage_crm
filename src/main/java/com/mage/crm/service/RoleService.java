package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.ModuleDao;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dao.RoleDao;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Module;
import com.mage.crm.vo.Permission;
import com.mage.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mage.crm.util.AssertUtil.*;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private ModuleDao moduleDao;

    public List<Role> queryAllRoles() {
        return roleDao.queryAllRoles();
    }

    public Map<String, Object> queryRolesByParams(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getRows());
        List<Role> roleList = roleDao.queryRolesByParams(roleQuery.getRoleName());
        PageInfo<Role> stringObjectPageInfo = new PageInfo<>(roleList);
        Map<String, Object> map = new HashMap<>();
        map.put("total",stringObjectPageInfo.getTotal());
        map.put("rows",stringObjectPageInfo.getList());
        return map;
    }

    public void insert(Role role) {
        //检查前台参数
        AssertUtil.isTrue(role.getRoleName()==null,"角色名称不能为空");
       //查询数据库中是否存在该角色名称
        Role role1 = roleDao.queryRoleName(role.getRoleName());
        AssertUtil.isTrue(role1!=null,"用户已存在");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        //不存在
        int insert = roleDao.insert(role);
        AssertUtil.isTrue(insert<1,"添加角色失败");

    }

    public void update(Role role) {
        //检查前台参数
        AssertUtil.isTrue(role.getRoleName()==null,"角色名称不能为空");
        //查询数据库中是否存在该角色名称
        Role role1 = roleDao.queryRoleName(role.getRoleName());
        AssertUtil.isTrue(role1!=null,"用户已存在");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.update(role)<1,"角色修改失败");
    }

    public void delete(Integer id) {
        AssertUtil.isTrue(roleDao.delete(id)<1,"删除角色失败");
    }

    //关联权限
    public void addPermission(Integer rid, Integer[] moduleIds) {
        //查询前台传过来的角色在数据库中是否存在
        AssertUtil.isTrue(null==rid || null==roleDao.queryRoleById(rid),"角色不存在");
        //查询角色所对应的模块数量
        int count = permissionDao.queryPermissionDaoByRid(rid);
        //删除原来所对应的模块
        if(count>0){
            AssertUtil.isTrue(permissionDao.deletePermissionDaoByRid(rid)<count,"删除原有权限失败，无法重新赋权限");
        }
        //批量添加权限
        List<Permission> permissionList = null;
        if(null!=moduleIds&&moduleIds.length>0){
            permissionList = new ArrayList<>();
            Module module = null;
            for (Integer moduleId:moduleIds) {
                module = moduleDao.queryModuleById(moduleId);
                Permission permission = new Permission();
                if(null!=null){
                    permission.setAclValue(module.getOptValue());
                }
                permission.setRoleId(rid);
                permission.setModuleId(moduleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permissionList.add(permission);

            }
            AssertUtil.isTrue(permissionDao.insertBatch(permissionList)<moduleIds.length,"角色授权失败");

        }


    }
}
