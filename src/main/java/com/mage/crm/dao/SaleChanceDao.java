package com.mage.crm.dao;

import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.vo.SaleChance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleChanceDao {
    List<SaleChance> querySaleChancesByParams(SaleChanceQuery saleChanceQuery);

    /**
     * 添加营销机会
     * @param saleChance
     * @return
     */
    int insert(SaleChance saleChance);

    /**
     * 修改营销机会
     * 通过saleChance修改用户信息返回受影响的行
     * @param saleChance
     * @return
     */
    int update(SaleChance saleChance);

    /**
     * 删除营销机会
     * @param id
     * @return
     */
    int delete(Integer[] id);

    /**
     * 根据id查询
     * @param id
     * @return
     */

    SaleChance querySaleChanceById(String id);



    /**
     * 根据saleChanceid查看详情中的添加，添加营销机会
     * @param saleChanceId
     * @return
     */

    SaleChance querySaleChancesById(Integer saleChanceId);

    int updateSaleChanceDevResult(@Param("dev") Integer dev, @Param("id") Integer id);
}
