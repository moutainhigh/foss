package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPmtAutoPayDto;

/**
 * 新增付款单DAO
 * @author ibm-zhuwei
 * @date 2012-10-24 上午11:32:35
 */
public interface IBillPaymentEntityDao {

    /**
     * 新增付款单
     * @author ibm-zhuwei
     * @date 2012-10-24 上午11:32:28
     * @param entity 付款单
     * @return
     */
    int add(BillPaymentEntity entity);

    /**
     * 红冲付款单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:35:31
     * @param entity 付款单
     * @return
     */
    int updateByWriteBack(BillPaymentEntity entity);
    
    /**
     * 批量审核/反审核付款单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-26 上午10:29:17
     * @param dto 付款单DTO
     * @return
     */
    int updateBillPaymentByBatchAudit(BillPaymentDto dto);

	/**
	 * 批量更新工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:40:48
	 * @param dto 付款单DTO
     * @return
	 */
	int updateBillPaymentByBatchWorkflow(BillPaymentDto dto);

	/**
	 * 批量更备用金工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午4:43:04
	 * @param dto 付款单DTO
     * @return
	 */
	int updateBillPaymentByBatchApplyWorkflow(BillPaymentDto dto);
	
	/**
	 * 更新付款单的退款状态
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:10:49
	 * @param dto 付款单DTO
     * @return
	 */
	int batchReverseRemitStatusBillPayment(BillPaymentDto dto);
	
	/** 
	 * 根据付款单号集合，批量更新付款状态
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-02 上午11:39:00
	 * @param dto 付款单DTO
     * @return
	 */
	int updateRemitStatusByPmtNos(BillPmtAutoPayDto dto);
	
	/** 
	 * 根据汇款状态、付款类型，查询应付单
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-004 上午10:47:00
	 * @param entity 付款单ENTITY
     * @return 付款单集合
	 */
	List<BillPaymentEntity> queryBillPaymentByPmtType(BillPaymentEntity entity);
	
    /**
     * 根据一到多个付款单号，获取一到多条付款单记录
     * @author 099995-foss-wujiangtao
     * @date 2012-10-25 上午9:27:02
     * @param paymentNos 付款单号集合
     * @param active     是否有效
     * @return
     * @see
     */
    List<BillPaymentEntity> queryBillPaymentByPaymentNOs
    (List<String> paymentNos,String active);
    
    /**
     * 
     * 根据一到多个付款单id，获取一到多条付款单记录
     * @author 045738-foss-maojianqiang
     * @date 2012-12-1 上午9:01:51
     * @param ids    ID集合
     * @param active 是否有效
     * @return
     */
    List<BillPaymentEntity> queryBillPaymentByPaymentIds(List<String> ids,String active);
    
    /**
     * 根据一到多个来源单号，获取一到多条付款单记录
     * @author 099995-foss-wujiangtao
     * @date 2012-10-25 上午9:30:43
     * @param sourceBillNos  来源单据号集合
     * @param sourceBillType 来源单据类型
     * @param active         是否有效
     * @return
     * @see
     */
    List<BillPaymentEntity> queryBillPaymentBySourceBillNOs
    (List<String> sourceBillNos,String sourceBillType,String active);
    
    /**
     * 根据应付单号，或来源单号、单据类型等查询条件查询付款单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-25 上午10:32:37
     * @param dto 付款单查询条件DTO
     * @return
     * @see
     */
    List<BillPaymentEntity> queryBillPaymentByCondition(BillPaymentConditionDto dto);
    
    /**
     * 根据一到多个付款单号，获取一到多条付款单的汇款状态
     * @author 099995-foss-wujiangtao
     * @date 2012-10-25 上午9:27:02
     * @param paymentNos 付款单号集合
     * @param active     是否有效
     * @return
     * @see
     */
    List<BillPaymentEntity> queryPaymentRemitStatusByPaymentNOs
    (List<String> paymentNos,String active);
    
    /**
     * 根据外发单号和外发代理编码是否已存在有效的付款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-19 下午7:05:15
     * @param dto 付款单查询条件DTO
     * @return
     */
    int queryPaymentByExternalBillNo(BillPaymentConditionDto dto);

    /**
	 * 根据批次号（即付款编号）来查询付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午5:00:02
	 * @param batchNos
	 * @param active
	 * @return
	 */
    List<BillPaymentEntity> queryPaymentByBatchNos(String payNos,String active);
    
    /**
     * 根据来源单号集合和部门编码集合，查询付款单信息 
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 上午9:30:12
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

	int updatePaymentBatchNoBack(BillPaymentDto billPaymentDto);

	/**
	 * 修改付款单转报销工作流号
	 * @param map
	 * @return
	 */
	int updatePaymentByBatchNo(Map<Object, String> map);
	
	/**
	 * 合伙人到付运费自动付款重推更新付款单信息
	 * @author 231438
	 * @param billPaymentDto
	 * @return int
	 */
	int updateFcusPaymentByPaymentNos(BillPmtAutoPayDto billPaymentDto);
}
