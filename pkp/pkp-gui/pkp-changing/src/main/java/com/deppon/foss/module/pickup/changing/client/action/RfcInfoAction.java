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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/RfcInfoAction.java
 * 
 * FILE NAME        	: RfcInfoAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
/**
 * 1.5.3	界面描述
 主界面标题：变更运单。
 “变更运单”主界面包含变更基本条件录入区域、
 运单信息显示及编辑区域、功能按钮区域。
 起草变更单（内部）操作：用户进入“变更运单”主界面后
 （见起草变更单（内部）界面-1），
 导入运单信息至操作界面，
 选择运单变更为“内部要求”，
 录入变更原因并修改运单信息，
 最终上传变更凭证提交运单变更申请。
 1.变更基本条件录入区域：
 1）单号：起草运单变更对应的运单号。
 2）客户要求：
 由于客户原因发起的运单变更需求；
 3）内部要求：
 由于内部操作失误发起的运单变更需求；
 4）变更类型：
 客户要求的运单变更包含转运、返货、更改、作废，
 内部要求的变更包含更改、中止。
 5）变更原因：
 起草变更运单的原因。
 2.运单信息显示及编辑区域：
 1）运单基础信息：
 包含收货部门、变更单号、上门接货。
 2）发货客户信息：
 包含发货人手机、电话、客户名称、联系人、地址。
 3）收货客户信息：
 包含收货人手机、电话、联系人、地址。
 4）运输信息：
 包含货物状态、提货方式、目的站、提货网点、
 运输性质、配载类型、预配航班、
 返单类别、对外备注、对内备注。
 5）货物信息：货物的基本信息，
 包含货物名称、包装、件数、重量、尺寸、
 体积、货物类型、大车直送、
 贵重物品、特殊物品。
 5）增值业务信息：包含保价声明价值、代收货款、
 退款类型、收款人、收款账号、包装费、
 送货费、装卸费、其他费用项。
 6）计费付款信息：包含公布价运费、
 增值服务费、优惠费用、费用合计、
 付款方式、到付金额、现付金额、
 预付费保密。
 7）详细计价信息：默认隐藏不显示，
 点击向左伸展按钮后伸展显示（见起草变更单
 （内部）界面-2）。包含计费类型、计费重量、
 费率、运费、保价声明价值、保价费率、保价费、
 代收货款、代收费率、代收手续费、包装费、
 送货费、其他费用、优惠费用、费用合计。
 8）变更信息：变更运单条目明细显示。
 该区域主要显示变更的项目及变更前后对应的运单信息。
 9）上传凭证区域：
 上传变更凭证电子件。
 3.功能按钮：
 导入：点击导入按钮将运单号对应的运单信息展现在变更运单界面。
 提交：点击提交变更运单申请。
 打印：提交运单变更后，可点击打印变更单。
 1.6	操作步骤
 序号	基本步骤	相关数据	补充步骤
 1	点击变更运单菜单进入“变更运单”主界面		系统初始化界面控件值
 2	输入运单号，点击“导入”按钮	运单信息	系统判断是否允许发起运单变更：1）若允许，则系统搜索运单信息并根据数据元素的对应关系将运单信息带至变更运单界面相应控件，参见业务规则SR1；
 2）若不允许，则系统弹出提示框给予相应提示。参见业务规则SR2
 3	点击“内部要求”单选按钮 		1.	运单项对应的编辑状态发生变化，参见业务规则SR3；
 2.	上传凭证区域显示默认上传的变更凭证名称，参见业务规则SR4。
 4	用户在“变更原因“输入框内输入内部申请变更运单的原因	变更运单原因	
 5	用户在运单信息显示区域修改相应的运单信息		“变更信息”区域显示变更项及其变更前后对应的运单信息，参见业务规则SR5
 6	点击变更凭证所在行的“上传”按钮 	变更凭证电子件	弹出【路径选择】模式窗口，参见业务规则SR10
 7	选择凭证存放的本地路径，点击“确定”按钮		凭证上传至变更界面
 8	点击“提交”按钮		弹出【运单变更信息】对话框，回显运单变更的信息，参见系统用例SUC-490：显示运单变更明细
 9	点击对话框界面的“确定”按钮		系统弹出对话框提示是否确定提交运单变更申请。
 10	点击“确定”按钮，确定提交运单变更申请		系统校验是否满足运单变更申请的条件，参见业务规则SR2、SR8
 1.	若满足则系统提示运单变更申请成功，同时
 1）系统根据凭证存放路径找到凭证文件后保存到系统中；
 2）“打印”按钮变为可点击。
 2.	若不满足则系统给予提示。

 扩展事件

 序号	扩展事件	相关数据	备注
 2a	输入运单号，点击“导入”按钮后，
 若未找到对应的运单信息，系统给以提示		
 2b	运单信息导入变更运单界面相应控件后，
 若再次点击“导入”按钮还原当前运单信息		
 7a	用户可以点击变更凭证所在行“删除”按钮删除已上传的凭证，
 重新选择文件上传	变更凭证电子件	
 7b	用户可以点击变更凭证所在行“查看”按钮查看已上传的凭证	
 变更凭证电子件	
 8a	若未对运单信息做任何修改，
 提交时系统给予提示不能提交		
 9a	点击对话框界面的“取消”按钮		
 取消运单变更申请，返回运单变更主界面
 10a	点击对话框界面的“取消”按钮		
 取消运单变更申请，返回运单变更主界面
 1.7	业务规则
 序号	描述
 SR1	1.  由于整车运单信息与非整车运单信息不同，
 导入整车运单至变更运单界面后，
 整车运单信息中不存在的字段控件值为空，不可编辑。
 2.  如导入的运单为整车运单，
 则运输性质控件显示为“整车”。
 SR2	
 下表为不能起草运单变更的类型及不能起草时系统提示内容

 序号	不能起草运单变更的类型	提示内容
 1	运单状态为“已保存待补录”的运单	
 运单未补录，不能起草运单变更！
 2	运单状态为“作废”、“中止”的运单	
 运单已作废，不能起草运单变更！
 运单已中止，不能起草运单变更！
 3	运单对应的收货部门与系统当前组织不一致	该单的收货部门不是您部门，
 不能起草运单变更！
 4	运单状态为“已签收”的运单	货物已签收，
 不能起草运单变更！
 5	变更单状态为“待审核”或“待受理”的运单	
 该运单有运单变更单还未被审核，不能起草变更运单！
 该运单有运单变更单还未被受理，不能起草变更运单！
 6	运单已进行结清货款操作	该运单已进行结清货款操作，
 如需更改请联系到达部门进行反结清货款操作！
 7	财务锁定的运单（财务将不允许发起变更的运单进行锁定）
 不能发起运单变更 
 该运单已被财务锁定，
 不能起草变更运单！


 SR3	1．	运单无出库记录，
 不允许起草转运单、返货单；
 a.	客户来源可以选择更改、作废；
 b.	内部来源可以选择更改、中止
 2．	运单出库记录，不允许起草作废单，
 涉及到目的站、提货网点的修改必须到转运信息、
 返货信息面板修改；如果变更提货方式，
 由“派送”该为“自提”，原始目的站不支持“自提”，
 提示“原始目的站不支持自提，请选择转运或返货类型”
 a.	客户来源可以选择更改、转运、返货；
 c.	内部来源可以选择更改、中止
 3．	若未选择变更要求（客户要求/内部要求），
 运单信息显示区域的控件为不可编辑状态；
 若选择了变更要求，
 则不可变更的运单项对应的控件为不可编辑状态，
 可以变更的运单项对应的控件为可编辑状态。
 运单项能否进行变更主要受变更要求、
 运输类型、货物当前库存部门三个维度的影响。
 其中内部要求发起的运单变更，
 可变更及不可变更的运单项请详见下表：


 SR4	1.	选择客户要求变更时，
 上传凭证名称默认显示为“身份证复印件”及“运单客户联”，
 可另新增变更凭证；选择内部要求变更时，
 上传凭证名称默认显示为“运单原件”，
 可另新增变更凭证；
 2.	默认显示的变更凭证所在行不可删除，
 新增的变更凭证所在行可删除；
 3.	已选择上传的变更凭证可点击查看，
 也可点击删除。
 SR5	1.	 “变更项”列显示为变更的运单项名称，
 “变更前信息”列显示变更项在变更前对应的运单信息，
 “变更后信息”列显示变更项在变更后对应的运单信息。
 如是转运或返货变更：“目的站”变更项对应的变更前信息为原目的站，
 变更后信息为转运/返货目的站；
 “提货网点”变更项对应的变更前信息为原提货网点，
 变更后信息为转运/返货提货网点。
 2.	对于“上门接货”、“预付费保密”、“大车直送”、
 “贵重物品”、“特殊物品”为复选框的变更项，
 变更前后对应的信息根据复选框勾选与否显示为是或否；
 3.	若变更项变更前信息或变更后信息为空，
 则“变更前信息”或“变更后信息”列显示为:—；
 4.	以下运单项不列入变更项：计费类型、
 计费重量、费率、保价费率、代收费率、装卸费、
 其他费用（总金额）、转运运输性质、转运配载类型、
 转运预配航班、转运计费类型、转运费率、
 返货运输性质、返货计费类型；
 5.	变更项行显示顺序为：运单基础信息变更项→
 发货客户信息变更项→收货客户信息变更项→运输信息变更项→
 货物信息变更项→增值业务信息变更项→详细计价信息变更项→计费付款信息变更项；
 6.	“变更前信息”与“变更后信息”列显示内容不能相同。
 SR6	1.	运单信息的修改录入操作参照运单生成相关系统用例的操作；
 2.	修改运单信息时，
 系统校验的业务规则参照运单生成相关系统用例的业务规则。
 SR7	1.	关键增值服务信息的变更规则：

 运单项	起草运单变更时对应的版本
 费率	产品在运单信息生成时对应的公布价版本
 保价费率	运单信息生成时的保价费率版本
 代收费率	运单信息生成时的代收费率版本
 公布价运费	运单信息生成时的公布价运费版本
 保价费	运单信息生成时对应的收费标准
 代收手续费	运单信息生成时对应的收费标准
 送货费	运单信息生成时对应的收费标准
 装卸费	运单信息生成时对应的收费标准
 其他费用项	运单信息生成时的收费标准
 优惠费用	运单开单信息生成时对应的优惠标准

 综上：起草变更单（内部）时，产品及价格优惠版本统一执行运单开单时对应的版本。
 举例：
 公布价运费：运单开单时精准卡航对应的公布价（上门发货）运费最低一票为40元，在开单后系统维护调整为50元最低一票。则在起草变更单时，最低一票仍执行运单开单时的标准，即为40元一票。
 2.	含有代收货款的运单，如取消代收货款（代收货款金额更改为0），代收手续费变为0。
 SR8	提交变更运单时必须已上传变更凭证，否则不能提交
 SR9	成功提交运单变更后，方可打印变更单
 SR10	1.	弹出路径选择框，
 默认指定路径在桌面，可选择保存路径；
 2.	可上传的凭证扫描件，应该为图片，格式允许选择常见的JPG,GIF,PNG,BPM；
 3.	上传图片大小有所限制（默认250K，允许系统后台配置），
 如果超出所设范围，系统给出提示“您上传的文件已超过最大允许大小250K，请调整后重新上传。”。
 SR11	对于发货客户的变更：
 1.	同一发货客户，若客户资质发生变化；
 a)	开单时为合同客户，开单后（起草更改单之前）
 客户属性变为非合同客户： FOSS在开单时记录客户资质，起草更改单时仍可选择免费送货；
 b)	开单时为非合同客户，开单后（起草更改单之前）
 客户属性升级为合同客户：起草更改单时仍可选择免费送货；
 2.	由某一发货客户更改为另一发货客户
 a)	合同客户更改为非合同客户：原开单提货方式为免费送货，
 与开单时保持一致，即直接清空提货方式，重新选择提货方式;
 b)	非合同客户更改为合同客户：提货方式中增加"免费送货"选项。
 SR12	1.	货物未到达第一打木架外场（开单录入代打木架信息时系统自动生成的代打木架信息），
 允许修改代打包装信息
 2.	货物已到达第一打木架外场，则不可发起代打包装信息更改
 3.	若有代打包装，则在录入代打包装信息后，
 系统自动计划出代打包装费用，显示至包装费中，
 营业员或开单员可以任意修改包装费。
 4.	在更改单界面中，加入代打包装货物件数的选择，
 即流水号选择。在发起代打包装更改单时，
 打开的代打包装信息中默认勾选开单录入的代装包装总件数的流水号，
 即：开单录入代打木架4件，代打木箱3件，
 则在更改单代打包装信息中，自勾选流水号1-7；
 5.	允许点击“代打木架”按钮直接修改打包装信息
 1.8	数据元素
 1.8.1	变更基本录入信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 客户要求	客户提出的运单变更申请	单选框		10	是	默认为空
 内部要求	由于内部开单失误而提出的运单变更申请	单选框		10	是	默认为空
 变更类型	变更运单的类型，客户要求下包含：转运、返货、作废、更改，内部要求下包含更改、中止。	下拉框		10	是	默认为“更改”
 变更原因	变更运单的原因	文本		80	是	默认为空
 1.8.2	运单基础信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 收货部门	收取货物的部门				是	
 变更单号	点击可变更运单单号	复选框			否	
 可勾选时，则勾选后显示变更单号文本框（可编辑）
 上门接货	前往客户处接收货物	复选框			否	

 1.8.3	发货客户信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 手机	发货联系人的手机号码	文本			否	导入运单信息
 电话	发货联系人的电话号码	文本			否	导入运单信息
 客户名称	发货企业客户的公司名，个人客户的姓名	公共选择框			是	导入运单信息
 联系人	发货客户的个人代表	文本			是	导入运单信息
 地址	发货客户的联系地址	文本			否	导入运单信息
 1.8.4	收货客户信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 手机	收货联系人的手机号码	文本			否	
 电话	收货联系人的电话号码	文本			否	
 联系人	收货客户的个人代表	文本			是	
 地址	收货客户的联系地址	文本			否	提货方式为送货时必填
 1.8.5	运输信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 货物状态	货物当前状态，包含：收货部门库存中、
 收货部门已出库（已出收货部门库存，中转部门未入库）、
 中转部门库存中、中转部门已出库（已出中转部门库存，即货物在途运输）、
 到达部门库存中、到达部门送货中。	文本			是	系统自动带出数据
 注：分批配载的货物，以距离最终配载部门最近的部门作为参照。
 提货方式	货物提取方式	下拉框			是	
 目的站	货物的目的站	文本			是	
 提货网点	货物的提货网点	公共选择框			是	
 运输性质	货物的运输性质	下拉框			是	
 配载类型	货物的配载类型	下拉框			是	由运输性质决定
 预配航班	空运班次，含早班、中班、晚班	下拉框			是	
 返单类别	签收单返回类型，含签收单原件返回、传真返回、扫描上传、无需返回	下拉框			是	
 对外备注	传达给客户的备注信息	下拉框				可选择多项
 对内备注	公司内部备注信息	文本			是	
 1.8.6	货物信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 名称	货物的名称	文本			是	
 包装	货物的外包装	文本			是	
 件数	货物的件数	文本			是	
 重量	货物的重量	文本			是	
 尺寸	货物的尺寸	文本			否	
 体积	货物的体积	文本			是	
 货物类型	货物的A/B类型，区分大小货	单选按钮			否	
 大车直送		复选框			否	
 贵重物品		复选框			否	
 特殊物品		复选框			否	
 1.8.7	增值业务信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 保价声明价值	货物保价金额	文本		8	是	
 代收货款	代收货款金额	文本		8	是	
 退款类型	代收货款退款方式	下拉框			否	
 收款人	代收货款收款人	文本			否	
 收款账号	收款人银行账号	文本			否	
 包装费	货物包装费用	文本			是	若无则输入0
 送货费	货物送货费用	文本			是	若无则输入0
 装卸费	装卸费用	文本			是	若无则输入0
 1.8.8	计费付款信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 公布价运费	根据公布价计算的运费				是	公布价运费=运费+转运费或返货费
 增值服务费	增值服务费用总计				是	
 优惠费用	优惠打折的总费用				是	
 费用合计	向客户收取的费用总计				是	
 付款方式	承运费用付款的方式	下拉框			是	
 到付金额	收货客户需要支付的费用	文本			是	若无则输入0
 现付金额	发货客户需要支付的费用	文本			是	若无则输入0
 预付费保密	对预付费用进行保密	复选框			否	
 1.8.9	变更信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 变更项	运单信息发生变更的项目				是	系统根据更改项自动生成显示
 变更前信息	变更项变更前对应的运单信息				是	系统自动生成
 变更后信息	变更项变更后对应的运单信息				是	系统自动生成
 1.8.10	详细计价信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 计费类型	分为重量计费、体积计费两种				是	系统自动生成
 计费重量	空运时为必填项，汽运时不可编辑				否	系统自动计算生成
 费率					是	
 保价费率					是	
 代收费率					是	
 运费					是	
 保价费					是	
 代收手续费					是	
 包装费					是	
 送货费					是	
 其他费用					是	
 优惠费用					是	
 费用合计					是	
 1.9	非功能性需求
 使用量	每天处理的运单约为1000000单，变更运单的数量占比约为20%
 2012年全网估计用户数	营业员数量约10000名
 响应要求（如果与全系统要求 不一致的话）	系统一般需求
 使用时间段	营业部上班时间
 高峰使用时间段	9:00-16:00，19:00-21:00
 1.10	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 客户信息接口	CRM系统	FOSS调用CRM客户信息接口，查询录入客户信息
 电子地图接口	GIS系统	FOSS调用电子地图接口查询录入目的站、提货网点信息
 库存接口	中转子系统	FOSS调用中转库存接口判断货物是否已签收出库
 财务类变更接口	结算系统	FOSS调用该接口判断财务是否可发起财务类变更，
 若财务对运单进行了锁定，则不可发起财务类变更


 */
