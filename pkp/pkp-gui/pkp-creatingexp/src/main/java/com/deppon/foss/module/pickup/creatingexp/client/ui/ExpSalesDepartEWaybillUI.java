package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
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
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.util.FossAppPathUtil;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.listener.SalesDeptWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillBatchImportAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillSalesDeptExportAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillSalesDeptResetAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillSalesDeptSearchAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpEWaybillSalesDeptSearchSortAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.CopyContent;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.ToDoMsgConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * <p>电子面单查询界面UI</p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-22 10:42:05
 */
@SuppressWarnings("rawtypes")
public class ExpSalesDepartEWaybillUI  extends JPanel implements IApplicationAware, IPluginAware  {
	public ExpSalesDepartEWaybillUI() {
		init();
	}
	/**
	 *  日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpSalesDepartEWaybillUI.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpSalesDepartEWaybillUI.class);

	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	// 已开单
	private static final String WAYBILLSTATUSPCACTIVE = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPcActive");

	// 已补录
	private static final String WAYBILLSTATUSPENDINGACTIVE = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPcActive");

	// 作废
	private static final String WAYBILL_OBSOLETE = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillAbort");
	
	// 中止
	private static final String WAYBILL_ABORT = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillAbort");
	
	// 电子面单激活失败
	private static final String EWAYBILL_ACTIVE_FAIL = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillUnactiveFail");

	/**
	 * date
	 */
	private static final String DATE = "date";

	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private static final int SEVEN = 7;

	private static final int THREE = 3;

	private static final int FIFTEEN = 15;

	private static final int NINETEEN = 19;

	private static final int FIVE = 5;

	protected static final int NUM_60000 = 60000;

	//运单号
	@Bind("mixNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JXTextField txtMixNo;
	
	//快递员工号
	@Bind("createUserCode")
	private JXTextField txtCreateUserCode;
	
	//发货客户编码
	@Bind("deliveryCustomerCode")
	private JXTextField txtCustomerCode;
	
	//运单号
	private JXTextField txtMixNo1;
	//快递员工号
	private JXTextField txtCreateUserCode1;
	//发货客户编码
	private JXTextField txtCustomerCode1;

	// 制单时间:开始时间
	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "下单时间") })
	private JXDateTimePicker zdStartDate;

	// 制单时间:结束时间
	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;
	// 制单时间:开始时间
	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "下单时间") })
	private JXDateTimePicker zdStartDate1;
	
	// 制单时间:结束时间
	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate1;

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
	
	//订单来源
	private JComboBox comboOrderChannel;

	//是否扫描
	private JComboBox combIsScan;
	
	//运单状态model
	private DefaultComboBoxModel comboWaybillStatusModel;
	
	//订单来源
	private DefaultComboBoxModel orderTypeModel;
	
	//是否扫描
	private DefaultComboBoxModel isScanModel;

	//选中的列
	private ExpEWaybillCheckBoxColumn checkBoxColumn;
	
	private Plugin plugin;

	// 全选
	private JCheckBox allSelectCheckBox;

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillSalesDeptSearchAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
	
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillSalesDeptSearchSortAction.class)
	private JButton searchButton2 = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillSalesDeptExportAction.class)
	private JButton exportButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.export"));
	
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillBatchImportAction.class)
	private JButton inputButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.import"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpEWaybillSalesDeptResetAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));


	//选中的checkbox
	private List<JCheckBox> list;

	// 结果表
	private JXTable table = new JXTable();
	// 分拣记录结果表
	private JXTable sortTable = new JXTable();
	
	//tableModel
	private ExpEWaybillTableMode tableModel;
	//tableModel2
	private ExpEWaybillSortTableMode tableModel2;

	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;
	
	private IWaybillService waybillService;

	// 绑定接口
	private IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder;

	private Map<String, IBinder<SalesDeptWaybillVo>> bindersMap = new HashMap<String, IBinder<SalesDeptWaybillVo>>();

	// 窗口应用程序
	private IApplication application;
	
	/**
	 * 显示异常数据的标签
	 */
