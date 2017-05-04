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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/service/impl/StockcheckingService.java
 *  
 *  FILE NAME          :StockcheckingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 *ISSUE-3252
 *1、新增一个配置参数，按照分钟为单位,默认提前120分钟(X)。
 *2、新增清仓任务时，按照当前清仓任务部门编号，查询此配置参数，
 *获取库存快照时，取当前库存快照对应的in_stock_time - X 分钟 的快照。
 *3、在清仓任务差异报告生成时，对于多货及少货的补充判断的时间段，也需要按照配置参数，
 *将查询的起始时间从“新增清仓任务的实际”提前X分钟
 *
 *ISSUE-3298
 *1、手工清仓任务，查询库区时，直接查询出非“清仓中”状态的货区。
 *之前SUC中提及的：“查询当天00:00:00到当前时间该货区的所有清仓任务，
 *如果任务个数为零则该货区状态为“未清”， *否则取建立时间距离当前时间最近的任务的状态即为该货区的状态包括清仓中、清仓完毕。”
 *此功能取消，只保证，不能清未处理完毕的清仓任务所属货区即可。
 *2、此ISSUE改动只针对驻地派送货区清仓时，做改动。非驻地派送货区清仓时，保持当前所有的清仓操作功能不变
 *3、当清仓时驻地派送货区时
 *3.1、可按件区选择(手工清仓和PDA清仓)，页面新增2个件数的输入框，
 *比如可以输入 1 到 3 表示下拉1到3件区的货物，如果没有输入表示下拉此派送货区的所有货物列表
 *3.2、PDA做派送货区清仓扫描时，多增加一个字段标识库位编码，
 *FOSS将更新此货件的库位信息(宋杰需提供接口，PDA组需返回库位信息)[3.2PDA按件未实现]
 *3.3、手工新建驻地派送货区，只能在外场做。在单独选出驻地派送货区时，页面显示件数的输入框，
 *如果没有选择驻地派送货区，隐藏此输入框。
 */
package com.deppon.foss.module.transfer.stockchecking.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskExportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StockcheckingVO;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 处理清仓任务相关的所有业务
 * @author foss-wuyingjie
 * @date 2012-10-16 下午4:55:41
 */
@Transactional(readOnly = true)
public class StockcheckingService implements IStockcheckingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockcheckingService.class);
	
	private IStTaskDao stTaskDao;
	private IStTaskListDao stTaskListDao;
	private IStResultListDao stResultListDao;
	private IStOperatorDao stOperatorDao;
	private IStResultListPdaDao stResultListPdaDao;
	
	private ITfrCommonService tfrCommonService;
	@Resource
	private IProductService productService4Dubbo;
	private IProductService productService;
	private IGoodsAreaService goodsAreaService;
	private IOutfieldService outfieldService;
	private IWaybillManagerService waybillManagerService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;

	private IConfigurationParamsService configurationParamsService;
	
	private IEmployeeService employeeService;

	private IDataDictionaryValueService dataDictionaryValueService;
	//清仓提货方式-自提
	private static final String RECEIVE_METHOD_PICKUP = "PICKUP";
	//清仓提货方式-派送
	private static final String RECEIVE_METHOD_DELIVER = "DELIVER";
	
	private ISendDistrictMapService sendDistrictMapService;
	
	public void setSendDistrictMapService(
			ISendDistrictMapService sendDistrictMapService) {
		this.sendDistrictMapService = sendDistrictMapService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public void setStTaskDao(IStTaskDao stTaskDao) {
		this.stTaskDao = stTaskDao;
	}
	
	public void setStTaskListDao(IStTaskListDao stTaskListDao) {
		this.stTaskListDao = stTaskListDao;
	}

	public void setStOperatorDao(IStOperatorDao stOperatorDao) {
		this.stOperatorDao = stOperatorDao;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	public void setStResultListDao(IStResultListDao stResultListDao) {
		this.stResultListDao = stResultListDao;
	}
	
	/**
	 * @param stResultListPdaDao the stResultListPdaDao to set
	 */
	public void setStResultListPdaDao(IStResultListPdaDao stResultListPdaDao) {
		this.stResultListPdaDao = stResultListPdaDao;
	}
//
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
	/** 
	 * 根据部门编码查询组织架构
	 * 
	 * */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 获取快递员车辆绑定的开单营业部 
	 */
    private IExpressVehiclesService expressVehiclesService;
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	/**
	 * 
	 * 和判断登录的是不是快递员
	 */
	private ICommonExpressEmployeeService commonExpressEmployeeService;
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:10
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryStTaskDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto, int, int)
	 */
	@Override
	public List<StTaskDto> queryStTaskDtoList(StTaskDto stTaskDto, int start, int limit) {
		
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
			//根据快递员的工号查找到快递员车辆所在的开大营业部
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//根据营业部编号获取组织架构
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity = this.getBigOrg(expressOrgEntity);
			System.out.println("查询清仓任务------快递员");
		}else{
			 orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
		}
		
		stTaskDto.setCurrentDeptCode(orgEntity.getCode());
		stTaskDto.setTransferCenter(orgEntity.getTransferCenter());
		
		List<StTaskDto> list = new ArrayList<StTaskDto>();
		
//		若此网点为外场，需带入库区关联条件查询
		if(StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
			list = stTaskDao.queryTransferCenterStTaskDtoList(stTaskDto, start, limit);
		}else if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//				获取空运总调对应的外场编号
			String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
			
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			String goodsAreaCode = null;
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
			}else{
				//根据部门查询货区失败
				throw new TfrBusinessException("空运总调用户登陆后获取外场对应的库区失败，接口：goodsAreaService.queryGoodsAreaListByType","");
			}
			
			stTaskDto.setCurrentDeptCode(transferCode);
			stTaskDto.setTransferCenter(transferCode);
			stTaskDto.setGoodsArea(goodsAreaCode);
			
			list = stTaskDao.queryTransferCenterStTaskDtoList(stTaskDto, start, limit);
		}else{
			list = stTaskDao.queryStTaskDtoList(stTaskDto, start, limit);
		}
		
		for(StTaskDto dto: list){
//			获取清仓人信息
			List<StOperatorEntity> operatorsList = queryOperatorsByStTaskId(dto.getId());
			StringBuffer operators = new StringBuffer();
			for(StOperatorEntity o: operatorsList){
				operators.append(o.getEmpName()).append(",");
			}
			String opers = operators.toString();
			if(StringUtils.endsWith(opers, ",")){
				opers = StringUtils.left(opers, opers.length() - 1);
			}
			
			dto.setEmpName(opers);
		}
		
		return list;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:15
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryStTaskDtoListCount(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto)
	 */
	@Override
	public Long queryStTaskDtoListCount(StTaskDto stTaskDto) {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
			//根据快递员的工号查找到快递员车辆所在的开大营业部
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//根据营业部编号获取组织架构
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity = this.getBigOrg(expressOrgEntity);
			System.out.println("查询清仓任务------快递员");
		}else{
			 orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
		}
		stTaskDto.setCurrentDeptCode(orgEntity.getCode());
		stTaskDto.setTransferCenter(orgEntity.getTransferCenter());
