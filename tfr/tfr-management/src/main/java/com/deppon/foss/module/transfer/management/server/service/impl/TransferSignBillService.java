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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/service/impl/TransferSignBillService.java
 *  
 *  FILE NAME          :TransferSignBillService.java
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
import com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao;
import com.deppon.foss.module.transfer.management.api.server.service.ITransferSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.define.SignBillConstants;
import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *  转货车签单Service
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public class TransferSignBillService implements ITransferSignBillService {
	
	/**
	 * 
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(TransferSignBillService.class);
	/** 转货车签单Dao  */
	private ITransferSignBillDao transferSignBillDao;
	/** 车辆信息Service */
	private IVehicleService vehicleService;
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 数字 */
	private static final BigDecimal BIGFEE = new BigDecimal(100);
	
	/**
	 * 设置 车辆信息Service.
	 *
	 * @param vehicleService the new 车辆信息Service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 *  新增转货签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	@Transactional	
	public int addTransferSignBill(TransferSignBillEntity transferSignBillEntity) {
		TransferSignBillEntity entity = new TransferSignBillEntity();		
		LOGGER.info("新增转货签单");
		// Id
		entity.setId(UUIDUtils.getUUID());
		//新增部门
		entity.setOrgCode(FossUserContext.getCurrentDeptCode());
		//新增部门
		entity.setOrgName(FossUserContext.getCurrentDept().getName());
		// 用车部门名称
		entity.setUseTruckOrgName(transferSignBillEntity.getUseTruckOrgName());
		// 司机
		entity.setDriverCode(transferSignBillEntity.getDriverCode());
		// 签单编号
		entity.setSignBillNo(transferSignBillEntity.getSignBillNo());
		// 用车部门
		entity.setUseTruckOrgCode(transferSignBillEntity.getUseTruckOrgCode());
		// 日期
		entity.setSignBillDate(transferSignBillEntity.getSignBillDate());
		// 司机姓名
		entity.setDriverName(transferSignBillEntity.getDriverName());
		// 是否第一个部门转货
		entity.setIsFirstTransfer(transferSignBillEntity.getIsFirstTransfer());
		// 车牌号
		entity.setVehicleNo(transferSignBillEntity.getVehicleNo());
		// 车型
		entity.setVehicleTypeLength(transferSignBillEntity.getVehicleTypeLength());
		// 转货里程
		entity.setTransferDistance(transferSignBillEntity.getTransferDistance());
		// 体积
		entity.setVolume(transferSignBillEntity.getVolume());
		// 重量
		entity.setWeight(transferSignBillEntity.getWeight());
		// 用车时间
		entity.setUseTruckDuration(transferSignBillEntity.getUseTruckDuration());
		// 转货提成
		entity.setTransferRoyalty(transferSignBillEntity.getTransferRoyalty().multiply(BIGFEE));
		// 用车费用划分
		entity.setUseTruckFee(transferSignBillEntity.getUseTruckFee().multiply(BIGFEE));
		// 币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//单据类型-营业部单据:STORE|车队单据:MOTORCADE
		entity.setTransferType(transferSignBillEntity.getTransferType());
		//交接单编号
		entity.setHandOverNo(transferSignBillEntity.getHandOverNo());
		//出发部门code
		entity.setOrigOrgCode(transferSignBillEntity.getOrigOrgCode());
		//出发部门name
		entity.setOrigOrgName(transferSignBillEntity.getOrigOrgName());
		//目的部门code
		entity.setDestOrgCode(transferSignBillEntity.getDestOrgCode());
		//目的部门name
		entity.setDestOrgName(transferSignBillEntity.getDestOrgName());
		//所属车队code
		entity.setMotorcadeCode(transferSignBillEntity.getMotorcadeCode());
		//所属车队name
		entity.setMotorcadeName(transferSignBillEntity.getMotorcadeName());
		//营业部签字
		entity.setStoreSign(transferSignBillEntity.getStoreSign());
		//约定用车时间
		entity.setArrangeUseTruckDate(transferSignBillEntity.getArrangeUseTruckDate());
		//卸车结束时间
		entity.setUnloadEndDate(transferSignBillEntity.getUnloadEndDate());
		//出发时间
		entity.setOrigDate(transferSignBillEntity.getOrigDate());
		//到达时间
		entity.setOrigDate(transferSignBillEntity.getOrigDate());
		
		// 新增转货车签单
		return transferSignBillDao.addTransferSignBill(entity);
	}

	/**
	 *   删除转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	@Transactional	
	public int deleteTransferSignBill(String id) {
		// 删除转货车签单
		return transferSignBillDao.deleteTransferSignBill(id);
	}

	/**
	 *   修改转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	@Transactional	
	public int updateTransferSignBill(TransferSignBillEntity transferSignBillEntity) {	
		//转货提成
		transferSignBillEntity.setTransferRoyalty(transferSignBillEntity.getTransferRoyalty().multiply(BIGFEE));
		//用车费用划分
		transferSignBillEntity.setUseTruckFee(transferSignBillEntity.getUseTruckFee().multiply(BIGFEE));
		// 修改转货车签单
		return transferSignBillDao.updateTransferSignBill(transferSignBillEntity);
	}

	
	/**
	 *   根据Id查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public TransferSignBillEntity queryTransferSignBillById(String id) {		
		// 根据Id查询转货车签单
		return transferSignBillDao.queryTransferSignBillById(id);
	}
	
	/**
	 * 设置 转货车签单Dao.
	 *
	 * @param transferSignBillDao the new 转货车签单Dao
	 */
	public void setTransferSignBillDao(ITransferSignBillDao transferSignBillDao) {
		this.transferSignBillDao = transferSignBillDao;
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
	 *   查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public List<TransferSignBillEntity> queryTransferSignBill(TransferSignBillDto transferSignBillDto, int start, int limit) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		transferSignBillDto.setOrgCodes(orgCodes);
		//查询转货车签单
		return transferSignBillDao.queryTransferSignBill(transferSignBillDto, start, limit);
	}

	/**
	 *   查询转货车签单总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public Long queryCount(TransferSignBillDto transferSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		transferSignBillDto.setOrgCodes(orgCodes);
		// 查询转货车签单总数
		return transferSignBillDao.queryCount(transferSignBillDto);
	}

	/**
	 *   查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public TransferSignBillDto queryTransferSignBillByFee(TransferSignBillDto transferSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		transferSignBillDto.setOrgCodes(orgCodes);
		// 查询费用和提成
		return transferSignBillDao.queryTransferSignBillByFee(transferSignBillDto);
	}	
	
	/**
	 * 创建cell
	 * @author 099197-foss-liming
	 * @date 2012-12-26 下午2:36:20
	 */
	private HSSFCell createHSSFCell(HSSFRow row, int columnIndex, String value) {
		//创建cell
		HSSFCell cell = row.createCell(columnIndex);
		//放入值
		cell.setCellValue(value);
		//放入类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}
	
	/**
	 * 
	 *
	 * @param sheet 
	 * @param rowIndex 
	 * @param result 
	 * @return 
	 */
	private HSSFRow createDataRow(HSSFSheet sheet, int rowIndex, TransferSignBillEntity result) {
		//得到表格行
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//日期格式化
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//签单编号
		createHSSFCell(row, columnIndex, result.getSignBillNo());		
		columnIndex ++;
		//签单编号
		createHSSFCell(row, columnIndex, result.getUseTruckOrgName());
		columnIndex ++;
		
		//司机姓名
		createHSSFCell(row, columnIndex, result.getDriverName());
		columnIndex ++;
		
		// 日期
		createHSSFCell(row, columnIndex, format.format(result.getSignBillDate()));
		columnIndex ++;			
		
		// 日期
		createHSSFCell(row, columnIndex, result.getVehicleNo());
		columnIndex ++;
		
		// 车型 名称
		createHSSFCell(row, columnIndex, result.getVehicleTypeLengthName());
		columnIndex ++;
			
		// 车型 名称
		createHSSFCell(row, columnIndex, result.getTransferDistance()+"");
		columnIndex ++;
		
		//转货里程
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
		
		//是否第一个部门转货.
		if (StringUtils.equals(SignBillConstants.IS_SING_BILL_CODE, result.getIsFirstTransfer())) {
			createHSSFCell(row, columnIndex, SignBillConstants.IS_SING_BILL_Name);
		}else{
			createHSSFCell(row, columnIndex, SignBillConstants.IS_NOT_SING_BILL_Name);
		}		
		columnIndex ++;
		
		//转货提成
		BigDecimal transferRoyalty = result.getTransferRoyalty() == null ? BigDecimal.ZERO : result.getTransferRoyalty();
		createHSSFCell(row, columnIndex, transferRoyalty + "");
		columnIndex ++;
		
		// 用车费用
		BigDecimal useTruckFee = result.getUseTruckFee() == null ? BigDecimal.ZERO : result.getUseTruckFee();
		createHSSFCell(row, columnIndex, useTruckFee + "");		
	
		return row;
	}
	
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public InputStream queryExportTransferSignBill(TransferSignBillDto transferSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		transferSignBillDto.setOrgCodes(orgCodes);
		//得到签单信息
		List<TransferSignBillEntity> transferSignBillList= transferSignBillDao.queryExportTransferSignBill(transferSignBillDto);
		InputStream excelStream = null;
		 LOGGER.info("统计");
		//总条数
		int dataSize = transferSignBillList.size();
		
		double sheetNo = Math.ceil(dataSize / SignBillConstants.SHEET_SIZE);
		ExcelExport excelExportUtil = new ExcelExport();
		HSSFWorkbook workbook = excelExportUtil.createWorkBook();
		// 样式对象   
	    HSSFCellStyle style = workbook.createCellStyle();    
	 // 垂直 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
     // 水平  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        //循环取值
        for(int index = 0; index <= sheetNo; index++) {
  			int rowIndex = 0;
  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "签单" + index);
            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_25);
           
			// 标题
     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
     	   //签单名称
     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, SignBillConstants.TRANSFER_SHEET_NAME);
     		titleCell2.setCellStyle(style);
     		CellRangeAddress cellRangeAddress  = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++;     		     		
			//头部
			HSSFRow headRow2 = sheet.createRow(rowIndex);
			
     		for(int i = 0, len = SignBillConstants.TRANSFER_ROW_HEADS.length; i<len; i++) {
     			createHSSFCell(headRow2, i, SignBillConstants.TRANSFER_ROW_HEADS[i]);
     		}
     		rowIndex++; 
     		/**
    		 * 司机个数
    		 */
    		 Integer  driverCount=0;      		 
    		/**
		    * 总的转货里程
		    */
		    BigDecimal transferDistanceTotal=new BigDecimal(0);
		    /**
    	     * 总体积
    	     */
    	     BigDecimal volumeTotal = new BigDecimal(0);
    	    /**
    	     * 总重量
    	     */
    	     BigDecimal weightTotal=  new BigDecimal(0);  

		    /**
		     * 总的用车时间
		     */
		     BigDecimal useTruckDurationTotal=new BigDecimal(0);
    		 
    		
    		int startNo = index * (SignBillConstants.SHEET_SIZE - 1);
            int endNo = Math.min(startNo + SignBillConstants.SHEET_SIZE - 1, dataSize);
          //司机
            Set<String> set=new HashSet<String>();
          //新增所有的司机
            List<String>  listCount=new ArrayList<String>();
            for (int i = startNo; i < endNo; i++) {
            	//得到结果
            	TransferSignBillEntity result = transferSignBillList.get(i);
            	//赋值
     			createDataRow(sheet, rowIndex, transferSignBillList.get(i));
     			rowIndex++;
     			// 累计
    			// 毛重累加
    			weightTotal = weightTotal.add(result.getWeight());
    			// 设置计费重量
    			volumeTotal =volumeTotal.add(result.getVolume());    			
    			//总的转货里程
    			transferDistanceTotal= transferDistanceTotal.add(result.getTransferDistance());  
    			//用车时间
    			useTruckDurationTotal= useTruckDurationTotal.add(result.getUseTruckDuration());     			
    			 listCount.add(result.getDriverCode());
            }
            set.addAll(listCount);   
            driverCount = set.size();
            //合计行
            HSSFRow totalRow = sheet.createRow(rowIndex);
            ///合计
            createHSSFCell(totalRow, 0, "合计");
            //司机数
            createHSSFCell(totalRow, 1, "司机数"+String.valueOf(driverCount));
            //转货里程合计
            createHSSFCell(totalRow, 2, "转货里程合计"+String.valueOf(transferDistanceTotal));
            //体积合计
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, "体积合计"+String.valueOf(volumeTotal));
            //送货费、到付费、代收货款
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_4, "重量合计"+String.valueOf(weightTotal));
            //用车时间
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_5,"用车时间"+ String.valueOf(useTruckDurationTotal));
            rowIndex ++;
         }
		excelStream = excelExportUtil.inputToClient(workbook);
        return excelStream;
	}

	/**
	 *  根据车牌号查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public TransferSignBillEntity queryTransferSignBillByVehicleNo(String vehicleNo) {
		//车辆信息
		VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		//转货车签单entity
		TransferSignBillEntity entity=new TransferSignBillEntity();
		if(vehicleAssociationDto!=null){
			//设置车型
			entity.setVehicleTypeLength(vehicleAssociationDto.getVehicleLengthCode()+"");
		}
		//返回结果
		return entity;
	}

	/**
	 * 查询费用
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	@Override
	public TransferSignBillEntity calculateTransferSignBillByFee(TransferSignBillDto transferSignBillDto) {
		//do nothing
		return null;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-5-2下午2:45:58
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}