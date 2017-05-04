/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
 * 
 * 
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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/JointTicketAndPickupAndChangeListCallEdiService.java
 *  
 *  FILE NAME          :JointTicketAndPickupAndChangeListCallEdiService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 SR-1	合大票清单excel导出文件模板参见SUC-250制作合大票清单的导出模板；
 
 SR-2	标题信息为合大票清单的代理名称；

 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.shared.util.file.IOUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IJointTicketAndPickupAndChangeListCallEdiService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirPickupBillJointlargeListException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirTransPickupBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToEDIService;
import com.deppon.foss.module.transfer.common.api.shared.dto.UploadJointTicketDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * 合大票、中转提货清单、变更清单调用Edi实现
 * @author 099197-foss-zhoudejun
 * @date 2012-12-27 上午10:31:56
 */
public class JointTicketAndPickupAndChangeListCallEdiService implements
		IJointTicketAndPickupAndChangeListCallEdiService {
	/**日志*/
	private static final Logger LOGGER = LoggerFactory.getLogger(JointTicketAndPickupAndChangeListCallEdiService.class);
	 /** 中转提货清单service*/
	private IAirTransPickupBillService airTransPickupBillService;
	
	/** 注入中转提货清单dao */
	private IAirTransPickupBillDao airTransPickupBillDao;
	
	/** 注入edi接口*/
	private IFOSSToEDIService fossToEDIService;
	
	/**dto*/
	private UploadJointTicketDto uploadJointTicketDto = new UploadJointTicketDto();
	
	/**
	 * 创建cell.
	 * @param row the row
	 * @param columnIndex the column index
	 * @param value the value
	 * @return the hSSF cell
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 下午2:36:20
	 */
	private HSSFCell createHSSFCell(HSSFRow row, int columnIndex, String value) {
		//创建单元格
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(value);
		//设置单元格类型string
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}
	
	/**
	 * 处理和大票行数据
	 * @param sheet 工作薄
	 * @param rowIndex 行索引
	 * @param result 清单信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:52:59
	 */
	private HSSFRow createPickupCallDataRow(HSSFSheet sheet, int rowIndex, AirPickupbillDetailEntity result) {
		//创建行
		HSSFRow row = sheet.createRow(rowIndex);
		//列索引从0开始
		int columnIndex = 0;
		//创建单元格指定行第1列
		createHSSFCell(row, columnIndex, result.getWaybillNo());
		columnIndex ++;
		//创建单元格指定行第2列
		createHSSFCell(row, columnIndex, result.getAirPickQty().toString());
		columnIndex ++;
		//创建单元格指定行第3列
		createHSSFCell(row, columnIndex, result.getWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第4列
		createHSSFCell(row, columnIndex, result.getBillingWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第5列
		createHSSFCell(row, columnIndex, result.getArrvRegionName());
		columnIndex ++;
		//创建单元格指定行第6列
		createHSSFCell(row, columnIndex, result.getGoodsName());
		columnIndex ++;
		//创建单元格指定行第7列
		String receiverInfo = "";
		if(result.getReceiverName() != null){
			receiverInfo = receiverInfo + result.getReceiverName();
		}
		if(result.getReceiverAddress() != null){
			receiverInfo = receiverInfo + "/" + result.getReceiverAddress();
		}
		if(result.getReceiverContactPhone() != null){
			receiverInfo = receiverInfo + "/" + result.getReceiverContactPhone();
		}
		createHSSFCell(row, columnIndex, receiverInfo);
		columnIndex ++;
		//创建单元格指定行第8列
		createHSSFCell(row, columnIndex, result.getDeliverFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第9列
		createHSSFCell(row, columnIndex, result.getArrivalFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第10列
		createHSSFCell(row, columnIndex, result.getCollectionFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第11列
		createHSSFCell(row, columnIndex, result.getPickupType());
		columnIndex ++;
		
		createHSSFCell(row, columnIndex, result.getNotes());
		
		return row;
	}
	
	/**
	 * 处理转提货清单行数据
	 * @param sheet 工作薄
	 * @param rowIndex 行索引
	 * @param result 清单信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:52:59
	 */
	private HSSFRow createTranPickupCallDataRow(HSSFSheet sheet, int rowIndex, AirTransPickupDetailEntity result) {
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//创建单元格指定行第1列
		createHSSFCell(row, columnIndex, result.getWaybillNo());
		columnIndex ++;
		//创建单元格指定行第2列
		createHSSFCell(row, columnIndex, result.getGoodsQty().toString());
		columnIndex ++;
		//创建单元格指定行第3列
		createHSSFCell(row, columnIndex, result.getWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第4列
		createHSSFCell(row, columnIndex, result.getBillingWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第5列
		createHSSFCell(row, columnIndex, result.getArrvRegionName());
		columnIndex ++;
		//创建单元格指定行第6列
		createHSSFCell(row, columnIndex, result.getGoodsName());
		columnIndex ++;
		//创建单元格指定行第7列
		createHSSFCell(row, columnIndex, result.getReceiverName());
		columnIndex ++;
		//创建单元格指定行第8列
		createHSSFCell(row, columnIndex, result.getDeliverFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第9列
		createHSSFCell(row, columnIndex, result.getArrivalFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第10列
		createHSSFCell(row, columnIndex, result.getCollectionFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第11列
		createHSSFCell(row, columnIndex, result.getPickupType());
		columnIndex ++;
		
		createHSSFCell(row, columnIndex, result.getNotes());
		
		return row;
	}
	
	/**
	 * 创建变更清单行数据
	 * @param sheet 工作薄
	 * @param rowIndex 行索引
	 * @param result 清单信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:52:59
	 */
	private HSSFRow createChangeListCallDataRow(HSSFSheet sheet, int rowIndex, AirPickupbillDetailEntity result) {
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//创建单元格指定行第1列
		createHSSFCell(row, columnIndex, result.getWaybillNo());
		columnIndex ++;
		//创建单元格指定行第2列
		createHSSFCell(row, columnIndex, result.getGoodsQty().toString());
		columnIndex ++;
		//创建单元格指定行第3列
		createHSSFCell(row, columnIndex, result.getWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第4列
		createHSSFCell(row, columnIndex, result.getBillingWeight().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第5列
		createHSSFCell(row, columnIndex, result.getArrvRegionName());
		columnIndex ++;
		//创建单元格指定行第6列
		createHSSFCell(row, columnIndex, result.getGoodsName());
		columnIndex ++;
		//创建单元格指定行第7列
		createHSSFCell(row, columnIndex, result.getReceiverName());
		columnIndex ++;
		//创建单元格指定行第8列
		createHSSFCell(row, columnIndex, result.getDeliverFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第9列
		createHSSFCell(row, columnIndex, result.getArrivalFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第10列
		createHSSFCell(row, columnIndex, result.getCollectionFee().toEngineeringString());
		columnIndex ++;
		//创建单元格指定行第11列
		createHSSFCell(row, columnIndex, result.getPickupType());
		columnIndex ++;
		
		createHSSFCell(row, columnIndex, result.getNotes());
		
		return row;
	}
	
	/**
	 * 合大票Edi接口
	 * @param idsList
	 * @param callIsNotEdiFlag
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:33:36
	 */
	@Override
	public InputStream uploadPickupCallEdi(List<String> idsList , String airWaybillNo ,String callIsNotEdiFlag){
		AirPickupbillEntity airPickupbillEntity = queryAirPickupbillEntity(airWaybillNo);
		if(airPickupbillEntity==null){
			LOGGER.info("上传edi:合票清单为空");
		}
		
		List<AirPickupbillDetailEntity> detailList = airTransPickupBillDao.queryAirPickupbillDetailList(airWaybillNo);
		if(CollectionUtils.isEmpty(detailList)){
			LOGGER.info("上传edi:合票清单明细为空");
		}
		ExcelExport excelExportUtil = new ExcelExport();
		HSSFWorkbook workbook = excelExportUtil.createWorkBook();
  		int dataSize = detailList.size();
  		int sheetSize = ConstantsNumberSonar.SONAR_NUMBER_5000;
  		
  		/**
  		 * @desc 使用BigDecimal代替之前的除法运算
  		 * @author wqh
  		 * @date 2013-08-05
  		 * */
  	    BigDecimal bdataSize=new BigDecimal(dataSize);
  	    BigDecimal bsheetSize=new BigDecimal(sheetSize);
  	    BigDecimal bresult=bdataSize.divide(bsheetSize).setScale(0, RoundingMode.UP);
  	    double sheetNo=bresult.doubleValue();
  		
  		//double sheetNo = Math.ceil(dataSize / sheetSize);

  		String title1 =  "TO:" + airPickupbillEntity.getDestOrgName();
  		
  		//模板中根据当前空运总调为广州空运总调则为"德 邦 物 流 合 票 提 货 清 单（广州）"
  		//或者"德 邦 物 流 合 票 提 货 清 单（深圳）"
  		//更好的设计是在数据字典表中维护部门code和城市名称的对应关系
  		//然后从缓存获取封装成map，此处直接get即可
  		//目测德邦几年内空运发展进度比较小，变化不大，故采用此方法
  		String cityName = "";
  		int num1 = airPickupbillEntity.getOrigOrgName().indexOf("广州");
  		int num2 = airPickupbillEntity.getOrigOrgName().indexOf("深圳");
  		int num3 = airPickupbillEntity.getOrigOrgName().indexOf("上海");
  		if(num1 != -1){
  			cityName = "广州";
  		}else if(num2 != -1){
  			cityName = "深圳";
  		}else if(num3 != -1){
  			cityName = "上海";
  		}else{
  		//无操作
  		}	
  		
  	    //定义标题
  		String title2 = "德邦物流合票提货清单(" + cityName + ")" ;
  		
  		// head1
  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  		String transferDate = format.format(airPickupbillEntity.getFlightDate());
  		String[] heads = { "提货单号",
  				airPickupbillEntity.getAirWaybillNo(), "", "航班号",
  				airPickupbillEntity.getFlightNo(), "", "航班日期",
				transferDate };
  		
  		// head 2
		String[] columnNames = { "单号", "件数", "毛重", "重量", "目的站", "品名", "收货人",
				"派送费", "到付费", "代收款", "提货方式", "备注" };
        HSSFCellStyle style = workbook.createCellStyle(); // 样式对象      
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直     
 		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平 居中    
  		for(int index = 0; index <= sheetNo; index++) {
  			int rowIndex = 0;
  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "空运" + index);
            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_25);
            
            // title1
     		HSSFRow titleRow1 = sheet.createRow(rowIndex);
     		createHSSFCell(titleRow1, 0, title1);
     		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++;
     		
			// title2
     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, title2);
     		titleCell2.setCellStyle(style);
     		cellRangeAddress = new CellRangeAddress(1, 1, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
			sheet.addMergedRegion(cellRangeAddress);
			rowIndex ++;
     		
			// head 1
			HSSFRow headRow1 = sheet.createRow(rowIndex);
			//HSSFCell headCell1 = null; 
     		for(int i = 0, len = heads.length; i<len; i++) {
     			createHSSFCell(headRow1, i, heads[i]);
     		}
     		rowIndex++;
     		
			// head 2
			HSSFRow headRow2 = sheet.createRow(rowIndex);
			//HSSFCell headCell2 = null; 
     		for(int i = 0, len = columnNames.length; i<len; i++) {
     			createHSSFCell(headRow2, i, columnNames[i]);
     		}
     		rowIndex++;
     		
     		// 件数合计
    		int goodsQtyTotal = 0;
    		// 毛重合计
    		BigDecimal weightTotal =BigDecimal.ZERO;
    		// 计费重量合计
    		BigDecimal billingWeightTotal = BigDecimal.ZERO;
    		//派送费合计
    		BigDecimal deliverFeeTotal = BigDecimal.ZERO;
    		//到付款合计
    		BigDecimal arrivalFeeTotal = BigDecimal.ZERO;
    		//代收货款合计
    		BigDecimal collectionFeeTotal = BigDecimal.ZERO;

    		int startNo = index * (sheetSize - 1);
            int endNo = Math.min(startNo + sheetSize - 1, dataSize);
            for (int i = startNo; i < endNo; i++) {
            	AirPickupbillDetailEntity result = detailList.get(i);
            	managerPickupType(detailList, i);
            	//HSSFRow dataRow = sheet.createRow(rowIndex);
            	createPickupCallDataRow(sheet, rowIndex, detailList.get(i));
     			rowIndex++;
     			// 累计
    			// 件数累加
    			goodsQtyTotal = goodsQtyTotal + result.getAirPickQty();
    			// 毛重累加
    			weightTotal = weightTotal.add(result.getWeight());
    			// 设置计费重量
    			billingWeightTotal = billingWeightTotal.add(result.getBillingWeight());
    			// 计算送货费
    			deliverFeeTotal = deliverFeeTotal.add(result.getDeliverFee());
    			// 到付款
    			arrivalFeeTotal = arrivalFeeTotal.add(result.getArrivalFee());
    			// 代收货款
    			collectionFeeTotal = collectionFeeTotal.add(result.getCollectionFee());
            }

            //合计行
            HSSFRow totalRow = sheet.createRow(rowIndex);
            createHSSFCell(totalRow, 0, "合计");
            createHSSFCell(totalRow, 1, String.valueOf(goodsQtyTotal));
            createHSSFCell(totalRow, 2, String.valueOf(weightTotal));
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, String.valueOf(billingWeightTotal));
            //送货费、到付费、代收货款
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_7, String.valueOf(deliverFeeTotal));
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_8, String.valueOf(arrivalFeeTotal));
            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_9, String.valueOf(collectionFeeTotal));
            rowIndex ++;
            
            // foot
            HSSFRow footRow = sheet.createRow(rowIndex);
            createHSSFCell(footRow, 0, "德邦物流");
            
            createHSSFCell(footRow, 1, "86769350/51/52/53");
            CellRangeAddress  dpCellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, 1, ConstantsNumberSonar.SONAR_NUMBER_5);
			sheet.addMergedRegion(dpCellRangeAddress);
			
            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_6, "制表人:" + airPickupbillEntity.getCreateUserName());
            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_7, "日期:" + format.format(new Date()));
         }
  		
		 byte[] excelBytes = getInputByte(workbook);
  		 //YES发送至edi
  		 String empCode = FossUserContext.getCurrentInfo().getEmpCode();
  		 if(AirfreightConstants.AIRFREIGHT_YES_SEND_EDI.equals(callIsNotEdiFlag)){
  			    UploadJointTicketDto dto = new UploadJointTicketDto();
  			    dto.setExcelBytes(excelBytes);
	  			dto.setCreatorNumber(airPickupbillEntity.getDestOrgName());
	  			dto.setSenderName(empCode);
	  			dto.setSendDate(new Date());
	  			SimpleDateFormat format2 = new SimpleDateFormat("ddHHmmss");
	  			dto.setSubject(AirfreightConstants.AIRFREIGHT_UPLOAD_PICKUP_EDI_ACCESSORY+" "+ airPickupbillEntity.getFlightNo()
	  					+"_"+airPickupbillEntity.getAirWaybillNo()+"_"+format2.format(new Date()));
	  			dto.setAttachmentName(airPickupbillEntity.getFlightNo()+"_"+airPickupbillEntity.getAirWaybillNo()+".xls");
			try {
				LOGGER.info("合票清单调用EDI:{}",airPickupbillEntity.getAirWaybillNo());
				fossToEDIService.uploadJointTicketList(dto);
			} catch (IOException e) {
				//System.out.println(e);
				throw new AirPickupBillJointlargeListException(e.getMessage());
			}
  		 }
		return new ByteArrayInputStream(excelBytes);
	}
	
	/**
	 * 中转提货Edi接口
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @throws IOException 
	 * @date 2012-12-27 上午10:33:36
	 */
	@Override
	public InputStream uploadTranPickupCallEdi(List<String> idsList , String transferNo ,String callIsNotEdiFlag){
				// 获取中转提货清单明细
				List<AirTransPickupDetailEntity> detailList = airTransPickupBillDao.queryAirTransPickupDetailListByNo(transferNo);
				if (CollectionUtils.isEmpty(detailList)) {
					throw new AirTransPickupBillException(
							AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDTRANSFERPICKUPDETAILBILL);
				}
				// 获取中转提货清单信息
				AirTransPickupbillEntity airTransPickupbillEntity = queryAirTransPickupbillEntity(transferNo);
				if (airTransPickupbillEntity == null) {
					throw new AirTransPickupBillException(
							AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDTRANSFERPICKUPBILL);
				}
				ExcelExport excelExportUtil = new ExcelExport();
				HSSFWorkbook workbook = excelExportUtil.createWorkBook();
		  		int dataSize = detailList.size();
		  		int sheetSize = ConstantsNumberSonar.SONAR_NUMBER_5000;
		  		/**
		  		 * @desc 使用BigDecimal代替之前的除法运算
		  		 * @author wqh
		  		 * @date 2013-08-05
		  		 * */
		  	    BigDecimal bdataSize=new BigDecimal(dataSize);
		  	    BigDecimal bsheetSize=new BigDecimal(sheetSize);
		  	    BigDecimal bresult=bdataSize.divide(bsheetSize).setScale(0, RoundingMode.UP);
		  	    double sheetNo=bresult.doubleValue();
		  		//double sheetNo = Math.ceil(dataSize / sheetSize);

		  		String title1 =  "TO:" + airTransPickupbillEntity.getDestOrgName();
		  		//模板中根据当前空运总调为广州空运总调则为"德 邦 物 流 合 票 提 货 清 单（广州）"
		  		//或者"德 邦 物 流 合 票 提 货 清 单（深圳）"
		  		//更好的设计是在数据字典表中维护部门code和城市名称的对应关系
		  		//然后从缓存获取封装成map，此处直接get即可
		  		//目测德邦几年内空运发展进度比较小，变化不大，故采用此方法
		  		String cityName = "";
		  		int num1 = airTransPickupbillEntity.getCreateOrgName().indexOf("广州");
		  		int num2 = airTransPickupbillEntity.getCreateOrgName().indexOf("深圳");
		  		int num3 = airTransPickupbillEntity.getCreateOrgName().indexOf("上海");
		  		if(num1 != -1){
		  			cityName = "广州";
		  		}else if(num2 != -1){
		  			cityName = "深圳";
		  		}else if(num3 != -1){
		  			cityName = "上海";
		  		}else{
		  			//无操作
		  		}	
		  	    //定义标题
		  		String title2 = "德邦物流中转提货清单(" + cityName + ")" ;
		  		// head1
		  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  		String transferDate = format.format(airTransPickupbillEntity.getTransferDate());
		  		String[] heads = { "提货单号",
						airTransPickupbillEntity.getAirWaybillNo(), "", "航班号",
						airTransPickupbillEntity.getTransferFlightNo(), "", "航班日期",
						transferDate };
		  		
		  		// head 2
				String[] columnNames = { "单号", "件数", "毛重", "重量", "目的站", "品名", "收货人",
						"派送费", "到付费", "代收款", "提货方式", "备注" };
		        HSSFCellStyle style = workbook.createCellStyle(); // 样式对象      
		        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直   
	     		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平 居中   
		  		for(int index = 0; index <= sheetNo; index++) {
		  			int rowIndex = 0;
		  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "空运" + index);
		            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_25);
		            
		            // title1
		     		HSSFRow titleRow1 = sheet.createRow(rowIndex);
		     		createHSSFCell(titleRow1, 0, title1);
		     		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
					sheet.addMergedRegion(cellRangeAddress);
					rowIndex ++;
		     		
					// title2
		     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
		     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, title2);
		     		titleCell2.setCellStyle(style);
		     		cellRangeAddress = new CellRangeAddress(1, 1, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
					sheet.addMergedRegion(cellRangeAddress);
					rowIndex ++;
		     		
					// head 1
					HSSFRow headRow1 = sheet.createRow(rowIndex);
					//HSSFCell headCell1 = null; 
		     		for(int i = 0, len = heads.length; i<len; i++) {
		     			createHSSFCell(headRow1, i, heads[i]);
		     		}
		     		rowIndex++;
		     		
					// head 2
					HSSFRow headRow2 = sheet.createRow(rowIndex);
					//HSSFCell headCell2 = null; 
		     		for(int i = 0, len = columnNames.length; i<len; i++) {
		     			createHSSFCell(headRow2, i, columnNames[i]);
		     		}
		     		rowIndex++;
		     		
		     		// 件数合计
		    		int goodsQtyTotal = 0;
		    		// 毛重合计
		    		BigDecimal weightTotal = new BigDecimal(0);
		    		// 计费重量合计
		    		BigDecimal billingWeightTotal = new BigDecimal(0);
		    		//派送费合计
		    		BigDecimal deliverFeeTotal = new BigDecimal(0);
		    		//到付款合计
		    		BigDecimal arrivalFeeTotal = new BigDecimal(0);
		    		//代收货款合计
		    		BigDecimal collectionFeeTotal = new BigDecimal(0);

		    		int startNo = index * (sheetSize - 1);
		            int endNo = Math.min(startNo + sheetSize - 1, dataSize);
		            for (int i = startNo; i < endNo; i++) {
		            	//空运免费自提
		            	if(StringUtil.equals(WaybillConstants.AIR_PICKUP_FREE, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("空运免费自提");
		            	}else if(StringUtil.equals(WaybillConstants.AIR_SELF_PICKUP, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("空运自提(不含机场提货费)");
		            	}else if(StringUtil.equals(WaybillConstants.DELIVER_FREE_AIR, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("空运免费送货");
		            	}else if(StringUtil.equals(WaybillConstants.AIRPORT_PICKUP, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("空运机场自提");
		            	}else if(StringUtil.equals(WaybillConstants.DELIVER_NOUP_AIR, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("送货(不含上楼)");
		            	}else if(StringUtil.equals(WaybillConstants.DELIVER_UP_AIR, detailList.get(i).getPickupType())){
		            		detailList.get(i).setPickupType("空运送货上楼");
		            	}else{
		            		detailList.get(i).setPickupType("空运送货进仓");
		            	}
		            	AirTransPickupDetailEntity result = detailList.get(i);
		            	//HSSFRow dataRow = sheet.createRow(rowIndex);
		            	createTranPickupCallDataRow(sheet, rowIndex, detailList.get(i));
		     			rowIndex++;
		     			// 累计
		    			// 件数累加
		    			goodsQtyTotal = goodsQtyTotal + result.getGoodsQty();
		    			// 毛重累加
		    			weightTotal = weightTotal.add(result.getWeight());
		    			// 设置计费重量
		    			billingWeightTotal = billingWeightTotal.add(result.getBillingWeight());
		    			// 计算送货费
		    			deliverFeeTotal = deliverFeeTotal.add(result.getDeliverFee());
		    			// 到付款
		    			arrivalFeeTotal = arrivalFeeTotal.add(result.getArrivalFee());
		    			// 代收货款
		    			collectionFeeTotal = collectionFeeTotal.add(result.getCollectionFee());
		            }

		            //合计行
		            HSSFRow totalRow = sheet.createRow(rowIndex);
		            createHSSFCell(totalRow, 0, "合计");
		            createHSSFCell(totalRow, 1, String.valueOf(goodsQtyTotal));
		            createHSSFCell(totalRow, 2, String.valueOf(weightTotal));
		            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_3, String.valueOf(billingWeightTotal));
		            //送货费、到付费、代收货款
		            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_7, String.valueOf(deliverFeeTotal));
		            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_8, String.valueOf(arrivalFeeTotal));
		            createHSSFCell(totalRow, ConstantsNumberSonar.SONAR_NUMBER_9, String.valueOf(collectionFeeTotal));
		            rowIndex ++;
		            
		            // foot
		            HSSFRow footRow = sheet.createRow(rowIndex);
		            createHSSFCell(footRow, 0, "德邦物流");
		            
		            createHSSFCell(footRow, 1, "86769350/51/52/53");
		            CellRangeAddress  dpCellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, 1, ConstantsNumberSonar.SONAR_NUMBER_5);
					sheet.addMergedRegion(dpCellRangeAddress);
					
		            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_6, "制表人:" + airTransPickupbillEntity.getCreateUserName());
		            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_7, "日期:" + format.format(new Date()));
		         }
		  		 byte[] excelBytes = getInputByte(workbook);
				 String empCode = FossUserContext.getCurrentInfo().getEmpCode();
				 //发送edi
				 if(AirfreightConstants.AIRFREIGHT_YES_SEND_EDI.equals(callIsNotEdiFlag)){
					UploadJointTicketDto uploadJointTicketDto = new UploadJointTicketDto();
					uploadJointTicketDto.setExcelBytes(excelBytes);
					uploadJointTicketDto.setCreatorNumber(airTransPickupbillEntity.getDestOrgName());
					uploadJointTicketDto.setSenderName(empCode);
					uploadJointTicketDto.setSendDate(new Date());
					SimpleDateFormat format2 = new SimpleDateFormat("ddHHmmss");
					uploadJointTicketDto.setSubject(AirfreightConstants.AIRFREIGHT_UPLOAD_TRAN_PICKUP_EDI_ACCESSORY+" "+airTransPickupbillEntity.getTransferFlightNo()+"_"+
							airTransPickupbillEntity.getAirWaybillNo()+"_"+format2.format(new Date()));
					uploadJointTicketDto.setAttachmentName(airTransPickupbillEntity.getTransferFlightNo()+"_"+
					airTransPickupbillEntity.getAirWaybillNo()+".xls");
					try {
						LOGGER.info("中转提货清单调用EDI:{}",airTransPickupbillEntity.getAirWaybillNo());
						fossToEDIService.uploadJointTicketList(uploadJointTicketDto);
					} catch (IOException e) {
						//System.out.println(e);
						throw new AirPickupBillJointlargeListException(e.getMessage());
					}
				}
				return new ByteArrayInputStream(excelBytes);
	}
	
	

	/**
	 * 变更Edi接口
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:33:36
	 */
	@Override
	public InputStream uploadChangeListCallEdi(List<String> idsList ,String callIsNotEdiFlag,String airWaybillNo) {
				AirPickupbillEntity airPickupbillEntity = queryAirPickupbillEntity(airWaybillNo);
				if(airPickupbillEntity==null){
					throw new AirTransPickupBillException(
							AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDCHANGEBILL);
				}
				List<AirPickupbillDetailEntity> airPickupbillDetailList = airTransPickupBillService.queryAirPickupbillDetailList(idsList);
				if(CollectionUtils.isEmpty(airPickupbillDetailList)){
					throw new AirTransPickupBillException(
							AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDCHANGEDETAILBILL);
				}
				ExcelExport excelExportUtil = new ExcelExport();
				HSSFWorkbook workbook = excelExportUtil.createWorkBook();
		  		int dataSize = airPickupbillDetailList.size();
		  		int sheetSize = ConstantsNumberSonar.SONAR_NUMBER_5000;
		  		
		  		/**
		  		 * @desc 使用BigDecimal代替之前的除法运算
		  		 * @author wqh
		  		 * @date 2013-08-05
		  		 * */
		  	    BigDecimal bdataSize=new BigDecimal(dataSize);
		  	    BigDecimal bsheetSize=new BigDecimal(sheetSize);
		  	    BigDecimal bresult=bdataSize.divide(bsheetSize).setScale(0, RoundingMode.UP);
		  	    double sheetNo=bresult.doubleValue();
		  		
		  		//double sheetNo = Math.ceil(dataSize / sheetSize);

		  		String title1 =  "TO:" + airPickupbillEntity.getDestOrgName();
		  		//模板中根据当前空运总调为广州空运总调则为"德 邦 物 流 合 票 提 货 清 单（广州）"
		  		//或者"德 邦 物 流 合 票 提 货 清 单（深圳）"
		  		//更好的设计是在数据字典表中维护部门code和城市名称的对应关系
		  		//然后从缓存获取封装成map，此处直接get即可
		  		//目测德邦几年内空运发展进度比较小，变化不大，故采用此方法
		  		String cityName = "";
		  		int num1 = airPickupbillEntity.getOrigOrgName().indexOf("广州");
		  		int num2 = airPickupbillEntity.getOrigOrgName().indexOf("深圳");
		  		int num3 = airPickupbillEntity.getOrigOrgName().indexOf("上海");
		  		if(num1 != -1){
		  			cityName = "广州";
		  		}else if(num2 != -1){
		  			cityName = "深圳";
		  		}else if(num3 != -1){
		  			cityName = "上海";
		  		}

		  		
		  	    //定义标题
		  		String title2 = "德邦物流变更清单(" + cityName + ")" ;
		  		
		  		// head1
		  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  		String transferDate = format.format(airPickupbillEntity.getFlightDate());
		  		String[] heads = { "提货单号",
		  				airPickupbillEntity.getAirWaybillNo(), "", "航班号",
		  				airPickupbillEntity.getFlightNo(), "", "航班日期",
						transferDate };
		  		
		  		// head 2
				String[] columnNames = { "单号", "件数", "毛重", "重量", "目的站", "品名", "收货人",
						"派送费", "到付费", "代收款", "提货方式", "备注" };
		        HSSFCellStyle style = workbook.createCellStyle(); // 样式对象      
		        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平     
		  		for(int index = 0; index <= sheetNo; index++) {
		  			int rowIndex = 0;
		  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "空运" + index);
		            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_25);
		            
		            // title1
		     		HSSFRow titleRow1 = sheet.createRow(rowIndex);
		     		createHSSFCell(titleRow1, 0, title1);
		     		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
					sheet.addMergedRegion(cellRangeAddress);
					rowIndex ++;
		     		
					// title2
		     		HSSFRow titleRow2 = sheet.createRow(rowIndex);
		     		HSSFCell titleCell2 = createHSSFCell(titleRow2, 0, title2);
		     		titleCell2.setCellStyle(style);
		     		cellRangeAddress = new CellRangeAddress(1, 1, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
					sheet.addMergedRegion(cellRangeAddress);
					rowIndex ++;
		     		
					// head 1
					HSSFRow headRow1 = sheet.createRow(rowIndex);
					//HSSFCell headCell1 = null; 
		     		for(int i = 0, len = heads.length; i<len; i++) {
		     			createHSSFCell(headRow1, i, heads[i]);
		     		}
		     		rowIndex++;
		     		
					// head 2
					HSSFRow headRow2 = sheet.createRow(rowIndex);
					//HSSFCell headCell2 = null; 
		     		for(int i = 0, len = columnNames.length; i<len; i++) {
		     			createHSSFCell(headRow2, i, columnNames[i]);
		     		}
		     		rowIndex++;

		    		int startNo = index * (sheetSize - 1);
		            int endNo = Math.min(startNo + sheetSize - 1, dataSize);
		            for (int i = startNo; i < endNo; i++) {
		            	//空运免费自提
		            	managerPickupType(airPickupbillDetailList, i);
		            	//AirPickupbillDetailEntity result = airPickupbillDetailList.get(i);
		            	//HSSFRow dataRow = sheet.createRow(rowIndex);
		            	createChangeListCallDataRow(sheet, rowIndex, airPickupbillDetailList.get(i));
		     			rowIndex++;
		            }
		            // foot
		            HSSFRow footRow = sheet.createRow(rowIndex);
		            createHSSFCell(footRow, 0, "德邦物流");
		            
		            createHSSFCell(footRow, 1, "86769350/51/52/53");
		            CellRangeAddress  dpCellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, 1, ConstantsNumberSonar.SONAR_NUMBER_5);
					sheet.addMergedRegion(dpCellRangeAddress);
					
		            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_6, "制表人:" + airPickupbillEntity.getCreateUserName());
		            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_7, "日期:" + format.format(new Date()));
		         }
		  		byte[] excelBytes = getInputByte(workbook);
		  		String empCode = FossUserContext.getCurrentInfo().getEmpCode();
				 //发送edi
				 if(AirfreightConstants.AIRFREIGHT_YES_SEND_EDI.equals(callIsNotEdiFlag)){
					UploadJointTicketDto uploadJointTicketDto = new UploadJointTicketDto();
					uploadJointTicketDto.setExcelBytes(excelBytes);
					uploadJointTicketDto.setCreatorNumber(airPickupbillEntity.getDestOrgName());
					uploadJointTicketDto.setSenderName(empCode);
					uploadJointTicketDto.setSendDate(new Date());
					SimpleDateFormat format2 = new SimpleDateFormat("ddHHmmss");
					uploadJointTicketDto.setSubject(AirfreightConstants.AIRFREIGHT_UPLOAD_CHANGE_EDI_ACCESSORY+" "
							+airPickupbillEntity.getAirWaybillNo()+"_"+format2.format(new Date()));
					uploadJointTicketDto.setAttachmentName(airPickupbillEntity.getAirWaybillNo()+".xls");
					try {
						LOGGER.info("变更清单调用EDI:{}",airPickupbillEntity.getAirWaybillNo());
						fossToEDIService.uploadJointTicketList(uploadJointTicketDto);
					} catch (IOException e) {
						//System.out.println(e);
						throw new AirChangeInventoryException(e.getMessage());
					}
				}
				return new ByteArrayInputStream(excelBytes);
	}

	/**
	 * sonar 优化 311396 wwb 2016年12月24日10:43:50
	 * @param airPickupbillDetailList
	 * @param i
	 */
	private void managerPickupType(
			List<AirPickupbillDetailEntity> airPickupbillDetailList, int i) {
		if(StringUtil.equals(WaybillConstants.AIR_PICKUP_FREE, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("空运免费自提");
		}else if(StringUtil.equals(WaybillConstants.AIR_SELF_PICKUP, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("空运自提(不含机场提货费)");
		}else if(StringUtil.equals(WaybillConstants.DELIVER_FREE_AIR, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("空运免费送货");
		}else if(StringUtil.equals(WaybillConstants.AIRPORT_PICKUP, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("空运机场自提");
		}else if(StringUtil.equals(WaybillConstants.DELIVER_NOUP_AIR, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("送货(不含上楼)");
		}else if(StringUtil.equals(WaybillConstants.DELIVER_UP_AIR, airPickupbillDetailList.get(i).getPickupType())){
			airPickupbillDetailList.get(i).setPickupType("空运送货上楼");
		}else{
			airPickupbillDetailList.get(i).setPickupType("空运送货进仓");
		}
	}
	
	/**
	 * 获取io字节 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-29 下午2:56:59
	 */
	private byte[] getInputByte(HSSFWorkbook woorkBook){
  		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			woorkBook.write(out);
		} catch (IOException e1) {
			//e1.printStackTrace();
		}finally{
			IOUtils.close(out);
		}
		return out.toByteArray();
	}
	
	/**
	 * 根据正单号查询合大票清单
	 * @param  airWaybillNo
	 * @return AirPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午1:10:30
	 */
	private AirPickupbillEntity queryAirPickupbillEntity (String airWaybillNo){
		return airTransPickupBillDao.queryAirPickupbillEntity(airWaybillNo);
	}
	
	/**
	 * 根据合大票idlist查询合大票明细list
	 * @param  idsList
	 * @return List<AirPickupbillDetailEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午1:12:11
	 */
	@SuppressWarnings("unused")
	private List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(List<String> idsList){
		return airTransPickupBillDao.queryAirPickupbillDetailList(idsList);
	}
	
	/**
	 * 查询中转提货清单信息
	 * @param  airWaybillNo
	 * @return AirTransPickupbillEntity 中转提货清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:41:10
	 */
	private AirTransPickupbillEntity queryAirTransPickupbillEntity (String transferNo){
		return airTransPickupBillDao.queryAirTransPickupbillEntityByNo(transferNo);
	} 
	
	/**
	 * 根据正单号查询中转提过货清单明细.
	 * @param airWaybillNo the air waybill no
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	private List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(List<String> idsList) {
		return airTransPickupBillDao.queryAirTransPickupDetailList(idsList);
	}
	
	/**
	 * 设置 中转提货清单service.
	 * @param airTransPickupBillService the new 中转提货清单service
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}

	/**
	 * 设置 注入中转提货清单dao.
	 * @param airTransPickupBillDao the new 注入中转提货清单dao
	 */
	public void setAirTransPickupBillDao(
			IAirTransPickupBillDao airTransPickupBillDao) {
		this.airTransPickupBillDao = airTransPickupBillDao;
	}

	/**
	 * 设置 注入edi接口.
	 * @param fossToEDIService the new 注入edi接口
	 */
	public void setFossToEDIService(IFOSSToEDIService fossToEDIService) {
		this.fossToEDIService = fossToEDIService;
	}
	/**
	 * 获取上传uploadJointTicketDto.
	 * @param fossToEDIService the new  
	 */
	public UploadJointTicketDto getUploadJointTicketDto() {
		return uploadJointTicketDto;
	}
	/**
	 * 设置上传uploadJointTicketDto.
	 * @param fossToEDIService the new  
	 */
	public void setUploadJointTicketDto(UploadJointTicketDto uploadJointTicketDto) {
		this.uploadJointTicketDto = uploadJointTicketDto;
	}
}