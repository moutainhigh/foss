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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/ExceptionProcessService.java
 * 
 * FILE NAME        	: NotifyCustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-17 	新增	熊勇	V0.1
2012-07-14	根据ITA审核进行修改	赵斌	V0.2
2012-10-12	修改新增派送异常界面（图三），修改异常环节数据字典	赵斌	V1.0
2012-11-19  	按照ISSUE-585进行用例修改  	 赵斌
V1.01


1.	SUC-488 –处理异常
1.1	相关业务用例
BUC_FOSS_5.50.10_560
1.2	用例描述
此用例为：《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》
 5用例的结合。
主要是营业员\调度在派送过程中对派送异常进行登记、异常信息的跟踪、通知出发部门及转弃货申请的操作过程。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	通知结果为“通知失败”
或
系统记录派送拉回货物	《DP-FOSS-接送货系统用例-客户自提-通知客户-通知客户》
《DP-FOSS-接送货系纺统用例-集中送货-交接送货-登记派送结果》
后置条件	如果处理后状态为“已处理”，系统允许进行预派送操作；	《DP-FOSS-接送货系纺统用例-集中送货-交接送货-创建预派送单》
1.4	操作用户角色
操作用户	描述
营业员、调度	新增、更新异常信息；在线通知出发部门；转弃货申请
1.5	界面要求
1.5.1	表现方式
Web页面 
1.5.3	界面描述
核心界面标题:处理派送异常
处理异常页面包括：处理派送异常（图1）、处理详情（图2）、新增派送异常（图3）\登记异常处理结果（图4）、在线通知（图5）、新增预弃货信息（图7）

详细界面描述请参见：
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》


需要提供的相关用例链接或提示信息：
1.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”图标，弹出“登记异常处理结果”界面（图4）。
2.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，弹出”异常处理详情”界面（图2）。
3.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，弹出”在线通知”界面（图5）。
4.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮,系统则弹出“新增预弃货信息”界面（图7）。
5.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮，系统则弹出“新增派送异常”界面（图3）。
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮。		系统则弹出“新增派送异常”界面（图3）。
2	用户录入新增异常信息，并提交。（详细录入步骤请参见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》）		系统提示新增成功，并关闭新增窗口。
3	用户录入查询条件点击查询。
（详细查询步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》）		系统在查询结果列表显示结果。
4	用户可以点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，来查看异常处理详情。		系统弹出”异常处理详情”界面（图2），并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）
5	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，来知通出发部门。
（详细通知步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）		系统则弹出“新增派送异常”界面（图3），并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）
6	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”图标,来进行登记异常处理结果。
(详细登记步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
		弹出“登记异常处理结果”界面（图4），并加载相关数据至界面。
（数据加载规则详见DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》）
7	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮,来进行新增预弃货信息操作。
（详细新增预弃货信息操作步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》）		系统则弹出“新增预弃货信息”界面（图7），并并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》）

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
			
			

1.7	业务规则
序号	描述
SR1	上报人为当前操作人员，异常原因，界面输入最多200字。
	
	
	
	

1.8	数据元素
1.8.1	登记派送异常
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	开单时录入的运单号	文本	请录入正确的运单号	8	是	全数字文本组成
异常类型		下拉列表框		20	是	包括通知、司机送货、其它异常
收货人		系统自动输入		20	是	全为数字组成
收货人电话		系统自动输入		20	是	收货人电话为收货人手机与固定电话。手机与固话的分隔符为“/”
						

1.8.2	异常记录列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常处理状态						
收货人						
收货人电话						包括手机与固定电话。中间用”/”分开
异常类型	登记异常时输入的异常类型					
收货部门	运单对应的收货部门					
登记人	登记异常时，系统记录的时间					
登记时间						
异常原因	导致货物未正常派送出库的原因					

1.8.3	查询条件
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
异常时间	登记异常时，系统记录的时间	时间选择域		20	否	默认的开始时间为前10天的00:00:00，结束时间为当天的23:00:00
异常类型	登记异常时输入的异常类型	下拉列表框		20	否	数据字典包括运单异常、货物异常及全部，默认为全部
运单号		文本		8	否	全为数字组成
异常状态		下拉列表框		5	否	数据字典包括全部、处理中、已处理、已转弃货
异常环节	派送异常出现的环节	下拉列表框		20	否	数据字典包括全部、送货通知、客户自提、司机送货、其它，默认为全部
库存时间	指货物到达到达部门的库存时间	时间选择域		20		默认为无

1.8.4	异常记录列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常处理状态						
收货人						
收货人电话						
异常类型	登记异常时输入的异常类型					
收货部门	运单对应的收货部门					
登记人	登记异常时，系统记录的时间					
登记时间						
异常原因	导致货物未正常派送出库的原因					

1.8.5	异常详情
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常类型	登记异常时输入的异常类型					
收货人	开单信息中的收货人					
收货人电话	开单信息中的收货人联系电话					包括手机与固定电话。中间用”/”分开
收货部门	开单时录入的收货部门					
登记时间	每次异常处理结果登记时系统的时间					
登记人	每次异常处理结果登记时系统的登记人					系统记录的是当前操作人
异常原因	异常处理的结果					

1.8.6	在线通知
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
通知接收部门	要通知到的部门	文本框		20	是	单号的写出发部门
通知内容	发送给出发部门的信息	文本域	请输入通知内容	200	是	
						
						

1.8.7	消息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
消息来自部门	发送消息的部门					
发送人	发送消息的用户姓名					
消息	通知时录入的通知内容					

1.8.8	登记派送异常处理结果
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	登常登记时的运单号	系统输入		8	是	
异常类型	导致派送异常的分类	下拉框		20	是	包括：通知异常、司机送货异常、其它异常；默认值为最后一次异常处理的异常类型。
收货人	运单号对应的收货人	系统输入		20	是	
收货人电话	运单号对应的手机号与固话	系统输入		20	是	手机号与固话中用“/”区分
收货部门	运单号对应的收货部门	系统输入		20	是	
上报人	上报异常的人	系统输入		20	是	默认为系统操作用户
处理结果	异常处理的结果	文本域		500	是	


1.8.9	异常处理历史
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
登记时间	每次异常处理时，系统记录的时间					
异常类型	每次异常处理时，录入的异常类型					
上报人	每次异常处理时，系统录入的上报人					
处理结果	每次异常处理时，用户录入的处理结果					

1.8.10	新增预弃货信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	异常信息登记时的运单号	系统输入	必须上传证明	20	是	
始发部门	登记异常时输入的异常类型	系统输入		20	是	
发货人手机	运单对应的发货人手机	系统输入		12	否	
体积	运单对应货物的体积	系统输入		5	是	
入库时间	货物到达到达部门的时间	系统输入		20	是	
仓储时长	入库时间与当前时间的相隔天数	系统输入		20	是	取整
操作人	预弃货信息的登记人	系统输入		20	是	默认为系统操作用户
弃货证明	弃货证明附件	文件上传		50	是	

1.9	非功能性需求
使用量	100W运单，预计20%的运单出现派送异常，每天登记20W票
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	3S
使用时间段	全天
高峰使用时间段	10:00-12:00   14:00-17:00


1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-17 	新增	熊勇	V0.1
2012-6-22	修正界面，新增货物流水号、异常环节	熊勇	V0.2
2012-7-14	按照ITA审核进行修改，加入处理部门	赵斌	V0.3
2012-11-19  	按照ISSUE-451进行修改  	赵斌  	V1.02 

1.	SUC-477 –登记异常处理结果
1.1	相关业务用例
BUC_FOSS_5.50.10_560
1.2	用例描述
营业员对登记的派送异常进行跟踪后，更新派送异常的处理情况的过程。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	异常的处理状态为处理中	
后置条件	如果处理后状态为“已处理”，系统允许进行预派送操作；	创建预派送单（SUC-447）
1.4	操作用户角色
操作用户	描述
营业员、调度	更新异常信息
1.5	界面要求
1.5.1	表现方式
Web页面 

1.5.3	界面描述
查询界面标题:登记异常处理结果
界面主要由异常信息登记区、异常处理历史列表区、功能按钮区三部分组成。
1.	异常信息登记区域：运单号、异常类型、收货人、收货人电话、收货部门、上报人、异常环节、货物流水号、处理结果。
a)	运单号：异常登记时的运单号。
b)	异常类型：包括运单异常、货物异常及全部；系统默认为全部。
c)	异常环节：包括通知异常、司机送货异常、客户自提及全部；
d)	收货人：单号对应的收货人名称。
e)	收货人电话：单号对应的手机号码、固定电话。
f)	收货部门：单号对应的的收货部门。
g)	上报人：异常上报人，系统默认为当前操作用户。
h)	处理结果：用来填写异常处理的详细结果

2.	异常历史列表区：异常历史
a)	异常历史：包括登记时间、异常类型、上报人、处理结果、处理部门。
3.	功能按钮区：更新、处理完毕、关闭
a)	更新：点击此按钮，新增一条处理记录，表示异常需继续跟踪，状态为“处理中”。
b)	处理完毕：点击此按钮，新增一条处理记录，并将处理状态标记为“已处理”。

需要提供的相关用例链接或提示信息：
1.	点击派送异常处理页面查询结果列表中操作列的“ ”按钮，弹出“登记异常处理结果”主界面。
2.	点击“登记异常处理结果”界面的“更新”按钮，系统新增一条处理记录，并跳转提示“操作成功”，3秒后自动关闭此页面。
3.	点击“登记异常处理结果”界面的“处理完毕”按钮，系统新增一条处理记录，并跳转提示“操作成功”，3秒后自动关闭此页面。
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	用户在派送处理界面的查询结果列表的操作列中点击“ ”按钮	登记异常处理结果信息	系统则弹出“登记异常处理结果”界面，并自动填充界面部门数据。（详见SR1）
2	用户录入异常类型、登记处理结果，然后点击“更新”或“处理完毕”按钮。（更新与处理完毕区别详见SR2）		a)	系统则自动新增一条异常处理记录（记录信息详见SR3），如果是“更新”，异常处理的状态为“处理中”；如果是“处理完毕”，异常处理的状态为“已处理”。
b)	，并跳转提示“操作成功”，3秒后自动关闭此页面。

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
	无		
			