//	JLabel lblExceptMsg;

	// 选中的导出运单号
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

	/**
	 * 打开UI界面初始化的界面
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-29 17:16:42
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
		ExpEnterKeySalesDept enterMixNo = new ExpEnterKeySalesDept(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		ExpEnterKeySalesDept enterCreateUserCode = new ExpEnterKeySalesDept(searchButton);
		txtCustomerCode.addKeyListener(enterCreateUserCode);
//		ExpEnterKeyESalesDept enterTable = new ExpEnterKeyESalesDept(this);
//		table.addKeyListener(enterTable);
		
		ExpEnterKeySalesDept enterMixNo1 = new ExpEnterKeySalesDept(searchButton2);
		txtMixNo1.addKeyListener(enterMixNo1);
		ExpEnterKeySalesDept enterCreateUserCode1 = new ExpEnterKeySalesDept(searchButton2);
		txtCustomerCode1.addKeyListener(enterCreateUserCode1);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		sortTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		sortTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}

	/**
	 * 初始化界面信息
	 */
	@SuppressWarnings("deprecation")
	private void init(){
		
		JTabbedPane  jp=new JTabbedPane(JTabbedPane.TOP) ;    //设置选项卡在坐标 
		JPanel p1=new JPanel() ;    
		JPanel p2=new JPanel() ;
		jp.add("运单状态查询", p1);
		initTable1(p1);
		initComboBox();
		jp.add("电子运单分拣查询", p2);
		initTable2(p2);
		setLayout(new BorderLayout());
		add(jp); 
		   
	}
	private void initTable1(JPanel p1) {
		p1.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel() ;
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		p1.add(panel, "1, 2, fill, default");
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
		
		// 运单号/订单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");
		
		txtMixNo = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtMixNo, "3, 3, fill, default");
		txtMixNo.setColumns(FIFTEEN);

//		JLabel labelOrder = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder"));
//		panel.add(labelOrder, "5, 3, right, default");
		
//		txtOrder = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
//		panel.add(txtOrder, "7, 3, fill, default");
		
		// 开单人
		JLabel lblCustomerCode = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label"));
		panel.add(lblCustomerCode, "5, 3, right, default");
		
		// 创建客户编码
		txtCustomerCode = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtCustomerCode, "7, 3, fill, default");
			
//		快递员工号
		JLabel lblCourier = new JLabel(i18n.get("foss.gui.creating.salesDeptEWaybillUI.expressCode"));
		panel.add(lblCourier, "9, 3, right, default");
//		工号输入框
		txtCreateUserCode = new JXTextField(i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryTips"));
		panel.add(txtCreateUserCode, "11, 3, fill, default");
		// 下单时间
//		JLabel label = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.orderTime"));
//		panel.add(label, "9, 3, right, default");
		JLabel label = new JLabel("起止时间");
		panel.add(label, "1, 5, right, default");
		
		zdStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(zdStartDate), "3, 5, fill, default");
		
		// --
		JLabel label2 = new JLabel("至");
		panel.add(label2, "5, 5, center,  default");
		
		zdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(zdEndDate), "7, 5, fill, default");
		
		//订单来源
		//JLabel lblOrderSource = new JLabel(i18n.get("foss.gui.creating.salesDeptEWaybillUI.order.orderChannel"));
		//panel.add(lblOrderSource, "5, 5, right, default");
		
		//comboOrderChannel = new JComboBox();
		//orderTypeModel = (DefaultComboBoxModel) comboOrderChannel.getModel();
		//panel.add(comboOrderChannel, "7, 5, fill, default");
		
		//JLabel label_2 = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.isScan"));
		//panel.add(label_2, "9, 5, right, default");
		
		//combIsScan = new JComboBox();
		//isScanModel = (DefaultComboBoxModel) combIsScan.getModel();
		//panel.add(combIsScan, "11, 5, fill, default");
		
//		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.activeTime"));
//		panel.add(lblNewLabel, "13, 5, right, default");
//		
//		fossStartDate = new JXDateTimePicker();
//		panel.add(getStartFormatTime(fossStartDate), "15, 5, fill, default");
//		
//		JLabel label_1 = new JLabel("--");
//		panel.add(label_1, "17, 5, center, default");
//		
//		fossdEndDate = new JXDateTimePicker();
//		panel.add(getEndFormatTime(fossdEndDate), "19, 5, fill, default");
		
		// 运单状态
		JLabel waybillType = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.two"));
		panel.add(waybillType, "9, 5, right, default");
		
		combWaybillStatus = new JComboBox();
		comboWaybillStatusModel = (DefaultComboBoxModel) combWaybillStatus.getModel();
		panel.add(combWaybillStatus, "11, 5, fill, default");
		
