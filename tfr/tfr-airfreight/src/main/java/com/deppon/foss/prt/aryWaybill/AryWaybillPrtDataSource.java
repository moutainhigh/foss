/**
 *  initial comments.
 */
/*******************************************************************************
 *
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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/aryWaybill/AryWaybillPrtDataSource.java
 *  
 *  FILE NAME          :AryWaybillPrtDataSource.java
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
 *  选中一个或多个【航空正单显示列表】，点击打印按钮，则可以批量打印正单信息与外发清单信息
 *  
 * 
1、	弹出选择打印模板界面，默认选中航空正单模板；

2、	如果选中航空正单模板，参见SUC-269打印航空正单用例；

3、	如果选中空运外发托运书模板，参见SUC-280打印外发托运书；

4、	如果选中外发清单模板，参见SUC-369打印空运外发清单用例；

      **  选择合适模板后，点击确定按钮，打印选择的模板；**

1、	如果选中航空正单模板，参见SUC-269打印航空正单用例；

2、	如果选中空运外发托运书模板，参见SUC-280打印外发托运书；

3、	如果选中外发清单模板，参见SUC-369打印空运外发清单用例；




SR-1	1、	正单号来源航空正单的正单号；

2、	始发站、目的站来源于航空正单的始发站、目的站；
3、	托运人单位/姓名、托运人电话/手机分别来源航空正单的托运人和当前部门的电话；
4、	收货人单位/姓名、收货人电话/手机和收货人地址分别来源航空正单的收货人信息的收货人、电话和地址；
5、	货物名称、件数和重量分别来源于航空正单的货物名称、件数和重量；
6、	储运事项来源于航空正单的航班号和运价拼接而成，形如：航班号+“；”+“运价 ”+运价；
7、	提货方式来源于航空正单的提货方式；
8、	外发代理来源于航空正单的外发代理，时间为航空正单制单时间，制单人为航空正单的制单人；
9、	以上信息填入外发托运书后，均能在外发托运书中进行修改，且修改只是针对本次打印，不牵涉其他信息修改；

SR-2	件数和重量输入限制为大于零的数字；




SR-1	航空公司数字前缀、航空公司LOGO和名称，在输入航空公司代码后，匹配航空公司基础资料自动生成，且不可更改。（目前只有南航，有数字前缀）

SR-2	件数：对应的正单号配载时的件数，自动生成，不可更改；

SR-3	重量：对应的正单号配载时的重量，自动生成，不可更改；

SR-4	始发站：对应的正单号的配载城市，自动生成，不可更改；

SR-5	目的站：对应的正单号配载时的目的站，自动生成，不可更改；

SR-6	打印数量：默认为件数的数值，但可手工修改；

SR-7	当前打印机自动搜索与电脑连接的打印机，并默认显示上一次使用的打印机型号；

SR-8	航空正单标签打印格式：长10厘米*宽7厘米。

SR-9	输入正单号时，系统加载显示正单号信息：件数、重量、始发站、目的站。

SR-10	如果件数与打印数量不一致时，则系统提示“件数与打印数量不一致，请确认”，系统只只做提示，即可。


 *  
 ******************************************************************************/
package com.deppon.foss.prt.aryWaybill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;


public class AryWaybillPrtDataSource implements MultiJasperDataSource {
	
	/*
	 * 
	 * */

