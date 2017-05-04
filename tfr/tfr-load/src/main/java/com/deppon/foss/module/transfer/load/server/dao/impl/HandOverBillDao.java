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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/HandOverBillDao.java
 *  
 *  FILE NAME          :HandOverBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
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
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandoverBillPrintRecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HoldingStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.HandOverBillVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: HandOverBillDao
 * @author: 045923-foss-shiwei
 * @description: 交接单dao实现类
 * @date: 2012-10-11 上午10:43:04
 * 
 */
public class HandOverBillDao extends iBatis3DaoImpl implements IHandOverBillDao{

	/**命名空间常量*/
	private static final String NAMESPACE = "foss.load.handoverbill.";
	/**记录日志*/
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 用于获取小部门所属的大部门
	 */
	private ILoadService loadService;
	
	/**
	 * @param loadService the loadService to set
	 */
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	/**
	 * @author 045923-foss-shiwei
	 * @return List<WaybillStockEntity>
	 * @date 2012-10-11
	 * @description 查询交接运单，获取部门库存运单dao
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<HandOverBillDetailEntity> queryWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockList",queyWaybillForHandOverBillDto,rowBounds);
	}
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取库存运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-3-25 下午6:39:48
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockByWaybillNo",queyWaybillForHandOverBillDto);
	}
	/**
	 * 查询交接运单，获取部门在途运单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午2:58:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryEnRouteWaybillList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryEnRouteWaybillList",queyWaybillForHandOverBillDto,rowBounds);
	}
	
	/**
	 * 查询交接运单，查询在途运单时根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午5:09:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillSerialNoDetailEntity> queryEnRouteSerialNoListByNos(
			String wayBillNo, String handOverBillNo) {
		//查询参数
		HashMap<String,String> map = new HashMap<String,String>();
		//交接单号
		map.put("handOverBillNo", handOverBillNo);
		//运单号
		map.put("waybillNo", wayBillNo);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryEnRouteSerialNoStockList",map);
	}

	/**
	 * @author 045923-foss-shiwei
	 * @date 2012-10-11 下午3:36:13
	 * @param waybillNo运单号，storageDept库存部门
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#querySerialNoStockList(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNoStockList",querySerialNoListForWaybillConditionDto);
	}
	
	/**
	 * @author 045923-foss-shiwei
	 * @date 2013-03-23 上午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SerialNoStockEntity> queryJoinCarSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryJoinCarSerialNoStockList",querySerialNoListForWaybillConditionDto);
	}
	
	
	/**
	 * 查询交接运单，获取在途运单总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-6 下午3:04:43
	 */
	@Override
	public Long getEnRouteWaybillCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getEnRouteWaybillCount",queyWaybillForHandOverBillDto);
	}
	
	/**
	 * 查询交接运单界面，分页时获取总条数
	 * @author 045923-dp-shiwei
	 * @date 2012-10-14 下午3:42:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#getWaybillStockCount()
	 */
	@Override
	public Long getWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getWaybillStockCount",queyWaybillForHandOverBillDto);
	}

	/**
	 * 新增时，保存交接单数据
	 * @author 045923-dp-shiwei
	 * @date 2012-10-19 上午10:46:48
	 * @param handOverBillEntity交接单基本信息实体
	 				   unSavedWaybillStockList运单库存实体list
	 				   unsavedSerialNoStockList流水号库存实体list
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#saveHandOverBill(com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity, java.util.List, java.util.List)
	 */
	@Override
	public boolean saveHandOverBill(HandOverBillEntity handOverBillEntity,List<HandOverBillDetailEntity> unSavedWaybillStockList,List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList) {
		
		//保存交接单基本信息
		getSqlSession().insert(NAMESPACE + "saveHandOverBillBasicInfo", handOverBillEntity);
		//批量插入运单、流水号
		Connection con = null;
		PreparedStatement waybillPs = null;
		PreparedStatement ps = null;
		try {
			//获取连接
			con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
			con.setAutoCommit(false);
			
			LOGGER.error("交接单：" + handOverBillEntity.getHandOverBillNo() + "开始插入运单！");
			//插入运单
			waybillPs = con.prepareStatement("insert into tfr.t_opt_handoverbill_detail (ID,HANDOVER_NO,WAYBILL_NO,TRANSPORT_TYPE,HANDOVER_GOODS_QTY,HANDOVER_WEIGHT,ACTUAL_WEIGHT,HANDOVER_VOLUME,ACTUAL_VOLUME,NOTES,GOODS_NAME,PACKING,WAYBILL_NOTES,BE_VALUABLE,RECEIVE_ORG_NAME,REACH_ORG_NAME,RECEIVER_NAME,DEST_REGION_NAME,GOODS_QTY,WAYBILL_FEE,DECLARATION_VALUE,CURRENCY_CODE,CREATE_TIME,MODIFY_TIME,ORIG_ORG_CODE,HANDOVER_TYPE,COD_AMOUNT,BE_JOIN_CAR,BE_FAST_GOODS,TRANSPORT_TYPE_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(HandOverBillDetailEntity waybill : unSavedWaybillStockList){
				waybillPs.setString(1, waybill.getId());
				waybillPs.setString(2, waybill.getHandOverBillNo());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_3, waybill.getWaybillNo());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_4, waybill.getTransProperty());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_5, waybill.getPieces());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_6, waybill.getWeight());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_7, waybill.getWeightAc());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_8, waybill.getCubage());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_9, waybill.getCubageAc());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_10, waybill.getNote());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_11, waybill.getGoodsName());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_12, waybill.getPacking());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_13, waybill.getWaybillNote());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_14, waybill.getIsPreciousGoods());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_15, waybill.getReceiveOrgName());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_16, waybill.getArriveDept());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_17,waybill.getConsignee());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_18, waybill.getDestination());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_19, waybill.getWaybillPieces());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_20, waybill.getWaybillFee());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_21, waybill.getInsuranceValue());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_22, waybill.getCurrencyCode());
				waybillPs.setTimestamp(LoadConstants.SONAR_NUMBER_23, new Timestamp(waybill.getCreateDate().getTime()));
				waybillPs.setTimestamp(LoadConstants.SONAR_NUMBER_24, new Timestamp(waybill.getCreateDate().getTime()));
				waybillPs.setString(LoadConstants.SONAR_NUMBER_25, waybill.getOrigOrgCode());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_26, waybill.getHandOverType());
				waybillPs.setBigDecimal(LoadConstants.SONAR_NUMBER_27, waybill.getCodAmount());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_28, waybill.getIsJoinCar());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_29, waybill.getIsFastGoods());
				waybillPs.setString(LoadConstants.SONAR_NUMBER_30, waybill.getTransPropertyCode());
				waybillPs.addBatch();
			}
			waybillPs.executeBatch();
			LOGGER.error("交接单：" + handOverBillEntity.getHandOverBillNo() + "插入运单结束！");
			
			LOGGER.error("交接单：" + handOverBillEntity.getHandOverBillNo() + "开始插入流水号！");
			//插入流水号
			ps = con.prepareStatement("insert into tfr.t_opt_handoverbill_serialno (ID,SERIAL_NO,CREATE_TIME,ORIG_ORG_CODE,HANDOVERBILL_NO,WAYBILL_NO,WEIGHT,VOLUMN)values(?,?,?,?,?,?,?,?)");
			for(HandOverBillSerialNoDetailEntity serialNo : unsavedSerialNoStockList){
				//ID
				ps.setString(1,serialNo.getId());
				//流水号
				ps.setString(2, serialNo.getSerialNo());
				//制单日期
				ps.setTimestamp(LoadConstants.SONAR_NUMBER_3, new Timestamp(serialNo.getHandOverTime().getTime()));
				//出发部门code
				ps.setString(LoadConstants.SONAR_NUMBER_4, serialNo.getOrigOrgCode());
				//交接单号
				ps.setString(LoadConstants.SONAR_NUMBER_5, serialNo.getHandOverBillNo());
				//运单号
				ps.setString(LoadConstants.SONAR_NUMBER_6, serialNo.getWaybillNo());
				//重量
				ps.setBigDecimal(LoadConstants.SONAR_NUMBER_7, serialNo.getWeight());
				//体积
				ps.setBigDecimal(LoadConstants.SONAR_NUMBER_8, serialNo.getVolumn());
				//批处理
				ps.addBatch();
			}
			//批量插入
			ps.executeBatch();
			LOGGER.error("交接单：" + handOverBillEntity.getHandOverBillNo() + "插入流水号结束！");
			
			//事务提交
			con.commit();
			LOGGER.error("交接单：" + handOverBillEntity.getHandOverBillNo() + "新增事务提交！");
		} catch (Exception e) {
			try {
				//回滚
				con.rollback();
			} catch (SQLException e1) {
				//记录异常日志
				LOGGER.error("事务回滚发生异常：" + e1.getMessage());
				throw new TfrBusinessException("数据库发生异常！");
			}
			//记录异常日志
			LOGGER.error("插入交接单流水号明细时异常：" + e.getMessage());
			throw new TfrBusinessException("数据库发生异常！");
		}finally{
			//关闭ps
			try {
				if(ps != null){
					ps.close();
				}
				if(waybillPs != null){
					waybillPs.close();
				}
			} catch (SQLException e) {
				//记录ps关闭异常信息
				try {
					con.rollback();
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new TfrBusinessException("数据库发生异常！");
				}
				LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				throw new TfrBusinessException("数据库发生异常！");
			}finally{
				if(con != null){
					try {
						//关闭connection
						con.close();
					} catch (SQLException e) {
						try {
							con.rollback();
						} catch (SQLException e1) {
							//记录异常日志
							LOGGER.error("事务回滚发生异常：" + e1.getMessage());
							throw new TfrBusinessException("数据库发生异常！");
						}
						//记录关闭con异常日志
						LOGGER.error("关闭connection异常：" + e.getMessage());
						throw new TfrBusinessException("数据库发生异常！");
					}
				}
			}
		}
		return true;                                                                                        
	}
	
	/**
	 * 查询交接单dao方法
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:17:47
	 * @param queryHandOverBillConditionDto查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillList(QueryHandOverBillConditionDto queryHandOverBillConditionDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		List<QueryHandOverBillDto> queryHandOverBillDto = new ArrayList<QueryHandOverBillDto>();
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=loadService.queryTCOrgByOrgCode(orgCode);
		String currentOrgCode= queryHandOverBillConditionDto.getCurrentDept();
		
		if(orgAdministrativeInfoEntity != null){
			LOGGER.info("登陆部门："+orgCode+"正交接单对应外场"+orgAdministrativeInfoEntity.getCode()+"其他交接单转换的部门"+currentOrgCode);
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(外场)！##########");
		}
		//查询 所有的 交接类型
		if(StringUtils.equals(queryHandOverBillConditionDto.getHandOverType(), "ALL")
		){
			if(orgAdministrativeInfoEntity != null){
			queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
			}
			//查询正交接单
			queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillList",queryHandOverBillConditionDto,rowBounds);
			queryHandOverBillConditionDto.setCurrentDept(currentOrgCode);
			if(CollectionUtils.isEmpty(queryHandOverBillDto)){
				queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto,rowBounds);
			}else{
				if((limit-queryHandOverBillDto.size())>0){
					rowBounds = new RowBounds(start, limit-queryHandOverBillDto.size());
					queryHandOverBillDto.addAll(this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto,rowBounds));
				}
				
			}	
			//快递空运交接单EXPRESS_AIR_HANDOVER 正单信息(空运)
		}else if(StringUtils.equals(queryHandOverBillConditionDto.getHandOverType(), "EXPRESS_AIR_HANDOVER")){
			if(orgAdministrativeInfoEntity != null){
			queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
			}
			//查询正交接单
			queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillList",queryHandOverBillConditionDto,rowBounds);
		}else{
			//
			queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto,rowBounds);
		}
		return queryHandOverBillDto;
	}
	/**
	 * 查询交接单，无分页，用于导出
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:27:20
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillListNoPaging(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListNoPaging(QueryHandOverBillConditionDto queryHandOverBillConditionDto){
		//返回查询结果
				List<QueryHandOverBillDto> queryHandOverBillDto = new ArrayList<QueryHandOverBillDto>();
				String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=loadService.queryTCOrgByOrgCode(orgCode);
				String currentOrgCode= queryHandOverBillConditionDto.getCurrentDept();
				
				if(orgAdministrativeInfoEntity != null){
					LOGGER.info("登陆部门："+orgCode+"正交接单对应外场"+orgAdministrativeInfoEntity.getCode()+"其他交接单转换的部门"+currentOrgCode);
				}else{
					//获取上级部门失败
					LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(外场)！##########");
				}
				//查询 所有的 交接类型
				if(StringUtils.equals(queryHandOverBillConditionDto.getHandOverType(), "ALL")
				){
					if(orgAdministrativeInfoEntity != null){
					//正交接单，查询部门 直接为对应外场，不管什么空运总调 和营业部
					queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
					}
					//查询正交接单
					queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillList",queryHandOverBillConditionDto);
					//其他交接单 查询部门 返回原来的规则
					queryHandOverBillConditionDto.setCurrentDept(currentOrgCode);
					queryHandOverBillDto.addAll(this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto));
					//快递空运交接单EXPRESS_AIR_HANDOVER 正单信息(空运)
				}else if(StringUtils.equals(queryHandOverBillConditionDto.getHandOverType(), "EXPRESS_AIR_HANDOVER")){
					if(orgAdministrativeInfoEntity != null){
					//正交接单，查询部门 直接为对应外场，不管什么空运总调 和营业部
					queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
					}
					//查询正交接单
					queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillList",queryHandOverBillConditionDto);
				}else{
					//
					queryHandOverBillDto=this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto);
				}
				return queryHandOverBillDto;
		//返回查询结果
	//	return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillList",queryHandOverBillConditionDto);
	}
	
	/**
	 * 获取交接单的总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 上午9:58:15
	 * @param queryHandOverBillConditionDto查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#getHandOverBillListCount(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@Override
	public Long getHandOverBillListCount(QueryHandOverBillConditionDto queryHandOverBillConditionDto){
		Long count=0L;
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=loadService.queryTCOrgByOrgCode(orgCode);
		String currentOrgCode= queryHandOverBillConditionDto.getCurrentDept();
		
		if(orgAdministrativeInfoEntity != null){
			LOGGER.info("登陆部门："+orgCode+"正交接单对应外场"+orgAdministrativeInfoEntity.getCode()+"其他交接单转换的部门"+currentOrgCode);
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(外场)！##########");
		}
		if(StringUtils.equals(queryHandOverBillConditionDto.getHandOverType(), "ALL")){
			if(orgAdministrativeInfoEntity != null){
			queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
			}
			count=(Long)this.getSqlSession().selectOne(NAMESPACE + "getAirHandOverBillListCount",queryHandOverBillConditionDto);
			queryHandOverBillConditionDto.setCurrentDept(currentOrgCode);
			count=count+(Long)this.getSqlSession().selectOne(NAMESPACE + "getHandOverBillListCount",queryHandOverBillConditionDto);
		}else if(StringUtils.equals("EXPRESS_AIR_HANDOVER", queryHandOverBillConditionDto.getHandOverType())){
			if(orgAdministrativeInfoEntity != null){
			queryHandOverBillConditionDto.setCurrentDept(orgAdministrativeInfoEntity.getCode());
			}
			count=(Long)this.getSqlSession().selectOne(NAMESPACE + "getAirHandOverBillListCount",queryHandOverBillConditionDto);
		}else{
			count=(Long)this.getSqlSession().selectOne(NAMESPACE + "getHandOverBillListCount",queryHandOverBillConditionDto);
		}
		
		//返回查询结果
		return count;
	}

	/**
	 * 根据交接单号获取交接单中运单list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午5:18:26
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillDetailByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryHandOverBillDetailByNo(String handOverBillNo,String waybillNo) {
		//查询参数
		Map<String,String> map = new HashMap<String,String>();
		//交接单号
		map.put("handOverBillNo",handOverBillNo);
		//若运单号不为空，则一条数据
		if(waybillNo != null){
			//运单号
			map.put("waybillNo",waybillNo);
		}
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillDetailByNo",map);
	}
	/**
	 * 根据交接单号获取交接单中运单list
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午3:29:22
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryAirHandOverBillDetailByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryAirHandOverBillDetailByNo(String handOverBillNo,String waybillNo) {
		//查询参数
		Map<String,String> map = new HashMap<String,String>();
		//交接单号
		map.put("handOverBillNo",handOverBillNo);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillDetailByNo",map);
	}

	/**
	 * 根据交接单号查询出打印交接单中需要的数据 
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 上午9:33:17
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailDto> queryPrintHandOverBillDataByNo(String handOverBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryPrintHandOverBillDataByNo",handOverBillNo);
	}
	/**
	 * 
	 * <p>根据交接单号查询出打印（空运）（正）交接单中需要的数据 </p> 
	 * @author 189284 
	 * @date 2015-10-15 上午11:30:31
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@ Override
	public List<HandOverBillDetailDto> queryPrintAirHandOverBillDataByNo(String handOverBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryPrintAirHandOverBillDataByNo",handOverBillNo);
	}
	
	/**
	 * 根据交接单号查询出打印外发清单中需要的数据 
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 上午9:33:17
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailDto> queryPrintPartialLineDataByNo(
			String handOverBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryPrintPartialLineDataByNo",handOverBillNo);
	}

	/**
	 * 根据交接单号、运单号获取流水号list
	 * @author 045923-foss-shiwei
	 * @date 2012-10-24 下午6:26:56
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryWaybillDetailByNos(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SerialNoLoadExceptionDto> queryWaybillDetailByNos(String handOverBillNo, String waybillNo) {
		//查询参数
		HashMap paramsMap = new HashMap();
		//交接单号
		paramsMap.put("handOverBillNo", handOverBillNo);
		//运单号
		paramsMap.put("waybillNo", waybillNo);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryPDAWaybillDetailByNos",paramsMap);
	}

	/**
	 * 根据交接单号，获取交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午10:15:34
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillOptLogByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillOptLogEntity> queryHandOverBillOptLogByNo(String handOverBillNo,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillOptLogByNo",handOverBillNo,rowBounds);
	}

	/**
	 * 根据交接单号，获取修改日志条数
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 上午11:11:08
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#getHandOverBillOptLogCount(java.lang.String)
	 */
	@Override
	public Long getHandOverBillOptLogCount(String handOverBillNo) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getHandOverBillOptLogCount",handOverBillNo);
	}

	/**
	 * 更新交接单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-10-25 下午4:44:04
	 * @param handOverBillNo交接单号，targetState目标状态(10：已预配，20：已交接，30：已出发，40：已到达，50：已入库，90：已作废)
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverBillState(java.util.List, int)
	 */
	@Override
	public boolean updateHandOverBillState(List<UpdateHandOverBillStateDto> list) {
		//更新交接单状态
		this.getSqlSession().update(NAMESPACE + "updateHandOverBillState", list);
		//返回值
		return true;
	}

	/**
	 * 根据交接单号获取交接单实体
	 * @author 045923-foss-shiwei
	 * @date 2012-10-26 下午1:53:01
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HandOverBillEntity queryHandOverBillByNo(String handOverBillNo) {
		//返回查询结果
		List<HandOverBillEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillByNo",handOverBillNo);
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据交接单号获取交接单实体
	 * @author 189284-foss-zx
	 * @date 2015-9-21 下午6:36:48
	 * @param handOverBillNo交接单号
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryAirHandOverBillByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HandOverBillEntity queryAirHandOverBillByNo(String handOverBillNo) {
		//返回查询结果
		List<HandOverBillEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryAirHandOverBillByNo",handOverBillNo);
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillSerialNoDetailEntity> getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", wayBillNo);
		params.put("handOverBillNo", handOverBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "getHandOverBillSerialNoDetailsByWayBillNo", params);
	}
	/**
	 * 
	 * <p>快递空运交接单 查询运单下的流水号</p> 
	 * @author 189284 
	 * @date 2015-9-22 上午10:12:05
	 * @param wayBillNo
	 * @param handOverBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillSerialNoDetailEntity> getAirHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", wayBillNo);
		params.put("handOverBillNo", handOverBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "getAirHandOverBillSerialNoDetailsByWayBillNo", params);
	}

	@Override
	public int addHandoverbillPrintrecord(
			HandoverBillPrintRecordEntity handoverBillPrintRecord) {
		this.getSqlSession().insert(NAMESPACE + "insertHandoverBillPrintRecord", handoverBillPrintRecord);
		return 1;
	}

	@Override
	public Long checkPrintHandOverBill(HandOverBillVo handOverBillVo) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "checkPrintHandOverBill", handOverBillVo);
	}

	/**
	 * 修改交接单后保存数据方法
	 * @author 045923-foss-shiwei
	 * @date 2012-11-1 上午10:04:14
	 * @param handOverBillEntity 修改后的交接单基本信息实体
	 				   deletedWaybillList 被删除的交接单内运单list
	 				   addedWaybillList 新增的交接单内运单list
	 				   updatedWaybillList 更新的交接单内运单list
	 				   deletedSerialNoList 被删除的流水号list
	 				   addedSerialNoList 新增的流水号list
	 				   handOverBillOptLogList 交接单修改日志list   
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverBill(com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public boolean updateHandOverBill(HandOverBillEntity handOverBillEntity,
			List<HandOverBillDetailEntity> deletedWaybillList,
			List<HandOverBillDetailEntity> addedWaybillList,
			List<HandOverBillDetailEntity> updatedWaybillList,
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList,
			List<HandOverBillSerialNoDetailEntity> addedSerialNoList,
			List<HandOverBillOptLogEntity> handOverBillOptLogList) {
		//更新交接单基本信息
		this.getSqlSession().update(NAMESPACE + "updateHandOverBillBasicInfo", handOverBillEntity);
		if(deletedWaybillList != null && deletedWaybillList.size() != 0){
			//删除交接单内运单
			this.getSqlSession().delete(NAMESPACE + "deleteWaybillList", deletedWaybillList);
		}
		if(addedWaybillList != null && addedWaybillList.size() != 0){
			//向交接单内增加运单
			this.getSqlSession().insert(NAMESPACE + "saveWaybillList", addedWaybillList);
		}
		if(updatedWaybillList != null && updatedWaybillList.size() != 0){
			//更新交接单内运单
			this.getSqlSession().update(NAMESPACE + "updateWaybillList", updatedWaybillList);
		}
		if(deletedSerialNoList != null && deletedSerialNoList.size() != 0){
			//删除流水号
			this.getSqlSession().delete(NAMESPACE + "deleteSerialNoList", deletedSerialNoList);
		}
		if(addedSerialNoList != null && addedSerialNoList.size() != 0){
			//新增流水号
			this.getSqlSession().insert(NAMESPACE + "saveSerialNoList", addedSerialNoList);
		}
		//如果日志list不为空，则插入
		if(handOverBillOptLogList != null && handOverBillOptLogList.size() != 0){
			this.getSqlSession().insert(NAMESPACE + "saveOptLogList", handOverBillOptLogList);
		}
		return true;
	}
	
	/**
	 * 批量插入交接单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:48:00
	 */
	@Override
	public int addHandOverBillModifyLogList(List<HandOverBillOptLogEntity> logList){
		//插入修改日志
		if(logList != null && logList.size() != 0){
			this.getSqlSession().insert(NAMESPACE + "saveOptLogList", logList);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 为配载单模块返回待配载交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:28:47
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListForVehicleAssembleBill(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillListForVehicleAssembleBill",queryHandOverBillConditionDto);
	}

	/****
	 * 根据交接单号查询出配置单号
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:37:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#getVehicleassembleNoByHandoverNo(java.lang.String)
	 */
	@Override
	public String getVehicleassembleNoByHandoverNo(String handOverBillNo) {
		//根据交接单号查询出配置单号
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getVehicleassembleNoByHandoverNo", handOverBillNo);
	}

	/**
	 * 更新交接单中某流水号的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午7:51:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverBillSerialNoPreHandOverState(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto)
	 */
	
	/**
	 * 更新交接单中流水号的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午8:19:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverBillSerialNoPreHandOverState(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto)
	 */
	@Override
	public boolean updateHandOverBillSerialNoPreHandOverState(
			UpdateHandOverBillSerialNoPreHandOverStateDto updateHandOverBillSerialNoPreHandOverStateDto) {
		//更新流水号的预配状态
		this.getSqlSession().update(NAMESPACE + "updateHandOverBillSerialNoPreHandOverState", updateHandOverBillSerialNoPreHandOverStateDto);
		return true;
	}

	/**
	 * 获取交接单中的流水号明细对象
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午8:20:21
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#querySerialNoFromHandOverBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillSerialNoPreHandOverStateDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillSerialNoDetailEntity> querySerialNoFromHandOverBill(
			UpdateHandOverBillSerialNoPreHandOverStateDto updateHandOverBillSerialNoPreHandOverStateDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNoFromHandOverBill",updateHandOverBillSerialNoPreHandOverStateDto);
	}
	
	/**
	 * 根据部门查询货件所在的交接单类型
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-12 下午4:24:08
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandoverType(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryHandoverType(String waybillNo, String serialNo,
			String orgCode) {
		//查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		//运单号
		paramsMap.put("waybillNo", waybillNo);
		//流水号
		paramsMap.put("serialNo", serialNo);
		//部门code
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryHandoverType", paramsMap);
	}

	/**
	 * 修改配载单车牌号时，批量更新配载单中交接单中的车牌及司机信息
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 下午8:26:40
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService#updateVehicleNoAndDriverInfoFromVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto)
	 */
	@Override
	public int updateVehicleNoAndDriverInfoFromVehicleAssembleBill(
			UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto dto) {
		//更新车牌、司机
		this.getSqlSession().update(NAMESPACE + "updateVehicleNoAndDriverInfoFromVehicleAssembleBill", dto);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 上报OA少货已找到时，传入到达部门code、运单号、流水号，获取出发部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-1-14 下午4:35:42
	 */
	@SuppressWarnings("unchecked")
	public List<String> querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(HandOverBillSerialNoDetailEntity queryEntity){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo", queryEntity);
	}
	

	/**
	 * 查询运单是否需要代打木架
	 * 	返回值大于0说明需要代打木架
	 * @author ibm-zhangyixin
	 * @date 2013-1-23 下午3:52:40
	 */
	@Override
	public Long queryWayBillPacked(String waybillNo) {
		//查询运单是否需要代打木架
		//返回值大于0说明需要代打木架
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryWayBillPacked", waybillNo);
	}
	
	/**
	 * 根据到达部门code、出发部门code、运单号、指定的交接单号list中匹配出交接单号，用于上报OA少货时获取交接单号
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午4:53:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryHandOverBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillNoForUnloadTaskLackGoods", queryDto);
	}

	/**
	 * 根据运单号、到达部门code获取运单号被交接过的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:53:06
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(
			String waybillNo, String destOrgCode) {
		//构造查询条件
		QuerySerialNoListForWaybillConditionDto queryDto = new QuerySerialNoListForWaybillConditionDto();
		//运单号
		queryDto.setWaybillNo(waybillNo);
		//到达部门code
		queryDto.setCurrentDeptCode(destOrgCode);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo", queryDto);
	}

	/**
	 * 根据运单号，查询该运单号在途的交接记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-24 下午5:15:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryGoodsOnTheWayRecordsByWaybillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryGoodsOnTheWayRecordsByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryGoodsOnTheWayRecordsByWaybillNo", waybillNo);
	}

	/**
	 * 通过交接单号校验任务车辆状态
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 下午4:48:29
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#validateHandOverBillStateFromTruckTask(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> validateHandOverBillStateFromTruckTask(
			String handOverBillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "validateHandOverBillStateFromTruckTask", handOverBillNo);
	}

	/**
	 * 查询待办未处理的交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:51:59
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryToDoUnDriftedHandOverBillList(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryHandOverBillDto> queryToDoUnDriftedHandOverBillList(
			Date endCreateTime) {
		return this.getSqlSession().selectList(NAMESPACE + "queryToDoUnDriftedHandOverBillList", endCreateTime);
	}

	/**
	 * 更新交接单“待办是否已漂移”字段
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午10:52:07
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverBillDriftedToDo(java.lang.String)
	 */
	@Override
	public int updateHandOverBillDriftedToDo(String handOverBillNo) {
		this.getSqlSession().update(NAMESPACE + "updateHandOverBillDriftedToDo", handOverBillNo);
		return FossConstants.SUCCESS;
	}

	/**
	 * 获取运单库存信息，去除“下一部门”过滤
	 * @author 045923-foss-shiwei
	 * @date 2013-6-10 下午4:10:00
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryWaybillStockByWaybillNoWithoutNextOrg(com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockByWaybillNoWithoutNextOrg(
			QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockByWaybillNoWithoutNextOrg", queyWaybillForHandOverBillDto);
	}

	/**
	 * 根据运单号查询该运单所有的被交接记录，并按交接时间排序
	 * @author 045923-foss-shiwei
	 * @date 2013-6-14 下午7:49:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOveredRecordsByWaybillNo", waybillNo);
	}
	
	/**
	 * 根据运单号查询该运单所有的被交接记录(包括废单号)，并按交接时间排序
	 * @author -foss-shiwei
	 * @date 2013-6-14 下午7:49:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillEntity> queryHandOveredAllRecordsByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOveredAllRecordsByWaybillNo", waybillNo);
	}


	
	
	
	
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillByLoadTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-18下午3:30:31
	 */ 
	@Override
	public HandOverBillEntity queryHandOverBillByLoadTaskNo(String taskNo) {
		return (HandOverBillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryHandOverBillByLoadTaskNo", taskNo);
	}

	/** 
	* @Title: addHandOveredSerialNo 
	* @Description: 新增一条交接单流水号明细
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-6 上午10:31:49 
	* @param @param serialNo
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public int addHandOveredSerialNo(HandOverBillSerialNoDetailEntity serialNo) {
		this.getSqlSession().insert(NAMESPACE+ "addHandOveredSerialNo",serialNo); 
		return FossConstants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailDto> queryHandOverBillDetailByHandbillNo(
			String handOverBillNo,String handOverType) {
		List<HandOverBillDetailDto> handOverBillDetailDto = new ArrayList<HandOverBillDetailDto>();
		Map<Object,String> map = new HashMap<Object, String>();
		map.put("handOverBillNo", handOverBillNo);
		if(StringUtils.equals(handOverType, "PACKAGE_HANDOVER")){
			handOverBillDetailDto =this.getSqlSession().selectList(NAMESPACE+"queryPrintAirHandOverBillDataByNo",map);
		}else{
		handOverBillDetailDto = this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillDetailByHandbillNo",map);
		}
		return handOverBillDetailDto;
	}
	/**
	 * 运单补录时更新交接单信息
	 * @author heyongdong 
	 * @param updatehandoverbills 
	 * @param updateHandoverbillDetails
	 * @param logs
	 * @date 2014年2月19日 14:56:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#updateHandOverbillWeigthAndVolumn(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public boolean updateHandOverbillWeigthAndVolumn(
			List<HandOverBillEntity> updatehandoverbills,
			List<HandOverBillDetailEntity> updateHandoverbillDetails,
			List<HandOverBillOptLogEntity> logs) {
		
		if(updatehandoverbills != null && updatehandoverbills.size() != 0){
			//更新交接单内运单
			this.getSqlSession().update(NAMESPACE + "updateHandOverbillForWaybill", updatehandoverbills);
		}
		if(updateHandoverbillDetails != null && updateHandoverbillDetails.size() != 0){
			//更新交接单内运单
			this.getSqlSession().update(NAMESPACE + "updateWaybillList", updateHandoverbillDetails);
		}
		//如果日志list不为空，则插入
		if(logs != null && logs.size() != 0){
			this.getSqlSession().insert(NAMESPACE + "saveOptLogList", logs);
		}
		return true;
	}

	/**
	 * @author niuly
	 * @date 2014-3-6上午11:07:59
	 * @function 根据车辆明细ids查询交接单明细信息
	 * @param detailIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryHandOverBillDetailByIds(List<String> detailIdList) {
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillDetailByIds",detailIdList);
	}

	/**
	 * @author niuly
	 * @date 2014-3-6下午2:33:47
	 * @function 根据运单号和到达部门编码查询到达总件数
	 * @param waybillNo
	 * @param destOrgCode
	 * @return
	 */
	@Override
	public int queryWaybillCountByNoAndDept(String waybillNo,String destOrgCode) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("destOrgCode", destOrgCode);
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillCountByNoAndDept",map);
	}
	/**
	 * @author heyongdong
	 * @function 根据运单号查询该运单是否补码
	 * @param waybillNo
	 * @date 2014年4月1日 09:52:02
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryWaybillComplement(java.lang.String)
	 */
	@Override
	public int queryWaybillComplement(String waybillNo) {
		
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillComplement",waybillNo);
	}	
	

	/**
	 * 检查交接单在租车标记表的预提状态，如果为预提中或者已预提则不允许修改
	 * @author 205109-foss-zenghaibin
	 * @date 2014-08-14 上午08:27:39
	 * @param handOverBillNo
	 */
	@Override
	public List<HoldingStateDto> queryHoldingState(String handOverBillNo){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryHoldingState",handOverBillNo);
	}
	
	/**
	 * 根据运单号查询代理单号
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-27
	 * @param waybillNo
	 * @return
	 * @update-author 269701--lln-2015/11/02
	 * @update-param waybillNo 运单号 serialNo 流水号
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String getAgentWaybillNoByWaybillNo(String waybillNo,String serialNo){
		//查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		//运单号
		paramsMap.put("waybillNo", waybillNo);
		//流水号
		paramsMap.put("serialNo", serialNo);
		List<String> agentWaybillNo=this.getSqlSession().selectList(NAMESPACE+"getAgentWaybillNoByWaybillNo", paramsMap);
		if(agentWaybillNo.size() >0){
			return agentWaybillNo.get(0);
		}
		return null;
	}
	
	/**
	 * 根据外场code 查询对应的异常库区code
	 * @Author: 218427  foss-hongwy
	 * 2015-8-4
	 * @param code
	 * @return
	 */
	public String queryGoodsExceptionArea(String code){
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryGoodsExceptionArea",code);
	}
	
	public int updateAreaCode(String waybillNo,String areacode,String code){
		Map<String,String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("areacode", areacode);
		map.put("code", code);
		return this.getSqlSession().update(NAMESPACE+"updateAreaCode",map);
	}
	
	
	/**
	  * 根据运单号、出发部门查询交接单明细表
	  * @author 273247
	  * @param waybillNo
	  * @param origOrgCode
	  * @return
	  */
	public List<HandOverBillDetailEntity> queryHandoverBillDetailByWaybillNoAndOrgCord(String waybillNo, String origOrgCode){
		//查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		//运单号
		paramsMap.put("waybillNo", waybillNo);
		//出发部门
		paramsMap.put("origOrgCode", origOrgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryHandoverBillDetailByWaybillNoAndOrgCord", paramsMap);
	}
		
	/**
	 * 根据商务专递交接单号获取交接单中运单list
	 * @author 272681chenlei
	 * @param HandOverBillNo交接单号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryBusAirHandOverBillDetailByNo(String handOverBillNo,String waybillNo) {
		//查询参数
		Map<String,String> map = new HashMap<String,String>();
		//商务专递交接单号
		map.put("handOverBillNo",handOverBillNo);
		//若正单号不为空，则一条数据
		if(waybillNo != null){
			//正单号
			map.put("waybillNo",waybillNo);
		}
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryBusAirHandOverBillDetailByNo",map);
	}
	
	/**
	 * 根据商务专递交接单号、运单号获取流水号list
	 * @author chenlei 272681
	 * @param handOverBillNo交接单号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<HandOverBillSerialNoDetailEntity> getBusAirHandOverBillSerialNoDetailsByWayBillNo(String wayBillNo, String handOverBillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", wayBillNo);
		params.put("handOverBillNo", handOverBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "getBusAirHandOverBillSerialNoDetailsByWayBillNo", params);
	}
	
	/** 
	 * @Title: queryHandOverBillNoByLoadTaskNo 
	 * @Description: 根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryHandOverBillNoByLoadTaskNo(java.lang.String)
	 * @author: 272681
	 * @throws 
	 * Date:2015-11-24
	 */ 
	@Override
	public String queryHandOverBillNoByLoadTaskNo(String taskNo) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryHandOverBillNoByLoadTaskNo", taskNo);
	}

	@Override
	public void updateWKHandOverBillStateByNo(Map<String,String> param) {
		this.getSqlSession().update(NAMESPACE + "updateWKHandOverBillStateByNo", param);
	}
	
	/** 
	 * @Title: querySaleWaybillStockList 
	 * @Description: 查询非本部门库存
	 * @param queyWaybillForHandOverBillDto,limit,start
	 * @return    
	 * @author: 332219
	 * @throws 
	 * Date:2016-09-19
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> querySaleWaybillStockList(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto,int limit, int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySaleWaybillStockList",queyWaybillForHandOverBillDto,rowBounds);
	}
	
	/**
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午3:36:13
	 * @param waybillNo运单号，storageDept库存部门，下一到达部门
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SerialNoStockEntity> querySaleJoinCarSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySaleJoinCarSerialNoStockList",querySerialNoListForWaybillConditionDto);
	}
	
	/**
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午3:36:13
	 * @param waybillNo运单号，storageDept库存部门
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SerialNoStockEntity> querysaleSerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySaleSerialNoStockList",querySerialNoListForWaybillConditionDto);
	}
	
	/**
	 * 查询非本部门交接运单界面，分页时获取总条数
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-09-20 上午3:36:13
	 */
	@Override
	public Long getSaleWaybillStockCount(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getSaleWaybillStockCount",queyWaybillForHandOverBillDto);
	}
	
	/**
	 * 交接单新增、修改界面，快速添加通过运单号获取非本部门库存运单信息
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-10-2 下午3:39:48
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockSaleByWaybillNo(QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockSaleByWaybillNo",queyWaybillForHandOverBillDto);
	}
	
	/**
	 * 获取非本部门运单库存信息，去除“下一部门”过滤
	 * @author 332219-foss-xuehaoyang
	 * @date 2016-10-2 下午3:39:48
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao#queryWaybillStockByWaybillSaleNoWithoutNextOrg(com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryWaybillStockByWaybillSaleNoWithoutNextOrg(
			QueryWaybillForHandOverBillConditionDto queyWaybillForHandOverBillDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockByWaybillSaleNoWithoutNextOrg", queyWaybillForHandOverBillDto);
	}
	
	/** 
	 * @Title: queryHandOverBillByLoadTaskNo 
	 * @Description: 根据运单获取最后一次交接单信息
	 * @param waybillNo
	 * @return    
	 * @author: tfr-360903
	 * Date:2016年11月7日 11:04:30
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public HandOverBillEntity queryHandOverBillByLast(String waybillNo) {
		//返回查询结果
		List<HandOverBillEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillByLast", waybillNo);
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
}
