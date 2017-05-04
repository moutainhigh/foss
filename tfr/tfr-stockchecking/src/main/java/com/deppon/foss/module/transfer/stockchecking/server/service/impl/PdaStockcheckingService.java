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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/service/impl/PdaStockcheckingService.java
 *  
 *  FILE NAME          :PdaStockcheckingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.StTaskSubmitException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaPackageStTastDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.StTaskZoneDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskPdaEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;

/**
 * 清仓任务相关的PDA接口服务
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:48:35
 */
@Transactional(readOnly = false)
public class PdaStockcheckingService implements IPdaStockcheckingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaStockcheckingService.class);
	
	private IStTaskDao stTaskDao;
	private IStTaskPdaDao stTaskPdaDao;
	private IStTaskListDao stTaskListDao;
	private IStResultListDao stResultListDao;
	private IStResultListPdaDao stResultListPdaDao;
	private IStOperatorDao stOperatorDao;
	
	private ITfrCommonService tfrCommonService;
	private IStockService stockService;
	private IGoodsAreaService goodsAreaService;
	private IEmployeeService employeeService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	@Resource
	private IProductService productService4Dubbo;
	private IProductService productService;
	private IOutfieldService outfieldService;
	private ISaleDepartmentService saleDepartmentService;
	private IAdministrativeRegionsService administrativeRegionsService;
	
	
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;
	
	private IConfigurationParamsService configurationParamsService;
	private ISendDistrictMapService sendDistrictMapService;
	//清仓提货方式-自提
	private static final String RECEIVE_METHOD_PICKUP = "PICKUP";
	//清仓提货方式-派送
	private static final String RECEIVE_METHOD_DELIVER = "DELIVER";
	
	public void setSendDistrictMapService(
			ISendDistrictMapService sendDistrictMapService) {
		this.sendDistrictMapService = sendDistrictMapService;
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

	public void setStResultListDao(IStResultListDao stResultListDao) {
		this.stResultListDao = stResultListDao;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	public void setStOperatorDao(IStOperatorDao stOperatorDao) {
		this.stOperatorDao = stOperatorDao;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setStTaskPdaDao(IStTaskPdaDao stTaskPdaDao) {
		this.stTaskPdaDao = stTaskPdaDao;
	}

	public void setStResultListPdaDao(IStResultListPdaDao stResultListPdaDao) {
		this.stResultListPdaDao = stResultListPdaDao;
	}
	
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
//	
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:48:50
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#queryGoodsAreaCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String queryGoodsAreaCode(String waybillNo, String serialNo,	String deptCode) {
		LOGGER.trace("into stockchecking pda service");
		
		StockEntity stockEntity = stockService.queryUniqueStock(waybillNo, serialNo);
//		库存中未找到时，返回空值
		if(null == stockEntity){
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_STTASK_GOODS_IS_NULL);
		}else{
			return stockEntity.getGoodsAreaCode();
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
	 * <pre>
	 * 建立清仓任务
	 * 1、当传入清仓任务编号为空时
	 *  1.2、如果本部门本库区存在一个清仓中的任务，则直接返回此任务对象，不建立新的任务
	 *  1.1、直接针对此PDA设备编号生成一个新的清仓任务
	 *  1.2、生成此PDA设备生成对应的PDA清仓任务分支
	 * 2、当传入清仓任务编号不为空时
	 *  2.1、通过此清仓任务编号，尝试查询已存在的清仓任务
	 *  2.2、存在的返回已存在的清仓任务对象
	 *  </pre>
	 * 
	 * @param deptCode      部门编号
	 * @param goodsAreaCode 货区编号
	 * @param operatorCode  操作人编号
	 * @param pdaNo         pda设备编号
	 * @param stTaskNo      清仓任务编号
	 * @return List<PdaStTaskDto> pda所需的清仓任务明细列表
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-12-13 上午10:10:15
	 */
	/*@Override
	public PdaStTaskDto createPdaStTask(String deptCode, String goodsAreaCode, String operatorCode, String pdaNo,String stTaskNo, String isStation, Integer startQty,Integer endQty) {
		LOGGER.trace("into stockchecking pda service");
		
		boolean isTransferCenter = false;  //是否为外场
		boolean isCommonSaleDepartment = false; //是否为一般性质的营业部，无派送职能
		boolean isByQty = false;	//是否按件清仓
		PdaStTaskDto pdaStTaskDto = new PdaStTaskDto();
		
		isByQty = StringUtils.equals("Y", isStation) && startQty != null && endQty != null && startQty > 0 && endQty > 0;
		
		if(StringUtils.isEmpty(deptCode)){
			throw new TfrBusinessException("此部门编号：" + deptCode + " 为空");
		}
//		通过PDA传入的部门编号获取部门实体，判定此部门性质是否为营业部或者外场
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
//		外场情况
		if(StringUtils.equals(FossConstants.YES, org.getTransferCenter())){
			isTransferCenter = true;
			//若是派送货区清仓
			if(StringUtils.equals("Y", isStation)) {
				//查询驻地派送货区
				goodsAreaCode = this.queryStationCode(deptCode);
				//库位号
				List<BaseDataDictDto> positionNoList = stockService.queryStorageListByGoodsAreaFrom(deptCode,goodsAreaCode);
				pdaStTaskDto.setPositionNoList(positionNoList);
			}
//			若为外场，则货区编号不能为空
			if(StringUtils.equals(FossConstants.YES, org.getTransferCenter()) && StringUtils.isNotEmpty(deptCode) && StringUtils.isEmpty(goodsAreaCode)){
				throw new TfrBusinessException("此外场：" + deptCode + " 的库区：" + goodsAreaCode + "编号不匹配");
			}
//		营业部情况
		}else if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())){
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(deptCode);
//			判断此营业部是否驻地部门并且是否有派送职能
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) && StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getDelivery())){
//				如果有派送职能的，需找到对应的外场及对应的库区
				deptCode = saleDepartmentEntity.getTransferCenter();
				
				if(null != deptCode){
					isTransferCenter = true;
				}
				
				List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(deptCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				if(CollectionUtils.isNotEmpty(goodsAreaList)){
					goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
//				根据部门查询货区失败
				}else{
					throw new TfrBusinessException("驻地派送营业部未找到外场对应的库区，外场编号：" + deptCode);
				}
			}else{
				isCommonSaleDepartment = true;
			}
			
//		空运总调情况
		}else if(StringUtils.equals(FossConstants.YES, org.getAirDispatch())){
//		获取空运总调对应的外场编号
			deptCode = outfieldService.queryTransferCenterByAirDispatchCode(org.getCode());
			if(StringUtils.isNotEmpty(deptCode)){
				isTransferCenter = true;
			}
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(deptCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
//			根据部门查询货区失败
			}else{
				throw new TfrBusinessException("空运总调未找到外场对应的库区，外场编号：" + deptCode);
			}
//		否则需找到对应的上级为外场或营业部性质的部门编码
		}else{
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			bizTypes.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
			OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
			if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
				throw new TfrBusinessException("未找到此部门：" + deptCode + " 所对应的的上级 外场 或 营业部");
			}else{
				deptCode = parentOrg.getCode();
				
				if(StringUtils.equals(FossConstants.YES, parentOrg.getTransferCenter())){
					isTransferCenter = true;
					//若是派送货区清仓
					if(StringUtils.equals("Y", isStation)) {
						//查询驻地派送货区
						goodsAreaCode = this.queryStationCode(deptCode);
						//库位号
						List<BaseDataDictDto> positionNoList = stockService.queryStorageListByGoodsAreaFrom(deptCode,goodsAreaCode);
						pdaStTaskDto.setPositionNoList(positionNoList);
					}
				}
			}
		}
		
		if(isCommonSaleDepartment){
			goodsAreaCode = null;
		}
//		若找到本级或者上级为外场的情况，需判断传入的货区是否存在于此外场下，不存在的情况抛出异常
		if(isTransferCenter){
			//校验外场下的货区是否存在
			GoodsAreaEntity godsAreaEntity = goodsAreaService.queryGoodsAreaByCode(deptCode, goodsAreaCode);
			if(null == godsAreaEntity){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_ERROR_DEPT);
			}
		}
		
//			如果编号为空，则尝试新建清仓任务
		if(StringUtils.isBlank(stTaskNo)){
//				通过清仓任务所在部门及库区判断是否存在为结束的清仓任务，若存在，则抛出异常
			List<StTaskDto> existsStTaskDtoList = stTaskDao.queryTaskInProcess(deptCode, goodsAreaCode);
			if(existsStTaskDtoList.size() > 0){
				//派送货区按件清仓
				if(isByQty) {
					for(StTaskDto dto:existsStTaskDtoList) {
						//非按件清仓
						if(dto.getStartQty() == null || dto.getStartQty() == 0 || dto.getEndQty() == null || dto.getEndQty() ==0) {
							throw new TfrBusinessException("已经存在全部货区的清仓任务，任务号：" + dto.getTaskNo() +",不能再按件清仓！");
						} else {
							if(startQty > dto.getEndQty() || endQty < dto.getStartQty()) {
								//不存在交叉的按件清仓任务,可以新建任务
								
							} else {
								//存在符合条件的按件清仓任务
								if(dto.getStartQty().equals(startQty) && dto.getEndQty().equals(endQty)) {
									//是PDA创建的任务，则返回任务
									if(StringUtils.equals("Y", dto.getIspda())) {
										StTaskEntity existTask = stTaskDao.queryStTaskByNo(dto.getTaskNo());
										pdaStTaskDto.setStTaskNo(existTask.getTaskNo());
										pdaStTaskDto.setCreatorCode(existTask.getCreatorCode());
										pdaStTaskDto.setGoodsAreaCode(existTask.getGoodsareacode());
										pdaStTaskDto.setGoodsAreaName(existTask.getGoodsareaname());
										
//						插入PDA清仓任务日志表
										StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
										stTaskPdaEntity.setStTaskNo(existTask.getTaskNo());
										stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
										stTaskPdaEntity.setPdaNo(pdaNo);
										stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
										stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
										
										stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
										
										return pdaStTaskDto;
									} else {
										//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
										throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
									}
								} else{
									//存在交叉的件区，给出提示
									throw new TfrBusinessException("已经存在"+ dto.getStartQty()+ "-" + dto.getEndQty() + "件区的清仓任务，任务号："+ dto.getTaskNo() +",不允许创建件数交叉的任务！");
								}
							}
						}
						
					}	
				} else {
					//存在PC端清仓中的任务时，不允许PDA创建任务
					boolean isPda = true;
					StringBuffer taskNos = new StringBuffer("");
					for(StTaskDto dto:existsStTaskDtoList) {
						boolean tempIsPda = true;
						if(StringUtils.equals("Y", dto.getIspda())) {
							tempIsPda = true;
						} else {
							tempIsPda = false;
							taskNos.append(dto.getTaskNo());
							taskNos.append(",");
						}
						isPda = isPda && tempIsPda;
					}
					//已存在按件清仓任务，则不允许创建整个货区的任务
					if(existsStTaskDtoList.get(0).getStartQty() != null && existsStTaskDtoList.get(0).getStartQty() >0
							&& existsStTaskDtoList.get(0).getEndQty() != null && existsStTaskDtoList.get(0).getEndQty() >0) {
						throw new TfrBusinessException("已经存在"+ existsStTaskDtoList.get(0).getStartQty()+ "-" + existsStTaskDtoList.get(0).getEndQty() + "件区的清仓任务，任务号："+ existsStTaskDtoList.get(0).getTaskNo() +",不允许创建整个货区的清仓任务！");
					} else {
						//PDA建立的任务
						if(StringUtils.equals("Y", existsStTaskDtoList.get(0).getIspda())) {
							StTaskEntity existTask = stTaskDao.queryStTaskByNo(existsStTaskDtoList.get(0).getTaskNo());
							pdaStTaskDto.setStTaskNo(existTask.getTaskNo());
							pdaStTaskDto.setCreatorCode(existTask.getCreatorCode());
							pdaStTaskDto.setGoodsAreaCode(existTask.getGoodsareacode());
							pdaStTaskDto.setGoodsAreaName(existTask.getGoodsareaname());
							
//				插入PDA清仓任务日志表
							StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
							stTaskPdaEntity.setStTaskNo(existTask.getTaskNo());
							stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
							stTaskPdaEntity.setPdaNo(pdaNo);
							stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
							stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
							
							stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
							
							
							return pdaStTaskDto;
						} else {
							throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + existsStTaskDtoList.get(0).getTaskNo() +",请处理完后再创建！");
						}
					}
				}
			}
			
			StTaskEntity stTaskEntity = new StTaskEntity();
			stTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.QC, deptCode);
			stTaskEntity.setTaskNo(stTaskNo);
			stTaskEntity.setGoodsareacode(goodsAreaCode);
			stTaskEntity.setGoodsareaname(goodsAreaService.queryNameByCode(deptCode, goodsAreaCode));
			stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_DOING);
			stTaskEntity.setDeptcode(deptCode);
			stTaskEntity.setCreatetime(Calendar.getInstance().getTime());
			stTaskEntity.setIspda(TransferDictionaryConstants.YES);
			stTaskEntity.setPdaNo(pdaNo);
			stTaskEntity.setCreatorCode(operatorCode);
			stTaskEntity.setCreatorName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
			if(isByQty) {
				stTaskEntity.setStartQty(startQty);
				stTaskEntity.setEndQty(endQty);
			}
			//在部门编号和货区上加业务锁
			MutexElement mutex = null;
			try {
				//锁定对象
				String lockStr = deptCode + goodsAreaCode;
				mutex = new MutexElement(lockStr, "新增清仓任务", MutexElementType.TFR_STOCKCHECKING_ADDNEW);
				// 锁定
				boolean flag = businessLockService.lock(mutex, 0);
				if (flag) {
					//判断是否已存在该部门该货区的清仓任务
					List<StTaskDto> taskList = stTaskDao.queryTaskInProcess(deptCode, goodsAreaCode);
					if(taskList != null && taskList.size() > 0) {
						if(isByQty) {
							for(StTaskDto dto: taskList) {
								if(dto.getStartQty() != null && dto.getStartQty() >0 && dto.getEndQty() != null && dto.getEndQty() > 0
										&& startQty == dto.getStartQty() && endQty == dto.getEndQty()) {
									throw new TfrBusinessException("本部门的库区"+ dto.getGoodsareaname() +"已经存在未结束的"+ startQty + "-" + endQty+"件区的清仓任务，请先处理未结束的任务！");
								}
							}
						}else {
							throw new TfrBusinessException("该部门正在创建该货区的清仓任务，请稍候重试！");
						}
					}
					//按件清仓
					if(isByQty) {
						stTaskDao.addStTaskEntityByQty(stTaskEntity);
					} else {
						//新增清仓任务
						stTaskDao.addStTaskEntity(stTaskEntity);
					}
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TfrBusinessException("该部门正在创建该货区的清仓任务，请稍候重试！");
				}
			} catch (Exception e) {
				// 解锁
				businessLockService.unlock(mutex);
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				
				throw new TfrBusinessException(e.getMessage());
			}
			
			//获取此时部门、货区中所有货件的快照，通过goodsAreaCode获取此时获取的快照，并插入到T_OPT_ST_TASK_LIST中
			List<StockEntity> stockList = stockService.queryStockByGoodsAreaCode(deptCode, goodsAreaCode);
			
			// 插入库存快照
//			List<StTaskListEntity> stTaskListEntityList = new ArrayList<StTaskListEntity>();
			for(StockEntity stock: stockList){
				StTaskListEntity taskListEntity = new StTaskListEntity();
				taskListEntity.setId(UUIDUtils.getUUID());
				taskListEntity.setStTaskId(stTaskEntity.getId());
				taskListEntity.setWaybillNo(stock.getWaybillNO());
				taskListEntity.setSerialNo(stock.getSerialNO());
//				stTaskListEntityList.add(taskListEntity);
				stTaskListDao.addStTaskListEntity(taskListEntity);
			}
//			stTaskListDao.addStTaskListEntityBatch(stTaskListEntityList);
			//获取时间
			int beforeTime = this.getBeforeTime(deptCode);
			if(isByQty) {
				LOGGER.info("PDA开始插入任务明细，任务号：" + stTaskEntity.getTaskNo());
				stTaskListDao.addStTaskListFromStockByQty(stTaskEntity.getId(), deptCode, goodsAreaCode, startQty, endQty, beforeTime);
				LOGGER.info("PDA结束插入任务明细，任务号：" + stTaskEntity.getTaskNo());
			} else {
				LOGGER.info("PDA开始插入任务明细，任务号：" + stTaskEntity.getTaskNo());
				//插入库存快照
				stTaskListDao.addStTaskListFromStock(stTaskEntity,beforeTime);
				LOGGER.info("PDA结束插入任务明细，任务号：" + stTaskEntity.getTaskNo());
			}
			
			//绑定清仓人
			StOperatorEntity operator = new StOperatorEntity();
			operator.setStTaskId(stTaskEntity.getId());
			operator.setEmpCode(operatorCode);
			operator.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
			
			stOperatorDao.addStOperatorEntity(operator);
//				插入PDA清仓任务日志表
			StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
			stTaskPdaEntity.setStTaskNo(stTaskEntity.getTaskNo());
			stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
			stTaskPdaEntity.setPdaNo(pdaNo);
			stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
			stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
			
			stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
			
//			返回清仓任务编号和创建人编号
			pdaStTaskDto.setStTaskNo(stTaskEntity.getTaskNo());
			pdaStTaskDto.setCreatorCode(stTaskEntity.getCreatorCode());
			pdaStTaskDto.setGoodsAreaCode(goodsAreaCode);
			pdaStTaskDto.setGoodsAreaName(goodsAreaService.queryNameByCode(deptCode, goodsAreaCode));
		}
			
		return pdaStTaskDto;
	}*/

	
	/**
	 * @author nly
	 * @date 2014年11月10日 上午8:30:54
	 * @function 新建PDA清仓任务
	 * @param deptCode 部门编码
	 * @param goodsAreaCode 货区编码
	 * @param operatorCode 操作人工号
	 * @param pdaNo PDA编码
	 * @param stTaskNo 任务号
	 * @param isStation 是否驻地货区
	 * @param startQty 开始件数
	 * @param endQty 结束件数
	 * @param receiveMethod 提货方式
	 * @param districtCode 分区code
	 * @return
	 */
	@Override
	public PdaStTaskDto createPdaStTask(String deptCode, String goodsAreaCode, String operatorCode, String pdaNo,String stTaskNo, String isStation, Integer startQty,Integer endQty,String receiveMethod,String districtCode,String isExpressGoods){

		LOGGER.trace("into stockchecking pda service");
		
		boolean isTransferCenter = false;  //是否为外场
		boolean isCommonSaleDepartment = false; //是否为一般性质的营业部，无派送职能
		//boolean isByQty = false;	//是否按件清仓
		PdaStTaskDto pdaStTaskDto = new PdaStTaskDto();
		
		//isByQty = StringUtils.equals("Y", isStation) && startQty != null && endQty != null && startQty > 0 && endQty > 0;
		
		if(StringUtils.isEmpty(deptCode)){
			throw new TfrBusinessException("此部门编号：" + deptCode + " 为空");
		}
//		通过PDA传入的部门编号获取部门实体，判定此部门性质是否为营业部或者外场
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
//		外场情况
		if(StringUtils.equals(FossConstants.YES, org.getTransferCenter())){
			isTransferCenter = true;
			//若是派送货区清仓
			if(StringUtils.equals("Y", isStation)) {
				//查询驻地派送货区
				goodsAreaCode = this.queryStationCode(deptCode);
				//库位号
				List<BaseDataDictDto> positionNoList = stockService.queryStorageListByGoodsAreaFrom(deptCode,goodsAreaCode);
				pdaStTaskDto.setPositionNoList(positionNoList);
			}
//			若为外场，则货区编号不能为空
			if(StringUtils.equals(FossConstants.YES, org.getTransferCenter()) && StringUtils.isNotEmpty(deptCode) && StringUtils.isEmpty(goodsAreaCode)){
				throw new TfrBusinessException("此外场：" + deptCode + " 的库区：" + goodsAreaCode + "编号不匹配");
			}
//		营业部情况
		}else if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())){
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(deptCode);
//			判断此营业部是否驻地部门并且是否有派送职能
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) && StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getDelivery())){
//				如果有派送职能的，需找到对应的外场及对应的库区
				deptCode = saleDepartmentEntity.getTransferCenter();
				
				if(null != deptCode){
					isTransferCenter = true;
				}
				
				List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(deptCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				if(CollectionUtils.isNotEmpty(goodsAreaList)){
					goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
//				根据部门查询货区失败
				}else{
					throw new TfrBusinessException("驻地派送营业部未找到外场对应的库区，外场编号：" + deptCode);
				}
			}else{
				isCommonSaleDepartment = true;
			}
			
//		空运总调情况
		}else if(StringUtils.equals(FossConstants.YES, org.getAirDispatch())){
//		获取空运总调对应的外场编号
			deptCode = outfieldService.queryTransferCenterByAirDispatchCode(org.getCode());
			if(StringUtils.isNotEmpty(deptCode)){
				isTransferCenter = true;
			}
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(deptCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
//			根据部门查询货区失败
			}else{
				throw new TfrBusinessException("空运总调未找到外场对应的库区，外场编号：" + deptCode);
			}
//		否则需找到对应的上级为外场或营业部性质的部门编码
		}else{
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			bizTypes.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
			OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
			if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
				throw new TfrBusinessException("未找到此部门：" + deptCode + " 所对应的的上级 外场 或 营业部");
			}else{
				deptCode = parentOrg.getCode();
				
				if(StringUtils.equals(FossConstants.YES, parentOrg.getTransferCenter())){
					isTransferCenter = true;
					//若是派送货区清仓
					if(StringUtils.equals("Y", isStation)) {
						//查询驻地派送货区
						goodsAreaCode = this.queryStationCode(deptCode);
						//库位号
						List<BaseDataDictDto> positionNoList = stockService.queryStorageListByGoodsAreaFrom(deptCode,goodsAreaCode);
						pdaStTaskDto.setPositionNoList(positionNoList);
					}
				}
			}
		}
		
		if(isCommonSaleDepartment){
			goodsAreaCode = null;
		}