/**--------------------------------------
 * 1.	纸包装件数默认显示数值等于货物总件数，
 * 营业员可以修改各包装的件数；
 * 系统自动在录入下一个包装前计算显示剩余未录入的件数，
 * 例：某票货物为50件，则自动显示纸包装50，
 * 在营业员修改纸包装为20时，木包装自动显示为30，
 * 在营业员修改木包装为10时，
 * 纤包装自动显示为20，该过程中未录入包装数值的默认显示为0；
 2.	货物包装总件数小于等于货物总件数；
 */
/**--------------------------------------
 * 1.	离线开单时，不录入代打木架信息；
 * 当木包装件数大于等于1时，系统不提示任何信息，
 * 也不弹出代打木架录入界面；
 2.	离线开单的代打木架信息在离线开单提交时，
 系统校验提醒；
 */
/**
 * 1.	运输类型为汽运时，货物类型为唯一选择项；即，
 * 非A即B；默认不可勾选，
 * 只有当走货路由经过特定的城市时需要录入货物类型,
 * 特定城市可在系统中进行配置；
 2.	运输类型为空
 默认显示为普货，目前只有这一个分类，该类型可做配置；
 --------------------------------------
 */
/**--------------------------------------
 * 1.	系统根据货物名称匹配生成的勾选贵重物品，
 * 营业员不可去掉勾选；
 2.	营业员也可以主动勾选贵重物品；
 3.	件数等于1、体积小于等于0.05个方、
 报价声明价值大于等于10000元，为贵重物品；
 系统自动勾选贵重物品，
 营业员可修改是否勾选；
 4.	件数大于等于2时，平均体积（体积/件数）
 小于等于0.5方，平均声明价值（保价声明价值/件数）
 大于等于10000元，为贵重物品；
 系统自动勾选贵重物品，
 营业员可修改是否勾选；
 1.	营业部开单时按打完木架后的包装开，
 即包装中含“木架/木箱”，
 开单件数为货物打木架/木箱前的实际件数（防止丢货），
 尺寸和重量按照以下公式计算：
 1.1	整票货物代打时：
 开单体积=代打货物体积*1.4；
 开单重量=所有货物重量+代打货物体积*42；
 1.2	部分货物代打时，
 开单体积=代打货物体积*1.4+未打木架货物体积；
 开单重量=所有货物重量+代打货物体积*42。
 即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
 重量另加，单票中未打木架的货物的体积和重量不变；
 1.3	例如：一票货物需全部代打，
 货物体积为1个方，重量为100KG，
 则开单体积为1.4个方，
 开单重量为100+1*42=142KG，
 收取客户包装费为150*1.4=210元；
 1.4	需要加托时，仍按照50元/个另外收取费用，
 托的重量和体积不再另加；
 营业部不需要再更改由于打木架引起重量和体积的变化；
 2.	开单件数为代打木架前货物实际件数，
 包装为打木架后的包装，
 打木架后件数发生变化后，
 需及时更改件数；
 --------------------------------------
 */

/**--------------------------------------
 * 每天处理的运单约为1000000单
 营业员数量约10000名
 系统一般要求
 营业部、集中开单小组上班时间
 营业部：16：00-20：00
 集中开单小组：21：00-次日4：00
 */

/**--------------------------------------
 * 本界面为录入增值服务信息。
 界面主要分为三个部分：
 录入增值服务、
 录入包装费、
 查询其它费用。
 1.	录入增值服务
 录入增值服务分为两个部分：
 录入增值服务信息界面、
 录入其它费用列表；
 1.1	录入增值服务信息界面
 录入增值服务信息界面包括：
 1.1.1	保价声明价值；
 1.1.2	保价费率：
 保价费率可由基础资料配置，
 可按出发城市区域，
 出发营业部等；
 1.1.3	保价费；
 1.1.4	代收货款；
 1.1.5	代收费率；
 1.1.6	代收手续费；
 1.1.7	退款类型：
 包括三日退、
 退日退、
 审核退，
 默认三日退；
 1.1.8	包装费；
 1.1.9	装卸费；
 1.1.10	送货费；
 1.1.11	其它费用合计；
 1.1.12	返单类别：
 包括无需返单、
 原件签收单返回、
 传真件签收单返回、
 扫描件返回，
 默认无需返单；
 1.1.13	预付费保密；
 1.2	录入其它费用列表
 录入其它费用列表包括：
 1.2.1	费用名称；
 1.2.2	费用金额；
 1.2.3	新增其它费用：
 功能按钮；
 1.2.4	删除其它费用：
 功能按钮；
 2.	录入包装费
 其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“录入包装费”；
 3.	查询其它费用
 其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“查询其它费用”；
 --------------------------------------
 */
/**
 * /**--------------------------------------
 * 1.	若货物为违禁品，
 * 	则系统自动提示“货物为违禁品，不可开单！”；
 2.	若货物为拒收品，
 则系统自动提示“货物为拒收品，不可开单！”；
 3.	若货物为贵重物品，
 则系统自动勾选“贵重物品”，
 且不可修改；
 4.	若货物为限保物品，
 则系统自动限定保价金额，且不可修改，
 并提示“货物为限保物品”；
 5.	违禁品、拒收品、
 贵重物品、限保物品（含保价金额上限）
 具体类型可在系统中进行配置；
 1.	货物重量单位为千克
 */
/**--------------------------------------
 * 1.	货物尺寸为计算器输入，
 * 显示为输入文本；
 2.	尺寸计算单位为厘米，
 尺寸计算出数据后转换单位为立方米后，
 在货物体积中显示数据；例如：
 尺寸录入为：50*50*20，
 则体积显示数据为：0.05；
 */
/**--------------------------------------
 * 1.	货物体积单位为立方米；
 2.	营业员可以修改通过尺寸计算器
 计算得出的体积数据；
 3.	系统设置货物重量体积比区间值（该值由基础资料配置），
 在运单提交时，
 系统自动对重量体积比进行校验：
 即重量体积比X=重量/体积；
 3.1	当X不在设置的区间中，
 弹出提示“请确认录入的重量体积是否准确！”；
 （该弹窗有两个按钮：确定、取消）点击确定时，
 弹出离线开单确认运单信息界面；点击取消，
 点返回运单录入界面；
 3.2	当X在区间中，无提示；
 直接进入确认运单信息界面；
 --------------------------------------
 */
/**--------------------------------------
 * 1.	纸包装件数默认显示数值等于货物总件数，
 * 营业员可以修改各包装的件数；
 * 系统自动在录入下一个包装前计算显示剩余未录入的件数，
 * 例：某票货物为50件，则自动显示纸包装50，
 * 在营业员修改纸包装为20时，木包装自动显示为30，
 * 在营业员修改木包装为10时，
 * 纤包装自动显示为20，该过程中未录入包装数值的默认显示为0；
 2.	货物包装总件数小于等于货物总件数；
 */
/**--------------------------------------
 * 1.	离线开单时，不录入代打木架信息；
 * 当木包装件数大于等于1时，系统不提示任何信息，
 * 也不弹出代打木架录入界面；
 2.	离线开单的代打木架信息在离线开单提交时，
 系统校验提醒；
 */
/**
 * 1.	运输类型为汽运时，货物类型为唯一选择项；即，
 * 非A即B；默认不可勾选，
 * 只有当走货路由经过特定的城市时需要录入货物类型,
 * 特定城市可在系统中进行配置；
 2.	运输类型为空
 默认显示为普货，目前只有这一个分类，该类型可做配置；
 --------------------------------------
 */
