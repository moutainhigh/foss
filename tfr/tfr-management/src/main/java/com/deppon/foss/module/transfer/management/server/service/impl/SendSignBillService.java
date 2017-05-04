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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/service/impl/SendSignBillService.java
 *  
 *  FILE NAME          :SendSignBillService.java
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
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao;
import com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.define.SignBillConstants;
import com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *  派送签单Service
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:32:47
 */
public class SendSignBillService implements ISendSignBillService {
	
	/**
	 * 
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(SendSignBillService.class);
	
	/**
	 * 
	 */
	private static final  BigDecimal BIGFEE=new BigDecimal(100);
	
	/**
	 * 派送签单Dao接口
	 */
	private ISendSignBillDao sendSignBillDao;
	
	/**
	 * 用来提供交互所有关于“车辆（公司、外请）”
	 * 的数据库对应数据访问复杂的Service接口：无SUC 
	 */
	private IVehicleService vehicleService;
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/****
	 * 派送排单Service接口
	 */
	private IDeliverbillService deliverbillService;
	
	/**
	 * 设置 用来提供交互所有关于“车辆（公司、外请）” 的数据库对应数据访问复杂的Service接口：无SUC.
	 *
	 * @param vehicleService the new 用来提供交互所有关于“车辆（公司、外请）” 的数据库对应数据访问复杂的Service接口：无SUC
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 设置 ** 派送排单Service接口.
	 *
	 * @param deliverbillService the new ** 派送排单Service接口
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
	/**
	 * 设置 派送签单Dao接口.
	 *
	 * @param sendSignBillDao the new 派送签单Dao接口
	 */
	public void setSendSignBillDao(ISendSignBillDao sendSignBillDao) {
		this.sendSignBillDao = sendSignBillDao;
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
	 * 新增派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#addSendSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity)
	 */
	@Override
	@Transactional	
	public int addSendSignBill(SendSignBillEntity sendSignBillEntity) {
		//上楼费
		sendSignBillEntity.setUpstairsFee(sendSignBillEntity.getUpstairsFee().multiply(BIGFEE));//上楼费
		//非进仓票数费用
		sendSignBillEntity.setNoInStockBillFee(sendSignBillEntity.getNoInStockBillFee().multiply(BIGFEE)); //非进仓票数费用
		//进仓票数费用
		sendSignBillEntity.setInStockBillFee(sendSignBillEntity.getInStockBillFee().multiply(BIGFEE));//进仓票数费用
		//司机实际提成总额
		sendSignBillEntity.setDriverRoyaltyAmount(sendSignBillEntity.getDriverRoyaltyAmount().multiply(BIGFEE));	//司机实际提成总额
		//用车费用划分
		sendSignBillEntity.setUseTruckFee(sendSignBillEntity.getUseTruckFee().multiply(BIGFEE));// 用车费用划分
		//其它费用
		sendSignBillEntity.setOtherFee(sendSignBillEntity.getOtherFee().multiply(BIGFEE)); //其它费用
		//上楼费提成
		sendSignBillEntity.setUpstairsFeeRoyalty(sendSignBillEntity.getUpstairsFeeRoyalty().multiply(BIGFEE));//上楼费提成
		//id
		sendSignBillEntity.setId(UUIDUtils.getUUID());
		//币种
		sendSignBillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//新增部门
		sendSignBillEntity.setOrgCode(FossUserContext.getCurrentDeptCode());
		//新增部门
		sendSignBillEntity.setOrgName(FossUserContext.getCurrentDept().getName());
		return sendSignBillDao.addSendSignBill(sendSignBillEntity);
	}

	/** 
	 * 删除派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#deleteSendSignBill(java.lang.String)
	 */
	@Override
	@Transactional	
	public int deleteSendSignBill(String id) {
		//删除派送签单
		return sendSignBillDao.deleteSendSignBill(id);
	}

	/** 
	 * 修改派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#updateSendSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity)
	 */
	@Override
	@Transactional	
	public int updateSendSignBill(SendSignBillEntity sendSignBillEntity) {
		if (sendSignBillEntity.getUpstairsFee()!=null) {
			//上楼费
			sendSignBillEntity.setUpstairsFee(sendSignBillEntity.getUpstairsFee().multiply(BIGFEE));//上楼费
		}
		//非进仓票数费用
		sendSignBillEntity.setNoInStockBillFee(sendSignBillEntity.getNoInStockBillFee().multiply(BIGFEE)); //非进仓票数费用
		//进仓票数费用
		sendSignBillEntity.setInStockBillFee(sendSignBillEntity.getInStockBillFee().multiply(BIGFEE));//进仓票数费用
		//司机实际提成总额
		sendSignBillEntity.setDriverRoyaltyAmount(sendSignBillEntity.getDriverRoyaltyAmount().multiply(BIGFEE));	//司机实际提成总额
		//用车费用划分
		sendSignBillEntity.setUseTruckFee(sendSignBillEntity.getUseTruckFee().multiply(BIGFEE));// 用车费用划分
		//其它费用
		sendSignBillEntity.setOtherFee(sendSignBillEntity.getOtherFee().multiply(BIGFEE)); //其它费用
		//上楼费提成
		sendSignBillEntity.setUpstairsFeeRoyalty(sendSignBillEntity.getUpstairsFeeRoyalty().multiply(BIGFEE));//上楼费提成
		//修改派送签单
		return sendSignBillDao.updateSendSignBill(sendSignBillEntity);
	}

	/** 
	 * 查询派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#querySendSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto, int, int)
	 */
	@Override
	public List<SendSignBillEntity> querySendSignBill(SendSignBillDto sendSignBillDto, int start, int limit) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		sendSignBillDto.setOrgCodes(orgCodes);
		//查询派送签单
		return sendSignBillDao.querySendSignBill(sendSignBillDto, start, limit);
	}