//		若找到本级或者上级为外场的情况，需判断传入的货区是否存在于此外场下，不存在的情况抛出异常
		if(isTransferCenter){
			//校验外场下的货区是否存在
			GoodsAreaEntity godsAreaEntity = goodsAreaService.queryGoodsAreaByCode(deptCode, goodsAreaCode);
			if(null == godsAreaEntity){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_ERROR_DEPT);
			}
		}
		StTaskEntity stTaskEntity = new StTaskEntity();
		stTaskEntity.setGoodsareacode(goodsAreaCode);
		stTaskEntity.setDeptcode(deptCode);
		stTaskEntity.setPdaNo(pdaNo);
		if(null != startQty && startQty > 0 && null != endQty && endQty > 0) {
			stTaskEntity.setStartQty(startQty);
			stTaskEntity.setEndQty(endQty);
		}
		stTaskEntity.setReceiveMethod(receiveMethod);
		stTaskEntity.setDistrictCode(districtCode);
//			如果编号为空，则尝试新建清仓任务
		if(StringUtils.isBlank(stTaskNo)){
			
			StTaskDto dto = this.checkPdaTask(pdaStTaskDto, stTaskEntity);
			if(null != dto) {
				pdaStTaskDto.setStTaskNo(dto.getTaskNo());
				pdaStTaskDto.setCreatorCode(dto.getCreatorCode());
				pdaStTaskDto.setGoodsAreaCode(dto.getGoodsareacode());
				pdaStTaskDto.setGoodsAreaName(dto.getGoodsareaname());
				
				//插入PDA清仓任务日志表
				StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
				stTaskPdaEntity.setStTaskNo(dto.getTaskNo());
				stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
				stTaskPdaEntity.setPdaNo(pdaNo);
				stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
				stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
				
				stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
				
				return pdaStTaskDto;
			}
			
			stTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.QC, deptCode);
			stTaskEntity.setTaskNo(stTaskNo);
			stTaskEntity.setGoodsareaname(goodsAreaService.queryNameByCode(deptCode, goodsAreaCode));
			stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_DOING);
			stTaskEntity.setCreatetime(Calendar.getInstance().getTime());
			stTaskEntity.setIspda(TransferDictionaryConstants.YES);
			stTaskEntity.setCreatorCode(operatorCode);
			stTaskEntity.setCreatorName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
			if(StringUtils.equals("Y", isStation)) {
				if(StringUtils.isNotEmpty(receiveMethod)) {
					stTaskEntity.setReceiveMethodList(this.getReceiveMethodList(receiveMethod));
				}
				if(StringUtils.isNotEmpty(districtCode)) {
					stTaskEntity.setDistrictName(this.queryZoneName(districtCode));
					stTaskEntity.setDistrictCodeList(this.getDistrictCodeList(districtCode));
				}
			}
			
			//在部门编号和货区上加业务锁
			MutexElement mutex = null;
			try {
				//锁定对象
				String lockStr = deptCode + goodsAreaCode;
				mutex = new MutexElement(lockStr, "新增清仓任务", MutexElementType.TFR_STOCKCHECKING_ADDNEW);
				// 锁定
				boolean flag = businessLockService.lock(mutex, 0);
				if (flag) {
					
					StTaskDto dtos = this.checkPdaTask(pdaStTaskDto, stTaskEntity);
					if(null != dtos) {
						pdaStTaskDto.setStTaskNo(dtos.getTaskNo());
						pdaStTaskDto.setCreatorCode(dtos.getCreatorCode());
						pdaStTaskDto.setGoodsAreaCode(dtos.getGoodsareacode());
						pdaStTaskDto.setGoodsAreaName(dtos.getGoodsareaname());
						
						//插入PDA清仓任务日志表
						StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
						stTaskPdaEntity.setStTaskNo(dtos.getTaskNo());
						stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
						stTaskPdaEntity.setPdaNo(pdaNo);
						stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
						stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
						
						stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
						
						return pdaStTaskDto;
					}
					
					//新增清仓任务
					stTaskDao.addStTaskEntity(stTaskEntity);
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TfrBusinessException("该部门正在创建该货区的清仓任务，请稍候重试！");
				}
			} catch (Exception e) {
				// 解锁
				businessLockService.unlock(mutex);
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				
				throw new TfrBusinessException(e.getMessage());
			}
			
			//获取时间
			int beforeTime = this.getBeforeTime(deptCode);
				
			LOGGER.info("PDA开始插入任务明细，任务号：" + stTaskEntity.getTaskNo());
			//插入库存快照
			if(StringUtil.isBlank(isExpressGoods)||StringUtil.equals(isExpressGoods, "ALL")){//如果为普通清仓员
				stTaskListDao.addStTaskListFromStock(stTaskEntity,beforeTime);
			}else if(StringUtil.equals(isExpressGoods, "Y")){//如果为快递清仓员
				stTaskListDao.addStTaskListFromStockForExpress(stTaskEntity, beforeTime);
			}else if(StringUtil.equals(isExpressGoods, "N")){
				stTaskListDao.addStTaskListFromStockForNoExpress(stTaskEntity, beforeTime);
			}
			LOGGER.info("PDA结束插入任务明细，任务号：" + stTaskEntity.getTaskNo());
			//绑定清仓人
			StOperatorEntity operator = new StOperatorEntity();
			operator.setStTaskId(stTaskEntity.getId());
			operator.setEmpCode(operatorCode);
			operator.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
			
			stOperatorDao.addStOperatorEntity(operator);
