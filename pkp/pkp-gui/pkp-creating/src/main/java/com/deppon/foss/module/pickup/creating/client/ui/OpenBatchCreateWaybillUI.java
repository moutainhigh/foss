/**
 * 批量开单
 */
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.collections.CollectionUtils;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
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
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.creating.client.action.BatchCreateWaybillQueryAction;
import com.deppon.foss.module.pickup.creating.client.action.BatchWaybillCreateImportAction;
import com.deppon.foss.module.pickup.creating.client.action.BatchWaybillDeleteAllAction;
import com.deppon.foss.module.pickup.creating.client.action.BatchWaybillSubmitAction;
import com.deppon.foss.module.pickup.creating.client.action.PrintButtonBatchCreateWaybillQueryAction;
import com.deppon.foss.module.pickup.creating.client.action.PrintSignSetupButtenAction;
import com.deppon.foss.module.pickup.creating.client.listener.BatchCreateWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillBatchCreateDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;



public class OpenBatchCreateWaybillUI extends JPanel implements IApplicationAware, IPluginAware {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(OpenBatchCreateWaybillUI.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(OpenBatchCreateWaybillUI.class);
	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * date
	 */
	private static final String DATE = "date";

	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";
	
	private static final String PREVIEWPNG = "preview.png";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_125 = 125;
	
	private EditorConfig editConfig;
	OpenBatchCreateWaybillUI ui;
	// 开单时间:开始时间

	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "制单时间") })
	private JXDateTimePicker zdStartDate;

	// 开单时间:结束时间

	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;
	// 开单状态
	private JComboBox waybillStatus;
	
	//打印状态
	private JComboBox printWaybillStatus;
	//查询
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = BatchCreateWaybillQueryAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.query"));
	
	//批量打印
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = PrintButtonBatchCreateWaybillQueryAction.class)
	JButton printButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.queryprint"));
	
	//打印设置
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = PrintSignSetupButtenAction.class)
	private JButton printViewButton = new JButton(i18n.get("foss.gui.creating.printSignUI.button.printSetup"));
	
	//添加
	private JButton addButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.add"), ImageUtil.getImageIcon(
			this.getClass(), "new.png"));
	
	//导入
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = BatchWaybillCreateImportAction.class)
	private JButton importButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.import"));	
	
	//提交
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "", type = BatchWaybillSubmitAction.class)
	private JButton submitButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.submit"));
	//清空
	@ButtonAction(icon = IconConstants.DELETE, shortcutKey = "", type = BatchWaybillDeleteAllAction.class)
	private JButton cleanUpButton = new JButton(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.cleanUpButton"));
	
	private Plugin plugin;
	private DeliveryBigCustomerColumn deliveryBigCustomerColumn;
	// 全选
	private JCheckBox allSelectCheckBox;

	// 结果表
	private JXTable table = new JXTable();

	// 表外滚动条
	private JScrollPane scrollPane;

	private JPanel tablePanel;

	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	private IWaybillService waybillService;

	// 绑定接口
	private IBinder<WaybillBatchCreateDto> salesDeptWaybillBinder;

	private Map<String, IBinder<WaybillBatchCreateDto>> bindersMap = new HashMap<String, IBinder<WaybillBatchCreateDto>>();

	// 窗口应用程序
	private IApplication application;
	

	/**
	 * 选中的导出运单号
	 */
	private List<String> selectExportWaybillNoList = new ArrayList<String>();
	
	private String waybillPringType;

	/**
	 * 选中的导出id
	 */
	private List<String> selectIdList = new ArrayList<String>();

	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;

	/**
	 * 选中的列
	 */
	private BatchCheckBoxColumn checkBoxColumn;
	
	/**
	 * 构造方法
	 */
	public void openUI() {
		init();
		initComboBox();
		bind();
		registToRespository();
	
		waybillService = WaybillServiceFactory.getWaybillService();
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"),FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "1, 2, left, default");
		FormLayout flpanel = new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:max(145px;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(33dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(96dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("max(6dlu;default)"),
				FormFactory.GLUE_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		flpanel.setColumnGroups(new int[][]{new int[]{NumberConstants.NUMBER_15, NumberConstants.NUMBER_11, NumberConstants.NUMBER_7, NumberConstants.NUMBER_3}});
		panel.setLayout(flpanel);

		zdStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(zdStartDate), "3, 3, 2, 1, fill, fill");

		zdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(zdEndDate), "6, 3, 2, 1, fill, default");

		// 开单日期
		JLabel lblFoss = new JLabel(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.waybillDate"));
		panel.add(lblFoss, "1, 3, right, default");


		JLabel label8 = new JLabel("--");
		panel.add(label8, "5, 3");


		// 开单状态
		JLabel label = new JLabel(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.waybillStatus"));
		label.setToolTipText("");
		panel.add(label, "9, 3, right, default");

		waybillStatus = new JComboBox();
		panel.add(waybillStatus, "11, 3, fill, default");
		
		//打印状态
		JLabel labePrint = new JLabel(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.PrintWaybillStatus"));
		label.setToolTipText("");
		panel.add(labePrint, "13, 3, right, default");
		
		printWaybillStatus = new JComboBox();
		panel.add(printWaybillStatus, "15, 3, fill, default");
		
		importButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(importButton, "7, 5, right, default");
		panel.add(searchButton, "9, 5, center, default");
		panel.add(addButton, "11, 5, left, default");
		panel.add(printButton, "13, 5, left, default");
		panel.add(printViewButton, "15, 5, left, default");
		addButton.setHorizontalAlignment(SwingConstants.RIGHT);
		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.queryResult"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablePanel, "1, 3, fill, default");
		tablePanel
				.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("9px"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		// 全选
		allSelectCheckBox = new JCheckBox(i18n.get("foss.gui.creating.OpenBatchCreateWaybillUI.selectAll"));
		tablePanel.add(allSelectCheckBox, "1, 2, fill, top");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);

		initTable();
		//SelectListener selectListener = new SelectListener();
		//table.addMouseListener(selectListener);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JPanel tableDown = new JPanel();
		add(tableDown, "1, 4, fill, default");
		tableDown.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("9px"),}));
		tableDown.add(submitButton, "4,2,left, default");
		tableDown.add(cleanUpButton, "6,2,left, default");
		this.initListener(ui);
	}

	/**
	 * 获得格式化后的开始日期
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 上午10:57:24
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
			if(i==0 || i==1){
				tc.setPreferredWidth(NumberConstants.NUMBER_40);
				tc.setMaxWidth(NumberConstants.NUMBER_40);
			}
			if(i == 2){
				tc.setPreferredWidth(NUM_125);
				tc.setMaxWidth(NUM_125);
			}else{
				tc.setPreferredWidth(NumberConstants.NUMBER_80);
				tc.setMaxWidth(NumberConstants.NUMBER_80);
			}
			
		}

		// 刷新表格
		BatchCreateWaybillTableModel tableModel = new BatchCreateWaybillTableModel(getArray(list));
		table.setModel(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		refreshTable(table);

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
		BatchCreateWaybillTableModel.adjustColumnPreferredWidths(table);
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
	 * 
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	public void initComboBox() {
		// 运单状态
		initWaybillStatus();
		//打印状态
		initPrintWaybillStatus();
		// 运单状态(待处理类型)
		initCombPendingType();
		// 收货部门
		initCombReceiveOrg();
		// 制单部门
		initCombCreateOrg();
	}

	/**
	 * <p>
	 * (初始化产品类型)运输性质
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initWaybillStatus() {

		DefaultComboBoxModel productTypeModel = new DefaultComboBoxModel();
		List<DataDictionaryValueEntity> list = new ArrayList<DataDictionaryValueEntity>();
		DataDictionaryValueEntity datea = new DataDictionaryValueEntity();
		datea.setId(String.valueOf(1));
		datea.setActive(FossConstants.YES);
		datea.setValueName("已开单");
		datea.setValueCode(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE);
		DataDictionaryValueEntity dateb = new DataDictionaryValueEntity();
		dateb.setId(String.valueOf(2));
		dateb.setActive(FossConstants.YES);
		dateb.setValueName("未开单");
		dateb.setValueCode(WaybillConstants.WAYBILL_STATUS_PC_PENDING);
		list.add(datea);
		list.add(dateb);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			productTypeModel.addElement(vo);
		}
		this.waybillStatus.setModel(productTypeModel);
	}
	
	/**
	 * <p>
	 * (初始是否打印)
	 * </p>
	 * 
	 * @author 280747
	 * @date 2016-10-13 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initPrintWaybillStatus() {

		DefaultComboBoxModel productTypeModel = new DefaultComboBoxModel();
		List<DataDictionaryValueEntity> list = new ArrayList<DataDictionaryValueEntity>();
		DataDictionaryValueEntity datea = new DataDictionaryValueEntity();
		datea.setId(String.valueOf(1));
		datea.setActive(FossConstants.YES);
		datea.setValueName("已打印");
		datea.setValueCode("1");
		DataDictionaryValueEntity dateb = new DataDictionaryValueEntity();
		dateb.setId(String.valueOf(2));
		dateb.setActive(FossConstants.YES);
		dateb.setValueName("未打印");
		datea.setValueCode("2");
		list.add(datea);
		list.add(dateb);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			productTypeModel.addElement(vo);
		}
		this.printWaybillStatus.setModel(productTypeModel);
	}

	/**
	 * <p>
	 * (初始化运单状态 PENDING)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	private void initCombPendingType() {
		salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
		List<DataDictionaryValueEntity> list = salesDeptWaybillService.queryPendingType();
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		/* 附加信息 */
		DataDictionaryValueVo ddValuevo = new DataDictionaryValueVo();
		ddValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		ddValuevo.setValueCode("all");
		comboModel.addElement(ddValuevo);

		for (int i = list.size(); i > 0; i--) {
			DataDictionaryValueEntity dataDictionary = (DataDictionaryValueEntity) list.get(i - 1);
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			comboModel.addElement(vo);
		}
		
	}

	/**
	 * <p>
	 * (初始化收货部门)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	private void initCombReceiveOrg() {
		/**
		 * 如果是非集中接送货,收货部门为当前部门，制单部门包括当前部门和集中接送货部门.
		 * 如果是集中接送货,制单部门为本部门,收货部门为旗下所有营业部
		 * 
		 * 设置收货部门： 1）当前部门为开单查询组：开单组所包含的收货部门 2）当前部门非开单查询组：当前部门
		 * 
		 * 设置制单部门： 1）当前部门为开单查询组：当前部门 2）当前部门非开单查询组：开单组 + 当前部门
		 */
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 获得当前部门所属组织
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();

		// 若为开单组，
		if (FossConstants.YES.equals(dept.getBillingGroup())) {
			// 自有部门服务
			salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
			// 根据部门编码查询自有网点
			
			//Offline to Online
			IWaybillService wayBillService = WaybillServiceFactory.getWaybillService();
			List<SaleDepartmentEntity> list = wayBillService.querySaleDeptsByBillingGroupCode(StringUtil.defaultIfNull(dept.getCode()));
//			List<SaleDepartmentEntity> list = salesDeptWaybillService.querySaleDeptsByBillingGroupCode(StringUtil.defaultIfNull(dept.getCode()));
			// 集合非空判断
			if (CollectionUtils.isNotEmpty(list)) {
				// 下拉框选项
				DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
				// 若有1个以上的下拉选项，则加上全部
				if (list.size() > 1) {
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					// 值名称
					vo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
					// 值编码
					vo.setValueCode("all");
					// 增加下拉框
					comboModel.addElement(vo);
				}

				// 设置下拉框
				for (SaleDepartmentEntity saleDept : list) {
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					// 部门ID
					vo.setId(saleDept.getId());
					// 值名称
					vo.setValueName(saleDept.getName());
					// 值编码
					vo.setValueCode(saleDept.getCode());
					// 增加下拉框
					comboModel.addElement(vo);
				}

			}
		}
		// 若非开单组，
		else {
			// 下拉框选项
			DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
			// 收货部门为当前部门
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			// 部门ID
			vo.setId(dept.getId());
			// 值名称
			vo.setValueName(dept.getName());
			// 值编码
			vo.setValueCode(dept.getCode());
			// 增加下拉框
			comboModel.addElement(vo);

		}
	}

	/**
	 * 初始化制单部门
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-20 下午8:07:07
	 */
	private void initCombCreateOrg() {
		/**
		 * 如果是非集中接送货,收货部门为当前部门，制单部门包括当前部门和集中接送货部门.
		 * 如果是集中接送货,制单部门为本部门,收货部门为旗下所有营业部
		 * 
		 * 设置收货部门： 1）当前部门为开单查询组：开单组所包含的收货部门 2）当前部门非开单查询组：当前部门
		 * 
		 * 设置制单部门： 1）当前部门为开单查询组：当前部门 2）当前部门非开单查询组：开单组 + 当前部门
		 */
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 获得当前部门所属组织
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();

		// 下拉框选项
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();

		// 若非开单组，则可查询全部
		if (FossConstants.NO.equals(dept.getBillingGroup())) {
			// 增加“全部”选项
			DataDictionaryValueVo valueVo = new DataDictionaryValueVo();
			// 值名称
			valueVo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
			// 值编码
			valueVo.setValueCode("all");
			// 增加下拉框
			comboModel.addElement(valueVo);
		}

		// 制单部门为当前部门
		DataDictionaryValueVo vo = new DataDictionaryValueVo();
		// 部门ID
		vo.setId(dept.getId());
		// 值名称
		vo.setValueName(dept.getName());
		// 值编码
		vo.setValueCode(dept.getCode());
		// 增加下拉框
		comboModel.addElement(vo);



	}


	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		salesDeptWaybillBinder = BindingFactory.getIntsance().moderateBind(WaybillBatchCreateDto.class, this, true);

		salesDeptWaybillBinder.addValidationListener(new BatchCreateWaybillValidationListner());
		// salesDeptWaybillBinder.addBindingListener(new
		// SalesDeptWaybillBindingListener(this));
	}

	/**
	 * 
	 * @description：保存绑定对象
	 * @date 2012-7-19
	 * @author yangtong
	 */
	public void registToRespository() {
		bindersMap.put("salesDeptWaybillBinder", salesDeptWaybillBinder);
		// canvasMap.put("canvasBinder", canvasBinder);
	}

	/**
	 * 全选
	 * 
	 * @author shixw
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

	class SelectListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int index = table.getSelectedRow();
			if(index>-1) {
				int row = table.convertRowIndexToView(index);
				JCheckBox ch = list.get(row);
				checkBoxColumn.setRow(row);				
				if(ch.isSelected()) {
					ch.setSelected(false);
				}else {
					ch.setSelected(true);
				}
				
			}
			
		}
	}
	@SuppressWarnings({ "rawtypes" })
	public Object[][] getArray(List list) {
		if (list != null && !list.isEmpty()) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			// 根据集合中的工号查询对应的姓名
			// List<EmployeeEntity> empList = queryEmployeeByCode(list);
			for (int i = 0; i < list.size(); i++) {
				Object pending = (Object) list.get(i);
				
				q = getRowValue(pending,i);

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
	public Object[] getRowValue(Object object,int flag) {
		
		String no = flag+1+"";
		// 运单状态
		String status = "";
		// 运单号
		String waybillNo = "";
		//发货人手机
		String deliveryCustomerMobilephone = "";
		//发货人电话
		String deliveryCustomerPhone = "";
		//发货人名称
		String deliveryCustomerName = "";
		//发货人地址
		String deliveryCustomerAddress = "";
		//收货人手机
		String receiveCustomerMobilephone = "";
		//收货人电话
		String receiveCustomerPhone = "";
		//收货人地址
	    String receiveCustomerAddress = "";
	    //运输性质
	    String transportProperies = "";
	    //提货方式
	    String receiveMethod = "";//内部带货自提,内部带货送货
	    //目的站
	    String trgetName = "";
	    //货物名称
	    String goodsName = "";
	    //件数
	    String totalNum = "";
	    //重量
	    String totalWeight = "";
	    //总体积
	    String totalVolume = "";
	    //包装
	    String goodsPackage = "";
	    //货物类型
	    String goodsTypeCode = "";
	    //对内备注
	    String inRemark = "" ;
	    //保价声明价值
	    String insuranceAmount  = ""; 
	    //代收货款
	    String codAmount = "";
	    //付款方式
	    String paidMethod = "";
		//运单补录状态 (隐藏)
	    String pendingType="";
	  //通用集合
  		Map<String,String> map =null;
  		if(null==map){
  			map = new HashMap<String,String>();
  			List<DataDictionaryValueEntity>  paidMethodList =  waybillService.selectProductCodeDataDictValue(WaybillConstants.PAYMENT_MODE);
  			for(DataDictionaryValueEntity dictionary:paidMethodList){
  	  			map.put(dictionary.getValueCode(),dictionary.getValueName());
  	  		}
  		}
		
		if (object instanceof WaybillPendingEntity) {
			WaybillPendingEntity vo = (WaybillPendingEntity) object;
			status = vo.getPendingType();
			waybillPringType = vo.getPendingType();
			if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(status)){
				status="已开单";
			}else if(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(status)){
				status="运单暂存";
			}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(status)){
				status="运单异常";
			}
			waybillNo =vo.getWaybillNo();
			deliveryCustomerMobilephone = StringUtil.defaultIfNull(vo.getDeliveryCustomerMobilephone());
			deliveryCustomerPhone = StringUtil.defaultIfNull(vo.getDeliveryCustomerPhone());
			deliveryCustomerName = StringUtil.defaultIfNull(vo.getDeliveryCustomerContact());
			deliveryCustomerAddress = StringUtil.defaultIfNull(vo.getDeliveryCustomerAddress());
			receiveCustomerMobilephone = StringUtil.defaultIfNull(vo.getReceiveCustomerMobilephone());
			receiveCustomerPhone = StringUtil.defaultIfNull(vo.getReceiveCustomerPhone());
			receiveCustomerAddress = StringUtil.defaultIfNull(vo.getReceiveCustomerAddress());
			
			if(map.get(vo.getProductId())!=null){
				transportProperies=map.get(vo.getProductId());
			}else{
				ProductEntity product =waybillService.queryTransTypeById(vo.getProductId());
				transportProperies=product.getName();
				map.put(vo.getProductId(), product.getName());
			}
			if(map.get(vo.getReceiveMethod())!=null){
				receiveMethod = map.get(vo.getReceiveMethod());
			}else{
				DataDictionaryValueEntity dataDictionaryValueEntity = waybillService.queryDataDictoryValueByCode(WaybillConstants.PICKUP_GOODS_HIGHWAYS, vo.getReceiveMethod());
				receiveMethod = dataDictionaryValueEntity.getValueName();
				map.put(vo.getReceiveMethod(), receiveMethod);
				
			}
			if(map.get(vo.getCustomerPickupOrgCode())!=null){
				trgetName = map.get(vo.getCustomerPickupOrgCode());
			}else{
				SaleDepartmentEntity dept = waybillService.querySaleDepartmentByCode(vo.getCustomerPickupOrgCode());
				trgetName=dept.getName();
				map.put(vo.getCustomerPickupOrgCode(), dept.getName());
			}
			
			goodsName = StringUtil.defaultIfNull(vo.getGoodsName());
			totalNum = vo.getGoodsQtyTotal() == null ? "" : vo.getGoodsQtyTotal().toString();
			totalWeight = vo.getGoodsWeightTotal()==null ? "" :vo.getGoodsWeightTotal().toString();
			totalVolume = vo.getGoodsVolumeTotal()==null ? "" : vo.getGoodsVolumeTotal().toString();
			goodsPackage = StringUtil.defaultIfNull(vo.getGoodsPackage());
			goodsTypeCode  = StringUtil.defaultIfNull(vo.getGoodsTypeCode());
			inRemark = StringUtil.defaultIfNull(vo.getInnerNotes());
			insuranceAmount = vo.getInsuranceAmount() == null ? "" : vo.getInsuranceAmount().toString();
			codAmount  = vo.getCodAmount() == null ? "" : vo.getCodAmount().toString();
			paidMethod  = map.get(vo.getPaidMethod());
			pendingType= vo.getPendingType();
		}else if(object instanceof WaybillEntity){
			WaybillEntity vo = (WaybillEntity) object;
			waybillPringType = vo.getPendingType();
			status = vo.getPendingType();
			if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(status)){
				status="已开单";
			}else if(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(status)){
				status="运单暂存";
			}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(status)){
				status="运单异常";
			}
			waybillNo =vo.getWaybillNo();
			deliveryCustomerMobilephone = StringUtil.defaultIfNull(vo.getDeliveryCustomerMobilephone());
			deliveryCustomerPhone = StringUtil.defaultIfNull(vo.getDeliveryCustomerPhone());
			deliveryCustomerName = StringUtil.defaultIfNull(vo.getDeliveryCustomerContact());
			deliveryCustomerAddress = StringUtil.defaultIfNull(vo.getDeliveryCustomerAddress());
			receiveCustomerMobilephone = StringUtil.defaultIfNull(vo.getReceiveCustomerMobilephone());
			receiveCustomerPhone = StringUtil.defaultIfNull(vo.getReceiveCustomerPhone());
			receiveCustomerAddress = StringUtil.defaultIfNull(vo.getReceiveCustomerAddress());
			if(map.get(vo.getProductId())!=null){
				transportProperies=map.get(vo.getProductId());
			}else{
				ProductEntity product =waybillService.queryTransTypeById(vo.getProductId());
				transportProperies=product.getName();
				map.put(vo.getProductId(), product.getName());
			}
			if(map.get(vo.getReceiveMethod())!=null){
				receiveMethod = map.get(vo.getReceiveMethod());
			}else{
				DataDictionaryValueEntity dataDictionaryValueEntity = waybillService.queryDataDictoryValueByCode(WaybillConstants.PICKUP_GOODS_HIGHWAYS, vo.getReceiveMethod());
				receiveMethod = dataDictionaryValueEntity.getValueName();
				map.put(vo.getReceiveMethod(), receiveMethod);
				
			}
			if(map.get(vo.getCustomerPickupOrgCode())!=null){
				trgetName = map.get(vo.getCustomerPickupOrgCode());
			}else{
				SaleDepartmentEntity dept = waybillService.querySaleDepartmentByCode(vo.getCustomerPickupOrgCode());
				trgetName=dept.getName();
				map.put(vo.getCustomerPickupOrgCode(), dept.getName());
			}
			goodsName = StringUtil.defaultIfNull(vo.getGoodsName());
			totalNum = vo.getGoodsQtyTotal() == null ? "" : vo.getGoodsQtyTotal().toString();
			totalWeight = vo.getGoodsWeightTotal()==null ? "" :vo.getGoodsWeightTotal().toString();
			totalVolume = vo.getGoodsVolumeTotal()==null ? "" : vo.getGoodsVolumeTotal().toString();
			goodsPackage = StringUtil.defaultIfNull(vo.getGoodsPackage());
			goodsTypeCode  = StringUtil.defaultIfNull(vo.getGoodsTypeCode());
			inRemark = StringUtil.defaultIfNull(vo.getInnerNotes());
			insuranceAmount = vo.getInsuranceAmount() == null ? "" : vo.getInsuranceAmount().toString();
			codAmount  = vo.getCodAmount() == null ? "" : vo.getCodAmount().toString();
			paidMethod  = map.get(vo.getPaidMethod());
			pendingType= vo.getPendingType();
		}

		
		// 获取对象成员保存至一维数组
		Object[] obj = {"",no,"",status,waybillNo,deliveryCustomerMobilephone,deliveryCustomerPhone,deliveryCustomerName,deliveryCustomerAddress,
				receiveCustomerMobilephone,receiveCustomerPhone,receiveCustomerAddress,transportProperies,receiveMethod,trgetName,
				goodsName,totalNum,totalWeight,goodsPackage,totalVolume,goodsTypeCode,inRemark,insuranceAmount,codAmount,paidMethod,pendingType};
		return obj;
	}

	/**
	 * 将list集合中的工号转换为名称 由于t_bas_employee没有在本地下载数据表行列 因此需要判断： 若是离线，则直接还是用工号；
	 * 若为在线，则将所有list表中工号一次性传至后台查询出后一次性传至前台显示（减少网络传输）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-26 下午8:18:02
	 */
	public List<EmployeeEntity> queryEmployeeByCode(List<Object> list) {
		// 集合非空判断
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// 定义员工对象集合
		List<EmployeeEntity> empList = null;
		// 定义集合收用于收集list中的工号
		List<String> codes = new ArrayList<String>();
		// 定义一个新的集合来接收去重后值
		List<String> newCodes = new ArrayList<String>();
		// 遍历集合
		for (Object object : list) {
			// 判断数据类型
			if (object instanceof WaybillPendingEntity) {
				// 转换对象
				WaybillPendingEntity pendingEntity = (WaybillPendingEntity) object;
				// 获得开单人工号
				codes.add(StringUtil.defaultIfNull(pendingEntity.getCreateUserCode()));
			} else if (object instanceof WaybillEntity) {
				// 转换对象
				WaybillEntity waybillEntity = (WaybillEntity) object;
				// 获得开单人工号
				codes.add(StringUtil.defaultIfNull(waybillEntity.getCreateUserCode()));
			} else {
				// 若为其它类型则直接退出本次循环
				continue;
			}

			// 集合去重操作
			// 利用HashSet的特性去重
			Set<String> set = new HashSet<String>();
			set.addAll(codes);
			newCodes.addAll(set);
		}
		// 查询所有工号对应的员工对象
		waybillService = WaybillServiceFactory.getWaybillService();
		//empList = waybillService.queryEmployeeByCodeList(newCodes);
		//内存溢出，性能优化
		empList = waybillService.queryEmployeeNameAndCodeByCodeList(newCodes);
		return empList;
	}

	private void initListener(OpenBatchCreateWaybillUI ui){
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				openUIActionAndReturn();
			}
		});
	}
	public WaybillEditUI openUIActionAndReturn() {
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillSaleDepartment.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");

		WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.setBatchWaybill(true);
		waybillEditUI.openUI();
		return waybillEditUI;
	}
	
	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
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
	public HashMap<String, IBinder<WaybillBatchCreateDto>> getBindersMap() {
		return (HashMap<String, IBinder<WaybillBatchCreateDto>>) bindersMap;
	}





	/**
	 * getZdStartDate
	 * 
	 * @return
	 */
	public JXDateTimePicker getZdStartDate() {
		return zdStartDate;
	}

	/**
	 * setZdStartDate
	 * 
	 * @param zdStartDate
	 */
	public void setZdStartDate(JXDateTimePicker zdStartDate) {
		this.zdStartDate = zdStartDate;
	}

	/**
	 * getZdEndDate
	 * 
	 * @return
	 */
	public JXDateTimePicker getZdEndDate() {
		return zdEndDate;
	}

	/**
	 * setZdEndDate
	 * 
	 * @param zdEndDate
	 */
	public void setZdEndDate(JXDateTimePicker zdEndDate) {
		this.zdEndDate = zdEndDate;
	}

	/**
	 * getwaybillStatus
	 * 
	 * @return
	 */
	public JComboBox getWaybillStatus() {
		return waybillStatus;
	}

	/**
	 * setwaybillStatus
	 * 
	 * @param waybillStatus
	 */
	public void setWaybillStatus(JComboBox waybillStatus) {
		this.waybillStatus = waybillStatus;
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
	 * @return the tablePanel
	 */
	public JPanel getTablePanel() {
		return tablePanel;
	}

	/**
	 * @param tablePanel
	 *            the tablePanel to set
	 */
	public void setTablePanel(JPanel tablePanel) {
		this.tablePanel = tablePanel;
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
	public IBinder<WaybillBatchCreateDto> getSalesDeptWaybillBinder() {
		return salesDeptWaybillBinder;
	}

	/**
	 * @param salesDeptWaybillBinder
	 *            the salesDeptWaybillBinder to set
	 */
	public void setSalesDeptWaybillBinder(IBinder<WaybillBatchCreateDto> salesDeptWaybillBinder) {
		this.salesDeptWaybillBinder = salesDeptWaybillBinder;
	}

	/**
	 * @param bindersMap
	 *            the bindersMap to set
	 */
	public void setBindersMap(Map<String, IBinder<WaybillBatchCreateDto>> bindersMap) {
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

	
	

	public BatchCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public void setCheckBoxColumn(BatchCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}

	/**
	 * @return the selectIdList
	 */
	public List<String> getSelectIdList() {
		return selectIdList;
	}

	/**
	 * @param selectIdList
	 *            the selectIdList to set
	 */
	public void setSelectIdList(List<String> selectIdList) {
		this.selectIdList = selectIdList;
	}

	public DeliveryBigCustomerColumn getDeliveryBigCustomerColumn() {
		return deliveryBigCustomerColumn;
	}

	public void setDeliveryBigCustomerColumn(
			DeliveryBigCustomerColumn deliveryBigCustomerColumn) {
		this.deliveryBigCustomerColumn = deliveryBigCustomerColumn;
	}

	public JButton getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(JButton submitButton) {
		this.submitButton = submitButton;
	}

	public JButton getCleanUpButton() {
		return cleanUpButton;
	}

	public void setCleanUpButton(JButton cleanUpButton) {
		this.cleanUpButton = cleanUpButton;
	}

	public JButton getPrintButton() {
		return printButton;
	}

	public void setPrintButton(JButton printButton) {
		this.printButton = printButton;
	}

	public JButton getPrintViewButton() {
		return printViewButton;
	}

	public void setPrintViewButton(JButton printViewButton) {
		this.printViewButton = printViewButton;
	}

	public String getWaybillPringType() {
		return waybillPringType;
	}

	public void setWaybillPringType(String waybillPringType) {
		this.waybillPringType = waybillPringType;
	}

	public JComboBox getPrintWaybillStatus() {
		return printWaybillStatus;
	}

	public void setPrintWaybillStatus(JComboBox printWaybillStatus) {
		this.printWaybillStatus = printWaybillStatus;
	}
	

}