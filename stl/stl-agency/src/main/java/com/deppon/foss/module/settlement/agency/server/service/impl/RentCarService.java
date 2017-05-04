/**
 * 
 */
package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.agency.api.server.service.IRentCarService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.RentCarDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 045738
 * @外租车应付单生成和取消
 */
public class RentCarService implements IRentCarService {
	/**
	 * 插入日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(RentCarService.class);
	/**
	 *注入应付单公共接口 
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 结算通用Common Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入组织信息接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**  
	 * 注入组织公司
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 外场
	 */
	private IOutfieldService outfieldService;
	/**
	 * 功能：生成外租车应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-11
	 * @return  外租车信息(由于存在多车走货，可能一次生成多个应付单)
	 */
	public void addRentCar(List<RentCarDto> list, CurrentInfo cInfo) {
		//校验租车信息
		if(CollectionUtils.isEmpty(list) || list.size()==0){
			throw new SettlementException("传递进来的租车信息不能为空！");
		}
		LOGGER.info("STL 开始录入外租车服务：外租车记录数为"+list.size());
		//声明当前日期
		Date nowDate = new Date();
		List<BillPayableEntity> payList = dealParams(list,nowDate,cInfo);
		//循环插入应付单
		for (BillPayableEntity entity:payList){
			billPayableService.addBillPayable(entity, cInfo);
		}
		LOGGER.info(" STL 录入配载单服务成功！ ");
	}

	/**
	 * 功能：取消标记
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-11
	 * @return
	 */
	public void disableRentCar(String rentCarNo) {

	}
	
	
	/**
	 * 功能：校验传递参数
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-23
	 * @return
	 */
	private List<BillPayableEntity> dealParams(List<RentCarDto> dtoList,Date nowDate,CurrentInfo cInfo){
		//应付单列表
		List<BillPayableEntity> payList = new ArrayList<BillPayableEntity>();
		//租车编号列表
		List<String> rentCarNos = new ArrayList<String>();
		//循环封装校验传入参数
		for (RentCarDto dto:dtoList){
			//校验接口传入参数
			vailidate(dto,nowDate);
			//生成应付单
			rentCarNos.add(dto.getRentCarNo());
		}
		// 校验是否存在相同记录的应付单
		List<BillPayableEntity> list = this.billPayableService.queryBySourceBillNOs(
											rentCarNos,
											SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__RENTCAR,
											FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(list)){
			//获取系统中存在的外租车应付单
			BillPayableEntity entity = list.get(0);
			throw new SettlementException(entity.getSourceBillNo()+"外租车应付单在系统中已经存在，不能重复生成！");
		}
				
		//循环封装应付单
		for (RentCarDto dto:dtoList){
			BillPayableEntity entity = getBillPayableEntity(dto, nowDate, cInfo);
			payList.add(entity);
		}
		return payList;
	}
	
	/**
	 * 功能：校验传入租车信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-24
	 * @return
	 */
	private void vailidate(RentCarDto dto,Date nowDate){
		//校验租车编号
		if(StringUtils.isBlank(dto.getRentCarNo())){
			throw new SettlementException("租车编号不能为空！");
		}
		//租车金额不能为空
		if(dto.getAmount()==null){
			throw new SettlementException(dto.getRentCarNo()+"租车金额不能为空！");
		}
		//租车金额不能为空
		if(BigDecimal.ZERO.compareTo(dto.getAmount())>=0){
			throw new SettlementException(dto.getRentCarNo()+"租车金额必须大于0！");
		}
		//租车金额不能为空
		if(dto.getUseCarDate()==null){
			throw new SettlementException(dto.getRentCarNo()+"租车日期不能为空！");
		}
		//增加业务日期不能大于记账日期校验
		if(dto.getUseCarDate().after(nowDate)){
			throw new SettlementException(dto.getRentCarNo()+"用车日期不能晚于当前日期！");
		}
		//是否多次标记不能为空
		if(StringUtils.isEmpty(dto.getIsRepeatemark())){
			throw new SettlementException(dto.getRentCarNo()+"是否多次标记不能为空！");
		}
		//司机还不确定是否要添加
		if(StringUtils.isEmpty(dto.getCustomerCode())){
			throw new SettlementException(dto.getRentCarNo()+"传入的司机编号不能为空！");
		}
		if(StringUtils.isEmpty(dto.getRentCarUseType())){
			throw new SettlementException(dto.getRentCarNo()+"租车用途不能为空！");
		}
	}
	