/**--------------------------------------
 * 1.	系统根据货物名称匹配生成的勾选贵重物品，
 * 营业员不可去掉勾选；
 2.	营业员也可以主动勾选贵重物品；
 3.	件数等于1、体积小于等于0.05个方、
 报价声明价值大于等于10000元，为贵重物品；
 系统自动勾选贵重物品，
 营业员可修改是否勾选；
 4.	件数大于等于2时，平均体积（体积/件数）
 小于等于0.5方，平均声明价值（保价声明价值/件数）
 大于等于10000元，为贵重物品；
 系统自动勾选贵重物品，
 营业员可修改是否勾选；
 1.	营业部开单时按打完木架后的包装开，
 即包装中含“木架/木箱”，
 开单件数为货物打木架/木箱前的实际件数（防止丢货），
 尺寸和重量按照以下公式计算：
 1.1	整票货物代打时：
 开单体积=代打货物体积*1.4；
 开单重量=所有货物重量+代打货物体积*42；
 1.2	部分货物代打时，
 开单体积=代打货物体积*1.4+未打木架货物体积；
 开单重量=所有货物重量+代打货物体积*42。
 即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
 重量另加，单票中未打木架的货物的体积和重量不变；
 1.3	例如：一票货物需全部代打，
 货物体积为1个方，重量为100KG，
 则开单体积为1.4个方，
 开单重量为100+1*42=142KG，
 收取客户包装费为150*1.4=210元；
 1.4	需要加托时，仍按照50元/个另外收取费用，
 托的重量和体积不再另加；
 营业部不需要再更改由于打木架引起重量和体积的变化；
 2.	开单件数为代打木架前货物实际件数，
 包装为打木架后的包装，
 打木架后件数发生变化后，
 需及时更改件数；
 --------------------------------------
 */

/**--------------------------------------
 * 每天处理的运单约为1000000单
 营业员数量约10000名
 系统一般要求
 营业部、集中开单小组上班时间
 营业部：16：00-20：00
 集中开单小组：21：00-次日4：00
 */

/**--------------------------------------
 * 本界面为录入增值服务信息。
 界面主要分为三个部分：
 录入增值服务、
 录入包装费、
 查询其它费用。
 1.	录入增值服务
 录入增值服务分为两个部分：
 录入增值服务信息界面、
 录入其它费用列表；
 1.1	录入增值服务信息界面
 录入增值服务信息界面包括：
 1.1.1	保价声明价值；
 1.1.2	保价费率：
 保价费率可由基础资料配置，
 可按出发城市区域，
 出发营业部等；
 1.1.3	保价费；
 1.1.4	代收货款；
 1.1.5	代收费率；
 1.1.6	代收手续费；
 1.1.7	退款类型：
 包括三日退、
 退日退、
 审核退，
 默认三日退；
 1.1.8	包装费；
 1.1.9	装卸费；
 1.1.10	送货费；
 1.1.11	其它费用合计；
 1.1.12	返单类别：
 包括无需返单、
 原件签收单返回、
 传真件签收单返回、
 扫描件返回，
 默认无需返单；
 1.1.13	预付费保密；
 1.2	录入其它费用列表
 录入其它费用列表包括：
 1.2.1	费用名称；
 1.2.2	费用金额；
 1.2.3	新增其它费用：
 功能按钮；
 1.2.4	删除其它费用：
 功能按钮；
 2.	录入包装费
 其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“录入包装费”；
 3.	查询其它费用
 其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“查询其它费用”；
 --------------------------------------
 */
