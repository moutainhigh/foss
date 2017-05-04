/**
 *  initial comments.
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/HandOverBillAction.java
 *  
 *  FILE NAME          :HandOverBillAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *   *   *  修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-04-25	新增	石巍	V0.1
2012-05-31	取消集配的配载查询方案，按走货路径查询；
					查询交接运单界面取消上下两表格移动的方式，
					采取在一个表格中直接勾选；	石巍	V0.2
2012-06-02	修改查询交接运单只查询库存中的货物；	石巍	V0.3
2012-06-11	 取消封签选择框；	周德军	V0.4
2012-10-17	根据RC-571修改	石巍	V1.1
2012-12-04	预配单可加在途运单、增加修改集配查询方案功能	石巍	V1.2
2013-02-23	Issue-1657	石巍	V1.3
2013-02-28	Issue-1842	石巍	V1.4
2013-03-01	Issue-1357	石巍	V1.5
2013-03-15	Issue-2057	石巍	V1.6

1.	SUC-87新增交接单(无PDA)
1.1	相关业务用例
BUC_FOSS_5.30.20_090无PDA-制作预配单
1.2	用例描述
营业员、配载员使用本用例新增交接单
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	
1、	本部门存在有库存中的货物；	
后置条件	
1、	生成交接单；
2、	更新货物的交接状态为“预配交接”(预配)；
3、	货物从本部门出库(非预配)、生成车辆放行需求。	
1.4	操作用户角色
操作用户	描述
营业员	经营部门工作人员，可查询、修改、作废、新增交接单
配载员	运作部门工作人员，可查询、修改、作废、新增交接单
1.5	界面要求
1.5.1	表现方式
Web方式
1.5.2	界面原型-交接单新增主界面
  
图 11：交接单新增主界面
1.5.3	界面描述-交接单新增主界面
	    该界面主要分为五个部分：
	    交接单基本信息、
	    运单信息列表、
	    运单扫描明细列表、
	    交接单统计信息、
	    功能按钮；
1、	交接单基本信息：包括交接类型、
		交接单编号、
		交接日期、
		出发部门、
		到达部门、
		司机电话、
		司机、
		车牌号、
		装车完成时间、
		交接单版本号、
		制单人、
		修改人、
		货物类型、
		是否预配交接单、
		备注、
		是否代理上门接货；
1.1	交接类型：分为集配交接单、
		短配交接单、
		外发交接单，参见业务规则SR-1；
1.2	交接单编号：系统自动生成的交接单编号；
1.3	交接日期：交接单的交接日期；
1.4	出发部门：交接单的出发部门；
1.5	到达部门：交接单的到达部门，共用选择框，
		内容读取自部门基础资料，参见业务规则SR-1；
1.6	司机电话：司机的电话号码，参见业务规则SR-2；
1.7	司机：司机姓名，共用选择框，内容读取自司机基础资料；
1.8	车牌号：负责装载、运输该交接单货物的车辆的车牌号，
		下拉框，内容读取自车辆基础资料；
1.9	装车完成时间：交接单货物装车完成时间；
1.10	交接单版本号：新增时默认为1.0，不可修改；
1.11	制单人：交接单第一次被保存时的系统操作人；
1.12	修改人：当前版本交接单生成时的系统操作人；
1.13	货物类型：货物的类型，分为A类、B类、全部，参见业务规则SR-9；
1.14	是否预配交接单：该交接单是否为预配；
1.15	是否代理上门接货：如果选是则代理直接上门接货；
1.16	备注：交接单备注信息；
2、	运单信息列表：参见数据元素【运单信息列表】；
3、	运单扫描明细列表：参见数据元素【运单扫描明细列表】；
4、	交接单统计信息：包括总票数、总件数、总重量、总体积；
4.1	总票数：【运单信息列表】中运单的总个数；
4.2	总件数：【运单信息列表】中“已配件数”的总和；
4.3	总重量：【运单信息列表】中“实际重量”的总和；
4.4	总体积：【运单信息列表】中“实际体积”的总和；
5、	功能按钮：包括保存、打印、删除运单、添加运单、查询交接运单、；
5.1	保存：保存交接单信息；
5.2	打印：打印交接单；
5.3	删除运单：此按钮位于【运单信息列表】的操作列中，
		点击该图标按钮删除【运单信息列表】中对应的运单；
5.4	添加运单：向【运单信息列表】中添加运单；
5.5	查询交接运单：点击此按钮，弹出查询交接运单界面，参见业务规则SR-3；
1.5.4	界面原型-查询交接运单
1.5.5	界面描述-查询交接运单
该界面分为五个部分：查询条件、查询结果列表、货物扫描明细列表、已选运单统计信息、功能按钮区；
1、	查询条件：包括入库时间、运输性质、运输类型、运单号、外发代理、目的站；
		各控件根据新增交接单主界面的“交接类型”来隐藏或者显示，具体规则参见业务规则SR-4；
	1.1	入库时间：货物在本部门的入库时间，当“交接类型”为短配交接单时，此控件显示；
	1.2	运输性质：开单时选择的产品类别，当“交接类型”为集配交接单时，此控件显示；
	1.3	运输类型：开单时填写的运输类型，
			包括全部、汽运、空运，当“交接类型”为短配交接单时，此控件显示；
	1.4	运单号：货物的运单号；
	1.5	目的站：货物的目的站，当“交接类型”为外发交接单时，此控件显示；
	1.6	外发代理：要外发的代理名称，共用选择框，内容读取自外发代理基础资料；
2、	查询结果列表：具体字段参见数据元素【待选运单列表】；
3、 功能按钮：查询、重置、提交、取消、更改查询方案；
2.1	查询：点击此按钮，查询部门库存货物信息，参见业务规则SR-3；
2.2	重置：点击此按钮重新初始化【待选运单查询条件】；
2.3	提交：点击此按钮，将【待选运单列表】中被勾选的货物提交至新增交接单主界面(图1)的【运单信息列表】，参见业务规则SR-10、SR-11；
2.4	取消：点击此按钮，退出当前界面。
2.42.5	更改查询方案：参见业务规则SR-22。
1.6	操作步骤
1.6.1	新增交接单
序号	基本步骤	相关数据	补充步骤
1	进入交接单新增界面		
2	输入【交接单基本信息】	【交接单基本信息】	参见业务规则SR-1、SR-2、SR-3
3	点击“添加运单”按钮，【运单信息列表】中增加一行，输入运单号，系统自动填充其他字段	【运单信息列表】	参见业务规则SR-21
4	点击【运单扫描明细列表】中某行的“删除”链接		将该货物从【运单扫描明细列表】中删除，同时更新【交接单统计信息】；本步骤可跳过，也可反复执行
5	点击“查询交接运单”按钮，弹出查询交接运单界面(图3)		本步骤可跳过，也可反复执行
6	在查询交接运单界面，输入待选运单查询条件	【待选运单查询条件】	
7	在查询交接运单界面，点击“查询”按钮		
8	在查询交接运单界面，勾选【待选运单列表】中的货物	【待选运单列表】	自动更新【已选运单统计信息】，参见业务规则SR-12
9	在查询交接运单界面，取消勾选【待选运单列表】中的货物	【待选运单列表】	自动更新【已选运单统计信息】，参见业务规则SR-12
10	在查询交接运单界面，点击“提交”按钮		关闭查询交接运单界面，显示新增交接单主界面，参见业务规则SR-10
11	点击“保存”按钮		保存交接单信息
12	点击“打印”按钮		参见打印交接单用例

序号	扩展事件	相关数据	备注
4a	步骤3中，用户可删除某运单的【运单扫描明细列表】中的某条记录，如果只有一条记录，则提示“无法删除，请直接删除该运单”		参见业务规则SR-8
5a	步骤5中，如果“交接类型”为外发交接单，且“外发代理”为空，则不弹出图3界面，告知用户“外发代理”不能为空。		提示内容：外发代理不能为空 

1.7	业务规则
序号	描述
SR-1	当“交接类型”为“外发交接单”时，“到达部门”控件隐藏、显示“外发代理” 控件，该控件为共用选择框，内容读取自外发代理基础资料；
SR-2	【交接单基本信息】中“司机电话”不可输入，当用户输入“司机”后，自动关联司机基础资料读取司机电话并填充；
SR-3	查询交接运单界面(图3)中，“入库时间”的截止日期默认为当前日期，日期差为20天；点击“查询”按钮后，查询所有符合查询条件的、本部门库存中的、“到达部门”或“外发代理”为其下一交接部门的货物；
1、	当“交接类型”为“集配交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、运输性质、运单号；只查询“运输性质”为精准卡航（长途）或精准汽运（长途）的货物；
2、	当“交接类型”为“短配交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、运输类型、运单号；
3、	当“交接类型”为“外发交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、外发代理、运单号；只查询“提货网点”为代理网点的货物；
SR-4	【运单信息列表】中“实际体积”、“实际重量”不可修改，“备注”字段可修改，“实际体积”、“实际重量”分别默认等于“已配体积”、“已配重量”；
SR-5	当在【运单信息列表】中增加一条运单后，【交接单统计信息】中“总件数”加上该运单的“已配件数”、“总票数”加1、“总重量”和“总体积”分别加上该运单的“实际重量”和“实际体积”；
当在【运单信息列表】中删除一条运单后，【交接单统计信息】中“总件数”减去该运单的“已配件数”、“总票数”减1、“总重量”和“总体积”分别减去该运单的“实际重量”和“实际体积”；
SR-6	假设【运单信息列表】中“已配重量”为a，“已配体积”为b，“已配件数”为c，当删除【运单扫描明细列表】中的某件货物：
1、【运单信息列表】中“已配重量”变为：a*(c-1)/c，“已配体积”变化为：b*(c-1)/c；
2、【交接单统计信息】中“总件数”减去1，“总体积”减去b/c，“总重量”减去a/c；
SR-7	保存时，交接单中【运单信息列表】至少有一条记录，【运单信息列表】中的每条运单的【运单扫描明细列表】至少有一条记录；
SR-8	当“交接类型”为“集配交接单”或“短配交接单”时，必须填写“到达部门”才能点击“查询交接运单”按钮，否则提示“到达部门不能为空” 否则该按钮不可用；
当“交接类型”为“外发交接单”，点击“查询交接运单”按钮时，“外发代理”不可为空，打开图3界面后，查询条件中的“外发代理”也不可为空、并且外发代理只可以选择一个代理，并为当前选择的代理生成一个交接单。不可选择多个代理生成交接单；
SR-9	【交接单基本信息】中“出发部门”默认为当前登录部门，不可修改；
“交接时间”默认为当前时间，不可修改，保存时“交接时间”保存为当前服务器时间；
“交接单编号”为系统自动生成，为八位数字组成的流水号，不可重复，不可修改；
“交接单版本号”默认为1.0，不可修改；
“货物类型”分为全部、A类、B类，只有“到达部门”为自动分拣部门时此控件才可编辑，其他情况下默认为“全部”，不可修改；
“是否预配交接单”默认勾选，可以手动取消勾选；
SR-10	查询交接运单界面(图3)：
		点击“提交”按钮，关闭当前页面，跳转至交接单新增主界面(图1)，
		将【待选运单列表】中的勾选的货物信息提交至交接单主界面的【运单信息列表】中，
		其中“已配件数”等于提交的【待选运单列表】的“件数”、“已配重量”等于提交的【待选运单列表】中的“重量”、
		“已配体积”等于提交的【待选运单列表】中的“体积”；
SR-11	当从【待选运单列表】中向【运单信息列表】中提交货物信息时，
		如果目标列表中存在和源列表中相同运单号、不同扫描流水号的货物，
		则只在目标列表的基础上对该条记录的“件数”、“体积”、“重量”进行累加，
		同时该条运单的【运单扫描明细列表】中增加提交的货物的流水号，并不在【运单信息列表】中新增行；
		如果目标列表中存在和源列表中相同运单号、相同扫描流水号的货物，则不做操作；
SR-12	勾选【待选运单列表】中的某件货物后，
		假设该条运单信息“件数”为a，“重量”为b，“体积”为c；
		则【已选运单统计信息】中“已选总件数”加1、“已选总重量”加b/a，“已选总体积”加c/a，
		若该运单中的货物之前未被勾选过，
		则【已选运单统计信息】中“总金额”加该运单的“运单金额”，否则“总金额”不变；
		取消勾选【待选运单列表】中的某件货物后，
		假设该条运单信息“件数”为a，“重量”为b，“体积”为c；
		则【已选运单统计信息】中“已选总件数”减1、“已选总重量”减b/a，“已选总体积”减c/a，
		若该运单中的货物都已取消勾选，
		则【已选运单统计信息】中“总金额”减去该运单的“运单金额”，否则“总金额”不变；
SR-13	当“交接类型”为“集配交接单”时，
		【待选运单列表】的记录需用颜色标识来区分“本地货物”和“中转货物”，
		“本地货物”是只由本部负责汽运配载的部门所开单的货物，其他货物都为“中转货物”；
SR-14	保存交接单时，如果为预配交接单，
		则更新【运单信息列表】中所有货物的交接状态为“预配交接”，库存不做改变；
		如果非预配交接单，保存成功后，列表中所有货物从本部门出库；
SR-15	保存交接单时，如果交接类型为“短配交接单”或“外发交接单”，
		且非预配交接单时，需要传入“车牌号”、“司机”、“交接单号”生成一条放行需求，
		如果当前车牌号已经存在一条状态为“待放行”的放行需求，则不重复生成放行需求；
SR-16	如果为非预配交接单，当前交接单的“到达部门”不在某票货物的走货路径上，
		则重新生成货物走货路径；针对需要打木架的货物，如果未完成打木架，不可保存交接单，
		提示“该票货物尚未打木架，不可交接出库”；
		针对贵重物品，如果执行了贵重物品入库操作但未执行贵重物品出库操作，不可保存交接单，提示“该票货物为贵重物品，尚未执行贵重物品出库操作，无法交接出库”；
SR-17	必须先保存交接单才能打印交接单；
SR-18	如果选择代理上门接货，则司机、车牌号为空；
SR-19	中转外场与驻地部门共用库存、参见部门与库存基础资料；
SR-20	【运单信息列表】中必走货排在最前面，
		必走货的定义如下：截止当天16：00未在规定出发时间前出发的卡货（城运）及晚发24小时以上的普货。
		规定出发时间：根据货物的开单时间、线路运行时刻表计算出每个经受运作的规定发车时间
		（有规定多班卡车的线路规定出发时间以可以兑现的最晚出发时间为准).即规定出发时间 <= 当前时间且规定出发时间 <= 当天16:00的卡货城运
		及规定出发时间-当前时间 >= 24小时的普货。
SR-21	根据输入的运单号，读取库存中运单信息，
		前提必须是满足图3中的查询条件的运单方可添加，
		否则提示“该运单在本部门没有库存中的货物”，
		若输入的运单号不存在，则提示“不存在该运单”；
		本步骤可跳过，也可反复执行
SR-21	当勾选“是否预配交接单时”，可向交接单中添加在途运单，
		所谓在途运单，是指已交接的、到达部门为本部门的在途交接单中的运单；
SR-22	只有交接类型为“集配交接单”时，
		“查询交接运单”界面中的“更改查询方案”按钮才可见；点击该按钮弹出图3界面，
		可在图3列表中添加配载部门，添加配载部门后，查询在途运单时，将可查询出已交接的、在途的，
		提货网点是本部门和此处添加的配载部门所辐射的营业部或者代理网点的运单；
SR-23	保存交接单时，【运单信息列表】中的“实际体积”、“实际重量”、“已配重量”、“已配体积”必须大于0；
SR-24	保存预配交接单时，“装车完成时间”不可输入；保存正式交接单时，“装车完成时间”必填；
SR-25	对于非“整车”交接单：
1、保存时必须校验“出发部门”和“到达部门”之间是否有线路存在，如果不存在该线路，则交接单无法保存；
2、当“到达部门”为偏线代理网点，则“交接类型”必须为外发交接单；当“出发部门”为外场且“到达部门”为外场，
“交接类型”必须为集配交接单，其他情况“交接类型”必须为短配交接单。

1.8	数据元素
1.8.1	交接单基本信息
字段名称 	说明	输入限制	输入提示文本	长度	是否必填	备注
交接类型	交接单的交接类型；	文本		N/A	N/A	
交接单编号	PDA或者FOSS系统自动生成的交接单编号，可作为交接单的唯一标识	N/A		N/A	N/A	
交接时间	交接单生成时的日期	N/A		N/A	N/A	格式为：yyyy-mm-dd hh24:mi:ss
出发部门	交接单的出发部门	N/A		N/A	N/A	
到达部门/外发代理	交接单的到达部门/外发代理	文本		N/A	N/A	
司机电话	司机的手机号码	N/A		N/A	N/A	
司机	负责运输此交接单货物的司机	文本		[0,2]	是	共用选择框，内容读取自司机基础资料
车牌号	负责装载、运输此交接单货物的车辆的车牌号	文本		[0,40]	是	共用选择框，内容读取自车辆基础资料
装车完成时间	此交接单货物装车完成时间	N/A时间		N/A20	N/A是	格式为：yyyy-mm-dd hh24:mi:ss
交接单版本号	交接单的版本编号	N/A		N/A	N/A	
货物类型	交接货物的类型	文本		[4,4]	是	下拉框，内容读取自数据字典
制单人	交接单生成时的操作人	N/A		N/A	N/A	
修改人	该版本的交接单生成时的操作人	N/A		N/A	N/A	
备注	交接单备注	N/A		N/A	N/A	
是否预配交接单	该交接单是否为预配	N/A		N/A	N/A	
1.8.2	运单信息列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
运单号	运单编号	N/A	N/A	N/A	N/A	
运输性质	运输性质	N/A	N/A	N/A	N/A	
已配件数	PDA扫描的货物件数	N/A	N/A	N/A	N/A	
已配重量	PDA扫描的货物重量	N/A	N/A	N/A	N/A	
实际重量	实际交接的货物重量	N/A	N/A	N/A	N/A	
已配体积	PDA扫描的货物体积	N/A	N/A	N/A	N/A	
实际体积	实际交接的货物体积	N/A	N/A	N/A	N/A	
备注	对此货物交接的备注信息	N/A	N/A	N/A	N/A	
货物名称	开单时填写的货物名称	N/A	N/A	N/A	N/A	
包装	货物包装	N/A	N/A	N/A	N/A	
运单备注	开单时填写的备注信息	N/A	N/A	N/A	N/A	
是否为贵重物品	该货物是否为贵重物品	N/A	N/A	N/A	N/A	
收货部门	货物的收货部门	N/A	N/A	N/A	N/A	
到达部门	货物的最终到达部门	N/A	N/A	N/A	N/A	
收货人	货物的收货人	N/A	N/A	N/A	N/A	
目的站	货物的目的站	N/A	N/A	N/A	N/A	
开单件数	开单时填写的货物件数	N/A	N/A	N/A	N/A	
保险价值	货物的保险价值	N/A	N/A	N/A	N/A	
1.8.3	运单扫描明细列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
扫描流水号	每件货物对应的流水号	N/A	N/A	N/A	N/A	
操作(删除)	点击链接删除该件货物	N/A	N/A	N/A	N/A	
1.8.4	交接单统计信息
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
总件数	【运单信息列表】中“已配件数”的总和	N/A	N/A	N/A	N/A	
总票数	【运单信息列表】中运单总数	N/A	N/A	N/A	N/A	
总重量	【运单信息列表】中“实际重量”的总和	N/A	N/A	N/A	N/A	
总体积	【运单信息列表】中“实际体积”的总和	N/A	N/A	N/A	N/A	
1.8.5	待选运单查询条件
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
入库时间	货物在本部门的入库时间	日期		[8,8]	是	格式为：yyyy-mm-dd
运输性质	运单的运输性质	下拉框		[0,40]	否	下拉框，包括：全部、精准卡航、精准汽运，精准城运，，
精准卡航，精准空运，精准汽运(短途)，精准汽运(长途)，汽运偏线，整车。内容读取自数据字典
运输类型	货物的运输类型	下拉框		[0,40]	否	下拉框，包括：全部、汽运、空运，内容读取自数据字典
目的站	货物到达的目的站			[0,40]	否	
外发代理	负责货物派送的外发代理	共用选择框		[0,40]	否	共用选择框，内容读取自外发代理基础资料
运单号	货物开单时生成的运单号	文本		[0,40]	否	

1.8.6	待选运单列表
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
运单号	货物开单时生成的运单号	N/A	N/A	N/A	N/A	
货物名称	货物的名称	N/A	N/A	N/A	N/A	
包装	货物包装	N/A	N/A	N/A	N/A	
预计到达时间	预计到达本部门的时间，由GPS跟踪车辆提供	N/A	N/A	N/A	N/A	
收货部门	该票货物的收货部门	N/A	N/A	N/A	N/A	
体积	货物的体积	N/A	N/A	N/A	N/A	
重量	货物的重量	N/A	N/A	N/A	N/A	
件数	货物件数	N/A	N/A	N/A	N/A	
保险价值	货物的开单保险价值	N/A	N/A	N/A	N/A	
入库时间	货物在本部门入库的日期	N/A	N/A	N/A	N/A	
到达部门	货物的最终到达部门	N/A	N/A	N/A	N/A	
开单日期	货物的开单日期	N/A	N/A	N/A	N/A	
运输性质	货物开单的运输性质	N/A	N/A	N/A	N/A	
运单件数	运单中记录的货物件数	N/A	N/A	N/A	N/A	
运单备注	运单提交时填写的备注	N/A	N/A	N/A	N/A	
运单金额	运单金额	N/A	N/A	N/A	N/A	
是否贵重物品	货物是否为贵重物品	N/A	N/A	N/A	N/A	

1.8.7	已选运单统计信息
字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
总件数	【待选运单列表】中的“件数”之和	N/A	N/A	N/A	N/A	
总体积	【待选运单列表】中的“体积”之和	N/A	N/A	N/A	N/A	
总重量	【待选运单列表】中的“重量”之和	N/A	N/A	N/A	N/A	
总金额	【待选运单列表】中的“运单金额”之和	N/A	N/A	N/A	N/A	

1.9	非功能性需求（可选）
使用量	10000~15000
2012年全网估计用户数	2000~3000
响应要求（如果与全系统要求 不一致的话）	响应与全系统要求一致
使用时间段	全天
高峰使用时段	5:00am~8:30， 18:00~21:00

1.10	接口描述：
接口名称 	对方系统	接口描述
无	
 *   *  修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-04-25	新增	石巍	V0.1
2012-05-31	取消集配的配载查询方案，按走货路径查询；
					查询交接运单界面取消上下两表格移动的方式，
					采取在一个表格中直接勾选；	石巍	V0.2
2012-06-02	修改查询交接运单只查询库存中的货物；	石巍	V0.3
2012-06-11	 取消封签选择框；	周德军	V0.4
2012-10-17	根据RC-571修改	石巍	V1.1
2012-12-04	预配单可加在途运单、增加修改集配查询方案功能	石巍	V1.2
2013-02-23	Issue-1657	石巍	V1.3
2013-02-28	Issue-1842	石巍	V1.4
2013-03-01	Issue-1357	石巍	V1.5
2013-03-15	Issue-2057	石巍	V1.6

1.	SUC-87新增交接单(无PDA)
1.1	相关业务用例
BUC_FOSS_5.30.20_090无PDA-制作预配单
1.2	用例描述
营业员、配载员使用本用例新增交接单
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	
1、	本部门存在有库存中的货物；	
后置条件	
1、	生成交接单；
2、	更新货物的交接状态为“预配交接”(预配)；
3、	货物从本部门出库(非预配)、生成车辆放行需求。	
1.4	操作用户角色
操作用户	描述
营业员	经营部门工作人员，可查询、修改、作废、新增交接单
配载员	运作部门工作人员，可查询、修改、作废、新增交接单
1.5	界面要求
1.5.1	表现方式
Web方式
1.5.2	界面原型-交接单新增主界面
  
图 11：交接单新增主界面
1.5.3	界面描述-交接单新增主界面
	    该界面主要分为五个部分：
	    交接单基本信息、
	    运单信息列表、
	    运单扫描明细列表、
	    交接单统计信息、
	    功能按钮；
1、	交接单基本信息：包括交接类型、
		交接单编号、
		交接日期、
		出发部门、
		到达部门、
		司机电话、
		司机、
		车牌号、
		装车完成时间、
		交接单版本号、
		制单人、
		修改人、
		货物类型、
		是否预配交接单、
		备注、
		是否代理上门接货；
1.1	交接类型：分为集配交接单、
		短配交接单、
		外发交接单，参见业务规则SR-1；
1.2	交接单编号：系统自动生成的交接单编号；
1.3	交接日期：交接单的交接日期；
1.4	出发部门：交接单的出发部门；
1.5	到达部门：交接单的到达部门，共用选择框，
		内容读取自部门基础资料，参见业务规则SR-1；
1.6	司机电话：司机的电话号码，参见业务规则SR-2；
1.7	司机：司机姓名，共用选择框，内容读取自司机基础资料；
1.8	车牌号：负责装载、运输该交接单货物的车辆的车牌号，
		下拉框，内容读取自车辆基础资料；
1.9	装车完成时间：交接单货物装车完成时间；
1.10	交接单版本号：新增时默认为1.0，不可修改；
1.11	制单人：交接单第一次被保存时的系统操作人；
1.12	修改人：当前版本交接单生成时的系统操作人；
1.13	货物类型：货物的类型，分为A类、B类、全部，参见业务规则SR-9；
1.14	是否预配交接单：该交接单是否为预配；
1.15	是否代理上门接货：如果选是则代理直接上门接货；
1.16	备注：交接单备注信息；
2、	运单信息列表：参见数据元素【运单信息列表】；
3、	运单扫描明细列表：参见数据元素【运单扫描明细列表】；
4、	交接单统计信息：包括总票数、总件数、总重量、总体积；
4.1	总票数：【运单信息列表】中运单的总个数；
4.2	总件数：【运单信息列表】中“已配件数”的总和；
4.3	总重量：【运单信息列表】中“实际重量”的总和；
4.4	总体积：【运单信息列表】中“实际体积”的总和；
5、	功能按钮：包括保存、打印、删除运单、添加运单、查询交接运单、；
5.1	保存：保存交接单信息；
5.2	打印：打印交接单；
5.3	删除运单：此按钮位于【运单信息列表】的操作列中，
		点击该图标按钮删除【运单信息列表】中对应的运单；
5.4	添加运单：向【运单信息列表】中添加运单；
5.5	查询交接运单：点击此按钮，弹出查询交接运单界面，参见业务规则SR-3；
1.5.4	界面原型-查询交接运单
1.5.5	界面描述-查询交接运单
该界面分为五个部分：查询条件、查询结果列表、货物扫描明细列表、已选运单统计信息、功能按钮区；
1、	查询条件：包括入库时间、运输性质、运输类型、运单号、外发代理、目的站；
		各控件根据新增交接单主界面的“交接类型”来隐藏或者显示，具体规则参见业务规则SR-4；
	1.1	入库时间：货物在本部门的入库时间，当“交接类型”为短配交接单时，此控件显示；
	1.2	运输性质：开单时选择的产品类别，当“交接类型”为集配交接单时，此控件显示；
	1.3	运输类型：开单时填写的运输类型，
			包括全部、汽运、空运，当“交接类型”为短配交接单时，此控件显示；
	1.4	运单号：货物的运单号；
	1.5	目的站：货物的目的站，当“交接类型”为外发交接单时，此控件显示；
	1.6	外发代理：要外发的代理名称，共用选择框，内容读取自外发代理基础资料；
2、	查询结果列表：具体字段参见数据元素【待选运单列表】；
3、 功能按钮：查询、重置、提交、取消、更改查询方案；
2.1	查询：点击此按钮，查询部门库存货物信息，参见业务规则SR-3；
2.2	重置：点击此按钮重新初始化【待选运单查询条件】；
2.3	提交：点击此按钮，将【待选运单列表】中被勾选的货物提交至新增交接单主界面(图1)的【运单信息列表】，参见业务规则SR-10、SR-11；
2.4	取消：点击此按钮，退出当前界面。
2.42.5	更改查询方案：参见业务规则SR-22。
1.6	操作步骤
1.6.1	新增交接单
序号	基本步骤	相关数据	补充步骤
1	进入交接单新增界面		
2	输入【交接单基本信息】	【交接单基本信息】	参见业务规则SR-1、SR-2、SR-3
3	点击“添加运单”按钮，【运单信息列表】中增加一行，输入运单号，系统自动填充其他字段	【运单信息列表】	参见业务规则SR-21
4	点击【运单扫描明细列表】中某行的“删除”链接		将该货物从【运单扫描明细列表】中删除，同时更新【交接单统计信息】；本步骤可跳过，也可反复执行
5	点击“查询交接运单”按钮，弹出查询交接运单界面(图3)		本步骤可跳过，也可反复执行
6	在查询交接运单界面，输入待选运单查询条件	【待选运单查询条件】	
7	在查询交接运单界面，点击“查询”按钮		
8	在查询交接运单界面，勾选【待选运单列表】中的货物	【待选运单列表】	自动更新【已选运单统计信息】，参见业务规则SR-12
9	在查询交接运单界面，取消勾选【待选运单列表】中的货物	【待选运单列表】	自动更新【已选运单统计信息】，参见业务规则SR-12
10	在查询交接运单界面，点击“提交”按钮		关闭查询交接运单界面，显示新增交接单主界面，参见业务规则SR-10
11	点击“保存”按钮		保存交接单信息
12	点击“打印”按钮		参见打印交接单用例

序号	扩展事件	相关数据	备注
4a	步骤3中，用户可删除某运单的【运单扫描明细列表】中的某条记录，如果只有一条记录，则提示“无法删除，请直接删除该运单”		参见业务规则SR-8
5a	步骤5中，如果“交接类型”为外发交接单，且“外发代理”为空，则不弹出图3界面，告知用户“外发代理”不能为空。		提示内容：外发代理不能为空 

1.7	业务规则
序号	描述
SR-1	当“交接类型”为“外发交接单”时，“到达部门”控件隐藏、显示“外发代理” 控件，该控件为共用选择框，内容读取自外发代理基础资料；
SR-2	【交接单基本信息】中“司机电话”不可输入，当用户输入“司机”后，自动关联司机基础资料读取司机电话并填充；
SR-3	查询交接运单界面(图3)中，“入库时间”的截止日期默认为当前日期，日期差为20天；点击“查询”按钮后，查询所有符合查询条件的、本部门库存中的、“到达部门”或“外发代理”为其下一交接部门的货物；
1、	当“交接类型”为“集配交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、运输性质、运单号；只查询“运输性质”为精准卡航（长途）或精准汽运（长途）的货物；
2、	当“交接类型”为“短配交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、运输类型、运单号；
3、	当“交接类型”为“外发交接单”时，点击“查询交接运单”弹出图3界面，查询条件包括：入库时间、外发代理、运单号；只查询“提货网点”为代理网点的货物；
SR-4	【运单信息列表】中“实际体积”、“实际重量”不可修改，“备注”字段可修改，“实际体积”、“实际重量”分别默认等于“已配体积”、“已配重量”；
SR-5	当在【运单信息列表】中增加一条运单后，【交接单统计信息】中“总件数”加上该运单的“已配件数”、“总票数”加1、“总重量”和“总体积”分别加上该运单的“实际重量”和“实际体积”；
当在【运单信息列表】中删除一条运单后，【交接单统计信息】中“总件数”减去该运单的“已配件数”、“总票数”减1、“总重量”和“总体积”分别减去该运单的“实际重量”和“实际体积”；
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo;
import com.deppon.foss.util.define.FossConstants;

/** 
 * 交接单模块action类
 * @author 045923-foss-shiwei
 * @date 2012-10-12 上午11:14:38
 */
