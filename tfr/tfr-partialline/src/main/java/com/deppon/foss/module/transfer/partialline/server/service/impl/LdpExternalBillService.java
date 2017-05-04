/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/LdpExternalBillService.java
 * 
 *  FILE NAME     :LdpExternalBillService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IPartbussPriceCaculateService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalLdpService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementLdpExternalBillDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputLdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.LdpExternalBillVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 落地配Service
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillService implements ILdpExternalBillService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdpExternalBillService.class);
	/**
	 * 落地配DAO接口
	 */
	private ILdpExternalBillDao ldpExternalBillDao;
	/**
	 * 查询未录入DAO接口
	 */
	private IUninputLdpExternalBillDao uninputLdpExternalBillDao;
	/**
	 * 结算接口Service
	 */
	private IVehicleAgencyExternalLdpService vehicleAgencyExternalLdpService;
	/**
	 * 计算费用接口Service
	 */
	@Resource
	private IPartbussPriceCaculateService partbussPriceCaculateService4Dubbo;
	/**
	 * 查询落地配代理
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * 运单Service	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private ITfrCommonService tfrCommonService;
	
	private IHandOverBillService handOverBillService;

	@Resource
	private IProductService productService4Dubbo;
	
	private IWaybillSignResultService waybillSignResultService;
	
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	private IWaybillRfcService waybillRfcService;

	private IInputWeightVolumnService inputWeightVolumnService;
	/**
	 * 落地配网点service接口
	 */
    private ILdpAgencyDeptService ldpAgencyDeptService;

    private IWaybillTrackingsService waybillTrackingsService;

	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
		this.waybillTrackingsService = waybillTrackingsService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}


	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	/**
	 * 落地配DAO接口
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:45:01
	 */
	public void setLdpExternalBillDao(ILdpExternalBillDao ldpExternalBillDao) {
		this.ldpExternalBillDao = ldpExternalBillDao;
	}

	/**
	 * 查询未录入DAO
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午6:56:15
	 */
	public void setUninputLdpExternalBillDao(IUninputLdpExternalBillDao uninputLdpExternalBillDao) {
		this.uninputLdpExternalBillDao = uninputLdpExternalBillDao;
	}

	/**
	 * 结算接口Service
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:45:17
	 */
	public void setVehicleAgencyExternalLdpService(IVehicleAgencyExternalLdpService vehicleAgencyExternalLdpService) {
		this.vehicleAgencyExternalLdpService = vehicleAgencyExternalLdpService;
	}
	
	/**
	 * 计算费用接口Service
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-25 上午11:45:17
	 */
	/*public void setPartbussPriceCaculateService(IPartbussPriceCaculateService partbussPriceCaculateService) {
		this.partbussPriceCaculateService = partbussPriceCaculateService;
	}*/

	/**
	 * 查询落地配代理Service
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17下午7:47:12
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * 运单查询Service
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:36:39
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setInputWeightVolumnService(
			IInputWeightVolumnService inputWeightVolumnService) {
		this.inputWeightVolumnService = inputWeightVolumnService;
	}

	/**
	 * 查询已录入落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:01:30
	 */
	@Override
	public List<LdpExternalBillDto> selectByParams(LdpExternalBillDto dto, int limit, int start, CurrentInfo currentInfo) {
		if (StringUtils.isNotBlank(dto.getAuditStatus())
				&& PartiallineConstants.PARTIALLINE_AUDITSTATUS_ALL.equals(dto.getAuditStatus())) {
			// 清空状态
			dto.setAuditStatus(null);
			// 状态列表
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			// 已经作废
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 设置
			dto.setList(availableStatus);
		}
		// 查询落地配列表
		List<LdpExternalBillDto> ldpExternalBillDtoList = ldpExternalBillDao.selectByParams(dto, limit, start);
		for(LdpExternalBillDto ldpExternalBillDto: ldpExternalBillDtoList){
			ldpExternalBillFeeDataConverting(ldpExternalBillDto);
		}

		return ldpExternalBillDtoList;
	}
	
	private void ldpExternalBillFeeDataConverting(LdpExternalBillDto ldpExternalBillDto) {
		BigDecimal payDpFee = ldpExternalBillDto.getPayDpFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getPayDpFee();
		BigDecimal codAmount = ldpExternalBillDto.getCodAmount() == null ? BigDecimal.ZERO : ldpExternalBillDto.getCodAmount();
		BigDecimal freightFee = ldpExternalBillDto.getFreightFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getFreightFee();
		BigDecimal externalInsuranceFee = ldpExternalBillDto.getExternalInsuranceFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getExternalInsuranceFee();
		BigDecimal codAgencyFee = ldpExternalBillDto.getCodAgencyFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getCodAgencyFee();
		BigDecimal billPayable = ldpExternalBillDto.getBillPayable() == null ? BigDecimal.ZERO : ldpExternalBillDto.getBillPayable();
		BigDecimal billReceiveable = ldpExternalBillDto.getBillReceiveable() == null ? BigDecimal.ZERO : ldpExternalBillDto.getBillReceiveable();
		BigDecimal agencyReturnFee = ldpExternalBillDto.getAgencyReturnFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getAgencyReturnFee();//快递代理返货费
		BigDecimal toPayFee = ldpExternalBillDto.getToPayFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getToPayFee();//到付手续费
		
		ldpExternalBillDto.setPayDpFee(payDpFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 		// 到付金额
		ldpExternalBillDto.setCodAmount(codAmount.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 代收货款
		ldpExternalBillDto.setFreightFee(freightFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 外发运费（德邦付给代理）
		ldpExternalBillDto.setExternalInsuranceFee(externalInsuranceFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 保价费 (外发)
		ldpExternalBillDto.setCodAgencyFee(codAgencyFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 代收货款手续费
		ldpExternalBillDto.setBillPayable(billPayable.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 应付费用
		ldpExternalBillDto.setBillReceiveable(billReceiveable.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 应收费用
		ldpExternalBillDto.setAgencyReturnFee(agencyReturnFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 快递代理返货费
		ldpExternalBillDto.setToPayFee(toPayFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 到付手续费

        //将详细地址设置入list
        WaybillDto waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
        WaybillEntity waybill = waybillDto.getWaybillEntity();
        String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(),
                waybill.getReceiveCustomerCityCode(), waybill.getReceiveCustomerDistCode(),
                waybill.getReceiveCustomerAddress());
        ldpExternalBillDto.setReceiveCustomerAddress(receiveCustomerAddress);

	}


	/**
	 * 查询已录入落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:01:30
	 */
	@Override
	public List<LdpExternalBillDto> queryExternalBillInfoList(LdpExternalBillDto dto, boolean flag) {
		//查询外发单明细list
		List<LdpExternalBillDto> resultList = queryOrigLdpExternalBillList(dto);
		//补充运单相关信息，参数：true表示是否需要付款方式，提货方式等属性转换成中文
		completeExternalBillList(resultList,flag);
		return resultList;
	}
	
	/**
	 * 查询已录入落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01 上午9:01:30
	 */
	@Override
	public List<LdpExternalBillDto> queryOrigLdpExternalBillList(LdpExternalBillDto dto) {
		
		// 如果状态选择了全部
		if (StringUtils.isNotBlank(dto.getAuditStatus())
				&& PartiallineConstants.PARTIALLINE_AUDITSTATUS_ALL.equals(dto.getAuditStatus())) {
			// 清空状态
			dto.setAuditStatus(null);
			// 状态列表
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			// 已经作废
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 设置
			dto.setList(availableStatus);
		}
		// 查询落地配列表
		List<LdpExternalBillDto> resultList = ldpExternalBillDao.queryLdpExternalBillList(dto);

		return resultList;
	}

	private void completeExternalBillList(List<LdpExternalBillDto> resultList, boolean showDesc) {
		WaybillDto waybillDto = null;
		for(LdpExternalBillDto ldpExternalBillDto : resultList){
			String waybillNo = ldpExternalBillDto.getWaybillNo();
			if(showDesc){
				ldpExternalBillDto.setAuditStatus(PartiallineConstants.getAuditStatusMap().get(ldpExternalBillDto.getAuditStatus()));
			}
			try {
				LOGGER.info("根据运单号查询运单信息:" + waybillNo);
				waybillDto = waybillManagerService.queryWaybillByNo(waybillNo);
				if(waybillDto == null){
					throw new Exception("查询运单失败");
				}
			} catch (Exception e) {
				//接送货接口查询运单信息出错
				LOGGER.error("新增快递代理外发单：" + PartiallineConstants.WAY_BILL_EXCEPTION, e);
				throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, e);
			}
			if (waybillDto != null) {
				WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
				if (waybillEntity != null) {
					// 付款方式
					if (StringUtils.isNotBlank(waybillEntity.getPaidMethod())) {
						ldpExternalBillDto.setPayType(waybillEntity.getPaidMethod());
						if(showDesc){
							// 付款方式中文
							ldpExternalBillDto.setPayType(DictUtil.rendererSubmitToDisplay(waybillEntity.getPaidMethod(), PartiallineConstants.SETTLEMENT__PAYMENT_TYPE));
						}
					}
					//提货方式
					//sonar-352203
					initLdpExternalBillDto(showDesc, ldpExternalBillDto,
							waybillEntity);
					//收货联系人姓名
					ldpExternalBillDto.setReceiveName(waybillEntity.getReceiveCustomerContact());
					//收货客户地址
					String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillEntity.getReceiveCustomerProvCode(),
							waybillEntity.getReceiveCustomerCityCode(), waybillEntity.getReceiveCustomerDistCode(), 
							waybillEntity.getReceiveCustomerAddress());
					ldpExternalBillDto.setReceiveAddr(receiveCustomerAddress);

					//收货客户(公司)
					ldpExternalBillDto.setReceiveCompany(waybillEntity.getReceiveCustomerName());
					//收货联系人电话
					String phoneNum = waybillEntity.getReceiveCustomerMobilephone();
					if(StringUtils.isBlank(phoneNum)){
						phoneNum = waybillEntity.getReceiveCustomerPhone();
					}
					ldpExternalBillDto.setReceivePhone(phoneNum);
					
					//货物声明价值
					ldpExternalBillDto.setDeclarationValue(waybillEntity.getInsuranceAmount());
					//件数
					ldpExternalBillDto.setGoodsNum(waybillEntity.getGoodsQtyTotal());
					//重量
					ldpExternalBillDto.setWeight(waybillEntity.getGoodsWeightTotal());
					//体积
					ldpExternalBillDto.setVolume(waybillEntity.getGoodsVolumeTotal());
				}
			}

            //设置地址
            WaybillEntity waybill = waybillDto.getWaybillEntity();
            String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(),
                    waybill.getReceiveCustomerCityCode(), waybill.getReceiveCustomerDistCode(),
                    waybill.getReceiveCustomerAddress());
            ldpExternalBillDto.setReceiveCustomerAddress(receiveCustomerAddress);
		}
	}

	/**
	 * @param showDesc
	 * @param ldpExternalBillDto
	 * @param waybillEntity
	 */
	private void initLdpExternalBillDto(boolean showDesc,
			LdpExternalBillDto ldpExternalBillDto, WaybillEntity waybillEntity) {
		if(StringUtils.isNotBlank(waybillEntity.getReceiveMethod())){
			ldpExternalBillDto.setPickupType(waybillEntity.getReceiveMethod());
			if(showDesc){
				ldpExternalBillDto.setPickupType(DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(),	PartiallineConstants.WAYBILL_PICKUP_TYPE));
					String receiveMethodDesc = tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
					if(StringUtils.isBlank(receiveMethodDesc)){
						receiveMethodDesc = tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS_AIR, waybillEntity.getReceiveMethod());
					}
				ldpExternalBillDto.setPickupType(receiveMethodDesc);
			}
		}
	}

	/**
	 * 查询当前用户部门对应的外场下所有的子孙部门
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:02:45
	 */
	private List<String> queryTransCenterChildrenCodes(String currentDeptCode) {
		List<String> rs = null;
		// 当前部门所对应的外场编码
		String transCenterCode = "";
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentDeptCode);
		if (transCenter != null) {
			transCenterCode = transCenter.getCode();
		}
		// 根据外场查询子孙部门编码
		if (StringUtils.isNotBlank(transCenterCode)) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService													 
					.queryOrgAdministrativeInfoEntityAllSubByCode(transCenterCode);					 
			if (CollectionUtils.isNotEmpty(orgs)) {
				rs = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					rs.add(org.getCode());
				}
			}
		}
		return rs;
	}

	/**
	 * 根据传入的部门code，获取该部门所属的外场
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:17:21
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		} else {
			// 获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括外场)！##########");
			return null;
		}
	}

	/**
	 * 获取落地配外发单总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午2:33:09
	 */
	@Override
	public Long queryCount(LdpExternalBillDto dto, CurrentInfo currentInfo) {
		if (StringUtils.isNotBlank(dto.getAuditStatus())
				&& PartiallineConstants.PARTIALLINE_AUDITSTATUS_ALL.equals(dto.getAuditStatus())) {
			// 清空状态
			dto.setAuditStatus(null);
			// 状态列表
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			// 已经作废
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 设置
			dto.setList(availableStatus);
		}
		// 查询总条数
		return ldpExternalBillDao.queryCount(dto);
	}
	
	/**
	 * 保存落地配外发单，并同步结算数据
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:47:04
	 * @update-author foss--269701--lln
	 * @update-date 2015/10/23
	 * @update-param  waybillNo 运单号,serialNo 流水号;
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addOneLdpExternalBill(String waybillNo,String serialNo ,CurrentInfo currentInfo) {
		LOGGER.info("生成快递代理外发单，并同步结算数据。运单号和流水号：" + waybillNo+","+serialNo);
		
		try{
			//需求变更前-默认为1件，流水号为0001
			//ldpExternalBillDao.addUnInputWaybillNo(UUIDUtils.getUUID(),waybillNo,"0001");
			//需求变更-- 一票多件时，根据流水号生成外发单
			//新增运单号流水号至中间表，用于并发控制
			ldpExternalBillDao.addUnInputWaybillNo(UUIDUtils.getUUID(),waybillNo,serialNo);
		}catch(Exception e){
			LOGGER.info("新增快递代理外发单，运单和流水号："+ waybillNo+","+serialNo+"已存在中间表中");
		}
		//更新数据，获取乐观锁，防止并发
		//需求变更前-- 默认为1件，流水号为0001
		//int result = ldpExternalBillDao.updateUnInputWaybillNo(waybillNo,"0001");
		//需求变更后--一票多件时，根据流水号生成外发单
		int result = ldpExternalBillDao.updateUnInputWaybillNo(waybillNo,serialNo);
		//未更新成功，则说明已生成外发单
		if(result == 0) {
			throw new ExternalBillException("已生成快递代理外发单");
		}
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		WaybillDto waybillDto = null;
		//设置用户信息
		userInfo(ldpExternalBillDto, currentInfo);
		try {
			LOGGER.info("根据运单号查询运单信息:" + waybillNo);
			//一个运单对应的所有流水号信息是一样，故可以根据流水号进行查询
			waybillDto = waybillManagerService.queryWaybillByNo(waybillNo);
		} catch (Exception e) {
			//接送货接口查询运单信息出错
			LOGGER.error("新增快递代理外发单：" + PartiallineConstants.WAY_BILL_EXCEPTION, e);
			throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, e);
		}
		if (waybillDto != null) {
			//modified by 257220 
			//子母件重量体积信息优先从录入的信息中获取
			InputWeightVolumnEntity inputWeightVolumnEntity = inputWeightVolumnService.queryInputWeightVolumnByWaybillNo(waybillNo,serialNo);
			if(inputWeightVolumnEntity != null){
				waybillDto.getWaybillEntity().setGoodsWeightTotal(inputWeightVolumnEntity.getWeight());
				waybillDto.getWaybillEntity().setGoodsVolumeTotal(inputWeightVolumnEntity.getVolumn());
			}
			//需求变更前--根据运单号判断运单是否签收
			//一票多件需求变更后--需要根据流水号进行判断签收
			checkSignStatus(waybillNo,serialNo);
			//根据运单判断运单是否存在未处理的更改单
			checkRfcWaybillStatus(waybillNo);
			LOGGER.info("通过网点查询代理编码查询代理公司,并补充代理信息");
			// 外发单补充落地配公司信息
			makeExternalBillAgentCompanyInfo(waybillDto, ldpExternalBillDto);
			// 外发单补充运单信息
			makeExternalBillWaybillInfo(waybillDto, ldpExternalBillDto);
			// UUID生成
			ldpExternalBillDto.setId(UUIDUtils.getUUID());
			// 是否中转
			ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
			// 发送状态
			ldpExternalBillDto.setSendStatus(PartiallineConstants.IS_HASSEND_NO);
			// 外发单号
			String externalBillNo = ldpExternalBillDao.createExternalBillNo(waybillNo, serialNo);
			ldpExternalBillDto.setExternalBillNo(externalBillNo);
			// 待审核状态
			ldpExternalBillDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 落地配类型
			ldpExternalBillDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
			//流水号
			ldpExternalBillDto.setSerialNo(serialNo);
			//需求变更前--根据运单号取最新的交接单明细信息，获取并设置交接单号
			//一票多件需求变更后--一票多件时，根据运单号和流水号取最新的交接单明细信息，获取并设置交接单号
			queryLastHandoverBillDetail(ldpExternalBillDto, currentInfo);
			
			//调用接口，计算费用信息
			caculateFeeInfo(waybillDto, ldpExternalBillDto);
			//需求变更前--根据运单号校验
			//需求变更后--一票多件时，根据流水号和运单号做校验
			//落地配外发表，需要新增流水号字段；之前一个运单号对应的流水号的外发单号是一样的，现在不一样
			// 执行业务校验
			validateAddExternalBill(ldpExternalBillDto);
			// 验证通过，执行录入操作
			// 插入落地配外发单
			LOGGER.info("插入快递代理外发单" + ldpExternalBillDto.toString());
			//根据运单号查询 第一个流水号
			String serialNoFirst=ldpExternalBillDao.queryFirstSerialNoByWayBill(waybillNo);
			//有到付、代收、保价等信息的一票多件外发，到付、代收、保价信息都显示在流水号为0001的单号上，并生成应收费用
			 //除0001流水号以外，其他流水号不挂到付、代收、保价等信息，应收费用为0
			//除外发运费外

			if(!StringUtils.equals(ldpExternalBillDto.getSerialNo(),serialNoFirst)){
				// 保价费
				ldpExternalBillDto.setExternalInsuranceFee(BigDecimal.ZERO);
				//到付运费
				ldpExternalBillDto.setPayDpFee(BigDecimal.ZERO);
				//代收货款
				ldpExternalBillDto.setCodAmount(BigDecimal.ZERO);
				// 代收货款手续费
				ldpExternalBillDto.setCodAgencyFee(BigDecimal.ZERO);
				//快递代理返货费
				ldpExternalBillDto.setAgencyReturnFee(BigDecimal.ZERO);
				//到付手续费
				ldpExternalBillDto.setToPayFee(BigDecimal.ZERO);
				// 应收费用
				ldpExternalBillDto.setBillReceiveable(BigDecimal.ZERO);
			}
			
			ldpExternalBillDao.insert(ldpExternalBillDto);
			// 结算系统数据插入
			SettlementLdpExternalBillDto stlDto = new SettlementLdpExternalBillDto();
			// 拷贝数据
			copyProperties(stlDto, ldpExternalBillDto, waybillDto);
			try {
				// 结算新增addExternalBill(stlDto)
				vehicleAgencyExternalLdpService.addExternalBill(stlDto, currentInfo);
			}catch (SettlementException e) {
				//接送货接口查询运单信息出错
				LOGGER.error("生成外发单时调用结算接口出错：", e);
				throw new ExternalBillException(e.getErrorCode());
			}
		} else {
			// 未查询到对应的运单
			throw new ExternalBillException("根据运单号未查询到对应的运单信息，运单号："+waybillNo);
		}

	}

	/**
	 * 基础数据处理-获取用户信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:40:01
	 */
	private void userInfo(LdpExternalBillDto ldpExternalBillDto, CurrentInfo user) {
		// 设置用户相关信息
		Date currentTime = new Date();
		// 录入时间
		ldpExternalBillDto.setRegisterTime(currentTime);
		// 修改时间
		ldpExternalBillDto.setModifyTime(currentTime);
		if (StringUtils.isNotBlank(user.getEmpCode())) {
			LOGGER.info("快递代理外发员工号" + user.getEmpCode());
			// 外发员工号
			ldpExternalBillDto.setExternalUserCode(user.getEmpCode());
			LOGGER.info("快递代理外发员姓名" + user.getEmpCode());
			// 外发员姓名
			ldpExternalBillDto.setExternalUserName(user.getEmpName());
			// 修改人
			ldpExternalBillDto.setModifyUserCode(user.getEmpCode());
			ldpExternalBillDto.setModifyUserName(user.getEmpName());
		}
	}
	
	/**
	 * 外发单补充落地配公司信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午1:56:09
	 */
	private void makeExternalBillAgentCompanyInfo(WaybillDto waybillDto, LdpExternalBillDto ldpExternalBillDto) {
		if (waybillDto != null) {
			// 根据运单号查询落地配代理信息
			// 提货网点CODE
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			// 落地配代理网点编码
			String agencyBranchCode = null;
			if (waybill != null) {
				// 代理网点信息CODE
				agencyBranchCode = waybill.getCustomerPickupOrgCode();
				LOGGER.info("快递代理网点CODE:" + agencyBranchCode);
			}else{
				waybill = new WaybillEntity();
			}
			// 落地配公司
			OuterBranchExpressEntity companyDto = null;
			if(StringUtils.isNotBlank(agencyBranchCode)) {
				// 落地配网点编码
				ldpExternalBillDto.setAgentOrgCode(agencyBranchCode);
				// 落地配网点名称
				ldpExternalBillDto.setAgentOrgName(waybill.getTargetOrgCode());
				//获取对应的省份(行政区域)
				OuterBranchExpressEntity orgEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(agencyBranchCode, FossConstants.ACTIVE);
				if(orgEntity == null){
					LOGGER.info("通过快递代理网点编码获取快递代理网点信息失败，未返回任何记录");
					throw new ExternalBillException("通过快递代理网点编码获取快递代理网点信息失败，未返回任何记录", "");
				}else{
					ldpExternalBillDto.setDistrictCode(orgEntity.getProvCode());
				}
				LOGGER.info("通过网点查询代理编码查询代理公司:");
				// 代理信息Dto // 代理网点编码查询代理公司
				companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(agencyBranchCode, FossConstants.ACTIVE);
			}
			if (companyDto != null) {
				// 落地配公司
				if (StringUtils.isNotBlank(companyDto.getAgentCompany())) {
					ldpExternalBillDto.setAgentCompanyCode(companyDto.getAgentCompany());
					LOGGER.info("快递代理公司编号( 外发代理编号):" + companyDto.getAgentCompany());
				}else{
					LOGGER.info("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理公司编号( 外发代理编号)，请检查综合配置");
					
					throw new ExternalBillException("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理公司编号( 外发代理编号)，请检查综合配置", "");
				}
				// 落地配代理名称（外发代理）
				if (StringUtils.isNotBlank(companyDto.getAgentCompanyName())) {
					ldpExternalBillDto.setAgentCompanyName(companyDto.getAgentCompanyName());
					LOGGER.info("快递代理代理名称（外发代理）:" + companyDto.getAgentCompanyName());
				}else{
					LOGGER.info("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理公司名称( 外发代理名称)，请检查综合配置");
					
					throw new ExternalBillException("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理公司名称( 外发代理名称)，请检查综合配置", "");
				}
			} else {
				LOGGER.info("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理代理信息,请检查综合配置");
				// 抛出业务异常“落地配代理为空,请核实”
				throw new ExternalBillException("无法通过" + waybill.getTargetOrgCode() + "找到对应的快递代理代理信息,请检查综合配置", "");
			}
		}
	}
	
	/**
	 * 外发单补充运单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午1:56:09
	 */
	private void makeExternalBillWaybillInfo(WaybillDto waybillDto, LdpExternalBillDto ldpExternalBillDto) {
		if (waybillDto != null) {
			// 根据运单号查询落地配代理信息
			// 提货网点CODE
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			if (waybill != null) {
				// 运单号
				ldpExternalBillDto.setWaybillNo(waybill.getWaybillNo());
				ldpExternalBillDto.setCreateOrgCode(waybill.getCreateOrgCode());
				ldpExternalBillDto.setCreateOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getCreateOrgCode()));
				//代收货款
				if(waybill.getCodAmount() == null){
					ldpExternalBillDto.setCodAmount(BigDecimal.ZERO);
				}else{
					ldpExternalBillDto.setCodAmount(waybill.getCodAmount());
				}
				//币制
				ldpExternalBillDto.setCurrencyCode(waybill.getCurrencyCode());
				//返单类型
				ldpExternalBillDto.setReturnType(waybill.getReturnBillType());
				//到付金额
				if(waybill.getToPayAmount() == null){
					ldpExternalBillDto.setPayDpFee(BigDecimal.ZERO);
				}else{
					ldpExternalBillDto.setPayDpFee(waybill.getToPayAmount());
				}
				//重量
				if(waybill.getGoodsWeightTotal() == null){
					ldpExternalBillDto.setWeight(BigDecimal.ZERO);
				}else{
					ldpExternalBillDto.setWeight(waybill.getGoodsWeightTotal());
				}
				//声明价值
				if(waybill.getInsuranceAmount() == null){
					ldpExternalBillDto.setDeclarationValue(BigDecimal.ZERO);
				}else{
					ldpExternalBillDto.setDeclarationValue(waybill.getInsuranceAmount());
				}
			}
			
		}
	}
	

	/**
	 * 生成外发单计算费用信息
	 * 
	 * @author ibm-liuzhaowei
	 * @param waybillDto
	 * @param ldpExternalBillDto
	 * @modify zhangzhanqin
	 */
	private void caculateFeeInfo(WaybillDto waybillDto, LdpExternalBillDto ldpExternalBillDto) {
		// 【到付运费】= 到付金额 - 代收货款
		ldpExternalBillDto.setPayDpFee(ldpExternalBillDto.getPayDpFee().subtract(ldpExternalBillDto.getCodAmount()));
		//【应收费用】= 到付运费 + 代收货款
		ldpExternalBillDto.setBillReceiveable(ldpExternalBillDto.getPayDpFee().add(ldpExternalBillDto.getCodAmount()));
		//定义调用接口的参数
		List<QueryPartBusinessPriceDto> dtoList = new ArrayList<QueryPartBusinessPriceDto>();
		//计算外发运费（ 参数：落地配公司编码，运单重量，计算运费的子类型）
		QueryPartBusinessPriceDto freightdto = new QueryPartBusinessPriceDto();
		freightdto.setWeight(ldpExternalBillDto.getWeight());
		freightdto.setSubType(PriceEntityConstants.PRICING_CODE_FRT);
		freightdto.setExpressPartbussCode(ldpExternalBillDto.getAgentCompanyCode());
		freightdto.setOuterBranchCode(ldpExternalBillDto.getAgentOrgCode());
		freightdto.setReceiveDate(ldpExternalBillDto.getBillTime());
		freightdto.setDistrictCode(ldpExternalBillDto.getDistrictCode());
		
		dtoList.add(freightdto);
		//计算代收货款手续费（ 参数：落地配公司编码，代收货款金额，计算代收货款的子类型）
		QueryPartBusinessPriceDto codAgencyFeedto = new QueryPartBusinessPriceDto();
		codAgencyFeedto.setOriginnalCost(ldpExternalBillDto.getCodAmount());
		codAgencyFeedto.setSubType(PriceEntityConstants.PRICING_CODE_HK);
		codAgencyFeedto.setExpressPartbussCode(ldpExternalBillDto.getAgentCompanyCode());
		codAgencyFeedto.setOuterBranchCode(ldpExternalBillDto.getAgentOrgCode());
		codAgencyFeedto.setReceiveDate(ldpExternalBillDto.getBillTime());
		codAgencyFeedto.setDistrictCode(ldpExternalBillDto.getDistrictCode());
		
		dtoList.add(codAgencyFeedto);
		//计算外发保价费（ 参数：落地配公司编码，声明价值，计算外发保价费的子类型）
		//如果运单对应的保费为零，则不需要计算外发保价费
		if(waybillDto == null || waybillDto.getWaybillEntity() == null || waybillDto.getWaybillEntity().getInsuranceFee() != BigDecimal.ZERO){
			QueryPartBusinessPriceDto externalInsuranceFeedto = new QueryPartBusinessPriceDto();
			externalInsuranceFeedto.setOriginnalCost(ldpExternalBillDto.getDeclarationValue());
			externalInsuranceFeedto.setSubType(PriceEntityConstants.PRICING_CODE_BF);
			externalInsuranceFeedto.setExpressPartbussCode(ldpExternalBillDto.getAgentCompanyCode());
			externalInsuranceFeedto.setOuterBranchCode(ldpExternalBillDto.getAgentOrgCode());
			externalInsuranceFeedto.setReceiveDate(ldpExternalBillDto.getBillTime());
			externalInsuranceFeedto.setDistrictCode(ldpExternalBillDto.getDistrictCode());
			
			dtoList.add(externalInsuranceFeedto);
		}
		//计算到付手续费
		if(waybillDto == null || waybillDto.getWaybillEntity() == null || waybillDto.getWaybillEntity().getInsuranceFee() !=BigDecimal.ZERO){
			QueryPartBusinessPriceDto billPayabledto = new QueryPartBusinessPriceDto();
			billPayabledto.setOriginnalCost(ldpExternalBillDto.getToPayFee());
			billPayabledto.setSubType(PriceEntityConstants.PRICING_CODE_FRTFREE);
			billPayabledto.setExpressPartbussCode(ldpExternalBillDto.getAgentCompanyCode());
			billPayabledto.setOuterBranchCode(ldpExternalBillDto.getAgentOrgCode());
			billPayabledto.setReceiveDate(ldpExternalBillDto.getBillTime());
			billPayabledto.setDistrictCode(ldpExternalBillDto.getDistrictCode());
			//需要将运单信息提供给接送货进行计算
			WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(waybillDto.getWaybillEntity().getWaybillNo());
			if(waybillEntity==null){
				throw new ExternalBillException("运单号：【"+waybillDto.getWaybillEntity().getWaybillNo()+"】不存在！");
				
			}
			//BillPayabledto.setWaybillEntity(waybillEntity);
			billPayabledto.setToPayAmount(waybillEntity.getToPayAmount());
			dtoList.add(billPayabledto);
		}
		
		
		//调用综合接口，返回的计算费用结果集合
		List<QueryPartBusinessPriceResultDto> resultList= partbussPriceCaculateService4Dubbo.caculateFee(dtoList);
		if(!CollectionUtils.isEmpty(resultList)){
			for(QueryPartBusinessPriceResultDto resultDto : resultList){
				//赋值代收货款手续费
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_HK, resultDto.getSubType())){
					ldpExternalBillDto.setCodAgencyFee(resultDto.getCaculateFee());
//				//赋值外发保价费
//				}else if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BF, resultDto.getSubType())){
//					ldpExternalBillDto.setExternalInsuranceFee(resultDto.getCaculateFee());
				//赋值外发运费
				}else if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, resultDto.getSubType())){
					ldpExternalBillDto.setFreightFee(resultDto.getCaculateFee());
				//赋值到付手续费
				}else if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRTFREE, resultDto.getSubType())){
					ldpExternalBillDto.setToPayFee(resultDto.getCaculateFee());
				}
			}
		}else{
			LOGGER.error("调用计算外发单费用接口返回信息为空！");
			throw new ExternalBillException("调用计算外发单费用接口返回信息为空！", "");
		}
		//sonar-352203
		initLdpExternalBillDtoFee(ldpExternalBillDto);
	}

	/**
	 * @param ldpExternalBillDto
	 */
	private void initLdpExternalBillDtoFee(LdpExternalBillDto ldpExternalBillDto) {
		if(ldpExternalBillDto.getCodAgencyFee() == null){
			ldpExternalBillDto.setCodAgencyFee(BigDecimal.ZERO);
		}
		if(ldpExternalBillDto.getExternalInsuranceFee() == null){
			ldpExternalBillDto.setExternalInsuranceFee(BigDecimal.ZERO);
		}
		if(ldpExternalBillDto.getFreightFee() == null){
			ldpExternalBillDto.setFreightFee(BigDecimal.ZERO);
		}
		if(ldpExternalBillDto.getToPayFee() == null){
			ldpExternalBillDto.setToPayFee(BigDecimal.ZERO);
		}
		
		//应付费用
		BigDecimal billPayable = BigDecimal.ZERO;
		//【应付费用】= 外发运费+保价费+代收货款手续费+快递代理返货费 + 到付手续费
		if(ldpExternalBillDto.getCodAgencyFee().compareTo(BigDecimal.ZERO) > 0){
			billPayable = billPayable.add(ldpExternalBillDto.getCodAgencyFee());   //代收货款手续费
		}
//		if(ldpExternalBillDto.getExternalInsuranceFee().compareTo(BigDecimal.ZERO) > 0){
//			billPayable = billPayable.add(ldpExternalBillDto.getExternalInsuranceFee());  //保价费
//		}
		if(ldpExternalBillDto.getFreightFee().compareTo(BigDecimal.ZERO) > 0){
			billPayable = billPayable.add(ldpExternalBillDto.getFreightFee());  //外发运费
		}
		if(ldpExternalBillDto.getToPayFee().compareTo(BigDecimal.ZERO) > 0){
			billPayable = billPayable.add(ldpExternalBillDto.getToPayFee());   //到付手续费
		}
		ldpExternalBillDto.setBillPayable(billPayable);
	}

	/**
	 * 更新落地配外发单，并同步结算数据
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:39:06
	 * @update-author 269701--lln
	 * @update-date 2015-10-26 上午 11:29:30
	 * @update-comment 一票多件时，更新落地配外发单，并同步结算数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public LdpExternalBillDto updateLdpExternalBill(LdpExternalBillDto ldpExternalBillDto, CurrentInfo currentInfo) throws ExternalBillException {

		if (ldpExternalBillDto != null) {
			//备注
			String note = ldpExternalBillDto.getNotes();
			//是否中转外发
			String transferExternal = ldpExternalBillDto.getTransferExternal();
			//应付费用
			BigDecimal billPayable = ldpExternalBillDto.getBillPayable();
			//外发运费（德邦付给代理）
			BigDecimal freightFee = ldpExternalBillDto.getFreightFee();
			//快递代理返货费
			BigDecimal agencyReturnFee = ldpExternalBillDto.getAgencyReturnFee();
			//根据ID主键查询落地配外发单
			ldpExternalBillDto = ldpExternalBillDao.queryByPrimaryKey(ldpExternalBillDto.getId());
			
			if (StringUtils.equals(FossConstants.NO, transferExternal)
					&& StringUtils.equals(FossConstants.YES,
							ldpExternalBillDto.getTransferExternal())) {
				throw new ExternalBillException("中转外发不允许改为非中转外发", "");
			}
			
			//当是否中转外发由否改为是时，更新中间表中的isAdd字段为N
			if(StringUtils.equals(FossConstants.NO,ldpExternalBillDto.getTransferExternal())
					&& StringUtils.equals(FossConstants.YES, transferExternal)) {
				//需求变更前--流水号是固定的
				//ldpExternalBillDao.updateIsAdd(ldpExternalBillDto.getWaybillNo(),"0001");
				//需求变更后--一票多件时，流水号需要改动
				ldpExternalBillDao.updateIsAdd(ldpExternalBillDto.getWaybillNo(),ldpExternalBillDto.getSerialNo());
			}
			ldpExternalBillFeeDataConverting(ldpExternalBillDto);
			//快递代理返货费
			ldpExternalBillDto.setAgencyReturnFee(agencyReturnFee);
			//外发运费（德邦付给代理）
			ldpExternalBillDto.setFreightFee(freightFee);
			//应付费用
			ldpExternalBillDto.setBillPayable(billPayable);
			//备注
			ldpExternalBillDto.setNotes(note);
			//是否中转外发
			ldpExternalBillDto.setTransferExternal(transferExternal);
			
			// 根据运单号查询运单信息
			WaybillDto waybillDto = null;
			try {
				waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
			} catch (Exception e) {
				LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
				throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, e);
			}
			
			//一票多件需求变更后--根据运单号和流水号进行判断签收
			checkSignStatus(ldpExternalBillDto.getWaybillNo(),ldpExternalBillDto.getSerialNo());
			if (waybillDto != null) {
				// 设置修改时间
				ldpExternalBillDto.setModifyTime(new Date());
				ldpExternalBillDto.setModifyUserCode(currentInfo.getEmpCode());
				ldpExternalBillDto.setModifyUserName(currentInfo.getEmpName());
				
				//如果中转外发为是，则“代收货款手续费”、“保价费”、“代收货款”、“到付运费”，“应收费用”都设置为0
				if(StringUtils.equals(PartiallineConstants.IS_TRANSFER_EXTERNAL_YES, ldpExternalBillDto.getTransferExternal())){
					// 代收货款手续费
					ldpExternalBillDto.setCodAgencyFee(BigDecimal.ZERO);
					// 保价费
					ldpExternalBillDto.setExternalInsuranceFee(BigDecimal.ZERO);
					// 应收费用
					ldpExternalBillDto.setBillReceiveable(BigDecimal.ZERO);
					//到付运费
					ldpExternalBillDto.setPayDpFee(BigDecimal.ZERO);
					//代收货款
					ldpExternalBillDto.setCodAmount(BigDecimal.ZERO);
					//到付手续费
					ldpExternalBillDto.setToPayFee(BigDecimal.ZERO);
					// 结算系统数据更新
					SettlementLdpExternalBillDto stlDto = new SettlementLdpExternalBillDto();
					// 拷贝数据
					copyProperties(stlDto, ldpExternalBillDto, waybillDto);
					// 结算更新
					vehicleAgencyExternalLdpService.modifyExternalBill(stlDto, currentInfo);
				}
				
//				根据ID主键，更新落地配外发单记录数据
				ldpExternalBillDao.updateByPrimaryKey(ldpExternalBillDto);
			} else {
				// 未查询到对应的运单
				throw new ExternalBillException("未查询到对应的运单", "");
			}
		}
		
		return ldpExternalBillDto;
	}

	/**
	 * 拷贝数据
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:05:52
	 */
	private void copyProperties(SettlementLdpExternalBillDto stlDto, LdpExternalBillDto ldpExternalBillDto, WaybillDto waybillDto) {
		if (stlDto != null && ldpExternalBillDto != null) {
			LOGGER.info("拷贝数据:" + ldpExternalBillDto.toString());
			// 运单号
			stlDto.setWaybillNo(ldpExternalBillDto.getWaybillNo());
			// 根据运单号查询运单信息
			if (waybillDto != null) {
				WaybillEntity waybill = waybillDto.getWaybillEntity();
				// 付款方式
				if (waybill != null) {
					stlDto.setPaidMethod(waybill.getPaidMethod());
					LOGGER.info("付款方式" + waybill.getPaidMethod());
				} else {
					throw new ExternalBillException("没有查询到对应的运单信息", "");
				}
				// --补充运单信息
				// 总费用
				stlDto.setTotalFee(waybill.getTotalFee());
				// 到达部门编码
				stlDto.setLastLoadOrgCode(waybill.getLastLoadOrgCode());
				// 出发部门编码
				stlDto.setReceiveOrgCode(waybill.getReceiveOrgCode());
				// waybillId
				stlDto.setWaybillId(waybill.getId());
			} else {
				throw new ExternalBillException("没有查询到对应的运单信息", "");
			}

			// 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(ldpExternalBillDto.getExternalOrgCode());
			// 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			if (transCenter != null && StringUtils.isNotBlank(transCenter.getCode())
					&& FossConstants.ACTIVE.equals(transCenter.getTransferCenter())) {
				LOGGER.info("外发员查找到对应的外场,外场编码:" + transCenter.getCode() + ",名称:" + transCenter.getName());
				// 外发部门//外发员所在的部门//外发部门修改为外发员所属的外场，如果找不到外场，则报错
				stlDto.setWaifabumen(transCenter.getCode());
				// 外发部门名称//外发员所在的部门
				stlDto.setWaifabumenName(transCenter.getName());
			} else {
				LOGGER.info("外发员没有查找到对应的外场,外发部门编码:" + ldpExternalBillDto.getExternalOrgCode());
				throw new ExternalBillException("外发员没有查找到对应的外场,请选择正确的外发员", "");
			}
			// 外发单号
			stlDto.setExternalBillNo(ldpExternalBillDto.getExternalBillNo());
			// 外发运费
			stlDto.setExternalAgencyFee(ldpExternalBillDto.getFreightFee());
			// 保价费
			stlDto.setExternalInsuranceFee(ldpExternalBillDto.getExternalInsuranceFee());
			// 代收货款手续费
			stlDto.setCodAgencyFee(ldpExternalBillDto.getCodAgencyFee());
			// 应付（成本总额）
			stlDto.setCostAmount(ldpExternalBillDto.getBillPayable());
			// 代收货款费用
			stlDto.setCodAmount(ldpExternalBillDto.getCodAmount());
			// 审核状态（数值型）
			stlDto.setAuditStatus(ldpExternalBillDto.getAuditStatus());
			// 落地配代理编号
			stlDto.setAgentCompanyCode(ldpExternalBillDto.getAgentCompanyCode());
			// 落地配代理名称
			stlDto.setAgentCompanyName(ldpExternalBillDto.getAgentCompanyName());
			// 落地配网点编号
			stlDto.setAgentOrgCode(ldpExternalBillDto.getAgentOrgCode());
			// 是否中转外发
			stlDto.setTransferExternal(ldpExternalBillDto.getTransferExternal());
			// 币种
			stlDto.setCurrencyCode(ldpExternalBillDto.getCurrencyCode());
			// 修改日期
			stlDto.setBusinessDate(ldpExternalBillDto.getModifyTime());
			// 录入日期
			stlDto.setCreateTime(ldpExternalBillDto.getRegisterTime());
		}

	}

	/**
	 * 
	 * 根据运单号查询运单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:21:14
	 */
	@Override
	public LdpExternalBillWayBillInfoDto queryWaybillInfo(LdpExternalBillDto ldpExternalBillDto, String validateWaybillNo,
			CurrentInfo currentInfo) {
		if (ldpExternalBillDto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName() + ",部门编码:" + currentInfo.getCurrentDeptCode());
			ldpExternalBillDto.setFilterOrgCode(currentInfo.getCurrentDeptCode());
		}
		LdpExternalBillWayBillInfoDto billInfo = null;
		if (ldpExternalBillDto != null) {
			// 落地配类型
			ldpExternalBillDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
			// 验证运单号(录入落地配外发情况)，需要查询最新的交接单号
			if (PartiallineConstants.VALIDATE_YES.equals(validateWaybillNo)) {
				// 根据运单号取最新的交接单明细信息，获取并设置交接单号
				queryLastHandoverBillDetail(ldpExternalBillDto,currentInfo);
				// 执行验证运单号(录入落地配外发情况)，需要查询最新的交接单号
				validateQueryWaybillInfo(ldpExternalBillDto);
			}
			/***
			 * 1、根据运单号，找结算获取付款方式、 到付金额、 目的站、 收货日期、自提等运单信息
			 * 2、根据运单号，到运单获取提货网点code，再跟这个code找综合接口获取外发代理、
			 * 到达网点、到达网点电话、到达网点地址、代理电话
			 ***/
			WaybillDto waybillDto = null;
			try {
				waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
			} catch (Exception e) {
				LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
				throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, "");
			}
			// 查询到运单信息
			//sonar-352203
			if (waybillDto == null) {
				LOGGER.info("没有查询到对应的运单信息,运单号:" + ldpExternalBillDto.getWaybillNo());
				throw new ExternalBillException("没有查询到对应的运单信息", "");
			}
				WaybillEntity waybill = waybillDto.getWaybillEntity();
				//sonar-352203
				if (waybill == null) {
					LOGGER.info("没有查询到对应的运单信息,运单号:" + ldpExternalBillDto.getWaybillNo());
					throw new ExternalBillException("没有查询到对应的运单信息", "");
				}
					//modified by 257220 
					//子母件重量体积信息优先从录入的信息中获取
					InputWeightVolumnEntity inputWeightVolumnEntity = inputWeightVolumnService.queryInputWeightVolumnByWaybillNo(waybill.getWaybillNo(),ldpExternalBillDto.getSerialNo());
					if(inputWeightVolumnEntity != null){
						waybillDto.getWaybillEntity().setGoodsWeightTotal(inputWeightVolumnEntity.getWeight());
						waybillDto.getWaybillEntity().setGoodsVolumeTotal(inputWeightVolumnEntity.getVolumn());
					}
					// 用于存储运单信息
					billInfo = new LdpExternalBillWayBillInfoDto();
					// 根据外发代理编号查询,外发代理,到达网点,到达网点电话,到达网点地址,代理电话
					// 提货网点CODE
					String agencyBranchCode = waybill.getCustomerPickupOrgCode();
					OuterBranchExpressEntity companyDto = null;
					if (StringUtils.isNotBlank(agencyBranchCode)) {
						// 代理信息Dto
//						companyDto = vehicleAgencyDeptService.queryAgencyBranchCompanyInfo(agencyBranchCode);
						companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(agencyBranchCode, FossConstants.ACTIVE);
					}
					if (companyDto != null) {
						// 到达网点名称
						if (StringUtils.isNotBlank(companyDto.getAgentDeptName())) {
							billInfo.setAgentDeptName(companyDto.getAgentDeptName());
						}
						// 到达网点电话
						if (StringUtils.isNotBlank(companyDto.getContactPhone()) || StringUtils.isNotBlank(companyDto.getMobilePhone())) {
							if(StringUtils.isNotBlank(companyDto.getMobilePhone())){
								billInfo.setContactPhone(companyDto.getMobilePhone());
							}else{
								billInfo.setContactPhone(companyDto.getContactPhone());
							}
						}
						// 到达网点地址
						if (StringUtils.isNotBlank(companyDto.getAddress())) {
							billInfo.setAddress(companyDto.getAddress());
						}
					} else {
						LOGGER.info("没有查询到对应的运单的代理信息,提货网点CODE:" + agencyBranchCode);
						throw new ExternalBillException("没有查询到对应的运单的代理信息", "");
					}
					// 目的站
					billInfo.setTargetOrgCode(waybill.getTargetOrgCode());
					// 提货方式（是否自提）
					billInfo.setReceiveMethod(waybill.getReceiveMethod());
					// 收货日期
					if(waybill.getCreateTime() != null){
						billInfo.setBillTime(DateUtils.convert(waybill.getCreateTime(), DateUtils.DATE_TIME_FORMAT));
					}
					// 预计到达时间
					if(waybill.getPreArriveTime() != null){
						billInfo.setPreArriveTime(DateUtils.convert(waybill.getPreArriveTime(), DateUtils.DATE_TIME_FORMAT));
					}
					// 收货部门
					if (StringUtils.isNotBlank(waybill.getCreateOrgCode())) {
						OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(waybill.getCreateOrgCode());
						if (org != null) {
							billInfo.setCreateOrgCode(org.getName());
						}
					}
					// 运输性质
					if(waybill.getProductCode() != null){
						billInfo.setProductCode(productService4Dubbo.getProductByCache(waybillDto.getWaybillEntity().getProductCode(), null).getName());
					}
					// 提货方式
					if(waybill.getReceiveMethod() != null){
						String receiveMethodDesc = tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS, waybill.getReceiveMethod());
						if(StringUtils.isBlank(receiveMethodDesc)){
							receiveMethodDesc = tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS_AIR, waybill.getReceiveMethod());
						}
						billInfo.setReceiveMethod(receiveMethodDesc);
					}
					// 返单类别
					if(waybill.getReturnBillType() != null){
						billInfo.setReturnBillType(DictUtil.rendererSubmitToDisplay(waybill.getReturnBillType(),
								PartiallineConstants.WAYBILL_RETURNBILL_TYPE));
					}
					// 运费
					billInfo.setTransportFee(waybill.getTransportFee());
					// 付款方式
					if (StringUtils.isNotBlank(waybill.getPaidMethod())) {
						billInfo.setPaidMethodCode(waybill.getPaidMethod());
						// 付款方式中文
						billInfo.setPaidMethod(DictUtil.rendererSubmitToDisplay(waybill.getPaidMethod(),
								PartiallineConstants.SETTLEMENT__PAYMENT_TYPE));
					}
					// 代收货款费率
					billInfo.setCodRate(waybill.getCodRate());
					// 保险费
					billInfo.setInsuranceFee(waybill.getInsuranceFee());
					// 保险价值
					billInfo.setInsuranceAmount(waybill.getInsuranceAmount());
					// 寄件单位
					billInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
					// 收件单位
					billInfo.setReceiveCustomerName(waybill.getReceiveCustomerName());
					// 收货客户联系人
					billInfo.setReceiveCustomerContact(waybill.getReceiveCustomerContact());
					// 收货联系方式
					String phoneNum = waybill.getReceiveCustomerMobilephone();
					if(StringUtils.isBlank(phoneNum)){
						phoneNum = waybill.getReceiveCustomerPhone();
					}
					billInfo.setReceiveCustomerPhone(phoneNum);
					// 收货详细地址
					
					/*String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(),
							waybill.getReceiveCustomerCityCode(), waybill.getReceiveCustomerDistCode(), 
							waybill.getReceiveCustomerAddress());*/
					//调用新的地址拼接接口
					String receiveCustomerAddress = waybillManagerService.getReceivingAddress(waybill.getWaybillNo());
					
					billInfo.setReceiveCustomerAddress(receiveCustomerAddress);
					// 重量
					billInfo.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
					// 体积
					billInfo.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
					// 件数
					if (waybill.getGoodsQtyTotal() != null) {
						billInfo.setGoodsQtyTotal(BigDecimal.valueOf(waybill.getGoodsQtyTotal()));
					}
					// 货物名称
					billInfo.setGoodsName(waybill.getGoodsName());

