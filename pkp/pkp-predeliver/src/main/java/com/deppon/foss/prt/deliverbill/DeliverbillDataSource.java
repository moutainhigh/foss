/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/deliverbill/DeliverbillDataSource.java
 * 
 * FILE NAME        	: DeliverbillDataSource.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.deliverbill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;

/**
 * 
 * 预派送单数据封装类
 * 
 * @author ibm-wangfei
 * @date Nov 16, 2012 1:39:56 PM
 */
public class DeliverbillDataSource implements JasperDataSource {

	private static final int NUMBER_66 = 66;
	
	private static final int NUMBER_36 = 36;
	DeliverbillVo vo;
	static String split = " ";

	@Override
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception {
		// 接受打印map
		Map<String, Object> parameter = new HashMap<String, Object>();
		String deliverbillId = (String) jasperContext.get("deliverbillId");
		vo = getDeliverbillVo(jasperContext, deliverbillId);
		if (vo != null && vo.getDeliverbill() != null) {
			DeliverbillEntity deliverbill = vo.getDeliverbill();
			parameter.put("deliverbillNo", defaultIfNull(deliverbill.getDeliverbillNo()) + split); // 派送单号
			parameter.put("driverName", defaultIfNull(deliverbill.getDriverName()) + split); // 司机姓名
			parameter.put("vehicleNo", defaultIfNull(deliverbill.getVehicleNo()) + split); // 车牌号
			parameter.put("driverTel", defaultIfNull(deliverbill.getDriverTel()) + split); // 司机电话号码
			parameter.put("deliveryDepartment", defaultIfNull(deliverbill.getDeliveryDepartment()) + split); // 派送部
			parameter.put("motorcade", defaultIfNull(deliverbill.getMotorcade()) + split); // 车队
			parameter.put("weightTotal", deliverbill.getWeightTotal()); // 总重量
			parameter.put("waybillQtyTotal", deliverbill.getWaybillQtyTotal()); // 总票数
			parameter.put("payAmountTotal", deliverbill.getPayAmountTotal()); // 总到付
			parameter.put("volumeTotal", deliverbill.getVolumeTotal()); // 总体积
			parameter.put("goodsQtyTotal", deliverbill.getGoodsQtyTotal()); // 总件数
			parameter.put("loadingRate", deliverbill.getLoadingRate()); // 装载率(重量/体积)
		}
		return parameter;
	}

	@Override
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> parameter;
		ApplicationContext applicationContext = jasperContext.getApplicationContext(); 
		if (vo != null && vo.getDeliverbillDetailList() != null) {
			List<DeliverbillDetailEntity> deliverbillDetails = vo.getDeliverbillDetailList();
			for (DeliverbillDetailEntity item : deliverbillDetails) {
				parameter = new HashMap<String, Object>();

				parameter.put("waybillNo", defaultIfNull(item.getWaybillNo() + split));// 运单号
				parameter.put("goodsName", defaultIfNull(item.getGoodsName() + split));// 货物名称
				parameter.put("goodsPackage", defaultIfNull(item.getGoodsPackage() + split));// 包装
				parameter.put("singleSignStatus", defaultIfNull(item.getSingleSignStatus()) + split);// 签收单
				parameter.put("goodsInfo", defaultIfNull(item.getGoodsInfo() + split + split));// 重量/体积/件数
				parameter.put("payAmount", item.getPayAmount());// 到付金额
				IProductService productService = (IProductService) applicationContext.getBean("productService");
				ProductEntity producTypeEntity = productService.getProductByCache(item.getTransportType(), new Date());
				parameter.put("transportType", defaultIfNull(producTypeEntity.getName()) + split);// 运输方式
				parameter.put("districtName", defaultIfNull(item.getDistrictName()) + split);// 行政区域 DMANA-4290
				if (StringUtil.isNotBlank(item.getConsigneeInfo())) {
					parameter.put("consigneeInfo", defaultIfNull(subStr(item.getConsigneeInfo(), NUMBER_66)) + split);// 收货人/联系方式/收货人地址
					
				}else{
					parameter.put("consigneeInfo", defaultIfNull(item.getConsigneeInfo()) + split);// 收货人/联系方式/收货人地址
				}
				if (StringUtil.isNotBlank(item.getDeliverRequire())) {
					parameter.put("deliverRequire", defaultIfNull(subStr(item.getDeliverRequire(),NUMBER_36)) + split);// 送货要求
				}else{
					parameter.put("deliverRequire", defaultIfNull(item.getDeliverRequire()) + split);// 送货要求
				}
				list.add(parameter);
			}
		} else {
			// 对于没有明细记录的信息，添加空map
			parameter = new HashMap<String, Object>();
			list.add(parameter);
		}
		return list;
	}
	
	/**
	 * 截取指定长度字符
	 * @param str
	 * @param max
	 * @return
	 */
	private String subStr(String str, int max){  
        int sum = 0;  
        if(str!=null ){  
            StringBuilder sb = new StringBuilder(max);  
            for (int i = 0; i < str.length(); i++) {  
                int c = str.charAt(i);  
                if((c & 0xff00) != 0)  
                    sum+=2;  
                else  
                    sum+=1;  
                if(sum<=max)  
                    sb.append((char)c);  
                else  
                    break;  
            }  
            return sb.toString();  
        }else  
            return str!=null ? str : "";  
}
	
	private String defaultIfNull(String str) {
		return StringUtil.defaultIfNull(str);
	}
	
	/**
	 * 
	 * 获取ID对应的预派送单信息
	 * 
	 * @author ibm-wangfei
	 * @date Nov 16, 2012 1:39:04 PM
	 */
	private DeliverbillVo getDeliverbillVo(JasperContext jasperContext, String deliverbillId) {
		vo = new DeliverbillVo();
		ApplicationContext applicationContext = jasperContext.getApplicationContext();
		IDeliverbillService deliverbillService = (IDeliverbillService) applicationContext.getBean("deliverbillService");
		vo.setDeliverbill(deliverbillService.queryDeliverbill(deliverbillId));
		vo.setDeliverbillDetailList(deliverbillService.queryDeliverbillDetailListForPrint(deliverbillId, 0, 99999));
//		 DeliverbillDto deliverbillDto = new DeliverbillDto();
//		 deliverbillDto.setDeliverbillNo("201200000007");
//		 vo.setDeliverbillDto(deliverbillDto);
//		 List<DeliverbillDetailEntity> items = new
//		 ArrayList<DeliverbillDetailEntity>();
//		 DeliverbillDetailEntity item;
//		 for (int i = 0; i < 144; i ++) {
//		 item = new DeliverbillDetailEntity();
//		 item.setWaybillNo("2012000" + i);
//		 item.setWeight(BigDecimal.valueOf(i));
//		 item.setDimension(i + "");
//		 item.setWaybillGoodsQty(i);
//		 item.setConsignee("张三" + i);
//		 item.setConsigneeContact("ABC" + i);
//		 item.setConsigneeAddress("BCD " + i);
//		 items.add(item);
//		 }
//		 vo.setDeliverbillDetailList(items);
		return vo;
	}
}