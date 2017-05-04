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
package com.deppon.foss.module.pickup.creatingexp.client.ui;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintException;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument.Content;

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
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI;
import com.deppon.foss.module.pickup.creating.client.vo.StartSignLabelVo;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpLabelBatchprintingpiAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpLabelPrintResetAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpLabelPrintSearchAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpPrintSignAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpPrintSignSetupAction;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpPrintSignBindingListener;
import com.deppon.foss.module.pickup.creatingexp.client.validation.descriptor.ExpPrinSignDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
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
public class ExpPrintSignUI extends JPanel implements IApplicationAware {
	private static final long serialVersionUID = 1L;
	private static final String SEARCH16GIF = "Search16.gif";

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpPrintSignUI.class);
	
	/**
	 * 批量打印快递标签
	 * @author 280747-FOSS-祝学
     * @date 2015-12-17 上午11:16:43
	 */
	// 打印
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = ExpLabelBatchprintingpiAction.class)
	JButton btnPrint1 = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));

	// 打印设置
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = ExpPrintSignSetupAction.class)
	JButton btnPrintConfigure1 = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
	
	//查询按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpLabelPrintSearchAction.class)
	private JButton btnPrint5 ;

	//重置按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpLabelPrintResetAction.class)
	private JButton btnPrint6;
	// 提交时间
	@Bind(value = "zdStartDate", property = DATE)
	//@BindingArgs({ @BindingArg(name = FIELDNAME, value = "提交时间") })
	JXDateTimePicker zdStartDate;
	
	public JXDateTimePicker getZdStartDate() {
		return zdStartDate;
	}

	public void setZdStartDate(JXDateTimePicker zdStartDate) {
		this.zdStartDate = zdStartDate;
	}

	public JXDateTimePicker getZdEndDate() {
		return zdEndDate;
	}

	public void setZdEndDate(JXDateTimePicker zdEndDate) {
		this.zdEndDate = zdEndDate;
	}

	@Bind(value = "zdEndDate", property = DATE)
	JXDateTimePicker zdEndDate;
	      
	private static final String DATE = "date";
	
	private JXTable table = new JXTable();
	// 全选
	private JCheckBox allSelectCheckBox;
	public JXTable getTable() {
		return table;
	}

	public void setTable(JXTable table) {
		this.table = table;
	}

	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;
	/**
	 * 选中的列
	 */
	private ExpPrintSignCheckBoxColumn checkBoxColumn;

	/**
	 * 选中的导出运单号
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

	public ExpPrintSignCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public void setCheckBoxColumn(ExpPrintSignCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}
	@FocusSeq(seq=1)
	private JTextField createUserCode;

	public JTextField getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(JTextField createUserCode) {
		this.createUserCode = createUserCode;
	}

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
	
	

	public JTextField gettWaybillNo() {
		return tWaybillNo;
	}

	public void settWaybillNo(JTextField tWaybillNo) {
		this.tWaybillNo = tWaybillNo;
	}

	private JTextField tReceiver;

	private JTextField tGoodsNum;

	private JTextField tTargetOrg;

	private JTextField tStartOrg;

	private JTextField tTransTpye;
	@FocusSeq(seq=2)
	private JTextField tBeginGoodNum;
	@FocusSeq(seq=3)
	private JTextField tEndGoodNum;

	// 是否异形货物
	private JCheckBox chckbxNewCheckBox;

	// 文本区 流水号
	private JTextArea textArea;

	// 打印设置
	@FocusSeq(seq=4)
	private JButton btnPrintConfigure;

	// 打印
	@FocusSeq(seq=5)
	private JButton btnPrint;

	private static final String COMMA = ",";

	private static final String FORMATSTR = "%04d";

	// 判断在线还是离线 true 在线 ，false 离线
	private Boolean isOnOrOffLIne;

	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();

	//存储根据运单号所查询
	private List<BarcodePrintLabelDto> printLabelBeans = new ArrayList<BarcodePrintLabelDto>();
	
	private List<EWaybillPrintDto> printEWaybillBeans = new ArrayList<EWaybillPrintDto>();
	//转寄退回件标签的实体
	private BarcodePrintLabelDto printBWaybillBean = new BarcodePrintLabelDto();
	
	private List<BarcodePrintLabelDto> printBWaybillBeans=new ArrayList<BarcodePrintLabelDto>();
	

	public BarcodePrintLabelDto getPrintBWaybillBean() {
		return printBWaybillBean;
	}

	public void setPrintBWaybillBean(BarcodePrintLabelDto printBWaybillBean) {
		this.printBWaybillBean = printBWaybillBean;
	}
	// 标签信息
	private List<LabeledGoodEntity> labeledlist;
	
	// 转寄退回件标签信息
	private List<LabeledGoodEntity> labeledForward;

	// 通过工厂类获得运单服务类
	private transient IWaybillService waybillService;
	
	private transient IOrgInfoService  orgInfoService;
	// 绑定接口
	private IBinder<StartSignLabelVo> printBinder;

	// 日志
	private static final Log LOG = LogFactory.getLog(ExpPrintSignUI.class);

	private static final String PREVIEWPNG = "preview.png";
	
	private static final int TEN = 10;
	
	private static final int SIX = 6;
	
	private static final int NUM_662 = 662;

	private static final int NUM_110 = 110;
	
	private static final int NUM_340 = 340;

	private Map<String, IBinder<StartSignLabelVo>> bindersMap = new HashMap<String, IBinder<StartSignLabelVo>>();

	// 打印
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = ExpPrintSignAction.class)
	JButton printButton = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));

	// 打印设置
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = ExpPrintSignSetupAction.class)
	JButton printViewButton = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));

//快递标签的
	private FocusListener focusListenerBegin;

	private FocusListener focusListenerEnd;
	
//电子运单的
	private FocusListener focusSerialListenerBegin;
	
	private FocusListener focusSerialListenerEnd;
	
private FocusListener focusBSerialBeigin;
	
	private FocusListener focusBSerialEnd;
	//以下是电子运单的控件
	//电子运单号
	private JTextField eWaybillNo;
	//开始流水号
	private JTextField txtSerialBeigin;
	//截止流水号
	private JTextField txtSerialEnd;
	//件数
	private JTextField txtPieces;
	//收货人
	private JTextField txtReceiver;
	//流水号
	private JTextArea textSerialNos;
	
	private List<String> serialNoList = new ArrayList<String>();
	// 打印设置
	private JButton btnEWaybillSet;
	// 打印
	private JButton btnEWaybillPrint; 
	//运单状态
	private String waybillStatus;
	
	//单选按钮
	JRadioButton rdbtnOld;
	//单选按钮
	JRadioButton rdbtnNew;
	JRadioButton rdbtnInside;
	JRadioButton rdbtnThree;
	JRadioButton rdbtnWareHouse;

	/**
	 * 转寄退回件
	 * 
	 */
	//运单号
	private JTextField bWaybillNo;
	//收货人
	private JTextField bReceiver;
	 //目的站  
	private JTextField bTargetOrg;
	//保价金额
	private JTextField insuredAmount;
	//代收货款
	private JTextField collectionOnDelivery;
	//到付款合计
	private JTextField totalPayment;
	// 打印设置
	private JButton printInstall;
	// 打印
	private JButton print; 
	//开始流水号
	private JTextField bSerialBeigin;
	//截止流水号
	private JTextField bSerialEnd;
	//流水号
	private JTextArea bSerialNos;
	public ExpPrintSignUI() {
		// 初始化界面
		init();
		// 初始化是否在线
		initIsOnOrOffLIne();
		// 初始化事件监听
		initListener();
		//事件监听
		addListener();
		bind();
		//保存绑定对象
		registToRespository();	
		
		ExpEnterKeySalesDept enterTable = new ExpEnterKeySalesDept(this);
		table.addKeyListener(enterTable);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
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

	/**
	 * 初始化监听
	 */
	private void initListener() {
		tWaybillNo.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				// 失去焦点时，可以认为输入结束
				if (isOnOrOffLIne) {
					if (StringUtils.isNotBlank(tWaybillNo.getText())) {
						// 不为空 就是标签打印 否则就应该是重打 进行界面初始赋值 因为标签打印会传来流水号
						// 在线操作
						getPrintBeanDetailInfoOnLine(tWaybillNo.getText().trim());
					}
				} else {
					// 离线操作
					if (StringUtils.isNotEmpty(tWaybillNo.getText())) {
						// 不为空 就是标签打印 否则就应该是重打 进行界面初始赋值 因为标签打印会传来流水号
						// 在线操作
						getPrintBeanDetailInfoOnLine(tWaybillNo.getText().trim());
					}
				}
			}
		});
		eWaybillNo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getEWaybillPrintDetailInfoOnline(eWaybillNo.getText().trim());
			}
		});
		//转寄退回件输入运单号后 焦点失去
		bWaybillNo.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent e){
				getBWaybillPrintDetailInfoOnline(bWaybillNo.getText().trim());
			}
		});
	}
	
	/**
	 * 打印电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-29 19:34:00
	 * @param waybillNo
	 */
	private void getEWaybillPrintDetailInfoOnline(String waybillNo) {
		if(StringUtils.isNotBlank(waybillNo)){
			//必须判断是否件数>0
			if(printEWaybillBeans != null && printEWaybillBeans.size()>0){
				if(eWaybillNo.getText().trim().equals(printEWaybillBeans.get(0).getWaybillNo())){
					return;
				}else{
					printEWaybillBeans.clear();
					serialNoList.clear();
					initEWaybillData();
				}
			}
			waybillService = WaybillServiceFactory.getWaybillService();
			try {
				printEWaybillBeans = waybillService.printEWaybillInfos(waybillNo, null);
			} catch (BusinessException e) {
				LOG.info(e.getMessage());
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), 
						e.getMessage(), JOptionPane.WARNING_MESSAGE);
				eWaybillNo.setText("");
				eWaybillNo.requestFocus();
				return;
			}
			
			//如果数据存在问题，需要这样弄
			if(printEWaybillBeans == null || printEWaybillBeans.size()==0){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), 
						i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			//进行控件的赋值
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<printEWaybillBeans.size();i++){
				serialNoList.add(printEWaybillBeans.get(i).getPrintSerialNos());
				if(i == printEWaybillBeans.size()-1){
					sb.append(printEWaybillBeans.get(i).getPrintSerialNos());
				}else{
					sb.append(printEWaybillBeans.get(i).getPrintSerialNos()).append(COMMA);
				}
			}
			//运单号
			eWaybillNo.setText(printEWaybillBeans.get(0).getWaybillNo());
			//开始流水号
			txtSerialBeigin.setText(printEWaybillBeans.get(0).getPrintSerialNos());
			//结束流水号
			txtSerialEnd.setText(printEWaybillBeans.get(printEWaybillBeans.size()-1).getPrintSerialNos());
			//总件数
			txtPieces.setText(String.valueOf(printEWaybillBeans.get(0).getGoodsQtyTotal()));
			txtPieces.setEditable(false);
			//收货人
			txtReceiver.setText(printEWaybillBeans.get(0).getReceiveCustomerContact());
			txtReceiver.setEditable(false);
			if(serialNoList.size()>1){
				Collections.sort(serialNoList);
				txtSerialBeigin.setEditable(true);
				txtSerialEnd.setEditable(true);
			}else{
				txtSerialBeigin.setEditable(false);
				txtSerialEnd.setEditable(false);
			}
			//流水号集合
			textSerialNos.setText(sb.toString());
			addNumListener();
		}
	}
	
	/**
	 * 初始化转寄退回件数据	//运单号
	private JTextField bWaybillNo;
	//收货人
	private JTextField bReceiver;
	 //目的站  
	private JTextField bTargetOrg;
	//保价金额
	private JTextField insuredAmount;
	//代收货款
	private JTextField collectionOnDelivery;
	//到付款合计
	private JTextField totalPayment;
	// 打印设置
	private JButton printInstall;
	// 打印
	private JButton print; 
	//开始流水号
	private JTextField bSerialBeigin;
	//截止流水号
	private JTextField bSerialEnd;
	//流水号
	private JTextArea bSerialNos;
	 */
	private void initBWaybillData() {
		bWaybillNo.setText("");
		bReceiver.setText("");
		bTargetOrg.setText("");
		insuredAmount.setText("");
		collectionOnDelivery.setText("");
		totalPayment.setText("");
		bSerialBeigin.setText("");
		bSerialEnd.setText("");
		bSerialNos.setText("");
	}
	/**
	 * 初始化电子运单数据
	 */
	private void initEWaybillData() {
		//电子运单集合数据
		eWaybillNo.setText("");
		//开始流水号
		txtSerialBeigin.setText("");
		//结束流水号
		txtSerialEnd.setText("");
		//总件数
		txtPieces.setText("");
		//收货人
		txtReceiver.setText("");
		//流水号集合
		textSerialNos.setText("");
	}

	/**
	 * 初始化当前面板
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-1 18:39:21
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("center:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("max(169dlu;default)"),
				RowSpec.decode("fill:max(147dlu;default)"), }));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panel = new JPanel();
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
		JLabel label_1 = new JLabel(i18n.get("foss.gui.creating.printSignUI.startOrg.label") + ":");
		panel.add(label_1, "3, 5, right, default");

		txtStartOrg = new JTextField();
		panel.add(txtStartOrg, "5, 5, 5, 1, fill, default");
		txtStartOrg.setColumns(TEN);
		txtStartOrg.setEditable(false);

		// 到达部门
		JLabel label_2 = new JLabel(i18n.get("foss.gui.creating.printSignUI.reachOrg.label") + ":");
		panel.add(label_2, "3, 7, right, default");

		txtReachOrg = new JTextField();
		panel.add(txtReachOrg, "5, 7, 5, 1, fill, default");
		txtReachOrg.setColumns(TEN);
		txtReachOrg.setEditable(false);

		// 目的站
		JLabel label_3 = new JLabel(i18n.get("foss.gui.creating.printSignUI.destnationOrg.label") + ":");
		panel.add(label_3, "3, 9, right, default");

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
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(22dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(70dlu;default)"), }));

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
		//按照水平来布局
		GroupLayout groupLayout = new GroupLayout(panel5);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGap(SIX).addComponent(panel2, GroupLayout.DEFAULT_SIZE, NUM_662, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(17)));

		// 最终打印流水号
		JLabel lblNewLabel10 = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.label") + ":");
		panel2.add(lblNewLabel10, "2, 8, 3, 1");

		textArea = new JTextArea();
		textArea.setRows(SIX);
		textArea.setLineWrap(true);
		textArea.setSize(NUM_340, NUM_110);
		JScrollPane jScrollPane = new JScrollPane(textArea);
		panel2.add(jScrollPane, "2, 10, 7, 1");

		JPanel panel4 = new JPanel();
		panel2.add(panel4, "9, 10, 2, 1, fill, fill");
		panel4.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(41dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
				
		// 打印设置
		btnPrintConfigure = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
		panel4.add(btnPrintConfigure, "2, 8");

		// 打印
		btnPrint = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
		panel4.add(btnPrint, "2, 10");

		panel5.setLayout(groupLayout);
		tGoodsNum.setEditable(false);
		tTargetOrg.setEditable(false);
		tStartOrg.setEditable(false);
		tTransTpye.setEditable(false);
		textArea.setEditable(false);
		tabbedPane.add(panel5, "1, 3, center, default");
		// 打印标签
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.print.label2"), panel5);
		tabbedPane.add(panel, "1, 2, center, default");
		// 签收单标签打印
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.print.label1"), panel);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				RowSpec.decode("max(66dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblEWaybillNo = new JLabel(i18n.get("foss.gui.creating.numberPanel.waybillNo.label"));
		panel_1.add(lblEWaybillNo, "2, 2, right, default");
		
		eWaybillNo = new JTextField();
		panel_1.add(eWaybillNo, "3, 2, fill, default");
		eWaybillNo.setColumns(TEN);
		
		JLabel lblPiece = new JLabel(i18n.get("foss.gui.creating.printSignUI.receiver.label"));
		panel.add(lblPiece, "2, 4, right, default");
		
		JLabel label_4 = new JLabel(i18n.get("foss.gui.creating.printSignUI.goodNum.label"));
		panel_1.add(label_4, "2, 4, right, default");
		
		txtPieces = new JTextField();
		panel_1.add(txtPieces, "3, 4, fill, default");
		txtPieces.setColumns(TEN);
		
		JLabel lblReceiver = new JLabel(i18n.get("foss.gui.creating.printSignUI.receiver.label"));
		panel_1.add(lblReceiver, "2, 6, right, default");
		
		txtReceiver = new JTextField();
		panel_1.add(txtReceiver, "3, 6, fill, default");
		txtReceiver.setColumns(TEN);
		
		JLabel lblSerialBegin = new JLabel(i18n.get("foss.gui.creating.printSignUI.beginGoodNum.label"));
		panel_1.add(lblSerialBegin, "2, 8, right, default");
		
		txtSerialBeigin = new JTextField();
		panel_1.add(txtSerialBeigin, "3, 8, fill, default");
		txtSerialBeigin.setColumns(TEN);
		
		JLabel lblSlash = new JLabel(i18n.get("foss.gui.creating.printSignUI.endGoodNum.label"));
		panel_1.add(lblSlash, "2, 10, right, default");
		
		txtSerialEnd = new JTextField();
		panel_1.add(txtSerialEnd, "3, 10, fill, default");
		txtSerialEnd.setColumns(TEN);
		
		JLabel label_5 = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.label"));
		panel_1.add(label_5, "2, 12, right, default");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "3, 12, fill, fill");
		
		textSerialNos = new JTextArea();
		scrollPane.setViewportView(textSerialNos);
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.printSerial.eWaybillSeperate"),panel_1);
		textSerialNos.setEditable(false);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "3, 13, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		ButtonGroup group = new ButtonGroup ();
		rdbtnOld = new JRadioButton(i18n.get("foss.gui.creating.ExpPrintSignUI.radioButton.oldVersion"));
		rdbtnNew = new JRadioButton(i18n.get("foss.gui.creating.ExpPrintSignUI.radioButton.newVersion"));
		rdbtnInside = new JRadioButton("内部使用模版(10*18)");
		rdbtnThree = new JRadioButton("三联模版(10*18)");
		rdbtnWareHouse = new JRadioButton("仓库分拣模版(10*15)");
		panel_2.add(rdbtnOld, "2, 2");
		panel_2.add(rdbtnNew, "4, 2");
		panel_2.add(rdbtnInside, "2, 4");
		panel_2.add(rdbtnThree, "4, 4");
		panel_2.add(rdbtnWareHouse, "2, 6");
		rdbtnThree.setSelected(true);
		group.add(rdbtnOld);
		group.add(rdbtnNew);
		group.add(rdbtnInside);
		group.add(rdbtnThree);
		group.add(rdbtnWareHouse);
		btnEWaybillPrint = new JButton(i18n.get("foss.gui.creating.barcodePrintDialog.print.buttonText"));
		panel_2.add(btnEWaybillPrint, "6, 2");
		
		btnEWaybillSet = new JButton(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printSet"));
		panel_2.add(btnEWaybillSet, "8, 2");
		/**
		 * 转寄退回件标签打印
		 */
		JPanel panel6= getPanel6(); 
		tabbedPane.add(panel6, "1, 3, center, default");
		
		// 转寄退回件
		tabbedPane.addTab(i18n.get("foss.gui.creating.printSignUI.forwardReturn.label"), panel6);

		/**
		 * 批量打印
		 * @author 280747-FOSS-祝学
         * @date 2015-12-17 上午11:16:43
		 */
		JPanel panel1 = new JPanel();
		FormLayout flpanel2 = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(25dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(76dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(74dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(224dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(126dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:max(22dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(13dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(253dlu;default)"),});
						panel1.setLayout(flpanel2);

		// 快递员工号
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.printSignUI.waybillNo.labe2") + ":");
		panel1.add(lblNewLabel1, "2, 2, right, default");

		createUserCode = new JTextField();
		panel1.add(createUserCode, "4, 2, fill, default");
		createUserCode.setColumns(TEN);
		createUserCode.setDocument(new NumberDocument(TEN));
		//查询
		btnPrint5 = new JButton(i18n.get("foss.gui.creating.queryPublishPriceUI.queryCondition.label2"));
		panel1.add(btnPrint5, "10, 2");
		//开单时间
	    JLabel label_6 = new JLabel(i18n.get("foss.gui.creating.queryPublishPriceUI.queryCondition.label3"));
		panel1.add(label_6, "2, 4");
		// FOSS提交时间
		zdStartDate = new JXDateTimePicker();
		panel1.add(getStartFormatTime(zdStartDate), "4, 4, 2, 1, fill, fill");
		
		JLabel label_7 = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.labe4"));
		panel1.add(label_7, "6, 4");

		//结束时间
		zdEndDate = new JXDateTimePicker();
		panel1.add(getEndFormatTime(zdEndDate), "8, 4, 2, 1, fill, default");
		
						
		btnPrint6 = new JButton(i18n.get("foss.gui.creating.queryPublishPriceUI.queryCondition.label5"));
		btnPrint6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel1.add(btnPrint6, "10, 4");

		// 打印
		btnPrint1 = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
		panel1.add(btnPrint1, "2, 6");

		btnPrintConfigure1 = new JButton(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printSet"));
		btnPrintConfigure1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel1.add(btnPrintConfigure1, "4, 6");
		JLabel label_77 = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.labell"));
		panel1.add(label_77, "4, 8");
		// 全选
	    allSelectCheckBox = new JCheckBox(i18n.get("foss.gui.creating.printSignUI.allotype.labe2"));
	    panel1.add(allSelectCheckBox, "2, 8, fill, top");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);
		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel1.add(scrollPane, "1, 10, 14, 1, fill, fill");
		tabbedPane.addTab(i18n.get("foss.gui.creating.buttonPanel.printLabel.labell"), panel1);

		tabbedPane.add(panel1, i18n.get("foss.gui.creating.buttonPanel.printLabel.labell"));
		
		add(tabbedPane, "1, 2, 3, 1, fill, fill");
		
	}
	
	/**
	 * 转寄退回件标签打印
	 * //运单号
		//运单号
//运单号
	private JTextField bWaybillNo;
	//收货人
	private JTextField bReceiver;
	 //目的站  
	private JTextField bTargetOrg;
	//保价金额
	private JTextField insuredAmount;
	//代收货款
	private JTextField collectionOnDelivery;
	//到付款合计
	private JTextField totalPayment;
	// 打印设置
	private JButton printInstall;
	// 打印
	private JButton print; 
	//开始流水号
	private JTextField bSerialBeigin;
	//截止流水号
	private JTextField bSerialEnd;
	//流水号
	private JTextArea bSerialNos;
	 
	 */
	private JPanel getPanel6(){
		JPanel panel6 = new JPanel();
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(25dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(76dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("center:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(74dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(84dlu;default)"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(22dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(70dlu;default)"), }));




		// 运单号
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.waybillNo.label") + ":");
		panel2.add(lblNewLabel, "2, 2, right, default");

		bWaybillNo = new JTextField();
		panel2.add(bWaybillNo, "4, 2, fill, default");
		bWaybillNo.setColumns(10);
		bWaybillNo.setDocument(new NumberDocument(10));

		// 保价金额
		JLabel insuredAmountLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.insuredAmount.label") + ":");
		panel2.add(insuredAmountLabel, "6, 2, right, default");

		insuredAmount = new JTextField();
		panel2.add(insuredAmount, "8, 2, fill, default");
		insuredAmount.setColumns(10);
		insuredAmount.setEditable(false);

		JPanel panel3 = new JPanel();
		panel2.add(panel3, "10, 1, 1, 6, default, top");
		panel3.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(64dlu;default):grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), }));

		// 起始件数库号
		JLabel bSerialBeiginLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.beginGoodNum.label"));
		panel3.add(bSerialBeiginLabel, "2, 2");

		// 截止件数库号
		JLabel bSerialEndLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.endGoodNum.label"));
		panel3.add(bSerialEndLabel, "8, 2");

		bSerialBeigin = new JTextField();
		panel3.add(bSerialBeigin, "2, 4, fill, default");
		bSerialBeigin.setColumns(10);

		// 输入起始件数回车获得对应流水号
		bSerialBeigin.setToolTipText(i18n.get("foss.gui.creating.printSignUI.beginGoodNum.notice"));
		bSerialBeigin.setDocument(new NumberDocument(6));

		bSerialEnd = new JTextField();
		panel3.add(bSerialEnd, "8, 4, fill, default");
		bSerialEnd.setColumns(10);

		// 输入截止件数回车获得对应流水号
		bSerialEnd.setToolTipText(i18n.get("foss.gui.creating.printSignUI.endGoodNum.notice"));
		bSerialEnd.setDocument(new NumberDocument(6));

		// 目的站
		JLabel bTargetOrgLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.destnationOrg.label") + ":");
		panel2.add(bTargetOrgLabel, "2, 4, right, default");

		bTargetOrg = new JTextField();
		panel2.add(bTargetOrg, "4, 4, fill, default");
		bTargetOrg.setColumns(10);
		bTargetOrg.setEditable(false);

		// 代收货款
		JLabel collectionOnDeliveryLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.collectionOnDelivery.label") + ":");
		panel2.add(collectionOnDeliveryLabel, "6, 4, right, default");

		collectionOnDelivery = new JTextField();
		panel2.add(collectionOnDelivery, "8, 4, fill, default");
		collectionOnDelivery.setColumns(10);
		collectionOnDelivery.setEditable(false);

		// 收货人
		JLabel bReceiverLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.receiver.label") + ":");
		panel2.add(bReceiverLabel, "2, 6, right, default");

		bReceiver = new JTextField();
		panel2.add(bReceiver, "4, 6, fill, default");
		bReceiver.setColumns(10);
		bReceiver.setEditable(false);
		//到付款合计
		JLabel totalPaymentLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.totalPayment.label") + ":");
		panel2.add(totalPaymentLabel, "6, 6, right, default");

		totalPayment = new JTextField();
		panel2.add(totalPayment, "8, 6, fill, default");
		totalPayment.setColumns(10);
		totalPayment.setEditable(false);
		//按照水平来布局
		GroupLayout groupLayout = new GroupLayout(panel6);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGap(6).addComponent(panel2, GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(17)));

		// 最终打印流水号
		JLabel bSerialNosLabel = new JLabel(i18n.get("foss.gui.creating.printSignUI.printSerial.label") + ":");
		panel2.add(bSerialNosLabel, "2, 8, 3, 1");

		bSerialNos = new JTextArea();
		bSerialNos.setRows(6);
		bSerialNos.setLineWrap(true);
		bSerialNos.setSize(340, 110);
		bSerialNos.setEditable(false);
		JScrollPane jScrollPane = new JScrollPane(bSerialNos);
		panel2.add(jScrollPane, "2, 10, 7, 1");
		
		JPanel panel4 = new JPanel();
		panel2.add(panel4, "9, 10, 2, 1, fill, fill");
		panel4.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(41dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
				
		// 打印设置
		printInstall = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
		panel4.add(printInstall, "2, 8");

		// 打印
		print = new JButton(i18n.get("foss.gui.creating.printSignUI.button.print"));
		panel4.add(print, "2, 10");

		panel6.setLayout(groupLayout);
		
		return panel6;
	} 
	
	/**
	 * 全选
	 * @author 280747-FOSS-祝学
     * @date 2015-12-17 上午11:16:43
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
	};
	
	/**
	 * 刷新table 显示数据
	 * @author 280747-FOSS-祝学
     * @date 2015-12-17 上午11:16:43
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		ExpLinkTableModePrint.adjustColumnPreferredWidths(table);
		table.setSortable(true);
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
			tc.setPreferredWidth(50);
			tc.setMaxWidth(50);
		}

		// 刷新表格
		ExpLinkTableModePrint tableModel = new ExpLinkTableModePrint(getArray(list, 2));
		table.setModel(tableModel);
		refreshTable(table);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
				q = getRowValue(pending, flag);

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
	 * 获取显示数据
	 * @author 280747-FOSS-祝学
     * @date 2015-12-17 上午11:16:43
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object, int flag) {
		// 运单号
		String waybillNo = "";
		//　开单人名
		String createUserName = "";
		// 开单人
		String createUserCode = "";
		// 收货市
		String deliveryCustomerCityCode = "";
		//收货件数
		String goodsQtyTotal="";
		//始发站
		String startName="";
		//目的站营业部
		String targetOrgName="";
		//运输性质
		String productCode="";
		if (object instanceof WaybillEntity) {
			// 待补录运单对象
			WaybillEntity pending = (WaybillEntity) object;
			
			// 运单号
			waybillNo = StringUtil.defaultIfNull(pending.getWaybillNo());
			// 开单人
			createUserCode = StringUtil.defaultIfNull(pending.getCreateUserCode());
			// 收货市
			deliveryCustomerCityCode = CommonUtils.getDestCityNameByCode(pending.getDeliveryCustomerCityCode());
			
			//　开单人名
			createUserName = getEmployeeName(createUserCode);
			
			//目的站营业部
			targetOrgName=StringUtil.defaultIfNull(pending.getTargetOrgCode());
			
			
			orgInfoService = WaybillServiceFactory.getOrgInfoService();
			
			OrgAdministrativeInfoEntity org = orgInfoService.queryByCode(pending.getCreateOrgCode());
			if (org != null) {
/*				//经济快递
				if(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillBean.getProductCode())){
					//若为经济快递则不用设置，因为在设置第二外场城市时已经设置了
				}else{
					// 设置 始发站 为 收货部门城市名称
					log.info(waybillNo+"标签打印始发站 :"+org.getCityName());*/
				//始发站
				startName=StringUtil.defaultIfNull(org.getCityName());
				
					//printLabelBean.setLeavecity(org.getCityName());
				//}
			}
			
			//始发站
			//startName=StringUtil.defaultIfNull(pending.getDeliveryCustomerCityCode());
			
			//运输性质
			productCode = CommonUtils.getNameByProductCode(pending.getProductCode());
			if(StringUtils.isEmpty(productCode)){
				ProductEntity productEntity = waybillService.getProductByCache(pending.getProductCode(), pending.getBillTime());
				if(productEntity == null){
					if(WaybillConstants.PACKAGE.equals(pending.getProductCode())){
						productCode = i18n.get("foss.gui.creating.salesDeptEWaybillUI.StandardExpress");
			}
			//新增快递产品 3.60 特惠件
			if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(pending.getProductCode())){
				productCode = i18n.get("foss.gui.creating.salesDeptEWaybillUI.360ex");
					}
			//电商尊享
			if(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(pending.getProductCode())){
				productCode = i18n.get("foss.gui.creating.salesDeptEWaybillUI.Electricitysuppliersenjoy");
			}
			//商务专递
			if(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(pending.getProductCode())){
				productCode = i18n.get("foss.gui.creating.salesDeptEWaybillUI.Businessmail");
			}
				}else{
					productCode = productEntity.getName();
				}
			}
			
			// 货物总件数
		 goodsQtyTotal = CommonUtils.defaultIfNull(pending.getGoodsQtyTotal()).toString();
		}
		// 获取对象成员保存至一维数组
		Object[] obj = { "",  waybillNo, createUserName,startName,targetOrgName,productCode,goodsQtyTotal};
		return obj;
	}
	

	/**
	 * 根据工号获得名称
	 * @author 280747-foss-zhuxue
	 * @date 2015-12-17 下午9:17:24
	 */
	public String getEmployeeName(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}else{
			if(CommonUtils.isOnline()){
				waybillService = WaybillServiceFactory.getWaybillService();
				EmployeeEntity entity = waybillService.queryEmployeeByEmpCode(code);
				if(null != entity){
					return StringUtils.defaultString(entity.getEmpName());
				}else{
					return code;
				}
			}else{
				return CommonUtils.getUserNameFromUserService(code);
			}
		}
		
	}
	
	/**
	 * 获得格式化后的开始日期
	 * @author 280747-FOSS-zhuxue
     * @date 2015-12-17 上午11:16:43
	 */
	public JXDateTimePicker getStartFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0); // 0点
		cal.set(Calendar.MINUTE, 0);// 0分
		cal.set(Calendar.SECOND, 0);// 0秒
		formatTime.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatTime.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		formatTime.setDate(cal.getTime());
		return formatTime;
	}

	/**
	 * 获得格式化后的结束日期
	 * @author 280747-FOSS-zhuxue
     * @date 2015-12-17 上午11:16:43
	 */
	public JXDateTimePicker getEndFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); // 0点
		cal.set(Calendar.MINUTE, NumberConstants.NUMBER_59);// 0分
		cal.set(Calendar.SECOND, NumberConstants.NUMBER_59);// 0秒
		formatTime.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatTime.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		formatTime.setDate(cal.getTime());
		return formatTime;
	}
	

	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		//整车标签打印用到的VO 运单号的检验
		printBinder = BindingFactory.getIntsance().moderateBind(StartSignLabelVo.class, this, new ExpPrinSignDescriptor(), true);
		//运单错误监听
		printBinder.addValidationListener(new WaybillValidationListner());
		//打印联动监听
		printBinder.addBindingListener(new ExpPrintSignBindingListener(this));
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
	private JRadioButton rdbtnNewRadioButton;

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
			tempAreaInfo = getPrintSerialnos(Integer.parseInt(tGoodsNum.getText()));
		}else {
			if (CollectionUtils.isEmpty(labeledlist)) {
				if(Integer.parseInt(tGoodsNum.getText())>0){
					return getPrintSerialnos(Integer.parseInt(tGoodsNum.getText())).toString();
				}else{
					return "";
				}
			}else{
				for(LabeledGoodEntity label : labeledlist){
					tempAreaInfo.append(label.getSerialNo()).append(COMMA);
				}
			}
		}
		return subStringAreaInfo(tempAreaInfo);
	}

	/**
	 * 转寄退回件打印
	 * @author Foss-232608-wusuhua
	 * @date 2015-12-02 19:34:00
	 * @param bWaybillNo
	 */
	private void getBWaybillPrintDetailInfoOnline(String waybillNo){
		if(StringUtils.isNotBlank(waybillNo)){
			waybillService = WaybillServiceFactory.getWaybillService();
			//List<CrmReturnedGoodsDto> queryReturnGoodsWorkOrder(CrmReturnedGoodsDto vo)
			CrmReturnedGoodsDto dto=new CrmReturnedGoodsDto();
			dto.setReturnStatus("HANDLED");
			dto.setOriWaybill(waybillNo);
			List<CrmReturnedGoodsDto> list=waybillService.queryReturnGoodsWorkOrder(dto);
			if(CollectionUtils.isEmpty(list)){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.forwardReturn.error"), 
						i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				//清除数据
				printBWaybillBean=null;
				initBWaybillData();
				return;
			}else{//是转寄退回件的逻辑
				List<WaybillRfcEntity> rfcEntities =waybillService.queryRecentRfc(waybillNo);
				if(CollectionUtils.isEmpty(rfcEntities)){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.forwardReturn.errorrfc"), 
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					//清除数据
					printBWaybillBean=null;
					initBWaybillData();
					return;
				}
				if(!"ACCECPT".equals(rfcEntities.get(0).getStatus())){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.forwardReturn.errorRfcStatus"), 
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					//清除数据
					printBWaybillBean=null;
					initBWaybillData();
					return;
				}
				if(!"RETURN_BACK".equals(list.get(0).getReturnType())&&!"SAME_CITY".equals(list.get(0).getReturnType())&&!"OTHER_CITY".equals(list.get(0).getReturnType())){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.forwardReturn.errorreturnStatus"), 
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					//清除数据
					printBWaybillBean=null;
					initBWaybillData();
					return;
				}
				//流水号
			labeledForward=waybillService.queryAllSerialByWaybillNo(waybillNo);
				//如果这个单子查询出来的流水号为空，则表示此单有问题，直接退出
				if(CollectionUtils.isEmpty(labeledForward)){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.forwardReturn.serial"), 
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					//清除数据
					printBWaybillBean=null;
					initBWaybillData();
					return;
				}
//				//流水号不为空的逻辑往下走
//				printBWaybillBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,null);
				// 判断是否存在更改单未受理情况
				List<String> serialNos = new ArrayList<String>();
				for (int i = 0; i < labeledForward.size(); i++) {
					serialNos.add(labeledForward.get(i).getSerialNo());
				}

				// 运单信息
				WaybillEntity waybillEntity = waybillService.queryWaybillByNumber(labeledForward.get(0).getWaybillNo());
				String destinationAndTodo = waybillService
						.isExistDestinationAndTodoRfc(waybillEntity, serialNos);
				// destinationAndTodo为true表示所有都通过了，但是目的站可能未改变
				 if("WAY_BSE".equals(destinationAndTodo)){
					
					printBWaybillBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
							waybillStatus);
					// 如果没有就报错
					if (printBWaybillBean == null) {
						JOptionPane
								.showMessageDialog(
										null,
										i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
										JOptionPane.WARNING_MESSAGE);
						return;
					}
					printBWaybillBeans.add(printBWaybillBean);
				}else{
					printBWaybillBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
					// 如果没有就报错
					if (printBWaybillBeans == null) {
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
				printBWaybillBean = printBWaybillBeans.get(0);
				
				
				
				// 如果没有就报错
				if (printBWaybillBean == null || StringUtils.isBlank(printBWaybillBean.getWaybillNumber())) {
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
					initBWaybillData();
					return;
				}
				//运单信息是否存在
				if(printBWaybillBean.getWaybillBean()==null){
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
					printBWaybillBean=null;
					initBWaybillData();
					return;
				}
				//打印需要省市区的名字 所以要加进去 实体里面没有  省
				AdministrativeRegionsEntity entity=null;
				if(StringUtils.isNotEmpty(printBWaybillBean.getWaybillBean().getReceiveCustomerProvCode())){
					entity=waybillService.queryAdministrativeRegionsByCode(printBWaybillBean.getWaybillBean().getReceiveCustomerProvCode());
					if(entity!=null){
						printBWaybillBean.setReceiveCustomerProvName(entity.getName());
					}
				}
				//市
				if(StringUtils.isNotEmpty(printBWaybillBean.getWaybillBean().getReceiveCustomerCityCode())){
					entity=waybillService.queryAdministrativeRegionsByCode(printBWaybillBean.getWaybillBean().getReceiveCustomerCityCode());
					if(entity!=null){
						printBWaybillBean.setReceiveCustomerCityName(entity.getName());
					}
				}
				//区
				if(StringUtils.isNotEmpty(printBWaybillBean.getWaybillBean().getReceiveCustomerDistCode())){
					entity=waybillService.queryAdministrativeRegionsByCode(printBWaybillBean.getWaybillBean().getReceiveCustomerDistCode());
					if(entity!=null){
						printBWaybillBean.setReceiveCustomerCityName(entity.getName());
					}
				}
				//提货网点所在市
				if(StringUtils.isNotEmpty(printBWaybillBean.getWaybillBean().getCustomerPickupOrgCode())){
					OrgAdministrativeInfoEntity en=waybillService.queryOrgAdministrativeInfoByCode(printBWaybillBean.getWaybillBean().getCustomerPickupOrgCode());
					if(en!=null){
						entity=waybillService.queryAdministrativeRegionsByCode(en.getCityCode());
						printBWaybillBean.setDestionCity(entity.getSimpleName());
					}
				}
				//返单类别为空
				if(StringUtils.isEmpty(list.get(0).getReturnType())){
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.forwardReturn.returnType"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					initBWaybillData();
					return;
				}
				//返单类别设置进去，用来打印
				printBWaybillBean.setForwardReturn(list.get(0).getReturnType());
				//先判定是否对应的总件数<0
				int totalPieces = Integer.parseInt(printBWaybillBean.getTotalPieces());
				if(totalPieces < 0){
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.forwardReturn.totalPiece"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					initBWaybillData();
					return;
				}
				
//				//收货人
//				private JTextField bReceiver;
//				 //目的站  
//				private JTextField bTargetOrg;
//				//保价金额
//				private JTextField insuredAmount;
//				//代收货款
//				private JTextField collectionOnDelivery;
//				//到付款合计
//				private JTextField totalPayment;
				bReceiver.setText(printBWaybillBean.getWaybillBean().getReceiveCustomerContact());
				bTargetOrg.setText(printBWaybillBean.getDestination());
				insuredAmount.setText(String.valueOf(printBWaybillBean.getWaybillBean().getInsuranceAmount()));
				collectionOnDelivery.setText(String.valueOf(printBWaybillBean.getWaybillBean().getCodAmount()));
				totalPayment.setText(String.valueOf(printBWaybillBean.getWaybillBean().getToPayAmount()));
				bSerialBeigin.setText("1");
				bSerialEnd.setText(String.valueOf(labeledForward.size()));
				StringBuffer tempAreaInfo = new StringBuffer("");
				for (int i = Integer.parseInt(bSerialBeigin.getText()) - 1; i < Integer.parseInt(bSerialEnd.getText()); i++) {
					tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledForward.get(i).getSerialNo()))).append(COMMA);
				}
				bSerialNos.setText(subStringAreaInfo(tempAreaInfo));
			}
			addNumListener();
			}
			
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
			if(waybillNo.equals(printLabelBean.getWaybillNumber())){
				return;
			//这边如果运单号不一致这个就需要清理了，不然真的还是打印之前的
			}else if(CollectionUtils.isNotEmpty(printLabelBeans)){
				printLabelBeans.clear();
				lblConsignee.setIcon(CommonUtils.createIcon(PrintSignUI.class, "", 1, 1));
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
				printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
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
				for(int i=1;i<=totalPieces;i++){
					labelGoodTemp = new LabeledGoodEntity();
					labelGoodTemp.setWaybillNo(printLabelBean.getWaybillNumber());//运单号
					labelGoodTemp.setSerialNo(String.format(FORMATSTR, i));//对应的流水号
					if(labeledlist != null){
						labeledlist.add(labelGoodTemp);//添加到货签信息数据里面
					}
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
				WaybillEntity waybillEntity = waybillService.queryWaybillByNumber(labeledlist.get(0).getWaybillNo());
				//是否为经济快递的运单号
				if(waybillEntity != null){
					if(!ExpWaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){					
					MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expPrintSignAction.MsgBox.notExpressProductNew"));
					return;
				}
				}
				// 判断是否存在更改单未受理情况
				String destinationAndTodo = waybillService
						.isExistDestinationAndTodoRfc(waybillEntity, serialNos);
				// destinationAndTodo为true表示所有都通过了，但是目的站可能未改变
				if ("WAY_UNDO".equals(destinationAndTodo)) {
					String xx = i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipbusiness");
					String xx1 = xx.replaceAll("XX", printLabelBeans.get(0).getDestination());
					// 线提示用户存在更改情况
					JOptionPane.showMessageDialog(null,xx1,
									i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipnext"),JOptionPane.WARNING_MESSAGE);
					return;
				}else if("WAY_BSE".equals(destinationAndTodo)){
					
				   printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
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
			// 界面初始赋值
			// 开单人.
			// tWaybillNo.setText(printLabelBean.getWaybillNumber());
			tReceiver.setText(printLabelBean.getWaybillBean().getReceiveCustomerContact());
			//添加大客户标记
			if(FossConstants.ACTIVE.equals(printLabelBean.getReceiveBigCustomer()) || FossConstants.ACTIVE.equals(printLabelBean.getDeliveryBigCustomer())){
				lblConsignee.setIcon(CommonUtils.createIcon(ExpPrintSignUI.class, IconConstants.BIG_CUSTOMER, 1, 1));
			}else{
				lblConsignee.setIcon(CommonUtils.createIcon(ExpPrintSignUI.class, "", 1, 1));
			}
			tTargetOrg.setText(printLabelBean.getDestination());
			tStartOrg.setText(printLabelBean.getLeavecity());
			tTransTpye.setText(printLabelBean.getTranstype());

			tBeginGoodNum.setText(StringUtils.isEmpty(printLabelBean.getWaybillNumber()) ? "" : "1");
			//增加对货签
			//BUG-48626,增加对货签信息与运单件数的校验，如果运单件数与货签信息不一致，则用货签信息为准
			if(labeledlist != null && Integer.parseInt(printLabelBean.getTotalPieces()) > labeledlist.size()){
				tGoodsNum.setText(String.valueOf(labeledlist.size()));
				tEndGoodNum.setText(String.valueOf(labeledlist.size()));
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
			return false;
		} else if (Integer.parseInt(tEndGoodNum.getText()) > Integer.parseInt(tGoodsNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.six"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}

/**
 * 转寄退回件的校验	
 * @return
 */
	private Boolean validateForward(){
		if (StringUtils.isEmpty(bSerialBeigin.getText()) || Integer.parseInt(bSerialBeigin.getText())<=0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.two"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(bSerialEnd.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.three"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(printBWaybillBean.getTotalPieces())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.four"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Integer.parseInt(bSerialBeigin.getText()) > Integer.parseInt(bSerialEnd.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.five"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Integer.parseInt(bSerialEnd.getText()) > Integer.parseInt(printBWaybillBean.getTotalPieces())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.six"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	private void addNumListener() {
		//转寄退回件的打印
		focusBSerialBeigin=new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(validateForward()){
					StringBuffer tempAreaInfo = new StringBuffer("");
					for (int i = Integer.parseInt(bSerialBeigin.getText()) - 1; i < Integer.parseInt(bSerialEnd.getText()); i++) {
						tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledForward.get(i).getSerialNo()))).append(COMMA);
					}
					bSerialNos.setText(subStringAreaInfo(tempAreaInfo));
				}
			}
			
		};
		//转寄退回件的打印监听
		focusBSerialEnd=new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(validateForward()){
					StringBuffer tempAreaInfo = new StringBuffer("");
					for (int i = Integer.parseInt(bSerialBeigin.getText()) - 1; i < Integer.parseInt(bSerialEnd.getText()); i++) {
						tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledForward.get(i).getSerialNo()))).append(COMMA);
					}
					bSerialNos.setText(subStringAreaInfo(tempAreaInfo));
				}
			}
			
		};
		
		focusListenerBegin = new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
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
		
		focusSerialListenerBegin = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String serianlBegin = txtSerialBeigin.getText().trim();
				String serianlEnd = txtSerialEnd.getText().trim();
				String goodNum = txtPieces.getText().trim();
				if(validateWaybillNum(serianlBegin, serianlEnd, goodNum)){
					//进行设值，因为可能出现数据被改过的情况
					serianlBegin = txtSerialBeigin.getText().trim();
					serianlEnd = txtSerialEnd.getText().trim();
					goodNum = txtPieces.getText().trim();
					StringBuffer sb = new StringBuffer();
					if(CollectionUtils.isNotEmpty(serialNoList)){
						for(int i=0;i<serialNoList.size();i++){
							if(Integer.parseInt(serianlBegin)<=Integer.parseInt(serialNoList.get(i)) && Integer.parseInt(serialNoList.get(i))<=Integer.parseInt(serianlEnd)){
								sb.append(serialNoList.get(i)).append(COMMA);
							}
						}
					}else{
						if(CollectionUtils.isNotEmpty(printEWaybillBeans)){
							String printSerialNo = null;
							for(int i=0;i<printEWaybillBeans.size();i++){
								printSerialNo = printEWaybillBeans.get(i).getPrintSerialNos();
								if(Integer.parseInt(serianlBegin)<=Integer.parseInt(printSerialNo) && Integer.parseInt(serialNoList.get(i))<=Integer.parseInt(printSerialNo)){
									sb.append(printSerialNo).append(COMMA);
								}
							}							
						}
					}
					String text = sb.toString();
					if(StringUtils.isNotEmpty(text)){
						textSerialNos.setText(text.toString().substring(0, text.length()-1));
					}
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		};
		
		focusSerialListenerEnd = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String serianlBegin = txtSerialBeigin.getText().trim();
				String serianlEnd = txtSerialEnd.getText().trim();
				String goodNum = txtPieces.getText().trim();
				if(validateWaybillNum(serianlBegin, serianlEnd, goodNum)){
					StringBuffer sb = new StringBuffer();
					serianlBegin = txtSerialBeigin.getText().trim();
					serianlEnd = txtSerialEnd.getText().trim();
					goodNum = txtPieces.getText().trim();
					if(CollectionUtils.isNotEmpty(serialNoList)){
						for(int i=0;i<serialNoList.size();i++){
							if(Integer.parseInt(serianlBegin)<=Integer.parseInt(serialNoList.get(i)) && Integer.parseInt(serialNoList.get(i))<=Integer.parseInt(serianlEnd)){
								sb.append(serialNoList.get(i)).append(COMMA);
							}
						}
					}else{
						if(CollectionUtils.isNotEmpty(printEWaybillBeans)){
							String printSerialNo = null;
							for(int i=0;i<printEWaybillBeans.size();i++){
								printSerialNo = printEWaybillBeans.get(i).getPrintSerialNos();
								if(Integer.parseInt(serianlBegin)<=Integer.parseInt(printSerialNo) && Integer.parseInt(serialNoList.get(i))<=Integer.parseInt(printSerialNo)){
									sb.append(printSerialNo).append(COMMA);
								}
							}							
						}
					}
					String text = sb.toString();
					if(StringUtils.isNotEmpty(text)){
						textSerialNos.setText(text.toString().substring(0, text.length()-1));
					}
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		};
		// 联动监听
		tBeginGoodNum.addFocusListener(focusListenerBegin);
		tEndGoodNum.addFocusListener(focusListenerEnd);
		txtSerialBeigin.addFocusListener(focusSerialListenerBegin);
		txtSerialEnd.addFocusListener(focusSerialListenerEnd);
		//转寄退回件2个输入框的失去焦点的监听
		bSerialBeigin.addFocusListener(focusBSerialBeigin);
		bSerialEnd.addFocusListener(focusBSerialEnd);
	}
	
	/**
	 * 校验数据的正确性
	 */
	public boolean validateWaybillNum(String startNum, String endNum, String goodNum){
		if (StringUtils.isBlank(startNum) || Integer.parseInt(startNum)<=0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.two"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			txtSerialBeigin.setText(serialNoList.get(0));
			return true;
		} else if (StringUtils.isBlank(endNum) || Integer.parseInt(endNum)<=0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.three"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			txtSerialEnd.setText(serialNoList.get(serialNoList.size()-1));
			return true;
		} else if (StringUtils.isEmpty(goodNum)) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.four"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			txtPieces.setText(printLabelBean.getTotalPieces());
			return false;
		} else if (Integer.parseInt(startNum) > Integer.parseInt(endNum)) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.five"), i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			txtSerialBeigin.setText(serialNoList.get(0));
			txtSerialEnd.setText(serialNoList.get(serialNoList.size()-1));
			return true;
		} else if (Integer.parseInt(endNum) > Integer.parseInt(goodNum)) {
			return true;
		} else {
			return true;
		}
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
		//转寄退回件打印
		print.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(printBWaybillBean==null){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.waybillNotExist.error"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				String[] seriesNos=bSerialNos.getText().split(COMMA);
				if(seriesNos.length == 0){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.endGoodNum.toolTipText"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
				PrintUtil printUtil=new PrintUtil();
				printBWaybillBean.setPrintSerialnos(bSerialNos.getText());
				LabelPrintContext ctx= printUtil.printForwardReturnData(printBWaybillBean);
				 //转寄退回件
				try {
					LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_forwardReturnPrintWorker, ctx);
					for (String  serieNo : seriesNos) {
						//记录打印日志
						GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
						printLabelEntity.setWaybillNo(bWaybillNo.getText());
						printLabelEntity.setSerialNo(serieNo);
						printLabelEntity.setPrintTime(new Date());
						printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
						printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
						waybillService.addPrintLabelInfo(printLabelEntity);
					}
				} catch (PrintException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				} catch (Exception exp) {
					// TODO Auto-generated catch block
					LOG.error("标签打印异常" + exp.toString(), exp);
					JOptionPane.showMessageDialog(null, exp.toString(), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		// 打印按钮
		btnPrint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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
						printLabelBean.setPrintSerialnos(textArea.getText());
						//设置异常货物标志
						printLabelBean.setUnusual(uunsual);
						PrintUtil printUtil = new PrintUtil();
						LabelPrintContext ctx = printUtil.printLabelData(printLabelBean);
						try {
							LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker, ctx);
							for (String  serieNo : seriesNo) {
								//记录打印日志
								GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
								printLabelEntity.setWaybillNo(tWaybillNo.getText());
								printLabelEntity.setSerialNo(serieNo);
								printLabelEntity.setPrintTime(new Date());
								printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
								printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
								waybillService.addPrintLabelInfo(printLabelEntity);
							}
						} catch (PrintException e2) {
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
			
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
									//设置单个流水号
									bean.setPrintSerialnos(seriesNo[i]);
									//设置异常货物标志
									bean.setUnusual(uunsual);
									PrintUtil printUtil = new PrintUtil();
									LabelPrintContext ctx = printUtil.printLabelData(bean);
									try {
										LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker, ctx);
										//记录打印日志
										GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
										printLabelEntity.setWaybillNo(tWaybillNo.getText());
										printLabelEntity.setSerialNo(seriesNo[i]);
										printLabelEntity.setPrintTime(new Date());
										printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
										printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
										waybillService.addPrintLabelInfo(printLabelEntity);
									} catch (PrintException e2) {
										JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
									}
								}
							}
						}
					}
					MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
				} catch (Exception exp) {
					LOG.error("标签打印异常" + exp.toString(), exp);
					JOptionPane.showMessageDialog(null, exp.toString(), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
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
		
		//打印电子运单
		btnEWaybillPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] seriesNo = textSerialNos.getText().split(COMMA);
				if(seriesNo.length == 0){
					//输入截止件数已获得流水号
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.endGoodNum.toolTipText"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				//进行已经被选择的数据进行封装打印
				List<EWaybillPrintDto> eWaybillPrintTemp = new ArrayList<EWaybillPrintDto>();
				for(int i=0;i<printEWaybillBeans.size();i++){
					for(int j=0;j<seriesNo.length;j++){
						if(seriesNo[j].equals(printEWaybillBeans.get(i).getPrintSerialNos())){
							eWaybillPrintTemp.add(printEWaybillBeans.get(i));
						}
					}
				}
				if(CollectionUtils.isNotEmpty(eWaybillPrintTemp) && eWaybillPrintTemp.size() > 0){
					//进行数据的打印
					PrintUtil printUtil = new PrintUtil();
					UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
					LabelPrintContext ctx = null;
					for(EWaybillPrintDto eWaybillPrintDto : eWaybillPrintTemp){
						try {
							String printVersion = null;
							if(rdbtnWareHouse.isSelected()){
								//printVersion = LblPrtServiceConst.key_lblprt_program_WareHouseOrderPrintWorker;
							}else if(rdbtnThree.isSelected()){
							///	printVersion = LblPrtServiceConst.key_lblprt_program_EWaybillThreePagePrintWorker;
							}else if(rdbtnInside.isSelected()){
							//	printVersion = LblPrtServiceConst.key_lblprt_program_EWaybillInsidePrintWorker;
							}else if(rdbtnNew.isSelected()){
								printVersion = LblPrtServiceConst.key_lblprt_program_EWaybillNewPrintWorker;
							}else if(rdbtnOld.isSelected()){
								if(eWaybillPrintDto.getGoodsQtyTotal() > 1 && Integer.parseInt(eWaybillPrintDto.getPrintSerialNos()) > 1){
									printVersion = LblPrtServiceConst.key_lblprt_program_EWaybillReceiveStubCopyPrintWorker;
								}else{
									printVersion = LblPrtServiceConst.key_lblprt_program_EWaybillReceiveStubCopyPrintWorker;
								}
							}else{
								//请选择一个版本
								MsgBox.showInfo(i18n.get("foss.gui.creating.ExpPrintSignUI.warning.selectOnVersion"));
								return;
							}
							ctx = printUtil.printEWaybillLabelData(eWaybillPrintDto);
							if(eWaybillPrintDto.getGoodsQtyTotal() > 1 && Integer.parseInt(eWaybillPrintDto.getPrintSerialNos()) > 1){
								LabelPrintManager.doLabelPrintAction(printVersion, ctx);
							}else{
								LabelPrintManager.doLabelPrintAction(printVersion, ctx);
							}
							//记录打印日志
							GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
							printLabelEntity.setWaybillNo(eWaybillPrintDto.getWaybillNo());
							printLabelEntity.setSerialNo(eWaybillPrintDto.getPrintSerialNos());
							printLabelEntity.setPrintTime(new Date());
							printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
							printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
							waybillService.addPrintLabelInfo(printLabelEntity);
							if(rdbtnWareHouse.isSelected()){
								break;
							}
						} catch (PrintException e2) {
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
							return;
						} catch (Exception exp) {
							LOG.error("标签打印异常" + exp.toString(), exp);
							JOptionPane.showMessageDialog(null, exp.toString(), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
			}
		});
		
		// 打印设置
		btnEWaybillSet.addActionListener(new ActionListener() {
			@Override
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
	 * 转寄退回件打印与打印设置按钮监听                                                               
	 */
		printInstall.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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

	public JTextField geteWaybillNo() {
		return eWaybillNo;
	}

	public void seteWaybillNo(JTextField eWaybillNo) {
		this.eWaybillNo = eWaybillNo;
	}

	public JTextField getTxtSerialBeigin() {
		return txtSerialBeigin;
	}

	public void setTxtSerialBeigin(JTextField txtSerialBeigin) {
		this.txtSerialBeigin = txtSerialBeigin;
	}

	public JTextField getTxtSerialEnd() {
		return txtSerialEnd;
	}

	public void setTxtSerialEnd(JTextField txtSerialEnd) {
		this.txtSerialEnd = txtSerialEnd;
	}

	public JTextField getTxtPieces() {
		return txtPieces;
	}

	public void setTxtPieces(JTextField txtPieces) {
		this.txtPieces = txtPieces;
	}

	public JTextField getTxtReceiver() {
		return txtReceiver;
	}

	public void setTxtReceiver(JTextField txtReceiver) {
		this.txtReceiver = txtReceiver;
	}

	public JTextArea getTextSerialNos() {
		return textSerialNos;
	}
	
	public void setTextSerialNos(JTextArea textSerialNos) {
		this.textSerialNos = textSerialNos;
	}

	public List<EWaybillPrintDto> getPrintEWaybillBeans() {
		return printEWaybillBeans;
	}
	
	public void setPrintEWaybillBeans(List<EWaybillPrintDto> printEWaybillBeans) {
		this.printEWaybillBeans = printEWaybillBeans;
	}

	public List<JCheckBox> getList() {
		return list;
	}

	public void setList(List<JCheckBox> list) {
		this.list = list;
	}

	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}

	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}

	public JButton getBtnPrint5() {
		return btnPrint5;
	}

	public void setBtnPrint5(JButton btnPrint5) {
		this.btnPrint5 = btnPrint5;
	}

	
}