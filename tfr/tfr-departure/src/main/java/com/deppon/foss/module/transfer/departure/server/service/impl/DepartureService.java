/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/DepartureService.java
 *  
 *  FILE NAME          :DepartureService.java
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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTPSService;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.StartArriveRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.StowageBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.DepartArriveToTpsDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.IVehicleInfoDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IQueryDriverByVehicleNoService;
import com.deppon.foss.module.transfer.departure.api.server.service.IUpdateTaskStatusService;
import com.deppon.foss.module.transfer.departure.api.server.service.IVehicleInfoService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.ArrivalInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.DepartInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.OnTheWayInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.VehicleInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.TruckTaskBillSummaryDto;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehiclePrintDTO;
import com.deppon.foss.module.transfer.departure.api.utils.Constants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.UUIDUtils;
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
public class DepartureService implements IDepartureService, IVehicleInfoService {
	/** *********日志************. */
	private static final Logger LOGGER = LogManager
			.getLogger(DepartureService.class);
	
	/**
	 * 配置参数获取派送任务放行时间段失败，则使用该默认值，早七点-晚九点半
	 */
	private static final String DEFAULT_DELIVER_TIME_RANGE = "0700-2130";
	
	/**
	 * 配置参数service，用于获取派送任务放行时间段
	 */
	private IConfigurationParamsService configurationParamsService;
	
	private IWkBillAddTfrNotifyService wkBillAddTfrNotifyService;
	
	/**
	 * @param wkBillAddTfrNotifyService the wkBillAddTfrNotifyService to set
	 */
	public void setWkBillAddTfrNotifyService(IWkBillAddTfrNotifyService wkBillAddTfrNotifyService) {
		this.wkBillAddTfrNotifyService = wkBillAddTfrNotifyService;
	}

	/**
	 * 插入待办job表
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	/**
	 * 同步给快递车辆任务 通知表
	 */
	private ITfrNotifyService tfrNotifyService;
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

	private IFossToCubcService fossToCubcService;

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	

