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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/service/impl/QueryUnpackService.java
 *  
 *  FILE NAME          :QueryUnpackService.java
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
 * 
 */
package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryUnpackDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.CurrentDeptDto;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;

/**
 * 查询营业部代包装业务层功能
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */

public class QueryUnpackService implements IQueryUnpackService {

	/**
	 *  DAO接口
	 */
	private IQueryUnpackDao queryUnpackDao;
	/**
	 * 货区接口
	 */
	private IGoodsAreaService goodsAreaService;
	/**
	 *  获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 数据字典
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * service层，查询所有需要本外场代打包装的货物信息
	 * 
	 * @param queryUnpackConditionEntity
	 *            查詢條件
	 * @param limit
	 *            限制数
	 * @param start
	 *            开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:10:00
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryUnpackALL(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity,
	 *      int, int)
	 */
	@Override
	public List<QueryUnpackResultEntity> queryUnpackALL(
			QueryUnpackConditionEntity queryUnpackConditionEntity, int limit,
			int start) {
	/*	PDAPackagingInfoEntity pda = new PDAPackagingInfoEntity();
		pda.setWayBillNumber("103937405");
		pda.setOrgCode("W1200030122");
		List<String> list = new ArrayList<String>();
		List<String> empList = new ArrayList<String>();
		empList.add("089902");
		empList.add("005566");
		empList.add("002320");
		list.add("0002");
		pda.setSerialNo(list);
		pda.setEmpCode(empList);
	
		pdaPackagingService.addPackagingInfo(pda);*/
		//String 
		/*List<QueryPDAUnpackResEntity> pdaList = pdaPackagingService.queryPDAUnpackResult(queryUnpackConditionEntity.getPackDept());
		System.out.print(pdaList.size());*/
		
		
		/*Map<String,Object> map = new HashMap<String,Object>();
		String waybillNo = "99400019";
		String serialNo = "0002";
		//运单号
		map.put("waybillNo", waybillNo);
		//流水号
		map.put("serialNo", serialNo);
		//是否已包装
		//FossConstants.NO 未包装
		map.put("isPacked", FossConstants.NO);
		//查询符合条件个数
		int num = pdaPackagingDao.selectSerialUnpack(map);
		//等于0，表示此流水号不符合条件
		if(num == 0){
			throw new PackagingException("流水号：" + serialNo + "已包装或不需要包装" );
		}*/
		/*PackagingRequireEntity pe = new PackagingRequireEntity();
		pe.setPackagingDeptCode("W1200030122");
		pe.setWaybillNo("103937405");
		List<String> addSerialNo = new ArrayList<String>();
		addSerialNo.add("0001");
		addSerialNo.add("0002");
		List<String> deleteSerialNo = new ArrayList<String>();
		packOutService.updatePackagingRequire(pe, addSerialNo, deleteSerialNo);*/
		
		// 获取处理后的查询条件
		QueryUnpackConditionEntity newQueryUnpackConditionEntity = obtainQueryUnpackCondition(queryUnpackConditionEntity);
		//结果集
		List<QueryUnpackResultEntity> queryUnpackResultList;
		//调用DAO，执行查询操作
		//queryUnpackResultList = queryUnpackDao.queryUnpackALL(
			//	newQueryUnpackConditionEntity, limit, start);
		//2013-04-02 ,取消分页
		queryUnpackResultList = queryUnpackDao.queryUnpackALL(newQueryUnpackConditionEntity);
		return queryUnpackResultList;
	}

