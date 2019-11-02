package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.ModuleDao;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dto.ModuleDto;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Module;
import com.mage.crm.vo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService {
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private PermissionDao permissionDao;

    public List<ModuleDto> queryAllsModuleDtos(Integer rid) {
        //查询id，pid,name
        List<ModuleDto> moduleDtos = moduleDao.queryAllsModuleDtos();
        //勾选问题
        //通过rid查询对应的module_id,通过permision表进行关联
        List<Integer> moduleIds = permissionDao.queryPermissionModuleIdsByRid(rid);
            if (null != moduleDtos && moduleDtos.size() > 0) {
            for (ModuleDto moduleDto : moduleDtos) {
                Integer mid = moduleDto.getId();
                for (int i = 0; i < moduleIds.size(); i++) {
                     if (moduleIds.get(i) == mid) {
                       moduleDto.setChecked(true);// 节点选中！！！
                    }
                }
            }
        }
        return moduleDtos;
    }

    public Map<String, Object> queryModulesByParams(ModuleQuery moduleQuery) {
        PageHelper.startPage(moduleQuery.getPage(),moduleQuery.getRows());
        List<Module> modules = moduleDao.queryModulesByParams(moduleQuery);
        PageInfo<Module> pageInfo = new PageInfo<>(modules);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }

    public List<Module> queryModulesByGrade(Integer grade) {
        return moduleDao.queryModulesByGrade(grade);
    }

    public void insert(Module module) {
        checkModuleParams(module.getModuleName(),module.getGrade(),module.getOptValue());
        //模块名称不能重复
        AssertUtil.isTrue(null != moduleDao.queryModuleByGradeAndModuleName(module.getGrade(),module.getModuleName()), "该层级下模块名已存在!");
        //权限值不能重复
        AssertUtil.isTrue(null != moduleDao.queryModuleByOptValue(module.getOptValue()), "权限值不能重复!");
        //等级不是根级，设置它的根级
        if(module.getGrade()!= 0){
            AssertUtil.isTrue(null == moduleDao.queryModuleByPid(module.getParentId()),"父级菜单不存在!");
        }
        module.setIsValid(1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleDao.insert(module) < 1, "添加模块失败");
    }

    private void checkModuleParams(String moduleName, Integer grade, String optValue) {
        AssertUtil.isTrue(StringUtils.isBlank(moduleName), "模块名非空!");
        AssertUtil.isTrue(null == grade, "层级值非法!");
        Boolean flag = (grade != 0 && grade != 1 && grade != 2);
        AssertUtil.isTrue(flag, "层级值非法!");
        AssertUtil.isTrue(StringUtils.isBlank(optValue), "权限值非空!");
    }
}
