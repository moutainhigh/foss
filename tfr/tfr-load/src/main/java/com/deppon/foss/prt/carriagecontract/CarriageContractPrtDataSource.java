/**
 *  initial comments.
 *  
 *  1.5.3	界面描述
运输合同打印模板：如图分为三个部分：
	一、表头信息区域，
	二、合同正文，
	三、需人工核对信息区域。
一、	表头信息区域：
1.	包含信息：
1.1	表头统一记录为：货物运输服务合同；
1.2	系统所属类型：变为FOSS
1.3	配载车次号：HZGZ1204701；
以下为表格形式;
1.4	车主姓名：司机姓名，
1.5	车牌号：格式为粤A2012；
1.6	主驾驶员：司机姓名；
1.7	身份证号：XX；
1.8	驾驶证号：XX；
1.9	发动机号：XX；
1.10	车架号：
1.11	营业证号：
1.12	行驶证号：
1.13	车主家庭住址及电话：XX；
以上信息：通过配载单界面的主驾驶员、车牌号字段，来自动关联外请车基础资料中的信息。
1.14	始发地：
1.15	目的地：
以上信息：通过配载单界面的始发部门及到达部门，来关联处对应的始发地的城市简称，目的地的城市简称。
1.16	发车时间：格式为月、日、时；在此，系统记录运输单打印时间为出发时间。
1.17	预定到达时间：为运行时数+发车时间；
1.18	负责人：为制单人，登录系统账号的人员；来源“新增配载单”。
1.19	总件数：配载单中的件数累积之和，来源“新增配载单”。
1.20	本车总运费：122，元（单位），大写：壹佰贰拾贰元；来源“新增配载           单”。
1.21	始发已付运费：22，元（单位），大写：贰拾贰元；来源“新增配载单”。
1.22	目的站联系人：显示交接到达部门/到达部门实际详细地址/部门负责人联系方式，eg：宁波专线，来源“新增配载单”，参见SR-7。
1.23	是否有押回单：配载单界面有勾选项，则打印时为有，无勾选项，则打印时记录为否。
1.24	备注：来源“新增配载单”备注字段。
二、	合同正文：
2.	包含信息：
1.1	托运人（甲方）：XX德邦物流有限公司，来源部门基础资料，eg：上海德邦物流有限公司；
1.2	承运人（乙方）：车主姓名，来源外请车基础资料；
1.3	正文：参见界面中的附件：“附件德邦运输合同模板”。
三、	需人工核对信息区域：
3.	包含信息：
1.1	甲方：XX德邦物流有限公司（盖章），
来源部门基础资料：部门-所属子公司名称；
1.2	经办人：制单人；来源“新增配载单”。
1.3	乙方（签字按手印）： _____________,； 
1.4	身份证号（乙方）：_____________,； 
1.6	操作步骤
1.6.1	查询及打印交接单
序号	基本步骤	相关数据	补充步骤
1	进入查询交接单或配载单界面		界面加载时默认显示本部门信息
2	填写查询条件，点击“查询“按钮，查找出满足条件的配载单	【配载交接单明细列表】	引用“查询交接单/配载单”
3	查看配载单明细	【配载交接单明细列表】	
4	点击打印运输合同按钮，系统按照运输合同模板打印；	【运输合同信息】	打印的前提是，配载单已经生成，否则系统报错。
系统记录运输合同的打印记录及记录打印次数（点击按钮数）。


序号	扩展事件	相关数据	备注
4a	勾选任一配载单明细列表复选框	【配载交接单明细列表】	按照已选的配载单，打印运输合同。配载单和运输合同是一一对应。只能逐份打印。 


1.7	业务规则
序号	描述
SR-1	操作员只能打印系统最新版本的集配交接单所对应的运输合同。
SR-2	一个车牌号可以对应多个配载单（两地卸货，就有多个配载单）；
点击打印运输合同按钮时，只打印至最终到达部门所对应的配载单，中途卸货配载单无需打印运输合同。
尾款结算与始发部门有关联，尾款与最终到达部门有关联，中途卸货部门不产生费用结算。
SR-3	运输合同打印：系统支持一个配载单对应打印一份运输合同。不允许勾选多个配载单。
SR-4	长途外请车，才会打印运输合同。
SR-5	系统保留运输合同打印记录且记录操作员点击打印按钮的次数（运输合同牵扯到费用结算，系统需要监控打印次数）。
SR-6	涉及金额字段，都大写；且金额（数字）字段都右对齐。
SR-7	打印合同界面的目的站联系人：
要显示信息：交接到达部门/交接到达部门实际详细地址/部门负责人联系方式。
信息来源参见：SUC-85修改_查询行政组织业务属性、SUC-35查询人员。
SR-8	1、取消承运人的自动关联，留空白，改为手动填写； 
2、营业证号改为"道运证号"； 
3、取消行驶证号一栏； 
4、合同条款修改：①、第二条甲方责任修改为：乙方到达目的地，经甲方卸车清点件数无误，如为整车则收货人正常签收后，及时以现金形式付清当次运输费（若甲乙双方关于费用结算有月结约定，则按照月结约定方式结款）。乙方或乙方司机每次收款时，均应开具收条。②、第三条乙方责任中第三点改为：乙方进入甲方场地，应服从甲方管理，遵守《外请车司机场地须知》等相关规定。如不遵守，造成损失由乙方负责。③、第五条结算依据改为：货物安全到达目的地后，此合同（如为整车则还需提供收货人正常签收证明）作为剩余运费的付款依据。"押回单"指返单付款，即剩余运费由甲方出发部门支付，支付的前提是收到车载全部货物的运单正常签收凭证。④、增加第七条：甲方公司投诉电话：13922231315；若经查实，投诉人将获得至少5000元奖励。 
5、增加卸车人及卸车确认签字。 
6、将"盖章"移至甲方后。 

1.8	数据元素
1.8.1	【运输合同信息】（打印）
字段名称 	说明 	输入限制	提示输入本文	长度	是否必填	备注
配载车次号	配载车次号”生成规则：始发城市简称+到达城市简称+出发日期，GZSH2012042801， 	N/A	N/A	N/A	N/A	来源新增配载单
车主姓名	姓名	N/A	N/A	N/A	N/A	来源外请车基础资料
车牌号	格式为粤A2012；	N/A	N/A	N/A	N/A	来源新增配载单
主驾驶员	司机姓名，驾驶员与车主可以不是同一个人	N/A	N/A	N/A	N/A	来源新增配载单
身份证号	驾驶员身份证来源外请车基础资料	N/A	N/A	N/A	N/A	来源外请车基础资料
驾驶证号	驾驶员的驾驶证号来源外请车基础资料	N/A	N/A	N/A	N/A	来源外请车基础资料
发动机号	机器出厂编号	N/A	N/A	N/A	N/A	来源外请车基础资料
车架号	机器出厂编号	N/A	N/A	N/A	N/A	来源外请车基础资料
营业证号	运输证编号	N/A	N/A	N/A	N/A	来源外请车基础资料
行驶证号	行驶证编号	N/A	N/A	N/A	N/A	来源外请车基础资料
车主家庭住址及电话	所处地址+电话	N/A	N/A	N/A	N/A	来源外请车基础资料
始发地	通过配载单界面的始发部门及到达部门，来关联处对应的始发地的城市简称，目的地的城市简称。	N/A	N/A	N/A	N/A	
目的地	通过配载单界面的始发部门及到达部门，来关联处对应的始发地的城市简称，目的地的城市简称。	N/A	N/A	N/A	N/A	
发车时间	格式为月、日、时；在此，系统记录运输单打印时间为出发时间。	N/A	N/A	N/A	N/A	系统打印时的服务器时间
预定到达时间	为运行时数+发车时间；	N/A	N/A	N/A	N/A	
负责人	为制单人	N/A	N/A	N/A	N/A	
总件数	所选配载单中的件数之和	N/A	N/A	N/A	N/A	
本车总运费	外请要付总费用	N/A	N/A	N/A	N/A	
始发已付运费	首款	N/A	N/A	N/A	N/A	
目的站 联系人	交接到达部门	N/A	N/A	N/A	N/A	
是否有押回单	配载单界面：有勾选状态，则打印时为有，无勾选项，则打印时记录为否。
	N/A	N/A	N/A	N/A	
备注	来源配载单中的的备注字段信息	N/A	N/A	N/A	N/A	
托运人（甲方）	始发部门-对应所属德邦子公司名称	N/A	N/A	N/A	N/A	来源部门信息基础资料
承运人（乙方）	车主姓名	N/A	N/A	N/A	N/A	来源外请车辆基础资料
合同正文	见界面-运输合同附件信息。					固定模板
甲方	始发部门-对应所属德邦子公司名称	N/A	N/A	N/A	N/A	
经办人	制单人	N/A	N/A	N/A	N/A	
乙方（签字按手印）	留给司机手输签字					为空
身份证号（乙方）	留给司机手输签字					为空

1.9	非功能性需求（可选）
使用量	1000~2000（外场）； 
2012年全网估计用户数	500~1000
响应要求（如果与全系统要求 不一致的话）	查询响应与全系统要求一致
使用时间段	全天
高峰使用时段	下午5：00-次日9：00

1.10	接口描述：
接口名称 	对方系统	接口描述
外请车基础新增信息（对内）	综合管理	见车辆基础资料信息（界面描述1.4~1.13）
始发地、到达地对应城市简称（对内）	综合管理	见部门信息基础资料（界面描述1.14~1.15）


 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/carriagecontract/CarriageContractPrtDataSource.java
 *  
 *  FILE NAME          :CarriageContractPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.carriagecontract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldTFCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintCarriageContractDataDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.MoneyUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运输合同打印
 * @author ibm-zhangyixin
 * @date 2013-1-6 上午10:21:22
 */
