package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * @author Foss-220125-yangxiaolong
 * @date 2015-05-29 
 * @param 
 * 由于快递自动补录涉及到的校验逻辑不同于电子运单，新加校验逻辑
 * @return
 * @throws Exception 
 */
public interface IExpressAutoMakeupValidateService {
	/**
	 * 提交时新增运单快递
	 */
	String addWaybillExpressEntity(WaybillExpressEntity waybillExpress);
	
	/**
	 *通过运单编号修改运单
	 * @param waybill
	 */
	int updateWaybillExpressByWaybillNo(WaybillExpressEntity waybillExpress);
	
	/**
	 * 修改运单
	 * @param waybill
	 */
	int updateWaybillExpressById(WaybillExpressEntity waybillExpress);
	
	/**
	 * 通过运单编号查询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);
	
	/**
	 * 通过运单Id查询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId);
	
	/**
	 * 通过id询运单快递
	 * 
	 * @param waybill
	 */
	WaybillExpressEntity queryWaybillExpressById(String id);

	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 */
	List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode);

	/**
	 * @param condition
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition);
	
	
	
	/**
	 * 获得预计派送/提货时间/长短途
	 */
	EffectiveDto getPickupDeliveryTime(WaybillEntity entity);
	
	/**
	 * 判断提货方式是否自提
	 */
	Boolean isPickup(WaybillEntity entity);
	
	/**
	 * 获得预计出发时间
	 */
	Date getLeaveTime(WaybillEntity entity);

	/**
	 * 快递重量、体积、件数校验
	 */
	void validateExpWeightVolume(WaybillEntity waybillEntity);

	/**
	 * 目的站校验
	 */
	void validateExpDestination(WaybillEntity waybillEntity);

	/**
	 * 产品校验
	 */
	void validateExpProduct(WaybillEntity waybillEntity);

	/**
	 * 包装校验
	 */
	void validateExpPack(WaybillEntity waybillEntity);

	/**
	 * 客户校验
	 */
	void validateExpCustomer(WaybillEntity waybillEntity);

	/**
	 * 校验提货网点重量、体积上限
	 */
	void validateExpVW(WaybillEntity waybillEntity);

	/**
	 * 付款方式校验
	 */
	void validateExpPaymentMode(WaybillEntity waybillEntity);

	/**
	 *  代收货款校验
	 */
	void validateExpCod(WaybillEntity waybillEntity);

	/**
	 * 只允许合同客户才可以选择免费送货
	 */
	void validateExpDeliverFree(WaybillEntity waybillEntity);

	/**
	 * 验证重量与提货方式
	 */
	void validateExpWeightDeliveryMode(WaybillEntity waybillEntity);

	/**
	 * 检查试点城市和试点营业部的逻辑
	 */
	void validateExpressCity(WaybillEntity waybillEntity, WaybillExpressEntity waybillExpressEntity);

	/**
	 * 检查保险声明价值
	 */
	void validateExpInsuranceFee(WaybillEntity waybillEntity);

	/**
	 * 检查德邦客户和发货人工号
	 */
	void validateExpDepponExpressEmpCode(WaybillExpressEntity waybillExpressEntity);

	/**
	 * 校验优惠券是否开启
	 */
	void validateExpPromotionsCode(WaybillEntity waybillEntity);
	
	/**
	 * 校验营销活动是否开启
	 */
	void validateExpActiveStart(WaybillEntity waybillEntity);

	//通过客户编码和银行帐号查询CusAccountEntity
	CusAccountEntity queryEWaybillCusAccountInfo(WaybillEntity waybillEntity);

	/**
	 * 根据提货方式判断：是否自提
	 */
	boolean verdictPickUpSelf(String receiveMethod);

	/**
	 * 根据提货方式判断：是否送货上门 
	 */
	boolean verdictPickUpDoor(String receiveMethod);

	/**
	 * 根据部门编码获取简称
	 * @date 
	 */
	String getOrgAdminSimpleName(String targetOrgCode, Date createDate);

	/**
	 * 计算快递相关费用，并赋值到WaybillEntity，填充waybillChargeDtlEntityList、waybillDisDtlEntityList
	 */
	void calculateExpressAllFee(WaybillDto waybillDto, String status,EamDto eamDto);

	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo,
			String returnType);
	/**
	 * 返单开单
	 * @param waybillExpress
	 */
	void addWaybillExpressEntityReturn(WaybillExpressEntity waybillExpress);

	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,
			String returnType);

	/**
	 * 检查运单变更状态
	 * @param waybillId
	 */
	void checkWayBillChange(String waybillId);
	
}
