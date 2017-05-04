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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/action/ArtificialDepartAction.java
 *  
 *  FILE NAME          :ArtificialDepartAction.java
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
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.service.IArtificialDepartService;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IQueryDriverByVehicleNoService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.vo.ArtificialDepartVO;

/**
 * 
 * 任务车辆放行 系统放行权限： 公司车、短途班车外请车统一由调度来进行放行操作 ，
 * 长途外请车由外场来放行（长途外请车在外场报到
 * ，公司车及短途班车外请车在车队报到）。 对应公司车辆及短途班车外请车辆的临时放行为车队调度处理； 
 * 长途外请车辆的放行由外场操作员办理
 * ，其他统一由车队调度办理；车队调度及外场操作员临时放行对象不同。 
 * 通过车辆基础资料的字段：车辆归属类型来区分(长途外请车)来区分。
 * 司机信息公开规则：公司长途车辆的司机、司机电话只有车队调度可以看到； 
 * 其他类型车辆的司机信息都可以公开。（通过部门来验证）
 * 司机信息来源参见“通过车牌查找司机信息”。 
 * 点击任务确认按钮，车辆状态记录为“待放行”，保安PDA校验放行需求。 临时任务查询界面
 * ：只能对日期为当日的年审/季审、保养的车辆执行任务确认操作，否则系统报错。
 *  1.每个车辆放行，后台增加放行流水号列，在查询车辆放行情况页面，
 * 可以查看车辆放行信息明细。 2.点击关闭按钮时； 任务车辆页面： 当车牌号不为空时，
 * 系统信息提示， “还有任务车辆，是否继续放行？”，选择是，
 * 则不退出页面，选择否，则退出页面； 当车牌号为空时，直接退出页面。
 *  临时任务车辆页面： 当车牌号不为空时，系统信息提示，
 * “还有临时任务车辆，是否继续放行？ ”，选择是，则不退出页面，选择否，则退出页面；
 * 当车牌号为空时，直接退出页面。 车牌号与司机信息绑定关系：
 * 人工放行界面录入车牌号，后台依据车牌号，关联车辆基础资料， 
 * 检索到此车牌号的车辆归属类型为外请车时 ，从外请车辆基础资料中关联处司机的姓名、联系电话；
 * 后台依据车牌号，判断车辆归属类型为公司车 ，检索司机字段是否为空，
 * 为空时系统提示车队调度手动录入；司机工号不为空时，
 * 后台依据司机工号到车辆基础资料中关联司机姓名及联系方式。 
 * 关联司机信息，显示在查询界面。 FOSS提供车辆信息接口（年审/季审/保养）
 * ，LMS完成年审/季审/保养月计划后 ，调用接口且将信息发送给FOSS系统；
 * 如果LMS计划有变动的 ，LMS调用该接口，更新车辆信息。
 * 车队调度查看FOSS（本地）已获取的车辆信息 ，手动在系统上做临时放行确认操作。
 *  FOSS提供车辆信息接口（年审/季审/保养
 * ），LMS完成年审/季审/保养月计划后 ，调用接口且将信息发送给FOSS系统； 
 * 如果LMS计划有变动的，LMS调用该接口，更新车辆信息。
 * 车队调度查看FOSS （本地）已获取的车辆信息，手动在系统上做临时放行确认操作。
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-19 上午10:31:26
 */
public class ArtificialDepartAction extends AbstractAction {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(ArtificialDepartAction.class);

