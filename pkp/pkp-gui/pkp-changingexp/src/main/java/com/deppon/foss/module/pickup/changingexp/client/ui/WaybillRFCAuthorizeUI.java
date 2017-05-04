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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/WaybillRFCAuthorizeUI.java
 * 
 * FILE NAME        	: WaybillRFCAuthorizeUI.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeAbortAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeAddAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeDeleteAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeEditAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeQueryAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcAuthorizeResetAction;
import com.deppon.foss.module.pickup.changingexp.client.listener.WaybillRfcAuthorizeComponentListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.MultiUserChooser;
import com.deppon.foss.module.pickup.changingexp.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 更改单审核授权页面
 * 界面描述
	 * 　　该用例新增审核代理或通过查询条件查询出已设置的审核代理设置后进行修改、
	*中止和删除操作。它包含2个界面授权运单变更审核权限（图1）和设置运单变更审核权限（图2）
	 * 　　
	 * 一、
	*界面标题：授权运单变更审核权限（图1）
	 * 查询条件：生效日期、
	*代理人、
	*生效状态。
	 * 生效日期：代理设置的生效时间
	 * 代理人：代理设置的代理人
	 * 生效状态：审核代理当前的生效状态，
	*包括全部、
	*已中止、
	*已生效、
	*未生效 
	 * 查询：根据录入的条件，
	*将查询出的代理结果显示在“查询结果”列表中 
	 * 重置：将查询条件重置为初始化状态
	 * 新增：增加一条代理人设置
	 * 查询结果：操作、
	*委托人、
	*代理人、
	*生效时间、
	*终止时间、
	*代理原因。
	 * 
	 * 二、
	*界面标题：设置运单变更审核权限（图1）
	 * 界面元素：包含委托人、
	*代理人、
	*生效时间、
	*终止时间、
	*代理原因。
	 * 委托人：发起代理委派的经理
	 * 代理人：委派拥有审核权限的员工
	 * 生效时间：审核权限生效的时间
	 * 终止时间：审核权限被系统收回的时间
	 * 代理原因：发起授权的原因
	 * 提交：保存数据，
	*返回图1，
	*刷新界面
	 * 返回：返回到图1
	 * 
	 * 操作步骤
	 * 序号	基本步骤	相关数据	补充步骤
	 * 1	打开“授权运单变更审核权限”界面	无	系统初始化界面数据
	 * （参见SR1）
	 * 2	录入查询条件后点击“查询”按钮开始查询审核代理	审核代理	系统根据查询条件查询出满足条件的审核代理并显示在下方列表中
	 * （参见SR2）
	 * 3	点击“新增”按钮增加一条代理人设置	审核代理	系统弹出“设置运单变更审核权限”（图2）界面，
	*初始化数据
	 * （参见SR3）
	 * 4	点击“提交”按钮，
	*确认已设置审核代理信息	审核代理	系统记录委托人、
	*代理人、
	*生效时间、
	*终止时间、
	*代理原因
	 * （参见SR5）
	 * 
	 * 扩展事件
	 * 序号	扩展事件	相关数据	备注
	 * 2a	当要重新查询时可点击“重置”按钮	无	系统将查询条件重置为初始值
	 * （参见SR1）
	 * 2b	若没有查询到数据时，
	*则不显示“查询结果”栏	无	系统提示“没有查询到相关数据，
	*请录入查询条件！”
	 * 3a	点击某一行前面的“修改”按钮修改授权记录	审核权限授权	系统弹出“设置运单变更审核权限”（图2）界面，
	*并根据选中的记录查询出对应的审核权限授权信息显示在界面中
	 * （参见SR4）
	 * 3b	点击某一行前面的“删除”按钮删除授权记录	审核权限授权	系统弹出“确认/取消对话框”，
	*提示“是否确认删除该行运单变更审核授权？” 
	 * （参见SR4）
	 * 3c	点击某一行前面的“中止”按钮中止代理审核设置	审核权限授权	系统弹出“确认/取消对话框”，
	*提示“是否确认中止该行运单变更审核授权？” 
	 * （参见SR4）
	 * 4a	若要关闭“设置运单变更审核权限”界面时，
	*可点击“返回”按钮	无	系统关闭界面回到“授权运单变更审核权限”界面
	 * 业务规则
	 * 序号	描述
	 * SR1	部门经理才有权限访问该页面
	 * 初始值设置：代理人为空，
	*生效类型默认为“全部”，
	*生效开始时间和结束时间分别为当月第一天与当月最后一天，
	*精确到日
	 * SR2	生效时间的查询时间段不能超过一个月
	 * 只能查询到由登陆人发起的审核代理信息，
	*不能查询到其他人员的设置信息。
	 * SR3	委托人为登陆人，
	*不能修改
	 * 代理人为部门经理所在部门的所有员工
	 * 生效时间默认为当前系统时间，
	*终止时间为N天后系统时间（N可通过参数配置）
	 * SR4	每一条配置可以有修改、
	*删除和中止操作，
	*按钮可用的规则如下：
	 * 当前时间在生效时间之前，
	*允许删除、
	*修改，
	*不能中止，
	*不保留历史记录
	 * 当前时间在生效时间与终止时间之间，
	*允许修改与中止，
	*不能删除，
	*修改时保留原先授权记录
	 * 当前时间在终止时间之后，
	*不允许做任何操作
	 * SR5	若生效时间早于当前系统时间，
	*系统提示“生效时间不能早于当前时间。”
	 * 若终止时间早于生效时间，
	*系统提示“终止时间不能早于生效时间。”
	 * 代理人允许多选
	 * * 1. 限保物品（例如：“陶瓷”、
	*“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；
	*
 * （限保物品从限运物品基础资料中获取）；
	*
 * 非限保物品的保价费率不可修改；
	*
 * 2. 实际保险费小于最低保费的按最低保费收取；
	*
 * 3. 保价费 = 保价申明价值*保价费率，
	*不可修改；
	*
 * 4. 保价申明价值默认为3000，
	*可以修改；
	*
 * 保价声明价值不封顶；
	*
 * 5. 精准（长途）、
	*普货（长途）、
	*偏线，
	*最低一票8元；
	*
 * 普货（短途）、
	*精准（短途）最低一票6元；
	*
 * 空运最低一票10元；
	*
 * 所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；
	*
 * 长短途数据读取计价基础资料；
	*
 * 6. 实际保价费小于最低保费的按最低保费收取；
	*
 * 7. 保价费率首先是配置的标准费率。
 * 当有区域保价费率时，
	*以区域保价费率为准。
 * 当客户为合同客户时，
	*则以合同签订为准。
 * 所有的保价费率以读取的为准，
	*不可修改。
 * 限保物品的保价费率同样不可修改
 * 8. 行政组织业务属性-营业部信息中增加增值服务（返回签单、
	*货到付款、
	*代收货款）选项，
	*
 * 开单是否可以进行返回签单、
	*货到付款、
	*
 * 代收货款需要根据到达部门属性是否可以（返回签单、
	*货到付款、
	*代收货款）来决定			
 * 1. 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，
	*
 * 代收货款设置为0且不可编辑；
	*
 * 2. 如果是CRM客户、
	*对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
	*
 * 代收货款可编辑，
	*且可以选择所有的退款类型，
	*若CRM中若无账户信息，
	*
 * 则退款类型只能选择审核退
 * 3. 开单时系统默认代收货款为空；
	*
 * 4. 代收货款栏默认为空，
	*如果没有代收货款，
	*则要求输入0；
	*
 * 否则，
	*进行提示：“请确认该单没有代收货款，
	*如无，
	*请输入数字0”；
	*
 * 当代收货款大于0时，
	*输入后，
	*对于选择的退款类型，
	*
 * 有如下限制：
 * 3.1 三日退：在收到客户代收货款后第三天给客户打款。
 * 3.1.1 默认退款类型为三日退；
	*
 * 3.1.2 代收10000元以下费率0.5%，
	* 
 * 10000元以上费率0.4%；
	*最低10元/票，
	*最高100元/票；
	*
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2 审核退：收到客户代收货款，
	*出发部门审核后，
	*给客户打款。
 * 3.2.1 代收10000元以下费率0.5%，
	*10000元以上费率0.4%；
	*
 * 最低10元/票，
	*最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2.2 选择审核退时，
	*客户收款信息体现在开单界面，
	*若无账号时可以提交运单后再走账号修改流程进行补充。
 * 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，
	*
 * 最低20元/票，
	*最高200元/票；
	*有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，
	*提交时，
	*银行信息不能为空；
	*
 * 3.3.3 只支持4个银行：农行、
	*工行、
	*建行、
	*招行；
	*否则，
	*给予提示信息；
	*
 * 5. 限制代收货款金额不能小于10元，
	*可以等于10元；
	*
 * 但可以为0；
	*该数字“10”可由基础资料配置；
	*
 * 6. 网上订单导入开单时，
	*代收货款金额读取网上订单的代收货款金额，
	*
 * 有数据时不可对代收货款进行修改，
	*只可起草出发更改进行修改；
	*
 * 若网上代收货款为0 ，
	*系统可支持修改代收货款金额；
	*
 * 7. 默认的代收费率由基础资料配置；
	*			
 * 1. 保价费和代收货款费用无法编辑更改，
	*只能更改保险声明价值和代收货款金额；
	*			
 * 1. 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，
	*
 * 且只能选择，
	*不能修改；
	*当退款人姓名和帐号唯一时，
	*直接显示；
	*
 * （数据读取CRM客户信息资料（退款帐户信息））
 * 2. CRM客户信息资料的要先在CRM中录入客户退款帐户信息，
	*
 * 且第一次在我司办理代收货款业务时，
	*退款类型只能为审核退；
	*
 * 3. 同一客户多个银行信息的显示问题：当有两个或以上账号时，
	*
 * 弹出账号信息（包括开户银行、
	*收款人、
	*账号、
	*备注），
	*选中其中的一条银行信息记录后，
	*账号和收款人信息显示在开单界面对应的位置；
	*			
 * 1. 包装费默认为0，
	*可手工修改；
	*
 * 2. 当录入有打木加信息时，
	*默认显示包装费=max（150*打木架货物体积，
	*30）+max（300*打木箱货物体积，
	*40），
	*
 * 且可修改，
	*修改的金额只能大于等于默认显示金额；
	*
 * 其中150、
	*30、
	*300、
	*40为打木架单价（元/方）、
	*打木架最低一票、
	*打木箱单价（元/方）、
	*打木箱最低一票，
	*
 * 可由基础资料配置；
	*			
 * 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
	*
 * （其中，
	*原费率Q0为：公布价）。 当装卸费大于零时，
	*开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	*
 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；
	*（对于专线的散客而言）
 * 2. 如果该客户为月结客户、
	*整车、
	*中转下线或偏线，
	*则原费率（Q0）为装卸费前的费率，
	*
 * 为系统默认的公布价。开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	*
 * 开单显示运费（C）=Q*Z=C0+M ；
	*
 * 3. 当修改除装卸费影响外有影响费率的地方时（如目的站更改、
	*重量和体积变化影响到费率），
	*
 * 需先清空装卸费为零，
	*然后再修改数据。如：月结客户与非月结客户、
	*整车与非整车、
	*目的站、
	*重量、
	*体积；
	*
 * 4. 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，
	*则最终显示费率Q2=Q1-M1/Z+M2/Z，
	*最终显示运费（C2）= Q2*Z。
 * 否则，
	*清空装卸费为零，
	*最终显示费率Q2=Q1-M1/Z，
	*最终显示运费（C2）= Q2*Z。
 * （其中Q1是装卸费M1时的费率,Z为重量/体积）；
	*
 * 5. 系统如果是运费最低一票，
	*要求装卸费=0，
	*即装卸费不允许修改。
 * （只限制配载类型为专线的，
	*包括月结）；
	*
 * 6. 对于显示费率不等于显示费率乘以重量的问题，
	*要求如下：
 * 6.1. 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，
	*则显示费率（Q）=费率（A）。
 * 且显示运费等于该显示费率*重量；
	*
 * 6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，
	*
 * 令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，
	*不四舍五入）。
 * 则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
	*
 * 7. 只要含与不含装卸费两者有交叉的，
	*均以不含为准；
	*
 * 8. 装卸费特殊部门表：（建议：做为可配置的基础数据表）
 * 9. 2012年12月1日开业的部门不能开装卸费
 * 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 * 11. 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 * 12. 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。13. 14. 15. 			
 * 1. 送货费默认不可改小，
	*但可改大。除了月结客户外，
	*只能对系统计算出的送货费取值进行上调，
	*不能下调。
 * 当送货费取值大于最高送货费（基础资料配置）时，
	*送货费自动跳改为最高送货费值，
	*但用户可以上调送货费；
	*			
 * 2. 通过送货费基础资料来实现：			
 * 2.1. 若提货方式为送货进仓，
	*开单送货费直接读取送货进仓送货费基础资料，
	*取值终止；
	*			
 * 2.2. 若提货方式为送货上门，
	*则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
	*
 * （即：先到特殊区域送货费基础资料中取值，
	*若取到值，
	*取值终止；
	*否则，
	*再到全国标准送货费基础资料取值，
	*取值终止。）			
 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率；
	* 			
 * 2.3.1 先判断开单体积在哪个体积区间，
	*筛选出符合条件的所有记录，
	*
 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，
	*来确定是哪一条记录。
 * 然后再根据费率计算，
	*计算出来的值与该条的最低送货费进行比较，
	*若小于最低送货费时，
	*
 * 就取最低送货费，
	*取值终止；
	*若大于最低送货费且小于最高送货费时，
	*就取计算出来的值，
	*取值终止；
	*若大于最高送货费时，
	*就取最高送货费，
	*取值终止。			
 * 2.3.2 标淮派送范围收取送货费标准：			
 * 货物重量	标准		
 * 0-300KG	55元/票		
 * 301-500KG	0.2元/KG		
 * 501KG或2.5立方米以上	100元/票，
	*不封顶		
 * 2.3.3 当送货费取值小于最高送货费时，
	*开单送货费不可以更改；
	*当送货费取值高于最高送货费时，
	*
 * 开单送货费可手动更改，
	*如果手动更改的值小于最高送货费时，
	*系统弹出提示框：“该票送货费不得低于【最高送货费】”			
 * 2.3.4 仅使用于汽运专线，
	*对“空运”、
	*“偏线”以及“中转下线” 的不受以上收费的限制。			
 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，
	*但可以修改。（送货费不受限制。可向上修改也可以向下修改，
	*最小为0）			
 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，
	*只能向上修改			
 * 2.3.7 最高送货费做基础资料配置；
	*			
 * 3. 非标准派送范围加收操作费标准：			
 * 3.1 超远加收送货费标准：			
 * 距离（公里）	30-50	50-100	100-150
 * 加收金额（元）	50	100	150
 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；
	*			
 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，
	*
 * 无论是否做派送，
	*该区域均不能收取超远加收送货费；
	*			
 * 3.1.3 非标准派送的费用添加无上限			
 * 3.2 特殊区域（进仓）：			
 * 3.2.1 特殊区域类型：大型超市和商场、
	*大型工厂（需提供进仓编号）；
	*			
 * 3.2.2 收费标准：进仓费实报实销，
	*并加收150元操作费；
	*			
 * 4. 区域送货费限制：			
 * 4.1 当开单提货网点的所在城市或区域为“XX”、
	*提货方式为：送货”XX”(XX待定，
	*
 * 但前提是：提货方式必须为送货)时，
	*送货费固定为XX元，
	*且不可修改；
	*财务成本提取为XX元。（该类型城市或区域、
	*送货费固定标准、
	*成本提取标准可配置）			
 * 4.2 当开单提货网点为XX营业部时，
	*开单送货费为XX元，
	*内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、
	*深圳华强钟表市场营业部）。
 * （该类营业部类型、
	*送货费固定标准、
	*成本提取标准可配置）			
 * 4.3 内部带货、
	*空运、
	*偏线及中转下线不受上述需求的限制。			
 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，
	*判断单件重量是否超过XXKG，
	*或体积是否超过X立方，
	*
 * 是的话提示：“单件超过XKG或单票超过X立方，
	*请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，
	*
 * 其他费用中的“送货上楼费”屏蔽或显示但不可选择；
	*			
 * 5.3 若“XX区域”或其它限制类型区域再开派送部，
	*适用以上需求；
	*			
 * 5.4 空运、
	*偏线及中转下线不受上述需求的限制；
	*			
 * 5.5 内部带货受上述需求的限制；
	*			
 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；
	*			
 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
	*
 * 费用根据基础资料中的内容进行读取，
	*根据开单内容自动添加；
	*（可基础资料配置）
 * 2. 运输类型为“精准（长途）、
	*普货（长途）、
	*偏线、
	*空运”时，
	*燃油附加费默认为4元；
	*
 * 运输类型为“普货（短途）、
	*精准（短途）”时，
	*燃油附加费默认为2元；
	*均不可修改；
	*（可基础资料配置）
 * 3. 综合服务费：（费用金额由基础资料配置）
 * 3.1 综合服务费默认为2元不可修改、
	*剔除；
	*
 * 3.2 月结客户可以删除2元的综合服务费；
	*
 * 3.3 淘宝、
	*阿里巴巴订单导入开单时，
	*系统自动不收取2元的综合服务费；
	*
 * 4. 燃油附加费：（费用金额由基础资料配置）运输类型为“精准（长途）、
	*普货（长途）、
	*偏线、
	*空运”时，
	*
 * 燃油附加费默认为4元；
	*运输类型为“普货（短途）、
	*精准（短途）”时，
	*燃油附加费默认为2元；
	*均不可修改；
	*
 * 5. 其他费用中“是否可修改”打勾时，
	*对应费用类型的金额可以金额上限和金额下限之间修改；
	*
 * 6. 其他费用合计等于其他费用列表中各项费用数据之和；
	*			
 * 1. 原件签收单返回类：系统默认收取客户15元/票，
	*可更改收取客户20元/票。偏线不允许做签收单；
	*
 * 2. 空运、
	*偏线和中转下线的“返单类型”不可选择；
	*
 * 3. 若有选择，
	*则返单费用信息自动显示在其他费用信息列表中，
	*可以其它费用列表中修改签收费用；
	*
 * 4. 如果选择有返单类型，
	*系统会自动生成一条签收单记录，
	*
 * 记录信息包含：运单号、
	*运单ID、
	*库存状态、
	*当前操作部门（运单开单时，
	*是填开部门）、
	*
 * 是否签收、
	*是否作废、
	*出发部门(运单开单出发部门)、
	*签收单类型、
	*签收状态；
	*
 * 5. 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
	*
 * 非月结客户只能选择对应的返单类别的默认金额，
	*不能修改；
	*返单费用项目不能删除			
 * 1. 运单新增时，
	*运输性质选择空运、
	*提货网点非我司自有网点时，
	*不能选择预付运费保密；
	*
 * 2. 已开启预付运费保密运单提交后，
	*始发更改中预付运费保密可以取消。
 * 未开启预付运费保密运单提交后，
	*若货物未有非本部门入库操作，
	*
 * 则始发更改中预付运费保密可选择；
	*若货物有非本部门入库操作，
	*
 * 则始发更改中预付运费保密否可选择，
	*若要更改，
	*则必须返货后，
	*作废重新开单；
	*
 * 3. 运单保存未提交时，
	*可以在调出运单的时候，
	*预付运费保密自由选择；
	*
 * 4. 已开启预付运费保密的运单，
	*始发更改中预付更改到付或到付更改预付，
	*
 * 涉及预付运费有变动时，
	*不影响预付费保密功能；
	*
 * 5. 开启预付运费保密，
	*预付运费不能为0，
	*否则不能保存、
	*提交；
	*
 * 6. 开单付款方式为临时欠款时，
	*预付运费保密选项不可选择，
	*其他付款方式都行；
	*
 * 7. 开启预付运费保密，
	*运单出库后，
	*始发更改中运输性质由汽运专线更改为偏线、
	*空运时，
	*
 * 必须取消预付运费保密后才能提交；
	*
 * 8. 预付运费保密开启时，
	*非出发部门用户综合查询预付运费不显示，
	*即“预付金额”不显示；
	*
 * 9. 预付运费保密开启后，
	*到达联打印时，
	*费率、
	*运费、
	*其他服务费用明细、
	*费用合计中的现付总计均不显示；
	*			
 * "1. ；
	*2. 1）开单总费用、
	*预付金额、
	*到付金额，
	*取整，
	*四舍五入；
	* 
 * 2）开单所有录入的金额数值，
	*最多可录入到小数点后两位小数，
	*无效的0省略不显示，
	*如100.00显示100,100.10显示100.1；
	* 
 * 3）开单总费用、
	*预付金额、
	*到付金额之外的，
	*即中间环节的金额保留两位小数，
	*依据四舍五入规则。 
 * 4）费率最多可保留两位小数，
	*小数点多于两位时，
	*全进1。比如，
	*系统折算生成的费率为1.201时，
	*显示为1.21。"			
 * 1. 接货费只能录入数字			
 * "1）限保物品不限制保价金额，
	*限制保价费率，
	*增值服务部门可以配置对应限保物品的保价费率；
	* 
 * 2）限保物品的保价费率通过基础资料进行配置；
	* 
 * 3）取消偏线、
	*空运最高保价5000元的限制；
	* 
 * 
 * 2、
	*装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 * 
 * 3、
	*""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，
	*同时需增加归集类别的配置资料。 
 * 
 * 4、
	*超远派送送货标准的基础资料是需要配置的；
	*超远派送费用无上限限制"			
 * 
 * 
 * 序号	描述		
	 * SR1	1.  由于整车运单信息与非整车运单信息不同，
	*导入整车运单至变更运单界面后，
	*整车运单信息中不存在的字段控件值为空，
	*不可编辑。2.  如导入的运单为整车运单，
	*则运输性质控件显示为“整车”。		
	 * SR2			
	 * 	下表为不能起草运单变更的类型及不能起草时系统提示内容		
	 * 			
	 * 	序号	不能起草运单变更的类型	提示内容
	 * 	1	运单状态为“已保存待补录”的运单	运单未补录，
	*不能起草运单变更！
	 * 	2	运单状态为“作废”、
	*“中止”的运单	运单已作废，
	*不能起草运单变更！运单已中止，
	*不能起草运单变更！
	 * 	3	运单对应的收货部门与系统当前组织不一致	该单的收货部门不是您部门，
	*不能起草运单变更！
	 * 	4	运单状态为“已签收”的运单	货物已签收，
	*不能起草运单变更！
	 * 	5	变更单状态为“待审核”或“待受理”的运单	该运单有运单变更单还未被审核，
	*不能起草变更运单！该运单有运单变更单还未被受理，
	*不能起草变更运单！
	 * 	6	运单已进行结清货款操作	该运单已进行结清货款操作，
	*如需更改请联系到达部门进行反结清货款操作！
	 * 	7	财务锁定的运单（财务将不允许发起变更的运单进行锁定）不能发起运单变更	该运单已被财务锁定，
	*不能起草变更运单！
	 * 	8	偏线运单以外发	偏线运单已中转外发，
	*不能起草运单变更！
	 * 			
	 * 			
	 * SR3	1． 运单无出库记录，
	*不允许起草转运单、
	*返货单；
	*a. 客户来源可以选择更改、
	*作废；
	*b. 内部来源可以选择更改、
	*中止2． 运单出库记录，
	*不允许起草作废单，
	*涉及到目的站、
	*提货网点的修改必须到转运信息、
	*返货信息面板修改；
	*如果变更提货方式，
	*由“派送”该为“自提”，
	*原始目的站不支持“自提”，
	*提示“原始目的站不支持自提，
	*请选择转运或返货类型”a. 客户来源可以选择更改、
	*转运、
	*返货；
	*c. 内部来源可以选择更改、
	*中止3． 若未选择变更要求（客户要求/内部要求），
	*运单信息显示区域的控件为不可编辑状态；
	*若选择了变更要求，
	*则不可变更的运单项对应的控件为不可编辑状态，
	*可以变更的运单项对应的控件为可编辑状态。运单项能否进行变更主要受变更要求、
	*运输类型、
	*货物当前库存部门三个维度的影响。其中内部要求发起的运单变更，
	*可变更及不可变更的运单项请详见下表：    		
	 * SR4	1. 选择客户要求变更时，
	*上传凭证名称默认显示为“身份证复印件”及“运单客户联”，
	*可另新增变更凭证；
	*选择内部要求变更时，
	*上传凭证名称默认显示为“运单原件”，
	*可另新增变更凭证；
	*2. 默认显示的变更凭证所在行不可删除，
	*新增的变更凭证所在行可删除；
	*3. 已选择上传的变更凭证可点击查看，
	*也可点击删除。		
	 * SR5	1.  “变更项”列显示为变更的运单项名称，
	*“变更前信息”列显示变更项在变更前对应的运单信息，
	*“变更后信息”列显示变更项在变更后对应的运单信息。如是转运或返货变更：“目的站”变更项对应的变更前信息为原目的站，
	*变更后信息为转运/返货目的站；
	*“提货网点”变更项对应的变更前信息为原提货网点，
	*变更后信息为转运/返货提货网点。2. 对于“上门接货”、
	*“预付费保密”、
	*“大车直送”、
	*“贵重物品”、
	*“特殊物品”为复选框的变更项，
	*变更前后对应的信息根据复选框勾选与否显示为是或否；
	*3. 若变更项变更前信息或变更后信息为空，
	*则“变更前信息”或“变更后信息”列显示为:—；
	*4. 以下运单项不列入变更项：计费类型、
	*计费重量、
	*费率、
	*保价费率、
	*代收费率、
	*装卸费、
	*其他费用（总金额）、
	*转运运输性质、
	*转运配载类型、
	*转运预配航班、
	*转运计费类型、
	*转运费率、
	*返货运输性质、
	*返货计费类型；
	*5. 变更项行显示顺序为：运单基础信息变更项→发货客户信息变更项→收货客户信息变更项→运输信息变更项→货物信息变更项→增值业务信息变更项→详细计价信息变更项→计费付款信息变更项；
	*6. “变更前信息”与“变更后信息”列显示内容不能相同。		
	 * SR6	1. 运单信息的修改录入操作参照运单生成相关系统用例的操作；
	*2. 修改运单信息时，
	*系统校验的业务规则参照运单生成相关系统用例的业务规则。		
	 * SR7	1. 关键增值服务信息的变更规则：		
	 * 			
	 * 	运单项	起草运单变更时对应的版本	
	 * 	费率	产品在运单信息生成时对应的公布价版本	
	 * 	保价费率	运单信息生成时的保价费率版本	
	 * 	代收费率	运单信息生成时的代收费率版本	
	 * 	公布价运费	运单信息生成时的公布价运费版本	
	 * 	保价费	运单信息生成时对应的收费标准	
	 * 	代收手续费	运单信息生成时对应的收费标准	
	 * 	送货费	运单信息生成时对应的收费标准	
	 * 	装卸费	运单信息生成时对应的收费标准	
	 * 	其他费用项	运单信息生成时的收费标准	
	 * 	优惠费用	运单开单信息生成时对应的优惠标准	
	 * 			
	 * 	综上：起草变更单（内部）时，
	*产品及价格优惠版本统一执行运单开单时对应的版本。		
	 * 	举例：		
	 * 	公布价运费：运单开单时精准卡航对应的公布价（上门发货）运费最低一票为40元，
	*在开单后系统维护调整为50元最低一票。则在起草变更单时，
	*最低一票仍执行运单开单时的标准，
	*即为40元一票。		
	 * 	2. 含有代收货款的运单，
	*如取消代收货款（代收货款金额更改为0），
	*代收手续费变为0。		
	 * SR8	成功提交运单变更后，
	*方可打印变更单		
	 * SR9	1. 弹出路径选择框，
	*默认指定路径在桌面，
	*可选择保存路径；
	*2. 可上传的凭证扫描件，
	*应该为图片，
	*格式允许选择常见的JPG,GIF,PNG,BPM；
	*3. 上传图片大小有所限制（默认250K，
	*允许系统后台配置），
	*如果超出所设范围，
	*系统给出提示“您上传的文件已超过最大允许大小250K，
	*请调整后重新上传。”。		
	 * SR10	对于发货客户的变更：1. 同一发货客户，
	*若客户资质发生变化；
	*a) 开单时为合同客户，
	*开单后（起草更改单之前）客户属性变为非合同客户： FOSS在开单时记录客户资质，
	*起草更改单时仍可选择免费送货；
	*b) 开单时为非合同客户，
	*开单后（起草更改单之前）客户属性升级为合同客户：起草更改单时仍可选择免费送货；
	*2. 由某一发货客户更改为另一发货客户a) 合同客户更改为非合同客户：原开单提货方式为免费送货，
	*与开单时保持一致，
	*即直接清空提货方式，
	*重新选择提货方式;b) 非合同客户更改为合同客户：提货方式中增加"免费送货"选项。		
	 * SR11	1. 货物未到达第一打木架外场（开单录入代打木架信息时系统自动生成的代打木架信息），
	*允许修改代打包装信息2. 货物已到达第一打木架外场，
	*则不可发起代打包装信息更改3. 若有代打包装，
	*则在录入代打包装信息后，
	*系统自动计划出代打包装费用，
	*显示至包装费中，
	*营业员或开单员可以任意修改包装费。4. 在更改单界面中，
	*加入代打包装货物件数的选择，
	*即流水号选择。在发起代打包装更改单时，
	*打开的代打包装信息中默认勾选开单录入的代装包装总件数的流水号，
	*即：开单录入代打木架4件，
	*代打木箱3件，
	*则在更改单代打包装信息中，
	*自勾选流水号1-7；
	*5. 允许点击“代打木架”按钮直接修改打包装信息		
	*
	*对于发货客户的变更：
	*同一发货客户，若客户资质发生变化；
	*开单时为合同客户，开单后（起草更改单之前）客户属性变为非合同客户： FOSS在开单时记录客户资质，起草更改单时仍可选择免费送货；
	*开单时为非合同客户，开单后（起草更改单之前）客户属性升级为合同客户：起草更改单时仍可选择免费送货；
	*由某一发货客户更改为另一发货客户
	*合同客户更改为非合同客户：原开单提货方式为免费送货，与开单时保持一致，即直接清空提货方式，重新选择提货方式;
		*非合同客户更改为合同客户：提货方式中增加"免费送货"选项。
	*货物未到达第一打木架外场（开单录入代打木架信息时系统自动生成的代打木架信息），允许修改代打包装信息
	*货物已到达第一打木架外场，则不可发起代打包装信息更改
	*	*若有代打包装，则在录入代打包装信息后，系统自动计划出代打包装费用，显示至包装费中，营业员或开单员可以任意修改包装费。
	*在更改单界面中，加入代打包装货物件数的选择，即流水号选择。在发起代打包装更改单时，打开的代打包装信息中默认勾选开单录入的代装包装总件数的流水号，即：开单录入代打木架4件，代打木箱3件，则在更改单代打包装信息中，自勾选流水号1-7；
	*允许点击“代打木架”按钮直接修改打包装信息
	*转运信息对应的相关业务规则：1. 转运目的站：转运提货网点对应的目的站，与收货人地址、提货方式进行关联。2. 转运提货网点：转运后的提货网点，与收货人地址、提货方式进行关联。3. 转运运输性质：1） 原运输类型为汽运：以原目的站作为转运始发站，与转运目的站匹配运输性质；2） 原运输类型为偏线或空运：ⅰ 若货物未出原最终配载部门库存，以原最终配载部门所在城市作为转运始发站，与转运目的站匹配运输性质；ⅱ 若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运。4. 提货方式：起草转运单时，提货方式与原运输信息取消关联，与转运提货网点进行关联。5. 转运配载类型：转运运输性质为空运时，包含合大票、单独开单；其他转运运输性质时，只含有汽运。6. 转运预配航班：转运运输性质为空运时为可选状态，包含早班、中班、晚班。7. 转运计费类型：分为重量计费、体积计费，系统根据货物重量、体积、转运费率确定转运计费类型。8. 转运费率：1) 原运输类型为汽运：原目的站作为转运始发站，系统根据转运始发站、转运目的站、转运运输性质读取公布价基础资料（上门发货公布价）来确定转运费率，若未读取到公布价，则人工录入转运费率。2) 原运输类型为偏线或空运：ⅰ 若货物未出原最终配载部门库存，系统根据原最终配载部门所在城市、转运目的站、转运运输性质读取公布价基础资料（上门发货公布价）来确定转运费率，若未读取到公布价，则人工录入转运费率；ⅱ 若货物已出原最终配载部门库存，则人工录入转运费率。    注：转运费率读取的公布价为运单开单时对应的产品价格版本9. 转运费：1）计费类型为“重量计费”时，转运费=转运费率*重量；2）计费类型为“体积计费”时，转运费=转运费率*体积。注：转运费无最低一票。返货信息对应的相关业务规则：1. 返货目的站：返货提货网点对应的目的站，与收货人地址进行关联。。2. 返货提货网点：货物返回的提货网点，与收货人地址进行关联，只能选择公司自有营业网点。3. 返货运输性质：1） 原运输类型为汽运：ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，与返货目的站匹配返货运输性质；ⅱ 货物已出第一中转外场库存：以原目的站作为返货始发站，与返货目的站匹配运输性质；2） 原运输类型为偏线或空运：ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，与返货目的站匹配返货运输性质；ⅱ 货物已出第一中转外场库存，未出原最终配载部门库存：以原最终配载部门所在城市作为返货始发站，与返货目的站匹配运输性质。ⅲ 货物已出原最终配载部门库存：返货运输性质只能为偏线或空运。4. 返货计费类型：分为重量计费、体积计费，系统根据货物重量、体积、返货费率确定返货计费类型。5. 返货费率：1） 原运输类型为汽运：ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；ⅱ 货物已出第一中转外场库存：原目的站作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率不变。2） 原运输类型为偏线或空运：ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；ⅱ 货物已出第一中转外场库存，未出原最终配载部门库存：原最终配载部门所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；ⅲ 货物已出原最终配载部门库存：人工录入返货费率，原费率不变。注：返货费率读取的公布价为运单开单时对应的产品价格版本。6. 返货费：1） 计费类型为“重量计费”时，返货费=返货费率*重量；2） 计费类型为“体积计费”时，返货费=返货费率*体积。注：返货费无最低一票。	
	 * 1. 运单信息的修改录入操作参照运单生成相关系统用例的操作；2. 修改运单信息时，系统校验的业务规则参照运单生成相关系统用例的业务规则。	
	 * 1. 起草转运单时，关键增值服务信息的变更规则：	
	 * 	
	 * 运单项	起草运单变更时对应的版本
	 * 费率	产品在运单信息生成时对应的公布价版本
	 * 保价费率	运单信息生成时的保价费率版本
	 * 代收费率	运单信息生成时的代收费率版本
	 * 公布价运费	运单信息生成时的公布价运费版本
	 * 保价费	运单信息生成时对应的收费标准
	 * 代收手续费	运单信息生成时对应的收费标准
	 * 送货费	运单信息生成时对应的收费标准
	 * 装卸费	运单信息生成时对应的收费标准
	 * 其他费用项	运单信息生成时的收费标准
	 * 优惠费用	运单开单信息生成时对应的优惠标准
	 * 	
	 * 2. 起草返货单时，关键增值服务信息的变更规则：	
	 *    	
	 * 运单项	起草运单变更时对应的版本
	 * 费率	若货物未出第一中转库存：原运输性质下的收货部门至返货始发站的费率（返货始发站为第一中转外场对应的城市），费率为运单开单时对应的版本
	 * 	若货物已出第一中转库存：产品在运单信息生成时对应的公布价版本
	 * 保价费率	运单信息生成时的保价费率版本
	 * 代收费率	运单信息生成时的代收费率版本
	 * 公布价运费	运单信息生成时的公布价运费版本
	 * 保价费	运单信息生成时对应的收费标准
	 * 代收手续费	运单信息生成时对应的收费标准
	 * 送货费	运单信息生成时对应的收费标准
	 * 装卸费	运单信息生成时对应的收费标准
	 * 其他费用项	运单信息生成时的收费标准
	 * 优惠费用	运单开单信息生成时对应的优惠标准
	 * 	
	 * 综上：起草转运单或返货单时，产品及价格优惠版本统一执行运单开单时对应的版本。	
	 * 3. 含有代收货款的运单，如取消代收货款（代收货款金额更改为0）：若货物未出收部门库存，代收手续费变为0；若货物已出收货部门库存，代收手续费不变。	
	 * 成功提交转运单或返货单申请后，方可打印变更单	
	 * 1. 弹出路径选择框，默认指定路径在桌面，可选择保存路径；2. 可上传的凭证扫描件，应该为图片，格式允许选择常见的JPG,GIF,PNG,BPM；上传图片大小有所限制（默认250K，允许系统后台配置），如果超出所设范围，系统给出提示“您上传的文件已超过最大允许大小250K，请调整后重新上传。”。	
	 * 
	 * 
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-15 下午4:54:08
 */
