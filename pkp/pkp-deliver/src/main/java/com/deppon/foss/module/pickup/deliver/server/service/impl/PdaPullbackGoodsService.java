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
 * PROJECT NAME	: pkp-deliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/PdaPullbackGoodsService.java
 * 
 * FILE NAME        	: PdaPullbackGoodsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service.impl;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPullbackGoodsService;
import com.deppon.foss.module.pickup.pda.api.shared.define.PdaConstants;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignOrGoodsBackToCrmService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExceptionDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PullbackRenewalDto;
import com.deppon.foss.module.pickup.sign.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * PDA客户货物签收   拉回货物接口实现
 * @author foss-meiying 
 * @date 2012-12-10 上午9:37:44
 * @since
 * @version
 */
public class PdaPullbackGoodsService implements IPdaPullbackGoodsService {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaPullbackGoodsService.class);
	/**
	 * 到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单service 
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 员工service
	 */
	private IEmployeeService employeeService;
	
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	/**
	 * 组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * IDeliverLoadTaskService
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;
	/**
	 * 派送单service
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * 交接 流水号service
	 */
	private IStayHandoverserialService stayHandoverserialService;
	/**
	 * 交接明细Service
	 */
	private IStayHandoverDetailService stayHandoverDetailService;
	/**
	 * 处理异常
	 * 主数据Service层
	 */
	private IExceptionProcessService exceptionProcessService;
	private IActualFreightDao actualFreightDao;
	
	private IWeixinSendService weixinSendService;
	
	private IGpsDeliverService gpsDeliverService;
	
	private IWaybillSignResultService waybillSignResultService;
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}


	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	
	private IDeliverbillDao deliverbillDao;
	
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	/** 
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	private ISignOrGoodsBackToCrmService signOrGoodsBackToCrmService;
	/**
	 * 交接单服务接口 
	 */
	private IDeliverHandoverbillService deliverHandoverbillService;
	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}

	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	
	
	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}
	
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}
	
	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}
	/**
	 * 
	 * 保存拉回货物信息
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2012-12-10 上午11:31:20
	 * @param pullbackgoodsDto.waybillNo
	 * 				 运单号
	 * 		  pullbackgoodsDto.arrivesheetNo 
	 * 				 到达联编号
	 * 		  pullbackgoodsDto.pullbackReason 
	 * 				拉回原因 
	 *   	  pullbackgoodsDto.driverCode  
	 *   			司机工号
	 *        pullbackgoodsDto.pullbackTime 
	 *        		拉回时间 
	 * 		  pullbackgoodsDto.signNote  
	 * 				备注
	 * 		  pullbackgoodsDto.pullbackQty 
	 * 				拉回件数
	 * 		  pullbackgoodsDto.vehicleNo 
	 * 				车牌号 
	 * @return
	 * @see
	 */
	public String addPullbackGoodsSign(PdaPullbackgoodsDto dto){
		LOGGER.info("--拉回货物开始"+ ReflectionToStringBuilder.toString(dto));//记录日志
		if(dto == null){
			LOGGER.error("拉回货物信息不能为空!!");//记录日志
			throw new PdaProcessException("拉回货物信息不能为空!");//拉回货物信息不能为空!!
		}
		if(StringUtil.isBlank(dto.getWaybillNo())){
			LOGGER.error("运单号为空!!");//记录日志
			throw new PdaProcessException("运单号为空!");//运单号为空!!
		}
		if(StringUtil.isBlank(dto.getArrivesheetNo())){
			LOGGER.error("到达联编号为空!!");//记录日志
			throw new PdaProcessException("到达联编号为空!");//到达联编号为空!
		}
		//查询运单号在运单表里是否存在
		boolean result = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
		//如果运单号不存在
		if(!result){
			LOGGER.error("运单号不存在！");//记录日志
			throw new PdaProcessException("运单号不存在！");//运单号不存在！
		}
		// 111111到达联
		this.updateArriveSheet(dto);
		DeliverbillDetailDto deliverbillDetailDto   = new DeliverbillDetailDto();
		//到达联编号
		deliverbillDetailDto.setArrivesheetNo(dto.getArrivesheetNo());
		//派送单状态  已取消
		deliverbillDetailDto.setStatus(DeliverbillConstants.STATUS_CANCELED);
		String deliverbillNo = deliverbillService.queryDeliverNoByArriveSheetNo(deliverbillDetailDto);
		//如果查询的派送单不为空
		if(StringUtils.isNotBlank(deliverbillNo)){
			StayHandoverDetailEntity detailEntity = new StayHandoverDetailEntity();
			//拉回件数
			detailEntity.setGoodsQty(dto.getPullbackQty());
			detailEntity.setWaybillNo(dto.getWaybillNo());//运单号
			detailEntity.setDeliverbillNo(deliverbillNo);//派送单号
			stayHandoverDetailService.addStayHandoverDetail(detailEntity);
			LOGGER.info("--交接明细添加成功"+ReflectionToStringBuilder.toString(detailEntity));//记录日志
			List<String> serialNos = deliverLoadTaskService.queryLastLoadSerialNos(deliverbillNo, dto.getWaybillNo());
			if(CollectionUtils.isEmpty(serialNos)){
				LOGGER.error("根据派送单编号，运单号查询拉回货物的流水号为空！");//记录日志
			}else {
				for (String serialNo : serialNos) {
					StayHandoverserialEntity stayHandoverserialEntity = new StayHandoverserialEntity();
					stayHandoverserialEntity.setSerailno(serialNo);//流水号
					stayHandoverserialEntity.settSrvStayHandoverdetailId(detailEntity.getId());//交接明细id
					stayHandoverserialService.addSelective(stayHandoverserialEntity);
				}
			}
		}else {
			LOGGER.error("查询派送单为空");//记录日志
			throw new PdaProcessException("查询派送单为空");//查询派送单为空
		}
		/**
		 * MANA-771
		 */
		createWeixinInfo(dto, deliverbillNo);
		//菜鸟项目 推送订单来源为‘TAOBAO’且运输性质为‘标准快递、3.60特惠件’的运单
		ExceptionDto exceptionDto=new ExceptionDto();
		exceptionDto.setWaybillNo(dto.getWaybillNo());
		exceptionDto.setSituation(ArriveSheetConstants.SITUATION_GOODS_BACK);
		
		//根据运单号，查询运单信息
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
		CurrentInfo currentInfo = this.getCurrentInfo(dto);// 获取当前用户
		boolean toReceiveResult = false; //给收货人发送短信返回结果
		//如果拉回原因为空
		if(StringUtils.isBlank(dto.getPullbackReason())){
			//签收备注记录 备注 字段
			exceptionDto.setExceptionType(dto.getSignNote());
		}else { 
			//如果拉回原因不为空
			//签收备注录入 拉回原因字段
			exceptionDto.setExceptionType(dto.getPullbackReason());
			
			LOGGER.info("--派件联系不上客户"+dto);//记录日志
			if(dto.getPullbackReason().equals(PdaConstants.CUSTOMER_NOT_CONTACT)){//派送拉回原因为“联系不到客户”
				
				//调用短信接口发送短信至客户
				toReceiveResult = waybillSignResultService.sendMessCustomer(dto,null, waybill, null,currentInfo,true);
				
				if(toReceiveResult){
					LOGGER.info("--派送短信，在线通知发送成功");//记录日志
				}else{
					LOGGER.info("--派送短信，在线通知发送失败");//记录日志
				}
			}
		}
		signOrGoodsBackToCrmService.exceptionOperatToCrm(exceptionDto, null);
		LOGGER.info("拉回货物完成");//记录日志
		return "拉回货物完成";	
	}
	
	/**
	 * 封装消息进行微信消息推送
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月21日 19:27:12
	 * @param dto
	 * @param deliverbillNo
	 */
	private void createWeixinInfo(PdaPullbackgoodsDto dto, String deliverbillNo) {
		//派送拉回
		WeixinSendDto weixinSendDto = new WeixinSendDto();
		//运单号
		weixinSendDto.setWaybillNo(dto.getWaybillNo());
		//如果拉回原因为空
		if(StringUtils.isBlank(dto.getPullbackReason())){
			//错误失败的原因
			weixinSendDto.setStateType(dto.getSignNote());
		}else { 
			//如果拉回原因不为空
			//错误失败的原因
			weixinSendDto.setStateType(dto.getPullbackReason());
		}
		//操作时间
		weixinSendDto.setCreateTime(dto.getPullbackTime());
		DeliverbillDto deliverbillDto = new DeliverbillDto();
		deliverbillDto.setDeliverbillNo(deliverbillNo);
		DeliverbillEntity deliverBillEntity = deliverbillDao.queryByDeliverbillDto(deliverbillDto);
		if(deliverBillEntity == null){
			LOGGER.info("派送单号为"+deliverbillNo+"后台查询不到");
		}else{
			//创建组织
			weixinSendDto.setDeliverOrgCode(deliverBillEntity.getCreateOrgCode());
			//封装派送人电话，所在部门的详细信息
			queryDriverTelephone(deliverBillEntity, weixinSendDto);
		}
		//推送消息
		LOGGER.info("单号为："+weixinSendDto.getWaybillNo()+"开始进行微信消息发送"+"模块：PDA签收");
		ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(weixinSendDto,WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE);
		if(ResultDto.FAIL.equals(resultDto.getCode())){
			LOGGER.info("单号为："+weixinSendDto.getWaybillNo()+"微信消息发送失败，错误详情："+resultDto.getMsg());
		}
		LOGGER.info("模块：PDA签收微信消息处理完毕");
		LOGGER.info("Foss取消送货任务至Gps开启");
		//推送消息
		com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto gpsResultDto = 
				gpsDeliverService.syscCancelDeliverTaskToGps(deliverBillEntity, dto.getWaybillNo());
		if(ResultDto.FAIL.equals(gpsResultDto.getCode())){
			LOGGER.info("单号为："+dto.getWaybillNo()+"Foss取消送货任务至Gps发送失败，错误详情："+gpsResultDto.getMsg());
		}
		LOGGER.info("模块：Foss取消送货任务至Gps处理完毕");
	}
	
	
	private void queryDriverTelephone(DeliverbillEntity deliverBillEntity, WeixinSendDto weixinSendDto) {
		//若司机工号不为空
		if (StringUtil.isNotEmpty(deliverBillEntity.getDriverCode())){
			//如果是快递派送单
			if(FossConstants.YES.equals(deliverBillEntity.getIsExpress())){
				if(StringUtils.isNotEmpty(deliverBillEntity.getVehicleNo())){
					ExpressVehicleDto expressVeh = new ExpressVehicleDto();
					expressVeh.setVehicleNo(deliverBillEntity.getVehicleNo());
					List<ExpressVehicleDto> expDtoList = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh,SettlementReportNumber.FIFTY , 0);
					if(expDtoList != null && expDtoList.size()>0){
						// 司机电话
						weixinSendDto.setDeliverManMobile(expDtoList.get(0).getMobilePhone());
						//司机姓名
						weixinSendDto.setDeliverManName(expDtoList.get(0).getEmpName());
					}else{
						EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(deliverBillEntity.getDriverCode());
						if(employEntity != null){
							// 司机电话
							weixinSendDto.setDeliverManMobile(employEntity.getMobilePhone());
							//司机姓名
							weixinSendDto.setDeliverManName(employEntity.getEmpName());
						}
					}				
				}
			}else{
				// 内部司机根据工号查询相关信息
				DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverBillEntity.getDriverCode());
				//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
				if (driverAssociationDto != null){
					// 司机电话
					weixinSendDto.setDeliverManMobile(driverAssociationDto.getDriverPhone());
					//司机姓名
					weixinSendDto.setDeliverManName(driverAssociationDto.getDriverName());
				} else{
				  // 外请司机根据车牌号进行查询
				  List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverBillEntity.getVehicleNo());
				  
				  if (org.apache.commons.collections.CollectionUtils.isNotEmpty(driverAssociationDtos)){
					  //司机姓名
					  weixinSendDto.setDeliverManName(driverAssociationDtos.get(0).getDriverName());
					  // 司机电话
					  weixinSendDto.setDeliverManMobile(driverAssociationDtos.get(0).getDriverPhone());
				  }
				}
			}
		//如果车牌号不为空
		} else if (StringUtil.isNotEmpty(deliverBillEntity.getVehicleNo())){
		    if(FossConstants.YES.equals(deliverBillEntity.getIsExpress())){
				if(StringUtils.isNotEmpty(deliverBillEntity.getVehicleNo())){
					ExpressVehicleDto expressVeh = new ExpressVehicleDto();
					expressVeh.setVehicleNo(deliverBillEntity.getVehicleNo());
					List<ExpressVehicleDto> expDtoList = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh,0 , SettlementReportNumber.FIFTY);
					if(expDtoList != null && expDtoList.size()>0){
						// 司机电话
						weixinSendDto.setDeliverManMobile(expDtoList.get(0).getMobilePhone());
						//司机姓名
						weixinSendDto.setDeliverManName(expDtoList.get(0).getEmpName());
					}else{
						EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(deliverBillEntity.getDriverCode());
						if(employEntity != null){
							// 司机电话
							weixinSendDto.setDeliverManMobile(employEntity.getMobilePhone());
							//司机姓名
							weixinSendDto.setDeliverManName(employEntity.getEmpName());
						}
					}				
				}
			}else{
		    	// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverBillEntity.getVehicleNo());
				
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(driverAssociationDtos)){
					//司机姓名
					weixinSendDto.setDeliverManName(driverAssociationDtos.get(0).getDriverName());
					// 司机电话
					weixinSendDto.setDeliverManMobile(driverAssociationDtos.get(0).getDriverPhone());
				}
		    }
		}
	}


	private CurrentInfo getCurrentInfo(PdaPullbackgoodsDto dto){
		//通过司机工号查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(dto.getDriverCode());
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//部门名称
		String deptName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(dto.getOperateOrgCode());
		if(emp != null){
			user.setEmployee(emp);//得到员工信息
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName(PdaConstants.DEFAULT_EMP_NAME_PDA);//员工名称
			emp.setEmpCode(dto.getDriverCode());//员工编码
			user.setEmployee(emp);//得到员工信息
		}
		//如果部门名称不为空.
		if(StringUtils.isNotBlank(deptName)){
			dept.setName(deptName);// 得到部门名称
		}else {//其他
			dept.setName(PdaConstants.DEFAULT_EMP_NAME_PDA);//默认的部门名称
		}
		//部门编码
		dept.setCode(dto.getOperateOrgCode());
		return new CurrentInfo(user, dept);//返回信息
	}
	/**
	 * 
	 * 更新到达联信息
	 * 
	 * @author foss-meiying
	 * 
	 * @date 2012-12-19 上午10:31:09
	 * 
	 * @param dto
	 * 
	 * @see
	 * 
	 */
	private void updateArriveSheet(PdaPullbackgoodsDto dto){
		LOGGER.info("修改到达联开始--"+ReflectionToStringBuilder.toString(dto));//记录日志
		ArriveSheetEntity entity = new ArriveSheetEntity();
		//到达联编号
		entity.setArrivesheetNo(dto.getArrivesheetNo());
		entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);//到达联的状态为派送中
		entity = arriveSheetManngerService.queryArriveSheetByEntity(entity);
		if(null!= entity){
			CurrentInfo currentInfo =this.getCurrentInfo(dto);
			//得到操作人
			entity.setOperator(currentInfo.getEmpName());
			//操作人工号==司机工号
			entity.setOperatorCode(dto.getDriverCode());
			//签收操作时间
			entity.setOperateTime(new Date());
			// 操作部门编码
			entity.setOperateOrgCode(dto.getOperateOrgCode());
			// 操作部门名称
			entity.setOperateOrgName(currentInfo.getCurrentDeptName());
			//到达联编号
			entity.setArrivesheetNo(dto.getArrivesheetNo());
			//是pda签收
			entity.setIsPdaSign(FossConstants.YES);
			//签收时间-拉回时间
			entity.setSignTime(dto.getPullbackTime());
			//到达联状态为拒收
			entity.setStatus(ArriveSheetConstants.STATUS_REFUSE);
			//签收件数为0
			entity.setSignGoodsQty(PdaConstants.ZERO);
			//签收情况为货物拉回
			entity.setSituation(ArriveSheetConstants.SITUATION_GOODS_BACK);
			entity.setSignStatus(SignConstants.SIGN_STATUS_NO);
			//如果拉回原因为空
			if(StringUtils.isBlank(dto.getPullbackReason())){
				//签收备注记录 备注 字段
				entity.setSignNote(dto.getSignNote());
			}else { 
				//如果拉回原因不为空
				//签收备注录入 拉回原因字段
				entity.setSignNote(dto.getPullbackReason());
			}
			//DN201503250001  再次送货时间
			entity.setNextDeliverTime(dto.getNextDeliverTime());
			//解决并发，设置要更改的到达联的状态必须为派送中
			entity.setOldStatus(ArriveSheetConstants.STATUS_DELIVER);

			//arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity);
			if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity)<= 0){
				LOGGER.error("--派送拉回操作失败，请重新查询一下"+ ReflectionToStringBuilder.toString(entity));//记录日志
				throw new PdaProcessException("派送拉回操作失败，到达联状态有误！");//该到达联无效
			}
			
			//派送拉回：调用轨迹接口，推送轨迹 --add by 231438 
			sendWaybillTrackService.packagingMethodForDeliveryBack(entity, currentInfo); //--add by 231438
			//派送拉回：调用轨迹接口，WQS推送轨迹 add by 243921
			sendWaybillTrackService.rookieTrackingsForDeliveryBack(entity);
			//app派送拉回原因优化 add by 231438;区分零担快递，零担走优化逻辑，快递走原来的流程逻辑
			//根据运单号，查询运单信息
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
			//快递
			if(PricingConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
				this.addExceptionProcessInfo(dto,entity,currentInfo,"EXPRESS");//添加异常信息
				// add by 353654 DN201608260005
		        PullbackRenewalDto pullbackRenewalDto = new PullbackRenewalDto();
		        pullbackRenewalDto.setEntity(entity);
		        pullbackRenewalDto.setCurrentInfo(currentInfo);
		        pullbackRenewalDto.setOrderNo(waybillEntity.getOrderNo());
		        LOGGER.info("封装调用updatePullBackStatus方法参数实体完成");
		        waybillSignResultService.updatePullBackStatus(pullbackRenewalDto);
		        LOGGER.info("FOSS推送OMS派送拉回原因完成");
			}else{ //零担
				//拉回原因为客户要求其他时间送货（客户），送货日期不为空 不添加异常信息，将运单入待交单
				if (ArriveSheetConstants.PULLBACK_REASON_OTHER_TIME_DELIVERY.equals(entity.getSignNote())){
					if(null == dto.getNextDeliverTime()){
						throw new PdaProcessException("再次派送时间不能为空");//再次派送时间不能为空
					}
				}
				//异常处理,添加异常信息
				this.addExceptionProcessInfo(dto,entity,currentInfo,null);//添加异常信息
				//派送交单退回DTO
				DeliverHandoverbillReturnDto returnDto = new DeliverHandoverbillReturnDto();
				//运单号
				returnDto.setWaybillNo(dto.getWaybillNo());
				//拉回件数==交单件数
				returnDto.setBillQty(dto.getPullbackQty());
				//再次派送时间
				returnDto.setPreDeliverDate(dto.getNextDeliverTime());
				// 调用接口 将运单进入待交单表
				deliverHandoverbillService.goodsBackReturnPreDeliverbill(returnDto);
			}
			//实际货物实体
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(entity.getWaybillNo());
			//到达联件数
			actualFreightEntity.setGenerateGoodsQty(entity.getArriveSheetGoodsQty());
			//排单件数
			actualFreightEntity.setArrangeGoodsQty(entity.getArriveSheetGoodsQty());
			//更新到达件数
			actualFreightDao.updateActualFreightPartByWaybillNo(actualFreightEntity);
		}else {
			LOGGER.error("该到达联无效--");//记录日志
			throw new PdaProcessException("该到达联无效");//该到达联无效
		}
	}
	/**
	 * @author 231438 
	 * 零担派送单拉回，添加异常信息(未处理异常)
	 * 封装方法共用，之前是在 更新到达联信息 方法中 
	 */
	private void addExceptionProcessInfo(PdaPullbackgoodsDto dto,ArriveSheetEntity entity,CurrentInfo currentInfo,String code){
		//异常签收时操作
		ExceptionEntity exceptionEntity = new ExceptionEntity();
		//运单号
		exceptionEntity.setWaybillNo(dto.getWaybillNo());
		//运单异常
		exceptionEntity.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
		//异常环节
		exceptionEntity.setExceptionLink(ExceptionProcessConstants.DELIVER);
		if("EXPRESS".equals(code)){//快递
			//异常状态 ---快递已处理
			exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
		}else{//零担
			//306566   DN201601290015  拉回原因 为客户要求其他时间送货（客户）的时候生成一条已处理的异常记录
			if(ArriveSheetConstants.PULLBACK_REASON_OTHER_TIME_DELIVERY.equals(dto.getPullbackReason())){//快递
				//异常状态 ---306566   DN201601290015  已处理
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
			}else{
				//异常状态 ---231438 零担修改为处理中的异常
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
			}
			
			//异常原因
			//exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(entity.getSignNote(),DictionaryConstants.PKP_PULLBACK_REASON));
			//异常操作  ---派送拉回
			exceptionEntity.setExceptiOperate(ExceptionProcessConstants.PKP_EXCEPTION_SEND_RETURN);
		}
		//     第二次优化
		//拉回备注----当拉回原因为其他原因的时候----把备注字段保存息306566  DN201601290015   拉回原因优化 
		if( ArriveSheetConstants.PULLBACK_REASON_OTHER.equals(dto.getPullbackReason())){
			if(StringUtils.isEmpty(dto.getSignNote())){
				exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(dto.getPullbackReason(), DictionaryConstants.PKP_PULLBACK_REASON));	
			}else{
				exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(dto.getPullbackReason(), DictionaryConstants.PKP_PULLBACK_REASON)+"【"+dto.getSignNote()+"】");	
			}
		}else{
			exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(dto.getPullbackReason(), DictionaryConstants.PKP_PULLBACK_REASON));	
		}
		
		exceptionEntity.setArrivesheetId(entity.getId());//到达联id
		//异常生成时间
		exceptionEntity.setExceptionTime(new Date());
		//登记人
		exceptionEntity.setCreateUserName(currentInfo.getEmpName() == null ? "" :currentInfo.getEmpName());
		//登记人编码
		exceptionEntity.setCreateUserCode(currentInfo.getEmpCode()== null ? "" : currentInfo.getEmpCode());
		//登记部门编码
		exceptionEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode()== null ? "" : currentInfo.getCurrentDeptCode());
		 //登记部门
		exceptionEntity.setCreateOrgName(currentInfo.getCurrentDeptName()== null ? "" : currentInfo.getCurrentDeptName());
		exceptionProcessService.addExceptionProcessInfo(exceptionEntity);//添加异常信息
	}
	/**
	 * 
	 * Sets 
	 * 			the 到达联service.
	 *
	 * @param arriveSheetManngerService 
	 * 			the new 到达联service
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * Sets 
	 * 		the 运单service.
	 *
	 * @param waybillManagerService 
	 * 		the new 运单service
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * Sets 
	 * 		the 员工service.
	 *
	 * @param employeeService 
	 * 		the new 员工service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * Sets 
	 * 		the 组织信息service.
	 *
	 * @param orgAdministrativeInfoService 
	 * 		the new 组织信息service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * Sets 
	 * 		the iDeliverLoadTaskService.
	 *
	 * @param deliverLoadTaskService 
	 * 		the new iDeliverLoadTaskService
	 */
	public void setDeliverLoadTaskService(IDeliverLoadTaskService deliverLoadTaskService) {
		this.deliverLoadTaskService = deliverLoadTaskService;
	}
	/**
	 * Sets 
	 * 		the 派送单service.
	 *
	 * @param deliverbillService 
	 * 		the new 派送单service
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	/**
	 * Sets
	 * 		 the 交接 流水号service.
	 *
	 * @param stayHandoverserialService 
	 * 		the new 交接 流水号service
	 */
	public void setStayHandoverserialService(IStayHandoverserialService stayHandoverserialService) {
		this.stayHandoverserialService = stayHandoverserialService;
	}
	/**
	 * Sets
	 * 		 the 交接明细Service.
	 *
	 * @param stayHandoverDetailService
	 * 
	 *  	the new 交接明细Service
	 */
	public void setStayHandoverDetailService(IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
	}
	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}

	public void setSignOrGoodsBackToCrmService(
			ISignOrGoodsBackToCrmService signOrGoodsBackToCrmService) {
		this.signOrGoodsBackToCrmService = signOrGoodsBackToCrmService;
	}
	
}