1.7	业务规则
序号	描述
SR1	a)	运单号、异常类型、收货人、收货人电话、收货部门为最后次异常登记时对应的信息；上报人为当前操作用户，所有控件为不可编辑。
b)	异常历史为每次异常处理记录的处理记录。包括：处理时间、异常类型、上报人、处理结果。
SR2	点更新时，新增一条异常处理记录，但异常状态为“处理中”；点处理完毕时，新增一条异常处理记录，但异常状态为“已处理”。
SR3	处理记录包括：处理时间、、上报人、处理结果、运单号、、收货人、收货人电话、收货部门。
	
	

1.8	数据元素
1.8.1	登记派送异常处理结果
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	登常登记时的运单号	系统输入		8	是	
异常类型	登记的异常信息的类型	文本		10	是	包括运单异常、货物异常
收货人	运单号对应的收货人	系统输入		20	是	
收货人电话	运单号对应的手机号与固话	系统输入		20	是	手机号与固话中用“/”区分
收货部门	运单号对应的收货部门	系统输入		20	是	
上报人	上报异常的人	系统输入		20	是	默认为系统操作用户
处理结果	异常处理的结果	文本域		500	是	
异常环节	发现派送异常的环节	文本		20	是	出现异常的环节，包括送货通知、司机送货、客户自提
货物流水号	记录异常货物件的流水号	文本		20	否	 


1.8.2	异常处理历史
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
登记时间	每次异常处理时，系统记录的时间					
异常类型	每次异常处理时，录入的异常类型					
上报人	每次异常处理时，系统录入的上报人					
处理结果	每次异常处理时，用户录入的处理结果					
处理部门	每次异常处理时，用户所在部门组织					

1.9	非功能性需求
使用量	100W运单，预计20%的运单出现派送异常，每天登记20W票
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	3S
使用时间段	全天
高峰使用时间段	10:00-12:00   14:00-17:00


1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		
		
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-17 	新增	熊勇	V0.1
2012-07-14	根据ITA审核进行修改	赵斌	V0.2
 			
  	  	  	 

1.	SUC-488 –处理异常
1.1	相关业务用例
BUC_FOSS_5.50.10_560
1.2	用例描述
此用例为：《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》
 5用例的结合。
主要是营业员\调度在派送过程中对派送异常进行登记、异常信息的跟踪、通知出发部门及转弃货申请的操作过程。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	通知结果为“通知失败”
或
系统记录派送拉回货物	《DP-FOSS-接送货系统用例-客户自提-通知客户-通知客户》
《DP-FOSS-接送货系纺统用例-集中送货-交接送货-登记派送结果》
后置条件	如果处理后状态为“已处理”，系统允许进行预派送操作；	《DP-FOSS-接送货系纺统用例-集中送货-交接送货-创建预派送单》
1.4	操作用户角色
操作用户	描述
营业员、调度	新增、更新异常信息；在线通知出发部门；转弃货申请
1.5	界面要求
1.5.1	表现方式
Web页面 
1.5.2	界面原型
 
处理派送异常（图1）

 
异常处理详情界面（图2）

 
新增派送异常（图3）

 
登记异常处理结果（图4）
 
在线通知（图5）

 
消息提醒（图6）
 
新增预弃货信息（图7）
1.5.3	界面描述
核心界面标题:处理派送异常
处理异常页面包括：处理派送异常（图1）、处理详情（图2）、新增派送异常（图3）\登记异常处理结果（图4）、在线通知（图5）、新增预弃货信息（图7）

详细界面描述请参见：
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》
《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》