public class CarriageContractPrtDataSource implements JasperDataSource {
	/**
	 * 打印时需要记录该交接单的打印记录 
	 * @author ibm-zhangyixin
	 * @date 2012-10-30 下午5:19:15
	 */
	public void addVehicleAssembleBillRecord(String vehicleassembleNo, IVehicleAssembleBillService vehicleAssembleBillService,
			String currentDeptCode, String currentDeptName, String currentUserCode, String currentUserName) {
		//汽运配载单打印记录
		MotorstowagebillrecordEntity motorstowagebillrecord = new MotorstowagebillrecordEntity();
		//设置ID
		motorstowagebillrecord.setId(UUIDUtils.getUUID());
		//类型为运输合同
		motorstowagebillrecord.setPrintType("CARRIAGECONTRACT");	//类型为运输合同
		//设置配载单号
		motorstowagebillrecord.setVehicleassembleNo(vehicleassembleNo);
		//设置打印用户name
		motorstowagebillrecord.setPrinterName(currentUserName);
		//设置打印用户code
		motorstowagebillrecord.setPrinterCode(currentUserCode);
		//设置打印时间
		motorstowagebillrecord.setPrintTime(new Date());
		//设置打印部门code
		motorstowagebillrecord.setOrgCode(currentDeptCode);
		//设置打印部门name
		motorstowagebillrecord.setOrgName(currentDeptName);
		//插入打印次数
		vehicleAssembleBillService.insertMotorstowagebillrecord(motorstowagebillrecord);
	}
	
