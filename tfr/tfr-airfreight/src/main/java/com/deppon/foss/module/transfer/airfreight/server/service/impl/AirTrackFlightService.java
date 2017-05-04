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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirTrackFlightService.java
 *  
 *  FILE NAME          :AirTrackFlightService.java
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
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTrackFlightDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
/**
 * 空运跟踪 service
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:30:20
 */
public class AirTrackFlightService implements IAirTrackFlightService {
	/**
	 * 注入航班跟踪Dao
	 */
	private IAirTrackFlightDao airTrackFlightDao;
	/**
	 * 注入航空正单Service
	 */
	private IAirWaybillService airWaybillService;
	
	/**
	 * 设置 注入航班跟踪Dao.
	 *
	 * @param airTrackFlightDao the new 注入航班跟踪Dao
	 */
	public void setAirTrackFlightDao(IAirTrackFlightDao airTrackFlightDao) {
		this.airTrackFlightDao = airTrackFlightDao;
	}
	 
	/**
	 * 设置 注入航空正单Service.
	 *
	 * @param airWaybillService the new 注入航空正单Service
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}

	/**
	 * 查询空运跟踪信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:48:50
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService#queryAirTrackFlight(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto, int, int)
	 */
	@Override
	//查询空运跟踪信dto
	public List<AirTrackFlightDto> queryAirTrackFlight(AirTrackFlightDto dto,
			int start, int limit) {
		//list限制参数
		List<AirTrackFlightDto> dtoList = airTrackFlightDao.queryAirTrackFlight(dto, start, limit);
		//返回查询结果
		return dtoList;
	}
	/**
	 * 起飞跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:49:07
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService#takeOffTrack(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto)
	 */
	@Transactional
	@Override
	public void takeOffTrack(AirTrackFlightDto dto) {
		
		//设置跟踪状态为待跟踪
		dto.setTrackState(AirfreightConstants.TRACKING);
		//判断跟踪信息是否为空，如果为空则设置默认值
		if(StringUtil.isBlank(dto.getMessage())){
			//注入起飞跟踪信息至dto
			dto.setMessage("起飞跟踪");
		}
		// 新增跟踪信息
		addTrackInfo(dto);
	}
	
	/**
	 * 过程跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:49:22
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService#processTrack(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto)
	 */
	@Transactional
	@Override
	public void processTrack(AirTrackFlightDto dto) {
		// 新增跟踪信息
		addTrackInfo(dto);
	}
	
	/**
	 *  到达跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:49:36
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService#arriveTrack(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto)
	 */
	@Transactional
	@Override
	public void arriveTrack(AirTrackFlightDto dto) {
		//设置跟踪状态为待跟踪
		dto.setTrackState(AirfreightConstants.TRACKEND);
		//如果跟踪信息为这设置为默认值：到达跟踪
		if(StringUtil.isBlank(dto.getMessage())){
			//设置信息
			dto.setMessage("到达跟踪");
		}
		// 新增跟踪信息
		addTrackInfo(dto);
	}
	/**
	 * 新增跟踪信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-26 上午9:35:45
	 */
	@Transactional
	public void addTrackInfo(AirTrackFlightDto dto){
		//设置待更新正单跟踪信息
		List<AirWaybillEntity> airWaybillEntityList = createAirWaybillEntity(dto);
		//设置待新增跟踪明细信息
		List<AirTrackFlightEntity> trackEntityList = createTrackEntity(dto);
		//调用正单service更新跟踪信息
		airWaybillService.updateAirWayBillTrack(airWaybillEntityList);
		//调用跟踪dao新增跟踪明细
		airTrackFlightDao.addAirTrackFlight(trackEntityList);
	}
	
