/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-edi-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/edi/server/service/impl/EdiSignInfoService.java
 * 
 * FILE NAME        	: EdiSignInfoService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.edi.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.airservice.AirService;
import com.deppon.foss.airservice.CommonException;
import com.deppon.foss.esb.edi.server.air.AirArriveCountRequest;
import com.deppon.foss.esb.edi.server.air.AirArriveCountResponse;
import com.deppon.foss.esb.edi.server.air.AirInfo;
import com.deppon.foss.esb.edi.server.air.AirInfoQueryRequest;
import com.deppon.foss.esb.edi.server.air.AirInfoQueryResponse;
import com.deppon.foss.esb.edi.server.air.BatchSignInfoDetail;
import com.deppon.foss.esb.edi.server.air.BatchSignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.BatchSignInfoSendResponse;
import com.deppon.foss.esb.edi.server.air.SignInfo;
import com.deppon.foss.esb.edi.server.air.SignInfoSendProcessDetail;
import com.deppon.foss.esb.edi.server.air.SignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.SignInfoSendResponse;
import com.deppon.foss.esb.edi.server.air.StockCountRequest;
import com.deppon.foss.esb.edi.server.air.StockCountResponse;
import com.deppon.foss.esb.edi.server.air.WayBillInfo;
import com.deppon.foss.esb.edi.server.air.WaybillInfoRequest;
import com.deppon.foss.esb.edi.server.air.WaybillInfoResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 空运运单签收接口实现EDI
 * 
 * @author foss-meiying
 * @date 2012-11-26 下午4:28:24
 * @since
 * @version
 */
