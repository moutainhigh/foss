package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.collections.CollectionUtils;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.networkstatus.NetworkMonitor;
import com.deppon.foss.framework.client.component.networkstatus.NetworkStatus;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.remote.IRemoteServer;
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
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.util.FossAppPathUtil;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.common.CopyContent;
import com.deppon.foss.module.pickup.creating.client.listener.SalesDeptWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillSalesDeptSearchAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillSalesDeptExportAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillPushLabelAction;
import com.deppon.foss.module.pickup.creating.client.action.LTLEWaybillSalesDeptResetAction;
//import com.deppon.foss.module.pickup.creatingexp.client.action.ExpImportWaybillEditUIAction;
//import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillBatchImportAction;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.ToDoMsgConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 零担电子运单管理页面ui
 */
@SuppressWarnings("serial")
public class LTLEWaybillManageUI  extends JPanel implements IApplicationAware, IPluginAware,ActionListener {
	public LTLEWaybillManageUI() {
		init();
		initComboBox();
	}
	/**
	 *  日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillManageUI.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillManageUI.class);

	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	// 已开单
	private static final String WAYBILLSTATUSPCACTIVE = i18n.get("foss.gui.creating.salesDeptWaybillUI.pcActive");

	// 已补录
	private static final String WAYBILLSTATUSPENDINGACTIVE = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.bill");

	// 作废
	private static final String WAYBILL_OBSOLETE = i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end");
	
	// 中止
	private static final String WAYBILL_ABORT = i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end");
	
	// 电子面单激活失败
	private static final String EWAYBILL_ACTIVE_FAIL = i18n.get("foss.gui.creating.waibillImporter.messageDialog.failed");

	/**
	 * date
	 */
	private static final String DATE = "date";

	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";

	//运单号
	@Bind("mixNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JXTextField txtMixNo;

	//订单号
	@Bind("orderNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "订单号") })
	private JXTextField txtOrder;
	
	//快递员工号
	@Bind("createUserCode")
	private JXTextField txtCreateUserCode;
	
	//发货客户编码
	@Bind("deliveryCustomerCode")
	private JXTextField txtCustomerCode;

	// 制单时间:开始时间
	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "下单时间") })
	private JXDateTimePicker zdStartDate;

	// 制单时间:结束时间
	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;

	// 提交时间
	@Bind(value = "createStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "开单时间") })
	JXDateTimePicker fossStartDate;

	@Bind(value = "createEndTime", property = DATE)
	JXDateTimePicker fossdEndDate;
	
	// 扫描时间
	@Bind(value = "beginScanTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "扫描时间") })
	private JXDateTimePicker dateBeginScanTime;
	
	@Bind(value = "endScanTime", property = DATE)
	private JXDateTimePicker dateEndScanTime;
	
	/**
	 * 异常数据是否刷新
	 */
	private JCheckBox chkExceptMsg;

	// 运单状态
	private JComboBox combWaybillStatus;

	//运单状态model
	private DefaultComboBoxModel comboWaybillStatusModel;
	
	//标签推送状态
	private JComboBox labelStatus;
	
	//标签状态Model
	private DefaultComboBoxModel labelStatusModel;
	
	//是否扫描
	private JComboBox combIsScan;
	
	//订单来源
	private DefaultComboBoxModel orderTypeModel;
	
	//是否扫描
	private DefaultComboBoxModel isScanModel;

	//选中的列
	private LTLEWaybillCheckBoxColumn checkBoxColumn;
	
	private Plugin plugin;

	// 全选
	private JCheckBox allSelectCheckBox;
	

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillSalesDeptSearchAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillSalesDeptExportAction.class)
	private JButton exportButton = new JButton(i18n.get("foss.gui.creating.LTLEWaybillManageUI.common.export"));
	
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillPushLabelAction.class)
	private JButton pushLabelButton = new JButton(i18n.get("foss.gui.creating.LTLEWaybillManageUI.common.label"));
	
