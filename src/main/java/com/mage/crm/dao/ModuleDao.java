package com.mage.crm.dao;

import com.mage.crm.dto.ModuleDto;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.vo.Module;

import java.util.List;

public interface ModuleDao {

    List<ModuleDto> queryAllsModuleDtos();

    Module queryModuleById(Integer moduleId);

    List<Module> queryModulesByParams(ModuleQuery moduleQuery);

    List<Module> queryModulesByGrade(Integer grade);

    Module queryModuleByGradeAndModuleName(Integer grade, String moduleName);

    Module queryModuleByOptValue(String optValue);

    Module queryModuleByPid(Integer parentId);

    int insert(Module module);
}
