/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/PickupAndDeliverySmallZoneAction.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IinviteFossVehicleCostService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.InviteFossVehicleCostVo;
import com.deppon.foss.util.copyUtil.XlsImpUtil;

/**
 * 零担外请车单票费用action
 * 
 * @author 332219-FOSS
 * @date 2017-03-11 14:10:10
 * @since
 * @version 0.01
 */
public class InviteFossVehicleCostAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971425L;

	// 事业部和大区 使用VO
    private InviteFossVehicleCostVo inviteFossVehicleCostVo = new InviteFossVehicleCostVo();
    /**
     * 零担外请车单票费用Service
     */
    private IinviteFossVehicleCostService inviteFossVehicleCostService;
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InviteFossVehicleCostAction.class);
    
    //上传文件每行的的总列数
  	private static final int CELL_COUNT = 3;
  	//上传数量1000
  	private static final int MAX_NUMBER = 1000;
	

    /**
     * <p>
     * 查询事业部
     * </p>
     * 
     * @author 332219-FOSS
     * @date 2017-03-11 10:10:10
     * @return String
     */
    @JSON
    public String queryInviteFossVehicleCost(){
		try{
			//验证参数
			if(inviteFossVehicleCostVo.getInviteFossVehicleCostQueryDto()==null){
				throw new BusinessException("查询事业部信息失败,参数异常");
			}
			//先查询总条数
			long total = inviteFossVehicleCostService.queryTotalByVehicleCost(inviteFossVehicleCostVo.getInviteFossVehicleCostQueryDto());
			//如果总数大于0
			if(total>0){
				//查询零担外请车单票费用信息
				List<InviteFossVehicleCostEntity> returnDto = inviteFossVehicleCostService.queryVehicleDrivingByVehicleCost(inviteFossVehicleCostVo.getInviteFossVehicleCostQueryDto(), this.getStart(), this.getLimit());
				//设置返回值
				inviteFossVehicleCostVo.setInviteFossVehicleCostEntityList(returnDto);
				this.setTotalCount(total);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}

    /**
     * 新增零担外请车单票费用
     * 
     * @author 332219-FOSS
     * @date 2016-03-22 10:10:10
     * @return String
     * @see
     */
  	public String addInviteFossVehicleCost(){
  		Workbook book = null;
  		try {
  			if(inviteFossVehicleCostVo.getUploadFile() != null && StringUtils.isNotBlank(inviteFossVehicleCostVo.getUploadFileFileName())){
  				FileInputStream inputStream = new FileInputStream(inviteFossVehicleCostVo.getUploadFile());
  				book = XlsImpUtil.create(inputStream);
  			}
  			String message="";
  			if(book!=null){
  				message=readWorkbook(book);
  			}
  			if(message.equals("success")){
  				return super.returnSuccess(MessageType.IMPORT_SUCCESS);
  			}else{
  				return returnError("数据参数错误!");
  			}
  		} catch (FileNotFoundException e) {
  			return returnError("文件不存在异常:" + e.getMessage(), e);
  		} catch (InvalidFormatException e) {
  			return returnError("文件格式异常:" + e.getMessage(), e);
  		} catch (Exception e) {
  			return returnError("文件异常:" + e.getMessage(), e);
  		}
  	}

  	private String readWorkbook(Workbook book) {
  		//获取sheet的个数
  		int sheetNum = book.getNumberOfSheets();
  		try {
			for(int i=0;i<sheetNum;i++){
				if(book.getSheetName(i) != null && book.getSheetName(i) != ""){
					//获取工作表
					Sheet sheet = book.getSheetAt(i);
					int rowNum = sheet.getPhysicalNumberOfRows();
					//目前上传数据量限定在1000条以内
					if(rowNum>MAX_NUMBER){
						return returnError("上传文件数据太多,每次最大上传1000条数据!!!");
					}
					//读取表头,进行校验
					Row row = sheet.getRow(0);
					if(row!=null){
						String result = this.sonarSplit(row);
						if(!result.isEmpty()){
							return result;
						}
					}else{
						return returnError("文件格式异常:请按照模板添加上传数据!!!");
					}
					List<InviteFossVehicleCostEntity> ses= new ArrayList<InviteFossVehicleCostEntity>();
					makeExcelToSes(sheet, ses);
					inviteFossVehicleCostService.importInviteFossVehicleCost(ses);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
  		return "success";
  	}
  	
  	private String sonarSplit(Row row){
  		String[] forCompare = { "事业部名称", "大区名称", "外请车单票费用上限(元)" };
  		for(int i = 0; i <= NumberConstants.NUMBER_2; i++){
  			Cell cell = row.getCell(i);
  			if(cell==null||cell.getCellType()!=Cell.CELL_TYPE_STRING||
  					!forCompare[i].equals(cell.getStringCellValue().trim())){
  						return returnError("文件格式异常:请按照模板添加上传数据!!!");
  					}
  		}
  		return StringUtils.EMPTY;
  	}
  	
  	private void makeExcelToSes(Sheet sheet,List<InviteFossVehicleCostEntity> ses){
  		//记录那些行的数据无法解析
  		List<Integer> numList = new ArrayList<Integer>();
  		try {
			if(sheet!=null&&ses!=null){
				// 获取物理行数
				int rowCount = sheet.getPhysicalNumberOfRows();
				// 根据行数循环
				Row row = null;
				// EXCEL行记录
				InviteFossVehicleCostEntity se = null;
				// 根据行数循环,标记外层循环
				for (int rowNum = 1; rowNum < rowCount; rowNum++) {
					// 获取每行数据
					se = new InviteFossVehicleCostEntity();
					// 取得一行的数据
					row = sheet.getRow(rowNum);
					Cell cell = null;
					String cellValue = null;
					// 如果有一行空白则不会再录数据(一行数据全为空)
					for (int colNum = 0; colNum < CELL_COUNT; colNum++) {
						cell = row.getCell(colNum);
						if(cell==null){
							numList.add(rowNum+1);
							break;
						}
						cellValue = cellValue(cell);
						//如果为空或者长度超过50说明该行数据无效,记录行数返回到页面
						if(cellValue==null||"".equals(cellValue)||cellValue.length()>NumberConstants.NUMBER_50){
							numList.add(rowNum+1);
							break;
						}
						if(colNum==0){
							se.setBusinessName(cellValue);
						}else if(colNum==NumberConstants.NUMBER_1){
							se.setRegionalName(cellValue);
						}else if(colNum==NumberConstants.NUMBER_2){
							try{
								se.setVehicleCostMax(Double.valueOf(cellValue));
							}catch(Exception e){
								numList.add(rowNum+1);
								break;
							}
						}
					}
				//添加集合
				ses.add(se);
				}
				inviteFossVehicleCostVo.setNumList(numList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
  	}
  	
  	private String cellValue(Cell cell){
  		switch (cell.getCellType()) {
  			case Cell.CELL_TYPE_STRING:
  				return cell.getStringCellValue().trim();
  			case Cell.CELL_TYPE_NUMERIC:
  				//排除掉导入数字数据后面的.0
  				String str = cell.getNumericCellValue()+"";
  				String[] nums = str.split("\\.");
  				if(nums.length==2){
  					try {
  						//Integer.parseInt(nums[0]);
  						//如果最后一个数字是0,就返回第一个字符串
  						if("0".equals(nums[1])){
  							return nums[0];
  						}
  					} catch (NumberFormatException e) {
  						return str;
  					}
  				}
  				return str;
  			default:
  				return null;
  		}
  	}
  	
  	/**
     * 修改零担外请车单票费用
     * @author 332219-foss
     * @date 2017-03-22 下午3:04:20
     */
    @JSON         
    public String updateInviteFossVehicleCost() {
		try {
		    //修改零担外请车单票费用
		    inviteFossVehicleCostService.updateInviteFossVehicleCost(inviteFossVehicleCostVo.getInviteFossVehicleCostEntity());
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e.getMessage(), e);
		}
    }
    
    /*
     * =================================================================
     * 下面是get,set方法：
     */
	public InviteFossVehicleCostVo getInviteFossVehicleCostVo() {
		return inviteFossVehicleCostVo;
	}

	public void setInviteFossVehicleCostVo(
			InviteFossVehicleCostVo inviteFossVehicleCostVo) {
		this.inviteFossVehicleCostVo = inviteFossVehicleCostVo;
	}

	public void setInviteFossVehicleCostService(
			IinviteFossVehicleCostService inviteFossVehicleCostService) {
		this.inviteFossVehicleCostService = inviteFossVehicleCostService;
	}
}