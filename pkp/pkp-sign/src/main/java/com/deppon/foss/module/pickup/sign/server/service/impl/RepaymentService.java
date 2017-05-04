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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/RepaymentService.java
 * 
 * FILE NAME        	: RepaymentService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 修订记录 
 * 
日期 	修订内容 	修订人员 	版本号 
2012-04-25 	
新增	王辉	V0.1
2012-6-25	
根据ITA建议修改	王辉	V0.2
2012-6-28	
根据2012-6-26评审建议修改	王辉	V0.3
2012-7-11	
将签收信息的录入移至出库界面	王辉	V0.4
2012-7-24	
业务评审完毕，升至0.9	王辉	V0.9
2012-10-12	
根据RC-592修订，增加业务规则	
邓亭亭	
2012-10-13	
根据RC-575修订	
王辉	
2012-10-13	
根据RC-620修订	
王辉	
2012-10-13	
根据RC-632修订	
王辉	
2012-10-29	
根据RC-1104修订	
王辉	
2012-11-23	
根据ISSUE-604修订	
王辉	
2012-11-27	
根据ISSUE-835修订	
王辉	

1.	SUC-122-
结清货款
1.1	
相关业务用例
BUC_FOSS_5.50.30_520 
提货付款及复核
1.2	
用例描述
客户上门自提，营业员核对客户身份并在系统中货款结清操作。
1.3	
用例条件
条件类型
描述	引用系统用例
前置条件	
1.	
运单已开单
2.	
货物库存中	
后置条件		
1.4	
操作用户角色
操作用户	
描述
营业员	
营业员在FOSS系统中结清货款
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
图1 
客户自提签收
 
图2 
操作人密码弹出框
1.5.3	
界面描述
界面标题：
结清货款
结清货款界面分为4个部分，
包括待处理信息、
查询条件、
签收信息、
运单基本信息

1.	待处理信息：
单号、
件数、
收货人
2.	查询条件：
a)	单号
b)	收货人
c)	收货人电话
d)	收货人手机
e)	运输性质
f)	入库时间
g)	查询
h)	重置

3.	签收信息： 
a)	客户：
收货人信息
b)	提货人名称：
提货人名称
c)	证件类型：
证件类型，
包括身份证、
护照、
驾驶证、
暂住证、
军官证。
d)	证件号码：
提货人有效证件号码
e)	付款方式
f)	款项认领编号：
我司收到客户支票或电汇款后，资金部出纳对每笔款项的实际到账情况进行确认、公布，并对其分配的流水编号。
g)	实付运费：
该票运单的实付运费
h)	代收货款：
该票运单的代收货款
i)	收款总额：
实付运费和代收货款之和
j)	应收代收款：
该票运单的应收代收款
k)	已收代收款：
该票运单的已收代收款
l)	应收到付款：
该票运单的应收到付款
m)	已收到付款：
该票运单的已收到付款
n)	仓储费：
货物的仓储费用
o)	结清货款按钮：
点击“结清货款”按钮，结清货款。若已结清货款，
则“结清货款”修改为“提交”，变为“提交”按钮

4.	运单基本信息：
参考“运单基本信息”数据元素

页面初始化时，系统自动载入信息：
入库时间默认为当前系统时间前三天至当前时间
付款方式默认为现金

需要提供的相关用例链接或提示信息：
偏线空运签收（SUC-123）
签收出库接口（SUC-485）
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1		营业员输入查询条件，点击“查询按钮”	
查询条件、基本信息	待处理信息自动加载符合条件的运单信息，参考SR1
2		
营业员选择待处理信息中一行		
1.	
系统自动加载运单对应的签收信息、运单基本信息
2.	
参考SR10,SR15
3		
点击“获取证件号码”按钮或手输证件号码	
证件号码	若有相关设备且身份证有效可读取，
系统自动获取身份证号码填写至证件号码文本框，
且焦点自动放在证件号码文本框中
4		
营业员录入签收信息	签收信息	
1.	证件号码失去焦点，进行证件号码校验,
参考SR2，证件号码校验失败，则显示“非有效证件号！”
2.	
参考SR3，SR4，SR5，SR6，SR7，SR8
5		
营业员点击“结清货款”按钮		
1.	参考SR9
2.	
系统弹窗提示”是否确认提交？”
6		
点击确定	签收信息	
1.	
系统记录签收信息
2.	
系统标记运单已结清货款
3.	
到达联打印规则，参考SR11
4.	
系统标记此到达联“派送中”
5.	
调用结算的结清货款接口，参考SR12,SR13
6.	
系统计算仓储费，
若有仓储费，
则系统提示“仓储费XXX，
请注意收取并及时录入小票收入！”，
仓储费计算规则参考“通知客户（SUC-101）”系统用例
7.	
系统弹窗提示“提交成功！”
7		
理货员使用PDA扫描出库		
详细参考“出库货物”系统用例

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
无查询结果，则提示“查询条件无效，
请重新输入！”		
2a	
未选择待处理中记录，
签收信息、
运单基本信息均初始化为页面加载默认值		
3a	
无相关设备，
则弹窗提示“读取身份证号码失败，
请检查设备是否正常！”
身份证无效，
则弹窗提示“读取身份证号码失败，
请检查身份证是否有效！”	
证件号码	
5a	
有代收货款时，系统弹出图2界面		
用户输入密码，点击确定，继续步骤7中补充步骤
5b	
有代收货款时，操作人密码不合法，
则系统提示“操作人密码错误，请重新输入！”		
用户点击确定，终止当前操作
5c	
有代收货款时，操作人密码为空，
则系统提示“操作人密码不能为空，请输入！”		
用户点击确定，终止当前操作
5d	
付款方式为“支票”或“电汇”且款项认领编号为空，
则系统提示“请输入款项认领编号！” 		
用户点击确定，终止当前操作
5e	
客户为空，则系统弹窗提示“客户不能为空，请选择客户！ ”		
用户点击确定，终止当前操作
5f	
提货人名称为空，则系统弹窗提示“提货人名称不能为空，请录入名称！”		
用户点击确定，终止当前操作
5g	
实付运费与代收货款之和大于运单开单时的到付运费和代收货款，
则系统提示“实付运费与代收货款之和不应大于运单开单时的到付运费和代收货款，
请检查！”		
用户点击确定，终止当前操作
6a	
系统判断运单若已做结清货款,		
1.	
系统记录签收信息
2.	
到达联打印规则，参考SR11
3.	
系统标记此到达联“派送中”
4.	
系统计算仓储费，若有仓储费，则系统提示“仓储费XXX，
请注意收取并及时录入小票收入！”，
仓储费计算规则参考“通知客户（SUC-101）”系统用例
5.	
系统弹窗提示“提交成功！”
6b	
调用结算的付款结清接口异常返回“存在多个代收货款应收单，
不能付款结清操作”，系统弹窗提示“存在多个代收货款应收单，请核实！”		
用户点击确定，终止当前操作
6c	
调用结算的付款结清接口异常返回“代收货款应收单不存在，
不能付款结清操作”，系统弹窗提示“该单无代收货款，请核实！”		
用户点击确定，终止当前操作
6d	
调用结算的付款结清接口异常返回“实收代收货款金额与代收货款应收单未核销金额不一致，
不能付款结清操作”，系统弹窗提示“代收货款金额有误，请核实！”		
用户点击确定，终止当前操作
6e	
调用结算的付款结清接口异常返回“存在多个到付运费应收单，不能付款结清操作”，
系统弹窗提示“存在多个到付运费应收单，请核实！”		
用户点击确定，终止当前操作
6f	
调用结算的付款结清接口异常返回“到付运费应收单不存在，
不能付款结清操作”，系统弹窗提示“该单无代收货款，请核实！”		
用户点击确定，终止当前操作
6g	
调用结算的付款结清接口异常返回“实收到付运费金额不能大于应收到付运费金额，
不能付款结清操作”，系统弹窗提示“实付运费有误，请核实！”		
用户点击确定，终止当前操作
6h	
调用结算的付款结清接口异常返回“签收部门为空，不能付款结清操作”，
系统弹窗提示“签收部门不能为空，请核实！”		
用户点击确定，终止当前操作
6i	
调用结算的付款结清接口异常返回“签收部门在系统中不存在，不能付款结清操作”，
系统弹窗提示“签收部门不合法，请核实！”		
用户点击确定，终止当前操作
6j	
调用结算的付款结清接口异常返回“客户编码为空，不能付款结清操作”，
系统弹窗提示“客户编码为空，请核实客户身份！”		
用户点击确定，终止当前操作
6k	
调用结算的付款结清接口异常返回“OA认领编号为空，不能付款结清操作”，
系统弹窗提示“款项认领编号为空，请录入款项认领编号！”		
用户点击确定，终止当前操作
6l	
调用结算的付款结清接口异常返回“客户XX的可用信用额度不够，
不能付款结清操作”，系统弹窗提示“客户XX的可用信用额度不足，
不能选择月结/临欠，请重新选择付款方式！”		
用户点击确定，终止当前操作
6m	
"结清货款"操作时系统校验是否存有未受理的更改单，
如存在则系统给予提示：“该单有未受理的更改单，
不能进行结清货款”并取消"结清货款"操作，需要将未受理的变更单受理或拒绝后才可继续"结清货款"。		
用户点击确定，终止当前操作
7a	
若PDA损坏或其他原因导致无法使用PDA，营业员在系统中做出库操作		

序号	基本步骤	相关数据	补充步骤
1		
营业员输入查询条件，点击“查询按钮”	
查询条件、基本信息	待处理信息自动加载符合条件的运单信息，
参考SR1
2		
营业员选择待处理信息中一行		
1.	
系统自动加载运单对应的签收信息、
运单基本信息
2.	
参考SR10,SR15
3		
点击“拒收”按钮		
1.	
系统弹出图2界面提示操作人输入密码
2.	
输入密码正确，系统将还未异步调用结算接口的付款信息取消, 
系统提示“拒收已完成！”
3.	
输入密码不正确，
系统提示“密码不正确！”

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
无查询结果，
则提示“查询条件无效，
请重新输入！”		
2a	
未选择待处理中记录，
签收信息、
运单基本信息均初始化为页面加载默认值		
3a	
若付款记录中已全部生成财务单据		
系统提示“拒收失败，
请走签收变更流程！”


1.7	业务规则
序号	描述
SR1		
查询规则：
1）	
符合“查询条件”区域录入的查询条件
2）	
货物库存中
3）	
运输方式为专线
4）	
运单未结清
5）	
到达部门为本部门
SR2		
使用身份证校验方法，
录入的时候，
需要对身份证的属地进行校验和身份证的号码算法校验，
校验号码是否合法，
算法参考：

身份证中第十八位数字的计算方法为： 
1.
将前面的身份证号码17位数分别乘以不同的系数。
从第一位到第十七位的系数分别为：
7. 9 .10 .5. 8. 4. 2. 1. 6. 3. 7. 9. 10. 5. 8. 4. 2. 
2.
将这17位数字和系数相乘的结果相加。 
3.
用加出来和除以11，看余数是多少？ 
4.
余数只可能有
0 、1、 2、 3、 4、 5、 6、 7、 8、 9、 10这11个数字。
其分别对应的最后一位身份证的号码为
1 .0. X. 9. 8. 7. 6. 5. 4. 3. 2.。 
5.
通过上面得知如果余数是2，
就会在身份证的第18位数字上出现罗马数字的Ⅹ。
如果余数是10，
身份证的最后一位号码就是2。 
倒数第二位是用来表示性别的 
例如：某男性的身份证号码是34052419800101001X。
我们要看看这个身份证是不是合法的身份证。 
首先：
我们得出，
前17位的乘积和是189 
然后：
用189除以11得出的结果是17 + 2/11，
也就是说余数是2。 
最后：
通过对应规则就可以知道余数2对应的数字是x。
所以，
这是一个合格的身份证号码
SR3		
运单开单无代收货款时，
“代收货款”不可输入
SR4		
付款方式为“支票”或“电汇”，
则款项认领编号不可为空
SR5		
有代收货款时，
需要填写操作人密码
SR6		
实付运费和代收货款不能大于运单开单时的到付运费和代收货款
SR7		
付款方式选择月结/临欠时，
“实付运费”不可输入且归0
SR8		
实付运费和代收货款必须大于等于0
SR9		
客户、
提货人名称、
证件号码、
实付运费、
代收货款为必输项
SR10		
运单已结清货款，
付款方式、
实付运费、
代收货款、
收款总额、
应收代收款、
已收代收款、
应收到付款、
已收到付款加载结清货款时的数据且不可编辑
SR11		
系统判断是否已生成到达联
1. 
已生成到达联，
判断件数是否一致，
不一致则系统更新件数
2. 
未生成到达联，
系统生成到达联

SR12		
1.	
付款方式为临欠或月结时：
调用结算接口-到付运费结转临欠月结（SUC-780）
2.	
付款方式为其他方式时：
调用结算接口-实收货款（SUC-779）

SR13		
付款方式为现金，
异步调用接口
（间隔X时间调用，X时间可配置）
SR14		
1.	
查询条件中单号、
收货人手机、
收货人电话、
收货人优先级由高到低，
且具有排他性
SR15		
收款总额、
应收代收款、
已收代收款、
应收到付款、
已收到付款不可编辑
SR16		
临欠、
月结付款方式只能整票临欠或月结


1.8	
数据元素
1.8.1	
待处理信息
字段名称 	说明 	输入限制	长度	是否必填	备注
单号		
文本	10		
件数		
文本	10		
收货人		
文本	20		

1.8.2	
查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
单号		
文本	10		
收货人		
文本	20		
收货人电话		
文本	20		
收货人手机		
文本	20		
运输性质		
下拉框10		
数据字典，
包括精准、
普货、
偏线、
空运，
全部，
默认为全部

入库时间

日期选择	6		
默认为当前系统时间前三天至当前时间

1.8.3	
签收信息
字段名称 	说明 	
输入限制	长度	是否必填	备注
客户		
公共选择框		
是	
提货人证件名称		
文本		
是	
证件类型		
下拉框		
是	
数据字典，
包括身份证、
护照、
驾驶证、
暂住证、
军官证，
默认为身份证
证件号码		
文本		
是	
付款方式		
下拉框		
是	
数据字典，
包括现金、
临欠、
月结、
银行卡、
支票、
电汇，
默认是现金
款项认领编号		
文本		
否	
实付运费		
文本（数字）		
是	
默认为空
代收货款		
文本（数字）		
是	
默认为空

收款总额		
文本（数字）		
否	
应收代收款		
文本（数字）		
否	调用结算接口

已收代收款		
文本（数字）		
否	调用结算接口

已收到付款		
文本（数字）		
否	调用结算接口

应收到付款		
文本（数字）		
否	调用结算接口



1.8.4	
运单基本信息
字段名称 	说明 	
输入限制	长度	是否必填	备注
收货人信息	
收货人/
地址/
电话
文本			
单号		
文本			
到达时间		
文本			
库存状态		
文本			
通知成功		
文本			
上次通知时间		
文本			
付款方式(出发部门)	
运单的付款方式	文本			
到付总额		
文本			
代收货款		
文本			
运费		
文本			
送货费		
文本			
货物价值		
文本			
保价费		
文本			
其他费用		
文本			
费用说明		
文本			
派送方式		
文本			
货物名称		
文本			
件数		
文本			
重量		
文本			
体积		
文本			
包装		
文本			
发货人		
文本			
始发站		
文本			
始发部门		
文本			
运输方式		
文本			
运输性质		
文本			
出发时间		
文本			

1.9	
非功能性需求
使用量	
每天签收80W，自提占40%，80W*40%=32W
2012年全网估计用户数	10000用户
响应要求（如果与全系统要求 不一致的话）	
3s内响应
使用时间段	
全天
高峰使用时间段	
09：00-18：00

1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
付款结清	Foss结算子系统	货款结清操作将调用财务数据生成接口生成财务数据
客户查询接口	CRM	查询收货客户


 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.PtpCodReversDeductReqEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.PtpCodReversDeductRespEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AccountBookRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.server.util.GrayForCUBCUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.CUBCCommonService;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcReverseSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCOtherRevenueException;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcSettleException;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayInfoRequest;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RemitTransferQueryResultDto;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结清货款Service.
 *
 * @author 043258-
 * foss-zhaobin
 * @date 2012-12-18
 * 下午8:44:35
 * @since
 * @version
 */
public class RepaymentService extends CUBCCommonService implements IRepaymentService {
	
	/**
	 * add by 353654
	 */
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	private String cubcValidataAdd;  //校验款项确认编号的地址
	public void setCubcValidataAdd(String cubcValidataAdd) {
		this.cubcValidataAdd = cubcValidataAdd;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService";
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
	public static final String OTHERNO_NO_ERROR = "该运单号已生成此对应收入类别的小票";
	public static final String QUERY_BAlANCE_AMOUNT_START="当付款方式为余额,调用CUBC查询余额开始";
	public static final String QUERY_BAlANCE_AMOUNT_END="当付款方式为余额,调用CUBC查询余额完成";
	public static final String QUERY_BAlANCE_AMOUNT_PARAMS_ERROR="当付款方式为余额,调用CUBC查询余额参数不合法";
	public static final String QUERY_BAlANCE_AMOUNT_CUBC_ERROR="当付款方式为余额,调用CUBC查询余额失败";
	public static final String THE_SAFEKEEPING_FEE_RECEIPTS = "保管费小票";
	public static final String MARK = "02";
	
	/** JOB name. */
	private static final String JOB_NAME = "PAYMENT_OPERATE_JOB";
	/** 运输性质为全部. */
	private static final String TOTAL = "ALL";
	
	/**1440. */
	private static final int JOBTIME = 1440;
	
	/**30. */
	private static final int DEFTIME = 30;
	
	/** 2013-08-01 */
	private static final String dateStr = "2013-08-01";
	
	/** 时间格式 */
	private static final String format = "yyyy-MM-dd";
	
	/** 日志服务. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RepaymentService.class);
	
	/** job初始化. */
	private static final String JOBID = "N/A";
	
	/** 付款信息dao. */
	private IRepaymentDao repaymentDao;
	
	/** 偏线空运服务. */
	private IAirAgencySignService airAgencySignService;
	
	/** 结算应收单服务. */
	private IBillReceivableService billReceivableService;
	
	/** 到达联服务. */
	private IArriveSheetManngerService arriveSheetManngerService;
	
	/** 签收结算货款服务. */
	private IPaymentSettlementService paymentSettlementService;
	
	/** 汇款服务接口类. */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;
	
	/** 实际货物服务. */
	private IActualFreightService actualFreightService;
	/**
	 * 中转配载校验
	 */
	private IArrivalService arrivalService;
	/** 登录服务. */
	private ILoginService loginService;
	
	/** 更改单service. */
	private IWaybillRfcService waybillRfcService;
	
	/** 外场相关共通接口. */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	/** 配载单模块service类 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	//张新 2015-2-4
	/** 运单接口service类 */
	private IWaybillExpressService waybillExpressService;
	/**张新
	 * 快递代理服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * esb地址配置Service
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	/** 
	 * 零
	 */
	@SuppressWarnings("unused")
	private static final int ZERO = 0;
	private IRepaymentService repaymentService;
	
	/** job日志dao. */
	private IShareJobDao shareJobDao;
	
	/** 系统配置参数 Service接口. */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	private ITakingService takingService;
	/**
	 * 小票服务
	 */
	private IOtherRevenueService otherRevenueService;
	/**
	 *调用中转接口服务 
	 */
	private IStReportService  stReportService;
	
	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 子母件服务接口层
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 运单签收结果
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 综合服务接口 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 综合服务接口 部门信息 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * T+0报表
	 * @author 309603 yangqiang
	 * @date 2016-02-23
	 */
	private IPdaPosManageService pdaPosManageService;
	
	/**
	 * 注入CUBC结清转接口Service
	 * @author 378375
	 */
	private ICubcSettlementService cubcSettlementService;
	/**
	 * 注入CUBC反结清接口Service
	 * @author 378375
	 */
	private ICubcReverseSettlementService cubcReverseSettlementService;
	
	/**
	 * 综合管理-客户信息Service
	 */
	private ICustomerService customerService;
    
	/**
	 * 返回待处理列表.
	 *
	 * @param airAgencyQueryDto the air agency query dto
	 * dto.waybillNo
	 * 运单号
	 * dto.receiveCustomerName
	 * 收货人(收货客户名称)
	 * dto.receiveCustomerPhone
	 * 货客户电话
	 * dto.receiveCustomerMobilephone
	 * 收货人手机
	 * dto.active
	 * 运单状态
	 * dto.settleStatus
	 * 结清状态
	 * dto.lastLoadOrgCode
	 * 最终配载部门（判断是否为本部门）
	 * dto.externalBillNo
	 * 外发单号
	 * dto.productCode
	 * 运输性质
	 * dto.transferExternal
	 * 是否中转外发
	 * dto.storageTimeBegin
	 * 入库时间(起)
	 * dto.storageTimeEnd
	 * 入库时间(止)
	 * dto.storageQty
	 * 件数
	 * dto.endStockOrgCode
	 * 最后库存code
	 * dto.goodsAreaCode
	 * 库区
	 * dto.auditStatus
	 * 审核状态
	 * dto.arriveNotoutGoodsQty
	 * 到达未出库件数
	 * @return the list
	 * the list
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-11-20
	 * 下午3:49:50
	 * @since
	 * @version
	 */
	public List<AirAgencyQueryDto> queryAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto){
		// 如果airAgencyQueryDto不为空
		if (airAgencyQueryDto != null){
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			//如果单号不为空
			if(StringUtil.isNotEmpty(airAgencyQueryDto.getWaybillNo())){
				boolean flag = repaymentService.isPayment(airAgencyQueryDto.getWaybillNo());
				if(flag){
					// 抛出业务异常
					throw new RepaymentException(RepaymentException.PAYMENT_FINISH);
				}
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(airAgencyQueryDto.getWaybillNo());
				if(waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && currOrgCode.equals(waybillEntity.getReceiveOrgCode())
						&& FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
				{
					//update by foss 231434 2015-6-2 
					//对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做配载校验
					Date date = DateUtils.convert(dateStr, format);
					//中转接口校验通过
					if(!date.before(waybillEntity.getBillTime()) || 
							vehicleAssembleBillService.queryVehicleAssemblyBillByWaybillNo(airAgencyQueryDto.getWaybillNo()))
					{
						//传入当前部门
						airAgencyQueryDto.setReceiveOrgCode(currOrgCode);
						// 设置状态为生效
						airAgencyQueryDto.setActive(FossConstants.YES);
						// 设置为未结清
						airAgencyQueryDto.setSettleStatus(FossConstants.NO);
						// 设置运单处理状态：已开单PC_ACTIVE，已经补录PDA_PENDING
						List<String> checkStatus = new ArrayList<String>();
						checkStatus.add("PDA_ACTIVE");
						checkStatus.add("PC_ACTIVE");
						airAgencyQueryDto.setCheckStatus(checkStatus);		
						// 查询待处理列表
						return repaymentDao.queryAirAgencyQueryDtoListByReceiveOrg(airAgencyQueryDto);
					}else
					{
						// 抛出业务异常
						throw new RepaymentException(RepaymentException.VEHICLEASSEMBLEBILL_INVALID);
					}
				}
			}
			// 如果运输性质为全部
			if (airAgencyQueryDto.getProductCode().equals(TOTAL)){
				//运输性质传空
				airAgencyQueryDto.setProductCode("");
			}
			// 如果收货人手机不为空
			if (StringUtil.isNotEmpty(airAgencyQueryDto.getReceiveCustomerMobilephone())){
				//收货客户电话传空
				airAgencyQueryDto.setReceiveCustomerPhone(null);
				//收货客户名称传空
				airAgencyQueryDto.setReceiveCustomerName(null);
			}
			// 如果收货人电话不为空
			else if (StringUtil.isNotEmpty(airAgencyQueryDto.getReceiveCustomerPhone())){
				//收货客户名称传空
				airAgencyQueryDto.setReceiveCustomerName(null);
			}
			
			// 获取当前部门
			airAgencyQueryDto.setLastLoadOrgCode(currOrgCode);
			// 设置状态为生效
			airAgencyQueryDto.setActive(FossConstants.YES);
			// 设置为未结清
			airAgencyQueryDto.setSettleStatus(FossConstants.NO);
			//设置运单处理状态：已开单PC_ACTIVE，已经补录PDA_PENDING
			List<String> checkStatus = new ArrayList<String>();
			checkStatus.add("PDA_ACTIVE");
			checkStatus.add("PC_ACTIVE");
			airAgencyQueryDto.setCheckStatus(checkStatus);			
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ld=new ArrayList<String>();
				ld.add(list.get(1));
				ld.add(list.get(2));
				//传入最终库存部门code
				airAgencyQueryDto.setEndStockOrgCode(list.get(0));
				//传入库区code
				airAgencyQueryDto.setGoodsAreaCodes(ld);
			}
			
			//根据是否快递字段判断   add  by  yangkang
			if(airAgencyQueryDto.getIsExpress().equals(FossConstants.YES)){
				// 查询快递待处理列表
				return repaymentDao.queryExpressAirAgencyQueryDtoList(airAgencyQueryDto);
			}else{
				// 查询待处理列表
				return repaymentDao.queryAirAgencyQueryDtoList(airAgencyQueryDto);
			}
		}
		//返回空
		return null;
	}	

