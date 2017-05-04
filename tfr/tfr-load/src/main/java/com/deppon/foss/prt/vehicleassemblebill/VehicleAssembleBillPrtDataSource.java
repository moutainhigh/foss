/**
 *  initial comments.
 *  *  
 *  
 *  1.5.3	界面描述
交接单打印模板：如图分为三个部分：
	一、表头信息区域，
	二、运单明细信息区域，
	三、需人工核对信息区域。
一、	表头信息区域：
1.	包含信息：
1.1	表头统一记录为车载货物配载单；
1.2	车次：HZGZ1204701，又名配载车次号；
1.3	车牌号：格式为粤A2012；
1.4	驾驶员：司机姓名；
1.5	交接单编号：分为两类，一是带字母P的（为PDA装车），一是不带字母P 的（非PDA装车）；eg：10708077,10708078P；（可以为多个）。
1.6	下划线；
1.7	To： 交接到达部门，eg：上海专线；
1.8	FROM：交接出发部门，eg：上海青浦重固营业部；来源于“生成交接单”。
1.9	交接类型：统一为配载单
以上信息来源于“新增配载单”。
二、	运单明细信息区域：
2.	包含信息：
1.1	从左到右，信息类型如下：运输性质（简称）、运单号、收货部门、目的站、收货人/联系方式、品名包装、件数（交接件数/开单件数）、重量、体积、备注；
交接件数明细：格式1/2/3……；对应交接件数。
来源于“新增配载单”,此字段信息显示在打印模板中。
1.2	运输性质（简称）字段不显示打印界面。
1.3	件数的格式为交接单数/开单件数，开单件数为运单开单时的总件数，交接件数：为已交接出库的件数。
1.4	交接件数明细字段，打印显示原则，当选择类型：打印配载单（流水号）时，打印模板中显示交接件数明细，当选择类型：打印配载单时，打印模板中不显示交接件数明细。
1.5	配载单打印结果的顺序：在文档中确认打印顺序，系统默认按照运输性质、运单号升序来来打印。
三、	需人工核对信息区域：
3.	包含信息：出发填写、到达填写；货物汇总信息处；
1.1	出发填写：
出发交接司机签字：此为空，后续人工签字；
业务要求说明：签收单以后会附在货物上，将不会再通过司机与营业员或操作员的交接方式来管理；货单同行、附信封以后会单独成件的方式来管理；
1.2	到达填写:
到达司机签字：______________,此处为空，后续人工签字处；
业务要求说明：签收单以后会附在货物上，将不会再通过司机与营业员或操作员的交接方式来管理；货单同行、附信封以后会单独成件的方式来管理；
1.3	货物汇总信息处：
装车人：XXX，总件数：XX，总重量：XX，总体积：XX，总金额：XX，制单人；XX，制单日期：2012-02-03  08:06；
本页票数：XX（系统按照页来累计每页中的票数），
封签号：
后门：7110522/7110523，侧门：X/X
右下角显示打印页数：第X页共X页；
显示打印时间：为当前服务器时间，2012-02-03  08:06；
信息来源“新增配载单”，数据算法引用业务规则：SR-4。
1.6	操作步骤
1.6.1	查询及打印交接单
序号	基本步骤	相关数据	补充步骤
1	进入查询交接单或配载单界面		界面加载时默认显示本部门信息
2	填写查询条件，点击“查询“按钮，查找出满足条件的交接单	【配载单明细列表】（打印）	引用“查询交接单/配载单”
3	勾选需要打印的配载单，显示勾选配载单明细；	【配载单明细列表】（打印）	人工勾选前面的复选框
4	查看配载单明细	【配载单明细列表】（打印）	配载单打印顺序：系统默认按运输性质、运单号升序排序
5	点击打印预览配载单按钮，预览配载单中的运单明细信息；	【配载单明细列表】（打印）	预览
6	点击“打印 ”按钮，进入打印选择框，选择配载单打印或配载单（流水号）打印。	【配载单明细列表】（打印）	1.系统按照勾选模板打印信息；
2.点击打印按钮时，假如有属于同一车牌号的配载单，没有勾选时，系统提示用户“此车牌中还有X个配载单没有选择打印，是否也要打印？“（两地卸货时，一个车上会有多个配载单）。


序号	扩展事件	相关数据	备注
2a	通过勾选配载单明细列表复选框，来选择配载单打印对象	【配载单明细列表】	系统支持多张配载单同时打印。
也就是N份打印清单。而不是打印到同一份清单里面。
6a	按钮，进入打印选择框，选择配载单打印或配载单（流水号）打印。	配载单打印、配载单（流水号）打印	根据选择项，加载对应的打印模板信息。 


1.7	业务规则
序号	描述
SR-1	名称：配载单打印。
SR-2	操作员只能打印系统最新版本的交接单。
SR-3	打印顺序：系统默认按照运输性质、运单号升序打印。
SR-4	本页票数=本页配载单中票数累积之和；
总件数=配载单中件数累积之和；
总重量=配载单中重量累积之和；单位为kg（无需带单位）
总体积=配载单中货物体积累积之和；单位为方(无需带单位)
总金额=配载单中装车总金额；
打印时间为服务器时间
业务显示要求：第XX页工XX页。
SR-5	一个车牌号可以对应多个配载单（两地卸货，就有多个配载单）；
点击打印按钮时，假如有属于同一车牌号的配载单，没有勾选时，系统提示用户“此车中还有X个配载单没有选择打印，是否也要打印？“。
SR-6	系统支持多张配载单同时打印。
也就是N份打印清单。而不是打印到同一份清单里面。
SR-7	运输性质：在打印短配交接单/集配交接单/打印外发交接单时，运输性质列简称为：卡、普、空、城
SR-8	交接单明细中的单号，打印字体设置大一号且加粗，方便操作员在交接单明细中通过单号查找定位。
SR-9	配载单每份都可以打印，不强制，只有最后到达的才显示金额。

1.8	数据元素
1.8.1	配载单明细列表（打印）
字段名称 	说明 	输入限制	提示输入本文	长度	是否必填	备注
车载货物交接单	集配时，统一记录为车载货物交接单；	文本		7	是	系统打印时分配
交接单编号	分为带P或不带p，eg：10708077,10708078P	N/A	N/A	N/A	N/A	
运输性质	货物运输类型，卡货、普货、城际等，	N/A	N/A	N/A	N/A	
收货人	收货联系人，来源运单信息	N/A	N/A	N/A	N/A	
收货人联系方式	收货联系人手机或电话，来源运单信息	N/A	N/A	N/A	N/A	
包装信息	来源运单信息	N/A	N/A	N/A	N/A	
收货部门	货物始发部门	N/A	N/A	N/A	N/A	
目的站	货物最终到达部门	N/A	N/A	N/A	N/A	
总体积	交接单的货物总体积，内容读取新增配载单	N/A	N/A	N/A	N/A	
总重量	交接单的货物总重量，内容读取新增配载单	N/A	N/A	N/A	N/A	
总件数	交接单的货物总件数，内容读取新增配载单	N/A	N/A	N/A	N/A	
本页票数	交接单中运单的个数	数字			是	系统打印时按页来分配
制单人	交接单的制单人	N/A	N/A	N/A	N/A	
制单日期	该交接单的生成日期	N/A	N/A	N/A	N/A	包括起始日期和截止日期，格式为：格式为：2012-04-01 08:30
车牌号	负责装载、运输某交接单货物的车辆的车牌号	N/A	N/A	N/A	N/A	
车次号	配载车次号，来源与“新增配载单”	N/A	N/A	N/A	N/A	
司机	负责该交接单的车辆运输的人员	N/A	N/A	N/A	N/A	
装车人	负责该交接单装车的人员	N/A	N/A	N/A	N/A	
交接类型	货物配载交接单（集配时）	N/A	N/A	N/A	N/A	
封签号	车辆的封签号	N/A	N/A	N/A	N/A	
备注	交接单的备注信息	N/A	N/A	N/A	N/A	
打印时间	服务器时间，打印时间	日期			是	
打印页数	第X页共X页。	文本				系统打印时分配
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/vehicleassemblebill/VehicleAssembleBillPrtDataSource.java
 *  
 *  FILE NAME          :VehicleAssembleBillPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.vehicleassemblebill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillHeaderDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.util.UUIDUtils;

/**
 * 配载单打印
 * @author ibm-zhangyixin
 * @date 2013-1-6 上午10:21:38
 */