	/** 
	 * 分页总数
	 * 
	 * @param queryUnpackConditionEntity
	 *            查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午5:17:11
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryTotalCount(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity)
	 */
	@Override
	public Long queryTotalCount(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		// 获取处理后的查询条件
		QueryUnpackConditionEntity newQueryUnpackConditionEntity = obtainQueryUnpackCondition(queryUnpackConditionEntity);
		//查询总数
		Long totalCount = queryUnpackDao
				.queryTotalCount(newQueryUnpackConditionEntity);
		return totalCount;

	}
	/**
	 * 整合处理查询条件
	 * 
	 * @param queryUnpackConditionEntity
	 *            查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-29 上午10:44:53
	 */
	private QueryUnpackConditionEntity obtainQueryUnpackCondition(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		//查找该包装部门的打木架货区
		List<GoodsAreaEntity> areaList = goodsAreaService.queryGoodsAreaListByType(
				queryUnpackConditionEntity.getPackDept().trim(),
				DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		if(areaList.size()==0){
			throw new PackagingException("系统中无当前包装部门");
		}
		GoodsAreaEntity goodsArea = areaList.get(0);
		if (goodsArea == null) {
			throw new PackagingException("你所处的外场无打木架货区");
		}
		//设置包装区code
		queryUnpackConditionEntity.setAreaCode(goodsArea.getGoodsAreaCode());
		/*queryUnpackConditionEntity
				.setStatusName(DictionaryConstants.PACKAGING_GOODS_STATUS_QUERY);*/
		//运输类型
		queryUnpackConditionEntity
				.setTransportType(TransferDictionaryConstants.TRANSPORT_TYPE);
		//设置数据字典可用性
		queryUnpackConditionEntity
				.setDictActive(PackagingConstants.PACKAING_DICT_ACTIVE_Y);
		//是否查询全部
		if (StringUtils.equals(queryUnpackConditionEntity.getGoodsStatus(),
				PackagingConstants.PACKAGING_DETAILS_GOODS_ALL)) {
			//查询条件中状态设置为空
			queryUnpackConditionEntity.setGoodsStatus(null);
		}
		//设置开单起始时间
		queryUnpackConditionEntity
				.setWaybillBeginDate(queryUnpackConditionEntity
						.getWaybillBeginDate());
		//设置开单结束时间
		queryUnpackConditionEntity.setWaybillEndDate(queryUnpackConditionEntity
				.getWaybillEndDate());
		return queryUnpackConditionEntity;
	}

	/** 
	 * service层，查询每票货物的木架区库存、是否已包装信息
	 * 
	 * @param waybillno
	 *            运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:11:47
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryUnpackDetails(java.lang.String)
	 */
	@Override
	public List<QueryUnpackDetailsEntity> queryUnpackDetails(
			QueryUnpackConditionEntity unpackCondition) {
		//获取当前部门外场
		CurrentDeptDto currentDeptDto = queryCurrentDept();
		List<QueryUnpackDetailsEntity> queryUnpackDetailsList = queryUnpackDao
				.queryUnpackDetails(unpackCondition);
		// 遍历集合，判断是否在木架区库存中
		for (int i = 0; i < queryUnpackDetailsList.size(); i++) {
			// 货区编码
			String areaCode = queryUnpackDetailsList.get(i).getGoodsAreaCode();
			//交接单号
			String hanover = queryUnpackDetailsList.get(i).getHandoverNo();
			//交接状态
			//String hanoverState = queryUnpackDetailsList.get(i).getHandoverState();
			//货区列表
			List<GoodsAreaEntity> areaList = goodsAreaService.queryGoodsAreaListByType(currentDeptDto.getDeptCode(),
					DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
			if(areaList.size()==0){
				throw new PackagingException("你所处的外场无打木架货区");
			}
			//货区
			GoodsAreaEntity goodsArea = areaList.get(0);
			//是否为空
			if(goodsArea == null){ 
				throw new PackagingException("你所处的外场无打木架货区"); 
			}
			 
			// 木架区编码
			String yokeCode = goodsArea.getGoodsAreaCode();
			// 判断是否木架区库存
			if (StringUtils.equals(areaCode, yokeCode)) {
				//木架区库存
				queryUnpackDetailsList.get(i).setIsStockInYoke(
						PackagingConstants.PACKAGING_IS_IN_YOKE);
			} else {
				//非木架区库存
				queryUnpackDetailsList.get(i).setIsStockInYoke(
						PackagingConstants.PACKAGING_NOT_IN_YOKE);
			}
			// 设置状态hanoverState
			if (StringUtils.isEmpty(hanover) && StringUtils.isEmpty(areaCode)) {
				//开单
				queryUnpackDetailsList.get(i).setGoodsStatus(
						PackagingConstants.PACKAGING_DETAILS_GOODS_CREATE);
			} else if (StringUtils.isEmpty(areaCode)) {
				//交接
				queryUnpackDetailsList.get(i).setGoodsStatus(
						PackagingConstants.PACKAGING_DETAILS_GOODS_HANDOVER);
			} else if (!StringUtils.isEmpty(areaCode)) {
				//库存中
				queryUnpackDetailsList.get(i).setGoodsStatus(
						PackagingConstants.PACKAGING_DETAILS_GOODS_STOCK);
			}
			
			//添加 是否打木托判定
			//-------------> 兼容无包装类型的都是默认打木架
			if(null != queryUnpackDetailsList.get(i).getPackageType() && null != queryUnpackDetailsList.get(i).getIsPacked()){
				
				String[] isPackeds = queryUnpackDetailsList.get(i).getIsPacked().split(",");
				String[] packageMaterials = queryUnpackDetailsList.get(i).getPackageType().split(",");
				
				//isPackeds[0]
				queryUnpackDetailsList.get(i).setIsPacked("");
				for(int j=0;j<packageMaterials.length;j++){
					if(packing.MAKE_WOODEN_STOCK.getValue().equals(packageMaterials[j]))
					{
						queryUnpackDetailsList.get(i).setIsWoodCare(isPackeds[j]);
					}else if(packing.WOODEN_FRAME.getValue().equals(packageMaterials[j])){
						queryUnpackDetailsList.get(i).setIsPacked(isPackeds[j]);
					}
				}
				
			}
		}
		return queryUnpackDetailsList;
	}

	/**
	 * 获取当前登录人组织信息
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 下午8:01:34
	 */
	@Override
	public CurrentDeptDto queryCurrentDept() {
		CurrentDeptDto deptDto = new CurrentDeptDto();
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//当前组织信息
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		//原始组织信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
						bizTypes);
		if(org != null){
			//部门编码
			deptDto.setDeptCode(org.getCode());
			//部门名称
			deptDto.setDeptName(org.getName());
			return deptDto;
		}else{
			throw new PackagingException("当前登录人无操作部门");
		}
	}
	
	/**
	 * 输出代包装信息字符流
	 * @author 046130-foss-xuduowei
	 * @date 2013-02-22 下午8:01:34
	 */
	@Override
	public InputStream exportExcelStream(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		//输出流
		InputStream excelStream = null;
		//excel表头信息
		SheetData sheetData = new SheetData();
		String[] rowHeads = null;
		rowHeads = PackagingConstants.PACKAGING_EXPORT_UNPACK_HEADER;
		sheetData.setRowHeads(rowHeads);
		//查询结果集
		List<QueryUnpackResultEntity> unpackList = queryUnpackDao.queryUnpackALL(obtainQueryUnpackCondition(queryUnpackConditionEntity));
		/*//判断是否为空
		if(unpackList == null || unpackList.isEmpty()){
			return null;
		}*/
		//输出流前身
		List<List<String>> excelList = new ArrayList<List<String>>();
		//临时集合变量
		List<String> tempList = null;
		//货物状态集合对象
		List<DataDictionaryValueEntity> goodsStatusList = 
				dataDictionaryValueService.queryDataDictionaryValueByTermsCode(
						DictionaryConstants.PACKAGING_GOODS_STATUS_QUERY);
		if(goodsStatusList == null || goodsStatusList.isEmpty()){
			throw new PackagingException("未找到货物状态数据字典");
		}
		//定义货物状态map
		Map<String,Object> statusMap = new HashMap<String,Object>();
		//设置到map中，在导出时根据code从map中取name
		for(DataDictionaryValueEntity dataDict : goodsStatusList){
			statusMap.put(dataDict.getValueCode(), dataDict.getValueName());
		}
		//遍历结果集
		for(QueryUnpackResultEntity unpackEntity : unpackList){
			tempList = new ArrayList<String>();
			//运单号
			tempList.add(unpackEntity.getWayBillNumber());
			//开单件数
			tempList.add(unpackEntity.getWaybillNum() + "");
			//开单体积
			tempList.add(unpackEntity.getWaybillVolume().toString());
			//需要包装体积
			tempList.add(unpackEntity.getNeedPackVolume().toString());
			//包装区入库件数
			tempList.add(unpackEntity.getPackStockNum() + "");
			//需要包装件数
			tempList.add(unpackEntity.getNeedPackNum() + "");
			//代包装要求
			tempList.add(unpackEntity.getPackRequire() + "");
			//已包装件数
			tempList.add(unpackEntity.getPackedNum() + "");
			//货物状态
			tempList.add(String.valueOf(statusMap.get(unpackEntity.getGoodsStatus())));
			//运输类型
			tempList.add(unpackEntity.getTransportationType());
			//货物名称
			tempList.add(unpackEntity.getGoodsName());
			//开单部门
			tempList.add(unpackEntity.getWaybillCreateDept());
			//目的站
			tempList.add(unpackEntity.getCustomerPickupOrgName());
			//开单时间
			tempList.add(unpackEntity.getWaybillCreateDate());
			/*if(!StringUtils.isEmpty(unpackEntity.getWaybillCreateDate())){
				LOGGER.info(unpackEntity.getWayBillNumber() + unpackEntity.getWaybillCreateDate());
				tempList.add(unpackEntity.getWaybillCreateDate().substring(0, unpackEntity.getWaybillCreateDate().indexOf(PackagingConstants.PACKAGING_EXPORT_DOT)));
			}else{
				tempList.add("");
			}*/
			//包装要求
			//tempList.add(unpackEntity.getPackRequire());
			//预计到达时间
			tempList.add(unpackEntity.getPredictArriveDate());
			/*if(!StringUtils.isEmpty(unpackEntity.getPredictArriveDate())){
				LOGGER.info(unpackEntity.getWayBillNumber() + unpackEntity.getPredictArriveDate());
				tempList.add(unpackEntity.getPredictArriveDate().substring(0, unpackEntity.getPredictArriveDate().indexOf(PackagingConstants.PACKAGING_EXPORT_DOT)));
			}else{
				tempList.add("");
			}*/
			//预计出发时间
			tempList.add(unpackEntity.getPredictDepartDate());
			/*if(!StringUtils.isEmpty(unpackEntity.getPredictDepartDate())){
				LOGGER.info(unpackEntity.getWayBillNumber() + unpackEntity.getPredictDepartDate());
				tempList.add(unpackEntity.getPredictDepartDate().substring(0, unpackEntity.getPredictDepartDate().indexOf(PackagingConstants.PACKAGING_EXPORT_DOT)));
			}else{
				
			}*/
			//增加到集合中
			excelList.add(tempList);
		}
		sheetData.setRowList(excelList);
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(
				sheetData, "代包装",PackagingConstants.PACKAGING_EXPORT_FILE_SIZE));
		return excelStream;
	}

	/**
	 * 获取 dAO接口.
	 *
	 * @return the dAO接口
	 */
	public IQueryUnpackDao getQueryUnpackDao() {
		return queryUnpackDao;
	}

	/**
	 * 设置 dAO接口.
	 *
	 * @param queryUnpackDao the new dAO接口
	 */
	public void setQueryUnpackDao(IQueryUnpackDao queryUnpackDao) {
		this.queryUnpackDao = queryUnpackDao;
	}

	/**
	 * 设置 货区接口.
	 *
	 * @param goodsAreaService the new 货区接口
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 设置 获取指定部门接口.
	 *
	 * @param orgAdministrativeInfoComplexService the new 获取指定部门接口
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	
}