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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/StayHandoverDetailService.java
 * 
 * FILE NAME        	: StayHandoverDetailService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.util.define.FossConstants;
/**
 * 交接明细Service实现类
 * @author foss-meiying
 * @date 2012-11-9 下午7:17:20
 * @since
 * @version
 */
public class StayHandoverDetailService  implements IStayHandoverDetailService {
	/**
	 * 交接明细dao接口
	 */
	private IStayHandoverDetailDao stayHandoverDetailDao;
	/**
	 * 添加交接明细表
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:30:50
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService#addStayHandoverDetail(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity)
	 */
	@Override
	@Transactional
	public StayHandoverDetailEntity addStayHandoverDetail(StayHandoverDetailEntity entity) {
		return stayHandoverDetailDao.addStayHandoverDetail(entity);
	}
	
	/**
	 * 查询WaybillPending(运单带处理信息)里的运单号，货物总件数,是否作废
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:30:37
	 * @param dto
	 * @return 
	 */
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPending(
			String driverCode) {
		return stayHandoverDetailDao.queryPickupByWaybillPending(driverCode);
	}
	
	
	/**
	 * 根据快递员查询补录的接货信息
	 * @param expressEmpCode
	 * @return
	 */
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingExpress(
			String expressEmpCode) {
		return stayHandoverDetailDao.queryPickupByWaybillPendingExpress(expressEmpCode);
	}

	/**
	 * Gets the 交接明细dao接口.
	 *
	 * @return the 交接明细dao接口
	 */
	public IStayHandoverDetailDao getStayHandoverDetailDao() {
		return stayHandoverDetailDao;
	}
	
	/**
	 * Sets the 交接明细dao接口.
	 *
	 * @param stayHandoverDetailDao the new 交接明细dao接口
	 */
	public void setStayHandoverDetailDao(
			IStayHandoverDetailDao stayHandoverDetailDao) {
		this.stayHandoverDetailDao = stayHandoverDetailDao;
	}
	/**
	 * 根据派送单编号 修改交接id 
	 * @author foss-meiying
	 * @date 2013-2-1 上午10:05:07
	 * @param dto 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService#updateByDeliverbillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public void updateByDeliverbillNo(DeliverbillDetailDto dto) {
		stayHandoverDetailDao.updateByDeliverbillNo(dto);
	}

	/**
	 * 根据运单号查询交接明细信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:04:49
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService
	 * #queryByWaybillNo(java.lang.String)
	 */
	@Override
	public StayHandoverDetailEntity queryByWaybillNo(String waybillNo) {
		return stayHandoverDetailDao.queryByWaybillNo(waybillNo);
	}
	/**
	 * 根据主键修改交接明细信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:43:24
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService
	 * #updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(StayHandoverDetailEntity entity) {
		return stayHandoverDetailDao.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 根据车号查询WayBillPending表里的货物信息，是否作废
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-23 上午8:28:09
	* @param @param vehicleNo
	* @param @return    设定文件
	 */
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingByVehicleNo(
			String vehicleNo,boolean isDriver) {
		StayHandoverDto param = new StayHandoverDto();
		param.setVehicleNo(vehicleNo);//司机车号
		param.setHandoverStatus(FossConstants.NO);//未生成交接
		Double result = getStartTime();
		if(isDriver){ //零担的运单判断是否在0-2点提交接货任务
			if(result>=0 && result<=2){ //0~2点之间提交,接货任务清零时间优化
				param.setIsExpandTime("Y");
			}
		}
		return stayHandoverDetailDao.queryPickupByWaybillPendingByVehicleNo(param);
	}
	/**
	 * @author 231438
	 * @return Long
	 * 获取当前操作时间与当天的0点0分0秒之间的差值
	 */
	private Double getStartTime(){  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        Long date = new Date().getTime(); //当前时间，转化为Long值
        //todayStart.getTime().getTime()当天的0点0分0秒的Long值
        return (date-todayStart.getTime().getTime())/SettlementReportNumber.ONE_THOUSAND/3600.00;  
    }

	/**
	 * add by 329757
	 * 根据司机工号查询waybillpending表里的货物信息
	 */
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingDetail(
			String driverCode, boolean isDriver) {
		StayHandoverDto param = new StayHandoverDto();
		param.setDriverCode(driverCode);//车牌号
		param.setHandoverStatus(FossConstants.NO);//未生成交接
		Double result = getStartTime();
		if(isDriver){ //零担的运单判断是否在0-2点提交接货任务
			if(result>=0 && result<=2){ //0~2点之间提交,接货任务清零时间优化
				param.setIsExpandTime("Y");
			}
		}
		return stayHandoverDetailDao.queryPickupByWaybillPendingDetail(param);
	}  


}