//		若此网点为外场，需带入库区关联条件查询
		if(StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
			return stTaskDao.queryTransferCenterStTaskDtoListCount(stTaskDto);
		}else if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//			获取空运总调对应的外场编号
			String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
			
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			String goodsAreaCode = null;
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
			}else{
				//根据部门查询货区失败
				throw new TfrBusinessException("空运总调用户登陆后获取外场对应的库区失败，接口：goodsAreaService.queryGoodsAreaListByType","");
			}
			
			stTaskDto.setCurrentDeptCode(transferCode);
			stTaskDto.setTransferCenter(transferCode);
			stTaskDto.setGoodsArea(goodsAreaCode);
			
			return stTaskDao.queryTransferCenterStTaskDtoListCount(stTaskDto);
		}else{
			return stTaskDao.queryStTaskDtoListCount(stTaskDto);
		}
		
		
	}
	/**
	 * @author niuly
	 * @date 2013-07-31 14:08:53
	 * @function 新建清仓任务时，获取任务部门的时间配置参数，用于下拉库存
	 * @param deptCode
	 * @return int
	 */
	private int getBeforeTime(String deptCode) {
		//默认为120分钟
		int createTaskTime = ConstantsNumberSonar.SONAR_NUMBER_120;
		try{
			ConfigurationParamsEntity defaultBizHourSlice1 = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_TASK_BEFORE_CREATE_TIME, deptCode);
			createTaskTime = Integer.valueOf(defaultBizHourSlice1.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取'清仓任务新建调整时间差'配置参数失败", ExceptionUtils.getFullStackTrace(e));
		}
		return createTaskTime;
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:21
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto, java.lang.String)
	 */
	@Override
	public List<GoodsStockDto> queryGoodsStockDtoList(GoodsStockDto goodsStockDto) {
		//按某部门分组查询库区统计信息
		List<GoodsStockDto> goodsStockDtoList = new ArrayList<GoodsStockDto>();
		List<GoodsStockDto> newGoodsStockDtoList = new ArrayList<GoodsStockDto>();
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
			//根据快递员的工号查找到快递员车辆所在的开大营业部
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
							//根据营业部编号获取组织架构
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity = this.getBigOrg(expressOrgEntity);
			System.out.println("快递员1<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}else{
			 orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
		}
		
		
		//如果为新增页面，则读取登陆人所在部门编号
		if(StringUtils.isBlank(goodsStockDto.getStTaskId())){
			goodsStockDto.setDeptNo(orgEntity.getCode());
		}else{
			//编辑页面，只通过ID查询对应的库区信息
			StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(goodsStockDto.getStTaskId());
			goodsStockDto.setDeptNo(stTaskEntity.getDeptcode());
			goodsStockDto.setGoodsArea(stTaskEntity.getGoodsareacode());
		}
		
//		判断是否为外场，为外场的需要以部门、库区、单号分组，否则已部门、单号分组
		if(StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
			//获取时间
			int beforeTime = this.getBeforeTime(goodsStockDto.getDeptNo());
			//新增状态
			if(StringUtils.isBlank(goodsStockDto.getStTaskId())) {
				//若货区为驻地派送货区且有件数时
				if(isBasStation(orgEntity.getCode(), goodsStockDto.getGoodsArea())) {
					if(StringUtils.isNotEmpty(goodsStockDto.getReceiveMethod())) {
						//提货方式
						goodsStockDto.setReceiveMethodList(this.getReceiveMethodList(goodsStockDto.getReceiveMethod()));
					}
					if(StringUtils.isNotEmpty(goodsStockDto.getDistrictCode())) {
						//行政区域
						goodsStockDto.setDistrictCodeList(this.getDistrictCodeList(goodsStockDto.getDistrictCode()));
					}
				}
				if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
					goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
					System.out.println("快递员2<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				}else{
					if(StringUtil.isBlank(goodsStockDto.getGoodsAreaType())||goodsStockDto.getGoodsAreaType().equals("ALL")){
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoList(goodsStockDto,beforeTime);
					}else if(goodsStockDto.getGoodsAreaType().equals("Y")){
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
					}else if(goodsStockDto.getGoodsAreaType().equals("N")){
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListNoExpress(goodsStockDto,beforeTime);
					}
				}
					
			} else {//编辑状态
				StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(goodsStockDto.getStTaskId());
				goodsStockDto.setStartQty(stTaskEntity.getStartQty());
				goodsStockDto.setEndQty(stTaskEntity.getEndQty());
				if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
					goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
					System.out.println("快递员3<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				}else{
					if(StringUtil.isBlank(goodsStockDto.getGoodsAreaType())||goodsStockDto.getGoodsAreaType().equals("ALL")){
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoList(goodsStockDto,beforeTime);
					}else if(goodsStockDto.getGoodsAreaType().equals("Y")){//清快递货物
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
					}else if(goodsStockDto.getGoodsAreaType().equals("N")){//清零担货物
						goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListNoExpress(goodsStockDto,beforeTime);
					}
				}	
			}
//		判断是否为空运总调
		}else if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//			获取空运总调对应的外场编号
			String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
			
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			String goodsAreaCode = null;
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
			}else{
				//根据部门查询货区失败
				throw new TfrBusinessException("空运总调用户登陆后获取外场对应的库区失败，接口：goodsAreaService.queryGoodsAreaListByType","");
			}
			
			goodsStockDto.setDeptNo(transferCode);
			goodsStockDto.setGoodsArea(goodsAreaCode);
			//获取时间
			int beforeTime = this.getBeforeTime(goodsStockDto.getDeptNo());
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
				goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
				System.out.println("快递员4<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}else{
				if(StringUtil.isBlank(goodsStockDto.getGoodsAreaType())||goodsStockDto.getGoodsAreaType().equals("ALL")){
					goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoList(goodsStockDto,beforeTime);
				}else if(goodsStockDto.getGoodsAreaType().equals("Y")){//清快递货物
					goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListExpress(goodsStockDto,beforeTime);
				}else if(goodsStockDto.getGoodsAreaType().equals("N")){//清零担货物
					goodsStockDtoList = stTaskDao.queryTransferCenterGoodsStockDtoListNoExpress(goodsStockDto,beforeTime);
				}
			}
		}else{
			//获取时间
			int beforeTime = this.getBeforeTime(goodsStockDto.getDeptNo());
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
				goodsStockDtoList = stTaskDao.queryExpressGoodsStockDtoList(goodsStockDto,beforeTime);
				System.out.println("快递员5<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}else{
				if(StringUtil.isBlank(goodsStockDto.getGoodsAreaType())||goodsStockDto.getGoodsAreaType().equals("ALL")){
					goodsStockDtoList = stTaskDao.queryGoodsStockDtoList(goodsStockDto,beforeTime);
				}else if(goodsStockDto.getGoodsAreaType().equals("Y")){//清快递货物
					goodsStockDtoList = stTaskDao.queryExpressGoodsStockDtoList(goodsStockDto,beforeTime);
				}else if(goodsStockDto.getGoodsAreaType().equals("N")){//清零担货物
					goodsStockDtoList = stTaskDao.queryNoExpressGoodsStockDtoList(goodsStockDto,beforeTime);
				}
			}
			
			if(CollectionUtils.isNotEmpty(goodsStockDtoList)){
				for(GoodsStockDto gs: goodsStockDtoList){
					gs.setDeptNo(orgEntity.getCode());
				}
			}else{
				GoodsStockDto stockDto = new GoodsStockDto();
				stockDto.setDeptNo(orgEntity.getCode());
				stockDto.setGoodVolumeTotal(new Double(0));
				stockDto.setGoodWeightTotal(new Double(0));
				stockDto.setStockGoodsQty(0);
				stockDto.setWaybillNum(0);
				goodsStockDtoList.add(stockDto);
			}
		}
		//Date currentDay = DateUtils.ceiling(DateUtils.addDays(Calendar.getInstance().getTime(), -1), Calendar.DATE);
		for(GoodsStockDto dto: goodsStockDtoList){
			String deptNo = "";
			// 从缓存中获取库区名称
			//dto.setGoodsAreaName(goodsAreaService.queryNameByCode(orgEntity.getCode(), dto.getGoodsAreaCode()));
			if(StringUtils.isBlank(goodsStockDto.getStTaskId())){
				deptNo = orgEntity.getCode();
			}else{
				StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(goodsStockDto.getStTaskId());
				deptNo = stTaskEntity.getDeptcode();
			}
			
			dto.setDeptNo(deptNo);
//			通过库区获取当前此库区的状态，货区清仓状态定义：查询当天00:00:00到当前时间该货区的所有清仓任务，如果任务个数为零则该货区状态为“未清仓”，否则取建立时间距离当前时间最近的任务的状态即为该货区的状态包括清仓中、清仓完毕。
			//List<StTaskEntity> stTaskList = stTaskDao.queryStTaskStatus(currentDay, dto.getGoodsAreaCode(), deptNo, TransferConstants.STOCK_CHECKING_CANCEL);
			//查询当前部门当前货区的最后的状态为清仓完毕或取消的任务
			List<StTaskEntity> stTaskList = stTaskDao.queryStTaskStatus(dto.getGoodsAreaCode(), deptNo);
			
			if(stTaskList.size() == 0){
				dto.setTaskStatus(TransferConstants.STOCK_CHECKING_HAVENOT);
			}else{
				dto.setTaskStatus(stTaskList.get(0).getTaskStatus());
			}
		}
		
		//匹配查询条件的清仓状态
		if(StringUtils.equals(TransferDictionaryConstants.DEFAULT_COMBO_VALUE, goodsStockDto.getTaskStatus()) || StringUtils.isEmpty(goodsStockDto.getTaskStatus())){
			newGoodsStockDtoList = goodsStockDtoList;
		}else if(StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, goodsStockDto.getTaskStatus())){
			for(GoodsStockDto dto: goodsStockDtoList){
				if(StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, dto.getTaskStatus())){
					newGoodsStockDtoList.add(dto);
				}
			}
		}else if(StringUtils.equals(TransferConstants.STOCK_CHECKING_DONE, goodsStockDto.getTaskStatus())){
			for(GoodsStockDto dto: goodsStockDtoList){
				if(StringUtils.equals(TransferConstants.STOCK_CHECKING_DONE, dto.getTaskStatus())){
					newGoodsStockDtoList.add(dto);
				}
			}
		}else if(TransferConstants.STOCK_CHECKING_CANCEL.equals(goodsStockDto.getTaskStatus())){
			for(GoodsStockDto dto: goodsStockDtoList){
				if(StringUtils.equals(TransferConstants.STOCK_CHECKING_CANCEL, dto.getTaskStatus())){
					newGoodsStockDtoList.add(dto);
				}
			}
		}else{
			for(GoodsStockDto dto: goodsStockDtoList){
				if(StringUtils.equals(TransferConstants.STOCK_CHECKING_HAVENOT, dto.getTaskStatus())){
					newGoodsStockDtoList.add(dto);
				}
			}
		}
		
		List<GoodsStockDto> finalStockDtoList = new ArrayList<GoodsStockDto>();
		
		for(GoodsStockDto dto: newGoodsStockDtoList){
			if(StringUtils.equals(TransferConstants.STOCK_CHECKING_HAVENOT, dto.getTaskStatus())
					|| StringUtils.equals(TransferConstants.STOCK_CHECKING_CANCEL, dto.getTaskStatus())
					|| StringUtils.equals(TransferConstants.STOCK_CHECKING_DONE, dto.getTaskStatus())){
				finalStockDtoList.add(dto);
			} else {
				//新增状态
				if(StringUtils.isBlank(goodsStockDto.getStTaskId())) {
					
					if(isBasStation(orgEntity.getCode(), goodsStockDto.getGoodsArea())) {
						
						StTaskEntity task = new StTaskEntity();
						task.setDeptcode(goodsStockDto.getDeptNo());
						task.setGoodsareacode(goodsStockDto.getGoodsArea());
						task.setReceiveMethod(goodsStockDto.getReceiveMethod());
						task.setDistrictCode(goodsStockDto.getDistrictCode());
						task.setStartQty(goodsStockDto.getStartQty());
						task.setEndQty(goodsStockDto.getEndQty());
						
						this.checkStockChecking(task);
						if(!finalStockDtoList.contains(dto)) {
							finalStockDtoList.add(dto);
						}
					}
					
				} else {
					//编辑状态时
					finalStockDtoList.add(dto);
				}
			}
		}
		
		return finalStockDtoList;
	}

	/**
	 * @author nly
	 * @date 2014年11月3日 上午9:43:58
	 * @function 获取运单提货方式
	 * @param goodsStockDto
	 */
	private List<String> getReceiveMethodList(String receiveMethod) {
		if(StringUtils.isEmpty(receiveMethod)) {
			return null;
		}
		
		List<String> receiveMethodList = new ArrayList<String>();
		if(RECEIVE_METHOD_DELIVER.equals(receiveMethod)) {
			receiveMethodList.add(WaybillConstants.DELIVER_NOUP); //汽运送货(不含上楼)
			receiveMethodList.add(WaybillConstants.DELIVER_FREE); //汽运免费派送
			receiveMethodList.add(WaybillConstants.DELIVER_STORAGE);//汽运送货进仓
			receiveMethodList.add(WaybillConstants.DELIVER_UP);	//汽运送货（上楼）
			receiveMethodList.add(WaybillConstants.DELIVER_FREE_AIR);//空运免费送货
			receiveMethodList.add(WaybillConstants.DELIVER_NOUP_AIR);//送货(不含上楼)
			receiveMethodList.add(WaybillConstants.DELIVER_UP_AIR);//空运送货上楼
			receiveMethodList.add(WaybillConstants.DELIVER_INGA_AIR);//空运送货进仓
		}
		if(RECEIVE_METHOD_PICKUP.equals(receiveMethod)) {
			receiveMethodList.add(WaybillConstants.SELF_PICKUP);	//汽运自提
			receiveMethodList.add(WaybillConstants.INNER_PICKUP);//汽运内部自提
			receiveMethodList.add(WaybillConstants.AIR_PICKUP_FREE);//空运免费自提
			receiveMethodList.add(WaybillConstants.AIR_SELF_PICKUP);//空运自提(不含机场提货费)
			receiveMethodList.add(WaybillConstants.AIRPORT_PICKUP);//空运机场自提
		}
		return receiveMethodList;
	}
	/**
	 * @author nly
	 * @date 2014年11月3日 上午9:44:04
	 * @function 获取分区对应的行政区域
	 * @param goodsStockDto
	 */
	private List<String> getDistrictCodeList(String districtCode) {
		if(StringUtils.isEmpty(districtCode)) {
			return null;
		}
		List<String> districtCodeList = new ArrayList<String>();
		List<SendDistrictMapEntity> entityList = new ArrayList<SendDistrictMapEntity>();
		try{
			entityList = this.getDistrictEntityList(districtCode);
			
			for(SendDistrictMapEntity e : entityList) {
				if(StringUtils.isNotEmpty(e.getDistrictCode())) {
					districtCodeList.add(e.getDistrictCode());
				}
			}	
		}catch(Exception e) {
			throw new TfrBusinessException("查询行政区域失败！","");
		}
		
		if(null == districtCodeList || districtCodeList.size() == 0) {
			throw new TfrBusinessException("查询行政区域失败！","");
		}
		return districtCodeList;
	}

	/**
	 * @author nly
	 * @date 2014年11月11日 上午8:30:46
	 * @function 获取分区对应的行政区域信息
	 * @param entity
	 * @return
	 */
	private List<SendDistrictMapEntity> getDistrictEntityList(String districtCode) {
		List<SendDistrictMapEntity> entityList = new ArrayList<SendDistrictMapEntity>();
		try{
			SendDistrictMapEntity entity = new SendDistrictMapEntity();
			entity.setZoneCode(districtCode);
			entityList = sendDistrictMapService.querySendDistrictMapListbyEntity(entity);
			
			if(CollectionUtils.isEmpty(entityList)) {
				throw new TfrBusinessException("查询行政区域失败！","");
			}
		}catch(Exception e) {
			throw new TfrBusinessException("查询行政区域失败！","");
		}
		return entityList;
	}
	/**
	 * @author niuly
	 * @param transferCode
	 * @param areaCode
	 * @function 判断外场的货区是否为驻地派送货区
	 * @return
	 */
	@Override
	public boolean isBasStation(String transferCode,String areaCode) {
		boolean isBasStation = false;
		GoodsAreaEntity areaEntity = goodsAreaService.queryGoodsAreaByCode(transferCode,areaCode);
		if(areaEntity != null && StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION, areaEntity.getGoodsAreaType())) {
			isBasStation = true;
		}
		return isBasStation;
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:31
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#addStTask(java.lang.String, java.lang.String, java.lang.String, java.util.List, java.util.List)
	 */
	@Transactional(readOnly = false)
	@Override
	public void addStTask(List<String> selectedGoodsAreasList, List<StOperatorEntity> stOperatorEntity, String receiveMethod,String districtCode,String districtName,Integer startQty, Integer endQty,String stType) {
		//创建所有清仓任务
		for(String goodsAreaCode: selectedGoodsAreasList){
			CurrentInfo user = FossUserContext.getCurrentInfo();
			OrgAdministrativeInfoEntity orgEntity = null;
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
				//根据快递员的工号查找到快递员车辆所在的开大营业部
				String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
								//根据营业部编号获取组织架构
				OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
				orgEntity = this.getBigOrg(expressOrgEntity);
			}else{
				 orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
			}
			String currentDeptCode = "";
			String currentGoodsAreaCode = "";
//			对于空运总调型的，需找到对应的外场所属的库区
			if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//				获取空运总调对应的外场编号
				currentDeptCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
				
				List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(currentDeptCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
				currentGoodsAreaCode = null;
				if(CollectionUtils.isNotEmpty(goodsAreaList)){
					currentGoodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
				}else{
					//根据部门查询货区失败
					throw new TfrBusinessException("空运总调用户登陆后获取外场对应的库区失败，接口：stockcheckingService.addStTask","");
				}
			}else{
				currentDeptCode = orgEntity.getCode();
				currentGoodsAreaCode = goodsAreaCode;
			}
			//外场驻地派送货区
			boolean isStation = StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter()) 
					&& this.isBasStation(currentDeptCode, currentGoodsAreaCode)
					/*&& startQty != null && endQty != null
					&& startQty > 0 && endQty > 0*/;
			
			StTaskEntity task = new StTaskEntity();
			
			task.setTaskNo(tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.QC, user.getCurrentDeptCode()));
			task.setGoodsareacode(currentGoodsAreaCode);
			task.setGoodsareaname(goodsAreaService.queryNameByCode(currentDeptCode, currentGoodsAreaCode));
			task.setTaskStatus(TransferConstants.STOCK_CHECKING_DOING);
			task.setDeptcode(currentDeptCode);
			task.setCreatetime(new Date());
			task.setIspda(TransferDictionaryConstants.NO);
			task.setCreatorName(user.getEmpName());
			task.setCreatorCode(user.getEmpCode());
			
			//外场的驻地派送货区
			if(isStation) {
				task.setStartQty(startQty);
				task.setEndQty(endQty);
				task.setReceiveMethod(receiveMethod);
				task.setDistrictCode(districtCode);
				task.setDistrictName(districtName);
				task.setReceiveMethodList(this.getReceiveMethodList(receiveMethod));
				task.setDistrictCodeList(this.getDistrictCodeList(districtCode));
			}
			
			//在部门编号和货区上加业务锁
			MutexElement mutex = null;
			try {
				//锁定对象
				String lockStr = currentDeptCode + currentGoodsAreaCode;
				mutex = new MutexElement(lockStr, "新增清仓任务", MutexElementType.TFR_STOCKCHECKING_ADDNEW);
				// 锁定
				boolean flag = businessLockService.lock(mutex, 0);
				if (flag) {
					//校验清仓任务
					this.checkStockChecking(task);
					
					//新增清仓任务
					stTaskDao.addStTaskEntity(task);
					
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TfrBusinessException("该部门正在创建该货区的清仓任务，请稍候重试！");
				}
			} 
			catch (Exception e) {
				LOGGER.error(ExceptionUtils.getMessage(e));
				throw new TfrBusinessException(e.getMessage());
			}
			finally {
				// 解锁
				businessLockService.unlock(mutex);
			}
			
			//获取 提前时间
			int beforeTime = this.getBeforeTime(currentDeptCode);
			LOGGER.info("开始插入任务明细，任务号：" + task.getTaskNo());
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
				stTaskListDao.addStTaskListFromStockForExpress(task,beforeTime);
			}else{
				if(StringUtil.isBlank(stType)||StringUtil.equals(stType, "ALL")){
					stTaskListDao.addStTaskListFromStock(task,beforeTime);
				}else if(StringUtil.equals(stType, "Y")){
					stTaskListDao.addStTaskListFromStockForExpress(task,beforeTime);
				}else if(StringUtil.equals(stType, "N")){
					stTaskListDao.addStTaskListFromStockForNoExpress(task,beforeTime);
				}
			}
			
			LOGGER.info("结束插入任务明细，任务号：" + task.getTaskNo());	
			
			//绑定清仓人
			for(StOperatorEntity entity: stOperatorEntity){
				entity.setStTaskId(task.getId());
				stOperatorDao.addStOperatorEntity(entity);
			}
		}
	}

	/**
	 * @author nly
	 * @date 2014年11月5日 上午8:39:14
	 * @function 判断是否可以新建清仓任务
	 * @param taskList
	 */
	private void checkStockChecking(StTaskEntity task) {
		List<StTaskDto> taskList = this.queryTaskInProcess(task.getDeptcode(), task.getGoodsareacode());
		//sonar-352203
		if(taskList == null || taskList.size() <= 0) {
			return;
		}
			//在建清仓任务类型
			boolean isGoodsArea = false;
			//boolean isReceiveMethod = false;
			boolean isPickUp = false;
			boolean isDeliver = false;
			boolean isDistrictCode = false;
			boolean isQty = false;
			
			if(StringUtils.isNotEmpty(task.getGoodsareacode())) {
				isGoodsArea = true;
			}
			if(StringUtils.isNotEmpty(task.getReceiveMethod())) {
				//isReceiveMethod = true;
				if(RECEIVE_METHOD_PICKUP.equals(task.getReceiveMethod())) {
					isPickUp = true;
				} else if(RECEIVE_METHOD_DELIVER.equals(task.getReceiveMethod())){
					isDeliver = true;
				}
			}
			if(StringUtils.isNotEmpty(task.getDistrictCode())) {
				isDistrictCode = true;
			}
			if(null != task.getStartQty() && null != task.getEndQty()) {
				isQty = true;
			}
			
			for(StTaskDto dto : taskList) {
				//sonar-352203
				if(null == dto) {
					continue;
				}
					//相同货区
					//已存在的清仓任务类型
					boolean isHasGoodsArea = false;
					boolean isHasPickUp = false;
					boolean isHasDeliver = false;
					boolean isHasDistrictCode = false;
					boolean isHasQty = false;
					
					if(StringUtils.isNotEmpty(dto.getGoodsareacode())) {
						isHasGoodsArea = true;
					}
					
					if(StringUtils.isNotEmpty(dto.getReceiveMethod())) {
						if(RECEIVE_METHOD_PICKUP.equals(dto.getReceiveMethod())) {
							isHasPickUp = true;
						} else if(RECEIVE_METHOD_DELIVER.equals(dto.getReceiveMethod())){
							isHasDeliver = true;
						}
					}
					
					if(StringUtils.isNotEmpty(dto.getDistrictCode())) {
						isHasDistrictCode = true;
					}
					
					if(null != dto.getStartQty() && null != dto.getEndQty()) {
						isHasQty = true;
					}
					
					//判断在创建的任务类型是否与现有的存在交叉，若交叉给出提示
					//已存在的清仓类型：营业部清仓
					if(!isHasGoodsArea) {
						throw new TfrBusinessException("本部门已存在未结束的清仓任务，请先处理未结束的任务！");
					}
					//已存在的清仓类型：货区
					else if(isHasGoodsArea && !isHasPickUp && !isHasDeliver && !isHasDistrictCode && !isHasQty) {
							throw new TfrBusinessException("该货区已存在未结束的清仓任务，请先处理未结束的任务！");
					}
					//已存在的清仓类型：货区 + 提货方式(自提)
					else if(isHasGoodsArea && isHasPickUp && !isHasQty) {
						boolean isAlert = false;
						//boolean isSameReceiveMethod = this.compare(task.getReceiveMethod(), dto.getReceiveMethod());
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
								isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(自提)
						else if(isGoodsArea && isPickUp) {
							isAlert = true;
						} 
						
						if(isAlert) {
							throw new TfrBusinessException("该货区已存在未结束的自提清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(自提) + 件区
					else if(isHasGoodsArea && isHasPickUp  && isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
								isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(自提)
						else if(isGoodsArea && isPickUp  && !isQty) {
							isAlert = true;
						} 
						//在建清仓类型：货区 + 提货方式(自提) + 件区
						else if(isGoodsArea && isPickUp  && isQty) {
							boolean isCrossQty = this.compareQty(task.getStartQty(), task.getEndQty(),dto.getStartQty(),dto.getEndQty());
							if(isCrossQty) {
								isAlert = true;
							}
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的自提清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(派送) + 分区
					else if(isHasGoodsArea && isHasDeliver && isHasDistrictCode && !isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
								isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(派送) + 分区
						else if(isGoodsArea && isDeliver && isDistrictCode) {
							boolean isSameDistrictCode = this.compare(task.getDistrictCode(), dto.getDistrictCode());
							if(isSameDistrictCode) {
								isAlert = true;
							}
						} 
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getDistrictName() +"分区的派送清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(派送) + 分区 + 件区
					else if(isHasGoodsArea && isHasDeliver && isHasDistrictCode && isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
								isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(派送) + 分区
						else if(isGoodsArea && isDeliver && isDistrictCode && !isQty) {
							boolean isSameDistrictCode = this.compare(task.getDistrictCode(), dto.getDistrictCode());
							if(isSameDistrictCode) {
								isAlert = true;
							}
						} 
						//在建清仓类型：货区 + 提货方式(派送) + 分区 + 件区
						else if(isGoodsArea && isDeliver && isDistrictCode && isQty) {
							boolean isSameDistrictCode = this.compare(task.getDistrictCode(), dto.getDistrictCode());
							boolean isCrossQty = this.compareQty(task.getStartQty(), task.getEndQty(),dto.getStartQty(),dto.getEndQty());
							if(isSameDistrictCode && isCrossQty) {
								isAlert = true;
							}
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getDistrictName() +"分区的"+dto.getStartQty() + "-" + dto.getEndQty()+"件区的派送清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 件区     （非试点外场，以后会去掉此类清仓方式）
					else if(isHasGoodsArea && !isHasPickUp && !isHasDeliver && !isHasDistrictCode && isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
							isAlert = true;
						}
						//在建清仓类型：货区 + 件区
						else if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && isQty) {
							boolean isCrossQty = this.compareQty(task.getStartQty(), task.getEndQty(),dto.getStartQty(),dto.getEndQty());
							if(isCrossQty) {
								isAlert = true;
							}
						} 
						//在建清仓类型：货区 + 提货方式(自提) (试点外场存在货区+件区的历史清仓类型时)
						else if(isGoodsArea && isPickUp) {
							isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(派送) + 分区 (试点外场存在货区+件区的历史清仓类型时)
						else if(isGoodsArea && isDeliver && isDistrictCode) {
							isAlert = true;
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的清仓任务，请先处理未结束的任务！");
						}
					}	
					
//				}	
			}	
//		}
	}
	
	/**
	 * @author nly
	 * @date 2014年11月5日 下午12:59:49
	 * @function 对比清仓件数是否存在交叉
	 * @param startQty
	 * @param endQty
	 * @param startQty2
	 * @param endQty2
	 * @return
	 */
	private boolean compareQty(Integer startQty, Integer endQty, Integer hasStartQty, Integer hasEndQty) {
		if(null == startQty || null == endQty ) {
			return false;
		}
		if(null == hasStartQty || null == hasEndQty ) {
			return false;
		}
		if(startQty > hasEndQty || endQty < hasStartQty){
			return false;
		}
		return true;
	}

	/**
	 * @author nly
	 * @date 2014年11月5日 下午12:50:08
	 * @function 对比货区或提货方式或分区是否相同
	 * @param value
	 * @param hasValue
	 * @return
	 */
	private boolean compare(String value,String hasValue) {
		if(StringUtils.isNotEmpty(value) && value.equals(hasValue)) {
			return true;
		}
		return false;
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:38
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryGoodsAreaUsage()
	 */
	@Override
	public List<BaseDataDictDto> queryGoodsAreaUsage() {
		
		return tfrCommonService.loadDictDataCombox(DictionaryConstants.BSE_GOODSAREA_USAGE);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:42
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryOperatorsByStTaskId(java.lang.String)
	 */
	@Override
	public List<StOperatorEntity> queryOperatorsByStTaskId(String stTaskId) {
		
		return stOperatorDao.queryOperatorsByStTaskId(stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:47
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#updateStTask(java.lang.String, java.util.List)
	 */
	@Transactional(readOnly = false)
	@Override
	public void updateStTask(String stTaskId, List<StOperatorEntity> selectedOperatorList) {
		//删除所有相关清仓人
		stOperatorDao.deleteOperatorByStTaskId(stTaskId);
		//按照页面选取的清仓人重新与清仓任务绑定
		//绑定清仓人
		//List<StOperatorEntity> stOperatorList = new ArrayList<StOperatorEntity>();
		for(StOperatorEntity entity: selectedOperatorList){
			entity.setId(UUIDUtils.getUUID());
			entity.setStTaskId(stTaskId);
			//stOperatorList.add(entity);
			stOperatorDao.addStOperatorEntity(entity);
		}
		//stOperatorDao.addStOperatorEntityBatch(stOperatorList);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:53
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#cancelStTask(java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public void cancelStTask(String stTaskId) {
		StTaskEntity stTaskEntity = new StTaskEntity();
		stTaskEntity.setId(stTaskId);
		stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_CANCEL);
//		更新清仓任务状态
		stTaskDao.updateStTaskStatus(stTaskEntity);
//		删除对应的库存快照（T_OPT_ST_TASK_LIST)
//		stTaskListDao.deleteByStTaskId(stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:50:58
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryStTaskById(java.lang.String)
	 */
	@Override
	public StTaskDto queryStTaskById(String stTaskId){
		StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(stTaskId);
		StTaskDto stTaskDto = new StTaskDto();
		BeanUtils.copyProperties(stTaskEntity, stTaskDto);
		
		return stTaskDto;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:03
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryStTaskWaybillNoListByStTaskId(java.lang.String)
	 */
	@Override
	public List<StTaskWaybillNoListDto> queryStTaskWaybillNoListByStTaskId(String stTaskId) {
		//通过清仓任务ID获取清仓货物清单
		List<StTaskListEntity> stTaskList = stTaskListDao.queryStTaskListByStTaskId(stTaskId);
		/**
		 * 组成以下格式
		 * 单号1 件数
		 *  流水号1
		 *  流水号2
		 *  流水号3
		 * 单号2 件数
		 *  流水号1
		 *  流水号2
		 */
		
		List<StTaskWaybillNoListDto> stTaskWaybillNoListDtoList = new ArrayList<StTaskWaybillNoListDto>();
		
		String tempWaybillNo = "";
		int tempCount = 0;
		List<String> tempSerialNoList = null;			//临时流水号列表
		StTaskWaybillNoListDto tempWaybillNoDto = null; //临时运单对象
		
		for(StTaskListEntity stTaskListEntity: stTaskList){
//			单号未变化，只更新计数，并将此流水号加入对应的单号
			if(StringUtils.equals(tempWaybillNo, stTaskListEntity.getWaybillNo())){
				tempCount++;
				tempWaybillNoDto.setSerialNoNum(tempCount);
				tempSerialNoList.add(stTaskListEntity.getSerialNo());
//		          单号发生变化，需重置单号、计数器、并创建新的单号及流水号对象并赋值
			}else{	
				tempCount = 1;
				tempWaybillNo = stTaskListEntity.getWaybillNo();
				
//				此处创建新的对象，改变临时变量内存地址指向
				tempSerialNoList = new ArrayList<String>();
				tempWaybillNoDto = new StTaskWaybillNoListDto(tempWaybillNo);
				
				stTaskWaybillNoListDtoList.add(tempWaybillNoDto);
				
				tempWaybillNoDto.setSerialNoNum(tempCount);
				tempSerialNoList.add(stTaskListEntity.getSerialNo());
				
				tempWaybillNoDto.setSerialNoList(tempSerialNoList);
				
			}
		}
		
		return stTaskWaybillNoListDtoList;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:11
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#confirmStTask(java.lang.String, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Transactional(readOnly = false)
	@Override
	public void confirmStTask(String stTaskId, 
							  List<StOperatorEntity> selectedOperatorList,
							  List<StTaskWaybillNoListDto> scanResultOkList,
							  List<StTaskWaybillNoListDto> scanResultHaveNotList,
							  List<StTaskWaybillNoListDto> scanResultSurplusList) {
//		更新清仓任务状态及结束时间
		StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(stTaskId);
		
		if(null==stTaskEntity){
			throw new BusinessException("无该清仓任务");
		}
		stTaskEntity.setFinishtime(Calendar.getInstance().getTime());
		stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_DONE);
		stTaskDao.updateStTaskEntity(stTaskEntity);
//		更新清仓人信息
		updateStTask(stTaskId, selectedOperatorList);
//		插入清仓结果
//		List<StResultListEntity> stResultListEntityList = new ArrayList<StResultListEntity>();
		//正常确认的货件
		for(StTaskWaybillNoListDto waybillNoDto: scanResultOkList){
			for(String serialNo: waybillNoDto.getSerialNoList()){
				StResultListEntity stResult = new StResultListEntity();
			//	stResult.setId(UUIDUtils.getUUID());
				stResult.setSerialNo(serialNo);
				stResult.setWaybillNo(waybillNoDto.getWaybillNo());
				stResult.setStTaskId(stTaskId);
				stResult.setScanStatus(TransferConstants.SCAN_MANUAL);
				stResult.setGoodsStatus(TransferConstants.GOODS_STATUS_OK);
				stResult.setCreateTime(Calendar.getInstance().getTime());
				stResult.setPdaUploadTime(Calendar.getInstance().getTime());
				
//				stResultListEntityList.add(stResult);
				stResultListDao.addStResultListEntity(stResult);
			}
		}
		//少货的货件
		for(StTaskWaybillNoListDto waybillNoDto: scanResultHaveNotList){
			for(String serialNo: waybillNoDto.getSerialNoList()){
				StResultListEntity stResult = new StResultListEntity();
		//		stResult.setId(UUIDUtils.getUUID());
				stResult.setSerialNo(serialNo);
				stResult.setWaybillNo(waybillNoDto.getWaybillNo());
				stResult.setStTaskId(stTaskId);
				stResult.setScanStatus(TransferConstants.SCAN_MANUAL);
				stResult.setGoodsStatus(TransferConstants.GOODS_STATUS_LACK);
				stResult.setCreateTime(Calendar.getInstance().getTime());
				stResult.setPdaUploadTime(Calendar.getInstance().getTime());
				
//				stResultListEntityList.add(stResult);
				stResultListDao.addStResultListEntity(stResult);
			}
		}
		//多货的货件
		for(StTaskWaybillNoListDto waybillNoDto: scanResultSurplusList){
			for(String serialNo: waybillNoDto.getSerialNoList()){
				StResultListEntity stResult = new StResultListEntity();
		//		stResult.setId(UUIDUtils.getUUID());
				stResult.setSerialNo(serialNo);
				stResult.setWaybillNo(waybillNoDto.getWaybillNo());
				stResult.setStTaskId(stTaskId);
				stResult.setScanStatus(TransferConstants.SCAN_MANUAL);
				stResult.setGoodsStatus(TransferConstants.GOODS_STATUS_SURPLUS);
				stResult.setCreateTime(Calendar.getInstance().getTime());
				stResult.setPdaUploadTime(Calendar.getInstance().getTime());
				
//				stResultListEntityList.add(stResult);
				stResultListDao.addStResultListEntity(stResult);
			}
		}
		
//		stResultListDao.addStResultListBatch(stResultListEntityList);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:19
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryStWaybillInfoDtoByStTaskId(java.lang.String)
	 */
	@Override
	public List<StWaybillInfoDto> queryStWaybillInfoDtoByStTaskId(String stTaskId) {
		List<StTaskWaybillNoListDto> stTaskList = stTaskListDao.queryStTaskWaybillNoListByStTaskId(stTaskId);
		List<StWaybillInfoDto> stWaybillInfoDtoList = new ArrayList<StWaybillInfoDto>();
		for(StTaskWaybillNoListDto dto: stTaskList){
			StWaybillInfoDto waybillInfo = new StWaybillInfoDto();
//			运单号
			waybillInfo.setWaybillNo(dto.getWaybillNo());
//			件数
			waybillInfo.setGoodsAreaNum(String.valueOf(dto.getSerialNoNum()));
//			运单信息
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(null != waybillEntity){
				waybillInfo.setProductCodeDesc(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), Calendar.getInstance().getTime()).getName());
				if(waybillEntity.getGoodsWeightTotal() != null){
					waybillInfo.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
				}
				if(waybillEntity.getGoodsVolumeTotal() != null){
					waybillInfo.setVolume(waybillEntity.getGoodsVolumeTotal().doubleValue());
				}
				
			}
//			已扫描数量
			Long scanCount = stResultListDao.queryFinishedScanCountInTask(stTaskId, waybillInfo.getWaybillNo());
			waybillInfo.setScanNum(String.valueOf(scanCount));
			
			stWaybillInfoDtoList.add(waybillInfo);
		}

		return stWaybillInfoDtoList;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:25
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#queryScanDetailInStTask(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ScanDetailDto> queryScanDetailInStTask(String stTaskId,	String waybillNo) {
		
		return stResultListDao.queryScanDetailInStTask(stTaskId, waybillNo);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:30
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#createFileName(java.lang.String)
	 */
	@Override
	public String createFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换出错", ExceptionUtils.getFullStackTrace(e));
			
			throw new TfrBusinessException(TfrBusinessException.EXPORT_FILE_ERROR_CODE, "");
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:51:35
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService#exportTaskById(java.lang.String)
	 */
	@Override
	public InputStream exportTaskById(String stTaskId) {
		InputStream excelStream = null;
		
		//判断是否PDA清仓
		StTaskEntity stTaskEntity = stTaskDao.queryStTaskById(stTaskId);
		String isPda = stTaskEntity.getIspda();
		
//		定义导出表头
		String[] rowHeads;
		if("Y".equals(isPda)) {
			String[] pdaRowHeads = {"任务编号", "货区名称", "清仓人","部门", "任务建立时间", "运单号", "流水号","扫描状态", "扫描时间","运输性质","货名", "包装", "开单件数","重量", "体积", "入库时间", "总票数", "总件数", "总重量", "总体积"};
			rowHeads = pdaRowHeads;
		} else {
			String[] pcRowHeads = 	{"任务编号", "货区名称", "清仓人", "任务建立时间", "运单号", "流水号","扫描状态", "扫描时间","运输性质","货名", "包装", "开单件数","重量", "体积", "入库时间", "总票数", "总件数", "总重量", "总体积"};
			rowHeads = pcRowHeads;
		}
//		行List
		List<List<String>> rowList = new ArrayList<List<String>>();
//		获取导出数据
		List<StTaskExportDto> stTaskExportDtoList = stTaskDao.queryStTaskExportInfoById(stTaskId);
		
		//清仓快照有数据时才执行查询
		if(stTaskExportDtoList!= null && stTaskExportDtoList.size() > 0) {
			//导出数据封装
			if("Y".equals(isPda)) {
				rowList = this.getPdaTaskRowList(stTaskEntity.getTaskNo(),stTaskExportDtoList);
			} else {
				rowList = this.getTaskRowList(stTaskId, stTaskExportDtoList);
			}
		}
		
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(rowHeads);
		sheetData.setRowList(rowList);
		
		ExcelExport excelExportUtil = new ExcelExport();
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, "清仓任务", TransferConstants.EXPORT_FILE_MAX_ROW));
		
        return excelStream;
	}

	/**
	 * @author niuly
	 * @date 2014-3-17下午5:23:00
	 * @function 导出PC端清仓任务
	 * @param taskId
	 * @param stTaskExportDtoList
	 * @param rowList
	 * @param waybillMap
	 * @return
	 */
	private List<List<String>> getTaskRowList(String taskId,List<StTaskExportDto> stTaskExportDtoList) {
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		//		获取清仓人信息
		List<StOperatorEntity> operatorsList = queryOperatorsByStTaskId(taskId);
		StringBuffer operators = new StringBuffer();
		for(StOperatorEntity o: operatorsList){
			operators.append(o.getEmpName()).append(",");
		}
		String opers = operators.toString();
		if(StringUtils.endsWith(opers, ",")){
			opers = StringUtils.left(opers, opers.length() - 1);
		}
		
		//获取该任务的扫描明细
		List<StResultListEntity> resultList = stResultListDao.queryResultListByTaskId(taskId);
		Map<String,List<String>> waybillMap = new HashMap<String,List<String>>();
		if(CollectionUtils.isNotEmpty(resultList)){
			for(StResultListEntity entity : resultList){
				if(!waybillMap.containsKey(entity.getWaybillNo())){
					List<String> serialList = new ArrayList<String>();
					serialList.add(entity.getSerialNo());
					waybillMap.put(entity.getWaybillNo(), serialList);
				}else{
					waybillMap.get(entity.getWaybillNo()).add(entity.getSerialNo());
				}
			}
		}
				
//		将需导出的数据载入至行
		for(StTaskExportDto exportDto: stTaskExportDtoList){
			List<String> columnList = new ArrayList<String>();
//			"", "总体积"
			columnList.add(exportDto.getTaskNo());			//任务编号
			columnList.add(exportDto.getGoodsareaname());	//货区名称
			columnList.add(opers);						//清仓人
			columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));	//任务建立时间
			columnList.add(exportDto.getWaybillNo());		//运单号
			columnList.add(exportDto.getSerialNo());		//流水号
			//处理扫描明细
			if(waybillMap != null && waybillMap.size() >0 )
			{
				if(waybillMap.containsKey(exportDto.getWaybillNo())) {
					if(waybillMap.get(exportDto.getWaybillNo()).contains(exportDto.getSerialNo())) {
						columnList.add("已扫描");
						StResultListEntity resultEntity = new StResultListEntity();
						resultEntity	= stResultListDao.queryStResultEntity(taskId, exportDto.getWaybillNo(), exportDto.getSerialNo());
						columnList.add(com.deppon.foss.util.DateUtils.convert(resultEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss")); //扫描时间
					}
					else {
						columnList.add("未扫描");
						columnList.add("");
					}
				} else {
					columnList.add("未扫描");
					columnList.add("");
				}
			} else {
				columnList.add("未扫描");
				columnList.add("");
			}
			columnList.add(exportDto.getTransProperty()); 	//运输性质
			columnList.add(exportDto.getGoodsName());		//货名
			columnList.add(exportDto.getGoodsPackage());	//包装
			columnList.add(String.valueOf(exportDto.getGoodsQty()));		//开单件数
			columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsWeight()));			//重量
			columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsVolume()));			//体积
			columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getStockTime(), "yyyy-MM-dd HH:mm:ss"));	//入库时间
			columnList.add(String.valueOf(exportDto.getTotalWaybillNo()));		//总票数
			columnList.add(String.valueOf(exportDto.getTotalGoodsQty()));		//总件数
			columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsWeight()));	//总重量
			columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsVolume()));	//总体积
			
			rowList.add(columnList);
		}
		return rowList;
	}
	/**
	 * @author niuly
	 * @date 2014-3-17下午2:54:33
	 * @function 导出PDA清仓任务数据
	 * @param List<StTaskExportDto> stTaskExportDtoList,List<List<String>> rowList
	 * @return
	 */
	private List<List<String>> getPdaTaskRowList(String taskNo,List<StTaskExportDto> stTaskExportDtoList) {
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		//该清仓任务所有的PDA扫描记录
		List<StResultListPdaEntity> pdaResultEntityList = new ArrayList<StResultListPdaEntity>();
		pdaResultEntityList = stResultListPdaDao.queryStPdaResultList(taskNo);
		
		//Map<单号，List<PDA扫描实体>>
		Map<String,List<StResultListPdaEntity>> pdaMap = new HashMap<String,List<StResultListPdaEntity>>();
		
		for(StResultListPdaEntity entity:pdaResultEntityList) {
			//以运单号流水号为key
			String key = entity.getWaybillNo() + entity.getSerialNo();
			if(!pdaMap.containsKey(key)) {
				List<StResultListPdaEntity> list = new ArrayList<StResultListPdaEntity>();
				list.add(entity);
				pdaMap.put(key, list);
			} else {
				pdaMap.get(key).add(entity);
			}
		}
		
		//		将需导出的数据载入至行
		for(StTaskExportDto exportDto: stTaskExportDtoList){
			String key = exportDto.getWaybillNo() + exportDto.getSerialNo();
			if(pdaMap != null && pdaMap.size() > 0 && pdaMap.containsKey(key)
					&& pdaMap.get(key) != null && pdaMap.get(key).size() > 0) {
				
				List<StResultListPdaEntity> resiltList = pdaMap.get(key);
				
				//pda每条扫描记录
				for(StResultListPdaEntity entity: resiltList) {
					List<String> columnList = new ArrayList<String>();
					columnList.add(exportDto.getTaskNo());			//任务编号
					columnList.add(exportDto.getGoodsareaname());	//货区名称
					columnList.add(entity.getEmpName());			//清仓人
					if(StringUtils.isNotEmpty(entity.getEmpCode()) && StringUtils.isNotEmpty(entity.getEmpName())) {
						//获取部门
						EmployeeEntity empEntity = employeeService.queryEmployeeByEmpCodeNoCache(entity.getEmpCode());
						if(empEntity != null && empEntity.getDepartment() != null) {
							columnList.add(empEntity.getDepartment().getName()); //部门名称
						} else {
							columnList.add(""); //部门名称
						}
					} else {
						columnList.add(""); //部门名称
					}
					
					columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));	//任务建立时间
					columnList.add(exportDto.getWaybillNo());		//运单号
					columnList.add(exportDto.getSerialNo());		//流水号
					columnList.add("已扫描");
					columnList.add(com.deppon.foss.util.DateUtils.convert(entity.getScanTime(), "yyyy-MM-dd HH:mm:ss")); //扫描时间
					columnList.add(exportDto.getTransProperty()); 	//运输性质
					columnList.add(exportDto.getGoodsName());		//货名
					columnList.add(exportDto.getGoodsPackage());	//包装
					columnList.add(String.valueOf(exportDto.getGoodsQty()));		//开单件数
					columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsWeight()));			//重量
					columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsVolume()));			//体积
					columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getStockTime(), "yyyy-MM-dd HH:mm:ss"));	//入库时间
					columnList.add(String.valueOf(exportDto.getTotalWaybillNo()));		//总票数
					columnList.add(String.valueOf(exportDto.getTotalGoodsQty()));		//总件数
					columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsWeight()));	//总重量
					columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsVolume()));	//总体积
					
					rowList.add(columnList);
				}
			} else {
				List<String> columnList = new ArrayList<String>();
				columnList.add(exportDto.getTaskNo());			//任务编号
				columnList.add(exportDto.getGoodsareaname());	//货区名称
				columnList.add("");			//清仓人
				columnList.add(""); //部门名称
				columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));	//任务建立时间
				columnList.add(exportDto.getWaybillNo());		//运单号
				columnList.add(exportDto.getSerialNo());		//流水号
				columnList.add("未扫描");							//是否扫描
				columnList.add("");								//扫描时间
				columnList.add(exportDto.getTransProperty()); 	//运输性质
				columnList.add(exportDto.getGoodsName());		//货名
				columnList.add(exportDto.getGoodsPackage());	//包装
				columnList.add(String.valueOf(exportDto.getGoodsQty()));		//开单件数
				columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsWeight()));			//重量
				columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getGoodsVolume()));			//体积
				columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getStockTime(), "yyyy-MM-dd HH:mm:ss"));	//入库时间
				columnList.add(String.valueOf(exportDto.getTotalWaybillNo()));		//总票数
				columnList.add(String.valueOf(exportDto.getTotalGoodsQty()));		//总件数
				columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsWeight()));	//总重量
				columnList.add(new java.text.DecimalFormat("#0.00").format(exportDto.getTotalGoodsVolume()));	//总体积
				
				rowList.add(columnList);
			}
			
		}
		return rowList;
	}
	
	
	@Override
	public List<StTaskDto> queryTaskInProcess(String deptCode, String goodsAreaCode) {
		return stTaskDao.queryTaskInProcess(deptCode, goodsAreaCode);
	}
	/**
	 * 获取当前部门的上级外场、空运总调大部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-14 下午3:50:19
	 */
	@Override
	public OrgAdministrativeInfoEntity getBigOrg(OrgAdministrativeInfoEntity currentOrg){
		if(StringUtils.equals(FossConstants.YES, currentOrg.getSalesDepartment()) || 
				StringUtils.equals(FossConstants.YES, currentOrg.getAirDispatch()) ||
				StringUtils.equals(FossConstants.YES, currentOrg.getTransferCenter())){
			System.out.println("普通营业部门>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return currentOrg;
		}else{
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			//空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrg.getCode(), bizTypesList);
			if(bigOrg == null){
				LOGGER.error("查询部门：" + currentOrg.getCode() + " 所属外场或空运总调大部门失败");
				throw new TfrBusinessException("查询当前部门所属外场或空运总调失败","");
			}
			return bigOrg;
		}
	}

	/**
	 * 查询外场的货区统计信息
	 * @author 205109-foss-zenghaibin
	 * @date 2014-9-19 下午2:36:18
	 * @param currentOrg
	 * @return StockcheckingVO
	 */
	public StockcheckingVO statistics(){
		
		OrgAdministrativeInfoEntity stockOrg = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			stockOrg=this.getBigOrg(expressOrgEntity);
		}else{
			stockOrg=this.getBigOrg(FossUserContext.getCurrentDept());
		}
			
			if(StringUtils.equals(FossConstants.YES, stockOrg.getTransferCenter())){
				//是外场
				return stTaskDao.statistics(stockOrg.getCode());
			}else{
				
				return null;
			}
    //非外场
	}
	/**
	 * @author niuly
	 * @date 2014-6-16下午4:31:50
	 * @function 自动取消清仓任务
	 */
	@Override
	public void cancelStTask() {
		//默认24小时
		
		int hours = ConstantsNumberSonar.SONAR_NUMBER_24;
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM_CANCEL_ST_TASK_TIME, FossConstants.ROOT_ORG_CODE);
			hours = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.CANCEL_ST_TASK.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.CANCEL_ST_TASK.getBizCode());
			jobProcessLogEntity.setRemark("自动取消清仓任务时获取配置参数失败失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		/**
		 *下面过程为，对于每个不同部门实现不同配置时间，取消清仓任务
		 * 思路如下：先取配置了时间的部门编码和配置时间值，放入map中，如果是相同时间的则放入同一个Map<String,List<Sring>>，key->时间，value->部门编码list
		 * @author 205109 zenghaibin
		 * @date 2014-11-01 17:10:45
		 ***/
		List<ConfigurationParamsEntity> list = new ArrayList<ConfigurationParamsEntity>();//放所配置的部门参数实体
		Map<String,List<String>> map = new HashMap<String,List<String>>();//对应时间和部门的map
	
		//List<String> allDeptCodes = new ArrayList<String>();	//所有配置部门code
		ConfigurationParamsEntity entity=new ConfigurationParamsEntity();
		entity.setCode(ConfigurationParamsConstants.TFR_PARM_CANCEL_ST_TASK_TIME);
		entity.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR);
		list=configurationParamsService.queryConfigurationParamsExactByEntity(entity,0,Integer.MAX_VALUE);//查询已经配置的部门和时间
		for(ConfigurationParamsEntity e:list) {
			//allDeptCodes.add(e.getOrgCode());
			
			if(map.containsKey(e.getConfValue())) {
				
				map.get(e.getConfValue()).add(e.getOrgCode());
			} else {
				List<String> arry = new ArrayList<String>();
				arry.add(e.getOrgCode());
				map.put(e.getConfValue(), arry);
			}
		}
		Set<String> keys = map.keySet();
		for(String key:keys) {
			if(!StringUtil.equals(key, hours+"")){
				List<String> deptCodeList = map.get(key);
				if(deptCodeList!=null&&!deptCodeList.isEmpty()){
					stTaskDao.updateTaskByDeptCode(key,deptCodeList);
				}
			}
		}
		stTaskDao.updateTask(hours);
	
	}

	/**
	 * @author nly
	 * @date 2014年10月30日 上午9:06:16
	 * @function 是否试点外场
	 * @return
	 */
	@Override
	public boolean isTestTrans() {
		boolean isTestTrans = false;
		//取上级外场
		OrgAdministrativeInfoEntity orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
		
		if(orgEntity != null) {
			//外场code
			String transferCode = orgEntity.getCode();
			//查询行政区清仓试点外场数据字典,若查不到或查询结果为空，则表示全国使用；若不为空，再根据外场code查询
			List<DataDictionaryValueEntity>  list = new ArrayList<DataDictionaryValueEntity>();
			try {
				list = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.TFR_ST_TASK_TEST_TRANS);
			} catch(Exception e) {
				//发生异常时，默认全国外场可用
				isTestTrans = true;
				LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			}
			//未配置参数时，全国外场可用
			if(null == list || list.size() == 0) {
				isTestTrans = true;
			} 
			//有参数时，判断该外场是否为试点
			 if(null != list && list.size() > 0) {
				 for(DataDictionaryValueEntity entity : list) {
					 if(StringUtils.isNotEmpty(transferCode) && transferCode.equals(entity.getValueCode())) {
						 isTestTrans = true;
					 }
				 }
			 }
			
		}
		return isTestTrans;
	}
	/**
	 * @author nly
	 * @date 2014年11月11日 下午4:35:55
	 * @function 查询所属外场
	 * @return
	 */
	@Override
	public String queryTransferCode() {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity=this.getBigOrg(expressOrgEntity);
		}else{
			orgEntity=this.getBigOrg(FossUserContext.getCurrentDept());
		}
		
		String currentDeptCode = "";
//		对于空运总调型的，需找到对应的外场所属的库区
		if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//			获取空运总调对应的外场编号
			currentDeptCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
			
		}else{
			currentDeptCode = orgEntity.getCode();
		}
		return currentDeptCode;
	}
	
	
	/**
	 * Author:268084
	 * 根据部门编码查询外场
	 * @param orgCode
	 * @return
	 */
	@Override
	public OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode) {
		if (StringUtils.isEmpty(orgCode)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,
						bizTypesList);

		return orgEntity;
	}
	
	
}