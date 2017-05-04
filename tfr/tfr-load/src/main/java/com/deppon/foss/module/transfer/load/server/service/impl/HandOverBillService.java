/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.shared.define.BillNumContants;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.service.IQueryDriverByVehicleNoService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HoldingStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.exception.HandOverBillException;
import com.deppon.foss.module.transfer.load.api.shared.exception.HandOverBillExceptionCode;
import com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.UpdateStockPreHandOverStateDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @className: HandOverBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单模块service
 * @date: 2012-10-11 上午11:04:24
 * 
 */
public class HandOverBillService implements IHandOverBillService {
	private IBillNumService billNumService;
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}

	@Resource
	public IProductService productService4Dubbo;
	
	/**
	 *营业部service 
	 */
	public ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 营业部映射service
	 */
	public IDeptTransferMappingService deptTransferMappingService;
	
	/**
	 * 产品定义
	 * @param productService the productService to set
	 */
/*	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/
	
	/**
	 * 营业部set
	 * @param saleDepartmentService
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * 营业部映射set
	 * @param deptTransferMappingService
	 */
	public void setDeptTransferMappingService(IDeptTransferMappingService deptTransferMappingService) {
		this.deptTransferMappingService = deptTransferMappingService;
	}

	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * 包状态(未生成包交接单)
	 */
	private static final String PACKAGESTATE_FINISHED = "FINISHED";
	
	/**
	 * 本模块dao
	 */
	private IHandOverBillDao handOverBillDao;
	
	
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * 用于获取小部门所属的大部门
	 */
	private ILoadService loadService;
	
	/**
	 * 用于作废包交接单，更新包信息状态
	 */
	private IExpressPackageService expressPackageService;
	
	/**
	 * 只调用了该service的一个方法queryProductTypeByHandoverNo，根据交接单号查询该交接单的第一条运单的运输性质
	 */
	
	/**查询虚拟车相关信息**/
	private IExpressVehiclesService  expressVehiclesService;
	/**
	 * 综合线路service，根据外场编码获取其辐射的营业部
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	private IEmployeeService employeeService;
	
	/**
	 * 重泡比 zwd
	 */
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	/**
	 * 重泡比 zwd
	 */
	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}

	public void setExpressPackageService(
			IExpressPackageService expressPackageService) {
		this.expressPackageService = expressPackageService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	
	
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	/**
	 * 综合部门service，传入“到达部门code”，判断该部门是否为营业部、外场
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 库存service，用来出库、更改流水号库存预配状态
	 */
	private IStockService stockService;
	
	/**
	 * 任务车辆service，用于修改、作废交接单时调用接口更新任务车辆明细信息
	 */
	private ITruckTaskService truckTaskService;
	
	/**
	 * 走货路径service，修改、作废正式交接单时调用接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 传入到达部门code，判断该部门是否支持自动分拣
	 */
	private IOutfieldService outfieldService;
	
	/**
	 * 发车计划service
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	
	/**
	 * 出发service，根据车牌号获取司机信息
	 */
	private IQueryDriverByVehicleNoService queryDriverByVehicleNoService;
	
	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 约车服务
	 */
	
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 偏线代理服务，用于获取代理公司下的网点
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	
	/**
	 * 车辆服务，用户查询车辆所有权
	 */
	private IVehicleService vehicleService;
	
	/**
	 * 自有司机service
	 */
	private IOwnDriverService ownDriverService;
	
	/**
	 * 外请司机service
	 */
	private ILeasedDriverService leasedDriverService;
	
	/**
	 * 落地配公司
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * 落地配公司
	 */
	private ILdpAgencyCompanyService ldpAgencyCompanyService;
	
	public IPDACommonService pdaCommonService;

	/**
	 * Sets the pda common service.
	 */
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	
	/**结算接口查询是否已付款**/
	private IBillPayableService billPayableService;
    
	public void setLdpAgencyCompanyService(
			ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

	private static final String ZERO = "0";
	
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	
/*	*//**
	 * 应用监控服务
	 *//*
	private IBusinessMonitorService businessMonitorService;

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}*/

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setQueryDriverByVehicleNoService(
			IQueryDriverByVehicleNoService queryDriverByVehicleNoService) {
		this.queryDriverByVehicleNoService = queryDriverByVehicleNoService;
	}

	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setHandOverBillDao(IHandOverBillDao handOverBillDao) {
		this.handOverBillDao = handOverBillDao;
	}


	/**
	 * @author shiwei
	 * @return List<WaybillStockEntity>
	 * @param queyWaybillForHandOverBillDto
	 * @param limit
	 * @param start
	 * @exception 无
	 * @date 2012-10-11
	 * @description 查询交接运单，获取部门库存运单service
	 */
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,
			int start) {
		/*
		 * 排序规则：必走货在前、非必走货在后，按运输性质、在库时长排序
		 * 必走货定义：截止当天16：00未在规定出发时间前出发的卡货（城运）
		 * 						及晚发24小时以上的普货。
		 * 规定出发时间：根据货物的开单时间、线路运行时刻表计算出
		 * 						每个经受运作的规定发车时间（有规定多班卡车的线路规定出发时间以可以兑现的最晚出发时间为准).
		 * 即规定出发时间 <= 当前时间且规定出发时间 <= 当天16:00的卡货城运
		 * 及规定出发时间-当前时间 >= 24小时的普货。
		 */
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		//获取下一部门code的list
		List<String> arriveDeptList = queyWaybillForHandOverBillDto.getArriveDeptList();
		//如果为外发交接单，则需要将前台传入的公司编码转换为网点编码
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, queyWaybillForHandOverBillDto.getHandOverType())){
			arriveDeptList = this.queryAgencySiteByComCode(arriveDeptList.get(0));
		}
		//如果为落地配交接单，则需要将前台传入的公司编码转换为网点编码
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, queyWaybillForHandOverBillDto.getHandOverType())){
			arriveDeptList = this.queryLdpSiteByComCode(arriveDeptList.get(0));
		}
		//添加整车下一到达部门常量
		arriveDeptList.add(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
		arriveDeptList.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
		queyWaybillForHandOverBillDto.setArriveDeptList(arriveDeptList);
		//设置必走货时间
		Calendar cal = Calendar.getInstance();
		//每日下午四点
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), LoadConstants.SONAR_NUMBER_16, 0, 0);
		Date priorityTime = cal.getTime();
		queyWaybillForHandOverBillDto.setPriorityTime(priorityTime);
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queyWaybillForHandOverBillDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//返回查询结果
		return handOverBillDao.queryWaybillStockList(queyWaybillForHandOverBillDto,limit, start);
	}
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取库存运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-3-25 下午6:39:48
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,Object> queryWaybillStockByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto){
		//如果是落地配交接单，如果没有补码则不能添加
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, queyWaybillForHandOverBillDto.getHandOverType())){
			int isComplement = handOverBillDao.queryWaybillComplement(queyWaybillForHandOverBillDto.getWaybillNo());
			if(isComplement<1){
				throw new TfrBusinessException("该运单未补码，不允许装车！请核实处理！");
			}
			
		}
		//获取配置参数，决定此处查询是否忽略走货路径
		boolean ignoreNextOrg = false;
		try{
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_HANDOVER_IGNORE_TRANSPORT_PATH_PC, 
				FossConstants.ROOT_ORG_CODE);
			if(StringUtils.equals(paramEntity.getConfValue(), FossConstants.YES)){
				ignoreNextOrg = true;
			}
		}catch(BusinessException e){
			LOGGER.error("获取pc交接是否忽略走货路径配置参数失败！");
		}
		
		//定义返回值
		Map map = new HashMap<String,Object>();
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		BigDecimal expressConvertParameter=null;
		try{
			 expressConvertParameter=this.queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积配置参数失败！");
		}
		if(expressConvertParameter==null){
			expressConvertParameter=new BigDecimal(1);
		}else{
			expressConvertParameter=expressConvertParameter.divide(new BigDecimal(LoadConstants.SONAR_NUMBER_1000));
		}
		/*
		 * 获取下一部门code的list
		 */
		List<String> arriveDeptList = new ArrayList<String>();
		//如果不忽略走货路径，则设置下一部门
		if(!ignoreNextOrg){
			arriveDeptList = queyWaybillForHandOverBillDto.getArriveDeptList();
			//如果为外发交接单，则需要将前台传入的公司编码转换为网点编码
			if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, queyWaybillForHandOverBillDto.getHandOverType())){
				arriveDeptList = this.queryAgencySiteByComCode(arriveDeptList.get(0));
			}
			//添加整车下一到达部门常量
			arriveDeptList.add(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			arriveDeptList.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			queyWaybillForHandOverBillDto.setArriveDeptList(arriveDeptList);
		}
		
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queyWaybillForHandOverBillDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//获取运单库存
		List<HandOverBillDetailEntity> waybillStockList = new ArrayList<HandOverBillDetailEntity>();
		if(!ignoreNextOrg){
			waybillStockList = handOverBillDao.queryWaybillStockByWaybillNo(queyWaybillForHandOverBillDto);
		}else{
			waybillStockList = handOverBillDao.queryWaybillStockByWaybillNoWithoutNextOrg(queyWaybillForHandOverBillDto);
		}
		
		//如果是出发部门是营业部则需查非本部门库存表   332219
		SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(currentDeptCode);
		if(departDept != null && CollectionUtils.isEmpty(waybillStockList)){
			//根据出发部门查询到对应的外场
			List<String> centerCodeList = lineService.queryTransferCodeListBySourceCode(currentDeptCode);
			//查询非本部门库存
			if(!ignoreNextOrg){
				//判断不为空
				if(centerCodeList != null && centerCodeList.size()>0){
					//添加
					arriveDeptList.add(centerCodeList.get(0));
				}
				waybillStockList = handOverBillDao.queryWaybillStockSaleByWaybillNo(queyWaybillForHandOverBillDto);
			}else{
				waybillStockList = handOverBillDao.queryWaybillStockByWaybillSaleNoWithoutNextOrg(queyWaybillForHandOverBillDto);
			}
		}
		//是否为修改交接单界面快速添加
		String isModify = queyWaybillForHandOverBillDto.getIsModifyHandOverBill();
		if(CollectionUtils.isEmpty(waybillStockList) && !StringUtils.equals(isModify, FossConstants.YES)){
			throw new TfrBusinessException("该运单在本部门没有库存或与交接单“到达部门”不匹配！");
		}else if(CollectionUtils.isEmpty(waybillStockList) && StringUtils.equals(isModify, FossConstants.YES)){
			return map;
		}
		HandOverBillDetailEntity waybillStock = waybillStockList.get(0);
		
		//根据运单号获取流水号
		QuerySerialNoListForWaybillConditionDto querySerialNoDto = new QuerySerialNoListForWaybillConditionDto();
		//运单号
		querySerialNoDto.setWaybillNo(waybillStock.getWaybillNo());
		//下一部门
		if(!ignoreNextOrg){
			querySerialNoDto.setNextDeptCodeList(arriveDeptList);
		}
		//是否合车
		querySerialNoDto.setIsJoinCar(waybillStock.getIsJoinCar());
		//是否通过运单号快速添加
		querySerialNoDto.setIsQuickAdd(FossConstants.YES);
		List<SerialNoStockEntity> serialNoList = this.querySerialNoStockList(querySerialNoDto);
		//如果本部门库存查询不到则查非本部门库存  332219
		if(CollectionUtils.isEmpty(serialNoList)){
			serialNoList = this.querySaleSerialNoStockList(querySerialNoDto);
		}
		BigDecimal weightTotal =  BigDecimal.ZERO;
		BigDecimal volumnTotal = BigDecimal.ZERO;
		/**
		* 获取所有的快递3级产品code
		* zx
		* 2015-09-06
		* */
		List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
		for(SerialNoStockEntity entity:serialNoList){
			//运输性质
			String productCode=waybillStock.getTransPropertyCode();
		
		//如果是快递没有上计泡机 则 计算重泡比
		if(CollectionUtils.isNotEmpty(productCodeList)
						&&productCodeList.contains(productCode)
						&& StringUtils.equals(entity.getBeScan(), FossConstants.NO)
			){
//			if((StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_PACKAGE) 
//					||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_RCP)
//					||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_EPEP))
//					&& StringUtils.equals(FossConstants.NO, entity.getBeScan())){
				BigDecimal weight = entity.getWeight()==null?new BigDecimal(0):entity.getWeight();
				BigDecimal volumn = expressConvertParameter.multiply(entity.getWeight()==null?new BigDecimal(0):entity.getWeight()).divide(new BigDecimal(1), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
				//重新设置流水号明细的重量体积
				entity.setWeight(weight);
				entity.setVolumn(volumn);
				//累加改运单的重量体积
				weightTotal=weightTotal.add(weight);
				volumnTotal=volumnTotal.add(volumn);
				
				
			}else{
				weightTotal=weightTotal.add(entity.getWeight());
				volumnTotal=volumnTotal.add(entity.getVolumn());
			}
			
		}
		//设置转换后的重量体积
		waybillStock.setWeightAc(weightTotal);
		waybillStock.setCubageAc(volumnTotal);
		
		map.put("waybillStock",waybillStock);
		if(CollectionUtils.isEmpty(serialNoList) && !StringUtils.equals(isModify, FossConstants.YES)){
			throw new TfrBusinessException("该运单下的所有流水号已被预配！");
		}else if(CollectionUtils.isEmpty(serialNoList) && StringUtils.equals(isModify, FossConstants.YES)){
			return map;
		}
		map.put("serialNoList",serialNoList);
		return map;
	}
	
	/**
	 * @param queryWaybillForHandOverBillDto
	 * @return 达部门、配载部门所辐射的营业部的list
	 * @date 2013-10-17 下午3:58:46
	 */
	@Override
	public List<String> getArrivedDeptListByDto(
			QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto) {

		// 接收到达部门、配载部门所辐射的营业部的list
		List<String> arrivedDeptList = new ArrayList<String>();
		// 获取传入的到达部门、配载部门list
		List<String> receivedDeptList = queryWaybillForHandOverBillDto
				.getArriveDeptList();
		// 如果为外发交接单，则需要将前台传入的公司编码转换为网点编码
		if (StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE,
				queryWaybillForHandOverBillDto.getHandOverType())) {
			arrivedDeptList = this.queryAgencySiteByComCode(receivedDeptList
					.get(0));
			// BUG-51898 偏线代理公司没有网点，做交接添加运单报错 这个list不能为空，为空的话设置一个常量
			// 防止sql报错
			if (CollectionUtils.isEmpty(arrivedDeptList)) {
				arrivedDeptList.add("NO_ARRIVEDDEPT_CODE");
			}

		} else {
			// 循环传入的到达部门、配载部门list
			for (int i = 0; i < receivedDeptList.size(); i++) {
				String code = receivedDeptList.get(i);
				// 获取该部门对象
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(code);
				arrivedDeptList.add(code);
				// 如果为外场，则获取其辐射的营业部code
				if (null != orgEntity
						&& StringUtils.equals(FossConstants.YES,
								orgEntity.getTransferCenter())) {
					List<String> list = lineService.queryArriveCodeListByTransferCode(code);
					List<String> explist = expresslineService.queryArriveCodeListByTransferCode(code);
					arrivedDeptList.addAll(list);
					arrivedDeptList.addAll(explist);
				}
			}
		}

		return arrivedDeptList;
	}
	
	
	/**
	 * 查询交接运单，获取部门在途运单
	 * @author 045923-foss-shiwei
	 * @param queyWaybillForHandOverBillDto
	 * @param limit
	 * @param start
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @date 2012-11-6 下午2:57:41
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryEnRouteWaybillList(int, int)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryEnRouteWaybillList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start) {
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		BigDecimal expressConvertParameter=null;
		try{
			 expressConvertParameter=this.queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积配置参数失败！");
		}
		if(expressConvertParameter==null){
			expressConvertParameter=new BigDecimal(1);
		}else{
			expressConvertParameter=expressConvertParameter.divide(new BigDecimal(LoadConstants.VOLUME_SIZE));
		}
		/*//接收到达部门、配载部门所辐射的营业部的list
		List<String> arrivedDeptList = new ArrayList<String>();
		//获取传入的到达部门、配载部门list
		List<String> receivedDeptList = queyWaybillForHandOverBillDto.getArriveDeptList();
		//如果为外发交接单，则需要将前台传入的公司编码转换为网点编码
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, queyWaybillForHandOverBillDto.getHandOverType())){
			arrivedDeptList = this.queryAgencySiteByComCode(receivedDeptList.get(0));
			//BUG-51898 偏线代理公司没有网点，做交接添加运单报错 这个list不能为空，为空的话设置一个常量
			//防止sql报错
			if(CollectionUtils.isEmpty(arrivedDeptList)){
				arrivedDeptList.add("NO_ARRIVEDDEPT_CODE");
			}
			
			
		}else{
			//循环传入的到达部门、配载部门list
			for(int i = 0;i < receivedDeptList.size();i++){
				String code = receivedDeptList.get(i);
				//获取该部门对象
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
				arrivedDeptList.add(code);
				//如果为外场，则获取其辐射的营业部code
				if(null != orgEntity && StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
					List<String> list = lineService.queryArriveCodeListByTransferCode(code);
					arrivedDeptList.addAll(list);
				}
			}
		}
		queyWaybillForHandOverBillDto.setArriveDeptList(arrivedDeptList);*/
		
		//设置运单下的流水号
		List<HandOverBillDetailEntity> waybillList = handOverBillDao.queryEnRouteWaybillList(queyWaybillForHandOverBillDto,limit, start);
		for(HandOverBillDetailEntity waybill : waybillList){
			List<HandOverBillSerialNoDetailEntity> serialNoList =this.queryEnRouteSerialNoListByNos(waybill.getWaybillNo(), waybill.getHandOverBillNo());
			BigDecimal weightTotal =  BigDecimal.ZERO;
			BigDecimal volumnTotal = BigDecimal.ZERO;
			//运输性质
			String productCode = waybill.getTransPropertyCode();
//			for(HandOverBillSerialNoDetailEntity entity:serialNoList){,,
//				//快递没有上计泡机的 计算重泡比
//				if((StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_PACKAGE) 
//						||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_RCP)
//						||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_EPEP))
//						&&StringUtils.equals(FossConstants.NO, entity.getBeScan())){
				/**
				* 获取所有的快递3级产品code
				* zx
				* 2015-09-06
				* */
				List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
				//如果是快递没有上计泡机 则 计算重泡比
			for(HandOverBillSerialNoDetailEntity entity:serialNoList){
						if(CollectionUtils.isNotEmpty(productCodeList)
								&&productCodeList.contains(productCode)
								&& StringUtils.equals(entity.getBeScan(), FossConstants.NO)
								){
					BigDecimal weight = entity.getWeight()==null?new BigDecimal(0):entity.getWeight();
					BigDecimal volumn = expressConvertParameter.multiply(entity.getWeight()==null?new BigDecimal(0):entity.getWeight()).divide(new BigDecimal(1), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					//重新设置流水号明细的重量体积
					entity.setWeight(weight);
					entity.setVolumn(volumn);
					//累加改运单的重量体积
					weightTotal=weightTotal.add(weight);
					volumnTotal=volumnTotal.add(volumn);
					
					
				}else{
					weightTotal=weightTotal.add(entity.getWeight());
					volumnTotal=volumnTotal.add(entity.getVolumn());
				}
				
			}
			//设置转换后的重量体积
			waybill.setWeightAc(weightTotal);
			waybill.setCubageAc(volumnTotal);
			waybill.setSerialNoHandOveredList(serialNoList);
		}
		//返回查询结果
		return waybillList;
	}

	/**
	 * 传入运单号、库存部门，获取流水号list，用于查询交接运单界面
	 * @author 045923-foss-shiwei
	 * @param querySerialNoListForWaybillConditionDto
	 * @return List<SerialNoStockEntity>
	 * @exception 无
	 * @date 2012-11-3 上午9:30:04
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#querySerialNoStockList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {	
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		//设置当前部门code
		querySerialNoListForWaybillConditionDto.setCurrentDeptCode(currentDeptCode);
		//如果不是通过快速添加功能获取流水号
		if(!StringUtils.equals(querySerialNoListForWaybillConditionDto.getIsQuickAdd(), FossConstants.YES)){
			List<String> nextDeptCodeList = new ArrayList<String>();
			nextDeptCodeList.add(querySerialNoListForWaybillConditionDto.getNextDeptCode());
			//下一到达部门list
			nextDeptCodeList.add(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			nextDeptCodeList.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			querySerialNoListForWaybillConditionDto.setNextDeptCodeList(nextDeptCodeList);
		}
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		querySerialNoListForWaybillConditionDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//运单是否合车
		String isJoinCar = querySerialNoListForWaybillConditionDto.getIsJoinCar();
		if(StringUtils.equals(isJoinCar, FossConstants.YES)){
			return handOverBillDao.queryJoinCarSerialNoStockList(querySerialNoListForWaybillConditionDto);
		}else{
			//返回查询结果
			return handOverBillDao.querySerialNoStockList(querySerialNoListForWaybillConditionDto);
		}
	}

	/**
	 * 获取运单库存的总记录数
	 * @author 045923-foss-shiwei
	 * @param queyWaybillForHandOverBillDto
	 * @return Long
	 * @exception 无
	 * @date 2012-11-3 上午9:31:09
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getWaybillStockCount()
	 */
	@Override
	public Long getWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		//设置当前部门
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queyWaybillForHandOverBillDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//货物类型(A,B)货
		if(StringUtils.isNotEmpty(queyWaybillForHandOverBillDto.getGoodsType())) {
			if(StringUtils.equals(queyWaybillForHandOverBillDto.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_A_TYPE)) {
				//A_TYPE to A
				queyWaybillForHandOverBillDto.setGoodsType(WaybillConstants.GOODS_TYPE_A);
			} else if (StringUtils.equals(queyWaybillForHandOverBillDto.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_B_TYPE)) {
				//B_TYPE to B
				queyWaybillForHandOverBillDto.setGoodsType(WaybillConstants.GOODS_TYPE_B);
			}
		}
		//如果运单号的查询条件不为空
		//则忽略调货物类型的查询条件
		if(StringUtils.isNotEmpty(queyWaybillForHandOverBillDto.getWaybillNo())) {
			queyWaybillForHandOverBillDto.setGoodsType("");
		}
		//查询本部门结果
		long stockCount = handOverBillDao.getWaybillStockCount(queyWaybillForHandOverBillDto);
		//查询非本部门结果
		long saleStockCount = handOverBillDao.getSaleWaybillStockCount(queyWaybillForHandOverBillDto);
		//返回结果
		return stockCount+saleStockCount;
	}
	
	/**
	 * 获取在途运单的总条数
	 * @author 045923-foss-shiwei
	 * @param queyWaybillForHandOverBillDto
	 * @return Long
	 * @exception 无
	 * @date 2012-11-6 下午3:07:49
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getEnRouteWaybillCount()
	 */
	@Override
	public Long getEnRouteWaybillCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		/*//接收到达部门、配载部门所辐射的营业部的list
		List<String> arrivedDeptList = new ArrayList<String>();
的到达部门、配载部门list
		List<String> receivedDeptList = queyWaybillForHandOverBillDto.getArriveDeptList();
		//循环传入的到达部门、配载部门list
		for(int i = 0;i < receivedDeptList.size();i++){
			String code = receivedDeptList.get(i);
			//获取该部门对象
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
			arrivedDeptList.add(code);
			//如果为外场，则获取其辐射的营业部code
			if(null != orgEntity && StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
				List<String> list = lineService.queryArriveCodeListByTransferCode(code);
				arrivedDeptList.addAll(list);
			}
		}
		queyWaybillForHandOverBillDto.setArriveDeptList(arrivedDeptList);*/
		//返回查询结果
		return handOverBillDao.getEnRouteWaybillCount(queyWaybillForHandOverBillDto);
	}
	
	/**
	 * 查询交接运单，查询在途运单后，双击获取运单下流水号列表时调用
	 * @author 045923-foss-shiwei
	 * @param wayBillNo
	 * @param handOverBillNo
	 * @return List<HandOverBillSerialNoDetailEntity>
	 * @exception 无
	 * @date 2012-11-6 下午5:16:53
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryEnRouteSerialNoListByNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HandOverBillSerialNoDetailEntity> queryEnRouteSerialNoListByNos(
			String wayBillNo, String handOverBillNo) {
		//返回查询结果
		return handOverBillDao.queryEnRouteSerialNoListByNos(wayBillNo, handOverBillNo);
	}
	
	/**
	 * @param detailEntities 交接单明细集合
	 * @return 交接单明细类型；表示此交接单明细中的货是否全部快递货或零担货
	 * @date 2014-1-3
	 */
	private String getHandOverBillDetailsType(List<HandOverBillDetailEntity> detailEntities) {
		if (CollectionUtils.isEmpty(detailEntities)) {
			throw new TfrBusinessException("请添加运单！");
		}

		Set<String> set = new HashSet<String>();

		// 交接单明细类型，分为快递EXPRESS和零担LTL
		String handOverBillDetailsType = LoadConstants.HANDOVERBILL_DETAILS_TYPE_LTL;
		
		for (HandOverBillDetailEntity detailEntity : detailEntities) {
			boolean isKuaidi=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(detailEntity.getTransPropertyCode())
					||LoadConstants.PRODUCT_CODE_RCP.equals(detailEntity.getTransPropertyCode())
					||LoadConstants.PRODUCT_CODE_EPEP.equals(detailEntity.getTransPropertyCode())
					||LoadConstants.PRODUCT_CODE_DEAP.equals(detailEntity.getTransPropertyCode());
			handOverBillDetailsType = isKuaidi ? LoadConstants.HANDOVERBILL_DETAILS_TYPE_EXPRESS
							: LoadConstants.HANDOVERBILL_DETAILS_TYPE_LTL;
			
			set.add(handOverBillDetailsType);
			
			if (set.size() > 1) {
				throw new TfrBusinessException("快递货和零担货不能混装在同一个交接单中！");
			}
		}

		return handOverBillDetailsType;
	}
	/**
	 * 
	 * <p>交接单明细  运输性质是否都为 商务专递  即 产品类型为商务专递DEPA</p> 
	 * @author 189284 
	 * @date 2015-9-6 下午5:12:54
	 * @param detailEntities
	 * @return
	 * @see
	 */
	private Boolean getTransPropertyCode(List<HandOverBillDetailEntity> detailEntities){
		Boolean falge =false;//默认不为商务专递
		for (HandOverBillDetailEntity handOverBillDetailEntity : detailEntities) {
			falge=StringUtils.equals(handOverBillDetailEntity.getTransPropertyCode(), 
					PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
		}
		
		return falge;
	}
	/**
	 * 新增时，保存交接单数据
	 * @author 045923-foss-shiwei
	 * @date 2012-10-20 下午3:31:43
	 * @param newHandOverBillDto交接单信息dto，，返回保存后的交接单号
	 * @return String
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#saveHandOverBill(com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto)
	 */
	@Override
	@Transactional
	public String saveHandOverBill(NewHandOverBillDto newHandOverBillDto) {
		/**
		 *获取dto中的交接单基本信息，补充前台未设置的属性
		 */
		//获取当前用户工号、姓名
		UserEntity user = FossUserContext.getCurrentUser();
		String currentOrgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity org = loadService.querySuperOrgByOrgCode(currentOrgCode);
		//用户code
		String userCode = user.getEmployee().getEmpCode();
		//用户name
		String userName = user.getEmployee().getEmpName();
		//获取当前时间
		Date nowDate = new Date();
		//获取传入的交接单基本信息实体
		HandOverBillEntity handOverBillEntity = newHandOverBillDto.getHandOverBillEntity();
		//是否为预配交接单
		boolean isPreHandOverBill = StringUtils.equals(FossConstants.YES, handOverBillEntity.getIsPreHandOverBill());
		
		//生成交接单号
		LOGGER.error("开始生成交接号，车牌号：" + handOverBillEntity.getVehicleNo());
	    //String handOverBillNo = loadService.generateHandOverBillNo();
		String handOverBillNo = handOverBillEntity.getHandOverBillNo();
		if(StringUtils.length(handOverBillNo) != BillNumContants.LENGTH_HANDOVERBILL){
			handOverBillNo = billNumService.generateHandoverbillNo();
		}
		//营业部交接单的单号须yy开头----332219
		if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
			handOverBillNo = BillNumContants.PREFIX_SALES_HANDOVER_TFR+handOverBillNo;
			//保存为预配
			isPreHandOverBill = false;
			//使用专用车牌号
			handOverBillEntity.setVehicleNo(LoadConstants.VEHICLE_NO_SALE);
		}
		
		//获取交接单明细类型，是全为快递货，还是全为零担货
		String handOverBillDetailsType = getHandOverBillDetailsType(newHandOverBillDto.getWaybillStockList());
		/**
		 * 是否商务专递 是商务专递 交接单号前加ky
		 */
		if(getTransPropertyCode(newHandOverBillDto.getWaybillStockList())){
			handOverBillNo = LoadConstants.HANDOVERBILL_EXPRESS_AIR_FREIGHT + handOverBillNo;
		}//如果交接单明细全是快递货，则在交接单号前面加K标识
		else if(LoadConstants.HANDOVERBILL_DETAILS_TYPE_EXPRESS.equals(handOverBillDetailsType)){
			handOverBillNo = LoadConstants.HANDOVERBILL_EXPRESS_PREFIX + handOverBillNo;
		}
		LOGGER.error("生成交接单号完毕，车牌号：" + handOverBillEntity.getVehicleNo() + "，交接单号：" + handOverBillNo);
		
		LOGGER.error("开始生成交接单，交接单号：" + handOverBillNo);
		//交接单号
		handOverBillEntity.setHandOverBillNo(handOverBillNo);
		// 补充基本信息属性
		handOverBillEntity.setCreateDate(nowDate);
		//交接时间
		handOverBillEntity.setHandOverTime(nowDate);
		// 创建人姓名、工号、币种
		handOverBillEntity.setCreateUserName(userName);
		//创建人code
		handOverBillEntity.setCreateUserCode(userCode);
		//币种
		handOverBillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//主键
		handOverBillEntity.setId(UUIDUtils.getUUID());
		//是否包交接单
		handOverBillEntity.setBePackage(FossConstants.NO);
		//转货类型
		handOverBillEntity.setTranGoodsType(handOverBillEntity.getTranGoodsType());
		
		/**
		 * 校验车辆、司机所有权：自有车、自有司机；外请车、外请车司机，不混搭
		 */
		LOGGER.error("交接单号：" + handOverBillNo + "开始校验车辆、司机所有权");
		String vehcileOwner=this.validateVehicleAndDriverOwnerShip(handOverBillEntity, org);
		LOGGER.error("交接单号：" + handOverBillNo + "校验车辆、司机所有权结束");
		if(StringUtils.equals(org.getExpressBranch(),"Y")&&StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)){
			if(!StringUtils.equals(handOverBillEntity.getVehicleNo().substring(0, 1),"德")){
				throw new TfrBusinessException("分部到点部交接时，车牌必须为虚拟车牌");
			}	
		}
		
		/**
		 * 检验营业部交接，映射校验和是否对接外场校验   332219
		 */
		if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
			 //获取当前部门的code
			 String superOrgCode = this.querySuperiorOrgCode();
			 //接收映射数据的所有部门code集合
			 List<String> arriveDeptList = new ArrayList<String>();
			 //获取当前部门映射数据
			 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(superOrgCode);
			 //判断结果集不为空才添加到映射的到达部门
			 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
				 for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
					   //判断是否是对接外场，否则是对接营业部，是则对接外场  
					   if(StringUtils.equals(FossConstants.NO, entity.getIsOutfield())){
					    	//获取对应营业部的code
							 String deptCode = entity.getDeptCode();
							 arriveDeptList.add(deptCode);
					     }
					     //获取一级网点的code
						 String fthNetworkCode = entity.getFthNetworkCode();
						 if(fthNetworkCode != null){
							 arriveDeptList.add(fthNetworkCode);
						 }
						 //获取二级网点的code
						 String secNetworkCode = entity.getSecNetworkCode();
						 if(secNetworkCode != null){
							 arriveDeptList.add(secNetworkCode);
						 }
					}
				 //去掉重复的数据再加入集合中
				 arriveDeptList.addAll(new HashSet(arriveDeptList));
			 }
			 //获取到达部门的code
			 String arriveDeptCode = handOverBillEntity.getArriveDeptCode();
			 //校验是否跨映射做营业部交接单
			 if(!arriveDeptList.contains(arriveDeptCode)){
				 throw new TfrBusinessException("营业部交接单不能跨映射！");
			 }
		}
		 
		//设置是否为PDA扫描生成：否
		handOverBillEntity.setIsCreatedByPDA(FossConstants.NO);
		//设置修改人修改日期
		handOverBillEntity.setModifyDate(nowDate);
		//新增时，修改人等于创建人
		handOverBillEntity.setModifyUser(handOverBillEntity.getCreateUser());
		//name
		handOverBillEntity.setModifyUserName(handOverBillEntity.getCreateUserName());
		//code
		handOverBillEntity.setModifyUserCode(handOverBillEntity.getCreateUserCode());
		//获取当前部门的上级部门的名称、编码
		String orgCode = this.querySuperiorOrgCode();
		OrgAdministrativeInfoEntity orgEntity = this.orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		String orgName = orgEntity.getName();
		// 出发部门
		handOverBillEntity.setDepartDept(orgName);
		//出发部门code
		handOverBillEntity.setDepartDeptCode(orgCode);
		/**
		 * 遍历dot中的运单list获取Vo中的运单列表，补充前台未设置的属性，遍历时同时计算总票数，总件数，总重量，总体积
		 */
		List<HandOverBillDetailEntity> waybillList = newHandOverBillDto.getWaybillStockList();
		
		
		String isCarLoad=FossConstants.NO;
		//如果只有一条运单，且是整车运单，则交接单为整车交接单
		if(waybillList.size() == 1&& StringUtils.equals(FossConstants.YES, waybillList.get(0).getIsCarLoad())){
			isCarLoad=FossConstants.YES;
		}
		//校验车牌号是否可用(集配交接单、是挂牌号且不是整车的 不需要校验--整车的不论是否挂牌都需要校验)
		if(!( StringUtils.equals(isCarLoad, FossConstants.NO)&&StringUtils.equals(handOverBillEntity.getBeTrailerVehicleNo(),FossConstants.YES)
				&&StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
				&&!StringUtils.equals(vehcileOwner, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				) &&StringUtils.isNotBlank(handOverBillEntity.getVehicleNo()) &&!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
			LOGGER.error("交接单号：" + handOverBillNo + "开始校验车牌号");
			//是否存在有未校验封签的记录
			loadService.validateVehicleNoCanBeUsed(handOverBillEntity.getVehicleNo());
			//校验其他部门是否有使用该车辆并且未出发的记录
			loadService.queryUndepartRecByVehicleNo(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getVehicleNo(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
			LOGGER.error("交接单号：" + handOverBillNo + "校验车牌号结束");
			//设置是否挂牌号为空（防止整车但是是挂牌号的情况，整车要忽略挂牌号的）
			handOverBillEntity.setBeTrailerVehicleNo(null);
		}
		
		//如果只有一条运单，且是整车运单，则交接单为整车交接单
		if(StringUtils.equals(FossConstants.YES, isCarLoad)){
			//是否整车交接单
			handOverBillEntity.setIsCarLoad(FossConstants.YES);
			String waybillNo = waybillList.get(0).getWaybillNo();
			/**
			 * ISSUE-3010	获取该运单的交接记录
			 */
			//如果为集配交接单
			if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)){
				List<HandOverBillEntity> hedList = this.queryHandOveredRecordsByWaybillNo(waybillNo);
				for(HandOverBillEntity hed : hedList){
					if(StringUtils.equals(hed.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
							&& (!StringUtils.equals(handOverBillEntity.getDepartDeptCode(), hed.getDepartDeptCode())
									|| !StringUtils.equals(handOverBillEntity.getArriveDeptCode(), hed.getArriveDeptCode()))){
						throw new TfrBusinessException("该整车运单已经做过集配交接，出发、到达部门分别为：" + hed.getDepartDept() + "、" + hed.getArriveDept() + "。");
					}
				}
			}
//			//获取运单中的约车No
//			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
//			/**
//			 * 如果为整车，则要求车牌号必须和整车运单约车信息中的车牌号一致
//			 * 如果不一直则中断操作
//			 */
//			if(waybill != null){
//				//获取运单 中的约车号
//				String inviteNo = waybill.getOrderVehicleNum();
//				//如果约车编号不为空
//				if(StringUtils.isNotBlank(inviteNo)){
//					//调用约车服务
//					InviteVehicleDto inviteInfo = inviteVehicleService.queryInviteVehicleByInviteNo(inviteNo);
//					//不一致则抛业务异常，中断操作
//					if(!StringUtils.equals(inviteInfo.getVehicleNo(), handOverBillEntity.getVehicleNo())){
//						//业务异常信息
//						throw new TfrBusinessException("交接单车牌号和整车运单约车信息中的车牌号不一致，请核对，外请车申请单号为：" + inviteNo + "，车牌号为：" + inviteInfo.getVehicleNo() + "。");
//					}
//				}
//			}
		}else{
			//是否整车交接单
			handOverBillEntity.setIsCarLoad(FossConstants.NO);
			//如果不是外发交接单、落地配交接单，则读取发车计划
			if(!StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, handOverBillEntity.getHandOverType())
					&& !StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, handOverBillEntity.getHandOverType())){
				LOGGER.error("交接单号：" + handOverBillNo + "开始读取发车计划");
				//获取发车计划信息
				TruckDepartPlanDetailDto planDetail =null;
				/**
				 * @desc 这里加一段代码，去拿当天的发车计划明细，如果拿不到当天的那就按照以前的逻辑走
				 * @author wqh
				 * @date   2015-01-05
				 * 
				 * */
				Date departDate=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//将时间格式化后获取日期
				String strDate=sdf.format(departDate).substring(0, ConstantsNumberSonar.SONAR_NUMBER_10);
				//在将日期字符转为日期格式
				try {
					departDate=sdf.parse(strDate);
				} catch (ParseException e) {					
					LOGGER.error("转换日期格式异常！");
				}

				if(StringUtils.equals(handOverBillEntity.getBeTrailerVehicleNo(),FossConstants.YES)){
					
					//将日期传入
					planDetail = truckDepartPlanDetailService.queryDepartPlanDetailByTrailerVehicleNo(orgCode, handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getVehicleNo(),departDate);
					//如果找不到当天的发车计划则按照以前的逻辑走
					if(planDetail==null)
					{
						planDetail = truckDepartPlanDetailService.queryDepartPlanDetailByTrailerVehicleNo(orgCode, handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getVehicleNo(),null);
						
					}
				}else{
					planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(orgCode, handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getVehicleNo(),departDate);
                    if(planDetail==null)
                    {
                    	planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(orgCode, handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getVehicleNo(),null);
                    	
                    }
					
				}
				
				if(planDetail != null){
					// 发车计划明细ID
					handOverBillEntity.setTruckDepartPlanDetailId(planDetail.getId());
					// 线路名称
					handOverBillEntity.setLineName(planDetail.getLineName());
					//线路虚拟编码
					handOverBillEntity.setLineCode(planDetail.getLineNo());
					//班次
					handOverBillEntity.setFrequencyNo(planDetail.getFrequencyNo());
				}
				LOGGER.error("交接单号：" + handOverBillNo + "读取发车计划结束");
			}
			
			//如果是营业部与营业部的交接单不用校验线路
			if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
				LOGGER.error("交接单号：" + handOverBillNo + "开始校验线路");
				//校验线路
				DepartureStandardDto departureStandard = 
						                                                         // 出发部门编号                            到达部门/外发代理编码 创建日期
					lineService.queryDepartureStandardListBySourceTargetDirectly(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate());
				//如果两部门之间线路不存在，则中断，不允许交接
	            if(departureStandard == null || StringUtils.isBlank(departureStandard.getLineVirtualCode())){
	            	DepartureStandardDto departureStandard2 = expresslineService .queryDepartureStandardListBySourceTargetDirectly(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate());
					//如果到达部门是落地配公司，则看到落地配公司对应网点是否有线路
					if(ldpAgencyCompanyService.queryEntityByCode(handOverBillEntity.getArriveDeptCode(), "Y") != null){
						departureStandard = lineService.queryDepartureStandardListByLdpAgentDepts(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate());
						if(departureStandard == null || StringUtils.isBlank(departureStandard.getLineVirtualCode())){
							departureStandard2 = expresslineService.queryDepartureStandardListByLdpAgentDepts(handOverBillEntity.getDepartDeptCode(), handOverBillEntity.getArriveDeptCode(), handOverBillEntity.getCreateDate());
							if(departureStandard2 == null || StringUtils.isBlank(departureStandard2.getLineVirtualCode())){
								throw new TfrBusinessException("本部门至“" + handOverBillEntity.getArriveDept() + "”之间的线路不存在，无法交接！");
							}
						}
					}else{
						if(departureStandard2 ==null||StringUtils.isBlank(departureStandard2.getLineVirtualCode())){
							//业务异常信息
							throw new TfrBusinessException("本部门至“" + handOverBillEntity.getArriveDept() + "”之间的线路不存在，无法交接！");
						}
					}
				}
				LOGGER.error("交接单号：" + handOverBillNo + "校验线路结束");
			}else{
				LOGGER.error("交接单号：" + handOverBillNo + "是营业部交接单不校验线路");
			}
			/**
			 * 校验交接类型
			 */
			LOGGER.error("交接单号：" + handOverBillNo + "开始校验交接类型");
			OrgAdministrativeInfoEntity departOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(handOverBillEntity.getDepartDeptCode());
			OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(handOverBillEntity.getArriveDeptCode());
			/**
			 * 到达部门、出发部门均为外场，则必须为集配交接单，否则只能为短配
			 */
			if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_PARTIALLINE)
					&& !StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LDP)){
				if(departOrg != null
						&& destOrg != null
						&& StringUtils.equals(departOrg.getTransferCenter(), FossConstants.YES)
						&& StringUtils.equals(destOrg.getTransferCenter(), FossConstants.YES)){
					if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)&&!StringUtils.equals(destOrg.getExpressBranch(),"Y")){
						//业务异常信息
						throw new TfrBusinessException("转运中心之间进行交接时，“交接类型”必须为“集配交接单”！");
					}
				}else if(!StringUtils.equals(handOverBillEntity.getIsCarLoad(), FossConstants.YES)){
					if(!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)
						&&!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_DIVISION)
						&&!StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
						//业务异常信息
						throw new TfrBusinessException("非整车交接时，“交接类型”必须为“短配交接单或者分部交接单或营业部交接单”！");
					}
				}
			}
			LOGGER.error("交接单号：" + handOverBillNo + "校验交接类型结束");
		}
		//构造待保存的运单list
		List<HandOverBillDetailEntity> unSavedWaybillStockList = new ArrayList<HandOverBillDetailEntity>();
		// 定义总票数、总件数、总重量、总体积、代收货款总额
		Integer totalWaybill = 0, totalPieces = 0, fastWaybillCount = 0;
		//定义精准卡航票数、重量、体积；精准空运票数、重量、体积；精准城运票数、重量、体积
		Integer waybillQtyFLF = 0, waybillQtyAF = 0, waybillQtyFSF = 0,waybillQtyBGFLF = 0, waybillQtyBGFSF = 0;
		BigDecimal weightFLF = BigDecimal.ZERO,weightAF = BigDecimal.ZERO,weightFSF = BigDecimal.ZERO,
				volumeFLF = BigDecimal.ZERO,volumeAF = BigDecimal.ZERO,volumeFSF = BigDecimal.ZERO,
						weightBGFLF = BigDecimal.ZERO,weightBGFSF = BigDecimal.ZERO,
						volumeBGFLF = BigDecimal.ZERO,volumeBGFSF = BigDecimal.ZERO;
		//总体积
		BigDecimal totalVolume = BigDecimal.ZERO, 
				//总重量
				totalWeight = BigDecimal.ZERO, 
				//总金额
				totalMoney = BigDecimal.ZERO,
				//代收货款总额
				totalCodAmount = BigDecimal.ZERO,
				//快递货总重量
				fastTotalWeight=BigDecimal.ZERO,
				//非快递货体积
				normalTotalVolume=BigDecimal.ZERO,
				//未转换体积
				unconvertTotalVolume=BigDecimal.ZERO
				;
		for (int i = 0; i < waybillList.size(); i++) {
			HandOverBillDetailEntity waybillEntity = waybillList.get(i);
			//如果为优先货
			if(StringUtils.equals(waybillEntity.getIsFastGoods(), FossConstants.YES)){
				//优先货累加
				fastWaybillCount += 1;
			}
			//如果为精准卡航
			if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FLF)){
				waybillQtyFLF += 1;
				weightFLF = weightFLF.add(waybillEntity.getWeight());
				volumeFLF = volumeFLF.add(waybillEntity.getCubage());
			//如果为精准空运
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_AF)){
				waybillQtyAF += 1;
				weightAF = weightAF.add(waybillEntity.getWeight());
				volumeAF = volumeAF.add(waybillEntity.getCubage());
			//如果为精准城运
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FSF)){
				waybillQtyFSF += 1;
				weightFSF = weightFSF.add(waybillEntity.getWeight());
				volumeFSF = volumeFSF.add(waybillEntity.getCubage());
			//如果为精准大票卡航
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_BGFLF)){
				waybillQtyBGFLF +=1;
				weightBGFLF = weightBGFLF.add(waybillEntity.getWeight());
				volumeBGFLF =  volumeBGFLF.add(waybillEntity.getCubage());
			//如果为精准大票城运
			}else if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_BGFSF)){
				waybillQtyBGFSF +=1;
				weightBGFSF = weightBGFSF.add(waybillEntity.getWeight());
				volumeBGFSF = volumeBGFSF.add(waybillEntity.getCubage());
			}
			
			//币种
			waybillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//交接单号
			waybillEntity.setHandOverBillNo(handOverBillEntity.getHandOverBillNo());
			//出发部门编码
			waybillEntity.setOrigOrgCode(handOverBillEntity.getDepartDeptCode());
			//交接类型
			waybillEntity.setHandOverType(handOverBillEntity.getHandOverType());
			//设置ID
			waybillEntity.setId(UUIDUtils.getUUID());
			//设置交接时间
			waybillEntity.setCreateDate(handOverBillEntity.getHandOverTime());
			//设置修改时间 
			waybillEntity.setModifyDate(handOverBillEntity.getHandOverTime());
			//保险价值，乘以100
			waybillEntity.setInsuranceValue(waybillEntity.getInsuranceValue().multiply(new BigDecimal(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
			//运单金额
			waybillEntity.setWaybillFee(waybillEntity.getWaybillFee().multiply(new BigDecimal(LoadConstants.INSURANCE_VALUE_MULTIPLY)));//存储总金额的时候以分为单位，乘以100
			unSavedWaybillStockList.add(waybillEntity);
			//票数+1
			totalWaybill += 1;
			//件数累加
			totalPieces += waybillEntity.getPieces().intValue();
			//总体积累加
			unconvertTotalVolume = unconvertTotalVolume.add(waybillEntity.getCubageAc());
			//总重量累加
			totalWeight = totalWeight.add(waybillEntity.getWeightAc());
			//总体积累加
			totalVolume = totalVolume.add(waybillEntity.getCubageAc());
			//总金额累加
			totalMoney = totalMoney.add(waybillEntity.getWaybillFee());
			//代收货款总额累加
			totalCodAmount = totalCodAmount.add(waybillEntity.getCodAmount());
			//快递转换体积
			if(StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_PACKAGE)
					||StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_RCP)
					||StringUtils.equals(waybillEntity.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_EPEP)){
				fastTotalWeight=fastTotalWeight.add(waybillEntity.getWeightAc());
			}else{
				normalTotalVolume=normalTotalVolume.add(waybillEntity.getCubageAc());
			}
			
		}
	
		//计算快递转换体积
	    // zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		//totalVolume=(normalTotalVolume.add(fastTotalWeight.multiply(queryExpressConverParameter(handOverBillEntity.getDepartDeptCode())).divide(new BigDecimal(1000)))).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		// 总重量，总体积，总金额、代收货款总额
		handOverBillEntity.setVolumeTotal(totalVolume);
		//重量
		handOverBillEntity.setWeightTotal(totalWeight);
		//总金额
		handOverBillEntity.setMoneyTotal(totalMoney);
		//代收货款总额
		handOverBillEntity.setCodAmountTotal(totalCodAmount);
		// 优先货票数
		handOverBillEntity.setFastWaybillQty(fastWaybillCount);
		// 总件数，总票数
		handOverBillEntity.setGoodsQtyTotal(totalPieces);
		handOverBillEntity.setWaybillQtyTotal(totalWaybill);
		//卡航、空运、城运、大票票数、重量、体积
		handOverBillEntity.setWaybillQtyAF(waybillQtyAF);
		handOverBillEntity.setGoodsWeightAF(weightAF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeAF(volumeAF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyFLF(waybillQtyFLF);
		handOverBillEntity.setGoodsWeightFLF(weightFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeFLF(volumeFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyFSF(waybillQtyFSF);
		handOverBillEntity.setGoodsWeightFSF(weightFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeFSF(volumeFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyBGFLF(waybillQtyBGFLF);
		handOverBillEntity.setGoodsWeightBGFLF(weightBGFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeBGFLF(volumeBGFLF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setWaybillQtyBGFSF(waybillQtyBGFSF);
		handOverBillEntity.setGoodsWeightBGFSF(weightBGFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		handOverBillEntity.setGoodsVolumeBGFSF(volumeBGFSF.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		
		/**
		 * 遍历dto中的流水号list获取流水号列表，补充前台未设置的属性
		 */
		List<HandOverBillSerialNoDetailEntity> serialNoList = newHandOverBillDto.getSerialNoStockList();
		// 循环流水号列表，设置其他属性
		List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList = new ArrayList<HandOverBillSerialNoDetailEntity>();
		//同时获取其中所有在库的流水号，如果为正式交接单，则出库所有流水号
		//List<InOutStockEntity> inStorageSerialNoList = new ArrayList<InOutStockEntity>();
		//获取所有在库流水号，如果为预配交接单，则更改库存预配状态
		List<UpdateStockPreHandOverStateDto> stockEntityList = new ArrayList<UpdateStockPreHandOverStateDto>();
		//同时获取其中所有不在库的流水号，为预配交接单，更改交接单中预配状态 
		//List<UpdateHandOverBillSerialNoPreHandOverStateDto> noInStorageSerialNoList = new ArrayList<UpdateHandOverBillSerialNoPreHandOverStateDto>();
		//构造出库list
		List<InOutStockEntity> outStockList = new ArrayList<InOutStockEntity>();
		for (int i = 0; i < serialNoList.size(); i++) {
			HandOverBillSerialNoDetailEntity serialNo = serialNoList.get(i);
			/*//若为不在库
			if(serialNo.getIsInStorage() == 0){//
//				UpdateHandOverBillSerialNoPreHandOverStateDto dto = new UpdateHandOverBillSerialNoPreHandOverStateDto();
//				dto.setHandOverBillNo(serialNo.getHandOverBillNo());
//				dto.setWaybillNo(serialNo.getWaybillNo());
//				dto.setSerialNo(serialNo.getSerialNo());
//				noInStorageSerialNoList.add(dto);
				//若为在库
			}else if(serialNo.getIsInStorage() == 1){
				InOutStockEntity entity = new InOutStockEntity();
				//运单号
				entity.setWaybillNO(serialNo.getWaybillNo());
				//流水号
				entity.setSerialNO(serialNo.getSerialNo());
				//部门code
				entity.setOrgCode(orgCode);
				//出库类型，手动交接出库
				entity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
				//操作人code
				entity.setOperatorCode(userCode);
				//操作人name
				entity.setOperatorName(userName);
				//交接单号
				entity.setInOutStockBillNO(handOverBillNo);
				inStorageSerialNoList.add(entity);
			}*/
			
			//如果为预配交接单，则构造为更新库存预配状态的实体集合
			if(isPreHandOverBill){
				UpdateStockPreHandOverStateDto e = new UpdateStockPreHandOverStateDto();
				//运单号
				e.setWaybillNo(serialNo.getWaybillNo());
				//流水号
				e.setSerialNo(serialNo.getSerialNo());
				//部门code
				e.setDeptCode(orgCode);
				stockEntityList.add(e);
			}else{
				//如果为合车的流水号，则调用走货路径接口，修改走货路径
				if(StringUtils.equals(serialNo.getIsJoinCar(),FossConstants.YES)){
					//修改走货路径
					this.calculateTransportPathService.arriveMistake(serialNo.getWaybillNo(), serialNo.getSerialNo(), handOverBillEntity.getArriveDeptCode(),handOverBillEntity.getDepartDeptCode());
				}
				InOutStockEntity entity = new InOutStockEntity();
				//运单号
				entity.setWaybillNO(serialNo.getWaybillNo());
				//流水号
				entity.setSerialNO(serialNo.getSerialNo());
				//部门code
				entity.setOrgCode(orgCode);
				//出库类型，手动交接出库
				entity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
				//操作人code
				entity.setOperatorCode(userCode);
				//操作人name
				entity.setOperatorName(userName);
				//交接单号
				entity.setInOutStockBillNO(handOverBillNo);
				outStockList.add(entity);
			}
			// 设置流水号之交接单编号
			serialNo.setHandOverBillNo(handOverBillEntity.getHandOverBillNo());
			// 设置流水号之交接时间
			serialNo.setHandOverTime(handOverBillEntity.getHandOverTime());
			// 设置流水号之出发部门编码
			serialNo.setOrigOrgCode(handOverBillEntity.getDepartDeptCode());
			// 流水号生成主键
			serialNo.setId(UUIDUtils.getUUID());
			unsavedSerialNoStockList.add(serialNo);
		}
		//营业部交接单出库 360903
		SaleDepartmentEntity stockDept = saleDepartmentService.querySaleDepartmentByCode(orgCode);
		//判断非空
		if(stockDept != null && CollectionUtils.isNotEmpty(outStockList)){
			if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)|| 
			   !StringUtils.equals(stockDept.getIsTwoLevelNetwork(),LoadConstants.DELIVER_LOAD_GAP_REPORT_VALID)){
				//记录批量出库日志
				LOGGER.error("交接单号：" + handOverBillNo + "流水号批量虚拟出库开始");
				//批量出库
				outStockList = stockService.queryStockInfoSale(outStockList);
				LOGGER.error("交接单号：" + handOverBillNo + "流水号批量虚拟出库结束");
			}
		}
//		//库存货物批量出库
//		if(!CollectionUtils.isEmpty(outStockList)){
//			//记录批量出库日志
//			LOGGER.error("交接单号：" + handOverBillNo + "流水号批量出库开始");
//			//批量出库
//			stockService.outStockBatchPC(outStockList);
//			LOGGER.error("交接单号：" + handOverBillNo + "流水号批量出库结束");
//		}
		//根据是否为预配交接单来设置交接单状态
		if(isPreHandOverBill){
			//已预配
			handOverBillEntity.setHandOverBillState(LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER);
			stockService.updatePreHandOverState(stockEntityList,StockConstants.PRE_HANDOVER_STATUS);
			//更改不在库的流水号的预配状态 
			//updateHandOverBillSerialNoPreHandOverState(noInStorageSerialNoList);
		}else{
			//已交接（正式交接单）
			handOverBillEntity.setHandOverBillState(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
		}
		//调用dao，传入交接单基本信息、运单列表、流水号列表，分三级insert数据
		handOverBillDao.saveHandOverBill(handOverBillEntity,unSavedWaybillStockList, unsavedSerialNoStockList);
		LOGGER.error("交接单号：" + handOverBillNo + "生成结束！");
		
		
		//库存货物批量出库
		if(!CollectionUtils.isEmpty(outStockList)){
			//记录批量出库日志
			LOGGER.error("交接单号：" + handOverBillNo + "流水号批量出库开始");
			
			//批量出库
			//stockService.outStockBatchPC(outStockList);
			
			//按照类型批量出库
			stockService.outStockBatchPCByType(outStockList, StockConstants.OUT_STOCK_TYPE_HANDOVER, handOverBillNo);
			
			LOGGER.error("交接单号：" + handOverBillNo + "流水号批量出库结束");
		}
		
		
		LOGGER.error("交接单号：" + handOverBillNo + "生成任务车辆开始！");
		truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
		LOGGER.error("交接单号：" + handOverBillNo + "生成任务车辆结束！");
		
		//返回前台重新生成的交接单号
		return handOverBillNo;
	}
	/**
	 * 查询快递转换体积参数
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryExpressConverParameter()
	 */
	public BigDecimal queryExpressConverParameter(String orgCode){
		
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter=BigDecimal.ZERO;
		String  stringValue = "";
		try{
			if(StringUtils.isNotEmpty(orgCode)){
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		}catch(Exception e){
			throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常"+e.toString());
		}

		if(stringValue!=null && StringUtils.isNotEmpty(stringValue)){
			double doubleValue = Double.valueOf(stringValue.toString());
			converParameter = new BigDecimal(doubleValue);
			BigDecimal a =new BigDecimal("1.000");
			//重泡比为重量体积转换参数分之一
			 converParameter = a.divide(converParameter,LoadConstants.SONAR_NUMBER_3);
			 return converParameter;
		}else{
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, 
					"DIP");
			if(paramEntity!=null){
				String value=paramEntity.getConfValue();
				try {
					double dvalue = Double.parseDouble(value);
					converParameter = new BigDecimal(dvalue);
				} catch (Exception e) {
					throw new TfrBusinessException("快递转换体积参数转换错误："+e.toString());
				}
				return converParameter;
				
			}else{
				throw new TfrBusinessException("请配置快递转换体积参数！");
			}
		}

	} 
	/**
	 * 查询交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:19:05
	 * @param queryHandOverBillConditionDto
	 * @param limit
	 * @param start
	 * @return List<QueryHandOverBillDto>
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillList(QueryHandOverBillConditionDto queryHandOverBillConditionDto,int limit,int start) {
		/**
		 * 调用dao，返回查询结果
		 * “出发部门”或者“到达部门”为本部门的交接单，
		 * 所有的查询都以此条件为基础；
		 * 查询结果中不包含“到达部门”为本部门的预配交接单，
		 */	
		String orgCode = this.querySuperiorOrgCode();
		queryHandOverBillConditionDto.setCurrentDept(orgCode);		
		return handOverBillDao.queryHandOverBillList(queryHandOverBillConditionDto,limit,start);
	}
	
	
	/**
	 * 获取交接单条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 上午10:00:31
	 * @return Long
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getHandOverBillListCount(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@Override
	public Long getHandOverBillListCount(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		/**
		 * 调用dao，返回查询结果
		 */
		String orgCode = this.querySuperiorOrgCode();
		queryHandOverBillConditionDto.setCurrentDept(orgCode);
		return handOverBillDao.getHandOverBillListCount(queryHandOverBillConditionDto);
	}

	/**
	 * 根据交接单号获取交接单运单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:19:25
	 * @param handOverBillNo 交接单号
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillDetailByNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryHandOverBillDetailByNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo,null);
	}
	
	/**
	 * 根据交接单号获取交接单运单列表
	 * <li>如果是子母件，则优先取录入的子母件重量与体积</li>
	 * @author 257220
	 * @date 2015-09-16 16:50:30
	 * @param handOverBillNo 交接单号
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillDetailByNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryHandOverBillDetailByHandNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		List<HandOverBillDetailEntity> list = handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo,null);
		return list;
	}
	/**
	 * 根据交接单号获取正交接单运单列表
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午3:25:17
	 * @param handOverBillNo 交接单号
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryAirHandOverBillDetailByNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryAirHandOverBillDetailByNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryAirHandOverBillDetailByNo(handOverBillNo,null);
	}
	/**
	 * 根据交接单号查询出打印外发清单中需要的数据 
	 * @author ibm-zhangyixin
	 * @param handOverBillNo
	 * @return List<HandOverBillDetailDto>
	 * @exception 无
	 * @date 2012-10-31 上午9:33:17
	 */
	@Override
	public List<HandOverBillDetailDto> queryPrintPartialLineDataByNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryPrintPartialLineDataByNo(handOverBillNo);
	}

	/**
	 * 根据交接单号查询出打印交接单中需要的数据 
	 * @author ibm-zhangyixin
	 * @param handOverBillNo
	 * @return List<HandOverBillDetailDto>
	 * @exception 无
	 * @date 2012-10-31 上午9:33:17
	 */
	@Override
	public List<HandOverBillDetailDto> queryPrintHandOverBillDataByNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryPrintHandOverBillDataByNo(handOverBillNo);
	}
	/**
	 * 
	 * <p>根据交接单号查询出打印（空运）（正）交接单中需要的数据</p> 
	 * @author 189284 
	 * @date 2015-10-15 上午11:37:10
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	@Override
	public List<HandOverBillDetailDto> queryPrintAirHandOverBillDataByNo(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryPrintAirHandOverBillDataByNo(handOverBillNo);
	}
	
	/**
	 * 根据交接单号、运单号获取流水号list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午6:27:44
	 * @param handOverBillNo交接单号
	 * @param waybillNo运单号
	 * @return List<SerialNoLoadExceptionDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryWaybillDetailByNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SerialNoLoadExceptionDto> queryWaybillDetailByNos(String handOverBillNo, String waybillNo) {
		String currentDeptCode = this.querySuperiorOrgCode();
		BigDecimal param = null;
		try{
			param =	queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积参数失败！");
		}
		List<SerialNoLoadExceptionDto>  serialDtos =handOverBillDao.queryWaybillDetailByNos(handOverBillNo, waybillNo);
		/**
		* 获取所有的快递3级产品code
		* zx
		* 2015-09-06
		* */
		List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
		if(param!=null){
			for(SerialNoLoadExceptionDto serialNoEntity:serialDtos){
				if(CollectionUtils.isNotEmpty(productCodeList)
						&&productCodeList.contains(serialNoEntity.getProductCode())
						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
						
						){
					/**
				if((StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_PACKAGE)
						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_RCP)
						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_EPEP))
						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
						){**/
					BigDecimal conVolumn =param.multiply(serialNoEntity.getWeight()==null?new BigDecimal(0):serialNoEntity.getWeight()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					serialNoEntity.setVolumn(conVolumn);	
				}
				 
			}
		}
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryWaybillDetailByNos(handOverBillNo, waybillNo);
	}

	/**
	 * 根据交接单号，获取交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午10:16:51
	 * @param handOverBillNo交接单号
	 * @param limit
	 * @param start
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillOptLogByNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillOptLogEntity> queryHandOverBillOptLogByNo(String handOverBillNo,int limit,int start) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.queryHandOverBillOptLogByNo(handOverBillNo,limit,start);
	}

	/**
	 * 根据交接单号，获取修改日志条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午11:13:25
	 * @param handOverBillNo交接单号
	 * @return Long
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getHandOverBillOptLogCount(java.lang.String)
	 */
	@Override
	public Long getHandOverBillOptLogCount(String handOverBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return handOverBillDao.getHandOverBillOptLogCount(handOverBillNo);
	}

	/**
	 * 批量更新交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 下午4:45:03
	 * @param handOverBillNoList交接单号list，
	 * 		targetState目标状态(10：已预配，
	 * 										20：已交接，
	 * 										21：已配载（集配交接单特有），
	 * 										30：已出发，
	 * 										40：已到达，
	 * 										50：已入库，
	 * 										90：已作废)
	 * @return boolean
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverBillState(java.util.List, int)
	 */
	@Override
	public boolean updateHandOverBillState(List<UpdateHandOverBillStateDto> list) throws BusinessException{
		//遍历传入的list
		for(UpdateHandOverBillStateDto dto : list){
			int targetState = dto.getTargetState();
			HandOverBillEntity entity = handOverBillDao.queryHandOverBillByNo(dto.getHandOverBillNo());
			if(entity == null){
				//校验交接单号是否存在
				throw new TfrBusinessException("交接单【" + dto.getHandOverBillNo() + "】不存在或者已经被作废！");
			}
			int nowState = entity.getHandOverBillState();
			//如果是作废交接单，则检查交接单状态
			if(targetState == LoadConstants.HANDOVERBILL_STATE_ALREADY_CANCEL){
				if(nowState != LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER && nowState != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
					//只能作废已预配、已交接的交接单
					throw new HandOverBillException(HandOverBillExceptionCode.HANDOVERBILL_ALREADY_LEAVE);
				}
				//校验任务车辆状态，不能作废已出发的车辆,营业部交接除外  by360903
				List<String> stateList = handOverBillDao.validateHandOverBillStateFromTruckTask(dto.getHandOverBillNo());
				if(CollectionUtils.isNotEmpty(stateList)
						&& !StringUtils.equals(stateList.get(0), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART)
						&& !StringUtils.equals(entity.getHandOverType(),LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
					throw new TfrBusinessException("车辆已经出发，无法作废交接单！");
				}
				//如果为正式交接单，则记录修改日志
				if(nowState >= LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
					//记录作废日志
					HandOverBillOptLogEntity logEntity = new HandOverBillOptLogEntity();
					//id
					logEntity.setId(UUIDUtils.getUUID());
					//操作时间
					logEntity.setOptTime(new Date());
					//创建人
					logEntity.setCreateUser(entity.getCreateUserCode());
					//交接单id
					logEntity.setHandOverBillID(entity.getId());
					//交接单号
					logEntity.setHandOverBillNo(entity.getHandOverBillNo());
					//交接时间
					logEntity.setHandOverTime(entity.getHandOverTime());
					//修改人code
					logEntity.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
					//修改人name
					logEntity.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
					//修改类别
					logEntity.setOptType("作废交接单");
					//修改内容
					logEntity.setOptContent("作废交接单");
					List<HandOverBillOptLogEntity> logList = new ArrayList<HandOverBillOptLogEntity>();
					logList.add(logEntity);
					handOverBillDao.addHandOverBillModifyLogList(logList);
				}
				LOGGER.error("作废交接单开始调用任务车辆接口，交接单号：" + entity.getHandOverBillNo());
				//则调用接口，删除任务车辆相关信息
				truckTaskService.deleteTruckTaskByHandOverBill(entity.getHandOverBillNo());
				LOGGER.error("作废交接单调用任务车辆接口结束，交接单号：" + entity.getHandOverBillNo());
			}
			//如果修改为已配载，则检查该交接单是否已配载  
			if(targetState == LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE){
				if(nowState != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER
						&& nowState != LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART){
					throw new TfrBusinessException("交接单【" + dto.getHandOverBillNo() + "】状态不是“已交接”，无法进行配载！");
				}
			}
			//如果修改为“已入库”之前的状态，则不执行update
			if(targetState < LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK
					&& nowState >= LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK){
				return false;
			}
			//如果为集配交接单，则不能从已交接跳到已出发，不能从已出发或者已配载回滚至已交接
			if(StringUtils.equals(entity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)){
				if(nowState == LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER
						&& targetState == LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART
						&& !StringUtils.equals(dto.getBeJump(), FossConstants.YES)){
					return false;
				}
				if((nowState == LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART
						|| nowState == LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE)
							&& targetState == LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER
							&& !StringUtils.equals(dto.getBeJump(), FossConstants.YES)){
					return false;
				}
			}
		}
		handOverBillDao.updateHandOverBillState(list);
		return true;
	}
	
	/**
	 * 根据交接单号单条更新交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-12 下午4:09:38
	 * @param handOverBillNo
	 * @param targetState
	 * @return boolean
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverBillStateByNo(java.lang.String, int)
	 */
	@Override
	public boolean updateHandOverBillStateByNo(String handOverBillNo,
			int targetState) {
		//构造更新交接单的传入参数
		UpdateHandOverBillStateDto dto = new UpdateHandOverBillStateDto();
		//交接单号
		dto.setHandOverBillNo(handOverBillNo);
		//目标状态
		dto.setTargetState(targetState);
		List<UpdateHandOverBillStateDto> list = new ArrayList<UpdateHandOverBillStateDto>();
		list.add(dto);
		//更新交接单状态
		this.updateHandOverBillState(list);
		return true;
	}
	
	
	/**
	 * 根据交接单号单条更新交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-12 下午4:09:38
	 * @param handOverBillNo
	 * @param targetState
	 * @return boolean
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverBillStateByNo(java.lang.String, int)
	 */
	@Override
	public void updateWKHandOverBillStateByNo(String handOverBillNo,
			String handOverBillState) {
		Map<String,String> param=new HashMap<String,String>();
		param.put("handOverBillNo", handOverBillNo);
		param.put("handOverBillState", handOverBillState);
		handOverBillDao.updateWKHandOverBillStateByNo(param);
	}

	/**
	 * 根据交接单号得到交接单实体
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午1:56:49
	 * @param handOverBillNo交接单号
	 * @return HandOverBillEntity
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillByNo(java.lang.String)
	 */
	@Override
	public HandOverBillEntity queryHandOverBillByNo(String handOverBillNo) {
		//返回查询结果
		return handOverBillDao.queryHandOverBillByNo(handOverBillNo);
	}
	/**
	 * 根据交接单号得到交接单实体
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午6:36:48
	 * @param handOverBillNo交接单号
	 * @return HandOverBillEntity
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryAirHandOverBillByNo(java.lang.String)
	 */
	@Override
	public HandOverBillEntity queryAirHandOverBillByNo(String handOverBillNo) {
		//返回查询结果
		return handOverBillDao.queryAirHandOverBillByNo(handOverBillNo);
	}
	
	
	/**
	 * 用于交接单详情界面，导出运单列表，返回excel文件的inputstream
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午2:33:44
	 * @param handOverBillNo交接单号
	 * @return List
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getWayBillExcelInputStream(java.util.List)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getWayBillExcelInputStream(String handOverBillNo,String handOverType){
		//获取交接单下所有运单
		List<HandOverBillDetailDto> wayBillList = handOverBillDao.queryHandOverBillDetailByHandbillNo(handOverBillNo,handOverType);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(HandOverBillDetailDto waybill : wayBillList){
			List<String> columnList = new ArrayList<String>();
			//运单号
			columnList.add(waybill.getWaybillNo());
			//运输性质
			columnList.add(waybill.getTransProperty());
			//货物类型
			String goodsType = "全部";
			if(StringUtils.isNotEmpty(waybill.getGoodsType())) {
				if(StringUtils.equals(WaybillConstants.GOODS_TYPE_A, waybill.getGoodsType())) {
					goodsType = "A类";
				} else if (StringUtils.equals(WaybillConstants.GOODS_TYPE_B, waybill.getGoodsType())) {
					goodsType = "B类";
				}
			}
			String weight = waybill != null && waybill.getWeight() != null ? waybill.getWeight().toString() : "0.00";
			String cubage = waybill != null && waybill.getCubage() != null ? waybill.getCubage().toString() : "0.00";
			
			columnList.add(goodsType);
			//已配件数
			columnList.add(waybill.getPieces() != null ? waybill.getPieces().toString() : "0");
			//已配重量
			columnList.add(weight);
			//实际重量
			columnList.add(waybill.getWeightAc() != null ? waybill.getWeightAc().toString() : "0.00");
			//已配体积
			columnList.add(cubage);
			//实际体积
			columnList.add(waybill.getCubageAc() != null ? waybill.getCubageAc().toString() : "0.00");
			//代收货款
			BigDecimal codAmt = waybill.getCodAmount();
			if(codAmt == null) {
				codAmt = BigDecimal.ZERO;
			}
			columnList.add(codAmt.toString());
			//备注
			columnList.add(waybill.getNote());
			//货物名称
			columnList.add(waybill.getGoodsName());
			//包装
			columnList.add(waybill.getPacking());
			//收货部门
			columnList.add(waybill.getReceiveOrgName());
			//提货网点
			columnList.add(waybill.getArriveDept());
			//目的站
			columnList.add(waybill.getDestination());
			//运单备注
			columnList.add(waybill.getWaybillNote());
			if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE,waybill.getHandOverType()) || 
					StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP,waybill.getHandOverType())){
				//保险价值费
				BigDecimal insuranceValue = waybill.getInsuranceValue();
				if(insuranceValue==null){
					insuranceValue=BigDecimal.ZERO;
				}
				columnList.add(insuranceValue.toString());		
			}/*else if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP,waybill.getHandOverType())){
				//保险价值费
				BigDecimal insuranceValue = waybill.getInsuranceValue();
				if(insuranceValue==null){
					insuranceValue=BigDecimal.ZERO;
				}
				columnList.add(insuranceValue.toString());
			}*/
			//是否贵重物品
			if(StringUtils.equals(waybill.getIsPreciousGoods(), FossConstants.YES)){
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			
			//收货联系人
			columnList.add(waybill.getReceiveCustomerContact());
			//收货人手机
//			columnList.add(waybill.getReceiveCustomerMobilephone());
			//收货人电话
//			columnList.add(waybill.getReceiveCustomerPhone());
			//收货人地址   调用接口组合拼接地址
			//String adress = handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(), waybill.getReceiveCustomerCityCode(), waybill.getReceiveCustomerDistCode(), waybill.getReceiveCustomerAddress());
//			String adress=waybillManagerService.getReceivingAddress(waybill.getWaybillNo());
//			if(StringUtils.isEmpty(adress)){
//				columnList.add("");
//			}else{
//				columnList.add(adress);	
//			}
			
			//货物尺寸
			columnList.add(waybill.getGoodsSize());
			//到付金额
			columnList.add(waybill.getToPayAmount()+"");
			if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP,waybill.getHandOverType())){
				//modified by 257220 发货人取值由发货客户名称更改为发货联系人 2015/08/11
				//发货人
				//columnList.add(waybill.getDeliveryCustomerName());
				columnList.add(waybill.getDeliveryCustomerContact());
				//出发部门地址
				String code=waybill.getReceiveOrgCode();
				if(StringUtils.isNotEmpty(code)){
					OrgAdministrativeInfoEntity administrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
					if(administrativeInfoEntity!=null){
						String provName = administrativeInfoEntity.getProvName();
					    String cityName = administrativeInfoEntity.getCityName();
					    String countyName = administrativeInfoEntity.getCountyName();
					    if(StringUtils.isEmpty(provName)){
					    	provName="";
					    }
					    if(StringUtils.isEmpty(cityName)){
					    	cityName="";
					    }
					    if(StringUtils.isEmpty(countyName)){
					    	countyName="";
					    }
					    columnList.add(provName+cityName+countyName);
					}else{
						 columnList.add("");
					}
				}else{
					 columnList.add("");
				}				
			}
			rowList.add(columnList);
		}
		//导出资源类
		ExportResource exportResource = new ExportResource();
		
		if(CollectionUtils.isNotEmpty(wayBillList)&& wayBillList.size()>0 && StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE,wayBillList.get(0).getHandOverType())){
			//定义表头
			String[] rowHeads = {"运单号",
											"运输性质",
											"货物类型",
											"已配件数",
											"已配重量",
											"实际重量",
											"已配体积",
											"实际体积",
											"代收货款",
											"备注",
											"货物名称",
											"包装",
											"收货部门",
											"提货网点",
											"目的站",
											"运单备注",
											"保险价值",
											"是否贵重物品",
											"收货联系人",
											"货物尺寸",
											"到付金额"
											};
			//			"收货人手机",			"收货人电话",			"收货人地址",			"货物尺寸",			"到付金额"
			//设置导出文件的表头
		    exportResource.setHeads(rowHeads);
			
		}else if(CollectionUtils.isNotEmpty(wayBillList)&& wayBillList.size()>0 && StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP ,wayBillList.get(0).getHandOverType())){
			String[] rowHeads = {"运单号",
											"运输性质",
											"货物类型",
											"已配件数",
											"已配重量",
											"实际重量",
											"已配体积",
											"实际体积",
											"代收货款",
											"备注",
											"货物名称",
											"包装",
											"收货部门",
											"提货网点",
											"目的站",
											"运单备注",
											"保险价值",
											"是否贵重物品",
											"收货联系人",
											"货物尺寸",
											"到付金额",
											"发货人",
											"出发部门地址"
			                                 };
			//			"收货人手机",			"收货人电话",			"收货人地址",			"货物尺寸",			"到付金额",			"发货人",			"出发部门地址"
			//设置导出文件的表头
		    exportResource.setHeads(rowHeads);
		}
			
			else {
			//定义表头
			String[] rowHeads = {"运单号",
					"运输性质",
					"货物类型",
					"已配件数",
					"已配重量",
					"实际重量",
					"已配体积",
					"实际体积",
					"代收货款",
					"备注",
					"货物名称",
					"包装",
					"收货部门",
					"提货网点",
					"目的站",
					"运单备注",
					"是否贵重物品",
					"收货联系人",
					"货物尺寸",
					"到付金额"
					
			};
			//			"收货人手机",			"收货人电话",			"收货人地址",			"货物尺寸",			"到付金额"
			//设置导出文件的表头
		    exportResource.setHeads(rowHeads);
		}
		
		
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("运单列表");
	    //设置sheet行数
	    exportSetting.setSize(wayBillList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = null;
		try {
			fileName = URLEncoder.encode("运单列表" + "-交接单号：" + handOverBillNo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	/**
	 * 导出交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:09:12
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getHandOverBillExcelInputStream(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getHandOverBillExcelInputStream(QueryHandOverBillConditionDto queryHandOverBillConditionDto){
		//查询交接单
		String orgCode = this.querySuperiorOrgCode();
		queryHandOverBillConditionDto.setCurrentDept(orgCode);
		List<QueryHandOverBillDto> handOverBillList = handOverBillDao.queryHandOverBillListNoPaging(queryHandOverBillConditionDto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(handOverBillList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(QueryHandOverBillDto bill : handOverBillList){
			List<String> columnList = new ArrayList<String>();
			//交接单号
			columnList.add(bill.getHandOverBillNo());
			//转货类型
			if(StringUtils.equals(bill.getTranGoodsType(),LoadConstants.TRANSITGOODS_TYPE_TRAN)){
				columnList.add("转货");
			}else if(StringUtils.equals(bill.getTranGoodsType(),LoadConstants.TRANSITGOODS_TYPE_TAKE)){
				columnList.add("带货");
			}else{
				columnList.add("");
			}
			//不导出已作废的交接单
			if(StringUtils.equals("PACKAGE_HANDOVER", bill.getHandOverType())){
				columnList.add("已交接");
			}else{
			if(bill.getHandOverBillState() == LoadConstants.HANDOVERBILL_STATE_ALREADY_CANCEL){
				continue;
			}else{
				String handOverBillState = this.convertStateNumber2Value(bill.getHandOverBillState());
				//交接单状态
				columnList.add(handOverBillState);
			}
			}
			//交接类型
			if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)){
				columnList.add("集配交接单");
			}else if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)){
				columnList.add("短配交接单");
			}else if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_PARTIALLINE)){
				columnList.add("外发交接单");
			}else if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_LDP)){
				columnList.add("快递代理交接单");
			}else if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_DIVISION)){
				columnList.add("分部交接单");
			}else if(StringUtils.equals(bill.getHandOverType(), "PACKAGE_HANDOVER")){
				columnList.add("快递空运交接单");
			}
			else{
				columnList.add(bill.getHandOverType());
			}
			//交接日期
			columnList.add(DateUtils.convert(bill.getHandOverTime(), DateUtils.DATE_TIME_FORMAT));
			//车牌号
			columnList.add(bill.getVehicleNo());
			//是否挂牌号
			if(StringUtils.equals(bill.getBeTrailerVehicleNo(), FossConstants.YES)){
				columnList.add("是");
			}else{
				columnList.add("");
			}
			//出发部门
			columnList.add(bill.getDepartDept());
			//出发时间
			columnList.add(DateUtils.convert(bill.getDepartTime(), DateUtils.DATE_TIME_FORMAT));
			//到达部门、外发代理
			if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_PARTIALLINE)){
				columnList.add(null);
				columnList.add(bill.getArriveDept());
			}else{
				columnList.add(bill.getArriveDept());
				columnList.add(null);
			}
			//到达时间
			columnList.add(DateUtils.convert(bill.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
			//总票数
			columnList.add(bill.getWaybillQtyTotal().toString());
			//总件数
			columnList.add(bill.getGoodsQtyTotal().toString());
			//总重量
			columnList.add(bill.getWeightTotal().toString());
			//总体积
			columnList.add(bill.getVolumeTotal().toString());
			if(bill.getMoneyTotal()==null){
				bill.setMoneyTotal(new BigDecimal("0"));
			}else{
			//总金额
			columnList.add(bill.getMoneyTotal().toString());
			}
			//BUG-57558   begin
			//代收货款总额
			if(bill.getCodAmountTotal()==null){
				columnList.add(null);
			}else{
			columnList.add(bill.getCodAmountTotal().toString());
			}
			//BUG-57558   end
			
			//空运票数
			if(bill.getWaybillQtyAF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getWaybillQtyAF().toString());
			}
			//空运重量
			if(bill.getGoodsWeightAF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsWeightAF().toString());
			}
			//空运体积
			if(bill.getGoodsVolumeAF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsVolumeAF().toString());
			}
			//卡航票数
			if(bill.getWaybillQtyFLF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getWaybillQtyFLF().toString());
			}
			//卡航重量
			if(bill.getGoodsWeightFLF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsWeightFLF().toString());
			}
			//卡航体积
			if(bill.getGoodsVolumeFLF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsVolumeFLF().toString());
			}
			//城运票数
			if(bill.getWaybillQtyFSF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getWaybillQtyFSF().toString());
			}
			//城运重量
			if(bill.getGoodsWeightFSF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsWeightFSF().toString());
			}
			//城运体积
			if(bill.getGoodsVolumeFSF() == null){
				columnList.add(ZERO);
			}else{
				columnList.add(bill.getGoodsVolumeFSF().toString());
			}
			//制单人
			if(StringUtils.isBlank(bill.getCreateUserCode())){
				columnList.add(null);
			}else{
				columnList.add(bill.getCreateUserName() + "(" + bill.getCreateUserCode() + ")");
			}
			//司机
			if(StringUtils.isBlank(bill.getDriver())){
				columnList.add(null);
			}else{
				columnList.add(bill.getDriverName() + "(" + bill.getDriver() + ")");
			}
			
			rowList.add(columnList);
		}
		//定义表头
		String[] rowHeads = {"交接单编号",
										"转货类型",
										"状态",
										"交接类型",
										"交接日期",
										"车牌号",
										"是否挂牌号",
										"出发部门",
										"出发时间",
										"到达部门",
										"外发代理",
										"到达时间",
										"总票数",
										"总件数",
										"总重量",
										"总体积",
										"总金额",
										//BUG-57558   begin
										"代收货款总额",
										//BUG-57558   end
										"空运票数",
										"空运重量",
										"空运体积",
										"卡航票数",
										"卡航重量",
										"卡航体积",
										"城运票数",
										"城运重量",
										"城运体积",
										"制单人",
										"司机"
										};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeads);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("交接单列表");
	    //设置sheet行数
	    exportSetting.setSize(handOverBillList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName ="交接单";
		try {
			String agent = (String) ServletActionContext.getRequest().getHeader(
    				"USER-AGENT");

    		if (agent != null && agent.indexOf("MSIE") == -1) {
    			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
    		} else {
    			fileName = URLEncoder.encode(fileName, "UTF-8");
    		}
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	/**
	 * 私有方法，用于根据状态code转换为具体的值
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:32:33
	 */
	private String convertStateNumber2Value(int state){
		switch(state){
			case LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER:
				return "已预配";
			case LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER:
				return "已交接";
			case LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE:
				return "已配载";
			case LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART:
				return "已出发";
			case LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE:
				return "已到达";
			case LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK:
				return "已入库";
			default:
				return "已作废";
		}
	}

	@Override
	public List<HandOverBillSerialNoDetailEntity> getHandOverBillSerialNoDetailsByWayBillNo( String wayBillNo, String handOverBillNo) {
		
		BigDecimal param = null;
		try{
			String currentDeptCode = this.querySuperiorOrgCode();
			param =	queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积参数失败！");
		}
		//没上计泡机的流水号需要计算重泡比
		List<HandOverBillSerialNoDetailEntity> serialNoList= handOverBillDao.getHandOverBillSerialNoDetailsByWayBillNo(wayBillNo, handOverBillNo);
		if(param!=null){
			for(HandOverBillSerialNoDetailEntity serialNoEntity:serialNoList){
				if((StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_PACKAGE)
						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_RCP)
						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_EPEP))
						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
						){
					BigDecimal conVolumn =param.multiply(serialNoEntity.getWeight()==null?new BigDecimal(0):serialNoEntity.getWeight()).divide(new BigDecimal(LoadConstants.SONAR_NUMBER_1000), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					serialNoEntity.setVolumn(conVolumn);	
				}
				 
			}
		}
		
		return serialNoList;
	}
	/**
	 * 
	 * <p>快递空运交接单 查询运单下的流水号</p> 
	 * @author 189284 
	 * @date 2015-9-22 上午9:52:32
	 * @param wayBillNo
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	@Override
	public List<HandOverBillSerialNoDetailEntity> getAirHandOverBillSerialNoDetailsByWayBillNo( String wayBillNo, String handOverBillNo) {
		
		BigDecimal param = null;
		try{
			String currentDeptCode = this.querySuperiorOrgCode();
			param =	queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积参数失败！");
		}
		if(StringUtils.isEmpty(wayBillNo)){
			 throw new TfrBusinessException("运单号不存在，无流水！"); 
		}
		//没上计泡机的流水号需要计算重泡比
		List<HandOverBillSerialNoDetailEntity> serialNoList= handOverBillDao.getAirHandOverBillSerialNoDetailsByWayBillNo(wayBillNo, handOverBillNo);
		/**
		* 获取所有的快递3级产品code
		* zx
		* 2015-09-06
		* */
		List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
		if(param!=null){
			for(HandOverBillSerialNoDetailEntity serialNoEntity:serialNoList){
				if(CollectionUtils.isNotEmpty(productCodeList)
						&&productCodeList.contains(serialNoEntity.getProductCode())
						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
						){
//				if((StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_PACKAGE)
//						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_RCP)
//						||StringUtils.equals(serialNoEntity.getProductCode(), LoadConstants.PRODUCT_CODE_EPEP))
//						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
//						){
					BigDecimal conVolumn =param.multiply(serialNoEntity.getWeight()==null?new BigDecimal(0):serialNoEntity.getWeight()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					serialNoEntity.setVolumn(conVolumn);	
				}
				 
			}
		}
		
		return serialNoList;
	}

	@Override
	public int addHandoverbillPrintrecord(
			HandoverBillPrintRecordEntity handoverBillPrintRecord) {
		return handOverBillDao.addHandoverbillPrintrecord(handoverBillPrintRecord);
	}

	/**
	 * 打印交接单时校验是否已选中改车牌下所有的交接单 
	 * @author ibm-zhangyixin
	 * @date 2012-11-2 下午2:23:49
	 * @param handOverBillVo
	 * @return Long
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#checkPrintHandOverBill(com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo)
	 */
	@Override
	public Long checkPrintHandOverBill(HandOverBillVo handOverBillVo) {
		//打印交接单时校验是否已选中改车牌下所有的交接单 
		return handOverBillDao.checkPrintHandOverBill(handOverBillVo);
	}

	/**
	 * 修改交接单保存数据service
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 上午9:53:52
	 * @param updateHandOverBillDto交接单修改数据dto
	 * @return boolean
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillDto)
	 */
	@Transactional
	@Override
	public boolean updateHandOverBill(
			UpdateHandOverBillDto updateHandOverBillDto) {
		/**
		 * 只能修改符合以下条件的交接单：
		 * 1、	“出发部门”为本部门；
		 * 2、	交接单状态为“已预配”、“已交接”或“已出发”；
		 */
		/**
		 * 如果为PDA生成的交接单
		 * 1、分别delete、update被删除的、被删减流水号的运单；
		 * 2、delete被删掉的流水号；
		 * 3、将被删除的流水号在本部门入库；
		 * 4、调用走货路径接口；
		 * 5、记录交接单修改日志；
		 * 6、如果车牌号被修改，并且交接单已生成任务车辆明细，则调用任务车辆接口
		 */
		/**
		 * 如果为非PDA生成的交接单
		 * 一）如果修改前为预配交接单，修改后为预配交接单
		 * 1、分别delete、update、insert被删除的、被删减流水号的、新增加的运单；
		 * 2、delete被删掉的流水号；
		 * 3、若删除的流水号为在库流水号，则更新流水号库存的“预配状态”为空；
		 * 4、insert新增的流水号；
		 * 5、若新增的流水号为在库流水号，则更新流水号库存的“预配状态”为PRE_HANDOVER；
		 * 二）如果修改前为预配交接单，修改后为正式交接单
		 * 1、分别delete、update、insert被删除的、被删减流水号的、新增加的运单；
		 * 2、delete被删掉的流水号；
		 * 3、insert新增的流水号；
		 * 4、调用库存接口，出库所有流水号；
		 * 5、调用走货路径接口；
		 * 三）如果修改前为正式交接单，修改后为正式交接单
		 * 1、分别delete、update、insert被删除的、被删减流水号的、新增加的运单；
		 * 2、delete被删掉的流水号；
		 * 3、将删掉的流水号在本部门入库；
		 * 4、insert新增的流水号；
		 * 5、将新增的流水号从本部门出库；
		 * 6、如果车牌号被修改，并且交接单已生成任务车辆明细，则调用任务车辆接口；
		 * 7、调用走货路径接口；
		 * 8、记录交接单修改日志；
		 * 
		 * BUG-46488要求非整车交接单不能添加整车运单.
		 */
		//获取修改后的交接单基本信息实体
		HandOverBillEntity nowHandOverBillEntity = updateHandOverBillDto.getHandOverBillEntity();
		
		System.out.println(nowHandOverBillEntity.getIsAgencyVisit());
		
		//获取“是否PDA生成”
		String isCreatedByPDA = nowHandOverBillEntity.getIsCreatedByPDA();
		//更新非PDA生成的交接单
		HandOverBillEntity handoverBill = queryHandOverBillByNo(nowHandOverBillEntity.getHandOverBillNo());
		
		List<HandOverBillDetailEntity> handoverBillDetails = updateHandOverBillDto.getAllWaybillList();
		
		String handOverBillDetailsType = getHandOverBillDetailsType(handoverBillDetails);
		String handOverBillNo = nowHandOverBillEntity.getHandOverBillNo();
	
		//如果是代理上门接货就不需要校验司机信息，否则需要。另外：extjs 的 checkbox 取值 为 on,营业部交接跳过此验证
		if(!StringUtils.equals(nowHandOverBillEntity.getIsAgencyVisit(), "on")
				&& !StringUtils.equals(nowHandOverBillEntity.getHandOverType(),LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
			if(StringUtils.isBlank(nowHandOverBillEntity.getDriver())){
				throw new TfrBusinessException("请输入司机信息！");
			}
			if(StringUtils.isBlank(nowHandOverBillEntity.getVehicleNo())){
				throw new TfrBusinessException("请输入车牌号！");
			}
		}
		String currentOrgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity org = loadService.querySuperOrgByOrgCode(currentOrgCode);
		
		if(StringUtils.equals(org.getExpressBranch(),"Y")&&StringUtils.equals(nowHandOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)){
			if(!StringUtils.equals(nowHandOverBillEntity.getVehicleNo().substring(0, 1),"德")){
				throw new TfrBusinessException("分部到点部交接时，车牌必须为虚拟车牌");
			}	
		}
		 
		String  holdingState=this.queryHoldingState(handOverBillNo);
		if(holdingState.equals("Y")){
			throw new TfrBusinessException("交接单生成的临时租车已付款、已预提或在预提中，不允许修改！");
		}
		if(handOverBillNo.startsWith(LoadConstants.HANDOVERBILL_EXPRESS_PREFIX)){
			if(LoadConstants.HANDOVERBILL_DETAILS_TYPE_LTL.equals(handOverBillDetailsType)){
				throw new TfrBusinessException("交接单为快递交接单，不允许将交接单明细的运单修改为零担货！");
			}
		}else if(LoadConstants.HANDOVERBILL_DETAILS_TYPE_EXPRESS.equals(handOverBillDetailsType) && !handOverBillNo.startsWith(LoadConstants.HANDOVERBILL_EXPRESS_PAKAGE)){
			throw new TfrBusinessException("交接单为零担交接单，不允许将交接单明细的运单修改为快递货！");
		}
		//如果当前交接单为非整车交接单
		if(StringUtils.equals(FossConstants.NO, handoverBill.getIsCarLoad())) {
			if(!CollectionUtils.isEmpty(handoverBillDetails)) {
				for(HandOverBillDetailEntity handOverBillDetail : handoverBillDetails) {
					if(StringUtils.equals(handOverBillDetail.getIsCarLoad(), FossConstants.YES)) {
						throw new TfrBusinessException("运单" + handOverBillDetail.getWaybillNo() + "为整车, 当前交接单为非整车不能交接!");
					}
				}
			}
		} else {
			if(!CollectionUtils.isEmpty(handoverBillDetails)) {
				for(HandOverBillDetailEntity handOverBillDetail : handoverBillDetails) {
					if(StringUtils.equals(handOverBillDetail.getIsCarLoad(), FossConstants.NO)) {
						throw new TfrBusinessException("运单" + handOverBillDetail.getWaybillNo() + "为非整车, 当前交接单为整车不能交接!");
					}
				}
			}
		}
		
		if(StringUtils.equals(isCreatedByPDA, FossConstants.NO)){
			updateNoPDAHandOverBill(updateHandOverBillDto);
		}else{//更新PDA生成的
			updatePDAHandOverBill(updateHandOverBillDto);
		}
		return true;
	}
	
	/**
	 * 私有方法，更新非PDA生成的交接单
	 * @author 045923-foss-shiwei
	 * @param updateHandOverBillDto
	 * @return void
	 * @exception 无
	 * @date 2012-11-3 下午4:36:07
	 */
	@SuppressWarnings("unchecked")
	private void updateNoPDAHandOverBill(UpdateHandOverBillDto updateHandOverBillDto){
			//获取当前用户名、工号、部门code
			String userName = FossUserContext.getCurrentInfo().getEmpName();
			//当前用户code
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();
			//当前登录部门code
			String deptCode = this.querySuperiorOrgCode();
			OrgAdministrativeInfoEntity org = this.orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
			//获取修改后的交接单基本信息实体
			HandOverBillEntity nowHandOverBillEntity = updateHandOverBillDto.getHandOverBillEntity();
			//获取交接单号
			String handOverBillNo = nowHandOverBillEntity.getHandOverBillNo();
			//为修改后的交接单补充其他属性
			addFieldsValueForHandOverBill(nowHandOverBillEntity,updateHandOverBillDto);
			//获取新增的运单Map
			Map<String,HandOverBillDetailEntity> addedWaybillMap = updateHandOverBillDto.getAddedWaybillMap();
			//获取删除的运单Map
			Map<String,HandOverBillDetailEntity> deletedWaybillMap = updateHandOverBillDto.getDeletedWaybillMap();
			//获取被修改的运单Map
			Map<String,HandOverBillDetailEntity> updatedWaybillMap = updateHandOverBillDto.getUpdatedWaybillMap();
			//获取被删除的流水号list
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList = updateHandOverBillDto.getDeletedSerialNoList();
			//获取新增的流水号list
			List<HandOverBillSerialNoDetailEntity> addedSerialNoList = updateHandOverBillDto.getAddedSerialNoList();
			//获取原来的交接单基本信息实体
			HandOverBillEntity oldHandOverBillEntity = handOverBillDao.queryHandOverBillByNo(handOverBillNo);
			nowHandOverBillEntity.setDepartDeptCode(oldHandOverBillEntity.getDepartDeptCode());
			
			//车辆所属类型
			String truckOwner ="";
			//如果车牌号或者司机被修改，则校验司机、车辆的所有权是否一致
			
			if(!StringUtils.equals(oldHandOverBillEntity.getVehicleNo(), nowHandOverBillEntity.getVehicleNo())
					|| !StringUtils.equals(oldHandOverBillEntity.getDriver(), nowHandOverBillEntity.getDriver())){
				truckOwner=this.validateVehicleAndDriverOwnerShip(nowHandOverBillEntity,org);
			}			
			
			//如果车牌号被修改，则校验车牌号
			if(!StringUtils.equals(oldHandOverBillEntity.getVehicleNo(), nowHandOverBillEntity.getVehicleNo())){
				//校验车牌号是否可用(集配交接单、是挂牌号且不是整车的 不需要校验--整车的不论是否挂牌都需要校验)
				if(!(StringUtils.equals(oldHandOverBillEntity.getIsCarLoad(), FossConstants.NO)&&StringUtils.equals(nowHandOverBillEntity.getBeTrailerVehicleNo(),FossConstants.YES)
						&&StringUtils.equals(nowHandOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
						&&!StringUtils.equals(truckOwner, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED))&&
						StringUtils.isNotBlank(nowHandOverBillEntity.getVehicleNo())){
						//校验车牌号是否可用
						//是否存在有未校验封签的记录
						loadService.validateVehicleNoCanBeUsed(nowHandOverBillEntity.getVehicleNo());
						//校验其他部门是否有使用该车辆并且未出发的记录
						loadService.queryUndepartRecByVehicleNo(nowHandOverBillEntity.getDepartDeptCode(), nowHandOverBillEntity.getVehicleNo(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
						LOGGER.error("交接单号：" + handOverBillNo + "校验车牌号结束");
						nowHandOverBillEntity.setBeTrailerVehicleNo(null);
				}
			}
			
		
			
			//BUG-56959   如果为集配, 短配交接单，且状态为“已配载”或以后的状态，则车牌号不能修改；
			String handOverType= oldHandOverBillEntity.getHandOverType();
			int handOverBillState = oldHandOverBillEntity.getHandOverBillState();
			if((StringUtils.equals(handOverType,LoadConstants.HANDOVER_TYPE_LONG_DISTANCE) || StringUtils.equals(handOverType,LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE))
					&& handOverBillState != LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER 
					&& handOverBillState != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
					if(!StringUtils.equals(oldHandOverBillEntity.getVehicleNo(), nowHandOverBillEntity.getVehicleNo()) ){	
						throw new TfrBusinessException("交接单已出发，无法修改车牌号");
					}
					if(!StringUtils.equals(oldHandOverBillEntity.getDriver(),nowHandOverBillEntity.getDriver()) ){
						throw new TfrBusinessException("交接单已出发，无法修改司机");
					}
			}
			
			//为新增的流水号补充缺失属性
			addFieldsValueForAddedSerialNoList(addedSerialNoList,oldHandOverBillEntity);
			//获取提交来的所有的流水号list
			List<HandOverBillSerialNoDetailEntity> allSerialNoList = updateHandOverBillDto.getAllSerialNoList();//出库
			//将待删除的运单map转化为list
			List<HandOverBillDetailEntity> deletedWaybillList = convertMap2List(nowHandOverBillEntity,deletedWaybillMap, 0);
			//将待新增的运单map转化为list
			List<HandOverBillDetailEntity> addedWaybillList = convertMap2List(nowHandOverBillEntity,addedWaybillMap, 1);
			//将待更新的运单map转化为list
			List<HandOverBillDetailEntity> updatedWaybillList = convertMap2List(nowHandOverBillEntity,updatedWaybillMap, 2);
			//将删除的流水号list转化为map
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap = convertList2Map(deletedSerialNoList);
			//将新增的流水号list转化为map
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> addedSerialNoMap = convertList2Map(addedSerialNoList);
			//获取修改后的所有运单List
			List<HandOverBillDetailEntity> allWaybillList = updateHandOverBillDto.getAllWaybillList();
			//如果运单有增删，则重新计算优先货票数
			if(!CollectionUtils.isEmpty(deletedWaybillList) || !CollectionUtils.isEmpty(addedWaybillList)){
				nowHandOverBillEntity.setFastWaybillQty(oldHandOverBillEntity.getFastWaybillQty() + nowHandOverBillEntity.getFastWaybillQty());
			}else{
				nowHandOverBillEntity.setFastWaybillQty(oldHandOverBillEntity.getFastWaybillQty());
			}
			//如果流水号有增删，则重新计算卡航、空运、城际统计信息
			if(CollectionUtils.isNotEmpty(addedSerialNoList) || CollectionUtils.isNotEmpty(deletedSerialNoList)){
				this.calculateStaInfoForUpdateHandOverBill(nowHandOverBillEntity, allWaybillList);
			}
			//如果不是外发交接单，且非整车交接单，则读取发车计划
			if(!StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, nowHandOverBillEntity.getHandOverType())
					&& !StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, nowHandOverBillEntity.getHandOverType())
					&& StringUtils.equals(oldHandOverBillEntity.getIsCarLoad(), FossConstants.NO)){
				//获取发车计划信息
				TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(deptCode, oldHandOverBillEntity.getArriveDeptCode(), nowHandOverBillEntity.getVehicleNo(),null);
				if(planDetail != null){
					// 发车计划明细ID
					nowHandOverBillEntity.setTruckDepartPlanDetailId(planDetail.getId());
					// 线路名称
					nowHandOverBillEntity.setLineName(planDetail.getLineName());
					//线路虚拟编码
					nowHandOverBillEntity.setLineCode(planDetail.getLineNo());
					//班次
					nowHandOverBillEntity.setFrequencyNo(planDetail.getFrequencyNo());
				}
			}
			//获取修改前和修改后的交接单状态
			int oldState = oldHandOverBillEntity.getHandOverBillState();
			//已预配
			if(oldState != LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER
					//已交接
					&& oldState != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER
					//已出发
					&& oldState != LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART
					//已配载
					&& oldState != LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE){
				throw new TfrBusinessException("交接单已到达或者已作废，无法修改");
			}
			//上一状态为已预配
			if(oldState == LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER){
				handOverBillDao.updateHandOverBill(nowHandOverBillEntity, 
						deletedWaybillList, 
						addedWaybillList, 
						updatedWaybillList, 
						deletedSerialNoList, 
						addedSerialNoList, 
						null);
				//如果上一状态为已预配，则只更新交接单数据，无须记录日志
				//如果修改后的状态为非预配，则需出库货物
				if(StringUtils.equals(FossConstants.NO,nowHandOverBillEntity.getIsPreHandOverBill())){
					for(HandOverBillSerialNoDetailEntity serialNo : allSerialNoList){
						InOutStockEntity outStockEntity = new InOutStockEntity();
						//运单号
						outStockEntity.setWaybillNO(serialNo.getWaybillNo());
						//流水号
						outStockEntity.setSerialNO(serialNo.getSerialNo());
						//部门code
						outStockEntity.setOrgCode(deptCode);
						//操作人code
						outStockEntity.setOperatorCode(userCode);
						//操作人name
						outStockEntity.setOperatorName(userName);
						//手动交接出库
						outStockEntity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
						//交接单号
						outStockEntity.setInOutStockBillNO(handOverBillNo);
						//调用接口，出库
						int outStockResult = stockService.outStockPC(outStockEntity);
						if(outStockResult == FossConstants.FAILURE){
							if(isNeedOutStockSale(outStockEntity)){
								throw new TfrBusinessException("运单号：" + outStockEntity.getWaybillNO() + "，流水号：" + outStockEntity.getSerialNO() + "不在本部门库存");
							}
						}
					}
					//更新该交接单状态为“已交接”
					this.updateHandOverBillStateByNo(handOverBillNo, LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
				}else{
					/**
					 * 如果修改后的交接单仍然为预配交接单
					 * 1、将添加的在库的流水号库存的预配状态修改为“PRE_HANDOVER”；
					 * 2、将删除的在库的流水号库存的预配状态修改为null；
					 */
					List<UpdateStockPreHandOverStateDto> addDtoList = new ArrayList<UpdateStockPreHandOverStateDto>();
					//获取添加的在库流水号
					for(HandOverBillSerialNoDetailEntity serialNo : addedSerialNoList){
						if(serialNo.getIsInStorage() == 1){
							UpdateStockPreHandOverStateDto dto = new UpdateStockPreHandOverStateDto();
							//部门code
							dto.setDeptCode(deptCode);
							//运单号
							dto.setWaybillNo(serialNo.getWaybillNo());
							//流水号
							dto.setSerialNo(serialNo.getSerialNo());
							addDtoList.add(dto);
						}
					}
					//更新添加的流水号的预配状态
					if(addDtoList.size() > 0){
						stockService.updatePreHandOverState(addDtoList, StockConstants.PRE_HANDOVER_STATUS);
					}
				}
				List<UpdateStockPreHandOverStateDto> deleteDtoList = new ArrayList<UpdateStockPreHandOverStateDto>();
				//获取删除的在库流水号，将删除的在库流水号的预配状态置空
				for(HandOverBillSerialNoDetailEntity serialNo : deletedSerialNoList){
					UpdateStockPreHandOverStateDto dto = new UpdateStockPreHandOverStateDto();
					//部门code
					dto.setDeptCode(deptCode);
					//运单号
					dto.setWaybillNo(serialNo.getWaybillNo());
					//流水号
					dto.setSerialNo(serialNo.getSerialNo());
					deleteDtoList.add(dto);
				}
				if(deleteDtoList.size() > 0){
					stockService.updatePreHandOverState(deleteDtoList, null);
				}
			}else{
				//正式交接单，一次最多修改十条(可配置)
				ConfigurationParamsEntity countEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
						ConfigurationParamsConstants.TFR_PARM_HANDOVERBILL_CANBEMODIFIED_WAYBILL_COUNT, 
						FossConstants.ROOT_ORG_CODE);
				if(countEntity != null && countEntity.getConfValue() != null){
					int count = Integer.parseInt(countEntity.getConfValue());
					if(addedWaybillList.size() + deletedWaybillList.size() + updatedWaybillList.size() > count){
						throw new TfrBusinessException("正式交接单一次只能操作" + count + "条运单！");
					}
				}
				//需要重新入库删除的流水号  deletedSerialNoList
				for(HandOverBillSerialNoDetailEntity serialNo : deletedSerialNoList){
					InOutStockEntity inStockEntity = new InOutStockEntity();
					//运单号
					inStockEntity.setWaybillNO(serialNo.getWaybillNo());
					//流水号
					inStockEntity.setSerialNO(serialNo.getSerialNo());
					//入库类型
					inStockEntity.setInOutStockType(StockConstants.MODIFY_HANDOVERBILL_DELETE_SERIALNO);
					//操作人code
					inStockEntity.setOperatorCode(userCode);
					//操作人name
					inStockEntity.setOperatorName(userName);
					//部门code
					inStockEntity.setOrgCode(deptCode);
					//调用库存服务，重新在本部门入库
					stockService.inStockPC(inStockEntity);
				}
				//需要将新增的流水号出库  addedSerialNoList
				for(HandOverBillSerialNoDetailEntity serialNo : addedSerialNoList){
					InOutStockEntity entity = new InOutStockEntity();
					//运单号
					entity.setWaybillNO(serialNo.getWaybillNo());
					//流水号
					entity.setSerialNO(serialNo.getSerialNo());
					//出库类型
					entity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
					//操作人code
					entity.setOperatorCode(userCode);
					//操作人name
					entity.setOperatorName(userName);
					//部门code
					entity.setOrgCode(deptCode);
					//交接单号
					entity.setInOutStockBillNO(handOverBillNo);
					//调用库存服务，从本部门出库
					int outStockResult = stockService.outStockPC(entity);
					if(outStockResult == FossConstants.FAILURE){
						if(isNeedOutStockSale(entity)){
						throw new TfrBusinessException("运单号：" + entity.getWaybillNO() + "，流水号：" + entity.getSerialNO() + "不在本部门库存");
						}
					}
				}
				//如果交接单中运单或者流水号发生增删，调用走货路径接口
				if(deletedSerialNoList.size() != 0 || addedSerialNoList.size() != 0){
					try{
//						loadService.callTransportPathService(nowHandOverBillEntity,allWaybillList, deletedSerialNoList, allSerialNoMap);
					}catch(Exception e){
						LOGGER.error("交接单号：" + handOverBillNo + "调用走货路径接口出现异常，异常信息：" + e.getMessage());
					}
				}
				//上一状态为非预配，则需根据提交来的数据对比输出修改日志，并保存；
				List<HandOverBillOptLogEntity> logList = outputLogList(nowHandOverBillEntity,
						oldHandOverBillEntity,
						addedWaybillMap,
						deletedWaybillMap,
						updatedWaybillMap,
						deletedSerialNoMap,
						addedSerialNoMap);
				//调用dao，修改交接单
				handOverBillDao.updateHandOverBill(nowHandOverBillEntity, 
						deletedWaybillList,
						addedWaybillList, 
						updatedWaybillList, 
						deletedSerialNoList,
						addedSerialNoList,
						logList);
			}
			//如果车牌号被修改，则需调用任务车辆接口，必须放在最后
			if(!StringUtils.equals(nowHandOverBillEntity.getVehicleNo(), oldHandOverBillEntity.getVehicleNo())){
				//如果已生成任务车辆明细，则调用任务车辆接口
				truckTaskService.handOverBillUpdateVehicleNo(handOverBillNo);
				truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
			}
	}
	
	/**
	 * 私有方法，更新PDA生成的交接单
	 * @author 045923-foss-shiwei
	 * @param updateHandOverBillDto
	 * @return void
	 * @exception 无
	 * @date 2012-11-3 下午4:46:14
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void updatePDAHandOverBill(UpdateHandOverBillDto updateHandOverBillDto){
		//获取当前用户名、工号、部门code
		String userName = FossUserContext.getCurrentInfo().getEmpName();
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
		String deptCode = this.querySuperiorOrgCode();
		OrgAdministrativeInfoEntity org = this.orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		//获取修改后的交接单基本信息实体
		HandOverBillEntity nowHandOverBillEntity = updateHandOverBillDto.getHandOverBillEntity();
		//获取交接单号
		String handOverBillNo = nowHandOverBillEntity.getHandOverBillNo();
		//为修改后的交接单补充其他属性
		addFieldsValueForHandOverBill(nowHandOverBillEntity,updateHandOverBillDto);
		//获取删除的运单Map
		Map<String,HandOverBillDetailEntity> deletedWaybillMap = updateHandOverBillDto.getDeletedWaybillMap();
		//获取被修改的运单Map
		Map<String,HandOverBillDetailEntity> updatedWaybillMap = updateHandOverBillDto.getUpdatedWaybillMap();
		//获取被删除的流水号list
		List<HandOverBillSerialNoDetailEntity> deletedSerialNoList = updateHandOverBillDto.getDeletedSerialNoList();
		//获取提交来的所有的流水号list
		//未使用-352203-sonar
//		List<HandOverBillSerialNoDetailEntity> allSerialNoList = updateHandOverBillDto.getAllSerialNoList();//出库
		//获取原来的交接单基本信息实体
		HandOverBillEntity oldHandOverBillEntity = handOverBillDao.queryHandOverBillByNo(handOverBillNo);
		nowHandOverBillEntity.setDepartDeptCode(oldHandOverBillEntity.getDepartDeptCode());
		nowHandOverBillEntity.setArriveDeptCode(oldHandOverBillEntity.getArriveDeptCode());
		//车辆所属类型
		String truckOwner="";
		
		if(!StringUtils.equals(oldHandOverBillEntity.getVehicleNo(), nowHandOverBillEntity.getVehicleNo())
				|| !StringUtils.equals(oldHandOverBillEntity.getDriver(), nowHandOverBillEntity.getDriver())){
			truckOwner=this.validateVehicleAndDriverOwnerShip(nowHandOverBillEntity, org);
		}			
		
		//如果车牌号被修改，则校验车牌号
		if(!StringUtils.equals(oldHandOverBillEntity.getVehicleNo(), nowHandOverBillEntity.getVehicleNo())){
			//校验车牌号是否可用(集配交接单、是挂牌号且不是整车的 不需要校验--整车和外请车的不论是否挂牌都需要校验)
			if(!(StringUtils.equals(oldHandOverBillEntity.getIsCarLoad(), FossConstants.NO)&&StringUtils.equals(nowHandOverBillEntity.getBeTrailerVehicleNo(),FossConstants.YES)
					&&StringUtils.equals(nowHandOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
					&&!StringUtils.equals(truckOwner, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED))&&
					StringUtils.isNotBlank(nowHandOverBillEntity.getVehicleNo())){
				//校验车牌号是否可用
				//是否存在有未校验封签的记录
				loadService.validateVehicleNoCanBeUsed(nowHandOverBillEntity.getVehicleNo());
				//校验其他部门是否有使用该车辆并且未出发的记录
				loadService.queryUndepartRecByVehicleNo(nowHandOverBillEntity.getDepartDeptCode(), nowHandOverBillEntity.getVehicleNo(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
				LOGGER.error("交接单号：" + handOverBillNo + "校验车牌号结束");
				nowHandOverBillEntity.setBeTrailerVehicleNo(null);
			}
		}
		
		int oldState = oldHandOverBillEntity.getHandOverBillState();
		//已预配
		if(oldState != LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER
				//已交接
				&& oldState != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER
				//已出发
				&& oldState != LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART
				//已配载
				&& oldState != LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE){
			//抛异常，中断
			throw new TfrBusinessException("交接单已到达或者已作废，无法修改");
		}
		//将待删除的运单map转化为list
		List<HandOverBillDetailEntity> deletedWaybillList = convertMap2List(nowHandOverBillEntity,deletedWaybillMap,0);
		//将待更新的运单map转化为list
		List<HandOverBillDetailEntity> updatedWaybillList = convertMap2List(nowHandOverBillEntity,updatedWaybillMap,2);
		//正式交接单，一次最多修改十条(可配置)
		ConfigurationParamsEntity countEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_HANDOVERBILL_CANBEMODIFIED_WAYBILL_COUNT, 
				FossConstants.ROOT_ORG_CODE);
		if(countEntity != null && countEntity.getConfValue() != null){
			int count = Integer.parseInt(countEntity.getConfValue());
			if(deletedWaybillList.size() + updatedWaybillList.size() > count){
				throw new TfrBusinessException("正式交接单一次只能操作" + count + "条运单！");
			}
		}
		//获取修改后的所有运单List
		List<HandOverBillDetailEntity> allWaybillList = updateHandOverBillDto.getAllWaybillList();
		//将删除的流水号list转化为map
		Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap = convertList2Map(deletedSerialNoList);
		//将所有的流水号list转化为map
		//sonar 311396 下面没有使用
//		Map<String,Map<String,HandOverBillSerialNoDetailEntity>> allSerialNoMap = convertList2Map(allSerialNoList);
		//重新计算优先货票数
		if(CollectionUtils.isNotEmpty(deletedWaybillList)){
			nowHandOverBillEntity.setFastWaybillQty(oldHandOverBillEntity.getFastWaybillQty() + nowHandOverBillEntity.getFastWaybillQty());
		}else{
			nowHandOverBillEntity.setFastWaybillQty(oldHandOverBillEntity.getFastWaybillQty());
		}
		//如果流水号有增删，则重新计算卡航、空运、城际统计信息
		if(CollectionUtils.isNotEmpty(deletedSerialNoList)){
			this.calculateStaInfoForUpdateHandOverBill(nowHandOverBillEntity, allWaybillList);
		}
		//如果不是外发交接单，且非整车交接单，则读取发车计划
		if(!StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, nowHandOverBillEntity.getHandOverType())
				&& !StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, nowHandOverBillEntity.getHandOverType())
				&& StringUtils.equals(oldHandOverBillEntity.getIsCarLoad(), FossConstants.NO)){
			//获取发车计划信息
			TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(deptCode, oldHandOverBillEntity.getArriveDeptCode(), nowHandOverBillEntity.getVehicleNo(),null);
			if(planDetail != null){
				// 发车计划明细ID
				nowHandOverBillEntity.setTruckDepartPlanDetailId(planDetail.getId());
				// 线路名称
				nowHandOverBillEntity.setLineName(planDetail.getLineName());
				//线路虚拟编码
				nowHandOverBillEntity.setLineCode(planDetail.getLineNo());
				//班次
				nowHandOverBillEntity.setFrequencyNo(planDetail.getFrequencyNo());
			}
		}
		//重新入库删除的流水号
		for(HandOverBillSerialNoDetailEntity serialNo : deletedSerialNoList){
			InOutStockEntity inStockEntity = new InOutStockEntity();
			//运单号
			inStockEntity.setWaybillNO(serialNo.getWaybillNo());
			//流水号
			inStockEntity.setSerialNO(serialNo.getSerialNo());
			//入库类型
			inStockEntity.setInOutStockType(StockConstants.MODIFY_HANDOVERBILL_DELETE_SERIALNO);
			//操作人code
			inStockEntity.setOperatorCode(userCode);
			//操作人name
			inStockEntity.setOperatorName(userName);
			//部门code
			inStockEntity.setOrgCode(deptCode);
			//调用库存服务，重新在本部门入库
			stockService.inStockPC(inStockEntity);
		}
		//调用走货路径接口
		if(CollectionUtils.isNotEmpty(deletedSerialNoList)){
			try{
//				loadService.callTransportPathService(nowHandOverBillEntity,allWaybillList, deletedSerialNoList, allSerialNoMap);
			}catch(Exception e){
				LOGGER.error("交接单号：" + handOverBillNo + "调用走货路径接口出现异常，异常信息：" + e.getMessage());
			}
		}
		//生成修改日志
		List<HandOverBillOptLogEntity> logList = outputLogList(nowHandOverBillEntity,
				oldHandOverBillEntity,
				new HashMap<String,HandOverBillDetailEntity>(),
				deletedWaybillMap,
				updatedWaybillMap,
				deletedSerialNoMap,
				new HashMap<String,Map<String,HandOverBillSerialNoDetailEntity>>());
		//更新交接单信息，记录日志
		handOverBillDao.updateHandOverBill(nowHandOverBillEntity, 
				deletedWaybillList, 
				null, 
				updatedWaybillList, 
				deletedSerialNoList, 
				null, 
				logList);
		//如果车牌号被修改，则需调用任务车辆接口，必须放在最后
		if(!StringUtils.equals(nowHandOverBillEntity.getVehicleNo(), oldHandOverBillEntity.getVehicleNo())){
			//如果已生成任务车辆明细，则调用任务车辆接口
			truckTaskService.handOverBillUpdateVehicleNo(handOverBillNo);
			truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
		}
	}
	
	/**
	 * 修改交接单时，重新统计卡货、空运、城际的票数、重量、体积
	 * @author 045923-foss-shiwei
	 * @date 2013-6-27 下午2:45:03
	 */
	private void calculateStaInfoForUpdateHandOverBill(HandOverBillEntity billEntity,List<HandOverBillDetailEntity> waybillList){
		Integer waybillQtyAF = 0,waybillQtyFLF = 0,waybillQtyFSF = 0,waybillQtyBGFLF = 0, waybillQtyBGFSF = 0;
		BigDecimal weightAF = BigDecimal.ZERO,volumeAF = BigDecimal.ZERO,
				weightFLF = BigDecimal.ZERO,volumeFLF = BigDecimal.ZERO,
				weightFSF = BigDecimal.ZERO,volumeFSF = BigDecimal.ZERO,
				weightBGFLF = BigDecimal.ZERO,weightBGFSF = BigDecimal.ZERO,
				volumeBGFLF = BigDecimal.ZERO,volumeBGFSF = BigDecimal.ZERO;
		for(HandOverBillDetailEntity waybill : waybillList){
			if(StringUtils.equals(waybill.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_AF)){
				waybillQtyAF += 1;
				weightAF = weightAF.add(waybill.getWeight());
				volumeAF = volumeAF.add(waybill.getCubage());
			}else if(StringUtils.equals(waybill.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FLF)){
				waybillQtyFLF += 1;
				weightFLF = weightFLF.add(waybill.getWeight());
				volumeFLF = volumeFLF.add(waybill.getCubage());
			}else if(StringUtils.equals(waybill.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_FSF)){
				waybillQtyFSF += 1;
				weightFSF = weightFSF.add(waybill.getWeight());
				volumeFSF = volumeFSF.add(waybill.getCubage());
			//如果为精准大票卡航
			}else if(StringUtils.equals(waybill.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_BGFLF)){
				waybillQtyBGFLF +=1;
				weightBGFLF = weightBGFLF.add(waybill.getWeight());
				volumeBGFLF = volumeBGFLF.add(waybill.getCubage());
			//如果为精准大票城运
			}else if(StringUtils.equals(waybill.getTransPropertyCode(), LoadConstants.PRODUCT_CODE_BGFSF)){
				waybillQtyBGFSF +=1;
				weightBGFSF = weightBGFSF.add(waybill.getWeight());
				volumeBGFSF = volumeBGFSF.add(waybill.getCubage());
			}
		}
		billEntity.setWaybillQtyAF(waybillQtyAF);
		billEntity.setWaybillQtyFLF(waybillQtyFLF);
		billEntity.setWaybillQtyFSF(waybillQtyFSF);
		billEntity.setGoodsVolumeAF(volumeAF);
		billEntity.setGoodsVolumeFLF(volumeFLF);
		billEntity.setGoodsVolumeFSF(volumeFSF);
		billEntity.setGoodsWeightAF(weightAF);
		billEntity.setGoodsWeightFLF(weightFLF);
		billEntity.setGoodsWeightFSF(weightFSF);
		//精准大票
		billEntity.setWaybillQtyBGFLF(waybillQtyBGFLF);
		billEntity.setWaybillQtyBGFSF(waybillQtyBGFSF);
		billEntity.setGoodsVolumeBGFLF(volumeBGFLF);
		billEntity.setGoodsVolumeBGFSF(volumeBGFSF);
		billEntity.setGoodsWeightBGFLF(weightBGFLF);
		billEntity.setGoodsVolumeBGFSF(volumeBGFSF);
		//billEntity.setFastWaybillQty(waybillQtyFLF+waybillQtyFSF+waybillQtyAF); 前面有统计优先货规则，且本次统计有误
	}
	
	/**
	 * 私有方法，为更新后的交接单基本信息补充属性
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 下午10:09:54
	 * @param nowHandOverBillEntity
	 * @param updateHandOverBillDto
	 * @return void
	 * @exception 无
	 */
	private void addFieldsValueForHandOverBill(HandOverBillEntity nowHandOverBillEntity,UpdateHandOverBillDto updateHandOverBillDto){
		//为nowHandOverBillEntity设置属性值，重量体积等，修改日期  转换快递体积
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//修改日期
		nowHandOverBillEntity.setModifyDate(new Date());
		//修改人
		nowHandOverBillEntity.setModifyUser(currentInfo.getEmpCode());
		//修改人code
		nowHandOverBillEntity.setModifyUserCode(currentInfo.getEmpCode());
		//修改人name
		nowHandOverBillEntity.setModifyUserName(currentInfo.getEmpName());
		//总金额
		nowHandOverBillEntity.setMoneyTotal(updateHandOverBillDto.getTotalMoney().multiply(new BigDecimal(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		//代收货款总额
		nowHandOverBillEntity.setCodAmountTotal(updateHandOverBillDto.getTotalCodAmount());
		//总重量
		nowHandOverBillEntity.setWeightTotal(updateHandOverBillDto.getTotalWeight());
		//总体积
		nowHandOverBillEntity.setVolumeTotal(updateHandOverBillDto.getTotalCubage());
		//总票数
		nowHandOverBillEntity.setWaybillQtyTotal(updateHandOverBillDto.getTotalCount().intValue());
		//总件数
		nowHandOverBillEntity.setGoodsQtyTotal(updateHandOverBillDto.getTotalPieces().intValue());
	}
	
	/**
	 * 私有方法，为新增的流水号补充值
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 上午10:33:47
	 * @param list
	 * @param oldHandOverBillEntity
	 * @return void
	 * @exception 无
	 */
	private void addFieldsValueForAddedSerialNoList(List<HandOverBillSerialNoDetailEntity> list,HandOverBillEntity oldHandOverBillEntity){
		for(int i = 0;i < list.size();i++){
			HandOverBillSerialNoDetailEntity serialNo = list.get(i);
			// 设置流水号之交接单编号
			serialNo.setHandOverBillNo(oldHandOverBillEntity.getHandOverBillNo());
			// 设置流水号之交接时间
			serialNo.setHandOverTime(oldHandOverBillEntity.getHandOverTime());
			// 设置流水号之出发部门编码
			serialNo.setOrigOrgCode(oldHandOverBillEntity.getDepartDeptCode());
			// 流水号生成主键
			serialNo.setId(UUIDUtils.getUUID());
		}
	}
	
	/**
	 * 私有方法，将流水号list转化为Map，map结构见方法内说明
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 上午10:58:26
	 * @param list
	 * @return Map
	 * @exception 无
	 */
	@SuppressWarnings({ "rawtypes"})
	private Map convertList2Map(List<HandOverBillSerialNoDetailEntity> list){
		//定义map，key为运单号，value为map（key为流水号，map为流水号实体）
		Map<String,Map<String,HandOverBillSerialNoDetailEntity>> map = new HashMap<String,Map<String,HandOverBillSerialNoDetailEntity>>();
		if(list != null && list.size() != 0){
			for(int i = 0;i < list.size();i++){
				String waybillNo = list.get(i).getWaybillNo();
				String serialNo = list.get(i).getSerialNo();
				if(map.get(waybillNo) == null){
					//若一层map中尚无此运单号，则新建map，放入流水号后put进一层map
					Map<String,HandOverBillSerialNoDetailEntity> serialNoMap = new HashMap<String,HandOverBillSerialNoDetailEntity>();
					serialNoMap.put(serialNo, list.get(i));
					map.put(waybillNo, serialNoMap);
				}else{//若一层map中有此运单号，则取出二层map，放入流水号后重新put进一层map
					Map<String,HandOverBillSerialNoDetailEntity> serialNoMap = map.get(waybillNo);
					serialNoMap.put(serialNo, list.get(i));
					map.put(waybillNo, serialNoMap);
				}
			}
		}
		return map;
	}
	
	/**
	 * 私有方法，将运单信息Map转化为List，同时补充字段信息
	 * @author 045923-foss-shiwei
	 * @param nowHandOverBillEntity
	 * @param map
	 * @param optType：0，删除；1，新增；2，更新
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @date 2012-11-1 上午11:04:54
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillDetailEntity> convertMap2List(HandOverBillEntity nowHandOverBillEntity,Map<String,HandOverBillDetailEntity> map,int optType){
		//获取交接单号
		String handOverBillNo = nowHandOverBillEntity.getHandOverBillNo();
		List<HandOverBillDetailEntity> list = new ArrayList<HandOverBillDetailEntity>();
		if(map != null && map.size() != 0){
			//优先货票数
			int fastWaybillQty = 0;
			Set entrySet = map.entrySet();
			Iterator iterator = entrySet.iterator();
			//遍历map，补充属性值，转化为list
			while(iterator.hasNext()) {
				Map.Entry<String,HandOverBillDetailEntity> entry = (Map.Entry<String,HandOverBillDetailEntity>)iterator.next();//key
				HandOverBillDetailEntity value = entry.getValue();//value
				//计算优先货票数
				if(StringUtils.equals(value.getIsFastGoods(), FossConstants.YES)){
					fastWaybillQty += 1;
				}
				//交接单号
				value.setHandOverBillNo(handOverBillNo);
				//设置出发部门编码
				value.setOrigOrgCode(nowHandOverBillEntity.getDepartDeptCode());
				//设置ID
				value.setId(UUIDUtils.getUUID());
				//设置交接时间
				value.setCreateDate(nowHandOverBillEntity.getHandOverTime());
				//设置修改时间
				value.setModifyDate(new Date());
				//币种
				value.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				//交接类型
				value.setHandOverType(nowHandOverBillEntity.getHandOverType());
				//运单金额
				value.setWaybillFee(value.getWaybillFee().multiply(new BigDecimal(LoadConstants.INSURANCE_VALUE_MULTIPLY)));//存储总金额的时候以分为单位，乘以100
				//将map中的运单信息放入list
				list.add(value);
			} 
			if(nowHandOverBillEntity.getFastWaybillQty() == null){
				nowHandOverBillEntity.setFastWaybillQty(0);
			}
			if(optType == 0){
				nowHandOverBillEntity.setFastWaybillQty(nowHandOverBillEntity.getFastWaybillQty() - fastWaybillQty);
			}else if(optType== 1){
				nowHandOverBillEntity.setFastWaybillQty(nowHandOverBillEntity.getFastWaybillQty() + fastWaybillQty);
			}
		}
		return list;
	}
	
	/**
	 * 私有方法，根据传入的待修改的参数，对比原先的交接单信息，输出修改日志list
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 下午1:50:03
	 * @param handOverBillEntity 修改后的交接单实体
	 * @param addedWaybillMap 待新增的运单信息map（key，运单号；value，HandOverBillDetailEntity）
	 * @param deletedWaybillMap 待删除的运单信息map（key，运单号；value，HandOverBillDetailEntity）
	 * @param updatedWaybillMap 待更新的运单信息map（key，运单号；value，HandOverBillDetailEntity）
	 * @param deletedSerialNoList 待删除的流水号list
	 * @param addedSerialNoList 待新增的流水号list
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 */
	private List<HandOverBillOptLogEntity> outputLogList( HandOverBillEntity nowHandOverBillEntity,
																							HandOverBillEntity oldHandOverBillEntity,
																							Map<String,HandOverBillDetailEntity> addedWaybillMap,
																							Map<String,HandOverBillDetailEntity> deletedWaybillMap,
																							Map<String,HandOverBillDetailEntity> updatedWaybillMap,
																							Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap,
																							Map<String,Map<String,HandOverBillSerialNoDetailEntity>> addedSerialNoMap){
		//获取基本信息修改日志实体
		List<HandOverBillOptLogEntity> optLogList = outPutLogForBasicInfo(oldHandOverBillEntity,nowHandOverBillEntity);
		//添加”添加运单“的操作日志
		List<HandOverBillOptLogEntity> addedWaybillLogList = outputLogForAddedWaybill(addedWaybillMap,
				nowHandOverBillEntity,
                oldHandOverBillEntity,
                addedSerialNoMap);
		optLogList.addAll(addedWaybillLogList);
		//添加”删除运单“的操作日志
		List<HandOverBillOptLogEntity> deletedWaybillLogList = outputLogForDeletedWaybill(deletedWaybillMap,
				nowHandOverBillEntity,
                oldHandOverBillEntity,
                deletedSerialNoMap);
		optLogList.addAll(deletedWaybillLogList);
		//添加“修改运单”的操作日志
		List<HandOverBillOptLogEntity> updatedWaybillLogList =  outputLogForUpdatedWaybill(updatedWaybillMap,
                deletedSerialNoMap,
                addedSerialNoMap,
                nowHandOverBillEntity,
				oldHandOverBillEntity);
		optLogList.addAll(updatedWaybillLogList);
		//添加“删除流水号”的操作日志
		List<HandOverBillOptLogEntity> deletedSerialNoLogList = outputLogForDeletedSerialNo(nowHandOverBillEntity,
				oldHandOverBillEntity,
				updatedWaybillMap,
				deletedSerialNoMap);
		optLogList.addAll(deletedSerialNoLogList);
		//添加“添加流水号”的操作日志
		List<HandOverBillOptLogEntity> addedSerialNoLogList = outputLogForAddedSerialNo(nowHandOverBillEntity,
				oldHandOverBillEntity,
				updatedWaybillMap,
				addedSerialNoMap);
		optLogList.addAll(addedSerialNoLogList);
		return optLogList;
	}
	
	/**
	 * 私有方法，为”添加运单“操作组合修改日志
	 * @author 045923-foss-shiwei
	 * @param addedWaybillMap
	 * @param nowHandOverBillEntity
	 * @param oldHandOverBillEntity
	 * @param addedSerialNoMap
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 * @date 2012-11-2 下午4:49:15
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillOptLogEntity> outputLogForAddedWaybill(Map<String,HandOverBillDetailEntity> addedWaybillMap,
			                                                                                                    HandOverBillEntity nowHandOverBillEntity,
			                                                                                                    HandOverBillEntity oldHandOverBillEntity,
			                                                                                                    Map<String,Map<String,HandOverBillSerialNoDetailEntity>> addedSerialNoMap){
		List<HandOverBillOptLogEntity> addedWaybillLogList = new ArrayList<HandOverBillOptLogEntity>();
		if(addedWaybillMap != null && addedWaybillMap.size() != 0){
			Set entrySet = addedWaybillMap.entrySet();
			Iterator iterator = entrySet.iterator();
			//遍历map，补充属性值
			while(iterator.hasNext()) {
				Map.Entry<String, HandOverBillDetailEntity> entry = (Map.Entry<String, HandOverBillDetailEntity>)iterator.next();
				String key = entry.getKey();
				HandOverBillDetailEntity value = entry.getValue();//value
				//创建新的修改日志实体
				HandOverBillOptLogEntity addedWaybillLog = new HandOverBillOptLogEntity();
				//操作类别为添加运单
				addedWaybillLog.setOptType("添加运单");
				//设置其他属性
				addFieldValueForLogEntity(addedWaybillLog,oldHandOverBillEntity,nowHandOverBillEntity);
				//拼接操作内容
				StringBuffer sb = new StringBuffer();
				sb.append("运单号：");
				sb.append(key);
				sb.append("；包括");
				sb.append(value.getPieces());
				sb.append("件货物，流水号为：");
				Map<String,HandOverBillSerialNoDetailEntity> tempSerialNoMap = addedSerialNoMap.get(key);
				Set entrySet2 = tempSerialNoMap.entrySet();
				Iterator iterator2 = entrySet2.iterator();
				int i = 0;
				while(iterator2.hasNext()){
					i +=1;
					Map.Entry<String, HandOverBillSerialNoDetailEntity> entry2 = (Map.Entry<String, HandOverBillSerialNoDetailEntity>)iterator2.next();
					String key2 = entry2.getKey();
					sb.append(key2);
					if(i == tempSerialNoMap.size()){
						//若是最后一个流水号，则结尾用分号，否则用顿号
						sb.append("；");
					}else{
						sb.append("、");
					}
				}
				sb.append("重量为：");
				sb.append(value.getWeightAc());
				sb.append("，体积为：");
				sb.append(value.getCubageAc());
				sb.append("。");
				//使用完毕后从addedSerialNoMap中删除该运单号对应记录，如此addedSerialNoMap中只剩下”添加流水号“操作剩下的流水号map
				addedSerialNoMap.remove(key);
				addedWaybillLog.setOptContent(sb.toString());
				addedWaybillLogList.add(addedWaybillLog);
			} 
		}
		return addedWaybillLogList;
	}
	
	/**
	 * 私有方法，为”删除运单“操作组合修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午4:49:15
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillOptLogEntity> outputLogForDeletedWaybill(Map<String,HandOverBillDetailEntity> deletedWaybillMap,
			                                                                                                    HandOverBillEntity nowHandOverBillEntity,
			                                                                                                    HandOverBillEntity oldHandOverBillEntity,
			                                                                                                    Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap){
		List<HandOverBillOptLogEntity> deletedWaybillLogList = new ArrayList<HandOverBillOptLogEntity>();
		if(deletedWaybillMap != null && deletedWaybillMap.size() != 0){
			Set entrySet = deletedWaybillMap.entrySet();
			Iterator iterator = entrySet.iterator();
			//遍历map，补充属性值
			while(iterator.hasNext()) {
				Map.Entry<String, HandOverBillDetailEntity> entry = (Map.Entry<String, HandOverBillDetailEntity>)iterator.next();//key
				HandOverBillDetailEntity value = entry.getValue();//value
				String key = entry.getKey();//运单号
				//创建新的修改日志实体
				HandOverBillOptLogEntity deletedWaybillLog = new HandOverBillOptLogEntity();
				//操作类别为添加运单
				deletedWaybillLog.setOptType("删除运单");
				//设置其他属性
				addFieldValueForLogEntity(deletedWaybillLog,oldHandOverBillEntity,nowHandOverBillEntity);
				//拼接操作内容
				StringBuffer sb = new StringBuffer();
				sb.append("运单号：");
				sb.append(key);
				sb.append("；包括");
				sb.append(value.getPieces());
				sb.append("件货物，流水号为：");
				Map<String,HandOverBillSerialNoDetailEntity> tempSerialNoMap = deletedSerialNoMap.get(key);
				Set keysSet2 = tempSerialNoMap.keySet();
				Iterator iterator2 = keysSet2.iterator();
				int i = 0;
				while(iterator2.hasNext()){
					i +=1;
					Object key2 = iterator2.next();//key
					sb.append(key2);
					if(i == tempSerialNoMap.size()){
						//若是最后一个流水号，则结尾用分号，否则用顿号
						sb.append("；");
					}else{
						sb.append("、");
					}
				}
				sb.append("重量为：");
				sb.append(value.getWeightAc());
				sb.append("，体积为：");
				sb.append(value.getCubageAc());
				sb.append("。");
				//使用完毕后从deletedSerialNoMap中删除该运单号对应记录，如此deletedSerialNoMap中只剩下”删除流水号“操作剩下的流水号map
				deletedSerialNoMap.remove(key);
				deletedWaybillLog.setOptContent(sb.toString());
				deletedWaybillLogList.add(deletedWaybillLog);
			} 
		}
		return deletedWaybillLogList;
	}
	/**
	 * 私有方法，为”修改运单“操作组合修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午5:11:17
	 * @param updatedWaybillMap
	 * @param deletedSerialNoMap
	 * @param addedSerialNoMap
	 * @param nowHandOverBillEntity
	 * @param oldHandOverBillEntity
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillOptLogEntity> outputLogForUpdatedWaybill(Map<String,HandOverBillDetailEntity> updatedWaybillMap,
			                                                                                                       Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap,
			                                                                                                       Map<String,Map<String,HandOverBillSerialNoDetailEntity>> addedSerialNoMap,
			                                                                                                       HandOverBillEntity nowHandOverBillEntity,
			           																							   HandOverBillEntity oldHandOverBillEntity){
		List<HandOverBillOptLogEntity> updatedWaybillLogList = new ArrayList<HandOverBillOptLogEntity>();
		//获取交接单号
		String handOverBillNo = oldHandOverBillEntity.getHandOverBillNo();
		if(updatedWaybillMap != null && updatedWaybillMap.size() != 0){
			Set entrySet = updatedWaybillMap.entrySet();
			Iterator iterator = entrySet.iterator();
			//遍历map，补充属性值
			while(iterator.hasNext()) {
				//key为运单号
				Map.Entry<String,HandOverBillDetailEntity> entry = (Map.Entry<String, HandOverBillDetailEntity>)iterator.next();
				String key = entry.getKey();
				//若删除流水号map和增加流水号map中没有该单号的记录，则说明该更新记录是因为更新了运单的”实际体积“或者”实际重量“或者”备注“引起
				if(deletedSerialNoMap.get(key) == null && addedSerialNoMap.get(key) == null){
					//获取修改后的运单实体
					HandOverBillDetailEntity newEntity = entry.getValue();
					//new日志实体
					HandOverBillOptLogEntity updatedWaybillLog = new HandOverBillOptLogEntity();
					//为日志实体设置属性值
					addFieldValueForLogEntity(updatedWaybillLog,oldHandOverBillEntity,nowHandOverBillEntity);
					updatedWaybillLog.setOptType("修改运单");
					//从数据库获取修改前的运单实体
					HandOverBillDetailEntity oldEntity = handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo, key).get(0);
					StringBuffer sb = new StringBuffer();
					sb.append("运单号：");
					sb.append(key);
					sb.append("；");
					//如果实际重量不相等
					if(oldEntity.getWeightAc().compareTo(newEntity.getWeightAc()) != 0){
						sb.append("“实际重量”由");
						sb.append(oldEntity.getWeightAc());
						sb.append("修改为");
						sb.append(newEntity.getWeightAc());
						sb.append("；     ");
					}
					//如果实际体积不相等
					if(oldEntity.getCubageAc().compareTo(newEntity.getCubageAc()) != 0){
						sb.append("“实际体积”由");
						sb.append(oldEntity.getCubageAc());
						sb.append("修改为");
						sb.append(newEntity.getCubageAc());
						sb.append("；     ");
					}
					//如果备注不相等
					if("".equals(oldEntity.getNote()) || oldEntity.getNote() == null){
						oldEntity.setNote("空");
					}
					if("".equals(newEntity.getNote()) || newEntity.getNote() == null){
						newEntity.setNote("空");
					}
					if(!StringUtils.equals(oldEntity.getNote(), newEntity.getNote())){
						sb.append("“备注”由“");
						sb.append(oldEntity.getNote());
						sb.append("”修改为“");
						sb.append(newEntity.getNote());
						sb.append("”。");
					}
					updatedWaybillLog.setOptContent(sb.toString());
					updatedWaybillLogList.add(updatedWaybillLog);
					if("空".equals(newEntity.getNote())){
						newEntity.setNote(null);
					}
				}
			}
		}
		return updatedWaybillLogList;
	}
	
	/**
	 * 私有方法，为”删除流水号“操作组合修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午6:34:42
	 * @param nowHandOverBillEntity
	 * @param oldHandOverBillEntity
	 * @param updatedWaybillMap
	 * @param deletedSerialNoMap
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillOptLogEntity> outputLogForDeletedSerialNo(HandOverBillEntity nowHandOverBillEntity,
			HandOverBillEntity oldHandOverBillEntity,
			Map<String,HandOverBillDetailEntity> updatedWaybillMap,
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> deletedSerialNoMap){
		//经过组合“删除运单”日志后，所有经过删除整个运单导致的流水号记录已经全部从deletedSerialNoList中删除；
		List<HandOverBillOptLogEntity> deletedSerialNoLogList = new ArrayList<HandOverBillOptLogEntity>();
		//获取交接单号
		String handOverBillNo = oldHandOverBillEntity.getHandOverBillNo();
		//遍历map
		if(deletedSerialNoMap != null && deletedSerialNoMap.size() != 0){
			Set entrySet = deletedSerialNoMap.entrySet();
			Iterator iterator = entrySet.iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, Map<String,HandOverBillSerialNoDetailEntity>> entry = (Map.Entry<String, Map<String,HandOverBillSerialNoDetailEntity>>)iterator.next();
				//运单号
				String key = entry.getKey();
				//new“删除流水号”日志实体
				HandOverBillOptLogEntity deletedSerialNoLog = new HandOverBillOptLogEntity();
				//补充日志实体属性
				addFieldValueForLogEntity(deletedSerialNoLog,oldHandOverBillEntity,nowHandOverBillEntity);
				//修改类别
				deletedSerialNoLog.setOptType("删除流水号");
				HandOverBillDetailEntity newEntity = updatedWaybillMap.get(key);
				HandOverBillDetailEntity oldEntity = handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo, key).get(0);
				StringBuffer sb = new StringBuffer();
				sb.append("运单号：");
				sb.append(key);
				sb.append("；包括");		
				Map<String,HandOverBillSerialNoDetailEntity> tempSerialNoMap = entry.getValue();
				int j = tempSerialNoMap.size();
				sb.append(j);
				sb.append("件货物，流水号为：");
				int i = 0;	
				Set entrySet2 = tempSerialNoMap.entrySet();
				Iterator iterator2 = entrySet2.iterator();
				while(iterator2.hasNext()) {
					i += 1;
					//key为流水号
					Map.Entry<String, HandOverBillSerialNoDetailEntity> entry2 = (Map.Entry<String, HandOverBillSerialNoDetailEntity>)iterator2.next();
					String key2 = entry2.getKey();
					sb.append(key2);
					if(i == j){//遍历至最后一个流水号时用分号，其他用顿号
						sb.append("；     ");
					}else{
						sb.append("、");
					}
				}
				if(oldEntity.getWeightAc().compareTo(newEntity.getWeightAc()) != 0){
					sb.append("重量由");
					sb.append(oldEntity.getWeightAc());
					sb.append("修改为");
					sb.append(newEntity.getWeightAc());
					sb.append("；     ");
				}
				if(oldEntity.getCubageAc().compareTo(newEntity.getCubageAc()) != 0){
					sb.append("体积由");
					sb.append(oldEntity.getCubageAc());
					sb.append("修改为");
					sb.append(newEntity.getCubageAc());
					sb.append("；     ");
				}
				if(oldEntity.getNote() == null || "".equals(oldEntity.getNote())){
					oldEntity.setNote("空");
				}
				if(newEntity.getNote() == null || "".equals(newEntity.getNote())){
					newEntity.setNote("空");
				}
				if(!StringUtils.equals(oldEntity.getNote(), newEntity.getNote())){
					sb.append("备注由“");
					sb.append(oldEntity.getNote());
					sb.append("”修改为“");
					sb.append(newEntity.getNote());
					sb.append("”。");
				}
				deletedSerialNoLog.setOptContent(sb.toString());
				deletedSerialNoLogList.add(deletedSerialNoLog);
				if("空".equals(newEntity.getNote())){
					newEntity.setNote(null);
				}
			}
		}
		return deletedSerialNoLogList;
	}
	
	/**
	 * 私有方法，为”添加流水号“操作组合修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午8:26:41
	 * @param nowHandOverBillEntity
	 * @param oldHandOverBillEntity
	 * @param updatedWaybillMap
	 * @param addedSerialNoMap
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HandOverBillOptLogEntity> outputLogForAddedSerialNo(HandOverBillEntity nowHandOverBillEntity,
			HandOverBillEntity oldHandOverBillEntity,
			Map<String,HandOverBillDetailEntity> updatedWaybillMap,
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> addedSerialNoMap){
		List<HandOverBillOptLogEntity> addedSerialNoLogList = new ArrayList<HandOverBillOptLogEntity>();
		//经过组合“添加运单”日志后，所有经过添加整个运单导致的流水号记录已经全部从addedSerialNoList中删除；
		String handOverBillNo = oldHandOverBillEntity.getHandOverBillNo();
		if(addedSerialNoMap != null && addedSerialNoMap.size() != 0){
			Set entrySet = addedSerialNoMap.entrySet();
			Iterator iterator = entrySet.iterator();
			while(iterator.hasNext()) {
				//key为运单号
				Map.Entry<String, Map<String,HandOverBillSerialNoDetailEntity>> entry = (Map.Entry<String, Map<String,HandOverBillSerialNoDetailEntity>>)iterator.next();
				String key = entry.getKey();//运单号
				//new“删除流水号”日志实体
				HandOverBillOptLogEntity addedSerialNoLog = new HandOverBillOptLogEntity();
				//补充日志实体属性
				addFieldValueForLogEntity(addedSerialNoLog,oldHandOverBillEntity,nowHandOverBillEntity);
				addedSerialNoLog.setOptType("添加流水号");
				HandOverBillDetailEntity newEntity = updatedWaybillMap.get(key);
				HandOverBillDetailEntity oldEntity = handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo, key).get(0);
				StringBuffer sb = new StringBuffer();
				sb.append("运单号：");
				sb.append(key);
				sb.append("；包括");
				Map<String,HandOverBillSerialNoDetailEntity> tempSerialNoMap = entry.getValue();
				int j = tempSerialNoMap.size();
				sb.append(tempSerialNoMap.size());
				sb.append("件货物，流水号为：");
				int i = 0;
				Set entrySet2 = tempSerialNoMap.entrySet();
				Iterator iterator2 = entrySet2.iterator();
				while(iterator2.hasNext()) {
					i += 1;
					//key为流水号
					Map.Entry<String, HandOverBillSerialNoDetailEntity> entry2 = (Map.Entry<String, HandOverBillSerialNoDetailEntity>)iterator2.next();
					String key2 = entry2.getKey();
					sb.append(key2);
					if(i == j){//遍历至最后一个流水号时用分号，其他用顿号
						sb.append("；     ");
					}else{
						sb.append("、");
					}
				}
				if(oldEntity.getWeightAc().compareTo(newEntity.getWeightAc()) != 0){
					sb.append("重量由");
					sb.append(oldEntity.getWeightAc());
					sb.append("修改为");
					sb.append(newEntity.getWeightAc());
					sb.append("；     ");
				}
				if(oldEntity.getCubageAc().compareTo(newEntity.getCubageAc()) != 0){
					sb.append("体积由");
					sb.append(oldEntity.getCubageAc());
					sb.append("修改为");
					sb.append(newEntity.getCubageAc());
					sb.append("；     ");
				}
				if(oldEntity.getNote() == null || "".equals(oldEntity.getNote())){
					oldEntity.setNote("空");
				}
				if(newEntity.getNote() == null || "".equals(newEntity.getNote())){
					newEntity.setNote("空");
				}
				if(!StringUtils.equals(oldEntity.getNote(), newEntity.getNote())){
					sb.append("备注由“");
					sb.append(oldEntity.getNote());
					sb.append("”修改为“");
					sb.append(newEntity.getNote());
					sb.append("”。");
				}
				addedSerialNoLog.setOptContent(sb.toString());
				addedSerialNoLogList.add(addedSerialNoLog);
				if("空".equals(newEntity.getNote())){
					newEntity.setNote(null);
				}
			}
		}
		return addedSerialNoLogList;
	}
	
	/**
	 * 私有方法，为日志实体设置属性值
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午3:47:38
	 * @param logEntity
	 * @param oldEntity
	 * @param newEntity
	 * @return void
	 * @exception 无
	 */
	private void addFieldValueForLogEntity(HandOverBillOptLogEntity logEntity,HandOverBillEntity oldEntity,HandOverBillEntity newEntity){
		//id
		logEntity.setId(UUIDUtils.getUUID());
		//操作时间
		logEntity.setOptTime(newEntity.getModifyDate());
		//创建人
		logEntity.setCreateUser(newEntity.getModifyUserCode());
		//交接单id
		logEntity.setHandOverBillID(oldEntity.getId());
		//交接单号
		logEntity.setHandOverBillNo(oldEntity.getHandOverBillNo());
		//交接时间
		logEntity.setHandOverTime(oldEntity.getHandOverTime());
		//修改人code
		logEntity.setOperatorCode(newEntity.getModifyUserCode());
		//修改人name
		logEntity.setOperatorName(newEntity.getModifyUserName());
	}
	/**
	 * 私有方法，比较新旧交接单基本信息实体，输入基本信息修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-2 下午2:57:55
	 * @param oldEntity
	 * @param newEntity
	 * @return List<HandOverBillOptLogEntity>
	 * @exception 无
	 */
	private List<HandOverBillOptLogEntity> outPutLogForBasicInfo(HandOverBillEntity oldEntity,HandOverBillEntity newEntity){
		/**
		 * 如果车牌号被修改，并且已生成任务车辆明细，则调用任务车辆接口
		 */
		List<HandOverBillOptLogEntity> list = new ArrayList<HandOverBillOptLogEntity>();
		//备注可以为空，故做特殊处理
		if(StringUtils.isEmpty(oldEntity.getNote())){
			oldEntity.setNote("");
		}
		if(StringUtils.isEmpty(newEntity.getNote())){
			newEntity.setNote("");
		}
		//司机
		if(!StringUtils.equals(newEntity.getDriver(),oldEntity.getDriver())
				//车牌号
				|| !StringUtils.equals(newEntity.getVehicleNo(),oldEntity.getVehicleNo())
				//备注
				|| !StringUtils.equals(newEntity.getNote(), oldEntity.getNote())
				//货物类型
				|| !StringUtils.equals(newEntity.getGoodsType(), oldEntity.getGoodsType())
				//转货类型
				|| !StringUtils.equals(newEntity.getTranGoodsType(), oldEntity.getTranGoodsType())
				//总票数
				|| newEntity.getWaybillQtyTotal().compareTo(oldEntity.getWaybillQtyTotal()) != 0
				//总件数
				|| newEntity.getGoodsQtyTotal().compareTo(oldEntity.getGoodsQtyTotal()) != 0
				//总体积
				|| newEntity.getVolumeTotal().compareTo(oldEntity.getVolumeTotal()) != 0
				//总重量
				|| newEntity.getWeightTotal().compareTo(oldEntity.getWeightTotal()) != 0){
			HandOverBillOptLogEntity basicInfoOptLog = new HandOverBillOptLogEntity();
			//补充日志实体其他属性值
			addFieldValueForLogEntity(basicInfoOptLog,oldEntity,newEntity);
			basicInfoOptLog.setOptType("修改基本信息");
			StringBuffer sb = new StringBuffer();
			if(!StringUtils.equals(newEntity.getDriver(),oldEntity.getDriver())){
				sb.append("”司机“由“");
				sb.append(oldEntity.getDriverName());
				sb.append("(");
				sb.append(oldEntity.getDriver());
				sb.append(")");
				sb.append("“修改为”");
				sb.append(newEntity.getDriverName());
				sb.append("(");
				sb.append(newEntity.getDriver());
				sb.append(")");
				sb.append("“；     ");
			}
			if(!StringUtils.equals(newEntity.getVehicleNo(), oldEntity.getVehicleNo())){
				sb.append("”车牌号“由“");
				sb.append(oldEntity.getVehicleNo());
				sb.append("“修改为”");
				sb.append(newEntity.getVehicleNo());
				sb.append("“；     ");
				
				/**
				 * 如果是集配交接单，且已 经配载，则抛出异常，不允许修改车牌号
				 */
				//获取当前最新的交接单信息
				HandOverBillEntity nowEntity = this.queryHandOverBillByNo(newEntity.getHandOverBillNo());
				if(StringUtils.equals(nowEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_LONG_DISTANCE)
						&& (nowEntity.getHandOverBillState() != LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER
						&& nowEntity.getHandOverBillState() != LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER)){
					//获取配载单号
					String vehicleAssembleNo = this.getVehicleassembleNoByHandoverNo(newEntity.getHandOverBillNo());
					throw new TfrBusinessException("该交接单已经被配载，无法修改车牌号，请直接修改配载单【" + vehicleAssembleNo + "】的车牌号！");
				}
			}
			if(!StringUtils.equals(newEntity.getNote(), oldEntity.getNote())){
				sb.append("”备注“由“");
				sb.append(oldEntity.getNote());
				sb.append("“修改为”");
				sb.append(newEntity.getNote());
				sb.append("“；     ");
			}
			if(StringUtils.equals(newEntity.getNote(), "空")){
				newEntity.setNote(null);
			}
			if(!StringUtils.equals(newEntity.getGoodsType(), oldEntity.getGoodsType())){
				sb.append("”货物类型“由“");
				if(StringUtils.equals(oldEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_ALL)){
					sb.append("“全部”");
				}else if(StringUtils.equals(oldEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_A_TYPE)){
					sb.append("“A类”");
				}else if(StringUtils.equals(oldEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_B_TYPE)){
					sb.append("“B类”");
				}else{
					sb.append("“未知类型”");
				}
				sb.append("“修改为”");
				if(StringUtils.equals(newEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_ALL)){
					sb.append("“全部”");
				}else if(StringUtils.equals(newEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_A_TYPE)){
					sb.append("“A类”");
				}else if(StringUtils.equals(newEntity.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_B_TYPE)){
					sb.append("“B类”");
				}else{
					sb.append("“未知类型”");
				}
				sb.append("“；     ");
			}
			if(!StringUtils.equals(newEntity.getTranGoodsType(), oldEntity.getTranGoodsType())){
				sb.append("”转货类型“由“");
				 if(StringUtils.equals(oldEntity.getTranGoodsType(), LoadConstants.TRANSITGOODS_TYPE_TRAN)){
					sb.append("“转货”");
				}else if(StringUtils.equals(oldEntity.getTranGoodsType(), LoadConstants.TRANSITGOODS_TYPE_TAKE)){
					sb.append("“带货”");
				}else{
					sb.append("“未知类型”");
				}
				sb.append("“修改为”");
				if(StringUtils.equals(newEntity.getTranGoodsType(), LoadConstants.TRANSITGOODS_TYPE_TRAN)){
					sb.append("“转货”");
				}else if(StringUtils.equals(newEntity.getTranGoodsType(), LoadConstants.TRANSITGOODS_TYPE_TAKE)){
					sb.append("“带货”");
				}else{
					sb.append("“未知类型”");
				}
				sb.append("“；     ");
			}
			if(newEntity.getWaybillQtyTotal().compareTo(oldEntity.getWaybillQtyTotal()) != 0){
				sb.append("”总票数“由");
				sb.append(oldEntity.getWaybillQtyTotal());
				sb.append("修改为");
				sb.append(newEntity.getWaybillQtyTotal());
				sb.append("；     ");
			}
			if(newEntity.getGoodsQtyTotal().compareTo(oldEntity.getGoodsQtyTotal()) != 0){
				sb.append("”总件数“由");
				sb.append(oldEntity.getGoodsQtyTotal());
				sb.append("修改为");
				sb.append(newEntity.getGoodsQtyTotal());
				sb.append("；     ");
			}
			if(newEntity.getVolumeTotal().compareTo(oldEntity.getVolumeTotal()) != 0){
				sb.append("”总体积“由");
				sb.append(oldEntity.getVolumeTotal());
				sb.append("修改为");
				sb.append(newEntity.getVolumeTotal());
				sb.append("；     ");
			}
			if(newEntity.getWeightTotal().compareTo(oldEntity.getWeightTotal()) != 0){
				sb.append("”总重量“由");
				sb.append(oldEntity.getWeightTotal());
				sb.append("修改为");
				sb.append(newEntity.getWeightTotal());
				sb.append("；     ");
			}
			if(StringUtils.equals(newEntity.getNote(), "空")){
				newEntity.setNote(null);
			}
			basicInfoOptLog.setOptContent(sb.toString());
			list.add(basicInfoOptLog);
		}
		return list;
	}

	/**
	 * 为配载单模块返回待配载交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:28:47
	 * @param queryHandOverBillConditionDto
	 * @return List<QueryHandOverBillDto>
	 * @exception 无
	 */
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListForVehicleAssembleBill(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		//返回查询结果
		return handOverBillDao.queryHandOverBillListForVehicleAssembleBill(queryHandOverBillConditionDto);
	}
	
	/**
	 * 前台界面获取交接单号
	 * @author 045923-foss-shiwei
	 * @date 2012-11-21 上午10:26:02
	 * @return String
	 * @exception 无
	 */
	@Override
	public String[] showHandOverBillNo(){
		//生成交接单号
		//String handOverBillNo = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.JJD);
		String handOverBillNo = billNumService.generateHandoverbillNo();
		
		//获取上级组织
		String currentOrgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgEntity = loadService.querySuperOrgByOrgCode(currentOrgCode);
		//如果上级组织为空
		if(orgEntity == null){
			throw new TfrBusinessException("获取当前部门的上级组织失败！(包括营业部、派送部、总调、转运场)");
		}
		//返回交接单号
		return new String[]{handOverBillNo,orgEntity.getName(),orgEntity.getSalesDepartment(),orgEntity.getCode(),orgEntity.getExpressBranch()};
	}
	
	/****
	 * 根据交接单号查询出配置单号
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:37:26
	 * @param handOverBillNo
	 * @return String
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#getVehicleassembleNoByHandoverNo(java.lang.String)
	 */
	@Override
	public String getVehicleassembleNoByHandoverNo(String handOverBillNo) {
		//根据交接单号查询出配载单号
		return handOverBillDao.getVehicleassembleNoByHandoverNo(handOverBillNo);
	}

	/**
	 * 更新交接单中流水号预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午8:29:32
	 * @param dtoList
	 * @return boolean
	 * @exception TfrBusinessException
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverBillSerialNoPreHandOverState(java.util.List)
	 */
	@Override
	public boolean updateHandOverBillSerialNoPreHandOverState(
			List<UpdateHandOverBillSerialNoPreHandOverStateDto> dtoList) throws TfrBusinessException{
		UpdateHandOverBillSerialNoPreHandOverStateDto dto = null;
		for(int i = 0;i < dtoList.size();i++){
			dto = dtoList.get(i);
			if(null == handOverBillDao.querySerialNoFromHandOverBill(dto)){
				throw new TfrBusinessException("运单号：" + dto.getWaybillNo() + "，流水号：" + dto.getSerialNo() + "状态不合法！");
			}else{
				handOverBillDao.updateHandOverBillSerialNoPreHandOverState(dto);
			}
		}
		return true;
	}

	/**
	 * 作废交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-29 下午3:52:35
	 * @param handOverBillNo
	 * @return boolean
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#cancelHandOverBill(java.lang.String)
	 */
	@SuppressWarnings("static-access")
	@Override
	@Transactional
	public boolean cancelHandOverBill(String handOverBillNo) {
		/**
		 * 只能作废符合以下条件的交接单：
		 *	1、	“出发部门”为本部门；
		 *	2、	状态为“已预配”或“已交接”；
		 * 	3、	非PDA生成；
		 */
		/**
		 * 如果为预配交接单：
		 * 1、更新交接单状态为“已作废”；
		 * 2、更新交接单中在库的流水号的预配状态；
		 * 如果为正式交接单：
		 * 1、更新交接单状态为“已作废”；
		 * 2、将交接单中的流水号从在本部门入库；
		 * 3、如果已生成任务车辆，则调用接口，删除任务车辆相关信息；
		 * 4、调用走货路径接口；
		 * 
		 * 作废集配交接单时，如果该集配交接单已经被加入到某配载单中，
		 * 则必须先把此交接单从配载单中删除，才可作废此交接单；
		 */
		LOGGER.error("开始作废交接单：" + handOverBillNo);
		//获取要作废的交接单实体
		HandOverBillEntity billEntity = this.queryHandOverBillByNo(handOverBillNo);
		//获取交接单下所有的流水号
		List<HandOverBillSerialNoDetailEntity> serialNoList = this.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
		//流水号空判断
		if(CollectionUtils.isEmpty(serialNoList)){
			throw new TfrBusinessException("交接单流水号为空，无法作废！");
		}
		
		String  holdingState=this.queryHoldingState(handOverBillNo);
		if(holdingState.equals("Y")){
			throw new TfrBusinessException("交接单生成的临时租车已付款、已预提或在预提中，不允许修改！");
		}
		LOGGER.error("作废交接单（" + handOverBillNo + "）时获取交接单的流水号个数为：" + serialNoList.size());
		//获取当前部门code，用户姓名、code
		String deptCode = this.querySuperiorOrgCode();
		String userName = FossUserContext.getCurrentInfo().getEmpName();
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
		//更新交接单状态为已作废
		this.updateHandOverBillStateByNo(handOverBillNo, LoadConstants.HANDOVERBILL_STATE_ALREADY_CANCEL);
		//收集交接单下所有在库的流水号
		List<UpdateStockPreHandOverStateDto> dtoList = new ArrayList<UpdateStockPreHandOverStateDto>();
		//遍历流水号；根据是否为预配交接单调用库存、任务车辆，走货路径接口
		for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
			//如果为预配交接单
			if(billEntity.getHandOverBillState() == LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER){
				//如果流水号在库，则更新在库流水号的预配状态
				UpdateStockPreHandOverStateDto dto = new UpdateStockPreHandOverStateDto();
				//部门code
				dto.setDeptCode(deptCode);
				//运单号
				dto.setWaybillNo(serialNo.getWaybillNo());
				//流水号
				dto.setSerialNo(serialNo.getSerialNo());
				dtoList.add(dto);
			//如果为正式交接单
			}else{
				//在本部门入库所有流水号
				InOutStockEntity inStockEntity = new InOutStockEntity();
				//运单号
				inStockEntity.setWaybillNO(serialNo.getWaybillNo());
				
				//流水号
				inStockEntity.setSerialNO(serialNo.getSerialNo());
				//部门code
				inStockEntity.setOrgCode(deptCode);
				//操作人code
				inStockEntity.setOperatorCode(userCode);
				//操作人name
				inStockEntity.setOperatorName(userName);
				//如果为整车交接单，则传入整车入库类型
				if(StringUtils.equals(FossConstants.YES, billEntity.getIsCarLoad())){
					//整车交接单作废入库
					inStockEntity.setInOutStockType(StockConstants.WHOLE_VEHICLE_CANCEL_HANDOVERBILL);
				}else{
					//交接单作废入库
					inStockEntity.setInOutStockType(StockConstants.CANCEL_HANDOVERBILL);
					//调用走货路径rollBack方法
//					calculateTransportPathService.rollBack(serialNo.getWaybillNo(), serialNo.getSerialNo(), billEntity.getVehicleNo(), TransportPathConstants.TRANSPORTPATH_STATUS_HANDOVER);
				}
			
				//入库s
				LOGGER.error("作废交接单：" + handOverBillNo + "入库开始，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo());		
				if(isAddtStockSale(inStockEntity,billEntity)){
					//设置操作出库设备类型
					inStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
					//设置当前调用时间为扫描时间
					inStockEntity.setScanTime(new Date());
					//设置计划出发时间
					inStockEntity.setPlanStartTime(new Date());
					//设置下一站
					inStockEntity.setNextOrgCode(billEntity.getArriveDeptCode());
					stockService.addStockSale(inStockEntity);
				}else{
				stockService.inStockPC(inStockEntity);
				}
//				ReturnGoodsRequestEntity returnGoodsRequestEntity = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(serialNo.getWaybillNo());
//				if(returnGoodsRequestEntity!=null&&StringUtils.equals("OUTBOUND_THREE_DAYS_RETURN", returnGoodsRequestEntity.getReturnType())){
//					//入异常库区 更新库区
//					String code= serialNo.getDestOrgCode();
//					//根据到达外场code查询异常库区Code --BSE_GOODSAREA_TYPE_EXCEPTION(异常库)
//					String areacode = handOverBillDao.queryGoodsExceptionArea(code);
//					//更新库区为异常库区
//					handOverBillDao.updateAreaCode(serialNo.getWaybillNo(),areacode,code);
//				}
				LOGGER.error("作废交接单：" + handOverBillNo + "入库结束，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo());
			}
		}
		if(dtoList.size() > 0){
			//还原在库流水号的预配状态
			stockService.updatePreHandOverState(dtoList,null);
		}
		/**
		 * PC端新增包，出现的问题，需要在作废包交接单的时候，将包的状态变成未交接
		 */
		if(StringUtils.equals(billEntity.getBePackage(), FossConstants.YES)){
			//更新包状态为未交接
			expressPackageService.updateExpressPackageState(billEntity.getHandOverBillNo(), this.PACKAGESTATE_FINISHED);
			//撤销包   使已建包的运单可以重新在pc端建包
			expressPackageService.cancelExpressPackage(billEntity.getHandOverBillNo());
		}
		LOGGER.error("作废交接单完毕：" + handOverBillNo);
		return true;
	}

	/**
	 * 传入到达部门code，返回外场实体，如果不是外场，则返回null
	 * @author 045923-foss-shiwei
	 * @date 2012-12-5 下午9:18:50
	 * @param arriveDeptCode
	 * @return OutfieldEntity
	 * @exception 无
	 */
	@Override
	public OutfieldEntity queryOutfieldByOrgCode(String arriveDeptCode) {
		//返回查询结果
		return outfieldService.queryOutfieldByOrgCode(arriveDeptCode);
	}
	
	/**
	 * 根据部门查询货件所在的交接单类型
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-12 下午4:33:31
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @return List<String>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandoverType(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> queryHandoverType(String waybillNo, String serialNo,
			String orgCode) {
		//返回查询结果
		return handOverBillDao.queryHandoverType(waybillNo, serialNo, orgCode);
	}

	/**
	 * 修改配载单车牌号时，批量更新配载单中交接单中的车牌及司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:26:40
	 * @param dto
	 * @return int
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateVehicleNoAndDriverInfoFromVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto)
	 */
	@Override
	public int updateVehicleNoAndDriverInfoFromVehicleAssembleBill(
			UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto dto) {
		//获取配载车次号
		String vehicleAssembleNo = dto.getVehicleAssembleNo();
		//获取交接单号List
		List<String> handOverBillNoList = dto.getHandOverBillNoList();
		/*如果此时的交接单已经生成任务车辆，则调用任务车辆接口，同时构造交接单修改日志list*/
		//获取当前登陆人工号、姓名
		String userName = FossUserContext.getCurrentInfo().getEmpName();
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
		List<HandOverBillOptLogEntity> logList = new ArrayList<HandOverBillOptLogEntity>();
		for(String handOverBillNo : handOverBillNoList){
			//获取交接单信息
			HandOverBillEntity entity = this.queryHandOverBillByNo(handOverBillNo);
			
			//更新交接单最后修改人、时间等信息
			entity.setModifyDate(new Date());
			entity.setModifyUserCode(userCode);
			entity.setModifyUserName(userName);
			//在配载单中修改车牌号一定不是挂牌号所以交接单是否挂牌号更新为空
			entity.setBeTrailerVehicleNo(null);
			handOverBillDao.updateHandOverBill(entity, null, null, null, null, null, null);
			
			//记录修改日志
			HandOverBillOptLogEntity logEntity = new HandOverBillOptLogEntity();
			//ID
			logEntity.setId(UUIDUtils.getUUID());
			//修改类别
			logEntity.setOptType("修改基本信息");
			//交接单ID
			logEntity.setHandOverBillID(entity.getId());
			//交接单号
			logEntity.setHandOverBillNo(handOverBillNo);
			//修改人code
			logEntity.setOperatorCode(userCode);
			//修改人姓名
			logEntity.setOperatorName(userName);
			//修改日期
			logEntity.setOptTime(new Date());
			//交接时间
			logEntity.setHandOverTime(entity.getHandOverTime());
			//拼接修改内容
			StringBuffer sb = new StringBuffer();
			sb.append("修改配载单【");
			sb.append(vehicleAssembleNo);
			sb.append("】的车牌号时，同步对该交接单做以下修改：");
			sb.append("”车牌号“由“");
			sb.append(entity.getVehicleNo());
			sb.append("“修改为”");
			sb.append(dto.getVehicleNo());
			if(StringUtils.equals(entity.getDriver(), dto.getDriverCode())){
				sb.append("“。");
			}else{
				sb.append("“；     ");
				sb.append("”司机“由“");
				sb.append(entity.getDriverName());
				sb.append("(");
				sb.append(entity.getDriver());
				sb.append(")");
				sb.append("“修改为”");
				sb.append(dto.getDriverName());
				sb.append("(");
				sb.append(dto.getDriverCode());
				sb.append(")");
				sb.append("“。");
			}
			logEntity.setOptContent(sb.toString());
			logList.add(logEntity);
		}
		if(logList != null && logList.size() != 0){
			handOverBillDao.addHandOverBillModifyLogList(logList);
		}
		handOverBillDao.updateVehicleNoAndDriverInfoFromVehicleAssembleBill(dto);
		
		for(String no : handOverBillNoList){
			//调用任务车辆
			truckTaskService.handOverBillUpdateVehicleNo(no);
			truckTaskService.createTruckTaskByHandOverBill(no);
		}
		return FossConstants.SUCCESS;
	}

	/**
	 * 上报OA少货已找到时，传入到达部门code、运单号、流水号，获取出发部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-1-14 下午4:35:42
	 * @param queryEntity
	 * @return List<String>
	 * @exception 无
	 */
	@Override
	public List<String> querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(
			HandOverBillSerialNoDetailEntity queryEntity) {
		//返回查询结果
		return handOverBillDao.querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(queryEntity);
	}

	/**
	 * 根据车牌号获取司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-24 上午11:44:40
	 * @param vehicleNo
	 * @return DriverBaseDto
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryDriverInfoByVehicleNo(java.lang.String)
	 */
	@Override
	public DriverBaseDto queryDriverInfoByVehicleNo(String vehicleNo) {
		//调用接口
		RelationInfoEntity entity = queryDriverByVehicleNoService.queryDriverInfoByVehicleNo(vehicleNo);
		DriverBaseDto driver = new DriverBaseDto();
		if(entity != null){
			//司机code
			driver.setDriverCode(entity.getDriverCode());
			//司机name
			driver.setDriverName(entity.getDriverName());
			//司机电话
			driver.setDriverPhone(entity.getDriverPhone());
		}
		return driver;
	}

	/**
	 * 根据车牌号获取虚拟车牌司机信息
	 * @author 205109-foss-zenghaibin
	 * @date 2014-9-30 上午11:42:27
	 *  @param vehicleNo
	 *  @return DriverBaseDto
	 */
	@Override
	public ExpressVehiclesEntity queryDivisionDriverInfo(String vehicleNo){
		
		ExpressVehiclesEntity result = null;
		
		ExpressVehiclesEntity qcDto=new ExpressVehiclesEntity();
		qcDto.setVehicleNo(vehicleNo);
		qcDto.setActive("Y");
		List<ExpressVehiclesEntity> entityList=expressVehiclesService.queryExpressVehiclesByEntity(qcDto);
		
		
		if(CollectionUtils.isEmpty(entityList)){
			throw new TfrBusinessException("车牌"+vehicleNo+"司机信息不存在！");
		}
		
		result = entityList.get(0);
		
		EmployeeEntity employeeInfo = employeeService.queryEmployeeByEmpCode(result.getEmpCode());
		if(employeeInfo == null){
			throw new TfrBusinessException("车牌"+vehicleNo+"司机信息不存在！");
		}
		result.setEmpName(employeeInfo.getEmpName());
		
		return result;
	}
	/**
	 * 查询运单是否需要代打木架
	 * 	返回值大于0说明需要代打木架
	 * @author ibm-zhangyixin
	 * @date 2013-1-23 下午3:52:40
	 * @param waybillNo
	 * @return Long
	 * @exception 无
	 */
	@Override
	public Long queryWayBillPacked(String waybillNo) {
		//查询运单是否需要代打木架
		//返回值大于0说明需要代打木架
		return handOverBillDao.queryWayBillPacked(waybillNo);
	}
	
	/**
	 * 根据到达部门code、出发部门code、运单号、指定的交接单号list中匹配出交接单号，用于上报OA少货时获取交接单号
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午4:53:35
	 * @param queryDto
	 * @return List<String>
	 * @exception 无
	 */
	@Override
	public List<String> queryHandOverBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto){
		return handOverBillDao.queryHandOverBillNoForUnloadTaskLackGoods(queryDto);
	}
	
	/**
	 * 获取当前部门的上级部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:48:53
	 */
	@Override
	public String querySuperiorOrgCode(){
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			LOGGER.error("###################根据部门（code：" + orgCode + "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		}else{
			return superEntity.getCode();
		}
	}

	/**
	 * 根据运单号、到达部门code获取被交接过的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:38:00
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(
			String waybillNo, String destOrgCode) {
		return handOverBillDao.queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(waybillNo, destOrgCode);
	}

	/**
	 * 交接单新增、修改界面，一步查询运单、流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-20 上午11:07:22
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryWaybillStockListAndSerialNoStockList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto, int, int)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockListAndSerialNoStockList(
			QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,
			int limit, int start) {
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		BigDecimal expressConvertParameter=null;
		try{
			 expressConvertParameter=this.queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积配置参数失败！");
		}
		if(expressConvertParameter==null){
			expressConvertParameter=new BigDecimal(1);
		}else{
			expressConvertParameter = expressConvertParameter.divide(new BigDecimal(LoadConstants.VOLUME_SIZE));
		}
		
		//GUI嵌入页面时，combobox为空时，获取值不是null，而是空数组，此处做处理
		if(CollectionUtils.isEmpty(queyWaybillForHandOverBillDto.getTransPropertyList()) || 
				StringUtils.isBlank(queyWaybillForHandOverBillDto.getTransPropertyList().get(0))){
			queyWaybillForHandOverBillDto.setTransPropertyList(null);
		}/*else if(StringUtils.isBlank(queyWaybillForHandOverBillDto.getTransPropertyList().get(0))){
			//规避空指针异常
			queyWaybillForHandOverBillDto.setTransPropertyList(null);
		}*/
		
		queyWaybillForHandOverBillDto.setCurrentDeptCode(currentDeptCode);
		//获取下一部门code的list
		List<String> arriveDeptList = queyWaybillForHandOverBillDto.getArriveDeptList();
		//如果为外发交接单，则需要将前台传入的公司编码转换为网点编码
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_PARTIALLINE, queyWaybillForHandOverBillDto.getHandOverType())){
			arriveDeptList = this.queryAgencySiteByComCode(arriveDeptList.get(0));
		}
		//如果为落地配交接单，则需要将前台传入的公司编码转换为网点编码
		if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_LDP, queyWaybillForHandOverBillDto.getHandOverType())){
			arriveDeptList = this.queryLdpSiteByComCode(arriveDeptList.get(0));
		}
		//添加整车下一到达部门常量
		arriveDeptList.add(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
		arriveDeptList.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
		queyWaybillForHandOverBillDto.setArriveDeptList(arriveDeptList);
		//设置必走货时间
		Calendar cal = Calendar.getInstance();
		//每日下午四点
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), LoadConstants.SONAR_NUMBER_16, 0, 0);
		Date priorityTime = cal.getTime();
		queyWaybillForHandOverBillDto.setPriorityTime(priorityTime);
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queyWaybillForHandOverBillDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);

		//货物类型(A,B)货
		if(StringUtils.isNotEmpty(queyWaybillForHandOverBillDto.getGoodsType())) {
			if(StringUtils.equals(queyWaybillForHandOverBillDto.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_A_TYPE)) {
				//A_TYPE to A
				queyWaybillForHandOverBillDto.setGoodsType(WaybillConstants.GOODS_TYPE_A);
			} else if (StringUtils.equals(queyWaybillForHandOverBillDto.getGoodsType(), LoadConstants.LOAD_GOODSTYPE_B_TYPE)) {
				//B_TYPE to B
				queyWaybillForHandOverBillDto.setGoodsType(WaybillConstants.GOODS_TYPE_B);
			}
		}
		//如果运单号的查询条件不为空
		//则忽略调货物类型的查询条件
		if(StringUtils.isNotEmpty(queyWaybillForHandOverBillDto.getWaybillNo())) {
			queyWaybillForHandOverBillDto.setGoodsType("");
		}
		//返回查询结果
		List<HandOverBillDetailEntity> waybillStockList = handOverBillDao.queryWaybillStockList(queyWaybillForHandOverBillDto,limit, start);
		
		//查询流水号
		//构造流水号查询条件
		QuerySerialNoListForWaybillConditionDto queryDto = new QuerySerialNoListForWaybillConditionDto();
		queryDto.setCurrentDeptCode(currentDeptCode);
		queryDto.setNextDeptCodeList(arriveDeptList);
		queryDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//循环获取流水号list
		for(HandOverBillDetailEntity waybillStock : waybillStockList){
			queryDto.setIsJoinCar(waybillStock.getIsJoinCar());
			queryDto.setWaybillNo(waybillStock.getWaybillNo());
			List<SerialNoStockEntity> serialNoStockList = this.querySerialNoStockListByWaybillNo(queryDto);
			waybillStock.setSerialNoStockList(serialNoStockList);
			//运输性质
			String productCode = waybillStock.getTransPropertyCode();
			BigDecimal weightTotal =  BigDecimal.ZERO;
			BigDecimal volumnTotal = BigDecimal.ZERO;
			/**
			* 获取所有的快递3级产品code
			* zx
			* 2015-09-06
			* */
			List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
			//如果是快递没有上计泡机 则 计算重泡比
				for(SerialNoStockEntity entity:serialNoStockList){
					if(CollectionUtils.isNotEmpty(productCodeList)
							&&productCodeList.contains(productCode)
							&& StringUtils.equals(entity.getBeScan(), FossConstants.NO)
							){
//			for(SerialNoStockEntity entity:serialNoStockList){
//				//如果是快递没有上计泡机 则 计算重泡比
//				if((StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_PACKAGE) 
//						||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_RCP)
//						||StringUtils.equals(productCode, LoadConstants.PRODUCT_CODE_EPEP))
//						&& StringUtils.equals(FossConstants.NO, entity.getBeScan())){
					BigDecimal weight = entity.getWeight()==null?new BigDecimal(0):entity.getWeight();
					BigDecimal volumn = expressConvertParameter.multiply(entity.getWeight()==null?new BigDecimal(0):entity.getWeight()).divide(new BigDecimal(1), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					//重新设置流水号明细的重量体积
					entity.setWeight(weight);
					entity.setVolumn(volumn);
					//累加改运单的重量体积
					weightTotal=weightTotal.add(weight);
					volumnTotal=volumnTotal.add(volumn);
				}else{
					weightTotal=weightTotal.add(entity.getWeight());
					volumnTotal=volumnTotal.add(entity.getVolumn());
				}
				
			}
			//设置转换后的重量体积
			waybillStock.setWeightAc(weightTotal);
			waybillStock.setCubageAc(volumnTotal);
		}
		/**
		 * 查询非本部门库存运单流水
		 * 332219
		 * 2016-9-19
		 **/
		 //映射网点集
		 List<DeptTransferMappingEntity> deptTransferMappinglist = new ArrayList<DeptTransferMappingEntity>();
		 //非本部门库存运单结果集
		 List<HandOverBillDetailEntity> saleWaybillStockList = new ArrayList<HandOverBillDetailEntity>();
		 //查询出发部门
		 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(currentDeptCode);
		 //不能在营业部查询到出发部门，则出发部门为外场不需要查询非本部门库存
		 if(departDept != null){
			 //出发部门是营业部
			 if("N".equals(departDept.getIsTwoLevelNetwork()) && "N".equals(departDept.getIsLeagueSaleDept())){
				 deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(currentDeptCode);
			 }
	         //出发部门是一级网点
			 if("N".equals(departDept.getIsTwoLevelNetwork()) && "Y".equals(departDept.getIsLeagueSaleDept())){
				 deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(currentDeptCode);
			 }
			 //映射网点集合不为空查询非本部门库存
			 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
				 //查询非本部门库存集
				 saleWaybillStockList = handOverBillDao.querySaleWaybillStockList(queyWaybillForHandOverBillDto,limit, start);
			 }
			 //非本部门查询数据不为空则加入结果集
			 if(saleWaybillStockList!=null && saleWaybillStockList.size()>0){
				//循环获取流水号list
				for(HandOverBillDetailEntity waybillStock : saleWaybillStockList){
					queryDto.setIsJoinCar(waybillStock.getIsJoinCar());
					queryDto.setWaybillNo(waybillStock.getWaybillNo());
					List<SerialNoStockEntity> serialNoStockList = this.querySaleSerialNoStockListByWaybillNo(queryDto);
					waybillStock.setSerialNoStockList(serialNoStockList);
					//运输性质
					String productCode = waybillStock.getTransPropertyCode();
					BigDecimal weightTotal =  BigDecimal.ZERO;
					BigDecimal volumnTotal = BigDecimal.ZERO;
					/**
					* 获取所有的快递3级产品code
					* zx
					* 2015-09-06
					* */
					List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
					//如果是快递没有上计泡机 则 计算重泡比
						for(SerialNoStockEntity entity:serialNoStockList){
							if(CollectionUtils.isNotEmpty(productCodeList) && productCodeList.contains(productCode) && StringUtils.equals(entity.getBeScan(), FossConstants.NO)){
								BigDecimal weight = entity.getWeight()==null?new BigDecimal(0):entity.getWeight();
								BigDecimal volumn = expressConvertParameter.multiply(entity.getWeight()==null?new BigDecimal(0):entity.getWeight()).divide(new BigDecimal(1), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
								//重新设置流水号明细的重量体积
								entity.setWeight(weight);
								entity.setVolumn(volumn);
								//累加改运单的重量体积
								weightTotal=weightTotal.add(weight);
								volumnTotal=volumnTotal.add(volumn);
							}else{
								weightTotal=weightTotal.add(entity.getWeight());
								volumnTotal=volumnTotal.add(entity.getVolumn());
							}
						
					}
					//设置转换后的重量体积
					waybillStock.setWeightAc(weightTotal);
					waybillStock.setCubageAc(volumnTotal);
				 }
				 waybillStockList.addAll(saleWaybillStockList);
			 }
		 }
		//返回结果
		return waybillStockList;
	}
	
	/**
	 * 内部调用，根据运单号获取流水号库存，分为库存表和合车
	 * @author 045923-foss-shiwei
	 * @date 2013-4-20 上午11:33:22
	 */
	private List<SerialNoStockEntity> querySerialNoStockListByWaybillNo(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {	
		String isJoinCar = querySerialNoListForWaybillConditionDto.getIsJoinCar();
		if(StringUtils.equals(isJoinCar, FossConstants.YES)){
			return handOverBillDao.queryJoinCarSerialNoStockList(querySerialNoListForWaybillConditionDto);
		}else{
			//返回查询结果
			return handOverBillDao.querySerialNoStockList(querySerialNoListForWaybillConditionDto);
		}
	}
	
	
	/**
	 * 内部调用，根据运单号获取非本部门流水号库存，分为非本部门库存表和合车
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午11:33:22
	 */
	private List<SerialNoStockEntity> querySaleSerialNoStockListByWaybillNo(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {	
		String isJoinCar = querySerialNoListForWaybillConditionDto.getIsJoinCar();
		if(StringUtils.equals(isJoinCar, FossConstants.YES)){
			return handOverBillDao.querySaleJoinCarSerialNoStockList(querySerialNoListForWaybillConditionDto);
		}else{
			//返回查询结果
			return handOverBillDao.querysaleSerialNoStockList(querySerialNoListForWaybillConditionDto);
		}
	}
	
	/**
	 * 内部调用，根据偏线代理公司code获取其下网点
	 * @author 045923-foss-shiwei
	 * @date 2013-5-14 上午10:19:09
	 */
	private List<String> queryAgencySiteByComCode(String comCode){
		List<OuterBranchEntity> siteList = this.vehicleAgencyDeptService.queryOuterBrangchsByCode(comCode);
		List<String> siteCodeList = new ArrayList<String>();
		StringBuffer logSb = new StringBuffer();
		logSb.append("#######根据代理公司(code:" + comCode + ")获取到的代理网点为：");
		if(CollectionUtils.isNotEmpty(siteList)){
			for(OuterBranchEntity siteEntity : siteList){
				logSb.append(siteEntity.getAgentDeptName());
				logSb.append("/");
				logSb.append(siteEntity.getAgentDeptCode());
				logSb.append(",");
				siteCodeList.add(siteEntity.getAgentDeptCode());
			}
		}
		LOGGER.error(logSb.toString());
		return siteCodeList;
	}
	
	/**
	 * 内部调用，根据公司获取落地配网点
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 上午9:51:00
	 */
	private List<String> queryLdpSiteByComCode(String comCode){
		List<OuterBranchExpressEntity> siteList = ldpAgencyDeptService.queryLdpAgencyDeptsByagencyCompanyCode(comCode,FossConstants.ACTIVE);
		List<String> siteCodeList = new ArrayList<String>();
		StringBuffer logSb = new StringBuffer();
		logSb.append("#######根据快递代理公司(code:" + comCode + ")获取到的代理网点为：");
		if(CollectionUtils.isNotEmpty(siteList)){
			for(OuterBranchExpressEntity siteEntity : siteList){
				logSb.append(siteEntity.getAgentDeptName());
				logSb.append("/");
				logSb.append(siteEntity.getAgentDeptCode());
				logSb.append(",");
				siteCodeList.add(siteEntity.getAgentDeptCode());
			}
		}
		LOGGER.error(logSb.toString());
		return siteCodeList;
	}
	
	/**
	 * 校验车牌号和司机的所有权，两者必须一致
	 * @author 045923-foss-shiwei
	 * @date 2013-5-16 下午4:23:10
	 */
	private String validateVehicleAndDriverOwnerShip(HandOverBillEntity handOverBillEntity,OrgAdministrativeInfoEntity org){
		
		String vehicleNo = handOverBillEntity.getVehicleNo();
		String driverCode=handOverBillEntity.getDriver();
		if(StringUtils.isNotBlank(vehicleNo) && StringUtils.isNotBlank(driverCode)){
			//对与出发部门是外场或者分部 的 短途交接单 不需要校验 “德XXXXX”的车牌号
			if(null != org
				&& ( StringUtils.equals(org.getExpressBranch(),FossConstants.YES) ||StringUtils.equals(org.getTransferCenter(),FossConstants.YES)) 
				&& (StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)) 
				&& (StringUtils.equals(handOverBillEntity.getVehicleNo().substring(0, 1),"德"))){
				
				return ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY;
			}
			VehicleAssociationDto vehicleInfo = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
			if(vehicleInfo != null){
				String vehicleType = vehicleInfo.getVehicleOwnershipType();
				//如果为外请车
				if(StringUtils.equals(vehicleType, ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)){
					LeasedDriverEntity queryCon = new LeasedDriverEntity();
					queryCon.setIdCard(driverCode);
					LeasedDriverEntity driver = this.leasedDriverService.queryLeasedDriverBySelective(queryCon);
					if(driver == null){
						throw new TfrBusinessException("外请车必须使用外请司机！");
					}
					//如果为公司车
				}else if(StringUtils.equals(vehicleType, ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)){
					DriverAssociationDto driver = this.ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
					if(driver == null){
						throw new TfrBusinessException("公司车必须使用公司司机！");
					}
				}
				return vehicleType;
			}else{
				throw new TfrBusinessException("选择的车辆不存在！");
			}
		}
		return null;
	}

	/**
	 * 判断是否为营业部
	 * @author 045923-foss-shiwei
	 * @date 2013-5-23 上午9:49:58
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryOrgBeSalesDepartment(java.lang.String)
	 */
	@Override
	public String queryOrgBeSalesDepartment(String orgCode) {
		if(StringUtils.isBlank(orgCode)){
			return FossConstants.NO;
		}
		OrgAdministrativeInfoEntity org = this.orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if(org == null){
			return FossConstants.NO;
		}
		return org.getSalesDepartment();
	}
	
	/**
	 * 查询某运单是否有在途的、分批交接的记录
	 * @return null，表示没有在途的交接记录
	 * 				  "Y"，表示分批交接
	 * 				  "N"，表示没有 分批交接
	 * @author 045923-foss-shiwei
	 * @date 2013-5-24 下午4:04:39
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryGoodsBeSeparatedByWaybillNo(java.lang.String)
	 */
	@Override
	public String queryGoodsBeSeparatedFromHandOverBillByWaybillNo(String waybillNo){
		if(StringUtils.isBlank(waybillNo)){
			return null;
		}
		
		/*
		 * 获取运单所有的在途交接记录
		 */
		List<HandOverBillDetailEntity> hList = handOverBillDao.queryGoodsOnTheWayRecordsByWaybillNo(waybillNo);
		
		/*
		 * 如果没有符合条件的记录，返回null
		 */
		if(CollectionUtils.isEmpty(hList)){
			return null;
		}
		
		/*
		 * 如果返回多条记录，则为分批交接
		 */
		if(hList.size() > 1){
			return FossConstants.YES;
		}
		
		/*
		 * 如果为单条，则判断交接件数是否等于开单件数
		 */
		WaybillEntity waybillEntity = this.waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//开单件数
		Integer waybillPieces = waybillEntity.getGoodsQtyTotal();
		//交接件数
		HandOverBillDetailEntity hWaybill = hList.get(0);
		BigDecimal hPieces = hWaybill.getPieces();
		//如果交接件数不等于开单件数，则为分批交接
		if(waybillPieces.compareTo(hPieces.intValue()) != 0){
			return FossConstants.YES;
		}
		
		return FossConstants.NO;
	}

	/**
	 * 获取三级产品类型
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 上午10:00:37
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryProductList()
	 */
	@Override
	public List<BaseDataDictDto> queryProductList() {
		List<BaseDataDictDto> productList = stockService.queryProductList();
		return productList;
	}

	/**
	 * 用于job调用，漂移交接单中未处理的待办至下一部门
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:56:03
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#driftedToDoMsgAfterHandOvered()
	 */
	@Override
	public int driftedToDoMsgAfterHandOvered() {
		//获取配置参数中的日期参数
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_HANDOVERBILL_DRIFTED_TODO_DATE, 
				FossConstants.ROOT_ORG_CODE);
		Date endCreateTime = null;
		if(defaultBizHourSlice == null
			|| StringUtils.isEmpty(defaultBizHourSlice.getConfValue())){
			LOGGER.error("###########################读取配置参数中的交接单待办漂移时间为空！##########################");
			throw new TfrBusinessException("读取配置参数中的交接单待办漂移时间为空！");
		}else{
			endCreateTime = DateUtils.strToDate(defaultBizHourSlice.getConfValue());
		}
		List<QueryHandOverBillDto> baseDtoList = handOverBillDao.queryToDoUnDriftedHandOverBillList(endCreateTime);
		for(QueryHandOverBillDto baseDto : baseDtoList){
			//获取流水号
			String handOverBillNo = baseDto.getHandOverBillNo();
			List<HandOverBillSerialNoDetailEntity> serialNoList = this.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
			//调用待办漂移接口
			try{
				loadService.driftToDoAfterHandOvered(baseDto, serialNoList);
			}catch(Exception e){
				//日志
				LOGGER.error("调用pkp接口进行待办漂移发生异常，交接单号：" + handOverBillNo + "，异常信息：" + e.getMessage());
				continue;
			}
		}
		
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据运单号查询该运单所有的被交接记录，并按交接时间排序
	 * @author 045923-foss-shiwei
	 * @date 2013-6-14 下午7:55:42
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOveredRecordsByWaybillNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo) {
		return handOverBillDao.queryHandOveredRecordsByWaybillNo(waybillNo);
	}

	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillByLoadTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-18下午3:29:58
	 */ 
	@Override
	public HandOverBillEntity queryHandOverBillByLoadTaskNo(String taskNo) {
		return handOverBillDao.queryHandOverBillByLoadTaskNo(taskNo);
	}

	/* (非 Javadoc) 
	* <p>Title: queryBeLdpHandOveredByWaybillNo</p> 
	* <p>Description: </p> 
	* @param waybillNo
	* @return 
	* @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryBeLdpHandOveredByWaybillNo(java.lang.String) 
	*/
	@Override
	public boolean queryBeLdpHandOveredByWaybillNo(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			return false;
		}
		List<HandOverBillEntity> billList = handOverBillDao.queryHandOveredRecordsByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(billList)){
			for(HandOverBillEntity bill : billList){
				if(StringUtils.equals(bill.getHandOverType(), LoadConstants.HANDOVER_TYPE_LDP)){
					return true;
				}
			}
		}
		return false;
	}
	
	/* (非 Javadoc) 
	 * 此方法根据运单号查询所有的交接单（包括废单）
	* <p>Title: queryBeLdpHandOveredAllByWaybillNo</p> 
	* <p>Description: </p> 
	* @param waybillNo
	* @return                                                                               
	* @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryBeLdpHandOveredAllByWaybillNo(java.lang.String) 
	*/
	@Override
	public boolean queryBeLdpHandOveredAllByWaybillNo(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			return false;
		}                                                   
		List<HandOverBillEntity> billList = handOverBillDao.queryHandOveredAllRecordsByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(billList)){
			return true;
				}
		return false;
	}
	/**
	 * (non-Javadoc)
	 * @param waybillNo
	 * @param totalPiece
	 * @param totalweigth
	 * @param totalVolumne
	 * @param goodsName
	 * @param goodsPackage 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateHandOverbillforMakeUpWaybill(java.lang.String, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String updateHandOverbillforMakeUpWaybill(MakeUpWaybillEntity makeUpWaybillEntity) {
		//交接单（t_opt_handoverbill）：总重量，总体积，卡货（城际等）重量体积、
		//交接单明细（t_opt_handoverbill_detail）：交接重量、实际交接重量、交接体积、实际交接体积、（货物名称、货物包装、收货部门、到达部门、收货人、）
		//交接单日志（t_opt_handoverbill_log）：新增一条修改基础信息的日志
		LOGGER.error("=============补录运单-更新交接单信息-start================");
		String waybillNo=makeUpWaybillEntity.getWaybillNo();
		BigDecimal totalPiece = makeUpWaybillEntity.getQuantity();
		BigDecimal totalweigth = makeUpWaybillEntity.getWeight();
		BigDecimal totalVolumne = makeUpWaybillEntity.getVolume();
		if((totalweigth==null||totalweigth.equals(BigDecimal.ZERO))&&(totalVolumne==null||totalVolumne.equals(BigDecimal.ZERO))){
			LOGGER.error("补录运单传递的重量体积为0");
			return "error";
		}
		if(totalPiece==null||totalPiece.equals(BigDecimal.ZERO)){
			LOGGER.error("补录运单传递参数件数为0");
			return "error";
		}
		List<HandOverBillEntity> handoverbilList = handOverBillDao.queryHandOveredRecordsByWaybillNo(waybillNo);
		if(CollectionUtils.isEmpty(handoverbilList)){
			LOGGER.error("查询不到需要更新的交接单！");
			return "success";
		}
		//要更新的交接单
		List<HandOverBillEntity> updatehandoverbills = new ArrayList<HandOverBillEntity>();
		//要更新的运单
		List<HandOverBillDetailEntity> updateHandoverbillDetails = new ArrayList<HandOverBillDetailEntity>();
		//记录日志
		List<HandOverBillOptLogEntity> logs = new ArrayList<HandOverBillOptLogEntity>();
		//交接单号
		String handOverBillNo="";
		//总重量
		BigDecimal handoverbillTotalweigth = BigDecimal.ZERO;
		//总体积
		BigDecimal handoverbillTotalVolumn = BigDecimal.ZERO;
		//空运总体积
		BigDecimal handoverbillAFVolumn;
		//空运总重量
		BigDecimal handoverbillAFweigth;
		//城运总重量
		BigDecimal handoverbillFSFweigth;
		//城运总重量
		BigDecimal handoverbillFSFVolumn;
		//卡航总重量
		BigDecimal handoverbillFLFweigth;
		//卡航总重量
		BigDecimal handoverbillFLFVolumn;
		
		for(HandOverBillEntity handoverbill:handoverbilList){
			
			handOverBillNo = handoverbill.getHandOverBillNo();
			 
			List<HandOverBillDetailEntity> handOverBillDetailList=handOverBillDao.queryHandOverBillDetailByNo(handOverBillNo, waybillNo);
			if(CollectionUtils.isEmpty(handOverBillDetailList)||handOverBillDetailList.size()<=0){
				LOGGER.error("查询不到需要更新的交接单！");
				return "success";
			}
			HandOverBillEntity updatehandoverbill = new HandOverBillEntity();//更新交接单实体
			HandOverBillDetailEntity updateHandoverbillDetail=new HandOverBillDetailEntity();//更新交接单明细实体
			//交接单明细
			HandOverBillDetailEntity handoverbillDetail = handOverBillDetailList.get(0);
			BigDecimal propertyweigth= BigDecimal.ZERO;
			if(totalweigth!=null&&!totalweigth.equals(BigDecimal.ZERO)){
				//需要更新交接单明细中对应运单的重量
				BigDecimal updateDetailWeigth = totalweigth.divide(totalPiece,2,BigDecimal.ROUND_HALF_UP).multiply(handoverbillDetail.getPieces());
				//减去原有重量后的重量
				propertyweigth = updateDetailWeigth.subtract(handoverbillDetail.getWeightAc());
				//公式 交接单总重量 = 交接单总重量+ （补录运单重量/补录运单件数)x该运单交接件数-该运单交接的实际重量
				if(handoverbill.getWeightTotal()==null){
					handoverbillTotalweigth=propertyweigth;
				}else{
					handoverbillTotalweigth = handoverbill.getWeightTotal().add(propertyweigth);
				}
				
				
				updatehandoverbill.setWeightTotal(handoverbillTotalweigth);
				updateHandoverbillDetail.setWeight(updateDetailWeigth);
				updateHandoverbillDetail.setWeightAc(updateDetailWeigth);				
			}
			
			BigDecimal propertyVolumn = BigDecimal.ZERO;
			if(totalVolumne!=null && !totalVolumne.equals(BigDecimal.ZERO)){
				//需要更新的交接单明细体积
				BigDecimal updateDetailVolumn = totalVolumne.divide(totalPiece,2,BigDecimal.ROUND_HALF_UP).multiply(handoverbillDetail.getPieces());
				 propertyVolumn =updateDetailVolumn.subtract(handoverbillDetail.getCubageAc());
				//交接重体积
				if(handoverbill.getVolumeTotal()==null){
					handoverbillTotalVolumn = propertyVolumn;
				}else{
					handoverbillTotalVolumn = handoverbill.getVolumeTotal().add(propertyVolumn);				
				}
				
				updateHandoverbillDetail.setCubage(updateDetailVolumn);
				updateHandoverbillDetail.setCubageAc(updateDetailVolumn);
				updatehandoverbill.setVolumeTotal(handoverbillTotalVolumn);
			}
			
			updateHandoverbillDetail.setWaybillNo(waybillNo);
			updateHandoverbillDetail.setHandOverBillNo(handOverBillNo);
			updateHandoverbillDetail.setPieces(handoverbillDetail.getPieces());
			updateHandoverbillDetail.setNote(handoverbillDetail.getNote());
			updateHandoverbillDetails.add(updateHandoverbillDetail);
			
			if(StringUtils.equals("精准空运", handoverbillDetail.getTransProperty())){
				
				if(handoverbill.getGoodsWeightAF()==null){
					handoverbillAFweigth = propertyweigth;
				}else{
					handoverbillAFweigth = handoverbill.getGoodsWeightAF().add(propertyweigth);					
				}
				if(handoverbill.getGoodsVolumeAF()==null){
					handoverbillAFVolumn = propertyVolumn;
				}else{
					handoverbillAFVolumn = handoverbill.getGoodsVolumeAF().add(propertyVolumn);
				}
				updatehandoverbill.setGoodsVolumeAF(handoverbillAFVolumn);
				updatehandoverbill.setGoodsWeightAF(handoverbillAFweigth);
			}else if(StringUtils.equals("精准卡航",handoverbillDetail.getTransProperty())){
				if(handoverbill.getGoodsWeightFLF()==null){
					handoverbillFLFweigth = propertyweigth;
				}else{
					handoverbillFLFweigth = handoverbill.getGoodsWeightFLF().add(propertyweigth);					
				}
				if(handoverbill.getGoodsVolumeFLF()==null){
					handoverbillFLFVolumn = propertyVolumn;
				}else{
					handoverbillFLFVolumn = handoverbill.getGoodsVolumeFLF().add(propertyVolumn);					
				}
				updatehandoverbill.setGoodsVolumeFLF(handoverbillFLFVolumn);
				updatehandoverbill.setGoodsWeightFLF(handoverbillFLFweigth);
			}else if(StringUtils.equals("精准城运",handoverbillDetail.getTransProperty())){
				if(handoverbill.getGoodsWeightFSF()==null){
					handoverbillFSFweigth = propertyweigth;
				}else{
					handoverbillFSFweigth = handoverbill.getGoodsWeightFSF().add(propertyweigth);
				}
				if(handoverbill.getGoodsVolumeFSF()==null){
					handoverbillFSFVolumn = propertyVolumn;
				}else{
					handoverbillFSFVolumn = handoverbill.getGoodsVolumeFSF().add(propertyVolumn);
				}
				
				
				updatehandoverbill.setGoodsVolumeFSF(handoverbillFSFVolumn);
				updatehandoverbill.setGoodsWeightFSF(handoverbillFSFweigth);
			}
			updatehandoverbill.setHandOverBillNo(handOverBillNo);
			updatehandoverbills.add(updatehandoverbill);//添加更新的交接单
			logs.add(addLogForMakeUpWaybill(handoverbill,handoverbillTotalweigth,handoverbillTotalVolumn));//添加日志
		}
		
		handOverBillDao.updateHandOverbillWeigthAndVolumn(updatehandoverbills,updateHandoverbillDetails,logs);
		LOGGER.error("=============补录运单-更新交接单信息-end================");
		return "success";
	}
	/**
	 * 私有方法，补录运单添加交接单日志
	 * @param oldHandoverbill
	 * @param weight
	 * @param volumn
	 * @return
	 */
	private HandOverBillOptLogEntity addLogForMakeUpWaybill(HandOverBillEntity oldHandoverbill,BigDecimal weight,BigDecimal volumn){
		HandOverBillOptLogEntity logEntity = new HandOverBillOptLogEntity();
		//id
		logEntity.setId(UUIDUtils.getUUID());
		//操作时间
		logEntity.setOptTime(new Date());
		//创建人
		logEntity.setCreateUser("系统");
		//交接单id  不知道id能否获取到？
		logEntity.setHandOverBillID(oldHandoverbill.getId());
		//交接单号
		logEntity.setHandOverBillNo(oldHandoverbill.getHandOverBillNo());
		//交接时间
		logEntity.setHandOverTime(oldHandoverbill.getHandOverTime());
		//修改人code
		logEntity.setOperatorCode("系统补录运单");
		//修改人name
		logEntity.setOperatorName("系统补录运单");
		logEntity.setOptType("修改基本信息");
		StringBuffer sb = new StringBuffer();
		if(!weight.equals(BigDecimal.ZERO)){
			sb.append("”总重量“由");
			sb.append(oldHandoverbill.getWeightTotal());
			sb.append("修改为");
			sb.append(weight);
			sb.append("；     ");
		}
		if(!volumn.equals(BigDecimal.ZERO)){
			sb.append("”总体积“由");
			sb.append(oldHandoverbill.getVolumeTotal());
			sb.append("修改为");
			sb.append(volumn);
			sb.append("；     ");
		}
		
		
		logEntity.setOptContent(sb.toString());
		return logEntity;
	}

	/**
	 * @author niuly
	 * @date 2014-3-6上午11:07:59
	 * @function 根据车辆明细ids查询交接单明细信息
	 * @param detailIds
	 * @return
	 */
	@Override
	public List<HandOverBillDetailEntity> queryHandOverBillDetailByIds(List<String> detailIdList) {
		return handOverBillDao.queryHandOverBillDetailByIds(detailIdList);
	}

	/**
	 * @author niuly
	 * @date 2014-3-6下午2:30:02
	 * @function 根据运单号和到达部门编码查询到达总件数
	 * @param waybillNo
	 * @param destOrgCode
	 * @return
	 */
	@Override
	public int queryWaybillCountByNoAndDept(String waybillNo,String destOrgCode) {
		return handOverBillDao.queryWaybillCountByNoAndDept(waybillNo,destOrgCode);
	}   
	
	/**
	 * 检查交接单在租车标记表的预提状态，如果为预提中或者已预提则不允许修改
	 * @author 205109-foss-zenghaibin
	 * @date 2014-08-14 上午08:27:39
	 * @param handOverBillNo
	 */
	@Override
	public String queryHoldingState(String handOverBillNo){
		//BillPayableService.queryBySourceBillNOs()
		List<HoldingStateDto> holdingStateDtoList=handOverBillDao.queryHoldingState(handOverBillNo);
		String holdingState="";//预提状态
		String payState="";//付款状态
		HoldingStateDto holdingStateDto;
		if(null!=holdingStateDtoList&&holdingStateDtoList.size()>0){
			List<String> holdList=new ArrayList<String>();
			List<BillPayableEntity> billPayableEntityList=new ArrayList<BillPayableEntity>();
			BillPayableEntity billPayableEntity=new BillPayableEntity();
			holdingStateDto=holdingStateDtoList.get(0);
			holdList.add(holdingStateDto.getTempRentalMarkNo());
			billPayableEntityList=billPayableService.queryBySourceBillNOs(holdList ,null,"Y");
			if(null!=billPayableEntityList&&billPayableEntityList.size()>0){
			 billPayableEntity= billPayableEntityList.get(0); 
			 payState=billPayableEntity.getPayStatus();
			}
			holdingState=holdingStateDto.getAccruedState();
			
		}
		
		 
		if("TG".equals(holdingState)||"TD".equals(holdingState)||"Y".equals(payState)){//"TG"预提中，“TD”已预提
			
			return "Y";//在预提中或者已预提，交接单不可修改
		}else{
			
			return "N";//交接单 可修改
		}
	}
	
	/**
	 * 根据运单号查询代理运单号
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-27
	 * @param waybillNo
	 * @return
	 * @update-author 269701-lln-2015/11/02
	 * @update-param waybillNo运单号 serialNo流水号
	 */
	@Override
	public String getAgentWaybillNoByWaybillNo(String waybillNo,String serialNo){
		 String agentNo = handOverBillDao.getAgentWaybillNoByWaybillNo(waybillNo,serialNo);
		 return StringUtils.equals(agentNo, null)?"":agentNo;
	}

	@Override
	public List<HandOverBillDetailEntity> queryHandoverBillDetailByWaybillNoAndOrgCord(
			String waybillNo, String origOrgCode) {
		return handOverBillDao.queryHandoverBillDetailByWaybillNoAndOrgCord(waybillNo, origOrgCode);
	}
	
	//2015/9/1 商务专递 272681
	@Override
	public List<HandOverBillSerialNoDetailEntity> getBusAirHandOverBillSerialNoDetailsByWayBillNo( String wayBillNo, String handOverBillNo) {
		
		BigDecimal param = null;
		try{
			String currentDeptCode = this.querySuperiorOrgCode();
			param =	queryExpressConverParameter(currentDeptCode);
		}catch(Exception e){
			LOGGER.error("获取快递转换体积参数失败！");
		}
		List<HandOverBillSerialNoDetailEntity> serialNoList= handOverBillDao.getBusAirHandOverBillSerialNoDetailsByWayBillNo(wayBillNo, handOverBillNo);
		//没上计泡机的流水号需要计算重泡比
		if(param!=null){
			for(HandOverBillSerialNoDetailEntity serialNoEntity:serialNoList){
				if((StringUtils.equals(serialNoEntity.getProductCode(), "PACKAGE")
						||StringUtils.equals(serialNoEntity.getProductCode(), "RCP")
						||StringUtils.equals(serialNoEntity.getProductCode(), "EPEP")
						||StringUtils.equals(serialNoEntity.getProductCode(), "DEAP"))
						&& StringUtils.equals(serialNoEntity.getBeScan(), FossConstants.NO)
						){
					BigDecimal conVolumn =param.multiply(serialNoEntity.getWeight()==null?new BigDecimal(0):serialNoEntity.getWeight

							()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE), LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					serialNoEntity.setVolumn(conVolumn);	
				}
				 
			}
		}
		
	   return serialNoList;
	}
	
	/**
	 * 根据商务专递交接单号获取交接单运单列表
	 * @author 272681 chenlei
	 * @date 2015/9/17
	 * @param handOverBillNo 交接单号
	 * @return List<HandOverBillDetailEntity>
	 * @exception 无
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryAirHandOverBillDetailByNo(java.lang.String)
	 */
	@Override
	public List<HandOverBillDetailEntity> queryBusAirHandOverBillDetailByNo(String handOverBillNo) {
		//调用dao,返回商务专递交接单的运单列表272681
		return handOverBillDao.queryBusAirHandOverBillDetailByNo(handOverBillNo,null);
			
	}
	
	/** 
	 * @Title: queryHandOverBillNoByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对多的关系 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryHandOverBillNoByLoadTaskNo(java.lang.String)
	 * @author: 272681
	 * @throws 
	 * Date:2015-11-24
	 */ 
	@Override
	public String queryHandOverBillNoByLoadTaskNo(String taskNo) {
		return handOverBillDao.queryHandOverBillNoByLoadTaskNo(taskNo);
	}

	/** 
	 * @Title: queryArriveDeptList 
	 * @Description: 根据交接单VO信息，查询出他所有的到达部门
	 * @param handOverBillVo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#queryArriveDeptList(HandOverBillVo handOverBillVo)
	 * @author: 332219
	 * @throws 
	 * Date:2016-9-10
	 */ 
	@Override
	public List<String> queryArriveDeptList(HandOverBillVo handOverBillVo) {
		//接收到达部门code的集合
		List<String> arriveDeptList = new ArrayList<String>();
		//获取查询条件dto
		QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto = handOverBillVo.getQueryWaybillForHandOverBillDto();
		//获取到达部门的code集合
		arriveDeptList = queryWaybillForHandOverBillDto.getArriveDeptList();
		//获取交接类型
		String handOverType = queryWaybillForHandOverBillDto.getHandOverType();
		//营业部交接
		if("SALES_DEPARTMENT_HANDOVER".equals(handOverType)){
			
			 //获取当前部门的code
			 String superOrgCode = this.querySuperiorOrgCode();
			 //查询出发部门获取是否是营业部、一级网点、二级网点
			 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
			 //出发部门  营业部："YYB"  , 一级网点 "YJB" , 二级网点 "EJB"
			 //默认为营业部
			 String departWork = "YYB";
			 //营业部数据非空
			 if(departDept != null){
				//判断是否是一级网点
				 String departModel = departDept.getIsLeagueSaleDept();
				 //判断是否是二级网点
				 String departNetwork = departDept.getIsTwoLevelNetwork();
				 //一级网点
				 if("Y".equals(departModel) && "N".equals(departNetwork)){
					 departWork = "YJB";
				 }
				 //二级网点
				 if("Y".equals(departNetwork)){
					 departWork = "EJB";
				 }
			 }else{
				 LOGGER.error("当前部门营业部数据为空！");
			 }
			 //获取到达部门的code
			 String arriveDeptCode = arriveDeptList.get(0);
			 //查询到达部门获取是否是营业部、一级网点、二级网点
			 SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(arriveDeptCode);
			 //到达部门   营业部："YYB"  , 一级网点 "YJB" , 二级网点 "EJB"
			 //默认为营业部
			 String arriveWork = "YYB";
			 //营业部数据非空
			 if(arriveDept != null){
				//判断是否是一级网点
				 String arriveModel = arriveDept.getIsLeagueSaleDept();
				//判断是否是二级网点
				 String arriveNetwork = arriveDept.getIsTwoLevelNetwork();
				 //一级网点
				 if("Y".equals(arriveModel) && "N".equals(arriveNetwork)){
					 arriveWork = "YJB";
				 }
				 //二级网点
				 if("Y".equals(arriveNetwork)){
					 arriveWork = "EJB";
				 }
			 }else{
				 throw new TfrBusinessException("当前到达部门不是营业部！");
			 }	 
			 
			 //二级网点到一级网点
			 if("EJB".equals(departWork) && "YJB".equals(arriveWork)){
				 //根据出发部门查询到对应的外场
				 List<String> centerCodeList = lineService.queryTransferCodeListBySourceCode(superOrgCode);
				 //替换
				 if(centerCodeList != null && centerCodeList.size()>0){
					 arriveDeptList.set(0, centerCodeList.get(0));
					 }
			 }
			 
			 //一级网点到营业部
			 if("YJB".equals(departWork) && "YYB".equals(arriveWork)){
				 //根据出发部门查询到对应的外场
				 List<String> centerCodeList = lineService.queryTransferCodeListBySourceCode(superOrgCode);
				 //添加
				 if(centerCodeList != null && centerCodeList.size()>0){
					 arriveDeptList.add(centerCodeList.get(0));
				 }
			 }
		//短配交接单	
		}else if("SHORT_DISTANCE_HANDOVER".equals(handOverType)){
			 //获取出发部门的code
			 String superOrgCode = this.querySuperiorOrgCode();
			 //查询出发部门获取是否是营业部
			 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
			 //出发部门默认是外场
			 String departWork = "YC";
			 //判断不为空
			 if(departDept != null){
				 //判断是否是一级网点
				 String departModel = departDept.getIsLeagueSaleDept();
				 //判断是否是二级网点
				 String departNetwork = departDept.getIsTwoLevelNetwork();
				//出发部门是营业部
				 if("N".equals(departModel) && "N".equals(departNetwork)){
					 departWork = "YYB";
				 }
				 //出发部门一级网点
				 if("Y".equals(departModel) && "N".equals(departNetwork)){
					 departWork = "YJB";
				 }
			 }
			 //获取到达部门的code
			 String arriveDeptCode = arriveDeptList.get(0);
			 //查询到达部门是否是营业部
			 SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(arriveDeptCode);
			 //到达部门默认是 外场           外场:"YC" ,  营业部："YYB"  , 一级网点 "YJB"
			 String arriveWork = "YC";
			 //判断不为空
			 if(arriveDept != null){
				 //判断是否是一级网点
				 String arriveModel = arriveDept.getIsLeagueSaleDept();
				 //判断是否是二级网点
				 String arriveNetwork = arriveDept.getIsTwoLevelNetwork();
				 //到达部门是营业部
				 if("N".equals(arriveModel) && "N".equals(arriveNetwork)){
					 arriveWork = "YYB";
				 }
				 //	到达部门一级网点
				 if("Y".equals(arriveModel) && "N".equals(arriveNetwork)){
					 arriveWork = "YJB";
				 }
			 }
			 //外场到营业部 
			 if("YC".equals(departWork) && "YYB".equals(arriveWork)){
				 //根据营业部查询到映射表的数据 
				 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
				 //结果集不为空才添加到映射的到达部门
				 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
					 //映射的到达部门结果集
					 List<String> arrivelist = new ArrayList<String>();
					 for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
							 //获取一级网点的code
						     String fthNetworkCode = entity.getFthNetworkCode();
							 if(fthNetworkCode != null){
								 arrivelist.add(fthNetworkCode);
							 }
							 //获取二级网点的code
							 String secNetworkCode = entity.getSecNetworkCode();
							 if(secNetworkCode != null){
								 arrivelist.add(secNetworkCode);
							 }												 
						}
					  //去掉重复的数据再加入集合中
					 arriveDeptList.addAll(new HashSet(arrivelist));
				 }
			 }
			 //外场到一级网点
			 if("YC".equals(departWork) && "YJB".equals(arriveWork)){
				 //根据一级网点查询到映射表的数据 
				 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
				 //判断结果集不为空才添加到映射的到达部门
				 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
					 for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
							//获取二级网点的code
							 String secNetworkCode = entity.getSecNetworkCode();
							 if(secNetworkCode != null){
								 	//添加关联
								 	arriveDeptList.add(secNetworkCode);
							 }
						}
				 }
			 }
			 //一级网点到外场
			 if("YJB".equals(departWork) && "YC".equals(arriveWork)){
				 //根据一级网点查询到映射表的数据 
				 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(superOrgCode);
				 //判断结果集不为空才添加到映射的到达部门 
				 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
					 //获取直营网点的code
					 String deptCode = deptTransferMappinglist.get(0).getDeptCode();
					 //替换下一部门code
					 if(deptCode != null){
						 arriveDeptList.add(deptCode);
					 }
				 }
				 
			 }
		}
		return arriveDeptList;
	}
	
	/**
	 * 传入运单号、库存部门，获取流水号list，用于查询交接运单界面
	 * @author 332219-foss-xuehaoyang
	 * @param querySerialNoListForWaybillConditionDto
	 * @return List<SerialNoStockEntity>
	 * @exception 无
	 * @date 2016-10-2 上午9:30:04
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#querySerialNoStockList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SerialNoStockEntity> querySaleSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		//获取当前部门编码
		String currentDeptCode = this.querySuperiorOrgCode();
		//设置当前部门code
		querySerialNoListForWaybillConditionDto.setCurrentDeptCode(currentDeptCode);
		//如果不是通过快速添加功能获取流水号
		if(!StringUtils.equals(querySerialNoListForWaybillConditionDto.getIsQuickAdd(), FossConstants.YES)){
			List<String> nextDeptCodeList = new ArrayList<String>();
			nextDeptCodeList.add(querySerialNoListForWaybillConditionDto.getNextDeptCode());
			//下一到达部门list
			nextDeptCodeList.add(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			nextDeptCodeList.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			querySerialNoListForWaybillConditionDto.setNextDeptCodeList(nextDeptCodeList);
		}
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		querySerialNoListForWaybillConditionDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		//运单是否合车
		String isJoinCar = querySerialNoListForWaybillConditionDto.getIsJoinCar();
		if(StringUtils.equals(isJoinCar, FossConstants.YES)){
			return handOverBillDao.querySaleJoinCarSerialNoStockList(querySerialNoListForWaybillConditionDto);
		}else{
			//返回查询结果
			return handOverBillDao.querysaleSerialNoStockList(querySerialNoListForWaybillConditionDto);
		} 
	}
	
	/**营业部交接
	 * 是否恢复虚拟库存信息
	 * @author 360903  linhua.yan 
	 * @date 2016年10月19日 15:08:44
	 */
	@Transactional
	private boolean isAddtStockSale(InOutStockEntity inOutStockEntity,HandOverBillEntity handOverBillEntity) {
		WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
		//交接单为营业部交接单,且交接部门不为始发营业部部入虚拟库存
		if(StringUtils.equals(handOverBillEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)
				&& !StringUtils.equals(wayBillEntity.getCreateOrgCode(),handOverBillEntity.getDepartDeptCode())){
			return true;
		}
	 	SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(handOverBillEntity.getDepartDeptCode());
		//交接单为短配交接单，交接部门为营业部，存在合伙人映射，且不为始发营业部
		if(StringUtils.equals(handOverBillEntity.getHandOverType(),LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE)
				&& departDept != null){
			List<DeptTransferMappingEntity> currentDeptTrans = deptTransferMappingService.queryDeptTransferMappingListByCode(handOverBillEntity.getDepartDeptCode());
		 	if(CollectionUtils.isNotEmpty(currentDeptTrans) && !StringUtils.equals(wayBillEntity.getCreateOrgCode(),handOverBillEntity.getDepartDeptCode())){
		 		return true;
		 	}
		}
		return false;
	}
	
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-360903
	 * Date:2016年11月7日 11:04:30
	 */ 
	@Override
	public int queryHandOverBillByLast(String waybillNo,String orgCode) {	
		if (StringUtils.isEmpty(waybillNo) || StringUtils.isEmpty(orgCode)){
			throw new TfrBusinessException("传入的参数：运单号和最终部门不能为空！");
		}
		//单票入库，无交接，看库存
		boolean flag = stockService.querystockOrgListByWaybillNo(waybillNo, orgCode);	
		if(flag){
			return FossConstants.SUCCESS;
		}else{
			//正常流程，看交接
			HandOverBillEntity handOverBillEntity =  handOverBillDao.queryHandOverBillByLast(waybillNo);
			if(null != handOverBillEntity){
				if(StringUtils.equals(orgCode,handOverBillEntity.getArriveDeptCode())){
					return FossConstants.SUCCESS;
				}
			}	
		}
		return FossConstants.FAILURE;
	}
	
	/** 
	 * @Title: 营业部交接修改新增流水出库
	 * @return    
	 * @author: tfr-360903
	 * Date2016年12月21日 16:23:45
	 */ 
	public boolean isNeedOutStockSale(InOutStockEntity inOutStockEntity){
		List<InOutStockEntity> list  = new ArrayList<InOutStockEntity>();
		list.add(inOutStockEntity);
		boolean flag = true;
		StockSaleEntity entity =  stockService.queryUniqueStockSale(inOutStockEntity);
        if(entity != null){
        	list = stockService.queryStockInfoSale(list);
        	flag= false;
        }
		return flag;
	}
	
	/** 
	 * @Title: queryHandOverBillByWaybillNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-332219
	 * Date:2016年12月27日
	 */ 
	@Override
	public HandOverBillEntity queryHandOverBillByWaybillNo(String waybillNo) {
		return  handOverBillDao.queryHandOverBillByLast(waybillNo);
	}
}