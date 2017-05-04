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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/DeliverbillAction.java
 * 
 * FILE NAME        	: DeliverbillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.

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


修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-22 	新增	王辉	V0.1
2012-7-19	根据评审意见：
1.	加入总体积
2.	加入查看装车人功能	王辉	V0.3
2012-07-24	业务评审完毕，升至0.9	王辉	V0.9





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
		
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverHandoverbillVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.util.define.FossConstants;



/**
 * 派送交单ACTION
 * @author 159231 meiying
 * 2015-4-13  上午10:51:35
 */
public class DeliverHandoverbillAction extends AbstractAction {

	/** 
	 * 序列化
	 */
	private static final long serialVersionUID = 7512460180364008438L;
	private DeliverHandoverbillVo vo;
	private IDeliverHandoverbillService deliverHandoverbillService;
	private ITakingService takingService;

	
	/**
	 * 查询待交单运单信息
	 * @author 159231 meiying
	 * 2015-4-21  上午8:40:23
	 * @return
	 */
	@JSON
	public String queryPreDeliverHandover() {
		try {
			if(vo.getPreDeliverHandoverbillQueryDto()!=null){
				if(StringUtil.isNotBlank(vo.getPreDeliverHandoverbillQueryDto().getWaybillNo())){
					List<PreDeliverHandoverbillDto> lists =deliverHandoverbillService.queryPreDeliverHandoverByWaybillNosList(vo.getPreDeliverHandoverbillQueryDto(), this.getStart(), this.getLimit());
					if(CollectionUtils.isNotEmpty(lists)){
						List<String> notPayWaybillNos=new ArrayList<String>();
						for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : lists) {
							if(StringUtils.isNotBlank(preDeliverHandoverbillDto.getPaidMethod())&&SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(preDeliverHandoverbillDto.getPaidMethod())){
								notPayWaybillNos.add(preDeliverHandoverbillDto.getWaybillNo());
								
							}
						}
						if(notPayWaybillNos!=null && notPayWaybillNos.size()>0){
							//调用结算接口判断 如果网上支付未完成时 给出相应提示
		                	List<String> notPayWaybillNos1=takingService.unpaidOnlinePay(notPayWaybillNos);
		                	if(notPayWaybillNos1!=null && notPayWaybillNos1.size()>0){
		                		for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : lists) {
		                			if(notPayWaybillNos1.contains(preDeliverHandoverbillDto.getWaybillNo())){
		                				preDeliverHandoverbillDto.setIsOLNotPaid(FossConstants.YES);
		                			}
									
								}
		                	}
							
						}
						this.setTotalCount(Long.valueOf(lists.size()));
						// 查询符合条件的记录列表
						vo.setPreDeliverHandoverbillDtos(lists);
					}else{
						vo.setPreDeliverHandoverbillDtos(null);
						// 设置页面显示的记录总数
						this.setTotalCount(Long.valueOf(0));
					}
				}else{
					// 查询符合条件的运单总记录数
					Long count= this.deliverHandoverbillService.queryPreDeliverHandoverCount(vo.getPreDeliverHandoverbillQueryDto());
					// 根据运单总记录数
					if (count != null && count>0) {
						this.setTotalCount(count);
						List<PreDeliverHandoverbillDto> lists=deliverHandoverbillService.queryPreDeliverHandoverList(vo.getPreDeliverHandoverbillQueryDto(), this.getStart(), this.getLimit());
						if(CollectionUtils.isNotEmpty(lists)){
							List<String> notPayWaybillNos=new ArrayList<String>();
							for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : lists) {
								if(StringUtils.isNotBlank(preDeliverHandoverbillDto.getPaidMethod())&&SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(preDeliverHandoverbillDto.getPaidMethod())){
									notPayWaybillNos.add(preDeliverHandoverbillDto.getWaybillNo());
								}
							}
							if(notPayWaybillNos!=null && notPayWaybillNos.size()>0){
								//调用结算接口判断 如果网上支付未完成时 给出相应提示
			                	List<String> notPayWaybillNos1=takingService.unpaidOnlinePay(notPayWaybillNos);
			                	if(notPayWaybillNos1!=null && notPayWaybillNos1.size()>0){
			                		for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : lists) {
			                			if(notPayWaybillNos1.contains(preDeliverHandoverbillDto.getWaybillNo())){
			                				preDeliverHandoverbillDto.setIsOLNotPaid(FossConstants.YES);
			                			}
										
									}
			                	}
								
							}
						}
						// 查询符合条件的记录列表
						vo.setPreDeliverHandoverbillDtos(lists);
					} else {
						vo.setPreDeliverHandoverbillDtos(null);
						// 设置页面显示的记录总数
						this.setTotalCount(Long.valueOf(0));
					}
				}
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
    /**
     * 查询已交单运单信息
     * @author 159231 meiying
     * 2015-4-21  上午8:40:23
     * @return
     */
    @JSON
    public String queryDeliverHandoverList() {
        try {
            // 查询符合条件的运单总记录数(里面有调用initDeliverHandoverbillQuery方法初始化部分数据)
            Long count= this.deliverHandoverbillService.queryDeliverHandoverCount(vo.getDeliverHandoverbillQueryDto());
            // 根据运单总记录数
            if (count != null && count>0) {
                this.setTotalCount(count);
                // 查询符合条件的记录列表
                vo.setDeliverHandoverbillDtos(deliverHandoverbillService.queryDeliverHandoverList(vo.getDeliverHandoverbillQueryDto(), this.getStart(), this.getLimit()));
            } else {
                vo.setPreDeliverHandoverbillDtos(null);
                // 设置页面显示的记录总数
                this.setTotalCount(Long.valueOf(0));
            }
        } catch (BusinessException e) {
            // 有异常时，返回异常信息
            return super.returnError(e);
        }
        // 返回成功信息
        return super.returnSuccess();
    }

	/**
	 * 根据运单号查询最后一次通知的信息
	 * @author 159231 meiying
	 * 2015-4-29  下午8:10:39
	 * @return
	 */
	@JSON
	public String queryLastNotifyByWaybillNo() {
		try {
			if(vo.getPreDeliverHandoverbillDto()!= null &&StringUtils.isNotBlank( vo.getPreDeliverHandoverbillDto().getWaybillNo())){
				
				vo.setNotificationEntity(deliverHandoverbillService.queryLastNotifyByWaybillNo(vo.getPreDeliverHandoverbillDto()));
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 修改最后一次通知信息
	 * @author 159231 meiying
	 * 2015-4-29  下午8:10:39
	 * @return
	 */
	@JSON
	public String updateLastNotifyByWaybillNo() {
		try {
			if(null!=vo&&null!=vo.getNotificationEntity()){
                deliverHandoverbillService.updateLastNotifyByWaybillNo(vo.getNotificationEntity());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 修改预计送货日期
	 * @author 159231 meiying
	 * 2015-4-29  下午8:10:39
	 * @return
	 */
	@JSON
	public String updatePreDeliverDateByWaybillNo() {
		try {
			if(vo.getPreDeliverHandoverbillQueryDto()!= null){
                deliverHandoverbillService.updatePreDeliverDateByWaybillNo(vo.getPreDeliverHandoverbillQueryDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 派送交单
	 * @author 159231 meiying
	 * 2015-4-29  下午8:10:39
	 * @return
	 */
	@JSON
	public String executePreDeliverbill() {
		try {
			if(vo.getPreDeliverHandoverbillDtos()!= null){
				deliverHandoverbillService.executePreDeliverbill(vo.getPreDeliverHandoverbillDtos());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 校验网上支付的是否已经支付完成
	 * @author 159231 meiying
	 * 2015-5-7  下午7:01:20
	 * @return
	 */
	@JSON
	public String checkUnpaidOnlinePay() {
		try {
			if(vo.getPreDeliverHandoverbillQueryDto()!= null && vo.getPreDeliverHandoverbillQueryDto().getWaybillNos()!=null){
				//调用结算接口判断 如果网上支付未完成时 给出相应提示
				List<String> notPayWaybillNos=takingService.unpaidOnlinePay(vo.getPreDeliverHandoverbillQueryDto().getWaybillNos());
				if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
					vo.setNotPayByOLWaybillNos(notPayWaybillNos);
				}
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 撤销派送交单
	 * @author 159231 meiying
	 * 2015-4-29  下午8:10:39
	 * @return
	 */
	@JSON
	public String revokedPreDeliverbill() {
		try {
            if(vo.getPreDeliverHandoverbillQueryDto()!= null){
                deliverHandoverbillService.revokedPreDeliverbill(vo.getPreDeliverHandoverbillQueryDto());
            }
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	
	/**
	 * 设置deliverHandoverbillService  
	 * @param deliverHandoverbillService deliverHandoverbillService 
	 */
	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}



	/**
	 * 获取vo  
	 * @return vo vo
	 */
	public DeliverHandoverbillVo getVo() {
		return vo;
	}



	/**
	 * 设置vo  
	 * @param vo vo 
	 */
	public void setVo(DeliverHandoverbillVo vo) {
		this.vo = vo;
	}
	/**
	 * 设置takingService  
	 * @param takingService takingService 
	 */
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}


	

}