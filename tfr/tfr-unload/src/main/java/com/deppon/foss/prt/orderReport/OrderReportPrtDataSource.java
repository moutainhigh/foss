
/*******************************************************************************
 * Copyright 2016 TFR TEAM
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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/partialline/OrderReportPrtDataSource.java
 *  
 *  FILE NAME          :OrderReportPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.orderReport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderDifferReportService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
/**
 * 点单差异报告打印
 * @author 18928
 * @date 2013-1-6 上午10:20:31
 */
public class OrderReportPrtDataSource implements MultiJasperDataSource {
	
	/****
	 * 批量打印的jasperDataSource
	 * @author 18928
	 * @date 2016-1-25 下午6:51:38
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		//获取点单编号
		Object obj = jasperContext.get("reportNo");
		String[] reportNos = null;
		if(obj instanceof String) {
			reportNos = new String[1];
			reportNos[0] = (String) obj;
		} else {
			reportNos = (String[]) obj;
		}
		
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		for(String reportNo : reportNos ){
			data.add(new MyJasperDataSource(reportNo));
		}
		return data.toArray(new JasperDataSource[0]);
	}
	
	/***
	 * 
	 * 点单差异报告打印
	 * @author 18928
	 * @date 2016-1-25 下午6:51:38
	 */
	class MyJasperDataSource implements JasperDataSource {
		//报告编号
		String reportNo = null;
		
		public MyJasperDataSource(String reportNo){
			this.reportNo = reportNo;
		}
		
		/****
		 * 创建打印参数数据源
		 * @author 18928
		 * @date 2016-1-25 下午6:51:38
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//交接单service
			IOrderDifferReportService orderDifferReportService = (IOrderDifferReportService) applicationContext.getBean("orderDifferReportService");
			//根据交接单号查询出该交接单
			
			OrderDifferReportDto orderDifferReportDto =orderDifferReportService.queryOrderReportByReportNo(reportNo);
			List<OrderReportDetailDto>   differQtyTotal=orderDifferReportService.queryOrderReportDetailByNo(reportNo);
			//打印中需要的参数
			Map<String, Object> parameter = new HashMap<String, Object>();
			//报告编号
			parameter.put("reportNo", reportNo);
			if(StringUtils.isEmpty(orderDifferReportDto.getOrderOrgName())){
				//点单部门
				parameter.put("orderOrgName", "");
			}else{
				//点单部门
				parameter.put("orderOrgName", orderDifferReportDto.getOrderOrgName());
			}
			
			//点单人
			parameter.put("orderManName", orderDifferReportDto.getOrderManName());
			//票数
			parameter.put("differQtyTotal", differQtyTotal.size());
			//少货
			parameter.put("lostGoodsQtyTotal", orderDifferReportDto.getLostGoodsQty());
			//多货
			parameter.put("moreGoodsQtyTotal", orderDifferReportDto.getMoreGoodsQty());
			//制单时间
			parameter.put("createTime", orderDifferReportDto.getCreateTime());
			
			return parameter;
		}
		
		/****
		 * 创建打印field数据源
		 * @author 18928
		 * @date 2016-1-25 下午6:51:38
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//点单差异serivce
			IOrderDifferReportService orderDifferReportService = (IOrderDifferReportService) applicationContext.getBean("orderDifferReportService");
			//查询出打印中需要的数据
			List<OrderReportDetailDto>   orderReportDetailList=orderDifferReportService.queryOrderReportDetailByNo(reportNo);
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(orderReportDetailList.size());
			for(OrderReportDetailDto orderReportDetailDto : orderReportDetailList){
				
				Map<String, Object> m = new HashMap<String, Object>();
				//运单号
				m.put("waybillNo", orderReportDetailDto.getWaybillNo());
				//丢货件数
				BigDecimal lostGoodsQty =orderReportDetailDto.getLostGoodsQty();
				if(lostGoodsQty == null) {
					lostGoodsQty = BigDecimal.ZERO;
				}
				//少货件数
				m.put("lostGoodsQty", lostGoodsQty);
				//多货件数
				BigDecimal moreGoodsQty =orderReportDetailDto.getMoreGoodsQty();
				if(moreGoodsQty == null) {
					moreGoodsQty = BigDecimal.ZERO;
				}
				//多货件数
				m.put("moreGoodsQty",moreGoodsQty);
				//件数
				m.put("handoverGoodsQty", orderReportDetailDto.getHandoverGoodsQty());
				data.add(m);
			}
			return data;
		}
	}
}