public class EdiSignInfoService implements AirService {
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.edi.server.service.impl.EdiSignInfoService";
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EdiSignInfoService.class.getName());
	/**
	 * 默认值1
	 */
	private static final int ONE = 1;
	/**
	 * 默认值0
	 */
	private static final int ZERO = 0;
	/**
	 * 运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 签收空运偏线货物service
	 */
	private IAirAgencySignService airAgencySignService;

	/**
	 * ActualFreight DAO
	 */
	private IActualFreightDao actualFreightDao;
	/**
	 * 结算应收已收服务
	 */
	private IBillReceivableService billReceivableService;
	private IVehicleAgencyDeptService  vehicleAgencyDeptService;

	/**
	 *  注册BigDecimal转换器，
	 *  否则Bigdecimal转换报错
	 */
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);//日期转换
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}

	/**
	 * Send sign info.
	 * @author foss-meiying
	 * @date 2012-12-25 上午9:25:56
	 * @param payload
	 * @param esbHeader
	 * @return 
	 * @see com.deppon.foss.airservice.AirService
	 * #sendSignInfo(com.deppon.foss.esb.edi.server.air.SignInfoSendRequest,
	 *  javax.xml.ws.Holder)
	 */
	@Override
	@Transactional
	public SignInfoSendResponse sendSignInfo(SignInfoSendRequest payload, Holder<ESBHeader> esbHeader) {
		esbHeader.value.setResponseId(payload.getSignInfoList().get(0).getWaybillNo());
		esbHeader.value.setResultCode(ONE);
		if (payload.getSignInfoList() == null) {
			SignInfoSendResponse response = new SignInfoSendResponse();
			// 调用明细
			SignInfoSendProcessDetail detail = new SignInfoSendProcessDetail();
			// 失败总数
			response.setFailCount(ONE);
			// 成功总数
			response.setSuccessCount(ZERO);
			// 原因
			detail.setReason("签收信息为空");
			// 添加到原因明细里
			response.getDetail().add(detail);
			// 记录
			LOGGER.error(" --签收信息为空 ");
			// 直接返回
			return response;
		} 
		LOGGER.info(" --签收操作开始----  ");
		SignInfoSendResponse response = sign(payload);
		LOGGER.info("--签收操作结束----");//记录日志
		// 返回信息
		return response;
	}
	
	
	private SignInfoSendResponse sign(SignInfoSendRequest payload) {
		SignInfoSendResponse response = new SignInfoSendResponse();
		// 得到请求的签收信息集合
		List<SignInfo> signInfoList = payload.getSignInfoList();
		// 成功总数
		int successCount = ZERO;
		// 失败总数
		int failureCount = ZERO;
		// 循环遍历运单签收list
		for (SignInfo signInfo : signInfoList) {
			WaybillSignResultEntity resultEntity = new WaybillSignResultEntity(signInfo.getWaybillNo(), FossConstants.ACTIVE);
			// 调用明细
			SignInfoSendProcessDetail detail = new SignInfoSendProcessDetail();
			// 根据运单号查询运单部分信息
			WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(signInfo.getWaybillNo());
			// 不存在
			if (waybill == null) {
				// 失败次数加1
				failureCount += ONE;
				// 失败原因
				detail.setReason("运单号为 " + signInfo.getWaybillNo() + " 在运单表里不存在");
				// 失败
				detail.setResult(false);
				LOGGER.error("--运单号为 " + signInfo.getWaybillNo() + " 在运单表里不存在");
			} else {// 存在
				// 查询运单签收结果表里是否存在该运单
				WaybillSignResultEntity signResult = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
				detail.setWaybillNo(signInfo.getWaybillNo());// 运单号
				// 如果存在
				if (signResult != null) {
					failureCount += ONE;// 失败次数加1
					// 失败原因
					detail.setReason("运单号为 " + signInfo.getWaybillNo() + " 在运单签收结果表里已经存在");
					detail.setResult(false);// 失败
					LOGGER.error(" --运单号为 " + signInfo.getWaybillNo() + " 在运单签收结果表里已经存在");//记录日志
				} else {
					try {
						// 如果不存在,添加到运单签收结果表里
						WaybillSignResultEntity signResultEntity = new WaybillSignResultEntity();
						this.initWaybillSignResult(signResultEntity, signInfo, waybill.getGoodsQtyTotal());
						this.addAirWaybill(signResultEntity, waybill);
						successCount += ONE;
						detail.setResult(true);// 成功
					} catch (BusinessException e) {
						// 失败次数加1
						failureCount += ONE;
						detail.setResult(false);// 失败
						// 原因
						detail.setReason(e.getMessage());
						LOGGER.error("--出错了" + e.getMessage(), e);//记录日志
					}
				}
			}
			// 添加调用明细
			response.getDetail().add(detail);
		}
		// 失败总数
		response.setFailCount(failureCount);
		// 成功总数
		response.setSuccessCount(successCount);
		return response;
	}
	
	/**
	 * 拼装签收部门信息.
	 * @author 038590-foss-wanghui
	 * @date 2013-3-3 下午5:30:41
	 * @param WaybillSignResultEntity  
	 * @return
	 * @see
	 */
	public CurrentInfo getCurrentInfo(WaybillSignResultEntity signResultEntity) {
		UserEntity user = new UserEntity();//new个对象
		EmployeeEntity employee = new EmployeeEntity();//new员工个对象
		employee.setEmpCode("EDI"); //员工编号
		employee.setEmpName(signResultEntity.getCreator());//员工名称
		user.setEmployee(employee);//添加员工信息
		user.setUserName(signResultEntity.getCreator());//用户信息
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(signResultEntity.getAgentCode());//部门编码
		// 获取操作部门
		OuterBranchEntity org = vehicleAgencyDeptService.queryOuterBranchByBranchCode(signResultEntity.getAgentCode(),DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
		if(org != null){
			dept.setName(org.getAgentDeptName());// 得到部门名称
		}
		return new CurrentInfo(user, dept);//返回用户信息
	}

	/**
	 * 	得到运单签收结果信息.
	 * @param signResultEntity  运单签收结果
	 * 		
	 * @param signInfo 
	 * 			the signInfo
	 * @param goodsQtyTotal 
	 * 			运单开单件数
	 * @author foss-meiying
	 * @date 2013-1-17 下午5:37:04
	 * @see
	 */
	private void initWaybillSignResult(WaybillSignResultEntity signResultEntity, SignInfo signInfo, Integer goodsQtyTotal) {
		signResultEntity.setWaybillNo(signInfo.getWaybillNo());// 运单号
		signResultEntity.setAgentCode(signInfo.getLadingStationNumber());// 代理网点编号
		signResultEntity.setCreator(signInfo.getSignBillCreator());//当前操作人
		signResultEntity.setSignNote(signInfo.getSignDesc());// 签收备注
		signResultEntity.setSignTime(signInfo.getSignTime());// 签收时间
		signResultEntity.setActive(FossConstants.ACTIVE);// 有效
		signResultEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);// 全部签收
		signResultEntity.setDeliverymanName(signInfo.getSigner());// 签收人
		signResultEntity.setSignSituation(signInfo.getSignState());// 签收情况
		signResultEntity.setSignGoodsQty(goodsQtyTotal);// 签收件数=运单开单件数
		// 到达时间
		signResultEntity.setArriveTime(signInfo.getArriveTime());
		// 送货人 
		signInfo.getDeliveryMan();
		// 送货方式
		signResultEntity.setReceiveMethod(StringUtils.isBlank(signInfo.getDeliveryType()) ? "" : signInfo.getDeliveryType());
		// 送货时间
		signResultEntity.setDeliverDate(signInfo.getDeliveryTime());
	}

	/**
	 * 添加空运信息
	 * @author foss-meiying
	 * @date 2013-3-8 下午5:46:20
	 * @param signResultEntity
	 * 			运单签收结果信息
	 * @param waybill
	 * 			运单信息
	 * @see
	 */
	private void addAirWaybill(WaybillSignResultEntity signResultEntity, WaybillEntity waybill) {
		CurrentInfo currentInfo = this.getCurrentInfo(signResultEntity);
		LineSignDto dto = new LineSignDto();
		dto.setIsWholeVehicle(waybill.getIsWholeVehicle());// 得到是否整车运单
		dto.setProductType(waybill.getProductCode());// 得到运输性质
		dto.setWaybillNo(signResultEntity.getWaybillNo());// 运单号
		airAgencySignService.signOperat(signResultEntity, currentInfo, dto, null,waybill);
	}
	
	/**
	 * 根据空运单号查询运单信息.
	 * 
	 * @param payload 
	 * 			the payload
	 * @param esbHeader 
	 * 			the esbHeader
	 * @return the waybill 
	 * 			info response
	 * @throws CommonException 
	 * 			the common exception
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 下午5:55:26
	 * @see com.deppon.foss.airservice.AirService
	 * #queryWaybill(com.deppon.esb.inteface.domain.air.WaybillInfoRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public WaybillInfoResponse queryWaybill(WaybillInfoRequest payload, Holder<ESBHeader> esbHeader) throws CommonException {
		esbHeader.value.setResponseId(payload.getWaybillNo());
		esbHeader.value.setResultCode(ONE);
		// 初始化条件
		AirWaybillCondition condition = new AirWaybillCondition();
		condition.setWaybillNo(payload.getWaybillNo());//运单号
		condition.setActive(FossConstants.ACTIVE);//有效
		// 查询运单的信息
		AirWaybillDto airWaybillDto = waybillSignResultService.queryWaybillInfoForEdi(condition);
		// copy属性
		AirInfo airInfo = null;
		try {
			if (airWaybillDto != null) {//如果查询运单的信息不为空
				if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(airWaybillDto.getProductCode())) {
					LOGGER.error("--该运单的运输性质不为精准空运，不能签收！");//记录日志
					throw new CommonException("该运单的运输性质不为精准空运，不能签收！");//抛出异常
				}
				airInfo = new AirInfo();
				BeanUtils.copyProperties(airInfo, airWaybillDto);//把airWaybillDto的内容复制到airInfo
				airInfo.setWayBillNo(payload.getWaybillNo());//运单号
				BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(payload.getWaybillNo());
				//财务单据查询，灰度改造   353654 ---------------------------start 
				List<BillReceivableEntity> billReceivableEntities = null;
				String vestSystemCode = null;
	            try {
	            	List<String> arrayList = new ArrayList<String>();
	            	arrayList.add(payload.getWaybillNo());
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryWaybill",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();		
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+payload.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("FOSS查询财务单据开始!运单号："+ payload.getWaybillNo());
					billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
					LOGGER.info("FOSS查询财务单据完成!运单号："+ payload.getWaybillNo());
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					try {
						billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(payload.getWaybillNo());			
					} catch (Exception e) {
						LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
						throw new CommonException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
					}
				}
				//财务单据查询，灰度改造   353654 ---------------------------end
				BigDecimal realArriveMoney = BigDecimal.valueOf(0);//赋值为0
				if(!CollectionUtils.isEmpty(billReceivableEntities)){
					for (BillReceivableEntity billReceivableEntity : billReceivableEntities){
						// 到达应收单
						if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(billReceivableEntity.getBillType()))
						{
							// 已收到付款
							airInfo.setArriveCharge(billReceivableEntity.getVerifyAmount());
							realArriveMoney = realArriveMoney.add(billReceivableEntity.getVerifyAmount());
						} // 代收货款应收单
						else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(billReceivableEntity.getBillType()))
						{
							// 已收代收款
							airInfo.setRealRefund(billReceivableEntity.getVerifyAmount());
							realArriveMoney = realArriveMoney.add(billReceivableEntity.getVerifyAmount());
						}
					}
				}
				airInfo.setRealArriveMoney(realArriveMoney);
				LOGGER.info("--调用结算接口后 ：" + ReflectionToStringBuilder.toString(airInfo));//记录日志
			}
		} catch (IllegalAccessException e) {//异常信息
			LOGGER.error(e.getMessage(), e);//记录日志
		} catch (InvocationTargetException e) {//捕获异常
			LOGGER.error(e.getMessage(), e);//记录日志
		}
		WaybillInfoResponse response = new WaybillInfoResponse();
		response.setWaybills(airInfo);//得到返回的运单信息
		return response;//返回结果 
	}

	/**
	 * 批量到达.
	 * 
	 * @param batchSignInfo
	 *  	the batchSignInfo
	 * @param esbHeader 
	 * 		the esbHeader
	 * @return the batch sign info send response
	 * @throws CommonException the common exception
	 * @author 038590-foss-wanghui
	 * @date 2013-3-8 上午9:42:13
	 * @see com.deppon.foss.airservice.AirService#batchSignInfo(com.deppon.foss.esb.edi.server.air.BatchSignInfoSendRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public BatchSignInfoSendResponse batchSignInfo(BatchSignInfoSendRequest batchSignInfo, Holder<ESBHeader> esbHeader) throws CommonException {
		if (batchSignInfo == null) {//如果传入的参数为空
			return null;
		}
		// 成功总数
		int successCount = ZERO;
		// 失败总数
		int failureCount = ZERO;
		// 构造ActualFreight
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		BatchSignInfoSendResponse response = new BatchSignInfoSendResponse();
		WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();//运单签收结果表
		resultEntity.setActive(FossConstants.ACTIVE);//有效
		for (WayBillInfo wayBillInfo : batchSignInfo.getBatchWayBillList()) {//循环传入的对象集合
			try {
				BatchSignInfoDetail detail = new BatchSignInfoDetail(); //处理明细
				resultEntity.setWaybillNo(wayBillInfo.getWaybillNo());//运单号
				// 查询运单签收结果表里是否存在该运单
				WaybillSignResultEntity signResult = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
				if(signResult != null){//如果该运单已经签收
					failureCount+=ONE;//失败次数加1
					detail.setWaybillNo(wayBillInfo.getWaybillNo());//运单号
					detail.setResult(false);
					detail.setReason("运单号"+wayBillInfo.getWaybillNo()+"已经签收");
					response.getDetail().add(detail);
					continue;
				}
				actualFreightEntity.setWaybillNo(wayBillInfo.getWaybillNo());//运单号
				actualFreightEntity.setArriveTime(wayBillInfo.getSignTime());//到达时间
				// 更新运单到达时间
				actualFreightDao.updateArriveByWaybillNo(actualFreightEntity);
				successCount+=ONE;//成功次数加1
				detail.setWaybillNo(wayBillInfo.getWaybillNo());//运单号
				detail.setResult(true);
				response.getDetail().add(detail);
			} catch (Exception e) {//捕获异常
				BatchSignInfoDetail detail = new BatchSignInfoDetail();
				failureCount+=ONE;//失败次数加1
				detail.setWaybillNo(wayBillInfo.getWaybillNo());//运单号
				detail.setResult(false);
				detail.setReason(e.getMessage());//失败原因
				response.getDetail().add(detail);
			}
		}
		response.setSuccessCount(successCount);//成功次数
		response.setFailCount(failureCount);//失败次数
		return response;//返回信息
	}

	/**
	 * 
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-8 上午11:39:59
	 * @see com.deppon.foss.airservice.AirService
	 * #queryAirArriveCount(com.deppon.foss.esb.edi.server.air.AirArriveCountRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public AirArriveCountResponse queryAirArriveCount(AirArriveCountRequest payload, Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}

	/**
	 * 
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-8 上午11:39:59
	 * @see com.deppon.foss.airservice.AirService
	 * #queryStockCount(com.deppon.foss.esb.edi.server.air.StockCountRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public StockCountResponse queryStockCount(StockCountRequest payload, Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}

	/**
	 * 
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-8 上午11:39:59
	 * @see com.deppon.foss.airservice.AirService
	 * #queryAirInfo(com.deppon.foss.esb.edi.server.air.AirInfoQueryRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public AirInfoQueryResponse queryAirInfo(AirInfoQueryRequest payload, Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}

	/**
	 * Sets 
	 * 		the 运单签收结果service.
	 *
	 * @param waybillSignResultService 
	 * 		the new 运单签收结果service
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Gets 
	 * 		the 运单签收结果service.
	 *
	 * @return
	 * 		 the 运单签收结果service
	 */
	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	/**
	 * Sets 
	 * 		the 运单service.
	 *
	 * @param waybillManagerService 
	 * 		the new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * Sets 
	 * 		the 签收空运偏线货物service.
	 *
	 * @param airAgencySignService 
	 * 		the new 签收空运偏线货物service
	 */
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		this.airAgencySignService = airAgencySignService;
	}

	/**
	 * Sets 
	 * 		the actualFreight DAO.
	 *
	 * @param actualFreightDao
	 * 		 the new actualFreight DAO
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	/**
	 * Sets 
	 * 		the 结算应收已收服务.
	 *
	 * @param billReceivableService
	 * 		 the new 结算应收已收服务
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}


	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

}