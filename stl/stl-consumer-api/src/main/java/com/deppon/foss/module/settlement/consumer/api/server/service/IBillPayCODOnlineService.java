package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;

/**
 * 线上汇代收货款给客户服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-22 上午11:33:49
 */
public interface IBillPayCODOnlineService extends IService {
	
	/**
	 * 准备符合条件的全部代收货款做线上汇代收货款服务前，更改代收货款状态
	 * @param codBatchNumberStr
	 * @param codPayableDtoList
	 * @param currentInfo
	 * @author guxinhua
	 * @date 2013-11-30 上午11:35:50
	 */
	public void readySendBillPaybleToBankAll(String codBatchNumberStr,
								List<CODPayableDto> codPayableDtoList,
								List<CODMergeDto> mergeCodPayableDtoList,
								List<String> codTypes,
								CurrentInfo currentInfo);
	
	/**
	 * 符合条件的全部代收货款做线上汇代收货款服务.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @return the string
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-22 上午11:35:50
	 */
	String doWithSendBillPaybleToBankAll(Date endSignDate,
			List<String> codTypes, List<String> banks, String publicPrivateFlag, CurrentInfo currentInfo);

	/**
	 * 准备处理线上汇代收货款服务前，更改代收货款状态
	 * @param codBatchNumberStr
	 * @param codEntityList
	 * @param mergeCodMergeDtoList
	 * @param currentInfo
	 * @author guxinhua
	 * @date 2013-11-30 上午11:35:50
	 */
	public void readySendBillPaybleToBank(String codBatchNumberStr,
								List<CODEntity> codEntityList,
								List<CODMergeDto> mergeCodMergeDtoList,
								List<String> codTypes,
								CurrentInfo currentInfo);
	
	/**
	 * 
	 * 处理线上汇代收货款服务
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-22 上午11:35:50
	 */
	String doWithSendBillPaybleToBank(List<String> entityIds, List<String> codTypes,CurrentInfo currentInfo) throws SettlementException;

	/**
	 * 发送银企，线上汇代收货款异常处理
	 * @param codBatchNumberStr
	 * @param failNotes
	 * @param currentInfo
	 * @author guxinhua
	 * @date 2013-11-30 上午11:35:50
	 */
	public void errorDoSendBillPaybleToBank(String codBatchNumberStr,String failNotes,CurrentInfo currentInfo);
	
	/**
	 * 
	 * 生成代收货款付款单
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:39:30
	 */
	void generateCODPaymentBill(CODEntity entity, CurrentInfo currentInfo)
			throws SettlementException;
	
	/**
	 * 
	 * 生成代收货款付款单
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:39:30
	 */
	void generateCODPaymentBill(List<CODEntity> entityList,List<BillPayableEntity> billPayableEntityList, CurrentInfo currentInfo)
			throws SettlementException;
	/**
	 * 代收货款审核不通过
	 * @author lianghaisheng
	 * @date 2013-08-20
	 * */
	void processAuditFailed(String batchNumber, String remark) throws SettlementException;
	
	/**
	 * 代收货款审核成功
	 * @author lianghaisheng
	 * @date 2013-08-20
	 * */
	void processAuditSuccess(String batchNumber)throws SettlementException;

}
