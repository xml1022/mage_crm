package com.mage.crm.dao;

import com.mage.crm.vo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    List<Role> queryAllRoles();


    List<Role> queryRolesByParams(@Param("roleName") String roleName);

    Role queryRoleName(String roleName);

    int insert(Role role);

    int update(Role role);

    int delete(Integer id);

    Role queryRoleById(Integer rid);
}