/**
 * 录入保价声明价值
 * 1.	系统根据录入的保价声明价值和系统自动获得的保价费率计算保价费；
 * 2.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR1、SR3；
 * 录入代货货款
 * 1.	系统根据录入的代收货款和系统自动获得的代收费率计算代收手续费；
 2.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR2、SR3；
 录入退款类型
 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR2、SR4；
 2.	规则-请参见系统规则SR1；
 录入包装费：焦点移至包装费时，
 自动弹出包装费录入界面
 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR5；
 录入装卸费
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR6；
 * 录入送货费
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-
 * 确认承运信息-录入增值服务信息”中系统规则SR7；
 * 录入其它费用：点击新增其它费用，增加新的其它费用列后，点击放大镜，弹出其它费用查询列表；
 * 运单生成-确认承运信息-录入增值服务信息”中系统规则SR8；
 * 录入返单类别
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例
 * -运单生成-确认承运信息-录入增值服务信息”中系统规则SR9；
 * 录入预付费保密
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则
 * SR10,SR11,SR12；
 * 1.	离线开单中不生成代收货款退款收银人信息，
 * 且不录入；
 * 其代收货款的退款收银人信息在有线后提交运单时录入；
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 * 保价声明价值		数字		8	是	
 保价费率		数字、符号		8		
 保价费		数字		8		系统自动生成
 代收货款		数字		8	是	
 代收费率		数字、符号		8		
 代收手续费		数字		8		系统自动生成
 退款类型		下拉框		3	是	
 包装费		数字		8	是	
 装卸费		数字		8	是	
 送货费		数字		8	是	
 其它费用合计		数字		8	是	
 返单类别		下拉框		10	是	
 预付费保密		复选框		1	否	
 --------------------------------------
 使用量	每天处理的运单约为1000000单
 2012年全网估计用户数	营业员数量约10000名
 响应要求（如果与全系统要求 不一致的话）	系统一般要求
 使用时间段	营业部、集中开单小组上班时间
 高峰使用时间段	营业部：16：00-20：00
 集中开单小组：21：00-次日4：00
 --------------------------------------
 1.10	接口描述
 输入的尺寸可以进行加减例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5*
 体积为空时，录入尺寸后，
 填充体积。
 当尺寸修改时，
 体积随之变处。
 当修改体积时，
 尺寸不变化。 

 货物包装总件数小于等于货物总件数，
 如果大于总件数，
 提示：“包装件数不能大于总件数”
 --------------------------------------
 贵重物品规则变更： 
 1.修改现有系统对"贵重物品"的判断条件如下： 
 a. 单票单件货物，
 体积≤0.05立方且保价声明价值≥1万元； 
 b. 单票多件货物，
 平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
 （注：平均单件体积=开单总体积÷开单件数，
 平均单件保价=开单保价声明价值÷开单件数） 
 c. 高保价货物，
 单票货物保价≥10万元； 
 满足以上任意一个条件时，
 系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，
 且为灰色，不可编辑；不满足以上条件时，
 该复选框为可编辑状态，
 用户可根据实际情况自行选择是否勾选； 
 2.若"贵重物品"复选框被勾选，
 则在【储运注意事项】中自动加入提示记录"贵重物品"字段，
 若该货物为贵重物品时，
 "储运注意事项"中信息显示优先级为：贵重物品＞其他； 
 --------------------------------------
 尺寸录入框可以不录入件数，
 如件数为1时。
 因此录入框即可以输入10*10*10，
 又可以输入10*10*10*1
 进入该页面时，木架和木箱都赋初始值0，
 仍保持必填，操作员根据实际情况改，
 选择木架或木箱或两者都选择后则该值不能为0
 --------------------------------------
 ISSUE-1105
 1.运单开单时，货物的件数、
 重量及体积文本框是必填项，
 默认值为空； 
 2.件数只能是大于等于1的整数，
 重量及体积只能大于0。
 体积、重量这种过程数据保留两位小数
 --------------------------------------
 前置条件		1.	贵重物品基础资料
 2.	违禁物品基础资料
 3.	分拣目的站基础资料
 后置条件		1.	打木架录入
 2.	运单收银
 --------------------------------------
 营业员点击运单开单，进入运单开单界面。
 本界面为录入货物信息。
 界面主要分为两个部分：录入货物信息、代收木架信息录入。
 1.	录入货物信息：
 录入信息包括：货物名称、总件数、
 总重量、货物尺寸、
 总体积、货物包装、
 货物类型、是否贵重物品；
 1.1	货物名称：
 货物的名称；
 1.2	总件数：
 收货时货物的总件数；
 1.3	总重量：
 收货时货物的总重量；
 1.4	货物尺寸：
 收货时货物的尺寸；
 1.5	总体积：
 收货时货物的总体积；
 1.6	货物包装：
 货物的包装，
 其包装总件数等于打木架和打木箱后的总件数；
 1.7	货物类型：
 1.7.1	运输类型为汽运时，
 货物类型为公司规则的分拣条件的类型， 
 为唯一选择框，包括：A、B；
 1.7.2	运输类型为空运时，
 货物类型为下拉选择框，
 默认显示为普货，
 目前只这一个分类，
 该类型可做配置；
 1.8	是否贵重物品：
 货物是否为公司规则的贵重物品，
 为选择框；
 2.	代打木架信息录入；
 当包装木的件数大于等于1时，
 自动弹出对话框：“是否收入代打木架？”，是，
 则进入场代打木架信息录入界面；
 录入信息包括：代打部门、打木架货物件数、
 代打木架要求、打木架货物尺寸、打木架货物体积、
 打木箱货物件数、代打木箱要求、打木箱货物尺寸、打木架货物体积；
 2.1	代打部门：
 代打木箱或木架的部门名称；
 2.2	打木架货物件数：
 需要代打木架的货物件数；
 2.3	代打木架要求：
 代打木架的要求，
 如“1和2合打，3、4分开打”；
 2.4	打木架货物尺寸：
 需要代打木架的货物尺寸；
 2.5	打木架货物体积：
 需要代打木架的货物体积；
 2.6	打木箱货物件数：
 需要代打木架的货物件数；
 2.7	代打木箱要求：
 代打木箱的要求，如“5和6合打，加一个木托”；
 2.8	打木箱货物尺寸：
 需要代打木箱的货物尺寸；
 2.9	打木箱货物体积：
 需要代打木箱的货物体积；
 2.10	功能按钮：
 确定、关闭、重置；k
 --------------------------------------------------
 1.6.1	录入货物信息
 序号	基本步骤	相关数据	补充步骤
 1	录入货物名称		
 1.	系统自动匹配违禁品、贵重物品、限保物品基础资料，生成规则；
 2.	规则-请参见系统规则SR1；
 2	录入总件数		
 规则-请参见系统规则SR2
 3	录入总重量		
 1.	规则-请参见系统规则SR2、SR11；
 4	录入货物尺寸		
 1.	规则-请参见系统规则SR3、SR4；
 5	录入总体积		
 1.	规则-请参见系统规则SR2、SR3、SR4、SR10；
 6	录入货物包装		
 1.	当木包装大于等于1时，
 系统校验出发城市和对应目的站的走货路由中是否有有打木架功能的部门，
 若有，则提醒营业员“是否收入代打木架？”，是，
 则进入场代打木架信息录入界面”，
 并显示第一个有打木架功能的部门名称；
 若走货路由中无有打木架功能的部门，
 则不提示“是否收入代打木架？”；
 2.	规则-请参见系统规则SR5、SR6、SR10；
 7	录入货物类型		
 1.	规则-请参见系统规则SR7；
 8	录入是否贵重货物		
 1.	规则-请参见系统规则SR8；
 9       录入打木箱货物体积		
 1.	规则-请参见系统规则SR3、SR4、SR10；
 -----------------------------------
 1.	若货物为违禁品，
 则系统自动提示“货物为违禁品，不可开单！”；
 2.	若货物为贵重物品，
 则系统自动勾选“贵重物品”，
 且不可修改；
 3.	若货物为限保物品，
 则系统自动限定保价金额，
 且不可修改，
 并提示“货物为限保物品”；
 4.	违禁品、
 拒收品、
 贵重物品、
 限保物品（含保价金额上限）
 具体类型可在系统中进行配置；
 1.	货物重量单位为千克；
 2.	运单开单时，
 货物的件数、
 重量及体积文本框是必填项，
 默认值为空； 
 3.	件数只能是大于等于1的整数，
 重量及体积只能大于0。
 4.	体积、
 重量这种过程数据保留两位小数
 1.	尺寸录入文本框，
 支持'长*宽*高*件数+长*宽*高*（即多个尺寸相加）的计算；
 2.	如果件数是1，
 则不强制在录入时要“*1”；
 3.	货物尺寸为计算器输入，
 输入的尺寸可以进行加减，
 例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5，
 显示为输入文本；
 4.	尺寸计算单位为厘米，
 尺寸计算出数据后转换单位为立方米后，
 在货物体积中显示数据；
 例如：尺寸录入为：50*50*20（其中20为件数），
 则体积显示数据为：0.05； 
 5.	体积为空时，
 录入尺寸后，填充体积。
 当尺寸修改时，体积随之变处。
 当修改体积时，尺寸不变化。
 6.	体积初始值为“0”，
 仍保持必填，
 操作员根据实际情况改，
 选择木架或木箱或两者都选择后则该值不能为0

 1.	货物体积单位为立方米；
 2.	营业员可以修改通过尺寸计算器计算得出的体积数据；
 3.	系统设置货物重量体积比区间值（该值由基础资料配置），
 在运单提交时，
 系统自动对重量体积比进行校验：即重量体积比X=重量/体积； 
 3.1	当X不在设置的区间中，
 弹出提示“请确认录入的重量体积是否准确！”；
 （该弹窗有两个按钮：确定、取消）点击确定时，
 弹出确认运单信息界面；点击取消，点返回运单录入界面；
 3.2	当X在区间中，无提示；
 直接进入确认运单信息界面；
 4.	录入重量体积后，
 系统校验单票的重量体积及单件（平均单件）
 的重量体积是否满足“修改-查询行政组织业务属性”
 基础资料中的单票和单件重量体积限制；
 只要该四项中有一项不满足，
 则提示“XX超出提货网点限制，
 请重新选择提货网点！”； 
 1.	货物包装总件数小于等于货物总件数，
 如果大于总件数，
 提示：“包装件数不能大于总件数”；
 1.	当木包装件数大于等于1时，
 系统校验出发城市和对应目的站的走货路由中是否有有打木架功能的部门，
 若有，则提醒营业员“是否收入代打木架？”，是，
 则进入场代打木架信息录入界面”，
 并显示第一个有打木架功能的部门名称；
 若走货路由中无有打木架功能的部门，
 则不提示“是否收入代打木架？”；
 2.	当走货路由中有有打木架功能的部门为多个时，
 只显示系统路由中第一有有打木架功能的部门，
 且不可修改；
 1.	运输类型为汽运时，
 货物类型为唯一选择项；
 即，非A即B；默认不可勾选，
 只有当走货路由经过特定的城市时需要录入货物类型,
 特定城市可在系统中进行配置；
 2.	运输类型为空运时，
 货物类型为下拉选择框，
 默认显示为普货，
 目前只有这一个分类，
 该类型可做配置；
 1.	贵重物品判断规则： 
 a. 单票单件货物，
 体积≤0.05立方且保价声明价值≥1万元； 
 b. 单票多件货物，
 平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
 （注：平均单件体积=开单总体积÷开单件数，
 平均单件保价=开单保价声明价值÷开单件数） 
 c. 高保价货物，
 单票货物保价≥10万元； 
 满足以上任意一个条件时，
 系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，
 且为灰色，
 不可编辑；
 不满足以上条件时，
 该复选框为可编辑状态，
 用户可根据实际情况自行选择是否勾选； 
 2.	若"贵重物品"复选框被勾选，
 则在【储运注意事项】中自动加入提示记录"贵重物品"字段
 ，若该货物为贵重物品时，
 "储运注意事项"中信息显示优先级为：
 贵重物品＞其他；
 ======================================
 1.	录入的打木架货物件数和打木箱件数之和必须大于等于录入的木包装件数；
 2.	系统默认标签流水号前X的货物为需要代打木架货物，
 X等于录入的“打木架货物件数”和“打木箱货物件数”之和；
 3.	营业员在打印标签时，
 按流水号先贴要打木架或打木箱的货物；
 1.	录入的打木架货物体积和打木箱货物体积之和乘以1.4必须小于等于货物总体积；
 该1.4为打木架体积计算系统，可配置；
 2.	营业部开单时按打完木架后的包装开，
 即包装中含“木架/木箱”，
 开单件数为货物打木架/木箱前的实际件数（防止丢货），
 尺寸和重量按照以下公式计算：
 1.1	整票货物代打时：开单体积=代打货物体积*1.4；
 开单重量=所有货物重量+代打货物体积*42；
 1.2	部分货物代打时，
 开单体积=代打货物体积*1.4+未打木架货物体积；
 开单重量=所有货物重量+代打货物体积*42。
 即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
 重量另加，
 单票中未打木架的货物的体积和重量不变；
 1.3	例如：一票货物需全部代打，货物体积为1个方，
 重量为100KG，则开单体积为1.4个方，
 开单重量为100+1*42=142KG，
 收取客户包装费为150*1.4=210元；
 1.4	需要加托时，仍按照50元/个另外收取费用，
 托的重量和体积不再另加；
 营业部不需要再更改由于打木架引起重量和体积的变化；
 3.	打木架要在“对内备注”中备注“代打木架/木箱”，
 特殊要求（特别是合打情况）必须在对内备注和A4纸上都注明，
 例如：货物1、2、3合打成一件等；
 4.	开单件数为代打木架前货物实际件数，
 包装为打木架后的包装，打木架后件数发生变化后，
 需及时更改件数；
 =======================================
 1.8.1	录入货物信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 货物名称		文本		100	是	
 货物总件数		数字		4	是	
 货物总重量		数字		8	是	
 货物尺寸		数字、符号		500	否	
 货物总体积		数字		8	是	
 货物包装		数字、文本		4	是	
 货物类型		选择框		10	否	
 贵重物品		选择框		1	否	
 =======================================
 1.8.2	代打木架信息录入
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 代打部门		文本		20	是	
 打木架货物件数		数字		4	是	
 代打木架要求		文本		100	是	
 打木架货物尺寸		数字、符号		500	否	
 打木架货物体积		数字		8	是	
 打木箱货物件数		数字		4	是	
 代打木箱要求		文本		100	是	
 打木箱货物尺寸		数字、符号		500	否	
 打木箱货物体积		数字		8	是	
 ==========================================
 使用量	每天处理的运单约为1000000单
 2012年全网估计用户数	营业员数量约10000名
 响应要求（如果与全系统要求 不一致的话）	系统一般要求
 使用时间段	营业部、集中开单小组上班时间
 高峰使用时间段	营业部：16：00-20：00
 集中开单小组：21：00-次日4：00
 ============================================
 根据电话号码带出客户信息，
 仅在客户名称为空的前提下才会有效。
 即意味着由其他方式带出客户信息后，
 修改电话号码并不会触发重新的检索。
 ==========================================
 1.	使用电话号码和手机进行匹配，
 如果是唯一匹配一条CRM客户信息时，
 填充客户编码、客户名称、联系人、地址，
 如果有多条需弹出选择框进行选择， 
 如果查询不到CRM客户信息时，
 使用FOSS三个月运单历史记录中的收货信息查询，
 唯一匹配一条进行填充收货客户信息，多条进行弹窗选择，
 如果都查询不到，不做其他操作
 2.	但是对于电话号码匹配，只有当发货人手机、
 客户名称为空时，才会用电话号码检索并弹窗显示使用手机号码、
 电话号码、客户名称弹出选择框选择记录后覆盖原先记录
 3.	修改联系人时，需要清空客户名称，
 当清空客户名称时，会同时删除客户编码
 4.	使用手机号码、
 电话号码弹出选择框选择记录后覆盖原先记录
 ==========================================
 身份证号、客户编码、客户名称、
 联系人编码可以查询到全公司的客户
 ==========================================
 本界面分为两个界面：录入收货客户信息、选择收货客户。
 1.	录入收货客户信息：
 界面为信息录入界面：包括：手机、电话、
 发货收货联系人（发货收货部门）、发货收货人地址；
 1.1	手机：发货收货人手机号码；
 1.2	电话：发货收货人电话号码；
 1.3	收货联系人（收货部门）：
 收货客户的客户姓名，
 当“运单开单”中的“开单提货方式”
 为“内部带货自提”时，
 “收货联系人”字段更改为“收货部门”；
 1.4	收货人地址：收货客户的详细联系地址，
 支持国家行政区域自动过滤；
 1.5	客户名称
 1.6	客户编码
 2.	选择收货客户界面：
 界面为选择客户信息界面：包括两部分：
 客户信息列表区域、功能按钮区域；
 2.1.	客户信息列表区域：
 包括：联系人、手机、电话、
 地址（规范化地址和详细地址）；
 2.2.	功能按钮区域：
 包括：确定、取消；
 3.	    选择热门城市界面
 界面信息包含人热门城市
 4.	选择省份界面
 界面信息包含省份
 5.	选择城市界面
 界面信息保护城市
 6.	选择区县界面
 界面信息保护区县
 ==============================================
 1.6.1	录入
 序号	基本步骤	相关数据	补充步骤
 1	录入手机号码		
 1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
 如果有弹窗，
 弹窗操作见扩展1a，和如果没有，
 弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 并弹窗，弹窗操作见扩展1b；
 2.	规则-请参见系统规则SR1、SR5、SR6；
 2	录入电话号码		
 1.	如果手机号码没有填写，
 系统自动查询CRM系统中对应电话号码绑定的客户信息，
 如果有弹窗，
 弹窗操作见扩展2a，
 如果没有，
 弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 并弹窗，弹窗操作见扩展2b；
 1.	1、系统自动查询CRM系统中对应电话号码绑定的
 客户信息和FOSS历史开单记录中对应手机号码绑定的客户信息，
 并弹窗，弹窗操作参见扩展2a；
 2.	规则-请参见系统规则
 SR1、SR5 、SR6；
 ------------------------------------
 3	录入收货联系人（收货部门）		
 1.	规则-请参见系统规则SR2、SR3；
 4	录入收货人地址		
 1.	提供下拉框选择输入，
 系统自动过滤输入的行政区下一级行政级的字段；
 2.	地址在系统后台通过GIS系统进行匹配，
 如果是禁行区域，地址颜色为红色，
 如果是进仓区域，地址颜色为黄色
 3.	规则-请参见系统规则SR4；
 ===================================
 1.8.1.1	托运人信息

 字段名称 	说明 	数据类型	备注
 发货客户	发货联系人所属公司	文本	
 发货联系人	发货联系人名称	文本	
 发货人手机	发货联系人手机	文本	
 发货人电话	发货联系人电话	文本	
 发货人地址	发货联系统人的省份城市区县详细 地址	文本	
 发货人编码	客户编码	文本	
 1.8.1.2	收货人信息
 字段名称 	说明 	数据类型	备注
 收货客户	收货联系人所属公司	文本	
 收货联系人	收货联系人名称	文本	
 收货人手机	收货联系人手机	文本	
 收货人电话	收货联系人电话	文本	
 收货人地址	收货联系统人的省份城市区县详细 地址	文本	

 1.8.1.3	增值业务信息
 字段名称 	说明 	数据类型	备注
 保险价值	声明保险价值；	文本	
 代收类型	代收货款类型，即日退和三日退；	文本	
 代收货款	代收货款金额	文本	
 1.8.1.4	收款人信息
 字段名称 	说明 	数据类型	备注
 户名	收款人户名	文本	
 开户行	所属银行	文本	
 收款账号	帐号	文本	


 1.8.1.5	运输信息

 字段名称 	说明 	数据类型	备注
 到达类型	到达营业部、到达客户处	文本	
 返单类别	同运单界面的签收单返回类型；	文本	

 1.8.1.6	货物基本信息

 字段名称 	说明 	数据类型	备注
 货物名称	货物名称	文本	
 总重量	总重量	文本	
 总体积	总体积	文本	
 总件数	总件数	文本	
 货物包装	货物包装	文本	
 是否上门接货	货物是否上门接货	布尔值	

 1.8.1.7	计费付款

 字段名称 	说明 	数据类型	备注
 付款方式	现金、到付、月结；	文本	
 计费费率	计费的费率	文本	
 总运费	运单运费	文本	
 其他费用	综合服务费、燃油附加费等	文本	
 预付	预付费用	文本	
 到付	到付费用	文本	

 1.8.1.8	部门信息
 字段名称 	说明 	数据类型	备注
 部门名称	现金、到付、月结；	文本	
 部门地址	计费的费率	文本	
 部门对外电话	运单运费	文本	
 城市	所在城市	文本	


 1.8.1.9	打印其他信息
 字段名称 	说明 	数据类型	备注
 制单人	开单人	文本	
 开单日期	开单日期	文本	




 使用量	每天处理的运单约为9000单
 2012年全网估计用户数	营业员数量约10000名
 响应要求（如果与全系统要求 不一致的话）	一般要求
 使用时间段	营业部上班时间
 高峰使用时间段	无


 1.9	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述




 */
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.commons.validation.IValidationListener;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.commons.validation.ValidationErrorEvent;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.RFCInfoDialog;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.validation.descriptor.WaybillInfoDescriptor;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;

/**
 * 
 * 更改单提交Action
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午7:51:38
 */
public class RfcInfoAction implements IButtonActionListener<WaybillRFCUI> {

	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(RfcInfoAction.class);

	/**
	 * UI OBJECT
	 */
	private WaybillRFCUI ui;

	// 客户服务接口
	private ICustomerService customerService;

	private IWaybillRfcService waybillRfcService = WaybillRfcServiceFactory
			.getWaybillRfcService();

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(RfcInfoAction.class);

	/**
	 * 构造方法
	 */
	public RfcInfoAction() {
		Injector injector = GuiceContextFactroy.getInjector();
		customerService = injector.getInstance(CustomerService.class);
		LOG.debug(customerService);
	}

