package com.mage.crm.dao;

import com.mage.crm.vo.Permission;

import java.util.List;

public interface PermissionDao {
    int queryPermissionDaoByRid(Integer rid);

    int deletePermissionDaoByRid(Integer rid);

    int insertBatch(List<Permission> permissionList);

    List<Integer> queryPermissionModuleIdsByRid(Integer rid);
}