	/****
	 * 创建打印参数数据源
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 下午3:52:09
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
		ApplicationContext applicationContext = jasperContext.getApplicationContext();
		//当前登录用户部门Code
		String currentDeptCode = (String) jasperContext.get("currentDeptCode");
		//当前登录用户部门Name
		String currentDeptName = (String) jasperContext.get("currentDeptName");
		//当前登录用户Code
		String currentUserCode = (String) jasperContext.get("currentUserCode");
		//当前登录用户Name
		String currentUserName = (String) jasperContext.get("currentUserName");
		//配置单号, 可能为数组
		Object obj = jasperContext.get("vehicleAssembleNo");
		String vehicleAssembleNo = "";
		if(obj instanceof String) {
			vehicleAssembleNo = (String) obj;
		} else {
			String[] vehicleAssembleNos = (String[]) obj;
			vehicleAssembleNo = vehicleAssembleNos[0];
		}
		//配置单service
		IVehicleAssembleBillService vehicleAssembleBillService = (IVehicleAssembleBillService) applicationContext.getBean("vehicleAssembleBillService");
		IOutfieldTFCompanyService outfieldTFCompanyService = (IOutfieldTFCompanyService) applicationContext.getBean("outfieldTFCompanyService");
		//财务组织复杂查询 service接口
//		IFinancialOrganizationsComplexService financialOrganizationsComplexService = (IFinancialOrganizationsComplexService) applicationContext.getBean("financialOrganizationsComplexService");
		//组织信息service
		IOrgAdministrativeInfoService orgAdministrativeInfoService = (IOrgAdministrativeInfoService)applicationContext.getBean("orgAdministrativeInfoService");
		//外请车司机service
		ILeasedDriverService leasedDriverService = (ILeasedDriverService)applicationContext.getBean("leasedDriverService");
		//获取运输合同数据
		PrintCarriageContractDataDto printCarriageContractDataDto = vehicleAssembleBillService.queryPrintCarriageContractData(vehicleAssembleNo);
		
		//打印的参数
		Map<String, Object> parameter = new HashMap<String, Object>();
		//配载单号
		parameter.put("vehicleAssembleNo", vehicleAssembleNo);
		//车主名称
		parameter.put("contact", printCarriageContractDataDto.getContact());
		//车牌号
		parameter.put("vehicleNo", printCarriageContractDataDto.getVehicleNo());
		//司机
		String driverName = printCarriageContractDataDto.getDriverName();
		String idCard = printCarriageContractDataDto.getDriverCode();
		String driverLicense = printCarriageContractDataDto.getDriverLicense();
		if(StringUtils.isEmpty(driverName)) {
			LeasedDriverEntity queryCon = new LeasedDriverEntity();
			queryCon.setIdCard(idCard);
			LeasedDriverEntity driver = leasedDriverService.queryLeasedDriverBySelective(queryCon);
			if(driver == null) {
				throw new TfrBusinessException("外请车司机不存在! 身份证号:" + idCard);
			}
			driverName = driver.getDriverName();
			driverLicense = driver.getDriverLicense();
		}
		parameter.put("driverName", driverName);
		//身份证
		parameter.put("idCard", idCard);
		//驾驶证
		parameter.put("driverLicense", driverLicense);
		//发动机号
		parameter.put("engineNo", printCarriageContractDataDto.getEngineNo());
		//车架号
		String vehicleFrameNo = printCarriageContractDataDto.getVehicleFrameNo();
		parameter.put("vehicleFrameNo", vehicleFrameNo);
		//营运证号
		parameter.put("operatingLic", printCarriageContractDataDto.getOperatingLic());
		//行驶证
		parameter.put("drivingLicense", printCarriageContractDataDto.getDrivingLicense());
		//出发部门
		parameter.put("origOrgName", printCarriageContractDataDto.getOrigOrgName());
		//到达部门
		parameter.put("destOrgName", printCarriageContractDataDto.getDestOrgName());
		//家庭住址
		String address = "";
		if(StringUtils.isNotEmpty(printCarriageContractDataDto.getAddress())) {
			address = printCarriageContractDataDto.getAddress();
		}
		parameter.put("address", address);
		//司机电话
		String driverPhone = "";
		if(StringUtils.isNotEmpty(printCarriageContractDataDto.getDriverPhone())) {
			driverPhone = printCarriageContractDataDto.getDriverPhone();
		}
		parameter.put("driverPhone", driverPhone);
		//发车时间
		parameter.put("departTime", printCarriageContractDataDto.getDepartTime());
		//预计到达时间
		parameter.put("eta", printCarriageContractDataDto.getEta());
		//制单人
		parameter.put("createUserName", printCarriageContractDataDto.getCreateUserName());
		//总件数
		parameter.put("totQty", printCarriageContractDataDto.getTotQty());
		//本车总运费
		BigDecimal feeTotal = printCarriageContractDataDto.getFeeTotal();
		if(feeTotal != null) {
			//数据库中保存的是(分)
			feeTotal = feeTotal.divide(BigDecimal.valueOf(LoadConstants.SONAR_NUMBER_100));
		} else {
			feeTotal = BigDecimal.ZERO;
		}
		//本车总运费
		parameter.put("feeTotal", feeTotal);
		//已付运费
		BigDecimal prePaidFeeTotal = printCarriageContractDataDto.getPrePaidFeeTotal();
		if(prePaidFeeTotal != null) {
			//数据库中保存的是(分)
			prePaidFeeTotal = prePaidFeeTotal.divide(BigDecimal.valueOf(LoadConstants.SONAR_NUMBER_100));
		} else {
			prePaidFeeTotal = BigDecimal.ZERO;
		}
		//已付运费
		parameter.put("prePaidFeeTotal", prePaidFeeTotal);
		//币种
		parameter.put("currencyCode", getCurrencyMap().get(printCarriageContractDataDto.getCurrencyCode()));
		//总运费CN
		parameter.put("feeTotalCN", MoneyUtils.amountToChinese(feeTotal.doubleValue()));
		//已付运费CN
		parameter.put("prePaidFeeTotalCN", MoneyUtils.amountToChinese(prePaidFeeTotal.doubleValue()));
		//组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(printCarriageContractDataDto.getDestStationContact());
		////交接到达部门/交接到达部门实际详细地址/部门负责人联系方式。
		StringBuffer destStationContact = new StringBuffer();
		if(orgAdministrativeInfo != null) {
			if(orgAdministrativeInfo.getName() != null){
				destStationContact.append(orgAdministrativeInfo.getName());
			}
			if(orgAdministrativeInfo.getAddress() != null){
				destStationContact.append("/").append(orgAdministrativeInfo.getAddress());
			}
			if(orgAdministrativeInfo.getDepTelephone() != null){
				destStationContact.append("/").append(orgAdministrativeInfo.getDepTelephone());
			}
		}
		//交接到达部门/交接到达部门实际详细地址/部门负责人联系方式。
		parameter.put("destStationContact", destStationContact.toString());
		//是否押回单
		String beReturnReceipt = StringUtils.equals("Y", printCarriageContractDataDto.getBeReturnReceipt()) ? "是" : "否";
		//是否押回单
		parameter.put("beReturnReceipt", beReturnReceipt);
		//备注
		parameter.put("note", printCarriageContractDataDto.getNote());
		

		//承运方
		String firstParty = "";
		//有些外场的子公司是另一个基础资料维护的，以这个基础资料优先，有这显示该子公司，没有则原有的规则不变
		//调用综合接口获取子公司
		firstParty =outfieldTFCompanyService.queryCompanyNameByOutfieldCode(printCarriageContractDataDto.getOrigOrgCode());
		if(StringUtils.isEmpty(firstParty)){
			//财务组织
			IFinancialOrganizationsService financialOrganizationsService = (IFinancialOrganizationsService)applicationContext.getBean("financialOrganizationsService");
			orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentDeptCode);
			FinancialOrganizationsEntity financialOrganizations = null;
			if(orgAdministrativeInfo != null) {
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(orgAdministrativeInfo.getUnifiedCode());
				financialOrganizations = financialOrganizationsService.queryFinancialOrganizationsByCode(org.getSubsidiaryCode());
			}
			if(financialOrganizations != null) {
				firstParty = financialOrganizations.getSubCompanyName();
			}
		}
		
		//承运人(甲方): 上海德邦物流
		parameter.put("firstParty", firstParty);	
		//车主名称
		parameter.put("contact", printCarriageContractDataDto.getContact());
		//奖罚协议
		if(StringUtils.equals(printCarriageContractDataDto.getBeTimeLiness(), FossConstants.YES)){
			//是否时效
			parameter.put("beTimeLiness", "是");
			String str1="";
			String str2="";
			RewardOrPunishAgreementDto rewardOrPunishAgreementDto=vehicleAssembleBillService.queryRewardOrPunishAgreement(vehicleAssembleNo);
			Map<Integer,RewardOrPunishAgreementEntity> tempMap =orderAgreement(rewardOrPunishAgreementDto);
			int[] array={0,1,2};
			for(int i=0;i<tempMap.size();i++){
				RewardOrPunishAgreementEntity tempEntity=tempMap.get(array[i]);
				if(tempMap.size()==i+1){
					str2="运行超时"+tempEntity.getTimeLimit()+"（不含"+tempEntity.getTimeLimit()+"）小时以上，负激励"+tempEntity.getAgreementMoney()+"元。";
				}else{
					str1=str1+"运行超时"+tempEntity.getTimeLimit()+"（含"+tempEntity.getTimeUp()+"）小时，负激励"+tempEntity.getAgreementMoney()+"元，";
				}
			}
			//str.append("（若手写负激励金额与德邦系统不一致时，则以德邦系统为准）");

			parameter.put("rewardAndPunisText1", str1);
			parameter.put("rewardAndPunisText2", str2);
			
		}else{
			parameter.put("rewardAndPunisText1", "");
			parameter.put("rewardAndPunisText2", "");
			//是否时效
			parameter.put("beTimeLiness", "否");
		}
		
		
		//插入配载单运输合同打印记录
		addVehicleAssembleBillRecord(vehicleAssembleNo, vehicleAssembleBillService,
				currentDeptCode, currentDeptName, currentUserCode, currentUserName);
		return parameter;
	}
	
	/****
	 * 币种类型map
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 上午11:33:18
	 */
	private Map<String, String> getCurrencyMap() {
		Map<String, String> map = new HashMap<String, String>(LoadConstants.SONAR_NUMBER_4) {
			private static final long serialVersionUID = 1217029861868567655L;

			@Override
			public String get(Object key) {
				//如果找不到相应的币种, 就直接把code返回
				if(super.get(key) != null) {
					return super.get(key);
				}
				return (String)key;
			}
		};
		map.put(FossConstants.CURRENCY_CODE_RMB, "人民币");
		map.put(FossConstants.CURRENCY_CODE_HKD, "港币");
		map.put(FossConstants.CURRENCY_CODE_NTD, "台币");
		map.put(FossConstants.CURRENCY_CODE_USD, "美元");
		return map;
	}

