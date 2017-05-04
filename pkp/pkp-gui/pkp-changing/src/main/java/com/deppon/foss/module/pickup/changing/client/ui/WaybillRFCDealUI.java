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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/WaybillRFCDealUI.java
 * 
 * FILE NAME        	: WaybillRFCDealUI.java
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
package com.deppon.foss.module.pickup.changing.client.ui;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changing.client.action.WaybillRfcDealQueryAction;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.DealChangeApplyDialog;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.JImageViewCheckDialog;
import com.deppon.foss.module.pickup.changing.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.LinkedButtonColumn;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 运单变更受理UI
 * 
 * 界面描述
	 * 主界面标题：运单变更受理。
	 * 运单变更审核员（暂定为部门经理）在审核同意运单变更后，
对于非即时生效的运单变更流经受理部门进行受理。用户输入查询条件查询出本部门未受理的运单变更，
进入图1界面，
点击查询结果列表中某一行记录展开为图2界面。
	 * 界面主要由两部分组成：查询条件、
查询结果。
	 * 1.查询条件：申请日期、
单号、
受理状态。
	 *   1）申请日期：提交运单变更申请的日期。
	 * 　2）单号：未受理的运单变更对应的运单号。
	 * 　3）受理状态：运单变更受理的状态，
包含待受理、
同意、
拒绝，
默认为全部。
	 * 　4）查询按钮：查询事件的按钮。
	 *   5）重置按钮：重置查询条件至初始值的按钮。
	 * 2.查询结果：单号、
受理状态、
变更属性、
变更原因、
变更凭证、
库存状态、
申请部门、
申请人、
受理部门、
受理人、
申请时间、
受理时间。详见数据数据元素【查询结果】。
	 * 运单变更状态图
	 * 
	 * 操作步骤
	 * 序号	基本步骤	相关数据	补充步骤
	 * 1	点击“运单变更受理”菜单，
进入“运单变更受理”界面		系统初始化界面控件值，
参见业务规则SR1
	 * 2	录入查询条件，
点击“查询”按钮	查询条件	查询条件的录入参见业务规则SR2；
	 * 系统将符合查询条件的数据显示在“查询结果”界面中，
参见业务规则SR3
	 * 3	点击查询结果列表中任一条记录	查询结果	该行记录扩展显示，
参见业务规则SR3-SR4
	 * 4	选择点击受理状态为“待受理”的一条记录		该行记录扩展显示，
参见业务规则SR3-SR4
	 * 5	点击变更凭证列的“查看“按钮		系统自动在浏览器中打开新页签并显示凭证的电子图片
	 * 6	选填受理备注，
点击“同意”按钮		系统刷新该条记录，
参见业务规则SR5-SR6
	 * 
	 * 扩展事件
	 * 序号	扩展事件	相关数据	备注
	 * 2a	点击“查询”后，
结果列表中无数据显示		提示“未查询到相关信息”
	 * 2b	点击重置按钮		查询条件重置至初始值
	 * 3a	用户可点击变更凭证列的“查看”按钮		系统自动在浏览器中打开新页签并显示凭证的电子图片
	 * 4a	输入受理备注，
点击“拒绝”按钮		系统刷新该条记录，
参见业务规则SR5-SR6
	 * 业务规则
	 * 序号	描述
	 * SR1	申请日期：起始及截止日期均默认为系统当前日期。
	 * 单号：默认为空。
	 * 受理状态：默认为“全部”。
	 * SR2	查询条件中申请日期的起始日期≤截止日期，
起始日期与截止日期间隔不能超过10天。
	 * 查询条件支持组合查询或单一查询，
若单号不为空则以单号为最高查询条件，
不受其他查询条件限制。
	 * SR3	查询结果列表支持扩展显示功能；
	 * 只可查询出受理部门为系统当前组织的运单变更申请。
	 * SR4	查询结果扩展显示包含：【变更信息】、
受理备注、
“同意”按钮、
“拒绝”按钮。
	 * 变更信息：以列表形式显示，
调用运单变更起草界面的【变更信息】。
	 * 受理备注：受理状态为“待受理”时，
受理备注以文本框显示，
可编辑。其他受理状态，
受理备注以文本显示，
显示为受理时的备注信息。
	 * 同意按钮：受理状态为“待受理”时显示，
其他受理状态下不显示。
	 * 拒绝按钮：受理状态为“待受理”时显示，
其他受理状态下不显示。
	 * SR5	若同意受理，
受理备注为选填项，
若未填则系统默认记录受理备注为“同意！”
	 * 若拒绝受理，