//				} else {
//					LOGGER.info("没有查询到对应的运单信息,运单号:" + ldpExternalBillDto.getWaybillNo());
//					throw new ExternalBillException("没有查询到对应的运单信息", "");
//				}
//			} else {
//				LOGGER.info("没有查询到对应的运单信息,运单号:" + ldpExternalBillDto.getWaybillNo());
//				throw new ExternalBillException("没有查询到对应的运单信息", "");
//			}
		}
		return billInfo;
	}

	/**
	 * 验证运单号已经做过交接，且未录入落地配外发单；以及外发单未被使用
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:56:39
	 * @update-author 269701--lln
	 * @update-date 2015-10-25 下午 10:15:20
	 * @update-comment 验证运单号已经做过交接，且未录入落地配外发单；以及外发单未被使用,条件追加流水号
	 */
	private void validateAddExternalBill(LdpExternalBillDto ldpExternalBillDto) throws ExternalBillException {
		LOGGER.info("校验运单号是否做过交接的快递代理运单，如果本运单号未做过交接，则提示错误消息");
		// 校验运单号是否做过交接的落地配运单，如果本运单号未做过交接，则提示错误消息
		hasWaybillNoHanded(ldpExternalBillDto);
		// 校验本运单号是否已经被录入了落地配外发单，如果已经录入，则提示错误消息
		LOGGER.info("校验该流水号是否已经被录入了快递代理外发单，如果已经录入，则提示错误消息");
		hasSerialNoInputed(ldpExternalBillDto);
		// 验证外发单号未录入过，如果外发单已经录入过，则返回错误提示信息
		//LOGGER.info(" 验证外发单号未录入过，如果外发单已经录入过，则返回错误提示信息");
		//hasExternalBillNoInputed(ldpExternalBillDto);
	}

	/**
	 * 验证查询的运单号
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:43:52
	 */
	private void validateQueryWaybillInfo(LdpExternalBillDto ldpExternalBillDto) throws ExternalBillException {

		// 通过运单号,查询是否存在(1.落地配 2.已经做过交接)的外发单交接单明细，如果本运单号未做过交接，则提示错误消息
		hasWaybillNoHanded(ldpExternalBillDto);
		// 验证运单号未录入过，如果运单号已经录入过，则返回错误提示信息
		hasWaybillNoInputed(ldpExternalBillDto);
	}

	/**
	 * 通过运单号,查询是否存在(1.落地配 2.已经做过交接)的外发单交接单明细
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:43:26
	 * @update-author 269701--lln
	 * @update-date 2015-10-25 上午 10:10:10
	 * @update-comment 通过运单号和流水号,查询是否存在(1.落地配 2.已经做过交接)的外发单交接单明细
	 * 
	 */
	private void hasWaybillNoHanded(LdpExternalBillDto ldpExternalBillDto) throws ExternalBillException {
		if (ldpExternalBillDto != null) {
			// 非已预配、非作废状态
			List<Integer> billStatuslist = new ArrayList<Integer>();
			// 20：已交接，
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
			// (集配交接单专属状态，21：已配载)
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
			// 30：已出发，
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
			// 40：已到达，
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
			// 50：已入库
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);

			ldpExternalBillDto.setBillStatuslist(billStatuslist);
			// 查询
			Long handCnt = ldpExternalBillDao.queryCount(ldpExternalBillDto, "queryWaybillNoHasHanded");
			// 如果运单号未交接
			if (handCnt < PartiallineConstants.LIST_SIZE_ONE) {
				throw new ExternalBillException(PartiallineConstants.EXCEPTION_WAYBILL_HAS_NOT_HANDED);
			}
		}

	}

	/**
	 * 验证运单号是否录入过，如果运单已经录入过，则返回错误提示信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:42:38
	 */
	private void hasWaybillNoInputed(LdpExternalBillDto ldpExternalBillDto) throws ExternalBillException {
		// 根据运单号查询已录入列表
		List<LdpExternalBillDto> inputedList = ldpExternalBillDao.queryByWaybillNo(ldpExternalBillDto);
		// 如果运单号已经被录入过
		if (CollectionUtils.isNotEmpty(inputedList)) {
			// 如果本运单已经录入过，则需要遍历是否全部为外发退回情况；如果对应的外发单全部为外发退回，则可以通过校验
			boolean flag = false;
			for (LdpExternalBillDto dto : inputedList) {
				// 如果存在非作废且非中转外发，则不允许录入, 抛出异常
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto.getTransferExternal())
						&& !PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID.equals(dto.getAuditStatus())) {
					flag = true;
					break;
				}
			}
			if (flag) {
				throw new ExternalBillException(PartiallineConstants.EXCEPTION_WAYBILL_HAS_INPUTED);
			}
		}
	}

	/**
	 * 验证流水号是否录入过，如果流水号已经录入过，则返回错误提示信息
	 * 
	 * @author 269701--lln
	 * @date 2015-10-25 上午 10:54:50
	 * 
	 */
	private void hasSerialNoInputed(LdpExternalBillDto ldpExternalBillDto) throws ExternalBillException {
		// 需求变更前--根据运单号查询已录入列表
		//一票多件需求变更后--根据流水号查询已录入列表
		List<LdpExternalBillDto> inputedList = ldpExternalBillDao.queryByWaybillNo(ldpExternalBillDto);
		// 如果流水号已经被录入过
		if (CollectionUtils.isNotEmpty(inputedList)) {
			// 如果本运单已经录入过，则需要遍历是否全部为外发退回情况；如果对应的外发单全部为外发退回，则可以通过校验
			boolean flag = false;
			for (LdpExternalBillDto dto : inputedList) {
				// 如果存在非作废且非中转外发，则不允许录入, 抛出异常
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto.getTransferExternal())
						&& !PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID.equals(dto.getAuditStatus())) {
					flag = true;
					break;
				}
			}
			if (flag) {
				//流水号已经录入
				throw new ExternalBillException(PartiallineConstants.EXCEPTION_SERIALNO_HAS_INPUTED);
			}
		}
	}
	
	/**
	 * 审核落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 上午11:48:48
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int auditLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException, InvocationTargetException {
		// 查询审核的外发单列表，提供给结算系统
		List<LdpExternalBillDto> auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementLdpExternalBillDto> stlDtos = new ArrayList<SettlementLdpExternalBillDto>();
			// 循环拷贝
			SettlementLdpExternalBillDto stlDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (LdpExternalBillDto ldpExternalBillDto : auditList) {
				ldpExternalBillFeeDataConverting(ldpExternalBillDto);
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(ldpExternalBillDto.getAuditStatus())) {
					//269701--lln--2015-10-27 
					//抛错信息：流水号
					throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo() +",流水号: "+ldpExternalBillDto.getSerialNo()+ ",外发单已审核,请核实再操作", "");
				}
				stlDto = new SettlementLdpExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				
				//BUGKD-1502审核放开此限制，2013-10-15
				//checkSignStatus(ldpExternalBillDto.getWaybillNo());
				//stlDto.
				if (waybillDto != null) {
					// 拷贝数据
					copyProperties(stlDto, ldpExternalBillDto, waybillDto);
					stlDtos.add(stlDto);
				}

			}
			// 结算审核
			vehicleAgencyExternalLdpService.auditExternalBill(stlDtos, currentInfo);
		}
		// 再次查询，防止并发情况,做校验
		auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (LdpExternalBillDto ldpExternalBillDto : auditList) {
			// 如果存在不满足的情况，则改变标记,非待审核或单审核，则不允许审核操作
			if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(ldpExternalBillDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID.equals(ldpExternalBillDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNKNOWN.equals(ldpExternalBillDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不是待审核状态的外发单,请核实再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“已审核”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED;
			// 执行更新
			ldpExternalBillDao.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
		}

		//轨迹信息
		//非中转外发单审核
		for(LdpExternalBillDto dto : auditList) {
			if(StringUtils.equals(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO, dto.getTransferExternal())) {
				try{
					WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
					trackDto.setWaybillNo(dto.getWaybillNo());
					trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE);
					trackDto.setOperateDeptCode(currentInfo.getCurrentDeptCode()); 
					trackDto.setOperateDeptName(currentInfo.getCurrentDeptName()); 
					trackDto.setOperateTime(new Date());
					trackDto.setOperatorName(currentInfo.getEmpName());
					
					waybillTrackingsService.addOneWaybillTrack(trackDto);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 反审核落地配
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 下午1:44:54
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deAuditLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException {

		// 查询反审核的外发单列表，提供给结算系统
		List<LdpExternalBillDto> auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementLdpExternalBillDto> stlDtos = new ArrayList<SettlementLdpExternalBillDto>();
			// 循环拷贝
			SettlementLdpExternalBillDto stlDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (LdpExternalBillDto ldpExternalBillDto : auditList) {
				// 待审核状态，不能反审核
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT.equals(ldpExternalBillDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo() + ",外发单为待审核,不能翻审核,请核实再操作", "");
				}
				// 已经为反审核状态，不能再做反审核
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT.equals(ldpExternalBillDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo() + ",外发单已为反审核状态,请核实再操作", "");
				}
				stlDto = new SettlementLdpExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				if (waybillDto != null) {
					checkSignStatus(ldpExternalBillDto.getWaybillNo(),ldpExternalBillDto.getSerialNo());
					// 拷贝数据
					copyProperties(stlDto, ldpExternalBillDto, waybillDto);
					stlDtos.add(stlDto);
				}

			}
			// 结算反审核
			vehicleAgencyExternalLdpService.reverseAuditExternalBill(stlDtos, currentInfo);
		}
		// 再次查询查询，防止并发情况,做校验
		auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (LdpExternalBillDto ldpExternalBillDto : auditList) {
			// 如果存在不满足的情况，则改变标记
			if (!PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(ldpExternalBillDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不是已审核状态的外发单,请核实再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“反审核”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT;
			// 执行更新
			ldpExternalBillDao.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
		}

		//新增轨迹
		//非中转外发单反审核
		for(LdpExternalBillDto dto : auditList) {
			if(StringUtils.equals(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO, dto.getTransferExternal()))
			try{
				WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
				trackDto.setWaybillNo(dto.getWaybillNo());
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE_DEAUDIT);
				trackDto.setOperateDeptCode(currentInfo.getCurrentDeptCode()); 
				trackDto.setOperateDeptName(currentInfo.getCurrentDeptName()); 
				trackDto.setOperateTime(new Date());
				trackDto.setOperatorName(currentInfo.getEmpName());
				
				waybillTrackingsService.addOneWaybillTrack(trackDto);
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 作废落地配
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 下午1:44:54
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int invalideLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException {

		// 查询作废的外发单列表，提供给结算系统
		List<LdpExternalBillDto> auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementLdpExternalBillDto> stlDtos = new ArrayList<SettlementLdpExternalBillDto>();
			// 循环拷贝
			SettlementLdpExternalBillDto stlDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (LdpExternalBillDto ldpExternalBillDto : auditList) {
				ldpExternalBillFeeDataConverting(ldpExternalBillDto);
				// 已作废，则不能再作废
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID.equals(ldpExternalBillDto.getAuditStatus())) {
					//269701--lln
					//异常信息：新增流水号
					throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo() +"流水号： "
								+ldpExternalBillDto.getSerialNo()+ ",外发单已作废,请核实再操作", "");
					}
				
//				中转外发，若为“是”则不允许作废 ISSUE-4530
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_YES.equals(ldpExternalBillDto.getTransferExternal())) {
					//269701--lln
					//异常信息：新增流水号
					throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo()+",流水号： "+ldpExternalBillDto.getSerialNo()+ ", 外发单不能作废,此单中转外发状态已标记为【是】", "");
				}
				
				stlDto = new SettlementLdpExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(ldpExternalBillDto.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				if (waybillDto != null) {
					//一票多件需求变更后--需要根据流水号进行判断签收
					checkSignStatus(ldpExternalBillDto.getWaybillNo(),ldpExternalBillDto.getSerialNo());
					// 拷贝数据
					copyProperties(stlDto, ldpExternalBillDto, waybillDto);
					stlDtos.add(stlDto);
				}

			}
			// 结算作废
			vehicleAgencyExternalLdpService.disableExternalBill(stlDtos, currentInfo);
		}
		// 再次查询查询，防止并发情况,做校验
		auditList = ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (LdpExternalBillDto ldpExternalBillDto : auditList) {
			// 如果状态为作废或者已审核状态，则不能进行作废，则改变标记
			if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID.equals(ldpExternalBillDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(ldpExternalBillDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不能作废的外发单,请刷新后,再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“已作废”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID;
			// 执行更新
			ldpExternalBillDao.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
		}
		//当是否中转外发由否改为是时，更新中间表中的isAdd字段为N
		for(LdpExternalBillDto dto: auditList) {
			//需求变更前   一票多件时，流水号需要改动
			//ldpExternalBillDao.updateIsAdd(dto.getWaybillNo(),"0001");
			//需求变更后--一票多件时，流水号为实际流水号
			ldpExternalBillDao.updateIsAdd(dto.getWaybillNo(),dto.getSerialNo());
		}
		
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 根据运单号取最新的交接单明细信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午2:56:03
	 */
	private void queryLastHandoverBillDetail(LdpExternalBillDto ldpExternalBillDto, CurrentInfo currentInfo) {

		if (ldpExternalBillDto != null) {
			// 交接明细
			LdpHandoverBillDetailDto detailDto = new LdpHandoverBillDetailDto();
			// 运单号
			detailDto.setWaybillNo(ldpExternalBillDto.getWaybillNo());
			//269701--lln--2015/10/24--begin
			//流水号
			detailDto.setSerialNo(ldpExternalBillDto.getSerialNo());
			//269701--lln--2015/10/24--end
			// 设置查询部门
			if (currentInfo != null) {
				LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName() + ",部门编码:" + currentInfo.getCurrentDeptCode());				
				detailDto.setOrgCodes(queryTransCenterChildrenCodes(currentInfo.getCurrentDeptCode()));
			}
			LOGGER.info("根据运单号和流水号取最新的交接单明细信息,运单号:" + ldpExternalBillDto.getWaybillNo()
					+",流水号:"+ldpExternalBillDto.getSerialNo());
			//查询未录入的交接明细--追加查询条件-流水号
			LdpHandoverBillDetailDto hdetail = uninputLdpExternalBillDao.queryLastHandoverBillDetail(detailDto);
			// 获取交接单号
			if (hdetail != null && StringUtils.isNotBlank(hdetail.getHandoverNo())) {
				ldpExternalBillDto.setHandoverNo(hdetail.getHandoverNo());
				//通过交接单号获取交接单信息，且获得交接单提交日期
				HandOverBillEntity handOverBillEntity = handOverBillService.queryHandOverBillByNo(hdetail.getHandoverNo());
				ldpExternalBillDto.setBillTime(handOverBillEntity.getHandOverTime());
				ldpExternalBillDto.setExternalOrgName(handOverBillEntity.getDepartDept());
				ldpExternalBillDto.setExternalOrgCode(handOverBillEntity.getDepartDeptCode());
				
				LOGGER.info("根据运单号取最新的交接单明细信息,交接单号:" + hdetail.getHandoverNo());
			} else {
				LOGGER.error("运单号:" + ldpExternalBillDto.getWaybillNo()+",流水号: " +ldpExternalBillDto.getSerialNo()+ ",没有找到相关的交接单号,未作交接或已经录入,请核实再操作");
				throw new ExternalBillException("运单号:" + ldpExternalBillDto.getWaybillNo() +",流水号: " +ldpExternalBillDto.getSerialNo()+ ",没有找到相关的交接单号,未作交接或已经录入,请核实再操作", "");
			}
		}
	}

	/**
	 * 根据运单号查询外发单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:39:54
	 */
	@Override
	public LdpExternalBillDto queryLdpExternalBillByWaybillNo(LdpExternalBillDto ldpExternalBillDto) {
		// 验证运单号码
		if (ldpExternalBillDto == null) {
			throw new ExternalBillException("查询参数为空", "");
		} else {
			if (StringUtils.isBlank(ldpExternalBillDto.getWaybillNo())) {
				throw new ExternalBillException("运单号为空", "");
			}
		}
		return ldpExternalBillDao.queryLdpExternalBillByWaybillNo(ldpExternalBillDto);
	}

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单(用于更改单的查询)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:57:54
	 */
	@Override
	public LdpExternalBillDto queryLdpExternalBillByWaybillNoForChange(LdpExternalBillDto ldpExternalBillDto) {
		// 验证运单号码
		if (ldpExternalBillDto == null) {
			throw new ExternalBillException("查询参数为空", "");
		} else {
			if (StringUtils.isBlank(ldpExternalBillDto.getWaybillNo())) {
				throw new ExternalBillException("运单号为空", "");
			}
		}
		
		LdpExternalBillDto ldpDto = ldpExternalBillDao.queryLdpExternalBillByWaybillNoForChange(ldpExternalBillDto);
		ldpExternalBillFeeDataConverting(ldpDto);
		
		return ldpDto;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 导出落地配外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * 
	 * @date 2013-07-17 上午9:25:38
	 * 
	 */
	@Override
	public InputStream exportLdpExternalBill(List<LdpExternalBillDto> ldpExternalBillList, LdpExternalBillVo vo) {
		// 导出文件流
		InputStream excelStream = null;
		// 定义表头
		String[] rowHeads = PartiallineConstants.LDP_ROW_HEADS;

		SheetData sheetData = new SheetData();
		// 头数据
		sheetData.setRowHeads(rowHeads);
		// 查询出的所有数据
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 循环转换
		List<String> columnList = null;
		// 判空
		if (CollectionUtils.isNotEmpty(ldpExternalBillList)) {
			// 循环转换
			for (LdpExternalBillDto dto : ldpExternalBillList) {
				ldpExternalBillFeeDataConverting(dto);
				columnList = new ArrayList<String>();
				// 外发流水号
				//columnList.add(dto.getId());
				// 运单号
				columnList.add(dto.getWaybillNo());
				//added by 257220  一票多件外发
				//流水号
				columnList.add(dto.getSerialNo());
				//代理单号
				columnList.add(dto.getAgencyWaybillNo());
				// 落地配公司
				columnList.add(dto.getAgentCompanyName());
				// 落地配公司编码
				columnList.add(dto.getAgentCompanyCode());
				// 到付金额
				columnList.add(String.valueOf(dto.getPayDpFee()));
				// 代收货款
				columnList.add(String.valueOf(dto.getCodAmount()));
				// 应收费用
				columnList.add(String.valueOf(dto.getBillReceiveable()));
				//外发运费
				columnList.add(String.valueOf(dto.getBillPayable()));
				// 返单类型 
				columnList.add(tfrCommonService.queryDictNameByCode(DictionaryConstants.RETURN_BILL_TYPE, dto.getReturnType()));
				// 外发员
				columnList.add(dto.getExternalUserName());
				// 外发员工号
				columnList.add(dto.getExternalUserCode());
				// 外发部门名称
				columnList.add(dto.getExternalOrgName());
				// 外发部门编码
				columnList.add(dto.getExternalOrgCode());
				// 生成时间
				columnList.add(DateUtils.convert(dto.getRegisterTime(), DateUtils.DATE_TIME_FORMAT));
				// 币种
				columnList.add(dto.getCurrencyCode());
				
				// 付款方式
				columnList.add(dto.getPayType());
				// 提货方式
				columnList.add(dto.getPickupType());
				// 收货联系人姓名
				columnList.add(dto.getReceiveName());
				// 收货客户地址(改成新的收货地址)
				String recieveAddress=waybillManagerService.getReceivingAddress(dto.getWaybillNo());
				columnList.add(recieveAddress);
				//columnList.add(dto.getReceiveAddr());
				// 收货客户(公司)
				columnList.add(dto.getReceiveCompany());
				// 收货联系人电话
				columnList.add(dto.getReceivePhone());
				// 货物声明价值
				columnList.add(String.valueOf(dto.getDeclarationValue()));
				
				// 件数
				columnList.add(String.valueOf(dto.getGoodsNum()));
				// 重量
				columnList.add(String.valueOf(dto.getWeight()));
				// 体积
				columnList.add(String.valueOf(dto.getVolume()));
				// 备注
				columnList.add(dto.getNotes());
				// 外发单状态
				columnList.add(dto.getAuditStatus());
				// 修改人
				columnList.add(dto.getModifyUserName());
				// 修改时间
				columnList.add(DateUtils.convert(dto.getModifyTime(), DateUtils.DATE_TIME_FORMAT));

                columnList.add(dto.getReceiveCustomerAddress());
				// 加入列表
				rowList.add(columnList);
			}
		}
		sheetData.setRowList(rowList);
		// 导出工具
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData,
				PartiallineConstants.EXPORT_LDP_EXTERNALBILL_SHEET_NAME, PartiallineConstants.EXCEL_DEFAULT_SHEET_SIZE));
		// 返回
		return excelStream;
	}
	
	/**
	 * 根据运单号，落地配公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-22 上午10:57:54
	 */
	@Override
	public boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,String agentCompanyCode) {
		// 验证运单号码
		if(StringUtils.isBlank(waybillNo)) {
			throw new ExternalBillException("查询参数运单号为空", "");
		}
		LOGGER.info("checkIfExistValidLdpExternalBillForStl--运单号：" +waybillNo+";快递代理公司："+agentCompanyCode );
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		//运单号
		ldpExternalBillDto.setWaybillNo(waybillNo);
		//落地配公司编码
		ldpExternalBillDto.setAgentCompanyCode(agentCompanyCode);
		//已审核
		ldpExternalBillDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
		//非中转
		ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		Long cnt = ldpExternalBillDao.queryCountIfExistValidLdpExternalBillForStl(ldpExternalBillDto);
		
		return cnt.longValue() > 0 ? true : false;
	}
	
	//BUGKD-1674
	//落地配外发单未审核的状态下，其他应收应付新增成功（实际上是要已审核的状态，才能新增其他应收应付)
	//checkIfExistValidLdpExternalBillForStl是查状态不等于作废的，实际上结算是要状态等于审核的
	//checkIfExistValidLdpExternalBillForStl由于更改单已使用，所以为结算新增此接口
	@Override
	public boolean existHasAuditedLdpExternalBillForStl(String waybillNo,String agentCompanyCode) {
		// 验证运单号码
		if(StringUtils.isBlank(waybillNo)) {
			throw new ExternalBillException("查询参数运单号为空", "");
		}
		LOGGER.info("existHasAuditedLdpExternalBillForStl--运单号：" +waybillNo+";快递代理公司："+agentCompanyCode );
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		//运单号
		ldpExternalBillDto.setWaybillNo(waybillNo);
		//落地配公司编码
		ldpExternalBillDto.setAgentCompanyCode(agentCompanyCode);
		//已审核
		ldpExternalBillDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		//非中转
		ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		Long cnt = ldpExternalBillDao.queryCountHasAuditedLdpExternalBill(ldpExternalBillDto);
		
		return cnt.longValue() > 0;
	}
	
	/**
	 * 查询条件查询有效落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-09  上午11:35:54
	 */
	@Override
	public LdpExternalBillDto queryExternalBillByWaybillNo(LdpExternalBillDto ldpExternalBillDto) {
		LdpExternalBillDto retDto = null;
		// 验证运单号码
		if (ldpExternalBillDto == null) {
			throw new ExternalBillException("查询参数为空", "");
		} else {
			if (StringUtils.isBlank(ldpExternalBillDto.getWaybillNo())) {
				throw new ExternalBillException("运单号为空", "");
			}
		}
		//已审核
		ldpExternalBillDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		//非中转
		ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		//结果list
		List<LdpExternalBillDto> tempList = queryExternalBillInfoList(ldpExternalBillDto, true);
		//返回唯一结果
		
		if (CollectionUtils.isNotEmpty(tempList)) {
			retDto = tempList.get(0);
		}
		//落地配网点不为空,查询网点的电话和地址
		if(retDto != null && StringUtils.isNotBlank(retDto.getAgentOrgCode())){
			// 代理网点信息Dto
			AgencyBranchOrCompanyDto companyDto = vehicleAgencyDeptService.queryAgencyBranchCompanyInfo(retDto.getAgentOrgCode());
			if (companyDto != null) {
				// 到达网点电话
				if (StringUtils.isNotBlank(companyDto.getBranchContactPhone())) {
					retDto.setAgentOrgPhone(companyDto.getBranchContactPhone());
				}
				// 到达网点地址
				if (StringUtils.isNotBlank(companyDto.getBranchAddress())) {
					retDto.setAgentOrgaddr(companyDto.getBranchAddress());
				}
			}
		}
		return retDto;
	}


	@Override
	public boolean checkExistLdpExternalBillByWaybillNo(String waybillNo) {
		long count = ldpExternalBillDao.queryLdpExternalBillCountByWaybillNo(waybillNo);
		
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public LdpExternalBillDto queryLdpExternalBillDetail(String id) {
		LdpExternalBillDto dto = ldpExternalBillDao.queryByPrimaryKey(id);
		
		if(dto == null){
			return new LdpExternalBillDto();
		}else{
			ldpExternalBillFeeDataConverting(dto);
			
			return dto;
		}
	}


//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	
	/**
	 * 判断运单是否签收
	 * @author foss-wuyingjie
	 * @param waybillNo 运单号
	 * @update-author foss--269701--2015/10/24
	 * @update-param waybillNo 运单号,serialNo 流水号
	 */
	private void checkSignStatus(String waybillNo,String serialNo){
		WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
		resultEntity.setWaybillNo(waybillNo);
		//新增流水号serialNo
		resultEntity.setSerialNo(serialNo);
		resultEntity.setActive(FossConstants.ACTIVE);
		//一票多件时，结算接口变化
		//变更前--queryWaybillSignResultByWaybillNo（WaybillSignResultEntity entity）
		//外发校验运单是否签收  返回waybillSignResultEntity有值表示已签收，null未签收
		WaybillSignResultEntity waybillSignResultEntity= waybillSignResultService.checkWaybillSignResult(resultEntity);
		if(waybillSignResultEntity != null){
			LOGGER.error("运单：" + waybillNo +",流水号: "+serialNo+ " 已签收，不能继续操作");
			throw new ExternalBillException("运单：" + waybillNo+",流水号: "+serialNo + " 已签收，不能继续操作");
		}
	}
	
	/**
	 * 判断运单是否存在未处理的更改单
	 * @author foss-wuyingjie
	 * @param waybillNo 运单号
	 */
	private void checkRfcWaybillStatus(String waybillNo){
		Boolean isExistWaybillRfc = waybillRfcService.isExsitsWayBillRfc(waybillNo);
		if(isExistWaybillRfc){
			LOGGER.error("运单：" + waybillNo + " 存在未处理的更改单，不能继续操作");
			throw new ExternalBillException("运单：" + waybillNo + " 存在未处理的更改单，不能继续操作");
		}
	}

	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}


	@Override
	public List<LdpExternalBillDto> queryExternalBillListForLdpSign(String waybillNo) {

		return ldpExternalBillDao.queryExternalBillListForLdpSign(waybillNo);
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @author nly
	 * @date 2014年9月3日 上午9:02:54
	 * @function 更新落地配外发单是否上报OA字段
	 * @param billId
	 * @param isReport
	 * @return
	 */
	@Override
	public int updateReportOAByPrimaryKey(String billId, String isReport) {
		return ldpExternalBillDao.updateReportOAByPrimaryKey(billId,isReport);
	}
	/**
	 * 通过运单号查询外发单信息列表
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<LdpExternalBillDto> queryExternalBillListByWaybillNo(
			String waybillNo) {
		return ldpExternalBillDao.queryExternalBillListByWaybillNo(waybillNo);
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 下午5:03:35
	 * @function 根据单号查找有效的落地配外发单
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<LdpExternalBillDto> queryByWaybillNo(String waybillNo) {
		LdpExternalBillDto dto = new  LdpExternalBillDto();
		dto.setWaybillNo(waybillNo);
		return ldpExternalBillDao.queryByWaybillNo(dto);
	}

	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24
	 */
	@Override
	public List<LdpExternalBillDto> queryLdpExternalBillNoList(
			List<String> waybillList) {
		if(CollectionUtils.isNotEmpty(waybillList)){
			return ldpExternalBillDao.queryLdpExternalBillNoList(waybillList);
		}else
			return null;
	}
	
	
}