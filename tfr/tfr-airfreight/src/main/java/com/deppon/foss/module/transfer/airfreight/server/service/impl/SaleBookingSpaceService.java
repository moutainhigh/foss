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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/SaleBookingSpaceService.java
 *  
 *  FILE NAME          :SaleBookingSpaceService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
/**
 * 营业部订舱service
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:29:53
 */
public class SaleBookingSpaceService implements ISaleBookingSpaceService {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(SaleBookingSpaceService.class);
	
	/**
	 * setter注入saleBookingSpaceDao
	 */
	private ISaleBookingSpaceDao saleBookingSpaceDao;
	/**
	 * setter注入tfrCommonService
	 */
	private ITfrCommonService tfrCommonService;
	/**
	 * setter注入msgService
	 */
	private IMessageService messageService;
	
	/**线路信息*/
	private ILineService lineService;
	
	/**组织信息 Service*/
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	

	/**
	 * 获取上级空运总调
	 */
	private IAirDispatchUtilService airDispatchUtilService;
	
	
	/**
	 * 设置 获取上级空运总调.
	 *
	 * @param airDispatchUtilService the new 获取上级空运总调
	 */
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	

	/**
	 * 设置 组织信息 Service.
	 *
	 * @param orgAdministrativeInfoService the new 组织信息 Service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 线路信息.
	 *
	 * @param lineService the new 线路信息
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/**
	 * 设置 setter注入saleBookingSpaceDao.
	 *
	 * @param saleBookingSpaceDao the new setter注入saleBookingSpaceDao
	 */
	public void setSaleBookingSpaceDao(ISaleBookingSpaceDao saleBookingSpaceDao) {
		this.saleBookingSpaceDao = saleBookingSpaceDao;
	}

	/**
	 * 设置 setter注入tfrCommonService.
	 *
	 * @param tfrCommonService the new setter注入tfrCommonService
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 设置 setter注入msgService.
	 *
	 * @param messageService the new setter注入msgService
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 查询营业部订舱信息, 参数1：entity 封装的查询对象
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 上午9:33:42
	 */
	@Override
	public List<SaleBookingSpaceDto> queryBookingSpace(
			SaleBookingSpaceDto dto, int limit,int start) {
		//查询营业部订舱信息, 参数1：entity 封装的查询对象
		return saleBookingSpaceDao.queryBookingSpace(dto, limit, start);
	}

