package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.FossExportExcelButton;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.SearchPictureQueryAction;
import com.deppon.foss.module.pickup.creating.client.action.SearchPictureResetAction;
import com.deppon.foss.module.pickup.creating.client.listener.SearchPictureWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SearchPictureWaybillUI extends JPanel implements
		IApplicationAware, IPluginAware {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(SearchPictureWaybillUI.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(SearchPictureWaybillUI.class);

	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * date
	 */
	private static final String DATE = "date";
	
	// 15
	private static final int FIFIVETEEN = 15;

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

	private static final int ELEVEN = 11;

	private static final int FIFTEEN = 15;

	private static final int TEN = 10;

	private static final int NUM_600 = 600;

	private static final int TWENTY = 20;

	private static final int SIXTEEN = 16;

	private static final int FOURTEEN = 14;

	private static final int SEVENTEEN = 17;

	private static final int NINEETEEN = 19;

	private static final int THIRTEEN = 13;

	private static final int FOUR = 4;

	private static final int EIGHTEEN = 18;

	private static final Long NUM_THREE = 3L;
	
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	@Bind("waybillNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JTextFieldValidate txtMixNo;

	// 收货部门
	private JComboBox receiveOrgCodeComboBox;

	// 开单人
	private JTextField operator;
	
	// 运输性质
	private JComboBox transTypeComboBox;

	// 开单组织
	private JComboBox createOrgCodeComboBox;

	// 提交时间
	@Bind(value = "createTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "提交时间") })
	JXDateTimePicker fossStartDate;

	@Bind(value = "createTime", property = DATE)
	JXDateTimePicker fossdEndDate;

	// 运单图片类型
	private JComboBox waybillPictureTypeComboBox;

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = SearchPictureQueryAction.class)
	private JButton searchButton = new JButton(
			i18n.get("foss.gui.creating.waybillEditUI.common.query"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = SearchPictureResetAction.class)
	private JButton resetButton = new JButton(
			i18n.get("foss.gui.creating.waybillEditUI.common.reset"));

	private Plugin plugin;

	// 结果表
	private JXTable table = new JXTable();

	// 表外滚动条
	private JScrollPane scrollPane;

	private JPanel tablePanel;

	private IWaybillService waybillService;

	// 绑定接口
	private IBinder<SearchPictureVo> searchPictureWaybillBinder;

	private Map<String, IBinder<SearchPictureVo>> bindersMap = new HashMap<String, IBinder<SearchPictureVo>>();

	// 窗口应用程序
	private IApplication application;

	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;

	/**
	 * 选中的列
	 */
	private CheckBoxColumn checkBoxColumn;

	private DeliveryBigCustomerColumn deliveryBigCustomerColumn;

	private JPanel buttonPanel;

	private JLabel labelOrder;
	//统计总共条数
	private JLabel total;

	@Bind("orderNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "订单号") })
	private JTextField txtOrder;
	//导出按钮
	private JButton exportBtn;
	//司机工号
	private JTextField driverNoInput;
	//司机姓名
	private JTextField driverNameInput ;

	/**
	 * 构造方法
	 */
	public SearchPictureWaybillUI() {
		init();
		initComboBox();
		bind();
		registToRespository();
		// 监听Enter
		EnterKeySearchPicture enterMixNo = new EnterKeySearchPicture(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		EnterKeySearchPicture enterOrder = new EnterKeySearchPicture(searchButton);
		txtOrder.addKeyListener(enterOrder);
		EnterKeySearchPicture enterTable = new EnterKeySearchPicture(this);
		table.addKeyListener(enterTable);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
						KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}

	/**
	 * @return the txtOrder
	 */
	public JTextField getTxtOrder() {
		return txtOrder;
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n
				.get("foss.gui.creating.buttonPanel.searchPicture.label"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default):grow"), }, new RowSpec[] {
				RowSpec.decode("max(6dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("23px:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),FormFactory.RELATED_GAP_ROWSPEC,RowSpec.decode("max(10dlu;default)"),});
		flpanel.setColumnGroups(new int[][] { new int[] { FIFTEEN, ELEVEN, SEVEN, THREE } });
		panel.setLayout(flpanel);

		// 运单号/订单号
		JLabel label1 = new JLabel(
				i18n.get("foss.gui.creating.SearchPictureWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");

		txtMixNo = new JTextFieldValidate();
		panel.add(txtMixNo, "3, 3, fill, default");
		txtMixNo.setColumns(FIFIVETEEN);

		labelOrder = new JLabel(
				i18n.get("foss.gui.creating.SearchPictureWaybillUI.labelOrder"));
		panel.add(labelOrder, "5, 3, right, default");

		txtOrder = new JTextField();
		panel.add(txtOrder, "7, 3, fill, default");
		txtOrder.setColumns(TEN);

		// 收货部门
		JLabel label3 = new JLabel(
				i18n.get("foss.gui.creating.salesDeptWaybillUI.receiveOrgCode.label"));
		panel.add(label3, "13, 5, right, default");

		receiveOrgCodeComboBox = new JComboBox();
		panel.add(receiveOrgCodeComboBox, "15, 5, fill, default");

		// 制单部门
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.SearchPictureWaybillUI.createOrgCode.label"));
		panel.add(label7, "13, 3, right, default");

		// 制单部门编码的下拉框
		createOrgCodeComboBox = new JComboBox();
		panel.add(createOrgCodeComboBox, "15, 3, fill, default");

		// FOSS提交时间
		JLabel lblFoss = new JLabel(
				i18n.get("foss.gui.creating.SearchPictureTableMode.column.submitTime"));
		panel.add(lblFoss, "1, 5, right, default");

		// 提交开始时间
		fossStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(fossStartDate),
				"3, 5, 2, 1, fill, default");
		
		JLabel label8 = new JLabel("--");
		panel.add(label8, "5, 5");

		fossdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(fossdEndDate), "6, 5, 2, 1, fill, default");
		
		// 运单图片类型
		JLabel label = new JLabel("运单图片类型");
		panel.add(label, "9, 5, right, default");

		waybillPictureTypeComboBox = new JComboBox();
		panel.add(waybillPictureTypeComboBox, "11, 5, fill, default");

		// 开单员
		JLabel labelOperator = new JLabel(
				i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.bill.people"));
		panel.add(labelOperator, "9, 3, right, default");
	
		operator = new JTextField();
		panel.add(operator, "11, 3, fill, default");
		//运输性质
		JLabel labelTransTypeComboBox = new JLabel(
				i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"));
		panel.add(labelTransTypeComboBox, "1, 7, right, default");
	
		transTypeComboBox = new JComboBox();
		panel.add(transTypeComboBox, "3, 7, fill, default");
		//TODO 司机工号
		JLabel driverNoLab = new JLabel("司机工号");
		panel.add(driverNoLab, "5, 7, right, default");
		driverNoInput = new JTextField();
		panel.add(driverNoInput, "7, 7, fill, default");
		//司机姓名
		JLabel driverNameLab = new JLabel("司机姓名");
		panel.add(driverNameLab, "9, 7, right, default");
		 driverNameInput = new JTextField();
		panel.add(driverNameInput, "11, 7, fill, default");
		
		buttonPanel = new JPanel();
		panel.add(buttonPanel, "15, 7, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(resetButton);

		tablePanel = new JPanel();
		tablePanel
				.setBorder(new TitledBorder(
						null,
						i18n.get("foss.gui.creating.SearchPictureWaybillUI.queryWaybill.label"),
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						RowSpec.decode("9px"), FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.DEFAULT_ROWSPEC, }));

		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(NUM_600, NumberConstants.NUMBER_100));
		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");
		
		total = new JLabel();
		total.setText("总共0条数据");
		tablePanel.add(total, "4, 2, default, top");
		
		exportBtn = new FossExportExcelButton(table,"导出","图片查询",new Integer[]{1,FOUR,THIRTEEN,FOURTEEN,FIFTEEN,SIXTEEN,EIGHTEEN,NINEETEEN,TWENTY});
		tablePanel.add(exportBtn, "2, 2");
	}
	/**
	 * 获取默认渲染器
	 * @author Foss-278328-hujinyang
	 * @return
	 */
	private  void resetTableModel(SearchPictureTableMode tableModel){
		
		table = new JXTable(tableModel){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				 Component component = super.prepareRenderer(renderer, row, column);
				 
				 String stuts = (String)table.getValueAt(row, NumberConstants.NUMBER_13);
		    	 String remark = (String)table.getValueAt(row, NumberConstants.NUMBER_6);
		    	 
		    	//如果备注不为空
		    	 if(column != NumberConstants.NUMBER_4 && !"".equals(StringUtil.defaultIfNull(remark)) && (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(stuts) ||
	    				 WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(stuts))){
					 component.setBackground(Color.RED);
		    	 }
		    	 
				return component;
			}
		};
		
	}

	/**
	 * 获得格式化后的开始日期
	 * 
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
		/**
		 * 固定表格的宽度
		 */
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			
			if (i == 0 || i == FOUR) {
				tc.setPreferredWidth(NumberConstants.NUMBER_50);
				tc.setMaxWidth(NumberConstants.NUMBER_50);
			} else if (i == 1) {
				tc.setPreferredWidth(NumberConstants.NUMBER_330);//250
				tc.setMaxWidth(NumberConstants.NUMBER_330);//250
			} else if (i == SEVENTEEN) {
				tc.setPreferredWidth(NumberConstants.NUMBER_130);
				tc.setMaxWidth(NumberConstants.NUMBER_130);
			} else if (i == TEN) {
				tc.setPreferredWidth(NumberConstants.NUMBER_180);
				tc.setMaxWidth(NumberConstants.NUMBER_180);
			} else if(i == THIRTEEN) {
				tc.setPreferredWidth(NumberConstants.NUMBER_150);
				tc.setMaxWidth(NumberConstants.NUMBER_150);
			}else {
				tc.setPreferredWidth(NumberConstants.NUMBER_100);
			}
		}

		// 刷新表格
		SearchPictureTableMode tableModel = new SearchPictureTableMode(getArray(list));
//		table.setModel(tableModel);
		resetTableModel(tableModel);
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
		SearchPictureTableMode.adjustColumnPreferredWidths(table);
		table.setSortable(false);
		
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
		// 运输性质
		initCombProductType();
		// 运单图片类型
		initWaybillPictureType();
		// 收货部门
		initCombReceiveOrg();
		// 制单部门
		initCombCreateOrg();
	}

	/**
	 * <p>
	 * 初始化运单图片类型
	 * </p>
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initWaybillPictureType() {
		DefaultComboBoxModel pictureTypeModel = new DefaultComboBoxModel();
		waybillService = WaybillServiceFactory.getWaybillService();
		/* 暂时固定写法 */
		//all
		DataDictionaryValueVo allValueVo = new DataDictionaryValueVo();
		allValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.all"));
		allValueVo.setValueCode("ALL");
		pictureTypeModel.addElement(allValueVo);
		DataDictionaryValueVo waitValueVo = new DataDictionaryValueVo();
		waitValueVo.setValueName("待录入");
		waitValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
		pictureTypeModel.addElement(waitValueVo);
		//已录入
		DataDictionaryValueVo writeValueVo = new DataDictionaryValueVo();
		writeValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.write"));
		writeValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING);
		pictureTypeModel.addElement(writeValueVo);
		//已开单
		DataDictionaryValueVo billValueVo = new DataDictionaryValueVo();
		billValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.bill"));
		billValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
		pictureTypeModel.addElement(billValueVo);
		//已退回
		DataDictionaryValueVo returnValueVo = new DataDictionaryValueVo();
		returnValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.return"));
		returnValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
		pictureTypeModel.addElement(returnValueVo);
		//生成运单异常
		DataDictionaryValueVo exceptionValueVo = new DataDictionaryValueVo();
		exceptionValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.exception"));
		exceptionValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
		pictureTypeModel.addElement(exceptionValueVo);
		//已分配运单 by 352676
		DataDictionaryValueVo distributedValueVo = new DataDictionaryValueVo();
		distributedValueVo.setValueName(i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.distributed"));
		distributedValueVo.setValueCode(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED);
		pictureTypeModel.addElement(distributedValueVo);
		this.waybillPictureTypeComboBox.setModel(pictureTypeModel);
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
	private void initCombProductType() {
		DefaultComboBoxModel productTypeModel = new DefaultComboBoxModel();
		waybillService = WaybillServiceFactory.getWaybillService();
		//zxy 20130925 BUG-55437 start 修改：直接查询出所有的运输性质，不用按部门进行区分了、
		ProductEntity paramEntity = new ProductEntity();
		paramEntity.setLevels(NUM_THREE);
		paramEntity.setActive(FossConstants.YES);
		List<ProductEntity> list = waybillService.findProducts(paramEntity);
		Set set = new HashSet();
		for (ProductEntity product : list) {
			//零担界面不能查询为经济快递的单
			if(!CommonUtils.directDetermineIsExpressByProductCode(product.getCode())){
				set.add(product);
			}			
		}
		/* 附加信息 */
		DataDictionaryValueVo allValueVo = new DataDictionaryValueVo();
		allValueVo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		allValueVo.setValueCode("ALL");
		productTypeModel.addElement(allValueVo);

		Iterator it = set.iterator();
		while (it.hasNext()) {
			ProductEntity productEntity = (ProductEntity) it.next();
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			vo.setValueCode(productEntity.getCode());
			vo.setValueName(productEntity.getName());
			productTypeModel.addElement(vo);
		}

		this.transTypeComboBox.setModel(productTypeModel);
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
					vo.setValueCode("ALL");
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

				// 值与控件绑定
				receiveOrgCodeComboBox.setModel(comboModel);
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

			// 值与控件绑定
			receiveOrgCodeComboBox.setModel(comboModel);
			// 收货部门不可编辑
			receiveOrgCodeComboBox.setEnabled(false);
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
			valueVo.setValueCode("ALL");
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

		// 若为开单组，
		if (FossConstants.YES.equals(dept.getBillingGroup())) {
			// 制单部门不可编辑
			createOrgCodeComboBox.setEnabled(false);
		}
		// 若非开单组，
		else {
			salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
			// 获得开单查询组
			List<OrgAdministrativeInfoEntity> billGroups = salesDeptWaybillService.queryBillingGroupsBySaleDeptCode(StringUtil.defaultIfNull(dept.getCode()));
			// 非空判断
			if (CollectionUtils.isNotEmpty(billGroups)) {
				// 遍历集合
				for (OrgAdministrativeInfoEntity orgInfo : billGroups) {
					// 设置下拉框
					DataDictionaryValueVo value = new DataDictionaryValueVo();
					// 部门ID
					value.setId(orgInfo.getId());
					// 值名称
					value.setValueName(orgInfo.getName());
					// 值编码
					value.setValueCode(orgInfo.getCode());
					// 增加下拉框
					comboModel.addElement(value);
				}
			}
			// 制单部门为可编辑
			createOrgCodeComboBox.setEnabled(true);
		}

		// 下拉项个数
		int size = comboModel.getSize();
		// 判断下拉选项有多少个，若少于3个则删除“all”，并设置为不可编辑
		if (size <= 2 && FossConstants.NO.equals(dept.getBillingGroup())) {
			// 删除第一个all
			comboModel.removeElementAt(0);
			// 制单部门为可编辑
			createOrgCodeComboBox.setEnabled(false);
		}

		// 值与控件绑定
		createOrgCodeComboBox.setModel(comboModel);

	}
	
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		searchPictureWaybillBinder = BindingFactory.getIntsance().moderateBind(SearchPictureVo.class, this, true);

		searchPictureWaybillBinder.addValidationListener(new SearchPictureWaybillValidationListner());
		// salesDeptWaybillBinder.addBindingListener(new
		// SalesDeptWaybillBindingListener(this));
	}
	


	/**
	 * 保存绑定对象
	 */
	public void registToRespository() {
		bindersMap.put("searchPictureWaybillBinder", searchPictureWaybillBinder);
		// canvasMap.put("canvasBinder", canvasBinder);
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
				codes.add(StringUtil.defaultIfNull(pendingEntity
						.getCreateUserCode()));
			} else if (object instanceof WaybillEntity) {
				// 转换对象
				WaybillEntity waybillEntity = (WaybillEntity) object;
				// 获得开单人工号
				codes.add(StringUtil.defaultIfNull(waybillEntity
						.getCreateUserCode()));
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
		// empList = waybillService.queryEmployeeByCodeList(newCodes);
		// 内存溢出，性能优化
		empList = waybillService.queryEmployeeNameAndCodeByCodeList(newCodes);
		return empList;
	}


	/**
	 * 根据工号获得名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午9:17:24
	 */
	public String getEmployeeName(String code) {
		if (StringUtils.isEmpty(code)) {
			return "";
		} else {
			if (CommonUtils.isOnline()) {
				waybillService = WaybillServiceFactory.getWaybillService();
				EmployeeEntity entity = waybillService
						.queryEmployeeByEmpCode(code);
				if (null != entity) {
					return StringUtils.defaultString(entity.getEmpName());
				} else {
					return code;
				}
			} else {
				return CommonUtils.getUserNameFromUserService(code);
			}
		}

	}

	/**
	 * getRowValue结果列表
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object) {

		int no = 1;
		// 运单状态
		String status = "";
		
		String active = "";
		// 运单号
		String waybillNo = "";
		// 订单号
		String orderNo = "";
		// Foss提交时间
		String createTime = "";
		// 收货部门
		String receiveOrgCode = "";
		// 收货部门名称
		String receiveOrgName = "";
		//司机工号
		String driverWorkNo = "";
		//司机姓名
		String driverName = "";
		//车牌号
		String vehicleNo = "";
		
		String createUserCode = "";
		//开单员姓名
		String operatorName = "";
		//开单员工号
		String operator = "";
		
		String createOrgCode = "";
		
		String reMark = "";
		//现金标记
		String cashPayFlag = "";
		//现金标记
		String pictureWaybillId = "";
		
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
		
		//TODO 设置新增 列
		String imgUploadTime="";
		String transportTypeName = "";
		String distStation = "";
		/**
		 * 补录人
		 */
		 String outerOptCode="";
		/**
		 * 补录人部门
		 */
		 String outerName="";
				 
		 /**
			 * 本地开单组
			 */
		 String localBillGroupCode="";
		
		 /**
		  * 开单部门
		  * 
		  */
		 String belongOrgCode="";
				 
		if (object instanceof SearchPictureVo) {
			
			SearchPictureVo vo = (SearchPictureVo) object;
			
			no = vo.getNo();
			
			status = getStatusDesc(StringUtil.defaultIfNull(vo.getActive()));
			
			active = StringUtil.defaultIfNull(vo.getActive());
			
			waybillNo = StringUtil.defaultIfNull(vo.getWaybillNo());
			
			orderNo = StringUtil.defaultIfNull(vo.getOrderNo());
			
			createTime = dateFormat.format(vo.getCreateTime());
			
			receiveOrgCode = StringUtil.defaultIfNull(vo.getReceiveOrgCode());
			
			receiveOrgName = StringUtil.defaultIfNull(vo.getReceiveOrgName());
			
			driverWorkNo = StringUtil.defaultIfNull(vo.getDriverWorkNo());
			
			driverName = StringUtil.defaultIfNull(vo.getDriverName());
			
			vehicleNo = StringUtil.defaultIfNull(vo.getVehicleNo());
			
			createUserCode = StringUtil.defaultIfNull(vo.getCreateUserCode());
			
			operator = StringUtil.defaultIfNull(vo.getOperator());
			
			operatorName = StringUtil.defaultIfNull(vo.getOperatorName());
			
			createOrgCode = StringUtil.defaultIfNull(vo.getReceiveOrgCode());
			
			reMark = StringUtil.defaultIfNull(vo.getReMark());
			
			cashPayFlag = StringUtil.defaultIfNull(vo.getCashPayFlag());
			
			pictureWaybillId = StringUtil.defaultIfNull(vo.getId());
			
			if(vo.getImgUploadTime()!=null){
				imgUploadTime =  dateFormat.format(vo.getImgUploadTime());
			}
			
			transportTypeName = getComboBoxValByKey(transTypeComboBox, vo.getTransportType());
			
			distStation =  StringUtil.defaultIfNull(vo.getDistStation());
			
			outerOptCode =  StringUtil.defaultIfNull(vo.getOuterOptCode());
			
			outerName = StringUtil.defaultIfNull(vo.getOuterName());
			
			localBillGroupCode = StringUtil.defaultIfNull(vo.getLocalBillGroupCode());
			belongOrgCode= StringUtil.defaultIfNull(vo.getBelongOrgCode());
		}

		
		// 获取对象成员保存至一维数组
		Object[] obj = { no, "", waybillNo, orderNo, "",
				status,	reMark, driverWorkNo, driverName, vehicleNo, 
				receiveOrgName,operator,operatorName,active,receiveOrgCode,createUserCode,createOrgCode,
				createTime,cashPayFlag,pictureWaybillId,localBillGroupCode,imgUploadTime,transportTypeName,distStation,
				outerOptCode,outerName,belongOrgCode};
		return obj;
	}
	
	private String getStatusDesc (String statusCode) {
		String statusName = "";
		//已录入
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(statusCode)){
			statusName = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.write");
		}
		//已开单
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(statusCode)){
			statusName = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.bill");
		}
		//已退回
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(statusCode)){
			statusName = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.return");
		}
		//生成运单异常
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(statusCode)){
			statusName = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.exception");
		}
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(statusCode)){
			statusName = "待录入";
		}
		//已分配运单  2017年4月18日11:48:59  by:352676
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(statusCode)){
			statusName = i18n.get("foss.gui.creating.SearchPictureWaybillUI.pictureType.distributed");
		}
		return statusName;
	}
	/**
	 * 
	* @Description: 通过key获取combobox对应值
	* @author hebo 
	* @date 2015-5-12 下午3:51:27 
	  @param comboBox
	  @param key
	  @return
	 */
	private String getComboBoxValByKey(JComboBox comboBox,String key){
		String val = "";
		int count = comboBox.getItemCount();
		DefaultComboBoxModel  model= (DefaultComboBoxModel) comboBox.getModel();
		if(count > 0 && StringUtil.isNotEmpty(key)){
			for (int i = 0; i < count; i++) {
				DataDictionaryValueVo data  = (DataDictionaryValueVo) model.getElementAt(i);
				if(key.equals(data.getValueCode())){
					return data.getValueName();
				}
			}
		}
		return val;
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
	public HashMap<String, IBinder<SearchPictureVo>> getBindersMap() {
		return (HashMap<String, IBinder<SearchPictureVo>>) bindersMap;
	}

	/**
	 * getTxtMixNo
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtMixNo() {
		return txtMixNo;
	}

	/**
	 * setTxtMixNo
	 * 
	 * @param txtMixNo
	 */
	public void setTxtMixNo(JTextFieldValidate txtMixNo) {
		this.txtMixNo = txtMixNo;
	}

	/**
	 * getFossStartDate
	 */
	public JXDateTimePicker getFossStartDate() {
		return fossStartDate;
	}

	/**
	 * setFossStartDate
	 * 
	 * @param fossStartDate
	 */
	public void setFossStartDate(JXDateTimePicker fossStartDate) {
		this.fossStartDate = fossStartDate;
	}

	/**
	 * getFossdEndDate
	 * 
	 * @return
	 */
	public JXDateTimePicker getFossdEndDate() {
		return fossdEndDate;
	}

	/**
	 * setFossdEndDate
	 * 
	 * @param fossdEndDate
	 */
	public void setFossdEndDate(JXDateTimePicker fossdEndDate) {
		this.fossdEndDate = fossdEndDate;
	}


	/**
	 * getWaybillPictureTypeComboBox
	 * 
	 * @return
	 */
	public JComboBox getWaybillPictureTypeComboBox() {
		return waybillPictureTypeComboBox;
	}

	/**
	 * setWaybillPictureTypeComboBox
	 * 
	 * @param waybillPictureTypeComboBox
	 */
	public void setWaybillPictureTypeComboBox(JComboBox waybillPictureTypeComboBox) {
		this.waybillPictureTypeComboBox = waybillPictureTypeComboBox;
	}

	/**
	 * getReceiveOrgCodeComboBox
	 * 
	 * @return
	 */
	public JComboBox getReceiveOrgCodeComboBox() {
		return receiveOrgCodeComboBox;
	}

	/**
	 * setReceiveOrgCodeComboBox
	 * 
	 * @param receiveOrgCodeComboBox
	 */
	public void setReceiveOrgCodeComboBox(JComboBox receiveOrgCodeComboBox) {
		this.receiveOrgCodeComboBox = receiveOrgCodeComboBox;
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
	 * @return the operator
	 */
	public JTextField getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(JTextField operator) {
		this.operator = operator;
	}


	/**
	 * @return the createOrgCodeComboBox .
	 */
	public JComboBox getCreateOrgCodeComboBox() {
		return createOrgCodeComboBox;
	}

	/**
	 * @param createOrgCodeComboBox
	 *            the createOrgCodeComboBox to set.
	 */
	public void setCreateOrgCodeComboBox(JComboBox createOrgCodeComboBox) {
		this.createOrgCodeComboBox = createOrgCodeComboBox;
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

	public IBinder<SearchPictureVo> getSearchPictureWaybillBinder() {
		return searchPictureWaybillBinder;
	}

	public void setSearchPictureWaybillBinder(
			IBinder<SearchPictureVo> searchPictureWaybillBinder) {
		this.searchPictureWaybillBinder = searchPictureWaybillBinder;
	}

	
	/**
	 * @return the transTypeComboBox
	 */
	public JComboBox getTransTypeComboBox() {
		return transTypeComboBox;
	}
	/**
	 * @param transTypeComboBox
	 *            the transTypeComboBox to set
	 */
	public void setTransTypeComboBox(JComboBox transTypeComboBox) {
		this.transTypeComboBox = transTypeComboBox;
	}

	/**
	 * @param bindersMap
	 *            the bindersMap to set
	 */
	public void setBindersMap(
			Map<String, IBinder<SearchPictureVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}


	/**
	 * @return the checkBoxColumn
	 */
	public CheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	/**
	 * @param checkBoxColumn
	 *            the checkBoxColumn to set
	 */
	public void setCheckBoxColumn(CheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}

	public DeliveryBigCustomerColumn getDeliveryBigCustomerColumn() {
		return deliveryBigCustomerColumn;
	}

	public void setDeliveryBigCustomerColumn(
			DeliveryBigCustomerColumn deliveryBigCustomerColumn) {
		this.deliveryBigCustomerColumn = deliveryBigCustomerColumn;
	}

	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}

	public void setSalesDeptWaybillService(
			ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	}

	public IWaybillService getWaybillService() {
		return waybillService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}
	
	public JLabel getTotal() {
		return total;
	}

	public void setTotal(JLabel total) {
		this.total = total;
	}

	public JTextField getDriverNoInput() {
		return driverNoInput;
	}

	public void setDriverNoInput(JTextField driverNoInput) {
		this.driverNoInput = driverNoInput;
	}

	public JTextField getDriverNameInput() {
		return driverNameInput;
	}

	public void setDriverNameInput(JTextField driverNameInput) {
		this.driverNameInput = driverNameInput;
	}

}