	/**
	 * 根据运单号返回详细信息.
	 *
	 * @param waybillNo 
	 * 		the 
	 * 			waybill no
	 * @return 
	 * 		the 
	 * 			repayment dto
	 * the repayment dto
	 * @author 
	 * 		043258-
	 * 			foss-zhaobin
	 * @date 2012-11-21
	 * 下午2:03:40
	 * @since
	 * @version
	 */
	public RepaymentDto queryPaymentByWaybillNo(String waybillNo){
		// 如果运单号不为空
		if (StringUtils.isNotEmpty(waybillNo)){
			//付款dto
			RepaymentDto repaymentDto = new RepaymentDto();
			// 获得运单信息
			WaybillDto dto = new WaybillDto();
			dto = airAgencySignService.queryByWaybillNo(waybillNo);
			
			this.addCentralizedSettlementFeildsToWaybillDto( waybillNo, dto);
			
			repaymentDto.setWaybillDto(dto);
			if(repaymentDto.getWaybillDto() != null ){
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentDto.getWaybillDto().getPaidMethod())){
					//ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
					List<String> waybillNos = new ArrayList<String>();
					waybillNos.add(waybillNo);
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
					List<String> notPayWaybillNo = null;
					//灰度分流 --update by 243921 
					String vestSystemCode = GrayForCUBCUtil.getGray(waybillNos, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						notPayWaybillNo=takingService.unpaidOnlinePay(waybillNos);
						if(CollectionUtils.isNotEmpty(notPayWaybillNo)){
							repaymentDto.setIsPayByOL(FossConstants.NO);
						}
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//调用CUBC查询物流交易单  应收信息校验  353654
							FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
							requestDto1.setWaybillNos(waybillNos);
							FossSearchTradeResponseDO responseDto1 = null;
							try {
								responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
							} catch (Exception e) {
								LOGGER.error("调用CUBC查询物流交易单接口出现异常,异常信息为："+e);
								throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
							}
							if(responseDto1 != null){
								if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
									LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
									throw new SettlementException(responseDto1.getMsg());
								}
								Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
								List<TradeDO> tradeslist = dataMap.get(waybillNo);
								if(!CollectionUtils.isEmpty(tradeslist)){
									for (TradeDO tradeDO : tradeslist) {
										if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
							    				   equals(tradeDO.getOrderSubType())&&
							    				   SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
							    				   equals(tradeDO.getOrderSubType())
							    				   &&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
							    			   //添加数据
							    			   notPayWaybillNo.add(waybillNo);
							    		   }
									}
									if(CollectionUtils.isNotEmpty(notPayWaybillNo)){
										repaymentDto.setIsPayByOL(FossConstants.NO);
									}
								}
							}
					}
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
				}
			}
			// 获得财务签收信息
			FinancialDto financialDto = new FinancialDto();
			//存放应收单查询过滤条件 dto
			BillReceivableConditionDto billReceiveable = null;
			
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("waybillNo", waybillNo);
			params.put("active", FossConstants.YES);
			// 根据运单号、订单号判定是否子母件
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			//判断是否子母件		
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				billReceiveable = new BillReceivableConditionDto(twoInOneWaybillDto.getMainWaybillNo()); 
				WaybillEntity  waybillEntity = waybillManagerService.queryWaybillBasicByNo(twoInOneWaybillDto.getMainWaybillNo());
				//add of 329757 2016-5-20
				//判断母单信息是否为空，若为空 ，阻断抛出异常
				if(null==waybillEntity){
					throw new RepaymentException("母单信息为空,不能结清货款");
				}
				financialDto.setTotalMoney(waybillEntity.getTotalFee());
			} else { // 不是子母件
				billReceiveable = new BillReceivableConditionDto(waybillNo);
			}
			//财务单据查询，灰度改造   353654 ---------------------------start 
			List<BillReceivableEntity> billReceivableEntities = null;
			ArrayList<String> arrayList = new ArrayList<String>();
        	arrayList.add(waybillNo);
            //灰度分流  --update by 243921
			String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillNo);
				billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
				LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillNo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				try {
					billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillNo);			
				} catch (Exception e) {
					LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
					throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
				}
			}
			//财务单据查询，灰度改造   353654 ---------------------------end
			if(CollectionUtils.isEmpty(billReceivableEntities)){
				LOGGER.info("单号"+waybillNo+";"+vestSystemCode+"财务查询为空");
				// 应收代收款
				financialDto.setReceiveableAmount(BigDecimal.ZERO);
				// 已收代收款
				financialDto.setReceivedAmount(BigDecimal.ZERO);
				// 应收到付款
				financialDto.setReceiveablePayAmoout(BigDecimal.ZERO);
				//已收到付款
				financialDto.setReceivedPayAmount(BigDecimal.ZERO);
			}
			if(!CollectionUtils.isEmpty(billReceivableEntities)){
				for (BillReceivableEntity billReceivableEntity : billReceivableEntities){
					// 到达应收单
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						// 应收到付款
						financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
						// 已收到付款
						financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
					} // 代收货款应收单
					else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						// 应收代收款
						financialDto.setReceiveableAmount(billReceivableEntity.getUnverifyAmount());
						// 已收代收款
						financialDto.setReceivedAmount(billReceivableEntity.getVerifyAmount());
					}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						// 应收到付款
						financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
						// 已收到付款
						financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
					}
				}
				//客户编码
				financialDto.setConsigneeCode(billReceivableEntities.get(0).getSettleCustCode());
				//客户名称
				financialDto.setConsigneeName(billReceivableEntities.get(0).getSettleCustName());
			}
			//张新 2015-2-3$$
			//调用判断是否是新单接口
			WaybillExpressEntity waybills = new WaybillExpressEntity();
			List<WaybillExpressEntity> expList = waybillExpressService.queryWaybillListByWaybillNo(waybillNo);
			waybills = CollectionUtils.isNotEmpty(expList) ? expList.get(0) : null;
			//判断是否是新单(返货)
			if(waybills!=null){//是
				//设置返货标记，用于前台界面展示影藏字段
				repaymentDto.setIsback(1);
				//调用获取关联单号金额接口
				//根据原单得到结算应收单信息
				FinancialDto financialDtoco = null;
				// 设置原运单号
				params.put("waybillNo", waybills.getOriginalWaybillNo());
				// 根据运单号、订单号判定是否子母件
				TwoInOneWaybillDto twoInOneWaybillDtoOrig = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
				//判断原运单号是否子母件		
				if (twoInOneWaybillDtoOrig != null && FossConstants.YES.equals(twoInOneWaybillDtoOrig.getIsTwoInOne())) { // 是子母件
					financialDtoco = queryFinanceSign(twoInOneWaybillDtoOrig.getMainWaybillNo());
				} else { // 不是子母件
					financialDtoco = queryFinanceSign(waybills.getOriginalWaybillNo());
				}
				//应收到付款 即  关联单号费用
				BigDecimal receiveablePayAmoout = financialDtoco.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDtoco.getReceiveablePayAmoout();
				//设置关联单号费用
				financialDto.setConnetnumCost(receiveablePayAmoout);
				//应收代付款非空转换
				BigDecimal finrece=financialDto.getReceiveableAmount()==null ?  BigDecimal.ZERO : financialDto.getReceiveableAmount();
				//应收到付款非空转换
				BigDecimal finrecep=financialDto.getReceiveablePayAmoout()==null ? BigDecimal.ZERO:financialDto.getReceiveablePayAmoout();
				//总收款金额                                                                          关联单号费用                                                                          应收代收款                        应收到付款
				financialDto.setTotalPayment((financialDto.getConnetnumCost().add(finrece)).add(finrecep));
			}
			//设置财务dto
			repaymentDto.setFinancialDto(financialDto);

			// 获得付款记录
			RepaymentEntity entity = new RepaymentEntity();
			
			//判断是否子母件		
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				//设置运单号
				entity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
			} else { // 不是子母件
				//设置运单号
				entity.setWaybillNo(waybillNo);
			}
			//有效
			entity.setActive(FossConstants.YES);
			//查询付款记录
			List<RepaymentEntity> repaymentEntityList = repaymentDao.searchRepaymentList(entity);
			//设置付款记录
			repaymentDto.setRepaymentEntityList(repaymentEntityList);
			//返回dto
			return repaymentDto;
		}
		//返回空
		return null;
	}
	
	/**
	 * 根据运单号返回详细信息--整车
	 *
	 * @param waybillNo 
	 * 		the 
	 * 			waybill no
	 * @return 
	 * 		the 
	 * 			repayment dto
	 * the repayment dto
	 * @author 306579 guoxinru
	 * @date 2016-05-10
	 */
	public RepaymentDto vtsQueryPaymentByWaybillNo(String waybillNo){
		// 如果运单号不为空
		if (StringUtils.isNotEmpty(waybillNo)){
			//付款dto
			RepaymentDto repaymentDto = new RepaymentDto();
			// 获得运单信息
			WaybillDto dto = new WaybillDto();
			
			try {
				dto = airAgencySignService.queryByWaybillNo(waybillNo);
			} catch (SettlementException e) {
				LOGGER.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			
			if(null==dto){
				LOGGER.error("根据运单号查询运单表为空！");
				throw new SettlementException("根据运单号查询运单表为空！");
			}
			
			this.addCentralizedSettlementFeildsToWaybillDto( waybillNo, dto);
			
			repaymentDto.setWaybillDto(dto);
			if(repaymentDto.getWaybillDto() != null ){
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentDto.getWaybillDto().getPaidMethod())){
					//ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
					List<String> waybillNos = new ArrayList<String>();
					waybillNos.add(waybillNo);
					List<String> notPayWaybillNo = null;
					try {
						//灰度分流  --update by 243921
						String vestSystemCode = GrayForCUBCUtil.getGray(waybillNos, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							notPayWaybillNo = takingService.unpaidOnlinePay(waybillNos);
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							//调用CUBC查询物流交易单  应收信息校验  353654
							FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
							requestDto1.setWaybillNos(waybillNos);
							FossSearchTradeResponseDO responseDto1 = null;
							try {
								responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
							} catch (Exception e) {
								LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
								throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
							}
							if(responseDto1 != null){
								if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
									LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
									throw new SettlementException(responseDto1.getMsg());
								}
								Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
								List<TradeDO> tradeslist = dataMap.get(waybillNo);
								if(!CollectionUtils.isEmpty(tradeslist)){
									for (TradeDO tradeDO : tradeslist) {
										if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(tradeDO.getOrderSubType()) 
												&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(tradeDO.getOrderSubType())
								   				&& BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
								    			//添加数据
								    		notPayWaybillNo.add(waybillNo);
								    	}
									}
								}
							}
						}
						//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
					} catch (SettlementException e) {
						LOGGER.error(e.getMessage(), e);
						throw new SettlementException(e.getErrorCode());
					}
					if(CollectionUtils.isNotEmpty(notPayWaybillNo)){
						repaymentDto.setIsPayByOL(FossConstants.NO);
					}
				}
			}
			// 获得财务签收信息
			FinancialDto financialDto = new FinancialDto();
			//存放应收单查询过滤条件 dto
			BillReceivableConditionDto billReceiveable = null;
			
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("waybillNo", waybillNo);
			params.put("active", FossConstants.YES);
	
			billReceiveable = new BillReceivableConditionDto(waybillNo);
			try {
				//财务单据查询，灰度改造   353654 ---------------------------start 
				List<BillReceivableEntity> billReceivableEntities = null;
				ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
				//灰度分流  --update by 243921
				String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillNo);
					billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
					LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillNo);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					try {
						billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillNo);			
					} catch (Exception e) {
						LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
						throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
					}
				}
				//财务单据查询，灰度改造   353654 ---------------------------end
				if(!CollectionUtils.isEmpty(billReceivableEntities)){
					for (BillReceivableEntity billReceivableEntity : billReceivableEntities){
						// 到达应收单
						if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())){
							// 应收到付款
							financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
							// 已收到付款
							financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
						} // 代收货款应收单
						else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())){
							// 应收代收款
							financialDto.setReceiveableAmount(billReceivableEntity.getUnverifyAmount());
							// 已收代收款
							financialDto.setReceivedAmount(billReceivableEntity.getVerifyAmount());
						}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
							// 应收到付款
							financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
							// 已收到付款
							financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
						}
					}
				}else{
					LOGGER.info("单号"+waybillNo+";"+vestSystemCode+"财务查询为空");
					// 应收代收款
					financialDto.setReceiveableAmount(BigDecimal.ZERO);
					// 已收代收款
					financialDto.setReceivedAmount(BigDecimal.ZERO);
					// 应收到付款
					financialDto.setReceiveablePayAmoout(BigDecimal.ZERO);
					//已收到付款
					financialDto.setReceivedPayAmount(BigDecimal.ZERO);
				}
			} catch (SettlementException e) {
				LOGGER.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			//张新 2015-2-3$$
			//调用判断是否是新单接口
			WaybillExpressEntity waybills = new WaybillExpressEntity();
			List<WaybillExpressEntity> expList = null;
			try {
				expList = waybillExpressService.queryWaybillListByWaybillNo(waybillNo);
			} catch (SettlementException e) {
				LOGGER.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			waybills = CollectionUtils.isNotEmpty(expList) ? expList.get(0) : null;
			//判断是否是新单(返货)
			if(waybills!=null){//是
				//设置返货标记，用于前台界面展示影藏字段
				repaymentDto.setIsback(1);
				//调用获取关联单号金额接口
				//根据原单得到结算应收单信息
				FinancialDto financialDtoco = null;
				// 设置原运单号
				params.put("waybillNo", waybills.getOriginalWaybillNo());
				// 根据运单号、订单号判定是否子母件
				//判断原运单号是否子母件		
				financialDtoco = queryFinanceSign(waybills.getOriginalWaybillNo());
				//应收到付款 即  关联单号费用
				BigDecimal receiveablePayAmoout = financialDtoco.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDtoco.getReceiveablePayAmoout();
				//设置关联单号费用
				financialDto.setConnetnumCost(receiveablePayAmoout);
				//应收代付款非空转换
				BigDecimal finrece=financialDto.getReceiveableAmount()==null ?  BigDecimal.ZERO : financialDto.getReceiveableAmount();
				//应收到付款非空转换
				BigDecimal finrecep=financialDto.getReceiveablePayAmoout()==null ? BigDecimal.ZERO:financialDto.getReceiveablePayAmoout();
				//总收款金额                                                                          关联单号费用                                                                          应收代收款                        应收到付款
				financialDto.setTotalPayment((financialDto.getConnetnumCost().add(finrece)).add(finrecep));
			}
			//设置财务dto
			repaymentDto.setFinancialDto(financialDto);

			// 获得付款记录
			RepaymentEntity entity = new RepaymentEntity();
			entity.setWaybillNo(waybillNo);
			//有效
			entity.setActive(FossConstants.YES);
			//查询付款记录
			List<RepaymentEntity> repaymentEntityList = repaymentDao.searchRepaymentList(entity);
			//设置付款记录
			repaymentDto.setRepaymentEntityList(repaymentEntityList);
			//返回dto
			return repaymentDto;
		}
		//返回空
		return null;
	}
	
	/**
	 * 选中运单是否有签收单原件返回
	 * @return
	 */
	@Override
	public WaybillDto queryWaybillByBack(String waybillNo){
		WaybillDto waybillDto = new WaybillDto();
		if (StringUtils.isNotBlank(waybillNo)) {
				//查询运单返单类别
				WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if ( waybillEntity != null) {
					if (StringUtils.isNotBlank(waybillEntity.getReturnBillType())) {
						waybillDto.setReturnbillType(waybillEntity.getReturnBillType());
						//返单类别为非“无需返单”
						if (!waybillEntity.getReturnBillType().equals("NONE")) {
							//查询运单对应返单号
							WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryExpressWaybillByOriginalWaybillNo(waybillNo);
							//List<ReturnBillProcessEntity> billProcessEntities = returnSignBillProcessService.querySignedBillByWaybillNo(waybillNo);
							if (waybillExpressEntity != null && StringUtils.isNotBlank(waybillExpressEntity.getWaybillNo())){
									waybillDto.setReturnWaybillNo(waybillExpressEntity.getWaybillNo());
							}
						}
					}
				}
		}
		return waybillDto;
	}
	
	/**
	 * 添加统一结算字段到waybillDto上
	 * @param waybillNo
	 * @param dto
	 */
	private void addCentralizedSettlementFeildsToWaybillDto(String waybillNo,WaybillDto dto){
		
		ActualFreightEntity afEntity = waybillManagerService.queryWaybillByNo(waybillNo).getActualFreightEntity();
		if(afEntity!=null) {
		dto.setStartCentralizedSettlement(afEntity.getStartCentralizedSettlement());
		dto.setArriveCentralizedSettlement(afEntity.getArriveCentralizedSettlement());
		dto.setStartContractOrgCode(afEntity.getStartContractOrgCode());
		dto.setArriveContractOrgCode(afEntity.getArriveContractOrgCode());
		dto.setStartContractOrgName(afEntity.getStartContractOrgName());
		dto.setArriveContractOrgName(afEntity.getArriveContractOrgName());
		dto.setStartReminderOrgCode(afEntity.getStartReminderOrgCode());
		dto.setArriveReminderOrgCode(afEntity.getArriveReminderOrgCode());
	}
	}
	
	/**
	 * 新增付款信息(内含财务接口调用).
	 *
	 * @param repaymentEntity the repayment entity
	 * 		waybillNo  
	 * 			运单号
	 * 		repaymentNo  
	 * 			付款编号
	 * 		active	
	 * 			是否有效
	 * 		consigneeCode
	 * 			客户编码
	 * 		consigneeName	
	 * 			客户名称
	 * 		paymentType		
	 * 			付款方式
	 * 		claimNo
	 * 			款项认领编号
	 * 		actualFreight
	 * 			实付运费
	 * 		codAmount	
	 * 			代收货款
	 * 		paymentTime	
	 * 			付款时间
	 * 		modifyTime 	
	 * 			最后更新时间
	 * 		storageFee	
	 * 			仓储费
	 * 		operator	
	 * 			操作人
	 * 		operatorCode	
	 * 			操作人编码
	 * 		operateOrgName	
	 * 			操作部门	
	 * 		operateOrgCode	
	 * 			操作部门编码
	 * 		currencyCode	
	 * 			币种	
	 * 		isRfcing	
	 * 			是否审批中
	 * 		stlbillGeneratedStatus	
	 * 			是否已有财务单据
	 * 		jobId	
	 * 			job id
	 * 		deliverymanName	
	 * 			提货人姓名
	 * 		identifyType	
	 * 			证件类型
	 * 		identifyCode	
	 * 			证件号码
	 * 		passWord	
	 * 			密码
	 * 		timeRange	
	 * 			后台job时间间隔
	 * 		firStlbillGeneratedStatus	
	 * 			财务单据初始状态
	 * 		defJobId	
	 * 			默认job id	
	 * @param currentInfo the current info
	 * 		userName	
	 * 			用户名
	 * 		empCode 	
	 * 			员工工号
	 * 		empName		
	 * 			员工姓名
	 * 		currentDeptCode	
	 * 			当前登录部门编码
	 * 		currentDeptName	
	 * 			当前登录部门名称
	 * 		user	
	 * 			当前登录用户
	 * 		dept 	
	 * 			当前部门
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-26
	 * 		 下午3:01:23
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#addPaymentInfo
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Transactional
	@Override
	public void addPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,WaybillDto waybilldto) {
		// 判断传入参数 付款 对象为空
		if (repaymentEntity == null) {
			throw new RepaymentException("传入参数  付款 对象不能为空");
		}
		/*
		// 判断传入参数 当前用户 对象为空
		if (currentInfo == null) {
			throw new RepaymentException("传入参数  当前用户 对象不能为空");
		}
		// 判断传入参数 运单基本信息Dto 对象为空
		if (waybilldto == null) {
			throw new RepaymentException("传入参数  运单基本信息Dto 对象不能为空");
		}*/
		// 运单
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(repaymentEntity.getWaybillNo());	
		
		// 子母件查询通用接口
		TwoInOneWaybillDto twoInOneWaybillDto = null;
		// 结清前验证
		verification (repaymentEntity, currentInfo, waybillEntity);
		
		// 查询实际货物信息
		ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(repaymentEntity.getWaybillNo());
					
		// 判断运单 是否已终止或已作废
		if (actentity != null && (WaybillConstants.ABORTED.equals(actentity.getStatus()) 
				|| WaybillConstants.OBSOLETE.equals(actentity.getStatus()))) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.WAYBILLRFC_ERROR); 
		}	
		
		// 得到结算应收单信息
		FinancialDto financialDto = queryFinanceSign(repaymentEntity.getWaybillNo());
		// 应收代收款
		BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
		// 应收到付款
		BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
		
		// 是否返货单标识
		boolean isReturnOrders = false;
		// 判断是否快递
		if (waybillEntity != null &&  WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {
			// 运单快递 
			WaybillExpressEntity waybillExpress = waybillExpressService.queryWaybillExpressByNo(repaymentEntity.getWaybillNo());
			// 判断是否返货单
			if (waybillExpress != null && StringUtils.isNotBlank(waybillExpress.getReturnType()) && StringUtils.isNotBlank(waybillExpress.getOriginalWaybillNo())) {
				isReturnOrders = true;
				// 定义一个结算应收单信息
				FinancialDto financialDtoOriginal = null;

				// 创建Map集合存放查询条件
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("waybillNo", waybillExpress.getOriginalWaybillNo());
				params.put("active", FossConstants.YES);
				// 根据运单号、订单号判定是否子母件
				twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);

				// 判断是否子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					// 得到母单 结算应收单信息
					financialDtoOriginal = queryFinanceSign(twoInOneWaybillDto.getMainWaybillNo());
				} else {
					// 得到原单 结算应收单信息
					financialDtoOriginal = queryFinanceSign(waybillExpress.getOriginalWaybillNo());
				}
				
				// 应收代收款
				BigDecimal receiveableAmountOriginal = financialDtoOriginal.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDtoOriginal.getReceiveableAmount();
				// 应收到付款
				BigDecimal receiveablePayAmooutOriginal = financialDtoOriginal.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDtoOriginal.getReceiveablePayAmoout();
	
				//  创建付款信息对象存储原单付款信息
				RepaymentEntity originalreWaybillPayment = new RepaymentEntity();
				BeanUtils.copyProperties(repaymentEntity, originalreWaybillPayment);
				// 判断新单是否一次性结清
				if ((!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(repaymentEntity.getPaymentType())) 
						&& (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(repaymentEntity.getPaymentType()))
						&& (!repaymentEntity.getCodAmount().equals(receiveableAmount) 
						|| !repaymentEntity.getActualFreight().equals(receiveablePayAmoout.add(receiveablePayAmooutOriginal)))) {
					// 不是   抛出业务异常 
					throw new RepaymentException(RepaymentException.ONCE_ERROR);
				}
				//设置运单信息为原单运单号
				originalreWaybillPayment.setWaybillNo(waybillExpress.getOriginalWaybillNo());
				// 按票返
				if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpress.getReturnType())) {
					originalreWaybillPayment.setActualFreight(receiveablePayAmooutOriginal);
					originalreWaybillPayment.setCodAmount(receiveableAmountOriginal);
					//原单结清
					this.addOriginalPaymentInfo(originalreWaybillPayment, currentInfo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO, waybillExpress.getWaybillNo(), twoInOneWaybillDto);
				} else { // 按件返
					//原单结清
					this.addOriginalPaymentInfo(originalreWaybillPayment, currentInfo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE, waybillExpress.getWaybillNo(), twoInOneWaybillDto);
				}
				
				if (repaymentEntity.getActualFreight() != null && repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0){
					repaymentEntity.setActualFreight(repaymentEntity.getActualFreight().subtract(receiveablePayAmooutOriginal));
				}
				twoInOneWaybillDto = null;
			} else {
				// 通过原单编号、开单类型查询运单快递
				waybillExpress = waybillExpressService.queryWaybillByOriginalWaybillNo(repaymentEntity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				// 判断是否是 返货单的原单
				if(waybillExpress != null ){ // 是
					// 抛出业务异常    此时原单不能结清货款
					throw new RepaymentException(RepaymentException.EXPRESS_ERROR);
			    }
				
				// 创建Map集合存放查询条件
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("waybillNo", repaymentEntity.getWaybillNo());
				params.put("active", FossConstants.YES);
				// 根据运单号、订单号判定是否子母件
				twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
				// 判断是否是子母件
				validaCurrentIn(repaymentEntity, currentInfo, waybilldto,
						twoInOneWaybillDto); 
			}
		}	
		// 创建 付款 对象 充作查询条件
		RepaymentEntity queryParam = new RepaymentEntity();
		queryParam.setActive(FossConstants.ACTIVE);
		queryParam.setWaybillNo(repaymentEntity.getWaybillNo());
		// 获得付款LIST--通过运单号
		List<RepaymentEntity> repaymentList = repaymentDao.searchRepaymentList(queryParam);
		// 实付运费
		BigDecimal actualFreight = repaymentEntity.getActualFreight() == null ? BigDecimal.ZERO : repaymentEntity
				.getActualFreight();
		// 代收货款
		BigDecimal codAmount = repaymentEntity.getCodAmount() == null ? BigDecimal.ZERO : repaymentEntity
				.getCodAmount();
		// 遍历付款LIST
		for (RepaymentEntity repayment : repaymentList) {
			// 财务单据未生成 或 财务单据生成中
			if (RepaymentConstants.STLBILL_NOGENERATE.equals(repayment.getStlbillGeneratedStatus())
					|| RepaymentConstants.STLBILL_GENERATEING.equals(repayment.getStlbillGeneratedStatus())) {
				codAmount = codAmount.add(repayment.getCodAmount());
				actualFreight = actualFreight.add(repayment.getActualFreight());
			}
		}

		// 判断实付运费大于应收到付款
		if (actualFreight.compareTo(receiveablePayAmoout) == 1) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.PAYMENT_NOTGTPAYAMOUNT);
		}

		// 判断代收货款大于应收代收款
		if (codAmount.compareTo(receiveableAmount) == 1) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.PAYMENT_NOTGTRECEIVEABLE);
		}

		// 判断是否统一结算
		if (FossConstants.ACTIVE.equals(actentity.getArriveCentralizedSettlement()) && !isReturnOrders) {
			// 判断 实付运费 大于 0 并且 代收货款等于 0
			if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0
					&& repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) == 0) {
				// 判断是否是快递
				if (waybillEntity != null
						&& WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {
					if (!Arrays.asList("CT", "TT").contains(repaymentEntity.getPaymentType())) {
						throw new RepaymentException("统一结算限制:运单产品类型为快递，实付运费>0，代收=0时。结清货款时付款方式只能为“月结”或“电汇”");
					}
				} else { // 零担
					if (!Arrays.asList("DT", "CT", "TT").contains(repaymentEntity.getPaymentType())) {
						throw new RepaymentException("统一结算限制:运单产品类型为零担，实付运费>0，代收=0时。结清货款时付款方式只能为“临时欠款”、“月结”或“电汇”");
					}
				}
				// 判断实付运费等于 0 并且 代收货款大于 0
			} else if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) == 0
					&& repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
				if (Arrays.asList("DT", "CT").contains(repaymentEntity.getPaymentType())) {
					throw new RepaymentException("统一结算限制:实付运费=0，代收>0时，以代收货款标准限制付款方式（不可为临欠、月结）");
				}
				// 判断实付运费大于0 并且代收货款大于0
			} else if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0
					&& repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
				if (!"TT".equals(repaymentEntity.getPaymentType())) {
					throw new RepaymentException("统一结算限制:实付运费>0，代收>0时，付款方式限制（只可为电汇）");
				}
			}
		}

		// 调用 根据不同的付款方式 结清货款方法
		settleUpRepayment(repaymentEntity, currentInfo, receiveableAmount, receiveablePayAmoout, twoInOneWaybillDto, waybilldto);

	}

	private void validaCurrentIn(RepaymentEntity repaymentEntity,
			CurrentInfo currentInfo, WaybillDto waybilldto,
			TwoInOneWaybillDto twoInOneWaybillDto) {
		if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 为子母件
			// 判断 是母件 还是子件
			if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子件
				// 判断母件是否 已结清
				if (!isPayment(twoInOneWaybillDto.getMainWaybillNo()))  { // 没结清
					//得到原单 结算应收单信息
					FinancialDto financialMain = queryFinanceSign(twoInOneWaybillDto.getMainWaybillNo());
					//应收代收款
					BigDecimal receiveableAmountMain = financialMain.getReceiveableAmount()==null ? BigDecimal.ZERO : financialMain.getReceiveableAmount();
					//应收到付款
					BigDecimal receiveablePayAmooutMain = financialMain.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialMain.getReceiveablePayAmoout();
					// 创建付款信息对象存储母件付款信息
					RepaymentEntity mainPayment = new RepaymentEntity();
					BeanUtils.copyProperties(repaymentEntity, mainPayment);
					//设置运单信息为原单运单号
					mainPayment.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
					mainPayment.setActualFreight(receiveableAmountMain);
					mainPayment.setCodAmount(receiveablePayAmooutMain);
					// 调用 根据不同的付款方式 结清货款方法
					settleUpRepayment(mainPayment, currentInfo, receiveableAmountMain, receiveablePayAmooutMain, twoInOneWaybillDto, waybilldto);

					if (repaymentEntity.getActualFreight() != null && repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0){
						repaymentEntity.setActualFreight(repaymentEntity.getActualFreight().subtract(receiveablePayAmooutMain));
					}
				}
			} 	
		}
	}
	
	/** 
	 * 根据不同的付款方式 结清货款
	 * @param repaymentEntity 付款 对象
	 * @param currentInfo 当前信息对象
	 * @param receiveableAmount 应收代收款
	 * @param receiveablePayAmoout 应收到付款
	 * @param twoInOneWaybillDto 子母件通用接口
	 * @author fangwenjun 237982
	 * @date 2015年9月14日 上午8:57:10 
	 */
	@SuppressWarnings("unused")
	private void settleUpRepayment(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, BigDecimal receiveableAmount, 
			BigDecimal receiveablePayAmoout, TwoInOneWaybillDto twoInOneWaybillDto, WaybillDto waybilldto) {
		// 生成付款编号
		StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		//拼接付款编号
		dateStr = dateStr.append(repaymentEntity.getWaybillNo());
		// 付款方式为现金时，暂不调用结算接口，暂存到付款表中，30分钟后job再调用实收货款接口
		if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH).equals(repaymentEntity.getPaymentType())) {	
			//付款编号
			repaymentEntity.setRepaymentNo(dateStr.toString());
			//付款时间
			repaymentEntity.setPaymentTime(new Date());
			//操作人
			repaymentEntity.setOperator(FossUserContextHelper.getUserName());
			//操作人编码
			repaymentEntity.setOperatorCode(FossUserContextHelper.getUserCode());
			//操作部门
			repaymentEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
			//操作部门编码
			repaymentEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
			//币种
			repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//当实付运费和代收货款同时为0时 设置为财务单据无需生成
			if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
				/**
				 * 开单网上支付时  结清货款界面查询的运费和代收货款都同时=0  然后用现金方式结清的时候进入这里进行分流
				 * FOSS还是走原来的逻辑    如果是CUBC 则调用一次结清接口对单子进行校验  看其始发应收流水是否核销
				 * 本次修复PCUBC-6135
				 * @date 2017年2月23日 20:15:45
				 */
				//------CUBC结清校验开单网上支付服务开始--------add by 378375
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add(repaymentEntity.getWaybillNo());
				//灰度分流  --update by 243921
				String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					// 更新ActualFreight表中的结清状态为已结清
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					//运单号
					actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
					//结清状态-已结清
					actualFreightEntity.setSettleStatus(FossConstants.YES);
					//结款日期
					actualFreightEntity.setSettleTime(new Date());
					//收货人
					actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
					//证件类型
					actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
					//证件号
					actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
					if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
						//证件类型(代收人)
						actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
						//证件号码（代收）
						actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
					} else {
						//证件类型(代收人)
						actualFreightEntity.setCodIdentifyType("");
						//证件号码（代收）
						actualFreightEntity.setCodIdentifyCode("");
					}
					//更新actualFreight表
					actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					// 无需结清 将付款信息置0
					repaymentEntity.setActualFreight(BigDecimal.ZERO);
					// 无需结清 将付款信息置0
					repaymentEntity.setCodAmount(BigDecimal.ZERO);
					// 设置财务单据无需生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					PaymentSettlementDto dto = new PaymentSettlementDto();
					// 运单号
					dto.setWaybillNo(repaymentEntity.getWaybillNo());
					// 付款类型
					dto.setPaymentType(repaymentEntity.getPaymentType());
					// 部门code
					dto.setDestOrgCode(FossUserContextHelper.getOrgCode());
					// 部门名称
					dto.setDestOrgName(FossUserContextHelper.getOrgName());
					// 客户code
					dto.setCustomerCode(repaymentEntity.getConsigneeCode());
					// 客户名称
					dto.setCustomerName(repaymentEntity.getConsigneeName());
					// 时间
					dto.setBusinessDate(new Date());
					// 实收代收货款费用
					dto.setCodFee(repaymentEntity.getCodAmount());
					// 实收到付运费
					dto.setToPayFee(repaymentEntity.getActualFreight());
					FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
					fossDto.setDto(dto);
					fossDto.setCurrentInfo(currentInfo);
					CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMessage())){
							LOGGER.info("调用CUBC结清校验开单网上支付接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
					}
					if((StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES))){
						// 更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						//运单号
						actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
						//结清状态-已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						//结款日期
						actualFreightEntity.setSettleTime(new Date());
						//收货人
						actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
						//证件类型
						actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
						//证件号
						actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
						if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
							//证件类型(代收人)
							actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
							//证件号码（代收）
							actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
						} else {
							//证件类型(代收人)
							actualFreightEntity.setCodIdentifyType("");
							//证件号码（代收）
							actualFreightEntity.setCodIdentifyCode("");
						}
						//更新actualFreight表
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
						// 无需结清 将付款信息置0
						repaymentEntity.setActualFreight(BigDecimal.ZERO);
						// 无需结清 将付款信息置0
						repaymentEntity.setCodAmount(BigDecimal.ZERO);
						// 设置财务单据无需生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
					}
				}
				//------CUBC结清校验开单网上支付服务结束--------add by 378375
			} else {
				// 更新ActualFreight表中的提货人、身份证
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
				//收货人
				actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
				//证件类型
				actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
				//证件号
				actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
				if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
					//证件号码（代收人）
					actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
				} else {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType("");
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode("");
				}
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				// 判断是否是子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
						//设置财务单据未生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
					} else { // 为子母件的子件
						// 设置财务单据无需生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
					}
				} else { // 不是子母件
					//设置财务单据未生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
				}
			}
			// 生成付款信息
			repaymentDao.addPaymentInfo(repaymentEntity);
			// 生成到达联信息
			ArriveSheetEntity entity = new ArriveSheetEntity();
			//运单号
			entity.setWaybillNo(repaymentEntity.getWaybillNo());
			//收货人
			entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
			//证件类型
			entity.setIdentifyType(repaymentEntity.getIdentifyType());
			//证件号
			entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
			// 调用到达联接口
			arriveSheetManngerService.checkGenerateArriveSheet(entity);
			//更新到达联,通过运单号
			arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(), repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(), repaymentEntity.getIdentifyCode());
		} else {
			// 结算传入
			PaymentSettlementDto dto = new PaymentSettlementDto();
			// 如果付款方式为临欠或月结时 调用结算接口-到付运费结转临欠月结
			if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(repaymentEntity.getPaymentType()) || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(repaymentEntity.getPaymentType())) {
				// 运单号
				dto.setWaybillNo(repaymentEntity.getWaybillNo());
				// 付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				// 部门cide
				dto.setDestOrgCode(FossUserContextHelper.getOrgCode());
				// 部门名称
				dto.setDestOrgName(FossUserContextHelper.getOrgName());
				// 客户code
				dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				// 客户名称
				dto.setCustomerName(repaymentEntity.getConsigneeName());
				// 时间
				dto.setBusinessDate(new Date());
				// 实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				// 实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				
				// 判断是否是子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
						//------CUBC灰度结清服务开始1--------add by 378375
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(dto.getWaybillNo());
                        //灰度分流  --update by 243921
                        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
							paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
							LOGGER.info("调用结算接口结束");//记录日志					
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							//验证用户选择的付款方式是否为临欠月结
						//	validatePaymentType(dto);
							FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
							fossDto.setDto(dto);
							fossDto.setCurrentInfo(currentInfo);
							CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMessage())){
									LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
									throw new CubcSettleException(resultDto1.getMessage());
								}
							}
						}
						//------CUBC灰度结清服务结束--------add by 378375
					} 
				} else { // 不是子母件
					//------CUBC灰度结清服务开始2--------add by 378375
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(dto.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
						paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
						LOGGER.info("调用结算接口结束");//记录日志					
						// 更新ActualFreight表中的结清状态为已结清
						updateActualFreightEntity(repaymentEntity);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//验证用户选择的付款方式是否为临欠月结
					//	validatePaymentType(dto);
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(dto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMessage())){
								LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
								throw new CubcSettleException(resultDto1.getMessage());
							}
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
							// 更新ActualFreight表中的结清状态为已结清
							updateActualFreightEntity(repaymentEntity);
						}
					}
					//------CUBC灰度结清服务结束--------add by 378375
				}
				//余额结清时。。。 add by 353654 
			}else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__YUE.equals(repaymentEntity.getPaymentType())){
				// 运单号
				dto.setWaybillNo(repaymentEntity.getWaybillNo());
				// 付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				//付款编号
				dto.setSourceBillNo(dateStr.toString());
				// 部门cide
				dto.setDestOrgCode(FossUserContextHelper.getOrgCode());
				// 部门名称
				dto.setDestOrgName(FossUserContextHelper.getOrgName());
				// 客户code
				dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				// 客户名称
				dto.setCustomerName(repaymentEntity.getConsigneeName());
				// 时间
				dto.setBusinessDate(new Date());
				// 实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				// 实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				// 判断是否是子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
						//------CUBC灰度结清服务开始3--------add by 378375
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(dto.getWaybillNo());
                        //灰度分流  --update by 243921
                        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
							paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
							LOGGER.info("调用结算接口结束");//记录日志					
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							//客户余额小于代收货款和到付运费之和的时候 提醒余额不足。
							BigDecimal amount = repaymentEntity.getBalanceAmount();
							//代收货款+到付运费
							BigDecimal total = repaymentEntity.getCodAmount().add(repaymentEntity.getActualFreight());
							if(amount.compareTo(total)<0){
								LOGGER.info("客户余额不足，请选择其他方式结清！");
								throw new CubcSettleException("客户余额不足，请选择其他方式结清！");
							}
							FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
							fossDto.setDto(dto);
							fossDto.setCurrentInfo(currentInfo);
							CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMessage())){
									LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
									throw new CubcSettleException(resultDto1.getMessage());
								}
							}
						}
						//------CUBC灰度结清服务结束--------add by 378375
					} 
				} else { // 不是子母件
					//------CUBC灰度结清服务开始4--------add by 378375
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(dto.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
						paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
						LOGGER.info("调用结算接口结束");//记录日志					
						// 更新ActualFreight表中的结清状态为已结清
						updateActualFreightEntity(repaymentEntity);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//客户余额小于代收货款和到付运费之和的时候 提醒余额不足。
						BigDecimal amount = repaymentEntity.getBalanceAmount();
						//代收货款+到付运费
						BigDecimal total = repaymentEntity.getCodAmount().add(repaymentEntity.getActualFreight());
						if(amount.compareTo(total)<0){
							LOGGER.info("客户余额不足，请选择其他方式结清！");
							throw new CubcSettleException("客户余额不足，请选择其他方式结清！");
						}
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(dto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMessage())){
								LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
								throw new CubcSettleException(resultDto1.getMessage());
							}
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
							// 更新ActualFreight表中的结清状态为已结清
							updateActualFreightEntity(repaymentEntity);
						}
					}
					//------CUBC灰度结清服务结束--------add by 378375
				}
			} 
			// 付款方式为其他方式时,调用结算接口-实收货款
			else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(repaymentEntity.getPaymentType())
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(repaymentEntity.getPaymentType())
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentEntity.getPaymentType())) {
				//付款方式为网上支付时候   add  by  309603  yangqiang
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentEntity.getPaymentType())
						&& StringUtil.isBlank(repaymentEntity.getClaimNo())) {
					if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO)!=0 || repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO)!=0)
					throw new RepaymentException("付款方式为网上支付时候，款项确认编号不能为空。");
				}
				// 付款方式是‘银行卡’时，款项确认编号必须输入数字。
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
						&& (StringUtil.isBlank(repaymentEntity.getClaimNo())
						|| !repaymentEntity.getClaimNo().matches("[0-9]+"))) {
					throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
				}
				/**
				 * 银行卡检验
				 * @author yangqiang 309603
				 * @date   2016-2-23
				 */
				PosCardEntity    posCardEntity    = null;	//POS实体
				BigDecimal amount = null;					//POS未使用金额
				BigDecimal codAmount = null;				// 实收代收货款费用
				BigDecimal actualFreight = null;			// 实收到实付运费
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
						&& (!StringUtil.isBlank(repaymentEntity.getClaimNo())
						&& repaymentEntity.getClaimNo().matches("[0-9]+"))
						) {
					//查询T+0报表获取未使用金额
					//准备查询数据
					PosCardManageDto posCardManageDto = new PosCardManageDto();
					posCardManageDto.setTradeSerialNo(repaymentEntity.getClaimNo());
					posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
					//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
					//查询T+0报表数据
					//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
                    List<PosCardEntity> posCardEntitys = null;
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(repaymentEntity.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
        			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
        				posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
        				//是否存在
        				if (posCardEntitys==null||posCardEntitys.isEmpty()) {
        					throw new RepaymentException("查询POS机刷卡数据为空，请核实！");
        				}else{
        					posCardEntity = posCardEntitys.get(0);
        				}
        				//判断所属模块，结清货款，
        				if(SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())){
        					throw new RepaymentException("该交易流水号仅能做预收或做小票！");
        				}
        				//获取未使用金额
        				amount =posCardEntity.getUnUsedAmount();
        				
        				//比较
        				// 实收代收货款费用
        				codAmount = repaymentEntity.getCodAmount();
        				// 实收到实付运费
        				actualFreight = repaymentEntity.getActualFreight();
        				
        				if (amount.compareTo(codAmount.add(actualFreight))<0) {//可用金额小于实收代收货款+实收到付运费
        					
        					throw new RepaymentException("可用余额不足。");
        				}
        			}
        			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
        				//TODO  银行卡校验  不管...
        			}
        			//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
				}
				// 款项认领编号
				dto.setPaymentNo(repaymentEntity.getClaimNo());
				// 运单号
				dto.setWaybillNo(repaymentEntity.getWaybillNo());
				// 付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				// 部门cide
				dto.setDestOrgCode(FossUserContextHelper.getOrgCode());
				// 部门名称
				dto.setDestOrgName(FossUserContextHelper.getOrgName());
				// 客户code
				dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				// 客户名称
				dto.setCustomerName(repaymentEntity.getConsigneeName());
				// 时间
				dto.setBusinessDate(new Date());
				// 付款编号
				dto.setSourceBillNo(dateStr.toString());
				// 实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				// 实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				
				// 当实付运费和代收货款同时为0时 更改为已结清
				if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
						&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
					// 更新ActualFreight表中的结清状态为已结清
					updateActualFreightEntity(repaymentEntity);
				} else {
					// 判断是否是子母件
					if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
						validaRepayment(repaymentEntity, currentInfo,
								twoInOneWaybillDto, dto);
						if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
							//------CUBC灰度结清服务开始5--------add by 378375
							String message = null;
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(repaymentEntity.getWaybillNo());
                            //灰度分流  --update by 243921
                            String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
								LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
								message = paymentSettlementService.confirmToPayment(dto, currentInfo);
								LOGGER.info("调用结算接口结束");//记录日志	
								
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
								FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
								fossDto.setDto(dto);
								fossDto.setCurrentInfo(currentInfo);
								CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMessage())){
										LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
										throw new CubcSettleException(resultDto1.getMessage());
									}
								}
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
									message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
								}
							}
							//------CUBC灰度结清服务结束--------add by 378375
							if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE.equals(message)) {
								// 更新ActualFreight表中的结清状态为已结清
								updateActualFreightEntity(repaymentEntity);
							}
						} else {
							// 更新ActualFreight表中的结清状态为已结清
							updateActualFreightEntity(repaymentEntity);
						}
					} else { // 不是子母件					
						//------CUBC灰度结清服务开始6--------add by 378375
						String message = null;
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(repaymentEntity.getWaybillNo());
                        //灰度分流  --update by 243921
                        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
							message = paymentSettlementService.confirmToPayment(dto, currentInfo);
							LOGGER.info("调用结算接口结束");//记录日志					
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
							fossDto.setDto(dto);
							fossDto.setCurrentInfo(currentInfo);
							CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMessage())){
									LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
									throw new CubcSettleException(resultDto1.getMessage());
								}
							}
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
								message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
							}
						}
						//------CUBC灰度结清服务结束--------add by 378375
						if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE.equals(message)) {
							// 更新ActualFreight表中的结清状态为已结清
							updateActualFreightEntity(repaymentEntity);
						}
					}
				}
				/**
				 *更新T+0数据 调用更新数据
				 * @author yangqiang 309603
				 * @date   2016-2-23
				 */
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
						&& (!StringUtil.isBlank(repaymentEntity.getClaimNo())
						&& repaymentEntity.getClaimNo().matches("[0-9]+"))
						){
					
					//更新POS刷卡信息  灰度改造  353654---start
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(repaymentEntity.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode1 = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
		            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
		            	
		            	//更新POS刷卡信息
						//准备数据
						BigDecimal payAmount = codAmount.add(actualFreight);										//使用总金额
						posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
						posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
						posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));					//已使用金额
						posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(payAmount)); 								//未使用金额
		            	
		            	pdaPosManageService.updatePosCardMessageAmount(posCardEntity);//调用接口插入数据
		            	//插入新的POS刷卡明细
						//准备数据
						PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
						posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
						posCardDetailEntity.setInvoiceType("W2");													//运单
						posCardDetailEntity.setInvoiceNo(repaymentEntity.getWaybillNo());							//运单号
						//posCardDetailEntity.setAmount(waybilldto.getToPayAmount());									//发生金额
						BigDecimal totalAmount = repaymentDao.getTotalAmount(repaymentEntity.getWaybillNo());
						posCardDetailEntity.setAmount(totalAmount);									//发生金额
						posCardDetailEntity.setOccupateAmount(payAmount);											//已占用金额
						BigDecimal unVerifyAmount = (receiveableAmount.add(receiveablePayAmoout)).subtract(payAmount);
						posCardDetailEntity.setUnVerifyAmount(unVerifyAmount);										//未核销金额
						posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
						posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
						pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
						//根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据，灰度改造   353654 ---------------------------end
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
						//CUBC TODO
					}
				}
			}
			//付款编号
			repaymentEntity.setRepaymentNo(dateStr.toString());
			//付款时间
			repaymentEntity.setPaymentTime(new Date());
			//操作人
			repaymentEntity.setOperator(FossUserContextHelper.getUserName());
			//操作人编码
			repaymentEntity.setOperatorCode(FossUserContextHelper.getUserCode());
			//操作部门
			repaymentEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
			//操作部门编码
			repaymentEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
			//当实付运费和代收货款同时为0时 更改为无需生成
			if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
				// 无需结清 将付款信息置0
				repaymentEntity.setActualFreight(BigDecimal.ZERO);
				// 无需结清 将付款信息置0
				repaymentEntity.setCodAmount(BigDecimal.ZERO);
				// 财务单据无需生成
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			} else {
				// 判断是否是子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
						// 财务单据已生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
					} else { // 为子母件的子件
						// 设置财务单据无需生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
					}
				} else { // 不是子母件
					// 财务单据已生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
				}
			}
			// 币种
			repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// job id
			repaymentEntity.setJobId(UUIDUtils.getUUID());
			// 生成付款信息
			repaymentDao.addPaymentInfo(repaymentEntity);
			// 生成到达联信息
			ArriveSheetEntity entity = new ArriveSheetEntity();
			// 运单号
			entity.setWaybillNo(repaymentEntity.getWaybillNo());
			// 收货人
			entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
			// 证件类型
			entity.setIdentifyType(repaymentEntity.getIdentifyType());
			// 证件号
			entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
			// 调用到达联接口
			arriveSheetManngerService.checkGenerateArriveSheet(entity);
			// 更新到达联,通过运单号
			arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(), repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(), repaymentEntity.getIdentifyCode());
		}
		
		
		// 判断是否是子母件 modify by 353654
		if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
			if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
					//如果有小票就调用 ---调用结算接口添加小票号信息 加判断  如何判断有小票 TODO
					if(repaymentEntity.getStorageFee().compareTo(BigDecimal.ZERO)>0 && repaymentEntity.getStorageFee() != null){//保管费大于0
						this.repaymentService.addOtherRevenueInfo(repaymentEntity,waybilldto,currentInfo);						
					}
				}
		} else { //TODO 353654
				//如果有小票就调用 ---调用结算接口添加小票号信息  加判断  如何判断有小票
				if(repaymentEntity.getStorageFee().compareTo(BigDecimal.ZERO)>0 && repaymentEntity.getStorageFee() != null){//保管费大于0
					this.repaymentService.addOtherRevenueInfo(repaymentEntity,waybilldto,currentInfo);						
				}
		}
	}

	private void validaRepayment(RepaymentEntity repaymentEntity,
			CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto,
			PaymentSettlementDto dto) {
		if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
			//------CUBC灰度结清服务开始7--------add by 353654
			String message = null;
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(repaymentEntity.getWaybillNo());
            //灰度分流  --update by 243921
            String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
				message = paymentSettlementService.confirmToPayment(dto, currentInfo);
				LOGGER.info("调用结算接口结束");//记录日志			
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
				fossDto.setDto(dto);
				fossDto.setCurrentInfo(currentInfo);
				CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
				if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
					if(StringUtils.isNotBlank(resultDto1.getMessage())){
						LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
						throw new CubcSettleException(resultDto1.getMessage());
					}
				}
				if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
					message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
				}
			}
			//------CUBC灰度结清服务结束--------add by 353654
			if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE.equals(message)) {
				// 更新ActualFreight表中的结清状态为已结清
				updateActualFreightEntity(repaymentEntity);
			}
		} else {
			// 更新ActualFreight表中的结清状态为已结清
			updateActualFreightEntity(repaymentEntity);
		}
	}
	
	/** 
	 * 更新实际承运表的结清状态
	 * @author fangwenjun 237982
	 * @date 2015年9月15日 下午4:31:07 
	 */
	private void updateActualFreightEntity (RepaymentEntity repaymentEntity) {
		// 更新ActualFreight表中的结清状态为已结清
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
		// 结清状态-已结清
		actualFreightEntity.setSettleStatus(FossConstants.YES);
		// 结款日期
		actualFreightEntity.setSettleTime(new Date());
		// 收货人
		actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
		// 证件类型
		actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
		// 证件号
		actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
		if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
		} else {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType("");
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode("");
		}
		// 更新actualFreight表
		actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
	}
	
	/** 
	 * 结清货款前验证 
	 * @param repaymentEntity 付款 对象
	 * @param currentInfo 当前信息对象
	 * @param waybillEntity 运单
	 * @author fangwenjun 237982
	 * @date 2015年9月14日 下午2:33:29 
	 */
	private void verification(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, WaybillEntity waybillEntity) {
		//合伙人结清、整车与数据初始化4.10----start-------------239284------------------------------------------------
		if (null != waybillEntity) {
			//如果不是整车，判断当然运单是否是合伙人运单； 如果是整车，则走原有的结清流程
			if (!FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
						//到达部门
						SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(waybillEntity.getLastLoadOrgCode());
						if (null != saleDept) {
								//如果达到部门是合伙人部门
								if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
									//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
									String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
									if (StringUtils.isNotBlank(configString)) {
									    	try {
									    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
												long intTime = sdf.parse(configString.trim()).getTime();
												long destTime = waybillEntity.getBillTime().getTime();
												//如果开单日期大于初始化日期，则走合伙人结清流程
												if (destTime >= intTime) {
													throw new RepaymentException("请走合伙人签收!");
								 				} 
											} catch (ParseException e) {
												throw new RepaymentException("合伙人结清初始化日期开关解析错误！");
											}
									}
								}
						} else {
							throw new RepaymentException("合伙人运单，结清到达部门信息不存在 ！");
						}
			}
		} else {
			throw new RepaymentException(repaymentEntity.getWaybillNo() + "运单信息未找到!");
		}
		//合伙人结清、整车与数据初始化4.10----end-------------239284---------------------------------
		
		// 校验未受理的更改单
		if (waybillRfcService.isExsitsWayBillRfc(repaymentEntity.getWaybillNo())) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.EXIST_WAYBILLRFC);
		}
					
		// 判断是否 已结清
		if (isPayment(repaymentEntity.getWaybillNo()))  {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.PAYMENT_FINISH);
		}
		
		// 校验更改单或者更改单申请未处理
		if (waybillEntity != null && WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {					
			List<String> waybillRfcList=new ArrayList<String>();
			waybillRfcList.add(repaymentEntity.getWaybillNo());
			List<String> notWaybillRfc=waybillRfcService.queryUnActiveRfcWaybillNo(waybillRfcList);	//调用接口				
			if(CollectionUtils.isNotEmpty(notWaybillRfc) && notWaybillRfc.size()>0){
				// 抛出业务异常
				throw new RepaymentException("有更改单申请未处理，请受理后再次操作！");
			}
		}
					
		// 判断如果当前运单是整车
		if (waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
			boolean arrival = FossConstants.YES.equals(waybillEntity.getIsPassOwnDepartment());
			if (!arrival) {
				//如果当前部门==最终配载部门
				if (currentInfo.getCurrentDeptCode().equals(waybillEntity.getLastLoadOrgCode())) {
					arrival=true;
				}
			}
			//update by foss 231434 2015-6-2 
			//对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做校验
			Date date = DateUtils.convert(dateStr, format);
			if (date.before(waybillEntity.getBillTime())) {
				// 校验运单是否配载是否到达
				String message = arrivalService.validateWaybillNo(repaymentEntity.getWaybillNo(), arrival);
				if(message != null){
					throw new RepaymentException(message);
				}
			}
		} else {
			// 判断当前部门是否是最终到达部门
			if(!currentInfo.getCurrentDeptCode().equals(waybillEntity.getLastLoadOrgCode())){
				throw new RepaymentException("当前部门与最终到达部门不一致，不能结清货款！");
			}
		}
        //开单网上支付但是尚未支付的单据， H-D 合伙人运单添加 是否生成财务单据的校验 灰度改造   353654  update by 243921--------------start
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(repaymentEntity.getWaybillNo());
        List<String> notPayWaybillNo = null;
		//灰度分流
		String vestSystemCode = GrayForCUBCUtil.getGray(waybillNos, grayByWaybillNoUrl);
        //FOSS
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
            // 判断是否网上支付  //ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(waybillEntity.getPaidMethod())) {
                // 开单网上支付但是尚未支付的单据
                notPayWaybillNo = takingService.unpaidOnlinePay(waybillNos);
                if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
                    throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
                }
            }
            /**
             * 243921
             * H-D 合伙人运单添加 是否生成财务单据的校验
             */
            if(isPartnerDept(waybillEntity.getReceiveOrgCode())){
            	//如果是合伙人整车不加该校验  DN201703230017
    			if (!FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
    				//出发部门是合伙人部门--校验CR 和 PFCR
                    BillReceivableConditionDto recDto = new BillReceivableConditionDto(repaymentEntity.getWaybillNo());
                    recDto.setBillTypes(new String[]{
                            SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
                            SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
                    });
                    recDto.setActive(FossConstants.ACTIVE);
                    //代收货款应收单
                    List<BillReceivableEntity> recList =
                            billReceivableService.queryBillReceivableByCondition(recDto);
                    //校验开单有到付总金额 但未生成单据
                    if(waybillEntity.getToPayAmount().compareTo(BigDecimal.ZERO) > 0 && CollectionUtils.isEmpty(recList)){
                        throw new SettlementException("结清失败，该运单无有效的代收货款应收单或者到付运费应收单，请于十分钟后进行结清操作!");
                    }

                    if(CollectionUtils.isNotEmpty(recList) && recList.size() == 1){ //只有一条有效的单据
                        BillReceivableEntity receivableEntity = recList.get(0);
                        //有效的单据为代收货款应收单
                        if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(receivableEntity.getBillType())){
                            //判断开单总金额-代收金额 大于 0 则应生成到付运费应收单
                            if(waybillEntity.getToPayAmount().subtract(waybillEntity.getCodAmount()).compareTo(BigDecimal.ZERO) > 0){
                                throw new SettlementException("结清失败，该运单开单有到付金额，但无有效的到付运费应收单，请于十分钟后进行结清操作!");
                            }
                        }
                        //有效单据为到付运费应收单
                        if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(receivableEntity.getBillType())){
                            //校验开单有代收货款，但未生成代收货款应收单
                            if(waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
                                throw new SettlementException("结清失败，该运单开单有代收货款,但无有效的代收货款应收单，请于十分钟后进行结清操作!");
                            }
                        }
                    }
    			}                
            }
        }
        //CUBC
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
            // 判断是否网上支付  //ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(waybillEntity.getPaidMethod())) {
                //调用CUBC查询物流交易单  应收信息校验  353654
                FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
                requestDto1.setWaybillNos(waybillNos);
                FossSearchTradeResponseDO responseDto1 = null;
                try {
                    responseDto1 = (FossSearchTradeResponseDO) HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
                } catch (Exception e) {
                    LOGGER.error("调用CUBC接口出现异常,异常信息为：" + e);
                    throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
                }
                if (responseDto1 != null) {
                    if (StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())) {
                        LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
                        throw new SettlementException(responseDto1.getMsg());
                    }
                    Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
                    List<TradeDO> tradeslist = dataMap.get(repaymentEntity.getWaybillNo());
                    if (CollectionUtils.isNotEmpty(tradeslist)) {
                        for (TradeDO tradeDO : tradeslist) {
                            if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
                                    equals(tradeDO.getOrderSubType()) &&
                                    SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
                                            equals(tradeDO.getOrderSubType())
                                    && BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount()) < 0) {
                                //添加数据
                                notPayWaybillNo.add(repaymentEntity.getWaybillNo());
                                if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
                                    throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
                                }
                            }
                        }
                    }
                }
            }
            /**
             * 243921   调用CUBC查询物流交易单  应收信息校验
             * H-D 合伙人运单添加 是否生成财务单据的校验
             */
            if(isPartnerDept(waybillEntity.getReceiveOrgCode())) {
            	//如果是合伙人整车不加该校验  DN201703230017
    			if (!FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
    				//出发部门是合伙人部门--校验CR 和 PFCR
                    List<String> billTypes = new ArrayList<String>();
                    billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
                    billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
                    //调用CUBC查询物流交易单  应收信息校验  353654
                    FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
                    requestDto1.setWaybillNos(waybillNos); //运单号
                    requestDto1.setOrderSubType(billTypes);//单据类型
                    FossSearchTradeResponseDO responseDto1 = null;
                    try {
                        responseDto1 = (FossSearchTradeResponseDO) HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
                    } catch (Exception e) {
                        LOGGER.error("调用CUBC接口出现异常,异常信息为：" + e);
                        throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
                    }
                    if (responseDto1 != null) {
                        if (StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())) {
                            LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
                            throw new SettlementException(responseDto1.getMsg());
                        }
                        Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
                        List<TradeDO> tradeslist = dataMap.get(repaymentEntity.getWaybillNo());
                        //校验开单有到付总金额 但未生成单据
                        if(waybillEntity.getToPayAmount().compareTo(BigDecimal.ZERO) > 0 && CollectionUtils.isEmpty(tradeslist)){
                            throw new SettlementException("结清调用CUBC查询物流交易单接口失败，该运单无有效的代收货款应收流水或者到付运费应收流水，请于十分钟后进行结清操作!");
                        }
                        if (CollectionUtils.isNotEmpty(tradeslist) && tradeslist.size() == 1) {//只有一条有效的单据
                            TradeDO tradeDO = tradeslist.get(0);
                            //有效的单据为代收货款应收单
                            if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(tradeDO.getOrderSubType())){
                                //判断开单总金额-代收金额 大于 0 则应生成到付运费应收单
                                if(waybillEntity.getToPayAmount().subtract(waybillEntity.getCodAmount()).compareTo(BigDecimal.ZERO) > 0){
                                    throw new SettlementException("结清调用CUBC查询物流交易单接口失败，该运单开单有到付金额，但无有效的到付运费应收流水，请于十分钟后进行结清操作!");
                                }
                            }
                            //有效单据为到付运费应收单
                            if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(tradeDO.getOrderSubType())){
                                //校验开单有代收货款，但未生成代收货款应收单
                                if(waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
                                    throw new SettlementException("结清调用CUBC查询物流交易单接口失败，该运单开单有代收货款,但无有效的代收货款应收流水，请于十分钟后进行结清操作!");
                                }
                            }
                        }
                    }
    			}                
            }
		}
        //开单网上支付但是尚未支付的单据，H-D 合伙人运单添加 是否生成财务单据的校验 灰度改造   353654 update by 243921---------------------end
	}
	
	
	/**
	 * 校验操作人密码.
	 *
	 * @param repaymentEntity the repayment entity
	 * waybillNo
	 * 运单号
	 * repaymentNo
	 * 付款编号
	 * active
	 * 是否有效
	 * consigneeCode
	 * 客户编码
	 * consigneeName
	 * 客户名称
	 * paymentType
	 * 付款方式
	 * claimNo
	 * 款项认领编号
	 * actualFreight
	 * 实付运费
	 * codAmount
	 * 代收货款
	 * paymentTime
	 * 付款时间
	 * modifyTime
	 * 最后更新时间
	 * storageFee
	 * 仓储费
	 * operator
	 * 操作人
	 * operatorCode
	 * 操作人编码
	 * operateOrgName
	 * 操作部门
	 * operateOrgCode
	 * 操作部门编码
	 * currencyCode
	 * 币种
	 * isRfcing
	 * 是否审批中
	 * stlbillGeneratedStatus
	 * 是否已有财务单据
	 * jobId
	 * job id
	 * deliverymanName
	 * 提货人姓名
	 * identifyType
	 * 证件类型
	 * identifyCode
	 * 证件号码
	 * passWord
	 * 密码
	 * timeRange
	 * 后台job时间间隔
	 * firStlbillGeneratedStatus
	 * 财务单据初始状态
	 * defJobId
	 * 默认job id
	 * @param currentInfo the current info
	 * userName
	 * 用户名
	 * empCode
	 * 员工工号
	 * empName
	 * 员工姓名
	 * currentDeptCode
	 * 当前登录部门编码
	 * currentDeptName
	 * 当前登录部门名称
	 * user
	 * 当前登录用户
	 * dept
	 * 当前部门
	 * @throws RepaymentException the repayment exception
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-11-26
	 * 下午3:01:23
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#addPaymentInfo
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	@Transactional
	public void validatePaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,WaybillDto waybillDto) throws RepaymentException{
		// 如果repaymentEntity、currentInfo不为空
		if (repaymentEntity != null && currentInfo != null){
            if(StringUtils.isBlank(repaymentEntity.getPaymentType())){
                throw new RepaymentException(RepaymentException.PAYMENTTYPE_ISBLANK);
            }
			//用户名
			String userName = currentInfo.getUser().getUserName();
			//密码
			String passWord = repaymentEntity.getPassWord();
			// 校验当前登录密码
			boolean flag = loginService.validateUser(userName, passWord);
			// 校验登录情况，如果通过
			if (flag){
				// 新增付款信息
				addPaymentInfo(repaymentEntity, currentInfo,waybillDto);
				
			} else{
				// 抛出业务异常
				throw new RepaymentException(RepaymentException.PASSWORD_ERROR);
			}
		}
	}

	/**
	 * 退款操作.
	 *
	 * @param waybillNo 
	 * 		the waybill no
	 * @return true, 
	 * 		if successful
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午2:30:42
	 */
	@Override
	public boolean refundPaymentInfo(String waybillNo){
		// 如果运单号不为空
		if (StringUtils.isNotEmpty(waybillNo)){
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("waybillNo", waybillNo);
			params.put("active", FossConstants.YES);
			// 根据运单号、订单号判定是否子母件
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			//判断是否子母件		
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				// 获取子母件运单号集合
				List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
				// 记录退款操作成功运单总数
				int refundPaymentCount = 0;
				if (waybillNoList != null && waybillNoList.size() > 0) {
					// 创建付款对象 存储修改内容
					RepaymentEntity repaymentEntity = new RepaymentEntity();
					// 遍历反结清子件
					for (int i = 0, len = waybillNoList.size(); i < len; i++) {
						//运单号
						repaymentEntity.setWaybillNo(waybillNoList.get(i));
						// 置为失效状态
						repaymentEntity.setActive(FossConstants.NO);
						// 设置财务单据无需生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
						// 判断退款操作是否成功
						if (repaymentDao.refundPaymentInfo(repaymentEntity)) {
							refundPaymentCount ++;
						}
					}
					//运单号
					repaymentEntity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
					// 置为失效状态
					repaymentEntity.setActive(FossConstants.NO);
					// 修改为未生成财务单据
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
					// 判断退款操作是否成功
					if (repaymentDao.refundPaymentInfo(repaymentEntity)) {
						refundPaymentCount ++;
					}
					
					// 判断子母件是否全部已退款操作
					if ((waybillNoList.size() + 1) == refundPaymentCount) {
						return true;
					}
				}
				
			} else { // 不是子母件	
				RepaymentEntity repaymentEntity = new RepaymentEntity();
				//运单号
				repaymentEntity.setWaybillNo(waybillNo);
				// 置为失效状态
				repaymentEntity.setActive(FossConstants.NO);
				// 修改为未生成财务单据
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
				//返回退款状态
				return repaymentDao.refundPaymentInfo(repaymentEntity);
			}
		}
		//返回错误
		return false;
	}

	/**
	 * 是否结清货款.
	 *
	 * @param waybillNo 
	 * 		the waybill no
	 * @return true, 
	 * 		if is payment
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午7:22:40
	 */
	@Override
	public boolean isPayment(String waybillNo){
		// 如果运单号不为空
		if (StringUtils.isNotEmpty(waybillNo)){
			// 查看ActualFreight表中是否已经结清
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			if (actualFreightEntity != null && FossConstants.YES.equals(actualFreightEntity.getSettleStatus())){
				//返回正常
				return true;
			}
			//返回错误
			return false;
		}
		//返回错误
		return false;
	}

	/**
	 * 签收出库调用.
	 *
	 * @param repaymentEntity the repayment entity
	 * 		waybillNo  
	 * 			运单号
	 * 		repaymentNo  
	 * 			付款编号
	 * 		active	
	 * 			是否有效
	 * 		consigneeCode
	 * 			客户编码
	 * 		consigneeName	
	 * 			客户名称
	 * 		paymentType		
	 * 			付款方式
	 * 		claimNo
	 * 			款项认领编号
	 * 		actualFreight
	 * 			实付运费
	 * 		codAmount	
	 * 			代收货款
	 * 		paymentTime	
	 * 			付款时间
	 * 		modifyTime 	
	 * 			最后更新时间
	 * 		storageFee	
	 * 			仓储费
	 * 		operator	
	 * 			操作人
	 * 		operatorCode	
	 * 			操作人编码
	 * 		operateOrgName	
	 * 			操作部门	
	 * 		operateOrgCode	
	 * 			操作部门编码
	 * 		currencyCode	
	 * 			币种	
	 * 		isRfcing	
	 * 			是否审批中
	 * 		stlbillGeneratedStatus	
	 * 			是否已有财务单据
	 * 		jobId	
	 * 			job id
	 * 		deliverymanName	
	 * 			提货人姓名
	 * 		identifyType	
	 * 			证件类型
	 * 		identifyCode	
	 * 			证件号码
	 * 		passWord	
	 * 			密码
	 * 		timeRange	
	 * 			后台job时间间隔
	 * 		firStlbillGeneratedStatus	
	 * 			财务单据初始状态
	 * 		defJobId	
	 * 			默认job id	
	 * @return true, 
	 * 		if successful
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午7:22:40
	 */
	@Override
	public boolean paymentOperate(RepaymentEntity repaymentEntity){
		// 如果repaymentEntity不为空
		if (repaymentEntity != null){
			// 获取运单号
			String waybillNo = repaymentEntity.getWaybillNo();
			// 根据单号查询实际货物
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			// 判断是否已结清货款
			if (actualFreightEntity != null && FossConstants.YES.equals(actualFreightEntity.getSettleStatus())) {
				return true;
			}
			// 如果该单没有结清货款
			else{
				// 获得未生成财务单据的付款记录
				RepaymentEntity entity = new RepaymentEntity();
				// 运单号
				entity.setWaybillNo(waybillNo);
				// 有效
				entity.setActive(FossConstants.ACTIVE);
				// 财务单据未生成
				entity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
				// 财务单据生成中 
				entity.setFirStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
				List<RepaymentEntity> repaymentEntityList = repaymentDao.queryRepaymentList(entity);
				// 如果该单不存在未付款的财务单据，返回false
				if (CollectionUtils.isEmpty(repaymentEntityList)){
					return false;
				} else{
					for (RepaymentEntity irepaymentEntity : repaymentEntityList){
						//结清方式     243921
						irepaymentEntity.setSettleApproach(repaymentEntity.getSettleApproach());
						//生成uuid
						String uuid = UUIDUtils.getUUID();
						// 占用付款信息jobid
						irepaymentEntity.setJobId(uuid);
						// 先更新当前付款信息jobid
						repaymentService.updateRepayment(irepaymentEntity);
						
						// 调用结算结清货款接口
						String message = operatepaymentSettlement(irepaymentEntity);
						if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)){
							//返回
							return true;
						}
					}
					//返回错误
					return false;
				}
			}
		}
		//返回错误
		return false;
	}

	/**
	 * 调用结算服务进行结清货款.
	 *
	 * @param irepaymentEntity the irepayment entity
	 * 		waybillNo  
	 * 			运单号
	 * 		repaymentNo  
	 * 			付款编号
	 * 		active	
	 * 			是否有效
	 * 		consigneeCode
	 * 			客户编码
	 * 		consigneeName	
	 * 			客户名称
	 * 		paymentType		
	 * 			付款方式
	 * 		claimNo
	 * 			款项认领编号
	 * 		actualFreight
	 * 			实付运费
	 * 		codAmount	
	 * 			代收货款
	 * 		paymentTime	
	 * 			付款时间
	 * 		modifyTime 	
	 * 			最后更新时间
	 * 		storageFee	
	 * 			仓储费
	 * 		operator	
	 * 			操作人
	 * 		operatorCode	
	 * 			操作人编码
	 * 		operateOrgName	
	 * 			操作部门	
	 * 		operateOrgCode	
	 * 			操作部门编码
	 * 		currencyCode	
	 * 			币种	
	 * 		isRfcing	
	 * 			是否审批中
	 * 		stlbillGeneratedStatus	
	 * 			是否已有财务单据
	 * 		jobId	
	 * 			job id
	 * 		deliverymanName	
	 * 			提货人姓名
	 * 		identifyType	
	 * 			证件类型
	 * 		identifyCode	
	 * 			证件号码
	 * 		passWord	
	 * 			密码
	 * 		timeRange	
	 * 			后台job时间间隔
	 * 		firStlbillGeneratedStatus	
	 * 			财务单据初始状态
	 * 		defJobId	
	 * 			默认job id	
	 * @return the string
	 * @throws RepaymentException 
	 * 		the repayment exception
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-29 
	 * 		上午10:35:09
	 */
	public String operatepaymentSettlement(RepaymentEntity irepaymentEntity) throws RepaymentException{
		// 如果irepaymentEntity不为空
		if (irepaymentEntity != null){
			//用户
			UserEntity user = new UserEntity();
			//组织
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			//人员
			EmployeeEntity employee = new EmployeeEntity();
			// 设置用户信息
			employee.setEmpName(irepaymentEntity.getOperator());
			//用户code
			employee.setEmpCode(irepaymentEntity.getOperatorCode());
			//用户
			user.setEmployee(employee);
			// 设置部门信息
			dept.setName(irepaymentEntity.getOperateOrgName());
			//部门code
			dept.setCode(irepaymentEntity.getOperateOrgCode());
			CurrentInfo currentInfo = new CurrentInfo(user, dept);
			
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", irepaymentEntity.getWaybillNo());
			params.put("active", FossConstants.YES);
			// 根据运单号、订单号判定是否子母件
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			// 调用结算接口
			return this.repaymentService.paymentSettlement(irepaymentEntity, currentInfo, twoInOneWaybillDto);
		}
		//返回空
		return null;
	}

	/**
	 * 派送处理调用.
	 *
	 * @param repaymentEntity the repayment entity
	 * 		waybillNo  
	 * 			运单号
	 * 		repaymentNo  
	 * 			付款编号
	 * 		active	
	 * 			是否有效
	 * 		consigneeCode
	 * 			客户编码
	 * 		consigneeName	
	 * 			客户名称
	 * 		paymentType		
	 * 			付款方式
	 * 		claimNo
	 * 			款项认领编号
	 * 		actualFreight
	 * 			实付运费
	 * 		codAmount	
	 * 			代收货款
	 * 		paymentTime	
	 * 			付款时间
	 * 		modifyTime 	
	 * 			最后更新时间
	 * 		storageFee	
	 * 			仓储费
	 * 		operator	
	 * 			操作人
	 * 		operatorCode	
	 * 			操作人编码
	 * 		operateOrgName	
	 * 			操作部门	
	 * 		operateOrgCode	
	 * 			操作部门编码
	 * 		currencyCode	
	 * 			币种	
	 * 		isRfcing	
	 * 			是否审批中
	 * 		stlbillGeneratedStatus	
	 * 			是否已有财务单据
	 * 		jobId	
	 * 			job id
	 * 		deliverymanName	
	 * 			提货人姓名
	 * 		identifyType	
	 * 			证件类型
	 * 		identifyCode	
	 * 			证件号码
	 * 		passWord	
	 * 			密码
	 * 		timeRange	
	 * 			后台job时间间隔
	 * 		firStlbillGeneratedStatus	
	 * 			财务单据初始状态
	 * 		defJobId	
	 * 			默认job id	
	 * @param currentInfo the current info
	 * 		userName	
	 * 			用户名
	 * 		empCode 	
	 * 			员工工号
	 * 		empName		
	 * 			员工姓名
	 * 		currentDeptCode	
	 * 			当前登录部门编码
	 * 		currentDeptName	
	 * 			当前登录部门名称
	 * 		user	
	 * 			当前登录用户
	 * 		dept 	
	 * 			当前部门
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-29
	 * 		 下午12:00:52
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#deliverOperate
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void deliverOperate(RepaymentEntity repaymentEntity, CurrentInfo currentInfo){
		// 如果repaymentEntity、currentInfo不为空
		if (repaymentEntity != null && currentInfo != null) {
			// 生成付款编号
			StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			//拼接付款编号
			dateStr = dateStr.append(repaymentEntity.getWaybillNo());
			
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(repaymentEntity.getWaybillNo());
			if (!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType()) 
					&& !WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType())) {
				//抛出异常 参数错误
				throw new RepaymentException(RepaymentException.WAYBILLRFC_ERROR);
			}	
			
			//付款编号
			repaymentEntity.setRepaymentNo(dateStr.toString());
			//操作人
			repaymentEntity.setOperator(currentInfo.getEmpName());
			//操作人编码
			repaymentEntity.setOperatorCode(currentInfo.getEmpCode());
			//操作部门
			repaymentEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
			//操作部门编码
			repaymentEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			// 财务单据生成中
			repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
			//RMB
			repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//UUID
			repaymentEntity.setJobId(UUIDUtils.getUUID());
			
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", repaymentEntity.getWaybillNo());
			params.put("active", FossConstants.YES);
			// 根据运单号、订单号判定是否子母件
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			// 判断是否子母件
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) {
					// 重新设置 是否已有财务单据
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
				} 
			}
			// 生成付款信息
			repaymentDao.addPaymentInfo(repaymentEntity);
			// 调用结算接口
			repaymentService.paymentSettlement(repaymentEntity, currentInfo, twoInOneWaybillDto);
		
		}
	}

	/**
	 * job批量操作调用结算接口.
	 *
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-30 上午10:32:19
	 */
	@Override
	public void batchjobs(){
		String uuid = UUIDUtils.getUUID();
		Date currentTime = new Date();
		// 更新job id
		int count = repaymentService.updateRepaymentForJob(uuid, currentTime);
		if (count > 0){
			// 调用结清货款接口
			repaymentService.operatepaymentSettlementForJob(uuid, currentTime);
		}
	}

	/**
	 * 更新job id.
	 *
	 * @param uuid the uuid
	 * @return the int
	 * the int
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-12-20
	 * 下午6:22:55
	 */
	@Transactional
	public int updateRepaymentForJob(String uuid, Date currentTime){
		RepaymentEntity repaymentEntity = new RepaymentEntity();
		// 先更新当前付款信息jobid
		repaymentEntity.setJobId(uuid);
		// 更新为生成中
		repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
		// 间隔30分
		String confValue = configurationParamsService.queryConfValueByCode("PAYMENT_TIME");
		
		if(confValue!=null)
		{
			//传入异步时间	
			repaymentEntity.setTimeRange(BigDecimalOperationUtil.div(new BigDecimal(confValue), new BigDecimal(JOBTIME)));
		}else
		{
			//传入异步时间	
			repaymentEntity.setTimeRange(BigDecimalOperationUtil.div(new BigDecimal(DEFTIME), new BigDecimal(JOBTIME)));
		}
		
		// 有效
		repaymentEntity.setActive(FossConstants.YES);
		// 初始财务状态
		repaymentEntity.setFirStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
		// 初始JobId
		repaymentEntity.setDefJobId(JOBID);
		// 设置当前时间
		repaymentEntity.setCurrentTime(currentTime);
		//更新job id
		return repaymentDao.updateRepaymentListForJob(repaymentEntity);
	}

	/**
	 * job 调用结清货款接口.
	 *
	 * @param uuid 
	 * 		the uuid
	 * @throws RepaymentException 
	 * 		the repayment exception
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-20
	 * 		 下午6:50:50
	 */
	public void operatepaymentSettlementForJob(String uuid, Date currentTime) throws RepaymentException{
		//付款信息
		RepaymentEntity repaymentEntity1 = new RepaymentEntity();
		//财务单据生成中
		repaymentEntity1.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
		//有效
		repaymentEntity1.setActive(FossConstants.YES);
		//job id
		repaymentEntity1.setJobId(uuid);
		// 设置当前时间
		repaymentEntity1.setCurrentTime(currentTime);
		// 间隔30分
		String confValue = configurationParamsService.queryConfValueByCode("PAYMENT_TIME");
		if(confValue!=null)
		{
			//传入异步时间	
			repaymentEntity1.setTimeRange(BigDecimalOperationUtil.div(new BigDecimal(confValue), new BigDecimal(JOBTIME)));
		}else
		{
			//传入异步时间	
			repaymentEntity1.setTimeRange(BigDecimalOperationUtil.div(new BigDecimal(DEFTIME), new BigDecimal(JOBTIME)));
		}
		List<RepaymentEntity> repaymentEntityList = repaymentDao.queryRepaymentListForJob(repaymentEntity1);

		for (RepaymentEntity irepaymentEntity : repaymentEntityList){
			try{
				//调用结算服务进行结清货款
				operatepaymentSettlement(irepaymentEntity);
			} catch (RepaymentException e){
				// 异常处理
				LOGGER.error("error", e);
				// 若有异常，则将jobid初始化“N/A”
				irepaymentEntity.setJobId(JOBID);
				repaymentService.updateRepayment(irepaymentEntity);
				repaymentService.addJobException(irepaymentEntity.getId(), e);
			}
		}
	}

	/**
	 * 
	 * 添加成功日志
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-7 下午5:47:12
	 */
	@Override
	public void addJobSuccess(String repaymentid) 
	{
		//记录成功日志
		JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
		//id
		successLogEntity.setId(UUIDUtils.getUUID());
		//JOB 名称
		successLogEntity.setJobName(JOB_NAME);
		//成功id
		successLogEntity.setSuccessId(repaymentid);
		//日志消息
		successLogEntity.setSuccessMsg("付款成功");
		//日志时间
		successLogEntity.setCreateDate(new Date());
		//添加成功日志表
		shareJobDao.addJobSuccessLogEntity(successLogEntity);
	}

	/**
	 * 
	 * 添加异常日志
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-7 下午5:47:30
	 */
	@Transactional
	@Override
	public void addJobException(String repaymentid, RepaymentException e)
	{
		// 添加至异常日志表
		JobExceptionLogEntity exceptionLogEntity = new JobExceptionLogEntity();
		//id
		exceptionLogEntity.setId(UUIDUtils.getUUID());
		//JOB 名称
		exceptionLogEntity.setJobName(JOB_NAME);
		//异常id
		exceptionLogEntity.setErrorId(repaymentid);
		//异常消息
		exceptionLogEntity.setErrorCode(e.getMessage());
		//异常时间
		exceptionLogEntity.setCreateDate(new Date());
		StringBuffer sb = new StringBuffer();
		// 堆栈信息
		for(StackTraceElement element : e.getStackTrace()){
			if(element.toString().indexOf("com.deppon") != -1){
				sb.append(element.toString());
				sb.append(",");
			}
		}
		//异常信息
		exceptionLogEntity.setErrorMsg(sb.substring(0, sb.length() - 1));
		//添加至异常日志表
		shareJobDao.addJobExceptionLogEntity(exceptionLogEntity);
	}
	
	/**
	 * 调用结算接口.
	 *
	 * @param repaymentEntity the repayment entity
	 * waybillNo
	 * 运单号
	 * repaymentNo
	 * 付款编号
	 * active
	 * 是否有效
	 * consigneeCode
	 * 客户编码
	 * consigneeName
	 * 客户名称
	 * paymentType
	 * 付款方式
	 * claimNo
	 * 款项认领编号
	 * actualFreight
	 * 实付运费
	 * codAmount
	 * 代收货款
	 * paymentTime
	 * 付款时间
	 * modifyTime
	 * 最后更新时间
	 * storageFee
	 * 仓储费
	 * operator
	 * 操作人
	 * operatorCode
	 * 操作人编码
	 * operateOrgName
	 * 操作部门
	 * operateOrgCode
	 * 操作部门编码
	 * currencyCode
	 * 币种
	 * isRfcing
	 * 是否审批中
	 * stlbillGeneratedStatus
	 * 是否已有财务单据
	 * jobId
	 * job id
	 * deliverymanName
	 * 提货人姓名
	 * identifyType
	 * 证件类型
	 * identifyCode
	 * 证件号码
	 * passWord
	 * 密码
	 * timeRange
	 * 后台job时间间隔
	 * firStlbillGeneratedStatus
	 * 财务单据初始状态
	 * defJobId
	 * 默认job id
	 * @param currentInfo the current info
	 * userName
	 * 用户名
	 * empCode
	 * 员工工号
	 * empName
	 * 员工姓名
	 * currentDeptCode
	 * 当前登录部门编码
	 * currentDeptName
	 * 当前登录部门名称
	 * user
	 * 当前登录用户
	 * dept
	 * 当前部门
	 * @return the string
	 * @throws RepaymentException the repayment exception
	 * the string
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-12-3
	 * 下午4:09:51
	 */
	@Transactional
	public String paymentSettlement(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto) throws RepaymentException{
		try{
			PaymentSettlementDto dto = new PaymentSettlementDto();
			String paymentType = null;
			// 得到付款方式
			if (repaymentEntity != null){
				//付款方式
				paymentType = repaymentEntity.getPaymentType();
			} else{
				//抛出异常 参数错误
				throw new RepaymentException(RepaymentException.ARGS_ERROR);
			}
			
			// 子母件母件或不是子母件标识
			boolean isMainWaybillNo = true;
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				
				if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) {
					isMainWaybillNo = false;
				} 
			}
			
			//得到结算应收单信息
			FinancialDto financialDto = queryFinanceSign(repaymentEntity.getWaybillNo());
			//应收代收款
			BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
            //已收代收款
            BigDecimal receivedAmount= financialDto.getReceivedAmount()==null ? BigDecimal.ZERO : financialDto.getReceivedAmount();
			//应收到付款
			BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
            //已收到付款
            BigDecimal receivedPayAmount = financialDto.getReceivedPayAmount() ==null ? BigDecimal.ZERO : financialDto.getReceivedPayAmount();

			// 如果付款方式为临欠或月结时 调用结算接口-到付运费转临欠月结
			if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType) || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)){
				//运单号
				dto.setWaybillNo(repaymentEntity.getWaybillNo());
				//结清方式     243921
				dto.setSettleApproach(repaymentEntity.getSettleApproach());
				//付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				//部门code
				dto.setDestOrgCode(repaymentEntity.getOperateOrgCode());
				//部门名称
				dto.setDestOrgName(repaymentEntity.getOperateOrgName());
				if(repaymentEntity.getConsigneeCode()!= null){
					//客户code
					dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				}
				if(repaymentEntity.getConsigneeName()!= null){
					//客户名称
					dto.setCustomerName(repaymentEntity.getConsigneeName());
				}
				//时间
				dto.setBusinessDate(repaymentEntity.getPaymentTime());
				//付款编号
				dto.setSourceBillNo(repaymentEntity.getRepaymentNo());
				//款项认领编号
				dto.setPaymentNo(repaymentEntity.getClaimNo());
				//实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				//实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				//币种
				dto.setCurrencyCode(repaymentEntity.getCurrencyCode());
				
				//-------快递新增属性
				//串号
				dto.setPosSerialNum(repaymentEntity.getPdaSerial());
				//银行交易流水号
				dto.setBatchNo(repaymentEntity.getBankTradeSerail());
				
				// 判断是否是子母件的母件
				if (isMainWaybillNo) { // 为母件或者不是子母件
					//------CUBC灰度结清服务开始8--------add by 378375
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(dto.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
						paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
						LOGGER.info("调用结算接口结束");//记录日志
						//修改日期
						repaymentEntity.setModifyDate(new Date());
						//更新付款
						repaymentDao.updateRepayment(repaymentEntity);
						// 更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						//运单号
						actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
						//已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						//结清时间
						actualFreightEntity.setSettleTime(new Date());
						//更新actualFreight表
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
						repaymentService.addJobSuccess(repaymentEntity.getId());
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//验证用户选择的付款方式是否为临欠月结
					//	validatePaymentType(dto);
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(dto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMessage())){
								LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
								throw new CubcSettleException(resultDto1.getMessage());
							}
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
							// 更新ActualFreight表中的结清状态为已结清
							ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
							//运单号
							actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
							//已结清
							actualFreightEntity.setSettleStatus(FossConstants.YES);
							//结清时间
							actualFreightEntity.setSettleTime(new Date());
							//更新actualFreight表
							actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
						}
					}
					//------CUBC灰度结清服务结束--------add by 378375
					//财务单据已生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
				} else { // 为子件无需调用结算接口和财务单据
					//财务单据无需生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
				}
				return SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
			}
			// 付款方式为其他方式时,调用结算接口-实收货款
			else{
				//运单号
				dto.setWaybillNo(repaymentEntity.getWaybillNo());
				//结清方式     243921
				dto.setSettleApproach(repaymentEntity.getSettleApproach());
				//付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				//部门cide
				dto.setDestOrgCode(repaymentEntity.getOperateOrgCode());
				//部门名称
				dto.setDestOrgName(repaymentEntity.getOperateOrgName());
				//客户code
				dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				//客户名称
				dto.setCustomerName(repaymentEntity.getConsigneeName());
				//时间
				dto.setBusinessDate(repaymentEntity.getPaymentTime());
				//付款编号
				dto.setSourceBillNo(repaymentEntity.getRepaymentNo());
				//款项认领编号
				dto.setPaymentNo(repaymentEntity.getClaimNo());
				//实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				//实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				
				//-------快递新增属性
				//串号
				dto.setPosSerialNum(repaymentEntity.getPdaSerial());
				//银行交易流水号
				dto.setBatchNo(repaymentEntity.getBankTradeSerail());
				
				//解决由于JOB并发导致现金结清付款表为0情况----start
				//应收到付款
				BigDecimal actualFreight = repaymentEntity.getActualFreight() ==null ? BigDecimal.ZERO : repaymentEntity.getActualFreight();
	            //已收到付款
	            BigDecimal codAmount = repaymentEntity.getCodAmount() ==null ? BigDecimal.ZERO : repaymentEntity.getCodAmount();

                if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(paymentType)
                        && BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
                        && BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)
                        && (receivedAmount.compareTo(BigDecimal.ZERO)>0
                        || receivedPayAmount.compareTo(BigDecimal.ZERO)>0)
                        && (actualFreight.compareTo(BigDecimal.ZERO)>0
                                || codAmount.compareTo(BigDecimal.ZERO)>0)) {
                	if(this.isPayment(repaymentEntity.getWaybillNo())){
                		return null;
                	}
                }
                //解决由于JOB并发导致现金结清付款表为0情况----end

				//当实付运费和代收货款同时为0时 更改为无需生成
				if(BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)){
					//无需结清 将付款信息置0
					repaymentEntity.setActualFreight(BigDecimal.ZERO);
					//无需结清 将付款信息置0
					repaymentEntity.setCodAmount(BigDecimal.ZERO);
					// 财务单据无需生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
				}
				else{
					// 判断是否是子母件的母件
					if (isMainWaybillNo) { // 为母件或者不是子母件
						// 财务单据已生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
					} else { // 为子件
						// 财务单据无需生成
						repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
					}
				}
				//更改时间
				repaymentEntity.setModifyDate(new Date());
				repaymentDao.updateRepayment(repaymentEntity);
				//当实付运费和代收货款同时为0时 更改为已结清
				if(BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)){
					// 更新ActualFreight表中的结清状态为已结清
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					//运单号
					actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
					//已结清
					actualFreightEntity.setSettleStatus(FossConstants.YES);
					//结清时间
					actualFreightEntity.setSettleTime(new Date());
					//更新actualFreight表
					actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				}
				else{				
					//------CUBC灰度结清服务开始9--------add by 378375
					String message = null;
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(repaymentEntity.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
						message = paymentSettlementService.confirmToPayment(dto, currentInfo);
						LOGGER.info("调用结算接口结束");//记录日志					
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(dto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){						
							LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
							message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
						}
					}
					//------CUBC灰度结清服务结束--------add by 378375
					if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)) {
						// 更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						// 运单号
						actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
						// 已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						// 结清时间
						actualFreightEntity.setSettleTime(new Date());
						// 更新actualFreight表
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					}
					
					repaymentService.addJobSuccess(repaymentEntity.getId());
					return message;
				}
				//返回空
				return null;
			}
		} catch (SettlementException se){
			// 异常处理
			LOGGER.error("error", se);
			throw new RepaymentException(se.getErrorCode(), se);
		}
	}

	/**
	 * 签收变更接口调用反结清.
	 *
	 * @param oldRepayment the old repayment
	 * waybillNo
	 * 运单号
	 * repaymentNo
	 * 付款编号
	 * active
	 * 是否有效
	 * consigneeCode
	 * 客户编码
	 * consigneeName
	 * 客户名称
	 * paymentType
	 * 付款方式
	 * claimNo
	 * 款项认领编号
	 * actualFreight
	 * 实付运费
	 * codAmount
	 * 代收货款
	 * paymentTime
	 * 付款时间
	 * modifyTime
	 * 最后更新时间
	 * storageFee
	 * 仓储费
	 * operator
	 * 操作人
	 * operatorCode
	 * 操作人编码
	 * operateOrgName
	 * 操作部门
	 * operateOrgCode
	 * 操作部门编码
	 * currencyCode
	 * 币种
	 * isRfcing
	 * 是否审批中
	 * stlbillGeneratedStatus
	 * 是否已有财务单据
	 * jobId
	 * job id
	 * deliverymanName
	 * 提货人姓名
	 * identifyType
	 * 证件类型
	 * identifyCode
	 * 证件号码
	 * passWord
	 * 密码
	 * timeRange
	 * 后台job时间间隔
	 * firStlbillGeneratedStatus
	 * 财务单据初始状态
	 * defJobId
	 * 默认job id
	 * @param newRepayment the new repayment
	 * @param currentInfo the current info
	 * userName
	 * 用户名
	 * empCode
	 * 员工工号
	 * empName
	 * 员工姓名
	 * currentDeptCode
	 * 当前登录部门编码
	 * currentDeptName
	 * 当前登录部门名称
	 * user
	 * 当前登录用户
	 * dept
	 * 当前部门
	 * @return the string
	 * the string
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-12-19
	 * 下午3:03:33
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#changeRepayment
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public String changeRepayment(RepaymentEntity oldRepayment, RepaymentEntity newRepayment, CurrentInfo currentInfo) {
		try {
			if (oldRepayment != null && newRepayment != null && currentInfo != null) {
				// 子母件母件或不是子母件标识
				boolean isMainWaybillNo = true;
				// 创建Map集合存放查询条件
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("waybillNo", oldRepayment.getWaybillNo());
				params.put("active", FossConstants.YES);
				// 根据运单号、订单号判定是否子母件
				TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
				// 判断是否子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					isMainWaybillNo = validaWaybillNo(oldRepayment,
							isMainWaybillNo, twoInOneWaybillDto);
				}
				Date newDate = new Date();
				// 获取原始付款方式
				String oldPaymentType = oldRepayment.getPaymentType();
				// 无效
				oldRepayment.setActive(FossConstants.INACTIVE);
				// 修改时间
				oldRepayment.setModifyTime(newDate);
				// 更新原始付款信息为无效
				repaymentDao.updateRepayment(oldRepayment);

				// old代收货款
				BigDecimal receiveableAmount = oldRepayment.getCodAmount();
				// old实付运费
				BigDecimal receiveablePayAmoout = oldRepayment.getActualFreight();

				// new代收货款
				BigDecimal newReceiveableAmount = newRepayment.getCodAmount();
				// new实付运费
				BigDecimal newReceiveablePayAmoout = newRepayment.getActualFreight();

				// 新增付款信息
				newRepayment.setPaymentTime(newDate);
				String id = repaymentDao.addPaymentInfo(newRepayment);
				// 获取新付款方式
				String newPaymentType = newRepayment.getPaymentType();

				// 设置老的结算Dto
				PaymentSettlementDto dto = new PaymentSettlementDto();
				// 运单号
				dto.setWaybillNo(oldRepayment.getWaybillNo());
				// 付款类型
				dto.setPaymentType(oldRepayment.getPaymentType());
				// 部门code
				dto.setDestOrgCode(oldRepayment.getOperateOrgCode());
				// 部门名称
				dto.setDestOrgName(oldRepayment.getOperateOrgName());
				// 客户code
				dto.setCustomerCode(oldRepayment.getConsigneeCode());
				// 客户名称
				dto.setCustomerName(oldRepayment.getConsigneeName());
				// 时间
				dto.setBusinessDate(oldRepayment.getPaymentTime());
				// 实收代收货款费用
				dto.setCodFee(oldRepayment.getCodAmount());
				// 实收到付运费
				dto.setToPayFee(oldRepayment.getActualFreight());
				// 币种
				dto.setCurrencyCode(oldRepayment.getCurrencyCode());
				// 付款编号
				dto.setSourceBillNo(oldRepayment.getRepaymentNo());
				// 款项认领编号
				dto.setPaymentNo(oldRepayment.getClaimNo());

				// 设置新的结算Dto
				PaymentSettlementDto dto1 = new PaymentSettlementDto();
				// 运单号
				dto1.setWaybillNo(newRepayment.getWaybillNo());
				// 付款类型
				dto1.setPaymentType(newRepayment.getPaymentType());
				// 部门code
				dto1.setDestOrgCode(newRepayment.getOperateOrgCode());
				// 部门名称
				dto1.setDestOrgName(newRepayment.getOperateOrgName());
				// 客户code
				dto1.setCustomerCode(newRepayment.getConsigneeCode());
				// 客户名称
				dto1.setCustomerName(newRepayment.getConsigneeName());
				// 时间
				dto1.setBusinessDate(newRepayment.getPaymentTime());
				// 实收代收货款费用
				dto1.setCodFee(newRepayment.getCodAmount());
				// 实收到付运费
				dto1.setToPayFee(newRepayment.getActualFreight());
				// 币种
				dto1.setCurrencyCode(newRepayment.getCurrencyCode());
				// 付款编号
				dto1.setSourceBillNo(newRepayment.getRepaymentNo());
				// 款项认领编号
				dto1.setPaymentNo(newRepayment.getClaimNo());

				/* 校验统一结算 */
				valideUnifiedSettlement(dto1);

				// 如果原始付款方式为临欠或月结时 调用结算接口-反到付运费结转临欠/月结
				if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(oldPaymentType)
						|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(oldPaymentType)) {
					if (isMainWaybillNo) {
											
						//------CUBC灰度反结清服务开始10--------add by 378375
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(oldRepayment.getWaybillNo());
                        //灰度分流  --update by 243921
                        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							paymentSettlementService.reversConfirmToBillReceiveable(dto, currentInfo);	
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
							fossDto.setDto(dto);
							fossDto.setCurrentInfo(currentInfo);
							CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMessage())){
									LOGGER.info("调用CUBC反结清接口异常信息如下:"+resultDto1.getMessage());
									throw new CubcSettleException(resultDto1.getMessage());
								}
							}
						}
						//------CUBC灰度反结清服务结束--------add by 378375
					}
				} else {
					// 当实收货款和到付运费不都为0时
					if (!BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
							|| !BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
						if (isMainWaybillNo) {						
							//------CUBC灰度反结清服务开始11--------add by 378375
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(dto.getWaybillNo());
                            //灰度分流  --update by 243921
                            String vestSystemCode1 = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
								LOGGER.info("调用结算接口开始"+"运单号："+oldRepayment.getWaybillNo());//记录日志
								paymentSettlementService.reversConfirmPayment(dto, currentInfo);
								LOGGER.info("调用结算接口结束");//记录日志					
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
								FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
								fossDto.setDto(dto);
								fossDto.setCurrentInfo(currentInfo);
								CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMessage())){
										LOGGER.info("调用CUBC反结清接口异常信息如下:"+resultDto1.getMessage());
										throw new CubcSettleException(resultDto1.getMessage());
									}
								}
							}
							//------CUBC灰度反结清服务结束--------add by 378375
						}
					}
				}

				// 如果新付款方式为临欠或月结时 调用结算接口-到付运费转临欠月结
				if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(newPaymentType)
						|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(newPaymentType)) {
					if (isMainWaybillNo) {
						//------CUBC灰度结清服务开始12--------add by 378375
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(dto1.getWaybillNo());
                        //灰度分流  --update by 243921
                        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							LOGGER.info("调用结算接口开始"+"运单号："+oldRepayment.getWaybillNo());//记录日志
							paymentSettlementService.confirmToBillReceivable(dto1, currentInfo);
							LOGGER.info("调用结算接口结束");//记录日志					
							// 更新结清状态
							updateSettleStatus(newRepayment, FossConstants.YES);
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							//验证用户选择的付款方式是否为临欠月结
						//	validatePaymentType(dto);
							FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
							fossDto.setDto(dto1);
							fossDto.setCurrentInfo(currentInfo);
							CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMessage())){
									LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
									throw new CubcSettleException(resultDto1.getMessage());
								}
							}
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
								// 更新ActualFreight表中的结清状态为已结清
								updateSettleStatus(newRepayment, FossConstants.YES);
							}
						}
						//------CUBC灰度结清服务结束--------add by 378375
						
					}
				} else {
					// 当实收货款和到付运费不都为0时
					if (!BigDecimalOperationUtil.compare(newReceiveableAmount, BigDecimal.ZERO)
							|| !BigDecimalOperationUtil.compare(newReceiveablePayAmoout, BigDecimal.ZERO)) {
						if (isMainWaybillNo) {
							//------CUBC灰度结清服务开始13--------add by 378375
							String message = null;
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(dto.getWaybillNo());
                            //灰度分流  --update by 243921
                            String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
								LOGGER.info("调用结算接口开始" + "运单号：" + newRepayment.getWaybillNo());// 记录日志
								message = paymentSettlementService.confirmToPayment(dto1, currentInfo);
								LOGGER.info("调用结算接口结束");//记录日志					
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
								FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
								fossDto.setDto(dto1);
								fossDto.setCurrentInfo(currentInfo);
								CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMessage())){
										LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
										throw new CubcSettleException(resultDto1.getMessage());
									}
								}
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
									message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
								}
							}
							//------CUBC灰度结清服务结束--------add by 378375
							if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)) {
								// 更新结清状态
								updateSettleStatus(newRepayment, FossConstants.YES);
							} else {
								// 更新结清状态
								updateSettleStatus(newRepayment, FossConstants.NO);
							}
						} else {
							// 更新结清状态
							updateSettleStatus(newRepayment, FossConstants.YES);
						}
					} else {
						// 更新结清状态
						updateSettleStatus(newRepayment, FossConstants.YES);
					}
				}
				return id;
			} else {
				// 抛出异常 参数错误
				throw new RepaymentException(RepaymentException.ARGS_ERROR);
			}
		} catch (SettlementException se) {
			// 抛出异常
			throw new RepaymentException(se.getErrorCode(), se);
		}
	}

	/**
	 * 反签收调用反结清.
	 *
	 * @param list the list
	 * waybillNo
	 * 运单号
	 * repaymentNo
	 * 付款编号
	 * active
	 * 是否有效
	 * consigneeCode
	 * 客户编码
	 * consigneeName
	 * 客户名称
	 * paymentType
	 * 付款方式
	 * claimNo
	 * 款项认领编号
	 * actualFreight
	 * 实付运费
	 * codAmount
	 * 代收货款
	 * paymentTime
	 * 付款时间
	 * modifyTime
	 * 最后更新时间
	 * storageFee
	 * 仓储费
	 * operator
	 * 操作人
	 * operatorCode
	 * 操作人编码
	 * operateOrgName
	 * 操作部门
	 * operateOrgCode
	 * 操作部门编码
	 * currencyCode
	 * 币种
	 * isRfcing
	 * 是否审批中
	 * stlbillGeneratedStatus
	 * 是否已有财务单据
	 * jobId
	 * job id
	 * deliverymanName
	 * 提货人姓名
	 * identifyType
	 * 证件类型
	 * identifyCode
	 * 证件号码
	 * passWord
	 * 密码
	 * timeRange
	 * 后台job时间间隔
	 * firStlbillGeneratedStatus
	 * 财务单据初始状态
	 * defJobId
	 * 默认job id
	 * @param currentInfo the current info
	 * userName
	 * 用户名
	 * empCode
	 * 员工工号
	 * empName
	 * 员工姓名
	 * currentDeptCode
	 * 当前登录部门编码
	 * currentDeptName
	 * 当前登录部门名称
	 * user
	 * 当前登录用户
	 * dept
	 * 当前部门
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2012-12-19
	 * 下午3:03:51
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#reverseRepayment
	 * (java.util.List,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void reverseRepayment(List<RepaymentEntity> list, CurrentInfo currentInfo){
		try{
			if (CollectionUtils.isNotEmpty(list) && currentInfo != null){
				for (RepaymentEntity repaymentEntity : list) {
					// 子母件母件或不是子母件标识
					boolean isMainWaybillNo = true;
					// 创建Map集合存放查询条件
					Map<String, Object> params = new HashMap<String,Object>();
					params.put("waybillNo", repaymentEntity.getWaybillNo());
					params.put("active", FossConstants.YES);
					// 根据运单号、订单号判定是否子母件
					TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
					//判断是否子母件		
					if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
						// 判断是否母件
						isMainWaybillNo = validaWaybillNo(repaymentEntity,
								isMainWaybillNo, twoInOneWaybillDto);
					} 
					// 设置付款信息为无效
					repaymentEntity.setActive(FossConstants.INACTIVE);
					// 更新付款信息
					repaymentDao.updateRepayment(repaymentEntity);
					// 代收货款
					BigDecimal receiveableAmount = repaymentEntity.getCodAmount();
					// 实付运费
					BigDecimal receiveablePayAmoout = repaymentEntity.getActualFreight();

					// 获取原始付款方式
					String paymentType = repaymentEntity.getPaymentType();
					// 封装结算结清货款dto
					PaymentSettlementDto dto = new PaymentSettlementDto();
					// 运单号
					dto.setWaybillNo(repaymentEntity.getWaybillNo());
					// 付款类型
					dto.setPaymentType(repaymentEntity.getPaymentType());
					// 部门code
					dto.setDestOrgCode(repaymentEntity.getOperateOrgCode());
					// 部门名称
					dto.setDestOrgName(repaymentEntity.getOperateOrgName());
					// 客户code
					dto.setCustomerCode(repaymentEntity.getConsigneeCode());
					// 客户名称
					dto.setCustomerName(repaymentEntity.getConsigneeName());
					// 时间
					dto.setBusinessDate(repaymentEntity.getPaymentTime());
					// 实收代收货款费用
					dto.setCodFee(repaymentEntity.getCodAmount());
					// 实收到付运费
					dto.setToPayFee(repaymentEntity.getActualFreight());
					// 币种
					dto.setCurrencyCode(repaymentEntity.getCurrencyCode());
					// 付款编号
					dto.setSourceBillNo(repaymentEntity.getRepaymentNo());
					// 如果原始付款方式为临欠或月结时 调用结算接口-反到付运费结转临欠/月结
					if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType)
							|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)) {
						if (isMainWaybillNo) {
							//------CUBC灰度反结清服务开始14--------add by 378375
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(dto.getWaybillNo());
                            //灰度分流  --update by 243921
                            String vestSystemCode1 = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
								paymentSettlementService.reversConfirmToBillReceiveable(dto, currentInfo);
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
								FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
								fossDto.setDto(dto);
								fossDto.setCurrentInfo(currentInfo);
								CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMessage())){
										LOGGER.info("调用CUBC反结清接口异常信息如下:"+resultDto1.getMessage());
										throw new CubcSettleException(resultDto1.getMessage());
									}
								}
							}
							//------CUBC灰度反结清服务结束--------add by 378375
						}
						// 更新结清状态为未结清
						updateSettleStatus(repaymentEntity, FossConstants.NO);
					} else {
						// 当实收货款和到付运费不都为0时
						if (!BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
								|| !BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
							if (isMainWaybillNo) {//如果运单(不是子母件运单) 或者 (是子母件中的母件)							
								//------CUBC灰度反结清服务开始15--------add by 378375
                                ArrayList<String> arrayList = new ArrayList<String>();
                                arrayList.add(repaymentEntity.getWaybillNo());
                                //灰度分流  --update by 243921
                                String vestSystemCode1 = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
								if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
									LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
									paymentSettlementService.reversConfirmPayment(dto, currentInfo);
									LOGGER.info("调用结算接口结束");//记录日志					
								}
								if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
									FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
									fossDto.setDto(dto);
									fossDto.setCurrentInfo(currentInfo);
									CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
									if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
										if(StringUtils.isNotBlank(resultDto1.getMessage())){
											LOGGER.info("调用CUBC反结清接口异常信息如下:"+resultDto1.getMessage());
											throw new CubcSettleException(resultDto1.getMessage());
										}
									}
								}
								//------CUBC灰度反结清服务结束--------add by 378375
								// 更新结清状态为未结清 --避免出现异常同步接口操作问题。
								updateSettleStatus(repaymentEntity, FossConstants.NO);
								
								//--------------合伙人零担-反结清----start----239284-------------------------------------------------------------------------------
								/*WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(repaymentEntity.getWaybillNo());
								if (null == wayBillEntity) {
									throw new RepaymentException("运单信息不存在！");
								}
								//到达部门
								SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
								if (null != saleDept) {
										//如果达到部门是合伙人部门
										if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
											//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
											String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
											if (StringUtils.isNotBlank(configString)) {
											    	try {
											    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
														long intTime = sdf.parse(configString.trim()).getTime();
														long destTime = wayBillEntity.getBillTime().getTime();
														//如果开单日期大于初始化日期，则走合伙人结清流程
														if (destTime >= intTime) {
															LOGGER.info("合伙人零担-调用反结清接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
															//如果反结清代收货款金额不为0， 则释放代收货款金额，并修改状态。
															if (BigDecimal.ZERO.compareTo(receiveableAmount) < 0) {
																LOGGER.info("合伙人零担-调用反结清接口参数=== 金额" + receiveableAmount + ", dto:" + ReflectionToStringBuilder.toString(dto));// 记录日志
																reversPtpCOD(dto, currentInfo, receiveableAmount);
																
																//更新代收货款应收单扣款状态为未扣款
																BillReceivableEntity entity = billReceivableService.selectByWayBillNoAndBillType(repaymentEntity.getWaybillNo(), SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
																if (null != entity) {
																	entity.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);
																	billReceivableService.updateBillReceivableWithholdStatus(entity);
																}
															}
															LOGGER.info("合伙人零担-调用反结清接口结束");
														} 
													} catch (ParseException e) {
														throw new RepaymentException("合伙人结清初始化日期开关解析错误！");
													}
											} else { //如果配置参数为null，则走合伙人反扣款接口
												//如果反结清代收货款金额不为0， 则释放代收货款金额，并修改状态。
												if (BigDecimal.ZERO.compareTo(receiveableAmount) < 0) {
													LOGGER.info("合伙人零担-调用反结清接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
													//如果反结清代收货款金额不为0， 则释放代收货款金额，并修改状态。
													if (!BigDecimal.ZERO.equals(receiveableAmount)) {
														LOGGER.info("合伙人零担-调用反结清接口参数=== 金额" + receiveableAmount + ", dto:" + ReflectionToStringBuilder.toString(dto));// 记录日志
														reversPtpCOD(dto, currentInfo, receiveableAmount);
														
														//更新代收货款应收单扣款状态为未扣款
														BillReceivableEntity entity = billReceivableService.selectByWayBillNoAndBillType(repaymentEntity.getWaybillNo(), SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
														if (null != entity) {
															entity.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);
															billReceivableService.updateBillReceivableWithholdStatus(entity);
														}
													}
													LOGGER.info("合伙人零担-调用反结清接口结束");
												}
											}
											
											//合伙人反签收审核同意，作废ptp流水.
											PartnerUpdateTakeEffectTimeRequest request = new PartnerUpdateTakeEffectTimeRequest();
											request.setSourceBillNo(repaymentEntity.getWaybillNo());
											request.setTakeEffectTime(new Date());
											request.setOperatorCode(currentInfo.getEmpCode());
											request.setOperatorName(currentInfo.getEmpName());
											request.setFloweffectivestatus(FossConstants.NO);  //这里使用是否作废的标识
											boolean isSuccess = ptpSignService.validBillSaleFlowByAsynESB(request);
											if(!isSuccess) {
												throw new SignException("合伙人反签收审核更改PTP流水状态发送ESB失败!");
											}
											
										}
								} else {
									throw new RepaymentException("合伙人运单，结清到达部门信息不存在 ！");
								}*/
								//--------------合伙人零担-反结清----end----239284-------------------------------------------------------------------------------
								
								// 记录日志
								LOGGER.info("调用结算接口结束");
							}
						}else{ //为0 ，0 的结清时不掉结算接口，将实际承运表结清状态更新为未结清
							// 更新结清状态为未结清
							updateSettleStatus(repaymentEntity, FossConstants.NO);
						}
					}
				}
			} else{
				throw new RepaymentException(RepaymentException.PAYMENT_ERROR);
			}
		} catch (SettlementException se){
			throw new RepaymentException(se.getErrorCode(), se);
		}
	}

	private boolean validaWaybillNo(RepaymentEntity repaymentEntity,
			boolean isMainWaybillNo, TwoInOneWaybillDto twoInOneWaybillDto) {
		if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为母件
			// 创建付款对象用作查询条件
			RepaymentEntity repayment = new RepaymentEntity();
			// 设置付款信息有效
			repayment.setActive(FossConstants.ACTIVE);
			// 获得子母件单号集合
			List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
			List<RepaymentArriveDto> repaymentArriveList = null;
			// 遍历子件单号集合
			for (int i = 0, len = waybillNoList.size(); i < len; i++) {
				// 设置单号
				repayment.setWaybillNo(waybillNoList.get(i));
				// 根据 付款对象 查询有效付款信息 如果查询出结果 说明此单号没有反结清
				repaymentArriveList = repaymentDao.queryRepaymentListbyNo(repayment);
				// 判断付款信息集合是否为空
				if (CollectionUtils.isNotEmpty(repaymentArriveList)) {
					throw new RepaymentException("子件没有全部反结清货款，请先把子件全部反结清，再进行母件反结清。");
				}
			}
		} else { // 为子件
			isMainWaybillNo = false;
		}
		return isMainWaybillNo;
	}

	/**
	 * 更新结清状态.
	 *
	 * @param repayment the repayment
	 * @param state the state
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-19 下午3:39:35
	 */
	public void updateSettleStatus(RepaymentEntity repayment, String state){
		if (repayment != null && StringUtils.isNotEmpty(state)) {
			// 更新ActualFreight表中的结清状态
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(repayment.getWaybillNo());
			//状态
			actualFreightEntity.setSettleStatus(state);
			//时间
			actualFreightEntity.setSettleTime(new Date());
			//更新结清状态
			actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
		}
	}

	/**
	 * 根据运单号查询付款信息.
	 *
	 * @param waybillNo the waybill no
	 * @return the list
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-24 下午2:48:54
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#queryRepaymentList(java.lang.String)
	 */
	@Override
	public List<RepaymentArriveDto> queryArriveFeeByWaybillNo(String waybillNo){
		RepaymentEntity repayment = new RepaymentEntity();
		//财务单据已生成
		repayment.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
		//有效
		repayment.setActive(FossConstants.ACTIVE);
		//运单号
		repayment.setWaybillNo(waybillNo);
		
		return repaymentDao.queryRepaymentListbyNo(repayment);
	}

	
	/**
	 * 返回财务应收信息.
	 *
	 * @param waybillNo the waybill no
	 * @return the financial dto
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-11 下午3:22:00
	 */
	@Override
	public FinancialDto queryFinanceSign(String waybillNo) {
		FinancialDto financialDto = new FinancialDto();
		BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
		//财务单据查询，灰度改造   353654 ---------------------------start 
		List<BillReceivableEntity> billReceivableEntities = null;
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(waybillNo);
        //灰度分流  --update by 243921
        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillNo);
			billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
			LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillNo);
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			try {
				billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillNo);			
			} catch (Exception e) {
				LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
				throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
			}
		}
		//财务单据查询，灰度改造   353654 ---------------------------end
		if(CollectionUtils.isEmpty(billReceivableEntities)){
			LOGGER.info("单号"+waybillNo+";"+vestSystemCode+"财务查询为空");
			// 应收代收款
			financialDto.setReceiveableAmount(BigDecimal.ZERO);
			// 已收代收款
			financialDto.setReceivedAmount(BigDecimal.ZERO);
			// 应收到付款
			financialDto.setReceiveablePayAmoout(BigDecimal.ZERO);
			//已收到付款
			financialDto.setReceivedPayAmount(BigDecimal.ZERO);
			return financialDto;
		}
		for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
			// 到达应收单
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity
						.getVerifyAmount());
			} // 代收货款应收单
			else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收代收款
				financialDto.setReceiveableAmount(billReceivableEntity.getUnverifyAmount());
				// 已收代收款
				financialDto.setReceivedAmount(billReceivableEntity.getVerifyAmount());
			}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
				//合伙人到付运费应收
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
			}
		}
		return financialDto;
	}
	
	/**
	 * 是否存在付款信息.
	 *
	 * @param waybillNo the waybill no
	 * @return true, if is exist repayment
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-28 下午4:11:10
	 */
	@Override
	public boolean isExistRepayment(String waybillNo){
		RepaymentEntity repayment = new RepaymentEntity();
		//运单号
		repayment.setWaybillNo(waybillNo);
		//有效
		repayment.setActive(FossConstants.ACTIVE);
		//返回是否存在
		return repaymentDao.isExistRepayment(repayment);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.addOtherRevenueInfo
	 * @Description:结清货款部分调用结算接口添加小票信息
	 *
	 * @param repaymentEntity
	 * @param currentInfo
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午1:44:55
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-20    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public void addOtherRevenueInfo(RepaymentEntity repaymentEntity, WaybillDto waybillDto,CurrentInfo currentInfo) {
		
		//CUBC小票新增   灰度改造    353654 ---------------------------start
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(repaymentEntity.getWaybillNo());
        //灰度分流  --update by 243921
        String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			OtherRevenueDto otherRevenueDto = new OtherRevenueDto();
			if(StringUtils.isBlank(repaymentEntity.getRevenuePaymentType())){
				throw new RepaymentException("小票单号不为空时，小票付款方式必填！");
			}
			//付款方式为‘银行卡’时，款项确认编号必须输入数字。
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
					&& (StringUtil.isBlank(repaymentEntity.getRevenueClaimNo()) || !repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))){
				throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
			}
			//设置小票收款方式
			otherRevenueDto.setPaymentType(repaymentEntity.getRevenuePaymentType());
			//设置小票汇款编号
			otherRevenueDto.setBatchNo(repaymentEntity.getRevenueClaimNo());
			//设置小票单号
			otherRevenueDto.setOtherRevenueNo(repaymentEntity.getOtherRevenueNo());
			otherRevenueDto.setWaybillNo(repaymentEntity.getWaybillNo());//设置运单号
			//客户编码和客户名称  先从签收信息中取数  如果签收信息中没有则从运单信息中取数 可以为空
			if(StringUtils.isNotBlank(repaymentEntity.getConsigneeCode())&&StringUtils.isNotBlank(repaymentEntity.getConsigneeName())){
				otherRevenueDto.setCustomerCode(repaymentEntity.getConsigneeCode());//设置客户编码
				otherRevenueDto.setCustomerName(repaymentEntity.getConsigneeName());//设置客户名称
			}else{
				otherRevenueDto.setCustomerCode(waybillDto.getReceiveCustomerCode());//设置客户编码
				otherRevenueDto.setCustomerName(waybillDto.getReceiveCustomerName());//设置客户名称
			}
			if(StringUtils.isNotBlank(waybillDto.getReceiveCustomerMobilephone())){
				otherRevenueDto.setCustomerPhone(waybillDto.getReceiveCustomerMobilephone());//设置客户电话
			}else{
				otherRevenueDto.setCustomerPhone(waybillDto.getReceiveCustomerPhone());//设置客户电话
			}
			otherRevenueDto.setInvoiceMark(waybillDto.getInvoice());//设置发票标记
			otherRevenueDto.setAmount(repaymentEntity.getStorageFee());//设置小票金额
			otherRevenueDto.setCustomerType(SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER);//设置客户类型
			otherRevenueDto.setIncomeCategories(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__WAREHOUSE);//设置收入类别 保管费 默认为W
			otherRevenueDto.setInvoiceCategory(SettlementDictionaryConstants.SETTLEMENT_INVOICE_CATEGORY);//设置发票产生方式  结清货款部分产生的小票默认类别为WAYBILL
			/**
			 * 银行卡检验
			 * @author yangqiang 309603
			 * @date 2016-2-23
			 */
			PosCardEntity    posCardEntity    = null;			//POS实体
			BigDecimal 		 amount		      = null; 			//未使用金额
			BigDecimal       paymentAmount 	  = null;			//收款金额
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
				&& (!StringUtil.isBlank(repaymentEntity.getRevenueClaimNo())&& repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))){
				//查询T+0报表获取未使用金额
				//准备查询数据
				PosCardManageDto posCardManageDto = new PosCardManageDto();
				posCardManageDto.setTradeSerialNo(otherRevenueDto.getBatchNo());
				posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
				//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_DEPOSIT);
				//查询T+0报表数据   --此处为FOSS逻辑 不用灰度
				List<PosCardEntity> posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
				//是否存在
				if (posCardEntitys==null||posCardEntitys.isEmpty()) {
					throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确。");
				}else{
					posCardEntity = posCardEntitys.get(0);
				}
				//获取未使用金额
				amount =posCardEntity.getUnUsedAmount();	
				//比较
				//收款金额
				paymentAmount = repaymentEntity.getStorageFee();
					
				if (amount.compareTo(paymentAmount)<0) {//可用金额小于还款金额		
					throw new SettlementException("可用余额不足。");
				}	
			}
			this.otherRevenueService.addOtherRevenueByWayBill(otherRevenueDto, currentInfo);
			/**
			 *更新T+0数据 调用更新数据
			 * @author yangqiang 309603
			 * @date 2016-2-23
			 */
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
					&& (!StringUtil.isBlank(repaymentEntity.getRevenueClaimNo())
					&& repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))
					){
				//更新POS刷卡信息
				//准备数据
				posCardEntity.setModifyUser(currentInfo.getEmpName());											//更新人名称
				posCardEntity.setModifyUserCode(currentInfo.getEmpCode());										//更新人编码
				posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(paymentAmount));					//已使用金额
				posCardEntity.setUnUsedAmount(amount.subtract(paymentAmount));   								//未使用金额
				//根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---start  ---此处不用灰度
				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
				//插入新的POS刷卡明细
				//准备数据
				PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
				posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
				posCardDetailEntity.setInvoiceType("XP");													//小票
				posCardDetailEntity.setInvoiceNo(otherRevenueDto.getOtherRevenueNo());						//小票号
				posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
				posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
				posCardDetailEntity.setAmount(paymentAmount);												//发生金额
				posCardDetailEntity.setOccupateAmount(paymentAmount);										//已占用金额
				posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
				pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
			}
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			//付款方式为‘银行卡’时，款项确认编号必须输入数字。
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
					&& (StringUtil.isBlank(repaymentEntity.getRevenueClaimNo()) || !repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))){
				throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
			}
			CUBCOtherRevenueRequestDto requestDto = new CUBCOtherRevenueRequestDto();
			OtherRevenueDto otherRevenueDto = new OtherRevenueDto();
			//收入类别
			requestDto.setIncomeCategories(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__WAREHOUSE);
			otherRevenueDto.setIncomeCategories(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__WAREHOUSE);
			//运单号
			requestDto.setWaybillNo(repaymentEntity.getWaybillNo());
			otherRevenueDto.setWaybillNo(repaymentEntity.getWaybillNo());
			//业务类型
			if(waybillDto.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)){
				requestDto.setBusinessType(SettlementConstants.BREAKBULK);//零担
			}
			if(waybillDto.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)){
				requestDto.setBusinessType(SettlementConstants.EXPRESS);//快递
			}
			//收款方式
			requestDto.setPaymentType(repaymentEntity.getRevenuePaymentType());
			otherRevenueDto.setPaymentType(repaymentEntity.getRevenuePaymentType());
			if(StringUtils.isNotBlank(repaymentEntity.getRevenueClaimNo())){
				//POS机流水号
				requestDto.setBatchNo(repaymentEntity.getRevenueClaimNo());
				otherRevenueDto.setBatchNo(repaymentEntity.getRevenueClaimNo());
			}
			//金额
			requestDto.setAmount(repaymentEntity.getStorageFee());
			otherRevenueDto.setAmount(repaymentEntity.getStorageFee());
			//客户类型
			requestDto.setCustomerType(SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER);
			otherRevenueDto.setCustomerType(SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER);
			//客户编码,名称,手机电话
			if(StringUtils.isNotBlank(repaymentEntity.getConsigneeCode())&&StringUtils.isNotBlank(repaymentEntity.getConsigneeName())){
				requestDto.setCustomerCode(repaymentEntity.getConsigneeCode());//设置客户编码
				otherRevenueDto.setCustomerCode(repaymentEntity.getConsigneeCode());
				requestDto.setCustomerName(repaymentEntity.getConsigneeName());//设置客户名称
				otherRevenueDto.setCustomerName(repaymentEntity.getConsigneeName());
			}else{
				requestDto.setCustomerCode(waybillDto.getReceiveCustomerCode());//设置客户编码
				otherRevenueDto.setCustomerCode(waybillDto.getReceiveCustomerCode());
				requestDto.setCustomerName(waybillDto.getReceiveCustomerName());//设置客户名称
				otherRevenueDto.setCustomerName(waybillDto.getReceiveCustomerName());
			}
			if(StringUtils.isNotBlank(waybillDto.getReceiveCustomerMobilephone())){
				requestDto.setCustomerPhone(waybillDto.getReceiveCustomerMobilephone());//设置客户电话
				otherRevenueDto.setCustomerPhone(waybillDto.getReceiveCustomerMobilephone());
			}else{
				requestDto.setCustomerPhone(waybillDto.getReceiveCustomerPhone());//设置客户电话
				otherRevenueDto.setCustomerPhone(waybillDto.getReceiveCustomerPhone());
			}
			//小票标记
			requestDto.setInvoiceMark(MARK);
			otherRevenueDto.setInvoiceMark(MARK);
			//是否统一结算
			requestDto.setIsUnifySettlement(FossConstants.NO);
			//不设置合同部门编码和名称
			//设置操作人和部门信息，备注
			Date date = new Date();
			requestDto.setCreateOrgCode(currentInfo.getCurrentDeptCode());
			otherRevenueDto.setCreateOrgCode(currentInfo.getCurrentDeptCode());
			requestDto.setCreateOrgName(currentInfo.getCurrentDeptName());
			otherRevenueDto.setCreateOrgName(currentInfo.getCurrentDeptName());
			requestDto.setCreateUserCode(currentInfo.getEmpCode());
			otherRevenueDto.setCreateUserCode(currentInfo.getEmpCode());
			requestDto.setCreateUserName(currentInfo.getEmpName());
			otherRevenueDto.setCreateUserName(currentInfo.getEmpName());
			requestDto.setCreateTime(date);
			otherRevenueDto.setCreateDate(date);
			requestDto.setNotes(THE_SAFEKEEPING_FEE_RECEIPTS);
			/**
			 * 银行卡检验
			 * @author yangqiang 309603
			 * @date 2016-2-23
			 */
			/*PosCardEntity    posCardEntity    = null;			//POS实体
			BigDecimal 		 amount		      = null; 			//未使用金额
			BigDecimal       paymentAmount 	  = null;			//收款金额
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
				&& (!StringUtil.isBlank(repaymentEntity.getRevenueClaimNo())&& repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))){
				//查询T+0报表获取未使用金额
				//准备查询数据
				PosCardManageDto posCardManageDto = new PosCardManageDto();
				posCardManageDto.setTradeSerialNo(repaymentEntity.getRevenueClaimNo());
				posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
				//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_DEPOSIT);
				//查询T+0报表数据
				List<PosCardEntity> posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
				//是否存在
				if (posCardEntitys==null||posCardEntitys.isEmpty()) {
					throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确。");
				}else{
					posCardEntity = posCardEntitys.get(0);
				}
				//冻结金额
				BigDecimal frozenAmount = posCardEntity.getFrozenAmount()==null?new BigDecimal(0):posCardEntity.getFrozenAmount();
				//冻结状态判断 1.全部冻结的
				if(SettlementConstants.POS_CARD_FROZEN_STATUS_1==posCardEntity.getFrozenStatus()){
					throw new RepaymentException("该交易流水号:"+posCardEntity.getTradeSerialNo()+"已冻结，请联系资金支持部解除锁定后使用！");
				}else if(SettlementConstants.POS_CARD_FROZEN_STATUS_2==posCardEntity.getFrozenStatus()){
					//部分冻结的，允许使用金额为 未使用金额-已冻结金额
					amount = posCardEntity.getUnUsedAmount().subtract(frozenAmount);
				}else{
					//获取未使用金额；未冻结的交易流水的可使用金额为未使用金额
					amount =posCardEntity.getUnUsedAmount();
				}
				
				//比较
				//收款金额
				paymentAmount = repaymentEntity.getStorageFee();
					
				if (amount.compareTo(paymentAmount)<0) {//可用金额小于还款金额		
					throw new SettlementException("可用余额不足。");
				}	
			}*/
			CUBCOtherRevenueResultDto resultDto1 = this.otherRevenueService.addOtherRevenueToCUBC(requestDto);
			if(resultDto1 != null && StringUtils.isNotBlank(resultDto1.getMeg()) && !StringUtils.equals(resultDto1.getMeg(), OTHERNO_NO_ERROR)){
				LOGGER.error("调用CUBC新增小票接口失败，异常信息："+resultDto1.getMeg());
				throw new CUBCOtherRevenueException(resultDto1.getMeg());
			}
			if(resultDto1 != null && StringUtils.isNotBlank(resultDto1.getOtherRevenueNo())){
				otherRevenueDto.setOtherRevenueNo(resultDto1.getOtherRevenueNo());
			}
			/**
			 *更新T+0数据 调用更新数据
			 * @author yangqiang 309603
			 * @date 2016-2-23
			 */
			/*if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getRevenuePaymentType())
					&& (!StringUtil.isBlank(repaymentEntity.getRevenueClaimNo())
					&& repaymentEntity.getRevenueClaimNo().matches("[0-9]+"))
					){
					//更新POS刷卡信息
					//准备数据
					posCardEntity.setModifyUser(currentInfo.getEmpName());											//更新人名称
					posCardEntity.setModifyUserCode(currentInfo.getEmpCode());										//更新人编码
					posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(paymentAmount));					//已使用金额
			posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(paymentAmount));   								//未使用金额
					pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
					//插入新的POS刷卡明细
					//准备数据
					PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
					posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
					posCardDetailEntity.setInvoiceType("XP");													//小票
					posCardDetailEntity.setInvoiceNo(otherRevenueDto.getOtherRevenueNo());						//小票号
					posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
					posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
					posCardDetailEntity.setAmount(paymentAmount);												//发生金额
					posCardDetailEntity.setOccupateAmount(paymentAmount);										//已占用金额
					posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
					pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);								//调用接口插入数据
				//TODO 调用CUBC接口方法
				}*/
			}
		//CUBC小票新增   灰度改造    353654 ---------------------------end
	}

	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.storageChargeByOtherRevenue
	 * @Description:根据运单号和发票产生类别查询小票记录集合  计算出该运单已经产生小票记录的保管费总和
	 *
	 * @param wayBillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-21 下午3:20:07
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-21    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public BigDecimal queryStorageChargeWithOtherRevenue(String wayBillNo){
		List<OtherRevenueEntity> otherRevenueEntityList = null;
		//根据运单号和发票产生类别 发票产生部门查询小票记录---------灰度----------start---------------356354
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(wayBillNo);
        //灰度分流  --update by 243921
        String vestSystemCode2 = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
			otherRevenueEntityList = this.otherRevenueService.queryOtherRevenueByWayBillNOAndInvoiceCategory(wayBillNo,null);
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
			//TODO
		}
		//根据运单号和发票产生类别 发票产生部门查询小票记录---------灰度----------end---------------356354
	    if(CollectionUtils.isEmpty(otherRevenueEntityList)){
	    	return BigDecimal.ZERO;
	    }
	    BigDecimal count = BigDecimal.ZERO;
	    for(OtherRevenueEntity entity:otherRevenueEntityList){
	    	count=count.add(entity.getAmount());
	    }
		return count;
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.querystorageChargeUpdateInfo
	 * @Description:调用偏线空运服务中的方法查询运单的仓储费
	 *
	 * @param wayBillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午4:26:15
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public WaybillDto querystorageChargeUpdateInfo(String wayBillNo){
		if(StringUtils.isNotEmpty(wayBillNo)){
			return airAgencySignService.queryByWaybillNo(wayBillNo);
		}else{
			return null;
		}
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.updatestorageCharge
	 * @Description:根据运单号更新运单的保管费
	 *
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午6:09:36
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public int updatestorageCharge(WaybillDto dto,CurrentInfo currentInfo){
		return this.repaymentDao.updatestorageCharge(dto,currentInfo);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.isLack
	 * @Description:调用中转接口查询运单是否有上报差错
	 *
	 * @param waybillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-6-22 下午6:03:23
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-6-22    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public boolean isLack(String waybillNo){
		return this.stReportService.isLack(waybillNo);
	}
	/**
	 * 
	 * 查询汇款信息 
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-11 
	 *				下午4:28:31
	 * @param remitTransNum
	 * @return
	 * @see
	 */
	@Override
	public BigDecimal queryTransfer(String remitTransNum,String paymentType,String waybillNo) {
		//modify by 353654
			if(StringUtils.isBlank(remitTransNum) || StringUtils.isBlank(paymentType) || StringUtils.isBlank(waybillNo)){
				logger.debug("查询汇款信息请求参数不合法,值为空");
				throw new RepaymentException(RepaymentException.TRANSFER_ERROR);
			}
			BigDecimal noCancelAmount = null;
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(waybillNo);
            //灰度分流  --update by 243921
            String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                //银行卡不校验财务自助 --PCUBC-7029
                if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)){
                    try{
                        RemitTransferQueryResultDto remitTransferQueryResultDto = fossToFinanceRemittanceService.queryTransfer(remitTransNum,paymentType);
                        if(remitTransferQueryResultDto != null){
                            noCancelAmount = remitTransferQueryResultDto.getNoCancelAmount();
                        }
                    }catch (SettlementException se){
                        LOGGER.error("FOSS调财务自助查询汇款异常："+ se.getErrorCode()+"---"+se);
                        throw new RepaymentException("系统繁忙,校验汇款编号失败,请稍后重试");
                    }
                    if(noCancelAmount == null){
                        throw new RepaymentException(RepaymentException.TRANSFER_ERROR);
                    }
                }
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){	
				//cubc校验款项确认编号  add by 378375
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();//获取当前用户信息
				PayInfoRequest requestDto = new PayInfoRequest();
				requestDto.setPayCode(remitTransNum);//款项确认编号
				requestDto.setPayType(paymentType);//付款方式
				requestDto.setPayDeptCode(currentInfo.getCurrentDeptCode());//当前登录部门编码
				PayInfoRequest response = new PayInfoRequest();
				try {
					response = (PayInfoRequest) HttpClientUtils.postMethod(requestDto, new PayInfoRequest(), cubcValidataAdd);
					if(!response.getIsSuccess()){
						logger.info("查询款项确认编号失败信息为："+response.getErrorMessage());
						throw new CubcSettleException(response.getErrorMessage());
					}
				} catch (SettlementException se) {
					logger.info("CUBC服务器正忙,请稍后重试");
					throw new RepaymentException(RepaymentException.TRANSFER_ERROR);
				}
				if(response != null){
					//未核销金额
					noCancelAmount = response.getAmount();
				}
                if(noCancelAmount == null){
                    throw new RepaymentException(RepaymentException.TRANSFER_ERROR);
                }
			}
			return noCancelAmount;
	}
	
	/**
	 * 
	 * 修改付款数据
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-7 下午6:52:25
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#updateRepayment(com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Transactional
	@Override
	public int updateRepayment(RepaymentEntity record) 
	{
		return  repaymentDao.updateRepayment(record);
	}
	
	/**
	 * Sets the air agency sign service.
	 *
	 * @param airAgencySignService the new air agency sign service
	 */
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService){
		this.airAgencySignService = airAgencySignService;
	}

	/**
	 * Sets the repayment dao.
	 *
	 * @param repaymentDao the new repayment dao
	 */
	public void setRepaymentDao(IRepaymentDao repaymentDao){
		this.repaymentDao = repaymentDao;
	}

	/**
	 * Sets the bill receivable service.
	 *
	 * @param billReceivableService the new bill receivable service
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService){
		this.billReceivableService = billReceivableService;
	}

	/**
	 * Sets the arrive sheet mannger service.
	 *
	 * @param arriveSheetManngerService the new arrive sheet mannger service
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService){
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * Sets the payment settlement service.
	 *
	 * @param paymentSettlementService the new payment settlement service
	 */
	public void setPaymentSettlementService(IPaymentSettlementService paymentSettlementService){
		this.paymentSettlementService = paymentSettlementService;
	}

	/**
	 * Sets the actual freight service.
	 *
	 * @param actualFreightService the new actual freight service
	 */
	public void setActualFreightService(IActualFreightService actualFreightService){
		this.actualFreightService = actualFreightService;
	}

	/**
	 * Sets the login service.
	 *
	 * @param loginService the new login service
	 */
	public void setLoginService(ILoginService loginService){
		this.loginService = loginService;
	}

	/**
	 * Sets the waybill rfc service.
	 *
	 * @param waybillRfcService the new waybill rfc service
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService){
		this.waybillRfcService = waybillRfcService;
	}
	
	/**
	 * Sets the handle query outfield service.
	 *
	 * @param handleQueryOutfieldService the new handle query outfield service
	 */
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	
	/**
	 * 根据运单号获取最后插入的 一条付款记录（查询付款方式，实付运费）.
	 *
	 * @param repayment the repayment
	 * @return the repayment entity
	 * @author foss-
	 * meiying
	 * @date 2013-2-21
	 * 下午2:29:31
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#queryRepaymentTypebyNo
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	public RepaymentEntity queryRepaymentTypebyNo(RepaymentEntity repayment) {
		return repaymentDao.queryRepaymentTypebyNo(repayment);
	}
	
	/**
	 * 提前找货接口
	 *
	 * @param WaybillDto the WaybillDto
	 * @return the repayment entity
	 * @author foss-yuting
	 * @date 2014-11-24
	 * 下午2:29:31
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService#updateInadvanceGoodsByRepayment
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	@Transactional
	public String updateInadvanceGoodsByRepayment(WaybillDto dto) {
		 //验证改运单是否可以提前找货
		  if(dto!=null){
			  String waybillNo = dto.getWaybillNo();
			  if(StringUtils.isNotEmpty(waybillNo)){
				  WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(waybillNo);
				  if(waybill!=null){
					  if(FossConstants.YES.equals(waybill.getIsWholeVehicle())){//是整车运单
						  //是整车环境
						  return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
					  }
				  }
				//校验未受理的更改单
				if(waybillRfcService.isExsitsWayBillRfc(waybillNo)){
					 //抛出业务异常
					  return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
				}
				//查询该条运单是否存在
				PickupResultDto rstPickupResultDto = pickupService.isExistByWaybill(waybillNo);
				WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(waybillNo);
				if(rstPickupResultDto==null){
					rstPickupResultDto=new PickupResultDto();
					rstPickupResultDto=updateDataInit(rstPickupResultDto,rstWaybill,PickupConstants.PICKUP_INSERT);
					pickupService.insertEntity(rstPickupResultDto);
				}else{
					//数据实时更新
					if(StringUtils.isNotEmpty(rstPickupResultDto.getState())&&rstPickupResultDto.getState().equals(PickupConstants.GOOD_STATE_ALLSIGN)){
						return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
					}else if(StringUtils.isNotEmpty(rstPickupResultDto.getState())&&
							rstPickupResultDto.getState().equals(PickupConstants.GOOD_STATE_HASINFORM)){
						  return PickupConstants.CURRENT_WAYBILL_ALREADY_GOODS_INADVANCE;
					}else{
						rstPickupResultDto=updateDataInit(rstPickupResultDto,rstWaybill,null);
						pickupService.updatePickupStateByPickupResultDto(rstPickupResultDto);  
					}
				}
			  }
		  }
			return PickupConstants.OPERATE_SUCCESS;
		}

    /**
     * 变更签收结果时的统一结算校验
     * @param dto 变更后的付款方式dto
     *
     * 统一结算需求
     * 运单到达客户为统一结算客户则：
     * 1.结清货款客户必须为到达客户
     * 2.付款方式只能为电汇
     *
     * @Author 105762
     */
    private void valideUnifiedSettlement(PaymentSettlementDto dto) {
        ActualFreightEntity actualFreightEntity;
        com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto waybill;
        try {
            //获取运单信息
            waybill = waybillManagerService.queryWaybillByNo(dto.getWaybillNo());
            actualFreightEntity = waybill.getActualFreightEntity();
            if (null == waybill || null == actualFreightEntity) {
                throw new RepaymentException("获取运单信息异常！");
            }
        } catch (Exception e) {
            throw new RepaymentException("查询运单发票标记接口（接送货模块）发生异常，信息：" + e.getMessage());
        }

        //判断是否到达统一结算
        if (StringUtil.equals(actualFreightEntity.getArriveCentralizedSettlement(), FossConstants.YES)) {
            //结清货款客户只能与运单上的到达客户相同
            if (!StringUtil.equals(dto.getCustomerCode(), waybill.getWaybillEntity().getReceiveCustomerCode())) {
                throw new RepaymentException(String.format("运单：%s 到达客户为【统一结算】，则变更签收结果时新客户只能选择编号为【%s】的" +
                                "客户，且付款方式只能选择为【临欠】【月结】或者【电汇】。",
                        dto.getWaybillNo(), waybill.getWaybillEntity().getReceiveCustomerCode()));
            }
            //付款方式只能为电汇
            if (!StringUtil.equals(dto.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)) {
                throw new RepaymentException(String.format("运单：%s 到达客户为统一结算，则变更签收结果时新客户只能选择编号为【%s】的" +
                                "客户，且付款方式只能选择为【临欠】【月结】或者【电汇】。",
                        dto.getWaybillNo(), waybill.getWaybillEntity().getReceiveCustomerCode()));
            }
        }
    }

    /**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto rstPickupResultDto,WaybillEntity rstWaybill,String state) {
		if(rstWaybill!=null){
			rstPickupResultDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			rstPickupResultDto.setGoodPackage(rstWaybill.getGoodsPackage());
			rstPickupResultDto.setGoodSize(rstWaybill.getGoodsSize());
			rstPickupResultDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			rstPickupResultDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			rstPickupResultDto.setOperTime(new Date());
			rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
			if(StringUtil.isNotEmpty(state)&&StringUtils.equals(state, PickupConstants.PICKUP_INSERT)){//插入操作
				rstPickupResultDto.setWaybillNo(rstWaybill.getWaybillNo());
				rstPickupResultDto.setId(UUIDUtils.getUUID());
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				rstPickupResultDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
				rstPickupResultDto.setOperateOrgName(currentInfo.getCurrentDeptName());
				rstPickupResultDto.setOperateUserName(currentInfo.getEmpName());
				rstPickupResultDto.setOperateUserCode(currentInfo.getEmpCode());
				rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
				List<String> endStockOrgCodes = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currentInfo.getCurrentDeptCode());
				// 获取对应的营业部或驻地外场
				if(CollectionUtils.isNotEmpty(endStockOrgCodes) && StringUtils.isNotEmpty(endStockOrgCodes.get(0))){
					rstPickupResultDto.setEndStockOrgCode(endStockOrgCodes.get(0));
				}else {
					rstPickupResultDto.setEndStockOrgCode(DeliverHandlerConstants.DEFAULT_TRANSFER_CENTER);
				}
				return rstPickupResultDto;
			}
		}
		return rstPickupResultDto;
	}
	
	/**
	 * 张新 2015-2-3
	 * 		
	 * 原单结清货款
	 * 
	 * 		
	 */
	@Override
	public void addOriginalPaymentInfo(RepaymentEntity repaymentEntity,CurrentInfo currentInfo, String returnType, String waybillNo, TwoInOneWaybillDto twoInOneWaybillDto) {
		try {
			// 得到付款方式
			if (repaymentEntity != null) {
				// 查询原单运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(repaymentEntity.getWaybillNo());
				if (waybill == null) {
					// 原单运单为空
					return;
				}
				// 1.判断是不是快递代理
				OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
						waybill.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
				if (companyDto != null) {
					// 快递代理
					return;
				}
				// 根据通过返单号查询对应原单号
				List<WaybillExpressEntity> waybillLists = waybillExpressService.queryWaybillListByWaybillNo(waybillNo);

				if (CollectionUtils.isNotEmpty(waybillLists)) {
					int paymentCount = 0;
					for (int i = 0, len = waybillLists.size(); i < len; i++) {
						WaybillExpressEntity waybillExpress = waybillLists.get(i);
						if (isPayment(waybillExpress.getOriginalWaybillNo())) {
							paymentCount ++;
						}
					}
					
					// 原单号是否结清
					if (waybillLists.size() == paymentCount) {
						return;
					}
				}
				
				// 判断是否子母件
				if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
					// 按票返
					if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)) {
						// 判断母件是否结清
						validaCurrentInfo(repaymentEntity, currentInfo,
								twoInOneWaybillDto);
					} else {
						if (CollectionUtils.isNotEmpty(waybillLists)) {
							// 判断母件是否结清
							validaRepaymentEntity(repaymentEntity, currentInfo,
									twoInOneWaybillDto, waybillLists);
						}
					}
				} else { // 不是子母件
					// 张新 原单应收款大于零时抛出异常$$
					if (repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
						// 抛出业务异常
						throw new RepaymentException(RepaymentException.EXIST_WAYBILLERROR);
					}
					settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
				}

			}
		} catch (SettlementException se) {
			// 抛出异常
			throw new RepaymentException(se.getErrorCode(), se);
		}
	}

	private void validaCurrentInfo(RepaymentEntity repaymentEntity,
			CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto) {
		if (!isPayment(twoInOneWaybillDto.getMainWaybillNo())) {
			// 得到结算应收单信息
			FinancialDto financialDto = queryFinanceSign(repaymentEntity.getWaybillNo());
			// 应收代收款
			BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
			// 应收到付款
			BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
			// 设置 实付运费
			repaymentEntity.setActualFreight(receiveablePayAmoout);
			// 设置 代收货款
			repaymentEntity.setCodAmount(receiveableAmount);
			// 设置运单号
			repaymentEntity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
			// 原单结清货款
			settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
		}
		// 判断子件集合是否为空
		if (CollectionUtils.isNotEmpty(twoInOneWaybillDto.getWaybillNoList())) {
			List<String> waybillNos = twoInOneWaybillDto.getWaybillNoList();
			// 遍历子件集合
			for (int i = 0, len = waybillNos.size(); i < len; i++) {
				if (!isPayment(waybillNos.get(i))) {
					// 得到结算应收单信息
					FinancialDto financialDto = queryFinanceSign(waybillNos.get(i));
					// 应收代收款
					BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
					// 应收到付款
					BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
					// 设置 实付运费
					repaymentEntity.setActualFreight(receiveablePayAmoout);
					// 设置 代收货款
					repaymentEntity.setCodAmount(receiveableAmount);
					// 设置运单号
					repaymentEntity.setWaybillNo(waybillNos.get(i));
					// 原单结清货款
					settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
				}
			}
		}
	}

	private void validaRepaymentEntity(RepaymentEntity repaymentEntity,
			CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto,
			List<WaybillExpressEntity> waybillLists) {
		if (!isPayment(twoInOneWaybillDto.getMainWaybillNo())) {
			// 得到结算应收单信息
			FinancialDto financialDto = queryFinanceSign(repaymentEntity.getWaybillNo());
			// 应收代收款
			BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
			// 应收到付款
			BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
			// 设置 实付运费
			repaymentEntity.setActualFreight(receiveablePayAmoout);
			// 设置 代收货款
			repaymentEntity.setCodAmount(receiveableAmount);
			// 设置运单号
			repaymentEntity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
			// 原单结清货款
			settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
		}
		for (int i = 0, len = waybillLists.size(); i < len; i++) {
			WaybillExpressEntity waybillExpress = waybillLists.get(i);
			if (!waybillExpress.getOriginalWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) {
				// 得到结算应收单信息
				FinancialDto financialDto = queryFinanceSign(waybillExpress.getOriginalWaybillNo());
				// 应收代收款
				BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
				// 应收到付款
				BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
				// 设置 实付运费
				repaymentEntity.setActualFreight(receiveablePayAmoout);
				// 设置 代收货款
				repaymentEntity.setCodAmount(receiveableAmount);
				// 设置运单号
				repaymentEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo());
				// 原单结清货款
				settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
			}
		}
	}
	
	/** 
	 * 根据不同的付款方式 结清货款(原单)
	 * @param repaymentEntity 付款对象
	 * @param currentInfo 当前信息对此昂
	 * @param paymentType 付款方式
	 * @param dateStr2  付款编号
	 * @author fangwenjun 237982
	 * @date 2015年9月15日 上午9:43:16 
	 */
	private void settleUpOriginalRepayment(RepaymentEntity repaymentEntity,CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto) {
		// 校验未受理的更改单
		if (waybillRfcService.isExsitsWayBillRfc(repaymentEntity.getWaybillNo())) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.EXIST_WAYBILLRFC);
		}
		
		ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(repaymentEntity.getWaybillNo());
		// 判断运单是否已中止或已作废
		if (actentity != null && (WaybillConstants.ABORTED.equals(actentity.getStatus()) 
				|| WaybillConstants.OBSOLETE.equals(actentity.getStatus()))) {
			// 抛出业务异常
			throw new RepaymentException(RepaymentException.WAYBILLRFC_ERROR);
		}
		
		// 获取付款方式
		String paymentType = repaymentEntity.getPaymentType();
		// 生成付款编号
		StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		// 拼接付款编号
		dateStr = dateStr.append(repaymentEntity.getWaybillNo());
		
		//张新  当付款方式为临欠或者月结 且到付运费为零的时候  付款方式修改为现金
		if (((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType) 
				|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)) 
				&& (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO)==0)) {
			repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			paymentType=SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH;
		}
		
		// 子母件母件或不是子母件标识
		boolean isMainWaybillNo = true;
		if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
			
			if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) {
				isMainWaybillNo = false;
			} 
		}
		
		
		//结算传入
		PaymentSettlementDto psldto = new PaymentSettlementDto(); 
		// 如果付款方式为临欠或月结时 调用结算接口-到付运费结转临欠月结
		if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType) || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)){
			//运单号
			psldto.setWaybillNo(repaymentEntity.getWaybillNo());
			//付款类型
			psldto.setPaymentType(repaymentEntity.getPaymentType());
			//部门cide
			psldto.setDestOrgCode(FossUserContextHelper.getOrgCode());
			//部门名称
			psldto.setDestOrgName(FossUserContextHelper.getOrgName());
			//客户code
			psldto.setCustomerCode(repaymentEntity.getConsigneeCode());
			//客户名称
			psldto.setCustomerName(repaymentEntity.getConsigneeName());
			//时间
			psldto.setBusinessDate(new Date());
			//实收代收货款费用
			psldto.setCodFee(repaymentEntity.getCodAmount());
			//实收到付运费
			psldto.setToPayFee(repaymentEntity.getActualFreight());
			//当实收货款和到付运费不都为0时
			if (isMainWaybillNo) {		
				//------CUBC灰度结清服务开始16--------add by 378375
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(psldto.getWaybillNo());
                //灰度分流  --update by 243921
                String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
					paymentSettlementService.confirmToBillReceivable(psldto, currentInfo);
					LOGGER.info("调用结算接口结束");//记录日志	
					//更新ActualFreight表中的结清状态为已结清
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					//运单号
					actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
					//结清状态-已结清
					actualFreightEntity.setSettleStatus(FossConstants.YES);
					//结款日期
					actualFreightEntity.setSettleTime(new Date());
					//收货人
					actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
					//证件类型
					actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
					//证件号
					actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
					if(StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())){
						//证件类型(代收人)
						actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
						//证件号码（代收）
						actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
					} else {
						//证件类型(代收人)
						actualFreightEntity.setCodIdentifyType("");
						//证件号码（代收）
						actualFreightEntity.setCodIdentifyCode("");
					}
					//更新actualFreight表
					actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					//验证用户选择的付款方式是否为临欠月结
				//	validatePaymentType(psldto);
					FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
					fossDto.setDto(psldto);
					fossDto.setCurrentInfo(currentInfo);
					CUBCResponseDto resultDto1 = cubcReverseSettlementService.reverseSettleReqToCUBU(fossDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMessage())){
							LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
					}
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
						//更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						//运单号
						actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
						//结清状态-已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						//结款日期
						actualFreightEntity.setSettleTime(new Date());
						//收货人
						actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
						//证件类型
						actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
						//证件号
						actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
						if(StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())){
							//证件类型(代收人)
							actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
							//证件号码（代收）
							actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
						} else {
							//证件类型(代收人)
							actualFreightEntity.setCodIdentifyType("");
							//证件号码（代收）
							actualFreightEntity.setCodIdentifyCode("");
						}
						//更新actualFreight表
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					}
				}
				//------CUBC灰度结清服务结束--------add by 378375
			}
            			
		// 付款方式为其他方式时,调用结算接口-实收货款
		} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(paymentType)
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(paymentType)
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(paymentType)
				|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH).equals(paymentType)) {
			//付款方式为网上支付时候   add  by  309603  yangqiang
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentEntity.getPaymentType())
					&& StringUtil.isBlank(repaymentEntity.getClaimNo())) {
				if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO)!=0 || repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO)!=0)
				throw new RepaymentException("付款方式为网上支付时候，款项确认编号不能为空。");
			}
			// 付款方式是‘银行卡’时，款项确认编号必须输入数字。
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
					&& (StringUtil.isBlank(repaymentEntity.getClaimNo())
					|| !repaymentEntity.getClaimNo().matches("[0-9]+"))) {
				throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
			}
			/**
			 * 银行卡检验
			 * @author yangqiang 309603
			 * @date   2016-2-23
			 */
			PosCardEntity    posCardEntity    = null;	//POS实体
			BigDecimal amount = null;					//POS未使用金额
			BigDecimal codAmount = null;				// 实收代收货款费用
			BigDecimal actualFreight = null;			// 实收到实付运费
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
					&& (!StringUtil.isBlank(repaymentEntity.getClaimNo())
					&& repaymentEntity.getClaimNo().matches("[0-9]+"))
					) {
				//查询T+0报表获取未使用金额
				//准备查询数据
				PosCardManageDto posCardManageDto = new PosCardManageDto();
				posCardManageDto.setTradeSerialNo(repaymentEntity.getClaimNo());
				posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
				//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
				//查询T+0报表数据
				List<PosCardEntity> posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
				//是否存在
				if (posCardEntitys==null||posCardEntitys.isEmpty()) {
					throw new RepaymentException("输入流水号不存在或者输入流水号有误或者流水所属部门不正确。");
				}else{
					posCardEntity = posCardEntitys.get(0);
				}
				//判断所属模块，结清货款，
				if(SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())){
					throw new RepaymentException("该交易流水号仅能做预收或做小票！");
				}
				//冻结金额
				BigDecimal frozenAmount = posCardEntity.getFrozenAmount()==null?new BigDecimal(0):posCardEntity.getFrozenAmount();
				//冻结状态判断 1.全部冻结的
				if(SettlementConstants.POS_CARD_FROZEN_STATUS_1==posCardEntity.getFrozenStatus()){
					throw new RepaymentException("该交易流水号:"+posCardEntity.getTradeSerialNo()+"已冻结，请联系资金支持部解除锁定后使用！");
				}else if(SettlementConstants.POS_CARD_FROZEN_STATUS_2==posCardEntity.getFrozenStatus()){
					//部分冻结的，允许使用金额为 未使用金额-已冻结金额
					amount = posCardEntity.getUnUsedAmount().subtract(frozenAmount);
				}else{
					//获取未使用金额；未冻结的交易流水的可使用金额为未使用金额
					amount =posCardEntity.getUnUsedAmount();
				}
				
				//比较
				// 实收代收货款费用
				codAmount = repaymentEntity.getCodAmount();
				// 实收到实付运费
				actualFreight = repaymentEntity.getActualFreight();
				
				if (amount.compareTo(codAmount.add(actualFreight))<0) {//可用金额小于实收代收货款+实收到付运费
					
					throw new RepaymentException("可用余额不足。");
				}	
			}
			//运单号
			psldto.setWaybillNo(repaymentEntity.getWaybillNo());
			//付款类型
			psldto.setPaymentType(repaymentEntity.getPaymentType());
			//部门cide
			psldto.setDestOrgCode(FossUserContextHelper.getOrgCode());
			//部门名称
			psldto.setDestOrgName(FossUserContextHelper.getOrgName());
			//客户code
			psldto.setCustomerCode(repaymentEntity.getConsigneeCode());
			//客户名称
			psldto.setCustomerName(repaymentEntity.getConsigneeName());
			//时间
			psldto.setBusinessDate(new Date());
			//付款编号
			psldto.setSourceBillNo(dateStr.toString());
			//实收代收货款费用
			psldto.setCodFee(repaymentEntity.getCodAmount());
			//实收到付运费
			psldto.setToPayFee(repaymentEntity.getActualFreight());
			//款项认领编号
			psldto.setPaymentNo(repaymentEntity.getClaimNo());
				
			//当实付运费和代收货款同时为0时 更改为已结清
			if (BigDecimalOperationUtil.compare(repaymentEntity.getCodAmount(), BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(repaymentEntity.getActualFreight(), BigDecimal.ZERO)) {
				// 更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
				//结清状态-已结清
				actualFreightEntity.setSettleStatus(FossConstants.YES);
				//结款日期
				actualFreightEntity.setSettleTime(new Date());
				//收货人
				actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
				//证件类型
				actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
				//证件号
				actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
				if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
				} else {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType("");
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode("");
				}
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
			} else {
				if (isMainWaybillNo) {			
					//------CUBC灰度结清服务开始17--------add by 378375
					String message = null;
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(repaymentEntity.getWaybillNo());
                    //灰度分流  --update by 243921
                    String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
						message = paymentSettlementService.confirmToPayment(psldto, currentInfo);
						LOGGER.info("调用结算接口结束");//记录日志					
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(psldto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMessage())){
								LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
								throw new CubcSettleException(resultDto1.getMessage());
							}
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
							message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
						}
					}
					//------CUBC灰度结清服务结束--------add by 378375
					if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)) {
						// 更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						// 运单号
						actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
						// 结清状态-已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						// 结款日期
						actualFreightEntity.setSettleTime(new Date());
						// 收货人
						actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
						// 证件类型
						actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
						// 证件号
						actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
						if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
							// 证件类型(代收人)
							actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
							// 证件号码（代收）
							actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
						} else {
							// 证件类型(代收人)
							actualFreightEntity.setCodIdentifyType("");
							// 证件号码（代收）
							actualFreightEntity.setCodIdentifyCode("");
						}
						// 更新actualFreight表
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					}
				} else {
					// 更新ActualFreight表中的结清状态为已结清
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					// 运单号
					actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
					// 结清状态-已结清
					actualFreightEntity.setSettleStatus(FossConstants.YES);
					// 结款日期
					actualFreightEntity.setSettleTime(new Date());
					// 收货人
					actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
					// 证件类型
					actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
					// 证件号
					actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
					if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
						// 证件类型(代收人)
						actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
						// 证件号码（代收）
						actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
					} else {
						// 证件类型(代收人)
						actualFreightEntity.setCodIdentifyType("");
						// 证件号码（代收）
						actualFreightEntity.setCodIdentifyCode("");
					}
					// 更新actualFreight表
					actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				}
			}
			/**
			 *更新T+0数据 调用更新数据
			 * @author yangqiang 309603
			 * @date   2016-2-23
			 */
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())
					&& (!StringUtil.isBlank(repaymentEntity.getClaimNo())
					&& repaymentEntity.getClaimNo().matches("[0-9]+"))
					){
				//更新POS刷卡信息
				//准备数据
				BigDecimal payAmount = codAmount.add(actualFreight);										//使用总金额
				posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
				posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
				posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));					//已使用金额
				posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(payAmount));   		//未使用金额
				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
				//插入新的POS刷卡明细
				//准备数据
				PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
				posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
				posCardDetailEntity.setInvoiceType("W2");													//运单
				posCardDetailEntity.setInvoiceNo(repaymentEntity.getWaybillNo());							//运单号
				//posCardDetailEntity.setAmount(waybilldto.getToPayAmount());									//发生金额
				BigDecimal totalAmount = repaymentDao.getTotalAmount(repaymentEntity.getWaybillNo());
				posCardDetailEntity.setAmount(totalAmount);									//发生金额
				posCardDetailEntity.setOccupateAmount(payAmount);											//已占用金额
				//BigDecimal unVerifyAmount = (repaymentEntity.getCodAmount().add(repaymentEntity.getActualFreight())).subtract(payAmount);
				posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
				posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
				posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
				pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);									//调用接口插入数据
			}
		}
		// 付款编号
		repaymentEntity.setRepaymentNo(dateStr.toString());
		// 付款时间
		repaymentEntity.setPaymentTime(new Date());
		// 操作人
		repaymentEntity.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		repaymentEntity.setOperatorCode(FossUserContextHelper.getUserCode());
		// 操作部门
		repaymentEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		repaymentEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		// 当实付运费和代收货款同时为0时 更改为无需生成
		if (BigDecimalOperationUtil.compare(repaymentEntity.getCodAmount(), BigDecimal.ZERO)
				&& BigDecimalOperationUtil.compare(repaymentEntity.getActualFreight(), BigDecimal.ZERO)) {
			// 无需结清 将付款信息置0
			repaymentEntity.setActualFreight(BigDecimal.ZERO);
			// 无需结清 将付款信息置0
			repaymentEntity.setCodAmount(BigDecimal.ZERO);
			// 财务单据无需生成
			repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
		} else {
			// 判断是否是子母件
			if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
				if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
					// 财务单据已生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
				} else { // 为子母件的子件
					// 设置财务单据无需生成
					repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
				}
			} else { // 不是子母件
				// 财务单据已生成
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			}
		}
		// 币种
		repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// job id
		repaymentEntity.setJobId(UUIDUtils.getUUID());
		// 生成付款信息
		repaymentDao.addPaymentInfo(repaymentEntity);
		// 生成到达联信息
		ArriveSheetEntity entity = new ArriveSheetEntity();
		// 运单号
		entity.setWaybillNo(repaymentEntity.getWaybillNo());
		// 收货人
		entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
		// 证件类型
		entity.setIdentifyType(repaymentEntity.getIdentifyType());
		// 证件号
		entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
		// 调用到达联接口
		arriveSheetManngerService.checkGenerateArriveSheet(entity);
		// 更新到达联,通过运单号
		arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(),
				repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(),
				repaymentEntity.getIdentifyCode());
	}
	
	@Override
	public boolean isHaveRepayment(String waybillNo) {
		RepaymentEntity repayment = new RepaymentEntity();
		//运单号
		repayment.setWaybillNo(waybillNo);
		//返回是否存在
		return repaymentDao.isExistRepayment(repayment);
	}
	
	/**
	 * 查询母单的一条有效的付款信息；
	 * 如果子单没做结清，快递自提自动结清就copy母单的结清信息
	 * @author 231438-chenjunying
	 * @date 2015-08-29 上午9:45:19
	 */
	@Override
	public RepaymentEntity queryOneRepaymentInfo(RepaymentEntity entity){
		entity.setActive(FossConstants.YES);
		List<RepaymentEntity> paymentList = repaymentDao.searchRepaymentList(entity);
		if(CollectionUtils.isNotEmpty(paymentList) && paymentList.size()>0){
			return paymentList.get(0);
		}else{
			return null;
		}
	}
	/**
	 * Sets 
	 * 		the share job dao.
	 *
	 * @param shareJobDao 
	 * 		the new share job dao
	 */
	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	
	/**
	 * 零担合伙人代收货款反结清<br>
	 * 		若同步接口异常，则继续抛出异常。
	 * @author 239284
	 * 
	 */
	public void reversPtpCOD(PaymentSettlementDto dto, CurrentInfo currentInfo, BigDecimal codAmount) {
		LOGGER.info("合伙人-代收货款反结清-释放余额更改应收单状态-start...");
		if (null == codAmount || BigDecimal.ZERO.equals(codAmount)) {
			return ;
		}
		String url = "";
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(SignConstants.PTP_REFUND_ESB_SYN_SERVER_CODE);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			throw new RepaymentException("合伙人-代收货款反结清-读取esb地址有误!");
		}
		HttpClient httpClient = new HttpClient();
		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);
		PtpCodReversDeductReqEntity req = new PtpCodReversDeductReqEntity();
		PtpCodReversDeductRespEntity result = new PtpCodReversDeductRespEntity();
		try {

			// 来源单号
			if (null ==dto  ||  StringUtil.isEmpty(dto.getWaybillNo())) {
				throw new Exception("运单号为空");
			} else {
				req.setSourceBillNo(dto.getWaybillNo());
			}
			
			WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if (null != wayBillEntity) {
				
				//设置开单日期
				req.setBizDate(wayBillEntity.getBillTime());
				
				// 判断合伙人信息-不能为空-传标杆编码
				if (StringUtil.isNotEmpty(wayBillEntity.getLastLoadOrgCode())) {
					OrgAdministrativeInfoEntity orgAdminEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(wayBillEntity.getLastLoadOrgCode());
					if (null != orgAdminEntity && StringUtil.isNotEmpty(orgAdminEntity.getUnifiedCode() )) {
						req.setPartnerOrgCode(orgAdminEntity.getUnifiedCode());
					} else {
						throw new Exception("合伙人到达部门标杆编码为空!");
					}
				} else {
					throw new Exception("该运单号：" + dto.getWaybillNo() + ", 的到达部门不存在!");
				}
			} else {
				throw new Exception("该运单号：" + dto.getWaybillNo() + ", 不存在!");
			}
			
			//设置当前操作人code
			req.setOperatorCode(currentInfo.getEmpCode());
			//设置当前操作人name
			req.setOperatorName(currentInfo.getEmpName());

			// 检查扣款类别-代收货款应收单
			req.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
			//检查流水类别-代收货款应收单
			req.setFlowType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);

			// 代收货款金额金额(调接口传入负数)
			req.setAmount(codAmount.multiply(new BigDecimal(-1)));
			
			Object obj = JSONObject.toJSON(req);
			String json = null == obj ? "" : obj.toString();
			LOGGER.info("合伙人-代收货款反结清-请求PTP参数:" + json);
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			String responseBody = "";
	
			// 执行postMethod
			httpClient.executeMethod(postMethod);
	
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			LOGGER.info("合伙人-代收货款反结清-响应信息:" + responseBody);
			
			// 将返回值转换成对象
			JSONObject returnJSON = JSONObject.parseObject(responseBody);
	
			result = JSONObject.toJavaObject(returnJSON, PtpCodReversDeductRespEntity.class);
			
			//如果接口有返回异常，则抛出异常信息
			if (null !=result  && !result.getSuccess()) {
				throw new Exception(result.getMessage());
			}
			
		} catch (Exception e) {
			throw new RepaymentException(e.getMessage(), e);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
		LOGGER.info("合伙人-代收货款反结清-释放余额更改应收单状态-end...");
	}
	
	/**
	 * 
	 * @param orgCode
	 * @return
	 */
	private boolean isPartnerDept(String orgCode){
		//DDW
		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(orgCode);
		//判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
		if(entity != null && FossConstants.YES.equals(entity.getIsLeagueSaleDept())){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * Sets 
	 * 		the configuration params service.
	 *
	 * @param configurationParamsService 
	 * 		the new configuration params service
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setFossToFinanceRemittanceService(
			IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}

	public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}

	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}

	public void setStReportService(IStReportService stReportService) {
		this.stReportService = stReportService;
	}

	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	
	public IWaybillRelateDetailEntityService getWaybillRelateDetailEntityService() {
		return waybillRelateDetailEntityService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}

	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setCubcSettlementService(
			ICubcSettlementService cubcSettlementService) {
		this.cubcSettlementService = cubcSettlementService;
	}

	public void setCubcReverseSettlementService(
			ICubcReverseSettlementService cubcReverseSettlementService) {
		this.cubcReverseSettlementService = cubcReverseSettlementService;
	}
	
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * 当付款方式为余额时返回客户的余额  add by 353654 调用CUBC的服务
	 * 
	 */
	@SuppressWarnings("unused")
	@Override
	public BigDecimal queryBalanceAmountByConsigneeCode(AccountBookRequestDto dto) {
		if(StringUtils.isBlank(dto.getCustomerCode()) || StringUtils.isBlank(dto.getDeptCode()) || dto==null){
			LOGGER.error(QUERY_BAlANCE_AMOUNT_PARAMS_ERROR);
			throw new RepaymentException(QUERY_BAlANCE_AMOUNT_PARAMS_ERROR);
		}
		logger.info("CUBC用户余额查询服务请求地址信息:" + esbAddress);
		PostMethod post = null;
		String errorMessge = null;
		String flag = null;
		BigDecimal balanceAmount = new BigDecimal(Double.toString(0));
		/*CustomerEntity entity = null;
		try {
			entity = customerService.queryCustomerInfoByCusCode(dto.getCustomerCode());
		} catch (Exception e1) {
			throw new RepaymentException("未能获取到客户类型,请核实客户基础信息");
		}
		if(entity == null || StringUtils.isBlank(entity.getType())){
			throw new RepaymentException("未能获取到客户类型,请核实客户基础信息");
		}
		dto.setCustomerType(entity.getType());*/
		dto.setCustomerType("LC");
		try {
			String requestJson = JSONObject.toJSONString(dto);
			logger.info("调用CUBC查询用户余额服务请求JSON信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson,APPLICATION_JSON,CODE_FORMAT);
			post = new PostMethod(esbAddress);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader(CONTENT_TYPE,REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_15000);
			params.setSoTimeout(NumberConstants.NUMBER_2999);
			httpClient.executeMethod(post);
//			String resultJson = post.getResponseBodyAsString();
			InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resultJson = sbf.toString();
			logger.info("调用CUBC查询用户余额服务响应JSON信息："+resultJson);
			AccountBookRequestDto resultDto = JSONObject.parseObject(resultJson,AccountBookRequestDto.class);
			errorMessge = resultDto.getErrorMessage();
			flag = resultDto.getIsSuccess();
			if(resultDto != null){
				balanceAmount = resultDto.getBalance();
				return balanceAmount;
			}else{
				return balanceAmount;
			}
		} catch (Exception e) {
			if(StringUtils.isNotBlank(errorMessge) || StringUtils.equals("false", flag)){
				if(StringUtils.isNotBlank(errorMessge)){
					logger.error("调用CUBC查询用户余额服务失败，异常信息："+errorMessge);
					throw new RepaymentException("未能获取到客户账本信息,请核实客户基础信息");					
				}else{
					throw new RepaymentException("系统繁忙,请稍后重试,若反复重试无效,请联系工作人员");
				}
			}else{
				throw new RepaymentException("系统繁忙,请稍后重试,若反复重试无效,请联系工作人员");
			}
		} finally{
			if(post!=null){
				post.releaseConnection();
			}
		}
	}

	/**
	 * 验证付款方式是否为临欠/月结
	 * 
	 * @author 378375
	 * @date 2017.01.09
	 */
	private void validatePaymentType(PaymentSettlementDto dto){
		//根据客户编码查询出客户的相关信息
		CustomerDto customerDto = customerService.queryCustInfoByCode(dto.getCustomerCode());
		if(dto.getDestOrgName() != null && customerDto.getDeptname() != null){
			//客户所属部门名称
			if(!customerDto.getDeptname().equals(dto.getDestOrgName())){
				if((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(dto.getPaymentType())){
					throw new SettlementException("该客户非本部门月结客户,不能进行月结!");
				}
			}
		}
	}
	/**
	 * 根据运单号查询运单所有的付款信息
	 * @author 243921-foss-zhangtingting
	 * @date 2017-03-02 17:50:42
	 */
	@Override
	public List<RepaymentEntity> queryRepaymentListByWaybillNo(String waybillNo) {
		RepaymentEntity repayment = new RepaymentEntity();
		//有效
		repayment.setActive(FossConstants.ACTIVE);
		//运单号
		repayment.setWaybillNo(waybillNo);
		return repaymentDao.searchRepaymentList(repayment);
	}
	/**
	 * 添加结清货款信息
	 * @author 243921
	 * @date 2017年3月5日 下午6:01:25
	 * @param repaymentEntity
	 * @see
	 */
	@Override
	public void addPaymentInfo(RepaymentEntity repaymentEntity) {
		repaymentDao.addPaymentInfo(repaymentEntity);
	}	
}