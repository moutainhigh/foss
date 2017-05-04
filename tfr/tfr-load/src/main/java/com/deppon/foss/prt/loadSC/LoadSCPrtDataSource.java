/**
 *  initial comments.
 *  *  
 *  1.5.3	界面描述
交接单打印模板：如图分为三个部分：一、表头信息区域，二、运单明细信息区域，三、需人工核对信息区域。
一、	表头信息区域：
1.	包含信息：
1.1	To：交接到达部门，eg：上海专线；来源于“新增交接单”。
1.2	FROM：交接出发部门，eg：上海青浦重固营业部；来源于“新增交接单”。
1.3	交接单编号：系统自动分配的交接编号，新增交接单时系统产生，eg：21962843；来源于“新增交接单”。
1.4	车牌号：出发车辆编号，eg：粤A20581；来源于“新增交接单”。
1.5	备注:出发部门备注的异常信息；来源于“新增交接单”。
1.6	日期（制单）：指交接单制单时间，eg：2012-03-06  08:00；来源于“新增交接单”。
1.7	打印部门：系统登录账号所属部门，eg：上海青浦重固营业部； 
1.8	交接单类型：目前有以下几类类型，短配交接单、集配交接单、外发交接单；来源于“新增交接单”。
二、	运单明细信息区域：
2.	包含信息：
2.1	从左到右，信息类型如下：运输性质（简称）、运单号、收货人及电话、收货部门、件数、重量、体积、目的站、品名包装、提货方式；来源于“新增交接单”。
2.2	交接件数明细：格式1/2/3/4；对应件数明细。（件数与流水号尾数一一对应）。当选择打印类型为：交接单（流水号）打印时，才显示交接件数明细内容。
2.3	件数格式：为交接件数/开单件数。对应1.2显示交接件数明细。
2.4	打印结果的顺序：在文档中确认打印顺序，系统默认按照运输性质、运单号升序来来打印。
三、	需人工核对信息区域：
3.	包含信息：出发填写、到达填写；货物汇总信息处；
3.1	出发填写：
封签号：
后门：65551537/1111111；侧门：65551538/1111112；
封签号：来源“新增交接单”。
出发装完车时间：2012-06-08 08:00;为PDA装车最后一次提交任务时间，假如无PDA，则为空；后续人工纸质签字填写；
装车人签字：此为空，后续人工签字处；
出发交接司机签字：此为空，后续人工签字；
业务要求说明：签收单以后会附在货物上，将不会再通过司机与营业员或操作员的交接方式来管理；货单同行、附信封以后会单独成件的方式来管理；
3.2	到达填写:
到达具体时间：_月_日_点_分,此处为空，后续人工签字处；
封签号是否正确：_________________,此处为空，后续人工签字处；
卸车人签字：________________,此处为空，后续人工签字处；
到达司机签字：______________,此处为空，后续人工签字处；
业务要求说明：签收单以后会附在货物上，将不会再通过司机与营业员或操作员的交接方式来管理；货单同行、附信封以后会单独成件的方式来管理；
3.3	货物汇总信息处：
3.3.1	包含信息： 票数、总件数、总重量、总体积、制单人；信息来源“新增交接单”，数据算法引用业务规则：SR-4。
3.3.2	右下脚显示打印页数（第X页共X页）、打印时间（为服务器时间）。
1.6	操作步骤
1.6.1	查询及打印交接单
序号	基本步骤	相关数据	补充步骤
1	进入查询交接单或配载单界面		界面加载时默认显示本部门信息
2	填写查询条件，点击“查询“按钮，查找出满足条件的交接单	【交接单明细列表】（打印）	
3	勾选需要打印的交接单，显示勾选交接单明细；	【交接单明细列表】（打印）	人工勾选前面的复选框
4	查看交接单明细，按照交接单字段排序；	【交接单明细列表】（打印）	系统默认按运输性质、运单号升序排序。
5	点击打印预览交接单按钮，预览交接单中的运单明细信息；	【交接单明细列表】（打印）	预览
6	点击“打印“按钮，打印交接单明细	【交接单明细列表】（打印）	打印时会打印出已选的交接单明细；
点击打印按钮时，假如有属于同一车牌号的交接单，没有勾选时，系统提示用户“此车牌中还有X个交接单没有选择打印，请勾选“


序号	扩展事件	相关数据	备注
1	勾选交接单明细列表复选框，来选择打印对象	【交接单明细列表】（打印）	系统支持多张交接单同时打印。
也是N分打印清单。而不是打印到同一份清单里面。
2	勾选交接单明细列表复选框	【交接单明细列表】（打印）	交接单明细列表中已勾选项，记录为待打印；
点击打印按钮时，记录为已打印。 


1.7	业务规则
序号	描述
SR-1	打印名称为交接单打印；
模板中的交接类型：短配交接单及集配交接单、外发交接单。
SR-2	营业员、操作员只能打印系统最新版本的交接单，并在纸质交接单上显示版本号；
SR-3	打印顺序：系统默认按照运输性质、运单号升序打印。
SR-4	票数=交接单中票数累积之和；
总件数=交接单中件数累积之和；
总重量=交接单中重量累积之和；单位为kg（无需带单位）
总体积=交接单中货物体积累积之和；单位为方(无需带单位)
打印时间为服务器时间
SR-5	点击打印按钮时，假如有属于同一车牌号的交接单，没有勾选时，系统提示用户“此车中还有X个交接单没有选择打印，请勾选 “。
SR-6	系统支持多张交接单同时打印。
也是N分打印清单。而不是打印到同一份清单里面。
SR-7	在打印短配交接单/集配交接单/打印外发交接单时，运输性质列简称为：卡、普、空、城
SR-8	交接件数明细：显示格式1/2/3/6/8；字体加粗。
数据来源：已交接件数（交接件数不一定为开单件数，分批配载）。
交接件数明细打印显示原则：
当选择打印模板类型：交接单（流水号）打印时，才会显示交接件数明细。交接单打印时，不显示交接件数明细行。
SR-9	交接单明细中的单号，打印字体设置大一号且加粗，方便操作员在交接单明细中通过单号查找定位。
SR-10	交接件数明细：按照流水号递增原则，显示流水号尾数。
SR-11	交接单生成之后要区分开 空运、贵重物品、代打木架货物，打印出来可以明显区别。先按照 空运、贵重物品、代打木架货物，其他的顺序显示货物

1.8	数据元素
1.8.1	交接单明细列表（打印）
字段名称 	说明 	输入限制	提示输入本文	长度	是否必填	备注
交接单编号	PDA或者FOSS系统自动生成的交接单编号，可作为交接单的唯一标识	N/A	N/A	N/A	N/A	内容读取交接单
运输性质	货物运输类型简称，卡、普、城、空等，来源运单信息。	N/A	N/A	N/A	N/A	
收货人	收货联系人，来源运单信息	N/A	N/A	N/A	N/A	
收货人联系方式	收货联系人手机或电话，来源运单信息	N/A	N/A	N/A	N/A	
提货方式	客户提货的方式，自提或派送，来源运单信息	N/A	N/A	N/A	N/A	
包装信息	来源运单信息	N/A	N/A	N/A	N/A	
收货部门	货物始发部门	N/A	N/A	N/A	N/A	
目的站	货物最终目的站	N/A	N/A	N/A	N/A	
总体积	交接单的货物总体积	N/A	N/A	N/A	N/A	默认为方，单位不显示
总重量	交接单的货物总重量	N/A	N/A	N/A	N/A	默认为千克，单位不显示
总件数	交接单的货物总件数	N/A	N/A	N/A	N/A	
总票数	交接单中运单的个数	N/A	N/A	N/A	N/A	
制单日期	该交接单的生成日期	N/A	N/A	N/A	N/A	包括起始日期和截止日期，格式为：格式为：2012-04-01 08:30
车牌号	负责装载、运输某交接单货物的车辆的车牌号	N/A	N/A	N/A	N/A	
司机	负责该交接单的车辆运输的人员	文本			否	
装车人	负责该交接单装车的人员	文本			否	
交接类型	交接单的交接类型	N/A	N/A	N/A	是	
封签号	车辆的封签号	N/A	N/A	N/A	是	
备注	交接单的备注信息	N/A	N/A	N/A	否	
制单人	交接单的制单人	N/A	N/A	N/A	是	
出发装车时间	交接单的装车完成时间，格式为：2012-04-01 08:30	N/A	N/A	N/A	否	来源最后一次PDA装车任务提交时间或FOSS系统装车结束时间
车辆到达时间	交接单车辆到达的时间，格式为：2012-04-01 08:30	日期		[0,40]	否	人工签字
卸车人	负责此交接单卸货的人员	文本			否	人工签字

1.9	非功能性需求（可选）
使用量	10000~15000（外场+营业部）；备注：后期PDA全面推广，将实现无单化操作；交接单打印的业务量将会减少。
2012年全网估计用户数	2000~3000
响应要求（如果与全系统要求 不一致的话）	查询响应与全系统要求一致
使用时间段	全天
高峰使用时段	下午5：00-次日9：00

 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/load/LoadPrtDataSource.java
 *  
 *  FILE NAME          :LoadPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.loadSC;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.util.UUIDUtils;

/**
 * 交接单打印
 * 可以批量打印
 * @author ibm-zhangyixin
 * @date 2013-1-6 上午10:20:54
 */