	/**
	 * 根据传入的参数和单据类型设置应付单属性值
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-29 下午3:38:20
	 * @param dto
	 * @param billType
	 * @param date
	 * @param currentInfo
	 */
	private BillPayableEntity getBillPayableEntity(
			RentCarDto dto, Date date,
			CurrentInfo currentInfo) {
		if(null==currentInfo || null==dto){
			throw new SettlementException("根据传入的参数和单据类型设置应付单属性值时参数不能为空");
		}
		BillPayableEntity entity = new BillPayableEntity();
		//获取当前操作部门
		OrgAdministrativeInfoEntity currentOrg = currentInfo.getDept();
		if(null == currentOrg){
			throw new SettlementException("获取当前操作部门信息为空!");
		}	
		//如果不为营业部，则需判断是否为外场或车队下面，如果为外场，需要挂在顶级车队上
		if(!FossConstants.ACTIVE.equals(currentOrg.getSalesDepartment()) && 
				!FossConstants.ACTIVE.equals(currentOrg.getTransferCenter())){
			//如果不是外场，也不是营业部，则先找车队，找不到则找顶级外场
			currentOrg = orgAdministrativeInfoComplexService.getTopFleetByCode(currentInfo.getCurrentDeptCode());
			//如果为空，则取找顶级外场
			if(null == currentOrg) {
				List<String> bizTypes = new ArrayList<String>();
				//声明 --查询外场
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				currentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode(), bizTypes);
				if(null == currentOrg){
					throw new SettlementException("找不到顶级车队或对应的外场!");
				}				
			}
		}
		// ID
		entity.setId(UUIDUtils.getUUID());

		// 运单号
		entity.setWaybillNo(null);
		entity.setWaybillId(null);

		// 付款单号N/A
		entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		// 系统生成方式
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		// 配载车次号
		entity.setSourceBillNo(dto.getRentCarNo());

		// 来源单据类型
		entity.setSourceBillType(SettlementDictionaryConstants.
				BILL_PAYABLE__SOURCE_BILL_TYPE__RENTCAR);

		// 出发部门编码
		entity.setOrigOrgCode(currentOrg.getCode());
		// 出发部门名称
		entity.setOrigOrgName(currentOrg.getName());
		// 到达部门编码
		entity.setDestOrgCode(currentOrg.getCode());
		// 到达部门编码
		entity.setDestOrgName(currentOrg.getName());
		// 应付部门编码
		entity.setPayableOrgCode(currentOrg.getCode());
		// 应付部门名称
		entity.setPayableOrgName(currentOrg.getName());

		// 司机编码
		entity.setCustomerCode(dto.getCustomerCode());

		// 司机名称
		entity.setCustomerName(dto.getCustomerName());

		// 单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR);
		// 生效时间
		entity.setEffectiveDate(date);
		
		// 生效状态-已生效
		entity.setEffectiveStatus(SettlementDictionaryConstants.
				BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		// 生效人编码
		entity.setEffectiveUserCode(currentInfo.getEmpCode());
		// 生效人名称
		entity.setEffectiveUserName(currentInfo.getEmpName());	
		//设置应付单号
		entity.setPayableNo(this.settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF62));

		// 金额
		entity.setAmount(dto.getAmount());
		//如果为多次标记，则设置应付类型为多次标记
		if(FossConstants.ACTIVE.equals(dto.getIsRepeatemark())){
			entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__RENTCAR_REPET);
		}
		
		//设置应付公司 
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
		//如果部门为空，则抛出异常
		if(orgEntity!=null){
			//设置部门编码和名称
			entity.setPayableComCode(orgEntity.getSubsidiaryCode());
			//设置部门名称
			entity.setPayableComName(orgEntity.getSubsidiaryName());
		}
		
		// 已核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);
		
		// 未核销金额
		entity.setUnverifyAmount(entity.getAmount());
		
		// 币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		
		// 记账日期
		entity.setAccountDate(date);
		
		// 业务日期
		entity.setBusinessDate(dto.getUseCarDate());
		
		// 版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		// 是否有效
		entity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		entity.setIsInit(FossConstants.NO);
		
		// 冻结状态
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		
		// 支付状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 对账单号,默认为N/A
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 创建时间
		entity.setCreateTime(date);
		
		// 修改时间
		entity.setModifyDate(date);
		
		// 司机编码
		entity.setLgdriverCode(dto.getCustomerCode());
		
		// 司机名称
		entity.setLgdriverName(dto.getCustomerName());
		
		// 审批状态-已审核
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		//产品类型 -三级产品-精准汽运(短途)
		entity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
		//发票标记
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		
		return entity;
	}
	
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IOutfieldService getOutfieldService() {
		return outfieldService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
}
