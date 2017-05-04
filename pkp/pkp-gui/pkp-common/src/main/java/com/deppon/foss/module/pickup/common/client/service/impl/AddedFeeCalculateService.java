package com.deppon.foss.module.pickup.common.client.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddedPlanFeeCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
/**
 * 加收方案费用计算service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-351326-xingjun,date:2016-8-30 下午3:00:09,content:TODO </p>
 * @author Foss-351326-xingjun 
 * @date 2016-8-30 下午3:00:09
 * @since
 * @version
 */
public class AddedFeeCalculateService implements IAddedFeeCalculateService {
	//远程访问Service
	IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
	
	/**
	 * gui开单加收方案费用计算
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-30 下午3:17:40
	 * @param bean 
	 * @see com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService#guiWaybillCreate(com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo)
	 */
	@Override
	public void guiWaybillCreate(WaybillPanelVo bean) {
		//目的站,使用提货网点
		String targetOrgCode = bean.getCustomerPickupOrgCode() == null ? "" : bean.getCustomerPickupOrgCode().getCode();
		
		//正常开单在选择目的站时，会将网点信息set到bean
		//pda补录重量体积失败时，可通过FOSS再次进行录入重量体积计算运费，此时因为直接加载了运单信息不会重新选择目的站，所以目的站网点的信息没有设置，还是为空，需要根据网点编号查询设置
		if(null == bean.getIsTwoLevelNetwork() || null == bean.getNetworkModel() || null == bean.getIsLeagueSaledept()){
			//查询网点信息
			SaleDepartmentEntity departmentEntity = waybillRemotingService.querySimpleSaleDepartmentByCodeCache(targetOrgCode);
			//未找到网点信息则不处理
			if(null == departmentEntity)
				return;
			//是否二级网点
			bean.setIsTwoLevelNetwork(departmentEntity.getIsTwoLevelNetwork());
			//网点模式
			bean.setNetworkModel(departmentEntity.getNetworkModel());
			//是否合伙人
			bean.setIsLeagueSaledept(departmentEntity.getIsLeagueSaleDept());
		}
		
		//处理费用
		guiAddedFeeCalculate(bean,targetOrgCode,"create");
	}
	
	/**
	 * gui改单处理加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-2 下午2:20:01
	 * @param bean 
	 * @param finalCustomerPickupOrgCode 最终提货网点
	 * @param dto
	 * @see com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService#guiWabillChange(com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo)
	 */
	@Override
	public void guiWabillChange(WaybillPanelVo bean,String finalCustomerPickupOrgCode,WaybillDto dto) {
		//如果未重新计算运费，先扣减原加收费，按照最新的目的站网点计算加收费
		if(FossConstants.NO.equals(bean.getIsCalTraFee())){
			//防止改单多次更改目的站网点时重复扣减，取改单前的运单实体信息作为基础扣减
			WaybillEntity waybillEntity = dto.getWaybillEntity();
			//取出折前表加收费、加收费率，后续扣减
			PartnersWaybillEntity partnersWaybillEntity = waybillRemotingService.queryPartnersWaybillEntityById(bean.getId());
			BigDecimal oldOverTransportFee = BigDecimal.ZERO;
			BigDecimal oldOverTransportRate = BigDecimal.ZERO;
			if(null != partnersWaybillEntity){
				oldOverTransportFee = null != partnersWaybillEntity.getOverTransportFee() ? partnersWaybillEntity.getOverTransportFee().divide(new BigDecimal(NumberConstants.NUMBER_100)) : BigDecimal.ZERO;
				oldOverTransportRate = null != partnersWaybillEntity.getOverTransportRate() ? partnersWaybillEntity.getOverTransportRate().divide(new BigDecimal(NumberConstants.NUMBER_100)) : BigDecimal.ZERO;
			}
			
			//运费扣减改单前加收费
			bean.setTransportFee(waybillEntity.getTransportFee().subtract(oldOverTransportFee));
			//费率（加收费率是开单直接累加到公布价费率上，所以这里直接扣减）
			bean.setUnitPrice(waybillEntity.getUnitPrice().subtract(oldOverTransportRate));
			//重新计算总运费(公布价运费变动需要重新计算预付、到付以及总运费，因为下面计算加收费用公用方法指是针对BEAN中费费用进行了累加)
			CalculateFeeTotalUtils.resetCalculateFee(bean);
			//将当前加收费和加收费率清空
			bean.setOverTransportFee(BigDecimal.ZERO);
			bean.setOverTransportRate(BigDecimal.ZERO);
			
			/**设置画布信息*/
			bean.setTransportFeeCanvas(bean.getTransportFee().toString());
			bean.setTotalFeeCanvas(bean.getTotalFee().toString());
		}
		
		//查询网点信息
		SaleDepartmentEntity departmentEntity = waybillRemotingService.querySimpleSaleDepartmentByCodeCache(finalCustomerPickupOrgCode);
		//未找到网点信息则不处理
		if(null == departmentEntity)
			return;
		//是否二级网点
		bean.setIsTwoLevelNetwork(departmentEntity.getIsTwoLevelNetwork());
		//网点模式
		bean.setNetworkModel(departmentEntity.getNetworkModel());
		//是否合伙人
		bean.setIsLeagueSaledept(departmentEntity.getIsLeagueSaleDept());
		guiAddedFeeCalculate(bean,finalCustomerPickupOrgCode,"change");
	}
	