//				插入PDA清仓任务日志表
			StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
			stTaskPdaEntity.setStTaskNo(stTaskEntity.getTaskNo());
			stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_DOING);
			stTaskPdaEntity.setPdaNo(pdaNo);
			stTaskPdaEntity.setScanTime(Calendar.getInstance().getTime());
			stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
			
			stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
			
//			返回清仓任务编号和创建人编号
			pdaStTaskDto.setStTaskNo(stTaskEntity.getTaskNo());
			pdaStTaskDto.setCreatorCode(stTaskEntity.getCreatorCode());
			pdaStTaskDto.setGoodsAreaCode(goodsAreaCode);
			pdaStTaskDto.setGoodsAreaName(goodsAreaService.queryNameByCode(deptCode, goodsAreaCode));
		}
			
		return pdaStTaskDto;
	
	}
	/** 
	 * <pre>
	 * 刷新清仓任务
     *
	 * 通过清仓任务编号获取对应的清仓任务快照
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:10
	 * @param stTaskNo
	 * @return List<PdaStTaskDto>
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#queryPdaStTaskDtoList(java.lang.String)
	 */
	/*@Override
	public List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo) {
		LOGGER.info("刷新清仓任务:通过清仓任务编号获取对应的清仓任务快照: " + stTaskNo);
		
		List<PdaStTaskDto> pdaWaybillStTastDtoList = new ArrayList<PdaStTaskDto>();
//		按照清仓任务编号，查询清仓任务货件明细列表
		List<PdaStTaskDto> pdaStTaskDtoList = stTaskListDao.queryPdaStTaskDtoList(stTaskNo);
		try{
			//按照运单号分组  //确认labelTrash 是否为空
			if(CollectionUtils.isNotEmpty(pdaStTaskDtoList)){
				String waybillNo = "";
				Map<String, PdaStTaskDto> waybillMap = new HashMap<String, PdaStTaskDto>();
				for(PdaStTaskDto pdaStTaskDto : pdaStTaskDtoList){
					if(!StringUtils.equals(waybillNo, pdaStTaskDto.getWaybillNo())){
						waybillNo = pdaStTaskDto.getWaybillNo();
						PDAGoodsSerialNoDto pdaGoodsSerialNoDto = new PDAGoodsSerialNoDto();
						pdaGoodsSerialNoDto.setSerialNo(pdaStTaskDto.getSerialNo());
						int isChanged = stTaskPdaDao.queryIsChanged(pdaStTaskDto.getWaybillNo(), pdaStTaskDto.getSerialNo(), pdaStTaskDto.getDeptCode());
						pdaGoodsSerialNoDto.setIsToDoList(isChanged > 0 ? "Y" : "N");
						pdaStTaskDto.getSerialNos().add(pdaGoodsSerialNoDto);
						waybillMap.put(waybillNo, pdaStTaskDto);
					}else{
						PDAGoodsSerialNoDto pdaGoodsSerialNoDto = new PDAGoodsSerialNoDto();
						pdaGoodsSerialNoDto.setSerialNo(pdaStTaskDto.getSerialNo());
						int isChanged = stTaskPdaDao.queryIsChanged(pdaStTaskDto.getWaybillNo(), pdaStTaskDto.getSerialNo(), pdaStTaskDto.getDeptCode());
						pdaGoodsSerialNoDto.setIsToDoList(isChanged > 0 ? "Y" : "N");
						waybillMap.get(waybillNo).getSerialNos().add(pdaGoodsSerialNoDto);
					}
				}
				
				for(Entry<String, PdaStTaskDto> dto: waybillMap.entrySet()){
					PdaStTaskDto stTaskDto = dto.getValue();
					stTaskDto.setReceiveOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(stTaskDto.getReceiveOrgCode())); //获取收货部门名称
					stTaskDto.setTargetOrgName(stTaskDto.getTargetOrgCode());   //获取到达部门名称
					stTaskDto.setProductCode(productService.getProductByCache(stTaskDto.getProductCode(), null).getName());
					
//					获取此运单号已完成扫描的件数
					int scanDoneCount = stResultListDao.queryScanDoneCountByWaybillNo(stTaskNo, dto.getKey(), TransferConstants.SCAN_DONE);
					stTaskDto.setFinishedQty(scanDoneCount);

					pdaWaybillStTastDtoList.add(stTaskDto);
				}
			}
		} catch(Exception e){
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE, ExceptionUtils.getFullStackTrace(e));
		}
		
		//刷新清仓任务:返回PDA快照列表 日志
		LOGGER.info("刷新清仓任务:返回PDA快照列表，任务编号: " + stTaskNo);
		for(PdaStTaskDto stTaskDto : pdaWaybillStTastDtoList){
			StringBuffer waybillNoSerialNos = new StringBuffer();
			waybillNoSerialNos.append("TaskNo: ");
			waybillNoSerialNos.append(stTaskNo);
			waybillNoSerialNos.append("---WaybillNo: ");
			waybillNoSerialNos.append(stTaskDto.getWaybillNo());
			waybillNoSerialNos.append("---SerialNo: ");
			for(PDAGoodsSerialNoDto pdaGoodsSerialNoDto : stTaskDto.getSerialNos()){
				waybillNoSerialNos.append(pdaGoodsSerialNoDto.getSerialNo());
				waybillNoSerialNos.append(",");
			}
			LOGGER.info(waybillNoSerialNos.toString());
		}
		
		return pdaWaybillStTastDtoList;
	}*/
	
	
	/**
	 * @desc 优化上面注释掉的刷新pda清仓明细
	 * @param stTaskNo
	 * @return
	 * @date 2016年9月12日 上午11:32:36
	 */
	@Override
	public List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo) {
		List<PdaStTaskDto> pdaStTaskDtoList = stTaskListDao.queryPdaStSnap(stTaskNo);
		
		if(CollectionUtils.isEmpty(pdaStTaskDtoList)){
			return new ArrayList<PdaStTaskDto>();
		}
		
		String waybillNo = "";
		Map<String, PdaStTaskDto> waybillMap = new HashMap<String, PdaStTaskDto>();
		for(PdaStTaskDto pdaStTaskDto : pdaStTaskDtoList){
			if(!StringUtils.equals(waybillNo, pdaStTaskDto.getWaybillNo())){
				waybillNo = pdaStTaskDto.getWaybillNo();
				PDAGoodsSerialNoDto pdaGoodsSerialNoDto = new PDAGoodsSerialNoDto();
				pdaGoodsSerialNoDto.setSerialNo(pdaStTaskDto.getSerialNo());
				int isChanged = stTaskPdaDao.queryIsChanged(pdaStTaskDto.getWaybillNo(), pdaStTaskDto.getSerialNo(), pdaStTaskDto.getDeptCode());
				pdaGoodsSerialNoDto.setIsToDoList(isChanged > 0 ? FossConstants.YES : FossConstants.NO);
				pdaStTaskDto.getSerialNos().add(pdaGoodsSerialNoDto);
				waybillMap.put(waybillNo, pdaStTaskDto);
			}else{
				PDAGoodsSerialNoDto pdaGoodsSerialNoDto = new PDAGoodsSerialNoDto();
				pdaGoodsSerialNoDto.setSerialNo(pdaStTaskDto.getSerialNo());
				int isChanged = stTaskPdaDao.queryIsChanged(pdaStTaskDto.getWaybillNo(), pdaStTaskDto.getSerialNo(), pdaStTaskDto.getDeptCode());
				pdaGoodsSerialNoDto.setIsToDoList(isChanged > 0 ? FossConstants.YES : FossConstants.NO);
				waybillMap.get(waybillNo).getSerialNos().add(pdaGoodsSerialNoDto);
			}
		}
		return attachPdaStSnop(stTaskNo,waybillMap);
	}
	
	
	private List<PdaStTaskDto> attachPdaStSnop(String stTaskNo, Map<String, PdaStTaskDto> waybillMap) {
		List<PdaStTaskDto> reuslt = new ArrayList<PdaStTaskDto>();
		for (Entry<String, PdaStTaskDto> dto : waybillMap.entrySet()) {
			PdaStTaskDto stTaskDto = dto.getValue();

			String waybillNo = stTaskDto.getWaybillNo();
			PdaStTaskDto waybillInfo = stTaskListDao.queryWaybillInfo(waybillNo);
			if (waybillInfo == null) {
				continue;
			}
			
			stTaskDto.setProductCode(waybillInfo.getProductCode());
			stTaskDto.setGoodsName(waybillInfo.getGoodsName());
			stTaskDto.setPreciousGoods(waybillInfo.getPreciousGoods());
			stTaskDto.setPackageType(waybillInfo.getPackageType());
			stTaskDto.setReceiveOrgCode(waybillInfo.getReceiveOrgCode());
			stTaskDto.setTargetOrgCode(waybillInfo.getTargetOrgCode());
			stTaskDto.setWayBillQty(waybillInfo.getWayBillQty());
			stTaskDto.setCreatorCode(waybillInfo.getCreatorCode());
			stTaskDto.setGoodsWeight(waybillInfo.getGoodsWeight());
			stTaskDto.setGoodsVolume(waybillInfo.getGoodsVolume());
			
			String receiveCustomerDistName = administrativeRegionsService.queryRegionName(waybillInfo.getReceiveCustomerDistCode());
			stTaskDto.setReceiveCustomerDistName(receiveCustomerDistName);
			
			String stationNumber = stTaskListDao.queryOrgStationNum(waybillInfo.getCustomerPickupOrgCode());
			if(stationNumber == null){
				stationNumber = stTaskListDao.queryOuterBranchStationNum(waybillInfo.getCustomerPickupOrgCode());
			}
			stTaskDto.setStationNumber(stationNumber);
			
			String contraband = stTaskListDao.queryContraband(waybillNo) > 0 ? FossConstants.YES : FossConstants.NO;
			stTaskDto.setContraband(contraband);
			
			String needWoodenPackage = stTaskListDao.queryNeedPack(waybillNo) > 0 ? FossConstants.YES
					: FossConstants.NO;
			stTaskDto.setNeedWoodenPackage(needWoodenPackage);
			
			stTaskDto.setReceiveOrgName(
					orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(stTaskDto.getReceiveOrgCode()));
			stTaskDto.setTargetOrgName(stTaskDto.getTargetOrgCode());//上面之前的方法也是把code放到name里面
			stTaskDto.setProductCode(productService4Dubbo.getProductByCache(stTaskDto.getProductCode(), null).getName());

			// 获取此运单号已完成扫描的件数
			int scanDoneCount = stResultListDao.queryScanDoneCountByWaybillNo(stTaskNo, dto.getKey(),
					TransferConstants.SCAN_DONE);
			stTaskDto.setFinishedQty(scanDoneCount);

			reuslt.add(stTaskDto);
		}
		return reuslt;
	}
	
	/** 
	 * <pre>
	 * 刷新包清仓
     *
	 * 通过清仓任务编号获取对应的清仓任务包清仓快照
	 * </pre>
	 * @author foss-zhuyunrong
	 * @date 2015-03-25 上午10:49:10
	 * @param stTaskNo
	 * @return List<PdaStTaskDto>
	 */
	@Override
	public List<PdaPackageStTastDto> queryPdaPackageStTastDtoList(String stTaskNo) {
		LOGGER.info("刷新清仓任务:通过清仓任务编号获取对应的清仓任务包清仓快照: " + stTaskNo);
		List<PdaPackageStTastDto> pdaPackageNoStTastDtoList  = stTaskListDao.queryPdaPackageStTastDtoList(stTaskNo);
		try{
			if(CollectionUtils.isNotEmpty(pdaPackageNoStTastDtoList)){
				for(PdaPackageStTastDto dto : pdaPackageNoStTastDtoList){
					boolean  existScan  = checkPackageExistScan(dto.getStTaskId(), dto.getPackageNo());
					dto.setScanStatus(existScan ? "Y" : "N");
					dto.setType("快");
				}
			}
		} catch(Exception e){
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE, ExceptionUtils.getFullStackTrace(e));
		}
		//刷新包清仓:返回PDA快照列表 日志
				LOGGER.info("刷新包清仓:返回PDA快照列表，任务编号: " + stTaskNo);
				for(PdaPackageStTastDto pStTaskDto : pdaPackageNoStTastDtoList){
					StringBuffer waybillNoSerialNos = new StringBuffer();
					waybillNoSerialNos.append("TaskNo: ");
					waybillNoSerialNos.append(stTaskNo);
					waybillNoSerialNos.append("---packageNo: ");
					waybillNoSerialNos.append(pStTaskDto.getPackageNo());
					LOGGER.info(waybillNoSerialNos.toString());
				}
		return pdaPackageNoStTastDtoList;
	}

	/** 
	 * <pre>
	 * 清仓扫描
	 * 1、往PDA扫描结果表中插入一条数据，包含：正常、多货、撤销
	 * 2、插入一条数据至pda扫描结果表中
	 * 3、按pda扫描时间排序，查询此件已扫描的历史记录
	 *  2.1、若存在，则需通过清仓任务编号、运单号、序列号覆盖此最后一个数据的信息
	 *   2.1.1、若为撤销情况，则删除对应的清仓任务结果列表中的数据
	 *   2.1.2、若不为撤销状态，则更新对应的清仓任务结果列表中的数据
	 *  2.2、若不存在，则插入一条数据至清仓任务结果列表中
	 * 3、任何异常情况，返回false,成功返回true
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:15
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#scanStTaskDetail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean scanStTaskDetail(String stTaskNo, String waybillNo, String serialNo, String scanResultStatus, Date scanTime,	String operatorCode, String pdaNo,String positionNo) {
		LOGGER.trace("into stockchecking pda service");
		LOGGER.info("PDA传输数据开始：任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo + "扫描状态：" + scanResultStatus + "操作人code:" + operatorCode+ "PDA编码：" + pdaNo+ "库位号：" + positionNo);
		boolean result = false;
		//			如果此清仓任务不存在，不作处理
		StTaskEntity stTaskEntity = stTaskDao.queryStTaskByNo(stTaskNo);
		if(StringUtils.isNotEmpty(stTaskEntity.getId())){
			//			插入一条扫描数据到pda扫描结果表中
			StResultListPdaEntity stResultListPdaEntity = new StResultListPdaEntity();
			stResultListPdaEntity.setStTaskNo(stTaskNo);
			stResultListPdaEntity.setWaybillNo(waybillNo);
			stResultListPdaEntity.setSerialNo(serialNo);
			stResultListPdaEntity.setScanStatus(scanResultStatus);
			stResultListPdaEntity.setScanTime(scanTime);
			stResultListPdaEntity.setPdaNo(pdaNo);
			stResultListPdaEntity.setCreateTime(Calendar.getInstance().getTime());
			stResultListPdaEntity.setEmpCode(operatorCode);
			stResultListPdaEntity.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());


			stResultListPdaDao.addStResultListPdaEntity(stResultListPdaEntity);
			LOGGER.info("新增PDA清仓扫描明细成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);

			StResultListEntity stResultListEntity = new StResultListEntity();
			stResultListEntity.setStTaskId(stTaskEntity.getId());
			stResultListEntity.setWaybillNo(waybillNo);
			stResultListEntity.setSerialNo(serialNo);
			stResultListEntity.setScanStatus(TransferConstants.SCAN_DONE);
			stResultListEntity.setGoodsStatus(scanResultStatus);
			stResultListEntity.setPdaUploadTime(scanTime);
			stResultListEntity.setCreateTime(Calendar.getInstance().getTime());
			stResultListEntity.setPdaNo(pdaNo);
			//库位号
			stResultListEntity.setPositionNo(positionNo);

			// 此处将当前货件与此清仓任务快照作对比，判断实际的多货、正常情况
			//				stResultListEntity.setGoodsStatus(checkRealGoodsStatus(stTaskEntity.getId(), waybillNo, serialNo));

			//			查看是否此件之前已扫描过
			boolean existScan = checkGoodsExistScan(stTaskEntity.getId(), waybillNo, serialNo);
			//				若扫描过
			if(existScan){
				//					按扫描时间倒序排序取第一个数据，此数据为最后一次扫描的数据，不能以当前扫描状态为准，PDA上传为异步上传
				StResultListPdaEntity lastPdaScanEntity = stResultListPdaDao.queryLastScanEntity(stResultListPdaEntity);
				//					通过清仓任务ID、运单号、流水号，获取此扫描明细结果ID
				StResultListEntity oldStResultListEntity = stResultListDao.queryStResultEntity(stTaskEntity.getId(), waybillNo, serialNo);
				stResultListEntity.setId(oldStResultListEntity.getId());
				stResultListDaoDeleteUpdate(stResultListEntity,
						lastPdaScanEntity);
			}else{
				//				不存在此件的将数据插入到清仓任务结果表中
				stResultListEntity.setId(UUIDUtils.getUUID());
				stResultListDao.addStResultListEntity(stResultListEntity);
				LOGGER.info("新增清仓扫描明细成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
			}
			//查询该任务下清仓人是否包括当前操作人				
			List<StOperatorEntity> operatorList = stOperatorDao.queryOperatorsByStTaskId(stTaskEntity.getId());
			boolean isExistFlag = false;
			if(CollectionUtils.isNotEmpty(operatorList)){
				for(StOperatorEntity operator : operatorList){
					if(StringUtils.equals(operatorCode, operator.getEmpCode())){
						isExistFlag = true;
						break;
					}
				}
			}
			//不包括当前操作人
			if(!isExistFlag){
				//绑定清仓人
				StOperatorEntity operator = new StOperatorEntity();
				operator.setStTaskId(stTaskEntity.getId());
				operator.setEmpCode(operatorCode);
				operator.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
				try{
					stOperatorDao.addStOperatorEntity(operator);
					LOGGER.info("新增清仓人成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
				}catch(Exception e){
					//empCode + taskId创建唯一索引，如果重复插入就捕捉异常且不抛出，不影响程序的正常运行
					LOGGER.info("任务为："+stTaskEntity.getId()+"清仓人工号为："+operatorCode+"的记录已经存在！");
				}
			}

			//库位号不为空且正常扫描时更新库位号
			try{
				if(StringUtils.isNotEmpty(positionNo) && StringUtils.equals(TransferPDADictConstants.ST_TASK_SCAN_STATUS_OK, scanResultStatus)) {
					List<InOutStockEntity> inStockList = new ArrayList<InOutStockEntity>();
					InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);	
					stockEntity.setSerialNO(serialNo);
					stockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
					inStockList.add(stockEntity);

					//操作人姓名
					String operatorName = employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName();
					stockService.updateStockStockPosition(inStockList, positionNo, stTaskEntity.getDeptcode(), operatorCode, operatorName);
					LOGGER.info("更新库位成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
				}
			} catch(Exception e) {
				e.printStackTrace();
				LOGGER.info("PDA扫描时，更新库位失败！任务号" + stTaskNo+",运单号：" + waybillNo + ",流水号：" + serialNo +",库位号：" + positionNo);
			}
		}

		result = true;
		
		LOGGER.info("PDA传输数据结束：任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo + "扫描状态：" + scanResultStatus + "操作人code:" + operatorCode+ "PDA编码：" + pdaNo+ "库位号：" + positionNo);
		return result;
	}
	
	/** 
	 * <pre>
	 * 包清仓扫描
	 * 1、往PDA扫描结果表中插入n条数据，包含：正常、多货、撤销
	 * 2、插入n条数据至pda扫描结果表中
	 * 3、按pda扫描时间排序，查询此件已扫描的历史记录
	 *  2.1、若存在，则需通过清仓任务编号、运单号、序列号覆盖此最后一个数据的信息
	 *   2.1.1、若为撤销情况，则删除对应的清仓任务结果列表中的数据
	 *   2.1.2、若不为撤销状态，则更新对应的清仓任务结果列表中的数据
	 *  2.2、若不存在，则插入一条数据至清仓任务结果列表中
	 * 3、任何异常情况，返回false,成功返回true
	 * </pre>
	 * @author foss-zhuyunrong
	 * @date 2015-04-08 下午05:22:15
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#scanStTaskPackageDetail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean scanStTaskPackageDetail(String stTaskNo, String packageNo, String scanResultStatus, Date scanTime,	String operatorCode, String pdaNo,String positionNo) {
		LOGGER.trace("into stockchecking pda service");
		LOGGER.info("PDA传输数据开始：任务号：" + stTaskNo + "包号" + packageNo + "扫描状态：" + scanResultStatus + "操作人code:" + operatorCode+ "PDA编码：" + pdaNo+ "库位号：" + positionNo);
		boolean result = false;
		try{
//			如果此清仓任务不存在，不作处理
			StTaskEntity stTaskEntity = stTaskDao.queryStTaskByNo(stTaskNo);
			if(StringUtils.isNotEmpty(stTaskEntity.getId())){
				if(StringUtils.isNotEmpty(packageNo)) {
					List<PcakageWayBillDto> pcakageWayBillDtos = this.queryPackageDetail(packageNo);
					if(pcakageWayBillDtos.size() == 0) {
						LOGGER.error("包内运单明细为空");
						throw new TfrBusinessException("包内运单明细为空");
					}
					for(PcakageWayBillDto pcakageWayBillDto : pcakageWayBillDtos ) {
						String waybillNo = pcakageWayBillDto.getWayBillNo();
						String serialNo = pcakageWayBillDto.getSerialNo();
//						插入一条扫描数据到pda扫描结果表中
						StResultListPdaEntity stResultListPdaEntity = new StResultListPdaEntity();
						stResultListPdaEntity.setStTaskNo(stTaskNo);
						stResultListPdaEntity.setPackageNo(packageNo);
						stResultListPdaEntity.setWaybillNo(waybillNo);
						stResultListPdaEntity.setSerialNo(serialNo);
						stResultListPdaEntity.setScanStatus(scanResultStatus);
						stResultListPdaEntity.setScanTime(scanTime);
						stResultListPdaEntity.setPdaNo(pdaNo);
						stResultListPdaEntity.setCreateTime(Calendar.getInstance().getTime());
						stResultListPdaEntity.setEmpCode(operatorCode);
						stResultListPdaEntity.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
						
						
						stResultListPdaDao.addStResultListPdaEntity(stResultListPdaEntity);
						LOGGER.info("新增PDA清仓扫描明细成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
						
						StResultListEntity stResultListEntity = new StResultListEntity();
						stResultListEntity.setStTaskId(stTaskEntity.getId());
						stResultListEntity.setPackageNo(packageNo);
						stResultListEntity.setWaybillNo(waybillNo);
						stResultListEntity.setSerialNo(serialNo);
						stResultListEntity.setScanStatus(TransferConstants.SCAN_DONE);
						stResultListEntity.setGoodsStatus(scanResultStatus);
						stResultListEntity.setPdaUploadTime(scanTime);
						stResultListEntity.setCreateTime(Calendar.getInstance().getTime());
						stResultListEntity.setPdaNo(pdaNo);
						//库位号
						stResultListEntity.setPositionNo(positionNo);
						
						// 此处将当前货件与此清仓任务快照作对比，判断实际的多货、正常情况
//						stResultListEntity.setGoodsStatus(checkRealGoodsStatus(stTaskEntity.getId(), waybillNo, serialNo));
						
//					查看是否此件之前已扫描过
						boolean existScan = checkGoodsExistScan(stTaskEntity.getId(), waybillNo, serialNo);
//						若扫描过
						if(existScan){
//							按扫描时间倒序排序取第一个数据，此数据为最后一次扫描的数据，不能以当前扫描状态为准，PDA上传为异步上传
							StResultListPdaEntity lastPdaScanEntity = stResultListPdaDao.queryLastScanEntity(stResultListPdaEntity);
//							通过清仓任务ID、运单号、流水号，获取此扫描明细结果ID
							StResultListEntity oldStResultListEntity = stResultListDao.queryStResultEntity(stTaskEntity.getId(), waybillNo, serialNo);
							stResultListEntity.setId(oldStResultListEntity.getId());
//							如果最后一次扫描数据为撤消操作
							//sonar-352203
							stResultListDaoDeleteUpdate(stResultListEntity,
									lastPdaScanEntity);
						}else{
//						不存在此件的将数据插入到清仓任务结果表中
							stResultListEntity.setId(UUIDUtils.getUUID());
							stResultListDao.addStResultListEntity(stResultListEntity);
							LOGGER.info("新增清仓扫描明细成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
						}
						//查询该任务下清仓人是否包括当前操作人				
						List<StOperatorEntity> operatorList = stOperatorDao.queryOperatorsByStTaskId(stTaskEntity.getId());
						boolean isExistFlag = false;
						if(CollectionUtils.isNotEmpty(operatorList)){
							for(StOperatorEntity operator : operatorList){
								if(StringUtils.equals(operatorCode, operator.getEmpCode())){
									isExistFlag = true;
									break;
								}
							}
						}
						//不包括当前操作人
						if(!isExistFlag){
							//绑定清仓人
							StOperatorEntity operator = new StOperatorEntity();
							operator.setStTaskId(stTaskEntity.getId());
							operator.setEmpCode(operatorCode);
							operator.setEmpName(employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName());
							try{
								stOperatorDao.addStOperatorEntity(operator);
								LOGGER.info("新增清仓人成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
							}catch(Exception e){
								//empCode + taskId创建唯一索引，如果重复插入就捕捉异常且不抛出，不影响程序的正常运行
								LOGGER.info("任务为："+stTaskEntity.getId()+"清仓人工号为："+operatorCode+"的记录已经存在！");
							}
						}
						
						//库位号不为空且正常扫描时更新库位号
						try{
							if(StringUtils.isNotEmpty(positionNo) && StringUtils.equals(TransferPDADictConstants.ST_TASK_SCAN_STATUS_OK, scanResultStatus)) {
								List<InOutStockEntity> inStockList = new ArrayList<InOutStockEntity>();
								InOutStockEntity stockEntity = new InOutStockEntity();
								stockEntity.setWaybillNO(waybillNo);	
								stockEntity.setSerialNO(serialNo);
								stockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
								inStockList.add(stockEntity);
								
								//操作人姓名
								String operatorName = employeeService.queryEmployeeByEmpCode(operatorCode).getEmpName();
								stockService.updateStockStockPosition(inStockList, positionNo, stTaskEntity.getDeptcode(), operatorCode, operatorName);
								LOGGER.info("更新库位成功！任务号：" + stTaskNo + "运单号" +waybillNo +"流水号：" + serialNo+",库位号：" + positionNo);
							}
						} catch(Exception e) {
							e.printStackTrace();
							LOGGER.info("PDA扫描时，更新库位失败！任务号" + stTaskNo+",运单号：" + waybillNo + ",流水号：" + serialNo +",库位号：" + positionNo);
						}
					}
				}
			}

			result = true;
		}catch(Exception e){
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE, ExceptionUtils.getFullStackTrace(e));
		}
		LOGGER.info("PDA传输数据结束：任务号：" + stTaskNo + "包号" + packageNo + "扫描状态：" + scanResultStatus + "操作人code:" + operatorCode+ "PDA编码：" + pdaNo+ "库位号：" + positionNo);
		return result;
	}
	/**
	 * @param stResultListEntity
	 * @param lastPdaScanEntity
	 */
	private void stResultListDaoDeleteUpdate(
			StResultListEntity stResultListEntity,
			StResultListPdaEntity lastPdaScanEntity) {
		if(StringUtils.equals(TransferPDADictConstants.ST_TASK_SCAN_STATUS_CANCEL, lastPdaScanEntity.getScanStatus())){
//								删除对应的清仓结果列表中的数据
			stResultListDao.deleteStResultListEntity(stResultListEntity);
		}else{
//								更新对应的清仓结果列表中的数据
			stResultListDao.updateByPrimaryKeySelective(stResultListEntity);
		}
	}

	/**
	 * 通过清仓任务ID、运单号、流水号，与清仓库存快照比较，判断实际是否是多货和正常,并返回扫描状态
	 * @param stTaskId
	 * @param waybillNo
	 * @param serialNo
	 * @return: 扫描状态
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-18 上午11:04:18
	 */
	@SuppressWarnings("unused")
	private String checkRealGoodsStatus(String stTaskId, String waybillNo,	String serialNo) {
		Long count = stTaskListDao.queryEntityCountByGoodsInfo(stTaskId, waybillNo, serialNo);
		if(count == 0){
			return TransferConstants.GOODS_STATUS_SURPLUS;
		}else{
			return TransferConstants.GOODS_STATUS_OK;
		}
	}

	/**
	 * 查看清仓任务中的某一件货是否已经被扫描过
	 *
	 * @param stTaskNo	清仓任务
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @return 若此件已扫描过， 返回 true | 否则返回 false
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午9:10:33
	 */
	private boolean checkGoodsExistScan(String stTaskId, String waybillNo,	String serialNo) {
		long historyScanCount = stResultListDao.queryStResultListCount(stTaskId, waybillNo, serialNo);
		
		if(historyScanCount > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查看清仓任务中的某一包是否已经被扫描过
	 *
	 * @param stTaskNo	清仓任务
	 * @param packageNo 包号
	 * @return 若此包中所以件已扫描过， 返回 true | 否则返回 false
	 *
	 * @author zhuyunrong
	 * @date 2015-04-02 上午8:40:43
	 */
	private boolean checkPackageExistScan(String stTaskId, String packageNo) {
		List<PcakageWayBillDto> pcakageWayBillDtos = this.queryPackageDetail(packageNo);
		if(pcakageWayBillDtos.size() == 0) {
			LOGGER.error("包内运单明细为空");
			throw new TfrBusinessException("包内运单明细为空");
		}
		for(PcakageWayBillDto pcakageWayBillDto : pcakageWayBillDtos ) {
			String waybillNo = pcakageWayBillDto.getWayBillNo();
			String serialNo = pcakageWayBillDto.getSerialNo();
			boolean exist = this.checkGoodsExistScan(stTaskId, waybillNo, serialNo);
			if(!exist) {
				return false;
			}
		}
		return true;
	}

	/** 
	 * <pre>
	 * 提交清仓任务
	 * 1、判断PDA清仓任务表中是否各pda设备都已完成任务
	 *  1.1、若完成清仓任务，更新清仓任务状态至TransferConstants.STOCK_CHECKING_DONE，并返回true
	 *  1.2、若存在未完成的pda清仓任务，则返回false
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:27
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#submitPdaStTask(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public boolean submitPdaStTask(String stTaskNo, String pdaNo, Date scanTime) {
		LOGGER.trace("into stockchecking pda service");
		
		boolean result = false;
//		如果此清仓任务不存在，或此清仓任务已完成的及已撤销的，不作处理
		StTaskEntity stTask = stTaskDao.queryStTaskByNo(stTaskNo);
		if(StringUtils.isNotEmpty(stTask.getId()) ||
		   StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, stTask.getTaskStatus())){
//		查看是否此清仓任务对应的各PDA分支都已完成操作
			boolean branchFinished = checkStTaskBranchFinished(stTaskNo);
//				若各分支都已完成
			if(branchFinished){
//					更新清仓任务状态为结束状态
				StTaskEntity stTaskEntity = new StTaskEntity();
				stTaskEntity.setId(stTask.getId());
				stTaskEntity.setFinishtime(Calendar.getInstance().getTime());
				stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_DONE);
				
				stTaskDao.updateByPrimaryKeySelective(stTaskEntity);
				
				result = true;
			}else{
				throw new StTaskSubmitException(stTaskDao.getNoSubmitPackageNumByStTaskNo(stTaskNo), stTaskDao.getNoSubmitWaybillNumByStTaskNo(stTaskNo), stTaskDao.getNoSubmitSerialNumByStTaskNo(stTaskNo));
			}
		}
		
		return result;
	}

	/**
	 * 查看是否此清仓任务对应的各PDA分支都已完成操作
	 *
	 * @param stTaskNo
	 * @return 若各分支都已完成，返回true | 否则返回false
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午2:43:25
	 */
	private boolean checkStTaskBranchFinished(String stTaskNo) {
//		查询所有的分支是否都已处于完成状态，并且各分支任务完成任务之后都无撤销动作
		boolean result = true;
		String tempPdaNo = "";
		List<StTaskPdaEntity> lastScanTaskPdaList = new ArrayList<StTaskPdaEntity>();
		
		List<StTaskPdaEntity> taskPdaList = stTaskPdaDao.queryBranchPdaTask(stTaskNo);
		
		for(StTaskPdaEntity stTaskPdaBranch: taskPdaList){
			if(!StringUtils.equals(tempPdaNo, stTaskPdaBranch.getPdaNo())){
				tempPdaNo = stTaskPdaBranch.getPdaNo();
				
				lastScanTaskPdaList.add(stTaskPdaBranch);
			}
		}
		
		for(StTaskPdaEntity pdaEntity: lastScanTaskPdaList){
			if(StringUtils.equals(TransferPDADictConstants.ST_TASK_STATUS_DOING, pdaEntity.getStatus()) ||
			   StringUtils.equals(TransferPDADictConstants.ST_TASK_STATUS_CANCEL, pdaEntity.getStatus())){
				result = false;
				
				break;
			}
		}
		
		return result;
	}
	
	/** 
	 * <pre>
	 * 强制提交任务，不判断PDA清仓任务表中是否各pda设备都已完成任务
	 * 1、更新清仓任务状态及完成时间TransferConstants.STOCK_CHECKING_DONE
	 * 2、若此时有N个pda分支，则向PDA清仓任务表中插入n条新的数据，各数据状态均为TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH完成状态
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:35
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#enforceSubmitPdaStTask(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public boolean enforceSubmitPdaStTask(String stTaskNo, String pdaNo, Date scanTime) {
		LOGGER.trace("into stockchecking pda service");
		
		boolean result = false;
		try{
//			如果此清仓任务不存在，或此清仓任务已完成的及已撤销的，不作处理
			StTaskEntity stTask = stTaskDao.queryStTaskByNo(stTaskNo);
			if(StringUtils.isNotEmpty(stTask.getId()) || 
			   StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, stTask.getTaskStatus())){
				stTask.setFinishtime(new Date());
				stTask.setTaskStatus(TransferConstants.STOCK_CHECKING_DONE);
				
				stTaskDao.updateStTaskEntity(stTask);
				
				String tempPdaNo = "";
//				List<StTaskPdaEntity> lastScanTaskPdaList = new ArrayList<StTaskPdaEntity>();
				
				List<StTaskPdaEntity> taskPdaList = stTaskPdaDao.queryBranchPdaTask(stTaskNo);
//				若此时有N个pda分支，则向PDA清仓任务表中插入n条新的数据，各数据状态均为TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH完成状态	
				for(StTaskPdaEntity stTaskPdaBranch: taskPdaList){
					if(!StringUtils.equals(tempPdaNo, stTaskPdaBranch.getPdaNo())){
						tempPdaNo = stTaskPdaBranch.getPdaNo();
						
						StTaskPdaEntity stTaskPda = new StTaskPdaEntity();
						stTaskPda.setId(UUIDUtils.getUUID());
						stTaskPda.setStTaskNo(stTaskNo);
						stTaskPda.setStatus(TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH);
						stTaskPda.setPdaNo(stTaskPdaBranch.getPdaNo());
						stTaskPda.setScanTime(scanTime);
						stTaskPda.setCreateTime(Calendar.getInstance().getTime());
						//lastScanTaskPdaList.add(stTaskPdaBranch);
						stTaskPdaDao.addStTaskPdaEntity(stTaskPda);
					}
				}
				
				//stTaskPdaDao.addStTaskPdaEntityBatch(lastScanTaskPdaList);
			}
		}catch(Exception e){
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE, ExceptionUtils.getFullStackTrace(e));
		}
		
		return result;
	}

	/** 
	 * <pre>
	 * 撤销清仓任务
	 * 1、判断清仓任务下的所有的清仓任务明细信息是否都已撤销
	 *  1.1、若存在未撤销的明细数据，则返回false
	 *  1.2、若都已撤销完毕，则更新此清仓任务状态至TransferConstants.STOCK_CHECKING_CANCEL，操作成功后返回true
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:42
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#cancelPdaStTask(java.lang.String, java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public boolean cancelPdaStTask(String stTaskNo, String pdaNo, Date scanTime, String operatorCode) {
		LOGGER.trace("into stockchecking pda service");
		
		boolean result = false;
		try{
			StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
			
			stTaskPdaEntity.setStTaskNo(stTaskNo);
			stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_CANCEL);
			stTaskPdaEntity.setScanTime(scanTime);
			stTaskPdaEntity.setPdaNo(pdaNo);
			stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
			
			stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
//			如果此清仓任务不存在，或此清仓任务已完成的及已撤销的，不作处理
			StTaskEntity stTask = stTaskDao.queryStTaskByNo(stTaskNo);
			if(StringUtils.isNotEmpty(stTask.getId()) || 
			   StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, stTask.getTaskStatus())){
//			查看是否此清仓任务对应的PDA扫描明细都已撤销
				boolean scanDetailAllCanceled = checkStTaskScanDetailAllCanceled(stTaskNo);
//				若所有明细都已撤销
				if(scanDetailAllCanceled){
//					更新清仓任务状态为撤销状态
					StTaskEntity stTaskEntity = new StTaskEntity();
					stTaskEntity.setId(stTask.getId());
					stTaskEntity.setFinishtime(Calendar.getInstance().getTime());
					stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_CANCEL);
					
					stTaskDao.updateByPrimaryKeySelective(stTaskEntity);
					
					result = true;
				}
			}
			//若取消失败，则抛出异常给PDA
			if(!result) {
				throw new TfrBusinessException("已经存在扫描过的数据，不能撤销！");
			}
		}catch(Exception e){
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE, ExceptionUtils.getFullStackTrace(e));
		}
		
		return result;
	}

	/**
	 * 查看是否此清仓任务对应的PDA扫描明细都已撤销
	 *
	 * @param stTaskNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午4:29:10
	 */
	private boolean checkStTaskScanDetailAllCanceled(String stTaskNo) {
		boolean result = true;
		String tempGoodsNo = "";
		List<StResultListPdaEntity> lastScanPdaList = new ArrayList<StResultListPdaEntity>();
		
		List<StResultListPdaEntity> scanDetailList = stResultListPdaDao.queryStResultListPda(stTaskNo);
		
		for(StResultListPdaEntity scanDetail: scanDetailList){
			if(!StringUtils.equals(tempGoodsNo, scanDetail.getWaybillNo() + scanDetail.getSerialNo())){
				tempGoodsNo = scanDetail.getWaybillNo() + scanDetail.getSerialNo();
				
				lastScanPdaList.add(scanDetail);
			}
		}
		
		for(StResultListPdaEntity pdaEntity: lastScanPdaList){
			if(!StringUtils.equals(TransferPDADictConstants.ST_TASK_SCAN_STATUS_CANCEL, pdaEntity.getScanStatus())){
				result = false;
				
				break;
			}
		}
		
		return result;
	}

	/** 
	 * <pre>
	 * 完成清仓任务
	 * 1、仅仅标记某PDA分支已做完成的动作，不对清仓任务状态做处理
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:49:48
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService#finishPdaStTask(java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public boolean finishPdaStTask(String stTaskNo, Date scanTime, String pdaNo) {
		LOGGER.trace("into stockchecking pda service");
		
		boolean result = true;
		StTaskPdaEntity stTaskPdaEntity = new StTaskPdaEntity();
		
		stTaskPdaEntity.setStTaskNo(stTaskNo);
		stTaskPdaEntity.setStatus(TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH);
		stTaskPdaEntity.setScanTime(scanTime);
		stTaskPdaEntity.setPdaNo(pdaNo);
		stTaskPdaEntity.setCreateTime(Calendar.getInstance().getTime());
		
		stTaskPdaDao.addStTaskPdaEntity(stTaskPdaEntity);
		/*
//			如果此清仓任务不存在，不作处理
		StTaskEntity stTask = stTaskDao.queryStTaskByNo(stTaskNo);
		if(StringUtils.isNotEmpty(stTask.getId())){
//			按扫描时间倒序查询是否此清仓任务的此分支是否可执行完成操作
			boolean canFinishBranchTask = checkIfFinishBranchTask(stTaskNo, pdaNo);
			if(canFinishBranchTask){
//				更新清仓任务状态为撤销状态
				StTaskEntity stTaskEntity = new StTaskEntity();
				stTaskEntity.setId(stTask.getId());
				stTaskEntity.setFinishtime(Calendar.getInstance().getTime());
				stTaskEntity.setTaskStatus(TransferConstants.STOCK_CHECKING_DONE);
				
				stTaskDao.updateByPrimaryKeySelective(stTaskEntity);
				
				result = true;
			}else{
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_STTASK_BRANCH_NOT_EXISTS);
			}
		}
		*/
		
		return result;
	}

	/**
	 * <pre>
	 * 按pda编号、清仓任务编号查询pda清仓任务分支，并按扫描时间倒序排列
	 * 若上次扫描的pda清仓任务分支的状态为ST_TASK_STATUS_DOING或ST_TASK_STATUS_BRANCH_FINISH，可以执行完成清仓任务,返回true
	 * 若不存在此分支，返回false
	 * </pre>
	 * 
	 * @param stTaskNo
	 * @param pdaNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午5:06:03
	 */
	@SuppressWarnings("unused")
	private boolean checkIfFinishBranchTask(String stTaskNo, String pdaNo) {
		StTaskPdaEntity stTaskEntity = stTaskPdaDao.queryBranchPdaTask(stTaskNo, pdaNo);
		
		if(StringUtils.isEmpty(stTaskEntity.getId())){
			return false;
		}else if(StringUtils.equals(TransferPDADictConstants.ST_TASK_STATUS_DOING, stTaskEntity.getStatus()) ||
				 StringUtils.equals(TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH, stTaskEntity.getStatus())){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * @author niuly
	 * @date 2013-08-12 16:30:36
	 * @function 获取驻地派送货区code
	 * @param organizationCode
	 * @param goodsAreaType
	 * @return
	 */
	private String queryStationCode(String deptCode) {
		String stationCode = "";
		List<GoodsAreaEntity> resultList = new ArrayList<GoodsAreaEntity> ();
		resultList = goodsAreaService.querySimpleGoodsAreaListByType(deptCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		if(CollectionUtils.isNotEmpty(resultList)) {
			stationCode = resultList.get(0).getGoodsAreaCode();
		}
		return stationCode;
	}
	
	/**
	 * @author nly
	 * @date 2014年11月10日 上午8:45:45
	 * @function 校验是否可创建清仓任务
	 * @return
	 */
	private StTaskDto checkPdaTask(PdaStTaskDto pdaStTaskDto,StTaskEntity task){
		
		StTaskDto taskDto = null;
		List<StTaskDto> taskList = stTaskDao.queryTaskInProcess(task.getDeptcode(), task.getGoodsareacode());
		//sonar-352203
		if(taskList == null || taskList.size() <= 0) {
			return taskDto;
		}
			
			//在建清仓任务类型
			boolean isGoodsArea = false;
			boolean isPickUp = false;
			boolean isDeliver = false;
			boolean isDistrictCode = false;
			boolean isQty = false;
			
			if(StringUtils.isNotEmpty(task.getGoodsareacode())) {
				isGoodsArea = true;
			}
			if(StringUtils.isNotEmpty(task.getReceiveMethod())) {
				if(RECEIVE_METHOD_PICKUP.equals(task.getReceiveMethod())) {
					isPickUp = true;
				} else if(RECEIVE_METHOD_DELIVER.equals(task.getReceiveMethod())){
					isDeliver = true;
				}
			}
			if(StringUtils.isNotEmpty(task.getDistrictCode())) {
				isDistrictCode = true;
			}
			if(null != task.getStartQty() && task.getStartQty() > 0 && 
					null != task.getEndQty() && task.getEndQty() > 0) {
				isQty = true;
			}
			
			for(StTaskDto dto : taskList) {
				//sonar-352203
				if(null == dto) {
					continue;
				}
					//是否存在相同类型的任务
					//boolean isExists = false;
					//已存在的清仓任务类型
					boolean isHasGoodsArea = false;
					//boolean isHasReceiveMethod = false;
					boolean isHasPickUp = false;
					boolean isHasDeliver = false;
					boolean isHasDistrictCode = false;
					boolean isHasQty = false;
					boolean isHasPda = false;
					
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
					
					if(null != dto.getStartQty() && dto.getStartQty() > 0
							&& null != dto.getEndQty() && dto.getEndQty() > 0) {
						isHasQty = true;
					}
					
					if("Y".equals(dto.getIspda())) {
						isHasPda = true;
					}

					//判断在创建的任务类型是否与现有的存在交叉，若交叉给出提示
					//已存在清仓任务类型：营业部清仓
					if(!isHasGoodsArea) {
						return dto;
					}
					//已存在的清仓类型：货区
					else if(isHasGoodsArea && !isHasPickUp && !isHasDeliver && !isHasDistrictCode && !isHasQty) {
						//在建清仓任务：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
							if(isHasPda) {
							//isExists = true;
								return dto;
							} else {
								//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
								throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
							}	
						} else {
							throw new TfrBusinessException("该货区已存在未结束的清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(自提)
					else if(isHasGoodsArea && isHasPickUp && !isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区+件区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && isQty) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的清仓任务，请先处理未结束的任务！");
						}
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
							isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(自提)
						else if(isGoodsArea && isPickUp && !isHasQty) {
							if(isHasPda) {
								//isExists = true;
								return dto;
							}else {
								//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
								throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
							}
						} 
						//在建清仓任务：货区 + 提货方式(自提) + 件区
						else if(isGoodsArea && isPickUp && isHasQty) {
							isAlert = true;
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已存在未结束的自提清仓任务，请先处理未结束的任务！");
						}	
					}
					//已存在的清仓类型：货区 + 提货方式(自提) + 件区
					else if(isHasGoodsArea && isHasPickUp  && isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区+件区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && isQty) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的清仓任务，请先处理未结束的任务！");
						}
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
							} else if(task.getStartQty().equals(dto.getStartQty()) && task.getEndQty().equals(dto.getEndQty())) {
								if(isHasPda) {
									//isExists = true;
									return dto;
								} else {
									//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
									throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
								}
							}
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的自提清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(派送) + 分区
					else if(isHasGoodsArea && isHasDeliver && isHasDistrictCode && !isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区+件区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && isQty) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的清仓任务，请先处理未结束的任务！");
						}
						//在建清仓类型：货区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && !isQty) {
								isAlert = true;
						}
						//在建清仓类型：货区 + 提货方式(派送) + 分区
						else if(isGoodsArea && isDeliver && isDistrictCode && !isQty) {
							boolean isSameDistrictCode = this.compare(task.getDistrictCode(), dto.getDistrictCode());
							if(isSameDistrictCode) {
								if(isHasPda) {
									//isExists = true;
									return dto;
								} else {
									//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
									throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
								}
							}
						} 
						//在建清仓类型：货区 + 提货方式(派送) + 分区 + 件区
						else if(isGoodsArea && isDeliver && isDistrictCode && isQty) {
							isAlert = true;
						}
						if(isAlert) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getDistrictName() +"分区的派送清仓任务，请先处理未结束的任务！");
						}
					}
					//已存在的清仓类型：货区 + 提货方式(派送) + 分区 + 件区
					else if(isHasGoodsArea && isHasDeliver && isHasDistrictCode && isHasQty) {
						boolean isAlert = false;
						//在建清仓类型：货区+件区
						if(isGoodsArea && !isPickUp && !isDeliver && !isDistrictCode && isQty) {
							throw new TfrBusinessException("该货区已经存在未结束的"+ dto.getStartQty() + "-" + dto.getEndQty()+"件区的清仓任务，请先处理未结束的任务！");
						}
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
							if(isSameDistrictCode) {
								if(isCrossQty) {
									isAlert = true;
								}else if(task.getStartQty().equals(dto.getStartQty()) && task.getEndQty().equals(dto.getEndQty())) {
									if(isHasPda) {
										return dto;
									} else {
										//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
										throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
									}
								}
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
							} else if(task.getStartQty().equals(dto.getStartQty()) && task.getEndQty().equals(dto.getEndQty())) {
								if(isHasPda) {
									//isExists = true;
									return dto;
								} else {
									//PC端的任务时给出提示，不允许PDA处理PC端建立的清仓任务
									throw new TfrBusinessException("已经存在PC机创建的清仓任务，任务号：" + dto.getTaskNo() +",请处理完后再创建！");
								}
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
		return taskDto;
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
		if(startQty.equals(hasStartQty) && endQty.equals(hasEndQty)) {
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
	 * @author nly
	 * @date 2014年11月14日 下午4:45:04
	 * @function 根据分区code查询分区名称
	 * @param zoneCode
	 * @return
	 */
	private String queryZoneName(String zoneCode) {
		SendDistrictMapEntity entity = new SendDistrictMapEntity();
		entity.setZoneCode(zoneCode);
		List<SendDistrictMapEntity> list = sendDistrictMapService.querySendDistrictMap(entity);
		if(null == list || list.size() <= 0) {
			throw new TfrBusinessException("查询分区失败！","");
		}
		
		if(null != list.get(0)) {
			return list.get(0).getZoneName();
		}
		return "";
	}
	/**
	 * @author nly
	 * @date 2014年11月13日 上午11:04:31
	 * @function 获取外场下的分区信息
	 * @param deptCode
	 * @return
	 */
	@Override
	public List<StTaskZoneDto> queryZoneInfo(String deptCode) {
		List<StTaskZoneDto> dtoList = new ArrayList<StTaskZoneDto>();
		
		SendDistrictMapEntity entity = new SendDistrictMapEntity();
		entity.setTransferCenterCode(deptCode);
		List<SendDistrictMapEntity> list = sendDistrictMapService.querySendDistrictMap(entity);
		if(null == list || list.size() <= 0) {
			throw new TfrBusinessException("查询分区失败！","");
		}
		
		for(SendDistrictMapEntity zoneEntity : list) {
			StTaskZoneDto dto = new StTaskZoneDto();
			dto.setDeptCode(deptCode);
			dto.setZoneCode(zoneEntity.getZoneCode());
			dto.setZoneName(zoneEntity.getZoneName());
			
			dtoList.add(dto);
		}
		return dtoList;
	}
	/**
	 * 下拉包的运单明细
	 * @author zhuyunrong
	 * @date 2015-03-31 09：17
	 * @param Strng packageNo
	 * @return List<PcakageWayBillDto> (运单和流水号)
	 * **/
	private List<PcakageWayBillDto> queryPackageDetail(String packageNo){
		return stResultListDao.queryPackageDetail(packageNo) ;
	}
	/**
	 * @author 268084
	 * @date 
	 * @function 根据外场code和库区编号查找库区实体(类型)
	 * @param deptCode
	 * @return
	 */
	@Override
	public String queryGoodsType(String transferCenterCode,
			String goodsAreaCode) {
		if(StringUtil.isBlank(transferCenterCode)||StringUtil.isBlank(goodsAreaCode)){
			return null;
		}
		try {
			GoodsAreaEntity goodsAreaEntity=goodsAreaService.queryGoodsAreaByCode(transferCenterCode, goodsAreaCode);
			if(goodsAreaEntity==null){
				return null;
			}
			if(StringUtil.equals(goodsAreaEntity.getGoodsAreaType(), "BSE_GOODSAREA_TYPE_EXCEPTION")){
				return "Y";
			}else{
				return "N";
			}
		} catch (BusinessException e) {
			LOGGER.info("调用综合查询库区信息出错"+e);
			return null;
		}
		
	}
	/**
	 * @author 268084
	 * @date 
	 * @function 根据部门编号获取货区信息
	 * @param deptCode
	 * @return
	 */
	@Override
	public String queryDeptInfo(String deptCode){
		try {
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(deptCode);
			if(saleDepartmentEntity==null){
				return "N";
			}
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
				return "Y";
			}else{
				return "N";
			}
		} catch (BusinessException e) {
			LOGGER.info("调用综合接口查询部门信息失败"+e);
			return null;
		}
	}
	
}