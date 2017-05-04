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
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/dao/impl/DepartureDao.java
 *  
 *  FILE NAME          :DepartureDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.dao.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.dto.DepartArriveToTpsDto;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.IVehicleInfoDao;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.ArrivalInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.DepartInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.OnTheWayInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsVehicleDailySummaryDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.TruckTaskBillSummaryDto;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleInfoForGpsDTO;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;
/**
 * 
 * 查询车辆放行 系统默认显示数据原则：
 * 公司车、短途班车班车外请车统一由调度来进行放行操作
 * ，长途外请车由外场来放行（
 * 长途外请车在外场报到，公司车及短途班车班车外请车在车队报到）。
 * 对应公司车辆及短途班车班车外请车辆的临时放行为车队调度处理；
 * 长途外请车辆的临时放行为外场操作员处理；
 * 车队调度及外场操作员管理的车辆列表数据源不同。
 * 司机信息公开规则：公司长途车辆的司机
 * 、司机电话只有车队调度可以看到；其他类型车辆的司机信息都可以公开
 * 。（通过部门来验证） 司机信息来源参见“通过车牌查找司机信息”。
 * 点击任务确认按钮，车辆状态记录为“待放行”，保安PDA校验放行需求。
 * 临时任务查询界面
 * ：只能对日期为当日的年审/季审、保养的车辆执行任务确认操作，否则系统报错。
 * 1.每个车辆放行，后台增加放行流水号列，在查询车辆放行情况页面，
 * 可以查看车辆放行信息明细。 2.点击关闭按钮时； 任务车辆页面：
 * 当车牌号不为空时，系统信息提示，
 * “还有任务车辆，是否继续放行？”，选择是，
 * 则不退出页面，选择否，则退出页面； 当车牌号为空时，直接退出页面。
 * 临时任务车辆页面： 当车牌号不为空时，系统信息提示，
 * “还有临时任务车辆，是否继续放行？
 * ”，选择是，则不退出页面，选择否，则退出页面；
 * 当车牌号为空时，直接退出页面。 车牌号与司机信息绑定关系：
 * 人工放行界面录入车牌号，后台依据车牌号，关联车辆基础资料，
 * 检索到此车牌号的车辆归属类型为外请车时
 * ，从外请车辆基础资料中关联处司机的姓名、联系电话；
 * 后台依据车牌号，判断车辆归属类型为公司车
 * ，检索司机字段是否为空，为空时系统提示车队调度手动录入；司机工号不为空时，
 * 后台依据司机工号到车辆基础资料中关联司机姓名及联系方式。
 * 关联司机信息，显示在查询界面。 放行方式：记录为纸质确认放行。
 * 放行状态：纸质放行的车辆，车辆状态记录为已出发。
 * 打印放行条模板，参见系统用例“查询车辆放行情况”。
 * 打印放行条按钮：此按钮，
 * 针对来源LMS系统的年审/季审、保养数据来开具纸质放行条。
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-19 上午10:34:55
 */