	//航空正单打印数据
	private List<AirWaybillEntity> list;
	//根据id获取打印数据
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		String[] ids = null;
		String id = null;
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		if(jasperContext.get("isNotbatchPrint").equals("1")){
			ids = jasperContext.get("ids").toString().split(",");
			for(String idsa : ids ){
				data.add(new MyJasperDataSource(idsa));
			}
		}else{
			id = jasperContext.get("record").toString();
			data.add(new MyJasperDataSource(id));
		}
		return data.toArray(new JasperDataSource[0]);
	}
	class MyJasperDataSource implements JasperDataSource {
			String ids = null;
			
			public MyJasperDataSource(String ids){
				this.ids = ids;
			}
		
		public void queryAirWaybillListByPrint(JasperContext jasperContext){
			IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService", IAirWaybillService.class);
			//根据ID查询运单明细
			
			String[] ids = new String[1];
			ids[0] = this.ids;
			list = airWaybillService.queryAirWaybillListPrint(ids);
		}
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			queryAirWaybillListByPrint(jasperContext);
			return parameter;
		}
	
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
				List<Map<String, Object>> map = new ArrayList<Map<String, Object>>(list.size());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				for(int i=0;i<list.size();i++){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					AirWaybillEntity entitys = list.get(i);
					if(entitys.getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE))
					{
						entitys.setItemCode("");
					}
					//要求起始站和目的站不能有"市"
					//设置始发站
					dataMap.put("deptRegionName",simpleRegionName(entitys.getDeptRegionName()));
					//设置目的站
					dataMap.put("arrvRegionName",simpleRegionName(entitys.getArrvRegionName()));
					//设置托运人姓名
					dataMap.put("shipperName",entitys.getShipperName());
					//设置托运人地址
					dataMap.put("shipperAddress",entitys.getShipperAddress());
					//设置托运人电话
					dataMap.put("shipperContactPhone",entitys.getShipperContactPhone());
					//设置收货人姓名
					dataMap.put("receiverName",entitys.getReceiverName());
					//设置收货人地址
					dataMap.put("receiverAddress",entitys.getReceiverAddress());
					//设置收货人电话
					dataMap.put("receiverContactPhone",entitys.getReceiverContactPhone());
					//设置结算事项
					dataMap.put("accountItem",entitys.getAccountItem());
					//设置填开代理
					dataMap.put("billingAgency",entitys.getBillingAgency());
					//设置航班号
					dataMap.put("flightNo",entitys.getFlightNo()+"/"+format.format(entitys.getFlightDate()));
					//设置航班日期
					dataMap.put("flightDate",format.format(entitys.getFlightDate()));
					//设置航空公司二字代码
					dataMap.put("airLineTwoletter",entitys.getAirLineTwoletter());
					//设置声明价值
					dataMap.put("declareValue",entitys.getDeclareValue());
					//设置储运事项
					dataMap.put("storageItem",entitys.getStorageItem());
					//设置件数
					dataMap.put("goodsQty",entitys.getGoodsQty());
					//设置毛重
					dataMap.put("grossWeight",entitys.getGrossWeight());
					//设置商品代号
					System.err.println(entitys.getItemCode());
					dataMap.put("itemCode",entitys.getItemCode());
					//设置计费重量
					dataMap.put("billingWeight",entitys.getBillingWeight());
					//设置名称和包装
					dataMap.put("goodsName",entitys.getGoodsName());
					//设置费用说明
					dataMap.put("feePlain",entitys.getFeePlain());
					//航空运费、附加费、地面运费、其他费用、保险费、总金额不用打印
					if(AirfreightConstants.AIRFREIGHT_ISNOTMONEY.equals(jasperContext.get("isnotMoney"))){
						//设置运价
						dataMap.put("fee",entitys.getFee());
						//设置航空运费
						dataMap.put("airFee",entitys.getAirFee());
						//设置附加费
						dataMap.put("extraFee",entitys.getExtraFee());
						//设置地面运费
						dataMap.put("groundFee",entitys.getGroundFee());
						//设置其他运费  “其他费用”对应“燃油附加税”
						dataMap.put("parameter1",entitys.getFuelSurcharge());
						//设置保险费
						dataMap.put("inseranceFee",entitys.getInseranceFee());
						//设置总金额
						dataMap.put("feeTotal",entitys.getFeeTotal());
					}
					//设置付款方式
					dataMap.put("paymentType",DictUtil.rendererSubmitToDisplay(entitys.getPaymentType(), "PAYMENT_TYPE"));
					//设置 制单人
					dataMap.put("createUserName",entitys.getCreateUserName());
					//设置制单日期
					dataMap.put("createTime",format.format(entitys.getCreateTime()));
					map.add(dataMap);
				}
				return map;
		}
		
		//此方法不能完全保证取到的地区名称是符合实际情况的，暂时使用，因为系统中取不到地区简称
		public String simpleRegionName(String regionName){
			if(regionName == null){
				return "";
			}
			if(regionName.endsWith("市") || regionName.endsWith("盟")){
				return regionName.substring(0, regionName.length()-1);
			}else if(regionName.endsWith("地区")){
				return regionName.substring(0, regionName.length()-2);
			}else if(regionName.startsWith("黔东南") || regionName.startsWith("黔西南")){
				return regionName.substring(0,ConstantsNumberSonar.SONAR_NUMBER_3);
			}else if(regionName.startsWith("博尔塔拉") || regionName.startsWith("巴音郭楞")
					|| regionName.startsWith("西双版纳")){
				return regionName.substring(0,ConstantsNumberSonar.SONAR_NUMBER_4);
			}else{
				return regionName.substring(0,2);
			}
		}
	}
}