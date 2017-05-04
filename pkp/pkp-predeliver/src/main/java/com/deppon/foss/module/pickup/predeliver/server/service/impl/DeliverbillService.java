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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/DeliverbillService.java
 * 
 * FILE NAME        	: DeliverbillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-31 	
新增	
熊勇	V0.1
2012-6-25	
根据ITA建议修改	
王辉	V0.2
2012-6-30	
根据6月27日评审建议修改	
王辉	V0.3
2012-7-19	
调整列表显示	 
王辉	V0.4
2012-7-24	
业务评审完毕，
升至0.9	王辉	V0.9
2012-10-12	
根据 RC-606修订	
邓亭亭	
2012-10-13	
根据RC-125修订	
王辉	
2012-10-13	
根据RC-459修订	
王辉	
2012-10-13	
根据RC-593修订	
王辉	
2012-11-19	
根据ISSUE-665修订	
王辉	
2012-11-19	
根据ISSUE-434修订	
王辉	
2012-12-7	
根据ISSUE-1053修订	
王辉	
2013-1-15	
根据ISSUE-1530修订	
王辉	
2013-1-15	
根据ISSUE-1524修订	
王辉	

1.	SUC-447 –创建预派送
1.1	相关业务用例
BUC_FOSS_5.50.10_510（派送排单）
1.2	用例描述
排单员根据条件查询出符合排单要求的运单，
并将一个或多个运单与派送单号进行绑定操作、保存到系统。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	运单货物"已到达"最终库存部门
SUC-238《自动出入库》

后置条件	打印到达联	
SUC-448打印预派送单(预派送单)
1.4	操作用户角色
操作用户	描述
排单员	创建预派送单
1.5	界面要求
1.5.1	表现方式
Web页面 
1.5.2	界面原型
 
注：页面增加货物包装和运单体积（非尺寸），
原界面上体积修改为尺寸
创建预派送单主界面(图1)


 
注：页面增加货物包装和运单体积（非尺寸），
原界面上体积修改为尺寸

查询待排单的运单【子窗口】(图2)

 
提交预派送单成功对话框架（图3）
 
保存预派送单成功对话框架（图4）
1.5.3	界面描述
创建预派送单主界面(图1)
界面标题：创建派送单
查询待排单的运单(图2)界面标题：
查询待排单运单

创建派送单界面主要分为3个部分：
预排单信息区、
功能区、
信息统计区。
1.	预排单信息区：
派送单、
车辆、
司机、
待排单信息、
货物总重量、
总体积
a)	
派送单：
派送单号，
不可编辑，
系统生成。
文本框。
b)	
车辆：
弹出选择，
为公共选择框。
c)	
司机：
弹出选择，
为公共选择框。
d)	
待排单信息：
为列表框，
包括运单号、
货物名称、
重量|体积（长宽高）
|件数、
收货人、
联系方式、
收货地址、
收货习惯、
送货要求、
运输性质、
到达时间、
货物状态、
是否已联系客户、
预计送货时间、
是否异常、
是否需要发票、
付款方式、
备注、
送货方式
e)	
待排单信息中操作列：
i.	
单击某一行中此图标，
若此行未位于最底层，
则此行移至下一行位置
ii.
单击某一行中此图标，
若此行未位于最顶层，
则此行移至上一行位置
iii.
单击某一行中此图标，
系统删除此行
f)	
货物总重量：
系统计算出列表中各运单货物重量的总和。
g)	
总体积：
系统计算出列表中各运单货物的体总和。
2.	
功能区：
排单按钮、
提交、
保存。
3.	
信息统计区：
车辆、
车型、
司机、
可载重量、
可载体积、
已载重量、
已载体积、
对应预派送单号、
装载率、
到付总金额、
总票数、
总件数。
（详见信息统计数据元素）


查询待排单的运界面主要分为4个部分：查询条件区、运单信息列表区、功能按钮区、信息统计区。
1.	
查询条件区：
区域、
送货方式、
收货人名称、
收货人电话、
运单号、
是否已派单。
a)	
行政区域：
公共选择框。
b)	
定人定区：
公共选择框，
送货定人定区
c)	
送货方式：
下接列表框，
包括自提、
派送（默认全部）
d)	
收货人名称：
指运单信息中的收货人的姓名，
文本框。
e)	
收货人电话：
指运单信息中的运单收货人电话，
文本框。
f)	
运单号：
运单信息中的运单号，
文本框。
2.	
运单信息列表区：
运单号、
货物名称、
重量|体积（长宽高）|件数、
运输方式、
收货人、
联系方式、
收货地址、
收货习惯、
送货要求、
运输方式、
到达时间、
货物状态、
是否已联系客户、
预计送货时间、
是否异常、
是否需要发票、
付款方式、
备注、
送货方式。
3.	
功能按钮区：
确认按钮
a)	
确认按钮：
点击此按钮，
可以将查询条件勾选的运单信息带入至创建派送单界面的列表信息中。

需要提供的相关用例链接或提示信息：
a)	
通过派送排单菜单进入“创建派送单”界面(图1);
b)	
点击“排单”按钮后，弹出“查询待排单的运单”模态窗口形式打开(不允许操作后面的窗口)；
c)	
点击“提交”，
系统弹窗提示如图3;
d)	
点击“保存”，
系统弹窗提示如图4，
同时排单按钮变灰色，保存按钮变成“修改按钮”;

1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1		
用户根据查询货量的功能，
来确定要安排的司机与车辆，
然后在创建预派送单主界面(图1)中点击司机的选择框。	
预排单信息	
系统则弹出“司机选择框”。

2		
排单员根据条件找到对应的司机，点击确认。	
预排单信息	
系统将司机的姓名显示在预派送单主界面(图1)中的司机选择框中。
3		
排单员点击车辆选择框	预排单信息	
系统则弹出“车辆选择框”业务规则参照SR8
4		
排单员根据条件找到对应的车辆，
点击确认。	
预排单信息	
1.	
系统将车牌号显示在预派送单主界面(图1)中的车辆选择框中。
2.	
系统根据车牌号在排班和PDA绑定中查找对应司机，参见SR9
5		
排单员点击“排单”按钮		
系统弹出“查询待排单的运单”模态窗口形式打开(不允许操作后面的窗口)；业务规则参照：SR7
6		
排单员输入查询条件，点击“查询按钮”。
（查询规则限制详见SR1）	
查询待排单运单
运单信息结果列表	系统根据查询条件及后台过滤规则（后台过滤规则详见SR2），将符合条件的运单信息在查询待排单的运单(图2)中以列表的形式显示出来，并遵循排序规则。
（排序规则详见SR3）
SR11
7		
用户点击列表头中的“预计送货时间” 		
系统自动按预计送货时间排序（是升序还降序规则与上次排序相反）
8		
排单员勾选列表中的相应运单。 		
9		
排单员选择需要修改件数的运单，
双击表格中 “件数”		
表格进入件数进入修改编辑状态，
排单员输入件数，焦点失去后，
待排单运单件数变为输入件数
10		
排单员点击“确认”按钮		
a）	
系统自动将已勾选的运单信息及选择的货物件数信息带入至创建派送单界面的列表信息中。（带入规则见SR4）
b）	
系统修改已勾选的运单状态为“已排单”
c）	
系统自动创建（或更新）预派送单，并与列表中的运单绑定保存。预派送单的状态为"保存"。
d）	 
系统将界面的信息统计区信息更新。
11		
排单员点击“保存”	信息统计	a)	系统自动后台进行保存校验（详细校验规则见SR2）
b)	
系统自动创建预派送单，并与列表中的运单绑定保存。预派送单的状态为“保存”。
c)	
系统将界面的保存按钮变成“修改”按钮。
d)	
系统将界面的信息统计区信息更新。（更新规则详见SR5）
e)	
系统弹出图4
12		
排单员点击“提交”按钮		
a)	
系统后台自动进行校验（校验规则详见SR2）
b)	
系统自动判断，之前是否保存派送单，如果有，则直接将预派送单的状态改成“已提交”，并将列表中的运单号与派送单绑定。
c)	
系统将界面的信息统计区信息更新。（更新规则详见SR5）
d)	
系统后台数据保存后，系统弹出图3界面提示

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
4a	
选择的为车辆状态为不可用时		
系统提示“此车辆状态为不可用，是否继续”，
点击“是”关闭对话框，将牌号带创建派送单页面；
点击“否”关闭对话框，重新弹出“车辆选择框”。
8a	
勾选运单信息时，如果无通知记录		
系统则弹出对话框“此单无通知记录，是否继续派送？”。
8b	
如果勾选的运单，与排单员的部门（当前操作用户的部门）不是属于同一个区域。		
系统则弹出对话框“此单所属区域非您部门所在的区域，是否继续派送”
10a	
点击“确认”时，
如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。 		
系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
10b	
点击“确认”时，如果由于其他用户操作，导致未达到排单的条件（详见SR2）。		
系统提示“运单号***，此单货物状态发生了变更，是否剔除此运单？”。点击“是”，则从派单列表中剔除，点击“否”，则将光标提定位到该运单那行。
10c	
点击“确认”时，如果必送货未选择		
系统提示“必送货未排单，是否确认”。点击“确认”，则继续步骤10，点击“取消”，返回当前界面
11a
12a	
排单员点击“保存”或“提交”时，如果未有选车辆。		
系统提示“请选择车辆”
11b
12b	
排单员点击“保存”或“提交”时，如果未有运单货物。
系统则提示“待排单货物为空，请选择待排单货物！”
11c
12c	
点击“保存”或“提交”时，到付金额达到到付金额提示值时		
系统则弹出提示“到付金额已达到【配置项】元，是否继继派送？”
12d	点击“提交”按钮时，如果之前未保存操作		
系统则新增预派送单，并与列表中的运单绑定。

1.7	业务规则
序号	描述
SR1	
1）	
根据运单号精确查询运单，不受货物运单状态限制显示运单信息。
2）	
收货人名称、电话，精确查询到运单，不受货物运单状态限制显示运单信息。
3）	
行政区域来自于行政区域基础信息， 默认为全部，表示全部区域。
4）	
定人定区来自于送货定人定区基础信息，默认为全部，表示本部门负责的所有区域。
5）	
送货方式包括派送、自提，默认为全部，表示两种送货方式都包括，这里的送货方式与运单开单时的送货方式对应
SR2	
1）	
货物已到达。
2）	
派送状态为“待排单”的运单。
3）	
无异常状态为未处理的派送联运单对应的件数。
4）	
无更改单未受理的运单；
5）	
送货日期在查询范围内（当前日期或当前日期之后）
6）	
运单的代收货款小于XX金额（XX在综合配置）
SR3	
1）	
运单的运输方式排序，按照必送货（通知客户中确定）、精准、普货优先级依次排序，必送货中再按照先精准后普货的优先级排序
2）
	货物到达时间排序，到达越久排序越靠前
3）
	按运单中记录的派送方式排序（派送排序在先，自提排序在后）
以上排序方式优先顺序依次为1）、2）、3）
SR4	
1）	
查询待排单的运单界面中的货物信息列表与创建预派送单界面中的待排单信息列表一一对应
2）
如果排单货物的件数与开单的件数不同，系统在运单号旁边加 标识此行
3）	
排单件数与库存件数不同，系统用红色标识此行
4）	
待排单件数为"已到达"件数减去"已排单"件数

SR5	
信息显示区中，货物统计信息计算规则：
1）	
货物总重量=预派送单中所有运单货物重量累加；
2）	
货物总体积=预派送单中所有运单货物体积累加
3）	
总票数=预派送单中运单数量
4）	
总件数=预派送中所有运单件数总和
5）	
到付总金额=预派送单中所有运单的到付金额之和（超出提示值红色标识）
信息显示区中，车辆统信息计算规则：
1)	
可载重量=车辆可载重-预派送单货物总重量；
2)	
可载体积=车辆可载体积-预派送单货物总体积；
3)	
系统支持一车同时对应多派送单，车辆剩余可载重量及车辆剩余可载体积应减去其他派送单所占用重量、体积；
4)	
装载率为车辆的重量装载率和体积装载率
SR6	
预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001



SR8	
车辆查询范围为:排单员所在部门对应车队的接送货车辆、借车车辆、外请车。
SR9	
1.	
若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
2.	
若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
3.	
当排班和PDA绑定同时存在时，以PDA绑定为准
SR10	
必送货用颜色粉红色标识
SR11	
图2查询条件中的定人定区增加“未分配”选项，过滤出调用GIS接口后没有匹配定人定区的运单

1.8	
数据元素
1.8.1	
预排单信息
字段名称 	说明 	
输入限制	输入项提示文本	长度	是否必填	备注
 
派送单	派送单号	文本		20	是	系统根据规则自动生成（SR6）
司机	指定派送单对应的司机	公共选择框	请选择司机	20	否	来自于统一维护的司机基础信息
车辆	指定派送单对应的车辆	公共选择框	请选择车辆	20	是	来自于统一维护的车辆基础信息,包括公司车与外请车
运单号	运单信息中的运单号	文本		8	是	来自于运单信息
货物名称	运单信息中的货物名称	文本		20	是	
件数	选择派送的件数	文本		3	是	为全数字，且大于0.
重量	选择派送件的总重量	文本		10	是	小数点保存2位，且大于0.
包装	运单的包装	文本				
体积	运单的总体积	文本				
尺寸
选择派送件的总体积	文本		8	是	用长*宽*高表示
运输方式	运单信息中的运输方式	文本		20	是	
收货人	运单信息中的收货人信息	文本		20	是	
联系方式	运单信息中的收货人联系固定电话/手机	文本		20	是	固定电话与手机之间用“/”分开
收货地址	运单信息中的收货人地址	文本		50	是	
收货习惯	CRM中维护的收货习惯	文本			否	
送货要求	通知客户时的送货要求，读取最新的	文本		50	是	
到达时间	货物到达时间	文本		6	是	
货物状态	货物的库存状态	文本		20	是	“库存中”、“到达”
是否已联系客户	是否已进行送货通知	文本		2	是	包括“是”、“否”
预计送货时间	派送预计的送货时间，送货通知客户时记录的。	时间		2	否	20
是否异常	派送异常信息中是否有无处理的此运单记录	文本		2	是	“是”
“否”
是否需要发票	客户是否需要发票，送货通知客户时记录的。	文本		2	是	“是”
“否”
付款方式	运单信息中的付款方式	文本		20	是	
备注	运单信息中的备注信息	文本		100	否	
送货方式	运单信息中的送货方式	文本		20	是	


1.8.2	
查询待排单运单
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
行政区域	行政区域	公共选择框			否	区域信息来自于区域基础资料。
定人定区	送货定人定区	公共选择框				
送货方式	运单信息中的送货方式	下拉框			否	包括自提、派送（默认全部）
收货人名称	运单信息中的收货人名称	文本框			否	
收货人电话	指运单信息中的运单收货人电话	文本			否	
运单号	指运单信息中的运单号	文本	“请输正确的运单号”		否	全数字且8位。


1.8.3	
运单信息结果列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	运单信息中的运单号	文本		8	是	来自于运单信息
货物名称	运单信息中的货物名称	文本		20	是	
件数	选择派送的件数	文本		3	是	为全数字，且大于0.
重量	选择派送件的总重量	文本		10	是	小数点保存2位，且大于0.
体积	选择派送件的总体积	文本		8	是	用长*宽*高表示
运输方式	运单信息中的运输方式	文本		20	是	
收货人	运单信息中的收货人信息	文本		20	是	
联系方式	运单信息中的收货人联系固定电话/手机	文本		20	是	固定电话与手机之间用“/”分开
收货地址	运单信息中的收货人地址	文本		50	是	
收货习惯	CRM中维护的收货习惯	文本			否	
送货要求	通知客户时的送货要求，读取最新的	文本		50	是	
到达时间	货物到达时间	文本		6	是	
货物状态	货物的库存状态	文本		20	是	“库存中”、“到达”
是否已联系客户	是否已进行送货通知	文本		2	是	包括“是”、“否”
预计送货时间	派送预计的送货时间，送货通知客户时记录的。	时间		2	否	20
是否异常	派送异常信息中是否有无处理的此运单记录	文本		2	是	“是”
“否”
是否需要发票	客户是否需要发票，送货通知客户时记录的。	文本		2	是	“是”
“否”
付款方式	运单信息中的付款方式	文本		20	是	
备注	运单信息中的备注信息	文本		100	否	
送货方式	运单信息中的送货方式	文本		20	是	

1.8.4	
信息统计区
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
车辆	预派送单对应的车牌号	文本				
车型		文本				
司机	预派送单对应的司机姓名	文本				
对应预派送单	车辆被派送单（可以多个）	文本				
可载重量	选择的车辆的可载重量	文本				
可载体积	选择的车辆的可载体积	文本				
总重量	排单的总重量	文本				
总体积	排单的总体积	文本				
装载率（重量/体积）	选择的车辆的装载率	文本				
到付总金额	预排单中的运单的到付金额总和	文本				
总票数		文本				
总件数		文本				

1.9	
非功能性需求
使用量	100W运单，按每次派送20单算，每天创建预派送单5W次。
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	3S
使用时间段	全天
高峰使用时间段	10:00-12:00   14:00-17:00

1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
获取客户信息
Foss综合子系统	获取收货客户的收货习惯

*************************************************************************************

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-22 	
新增	王辉	V0.1
2012-7-19	
根据评审意见：
1.	加入总体积
2.	加入查看装车人功能	
王辉	V0.3
2012-07-24	
业务评审完毕，
升至0.9	王辉	V0.9
2012-10-13	
根据RC-125修订	
王辉	
2012-10-13	
根据RC-631修订	
王辉	
2012-10-13	
根据RC-232修订	
王辉	
2012-10-13	
根据RC-486修订	
王辉	
2012-12-7	
根据ISSUE-1057修订	
王辉	
2013-2-1	
根据ISSUE-1692修订	
王辉	

1.	
SUC-459-确认_取消预派送单
1.1	
相关业务用例
BUC_FOSS_5.50.10_520  
确认派送
1.2	
用例描述
排单员确认、
取消预派送单。
1.3	
用例条件
条件类型	
描述	引用系统用例
前置条件	
1.	
预派送单已装车	
后置条件		
1.4	
操作用户角色
操作用户	描述
排单员	
排单员确认、
取消本部门预派送单
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
图1 预派送单明细
 
图2 差异明细
 
图3扫描明细
 
图4 查询公司司机
 
 
图5 确认到达联件数
 
图6 默认通知客户
 
图7 查看装车人
1.5.3	
界面描述
图1界面标题：
查看预派送单
界面主要分为预派送单信息、
排单信息、
装车信息和功能按钮
1. 预派送单信息：
预派送单号、
车辆、
车型、
可载重量、
可载体积、
已载重量、
已载体积、
装载率、
到付总金额、
总票数、
总件数
a)	
预派送单号
b)	
车辆
c)	
车型
d)	
可载重量：
车辆剩余可载重量
e)	
可载体积：
车辆剩余可载体积
f)	
已载重量
g)	
已载体积
h)	
装载率：
分为重量装载率和体积装载率
i)	
到付总金额：
到付总金额，
超过限定值时，
显示红色
j)	
总票数
k)	
总件数
l)	
司机：可以修改

2. 
排单信息：
运单号、
货物名称、
重量、
体积、
排单件数、
收货人、
联系方式、
收货地址、
收货人注意事项、
预计送货时间、
是否已联系客户

3. 
装车信息分为3个部分装车差异报告、
差异统计信息、
装车任务明细和功能按钮
3.1 
装车差异报告：
运单号、
差异类型、
预装车件数、
实际装车件数、
少货件数、
运输性质、
备注
3.2 
差异统计信息：
预计装车总件数、
实际装车总件数和少货总件数
3.2 
装车任务明细：
出发部门、
到达部门、
运单号、
运输性质、
库存件数、
已操作件数、
库存重量、
库存体积、
货名、
包装
3.3 
功能按钮：
查看历史装车差异报告、
确认装车报告、
退回装车报告
a)	
查看历史装车差异报告：
点击超链接，跳转到“查询装车差异报告”界面，
参见“查询派送装车差异报告（SUC-601）”系统用例	
b)	
退回装车报告：
点击此按钮，
退回装车报告
c)	
查看装车人：
点击超链接，
弹出“查看装车人”界面。

4. 
功能按钮：
确认预派送单、
取消预派送单
a)	
确认：
点击此按钮，
确认预派送单
b)	
取消：
点击此按钮，
取消预派送单

图2界面标题：
差异明细，
数据元素参考“少货明细列表”，
排单员点击“运单号”，
弹出此界面，显示少货明细

图3界面标题：
扫描明细, 数据元素参考“流水号明细列表”，
排单员点击“运单号”，
弹出此界面，显示扫描明细

图4界面标题：
查询司机
查询公司司机界面分为2个部分：
查询条件、
查询结果列表
1.	
查询条件包括：
a）	
工号
b）	
姓名
c）	
电话号码
2.	
查询结果列表包括操作列和结果信息
a) 
操作列：
 表示确认，
 点击后提示“是否确认分配给该司机？”
b）
结果信息：
参考公司司机数据元素

图5
到达联件数不一致界面分为2个部分：
不一致列表、功能按钮
1.	
不一致列表包括：
a)	
运单号
b)	
排单件数
c)	
到达联件数
d)	
状态：包括未处理、已确认、已重打 
2.	
功能按钮包括确认、重打
a)	
确认：排单员点击确认，系统更新到达联
b)	
重打：
排单员点击重打，
系统打印已勾选的运单到达联并作废重新生成到达联。
库存件数不一致界面包括不一致列表
1. 
不一致列表包括
a)	
运单号
b)	
排单件数
c)	
库存件数

图6界面标题：
通知客户
通知客户界面分为2个部分：
待通知列表、
功能按钮
1.	
待通知列表包括：
a)	
运单号
b)	
收货人
2. 
功能按钮包括确认、取消
a)	
确认：排单员点击确认，系统将待通知列表中运单自动通知客户（短信）
b)	
取消：排单员点击取消，系统不做任何操作

图7界面标题：
装车人
界面主要包括：
工号、
姓名、
加入时间、
退出时间
1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1		
排单员查看预派送单信息、
装车信息，
预派送单信息、
排单信息、
差异报告明细列表、
装车明细列表	

2		
排单员选择司机	
公司司机查询条件、
公司司机查询结果列表	
系统弹出界面图4，参见SR7
3		
排单员点击“ ”	公司司机查询条件、
公司司机查询结果列表	司机信息载入“查看预派送单”界面中的司机公共选择框
4		
排单员点击“确认”
1.	
参考SR2
2.	
系统自动根据差异，将不存在于装车报告中的运单从预派送单中移除
3.	
派送单中必送货未装车，系统提示“必送货：XXXX未装车！是否确认预派送单？，参考SR1
4.	
系统修改预派送单状态为“已确认”
5.	
调用中转更新装车任务状态接口
6.	
系统更新预派送单司机信息
5		
排单员点击“取消”
1.	
系统修改派送单状态为“已取消”
2.	
调用中转更新装车任务状态接口，参见SR8
3.	
系统调用中转接口生成卸车任务，参考SR4

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
排单员查看预派送单信息、装车信息，不认可装车，点击“退回装车报告”	
预派送单信息、排单信息、差异报告明细列表、装车明细列表	
1.	调用中转更新装车任务状态接口

4a
系统判断排单信息中运单件数与已生成到达联是否一致，若不一致，系统弹出界面如图6		
1.	
参考SR5，SR6
2.	
排单员勾选需确认运单，点击“确认”，系统标记已确认，系统更新运单对应的到达联
3.	
排单员点击“重打”，系统标记已重打，系统打印列表中运单到达联， 系统作废运单已生成的对应的到达联并重新生成到达联
4b
系统判断排单信息中运单是否已生成到达联， 		
若未生成，则系统生成排单信息中运单对应的到达联
4c
排单信息中存在运单未通知客户，则系统弹出提示框图6		
1.	
排单员点击“确认”，系统自动生成短信(模板待定)通知客户
2.	
排单员点击“取消”，继续步骤4
5a	
预派送单状态为“装车中”，系统提示“装车中不允许取消预派送单！”		

1.7	
业务规则
序号	描述
SR1	
必送货为通知客户时标识

SR2	
车辆实际重量装载率或车辆实际体积装载率（当一车多个派送单情况时，以车辆总的重量装载率和体积装载率为准）没有达到60%，
系统提示“重量装载率未达到60%，是否确认预派送单？”或“体积装载率未达到60%，是否确认预派送单？”，60%提供参数配置，可部门设置。
SR3	
中转在理货员提交装车任务后，系统会发送站内消息，提醒排单部门确认派送单。
排单员在"确认派送单"页面可以查询差异报告。

SR4	
已装车和已确认状态下的预派送单取消后生成卸车任务

SR7	
1.	
若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
2.	
若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
3.	
当排班和PDA绑定同时存在时，以PDA绑定为准
SR8	
1.	
未确认派送单，调用中转接口
2.	
已确认派送单，不调用

1.8	
数据元素
1.8.1	
预派送单信息
字段名称 	说明 	
输入限制	输入项提示文本	长度	是否必填	备注
预派送单号		文本				
车辆		文本				
车型		文本				
可载重量		文本				
可载体积		文本				
已载重量		文本				
已载体积		文本				
装载率		文本				
到付总金额		文本				
总票数		文本				
总件数		文本				

1.8.2	排单信息
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
运单号		文本				
排单件数		文本				
货物名称		文本				
重量		文本				
体积		文本				
收货人		文本				
联系方式		文本				
收货地址		文本				
送货要求		文本				
预计送货时间		文本				
是否已联系客户		文本				

1.8.3	
差异报告明细列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
运单号	货物的运单编号	文本				
预装车件数	预装车件数	文本				
实际装车件数	实际装车件数	文本				
运输性质	货物运输类型	文本				
差异类型	少货	文本				
备注	PDA装车扫描时对运单的备注	文本				

1.8.4	
装车明细列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
出发部门	交接单/配载单出发部门	文本				

到达部门	交接单/配载单到达部门	文本			是	
运单号	运单号	文本			是	无
运输性质	运输性质	文本			是	无
库存件数	库存件数	文本				
已操作件数	已操作件数	文本			是	
库存重量	开单重量*库存件数/开单件数	文本			是	无
库存体积	开单体积*库存件数/开单件数	文本			是	无
货名	货名	文本			是	无
包装	包装	文本			是	

