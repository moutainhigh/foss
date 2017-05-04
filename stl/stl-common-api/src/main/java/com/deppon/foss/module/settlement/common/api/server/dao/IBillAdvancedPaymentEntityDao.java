package com.deppon.foss.module.settlement.common.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;


/**
 * 预付单公共DAO接口类
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午9:26:23
 */
public interface IBillAdvancedPaymentEntityDao {

    /**
     * 修改预付单的对账单号及是否生成对账单字段值
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午7:30:36
     * @param entity 预付单
     * @return
     */
    int updateBillAdvancedPaymentByMakeStatement(BillAdvancedPaymentEntity entity);
    
    /**
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-18 上午11:52:05
	 * @param sourceBillNos
	 *            来源单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(
			List<String> advancesNos, String active);
	
	/**
	 * 核销空运预付的金额
	 * @author foss-pengzhen
	 * @date 2012-10-22 下午1:54:20
	 * @param id
	 * @param writeOffAmount 核销金额
	 * @return
	 */
	int writeoffAdvancedPayment(BillAdvancedPaymentEntity entity, BigDecimal writeOffAmount);
	
	/**
	 * 根据单个单号查询数据
	 * @author foss-pengzhen
	 * @date 2012-10-23 下午5:00:57
	 * @param advancesNo 预付单号
	 * @param active 是否有效
	 * @return
	 * @see
	 */
	BillAdvancedPaymentEntity queryBillAdvancedPaymentNo(String advancesNo,String active);

	/**
	 * 新增预收单
	 * @author foss-pengzhen
	 * @date 2012-11-12 下午5:30:59
	 * @param entity 预付单
	 * @return
	 * @see
	 */
	int addAdvancedPaymentEntity(BillAdvancedPaymentEntity entity);
	
	/**
	 * 修改审批状态
	 * @author foss-pengzhen
	 * @date 2013-1-7 下午4:24:23
	 * @param entity
	 * @return
	 * @see
	 */
	int updateAdvancePaymentAuditStatus(BillAdvancedPaymentEntity entity);
	
	/**
	 * 根据预付单，更新费控产生工作流号到Foss
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午4:14:12
	 * @param entity 预付单
	 * @return
	 */
	int updatePaymentBillWorkFlow(BillAdvancedPaymentEntity entity);
	
	/**
	 * 更新费控返回审批结果
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 * @param entity 预付单
	 * @return
	 */
	int updatePaymentBillResult(BillAdvancedPaymentEntity entity);
	
	/**
	 * 作废预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午6:33:02
	 * @param entity 预付单
	 * @return
	 */
	int writeBackAdvancePay(BillAdvancedPaymentEntity entity);
	
	/**
	 * 批量修改应付单的对账单单号
	 * @author foss-pengzhen
	 * @date 2012-12-4 下午8:06:47
	 * @param dto 预付单Dto
	 * @return
	 */
	int updateBatchByMakeStatement(BillAdvancedPaymentEntityDto dto);
	
	
	
	/**
	 * 更新费控返回审批结果，为审批失败
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 * @param entity 预付单
	 * @return
	 */
	int updatePaymentBillResultFail(BillAdvancedPaymentEntity entity);
}