	/** 
	 * 查询费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#querySendSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public SendSignBillDto querySendSignBillByFee(SendSignBillDto sendSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		sendSignBillDto.setOrgCodes(orgCodes);
		return sendSignBillDao.querySendSignBillByFee(sendSignBillDto);
	}

	/** 
	 * 查询派送签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public Long queryCount(SendSignBillDto sendSignBillDto) {		
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		sendSignBillDto.setOrgCodes(orgCodes);
		//查询派送签单总数
		return sendSignBillDao.queryCount(sendSignBillDto);
	}

	/** 
	 * 根据Id查询派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#querySendSignBillById(java.lang.String)
	 */
	@Override
	public SendSignBillEntity querySendSignBillById(String id) {
		//根据Id查询派送签单
		return sendSignBillDao.querySendSignBillById(id);
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
	
	
	/**
	 * 
	 * 创建excel中的一行
	 * @param sheet 
	 * @param rowIndex 
	 * @param result 
	 * @return 
	 */
	private HSSFRow createDataRow(HSSFSheet sheet, int rowIndex, SendSignBillEntity result) {
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//格式化日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//签单编号
		createHSSFCell(row, columnIndex, result.getSignBillNo());		
		columnIndex ++;
		
		//用车部门名称
		createHSSFCell(row, columnIndex, result.getUseTruckOrgName());
		columnIndex ++;
		
		//司机姓名.
		createHSSFCell(row, columnIndex, result.getDriverName());
		columnIndex ++;		
		
		//日期
		createHSSFCell(row, columnIndex, format.format(result.getSignBillDate()));
		columnIndex ++;
		
		//车牌号
		createHSSFCell(row, columnIndex, result.getVehicleNo());
		columnIndex ++;
		
		//车型
		createHSSFCell(row, columnIndex, result.getVehicleTypeLengthName());
		columnIndex ++;
		
		//派送票数
		Long sendBillQty = result.getSendBillQty() == null ? 0 : result.getSendBillQty();
		createHSSFCell(row, columnIndex, sendBillQty + "");
		columnIndex ++;
		
		//体积
		BigDecimal volume = result.getVolume() == null ? BigDecimal.ZERO : result.getVolume();
		createHSSFCell(row, columnIndex, volume + "");
		columnIndex ++;
		
		//重量
		BigDecimal weight = result.getWeight() == null ? BigDecimal.ZERO : result.getWeight();
		createHSSFCell(row, columnIndex,  weight + "");
		columnIndex ++;
		
		//进仓票数
		BigDecimal inStockBillQty = result.getInStockBillQty() == null ? BigDecimal.ZERO : result.getInStockBillQty();
		createHSSFCell(row, columnIndex, inStockBillQty + "");
		columnIndex ++;
		
		//上楼票数
		BigDecimal upstairsBillQty = result.getUpstairsBillQty() == null ? BigDecimal.ZERO : result.getUpstairsBillQty();
		createHSSFCell(row, columnIndex, upstairsBillQty + "");
		columnIndex ++;		
		
		//上楼费
		BigDecimal upstairsFee = result.getUpstairsFee() == null ? BigDecimal.ZERO : result.getUpstairsFee();
		createHSSFCell(row, columnIndex, upstairsFee + "");
		columnIndex ++;
				
		// 里程
		BigDecimal distance = result.getDistance() == null ? BigDecimal.ZERO : result.getDistance();
		createHSSFCell(row, columnIndex, distance + "");
		columnIndex ++;
		
		//拉回票数
		Long haulBackBillQty = result.getHaulBackBillQty() == null ? 0 : result.getHaulBackBillQty();
		createHSSFCell(row, columnIndex, haulBackBillQty + "");
		columnIndex ++;
		
		if (StringUtils.equals(SignBillConstants.IS_SING_BILL_CODE, result.getIsSingleSend())) {
			createHSSFCell(row, columnIndex, SignBillConstants.IS_SING_BILL_Name);
		}else{
			createHSSFCell(row, columnIndex, SignBillConstants.IS_NOT_SING_BILL_Name);
		}	
		return row;
	}
	
	/** 
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#queryExportSendSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public InputStream queryExportSendSignBill(SendSignBillDto sendSignBillDto) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		List<String> orgCodes = queryChildDept(orgCode);
		sendSignBillDto.setOrgCodes(orgCodes);
		List<SendSignBillEntity> sendSignBillList = sendSignBillDao.queryExportSendSignBill(sendSignBillDto);
		InputStream excelStream = null;
		
		//总条数
		int dataSize = sendSignBillList.size();//总条数
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
			// title2
     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, SignBillConstants.SEND_SHEET_NAME);
     		titleCell2.setCellStyle(style);
     		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++;
			// head 2
			HSSFRow headRow2 = sheet.createRow(rowIndex);
			//HSSFCell headCell2 = null; 
     		for(int i = 0, len = SignBillConstants.SEND_ROW_HEADS.length; i<len; i++) {
     			createHSSFCell(headRow2, i, SignBillConstants.SEND_ROW_HEADS[i]);
     		}
     		rowIndex++; 
    		/** 司机个数 */
    		 Integer  driverCount=0;    		
    	   /** 总派送票数 */
    		 Long sendBillQtyTotal = 0L;
    	    /** 总体积  */
    	     BigDecimal volumeTotal = new BigDecimal(0);
    	    /** 总重量*/
    	     BigDecimal weightTotal=  new BigDecimal(0); 
    		/** 总进仓票数*/
    	     BigDecimal inStockBillQtyTotal = new BigDecimal(0);
    		/**总上楼费 */
    		 BigDecimal upstairsFeeTotal=  new BigDecimal(0);      		 
    		/** 总里程*/
    		BigDecimal distanceTotal = new BigDecimal(0);     		
    		int startNo = index * (SignBillConstants.SHEET_SIZE - 1);
            int endNo = Math.min(startNo + SignBillConstants.SHEET_SIZE - 1, dataSize);
            Set<String> set=new HashSet<String>();//司机
            List<String>  listCount=new ArrayList<String>();//新增所有的司机
            for (int i = startNo; i < endNo; i++) {
				SendSignBillEntity result = sendSignBillList.get(i);
				// HSSFRow dataRow = sheet.createRow(rowIndex);
				createDataRow(sheet, rowIndex, sendSignBillList.get(i));
				rowIndex++;
				// 累计
				// 毛重累加
				if(result.getWeight()!=null){
					weightTotal = weightTotal.add(result.getWeight());
				}				
				// 设置计费重量				
				if(result.getVolume()!=null){
					volumeTotal = volumeTotal.add(result.getVolume());
				}				
				sendBillQtyTotal = sendBillQtyTotal + result.getSendBillQty();					
				if(result.getInStockBillFee()!=null){
					inStockBillQtyTotal=inStockBillQtyTotal.add(result.getInStockBillFee());	
				}				
				if(result.getUpstairsFee()!=null){
					upstairsFeeTotal = upstairsFeeTotal.add(result.getUpstairsFee());
				}				
				if(result.getDistance()!=null){
					distanceTotal = distanceTotal.add(result.getDistance());
				}
				
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
            //派送票数合计: 
            createHSSFCell(totalRow, 2, "派送票数合计: "+String.valueOf(sendBillQtyTotal));
            //体积合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, "体积合计: "+String.valueOf(volumeTotal));           
            //重量合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_4, "重量合计: "+String.valueOf(weightTotal));
            //进仓票数合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_5, "进仓票数合计: "+String.valueOf(inStockBillQtyTotal));
            //上楼费合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_6, "上楼费合计: "+String.valueOf(upstairsFeeTotal));         
            //里程合计: 
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_7, "里程合计: "+String.valueOf(distanceTotal));
            rowIndex ++;  
         }
        excelStream = excelExportUtil.inputToClient(workbook);		
        return excelStream;
	}

	/** 
	 * 查询费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#calculateSendSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public SendSignBillEntity calculateSendSignBillByFee(SendSignBillDto sendSignBillDto) {
		//do nothing
		return null;
	}

	/** 
	 * 根据车牌号查询派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#querySendSignBillByVehicleNo(java.lang.String)
	 */
	@Override
	public SendSignBillEntity querySendSignBillByVehicleNo(String vehicleNo) {
		//车辆信息
		VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		//派送签单entity
		SendSignBillEntity sendSignBillEntity=new SendSignBillEntity();
		if(vehicleAssociationDto!=null){
			//设置车型
			sendSignBillEntity.setVehicleTypeLength(vehicleAssociationDto.getVehicleLengthCode()+"");
		}
		//返回结果
		return sendSignBillEntity;
	}

	/** 
	 * 根据派送单号查询派送签单信息
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:19:21
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService#queryDeliveryInfoByDeliverbillNo(java.lang.String)
	 */
	@Override
	public SendSignBillEntity queryDeliveryInfoByDeliverbillNo(String signBillNo) {
		//派送单Dto
		DeliverbillDto dto=deliverbillService.queryDeliveryInfoByDeliverbillNo(signBillNo);
		SendSignBillEntity entity=null;
		if (dto!=null) {
			entity=new SendSignBillEntity();
			//派送部门编号
			entity.setUseTruckOrgCode(dto.getCreateOrgCode());//派送部门编号
			//派送部门名称
			entity.setUseTruckOrgName(dto.getCreateOrgName());//派送部门名称
			//车牌号
			entity.setVehicleNo(dto.getVehicleNo());//车牌号
			//司机编号
			entity.setDriverCode(dto.getDriverCode());//司机编号
			//司机姓名
			entity.setDriverName(dto.getDriverName());//司机姓名
			//派送日期
			entity.setSignBillDate(dto.getOperateTime());//派送日期
			//派送成功票数
			entity.setSendBillQty(dto.getDeliverWaybillQty());//派送成功票数
			//派送拉回票数
			entity.setHaulBackBillQty(dto.getPullbackWaybillQty());//派送拉回票数
			//体积
			entity.setVolume(dto.getVolumeTotal());//体积
			//重量
			entity.setWeight(dto.getWeightTotal());//重量         			
			if(StringUtils.isNotBlank(dto.getVehicleNo())){
				//车辆信息
				VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(dto.getVehicleNo());//根据车牌号找车型
				LOGGER.info("");
				//设置车型
				entity.setVehicleTypeLength(vehicleAssociationDto.getVehicleLengthValue().toEngineeringString());//车型
			}			
		}
		//返回结果
		return entity;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-5-7下午3:11:22
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
}