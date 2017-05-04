package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.esb.inteface.domain.payment.StowageEntity;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;


/**
 * 录入付款单服务
 * @author 045738-foss-maojianqiang
 * @date 2012-11-27 下午7:44:46
 */
public interface IBillPaymentPayService extends IService {
	/**
	 * 录入付款单服务
	 * @param 付款单头
	 * @param 付款单明细列表
	 * @param 当前登录用户信息
	 * @author 045738-foss-maojianqiang
	 * @return 
	 * @date 2012-11-27 下午7:48:17
	 */
	String addBillPaymentInfo(BillPaymentEntity paymentEntity,List<BillPaymentAddDto> addDtoList,CurrentInfo currentInfo);
	
	/**
	 * 更新付款工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:54:58
	 */
	void updateWorkFlow(String paymentNos,String workflowNo);

	/**
	 * 对账单界面付款功能
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-11 下午7:45:44
	 */
	BillPayableManageResultDto payFormStatement(String[] billNos,CurrentInfo currentInfo);
	
	/**
	 * 封装财务共享的外请车付款配载单信息
	 * @author 045738-foss-maojianqiang
	 * @date 2013-5-24 上午10:00:19
	 * @param stowageDetail
	 * @param vehicleAssembleList
	 * @param trunkNos
	 * @param isReturnBackBalance
	 * @param currentInfo
	 */
	public void getStowageEntityList(List<StowageEntity> stowageDetail,List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList,
			List<String> trunkNos,boolean isReturnBackBalance,CurrentInfo currentInfo);
}