需要提供的相关用例链接或提示信息：
1.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”图标，弹出“登记异常处理结果”界面（图4）。
2.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，弹出”异常处理详情”界面（图2）。
3.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，弹出”在线通知”界面（图5）。
4.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮,系统则弹出“新增预弃货信息”界面（图7）。
5.	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮，系统则弹出“新增派送异常”界面（图3）。
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮。		系统则弹出“新增派送异常”界面（图3）。
2	用户录入新增异常信息，并提交。（详细录入步骤请参见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常信息》）		系统提示新增成功，并关闭新增窗口。
3	用户录入查询条件点击查询。
（详细查询步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-查询异常信息》）		系统在查询结果列表显示结果。
4	用户可以点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，来查看异常处理详情。		系统弹出”异常处理详情”界面（图2），并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）
5	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ” 图标，来知通出发部门。
（详细通知步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）		系统则弹出“新增派送异常”界面（图3），并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-通知出发部门》）
6	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”图标,来进行登记异常处理结果。
(详细登记步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》
		弹出“登记异常处理结果”界面（图4），并加载相关数据至界面。
（数据加载规则详见DP-FOSS-接送货系统用例-集中送货-处理派送异常-登记异常处理结果》）
7	用户点击处理派送异常（图1）页面中查询结果列表中操作列的“ ”按钮,来进行新增预弃货信息操作。
（详细新增预弃货信息操作步骤详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》）		系统则弹出“新增预弃货信息”界面（图7），并并加载相关数据至界面。
（数据加载规则详见《DP-FOSS-接送货系统用例-集中送货-处理派送异常-记录预弃货信息》）

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
			
			

1.7	业务规则
序号	描述
	
	
	
	
	

1.8	数据元素
1.8.1	登记派送异常
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	开单时录入的运单号	文本	请录入正确的运单号	8	是	全数字文本组成
异常类型		下拉列表框		20	是	包括通知、司机送货、其它异常
收货人		系统自动输入		20	是	全为数字组成
收货人电话		系统自动输入		20	是	收货人电话为收货人手机与固定电话。手机与固话的分隔符为“/”
						

1.8.2	异常记录列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常处理状态						
收货人						
收货人电话						包括手机与固定电话。中间用”/”分开
异常类型	登记异常时输入的异常类型					
收货部门	运单对应的收货部门					
登记人	登记异常时，系统记录的时间					
登记时间						
异常原因	导致货物未正常派送出库的原因					

1.8.3	查询条件
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
异常时间	登记异常时，系统记录的时间	时间选择域		20	否	默认的开始时间为前10天的00:00:00，结束时间为当天的23:00:00
异常类型	登记异常时输入的异常类型	下拉列表框		20	否	包括运单异常、货物异常及全部，默认为全部
运单号		文本		8	否	全为数字组成
异常状态		下拉列表框		5	否	包括全部、处理中、已处理、已转弃货
异常环节	派送异常出现的环节	下拉列表框		20	否	包括全部、送货通知、客户自提、司机送货，默认为全部
库存时间	指货物到达到达部门的库存时间	时间选择域		20		默认为无

1.8.4	异常记录列表
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常处理状态						
收货人						
收货人电话						
异常类型	登记异常时输入的异常类型					
收货部门	运单对应的收货部门					
登记人	登记异常时，系统记录的时间					
登记时间						
异常原因	导致货物未正常派送出库的原因					

1.8.5	异常详情
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号						
异常类型	登记异常时输入的异常类型					
收货人	开单信息中的收货人					
收货人电话	开单信息中的收货人联系电话					包括手机与固定电话。中间用”/”分开
收货部门	开单时录入的收货部门					
登记时间	每次异常处理结果登记时系统的时间					
登记人	每次异常处理结果登记时系统的登记人					系统记录的是当前操作人
异常原因	异常处理的结果					

1.8.6	在线通知
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
通知接收部门	要通知到的部门	文本框		20	是	单号的写出发部门
通知内容	发送给出发部门的信息	文本域	请输入通知内容	200	是	
						
						

1.8.7	消息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
消息来自部门	发送消息的部门					
发送人	发送消息的用户姓名					
消息	通知时录入的通知内容					

1.8.8	登记派送异常处理结果
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	登常登记时的运单号	系统输入		8	是	
异常类型	导致派送异常的分类	下拉框		20	是	包括：通知异常、司机送货异常、其它异常；默认值为最后一次异常处理的异常类型。
收货人	运单号对应的收货人	系统输入		20	是	
收货人电话	运单号对应的手机号与固话	系统输入		20	是	手机号与固话中用“/”区分
收货部门	运单号对应的收货部门	系统输入		20	是	
上报人	上报异常的人	系统输入		20	是	默认为系统操作用户
处理结果	异常处理的结果	文本域		500	是	


1.8.9	异常处理历史
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
登记时间	每次异常处理时，系统记录的时间					
异常类型	每次异常处理时，录入的异常类型					
上报人	每次异常处理时，系统录入的上报人					
处理结果	每次异常处理时，用户录入的处理结果					

1.8.1	新增预弃货信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号	异常信息登记时的运单号	系统输入	必须上传证明	20	是	
始发部门	登记异常时输入的异常类型	系统输入		20	是	
发货人手机	运单对应的发货人手机	系统输入		12	否	
体积	运单对应货物的体积	系统输入		5	是	
入库时间	货物到达到达部门的时间	系统输入		20	是	
仓储时长	入库时间与当前时间的相隔天数	系统输入		20	是	取整
操作人	预弃货信息的登记人	系统输入		20	是	默认为系统操作用户
弃货证明	弃货证明附件	文件上传		50	是	

1.9	非功能性需求
使用量	100W运单，预计20%的运单出现派送异常，每天登记20W票
2012年全网估计用户数	30000
响应要求（如果与全系统要求 不一致的话）	3S
使用时间段	全天
高峰使用时间段	10:00-12:00   14:00-17:00


1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
无		

 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionOperateDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SerialNoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 处理异常主数据Service层.
 *
 * @author 043258-
 * foss-zhaobin
 * @date 2012-10-31
 * 上午11:04:50
 * @since
 * @version
 */
public class ExceptionProcessService implements IExceptionProcessService {
	
	//日志属性
	private static final Logger LOGGER = LogManager.getLogger(ExceptionProcessService.class);
	
	/** 全部. */
	private static final String TOTAL = "ALL";
	
	/** 常量20000. */
	private static final int NUMBER = 20000;
	
	/** 获取处理异常dao. */
	private IExceptionProcessDao exceptionProcessDao;
	
	/** 运单管理服务. */
	private IWaybillManagerService waybillManagerService;
	
	/** 消息服务. */
	private IMessageService messageService;
	
	/** actualFreight Dao. */
	private IActualFreightDao actualFreightDao;
	
	/**
	 * 部门 DAO接口
	 */
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	/** 签收. */
	private IWaybillSignResultService waybillSignResultService;
	
	private ISignService signService;
	/**
	 *外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	
	private IExceptionProcessService exceptionProcessService;
	/** 行政区域服务 **/
	private IAdministrativeRegionsService administrativeRegionsService;
	private IArriveSheetManngerService arriveSheetManngerService;
	
	/** 
	 * @Fields notifyCustomerService : 通知客户
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/** 
	 * @Fields customerReceiptHabitService : 客户收货习惯
	 */
	private ICustomerReceiptHabitService customerReceiptHabitService;
	
	/** 
	 * @Fields beforeNoticeService : 提前通知
	 */
	private IBeforeNoticeService beforeNoticeService;
	
	private IBillReceivableService billReceivableService;
	
	/** 
	 * @Fields stockService : 库存的各种操作 
	 */
	private IStockService stockService;
	
	public ICustomerReceiptHabitService getCustomerReceiptHabitService() {
		return customerReceiptHabitService;
	}

	public void setCustomerReceiptHabitService(
			ICustomerReceiptHabitService customerReceiptHabitService) {
		this.customerReceiptHabitService = customerReceiptHabitService;
	}

	public INotifyCustomerService getNotifyCustomerService() {
		return notifyCustomerService;
	}

	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	public void setSignService(ISignService signService) {
		this.signService = signService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			actual freight dao.
	 *
	 * @param actualFreightDao 
	 * 		
	 * 		the 
	 * 			new actual freight dao
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			exception process dao.
	 *
	 * @param exceptionProcessDao 
	 * 	
	 * 		the 
	 * 			new exception process dao
	 */
	public void setExceptionProcessDao(IExceptionProcessDao exceptionProcessDao) {
		this.exceptionProcessDao = exceptionProcessDao;
	}
	

	/**
	 * Sets 
	 * 		the 
	 * 			waybill manager service.
	 *
	 * @param waybillManagerService 
	 * 	
	 * 		the 
	 * 			new waybill manager service
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			message service.
	 *
	 * @param messageService 
	 * 		
	 * 		the 
	 * 			new message service
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 按照查询条件查询异常信息.
	 *
	 * @param 
	 * 		dto.storageDayBegin
	 * 		库存天数(起)
	 * 		dto.storageDayEnd
	 * 		库存天数(止)
	 * 		dto.exceptionTimeBegin
	 * 		异常时间(起)
	 * 		dto.exceptionTimeEnd
	 * 		异常时间(止)
	 * 		dto.exceptionType
	 * 		异常类型
	 * 		dto.exceptionLink
	 * 		异常环节
	 * 		dto.status
	 * 		状态
	 * 		dto.waybillNo
	 * 		运单号
	 * 		dto.tSrvExceptionId
	 * 		异常_ID
	 * 		dto.notes
	 * 		异常处理结果
	 * 		dto.serialNo
	 * 		流水号
	 * 		dto.departmentCode
	 * 		当前登录部门
	 * 		dto.active
	 * 		运单状态	
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return the list
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-10-31 
	 * 		上午11:04:50
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#queryExceptionProcessInfo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto, int, int)
	 */
	@Override
	public List<ExceptionProcessDto> queryExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto, int start, int limit)
	{
		//如果传入dto不为空
		if(exceptionProcessConditionDto != null)
		{
			//如果异常环节为全部
			if(exceptionProcessConditionDto.getExceptionLink().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setExceptionLink("");
			}
			//如果异常类型为全部
			if(exceptionProcessConditionDto.getExceptionType().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setExceptionType("");
			}
			//如果异常状态为全部
			if(exceptionProcessConditionDto.getStatus().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setStatus("");
			}
			
			//如果异常操作为全部
			if (exceptionProcessConditionDto.getExceptionOperate().equals(TOTAL)) {
				//传入空
				exceptionProcessConditionDto.setExceptionOperate("");
			}
			
			//设置异常信息生效
			exceptionProcessConditionDto.setActive(FossConstants.ACTIVE);
			//获取当前登录用户
			UserContextHelper userContextHelper = new UserContextHelper();
			//传入当前用户所在部门
			exceptionProcessConditionDto.setDepartmentCode(userContextHelper.getOrgCode());
			
			//add by foss-sunyanfei 2015-0826
			if (StringUtil.isNotBlank(exceptionProcessConditionDto.getWaybillNo())) {
				// 解析运单号为列表
				List<String> waybillNos = getList(exceptionProcessConditionDto.getWaybillNo().split("\\n"));
				String[] arrayWaybills = waybillNos.toArray(new String[waybillNos.size()]);
				
				exceptionProcessConditionDto.setArrayWaybillNos(arrayWaybills);
			}
			//add by foss-sunyanfei 2015-0826
			
			//根据传入条件返回异常信息
			return exceptionProcessDao.queryExceptionProcessInfo(exceptionProcessConditionDto, start, limit);
		}
		//返回空
		return null;
	}
	
	/**
	 * 总条数查询.
	 *
	 * @param 
	 * 		dto.storageDayBegin
	 * 		库存天数(起)
	 * 		dto.storageDayEnd
	 * 		库存天数(止)
	 * 		dto.exceptionTimeBegin
	 * 		异常时间(起)
	 * 		dto.exceptionTimeEnd
	 * 		异常时间(止)
	 * 		dto.exceptionType
	 * 		异常类型
	 * 		dto.exceptionLink
	 * 		异常环节
	 * 		dto.status
	 * 		状态
	 * 		dto.waybillNo
	 * 		运单号
	 * 		dto.tSrvExceptionId
	 * 		异常_ID
	 * 		dto.notes
	 * 		异常处理结果
	 * 		dto.serialNo
	 * 		流水号
	 * 		dto.departmentCode
	 * 		当前登录部门
	 * 		dto.active
	 * 		运单状态	
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return the list
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-10-31 
	 * 		上午11:04:50
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#queryExceptionProcessInfo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto, int, int)
	 */
	@Override
	public Long queryExceptionProcessInfoCount(ExceptionProcessConditionDto exceptionProcessConditionDto) 
	{
		//如果传入dto不为空
		if(exceptionProcessConditionDto!= null)
		{
			Long count1 = Long.valueOf(0);
			Long count2 = Long.valueOf(0);
			//如果异常环节为全部
			if(exceptionProcessConditionDto.getExceptionLink().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setExceptionLink("");
			}
			//如果异常类型为全部
			if(exceptionProcessConditionDto.getExceptionType().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setExceptionType("");
			}
			//如果异常状态为全部
			if(exceptionProcessConditionDto.getStatus().equals(TOTAL))
			{
				//传入空
				exceptionProcessConditionDto.setStatus("");
			}
			//如果异常操作为全部
			if (exceptionProcessConditionDto.getExceptionOperate().equals(TOTAL)) {
				//传入空
				exceptionProcessConditionDto.setExceptionOperate("");
			}
			
			//设置异常信息生效
			exceptionProcessConditionDto.setActive(FossConstants.ACTIVE);
			
			//获取当前登录用户
			UserContextHelper userContextHelper = new UserContextHelper();
			
			//传入当前用户所在部门
			exceptionProcessConditionDto.setDepartmentCode(userContextHelper.getOrgCode());
			
			//add by foss-sunyanfei 2015-0826
			if (StringUtil.isNotBlank(exceptionProcessConditionDto.getWaybillNo())) {
				// 解析运单号为列表
				exceptionProcessConditionDto.setArrayWaybillNos(exceptionProcessConditionDto.getWaybillNo().split("\\n"));
			}
			//add by foss-sunyanfei 2015-0826
			
			//返回收货部门查询总条数	
			count1 = exceptionProcessDao.queryExceptionProcessInfoCount(exceptionProcessConditionDto);
			
			//返回最终配载部门查询总条数
			count2 = exceptionProcessDao.queryExceptionProcessInfoCountLast(exceptionProcessConditionDto);
			
			return count1+count2;
		}
		//返回0
		return Long.valueOf(0);
	}

	/**
	 * 新增异常信息.
	 *
	 * @param 
	 * exceptionEntity 
	 * 		waybillNo 
	 * 运单号
	 * 		exceptionType 
	 * 异常类型
	 * 		exceptionLink 
	 * 异常环节
	 * 		status 
	 * 状态
	 * 		serialNo  
	 * 流水号
	 * 		exceptionTime 
	 * 异常生成时间
	 * 		createUserName 
	 * 登记人
	 * 		createUserCode 
	 * 登记人编码
	 * 		createOrgName 
	 * 登记部门
	 * 		createOrgCode 
	 * 登记部门编码
	 * @author 043258-
	 * 		foss-
	 * zhaobin
	 * @date 2012-10-31 
	 * 		上午11:04:50
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#addExceptionProcessInfo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
	 */
	@Override
	public void addExceptionProcessInfo(ExceptionEntity exceptionEntity) 
	{
		//如果传入exceptionEntity不为空
		if(exceptionEntity != null)
		{
			//校验运单是否存在
			boolean exist = waybillManagerService.isWayBillExsits(exceptionEntity.getWaybillNo());
			//如果存在
			if(exist)
			{	
				String waybillNo = exceptionEntity.getWaybillNo();
				String serialNo = exceptionEntity.getSerialNo();
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if (waybillEntity!=null) {
					if (StringUtils.isNotBlank(waybillEntity.getReceiveCustomerDistCode())) {
						//通过运单区域code获取对应区域名字
						String countyName = handleQueryOutfieldService.getCompleteAddress(null, null, waybillEntity.getReceiveCustomerDistCode(), "");
						exceptionEntity.setCountyName(countyName);
						exceptionEntity.setCountyCode(waybillEntity.getReceiveCustomerDistCode());
					}
				}
				
				if(StringUtils.isNotEmpty(serialNo))
				{
					int i = exceptionProcessDao.queryHandlingInfo(waybillNo, serialNo);
					if(i>0)
					{
						//流水号存在处理中异常
						throw new ExceptionProcessException(ExceptionProcessException.SERIALNO_IS_HANDLING);
					}else
					{
						//新增异常信息
						exceptionProcessDao.addExceptionProcessInfo(exceptionEntity);
					}
				}else
				{
					//新增异常信息
					exceptionProcessDao.addExceptionProcessInfo(exceptionEntity);
				}
				
			}
		}
	}
	
	/**
	 * 根据异常ID查看异常详情.
	 *
	 * @param exceptionProcessid 
	 * 		the 
	 * exception processid
	 * @return 
	 * 		the 
	 * exception process detail dto
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-1 
	 * 		上午11:25:45
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#queryExceptionProcessDetailInfo
	 * (java.lang.String)
	 */
	@Override
	public ExceptionProcessDetailDto queryExceptionProcessDetailDto(String exceptionProcessid) 
	{
		//如果exceptionProcessid不为空
		if(StringUtils.isNotEmpty(exceptionProcessid))
		{
			//处理异常DTO
			ExceptionProcessConditionDto exceptionProcessConditionDto = new ExceptionProcessConditionDto();
			//传入异常id
			exceptionProcessConditionDto.setTSrvExceptionId(exceptionProcessid);
			//查询状态为生效
			exceptionProcessConditionDto.setActive(FossConstants.ACTIVE);
			//返回异常信息
			ExceptionProcessDetailDto dto = exceptionProcessDao.queryExceptionProcessDetailDto(exceptionProcessConditionDto);
			
			NotifyCustomerConditionDto conditionDto = new NotifyCustomerConditionDto();
			conditionDto.setWaybillNo(dto.getWaybillNo());
			//获取当前登录用户
			UserContextHelper userContextHelper = new UserContextHelper();
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(userContextHelper.getOrgCode());
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ld=new ArrayList<String>();
				ld.add(list.get(1));
				ld.add(list.get(2));
				conditionDto.setEndStockOrgCode(list.get(0));
				conditionDto.setGoodsAreaCodes(ld);
			}
			// 查询运单信息
			NotifyCustomerDto notifyCustomerDto = beforeNoticeService.queryBeforeNoticeDetailByWaybillNo(conditionDto);
			if (notifyCustomerDto == null) {
				throw new ExceptionProcessException("查询运单信息失败出现异常");
			}
			//获取最后处理异常信息
			ExceptionProcessDetailEntity exceptionProcessDetail = exceptionProcessDao.selectSingleExceptionProcessDetailOfEnd(exceptionProcessid);
		
			//取最新一条通知记录的实际收货地址，如果通知记录没有则读取开单地址
			NotificationEntity notificationEntity = new NotificationEntity();
			notificationEntity.setWaybillNo(dto.getWaybillNo());
			// 查询最后一次通知记录
			notificationEntity = notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
			String actualProvN = ""; // 省名称
			String actualCityN = ""; // 市名称
			String actualDistN = ""; // 区名称
			if (notificationEntity != null) { // 如果有通知记录  获取最后一次读取记录的信息
				dto.setPaymentType(notifyCustomerDto.getPaidMethodVir());
				dto.setToPayAmount(notifyCustomerDto.getToPayAmount());
				
				if (StringUtils.isNotBlank(notificationEntity.getActualProvCode()) && 
						StringUtils.isNotBlank(notificationEntity.getActualCityCode()) &&
						StringUtils.isNotBlank(notificationEntity.getActualDistrictCode())) { // 通知实际收货地址为空
					dto.setActualProvCode(notificationEntity.getActualProvCode());
					dto.setActualCityCode(notificationEntity.getActualCityCode());
					dto.setActualDistrictCode(notificationEntity.getActualDistrictCode());
					actualProvN = handleQueryOutfieldService.getCompleteAddress(notificationEntity.getActualProvCode(), null, null, "");
					dto.setActualProvN(actualProvN);
					actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notificationEntity.getActualCityCode(), null, "");
					dto.setActualCityN(actualCityN);
					actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null,notificationEntity.getActualDistrictCode(), "");
					dto.setActualDistrictN(actualDistN);
					dto.setActualAddressDetail(notificationEntity.getActualAddressDetail());
					dto.setActualStreetn(notificationEntity.getActualStreetn());
					
					// 查询开单的省、市、区名称
					actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
					actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
					actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null,notifyCustomerDto.getReceiveCustomerDistCode(), "");	
				} else {
					dto.setActualProvCode(notifyCustomerDto.getReceiveCustomerProvCode());
					dto.setActualCityCode(notifyCustomerDto.getReceiveCustomerCityCode());
					dto.setActualDistrictCode(notifyCustomerDto.getReceiveCustomerDistCode());
					actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
					dto.setActualProvN(actualProvN);
					actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
					dto.setActualCityN(actualCityN);
					actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null,notifyCustomerDto.getReceiveCustomerDistCode(), "");
					dto.setActualDistrictN(actualDistN);
					dto.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
					dto.setActualAddressDetail(notifyCustomerDto.getReceiveCustomerAddress());
				}
				dto.setIsExhibition(notificationEntity.getIsExhibition() == null ? "N" : notificationEntity.getIsExhibition());
				dto.setIsEmptyCar(notificationEntity.getIsEmptyCar() == null ? "N" : notificationEntity.getIsEmptyCar());
				dto.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
				dto.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
				dto.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
				dto.setInvoiceType(notificationEntity.getInvoiceType());
				dto.setInvoiceDetail(notificationEntity.getInvoiceDetail());
				dto.setIsSentRequired(notificationEntity.getIsSentRequired());
				String notes = "";
				if (StringUtils.isNotBlank(notificationEntity.getDeliverRequire())) { //判断送货要求是否为空
					notes = notificationEntity.getDeliverRequire();
				} else if (StringUtils.isNotBlank(notificationEntity.getNoticeContent())) { // 判断通知内容是否为空
					notes = notificationEntity.getNoticeContent();
				}
				dto.setNotes(notes);
			} else { //如果没有通知记录 读取运单的 和 收货习惯的
				dto.setPaymentType(notifyCustomerDto.getPaidMethodVir());
				dto.setToPayAmount(notifyCustomerDto.getToPayAmount());
				dto.setActualProvCode(notifyCustomerDto.getReceiveCustomerProvCode());
				dto.setActualCityCode(notifyCustomerDto.getReceiveCustomerCityCode());
				dto.setActualDistrictCode(notifyCustomerDto.getReceiveCustomerDistCode());
				actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
				dto.setActualProvN(actualProvN);
				actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
				dto.setActualCityN(actualCityN);
				actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null,notifyCustomerDto.getReceiveCustomerDistCode(), "");
				dto.setActualDistrictN(actualDistN);
				dto.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
				dto.setActualAddressDetail(notifyCustomerDto.getReceiveCustomerAddress());
				dto.setIsExhibition(notifyCustomerDto.getIsExhibitCargo() == null ? "N" : notifyCustomerDto.getIsExhibitCargo());
				CustomerReceiptHabitEntity customerReceiptHabit = new CustomerReceiptHabitEntity();
				customerReceiptHabit.setCustomerCode(notifyCustomerDto.getReceiveCustomerCode());
				customerReceiptHabit.setCustomerName(notifyCustomerDto.getReceiveCustomerName());
				customerReceiptHabit.setCustomerContactName(notifyCustomerDto.getReceiveCustomerContact());
				customerReceiptHabit.setCustomerMobilePhone(notifyCustomerDto.getReceiveCustomerMobilephone());
				customerReceiptHabit.setCustomerPhone(notifyCustomerDto.getReceiveCustomerPhone());
				customerReceiptHabit = customerReceiptHabitService.selectReceiptHabitByParam(customerReceiptHabit);
				if (customerReceiptHabit != null) {
					dto.setDeliveryTimeInterval(customerReceiptHabit.getDeliveryTimeInterval());
					dto.setDeliveryTimeStart(customerReceiptHabit.getDeliveryTimeStart());
					dto.setDeliveryTimeOver(customerReceiptHabit.getDeliveryTimeOver());
					dto.setInvoiceType(customerReceiptHabit.getInvoiceType());
					dto.setInvoiceDetail(customerReceiptHabit.getInvoiceDetail());
				}
				if (exceptionProcessDetail != null) {
					dto.setNotes(exceptionProcessDetail.getNotes());
				}
			}
			
			//判断开单为到付, 是否已经网上支付-239284
			String payType = notifyCustomerDto.getPaidMethodVir();
			if (StringUtils.isNotBlank(payType) 
					&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(payType)) {
				String[] billTypes = new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE};
				String wayBillNo = notifyCustomerDto.getWaybillNo();
				String actualType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE; //SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE;
				int count = billReceivableService.queryIsOrPayByBillNo(billTypes, wayBillNo, null, null, actualType);
				if (count > 0) {
					notifyCustomerDto.setIsOrPayStatus("网上支付（已付）");
				}
			}
			// 客户收收货部门
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoDao.queryOrgAdministrativeInfoByCode(dto.getReceiveOrgCode());
			if (orgAdministrativeInfo!=null) {
				//设置收货部门名称
				dto.setReceiveOrgName(orgAdministrativeInfo.getName());
				//设置收货部门电话
				dto.setReceiveOrgTel(orgAdministrativeInfo.getDepTelephone());
				// 设置运单信息的始发部门名称
				notifyCustomerDto.setReceiveOrgName(orgAdministrativeInfo.getName());
			}	
			//获得预计送货时间
			Date deliverDate=exceptionProcessDao.queryActualFreightInfo(dto.getWaybillNo());
			//设置预计送货时间
			dto.setDeliverDate(deliverDate);
			String address = actualProvN + actualCityN + actualDistN + notifyCustomerDto.getReceiveCustomerAddress();
			notifyCustomerDto.setReceiveCustomerAddress(address);
			
			// 判断是否在库存中
			if (notifyCustomerDto.getStockQty() != null && notifyCustomerDto.getStockQty() > 0) {
				notifyCustomerDto.setStockStatus("库存中");
			} else {
				notifyCustomerDto.setStockQty(0);
				notifyCustomerDto.setStockStatus("未入库");
			}
			
			if (notifyCustomerDto.getStorageDay() == null) {
				notifyCustomerDto.setStorageDay(0);
			}
			
			if (notifyCustomerDto.getStorageCharge() == null) {
				notifyCustomerDto.setStorageCharge(BigDecimal.ZERO);
			}
			
			dto.setNotifyCustomerDto(notifyCustomerDto);
			
			//返回此dto
			return dto;
		}
		//返回空
		return null ;
	}
	
	/**
	 * 更新异常信息.
	 *
	 * @param 
	 * exceptionEntity 
	 *   	waybillNo 
	 *   运单号
	 * 		exceptionType 
	 * 异常类型
	 * 		exceptionLink 
	 * 异常环节
	 * 		status 
	 * 状态
	 * 		serialNo  
	 * 流水号
	 * 		exceptionTime 
	 * 异常生成时间
	 * 		createUserName 
	 * 登记人
	 * 		createUserCode 
	 * 登记人编码
	 * 		createOrgName 
	 * 登记部门
	 * 		createOrgCode 
	 * 登记部门编码
	 * @return 
	 * true, 
	 * 		if successful
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-1 
	 * 		下午6:31:33
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#updateExceptionProcessInfo
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateExceptionProcessInfo(ExceptionEntity exceptionEntity)
	{
		//如果exceptionEntity不为空
		if(exceptionEntity != null)
		{
			//更新异常信息
			return exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
		}
		//返回错误
		return false;
	}
	
	/**
	 * 1、新增异常处理记录
	 * 2、跟新异常状态为处理中.
	 *
	 * @param 
	 * tSrvExceptionId 
	 * 		the 
	 * t srv exception id 异常id
	 * @param notes 
	 * 		the 
	 * notes	备注
	 * @author 043258-
	 * 		foss-
	 * zhaobin
	 * @date 2012-11-2 
	 * 		下午2:47:23
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#addExceptionProcessDetail
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity)
	 */
	@Override
	public void addExceptionProcessDetail(String tSrvExceptionId , String notes , String waybillNo , Date deliverDate ) 
	{
		if (StringUtils.isBlank(tSrvExceptionId)) {
			//流水号 不能为空
			LOGGER.info("流水号 不能为空");
			throw new ExceptionProcessException("流水号 不能为空,请等数据加载完毕再操作。");
		}
		
		//当前异常业务 还未完结
		if(!exceptionProcessDao.isOperate(tSrvExceptionId))
		{
			//如果异常id、异常备注不为空
			if(StringUtils.isNotEmpty(tSrvExceptionId) && StringUtils.isNotEmpty(notes))	
			{
				//获得用户信息
				UserContextHelper userContextHelper = new UserContextHelper();
				//异常实体
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				//新增异常处理信息
				ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
				//异常id
				exceptionEntity.setId(tSrvExceptionId);
				//处理中
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
				// 备注
				exceptionEntity.setExceptionReason(notes);
				//新增异常处理记录
				exceptionProcessDetailEntity.settSrvExceptionId(tSrvExceptionId);
				//备注
				exceptionProcessDetailEntity.setNotes(notes);
				//用户名
				exceptionProcessDetailEntity.setOperator(userContextHelper.getUserName());
				//用户code
				exceptionProcessDetailEntity.setOperatorCode(userContextHelper.getUserCode());
				//组织名称
				exceptionProcessDetailEntity.setOperateOrgName(userContextHelper.getOrgName());
				//组织code
				exceptionProcessDetailEntity.setOperateOrgCode(userContextHelper.getOrgCode());
				//将当前时间赋值给操作时间
				exceptionProcessDetailEntity.setOperateTime(new Date());
				
				//新增异常处理记录
				exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
				//更新异常状态为处理中
				exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
				if (deliverDate!=null) {
					//更新预计送货日期
					exceptionProcessDao.updateActualFreightInfo(waybillNo , deliverDate);
				}
				
			}
		}
		else
		{
			//流水号不存在 抛出业务异常
			throw new ExceptionProcessException(ExceptionProcessException.EXCEPTION_DISPOSED);
		}
	}
	
	/**
	 * 1、新增异常处理记录
	 * 2、跟新异常状态为已处理.
	 *
	 * @param 
	 * tSrvExceptionId 
	 * 		the 
	 * t srv exception id
	 * @param 
	 * 		notes 
	 * 		the 
	 * 		notes
	 * @param 
	 * 		exceptionType 
	 * 		the 
	 * 		exception type
	 * @param 
	 *		exceptionLink 
	 * 		the 
	 * 		exception link
	 * @param 
	 * 		waybillNo 
	 * 		the 
	 * 		waybill no
	 * @author 
	 * 		043258-
	 * 		foss-
	 * 		zhaobin
	 * @date 2012-11-3 
	 * 		上午8:20:35
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#operateExceptionProcessDetail
	 * (java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public void operateExceptionProcessDetail(String tSrvExceptionId,String notes,String exceptionType, String exceptionLink, String waybillNo,String arrivesheetId , Date deliverDate)
	{
		if (StringUtils.isBlank(tSrvExceptionId)) {
			//流水号 不能为空
			LOGGER.info("流水号 不能为空");
			throw new ExceptionProcessException("流水号 不能为空,请等数据加载完毕再操作。");
		}
		
		//当前异常业务 还未完结
		if(!exceptionProcessDao.isOperate(tSrvExceptionId))
		{
			//如果异常id、异常备注不为空
			if(StringUtils.isNotEmpty(tSrvExceptionId) && StringUtils.isNotEmpty(notes))	
			{
				//获得用户信息
				UserContextHelper userContextHelper = new UserContextHelper();
				//异常实体
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				//新增异常处理信息
				ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
				//新增异常信息
				exceptionEntity.setId(tSrvExceptionId);
				//处理完成
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
				// 备注
				exceptionEntity.setExceptionReason(notes);
				//新增异常处理记录
				exceptionProcessDetailEntity.settSrvExceptionId(tSrvExceptionId);
				//备注
				exceptionProcessDetailEntity.setNotes(notes);
				//用户名
				exceptionProcessDetailEntity.setOperator(userContextHelper.getUserName());
				//用户code
				exceptionProcessDetailEntity.setOperatorCode(userContextHelper.getUserCode());
				//组织名称
				exceptionProcessDetailEntity.setOperateOrgName(userContextHelper.getOrgName());
				//组织code
				exceptionProcessDetailEntity.setOperateOrgCode(userContextHelper.getOrgCode());
				//将当前时间赋值给操作时间
				exceptionProcessDetailEntity.setOperateTime(new Date());
				
				//新增异常处理记录
				exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
				//跟新异常状态为已处理
				exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
				if (deliverDate!=null) {
					//更新预计送货日期
					exceptionProcessDao.updateActualFreightInfo(waybillNo , deliverDate);
				}
						
				//货物异常时，若异常环节为客户自提
				if(ExceptionProcessConstants.LABELEDGOOD_EXCEPTION.equals(exceptionType) && ExceptionProcessConstants.CUSTOMER_PICKUP.equals(exceptionLink))
				{
					ActualFreightEntity ac =actualFreightDao.queryByWaybillNo(waybillNo);
					if(ac != null){
						int generateGoodsQty=ac.getGenerateGoodsQty();//生成件数
						ArriveSheetDto dto = new ArriveSheetDto();
						dto.setWaybillNo(waybillNo);
						List<String> statusList = new ArrayList<String>();
						statusList.add(ArriveSheetConstants.STATUS_GENERATE);
						statusList.add(ArriveSheetConstants.STATUS_DELIVER);
						dto.setArriveSheetStatus(statusList);
						dto.setActive(FossConstants.YES);
						dto.setDestroyed(FossConstants.NO);
						List<ArriveSheetEntity> returnArriveSheet =	arriveSheetManngerService.queryArriveSheetByLifeCycle(dto);
						if(CollectionUtils.isNotEmpty(returnArriveSheet)){
							for (ArriveSheetEntity arriveSheetEntity : returnArriveSheet) {
								generateGoodsQty=generateGoodsQty-arriveSheetEntity.getArriveSheetGoodsQty();
							}
							if(generateGoodsQty<=0){
								generateGoodsQty=0;
							}
						}
						int result = 0;
						if(generateGoodsQty>(ac.getArriveGoodsQty()-ac.getArriveNotoutGoodsQty())){
							//实际货物实体
							ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
							//运单号
							actualFreightEntity.setWaybillNo(waybillNo);
							//数字一
							actualFreightEntity.setGenerateGoodsQty(NumberConstants.ONE);
							//排单件数-1
							actualFreightEntity.setArrangeGoodsQty(NumberConstants.ONE);
							result =actualFreightDao.updateSubGenerateGoodsQtyByWaybillNo(actualFreightEntity);
						}
						//更新到达件数
						if(result<=0){
							// 到达未出库件数
							Integer arriveNotoutGoodsQty = 0;
							if(Integer.valueOf("0").equals(ac.getArriveNotoutGoodsQty())){
									WaybillSignResultEntity sr = new WaybillSignResultEntity(waybillNo,FossConstants.ACTIVE);
									WaybillSignResultEntity srResult =waybillSignResultService.queryWaybillSignResultByWaybillNo(sr);
									if(srResult != null){
										if(ac.getArriveGoodsQty()>srResult.getSignGoodsQty()){
												arriveNotoutGoodsQty =ac.getArriveGoodsQty()-srResult.getSignGoodsQty();
												if(generateGoodsQty>(ac.getArriveGoodsQty()-arriveNotoutGoodsQty)){
													//实际货物实体
													ActualFreightEntity actualFreight= new ActualFreightEntity();
													actualFreight.setWaybillNo(waybillNo);
													actualFreight.setArriveNotoutGoodsQty(arriveNotoutGoodsQty);
													actualFreight.setGenerateGoodsQty(NumberConstants.ONE);
													//排单件数-1
													actualFreight.setArrangeGoodsQty(NumberConstants.ONE);
													actualFreightDao.updateActualFreightPartByWaybillNo(actualFreight);
												}
										}
								}
							}
						}
					}
				}
			}
		}
		else
		{
			//流水号不存在 抛出业务异常
			throw new ExceptionProcessException(ExceptionProcessException.EXCEPTION_DISPOSED);
		}
	}
	
	/**
	 * 新增异常信息.
	 *
	 * @param 
	 * exceptionType 
	 * 		the 
	 * 			exception type
	 * @param 
	 * exceptionLink 
	 * 		the 
	 * 			exception link
	 * @param 
	 * waybillNo 
	 * 		the 
	 * 			waybill no
	 * @param 
	 * serialNo 
	 * 		the 
	 * 			serial no
	 * @throws 
	 * ExceptionProcessException 
	 * 		the 
	 * 			exception process exception
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-3
	 * 		 下午3:54:53
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#addExceptionProcessInfo
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto) throws ExceptionProcessException
	{
		// 判断是否区县code为空
		if(StringUtils.isNotEmpty(exceptionProcessConditionDto.getCountyCode())){
			String name = administrativeRegionsService.queryAdministrativeRegionsNameByCode(exceptionProcessConditionDto.getCountyCode());
			exceptionProcessConditionDto.setCountyName(name);
		}
		//如果异常类型、环节、运单号不为空
		if(StringUtils.isNotEmpty(exceptionProcessConditionDto.getExceptionType()) && StringUtils.isNotEmpty(exceptionProcessConditionDto.getExceptionLink()) && StringUtils.isNotEmpty(exceptionProcessConditionDto.getWaybillNo()))
		{
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(exceptionProcessConditionDto.getWaybillNo());
			if(waybillEntity != null){
				String lastLoadOrgCode = waybillEntity.getLastLoadOrgCode()==null?"":waybillEntity.getLastLoadOrgCode();
				if(!lastLoadOrgCode.equals(FossUserContextHelper.getOrgCode())){
					//部门不匹配 抛出业务异常
					throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_NOT_MATCH);
				}else
				{
					WaybillSignResultEntity entity = new WaybillSignResultEntity(); 
					entity.setWaybillNo(exceptionProcessConditionDto.getWaybillNo());
					entity.setActive(FossConstants.ACTIVE);
					WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
					//判断运单签收情况是否是异常签收，签收状态是否为全部签
					if(waybillSignResultEntity != null && SignConstants.NORMAL_SIGN.equals(waybillSignResultEntity.getSignSituation()) && SignConstants.SIGN_STATUS_ALL.equals(waybillSignResultEntity.getSignStatus()))
					{
						//已全部签收 抛出业务异常
						throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_SIGNED);
					}else
					{
						//如果流水号不为空
						if(StringUtils.isNotEmpty(exceptionProcessConditionDto.getSerialNo()))
						{
							String[] serialNos = exceptionProcessConditionDto.getSerialNo().split(",");
							for(String serial : serialNos){
								//异常实体
								ExceptionEntity exceptionEntity = new ExceptionEntity();
								//用户信息
								UserContextHelper userContextHelper = new UserContextHelper();
								//异常类型
								exceptionEntity.setExceptionType(exceptionProcessConditionDto.getExceptionType());
								//异常环节
								exceptionEntity.setExceptionLink(exceptionProcessConditionDto.getExceptionLink());
								//异常操作
								exceptionEntity.setExceptiOperate(exceptionProcessConditionDto.getExceptionOperate());
								//通知内容
								exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());
								//运单号
								exceptionEntity.setWaybillNo(exceptionProcessConditionDto.getWaybillNo());
								//流水号
								exceptionEntity.setSerialNo(serial);
								// 区县编码
								exceptionEntity.setCountyCode(exceptionProcessConditionDto.getCountyCode());
								// 区县名称
								exceptionEntity.setCountyName(exceptionProcessConditionDto.getCountyName());
								//处理状态
								exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
								//用户名
								exceptionEntity.setCreateUserName(userContextHelper.getUserName());
								//用户code
								exceptionEntity.setCreateUserCode(userContextHelper.getUserCode());
								//组织名称
								exceptionEntity.setCreateOrgName(userContextHelper.getOrgName());
								//组织code
								exceptionEntity.setCreateOrgCode(userContextHelper.getOrgCode());
								// 添加异常
								exceptionProcessService.addException(exceptionEntity);
							}
						}else{
							//异常实体
							ExceptionEntity exceptionEntity = new ExceptionEntity();
							//用户信息
							UserContextHelper userContextHelper = new UserContextHelper();
							//异常类型
							exceptionEntity.setExceptionType(exceptionProcessConditionDto.getExceptionType());
							//异常环节
							exceptionEntity.setExceptionLink(exceptionProcessConditionDto.getExceptionLink());
							//异常操作
							exceptionEntity.setExceptiOperate(exceptionProcessConditionDto.getExceptionOperate());
							//通知内容
							exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());
							//运单号
							exceptionEntity.setWaybillNo(exceptionProcessConditionDto.getWaybillNo());
							// 区县编码
							exceptionEntity.setCountyCode(exceptionProcessConditionDto.getCountyCode());
							// 区县名称
							exceptionEntity.setCountyName(exceptionProcessConditionDto.getCountyName());
							//处理状态
							exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
							//用户名
							exceptionEntity.setCreateUserName(userContextHelper.getUserName());
							//用户code
							exceptionEntity.setCreateUserCode(userContextHelper.getUserCode());
							//组织名称
							exceptionEntity.setCreateOrgName(userContextHelper.getOrgName());
							//组织code
							exceptionEntity.setCreateOrgCode(userContextHelper.getOrgCode());
							//增加异常信息
							exceptionProcessDao.addExceptionProcessInfo(exceptionEntity);
						}
					}
				}
			}else{
				//运单号不存在 抛出业务异常
				throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_NOTEXIST);
			}
		}
	}
	
	/**
	 * 
	 * 添加异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午2:20:04
	 */
	@Transactional
	public void addException(ExceptionEntity exceptionEntity){
		//校验流水号是否存在
		boolean isSerialNo = waybillManagerService.isSerialNoExsits(exceptionEntity.getWaybillNo(), exceptionEntity.getSerialNo());
		//若存在
		if(isSerialNo)
		{
			//修改异常件数
			exceptionProcessDao.updateExceptionGoodsQty(exceptionEntity.getWaybillNo());
		}
		else
		{
			//流水号不存在 抛出业务异常
			throw new ExceptionProcessException(ExceptionProcessException.SERIALNO_NOT_MATCH);
		}
		int i = exceptionProcessDao.queryHandlingInfo(exceptionEntity.getWaybillNo(), exceptionEntity.getSerialNo());
		if(i>0)
		{
			//流水号存在处理中抛出业务异常
//			throw new ExceptionProcessException(ExceptionProcessException.SERIALNO_IS_HANDLING);
			return ;
		}
		//增加异常信息
		exceptionProcessDao.addExceptionProcessInfo(exceptionEntity);
	}
	
	/**
	 * 新增历史处理详情.
	 *
	 * @param 
	 * exceptionProcessDetailEntity 
	 * 		the 
	 * 			exception process detail entity
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-3 
	 * 		下午3:54:53
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#addExceptionProcessInfo
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addExceptionProcessDetail(ExceptionProcessDetailEntity exceptionProcessDetailEntity) 
	{
		//如果exceptionProcessDetailEntity不为空
		if(exceptionProcessDetailEntity != null)
		{
			//新增处理详情
			exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
		}
	}
	
	/**
	 * 查询异常信息（非分页）.
	 *
	 * @param 
	 * exceptionProcessConditionDto 
	 * 		the 
	 * 			exception process condition dto
	 * @return 
	 * 		the 
	 * 			input stream
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-6 
	 * 		下午4:27:18
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#queryExceptionProcessInfo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto)
	 */
	@Override
	public InputStream queryExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto) 
	{	
		//如果exceptionProcessConditionDto不为空
		if(exceptionProcessConditionDto != null)
		{
			//如果异常环节为全部
			if(exceptionProcessConditionDto.getExceptionLink().equals(TOTAL))
			{
				//返回空
				exceptionProcessConditionDto.setExceptionLink("");
			}
			//如果异常类型为全部
			if(exceptionProcessConditionDto.getExceptionType().equals(TOTAL))
			{
				//返回空
				exceptionProcessConditionDto.setExceptionType("");
			}
			//如果异常状态为全部
			if(exceptionProcessConditionDto.getStatus().equals(TOTAL))
			{
				//返回空
				exceptionProcessConditionDto.setStatus("");
			}
			//如果异常操作为全部
			if (exceptionProcessConditionDto.getExceptionOperate().equals(TOTAL)) {
				//传入空
				exceptionProcessConditionDto.setExceptionOperate("");
			}
			//获取当前登录用户
			UserContextHelper userContextHelper = new UserContextHelper();
			//设置生效条件
			exceptionProcessConditionDto.setActive(FossConstants.ACTIVE);
			//组织code
			exceptionProcessConditionDto.setDepartmentCode(userContextHelper.getOrgCode());
			//得到异常信息list
			List<ExceptionProcessDto> list = exceptionProcessDao.queryExceptionProcess(exceptionProcessConditionDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for(ExceptionProcessDto exception : list){
				List<String> columnList = new ArrayList<String>();
				//运单号
				columnList.add(exception.getWaybillNo());
				//异常状态
				columnList.add(DictUtil.rendererSubmitToDisplay(exception.getStatus(), DictionaryConstants.PKP_EXCEPTION_STATE));
				//收货人
				columnList.add(exception.getReceiveCustomerName());
				//收货人手机
				columnList.add(exception.getReceiveCustomerMobilephone());
				//收货人电话
				columnList.add(exception.getReceiveCustomerPhone());
				//异常类型
				columnList.add(DictUtil.rendererSubmitToDisplay(exception.getExceptionType(), DictionaryConstants.PKP_EXCEPTION_TYPE));
				//异常环节
				columnList.add(DictUtil.rendererSubmitToDisplay(exception.getExceptionLink(), DictionaryConstants.PKP_EXCEPTION_PHASE));
				//异常操作
				columnList.add(DictUtil.rendererSubmitToDisplay(exception.getExceptionOperate(), DictionaryConstants.PKP_EXCEPTION_OPERATE));
				//通知内容
				columnList.add(exception.getNoticeContext());
				//流水号
				columnList.add(exception.getSerialNo());
				//收货部门
				columnList.add(exception.getReceiveOrgName());
				//登记人
				columnList.add(exception.getCreateUserName());
				//登记时间
				columnList.add(DateUtils.convert(exception.getExceptionTime(), DateUtils.DATE_TIME_FORMAT));
				//更新时间
				columnList.add(DateUtils.convert(exception.getModifyTime(), DateUtils.DATE_TIME_FORMAT));
				//异常原因exceptionReason
				columnList.add(exception.getExceptionReason());
				//如果库存天数不为空
				if(exception.getStorageDay()!= null)
				{
					columnList.add(exception.getStorageDay().toString());//库存天数
				}else
				{
					columnList.add("");//库存天数
				}
				rowList.add(columnList);
			}
			String[] rowHeads = {"运单号","异常状态","收货人","收货人手机","收货人电话","异常类型","异常环节","异常操作","通知内容","流水号","收货部门","登记人","登记时间","更新时间","异常原因","库存天数"};
			
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(rowHeads);
		    exportResource.setRowList(rowList);
		    ExportSetting exportSetting = new ExportSetting();
		    exportSetting.setSheetName("异常列表");
		    exportSetting.setSize(NUMBER);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	        return objExporterExecutor.exportSync(exportResource, exportSetting);
		}
		//返回空
		return null;
	}
	
	/**
	 * 查询异常信息（非分页）.
	 *
	 * @param exceptionProcessConditionDto 
	 * 		the 
	 * 			exception process condition dto
	 * @return 
	 * 		the 
	 * 			list
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-13
	 * 		 下午1:51:11
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#queryExceptionProcess
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto)
	 */
	@Override
	public List<ExceptionProcessDto> queryExceptionProcess(ExceptionProcessConditionDto exceptionProcessConditionDto) 
	{
		//如果exceptionProcessConditionDto不为空
		if(exceptionProcessConditionDto != null)
		{
			//返回异常信息
			return exceptionProcessDao.queryExceptionProcess(exceptionProcessConditionDto);
		}
		//返回空
		return null;
	}
	
	/**
	 * 删除客户自提异常信息(2王飞).
	 *
	 * @param exceptionEntity 
	 * 		the 
	 * 			exception entity
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-7
	 * 		 上午10:00:27
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#deleteExceptionProcessInfo2PICKUP
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
	 */
	@Override
	public void deleteExceptionProcessInfo2Pickup(ExceptionEntity exceptionEntity) 
	{
		//如果exceptionEntity不为空
		if(exceptionEntity != null)
		{
			//返回异常信息List
			List<ExceptionEntity> exceptionList =  exceptionProcessDao.queryExceptionProcessInfo(exceptionEntity);
			//循环List
			for(ExceptionEntity exception : exceptionList)
			{
				//异常id
				String exceptionId = exception.getId();
				//删除异常主数据
				exceptionProcessDao.deleteExceptionProcessInfo(exceptionId);
				//删除异常处理信息
				exceptionProcessDao.deleteExceptionProcessDetail(exceptionId);
			}
		}
	}
	
	/**
	 * 处理异常 提供给中转调用.
	 *
	 * @param exceptionOperateDto 
	 * 		the 
	 * 			exception operate dto
	 * @author 
	 * 	043258-
	 * 		foss-
	 * 			zhaobin
	 * @date 2012-12-7
	 * 		 下午3:43:16
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#operateExceptionProcessInfo
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionOperateDto)
	 */
	@Override
	public void operateExceptionProcessInfo(ExceptionOperateDto exceptionOperateDto) 
	{
		//如果exceptionOperateDto不为空
		if(exceptionOperateDto != null)
		{
			//得到异常信息
			ExceptionEntity exceptionEntity = exceptionOperateDto.getExceptionEntity();
			//返回异常集合
			List<ExceptionProcessDetailEntity> list = exceptionOperateDto.getExceptionProcessDetailEntityList();
			//新增异常信息
			exceptionProcessDao.addExceptionProcessInfo(exceptionEntity);
			//如果list不为空
			if(CollectionUtils.isNotEmpty(list))
			{
				//循环list
				for(ExceptionProcessDetailEntity exceptionProcessDetailEntity : list)
				{
					//新增异常处理信息
					exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
				}
			}
		}
	}
	private String getSmsContent(ExceptionProcessDetailDto mapParam,String smsCode) {
		String sms = ""; // 返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		//获取当前登录用户
		UserContextHelper userContextHelper = new UserContextHelper();
		// 部门编码
		smsParamDto.setOrgCode(userContextHelper.getOrgCode());
		smsParamDto.setMap(getSmsParam(mapParam));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		return sms;
	}
	private Map<String, String> getSmsParam(ExceptionProcessDetailDto mapParam) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("receiveOrgName", mapParam.getReceiveOrgName());
		map.put("waybillNo", mapParam.getWaybillNo());
		map.put("notes", mapParam.getNotes());
		map.put("exceptionType", mapParam.getExceptionType());
		return map;
	}
	
	/**
	 * 新增通知消息.
	 *
	 * @param 
	 * receiveOrgCode 
	 * 		the 
	 * 			receive org code
	 * @param 
	 * 		noticeContext 
	 * 			the notice context
	 * @throws 	
	 * ExceptionProcessException 
	 * 		the 
	 * 			exception process exception
	 * @author 
	 * 		043258-
	 * 		foss-
	 * 		zhaobin
	 * @date 2012-12-15
	 * 		 上午10:59:03
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#addNotice
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void addNotice(String receiveOrgCode, ExceptionProcessDetailDto noticeMap) throws ExceptionProcessException
	{
		try {
			//如果接受组织code、通知内容不为空
			if(StringUtils.isNotEmpty(receiveOrgCode) && noticeMap!= null)
			{	
				//获得用户
				UserContextHelper userContextHelper = new UserContextHelper();
				//站内消息Job分发表
				InstationJobMsgEntity entity = new InstationJobMsgEntity();
				//UUID
				entity.setId(UUIDUtils.getUUID());
				//发送人员编码
				entity.setSenderCode(userContextHelper.getUserCode());
				//发送人员名称
				entity.setSenderName(userContextHelper.getUserName());
				//发送方组织编码
				entity.setSenderOrgCode(userContextHelper.getOrgCode());
				//发送方组织名称
				entity.setSenderOrgName(userContextHelper.getOrgName());
				//接收方组织编码
				entity.setReceiveOrgCode(receiveOrgCode);
				//接收方类型
				entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
				//消息内容
				entity.setContext(getSmsContent(noticeMap,ExceptionProcessConstants.NOTICE_CONTENT_CODE));
				//站内消息类型
				entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
				//发送时间
				entity.setPostDate(new Date());
				//消息状态
				entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
				//人员对组织的站内消息发送
				messageService.createBatchInstationMsg(entity);
			}else
			{
				//接受组织code、通知内容为空 抛出业务异常
				throw new ExceptionProcessException(ExceptionProcessException.NOTICE_FAILED);
			}
		} catch (MessageException e) {
			//抛出异常信息
			throw new ExceptionProcessException(e.getErrorCode(), e);
		}
	}
	
	/**
	 * 
	 * 查询运单流水号
	 * @author 043258-foss-zhaobin
	 * @date 2013-4-23 上午9:23:14
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public List<SerialNoDto> querySerialNos(String waybillNo) {
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		List<SerialNoDto> serialNoDtoList = new ArrayList<SerialNoDto>();
		if(waybillEntity != null && StringUtils.isNotBlank(waybillEntity.getLastLoadOrgCode())){
			if(!waybillEntity.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
				//运单到达部门与当前登录部门不匹配抛出业务异常
				throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_NOT_MATCH);
			}else{
				SignDto dto = new SignDto();
				dto.setWaybillNo(waybillNo);
				dto.setLastLoadOrgCode(waybillEntity.getLastLoadOrgCode());
				List<StockDto> stockDtoList = signService.dealExceptionQueryStock(dto);
				
				if(CollectionUtils.isNotEmpty(stockDtoList))
				{
					for (StockDto stockDto : stockDtoList) {
						SerialNoDto serialNoDto = new SerialNoDto();
						serialNoDto.setSerialNo(stockDto.getSerialNo());
						serialNoDtoList.add(serialNoDto);
					}
				}
				
				return serialNoDtoList;
			}
		}else {
			//流水号不存在 抛出业务异常
			throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_NOTEXIST);
		}
		
	}
	
	/**
	 * 获取当前登录用户.
	 *
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-9 
	 * 		下午4:28:56
	 */
	public static class UserContextHelper
	{
		//获取当前登录人员信息
		/** The user. */
		UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
		
		/**
		 * 获取当前登录用户名.
		 *
		 * @return the user name
		 * @author 043258-
		 * 		foss-zhaobin
		 * @date 2012-11-9
		 * 		 下午4:29:32
		 * @return
		 * @see
		 */
		String getUserName() {
			return user == null ? "" : user.getEmployee().getEmpName();
		}
		
		/**
		 * 获取当前登录用户code.
		 *
		 * @return the user code
		 * @author 043258-
		 * 		foss-zhaobin
		 * @date 2012-11-9
		 * 		 下午4:29:55
		 * @return
		 * @see
		 */
		String getUserCode() {
			return user == null ? "" : user.getEmployee().getEmpCode();
		}

		/**
		 * 获取当前登录用户所在部门.
		 *
		 * @return the org name
		 * @author 043258-
		 * 		foss-zhaobin
		 * @date 2012-11-9 
		 * 		下午4:30:24
		 * @return
		 * @see
		 */
		String getOrgName() {
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept ();
			return org == null ? "" : org.getName();
		}
		
		/**
		 * 获取当前登录用户所在部门code.
		 *
		 * @return the org code
		 * @author 043258-
		 * 		foss-zhaobin
		 * @date 2012-11-9 
		 * 		下午4:30:44
		 * @return
		 * @see
		 */
		String getOrgCode() {
			return FossUserContext. getCurrentDeptCode();
		}
	}


	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * 
	 * 批量处理异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午5:34:10
	 */
	@Override
	public void batchProcess(List<ExceptionProcessDto> exceptionProcessDtos) {
		// 批量添加备注
		for(ExceptionProcessDto exceptionProcessDto : exceptionProcessDtos){
			exceptionProcessService.addExceptionProcessDetail(exceptionProcessDto.getExceptionProcessId(), exceptionProcessDto.getNotes(),null,null);
		}
	}
	
	/**
	 * 
	 * 批量处理完毕异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午5:34:10
	 */
	@Override
	public void batchComplete(List<ExceptionProcessDto> exceptionProcessDtos) {
		// 批量添加备注
		for(ExceptionProcessDto exceptionProcessDto : exceptionProcessDtos){
			exceptionProcessService.operateExceptionProcessDetail(exceptionProcessDto.getExceptionProcessId(),exceptionProcessDto.getNotes(),exceptionProcessDto.getExceptionType(),exceptionProcessDto.getExceptionLink(),exceptionProcessDto.getWaybillNo(),exceptionProcessDto.getArrivesheetId(),null);
		}
	}

	/**
	 * 根据运单号查询处理异常信息.  现在废弃
	 *
	 * @param exceptionProcessConditionDto 
	 * 		查询条件
	 * 
	 * @param start 
	 * 			the start
	 * @param limit 
	 * 			the limit
	 * @return the list
	 * @author foss-sunyanfei
	 * @date 2015-9-1  上午11:32:27
	 */
	@Transactional
	public List<ExceptionProcessDto> queryExceptionProcessListByCondition(
			ExceptionProcessConditionDto exceptionProcessConditionDto, int start,
			int limit) {

		//去除查询条件中的重复的运单号
		if(null != exceptionProcessConditionDto && null != exceptionProcessConditionDto.getArrayWaybillNos()) {
			exceptionProcessConditionDto.setArrayWaybillNos((String[])getList(exceptionProcessConditionDto.getArrayWaybillNos()).toArray());
		}
		LOGGER.info("异常处理-----对象实体"+ReflectionToStringBuilder.toString(exceptionProcessConditionDto));
		List<ExceptionProcessDto> exceptionProcessDto = new ArrayList<ExceptionProcessDto>();
		
		exceptionProcessDto = this.queryExceptionProcessInfo(exceptionProcessConditionDto, start, limit);
		if (null != exceptionProcessDto && exceptionProcessDto.size() > 0) {
			if (StringUtil.isNotBlank(exceptionProcessConditionDto.getWaybillNo()))  {
				HashMap<String , ExceptionProcessDto> map = new HashMap<String, ExceptionProcessDto>();
				for (int i = 0; i < exceptionProcessDto.size(); i++) {
					map.put(exceptionProcessDto.get(i).getWaybillNo(), exceptionProcessDto.get(i));
				}
				List<ExceptionProcessDto> exceptionProcessDto1 = new ArrayList<ExceptionProcessDto>();
				if (StringUtil.isNotBlank(exceptionProcessConditionDto.getWaybillNo())) {
					// 解析运单号为列表
					exceptionProcessConditionDto.setArrayWaybillNos(exceptionProcessConditionDto.getWaybillNo().split("\\n"));
					// 将运单号数组去重
					List<String> list = getList(exceptionProcessConditionDto.getArrayWaybillNos());
					for (String waybill : list) {
						if (map.containsKey(waybill)) {
							exceptionProcessDto1.add(map.get(waybill));
						}
					}
				}
				return exceptionProcessDto1;
			}
		}
		LOGGER.info("异常处理查询完毕");
		return exceptionProcessDto;
	}
	
	/**
	 * 字符串数组去重
	 * @author sunyanfei
	 * @date 2015-09-06
	 * @param str
	 * @return
	 */
	public List<String> getList(String [] str){
		 
		List<String> strs = new ArrayList<String>();
		 
		for(int i = 0;i<str.length;i++){
			if(!strs.contains(str[i])){
				strs.add(str[i]);
			}
		}
		return strs;
	}
	
	
	/**
	 * 修改处理异常状态，
	 * 1、对于变更目的站的运单，当系统或人工受理更改单时，将该运单已生成的异常信息自动完结，变为已处理<br/>
	 * 2、处理结果显示为"运单变更目的站"
	 * @author gpz-foss
	 * @date 2015-10-21 下午3:16:44
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService#updateExceptionProcessStatus(java.lang.String)
	 */
	@Override
	public boolean updateExceptionProcessStatus(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			throw new ExceptionProcessException(ExceptionProcessException.WAYBILLNO_NOTEXIST);
		}
		
		//获得用户信息
		UserContextHelper userContextHelper = new UserContextHelper();
		
		//查询正在处理过程中的异常处理信息记录
		ExceptionEntity exceptionCondition = new ExceptionEntity();
		exceptionCondition.setWaybillNo(waybillNo);//运单号
		exceptionCondition.setStatus(ExceptionProcessConstants.HANDLING);//异常状态正在处理
		//根据运单号和异常状态查询处理异常记录
		List<ExceptionEntity> exceptionEntityList = exceptionProcessDao.queryExceptionProcessInfoByParams(exceptionCondition);
		if(CollectionUtils.isNotEmpty(exceptionEntityList)){
			for(ExceptionEntity entity : exceptionEntityList){
				//异常实体
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				exceptionEntity.setId(entity.getId());//异常id
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);//已处理
				exceptionEntity.setExceptionReason("运单变更目的站");// 备注
				
				//新增异常处理信息
				ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
				//新增异常处理记录
				exceptionProcessDetailEntity.settSrvExceptionId(entity.getId());
				//异常处理结果
				exceptionProcessDetailEntity.setNotes(exceptionEntity.getExceptionReason());
				//用户名
				exceptionProcessDetailEntity.setOperator(userContextHelper.getUserName());
				//用户code
				exceptionProcessDetailEntity.setOperatorCode(userContextHelper.getUserCode());
				//组织名称
				exceptionProcessDetailEntity.setOperateOrgName(userContextHelper.getOrgName());
				//组织code
				exceptionProcessDetailEntity.setOperateOrgCode(userContextHelper.getOrgCode());
				//将当前时间赋值给操作时间
				exceptionProcessDetailEntity.setOperateTime(new Date());
				//新增异常处理记录
				exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
				
				//根据处理异常记录id修改处理异常状态
				exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
			}
		}
		
		return true;
	}
	

	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	@Override
	public List<ExceptionProcessDetailEntity> selectExceptionProcessDetailList(
			String exceptionProcessId) {
		if (StringUtils.isNotBlank(exceptionProcessId)) {
			return exceptionProcessDao.selectExceptionProcessDetailList(exceptionProcessId);
		}
		return null;
	}

	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public IBeforeNoticeService getBeforeNoticeService() {
		return beforeNoticeService;
	}

	public void setBeforeNoticeService(IBeforeNoticeService beforeNoticeService) {
		this.beforeNoticeService = beforeNoticeService;
	}

	@Override
	@Transactional
	public void operateExceptionAndNotifyCustomer(ExceptionProcessDetailDto exceptionProcessDetailDto) {
		
		if (exceptionProcessDetailDto == null) {
			//传入对象 不能为空
			LOGGER.info("operateExceptionAndNotifyCustomer 方法传入参数不能为空");
			throw new ExceptionProcessException("传入对象 不能为空");
		}
		// 处理完毕
		operateExceptionProcessDetail(exceptionProcessDetailDto.getExceptionProcessId(), exceptionProcessDetailDto.getNotes(),
				exceptionProcessDetailDto.getExceptionType(),exceptionProcessDetailDto.getExceptionLink(),
				exceptionProcessDetailDto.getWaybillNo(),exceptionProcessDetailDto.getArrivesheetId(), exceptionProcessDetailDto.getDeliverDate());
	
		NotifyCustomerConditionDto notifyCustomerConditionDto = createNotifyCustomerConditionDto(exceptionProcessDetailDto);
		// 通知客户
		beforeNoticeService.addNotificationInfo(notifyCustomerConditionDto);
	}
	
	/** 
	 * @Title: createNotifyCustomerConditionDto 
	 * @Description: 创建通知信息 
	 * @param @param exceptionProcessDetailDto
	 * @return NotifyCustomerConditionDto    返回类型 
	 * @throws 
	 */
	private NotifyCustomerConditionDto createNotifyCustomerConditionDto(ExceptionProcessDetailDto exceptionProcessDetailDto) {
		NotifyCustomerConditionDto conditionDto = new NotifyCustomerConditionDto();
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setWaybillNo(exceptionProcessDetailDto.getWaybillNo());
		notificationEntity.setReceiveCustomerMobilephone(exceptionProcessDetailDto.getReceiveCustomerMobilephone());
		notificationEntity.setDeliverDate(DateUtils.convert(exceptionProcessDetailDto.getDeliverDate(), DateUtils.DATE_FORMAT));
		notificationEntity.setDeliverRequire(exceptionProcessDetailDto.getNotes());
		notificationEntity.setPaymentType(exceptionProcessDetailDto.getPaymentType());
		notificationEntity.setToPayAmount(exceptionProcessDetailDto.getToPayAmount());
		notificationEntity.setNoticeResult("SUCCESS");
		notificationEntity.setIsPreNotify(exceptionProcessDetailDto.getIsPreNotify());
		notificationEntity.setActualProvCode(exceptionProcessDetailDto.getActualProvCode());
		notificationEntity.setActualCityCode(exceptionProcessDetailDto.getActualCityCode());
		notificationEntity.setActualDistrictCode(exceptionProcessDetailDto.getActualDistrictCode());
		notificationEntity.setActualAddressDetail(exceptionProcessDetailDto.getActualAddressDetail());
		notificationEntity.setActualStreetn(exceptionProcessDetailDto.getActualStreetn());
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE);
		notificationEntity.setReceiveCustomerCode(exceptionProcessDetailDto.getReceiveCustomerCode());
		notificationEntity.setReceiveCustomerContact(exceptionProcessDetailDto.getReceiveCustomerContact());
		notificationEntity.setDeliveryTimeInterval(exceptionProcessDetailDto.getDeliveryTimeInterval());
		notificationEntity.setDeliveryTimeStart(exceptionProcessDetailDto.getDeliveryTimeStart());
		notificationEntity.setDeliveryTimeOver(exceptionProcessDetailDto.getDeliveryTimeOver());
		notificationEntity.setIsExhibition(exceptionProcessDetailDto.getIsExhibition());
		notificationEntity.setIsEmptyCar(exceptionProcessDetailDto.getIsEmptyCar());
		notificationEntity.setInvoiceType(exceptionProcessDetailDto.getInvoiceType());
		notificationEntity.setInvoiceDetail(exceptionProcessDetailDto.getInvoiceDetail());
		notificationEntity.setIsSentRequired(exceptionProcessDetailDto.getIsSentRequired());
		
		conditionDto.setNotificationEntity(notificationEntity);
		
		NotifyCustomerDto customerDto = new NotifyCustomerDto();
		customerDto.setReceiveCustomerName(exceptionProcessDetailDto.getReceiveCustomerName());
		customerDto.setReceiveCustomerPhone(exceptionProcessDetailDto.getReceiveCustomerPhone());
		
		conditionDto.setNotifyCustomerDto(customerDto);
		
		return conditionDto;
	}

	public IStockService getStockService() {
		return stockService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	
}