	/****
	 * 创建打印field数据源
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 上午11:33:33
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		data.add(parameter);
		return data;
	}
	
	/***
	 * 排序时效条款
	 * @author foss-heyongdong
	 * @date 2013年12月25日 15:42:33
	 * */
	private	Map<Integer,RewardOrPunishAgreementEntity>  orderAgreement(RewardOrPunishAgreementDto rewardOrPunishAgreementDto){
		//时间段0，0-1，1-2，2起始点数组 0，1，2 
		int[] array={0,1,2};
		List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity=rewardOrPunishAgreementDto.getRewardOrPunishAgreementEntity();
		Map<Integer,RewardOrPunishAgreementEntity> rewardAgreementMap= new HashMap<Integer,RewardOrPunishAgreementEntity>();
		Map<Integer,RewardOrPunishAgreementEntity> fineAgreementMap= new HashMap<Integer,RewardOrPunishAgreementEntity>();
		
		for(int i=0;i<array.length;i++){
			for(int j=0;j<rewardOrPunishAgreementEntity.size();j++){
				RewardOrPunishAgreementEntity temp = rewardOrPunishAgreementEntity.get(j);
				if(temp.getTimeDown()==array[i] && StringUtils.equals(temp.getRewardOrPunishType(),LoadConstants.REWARDPROT_TYPE_FINE)){
					fineAgreementMap.put(array[i], temp);
				}else if(temp.getTimeDown()==array[i] && StringUtils.equals(temp.getRewardOrPunishType(),LoadConstants.REWARDPROT_TYPE_REWARD)){
					rewardAgreementMap.put(array[i], temp);
				}
			}
		}
		//占时只返回罚款类型的，由于还没有扩展奖励业务
		return fineAgreementMap;
	}
}