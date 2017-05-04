/**
 *  initial comments.
 *  *  
 *  1.5.3	界面描述
外发清单打印模板：如图分为三个部分：
	一、表头信息区域，
	二、运单明细信息区域，
	三、汇总合计区域。
一、	表头信息区域：
1.	包含信息：
1.1	表头统一标记为：“德邦物流：汽运外发清单”；
1.2	出发部门：偏线外发交接的出发部门eg：广州汽运偏线；来源于“新增交接单”。
1.3	外发代理：外发代理公司名称，来源于“外发代理基础资料”。
1.4	车牌号：出发车辆编号，eg：粤A20581；来源于“新增交接单”。
1.5	制单时间：系统生成外发交接单的时间。eg：2012-03-06  08:00；来源于“新增交接单”。
1.6	交接类型：外发交接单
1.7	单据编号：系统自动分配的外发交接编号，新增外发交接单时系统产生，eg：21962843；来源于“新增交接单”。
以上1.3~1.8字段信息：在“新增交接单”界面已产生。
二、	运单明细信息区域：
2.	包含信息：
2.1	从左到右，信息类型如下：目的站、运单号、收货人资料、品名/包装、件数（8/30）、重量、体积、提货方式、付款方式、到付金额；来源于“新增交接单”。
2.2	收货人资料：又包含四个字段：收货人姓名、收货人电话、收货人手机、收货人地址；
收货人资料来源于“运单信息”。
2.3	件数格式：为交接件数/开单件数，eg：8/30，开单件数为30，本部门交接走货为8，来源“新增交接单”。
2.4	交接件数明细：为本部门交接（配载走货）件数明细，格式1/2/3/4；对应件数明细。（件数与流水号尾数一一对应）。当选择打印类型为：外发清单（流水号）打印时，才显示交接件数明细内容。
2.5	打印结果的顺序：在文档中确认打印顺序，系统默认按照目的站、运单号升序来打印。
三、	汇总合计区域：
3.	包含信息：出发填写、到达填写；货物汇总信息处；
3.1	货物汇总信息处：
3.3.1	包含信息： 制单人 __、合计：__票数、__总件数、___总重量、____总体积、__总金额；信息来源“新增交接单”，数据算法引用业务规则：SR-4。
3.3.2	右下脚显示打印页数（第X页共X页）、打印时间（为服务器时间）。
3.3.3	签字录入框：包含外场：___   、 外发员：___ 、 代理：____。

业务要求说明：签收单以后会附在货物上，将不会再通过司机与营业员或操作员的交接方式来管理；货单同行、附信封以后会单独成件的方式来管理；
1.6	操作步骤
1.6.1	查询及打印交接单
序号	基本步骤	相关数据	补充步骤
1	进入查询交接单或配载单界面		界面加载时默认显示本部门信息
2	填写查询条件，点击“查询“按钮，查找出满足条件的交接单	【交接单明细列表】（打印）	
3	勾选需要打印的交接单，显示勾选交接单明细；	【交接单明细列表】（打印）	人工勾选前面的复选框
4	查看交接单明细，按照交接单字段排序；	【交接单明细列表】（打印）	交接单查询界面，系统默认按运输性质、运单号升序排序。
5	点击“打印按钮”，进入打印模板选择框，通过选择外发清单打印或外发清单（流水号）打印		点击打印按钮时，假如有属于同一车牌号的外发交接单，没有勾选时，系统提示用户“此车牌中还有X个交接单没有选择打印，请勾选“
6	勾选对应的外发打印模板，点击“打印“按钮，系统按照固定模板打印外发信息。	【交接单明细列表】（打印）	1.系统：按照已经勾选的打印模板加载信息，打印纸质外发单明细。 


序号	扩展事件	相关数据	备注
5a	勾选外发交接单明细列表复选框，来选择打印对象	【交接单明细列表】（打印）	系统支持多张外发交接清单同时打印。
也是N分打印清单。而不是打印到同一份清单里面。


1.7	业务规则
序号	描述
SR-1	打印名称为外发清单打印、外发清单（流水号）打印；
模板中的交接类型：外发交接单。
SR-2	外发操作员只能打印系统最新版本的交接单。
SR-3	打印顺序：系统默认按照目的站、运单号升序打印。
SR-4	总票数=外发交接单中票数累积之和；
总件数=外发交接单中件数累积之和；
总重量=外发交接单中重量累积之和；单位为kg（无需带单位）
总体积=外发交接单中货物体积累积之和；单位为方(无需带单位)
总金额=外发单中货物到付总金额。
打印时间为服务器时间
SR-5	点击打印按钮时，假如有属于同一车牌号的外发交接单，没有勾选时，系统提示用户“此车中还有X个外发交接单没有选择打印，请勾选 “。
SR-6	系统支持多张外发交接单同时打印。
也是N分打印外发清单。而不是打印到同一份外发清单里面。
SR-7	交接件数明细：显示格式1/2/3/6/8；加粗。
数据来源：已交接件数（交接件数不等于开单件数，如分批配载）。
交接件数明细打印显示原则：
当选择打印模板类型：外发清单（流水号）打印时，才会显示交接件数明细。外发清单打印时，不显示交接件数明细行。
SR-8	偏线自有车辆上门，则无车牌号码

SR-9	交接单明细中的单号，打印字体设置大一号且加粗，方便操作员在交接单明细中通过单号查找定位。

1.8	数据元素
1.8.1	交接单明细列表（打印）
字段名称 	说明 	输入限制	提示输入本文	长度	是否必填	备注
单据编号	PDA或者FOSS系统自动生成的外发交接单编号，可作为交接单的唯一标识	N/A	N/A	N/A	N/A	内容读取交接单
收货人资料	包含收货人姓名、电话、手机、收货人地址信息，来源运单信息	N/A	N/A	N/A	N/A	
提货方式	客户提货的方式，自提或派送，来源运单信息	N/A	N/A	N/A	N/A	
品名/包装	货物名称及包装信息来源运单信息	N/A	N/A	N/A	N/A	
目的站	货物最终目的站	N/A	N/A	N/A	N/A	
总体积	交接单的货物总体积	N/A	N/A	N/A	N/A	默认为方，单位不显示
总重量	交接单的货物总重量	N/A	N/A	N/A	N/A	默认为千克，单位不显示
总件数	交接单的货物总件数	N/A	N/A	N/A	N/A	
总票数	交接单中运单的个数	N/A	N/A	N/A	N/A	
总金额	交接单中到付总金额					
制单时间（	该交接单的生成日期	N/A	N/A	N/A	N/A	包括起始日期和截止日期，格式为：格式为：2012-04-01 08:30
付款方式	开单时记录的货物付款方式	N/A	N/A	N/A	N/A	
到付金额	开单时记录的货物到付金额	N/A	N/A	N/A	N/A	
外发代理	外发代理名称	N/A	N/A	N/A	是	
外发员	纸质签字处					为空，人工签字
外场	外发清单办理部门，纸质签字处					为空，人工签字
代理	外发代理名称，纸质签字处					为空，人工签字

1.9	非功能性需求（可选）
2012年数据量	1000-3000
2012年全网估计用户数	100-300
响应要求（如果与全系统要求 不一致的话）	查询响应与全系统要求一致
使用时间段	全天
高峰使用时段	下午5：00-次日9：00

1.10	接口描述：
接口名称 	对方系统	接口描述
外发代理基础资料	综合管理-外发代理基础资料	代理名称、代理联系方式、外发员


 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/partiallinesn/PartialLinePrtDataSource.java
 *  
 *  FILE NAME          :PartialLinePrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.partiallinesn;

import java.math.BigDecimal;
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
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.util.UUIDUtils;

/**
 * 外发清单流水打印
 * @author ibm-zhangyixin
 * @date 2013-1-6 上午10:20:44
 */
