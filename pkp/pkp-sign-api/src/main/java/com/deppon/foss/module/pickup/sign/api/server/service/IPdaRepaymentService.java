package com.deppon.foss.module.pickup.sign.api.server.service;
import java.util.List;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaRepaymentDto;
import java.math.BigDecimal;

public interface IPdaRepaymentService {
	/**
	 * POS端自动结清货款   新增付款信息(内含财务接口调用)
	 * 零担业务自用 
	 * 供PDA运单结清货款
	 * @author 309603 yangqiang
	 * @date 2016-3-1
	 * @param CommonQueryParamDto dto
	 * 				empCode   		员工工号
	 * 				empName   		员工姓名
	 *				orgCode   		登录部门
	 *				isDriver  		是否为司机
	 * 				tradeSerialNo  	交易流水号
	 *		List<String> waybillNo  运单号
	 * @return RepaymentDto 未核销运单
	 */
	List<PdaRepaymentDto> addPaymentInfo(List<CommonQueryParamDto> dto);
	
	/**
	 * 新增付款信息(内含财务接口调用). 供PDA使用
	 * @author 309603 yangqiang
	 * @date 2016-3-3
	 */
	void addPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,WaybillDto waybilldto);

	/**
	 *@author 309603 yangqiang
	 *@date 2016-3-3
	 * 		
	 * 原单结清货款
	 * 
	 * 		
	 */
	public void addOriginalPaymentInfo(RepaymentEntity repaymentEntity,CurrentInfo currentInfo,String returnType, String waybillNo, TwoInOneWaybillDto twoInOneWaybillDto);
	/**
	 * 判断 是 司机还是营业员登陆
	 * @author 309603 yangqiang
	 * @date 2016-3-3
	 * @param vehicleNo 车牌号
	 * 		  waybill 运单号
	 * @return
	 */
	public CommonQueryParamDto isDriver(CommonQueryParamDto dto);
	/**
	 * 校验运单是否已核销
	 * @author 309603 yangqiang
	 * @date 2016-3-31
	 * @param waybill
	 * 			运单号
	 */
	public String checkVerification(String waybill);
    /**
     * 根据 交易流水号，部门编码，查询T+0数据返回，并判断余额大小
     * @param seriaNo           交易流水号
     * @param deptCode          部门编码
     * @param codAmount         实际支付代收货款
     * @param actualFreight     实际支付运费
     * @return
     */
    public PosCardEntity queryPosCard(String seriaNo, String deptCode, BigDecimal codAmount, BigDecimal actualFreight);

    public void applyPosCardAndDetail(String waybillNo,BigDecimal codAmount,BigDecimal actualFreight,CurrentInfo currentInfo, PosCardEntity posCardEntity,String invoiceType);
	
	 /**
     * 查询应收单数据
     * @param waybill
     * @return
     */
    public BillReceivableEntity queryBillReceivable(String waybill);
	
}
