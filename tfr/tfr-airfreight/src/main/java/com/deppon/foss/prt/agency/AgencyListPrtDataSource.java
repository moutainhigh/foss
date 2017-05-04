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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/agency/AgencyListPrtDataSource.java
 *  
 *  FILE NAME          :AgencyListPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.agency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.util.DateUtils;

public class AgencyListPrtDataSource implements MultiJasperDataSource {

	// 给转换json对象赋值
	AirWaybillEntity wayBill;

	private List<AirWaybillDetailEntity> list;

	/**
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-16 下午2:41:33
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(JasperContext jasperContext) {

		String[] ids = null;
		String id = null;
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		if (jasperContext.get("isNotbatchPrint") != null && jasperContext.get("isNotbatchPrint").equals("1")) {
			ids = jasperContext.get("ids").toString().split(",");
			for (String idsa : ids) {

				data.add(new AgencyJasperDataSource(idsa));
			}
		} else {
			id = jasperContext.get("id").toString();
			data.add(new AgencyJasperDataSource(id));
		}
		return data.toArray(new JasperDataSource[0]);
	}

	class AgencyJasperDataSource implements JasperDataSource {
		String ids = null;

		public AgencyJasperDataSource(String ids) {
			this.ids = ids;
		}

		/**
		 * 创建数据源
		 * 
		 * @author 096598-foss-zhongyubing
		 * @date 2013-3-16 下午2:45:36
		 */
		@Override
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			List<Map<String, Object>> map = new ArrayList<Map<String, Object>>(list.size());
			queryAirWaybillListByPrint(jasperContext, this.ids);

			if (CollectionUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					AirWaybillDetailEntity entity = list.get(i);
					// 设置运单号
					dataMap.put("airWaybillNo", entity.getWaybillNo());
					// 设置件数
					dataMap.put("goodsQty", entity.getGoodsQty());

					// 设置目的站
					dataMap.put("arrvRegionName", entity.getArrvRegionName());
					// 设置重量
					dataMap.put("grossWeight", entity.getGrossWeight());
					// 设置品名/包装名
					dataMap.put("goodsName", entity.getGoodsName()+"/"+entity.getGoodsPackage());
					//dataMap.put("goodsName", entity.getGoodsName()+"/");
					String isnotRecDept= jasperContext.get("isnotRecDept").toString();
					if(!AirfreightConstants.AIRFREIGHT_ISNOTRECEIVERNAME.equals(isnotRecDept)){
						// 设置收货部门
						dataMap.put("receiverOrgName", entity.getReceiveOrgName());
					}else{
						dataMap.put("receiverName", "");
					}
					map.add(dataMap);
				}
			}
			return map;

		}

		// 根据id获取打印数据
		public void queryAirWaybillListByPrint(JasperContext jasperContext, String recordId) {
			IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService",
					IAirWaybillService.class);
			// 根据航空正单ID查询运单明细
			list = airWaybillService.queryAirWaybillListForPrint(recordId);
		}

		/**
		 * 
		 * @author 096598-foss-zhongyubing
		 * @date 2013-3-16 下午4:05:35
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception {

			// 接受打印map
			Map<String, Object> parameter = new HashMap<String, Object>();
			IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService",
					IAirWaybillService.class);
			// 查询头信息
			wayBill = airWaybillService.queryAirWaybillEntityPrint(this.ids);
			// 获取前台json
			if (wayBill != null) {
				queryAirWaybillListByPrint(jasperContext, wayBill.getId());
				// 设置总件数
				int goodsQtyTotal = 0;
				// 设置总重量
				BigDecimal grossWeightTotal = new BigDecimal(0);
				for (int i = 0; i < list.size(); i++) {
					// 计算总件数
					goodsQtyTotal = (goodsQtyTotal + list.get(i).getGoodsQty());
					// 计算总重量
					grossWeightTotal = grossWeightTotal.add(list.get(i).getGrossWeight());
				}
				String str = "";
				str = wayBill.getFlightNo() + '/'
						+ DateUtils.convert(wayBill.getTakeOffTime(), DateUtils.DATE_TIME_FORMAT) + '/'
						+ DateUtils.convert(wayBill.getArriveTime(), DateUtils.DATE_TIME_FORMAT);
				// 设置代理名称
				parameter.put("agencyName", wayBill.getAgencyName());
				// 设置航班号/起飞时间/到达时间
				parameter.put("flightNo", str);
				// 设置合票号
				parameter.put("jointTicketNo", wayBill.getJointTicketNo());
				// 设置托运人
				parameter.put("shipperName", wayBill.getShipperName());

				// 设置打印时间
				parameter.put("printTime",
						DateUtils.convert(Calendar.getInstance().getTime(), DateUtils.DATE_TIME_FORMAT));
				// 设置总件数
				parameter.put("goodsQtyTotal", goodsQtyTotal);
				// 设置总重量
				parameter.put("grossWeightTotal", grossWeightTotal.longValue());
				// 设置制单人
				parameter.put("createUserName", jasperContext.get("createUserName"));
			}

			return parameter;
		}
	}
}