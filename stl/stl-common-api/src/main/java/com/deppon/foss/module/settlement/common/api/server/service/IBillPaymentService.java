package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPmtAutoPayDto;

/**
 * 付款单服务
 * @author ibm-zhuwei
 * @date 2012-10-24 上午11:30:23
 */
public interface IBillPaymentService extends IService  {
	
	/**
	 * 新增付款单
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午9:35:02
	 */
	void addBillPayment(BillPaymentEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 红冲付款单
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午11:30:15
	 */
	void writeBackBillPayment(BillPaymentEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 批量审核付款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:37:21
	 * @param dto
	 * @param currentInfo
	 */
	void batchAuditBillPayment(BillPaymentDto dto,CurrentInfo  currentInfo);
	
	/**
	 * 批量反审核付款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午10:38:45
	 * @param dto
	 * @param currentInfo
	 */
	void batchReverseAuditBillPayment(BillPaymentDto dto,CurrentInfo  currentInfo);
	
	/**
	 * 根据一到多个付款单号，获取一到多条付款单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午11:45:46
	 * @param paymentNos
	 * @param active
	 * @return
	 * @see
	 */
	 List<BillPaymentEntity> queryBillPaymentByPaymentNOs(List<String> paymentNos, String active);
	 
	 /** 
	  * 根据一到多个付款单id，获取一到多条付款单记录
	  * @author 045738-foss-maojianqiang
	  * @date 2012-12-1 上午8:59:50
	  * @param ids
	  * @param active
	  */
	 List<BillPaymentEntity> queryBillPaymentByPaymentIds(List<String> ids, String active);
	 
	 /**
	  * 根据一到多个来源单号，获取一到多条付款单记录
	  * @author 099995-foss-wujiangtao
	  * @date 2012-10-25 上午11:46:14
	  * @param sourceBillNos
	  * @param active
	  * @param sourceBillType
	  * @return
	  * @see
	  */
	 List<BillPaymentEntity> queryBillPaymentBySourceBillNOs(List<String> sourceBillNos, String sourceBillType ,String active);
	 
	 /**
	  * 根据付款单号，或来源单号、单据类型等查询条件查询付款单信息 
	  * @author 099995-foss-wujiangtao
	  * @date 2012-10-25 上午11:46:47
	  * @param dto
	  * @return
	  * @see
	  */
	 List<BillPaymentEntity> queryBillPaymentByCondition(BillPaymentConditionDto dto);
	 
	 /**
	  * 根据一到多个付款单号，获取一到多条付款单的汇款状态
	  * @author 099995-foss-wujiangtao
	  * @date 2012-10-25 下午1:43:15
	  * @param paymentNos
	  * @param active
	  * @return
	  * @see
	  */
	 List<BillPaymentEntity> queryPaymentRemitStatusByPaymentNOs(List<String> paymentNos, String active);
	 
	 /**
	  * 根据外发单号和外发代理编码是否已存在有效的付款单信息
	  * 
	  * @author 099995-foss-wujiangtao
	  * @date 2012-11-19 下午7:07:27
	  * @param dto
	  * @return
	  */
	 int queryPaymentByExternalBillNo(BillPaymentConditionDto dto);

	/**
	 * 批量更新付款单的付款工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:20:04
	 */
	void batchReverseWorkFlowNoBillPayment(BillPaymentDto dto,CurrentInfo currentInfo);

	/**
	 * 批量更新付款单的备用金工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午4:47:57
	 */
	void batchReverseApplyWorkflowBillPayment(BillPaymentDto dto,CurrentInfo currentInfo);
	
	/**
	 * 批量更付款单的付款状态
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:08:18
	 */
	void batchReverseRemitStatusBillPayment(BillPaymentDto dto,CurrentInfo  currentInfo);
	
	/** 
	 * 根据付款单号集合，批量更新付款状态
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-02 上午11:39:00
	 * @param dto 付款单DTO
     * @return
	 */
	void updateRemitStatusByPmtNos(BillPmtAutoPayDto dto);
	
	/**
	 * 根据批次号（即付款编号）来查询付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午5:00:02
	 * @param batchNos
	 * @param active
	 * @return
	 */
	List<BillPaymentEntity> queryPaymentByBatchNos(String batchNos, String active);
	
	/**
	 * 根据来源单号集合和部门编码集合，查询付款单信息 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:30:59
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param orgCodes
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	List<BillPaymentEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos,
			String sourceBillType,List<String> orgCodes,String active,CurrentInfo currentInfo);
	
	  /**
     * 根据单个付款单号，查询付款单的汇款状态
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-3-7 下午4:54:08
     * @param paymentNo
     * @param active
     * @return
     */
    String queryPaymentRemitStatusByPaymentNO(String paymentNo,String active);
    
    /**
     * 保存付款单和付款单明细记录
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-3-17 下午3:43:03
     * @param entity
     * @param paymentDetails
     * @param currentInfo
     */
    void addBillPaymentAndDetails(BillPaymentEntity entity,
    		List<BillPaymentDEntity> paymentDetails,
    		CurrentInfo currentInfo);

	void updatePaymentBatchNoBack(BillPaymentDto billPaymentDto, CurrentInfo currentInfo);
	/**
	 * 新增代打木架对账单
	 * @author ddw
	 * @date 2014-06-17
	 */
	void addBillWoodenPayment(BillPaymentEntity entity);
	
	/**
	 * 家装对账单
	 * @author zya
	 * @date 2014-06-17
	 */
	void addBillDopPayment(BillPaymentEntity entity);
	
	/**
	 * 新增合伙人奖罚付款单
	 * @author gxr
	 * @date 2016-02-26
	 */
	void addBillPAwardPayment(BillPaymentEntity entity);
	
	/**
	 * 新增自动付款付款单
	 * @author Jiang Xun
	 * @date 2016-05-18
	 */
	void addBilAutoPayPayment(BillPaymentEntity entity);

	/**
	 * 修改付款单转报销工作流号
	 * @param map
	 * @return
	 */
	int updatePaymentByBatchNo(Map<Object, String> map);
	
	/** 
	 * 根据汇款状态、付款类型，查询应付单
	 * @author 231438
	 * @date 2016-06-06 上午:47:00
	 * @param entity 付款单ENTITY
     * @return 付款单集合
	 */
	List<BillPaymentEntity> queryBillPaymentByPmtType(BillPaymentEntity entity);
	
	/**
	 * 合伙人到付运费自动付款重推更新付款单信息
	 * @author 231438
	 * @param billPaymentDto
	 * @return int
	 */
	int updateFcusPaymentByPaymentNos(BillPmtAutoPayDto billPaymentDto);
}
