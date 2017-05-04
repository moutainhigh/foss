package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;


import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;


/**
 * 部门收支平衡表 Dao
 * @author dp-huangxb
 * @date 2012-10-19 下午3:17:24
 */
public interface ICreditOrgEntityDao {

	
	/**
	 * 
	 * 新加组织平衡表
	 * @author dp-huangxb
	 * @return 
	 * @date 2012-10-20 下午5:35:43
	 */
	int addCreditOrg(CreditOrgEntity item);
	
	
	/**
	 * 更新组织临欠基本信息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午11:10:30
	 */
	int updateCreditOrg(CreditOrgEntity item);
	
	/**
	 * 
	 * 查询部门收支平衡表
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:25:26
	 */
    CreditOrgEntity queryByOrgCode(String orgCode);
    
    
    /**
     * 
     * 按组织编码查询组织的信用额度信息
     * @author dp-huangxb
     * @date 2012-10-19 下午5:41:54
     * @param orgCode 组织编码
     */
    CreditOrgDto queryDebitByOrgCode(String orgCode);
 
    
    
    /**
     * 
     * 更新已用的信用额度
     * @author dp-huangxb
     * @date 2012-10-20 下午4:30:50
     */
    int updateUsedAmount(CreditOrgEntity entity);
    
    
    /**
     * 更新超期欠款标记
     * @author 000123-foss-huangxiaobo
     * @date 2012-10-23 下午3:23:48
     */
    int updateOverdueState(CreditOrgEntity entity);
    
    /**
     * 
     * 获得组织行数
     * @author 000123-foss-huangxiaobo
     * @date 2012-10-24 上午11:22:49
     */
    int queryTotalRows();
    
    
    /**
     * 
     * 通过分页的方式查询组织信息
     * @author 000123-foss-huangxiaobo
     * @date 2012-10-24 上午11:28:23
     */
    List<CreditOrgDto> queryOrgCodeByPage(int offset,int limit);

    /**
     * 根据部门编码查询部门欠款情况
     * @param orgCode
     * @return
     */
    List<CreditOrgDto> queryOrgDebitInfo(String orgCode);
}
