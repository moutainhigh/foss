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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送排单Action.
 *
 * @author ibm-
 * 		wangxiexu
 * @date 2012-10-17
 * 		上午9:49:32
 * @since
 * @version
 */
public class DeliverbillAction extends AbstractAction {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillAction.class);

	/** 
	 * 序列化
	 */
	private static final long serialVersionUID = 7512460180364008438L;

	/**
	 * 符号
	 */
	private static final String STRING_DELIMETER = ",";

	/**
	 * 派送单vo
	 */
	private DeliverbillVo deliverbillVo = new DeliverbillVo();
	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 100;

	/**
	 * 派送单service
	 */
	private IDeliverbillService deliverbillService;

	/**
	 * 车辆service
	 */
	private IVehicleService vehicleService;

	/**
	 * 配置参数
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 车队编码
	 */
	private String fleetCode;
	/**
	 * sequence
	 */
	private String sequence;
	/**
	 * 省名称
	 */
	private String provName;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 行政区域 Service接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 根据查询条件，查询派送单，分页显示.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-22 
	 * 		下午4:39:58
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillList() {
		try {
			//查询总条数
			this.totalCount = this.deliverbillService.queryDeliverbillCountByCondition(this.deliverbillVo.getDeliverbillDto());
			//若总条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//查询派送单
				this.deliverbillVo.setDeliverbillDtoList(this.deliverbillService.queryDeliverbillList(this.deliverbillVo.getDeliverbillDto(),this.getStart(), this.getLimit()));
			} else {
				//设置为null
				this.deliverbillVo.setDeliverbillDtoList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据查询条件，查询派送单，分页显示.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-22 
	 * 		下午4:39:58
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillListForSale() {
		try {
			//查询总条数
			this.totalCount = this.deliverbillService.queryDeliverbillCountByCondition(this.deliverbillVo.getDeliverbillDto());
			//若总条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//查询派送单
				this.deliverbillVo.setDeliverbillDtoList(this.deliverbillService.queryDeliverbillList(this.deliverbillVo.getDeliverbillDto(),this.getStart(), this.getLimit()));
			} else {
				//设置为null
				this.deliverbillVo.setDeliverbillDtoList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbill() {
		try {
			if(null ==deliverbillVo || null == deliverbillVo.getDeliverbillDto() || StringUtils.isBlank(this.deliverbillVo.getDeliverbillDto().getId())){
				throw new BusinessException ("查询派送单信息，传入查询参数不能为空！"); 
			}
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.deliverbillVo.getDeliverbillDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillForSale() {
		try {
			if(null==deliverbillVo||null==deliverbillVo.getDeliverbillDto()||null==deliverbillVo.getDeliverbillDto().getId()){
				LOGGER.info("查询派送单信息，传入的查询参数不能为空！");
				throw new BusinessException("查询派送单信息，传入的查询参数不能为空！");
			}
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.deliverbillVo.getDeliverbillDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillForArriveSheet() {
		try {
			if(null==deliverbillVo||null==deliverbillVo.getDeliverbillDto()||null==deliverbillVo.getDeliverbillDto().getId()){
				LOGGER.info("查询派送单信息，传入的查询参数不能为空！");
				throw new BusinessException("查询派送单信息，传入的查询参数不能为空！");
			}
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.deliverbillVo.getDeliverbillDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查询派送单下的明细信息，分页显示.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30
	 * 		 下午7:45:29
	 */
	@JSON
	public String queryDeliverbillDetailList() {
		try {
			// 传入对象非空判断
			if (null == this.deliverbillVo||null==this.deliverbillVo.getDeliverbillDto()||null == this.deliverbillVo.getDeliverbillDto().getId()) {
				LOGGER.info("查询派送单下的明细信息,传入参数不能为空！");
				// 如果没有值则抛出异常
				throw new SettlementException("查询派送单下的明细信息,传入参数不能为空！");
			}
			//根据派送单ID查找派送单明细条数
			this.totalCount = this.deliverbillService.queryDeliverbillDetailCountByDeliverbillId(this.deliverbillVo.getDeliverbillDto().getId());
			//若大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//根据派送单ID查找派送单明细列表大小
				this.deliverbillVo.setDeliverbillDetailList(this.deliverbillService.queryDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId(), this.getStart(), this.getLimit()));
			} else {
				//设置为null
				this.deliverbillVo.setDeliverbillDetailList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 
	 *查询派送单明细 非分页
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-7 下午5:26:00
	 */
	@JSON
	public String queryDeliverbillDetailsList() {
		try {
			if(null==deliverbillVo||null==deliverbillVo.getDeliverbillDto()||null==deliverbillVo.getDeliverbillDto().getId()){
				LOGGER.info("查询派送单明细非分页,传入的查询参数不能为空！");
				throw new BusinessException("查询派送单明细非分页,传入的查询参数不能为空！");
			}
			this.deliverbillVo.setDeliverbillDetailList(this.deliverbillService.queryDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	

	/**
	 * 查询派送单下的全部明细信息.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30 
	 * 		下午7:45:29
	 */
	@JSON
	public String queryAllDeliverbillDetail() {
		try {
			//根据派送单ID查找派送单明细列表
			this.deliverbillVo.setDeliverbillDetailList(this.deliverbillService.queryDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查询派送单下已生成到达联的派送明细列表.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30
	 * 		 下午7:45:29
	 */
	@JSON
	public String queryDeliverbillArrivesheetList() {
		try {
			//根据派送单ID查找已生成到达联的派送明细列表
			this.deliverbillVo.setDeliverbillDetailList(this.deliverbillService.queryDeliverbillArrivesheetList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询派送单下已生成到达联的派送明细列表.
	 *
	 * @return the string
	 * @author ibm-
	 * 		wangxiexu
	 * @date 2012-10-30
	 * 		 下午7:45:29
	 */
	@JSON
	public String queryDeliverbillArrivesheetListForSale() {
		try {
			//根据派送单ID查找已生成到达联的派送明细列表
			this.deliverbillVo.setDeliverbillDetailList(this.deliverbillService.queryDeliverbillArrivesheetList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	

	/**
	 * 删除派送单明细.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 */
	@JSON
	public String deleteDeliverbillDetails() {
		try {
			//从派送单中批量移除派送单明细
			if (this.deliverbillVo.getDeliverbillDetailIds() != null) {
				this.deliverbillVo.setDeliverbill(this.deliverbillService.deleteDeliverbillDetails(this.deliverbillVo.getDeliverbillDto().getId(),
												  this.deliverbillVo.getDeliverbillDetailIds().split(STRING_DELIMETER)));
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 提高派送单明细序号.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 */
	@JSON
	public String upgradeDeliverbillDetail() {
		try {
			//提高派送单明细优先级
			this.deliverbillService.upgradeDeliverbillDetail(this.deliverbillVo.getDeliverbillDto().getId(), this.deliverbillVo.getDeliverbillDetail().getId(), this.deliverbillVo.getDeliverbillDetail().getSerialNo());
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 降低派送单明细序号.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 */
	@JSON
	public String downgradeDeliverbillDetail() {
		try {
			//降低派送单明细优先级
			this.deliverbillService.downgradeDeliverbillDetail(this.deliverbillVo.getDeliverbillDto().getId(),
					this.deliverbillVo.getDeliverbillDetail().getId(),this.deliverbillVo.getDeliverbillDetail().getSerialNo());
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 
	 * 查询待排运单
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-9 下午2:39:50
	 */
	@JSON
	public String queryWaybillToArrangeByWaybillNo() {
		try {
			// --- 设置默认查询条件 ---
			this.initQueryWaybillToArrangeCondition(this.deliverbillVo.getWaybillToArrangeDto());
			//根据查询条件查询待排运单数量条数
			this.totalCount = this.deliverbillService.queryWaybillToArrangeCountByCondition(this.deliverbillVo.getWaybillToArrangeDto());
			//条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//根据查询条件查询待排运单
				this.deliverbillVo.setWaybillToArrangeDtoList(this.deliverbillService.queryWaybillToArrangeByCondition(
										this.deliverbillVo.getWaybillToArrangeDto(), this.getStart(), this.getLimit()));
				if(this.getStart()== 0)
				{
					DeliverbillDto deliverbillDto = this.deliverbillService.queryDeliverbillTotal(this.deliverbillVo.getWaybillToArrangeDto());
					deliverbillDto.setTotalGoodsQty(totalCount.toString());
					//根据查询条件查询待排运单
					this.deliverbillVo.setDeliverbillDto(deliverbillDto);
				}
			} else {
				DeliverbillDto deliverbillDto = new DeliverbillDto();
				deliverbillDto.setTotalGoodsQty(NumberConstants.NUMERAL_S_ZORE);
				deliverbillDto.setTotalGoodsWeight(BigDecimal.ZERO);
				deliverbillDto.setTotalGoodsVolume(BigDecimal.ZERO);
				//设置为null
				this.deliverbillVo.setWaybillToArrangeDtoList(null);
				this.deliverbillVo.setDeliverbillDto(deliverbillDto);
				throw new DeliverbillException(DeliverbillException.DELIVERBILL_CANT_OPERATE);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询待排运单.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:45:29
	 */
	@JSON
	public String queryWaybillToArrange() {
		try {
			// --- 设置默认查询条件 ---
			this.initQueryWaybillToArrangeCondition(this.deliverbillVo.getWaybillToArrangeDto());
			this.getDeliverbillVo().getWaybillToArrangeDto().setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);//快递		
			//过滤快递产品的运单 add by yangkang
			this.getDeliverbillVo().getWaybillToArrangeDto().setIsExpress(FossConstants.YES);
			//修复可排单件数
			this.deliverbillService.updateWaybillToArrangeCountByCondition(this.deliverbillVo.getWaybillToArrangeDto());
			//根据查询条件查询待排运单数量条数
			this.totalCount = this.deliverbillService.queryWaybillToArrangeCountByCondition(this.deliverbillVo.getWaybillToArrangeDto());
			//条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//根据查询条件查询待排运单
				this.deliverbillVo.setWaybillToArrangeDtoList(this.deliverbillService.queryWaybillToArrangeByCondition(
										this.deliverbillVo.getWaybillToArrangeDto(), this.getStart(), this.getLimit()));
				if(this.getStart()== 0)
				{
					DeliverbillDto deliverbillDto = this.deliverbillService.queryDeliverbillTotal(this.deliverbillVo.getWaybillToArrangeDto());
					deliverbillDto.setTotalGoodsQty(totalCount.toString());
					//根据查询条件查询待排运单
					this.deliverbillVo.setDeliverbillDto(deliverbillDto);
				}
			} else {
				DeliverbillDto deliverbillDto = new DeliverbillDto();
				deliverbillDto.setTotalGoodsQty(NumberConstants.NUMERAL_S_ZORE);
				deliverbillDto.setTotalGoodsWeight(BigDecimal.ZERO);
				deliverbillDto.setTotalGoodsVolume(BigDecimal.ZERO);
				//设置为null
				this.deliverbillVo.setWaybillToArrangeDtoList(null);
				this.deliverbillVo.setDeliverbillDto(deliverbillDto);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 设置查询待排运单初始条件.
	 *
	 * @param waybillToArrangeDto 查询待排运单初始条件
	 * @author ibm-wangxiexu
	 * @date 2012-11-14 上午10:09:09
	 */
	private void initQueryWaybillToArrangeCondition(
			WaybillToArrangeDto waybillToArrangeDto) {
		// --- 运单状态为ACTIVE ---
		waybillToArrangeDto.setWaybillActive(FossConstants.ACTIVE);

		// --- 精准卡航产品代码 ---
		waybillToArrangeDto.setFastWaybillCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);

		// --- 通知客户成功标志 ---
		waybillToArrangeDto.setNotifySuccessFlag(NotifyCustomerConstants.SUCCESS);

		// --- 更改处理状态为已受理 ---
		waybillToArrangeDto.setRfcStatus(WaybillRfcConstants.ACCECPT);
		
		// --- 更改处理状态为受理拒绝 ---
		waybillToArrangeDto.setRfcStatusAcceptDeny(WaybillRfcConstants.ACCECPT_DENY);
		
		// --- 更改处理状态为审核拒绝---
		waybillToArrangeDto.setRfcStatusAuditDeny(WaybillRfcConstants.AUDIT_DENY);

		// --- 异常类型为运单异常 ---
		waybillToArrangeDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);

		// --- 异常处理状态为已受理 ---
		waybillToArrangeDto.setExceptionStatus(ExceptionProcessConstants.HANDLING);

		// --- 关联的到达联有效 ---
		waybillToArrangeDto.setArrivesheetActive(FossConstants.ACTIVE);

		// --- 关联的到达联未删除
		waybillToArrangeDto.setArrivesheetDestroyed(FossConstants.INACTIVE);

		// --- 关联的到达联为“生成”状态
		waybillToArrangeDto.setArrivesheetStatus(ArriveSheetConstants.STATUS_GENERATE);
		
		//家装项目的提货方式不希望交单、创建派送单 - start - 239284
		List<String> notReceviedMethods = new ArrayList<String>();
		DataDictionaryEntity datas = DictUtil.getDataByTermsCode("PICKUPGOODSSPECIALDELIVERYTYPE");
		if (null != datas) {
			for (DataDictionaryValueEntity value : datas.getDataDictionaryValueEntityList()) {
				notReceviedMethods.add(value.getValueCode());
			}
		}
		waybillToArrangeDto.setNotReceviedMethods(notReceviedMethods);
		//家装项目的提货方式不希望交单、创建派送单 - end - 239284
	}

	/**
	 * 添加运单至派送单.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-13 下午1:58:07
	 */
	@JSON
	public String addWaybillToArrange() {
		//根据派送单ID查找派送单信息
		DeliverbillEntity deliverbill = this.deliverbillService.queryDeliverbill(this.deliverbillVo.getDeliverbill().getId());
		MutexElement mutexElement = new MutexElement(deliverbill.getDeliverbillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
		try {
			//若派送单不为空
			if (deliverbill != null) {
				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				//互斥锁定
				boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
				//若未上锁
				if(!isLocked){
					//抛出派送单异常
					throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
				}
				//添加运单至派送单
				this.deliverbillVo.setWaybillToArrangeDtoList(this.deliverbillService.addWaybillToArrange(deliverbill,this.deliverbillVo.getWaybillToArrangeDtoList(),currentInfo));
				//解锁
				businessLockService.unlock(mutexElement);
			}
			//返回成功
			return super.returnSuccess("排单成功");
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 通过车牌号查询车辆信息(包括公司车和外请车).
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-19 下午1:42:50
	 */
	@JSON
	public String queryVehicleByVehicleNo() {
		try {
			if(null==deliverbillVo || null==deliverbillVo.getDeliverbillDto()){
				LOGGER.info("通过车牌号查询车辆信息,传入参数不能为空！");//车牌号再里层的使用有判空，这里就不判断了
				throw new BusinessException("通过车牌号查询车辆信息,传入参数不能为空！");
			}
			//提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO
			this.deliverbillVo.setVehicleAssociationDto(this.vehicleService.queryVehicleAssociationDtoByVehicleNo(this.deliverbillVo.getDeliverbillDto().getVehicleNo()));
			/*
			 * 刷新司机信息 SR9 1. 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 2.
			 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 3. 当排班和PDA绑定同时存在时，以PDA绑定为准
			 */
			if (this.deliverbillVo.getVehicleAssociationDto() != null) {
				this.deliverbillVo.setDriverDto(this.deliverbillService.queryDriverByVehicleNo(this.deliverbillVo.getVehicleAssociationDto().getVehicleNo()));
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据车牌号找寻司机信息.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-22 下午4:48:27
	 */
	@JSON
	public String queryVehiclePdaSigned() {
		try {
			/*
			 * 刷新司机信息 SR9 1. 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 2.
			 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 3. 当排班和PDA绑定同时存在时，以PDA绑定为准
			 */
			if(null==deliverbillVo || null==deliverbillVo.getDeliverbillDto()||null==deliverbillVo.getDeliverbillDto().getVehicleNo()){
				LOGGER.info("通过车牌号寻找司机信息,传入参数不能为空！");
				throw new BusinessException("通过车牌号寻找司机信息,传入参数不能为空！");
			}
			this.deliverbillVo.setDriverDto(this.deliverbillService.queryDriverByVehicleNo(this.deliverbillVo.getDeliverbillDto().getVehicleNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 更新预派送单.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-22 下午4:54:00
	 */
	@JSON
	public String saveDeliverbill() {
		try {
			//外请司机不能修改
			/*if(this.deliverbillVo.getDeliverbill().getDelStatus().equals("LOADED")|| this.deliverbillVo.getDeliverbill().getDelStatus().equals("CONFIRMED")){
				int driverCode=this.deliverbillVo.getDeliverbill().getDriverCode().length();
				if(driverCode == NumberConstants.NUMBER_15 || driverCode == NumberConstants.NUMBER_18)
				{
					return super.returnError("外请司机暂时不能更改，请选择其他类型司机");
				}
				
			}*/
			//保存(新增/更新)派送单
			DeliverbillEntity deliverbill = this.deliverbillService.saveDeliverbill(this.deliverbillVo.getDeliverbill());
			//若派送单为空
			if (deliverbill == null) {
				//返回异常
				return super.returnError("保存失败");
			}
			//设置派送单
			this.deliverbillVo.setDeliverbill(deliverbill);
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}

	}

	/**
	 * 编辑/新增预派送单入口.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-27 下午8:31:15
	 */
	@JSON
	public String editDeliverbillIndex() {
		try {
			//若派送单id为空
			if (this.deliverbillVo.getDeliverbill().getId() == null|| "".equals(this.deliverbillVo.getDeliverbill().getId())) {
				this.deliverbillVo.getDeliverbill().setId("");

				// SUC-447 –创建预派送单 SR6 预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001。
				// 生成P+9位编号
				this.deliverbillVo.getDeliverbill().setDeliverbillNo("P" + deliverbillService.querySequence());
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 编辑/新增预派送单入口.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-27 下午8:31:15
	 */
	@JSON
	public String editToDeliverbillIndex() {
		try {
			//若派送单id为空
			if (this.deliverbillVo.getDeliverbill().getId() == null|| "".equals(this.deliverbillVo.getDeliverbill().getId())) {
				this.deliverbillVo.getDeliverbill().setId("");

				// SUC-447 –创建预派送单 SR6 预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001。
				// 生成P+9位编号
				this.deliverbillVo.getDeliverbill().setDeliverbillNo("P" + deliverbillService.querySequence());
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据派送单编号查询装车任务.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午4:02:44
	 */
	@JSON
	public String queryLoadTask() {
		try {
			//根据派送单编号查询装车任务
			this.deliverbillVo.setLoadTaskDto(this.deliverbillService.queryLoadTaskByDeliverbillNo(this.deliverbillVo.getDeliverbillDto().getDeliverbillNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据派送单编号查询装车明细.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午4:02:44
	 */
	@JSON
	public String queryLoadTaskDetail() {
		try {
			//根据装车任务编号查询装车明细
			this.deliverbillVo.setLoadTaskDetailList(this.deliverbillService.queryLoadTaskDetailListByTaskNo(this.deliverbillVo.getLoadTaskDto().getTaskNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据派送单编号查询装车差异报告.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午4:02:44
	 */
	@JSON
	public String queryLoadGaprep() {
		try {
			//根据差异报告ID查询装车差异报告明细
			this.deliverbillVo.setLoadGaprepWaybillList(this.deliverbillService.queryLoadGaprepWaybillListByRepId(this.deliverbillVo.getLoadTaskDto().getLoadGaprepId(),this.deliverbillVo.getDeliverbillDto().getDeliverbillNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 确认派送装车报告.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-2 下午5:40:04
	 */
	@JSON
	public String comfirmLoadTask() {
		try {
			//确认派送装车报告
			int result = this.deliverbillService.comfirmLoadTask(this.deliverbillVo.getDeliverbillDto().getDeliverbillNo());
			//若返回值大于0
			if (result > 0) {
				//返回成功
				return super.returnSuccess("派送装车确认成功");
			} else {
				//返回异常
				return super.returnError("派送装车确认失败");
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 退回派送装车报告.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-2 下午5:40:04
	 */
	@JSON
	public String rejectLoadTask() {
		try {
			//退回派送装车报告
			int result = this.deliverbillService.rejectLoadTask(this.deliverbillVo.getDeliverbillDto().getDeliverbillNo());
			//若返回值大于0
			if (result > 0) {
				//返回成功
				return super.returnSuccess("派送装车退回成功");
			} else {
				//返回异常
				return super.returnError("派送装车退回失败");
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 确认派送单.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-2 下午5:40:04
	 */
	@JSON
	public String confirmDeliverbill() {
		MutexElement mutexElement = new MutexElement(deliverbillVo.getDeliverbillDto().getDeliverbillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
		try {
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
			//若未上锁
			if(!isLocked){
				//抛出派送单异常
				throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
			}
			//确认预派送单
			int result = this.deliverbillService.confirmDeliverbill(this.deliverbillVo.getDeliverbillDto(),this.deliverbillVo.getLoadTaskDto().getTaskNo());
			//解锁
			businessLockService.unlock(mutexElement);
			//若返回值大于0
			if (result > 0) {
				//返回成功
				return super.returnSuccess("确认派送单成功");
			} else {
				//返回异常
				return super.returnError("确认派送单失败");
			}
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 取消派送单.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-2 下午5:40:04
	 */
	@JSON
	public String cancelDeliverbill() {
		try {
			MutexElement mutexElement = new MutexElement(deliverbillVo.getDeliverbillDto().getDeliverbillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
			//若未上锁
			if(!isLocked){
				//抛出派送单异常
				throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
			}
			//若派送单状态为已保存
			if(DeliverbillConstants.STATUS_SAVED.equals(this.deliverbillVo.getDeliverbillDto().getStatus())||
					DeliverbillConstants.STATUS_SUBMITED.equals(this.deliverbillVo.getDeliverbillDto().getStatus()))
			{
				//取消已保存的派送单
				int result = this.deliverbillService.cancelDeliverbillForSaved(this.deliverbillVo.getDeliverbillDto().getId(),this.deliverbillVo.getDeliverbillDto().getDeliverbillNo());
				//解锁
				businessLockService.unlock(mutexElement);
				if (result > 0) {
					//返回成功
					return super.returnSuccess("取消派送单成功");
				} else {
					//返回异常
					return super.returnError("取消派送单失败");
				}
			}else
			{	
				//取消已保存的派送单
				int result = this.deliverbillService.cancelDeliverbill(this.deliverbillVo.getDeliverbillDto().getId(),this.deliverbillVo.getDeliverbillDto().getDeliverbillNo());

				if (result > 0) {
					//返回成功
					return super.returnSuccess("取消派送单成功");
				} else {
					//返回异常
					return super.returnError("取消派送单失败 - 中转入库失败");
				}
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据派送装车明细ID查询扫描明细列表.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-4 下午9:34:33
	 */
	@JSON
	public String queryScanDetailList() {
		try {
			//根据派送装车明细ID查询扫描明细列表
			this.deliverbillVo.setScanDetailList(this.deliverbillService.queryScanDetailListByLoadDetailId(this.deliverbillVo.getLoadTaskDetailId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 根据派送装车明细ID查询扫描明细列表.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-4 下午9:34:33
	 */
	@JSON
	public String queryLoadGaprepDetailList() {
		try {
			//根据差异报告ID和运单号查询少货明细
			this.deliverbillVo.setLoadGaprepDetailList(this.deliverbillService.queryLoadGaprepDetailListByRepIdAndWaybillNo(
							this.deliverbillVo.getLoadTaskDto().getLoadGaprepId(), this.deliverbillVo.getWaybillNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查看装车人.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-6 上午9:39:12
	 */
	@JSON
	public String queryLoaderList() {
		try {
			//根据任务ID查找装车人
			this.deliverbillVo.setLoaderList(this.deliverbillService.queryLoaderListByTaskId(this.deliverbillVo.getLoadTaskDto().getTaskId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查看历史差异报告.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2012-12-6 上午9:39:12
	 */
	@JSON
	public String queryLoadGaprepList() {
		try {
			//根据装车任务ID和派送单号查询历史装车差异报告
			this.deliverbillVo.setLoadGaprepList(this.deliverbillService.queryLoadGaprepListByTaskIdAndDeliverbillNo(
							this.deliverbillVo.getLoadTaskDto().getTaskId(),this.deliverbillVo.getDeliverbillDto().getDeliverbillNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查询公司司机.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2013-1-4 下午9:51:38
	 */
	@JSON
	public String queryDriver() {
		try {
			//根据查询条件(工号/姓名/电话号码)查询公司司机
			this.deliverbillVo.setDriverList(this.deliverbillService.queryDriverListByDriverDto(this.deliverbillVo.getDriverDto()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 分配派送任务.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2013-1-4 下午9:51:38
	 */
	@JSON
	public String assignDriver() {
		try {
			//分配派送任务
			this.deliverbillService.assignDriver(this.deliverbillVo.getDeliverbill().getId(), this.deliverbillVo.getDriverDto());
			//返回成功
			return super.returnSuccess("分配派送任务成功");
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查询未通知客户的派送单明细列表.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2013-1-15 下午10:42:01
	 */
	@JSON
	public String queryUnnotifiedDeliverbillDetailList() {
		try {
			//查询未通知客户的派送单明细列表
			this.deliverbillVo.setDeliverbillDetailDtoList(this.deliverbillService.queryUnnotifiedDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 通知客户.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2013-1-16 上午11:32:46
	 */
	@JSON
	public String notifyUnnotifiedCustomer() {
		try {
			//通知客户
			this.deliverbillService.notifyCustomer(this.deliverbillVo.getDeliverbillDetailDtoList());
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 查询排单件数与到达联件数不一致的派送单明细列表.
	 *
	 * @return the string
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午4:03:54
	 */
	@JSON
	public String queryQtyInconsistentDeliverbillDetailList() {
		try {
			if(null==deliverbillVo||null==deliverbillVo.getDeliverbillDto()||null==this.deliverbillVo.getDeliverbillDto().getId()){
				LOGGER.info("查询排单件数与到达联件数不一致的派送单明细列表,传入的查询参数不能为空！");
				throw new BusinessException("查询排单件数与到达联件数不一致的派送单明细列表,传入的查询参数不能为空！");
			}
			// 到达联件数与排单件数不一致列表
			this.deliverbillVo.setDeliverbillDetailDtoList(this.deliverbillService
							.queryArrivesheetQtyInconsistentDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId()));

			// 库存件数与排单件数不一致列表
			this.deliverbillVo.setQtyInconsistentDeliverbillDetailList(this.deliverbillService
							.queryInStockQtyInconsistentDeliverbillDetailList(this.deliverbillVo.getDeliverbillDto().getId()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 获取组织所对应的车队.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-22 上午10:50:18
	 */
	@JSON
	public String querySuperOrg() {
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(orgAdministrativeInfoEntity != null)
		{
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()))
			{
				//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
				SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
				entity.setSalesdeptCode(orgCode);
				List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_SIZE);
				if (!CollectionUtils.isEmpty(salesMotorcadeList))
				{
					StringBuffer sb = new StringBuffer();
					for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
					{
							sb.append(salesMotorcadeEntity.getMotorcadeCode()) ;
							sb.append(",");
					}
					fleetCode=StringUtil.isNotEmpty(sb.toString()) ? sb.substring(0, sb.length()-1) :null;
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}else{
				// 调用综合组的服务获取当前组织所在的车队
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) 
				{
					fleetCode = orgAdministrativeInfoEntity1.getCode();
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 查询派送单序列.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-8 上午10:57:16
	 */
	@JSON
	public String querySequence(){
		sequence = deliverbillService.querySequence();
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 * 再判断该运单集合中是否有付款方式为网上支付未支付完成的。
	 * @author foss-meiying
	 * @date 2013-4-9 下午7:30:53
	 * @return
	 * @see
	 */
	@JSON
	public String queryIsExsitsWayBillRfcs(){
		deliverbillVo=deliverbillService.checkWaybillNos(deliverbillVo.getWaybillNos()); 
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 查询当前部门省市名称.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-8 上午10:57:16
	 */
	@JSON
	public String queryProv(){
		//获取当前用户设置的当前部门
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		if(org != null)
		{
			if(StringUtil.isNotEmpty(org.getProvCode()))
			{
				AdministrativeRegionsEntity entity = administrativeRegionsService.queryAdministrativeRegionsByCode(org.getProvCode());
				if(entity != null)
				{
					provName = entity.getName();
				}
			}
			if(StringUtil.isNotEmpty(org.getCityCode()))
			{
				AdministrativeRegionsEntity entity = administrativeRegionsService.queryAdministrativeRegionsByCode(org.getCityCode());
				if(entity != null)
				{
					cityName = entity.getName();
				}
			}
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * 检验传入的整车运单是否做配载和到达
	 *  @author 159231-foss-meiying
	 * @date 2014-5-6 下午7:30:51
	 */
	@JSON
	public String checkWayBillNosWVH() {
		try {
			deliverbillService.checkWayBillNosWVH(deliverbillVo.getWaybillNos());
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * Gets the deliverbill vo.
	 *
	 * @return the deliverbill vo
	 */
	public DeliverbillVo getDeliverbillVo() {
		return deliverbillVo;
	}

	/**
	 * Sets the deliverbill vo.
	 *
	 * @param deliverbillVo the new deliverbill vo
	 */
	public void setDeliverbillVo(DeliverbillVo deliverbillVo) {
		this.deliverbillVo = deliverbillVo;
	}

	/**
	 * Sets the deliverbill service.
	 *
	 * @param deliverbillService the new deliverbill service
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	/**
	 * Sets the vehicle service.
	 *
	 * @param vehicleService the new vehicle service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * Sets the configuration params service.
	 *
	 * @param configurationParamsService the new configuration params service
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * Sets the org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService the new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * Gets the fleet code.
	 *
	 * @return the fleet code
	 */
	public String getFleetCode() {
		return fleetCode;
	}

	/**
	 * Sets the fleet code.
	 *
	 * @param fleetCode the new fleet code
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * Gets the sequence.
	 *
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence.
	 *
	 * @param sequence the new sequence
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}