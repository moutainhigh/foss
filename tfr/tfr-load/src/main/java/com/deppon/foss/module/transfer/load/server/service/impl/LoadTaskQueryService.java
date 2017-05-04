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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/LoadTaskQueryService.java
 *  
 *  FILE NAME          :LoadTaskQueryService.java
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadTaskVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ToFossTaskDetailVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ToFossTaskVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ToFossWaybillVo;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.DateUtils;


/**
 * 查询装车任务service
 * @author ibm-zhangyixin
 * @date 2012-12-4 下午2:57:34
 */
public class LoadTaskQueryService implements ILoadTaskQueryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadTaskQueryService.class);
	private ILoadTaskQueryDao loadTaskQueryDao;
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IFOSSToWkService fossToWkService;

	public void setLoadTaskQueryDao(ILoadTaskQueryDao loadTaskQueryDao) {
		this.loadTaskQueryDao = loadTaskQueryDao;
	}
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据部门code找顶级组织 
	 * @param orgCode
	 * @return    
	 * @return OrgAdministrativeInfoEntity    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-28下午4:46:59
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
		
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}

	/**
	 * 根据输入的参数查询出装车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:24
	 */
	public List<LoadTaskDto> queryLoadTask(LoadTaskDto loadTaskDto, int limit, int start) {
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		loadTaskDto.setOrigOrgCode(orgCode);
		return loadTaskQueryDao.queryLoadTask(loadTaskDto, limit, start);
	}

	/**
	 * 查询出总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:43
	 */
	public Long getTotCount(LoadTaskDto loadTaskDto) {
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		loadTaskDto.setOrigOrgCode(orgCode);
		return loadTaskQueryDao.getTotCount(loadTaskDto);
	}

	/**
	 * 根据装车任务ID 查询出所有理货员
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:54
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderByTaskId(String taskId) {
		return loadTaskQueryDao.queryLoaderByTaskId(taskId);
	}

	/**
	 * 根据装车任务编号查询出所有的装车运单明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:14
	 */
	@Override
	public List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo) {
		return loadTaskQueryDao.queryLoadWayBillByTaskNo(taskNo);
	}

	/**
	 * 根据任务No得到装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:35
	 */
	@Override
	public LoadTaskDto getLoadTaskByTaskNo(String taskNo) {
		return loadTaskQueryDao.getLoadTaskByTaskNo(taskNo);
	}

	/**
	 * 根据loadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:57
	 */
	@Override
	public List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(
			String loadWaybillDetailId) {
		return loadTaskQueryDao.queryloadSerialNoByLoadWaybillDetailId(loadWaybillDetailId);
	}

	/**
	 * 根据配送装车No获取配送装车Id
	 * @author ibm-zhangyixin
	 * @date 2012-12-3 下午5:18:11
	 */
	@Override
	public String queryLoadGaprepIdByGaprepNo(String gaprepNo) {
		return loadTaskQueryDao.queryLoadGaprepIdByGaprepNo(gaprepNo);
	}

	/**
	 * 根据派送装车差异报告ID查询出派送装车数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:06
	 */
	@Override
	public DeliverLoadGapReportEntity queryLoadGaprepReport(String loadGaprepId) {
		return loadTaskQueryDao.queryLoadGaprepReport(loadGaprepId);
	}

	/**
	 * 根据派送装车差异报告ID查询出运单数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:32
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(
			String loadGaprepId) {
		return loadTaskQueryDao.queryDeliverLoadGapReportWayBills(loadGaprepId);
	}

	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码 
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:10:28
	 */ 
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}

	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出装车明细数据  
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:10:28
	 */ 
	@Override
	public InputStream exportLoadWayBillByTaskNo(String taskNo) {
		InputStream excelStream = null;
		
		List<LoadWayBillDetailDto> loadWayBillDetails = loadTaskQueryDao.queryExportLoadWayBillByTaskNo(taskNo);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LoadWayBillDetailDto loaderWorkload : loadWayBillDetails){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//出发部门
			columnList.add(loaderWorkload.getOrigOrgName());
			
			//到达部门
			columnList.add(loaderWorkload.getReachOrgName());
			
			//运单号
			columnList.add(loaderWorkload.getWaybillNo());
			
			//流水号
			columnList.add(loaderWorkload.getSerialNo());

			//扫描时间
			Date optTime = loaderWorkload.getOptTime();
			String optTimeStr = "";
			if(optTime != null) {
				optTimeStr = DateUtils.convert(optTime);
			}
			columnList.add(optTimeStr);
			
			//运输性质
			columnList.add(loaderWorkload.getTransportType());
			
			//库存件数
			Integer stockQty = loaderWorkload.getStockQty() == null ? 0 : loaderWorkload.getStockQty();
			columnList.add(stockQty + "");
			
			//已扫描件数
			Integer scanQty = loaderWorkload.getScanQty() == null ? 0 : loaderWorkload.getScanQty();
			columnList.add(scanQty + "");
			
			//已装车件数
			Integer loadQty = loaderWorkload.getLoadQty() == null ? 0 : loaderWorkload.getLoadQty();
			columnList.add(loadQty + "");
			
			//库存重量(公斤)
			BigDecimal stockWeight = loaderWorkload.getStockWeight() == null ? BigDecimal.ZERO : loaderWorkload.getStockWeight();
			DecimalFormat df = new DecimalFormat("0.000");
			columnList.add(df.format(stockWeight));
			
			//库存体积(方)
			BigDecimal stockVolume = loaderWorkload.getStockVolume() == null ? BigDecimal.ZERO : loaderWorkload.getStockVolume();
			columnList.add(df.format(stockVolume));
			
			//货名
			columnList.add(loaderWorkload.getGoodsName());
			
			//包装
			columnList.add(loaderWorkload.getPack());
			rowList.add(columnList);
		}
		String[] rowHeads = {"出发部门","到达部门","运单号","流水号", "扫描时间", "运输性质","库存件数","已扫描件数","已装车件数",
				"库存重量(公斤)","库存体积(方)","货名","包装"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("装车明细");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	
	/** 
	 * @Title: exportLoadTask 
	 * @Description: 根据查询参数导出装车任务
	 * @param loadTaskDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService#exportLoadTask(com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午9:57:08
	 */ 
	@Override
	public InputStream exportLoadTask(LoadTaskDto loadTaskDto) {
		InputStream excelStream = null;
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		loadTaskDto.setOrigOrgCode(orgCode);
		List<LoadTaskDto> loadTasks = loadTaskQueryDao.queryLoadTask(loadTaskDto);
		
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LoadTaskDto loadTask : loadTasks){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//任务编号
			columnList.add(loadTask.getTaskNo());
			
			//差异报告编号
			columnList.add(loadTask.getGaprepNo());
			
			//车牌号
			columnList.add(loadTask.getVehicleNo());
			
			//线路
			columnList.add(loadTask.getLine());

			//理货员
			columnList.add(loadTask.getLoaderName());

			//月台号
			columnList.add(loadTask.getPlatformNo());
			
			//装车类型
			columnList.add(getTaskTypeMap().get(loadTask.getTaskType()));
			
			//装车状态
			columnList.add(getStateMap().get(loadTask.getState()));
			
			//规定发车时间
			columnList.add(DateUtils.convert(loadTask.getPlanDepartTime()));

			//任务建立时间
			columnList.add(loadTask.getLoadStartTime());

			//任务完成时间
			columnList.add(loadTask.getLoadEndTime());
			rowList.add(columnList);
		}
		String[] rowHeads = {"任务编号","差异报告编号","车牌号","线路","理货员","月台号","装车类型",
				"装车状态","规定发车时间","任务建立时间","任务完成时间"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("装车任务");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	
	private Map<String, String> taskTypeMap;
	private Map<String, String> getTaskTypeMap() {
		if(taskTypeMap == null) {
			taskTypeMap = new HashMap<String, String>();
			taskTypeMap.put("DELIVER_LOAD", "派送装车");
			taskTypeMap.put("PARTIALLINE_LOAD", "偏线装车");
			taskTypeMap.put("LONG_DISTANCE_LOAD", "长途装车");
			taskTypeMap.put("SHORT_DISTANCE_LOAD", "短途装车");
		}
		return taskTypeMap;
	}

	private Map<String, String> stateMap;
	private Map<String, String> getStateMap() {
		if(stateMap == null) {
			stateMap = new HashMap<String, String>();
			stateMap.put("LOADING", "进行中");
			stateMap.put("FINISHED", "已完成");
			stateMap.put("SUBMITED", "已提交");
			stateMap.put("CANCELED", "已取消");
		}
		return stateMap;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-4-28下午4:59:36
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	

	@Override
	public List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo, String source) {
		List<LoadWayBillDetailDto> dto = new ArrayList<LoadWayBillDetailDto>();
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.info("对接悟空开关（是否查询悟空装车明细）：" + tfrSwitch4Ecs);
		if(tfrSwitch4Ecs){
			List<LoadWayBillDetailDto> dtokuai = queryWKLoadWayBillByTaskNo(taskNo);
			if (dtokuai != null) {
				dto.addAll(dtokuai);
			}
		}
		List<LoadWayBillDetailDto> dtolin = loadTaskQueryDao.queryLoadWayBillByTaskNo(taskNo);
		if (dtolin != null) {
			dto.addAll(dtolin);
		}
		// 悟空同步过来的调接口
//		if (LoadSourceEnum.KUIDI.getCode().equals(Integer.valueOf(source))) {
//			return queryWKLoadWayBillByTaskNo(taskNo);
//		} else {
//			return loadTaskQueryDao.queryLoadWayBillByTaskNo(taskNo);
//		}
		
		return dto;
	}

	@Override
	public List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(LoadTaskVo loadTaskVo) {
		if (StringUtils.isEmpty(loadTaskVo.getLoadSerialNo().getCargoType())) {
			return loadTaskQueryDao.queryloadSerialNoByLoadWaybillDetailId(loadTaskVo.getLoadSerialNo().getLoadWaybillDetailId());
		} else  {
			// 当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			// 当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
			// 当前部门顶级组织code
			String orgCode = administrativeInfo.getCode();
			Map<String, String> map = new HashMap<String, String>();
			map.put("loadTaskNo", loadTaskVo.getLoadTaskDto().getTaskNo());
			map.put("operationOrgCode", orgCode);
			map.put("cargoType", loadTaskVo.getLoadSerialNo().getCargoType());
			map.put("cargoNo", loadTaskVo.getLoadSerialNo().getCargoNo());
			
			String requestParameter = JSON.toJSONString(map);
			try {
				ToFossTaskVo taskvo = (ToFossTaskVo) fossToWkService.QueryWKLoadTaskDetail(requestParameter);
				List<ToFossWaybillVo> detailList = taskvo.getToFossWaybillDtoList();
				if(detailList != null ) {
					List<LoadSerialNoEntity> resList = new ArrayList<LoadSerialNoEntity>();
					for (ToFossWaybillVo dto : detailList) {
						
						resList.add(dto.getLoadSerialNoEntity());
					}
					return resList;
				} 
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public InputStream exportLoadWayBillByTaskNo(String taskNo, String source) {
		InputStream excelStream = null;
		
		List<LoadWayBillDetailDto> loadWayBillDetails = new ArrayList<LoadWayBillDetailDto>();
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.info("对接悟空开关（是否export悟空装车明细）：" + tfrSwitch4Ecs);
		if(tfrSwitch4Ecs){
			List<LoadWayBillDetailDto> dtokuai = queryWKLoadWayBillByTaskNo(taskNo);
			if (dtokuai != null) {
				loadWayBillDetails.addAll(dtokuai);
			}
		}
		List<LoadWayBillDetailDto> dtolin = loadTaskQueryDao.queryExportLoadWayBillByTaskNo(taskNo);
		if (dtolin != null) {
			loadWayBillDetails.addAll(dtolin);
		}
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LoadWayBillDetailDto loaderWorkload : loadWayBillDetails){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//出发部门
			columnList.add(loaderWorkload.getOrigOrgName());
			
			//到达部门
			columnList.add(loaderWorkload.getReachOrgName());
			
			//运单号
			columnList.add(loaderWorkload.getWaybillNo());
			
			//流水号
			columnList.add(loaderWorkload.getSerialNo());

			//扫描时间
			Date optTime = loaderWorkload.getOptTime();
			String optTimeStr = "";
			if(optTime != null) {
				optTimeStr = DateUtils.convert(optTime);
			}
			columnList.add(optTimeStr);
			
			//运输性质
			columnList.add(loaderWorkload.getTransportType());
			
			//库存件数
			Integer stockQty = loaderWorkload.getStockQty() == null ? 0 : loaderWorkload.getStockQty();
			columnList.add(stockQty + "");
			
			//已扫描件数
			Integer scanQty = loaderWorkload.getScanQty() == null ? 0 : loaderWorkload.getScanQty();
			columnList.add(scanQty + "");
			
			//已装车件数
			Integer loadQty = loaderWorkload.getLoadQty() == null ? 0 : loaderWorkload.getLoadQty();
			columnList.add(loadQty + "");
			
			//库存重量(公斤)
			BigDecimal stockWeight = loaderWorkload.getStockWeight() == null ? BigDecimal.ZERO : loaderWorkload.getStockWeight();
			DecimalFormat df = new DecimalFormat("0.000");
			columnList.add(df.format(stockWeight));
			
			//库存体积(方)
			BigDecimal stockVolume = loaderWorkload.getStockVolume() == null ? BigDecimal.ZERO : loaderWorkload.getStockVolume();
			columnList.add(df.format(stockVolume));
			
			//货名
			columnList.add(loaderWorkload.getGoodsName());
			
			//包装
			columnList.add(loaderWorkload.getPack());
			rowList.add(columnList);
		}
		String[] rowHeads = {"出发部门","到达部门","运单号","流水号", "扫描时间", "运输性质","库存件数","已扫描件数","已装车件数",
				"库存重量(公斤)","库存体积(方)","货名","包装"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("装车明细");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	
	
	
	public List<LoadWayBillDetailDto> queryWKLoadWayBillByTaskNo(String taskNo) {
			// 当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			// 当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
			// 当前部门顶级组织code
			String orgCode = administrativeInfo.getCode();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("loadTaskNo", taskNo);
			map.put("operationOrgCode", orgCode);
			String requestParameter = JSON.toJSONString(map);
			
			try {
				ToFossTaskVo taskvo = (ToFossTaskVo) fossToWkService.QueryWKLoadTaskDetail(requestParameter);
				List<ToFossTaskDetailVo> detailList = (List<ToFossTaskDetailVo>) taskvo.getToFossTaskDetailDtoList();
				if(detailList != null ) {
					List<LoadWayBillDetailDto> resList = new ArrayList<LoadWayBillDetailDto>();
					for (ToFossTaskDetailVo dto : detailList) {
						
						resList.add(dto.getLoadWayBillDetailDto());
					}
					return resList;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		
	}

}