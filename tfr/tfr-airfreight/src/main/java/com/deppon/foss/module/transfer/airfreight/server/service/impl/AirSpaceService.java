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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirSpaceService.java
 *  
 *  FILE NAME          :AirSpaceService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirSpaceException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
/**

 * 航空舱位服务
 * @author 038300-foss-pengzhen
 * @date 2012-11-8 下午3:11:24
 */
public class AirSpaceService implements IAirSpaceService {
	
	/**
	 * 航空舱位信息管理DAO
	 */
	private IAirSpaceDao airSpaceDao;
	
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
	 * set注入
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午6:06:39
	 */
	public void setAirSpaceDao(IAirSpaceDao airSpaceDao) {
		this.airSpaceDao = airSpaceDao;
	}
	
	/**
	 * 查询航空舱位信息列表Service,参数说明：dto对象，分页大小，分页开始
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-16 下午2:06:30
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#queryAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto, int, int)
	 */
	@Override
	public List<AirSpaceDto> queryAirSpace(AirSpaceDto dto, int limit, int start) {
		//设置目的站code
		dto.setArrvRegionCode(dto.getArrvRegionCode());
		//设置统计已受理状态
		dto.setAcceptStatus(AirfreightConstants.AGREE_ACCEPT);
		return airSpaceDao.queryAirSpace(dto, limit, start);
	}
	
	/**
	 * 新增舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-30 下午8:51:32
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#addAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@Transactional
	@Override
	public void addAirSpace(AirSpaceDto dto) {
		//如果不存在同一日期到同一目的地的舱位信息
		if(!isExistSpace(dto)){
			//录入时间
			dto.setCreateTime(new Date()); 
			//修改时间
			dto.setUpdateTime(new Date()); 
			//目的站编号
			dto.setArrvRegionCode(dto.getArrvRegionCode());  
			//目的站名称
			dto.setArrvRegionName(dto.getArrvRegionName());  
			//主键ID
			dto.setId(UUIDUtils.getUUID());
			//新增舱位
			airSpaceDao.addAirSpace(dto);
			//新增舱位明细
			addAirSpaceDetail(dto);
		}else{
			//抛出异常
			throw new AirSpaceException(AirSpaceException.AIRFREIGHT_EXCEPTION_EXISTAIRSPACE,"");
		}
	}
	
	/**
	 * 新增舱位明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-25 下午2:58:15
	 */
	public void addAirSpaceDetail(AirSpaceDto dto){
		//航空舱位明细列表
		List<AirSpaceDetailEntity> detailList = new ArrayList<AirSpaceDetailEntity>();
		//用于后台验证计数,必须有一个舱位不为空或0
		int count = 0;
		//如果即日达舱位不为空或0，则新增
		//如果早班舱位不为空或0，则新增
		if(dto.getMorningShift() != null && dto.getMorningShift().intValue() != 0){
			AirSpaceDetailEntity detailEntity = new AirSpaceDetailEntity();
			// 航班类型-早班 -》即日达
			detailEntity.setFlightType(AirfreightConstants.MORNING_FLIGHT);
			//总舱位
			detailEntity.setSpaceTotal(dto.getMorningShift());
			//添加到明细
			detailList.add(detailEntity);
			//自加
			count ++;
		}
		//如果中班舱位不为空则新增
		if(dto.getMiddleShift() != null && dto.getMiddleShift().intValue() != 0){
			AirSpaceDetailEntity detailEntity = new AirSpaceDetailEntity();
			// 航班类型-中班 -》次日达
			detailEntity.setFlightType(AirfreightConstants.MIDDLE_FLIGHT);
			//总舱位
			detailEntity.setSpaceTotal(dto.getMiddleShift());
			//添加到明细
			detailList.add(detailEntity);
			//自加
			count ++;
		}
		/*//如果晚班舱位不为空则新增
		if(dto.getNightShift() != null && dto.getNightShift().intValue() != 0){
			AirSpaceDetailEntity detailEntity = new AirSpaceDetailEntity();
			// 航班类型-晚班 -》次日达
			detailEntity.setFlightType(AirfreightConstants.NIGHT_FLIGHT);
			//总舱位
			detailEntity.setSpaceTotal(dto.getNightShift());
			//添加到明细
			detailList.add(detailEntity);
			//自加
			count ++;
		}*/
		//如果中转舱位不为空则新增
		if(dto.getTransitShift() != null && dto.getTransitShift().intValue() != 0){
			AirSpaceDetailEntity detailEntity = new AirSpaceDetailEntity();  
			//航班类型-中转 -》航空普运
			detailEntity.setFlightType(AirfreightConstants.TRANSFER_FLIGHT); 
			//总舱位
			detailEntity.setSpaceTotal(dto.getTransitShift());
			//添加到明细
			detailList.add(detailEntity);
			//自加
			count ++;
		}
		if(count != 0){
			for(int i=0; i<detailList.size(); i++){
				//设置ID
				detailList.get(i).setId(UUIDUtils.getUUID());
				//设置舱位ID
				detailList.get(i).setAirspaceId(dto.getId());
				//录入航空舱位明细
				airSpaceDao.addAirSpaceDetail(detailList.get(i));
			}
		}else{
			//抛出异常
			throw new AirSpaceException(AirSpaceException.AIRFREIGHT_EXCEPTION_NEEDINPUTONESPACE,"");
		}
	}
	
