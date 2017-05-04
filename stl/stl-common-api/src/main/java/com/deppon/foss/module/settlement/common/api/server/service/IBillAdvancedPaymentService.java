package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;

/**
 * 预付单公共service接口类
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 下午1:08:47
 */
public interface IBillAdvancedPaymentService extends IService {
	/**
	 * 修改预付单的对账单号及是否生成对账单字段值
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午7:30:36
	 */
	int updateBillAdvancedPaymentByMakeStatement(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
	 /**
	 * 根据传入的一到多个来源单号，获取一到多条预付单信息
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
	 * 核销空运预付的金额等字段信息
	 * @author foss-pengzhen
	 * @date 2012-10-22 下午1:54:20
	 * @param 
	 * @return
	 * @see
	 */
	void writeoffAdvancedPayment(BillAdvancedPaymentEntity entity, BigDecimal writeoffAmount,CurrentInfo currentInfo);
	
	/**
	 * 根据单个单号查询空运预付单数据
	 * @author foss-pengzhen
	 * @date 2012-10-23 下午5:40:56
	 * @param advancesNo
	 * @param active
	 * @return
	 * @see
	 */
	BillAdvancedPaymentEntity queryBillAdvancedPaymentNo(String advancesNo,String active);
	
	/**
	 * 新增预收单
	 * @author foss-pengzhen
	 * @date 2012-11-12 下午5:30:59
	 * @param entity
	 * @return
	 * @see
	 */
	int addAdvancedPaymentEntity(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
	
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
	 */
	int updatePaymentBillWorkFlow(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 更新费控返回审批结果
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 */
	int updatePaymentBillResult(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 作废预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午6:33:02
	 */
	int writeBackAdvancePay(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 批量修改预付单的对账单单号
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-4 下午6:34:13
	 * @param dto
	 */
	void batchUpdateByMakeStatement(BillAdvancedPaymentEntityDto dto, CurrentInfo currentInfo);
	
	
	/**
	 *  更新费控返回审批结果为审批失败
	 * @param entity
	 * @param currentInfo
	 * @return
	 */
	int updatePaymentBillResultFail(BillAdvancedPaymentEntity entity,CurrentInfo currentInfo);
}
