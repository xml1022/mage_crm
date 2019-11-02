package com.mage.crm.dao;

public interface UserRoleDao {
    int deleteUserRolesByUserId(int userId);

    int queryUserRoleCountsByUserId(Integer id);
}