	/**
	 * 运单更改中转信息计算加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-5 上午11:24:52
	 * @param bean
	 * @param waybillRfcTranferEntity 
	 * @see com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService#guiWaybillRfcTranfer(com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo, com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity)
	 */
	@Override
	@Deprecated
	public void guiWaybillRfcTranfer(WaybillPanelVo bean,WaybillRfcTranferEntity waybillRfcTranferEntity) {
		//目的站,转运或返货到达部门
		String targetOrgCode = waybillRfcTranferEntity.getChangeTargerOrgCode();
		//非合伙人不需要加收
		if(!"Y".equals(bean.getIsLeagueSaledept()))
			return;
		//判断是否需要加收
		if(!judgeNetWorkModel(bean.getIsTwoLevelNetwork(),bean.getNetworkModel()))
			return;
		//计费重量
		BigDecimal billWeight = bean.getGoodsWeightTotal();
		//体积
		BigDecimal volume = bean.getGoodsVolumeTotal();
		//开单时间
		Date createTime = bean.getBillTime();
		//中转费计费类型
		String billingtype = waybillRfcTranferEntity.getBillingType();
		//加收费
		AddedPlanFeeCalculateDto dto = waybillRemotingService.caculateAddFee(targetOrgCode, createTime, billingtype, volume, billWeight,"change");
		
		BigDecimal addedFee = dto.getAddedFee();
		//加收费率
		BigDecimal feeRate = dto.getFeeRate();
		//四舍五入
		addedFee = addedFee.setScale(0, BigDecimal.ROUND_HALF_UP);
		
		//加收费累加到中转费
		waybillRfcTranferEntity.setTransportFee(waybillRfcTranferEntity.getTransportFee().add(addedFee));
		//加收费率累加到中转费率
		waybillRfcTranferEntity.setTransportFeeRate(waybillRfcTranferEntity.getTransportFeeRate().add(feeRate));
		
		//加收费用
		bean.setOverTransportFee(addedFee);
		
	}	
	
	/**
	 * 根据WaybillPanelVo计算加收费用，并设置WaybillPanelVo中信息
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-3 上午11:11:53
	 * @param bean
	 * @param targetOrgCode 目的站网点
	 * @param createOrChange
	 * @see
	 */
	private void guiAddedFeeCalculate(WaybillPanelVo bean,String targetOrgCode,String createOrChange){
		//判断是否需要加收,非合伙人不需要加收或者非加收模式不需要加收（当前加收是针对合伙人二级网点）
		if(!"Y".equals(bean.getIsLeagueSaledept()) || 
				!judgeNetWorkModel(bean.getIsTwoLevelNetwork(),bean.getNetworkModel()))
			return;
		//计费重量
		BigDecimal billWeight = bean.getGoodsWeightTotal();
		//体积
		BigDecimal volume = bean.getGoodsVolumeTotal();
		//开单时间
		Date createTime = bean.getBillTime();
		//运费计费类型（体积、重量）
		String billingtype = bean.getBillingType().getValueCode();
		//加收费
		AddedPlanFeeCalculateDto dto = waybillRemotingService.caculateAddFee(targetOrgCode, createTime, billingtype, volume, billWeight,createOrChange);
		BigDecimal addedFee = dto.getAddedFee();
		//加收费率
		BigDecimal feeRate = dto.getFeeRate();
		//四舍五入
		addedFee = addedFee.setScale(0, BigDecimal.ROUND_HALF_UP);
		
		//付款方式
		String paidMethod = bean.getPaidMethod().getValueCode();
		//根据到付或者其他付款方式设置金额
		if(WaybillConstants.ARRIVE_PAYMENT.equals(paidMethod)){
			//将加收费累加到付金额
			bean.setToPayAmount(bean.getToPayAmount().add(addedFee));
		}else{
			//将加收费累加到预付金额
			bean.setPrePayAmount(bean.getPrePayAmount().add(addedFee));
		}
		//运费累加加收费
		bean.setTransportFee(bean.getTransportFee().add(addedFee));
		//总费用累加加收费
		bean.setTotalFee(bean.getTotalFee().add(addedFee));
		//费率
		bean.setUnitPrice(bean.getUnitPrice().add(feeRate));
		
		//加收费用（存放折前表）
		bean.setOverTransportFee(addedFee);
		//加收费率(存放折前表)
		bean.setOverTransportRate(feeRate);
		
		/**设置画布信息		 */
		bean.setTransportFeeCanvas(bean.getTransportFee().toString());
		bean.setTotalFeeCanvas(bean.getTotalFee().toString());
	}
	
	/**
	 * 判断网点模式需要加收
	 * 标准模式	STANDARD_MODEL
	 * 到达模式	ARRIVAL_MODEL
	 * 简装模式	SIMPLE_MODEL
	 * 代理模式	PROXY_MODEL
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-31 下午3:53:14
	 * @param networkModel
	 * @return true 需要加收 false 不需要
	 * @see
	 */
	private boolean judgeNetWorkModel(String isTwoLevelNetwork,String networkModel){
		//到达模式、简装模式、代理模式时，需要收取加收费用
		if("Y".equals(isTwoLevelNetwork) && ("ARRIVAL_MODEL".equals(networkModel) || 
				"SIMPLE_MODEL".equals(networkModel) ||
				"PROXY_MODEL".equals(networkModel)))
			return true;
		return false;
	}

}
