package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayForTfrService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackageAssistPriceDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackCommonService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageAssistPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackagePriceToCubcService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackingToCubcEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.ValidatePackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.queryPackedWaybillNoDto;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * @desc 辅助包装类dao
 * @author foss-中转开发组-105795-wqh
 * @date   2014-05-08
 * **/
public class PackageAssistPriceService implements IPackageAssistPriceService {

    private static final Logger LOGGER=LoggerFactory.getLogger(PackageMainPriceService.class);
	
	//获取开单信息
	private IWaybillManagerService waybillManagerService;
	
	//辅助包装dao
	private IPackageAssistPriceDao packageAssistPriceDao;
	//综合包装供应商接口
	private IPackagingSupplierService packagingSupplierService;
	
	/**
	 * 运单签收service
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	//营业部代打木架信息
	private IWoodenRequirementsService woodenRequirementsService;
	
	//职员信息
	private IEmployeeService employeeService;
	
	//主要包装service
	private IPackageMainPriceService packageMainPriceService;
	
	//包装供应service
	private IPackCommonService packCommonService;
	
	//异步至CUBC
	private IPackagePriceToCubcService packagePriceToCubcService;
	
	//同步至CUBC
	private IFossToCubcService fossToCubcService;
	
	/**
	 * 获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IPackingRecAndPayForTfrService packingRecAndPayForTfrService;
	//部门组织
	private IOrgAdministrativeInfoService OrgAdministrativeInfoService;
	
	private final String PAYABLE_BILL_OPERATE_TYPE_ADD = "add";
	private final String PAYABLE_BILL_OPERATE_TYPE_UPDATE = "update";
	private final String PAYABLE_BILL_OPERATE_TYPE_DELETE = "delete";
	private final String PAYABLE_BILL_OPERATE_TYPE_AUDIT = "audit";
	private final String PAYABLE_BILL_OPERATE_TYPE_DEAUDIT = "deaudit";

	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}


	/**
	 * 
	     *
		 * @desc  PackageAssistPriceEntity类型转换为PackingRecAndPayTfrDto类型
		 * @author 042795
		 * @date 2014-6-26 下午3:28:40
		 * @see typeConversion
	 */
	private PackingRecAndPayTfrDto typeConversion(PackageAssistPriceEntity packageAssistPriceEntity){
		PackingRecAndPayTfrDto packingRecAndPayTfrDto = null;
		if(packageAssistPriceEntity != null){
			packingRecAndPayTfrDto = new PackingRecAndPayTfrDto();
			packingRecAndPayTfrDto.setBillOrgCode(packageAssistPriceEntity.getPackageOrgCode());
			packingRecAndPayTfrDto.setBillOrgName(packageAssistPriceEntity.getPackageOrgName());
			packingRecAndPayTfrDto.setPackageOrgCode(packageAssistPriceEntity.getPackageSupplierCode());
			packingRecAndPayTfrDto.setPackageOrgName(packageAssistPriceEntity.getPackageSupplierName());
			packingRecAndPayTfrDto.setPackagePayableMoney(packageAssistPriceEntity.getPackagePayableMoney());
			packingRecAndPayTfrDto.setWaybillNo(packageAssistPriceEntity.getWaybillNo());
		}
		return packingRecAndPayTfrDto;
	}
	

	/**
	 * @desc PackingRecAndPayTfrDto类型转换为PackingToCubcEntity类型
	 * @author 316759-RuipengWang-foss
	 * @date 2016-11-11 9:20:40 AM
	 * @see typeConversion
	 */
	private PackingToCubcEntity typeConversion(PackageAssistPriceEntity packingRecAndPayTfrDto, String type, CurrentInfo currentInfo) {
		PackingToCubcEntity cubcEntity = null;
		if (packingRecAndPayTfrDto != null) {
			cubcEntity = new PackingToCubcEntity();
			
			//id
			if (packingRecAndPayTfrDto.getId() != null) {
				cubcEntity.setId(packingRecAndPayTfrDto.getId());
			} else {
				cubcEntity.setId(UUIDUtils.getUUID());
			}
			
			// 运单号
			cubcEntity.setWaybillNo(packingRecAndPayTfrDto.getWaybillNo());
			// 开单部门code
			cubcEntity.setBillOrgCode(packingRecAndPayTfrDto.getBillOrgCode());
			// 开单部门名称
			cubcEntity.setBillOrgName(packingRecAndPayTfrDto.getBillOrgName());
			// 包装部门code
			cubcEntity.setPackageOrgCode(packingRecAndPayTfrDto.getPackageOrgCode());
			// 包装部门名称
			cubcEntity.setPackageOrgName(packingRecAndPayTfrDto.getPackageOrgName());
			// 应付金额
			cubcEntity.setPackagePayableMoney(packingRecAndPayTfrDto.getPackagePayableMoney());
			// 操作
			cubcEntity.setStatus(type);
			// 当前操作人工号
			cubcEntity.setCurentEmpCode(currentInfo.getEmpCode());
			// 当前登录人姓名
			cubcEntity.setCurentEmpName(currentInfo.getEmpName());
			// 当前登录人部门编码
			cubcEntity.setCurentEmpDeptCode(currentInfo.getCurrentDeptCode());
			// 当前登录人部门名称
			cubcEntity.setCurentEmpDeptName(currentInfo.getCurrentDeptName());
			
			//木条长度woodenBarLong
			cubcEntity.setWoodenBarLong(packingRecAndPayTfrDto.getWoodenBarLong());
			//气泡膜体积	bubbVelamenVolume
			cubcEntity.setBubbVelamenVolume(packingRecAndPayTfrDto.getBubbVelamenVolume());
			//缠绕膜体积	bindVelamenVolume
			cubcEntity.setBindVelamenVolume(packingRecAndPayTfrDto.getBindVelamenVolume());
			//包带根数	bagBeltNum
			cubcEntity.setBagBeltNum(packingRecAndPayTfrDto.getBagBeltNum());
			//是否激活	active
			cubcEntity.setActive(packingRecAndPayTfrDto.getActive());
			//审核人姓名	auditorName
			cubcEntity.setAuditorName(packingRecAndPayTfrDto.getAuditorName());
			//审核人code	auditorCode
			cubcEntity.setAuditorCode(packingRecAndPayTfrDto.getAuditorCode());
			//审核日期	auditDate
			cubcEntity.setAuditDate(packingRecAndPayTfrDto.getAuditDate());
			//反审核人姓名	deauditorName
			cubcEntity.setDeauditorName(packingRecAndPayTfrDto.getDeauditorName());
			//反审核人code	deauditorCode
			cubcEntity.setDeauditorCode(packingRecAndPayTfrDto.getDeauditorCode());
			//反审核日期	deauditDate
			cubcEntity.setDeauditDate(packingRecAndPayTfrDto.getDeauditDate());
			//理论打木架体积	theoryFrameVolume
			cubcEntity.setTheoryFrameVolume(packingRecAndPayTfrDto.getTheoryFrameVolume());
			//实际打木架体积	actualFrameVolume
			cubcEntity.setActualFrameVolume(packingRecAndPayTfrDto.getActualFrameVolume());
			//打木架体积	packageFrameVolume
			cubcEntity.setPackageFrameVolume(packingRecAndPayTfrDto.getPackageFrameVolume());
			//理论打木箱体积	theoryWoodenVolume
			cubcEntity.setTheoryWoodenVolume(packingRecAndPayTfrDto.getTheoryWoodenVolume());
			//实际打木箱体积	actualWoodenVolume
			cubcEntity.setActualWoodenVolume(packingRecAndPayTfrDto.getActualWoodenVolume());
			//打木箱体积	packageWoodenVolume
			cubcEntity.setPackageWoodenVolume(packingRecAndPayTfrDto.getPackageWoodenVolume());
			//实际打木托个数	theoryMaskNumber
			cubcEntity.setTheoryMaskNumber(packingRecAndPayTfrDto.getTheoryMaskNumber());
			//打木托个数	actualMaskNumber
			cubcEntity.setActualMaskNumber(packingRecAndPayTfrDto.getActualMaskNumber());
			//包装供应商code	packageSupplierCode
			cubcEntity.setPackageSupplierCode(packingRecAndPayTfrDto.getPackageSupplierCode());
			//包装供应商	packageSupplierName
			cubcEntity.setPackageSupplierName(packingRecAndPayTfrDto.getPackageSupplierName());
			//包装材料
			cubcEntity.setPackageMaterial(packingRecAndPayTfrDto.getPackageMaterial());
			//创建部门
			cubcEntity.setCreateOrgName(packingRecAndPayTfrDto.getCreateOrgName());
			//创建部门Code
			cubcEntity.setCreateOrgCode(packingRecAndPayTfrDto.getCreateOrgCode());
			//创建人code
			cubcEntity.setCreateUserCode(packingRecAndPayTfrDto.getCreateUserCode());
			//创建人
			cubcEntity.setCreateUserName(packingRecAndPayTfrDto.getCreateUserName());
			//创建时间
			cubcEntity.setCreateTime(packingRecAndPayTfrDto.getCreateTime());
			//修改人code
			cubcEntity.setModifyUserCode(packingRecAndPayTfrDto.getModifyUserCode());
			//修改人
			cubcEntity.setModifyUserName(packingRecAndPayTfrDto.getModifyUserName());
			//精确到毫秒的修改时间
			cubcEntity.setModifyTimeMs(packingRecAndPayTfrDto.getModifyTimeMs());
			//修改时间
			cubcEntity.setModifyTime(packingRecAndPayTfrDto.getModifyTime());
			//是否当月单据
			cubcEntity.setIsNowMonth(packingRecAndPayTfrDto.getIsNowMonth());
			//审核状态
			cubcEntity.setAuditStatus(packingRecAndPayTfrDto.getAuditStatus());
		}
		return cubcEntity;
	}
	