public class LoadSCPrtDataSource implements MultiJasperDataSource {
	/****
	 * 批量打印的jasperDataSource
	 * @author foss-zenghaibin 205109
	 * @date 2013-3-14 下午3:54:51
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		//获取交接单单号(有可能为一个集合, 也可能是一个String)
		Object obj = jasperContext.get("connectionBillNos");
		String[] connectionBillNos = null;
		if(obj instanceof String) {
			connectionBillNos = new String[1];
			connectionBillNos[0] = (String) obj;
		} else {
			connectionBillNos = (String[]) obj;
		}
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		for(String connectionBillNo : connectionBillNos ){
			data.add(new MyJasperDataSource(connectionBillNo));
		}
		
		return data.toArray(new JasperDataSource[0]);
	}
	
	/**
	 * 打印时需要记录该交接单的打印记录 
	 * @author ibm-zhangyixin
	 * @date 2012-10-30 下午5:19:15
	 */
	public void addHandoverbillPrintrecord(String handOverBillNo, Date createTime, IHandOverBillService handOverBillService, 
			String currentDeptCode, String currentDeptName, String currentUserCode, String currentUserName) {
		
		//交接单打印记录
		HandoverBillPrintRecordEntity handoverBillPrintRecord = new HandoverBillPrintRecordEntity();
		//设置ID
		handoverBillPrintRecord.setId(UUIDUtils.getUUID());
		//设置交接单号
		handoverBillPrintRecord.setHandoverNo(handOverBillNo);
		//设置打印用户code
		handoverBillPrintRecord.setOperatorCode(currentUserCode);
		//设置打印用户name
		handoverBillPrintRecord.setOperatorName(currentUserName);
		//设置打印时间
		handoverBillPrintRecord.setOperatTime(new Date());
		//设置打印部门code
		handoverBillPrintRecord.setOrgCode(currentDeptCode);
		//设置打印部门name
		handoverBillPrintRecord.setOrgName(currentDeptName);
		//设置创建时间
		handoverBillPrintRecord.setCreateTime(createTime);
		//插入操作
		handOverBillService.addHandoverbillPrintrecord(handoverBillPrintRecord);
	}
	
