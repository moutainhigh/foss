package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;


import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillDownLoadWeightAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillExportWeightAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillQueryWeightAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillResetWeightAction;
import com.deppon.foss.module.pickup.creating.client.common.CopyContent;
import com.deppon.foss.module.pickup.creating.client.listener.SalesDeptWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.ewaybill.LTLEWaybilInfoDialog;
import com.deppon.foss.module.pickup.creating.client.ui.ewaybill.LTLEWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 电子导入重量体积UI
 * @author 305082
 */
public class LTLEWaybillImportWeightUI  extends JPanel implements IApplicationAware, IPluginAware,ActionListener  {
	public LTLEWaybillImportWeightUI() {
		init();// 运单状态(待处理类型)
		initCombRfcStatus();
		initCombTaskDept();
	}
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillImportWeightUI.class);

	//国际化对象
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillImportWeightUI.class);

	//field name
	private static final String FIELDNAME = "fieldName";

	//date
	private static final String DATE = "date";

	//gif picture
	private static final String SEARCH16GIF = "Search16.gif";

	//序列化版本号
	private static final long serialVersionUID = -3007090409503842546L;

	//运单号
	@Bind("mixNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JXTextField txtMixNo;
	
	//发货客户编码
	@Bind("deliveryCustomerCode")
	private JXTextField txtCustomerCode;
	
	//更改单状态
	private JComboBox combRfcStatus;
	
	//任务部门
	private JComboBox combRfcTaskDept; 

	// 提交时间
	@Bind(value = "createStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "开单时间") })
	JXDateTimePicker importStartTime;

	@Bind(value = "createEndTime", property = DATE)
	JXDateTimePicker importEndTime;
	
	//运单状态model
	private DefaultComboBoxModel comboWaybillStatusModel;
	
	//任务部门
	private DefaultComboBoxModel comboWaybillTaskDept;
	
	private Plugin plugin;
	
	//查询按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillQueryWeightAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
	
	//重置按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillResetWeightAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));
	
	//下载模板
	@ButtonAction(type = LTLEWaybillDownLoadWeightAction.class)
	private JButton downLoadButton = new JButton(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.LTLEWaybillChangeEWaybilInfoDialog.downloadTemplate"));
	
	//导出按钮
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillExportWeightAction.class)
	private JButton exportButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.export"));
	
	//导入按钮
	private JButton inputButton = new JButton(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.LTLEWaybillChangeEWaybilInfoDialog.importTemplate"));
	
	// 全选 
	private JCheckBox allSelectCheckBox;

	// 选中的列 
	private CheckBoxColumn checkBoxColumn;
	

	// 结果表
	private JXTable table ;
	
	private LTLEWaybillTableModel tableModel;

	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();


	// 绑定接口
	private IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder;

	private Map<String, IBinder<SalesDeptWaybillVo>> bindersMap = new HashMap<String, IBinder<SalesDeptWaybillVo>>();

	// 窗口应用程序
	private IApplication application;

	// 选中的导出运单号
	private List<String> selectExportWaybillNoList = new ArrayList<String>();
	
	private static final int THREE = 3;
	
	private static final int FIVE = 5;
	
	private static final int SIX = 6;
	
	private static final int SEVEN = 7;
	
	private static final int TEN = 10;
	
	private static final int FIFTEEN = 15;
	
	private static final int NINETEEN = 19;
	
	private static final int TWENTY_THREE = 23;
	
	private static final int FIFTY_NINE = 59;
	

	/**
	 * 打开UI界面初始化的界面
	 * @author 305082
	 */
	public void openUI() {
		if(SwingUtilities.isEventDispatchThread()){
			initCommonWaybillEditUI();
		}else{
			//由于是异步打开窗口  所以需要放在swing专用图形线程中，否则界面会出错
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					initCommonWaybillEditUI();
				}
			});
		}	
	}

	private void initCommonWaybillEditUI() {
		init();
		// 更改单状态(待处理类型)
		initCombRfcStatus();
		initCombTaskDept();
		bind();
		registToRespository();
		addLisner();
		// 监听Enter
		EnterKeySalesDept enterMixNo = new EnterKeySalesDept(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		EnterKeySalesDept enterCreateUserCode = new EnterKeySalesDept(searchButton);
		txtCustomerCode.addKeyListener(enterCreateUserCode);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}

	private void addLisner() {
		inputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//增加配置参数
				ConfigurationParamsEntity config = waybillService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillRfcConstants.WAYBILL_BATCH_RFC_SWITCH, FossConstants.ROOT_ORG_CODE);
				if(config == null || !WaybillConstants.YES.equalsIgnoreCase(config.getConfValue())){
					MsgBox.showInfo("没有配置批量更改配置参数，请联系管理员配置!");
				}else{
					LTLEWaybilInfoDialog dialog = new LTLEWaybilInfoDialog();
	    			// 剧中显示弹出窗口
	    			WindowUtil.centerAndShow(dialog);
				}
			}
		});
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.LTLEWaybillWeightUI.importWeightAndVolume"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "1, 2, fill, default");
		FormLayout flpanel = new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:max(145px;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(33dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(96dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(81dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(65dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("max(6dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("23px:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		flpanel.setColumnGroups(new int[][]{new int[]{NINETEEN, FIFTEEN, SEVEN, THREE}});
		panel.setLayout(flpanel);
		this.setBackground(CommonUtils.getExpressColor());
		
		// 运单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");
		//运单号输入框
		txtMixNo = new JXTextField(i18n.get("foss.gui.creating.numberPanel.waybillNo.label"));
		panel.add(txtMixNo, "3, 3, fill, default");
		txtMixNo.setColumns(FIFTEEN);
		
		// 发货客户编码
		JLabel lblCustomerCode = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label"));
		panel.add(lblCustomerCode, "5, 3, right, default");
		
		// 发货客户编码输入框
		txtCustomerCode = new JXTextField(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label"));
		panel.add(txtCustomerCode, "7, 3, fill, default");
		
			
		//收货部门
		JLabel deliverDept = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.receiveOrgCode.label"));
		panel.add(deliverDept, "11, 3, right, default");
		
		combRfcTaskDept = new JComboBox();
		comboWaybillTaskDept = (DefaultComboBoxModel) combRfcTaskDept.getModel();
		panel.add(combRfcTaskDept, "13, 3, fill, default");

		// 下单时间
		JLabel label = new JLabel(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.importTime"));
		panel.add(label, "1, 5, right, default");
		
		importStartTime = new JXDateTimePicker();
		panel.add(getStartFormatTime(importStartTime), "3, 5, fill, default");
		
		// --
		JLabel label2 = new JLabel("至");
		panel.add(label2, "5, 5, center, default");
		
		importEndTime = new JXDateTimePicker();
		panel.add(getEndFormatTime(importEndTime), "7, 5, fill, default");
		//导入状态
		JLabel importStats = new JLabel(i18n.get("foss.gui.creating.LTLEWaybillWeightUI.importStatus"));
		panel.add(importStats, "11, 5, right, default");
		
		combRfcStatus = new JComboBox();
		comboWaybillStatusModel = (DefaultComboBoxModel) combRfcStatus.getModel();
		panel.add(combRfcStatus, "13, 5, fill, default");

		
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, "17, 9, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, FIVE, FIVE));
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(resetButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.queryResult"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablePanel, "1, 3, fill, default");
		tablePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("250dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("9px"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.DEFAULT_ROWSPEC,}));
		 allSelectCheckBox  = new JCheckBox(i18n.get("foss.gui.creating.waybillEditUI.common.selectAll"));
		 allSelectCheckBox.addActionListener(this);
		 tablePanel.add(allSelectCheckBox, "1, 2,fill,top");
		initTable();
		tablePanel.add(downLoadButton, "2, 2, fill, top");
		tablePanel.add(inputButton, "4, 2, default, top");		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePanel.add(scrollPane, "1, 3, 13, 1, fill, fill");
	}

	/**
	 * 获得格式化后的开始日期
	 */
	public JXDateTimePicker getStartFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -SIX);
		cal.set(Calendar.HOUR_OF_DAY, 0); // 0点
		cal.set(Calendar.MINUTE, 0);// 0分
		cal.set(Calendar.SECOND, 0);
		formatTime.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatTime.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		formatTime.setDate(cal.getTime());
		return formatTime;
	}

	/**
	 * 获得格式化后的结束日期
	 */
	public JXDateTimePicker getEndFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, TWENTY_THREE); // 0点
		cal.set(Calendar.MINUTE, FIFTY_NINE);// 0分
		cal.set(Calendar.SECOND, FIFTY_NINE);
		formatTime.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatTime.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		formatTime.setDate(cal.getTime());
		return formatTime;
	}

	/**
	 * 初始化查询结果表格
	 * 
	 */
	private void initTable() {
		tableModel = new LTLEWaybillTableModel(null);
		table= new JXTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(FIVE);
		table.getColumnModel().getColumn(1).setPreferredWidth(TEN);
		// 刷新表格
		refreshTable(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowIndex=table.getSelectedRow();
				if(rowIndex!=-1){
					Boolean selectValue=(Boolean) table.getValueAt(rowIndex, 0);
					if(!selectValue&&allSelectCheckBox.isSelected()){
					allSelectCheckBox.setSelected(false);
					}
				}
			}			
		});
		// 初始化复制内容事件
		new CopyContent(table,i18n.get("foss.gui.chaning.waybillRFCUI.common.copy"));
	}

	
	/**
	 * 刷新table
	 * @param table
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		//ExpLinkTableMode.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}

	/**
	 * <p>(初始化运单状态 PENDING)</p>
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "unchecked"})
	private void initCombRfcStatus() {
		//全部、更改成功，更改失败
		//全部
		DataDictionaryValueVo preActive = new DataDictionaryValueVo();
		preActive.setValueCode("ALL");//全部
		preActive.setValueName(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.allChange"));
		comboWaybillStatusModel.addElement(preActive);
		
		comboWaybillStatusModel = this.getComboWaybillStatusModel();
		//更改成功
		DataDictionaryValueVo ddValuevo = new DataDictionaryValueVo();
		ddValuevo.setValueName(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.changeSuccess"));
		ddValuevo.setValueCode("RFC_SUCCESS");
		comboWaybillStatusModel.addElement(ddValuevo);

		//更改失败
		DataDictionaryValueVo ordered = new DataDictionaryValueVo();
		ordered.setValueCode("RFC_FAIL");//更改失败
		ordered.setValueName(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.changeFail"));
		comboWaybillStatusModel.addElement(ordered);
		
	}
	
	
	/**
	 * <p>(初始化任务部门)</p>
	 */
	private void initCombTaskDept() {
		//全部，当前部门
		comboWaybillTaskDept = this.getComboWaybillTaskDept();
		
		//获取当前部门名称
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		//判定当前登陆人信息是否为空
		if(user == null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
		}
		String currentDeptName = user.getEmployee().getDepartment().getName();
		//当前部门
		DataDictionaryValueVo ordered = new DataDictionaryValueVo();
		ordered.setValueCode("RFC_CURRENTDEPT");//更改失败
		ordered.setValueName(currentDeptName);
		comboWaybillTaskDept.addElement(ordered);
		
		//全部
		DataDictionaryValueVo ddValuevo = new DataDictionaryValueVo();
		ddValuevo.setValueName(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.allChange"));
		ddValuevo.setValueCode("all");
		comboWaybillTaskDept.addElement(ddValuevo);

	}

	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		salesDeptWaybillBinder = BindingFactory.getIntsance().moderateBind(SalesDeptWaybillVo.class, this, true);
		salesDeptWaybillBinder.addValidationListener(new SalesDeptWaybillValidationListner());
	}

	/**
	 * 
	 * @description：保存绑定对象
	 */
	public void registToRespository() {
		bindersMap.put("salesDeptWaybillBinder", salesDeptWaybillBinder);
	}

/*	public List<JCheckBox> getList() {
		return list;
	}

	public void setList(List<JCheckBox> list) {
		this.list = list;
	}*/
	
	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;

	}

	public Plugin getPlugin() {
		return plugin;
	}

	public IApplication getApplication() {
		return application;
	}

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	public HashMap<String, IBinder<SalesDeptWaybillVo>> getBindersMap() {
		return (HashMap<String, IBinder<SalesDeptWaybillVo>>) bindersMap;
	}

	public JXTable getTable() {
		return table;
	}

	public void setTable(JXTable table) {
		this.table = table;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	public JButton getExportButton() {
		return exportButton;
	}

	public void setExportButton(JButton exportButton) {
		this.exportButton = exportButton;
	}

	public JButton getInputButton() {
		return inputButton;
	}

	public void setInputButton(JButton inputButton) {
		this.inputButton = inputButton;
	}

	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}

	public void setSalesDeptWaybillService(ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	}

	public IBinder<SalesDeptWaybillVo> getSalesDeptWaybillBinder() {
		return salesDeptWaybillBinder;
	}

	public void setSalesDeptWaybillBinder(IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder) {
		this.salesDeptWaybillBinder = salesDeptWaybillBinder;
	}

	public void setBindersMap(Map<String, IBinder<SalesDeptWaybillVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}

	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}

	public void setSelectExportWaybillNoList(List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}

	public JTextField getTxtCreateUserCode() {
		return txtCustomerCode;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}

	public JButton getDownLoadButton() {
		return downLoadButton;
	}

	public void setDownLoadButton(JButton downLoadButton) {
		this.downLoadButton = downLoadButton;
	}

	public JTextField getTxtCustomerCode() {
		return txtCustomerCode;
	}

	public JXTextField getTxtMixNo() {
		return txtMixNo;
	}

	public DefaultComboBoxModel getComboWaybillStatusModel() {
		return comboWaybillStatusModel;
	}

	public void setComboWaybillStatusModel(
			DefaultComboBoxModel comboWaybillStatusModel) {
		this.comboWaybillStatusModel = comboWaybillStatusModel;
	}

	public void setTxtCustomerCode(JXTextField txtCustomerCode) {
		this.txtCustomerCode = txtCustomerCode;
	}

	public JXDateTimePicker getImportStartTime() {
		return importStartTime;
	}

	public void setImportStartTime(JXDateTimePicker importStartTime) {
		this.importStartTime = importStartTime;
	}

	public JXDateTimePicker getImportEndTime() {
		return importEndTime;
	}

	public void setImportEndTime(JXDateTimePicker importEndTime) {
		this.importEndTime = importEndTime;
	}

	public void setTxtMixNo(JXTextField txtMixNo) {
		this.txtMixNo = txtMixNo;
	}

	public JComboBox getCombRfcStatus() {
		return combRfcStatus;
	}

	public void setCombRfcStatus(JComboBox combRfcStatus) {
		this.combRfcStatus = combRfcStatus;
	}

	public JComboBox getCombRfcTaskDept() {
		return combRfcTaskDept;
	}

	public void setCombRfcTaskDept(JComboBox combRfcTaskDept) {
		this.combRfcTaskDept = combRfcTaskDept;
	}

	public DefaultComboBoxModel getComboWaybillTaskDept() {
		return comboWaybillTaskDept;
	}

	public void setComboWaybillTaskDept(DefaultComboBoxModel comboWaybillTaskDept) {
		this.comboWaybillTaskDept = comboWaybillTaskDept;
	}

	public LTLEWaybillTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(LTLEWaybillTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}

	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}

	public CheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public void setCheckBoxColumn(CheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}

	/** (非 Javadoc) 
	* <p>Title: actionPerformed</p> 
	* <p>Description: 全选按钮事件</p> 
	* @param e 
	* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) 
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==allSelectCheckBox){
			for (int i = 0; i < table.getRowCount(); i++) {
				if(allSelectCheckBox.isSelected()){
				table.setValueAt(true, i, 0);
			}else{
				table.setValueAt(false, i, 0);
			}
				
			}
			refreshTable(table);
		}
	}
}