受理备注为必填项，
需要录入拒绝受理的原因。
	 * SR6	运单变更受理同意后，
受理状态由“未受理”变为“同意”，
查询结果列表自动刷新该条记录，
运单变更确认状态变为“已确认”；
	 * 运单变更受理拒绝后，
受理状态由“未受理”变为“拒绝”，
查询结果列表自动刷新清除该条记录，
同时生成在线通知通知运单变更申请部门。
	 * 数据元素
	 * 
	 * 查询条件
	 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	 * 申请日期	运单变更申请的	格式：2012-07-10		20	是	
	 * 单号	未受理的运单变更对应的运单号	文本		8	否	
	 * 受理状态	运单变更的受理状态，
包含待受理、
同意、
拒绝，
默认显示为全部	下拉框		10	否	
	 * 查询结果
	 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	 * 单号	未受理的运单变更对应的运单号					
	 * 受理状态	运单变更的受理状态					
	 * 变更属性	分为客户要求及内部要求					在起草运单变更申请时选择客户要求则对应的变更属性为客户要求；选择内部要求则对应的变更属性为内部要求。
	 * 变更原因	运单变更起草时录入的变更原因					
	 * 变更凭证	运单变更提交时上传的变更凭证					
	 * 库存状态	货物在受理部门的库存状态，
含库存中、
未入库、
已出库					
	 * 申请部门	提交运单变更申请的部门					
	 * 申请人	运单变更申请人					
	 * 受理部门	运单变更受理的部门					
	 * 受理人	运单变更受理人					
	 * 申请时间	运单变更申请时间，
格式为2012-10-10  00:00:00					
	 * 受理时间	运单变更受理时间，
格式为2012-10-10  00:00:00					
 * 
 * @author foss-jiangfei
 * @date 2012-12-6 下午7:27:22
 * @since
 * @version
 */