	/**
	 * 新增订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 下午4:41:16
	 */
	@Override
	public void addBookingSpace(SaleBookingSpaceDto dto) {
		dto.setId(UUIDUtils.getUUID());
		//当前登录用户实体
		UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		if (emp != null) {
			// 订舱人
			dto.setCreateUserName(emp.getEmpName());
		}else{
			//空
		}
		//申请部门名称
		dto.setApplyOrgName(getOrgAdministrative().getName()); 
		//申请部门编码
		dto.setApplyOrgCode(getOrgAdministrative().getCode());  
		//订舱日期
		dto.setCreateTime(new Date());
		//未受理状态
		dto.setAcceptState(AirfreightConstants.UN_ACCEPT);
		//生成订舱编号
		dto.setBookingNo(tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC));
		//调用dao保存
		saleBookingSpaceDao.addBookingSpace(dto);
		//新增待办事项
		addBacklogSchedule(dto);
	}
	
	/**
	 * 调用综合接口IMsgService 生成待办事项
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-13 下午2:46:08
	 */
	public void addBacklogSchedule(SaleBookingSpaceDto dto){
		//待办消息实体
		InstationJobMsgEntity instationJobMsgEntity = new InstationJobMsgEntity();   
		//发送方编码
		instationJobMsgEntity.setSenderCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());   
		//发送方姓名
		instationJobMsgEntity.setSenderName(FossUserContext.getCurrentUser().getEmployee().getEmpName()); 
		//发送方部门编码
		instationJobMsgEntity.setSenderOrgCode(getOrgAdministrative().getCode());   
		//发送方部门名称
		instationJobMsgEntity.setSenderOrgName(getOrgAdministrative().getName());   
		//接收方类型
		instationJobMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		//消息类型
		instationJobMsgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);  
		//发送时间
		instationJobMsgEntity.setPostDate(new Date());  
		//站内消息状态
		instationJobMsgEntity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);  
		//接收方部门
		instationJobMsgEntity.setReceiveOrgCode(dto.getAcceptOrgCode());   
		//ID
		instationJobMsgEntity.setId(UUIDUtils.getUUID());
		String flightType = DictUtil.rendererSubmitToDisplay(dto.getFlightType(), "AIR_FLIGHT_TYPE");
		String createTime = DateUtils.convert(dto.getCreateTime());
		String message = getOrgAdministrative().getName()+"于"+createTime+"请求预订"+flightType+
				"去往"+dto.getArrvRegionName()+",订舱编号:"+dto.getBookingNo()+"、货物重量:"+dto.getWeight()+"（公斤）、货物体积:"+dto.getVolume()+"（方）,请尽快处理订舱信息!";
		instationJobMsgEntity.setContext(message);
		try{
			//发送待办
			messageService.createBatchInstationMsg(instationJobMsgEntity);
		}catch(BusinessException e){
			//打印异常日志
			LOGGER.error(e.getMessage());
			throw new TfrBusinessException(e.getErrorCode(),"");
		}
	}
	
	/**
	 * 受理营业部订舱
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-13 下午12:58:26
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService#acceptBookingSpace(boolean, java.lang.String, java.util.List)
	 */
	@Override
	public void acceptBookingSpace(boolean acceptType, String acceptNotes,
			List<String> acceptIds) {
		SaleBookingSpaceDto dto = new SaleBookingSpaceDto();
		//当前登录用户实体
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		if (emp != null) {
			//受理人
			dto.setAcceptUserName(emp.getEmpName());
		}
		//设置受理时间
		dto.setAcceptTime(new Date());
		//设置受理列表id
		dto.setAcceptIds(acceptIds);
		if(acceptType){
			//同意受理
			dto.setAcceptState(AirfreightConstants.AGREE_ACCEPT);
		}else{
			//拒绝受理
			dto.setAcceptState(AirfreightConstants.REFUSE_ACCEPT);
		}
		//设备受理备注
		dto.setAcceptNotes(acceptNotes);
		saleBookingSpaceDao.acceptBookingSpace(dto);
	}
	
	/**
	 * 根据空运总调调出始发站
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-24 下午8:45:25
	 */
	@Override
	public SaleBookingSpaceDto queryDeptRegion(String acceptOrgCode){
		SaleBookingSpaceDto dto = new SaleBookingSpaceDto();
		//根据空运总调查询始发站
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
		//如果受理部门不为空
		if(StringUtils.isNotBlank(acceptOrgCode)){
			
			//根据受理部门查询组织信息
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(acceptOrgCode);
		}
		//出发部门code
		String deptRegionCode = "";
		//出发部门名称
		String deptRegionName = "";
		//如果城市名称和城市code都不为空
		if(StringUtils.isNotBlank(orgAdministrativeInfoEntity.getCityName()) && StringUtils.isNotBlank(orgAdministrativeInfoEntity.getCityCode())){
			//设置出发部门code
			deptRegionCode = orgAdministrativeInfoEntity.getCityCode();
			//设置出发部门名称
			deptRegionName = orgAdministrativeInfoEntity.getCityName();
		}
		//设置始发站code
		dto.setDeptRegionCode(deptRegionCode);
		//设置始发站名称
		dto.setDeptRegionName(deptRegionName);
		return dto;
	}
	
	/**
	 * 初始化界面参数 订舱编号/空运总调/始发站
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-9 下午8:43:12
	 */
	@Override
	public SaleBookingSpaceDto initBookingSpace() {
		SaleBookingSpaceDto dto = new SaleBookingSpaceDto();
		//显示订舱编号
		String bookingNo = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		//设置订舱编号
		dto.setBookingNo(bookingNo);
		//获取空运总调
		String acceptOrgCode = "";
		String acceptOrgName = "";
		if(getOrgAdministrative().checkDoAirDispatch()){
			//受理部门code
			acceptOrgCode = getOrgAdministrative().getCode();
			//受理部门名称
			acceptOrgName = getOrgAdministrative().getName();
		}else{
			LineEntity lineEntity = new LineEntity();
			//始发到达线路-空运
			lineEntity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN);
			//组织code
			lineEntity.setOrginalOrganizationCode(getOrgAdministrative().getCode());
			//始发线路
			lineEntity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			//根据查询条件查询线路
			List<LineEntity> lineEntities = lineService.queryLineListByCondition(lineEntity);
			if(lineEntities.size() > 0 ){
				/**
				 * BUG-51110 
				 * WEB-营业部订舱界面默认受理部门不是营业部对应的空运总调，而是对应的外场
				 * author: wqh
				 * date: 2013-08-15
				 * */
				for(int i=0;i<lineEntities.size();i++){
					LineEntity lineEntiry=lineEntities.get(i);
					if("Y".equals(lineEntiry.getIsDefault()) &&
							"TRANS_AIRCRAFT".equals(lineEntiry.getTransType())){
						//设置受理部门code
						acceptOrgCode = lineEntiry.getDestinationOrganizationCode();
						//设置受理部门名称
						acceptOrgName = lineEntiry.getDestinationOrganizationName();
						break;
					}
					
				}
				
//				//设置受理部门code
//				acceptOrgCode = lineEntities.get(0).getDestinationOrganizationCode();
//				//设置受理部门名称
//				acceptOrgName = lineEntities.get(0).getDestinationOrganizationName();
			}
		}
		//设置受理部门
		dto.setAcceptOrgCode(acceptOrgCode);
		dto.setAcceptOrgName(acceptOrgName);
		
		//根据空运总调查询始发站
		SaleBookingSpaceDto bookingSpaceDto = queryDeptRegion(acceptOrgCode);
		//设置始发站
		dto.setDeptRegionCode(bookingSpaceDto.getDeptRegionCode());
		dto.setDeptRegionName(bookingSpaceDto.getDeptRegionName());
		return dto;
	}
	
	/**
	 * 通过id查询营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午10:34:24
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService#queryBookingSpaceById()
	 */
	@Override
	public SaleBookingSpaceDto queryBookingSpaceById(String id) {
		//通过id查询营业部订舱信息
		return saleBookingSpaceDao.queryBookingSpaceById(id);
	}
	
	/**
	 * 更新营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午11:29:11
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService#updateBookingSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto)
	 */
	@Override
	public void updateBookingSpace(SaleBookingSpaceDto dto) {
		//更新营业部订舱信息
		saleBookingSpaceDao.updateBookingSpace(dto);
	}
	
	/**
	 * 获取总调未受理订舱总条数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 下午5:28:35
	 */
	@Override
	public Long queryNoAcceptCount(SaleBookingSpaceDto dto) {
		//查询未受理状态
		dto.setAcceptState(AirfreightConstants.UN_ACCEPT);
		return saleBookingSpaceDao.queryNoAcceptCount(dto);
	}
	/**
	 * 获取订舱列表总记录数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-13 下午5:23:18
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService#queryCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto)
	 */
	@Override
	public Long queryCount(SaleBookingSpaceDto dto) {
		// 获取订舱列表总记录数
		return saleBookingSpaceDao.queryCount(dto);
	}
	
	/**
	 * 根据运单号查询需要订舱的信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-21 下午4:35:38
	 */
	@Override
	public SaleBookingSpaceDto queryWaybillInfo(String waybillNo){
		//如果运单号为空则抛出异常
		if(StringUtils.isBlank(waybillNo)){
			throw new TfrBusinessException("请输入运单号！","");
		}
		//根据运单号查询您
		List<SaleBookingSpaceDto> bookingSpaceDtos = saleBookingSpaceDao.queryWaybillInfo(waybillNo);
		//如果没有记录，则抛出异常
		if(bookingSpaceDtos.size() < 1){
			throw new TfrBusinessException("根据运单号未找到匹配的记录！");
		}
		//return dto
		return bookingSpaceDtos.get(0);
	}
	
	/**
	 * 获取上级空运总调组织
	 */
	@Override
	public OrgAdministrativeInfoEntity getOrgAdministrative(){
		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=airDispatchUtilService.queryAirDispatchDept(deptCode);
		return orgAdministrativeInfoEntity;
	}
}