//		JLabel label_3 = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.scanTime"));
//		panel.add(label_3, "13, 7, right, default");
//		
//		dateBeginScanTime = new JXDateTimePicker();
//		panel.add(getStartFormatTime(dateBeginScanTime), "15, 7, fill, default");
//		
//		JLabel label_4 = new JLabel("--");
//		panel.add(label_4, "17, 7, center, default");
//		
//		dateEndScanTime = new JXDateTimePicker();
//		panel.add(getEndFormatTime(dateEndScanTime), "19, 7, fill, default");
//		异常数据
//		chkExceptMsg = new JCheckBox(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult"));
//		//默认设置为定期刷新异常数据
//		chkExceptMsg.setSelected(false);
//		ExceptMsgListener exceptListener = new ExceptMsgListener();
//		chkExceptMsg.addItemListener(exceptListener);
//		panel.add(chkExceptMsg, "17, 9");

		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, "17, 9, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(resetButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.queryResult"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		p1.add(tablePanel, "1, 3, fill, default");
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

		// 全选
		allSelectCheckBox = new JCheckBox(i18n.get("foss.gui.creating.waybillEditUI.common.selectAll"));
		tablePanel.add(allSelectCheckBox, "1, 2, fill, top");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);
		
		tablePanel.add(exportButton, "2, 2, default, top");
		//exportButton.setEnabled(false);

		initTable();
//		lblExceptMsg = new JLabel(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+0);
//		tablePanel.add(lblExceptMsg, "4, 2");
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePanel.add(scrollPane, "1, 3, 12, 2, fill, fill");
	}
	private void initTable2(JPanel p1) {
		p1.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));
		
		JPanel panel=new JPanel() ;
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		p1.add(panel, "1, 2, fill, default");
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
		
		// 运单号/订单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");
		
		txtMixNo1 = new JXTextField();
		panel.add(txtMixNo1, "3, 3, fill, default");
		txtMixNo1.setColumns(FIFTEEN);
		
		// 发货客户编码
		JLabel lblCustomerCode = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label"));
		panel.add(lblCustomerCode, "5, 3, right, default");
		
		// 发货客户编码
		txtCustomerCode1 = new JXTextField();
		panel.add(txtCustomerCode1, "7, 3, fill, default");
		
//		快递员工号
		JLabel lblCourier = new JLabel(i18n.get("foss.gui.creating.expEWaybillTableMode.column.seventeen"));
		panel.add(lblCourier, "9, 3, right, default");