	/**
	 * 
	 * 变更明细显示
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:24:48
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		WaybillInfoVo bean = ui.getBinderWaybill();
		
		ui.consigneePanel.getTxtConsigneeLinkMan().setText(ui.getBinderWaybill().getReceiveCustomerContact());
		ui.consignerPanel.getTxtConsignerLinkMan().setText(ui.getBinderWaybill().getDeliveryCustomerContact());
		
		  //获取运单面板里面的数据 
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> data = model.getData();
		//提货方式
				if(bean.getSpecialValueAddedServiceType()!=null&&!"".equals(bean.getSpecialValueAddedServiceType()))
				{
					if(bean.getSpecialValueAddedServiceType().getValueCode()!=null&&!"".equals(bean.getSpecialValueAddedServiceType().getValueCode()))
					{
						if(!"".equals(bean.getReceiveMethod())&&bean.getReceiveMethod()!=null)
						{
							if(data.size()>0)
							{
							//对数据code进行封装
							List<String> list=new ArrayList<String>();
							/*public static final String instCode = "JZ";//安装code
							public static final String instsCode = "RGBL";//上楼code
							public static final String instqCode = "TS-SHF";//区域code
				*/			//对选择信息进行分装
							for(OtherChargeVo otherChargeVo:data)
							{
								if(!"".equals(otherChargeVo.getCode())&&otherChargeVo.getCode()!=null)
								{
									//对安装费和上楼费就行过滤
									if(otherChargeVo.getCode().startsWith(WaybillConstants.instCode))
									{
										list.add(WaybillConstants.instCode);
									}
									if(otherChargeVo.getCode().startsWith(WaybillConstants.instsCode))
									{
										list.add(WaybillConstants.instsCode);
									}
									else if(!otherChargeVo.getCode().startsWith(WaybillConstants.instsCode)&&!otherChargeVo.getCode().startsWith(WaybillConstants.instCode))
									{
									list.add(otherChargeVo.getCode());
									}
								}
				
							}
						    //送货上楼(家装)
							if(WaybillConstants.SEND_UPSTAIRS_EQUIP.equals(bean.getReceiveMethod().getValueCode()))
							{
								//送货安装//送货不上楼(家装)存在这两个提示错误
								if(list.contains(WaybillConstants.SHAZ)||list.contains(WaybillConstants.SHBSL)
										||!list.contains(WaybillConstants.SHSL))
								{
									MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shsl.t"));
									return;
								}
							}
							//送货安装
							if(WaybillConstants.SEND_AND_EQUIP.equals(bean.getReceiveMethod().getValueCode()))
							{
								
								//判断是否送货上楼，送货费
								if(!list.contains(WaybillConstants.SHAZ)||list.contains(WaybillConstants.SHBSL)
										||list.contains(WaybillConstants.SHSL)||!list.contains(WaybillConstants.instCode))
								{
									MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shaz.t"));
									return;
								}
							}
							//送货不上楼(家装)
							if(WaybillConstants.SEND_NO_UPSTAIRS.equals(bean.getReceiveMethod().getValueCode()))
							{
								//判断是否送货上楼，送货费
								if(list.contains(WaybillConstants.SHAZ)||!list.contains(WaybillConstants.SHBSL)
										||list.contains(WaybillConstants.SHSL))
								{
									MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shbsl.t"));
									return;
								}
							}
						}
							else
							{
								MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.message"));
								return;
							}
						}
					}
				}
		/**
		 *DMANA-3563 整车开单判断是否盈利
		 *新增对整车是否可以开单的判断
		 */
		validateVehicleFeeIsProfit(bean);
		
		List<ValidationError> valiationErrors = ui.getBinder().validate();
		List<ValidationError> finalValiationErrors = new ArrayList<ValidationError>();
		//变更类型		
		DataDictionaryValueVo rfcType = ui.getBinderWaybill().getRfcType();		
		if(rfcType == null){
			MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.msgBox.nullRfcType"));
			return;
		}
		//中止未输入差错编号需提示，根据DEFECT-3516修改
		if(WaybillRfcConstants.ABORT.equals(rfcType.getValueCode())){
			//没有出库记录允许不填写差错处理编号 //没有单票入库记录时允许不填写差错处理编号  DP-营销-FOSS-零担更改单优化 355673 2016-11-30
			if (!WaybillRfcConstants.RECEIVE_STOCK.equals(ui.getBinderWaybill().getGoodsStatus().getValueCode())
					||WaybillRfcServiceFactory.getWaybillRfcRemotingService().IsWaybillPutInStorage(bean.getWaybillNo())){
				if(StringUtils.isEmpty(bean.getErrorHandlingCode())){
					MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.msgBox.errorCode.isnotNull"));
					return;
				}				
			}
		}
		
		//校验出错
		if(valiationErrors.size() > 0){
			for(ValidationError error : valiationErrors){
				if(!error.getMessage().equals(WaybillConstants.SUCCESS)){
					finalValiationErrors.add(error);
				}
			}
			if(finalValiationErrors.size() > 0){
				IValidationListener validationListener = ui.getValidationListener();
				ValidationErrorEvent errorEvent = new ValidationErrorEvent(valiationErrors);
				validationListener.validationError(errorEvent);
				return;
			}
		}
		
		if(StringUtil.isEmpty(ui.getBinderWaybill().getRfcReason())			
				&&(ui.getBinderWaybill().getRfcCustomerReason()==null
				|| StringUtil.isEmpty(ui.getBinderWaybill().getRfcCustomerReason().getValueCode()))){

			MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.msgBox.nullRfcReason"));
			return;
		}
		try {
			//地址校验
			CommonUtils.checkAddress(bean);			
		} catch (WaybillValidateException e2) {
			if(!"".equals(e2.getMessage())){
				MsgBox.showError(MessageI18nUtil.getMessage(e2, i18n));
				return ;
			}
		}
				
		
		//中止
		if(WaybillRfcConstants.ABORT.equals(rfcType.getValueCode())){
			//整车运单中止需要校验该运单是否存在集配交接单有效数据 ,若存在需先作废交接单数据。
			/*List<HandOverBillEntity> handoverbillentity = 
					waybillRfcService.queryHandOveredRecordsByWaybillNo(bean.getWaybillNo()); 
			if(handoverbillentity!=null&&handoverbillentity.size()!=0){
					MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.exception.handOverBill"));
					return;
			 }*/
			//没有出库记录允许不填写差错处理编号
			if (!WaybillRfcConstants.RECEIVE_STOCK.equals(ui.getBinderWaybill().getGoodsStatus().getValueCode())) {
				//运单第一件流水号的库存不在开单时部门库存或有交接记录的，必须填写有效的有单无差编号
				if(!ui.getBinder().getBean().isErrorCodeIsTrue()){
					//获取差错处理编号控件
					JTextFieldValidate jText = ui.getMessagePanel().getAbortInfoPanel().getTxtErrorCode();
					//获取控件的左上角的位置
					Point p = jText.getLocation();
					//获取控件高度
					int height = jText.getHeight();
					//获取左下角的位置
					p.setLocation(p.getX(), p.getY()+height);
					//滚动屏幕，使控件显示
					Rectangle rectangle = new Rectangle(p);
					ui.getMessagePanel().getAbortInfoPanel().scrollRectToVisible(rectangle);
					//控件获取焦点
					jText.requestFocus();					
					return;
				}
			}
			//终止时需要处理 2016年4月28日 16:05:48 葛亮亮
			PtpWaybillOrgVo ptpWaybillOrgVo = bean.getPtpWaybillOrgVo();
			if(ptpWaybillOrgVo == null){
				ptpWaybillOrgVo = PtpWaybillOrgVo.init() ;
				bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
			}
		//作废
		}else if(WaybillRfcConstants.INVALID.equals(rfcType.getValueCode())){
			LOG.info("WaybillRfcConstants.INVALID.equals(rfcType.getValueCode())");
			//终止时需要处理 2016年4月28日 16:05:48 葛亮亮
			PtpWaybillOrgVo ptpWaybillOrgVo = bean.getPtpWaybillOrgVo();
			if(ptpWaybillOrgVo == null){
				ptpWaybillOrgVo = PtpWaybillOrgVo.init() ;
				bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
			}
		//转运、返货、更改
		}else{
			if(!WaybillRfcConstants.TRANSFER.equals(rfcType.getValueCode()) && 
					!WaybillRfcConstants.RETURN.equals(rfcType.getValueCode())){
				List<WaybillRfcChangeDetailDto> detailDtos = ui.getMessagePanel().getChangedInfoPanel().getTableData();
				if((detailDtos == null || detailDtos.size() == 0) && !ui.getBinderWaybill().isOtherChargeChanged()){
					MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.msgBox.nullChangedInfo"));
					return;
				}
			}
			
			try{
				//如果在客户圈就赋值成主客户编码
				if(StringUtils.isNotBlank(bean.getIsCircle()) && StringUtils.equals("Y",bean.getIsCircle())){
					bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
				}
				//验证
				validate(ui.getBinderWaybill());
				
				//合伙人开单需要验证开单金额（开单和更改都走同一的验证） 2016年1月25日 18:26:19 葛亮亮
				if(!true == bean.getIsWholeVehicle() && BZPartnersJudge.IS_PARTENER)
				{
					partnerMonthlyPrestoreCheck(bean);
				}
			}catch (WaybillValidateException e1) {
				LOG.error("WaybillValidateException", e1);
				MsgBox.showError(e1.getMessage());
				return;
			}
		}
		//add by 306486 校验分拣数据获取分拣结果
        //获取分拣开关
        String Switch=CommonUtils.readConfValue(WaybillConstants.LIGHT_GOODS_SORTING);
		//分拣开关-开 开始校验
		if(StringUtils.isNotBlank(Switch)&&
                Switch.equals(WaybillConstants.YES)&&
                StringUtils.isNotBlank(bean.getWaybillDto().getActualFreightEntity().getSortingResult()))
		{
		    String oldSortingResult=bean.getWaybillDto().getActualFreightEntity().getSortingResult();
		    String newSortingResult="";
            if(CommonUtils.getSortingFlagFromProductCode(bean.getProductCode().getCode()).equals(WaybillConstants.NO)||
                    CommonUtils.getSortingFlagFromWoodAndSalver(bean.getWood()).equals(WaybillConstants.NO)||
                    CommonUtils.getSortingFlagFromWoodAndSalver(bean.getSalver()).equals(WaybillConstants.NO)||
                    CommonUtils.getSortingFlagFromGoodsQtyTotal(bean.getGoodsQtyTotal()).equals(WaybillConstants.NO)||
                    CommonUtils.getSortingFlagFromGoodsWeightTotal(bean.getGoodsWeightTotal(),bean.getGoodsQtyTotal()).equals(WaybillConstants.NO)||
                    CommonUtils.getSortingFlagFromOtherPackage(bean.getOtherPackage()).equals(WaybillConstants.NO))
            {
                newSortingResult=WaybillConstants.NO;
            }else{
                newSortingResult=WaybillConstants.YES;
            }
            //设置分拣结果
            bean.getWaybillDto().getActualFreightEntity().setSortingResult(newSortingResult);
            if(StringUtils.isNotBlank(newSortingResult)){
                String sortingResultIsChanged =oldSortingResult.equals(newSortingResult)?WaybillConstants.NO:WaybillConstants.YES;
                bean.getWaybillDto().setSortingResultIsChanged(sortingResultIsChanged);
            }
        }
		
		// 处理内部带货
		Common.innerPickup(bean, ui, bean.getFinalReceiveMethod().getValueCode());
		//客戶圈客户 异地调货项目 把主客户编码设置成实际发货客户 311417
		if(StringUtils.isNotBlank(bean.getIsCircle()) && StringUtils.equals("Y",bean.getIsCircle())){
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
		}
		//变更明细对话框
		RFCInfoDialog dialog = new RFCInfoDialog(ui);
		BigDecimal oldTotalFee = ui.getOriginWaybill().getTotalFee();
		BigDecimal newTotalFee = ui.getBinderWaybill().getTotalFee();
		//计算总费用差额
		BigDecimal changedTotalFee = newTotalFee.subtract(oldTotalFee).abs();
		dialog.setTotalFeeChange(changedTotalFee);
		WindowUtil.centerAndShow(dialog);
	}
	
	//合伙人校验月结和预存资金池 2016年1月29日 10:44:22 葛亮亮
	public void partnerMonthlyPrestoreCheck(WaybillPanelVo bean){
		
		ConfigurationParamsEntity entityDate= BaseDataServiceFactory.getBaseDataService().queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410,FossConstants.ROOT_ORG_CODE);
		if (StringUtils.isNotEmpty(entityDate.getConfValue())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parterOnlineDate;
			try {
				parterOnlineDate = sdf.parse(entityDate.getConfValue());
				if(!bean.getBillTime().after(parterOnlineDate)){
					return;
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//登陆人信息
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		//登陆人部门编号
		String partnerDeptCode = user.getEmployee().getDepartment().getCode();
		//组织标杆编码
		String unifieldCode = user.getEmployee().getDepartment().getUnifiedCode();
		//开单总费用
		//BigDecimal limit = bean.getTotalFee() != null ? bean.getTotalFee() : BigDecimal.ZERO;
		//改前总金额  --之前资金池校验传的是修改后总金额，现改为更改前总金额   by 352676  2017年2月20日16:47:03
		//校验bean.getPtpWaybillOrgVo()是否为null
		BigDecimal limit = BigDecimal.ZERO;
		if (bean.getPtpWaybillOrgVo() != null) {
			limit = bean.getPtpWaybillOrgVo().getTotalFee() != null ? bean.getPtpWaybillOrgVo().getTotalFee() : BigDecimal.ZERO;
		}
		//代收货款
		BigDecimal codAmount = bean.getCodAmount() != null ? bean.getCodAmount() : BigDecimal.ZERO ;
				
		//预付金额
		BigDecimal prePayAmount = bean.getPrePayAmount() != null ? bean.getPrePayAmount() : BigDecimal.ZERO;
		// 订单付款方式
		String payCode = bean.getPaidMethod().getValueCode();
		
		//月结,从 杜军辉 接口验证
		if(WaybillConstants.MONTH_PAYMENT.equals(payCode))
		{
			if(prePayAmount.compareTo(BigDecimal.ZERO) > 0){
				//传值用的实体类
				PartenerDeductDto deductDto = new PartenerDeductDto();						
				deductDto.setPartnerDeptCode(partnerDeptCode);
				//月结传预付金额
				deductDto.setLimit(prePayAmount.toString());
				//接口返回的实体
				SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeductResponse = waybillRfcService.partenerMonthlyLineDeduct(deductDto);
				Boolean isSuccess = partenerMonthlyLineDeductResponse.isSuccess();
				String errorMsg = partenerMonthlyLineDeductResponse.getErrorMsg();
				if(!isSuccess){ //验证失败
					throw new WaybillValidateException(errorMsg);
				}
			}
		}else{//剩余的从黄老板的接口走
			if(limit.compareTo(BigDecimal.ZERO) > 0){
				//传值用的实体类
				PartenerPrestoreDeductDto restoreDeductDto = new PartenerPrestoreDeductDto();
				//这个接口需要传递部门标杆编码
				restoreDeductDto.setPartnerOrgCode(unifieldCode);
				restoreDeductDto.setAmount(limit.subtract(codAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
				
				//接口返回实体
				SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse = new SynPartenerPrestoreDeductResponse();
				partenerPrestoreDeductResponse = waybillRfcService.partenerPrestoreDeductResponse(restoreDeductDto);
				Boolean isSuccess = partenerPrestoreDeductResponse.isSuccess();
				String errorInfo = partenerPrestoreDeductResponse.getErrorInfo();
				if(!isSuccess){ //验证失败
					throw new WaybillValidateException(errorInfo);
				}
			}
		}
	}

	/**
	 * 
	 * 校验运单合法性
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-19 上午7:48:51
	 */
	private void validate(WaybillInfoVo bean) {
		// zxy 20131031 BUG-58082 start 新增：提交的时候检查发货人和收获人地址是否为空判断(跟计算总运费逻辑一样)
		if (StringUtils.isEmpty(ui.consignerPanel.getTxtConsignerArea()
				.getText())) {
			// 原件签收单返回
			if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
					.getReturnBillType().getValueCode())) {
				JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
				AddressFieldDto consignerAddress = jd.getAddressFieldDto();
				if (StringUtils.isBlank(jd.getText())
						|| consignerAddress == null
						|| StringUtils.isBlank(consignerAddress.getProvinceId())
						|| StringUtils.isBlank(bean.getDeliveryCustomerAddress())) {
					ui.consignerPanel.getTxtConsignerArea().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
				}
			}
		}

		if (ui.consigneePanel.getTxtConsigneeArea().isEnabled()) {
			// 提货方式
			if (bean.getFinalReceiveMethod() == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.transportInfoPanel.pickMode.label.isNull"));
			}
			String code = bean.getFinalReceiveMethod().getValueCode();
			if (WaybillConstants.DELIVER_NOUP.equals(code)
					|| WaybillConstants.DELIVER_FREE.equals(code)
					|| WaybillConstants.DELIVER_STORAGE.equals(code)
					|| WaybillConstants.DELIVER_UP.equals(code)
					|| WaybillConstants.DELIVER_FREE_AIR.equals(code)
					|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)
					|| WaybillConstants.DELIVER_UP_AIR.equals(code)
					|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
				JAddressField jd = ui.consigneePanel.getTxtConsigneeArea();
				AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
//				String consigneeAddress = ui.consigneePanel
//						.getTxtConsigneeArea().getText();
				if (StringUtils.isBlank(jd.getText())
						|| consigneeAddress == null
						|| StringUtils.isBlank(consigneeAddress.getProvinceId())
						|| StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
					ui.consigneePanel.getTxtConsigneeArea().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
				}
			}
		}
		// zxy 20131031 BUG-58082 end 新增：提交的时候检查发货人和收获人地址是否为空判断(跟计算总运费逻辑一样)

		if (StringUtil.isEmpty(ui.getWaybillInfoPanel().getIncrementPanel()
				.getTxtCashOnDelivery().getText())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.errorCodAmount"));

		}

		Common.getPackage(bean);
		//校验伙伴开单
		//这里原先是检验伙伴开单时公布价运费不能低于标准乘以一定比例，但是现在已经不再适用，现在只是给予提示 2016年1月21日 17:35:49 葛亮亮
//		if(ui.getWaybillInfoPanel().getBasicPanel().getPartnerCheckBox().isSelected()){
//			validateTransportFee(bean);
//		}
		// 验证整车业务
		validateVehicleNumber(bean);

		validateCod(bean);

		// 提货方式校验
		validateReceiveMethod(bean);

		// 只允许合同客户才可以选择免费送货
		Common.validateDeliverFree(bean, ui);
		/**
		 * 当运输时性质为门到门的时候不能选择自提
		 * 
		 * Common.setDTDSelectUpOwn(bean); Common.setDTDSelectUpTRF(bean);
		 */
		// 验证客户信息 -- 联系人不能为空
		validateCustomer(bean);

		// 验证配载线路
		// validateFreightRout(bean);
		// 预付费保密校验
		validateSecretPrepaid(bean);
		// 验证偏线的保险声明价值
		validateProduct(bean);
		// 验证空运业务-机场自提
		validateAir(bean);
		// 验证费用
		validateFee(bean);
		// 在上门接货的时候 司机工号必填
		// validateDriverNo(bean);
		// 检查件数
		valiadateGoodQtyAndWoodQty(bean);
		// 手机及电话号码必须录入一个
		validatePhone(bean);
		// 付款方式选择临欠 不能超过客户信用额度
		// validateCustomerPrepayCredit(bean);
		// 判断客户信用度以结算判断为准，CRM的信用度暂不用判断
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付，若servicetype为12表示裹裹订单不校验
		//String servicetype = Common.getServiceType(bean.getOrderNo());
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
				.getValueCode())) {
			if (!WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())  && !WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean
					.getOrderChannel())
					&& !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean
							.getOrderPayment())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.errorPaidMethod"));
			}
		}

		// 验证走货路径
		validateTransportPath(bean);

		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
		validatePayment(bean);
