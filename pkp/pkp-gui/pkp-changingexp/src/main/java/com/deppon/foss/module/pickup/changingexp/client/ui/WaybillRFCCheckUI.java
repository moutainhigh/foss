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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/WaybillRFCCheckUI.java
 * 
 * FILE NAME        	: WaybillRFCCheckUI.java
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

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcCheckQueryAction;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.ChangeCheckStatusDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.JImageViewCheckDialog;
import com.deppon.foss.module.pickup.changingexp.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.LinkedButtonColumn;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * <p>
 * 更改审核UI<br />
 * </p>
 * 
 * 界面描述 主界面标题：运单变更审核。 营业员发起运单变更（含作废运单、 中止运单）后， 用户点击“运单变更审核”菜单录入查询条件查询后进入图1界面，
 * 点击查询结果列表中某一行记录展开为图2界面。 界面主要由两部分组成：查询条件、 查询结果 1.查询条件：起始日期、 截止日期、 单号、 变更类型、
 * 审核状态、 申请部门。 1）申请日期：提交运单变更申请的日期， 格式为：2012-05-10。 2）单号：运单变更的单号。
 * 3）审核状态：运单变更审核的状态， 包含同意、 拒绝、 待审核， 默认显示为全部。 4）申请部门：运单变更申请的部门， 暂定为不可编辑状态。
 * 5）查询按钮：查询事件的按钮。 6）重置按钮：重置查询条件至初始值的按钮。 2.查询结果：单号、 审核状态、 变更凭证、 变更类型、 申请部门、 申请人、
 * 申请时间、 审核人、 审核时间。详见数据元素【查询结果】。 运单变更状态图
 * 
 * 
 * 操作步骤 序号 基本步骤 相关数据 补充步骤 1 点击“运单变更审核”菜单， 进入“运单变更审核”界面 系统初始化界面控件值， 参见业务规则SR1 2
 * 录入查询条件， 点击“查询”按钮 参见数据元素【查询条件】 查询条件的录入参见业务规则SR2； 系统将符合查询条件的数据显示在“查询结果”界面中，
 * 查询结果显示参见数据元素【查询结果】 3 点击查询结果列表中任一条记录 该行记录扩展显示， 参见业务规则SR3-SR4 4
 * 选择点击审核状态为“待审核”的一条记录 该行记录扩展显示， 参见业务规则SR3-SR4 5 点击变更凭证列的“查看“按钮
 * 系统自动在浏览器中打开新页签并显示凭证的电子图片 6 选填审核备注， 点击“同意”按钮 系统刷新该条记录， 审核状态变为“同意”；
 * 系统根据变更类型的不同做相应的处理， 参见业务规则SR7；
 * 
 * 扩展事件 序号 扩展事件 相关数据 备注 2a 点击“查询”后， 结果列表中无数据显示 提示“未查询到相关信息” 2b 点击重置按钮 查询条件重置至初始值
 * 6a 输入审核备注， 点击“拒绝”按钮 系统刷新该条记录， 审核状态变为“拒绝” 业务规则 序号 描述 SR1
 * 申请日期：起始及截止日期均默认为系统当前日期。 单号：默认为空。 审核状态：默认为“全部”。 申请部门：默认为系统当前组织， 不可编辑。 SR2
 * 查询条件中申请日期的起始日期≤截止日期， 起始日期与截止日期间隔不能超过10天。 查询条件支持组合查询或单一查询， 若单号不为空则以单号为最高查询条件，
 * 不受其他查询条件限制。 部门经理可以查询到所属部门的变更申请；员工如果拥有代理权限并在生效期内， 也拥有相应查询权限。 SR3
 * 查询结果列表支持扩展显示功能； 操作用户只可查询到本部门发起的运单变更申请。 SR4 查询结果扩展显示包含：【变更信息】、 审核备注、 同意按钮、
 * 拒绝按钮。 变更信息：变更类型为更改单、 转运单、 返货单时， 变更信息区域以列表形式显示， 调用运单变更起草界面的【变更信息】。变更类型为作废运单、
 * 中止运单时， 变更信息区域以文本显示， 分别显示为“作废运单”、 “中止运单”。 审核备注：审核状态为“待审核”时， 审核备注以文本框显示，
 * 可编辑。其他审核状态， 审核备注以文本显示， 显示为审核时的备注信息。 同意按钮：审核状态为“待审核”时显示， 其他审核状态下不显示。
 * 拒绝按钮：审核状态为“待审核”时显示， 其他审核状态下不显示。 SR5 若同意审核， 审核备注为选填项， 若未填则系统默认记录审核备注为“同意！”
 * 若拒绝审核， 审核备注为必填项， 需要录入拒绝审核的原因。 SR6 若变更类型为内部变更或外部变更， 且修改了提货网点；需判断货物是否已出开单部门库存，
 * 如果已出库， 审核不能通过， 提示“该运单已出开单部门库存， 请重新起草转运单或返货单修改提货网点！” 若运单号被修改， 需判断货物是否已出开单部门库存，
 * 如果已出库， 审核不能通过， 提示“该运单已出开单部门库存， 无法更改运单号！”
 * 
 * SR7 变更类型为作废运单的运单变更， 在审核同意后： 运单状态变为“已作废”； 红冲财务类相关单据； 系统自动出库该运单。
 * 变更类型为中止运单的运单变更， 在审核同意后： 运单状态变为“已中止”； 红冲财务类相关单据。 对于系统自动受理的运单变更，
 * 在审核同意后运单信息更新为更改后的运单信息； 对于系统自动受理的运单变更， 如是涉及财务类的变更申请， 在审核同意后， 系统更新财务相关信息，
 * 参见财务附件： 　　 变更中有引起①开单总金额变小②开单总金额变大③付款方式变更（无论总金额是否变化）的更改单，
 * 需要生成会计的待核销申请（参见系统用例SUC-776：查询_核销_反核销更改单）。 对于系统自动受理的运单变更， 如果选择通知发货人或者收货人，
 * 短信通知客户；（短信模板可编辑， 模板：**先生/女士， 您好， 这里是德邦物流运单内容变更通知， 应您的需求，
 * 单号********运单信息已发生如下变更：*******************。请你核实。如有疑问请联系：021-**********【德邦物流】）
 * 对于系统自动受理的运单变更， 如是涉及提货网点、 包装、 件数、 货物类型、 运输性质、 提货方式中的任一项变更，
 * 在审核同意后生成货物当前库存部门或下一环节部门（在途时）的待办事项； 在审核同意后， 需要人工受理的运单变更由系统自动生成受理部门的消息提醒，
 * 提醒受理部门所有成员， 及时处理此申请， 申请信息主要包括：单号、 更改类型、 申请时间、 最迟处理时间。 如单号发生变更， 原单号可以再次被使用
 * 注：更改单在审核同意后， 分为两种：不需要人工受理即系统自动受理、 需要人工受理。 以下情形不需要人工受理： 汽运： 未打印到达联；
 * 空运/偏线（含中转下线）： 未到达原最终配载部门， 即外发部门； 整车： 未打印到达联且未交接出库；
 * 
 * 以下情形需要人工受理： 汽运： 已打印到达联； 空运/偏线（含中转下线）： 已到达原最终配载部门， 即外发部门； 整车： 已经打印到达联或者交接出库；
 * 
 * 
 * SR8 运单变更审核权限授权相关的业务规则： 代理人只能选择本组织下的用户； 代理人不能为委托人本人； 权限授权给委托人后， 代理人仍具有审核权限；
 * 
 * 数据元素 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 申请日期 运单变更申请的 格式：2012-07-10 20 是 单号
 * 运单变更的运单号 文本 8 否 审核状态 运单变更的审核状态， 包含待审核、 同意、 拒绝， 默认显示为全部 下拉框 10 否 申请部门
 * 运单变更申请的部门 文本 20 是 系统默认当前组织， 不可编辑 查询结果 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 单号
 * 运单变更起草的起始日期 日期 20 审核状态 运单变更的审核状态 变更类型 运单变更的变更类型， 包含：转运单、 返货单、 更改单、 作废运单、 中止运单
 * 变更凭证 运单变更上传的凭证， 可点击查看 申请部门 运单变更申请人所属部门 申请人 运单变更的申请人 申请时间 运单变更的申请时间，
 * 格式为2012-10-10 00:00:00 审核人 执行运单变更审核的操作用户， 若审核状态为“待审核”则显示为空 审核时间 运单变更审核的时间，
 * 格式为2012-10-10 00:00:00 若审核状态为“待审核”则显示为空
 * 
 * @title WaybillRFCCheckUI.java
 * @package com.deppon.foss.module.pickup.changingexp.client.ui
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillRFCCheckUI extends JPanel {

	/**
	 * 
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(WaybillRFCCheckUI.class);

	/**
	 * 80
	 */
	private static final int EIGHTY = 80;

	/**
	 * 3
	 */
	private static final int THREE = 3;

	/**
	 * 30
	 */
	private static final int THIRDTY = 30;

	/**
	 * 50
	 */
	private static final int FIFTY = 50;

	/**
	 * 16
	 */
	private static final int SIXTEEN = 16;

	/**
	 * 12
	 */
	private static final int TWELVE = 12;

	/**
	 * 8
	 */
	private static final int EIGHT = 8;

	/**
	 * 2
	 */
	private static final int TWO = 2;

	/**
	 * 18
	 */
	private static final int EIGHTEEN = 18;

	/**
	 * 6
	 */
	private static final int SIX = 6;

	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 14
	 */
	private static final int FOURTEEN = 14;

	/**
	 * 10
	 */
	private static final int TEN = 10;
	

	private static final int SEVEN = 7;

	private static final int FIVE = 5;

	private static final int NINE = 9;
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6930229163219897964L;

	/**
	 * 运单号
	 */
	private JTextField waybillNoTxt;

	/**
	 * 审核部门
	 */
	private JTextField approveDepartmentText;

	/**
	 * 审核状态
	 */
	private JComboBox status;

	/**
	 * 审核状态Model
	 */
	private DefaultComboBoxModel statusModel;

	/**
	 * 审核表格
	 */
	private JXTable table;

	/**
	 * 重置按钮
	 */
	private JButton resetBtn;

	/**
	 * 查询按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = WaybillRfcCheckQueryAction.class)
	private JButton queryBtn;

	/**
	 * 开始时间
	 */
	private JXDatePicker fossdBeginDate;

	/**
	 * 结束时间
	 */
	private JXDatePicker fossdEndDate;

	/**
	 * 查看列
	 */
	private LinkedButtonColumn viewColumn;

	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();
	private JLabel lblNewLabel_1;
	private JTextField txtWaybillNo;

	/**
	 * 
	 * WaybillRFCCheckUI
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:30:02
	 */
	public WaybillRFCCheckUI() {
		init();
		addListener();
		bind();
	}

	/**
	 * 
	 * 页面绑定
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:30:09
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 添加按钮监听
	 * 
	 * 界面描述 主界面标题：运单变更审核。 营业员发起运单变更（含作废运单、 中止运单）后，
	 * 用户点击“运单变更审核”菜单录入查询条件查询后进入图1界面， 点击查询结果列表中某一行记录展开为图2界面。 界面主要由两部分组成：查询条件、
	 * 查询结果 1.查询条件：起始日期、 截止日期、 单号、 变更类型、 审核状态、 申请部门。 1）申请日期：提交运单变更申请的日期，
	 * 格式为：2012-05-10。 2）单号：运单变更的单号。 3）审核状态：运单变更审核的状态， 包含同意、 拒绝、 待审核， 默认显示为全部。
	 * 4）申请部门：运单变更申请的部门， 暂定为不可编辑状态。 5）查询按钮：查询事件的按钮。 6）重置按钮：重置查询条件至初始值的按钮。
	 * 2.查询结果：单号、 审核状态、 变更凭证、 变更类型、 申请部门、 申请人、 申请时间、 审核人、 审核时间。详见数据元素【查询结果】。
	 * 运单变更状态图
	 * 
	 * 
	 * 操作步骤 序号 基本步骤 相关数据 补充步骤 1 点击“运单变更审核”菜单， 进入“运单变更审核”界面 系统初始化界面控件值， 参见业务规则SR1
	 * 2 录入查询条件， 点击“查询”按钮 参见数据元素【查询条件】 查询条件的录入参见业务规则SR2；
	 * 系统将符合查询条件的数据显示在“查询结果”界面中， 查询结果显示参见数据元素【查询结果】 3 点击查询结果列表中任一条记录 该行记录扩展显示，
	 * 参见业务规则SR3-SR4 4 选择点击审核状态为“待审核”的一条记录 该行记录扩展显示， 参见业务规则SR3-SR4 5
	 * 点击变更凭证列的“查看“按钮 系统自动在浏览器中打开新页签并显示凭证的电子图片 6 选填审核备注， 点击“同意”按钮 系统刷新该条记录，
	 * 审核状态变为“同意”； 系统根据变更类型的不同做相应的处理， 参见业务规则SR7；
	 * 
	 * 扩展事件 序号 扩展事件 相关数据 备注 2a 点击“查询”后， 结果列表中无数据显示 提示“未查询到相关信息” 2b 点击重置按钮
	 * 查询条件重置至初始值 6a 输入审核备注， 点击“拒绝”按钮 系统刷新该条记录， 审核状态变为“拒绝” 业务规则 序号 描述 SR1
	 * 申请日期：起始及截止日期均默认为系统当前日期。 单号：默认为空。 审核状态：默认为“全部”。 申请部门：默认为系统当前组织， 不可编辑。 SR2
	 * 查询条件中申请日期的起始日期≤截止日期， 起始日期与截止日期间隔不能超过10天。 查询条件支持组合查询或单一查询，
	 * 若单号不为空则以单号为最高查询条件， 不受其他查询条件限制。 部门经理可以查询到所属部门的变更申请；员工如果拥有代理权限并在生效期内，
	 * 也拥有相应查询权限。 SR3 查询结果列表支持扩展显示功能； 操作用户只可查询到本部门发起的运单变更申请。 SR4
	 * 查询结果扩展显示包含：【变更信息】、 审核备注、 同意按钮、 拒绝按钮。 变更信息：变更类型为更改单、 转运单、 返货单时，
	 * 变更信息区域以列表形式显示， 调用运单变更起草界面的【变更信息】。变更类型为作废运单、 中止运单时， 变更信息区域以文本显示，
	 * 分别显示为“作废运单”、 “中止运单”。 审核备注：审核状态为“待审核”时， 审核备注以文本框显示， 可编辑。其他审核状态， 审核备注以文本显示，
	 * 显示为审核时的备注信息。 同意按钮：审核状态为“待审核”时显示， 其他审核状态下不显示。 拒绝按钮：审核状态为“待审核”时显示，
	 * 其他审核状态下不显示。 SR5 若同意审核， 审核备注为选填项， 若未填则系统默认记录审核备注为“同意！” 若拒绝审核， 审核备注为必填项，
	 * 需要录入拒绝审核的原因。 SR6 若变更类型为内部变更或外部变更， 且修改了提货网点；需判断货物是否已出开单部门库存， 如果已出库，
	 * 审核不能通过， 提示“该运单已出开单部门库存， 请重新起草转运单或返货单修改提货网点！” 若运单号被修改， 需判断货物是否已出开单部门库存，
	 * 如果已出库， 审核不能通过， 提示“该运单已出开单部门库存， 无法更改运单号！”
	 * 
	 * SR7 变更类型为作废运单的运单变更， 在审核同意后： 运单状态变为“已作废”； 红冲财务类相关单据； 系统自动出库该运单。
	 * 变更类型为中止运单的运单变更， 在审核同意后： 运单状态变为“已中止”； 红冲财务类相关单据。 对于系统自动受理的运单变更，
	 * 在审核同意后运单信息更新为更改后的运单信息； 对于系统自动受理的运单变更， 如是涉及财务类的变更申请， 在审核同意后， 系统更新财务相关信息，
	 * 参见财务附件： 　　 变更中有引起①开单总金额变小②开单总金额变大③付款方式变更（无论总金额是否变化）的更改单，
	 * 需要生成会计的待核销申请（参见系统用例SUC-776：查询_核销_反核销更改单）。 对于系统自动受理的运单变更， 如果选择通知发货人或者收货人，
	 * 短信通知客户；（短信模板可编辑， 模板：**先生/女士， 您好， 这里是德邦物流运单内容变更通知， 应您的需求，
	 * 单号********运单信息已发生如下变更
	 * ：*******************。请你核实。如有疑问请联系：021-**********【德邦物流】） 对于系统自动受理的运单变更，
	 * 如是涉及提货网点、 包装、 件数、 货物类型、 运输性质、 提货方式中的任一项变更，
	 * 在审核同意后生成货物当前库存部门或下一环节部门（在途时）的待办事项； 在审核同意后， 需要人工受理的运单变更由系统自动生成受理部门的消息提醒，
	 * 提醒受理部门所有成员， 及时处理此申请， 申请信息主要包括：单号、 更改类型、 申请时间、 最迟处理时间。 如单号发生变更， 原单号可以再次被使用
	 * 注：更改单在审核同意后， 分为两种：不需要人工受理即系统自动受理、 需要人工受理。 以下情形不需要人工受理： 汽运： 未打印到达联；
	 * 空运/偏线（含中转下线）： 未到达原最终配载部门， 即外发部门； 整车： 未打印到达联且未交接出库；
	 * 
	 * 以下情形需要人工受理： 汽运： 已打印到达联； 空运/偏线（含中转下线）： 已到达原最终配载部门， 即外发部门； 整车：
	 * 已经打印到达联或者交接出库；
	 * 
	 * 
	 * SR8 运单变更审核权限授权相关的业务规则： 代理人只能选择本组织下的用户； 代理人不能为委托人本人； 权限授权给委托人后，
	 * 代理人仍具有审核权限；
	 * 
	 * 数据元素 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 申请日期 运单变更申请的 格式：2012-07-10 20 是 单号
	 * 运单变更的运单号 文本 8 否 审核状态 运单变更的审核状态， 包含待审核、 同意、 拒绝， 默认显示为全部 下拉框 10 否 申请部门
	 * 运单变更申请的部门 文本 20 是 系统默认当前组织， 不可编辑 查询结果 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 单号
	 * 运单变更起草的起始日期 日期 20 审核状态 运单变更的审核状态 变更类型 运单变更的变更类型， 包含：转运单、 返货单、 更改单、 作废运单、
	 * 中止运单 变更凭证 运单变更上传的凭证， 可点击查看 申请部门 运单变更申请人所属部门 申请人 运单变更的申请人 申请时间 运单变更的申请时间，
	 * 格式为2012-10-10 00:00:00 审核人 执行运单变更审核的操作用户， 若审核状态为“待审核”则显示为空 审核时间
	 * 运单变更审核的时间， 格式为2012-10-10 00:00:00 若审核状态为“待审核”则显示为空
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:30:20
	 */
	private void addListener() {
		/**
		 * 审核开始时间
		 */
		fossdBeginDate.addActionListener(new ActionListener() {

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
				if (selectedYear > nowYear) {
					MsgBox.showInfo(i18n
							.get("foss.gui.changingexp.waybillRFCCheckAndDeal.date.validate.msg"));
					fossdBeginDate.setDate(new Date());
					return;
				}
			}
		});

		/**
		 * 审核结束时间
		 */
		fossdEndDate.addActionListener(new ActionListener() {

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
				if (selectedYear > nowYear) {
					MsgBox.showInfo(i18n
							.get("foss.gui.changingexp.waybillRFCCheckAndDeal.date.validate.msg"));
					fossdBeginDate.setDate(new Date());
					return;
				}
			}
		});
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				waybillNoTxt.setText("");
				Date date = new Date();
				fossdEndDate.setDate(date);
				fossdBeginDate.setDate(date);
				status.setSelectedIndex(0);
			}
		});

		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = table.getSelectedRow();
					if (row >= 0) {
						ApplyChangeVoModel model = (ApplyChangeVoModel) table
								.getModel();
						List<WaybillRfcChangeDto> data = model.getData();
						WaybillRfcChangeDto selecteInfo = data.get(row);
						txtWaybillNo.setText(selecteInfo.getWaybillNumber());
					}
				}
			
				
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					if (row >= 0) {
						ApplyChangeVoModel model = (ApplyChangeVoModel) table
								.getModel();
						List<WaybillRfcChangeDto> data = model.getData();
						WaybillRfcChangeDto selecteInfo = data.get(row);
						ChangeCheckStatusDialog dialog = new ChangeCheckStatusDialog(
								selecteInfo, WaybillRFCCheckUI.this, row);
						WindowUtil.centerAndShow(dialog);
					}
				}
			}
		});

		// 查看凭证的监听事件
		viewColumn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					WaybillRfcChangeDto dto = getSelectedDto();
					List<WaybillRfcProofEntity> waybillRfcProofList = waybillRfcVarificationService
							.queryWayBillRfcProofByRfcId(dto.getChangeBillId());
					if (waybillRfcProofList.size() == 0) {
						MsgBox.showInfo(i18n
								.get("foss.gui.changingexp.waybillRFCCheckAndDeal.proof.validate.msg"));
					} else {
						JImageViewCheckDialog dialog = new JImageViewCheckDialog(
								waybillRfcProofList);
						WindowUtil.centerAndShow(dialog);
					}

				} catch (BusinessException e1) {
					MsgBox.showError(MessageI18nUtil.getMessage(e1, i18n));
				}
			}
		});
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:30:47
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(75dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,}));

		JPanel panel1 = new JPanel();
		panel1.setBorder(new JDTitledBorder(
				i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.queryCondition.label")));

		add(panel1, "2, 2, 5, 1, fill, fill");
		FormLayout flpanel1 = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC, });
		flpanel1.setColumnGroups(new int[][] { new int[] { FOUR, SIX, TEN, FOURTEEN, EIGHTEEN },
				new int[] { TWO, EIGHT, TWELVE, SIXTEEN } });
		panel1.setLayout(flpanel1);

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.applyDate.label"));
		panel1.add(lblNewLabel, "2, 2");
		this.setBackground(CommonUtils.getExpressColor());
		fossdBeginDate = new JXDatePicker();
		Date date = new Date();
		fossdBeginDate.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
		fossdBeginDate.getLinkPanel().setVisible(false);
		fossdBeginDate.setDate(date);
		fossdBeginDate.getEditor().setEditable(false);
		panel1.add(fossdBeginDate, "4, 2");

		fossdEndDate = new JXDatePicker();
		fossdEndDate.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
		fossdEndDate.getLinkPanel().setVisible(false);
		fossdEndDate.setDate(date);
		fossdEndDate.getEditor().setEditable(false);
		panel1.add(fossdEndDate, "6, 2");

		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.billNo.label"));
		panel1.add(lblNewLabel1, "8, 2, right, default");

		waybillNoTxt = new JTextField();
		panel1.add(waybillNoTxt, "10, 2, fill, default");
		waybillNoTxt.setColumns(TEN);
		NumberDocument numberDocument = new NumberDocument(TEN);
		waybillNoTxt.setDocument(numberDocument);

		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.auditStatus.label"));
		panel1.add(label, "12, 2, 2, 1");

		status = new JComboBox();
		panel1.add(status, "14, 2, fill, fill");

		statusModel = new DefaultComboBoxModel();
		statusModel.addElement(new CheckStatusVo(null, i18n
				.get("foss.gui.changingexp.waybillRFCUI.common.all")));
		statusModel.addElement(new CheckStatusVo(WaybillRfcConstants.PRE_AUDIT,
				i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.pendingAudit.label")));
		statusModel
				.addElement(new CheckStatusVo(
						WaybillRfcConstants.PRE_ACCECPT,
						i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.agree")));
		statusModel.addElement(new CheckStatusVo(
				WaybillRfcConstants.AUDIT_DENY, i18n
						.get("foss.gui.changingexp.waybillRFCCheckAndDeal.deny")));

		status.setModel(statusModel);
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.auditDept.label"));
		panel1.add(lblNewLabel2, "16, 2, right, default");

		approveDepartmentText = new JTextField();
		approveDepartmentText.setEnabled(false);

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		String OrgCurrentName = user.getEmployee().getDepartment().getName();
		approveDepartmentText.setText(OrgCurrentName);

		panel1.add(approveDepartmentText, "18, 2, fill, default");
		approveDepartmentText.setColumns(TEN);

		JPanel panel3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel3.getLayout();
		flowLayout.setHgap(FIFTY);
		panel1.add(panel3, "2, 4, 17, 1, fill, fill");

		Dimension dimension = new Dimension(FIFTY, THIRDTY);

		queryBtn = new JButton(
				i18n.get("foss.gui.changingexp.waybillRFCUI.common.query"));
		queryBtn.setMinimumSize(dimension);
		queryBtn.setMaximumSize(dimension);
		queryBtn.setPreferredSize(dimension);
		panel3.add(queryBtn);

		resetBtn = new JButton(
				i18n.get("foss.gui.changingexp.waybillRFCUI.common.reset"));
		resetBtn.setSize(dimension);
		resetBtn.setMinimumSize(dimension);
		resetBtn.setPreferredSize(dimension);
		panel3.add(resetBtn);

		JPanel panel2 = new JPanel();
		panel2.setBorder(new JDTitledBorder(
				i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.queryResult.label")));
		add(panel2, "2, 4, 5, 1, fill, fill");
		panel2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC, }));

		JScrollPane scrollPane = new JScrollPane();
		panel2.add(scrollPane, "2, 2, fill, fill");

		table = new JXTable(new ApplyChangeVoModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		// table.setEditable(false);
		table.setColumnControlVisible(true);
		table.getColumn(TWO).setCellRenderer(new ColorRender());

		// 添加Button样式
		viewColumn = TableFactory
				.createLinkedButtonColumn(table, THREE, EIGHTY);
		TableFactory.createRowColumn(table);
		scrollPane.setViewportView(table);
		
		lblNewLabel_1 = new JLabel("运单号：");
		add(lblNewLabel_1, "2, 6, right, default");
		
		txtWaybillNo = new JTextField();
		add(txtWaybillNo, "4, 6, fill, default");
		txtWaybillNo.setColumns(TEN);

	}

	/**
	 * 刷新 waybillRFCCheckUI的表格
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-7 上午9:32:15
	 * @param selectedRowNo
	 * @see
	 */
	public void waybillRFCCheckUITableRefresh(int selectedRowNo) {
		queryBtn.doClick();
	}

	/**
	 * 
	 * 获取当前选中审核对象
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:31:38
	 */
	protected WaybillRfcChangeDto getSelectedDto() {
		int row = table.getSelectedRow();
		return getTableData().get(row);
	}

	/**
	 * 
	 * 获取表格数据
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:31:56
	 */
	public List<WaybillRfcChangeDto> getTableData() {
		ApplyChangeVoModel applyChangeVoModel = ((ApplyChangeVoModel) table
				.getModel());
		return applyChangeVoModel.getData();
	}

	/**
	 * @return the waybillNoTxt
	 */
	public JTextField getWaybillNoTxt() {
		return waybillNoTxt;
	}

	/**
	 * @return the approveDepartmentText
	 */
	public JTextField getApproveDepartmentText() {
		return approveDepartmentText;
	}

	/**
	 * @return the status
	 */
	public JComboBox getStatus() {
		return status;
	}

	/**
	 * @return the statusModel
	 */
	public DefaultComboBoxModel getStatusModel() {
		return statusModel;
	}

	/**
	 * @return the table
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * @return the resetBtn
	 */
	public JButton getResetBtn() {
		return resetBtn;
	}

	/**
	 * @return the queryBtn
	 */
	public JButton getQueryBtn() {
		return queryBtn;
	}

	/**
	 * @return the fossdBeginDate
	 */
	public JXDatePicker getFossdBeginDate() {
		return fossdBeginDate;
	}

	/**
	 * @return the fossdEndDate
	 */
	public JXDatePicker getFossdEndDate() {
		return fossdEndDate;
	}

	/**
	 * @return the viewColumn
	 */
	public LinkedButtonColumn getViewColumn() {
		return viewColumn;
	}

	/**
	 * 
	 * 封装表格的数据模型， 设置成以对象进行操作
	 * 
	 * @author FOSS-jiangfei
	 * @date 2012-11-3 上午11:30:03
	 */
	public class ApplyChangeVoModel extends DefaultTableModel {

		private static final long serialVersionUID = -5039259275224547909L;

		private List<WaybillRfcChangeDto> applyChangeVoList;

		public List<WaybillRfcChangeDto> getData() {
			return applyChangeVoList;
		}

		public void setData(List<WaybillRfcChangeDto> applyChangeVoList) {
			this.applyChangeVoList = applyChangeVoList;
		}

		/**
		 * 
		 * 界面描述 主界面标题：运单变更审核。 营业员发起运单变更（含作废运单、 中止运单）后，
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-19 下午5:18:01
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		// 单号、
		// 审核状态、
		// 变更凭证、
		// 变更类型、
		// 申请部门、
		// 申请人、
		// 申请时间、
		// 审核人、
		// 审核时间
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.billNo");
			case 1:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.rfcType");
			case 2:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.auditStatus");
			case THREE:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.rfcEvidence");
			case FOUR:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.applyDept");
			case FIVE:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.applicant");
			case SIX:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.applyTime");
			case SEVEN:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.examiner");
			case EIGHT:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.columnName.auditTime");

			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return NINE;
		}

		@Override
		public int getRowCount() {
			return applyChangeVoList == null ? 0 : applyChangeVoList.size();
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == THREE;
		}

		/**
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-19 下午4:38:50
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				// 单号"
				return applyChangeVoList.get(row).getWaybillNumber();
			case 1:
				// 变更类型
				return getChangeType(applyChangeVoList.get(row).getRfcType());
			case 2:
				// 审核状态
				return applyChangeVoList.get(row).getDealStauts();
			case THREE:
				return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.view.label");

			case FOUR:
				// 申请部门
				return applyChangeVoList.get(row).getApplyDeptName();
			case FIVE:
				// 申请人
				return applyChangeVoList.get(row).getApplyPerson();
			case SIX:
				// 申请时间
				return (applyChangeVoList.get(row).getApplyTime() != null) ? DateUtils
						.getDateStrWithTime(applyChangeVoList.get(row)
								.getApplyTime()) : "";
			case SEVEN:
				// 审核人
				return applyChangeVoList.get(row).getCheckPerson();
			case EIGHT:
				// 审核时间
				return (applyChangeVoList.get(row).getCheckDate() != null) ? DateUtils
						.getDateStrWithTime(applyChangeVoList.get(row)
								.getCheckDate()) : "";
			default:
				return super.getValueAt(row, column);
			}
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			return;
		}

	}

	/**
	 * 转换审核状态
	 * 
	 * @param codeName
	 * @return
	 */
	private String getChangeDealStatus(String codeName) {

		if (StringUtil.isEmpty(codeName)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.init.pendingAudit.label");
		} else if (codeName.equals(WaybillRfcConstants.PRE_ACCECPT)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.agree");
		} else if (codeName.equals(WaybillRfcConstants.AUDIT_DENY)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.deny");
		} else {
			return "";
		}
	}

	/**
	 * 
	 * <p>
	 * 标记状态颜色
	 * </p>
	 * 
	 * @title WaybillRFCCheckUI.java
	 * @package com.deppon.foss.module.pickup.changingexp.client.ui
	 * @author suyujun
	 * @version 0.1 2012-12-18
	 */
	public class ColorRender extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 925311539841066645L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component com = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (value == null) {
				value = "";
			}
			String checkStatus = value.toString();// getChangeDealStatus();
			if (!isSelected) {
				if (StringUtil.isEmpty(checkStatus)) {
					// com.setBackground(Color.GREEN);
				} else if (WaybillRfcConstants.PRE_ACCECPT.equals(checkStatus)) {
					com.setBackground(Color.GREEN);
				} else if (WaybillRfcConstants.AUDIT_DENY.equals(checkStatus)) {
					com.setBackground(Color.PINK);
				}
			}
			JLabel lbl = (JLabel) com;
			lbl.setOpaque(true);
			lbl.setText(getChangeDealStatus(checkStatus));
			return com;
		}
	}

	private String getChangeType(String changeType) {
		if (changeType.equals(WaybillRfcConstants.CUSTOMER_CHANGE)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.change");
		} else if (changeType.equals(WaybillRfcConstants.TRANSFER)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.tansfer");
		} else if (changeType.equals(WaybillRfcConstants.RETURN)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.return");
		} else if (changeType.equals(WaybillRfcConstants.INVALID)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.invalid");
		} else if (changeType.equals(WaybillRfcConstants.INSIDE_CHANGE)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.change");
		} else if (changeType.equals(WaybillRfcConstants.ABORT)) {
			return i18n.get("foss.gui.changingexp.waybillRFCCheckUI.changeType.suspend");
		} else {
			return "";
		}
	}
}