@SuppressWarnings("unchecked")
public class DepartureDao extends iBatis3DaoImpl implements IDepartureDao,
		IVehicleInfoDao {
	private static final String NAMESPACE = "tfr-departure.";
	/**
	 * 
	 * 查询放行信息（分页）
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckDepartEntity> queryDepart(
			QueryDepartEntity queryEntity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		List<TruckDepartEntity> truckDepartkList = new ArrayList<TruckDepartEntity>();
		// 防止queryEntity.currentOrgCodes超过1000个
		if(queryEntity.getCurrentOrgCodes()!=null){
			if(queryEntity.getCurrentOrgCodes().size()>LoadConstants.SONAR_NUMBER_1000)
			{
				List<String> tmpCurrentOrgCodes = queryEntity.getCurrentOrgCodes();
				while(tmpCurrentOrgCodes.size()>LoadConstants.SONAR_NUMBER_1000)
				{
					int splitlen = tmpCurrentOrgCodes.size()>2?2:tmpCurrentOrgCodes.size();
					List<String> tmp = tmpCurrentOrgCodes.subList(splitlen, tmpCurrentOrgCodes.size());
					tmpCurrentOrgCodes = tmpCurrentOrgCodes.subList(0, splitlen);
					queryEntity.setCurrentOrgCodes(tmpCurrentOrgCodes);
					
					truckDepartkList.addAll(this.getSqlSession()
							.selectList(NAMESPACE + "departureQuery", queryEntity));
					
					tmpCurrentOrgCodes = tmp;
				}
				if(truckDepartkList.size()>limit){
					truckDepartkList = truckDepartkList.subList(start, 
							truckDepartkList.size()>(start+limit)?(start+limit):truckDepartkList.size());
				}

				return truckDepartkList;
			}
			
		}
		
		truckDepartkList = this.getSqlSession().selectList(NAMESPACE + "departureQuery", queryEntity,
							rowBounds);
		return truckDepartkList;
	}
	/**
	 * 
	 * 查询放行信息（不分页）
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckDepartEntity> queryDepart(
			QueryDepartEntity queryEntity) {
		List<TruckDepartEntity> truckDepartkList = new ArrayList<TruckDepartEntity>();
		// 防止queryEntity.currentOrgCodes超过1000个
		if(queryEntity.getCurrentOrgCodes()!=null){
			if(queryEntity.getCurrentOrgCodes().size()>LoadConstants.SONAR_NUMBER_1000)
			{
				List<String> tmpCurrentOrgCodes = queryEntity.getCurrentOrgCodes();
				while(tmpCurrentOrgCodes.size()>LoadConstants.SONAR_NUMBER_1000)
				{
					int splitlen = tmpCurrentOrgCodes.size()>2?2:tmpCurrentOrgCodes.size();
					List<String> tmp = tmpCurrentOrgCodes.subList(splitlen, tmpCurrentOrgCodes.size());
					tmpCurrentOrgCodes = tmpCurrentOrgCodes.subList(0, splitlen);
					queryEntity.setCurrentOrgCodes(tmpCurrentOrgCodes);
					
					truckDepartkList.addAll(this.getSqlSession().selectList(NAMESPACE + "departureQuery", queryEntity));
					
					tmpCurrentOrgCodes = tmp;
				}
				
				return truckDepartkList;
			}
		}
		
		truckDepartkList = this.getSqlSession()
					.selectList(NAMESPACE + "departureQuery", queryEntity);
		return truckDepartkList;
	}
	/**
	 * 
	 * 放行查到的条数
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:52:24
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#getCount(com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity)
	 */
	@Override public Long getCount(QueryDepartEntity queryEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getCount",
				queryEntity);
	}
	/**
	 * 
	 * 判断放行的状态是否可放行
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public Long validBeforeCancleOrActiveDepart(
			QueryDepartEntity queryEntity) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "validBeforeCancleOrActiveDepart", queryEntity);
	}
	/**
	 * 
	 * 放行前校验是否有相同车牌号的待放行申请或者失效的申请
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public Long validBeforeDepart(QueryDepartEntity queryEntity) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "validBeforeDepart", queryEntity);
	}
	/**
	 * 
	 * 查询交接单信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckTaskBillEntity> queryTaskBill(
			VehicleDetailDTO dto) {
		List<TruckTaskBillEntity> taskBillList = this.getSqlSession()
				.selectList(NAMESPACE + "queryTaskBillById", dto);
		return taskBillList;
	}
	
	/**
	 * 在途跟踪优化需求
	 * 查询车辆任务下运单的总票数, 总总量, 总体积;
	 * 根据运单运输性质分组
	 * (只查询运输性质为“精准卡航”、“精准城运”货物<业务部门要求>) 感觉好奇葩
	 * @author 163580
	 * @date 2014-5-7 下午2:15:30
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IVehicleInfoDao#queryTaskBillSummary(com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskBillSummaryDto> queryTaskBillSummary(
			VehicleDetailDTO dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTaskBillSummary", dto);
	}
	
	/**
	 * 
	 * 取消放行信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void cancleDepart(List ids) {
		QueryDepartEntity queryDepartEntity = new QueryDepartEntity();
		// 设置取消状态
		queryDepartEntity.setStatus(DepartureConstant.DEPART_STATUS_CANCLED);
		queryDepartEntity.setIdsForCancle(ids);
		this.getSqlSession().update(NAMESPACE + "cancleOrActiveDepart",
				queryDepartEntity);
	}
	/**
	 * 
	 * 取消放行信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void cancleDepartByTaskId(QueryDepartEntity queryEntity) {
		this.getSqlSession().update(NAMESPACE + "cancleDepartByTaskId",
				queryEntity);
	}
	/**
	 * 
	 * 重新激活失效放行信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void activeDepart(List ids) {
		QueryDepartEntity queryDepartEntity = new QueryDepartEntity();
		// 设置激活状态
		queryDepartEntity.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
		queryDepartEntity.setIdsForCancle(ids);
		this.getSqlSession().update(NAMESPACE + "cancleOrActiveDepart",
				queryDepartEntity);
	}
	/**
	 * 
	 * 查询到达信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public ArrivalInfoEntity queryTaskDetail(VehicleDetailDTO dto) {
		List<ArrivalInfoEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "arrivalQuery", dto);
		if (null != list && list.size() > 0) {
			return list.get(0);
		} else {
			return new ArrivalInfoEntity();
		}
	}
	/**
	 * 
	 * 查询在途信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public OnTheWayInfoEntity queryOnTheWay(VehicleDetailDTO dto) {
		List<OnTheWayInfoEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "onTheWayQuery", dto);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new OnTheWayInfoEntity();
	}
	/**
	 * 
	 * 查询放行信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public DepartInfoEntity queryDepart(VehicleDetailDTO dto) {
		List<DepartInfoEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "departQuery", dto);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new DepartInfoEntity();
	}
	
	/**
	 * 
	 * 查询要放行车牌号下是否有多个已确认状态的派单单号车辆未放行
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 * 
	 */
	@Override 
	public List<String> queryDeliverbill(String id) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "queryDeliverbill", id);
		return list;
	}
	
	/**
	 * 
	 * 查询全部要放行的记录
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 * 
	 */
	@Override 
	public List<TruckDepartEntity> queryManualAll(String vehicleNo) {
		List<TruckDepartEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryManualAll", vehicleNo);
		return list;
	}
	
	/**
	 * 
	 * 查询任务车辆明细中交接单、配载单的信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public BusinessInfoEntity queryBusiness(VehicleDetailDTO dto) {
		// 取得交接单的体积、数量和
		List<BusinessInfoEntity> handovlist = this.getSqlSession().selectList(
				NAMESPACE + "handovQuery", dto);
		BusinessInfoEntity handov = new BusinessInfoEntity();
		if (null == handovlist || handovlist.size() < 1) {
			handov = new BusinessInfoEntity();
		} else {
			for (BusinessInfoEntity entity : handovlist) {
				if(entity!=null)
				{
					handov.setWeight(handov.getWeight().add(entity.getWeight()));
					handov.setVolume(handov.getVolume().add(entity.getVolume()));
					handov.setWaybill(handov.getWaybill() + entity.getWaybill());
				}
			}
		}
		// 取得配载单的体积、数量和
		List<BusinessInfoEntity> vehicleasslist = this.getSqlSession()
				.selectList(NAMESPACE + "vehicleassQuery", dto);
		BusinessInfoEntity vehicleass = new BusinessInfoEntity();
		if (null == vehicleasslist || vehicleasslist.size() < 1) {
			vehicleass = new BusinessInfoEntity();
		} else {
			for (BusinessInfoEntity entity : vehicleasslist) {
				if(entity!=null)
				{
					vehicleass.setWeight(vehicleass.getWeight().add(
							entity.getWeight()));
					vehicleass.setVolume(vehicleass.getVolume().add(
							entity.getVolume()));
					vehicleass.setWaybill(vehicleass.getWaybill()
							+ entity.getWaybill());
				}
			}
		}
		BusinessInfoEntity business = new BusinessInfoEntity();
		if (handov.getWeight() != null) {
			business.setWeight(handov.getWeight().add(vehicleass.getWeight()));
			business.setVolume(handov.getVolume().add(vehicleass.getVolume()));
			business.setWaybill(handov.getWaybill() + vehicleass.getWaybill());
		}
		return business;
	}
	/**
	 * 
	 * 查询任务信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public ArrivalInfoEntity queryBusinessInfo(VehicleDetailDTO dto) {
		List<ArrivalInfoEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "arrivalQuery", dto);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return new ArrivalInfoEntity();
	}
	/**
	 * 
	 * 新增任务车辆明细信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void addManual(TruckDepartEntity manualEntity) {
		this.getSqlSession().insert(NAMESPACE + "insertDepartEntity",
				manualEntity);
	}
	/**
	 * 
	 * 保存任务车辆明细信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void saveManual(TruckDepartEntity manualEntity) {
		this.getSqlSession().insert(NAMESPACE + "updateManual", manualEntity);
	}
	/**
	 * 
	 * 更新任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 下午2:44:09
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#updateTaskByManual(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override public String updateTaskByManual(TruckTaskDetailEntity detail) {
		this.getSqlSession().update(NAMESPACE + "updateTaskByManual", detail);
		
		// job表增加一条数据
		TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
		// 类型
		String bundType = "-1";
		if(StringUtils.isNotEmpty(detail.getActualArriveType())) {
			bundType = detail.getActualArriveType();
		}
		actionDetail.setBundType(bundType);
		// 任务车辆明细ID
		String id = detail.getId();
		if(StringUtils.isEmpty(id)) {
			//如果为空则取TruckTaskDetailId
			//此ID正常情况中应该和上面的相同
			id = detail.getTruckTaskDetailId();
		}
		if(StringUtils.isEmpty(id)) {
			//如果上面的都为空则去主任务的id
			id = detail.getTruckTaskId();
		}
		if(StringUtils.isEmpty(id)) {
			//如果还为空则取车牌号
			id = detail.getVehicleNo();
		}
		//目的: 尽可能的标识出当前操作的相关数据, 方便日后排查问题
		if(StringUtils.isEmpty(id)) {
			//如果继续为空就设置为空
			id = "null";
		}
		actionDetail.setTruckTaskDetailId(id);
		// 状态
		actionDetail.setStatus("-1");
		// 创建时间
		actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
		
		//该方法可能由其他系统调用过来, 因此可能无法获取到操作用户
		UserEntity user = null;
		try {
			user = FossUserContext.getCurrentUser();
		} catch (Exception e) {
			logger.error("用户未登录FOSS或在非FOSS系统操作");
		}
		String code = "FOSS";
		String name = "FOSS";
		if(user != null) {
			EmployeeEntity employee = user.getEmployee();
			if(employee != null) {
				code = employee.getEmpCode();
				name = employee.getEmpName();
			}
			if(StringUtils.isEmpty(code)) {
				code = "FOSS";
			}
			if(StringUtils.isEmpty(name)) {
				name = "FOSS";
			}
		}
		
		//如果到达操作为最终到达后自动到达其他子任务
		if(StringUtils.equals(detail.getActualArriveType(), DepartureConstant.ACTUAL_DEPART_TYPE_FOSS)) {
			code += "自动到达子任务";
			name += "自动到达子任务";
		}
		
		actionDetail.setOperatorCode(code);
		actionDetail.setOperatorName(name);
		addTruckActionDetail(actionDetail);
		return null;
	}
	/**
	 * 
	 * 取消车辆到达
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 下午2:44:09
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#updateTaskDetailCancleArrive(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override public String updateTaskDetailCancleArrive(
			TruckTaskDetailEntity detail) {
		this.getSqlSession().update(NAMESPACE + "updateTaskDetailCancleArrive",
				detail);
		return null;
	}
	/**
	 * 
	 * 取消车辆放行
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 下午2:44:09
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#updateTaskDetailCancleDepart(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override public String updateTaskDetailCancleDepart(
			TruckTaskDetailEntity detail) {
		this.getSqlSession().update(NAMESPACE + "updateTaskDetailCancleDepart",
				detail);
		return null;
	}
	/**
	 * 
	 * 查询LMS数据
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckDepartEntity> queryTemporaryAssignments(
			QueryDepartEntity queryEntity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		List<TruckDepartEntity> waybillStockList = this.getSqlSession()
				.selectList(NAMESPACE + "queryTemporaryAssignments",
						queryEntity, rowBounds);
		return waybillStockList;
	}
	/**
	 * 
	 * 新增任务车辆job表哦
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void addTruckActionDetail(
			TruckActionDetailEntity actionDetail) {
		this.getSqlSession().insert(NAMESPACE + "insertTruckActionDetail",
				actionDetail);
	}
	/**
	 * 
	 * 删除任务车辆job表
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void deleteTruckActionDetail() {
		this.getSqlSession().delete(NAMESPACE + "deleteTruckActionDetail");
	}
	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckTaskDetailEntity> queryTruckTaskDetail(
			TruckTaskDetailEntity truckTaskDetailEntity) {
		List<TruckTaskDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckTaskDetail", truckTaskDetailEntity);
		return list;
	}
	

	
	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-336540
	 * @date 2016年9月1日11:54:37
	 */
	@Override public List<TruckTaskDetailEntity> queryTruckTaskDetailByIDList(String id){
		List<TruckTaskDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckTaskDetailById", id);
		return list;
	}
	
	/**
	 * 
	 * 根据车辆放行id获取该车辆放行对应的任务下，出发部门一致，车牌号一致的所有的任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckTaskDetailEntity> queryTruckTaskDetailByDepartId(
			TruckTaskDetailEntity truckTaskDetailEntity) {
		List<TruckTaskDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckTaskDetailByDepartId", truckTaskDetailEntity);
		return list;
	}
	
	/**
	 * 
	 * 根据GPSid获取该车辆放行对应的任务下，出发部门一致，车牌号一致的所有的任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<TruckTaskDetailEntity> queryTruckTaskDetailByTaskId(
			TruckTaskDetailEntity truckTaskDetailEntity) {
		List<TruckTaskDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckTaskDetailByTaskId", truckTaskDetailEntity);
		return list;
	}
	
	
	
	/**
	 * @author 268084
	 * @date 1015-07-14
	 * 
	 * 根据车辆任务编号，到达部门编号，车牌号查找车辆任务详情，判断是否为GPS第一次到达，如果是就调用结算接口，不是什么都不做
	 */
	@Override
	public List<TruckTaskDetailEntity> queryJudgeIsFirstArrival(
			TruckTaskDetailEntity truckTaskDetailEntity) {
		
		return this.getSqlSession().selectList(
				NAMESPACE + "queryJudgeIsFirstArrival", truckTaskDetailEntity);
	}
	/**
	 * 
	 * 更新任务车辆表
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 下午2:44:38
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#updateTruckTask(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override public String updateTruckTask(TruckTaskDetailEntity detail) {
		this.getSqlSession().update(NAMESPACE + "updateTruckTask", detail);
		return null;
	}
	/**
	 * 
	 * GPSjiekou更新出发信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void notifyStarttime(GpsNotifyDTO gpsNotifyDTO) {
		this.getSqlSession()
				.update(NAMESPACE + "notifyStarttime", gpsNotifyDTO);
	}
	/**
	 * 
	 * GPS接口更新到达信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void notifyArrivaltime(GpsNotifyDTO gpsNotifyDTO) {
		this.getSqlSession().update(NAMESPACE + "notifyArrivaltime",
				gpsNotifyDTO);
	}
	/**
	 * 
	 * GPS接口更新车辆任务信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void updateVehicleTrack(GpsNotifyDTO gpsNotifyDTO) {
		this.getSqlSession().insert(NAMESPACE + "updateVehicleTrack",
				gpsNotifyDTO);
	}
	/**
	 * 
	 * 通过放行ID获得PDA放行信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public PDADepartResultDTO getPDADepartResult(String departId) {
		List<PDADepartResultDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getPDADepartResult", departId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		} else {
			return new PDADepartResultDTO();
		}
	}
	/**
	 * 
	 * 通过放行ID获得交接单号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<String> getHandoverBillsByDepartId(String departId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getHandoverBillsByDepartId", departId);
		return list;
	}
	/**
	 * 
	 * 通过放行ID获得封签号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<String> getSealNosByDepartId(String departId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getSealNosByDepartId", departId);
		return list;
	}
	/**
	 * 
	 * 通过GPSID查询车辆任务明细ID
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public List<VehicleInfoForGpsDTO> queryTruckTaskDetailByGpsId(
			String gpsId) {
		List<VehicleInfoForGpsDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckTaskDetailByGpsId", gpsId);
		return list;
	}
	/**
	 * 
	 * 更新LMS放行计划
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	@Override public void insertLMS(LmsTruckDepartPlanEntity lms) {
		this.getSqlSession().insert(NAMESPACE + "insertLMS", lms);
	}
	@Override public List<TruckTaskDetailEntity> getTaskIdByDepartId(
			String departId) {
		List<TruckTaskDetailEntity> waybillStockList = this.getSqlSession()
				.selectList(NAMESPACE + "getTaskIdByDepartId", departId);
		return waybillStockList;
	}
	/**
	 * 
	 * 放行的时候判断是否是集配交接单，集配交接单不能放行
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	@Override public List<String> isDistanceHandover(QueryDepartEntity queryEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "isDistanceHandover",
				queryEntity);
	}
	/**
	 * 更新车辆放行的时间
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	@Override public void updateTruckDepartTimeByTask(TruckDepartEntity manualEntity) {
		this.getSqlSession().insert(NAMESPACE + "updateTruckDepartByTask", manualEntity);
		
	}
	/**
	 * 
	 *  提供接口给接送货，根据运单查询在途车辆任务
	 * @author alfred
	 * @date 2013-9-11 上午10:42:18
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#querycarLoadTruckTaskDetailList(java.lang.String)
	 */
	@Override
	public List<TruckTaskDetailEntity> querycarLoadTruckTaskDetailList(
			String waybillNo) {
		List<TruckTaskDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "querycarLoadTruckTaskDetailList", waybillNo);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> queryUnArrivalTaskByTaskId(
			String truckTaskId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryUnArrivalTaskByTaskId", truckTaskId);
	}
	
	/**
	 * 根据任务车辆明细ID查询任务车辆明细
	 * @author 105795
	 * @date 2014年11月16日 16:55:25
	 * @param id
	 * @return
	 */
	public TruckTaskDetailEntity queryTruckTaskDetailById(String id)
	{
		return (TruckTaskDetailEntity) this.getSqlSession().selectOne("tfr-task.queryTruckTaskDetailById", id);
	}
	
	/**GPS供应商同步车辆信息经过DOP到ESB再到FOSS
	 * @author heyongdong
	 * @param GpsVehicleDailySummaryDTO
	 * @date 2014年11月21日 09:12:24 
	 */
	@Override
	public void insertVehicleDailySummary(List<GpsVehicleDailySummaryDTO> vehicleDailys) {
		String name = NAMESPACE+"insertVehicleDailySummary";
		if(vehicleDailys!=null){
			for(GpsVehicleDailySummaryDTO vehicleDaily:vehicleDailys){
				this.getSqlSession().insert(name, vehicleDaily);
			}
		}
	}
	
	/**
	 * 根据车辆任务明细和TPS信息部 查询 发车要传递的信息
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-15
	 * @param truckTaskDetailId
	 * @param infoDept
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DepartArriveToTpsDto> synchDepartArriveInfoToTps(String truckTaskDetailId,String infoDept){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("truckTaskDetailId", truckTaskDetailId);
		map.put("infoDept", infoDept);
		return this.getSqlSession().selectList(NAMESPACE+"synchDepartArriveInfoToTps", map);
	}
	
	/**
	 * 计算配载单的总重量和总体积
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-16
	 * @param id
	 * @return
	 */
	public DepartArriveToTpsDto queryWeightVolumeByVehicleassembleNo(String id){
		return (DepartArriveToTpsDto) this.getSqlSession().selectOne(NAMESPACE+"queryWeightVolumeByVehicleassembleNo", id);
	}
	
	
	/**
	 * 营业部交接卸车改到达时间
	 * 更新任务车辆明细
	 * @author 360903
	 * @date 2016年12月14日 12:49:19
	 */
	@Override 
	public void updateTaskByByHandOverBillSale(String handOverBillNo) {
		TruckTaskDetailEntity detail = new TruckTaskDetailEntity();
		detail.setTruckDepartId(handOverBillNo);
		this.getSqlSession().update(NAMESPACE + "updateTaskByHandOverBillSale", detail);
	}
	
}