public class WaybillRFCAuthorizeUI extends JPanel {
	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRFCAuthorizeUI.class);
	
	private static final int THIRDTY = 30;

	/**
	 * 50
	 */
	private static final int FIFTY = 50;
	
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * default css
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = 8489170231021834789L;

	/**
	 * 开始时间
	 */
	private JXDatePicker datePickerStart;

	/**
	 * 结束时间
	 */
	private JXDatePicker datePickerEnd;

	/**
	 * 生效状态
	 */
	private JComboBox comboBoxType;

	/**
	 * 代理人
	 */
	private MultiUserChooser txtUser;

	/**
	 * 查询按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeQueryAction.class)
	private JButton btnSearch;

	/**
	 * 重置按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeResetAction.class)
	private JButton btnReset;

	/**
	 * 添加按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeAddAction.class)
	private JButton btnAdd;

	/**
	 * 编辑按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeEditAction.class)
	private JButton btnEdit;

	/**
	 * 删除按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeDeleteAction.class)
	private JButton btnDelete;

	/**
	 * 中止按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeAbortAction.class)
	private JButton btnAbort;

	/**
	 * 审核代理明细表格
	 */
	private JXTable table;

	/**
	 * 审核代理数据模型
	 */
	private RFCAuthorizeTableModel dataModel;

	/**
	 * 审核代理状态Model
	 */
	private DefaultComboBoxModel statusModel;
	
	private WaybillRfcAuthorizeComponentListener componentListener;

	/**
	 * 实例化审核代理对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:27:48
	 */
	public WaybillRFCAuthorizeUI() {
		init();
		createListener();
	}

	/**
	 * 
	 * 创建绑定与数据初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:28:03
	 */
	private void createListener() {
		
		/**
		 * 开始时间
		 */
		datePickerStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JXDatePicker begin = (JXDatePicker) e.getSource();
				Calendar cal = Calendar.getInstance();
				cal.setTime(begin.getDate());
				
				Date now = new Date();
				Calendar nowCal = Calendar.getInstance();
				nowCal.setTime(now);
				
				int selectedYear = cal.get(Calendar.YEAR);
				int nowYear = nowCal.get(Calendar.YEAR);
				if(selectedYear > nowYear){
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.date.validate.msg"));
					datePickerStart.setDate(new Date());
					return;
				}
			}
		});
		
		/**
		 * 结束时间
		 */
		datePickerEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JXDatePicker begin = (JXDatePicker) e.getSource();
				Calendar cal = Calendar.getInstance();
				cal.setTime(begin.getDate());
				
				Date now = new Date();
				Calendar nowCal = Calendar.getInstance();
				nowCal.setTime(now);
				
				int selectedYear = cal.get(Calendar.YEAR);
				int nowYear = nowCal.get(Calendar.YEAR);
				if(selectedYear > nowYear){
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.date.validate.msg"));
					datePickerEnd.setDate(new Date());
					return;
				}
			}
		});
		
		
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 画面初始化完毕加载数据
		componentListener = new WaybillRfcAuthorizeComponentListener(this);
		addHierarchyListener(componentListener);
	}

	
	/**
	 * @return the componentListener
	 */
	public WaybillRfcAuthorizeComponentListener getComponentListener() {
		return componentListener;
	}

	/**
	 * 
	 * 页面布局初始化
	 * 
	 * 界面描述
	 * 　　该用例新增审核代理或通过查询条件查询出已设置的审核代理设置后进行修改、
	*中止和删除操作。它包含2个界面授权运单变更审核权限（图1）和设置运单变更审核权限（图2）
	 * 　　
	 * 一、
	*界面标题：授权运单变更审核权限（图1）
	 * 查询条件：生效日期、
	*代理人、
	*生效状态。
	 * 生效日期：代理设置的生效时间
	 * 代理人：代理设置的代理人
	 * 生效状态：审核代理当前的生效状态，
	*包括全部、
	*已中止、
	*已生效、
	*未生效 
	 * 查询：根据录入的条件，
	*将查询出的代理结果显示在“查询结果”列表中 
	 * 重置：将查询条件重置为初始化状态
	 * 新增：增加一条代理人设置
	 * 查询结果：操作、
	*委托人、
	*代理人、
	*生效时间、
	*终止时间、
	*代理原因。
	 * 
	 * 二、
	*界面标题：设置运单变更审核权限（图1）
	 * 界面元素：包含委托人、
	*代理人、
	*生效时间、
	*终止时间、
	*代理原因。
	 * 委托人：发起代理委派的经理
	 * 代理人：委派拥有审核权限的员工
	 * 生效时间：审核权限生效的时间
	 * 终止时间：审核权限被系统收回的时间
	 * 代理原因：发起授权的原因
	 * 提交：保存数据，
	*返回图1，
	*刷新界面
	 * 返回：返回到图1
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:28:30
	 */
	private void init() {
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC, });
		setLayout(formLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new JDTitledBorder(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.queryCondition.label")));
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		//生效日期
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.date.label"));
		panel.add(lblNewLabel1, "2, 2");
		datePickerStart = new JXDatePicker();
		Date date = new Date();
		datePickerStart.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
		datePickerStart.getLinkPanel().setVisible(false);
		datePickerStart.setDate(date);
		datePickerStart.getEditor().setEditable(false);
		panel.add(datePickerStart, "4, 2");

		JLabel lblNewLabel2 = new JLabel("");
		panel.add(lblNewLabel2, "6, 2");
		
		datePickerEnd = new JXDatePicker();
		datePickerEnd.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
		datePickerEnd.getLinkPanel().setVisible(false);
		datePickerEnd.setDate(date);
		datePickerEnd.getEditor().setEditable(false);
		panel.add(datePickerEnd, "8, 2");

		//代理人
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.user.label")+"：");
		panel.add(lblNewLabel3, "10, 2, right, default");

		txtUser = new MultiUserChooser(false, this);
		panel.add(txtUser, "12, 2, fill, default");

		//生效状态：
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.status.label")+"：");
		panel.add(lblNewLabel4, "14, 2, right, default");

		comboBoxType = new JComboBox();
		panel.add(comboBoxType, "16, 2, fill, default");
		
		//给下拉框设值
		statusModel = new DefaultComboBoxModel();
		statusModel.addElement(new CheckStatusVo(WaybillRfcConstants.SEARCH_TYPE_ALL, i18n.get("foss.gui.changingexp.waybillRFCUI.common.all")));
		statusModel.addElement(new CheckStatusVo(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT,
				"已中止"));
		statusModel.addElement(new CheckStatusVo(
				WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE, i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.avaliable.label")));
		statusModel.addElement(new CheckStatusVo(
				WaybillRfcConstants.RFC_AGENT_STATUS_INVALID, i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.invalid.label")));

		comboBoxType.setModel(statusModel);

		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setHgap(THIRDTY);
		panel.add(panel2, "2, 4, 15, 1, fill, fill");

		Dimension dimension = new Dimension(FIFTY,THIRDTY);
		
		btnSearch = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.query"));
		btnSearch.setMinimumSize(dimension);
		btnSearch.setMaximumSize(dimension);
		btnSearch.setPreferredSize(dimension);
		panel2.add(btnSearch);

		btnReset = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.reset"));
		btnReset.setMinimumSize(dimension);
		btnReset.setMaximumSize(dimension);
		btnReset.setPreferredSize(dimension);
		panel2.add(btnReset);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new JDTitledBorder(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.queryResult.label")));
		add(panel1, "2, 4, fill, fill");
		panel1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		JScrollPane scrollPane = new JScrollPane();
		panel1.add(scrollPane, "2, 2, 9, 1, fill, fill");
		this.setBackground(CommonUtils.getExpressColor());
		table = new JXTable();
		dataModel = new RFCAuthorizeTableModel();
		table.setModel(dataModel);
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);
		TableFactory.createRowColumn(table);

		scrollPane.setViewportView(table);

		btnAdd = new JButton(i18n.get("pickup.changingexp.add"));
		panel1.add(btnAdd, "4, 4");

		btnEdit = new JButton(i18n.get("pickup.changingexp.modify"));
		panel1.add(btnEdit, "6, 4");

		btnDelete = new JButton(i18n.get("pickup.changingexp.delete"));
		panel1.add(btnDelete, "8, 4");

		btnAbort = new JButton(i18n.get("pickup.changingexp.terminate"));
		panel1.add(btnAbort, "10, 4");
		
		

		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();
				if (row >= 0) {
					RFCAuthorizeTableModel model = (RFCAuthorizeTableModel) table.getModel();
					List<WaybillRfcAgentEntity> data = model.getData();
					WaybillRfcAgentEntity waybillRfcAgentEntity = data.get(row);
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);
					btnAbort.setEnabled(true);
					
					if (waybillRfcAgentEntity != null) {

						/**
						 * 判斷 是否終止
						 */
						if(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT.equals(getStatus(waybillRfcAgentEntity)))
						{
							btnEdit.setEnabled(false);
							btnDelete.setEnabled(false);
							btnAbort.setEnabled(false);
						}
						
						/**
						 * 判断 已删除
						 */
						else if (WaybillRfcConstants.RFC_AGENT_STATUS_DELETED.equals(getStatus(waybillRfcAgentEntity))){
							btnEdit.setEnabled(false);
							btnDelete.setEnabled(false);
							btnAbort.setEnabled(false);
							
						}
						/**
						 * 判断 未生效
						 */
						else if(WaybillRfcConstants.RFC_AGENT_STATUS_INVALID.equals(getStatus(waybillRfcAgentEntity)))
						{
							btnAbort.setEnabled(false);
						}
						/**
						 * 判断 生效
						 */
						else if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(getStatus(waybillRfcAgentEntity)))
						{
							btnDelete.setEnabled(false);
						}
						
					}
				}
			}
		});
		
		
		

	}
	/**
	 * 
	 * 获取生效状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午8:04:34
	 */
	private String   getStatus(
			WaybillRfcAgentEntity waybillRfcAgentEntity) {
		String status = waybillRfcAgentEntity.getStatus();
		if(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT.equals(status)){
			return WaybillRfcConstants.RFC_AGENT_STATUS_ABORT;//已中止
		}else if(WaybillRfcConstants.RFC_AGENT_STATUS_DELETED.equals(status)){
			return WaybillRfcConstants.RFC_AGENT_STATUS_DELETED;//已删除
		}else if(WaybillRfcConstants.RFC_AGENT_STATUS_INVALID.equals(status)){
			return WaybillRfcConstants.RFC_AGENT_STATUS_INVALID;//未生效
		}else if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(status)){
			Date overdueTime = waybillRfcAgentEntity.getOverdueTime();
			
			if (overdueTime.before(new Date())) {
				return WaybillRfcConstants.RFC_AGENT_STATUS_ABORT;//已中止
			}
			
			Date validTime = waybillRfcAgentEntity.getValidTime();
			if(validTime == null){
				return WaybillRfcConstants.RFC_AGENT_STATUS_INVALID;//未生效
			}else if(validTime.after(new Date())){
				return WaybillRfcConstants.RFC_AGENT_STATUS_INVALID;//未生效
			}else{
				return WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE;//已生效
			}
		}
		return null;
	}
	/**
	 * 
	 * 变更审核代理数据模型
	 * 
	 * 设置运单变更审核权限
	 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	 * 委托人	运单变更审核授权的委托人	文本		20	是	自动默认为主操作用户
	 * 代理人	执行运单变更审核授权的代理人	文本		20	是	只能选择本部门的用户，
	*不能为委托人本人。
	 * 生效时间	代理权限生效时间	时间
	 * 格式为：2012-7-26 10:00:00		20	是	生效时间≥系统当前时间，
	*生效时间≤终止时间
	 * 终止时间	代理权限终止时间	时间
	 * 格式为：2012-7-26 10:00:00			是	
	 * 代理原因	审核权限授权给代理人的原因	文本		80	是	
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:28:45
	 */
	public class RFCAuthorizeTableModel extends DefaultTableModel {
		
		private static final int SIX = 6;
		
		/**
		 * 5
		 */
		private static final int FIVE = 5;

		/**
		 * 4
		 */
		private static final int FOUR = 4;

		/**
		 * 3
		 */
		private static final int THREE = 3;

		/**
		 * 2
		 */
		private static final int TWO = 2;

		/**
		 * 1
		 */
		private static final int ONE = 1;

		/**
		 * 0
		 */
		private static final int ZERO = 0;

		/**
		 * 序列化版本
		 */
		private static final long serialVersionUID = 5883365603131625962L;


		/**
		 * 代理数据
		 */
		private List<WaybillRfcAgentEntity> data;

		public List<WaybillRfcAgentEntity> getData() {
			return data;
		}

		public void setData(List<WaybillRfcAgentEntity> data) {
			this.data = data;
		}

		/**
		 * 设置运单变更审核权限
    	 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
    	 * 委托人	运单变更审核授权的委托人	文本		20	是	自动默认为主操作用户
    	 * 代理人	执行运单变更审核授权的代理人	文本		20	是	只能选择本部门的用户，
	*不能为委托人本人。
    	 * 生效时间	代理权限生效时间	时间
    	 * 格式为：2012-7-26 10:00:00		20	是	生效时间≥系统当前时间，
	*生效时间≤终止时间
    	 * 终止时间	代理权限终止时间	时间
    	 * 格式为：2012-7-26 10:00:00			是	
    	 * 代理原因	审核权限授权给代理人的原因	文本		80	是	
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-19 下午4:36:29
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.client");
			case ONE:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.agent");
			case TWO:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.effectiveStatus");
			case THREE:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.effectiveTime");
			case FOUR:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.terminateTime");
			case FIVE:
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.columnName.agentReason");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return SIX;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return data.get(row).getEntrustEmpName();
			case ONE:
				String agentPerson = getAgentPerson(data.get(row));
				return agentPerson;
			case TWO:
				String agentStatus = getAgentStatus(data.get(row));
				return agentStatus;
			case THREE:
				Date validTime = data.get(row).getValidTime();
				if(validTime == null){
					return null;
				}
				return DATEFORMAT.format(validTime);
			case FOUR:
				Date overdueTime = data.get(row).getOverdueTime();
				if(overdueTime == null){
					return null;
				}
				return DATEFORMAT.format(overdueTime);
			case FIVE:
				return data.get(row).getAgentReason();
			default:
				return "";
			}
		}

		/**
		 * 
		 * 获取生效状态
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-27 下午8:04:34
		 */
		private String getAgentStatus(
				WaybillRfcAgentEntity waybillRfcAgentEntity) {
			String status = waybillRfcAgentEntity.getStatus();
			if(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT.equals(status)){
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.aborted.label");
			}else if(WaybillRfcConstants.RFC_AGENT_STATUS_DELETED.equals(status)){
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.deleted.label");
			}else if(WaybillRfcConstants.RFC_AGENT_STATUS_INVALID.equals(status)){
				return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.invalid.label");
			}else if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(status)){
				Date validTime = waybillRfcAgentEntity.getValidTime();
				if(validTime == null){
					return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.invalid.label");
				}else if(validTime.after(new Date())){
					return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.invalid.label");
				}else{
					return i18n.get("foss.gui.changingexp.waybillRFCAuthorizeUI.avaliable.label");
				}
			}
			return null;
		}

		/**
		 * 
		 * 获取审核代理人
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-27 下午8:02:31
		 */
		private String getAgentPerson(
				WaybillRfcAgentEntity waybillRfcAgentEntity) {
			StringBuffer sb = new StringBuffer();
			List<WaybillRfcAgentPersonEntity> rfcAgentPersonEntities = waybillRfcAgentEntity.getAgentEmpList();
			if(rfcAgentPersonEntities != null){
				for(WaybillRfcAgentPersonEntity rfcAgentPersonEntity : rfcAgentPersonEntities){
					sb.append(rfcAgentPersonEntity.getAgentEmpName());
					sb.append(",");
				}
			}
			if(sb.length()>0){
				return sb.substring(0, sb.length() - 1);
			}else{
				return sb.toString();
			}
		}
	}

	/**
	 * 
	 * 获取当前选中的代理人信息
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:29:16
	 */
	public WaybillRfcAgentEntity getSelectedAuthorizeBean() {
		int row = getSelectedTableModelRow();
		if(row >= 0){
			return dataModel.getData().get(row);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * 获取表格选中行
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:29:34
	 */
	private int getSelectedTableModelRow() {
		int row = table.getSelectedRow();
		if(row >= 0){
			row = table.convertRowIndexToModel(row);
		}
		return row;
	}

	/**
	 * 
	 * 开始时间
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:29:47
	 */
	public JXDatePicker getDatePickerStart() {
		return datePickerStart;
	}

	/**
	 * 
	 * 结束时间
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:29:57
	 */
	public JXDatePicker getDatePickerEnd() {
		return datePickerEnd;
	}

	/**
	 * 
	 * 生效状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:05
	 */
	public JComboBox getComboBoxType() {
		return comboBoxType;
	}

	/**
	 * 
	 * 登录用户
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:31:05
	 */
	public MultiUserChooser getTxtUser() {
		return txtUser;
	}

	/**
	 * 
	 * 查询按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:53
	 */
	public JButton getBtnSearch() {
		return btnSearch;
	}

	/**
	 * 
	 * 重置按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JButton getBtnReset() {
		return btnReset;
	}

	/**
	 * 
	 * 添加按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JButton getBtnAdd() {
		return btnAdd;
	}

	/**
	 * 
	 * 修改按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JButton getBtnEdit() {
		return btnEdit;
	}

	/**
	 * 
	 * 删除按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}

	/**
	 * 
	 * 中止按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JButton getBtnAbort() {
		return btnAbort;
	}

	/**
	 * 
	 * 代理设置表格
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:30:43
	 */
	public JXTable getTable() {
		return table;
	}

	
	public RFCAuthorizeTableModel getDataModel() {
		return dataModel;
	}

	/**
	 * 
	 * 重新执行查询，
	*刷新数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午2:17:58
	 */
	public void refreshData() {
		btnSearch.doClick();
	}
	
}