	/**
	 * 分页查询放行申请记录.
	 * 1.任务车辆的数据来源于“申请车辆放行（自动
	 * ）”用例，属于系统自动放行获取。
	 * 2.临时任务车辆数据来源于“申请车辆放行
	 * （人工）用例，属于系统中人工放行获取。
	 * 3.临时放空出发（约车）车辆数据来源于
	 * “系统约车”，属于系统约车放空出发放行。
	 * 4.系统默认显示“待放行状态的车辆
	 * ”，即车辆已做出发申请但保安处未放行车辆。
	 * 5.车辆放行包含任务车辆及临时任务车辆，系统默认显示全部车辆。
	 * 6.起始时间：默认显示当前时间点，当日时间段内。
	 * 7.【查询条件结果显示】界面的字段排序等要求参见1.5.3.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @param limit
	 *            the limit
	 * @param start
	 *            the start
	 * @return the list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	@Override public List<TruckDepartEntity> queryDepart(
			QueryDepartEntity queryEntity, int limit, int start) {
		return departureDao.queryDepart(queryEntity, limit, start);
	}
	@Override public List<TruckDepartEntity> queryDepart(
			QueryDepartEntity queryEntity) {
		return departureDao.queryDepart(queryEntity);
	}
	/**
	 * 取得记录数，用于分页.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the count
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:27
	 */
	@Override public Long getCount(QueryDepartEntity queryEntity) {
		return departureDao.getCount(queryEntity);
	}
	/**
	 * 通过车牌号取得车辆的信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the vehicle info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public VehicleInfoEntity getVehicleInfo(String vehicleNo) {
		// 根据车牌号查询车辆信息
		VehicleAssociationDto dto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		VehicleInfoEntity vehicleInfoEntity = new VehicleInfoEntity();
		if (null != dto) {
			// 车牌号
			vehicleInfoEntity.setVehicleNo(vehicleNo);
			if (DepartureConstant.TRUCK_TYPE_LEASED.equals(dto
					.getVehicleOwnershipType())) {
				// 通过是否存在直属部门来判断是否外请车
				vehicleInfoEntity.setTruckType("外请车");
			}
			if (DepartureConstant.TRUCK_TYPE_OWN.equals(dto
					.getVehicleOwnershipType())) {
				// 公司车
				vehicleInfoEntity.setTruckType("公司车");
			}
			// 车辆业务类型
			vehicleInfoEntity.setBusinessType(dto.getVehicleUsedTypeName());
			// 车辆归属部门名称
			vehicleInfoEntity.setTruckOrgName(dto.getVehicleOrganizationName());
			// 车长名称
			vehicleInfoEntity.setVehicleLength(dto.getVehicleLengthValue());
			// 预计卸车时长
			vehicleInfoEntity
					.setSelfcubage((dto.getVehicleDeadLoad() == null ? "0"
							: dto.getVehicleDeadLoad())
							+ DepartureConstant.VEHICLE_UNIT_OF_WEIGHT
							+ (dto.getVehicleSelfVolume() == null ? "0" : dto
									.getVehicleSelfVolume())
							+ DepartureConstant.VEHICLE_UNIT_OF_VOLUME);
			// 是否有GPS
			vehicleInfoEntity.setHasGPS(String.valueOf(dto.isHasGps()));
		}
		// 车牌号
		vehicleInfoEntity.setVehicleNo(vehicleNo);
		return vehicleInfoEntity;
	}
	/**
	 * 通过车牌号找到司机的联系信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public RelationInfoEntity getRelationInfo(String vehicleNo) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		// 先查询公司车的信息
		try {
			// 调用接口根据车牌号查询司机信息
			relationInfoEntity = queryDriverByVehicleNoService
					.queryDriverInfoByVehicleNo(vehicleNo);
		} catch (BusinessException e) {
			// 未查到车牌号
			LOGGER.error("未查到车牌号");
		}
		return relationInfoEntity;
	}
	
	/**
	 * 通过车牌号找到司机的联系信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public RelationInfoEntity getRelationInfo(String vehicleNo, String id) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		// 先查询公司车的信息
		try {
			// 调用接口根据车牌号查询司机信息
			relationInfoEntity = queryDriverByVehicleNoService
					.queryDriverInfoByVehicleNo(vehicleNo, id);
		} catch (BusinessException e) {
			// 未查到车牌号
			LOGGER.error("未查到车牌号");
		}
		return relationInfoEntity;
	}
	
	/**
	 * 通过车牌号找到业务信息.
	 * 
	 * @param dto
	 *            the dto
	 * @return the business info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public BusinessInfoEntity getBusinessInfo(VehicleDetailDTO dto) {
		BusinessInfoEntity busi = vehicleInfoDao.queryBusiness(dto);
		// 获取任务车辆类型,线路，运行时长
		ArrivalInfoEntity arrivalInfoEntity = vehicleInfoDao
				.queryBusinessInfo(dto);
		// 线路
		busi.setLineNo(arrivalInfoEntity.getLineNo());
		// 车辆业务类型
		busi.setBusinessType(arrivalInfoEntity.getBusinessType());
		// 计算车辆运行时间
		int gapMin = DepartureHandle.getGapByHour(
				arrivalInfoEntity.getPlanDepartTime(),
				arrivalInfoEntity.getPlanArriveTime());
		if (gapMin > 0) {
			// 拼装车辆运行的时间
			busi.setRunningTime((gapMin - gapMin % DepartureConstant.SIXTEEN)
					/ DepartureConstant.SIXTEEN + "（小时）" + gapMin
					% DepartureConstant.SIXTEEN + "（分）");
		}
		return busi;
	}
	/**
	 * 通过车牌号交接单的列表.
	 * 
	 * @param dto
	 *            the dto
	 * @return the list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public List<TruckTaskBillEntity> queryTaskBill(
			VehicleDetailDTO dto) {
		return vehicleInfoDao.queryTaskBill(dto);
	}

	/**
	 * 在途跟踪优化需求
	 * 查询车辆任务下运单的总票数, 总总量, 总体积;
	 * 根据运单运输性质分组
	 * (只查询运输性质为“精准卡航”、“精准城运”货物<业务部门要求>) 感觉好奇葩
	 * @author 163580
	 * @date 2014-5-7 下午2:13:21
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IVehicleInfoService#queryTaskBillSummary(com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO)
	 */
	@Override
	public List<TruckTaskBillSummaryDto> queryTaskBillSummary(
			VehicleDetailDTO dto) {
		return vehicleInfoDao.queryTaskBillSummary(dto);
	}
	/**
	 * 通过条件找到申请放行信息信息.
	 * 
	 * @param dto
	 *            the dto
	 * @return the depart info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public DepartInfoEntity getDepartInfo(VehicleDetailDTO dto) {
		// 填充放行方式
		return vehicleInfoDao.queryDepart(dto);
	}
	/**
	 * 通过车牌号找到出发到达信息.
	 * 
	 * @param dto
	 *            the dto
	 * @return the arrival info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public ArrivalInfoEntity getArrivalInfo(VehicleDetailDTO dto) {
		return vehicleInfoDao.queryTaskDetail(dto);
	}
	/**
	 * 通过车牌号找到在途信息.
	 * 
	 * @param dto
	 *            the dto
	 * @return the on the way info
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public OnTheWayInfoEntity getOnTheWayInfo(VehicleDetailDTO dto) {
		return vehicleInfoDao.queryOnTheWay(dto);
	}
	/**
	 * 通过申请信息的id取消申请信息.
	 * 1.系统后台检索车牌号的状态是否为“待放行”；
	 * 2.系统检索不是“待放行”状态时，系统系统报错提示；
	 * 3.系统检索是 “待放行”状态，点击取消申请按钮时，系统提示：“
	 * 是否确认取消申请 ”？，让用户确认一下。
	 * 
	 * @param activeList
	 *            the active list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override @Transactional public void cancleDeparts(
			List<TruckDepartEntity> activeList) {
		// 存放待放行记录
		List<String> strWaitDepart = new ArrayList<String>();
		// 存放已放行记录
		List<String> strDeparted = new ArrayList<String>();
		for (TruckDepartEntity truck : activeList) {
			if (DepartureConstant.DEPART_STATUS_WAIT.equals(truck.getStatus())) {
				// 待放行
				strWaitDepart.add(truck.getId());
			}
			if (DepartureConstant.DEPART_STATUS_DEPARTURE.equals(truck
					.getStatus())) {
				// 已出发
				strDeparted.add(truck.getId());
			}
		}
		if (strWaitDepart.size() > 0) {
			// 更行状态
			cancleWaitDepart(strWaitDepart);
		}
		if (strDeparted.size() > 0) {
			// 更行状态
			cancleDeparteds(strDeparted);
		}
		if (strDeparted.size() > 0) {
			// 更行状态
			cancleWaitDepart(strDeparted);
		}
	}
	/**
	 * 取消待放行的记录.
	 * 
	 * @param strWaitDepart
	 *            the str wait depart
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 下午7:20:27
	 */
	@Override
	public void cancleWaitDepart(List<String> strWaitDepart) {
		departureDao.cancleDepart(strWaitDepart);
	}
	/**
	 * 取消已放行的记录.
	 * 
	 * @param strDeparted
	 *            the str departed
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 下午7:20:27
	 */
	private void cancleDeparteds(List<String> strDeparted) {
		for (String str : strDeparted) {
			// 取得该放行信息的任务明细ID，任务ID
			List<TruckTaskDetailEntity> list = departureDao
					.getTaskIdByDepartId(str);
			OperationDTO operationDTO = new OperationDTO();
			if (list != null && list.size() > 0) {
				// 任务ID
				operationDTO.setTruckTaskId(list.get(0).getTruckTaskId());
				// 出发部门
				operationDTO.setOrigOrgCode(list.get(0).getOrigOrgCode());
				arrivalService.cancleDepart(operationDTO);
			}
		}
	}
	/**
	 * 通过申请信息的id取消申请信息(组内接口).
	 * 
	 * @param ids
	 *            the ids
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@SuppressWarnings("rawtypes")
	@Override public void cancleDepart(List ids) {
		departureDao.activeDepart(ids);
	}
	/**
	 * 通过申请信息的id激活申请信息.
	 * 
	 * @param ids
	 *            the ids
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@SuppressWarnings("rawtypes")
	@Override public void activeDepart(List ids) {
		departureDao.activeDepart(ids);
	}
	/**
	 * 1.针对车牌号字段：手动录入的车牌信息，
	 * 后台匹配车辆基础资料的车牌列字段。
	 * 2、查询界面已单选的车牌号时，关联显示在车牌号字段。
	 * 3.查询界面对应的发车放行类型要与弹出框里的临时任务类型
	 * 、任务类型匹配一致，临时任务类型、任务类型为不可编辑状态。
	 * 4.假如查询界面选择的车牌号
	 * ，出发放行类型为空时，在弹框框里，临时任务类型
	 * 、任务类型为可编辑状态。
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-18 下午4:08:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#addManual(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity)
	 */
	@Transactional @Override public void addManual(
			TruckDepartEntity manualEntity) {
		// 检查是否已有相同车牌号的申请信息，并且状态是待放行和失效的
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		// 车牌号
		queryEntity.setVehicleNo(manualEntity.getVehicleNo());
		// 创建时间
		queryEntity.setCreateDate(DepartureHandle.getSevenDaysBefore());
		List<String> statuses = new ArrayList<String>();
		statuses.add(DepartureConstant.DEPART_STATUS_WAIT);
		statuses.add(DepartureConstant.DEPART_STATUS_Fail);
		queryEntity.setStatuses(statuses);
		List<TruckDepartEntity> truckList = queryDepart(queryEntity);
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
		// 放行状态置为已出发
		manualEntity.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
		if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(manualEntity
				.getDepartItems())
				|| DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(manualEntity
						.getDepartItems())
				|| DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(manualEntity
						.getDepartItems())) {
			// 长途、短途、接送货设置为任务车辆
			manualEntity
					.setDepartType(DepartureConstant.DEPART_TYPE_TASK_VEHICLE);
		} else {
			// 其他的都是非任务车辆
			manualEntity
					.setDepartType(DepartureConstant.DEPART_TYPE_NOT_TASK_VEHICLE);
		}
		// 第二个参数表示是否纸质放行
		addDepartTask(manualEntity, false);
	}
	/**
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-18 下午4:08:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#addTask(com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity)
	 */
	@Transactional @Override public void addTask(TruckDepartEntity taskEntity) {
		// 放行状态置为已出发
		taskEntity.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
		// 增加申请时不需要放行
		addDepartTask(taskEntity, true);
	}
	/**
	 * 打印纸质放行条（对已有申请信息进行操作）.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @param isManual
	 *            the is manual
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Transactional private void addDepartTask(TruckDepartEntity manualEntity,
			boolean isManual) {
		manualEntity.setId(UUIDUtils.getUUID());
		if (!isManual) {
			//增加申请时不需要放行
			manualEntity.setManualDepartUserCode(DepartureHandle
					.getCurrentUserCode());
			manualEntity.setManualDepartOrgCode(DepartureHandle
					.getCurrentOrgCode());
			manualEntity.setManualDepartTime(DepartureHandle.getCurrentDate());
		}
		manualEntity.setCreateTime(DepartureHandle.getCurrentDate());
		manualEntity.setCreateUserCode(DepartureHandle.getCurrentUserCode());
		manualEntity.setCreateOrgCode(DepartureHandle.getCurrentOrgCode());
		manualEntity.setCreateUserName(DepartureHandle.getCurrentUserName());
		manualEntity.setUpdateUserCode(DepartureHandle.getCurrentUserCode());
		manualEntity.setUpdateOrgCode(DepartureHandle.getCurrentOrgCode());
		manualEntity.setUpdateUserName(DepartureHandle.getCurrentUserName());
		manualEntity.setUpdateTime(DepartureHandle.getCurrentDate());
		// 申请方式
		manualEntity.setApplyType(DepartureConstant.DEPART_APPLY_TYPE_MANUAL);
		manualEntity.setApplyUserCode(DepartureHandle.getCurrentUserCode());
		// 申请部门设置成当前部门对应的外场的编码
		manualEntity.setApplyOrgCode(queryBigOrgCode(DepartureHandle
				.getCurrentOrgCode()));
		manualEntity.setApplyDepartTime(DepartureHandle.getCurrentDate());
		manualEntity.setActiveTime(new Date());
		//找到该车牌未出发的任务，取出任务车辆ID
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setVehicleNo(manualEntity.getVehicleNo());
		truckTaskDetailEntity.setOrigOrgCode(queryBigOrgCode(DepartureHandle
				.getCurrentOrgCode()));
		truckTaskDetailEntity.setStatus(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART);
		List<TruckTaskDetailEntity> taskDetailList = departureDao
				.queryTruckTaskDetail(truckTaskDetailEntity);
		if (!isManual
				&& DepartureConstant.DEPART_TYPE_TASK_VEHICLE
						.equals(manualEntity.getDepartType())) {
			if(taskDetailList==null||taskDetailList.size()<=0)
			{
				LOGGER.error("不存在该车辆的任务，不能放行");
				throw new TfrBusinessException("不存在该车辆的任务，不能放行", "");
			}
			else
			{
				manualEntity.setTruckTaskId(taskDetailList.get(0).getTruckTaskId());
			}
			// 临时任务车辆不用更新任务状态
			updateTaskStatusService.updateTaskStatus(manualEntity,
					DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
		} else if (DepartureConstant.DEPART_TYPE_TASK_VEHICLE
				.equals(manualEntity.getDepartType())) {
			// 判断是否存在该车牌号的该任务，通过车牌号，出发部门，状态来查询
			LOGGER.error("判断是否存在该车牌号的该任务，通过车牌号，出发部门，状态来查询");
			updateTaskStatusService.existTaskOrNot(manualEntity,
					DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
			if(taskDetailList==null||taskDetailList.size()<=0)
			{
				LOGGER.error("不存在该车辆的任务，不能放行");
				throw new TfrBusinessException("不存在该车辆的任务，不能放行", "");
			}
			else
			{
				manualEntity.setTruckTaskId(taskDetailList.get(0).getTruckTaskId());
			}
		}
		departureDao.addManual(manualEntity);
	}
	/**
	 * 打印纸质放行条（新增）.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Transactional @Override public void saveManual(
			TruckDepartEntity manualEntity) {
		// 放行状态置为已出发
		manualEntity.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
		manualEntity.setManualDepartUserCode(DepartureHandle
				.getCurrentUserCode());
		manualEntity
				.setManualDepartOrgCode(DepartureHandle.getCurrentOrgCode());
		manualEntity.setManualDepartTime(DepartureHandle.getCurrentDate());
		manualEntity.setUpdateUserCode(DepartureHandle.getCurrentUserCode());
		manualEntity.setUpdateOrgCode(DepartureHandle.getCurrentOrgCode());
		manualEntity.setUpdateUserName(DepartureHandle.getCurrentUserName());
		manualEntity.setUpdateTime(DepartureHandle.getCurrentDate());
		departureDao.saveManual(manualEntity);
		//派送放行后，给收货客户发短信
		this.addJobTodo4DeliverDepart(manualEntity);
		// 只更新任务车辆，且类型是长途跟短途的车辆
		if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(manualEntity
				.getDepartItems())
				|| DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(manualEntity
						.getDepartItems())
				|| DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(manualEntity
						.getDepartItems())) {
			updateTaskStatusService.updateTaskStatus(manualEntity,
					DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
		}
	}
	
	/**
	 * 全部放行.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @author foss-zyr
	 * @date 2015-06-24 下午2:09:50
	 */
	@Transactional @Override 
	public void saveManualAll(TruckDepartEntity manualEntity) {
		//查询全部要放行的记录
		List<TruckDepartEntity> truckDepartEntitys = departureDao.queryManualAll(manualEntity.getVehicleNo());
		//每个放行
		for(TruckDepartEntity truckDepartEntity :truckDepartEntitys) {
			truckDepartEntity.setManualDepartNotes(manualEntity.getManualDepartNotes());
			LOGGER.info("放行");
			saveManual(truckDepartEntity);
		}
	}
	
	/**
	 * 
	 * addJobTodo4DeliverDepart:(派送任务手工放行时需要给客户发送短息，此处做记录，job异步处理). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com    
	 * @since JDK 1.6
	 */
	private void addJobTodo4DeliverDepart(TruckDepartEntity manualEntity){
		//必须是派送任务手工放行
		if(!StringUtils.equals(manualEntity.getDepartItems(), DepartureConstant.DEPART_ITEM_TYPE_PKP)){
			return;
		}
		//放行时间
		Date departTime = manualEntity.getManualDepartTime();
		//获取配置参数中配置的时间段
		String timeRangeStr = DEFAULT_DELIVER_TIME_RANGE;
		ConfigurationParamsEntity paramEntity = null;
		try{
			paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_DELIVER_DEPART_TIME_RANGE, 
					manualEntity.getManualDepartOrgCode());
		}catch(Exception e){
			LOGGER.error(e);
		}
		if(paramEntity != null){
			//解析配置参数，并且检验合法性
			String confValue = paramEntity.getConfValue();
			String[] timeRangeArray = confValue.split("-");
			//如果时间范围不合法，则使用默认值
			if(timeRangeArray.length != 2){
				timeRangeStr = DEFAULT_DELIVER_TIME_RANGE;
			}
			try{
				for(String str : timeRangeArray){
					//如果时间范围的两端，长度不是4、或者不能转化为数字，则使用默认值
					Integer.parseInt(str);
					if(str.length() != LoadConstants.SONAR_NUMBER_4){
						throw new NumberFormatException();
					}
				}
				timeRangeStr = confValue;
			}catch(NumberFormatException ne){
				timeRangeStr = DEFAULT_DELIVER_TIME_RANGE;
			}
		}
		String[] timeRangeArray = timeRangeStr.split("-");
		if(this.judgeTimeRange(timeRangeArray, departTime)){
			tfrJobTodoService.addJobTodo(manualEntity.getId(), 
					BusinessSceneConstants.BUSINESS_SCENE_DELIVER_DEPART, 
					new String[]{BusinessGoalContants.BUSINESS_GOAL_SENDSMS_AFTER_DELIVERDEPART}, 
					departTime, 
					manualEntity.getUpdateUserCode());
		}
		
	}
	
	/**
	 * 
	 * judgeTimeRange:(判断放行时间时间是否在配置的派送放行时间段范围内). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param timeRangeArray
	 * @return  
	 * @since JDK 1.6
	 */
	private boolean judgeTimeRange(String[]timeRangeArray,Date departTime){
		//获取配置的开始时间、结束时间
		String sTimeStr = timeRangeArray[0],
				eTimeStr = timeRangeArray[1];
		//将开始、结束时间转化为数字
		int sTime = Integer.parseInt(sTimeStr),
				eTime = Integer.parseInt(eTimeStr);
		//截取放行时间的时、分，转成HHmm形式，24小时制
		DateFormat df = new SimpleDateFormat("HHmm");
		String nTimeStr = df.format(departTime);
		//讲当前时间的小时、分钟转化为数字
		int nTime = Integer.parseInt(nTimeStr);
		//此处比较大小，如果当前时间在配置时间范围内，则返回true
		if(nTime >= sTime && nTime < eTime){
			return true;
		}
		return false;
	}
	/**
	 * 对申请信息做操作前校验状态是否过期.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午5:02:55
	 */
	@Override public String validStatus(TruckDepartEntity manualEntity) {
		VehicleDetailDTO dto = new VehicleDetailDTO();
		dto.setDepartId(manualEntity.getId());
		// 先查一遍状态
		DepartInfoEntity entity = vehicleInfoDao.queryDepart(dto);
		if (!DepartureConstant.DEPART_STATUS_WAIT.equals(entity.getStatus())) {
			return DepartureConstant.DEPART_VALID_STATUS_ERROR;
		}
		return DepartureConstant.DEPART_VALID_STATUS_SUCCESS;
	}
	
	/**
	 * 查询要放行车牌号下是否有多个已确认状态的派单单号车辆未放行.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @return the string
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 */
	@Override 
	public String releaseReminder(TruckDepartEntity manualEntity) {
		StringBuffer deliverbills = new StringBuffer();
		List<String> list = departureDao.queryDeliverbill(manualEntity.getId());
		if (null != list && list.size() > 0) {
			for(int i=0;i<list.size();i++) {
				if(i == list.size()-1) {
					deliverbills.append(list.get(i));
				}else {
					deliverbills.append(list.get(i)).append(",");
				}
			}
		}
		return deliverbills.toString();
	}
	
	/**
	 * 激活、取消申请前判断状态.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the long
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	@Override public Long validBeforeCancleOrActiveDepart(
			QueryDepartEntity queryEntity) {
		return departureDao.validBeforeCancleOrActiveDepart(queryEntity);
	}
	/**
	 * 激活、取消申请前判断状态.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the long
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	@Override public Long validBeforeDepart(QueryDepartEntity queryEntity) {
		return departureDao.validBeforeDepart(queryEntity);
	}
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
	@Override public List<TruckDepartEntity> queryTemporaryAssignments(
			QueryDepartEntity queryEntity, int limit, int start) {
		return departureDao
				.queryTemporaryAssignments(queryEntity, limit, start);
	}
	/**
	 * 查询打印信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the vehicle print dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-18 下午4:07:10
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryVehicleInfoForPrint(java.lang.String)
	 */
	@Override public VehiclePrintDTO queryVehicleInfoForPrint(String vehicleNo) {
		VehicleAssociationDto dto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		VehiclePrintDTO vehiclePrintDTO = new VehiclePrintDTO();
		vehiclePrintDTO.setVehicleNo(vehicleNo);
		if (dto != null) {
			vehiclePrintDTO.setVehicleOrganizationName(dto
					.getVehicleOrganizationName());
			vehiclePrintDTO.setVehicleMotorcadeName(dto
					.getVehicleMotorcadeName());
		}
		vehiclePrintDTO.setVehicleCode(Constants
				.convertVehicleName2Code(vehicleNo));
		vehiclePrintDTO.setUserCode(DepartureHandle.getCurrentUserCode());
		// 时间转换
		vehiclePrintDTO.setPrintTime(DepartureHandle.getDateStr(new Date(),
				DepartureHandle.DATE_FORMAT_2));
		return vehiclePrintDTO;
	}
	/**
	 * 
	 * 根据当前部门的编码获取对应的外场的编码
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	@Override public String queryBigOrgCode(String currentOrgCode) {
		if (currentOrgCode == null) {
			LOGGER.error("申请部门不能为空");
			throw new BusinessException("申请部门不能为空");
		}
		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 车队类型
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		// 派送不营业部
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门编号
			return orgAdministrativeInfoEntity.getCode();
		} else {
			// 上级部门不存在
			return currentOrgCode;
		}
	}
	/**
	 * 
	 * 放行的时候判断是否是集配交接单，集配交接单不能放行
	 * 
	 * @param departId
	 *            放行id
	 * @param origOrgCode
	 *            出发部门，PDA使用，其他的传空值过来
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	public void isDistanceHandover(String taskDetailId, String origOrgCode,String destOrgCode) {
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		// 单据级别为有效
		queryEntity.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		// 交接单类型为集配
		queryEntity.setHandoverType(LoadConstants.HANDOVER_TYPE_LONG_DISTANCE);
		// 出发部门为本部门
		if (origOrgCode == null) {// 表示web端的调用
			queryEntity.setOrigOrgCode(queryBigOrgCode(DepartureHandle
					.getCurrentOrgCode()));
		} else {// pda端的调用
			queryEntity.setOrigOrgCode(origOrgCode);
		}
		queryEntity.setId(taskDetailId);
		queryEntity.setOrigOrgCode(origOrgCode);
		queryEntity.setDestOrgCode(destOrgCode);
		List<String> strlist = departureDao.isDistanceHandover(queryEntity);
		if (strlist.size() > 0) {
			LOGGER.error("集配交接单未配载不能出发");
			throw new TfrBusinessException("集配交接单"+strlist.toString()+"未配载不能出发", "");
		}
	}
	/**
	 * 
	 * 根据任务车辆明细判断是否是集配交接单，集配交接单不能放行
	 * 
	 * @param departId
	 *            放行id
	 * @param origOrgCode
	 *            出发部门，PDA使用，其他的传空值过来
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	@Override public void taskIsDistanceHandover(String taskDetailId, String origOrgCode,String destOrgCode) {
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		// 单据级别为有效
		queryEntity.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		// 交接单类型为集配
		queryEntity.setHandoverType(LoadConstants.HANDOVER_TYPE_LONG_DISTANCE);
		queryEntity.setId(taskDetailId);
		queryEntity.setOrigOrgCode(origOrgCode);
		queryEntity.setDestOrgCode(destOrgCode);
		List<String> strlist = departureDao.isDistanceHandover(queryEntity);
		if (strlist.size() > 0) {
			LOGGER.error("集配交接单未配载不能出发");
			throw new TfrBusinessException("集配交接单"+strlist.toString()+"未配载不能出发", "");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService#convertVehicleCode2Name(java.lang.String)
	 */

	@Override
	public RelationInfoEntity convertVehicleCode2Name(String vehicleNo) {
		String newVehicleNo=Constants.convertVehicleCode2Name(vehicleNo);
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		// 先查询公司车的信息
		try {
			// 调用接口根据车牌号查询司机信息
			relationInfoEntity = queryDriverByVehicleNoService
					.queryDriverInfoByVehicleNo(vehicleNo);
		} catch (BusinessException e) {
			// 未查到车牌号
			LOGGER.error("未查到车牌号");
		}
		if(relationInfoEntity==null)
		{
			relationInfoEntity = new RelationInfoEntity();
		}
		relationInfoEntity.setVehicleNo(newVehicleNo);
		return relationInfoEntity;
	}
	
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public String encodeFileName(String fileName) {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader(
					"USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 导出车辆出发到达明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public InputStream exportTruckDepartOrArriveByTaskNo(String ids) {
		InputStream excelStream = null;
		QueryDepartEntity queryEntity =new QueryDepartEntity();
		ids = ids.replaceAll(" ", "");
		queryEntity.setDepartIds(Arrays.asList(ids.split(",")));
		List<TruckDepartEntity>  list = queryDepart(queryEntity);

		List<List<String>> rowList = new ArrayList<List<String>>();
		for(TruckDepartEntity entity : list){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//出发放行类型 
			if("NO_TASK".equals(entity.getDepartType()))
			{
				columnList.add("临时任务车辆");
			}else if("TASK".equals(entity.getDepartType()))
			{
				columnList.add("任务车辆");
			}else 
			{
				columnList.add("");
			}
			//车牌号
			columnList.add(entity.getVehicleNo());
			
			//申请放时间
			columnList.add(DepartureHandle.getDateStr(entity.getApplyDepartTime()));
			
			//放行状态
			if("1".equals(entity.getStatus()))
			{
				columnList.add("已取消 ");
			}else if("2".equals(entity.getStatus()))
			{
				columnList.add("已失效");
			}else if("5".equals(entity.getStatus()))
			{
				columnList.add("待放行");
			}else if("9".equals(entity.getStatus()))
			{
				columnList.add("已出发");
			}else 
			{
				columnList.add("");
			}
			
			//申请部门
			columnList.add(entity.getApplyOrgName());
			
			//申请人
			columnList.add(entity.getApplyUserName());
			
			//司机姓名
			columnList.add(entity.getDriverName());
			
			//联系方式
			columnList.add(entity.getDriverPhone());
			
			//放行方式
			if("NO_TASK_KEEP".equals(entity.getDepartItems()))
			{
				columnList.add("保养");
			}else if("NO_TASK_YEAR_VERIFICATION".equals(entity.getDepartItems()))
			{
				columnList.add("年审/季审 ");
			}else if("NO_TASK_APPOINT".equals(entity.getDepartItems()))
			{
				columnList.add("临时放空出发/约车 ");
			}else if("NO_TASK_OIL".equals(entity.getDepartItems()))
			{
				columnList.add("加油 ");
			}else if("NO_TASK_REPAIR".equals(entity.getDepartItems()))
			{
				columnList.add("维修");
			}else if("NO_TASK_OTHERS".equals(entity.getDepartItems()))
			{
				columnList.add("其他任务出发");
			}else if("LONG_DISTANCE".equals(entity.getDepartItems()))
			{
				columnList.add("长途");
			}else if("SHORT_DISTANCE".equals(entity.getDepartItems()))
			{
				columnList.add("短途");
			}else if("DELIVER".equals(entity.getDepartItems()))
			{
				columnList.add("接送货");
			}else 
			{
				columnList.add("");
			}
			
			//放行时间
			if (entity.getGpsDepartTime() != null) {
				columnList.add(DepartureHandle.getDateStr(entity
						.getGpsDepartTime()));
			} else if (entity.getPdaDepartTime() != null) {
				columnList.add(DepartureHandle.getDateStr(entity
						.getPdaDepartTime()));
			} else if (entity.getManualDepartTime() != null) {
				columnList.add(DepartureHandle.getDateStr(entity
						.getManualDepartTime()));
			}else
			{
				columnList.add("");
			}
			
			//纸质放行人
			columnList.add(entity.getManualDepartUserName());
			
			//备注
			columnList.add(entity.getApplyNotes());
			rowList.add(columnList);
		}
		String[] rowHeads = { "出发放行类型", "车牌号", "申请放行时间", "放行状态", "申请部门", "申请人",
				"司机姓名", "联系方式", "放行方式", "放行时间", "纸质放行人", "备注"};// 定义表头

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("车辆纸质放行情况");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return excelStream;
	}
	
	/**
	 * 
	 * 提供接口给接送货，整车签收时，未到达的车辆统一做到达
	 * @author alfred
	 * @date 2013-9-11 上午9:25:31
	 * @param waybillNo 
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#carLoadArrive(java.lang.String)
	 */
	@Override
	public void carLoadArrive(String waybillNo) {
		//非空校验
		if (StringUtil.isBlank(waybillNo)) {
			LOGGER.error("传入运单号为空");
			throw new TfrBusinessException("传入运单号为空", "");
		}
		//根据运单查询在途车辆任务
		List<TruckTaskDetailEntity> taskDetailList = departureDao.querycarLoadTruckTaskDetailList(waybillNo);
		if(taskDetailList!=null&&taskDetailList.size()>0){
			for (TruckTaskDetailEntity truckTaskDetailEntity : taskDetailList){
				truckTaskDetailEntity.setActualArriveTime(new Date());
				truckTaskDetailEntity.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
				truckTaskDetailEntity.setManualArriveTime(new Date());
				String arriveId = truckTaskDetailEntity.getTruckArriveId();
				if (StringUtil.isBlank(arriveId)){ 
					arriveId= arrivalService.addTruckArrival(truckTaskDetailEntity.getVehicleNo(), truckTaskDetailEntity.getPlanArriveTime());
				}
				truckTaskDetailEntity.setTruckArriveId(arriveId);
				truckTaskDetailEntity.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
				departureDao.updateTaskByManual(truckTaskDetailEntity);
				
				//自动到达调用结算接口，Alfred 2014-06-13
				List<String> vehicleBillNoList= sharedDao.getVehicleAssembByDetail(truckTaskDetailEntity.getId());
			    if(vehicleBillNoList!=null&&vehicleBillNoList.size()>0)
			    {
			    	for(int i=0;i<vehicleBillNoList.size();i++)
			    	{
			    		String vehicleBillNo=vehicleBillNoList.get(i);
			    		if(StringUtil.isBlank(vehicleBillNo))
			    		{
			    			continue;
			    		}
			    		
			    		TruckArriveConfirmDto truckArriveConfirmDto=new TruckArriveConfirmDto();
			    		truckArriveConfirmDto.setHandleNo(vehicleBillNo);
			    		truckArriveConfirmDto.setConfirmTime(new Date());
			    		truckArriveConfirmDto.setConfirmType(SettlementDictionaryConstants.TRUCK_ARRIVE_CONFIRM);
			    		
						// 封装灰度实体，类型是配载单
						GrayParameterDto parDto = new GrayParameterDto();
						parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
						parDto.setSourceBillNos(new String[] { vehicleBillNo });
						VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

						if (CUBCGrayContants.SYSTEM_CODE_FOSS
								.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
							// 调结算接口
							try {

								truckArriveConfirmService.truckConfirm(truckArriveConfirmDto);
							} catch (SettlementException e) {
								LOGGER.error("调结算接口异常：" + e.getErrorCode());
								throw new TfrBusinessException("调结算接口异常：" + e.getErrorCode());

							}
						} else {
							String json = com.alibaba.fastjson.JSONObject.toJSONString(truckArriveConfirmDto);
							net.sf.json.JSONObject paramJson = net.sf.json.JSONObject.fromObject(json);
							paramJson.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
							paramJson.put("empName", FossUserContext.getCurrentInfo().getEmpName());
							paramJson.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
							paramJson.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());
							// 调CUBC接口
							CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = fossToCubcService
									.truckConfirm(paramJson);
							if (null != cubcTruckConfirmArrivalResponse) {
								if (0 == cubcTruckConfirmArrivalResponse.getResult()) {
									throw new TfrBusinessException(
											"调CUBC接口异常：" + cubcTruckConfirmArrivalResponse.getReason());
								}
							} else {
								throw new TfrBusinessException("调CUBC接口异常");
							}
						}

					}
			    	
			    }
				
				// 更新任务车辆的状态，先查看该条信息是不是最后一条未到达的任务
				ArrivalEntity arrivalEntity = new ArrivalEntity();
				arrivalEntity.setId(truckTaskDetailEntity.getId());
				arrivalEntity.setTruckTaskId(truckTaskDetailEntity.getTruckTaskId());
				arrivalEntity
						.setDetailStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
				if (arrivalDao.isLastTask(arrivalEntity) <= 0) {
					// 已经是最后一条了，需要更新任务车辆状态
					departureDao.updateTruckTask(truckTaskDetailEntity);
				}
				//插入jobtodo表中
				tfrJobTodoService.addJobTodo(truckTaskDetailEntity.getId(), 
						BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL, 
						new String[]{BusinessGoalContants.BUSINESS_GOAL_TPS,BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO}, 
						new Date(),
						DepartureHandle.getCurrentUserCode());
				//调用ECS系统接口开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					/** 新增同步给快递系统 车辆任务信息 通知任务 */
					Log.error("调用插入临时表，通过JOB推送数据给悟空");
					// 根据车辆任务明细ID查询出悟空交接单
					List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
							.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailEntity.getId());
					// 已交接单为单位插入到临时表
					for (WkHandOverBillEntity entity : wkHandoverbillList) {
						String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
						String currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
								+ DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
						tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
								NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL, truckTaskDetailEntity.getId(),
								BusinessSceneConstants.WK_HANDORVERBILL_HAVE_ARRIVE, currentUserInfo, paramJson));
					}
				}
				
				// job表增加一条数据
				TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
				actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
				actionDetail.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL);
				actionDetail.setTruckTaskDetailId(truckTaskDetailEntity.getId());
				actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
				actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
				actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
				actionDetail.setOperatorName(DepartureHandle.getCurrentUserName()+"(签收到达)");
				departureDao.addTruckActionDetail(actionDetail);
			}
		}
		
	}

	/**
	 * 根据任务车辆明细ID查询任务车辆明细
	 * @author 105795
	 * @date 2014年11月26日 16:55:25
	 * @param id
	 * @return
	 */
	public TruckTaskDetailEntity queryTruckTaskDetailById(String id)
	{
		if(StringUtils.isEmpty(id))
		{
			LOGGER.error("任务车辆明细id为空");
			throw new TfrBusinessException("任务车辆明细id为空");
		}
		return departureDao.queryTruckTaskDetailById(id);
	}
	
	/***
	 * 调用tps接口，同步到达信息
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-16
	 * @param truckTaskDetailId
	 * @param arriveTime
	 * @param operateType   出发还是到达
	 */
	public boolean synchDepartArriveInfoToTps(String truckTaskDetailId,Date departArriveTime,String operateType){
		boolean result = true;
		TruckTaskDetailEntity truckTaskDetailEntity = this.departureDao.queryTruckTaskDetailById(truckTaskDetailId);
		//如果是长途，并且是外请车时，进行处理，否则不处理
		if(StringUtil.equals(truckTaskDetailEntity.getBusinessType(), "LONG_DISTANCE")
				&& StringUtil.equals(truckTaskDetailEntity.getVehicleOwnerType(), "Leased")){
			//车牌号
			String vehicleNo = truckTaskDetailEntity.getVehicleNo();
			//查询所有tps审核的该车辆任务明细的出发到达信息
			List<DepartArriveToTpsDto> departArriveToTpsList = this.departureDao.synchDepartArriveInfoToTps(truckTaskDetailId,TransferConstants.TPS_INFODEPT_NAME);
			if(CollectionUtils.isNotEmpty(departArriveToTpsList) && departArriveToTpsList.size()>0){
				//声明请求参数
				RequestParameterEntity requestParameter = new RequestParameterEntity();
				List<StartArriveRequestEntity> requestEntityList = new ArrayList<StartArriveRequestEntity>();
				List<String> inviteNos = new ArrayList<String>();
				Map<String,StartArriveRequestEntity> map = new HashMap<String, StartArriveRequestEntity>();
				/**
				 * type  可以不用设置
				 * 格式为：requestParameterEntity:{'type':'xxxx',
				 * 'startArriveRequestEntity':[{'inviteNo':'xxxxx','operationType':'xxxxx','stowageBill':[{xxxx},{xxxx}]}]}
				 */
				//到达
				if(StringUtil.equals(operateType, "arrive")){
					for (DepartArriveToTpsDto departArriveToTpsDto : departArriveToTpsList) {
						
						if(!inviteNos.contains(departArriveToTpsDto.getFossAboutCode())){
							inviteNos.add(departArriveToTpsDto.getFossAboutCode());
							StartArriveRequestEntity requestEntity2  = new StartArriveRequestEntity();
							requestEntity2.setOperateType(operateType);
							requestEntity2.setFossAboutCode(departArriveToTpsDto.getFossAboutCode());
							map.put(departArriveToTpsDto.getFossAboutCode(), requestEntity2);
						}
						
						StartArriveRequestEntity requestEntity = map.get(departArriveToTpsDto.getFossAboutCode());
						StowageBillEntity stowageBill = new StowageBillEntity();
						//查询配载单的总重量和总体积
						DepartArriveToTpsDto dto = this.departureDao.queryWeightVolumeByVehicleassembleNo(departArriveToTpsDto.getVehicletips());
						stowageBill.setTotalWeight(dto.getTotalWeight());
						stowageBill.setTotalVolume(dto.getTotalVolume());
						//查询配载单的出发部门和到达部门  272681 2015/10/21
						stowageBill.setCarDepartment(dto.getCarDepartment());
						stowageBill.setArrivalDepartment(dto.getArrivalDepartment());
						stowageBill.setCarDepartmentCode(dto.getCarDepartmentCode());
						stowageBill.setArrivalDepartmentCode(dto.getArrivalDepartmentCode());
						stowageBill.setVehicleNo(departArriveToTpsDto.getVehicleNo());
						stowageBill.setStowageDate(departArriveToTpsDto.getStowageDate());
						stowageBill.setVehicletips(departArriveToTpsDto.getVehicletips());
						stowageBill.setTotalPrice(departArriveToTpsDto.getTotalPrice());
						stowageBill.setNominaLcearance(departArriveToTpsDto.getNominaLcearance());
						stowageBill.setNominalLoad(departArriveToTpsDto.getNominalLoad());
						stowageBill.setRemark(departArriveToTpsDto.getRemark());
						stowageBill.setAchieveDate(departArriveTime);
						if(requestEntity.getStowageBillList()==null){
							//构造配载信息实体集合
							List<StowageBillEntity> stowageBills = new ArrayList<StowageBillEntity>();
							stowageBills.add(stowageBill);
							requestEntity.setStowageBillList(stowageBills);
						}else{
							requestEntity.getStowageBillList().add(stowageBill);
						}
						
					}
					//放到list中
					for (Map.Entry<String, StartArriveRequestEntity> entity : map.entrySet()) {
						requestEntityList.add(entity.getValue());
					}				
					requestParameter.setRequestEntity(requestEntityList);
				}else{//出发
					for (DepartArriveToTpsDto departArriveToTpsDto : departArriveToTpsList) {
						if(!inviteNos.contains(departArriveToTpsDto.getFossAboutCode())){
							inviteNos.add(departArriveToTpsDto.getFossAboutCode());
						}
					}
					//循环约车编号
					for (String inviteNo : inviteNos) {
						StartArriveRequestEntity requestEntity = new StartArriveRequestEntity();
						requestEntity.setOperateType(operateType);//tps 的出发是 start
						requestEntity.setFossAboutCode(inviteNo);
						//构造配载信息实体集合
						List<StowageBillEntity> stowageBills = new ArrayList<StowageBillEntity>();
						StowageBillEntity stowageBill = new StowageBillEntity();
						stowageBill.setSetoutDate(departArriveTime);
						stowageBill.setVehicleNo(vehicleNo);
						//添加实体
						stowageBills.add(stowageBill);
						requestEntity.setStowageBillList(stowageBills);
						requestEntityList.add(requestEntity);
					}
					requestParameter.setRequestEntity(requestEntityList);
				}
				try {
					//手工、gps、pda 出发到达 调用tps 的接口  同步信息
					ResponseParameterEntity resultEntity = fossToTPSService.updateDepartArriveinfo(requestParameter);
					if(resultEntity==null){
						result=false;
					}else{
						result = resultEntity.isResultFlag();
					}
				} catch (Exception e) {
					result = false;
					LOGGER.error("synchArriveInfoToTps - 调用tps 接口 - 同步车辆出发到达信息失败！错误信息："+e.toString());
					e.printStackTrace();
				}
			}else{
				LOGGER.error("该车辆不是TPS约车受理的车辆不 调用tps接口 同步车辆出发到达信息！");
			}
			
		}else{
			LOGGER.error("该任务："+truckTaskDetailId+"非长途外请车任务！同步到TPS失败！");
		}
		
		return result;
		
	}
	
	
	
	/**
	 * *********************************
	 * ******************** ******.
	 */
	/****** 放行底层实现 *******/
	private IDepartureDao departureDao;
	/** ****车辆信息******. */
	private IVehicleInfoDao vehicleInfoDao;
	/** ****车辆服务******. */
	private IVehicleService vehicleService;
	/** ****到达底层实现******. */
	private IArrivalService arrivalService;
	/** ****根据车牌号车讯车辆信息******. */
	private IQueryDriverByVehicleNoService queryDriverByVehicleNoService;
	/** ****放行底层实现******. */
	private IUpdateTaskStatusService updateTaskStatusService;
	/** ****部门实体******. */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**** 到达底层接口***/
	private IArrivalDao arrivalDao;
	/**调用tps接口，同步发车到达信息*/
	private IFOSSToTPSService fossToTPSService;
	
	private ITruckArriveConfirmService truckArriveConfirmService;
	
	/** The shared dao. */
	private ISharedDao sharedDao;
	
	/**
	 * 设置 ******************************* ************.
	 * 
	 * @param arrivalDao
	 *            the new *************** ******** ********************
	 */
	public void setArrivalDao(IArrivalDao arrivalDao) {
		this.arrivalDao = arrivalDao;
	}
	
	
	public void setFossToTPSService(IFOSSToTPSService fossToTPSService) {
		this.fossToTPSService = fossToTPSService;
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
	 * Sets the update task status
	 * service.
	 * 
	 * @param updateTaskStatusService
	 *            the new update task
	 *            status service
	 */
	public void setUpdateTaskStatusService(
			IUpdateTaskStatusService updateTaskStatusService) {
		this.updateTaskStatusService = updateTaskStatusService;
	}
	/**
	 * Sets the vehicle service.
	 * 
	 * @param vehicleService
	 *            the new vehicle
	 *            service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	
	/**
	 * Sets the vehicle info dao.
	 * 
	 * @param vehicleInfoDao
	 *            the new vehicle info
	 *            dao
	 */
	public void setVehicleInfoDao(IVehicleInfoDao vehicleInfoDao) {
		this.vehicleInfoDao = vehicleInfoDao;
	}
	/**
	 * Sets the arrival service.
	 * 
	 * @param arrivalService
	 *            the new arrival
	 *            service
	 */
	public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}
	/**
	 * Sets the query driver by vehicle
	 * no service.
	 * 
	 * @param queryDriverByVehicleNoService
	 *            the new query driver
	 *            by vehicle no service
	 */
	public void setQueryDriverByVehicleNoService(
			IQueryDriverByVehicleNoService queryDriverByVehicleNoService) {
		this.queryDriverByVehicleNoService = queryDriverByVehicleNoService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setTruckArriveConfirmService(
			ITruckArriveConfirmService truckArriveConfirmService) {
		this.truckArriveConfirmService = truckArriveConfirmService;
	}
	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}
	
	
	
}