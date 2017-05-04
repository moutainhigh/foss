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
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/AirQueryWaybillAction.java
 * 
 *  FILE NAME          :AirQueryWaybillAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirWayBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillVO;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

import static com.deppon.foss.module.transfer.scheduling.api.shared.util.CompareUtils.LOGGER;

/**
 * 查询航空正单action 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午7:00:25
 */
public class AirQueryWaybillAction extends AbstractAction {
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -2058435439304477136L;

	/**
	 * 航空正单serivice
	 */
	private IAirWaybillService airWaybillService;
	
	

	/**
	 * 航空正单vo
	 */
	private AirWayBillVO airWayBillVo;
	
	/** 导出Excel 文件流. */
	private InputStream excelStream;  
	
	/** 导出Excel 文件名. */
	private String fileName; 

	/**
	 * 查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:35:10
	 */
	@JSON
	public String queryAirWayBill() {
		try {
			
			//获取dto
			AirWayBillDto airWayBillDto = airWayBillVo.getAirWayBillDto();
			//根据dto中的参数查询航空正单明细list
			//返回结果集
			List<AirWaybillEntity>  result = airWaybillService.queryAirWayBillList(airWayBillDto,this.getLimit(), this.getStart());
			for(int i=0;i<result.size();i++){
				if(result.get(i).getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE)){
					result.get(i).setItemCode("");
				}
			}
			//设置结果集
			airWayBillVo.setResult(result);
			//获取中记录数
			Long totalCount = airWaybillService.getCount(airWayBillDto);
			//设置总记录数
			this.setTotalCount(totalCount);
			//返回成功信息
			return super.returnSuccess();
			//catch 异常
		} catch (AirWayBillException e) {
			//返回失败信息
			return super.returnError(e);
		}
	}
	
	/**
	 * 导出航空正单列表
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:51:23
	 */
	public String queryAirWaybillForExport(){
		//获取dto
		if(null == airWayBillVo){
			throw new ParameterException("传入航空证单信息为空"); 
		}
		AirWayBillDto airWayBillDto = airWayBillVo.getAirWayBillDto();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			fileName = URLEncoder.encode("航空正单列表信息"+format.format(new Date()) ,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AirChangeInventoryException("编码转换异常");
		}
		try {
			excelStream = airWaybillService.queryAirWaybillForExport(airWayBillDto);
		} catch (AirChangeInventoryException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据航空正单正单号查询
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:35:45
	 */
	@JSON
	public String queryAirwaybillNo() {
		try {
			//获取dto中的参数
			//调用航空正单查询服务
			//返回航空正单信息
			AirWaybillEntity entity = airWaybillService.queryResultEntity(airWayBillVo.getAirWayBillDto());
			//设置航空正单信息
			airWayBillVo.getAirWayBillDto().setOptAirWaybillEntity(entity);
			//返回成功信息
			return super.returnSuccess();
			//catch 异常
		} catch (AirWayBillException e) {
			//返回异常信息
			return super.returnError(e);
		}
	}
	/**
	 * 根据ID获取当前航空正单付款状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:15
	 */
	@JSON
	public String queryById() {
		try {
			//获取dto
			//根据dto中的状态调用航空正单查询服务
			//返回付款状态
			String paymentStatus = airWaybillService.queryStatus(airWayBillVo.getAirWayBillDto());
			//设置付款状态
			airWayBillVo.getAirWayBillDto().setPaymentStatus(paymentStatus);
			//返回成功信息
			return super.returnSuccess();
			//catch异常
		} catch (AirWayBillException e) {
			//返回错误信息
			return super.returnError(e);
		}
	}
	
	/**
	 * 根据id查询航空正单 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-26 下午3:25:00
	 * */
	@JSON
	public String queryAirWaybillEntity() {
		//获取员工信息
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    ParsePosition pos = new ParsePosition(0);   
	    java.util.Date datetime = formatter.parse("2012-11-30 16:41:48", pos); 
	    //代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
		String id = airWayBillVo.getAirWayBillDto().getAirwaybillId();
		AirWayBillDto airWayBillDto = airWaybillService.airWayBillDto(id);
		 AirlinesValueAddEntity airlinesValueAddEntity = airWaybillService
				 .queryRate("1","XZQY-000000002","1",datetime);
		airWayBillVo = airWayBillDto.getAirWayBillVO();
		airWayBillVo.setAirlinesValueAddEntity(airlinesValueAddEntity);
		return super.returnSuccess();
	}
	 
	/**
	 * 获取 航空正单vo.
	 * @return the 航空正单vo
	 */
	public AirWayBillVO getAirWayBillVo() {
		return airWayBillVo;
	}
	
	/**
	 * 设置 航空正单vo.
	 * @param airWayBillVo the new 航空正单vo
	 */
	public void setAirWayBillVo(AirWayBillVO airWayBillVo) {
		this.airWayBillVo = airWayBillVo;
	}
	
	/**
	 * 设置 航空正单serivice.
	 * @param airWaybillService the new 航空正单serivice
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}
	
	/**
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 导出Excel 文件名.
	 *
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}