public class VehicleAssembleBillPrtDataSource implements MultiJasperDataSource {

	/****
	 * 批量打印的jasperDataSource
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 下午4:04:33
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		//获取配载单单号(有可能为一个集合, 也可能是一个String)
		Object obj = jasperContext.get("vehicleAssembleNos");
		String[] vehicleAssembleNos = null;
		if(obj instanceof String) {
			vehicleAssembleNos = new String[1];
			vehicleAssembleNos[0] = (String) obj;
		} else {
			vehicleAssembleNos = (String[]) obj;
		}
		
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		for(String vehicleAssembleNo : vehicleAssembleNos ){
			data.add(new MyJasperDataSource(vehicleAssembleNo));
		}
		
		return data.toArray(new JasperDataSource[0]);
	}
	
	/**
	 * 打印时需要记录该配载单的打印记录 
	 * @author ibm-zhangyixin
	 * @date 2012-11-21 下午5:19:15
	 */
	public void addVehicleAssembleBillRecord(String vehicleassembleNo, IVehicleAssembleBillService vehicleAssembleBillService,
			String currentDeptCode, String currentDeptName, String currentUserCode, String currentUserName) {
		//汽运配载单打印记录
		MotorstowagebillrecordEntity motorstowagebillrecord = new MotorstowagebillrecordEntity();
		//设置ID
		motorstowagebillrecord.setId(UUIDUtils.getUUID());
		//设置打印的类型为"配载单"
		motorstowagebillrecord.setPrintType("VEHICLEASSEMBLEBILL");	//类型为配载单
		//设置配载单号
		motorstowagebillrecord.setVehicleassembleNo(vehicleassembleNo);
		//设置打印用户名
		motorstowagebillrecord.setPrinterName(currentUserName);
		//设置打印用户编号
		motorstowagebillrecord.setPrinterCode(currentUserCode);
		//设置打印时间
		motorstowagebillrecord.setPrintTime(new Date());
		//设置部门号
		motorstowagebillrecord.setOrgCode(currentDeptCode);
		//设置部门名称
		motorstowagebillrecord.setOrgName(currentDeptName);
		//插入操作
		vehicleAssembleBillService.insertMotorstowagebillrecord(motorstowagebillrecord);
	}
	