1.8.5	
公司司机查询条件
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
工号		文本		10		
司机姓名		文本		10		
电话号码		文本		20		

1.8.6	
公司司机查询结果列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
工号		文本		10		
司机姓名		文本		10		
电话号码		文本		20		
部门		文本		10		

1.8.7	
少货明细列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
流水号	流水号	文本				
扫描状态	已扫描、未扫描	文本				
扫描时间	扫描时间	日期				
货物状态	未装车、强装-有更改	文本				

1.8.8	
流水号明细列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
流水号	运单流水号	文本			是	无
扫描状态	已扫描、未扫描	文本			是	无
货物状态	正常、未装车、多货-夹带、强装-异地夹带、强装-有更改、强装-未打包装、强装-贵重物品未出库、强装-带打包装未出库，强装-未预配	文本				
是否装车	是否装车	文本			是	在货物状态为正常、多货-夹带时是否装车为是，否则为否
操作时间	扫描时间	数字			是	

1.8.9	
装车人
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
工号		文本			是	
姓名		文本			是	
加入时间		文本			是	
退出时间		数字			是	

1.9	
非功能性需求
使用量	100W运单，按每次派送20单算，每天确认/取消5W次。
2012年全网估计用户数	10000用户
响应要求（如果与全系统要求 不一致的话）	3s内响应
使用时间段	07:00-10:00
高峰使用时间段	07:00-10:00

1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		

*************************************************************************************		
	
修订记录 
日期 	
修订内容 	修订人员 	版本号 
2012-05-31 	
新增
熊勇	V0.1
2012-7-2	
根据评审意见修改	
王辉	V0.2
2012-7-20	
1.	后置条件去掉
2.	扩展流程2a删掉
3.	增加到达联已打印判断	
王辉	V0.3
2012-7-24	
业务评审完毕，升至0.9	
王辉	V0.9

1.	
SUC-449 –打印到达联(排单)
1.1	
相关业务用例
BUC_FOSS_5.50.10_510（派送排单）
1.2	
用例描述
排单员查询预派送排单可以通过系统一次性全部打印到达联
1.3	
用例条件
条件类型	描述	引用系统用例
前置条件	预派送单为已提交状态	
SUC-447（创建预派送单）
后置条件		
1.4	
操作用户角色
操作用户	
描述
排单员	
打印到达联
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
查询预派送单
（图1）
 
根据预派送单打印到达联
（图2）

1.5.3	
界面描述
打印到达联界面标题：
根据预派送单打印到达联

创建派送单界面主要由派送单主信息区、到达联信息区。
1.	
派送单主信息区：
派送单编号、
车牌号、
司机。
2.	
到达联信息区：
包括运单号、
货物名称、
件数、
重量、
体积（长宽高）、
到达时间、
运输方式、
收货人、
联系方式、
收货地址、
收货人注意事项、
货物状态、
是否已联系客户、
预计送货时间、
是否异常、
是否需要发票、
付款方式、
备注、
送货方式、
排单状态

需要提供的相关用例链接或提示信息：
a)	
用户通过点击“查询预派送单(图1)”中结果列表中的到达联打印图标，
系统则弹出“根据预派送单打印到达联（图2）”界面。
b)	
用户点击打印图标或图2中的打印按钮，
则弹出打印模板选择框进行选择确认。
c)	
选择模板后点击打印按钮，
系统则跳转提示“打印中…”。
d)	
打印成功后，系统则提示“打印成功！”

1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1	
用户通过点击“查询预派送单(图1)”中结果列表中的到达联打印图标。		
a)	
系统则弹出“根据预派送单打印到达联（图2）”界面。
b)	
系统将界面的初始化数据。（初始化规则详见SR1）。
2	
用户点击“打印”按钮或点击到达联信息列表中操作列的“ ”图标。		
系统弹出打印模板选择框进行选择确认，参考SR2
3	
用户选择模板，点击确认。		
系统则打印未发生变更的到达联。
（具体打印规则请参见系统用例SUC-530-打印到达联）

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
2a	
运单发生变更，系统弹窗提示“运单XXXX发生变更，请受理后再打印！”		
用户点击确定，系统在图2界面中自动将发生变更的运单颜色标识，参考SR2
2b	
到达联已打印过，系统弹窗提示 		
用户点击确定
1.	
重新打印到达联
2.	
系统作废已有到达联并重新生成到达联
用户点击取消
1.	
系统不做操作
3a	
如果打印机不成功		
由操作系统提示不成功原因

1.7	
业务规则
序号	描述
SR1	
1）	
将预排单编号、
司机姓名、
车牌号从预派送单信息中读取出来，
并按图1的形式初始化。
2）	
预派送单绑定的运单货物信息以列表的形式显示出来
（图1到达联信息区）。
（详细数据元素请见**）
SR2	
界面中发生变更的 运单用红色标识

1.8	
数据元素
1.8.1	
到达联信息
字段名称 	说明 	
输入限制	输入项提示文本	
长度	是否必填	备注
 
派送单	
派送单号	文本		
20	
是	
系统根据规则自动生成（SR6）
司机	指定派送单对应的司机	
公共选择框	请选择司机	20	否	来自于统一维护的司机基础信息
车辆	指定派送单对应的车辆	公共选择框	请选择车辆	20	是	来自于统一维护的车辆基础信息,包括公司车与外请车
运单号	运单信息中的运单号	文本		8	是	来自于运单信息
货物名称	运单信息中的货物名称	文本		20	是	
件数	选择派送的件数	文本		3	是	为全数字，且大于0.
重量	选择派送件的总重量	文本		10	是	小数点保存2位，且大于0.
体积	选择派送件的总体积	文本		8	是	用长*宽*高表示
运输方式	运单信息中的运输方式	文本		20	是	
收货人	运单信息中的收货人信息	文本		20	是	
联系方式	运单信息中的收货人联系固定电话/手机	文本		20	是	固定电话与手机之间用“/”分开
收货地址	运单信息中的收货人地址	文本		50	是	
送货要求	通知客户时的送货要求	文本		50	是	
货物状态	货物的库存状态	文本		20	是	“库存中”、“在途中”
是否已联系客户	是否已进行送货通知	文本		2	是	包括“是”、“否”
预计送货时间	派送预计的送货时间，送货通知客户时记录的。	时间		2	否	20
是否异常	派送异常信息中是否有无处理的此运单记录	文本		2	是	“是”
“否”
是否需要发票	客户是否需要发票，送货通知客户时记录的。	文本		2	是	“是”
“否”
付款方式	运单信息中的付款方式	文本		20	是	
备注	运单信息中的备注信息	文本		100	否	
送货方式	运单信息中的送货方式	文本		20	是	


1.9	非功能性需求
使用量	
100W运单，按每次派送20单算，
每天创建预派送单5W次，打印5W次。
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	
3S
使用时间段	全天
高峰使用时间段	
10:00-12:00   14:00-17:00

**************************************************************************************************

修订记录 
日期 	
修订内容 	修订人员 	版本号 
2012-05-31 	
新增	
熊勇	V0.1
2012-7-2	
根据评审意见修改	
王辉	V0.2
2012-7-20	
1.	
后置条件去掉
2.	
扩展流程2a删掉
3.	
增加到达联已打印判断	
王辉	V0.3
2012-7-24	
业务评审完毕，
升至0.9	王辉	V0.9

1.	
SUC-449 –打印到达联(排单)
1.1	
相关业务用例
BUC_FOSS_5.50.10_510（派送排单）
1.2	
用例描述
排单员查询预派送排单可以通过系统一次性全部打印到达联
1.3	
用例条件
条件类型	描述	引用系统用例
前置条件	
预派送单为已提交状态	
SUC-447（创建预派送单）
后置条件		
1.4	
操作用户角色
操作用户	
描述
排单员	
打印到达联
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
查询预派送单（图1）
 
根据预派送单打印到达联（图2）

1.5.3	
界面描述
打印到达联界面标题：
根据预派送单打印到达联

创建派送单界面
主要由派送单主信息区、
到达联信息区。
1.	
派送单主信息区：
派送单编号、
车牌号、
司机。
2.	
到达联信息区：
包括运单号、
货物名称、
件数、
重量、
体积（长宽高）、
到达时间、
运输方式、
收货人、
联系方式、
收货地址、
收货人注意事项、
货物状态、
是否已联系客户、
预计送货时间、
是否异常、
是否需要发票、
付款方式、
备注、
送货方式、
排单状态

需要提供的相关用例链接或提示信息：
a)	
用户通过点击“查询预派送单(图1)”中结果列表中的到达联打印图标，
系统则弹出“根据预派送单打印到达联（图2）”界面。
b)	
用户点击打印图标或图2中的打印按钮，则弹出打印模板选择框进行选择确认。
c)	
选择模板后点击打印按钮，系统则跳转提示“打印中…”。
d)	
打印成功后，系统则提示“打印成功！”

1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1	
用户通过点击“查询预派送单(图1)”中结果列表中的到达联打印图标。		
a)	
系统则弹出“根据预派送单打印到达联（图2）”界面。
b)	
系统将界面的初始化数据。（初始化规则详见SR1）。
2	
用户点击“打印”按钮或点击到达联信息列表中操作列的“ ”图标。		
系统弹出打印模板选择框进行选择确认，参考SR2
3	
用户选择模板，点击确认。		
系统则打印未发生变更的到达联。
（具体打印规则请参见系统用例SUC-530-打印到达联）

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
2a	
运单发生变更，系统弹窗提示“运单XXXX发生变更，请受理后再打印！”	
用户点击确定，系统在图2界面中自动将发生变更的运单颜色标识，参考SR2
2b	
到达联已打印过，
系统弹窗提示 		
用户点击确定
1.	
重新打印到达联
2.	
系统作废已有到达联并重新生成到达联
用户点击取消
1.	
系统不做操作
3a	
如果打印机不成功		
由操作系统提示不成功原因

1.7	
业务规则
序号	描述
SR1	
1）	
将预排单编号、
司机姓名、
车牌号从预派送单信息中读取出来，
并按图1的形式初始化。
2）	
预派送单绑定的运单货物信息以列表的形式显示出来
（图1到达联信息区）。
（详细数据元素请见**）
SR2	
界面中发生变更的 运单用红色标识

1.8	
数据元素
1.8.1	
到达联信息
字段名称 	说明 	
输入限制	输入项提示文本	
长度	是否必填	备注
 
派送单	
派送单号	文本		
20	是	系统根据规则自动生成（SR6）
司机	指定派送单对应的司机	公共选择框	请选择司机	20	否	来自于统一维护的司机基础信息
车辆	指定派送单对应的车辆	公共选择框	请选择车辆	20	是	来自于统一维护的车辆基础信息,包括公司车与外请车
运单号	运单信息中的运单号	文本		8	是	来自于运单信息
货物名称	运单信息中的货物名称	文本		20	是	
件数	选择派送的件数	文本		3	是	为全数字，且大于0.
重量	选择派送件的总重量	文本		10	是	小数点保存2位，且大于0.
体积	选择派送件的总体积	文本		8	是	用长*宽*高表示
运输方式	运单信息中的运输方式	文本		20	是	
收货人	运单信息中的收货人信息	文本		20	是	
联系方式	运单信息中的收货人联系固定电话/手机	文本		20	是	固定电话与手机之间用“/”分开
收货地址	运单信息中的收货人地址	文本		50	是	
送货要求	通知客户时的送货要求	文本		50	是	
货物状态	货物的库存状态	文本		20	是	“库存中”、“在途中”
是否已联系客户	是否已进行送货通知	文本		2	是	包括“是”、“否”
预计送货时间	派送预计的送货时间，送货通知客户时记录的。	时间		2	否	20
是否异常	派送异常信息中是否有无处理的此运单记录	文本		2	是	“是”
“否”
是否需要发票	客户是否需要发票，送货通知客户时记录的。	文本		2	是	“是”
“否”
付款方式	运单信息中的付款方式	文本		20	是	
备注	运单信息中的备注信息	文本		100	否	
送货方式	运单信息中的送货方式	文本		20	是	


1.9	
非功能性需求
使用量	100W运单，
按每次派送20单算，
每天创建预派送单5W次，打印5W次。
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	
3S
使用时间段	
全天
高峰使用时间段	1
0:00-12:00   14:00-17:00

1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无			
		
******************************************************************************

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-31 	
新增	
熊勇	V0.1
2012-7-2	
根据评审建议修改	
王辉	V0.2
2012-7-19	
打印列表增加包装和是否有签收单	
王辉	V0.3
2012-07-24	 
业务评审完毕，升至0.9	
王辉	V0.9

1.	
SUC-448 –打印预派送单(排单)
1.1	
相关业务用例
BUC_FOSS_5.50.10_510
（派送排单）
1.2	
用例描述
排单员根预派送单查询功能查询出预派送单信息，
然后点击打印预派送单；
或者排单员在预派送单提交后在直接点击选择打印预派送单，
将派送单信息打印出来，方便后续跟踪处理。
1.3	
用例条件
条件类型	描述	引用系统用例
前置条件	预派送单为已提交状态	
SUC-447（创建预派送单）
后置条件		
1.4	
操作用户角色
操作用户	描述
排单员	
打印预派送单
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
查询预派送单（图1）
 
提交预派送单提示信息（图2）

 
打印预派送单（图3）
1.5.3	
界面描述
打印预派送单界面标题：打印预派送单

创建派送单界面主要由打印信息区、功能区组成。
1.	
打印信息区：
标题（预派送单据）、
派送单编号、
车牌号、
派送部、
司机姓名、
司机电话号码、
车队、
列表信息
（运单号、货物名称、包装、签收单、重量/体积/件数、到付金额、运输方式、收货人/联系方式/收货人地址、送货要求、总重量、总体积、总票数、总件数、总到付金额、装载率(重量/体积)。
2.	
功能区：
打印按钮、关闭按钮。
a)	
打印：
点击打印按钮，系统打印派送单信息区的信息。
b)	
关闭：
点击此按钮系统，关闭此界面


需要提供的相关用例链接或提示信息：
a)	
用户通过点击“查询预派送单(图1)”中结果列表中的预派送单打印图标，系统则弹出“打印预派送单”界面（图3）。
b)	
用户通过点击“提交预派送单提示信息（图2）”中的打印按钮，系统则弹出“打印预派送单”界面（图3）；
c)	
点击打印，系统关闭此窗口，并进行打印；

1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1	
用户通过点击“提交预派送单提示信息（图2）”界面中的是按钮，或点击“查询预派送单(图1)”中结果列表中的预派送单打印图标。	打印信息	系统弹出“打印预派送单”界面。
并初始化界面信息。（初始化规则详见SR1）
2	
用户点击“打印”按钮		
a)	
关闭“打印预派送单”界面。
b)	
系统调用打印机进行打印操作，参考SR2,SR3，SR4

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
用户点击“关闭”按钮		系统关闭打印界面
2a	
如果打印机不成功		由操作系统提示不成功原因

1.7	
业务规则
序号	描述
SR1	
1）	
将提交的派送单信息：派送单编号、车牌号、派送部、司机姓名、司机电话号码、车队及绑定货物信息（运单号、货物名称、重量/体积/件数、运输方式）及收货人信息（收货人/联系方式/收货人地址、送货要求）加载至打印预派送单界面。
2）	
送货要求为通知客户时记录，显示最新的送货要求。
SR2	
系统按A4横板进行打印
SR3	
当有多页时，每都必须打印列表信息上面的标题（预派送单据）、派送单编号、车牌号、派送部、司机姓名、司机电话号码、车队、总重量、总体积、总票数、总件数、总到付金额、装载率（重量/体积）。如下图： 
 
SR4	
到付金额超出阈值，加粗打印

1.8	
数据元素
1.8.1	
打印信息
字段名称 	说明 	
输入限制	输入项提示文本	长度	是否必填	备注
派送单编号	提交的预派送单的编号			20	是	
车牌号	预派送单中保存的车牌号			10	是	
派送部	排单的派送部			20	是	
司机姓名	预派送单中的司机姓名			10	否	
司机电话号码	司机对应的手机号			11	否	司机信息中维护的电话号码
车队	承接货物的车队					
运单号	预派送单中绑定的运单号			8	是	
货物名称	运单信息中货物名称			20	是	
包装	货物包装			20	是	
签收单	是否有签收单			5	是	
重量/体积/件数	预派送单绑定某运单对应件的总重量/总体积/件数			20	是	
到付金额	运单到付金额			10	是	
运输方式	运单信息中的运输方式			5	是	
收货人/联系方式/收货人地址	预派送单信息中保存的运单货物的收货人/联系方式/地址			20	是	从预派送单中读取
送货要求	通知客户时的送货要求			20	是	
总重量	货物总重量			10	是	
总体积	货物总体积			10	是	
总票数	货物总票数			10	是	
总件数	货物总件数			10	是	
总到付金额	总到付金额			10	是	
装载率	车辆的装载率			10	是	
1.9	
非功能性需求
使用量	
100W运单，按每次派送20单算，每天创建预派送单5W次，打印5W次。
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	
3S
使用时间段	
全天
高峰使用时间段	
10:00-12:00   
14:00-17:00

1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		
		
*************************************************************************

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-22 	
新增	
王辉	V0.1
2012-7-2	
根据评审建议修改	
王辉	V0.2
2012-07-24	
 业务评审完毕，升至0.9	
 王辉	V0.9
  	  	  	 
1.	
SUC-458-查询_修改预派送单
1.1	
相关业务用例
BUC_FOSS_5.50.10_520  
确认派送
1.2	
用例描述
排单员在确认派送过程中，
查询、修改预派送单。
1.3	
用例条件
条件类型	描述	引用系统用例
前置条件
1.	预派送单已创建	
1.	创建预派送单（SUC-447）
后置条件		
1.4	
操作用户角色
操作用户	描述
排单员	
排单员查询、
修改本部门预派送单
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
1.5.3	
界面描述
界面标题:预派送单处理
界面主要分为2个部分查询条件区域、
查询结果列表。
1.	
查询条件区域：
预派送单号、
车辆、
司机姓名、
预排单状态、
提交时间
a)	预派送单号
b)	车辆
c)	司机姓名
d)	预排单状态
e)	提交时间
f)	查询按钮
g)	重置按钮


2.	查询结果列表包括2个部分：操作列和数据信息
2.1.	
操作列：
派送单操作、
打印
a）	
派送单操作：
	 ：修改预派送单，弹出“创建预派送单”界面，界面加载对应预派送单信息
	 ：弹出“预派送单明细”界面，进行确认和取消预派送单操作，参见“确认_取消预派送单（SUC-459）”
b）	
打印预派送单
	 ：打印预派送单，参见“打印预派送单（排单）(SUC-448)”
c）	
打印到达联
	 ：打印到达联，参见“打印到达联（排单）(SUC-449)”
d）	
分配任务
	 ：分配派送任务给司机，参见“分配派送任务（SUC-460）”
2.2.	
数据信息参见数据元素“派送单信息”


1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1	
排单员输入查询条件，点击“查询”按钮	查询条件、派送单信息	
1.	
参考SR1
2.	
查询结果列表加载符合条件的预派送单，参考SR2
2	
排单员选择查询结果列表中一条派送单，点击派送单操作“ ”修改当前派送单		弹出“创建派送排单”界面，系统根据选择的派送单号加载预派送单信息，包括待排单信息、车辆、司机、预派送单号和统计信息
3	
排单员修改预派送单		
1.	
修改步骤参见“创建预派送单（SUC-447）”系统用例
2.	
系统标记预派送单为修改状态，不能建立装车任务
3.	
参考SR3
4	
排单员点击“提交”按钮		系统提示“是否确认修改预派送单？”
5	
排单员点击“是”		
1.	
系统提示“修改预派送单成功！”
2.	
更新预派送单
6	
排单员选择查询结果列表中一条派送单，点击派送单操作“ ” 		
弹出“预派送单明细”界面，参见“确认_取消预派送单（SUC-459）”
7	
排单员选择查询结果列表中一条派送单，点击 “ ”打印预派送单		
系统打印预派送单，参见“打印预派送单（排单）(SUC-448)”
8	
排单员选择查询结果列表中一条派送单，点击 “ ”打印到达联		
系统打印到达联，参见“打印预派送单（排单）(SUC-449)”

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
5a	
排单员点击“否”，系统返回“创建预派送单”界面		

1.7	
业务规则
序号	描述
SR1	
查询条件中提交时间默认显示当天0:00-24:00，时间可按部门配置
SR2	
1.	
预派送单状态为“已确认”和“已取消”，则“ ” “ ”，“ ”不可见
2.	
预派送单状态非“已装车”，则“ ”不可见
SR3	
1.	
预派送单已建立装车任务：预派送单修改只允许增加排单信息，不允许删减排单信息且增加的排单信息顺序不可调整
2.	
预派送单未建立装车任务：预派送单修改无限制

1.8
数据元素
1.8.1	
查询条件
字段名称 	说明 	
输入限制	输入项提示文本	长度	是否必填	备注
预派送单号		文本		10	否	
车辆		文本		10	否	
司机姓名		文本		10	否	
预排单状态		下拉框		5	否	数据字典，包含已保存、已提交、装车中、已装车、已确认、已取消
提交时间		日期选择		6	是	默认为当天0:00至24:00

1.8.2	
派送单信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
预派送单号		文本		10		
车辆		文本		10		
司机姓名		文本		10		
预排单状态		文本		5		
提交人		文本		10		
提交时间		文本		6		


1.9	
非功能性需求
使用量	
100000次/天
2012年全网估计用户数	
10000用户
响应要求（如果与全系统要求 不一致的话）	
3s内响应
使用时间段	
07:00-10:00
高峰使用时间段	
07:00-10:00
1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		

****************************************************************

修订记录 
日期 	
修订内容 	修订人员 	版本号 
2012-05-22 	
新增	
王辉	V0.1
2012-7-19	
根据评审意见：
1.	加入总体积
2.	加入查看装车人功能	
王辉	V0.3
2012-07-24	
业务评审完毕，升至0.9	
王辉	V0.9
2012-10-13	
根据RC-125修订	
王辉	
2012-10-13	
根据RC-631修订	
王辉	
2012-10-13	
根据RC-232修订	
王辉	
2012-10-13	
根据RC-486修订	
王辉	
2012-12-7	
根据ISSUE-1057修订	
王辉	

1.	
SUC-459-确认_取消预派送单
1.1	
相关业务用例
BUC_FOSS_5.50.10_520  
确认派送
1.2	
用例描述
排单员确认、
取消预派送单。
1.3	
用例条件
条件类型	描述	引用系统用例
前置条件	
1.	
预派送单已装车	
后置条件		
1.4	
操作用户角色
操作用户	
描述
排单员	
排单员确认、
取消本部门预派送单
1.5	
界面要求
1.5.1	
表现方式
Web页面 
1.5.2	
界面原型
 
图1 预派送单明细
 
图2 差异明细
 
图3扫描明细
 
图4 查询公司司机
 
图5 确认到达联件数
 
图6 默认通知客户
 
图7 查看装车人
1.5.3	
界面描述
图1界面标题：查看预派送单
界面主要分为预派送单信息、排单信息、装车信息和功能按钮
1. 
预派送单信息：
预派送单号、
车辆、
车型、
可载重量、
可载体积、
已载重量、
已载体积、
装载率、
到付总金额、
总票数、
总件数
a)	
预派送单号
b)	
车辆
c)	
车型
d)	
可载重量：车辆剩余可载重量
e)	
可载体积：车辆剩余可载体积
f)	
已载重量
g)	
已载体积
h)	
装载率：分为重量装载率和体积装载率
i)	
到付总金额：到付总金额，超过限定值时，显示红色
j)	
总票数
k)	
总件数
l)	
司机：可以修改

2. 
排单信息：
运单号、
货物名称、
重量、
体积、
排单件数、
收货人、
联系方式、
收货地址、
收货人注意事项、
预计送货时间、
是否已联系客户

3. 
装车信息分为3个部分装车差异报告、
差异统计信息、
装车任务明细和功能按钮
3.1 
装车差异报告：
运单号、
差异类型、
预装车件数、
实际装车件数、
少货件数、
运输性质、
备注
3.2 
差异统计信息：
预计装车总件数、
实际装车总件数和少货总件数
3.2 
装车任务明细：
出发部门、
到达部门、
运单号、
运输性质、
库存件数、
已操作件数、
库存重量、
库存体积、
货名、
包装
3.3 
功能按钮：
查看历史装车差异报告、
确认装车报告、
退回装车报告
a)	
查看历史装车差异报告：
点击超链接，跳转到“查询装车差异报告”界面，
参见“查询派送装车差异报告（SUC-601）”系统用例	
b)	
退回装车报告：
点击此按钮，退回装车报告
c)	
查看装车人：
点击超链接，
弹出“查看装车人”界面。

4. 
功能按钮：
确认预派送单、
取消预派送单
a)	
确认：
点击此按钮，确认预派送单
b)	
取消：
点击此按钮，取消预派送单

图2界面标题：
差异明细，数据元素参考“少货明细列表”，
排单员点击“运单号”，弹出此界面，显示少货明细

图3界面标题：
扫描明细, 数据元素参考“流水号明细列表”，
排单员点击“运单号”，弹出此界面，显示扫描明细

图4界面标题：
查询司机
查询公司司机界面分为2个部分：
查询条件、查询结果列表
1.	
查询条件包括：
a）	
工号
b）	
姓名
c）	
电话号码
2.	
查询结果列表包括操作列和结果信息
a) 
操作列： 表示确认，
点击后提示“是否确认分配给该司机？”
b）
结果信息：
参考公司司机数据元素

图5
到达联件数不一致界面分为2个部分：
不一致列表、功能按钮
1.	
不一致列表包括：
a)	
运单号
b)	
排单件数
c)	
到达联件数
d)	
状态：包括未处理、已确认、已重打 
2.	
功能按钮包括确认、重打
a)	
确认：排单员点击确认，系统更新到达联
b)	
重打：排单员点击重打，系统打印已勾选的运单到达联并作废重新生成到达联。
库存件数不一致界面包括不一致列表
1. 
不一致列表包括
a)	
运单号
b)	
排单件数
c)	
库存件数

图6界面标题：
通知客户
通知客户界面分为2个部分：
待通知列表、
功能按钮
1.	
待通知列表包括：
a)	
运单号
b)	
收货人
2. 功能按钮包括确认、
取消
a)	
确认：
排单员点击确认，系统将待通知列表中运单自动通知客户（短信）
b)	
取消：
排单员点击取消，系统不做任何操作

