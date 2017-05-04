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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/airHandoverbillGZ/AirHandoverbillGZPrtDataSource.java
 *  
 *  FILE NAME          :AirHandoverbillGZPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.airHandoverbillGZ;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;

/**
 * 用于打印航空正单交接单-广州
 * @author foss-liuxue(for IBM)
 * @date 2012-11-13 下午2:26:41
 */
public class AirHandoverbillGZPrtDataSource implements JasperDataSource  {
private AirHandOverBillEntity airHandOverBillEntity;   //接受查询返回的交接单实体
	
	private List<AirHandOverBillDetailEntity> airHandOverBillDetailList;   //接受返回的正单明细

	/**
	 * 设置打印单上面的相关字段
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-13 14:27:44
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map createParameterDataSource(JasperContext jasperContext)
			//抛出异常
			throws Exception {
		//定义hashmap
		Map<String,Object> parameter = new HashMap<String,Object>();
		//查询交接单和明细信息
		queryAirHandOverBillAndDetailInfo(jasperContext);
		//定义打印重量
		String printWeight = (String)jasperContext.get("printWeight");
		//打印重量为空
		if(StringUtils.isBlank(printWeight)){
			//毛重
			parameter.put("grossWeightTotal", airHandOverBillEntity.getGrossWeightTotal());
		}else{
			//打印重量
			parameter.put("grossWeightTotal", new BigDecimal(printWeight));
		}
		//起飞时间
		Date flightTime = airHandOverBillEntity.getTakeOffTime();
		String takeOffTime = DateUtils.convert(flightTime, "HHmm");
		//航班号
		parameter.put("flightNo", airHandOverBillEntity.getFlightNo()+"/"+takeOffTime);
		//预定号码
		if(airHandOverBillEntity.getBookingNo()==null)
		{
			airHandOverBillEntity.setBookingNo("");
		}
		if(airHandOverBillEntity.getAirLevel()==null)
		{
			airHandOverBillEntity.setAirLevel("");
		}
		parameter.put("bookingNo", airHandOverBillEntity.getBookingNo()+"  "+airHandOverBillEntity.getAirLevel());
		//parameter.put("bookingNo", airHandOverBillEntity.getBookingNo());
		//交接部门
		parameter.put("handoverOrg", airHandOverBillEntity.getHandoverOrg());
		//航班日期
		Date flightDate = airHandOverBillEntity.getFlightDate();
		String[] dates = DateUtils.convert(flightDate,"yyyy-MM-dd").split("-");
		parameter.put("airYear", dates[0]);
		parameter.put("airMonth", dates[1]);
		parameter.put("airDay", dates[2]);
		//总件数
		parameter.put("goodsQtyTotal", airHandOverBillEntity.getGoodsQtyTotal());
		//运单总件数
		parameter.put("billingWeightTotal", airHandOverBillEntity.getBillingWeightTotal());
		//创建人名字
		parameter.put("createUserName", airHandOverBillEntity.getCreateUserName());
		//创建时间
		parameter.put("createTime", airHandOverBillEntity.getCreateTime());
		//备注
		parameter.put("notes",airHandOverBillEntity.getNotes()==null?"":
			airHandOverBillEntity.getNotes());
		//交接部门
		parameter.put("handoverEmp", airHandOverBillEntity.getHandoverEmp());
		//到达部门名称
		if(null != airHandOverBillDetailList && airHandOverBillDetailList.size() > 0){
			parameter.put("arrvRegionName", airHandOverBillDetailList.get(0).getArrvRegionName());
		}
		return parameter;
	}

	/**
	 * 设置打印单上面的相关字段
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-13 14:27:44
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List createDetailDataSource(JasperContext jasperContext)
			throws Exception {
		//抛出异常
		//定义list
		List list = new ArrayList();
		//获取交接单明细实体
		for(AirHandOverBillDetailEntity airHandOverBillDetailEntity : airHandOverBillDetailList){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//航线编码
			dataMap.put("airLineCode", airHandOverBillDetailEntity.getAirLineCode());
			//运单号
			dataMap.put("airWaybillNo", airHandOverBillDetailEntity.getAirWaybillNo());
			//总件数
			dataMap.put("goodsQty", airHandOverBillDetailEntity.getGoodsQty());
			//毛重
			dataMap.put("grossWeight",airHandOverBillDetailEntity.getGrossWeight()==null
					?BigDecimal.ZERO: airHandOverBillDetailEntity.getGrossWeight());
			//货物名称
			dataMap.put("goodsName",airHandOverBillDetailEntity.getGoodsName()==null
					?"": airHandOverBillDetailEntity.getGoodsName());
			//包装结构
			dataMap.put("packageStruction",airHandOverBillDetailEntity.getPackageStruction()==null
					?"": airHandOverBillDetailEntity.getPackageStruction());
			//BUG-56974 零担&快递集成环境-航空正单交接单界面新增和修改界面选择航空正单的打印方式进行打印，预览界面显示英文代码
			//备注
			dataMap.put("notes", airHandOverBillDetailEntity.getAirFee()==null
					?"": airHandOverBillDetailEntity.getAirFee().divide(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_100))+
					"("+airHandOverBillDetailEntity.getBillingWeight()+"Kg)");
			//到达地
			dataMap.put("arrvRegionName",airHandOverBillDetailEntity.getArrvRegionName()==null
					?"": airHandOverBillDetailEntity.getArrvRegionName());
			//获取到list
			list.add(dataMap);
		}
		//返回list
		return list;
	}

	/**
	 * 根据选中数据的交接单ID查找交接单信息以及正单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-13 下午2:28:36
	 */
	public void queryAirHandOverBillAndDetailInfo(JasperContext jasperContext){
		//空运交接单服务
		IAirHandOverBillService airHandOverBillService = jasperContext.getApplicationContext().getBean("airHandOverBillService", IAirHandOverBillService.class);
		//定义交接单号
		String airHandoverbillId = (String)jasperContext.get("airHandoverbillId");
		String ids = (String)jasperContext.get("ids");
		String[] id = ids.split(",");
		//交接单dto
		AirHandOverBillDto airHandOverBillDto = airHandOverBillService.loadAirHandOverBillInfoForPrint(airHandoverbillId,id);
		//交接单实体
		airHandOverBillEntity = airHandOverBillDto.getAirHandOverBillEntity();
		//交接单明细列表
		airHandOverBillDetailList = airHandOverBillDto.getAirHandOverBillDetailList();
	}

}