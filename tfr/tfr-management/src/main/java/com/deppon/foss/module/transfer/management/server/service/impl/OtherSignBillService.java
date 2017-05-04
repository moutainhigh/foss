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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/service/impl/OtherSignBillService.java
 *  
 *  FILE NAME          :OtherSignBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.service.impl;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao;
import com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.define.SignBillConstants;
import com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *  其他签单Service
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public class OtherSignBillService implements IOtherSignBillService {
	
	/**
	 * 
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(OtherSignBillService.class);
	
	/**
	 * 其他签单Dao接口
	**/
	private IOtherSignBillDao otherSignBillDao;
	
	/**
	 * 车辆service
	 */
	private IVehicleService vehicleService;	
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 
	 */
	private static final  BigDecimal BIGFEE=new BigDecimal(100);
	
	/**
	 * 设置 车辆service.
	 *
	 * @param vehicleService the new 车辆service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
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
		//顶级车队
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的事业部！##########");
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}
	
	/**
	 * @Title: queryChildDept 
	 * @Description: 根据部门编号找到该部门下所有子部门 
	 * @return    
	 * @return String    返回类型 
	 * queryChildDept
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-2下午2:28:32
	 */
	public List<String> queryChildDept(String orgCode) {
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> orgCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			orgCodes.add(orgAdministrativeInfo.getCode());
		}
		//返回部门code
		return orgCodes;
	}
	
	/** 
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#addOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity)
	 */
	@Override
	@Transactional	
	public int addOtherSignBill(OtherSignBillEntity otherSignBillEntity) {	
		OtherSignBillEntity entity=new OtherSignBillEntity();
		//ID
		entity.setId(UUIDUtils.getUUID());
		//新增部门
		entity.setOrgCode(FossUserContext.getCurrentDeptCode());
		//新增部门
		entity.setOrgName(FossUserContext.getCurrentDept().getName());
		//用车部门名称
		entity.setUseTruckOrgName(otherSignBillEntity.getUseTruckOrgName());// 用车部门名称
		//司机
		entity.setDriverCode(otherSignBillEntity.getDriverCode());// 司机
		//签单编号
		entity.setSignBillNo(otherSignBillEntity.getSignBillNo());// 签单编号
		//用车部门
		entity.setUseTruckOrgCode(otherSignBillEntity.getUseTruckOrgCode());// 用车部门
		//日期
		entity.setSignBillDate(otherSignBillEntity.getSignBillDate());// 日期
		//司机姓名
		entity.setDriverName(otherSignBillEntity.getDriverName());// 司机姓名
		//用车类型
		entity.setUseTruckType(otherSignBillEntity.getUseTruckType());// 用车类型
		//签单类型
		entity.setSignBillType(otherSignBillEntity.getSignBillType());// 签单类型
		//目的地
		entity.setArrvRegionName(otherSignBillEntity.getArrvRegionName());// 目的地
		//票数
		entity.setBillQty(otherSignBillEntity.getBillQty());// 票数
		//线路里程
		entity.setLineDistance(otherSignBillEntity.getLineDistance());// 线路里程
		//车牌号
		entity.setVehicleNo(otherSignBillEntity.getVehicleNo());// 车牌号
		//车型
		entity.setVehicleTypeLength(otherSignBillEntity.getVehicleTypeLength());// 车型
		//体积
		entity.setVolume(otherSignBillEntity.getVolume());// 体积
		//重量
		entity.setWeight(otherSignBillEntity.getWeight());// 重量
		//用车时间
		entity.setUseTruckDuration(otherSignBillEntity.getUseTruckDuration());// 用车时间
		//其它金额
		entity.setOtherFee(otherSignBillEntity.getOtherFee().multiply(BIGFEE));// 其它金额
		//司机提成
		entity.setDriverRoyalty(otherSignBillEntity.getDriverRoyalty().multiply(BIGFEE));// 司机提成
		//用车费用划分
		entity.setUseTruckFee(otherSignBillEntity.getUseTruckFee().multiply(BIGFEE));// 用车费用划分
		//备注
		entity.setNotes(otherSignBillEntity.getNotes());
		//币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//添加操作
		return otherSignBillDao.addOtherSignBill(entity);
	}

	/** 
	 * 删除其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#deleteOtherSignBill(java.lang.String)
	 */
	@Override
	@Transactional	
	public int deleteOtherSignBill(String id) {		
		return otherSignBillDao.deleteOtherSignBill(id);
	}

	/** 
	 * 修改其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#updateOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity)
	 */
	@Override
	@Transactional	
	public int updateOtherSignBill(OtherSignBillEntity otherSignBillEntity) {
		//其它金额
		otherSignBillEntity.setOtherFee(otherSignBillEntity.getOtherFee().multiply(BIGFEE));// 其它金额
		//司机提成
		otherSignBillEntity.setDriverRoyalty(otherSignBillEntity.getDriverRoyalty().multiply(BIGFEE));// 司机提成
		//用车费用划分
		otherSignBillEntity.setUseTruckFee(otherSignBillEntity.getUseTruckFee().multiply(BIGFEE));// 用车费用划分
		//返回结果
		return otherSignBillDao.updateOtherSignBill(otherSignBillEntity);
	}

	/** 
	 * 根据Id查询其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryOtherSignBillById(java.lang.String)
	 */
	@Override
	public OtherSignBillEntity queryOtherSignBillById(String id) {
		return otherSignBillDao.queryOtherSignBillById(id);
	}	

	/**
	 * 设置 其他签单Dao接口.
	 *
	 * @param otherSignBillDao the new 其他签单Dao接口
	 */
	public void setOtherSignBillDao(IOtherSignBillDao otherSignBillDao) {
		this.otherSignBillDao = otherSignBillDao;
	}

	/** 
	 * 查询其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto, int, int)
	 */
	@Override
	public List<OtherSignBillEntity> queryOtherSignBill(OtherSignBillDto otherSignBillDto, int start, int limit) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		otherSignBillDto.setOrgCodes(orgCodes);
		//返回结果
		return otherSignBillDao.queryOtherSignBill(otherSignBillDto, start, limit);
	}

	/** 
	 * 查询其他签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public Long queryCount(OtherSignBillDto otherSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		otherSignBillDto.setOrgCodes(orgCodes);
		return otherSignBillDao.queryCount(otherSignBillDto);
	}

	/** 
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryOtherSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public OtherSignBillDto queryOtherSignBillByFee(OtherSignBillDto otherSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		otherSignBillDto.setOrgCodes(orgCodes);
		return otherSignBillDao.queryOtherSignBillByFee(otherSignBillDto);
	}

	/** 
	 * 查询其他签单费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryOtherSignBillByVehicleNo(java.lang.String)
	 */
	@Override
	public OtherSignBillEntity queryOtherSignBillByVehicleNo(String vehicleNo) {
		//车辆信息
		VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		//其他签单Entity
		OtherSignBillEntity entity=new OtherSignBillEntity();
		if(vehicleAssociationDto!=null){
			//设置车型
			entity.setVehicleTypeLength(vehicleAssociationDto.getVehicleLengthCode()+"");
		}
		//返回结果
		return entity;
	}

	/** 
	 * 计算其他签单费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#calculateOtherSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public OtherSignBillEntity calculateOtherSignBillByFee(OtherSignBillDto otherSignBillDto) {
		//do nothing
		return null;
	}

	/**
	 * 创建cell
	 * @author 099197-foss-liming
	 * @date 2012-12-26 下午2:36:20
	 */
	private HSSFCell createHSSFCell(HSSFRow row, int columnIndex, String value) {
		//创建cell
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(value);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		//返回结果
		return cell;
	}
	
	
	/**
	 * 
	 * 创建一个excel行
	 * @param sheet 
	 * @param rowIndex 
	 * @param result 
	 * @return 
	 */
	private HSSFRow createDataRow(HSSFSheet sheet, int rowIndex, OtherSignBillEntity result) {
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//格式化时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//派送单号
		createHSSFCell(row, columnIndex, result.getSignBillNo());		//派送单号
		columnIndex ++;
		
		//部门
		createHSSFCell(row, columnIndex, result.getUseTruckOrgName());//部门
		columnIndex ++;
		
		//司机姓名
		createHSSFCell(row, columnIndex, result.getDriverName());//司机姓名
		columnIndex ++;		
		
		//日期
		createHSSFCell(row, columnIndex, format.format(result.getSignBillDate()));//日期
		columnIndex ++;
		
		//车牌号
		createHSSFCell(row, columnIndex, result.getVehicleNo());//车牌号
		columnIndex ++;
		
		//车型
		createHSSFCell(row, columnIndex, result.getVehicleTypeLengthName());//车型
		columnIndex ++;		
		
		///签单类型
		if(StringUtils.equalsIgnoreCase(result.getSignBillType(), SignBillConstants.INTERIOR_SIGN_BILL)){//签单类型
			//内部签单 
			createHSSFCell(row, columnIndex, SignBillConstants.INTERIOR_SIGN_BILL_NAME);
		}else if(StringUtils.equalsIgnoreCase(result.getSignBillType(), SignBillConstants.SPECIAL_SIGN_BILL)){
			//专车签单
			createHSSFCell(row, columnIndex, SignBillConstants.SPECIAL_SIGN_BILL_NAME);//专车签单
		}else{
			//其他签单
			createHSSFCell(row, columnIndex, SignBillConstants.OTHER_SIGN_BILL_NAME);//其他签单
		}
		
		columnIndex ++;
		
		//目的地
		createHSSFCell(row, columnIndex, result.getArrvRegionName());
		columnIndex ++;
		
		//线路里程
		createHSSFCell(row, columnIndex, result.getLineDistance()+"");
		columnIndex ++;
		
		// 票数
		Integer billQty = result.getBillQty() == null ? Integer.valueOf(0) : result.getBillQty();
		createHSSFCell(row, columnIndex, billQty + "");
		columnIndex ++;
		
		//体积
		BigDecimal volume = result.getVolume() == null ? BigDecimal.ZERO : result.getVolume();
		createHSSFCell(row, columnIndex, volume + "");
		columnIndex ++;
		
		//重量
		BigDecimal weight = result.getWeight() == null ? BigDecimal.ZERO : result.getWeight();
		createHSSFCell(row, columnIndex, weight + "");
		columnIndex ++;
		
		//用车时间
		BigDecimal useTruckDuration = result.getUseTruckDuration() == null ? BigDecimal.ZERO : result.getUseTruckDuration();
		createHSSFCell(row, columnIndex, useTruckDuration + "");
		columnIndex ++;
		
		//其它金额
		BigDecimal otherFee = result.getOtherFee() == null ? BigDecimal.ZERO : result.getOtherFee();
		createHSSFCell(row, columnIndex, otherFee + "");
	
		return row;
	}
	
	/** 
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:05:31
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService#queryExportOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public InputStream queryExportOtherSignBill(OtherSignBillDto otherSignBillDto) {
		//其他签单Entity
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		otherSignBillDto.setOrgCodes(orgCodes);
		List<OtherSignBillEntity> otherSignBillList=otherSignBillDao.queryExportOtherSignBill(otherSignBillDto);
		InputStream excelStream = null;
		LOGGER.info("开始");
		//总条数
		int dataSize = otherSignBillList.size();//总条数
		double sheetNo = Math.ceil(dataSize / SignBillConstants.SHEET_SIZE);	
		ExcelExport excelExportUtil = new ExcelExport();
		HSSFWorkbook workbook = excelExportUtil.createWorkBook();
		//样式对象
	    HSSFCellStyle style = workbook.createCellStyle(); // 样式对象
	    //垂直
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        //水平
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平     
        for(int index = 0; index <= sheetNo; index++) {
  			int rowIndex = 0;
  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "签单" + index);
            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_25);            
			// 标题
     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, SignBillConstants.OTHER_SHEET_NAME);
     		titleCell2.setCellStyle(style);
     		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++;     		     		
			// head 2
			HSSFRow headRow2 = sheet.createRow(rowIndex);			
     		for(int i = 0, len = SignBillConstants.OTHER_ROW_HEADS.length; i<len; i++) {
     			createHSSFCell(headRow2, i, SignBillConstants.OTHER_ROW_HEADS[i]);
     		}
			rowIndex++;
			/** 司机个数 */
			Integer driverCount = 0;
			/**总体积*/
			BigDecimal volumeTotal = new BigDecimal(0);
			/** 总重量 */
			BigDecimal weightTotal = new BigDecimal(0);
			/** 总线路里程 */
			BigDecimal lineDistanceTotal = new BigDecimal(0);
			/** 总票数 */
			Integer billQtyCount = 0;
			/** 总的用车时间 */
			BigDecimal useTruckDurationTotal = new BigDecimal(0);
			/** 总其它金额*/
			BigDecimal otherFeeTotal = new BigDecimal(0);
			int startNo = index * (SignBillConstants.SHEET_SIZE - 1);
			int endNo = Math.min(startNo + SignBillConstants.SHEET_SIZE - 1,
					dataSize);
			Set<String> set = new HashSet<String>();// 司机
			List<String> listCount = new ArrayList<String>();// 新增所有的司机
			for (int i = startNo; i < endNo; i++) {
				OtherSignBillEntity result = otherSignBillList.get(i);
				createDataRow(sheet, rowIndex, otherSignBillList.get(i));
				rowIndex++;
				// 累计
				// 毛重累加
				weightTotal = weightTotal.add(result.getWeight());
				// 设置计费重量
				volumeTotal = volumeTotal.add(result.getVolume());
				// 总线路里程
				lineDistanceTotal = lineDistanceTotal.add(result
						.getLineDistance());
				// 总票数
				billQtyCount = billQtyCount + result.getBillQty();
				// 总的用车时间
				useTruckDurationTotal = useTruckDurationTotal.add(result
						.getUseTruckDuration());
				// 总其它金额
				otherFeeTotal = otherFeeTotal.add(result.getOtherFee());
				listCount.add(result.getDriverCode());
			}
			set.addAll(listCount);
			driverCount = set.size();
            //合计行
            HSSFRow totalRow = sheet.createRow(rowIndex);
            //合计
            createHSSFCell(totalRow, 0, "合计");
            //司机数:  
            createHSSFCell(totalRow, 1, "司机数:  "+String.valueOf(driverCount));
            // 线路里程合计: 
            createHSSFCell(totalRow, 2, " 线路里程合计: "+String.valueOf(lineDistanceTotal));
            //体积合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, "体积合计: "+String.valueOf(volumeTotal));    
            //重量合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_4, "重量合计: "+String.valueOf(weightTotal));
            //票数合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_5, "票数合计: "+String.valueOf(billQtyCount));
            // 用车时间合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_6, " 用车时间合计: "+String.valueOf(useTruckDurationTotal));
            //总其它金额: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_7, "总其它金额: "+String.valueOf(otherFeeTotal));
            rowIndex ++; 
         }
		excelStream = excelExportUtil.inputToClient(workbook);
		//返回结果
        return excelStream;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-5-7下午2:47:20
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}