图7界面标题：装车人
界面主要包括：
工号、
姓名、
加入时间、
退出时间
1.6	
操作步骤
序号	基本步骤	相关数据	补充步骤
1		
排单员查看预派送单信息、
装车信息，
预派送单信息、
排单信息、
差异报告明细列表、
装车明细列表	

2		
排单员选择司机	
公司司机查询条件、
公司司机查询结果列表	系统弹出界面图4，参见SR7
3		
排单员点击“ ”	
公司司机查询条件、
公司司机查询结果列表	司机信息载入
“查看预派
送单”界面中的司机公共选择框
4		
排单员点击“确认”	
1.	
参考SR2
2.	
系统自动根据差异，将不存在于装车报告中的运单从预派送单中移除
3.	
派送单中必送货未装车，系统提示“必送货：XXXX未装车！是否确认预派送单？，参考SR1
4.	
系统修改预派送单状态为“已确认”
5.	
调用中转更新装车任务状态接口
6.	
系统更新预派送单司机信息
5		
排单员点击“取消”
1.	
系统修改派送单状态为“已取消”
2.	
调用中转更新装车任务状态接口，参见SR8
3.	
系统调用中转接口生成卸车任务，参考SR4

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
排单员查看预派送单信息、装车信息，
不认可装车，点击“退回装车报告”	
预派送单信息、排单信息、差异报告明细列表、
装车明细列表	
1.	调用中转更新装车任务状态接口

4a
系统判断排单信息中运单件数与已生成到达联是否一致，若不一致，系统弹出界面如图6		
1.	
参考SR5，SR6
2.	
排单员勾选需确认运单，点击“确认”，系统标记已确认，系统更新运单对应的到达联
3.	
排单员点击“重打”，系统标记已重打，系统打印列表中运单到达联， 系统作废运单已生成的对应的到达联并重新生成到达联
4b
系统判断排单信息中运单是否已生成到达联， 		
若未生成，则系统生成排单信息中运单对应的到达联
4c
排单信息中存在运单未通知客户，则系统弹出提示框图6		
1.	
排单员点击“确认”，系统自动生成短信(模板待定)通知客户
2.	
排单员点击“取消”，继续步骤4
5a	
预派送单状态为“装车中”，系统提示“装车中不允许取消预派送单！”		

1.7	
业务规则
序号	描述
SR1	
必送货为通知客户时标识

SR2	
车辆实际重量装载率或车辆实际体积装载率（当一车多个派送单情况时，以车辆总的重量装载率和体积装载率为准）没有达到60%，
系统提示“重量装载率未达到60%，是否确认预派送单？”或“体积装载率未达到60%，是否确认预派送单？”，60%提供参数配置，
可部门设置。
SR3	
理货员提交装车任务后，系统提示排单员审核货物是否确认派送，排单员选择“是”，转到查看装车差异报告界面，排单员选择“否”，则不做任何操作
SR4	
已装车和已确认状态下的预派送单取消后生成卸车任务






SR7	
1.	
若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
2.	
若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
3.	
当排班和PDA绑定同时存在时，以PDA绑定为准
SR8	
1.	
未确认派送单，调用中转接口
2.	
已确认派送单，不调用

1.8	
数据元素
1.8.1	
预派送单信息
字段名称 	说明 	
输入限制	输入项提示文本	长度	是否必填	备注
预派送单号		
文本				
车辆
文本				
车型		
文本				
可载重量	
文本				
可载体积		
文本				
已载重量		
文本				
已载体积		
文本				
装载率		
文本				
到付总金额		
文本				
总票数		
文本				
总件数		
文本				

1.8.2	
排单信息
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
运单号		
文本				
排单件数		
文本				
货物名称		
文本				
重量		
文本				
体积		
文本				
收货人		
文本				
联系方式		
文本				
收货地址		
文本				
送货要求		
文本				
预计送货时间		
文本				
是否已联系客户		
文本				

1.8.3	
差异报告明细列表
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
运单号	
货物的运单编号	
文本				
预装车件数	
预装车件数	
文本				
实际装车件数	
实际装车件数	
文本				
运输性质	
货物运输类型	
文本				
差异类型
少货	
文本				
备注	PDA装车扫描时对运单的备注	
文本				

1.8.4	
装车明细列表
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
出发部门	交接单/配载单出发部门	文本				

到达部门	交接单/配载单到达部门	文本			
是	
运单号	运单号	
文本			
是	无
运输性质	运输性质	
文本			
是	无
库存件数	库存件数	
文本				
已操作件数	已操作件数	
文本			
是	
库存重量	开单重量*库存件数/开单件数	
文本			
是	无
库存体积	开单体积*库存件数/开单件数	
文本			
是	无
货名	货名	文本			
是	无
包装	包装	文本			
是	

1.8.5	
公司司机查询条件
字段名称 	说明 	
输入限制	输入项提示文本	
长度	是否必填	备注
工号		
文本		10		
司机姓名		
文本		10		
电话号码		
文本		20		

1.8.6	
公司司机查询结果列表
字段名称 	说明 	
输入限制	输入项提示文本	
长度	是否必填	备注
工号		
文本		10		
司机姓名		
文本		10		
电话号码		
文本		20		
部门		
文本		10		

1.8.7	
少货明细列表
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
流水号	
流水号	
文本				
扫描状态	已扫描、未扫描	
文本				
扫描时间	扫描时间	日期				
货物状态	未装车、
强装-有更改	文本				

1.8.8	
流水号明细列表
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
流水号	
运单流水号	文本			
是	无
扫描状态	已扫描、未扫描	
文本			
是	无
货物状态	正常、
未装车、
多货-夹带、
强装-异地夹带、
强装-有更改、
强装-未打包装、
强装-贵重物品未出库、
强装-带打包装未出库，
强装-未预配	
文本				
是否装车	是否装车	文本			
是	在货物状态为正常、多货-夹带时是否装车为是，否则为否
操作时间	扫描时间	数字			
是	

1.8.9	
装车人
字段名称 	说明 	
输入限制	输入提示文本	长度	是否必填	备注
工号		
文本			
是	
姓名		
文本			
是	
加入时间		
文本			
是	
退出时间		
数字			
是	

1.9	
非功能性需求
使用量	100W运单，按每次派送20单算，每天确认/取消5W次。
2012年全网估计用户数	10000用户
响应要求（如果与全系统要求 不一致的话）	
3s内响应
使用时间段	
07:00-10:00
高峰使用时间段	
07:00-10:00


1.10	
接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		
		
******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckScheduleDao;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutService;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.TruckConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVehicleActualSituationManageService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrangeArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoaderDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.predeliver.server.util.IntegerUtils;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送单Service实现.
 *
 * @author ibm-
 * 		wangxiexu
 * @date 2012-10-18 
 * 		下午5:18:13
 * @since
 * @version
 */
