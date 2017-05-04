/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDestinationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressArrivalSheetDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressDto;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IWaybillExpressService {
	/**
	 * 提交时新增运单快递
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	String addWaybillExpressEntity(WaybillExpressEntity waybillExpress);
	
	/**
	 *通过运单编号修改运单
	 * 
	 * @param waybill
	 */
	int updateWaybillExpressByWaybillNo(WaybillExpressEntity waybillExpress);
	
	/**
	 * 修改运单
	 * 
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
	 * 内部接口：补码-更新运单提货网点等信息
	 * 输入 
	 * waybillNo  运单号          addCodeTime补码时间             customerPickupOrgCode新的客户提货网点
	 * @param dto
	 */
	WaybillDestinationDto addWaybillExpressCode(WaybillExpressDto dto);

	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:07:18
	 */
	List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode);
	
	/**
	 * 快递 提交派送装车任务后生成派送单和到达联 
	 * @param dto
	 * @return
	 */
	WaybillExpressArrivalSheetDto createExpressArrivalSheetAndDeliveryBill(WaybillExpressArrivalSheetDto dto);

	/**
	 * @param condition
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition);
	
	
	
	/*
	 * 获得预计派送/提货时间/长短途
	 */
	EffectiveDto getPickupDeliveryTime(WaybillEntity entity);
	
	/*
	 * 判断提货方式是否自提
	 */
	Boolean isPickup(WaybillEntity entity);
	
	/*
	 * 获得预计出发时间
	 */
	Date getLeaveTime(WaybillEntity entity);

	/*
	 * 快递重量、体积、件数校验
	 */
	void validateExpWeightVolume(WaybillEntity waybillEntity);

	/*
	 * 目的站校验
	 */
	void validateExpDestination(WaybillEntity waybillEntity);

	/*
	 * 产品校验
	 */
	void validateExpProduct(WaybillEntity waybillEntity);

	/*
	 * 包装校验
	 */
	void validateExpPack(WaybillEntity waybillEntity);

	/*
	 * 客户校验
	 */
	void validateExpCustomer(WaybillEntity waybillEntity);

	/*
	 * 校验提货网点重量、体积上限
	 */
	void validateExpVW(WaybillEntity waybillEntity);

	/*
	 * 付款方式校验
	 */
	void validateExpPaymentMode(WaybillEntity waybillEntity);

	/*
	 *  代收货款校验
	 */
	void validateExpCod(WaybillEntity waybillEntity);

	/*
	 * 只允许合同客户才可以选择免费送货
	 */
	void validateExpDeliverFree(WaybillEntity waybillEntity);

	/*
	 * 验证重量与提货方式
	 */
	void validateExpWeightDeliveryMode(WaybillEntity waybillEntity);

	/*
	 * 检查试点城市和试点营业部的逻辑
	 */
	void validateExpressCity(WaybillEntity waybillEntity, WaybillExpressEntity waybillExpressEntity);

	/*
	 * 检查保险声明价值
	 */
	void validateExpInsuranceFee(WaybillEntity waybillEntity);

	/*
	 * 检查德邦客户和发货人工号
	 */
	void validateExpDepponExpressEmpCode(WaybillExpressEntity waybillExpressEntity);

	/*
	 * 校验优惠券是否开启
	 */
	void validateExpPromotionsCode(WaybillEntity waybillEntity);
	
	/*
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
	 * @author 136334-BaiLei
	 * @date 2013-08-28 
	 */
	String getOrgAdminSimpleName(String targetOrgCode, Date createDate);

	/**
	 * 计算快递相关费用，并赋值到WaybillEntity，填充waybillChargeDtlEntityList、waybillDisDtlEntityList
	 * @author 136334-BaiLei
	 * @date 2013-09-10 
	 */
	void calculateExpressAllFee(WaybillDto waybillDto, String status);

	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo,
			String returnType);
	
	/**
	 * 根据原单号查询单号信息
	 * @param originalWaybillNo
	 * @author foss-206860
	 * */
	List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(String waybillNo);
	/**
	 * 返单开单
	 * @param waybillExpress
	 */
	void addWaybillExpressEntityReturn(WaybillExpressEntity waybillExpress);
	
	/**
	 * 返单开单
	 * @param waybillExpress
	 */
	void addWaybillExpressEntityReturn(List<WaybillExpressEntity> entitys);

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
	
	/**
	 * 根据查询参数进行查询对应目的站数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-19 18:23:05
	 * @param queryPickupPointDto
	 * @return
	 */
	SaleDepartmentEntity getTargetOrgCode(WaybillEntity waybillEntity);

	String getExceptionErrorInfo(Exception e);

	/**
	 * 判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:21:26
	 */
	boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	//查询返货信息
	WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo);
	/**
	 * 查询系统现有所有零担产品编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:44
	 * @return
	 */
	List<String> getAllLevels3ExpressProductCode();

	/**
	 * 查询系统现有所有快递产品编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:44
	 * @return
	 */
	List<String> getAllLevels3CargoProductCode();

	/**
	 * 根据三级产品查询出对应的一级产品类型
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:19
	 * @param productCode
	 * @return
	 */
	String getTransTypeByLevel3ProductCode(String productCode);

	
	/**
	 * TODO 通过返单号查询对应原单号
	 * @param waybillNo
	 * @param returnType
	 * @author foss-206860
	 * @date 2015-9-16 下午1:55:20
	 */
	List<WaybillExpressEntity> queryWaybillListByWaybillNo(String waybillNo);
	/**
	 * 通过原单号查询快递表中的信息
	 * 232608
	 */
	WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo);
	
	/**
	 * 通过返单号查询对应原单号
	 * @param waybillExpress
	 * @return
	 */
	public List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(WaybillExpressEntity waybillExpress);
	
}