//		boolean isNeedCheck = false;

		validateCustomerPickupOrgName(bean);

		/**
		 * 如果没有目的站的更改，不需要校验：检查该网点是否支持该提货方式
		 */
		// List<WaybillRfcChangeDetailDto> rfcChangeDetailList =
		// ui.getMessagePanel().getChangedInfoPanel().getTableData();
		// for (WaybillRfcChangeDetailDto waybillRfcChangeDetailDto :
		// rfcChangeDetailList) {
		// if (waybillRfcChangeDetailDto.getPropertyName() != null
		// && "customerPickupOrgName".equals(waybillRfcChangeDetailDto
		// .getPropertyName())) {
		// isNeedCheck = true;
		// break;
		// }
		// }
		// if(isNeedCheck && !bean.getIsWholeVehicle()){
		// validateCustomerPointBySelfPickup(bean);
		// }

		// 验证省市区信息 DMANA-4292
		Common.validateCity(bean);
		validateSettlementDay(bean);

		//liding comment
		//NCI项目,付款方式银行卡时取消校验
		/**
		 * 验证交易流水号是否满足开单要求
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-23
		 */
//		Common.validateTransactionSerialNumber(bean, ui);
		validateSpecialValueAddService(bean);
		
		// ===========LianHe/"内部带货"时,校验工号不为空，并校验工号信息和客户信息一致 /2017年1月11日/start=======
		CommonUtils.validateInternalDelivery(bean);
		// ===========LianHe/"内部带货"时,校验工号不为空，并校验工号信息和客户信息一致 /2017年1月11日/end=======
	}
    /**
     * 对家装运单进行校验，不支持到付，代收货款，签收单返单。
     * mabinliang-254615
     * @param bean
     */
	private void validateSpecialValueAddService(WaybillInfoVo bean) {
		if (bean.getSpecialValueAddedServiceType()!=null&&bean.getSpecialValueAddedServiceType().getValueCode()!=null) {
			if(bean.getCodAmount().compareTo(BigDecimal.ZERO)!=0||
			   (bean.getPaidMethod()!=null&&WaybillConstants.PAYMENT_TO_PAY.equals(bean.getPaidMethod().getValueCode()))||
			   (bean.getReturnBillType()!=null&&!bean.getReturnBillType().getValueCode().equals(WaybillConstants.NOT_RETURN_BILL))){
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.listener.Waybill.special"));
	
			}
			
		}
		
	}

	/**
	 * @param bean
	 */
	private void validateCustomerPickupOrgName(WaybillInfoVo bean) {
		String newName = bean.getCustomerPickupOrgName();
		String oldName = ui.getOriginWaybill().getCustomerPickupOrgName();

		// //名称不一致 但是code一致
		if (newName != null && !newName.equals(oldName)) {
			BranchVo vo = bean.getCustomerPickupOrgCode();
			BranchVo voOld = ui.getOriginWaybill().getCustomerPickupOrgCode();
			if (vo != null && voOld != null && vo.getCode() != null
					&& vo.getCode().equals(voOld.getCode())) {

				SaleDepartmentEntity dept = waybillRfcService
						.querySaleDepartmentByCode(vo.getCode());
				if (dept != null) {
					String newName2 = dept.getName();
					if (!newName2.equals(newName)) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePickupOrg"));

					}
				}
			}
		}
	}

	/**
	 * 
	 * 验证客户信息 -- 联系人不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-18 上午09:55:02
	 */
	private void validateCustomer(WaybillPanelVo bean) {
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (StringUtils.isEmpty(bean.getDeliveryCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.innerPickUp.deliveryCustomer"));
			}

			if (StringUtils.isEmpty(bean.getReceiveCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.innerPickUp.receiveCustomer"));
			}
		} else {
			if (StringUtils.isEmpty(bean.getDeliveryCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.deliveryCustomer"));
			}

			if (StringUtils.isEmpty(bean.getReceiveCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.receiveCustomer"));
			}
		}
		
		//校验收货联系人的电话
		if(ui.consigneePanel.getTxtConsigneePhone().isEnabled()){
			String result = new WaybillInfoDescriptor(ui).validateReceiveCustomerPhone(bean.getReceiveCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consigneePanel.getTxtConsigneePhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
		    }
			
		}
		// 校验发货联系人的电话
		if (ui.consignerPanel.getTxtConsignerPhone().isEnabled()) {
			String result = new WaybillInfoDescriptor(ui).validateDeliveryCustomerPhone(bean.getDeliveryCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consignerPanel.getTxtConsignerPhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
			}
		}
				
	}

	/**
	 * 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
	 * 
	 * @param bean
	 */
	private void validateCustomerPointBySelfPickup(WaybillInfoVo bean) {
		// 获得网点
		BranchVo customerPickupOrgCode = null;
		// 变更类型
		String rfcType = bean.getRfcType().getValueCode();
		//
		if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			customerPickupOrgCode = bean.getRtnCustomerPickupOrgCode();
		} else if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			customerPickupOrgCode = bean.getTfrCustomerPickupOrgCode();
		} else if (WaybillRfcConstants.INSIDE_CHANGE.equals(rfcType)
				&& ((bean.getReturnRecordList().size() > 0 || bean
						.getTransferRecordList().size() > 0))
				&& ui.getWaybillInfoPanel().getTransferPanel()
						.getTransportRecordTabPanel().getTransferRecordPanel()
						.getTable().getSelectedRow() == ui
						.getWaybillInfoPanel().getTransferPanel()
						.getTransportRecordTabPanel().getTransferRecordPanel()
						.getTable().getRowCount() - 1) {
			customerPickupOrgCode = bean.getRecordCustomerPickupOrgCode();
		} else {
			customerPickupOrgCode = bean.getCustomerPickupOrgCode();
		}

		if (customerPickupOrgCode != null) {

			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod()
					.getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIRPORT_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())) {

				// 不支持自提
				if (!FossConstants.YES.equals(customerPickupOrgCode
						.getPickupSelf())) {
					// bean.setReceiveMethod(ui.getOriginWaybill().getReceiveMethod());
					// ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getModel().setSelectedItem(bean.getReceiveMethod());
					throw new WaybillValidateException(
							bean.getCustomerPickupOrgName()
									+ i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.pickupNotSupport"));

				}
			} else if (WaybillConstants.DELIVER_FREE.equals(bean
					.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_STORAGE.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_NOUP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_FREE_AIR.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_NOUP_AIR.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP_AIR.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_INGA_AIR.equals(bean
							.getReceiveMethod().getValueCode())) {

				// 不支持送货上门
				if (!FossConstants.YES.equals(customerPickupOrgCode
						.getDelivery())) {
					// bean.setReceiveMethod(ui.getOriginWaybill().getReceiveMethod());
					// ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getModel().setSelectedItem(bean.getReceiveMethod());
					throw new WaybillValidateException(
							bean.getCustomerPickupOrgName()
									+ i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.pickupNotSupport"));

				}
			}
		}
	}

	/**
	 * 
	 * 验证整车
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-2 下午06:06:50
	 */
	private void validateVehicleNumber(WaybillPanelVo bean) {
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			// 没有经过营业部则不允许到付金额大于0
			if (bean.getIsPassDept() != null && !bean.getIsPassDept()) {
				if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.vehicle"));
				}
			}

			BigDecimal rateLow = bean.getWholeVehicleActualfeeFlowRangeLow();
			if (rateLow == null) {
				rateLow = BigDecimal.valueOf(NumberConstants.NUMBER_0_1d);
			}

			BigDecimal serviceFee = bean.getServiceFee();
			// zxy 20131112 BUG-58343 start 修改：sub 改成 add 按开单的为准
			BigDecimal lowrate = BigDecimal.ONE.add(rateLow);
			// zxy 20131112 BUG-58343 end 修改：sub 改成 add 按开单的为准
			BigDecimal lowLevel = bean.getWholeVehicleAppfee()
					.multiply(lowrate);

			if (bean.getTransportFee().subtract(serviceFee).compareTo(lowLevel) < 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.listener.Waybill.transportFee.three")
								+ lowLevel.doubleValue());
			}
		}
	}

	private void validatePayment(WaybillInfoVo bean) {
		if(StringUtil.isNotEmpty(bean.getIsCircle()) && "Y".equals(bean.getIsCircle())){
			//客户圈客户只能选择到付和月结-- 311417 
			validateIsCirclePayment(bean);
		}
		//零担电子运单付款方式 只能为 到付和月结 -- 272311-sangwenhao
		if(StringUtils.equals(bean.getWaybillType(), WaybillConstants.WAYBILL_STATUS_LTLEWAYBILL)){
			if(!(StringUtils.equals(bean.getPaidMethod().getValueCode(), WaybillConstants.MONTH_PAYMENT)
					||StringUtils.equals(bean.getPaidMethod().getValueCode(), WaybillConstants.ARRIVE_PAYMENT))){
				//零担电子面单付款方式只能选择到付和月结
				throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.LtlEwaybillPayment"));
			}
		}
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付，若为servicetype为12则不进行校验
		onlinePaidMethod(bean);
		// 判断是否能开月结
		monthPayment(bean);

		// 根据DEFECT-1433修改，不支持到付不能有到付金额
		if (!bean.getIsWholeVehicle()) {
			// 提货网点是否可以货到付款
			if (!FossConstants.YES.equals(bean.getArriveCharge())) {
				if (bean.getToPayAmount().intValue() > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.changing.calculateAction.exception.noArrivedPay"));
				}
			}
		}
	}

	private void monthPayment(WaybillInfoVo bean) {
		/*if(!StringUtils.equals("Y", bean.getIsCircle())){
			if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
				CusBargainVo vo = new CusBargainVo();
				vo.setChargeType(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(bean.getBillTime());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				boolean isOk = waybillRfcService.isCanPaidMethod(vo);
				if (!isOk) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.changing.listener.Waybill.exception.NocanPaidMethod"));
				}
			}
		}*/
		// 判断是否能开月结
			if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {

				CusBargainVo vo = new CusBargainVo();
				vo.setChargeType(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(bean.getBillTime());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				boolean isOk = waybillRfcService.isCanPaidMethod(vo);
				if (!isOk) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.changing.listener.Waybill.exception.NocanPaidMethod"));
				}
			}
	}

	private void onlinePaidMethod(WaybillInfoVo bean) {
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
				.getValueCode())) {
			if (StringUtils.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.crmOrderChannelOnlineNoCustNo"));
			}
			//String servicetype = Common.getServiceType(bean.getOrderNo());
			if (!WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())  && (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean
					.getOrderChannel())
					|| !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean
							.getOrderPayment()))) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.crmOrderChannelOnline"));
			}
		}
	}

	private void validateIsCirclePayment(WaybillInfoVo bean) {
		//客户圈客户为统一结算只能选择到付和月结-- 311417 
		if(StringUtils.equals(WaybillConstants.IS_NOT_NULL_FOR_AI,bean.getStartCentralizedSettlement())){
			//是否統一結算為是合同是月結
			if(bean.getCusBargainNewEntity() !=null && StringUtil.equals(WaybillConstants.MONTH_END, bean.getCusBargainNewEntity().getChargeType())){
				boolean isOk = false;
				
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					isOk= true; 
				}
				//判断发货客户是否满足月结的条件
//				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
//					CusBargainVo vo=new CusBargainVo();
//					vo.setChargeType(WaybillConstants.MONTH_END);
//					vo.setCustomerCode(bean.getMainCustomerCode());
//					vo.setBillDate(new Date());
//					vo.setBillOrgCode(bean.getReceiveOrgCode());
//					isOk =  WaybillRfcServiceFactory.getWaybillRfcService().isCanPaidMethod(vo);
//				}
				if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					isOk= true; 
				}
				if(!isOk){
					throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.settlementPaidMethod"));
				}
				//是否統一結算為是合同不是月結
			}else if(!WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.arrivePaidMethod"));
			}
		}else{
			if(bean.getCusBargainNewEntity() !=null && !StringUtil.equals(WaybillConstants.MONTH_END, bean.getCusBargainNewEntity().getChargeType()) 
					&& WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				//是否統一結算為否合同不是月結
				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.noMonthPaidMethod"));
			}
		}
	}

	/**
	 * 
	 * 代收货款业务规则校验
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-5 上午11:35:22
	 */
	private void validateCod(WaybillInfoVo bean) {

		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal codAmount = bean.getCodAmount();

		if (codAmount == null || codAmount.compareTo(zero) == 0) {
			if (bean.getRefundType() != null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.zeroCodAmount"));
			}
			if (JOptionPane.YES_OPTION != JOptionPane
					.showConfirmDialog(
							ui,
							i18n.get("foss.gui.changing.rfcInfoAction.confirmDialog.validateCod"),
							i18n.get("foss.gui.changing.waybillRFCUI.common.prompt"),
							JOptionPane.YES_NO_OPTION)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.nullCodAmount"));
			}
		} else {
			if (bean.getRefundType() == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.nullRefundType"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean
						.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName())
							|| StringUtils.isEmpty(bean.getAccountCode())
							|| StringUtils.isEmpty(bean.getAccountBank())) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.changing.rfcInfoAction.exception.nullAccountDetail"));
					}
				}
			}
		}
	}

	/**
	 * 在上门接货的时候 司机工号必填
	 * 
	 * @param bean
	 */
	protected void validateDriverNo(WaybillPanelVo bean) {
		if (bean.getPickupToDoor() != null && bean.getPickupToDoor()
				&& StringUtils.isEmpty(bean.getDriverCode())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.changing.rfcInfoAction.exception.nullDriverCode"));
		}
	}

	/**
	 * 
	 * 提货方式校验
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-5 下午2:08:47
	 */
	private void validateReceiveMethod(WaybillInfoVo bean) {
		// 客户要求不能选择内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (isCustomerChange(bean)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.notInnerPickup"));
			}
			// 在中转库存的运单不能修改内部带货自提
			if (ui.getOriginWaybill().getReceiveMethod() != null
					&& !WaybillConstants.INNER_PICKUP.equals(ui
							.getOriginWaybill().getReceiveMethod()
							.getValueCode())
					&& !WaybillRfcConstants.RECEIVE_STOCK.equals(bean
							.getGoodsStatus().getValueCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.notReceiveStock"));
			}
		}
		//客户原因(更改、转运、返货) 不能更改 提货方式 为 内部带货送货
		if(bean.getReceiveMethod() != null && WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode()) && isCustomerChange(bean)){
			String rfcType = bean.getRfcType().getValueCode() ;
			if(WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)
					|| WaybillRfcConstants.TRANSFER.equals(rfcType)
					|| WaybillRfcConstants.RETURN.equals(rfcType)){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.customer.pickupNotSupport"));
			}
		}
		// 在中转库存的运单不能修改内部带货送货
		if (ui.getOriginWaybill().getReceiveMethod() != null
				&& WaybillConstants.DELIVER_INNER_PICKUP.equals(ui
						.getOriginWaybill().getReceiveMethod()
						.getValueCode())
				&& !WaybillRfcConstants.RECEIVE_STOCK.equals(bean
						.getGoodsStatus().getValueCode())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.changing.rfcInfoAction.exception.notReceiveStockTwo"));
		}

		// 客户要求不能选择免费自提
		if (WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (isCustomerChange(bean)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.notFreeAirPickup"));
			}
			// 在中转库存的运单不能修改免费自提
			// if (ui.getOriginWaybill().getReceiveMethod()!= null &&
			// !WaybillConstants.AIR_PICKUP_FREE.equals(ui.getOriginWaybill().getReceiveMethod().getValueCode())
			// &&!WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode()))
			// {
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.changing.rfcInfoAction.exception.notFreeStock"));
			// }
		}
	}

	/**
	 * 
	 * 是否客户变更
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-5 下午1:47:23
	 */
	private boolean isCustomerChange(WaybillInfoVo bean) {
		String rfcSource = bean.getRfcSource();
		return WaybillRfcConstants.CUSTOMER_REQUIRE.equals(rfcSource);
	}

	/**
	 * 验证走货路径
	 * 
	 * @param bean
	 */
	private void validateTransportPath(WaybillInfoVo bean) {
		if (bean.getIsWholeVehicle() != null
				&& Boolean.TRUE.equals(bean.getIsWholeVehicle())) {
			// 整车没有走货路径
			// 此处无需判断
			return;
		}

		/**
		 * 如果没有目的站的更改，不需要校验：检查该网点是否支持该提货方式
		 */
		boolean isNeedCheck = false;

		List<WaybillRfcChangeDetailDto> rfcChangeDetailList = ui
				.getMessagePanel().getChangedInfoPanel().getTableData();

		if (rfcChangeDetailList != null) {
			for (WaybillRfcChangeDetailDto waybillRfcChangeDetailDto : rfcChangeDetailList) {
				if (waybillRfcChangeDetailDto.getPropertyName() != null
						&& "customerPickupOrgName"
								.equals(waybillRfcChangeDetailDto
										.getPropertyName())) {
					isNeedCheck = true;
					break;
				}
			}

			if (isNeedCheck) {
				// 拿到运单面板上的走货路径对象
				// 1、运单导入时查询获得
				// 2、修改提货网点重新查询获得
				OrgInfoDto dto = bean.getOrgInfoDto();
				// 根据新运输性质校验走货路径
				// 找不到走货路径无法提交
				String productCode = null;
				if (bean.getFinalProductCode() != null) {
					productCode = bean.getFinalProductCode().getCode();
				}
				// 根据ISSUE-3199修改，空运走货路径实体允许为空，因为走货路径是拼接起来的，虚拟编码为空
				if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
						.equals(productCode)) {
					if (dto == null) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.changing.rfcInfoAction.exception.nullFreightRoute"));
					}
				} else {
					if (dto == null || dto.getFreightRoute() == null) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.changing.rfcInfoAction.exception.nullFreightRoute"));
					}
				}
			}
		}

	}

	/**
	 * 检查件数
	 * 
	 * @param bean
	 */
	private void valiadateGoodQtyAndWoodQty(WaybillInfoVo bean) {
		int goodTotal = bean.getGoodsQtyTotal();
//		int wood = bean.getWood();
		int hasGood = 0;
//		int hasWood = 0;
		List<LabeledGoodChangeHistoryDto> list = bean
				.getLabeledGoodChangeHistoryDtoList();
		for (int i = 0; i < list.size(); i++) {
			LabeledGoodChangeHistoryDto dto = list.get(i);

			if (!LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE
					.equals(dto.getChangeType())) {
				hasGood++;
			}
			/*
			if (LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD
					.equals(dto.getChangeType())
					|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW
							.equals(dto.getChangeType())) {
				hasWood++;
			}*/
		}

		Integer goodQtyTotalNew = bean.getGoodsQtyTotal();
		Integer oldTotal = ui.getOriginWaybill().getGoodsQtyTotal();

		// 只有在改变件数的情况下 才做这个检查
		if (oldTotal != null && goodQtyTotalNew != null
				&& goodQtyTotalNew.intValue() != oldTotal.intValue()) {
			if (hasGood != goodTotal) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.rfcInfoAction.exception.wrongGoodTotal"));
			}
			// if(hasWood>wood ){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.changing.rfcInfoAction.exception.overHasWood"));
			// }
		}

	}

	/**
	 * 
	 * 手机及电话号码必须录入一个
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午03:49:00
	 */
	private void validatePhone(WaybillPanelVo bean) {
		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}

		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}
	}

	/**
	 * 
	 * 验证空运业务
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 上午09:19:51
	 */
	private void validateAir(WaybillPanelVo bean) {
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.one"));
				}
			}

			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.two"));
			}
		}
	}

	/**
	 * 
	 * 验证各种费
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-9 下午12:09:27
	 * @param bean
	 * @see
	 */
	private void validateFee(WaybillPanelVo bean) {
		if (bean.getTotalFee().compareTo(BigDecimal.ZERO) < 0) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFee.one"));
		}
	}

	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProduct(WaybillPanelVo bean) {
		
		// 判断产品是否偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean
				.getProductCode().getCode())) {
			BigDecimal insuranceAmount = bean.getInsuranceAmount();
			BigDecimal maxInsuranceAmount = bean.getMaxInsuranceAmount();
			if (insuranceAmount == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullInsuranceAmount"));
			}
			if (maxInsuranceAmount == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMaxInsuranceAmount"));
			}
			if (insuranceAmount.compareTo(maxInsuranceAmount) > 0) {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.overMaxInsuranceAmount")
								+ maxInsuranceAmount);
			}
		}

		// 判断产品是否空运
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean
				.getProductCode().getCode())) {
			// 合票方式
			DataDictionaryValueVo freightMethod = bean.getFreightMethod();
			if (freightMethod == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFreightMethod"));
			}

			// 航班类别
			DataDictionaryValueVo flightNumberType = bean.getFlightNumberType();
			if (flightNumberType == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFlightNumberType"));
			}
		}

	}

	/**
	 * 
	 * 预付费保密校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:45:05
	 */
	private void validateSecretPrepaid(WaybillPanelVo bean) {
		boolean bool = bean.getSecretPrepaid();
		if (bool) {
			if (bean.getPrePayAmount().longValue() == 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullPrePayAmount"));
			}
		}
	}

	/**
	 * 
	 * 验证走货路径
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-25 下午3:36:16
	 * @param bean
	 * @see
	 */
	/*
	 * private void validateFreightRout(WaybillPanelVo bean){
	 * //如果不是空运，区分AB货时必须选择货物类型（空运货物类型和汽运货物类型不是同一个字段） if
	 * (!ProductEntityConstants.
	 * PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
	 * if(bean.getGoodsTypeIsAB()!=null&&bean.getGoodsTypeIsAB()){
	 * if(StringUtils.isEmpty(bean.getGoodsType())) { throw new
	 * WaybillValidateException(i18n.get(
	 * "foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.four"
	 * )); } } }
	 * 
	 * }
	 */

	/**
	 * 
	 * 验证能否发中止单
	 * 
	 */
	private void validateAbort(WaybillInfoVo bean) {
		// 运单第一件流水号的库存在开单时部门库存且没有交接记录的直接中止
		if (WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus()
				.getValueCode())
				&& ((waybillRfcService.queryHandOveredRecordsByWaybillNo(bean
						.getWaybillNo()) == null || waybillRfcService
						.queryHandOveredRecordsByWaybillNo(bean.getWaybillNo())
						.size() == 0))) {
			bean.setErrorCodeIsTrue(true);
		} else {
			// 运单第一件流水号的库存不在开单时部门库存或有交接记录的，必须填写有效的有单无差编号
			if (!ui.getBinder().getBean().isErrorCodeIsTrue()) {
				// ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				// MsgBox.showError(i18n.get("foss.gui.changing.rfcInfoAction.msgBox.errorCode"));
				return;
			}
		}
	}

	/**
	 * 
	 * UI注入
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	public void setInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

	/**
	 * 
	 * 
	 * @Function: 
	 *            com.deppon.foss.module.pickup.creating.client.action.WaybillSubmitAction
	 *            .validateVehicleFeeIsProfit
	 * @Description:验证整车开单是否盈利
	 * 
	 *                         校验整车报价是否符合规则
	 *                         校验公式“（开单总运费—整车运费冲减）—约车报价—（开单总运费—整车运费冲减
	 *                         ）*其他成本参数—包装费*包装费参数—货物重量*重量参数+约车报价*车价参数—人力成本参数＞0
	 * @param bean
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-20 下午1:45:43
	 * 
	 *                  Modification History: Date Author Version Description
	 *                  ----
	 *                  ------------------------------------------------------
	 *                  ------- 2014-11-20 DP-FOSS-YANGKANG v1.0.0 create
	 */
	public void validateVehicleFeeIsProfit(WaybillInfoVo bean) {
		if (bean.getIsWholeVehicle()) {
			/**
			 * 读取配置参数，根据配置参数决定是否进行整车报价的校验
			 */
			ConfigurationParamsEntity entity = BaseDataServiceFactory
					.getBaseDataService().queryConfigurationParamsByEntity(
							null,
							PricingConstants.WHOLE_VEHICLE_VALIDATION_TIME,
							null);

			if (entity != null) {
				if (StringUtils.isNotEmpty(entity.getConfValue())) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// 指定整车校验的开始时间
					Date vehicleValidationTime = null;
					try {
						vehicleValidationTime = sdf
								.parse(entity.getConfValue());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// 当指定的整车报价校验时间不为空，且开单时间在指定的校验时间之后，则进行校验
					if (vehicleValidationTime != null
							&& bean.getBillTime().after(vehicleValidationTime)) {
						// 判断当前运输性质是否正确
						ProductEntityVo productVo = bean.getProductCode();
						if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
								.equals(productVo.getCode())) {
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.productCodeIsError"));
						}

						// 最终价格
						BigDecimal finalPrice = BigDecimal.ZERO;
						// 通过反推计算出来的开单总运费的值
						BigDecimal totalPrice = BigDecimal.ZERO;

						// 开单总运费-整车运费冲减
						/**
						 * 判断整车运费冲减是否为null,如果为null,说明开单的时候，没有选择对应的费用
						 */
						BigDecimal tempPriceOne = null;
						if (null != bean.getWholeVehicleFeeChange()) {
							tempPriceOne = bean.getTotalFee().subtract(
									bean.getWholeVehicleFeeChange());
						} else {
							tempPriceOne = bean.getTotalFee().subtract(
									BigDecimal.ZERO);
						}
						// 开单总运费-整车运费冲减-约车报价-装卸费
						BigDecimal tempPriceTwo = tempPriceOne
								.subtract(
										bean.getWholeVehicleAppfee() == null ? BigDecimal.ZERO
												: bean.getWholeVehicleAppfee())
								.subtract(
										bean.getServiceFee() == null ? BigDecimal.ZERO
												: bean.getServiceFee());
						// (开单总运费-整车运费冲减)*其他成本参数
						BigDecimal tempPriceThree = tempPriceOne
								.multiply(bean
										.getWholeVehicleOtherCostParameter() == null ? BigDecimal.ZERO
										: bean.getWholeVehicleOtherCostParameter());
						// 包装费*包装费参数+货物重量*重量参数
						BigDecimal tempPriceFour = ((bean.getPackageFee() == null ? BigDecimal.ZERO
								: bean.getPackageFee())
								.multiply(bean
										.getWholeVehiclePackageFeeParameter() == null ? BigDecimal.ZERO
										: bean.getWholeVehiclePackageFeeParameter()))
								.add(bean
										.getGoodsWeightTotal()
										.multiply(
												bean.getWholeVehicleWeightParameter() == null ? BigDecimal.ZERO
														: bean.getWholeVehicleWeightParameter()));
						// (约车报价*车价参数)-人力成本参数
						BigDecimal tempPriceFive = ((bean
								.getWholeVehicleAppfee() == null ? BigDecimal.ZERO
								: bean.getWholeVehicleAppfee())
								.multiply(bean
										.getWholeVehicleCarCostParameter() == null ? BigDecimal.ZERO
										: bean.getWholeVehicleCarCostParameter()))
								.subtract(bean
										.getWholeVehicleHumanCostParameter() == null ? BigDecimal.ZERO
										: bean.getWholeVehicleHumanCostParameter());

						finalPrice = tempPriceTwo.subtract(tempPriceThree)
								.subtract(tempPriceFour).add(tempPriceFive);
						// 对计算出的结果四舍五入 保留两位小数
						finalPrice = finalPrice.setScale(2,
								BigDecimal.ROUND_HALF_UP);

						if (finalPrice.compareTo(BigDecimal.ZERO) <= 0) {
							/**
							 * 通过校验公式（开单总运费—整车运费冲减）—约车报价—装卸费-（开单总运费—整车运费冲减）*
							 * 其他成本参数—包装费*包装费参数—货物重量*重量参数+约车报价*车价参数—人力成本参数=0
							 * 计算获得开单总运费
							 */
							totalPrice = (tempPriceFour
									.subtract(tempPriceFive)
									.add(bean.getWholeVehicleAppfee() == null ? BigDecimal.ZERO
											: bean.getWholeVehicleAppfee())
									.add(bean.getServiceFee() == null ? BigDecimal.ZERO
											: bean.getServiceFee()))
									.divide(BigDecimal.ONE
											.subtract(bean
													.getWholeVehicleOtherCostParameter() == null ? BigDecimal.ZERO
													: bean.getWholeVehicleOtherCostParameter()),
											2, BigDecimal.ROUND_HALF_DOWN);
							totalPrice = totalPrice
									.add(bean.getWholeVehicleFeeChange() == null ? BigDecimal.ZERO
											: bean.getWholeVehicleFeeChange());
							// 对计算出的结果四舍五入 保留两位小数
							totalPrice = totalPrice.setScale(2,
									BigDecimal.ROUND_HALF_UP);
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.calculateVehicleDialog.vehicleTotalFeeBig")
											+ totalPrice);
						}
					}
				}
			}

		}
	}

	/**
	 * DMANA-7911 更改单时当付款方式为【月结】或【临欠】是校验结算天数是否超期
	 * 
	 * @author 218438-foss-zhulei
	 */
	public void validateSettlementDay(WaybillInfoVo bean) {
		List<WaybillRfcChangeDetailDto> detailDtos = ui.getMessagePanel()
				.getChangedInfoPanel().getTableData();
		if (CollectionUtils.isNotEmpty(detailDtos)) {
			for (int i = 0; i < detailDtos.size(); i++) {
				// 判断客户编码是否发生变更
				if (WaybillConstants.DELIVERY_CUSTOMER_CODE.equals(detailDtos
						.get(i).getPropertyName())) {
					// 付款方式为【临欠】校验
					if (WaybillConstants.TEMPORARY_DEBT.equals(bean
							.getPaidMethod().getValueCode())) {
						try {
							waybillRfcService.canDebit(
									bean.getDeliveryCustomerCode(),
									bean.getReceiveOrgCode());
						} catch (Exception e) {
							LOG.info(e.getMessage());
							throw new WaybillValidateException(e.getMessage());
						}
					}
				}
			}
		}
	}
		//校验伙伴开单公布价
		public void validateTransportFee(WaybillPanelVo bean) {
		ConfigurationParamsEntity configuration= BaseDataServiceFactory.getBaseDataService()
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.CREATING_PARTNER_ORDER_DISCOUNT,
						FossConstants.ROOT_ORG_CODE);
		if(null!=configuration && StringUtils.isNotBlank(configuration.getConfValue())){
			double temp = Double.parseDouble(configuration.getConfValue());
			int intValue2 = bean.getTransportFee().intValue();
			if(intValue2!=0 && bean.getTempTransportFee()!=null){
				int transportFee = bean.getTempTransportFee().intValue();
				int intValue = (int) (transportFee*temp+NumberConstants.NUMBER_0_5d);
				if(intValue2>=intValue){
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.getButtonPanel().getBtnSubmit().setEnabled(true);
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.transportFeeMsgBox")+intValue);
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				}
			}
		}else{
			int intValue2 = bean.getTransportFee().intValue();
			if(intValue2!=0 && bean.getTempTransportFee()!=null){
				int transportFee = bean.getTempTransportFee().intValue();
				int intValue = (int) (transportFee*NumberConstants.NUMBER_0_35d+NumberConstants.NUMBER_0_5d);
				if(intValue2>=intValue){
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.getButtonPanel().getBtnSubmit().setEnabled(true);
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.transportFeeMsgBox")+intValue);
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				}
			}
		}
	}

}