	/**
	 * 
	 * @desc 批量增加/修改/删除财务单据
	 * @author 042795
	 * @date 2014-6-26 下午4:32:29
	 * @see handlerPayableBill
	 */
	private void batchHandlerPayableBill(List<PackageAssistPriceEntity> packageAssistPriceList,String operateType){
		if(CollectionUtils.isNotEmpty(packageAssistPriceList)){
			List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
			//运单号List，用于灰度判断
			List<String> wayBillList = new ArrayList<String>();
			for(PackageAssistPriceEntity packageAssistPriceEntity : packageAssistPriceList){
				PackingRecAndPayTfrDto packingRecAndPayTfrDto = this.typeConversion(packageAssistPriceEntity);
				packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
				//给运单添加到List里
				wayBillList.add(packageAssistPriceEntity.getWaybillNo());
			}
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			
			/**
			 * 转换为同步至CUBC类
			 */
			List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
			String type = "";
			if (this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
				// 新增
				type = SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING;
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)) {
				// 修改
				type = SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING;
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_AUDIT.equals(operateType)) {
				// 核销
				type = "0";
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_DEAUDIT.equals(operateType)) {
				// 反核销
				type = "1";
			} else {
				// 作废
				type = "2";
			}
			for (PackageAssistPriceEntity packingTfrDto : packageAssistPriceList) {

				PackingToCubcEntity cubcEntity = this.typeConversion(packingTfrDto, type, currentInfo);
				packingToCubcEntitys.add(cubcEntity);
			}
			LOGGER.error("灰度开始...");
			//封装灰度实体，类型是运单
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			//STL CUBC调用标识
			String fossCubc = "";
			List<String> fossWayBillList = new ArrayList<String>();
			List<String> ucbcWayBillList = new ArrayList<String>();
			List<PackingRecAndPayTfrDto> fossEntityList = new ArrayList<PackingRecAndPayTfrDto>();
			List<PackingToCubcEntity> ucbcEntityList = new ArrayList<PackingToCubcEntity>();
			//分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				//循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					 if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						 fossWayBillList = vestResult.getVestObject();
					 } else {
						 ucbcWayBillList = vestResult.getVestObject();
					 }
				}
				
				//得到运单号对应的实体，提供给FOSS STL用
				for (PackingRecAndPayTfrDto dto : packingRecAndPayTfrDtoList) {
					for (String wayBill : fossWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							fossEntityList.add(dto);
						}
					}
				}
				//得到运单号对应的实体，提供给UCBC用
				for (PackingToCubcEntity dto : packingToCubcEntitys) {
					for (String wayBill : ucbcWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							ucbcEntityList.add(dto);
						}
					}
				}
				
				//赋值
				packingRecAndPayTfrDtoList = fossEntityList;
				packingToCubcEntitys = ucbcEntityList;
			}
			LOGGER.error("灰度结束...");
			//灰度返回一条结果，SystemCode等于FOSS或返回多条结果会走一下逻辑
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				LOGGER.error("灰度判断走FOSS...");
				try{
					if(this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)){
						packingRecAndPayForTfrService.add(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)){
						packingRecAndPayForTfrService.update(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_AUDIT.equals(operateType)){
						packingRecAndPayForTfrService.audit(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_DEAUDIT.equals(operateType)){
						packingRecAndPayForTfrService.reverseAudit(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else{
						packingRecAndPayForTfrService.invalid(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}
				}catch(SettlementException e){
					throw new TfrBusinessException("财务异常："+e.getErrorCode());
				}
			}
			// 灰度返回一条结果，SystemCode等于UCBC或返回多条结果会走一下逻辑
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_CUBC) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				/**
				 * @desc 批量增加/修改/删除财务单据 同步至CUBC
				 * @author 316759-RuipengWang-foss
				 * @date 2016-11-11 9:33:29 AM
				 */
				LOGGER.error("灰度判断走CUBC...");
				ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
				if (this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
					// 新增
					packagePriceToCubcService.pushAddPackingRecAndPay(packingToCubcEntitys);
				} else if (this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)) {
					// 修改
					responseDto = fossToCubcService.pushUpdatePacking(requestStr);
				} else {
					// 核销/反核销/作废
					responseDto = fossToCubcService.pushAuditReverseAuditInvalid(requestStr);
				}
				if (!this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
					// 当不为新增时，判断返回值
				if (null == responseDto) {
					throw new TfrBusinessException("同步信息至CUBC异常：返回参数为null");
				} else if (StringUtils.equals(responseDto.getResult(), AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
					throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
					}
				}
			}
		}
	}

	/**
	 * @desc 增加/修改/删除财务单据
	 * @author 042795
	 * @date 2014-6-26 下午4:32:29
	 * @see handlerPayableBill
	 */
	private void handlerPayableBill(PackageAssistPriceEntity packageAssistPriceEntity,String operateType){
		if(packageAssistPriceEntity != null){
			PackingRecAndPayTfrDto packingRecAndPayTfrDto = this.typeConversion(packageAssistPriceEntity);
			List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
			packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			/**
			 * 转换为同步至CUBC类
			 */
			List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
			String type = "";
			if (this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
				// 新增
				type = SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING;
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)) {
				// 修改
				type = SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING;
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_AUDIT.equals(operateType)) {
				// 核销
				type = "0";
			} else if (this.PAYABLE_BILL_OPERATE_TYPE_DEAUDIT.equals(operateType)) {
				// 反核销
				type = "1";
			} else {
				// 作废
				type = "2";
			}
			PackingToCubcEntity cubcEntity = this.typeConversion(packageAssistPriceEntity, type, currentInfo);
			packingToCubcEntitys.add(cubcEntity);
			LOGGER.info("type = " + type);
			
			//封装灰度实体，类型是运单
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos(new String[] {packingRecAndPayTfrDtoList.get(0).getWaybillNo()});
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
				try{
					if(this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)){
						packingRecAndPayForTfrService.add(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)){
						packingRecAndPayForTfrService.update(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_AUDIT.equals(operateType)){
						packingRecAndPayForTfrService.audit(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else if(this.PAYABLE_BILL_OPERATE_TYPE_DEAUDIT.equals(operateType)){
						packingRecAndPayForTfrService.reverseAudit(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}else{
						packingRecAndPayForTfrService.invalid(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
					}
				}catch(SettlementException e){
					throw new TfrBusinessException("财务异常："+e.getErrorCode());
				}
			} else {
				/**
				 * @desc 增加/修改/删除财务单据 同步至CUBC
				 * @author 316759-RuipengWang-foss
				 * @date 2016-11-12 9:33:29 AM
				 */
				ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
				if (this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
					// 新增
					try {
						packagePriceToCubcService.pushAddPackingRecAndPay(packingToCubcEntitys);
					} catch (BusinessException e) {
						throw new TfrBusinessException("同步信息至CUBC异常：" + e.getMessage());
					}
				} else if (this.PAYABLE_BILL_OPERATE_TYPE_UPDATE.equals(operateType)) {
					// 修改
					responseDto = fossToCubcService.pushUpdatePacking(requestStr);
				} else {
					// 核销/反核销/作废
					responseDto = fossToCubcService.pushAuditReverseAuditInvalid(requestStr);
				}
				if (!this.PAYABLE_BILL_OPERATE_TYPE_ADD.equals(operateType)) {
					// 当不为新增时，判断返回值
				if (null == responseDto) {
					throw new TfrBusinessException("同步信息至CUBC接口异常");
				} else if (StringUtils.equals(responseDto.getResult(), AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
					throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
					}
				}
	
			}
		}
	}
	/**
	 *重新包装当前登录人信息，只用于运单更改时，读取包装信息修改人信息  
	 ****/
	
	private CurrentInfo reCurrentInfo(PackageAssistPriceEntity packageAssistPriceEntity){
		
		
		UserEntity user=new UserEntity();
		OrgAdministrativeInfoEntity dept=new OrgAdministrativeInfoEntity(); 
		EmployeeEntity employeeEntity=new EmployeeEntity();
		user.setEmployee(employeeEntity);
		if(StringUtils.isNotBlank(packageAssistPriceEntity.getModifyUserCode())){
			user.setUserName(packageAssistPriceEntity.getModifyUserName());
			user.getEmployee().setEmpCode(packageAssistPriceEntity.getModifyUserCode());
			user.getEmployee().setEmpName(packageAssistPriceEntity.getModifyUserName());
		}else if(StringUtils.isNotBlank(packageAssistPriceEntity.getCreateUserCode())){
			user.setUserName(packageAssistPriceEntity.getCreateOrgName());
			user.getEmployee().setEmpCode(packageAssistPriceEntity.getCreateUserCode());
			user.getEmployee().setEmpName(packageAssistPriceEntity.getCreateUserName());
		}else {
			throw new TfrBusinessException("当前辅助包装信息创建人和修改人为空");
			
		}
		dept.setCode(packageAssistPriceEntity.getPackageOrgCode());
		dept.setName(packageAssistPriceEntity.getPackageOrgName());
		CurrentInfo currentInfo = new CurrentInfo(user,dept);
		
		return currentInfo;
	}
	
	/**
	 * @desc 增加/修改/删除财务单据
	 * @author 205109-zenghaibin
	 * @date 2014-12-17 下午17:21:29
	 * @see handlerPayableBill
	 */
	private void handlerPayableBillForWayBill(PackageAssistPriceEntity packageAssistPriceEntity,String operateType){
		if(packageAssistPriceEntity != null){
			PackingRecAndPayTfrDto packingRecAndPayTfrDto = this.typeConversion(packageAssistPriceEntity);
			List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
			packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
			CurrentInfo currentInfo =reCurrentInfo(packageAssistPriceEntity);
			
			/**
			 * 转换为同步至CUBC类
			 */
			List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
			PackingToCubcEntity cubcEntity = this.typeConversion(packageAssistPriceEntity, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
			packingToCubcEntitys.add(cubcEntity);
			
			//封装灰度实体，类型是运单
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos(new String[] {packingRecAndPayTfrDtoList.get(0).getWaybillNo()});
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
				try{
					packingRecAndPayForTfrService.update(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
				}catch(SettlementException e){
					throw new TfrBusinessException("财务异常："+e.getErrorCode());
				}
			} else {
				ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
				
				responseDto = fossToCubcService.pushUpdatePacking(requestStr);

				if (null == responseDto) {
					throw new TfrBusinessException("同步信息至CUBC异常：返回参数为null");
				} else if (StringUtils.equals(responseDto.getResult(), AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
					throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
				}
			}
		}
	}
	
	/**
	 * 
	 * @desc:添加辅助包装信息，单条
	 * @param packageAssistPriceEntity
	 * @return void
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	@Transactional
	public void addPackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		LOGGER.info("添加辅助包装信息开始，单条");
		if(packageAssistPriceEntity==null)
		{
			throw new TfrBusinessException("保存信息不存在");

		}
		List<String> waybillNoList=new ArrayList<String>();
		waybillNoList.add(packageAssistPriceEntity.getWaybillNo());
		//查询已签收运单信息
		List<String> signList = waybillSignResultService.queryWaybillSignResultWaybillNos(waybillNoList);
		if(signList.size() > 0){
			throw new TfrBusinessException("运单已签收，不能录入包装信息");
		}
		//校验加木托个数
		this.validateMask(packageAssistPriceEntity);
		//开单基本信息
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(packageAssistPriceEntity.getWaybillNo());
		if(waybillEntity==null)
		{
			throw new TfrBusinessException("录入单号不存在");
			
		}
		
		//初始化辅助包装金额基本信息
		initPackedAssistPrice(packageAssistPriceEntity,waybillEntity);
		packageAssistPriceEntity.setBillOrgName(waybillEntity.getCreateUserDeptName());
		//packageAssistPriceEntity.setPackageSupplierCode("JHQS01");
		
		//调综合接口
		if(StringUtil.isEmpty(packageAssistPriceEntity.getPackageSupplierCode()))
		{
			throw new TfrBusinessException("包装供应商编码为空");
		}
		PackagingSupplierEntity resultEntity=getPackagingSupplier(packageAssistPriceEntity.getPackageSupplierCode(),
				packageAssistPriceEntity.getPackageOrgCode());
		packageAssistPriceEntity.setPackageSupplierName(resultEntity.getPackagingSupplier());
		
		//判断当前记录是否已经被录入
		List<PackageAssistPriceEntity> queryList=
				packageAssistPriceDao.queryAssistPackagePriceByWaybillNoAndSupplierCode(packageAssistPriceEntity);
		if(queryList!=null &&queryList.size()>0)
		{
			LOGGER.error("当前记录已经被录入");
			throw new TfrBusinessException("当前记录已经被录入");
		}
		//计算打木架、打木箱体积
		calculateFrameAndBoxVolume(packageAssistPriceEntity);
		//计算包装金额
		calculatePackagePayableMoney(packageAssistPriceEntity,resultEntity);
		List<PackageAssistPriceEntity> assistList=new ArrayList<PackageAssistPriceEntity>();
		assistList.add(packageAssistPriceEntity);
		//保留2位小数
		assistList=handlePoint(assistList);
		packageAssistPriceEntity=assistList.get(0);
		//保存辅助包装金额信息
		packageAssistPriceDao.addPackageAssistPrice(packageAssistPriceEntity);
		
		//调结算接口生成应付单 
		this.handlerPayableBill(packageAssistPriceEntity, this.PAYABLE_BILL_OPERATE_TYPE_ADD);
		
		LOGGER.info("添加辅助包装信息结束");
	}
	
	/**
	 *校验实际加木托个数
	 *@author 205109-foss-zenghaibin
	 *@date 2014-12-10 10:40:20
	 * @param packageAssistPriceEntity
	 * @return void
	 ***/
	private void validateMask(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		//获取运单信息
		String waybillNo=packageAssistPriceEntity.getWaybillNo();
		//判断加托个数是否为整数
		int maskQty=0;
		if(packageAssistPriceEntity.getActualMaskNumber()==null){
			throw new TfrBusinessException("加托个数不能为空");
		}
		try {
		  maskQty= packageAssistPriceEntity.getActualMaskNumber().intValue();
			
		} catch (TfrBusinessException e) {
			throw new TfrBusinessException("加托个数转换异常");
		}
		
		
		if(StringUtils.isEmpty(waybillNo))
		{
			throw new BusinessException("运单号为空");
		}
		WaybillEntity waybillEntity=null;
		try {
			 waybillEntity=waybillManagerService.queryWaybillBasicByNo(waybillNo);
			
		} catch (Exception e) {
			throw new TfrBusinessException("调接送货 查询运单信息接口异常："+e.getMessage());
		}
		if(waybillEntity==null)
		{
			throw new TfrBusinessException("运单信息不存在");
		}
		ValidatePackedDto validatePackeddto=new ValidatePackedDto();
		validatePackeddto.setWaybillNo(waybillNo);
		validatePackeddto.setMaskQty(maskQty);
		//访问后台进行校验
		packCommonService.checkWoodMask(waybillEntity, validatePackeddto);
	}
	
	
	
	
	
	private void validUpdatePackageAssistPriceEntity(PackageAssistPriceEntity newPackageAssistPrice,PackageAssistPriceEntity oldPackageAssistPrice){
		if(oldPackageAssistPrice.getModifyTime() != null){
			if(newPackageAssistPrice.getModifyTime() == null){
				throw new TfrBusinessException("该条信息已被修改，请重置后重新修改");
			}else{
				if(oldPackageAssistPrice.getModifyTime().after(newPackageAssistPrice.getModifyTime())){
					throw new TfrBusinessException("该条信息已被修改，请重置后重新修改");
				}
			}
			
		}
		if(!FossConstants.ACTIVE.equals(oldPackageAssistPrice.getActive())){
			throw new TfrBusinessException("该条信息已被删除，不能修改");
		}
		if(!PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT.equals(oldPackageAssistPrice.getAuditStatus())){
			throw new TfrBusinessException("该条信息已审核，不能修改");
		}
		if(oldPackageAssistPrice.equals(newPackageAssistPrice)){
			throw new TfrBusinessException("该条信息没有任何修改");
		}
	}
	/**
	 * 
	 * @desc:提供修改辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return void 
	 * @parm  packageAssistPriceEntity
	 * @param isPDA 是否PDA端修改
	 * @date   2014-05-08
	 * */
	@Transactional
	public Date updatePackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		if(packageAssistPriceEntity != null){
			LOGGER.info("修改辅助包装信息开始");
			//解决传入部门编码为空的问题
			packageAssistPriceEntity.setPackageOrgCode(getOutFieldCode().getCode());
			// 用户
			UserEntity user = FossUserContext.getCurrentUser();
			// 职员
			EmployeeEntity employee = user.getEmployee();
			PackagingSupplierEntity packagingSupplier=getPackagingSupplier(packageAssistPriceEntity.getPackageSupplierCode(),
					packageAssistPriceEntity.getPackageOrgCode());
			//旧辅助包装信息
			PackageAssistPriceEntity oldPackageAssistPriceEntity=packageAssistPriceDao.queryPackedAssistPriceById(packageAssistPriceEntity.getId());
			//增加状态，有效性校验
			if(oldPackageAssistPriceEntity != null){
				//校验实体
				this.validUpdatePackageAssistPriceEntity(packageAssistPriceEntity, oldPackageAssistPriceEntity);
				//得到更新后的辅助包装信息
				PackageAssistPriceEntity newPackageAssistPriceEntity=updateOldAssistPrice(employee,packageAssistPriceEntity,oldPackageAssistPriceEntity);
				if(newPackageAssistPriceEntity != null){
					//重新 计算打木架、打木箱体积
					calculateFrameAndBoxVolume(newPackageAssistPriceEntity);
					//重新计算包装金额
					calculatePackagePayableMoney(newPackageAssistPriceEntity,packagingSupplier);
					
					List<PackageAssistPriceEntity> assistList=new ArrayList<PackageAssistPriceEntity>();
					
					assistList.add(newPackageAssistPriceEntity);
					//保留2位小数
					assistList=handlePoint(assistList);
					if(CollectionUtils.isNotEmpty(assistList) && assistList.get(0) != null){
						newPackageAssistPriceEntity=assistList.get(0);
						packageAssistPriceDao.updatePackageAssistPrice(newPackageAssistPriceEntity);
						//设置ID
						newPackageAssistPriceEntity.setId(packageAssistPriceEntity.getId());
							//调结算接口修改财务单据
						this.handlerPayableBill(newPackageAssistPriceEntity, this.PAYABLE_BILL_OPERATE_TYPE_UPDATE);
						
						LOGGER.info("修改辅助包装信息结束");
						return newPackageAssistPriceEntity.getModifyTime();
					}else{
						throw new TfrBusinessException("重新计算包装金额时发证异常");
					}
				}else{
					throw new TfrBusinessException("更新该条信息时发生异常");
				}
			}else{
				throw new TfrBusinessException("根据ID未查询到所修改记录");
			}
		}else{
			throw new TfrBusinessException("所传入修改信息为空");
		}
	}
	/**
	 * 
	 * @desc:提供运单发生更改，理论体积发生变化时的金额计算
	 * @author foss-中转开发组-205109-zenghaibin
	 * @return void 
	 * @parm  packageAssistPriceEntity
	 * @date   2014-12-17
	 * */
	@Transactional
	private Date updatePackageAssistForWayBill(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		if(packageAssistPriceEntity != null){
			LOGGER.info("修改辅助包装信息开始");
			//解决传入部门编码为空的问题
			if(StringUtils.isBlank(packageAssistPriceEntity.getPackageOrgCode())){//
				//如果包装部门为空则去找对应外场包装部门
				packageAssistPriceEntity.setPackageOrgCode(getOutFieldCode().getCode());
			}
			// 职员
			EmployeeEntity employee =reCurrentInfo(packageAssistPriceEntity).getUser().getEmployee();
			PackagingSupplierEntity packagingSupplier=getPackagingSupplier(packageAssistPriceEntity.getPackageSupplierCode(),
					packageAssistPriceEntity.getPackageOrgCode());
			//旧辅助包装信息
			PackageAssistPriceEntity oldPackageAssistPriceEntity=packageAssistPriceDao.queryPackedAssistPriceById(packageAssistPriceEntity.getId());
			//增加状态，有效性校验
			if(oldPackageAssistPriceEntity != null){
				//得到更新后的辅助包装信息
				PackageAssistPriceEntity newPackageAssistPriceEntity=updateOldAssistPrice(employee,packageAssistPriceEntity,oldPackageAssistPriceEntity);
				if(newPackageAssistPriceEntity != null){
					//重新 计算打木架、打木箱体积
					calculateFrameAndBoxVolume(newPackageAssistPriceEntity);
					//重新计算包装金额
					calculatePackagePayableMoney(newPackageAssistPriceEntity,packagingSupplier);
					
					List<PackageAssistPriceEntity> assistList=new ArrayList<PackageAssistPriceEntity>();
					
					assistList.add(newPackageAssistPriceEntity);
					//保留2位小数
					assistList=handlePoint(assistList);
					if(CollectionUtils.isNotEmpty(assistList) && assistList.get(0) != null){
						newPackageAssistPriceEntity=assistList.get(0);
						packageAssistPriceDao.updatePackageAssistPrice(newPackageAssistPriceEntity);
						//设置ID
						newPackageAssistPriceEntity.setId(packageAssistPriceEntity.getId());
							//调结算接口修改财务单据
						this.handlerPayableBillForWayBill(newPackageAssistPriceEntity, this.PAYABLE_BILL_OPERATE_TYPE_UPDATE);
						
						LOGGER.info("修改辅助包装信息结束");
						return newPackageAssistPriceEntity.getModifyTime();
					}else{
						throw new TfrBusinessException("重新计算包装金额时发证异常");
					}
				}else{
					throw new TfrBusinessException("更新该条信息时发生异常");
				}
			}else{
				throw new TfrBusinessException("根据ID未查询到所修改记录");
			}
		}else{
			throw new TfrBusinessException("所传入修改信息为空");
		}
	}
	
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	public List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto,int limit,int start)
	{
	
		LOGGER.info("根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息");
		
		List<String> waybillNos=new ArrayList<String>();
		List<WaybillEntity> waybillNoList;
		if(queryAssistPackedDto==null)
		{
			throw new TfrBusinessException("查询条件不能为空");
		}
		List<PackageAssistPriceEntity> resultSet=packageAssistPriceDao.queryAssistPirceList(queryAssistPackedDto,limit,start);

		if(resultSet==null || resultSet.size()==0)
		{
			throw new TfrBusinessException("没有查到满足要求的数据");	
		}

		//获取运单号集合
		for(PackageAssistPriceEntity packageAssistPriceEntity:resultSet)
		{
			packageAssistPriceEntity.setIsNowMonth(PackagingConstants.PACKAGING_N);
			waybillNos.add(packageAssistPriceEntity.getWaybillNo());
		}
		
		if(waybillNos.size()>0)
		{
			waybillNoList=waybillManagerService.queryWaybillBasicByNoList(waybillNos);
			
			for(int i=0;i<waybillNoList.size();i++)
			{
				for(int j=0;j<resultSet.size();j++)
				{
					if(resultSet.get(j).getWaybillNo().equals(waybillNoList.get(i).getWaybillNo())
							&&isNowMonth(waybillNoList.get(i).getBillTime())){
						resultSet.get(j).setIsNowMonth(PackagingConstants.PACKAGING_Y);
					}

					/*if(isNowMonth(waybillNoList.get(i).getBillTime())
							&&(resultSet.get(j).getWaybillNo().equals(waybillNoList.get(i).getWaybillNo())))
					{
						resultSet.get(j).setIsNowMonth(PackagingConstants.PACKAGING_Y);
					}else
					{
						resultSet.get(j).setIsNowMonth(PackagingConstants.PACKAGING_N);
					}*/
					
				}
			}
			
			
		}
		resultSet=handlePoint(resultSet);
		
		LOGGER.info("查询结束");

		return resultSet;
	}
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息，不分页
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	public List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto)
	{
		return packageAssistPriceDao.queryAssistPirceList(queryAssistPackedDto);
	}
	
	/**
	 * 
	 * @desc:根据运单号、包装材料、包装供应商查询已经生成的辅助包装金额信息
	 * @param packageAssistPriceEntity
	 * @return List<PackageAssistPriceEntity>
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	public List<PackageAssistPriceEntity> queryAssistPackagePriceByWaybillNoAndSupplierCode(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		if(packageAssistPriceEntity.getWaybillNo()==null)
		{
			throw new TfrBusinessException("运单号不能为空");
		}
		if(packageAssistPriceEntity.getPackageOrgCode()==null)
		{
			throw new TfrBusinessException("包装部门不能为空");

		}
		if(packageAssistPriceEntity.getPackageSupplierCode()==null)
		{
			throw new TfrBusinessException("包装供应商不能为空");

		}
		return packageAssistPriceDao.queryAssistPackagePriceByWaybillNoAndSupplierCode(packageAssistPriceEntity);
	}

	
	/**
	 * 
	 * 计算打木架、打木箱体积
	 * 
	 * 1.当（实际打木架―理论打木架）∕理论打木架大于1%时，打木架体积=（理论打木架体积*1.01）； 
	 * 2.当（实际打木架―理论打木架）∕理论打木架小于等于1%时，打木架体积=理论打木架体积；
	 * 3.当（实际打木箱―理论打木箱）∕理论打木箱大于1%时，打木箱体积=（理论打木箱体积*1.01）； 
	 * 4.当（实际打木箱―理论打木箱）∕理论打木箱小于等于1%时，打木箱体积=理论打木箱体积；
	 * 
	 * **/
	private void calculateFrameAndBoxVolume(PackageAssistPriceEntity packageAssistPriceEntity)
	{

		//关系常量
		BigDecimal relationConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_THEORY_ACTUAL_RELATION_CONSTANT);
		//比较常量
		BigDecimal compareConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_COMPARE_CONSTANT);
		
		//接送货理论打木架信息
		WoodenRequirementsEntity woodenRequirePendingEntity=woodenRequirementsService.queryWoodenRequirements(
				packageAssistPriceEntity.getWaybillNo());
		if(woodenRequirePendingEntity==null)
		{
		   //可能存在没有打包装需求单外场切实打了包装的情况,这是打木架体积、打木箱体积等于实际录入的
			/*( 
			LOGGER.error("运单：{"+packageAssistPriceEntity.getWaybillNo()+"} 不存在包装需求");
			throw new PackagingException("运单：{"+packageAssistPriceEntity.getWaybillNo()+"} 不存在包装需求");*/
			//系统自动初始化
			woodenRequirePendingEntity=new WoodenRequirementsEntity();
			woodenRequirePendingEntity.setWaybillNo(packageAssistPriceEntity.getWaybillNo());
			woodenRequirePendingEntity.setStandGoodsVolume(BigDecimal.ZERO);
			woodenRequirePendingEntity.setBoxGoodsVolume(BigDecimal.ZERO);
			woodenRequirePendingEntity.setSalverGoodsNum(0);
		}
		
		if(woodenRequirePendingEntity.getStandGoodsVolume()==null)
		{
			woodenRequirePendingEntity.setStandGoodsVolume(BigDecimal.ZERO);
		}
		if(woodenRequirePendingEntity.getBoxGoodsVolume()==null)
		{
			woodenRequirePendingEntity.setBoxGoodsVolume(BigDecimal.ZERO);
		}
		if(woodenRequirePendingEntity.getSalverGoodsNum()==null)
		{
			woodenRequirePendingEntity.setSalverGoodsNum(0);
		}
		/**
		 * 打木架
		 * **/
		 //理论体积
	      BigDecimal throeyFVoume=woodenRequirePendingEntity.getStandGoodsVolume();
	       //实际体积
	      BigDecimal actFVoume=packageAssistPriceEntity.getActualFrameVolume();
	       //最后体积
	      BigDecimal voumeF=null;
	    
	      if(throeyFVoume.compareTo(BigDecimal.ZERO)>0&&actFVoume.compareTo(BigDecimal.ZERO)>0 )
	      {
	    	  BigDecimal resultF=(actFVoume.subtract(throeyFVoume)).divide(throeyFVoume,2,BigDecimal.ROUND_HALF_UP);
	    	  if(resultF.compareTo(compareConstant)>0){
	    		  voumeF=throeyFVoume.multiply(relationConstant);
	    		  
	    	  }else{
	    		  voumeF=throeyFVoume;
	    		  
	    	  }
	    
	      }else if(throeyFVoume.compareTo(BigDecimal.ZERO)>0&&actFVoume.compareTo(BigDecimal.ZERO)==0){
	    	  voumeF=BigDecimal.ZERO;
	      }else
	      {
	    	  voumeF=actFVoume;
	      }
	     
	     //理论打木架体积
	     packageAssistPriceEntity.setTheoryFrameVolume(throeyFVoume);
	     //实际打木架体积
	     packageAssistPriceEntity.setActualFrameVolume(actFVoume);
	     //打木架体积
	     packageAssistPriceEntity.setPackageFrameVolume(voumeF);
		
		/**
		 * 打木箱体积
		 * 
		 * **/
	 	   //理论体积
	      BigDecimal throeyBVoume=woodenRequirePendingEntity.getBoxGoodsVolume();
	       //实际体积
	      BigDecimal actBVoume=packageAssistPriceEntity.getActualWoodenVolume();
	       //最后体积
	      BigDecimal voumeB=null;
	    
	     if(throeyBVoume.compareTo(BigDecimal.ZERO)>0&&actBVoume.compareTo(BigDecimal.ZERO)>0)
	     {
	    	 BigDecimal resultB=(actBVoume.subtract(throeyBVoume)).divide(throeyBVoume,2, BigDecimal.ROUND_HALF_UP);
	    	 if(resultB.compareTo(compareConstant)>0){
	    		 voumeB=throeyBVoume.multiply(relationConstant);
	    		 
	    	 }else{
	    		 voumeB=throeyBVoume;
	    		 
	    	 }
	    	 
	     }else if(throeyBVoume.compareTo(BigDecimal.ZERO)>0&&actBVoume.compareTo(BigDecimal.ZERO)==0){
	    	 voumeB=BigDecimal.ZERO;
	     }else{
	    	 voumeB=actBVoume;
	     }
	      
	     //理论打木箱体积
	     packageAssistPriceEntity.setTheoryWoodenVolume(throeyBVoume);
	     //实际打木箱体积
	     packageAssistPriceEntity.setActualWoodenVolume(actBVoume);
	     //打木箱体积
	     packageAssistPriceEntity.setPackageWoodenVolume(voumeB);
	     
	    
	     BigDecimal maskNum=new BigDecimal(woodenRequirePendingEntity.getSalverGoodsNum());
		//判断录入的打木托个数是否大于开单时的打木托个数
	     if(packageAssistPriceEntity.getActualMaskNumber()==null)
	     {
	    	 packageAssistPriceEntity.setActualMaskNumber(BigDecimal.ZERO);
	     }
	     //由于前面有打木托的限制，这里的现在可以取消
	     /*if(packageAssistPriceEntity.getActualMaskNumber()!=null
	    		 &&packageAssistPriceEntity.getActualMaskNumber().compareTo(maskNum)>0)
	     {
	    	 throw new TfrBusinessException("录入的木托个数不能大于开单的木托个数");
	     }*/
	     //校验打木托个数
	    this.validateMask(packageAssistPriceEntity);
	     /**
	      *打木托 
	      * **/
	     packageAssistPriceEntity.setTheoryMaskNumber(maskNum);
	     //打木托个数
	     if(packageAssistPriceEntity.getActualMaskNumber()==null)
	     {
	    	 packageAssistPriceEntity.setPackageMaskNumber(BigDecimal.ZERO);
	     }else{
	    	 packageAssistPriceEntity.setPackageMaskNumber(packageAssistPriceEntity.getActualMaskNumber());
	    	 
	     }
	}
	
	
	
	/**
	 * 
	 * 处理保留小数位数
	 * 
	 * **/
	private List<PackageAssistPriceEntity> handlePoint(List<PackageAssistPriceEntity> packageAssistPriceEntityList)
	{
		LOGGER.info("处理小数位数开始");

		//DecimalFormat df = new DecimalFormat("#.00");
		//定义返回结果集
		List<PackageAssistPriceEntity> resultList=new  ArrayList<PackageAssistPriceEntity>();
		if(packageAssistPriceEntityList==null || packageAssistPriceEntityList.size()==0)
		{
			return null;
		}
		//循环，单条处理
		for(int i=0;i<packageAssistPriceEntityList.size();i++)
		{
			PackageAssistPriceEntity assistPriceEntity=packageAssistPriceEntityList.get(i);
			//实际打木架体积
			if(assistPriceEntity.getActualFrameVolume()!=null)
			{
				assistPriceEntity.setActualFrameVolume(assistPriceEntity.getActualFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
				/*assistPriceEntity.setActualFrameVolume(new BigDecimal(
						df.format(assistPriceEntity.getActualFrameVolume().doubleValue())));*/
			}
			//实际打木箱体积
			if(assistPriceEntity.getActualWoodenVolume()!=null)
			{
				assistPriceEntity.setActualWoodenVolume(assistPriceEntity.getActualWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setActualWoodenVolume(
						new BigDecimal(df.format(assistPriceEntity.getActualWoodenVolume().doubleValue())));*/
			}
			//理论打木架体积
			if(assistPriceEntity.getTheoryFrameVolume()!=null)
			{
				assistPriceEntity.setTheoryFrameVolume(assistPriceEntity.getTheoryFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setTheoryFrameVolume(
						new BigDecimal(df.format(assistPriceEntity.getTheoryFrameVolume().doubleValue())));*/
			}
			//理论打木箱体积
			if(assistPriceEntity.getTheoryWoodenVolume()!=null)
			{
				assistPriceEntity.setTheoryWoodenVolume(assistPriceEntity.getTheoryWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setTheoryWoodenVolume(
						new BigDecimal(df.format(assistPriceEntity.getTheoryWoodenVolume().doubleValue())));*/
			}
			//打木架体积
			if(assistPriceEntity.getPackageFrameVolume()!=null)
			{
				assistPriceEntity.setPackageFrameVolume(assistPriceEntity.getPackageFrameVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setPackageFrameVolume(
						new BigDecimal(df.format(assistPriceEntity.getPackageFrameVolume().doubleValue())));*/
			}
			//打木箱体积
			if(assistPriceEntity.getPackageWoodenVolume()!=null)
			{
				assistPriceEntity.setPackageWoodenVolume(assistPriceEntity.getPackageWoodenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setPackageWoodenVolume(
						new BigDecimal(df.format(assistPriceEntity.getPackageWoodenVolume().doubleValue())));*/
			}
			//气泡膜体积
			if(assistPriceEntity.getBubbVelamenVolume()!=null)
			{
				assistPriceEntity.setBubbVelamenVolume(assistPriceEntity.getBubbVelamenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setBubbVelamenVolume(
						new BigDecimal(df.format(assistPriceEntity.getBubbVelamenVolume().doubleValue())));*/
			}
			//木条长度
			if(assistPriceEntity.getWoodenBarLong()!=null)
			{
				assistPriceEntity.setWoodenBarLong(assistPriceEntity.getWoodenBarLong().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setWoodenBarLong(
						new BigDecimal(df.format(assistPriceEntity.getWoodenBarLong().doubleValue())));*/
			}
			//缠绕膜体积
			if(assistPriceEntity.getBindVelamenVolume()!=null)
			{
				assistPriceEntity.setBindVelamenVolume(assistPriceEntity.getBindVelamenVolume().setScale(2, BigDecimal.ROUND_HALF_UP));

				/*assistPriceEntity.setBindVelamenVolume(
						new BigDecimal(df.format(assistPriceEntity.getBindVelamenVolume().doubleValue())));*/
			}
			
			//将处理后的结果加入处理结果集中
			resultList.add(assistPriceEntity);
		}
		LOGGER.info("处理小数位数结束");

		return resultList;
    }
	
	/**
	 * 
	 * 判断是否当月开的单据
	 * 
	 * **/
	
	@SuppressWarnings("unused")
	private boolean isNowMonth(Date billTime)
	{
		boolean isNowMonth=false;
		//时间格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前时间
		String nowDate=df.format(new Date());
		//获得年份
		int nowYearStr=Integer.parseInt(nowDate.substring(0, PackagingConstants.SONAR_NUMBER_4));
		//获得月份
		int nowMonthStr=Integer.parseInt(nowDate.substring(PackagingConstants.SONAR_NUMBER_5, PackagingConstants.SONAR_NUMBER_7));
		
		//获取当前时间
		String billDate=df.format(billTime);
		//获得年份
		int billYearStr=Integer.parseInt(billDate.substring(0, PackagingConstants.SONAR_NUMBER_4));
		//获得月份
		int billMonthStr=Integer.parseInt(billDate.substring(PackagingConstants.SONAR_NUMBER_5, PackagingConstants.SONAR_NUMBER_7));
		//满足当前年月都相等
		if(nowYearStr==billYearStr&&nowMonthStr==billMonthStr)
		{
			isNowMonth=true;
		}
		
		return isNowMonth;
		
		
	}
	
	/**
	 * 初始化辅助包装金额基本信息
	 * 
	 * 
	 * **/
	
	private void initPackedAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity,WaybillEntity waybillEntity)
	{
		
		// 用户
		UserEntity user = FossUserContext.getCurrentUser();
		// 职员
		EmployeeEntity employee = user.getEmployee();

		//获取外场信息
		OrgAdministrativeInfoEntity org=getOutFieldCode();
		
		//set ID
		packageAssistPriceEntity.setId(UUIDUtils.getUUID());
		//开单部门
		packageAssistPriceEntity.setBillOrgCode(waybillEntity.getCreateOrgCode());
		//开单部门名称
		String billOrgName=OrgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode());
		
		packageAssistPriceEntity.setBillOrgName(billOrgName);
		
		//外场code
		packageAssistPriceEntity.setPackageOrgCode(org.getCode());
		//外场
		packageAssistPriceEntity.setPackageOrgName(org.getName());
		//创建人code
		packageAssistPriceEntity.setCreateUserCode(employee.getEmpCode());
		//创建人
		packageAssistPriceEntity.setCreateUserName(employee.getEmpName());
		//创建日期
		packageAssistPriceEntity.setCreateTime(new Date());
		//修改人code
		packageAssistPriceEntity.setModifyUserCode(employee.getEmpCode());
		//修改人
		packageAssistPriceEntity.setModifyUserName(employee.getEmpName());
		//修改日期
		packageAssistPriceEntity.setModifyTime(new Date());
		//创建部门code
		packageAssistPriceEntity.setCreateOrgCode(org.getCode());
		//创建部门
		packageAssistPriceEntity.setCreateOrgName(org.getName());
		//每个单项体积，不可大于开单时的总体积
		if(packageAssistPriceEntity.getActualFrameVolume()!=null 
				&& packageAssistPriceEntity.getActualFrameVolume().compareTo(waybillEntity.getGoodsVolumeTotal())>0)
		{
			throw new TfrBusinessException("实际打木架体积不能大于开单的总体积");
		}
		if(packageAssistPriceEntity.getActualWoodenVolume()!=null 
				&& packageAssistPriceEntity.getActualWoodenVolume().compareTo(waybillEntity.getGoodsVolumeTotal())>0)
		{
			throw new TfrBusinessException("实际打木箱体积不能大于开单的总体积");
		}
		packageAssistPriceEntity.setActive(PackagingConstants.PACKAGING_Y);
		packageAssistPriceEntity.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT);
		
	}
	
	
	/***
	 * 应付金额计算规则:不滿足2、3、4時取1
	 * 1. 应付金额=实际打木架体积*打木架单价+实际打木箱个数*打木箱单价+实际打托个数*打木托单价+
	 *          打包带根数*打包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价;
	 * 2. 当实际打木架体积小于或等于起步体积时：应付金额=最低一票+实际打木箱个数*打木箱单价+实际打托个数*打木托单价+
	 *          打包带根数*包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价膜体积*缠绕膜单价;
	 * 3. 当实际打木箱体积小于或等于起步体积时：应付金额=最低一票+实际打木架个数*打木架单价+实际打托个数*打木托单价+
	 *          打包带根数*包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价膜体积*缠绕膜单价;
	 * 4. 当打木架体积小于或等于起步体积时且当打木箱体积小于或等于起步体积时：应付金额=打木架最低一票费用 +打木箱最低一票费用+加木托个数*加木托单价+
	 *          打包带根数*包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价膜体积*缠绕膜单;
	 * 
	 * **/
	
	private void calculatePackagePayableMoney(PackageAssistPriceEntity packageAssistPriceEntity,
			PackagingSupplierEntity packagingSupplierEntity)
	{
		LOGGER.info("计算应付金额");
		
		//校验基础数据的合法性
		checkBaseInfo(packageAssistPriceEntity,packagingSupplierEntity);
		
		//应付金额
		BigDecimal payablePirce=null;
		
		
		//打木架起步价体积
		BigDecimal frameStartV=null;
		//打木架最低一票费用
		BigDecimal frameMinPice=null;
		//打木架单价
		BigDecimal framePice=null;
		if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0)
		{
			frameStartV=new BigDecimal(packagingSupplierEntity.getWoodenFrameStartVolume());
			frameMinPice=new BigDecimal(packagingSupplierEntity.getWoodenFrameMin());
			framePice=new BigDecimal(packagingSupplierEntity.getWoodenFrame());
		}
	
		
		//打木箱起步价体积
		BigDecimal boxStartV=null;
		//打木箱最低一票费用
		BigDecimal boxMinPice=null;
		//打木箱单价
		BigDecimal boxPrice=null;

		if(packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0)
		{
			boxStartV=new BigDecimal(packagingSupplierEntity.getWoodBoxStartVolume());
			boxMinPice=new BigDecimal(packagingSupplierEntity.getWoodBoxMin());
			boxPrice=new BigDecimal(packagingSupplierEntity.getWoodBox());
		}
		
		
		

		/**
		 * 单独计算 加木托个数*加木托单价+打包带根数*包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价
		 * **/
		BigDecimal otherResult=calculatePackagePayableMoneyTow(packageAssistPriceEntity,packagingSupplierEntity);
		
		BigDecimal resultFB=BigDecimal.ZERO;
		
		/**
		 * 1. 当打木架体积小于或等于起步体积且打木箱体积大于起步价体积时：resultFB=木架最低一票+实际打木箱个数*打木箱单价;
		 * **/
		if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)<=0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)>0)
		{
			
				
				resultFB=frameMinPice.add(packageAssistPriceEntity.getPackageWoodenVolume().multiply(boxPrice));
		/**
		 * 2. 当打木架体积小于或等于起步体积且打木箱体积=0时：resultFB=木架最低一票
		 * **/
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)<=0
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)==0 )
		{
			   resultFB=frameMinPice;
	   /**
		 *3. 当打木箱体积小于或等于起步体积且打木架体积大于打木架起步体积：resultFB=木箱最低一票+实际打木架个数*打木架单价;
		 * 
		 * **/
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)>0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0 
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)<=0)
		{
			  resultFB=boxMinPice.add(packageAssistPriceEntity.getPackageFrameVolume().multiply(framePice));
	  /**
		 *4. 当打木箱体积小于或等于起步体积且打木架体积=0：resultFB=木箱最低一票;
		 * 
		 * **/  
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)==0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0 
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)<=0)
		{
			   resultFB=boxMinPice;
		/**
		 * 5. 当打木架体积小于或等于起步体积时且当打木箱体积小于或等于起步体积时：resultFB=打木架最低一票费用 +打木箱最低一票费用;
		 */   
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)<=0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0 
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)<=0)
		{
			    resultFB=boxMinPice.add(frameMinPice);
	    /**
		 * 6. 当打木架体积大于或等于起步体积时且当打木箱体积大于或等于起步体积时：resultFB=实际打木架体积*打木架单价+实际打木箱个数*打木箱单价;
		 */   
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)>0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0 
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)>0)
		{
			   resultFB=(packageAssistPriceEntity.getPackageWoodenVolume().multiply(boxPrice)).add(packageAssistPriceEntity.getPackageFrameVolume().multiply(framePice));
		   /**
		    * 7. 当打木架体积大于或等于起步体积时且当打木箱体积=0：resultFB=实际打木架体积*打木架单价;
		    */ 
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(frameStartV)>0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)==0 )
		
		{
			resultFB=packageAssistPriceEntity.getPackageFrameVolume().multiply(framePice);
			/**
			  * 8. 当打木架体积大于或等于起步体积时且当打木箱体积=0：resultFB=实际打木箱体积*打木箱单价;
			 */
		}else if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)==0
				&&packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(boxStartV)>0)
		{
			resultFB=packageAssistPriceEntity.getPackageWoodenVolume().multiply(boxPrice);
		}
		
		
		//保留两位小数
		//DecimalFormat df = new DecimalFormat("#.00");
		//最后加上 otherResult
		
		payablePirce=resultFB.add(otherResult).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		packageAssistPriceEntity.setPackagePayableMoney(payablePirce);
	}
	
	
	
	
	/***
	 * 
	 * 计算 加木托个数*加木托单价+打包带根数*包带单价+木条长度*木条单价+气泡膜体积*气泡膜单价+缠绕膜体积*缠绕膜单价
	 * 
	 * **/
	private BigDecimal calculatePackagePayableMoneyTow(PackageAssistPriceEntity packageAssistPriceEntity,
			PackagingSupplierEntity packagingSupplierEntity)
	{
		BigDecimal result=BigDecimal.ZERO;
		//打木托
		if(packageAssistPriceEntity.getPackageMaskNumber()!=null
				&&packageAssistPriceEntity.getPackageMaskNumber().compareTo(BigDecimal.ZERO)>0)
		{
			result=result.add(packageAssistPriceEntity.getPackageMaskNumber().multiply(new BigDecimal(packagingSupplierEntity.getWoodPallet())));
		}
		//打包带
		if(packageAssistPriceEntity.getBagBeltNum()>0)
		{
			result=result.add(new BigDecimal(packageAssistPriceEntity.getBagBeltNum()).multiply(new BigDecimal(packagingSupplierEntity.getBagLine())));
		}
		//打木条
		if(packageAssistPriceEntity.getWoodenBarLong()!=null
				&&packageAssistPriceEntity.getWoodenBarLong().compareTo(BigDecimal.ZERO)>0)
		{
			result=result.add(packageAssistPriceEntity.getWoodenBarLong().multiply(new BigDecimal(packagingSupplierEntity.getWood())));
		}
		//气泡膜
		if(packageAssistPriceEntity.getBubbVelamenVolume()!=null
				&&packageAssistPriceEntity.getBubbVelamenVolume().compareTo(BigDecimal.ZERO)>0)
		{
			result=result.add(packageAssistPriceEntity.getBubbVelamenVolume().multiply(new BigDecimal(packagingSupplierEntity.getBubblefilm())));
		}
		//缠绕膜
		if(packageAssistPriceEntity.getBindVelamenVolume()!=null
				&&packageAssistPriceEntity.getBindVelamenVolume().compareTo(BigDecimal.ZERO)>0)
		{
			result=result.add(packageAssistPriceEntity.getBindVelamenVolume().multiply(new BigDecimal(packagingSupplierEntity.getWrappingFilm())));
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * 校验基础数据的合法性
	 * 
	 * **/
	
	private void checkBaseInfo(PackageAssistPriceEntity packageAssistPriceEntity,PackagingSupplierEntity packagingSupplierEntity)
	{
	
		//校验基础资料打木架相关信息
		if(packageAssistPriceEntity.getPackageFrameVolume()!=null
				&&packageAssistPriceEntity.getPackageFrameVolume().compareTo(BigDecimal.ZERO)>0)
		{
			if(packagingSupplierEntity.getWoodenFrameStartVolume()==null
					|| StringUtils.isEmpty(packagingSupplierEntity.getWoodenFrameStartVolume()))
			{
				LOGGER.error("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木架起步价体积-为空,请维护包装供应商基础资料");
				throw new TfrBusinessException("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木架起步价体积-为空,请维护包装供应商基础资料");
			}
			
			
			//打木架最低一票费用
			if(packagingSupplierEntity.getWoodenFrameMin()==null
					||StringUtils.isEmpty(packagingSupplierEntity.getWoodenFrameMin()))
			{
				LOGGER.error("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木架最低一票费用-为空,请维护包装供应商基础资料");
				throw new TfrBusinessException("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木架最低一票费用-为空,请维护包装供应商基础资料");
			}
			
			if(packagingSupplierEntity.getWoodenFrame()==null 
					||StringUtils.isEmpty(packagingSupplierEntity.getWoodenFrame()))
			{
				LOGGER.error("打木架单价为空,请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料基础资料");
				throw new TfrBusinessException("打木架单价为空请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料基础资料");
			}
			
		}
	
		//校验基础资料打木箱相关信息
		if(packageAssistPriceEntity.getPackageWoodenVolume()!=null
				&&packageAssistPriceEntity.getPackageWoodenVolume().compareTo(BigDecimal.ZERO)>0)
		{
			//打木箱起步价
			if(packagingSupplierEntity.getWoodBoxStartVolume()==null
					||StringUtils.isEmpty(packagingSupplierEntity.getWoodBoxStartVolume()))
			{
				LOGGER.error("包装供应商基础资料-打木箱起步价体积-为空,请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
				throw new TfrBusinessException("包装供应商基础资料-打木箱起步价体积-为空,请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			}
			
			
			
			//打木箱最低一票费用
			if(packagingSupplierEntity.getWoodBoxMin()==null
					||StringUtils.isEmpty(packagingSupplierEntity.getWoodBoxMin()))
			{
				LOGGER.error("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木箱最低一票费用-为空,请维护包装供应商基础资料");
				throw new TfrBusinessException("包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料-打木箱最低一票费用-为空,请维护包装供应商基础资料");
			}
			
			
			
			//打木箱单价
			if(packagingSupplierEntity.getWoodBox()==null
					||StringUtils.isEmpty(packagingSupplierEntity.getWoodBox()))
			{
				LOGGER.error("打木箱单价为空,请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
				throw new TfrBusinessException("打木箱单价为空,请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			}
		}
		
		
		//基础资料木托单价
		if(packageAssistPriceEntity.getPackageMaskNumber()!=null
				&&packageAssistPriceEntity.getPackageMaskNumber().compareTo(BigDecimal.ZERO)>0
				&&(packagingSupplierEntity.getWoodPallet()==null||(StringUtils.isEmpty(packagingSupplierEntity.getWoodPallet()))))
		{
			LOGGER.error("木托单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			throw new TfrBusinessException("木托单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
		}
		
		//基础资料打包带单价
		
		if(packageAssistPriceEntity.getBagBeltNum()>0
				&&(packagingSupplierEntity.getBagLine()==null
				||(StringUtils.isEmpty(packagingSupplierEntity.getBagLine()))
				||new BigDecimal(packagingSupplierEntity.getBagLine()).compareTo(BigDecimal.ZERO)==0))
		{
			LOGGER.error("打包带单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			throw new TfrBusinessException("打包带单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
		}
		
		
		//基础资料木条单价
		if(packageAssistPriceEntity.getWoodenBarLong()!=null
				&&packageAssistPriceEntity.getWoodenBarLong().compareTo(BigDecimal.ZERO)>0
				&&(packagingSupplierEntity.getWood()==null
				||(StringUtils.isEmpty(packagingSupplierEntity.getWood()))
				||new BigDecimal(packagingSupplierEntity.getWood()).compareTo(BigDecimal.ZERO)==0))
		{
			LOGGER.error("木条单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			throw new TfrBusinessException("木条单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
		}
		
		
		//基础资料气泡膜单价
		if(packageAssistPriceEntity.getBubbVelamenVolume()!=null
				&&packageAssistPriceEntity.getBubbVelamenVolume().compareTo(BigDecimal.ZERO)>0
				&&(packagingSupplierEntity.getBubblefilm()==null
				||(StringUtils.isEmpty(packagingSupplierEntity.getBubblefilm()))
				||new BigDecimal(packagingSupplierEntity.getBubblefilm()).compareTo(BigDecimal.ZERO)==0))
		{
			LOGGER.error("气泡膜单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			throw new TfrBusinessException("气泡膜单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
		}
		
		//基础资料缠绕膜单价
		if(packageAssistPriceEntity.getBindVelamenVolume()!=null
				&&packageAssistPriceEntity.getBindVelamenVolume().compareTo(BigDecimal.ZERO)>0
				&&(packagingSupplierEntity.getWrappingFilm()==null
				||(StringUtils.isEmpty(packagingSupplierEntity.getWrappingFilm()))
				||new BigDecimal(packagingSupplierEntity.getWrappingFilm()).compareTo(BigDecimal.ZERO)==0))
		{
			LOGGER.error("缠绕膜单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
			throw new TfrBusinessException("缠绕膜单价为空，请维护包装供应商:{"+packagingSupplierEntity.getPackagingSupplier()+"}基础资料");
		}
		
	}
	
	
	/**
	 * 
	 * @desc:根据ID删除辅助包装信息
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-14
	 * */
	public void deletePackedAssistPriceById(String id){
		
		//1 判断该记录是否审核
		PackageAssistPriceEntity packageAssistPriceEntity=packageAssistPriceDao.queryPackedAssistPriceById(id);
		
		if(packageAssistPriceEntity==null)
		{
			throw new TfrBusinessException("作废记录ID为空");
		}
		
		String auditStatus=packageAssistPriceEntity.getAuditStatus();
		if(StringUtils.isEmpty(auditStatus))
		{
			throw new TfrBusinessException("作废记录不存在审核状态");
		}
		if(auditStatus.equals(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED))
		{
			throw new TfrBusinessException("运单："+packageAssistPriceEntity.getWaybillNo()+" 已经审核，不能作废");
		}
		//设置ID
		packageAssistPriceEntity.setId(id);
			//2 调结算接口  作废财务单据
		this.handlerPayableBill(packageAssistPriceEntity, this.PAYABLE_BILL_OPERATE_TYPE_DELETE);
		
		// 3 辅助包装中作废本条记录
		packageAssistPriceDao.deletePackedAssistPriceById(id);
		
	}
	
	
	
	/**
	 * 
	 * 更新时，将旧辅助包装信息进行修改
	 * 
	 * **/
	private PackageAssistPriceEntity updateOldAssistPrice(EmployeeEntity employee,PackageAssistPriceEntity newAssist,PackageAssistPriceEntity oldAssist)
	{
		
		//修改人
		oldAssist.setModifyUserCode(employee.getEmpCode());
		oldAssist.setModifyUserName(employee.getEmpName());
		//修改时间
		oldAssist.setModifyTime(new Date());
		//打木架
		oldAssist.setActualFrameVolume(newAssist.getActualFrameVolume());
		//打木箱
		oldAssist.setActualWoodenVolume(newAssist.getActualWoodenVolume());
		//打木托
		
		oldAssist.setPackageMaskNumber(newAssist.getActualMaskNumber());
		oldAssist.setActualMaskNumber(newAssist.getActualMaskNumber());
		//木条长度
		oldAssist.setWoodenBarLong(newAssist.getWoodenBarLong());
		//气泡膜体积
		oldAssist.setBubbVelamenVolume(newAssist.getBubbVelamenVolume());
		//缠绕膜体积
		oldAssist.setBindVelamenVolume(newAssist.getBindVelamenVolume());
		//包带根数
		oldAssist.setBagBeltNum(newAssist.getBagBeltNum());
		return oldAssist;
		
	}
	
	
	
	/**
	 * 
	 * @desc:根据ID查询辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	public PackageAssistPriceEntity queryPackedAssistPriceById(String id)
	{
		return packageAssistPriceDao.queryPackedAssistPriceById(id);
		
	}
	

	/**
	 * 
	 * @desc:提供修改打木架、打木箱 辅助包装信息给主要包装进行更新 ，只修改打木箱、打木架体积
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	@Transactional
	public void updatePackageAssistPriceByMain(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		
		PackagingSupplierEntity resultEntity=getPackagingSupplier(packageAssistPriceEntity.getPackageSupplierCode(),
				packageAssistPriceEntity.getPackageOrgCode());
		
		packageAssistPriceEntity.setPackageSupplierName(resultEntity.getPackagingSupplier());
		
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(packageAssistPriceEntity.getModifyUserCode());
		if (employee == null) {
			LOGGER.error("请校验创建人工号是否存在");
			throw new PackagingException("请校验创建人工号是否存在");
		}
		//旧辅助包装信息
		PackageAssistPriceEntity oldPackageAssistPriceEntity=packageAssistPriceDao.queryPackedAssistPriceById(packageAssistPriceEntity.getId());
		
		//初始化
		boolean fvolMod=false;//是否修改木架体积
		boolean bvolMod=false;//是否修改木箱体积
		if(packageAssistPriceEntity.getActualFrameVolume()!=null
				&&packageAssistPriceEntity.getActualFrameVolume().compareTo(BigDecimal.ZERO)==0)
		{
			fvolMod=true;
		}
		if(packageAssistPriceEntity.getActualWoodenVolume()!=null
				&&packageAssistPriceEntity.getActualWoodenVolume().compareTo(BigDecimal.ZERO)==0)
		{
			bvolMod=true;
		}
		
		packageAssistPriceEntity.setActualFrameVolume(oldPackageAssistPriceEntity.getActualFrameVolume());
		packageAssistPriceEntity.setActualWoodenVolume(oldPackageAssistPriceEntity.getActualWoodenVolume());
		
		packageAssistPriceEntity.setPackageMaskNumber(oldPackageAssistPriceEntity.getPackageMaskNumber());
		packageAssistPriceEntity.setActualMaskNumber(oldPackageAssistPriceEntity.getActualMaskNumber());
		//木条长度
		packageAssistPriceEntity.setWoodenBarLong(oldPackageAssistPriceEntity.getWoodenBarLong());
		//气泡膜体积
		packageAssistPriceEntity.setBubbVelamenVolume(oldPackageAssistPriceEntity.getBubbVelamenVolume());
		//缠绕膜体积
		packageAssistPriceEntity.setBindVelamenVolume(oldPackageAssistPriceEntity.getBindVelamenVolume());
		//包带根数
		packageAssistPriceEntity.setBagBeltNum(oldPackageAssistPriceEntity.getBagBeltNum());
		
		
		//得到更新后的辅助包装信息
		PackageAssistPriceEntity newPackageAssistPriceEntity=updateOldAssistPrice(employee,packageAssistPriceEntity,oldPackageAssistPriceEntity);
		//重新计算打木架、打木箱体积
		
		if(fvolMod)
		{
			newPackageAssistPriceEntity.setActualFrameVolume(BigDecimal.ZERO);
			newPackageAssistPriceEntity.setPackageFrameVolume(BigDecimal.ZERO);
		}
		if(bvolMod)
		{
			newPackageAssistPriceEntity.setActualWoodenVolume(BigDecimal.ZERO);
			newPackageAssistPriceEntity.setPackageWoodenVolume(BigDecimal.ZERO);
		}
		/*if(newPackageAssistPriceEntity.getActualFrameVolume()!=null
				&&newPackageAssistPriceEntity.getActualFrameVolume().compareTo(BigDecimal.ZERO)==0)
		{
			newPackageAssistPriceEntity.setPackageFrameVolume(BigDecimal.ZERO);
		}
		if(newPackageAssistPriceEntity.getActualWoodenVolume()!=null
				&&newPackageAssistPriceEntity.getActualWoodenVolume().compareTo(BigDecimal.ZERO)==0)
		{
			newPackageAssistPriceEntity.setPackageWoodenVolume(BigDecimal.ZERO);
		}*/
		
		
		//计算包装金额
		calculatePackagePayableMoney(newPackageAssistPriceEntity,resultEntity);
		List<PackageAssistPriceEntity> assistList=new ArrayList<PackageAssistPriceEntity>();
		assistList.add(newPackageAssistPriceEntity);
		//保留2位小数
		assistList=handlePoint(assistList);
		newPackageAssistPriceEntity=assistList.get(0);
		packageAssistPriceDao.updatePackageAssistPriceByMain(newPackageAssistPriceEntity);
		UserEntity user = new UserEntity();
		user.setEmployee(employee);
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
							.queryOrgAdministrativeInfoByCode(newPackageAssistPriceEntity.getPackageOrgCode(),
							bizTypes);
	   if(org==null)
	   {
		   throw new TfrBusinessException("当前部门不存在");
		   
	   }
		CurrentInfo currentInfo = new CurrentInfo(user, org);
	    //这里需调结算接口重新更改财务单据
		PackingRecAndPayTfrDto packingRecAndPayTfrDto = this.typeConversion(newPackageAssistPriceEntity);
		List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
		packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);

		
		/**
		 * 转换为同步至CUBC类
		 */
		List<PackingToCubcEntity> packingToCubcEntitys = new ArrayList<PackingToCubcEntity>();
		PackingToCubcEntity cubcEntity = this.typeConversion(packageAssistPriceEntity, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
		packingToCubcEntitys.add(cubcEntity);
		
		//封装灰度实体，类型是运单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
		parDto.setSourceBillNos(new String[] {packingRecAndPayTfrDtoList.get(0).getWaybillNo()});
		//调用灰度
		VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
		if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
			try {
				
				packingRecAndPayForTfrService.update(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
				
			} catch (SettlementException e) {
				LOGGER.error("");
				throw new TfrBusinessException("修改辅助包装信息调结算接口异常："+e.getErrorCode());
			}
		} else {
			ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();
			String requestStr = JSONObject.toJSONString(packingToCubcEntitys);
			
			responseDto = fossToCubcService.pushUpdatePacking(requestStr);

			if (null == responseDto) {
				throw new TfrBusinessException("同步信息至CUBC异常：返回参数为null");
			} else if (StringUtils.equals(responseDto.getResult(), AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
				throw new TfrBusinessException("同步信息至CUBC异常：" + responseDto.getReason());
			}
		}
	}
	
	
	/**
	 * 
	 * 根据包装供应商code、外场code 获取包装信息
	 * 
	 * **/
	
	private PackagingSupplierEntity getPackagingSupplier(String supplierCode,String outFieldCode)
	{
		
		//调综合接口获取包装供应商信息 todo
		PackagingSupplierEntity queryEntity=new PackagingSupplierEntity();
		
		PackagingSupplierEntity resultEntity=new PackagingSupplierEntity();
		queryEntity.setPackagingSupplierCode(supplierCode);
		queryEntity.setOrgCode(outFieldCode);
		try {
			resultEntity= packagingSupplierService.queryPackagingSupplierByEntity(queryEntity.getOrgCode(),queryEntity.getPackagingSupplierCode());
			if(resultEntity==null)
			{
				LOGGER.error("包装供应商不存在");
				throw new TfrBusinessException("包装供应商不存在");
			}
		} catch (BusinessException e) {
			LOGGER.error("获取包装供应商时，调综合接口异常：{"+e.getErrorCode()+"}");
			throw new TfrBusinessException("获取包装供应商时，调综合接口异常：{"+e.getErrorCode()+"}");
		}
		
		return resultEntity;
	}
	
	
	/*
	 * @desc 获取当前外场部门的code
	 * @author foss-105795-wqh
	 * @param 
	 * @ date 2014-04-11
	 * 
	 * */
	private OrgAdministrativeInfoEntity getOutFieldCode(){
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
							bizTypes);
	   if(org==null)
	   {
		   throw new TfrBusinessException("当前部门不存在");
		   
	   }
	   return org;
     }
    
	
	/**
	 * 
	 * @desc:导出辅助包装金额信息
	 * @author foss中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-27
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List exportPackedAssistPriceExcel(QueryAssistPackedDto queryAssistPackedDto){
		

		LOGGER.info("导出辅助包装金额信息");
		List<PackageAssistPriceEntity> packageAssistPriceList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(queryAssistPackedDto==null)
		{
			LOGGER.info("参数不合法");
			throw new TfrBusinessException("参数不合法");
		}
		
	   
		packageAssistPriceList= queryAssistPirceList(queryAssistPackedDto);

		//判断导出数据数量
		if(packageAssistPriceList.size()>PackagingConstants.PACKAGE_PAYBILL_EXPORT_MAX_RECORD_NUM)
		{
			LOGGER.info("导出记录过多，请缩小时间端,分批导出");
			throw new TfrBusinessException("导出记录过多，请缩小查询时间段,分批导出");
		}
		//处理小数位数
		packageAssistPriceList=handlePoint(packageAssistPriceList);
		
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(packageAssistPriceList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		
		for(PackageAssistPriceEntity packageAssistPrice : packageAssistPriceList){
			List<String> columnList = new ArrayList<String>();
			//运单号
			columnList.add(packageAssistPrice.getWaybillNo()==null ?"":packageAssistPrice.getWaybillNo());
			//包装时间
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH：mm：ss"); 
			
			columnList.add(packageAssistPrice.getCreateTime()==null ?"":time.format(packageAssistPrice.getCreateTime()));
			//开单部门
			columnList.add(packageAssistPrice.getBillOrgName()==null?"":packageAssistPrice.getBillOrgName());
			//实际打木架体积(方)
			columnList.add(packageAssistPrice.getActualFrameVolume()==null?"":packageAssistPrice.getActualFrameVolume().toString());
			//实际打木箱体积(方)
			columnList.add(packageAssistPrice.getActualWoodenVolume()==null? "":packageAssistPrice.getActualWoodenVolume().toString());
			//实际打木托个数
			columnList.add(packageAssistPrice.getActualMaskNumber()==null?"":packageAssistPrice.getActualMaskNumber().toString());
			//理论打木架体积(方)
			columnList.add(packageAssistPrice.getTheoryFrameVolume()==null?"":packageAssistPrice.getTheoryFrameVolume().toString());
			//理论打木箱体积(方)
			columnList.add(packageAssistPrice.getTheoryWoodenVolume()==null?"":packageAssistPrice.getTheoryWoodenVolume().toString());
			//理论打托个数
			columnList.add(packageAssistPrice.getTheoryMaskNumber()==null?"":packageAssistPrice.getTheoryMaskNumber().toString());
			
			//木条长度(米)
			columnList.add(packageAssistPrice.getWoodenBarLong()==null ?"":packageAssistPrice.getWoodenBarLong().toString());
			//气泡膜体积(方)
			columnList.add(packageAssistPrice.getBubbVelamenVolume()==null ?"":packageAssistPrice.getBubbVelamenVolume().toString());
			//缠绕膜体积(方)
			columnList.add(packageAssistPrice.getBindVelamenVolume()==null?"":packageAssistPrice.getBindVelamenVolume().toString());
			//包带根数
			columnList.add(packageAssistPrice.getBagBeltNum()+"");
			//应付金额
			columnList.add(packageAssistPrice.getPackagePayableMoney()==null? "":packageAssistPrice.getPackagePayableMoney().toString());
			//包装供应商
			columnList.add(packageAssistPrice.getPackageSupplierName()==null?"":packageAssistPrice.getPackageSupplierName());
			//审核状态
			String auditStatus="";
			if(packageAssistPrice.getAuditStatus()!=null)
			{
				if(packageAssistPrice.getAuditStatus().equals(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT))
				{
					auditStatus="未审核";
				}else{
					auditStatus="已审核";
				}
				
			} 
			columnList.add(auditStatus);
			//新增人
			columnList.add(packageAssistPrice.getCreateUserName()==null?"":packageAssistPrice.getCreateUserName());
			//修改人
			columnList.add(packageAssistPrice.getModifyUserName()==null?"":packageAssistPrice.getModifyUserName());
			
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"运单号",
				"包装时间",
				"开单部门",
				"实际打木架体积(方)",
				"实际打木箱体积(方)",
				"实际打木托个数",
				"理论打木架体积(方)",
				"理论打木箱体积(方)",
				"理论打托个数",	
				"木条长度(米)",
				"气泡膜体积(方)",
				"缠绕膜体积(方)",
				"包带根数",
				"应付金额",
				"包装供应商",
				"审核状态",	
				"新增人",
				"修改人"
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("PC端辅助包装金额信息");
	    //设置sheet行数
	    exportSetting.setSize(packageAssistPriceList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    List list = new ArrayList();
	    InputStream excelStream=null;
		try {
			// 获取输入流
			 excelStream = objExporterExecutor.exportSync(
					exportResource, exportSetting);

			String name="PC端辅助包装金额信息";
			String fileName;

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(name, "UTF-8");
			}
			list.add(fileName);
			list.add(excelStream);
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
		finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					LOGGER.error("{exportPackedAssistPriceExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		}
        
       
        //返回action
        return list;
		
	}
 
	/**
	 * @desc 下载导入辅助包装金额模版
	 * @return list
	 * @author foss中转-105795-wqh
	 * @date   2014-05-27
	 * 
	 * **/
	@SuppressWarnings("rawtypes")
	public List downloadMasterplateToAssistExcel()
	{
		LOGGER.info("下载导入辅助包装金额模版");
		List<List<String>> rowList = new ArrayList<List<String>>();
		
		List<String> columnList = new ArrayList<String>();
		rowList.add(columnList);
		
	
		
		String[] rowHeaders={
				"运单号",
				"包装供应商编码",
				"实际打木架体积(方)",
				"实际打木箱体积(方)",
				"实际打木托个数",
				"木条长度(米)",
				"气泡膜体积(方)",
				"缠绕膜体积(方)",
				"包带根数"
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("导入辅助包装金额模版");
	    //设置sheet行数
	    exportSetting.setSize(1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    List<Object> list = new ArrayList<Object>();
	    InputStream excelStream=null;
		try {
			// 获取输入流
			 excelStream = objExporterExecutor.exportSync(
					exportResource, exportSetting);

			String name="导入辅助包装金额模版";
			String fileName;

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(name, "UTF-8");
			}
			list.add(fileName);
			list.add(excelStream);
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
		finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					LOGGER.error("{exportPackedAssistPriceExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		}
        
       
        //返回action
        return list;
	}
	
	
	
	
	 /**
	 * @desc 导入辅助包装金额信息
	 * @return list
	 * @author foss中转-105795-wqh
	 * @date   2014-06-07
	 * 
	 * **/
	@Transactional
   public int importPackedAssistPriceInfo(List<PackageAssistPriceEntity> packageAssistPriceList)
   {
	   int importCount=0;
	   if(packageAssistPriceList==null||packageAssistPriceList.size()==0)
	   {
		   throw new TfrBusinessException("导入数据不能为空");
	   }
	   //获取所有运单号
	   List<String> waybillNoList=new ArrayList<String>();
	   //获取所有包装供应商编码
	   List<String> supplierCodeList=new ArrayList<String>();
	   for(int i=0;i<packageAssistPriceList.size();i++)
	   {
		   waybillNoList.add(packageAssistPriceList.get(i).getWaybillNo());
		   String supplierCode=packageAssistPriceList.get(i).getPackageSupplierCode();
		   if(!supplierCodeList.contains(supplierCode))
		   {
			   supplierCodeList.add(supplierCode);
		   }
		  
		  
	   }
	   
	   //查询运单开单时包装需求信息 
	   List<queryPackedWaybillNoDto> packedRequireList=packageAssistPriceDao.queryPackedWaybillNoRequire(waybillNoList);
	   List<String> waybillNos=new ArrayList<String>();
	   for(int i=0;i<packedRequireList.size();i++)
	   {
		   waybillNos.add(packedRequireList.get(i).getWaybillNo());
	   }
	  //判断导入运单是否存在包装需求,考虑性能问题，
	   for(int i=0;i<waybillNoList.size();i++)
	   {
		   if(!waybillNos.contains(waybillNoList.get(i))){
			   
			   LOGGER.error(waybillNoList.get(i)+":不存在该运单号或者不存在该运单的包装需求");
			   throw new TfrBusinessException(waybillNoList.get(i)+":不存在该运单号或者不存在该运单的包装需求");
		   }
	   }
	   
	   //判断是否已经存在导入的数据
	   List<PackageAssistPriceEntity>  existAssistList=
			   packageAssistPriceDao.queryAssistPackagePriceListByWaybillNo(waybillNoList,getOutFieldCode().getCode());
	   
	   if(existAssistList!=null&&existAssistList.size()>0)
	   {
		   //将信息做拼接传给前台
		   StringBuffer bufMsg=new StringBuffer();
		   for(int i=0;i<existAssistList.size();i++)
		   {
			   for(int j=0;j<packageAssistPriceList.size();j++)
			   {
				   if(existAssistList.get(i).getWaybillNo().equals(packageAssistPriceList.get(j).getWaybillNo())
						   &&existAssistList.get(i).getPackageSupplierCode().equals(packageAssistPriceList.get(j).getPackageSupplierCode()))
				   {
					   
					   bufMsg.append("运单号："+existAssistList.get(i).getWaybillNo()+","
					   		+ "包装供应商编码:"+existAssistList.get(i).getPackageSupplierCode()+" 记录已经存在;");   
				   }
			   }
			   
			 
		   }
		   String msg=bufMsg.toString();
		   if(msg!=null&&!msg.equals(""))
		   {
			   LOGGER.error(msg);
			   throw new TfrBusinessException(msg.toString());
		   }
		 
		   
	   }
	   //初始化导入辅助包装信息
	   initAssistImport(packageAssistPriceList,packedRequireList);
	   
	   //调综合接口，获取本次导入的包装供应商信息
	   List<PackagingSupplierEntity>  packagingSupplierList=null;
	   try {
		   packagingSupplierList=
				   packagingSupplierService.queryPackagingSupplierByEntityList(getOutFieldCode().getCode(),supplierCodeList);
		   
		   
		} catch (Exception e) {
		
			LOGGER.error("调综合接口获取包装供应商编码异常： "+e.getMessage());
			throw new TfrBusinessException("调综合接口获取包装供应商编码异常： "+e.getMessage());
		}
	   
	   if(packagingSupplierList==null||packagingSupplierList.size()==0)
	   {
		   LOGGER.error("导入的所有包装供应商编码不存在");
			throw new TfrBusinessException("导入的所有包装供应商编码不存在");
	   }
	  
	   //检查哪些包装供应商编码录入错误
	   List<String> supplierCodes=new ArrayList<String>();
	   for(int i=0;i<packagingSupplierList.size();i++)
	   {
		   if(!supplierCodes.contains(packagingSupplierList.get(i).getPackagingSupplierCode()))
		   {
			   supplierCodes.add(packagingSupplierList.get(i).getPackagingSupplierCode());
		   }
		 //初始化辅助包装实体中的包装供应商名称
		   for(int j=0;j<packageAssistPriceList.size();j++)
		   {
			   if(packageAssistPriceList.get(j).getPackageSupplierCode().equals(packagingSupplierList.get(i).getPackagingSupplierCode()))
			   {
				   packageAssistPriceList.get(j).setPackageSupplierName(packagingSupplierList.get(i).getPackagingSupplier());
			   }
		   }
		   
		  
	   }
	   for(int i=0;i<packageAssistPriceList.size();i++)
	   {
		   String supplierCode=packageAssistPriceList.get(i).getPackageSupplierCode();
		   if(!supplierCodes.contains(supplierCode))
		   {
			   LOGGER.error("运单： "+packageAssistPriceList.get(i).getWaybillNo()+" 对应的包装供应商编码:"+supplierCode+" 不存在,请改正后在导入");
				throw new TfrBusinessException("运单： "+packageAssistPriceList.get(i).getWaybillNo()+" 对应的包装供应商编码:"+supplierCode+" 不存在,请改正后在导入");

		   }
	   }
	   
	 
	  /*
	   * 这里需要判断主要包装金额中是否已经录入在的包装金额信息，如果存在，则做如下处理：
	   * 1 主要包装中打木架不为0，则辅助包装中打木架为0
	   * 2 主要包装中打木箱不为0，则辅助包装中打木箱为0
	   * 3 主要包装中打木架、打木箱不为0，则辅助包装中打木架、打木箱为0
	   */
	   
	   List<PackageMainPriceEntity> packageMainPriceList=
			   packageMainPriceService.queryMainPackagePriceListByWaybillNo(waybillNoList, getOutFieldCode().getCode());
	   if(packageMainPriceList!=null&&packageMainPriceList.size()>0)
	   {
		   
		   //找出在主要包装中已经录入的信息
		   for(int i=0;i<packageAssistPriceList.size();i++)
		   {
			   
			   for(int j=0;j<packageMainPriceList.size();j++)
			   {
				   //同一个运单、同一包装部门，同一包装供应商 则为一条记录
				   if(packageAssistPriceList.get(i).getWaybillNo().equals(packageMainPriceList.get(j).getWaybillNo())
						   &&packageAssistPriceList.get(i).getPackageSupplierCode().equals(packageMainPriceList.get(j).getPackageSupplierCode()))
				   {
					   //主要包装中打木架不为0，则辅助包装中打木架为0
					   if(packageMainPriceList.get(j).getActualFrameVolume()!=null
							   &&packageMainPriceList.get(j).getActualFrameVolume().compareTo(BigDecimal.ZERO)>0)
					   {
						   packageAssistPriceList.get(i).setActualFrameVolume(BigDecimal.ZERO);
					   }
					   
					   // 主要包装中打木箱不为0，则辅助包装中打木箱为0
					   if(packageMainPriceList.get(j).getActualWoodenVolume()!=null
							   &&packageMainPriceList.get(j).getActualWoodenVolume().compareTo(BigDecimal.ZERO)>0)
					   {
						   packageAssistPriceList.get(i).setActualWoodenVolume(BigDecimal.ZERO);
					   }
					   
				   }
				   
			   }
			   
		   }
		   
		   
	   }
	   
	   //计算打木架体积、打木箱体积
	   packageAssistPriceList=batchCalculateFrameAndBoxVolume(packageAssistPriceList);
	   PackageAssistPriceEntity packageAssistPriceEntity=null;
	   //处理循环
	   for(int i=0;i<packageAssistPriceList.size();i++)
	   {
		   packageAssistPriceEntity=packageAssistPriceList.get(i);
		   //计算包装金额
		   String supplierCode=packageAssistPriceEntity.getPackageSupplierCode();
		   for(int j=0;j<packagingSupplierList.size();j++)
		   {
			  
			   if(supplierCode.equals(packagingSupplierList.get(j).getPackagingSupplierCode()))
			   {
				   calculatePackagePayableMoney(packageAssistPriceList.get(i),packagingSupplierList.get(j));
			   }
		   }
		
	   }
	   
	   //处理小数位数,这里先计算包装金额减少误差
	   handlePoint(packageAssistPriceList);
	   //批量添加辅助包装金额信息
	   try {
		   packageAssistPriceDao.addPackageAssistPriceList(packageAssistPriceList);
		   importCount=packageAssistPriceList.size();
		} catch (Exception e) {
			LOGGER.error("批量添加包装异常："+e.getMessage());
			throw new TfrBusinessException("批量添加包装异常："+e.getMessage());
		}
	   
	   //调结算接口 批量新增财务单据
	   if(CollectionUtils.isNotEmpty(packageAssistPriceList)){
//		   PackingRecAndPayTfrDto packingRecAndPayTfrDto;
//		   List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList = new ArrayList<PackingRecAndPayTfrDto>();
//		   for(PackageAssistPriceEntity packageAssistPrice : packageAssistPriceList){
//			   packingRecAndPayTfrDto = this.typeConversion(packageAssistPrice);
//			   packingRecAndPayTfrDtoList.add(packingRecAndPayTfrDto);
//		   }
//		   CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
//		   try {
//			
//			   packingRecAndPayForTfrService.add(packingRecAndPayTfrDtoList, SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING, currentInfo);
//		   } catch (SettlementException e) {
//			  LOGGER.error("导入辅助包装时调结算接口异常："+e.getErrorCode());
//			  throw new TfrBusinessException("导入辅助包装时调结算接口异常："+e.getErrorCode());
//		  }
		   /**
		    * TODO
		    * 调用私有方法异步新增至CUBC
		    * @author 316759-RuipengWang-foss
		    * @date 2016-11-13 17:35:25
		    */
		   this.batchHandlerPayableBill(packageAssistPriceList, this.PAYABLE_BILL_OPERATE_TYPE_ADD);
	   }
	//扑捉异常信息
	  // this.batchHandlerPayableBill(packageAssistPriceList, this.PAYABLE_BILL_OPERATE_TYPE_ADD);
	   
	   return importCount;
   }
	
   /**
    * 
    * 初始化导入包装信息，并判断：
    * 1 托盘个数不能大于开单时个个数
    * 2 每个单项体积不能大于开单时的总体积
    * 
    * **/
   private void initAssistImport(List<PackageAssistPriceEntity> packageAssistPriceList,
		   List<queryPackedWaybillNoDto> packedRequireList)
   {
	  //非空校验
	   if(CollectionUtils.isEmpty(packageAssistPriceList))
	   {
		   throw new TfrBusinessException("导入数据为空");
	   }
	   if(CollectionUtils.isEmpty(packedRequireList))
	   {
		   throw new TfrBusinessException("导入数据不存在包装需求");
	   }
	   //运单集合，用来做打木托个数限制
	   List<String> waybillNoList=new ArrayList<String>();
	   //循环处理
	   for(int i=0;i<packageAssistPriceList.size();i++)
	   {
		   //获取导入辅助包装的运单号
		   String waybillNo=packageAssistPriceList.get(i).getWaybillNo();
		   waybillNoList.add(waybillNo);
		   
		   for(int j=0;j<packedRequireList.size();j++)
		   {
			   //如果是同一个运单号
			   if(waybillNo.equals(packedRequireList.get(j).getWaybillNo()))
			   {
				   PackageAssistPriceEntity packageAssistPriceEntity=packageAssistPriceList.get(i);
				   //校验打木托
				   this.validateMask(packageAssistPriceEntity);
				   
				   //1 托盘个数不能大于开单时个个数
				 /*  if(new BigDecimal(packedRequireList.get(j).getMaskNum()).compareTo(
						   packageAssistPriceEntity.getActualMaskNumber())<0)
				   {
					   LOGGER.error("运单："+waybillNo+" 打木托个数不能大于开单时个个数");
					   throw new TfrBusinessException("运单："+waybillNo+" 打木托个数不能大于开单时个数");

				   }*/
				   //理论打木托
				   packageAssistPriceList.get(i).setTheoryMaskNumber(new BigDecimal(packedRequireList.get(j).getMaskNum()));
				   //实际打木托,这里计算会有问题，故取消
				  // packageAssistPriceList.get(i).setPackageMaskNumber(new BigDecimal(packedRequireList.get(j).getMaskNum()));
				   /**
				    * 2 每个单项体积不能大于开单时的总体积
				    * */
				  //a 木架
				   if(packedRequireList.get(j).getGoodsTotalVolume().compareTo(
						   packageAssistPriceEntity.getActualFrameVolume())<0)
				   {
					   LOGGER.error("运单："+waybillNo+" 打木架体积不能大于运单开单时的总体积");
					   throw new TfrBusinessException("运单："+waybillNo+" 打木架体积不能大于运单开单时的总体积");

				   }
				   //理论打木架体积
				   packageAssistPriceList.get(i).setTheoryFrameVolume(packedRequireList.get(j).getFrameVolume());
				   //b 木箱
				   if(packedRequireList.get(j).getGoodsTotalVolume().compareTo(
						   packageAssistPriceEntity.getActualWoodenVolume())<0)
				   {
					   LOGGER.error("运单："+waybillNo+" 打木箱体积不能大于运单开单时的总体积");
					   throw new TfrBusinessException("运单："+waybillNo+" 打木箱体积不能大于运单开单时的总体积");

				   }
				   //理论打木箱体积
				   packageAssistPriceList.get(i).setTheoryWoodenVolume(packedRequireList.get(j).getBoxVolume());
				   
				  /**
				   * 初始化其他信息
				   * **/
				   
				   
				  // 用户
					UserEntity user = FossUserContext.getCurrentUser();
					// 职员
					EmployeeEntity employee = user.getEmployee();

					//获取外场信息
					OrgAdministrativeInfoEntity org=getOutFieldCode();
					
					//set ID
					packageAssistPriceList.get(i).setId(UUIDUtils.getUUID());
					//开单部门
					packageAssistPriceList.get(i).setBillOrgCode(packedRequireList.get(j).getBingOrgCode());
					//开单部门名称					
					packageAssistPriceList.get(i).setBillOrgName(packedRequireList.get(j).getBingOrgName());
					
					//外场code
					packageAssistPriceList.get(i).setPackageOrgCode(org.getCode());
					//外场
					packageAssistPriceList.get(i).setPackageOrgName(org.getName());
					//创建人code
					packageAssistPriceList.get(i).setCreateUserCode(employee.getEmpCode());
					//创建人
					packageAssistPriceList.get(i).setCreateUserName(employee.getEmpName());
					//创建日期
					packageAssistPriceList.get(i).setCreateTime(new Date());
					//修改人code
					packageAssistPriceList.get(i).setModifyUserCode(employee.getEmpCode());
					//修改人
					packageAssistPriceList.get(i).setModifyUserName(employee.getEmpName());
					//修改日期
					packageAssistPriceList.get(i).setModifyTime(new Date());
					//创建部门code
					packageAssistPriceList.get(i).setCreateOrgCode(org.getCode());
					//创建部门
					packageAssistPriceList.get(i).setCreateOrgName(org.getName());
					
					packageAssistPriceList.get(i).setActive(PackagingConstants.PACKAGING_Y);
					packageAssistPriceList.get(i).setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT);
				   
				   
			   }
			   
			   
		   }
	   }
	   
	   /**
	    * DMANA-71 :代打木架加托个数取数与实际业务逻辑不符
	    * */
	   List<WaybillEntity> waybillList=  waybillManagerService.queryWaybillBasicByNoList(waybillNoList);
	   if(waybillList==null || waybillList.size()==0)
	   {
		   LOGGER.error("校验打木托个数，查询所有运单信息时出错");
		   throw new TfrBusinessException("校验打木托个数，查询所有运单信息时出错");
	   }
	   for(int i=0;i<packageAssistPriceList.size();i++)
	   {
		   for(int j=0;j<waybillList.size();j++)
		   {
			   WaybillEntity waybillEntity=waybillList.get(j);
			   //同一个单号判断录入的加托个数是否正确
			   if(packageAssistPriceList.get(i).getWaybillNo().equals(waybillEntity.getWaybillNo()))
			   {
				   ValidatePackedDto validatePackeddto=new ValidatePackedDto();
				   validatePackeddto.setWaybillNo(waybillEntity.getWaybillNo());
				   validatePackeddto.setMaskQty(packageAssistPriceList.get(i).getActualMaskNumber().intValue());
				   //调用共用方法进行判断
				   packCommonService.checkWoodMask(waybillEntity, validatePackeddto);
			   }
			   
			   
		   }
	   }
	   
   }
   
   
   /**
	 * 
	 * @desc:根据多个运单号、多个包装包装供应商、包装部门查询已经生成的辅助包装金额信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return List<PackageAssistPriceEntity>
	 * @date   2014-05-22
	 * */
	public List<PackageAssistPriceEntity> queryAssistPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode)
	{
		return packageAssistPriceDao.queryAssistPackagePriceListByWaybillNo(waybillNoList,packageOrgCode);
		
	}
			

	
	/**
	 * 在导入数据时，为了提高系统性能采用批量计算打木架、打木箱体积
	 * 
	 * **/
	private List<PackageAssistPriceEntity> batchCalculateFrameAndBoxVolume(List<PackageAssistPriceEntity> packageAssistPriceList)
	{
		
		List<PackageAssistPriceEntity> resultList=new ArrayList<PackageAssistPriceEntity>();
		
		
		//关系常量
		BigDecimal relationConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_THEORY_ACTUAL_RELATION_CONSTANT);
		//比较常量
		BigDecimal compareConstant=new BigDecimal(PackagingConstants.PACKAGE_W_F_COMPARE_CONSTANT);
		
		for(int i=0;i<packageAssistPriceList.size();i++)
		{
			PackageAssistPriceEntity packageAssistPriceEntity=packageAssistPriceList.get(i);
			/**
			 * 打木架
			 * **/
			 //理论体积
		      BigDecimal throeyFVoume=packageAssistPriceEntity.getTheoryFrameVolume();
		       //实际体积
		      BigDecimal actFVoume=packageAssistPriceEntity.getActualFrameVolume();
		       //最后体积
		      BigDecimal voumeF=null;
		    
		      if(throeyFVoume.compareTo(BigDecimal.ZERO)>0&&actFVoume.compareTo(BigDecimal.ZERO)>0)
		      {
		    	  BigDecimal resultF=(actFVoume.subtract(throeyFVoume)).divide(throeyFVoume,2,BigDecimal.ROUND_HALF_UP);
		    	  if(resultF.compareTo(compareConstant)>0){
		    		  voumeF=throeyFVoume.multiply(relationConstant);
		    		  
		    	  }else{
		    		  voumeF=throeyFVoume;
		    		  
		    	  }
		    	  
		      }else if(throeyFVoume.compareTo(BigDecimal.ZERO)==0){
		    	  voumeF=actFVoume;
		      }else{
		    	  voumeF=BigDecimal.ZERO;
		      }
		    
		     //打木架体积
		     packageAssistPriceEntity.setPackageFrameVolume(voumeF);
			
			/**
			 * 打木箱体积
			 * 
			 * **/
		 	   //理论体积
		      BigDecimal throeyBVoume=packageAssistPriceEntity.getTheoryWoodenVolume();
		       //实际体积
		      BigDecimal actBVoume=packageAssistPriceEntity.getActualWoodenVolume();
		       //最后体积
		      BigDecimal voumeB=null;
		    
		     if(throeyBVoume.compareTo(BigDecimal.ZERO)>0&&actBVoume.compareTo(BigDecimal.ZERO)>0)
		     {
		    	 BigDecimal resultB=(actBVoume.subtract(throeyBVoume)).divide(throeyBVoume,2, BigDecimal.ROUND_HALF_UP);
		    	 if(resultB.compareTo(compareConstant)>0){
		    		 voumeB=throeyBVoume.multiply(relationConstant);
		    		 
		    	 }else{
		    		 voumeB=throeyBVoume;
		    		 
		    	 }
		    	 
		     }else if(throeyBVoume.compareTo(BigDecimal.ZERO)==0){
		    	 voumeB=actBVoume;
		     }else{
		    	 voumeB=BigDecimal.ZERO;
		     }
		      
		     //打木箱体积
		     packageAssistPriceEntity.setPackageWoodenVolume(voumeB);
			
		     resultList.add(packageAssistPriceEntity);	
			
		}
		
	     return resultList;
		
	}
	
	/** 
	 * @desc 审核辅助包装金额信息
	 * @return string
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午3:00:16
	 */
	@Override
	@Transactional
	public void auditPackedAssistPrice(
			List<String> idList) {
		PackageAssistPriceEntity afterPackageStatus = new PackageAssistPriceEntity();
		afterPackageStatus.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED);
		afterPackageStatus.setAuditDate(new Date());
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity employee = user.getEmployee();
		afterPackageStatus.setAuditorName(employee.getEmpName());
		afterPackageStatus.setAuditorCode(employee.getEmpCode());
		//未审核改为已审核
		List<PackageAssistPriceEntity> packageAssistPriceList = this.modifyPackedAssisPriceStatus(idList,
				PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT,
				afterPackageStatus);
		//扑捉异常信息 
		//财务审核接口
		this.batchHandlerPayableBill(packageAssistPriceList, this.PAYABLE_BILL_OPERATE_TYPE_AUDIT);
		
	}

	/** 
	 * @desc 反审核辅助包装金额信息
	 * @return string
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午3:00:16
	 */
	@Override
	@Transactional
	public void deauditPackedAssistPrice(
			List<String> idList) {
		PackageAssistPriceEntity afterPackageStatus = new PackageAssistPriceEntity();
		afterPackageStatus.setAuditStatus(PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT);
		afterPackageStatus.setDeauditDate(new Date());
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity employee = user.getEmployee();
		afterPackageStatus.setDeauditorName(employee.getEmpName());
		afterPackageStatus.setDeauditorCode(employee.getEmpCode());
		//已审核改为未审核
		List<PackageAssistPriceEntity> packageAssistPriceList = this.modifyPackedAssisPriceStatus(idList,
				PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED,
				afterPackageStatus);
		//扑捉异常信息
		this.batchHandlerPayableBill(packageAssistPriceList, this.PAYABLE_BILL_OPERATE_TYPE_DEAUDIT);
		
	}
	/**
	 * @desc 修改审核状态
	 * @return 
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午3:00:16
	 */		
	private List<PackageAssistPriceEntity> modifyPackedAssisPriceStatus(List<String> idList,String beforeStatus,PackageAssistPriceEntity afterStatus){
		if(CollectionUtils.isNotEmpty(idList)){
			List<PackageAssistPriceEntity> packageAssistList = packageAssistPriceDao.queryAssistPackagePriceListByIdList(idList);
			if(CollectionUtils.isNotEmpty(packageAssistList)){
				if(idList.size() != packageAssistList.size()){
					throw new TfrBusinessException("该组运单已经被操作，请刷新后重新选择");
				}else{
					mupdatePackageAssistPrice(idList, beforeStatus,
							afterStatus, packageAssistList);
					return packageAssistList;
				}
			}else{
				throw new TfrBusinessException("该组运单已经被操作，请刷新后重新选择");
			}
		}else{
			throw new TfrBusinessException("该组运单已经被操作，请刷新后重新选择");
		}
	}
	//sonar 优化 降低嵌套层数 218427
	private void mupdatePackageAssistPrice(List<String> idList,
			String beforeStatus, PackageAssistPriceEntity afterStatus,
			List<PackageAssistPriceEntity> packageAssistList) {
		boolean isExist;
		for(String id : idList){
			isExist = false;
			for(PackageAssistPriceEntity packageAssist : packageAssistList){
				if(id.equals(packageAssist.getId())){
					if(!beforeStatus.equals(packageAssist.getAuditStatus())){//如状态已更改，则提示错误信息
						throw new TfrBusinessException("运单"+packageAssist.getWaybillNo()+"已经被操作，请刷新后重新选择");
					}
					isExist = true;
				}
			}
			if(!isExist){//如果数据库中不存在active为Y的运单，则提示错误信息
				throw new TfrBusinessException("该组运单已经被操作，请刷新后重新选择");
			}
		}
		//更新状态
		packageAssistPriceDao.updatePackageAssistPriceStatus(idList, afterStatus);
	}
	
	/** 
	 * @desc 提供运单发生更改时修改代包装体积和生成应付单
	 * @return 
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-08 下午4:27:16
	 */	
	@Override
	public void updatePackageAssistPriceByWayBillNo(String wayBillNo){
		List<String> wayBillNoList=new ArrayList<String>();
		wayBillNoList.add(wayBillNo);
		List<PackageAssistPriceEntity> packageAssistPriceList=this.queryAssistPackagePriceListByWaybillNo(wayBillNoList,null);
		
		if(CollectionUtils.isNotEmpty(packageAssistPriceList)){
			for(PackageAssistPriceEntity entity:packageAssistPriceList){
				
				if(StringUtil.equals(entity.getAuditStatus(), PackagingConstants.PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED)){
					throw new TfrBusinessException("该运单在部门"+entity.getPackageOrgCode()+"的辅助包装信息已经审核通过，不允许修改");
				}
				this.updatePackageAssistForWayBill(entity);
				
			}
		}
	}
	
	/** 
	 * @desc 提供运单发生更改时修改代包装体积和生成应付单
	 * @return 
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-08 下午4:27:16
	 */	
	@Override
	@Transactional
	public void updatePriceByWayBillNo(String wayBillNo){
		//修改主要包装信息
		packageMainPriceService.updateMainPriceByWayBillNo(wayBillNo);
		//修改辅助包装信息
		this.updatePackageAssistPriceByWayBillNo(wayBillNo);
		
	} 
	
	//set dao and service

	public void setPackageAssistPriceDao(
			IPackageAssistPriceDao packageAssistPriceDao) {
		this.packageAssistPriceDao = packageAssistPriceDao;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setWoodenRequirementsService(
			IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService; 
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		OrgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPackageMainPriceService(
			IPackageMainPriceService packageMainPriceService) {
		this.packageMainPriceService = packageMainPriceService;
	}
	
	public void setPackingRecAndPayForTfrService(
			IPackingRecAndPayForTfrService packingRecAndPayForTfrService) {
		this.packingRecAndPayForTfrService = packingRecAndPayForTfrService;
	}
	
	public void setPackCommonService(IPackCommonService packCommonService) {
		this.packCommonService = packCommonService;
	}

	public void setPackagePriceToCubcService(
			IPackagePriceToCubcService packagePriceToCubcService) {
		this.packagePriceToCubcService = packagePriceToCubcService;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	
}