	/****
	 * 
	 * 交接单打印
	 * @author foss-zenghaibin-205109
	 * @date 2013-3-15 下午3:57:21
	 */
	class MyJasperDataSource implements JasperDataSource {
		//交接单号
		String connectionBillNo = null;
		
		public MyJasperDataSource(String connectionBillNo){
			this.connectionBillNo = connectionBillNo;
		}
		
		/****
		 * 创建打印参数数据源
		 * @author foss-zenghaibin-205109
		 * @date 2013-3-14 下午3:55:56
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
			//当前登录用户部门名称
			String currentDeptName = (String) jasperContext.get("currentDeptName");
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//交接单service
			IConnectionBillService connectionBillService = (IConnectionBillService) applicationContext.getBean("connectionBillService");
			//插入打印日志
			IHandOverBillService handOverBillService = (IHandOverBillService) applicationContext.getBean("handOverBillService");
			//根据交接单号查询交接单信息
			ConnectionBillEntity connectionBill = connectionBillService.queryConnectionBillByNo(connectionBillNo);
			
			String vehicleNo = connectionBill.getVehicleNo();
			if(vehicleNo == null) {
				vehicleNo = "";
			}

			//打印中的参数
			Map<String, Object> parameter = new HashMap<String, Object>();
			//到达部门
			parameter.put("destOrgName", connectionBill.getArriveDeptName());
			//出发部门
			parameter.put("origOrgName", connectionBill.getDepartDeptName());
			//交接单号
			parameter.put("connectionBillNo", connectionBill.getConnectionBillNo());
			//车牌号
			parameter.put("vehicleNo", vehicleNo);
			//备注
			parameter.put("note", connectionBill.getNotes());
			//交接类型cn
			String handOverType = connectionBill.getHandOverType();
			
            if(StringUtil.equals(handOverType, LoadConstants.HANDOVER_TYPE_DIVISION_CONNECTION)) {
            	handOverType = "接驳交接单";
            }
            //交接类型en
			parameter.put("handOverType", handOverType);
			//打印部门
			parameter.put("printOrgName", currentDeptName);
			//开单日期
			parameter.put("createTime", connectionBill.getHandOverTime());
			//总票数
			parameter.put("waybillQtyTotal", connectionBill.getWaybillQtyTotal());
			//总件数
			parameter.put("goodsQtyTotal", connectionBill.getGoodsQtyTotal());
			//总重量
			parameter.put("weightTotal", connectionBill.getWeightTotal());
			//总体积
			parameter.put("volumeTotal", connectionBill.getVolumeTotal());
			//打印用户
			parameter.put("createUserName", connectionBill.getCreateUserName());
			
			//当前部门code
			String currentDeptCode = (String) jasperContext.get("currentDeptCode");
			//当前用户code
			String currentUserCode = (String) jasperContext.get("currentUserCode");
			//当前用户name
			String currentUserName = (String) jasperContext.get("currentUserName");
			//插入交接单打印记录
			addHandoverbillPrintrecord(connectionBillNo, connectionBill.getHandOverTime(),handOverBillService, 
					currentDeptCode, currentDeptName, currentUserCode, currentUserName);
			return parameter;
		}
		
		/****
		 * 创建打印field数据源
		 * @author ibm-zhangyixin
		 * @date 2013-3-14 下午3:58:03
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//交接单service
			IConnectionBillService connectionBillService = (IConnectionBillService) applicationContext.getBean("connectionBillService");
			List<HandOverBillDetailDto> connectionBillNoDetails = connectionBillService.queryPrintConnectionBillDataByNo(connectionBillNo);
			//产品service
			IProductService productService = (IProductService) applicationContext.getBean("productService4Dubbo");
			//产品dto
			ProductDto condtion = new ProductDto();
			//设置只查询出3级产品
			condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
			//所有3级的产品
			List<ProductEntity> productEntityList = productService.findExternalProductByCondition(condtion, null);
			//以key-value方式放在一个map中, 方便取值
			Map<String, String> productMap = new HashMap<String, String>(productEntityList.size());
			for(ProductEntity product : productEntityList) {
				productMap.put(product.getName(), product.getShortName());
			}
			
			//提货方式Dict
			Map<String, String> pickupGoodsDictMap = new HashMap<String, String>();
			//提货方式
			List<String> pickupGoods = new ArrayList<String>();
			//提货方式(汽运)
			pickupGoods.add(DictionaryConstants.PICKUP_GOODS);
			//提货方式(空运)
			pickupGoods.add(DictionaryConstants.PICKUP_GOODS_AIR);
			//提货方式dict
			//以key-value方式放在一个map中, 方便取值
			List<DataDictionaryEntity> pickupGoodsDict = DictUtil.getDataByTermsCodes(pickupGoods);
			for(DataDictionaryEntity dictionary : pickupGoodsDict) {
				for(DataDictionaryValueEntity value : dictionary.getDataDictionaryValueEntityList()) {
					pickupGoodsDictMap.put(value.getValueCode(), value.getValueName());
				}
			}
			//以key-value方式放在一个map中, 方便取值
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(connectionBillNoDetails.size());
			for(HandOverBillDetailDto connectionBillDetail : connectionBillNoDetails){
				//运单号
				String wayBillNo = connectionBillDetail.getWaybillNo();
//				//如果为空, 就空一行
				if(StringUtils.isEmpty(wayBillNo)) {
					continue;
				}
				Map<String, Object> m = new HashMap<String, Object>();
				//运输性质(简称)
				m.put("transProperty", productMap.get(connectionBillDetail.getTransProperty()));
				//运单号
				m.put("waybillNo", connectionBillDetail.getWaybillNo());
				//收货人
				m.put("consignee", connectionBillDetail.getConsignee());
				//收货人电话
				m.put("receiveCustomerMobilephone", connectionBillDetail.getReceiveCustomerMobilephone());
				if(connectionBillDetail.getConsignee() == null && connectionBillDetail.getReceiveCustomerMobilephone() == null) {
					m.put("consigneeinfo", null);
				} else {
					StringBuffer consigneeinfo = new StringBuffer();
					if(StringUtils.isNotEmpty(connectionBillDetail.getConsignee())) {
						consigneeinfo.append(connectionBillDetail.getConsignee());
					}
					if(StringUtils.isNotEmpty(connectionBillDetail.getReceiveCustomerMobilephone())) {
						if(StringUtils.isNotEmpty(connectionBillDetail.getConsignee())) {
							consigneeinfo.append("/");
						}
						consigneeinfo.append(connectionBillDetail.getReceiveCustomerMobilephone());
					}
					m.put("consigneeinfo", consigneeinfo.toString());
				}
				//收货部门
				m.put("receiveOrgName", connectionBillDetail.getReceiveOrgName());
				//件数
				m.put("pieces", connectionBillDetail.getPieces());
				//开单件数
				m.put("waybillPieces", connectionBillDetail.getWaybillPieces());
				if(connectionBillDetail.getPieces() == null && connectionBillDetail.getWaybillPieces() == null) {
					m.put("pieceinfo", null);
				} else {
					m.put("pieceinfo", connectionBillDetail.getPieces() + "/" + connectionBillDetail.getWaybillPieces());
				}
				//重量
				m.put("weight", connectionBillDetail.getWeight());
				//目的站
				m.put("destination", connectionBillDetail.getDestination());
				//实际体积
				m.put("cubage", connectionBillDetail.getCubage());
				
				//运单备注
				m.put("waybillNote", connectionBillDetail.getWaybillNote());
				//品名
				m.put("goodsName", connectionBillDetail.getGoodsName());
				//包装
				m.put("packing", connectionBillDetail.getPacking());
				if(connectionBillDetail.getGoodsName() == null && connectionBillDetail.getPacking() == null) {
					m.put("packinginfo", null);
				} else {
					m.put("packinginfo", connectionBillDetail.getGoodsName() + "/" + connectionBillDetail.getPacking());
				}
				//提货方式
				m.put("receiveMethod", pickupGoodsDictMap.get(connectionBillDetail.getReceiveMethod()));
				data.add(m);
			}
			return data;
		}
	}
}