//		操作人工号输入框
		txtCreateUserCode1 = new JXTextField();
		panel.add(txtCreateUserCode1, "11, 3, fill, default");
		// 下单时间
		JLabel label = new JLabel("起止时间");
		panel.add(label, "1, 5, right, default");
		zdStartDate1 = new JXDateTimePicker();
		panel.add(getSortStartFormatTime(zdStartDate1), "3, 5, fill, default");
		
		// --
		JLabel label2 = new JLabel("至");
		panel.add(label2, "5, 5, center,  default");
		
		zdEndDate1 = new JXDateTimePicker();
		panel.add(getSortEndFormatTime(zdEndDate1), "7, 5, fill, default");
		
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, "17, 9, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, FIVE, FIVE));
		buttonPanel.add(searchButton2);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, "电子运单分拣记录查询", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		p1.add(tablePanel, "1, 3, fill, default");
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
		
		initTable1();
		JScrollPane scrollPane = new JScrollPane(sortTable);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePanel.add(scrollPane, "1, 2, 12, 2, fill, fill");
	}

	
	
	/**
	 * 获得格式化后的开始日期
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 上午10:57:24
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
	public JXDateTimePicker getSortStartFormatTime(JXDateTimePicker formatTime) {
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
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 上午10:57:24
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
	public JXDateTimePicker getSortEndFormatTime(JXDateTimePicker formatTime) {
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
	 * 初始化查询结果表格
	 * 
	 */
	private void initTable() {
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setPreferredWidth(NumberConstants.NUMBER_50);
			tc.setMaxWidth(NumberConstants.NUMBER_50);
		}
		// 刷新表格
		tableModel = new ExpEWaybillTableMode(getArray(list, 2));
		table.setModel(tableModel);
		refreshTable(table);

		// 双击已开单记录
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				try {
					if (evt.getClickCount() == 2) {
						// 将UI上的行转成model中的行
						int index = table.getSelectedRow();
						if (index < 0) {
							return;
						}
						int row = table.convertRowIndexToModel(index);
						// 双击某一行以后进入界面
						Object obj = table.getModel().getValueAt(row, 2);
						if (obj != null) {
							if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
									|| WAYBILL_ABORT.equals(obj.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj.toString())) {
								Object object = table.getModel().getValueAt(row, THREE);
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
									ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
									uiAction.importWaybillEditUI(waybillDto);
								}
							}
						}
					}
				} catch (BusinessException ee) {
					LOGGER.error("导入已开运单异常", ee);
					MsgBox.showError(i18n.get("foss.gui.creating.salesDeptWaybillUI.msgBox.importWaybillException") + ee.getMessage());
				}
			}
		});
		
		 //初始化复制内容事件
	    new CopyContent(table,i18n.get("foss.gui.chaning.waybillRFCUI.common.copy"));;
	}
	/**
	 * 初始化查询结果表格
	 * 
	 */
	private void initTable1() {
		sortTable.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		TableColumnModel tcm = sortTable.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setPreferredWidth(NumberConstants.NUMBER_50);
			tc.setMaxWidth(NumberConstants.NUMBER_50);
		}
		// 刷新表格
		tableModel2 = new ExpEWaybillSortTableMode(getArray2(list));
		sortTable.setModel(tableModel2);
		refreshTable(sortTable);
		
		// 双击已开单记录
		sortTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				try {
					if (evt.getClickCount() == 2) {
						// 将UI上的行转成model中的行
						int index = sortTable.getSelectedRow();
						if (index < 0) {
							return;
						}
						int row = sortTable.convertRowIndexToModel(index);
						// 双击某一行以后进入界面
						Object obj = sortTable.getModel().getValueAt(row, 2);
						if (obj != null) {
							if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
									|| WAYBILL_ABORT.equals(obj.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj.toString())) {
								Object object = sortTable.getModel().getValueAt(row, THREE);
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
									ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
									uiAction.importWaybillEditUI(waybillDto);
								}
							}
						}
					}
				} catch (BusinessException ee) {
					LOGGER.error("导入已开运单异常", ee);
					MsgBox.showError(i18n.get("foss.gui.creating.salesDeptWaybillUI.msgBox.importWaybillException") + ee.getMessage());
				}
			}
		});
		
		//初始化复制内容事件
		new CopyContent(table,i18n.get("foss.gui.chaning.waybillRFCUI.common.copy"));;
	}

	/**
	 * Enter键监听表格
	 * @author WangQianJin
	 * @date 2013-5-20 下午1:06:17
	 */
	public void tableListenter() {
		try {
			// 将UI上的行转成model中的行
			int row = table.convertRowIndexToModel(table.getSelectedRow());
			int row2 = sortTable.convertRowIndexToModel(sortTable.getSelectedRow());
			// 双击某一行以后进入界面
			Object obj = table.getModel().getValueAt(row, 2);
			Object obj2 = sortTable.getModel().getValueAt(row2, 2);
			if (obj != null) {
				if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
						|| WAYBILL_ABORT.equals(obj.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj.toString())) {
					//ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = table.getModel().getValueAt(row, THREE).toString();
					IWaybillService wayBillService = WaybillServiceFactory.getWaybillService();
					WaybillDto waybillDto = wayBillService.queryWaybillByNo(waybillNo);
					if(waybillDto==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillEntity().getProductCode())){
						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
					}else{
						ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
					}
				}
			}
			if (obj2 != null) {
				if (WAYBILLSTATUSPCACTIVE.equals(obj2.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj2.toString()) || WAYBILL_OBSOLETE.equals(obj2.toString())
						|| WAYBILL_ABORT.equals(obj2.toString()) || EWAYBILL_ACTIVE_FAIL.equals(obj2.toString())) {
					//ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = sortTable.getModel().getValueAt(row2, THREE).toString();
					IWaybillService wayBillService = WaybillServiceFactory.getWaybillService();
					WaybillDto waybillDto = wayBillService.queryWaybillByNo(waybillNo);
					if(waybillDto==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillEntity().getProductCode())){
						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
					}else{
						ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
					}
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
		ExpEWaybillTableMode.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}

	/**
	 * 初始化下拉框
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	public void initComboBox() {
		// 运单状态(待处理类型)
		initCombPendingType();
		//订单来源
		//initOrderResource();
		// 是否已经被扫描
		//initIsScan();
		// 运单状态
		combWaybillStatus.setSelectedIndex(0);
		// 订单状态
		//订单来源
		//comboOrderChannel.setSelectedIndex(0);
		//是否扫描
		//combIsScan.setSelectedIndex(0);
	}
	
	/**
	 * 初始化是否扫描下拉框的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-29 14:56:46
	 */
	@SuppressWarnings("unchecked")
	private void initIsScan() {
		isScanModel = this.getIsScanModel();
		// 全部
		DataDictionaryValueVo allIsScan = new DataDictionaryValueVo();
		allIsScan.setValueCode("all");
		allIsScan.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		isScanModel.addElement(allIsScan);
		//是
		DataDictionaryValueVo yesIsScan = new DataDictionaryValueVo();
		yesIsScan.setValueCode(FossConstants.YES);
		yesIsScan.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.yes"));
		isScanModel.addElement(yesIsScan);
		//否
		DataDictionaryValueVo noIsScan = new DataDictionaryValueVo();
		noIsScan.setValueCode(FossConstants.NO);
		noIsScan.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.no"));
		isScanModel.addElement(noIsScan);
	}

	/**
	 * 初始化订单来源
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-29 10:56:44
	 */
	@SuppressWarnings({"unchecked" })
	private void initOrderResource() {
		waybillService = WaybillServiceFactory.getWaybillService();
		List<DataDictionaryValueEntity> list = waybillService.queryOrderType();
		DefaultComboBoxModel combOrderTypeModel = this.getOrderTypeModel();
		// 全部
		DataDictionaryValueVo allOrderType = new DataDictionaryValueVo();
		allOrderType.setValueCode("all");
		allOrderType.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		combOrderTypeModel.addElement(allOrderType);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				LOGGER.error("IllegalAccessException",e);
			} catch (InvocationTargetException e) {
				LOGGER.error("InvocationTargetException",e);
			}
			combOrderTypeModel.addElement(vo);
		}
	}

	/**
	 * <p>(初始化运单状态 PENDING)</p>
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "unchecked"})
	private void initCombPendingType() {
		comboWaybillStatusModel = this.getComboWaybillStatusModel();
		//所有
		DataDictionaryValueVo DDValuevo = new DataDictionaryValueVo();
		DDValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		DDValuevo.setValueCode("all");
		comboWaybillStatusModel.addElement(DDValuevo);

		//已下单 合并查询状态已下单和待激活，合并成未扫描
		DataDictionaryValueVo ordered = new DataDictionaryValueVo();
		ordered.setValueCode("ORDERED");//已下单
		ordered.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsUnScann"));
		comboWaybillStatusModel.addElement(ordered);
		
		//待激活
//		DataDictionaryValueVo preActive = new DataDictionaryValueVo();
//		preActive.setValueCode(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);//已开单
//		preActive.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPreactive"));
//		comboWaybillStatusModel.addElement(preActive);

		//待补录
		DataDictionaryValueVo activeFailed = new DataDictionaryValueVo();
		activeFailed.setValueCode(WaybillConstants.EWAYBILL_ACTIVE_FAIL);
		activeFailed.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPdaPending"));
		comboWaybillStatusModel.addElement(activeFailed);
		
		//已开单
		DataDictionaryValueVo effective = new DataDictionaryValueVo();
		effective.setValueCode(WaybillConstants.ACCOUNT_EFFECTIVE);//已开单
		effective.setValueName(i18n.get("foss.gui.creating.salesDeptWaybillUI.pcActive"));
		comboWaybillStatusModel.addElement(effective);
		
		//已扫描未下单
//		DataDictionaryValueVo isScaned = new DataDictionaryValueVo();
//		isScaned.setValueCode("SCAN");//已扫描未下单
//		isScaned.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsScanned"));
//		comboWaybillStatusModel.addElement(isScaned);
		
		//已退回 合并查询状态已扫描未下单和已退回，合并成已退回
		DataDictionaryValueVo orderReturn = new DataDictionaryValueVo();
		orderReturn.setValueCode(DispatchOrderStatusConstants.STATUS_RETURN);//已退回
		orderReturn.setValueName(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillOrderReturn"));
		comboWaybillStatusModel.addElement(orderReturn);
	}

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
	 * @date 2012-7-19
	 * @author yangtong
	 */
	public void registToRespository() {
		bindersMap.put("salesDeptWaybillBinder", salesDeptWaybillBinder);
	}

	/**
	 * 全选
	 * @author shixw
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
	 * 异常刷新CheckBox
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-23 11:26:15
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
							//快递与菜鸟对接项目CN-285 只有部门负责人可以导出异常数据
								if(StringUtil.equals(user.getUserName(), orgAdministrativeInfo.getPrincipalNo())){
									exportButton.setEnabled(true);
								}
							}
						}
					//快递与菜鸟对接项目 CN-263--end
					startExceptMsgAutoPopThread();
				}else{
					//exportButton.setEnabled(false);
				}
			}
		}
	};
	
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
					this.sleep(Long.valueOf(separate*1));
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
									calEnd.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); // 23点
									calEnd.set(Calendar.MINUTE, NumberConstants.NUMBER_59);// 59分
									calEnd.set(Calendar.SECOND, NumberConstants.NUMBER_59);// 59秒
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
										if(eWaybillList.size() > NumberConstants.NUMBER_100){
//											chkExceptMsg.setSelected(false);
											interrupt();
										}
										exceptCount = eWaybillList.size();
									}
									//设置为具体数据
//									getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+exceptCount);
									Object[][] datas = getArray(eWaybillList, 0);
									// 刷新表格
									tableModel = new ExpEWaybillTableMode(datas);
									getTable().setModel(tableModel);

									/*ExpEWaybillCheckBoxColumn checkColumn = new ExpEWaybillCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.select")), ui);
									*/
									/*new ExpEWaybillPendingButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), this, tableModel);
									List<JCheckBox> list = checkColumn.getRenderButtonList();
									ui.setList(list);
									ui.setCheckBoxColumn(checkColumn);*/
									
									//设置发货客户的大客户标记
									/*new ExpEWaybillDeliveryBigCustomerColumn(table.getColumn(i18n.get("foss.gui.creating.linkTableMode.column.deliveryCustomer")),ui,tableModel,datas);
									
									new ExpEWaybillPendingButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), ui, tableModel);*/
									refreshTable(getTable());
									// 默认选中查询结果的第一行
									if (table != null && table.getRowCount() > 0) {
										table.requestFocus();
										table.setRowSelectionAllowed(true);
										table.setRowSelectionInterval(0, 0);
									}
									
							}});
							// sonar高危 - 整形乘法的结果转换为long型
							this.sleep(Long.valueOf(separate * NUM_60000));
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
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-5 19:16:20
	 * @return
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
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public Properties loadConfigAsProperties(){
		Properties _Properties = new Properties();
		//根据sonar(不良实践 - 方法可能在关闭流时因为异常而失败)修改
		FileInputStream fi=null;
		try{
			String confFileName = FossAppPathUtil.getRemindSetFileName();
			fi = new FileInputStream(confFileName);
			_Properties.load(fi);
			return _Properties;
		}catch(Exception e){
			
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
//去除无效循环，提高程序运行
//				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
//				}
			}
			return objtemp;
		} else {
			return null;
		}
	}
	/**
	 * 封装分拣查询数据到二维数组
	 * @param list 分拣查询数据
	 * @return 封装好的数据
	 */
	public Object[][] getArray2(List list) {
		if (CollectionUtils.isNotEmpty(list)) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			// 封装数据到二维数据以在前台展示
			for (int i = 0; i < list.size(); i++) {
				Object pending = (Object) list.get(i);
				q = getRowValue2(pending);
				//封装数据
				objtemp[i] = q;
			}
			return objtemp;
		} else {
			return null;
		}
	}

	/**
	 * getRowValue结果列表
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-15 14:15:44
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object, int flag) {
		EWaybillSalesDepartDto dto = (EWaybillSalesDepartDto) object;
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
		//Foss激活时间 
//		String createTime = null;
//		if(dto.getCreateTime() != null){
//			//待补录状态的电子运单开单时间（FOSS生成正式运单时间）为空
//			if(WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(dto.getWaybillStatus())){
//				createTime = null;
//			}else{
//				createTime = dateFormat.format(dto.getCreateTime());
//			}
//		}
		//扫描时间
		String scanTime = null;
		if(dto.getScanTime() != null){
			scanTime = dateFormat.format(dto.getScanTime());
		}
		//卸车时间
//		String clerktime = null;
//		if(dto.getClerktime() != null){
//			clerktime = dateFormat.format(dto.getClerktime());
//		}
		//运单件数  修改待激活订单生成运单件数
//		Integer waybillGoodTotal = dto.getWaybillGoodTotal();
		//判定运单状态
		String waybillStatus = null;
		//已开单
		if(WaybillConstants.EFFECTIVE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPcActive");
			//已经作废
		}else if(WaybillConstants.OBSOLETE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillObsolete");
			//已经中止
		}else if(WaybillConstants.ABORTED.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillAbort");
			//已经下单
			//已下单统一显示成未扫描
		}else if("ORDERED".equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsUnScann");
			//已经扫描
			//更改已扫描未下单统一显示为已退回
		}else if(WaybillConstants.EWAYBILL_SCANED_TYPE.equals(dto.getWaybillStatus())
				||"SCAN".equals(dto.getWaybillStatus())||DispatchOrderStatusConstants.STATUS_RETURN.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillOrderReturn");
			//未扫描
		}else if(WaybillConstants.EWAYBILL_UNISSCAN_TYPE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsUnScann");
			//电子面单待激活
			//更改待激活统一显示成未扫描
		}else if(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING.equals(dto.getWaybillStatus())
				||WaybillConstants.UNACTIVE.equals(dto.getWaybillStatus())){
//			waybillGoodTotal = null;
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsUnScann");
			//电子面单激活失败
		}else if(WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPdaPending");
			//已卸车未激活
		}else if("UNLOAD".equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillOrderUnload");
			// 未处理
		}else if(DispatchOrderStatusConstants.STATUS_NONE_HANDLE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.noneHandle");
			// 已派车
		}else if(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.dispatchVehicle");
			// PDA接收
		}else if(DispatchOrderStatusConstants.STATUS_PDA_ACCEPT.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.pdaaccept");
			// 接货中
		}else if(DispatchOrderStatusConstants.STATUS_PICKUPING.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.pickuping");
			// 待改接
		}else if(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.againPickUp");
			// 揽货失败
		}else if(DispatchOrderStatusConstants.STATUS_PICKUP_FAILURE.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.pickUpFailure");
			// 已开单
		}else if(DispatchOrderStatusConstants.STATUS_WAYBILL.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.waybill");
			// 已撤销
		}else if(DispatchOrderStatusConstants.STATUS_REVOCATION.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.revocation");
			// 已作废
		}else if(DispatchOrderStatusConstants.STATUS_CANCEL.equals(dto.getWaybillStatus())){
			waybillStatus = i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.cancel");
		}else{
			//其他的类型臣妾不知道啊
			waybillStatus = dto.getWaybillStatus();
		}
		String orderReturn = null;
		//设置订单退回原因
		if(StringUtils.isNotEmpty(dto.getOrderReturnReason())){
			LOGGER.info(dto.getOrderReturnReason());
			LOGGER.info(i18n.get(dto.getOrderReturnReason()));
			orderReturn = i18n.get(dto.getOrderReturnReason());
			if(StringUtils.isEmpty(orderReturn)){
				orderReturn = dto.getOrderReturnReason();
			}
		}
		//设置订单激活失败原因
//		String createFailReason = null;
//				if(StringUtils.isNotEmpty(dto.getCreateFailReason())){
//					LOGGER.info(dto.getCreateFailReason());
//					LOGGER.info(i18n.get(dto.getCreateFailReason()));
//					createFailReason = i18n.get(dto.getCreateFailReason());
//					if(StringUtils.isEmpty(createFailReason)){
//						createFailReason = dto.getCreateFailReason();
//					}
//				}
		//订单重量
//		String orderWeight = "";
//		if(dto.getOrderWeight() != null && BigDecimal.ZERO.compareTo(dto.getOrderWeight()) <= 0){
//			orderWeight = dto.getOrderWeight().toString();
//		}else{
//			orderWeight = BigDecimal.ZERO.toString();
//		}
		//订单体积
//		String orderVolume = "";
//		if(dto.getOrderVolume() != null && BigDecimal.ZERO.compareTo(dto.getOrderVolume()) <= 0){
//			orderVolume = dto.getOrderVolume().toString();
//		}else{
//			orderVolume = BigDecimal.ZERO.toString();
//		}
		// 获取对象成员保存至一维数组
		Object[] obj = { "", "",
				waybillStatus,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.two"),//运单状态
				dto.getWaybillNo(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.three"),//单号
//				dto.getOrderNo(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.four"),//订单号
//				dto.getCreateOrgName(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.five"),开单部门
				dto.getReceiveOrgName(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.six"),收入部门
				billTime,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.seven"),//下单时间
//				createTime,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.eight"),//开单时间
				scanTime,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.nine"),//扫描时间
				dto.getDeliveryCustomerCode(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven"),//发货客户编码
				dto.getDeliveryCustomerContact(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twelve"),//发货联系人
//				dto.getReceiveCustomerContact(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.thirteen"),//收货联系人
				//dto.getDepartCity(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fourteen"),//出发城市
//				dto.getDestinationCity(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fifteen"),//到达城市
				dto.getDriverCode(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.seventeen"),//快递员工号
//				dto.getOrderChannel(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.eighteen"),//订单来源
//				dto.getProductName(),//i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"),//运输性质
				//orderWeight,//订单状态//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twenty"),//订单重量
				//dto.getWaybillWeight(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyone"),//运单重量
				//orderVolume,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentytwo"),//订单体积
				//dto.getWaybillVolume(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentythree"),//运单体积
				//dto.getOrderGoodTotal(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyfour"),//订单件数
//				waybillGoodTotal,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyfive"),//运单件数
//				dto.getPaidMethod(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentysix"),//付款方式
				//dto.getTotalFee(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyseven"),//总费用
				//dto.getCodeAmount(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyeight"),//代收货款金额
				orderReturn,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentynine")//订单退回原因
//				createFailReason,//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixty"),运单生成失败原因
//				dto.getOperateorgname(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtyone"),库存部门
//				dto.getClerkname(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtytwo"),卸车人
//				clerktime//i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtythree")卸车时间
				};
		return obj;
	}
	public Object[] getRowValue2(Object object) {
		SortingScanEntity dto = (SortingScanEntity) object;
		//判定转换过来的Dto是否为空
		if(dto == null){
			return null;
		}
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//扫描时间
		String scanTime = null;
		if(dto.getScanTime() != null){
			scanTime = dateFormat.format(dto.getScanTime());
		}
		// 获取对象成员保存至一维数组
		Object[] obj = { 
				dto.getWayBillNo(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.three"),//运单号
				dto.getDeliveryCustomerCode(),//i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven"),//发货客户编码
				dto.getOperatorCode(),//i18n.get("foss.gui.creating.expEWaybillSortTableMode.column.operatorCode"),//操作人工号
				dto.getOrgCode(),//i18n.get("foss.gui.creating.expEWaybillSortTableMode.column.sort"),//分拣中转场
				scanTime//i18n.get("foss.gui.creating.expEWaybillTableMode.column.nine"),//扫描时间
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
	public void setCheckBoxColumn(ExpEWaybillCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}


	public JComboBox getCombWaybillStatus() {
		return combWaybillStatus;
	}



	public void setCombWaybillStatus(JComboBox combWaybillStatus) {
		this.combWaybillStatus = combWaybillStatus;
	}



	public JComboBox getComboOrderChannel() {
		return comboOrderChannel;
	}



	public void setComboOrderChannel(JComboBox comboOrderChannel) {
		this.comboOrderChannel = comboOrderChannel;
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

	

	public JXTextField getTxtMixNo1() {
		return txtMixNo1;
	}

	public void setTxtMixNo1(JXTextField txtMixNo1) {
		this.txtMixNo1 = txtMixNo1;
	}


	public JXTextField getTxtCreateUserCode1() {
		return txtCreateUserCode1;
	}

	public void setTxtCreateUserCode1(JXTextField txtCreateUserCode1) {
		this.txtCreateUserCode1 = txtCreateUserCode1;
	}

	public JXTextField getTxtCustomerCode1() {
		return txtCustomerCode1;
	}

	public void setTxtCustomerCode1(JXTextField txtCustomerCode1) {
		this.txtCustomerCode1 = txtCustomerCode1;
	}

	public JXDateTimePicker getZdStartDate1() {
		return zdStartDate1;
	}

	public void setZdStartDate1(JXDateTimePicker zdStartDate1) {
		this.zdStartDate1 = zdStartDate1;
	}

	public JXDateTimePicker getZdEndDate1() {
		return zdEndDate1;
	}

	public void setZdEndDate1(JXDateTimePicker zdEndDate1) {
		this.zdEndDate1 = zdEndDate1;
	}

	public JButton getSearchButton2() {
		return searchButton2;
	}

	public void setSearchButton2(JButton searchButton2) {
		this.searchButton2 = searchButton2;
	}

	public JXTable getSortTable() {
		return sortTable;
	}

	public void setSortTable(JXTable sortTable) {
		this.sortTable = sortTable;
	}

	public ExpEWaybillCheckBoxColumn getCheckBoxColumn() {
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

//	public JLabel getLblExceptMsg() {
//		return lblExceptMsg;
//	}
//
//	public void setLblExceptMsg(JLabel lblExceptMsg) {
//		this.lblExceptMsg = lblExceptMsg;
//	}

//	public void setTxtOrder(JXTextField txtOrder) {
//		this.txtOrder = txtOrder;
//	}
	
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
//	public JTextField getTxtOrder() {
//		return txtOrder;
//	}
}