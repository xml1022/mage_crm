package com.mage.crm.dao;

import com.mage.crm.dto.UserDto;
import com.mage.crm.query.UserQuery;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名查询用户是否存在，返回一个用户对象
     * @param userName
     * @return
     */
    User queryUserByName(String userName);

    /**
     * 修改密码
     * @param id
     * @return
     */
     User queryUserById(String id);
     Integer updatePwd(@Param("id") String id, @Param("userPwd") String userPwd);

    /**
     * 查询所有的客户经理
     * @return
     */
    List<User> queryAllCustomerManager();

    List<UserDto> queryUsersByParams(UserQuery userQuery);

    int insert(User user);

    int insertBatch(List<UserRole> userRoles);

    int update(User user);

    int delete(Integer id);

    List<String> queryPermissionByUserId(String id);
}