public class WaybillRFCDealUI extends JPanel {
	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRFCDealUI.class);
	
	/**
	 * 80
	 */
	private static final int EIGHTY = 80;


	/**
	 * 30
	 */
	private static final int THIRDTY = 30;

	/**
	 * 50
	 */
	private static final int FIFTY = 50;

	/**
	 * 9
	 */
	private static final int NINE = 9;

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

	private static final int THREE = 3;
	
	private static final int FIVE = 5;
	
	private static final int SEVEN = 7;
	
	private static final int ELEVEN = 11;
	
	/**
	 * DEFAULT css style
	 */
	private static final String DEFAULTGROW = "default:grow";


	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6930229163219897964L;

	/**
	 * 运单号
	 */
	private JTextField waybillNoTxt;

	/**
	 * 受理部门
	 */
	private JTextField approveDepartmentText;

	/**
	 * 受理状态
	 */
	private JComboBox status;

	/**
	 * 受理状态Model
	 */
	private DefaultComboBoxModel statusModel;

	/**
	 * 受理查询表格
	 */
	private JXTable table;

	/**
	 * 重置按钮
	 */
	private JButton resetBtn;

	/**
	 * 查询按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = WaybillRfcDealQueryAction.class)
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

	/**
	 * 运单号
	 */
	@Bind("waybillNoTxt")
	private JTextFieldValidate waybillNoValidator;

	/**
	 * SERVICE
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();
	
	private IWaybillRfcService WaybillRfcService = WaybillRfcServiceFactory.getWaybillRfcService(); 
	
	private JLabel label_1;
	private JTextField txtWaybillNo;
	
	/**
     * 运单变更信息
     */
    private  JTextArea txtChangeInfo;
    
	/**
	 * 
	 * WaybillRFCDealUI
	 * 
	 *  * 界面描述			
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:17:15
	 */
	public WaybillRFCDealUI() {
		init();
		addListener();
		bind();
	}

	/**
	 * 
	 * 页面绑定
	 * 
	 * WaybillRFCDealUI.java
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:17:21
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 添加按钮监听			
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:17:31
	 */
	private void addListener() {
		/**
		 * 受理开始时间
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
				if(selectedYear > nowYear){
					MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.date.validate.msg"));
					fossdBeginDate.setDate(new Date());
					return;
				}
			}
		});
		
		/**
		 * 受理结束时间
		 */
		fossdEndDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JXDatePicker end = (JXDatePicker) e.getSource();
				Calendar cal = Calendar.getInstance();
				cal.setTime(end.getDate());	
				
				Date now = new Date();
				Calendar nowCal = Calendar.getInstance();
				nowCal.setTime(now);
				
				int selectedYear = cal.get(Calendar.YEAR);
				int nowYear = nowCal.get(Calendar.YEAR);
				if(selectedYear > nowYear){
					MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.date.validate.msg"));
					fossdBeginDate.setDate(new Date());
					return;
				}
			}
		});
		// 重置按钮
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
		// 表格双击事件
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = table.getSelectedRow();
					if(row >= 0)
					{
						ApplyChangeVoModel model = (ApplyChangeVoModel) table
						.getModel();
						List<WaybillRfcChangeDto> data = model.getData();
						WaybillRfcChangeDto selecteInfo = data.get(row);
						txtWaybillNo.setText(selecteInfo.getWaybillNumber());
					
						/**在更改单变更受理界面,添加更改单变更信息文本框
                         * I在查询结果页面单击，显示更改单变更后信息
                         * @auuthor 311417 wangfeng
                           @date  2016-4-12
                         * */
                        String waybillRfcId = selecteInfo.getChangeBillId();
                        String changeItems  = WaybillRfcService.queryRfcChangeItemsByWaybillRfcId(waybillRfcId);
                        if(StringUtils.isNotEmpty(changeItems)){
                            txtChangeInfo.setText(changeItems);
                        }
					}
				}
			
				
				if (e.getClickCount() == TWO) {
					int row = table.getSelectedRow();
					if(row >= 0)
					{
						ApplyChangeVoModel model = (ApplyChangeVoModel) table
						.getModel();
						List<WaybillRfcChangeDto> data = model.getData();
						WaybillRfcChangeDto selecteInfo = data.get(row);
						DealChangeApplyDialog dialog = new DealChangeApplyDialog(
								selecteInfo, WaybillRFCDealUI.this, row);
						WindowUtil.centerAndShow(dialog);
					}
				}
			}
		});

		// 查看凭证的监听事件
		viewColumn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WaybillRfcChangeDto dto = getSelectedDto();
				List<WaybillRfcProofEntity> waybillRfcProofList = waybillRfcVarificationService
						.queryWayBillRfcProofByRfcId(dto.getChangeBillId());
				if (waybillRfcProofList.size() == 0) {
					MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.proof.validate.msg"));
				} else {
					JImageViewCheckDialog dialog = new JImageViewCheckDialog(
							waybillRfcProofList);
					WindowUtil.centerAndShow(dialog);
				}
			}
		});
	}

	/**
	 * 
	 * 获取当前选中的受理对象
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:17:55
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
	 * @date 2012-12-25 上午10:18:20
	 */
	public List<WaybillRfcChangeDto> getTableData() {
		ApplyChangeVoModel applyChangeVoModel = ((ApplyChangeVoModel) table
				.getModel());
		return applyChangeVoModel.getData();
	}

	/**
	 * 刷新 waybillRFCDealUI的表格
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-7 上午9:32:15
	 * @param selectedRowNo
	 * @see
	 */
	public void waybillRFCDealUITableRefresh(int selectedRowNo) {
		queryBtn.doClick();
	}

	/**
	 * 
	 * 页面布局初始化
	 * 			
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:18:52
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(67dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				
				RowSpec.decode("55dlu"),
                FormFactory.RELATED_GAP_ROWSPEC,}));

		JPanel panel1 = new JPanel();
		panel1.setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.queryCondition.label")));

		add(panel1, "2, 2, 5, 1, fill, fill");
		FormLayout flpanel1 = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,});
		flpanel1.setColumnGroups(new int[][]{new int[]{FOUR, SIX, TEN, FOURTEEN, EIGHTEEN}, 
				new int[]{TWO, EIGHT, TWELVE, SIXTEEN}});
		panel1.setLayout(flpanel1);

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changing.waybillRFCCheckUI.init.applyDate.label"));
		panel1.add(lblNewLabel, "2, 2");

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

		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.changing.waybillRFCCheckUI.init.billNo.label"));
		panel1.add(lblNewLabel1, "8, 2, right, default");

		waybillNoTxt = new JTextField();
		panel1.add(waybillNoTxt, "10, 2, fill, default");
		waybillNoTxt.setColumns(TEN);
		NumberDocument numberDocument = new NumberDocument(NINE);
		waybillNoTxt.setDocument(numberDocument);

		JLabel label = new JLabel(i18n.get("foss.gui.changing.waybillRFCDealUI.init.acceptStatus.label"));
		panel1.add(label, "12, 2");

		status = new JComboBox();
		panel1.add(status, "14, 2, fill, fill");

		statusModel = new DefaultComboBoxModel();
		statusModel.addElement(new CheckStatusVo(null, i18n.get("foss.gui.changing.waybillRFCUI.common.all")));
		statusModel.addElement(new CheckStatusVo(
				WaybillRfcConstants.PRE_ACCECPT, i18n.get("foss.gui.changing.waybillRFCDealUI.init.pendingAccept.label")));
		statusModel.addElement(new CheckStatusVo(WaybillRfcConstants.ACCECPT,
				i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.agree")));
		statusModel.addElement(new CheckStatusVo(
				WaybillRfcConstants.ACCECPT_DENY, i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.deny")));

		status.setModel(statusModel);
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.changing.waybillRFCCheckUI.init.auditDept.label"));
		panel1.add(lblNewLabel2, "16, 2, right, default");

		approveDepartmentText = new JTextField();
		approveDepartmentText.setEnabled(false);

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		String orgCurrentName = user.getEmployee().getDepartment().getName();
		approveDepartmentText.setText(orgCurrentName);

		panel1.add(approveDepartmentText, "18, 2, fill, default");
		approveDepartmentText.setColumns(TEN);

		JPanel panel3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel3.getLayout();
		flowLayout.setHgap(FIFTY);
		panel1.add(panel3, "2, 4, 17, 1, fill, fill");

		Dimension dimension = new Dimension(FIFTY, THIRDTY);
		queryBtn = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.query"));
		queryBtn.setMinimumSize(dimension);
		queryBtn.setMaximumSize(dimension);
		queryBtn.setPreferredSize(dimension);
		panel3.add(queryBtn);

		resetBtn = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.reset"));
		resetBtn.setMinimumSize(dimension);
		resetBtn.setMaximumSize(dimension);
		resetBtn.setPreferredSize(dimension);
		panel3.add(resetBtn);

		JPanel panel2 = new JPanel();
		panel2.setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.queryResult.label")));
		add(panel2, "2, 4, 5, 1, fill, fill");
		panel2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));

		JScrollPane scrollPane = new JScrollPane();
		panel2.add(scrollPane, "2, 2, fill, fill");

		table = new JXTable(new ApplyChangeVoModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		table.getColumn(1).setCellRenderer(new ColorRender());

		// 添加Button样式
		viewColumn = TableFactory.createLinkedButtonColumn(table, FOUR, EIGHTY);
		TableFactory.createRowColumn(table);
		scrollPane.setViewportView(table);
		
		label_1 = new JLabel("单号：");
		add(label_1, "2, 6, right, default");
		
		txtWaybillNo = new JTextField();
		add(txtWaybillNo, "4, 6, fill, default");
		txtWaybillNo.setColumns(TEN);
		
		//更改单复制功能
		JPanel panel4 = new JPanel();
        panel4.setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.waybillRFCCheckAndDeal.changeInfo.label")));
        add(panel4, "2, 8, 5, 1, fill, fill");
        panel4.setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,},
        new RowSpec[] {
                FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_ROWSPEC,}));
        JScrollPane scrollPane4 = new JScrollPane();
        panel4.add(scrollPane4, "2, 2, fill, fill");
        txtChangeInfo = new JTextArea();
        scrollPane4.setViewportView(txtChangeInfo);//
        txtChangeInfo.setLineWrap(true);//激活自动换行功能
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
	 * @return the waybillNoValidator
	 */
	public JTextFieldValidate getWaybillNoValidator() {
		return waybillNoValidator;
	}

	/**
	 * 
	 * 封装表格的数据模型，			
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
		 * 界面描述		
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-19 下午4:40:12
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.columnName.billNo");
			case 1:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.acceptStatus");
			case 2:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.changeProperty");
			case THREE:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.changeReason");
			case FOUR:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.columnName.rfcEvidence");
			case FIVE:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.stockStatus");
			case SIX:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.columnName.applyDept");
			case SEVEN:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.columnName.applicant");
			case EIGHT:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.acceptDept");
			case NINE:
				return i18n.get("foss.gui.changing.waybillRFCDealUI.columnName.assignee");
			case TEN:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.columnName.applyTime");
			case ELEVEN:
				return i18n.get("foss.gui.changing.waybillRFCQueryCheckAndDealUI.columnName.acceptTime");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return TWELVE;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == FOUR;
		}

		@Override
		public int getRowCount() {
			return applyChangeVoList == null ? 0 : applyChangeVoList.size();
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			return;
		}

		/**
		 * 
		 *  * 界面描述			
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-19 下午5:20:41
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				// 单号
				return applyChangeVoList.get(row).getWaybillNumber();
			case 1:
				// 受理状态
				return applyChangeVoList.get(row).getDealStauts();
			case 2:
				// 变更属性
				return WaybillRfcConstants.CUSTOMER_REQUIRE.equals(applyChangeVoList.get(row).getRfcSource()) ? "客户要求" : "内部要求";
			case THREE:
				// 变更原因
				return applyChangeVoList.get(row).getRfcReason();
			case FOUR:
				return i18n.get("foss.gui.changing.waybillRFCCheckUI.view.label");
			case FIVE:
				// 库存状态
				String stockStatus = applyChangeVoList.get(row).getStockStatus();
				if("RECEIVE_STOCK".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.receiveStock");
				}else if("RECEIVE_STOCK_OUT".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.receiveStockOut");
				}else if("TRANSFER_STOCK".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.transferStock");
				}else if("TRANSFER_STOCK_OUT".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.transferStockOut");
				}else if("DELIVERY_STOCK".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.deliverStock");
				}else if("DELIVERY_STOCK_OUT".equals(stockStatus)){
					return i18n.get("foss.gui.changing.waybillRFCDealUI.stockStatus.deliverStockout");
				}else{
					return stockStatus;
				}
			case SIX:
				// 申请部门
				return applyChangeVoList.get(row).getApplyDeptName();
			case SEVEN:
				// 申请人
				return applyChangeVoList.get(row).getApplyPerson();
			case EIGHT:
				// 受理部门
				/*if (applyChangeVoList.get(row).getDealStauts()!=null&&
					applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.PRE_ACCECPT)) {
					return "";
					
				}else */if (applyChangeVoList.get(row).getDealStauts()!=null
					&&(applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT)
					    || applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT_DENY))
					) {
					return applyChangeVoList.get(row).getHandleDeptName();
				}else {
					return "";
				}	
				
			case NINE:
				// 受理人
				
				/*if (applyChangeVoList.get(row).getDealStauts()!=null&&
					applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.PRE_ACCECPT)) {
					return "";
				}else */if (
						applyChangeVoList.get(row).getDealStauts()!=null
						&&(applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT)
						   ||applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT_DENY))
						) {
					return applyChangeVoList.get(row).getHandlePerson();
				}else {
					return "";
				}		
			case TEN:
				// 申请时间				
				return (applyChangeVoList.get(row).getApplyTime() != null) ? 
						DateUtils.getDateStrWithTime(applyChangeVoList.get(row).getApplyTime()) : "";								
			case ELEVEN:
				// 受理时间
				/*if (applyChangeVoList.get(row).getDealStauts()!=null&&
					applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.PRE_ACCECPT)) {
					return "";
				} else */if (
						applyChangeVoList.get(row).getDealStauts()!=null
						&&(applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT)
						  || applyChangeVoList.get(row).getDealStauts().equals(WaybillRfcConstants.ACCECPT_DENY))
						  ) {
					return (applyChangeVoList.get(row).getHandleTime() != null) ? 
							DateUtils.getDateStrWithTime(applyChangeVoList.get(row).getHandleTime()) : "";
				} else{
					return "";
				}
				
				
			default:
				return super.getValueAt(row, column);
			}
		}

	}

	/**
	 * 转换受理状态
	 * 
	 * @param codeName
	 * @return
	 */
	private String getChangeDealStatus(String codeName) {

		if (codeName.equals(WaybillRfcConstants.PRE_ACCECPT)) {
			return i18n.get("foss.gui.changing.waybillRFCDealUI.init.pendingAccept.label");
		} else if (codeName.equals(WaybillRfcConstants.ACCECPT)) {
			return i18n.get("foss.gui.changing.waybillRFCDealUI.acceptAgree.label");
		} else if (codeName.equals(WaybillRfcConstants.ACCECPT_DENY)) {
			return i18n.get("foss.gui.changing.waybillRFCDealUI.acceptRefuse.label");
		}
		return "";
	}

	/**
	 * 
	 * <p>
	 * 颜色标记<br />
	 * </p>
	 * 
	 * @title WaybillRFCDealUI.java
	 * @package com.deppon.foss.module.pickup.changing.client.ui
	 * @author suyujun
	 * @version 0.1 2012-12-18
	 */
	public class ColorRender extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 334198048146656915L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component com = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			String checkStatus = value.toString();// getChangeDealStatus();

			if (WaybillRfcConstants.PRE_ACCECPT.equals(checkStatus)) {
				//to do nothing
			} else if (WaybillRfcConstants.ACCECPT.equals(checkStatus)) {
				com.setForeground(Color.GREEN);
			} else {
				com.setForeground(Color.RED);
			}
			((JLabel) com).setText(getChangeDealStatus(checkStatus));
			return com;
		}
	}
}