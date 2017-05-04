/**
 *  initial comments.
 */
/**
 * Copyright 2013 TFR TEAM
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/JointticketWayBillAction.java
 * 
 *  FILE NAME          :JointticketWayBillAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IFlightPricePlanService;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirLockWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSerialNoDetail;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirWayBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.PointsSingleJointTicketException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWaybillDetailVO;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.AirTransportRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.RepAirTransportDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.api.shared.vo.ResultBean;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;

import static com.deppon.foss.module.transfer.scheduling.api.shared.util.CompareUtils.LOGGER;

/**
 * 录入航空正单action 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午5:57:27
 */
public class JointticketWayBillAction extends AbstractAction {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JointticketWayBillAction.class);
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -687626259294636856L;

	/**
	 * 注入航空正单service
	 */
	private IAirWaybillDetailService pointsSingleJointTicketService;
	/**
	 * 注入航班信息service
	 */
	private IFlightService flightService;

	/**
	 * 航空司代理人service
	 */
	private IAirlinesAgentService airlinesAgentService;

	/**
	 * 运价方案服务
	 */
	@Resource
	private IFlightPricePlanService flightPricePlanService4Dubbo;

	/**
	 * 航空正单vo
	 */
	private AirWaybillDetailVO pointsSingleJointTicketVO = new AirWaybillDetailVO();
	
	/**
	 * 机场三字码接口
	 */
	private IAirportService airportService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/**空运锁票*/
	private IAirLockWaybillService airLockWaybillService;
	/**
	 * 航空正单vo
	 */
	private IFOSSToWkService fossToWkService;

	/** 注入外场服务. */
	private IOutfieldService outfieldService;
	
	/** 部门 复杂查询 service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 查询航空正单dao. */
	private IAirWaybillDetailDao pointsSingleJointTicketDao;
	
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:35:10
	 */
	@JSON
	public String queryWayBillInfo() {
		try {
            //根据当前部门取空运总调
            String deptCode = FossUserContext.getCurrentDeptCode();
            OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
            AirWaybillDetailDto ticketDto = pointsSingleJointTicketVO.getTicketDto();
            List<AirWaybillDetailEntity> list= new ArrayList<AirWaybillDetailEntity>();
           
            if(AirfreightConstants.PACKAGE_AIR.equals(pointsSingleJointTicketVO.getTransportType())){
                //设置运输性质
                ticketDto.setTransportType(pointsSingleJointTicketVO.getTransportType());
                /*****************首先按照20160418老方式查询*************************/
                list.addAll(pointsSingleJointTicketService.queryWaybillEntity(ticketDto));
                LOGGER.info("按照20160418老方式查询的结果： " + list.toString());
                if (configurationParamsService.queryTfrSwitch4Ecs()) {
                	//空运配载员所在外场
                	String transferCode = null;
                	
                	//判断当前登录部门是否是空运总调
                	if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
                		transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());		
                	}else{
                		List<String> bizTypesList = new ArrayList<String>();
                		//外场类型
                		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
                		//空运总调类型
                		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
                		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.
                				queryOrgAdministrativeInfoByCode(orgAdministrativeInfoEntity.getCode(), bizTypesList);
                		if (orgAdministrativeInfoEntity1 == null){
//                			throw new AirWayBillException("当前库存部门不存在！");
                			return returnError(new AirWayBillException("当前库存部门不存在！"));
                		} else {
                			transferCode = orgAdministrativeInfoEntity1.getCode();
                		}
                	}
                	//请求参数设置
                	AirTransportRequestEntity  airTransportRequest = new AirTransportRequestEntity();
                	if(StringUtils.isEmpty(ticketDto.getMakeWaybillWay())){
                		//查询全部
                		airTransportRequest.setBillWay(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
                	}else{
                		airTransportRequest.setBillWay(ticketDto.getMakeWaybillWay());
                	}
                	airTransportRequest.setBillBeginTime(ticketDto.getBeginInTime());
                	airTransportRequest.setBillEndTime(ticketDto.getEndInTime());
                	airTransportRequest.setCurrentOrgCode(transferCode);
                	airTransportRequest.setDestinationStation(ticketDto.getArrvRegionName());
                	airTransportRequest.setNextOrgCode(ticketDto.getNextDestOrg());
                	//库存中
                	airTransportRequest.setStockState(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
                	airTransportRequest.setStorageBeginInTime(ticketDto.getStorageBeginInTime());
                	airTransportRequest.setStorageEndInTime(ticketDto.getStorageEndInTime());
                	ResultBean<List<RepAirTransportDto>> resultBean = new ResultBean<List<RepAirTransportDto>>();
                	//调用航空正单查询接口
                	resultBean = fossToWkService.queryTicketInfo(airTransportRequest);
                	//航空正单查询接口返回参数设置
                	List<RepAirTransportDto> list1=resultBean.getData();
                	//航空正单明细界面
                	makeRetrunList(list, list1);
                }
                //list= pointsSingleJointTicketService.queryWaybillEntity(ticketDto);
            }else{
                //空运货量DTO.
				//设置目的站code
				ticketDto.setDestOrgCode(orgAdministrativeInfoEntity.getCode());
				//查询航空正单明细list	
				list = pointsSingleJointTicketService.queryWaybillEntity(ticketDto);
				//catch 异常
            }
        //设置航空正单明细list
        pointsSingleJointTicketVO.setBillDetailList(list);
        LOGGER.info("返回页面的列表： " + list);
        } catch (AirWayBillException e) {
			//返回错误信息
        	LOGGER.error("查询航空正单系统异常 AirWayBillException ",e);
			return super.returnError(e);
		} catch (Exception e) {
			LOGGER.error("查询航空正单系统异常 Exception ",e);
			return super.returnError(e.getMessage());
		}
		//返回成功信息
		return super.returnSuccess();
	}
	/**
	 * sonar优化 311396 wwb 抽出判空
	 * @param awde
	 */
	private void makeRetrunList(List<AirWaybillDetailEntity> list,
			List<RepAirTransportDto> list1) {
		AirWaybillDetailEntity awde = null;
		if(!CollectionUtils.isEmpty(list1)){
			for(int i = 0;i<list1.size();i++){
				awde = new AirWaybillDetailEntity();
				awde.setWaybillNo(list1.get(i).getBillNoPackNo());
				awde.setPlanFlightNo(list1.get(i).getPrMatchflight());
				awde.setMakeWaybillWay(list1.get(i).getBillWay());
				awde.setArrvRegionName(list1.get(i).getDestinationStation());
				awde.setGrossWeight(list1.get(i).getGrossWeight());
				awde.setBillingWeight(list1.get(i).getCountWeight());
				awde.setVolume(list1.get(i).getVolume());
				awde.setGoodsQty(list1.get(i).getNumber());
				awde.setGoodsName(list1.get(i).getGoodsName());
				awde.setStockStatus(list1.get(i).getStockState());
				awde.setDepartTime(list1.get(i).getStartTime());
				awde.setUnitPrice(list1.get(i).getRate());
				awde.setReceiveOrgName(list1.get(i).getReceiveDept());
				awde.setLockRemark(list1.get(i).getTheTicketRemarks());
				awde.setTransportType(AirfreightConstants.PACKAGE_AIR);
				awde.setProductCode(list1.get(i).getProductCode());
				awde.setPickupType(list1.get(i).getDeliveryMode());
				awde.setReceiverContactPhone(list1.get(i).getReceiverContactPhone());
				awde.setReceiverName(list1.get(i).getReceiverName());
				awde.setReceiverAddress(list1.get(i).getReceiverAddress());
				awde.setDeliverFee(list1.get(i).getDeliverFee());
				awde.setArrivalFee(list1.get(i).getArrivalFee());
				awde.setCollectionFee(list1.get(i).getCollectionFee());
				//航空正单数据库表中,一些字段不允许为空,这些字段有可能取不到，用NULL代替
				if(StringUtils.isEmpty(awde.getPlanFlightNo())){
					awde.setPlanFlightNo(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getMakeWaybillWay())){
					awde.setMakeWaybillWay(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getArrvRegionName())){
					awde.setArrvRegionName(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getGoodsName())){
					awde.setGoodsName(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getPickupType())){
					awde.setPickupType(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getReceiverContactPhone())){
					awde.setReceiverContactPhone(AirfreightConstants.NULL);
				}
				if(StringUtils.isEmpty(awde.getReceiverName())){
					awde.setReceiverName(AirfreightConstants.NULL);
				}
				//排除已经合过票的
				List<AirWaybillDetailEntity> awse = new ArrayList<AirWaybillDetailEntity>();
				awse = pointsSingleJointTicketDao.queryWaybillDetailEntityList(awde.getWaybillNo());
				if(CollectionUtils.isEmpty(awse)){
					list.add(awde);
				}
			}
		}
	}
	
	/**
	 * 将运单明细转换为航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:35:10
	 */
	@JSON
	public String queryConvertWayBillDetail() {
		try {
			//获取dto
			AirWaybillDetailDto ticketDto = pointsSingleJointTicketVO.getTicketDto();
			List<AirWaybillDetailEntity> list = new ArrayList<AirWaybillDetailEntity>();
			String deptCode = FossUserContext.getCurrentDeptCode();
            OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
            /*****************首先按照20160418老方式查询*************************/
            list.addAll(pointsSingleJointTicketService.queryWaybillEntity(ticketDto));
			if(AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())){
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					//空运配载员所在外场
					String transferCode = null;
					
					//判断当前登录部门是否是空运总调
					if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
						transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());		
					}else{
						List<String> bizTypesList = new ArrayList<String>();
						//外场类型
						bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
						//空运总调类型
						bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.
								queryOrgAdministrativeInfoByCode(orgAdministrativeInfoEntity.getCode(), bizTypesList);
						if (orgAdministrativeInfoEntity1 == null){
//							throw new AirWayBillException("当前库存部门不存在！");
							LOGGER.error("当前库存部门不存在！" + orgAdministrativeInfoEntity.getCode());
							return returnError(new AirWayBillException("当前库存部门不存在！"));
						} else {
							transferCode = orgAdministrativeInfoEntity1.getCode();
						}
					}
					//请求参数设置
					AirTransportRequestEntity  airTransportRequest = new AirTransportRequestEntity();
					if(StringUtils.isEmpty(ticketDto.getMakeWaybillWay())){
						//查询全部
						airTransportRequest.setBillWay(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
					}else{
						airTransportRequest.setBillWay(ticketDto.getMakeWaybillWay());
					}
					airTransportRequest.setBillBeginTime(ticketDto.getBeginInTime());
					airTransportRequest.setBillEndTime(ticketDto.getEndInTime());
					airTransportRequest.setCurrentOrgCode(transferCode);
					airTransportRequest.setDestinationStation(ticketDto.getArrvRegionName());
					airTransportRequest.setNextOrgCode(ticketDto.getNextDestOrg());
					//库存中
					airTransportRequest.setStockState(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
					airTransportRequest.setStorageBeginInTime(ticketDto.getStorageBeginInTime());
					airTransportRequest.setStorageEndInTime(ticketDto.getStorageEndInTime());
					ResultBean<List<RepAirTransportDto>> resultBean = new ResultBean<List<RepAirTransportDto>>();
					//调用航空正单查询接口
					resultBean = fossToWkService.queryTicketInfo(airTransportRequest);
					//航空正单查询接口返回参数设置
					List<RepAirTransportDto> list1=resultBean.getData();
					makeRetrunList(list, list1);
                }
            }else{
				//查询航空正单明细list
//            	list = pointsSingleJointTicketService.queryWaybillEntity(ticketDto);
            }//设置航空正单明细list
			pointsSingleJointTicketVO.setBillDetailList(list);
			//catch异常
		} catch (AirWayBillException e) {
			//返回错误信息
			LOGGER.error("查询航空正单系统异常 AirWayBillException ",e);
			return super.returnError(e);
		} catch (Exception e) {
			LOGGER.error("查询航空正单系统异常 Exception ",e);
			return super.returnError(e.getMessage());
		} 
		//返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 根据运单号查询流水号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-24 下午3:30:39
	 */
	@JSON
	public String queryWaybillNo() {
		try {
			//获取dto
			//调用库存查询
			//返回流水查询结果
			List<AirSerialNoDetail> list = pointsSingleJointTicketService.queryStockAirSerialNoDetail(pointsSingleJointTicketVO.getTicketDto());
			//设置流水结果
			pointsSingleJointTicketVO.setAirSerialNoDetailList(list);
			//catch 异常
		} catch (AirWayBillException e) {
			//返回错误信息
			return super.returnError(e);
		}
		//返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 查询待修改的正单流水明细
	 * @author 099197-boss-zhoudejun
	 * @date 2012-12-6 上午9:32:34
	 */
	@JSON
	public String queryWithModifySerailNo() {
		try {
			//获取运单号
//			String waybillNo = pointsSingleJointTicketVO.getTicketDto().getWaybillNo();
			//获取运单明细id
//			String airWaybillDetailId = pointsSingleJointTicketVO.getTicketDto().getAirWaybillDetailId();
			//根据运单号、运单明细id
			//查询需要修改的流水明细
			AirWaybillDetailDto //airWaybillDetailDto = pointsSingleJointTicketService.queryWithModifySerailNo(waybillNo,airWaybillDetailId);
			airWaybillDetailDto = pointsSingleJointTicketService.queryWithModifySerailNo(pointsSingleJointTicketVO.getTicketDto());
			//获取需要新增的流水明细list
			airWaybillDetailDto.getAddAirSerialNoDetailList();
			//获取需要删除的流水明细list
			airWaybillDetailDto.getDelAirSerialNoDetailList();
			//获取需要显示的流水明细list
			airWaybillDetailDto.getAirSerialNoViewDetailList();
			//设置结果
			pointsSingleJointTicketVO.setTicketDto(airWaybillDetailDto);
			//catch 异常
		} catch (AirWayBillException e) {
			//返回错误信息
			return super.returnError(e);
		}
		//返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 新增航空正单、明细、流水
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-25 下午4:41:13
	 */
	@JSON
	public String addAirwayBill() {
		try {
			AirWaybillDetailDto dto = pointsSingleJointTicketService
					.addAirWaybillEntity(
							pointsSingleJointTicketVO.getBillEntity(),
							pointsSingleJointTicketVO.getBillDetailList(),
							pointsSingleJointTicketVO.getDelParameterMap(),
							pointsSingleJointTicketVO.getParameter());
			pointsSingleJointTicketVO.setTicketDto(dto);
		} catch (PointsSingleJointTicketException e) {
			return super.returnError(e);
		}
		return super.returnSuccess();
	}

	/**
	 * 查询航空公司二字码 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 下午4:32:09
	 */
	@JSON
	public String queryAirLineTwoletterList() {
		try {
			// 查询航空公司二字码
			List<AirlinesEntity> queryAllAirlines = pointsSingleJointTicketService.queryAllAirlines();
			// 设置航空公司二字码
			pointsSingleJointTicketVO.setQueryAllAirlines(queryAllAirlines);
			//catch异常
		} catch (PointsSingleJointTicketException e) {
			//返回错误信息
			return super.returnError(e);
		}
		//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 修改航空正单 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-27 下午8:56:06
	 */
	@JSON
	public String modifyAirWaybill() {
		try {
			//获取需要航空正单主信息
			//获取航空正单明细信息
			//获取不需要保存的流水号
			//获取需要删除的流水号
			pointsSingleJointTicketService.modifyAirWaybill(pointsSingleJointTicketVO.getBillEntity(),pointsSingleJointTicketVO.getBillDetailList(),pointsSingleJointTicketVO.getAddParameterMap(),pointsSingleJointTicketVO.getDelParameterMap());
			//catch 异常
		} catch (PointsSingleJointTicketException e) {
			//返回错误信息
			return super.returnError(e);
		}
		//返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 获取基础资料
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-3 上午10:02:47
	 */
	@JSON
	public String queryBaseData(){
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		//获取员工信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取配置信息    2013-05-23 费用说明修改为根据基础费率表配置的燃油附加费 实时拼接出，例如：燃油附加费：0.2/Kg
	    //List<ConfigurationParamsEntity> configurationParamsList =  pointsSingleJointTicketService.queryConfigurationParamsExactByEntity(Integer.MIN_VALUE,Integer.MAX_VALUE);
		AirportEntity airport = new AirportEntity();
		airport.setCityCode(orgAdministrativeInfoEntity.getCityCode());
		List<AirportEntity> airportList = airportService.queryAirportListBySelective(airport);
		pointsSingleJointTicketVO.setDeptRegionAirportList(airportList);
		//获取费用说明
		//当前操作人所在部门所在城市
		String cityName = orgAdministrativeInfoEntity.getCityName();
		//获取当前登录人姓名
		String createUserName = currentInfo.getEmpName();
		//获取当前操作人所在部门
		String createOrgName = orgAdministrativeInfoEntity.getName();
		//获取合票号
		String jointTicketNo = pointsSingleJointTicketService.getGenerationJointTicketNo();
		// 设置合票号
		pointsSingleJointTicketVO.getTicketDto().setJointTicketNo(jointTicketNo);
		//设置始发站城市
		pointsSingleJointTicketVO.getTicketDto().setCityName(cityName);
		//设置当前操作人部门
		pointsSingleJointTicketVO.getTicketDto().setOrgCode(orgAdministrativeInfoEntity.getCode());
		//设置当前操作人部门电话zwd
		pointsSingleJointTicketVO.getTicketDto().setShipperContactPhone(orgAdministrativeInfoEntity.getDepTelephone());
		pointsSingleJointTicketVO.getTicketDto().setOrgName(createOrgName);
		//设置当前操作人
		pointsSingleJointTicketVO.getTicketDto().setCreateUserName(createUserName);
		//设置费用说明
		//pointsSingleJointTicketVO.setConfigurationParamsList(configurationParamsList);
		//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 获取基础费率
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-4 下午4:00:05
	 */
	@JSON
	public String queryRate(){
		//获取航空公司二字码
		String airLineTwoletter = pointsSingleJointTicketVO.getAirLineTwoletter();
		//获取机场三字码
		String airportCode = pointsSingleJointTicketVO.getAirportCode();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		String currentDeptCode = orgAdministrativeInfoEntity.getCode();
		//给基础费率对象赋值为空
		AirlinesValueAddEntity airlinesValueAddEntity = null;
	    try {
	    	//根据航空公司二字码
	    	//航空公司三字码
	    	//操作部门
	    	//当前操作时间
	    	//调用基础费率服务
	    	//返回查询结果
		  airlinesValueAddEntity = pointsSingleJointTicketService.queryRate(airLineTwoletter,currentDeptCode,airportCode,new Date());
		  //根据航空公司
		  //操作部门
		  //航空公司
		  //当前日期
		  //获取运价信息
		  FlightPricePlanEntity flightPricePlanEntity = flightPricePlanService4Dubbo.findFlightPricePlanByCondition(airLineTwoletter,currentDeptCode,FossConstants.ACTIVE,new Date());
			//设置运价号
		  airlinesValueAddEntity.setPriceNo(flightPricePlanEntity.getPriceNo());
		  //catch异常
		} catch (BusinessException e) {
			//返回错误信息
			return returnError(e);
		}
	    //设置费率对象
		pointsSingleJointTicketVO.setAirlinesValueAddEntity(airlinesValueAddEntity);
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 获取最低运价
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-4 下午5:37:16
	 */
	@JSON
	public String queryFlightMinPriceRate (){
		try {
			//航班号
			String flightNo =  pointsSingleJointTicketVO.getFlightNo();
			//航空公司二字码.
			String airLineTwoletter = pointsSingleJointTicketVO.getAirLineTwoletter();
			//航班信息
			FlightEntity flightEntity = pointsSingleJointTicketVO.getFlightEntity();
			//计费重量-传值
			BigDecimal weight = pointsSingleJointTicketVO.getWeight();
			//如果计费重量不为0
			if(weight!=null){
				//货物类别：通用
				String goodsType = GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001;
		  		//根据当前部门取空运总调
				String deptCode = FossUserContext.getCurrentDeptCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
				
				//获取航空公司运价方案明细
				//参数信息：航班号,航班公司编码, 配载部门编码,到达站编码,货物类型,计费重量,当前日期,获取最低运费
				FlightPricePlanDetailEntity entity =  pointsSingleJointTicketService
						.queryFlightMinPriceRate(flightNo,airLineTwoletter, orgAdministrativeInfoEntity.getCode(), 
												 flightEntity.getTargetCode(), goodsType, weight, new Date());
				//运价方案明细不为空
				if(entity!=null){
					//航空运费=计费重量*运价
//					double filghtFee = (weight.doubleValue()*entity.getCalculateFee().doubleValue())/100;
					//运价
					pointsSingleJointTicketVO.setPriceRate(BigDecimal.valueOf(entity.getCalculateFee().doubleValue()/ConstantsNumberSonar.SONAR_NUMBER_100));
					//最低运费
					pointsSingleJointTicketVO.setMinPriceRate(BigDecimal.valueOf(entity.getMinFee()/ConstantsNumberSonar.SONAR_NUMBER_100));
					//如果航空运费<最低运费 ,设置为以最低运费为准
//					if(filghtFee < entity.getMinFee()/100){
//						//设置基础运价
//						pointsSingleJointTicketVO.setPriceRate(BigDecimal.valueOf(entity.getMinFee()/100));
//					}else{
//						//设置运价
//						pointsSingleJointTicketVO.setPriceRate(BigDecimal.valueOf(entity.getCalculateFee().doubleValue()/100));
//					}
				}else{
					//取不到值时，实际运价为0
					pointsSingleJointTicketVO.setPriceRate(BigDecimal.valueOf(0));
					pointsSingleJointTicketVO.setMinPriceRate(BigDecimal.valueOf(0));
				}
			}else{
				//不做处理
			}
			//catch异常
		} catch (BusinessException e) {
			//返回错误信息
			return returnError(e.getMessage());
		}
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 制作唐翼制单
	 * @param airWaybillnNO
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:05:19
	 */
	public String saveTangYiAwb(){
		try {
			//获取正单号
			String airWaybillNo = pointsSingleJointTicketVO.getAirWaybillNo();
			//获取目的站code
			String deptRegionCode = pointsSingleJointTicketVO.getDeptRegionCode();
			//获取是否站code
			String arrvRegionCode = pointsSingleJointTicketVO.getArrvRegionCode();
			//根据始发站code、目的站code、正单号制作唐翼制单
			//调用唐翼制单服务
			pointsSingleJointTicketService.saveTangYiAwb(deptRegionCode,arrvRegionCode,airWaybillNo);
			//catch 异常
		} catch (AirWayBillException e) {
			//返回错误信息
			return returnError(e.getErrorCode());
		}
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 根据正单号查询待打印的航空正单
	 * @param airWaybillNo 正单号
	 * @return 航空正单实体 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 下午5:25:50
	 */
	@JSON
	public String queryWidthPrintAirWaybill(){
		//获取正单号
		String airWaybillNo = pointsSingleJointTicketVO.getAirWaybillNo();
		//调用打印服务
		//获取需要打印的正单
		AirWaybillEntity billEntity = pointsSingleJointTicketService.queryWidthPrintAirWaybill(airWaybillNo);
		if(billEntity.getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE)){
			billEntity.setItemCode("");
		}
		//332209 - BUG
		if (StringUtils.isEmpty(billEntity.getPackageStruction())) {
			//需求要求包装说明一直为"";
			billEntity.setPackageStruction("");
		}

		//设置正单信息
		pointsSingleJointTicketVO.setBillEntity(billEntity);
		//返回成功信息
		return returnSuccess();
	}

	/**
	 * 查询航班信息 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-4 下午1:48:13
	 */
	@JSON
	public String queryFlightNoList() {
		//调用航空信息服务
		//查询航班信息
		//返回查询结果
		List<FlightEntity> flightEntity = flightService.queryFlightListBySelective(new FlightEntity());
		//设置查询结果
		pointsSingleJointTicketVO.setFlightList(flightEntity);
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 根据配置部门code、航空公司code查询代理人code
	 * @return 代理人编码 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 上午10:41:22
	 */
	@JSON
	public String queryAirlinesAgentCode(){
		//初始化对象
		AirlinesAgentEntity airlinesAgentEntity = new AirlinesAgentEntity();
		//初始化对象
		AirlinesAgentEntity airlinesAgent = new AirlinesAgentEntity();
		//获取航班公司三字码
		String airLineTwoletterCode = pointsSingleJointTicketVO.getAirLineTwoletter();
		//获取配载类型
		String airAssembleType=pointsSingleJointTicketVO.getAirAssembleType();
		
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		//设置当前操作部门编码
		airlinesAgentEntity.setAssemblyDeptId(orgAdministrativeInfoEntity.getCode());
		//设置航空公司三字码
		airlinesAgentEntity.setAirlinesCode(airLineTwoletterCode);
		/**
		 * BUG-48600 
		 * 深航填开代理编码因外发代理人编码变动而变动
		 * @desc 设置当前航班为有效值
		 * @author wqh
		 * @date   2013-08-02
		 * 
		 * */
		airlinesAgentEntity.setActive("Y");
		//设置代理为内部代理
		String isOutAgent="N";
		//ISSUE-3789 航空公司代理人基础数据调整--录入航空正单明细相应调整
		if(StringUtils.isEmpty(airAssembleType)){
//			throw new TfrBusinessException("没有选择配载类型");
			LOGGER.error("没有选择配载类型");
			return returnError(new TfrBusinessException("没有选择配载类型"));
		}
		if(airAssembleType.equals("DDWFD")||airAssembleType.equals("HDPWF")){
			isOutAgent="Y";
		}
		airlinesAgentEntity.setIsOutAgent(isOutAgent);
		//获取航空公司代理人信息
		airlinesAgent = airlinesAgentService.queryAirlinesAgentBySelective(airlinesAgentEntity);
		//信息航空公司代理人信息
		pointsSingleJointTicketVO.setAirlinesAgent(airlinesAgent);
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 根据始发站编码查询所有航空公司三字码
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-17 上午10:57:57
	 */
	@JSON
	public String byDeptRegionCodeQueryAirportCode(){
		//初始化航空公司三字码对象
		AirportEntity airportEntity = new AirportEntity();
		//获取始发站code
		String deptRegionCode =  pointsSingleJointTicketVO.getDeptRegionCode();
		//设置始发站code
		airportEntity.setCityCode(deptRegionCode);
		//调用航过公司三字码服务
		//返回航空公司三字码list
		List<AirportEntity> deptRegionAirportList = airportService.queryAirportListBySelective(airportEntity);
		//设置航空公司三字码list
		pointsSingleJointTicketVO.setDeptRegionAirportList(deptRegionAirportList);
		//返回成功信息
		return returnSuccess();
	}
	
	/**
	 * 根据目的站编码查询所有航空公司三字码
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-17 上午10:57:57
	 */
	@JSON
	public String byArrvRegionCodeQueryAirportCode(){
		//初始化目的站三字码对象
		AirportEntity airportEntity = new AirportEntity();
		//获取目的站code
		String arrvRegionCode =  pointsSingleJointTicketVO.getArrvRegionCode();
		//设置目的站code
		airportEntity.setCityCode(arrvRegionCode);
		//调用目的站服务
		//查询目的站三字码list
		List<AirportEntity> arrvRegionAirportList = airportService.queryAirportListBySelective(airportEntity);
		//设置目的站站三字码list
		pointsSingleJointTicketVO.setArrvRegionAirportList(arrvRegionAirportList);
		//返回成功信息
		return returnSuccess();
	}
	
	/*
	 * 根据运单号查询运单详细信息
	 * @ waybillNo 运单号
	 * @ author wqh
	 * @ date    2013-09-11
	 * */
	@JSON
	public String queryWaybillDetailByWaybillNo(){
		String waybillNo = pointsSingleJointTicketVO.getWaybillDetailDto().getWaybillNo();
		WaybillDetailDto waybillDetailDto=pointsSingleJointTicketService.queryWaybillDetailByWaybillNo(waybillNo);
		pointsSingleJointTicketVO.setWaybillDetailDto(waybillDetailDto);
			
		return returnSuccess();
	}
	
	/*
	 * 添加空运锁票信息
	 * @ author 105795-foss-wqh
	 * @ date    2014-03-19
	 * */
	@JSON
	public String addAirLockWaybillDetail(){
		//从前台获取运单号
		String waybillNo=pointsSingleJointTicketVO.getAirLockWaybillDetailEntity().getWaybillNo();
		//从前台获取锁票备注
		String lockRemark=pointsSingleJointTicketVO.getAirLockWaybillDetailEntity().getLockRemark();
		
		if(StringUtil.isBlank(waybillNo)||StringUtil.isBlank(lockRemark)){
//			throw new TfrBusinessException("运单号为空或者没有锁票备注");
			return returnError(new TfrBusinessException("运单号为空或者没有锁票备注"));
		}
		AirLockWaybillDetailEntity airLockWaybillDetailEntity=new AirLockWaybillDetailEntity();
		airLockWaybillDetailEntity.setWaybillNo(waybillNo);
		airLockWaybillDetailEntity.setLockRemark(lockRemark);
		try {
			airLockWaybillService.addAirLockWaybillDetail(airLockWaybillDetailEntity);
			
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		return this.returnSuccess();
	}
	/*
	 * 空运解锁
	 * @ author 105795-foss-wqh
	 * @ date    2014-03-19
	 * */
	@JSON
	public String unlockAirWaybill(){
		//从前台获取运单号
		String waybillNo=pointsSingleJointTicketVO.getAirLockWaybillDetailEntity().getWaybillNo();
		if(StringUtil.isEmpty(waybillNo)){
			return this.returnError("请选择要解锁的运单");

		}
		AirLockWaybillDetailEntity airLockWaybillDetailEntity=new AirLockWaybillDetailEntity();
		airLockWaybillDetailEntity.setWaybillNo(waybillNo);
		try {
			airLockWaybillService.unlockAirWaybill(airLockWaybillDetailEntity);
			
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		return this.returnSuccess();
	}
	/**
	 * 获取 航空正单vo.
	 * @return the 航空正单vo
	 */
	public AirWaybillDetailVO getPointsSingleJointTicketVO() {
		return pointsSingleJointTicketVO;
	}
	
	/**
	 * 设置 航空正单vo.
	 * @param pointsSingleJointTicketVO the new 航空正单vo
	 */
	public void setPointsSingleJointTicketVO(
			AirWaybillDetailVO pointsSingleJointTicketVO) {
		this.pointsSingleJointTicketVO = pointsSingleJointTicketVO;
	}
	
	/**
	 * 设置 注入航空正单service.
	 * @param pointsSingleJointTicketService the new 注入航空正单service
	 */
	public void setPointsSingleJointTicketService(
			IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}

	/**
	 * 设置 注入航班信息service.
	 *
	 * @param flightService the new 注入航班信息service
	 */
	public void setFlightService(IFlightService flightService) {
		this.flightService = flightService;
	}

	/**
	 * 设置 航空司代理人service.
	 *
	 * @param airlinesAgentService the new 航空司代理人service
	 */
	public void setAirlinesAgentService(IAirlinesAgentService airlinesAgentService) {
		this.airlinesAgentService = airlinesAgentService;
	}

	/**
	 * 设置 机场三字码接口.
	 *
	 * @param airportService the new 机场三字码接口
	 */
	public void setAirportService(IAirportService airportService) {
		this.airportService = airportService;
	}

	/*public void setFlightPricePlanService(
			IFlightPricePlanService flightPricePlanService) {
		this.flightPricePlanService = flightPricePlanService;
	}*/

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	public void setAirLockWaybillService(
			IAirLockWaybillService airLockWaybillService) {
		this.airLockWaybillService = airLockWaybillService;
	}
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	/**
	 * 设置 查询航空正单dao.
	 * @param pointsSingleJointTicketDao the new 查询航空正单dao
	 */
	public void setPointsSingleJointTicketDao(
			IAirWaybillDetailDao pointsSingleJointTicketDao) {
		this.pointsSingleJointTicketDao = pointsSingleJointTicketDao;
	}
	

	
	
}