	class MyJasperDataSource implements JasperDataSource {
		String vehicleAssembleNo = null;
		
		public MyJasperDataSource(String handOverBillNo){
			this.vehicleAssembleNo = handOverBillNo;
		}

		/****
		 * 创建打印参数数据源
		 * @author ibm-zhangyixin
		 * @date 2013-3-14 下午4:04:59
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//当前登录用户部门Code
			String currentDeptCode = (String) jasperContext.get("currentDeptCode");
			//当前登录用户Code
			String currentUserCode = (String) jasperContext.get("currentUserCode");
			//当前登录用户Name
			String currentUserName = (String) jasperContext.get("currentUserName");
			//当前登录用户部门Name
			String currentDeptName = (String) jasperContext.get("currentDeptName");
			//配载单service
			IVehicleAssembleBillService vehicleAssembleBillService = (IVehicleAssembleBillService) applicationContext.getBean("vehicleAssembleBillService");
			//打印所需要的数据
			PrintVehicleAssembleBillHeaderDto vehicleAssembleBill = vehicleAssembleBillService.queryVehicleAssembleBillPrtDataSource(vehicleAssembleNo);
			//根据配载单号查询出装车人
			String loaderName = vehicleAssembleBillService.queryLoaderNameByVehicleassemblebillId(vehicleAssembleBill.getId());
			//查询出该配置单下所有交接单的汇总
			PrintVehicleAssembleBillHeaderDto vehicleAssembleBillSummary = vehicleAssembleBillService.querySummaryHandOverBillByVehicleassemblebill(vehicleAssembleBill.getId());
			//根据车次号查询出装车封签号, 结果为 7110522/7110523/XXXXX 类型
			String sealNos = vehicleAssembleBillService.querySealNosByVehicleAssembleNo(vehicleAssembleNo, null);
			//打印中需要的参数
			Map<String, Object> parameter = new HashMap<String, Object>();
			//配载单号
			parameter.put("vehicleAssembleNo", vehicleAssembleBill.getVehicleAssembleNo());
			//车牌号
			parameter.put("vehicleNo", vehicleAssembleBill.getVehicleNo());
			//司机
			parameter.put("driverName", vehicleAssembleBill.getDriverName());
			//交接单号
			String handoverBillNos = vehicleAssembleBill.getHandOverBillNos();
			String[] handoverBillNosStr = StringUtils.split(handoverBillNos, ",");
			StringBuffer handoverNo = new StringBuffer();
			if(handoverBillNosStr != null) {
				for(String str : handoverBillNosStr) {
					handoverNo.append(str).append("\n");
				}
			}
			parameter.put("handOverBillNos", handoverNo.toString());
			//到达部门
			parameter.put("destOrgName", vehicleAssembleBill.getDestOrgName());
			//出发部门
			parameter.put("origOrgName", vehicleAssembleBill.getOrigOrgName());
			//配载类型en
			String assembleType = vehicleAssembleBill.getAssembleType();
			if(StringUtils.equals(assembleType, "OWNER_LINE")){
				assembleType = "专线";
			} else if(StringUtils.equals(assembleType, "CAR_LOAD")){
				assembleType = "整车";
			}
			//配载类型cn
			parameter.put("assembleType", assembleType);
			//装车人
			parameter.put("loaderName", loaderName);//装车人, 从交接单里随便取一个
			if(vehicleAssembleBillSummary != null) {
				//总件数
				parameter.put("goodsQtyTotal", vehicleAssembleBillSummary.getGoodsQtyTotal());
				//总重量
				parameter.put("weightTotal", vehicleAssembleBillSummary.getWeightTotal());
				//总体积
				parameter.put("volumeTotal", vehicleAssembleBillSummary.getVolumeTotal());
			} else {
				//总件数
				parameter.put("goodsQtyTotal", BigDecimal.ZERO);
				//总重量
				parameter.put("weightTotal", BigDecimal.ZERO);
				//总体积
				parameter.put("volumeTotal", BigDecimal.ZERO);
			}
			//打印用户
			parameter.put("createUserName", vehicleAssembleBill.getCreateUserName());
			//打印时间
			parameter.put("createTime", vehicleAssembleBill.getCreateTime());
			//后门封签
			parameter.put("sealNos", sealNos);
			//插入配载单打印记录
			addVehicleAssembleBillRecord(vehicleAssembleNo, vehicleAssembleBillService, 
					currentDeptCode, currentDeptName, currentUserCode, currentUserName);
			return parameter;
		}

		/****
		 * 创建打印field数据源
		 * @author ibm-zhangyixin
		 * @date 2013-3-14 下午4:05:52
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//配载单serivce
			IVehicleAssembleBillService vehicleAssembleBillService = (IVehicleAssembleBillService) applicationContext.getBean("vehicleAssembleBillService");
			//产品service
			IProductService productService = (IProductService) applicationContext.getBean("productService4Dubbo");
			//产品信息Dto
			ProductDto condtion = new ProductDto();
			//只查询出3级的产品
			condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
			//查询出所有3级的产品
			List<ProductEntity> productEntityList = productService.findExternalProductByCondition(condtion, null);
			//把查出来的产品根据 name-shorname 放在一个map中, 方便取值
			Map<String, String> productMap = new HashMap<String, String>(productEntityList.size());
			for(ProductEntity product : productEntityList) {
				if(StringUtils.isNotBlank(product.getShortName())){
					productMap.put(product.getName(), product.getShortName());
				}else{
					productMap.put(product.getName(), "");
				}
				
			}
			//查询出打印中需要的数据
			PrintVehicleAssembleBillHeaderDto vehicleAssembleBill = vehicleAssembleBillService.queryVehicleAssembleBillPrtDataSource(vehicleAssembleNo);
			//查询出打印中detail中的数据
			List<PrintVehicleAssembleBillDetailDto> vehicleAssembleBillDetails = vehicleAssembleBillService.queryVehicleAssembleBillDetailPrtDataSource(vehicleAssembleBill.getId());
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>(vehicleAssembleBillDetails.size());
			for(PrintVehicleAssembleBillDetailDto vehicleAssembleBillDetail : vehicleAssembleBillDetails) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				//运单号
				String waybillNo = vehicleAssembleBillDetail.getWaybillNo();
				//交接单号
				String handOverNo = vehicleAssembleBillDetail.getHandoverNo();
				//运输类型(简称)
				parameter.put("transportType", productMap.get(vehicleAssembleBillDetail.getTransportType()));
				//运单号
				parameter.put("waybillNo", waybillNo);
				//收货部门
				parameter.put("receiveOrgName", vehicleAssembleBillDetail.getReceiveOrgName());
				//目的站
				parameter.put("reachOrgName", vehicleAssembleBillDetail.getReachOrgName());
				//收货人
				parameter.put("receiveCustomerName", vehicleAssembleBillDetail.getReceiveCustomerName());
				//收货电话
				String mobilPhone = vehicleAssembleBillDetail.getReceiveCustomerMobilephone();
				//如果mobilePhone为空则取另外一个的
				if(StringUtils.isEmpty(mobilPhone)) {
					//收货电话
					String phone = vehicleAssembleBillDetail.getReceiveCustomerPhone();
					mobilPhone = phone;
				}
				//联系电话
				parameter.put("receiveCustomerMobilephone", mobilPhone);
				//收货人
				String consigneeinfo = "";
				//收货人
				String receiveCustomerName = vehicleAssembleBillDetail.getReceiveCustomerName();
				if(StringUtils.isNotEmpty(receiveCustomerName)) {
					consigneeinfo = receiveCustomerName + "/";
				}
				if(StringUtils.isNotEmpty(mobilPhone)) {
					consigneeinfo += mobilPhone;
				}
				//收货人
				parameter.put("consigneeinfo", consigneeinfo);
				//品名
				parameter.put("goodsName", vehicleAssembleBillDetail.getGoodsName());
				//包装
				parameter.put("goodsPackage", vehicleAssembleBillDetail.getGoodsPackage());
				//交接件数
				parameter.put("handoverGoodsQty", vehicleAssembleBillDetail.getHandoverGoodsQty());
				//总件数
				parameter.put("goodsQtyTotal", vehicleAssembleBillDetail.getGoodsQtyTotal());
				//总重量
				parameter.put("goodsWeightTotal", vehicleAssembleBillDetail.getGoodsWeightTotal());
				//总体积
				parameter.put("goodsVolumeTotal", vehicleAssembleBillDetail.getGoodsVolumeTotal());
				//备注
				parameter.put("waybillNotes", vehicleAssembleBillDetail.getWaybillNotes());
				//流水号
				String serialNos = vehicleAssembleBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverNo);
				//流水号
				parameter.put("serialNos", serialNos);
				
				data.add(parameter);
			}
			return data;
		}
	}
}