/**
 *  initial comments.
 *  *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/service/impl/RegularTruckSignBillService.java
 *  
 *  FILE NAME          :RegularTruckSignBillService.java
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao;
import com.deppon.foss.module.transfer.management.api.server.service.IRegularTruckSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.define.SignBillConstants;
import com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *  专线对发签单Service
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:32:47
 */
 
public class RegularTruckSignBillService implements	IRegularTruckSignBillService {
	/**日志*/
	private static final Logger LOGGER=LoggerFactory.getLogger(RegularTruckSignBillService.class);
	/**注入专线对发签单dao*/
	private IRegularTruckSignBillDao regularTruckSignBillDao;
	/**注入车辆Service*/
	private IVehicleService vehicleService;
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private static final  BigDecimal BIGFEE=new BigDecimal(100);
	
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
	 * 新增专线对发签单
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public int addRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity) {
		regularTruckSignBillEntity.setMsldRoyalty(regularTruckSignBillEntity.getMsldRoyalty().multiply(BIGFEE)); //对发单程线路里程提成
		regularTruckSignBillEntity.setEmsldRoyalty(regularTruckSignBillEntity.getEmsldRoyalty().multiply(BIGFEE)); //空车对发单程里程提成	
		regularTruckSignBillEntity.setDriverRoyaltyAmount(regularTruckSignBillEntity.getDriverRoyaltyAmount().multiply(BIGFEE)); // 司机提成总额
		regularTruckSignBillEntity.setId(UUIDUtils.getUUID());
		regularTruckSignBillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//新增部门
		regularTruckSignBillEntity.setOrgCode(FossUserContext.getCurrentDeptCode());
		//新增部门
		regularTruckSignBillEntity.setOrgName(FossUserContext.getCurrentDept().getName());
		return regularTruckSignBillDao.addRegularTruckSignBill(regularTruckSignBillEntity);
		 
	}

	/**
	 * 删除专线对发签单
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public int deleteRegularTruckSignBill(String id) {
		return regularTruckSignBillDao.deleteRegularTruckSignBill(id);
		
	}

	/**
	 * 修改专线对发签单
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public int updateRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity) {
		//设置 对发单程线路里程提成
		regularTruckSignBillEntity.setMsldRoyalty(regularTruckSignBillEntity.getMsldRoyalty().multiply(BIGFEE)); //对发单程线路里程提成
		//设置 对发单程线路里程提成
		regularTruckSignBillEntity.setEmsldRoyalty(regularTruckSignBillEntity.getEmsldRoyalty().multiply(BIGFEE)); //空车对发单程里程提成
		//司机提成总额
		regularTruckSignBillEntity.setDriverRoyaltyAmount(regularTruckSignBillEntity.getDriverRoyaltyAmount().multiply(BIGFEE)); // 司机提成总额
		//返回结果
		return	regularTruckSignBillDao.updateRegularTruckSignBill(regularTruckSignBillEntity);
		
	}

	/**
	 * 分页查询专线对发签单
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public List<RegularTruckSignBillEntity> queryRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto, int start,int limit) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		regularTruckSignBillDto.setOrgCodes(orgCodes);
		//分页查询专线对发签单
		return regularTruckSignBillDao.queryRegularTruckSignBill(regularTruckSignBillDto, start, limit);
	}

	/**
	 * 查询总条数
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public Long queryCount(RegularTruckSignBillDto regularTruckSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		regularTruckSignBillDto.setOrgCodes(orgCodes);
		//查询总条数
		return  regularTruckSignBillDao.queryCount(regularTruckSignBillDto);		
	}

	/**
	 * 查询费用和提成
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public RegularTruckSignBillDto queryRegularTruckSignBillByFee(RegularTruckSignBillDto regularTruckSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		regularTruckSignBillDto.setOrgCodes(orgCodes);
		//查询费用和提成
		return regularTruckSignBillDao.queryRegularTruckSignBillByFee(regularTruckSignBillDto);
		 
	}

	/**
	 * 根据Id查询专线对发签单
	 * @author dp-liming
	 * @date 2013-1-2 上午11:00:26
	 */
	@Override
	public RegularTruckSignBillEntity queryRegularTruckSignBillById(String id) {
		//根据Id查询专线对发签单
		return  regularTruckSignBillDao.queryRegularTruckSignBillById(id);		
	}

	/****
	 * 计算费用提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:15:30
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IRegularTruckSignBillService#calculateRegularTruckSignBillFee(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto)
	 */
	@Override
	public RegularTruckSignBillEntity calculateRegularTruckSignBillFee(RegularTruckSignBillDto regularTruckSignBillDto) {
		//do nothing
		return null;
	}
	
	/**
	 * 创建cell
	 * @author 099197-foss-liming
	 * @date 2012-12-26 下午2:36:20
	 */
	private HSSFCell createHSSFCell(HSSFRow row, int columnIndex, String value) {
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(value);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}
	
	
	private HSSFRow createDataRow(HSSFSheet sheet, int rowIndex, RegularTruckSignBillEntity result) {
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//签单编号
		createHSSFCell(row, columnIndex, result.getSignBillNo());		
		columnIndex ++;
		
		//用车部门名称.
		createHSSFCell(row, columnIndex, result.getUseTruckOrgName());
		columnIndex ++;
		
		// 司机姓名
		createHSSFCell(row, columnIndex, result.getDriverName());
		columnIndex ++;		

		//日期.
		createHSSFCell(row, columnIndex, format.format(result.getSignBillDate()));
		columnIndex ++;
		
		//车牌号
		createHSSFCell(row, columnIndex, result.getVehicleNo());
		columnIndex ++;
		
		//车型
		createHSSFCell(row, columnIndex, result.getVehicleTypeLengthName());
		columnIndex ++;
		
		//线路名称
		createHSSFCell(row, columnIndex, result.getLineName()+"");
		columnIndex ++;
		
		//线路里程
		BigDecimal lineDistance = result.getLineDistance() == null ? BigDecimal.ZERO : result.getLineDistance();
		createHSSFCell(row, columnIndex, lineDistance + "");
		columnIndex ++;
		
		//体积
		BigDecimal volume = result.getVolume() == null ? BigDecimal.ZERO : result.getVolume();
		createHSSFCell(row, columnIndex, volume + "");
		columnIndex ++;
		
		//重量
		BigDecimal weight = result.getWeight() == null ? BigDecimal.ZERO : result.getWeight();
		createHSSFCell(row, columnIndex, weight + "");
		
		//返回结果
		return row;
	}
	
	/****
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:17:20
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IRegularTruckSignBillService#queryExportRegularTruckSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto)
	 */
	@Override
	public InputStream queryExportRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		regularTruckSignBillDto.setOrgCodes(orgCodes);
		List<RegularTruckSignBillEntity>  regularTruckSignBillList =regularTruckSignBillDao.queryExportRegularTruckSignBill(regularTruckSignBillDto);
		InputStream excelStream = null;		
		//总条数
		int dataSize = regularTruckSignBillList.size();//总条数
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
            LOGGER.info("导出");
			// 标题
     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, SignBillConstants.REGULARTRUCK_SHEET_NAME);
     		titleCell2.setCellStyle(style);
     		CellRangeAddress cellRangeAddress =new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++; 
			// head 2
			HSSFRow headRow2 = sheet.createRow(rowIndex);
			//HSSFCell headCell2 = null; 
     		for(int i = 0, len = SignBillConstants.REGULARTRUCK_ROW_HEADS.length; i<len; i++) {
     			createHSSFCell(headRow2, i, SignBillConstants.REGULARTRUCK_ROW_HEADS[i]);
     		}
			rowIndex++;
			/** 司机个数 */
			Integer driverCount = 0;
			/** 总线路里程 */
			BigDecimal lineDistanceTotal = new BigDecimal(0);
			/** 总体积 */
			BigDecimal volumeTotal = new BigDecimal(0);
			/** 总重量 */
			BigDecimal weightTotal = new BigDecimal(0);
			int startNo = index * (SignBillConstants.SHEET_SIZE - 1);
			int endNo = Math.min(startNo + SignBillConstants.SHEET_SIZE - 1,
					dataSize);
			Set<String> set = new HashSet<String>();// 司机
			List<String> listCount = new ArrayList<String>();// 新增所有的司机
			for (int i = startNo; i < endNo; i++) {
				RegularTruckSignBillEntity result = regularTruckSignBillList
						.get(i);
				// HSSFRow dataRow = sheet.createRow(rowIndex);
				createDataRow(sheet, rowIndex, regularTruckSignBillList.get(i));
				rowIndex++;
				// 累计
				// 毛重累加
				weightTotal = weightTotal.add(result.getWeight());
				// 设置计费重量
				volumeTotal = volumeTotal.add(result.getVolume());
				// 线路里程
				lineDistanceTotal = lineDistanceTotal.add(result
						.getLineDistance());
				listCount.add(result.getDriverCode());
			}
            set.addAll(listCount);   
            driverCount = set.size();
            //合计行
            HSSFRow totalRow = sheet.createRow(rowIndex);
            //合计
            createHSSFCell(totalRow, 0, "合计");
            //司机数:  
            createHSSFCell(totalRow, 2, "司机数:  "+String.valueOf(driverCount));  
            //线路里程合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, "线路里程合计: "+String.valueOf(lineDistanceTotal));
            //体积合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_4, "体积合计: "+String.valueOf(volumeTotal)); 
            //重量合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_5, "重量合计: "+String.valueOf(weightTotal));           
            rowIndex ++;                     
         }
		excelStream = excelExportUtil.inputToClient(workbook);
        return excelStream;
	}

	/****
	 * 根据车牌号查询专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:18:08
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IRegularTruckSignBillService#queryRegularTruckSignBillByVehicleNo(java.lang.String)
	 */
	@Override
	public RegularTruckSignBillEntity queryRegularTruckSignBillByVehicleNo(String vehicleNo) {
		//车辆信息
		VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		//专线对发签单Entity
		RegularTruckSignBillEntity entity=new RegularTruckSignBillEntity();
		if(vehicleAssociationDto!=null){
			//车型
			entity.setVehicleTypeLength(vehicleAssociationDto.getVehicleLengthCode()+"");
		}
		//返回结果
		return entity;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-5-7下午3:17:17
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setRegularTruckSignBillDao(IRegularTruckSignBillDao regularTruckSignBillDao) {
		this.regularTruckSignBillDao = regularTruckSignBillDao;
	}
}