//	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillBatchImportAction.class)
	private JButton inputButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.import"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = LTLEWaybillSalesDeptResetAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));


	//选中的checkbox
	private List<JCheckBox> list;

	// 结果表
	private JXTable table = new JXTable();
	
	//tableModel
	private LTLEWaybillManageTableModel tableModel;

	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;
	
	// 绑定接口
	private IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder;

	private Map<String, IBinder<SalesDeptWaybillVo>> bindersMap = new HashMap<String, IBinder<SalesDeptWaybillVo>>();

	// 窗口应用程序
	private IApplication application;
	
	/**
	 * 显示异常数据的标签
	 */
	JLabel lblExceptMsg;

	// 选中的导出运单号
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

	private static final long ONE = 1;
	
	private static final int TWO = 2;
	
	private static final int THREE = 3;
	
	private static final int FIVE = 5;
	
	private static final long SIXTY_THOUSAND = 60000;
	
	private static final int SEVEN = 7;
	
	private static final int ONT_HUNDRED = 100;
	
	private static final int FIFTEEN = 15;
	
	private static final int NINETEEN = 19;
	
	private static final int TWENTY_THREE = 23;
	
	private static final int FIFTY = 50;
	
	private static final int FIFTY_NINE = 59;
	/**
	 * 打开UI界面初始化的界面
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
//		init();
//		initComboBox();
		bind();
		registToRespository();
		// 监听Enter
		LTLEnterKeySalesDept enterMixNo = new LTLEnterKeySalesDept(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		LTLEnterKeySalesDept enterOrder = new LTLEnterKeySalesDept(searchButton);
		txtOrder.addKeyListener(enterOrder);
		LTLEnterKeySalesDept enterCreateUserCode = new LTLEnterKeySalesDept(searchButton);
		txtCustomerCode.addKeyListener(enterCreateUserCode);
//		ExpEnterKeyESalesDept enterTable = new ExpEnterKeyESalesDept(this);
//		table.addKeyListener(enterTable);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}

	/**
	 * @author 305082
	 * 初始化界面信息
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));

		/**
		 * 查询条件的小table
		 */
		JPanel panel = new JPanel() ;
		panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.LTLEWaybillManageUI.common.queryWaybillStatus"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		/**
		 * 运单号
		 */
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");
		/**
		 * 运单号输入框
		 */
		txtMixNo = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtMixNo, "3, 3, fill, default");
		txtMixNo.setColumns(FIFTEEN);
		/**
		 *  发货客户编码
		 */
		JLabel lblCustomerCode = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label"));
		panel.add(lblCustomerCode, "5, 3, right, default");
		
		/**
		 * 发货客户编码输入框
		 */
		txtCustomerCode = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtCustomerCode, "7, 3, fill, default");
		
		/**
		 * 订单号
		 */
		JLabel labelOrder = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder"));
		panel.add(labelOrder, "9, 3, right, default");
		/**
		 * 订单号输入框
		 */
		txtOrder = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtOrder, "11, 3, fill, default");
			
		/**
		 * 起止时间
		 * 当查询时运单号和订单号都没的输入，输入了发货客户编码，或者全部为空时，需要查询选定日期的当天时间内的运单信息
		 */
		JLabel label = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.orderTime"));
		panel.add(label, "1, 5, right, default");
		/**
		 * 开始时间为当前日期的0时0分0秒
		 */
		zdStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(zdStartDate), "3, 5, fill, default");
		
		/**
		 * 分隔符
		 */
		JLabel label2 = new JLabel("至");
		panel.add(label2, "5, 5, center,  default");
		/**
		 * 结束时间为当前日期的23时59分59秒
		 */
		zdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(zdEndDate), "7, 5, fill, default");
		
		
		/**
		 *  运单状态
		 */
		JLabel waybillType = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.two"));
		panel.add(waybillType, "9, 5, right, default");
		/**
		 * 运单状态的JComboBox下拉框
		 */
		combWaybillStatus = new JComboBox();
		comboWaybillStatusModel = (DefaultComboBoxModel) combWaybillStatus.getModel();
		panel.add(combWaybillStatus, "11, 5, fill, default");
		
		/**
		 * 标签推送状态
		 */
		JLabel labelType = new JLabel("标签推送状态");
		panel.add(labelType,"13, 5, right, default");
		/**
		 * 标签推送状态的JComboBox下拉框
		 */
		labelStatus = new JComboBox();
		labelStatusModel = (DefaultComboBoxModel)labelStatus.getModel();
		panel.add(labelStatus,"15, 5, fill, default");

		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, "17, 9, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, FIVE, FIVE));
		/**
		 * 添加查询按钮
		 */
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		/**
		 * 添加重置按钮
		 */
		buttonPanel.add(resetButton);

		/**
		 * 查询结果表格table
		 */
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.queryResult"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablePanel, "1, 3, fill, default");
		tablePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
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

		/**
		 * 全选按钮
		 */
		allSelectCheckBox = new JCheckBox(i18n.get("foss.gui.creating.waybillEditUI.common.selectAll"));
		tablePanel.add(allSelectCheckBox, "1, 2, fill, top");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);
		/**
		 * 导出按钮，当没有数据时，此按钮为灰，不可操作
		 */
		tablePanel.add(exportButton, "2, 2, default, top");
		exportButton.setEnabled(false);
		/**
		 * 重新推送标签的按钮，当没有数据时，此按钮为灰，不可操作
		 */
		tablePanel.add(pushLabelButton, "4, 2, default, top");
		pushLabelButton.setEnabled(false);

		/**
		 * 调用初始化查询结果表格
		 */
		initTable();
		/**
		 * 异常数据
		 */
		lblExceptMsg = new JLabel(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+0);
		//将标签添加到页面
		tablePanel.add(lblExceptMsg, "10, 2");
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePanel.add(scrollPane, "1, 3, 12, 2, fill, fill");
		
	}

	
	
	/**
	 * 获得格式化后是时期
	 * @author fei 305082
	 * @date 2016-6-15下午5:00:47
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
	 * 获得格式化后是时期
	 * @author fei 305082
	 * @date 2016-6-15下午5:07:24
	 *
	 */
	public JXDateTimePicker getEndFormatTime(JXDateTimePicker formatTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, TWENTY_THREE); // 0点
		cal.set(Calendar.MINUTE, FIFTY_NINE);// 0分
		cal.set(Calendar.SECOND, FIFTY_NINE);// 0秒
		formatTime.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatTime.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		formatTime.setDate(cal.getTime());
		return formatTime;
	}

	/**
	 * 初始化查询结果表格
	 * @author fei 305082
	 * @date 2016-6-15下午5:09:26
	 */
	
	private void initTable() {
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setPreferredWidth(FIFTY);
			tc.setMaxWidth(FIFTY);
		}
		// 刷新表格
		tableModel = new LTLEWaybillManageTableModel(getArray(list, TWO));
		table.setModel(tableModel);
		refreshTable(table);

		// 双击已开单记录
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				/**暂时去掉双击进入开单页面，
				try {
					if (evt.getClickCount() == 2) {
						// 将UI上的行转成model中的行
						int index = table.getSelectedRow();
						if (index < 0) {
							return;
						}
						int row = table.convertRowIndexToModel(index);
						// 双击某一行以后进入界面
						Object obj = table.getModel().getValueAt(row, 1);
						
						if (obj != null) {
							if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
									|| WAYBILL_ABORT.equals(obj.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj.toString())) {
								Object object = table.getModel().getValueAt(row, 3);
								//判定数据是否为空
								if(object == null){
									MsgBox.showError(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.noExist"));
									return;
								}
								String waybillNo = object.toString();
								IWaybillService wayBillService = WaybillServiceFactory.getWaybillService();
								WaybillDto waybillDto = wayBillService.queryWaybillByNo(waybillNo);
								//增加对产品的过滤
								if(waybillDto==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillEntity().getProductCode())){
									ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
									uiAction.importWaybillEditUI(waybillDto);
								}else{
									ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
									uiAction.importWaybillEditUI(waybillDto);
//									ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
//									uiAction.importWaybillEditUI(waybillDto);
								}
							}
						}
					}
				} catch (BusinessException ee) {
					LOGGER.error("导入已开运单异常", ee);
					MsgBox.showError(i18n.get("foss.gui.creating.salesDeptWaybillUI.msgBox.importWaybillException") + ee.getMessage());
				}*/
			}
		});
		
		 //初始化复制内容事件
	    new CopyContent(table,i18n.get("foss.gui.chaning.waybillRFCUI.common.copy"));
	}

	/**
	 * Enter键监听表格
	 */
	public void tableListenter() {
		try {
			// 将UI上的行转成model中的行
			int row = table.convertRowIndexToModel(table.getSelectedRow());
			// 双击某一行以后进入界面
			Object obj = table.getModel().getValueAt(row, TWO);
			if (obj != null) {
				if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
						|| WAYBILL_ABORT.equals(obj.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj.toString())) {
					//ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = table.getModel().getValueAt(row, THREE).toString();
					IWaybillService wayBillService = WaybillServiceFactory.getWaybillService();
					WaybillDto waybillDto = wayBillService.queryWaybillByNo(waybillNo);
//					if(waybillDto==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillEntity().getProductCode())){
						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
//					}else{
//						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
//						uiAction.importWaybillEditUI(waybillDto);
//						ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
//						uiAction.importWaybillEditUI(waybillDto);
//					}
				}
			}
		} catch (BusinessException ee) {
			LOGGER.error("导入已开运单异常", ee);
			MsgBox.showError(i18n.get("foss.gui.creating.salesDeptWaybillUI.msgBox.importWaybillException") + ee.getMessage());
		}
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
		LTLEWaybillManageTableModel.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}

	/**
	 * 初始化下拉框
	 */
	public void initComboBox() {
		// 运单状态(待处理类型)
		initCombPendingType();
		combWaybillStatus.setSelectedIndex(0);
		labelStatus.setSelectedIndex(0);
	}

	/**
	 * 初始化运单状态和标签推送状态
	 * @author fei 305082
	 * @date 2016-6-15下午5:12:48
	 */
	@SuppressWarnings({ "unchecked"})
	private void initCombPendingType() {
		//运单状态
		comboWaybillStatusModel = this.getComboWaybillStatusModel();
		//标签推送状态
		labelStatusModel = this.getLabelStatusModel();
		//全部
		DataDictionaryValueVo ddValue = new DataDictionaryValueVo();
		ddValue.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		ddValue.setValueCode("all");
		labelStatusModel.addElement(ddValue);
		
		DataDictionaryValueVo success = new DataDictionaryValueVo();
		success.setValueName("推送成功");
		success.setValueCode(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS);
		labelStatusModel.addElement(success);
		
		DataDictionaryValueVo failure = new DataDictionaryValueVo();
		failure.setValueName("推送失败");
		failure.setValueCode(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL);
		labelStatusModel.addElement(failure);
		
		//所有
		DataDictionaryValueVo ddValuevo = new DataDictionaryValueVo();
		ddValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		ddValuevo.setValueCode("all");
		comboWaybillStatusModel.addElement(ddValuevo);
		
		//已开单
		DataDictionaryValueVo effective = new DataDictionaryValueVo();
		effective.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);//已开单
		effective.setValueName(i18n.get("foss.gui.creating.salesDeptWaybillUI.pcActive"));
		comboWaybillStatusModel.addElement(effective);

		//待补录
		DataDictionaryValueVo activeFailed = new DataDictionaryValueVo();
		activeFailed.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING);
		activeFailed.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPdaPending"));
		comboWaybillStatusModel.addElement(activeFailed);
		
		//已撤消
		DataDictionaryValueVo revocation = new DataDictionaryValueVo();
		revocation.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL);
		revocation.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.revocation"));
		comboWaybillStatusModel.addElement(revocation);
		//已退回
		DataDictionaryValueVo orderReturn = new DataDictionaryValueVo();
		orderReturn.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);//已退回
		orderReturn.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.return"));
		comboWaybillStatusModel.addElement(orderReturn);
	}

	/**
	 * 
	 * @author fei 305082
	 * @date 2016-6-15下午5:13:41
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		try {
			salesDeptWaybillBinder = BindingFactory.getIntsance().moderateBind(SalesDeptWaybillVo.class, this, true);
			salesDeptWaybillBinder.addValidationListener(new SalesDeptWaybillValidationListner());
		} catch (Exception e) {
			LOGGER.error("初始化焦点事件失败"+e.getMessage(),e);
		}
	}

	/**
	 * 
	 * @description：保存绑定对象
	 */
	public void registToRespository() {
		bindersMap.put("salesDeptWaybillBinder", salesDeptWaybillBinder);
	}

	/**
	 * 
	 * @author 305082
	 * 全选按钮，全选后选择所有查询出来的数据
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
	
	/**
	 * 异常刷新CheckBox
	 */
	class ExceptMsgListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Object obj = e.getSource();
			if (obj.equals(chkExceptMsg)) {
				if(chkExceptMsg.isSelected()){
					//快递与菜鸟对接项目 CN-263 只有查询异常数据时，导出功能才存在--begin
					//当前登陆用户
					UserEntity user = (UserEntity) SessionContext.getCurrentUser();
					//用户判空
					if(user != null){
						//当前登陆用户所在组织信息
						OrgAdministrativeInfoEntity orgAdministrativeInfo = user.getEmployee().getDepartment();
						if(orgAdministrativeInfo != null){
								//判断当前用户编码是否与部门负责人编码一致
								if(StringUtil.equals(user.getUserName(), orgAdministrativeInfo.getPrincipalNo())){
									exportButton.setEnabled(true);
								}
							}
						}
					startExceptMsgAutoPopThread();
				}else{
					exportButton.setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * 启动定时刷新跳出代办提醒线程
	 */
	public void startExceptMsgAutoPopThread(){
		Thread t = new Thread("EWaybillExceptMsg"){
			@SuppressWarnings("static-access")
			public void run() {
				try{
					int separate = Integer.parseInt(getToDoMsgRefreshMinutes());
					LOGGER.debug("[ auto refresh To Do Message list by "+separate+" minutes for pop dialog ]");						
					this.sleep(Long.valueOf(separate*ONE));
					while (SessionContext.getCurrentUser()!=null){
						if(isNetworkOk()){
							SwingUtilities.invokeLater(new Runnable(){
								public void run() {
									//查询参数的封装
									UserEntity user = (UserEntity) SessionContext.getCurrentUser();
									//判定当前登陆人信息是否为空
									if(user == null){
										throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
									}
									//进行方法啥的进行注入
									salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
									//查询参数的封装
									ClientEWaybillConditionDto ewaybillConditionDto = new ClientEWaybillConditionDto();
									//设置当前登陆部门
									ewaybillConditionDto.setCurrentDeptCode(user.getEmployee().getDepartment().getCode());
									List<String> waybillStatusList = new ArrayList<String>();
									//中转卸车扫描未激活
									waybillStatusList.add("UNLOAD");
									//退回
									waybillStatusList.add(DispatchOrderStatusConstants.STATUS_RETURN);
									//激活失败
									waybillStatusList.add(WaybillConstants.EWAYBILL_ACTIVE_FAIL);
									ewaybillConditionDto.setWaybillStatusList(waybillStatusList);
									//已扫描未下单
									waybillStatusList.add("SCAN");
									Calendar calStart = Calendar.getInstance();
									calStart.add(Calendar.DATE, 0);
									calStart.set(Calendar.HOUR_OF_DAY, 0); // 0点
									calStart.set(Calendar.MINUTE, 0);// 0分
									calStart.set(Calendar.SECOND, 0);// 0秒
									ewaybillConditionDto.setBeginScanTime(calStart.getTime());
									ewaybillConditionDto.setCreateStartTime(calStart.getTime());
									ewaybillConditionDto.setBillStartTime(calStart.getTime());
									Calendar calEnd = Calendar.getInstance();
									calEnd.add(Calendar.DATE, 0);
									calEnd.set(Calendar.HOUR_OF_DAY, TWENTY_THREE); // 23点
									calEnd.set(Calendar.MINUTE, FIFTY_NINE);// 59分
									calEnd.set(Calendar.SECOND, FIFTY_NINE);// 59秒
									ewaybillConditionDto.setEndScanTime(calEnd.getTime());
									ewaybillConditionDto.setCreateEndTime(calEnd.getTime());
									ewaybillConditionDto.setBillEndTime(calEnd.getTime());
									//查询结果集
									List<EWaybillSalesDepartDto> eWaybillList = new ArrayList<EWaybillSalesDepartDto>();
									if(CollectionUtils.isNotEmpty(waybillStatusList)){
										for(int i=0;i<waybillStatusList.size();i++){
											eWaybillList.addAll(salesDeptWaybillService.queryEWaybillSalesDepart(ewaybillConditionDto, waybillStatusList.get(i)));
										}
									}
									int exceptCount = 0;
									if(CollectionUtils.isNotEmpty(eWaybillList)){
										//如果查询出来的东西大于100条，停止刷新
										if(eWaybillList.size() > ONT_HUNDRED){
											chkExceptMsg.setSelected(false);
											interrupt();
										}
										exceptCount = eWaybillList.size();
									}
									//设置为具体数据
									getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+exceptCount);
									Object[][] datas = getArray(eWaybillList, 0);
									// 刷新表格
									tableModel = new LTLEWaybillManageTableModel(datas);
									getTable().setModel(tableModel);
									refreshTable(getTable());
									// 默认选中查询结果的第一行
									if (table != null && table.getRowCount() > 0) {
										table.requestFocus();
										table.setRowSelectionAllowed(true);
										table.setRowSelectionInterval(0, 0);
									}
									
							}});
							// sonar高危 - 整形乘法的结果转换为long型
							this.sleep(Long.valueOf(separate*SIXTY_THOUSAND));
						}
					}
				}catch(Exception e){
					LOGGER.error(e.getMessage());
				}
			}
		};
		Application.getExecutorService().submit(t);
	}

	/**
	 * 判定是否与服务器连接
	 */
	private boolean isNetworkOk() {
		try{
			IRemoteServer remoteServer = DefaultRemoteServiceFactory.getInstance().getRemoteServer();
			NetworkMonitor networkMonitor = remoteServer.getTransport().getNetworkMonitor();
			if(NetworkStatus.ONLINE == networkMonitor.getStatus()){
				return true;
			}
		}catch(Exception e){
			LOGGER.error("[ isNetworkOk error : "+e.toString() + " ]",e);
		}
		return false;
	}
	
	/**
	 * 从配置文件读取刷新时间间隔
	 * @return
	 */
	public String getToDoMsgRefreshMinutes(){
		Properties properties = loadConfigAsProperties();
		String minute = null;
		if(properties!=null){
			minute = properties.getProperty(ToDoMsgConstants.KEY_TODO_MSG_AUTO_REFRESH_MINUTES);
		}
		if(minute!=null){
			return minute;
		}
		else {
			return ""+ ToDoMsgConstants.VALUE_TODO_MSG_AUTO_REFRESH_MINUTES;	
		}
	}
	

	/**
	 * 
	 * 加载configuration文件
	 */
	public Properties loadConfigAsProperties(){
		Properties properties = new Properties();
		//根据sonar(不良实践 - 方法可能在关闭流时因为异常而失败)修改
		FileInputStream fi=null;
		try{
			String confFileName = FossAppPathUtil.getRemindSetFileName();
			fi = new FileInputStream(confFileName);
			properties.load(fi);
			return properties;
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}finally{
			if(fi!=null){
				try{
					fi.close();
				}catch(Exception e){ 
					LOGGER.error(e.getMessage());
				}
			}
		}
		return null;
	}

	public Object[][] getArray(List list, int flag) {
		if (CollectionUtils.isNotEmpty(list)) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			// 根据集合中的工号查询对应的姓名
			for (int i = 0; i < list.size(); i++) {
				Object pending = (Object) list.get(i);
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
	 * getRowValue结果列表
	 */
	public Object[] getRowValue(Object object, int flag) {
		LTLEWaybillQueryResultDto dto = (LTLEWaybillQueryResultDto) object;
		//判定转换过来的Dto是否为空
		if(dto == null){
			return null;
		}
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//下单时间
		String billTime = null;
		if(dto.getBillTime() != null){
			billTime = dateFormat.format(dto.getBillTime());
		}
		//扫描时间
		String scanTime = null;
		if(dto.getScanTime() != null){
			scanTime = dateFormat.format(dto.getScanTime());
		}
		//判定运单状态
		String waybillStatus = null;
		if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.salesDeptWaybillUI.pcActive");//已开单
		}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.revocation");//已撤消
		}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.return");//已退回
		}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPdaPending");//待补录
		}else{
			//其他的类型暂不知道
			waybillStatus = dto.getWaybillStatus();
		}
		String labelStatus = null;
		if (WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS.equals(dto.getLabelPushStatus())) {
			labelStatus = "推送成功";
		}else if (WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL.equals(dto.getLabelPushStatus())) {
			labelStatus = "推送失败";
		}else if (WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHED.equals(dto.getLabelPushStatus())) {
			labelStatus = "已推送";
		}else if (WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHING.equals(dto.getLabelPushStatus())) {
			labelStatus = "推送中 ";
		}else if (WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_UNPUSHED.equals(dto.getLabelPushStatus())) {
			labelStatus = "未推送 ";
		}else {
			labelStatus = dto.getLabelPushStatus();
		}
		//重新设置运单状态
		dto.setWaybillStatus(waybillStatus);
		//重新设置标签状态
		dto.setLabelPushStatus(labelStatus);
		String failReason = i18n.get(dto.getFailReason());
		if (null == failReason || failReason.equals("")) {
			failReason = dto.getFailReason();
		}
		// 获取对象成员保存至一维数组
		Object[] obj = { "",
				dto.getWaybillStatus(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.two"),//运单状态
				dto.getLabelPushStatus(),//标签推送状态
				dto.getWaybillNo(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.three"),//运单号
				dto.getOrderNo(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.four"),//订单号
				dto.getReceiveOrgName(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.six"),//收入部门
				dto.getCreateOrgName(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.five"),开单部门
				dto.getCustomerPickupOrgName(),//目地站
				billTime,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.seven"),//下单时间
				scanTime,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.nine"),//扫描时间
				dto.getDeliveryCustomerCode(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven"),//发货客户编码
				dto.getDeliveryCustomerContact(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twelve"),//发货联系人
				failReason//i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.exception")//异常原因
				};
		return obj;
	}

	/**
	 * @return the list
	 */
	public List<JCheckBox> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<JCheckBox> list) {
		this.list = list;
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
	 * @return plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

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
	 * getBindersMap
	 * 
	 * @return
	 */
	public HashMap<String, IBinder<SalesDeptWaybillVo>> getBindersMap() {
		return (HashMap<String, IBinder<SalesDeptWaybillVo>>) bindersMap;
	}

	/**
	 * getStatusComboBox
	 * 
	 * @return
	 */
	public JComboBox getStatusComboBox() {
		return combWaybillStatus;
	}

	/**
	 * setStatusComboBox
	 * 
	 * @param statusComboBox
	 */
	public void setStatusComboBox(JComboBox statusComboBox) {
		this.combWaybillStatus = statusComboBox;
	}

	/**
	 * getTable
	 * 
	 * @return
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * setTable
	 * 
	 * @param table
	 */
	public void setTable(JXTable table) {
		this.table = table;
	}

	/**
	 * @return the searchButton
	 */
	public JButton getSearchButton() {
		return searchButton;
	}

	/**
	 * @param searchButton
	 *            the searchButton to set
	 */
	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	/**
	 * @return the exportButton
	 */
	public JButton getExportButton() {
		return exportButton;
	}

	/**
	 * @param exportButton
	 *            the exportButton to set
	 */
	public void setExportButton(JButton exportButton) {
		this.exportButton = exportButton;
	}
	

	public JButton getPushLabelButton() {
		return pushLabelButton;
	}

	public void setPushLabelButton(JButton pushLabelButton) {
		this.pushLabelButton = pushLabelButton;
	}

	/**
	 * @return the inputButton
	 */
	public JButton getInputButton() {
		return inputButton;
	}

	/**
	 * @param inputButton
	 *            the inputButton to set
	 */
	public void setInputButton(JButton inputButton) {
		this.inputButton = inputButton;
	}

	/**
	 * @return the allSelectCheckBox
	 */
	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}

	/**
	 * @param allSelectCheckBox
	 *            the allSelectCheckBox to set
	 */
	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}

	/**
	 * @return the salesDeptWaybillService
	 */
	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}

	/**
	 * @param salesDeptWaybillService
	 *            the salesDeptWaybillService to set
	 */
	public void setSalesDeptWaybillService(ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	}

	/**
	 * @return the salesDeptWaybillBinder
	 */
	public IBinder<SalesDeptWaybillVo> getSalesDeptWaybillBinder() {
		return salesDeptWaybillBinder;
	}

	/**
	 * @param salesDeptWaybillBinder
	 *            the salesDeptWaybillBinder to set
	 */
	public void setSalesDeptWaybillBinder(IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder) {
		this.salesDeptWaybillBinder = salesDeptWaybillBinder;
	}

	/**
	 * @param bindersMap
	 *            the bindersMap to set
	 */
	public void setBindersMap(Map<String, IBinder<SalesDeptWaybillVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}

	/**
	 * @return the selectExportWaybillNoList
	 */
	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}

	/**
	 * @param selectExportWaybillNoList
	 *            the selectExportWaybillNoList to set
	 */
	public void setSelectExportWaybillNoList(List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}

	/**
	 * @param checkBoxColumn
	 *            the checkBoxColumn to set
	 */
	public void setCheckBoxColumn(LTLEWaybillCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}


	public JComboBox getCombWaybillStatus() {
		return combWaybillStatus;
	}



	public void setCombWaybillStatus(JComboBox combWaybillStatus) {
		this.combWaybillStatus = combWaybillStatus;
	}



	public DefaultComboBoxModel getOrderTypeModel() {
		return orderTypeModel;
	}



	public void setOrderTypeModel(DefaultComboBoxModel orderTypeModel) {
		this.orderTypeModel = orderTypeModel;
	}



	public DefaultComboBoxModel getIsScanModel() {
		return isScanModel;
	}



	public void setIsScanModel(DefaultComboBoxModel isScanModel) {
		this.isScanModel = isScanModel;
	}



	public JComboBox getCombIsScan() {
		return combIsScan;
	}



	public void setCombIsScan(JComboBox combIsScan) {
		this.combIsScan = combIsScan;
	}



	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}



	public LTLEWaybillCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public JTextField getTxtCustomerCode() {
		return txtCustomerCode;
	}

	public JXTextField getTxtMixNo() {
		return txtMixNo;
	}

	public void setTxtMixNo(JXTextField txtMixNo) {
		this.txtMixNo = txtMixNo;
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

	public JXDateTimePicker getFossStartDate() {
		return fossStartDate;
	}

	public void setFossStartDate(JXDateTimePicker fossStartDate) {
		this.fossStartDate = fossStartDate;
	}

	public JXDateTimePicker getFossdEndDate() {
		return fossdEndDate;
	}

	public void setFossdEndDate(JXDateTimePicker fossdEndDate) {
		this.fossdEndDate = fossdEndDate;
	}

	public JXDateTimePicker getDateBeginScanTime() {
		return dateBeginScanTime;
	}

	public void setDateBeginScanTime(JXDateTimePicker dateBeginScanTime) {
		this.dateBeginScanTime = dateBeginScanTime;
	}

	public JXDateTimePicker getDateEndScanTime() {
		return dateEndScanTime;
	}

	public void setDateEndScanTime(JXDateTimePicker dateEndScanTime) {
		this.dateEndScanTime = dateEndScanTime;
	}

	public DefaultComboBoxModel getComboWaybillStatusModel() {
		return comboWaybillStatusModel;
	}

	public void setComboWaybillStatusModel(
			DefaultComboBoxModel comboWaybillStatusModel) {
		this.comboWaybillStatusModel = comboWaybillStatusModel;
	}
	
	public JComboBox getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(JComboBox labelStatus) {
		this.labelStatus = labelStatus;
	}

	public DefaultComboBoxModel getLabelStatusModel() {
		return labelStatusModel;
	}

	public void setLabelStatusModel(DefaultComboBoxModel labelStatusModel) {
		this.labelStatusModel = labelStatusModel;
	}

	public JLabel getLblExceptMsg() {
		return lblExceptMsg;
	}

	public void setLblExceptMsg(JLabel lblExceptMsg) {
		this.lblExceptMsg = lblExceptMsg;
	}

	public void setTxtOrder(JXTextField txtOrder) {
		this.txtOrder = txtOrder;
	}
	
	public JXTextField getTxtCreateUserCode() {
		return txtCreateUserCode;
	}
	
	public void setTxtCreateUserCode(JXTextField txtCreateUserCode) {
		this.txtCreateUserCode = txtCreateUserCode;
	}

	public void setTxtCustomerCode(JXTextField txtCustomerCode) {
		this.txtCustomerCode = txtCustomerCode;
	}

	public JCheckBox getChkExceptMsg() {
		return chkExceptMsg;
	}

	public void setChkExceptMsg(JCheckBox chkExceptMsg) {
		this.chkExceptMsg = chkExceptMsg;
	}

	/**
	 * @return the txtOrder
	 */
	public JTextField getTxtOrder() {
		return txtOrder;
	}

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