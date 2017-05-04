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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/ArtificialDepartService.java
 *  
 *  FILE NAME          :ArtificialDepartService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IArtificialDepartDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IArtificialDepartService;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 任务车辆放行 系统放行权限： 公司车、短途班车外请车统一由调度来进行放行操作 ，
 * 长途外请车由外场来放行（长途外请车在外场报到
 * ，公司车及短途班车外请车在车队报到）。 对应公司车辆及短途班车外请车辆的临时放行为车队调度处理；
 *  长途外请车辆的放行由外场操作员办理
 * ，其他统一由车队调度办理；车队调度及外场操作员临时放行对象不同。 
 * 通过车辆基础资料的字段：车辆归属类型来区分(长途外请车)来区分。
 * 司机信息公开规则：公司长途车辆的司机、司机电话只有车队调度可以看到；
 *  其他类型车辆的司机信息都可以公开。（通过部门来验证）
 * 司机信息来源参见“通过车牌查找司机信息”。 
 * 点击任务确认按钮，车辆状态记录为“待放行”，保安PDA校验放行需求。 
 * 临时任务查询界面：只能对日期为当日的年审/季审、保养的车辆执行任务确认操作，否则系统报错。
 *  1.每个车辆放行，后台增加放行流水号列，在查询车辆放行情况页面，
 * 可以查看车辆放行信息明细。
 *  2.点击关闭按钮时； 任务车辆页面： 当车牌号不为空时，系统信息提示，
 *   “还有任务车辆，是否继续放行？”，选择是，
 * 则不退出页面，选择否，则退出页面； 当车牌号为空时，直接退出页面。
 *  临时任务车辆页面： 当车牌号不为空时，系统信息提示，
 * “还有临时任务车辆，是否继续放行？ ”，选择是，则不退出页面，选择否，则退出页面；
 *  当车牌号为空时，直接退出页面。 车牌号与司机信息绑定关系：
 * 人工放行界面录入车牌号，后台依据车牌号，关联车辆基础资料， 
 * 检索到此车牌号的车辆归属类型为外请车时 ，从外请车辆基础资料中关联处司机的姓名、联系电话；
 * 后台依据车牌号，判断车辆归属类型为公司车 ，检索司机字段是否为空，
 * 为空时系统提示车队调度手动录入；司机工号不为空时，
 * 后台依据司机工号到车辆基础资料中关联司机姓名及联系方式。 
 * 关联司机信息，显示在查询界面。 FOSS提供车辆信息接口（年审/季审/保养）
 * ，LMS完成年审/季审/保养月计划后 ，调用接口且将信息发送给FOSS系统；
 * 如果LMS计划有变动的 ，LMS调用该接口，更新车辆信息。
 * 车队调度查看FOSS（本地）已获取的车辆信息 ，手动在系统上做临时放行确认操作。 
 * FOSS提供车辆信息接口（年审/季审/保养
 * ），LMS完成年审/季审/保养月计划后 ，调用接口且将信息发送给FOSS系统； 
 * 如果LMS计划有变动的，LMS调用该接口，更新车辆信息。
 * 车队调度查看FOSS （本地）已获取的车辆信息，手动在系统上做临时放行确认操作。
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-19 上午10:31:26
 */
public class ArtificialDepartService implements IArtificialDepartService {
	/** ****日志******. */
	private static final Logger LOGGER = LogManager
			.getLogger(ArtificialDepartService.class);
	/** *****人工放行dao*****. */
	private IArtificialDepartDao artificialDepartDao;
	/** *****出发dao********. */
	private IDepartureDao departureDao;

	/**
	 * 查询临时任务.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @param limit
	 *            the limit
	 * @param start
	 *            the start
	 * @return the list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	@Override
	public List<TruckDepartEntity> queryTemporaryAssignments(
			QueryDepartEntity queryEntity, int limit, int start) {
		// 查询临时任务
		return artificialDepartDao.queryTemporaryAssignments(queryEntity,
				limit, start);
	}

	/**
	 * Sets the artificial depart dao.
	 * 
	 * @param artificialDepartDao
	 *            the new artificial depart dao
	 */
	public void setArtificialDepartDao(IArtificialDepartDao artificialDepartDao) {
		this.artificialDepartDao = artificialDepartDao;
	}

	/**
	 * Sets the departure dao.
	 * 
	 * @param departureDao
	 *            the new departure dao
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	/**
	 * LMS放行.
	 * 
	 * @param truckList
	 *            the truck list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override
	public void lmsDepart(List<TruckDepartEntity> truckList) {
		for (TruckDepartEntity tr : truckList) {
			String uuid = UUIDUtils.getUUID();
			LmsTruckDepartPlanEntity lms = new LmsTruckDepartPlanEntity();
			// 任务ID
			lms.setId(tr.getId());
			// 放行ID
			lms.setTruckDepartId(uuid);
			// 插入一条车辆放行信息
			tr.setId(uuid);
			// 非任务车辆
			tr.setDepartType(DepartureConstant.DEPART_TYPE_NOT_TASK_VEHICLE);
			// 申请时间
			tr.setApplyDepartTime(DepartureHandle.getCurrentDate());
			// 申请方式
			tr.setApplyType(DepartureConstant.DEPART_APPLY_TYPE_MANUAL);
			tr.setApplyUserCode(DepartureHandle.getCurrentUserCode());
			// 申请部门设置成当前部门对应的外场的编码
			tr.setApplyOrgCode(departureService.queryBigOrgCode(DepartureHandle
					.getCurrentOrgCode()));
			tr.setApplyDepartTime(DepartureHandle.getCurrentDate());
			// 状态
			tr.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
			// 司机编码
			tr.setDriverCode(tr.getDriverCode());
			// 创建时间
			tr.setCreateTime(DepartureHandle.getCurrentDate());
			// 创建人工号
			tr.setCreateUserCode(DepartureHandle.getCurrentOrgCode());
			// 创建部门编码
			tr.setCreateOrgCode(DepartureHandle.getCurrentOrgCode());
			// 创建人名称
			tr.setCreateUserName(DepartureHandle.getCurrentUserName());
			// 更新人编码
			tr.setUpdateUserCode(DepartureHandle.getCurrentOrgCode());
			// 更新部门编码
			tr.setUpdateOrgCode(DepartureHandle.getCurrentOrgCode());
			// 更新人名称
			tr.setUpdateUserName(DepartureHandle.getCurrentUserName());
			// 更新时间
			tr.setUpdateTime(DepartureHandle.getCurrentDate());
			tr.setActiveTime(new Date());
			departureDao.addManual(tr);
			// 更新LMS计划放行表
			LOGGER.info("人工放行后更新LMS记录");
			artificialDepartDao.updateLMS(lms);
		}
	}

	/**
	 * 查询记录条数，用于分页.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the count
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:12:49
	 */
	@Override
	public Long getCount(QueryDepartEntity queryEntity) {
		// 查询记录条数
		return artificialDepartDao.getCount(queryEntity);
	}

	/** ***** 放行接口 ******. */
	private IDepartureService departureService;

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}
}