	/**
	 * 判断是否存在同一日期
	 * 到同一目的地的舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午8:12:28
	 */
	public boolean isExistSpace(AirSpaceDto dto){
		//判断是否存在同一日期
		//到同一目的地的舱位信息
		return airSpaceDao.isExistSpace(dto);
	}
	
	/**
	 * 删除航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午9:01:22
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#deleteAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@Transactional
	@Override
	public void deleteAirSpace(AirSpaceDto dto) {
		//获取用户当前操作部门
		String deptCode= getOrgAdministrative().getCode();
		//与舱位配载对比,进行规则校验
		if(!StringUtils.equals(deptCode, dto.getAssembleOrgCode())){
			throw new TfrBusinessException("非本部门的录入的信息，不能删除！","");
		}
		//获取当前日期的下一天
		//获取开始日期
		Date date = DateUtils.getStartDatetime(new Date(),1);
		//如果与要删除的日期相同，则允许修改
		//判断
		if(date.equals(dto.getTakeOffDate())){
			//删除舱位信息
			//是否与删除的日期相同
			//相同则允许修改
			Long flightCount = airSpaceDao.queryFlightCountById(dto);
			//如果单条记录中舱位数量大于1，则不删除主表，只删除选中的舱位信息,小于等于1则意味着是最后一条记录，需要连同主表一起删除
			if(flightCount.intValue() > 1){
				airSpaceDao.deleteSingleSpace(dto);
			}else{
				//执行删除
				airSpaceDao.deleteAirSpace(dto);
				//删除舱位明细信息
				//根据id删除
				airSpaceDao.deleteAirSpaceDetail(dto.getId());
			}
		}else{
			//抛出异常
			//只能删除第二天的舱位信息
			//其它的不允许删除
			throw new AirSpaceException(AirSpaceException.AIRFREIGHT_EXCEPTION_ONLYDELETENEXTDAYSPACE,"");
		}
	}
	/**
	 * 根据id查询航空公司舱位及明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午5:33:30
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#queryAirSpaceById(java.lang.String)
	 */
	@Override
	public AirSpaceDto queryAirSpaceById(String airSpaceId) {
		//根据id查询航空公司舱位及明细信息
		return airSpaceDao.queryAirSpaceById(airSpaceId);
	}
	
	/**
	 * 更新舱位信息及其明细
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午7:00:17
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#updateAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@Transactional
	@Override
	public void updateAirSpace(AirSpaceDto dto) {
		//判断舱位ID是否为空，如果为空则根据日期与目的站重新获取
		if(StringUtil.isBlank(dto.getId())){
			String id = airSpaceDao.getAriSpaceId(dto);
			//设置id
			dto.setId(id);
		}
		// 更新舱位信息
		airSpaceDao.updateAirSpace(dto);
		// 删除舱位明细 ( 更新明细使用先删除后新增的方式 )
		airSpaceDao.deleteAirSpaceDetail(dto.getId());
		// 新增舱位
		addAirSpaceDetail(dto); 
	}
	
	/**
	 * 统计根据条件查询出的结果数目
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-14 上午9:33:41
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService#queryAirSpaceCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@Override
	public Long queryAirSpaceCount(AirSpaceDto dto) {
		//统计根据条件查询出的结果数目
		return airSpaceDao.queryAirSpaceCount(dto);
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