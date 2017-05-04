package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushPartnerWaybillLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToPartnersService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDestinationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.util.ConvertBean;
import com.deppon.foss.util.define.FossConstants;

/**
 * 推送合伙人信息
 * @author 272311-sangwenhao
 * @date 2016-1-30
 */
public class WaybillToPartnersService implements IWaybillToPartnersService {
	
	private Logger logger = LoggerFactory.getLogger(PartnersWaybillService.class);
	
	//营业部信息服务
	private ISaleDepartmentService saleDepartmentService ;  
	
	//异步推送信息至ptp服务
	private IExpWaybillInfoToPtpService expWaybillInfoToPtpService ;
	
	//运单信息服务类
	private IWaybillManagerService waybillManagerService ; 

	/**
	 * 更改单受理相关查询接口
	 */
	private IWaybillRfcVarificationDao waybillRfcVarificationDao;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 系统配置项服务
	 * 提供与系统配置项相关的服务接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	//
	private IPushPartnerWaybillLogService pushPartnerWaybillLogService;
	
	
	/**
	 * 根据运单号，目的站是否为合伙人 推送运单信息至ptp
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-30
	 */
	@Override
	public boolean sendDestinationInfo(WaybillDestinationDto waybillDestinationDto) {
		boolean flag = true ;
		logger.info("进入 sendDestinationInfo ");
		if(waybillDestinationDto!=null){
			
			//合伙人项目于4.10凌晨上线，4.10前的运单更改不走PTP逻辑
			ConfigurationParamsEntity entityDate = configurationParamsService.queryConfigurationParamsOneByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
			if (StringUtils.isNotEmpty(entityDate.getConfValue())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parterOnlineDate = null;
				try {
					parterOnlineDate = sdf.parse(entityDate.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(waybillDestinationDto.getWaybillEntity() != null && waybillDestinationDto.getWaybillEntity().getBillTime() != null && !waybillDestinationDto.getWaybillEntity().getBillTime().after(parterOnlineDate)){
					return false;
				}
			}
			
			PtpWaybillDto ptpWaybillDto = new PtpWaybillDto();
			
			// 定义运单DTO对象
			WaybillDto waybillDto = new WaybillDto();
			// 设置合伙人信息
			waybillDto.setPartnersWaybillEntity(waybillManagerService.queryPartnerWaybillEntityByWaybillId(waybillDestinationDto.getWaybillNoid()));
			
			waybillDto.setWaybillEntity(waybillDestinationDto.getWaybillEntity());
			waybillDto.setActualFreightEntity(waybillDestinationDto.getActualFreightEntity());
			
			//如果有代收货款,将银行卡信息取出来
			if(null != waybillDto.getWaybillEntity() 
			   && null != waybillDto.getWaybillEntity().getCodAmount() 
			   && waybillDto.getWaybillEntity().getCodAmount().compareTo(BigDecimal.ZERO) > 0){
				//开户银行信息，这些银行信息是补充的没有存在先关运单表中所以这样查询出来 2016年3月9日 08:00:45 葛亮亮				
				CusAccountEntity openBank = waybillRfcVarificationDao.queryCusAccountByWaybillInfo(
						waybillDto.getWaybillEntity().getAccountName(), waybillDto.getWaybillEntity().getAccountCode(), waybillDto.getWaybillEntity().getAccountBank(),waybillDto.getWaybillEntity().getDeliveryCustomerCode());
			
				/**
				 * CRM统一客户信息会作废一些客户，如果查询不到有效的客户信息，则获取创建时间最晚的一次的客户信息
				 */
				if(openBank==null){
					openBank=waybillRfcVarificationDao.queryCusAccountByCreateTime(
							waybillDto.getWaybillEntity().getAccountName(), waybillDto.getWaybillEntity().getAccountCode(), waybillDto.getWaybillEntity().getAccountBank(),waybillDto.getWaybillEntity().getDeliveryCustomerCode());
				}
				waybillDto.setOpenBank(openBank);
			}
			
			ptpWaybillDto = ConvertBean.getPtpWaybillDto(waybillDto,null);
			
			ptpWaybillDto.setArriveOrgCode(waybillDestinationDto.getLastLoadOrgCode());
			ptpWaybillDto.setCustomerPickupOrgCode(waybillDestinationDto.getCustomerPickupOrgCode());
			ptpWaybillDto.setCustomerPickupOrgName(waybillDestinationDto.getCustomerPickupOrgName());
			ptpWaybillDto.setTargetOrgCode(waybillDestinationDto.getTargetOrgCode());
			
			//创建部门标杆编码
			OrgAdministrativeInfoEntity currentDept = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getCreateOrgCode());
			ptpWaybillDto.setCurrentDeptUnifieldCode(currentDept.getUnifiedCode()); 
			//出发部门标杆编码
			OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getReceiveOrgCode());
			if(orgReceiveEntity!=null){
				ptpWaybillDto.setReceiveDeptUnifieldCode(orgReceiveEntity.getUnifiedCode());
				ptpWaybillDto.setReceiveOrgName(orgReceiveEntity.getName());
			}
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=null;
			//到达部门标杆编码(使用补码后的提货网点进行查询)
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getCustomerPickupOrgCode());
			if(null != orgAdministrativeInfoEntity){
				ptpWaybillDto.setArriveDeptUnifieldCode(orgAdministrativeInfoEntity.getUnifiedCode());
			}else{
                orgAdministrativeInfoEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(waybillDestinationDto.getLastLoadOrgCode());
				if(null != orgAdministrativeInfoEntity){
					ptpWaybillDto.setArriveDeptUnifieldCode(orgAdministrativeInfoEntity.getUnifiedCode());
				}
			}
			
			if (waybillDestinationDto.getIsReceiveDepPartner() && waybillDestinationDto.getIsArriveDepPartner()) {
				ptpWaybillDto.setIsArriveDepPartner(true);
				ptpWaybillDto.setIsReceiveDepPartner(true);
				ptpWaybillDto.setFeeType(WaybillConstants.TWO);//0:成本  1：提成  2：成本和提成
			}else if(waybillDestinationDto.getIsReceiveDepPartner()){
				ptpWaybillDto.setIsArriveDepPartner(false);
				ptpWaybillDto.setIsReceiveDepPartner(true);
				ptpWaybillDto.setFeeType(WaybillConstants.ZERO);//0:成本  1：提成  2：成本和提成
			}else if(waybillDestinationDto.getIsArriveDepPartner()){
				ptpWaybillDto.setIsArriveDepPartner(true);
				ptpWaybillDto.setIsReceiveDepPartner(false);
				ptpWaybillDto.setFeeType(WaybillConstants.ONE);//0:成本  1：提成  2：成本和提成
			}else{
				ptpWaybillDto.setIsArriveDepPartner(false);
				ptpWaybillDto.setIsReceiveDepPartner(false);
				ptpWaybillDto.setFeeType(WaybillConstants.THREE);//0:成本  1：提成  2：成本和提成 3:作废
			}
			String isISD =null;
			//WAYBILL中原来的提货网点
			SaleDepartmentEntity lastPickupDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(waybillDestinationDto.getOriginalWaybill().getCustomerPickupOrgCode());
			if(null == lastPickupDepartmentEntity){
				 isISD = FossConstants.NO;
			}else{
				 isISD=lastPickupDepartmentEntity.getIsLeagueSaleDept();
			}
			//本次补码时间（数据库更新时间）
			ptpWaybillDto.setCreateTime(waybillDestinationDto.getWaybillExpressEntity().getAddCodeTime());
			//如果本次补码前有过更改（或者是开单），需要传递两者最大的修改时间（防止本次补码前的修改单还没有在PTP被执行）
			Date lastDate;
			if(null != waybillDestinationDto.getLastAddCodeTime()){
				lastDate = waybillDestinationDto.getWaybillEntity().getCreateTime().after(waybillDestinationDto.getLastAddCodeTime()) ? waybillDestinationDto.getWaybillEntity().getCreateTime() : waybillDestinationDto.getLastAddCodeTime();
			}else{
				lastDate = waybillDestinationDto.getWaybillEntity().getCreateTime();
			}
			
			//直营开单 先补码到合伙人在补码到直营再补码到合伙人（第一次补码同步，第二次补码撤销，所以第三次补码时还需要查询历史）
			Boolean isTrue = false;
			//上次是直营补到直营或者上次改单是直营改到直营如果还有推送日志则是直营到直营的撤销
			if((!waybillDestinationDto.getIsLastPickUpPartner() && !waybillDestinationDto.getIsReceiveDepPartner())
			    ||(!waybillDestinationDto.getIsReceiveDepPartner() && !( FossConstants.YES.equals(isISD)))){
				Map<String, Object> waybillLogMap = new HashMap<String, Object>();
				waybillLogMap.put("wayBillNo", waybillDestinationDto.getWaybillEntity().getWaybillNo()); //运单单号
				waybillLogMap.put("wayBillCreateTime", lastDate); //时间
				PushPartnerWaybillLogEntity pushPartnerWaybillLogEntity = pushPartnerWaybillLogService.queryPushPartnerWaybillLogByMap(waybillLogMap);
				if(null != pushPartnerWaybillLogEntity){ //如果有推送日志则需要查询历史数据
					isTrue = true;
				}
			}
			
			//1.当上次补码到合伙人；2.合伙人开单。这两种情况下只要还能运行到代码这一步那都需要查询历史数据
			if((FossConstants.YES.equals(waybillDestinationDto.getIsLastAddCode()) && waybillDestinationDto.getIsLastPickUpPartner())
				|| waybillDestinationDto.getIsReceiveDepPartner()
				|| isTrue){
				ptpWaybillDto.setIsSearchHistory(FossConstants.YES);
				ptpWaybillDto.setLastCreateTime(lastDate);
			}
			
			ptpWaybillDto.setIsChanged("B");
			
			//推送信息至ptp
			expWaybillInfoToPtpService.asynSendExpWaybillInfoToPtp(ptpWaybillDto);
		}else{
			logger.info("接收到的 WaybillDestinationDto 信息为空");
			flag = false ;
			throw new BusinessException("WaybillDestinationDto 信息为空，请核实。");
		}
		return flag;
	}
	
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public IExpWaybillInfoToPtpService getExpWaybillInfoToPtpService() {
		return expWaybillInfoToPtpService;
	}

	public void setExpWaybillInfoToPtpService(
			IExpWaybillInfoToPtpService expWaybillInfoToPtpService) {
		this.expWaybillInfoToPtpService = expWaybillInfoToPtpService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public IWaybillRfcVarificationDao getWaybillRfcVarificationDao() {
		return waybillRfcVarificationDao;
	}

	public void setWaybillRfcVarificationDao(
			IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public IPushPartnerWaybillLogService getPushPartnerWaybillLogService() {
		return pushPartnerWaybillLogService;
	}

	public void setPushPartnerWaybillLogService(
			IPushPartnerWaybillLogService pushPartnerWaybillLogService) {
		this.pushPartnerWaybillLogService = pushPartnerWaybillLogService;
	}

}