public class PartialLinePrtDataSource implements MultiJasperDataSource {
	/****
	 * 批量打印的jasperDataSource
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 下午4:03:28
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		//获取交接单单号(有可能为一个集合, 也可能是一个String)
		Object obj = jasperContext.get("handOverBillNos");
		String[] handOverBillNos = null;
		if(obj instanceof String) {
			handOverBillNos = new String[1];
			handOverBillNos[0] = (String) obj;
		} else {
			handOverBillNos = (String[]) obj;
		}
		
		List<JasperDataSource> data = new ArrayList<JasperDataSource>();
		for(String handOverBillNo : handOverBillNos ){
			data.add(new MyJasperDataSource(handOverBillNo));
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
	 * 外发清单流水打印
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 下午3:22:47
	 */
	class MyJasperDataSource implements JasperDataSource {
		//交接单号
		String handOverBillNo = null;
		
		public MyJasperDataSource(String handOverBillNo){
			this.handOverBillNo = handOverBillNo;
			
		}
		
		/****
		 * 创建打印参数数据源
		 * @author ibm-zhangyixin
		 * @date 2013-3-14 下午4:03:55
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//当前部门name
			String currentDeptName = (String) jasperContext.get("currentDeptName");
			//交接单service
			IHandOverBillService handOverBillService = (IHandOverBillService) applicationContext.getBean("handOverBillService");
			//根据交接单号查询出该交接单
			HandOverBillEntity handOverBill = handOverBillService.queryHandOverBillByNo(handOverBillNo);
			//打印中需要的参数
			Map<String, Object> parameter = new HashMap<String, Object>();
			//到达部门
			parameter.put("destOrgName", handOverBill.getArriveDept());
			//出发部门
			parameter.put("origOrgName", handOverBill.getDepartDept());
			//交接单号
			parameter.put("handOverBillNo", handOverBill.getHandOverBillNo());
			//车牌号
			String vehicleNo = handOverBill.getVehicleNo();
			if(vehicleNo == null) {
				vehicleNo = "";
			}
			parameter.put("vehicleNo", handOverBill.getVehicleNo());
			//备注
			parameter.put("note", handOverBill.getNote());
			//交接类型en
			String handOverType = handOverBill.getHandOverType();
			if(StringUtil.equals(handOverType, "SHORT_DISTANCE_HANDOVER")) {
            	handOverType = "短配交接单";
            } else if (StringUtil.equals(handOverType, "LONG_DISTANCE_HANDOVER")) {
            	handOverType = "集配交接单";
            } else if (StringUtil.equals(handOverType, "PARTIALLINE_HANDOVER")) {
            	handOverType = "外发交接单";
            }else if(StringUtils.equals(handOverType, LoadConstants.HANDOVER_TYPE_LDP)){
            	handOverType = "快递代理交接单";
            }
			//交接类型cn
			parameter.put("handOverType", handOverType);
			//打印部门
			parameter.put("printOrgName", currentDeptName);
			//交接时间
			parameter.put("createTime", handOverBill.getHandOverTime());
			//总票数
			parameter.put("waybillQtyTotal", handOverBill.getWaybillQtyTotal());
			//总件数
			parameter.put("goodsQtyTotal", handOverBill.getGoodsQtyTotal());
			//总重量
			parameter.put("weightTotal", handOverBill.getWeightTotal());
			//总体积
			parameter.put("volumeTotal", handOverBill.getVolumeTotal());
			//总金额
			BigDecimal moneyTotal = handOverBill.getMoneyTotal();
			//总金额
			parameter.put("moneyTotal", moneyTotal);
			//制单人
			parameter.put("createUserName", handOverBill.getCreateUserName());
			//当前部门code
			String currentDeptCode = (String) jasperContext.get("currentDeptCode");
			//当前用户code
			String currentUserCode = (String) jasperContext.get("currentUserCode");
			//当前用户name
			String currentUserName = (String) jasperContext.get("currentUserName");
			//插入交接单打印记录
			addHandoverbillPrintrecord(handOverBillNo, handOverBill.getHandOverTime(), handOverBillService,
					currentDeptCode, currentDeptName, currentUserCode, currentUserName);
			return parameter;
		}
		
		/****
		 * 创建打印field数据源
		 * @author ibm-zhangyixin
		 * @date 2013-3-14 下午4:04:06
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			//程序上下文
			ApplicationContext applicationContext = jasperContext.getApplicationContext();
			//交接单serivce
			IHandOverBillService handOverBillService = (IHandOverBillService) applicationContext.getBean("handOverBillService");
			//查询出打印中需要的数据
			List<HandOverBillDetailDto> handOverBillDetails = handOverBillService.queryPrintPartialLineDataByNo(handOverBillNo);
			
			//提货方式Dict
			Map<String, String> pickupGoodsDictMap = new HashMap<String, String>();
			//提货方式
			List<String> pickupGoods = new ArrayList<String>();
			//提货方式(汽运)
			pickupGoods.add(DictionaryConstants.PICKUP_GOODS);
			//提货方式(空运)
			pickupGoods.add(DictionaryConstants.PICKUP_GOODS_AIR);
			//获取数据字典中的替换方式
			//以key-value方式放在一个map中, 方便取值
			List<DataDictionaryEntity> pickupGoodsDict = DictUtil.getDataByTermsCodes(pickupGoods);
			for(DataDictionaryEntity dictionary : pickupGoodsDict) {
				for(DataDictionaryValueEntity value : dictionary.getDataDictionaryValueEntityList()) {
					pickupGoodsDictMap.put(value.getValueCode(), value.getValueName());
				}
			}
			
			//付款方式Dict
			Map<String, String> paymentModeDictMap = new HashMap<String, String>(pickupGoodsDict.size());
			//付款方式
			List<String> paymentMode = new ArrayList<String>();
			paymentMode.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
			//从数据字典中取付款方式
			//然后将付款方式以key-value方式放在一个map中方便取值
			List<DataDictionaryEntity> paymentModeDict = DictUtil.getDataByTermsCodes(paymentMode);
			for(DataDictionaryEntity dictionary : paymentModeDict) {
				if(dictionary == null) {
					continue;
				}
				for(DataDictionaryValueEntity value : dictionary.getDataDictionaryValueEntityList()) {
					if(value == null) {
						continue;
					}
					paymentModeDictMap.put(value.getValueCode(), value.getValueName());
				}
			}
			
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(handOverBillDetails.size());
			for(HandOverBillDetailDto handOverBillDetail : handOverBillDetails){
				//运单号
				String wayBillNo = handOverBillDetail.getWaybillNo();
				//获取交接单流水
				List<HandOverBillSerialNoDetailEntity> handOverBillSerialNoDetails = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(wayBillNo, handOverBillNo);
				//将交接单流水一"1,2,3,4,5"这样的方式输出
				StringBuffer serialNos = new StringBuffer();
				for(HandOverBillSerialNoDetailEntity handOverBillSerialNoDetail : handOverBillSerialNoDetails) {
					serialNos.append(Integer.valueOf(handOverBillSerialNoDetail.getSerialNo())).append("/");
				}
				String serialNosStr = serialNos.toString();
				if(StringUtils.isNotEmpty(serialNosStr) && serialNosStr.lastIndexOf("/") == serialNosStr.length() - 1) {
					serialNosStr = serialNosStr.substring(0, serialNosStr.length() - 1);
				}
				
				Map<String, Object> m = new HashMap<String, Object>();
				//流水号
				m.put("serialNos", serialNosStr);
				//运单号
				m.put("waybillNo", handOverBillDetail.getWaybillNo());
				//收货人
				m.put("consignee", handOverBillDetail.getConsignee());
				//收货部门
				m.put("receiveOrgName", handOverBillDetail.getReceiveOrgName());
				//件数
				m.put("pieces", handOverBillDetail.getPieces());
				//开单件数
				m.put("waybillPieces", handOverBillDetail.getWaybillPieces());
				//重量
				m.put("weight", handOverBillDetail.getWeightAc());
				//目的站
				m.put("destination", handOverBillDetail.getDestination());
				//实际体积
				m.put("cubage", handOverBillDetail.getCubageAc());
				//运单备注
				m.put("waybillNote", handOverBillDetail.getWaybillNote());
				//品名
				m.put("goodsName", handOverBillDetail.getGoodsName());
				//包装
				m.put("packing", handOverBillDetail.getPacking());
				//提货方式
				m.put("receiveMethod", pickupGoodsDictMap.get(handOverBillDetail.getReceiveMethod()));
				//收货人
				String receiveCustomerName = handOverBillDetail.getReceiveCustomerName();
				if(receiveCustomerName == null) {
					receiveCustomerName = "";
				}
				m.put("receiveCustomerName", receiveCustomerName);
				//收货人电话
				String receiveCustomerMobilephone = handOverBillDetail.getReceiveCustomerMobilephone();
				if(receiveCustomerMobilephone == null) {
					receiveCustomerMobilephone = "";
				}
				m.put("receiveCustomerMobilephone", receiveCustomerMobilephone);
				//收货人移动电话
				String receiveCustomerPhone = handOverBillDetail.getReceiveCustomerPhone();
				if(receiveCustomerPhone == null) 
					receiveCustomerPhone = "";
				m.put("receiveCustomerPhone", receiveCustomerPhone);
				//收货人地址
				String receiveCustomerAddress = handOverBillDetail.getReceiveCustomerAddress();
				if(receiveCustomerAddress == null) {
					receiveCustomerAddress = "";
				}
				m.put("receiveCustomerAddress", receiveCustomerAddress);
				//到付金额
				BigDecimal toPayAmount = handOverBillDetail.getToPayAmount();
				if(toPayAmount != null) {
					//数据库中保存的是(分)
					toPayAmount = toPayAmount.divide(BigDecimal.valueOf(LoadConstants.SONAR_NUMBER_100));
				} else {
					toPayAmount = BigDecimal.ZERO;
				}
				//到付金额
				m.put("toPayAmount", toPayAmount);
				//收货部门
				m.put("receiveOrgCode", handOverBillDetail.getReceiveOrgName());
				//目的站
				m.put("destination", handOverBillDetail.getDestination());
				//付款方式cn
				m.put("paidMethodCN", paymentModeDictMap.get(handOverBillDetail.getPaidMethod()));
				data.add(m);
			}
			return data;
		}
	}
}