	/**
	 *  拼装跟踪信息实体
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 下午5:09:06
	 */
	private List<AirTrackFlightEntity> createTrackEntity(AirTrackFlightDto dto){
		//定义日期
		Date date = new Date();
		//日期转换
		DateUtils.convert(date, DateUtils.DATE_TIME_FORMAT);
		//航空运单号list注入
		String[] ids = dto.getAirWaybillIds();
		//定义空运跟踪单实体
		List<AirTrackFlightEntity> list = new ArrayList<AirTrackFlightEntity>();
		//定义构造器
		StringBuilder sb = new StringBuilder();
		//信息拼接
		//创建人姓名
		//转换后日期
		//得到的运单信息
		String message = sb.append(dto.getCreateUserName()).
				append(" ").append(DateUtils.convert(date, DateUtils.DATE_TIME_FORMAT)).
				append(" ").append(dto.getMessage()).toString() ;
		//信息导入
		for(String id : ids){
			AirTrackFlightEntity entity = new AirTrackFlightEntity();
			//设置id
			entity.setId(UUIDUtils.getUUID());
			//设置航空正单id
			entity.setAirWaybillId(id);
			//设置创建人code
			entity.setCreateUserCode(dto.getCreateUserCode());
			//设置创建人name
			entity.setCreateUserName(dto.getCreateUserName());
			//设置消息体(操作人姓名+时间+跟踪记录)
			entity.setMessage(message);
			//设置创建部门code
			entity.setCreateOrgCode(dto.getCreateOrgCode());
			//设置创建部门name
			entity.setCreateOrgName(dto.getCreateOrgName());
			//设置创建时间
			entity.setCreateTime(date);
			//加入list
			list.add(entity);
		}
		return list;
	}
	
	/**
	 *  拼装待更新航空正单实体
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 下午5:09:42
	 */
	private List<AirWaybillEntity> createAirWaybillEntity(AirTrackFlightDto dto){
		//dto得到航空正单号
		String[] ids = dto.getAirWaybillIds();
		//定义航空运单list实体
		List<AirWaybillEntity> list = new ArrayList<AirWaybillEntity>();
		//获取信息
		for(String id : ids){
			AirWaybillEntity entity = new AirWaybillEntity();
			//设置获取航空正单id
			entity.setId(id);
			//设置实际起飞时间
			entity.setActualTakeOffTime(dto.getActualTakeOffTime());
			//设置实际到达时间
			entity.setActualArriveTime(dto.getActualArriveTime());
			//设置跟踪状态
			entity.setTrackState(dto.getTrackState());
			//设置修改人code
			entity.setModifyUserCode(dto.getCreateUserCode());
			//设置修改人姓名
			entity.setModifyUserName(dto.getCreateOrgName());
			//设置修改时间
			entity.setModifyTime(new Date());
			//list信息加入
			list.add(entity);
		}
		return list;
	}
	
	/**
	 *  统计查询结果记录总数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 下午5:11:16
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService#queryTrackFlightCount()
	 */
	@Override
	public Long queryTrackFlightCount(AirTrackFlightDto dto) {
		//返回查询到的信息
		return airTrackFlightDao.queryTrackFlightCount(dto);
	}
	
	/**
	 * 提供给综合查询的接口，根据正单号查询跟踪信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-23 下午2:52:30
	 */
	@Override
	public List<AirTrackFlightDto> queryTrackInfoByAirWaybillNo(String airWaybillNo){
		//验证正单号不能为空
		if(StringUtils.isBlank(airWaybillNo)){
			throw new TfrBusinessException("查询条件不能为空!","");
		}
		//查询跟踪信息
		List<AirTrackFlightDto> airTrackFlightDtos =  airTrackFlightDao.queryTrackInfoByAirWaybillNo(airWaybillNo);
		//用于重组记过的list
		List<AirTrackFlightDto> resultList = new ArrayList<AirTrackFlightDto>();
		//如果查询到的集合有值，则进行下一步操作，否则返回空的list
		if(airTrackFlightDtos.size() > 0){
			//同一正单只有一个跟踪状态，所以取第一条记录
			AirTrackFlightDto airTrackFlightDto = airTrackFlightDtos.get(0);
			//如果跟踪状态为正在跟踪，则将已经排序的第一条记录添加到resultList中，用于返回
			if(AirfreightConstants.TRACKING.equals(airTrackFlightDto.getTrackState())){
				resultList.add(airTrackFlightDto);
			}
			//如果跟踪状态为跟踪完成，则除了添加第一条记录，还要添加最后一条记录
			if(AirfreightConstants.TRACKEND.equals(airTrackFlightDto.getTrackState())){
				resultList.add(airTrackFlightDto);
				if(airTrackFlightDtos.size() >= 2){
					resultList.add(airTrackFlightDtos.get(airTrackFlightDtos.size()-1));
				}
			}
		}
		//返回处理后的集合
		return resultList;
	}
}