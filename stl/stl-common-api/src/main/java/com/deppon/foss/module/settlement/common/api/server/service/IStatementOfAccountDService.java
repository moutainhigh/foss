package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;

/**
 * 对账单明细公共service接口类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 下午12:31:46
 */
public interface IStatementOfAccountDService extends IService {

	/**
	 * 保存对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:43:22
	 */
	int add(StatementOfAccountDEntity entity);

	/**
	 * 根据对账单明细来源单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午10:56:09
	 */
	List<StatementOfAccountDEntity> queryByResourceNos(List<String> list);

	/**
	 * 根据对账单号、来源单号、及删除标记查询对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午3:43:27
	 */
	StatementOfAccountDEntity queryByResourceAndStatementNo(
			String sourceBillNo, String statementBillNo, String isDelete);

	/**
	 * 删除对账单明细时修改明细信息（逻辑删除）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 */
	void updateStatementDetailForDeleteDetail(StatementOfAccountDEntity entity);

	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:00:21
	 */
	List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo);
	
	/**
	 * 根据对账单号查询对账单明细信息(分页)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:00:21
	 */
	List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo,int pageNo,int pageSize);
	
	/**
	 * 根据对账单号查询对账单明细信息(总条数)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:00:21
	 */
	StatementOfAccountDetailCountDto queryCountByStatementBillNo(
			String statementBillNo);

	/**
	 * 由于对账单明细金额发生变法修改对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午11:30:33
	 */
	void updateStatementDetailByAmount(StatementOfAccountDEntity entity);
	
	/**
	 * 根据运单号集合查询对账单明细
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午11:30:33
	 * @param waybillNos
	 * @return
	 */
	List<StatementOfAccountDEntity> queryByWaybillNos(List<String> waybillNos);
	
	/**
	 * 
	 * 根据原始来源单号集合查询对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 11:14:18 AM
	 */
	List<StatementOfAccountDEntity> queryByOrigSourceBillNos(List<String> origSourceBillNos);
	
	/**
	 * 根据明细单号查询应收单、应付单、预收单、预付单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-18 下午2:41:49
	 */
	Map<Object,Object> queryBySourceBillNos(List<String> sourceBillNos);
	
	/**
	 * 查询单号List中是否至少一个单存在于代打木架对账单中
	 * @author 105762-Yang Shushuo
	 * @date 2014-07-16
	 */
	public boolean queryIfAtLeastOneBillExistsInStatement(List<String> billNoList);
}