public class DeliverbillService implements IDeliverbillService {
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.predeliver.server.service.impl.DeliverbillService";
	private ICUBCGrayService cUBCGrayService;
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}
	private IWaybillDao waybillDao;
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	/** 
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillService.class);
	
	/**
	 * 通知成功标志
	 */
	private static final String SUCCESS = "SUCCESS";
	
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
	/**
	 *  添加派送单明细操作. 
	 */
	private static final int ADD_OPERATION = 1;
	
	/** 
	 * 移除派送单明细操作. 
	 */
	private static final int DELETE_OPERATION = 2;
	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 1;
	
	private static final int PAGE_NUM = 100;
	
	/**
	 * 装载率百分比.
	 */
	private static final int LOADRATE = 100;
	
	/** 
	 * 零
	 */
	private static final int ZERO = 0;
	
	private static final int ONE = 1;
	
	/** 
	 * 用于改变正负
	 */
	private static final int MINUS_ONE = -1;
	
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 派单服务
	 */
	private IDeliverbillService deliverbillService;
	
	/**
	 * 确认派送单发送短信
	 */
	private IDeliverbillNewService deliverbillNewService;
	/**
	 * 派送单状态更新记录表Service 
	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	/** 
	 * 排单失败原因
	 */
	private static final String FAILED_REASON_GOODS_ARRANGED = "运单已排单";
	
	/** 
	 * 排单数量超过限制
	 */
	private static final String FAILED_REASON_NOT_ENOUGH_TO_ARRANGE = "排单数量超过限制";
	
	/** 
	 * 排单运行时错误
	 */
	private static final String FAILED_REASON_RUNTIME_EXCEPTION = "排单运行时错误";
	
	/** 
	 * 签收
	 */
	private static final String WAYBILL_IS_SIGN = "签收";
	/** 
	 * 未签收
	 */
	private static final String WAYBILL_IS_NOT_SIGN = "未签收";
	/** 
	 * 该整车运单没做配载
	 */
	private static final String WAYBILL_NO_ASSEMBLE = "该整车运单没做配载";
	/** 
	 *  派送单DAO接口
	 */
	private IDeliverbillDao deliverbillDao;
	/**
	 * 产品定义
	 */
	private IProductService productService;
	
	/** 
	 * 派送单明细DAO接口
	 */
	private IDeliverbillDetailDao deliverbillDetailDao;
	
	/** 
	 * 运单状态数据持久层
	 */
	private IActualFreightDao actualFreightDao;
	
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	/** 
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	
	/** 
	 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service接口
	 */
	private IVehicleService vehicleService;
	
	/** 
	 * 查询装车任务
	 */
	private ILoadTaskQueryService loadTaskQueryService;
	
	/** 
	 * 差异报告
	 */
	private IDeliverLoadGapReportService deliverLoadGapReportService;
	
	/** 
	 * 派送装车任务明细列表
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;
	
	/** 
	 * 车辆车载信息管理服务
	 */
	private IVehicleActualSituationManageService vehicleActualSituationManageService;
	
	/** 
	 * 到达联管理接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	
	/** 
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 
	 * 调度解除司机签到、调度解除司机签到 签到注销服务
	 */
	private ISignInAndLogOutService signInAndLogOutService;
	
	/** 
	 * 排班表DAO接口
	 */
	private ITruckScheduleDao truckScheduleDao;
	
	/** 
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/** 
	 * 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/** 
	 * 业务监控服务
	 */
	private IBusinessMonitorService businessMonitorService;
	
	/** 
	 * 提供给外部的接口
	 */
	private IWebDepartureService webDepartureService;
	
	/** 
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	
	private IWaybillManagerService waybillManagerService;
	
	private ICommonExpressVehicleService commonExpressVehicleService;
	/**
	 * 更改单服务类
	 */
	private IWaybillRfcService waybillRfcService;
	private ITakingService takingService;
	/**
	 * 中转配载校验
	 */
	private IArrivalService arrivalService;
	/**
	 * 微信服务类型
	 */
	private IWeixinSendService weixinSendService;
	
	//综合的接口，排除外请车
	private ILeasedVehicleService leasedVehicleService;
	
	//综合的接口,本公司的车
	private static final String ORDER_RESOURCE_LEASEDVENHICLE = "leasedVehicle";
	
	private IGpsDeliverService gpsDeliverService;
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}

	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	/**
	 * 微信服务类型
	 */
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			handle query outfield service.
	 *
	 * @param handleQueryOutfieldService 
	 * 		the 
	 * 			new handle query outfield service
	 */
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			web departure service.
	 *
	 * @param webDepartureService 
	 * 		the 
	 * 			new web departure service
	 */
	public void setWebDepartureService(IWebDepartureService webDepartureService) {
		this.webDepartureService = webDepartureService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 			business monitor service.
	 *
	 * @param businessMonitorService 
	 * 		the 
	 * 			new business monitor service
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			deliverbill dao.
	 *
	 * @param deliverbillDao 
	 * 		the 
	 * 			new deliverbill dao
	 */
	public void setDeliverbillDao(IDeliverbillDao deliverbillDao)
	{
		this.deliverbillDao = deliverbillDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			deliverbill detail dao.
	 *
	 * @param deliverbillDetailDao 
	 * 		the 
	 * 			new deliverbill detail dao
	 */
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao)
	{
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			actual freight dao.
	 *
	 * @param actualFreightDao 
	 * 		the 
	 * 			new actual freight dao
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao)
	{
		this.actualFreightDao = actualFreightDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			load task query service.
	 *
	 * @param loadTaskQueryService 
	 * 		the 
	 * 			new load task query service
	 */
	public void setLoadTaskQueryService(ILoadTaskQueryService loadTaskQueryService)
	{
		this.loadTaskQueryService = loadTaskQueryService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			leased driver service.
	 *
	 * @param leasedDriverService 
	 * 		the 
	 * 			new leased driver service
	 */
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService)
	{
		this.leasedDriverService = leasedDriverService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			vehicle service.
	 *
	 * @param vehicleService 
	 * 		the 
	 * 			new vehicle service
	 */
	public void setVehicleService(IVehicleService vehicleService)
	{
		this.vehicleService = vehicleService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			deliver load gap report service.
	 *
	 * @param deliverLoadGapReportService 
	 * 		the 
	 * 			new deliver load gap report service
	 */
	public void setDeliverLoadGapReportService(IDeliverLoadGapReportService deliverLoadGapReportService)
	{
		this.deliverLoadGapReportService = deliverLoadGapReportService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			deliver load task service.
	 *
	 * @param deliverLoadTaskService 
	 * 		the 
	 * 			new deliver load task service
	 */
	public void setDeliverLoadTaskService(IDeliverLoadTaskService deliverLoadTaskService)
	{
		this.deliverLoadTaskService = deliverLoadTaskService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			vehicle actual situation manage service.
	 *
	 * @param vehicleActualSituationManageService 
	 * 		the 
	 * 			new vehicle actual situation manage service
	 */
	public void setVehicleActualSituationManageService(IVehicleActualSituationManageService vehicleActualSituationManageService)
	{
		this.vehicleActualSituationManageService = vehicleActualSituationManageService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			arrive sheet mannger service.
	 *
	 * @param arriveSheetManngerService 
	 * 		the 
	 * 			new arrive sheet mannger service
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService)
	{
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService 
	 * 		the 
	 * 			new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService)
	{
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			sign in and log out service.
	 *
	 * @param signInAndLogOutService 
	 * 		the 
	 * 			new sign in and log out service
	 */
	public void setSignInAndLogOutService(ISignInAndLogOutService signInAndLogOutService)
	{
		this.signInAndLogOutService = signInAndLogOutService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			truck schedule dao.
	 *
	 * @param truckScheduleDao 
	 * 		the 
	 * 			new truck schedule dao
	 */
	public void setTruckScheduleDao(ITruckScheduleDao truckScheduleDao)
	{
		this.truckScheduleDao = truckScheduleDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			notify customer service.
	 *
	 * @param notifyCustomerService 
	 * 		the 
	 * 			new notify customer service
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService)
	{
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			sale department service.
	 *
	 * @param saleDepartmentService 
	 * 		the 
	 * 			new sale department service
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService)
	{
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			business lock service.
	 *
	 * @param businessLockService 
	 * 		the 
	 * 			new business lock service
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 打印派送单到达联查询.
	 *
	 * @param deliverbillDto 
	 * 	id					
	 * 派送单ID
	 * 	deliverbillNo		
	 * 派送单编号
	 * 	vehicleNo			
	 * 车辆车牌号	
	 * 	driverName			
	 * 司机姓名
	 * 	driverCode			
	 * 司机工号
	 *	status				
	 * 派送单状态
	 *	submitTimeBegin		
	 * 查询条件提交开始时间
	 * 	submitTimeEnd		
	 * 查询条件提交结束时间
	 * 	createTimeBegin		
	 * 查询条件创建开始时间
	 * 	createTimeEnd		
	 * 查询条件创建结束时间
	 * 	createUserName		
	 * 提交人	
	 * 	submitTime			
	 * 提交时间
	 * 	operateTime			
	 * 确认时间
	 * 	createOrgName		
	 * 创建部门
	 * 	createOrgCode		
	 * 创建部门编码
	 * 	weightTotal			
	 * 总重量
	 * 	volumeTotal			
	 * 总体积
	 * 	deliverWaybillQty	
	 * 派送成功票数
	 * 	pullbackWaybillQty	
	 * 派送拉回票数
	 * 	orgCode				
	 * 部门Code
	 * 
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return 
	 * 		the list
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-1-31 
	 * 		下午12:47:24
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#queryDeliverbillList
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto,int, int)
	 */
	@Override
	public List<DeliverbillDto> queryDeliverbillList(DeliverbillDto deliverbillDto, int start, int limit)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		if("P".equals(deliverbillDto.getDeliverbillNo()))
		{
			deliverbillDto.setDeliverbillNo("");
		}
		//若传入编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			// 根据部门code，查询非偏线和空运的外场和货区
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if (saleDepartment != null && saleDepartment.checkStation())
			{
				deliverbillDto.setTransferCenter(saleDepartment.getTransferCenter());
			}
				deliverbillDto.setOrgCode(orgCode);
			
			List<DeliverbillDto> deliverbillList = this.deliverbillDao.queryByCondition(deliverbillDto, start, limit);
			List<DeliverbillDto> deliverbillInfo = new ArrayList<DeliverbillDto>();
			for (DeliverbillDto deliverbillDto2 : deliverbillList) {
				  //若司机工号不为空
			      if (StringUtil.isNotEmpty(deliverbillDto2.getDriverCode()))
			      {
			        // 内部司机根据工号查询相关信息
			        DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverbillDto2.getDriverCode());
			        //用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
			        if (driverAssociationDto != null)
			        {
			          // 司机电话
			        deliverbillDto2.setDriverTel(driverAssociationDto.getDriverPhone());
			        } else
			        {
			          // 外请司机根据车牌号进行查询
			          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2.getVehicleNo());
			          
			          if (CollectionUtils.isNotEmpty(driverAssociationDtos))
			          {
				        	//司机姓名
				        	deliverbillDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
				        	// 司机电话
			        	    deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
			          }
			        }
			        //如果车牌号不为空
			      } else if (StringUtil.isNotEmpty(deliverbillDto2.getVehicleNo()))
			      {
			        // 外请司机根据车牌号进行查询
			        List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2.getVehicleNo());
			        
			        if (CollectionUtils.isNotEmpty(driverAssociationDtos))
			        {
			        	//司机姓名
			        	deliverbillDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
			        	// 司机电话
			        	deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
			        }
			      }
			      deliverbillInfo.add(deliverbillDto2);
			}
			//根据输入条件，查询派送单
			return deliverbillInfo;
		} else
		{
			//返回空
			return null;
		}
	}

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-3-19 
	 * 		上午10:23:42
	 * @param 
	 * 		deliverbillId
	 * @return 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#queryDeliverbillDetailList
	 * (java.lang.String)
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillDetailList(String deliverbillId)
	{
		// 获取当前部门
		String currOrgCode = FossUserContextHelper.getOrgCode();
		// 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
		String areaCode = null;
		if (CollectionUtils.isNotEmpty(list)) {
			   //传入库区code
			   areaCode = list.get(1);
		}
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		map.put("areaCode", areaCode);
		//根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> entitys = this.deliverbillDetailDao.queryByDeliverbillId(map);
		//返回派送单明细
		return entitys;
	}

	/**
	 * 根据派送单ID查找派送单明细列表.
	 *
	 * @param deliverbillId 
	 * 		派送单ID
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return 
	 * 		the list
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-24
	 * 		 上午10:05:28
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillDetailList(String deliverbillId, int start, int limit)
	{
		// 获取当前部门
		String currOrgCode = FossUserContextHelper.getOrgCode();
		String areaCode = null;
		//若当前部门编码 不为空时
		if (!StringUtils.isEmpty(currOrgCode))
		{	
		//获取当前用户设置的当前部门
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		String salesDepartment = org.getSalesDepartment();

		// 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
		if (FossConstants.YES.equals(salesDepartment))
		{
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
			if (CollectionUtils.isNotEmpty(list)) {
				areaCode = list.get(1);
			}
		} else
			{
				// 查询排单服务外场
				OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currOrgCode);
				String orgCode1 = null;
				if (transferCenter != null)
				{
					// 取外场驻地派送部的运单进行排单
					SaleDepartmentEntity sale = new SaleDepartmentEntity();
					sale.setTransferCenter(transferCenter.getCode());
					sale.setDelivery(FossConstants.YES);
					sale.setActive(FossConstants.ACTIVE);
					sale.setStation(FossConstants.ACTIVE);
					List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
					//若salelist集合不为空
					if (!CollectionUtils.isEmpty(salesList))
					{
						orgCode1 = salesList.get(0).getCode();
					}
				}
				// 添加库存外场、库区默认查询条件
				List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
				if (CollectionUtils.isNotEmpty(list)) {
					 //传入库区code
					 areaCode = list.get(1);
				}
			}
		}
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		map.put("areaCode", areaCode);
		
		//根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> entitys = this.deliverbillDetailDao.queryByDeliverbillId(map, start, limit);
		//List<DeliverbillDetailEntity> entitys = this.deliverbillDetailDao.selectByDeliverbillReturn_bill_type_Sort(map, start, limit);
		//如果集合不为空
		if (CollectionUtils.isNotEmpty(entitys))
		{	//循环派送单明细列表
			for (DeliverbillDetailEntity entity : entitys)
			{	
				//包装
				entity.setGoodsPackage(entity.getGoodsPackage());
				//返单类别
				//entity.setSingleSignStatus(WaybillConstants.NOT_RETURN_BILL.equals(entity.getReturnBillType()) ? "否" : "是");
				if (entity.getReturnBillType().equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL)) {
					entity.setSingleSignStatus("签收单原单返回");
				}else if(entity.getReturnBillType().equals(WaybillConstants.RETURNBILLTYPE_FAX)) {
					entity.setSingleSignStatus("签收单传真返回");
				}else{
					entity.setSingleSignStatus("");
				}
				// 货物信息
				entity.setGoodsInfo(joinString(entity.getWeight(), entity.getGoodsVolumeTotal(), entity.getArrangeGoodsQty()));
				// 客户信息
				entity.setConsigneeInfo(joinString(entity.getConsignee(), entity.getConsigneeContact(), entity.getConsigneeAddress()));
				//设置送货日期、时间段、时间点
				StringBuilder sb = new StringBuilder();
				sb.append(StringUtils.isBlank(entity.getPreDeliverDate())?"":entity.getPreDeliverDate().substring(0, NumberConstants.NUMBER_10)+"  ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeInterval())?"":entity.getDeliveryTimeInterval()+"  ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeStart())?"":entity.getDeliveryTimeStart());
				sb.append(" - ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeOver())?"":entity.getDeliveryTimeOver());
				entity.setDeliveryTime(sb.toString());
			}
		} else
		{
			
		}
		//返回派送单明细列表
		return entitys;
	}
	
	/**
	 * 
	 * 查询派送单打印（里面不能用session中的值 否则会报错 切记切记）	
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-12 上午8:50:58
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#queryDeliverbillDetailListForPrint(java.lang.String, int, int)
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillDetailListForPrint(String deliverbillId, int start, int limit){
		// 获取当前部门
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("deliverbillId", deliverbillId);

		// 根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> entitys = this.deliverbillDetailDao.queryByDeliverbillId(map, start, limit);
		// 如果集合不为空
		if (CollectionUtils.isNotEmpty(entitys)) { // 循环派送单明细列表
			for (DeliverbillDetailEntity entity : entitys) {
				// 包装
				entity.setGoodsPackage(entity.getGoodsPackage());
				//返单类别
				if (entity.getReturnBillType().equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL)) {
					entity.setSingleSignStatus("签收单原单返回");
				}else if(entity.getReturnBillType().equals(WaybillConstants.RETURNBILLTYPE_FAX)) {
					entity.setSingleSignStatus("签收单传真返回");
				}else{
					entity.setSingleSignStatus("");
				}
				// 货物信息
				entity.setGoodsInfo(joinString(entity.getWeight(), entity.getGoodsVolumeTotal(), entity.getArrangeGoodsQty()));
				// 客户信息
				entity.setConsigneeInfo(joinString(entity.getConsignee(), entity.getConsigneeContact(), entity.getConsigneeAddress()));
				//设置送货日期、时间段、时间点
				StringBuilder sb = new StringBuilder();
				sb.append(StringUtils.isBlank(entity.getPreDeliverDate())?"":entity.getPreDeliverDate().substring(0, NumberConstants.NUMBER_10)+"  ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeInterval())?"":entity.getDeliveryTimeInterval()+"  ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeStart())?"":entity.getDeliveryTimeStart());
				sb.append(" - ");
				sb.append(StringUtils.isBlank(entity.getDeliveryTimeOver())?"":entity.getDeliveryTimeOver());
				entity.setDeliveryTime(sb.toString());
			}
		} else {

		}
		// 返回派送单明细列表
		return entitys;
	}

	/**
	 * 根据派送单ID查找派送单信息.
	 *
	 * @param id 
	 * 		the id
	 * @return 
	 * 		the deliverbill entity
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-24
	 * 		 下午4:55:30
	 */
	@Override
	public DeliverbillEntity queryDeliverbill(String id)
	{
		//派送单实体
		DeliverbillEntity entity = null;
		//根据ID查找派送单
		entity = this.deliverbillDao.queryById(id);
		//如果实体不为空
		if (entity != null)
		{
			// 派送部 -取派送单创建部门
			entity.setDeliveryDepartment(entity.getCreateOrgName());
			// 装载率
			setLoadingRate(entity);
			// 设置派送单上的司机相关信息
			setDriverInfo(entity);
		} else
		{

		}
		//返回实体
		return entity;
	}

	/**
	 * 设置派送单上的司机相关信息.
	 *
	 * @param entity 
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @author ibm-
	 * 		wangfei
	 * @date Nov 27, 
	 * 		2012 10:33:18 AM
	 */
	private void setDriverInfo(DeliverbillEntity entity)
	{
		try
		{	//若司机工号不为空
			if (StringUtil.isNotEmpty(entity.getDriverCode()))
			{
				// 内部司机根据工号查询相关信息
				DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(entity.getDriverCode());
				//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
				if (driverAssociationDto != null)
				{
					// 司机电话
					entity.setDriverTel(driverAssociationDto.getDriverPhone());
					// 所属车队
					entity.setMotorcade(driverAssociationDto.getDriverOrganizationName());
				} else
				{
					// 外请司机根据车牌号进行查询
					List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
					
					if (CollectionUtils.isNotEmpty(driverAssociationDtos))
					{
			        	//司机姓名
						entity.setDriverName(driverAssociationDtos.get(0).getDriverName());
						// 司机电话
						entity.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
					}
				}
				//如果车牌号不为空
			} else if (StringUtil.isNotEmpty(entity.getVehicleNo()))
			{
				// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
				
				if (CollectionUtils.isNotEmpty(driverAssociationDtos))
				{
		        	//司机姓名
					entity.setDriverName(driverAssociationDtos.get(0).getDriverName());
					// 司机电话
					entity.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
				}
			}
		} catch (OwnDriverException oe)
		{	
			//日志记录
			LOGGER.error("error", oe);
			return;
		} catch (LeasedDriverException le)
		{	
			//日志记录
			LOGGER.error("error", le);
			return;
		}
	}

	/**
	 * 设置装载率.
	 *
	 * @param entity 
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @author ibm-
	 * 		   wangfei
	 * @date Nov 28, 
	 * 		 2012 8:18:14 PM
	 */
	private void setLoadingRate(DeliverbillEntity entity)
	{
		try
		{
			// 装载率(重量/体积)
			VehicleAssociationDto vehicleAssociationDto = this.vehicleService.queryVehicleAssociationDtoByVehicleNo(entity.getVehicleNo());
			//若装载率(重量/体积)不为空
			if (vehicleAssociationDto != null)
			{
				String vehicleLoad = null;
				String vehicleVolume = null;
				// 装载率(重量)
				BigDecimal vehicleLoadRate = BigDecimalOperationUtil.div(entity.getWeightTotal(), vehicleAssociationDto.getVehicleDeadLoad()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleDeadLoad().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_1000)),2);
				//如果装载率(重量)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleLoadRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleLoad = "0.00%";
				}
				else
				{
					vehicleLoad = (vehicleLoadRate.multiply(new BigDecimal(LOADRATE))).toString()+"%";
				}
				// 装载率(体积)
				BigDecimal vehicleVolumeRate = BigDecimalOperationUtil.div(entity.getVolumeTotal(), vehicleAssociationDto.getVehicleSelfVolume()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleSelfVolume(),2);
				
				//如果装载率(体积)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleVolumeRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleVolume = "0.00%";
				}
				else
				{
					vehicleVolume = (vehicleVolumeRate.multiply(new BigDecimal(LOADRATE))).toString()+"%";
				}
				//设置装载率(重量/体积)
				entity.setLoadingRate(joinString(vehicleLoad, vehicleVolume));
			}
		} catch (LeasedDriverException le)
		{	
			//日志记录
			LOGGER.error("error", le);
		}
	}

	/**
	 * 从派送单中批量移除派送单明细.
	 *
	 * @param deliverbillId 派送单ID
	 * @param deliverbillDetailIdArray 待删除的派送单明细ID数组
	 * @return the deliverbill entity
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30 下午7:10:25
	 */
	@Transactional
	@Override
	public DeliverbillEntity deleteDeliverbillDetails(String deliverbillId, String[] deliverbillDetailIdArray)
	{
		DeliverbillEntity deliverbill = deliverbillDao.queryById(deliverbillId);
		if(deliverbill != null)
		{	
			if(deliverbill!= null && (DeliverbillConstants.STATUS_LOADED.equals(deliverbill.getStatus())
				|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbill.getStatus()) 
				|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbill.getStatus()) 
				|| DeliverbillConstants.STATUS_CANCELED.equals(deliverbill.getStatus()) 
				|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbill.getStatus())))
			{
				throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
			}
			
			if(DeliverbillConstants.STATUS_ASSIGNED.equals(deliverbill.getStatus())||DeliverbillConstants.STATUS_SUBMITED.equals(deliverbill.getStatus()))
			{
				int a = Integer.parseInt(deliverbillDetailDao.queryCountByDeliverbillId(deliverbillId).toString());
				int b = deliverbillDetailIdArray.length;
				if(a == b)
				{
					throw new DeliverbillException(DeliverbillException.DELIVERBILLDETAL_CANT_DELETE);
				}
			}
		}

		//若待删除的派送单明细ID数组不为空
		if (deliverbillDetailIdArray != null && deliverbillDetailIdArray.length > 0)
		{
			// 批量删除派送单明细
			DeliverbillDetailEntity deliverbillDetail;
			int deleteResult = 0;
			for (String deliverbillDetailId : deliverbillDetailIdArray)
			{
				deliverbillDetail = this.deliverbillDetailDao.queryById(deliverbillDetailId);

				deleteResult = this.deliverbillDetailDao.delete(deliverbillDetailId);

				if (deleteResult > 0)
				{
					// 更新排序靠后的派送单明细序号
					this.deliverbillDetailDao.updateSerialNos(deliverbillDetail);

					// 更新派送单统计信息
					deliverbill = this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.DELETE_OPERATION);

					// 更新运单已排单件数
					ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(deliverbillDetail.getWaybillNo());
					//运单已排单件数不为空
					if (actualFreight != null)
					{
						actualFreight.setArrangeGoodsQty(actualFreight.getArrangeGoodsQty() - deliverbillDetail.getArrangeGoodsQty());
						
						this.actualFreightDao.updateByPrimaryKeySelective(actualFreight);
					}
				}
			}

			// 更新派送单统计信息
			this.deliverbillDao.update(deliverbill);
		}

		return deliverbill;
	}

	/**
	 * 更新派送单统计信息.
	 *
	 * @param deliverbill 
	 * 派送单
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @param deliverbillDetail 添加/移除的派送单明细
	 * 		id	
	 * the id 
	 * 		tSrvDeliverbillId	
	 * 派送单ID
	 * 		serialNo			
	 * 序号
	 * 		arrivesheetNo		
	 * 到达联编号
	 * 		waybillNo			
	 * 运单号
	 * 		arrangeStatus		
	 * 排单状态	
	 * 		preArrangeGoodsQty	
	 * 预排单件数
	 * 		arrangeGoodsQty		
	 * 确认排单件数
	 * 		weight				
	 * 重量
	 * 		dimension			
	 * 尺寸
	 * 		goodsName			
	 * 货物名称
	 * 		waybillGoodsQty		
	 * 运单件数
	 * 		transportType		
	 * 运输方式
	 * 		arriveTime			
	 * 到达时间
	 * 		consignee			
	 * 收货人
	 * 		consigneeContact	
	 * 收货人联系方式
	 * 		consigneeAddress	
	 * 收货人地址
	 * 
	 * @param operation 
	 * 操作类型(添加/移除)
	 * @return
	 *  更新后的派送单
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-10-29 
	 * 下午2:24:42
	 * 
	 */
	private DeliverbillEntity updateDeliverbillStat(DeliverbillEntity deliverbill, DeliverbillDetailEntity deliverbillDetail, int operation)
	{
		MutexElement mutexElement = new MutexElement(deliverbillDetail.getWaybillNo(), "运单排单", MutexElementType.DELIVERBILL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
		}
		//运单还未补录
		if(deliverbillDetail.getPayAmount() == null || deliverbillDetail.getWeight() == null){
			throw new DeliverbillException(DeliverbillException.WAYBILLNO_NOT_COMPLEMENT,new Object[]{deliverbillDetail.getWaybillNo()});
		}
		//若操作类型为添加派送单明细操作时
		if (operation == DeliverbillService.ADD_OPERATION)
		{
			// 添加派送单明细
			deliverbill.setWaybillQtyTotal(deliverbill.getWaybillQtyTotal() + 1);

			deliverbill.setGoodsQtyTotal(deliverbill.getGoodsQtyTotal() + deliverbillDetail.getArrangeGoodsQty());

			deliverbill.setPayAmountTotal(deliverbill.getPayAmountTotal().add(deliverbillDetail.getPayAmount()));

			deliverbill.setWeightTotal(deliverbill.getWeightTotal().add(deliverbillDetail.getWeight()));

			deliverbill.setVolumeTotal(deliverbill.getVolumeTotal().add(deliverbillDetail.getGoodsVolumeTotal()));

			deliverbill.setFastWaybillQty(deliverbill.getFastWaybillQty() + deliverbillDetail.getFastWaybillFlag());
		
		//如若操作类型为移除派送单明细操作时
		} else if (operation == DeliverbillService.DELETE_OPERATION)
		{
			// 移除派送单明细
			deliverbill.setWaybillQtyTotal(deliverbill.getWaybillQtyTotal() - 1);

			deliverbill.setGoodsQtyTotal(deliverbill.getGoodsQtyTotal() - deliverbillDetail.getArrangeGoodsQty());

			deliverbill.setPayAmountTotal(deliverbill.getPayAmountTotal().subtract(deliverbillDetail.getPayAmount()));

			deliverbill.setWeightTotal(deliverbill.getWeightTotal().subtract(deliverbillDetail.getWeight()));

			deliverbill.setVolumeTotal(deliverbill.getVolumeTotal().subtract(deliverbillDetail.getGoodsVolumeTotal()));

			deliverbill.setFastWaybillQty(deliverbill.getFastWaybillQty() - deliverbillDetail.getFastWaybillFlag());
		}
		//解锁
		businessLockService.unlock(mutexElement);
		return deliverbill;
	}

	/**
	 * 根据输入条件，查询符合条件的派送单数量.
	 *
	 * @param deliverbillDto 查询条件
	 * 		id		
	 * 派送单ID
	 * 		deliverbillNo	
	 * 派送单编号
	 * 		vehicleNo		
	 * 车辆车牌号
	 * 		driverName		
	 * 司机姓名
	 * 		driverCode		
	 * 司机工号
	 * 		status			
	 * 派送单状态
	 * 		submitTimeBegin	
	 * 查询条件 提交开始时间
	 * 		submitTimeEnd	
	 * 查询条件 提交结束时间
	 * 		createTimeBegin	
	 * 查询条件 创建开始时间
	 * 		createTimeEnd	
	 * 查询条件 创建结束时间
	 * 		createUserName	
	 * 提交人
	 * 		submitTime		
	 * 提交时间
	 * 		operateTime		
	 * 确认时间
	 * 		createOrgName	
	 * 创建部门
	 * 		createOrgCode	
	 * 创建部门编码
	 * 		weightTotal		
	 * 总重量
	 * 		volumeTotal		
	 * 总体积
	 * 		deliverWaybillQty	
	 * 派送成功票数
	 * 		pullbackWaybillQty	
	 * 派送拉回票数
	 * 		orgCode			
	 * 部门Code
	 * 
	 * @return 符合条件的派送单数量
	 * @author ibm-
	 * 		 wangxiexu
	 * @date 2012-10-29 下午5:01:01
	 */
	@Transactional
	public Long queryDeliverbillCountByCondition(DeliverbillDto deliverbillDto)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		if(null != deliverbillDto){
			if("P".equals(deliverbillDto.getDeliverbillNo()))
			{
				deliverbillDto.setDeliverbillNo("");
			}
			//如果派送单号或者运单号不为空
			if(StringUtils.isNotBlank(deliverbillDto.getDeliverbillNo())||StringUtils.isNotBlank(deliverbillDto.getWaybillNo())){
				deliverbillDto.setLoadTimeBegin(null);
				deliverbillDto.setLoadTimeEnd(null);
			}
		}		
		//若当前部门编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if (saleDepartment != null && saleDepartment.checkStation())
			{
				deliverbillDto.setTransferCenter(saleDepartment.getTransferCenter());
			}
				deliverbillDto.setOrgCode(orgCode);

			return this.deliverbillDao.queryCountByCondition(deliverbillDto);
		} else
		{
			return 0L;
		}
	}

	/**
	 * 根据派送单ID查找派送单明细列表大小.
	 *
	 * @param deliverbillId 派送单ID
	 * @return 派送单明细列表大小
	 * 派送单明细列表大小
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-10-29 下午5:01:01
	 */
	@Transactional
	public Long queryDeliverbillDetailCountByDeliverbillId(String deliverbillId)
	{
		return this.deliverbillDetailDao.queryCountByDeliverbillId(deliverbillId);
	}

	/**
	 * 提高派送单明细优先级.
	 *
	 * @param deliverbillId 
	 * 		派送单ID
	 * @param deliverbillDetailId 
	 * 		派送单明细ID
	 * @param serialNo 
	 * 		派送单明细序列号
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-10-30
	 * 		 下午2:34:52
	 */
	@Transactional
	@Override
	public void upgradeDeliverbillDetail(String deliverbillId, String deliverbillDetailId, Integer serialNo)
	{
		try {
			DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();

			// 降低排在之前派送单明细的优先级
			deliverbillDetail.setId(this.deliverbillDetailDao.queryIdBySerialNo(deliverbillId, serialNo - 1));
			deliverbillDetail.setSerialNo(serialNo);
			this.deliverbillDetailDao.update(deliverbillDetail);

			// 提高派送明细单优先级
			deliverbillDetail.setId(deliverbillDetailId);
			deliverbillDetail.setSerialNo(serialNo - 1);
			this.deliverbillDetailDao.update(deliverbillDetail);
		} catch (Exception e) {
			throw new DeliverbillException("提高派送单明细优先级失败！");
		}
		
	}

	/**
	 * 降低派送单明细优先级.
	 *
	 * @param deliverbillId 
	 * 		派送单ID
	 * @param deliverbillDetailId 
	 * 		派送单明细ID
	 * @param serialNo 
	 * 		派送单明细序列号
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30 
	 * 		下午2:34:52
	 */
	@Transactional
	@Override
	public void downgradeDeliverbillDetail(String deliverbillId, String deliverbillDetailId, Integer serialNo)
	{
		try {
			DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();

			// 提高排在之后派送单明细的优先级
			deliverbillDetail.setId(this.deliverbillDetailDao.queryIdBySerialNo(deliverbillId, serialNo + 1));
			deliverbillDetail.setSerialNo(serialNo);
			this.deliverbillDetailDao.update(deliverbillDetail);

			// 降低派送明细单优先级
			deliverbillDetail.setId(deliverbillDetailId);
			deliverbillDetail.setSerialNo(serialNo + 1);
			this.deliverbillDetailDao.update(deliverbillDetail);
		} catch (Exception e) {
			throw new DeliverbillException("降低派送单明细优先级失败！");
		}
		
	}

	/**
	 * 根据查询条件查询待排运单.
	 *
	 * @param waybillToArrangeDto 
	 * 		查询条件
	 * 		receiveCustomerProvCode	
	 * 		收货省份
	 * 		receiveCustomerCityCode	
	 * 		收货市
	 * 		receiveCustomerDistCode	
	 * 		收货区(行政区域)
	 * 		deliverRegionCode	
	 * 		送货小区编码
	 * 		waybillNo			
	 * 		运单号
	 * 		receiveMethod		
	 * 		提货方式
	 * 		receiveCustomerName	
	 * 		收货客户名称
	 * 		receiveCustomerPhone 
	 * 		收货客户电话
	 * 		fastWaybillCode		
	 * 		精准卡航产品编码
	 * 		waybillActive		
	 * 		运单有效状态
	 * 		rfcStatus		
	 * 		更改单受理状态
	 * 		arrivesheetStatus	
	 * 		到达联状态
	 * 		arrivesheetActive	
	 * 		到达联有效状态
	 * 		arrivesheetDestroyed
	 * 		到达联删除状态
	 * 		exceptionType
	 * 		异常类型
	 * 		exceptionStatus	
	 * 		异常处理状态
	 * 		fastWaybillFlag		
	 * 		卡货标志
	 * 		notifySuccessFlag
	 * 		通知客户成功标志
	 * 		id		
	 * 		The id
	 * 		tSrvDeliverbillId
	 * 		派送单ID
	 * 		serialNo
	 * 		The serial no	
	 * 		arrivesheetNo	
	 * 		The arrivesheet no
	 * 		arrangeStatus	
	 * 		The arrange status
	 * 		arrangableGoodsQty	
	 * 		可排单件数
	 * 		preArrangeGoodsQty	
	 * 		预排单件数
	 * 		arrangeGoodsQty		
	 * 		排单件数
	 * 		weight		
	 * 		重量
	 * 		dimension	
	 * 		货物尺寸(长*宽*高)
	 * 		goodsName	
	 * 		货物名称
	 * 		waybillGoodsQty		
	 * 		运单件数
	 * 		transportType		
	 * 		运输方式
	 * 
	 * @param start 
	 * 			the start
	 * @param limit 
	 * 			the limit
	 * @return the list
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-11-4 
	 * 		下午4:32:27
	 */
	@Transactional
	public List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(WaybillToArrangeDto waybillToArrangeDto, int start, int limit)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		//若当前部门编码 不为空时
		if (!StringUtils.isEmpty(orgCode))
		{	
			//获取当前用户设置的当前部门
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();

			if (org != null)
			{
				String salesDepartment = org.getSalesDepartment();

				// 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
				if (FossConstants.YES.equals(salesDepartment))
				{
					waybillToArrangeDto.setOrgCode(orgCode);
					// 添加库存外场、库区默认查询条件
					List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode);
					if (CollectionUtils.isNotEmpty(list)) {
						waybillToArrangeDto.setEndStockOrgCode(list.get(0));
						waybillToArrangeDto.setGoodsAreaCode(list.get(1));
					}
				} else
				{
					// 查询排单服务外场
					OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
					String orgCode1 = null;
					if (transferCenter != null)
					{
						// 取外场驻地派送部的运单进行排单
						SaleDepartmentEntity sale = new SaleDepartmentEntity();
						sale.setTransferCenter(transferCenter.getCode());
						sale.setDelivery(FossConstants.YES);
						sale.setActive(FossConstants.ACTIVE);
						sale.setStation(FossConstants.ACTIVE);
						List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
						//若salelist不为空的话
						if (!CollectionUtils.isEmpty(salesList))
						{
							orgCode1 = salesList.get(0).getCode();
							waybillToArrangeDto.setOrgCode(orgCode1);
						}
					}
					// 添加库存外场、库区默认查询条件
					List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
					if (CollectionUtils.isNotEmpty(list)) {
						waybillToArrangeDto.setEndStockOrgCode(list.get(0));
						waybillToArrangeDto.setGoodsAreaCode(list.get(1));
					}
				}
				if (StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo())) 
				{
					String [] waybillNos = waybillToArrangeDto.getWaybillNo().split("\\n");
					String [] a = getDistinct(waybillNos);
					waybillToArrangeDto.setArrayWaybillNos(a);
				}
				
				List<WaybillToArrangeDto> waybillToArrangeDtoList = new ArrayList<WaybillToArrangeDto>();
				
				if(FossConstants.YES.equals(waybillToArrangeDto.getIsStoring()))
				{
					waybillToArrangeDtoList = this.deliverbillDetailDao.queryWaybillToArrangeByStoringCondition(waybillToArrangeDto, start, limit);
				}else{
					waybillToArrangeDtoList = this.deliverbillDetailDao.queryWaybillToArrangeByCondition(waybillToArrangeDto, start, limit);
				}
				
				//转换拼接省市区编码
				for (WaybillToArrangeDto waybillToArrangeDto2 : waybillToArrangeDtoList) {
					String custAddr = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(waybillToArrangeDto2.getReceiveCustomerProvCode(), waybillToArrangeDto2.getReceiveCustomerCityCode(), waybillToArrangeDto2.getReceiveCustomerDistCode(), waybillToArrangeDto2.getConsigneeAddress(), waybillToArrangeDto2.getConsigneeAddressNote());
					waybillToArrangeDto2.setConsigneeAddress(custAddr);
				}
				if (StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo()))  {
					HashMap<String , WaybillToArrangeDto> map = new HashMap<String, WaybillToArrangeDto>();
					for (int i = 0; i < waybillToArrangeDtoList.size(); i++) {
						map.put(waybillToArrangeDtoList.get(i).getWaybillNo(), waybillToArrangeDtoList.get(i));
					}
					List<WaybillToArrangeDto> waybillToArrangeDtoList1 = new ArrayList<WaybillToArrangeDto>();
					for (String waybill : waybillToArrangeDto.getArrayWaybillNos()) {
						if (map.containsKey(waybill)) {
							waybillToArrangeDtoList1.add(map.get(waybill));
						}
					}
					return waybillToArrangeDtoList1;
				}
				return waybillToArrangeDtoList;
			} else
			{
				return null;
			}
		} else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * 数组去重
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-26 下午2:23:49
	 */
	 static String[] getDistinct(String num[]) {
         List<String> list = new java.util.ArrayList<String>();
         for (int i = 0; i < num.length; i++) {
             if (!list.contains(num[i])) {//如果list数组不包括num[i]中的值的话，就返回true。
                 list.add(num[i]); //在list数组中加入num[i]的值。已经过滤过。
             }
         }
         return list.toArray(new String[0]);  
     }
	

	/**
	 * 根据查询条件查询待排运单数量
	 * @param waybillToArrangeDto 
	 * 		查询条件
	 * 		receiveCustomerProvCode	
	 * 收货省份
	 * 		receiveCustomerCityCode	
	 * 收货市
	 * 		receiveCustomerDistCode	
	 * 收货区(行政区域)
	 * 		deliverRegionCode	
	 * 送货小区编码
	 * 		waybillNo			
	 * 运单号
	 * 		receiveMethod		
	 * 提货方式
	 * 		receiveCustomerName	
	 * 收货客户名称
	 * 		receiveCustomerPhone 
	 * 收货客户电话
	 * 		fastWaybillCode		
	 * 精准卡航产品编码
	 * 		waybillActive		
	 * 运单有效状态
	 * 		rfcStatus		
	 * 更改单受理状态
	 * 		arrivesheetStatus	
	 * 到达联状态
	 * 		arrivesheetActive	
	 * 到达联有效状态
	 * 		arrivesheetDestroyed	
	 * 到达联删除状态
	 * 		exceptionType		
	 * 异常类型
	 * 		exceptionStatus		
	 * 异常处理状态
	 * 		fastWaybillFlag		
	 * 卡货标志
	 * 		notifySuccessFlag	
	 * 通知客户成功标志
	 * 		id		
	 * The id
	 * 		tSrvDeliverbillId	
	 * 派送单ID
	 * 		serialNo		
	 * The serial no	
	 * 		arrivesheetNo	
	 * The arrivesheet no
	 * 		arrangeStatus	
	 * The arrange status
	 * 		arrangableGoodsQty	
	 * 可排单件数
	 * 		preArrangeGoodsQty	
	 * 预排单件数
	 * 		arrangeGoodsQty		
	 * 排单件数
	 * 		weight		
	 * 重量
	 * 		dimension	
	 * 货物尺寸(长*宽*高)
	 * 		goodsName	
	 * 货物名称
	 * 		waybillGoodsQty		
	 * 运单件数
	 * 		transportType		
	 * 运输方式
	 * 
	 * @return 满足查询条件的查询待排运单数量
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-11-12
	 * 		 上午10:33:08
	 */
	@Transactional
	public Long queryWaybillToArrangeCountByCondition(WaybillToArrangeDto waybillToArrangeDto)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		//若当前部门编码不为空时
		if (!StringUtils.isEmpty(orgCode))
		{	
			//获取当前用户设置的当前部门
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
			//若用户设置的当前部门不为空
			if (org != null)
			{
				String salesDepartment = org.getSalesDepartment();

				// 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
				if (FossConstants.YES.equals(salesDepartment))
				{
					waybillToArrangeDto.setOrgCode(orgCode);
					// 添加库存外场、库区默认查询条件
					List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode);
					if (CollectionUtils.isNotEmpty(list)) {
						waybillToArrangeDto.setEndStockOrgCode(list.get(0));
						waybillToArrangeDto.setGoodsAreaCode(list.get(1));
					}
				} else
				{
					// 查询排单服务外场
					OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
					String orgCode1 = null;
					//
					if (transferCenter != null)
					{
						// 取外场驻地派送部的运单进行排单
						SaleDepartmentEntity sale = new SaleDepartmentEntity();
						sale.setTransferCenter(transferCenter.getCode());
						sale.setDelivery(FossConstants.YES);
						sale.setActive(FossConstants.ACTIVE);
						sale.setStation(FossConstants.ACTIVE);
						List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
						//若salelist集合不为空
						if (!CollectionUtils.isEmpty(salesList))
						{
							orgCode1 = salesList.get(0).getCode();
							waybillToArrangeDto.setOrgCode(orgCode1);
						}
					}
					// 添加库存外场、库区默认查询条件
					List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
					if (CollectionUtils.isNotEmpty(list)) {
						waybillToArrangeDto.setEndStockOrgCode(list.get(0));
						waybillToArrangeDto.setGoodsAreaCode(list.get(1));
					}
				}
				if (StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo())) 
				{
					waybillToArrangeDto.setArrayWaybillNos(waybillToArrangeDto.getWaybillNo().split("\\n"));
				}
				if(FossConstants.YES.equals(waybillToArrangeDto.getIsStoring()))
				{
					return this.deliverbillDetailDao.queryWaybillToArrangeCountByStoringCondition(waybillToArrangeDto);
				}else{
					return this.deliverbillDetailDao.queryWaybillToArrangeCountByCondition(waybillToArrangeDto);
				}
			} else
			{
				return 0L;
			}
		} else
		{
			return 0L;
		}
	}

	/**
	 * 添加运单至派送单.
	 *
	 * @param deliverbill 
	 * 		派送单
	 * 	deliverbillNo
	 *  	派送单号
	 * 	vehicleNo
	 * 		车牌号	
	 * 	driverCode
	 * 		司机工号
	 * 	driverName
	 * 		司机姓名
	 * 	waybillQtyTotal
	 * 		总票数
	 *  goodsQtyTotal
	 *  	总件数
	 * 	payAmountTotal
	 * 		总到付金额
	 * 	weightTotal
	 * 		总重量
	 * 	volumeTotal
	 * 		总体积
	 * 	createUserName
	 * 		创建人
	 * 	createUserCode
	 * 		创建人编码
	 * 	submitTime	
	 * 		创建时间(提交时间)
	 * 	tOptTruckDepartId
	 * 		车辆放行ID
	 * 	status
	 * 		状态
	 * 	createOrgName	
	 * 		创建部门
	 * 	createOrgCode
	 * 		创建部门编码
	 * 	operator
	 * 		操作人
	 * 	operatorCode	
	 * 		操作人编码
	 * 	operateOrgName
	 * 		操作部门
	 * 	operateOrgCode
	 * 		操作部门编码
	 * 	operateTime
	 * 		操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 		卡货票数
	 * 	transferCenter
	 * 		车队服务外场
	 * 	currencyCode
	 * 		币种
	 * 	deliveryDepartment
	 * 		派送部
	 * 	driverTel
	 * 		司机电话号码
	 * 	motorcade
	 * 		车队
	 * 	loadingRate
	 * 		装载率(重量/体积)
	 * 
	 * @param waybillToArrangeDtoList 添加的运单列表
	 * @param currentInfo the current info
	 * @return 添加失败的运单列表
	 * @author ibm-wangxiexu
	 * @date 2012-11-13 下午2:21:41
	 */
	@Override
	public List<WaybillToArrangeDto> addWaybillToArrange(DeliverbillEntity deliverbill, List<WaybillToArrangeDto> waybillToArrangeDtoList, CurrentInfo currentInfo)
	{
		// 定义排单失败的运单列表，作为返回
		List<WaybillToArrangeDto> waybillListFailed = new ArrayList<WaybillToArrangeDto>();
		
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		int i = 0;
		//循环运单列表
		for (WaybillToArrangeDto waybill : waybillToArrangeDtoList)
		{
			// 单票运单排单
			WaybillToArrangeDto waybillFailed = deliverbillService.arrangeWaybill(deliverbill, waybill);
			//若单票运单排单不为空
			if (waybillFailed != null)
			{
				//添加到定义失败的运单列表
				waybillListFailed.add(waybillFailed);
			}
			i++;
		}
		Map<BusinessMonitorIndicator, Number> map = new HashMap<BusinessMonitorIndicator, Number>();
		map.put(BusinessMonitorIndicator.DELIVERY_SCHEDULE_COUNT, i);
		businessMonitorService.counter(map, currentInfo);
		return waybillListFailed;
	}

	/**
	 * 运单排单.
	 *
	 * @param deliverbill 
	 * 		派送单
	 * 	deliverbillNo 	
	 * 		派送单号
	 * 	vehicleNo	
	 * 		车牌号	
	 * 	driverCode
	 * 		司机工号
	 * 	driverName
	 * 		司机姓名
	 * 	waybillQtyTotal
	 * 		总票数
	 *  goodsQtyTotal	
	 *  	总件数
	 * 	payAmountTotal	
	 * 		总到付金额
	 * 	weightTotal		
	 * 		总重量
	 * 	volumeTotal		
	 * 		总体积
	 * 	createUserName
	 * 		创建人
	 * 	createUserCode
	 * 		创建人编码
	 * 	submitTime	
	 *		创建时间(提交时间)
	 * 	tOptTruckDepartId
	 * 		车辆放行ID
	 * 	status	
	 * 		状态
	 * 	createOrgName
	 * 		创建部门
	 * 	createOrgCode
	 * 		创建部门编码
	 * 	operator
	 * 		操作人
	 * 	operatorCode	
	 * 		操作人编码
	 * 	operateOrgName	
	 * 		操作部门
	 * 	operateOrgCode	
	 * 		操作部门编码
	 * 	operateTime		
	 * 		操作时间(确认时间)
	 * 	fastWaybillQty
	 * 		卡货票数
	 * 	transferCenter
	 * 		车队服务外场
	 * 	currencyCode
	 * 		币种
	 * 	deliveryDepartment
	 * 		派送部
	 * 	driverTel
	 * 		司机电话号码
	 * 	motorcade
	 * 		车队
	 * 	loadingRate
	 * 		装载率(重量/体积)
	 * 
	 * @param waybill 
	 * 		待排运单
	 * 		receiveCustomerProvCode	
	 * 		收货省份
	 * 		receiveCustomerCityCode	
	 * 		收货市
	 * 		receiveCustomerDistCode	
	 * 		收货区(行政区域)
	 * 		deliverRegionCode
	 * 		送货小区编码
	 * 		waybillNo
	 * 		运单号
	 * 		receiveMethod		
	 * 		提货方式
	 * 		receiveCustomerName	
	 *		 收货客户名称
	 * 		receiveCustomerPhone 
	 * 		收货客户电话
	 * 		fastWaybillCode		
	 * 		精准卡航产品编码
	 * 		waybillActive		
	 *		 运单有效状态
	 * 		rfcStatus		
	 * 		更改单受理状态
	 * 		arrivesheetStatus	
	 * 		到达联状态
	 * 		arrivesheetActive	
	 * 		到达联有效状态
	 * 		arrivesheetDestroyed	
	 * 		到达联删除状态
	 * 		exceptionType		
	 * 		异常类型
	 * 		exceptionStatus		
	 * 		异常处理状态
	 * 		fastWaybillFlag		
	 * 		卡货标志
	 * 		notifySuccessFlag	
	 * 		通知客户成功标志
	 * 		id		
	 * 		The id
	 * 		tSrvDeliverbillId	
	 * 		派送单ID
	 * 		serialNo		
	 * 		The serial no	
	 * 		arrivesheetNo	
	 * 		The arrivesheet no
	 * 		arrangeStatus	
	 * 		The arrange status
	 * 		arrangableGoodsQty	
	 * 		可排单件数
	 * 		preArrangeGoodsQty	
	 * 		预排单件数
	 * 		arrangeGoodsQty		
	 * 		排单件数
	 * 		weight		
	 * 		重量
	 * 		dimension	
	 * 		货物尺寸(长*宽*高)
	 * 		goodsName	
	 * 		货物名称
	 * 		waybillGoodsQty		
	 * 		运单件数
	 * 		transportType		
	 * 		运输方式
	 * 
	 * @return 若排单成功，
	 * 		则返回null；
	 * 		否则返回排单失败信息
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-11-13 
	 * 		下午2:47:15
	 */
	@Transactional
	public WaybillToArrangeDto arrangeWaybill(DeliverbillEntity deliverbill, WaybillToArrangeDto waybill)
	{
		MutexElement mutexElement = new MutexElement(waybill.getWaybillNo(), "运单排单", MutexElementType.DELIVERBILLDETAIL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
		}
		
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybill.getWaybillNo());
		if(!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType()) 
		  && !WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType()))
		{
			//解锁
			businessLockService.unlock(mutexElement);
			//抛出异常 参数错误
			throw new DeliverbillException(DeliverbillException.WAYBILLSTATE_CANT_OPERATE);
		}	
		
		/**
		 * BUG-53568 排单时 再校验一遍签收结果
		 */
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		entity.setWaybillNo(waybill.getWaybillNo());
		entity.setActive(FossConstants.ACTIVE); 
		WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		if(waybillSignResultEntity != null  && SignConstants.SIGN_STATUS_ALL.equals(waybillSignResultEntity.getSignStatus()))
		{
			//解锁
			businessLockService.unlock(mutexElement);
			//抛出异常 参数错误
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_CANT_OPERATE);
		}
		
		ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(waybill.getWaybillNo());

		if(actualFreight!=null){
			if(actualFreight.getEverTelnoticeSuccess() != null && FossConstants.ACTIVE.equals(actualFreight.getEverTelnoticeSuccess()))
			{
				actualFreight.setArriveGoodsQty(waybill.getWaybillGoodsQty());
			}
			Integer arrangableGoodsQty = actualFreight.getArriveGoodsQty() - actualFreight.getArrangeGoodsQty();
				
			if (arrangableGoodsQty >= waybill.getPreArrangeGoodsQty())
			{
				// 运单可排单
				waybill.settSrvDeliverbillId(deliverbill.getId());

				// 根据派送单编号和运单号查询派送单明细
				DeliverbillDetailEntity deliverbillDetail = this.deliverbillDetailDao.queryByCondition(waybill);
				//若派送单明细不为空
				if (deliverbillDetail != null)
				{
					// 运单已部分排单
					// 更新派送单的统计信息
					this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.DELETE_OPERATION);

					// 更新派送单明细的排单数量
					deliverbillDetail.setPreArrangeGoodsQty(deliverbillDetail.getPreArrangeGoodsQty() + waybill.getPreArrangeGoodsQty());

					// 重置派送单明细的件数/体积/重量
					this.updateDeliverbillDetailStat(deliverbillDetail, deliverbillDetail.getPreArrangeGoodsQty(), waybill.getWaybillGoodsQty(), waybill.getWeight(), waybill.getGoodsVolumeTotal());
					//更新派送单明细
					this.deliverbillDetailDao.update(deliverbillDetail);
					
				} else
				{
					// 新建派送单明细
					deliverbillDetail = new DeliverbillDetailEntity();

					try
					{	
						BeanUtils.copyProperties(deliverbillDetail, waybill);

						// 更新排单重量/体积/序号
						deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybill.getWeight().multiply(new BigDecimal(waybill.getPreArrangeGoodsQty())),
								new BigDecimal(waybill.getWaybillGoodsQty()), DeliverbillConstants.WEIGHT_PRECISION));
						
						deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybill.getGoodsVolumeTotal().multiply(new BigDecimal(waybill.getPreArrangeGoodsQty())),
								new BigDecimal(waybill.getWaybillGoodsQty()), DeliverbillConstants.VOLUME_PRECISION));

						deliverbillDetail.setSerialNo(this.deliverbillDetailDao.queryMaxSerialNoByDeliverbillId(deliverbill.getId()) + 1);
					} catch (IllegalAccessException e)
					{	
						//日志记录
						LOGGER.error(e.getMessage(), e);
						waybill.setFailedToArrangeReason(DeliverbillService.FAILED_REASON_RUNTIME_EXCEPTION);
						return waybill;
					} catch (InvocationTargetException e)
					{	
						//日志记录
						LOGGER.error(e.getMessage(), e);
						
						waybill.setFailedToArrangeReason(DeliverbillService.FAILED_REASON_RUNTIME_EXCEPTION);
						return waybill;
					}
					
					deliverbillDetail.setArrangeGoodsQty(deliverbillDetail.getPreArrangeGoodsQty());

					this.deliverbillDetailDao.add(deliverbillDetail);

				}

				// 更新派送单的统计信息
				this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.ADD_OPERATION);

				this.deliverbillDao.update(deliverbill);

				// 更新运单排单件数
				String actualFreightId = actualFreight.getId();
				int newArrangeGoodsQty = actualFreight.getArrangeGoodsQty() + waybill.getPreArrangeGoodsQty();

				actualFreight = new ActualFreightEntity();
				actualFreight.setId(actualFreightId);
				actualFreight.setArrangeGoodsQty(newArrangeGoodsQty);

				this.actualFreightDao.updateByPrimaryKeySelective(actualFreight);
				//解锁
				businessLockService.unlock(mutexElement);
				return null;
			} else
			{
				// 运单不可排单
				if (arrangableGoodsQty == 0)
				{
					waybill.setFailedToArrangeReason(DeliverbillService.FAILED_REASON_GOODS_ARRANGED);
				} else
				{	
					waybill.setFailedToArrangeReason(DeliverbillService.FAILED_REASON_NOT_ENOUGH_TO_ARRANGE);
					waybill.setArrangableGoodsQty(arrangableGoodsQty);
				}
				//解锁
				businessLockService.unlock(mutexElement);
				return waybill;
			}
		}else{
			//解锁
			businessLockService.unlock(mutexElement);
			throw new DeliverbillException(DeliverbillException.ACTUALFREIGHT_NOT_FOUND_ERROR); 
		}
	}

	/**
	 * 重置派送单明细的件数/体积/重量.
	 *
	 * @param deliverbillDetail 
	 * 更改前的派送单明细
	 * 		id	
	 * the id 
	 * 		tSrvDeliverbillId	
	 * 派送单ID
	 * 		serialNo			
	 * 序号
	 * 		arrivesheetNo		
	 * 到达联编号
	 * 		waybillNo			
	 * 运单号
	 * 		arrangeStatus		
	 * 排单状态	
	 * 		preArrangeGoodsQty	
	 * 预排单件数
	 * 		arrangeGoodsQty		
	 * 确认排单件数
	 * 		weight				
	 * 重量
	 * 		dimension			
	 * 尺寸
	 * 		goodsName			
	 * 货物名称
	 * 		waybillGoodsQty		
	 * 运单件数
	 * 		transportType		
	 * 运输方式
	 * 		arriveTime			
	 * 到达时间
	 * 		consignee			
	 * 收货人
	 * 		consigneeContact	
	 * 收货人联系方式
	 * 		consigneeAddress	
	 * 收货人地址
	 * 
	 * @param arrangeGoodsQty 
	 * 更改后的排单件数
	 * @param waybillGoodsQty 
	 * 运单件数
	 * @param waybillWeight 
	 * 运单重量
	 * @param waybillVolume 
	 * 运单体积
	 * @return 
	 * 更新后的派送单明细信息
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-12-3
	 * 		 下午7:10:36
	 */
	private DeliverbillDetailEntity updateDeliverbillDetailStat(DeliverbillDetailEntity deliverbillDetail, int arrangeGoodsQty, int waybillGoodsQty, BigDecimal waybillWeight, BigDecimal waybillVolume)
	{
		deliverbillDetail.setArrangeGoodsQty(arrangeGoodsQty);

		// 更新排单重量/体积
		deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybillWeight.multiply(new BigDecimal(arrangeGoodsQty)), new BigDecimal(waybillGoodsQty), DeliverbillConstants.WEIGHT_PRECISION));

		deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybillVolume.multiply(new BigDecimal(arrangeGoodsQty)), new BigDecimal(waybillGoodsQty),
				DeliverbillConstants.VOLUME_PRECISION));

		return deliverbillDetail;
	}

	/**
	 * 保存/提交派送单时调用.
	 *
	 * @param 
	 * deliverbill 
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @return the deliverbill entity
	 * @author 043258-foss-
	 * 					zhaobin
	 * @date 2013-2-28 下午1:48:56
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#saveDeliverbill(com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity)
	 */
	@Override
	@Transactional
	public DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill)
	{
		MutexElement mutexElement = new MutexElement(deliverbill.getDeliverbillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//若未上锁
		if(!isLocked){
			//抛出派送单异常
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
		}
		DeliverbillEntity deliverbillEntity = null;
		//特为修改确认中的派送单传递到Gps的变量
		DeliverbillEntity deliverbillForGps = null;
		//若派送单id号为空时
		if (deliverbill.getId() == null || "".equals(deliverbill.getId()))
		{	
			//更新派送单状态
			deliverbill.setStatus(DeliverbillConstants.STATUS_SAVED);
			this.updateDeliverbillCreateInfo(deliverbill);
			deliverbillEntity = this.deliverbillDao.add(deliverbill);
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(deliverbillEntity.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbillEntity.getStatus()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(deliverbillEntity.getDeliverbillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(deliverbillEntity.getStatus());//派送单状态
				deliverBillVary.setOperatorName(deliverbillEntity.getOperator());//操作人
				deliverBillVary.setOperatorCode(deliverbillEntity.getOperatorCode());//操作人编码
				deliverBillVary.setOperateOrgName(deliverbillEntity.getOperateOrgName());//操作部门
				deliverBillVary.setOperateOrgCode(deliverbillEntity.getOperateOrgCode());//操作部门编码
				//添加纪录
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
			deliverbillForGps = deliverbill;
		} else
		{
			this.updateDeliverbillOperateInfo(deliverbill);
			deliverbillEntity = this.deliverbillDao.update(deliverbill);
		
			//发现上面的deliverbillEntity数据不存在，所以重新查询一次吧
			deliverbillForGps = deliverbillDao.queryById(deliverbillEntity.getId());
			
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
			String areaCode = null;
			if (CollectionUtils.isNotEmpty(list)) {
				   //传入库区code
				   areaCode = list.get(1);
			}
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("deliverbillId", deliverbill.getId());
			map.put("areaCode", areaCode);
			
			// 得到派送单明细
			List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillDetailDao.queryByDeliverbillId(map);

			for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList)
			{
				ArriveSheetEntity entity = new ArriveSheetEntity();
				entity.setWaybillNo(deliverbillDetail.getWaybillNo());
				// 生成到达联
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
			}
			//只能是派送单确认之后才能修改派送单
			if(DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbill.getDelStatus())){
				LOGGER.info("Foss修改送货任务至Gps开始");
				//判断车辆是否是公司的车辆
				String queryVehicleType = leasedVehicleService.queryVehicleType(deliverbill.getVehicleNo());
				if(queryVehicleType != null && !ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)){
					//是公司的车辆就走下面的逻辑
					//修改派送单
					com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
							gpsDeliverService.syscModifyDeliverTaskToGps(deliverbillForGps, null);
					if(ResultDto.FAIL.equals(resultDto.getCode())){
						LOGGER.info("Foss修改送货任务至Gps失败，错误详情:"+resultDto.getMsg());
					}
				//add by 329757
				}else if(queryVehicleType != null && ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)){
					com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
							gpsDeliverService.syscModifyDeliverTaskToGps(deliverbillForGps, null);
					if(ResultDto.FAIL.equals(resultDto.getCode())){
						LOGGER.info("Foss修改送货任务至Gps失败，错误详情:"+resultDto.getMsg());
					}
				}
				LOGGER.info("Foss修改送货任务至Gps结束");
			}
		}
		//解锁
		businessLockService.unlock(mutexElement);
		return deliverbillEntity;
	}

	/**
	 * 更新派送单修改信息.
	 *
	 * @param deliverbill 
	 * 待修改的派送单
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @return 带操作时间/
	 * 操作人/
	 * 操作部门的派送单
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-11-22
	 * 		 下午6:44:51
	 */
	private DeliverbillEntity updateDeliverbillOperateInfo(DeliverbillEntity deliverbill)
	{
		UserEntity currentUser = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();

		if (currentUser != null)
		{
			EmployeeEntity employee = currentUser.getEmployee();
			//如果雇员不为空时
			if (employee != null)
			{	
				//更新操作人/操作人编码/修改人号的信息
				deliverbill.setModifyUser(employee.getEmpCode());
				deliverbill.setOperatorCode(employee.getEmpCode());
				deliverbill.setOperator(employee.getEmpName());
			}
		}
		
		// 仅当保存和提交环节计算派送外场
		if (currentOrg != null && (DeliverbillConstants.STATUS_SAVED.equals(deliverbill.getStatus()) || DeliverbillConstants.STATUS_SUBMITED.equals(deliverbill.getStatus())))
		{
			deliverbill.setOperateOrgCode(currentOrg.getCode());
			deliverbill.setOperateOrgName(currentOrg.getName());

			// 查询车队服务外场，设置派送单派送外场
			OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currentOrg.getCode());

			if (transferCenter != null)
			{
				deliverbill.setTransferCenter(transferCenter.getCode());
			}else{
				deliverbill.setTransferCenter(currentOrg.getCode());
			}
		}
		Date now = new Date();
		//更新操作/修改的时间日期
		deliverbill.setOperateTime(now);
		deliverbill.setModifyDate(now);

		
		
		//BUG-38291  当派送单为已下拉状态时  不能再修改状态
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		
		/* (DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) */
		if(deliverbillEntity!= null && DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus()))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		if(deliverbillEntity!= null && !DeliverbillConstants.STATUS_SAVED.equals(deliverbillEntity.getStatus()))
		{
			deliverbill.setStatus(null);
		}

		return deliverbill;
	}
	
	/**
	 * 
	 * 确认时调用此方法修改派送单信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-12 下午1:15:26
	 */
	private DeliverbillEntity updateDeliverbillOperateInfoForComfirm(DeliverbillEntity deliverbill)
	{
		UserEntity currentUser = FossUserContext.getCurrentUser();
		if (currentUser != null)
		{
			EmployeeEntity employee = currentUser.getEmployee();
			//如果雇员不为空时
			if (employee != null)
			{	
				//更新操作人/操作人编码/修改人号的信息
				deliverbill.setModifyUser(employee.getEmpCode());
				deliverbill.setOperatorCode(employee.getEmpCode());
				deliverbill.setOperator(employee.getEmpName());
			}
		}
		
		Date now = new Date();
		//更新操作/修改的时间日期
		deliverbill.setOperateTime(now);
		deliverbill.setModifyDate(now);

		return deliverbill;
	}
	

	/**
	 * 更新派送单创建/修改信息.
	 *
	 * @param deliverbill 
	 * 待修改的派送单
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @return 带创建/
	 * 操作时间/
	 * 操作人/
	 * 操作部门的派送单
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-11-22 
	 * 		下午6:44:51
	 */
	private DeliverbillEntity updateDeliverbillCreateInfo(DeliverbillEntity deliverbill)
	{
		UserEntity currentUser = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();

		if (currentUser != null)
		{
			EmployeeEntity employee = currentUser.getEmployee();
			//如果雇员不为空的话
			if (employee != null)
			{	
				
				deliverbill.setCreateUser(employee.getEmpCode());
				deliverbill.setCreateUserCode(employee.getEmpCode());
				deliverbill.setCreateUserName(employee.getEmpName());
				deliverbill.setModifyUser(employee.getEmpCode());
				deliverbill.setOperatorCode(employee.getEmpCode());
				deliverbill.setOperator(employee.getEmpName());
			}
		}

		if (currentOrg != null)
		{
			deliverbill.setCreateOrgCode(currentOrg.getCode());

			// 查询车队服务外场，设置派送单派送外场
			OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currentOrg.getCode());

			if (transferCenter != null)
			{
				deliverbill.setTransferCenter(transferCenter.getCode());
			}

			deliverbill.setCreateOrgName(currentOrg.getName());
			deliverbill.setOperateOrgCode(currentOrg.getCode());
			deliverbill.setOperateOrgName(currentOrg.getName());
		}

		Date now = new Date();
		deliverbill.setCreateDate(now);
		deliverbill.setOperateTime(now);
		deliverbill.setModifyDate(now);

		// submitTime为第一次保存的时间
		if (deliverbill.getSubmitTime() == null)
		{
			deliverbill.setSubmitTime(now);
		}

		return deliverbill;
	}

	/**
	 * Sets 
	 * the 
	 * owndriver service.
	 *
	 * @param ownDriverService 
	 * the 
	 * new own driver service
	 */
	public void setOwnDriverService(IOwnDriverService ownDriverService)
	{
		this.ownDriverService = ownDriverService;
	}

	/**
	 * 拼接页面信息.
	 *
	 * @param objects 
	 * the objects
	 * @return the string
	 * @author ibm-
	 * wangfei
	 * @date Nov 27, 
	 * 2012 3:50:11 PM
	 */
	private String joinString(Object... objects)
	{
		StringBuffer sb = new StringBuffer();
		for (Object o : objects)
		{
			if (o != null && StringUtil.isNotEmpty(o.toString()))
			{
				sb.append(o.toString()).append(DeliverbillConstants.SPLIT_CHAR);
			}
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
	}

	/**
	 * Query load task detail list by task no.
	 *
	 * @param taskNo 
	 * the 
	 * task no
	 * @return 
	 * the
	 * list
	 */
	@Override
	public List<LoadTaskDetailDto> queryLoadTaskDetailListByTaskNo(String taskNo)
	{
		List<LoadTaskDetailDto> loadTaskDetailList = new ArrayList<LoadTaskDetailDto>();

		if (taskNo != null)
		{
			// 调用中转接口，根据装车任务号查询装车任务明细
			List<LoadWayBillDetailDto> loadWaybillDetailList = this.loadTaskQueryService.queryLoadWayBillByTaskNo(taskNo);

			if (CollectionUtils.isNotEmpty(loadWaybillDetailList))
			{
				for (LoadWayBillDetailDto loadWaybillDetail : loadWaybillDetailList)
				{
					if (loadWaybillDetail.getStockVolume()!=null) {
						loadWaybillDetail.setStockVolume(loadWaybillDetail.getStockVolume().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if (loadWaybillDetail.getStockWeight()!=null) {
						loadWaybillDetail.setStockWeight(loadWaybillDetail.getStockWeight().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					LoadTaskDetailDto loadTaskDetailDto = new LoadTaskDetailDto();
					try
					{
						BeanUtils.copyProperties(loadTaskDetailDto, loadWaybillDetail);
						loadTaskDetailDto.setOrigOrgCode(loadWaybillDetail.getOrigOrgName());
						loadTaskDetailList.add(loadTaskDetailDto);
					} catch (IllegalAccessException e)
					{
						LOGGER.error("error", e);
					} catch (InvocationTargetException e)
					{
						LOGGER.error("error", e);
					}
				}
			}
		}

		return loadTaskDetailList;
	}

	/**
	 * Query load task by deliverbill no.
	 *
	 * @param deliverbillNo 
	 * the 
	 * deliverbill no
	 * @return 
	 * the 
	 * load task dto
	 */
	@Override
	public LoadTaskDto queryLoadTaskByDeliverbillNo(String deliverbillNo)
	{
		// 构造查询条件
		LoadTaskDto loadTaskDto = new LoadTaskDto();
		loadTaskDto.setDeliverbillNo(deliverbillNo);
		loadTaskDto.setLoadGaprepValid(LoadConstants.DELIVER_LOAD_GAP_REPORT_VALID);

		// 可查看已完成和已确认的装车任务
		List<String> stateList = new ArrayList<String>();
		stateList.add(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		stateList.add(LoadConstants.LOAD_TASK_STATE_SUBMITED);
		loadTaskDto.setStateList(stateList);

		return this.deliverbillDao.queryLoadTaskByDeliverbillNo(loadTaskDto);
	}

	/**
	 * 根据差异报告ID查询装车差异报告明细.
	 *
	 * @param loadGaprepId 差异报告ID
	 * @return 装车差异报告明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-11-30 
	 * 下午5:21:46
	 */
	@Override
	public List<LoadGaprepWaybillDto> queryLoadGaprepWaybillListByRepId(String loadGaprepId,String deliverbillNo)
	{
		List<LoadGaprepWaybillDto> loadGaprepWaybillList = new ArrayList<LoadGaprepWaybillDto>();

		if (StringUtils.isNotEmpty(deliverbillNo))
		{
			DeliverLoadGapReportEntity deliverLoadGapReport = new DeliverLoadGapReportEntity();
			deliverLoadGapReport.setId(loadGaprepId);
			deliverLoadGapReport.setDeliverBillNo(deliverbillNo);

			List<DeliverLoadGapReportWayBillEntity> deliverLoadGapReportWayBillList = this.deliverLoadGapReportService.queryDeliverLoadGapReportWayBills(deliverLoadGapReport);

			if (CollectionUtils.isNotEmpty(deliverLoadGapReportWayBillList))
			{
				for (DeliverLoadGapReportWayBillEntity deliverLoadGapReportWayBill : deliverLoadGapReportWayBillList)
				{
					// 取差异件数的绝对值
					/**if(deliverLoadGapReportWayBill.getLackGoodsQty() < 0){
						deliverLoadGapReportWayBill.setLackGoodsQty(-deliverLoadGapReportWayBill.getLackGoodsQty());
					}*/
					
					LoadGaprepWaybillDto loadGaprepWaybill = new LoadGaprepWaybillDto();
					try
					{
						BeanUtils.copyProperties(loadGaprepWaybill, deliverLoadGapReportWayBill);
						loadGaprepWaybillList.add(loadGaprepWaybill);
					} catch (IllegalAccessException e)
					{
						LOGGER.error("error", e);
					} catch (InvocationTargetException e)
					{
						LOGGER.error("error", e);
					}
				}
			}
		}

		return loadGaprepWaybillList;
	}

	/**
	 * 确认派送装车报告.
	 *
	 * @param deliverbillNo 派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-11-30 
	 * 下午5:21:46
	 */
	@Override
	@Transactional
	public int comfirmLoadTask(String deliverbillNo)
	{
		return this.deliverLoadTaskService.comfirmLoadTask(deliverbillNo);
	}

	/**
	 * 退回派送装车报告.
	 *
	 * @param deliverbillNo 派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-11-30
	 *  下午5:21:46
	 */
	@Override
	@Transactional
	public int rejectLoadTask(String deliverbillNo)
	{
		DeliverbillEntity entity = new DeliverbillEntity();
		entity.setDeliverbillNo(deliverbillNo);
		// 将状态改为 "装车中"
		entity.setStatus(DeliverbillConstants.STATUS_LOADING);
		deliverbillDao.updateStatusByDeliverbillNo(entity);
		//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
		if (StringUtils.isNotBlank(entity.getDeliverbillNo()) && StringUtils.isNotBlank(entity.getStatus()) ) {
			DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
			deliverBillVary.setDeliverBillNo(entity.getDeliverbillNo());//派送单号
			deliverBillVary.setDeliverBillStatus(entity.getStatus());//派送单状态
			//添加纪录
			deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
		}
		return this.deliverLoadTaskService.takeBackLoadTask(deliverbillNo);
	}

	/**
	 * 确认预派送单.
	 *
	 * @param deliverbillDto 
	 * 派送单，包含了派送单ID,派送单司机(编码和姓名)信息
	 * 		id		
	 * 派送单ID
	 * 		deliverbillNo	
	 * 派送单编号
	 * 		vehicleNo		
	 * 车辆车牌号
	 * 		driverName		
	 * 司机姓名
	 * 		driverCode		
	 * 司机工号
	 * 		status			
	 * 派送单状态
	 * 		submitTimeBegin	
	 * 查询条件 提交开始时间
	 * 		submitTimeEnd	
	 * 查询条件 提交结束时间
	 * 		createTimeBegin	
	 * 查询条件 创建开始时间
	 * 		createTimeEnd	
	 * 查询条件 创建结束时间
	 * 		createUserName	
	 * 提交人
	 * 		submitTime		
	 * 提交时间
	 * 		operateTime		
	 * 确认时间
	 * 		createOrgName	
	 * 创建部门
	 * 		createOrgCode	
	 * 创建部门编码
	 * 		weightTotal		
	 * 总重量
	 * 		volumeTotal		
	 * 总体积
	 * 		deliverWaybillQty	
	 * 派送成功票数
	 * 		pullbackWaybillQty	
	 * 派送拉回票数
	 * 		orgCode			
	 * 部门Code、
	 * 
	 * @return 若成功，
	 * 返回大于0；
	 * 否则返回0
	 * @author ibm-
	 * 			wangxiexu
	 * @date 2012-12-3 
	 * 上午11:52:10
	 */
	@Override
	@Transactional
	public int confirmDeliverbill(DeliverbillDto deliverbillDto,String taskNo)
	{
		String deliverbillId = deliverbillDto.getId();
		DeliverbillEntity deliverbill = this.deliverbillDao.queryById(deliverbillId);
		String deliverbillNo = deliverbill.getDeliverbillNo();
		
		//判断派送单状态,如果已经确认就不再执行下面操作
		if (DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbill.getStatus())) {
			throw new DeliverbillException("派送单已经确认, 请关闭该页面刷新查看!");
		}
		
		// 根据差异报告，更新派送单明细和派送单信息
		LoadTaskDto loadTaskDto = this.queryLoadTaskByDeliverbillNo(deliverbillNo);
		if (loadTaskDto != null)
		{
			List<LoadGaprepWaybillDto> loadGaprepWaybillDtoList = this.queryLoadGaprepWaybillListByRepId(loadTaskDto.getLoadGaprepId(),deliverbillNo);
			if (CollectionUtils.isNotEmpty(loadGaprepWaybillDtoList))
			{
				WaybillToArrangeDto condition = new WaybillToArrangeDto();

				for (LoadGaprepWaybillDto loadGaprepWaybillDto : loadGaprepWaybillDtoList)
				{
					condition.settSrvDeliverbillId(deliverbillId);
					condition.setWaybillNo(loadGaprepWaybillDto.getWayBillNo());

					// 根据派送单编号和运单号查询派送单明细
					DeliverbillDetailEntity deliverbillDetail = this.deliverbillDetailDao.queryByCondition(condition);

					if (deliverbillDetail != null)
					{
						// condition 1 : 如果有差异的运单在预排单列表中
						// 注意：中转提供的差异件数=预排单件数-实际排单件数，因此多货时候差异为负数，少货时候，差异为正数
						int arrangeGoodsQty = deliverbillDetail.getArrangeGoodsQty() - loadGaprepWaybillDto.getLackGoodsQty();

						if (arrangeGoodsQty < 0)
						{
							// 排单件数小于0，抛出异常
							throw new DeliverbillException(DeliverbillException.ARRANGEGOODSQTY_LTZERO);
						} else if (arrangeGoodsQty == 0)
						{
							// 排单件数等于0，删除派送单明细 ，同时更新派送单信息
							this.deliverbillDetailDao.delete(deliverbillDetail.getId());
							
							// 调整运单的可排单数量
							this.updateActualFreightArrangeGoodsQty(deliverbillDetail.getWaybillNo(), loadGaprepWaybillDto.getLackGoodsQty() * DeliverbillService.MINUS_ONE);

							// 更新派送单的统计信息
							this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.DELETE_OPERATION);
						} else
						{
							// 排单件数大于0，更新派送单明细排单件数，同时更新派送单信息
							// 更新派送单的统计信息
							this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.DELETE_OPERATION);
							ActualFreightEntity actualFreightEntity = this.actualFreightDao.queryByWaybillNo(deliverbillDetail.getWaybillNo());
							// 调整运单的可排单数量
							if (actualFreightEntity != null) {
								String id =actualFreightEntity.getId();
								int newArrangeGoodsQty = actualFreightEntity.getArrangeGoodsQty() + loadGaprepWaybillDto.getLackGoodsQty() * DeliverbillService.MINUS_ONE;
								if(arrangeGoodsQty>actualFreightEntity.getGoodsQty()){
									newArrangeGoodsQty=newArrangeGoodsQty-(arrangeGoodsQty-actualFreightEntity.getGoodsQty());
									arrangeGoodsQty=actualFreightEntity.getGoodsQty();
								}
								// 需要保证已排单件数大于0，且小于开单件数
								//newArrangeGoodsQty = newArrangeGoodsQty > actualFreightEntity.getGoodsQty() ? actualFreightEntity.getGoodsQty() : newArrangeGoodsQty;
								//newArrangeGoodsQty = newArrangeGoodsQty > 0 ? newArrangeGoodsQty : arrangeGoodsQty;
								actualFreightEntity = new ActualFreightEntity();
								actualFreightEntity.setId(id);
								actualFreightEntity.setArrangeGoodsQty(newArrangeGoodsQty);

								 this.actualFreightDao.updateByPrimaryKeySelective(actualFreightEntity);
							} else {
								throw new DeliverbillException(DeliverbillException.ACTUALFREIGHT_NOT_FOUND_ERROR);
							}
							// 更新排单重量/体积
							deliverbillDetail.setWeight(BigDecimalOperationUtil.div(deliverbillDetail.getWeight().multiply(new BigDecimal(arrangeGoodsQty)),
									new BigDecimal(deliverbillDetail.getArrangeGoodsQty()), DeliverbillConstants.WEIGHT_PRECISION));
							
							deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(deliverbillDetail.getGoodsVolumeTotal().multiply(new BigDecimal(arrangeGoodsQty)), new BigDecimal(
									deliverbillDetail.getArrangeGoodsQty()), DeliverbillConstants.VOLUME_PRECISION));
							
							// 更新派送单明细的排单数量
							deliverbillDetail.setArrangeGoodsQty(arrangeGoodsQty);
							
							deliverbillDetail = this.deliverbillDetailDao.update(deliverbillDetail);
								
							if (deliverbillDetail == null)
							{
								throw new DeliverbillException(DeliverbillException.UPDATEDELIVERBILL_ERROR);
							}
							

							// 更新派送单的统计信息
							this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.ADD_OPERATION);
						}
					} else{
						// condition 2 : 如果有差异的运单不在预排单列表中，说明是装车多货
						// 注意：中转提供的差异件数=预排单件数-实际排单件数，因此多货时候差异为负数，少货时候，差异为正数
						int arrangeGoodsQty = -loadGaprepWaybillDto.getLackGoodsQty();
						String waybillNo = loadGaprepWaybillDto.getWayBillNo();
						
						if (arrangeGoodsQty <= 0)
						{
							// 排单件数小于或等于0为异常情况，为兼容此种情况，不抛异常
						} else{
							// 如果有差异的运单不在预排单列表中，说明是装车多货
							MutexElement deliverbillDetailMutexElement = new MutexElement(waybillNo, "运单排单", MutexElementType.DELIVERBILLDETAIL_NO);
							// 互斥锁定
							boolean dbdMutexisLocked = businessLockService.lock(deliverbillDetailMutexElement, ZERO);
							// 如果没有上锁
							if (!dbdMutexisLocked) {
								throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
							}

							ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(waybillNo);
							WaybillEntity waybill = this.waybillManagerService.queryWaybillBasicByNo(waybillNo);
							ArriveSheetEntity arriveSheet = null;
							List<ArriveSheetEntity> arriveSheetList = this.arriveSheetManngerService.queryArriveSheetByWaybillNo(new ArriveSheetEntity(waybillNo, ArriveSheetConstants.STATUS_GENERATE,
									FossConstants.YES, FossConstants.NO));
							if (CollectionUtils.isNotEmpty(arriveSheetList)) {
								arriveSheet = arriveSheetList.get(0);
							}

							// 新建派送单明细
							deliverbillDetail = this.generateDeliverbillDetailFromWaybill(deliverbill, waybill, arrangeGoodsQty, actualFreight, arriveSheet);
							
							if(deliverbillDetail!=null){
								this.deliverbillDetailDao.add(deliverbillDetail);
								
								// 更新派送单的统计信息
								this.updateDeliverbillStat(deliverbill, deliverbillDetail, DeliverbillService.ADD_OPERATION);

								this.deliverbillDao.update(deliverbill);

								// 更新运单排单件数
								String actualFreightId = actualFreight.getId();
								int newArrangeGoodsQty = actualFreight.getArrangeGoodsQty() + deliverbillDetail.getArrangeGoodsQty();
								
								// 需要保证已排单件数大于0，且小于开单件数
								//newArrangeGoodsQty = newArrangeGoodsQty > actualFreight.getGoodsQty() ? actualFreight.getGoodsQty() : newArrangeGoodsQty;
								newArrangeGoodsQty = newArrangeGoodsQty > 0 ? newArrangeGoodsQty : 0;

								actualFreight = new ActualFreightEntity();
								actualFreight.setId(actualFreightId);
								actualFreight.setArrangeGoodsQty(newArrangeGoodsQty);

								this.actualFreightDao.updateByPrimaryKeySelective(actualFreight);
							}
							
							//解锁
							businessLockService.unlock(deliverbillDetailMutexElement);
						}
					}
				}
			}
		}

		// 生成到达联
		List<ArrangeArriveSheetDto> exceptionalArrangeArriveSheetDtoList = this.generateArriveSheet(deliverbill, deliverbillDto);
		for (ArrangeArriveSheetDto exceptionalArrangeArriveSheet : exceptionalArrangeArriveSheetDtoList)
		{
			// 根据排单件数与生成到达联件数只差更新运单已排单件数
			if( exceptionalArrangeArriveSheet.getArriveSheetNo()==null ){
				// 调整运单的可排单数量
				this.updateActualFreightArrangeGoodsQty(exceptionalArrangeArriveSheet.getWaybillNo(), exceptionalArrangeArriveSheet.getArrangeGoodsQty() * DeliverbillService.MINUS_ONE);
			}else{
				// 调整运单的可排单数量
				this.updateActualFreightArrangeGoodsQty(exceptionalArrangeArriveSheet.getWaybillNo(),
						(exceptionalArrangeArriveSheet.getArrangeGoodsQty() - exceptionalArrangeArriveSheet.getNewArriveSheetGoodsQty()) * DeliverbillService.MINUS_ONE);
			}
		}
			
		deliverbill.setDriverCode(deliverbillDto.getDriverCode());
		deliverbill.setDriverName(deliverbillDto.getDriverName());
		// 保存确认人和确认时间信息
		this.updateDeliverbillOperateInfoForComfirm(deliverbill);
		//是否有存在绑定中的到达联
		boolean flag = deliverbillDao.isExistValidDeliverbill(deliverbillId);
		if(!flag)
		{
			// 更新派送单状态为“已取消”
			deliverbill.setStatus(DeliverbillConstants.STATUS_CANCELED);	
		}else{
			// 更新派送单状态为“已确认”
			deliverbill.setStatus(DeliverbillConstants.STATUS_CONFIRMED);
		}
		deliverbill = this.deliverbillDao.update(deliverbill);
		//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
		if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
			DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
			deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
			deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
			deliverBillVary.setOperatorName(deliverbill.getOperator());//操作人
			deliverBillVary.setOperatorCode(deliverbill.getOperatorCode());//操作人编码
			deliverBillVary.setOperateOrgName(deliverbill.getOperateOrgName());//操作部门
			deliverBillVary.setOperateOrgCode(deliverbill.getOperateOrgCode());//操作部门编码
			//添加纪录
			deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
		}

		if (deliverbill != null)
		{ 
			//零担确认派送单，推送轨迹--add by 231438 
			//根据派送单id，查派送单详情表，查派送单的运单号
			List<DeliverbillDetailEntity> entityList = deliverbillDetailDao.queryByDeliverbillId(deliverbillId);
			//调用接口，赋值，推动轨迹
			sendWaybillTrackService.packagingMethodForConfirm(deliverbill,entityList);		
			// 调用中转接口，更新车辆任务状态为“已确认”
			int confirmLoadTaskResult = this.deliverLoadTaskService.comfirmLoadTask(deliverbillNo);

			if (confirmLoadTaskResult == 1)
			{ // 更新车载信息
				VehicleActualSituationEntity vehicleActualSituation = new VehicleActualSituationEntity();
				vehicleActualSituation.setVehicleNo(deliverbill.getVehicleNo());
				vehicleActualSituation.setNoneDeliverGoodsQty(deliverbill.getGoodsQtyTotal());
				vehicleActualSituation.setRemainingVolume(deliverbill.getVolumeTotal());
				vehicleActualSituation.setRemainingWeight(deliverbill.getWeightTotal());
				
				if (!this.vehicleActualSituationManageService.addWVByVehicleNo(vehicleActualSituation))
				{
					throw new DeliverbillException(DeliverbillException.UPDATECAR_ERROR);
				}else 
				{
					try {
						AutoDepartDTO autoDto = new AutoDepartDTO();
						autoDto.setVehicleNo(deliverbill.getVehicleNo());
						autoDto.setApplyUserName(FossUserContextHelper.getUserName());
						autoDto.setApplyUserCode(FossUserContextHelper.getUserCode());
						autoDto.setApplyOrgCode(FossUserContextHelper.getOrgCode());
						autoDto.setAutoDepartType(DepartureConstant.AUTO_DEPART_TYPE_DELIVERBILL);
						autoDto.setDepartItems(DepartureConstant.DEPART_ITEM_TYPE_PKP);
						autoDto.setDriverCode(deliverbillDto.getDriverCode());
						autoDto.setDriverName(deliverbillDto.getDriverName());
						// 当前部门编码
						String orgCode = FossUserContextHelper.getOrgCode();
						//获得当前营业部信息
						SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
						if (saleDepartment != null)
						{
							//如果是营业部 并且 是驻地部门 才调用
							 if(saleDepartment.checkStation())
							 {
									//调用中转接口 获得车辆放行ID
									String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
									deliverbill.settOptTruckDepartId(tOptTruckDepartId);
							 }
						}else //非营业部 也进行调用
						{	
							//调用中转接口 获得车辆放行ID
							String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
							deliverbill.settOptTruckDepartId(tOptTruckDepartId);
						}
						//确认派送单发送短信306548
						//deliverbillNewService.isSendSMSToConsumers(deliverbillId,taskNo);
						//更新派送单状态
						deliverbillDao.update(deliverbill);
					} catch (TfrBusinessException se) {
						//异常处理
						LOGGER.error("error", se);
						throw new DeliverbillException(se.getErrorCode(),se);
					}
				}
			} else
			{
				throw new DeliverbillException(DeliverbillException.UPDATECARTASK_ERROR);
			}
			//判断车辆是否是公司的车辆
			String queryVehicleType = leasedVehicleService.queryVehicleType(deliverbill.getVehicleNo());
			if(queryVehicleType != null && !ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)){
				//是公司的车辆就走下面的逻辑
				//推送消息至Gps
				com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = gpsDeliverService.syscConfirmDeliverTaskToGps(deliverbill);
				//为了代码的简洁，我只能使用0.不然又带出上面一大串
				if(ResultDto.FAIL.equals(resultDto.getCode())){
					LOGGER.info("Foss推送送货任务至Gps失败，错误详情："+resultDto.getMsg());
				}
			}
			LOGGER.info("派送单号："+deliverbill.getDeliverbillNo()+"Foss推送送货任务功能完成(不管是否成功)");
			return confirmLoadTaskResult;
		} else
		{
			throw new DeliverbillException(DeliverbillException.UPDATEDELIVERSTATE_ERROR);
		}
	}
	
	/**
	 * 
	 * 根据运单信息生成派送单明细信息
	 * 
	 * @param deliverbillId 派送单ID
	 * @param waybill 运单
	 * @param arrangeGoodsQty 排单件数
	 * @param actualFreight 实际承运信息
	 * @param arriveSheet 到达联
	 * @author ibm-wangxiexu
	 * @date 2013-6-29 上午3:19:34
	 */
	private DeliverbillDetailEntity generateDeliverbillDetailFromWaybill(DeliverbillEntity deliverbill, WaybillEntity waybill, int arrangeGoodsQty, 
			ActualFreightEntity actualFreight, ArriveSheetEntity arriveSheet){
		if(waybill!=null && arrangeGoodsQty>0 ){
			DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();
			
			deliverbillDetail.settSrvDeliverbillId(deliverbill.getId());
			deliverbillDetail.setSerialNo(this.deliverbillDetailDao.queryMaxSerialNoByDeliverbillId(deliverbill.getId()) + 1);
			deliverbillDetail.setWaybillNo(waybill.getWaybillNo());
			deliverbillDetail.setPreArrangeGoodsQty(0);
			deliverbillDetail.setArrangeGoodsQty(arrangeGoodsQty);
			
			deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybill.getGoodsWeightTotal().multiply(new BigDecimal(arrangeGoodsQty)),
					new BigDecimal(waybill.getGoodsQtyTotal()), DeliverbillConstants.WEIGHT_PRECISION));
			deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybill.getGoodsVolumeTotal().multiply(new BigDecimal(arrangeGoodsQty)),
					new BigDecimal(waybill.getGoodsQtyTotal()), DeliverbillConstants.VOLUME_PRECISION));
			
			deliverbillDetail.setDimension(waybill.getGoodsSize());
			deliverbillDetail.setGoodsName(waybill.getGoodsName());
			deliverbillDetail.setWaybillGoodsQty(waybill.getGoodsQtyTotal());
			deliverbillDetail.setTransportType(waybill.getProductCode());
			
			if(actualFreight!=null){
				deliverbillDetail.setArriveTime(actualFreight.getArriveTime());
				deliverbillDetail.setIsAlreadyContact(SUCCESS.equals(actualFreight.getNotificationResult()) ? FossConstants.ACTIVE : FossConstants.INACTIVE);
				deliverbillDetail.setEstimatedDeliverTime(actualFreight.getDeliverDate());
				deliverbillDetail.setPaymentType(actualFreight.getPaymentType());
			}
			
			deliverbillDetail.setConsignee(waybill.getReceiveCustomerContact());
			deliverbillDetail.setConsigneeContact(waybill.getReceiveCustomerMobilephone());
			if(deliverbill!=null &&FossConstants.YES.equals(deliverbill.getCreateType())){
				DeliverHandoverbillEntity billEntity= new DeliverHandoverbillEntity();
                billEntity.setWaybillNo(waybill.getWaybillNo());
                billEntity.setActive(FossConstants.ACTIVE);
                billEntity=deliverHandoverbillDao.queryByWaybillNo(billEntity);
                if(billEntity!=null){	
                	deliverbillDetail.setDeliverDate(billEntity.getPreDeliverDate());
                	String add=handleQueryOutfieldService.getCompleteAddress(billEntity.getReceiveCustomerProvCode(),billEntity.getReceiveCustomerCityCode(),billEntity.getReceiveCustomerDistCode(),billEntity.getReceiveCustomerAddress());
    				deliverbillDetail.setConsigneeAddress(StringUtils.isNotEmpty(billEntity.getReceiveCustomerAddressNote()) ? add+"-"+billEntity.getReceiveCustomerAddressNote() : add);
                }else{
                	String add=handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(),waybill.getReceiveCustomerCityCode(),waybill.getReceiveCustomerDistCode(),waybill.getReceiveCustomerAddress());
    				deliverbillDetail.setConsigneeAddress(StringUtils.isNotEmpty(actualFreight.getReceiveCustomerAddressNote()) ? add+"-"+actualFreight.getReceiveCustomerAddressNote() : add);
                }
			}else{//封装5级地址
				String add=handleQueryOutfieldService.getCompleteAddress(waybill.getReceiveCustomerProvCode(),waybill.getReceiveCustomerCityCode(),waybill.getReceiveCustomerDistCode(),waybill.getReceiveCustomerAddress());
				deliverbillDetail.setConsigneeAddress(StringUtils.isNotEmpty(actualFreight.getReceiveCustomerAddressNote()) ? add+"-"+actualFreight.getReceiveCustomerAddressNote() : add);
				
			}
			deliverbillDetail.setNotes(waybill.getInnerNotes());
			deliverbillDetail.setDeliverType(waybill.getReceiveMethod());
			deliverbillDetail.setPayAmount(waybill.getToPayAmount());
			deliverbillDetail.setFastWaybillFlag(WaybillConstants.TRUCK_FLIGHT.equals(waybill.getProductCode()) ? Short.parseShort(DeliverbillService.ONE + "") : Short
					.parseShort(DeliverbillService.ZERO + ""));
			deliverbillDetail.setCurrencyCode(waybill.getCurrencyCode());
			deliverbillDetail.setGoodsPackage(waybill.getGoodsPackage());
			deliverbillDetail.setReturnBillType(waybill.getReturnBillType());

			if(arriveSheet!=null){
				deliverbillDetail.setDeliverRequire(arriveSheet.getDeliverRequire());
				deliverbillDetail.setIsNeedInvoice(arriveSheet.getIsNeedInvoice());
			}
			
			return deliverbillDetail;
		}else{
			return null;
		}
	}

	/**
	 * 为派送单生成到达联.
	 *
	 * @param deliverbill 
	 * 派送单编号
	 * @return 
	 * 未正常生成的到达联清单
	 * @author ibm-
	 * wangxiexu
	 * @param deliverbillDto 
	 * @date 2012-12-8 
	 * 下午7:56:31
	 */
	private List<ArrangeArriveSheetDto> generateArriveSheet(DeliverbillEntity deliverbill, DeliverbillDto deliverbillDto)
	{
		// 获取当前部门
		String currOrgCode = FossUserContextHelper.getOrgCode();
		// 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
		String areaCode = null;
		if (CollectionUtils.isNotEmpty(list)) {
			   //传入库区code
			   areaCode = list.get(1);
		}
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbill.getId());
		map.put("areaCode", areaCode);
		//查询司机电话
		queryDriverTelephone(deliverbill, deliverbillDto);
		List<ArrangeArriveSheetDto> exceptionalArriveSheetDtoList = new ArrayList<ArrangeArriveSheetDto>();	
		List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillDetailDao.queryByDeliverbillId(map);
		List<WeixinSendDto> dtoList = new ArrayList<WeixinSendDto>();
		WeixinSendDto dto = null;
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList)
		{	
			dto = new WeixinSendDto();
			ArrangeArriveSheetDto arrangeArriveSheetDto = this.arriveSheetManngerService.checkGenerateArriveSheet(deliverbillDetail.getWaybillNo(), deliverbillDetail.getArrangeGoodsQty());

			if ( !IntegerUtils.equals(deliverbillDetail.getArrangeGoodsQty(), arrangeArriveSheetDto.getNewArriveSheetGoodsQty()))
			{
				// 需要修改排单件数的到达联添加进返回列表
				exceptionalArriveSheetDtoList.add(arrangeArriveSheetDto);
			}

			// 更新到达联编号
			if(arrangeArriveSheetDto.getArriveSheetNo()!=null){
				DeliverbillDetailEntity deliverbillDetailToUpdate = new DeliverbillDetailEntity();
				deliverbillDetailToUpdate.setId(deliverbillDetail.getId());
				deliverbillDetailToUpdate.setArrivesheetNo(arrangeArriveSheetDto.getArriveSheetNo());
				this.deliverbillDetailDao.update(deliverbillDetailToUpdate);
			}
			//MANA-771   张兴旺修改    2014年3月10日 10:20:39
			actualFreightEntity = this.actualFreightDao.queryByWaybillNo(deliverbillDetail.getWaybillNo());
			dto.setWaybillNo(deliverbillDetail.getWaybillNo());
            //当前派送单数
           dto.setCurrentPieces(arrangeArriveSheetDto.getNewArriveSheetGoodsQty());
            //已处理件数
			dto.setProcessedPieces(actualFreightEntity.getArrangeGoodsQty());
			//运单总件数
			dto.setGoodsTotal(actualFreightEntity.getGoodsQty());
			//派送人电话，一般为司机
			dto.setDeliverManMobile(deliverbillDto.getDriverTel());
			//派送人人姓名
			dto.setDeliverManName(deliverbillDto.getDriverName());
			//到付金额
			dto.setArrivePayAmount(deliverbillDetail.getPayAmount());
			//派送组织，为创建派送单的组织
			dto.setDeliverOrgCode(FossUserContext.getCurrentDeptCode());
			//创建组织
			dto.setDeliverOrgName(deliverbillDto.getCreateOrgName());
			//确定时间
			dto.setCreateTime(deliverbillDto.getSubmitTime());
			dtoList.add(dto);
		}
		//微信消息开始推送
		LOGGER.info("创建派送单微信消息开始推送");
		ResultDto resultDto = new ResultDto();
		if(CollectionUtils.isNotEmpty(dtoList)){
			LOGGER.info(ReflectionToStringBuilder.toString(dtoList));
			for(int i=0;i<dtoList.size();i++){
				LOGGER.info("单号"+dtoList.get(i).getWaybillNo()+"开始推送微信消息");
				resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dtoList.get(i), WeixinConstants.WEIXIN_DELIVER_TYPE);
				if(ResultDto.FAIL.equals(resultDto.getCode())){
					LOGGER.info("单号"+dtoList.get(i).getWaybillNo()+"错误详情："+resultDto.getMsg());
				}
			}
		}
		LOGGER.info("微信消息推送完毕");
		return exceptionalArriveSheetDtoList;
	}

	private void queryDriverTelephone(DeliverbillEntity deliverbill, DeliverbillDto deliverbillDto) {
		//若司机工号不为空
		if (StringUtil.isNotEmpty(deliverbillDto.getDriverCode())){
		    // 内部司机根据工号查询相关信息
		DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverbillDto.getDriverCode());
		//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
		if (driverAssociationDto != null){
		  // 司机电话
			deliverbillDto.setDriverTel(driverAssociationDto.getDriverPhone());
		} else{
		  // 外请司机根据车牌号进行查询
		  List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
		  
		  if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
		    	//司机姓名
		  deliverbillDto.setDriverName(driverAssociationDtos.get(0).getDriverName());
			// 司机电话
			  deliverbillDto.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
		  }
		}
		//如果车牌号不为空
		  } else if (StringUtil.isNotEmpty(deliverbill.getVehicleNo())){
		    // 外请司机根据车牌号进行查询
		List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
		
		if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
			//司机姓名
			deliverbillDto.setDriverName(driverAssociationDtos.get(0).getDriverName());
			// 司机电话
		    deliverbillDto.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
		    }
		}
	}

	/**
	 * 取消预派送单.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @param deliverbillNo 
	 * 派送单编号
	 * @return 若成功，
	 * 返回大于0；
	 * 否则返回0
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-3
	 *  上午11:52:10
	 */
	@Override
	@Transactional
	public int cancelDeliverbill(String deliverbillId, String deliverbillNo)
	{
		//PDA-1588  当派送单非已保存 已装车  不能取消状态
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbillId);	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_ASSIGNED.equals(deliverbillEntity.getStatus())
				|| DeliverbillConstants.STATUS_LOADING.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		// SUC-459-确认_取消预派送单 SR4 已装车和已确认状态下的预派送单取消后生成卸车任务
		// 现已更新为退回装车任务，并将装车货物重新入库。
		int i = this.deliverLoadTaskService.takeBackDeliverBill(deliverbillNo);
		if (i != ZERO) {
			// 更新派送单状态为“已取消”
			DeliverbillEntity deliverbill = new DeliverbillEntity();
			deliverbill.setId(deliverbillId);
			deliverbill.setStatus(DeliverbillConstants.STATUS_CANCELED);
			deliverbill = this.deliverbillDao.update(deliverbill);
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
				// 获取当前用户
				UserEntity currentUser = FossUserContext.getCurrentUser();
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
				deliverBillVary.setOperatorName(currentUser.getEmployee().getEmpName());//操作人
				deliverBillVary.setOperatorCode(currentUser.getEmployee().getEmpCode());//操作人编码
				deliverBillVary.setOperateOrgName(FossUserContext.getCurrentDeptName());//操作部门
				deliverBillVary.setOperateOrgCode(FossUserContext.getCurrentDeptCode());//操作部门编码
				//添加纪录
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}

			if (deliverbill != null) {
				// SUC-459-确认_取消预派送单 SR4 已装车和已确认状态下的预派送单取消后生成卸车任务，现已更新为退回装车任务。
				// 更新已排单运单的可排单数量
				
				Map<Object,Object> map = new HashMap<Object,Object>();
				map.put("deliverbillId", deliverbillId);
				
				List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillDetailDao.queryByDeliverbillIdForPrint(map,0, 99999);
				if (deliverbillDetailList !=null) {
					for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
						// 调整运单的可排单数量
						this.updateActualFreightArrangeGoodsQty(deliverbillDetail.getWaybillNo(), deliverbillDetail.getArrangeGoodsQty() * DeliverbillService.MINUS_ONE);
					}
				}else{
					throw new DeliverbillException(DeliverbillException.CANCELDELIVERBILL_ERROR);
				}
				
			} else {
				throw new DeliverbillException(DeliverbillException.CANCELDELIVERBILL_ERROR);
			}
		}
		return i;
	}
	
	/**
	 * 更新运单已排单件数.
	 *
	 * @param waybillNo 
	 * 运单号
	 * @param arrangeGoodsQtyMargin 
	 * 排单件数差
	 * @return 
	 * 更新条数
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-3-13 
	 * 下午2:41:19
	 */
	private int updateActualFreightArrangeGoodsQty(String waybillNo, int arrangeGoodsQtyMargin){
		ActualFreightEntity actualFreightEntity = this.actualFreightDao.queryByWaybillNo(waybillNo);

		if (actualFreightEntity != null) {
			String id = actualFreightEntity.getId();
			int newArrangeGoodsQty = actualFreightEntity.getArrangeGoodsQty() + arrangeGoodsQtyMargin;
			
			// 需要保证已排单件数大于0，且小于开单件数
			newArrangeGoodsQty = newArrangeGoodsQty > actualFreightEntity.getGoodsQty() ? actualFreightEntity.getGoodsQty() : newArrangeGoodsQty;
			newArrangeGoodsQty = newArrangeGoodsQty > 0 ? newArrangeGoodsQty : 0;

			actualFreightEntity = new ActualFreightEntity();
			actualFreightEntity.setId(id);
			actualFreightEntity.setArrangeGoodsQty(newArrangeGoodsQty);

			return this.actualFreightDao.updateByPrimaryKeySelective(actualFreightEntity);
		} else {
			throw new DeliverbillException(DeliverbillException.ACTUALFREIGHT_NOT_FOUND_ERROR);
		}
		
	}

	/**
	 * 根据派送装车明细ID查询扫描明细列表.
	 *
	 * @param loadDetailId 
	 * 装车明细ID
	 * @return 
	 * 扫描明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-4 
	 * 下午9:20:43
	 */
	@Override
	public List<ScanDetailDto> queryScanDetailListByLoadDetailId(String loadDetailId)
	{
		List<ScanDetailDto> scanDetailList = new ArrayList<ScanDetailDto>();

		List<LoadSerialNoEntity> loadSerialNoList = this.loadTaskQueryService.queryloadSerialNoByLoadWaybillDetailId(loadDetailId);

		if (CollectionUtils.isNotEmpty(loadSerialNoList))
		{
			for (LoadSerialNoEntity loadSerialNo : loadSerialNoList)
			{
				ScanDetailDto scanDetailDto = new ScanDetailDto();
				try
				{
					BeanUtils.copyProperties(scanDetailDto, loadSerialNo);
					scanDetailList.add(scanDetailDto);
				} catch (IllegalAccessException e)
				{
					LOGGER.error("error", e);
				} catch (InvocationTargetException e)
				{
					LOGGER.error("error", e);
				}
			}
		}

		return scanDetailList;
	}

	/**
	 * 根据差异报告ID和运单号查询少货明细.
	 *
	 * @param loadGaprepId 
	 * 差异报告ID
	 * @param waybillNo 
	 * 运单号
	 * @return 
	 * 少货明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-5 
	 * 下午7:47:49
	 */
	@Override
	public List<LoadGaprepDetailDto> queryLoadGaprepDetailListByRepIdAndWaybillNo(String loadGaprepId, String waybillNo)
	{
		List<LoadGaprepDetailDto> loadGaprepDetailList = new ArrayList<LoadGaprepDetailDto>();

		// 构造查询条件
		DeliverLoadGapReportWayBillEntity condition = new DeliverLoadGapReportWayBillEntity();
		condition.setReportId(loadGaprepId);
		condition.setWayBillNo(waybillNo);

		List<DeliverLoadGapReportSerialEntity> loadGaprepDetailEntityList = this.deliverLoadGapReportService.queryDeliverLoadGapReportSerials(condition);

		if (CollectionUtils.isNotEmpty(loadGaprepDetailEntityList))
		{
			for (DeliverLoadGapReportSerialEntity loadGaprepDetailEntity : loadGaprepDetailEntityList)
			{
				LoadGaprepDetailDto loadGaprepDetailDto = new LoadGaprepDetailDto();
				try
				{
					BeanUtils.copyProperties(loadGaprepDetailDto, loadGaprepDetailEntity);
					loadGaprepDetailList.add(loadGaprepDetailDto);
				} catch (IllegalAccessException e)
				{
					LOGGER.error("error", e);
				} catch (InvocationTargetException e)
				{
					LOGGER.error("error", e);
				}
			}
		}

		return loadGaprepDetailList;
	}

	/**
	 * 根据任务ID查找装车人.
	 *
	 * @param taskId 
	 * 装车任务ID
	 * @return 
	 * 装车人列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-5 
	 * 下午11:53:24
	 */
	@Override
	public List<LoaderDto> queryLoaderListByTaskId(String taskId)
	{
		List<LoaderDto> loaderList = new ArrayList<LoaderDto>();

		List<LoaderParticipationEntity> loaderParticipationEntityList = this.loadTaskQueryService.queryLoaderByTaskId(taskId);

		if (CollectionUtils.isNotEmpty(loaderParticipationEntityList))
		{
			for (LoaderParticipationEntity loaderParticipationEntity : loaderParticipationEntityList)
			{
				LoaderDto loaderDto = new LoaderDto();
				try
				{
					BeanUtils.copyProperties(loaderDto, loaderParticipationEntity);
					loaderList.add(loaderDto);
				} catch (IllegalAccessException e)
				{
					LOGGER.error("error", e);
				} catch (InvocationTargetException e)
				{
					LOGGER.error("error", e);
				}
			}
		}

		return loaderList;
	}

	/**
	 * 根据装车任务ID和派送单号查询历史装车差异报告.
	 *
	 * @param taskId 
	 * 装车任务ID
	 * @param deliverbillNo 
	 * 派送单编号
	 * @return 
	 * 历史装车差异报告
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-6 
	 * 下午2:28:31
	 */
	@Override
	public List<LoadGaprepDto> queryLoadGaprepListByTaskIdAndDeliverbillNo(String taskId, String deliverbillNo)
	{
		List<LoadGaprepDto> loadGaprepList = new ArrayList<LoadGaprepDto>();

		List<DeliverLoadGapReportEntity> deliverLoadGapReportEntityList = this.deliverLoadTaskService.queryDeliverLoadGapRep(taskId, deliverbillNo);

		if (CollectionUtils.isNotEmpty(deliverLoadGapReportEntityList))
		{
			for (DeliverLoadGapReportEntity deliverLoadGapReportEntity : deliverLoadGapReportEntityList)
			{
				LoadGaprepDto loadGaprepDto = new LoadGaprepDto();
				try
				{
					BeanUtils.copyProperties(loadGaprepDto, deliverLoadGapReportEntity);
					loadGaprepList.add(loadGaprepDto);
				} catch (IllegalAccessException e)
				{
					LOGGER.error("error", e);
				} catch (InvocationTargetException e)
				{
					LOGGER.error("error", e);
				}
			}
		}

		return loadGaprepList;
	}

	/**
	 * 根据派送单ID查找已生成到达联的派送明细列表.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @return the 
	 * list
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-10-24
	 *  上午10:05:28
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillArrivesheetList(String deliverbillId)
	{
		DeliverbillDetailDto deliverbillDetailDto = new DeliverbillDetailDto();
		deliverbillDetailDto.setDeliverbillId(deliverbillId);
		deliverbillDetailDto.setArrivesheetNo(DeliverbillConstants.NULL_ARRIVESHEET_NO);
		List<DeliverbillDetailEntity> detailList = deliverbillDetailDao.queryArrivesheetListByDeliverbillId(deliverbillDetailDto);
		for (int i = 0; i < detailList.size(); i++) {
			if(StringUtils.isEmpty(detailList.get(i).getArrivesheetNo()))
			{
				detailList.remove(i);
				i--;
			}
		} 
		return detailList;
	}
	
	/**
	 * 为中转提供的接口，检验运单是否在指定部门安排过派送.
	 *
	 * @param waybillNo 运单号
	 * @param deptCode 派送部门
	 * @return 运单是否在指定部门安排过派送
	 * @author ibm-wangxiexu
	 * @date 2012-12-14 下午4:20:53
	 */
	@Override
	public boolean queryWaybillArrangedFlag(String waybillNo, String deptCode)
	{
		DeliverbillDetailDto deliverbillDetailDto = new DeliverbillDetailDto();
		deliverbillDetailDto.setStatus(DeliverbillConstants.STATUS_CANCELED);
		deliverbillDetailDto.setWaybillNo(waybillNo);
		deliverbillDetailDto.setEndStockOrgCode(deptCode);

		Long cnt = this.deliverbillDetailDao.queryWaybillArrangedFlag(deliverbillDetailDto);

		return cnt != null && cnt > 0L;
	}

	/**
	 * 根据车牌号和司机工号查询PDA已下拉但未生成交接单的派送单列表.
	 *
	 * @return the 
	 * list
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-22 
	 * 下午7:45:58
	 */
	@Override
	public List<DeliverbillDto> queryPdaDownloadedDeliverbillList(DeliverbillDto deliverbillDto)
	{
		return this.deliverbillDao.queryStayHandoverByPda(deliverbillDto);
	}

	/**
	 * 根据派送单编号查询派送信息（要求派送单必须确认签收）.
	 *
	 * @param deliverbillNo 
	 * 派送单编号
	 * @return 
	 * 派送信息
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-26
	 *  下午7:15:43
	 */
	@Override
	public DeliverbillDto queryDeliveryInfoByDeliverbillNo(String deliverbillNo)
	{
		// 返回值默认为空
		DeliverbillDto deliverbillDto = null;

		// 构造查询条件(包括派送单号和签收确认状态)，查询派送单基础信息
		DeliverbillDto condition = new DeliverbillDto();
		condition.setDeliverbillNo(deliverbillNo);
		condition.setStatus(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);

		DeliverbillEntity deliverbillEntity = this.deliverbillDao.queryByDeliverbillDto(condition);

		if (deliverbillEntity != null)
		{
			// 若派送单存在，且已签收确认，则填充返回值，并查询派送成功票数和派送拉回票数
			deliverbillDto = new DeliverbillDto();
			deliverbillDto.setCreateOrgCode(deliverbillEntity.getCreateOrgCode());
			deliverbillDto.setCreateOrgName(deliverbillEntity.getCreateOrgName());
			deliverbillDto.setVehicleNo(deliverbillEntity.getVehicleNo());
			deliverbillDto.setDriverCode(deliverbillEntity.getDriverCode());
			deliverbillDto.setDriverName(deliverbillEntity.getDriverName());
			deliverbillDto.setOperateTime(deliverbillEntity.getOperateTime());
			deliverbillDto.setVolumeTotal(deliverbillEntity.getVolumeTotal());
			deliverbillDto.setWeightTotal(deliverbillEntity.getWeightTotal());

			// 查询派送成功票数和派送拉回票数
			// 构造查询条件
			ArriveSheetDto arriveSheetDto = new ArriveSheetDto();
			arriveSheetDto.setDeliverbillNo(deliverbillNo);
			arriveSheetDto.setActive(FossConstants.ACTIVE);
			arriveSheetDto.setDestroyed(FossConstants.INACTIVE);

			arriveSheetDto.setStatus(ArriveSheetConstants.STATUS_SIGN);
			// 设置成功票数
			deliverbillDto.setDeliverWaybillQty(this.deliverbillDao.querySignCountByArrivesheetDto(arriveSheetDto));

			arriveSheetDto.setStatus(ArriveSheetConstants.STATUS_REFUSE);
			// 设置拉回票数
			deliverbillDto.setPullbackWaybillQty(this.deliverbillDao.querySignCountByArrivesheetDto(arriveSheetDto));
		}

		return deliverbillDto;
	}

	/**
	 * 更新派送单的装车状态.
	 *
	 * @param deliverbillNo 
	 * 派送单号
	 * @param status 
	 * 派送单状态
	 * @return 
	 * 更新派送单的条目数
	 * @author ibm-
	 * wangxiexu
	 * @date 2012-12-27
	 *  下午4:42:20
	 */
	@Override
	public int updateDeliverbillLoadStatus(String deliverbillNo, String status)
	{
		if (!DeliverbillConstants.STATUS_ASSIGNED.equals(status) && !DeliverbillConstants.STATUS_LOADING.equals(status) && !DeliverbillConstants.STATUS_LOADED.equals(status))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERSTATE_INVALID);
		}

		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setDeliverbillNo(deliverbillNo);
		deliverbill.setStatus(status);
		return this.deliverbillDao.updateStatusByDeliverbillNo(deliverbill);
	}

	/**
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机.
	 *
	 * @param driverDto 
	 * 查询条件
	 * @return 
	 * 符合条件的公司司机列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-4 
	 * 下午9:55:30
	 */
	@Override
	public List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto)
	{
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		String isSale = org.getSalesDepartment();
		List<String> orgIdList = new ArrayList<String>();
		if (FossConstants.YES.equals(isSale))
		{
			//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
			SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
			entity.setSalesdeptCode(FossUserContextHelper.getOrgCode());
			List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_NUM);
			if (!CollectionUtils.isEmpty(salesMotorcadeList))
			{
				for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
				{
					orgIdList.add(salesMotorcadeEntity.getMotorcadeCode());
				}
				driverDto.setOrgIdList(orgIdList);
			}else
			{	
				orgIdList.add(FossUserContextHelper.getOrgCode());
				driverDto.setOrgIdList(orgIdList);
			}
		} else
		{
			// 获取当前组织对应车队
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContextHelper.getOrgCode());
			if(orgAdministrativeInfoEntity != null)
			{
				orgIdList.add(orgAdministrativeInfoEntity.getCode());
				driverDto.setOrgIdList(orgIdList);
			}else
			{
				orgIdList.add(FossUserContextHelper.getOrgCode());
				driverDto.setOrgIdList(orgIdList);
			}
		}
		List<String> subOrgCodeList = new ArrayList<String>();
		for (String string : orgIdList) {
			//根据IBM-罗越决议 找顶级车队下所有子组织code
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(string);
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (OrgAdministrativeInfoEntity orgEntity : orgList) {
					subOrgCodeList.add(orgEntity.getCode());
				}
			}
		}
		//添加本部门司机-应用于单点营业部自有车辆
		subOrgCodeList.add(FossUserContextHelper.getOrgCode());
		//添加顶级车队code
		subOrgCodeList.add(driverDto.getOrgId());
		driverDto.setSubOrgCodeList(subOrgCodeList);
		
		driverDto.setActive(FossConstants.ACTIVE);
		return this.deliverbillDao.queryDriverListByDriverDto(driverDto);
	}

	/**
	 * 分配派送任务.
	 *
	 * @param deliverbillId 
	 * 派送单id
	 * @param driver 
	 * 分配的司机
	 * @return 分配成功标志。
	 * 若成功，则返回1；否则不成功
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-6 
	 * 上午10:46:16
	 */
	@Override
	@Transactional
	public int assignDriver(String deliverbillId, DriverDto driver)
	{
		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setId(deliverbillId);
		deliverbill.setDriverCode(driver.getEmpCode());
		deliverbill.setDriverName(driver.getEmpName());

		/*
		 * SUC-447 -创建预派送 SR-9 1. 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
		 * 2. 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 当排班和PDA绑定同时存在时，以PDA绑定为准
		 */
		deliverbill = this.deliverbillDao.update(deliverbill);

		if (deliverbill == null)
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_NOTFOUND);
		}
		DeliverbillEntity deliverbillForGps = deliverbillDao.queryById(deliverbillId);
		if(deliverbillForGps != null && DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillForGps.getStatus())){
			//修改派送单
			LOGGER.info("修改派送单开始");
			//判断车辆是否是公司的车辆
			String queryVehicleType = leasedVehicleService.queryVehicleType(deliverbill.getVehicleNo());
			if(queryVehicleType != null && !ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)){
				//是公司的车辆就走下面的逻辑
				com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
						gpsDeliverService.syscModifyDeliverTaskToGps(deliverbillForGps, null);
				if(ResultDto.FAIL.equals(resultDto.getCode())){
					LOGGER.info("修改派送单失败，错误详情："+resultDto.getMsg());
				}
			}
			LOGGER.info("修改派送单结束");
		}
		return 1;
	}

	/**
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询.
	 *
	 * @param waybillNo 
	 * 运单号
	 * @return 
	 * 运单派送信息列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-8 
	 * 下午8:21:35
	 */
	@Override
	public List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNo(String waybillNo)
	{
		// 设置查询条件
		WaybillDeliveryDto waybillDeliveryDto = new WaybillDeliveryDto();

		waybillDeliveryDto.setWaybillNo(waybillNo);
		waybillDeliveryDto.setDriverActive(FossConstants.ACTIVE);

		// 派送单的状态查询条件设置
		List<String> deliverbillStatusList = new ArrayList<String>();
		deliverbillStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);
		waybillDeliveryDto.setDeliverbillStatusList(deliverbillStatusList);
		
		List<WaybillDeliveryDto> WaybillDeliveryDtoList = this.deliverbillDetailDao.queryWaybillDeliveryListByWaybillNo(waybillDeliveryDto);
		List<WaybillDeliveryDto> WaybillDeliveryInfo = new ArrayList<WaybillDeliveryDto>();
		for (WaybillDeliveryDto waybillDeliveryDto2 : WaybillDeliveryDtoList) {
			if (StringUtils.equals(waybillDeliveryDto2.getIsExpress(), FossConstants.YES) && 
					(StringUtils.isNotEmpty(waybillDeliveryDto2.getDriverCode()) || StringUtils.isNotEmpty(waybillDeliveryDto2.getVehicleNo()))) {
				// 如果是小件派送单,并且司机工号或者司机车牌号不为空
				ExpressVehicleDto expressVeh = new ExpressVehicleDto();
				// 司机工号
				expressVeh.setEmpCode(waybillDeliveryDto2.getDriverCode());
				// 车牌号
				expressVeh.setVehicleNo(waybillDeliveryDto2.getVehicleNo());
				expressVeh.setActive(FossConstants.YES);
				List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10, NumberConstants.ZERO);
				if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
					//司机姓名
		        	waybillDeliveryDto2.setDriverName(expressVehicleDtos.get(0).getEmpName());
		        	// 司机电话
		        	waybillDeliveryDto2.setDriverPhone(expressVehicleDtos.get(0).getMobilePhone());
				}
			} else {
				//若司机工号不为空
				if (StringUtil.isNotEmpty(waybillDeliveryDto2.getDriverCode()))
				{
					// 内部司机根据工号查询相关信息
					DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(waybillDeliveryDto2.getDriverCode());
					//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
					if (driverAssociationDto != null)
					{
						// 司机电话
						waybillDeliveryDto2.setDriverPhone(driverAssociationDto.getDriverPhone());
					} else
					{
						// 外请司机根据车牌号进行查询
						List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(waybillDeliveryDto2.getVehicleNo());
						
						if (CollectionUtils.isNotEmpty(driverAssociationDtos))
						{
							//司机姓名
							waybillDeliveryDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
							// 司机电话
							waybillDeliveryDto2.setDriverPhone(driverAssociationDtos.get(0).getDriverPhone());
						}
					}
					//如果车牌号不为空
				} else if (StringUtil.isNotEmpty(waybillDeliveryDto2.getVehicleNo()))
				{
					// 外请司机根据车牌号进行查询
					List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(waybillDeliveryDto2.getVehicleNo());
					
					if (CollectionUtils.isNotEmpty(driverAssociationDtos))
					{
						//司机姓名
						waybillDeliveryDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
						// 司机电话
						waybillDeliveryDto2.setDriverPhone(driverAssociationDtos.get(0).getDriverPhone());
					}
				}	
			}

			WaybillDeliveryInfo.add(waybillDeliveryDto2);
		}
		return WaybillDeliveryInfo;
	}
	
	/**
	 * 
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询
	 * 
	 * @param waybillNo
	 *               运单号
	 * @return 运单派送信息列表
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-3 下午2:21:45
	 */
	@Override
	public List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNoForBse(String waybillNo)
	{
		// 设置查询条件
		WaybillDeliveryDto waybillDeliveryDto = new WaybillDeliveryDto();

		waybillDeliveryDto.setWaybillNo(waybillNo);
		waybillDeliveryDto.setDriverActive(FossConstants.ACTIVE);

		// 派送单的状态查询条件设置
		List<String> deliverbillStatusList = new ArrayList<String>();
		deliverbillStatusList.add(DeliverbillConstants.STATUS_SAVED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_SUBMITED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_ASSIGNED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_LOADING);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_LOADED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
		deliverbillStatusList.add(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);
		waybillDeliveryDto.setDeliverbillStatusList(deliverbillStatusList);

		return this.deliverbillDetailDao.queryWaybillDeliveryListByWaybillNo(waybillDeliveryDto);
	}

	/**
	 * 
	 * 根据接送货车辆车牌号查询接送货司机(SUC-447 创建派送单 SR9 
	 * 1.若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 
	 * 2.若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 
	 * 3.当排班和PDA绑定同时存在时，以PDA绑定为准)
	 * 
	 * @param 
	 * vehicleNo
	 *  车牌号
	 * @return 
	 * 接送货司机
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-10 
	 * 下午9:52:47
	 */
	@Override
	public DriverDto queryDriverByVehicleNo(String vehicleNo)
	{
		DriverDto driver = null;

		// 查询车辆是否与司机通过PDA绑定
		PdaSignDto pdaSignDto = new PdaSignDto();
		pdaSignDto.setVehicleNo(vehicleNo);
		pdaSignDto.setStatus(PdaSignStatusConstants.BUNDLE);

		List<PdaSignDto> pdaSignDtoList = this.signInAndLogOutService.querySignedInfo(pdaSignDto);

		if (CollectionUtils.isNotEmpty(pdaSignDtoList))
		{
			pdaSignDto = pdaSignDtoList.get(0);
			driver = new DriverDto();
			driver.setEmpCode(pdaSignDto.getDriverCode());
			driver.setEmpName(pdaSignDto.getDriverName());
			driver.setPdaSigned(FossConstants.YES);
		} else
		{
			// 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
			OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();

			// 排班类型--接送货
			ownTruckConditionDto.setSchedulingType(TruckConstants.SCHEDULE_TYPE_DELIVERY);
			// 排班状态--可用
			ownTruckConditionDto.setSchedulingStatus(FossConstants.ACTIVE);
			// 司机状态--工作
			ownTruckConditionDto.setSchedulingPlanType(TruckConstants.PLAN_TYPE_WORK);
			ownTruckConditionDto.setVehicleNo(vehicleNo);
			ownTruckConditionDto.setActive(FossConstants.ACTIVE);

			List<OwnTruckDto> schedulingResult = truckScheduleDao.queryTruckSchedulingByVehicleNo(ownTruckConditionDto);

			if (CollectionUtils.isNotEmpty(schedulingResult))
			{
				OwnTruckDto ownTruckDto = schedulingResult.get(0);

				driver = new DriverDto();
				driver.setEmpCode(ownTruckDto.getDriverCode());
				driver.setEmpName(ownTruckDto.getDriverName());
				driver.setPdaSigned(FossConstants.NO);
			}else
			{
				// 外请司机根据车牌号进行查询
		          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(vehicleNo);
		          if (CollectionUtils.isNotEmpty(driverAssociationDtos))
		          {
		        	  driver = new DriverDto();
		        	  driver.setEmpCode(driverAssociationDtos.get(0).getDriverIdCard());
		        	  driver.setEmpName(driverAssociationDtos.get(0).getDriverName());
		        	  driver.setPdaSigned(FossConstants.NO);
		          }
		        }
		}
		return driver;
	}

	/**
	 * 查询未通知客户的派送单明细列表.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @return 
	 * 未通知客户的派送单明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-14 
	 * 下午1:40:15
	 */
	@Override
	public List<DeliverbillDetailDto> queryUnnotifiedDeliverbillDetailList(String deliverbillId)
	{
		// 设置查询条件
		DeliverbillDetailDto deliverbillDetailDto = new DeliverbillDetailDto();
		deliverbillDetailDto.settSrvDeliverbillId(deliverbillId);
		deliverbillDetailDto.setIsAlreadyContact(FossConstants.NO);

		return this.deliverbillDetailDao.queryUnnotifiedDeliverbillDetailList(deliverbillDetailDto);
	}

	/**
	 * 短信通知客户.
	 *
	 * @param 
	 * deliverbillDetailDtoList 待通知客户的派送单明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-16 
	 * 下午2:27:01
	 */
	@Override
	public void notifyCustomer(List<DeliverbillDetailDto> deliverbillDetailDtoList)
	{
		for (DeliverbillDetailDto deliverbillDetailDto : deliverbillDetailDtoList)
		{
			// 联系人方式存在时进行通知
			if (StringUtils.isNotBlank(deliverbillDetailDto.getConsigneeContact()))
			{
				NotificationEntity notification = new NotificationEntity();
				// 运单号
				notification.setWaybillNo(deliverbillDetailDto.getWaybillNo());
				// 通知类型
				notification.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
				// 接收人姓名
				notification.setConsignee(deliverbillDetailDto.getConsignee());
				// 手机号
				notification.setMobile(deliverbillDetailDto.getConsigneeContact());
				// 操作时间
				notification.setOperateTime(new Date());
				// 模块名称
				notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);

				UserEntity currentUser = FossUserContext.getCurrentUser();
				OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();

				if (currentUser != null)
				{
					EmployeeEntity employee = currentUser.getEmployee();
					if (employee != null)
					{
						// 操作人
						notification.setOperator(employee.getEmpName());
						// 操作人编码
						notification.setOperatorNo(employee.getEmpCode());
					}
				}

				if (currentOrg != null)
				{
					// 操作部门编码
					notification.setOperateOrgCode(currentOrg.getCode());
					// 操作部门
					notification.setOperateOrgName(currentOrg.getName());
				}

				// 设置通知内容
				NotifyCustomerDto notifyCustomerDto = new NotifyCustomerDto();
				// 提货方式
				notifyCustomerDto.setReceiveMethod(WaybillConstants.DELIVER_FREE);
				// 提货人姓名
				notifyCustomerDto.setReceiveCustomerContact(deliverbillDetailDto.getConsignee());
				// 运单号
				notifyCustomerDto.setWaybillNo(deliverbillDetailDto.getWaybillNo());
				// 到付款金额
				notifyCustomerDto.setToPayAmount(deliverbillDetailDto.getPayAmount());
				// 通知内容
				String [] noticeContents = this.notifyCustomerService.queryNoticeContent(notification, notifyCustomerDto);
				notification.setNoticeContent(noticeContents[0]);

				this.notifyCustomerService.sendMessage(notification);

			}

		}

	}

	/**
	 * 查询排单件数与到达联件数不一致的派送单明细列表.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @return 
	 * 排单件数与到达联件数不一致的派送单明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-17
	 *  下午12:03:23
	 */
	@Override
	public List<DeliverbillDetailDto> queryArrivesheetQtyInconsistentDeliverbillDetailList(String deliverbillId)
	{
		DeliverbillDetailDto deliverbillDetailDto = new DeliverbillDetailDto();
		deliverbillDetailDto.settSrvDeliverbillId(deliverbillId);

		ArriveSheetDto arriveSheetDto = new ArriveSheetDto();
		arriveSheetDto.setActive(FossConstants.ACTIVE);
		arriveSheetDto.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		arriveSheetDto.setDestroyed(FossConstants.NO);

		deliverbillDetailDto.setArriveSheetDto(arriveSheetDto);

		return this.deliverbillDetailDao.queryArrivesheetQtyInconsistentListByDeliverbillId(deliverbillDetailDto);
	}

	/**
	 * 查询排单件数与库存件数不一致的派送单明细列表.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @return 
	 * 排单件数与库存件数不一致的派送单明细列表
	 * @author ibm-
	 * wangxiexu
	 * @date 2013-1-17 
	 * 下午12:03:23
	 */
	@Override
	public List<DeliverbillDetailDto> queryInStockQtyInconsistentDeliverbillDetailList(String deliverbillId)
	{
		return this.deliverbillDetailDao.queryInStockQtyInconsistentDeliverbillDetailList(deliverbillId);
	}

	/**
	 * 取消已保存的派送单.
	 *
	 * @param deliverbillId t
	 * he deliverbill id
	 * @param deliverbillNo 
	 * the deliverbill no
	 * @return 
	 * the int
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2013-2-2 
	 * 下午3:30:18
	 */
	@Override
	public int cancelDeliverbillForSaved(String deliverbillId, String deliverbillNo)
	{			
		
		//PDA-1588  当派送单非已保存 已装车  不能取消状态
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbillId);	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_ASSIGNED.equals(deliverbillEntity.getStatus())
				|| DeliverbillConstants.STATUS_LOADING.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("deliverbillId", deliverbillId);

		// 根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillDetailDao.queryByDeliverbillIdForPrint(map, 0, 9999);

		for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
			// 调整运单的可排单数量
			this.updateActualFreightArrangeGoodsQty(deliverbillDetail.getWaybillNo(), deliverbillDetail.getArrangeGoodsQty() * DeliverbillService.MINUS_ONE);
		}
		
		// 更新派送单状态为“已取消”
		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setId(deliverbillId);
		deliverbill.setStatus(DeliverbillConstants.STATUS_CANCELED);
		//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
		if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
			deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
			deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
			deliverBillVary.setOperatorName(currentUser.getEmployee().getEmpName());//操作人
			deliverBillVary.setOperatorCode(currentUser.getEmployee().getEmpCode());//操作人编码
			deliverBillVary.setOperateOrgName(FossUserContext.getCurrentDeptName());//操作部门
			deliverBillVary.setOperateOrgCode(FossUserContext.getCurrentDeptCode());//操作部门编码
			//添加纪录
			deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
		}
		return  this.deliverbillDao.updateDeliverBill(deliverbill);	
	}
	
	/**
	 * 根据到达联编号，派送单状态查询派送单编号.
	 *
	 * @param dto 
	 * the dto
	 * @return the 
	 * string
	 * @author foss-
	 * meiying
	 * @date 2013-2-3 
	 * 上午10:43:49
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#queryDeliverNoByArriveSheetNo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public String queryDeliverNoByArriveSheetNo(DeliverbillDetailDto dto) {
		return deliverbillDetailDao.queryDeliverNoByArriveSheetNo(dto);
	}

	/**
	 * 查询派送单序列.
	 *
	 * @return 
	 * the string
	 * @author 043258-
	 * foss-zhaobin
	 * @date 2013-3-8 
	 * 上午10:57:16
	 */
	@Override
	public String querySequence() {
		return deliverbillDao.querySequence();
	}
	
	/**
	 * 
	 * 根据条件查询派送单
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-4 上午9:15:23
	 */
	@Override
	public InputStream queryDeliverbillList(DeliverbillDto deliverbillDto) 
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		//若传入编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			// 根据部门code，查询非偏线和空运的外场和货区
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if (saleDepartment != null && saleDepartment.checkStation())
			{
				deliverbillDto.setTransferCenter(saleDepartment.getTransferCenter());
			}
				deliverbillDto.setOrgCode(orgCode);
				
			//根据输入条件，查询派送单
			List<DeliverbillDto> deliverbillList =  this.deliverbillDao.queryByCondition(deliverbillDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (DeliverbillDto deliverbill : deliverbillList) {
				List<String> columnList = new ArrayList<String>();
				//派送单号
				columnList.add(deliverbill.getDeliverbillNo());
				//车辆
				columnList.add(deliverbill.getVehicleNo());
				//司机姓名
				columnList.add(deliverbill.getDriverName());
				if(StringUtil.isNotBlank(deliverbill.getDeliverType())){//派车类型
					if(DeliverbillConstants.NOMAL.equals(deliverbill.getDeliverType())){//正常
						columnList.add("正常");//派车类型
					}else if(DeliverbillConstants.SPECIAL.equals(deliverbill.getDeliverType())){// 专车
						columnList.add("专车");//派车类型
					}else {//带人
						columnList.add("带人");//派车类型
					}
					
				}else {
					columnList.add("");
				}
				//预排单状态
				columnList.add(DictUtil.rendererSubmitToDisplay(deliverbill.getStatus(), DictionaryConstants.PKP_DELIVERBILL_STATUS));
				//创建人
				columnList.add(deliverbill.getCreateUserName());
				//创建时间
				columnList.add(DateUtils.convert(deliverbill.getSubmitTime(), DateUtils.DATE_TIME_FORMAT));
				//装车完成时间
				columnList.add(DateUtils.convert(deliverbill.getLoadEndTime(), DateUtils.DATE_TIME_FORMAT));
				rowList.add(columnList);
			}
			String[] rowHeads = {"派送单号","车辆","司机姓名","派车类型","预排单状态","创建人","创建时间","装车完成时间"};
			
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(rowHeads);
		    exportResource.setRowList(rowList);
		    ExportSetting exportSetting = new ExportSetting();
		    exportSetting.setSheetName("派送单列表");
		    exportSetting.setSize(NUMBER);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	        return objExporterExecutor.exportSync(exportResource, exportSetting);
		} else
		{
			//返回空
			return null;
		}
	}

	/**
	 * 
	 * 根据条件查询派送单明细
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-4 上午9:15:23
	 */
	@Override
	public InputStream queryDeliverbillDetailLists(DeliverbillDto deliverbillDto) {
		// 当前部门编码
			String orgCode = FossUserContextHelper.getOrgCode();
		//若当前部门编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if (saleDepartment != null && saleDepartment.checkStation())
			{
				deliverbillDto.setTransferCenter(saleDepartment.getTransferCenter());
			}
				deliverbillDto.setOrgCode(orgCode);
			//根据输入条件，查询派送单
			List<DeliverbillDetailDto> deliverbillDetailList =  deliverbillDetailDao.queryByDeliverbillNos(deliverbillDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			if(CollectionUtils.isNotEmpty(deliverbillDetailList)){
				for (DeliverbillDetailDto deliverbill : deliverbillDetailList) {
					List<String> columnList = new ArrayList<String>();
					//派送单号
					columnList.add(deliverbill.getDeliverbillNo());
					columnList.add(deliverbill.getWaybillNo());//单号
					if(StringUtil.isNotBlank(deliverbill.getArrivesheetNo()) && DeliverbillConstants.NULL_ARRIVESHEET_NO.equals(deliverbill.getArrivesheetNo()))
					{
						columnList.add(null);//到达联编号
					}else {
						columnList.add(deliverbill.getArrivesheetNo());//到达联编号
					}
					if(null != deliverbill.getWaybillGoodsQty()){
						columnList.add( deliverbill.getWaybillGoodsQty().toString());//开单件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//开单件数
					}
					if(null != deliverbill.getStockGoodsQty()){
						columnList.add(deliverbill.getStockGoodsQty().toString());//库存件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//库存件数
					}
					if(null != deliverbill.getArrangeGoodsQty()){
						columnList.add(deliverbill.getArrangeGoodsQty().toString());//排单件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//排单件数
					}
					if(null != deliverbill.getWeight()){
						columnList.add(deliverbill.getWeight().toString());//排单重量
					}else {
						columnList.add(BigDecimal.ZERO.toString());//排单重量
					}
					if(null != deliverbill.getVolumeTotal()){
						columnList.add(deliverbill.getVolumeTotal().toString());//体积
					}else {
						columnList.add(BigDecimal.ZERO.toString());//体积
					}
					columnList.add(deliverbill.getGoodsPackage());//包装
					ProductEntity  pro =productService.getProductByCache(deliverbill.getTransportType(),new Date());
					if(null != pro){
						columnList.add(pro.getName());//运输性质
					}else {
						columnList.add(deliverbill.getTransportType());//运输性质
					}
					//导出的派送单明细里不包含一下两个字段
					/*columnList.add(deliverbill.getConsignee());//收货人
					columnList.add(deliverbill.getConsigneeContact());//联系方式
					*/	
					columnList.add(deliverbill.getConsigneeAddress());//送货地址
					columnList.add(DictUtil.rendererSubmitToDisplay(deliverbill.getDeliverType(), DictionaryConstants.PICKUP_GOODS));//提货方式
					if(null != deliverbill.getSignTime()){
						columnList.add(WAYBILL_IS_SIGN);
					}else {
						columnList.add(WAYBILL_IS_NOT_SIGN);
					}
					columnList.add(DateUtils.convert(deliverbill.getSignTime(), DateUtils.DATE_TIME_FORMAT));//签收时间
					//车辆
					columnList.add(deliverbill.getVehicleNo());
					if(StringUtil.isNotBlank(deliverbill.getSendCarType())){
						if(DeliverbillConstants.NOMAL.equals(deliverbill.getSendCarType())){//正常
							columnList.add("正常");//派车类型
						}else if(DeliverbillConstants.SPECIAL.equals(deliverbill.getSendCarType())){// 专车
							columnList.add("专车");//派车类型
						}else {//带人
							columnList.add("带人");//派车类型
						}
						
					}else {
						columnList.add("");
					}
					columnList.add(deliverbill.getDriverCode());//司机工号
					//司机姓名
					columnList.add(deliverbill.getDriverName());
					//排单时间
					columnList.add(DateUtils.convert(deliverbill.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
					rowList.add(columnList);
				}
				String[] rowHeads = {"派送单号","单号","到达联编号","开单件数","库存件数","排单件数","排单重量",
						"体积","包装","运输性质",/*"收货人","联系方式",*/"送货地址","提货方式",
						"是否已签收","签收时间","车牌号","派车类型","司机工号","司机姓名","排单时间"};
				
				ExportResource exportResource = new ExportResource();
				exportResource.setHeads(rowHeads);
				exportResource.setRowList(rowList);
				ExportSetting exportSetting = new ExportSetting();
				exportSetting.setSheetName("派送单明细列表");
				exportSetting.setSize(NUMBER);
				ExporterExecutor objExporterExecutor = new ExporterExecutor();
				return objExporterExecutor.exportSync(exportResource, exportSetting);
			}else {
				return null;
			}
		} else
		{
			return null;
		}
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 
	 * 是否存在派送单
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-22 
	 *				上午10:58:04
	 * @param deliverbill
	 * @param waybill
	 * @return
	 * @see
	 */
	@Override
	public boolean isExistDeliverbill(String waybill) 
	{
		return deliverbillDao.isExistDeliverbill(waybill);
	}

    /**
     * 
     * 查询排单统计信息
     * @author 043258-foss-zhaobin
     * @date 2013-6-25 下午4:31:52
     */
	@Override
	public DeliverbillDto queryDeliverbillTotal(WaybillToArrangeDto waybillToArrangeDto) {
		
		//如果传入dto不为空
		if(waybillToArrangeDto != null)
		{
			if(FossConstants.YES.equals(waybillToArrangeDto.getIsStoring()))
			{
				//查询排单返回Dto
				return this.deliverbillDetailDao.queryWaybillToStoringArrangeTotal(waybillToArrangeDto);
			}else{
				//查询排单返回Dto
				return this.deliverbillDetailDao.queryWaybillToArrangeTotal(waybillToArrangeDto);
			}
		}
		return null;
	}
	/**
	 * 
	 * 根据运单号查询派送情况
	 * @author foss-meiying
	 * @date 2013-7-2 上午10:57:16
	 */
	public List<DeliverbillDto> queryPartDeliverbillbyWaybill(DeliverbillDto deliverbillDto){
		return deliverbillDao.queryPartDeliverbillbyWaybill(deliverbillDto);
	}

	/**
	 * 
	 * 更新可排单件数
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-1 下午5:04:19
	 */
	@Override
	public void updateWaybillToArrangeCountByCondition(WaybillToArrangeDto waybillToArrangeDto) {
		if (waybillToArrangeDto != null && StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo())) 
		{
			waybillToArrangeDto.setArrayWaybillNos(waybillToArrangeDto.getWaybillNo().split("\\n"));
			String waybillNos[] = waybillToArrangeDto.getArrayWaybillNos();
			for (String waybillNo : waybillNos) {
				boolean flag = deliverbillDao.isNotCancelDeliverbill(waybillNo);
				if(!flag){
					ActualFreightDto dto = new ActualFreightDto();
					dto.setWaybillNo(waybillNo);
					dto.setArrangeGoodsQty(NumberUtils.INTEGER_ZERO);
					actualFreightDao.updatePartGoodsQtyControlByWaybillNo(dto);
				}
			}
		}
	}

	public void setCommonExpressVehicleService(ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	
	/**
	 * 
	 *传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 *再判断该运单集合中是否有付款方式为网上支付未支付完成的。
	 * @author meiying
	 * @date 2013-11-03 下午4:18:59queryUnActiveRfcWaybillNo
	 */
	public DeliverbillVo checkWaybillNos(List<String> waybillNos){
		DeliverbillVo vo = new DeliverbillVo();
		WaybillQueryInfoDto queryDto=  new WaybillQueryInfoDto();
		queryDto.setWaybillList(waybillNos);
		queryDto.setActive(FossConstants.YES);
		queryDto.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);//付款方式为网上支付的
		vo.setWaybillNos(waybillRfcService.isExsitsWayBillRfcs(waybillNos)); 
		vo.setNotWaybillrfcNos(waybillRfcService.queryUnActiveRfcWaybillNo(waybillNos));
		//ISSUE-4379查询有效的运单，支付方式为网上支付的
		List<String> paidMethodOLWaybillNos=waybillManagerService.queryWaybillNoMakeupByWaybillNo( queryDto);
		if(CollectionUtils.isNotEmpty(paidMethodOLWaybillNos)){//如果查出的不为空 调用结算接口
			//调用结算接口判断 如果网上支付未完成时 给出相应提示
			//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
			String vestSystemCode = null;
			List<String> notPayWaybillNos = null;
            try {
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(paidMethodOLWaybillNos,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".checkWaybillNos",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".checkWaybillNos");
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				notPayWaybillNos=takingService.unpaidOnlinePay(paidMethodOLWaybillNos);
				if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
					vo.setNotPayByOLWaybillNos(notPayWaybillNos);
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//调用CUBC查询物流交易单  应收信息校验  353654
					FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
					requestDto1.setWaybillNos(paidMethodOLWaybillNos);
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
						Set<String> keySet = dataMap.keySet();
						for (String waybillNo : keySet) {
							List<TradeDO> tradeslist = dataMap.get(waybillNo);
							if(!CollectionUtils.isEmpty(tradeslist)){
								for (TradeDO tradeDO : tradeslist) {
									if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
											equals(tradeDO.getOrderSubType())&&
											SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
											equals(tradeDO.getOrderSubType())
											&&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
										//添加数据
										notPayWaybillNos.add(waybillNo);
										if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
											vo.setNotPayByOLWaybillNos(notPayWaybillNos);
										}
									}
								}
							}	
						}
					}
			}
			//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
		}
		return vo;
	}
	
	@Override
	public DeliverbillEntity queryDeliverbillDetailEntityByWaybillNo(String waybillNo, List<String> statusList){
		return deliverbillDao.queryDeliverbillDetailEntityByWaybillNo(waybillNo, statusList);
	}

	 /**
	 * 校验传入的整车运单是否做配载和到达
	 *  @author 159231-foss-meiying
	 * @date 2014-5-6 下午7:32:40
	 */
	public void checkWayBillNosWVH(List<String> waybillNos){
		if(CollectionUtils.isNotEmpty(waybillNos)){//如果传入的运单号为空
			for (String waybillNo : waybillNos) {
				String message = arrivalService.validateWaybillNo(waybillNo, true);
				if(message != null)
				{
					if(message.startsWith(WAYBILL_NO_ASSEMBLE)){
						throw new RepaymentException("该整车运单:"+waybillNo+"未做配载，不允许创建派送单。");
					}else {
						throw new RepaymentException("该整车运单:"+waybillNo+"未录入到达，不允许创建派送单。");
					}
				}
			}
		}
	}
	 /**
	  * 
	  * 更新派送明细
	  * @author 043258-foss-zhaobin
	  * @date 2014-3-11 下午3:03:39
	  */
	@Override
	public void updateDeliverbillDetail(DeliverbillDetailEntity deliverbillDetailEntity) {
			deliverbillDetailDao.updateDetailByWaybillNo(deliverbillDetailEntity);
	}
	
	/**
	 * 根据拖动后更新派送单明细运单的序号
	 * @author 239284
	 * @param ids  派送单明细id集合
	 */
	@Transactional
	public int updateDeliverDetailSeriNoByDrag(List<String> ids) {
		if (null == ids && ids.size()  < 1) {
			throw new DeliverbillException("没有可排序的派送单明细列表!");
		}
		for (int i = 0; i < ids.size(); i++) {
			  this.deliverbillDetailDao.updateDeliverDetailSeriNoByDrag(ids.get(i), i+1);
		}
		return 1;
	}
	
	/**
	 * 根据运单号，查询司机信息集合 
	 * @author 302346	DN201606250013
	 * @param waybillNo 运单号
	 * @param status	派送单状态
	 * @return List<DeliverbillEntity>	包含司机编号、司机姓名和车牌号等信息的派送单集合
	 */
	public List<DeliverbillEntity> queryDeliverbillDetailListByWaybillNo(
			String waybillNo, String status){
		return deliverbillDao.queryDeliverbillDetailListByWaybillNo(waybillNo, status);
	}
	
	/**
	 * 根据运单号，查询送货中的司机信息
	 * @author 302346	DN201606250013
	 * @param ArriveSheetEntity 到达联实体
	 * @return DeliverbillDto	派送单实体
	 * 如果没有送货中的有效地到达联，返回null；否则返回包含司机编号、司机姓名和车牌号等信息的派送单集合DTO。
	 */
	public List<DeliverbillDto> queryDriverByWaybillNo(ArriveSheetEntity entity){
		entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);//派送中的到达联
		List<DeliverbillDto> deliverbillListDto = new ArrayList<DeliverbillDto>();
		List<DeliverbillEntity> deliverbillEntityList = new ArrayList<DeliverbillEntity>();
		//查询送货中的有效地到达联
		ArriveSheetEntity arriveSheetEntity = arriveSheetManngerService.queryArriveSheetByEntity(entity);
		if(arriveSheetEntity!=null){
			//查询司机信息
			deliverbillEntityList = deliverbillService.queryDeliverbillDetailListByWaybillNo(entity.getWaybillNo(), DeliverbillConstants.STATUS_CANCELED);
			if(CollectionUtils.isNotEmpty(deliverbillEntityList)){
				for(DeliverbillEntity item:deliverbillEntityList){
					DeliverbillDto deliverbillDto = new DeliverbillDto();
					deliverbillDto.setDriverCode(item.getDriverCode());//司机编号
					deliverbillDto.setDriverName(item.getDriverName());//司机姓名
					deliverbillDto.setVehicleNo(item.getVehicleNo());//车牌号
					deliverbillListDto.add(deliverbillDto);
				}
				return deliverbillListDto;
			}
		}
		return null;
	}
	
	
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}

	public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}
	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}
	/**
	 * 设置deliverHandoverbillDao  
	 * @param deliverHandoverbillDao deliverHandoverbillDao 
	 */
	public void setDeliverHandoverbillDao(
			IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}
	public void setDeliverbillNewService(
			IDeliverbillNewService deliverbillNewService) {
		this.deliverbillNewService = deliverbillNewService;
	}
	
	
}