	/**
	 * 查询临时任务.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 */
	@JSON
	public String queryTemporaryAssignments() {
		try {
			List<TruckDepartEntity> departList = artificialDepartService
					.queryTemporaryAssignments(
							artificialDepartVO.getQueryEntity(),
							this.getLimit(), this.getStart());
			artificialDepartVO.setDepartList(departList);
			this.setTotalCount(artificialDepartService
					.getCount(artificialDepartVO.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 增加一条临时任务.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午2:37:44
	 */
	@JSON
	public String addTemporary() {
		// 通过有没有主键值来判断是新增还是直接对申请信息进行操作
		try {
			// 检查是否已有相同车牌号的申请信息，并且状态是待放行和失效的
			QueryDepartEntity queryEntity = new QueryDepartEntity();
			queryEntity.setVehicleNo(artificialDepartVO.getTemporaryEntity()
					.getVehicleNo());
			queryEntity.setCreateDate(DepartureHandle.getSevenDaysBefore());
			List<String> statuses = new ArrayList<String>();
			statuses.add(DepartureConstant.DEPART_STATUS_WAIT);
			statuses.add(DepartureConstant.DEPART_STATUS_Fail);
			queryEntity.setStatuses(statuses);
			List<TruckDepartEntity> truckList = departureService
					.queryDepart(queryEntity);
			if (truckList != null && truckList.size() > 0) {
				LOGGER.error("此车牌号已经被（"
						+ truckList.get(0).getApplyOrgName()
						+ "）的（"
						+ truckList.get(0).getApplyUserName()
						+ "）在"
						+ DepartureHandle.getDateStr(truckList.get(0)
								.getApplyDepartTime()) + "号做放行申请，无法再次申请");
				throw new TfrBusinessException("此车牌号已经被（"
						+ truckList.get(0).getApplyOrgName()
						+ "）的（"
						+ truckList.get(0).getApplyUserName()
						+ "）在"
						+ DepartureHandle.getDateStr(truckList.get(0)
								.getApplyDepartTime()) + "号做放行申请，无法再次申请");
			}
			// 非任务车辆
			artificialDepartVO.getTemporaryEntity().setDepartType(
					DepartureConstant.DEPART_TYPE_NOT_TASK_VEHICLE);
			departureService.addTask(artificialDepartVO.getTemporaryEntity());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 增加一条任务.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午2:37:44
	 */
	@JSON
	public String addTaskDepart() {
		// 通过有没有主键值来判断是新增还是直接对申请信息进行操作
		try {
			// 检查是否已有相同车牌号的申请信息，并且状态是待放行和失效的
			QueryDepartEntity queryEntity = new QueryDepartEntity();
			queryEntity.setVehicleNo(artificialDepartVO.getTaskEntity()
					.getVehicleNo());
			queryEntity.setCreateDate(DepartureHandle.getSevenDaysBefore());
			List<String> statuses = new ArrayList<String>();
			statuses.add(DepartureConstant.DEPART_STATUS_WAIT);
			statuses.add(DepartureConstant.DEPART_STATUS_Fail);
			queryEntity.setStatuses(statuses);
			List<TruckDepartEntity> truckList = departureService
					.queryDepart(queryEntity);
			if (truckList != null && truckList.size() > 0) {
				LOGGER.error("此车牌号已经被（"
						+ truckList.get(0).getApplyOrgName()
						+ "）的（"
						+ truckList.get(0).getApplyUserName()
						+ "）在"
						+ DepartureHandle.getDateStr(truckList.get(0)
								.getApplyDepartTime()) + "号做放行申请，无法再次申请");
				throw new TfrBusinessException("此车牌号已经被（"
						+ truckList.get(0).getApplyOrgName()
						+ "）的（"
						+ truckList.get(0).getApplyUserName()
						+ "）在"
						+ DepartureHandle.getDateStr(truckList.get(0)
								.getApplyDepartTime()) + "号做放行申请，无法再次申请");
			}
			// 任务车辆
			artificialDepartVO.getTaskEntity().setDepartType(
					DepartureConstant.DEPART_TYPE_TASK_VEHICLE);
			departureService.addTask(artificialDepartVO.getTaskEntity());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * LMS放行.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 下午4:55:45
	 */
	@JSON
	public String lmsDepart() {
		try {
			List<TruckDepartEntity> truckList = new ArrayList<TruckDepartEntity>();
			for (TruckDepartEntity tr : artificialDepartVO.getDepartList()) {
				// 时间转换
				tr.setApplyDepartTime(tr.getPlanDepartTime());
				// 司机的id跟联系方式替换为用户输入的值
				tr.setDriverCode(artificialDepartVO.getDriverCode());
				tr.setDriverName(artificialDepartVO.getDriverName());
				tr.setDriverPhone(artificialDepartVO.getDriverPhone());
				truckList.add(tr);
			}
			artificialDepartService.lmsDepart(truckList);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 通过车牌号查找司机信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午11:50:47
	 */
	@JSON
	public String queryDriverByVehicleNo() {
		try {
			try {
				artificialDepartVO
						.setRelationInfoEntity(queryDriverByVehicleNoService
								.queryDriverInfoByVehicleNo(artificialDepartVO
										.getVehicleNo()));
			} catch (Exception e) {
				LOGGER.debug("未找到车牌号对应的司机");
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * @Title: convertVehicleCode2Name
	 * @Description: 将英文车牌号转换为中文(例:YUE-X-20000------->粤X20000)
	 * @return 设定文件
	 * @return String 返回类型
	 * @see convertVehicleCode2Name
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-9 下午2:32:24
	 * @throws
	 */
	@JSON
	public String convertVehicleCode2Name() {
		try {
			// 转换
			RelationInfoEntity relationInfoEntity = departureService
					.convertVehicleCode2Name(artificialDepartVO.getVehicleNo());
			artificialDepartVO.setRelationInfoEntity(relationInfoEntity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * ******************** 默认配置 **********************.
	 */
	private static final long serialVersionUID = -2956858602690171822L;
	/**
	 * ******************** 任务车辆 **********************.
	 */
	private IArtificialDepartService artificialDepartService;
	/**
	 * ******************** 放行底层实现 **********************.
	 */
	private IDepartureService departureService;
	/**
	 * ********************车牌号查询司机信息 **********************.
	 */
	private IQueryDriverByVehicleNoService queryDriverByVehicleNoService;
	/** The artificial depart vo. */
	private ArtificialDepartVO artificialDepartVO = new ArtificialDepartVO();

	/**
	 * Gets the artificial depart vo.
	 * 
	 * @return the artificial depart vo
	 */
	public ArtificialDepartVO getArtificialDepartVO() {
		return artificialDepartVO;
	}

	/**
	 * Sets the artificial depart vo.
	 * 
	 * @param artificialDepartVO
	 *            the new artificial depart vo
	 */
	public void setArtificialDepartVO(ArtificialDepartVO artificialDepartVO) {
		this.artificialDepartVO = artificialDepartVO;
	}

	/**
	 * Sets the artificial depart service.
	 * 
	 * @param artificialDepartService
	 *            the new artificial depart service
	 */
	public void setArtificialDepartService(
			IArtificialDepartService artificialDepartService) {
		this.artificialDepartService = artificialDepartService;
	}

	/**
	 * Sets the departure service.
	 * 
	 * @param departureService
	 *            the new departure service
	 */
	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	/**
	 * Sets the query driver by vehicle no service.
	 * 
	 * @param queryDriverByVehicleNoService
	 *            the new query driver by vehicle no service
	 */
	public void setQueryDriverByVehicleNoService(
			IQueryDriverByVehicleNoService queryDriverByVehicleNoService) {
		this.queryDriverByVehicleNoService = queryDriverByVehicleNoService;
	}
}