public class HandOverBillAction extends AbstractAction{

	private static final long serialVersionUID = 9166752079380663524L;
	
	/**
	 * 定义service
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 交接单Vo
	 */
	private HandOverBillVo handOverBillVo = new HandOverBillVo();
	
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream wayBillExcelStream;  
	
	/**
	 * 导出交接单
	 */
	private InputStream handOverBillExcelStream;
	
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String excelFileName;  
	/**
	 * 综合部门service，传入“到达部门code”，判断该部门是否为营业部、外场
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 查询交接单页面跳转，同时获取大部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午4:16:12
	 */
	@JSON
	public String handOverBillQueryIndex(){
		try{
			String superOrgCode = handOverBillService.querySuperiorOrgCode();
			handOverBillVo.setSuperOrgCode(superOrgCode);
		}catch(BusinessException e){
			handOverBillVo.setErrorMessage(e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}
	
	

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 新增交接单页面跳转action，同时返回交接单编号和出发部门名称（大部门）
	 * @author 045923-foss-shiwei
	 * @date 2012-11-22 上午10:17:34
	 */
	@JSON
	public String handOverBillAddNewIndex(){
		try{
			//生成要显示的交接单号
			String[] info = handOverBillService.showHandOverBillNo();
			//zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
			/*
			 List<HandOverBillDetailEntity> handOverBillDetailEntity = handOverBillService.queryHandOverBillDetailByNo(info[0]);
			String origOrgCode = handOverBillDetailEntity.get(0).getOrigOrgCode();
			*/
			//获取当前大部门的code
			/*2016年12月20日15:39:33 sonar 311396 更改，返回值没有使用 
			/*String superOrgCode = */
			handOverBillService.querySuperiorOrgCode();
			BigDecimal expressConvertParameter=new BigDecimal(1);//handOverBillService.queryExpressConverParameter(superOrgCode);
			//页面加载的同时传回前台要显示的交接单号
			handOverBillVo.setHandOverBillNo(info[0]);
			handOverBillVo.setDepartOrgName(info[1]);
			handOverBillVo.setBeSalesDept(info[2]);
			handOverBillVo.setSuperOrgCode(info[LoadConstants.SONAR_NUMBER_3]);
			handOverBillVo.setBeDivision(info[LoadConstants.SONAR_NUMBER_4]);
			handOverBillVo.setExpressConvertParameter(expressConvertParameter);
		}catch(BusinessException e){
			handOverBillVo.setErrorMessage(e.getMessage());
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 手工修改交接单界面跳转
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午7:37:47
	 */
	@JSON
	public String handOverBillModifyIndex(){
		try{
			//获取当前大部门的code
			String superOrgCode = handOverBillService.querySuperiorOrgCode();
			//当前部门是否为营业部
			String beSalesDept = handOverBillService.queryOrgBeSalesDepartment(superOrgCode);
			String[] info = handOverBillService.showHandOverBillNo();
			// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
			// List<HandOverBillDetailEntity> handOverBillDetailEntity = handOverBillService.queryHandOverBillDetailByNo(info[0]);
			// String origOrgCode = handOverBillDetailEntity.get(0).getOrigOrgCode();
			BigDecimal expressConvertParameter=handOverBillService.queryExpressConverParameter(superOrgCode);
			handOverBillVo.setBeSalesDept(beSalesDept);
			handOverBillVo.setBeDivision(info[LoadConstants.SONAR_NUMBER_4]);
			handOverBillVo.setExpressConvertParameter(expressConvertParameter);
		}catch(BusinessException e){
			handOverBillVo.setErrorMessage(e.getMessage());
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 查询交接运单，获取部门库存运单action
	 * @author 045923-foss-shiwei
	 * @date 2012-10-11 上午11:15:11
	 */
	@JSON
	public String queryWaybillStockList(){
		//获取查询条件dto
		QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto = handOverBillVo.getQueryWaybillForHandOverBillDto();
		/**
		 * 获取到达部门集合
		 * 332219
		 * 2016-09-19
		 */
		try{
			List<String> arriveDeptList = handOverBillService.queryArriveDeptList(handOverBillVo);
			//从新保存到查询交接运单之查询条件的实体中
			queryWaybillForHandOverBillDto.setArriveDeptList(arriveDeptList);
			//获取库存运单，返回前台
			handOverBillVo.setWaybillStockList(handOverBillService.queryWaybillStockListAndSerialNoStockList(queryWaybillForHandOverBillDto,this.getLimit(),this.getStart()));
			//获取库存运单的条数，分页
			Long totalCount = handOverBillService.getWaybillStockCount(queryWaybillForHandOverBillDto);
			//获取库存运单的条数，分页
			this.setTotalCount(totalCount);
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 通过运单号快速添加，获取运单库存和其下流水号库存
	 * @author 045923-foss-shiwei
	 * @date 2013-3-26 上午8:11:28
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryWaybillStockByWaybillNo(){
		//获取查询条件dto
		QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto = handOverBillVo.getQueryWaybillForHandOverBillDto();
		try{
			Map<String,Object> map = handOverBillService.queryWaybillStockByWaybillNo(queryWaybillForHandOverBillDto);
			//返回运单库存
			handOverBillVo.setWaybillStock((HandOverBillDetailEntity)map.get("waybillStock"));
			//返回流水号库存列表
			handOverBillVo.setSerialNoStockList((List<SerialNoStockEntity>)map.get("serialNoList"));
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询交接运单，获取部门在途运单action
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午2:51:22
	 */
	@JSON
	public String queryEnRouteWaybillList(){
		//获取查询条件dto
		QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillConditionDto = handOverBillVo.getQueryWaybillForHandOverBillDto();
		
		//设置到达部门、配载部门所辐射的营业部list
		List<String> arrivedDeptList = handOverBillService.getArrivedDeptListByDto(queryWaybillForHandOverBillConditionDto);
		//去除list中的null by 360903 2016年12月13日 09:52:29
		 Collection nuCon = new Vector(); 
		 nuCon.add(null); 
		 arrivedDeptList.removeAll(nuCon);
		queryWaybillForHandOverBillConditionDto.setArriveDeptList(arrivedDeptList);
 
		
		//返回在途运单list
		handOverBillVo.setWaybillStockList(handOverBillService.queryEnRouteWaybillList(queryWaybillForHandOverBillConditionDto,this.getLimit(), this.getStart()));
		//获取在途运单的条数
		Long totalCount = handOverBillService.getEnRouteWaybillCount(queryWaybillForHandOverBillConditionDto);
		//用于分页
		this.setTotalCount(totalCount);
		//返回success
		return returnSuccess();
	}

	/**
	 * 查询交接运单，获取部门库存运单对应流水号的action
	 * @author 045923-foss-shiwei
	 * @date 2012-10-11 上午11:15:37
	 */
	@JSON
	public String querySerialNoStockList(){
		//获取查询条件
		QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto = handOverBillVo.getQuerySerialNoListForWaybillConditionDto();
		//获取库存运单号下的流水号
		handOverBillVo.setSerialNoStockList(handOverBillService.querySerialNoStockList(querySerialNoListForWaybillConditionDto));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 新增交接单，保存提交的action
	 * @author 045923-foss-shiwei
	 * @date 2012-10-16 上午11:36:16
	 */
	@JSON
	public String saveHandOverBill(){
		//获取前台传入的待新增的交接单dto
		NewHandOverBillDto newHandOverBillDto = handOverBillVo.getNewHandOverBillDto();
		//try catch块
		try{
			//保存时重新生成交接单号
			String handOverBillNo = handOverBillService.saveHandOverBill(newHandOverBillDto);
			//生成的交接单号返回前台
			handOverBillVo.setHandOverBillNo(handOverBillNo);
			LOGGER.error("交接单号：" + handOverBillNo + "生成完毕，action返回处理成功");
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 查询交接单之action
	 * @author 045923-foss-shiwei
	 * @date 2012-10-23 下午2:59:55
	 */
	@JSON
	public String queryHandOverBillList(){
		//从VO中获取查询条件
		QueryHandOverBillConditionDto queryHandOverBillConditionDto = handOverBillVo.getQueryHandOverBillConditionDto();
		//调用service得到查询结果
		List<QueryHandOverBillDto> queriedHandOverBillList = handOverBillService.queryHandOverBillList(handOverBillVo.getQueryHandOverBillConditionDto(),this.getLimit(),this.getStart());
		//放入VO中
		handOverBillVo.setHandOverBillList(queriedHandOverBillList);
		//总条数
		Long totalCount = handOverBillService.getHandOverBillListCount(queryHandOverBillConditionDto);
		//返回前台，分页
		this.setTotalCount(totalCount);
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 根据交接单号获取交接单实体
	 * @author 045923-foss-shiwei
	 * @date 2012-11-12 上午9:13:25
	 */
	@JSON
	public String queryHandOverBillByNo(){
		//获取传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
		String handOverType = handOverBillVo.getHandOverType();
		if(StringUtils.equals(handOverType, "PACKAGE_HANDOVER")){
			//返回获取的正单交接单实体
			HandOverBillEntity billEntity = handOverBillService.queryAirHandOverBillByNo(handOverBillNo);
			handOverBillVo.setBaseEntity(billEntity);
			String beSalesDept = handOverBillService.queryOrgBeSalesDepartment(billEntity.getDepartDeptCode());
			handOverBillVo.setBeSalesDept(beSalesDept);
		}else{
			//返回获取的交接单实体
			HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByNo(handOverBillNo);
			handOverBillVo.setBaseEntity(billEntity);
			String beSalesDept = handOverBillService.queryOrgBeSalesDepartment(billEntity.getDepartDeptCode());
			handOverBillVo.setBeSalesDept(beSalesDept);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 查看交接单详情，根据交接单号获取运单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:23:15
	 */
	@JSON
	public String queryHandOverBillDetailByNo(){
		//从Vo中获取前台传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
		String handOverType=handOverBillVo.getHandOverType();
		//'PACKAGE_HANDOVER'  '空运快递交接单';
		if(StringUtil.equals(handOverType, "PACKAGE_HANDOVER")){
			//返回正交单运单列表
			handOverBillVo.setWaybillStockList(handOverBillService.queryAirHandOverBillDetailByNo(handOverBillNo));	
		}else{
			//返回运单列表
			handOverBillVo.setWaybillStockList(handOverBillService.queryHandOverBillDetailByHandNo(handOverBillNo));	
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 查看交接单详情(PDA)，根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午6:24:38
	 */
	@JSON
	public String queryWaybillDetailByNos(){
		//获取传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
		//获取传入的运单号
		String waybillNo = handOverBillVo.getWaybillNo();
		//返回交接单中运单下的流水号列表
		handOverBillVo.setPdaSerialNoList(handOverBillService.queryWaybillDetailByNos(handOverBillNo, waybillNo));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 根据交接单号，获取交接单号修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午10:18:09
	 */
	@JSON
	public String queryHandOverBillOptLogByNo(){
		//获取传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
		//获取操作日志列表
		handOverBillVo.setHandOverBillOptLogList(handOverBillService.queryHandOverBillOptLogByNo(handOverBillNo,this.getLimit(),this.getStart()));
		//获取日志总条数
		Long totalCount = handOverBillService.getHandOverBillOptLogCount(handOverBillNo);
		//分页
		this.setTotalCount(totalCount);
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 根据交接单号作废交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 上午11:06:39
	 */
	@JSON
	public String cancelHandOverBillByNo(){
		//获取传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
		//try catch语句块
		try{
			//调用service，作废交接单
			handOverBillService.cancelHandOverBill(handOverBillNo);
		}catch(BusinessException e){
			//返回异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 交接单详情界面，导出运单列表
	 * @author 045923-foss-shiwei
	 * @throws UnsupportedEncodingException 
	 * @date 2012-10-26 下午2:23:54
	 */
	@SuppressWarnings("rawtypes")
	public String exportWayBillExcel(){
		//获取传入的交接单号
		String handOverBillNo = handOverBillVo.getHandOverBillNo();
	    String handOverType = handOverBillVo.getHandOverType();
		//调用service，获取文件名、输入流
		List list = null;
		try{
			//调用service
			list = handOverBillService.getWayBillExcelInputStream(handOverBillNo,handOverType);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//文件名
		excelFileName = (String)list.get(0);
		//文件流
		wayBillExcelStream = (InputStream) list.get(1);
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 查询交接单界面，导出交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:02:26
	 */
	@SuppressWarnings("rawtypes")
	public String exportHandOverBillExcel(){
		//从VO中获取查询条件
		QueryHandOverBillConditionDto queryHandOverBillConditionDto = handOverBillVo.getQueryHandOverBillConditionDto();
		//调用service，获取文件名、输入流
		List list = null;
		try{
			//调用service
			list = handOverBillService.getHandOverBillExcelInputStream(queryHandOverBillConditionDto);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//文件名
		excelFileName = (String)list.get(0);
		//文件流
		handOverBillExcelStream = (InputStream) list.get(1);
		return returnSuccess();
	}
	
	/**
	 * 根据交接单号获取其下所有流水号list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-30 上午11:08:48
	 */
	@JSON
	public String querySerialNoListByHandOverBillNo(){
		try {
			//空运快递交接单
			if(StringUtils.equals("PACKAGE_HANDOVER", handOverBillVo.getHandOverType())){
				handOverBillVo.setSerialNoList(handOverBillService.getAirHandOverBillSerialNoDetailsByWayBillNo(handOverBillVo.getWaybillNo(),handOverBillVo.getHandOverBillNo()));
			}else{
			//传入运单号不为空，则为查询某运单号下的流水号列表
			if(handOverBillVo.getWaybillNo() != null){
				//若传入运单号不为空，则为获取交接单下某一运单号下的流水号
				handOverBillVo.setSerialNoList(handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(handOverBillVo.getWaybillNo(), handOverBillVo.getHandOverBillNo()));
			}else{//传入运单号为空，则忽略运单号，直接查询某交接单下所有流水号
				handOverBillVo.setSerialNoList(handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillVo.getHandOverBillNo()));
			}
			}
			//返回success
			return returnSuccess();
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnError(e);
		}
	
	}
	
	/**
	 * 查询交接运单，查询在途运单后，双击某运单得到流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午5:16:53
	 */
	@JSON
	public String queryEnRouteSerialNoListByNos(){
		//获取在途运单下的流水号
		handOverBillVo.setSerialNoList(handOverBillService.queryEnRouteSerialNoListByNos(handOverBillVo.getWaybillNo(), handOverBillVo.getHandOverBillNo()));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 修改交接单action
	 * @author 045923-foss-shiwei
	 * @date 2012-10-31 下午2:38:40
	 */
	@JSON
	public String updateHandOverBill(){
		try{
			//调用service，更新交接单信息
			handOverBillService.updateHandOverBill(handOverBillVo.getUpdateHandOverBillDto());
			//try catch语句块
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 传入到达部门code，返回获取的外场实体，若不是外场，则返回null
	 * 用于根据外场是否支持自动分拣来控制“货物类型”是否可编辑
	 * @author 045923-foss-shiwei
	 * @date 2012-12-5 下午9:23:40
	 */
	@JSON
	public String queryOutfieldByCode(){
		//获取前台传入的到达部门code
		String arriveDeptCode = handOverBillVo.getArriveDeptCode();
		//返回外场信息
		//如果不是外场，则返回null
		handOverBillVo.setOutfield(handOverBillService.queryOutfieldByOrgCode(arriveDeptCode));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 传入车牌号，获取司机信息，包括司机姓名、工号、电话
	 * @author 045923-foss-shiwei
	 * @date 2013-1-24 下午2:07:32
	 */
	@JSON
	public String queryDriverInfoByVehicleNo(){
		//获取传入的车牌号
		String vehicleNo = handOverBillVo.getVehicleNo();
		String handOverType=handOverBillVo.getHandOverType();//交接类型
		String beDivision=handOverBillVo.getBeDivision();//是否分部
		String orgCode = handOverBillService.querySuperiorOrgCode(); 
		OrgAdministrativeInfoEntity orgEntity = this.orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		try{
			if(StringUtils.equals(handOverType,LoadConstants.HANDOVER_TYPE_SHORT_DISTANCE) ){
				//虚拟外场出发的短途交接单
				if(StringUtils.equals("Y", beDivision)){
					ExpressVehiclesEntity driverEntity=handOverBillService.queryDivisionDriverInfo(vehicleNo);
					DriverBaseDto driverInfo=new DriverBaseDto();
					driverInfo.setDriverCode(driverEntity.getEmpCode());
					driverInfo.setDriverName(driverEntity.getEmpName());
					driverInfo.setDriverPhone(driverEntity.getMobilePhone());
					handOverBillVo.setDriverInfo(driverInfo);
				
					
				//外场出发的短途交接单
				}else if(orgEntity!=null&&StringUtils.equals(orgEntity.getTransferCenter(), FossConstants.YES)
						&&!StringUtils.equals("Y", beDivision)){
				
					ExpressVehiclesEntity driverEntity=handOverBillService.queryDivisionDriverInfo(vehicleNo);
					if(driverEntity!=null){
						DriverBaseDto driverInfo=new DriverBaseDto();
						driverInfo.setDriverCode(driverEntity.getEmpCode());
						driverInfo.setDriverName(driverEntity.getEmpName());
						driverInfo.setDriverPhone(driverEntity.getMobilePhone());
						handOverBillVo.setDriverInfo(driverInfo);
					}else{
						//调用service，获取车牌关联的司机信息
						DriverBaseDto driverInfo = handOverBillService.queryDriverInfoByVehicleNo(vehicleNo);
						//返回司机信息
						handOverBillVo.setDriverInfo(driverInfo);
					}
				//营业部出发的交接单
				}else{
				
					//调用service，获取车牌关联的司机信息
					DriverBaseDto driverInfo = handOverBillService.queryDriverInfoByVehicleNo(vehicleNo);
					//返回司机信息
					handOverBillVo.setDriverInfo(driverInfo);
				}
			}else{
				//调用service，获取车牌关联的司机信息
				DriverBaseDto driverInfo = handOverBillService.queryDriverInfoByVehicleNo(vehicleNo);
				//返回司机信息
				handOverBillVo.setDriverInfo(driverInfo);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage()+"----"+e.getStackTrace());
			//此处忽略一切异常
			return returnSuccess();
		}
		//返回success
		return returnSuccess();
	}

	/**
	 * 用于验证选择的交接单在该车牌下是否还有其他的没选择,以及交接单状态是否作废
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午4:00:01
	 */
	@JSON
	public String checkPrintHandOverBill(){
		//打印交接单时校验是否已选中改车牌下所有的交接单
		Long result = handOverBillService.checkPrintHandOverBill(handOverBillVo);
		//返回获取的交接单实体
		HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByNo(handOverBillVo.getHandOverBillNos().get(0));
		//修复BUG-48480新增交接单后，作废，在新增界面，点击打印系统报错
		if(billEntity == null){
			handOverBillVo.setErrorMessage("CANCEL");
		}else{
			handOverBillVo.setErrorMessage("CANUSE");
		}
		//返回结果
		handOverBillVo.setCheckPrintHandOverBillRestlt(result);
		////返回success
		return returnSuccess();
	}

	/****
	 * 根据交接单号找配载单号
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:35:28
	 */
	@JSON
	public String getVehicleassembleNoByHandoverNo(){
		if(handOverBillVo.getHandOverBillNo() == null) {
			////返回success
			return returnSuccess();
		}
		//根据交接单号找配载单号
		String vehicleassembleNo = handOverBillService.getVehicleassembleNoByHandoverNo(handOverBillVo.getHandOverBillNo());
		//配载单号
		handOverBillVo.setVehicleassembleNo(vehicleassembleNo);
		////返回success
		return returnSuccess();
	}
	
	/**
	 * 检查交接单在租车标记表的预提状态，如果为预提中或者已预提则不允许修改
	 * @author 205109-foss-zenghaibin
	 * @date 2014-08-14 上午08:27:39
	 */
	@JSON
	public String queryHoldingstate(){
		try{
			String handOverBillNo = handOverBillVo.getHandOverBillNo();//获取交接单号
			handOverBillVo.setHoldingState(handOverBillService.queryHoldingState(handOverBillNo));
			return returnSuccess();
		}catch(BusinessException e){
			
			return returnError(e.toString());
		}
		
		
	}
	
	/**
	 * 获取三级产品类型
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 上午10:03:17
	 */
	public String queryProductList(){
		List<BaseDataDictDto> productList  = handOverBillService.queryProductList();
		handOverBillVo.setProductList(productList);
		return returnSuccess();
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setHandOverBillVo(HandOverBillVo handOverBillVo) {
		this.handOverBillVo = handOverBillVo;
	}
	
	public HandOverBillVo getHandOverBillVo() {
		return handOverBillVo;
	}

	public InputStream getWayBillExcelStream() {
		return wayBillExcelStream;
	}

	public void setWayBillExcelStream(InputStream wayBillExcelStream) {
		this.wayBillExcelStream = wayBillExcelStream;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
	public InputStream getHandOverBillExcelStream() {
		return handOverBillExcelStream;
	}
	public void setHandOverBillExcelStream(InputStream handOverBillExcelStream) {
		this.handOverBillExcelStream = handOverBillExcelStream;
	}
	
}