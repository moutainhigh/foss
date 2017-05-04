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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/PrintSignUI.java
 * 
 * FILE NAME        	: PrintSignUI.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.creating.client.action.PrintSignAction;
import com.deppon.foss.module.pickup.creating.client.action.PrintSignSetupAction;
import com.deppon.foss.module.pickup.creating.client.listener.PrintSignBindingListener;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.PrinSignDescriptor;
import com.deppon.foss.module.pickup.creating.client.vo.StartSignLabelVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.DispatchOrderChannelNumberEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.gui.LblPrtSetupWindow;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;
import com.deppon.foss.prt.PrintUtil;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 始发签收单标签打印界面
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,content:
 * </p>
 * 
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
@ContainerSeq(seq=1)
public class PrintSignUI extends JPanel implements IApplicationAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int MAXLENGTH = 50;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PrintSignUI.class);

	// 运单号
	@Bind("waybillNo")
	private JTextFieldValidate txtWaybillNo;
	// 始发部门
	private JTextField txtStartOrg;
	// 到达部门
	private JTextField txtReachOrg;
	// 目的站
	private JTextField txtDestnationOrg;
	//收货人标签
    JLabel lblConsignee;
	
	/*****/
	@FocusSeq(seq=1)
	private JTextField tWaybillNo;

	private JTextField tReceiver;

	private JTextField tGoodsNum;

	private JTextField tTargetOrg;

	private JTextField tStartOrg;

	private JTextField tTransTpye;
	
	/**是否零担电子运单(是/否)*/
	private JTextField tIsElecLtlWayBill;
	
	@FocusSeq(seq=2)
	private JTextField tBeginGoodNum;
	@FocusSeq(seq=3)
	private JTextField tEndGoodNum;

	// 是否异形货物
	private JCheckBox chckbxNewCheckBox;
	
	// 是否显示“德邦物流”
	private JCheckBox chckbxPrintCheckBox;
	
	// 是否显示“德邦物流”
	private JCheckBox chckbxPrintWoodCheckBox;
	

	public JCheckBox getChckbxPrintWoodCheckBox() {
		return chckbxPrintWoodCheckBox;
	}

	public void setChckbxPrintWoodCheckBox(JCheckBox chckbxPrintWoodCheckBox) {
		this.chckbxPrintWoodCheckBox = chckbxPrintWoodCheckBox;
	}


	// 文本区 流水号
	private JTextArea textArea;

	// 打印设置
	@FocusSeq(seq=4)
	private JButton btnPrintConfigure;

	

	// 打印
	@FocusSeq(seq=5)
	private JButton btnPrint;
	
	/**
	 * 打木标签打印和打印设置按钮
	 */
	//单号
	@FocusSeq(seq=1)
	private JTextField woodWaybillNo;
	public JTextField getWoodWaybillNo() {
		return woodWaybillNo;
	}

	public void setWoodWaybillNo(JTextField woodWaybillNo) {
		this.woodWaybillNo = woodWaybillNo;
	}


	//打印设置
	@FocusSeq(seq=7)
	private JButton woodbtnPrintConfigure;
	//包装类型
	JLabel PackageType;
	private JTextField tPackageType;
	public JTextField gettPackageType() {
		return tPackageType;
	}

	public void settPackageType(JTextField tPackageType) {
		this.tPackageType = tPackageType;
	}

	//打木架件数
    JLabel standGoodsNum;
    private JTextField tstandGoodsNum;
    public JTextField getTstandGoodsNum() {
		return tstandGoodsNum;
	}

	public void setTstandGoodsNum(JTextField tstandGoodsNum) {
		this.tstandGoodsNum = tstandGoodsNum;
	}

	public JTextField getTboxGoodsNum() {
		return tboxGoodsNum;
	}

	public void setTboxGoodsNum(JTextField tboxGoodsNum) {
		this.tboxGoodsNum = tboxGoodsNum;
	}

	public JTextField getTsalverGoodsNum() {
		return tsalverGoodsNum;
	}

	public void setTsalverGoodsNum(JTextField tsalverGoodsNum) {
		this.tsalverGoodsNum = tsalverGoodsNum;
	}

	//打木箱件数
    JLabel boxGoodsNum;
    private JTextField tboxGoodsNum;
    //打木托件数
    JLabel salverGoodsNum;
    private JTextField tsalverGoodsNum;

	/*// 退出按钮
	private JButton btnExt;*/

	private static final String COMMA = ",";

	private static final String FORMATSTR = "%04d";

	// 判断在线还是离线 true 在线 ，false 离线
	private Boolean isOnOrOffLIne;

	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();

	//存储根据运单号所查询
	private List<BarcodePrintLabelDto> printLabelBeans = new ArrayList<BarcodePrintLabelDto>();
	
	// 标签信息
	private List<LabeledGoodEntity> labeledlist;

	// 通过工厂类获得运单服务类
	private transient IWaybillService waybillService;

	// 绑定接口
	private IBinder<StartSignLabelVo> printBinder;
	
	public IWoodenRequirementsService getWoodenRequirementsService() {
		return woodenRequirementsService;
	}

	public void setWoodenRequirementsService(
			IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}

	private IWoodenRequirementsService woodenRequirementsService;
	// 日志
	private static final Log LOG = LogFactory.getLog(PrintSignUI.class);
	
	private static final int TEN = 10;

	private static final int SIX = 6;

	private static final int NUM_340 = 340;

	private static final int NUM_110 = 110;

	private static final String PREVIEWPNG = "preview.png";
	
	private static final String SEARCH16GIF = "Search16.gif";

	private static final int NUM_662 = 662;

	private Map<String, IBinder<StartSignLabelVo>> bindersMap = new HashMap<String, IBinder<StartSignLabelVo>>();
	/**
	 * 打木标签
	 * zhuxue
	 */
	//查询按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = WoodLabelPrintSearchAction.class)
	JButton btnPrint5 = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
	
	//打印
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = LabelWoodBatchprintingpiAction.class)
	JButton woodbtnPrint = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
	
	
	
	// 打印
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = PrintSignAction.class)
	JButton printButton = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));

	// 打印设置
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = PrintSignSetupAction.class)
	JButton printViewButton = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));

	private FocusListener focusListenerBegin;

	private FocusListener focusListenerEnd;
	
	//运单状态
	private String waybillStatus;


	/**
	 * 以下是打木包装所需数据
	 * zhuxue
	 */
	// 全选
	private JCheckBox allSelectCheckBox;
	//流水号勾选框
	private JXTable table = new JXTable();
	//选中的checkbox
	private List<JCheckBox> list;
	//刷新列表
	private List listNew;
	
	public List getListNew() {
		return listNew;
	}

	public void setListNew(List listNew) {
		this.listNew = listNew;
	}

	//选中的列
	private PrintWoodSignCheckBoxColumn checkBoxColumn;
	
	public PrintWoodSignCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public void setCheckBoxColumn(PrintWoodSignCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}

	/**
	 * 选中的导出流水号
	 * zhuxue
	 */
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

	/**
	 * 选中的导出id
	 */
	private List<String> selectIdList = new ArrayList<String>();
	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}

	public void setSelectExportWaybillNoList(List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}

	public List<String> getSelectIdList() {
		return selectIdList;
	}

	public void setSelectIdList(List<String> selectIdList) {
		this.selectIdList = selectIdList;
	}

	public List<JCheckBox> getList() {
		return list;
	}

	public void setList(List<JCheckBox> list) {
		this.list = list;
	}

	public JXTable getTable() {
		return table;
	}

	public void setTable(JXTable table) {
		this.table = table;
	}

	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}

	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}
	
	public PrintSignUI() {
		// 初始化界面
		init();
		// 初始化是否在线
		initIsOnOrOffLIne();
		// 初始化事件监听
		initListener();
		addListener();
		bind();
		registToRespository();
	}

	/**
	 * 初始化在线还是离线
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-25 上午10:18:48
	 */
	private void initIsOnOrOffLIne() {
		if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			isOnOrOffLIne = true;
		} else {
			isOnOrOffLIne = false;
		}
	}

	private void initListener() {
		tWaybillNo.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				// 失去焦点时，可以认为输入结束
				if (isOnOrOffLIne) {
					if (StringUtils.isNotEmpty(tWaybillNo.getText())) {
						// 不为空 就是标签打印 否则就应该是重打 进行界面初始赋值 因为标签打印会传来流水号
						
						//判断是否超过50个字符，超过了提醒他，怕了于文浩了，净干这事情
						if(tWaybillNo.getText().trim().length()>MAXLENGTH){
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), "运单位数不能超过"+MAXLENGTH, JOptionPane.WARNING_MESSAGE);
							return;
						}
						// 在线操作
						getPrintBeanDetailInfoOnLine(tWaybillNo.getText().trim());
					}
				} else {
					// 离线操作
					if (StringUtils.isNotEmpty(tWaybillNo.getText())) {
						getPrintBeanDetailInfoOnLine(tWaybillNo.getText().trim());
					}
				}
			}
		});
	}

	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("center:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("max(200dlu;default)"),
				RowSpec.decode("fill:max(47dlu;default)"), }));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel panel = new JPanel();
		// panel.setBorder(new TitledBorder(null,
		// "\u7BA1\u7406\u8425\u4E1A\u90E8\u51FA\u53D1\u8FD0\u5355",
		// TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// add(panel, "1, 2, center, default");
		FormLayout flpanel = new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(145px;default):grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(37dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(39dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(53dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(51dlu;default):grow"), }, new RowSpec[] {
				RowSpec.decode("max(6dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("23px"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, });
		panel.setLayout(flpanel);

		// 运单号
		JLabel label = new JLabel(i18n.get("foss.gui.creating.printSignUI.waybillNo.label") + ":");
		panel.add(label, "3, 3, right, default");

		txtWaybillNo = new JTextFieldValidate();
		panel.add(txtWaybillNo, "5, 3, 5, 1");
		txtWaybillNo.setColumns(TEN);

		// 始发部门
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.printSignUI.startOrg.label") + ":");
		panel.add(label1, "3, 5, right, default");

		txtStartOrg = new JTextField();
		panel.add(txtStartOrg, "5, 5, 5, 1, fill, default");
		txtStartOrg.setColumns(TEN);
		txtStartOrg.setEditable(false);

		// 到达部门
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.printSignUI.reachOrg.label") + ":");
		panel.add(label2, "3, 7, right, default");

		txtReachOrg = new JTextField();
		panel.add(txtReachOrg, "5, 7, 5, 1, fill, default");
		txtReachOrg.setColumns(TEN);
		txtReachOrg.setEditable(false);

		// 目的站
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.printSignUI.destnationOrg.label") + ":");
		panel.add(label3, "3, 9, right, default");

		txtDestnationOrg = new JTextField();
		panel.add(txtDestnationOrg, "5, 9, 5, 1, fill, default");
		txtDestnationOrg.setColumns(TEN);
		txtDestnationOrg.setEditable(false);

		panel.add(printButton, "5, 11, left, center");
		panel.add(printViewButton, "9, 11, right, center");

		JPanel panel5 = new JPanel();

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(25dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(76dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("center:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(74dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(84dlu;default)"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(22dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(70dlu;default)"), }));

		// 运单号
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.waybillNo.label") + ":");
		panel2.add(lblNewLabel, "2, 2, right, default");

		tWaybillNo = new JTextField();
		panel2.add(tWaybillNo, "4, 2, fill, default");
		tWaybillNo.setColumns(TEN);
		tWaybillNo.setDocument(new NumberDocument(TEN));

		// 收货人
		lblConsignee = new JLabel(i18n.get("foss.gui.creating.printSignUI.receiver.label") + ":");
		panel2.add(lblConsignee, "6, 2, right, default");

		tReceiver = new JTextField();
		panel2.add(tReceiver, "8, 2, fill, default");
		tReceiver.setColumns(TEN);
		tReceiver.setEditable(false);

		JPanel panel3 = new JPanel();
		panel2.add(panel3, "10, 1, 1, 6, default, top");
		panel3.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(64dlu;default):grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), }));

		// 起始件数库号
		JLabel lblNewLabel6 = new JLabel(i18n.get("foss.gui.creating.printSignUI.beginGoodNum.label"));
		panel3.add(lblNewLabel6, "2, 2");

		// 截止件数库号
		JLabel lblNewLabel7 = new JLabel(i18n.get("foss.gui.creating.printSignUI.endGoodNum.label"));
		panel3.add(lblNewLabel7, "8, 2");

		tBeginGoodNum = new JTextField();
		panel3.add(tBeginGoodNum, "2, 4, fill, default");
		tBeginGoodNum.setColumns(TEN);

		// 输入起始件数回车获得对应流水号
		tBeginGoodNum.setToolTipText(i18n.get("foss.gui.creating.printSignUI.beginGoodNum.notice"));
		tBeginGoodNum.setDocument(new NumberDocument(SIX));

		tEndGoodNum = new JTextField();
		panel3.add(tEndGoodNum, "8, 4, fill, default");
		tEndGoodNum.setColumns(TEN);

		// 输入截止件数回车获得对应流水号
		tEndGoodNum.setToolTipText(i18n.get("foss.gui.creating.printSignUI.endGoodNum.notice"));
		tEndGoodNum.setDocument(new NumberDocument(SIX));

		// JLabel lblNewLabel8 new JLabel 补打件数
		// 异型货物
		chckbxNewCheckBox = new JCheckBox(i18n.get("foss.gui.creating.printSignUI.allotype.label"));
		panel3.add(chckbxNewCheckBox, "2, 6, left, bottom");

		//是否显示德邦LogoPrintSignUI
		chckbxPrintCheckBox = new JCheckBox(i18n.get("foss.gui.creating.printSignUI.allotype.isPrintLogo"));
		chckbxPrintCheckBox.setSelected(true);
		panel3.add(chckbxPrintCheckBox, "3, 6, 5, 1, left, default");
		// 件数
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.printSignUI.goodNum.label") + ":");
		panel2.add(lblNewLabel2, "2, 4, right, default");

		tGoodsNum = new JTextField();
		panel2.add(tGoodsNum, "4, 4, fill, default");
		tGoodsNum.setColumns(TEN);

		// 目的站
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.printSignUI.destnationOrg.label") + ":");
		panel2.add(lblNewLabel3, "6, 4, right, default");

		tTargetOrg = new JTextField();
		panel2.add(tTargetOrg, "8, 4, fill, default");
		tTargetOrg.setColumns(TEN);

		// 始发站
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.creating.printSignUI.startStation.label") + ":");
		panel2.add(lblNewLabel4, "2, 6, right, default");

		tStartOrg = new JTextField();
		panel2.add(tStartOrg, "4, 6, fill, default");
		tStartOrg.setColumns(TEN);

		// 运输性质
		JLabel lblNewLabel5 = new JLabel(i18n.get("foss.gui.creating.printSignUI.product.label") + ":");
		panel2.add(lblNewLabel5, "6, 6, right, default");

		tTransTpye = new JTextField();
		panel2.add(tTransTpye, "8, 6, fill, default");

		tTransTpye.setColumns(TEN);
		
		//是否零担电子运单
		JLabel lblIsElecWaybill = new JLabel(i18n.get("foss.gui.creating.printSignUI.isElecWayBill.label"));
		panel2.add(lblIsElecWaybill, "2, 8, right, default");
		
		tIsElecLtlWayBill = new JTextField();
		panel2.add(tIsElecLtlWayBill, "4, 8, fill, default");
		tIsElecLtlWayBill.setColumns(NumberConstants.NUMBER_10);
		
		GroupLayout groupLayout = new GroupLayout(panel5);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGap(NumberConstants.NUMBER_6).addComponent(panel2, GroupLayout.DEFAULT_SIZE, NUM_662, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(NumberConstants.NUMBER_17)));

		// 最终打印流水号
		JLabel lblNewLabel10 = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.label") + ":");
		panel2.add(lblNewLabel10, "2, 10, 3, 1");

		textArea = new JTextArea();
		textArea.setRows(SIX);
		textArea.setLineWrap(true);
		textArea.setSize(NUM_340, NUM_110);
		JScrollPane jScrollPane = new JScrollPane(textArea);
		panel2.add(jScrollPane, "2, 12, 7, 1");

		JPanel panel4 = new JPanel();
		panel2.add(panel4, "9, 12, 2, 1, fill, fill");
		panel4.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(41dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
				
						// 打印设置
						btnPrintConfigure = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
						panel4.add(btnPrintConfigure, "2, 8");
		
				// 打印
				btnPrint = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
				panel4.add(btnPrint, "2, 10");

		// 退出
		/*btnExt = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.quit"));
		panel4.add(btnExt, "2, 10");*/
		panel5.setLayout(groupLayout);
		tGoodsNum.setEditable(false);
		tTargetOrg.setEditable(false);
		tStartOrg.setEditable(false);
		tTransTpye.setEditable(false);
		tIsElecLtlWayBill.setEditable(false);
		textArea.setEditable(false);
		tabbedPane.add(panel5, "1, 3, center, default");
		// 打印标签
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.print.label2"), panel5);

		tabbedPane.add(panel, "1, 2, center, default");
		// 签收单标签打印
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.print.label1"), panel);
		
		
		/**
		 * 木包装打印
		 * @author 280747-FOSS-zhuxue
         * @date 2016-06-17 上午11:16:43
		 */
		JPanel panel22 = new JPanel();
		panel22.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(25dlu;default)"), 
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(76dlu;default)"), 
						FormFactory.RELATED_GAP_COLSPEC,ColumnSpec.decode("center:default"),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(74dlu;default)"), 
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(130dlu;default)"),},
						new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, 
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, 
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(22dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), 
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(140dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC }));

		// 运单号
		JLabel lblNewLabel2and2 = new JLabel(i18n.get("foss.gui.creating.printSignUI.waybillNo.label") + ":");
		panel22.add(lblNewLabel2and2, "2, 2, 2, 1, center, default");
		woodWaybillNo = new JTextField();
		panel22.add(woodWaybillNo, "4, 2, left, default");
		woodWaybillNo.setColumns(NumberConstants.NUMBER_10);
		woodWaybillNo.setDocument(new NumberDocument(NumberConstants.NUMBER_10));
		// 包装类型
		PackageType = new JLabel(i18n.get("foss.gui.creating.printSignUI.PackageType.label") + ":");
		panel22.add(PackageType, "2, 6, center, center");
		tPackageType = new JTextField();
		panel22.add(tPackageType, "4, 6, 2, 1, left, center");
		tPackageType.setColumns(NumberConstants.NUMBER_10);
		// 打木架件数
		standGoodsNum = new JLabel(i18n.get("foss.gui.creating.printSignUI.WoodNumber.label") + ":");
		panel22.add(standGoodsNum, "1, 3, 3, 3, left, default");
		tstandGoodsNum = new JTextField();
		panel22.add(tstandGoodsNum, "4, 4, left, center");
		tstandGoodsNum.setColumns(NumberConstants.NUMBER_10);
		tstandGoodsNum.setDocument(new NumberDocument(NumberConstants.NUMBER_10));
		// 打木箱件数
		boxGoodsNum = new JLabel(i18n.get("foss.gui.creating.printSignUI.Woodbox.label") + ":");
		panel22.add(boxGoodsNum, "6, 4, left, center");
		tboxGoodsNum = new JTextField();
		panel22.add(tboxGoodsNum, "8, 4, left, default");
		tboxGoodsNum.setColumns(NumberConstants.NUMBER_10);
		tboxGoodsNum.setDocument(new NumberDocument(NumberConstants.NUMBER_10));
		// 打托件数
		salverGoodsNum = new JLabel(i18n.get("foss.gui.creating.printSignUI.Support.label") + ":");
		panel22.add(salverGoodsNum, "9, 4, 2, 1, left, default");
		tsalverGoodsNum = new JTextField();
		panel22.add(tsalverGoodsNum, "10, 4, center, default");
		tsalverGoodsNum.setColumns(NumberConstants.NUMBER_10);
		tsalverGoodsNum.setDocument(new NumberDocument(NumberConstants.NUMBER_10));
		// 查询
		btnPrint5 = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		panel22.add(btnPrint5, "8, 2, 1, 2, default, center");
		// 显示德邦物流
		chckbxPrintWoodCheckBox = new JCheckBox(i18n.get("foss.gui.creating.printSignUI.allotype.isPrintLogo"));
		chckbxPrintWoodCheckBox.setSelected(true);
		panel22.add(chckbxPrintWoodCheckBox, "8, 6, 2, 2, right, default");
		// 打印 
		woodbtnPrint = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
		panel22.add(woodbtnPrint, "8, 8, default, center");
		// 打印设置
		woodbtnPrintConfigure = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
		panel22.add(woodbtnPrintConfigure, "10, 8");
		// 全选
	    allSelectCheckBox = new JCheckBox("全选");
	    panel22.add(allSelectCheckBox, "2, 7, 1, 2, fill, center");
	    AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);
		initTable();
		JScrollPane scrollPane = new JScrollPane();
		table.setVisibleRowCount(NumberConstants.NUMBER_100);
		table.setColumnMargin(2);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel22.add(scrollPane, "2, 9, 9, 4, fill, fill");
		
		tabbedPane.add(panel22, "1, 2, center, default");
		// 打木包装标签打印
		tabbedPane.addTab("打木包装标签打印", panel22);

		add(tabbedPane, "1, 2, 3, 2, fill, fill");
	}
	
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		printBinder = BindingFactory.getIntsance().moderateBind(StartSignLabelVo.class, this, new PrinSignDescriptor(), true);
		printBinder.addValidationListener(new WaybillValidationListner());
		printBinder.addBindingListener(new PrintSignBindingListener(this));
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
	}

	/**
	 * 
	 * @description：保存绑定对象
	 * @date 2012-7-19
	 * @author yangtong
	 */
	public void registToRespository() {
		bindersMap.put("printBinder", printBinder);
	}

	public HashMap<String, IBinder<StartSignLabelVo>> getBindersMap() {
		return (HashMap<String, IBinder<StartSignLabelVo>>) bindersMap;
	}

	// 窗口应用程序
	private IApplication application;

	/**
	 * getApplication
	 * 
	 * @return
	 */
	public IApplication getApplication() {
		return application;
	}

	/**
	 * setApplication
	 */
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	/**
	 * @return the waybillService
	 */
	public IWaybillService getWaybillService() {
		return waybillService;
	}

	/**
	 * @param waybillService
	 *            the waybillService to set
	 */
	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}

	/**
	 * @param bindersMap
	 *            the bindersMap to set
	 */
	public void setBindersMap(Map<String, IBinder<StartSignLabelVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}

	/**
	 * @return the printBinder
	 */
	public IBinder<StartSignLabelVo> getPrintBinder() {
		return printBinder;
	}

	/**
	 * @param printBinder
	 *            the printBinder to set
	 */
	public void setPrintBinder(IBinder<StartSignLabelVo> printBinder) {
		this.printBinder = printBinder;
	}

	/**
	 * @return the txtWaybillNo
	 */
	public JTextFieldValidate getTxtWaybillNo() {
		return txtWaybillNo;
	}

	/**
	 * @param txtWaybillNo
	 *            the txtWaybillNo to set
	 */
	public void setTxtWaybillNo(JTextFieldValidate txtWaybillNo) {
		this.txtWaybillNo = txtWaybillNo;
	}

	/**
	 * @return the txtStartOrg
	 */
	public JTextField getTxtStartOrg() {
		return txtStartOrg;
	}

	/**
	 * @param txtStartOrg
	 *            the txtStartOrg to set
	 */
	public void setTxtStartOrg(JTextField txtStartOrg) {
		this.txtStartOrg = txtStartOrg;
	}

	/**
	 * @return the txtReachOrg
	 */
	public JTextField getTxtReachOrg() {
		return txtReachOrg;
	}

	/**
	 * @param txtReachOrg
	 *            the txtReachOrg to set
	 */
	public void setTxtReachOrg(JTextField txtReachOrg) {
		this.txtReachOrg = txtReachOrg;
	}

	/**
	 * @return the txtDestnationOrg
	 */
	public JTextField getTxtDestnationOrg() {
		return txtDestnationOrg;
	}

	/**
	 * @param txtDestnationOrg
	 *            the txtDestnationOrg to set
	 */
	public void setTxtDestnationOrg(JTextField txtDestnationOrg) {
		this.txtDestnationOrg = txtDestnationOrg;
	}

	// 根据货物总件数生成流水号。
	private StringBuffer getPrintSerialnos(int totalGoodsQty) {
		StringBuffer serialnos = new StringBuffer();
		if (totalGoodsQty > 0) {
			for (int i = 1; i <= totalGoodsQty; i++) {
				serialnos.append(String.format(FORMATSTR, i)).append(COMMA);
			}
		}
		return serialnos;
	}
		
	/**
	 * 截取流水号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-23 下午2:59:40
	 */
	private String subStringAreaInfo(StringBuffer tempAreaInfo) {
		String resultString = "";
		if (tempAreaInfo.length() != 0) {
			resultString = tempAreaInfo.substring(0, tempAreaInfo.length() - 1);
		}
		return resultString;
	}

	/**
	 * 获得流水号 字符串
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-23 下午2:59:23
	 */
	public String getSerialNos(String waybillNo) {
		StringBuffer tempAreaInfo = new StringBuffer("");
		//如果运单为暂存则流水号
		if (WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)) {
			tempAreaInfo = getPrintSerialnos(Integer.parseInt(printLabelBean.getTotalPieces()));
		}else {
			if (CollectionUtils.isEmpty(labeledlist)) {
				if(Integer.parseInt(tGoodsNum.getText())>0){
					return getPrintSerialnos(Integer.parseInt(tGoodsNum.getText())).toString();
				}else{
					return "";
				}
			}
			for(LabeledGoodEntity label : labeledlist){
				tempAreaInfo.append(label.getSerialNo()).append(COMMA);
			}
		}
		return subStringAreaInfo(tempAreaInfo);
	}
	
	/**
	 * 全选
	 * @author 280747-FOSS-zhuxue
     * @date 2016-06-17 上午11:16:43
	 * 
	 */
	class AllListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			Object obj = e.getSource();
			if (obj.equals(allSelectCheckBox)) {
				if (allSelectCheckBox.isSelected()) {
					// /全选
					if (list != null) {
						int i = 0;
						for (JCheckBox c : list) {
							checkBoxColumn.setRow(i);
							c.setSelected(true);
							i++;
						}
					}
				} else {
					// 全不选
					if (list != null) {
						int i = 0;
						for (JCheckBox c : list) {
							checkBoxColumn.setRow(i);
							c.setSelected(false);
							i++;
						}
					}
				}

				if (table != null) {
					refreshTable(table);
				}
			}
		}
	}
	
	private void initTable() {
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		/**/
		/**
		 * 固定表格的宽度
		 */
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setPreferredWidth(NumberConstants.NUMBER_50);
			tc.setMaxWidth(NumberConstants.NUMBER_50);
		}
		// 刷新表格
		LinkWoodTableModePrint tableModel = new LinkWoodTableModePrint(getArray(list, 2));
		table.setModel(tableModel);
		refreshTable(table);
	}
	
	@SuppressWarnings({ "rawtypes"})
	public Object[][] getArray(List list, int flag) {
		if (list != null && !list.isEmpty()) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			// 根据集合中的工号查询对应的姓名
			//List<EmployeeEntity> empList = queryEmployeeByCode(list);
			for (int i = 0; i < list.size(); i++) {
				Object pending = (Object) list.get(i);
				/**
				 * KDTE-4624　客户端管理营业部出发运单界面查询报错
				 */
				q = getRowValue(pending,flag);

				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
				}
			}
			return objtemp;
		} else {
			return null;
		}
	}
	
	/**
	 * 刷新table 显示数据
	 * @author 280747-FOSS-zhuxue
     * @date 2016-06-17 上午11:16:43
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		LinkWoodTableModePrint.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}
	
	
	/**
	 * 获取显示数据
	 * @author 280747-FOSS-zhuxue
     * @date 2016-06-17 上午11:16:43
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object,int flag) {
		Object[] obj = {"",object};
		return obj;
	}

	/**
	 * <p>
	 * 获得在线打印bean信息
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-2 上午10:55:53
	 * @see
	 */
	private void getPrintBeanDetailInfoOnLine(String waybillNo) {
		//如果运单相同，不再查询数据库，较少交互
		if(printLabelBean != null){
			if(StringUtils.equals(printLabelBean.getWaybillNumber(), waybillNo)){
				return;
				//这边如果运单号不一致这个就需要清理了，不然真的还是打印之前的
			}else if(CollectionUtils.isNotEmpty(printLabelBeans)){
				printLabelBeans.clear();
				printLabelBean = null;
				initPanelData();
			}
		}
		waybillService = WaybillServiceFactory.getWaybillService();
		try{
			if(StringUtils.isEmpty(waybillNo)){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			//先查询货签信息表中数据
			labeledlist = waybillService.queryAllSerialByWaybillNo(waybillNo);
			if(labeledlist == null || labeledlist.size() == 0){
				// 离线或者暂存暂时打印
				printLabelBean = waybillService.getLabelPrintInfos(waybillNo,
						waybillStatus);
				// 如果没有就报错
				if (printLabelBean == null || StringUtils.isBlank(printLabelBean.getWaybillNumber())) {
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
					return;
				}
				//先判定是否对应的总件数<0
				int totalPieces = Integer.parseInt(printLabelBean.getTotalPieces());
				if(totalPieces < 0){
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				//离线或者暂存的还是拼装号对应的货签信息流水号吧
				LabeledGoodEntity labelGoodTemp = null;
				labeledlist = new ArrayList<LabeledGoodEntity>();
				for(int i=1;i<=totalPieces;i++){
					labelGoodTemp = new LabeledGoodEntity();
					labelGoodTemp.setWaybillNo(printLabelBean.getWaybillNumber());//运单号
					labelGoodTemp.setSerialNo(String.format(FORMATSTR, i));//对应的流水号
					labeledlist.add(labelGoodTemp);//添加到货签信息数据里面
				}
				printLabelBeans.add(printLabelBean);
			}else{
				// 这里肯定是存在货签信息的状态，这时需要判断是否进行过目的站更改
				// 得到流水号
				List<String> serialNos = new ArrayList<String>();
				for (int i = 0; i < labeledlist.size(); i++) {
					serialNos.add(labeledlist.get(i).getSerialNo());
				}

				// 运单信息
				WaybillEntity waybillEntity = waybillService
						.queryWaybillByNumber(labeledlist.get(0).getWaybillNo());
				if(CommonUtils.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
					JOptionPane
					.showMessageDialog(
							null,
							i18n.get("foss.gui.creating.printSignUI.msgbox.error.expressNo"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 判断是否存在更改单未受理情况
				String destinationAndTodo = waybillService
						.isExistDestinationAndTodoRfc(waybillEntity, serialNos);
				// destinationAndTodo为true表示所有都通过了，但是目的站可能未改变
				if ("WAY_UNDO".equals(destinationAndTodo)) {
					String xx = i18n
							.get("foss.gui.creating.printSignUI.msgbox.warming.tipbusiness");
					String xx1 = xx.replaceAll("XX", printLabelBeans.get(0)
							.getDestination());
					// 线提示用户存在更改情况
					JOptionPane
							.showMessageDialog(
									null,
									xx1,
									i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipnext"),
									JOptionPane.WARNING_MESSAGE);
					return;
				}else if("WAY_BSE".equals(destinationAndTodo)){
					//增加异常的处理，如果综合查询走货路径报错，走中转的走货路径,如果还报错，那我就无能为力了
					try{
						printLabelBean = waybillService.getLabelPrintInfos(waybillNo,
								waybillStatus);
						// 如果没有就报错
						if (printLabelBean == null) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
							return;
						}
						printLabelBeans.add(printLabelBean);
					}catch (Exception e) {
						printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
						// 如果没有就报错
						if (printLabelBeans == null) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
				}else{
					printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
					// 如果没有就报错
					if (printLabelBeans == null) {
						JOptionPane
								.showMessageDialog(
										null,
										i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
										JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				addNumListener();
				printLabelBean = printLabelBeans.get(0);
			}
			if (StringUtils.isEmpty(printLabelBean.getWaybillNumber())) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				 
				tBeginGoodNum.removeFocusListener(focusListenerBegin);
				tEndGoodNum.removeFocusListener(focusListenerEnd);
				return;
			} else {
				waybillStatus = printLabelBean.getWaybillStatus();
			}
			for(int q=0;q<printLabelBeans.size();q++){
				//如果是合伙人就调用综合的接口查询出对接外场
				String partner=waybillService.selectqueryWaybillForPrint(waybillNo);
				if("Y".equals(partner)){
					if(StringUtils.isEmpty(printLabelBeans.get(q).getCustomerPickupOrgCode())){
						JOptionPane.showMessageDialog(null,
								i18n.get("foss.waybill.eWaybillService.destinationIsNull"),
								i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					//对接外场
					String field=waybillService.selectqueryDeptDeptTransferForNameByCode(printLabelBeans.get(q).getCustomerPickupOrgCode());
					//如果有对接外场就添加进打印信息,如果对接外场为空就设置对接外场为空字符串（按照原有的货物标签打印）
					if(StringUtils.isBlank(field)){
						JOptionPane.showMessageDialog(null,
								i18n.get("foss.waybill.eWaybillService.partnerdestinationIsNull"),
								i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
								JOptionPane.WARNING_MESSAGE);
						printLabelBeans.get(q).setPartnerBillingLogo(" ");
					}else{
						printLabelBeans.get(q).setPartnerBillingLogo(field);
					}
				}else{
					printLabelBeans.get(q).setPartnerBillingLogo(" ");
				}
			}
			//如果是零担电子运单，并且运单状态为已撤销，那么则直接提示该零担电子运单已经撤销，不能打印标签
			if(FossConstants.YES.equals(printLabelBean.getIsElecLtlWaybill()) && WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(printLabelBean.getElecLtlWaybillStatus())){
				JOptionPane.showMessageDialog(null,
					i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.canNotPrint.LtlEWaybillCancaled"),
					i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
					JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			// 界面初始赋值
			// 开单人.
			tWaybillNo.setText(printLabelBean.getWaybillNumber());
			tReceiver.setText(printLabelBean.getReceiveCustomerContact());
			//添加大客户标记
			if(FossConstants.ACTIVE.equals(printLabelBean.getReceiveBigCustomer())|| FossConstants.ACTIVE.equals(printLabelBean.getDeliveryBigCustomer())){
				lblConsignee.setIcon(CommonUtils.createIcon(PrintSignUI.class, IconConstants.BIG_CUSTOMER, 1, 1));
			}else{
				lblConsignee.setIcon(CommonUtils.createIcon(PrintSignUI.class, "", 1, 1));
			}
			
			tTargetOrg.setText(printLabelBean.getDestination());
			tStartOrg.setText(printLabelBean.getLeavecity());
			tTransTpye.setText(printLabelBean.getTranstype());
			tIsElecLtlWayBill.setText(FossConstants.YES.equals(printLabelBean.getIsElecLtlWaybill())?"是":"否");

			tBeginGoodNum.setText(StringUtils.isEmpty(printLabelBean.getWaybillNumber()) ? "" : "1");
			//增加对货签
			//BUG-48626,增加对货签信息与运单件数的校验，如果运单件数与货签信息不一致，则用货签信息为准
			if(isOnOrOffLIne && Integer.parseInt(printLabelBean.getTotalPieces()) > labeledlist.size()){
				//如果货签信息件数=0，但是运单件数>0，这时候需要以运单件数为准，不然这样暂存运单无法打印
				if(labeledlist.size() == 0 && Integer.parseInt(printLabelBean.getTotalPieces())>=0){
					if(Integer.parseInt(printLabelBean.getTotalPieces())==0){
						tGoodsNum.setText(String.valueOf(labeledlist.size()));
						// 结束件数
						tEndGoodNum.setText(String.valueOf(labeledlist.size()));
					}else{
						tGoodsNum.setText(printLabelBean.getTotalPieces());
						// 结束件数
						tEndGoodNum.setText(printLabelBean.getTotalPieces());
					}
				}else{
					// 件数
					tGoodsNum.setText(String.valueOf(labeledlist.size()));
					// 结束件数
					tEndGoodNum.setText(String.valueOf(labeledlist.size()));
				}
			}else{
				tGoodsNum.setText(printLabelBean.getTotalPieces());
				tEndGoodNum.setText(printLabelBean.getTotalPieces());
			}			
			// 是否异形
			chckbxNewCheckBox.setSelected(i18n.get("foss.gui.creating.printSignUI.allotype.name").equals(printLabelBean.getUnusual()) ? true : false);
			// 文本区的赋值 补成4位的
			textArea.setText(StringUtils.isEmpty(printLabelBean.getWaybillNumber()) ? "" : getSerialNos(waybillNo));
		}catch (BusinessException w) {
			if (StringUtils.isNotEmpty(w.getMessage())) {
				MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
			}else if(StringUtils.isNotEmpty(w.getErrorCode())){
			    MsgBox.showInfo(w.getErrorCode());
			}
		}
		addNumListener();
	}

	/**
	 * 清除界面上所有信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-10-2 13:43:11
	 */
	private void initPanelData() {
		tWaybillNo.setText("");
		tReceiver.setText("");
		lblConsignee.setIcon(CommonUtils.createIcon(PrintSignUI.class, "", 1, 1));
		tTargetOrg.setText("");
		tStartOrg.setText("");
		tTransTpye.setText("");
		tIsElecLtlWayBill.setText("");
		tBeginGoodNum.setText("");
		tGoodsNum.setText("");
		tEndGoodNum.setText("");
		chckbxNewCheckBox.setSelected(false);
		textArea.setText("");
	}

	private Boolean validateNum() {		
		if (StringUtils.isEmpty(tBeginGoodNum.getText()) || Integer.parseInt(tBeginGoodNum.getText())<=0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.two"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(tEndGoodNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.three"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(tGoodsNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.four"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Integer.parseInt(tBeginGoodNum.getText()) > Integer.parseInt(tEndGoodNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.five"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			tBeginGoodNum.setText(String.valueOf(1));
			return false;
		} else if (Integer.parseInt(tEndGoodNum.getText()) > Integer.parseInt(tGoodsNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.six"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			tEndGoodNum.setText(tGoodsNum.getText());
			return false;
		} else {
			return true;
		}
	}

	private void addNumListener() {
		focusListenerBegin = new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//如果单号为空，什么都不做
				if(StringUtils.isEmpty(tWaybillNo.getText().trim())){
					return;
				}
				if (validateNum()) {
					StringBuffer tempAreaInfo = new StringBuffer("");
					//增加对数量的校验
					for (int i = Integer.parseInt(tBeginGoodNum.getText()) - 1; i < Integer.parseInt(tEndGoodNum.getText()); i++) {
						//删除运单为暂存状态的情况
						if (isOnOrOffLIne && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)) {
							// 在线操作
							// "%04d" means "%0" + 4 + "d"
							tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledlist.get(i).getSerialNo()))).append(COMMA);
						} else {
							// 离线操作
							tempAreaInfo.append(String.format(FORMATSTR, i + 1)).append(COMMA);
						}

					}
					textArea.setText(subStringAreaInfo(tempAreaInfo));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		};

		focusListenerEnd = new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//如果单号为空，什么都不做
				if(StringUtils.isEmpty(tWaybillNo.getText().trim())){
					return;
				}
				if (validateNum()) {
					// 文本区的赋值 补成4位的
					// 补打的情况
					StringBuffer tempAreaInfo = new StringBuffer("");
					for (int i = Integer.parseInt(tBeginGoodNum.getText()) - 1; i < Integer.parseInt(tEndGoodNum.getText()); i++) {
						//删除运单为暂存状态的情况
						if (isOnOrOffLIne && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)) {
							// 在线操作
							// "%04d" means "%0" + 4 + "d"
							tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledlist.get(i).getSerialNo()))).append(COMMA);
						} else {
							// 离线操作
							tempAreaInfo.append(String.format(FORMATSTR, i + 1)).append(COMMA);
						}

					}
					textArea.setText(subStringAreaInfo(tempAreaInfo));
				}

			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		};

		// 联动监听
		tBeginGoodNum.addFocusListener(focusListenerBegin);
		tEndGoodNum.addFocusListener(focusListenerEnd);
	}

	/**
	 * <p>
	 * 事件监听
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-29 上午10:46:46
	 * @see
	 */
	private void addListener() {
		// 打印按钮
		btnPrint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(printLabelBean == null){
					return;
				}
				//如果是零担电子运单，并且运单状态为已撤销，那么则直接提示该零担电子运单已经撤销，不能打印标签
				if(FossConstants.YES.equals(printLabelBean.getIsElecLtlWaybill()) && WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(printLabelBean.getElecLtlWaybillStatus())){
					JOptionPane.showMessageDialog(null,
						i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.canNotPrint.LtlEWaybillCancaled"),
						i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
						JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (StringUtils.isEmpty(tWaybillNo.getText()) || StringUtils.isEmpty(printLabelBean.getWaybillNumber())) {
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.canNotPrint.Exception"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					String uunsual = chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.printSignUI.allotype.name") : "";
					//获取所有的流水号
					String[] seriesNo = textArea.getText().split(COMMA);
					if(seriesNo.length == 0){
						//输入截止件数已获得流水号
						JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.endGoodNum.toolTipText"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
					
					//备注：当前只是针对走货路径是综合的设置的，因为综合没有返回流水号，需要自己设置
					if("".equals(printLabelBean.getPrintSerialnos()) || null == printLabelBean.getPrintSerialnos()){
						// 异常货物 标识 "异"
						printLabelBean.setUnusual(chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different") : "");
						//德邦Logo是否显示“德邦物流”
						printLabelBean.setIsPrintLogo(chckbxPrintCheckBox.isSelected() ? WaybillConstants.YES : WaybillConstants.NO);
						printLabelBean.setPrintSerialnos(textArea.getText());
						//设置异常货物标志
						printLabelBean.setUnusual(uunsual);
						PrintUtil printUtil = new PrintUtil();
						LabelPrintContext ctx = printUtil.printLabelData(printLabelBean);
						try {
							LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker, ctx);
							for (String  serieNo : seriesNo) {
								//记录打印日志
								GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
								printLabelEntity.setWaybillNo(tWaybillNo.getText());
								printLabelEntity.setSerialNo(serieNo);
								printLabelEntity.setPrintTime(new Date());
								printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
								printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
								printLabelEntity.setLableType(1);
								waybillService.addPrintLabelInfo(printLabelEntity);
							}
							//如果是拼箱营就继续打印
							WaybillEntity waybillEntity =new WaybillEntity();
							DispatchOrderChannelNumberEntity entity = new DispatchOrderChannelNumberEntity();
							//如果订单号不为空
							if(!"".equals(printLabelBean.getWaybillNumber()) && printLabelBean.getWaybillNumber() != null){
								//根据运单号查询订单号
								waybillEntity =waybillService.queryWaybillByNumber(printLabelBean.getWaybillNumber());
								if(waybillEntity != null){
									//根据订单号查询订单信息
									entity =waybillService.queryWaybillTypeEntityByOrderNo(waybillEntity.getOrderNo());
									//如果订单来源是拼箱营就打印此标签
									if(entity !=null){
										if(("PINXIANGYING").equals(entity.getOrderSource())){
											printLabelBean.setChannelNumber(entity.getChannelNumber());
											LabelPrintContext ctx1 = printUtil.printLabelData(printLabelBean);
											for (String  serieNo : seriesNo) {
												LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_PinxiangyingLabelPrintWorker,ctx1);
												//记录打印日志
												GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
												printLabelEntity.setWaybillNo(tWaybillNo.getText());
												printLabelEntity.setSerialNo(serieNo);
												printLabelEntity.setPrintTime(new Date());
												printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
												printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
												waybillService.addPrintLabelInfo(printLabelEntity);
											}
										}
									}
								}
							}
						} catch (PrintException e2) {
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						/**
						 * KDTE-4773标签打印异常
						 * 
						 * 打完一次后将该值清空，防止走else里的逻辑
						 */
						printLabelBean.setPrintSerialnos(null);
					}else{
						//根据流水号跟后台查出的数据进行比较，如果匹配正确，则进行打印
						BarcodePrintLabelDto bean = null;
						for(int i = 0; i<seriesNo.length;i++){
							for(int j = 0;j<printLabelBeans.size();j++){
								bean = printLabelBeans.get(j);
								if(seriesNo[i].equals(bean.getPrintSerialnos())){
									// 异常货物 标识 "异"
									bean.setUnusual(chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different") : "");
									//德邦Logo是否显示“德邦物流”
									bean.setIsPrintLogo(chckbxPrintCheckBox.isSelected() ? WaybillConstants.YES : WaybillConstants.NO);
									//设置单个流水号
									bean.setPrintSerialnos(seriesNo[i]);
									//设置异常货物标志
									bean.setUnusual(uunsual);
									PrintUtil printUtil = new PrintUtil();
									LabelPrintContext ctx = printUtil.printLabelData(bean);
									try {
										LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker, ctx);
										//记录打印日志
										GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
										printLabelEntity.setWaybillNo(tWaybillNo.getText());
										printLabelEntity.setSerialNo(seriesNo[i]);
										printLabelEntity.setPrintTime(new Date());
										printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
										printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
										waybillService.addPrintLabelInfo(printLabelEntity);
										//如果是拼箱营就继续打印
										WaybillEntity waybillEntity =new WaybillEntity();
										DispatchOrderChannelNumberEntity entity = new DispatchOrderChannelNumberEntity();
										if(!"".equals(printLabelBean.getWaybillNumber()) && printLabelBean.getWaybillNumber() != null){
											//根据运单号查询订单号
											waybillEntity =waybillService.queryWaybillByNumber(printLabelBean.getWaybillNumber());
											if(waybillEntity != null){
												//根据订单号查询订单信息
												entity =waybillService.queryWaybillTypeEntityByOrderNo(waybillEntity.getOrderNo());
												
											}
										}
										//如果订单来源是拼箱营就打印此标签
										if(entity!=null){
											if(("PINXIANGYING").equals(entity.getOrderSource())){
												printLabelBean.setChannelNumber(entity.getChannelNumber());
												LabelPrintContext ctx1 = printUtil.printLabelData(printLabelBean);
												LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_PinxiangyingLabelPrintWorker,ctx1);
												//记录打印日志
												GUIPrintLabelDto printLabelEntity1 = new GUIPrintLabelDto();
												printLabelEntity.setWaybillNo(tWaybillNo.getText());
												printLabelEntity.setSerialNo(seriesNo[i]);
												printLabelEntity.setPrintTime(new Date());
												printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
												printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
												waybillService.addPrintLabelInfo(printLabelEntity1);
											}
										}
									} catch (PrintException e2) {
										JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
										return;
									}
								}
							}
						}
					}
					MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
				} catch (Exception exp) {
					LOG.error("标签打印异常" + exp.toString(), exp);
					JOptionPane.showMessageDialog(null, exp.toString(), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		});
		// 打印设置
		btnPrintConfigure.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// print label setup
				try {
					PropertiesUtil.initProperties();
					LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
					setupwindow.openWindow();
				} catch (Exception exp) {

					LOG.error("打印设置异常:" + exp.toString(), exp);
				}
			}
		});
		/**
		 * 打木标签打印设置监听
		 */
		// 打印设置
		woodbtnPrintConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// print label setup
				try {
					PropertiesUtil.initProperties();
					LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
					setupwindow.openWindow();
				} catch (Exception exp) {
					LOG.error("打印设置异常:" + exp.toString(), exp);
				}
			}
		});
	}

}