package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpReturnGoodsApplyAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpReturnGoodsSearchAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpReturnedGoodsResetAction;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpReturnedGoodsWaybillValidationListner;
import com.deppon.foss.module.pickup.creatingexp.client.vo.ExpReturnedGoodsWaybillVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ExpReturnedGoodsWaybillUI extends JPanel implements IApplicationAware, IPluginAware  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 日志
	public static final Logger logger = LoggerFactory.getLogger(ExpReturnedGoodsWaybillUI.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpReturnedGoodsWaybillUI.class);

	// 窗口应用程序
	private IApplication application;
	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";
	/**
	 * gif picture
	 */
	private static final String SUPPLYWAYBILL = "supplywaybill.png";
	/**
	 * date
	 */
	private static final String DATE = "date";
	
	private static final int SEVEN = 7;
	
	private static final int FIFTEEN = 15;
	
	private static final int ELEVEN = 11;
	
	private static final int THREE = 3;
	
	private Plugin plugin;
	//返单号
	@Bind("returnWaybillNo")
	private JTextFieldValidate returnWaybillNo;
	//原单号
	@Bind("waybillNo")
	private JTextFieldValidate waybillNo;
	//受理状态
	@Bind("acceptanceStatus")
	private JComboBox statusComboBox;
	//返货类型
	@Bind("returnGoodsType")
	private JComboBox returnGoodsTypeComboBox;
	private JLabel label;
	//任务部门
	@Bind("taskDepartment")
	private JComboBox taskDepartmentComboBox;
	// 创建时间:开始时间
	@Bind(value = "createStartTime", property = DATE)
	private JXDateTimePicker zdStartDate;
	// 创建时间:结束时间
	@Bind(value = "createEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;
	//按钮面板
	private JPanel buttonPanel;
	// 结果表
	private JXTable table = new JXTable();
	// 表外滚动条
	private JScrollPane scrollPane;
	//结果列表面板
	private JPanel tablePanel;
	//查询按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpReturnGoodsSearchAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
	//重置按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpReturnedGoodsResetAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));
	//创建按钮
	@ButtonAction(icon = SUPPLYWAYBILL, shortcutKey = "", type = ExpReturnGoodsApplyAction.class)
	private JButton createButton = new JButton(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create"));
	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;
	/**
	 * 选中的列
	 */
	//private CheckBoxColumn checkBoxColumn;
	// 绑定接口
	private IBinder<ExpReturnedGoodsWaybillVo> expReturnedGoodsWaybillBinder;

	private Map<String, IBinder<ExpReturnedGoodsWaybillVo>> bindersMap = new HashMap<String, IBinder<ExpReturnedGoodsWaybillVo>>();

	public ExpReturnedGoodsWaybillUI(){
		init();
		bind();
		initComboBox();
		registToRespository();
		// 监听Enter
		EnterKeySalesDept enterMixNo = new EnterKeySalesDept(searchButton);
		returnWaybillNo.addKeyListener(enterMixNo);
		EnterKeySalesDept enterOrder = new EnterKeySalesDept(searchButton);
		waybillNo.addKeyListener(enterOrder);
		
		EnterKeySalesDept enterTable = new EnterKeySalesDept(this);
		table.addKeyListener(enterTable);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");

	}
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		expReturnedGoodsWaybillBinder = BindingFactory.getIntsance().moderateBind(ExpReturnedGoodsWaybillVo.class, this, true);
		expReturnedGoodsWaybillBinder.addValidationListener(new ExpReturnedGoodsWaybillValidationListner());

	}
	
	/**
	 * 
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	public void initComboBox() {
		//受理状态
		initAcceptanceStatus();
		//返货类型
		initReturnGoodsType();
		//任务部门
		initTaskDepartment();

	}
	
	/**
	 * 返货类型
	 * @see
	 */
	private void initReturnGoodsType() {
//		返货类型更改为转寄退回件类型，类型：客户拒收，7天返货，外发3天返货，继续派送，滞留件更改为：类型：退回件，同城转寄，非同城转寄，7天转寄退回件， 外发3天转寄退回件
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		/* 附加信息 */
		DataDictionaryValueVo DDValuevo = new DataDictionaryValueVo();
		DDValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		DDValuevo.setValueCode("all");
		comboModel.addElement(DDValuevo);
		
		//退回
		DataDictionaryValueVo back = new DataDictionaryValueVo();
		back.setValueCode(WaybillConstants.RETURN_BACK);
		back.setValueName("退回件");
		comboModel.addElement(back);
		
		//返货类型：同城转寄
		DataDictionaryValueVo obsolete = new DataDictionaryValueVo();
		obsolete.setValueCode(WaybillConstants.RETURNTYPE_SAME_CITY);
		obsolete.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.same"));
		comboModel.addElement(obsolete);
		
		////返货类型：非同城转寄
		DataDictionaryValueVo abort = new DataDictionaryValueVo();
		abort.setValueCode(WaybillConstants.RETURNTYPE_OTHER_CITY);
		abort.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.other"));
		comboModel.addElement(abort);
		
		//返货类型：7天返货
		DataDictionaryValueVo ret = new DataDictionaryValueVo();
		ret.setValueCode(WaybillConstants.RETURNTYPE_SEVEN_DAYS_RETURN);
		ret.setValueName("7天转寄退回件");
		comboModel.addElement(ret);
		
		//返货类型：外发3天返货 
		DataDictionaryValueVo wff = new DataDictionaryValueVo();
		wff.setValueCode(WaybillConstants.RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN);
		wff.setValueName("外发3天转寄退回件");
		comboModel.addElement(wff);
			
		
		/*//
		 * 
		 * //返货类型：客户拒收
		DataDictionaryValueVo ddv = new DataDictionaryValueVo();
		ddv.setValueCode(WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE);
//		ddv.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.refuse"));
		ddv.setValueName("退回件");
		comboModel.addElement(ddv);
		 * 
		 * 
		 * 	//返货类型：继续派送
		DataDictionaryValueVo jxps = new DataDictionaryValueVo();
		jxps.setValueCode(WaybillConstants.RETURNTYPE_CONTINUE_SENDING);
		jxps.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sending"));
		comboModel.addElement(jxps);
		
		 //返货类型：滞留件
		DataDictionaryValueVo zlj = new DataDictionaryValueVo();
		zlj.setValueCode(WaybillConstants.RETURNTYPE_DETAINED_GOODS);
		zlj.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.detaomedDoods"));
		comboModel.addElement(zlj);
		 
		DataDictionaryValueVo wf = new DataDictionaryValueVo();
		wf.setValueCode(WaybillConstants.RETURNTYPE_SEND_OUT);
		wf.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sendOut"));
		comboModel.addElement(wf);
		
		
		*/
		returnGoodsTypeComboBox.setModel(comboModel);
		
		ExpReturnedGoodsWaybillVo bean = expReturnedGoodsWaybillBinder.getBean();
		bean.setAcceptanceStatus(DDValuevo);
	}
	/**
	 * 初始化受理状态
	 * @see
	 */
	private void initAcceptanceStatus() {
//		受理状态内已处理、已退回和未受理更改为 受理状态：同意、不同意和未受理；
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		/* 附加信息 */
		DataDictionaryValueVo DDValuevo = new DataDictionaryValueVo();
		DDValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		DDValuevo.setValueCode("all");
		comboModel.addElement(DDValuevo);
		
		//已受理
		DataDictionaryValueVo obsolete = new DataDictionaryValueVo();
		obsolete.setValueCode(WaybillConstants.ACCEPTSTATUS_HANDLED);
//		obsolete.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.handled"));
		obsolete.setValueName("同意");
		comboModel.addElement(obsolete);
		
		//未受理
		DataDictionaryValueVo abort = new DataDictionaryValueVo();
		abort.setValueCode(WaybillConstants.ACCEPTSTATUS_REFUSED);
		abort.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.refused"));
		comboModel.addElement(abort);
		
		//已退回
		DataDictionaryValueVo sendBack = new DataDictionaryValueVo();
		sendBack.setValueCode(WaybillConstants.ACCEPTSTATUS_RETURNED);
//		sendBack.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returned"));
		sendBack.setValueName("不同意");
		comboModel.addElement(sendBack);
		
		//默认选中 未受理 -- sangwenhao-272311
		comboModel.setSelectedItem(abort);
		
		statusComboBox.setModel(comboModel);
		
		ExpReturnedGoodsWaybillVo bean = expReturnedGoodsWaybillBinder.getBean();
		bean.setAcceptanceStatus(DDValuevo);
	}
	
	/**
	 * 初始化任务部门
	 */
	private void initTaskDepartment(){
		EmployeeEntity employee = null;
		String orgCode = "";
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user != null){
			 employee = user.getEmployee();
		}
		if(employee !=null){
			orgCode = employee.getOrgCode();
		}
		
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		/* 附加信息 */
		DataDictionaryValueVo DDValuevo = new DataDictionaryValueVo();
		DDValuevo.setValueName("全部");
		DDValuevo.setValueCode("all");
		comboModel.addElement(DDValuevo);
		
		DataDictionaryValueVo current = new DataDictionaryValueVo();
		current.setValueName("当前部门");
		current.setValueCode(orgCode);
		comboModel.addElement(current);
		//默认选中 未受理 -- sangwenhao-272311
		comboModel.setSelectedItem(current);
		
		taskDepartmentComboBox.setModel(comboModel);
	}

	
	/**
	 * 
	 * @description：保存绑定对象
	 */
	public void registToRespository() {
		bindersMap.put("expReturnedGoodsWaybillBinder", expReturnedGoodsWaybillBinder);
	}

	
	/**
	 * 初始化界面信息
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "转寄退回件查询条件", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "1, 2, fill, default");
		
		FormLayout flpanel = new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:max(145px;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(33dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(96dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(51dlu;default):grow"), }, new RowSpec[] { RowSpec.decode("max(6dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("23px:grow"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default):grow"), });
		flpanel.setColumnGroups(new int[][] { new int[] { FIFTEEN, ELEVEN, SEVEN, THREE } });
		panel.setLayout(flpanel);

		// 返单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Query.ReturnWaybillNo"));
		panel.add(label1, "1, 3, right, top");

		returnWaybillNo = new JTextFieldValidate();
		panel.add(returnWaybillNo, "3, 3, fill, default");
		
		// 返单号
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Query.WaybillNo"));
		panel.add(label2, "5, 3, right, default");
		
		waybillNo = new JTextFieldValidate();
		panel.add(waybillNo, "7, 3, fill, default");
		
		// 受理状态
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Query.AcceptanceStatus"));
		panel.add(label3, "9, 3, right, default");
		
		statusComboBox = new JComboBox();
		panel.add(statusComboBox, "11, 3, fill, default");
		
		//返货类型
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Query.ReturnGoodsType"));
		panel.add(label4, "13, 3, right, default");

		returnGoodsTypeComboBox = new JComboBox();
		panel.add(returnGoodsTypeComboBox, "15, 3, fill, default");

		//创建时间
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Query.CreateTime"));
		panel.add(label5, "1, 5, right, default");
		//开始时间
		zdStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(zdStartDate), "3, 5, 2, 1, fill, fill");
		
		JLabel label6 = new JLabel("----");
		panel.add(label6, "5, 5");
		//结束时间
		zdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(zdEndDate), "6, 5, 2, 1, fill, default");
		
		label = new JLabel("任务部门");
		panel.add(label, "9, 5, right, default");
		
		taskDepartmentComboBox = new JComboBox();
		panel.add(taskDepartmentComboBox, "11, 5, fill, default");
		
		//按钮面板
		buttonPanel = new JPanel();
		panel.add(buttonPanel, "15, 7, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(resetButton);
		//数据结果面板
		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptWaybillUI.queryWaybill.label"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablePanel, "1, 3, fill, default");
		tablePanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("4dlu:grow"), FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
						new RowSpec[] { RowSpec.decode("9px"), FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.DEFAULT_ROWSPEC, }));

		//创建按钮
		tablePanel.add(createButton, "6, 2, fill, fill");

		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");
		
	}
	/**
	 * Enter键监听表格
	 * 
	 */
	public void tableListenter() {
		try {
			// 将UI上的行转成model中的行
			int row = table.convertRowIndexToModel(table.getSelectedRow());
			// 双击某一行以后进入界面
			Object obj = table.getModel().getValueAt(row, 2);
			if (obj != null) {
				
			}

		} catch (BusinessException ee) {
			logger.error("导入返货运单异常", ee);
			MsgBox.showError(i18n.get("foss.gui.creating.salesDeptWaybillUI.msgBox.importWaybillException") + ee.getMessage());
		}
	}
	/**
	 * 初始化查询结果表格
	 * 
	 */
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
			if(i==tcm.getColumnCount()-1||i==tcm.getColumnCount()-2){
				/*tc.setPreferredWidth(0);
				tc.setMaxWidth(0);
				tc.setMaxWidth(0); */
			    tc.setPreferredWidth(0); 
			    tc.setMinWidth(0);
			    tc.setMaxWidth(0);
			    tc.setWidth(0); 
			    table.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0); 
			    table.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0); 
			}
			
		}
		
		// 刷新表格
		LinkTableMode tableModel = new LinkTableMode(getArray(list));
		table.setModel(tableModel);
		refreshTable(table);
		
	}
	
	@SuppressWarnings({ "rawtypes"})
	public Object[][] getArray(List list) {
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
				q = getRowValue(pending);

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
	 * getRowValue结果列表
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object) {
		ExpReturnedGoodsWaybillVo vo = (ExpReturnedGoodsWaybillVo) object;
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 返单号
		String returnWaybillNo = StringUtil.defaultIfNull(vo.getReturnWaybillNo());
		// 原单号
		String waybillNo = StringUtil.defaultIfNull(vo.getWaybillNo());
		// 受理状态
		DataDictionaryValueVo status=vo.getAcceptanceStatus();
		String acceptanceStatus = (null!=status)?status.getValueName():"";//StringUtil.defaultIfNull(vo.getAcceptanceStatus().getValueName());
		//返货方式
		DataDictionaryValueVo mode = vo.getReturnMode();
		String returnMode = (null!=mode)?mode.getValueName():"";
		// 返货类型
		DataDictionaryValueVo type=vo.getReturnGoodsType();
		String returnGoodsType = (null!=type)?type.getValueName():"";//StringUtil.defaultIfNull(vo.getReturnGoodsType().getValueName());
		// 工单号
		String workOrder = StringUtil.defaultIfNull(vo.getWorkOrder());
		// 代收货款
		BigDecimal payment=vo.getGoodsPayment();		
		String goodsPayment =(null!=payment)?payment.toString():"";// StringUtil.defaultIfNull(vo.getGoodsPayment().toString());
		// 收货地址
		String address = StringUtil.defaultIfNull(vo.getAddress());
		// 创建人姓名
		String creatorName = StringUtil.defaultIfNull(vo.getCreatorName());
		String createTime = "";
		if(vo.getCreateTime() != null && !"".equals(vo.getCreateTime())){
			// 创建时间
			createTime = StringUtil.defaultIfNull(dateFormat.format(vo.getCreateTime()));

		}
		String handleTime = "";
		if(vo.getHandleTime() != null && !"".equals(vo.getHandleTime())){
			// 受理时间
			handleTime = StringUtil.defaultIfNull(dateFormat.format(vo.getHandleTime()));
		}
		// 受理人姓名
		String handlerName = StringUtil.defaultIfNull(vo.getHandlerName());
		//返货原因
		String returnReason=StringUtil.defaultIfNull(vo.getReturnReason());
		
		String isHandle=vo.getIsHandle();
		
		// 获取对象成员保存至一维数组
		Object[] obj = {"","",waybillNo,returnWaybillNo,workOrder,goodsPayment,address,returnMode,
				returnGoodsType,acceptanceStatus,creatorName,createTime,handlerName,handleTime,returnReason,isHandle};
		 return obj;
	}
	/**
	 * 刷新table
	 * 
	 * @param table
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		LinkTableMode.adjustColumnPreferredWidths(table);
		table.setSortable(true);
		
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	/**
	 * 获得格式化后的开始日期
	 * 
	 *
	 */
	public JXDateTimePicker getStartFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
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
	 *
	 * 
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
	
	/**
	 * set plugin
	 */
	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;

	}

	/**
	 * plugin
	 * 
	 * 
	 */
	public Plugin getPlugin() {
		return plugin;
	}


	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	/**
	 * getApplication
	 * 
	 */
	public IApplication getApplication() {
		return application;
	}
	public JTextFieldValidate getReturnWaybillNo() {
		return returnWaybillNo;
	}
	public void setReturnWaybillNo(JTextFieldValidate returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}
	public JTextFieldValidate getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(JTextFieldValidate waybillNo) {
		this.waybillNo = waybillNo;
	}
	public JComboBox getStatusComboBox() {
		return statusComboBox;
	}
	public void setStatusComboBox(JComboBox statusComboBox) {
		this.statusComboBox = statusComboBox;
	}
	public JComboBox getReturnGoodsTypeComboBox() {
		return returnGoodsTypeComboBox;
	}
	public void setReturnGoodsTypeComboBox(JComboBox returnGoodsTypeComboBox) {
		this.returnGoodsTypeComboBox = returnGoodsTypeComboBox;
	}
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
	public JPanel getButtonPanel() {
		return buttonPanel;
	}
	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}
	public JXTable getTable() {
		return table;
	}
	public void setTable(JXTable table) {
		this.table = table;
	}
	public JPanel getTablePanel() {
		return tablePanel;
	}
	public void setTablePanel(JPanel tablePanel) {
		this.tablePanel = tablePanel;
	}
	public JButton getSearchButton() {
		return searchButton;
	}
	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}
	public JButton getResetButton() {
		return resetButton;
	}
	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}
	public JButton getCreateButton() {
		return createButton;
	}
	public void setCreateButton(JButton createButton) {
		this.createButton = createButton;
	}
	public IBinder<ExpReturnedGoodsWaybillVo> getExpReturnedGoodsWaybillBinder() {
		return expReturnedGoodsWaybillBinder;
	}
	public void setExpReturnedGoodsWaybillBinder(
			IBinder<ExpReturnedGoodsWaybillVo> expReturnedGoodsWaybillBinder) {
		this.expReturnedGoodsWaybillBinder = expReturnedGoodsWaybillBinder;
	}
	public Map<String, IBinder<ExpReturnedGoodsWaybillVo>> getBindersMap() {
		return bindersMap;
	}
	public void setBindersMap(
			Map<String, IBinder<ExpReturnedGoodsWaybillVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}
	public JComboBox getTaskDepartmentComboBox() {
		return taskDepartmentComboBox;
	}
	public void setTaskDepartmentComboBox(JComboBox taskDepartmentComboBox) {
		this.taskDepartmentComboBox = taskDepartmentComboBox;
	}
	
	
}
