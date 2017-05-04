/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
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
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillTypeVo;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.SalesDeptWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpSalesDeptDeleteAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpSalesDeptExportAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpSalesDeptInputAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpSalesDeptResetAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpSalesDeptSearchAction;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.google.inject.Inject;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpSalesDeptWaybillUI  extends JPanel implements IApplicationAware, IPluginAware {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpSalesDeptWaybillUI.class);
	
	private static final int SEVEN = 7;
	
	private static final int FIFTEEN = 15;
	
	private static final int ELEVEN = 11;
	
	private static final int THREE = 3;
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpSalesDeptWaybillUI.class);

	/**
	 * 结算付款方式:现金、电汇、支票、网上支付、月结、临时欠款、到付
	 */
	private static final String SETTLEMENTPAYMENTTYPE = "SETTLEMENT__PAYMENT_TYPE";

	/**
	 * 定义离线的词条，包含“离线运单未提交”、“已删除”这两个值
	 */
	private static final String OFFLINEACTIVE = "OFFLINEACTIVE";

	/**
	 * 定义离线的词条，包含“待补录”、“运单暂存”、“已补录”、“已开单”这四个值
	 */
	private static final String PENDINGTYPE = "PENDINGTYPE";

	private static final String GOODSTYPE = "NO_LABEL_GOODS_CATEGORY";

	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	// 已开单
	private static final String WAYBILLSTATUSPCACTIVE = CommonUtils.getNameFromDict(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE, DictionaryConstants.PENDINGTYPE);

	// 已补录
	private static final String WAYBILLSTATUSPENDINGACTIVE = CommonUtils.getNameFromDict(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE, DictionaryConstants.PENDINGTYPE);

	// 作废
	private static final String WAYBILL_OBSOLETE = CommonUtils.getNameFromDict(WaybillRfcConstants.INVALID, DictionaryConstants.WAYBILL_RFC_TYPE_CUSTOMER);

	// 中止
	private static final String WAYBILL_ABORT = CommonUtils.getNameFromDict(WaybillRfcConstants.ABORT, DictionaryConstants.WAYBILL_RFC_TYPE_INSIDE);

	/**
	 * date
	 */
	private static final String DATE = "date";

	// 15
	private static final int FIFIVETEEN = 15;

	// 10
	private static final int TEN = 10;

	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	@Bind("mixNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JTextFieldValidate txtMixNo;

	// 收货部门
	private JComboBox receiveOrgCodeComboBox;

	// 制单时间:开始时间

	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "制单时间") })
	private JXDateTimePicker zdStartDate;

	// 制单时间:结束时间

	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;

	// 开单人
	private JTextField createUserCode;

	// 开单组织
	private JComboBox createOrgCodeComboBox;

	// 提交时间
	@Bind(value = "createStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "提交时间") })
	JXDateTimePicker fossStartDate;

	@Bind(value = "createEndTime", property = DATE)
	JXDateTimePicker fossdEndDate;

	// 运输性质
	private JComboBox transTypeComboBox;

	// 运单状态
	private JComboBox statusComboBox;

	// 离线运单待提交
	private JCheckBox checkBox;

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpSalesDeptSearchAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpSalesDeptExportAction.class)
	private JButton exportButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.export"));
	
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpSalesDeptInputAction.class)
	private JButton inputButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.import"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpSalesDeptDeleteAction.class)
	private JButton delButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.delete"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpSalesDeptResetAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));

	private Plugin plugin;

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
	private IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder;

	private Map<String, IBinder<SalesDeptWaybillVo>> bindersMap = new HashMap<String, IBinder<SalesDeptWaybillVo>>();

	// 窗口应用程序
	private IApplication application;

	/**
	 * 选中的导出运单号
	 */
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

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
	private ExpCheckBoxColumn checkBoxColumn;

	private JPanel buttonPanel;

	private JLabel labelOrder;

	@Bind("orderNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "订单号") })
	private JTextField txtOrder;

	// 运单类型
	private JComboBox waybillTypeComboBox;
	/**
	 * 构造方法
	 */
	public ExpSalesDeptWaybillUI() {
		init();
		initComboBox();
		bind();
		registToRespository();
		// 监听Enter
		ExpEnterKeySalesDept enterMixNo = new ExpEnterKeySalesDept(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		ExpEnterKeySalesDept enterOrder = new ExpEnterKeySalesDept(searchButton);
		txtOrder.addKeyListener(enterOrder);
		ExpEnterKeySalesDept enterCreateUserCode = new ExpEnterKeySalesDept(searchButton);
		createUserCode.addKeyListener(enterCreateUserCode);
		ExpEnterKeySalesDept enterTable = new ExpEnterKeySalesDept(this);
		table.addKeyListener(enterTable);
		// 禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
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
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptWaybillUI.title"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
				ColumnSpec.decode("max(51dlu;default):grow"),},
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
		flpanel.setColumnGroups(new int[][]{new int[]{FIFTEEN, ELEVEN, SEVEN, THREE}});
		panel.setLayout(flpanel);
		this.setBackground(CommonUtils.getExpressColor());
		// 运单号/订单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.label1"));
		panel.add(label1, "1, 3, right, top");

		txtMixNo = new JTextFieldValidate();
		panel.add(txtMixNo, "3, 3, fill, default");
		txtMixNo.setColumns(FIFIVETEEN);

		labelOrder = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder"));
		panel.add(labelOrder, "5, 3, right, default");

		txtOrder = new JTextField();
		panel.add(txtOrder, "7, 3, fill, default");
		txtOrder.setColumns(TEN);

		// 运单状态
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.status.label"));
		panel.add(label2, "9, 3, right, default");

		statusComboBox = new JComboBox();
		panel.add(statusComboBox, "11, 3, fill, default");

		// 收货部门
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.receiveOrgCode.label"));
		panel.add(label3, "13, 3, right, default");

		receiveOrgCodeComboBox = new JComboBox();
		panel.add(receiveOrgCodeComboBox, "15, 3, fill, default");

		// 制单时间
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.startDate.label"));
		panel.add(label4, "1, 5, right, default");

		zdStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(zdStartDate), "3, 5, 2, 1, fill, fill");

		JLabel label5 = new JLabel("--");
		panel.add(label5, "5, 5");

		zdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(zdEndDate), "6, 5, 2, 1, fill, default");

		// 开单人
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.createUserCode.label"));
		panel.add(label6, "9, 5, right, default");

		// 创建客户编码
		createUserCode = new JTextField();
		panel.add(createUserCode, "11, 5, fill, default");
		createUserCode.setColumns(TEN);

		// 制单部门
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.salesDeptWaybillUI.createOrgCode.label"));
		panel.add(label7, "13, 5, right, default");

		// 制单部门编码的下拉框
		createOrgCodeComboBox = new JComboBox();
		panel.add(createOrgCodeComboBox, "15, 5, fill, default");

		// FOSS提交时间
		JLabel lblFoss = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.six"));
		panel.add(lblFoss, "1, 7, right, default");

		// 提交开始时间
		fossStartDate = new JXDateTimePicker();
		panel.add(getStartFormatTime(fossStartDate), "3, 7, 2, 1, fill, default");

		JLabel label8 = new JLabel("--");
		panel.add(label8, "5, 7");

		fossdEndDate = new JXDateTimePicker();
		panel.add(getEndFormatTime(fossdEndDate), "6, 7, 2, 1, fill, default");
		OtherListener otherListener = new OtherListener();

		// 运输性质
		JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"));
		panel.add(label, "9, 7, right, default");

		transTypeComboBox = new JComboBox();
		panel.add(transTypeComboBox, "11, 7, fill, default");
		
		// liyongfei 运单类型
		JLabel waybillType = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.waybillType.label"));
		panel.add(waybillType, "13, 7, right, default");
		
		waybillTypeComboBox = new JComboBox();
		panel.add(waybillTypeComboBox, "15, 7, fill, default");

		// 离线运单待提交
		checkBox = new JCheckBox(i18n.get("foss.gui.creating.salesDeptWaybillUI.waitSubmit.label"));
		checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(checkBox, "13, 9");
		checkBox.addItemListener(otherListener);

		buttonPanel = new JPanel();
		panel.add(buttonPanel, "15, 9, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(searchButton);
		resetButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(resetButton);

		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.salesDeptWaybillUI.queryWaybill.label"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablePanel, "1, 3, fill, default");
		tablePanel
				.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("4dlu:grow"), FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
						new RowSpec[] { RowSpec.decode("9px"), FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.DEFAULT_ROWSPEC, }));

		// 全选
		allSelectCheckBox = new JCheckBox(i18n.get("foss.gui.creating.waybillEditUI.common.selectAll"));
		tablePanel.add(allSelectCheckBox, "1, 2, fill, top");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);

		tablePanel.add(exportButton, "4, 2, default, top");
		exportButton.setEnabled(false);
		tablePanel.add(inputButton, "6, 2, default, top");
		inputButton.setEnabled(false);
		// 删除按钮只有在离线的情况下才可以点 所以默认为disabled不能点
		delButton.setEnabled(false);
		tablePanel.add(delButton, "8, 2, default, top");

		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");

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
			tc.setPreferredWidth(NumberConstants.NUMBER_50);
			tc.setMaxWidth(NumberConstants.NUMBER_50);
		}

		// 刷新表格
		ExpLinkTableMode tableModel = new ExpLinkTableMode(getArray(list, 2));
		table.setModel(tableModel);
		refreshTable(table);

		/**
		 * 双击已开单记录
		 */
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
									|| WAYBILL_ABORT.equals(obj.toString())) {
								
								String waybillNo = table.getModel().getValueAt(row, 3).toString();
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
	}

	/**
	 * Enter键监听表格
	 * 
	 * @author WangQianJin
	 * @date 2013-5-20 下午1:06:17
	 */
	public void tableListenter() {
		try {
			// 将UI上的行转成model中的行
			int row = table.convertRowIndexToModel(table.getSelectedRow());
			// 双击某一行以后进入界面
			Object obj = table.getModel().getValueAt(row, 2);
			if (obj != null) {
				if (WAYBILLSTATUSPCACTIVE.equals(obj.toString()) || WAYBILLSTATUSPENDINGACTIVE.equals(obj.toString()) || WAYBILL_OBSOLETE.equals(obj.toString())
						|| WAYBILL_ABORT.equals(obj.toString())) {
					//ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = table.getModel().getValueAt(row, NumberConstants.NUMBER_3).toString();
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
		ExpLinkTableMode.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}

	/**
	 * 
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	public void initComboBox() {
		// 产品类型
		initCombProductType();
		// 运单状态(待处理类型)
		initCombPendingType();
		// 收货部门
		initCombReceiveOrg();
		// 制单部门
		initCombCreateOrg();
		//运单类型
		initCombWaybillType();
	}

	/**
	 * 初始化运单类型
	 */
	private void initCombWaybillType(){
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		WaybillTypeVo all = new WaybillTypeVo();
		all.setValueCode(WaybillRfcConstants.WAYBILL_TYPE_ALL);
		all.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		comboModel.addElement(all);
		WaybillTypeVo normal = new WaybillTypeVo();
		normal.setValueCode(WaybillRfcConstants.WAYBILL_TYPE_NORMAL);
		normal.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.normal"));
		comboModel.addElement(normal);
		WaybillTypeVo ewaybill = new WaybillTypeVo();
		ewaybill.setValueCode(WaybillRfcConstants.WAYBILL_TYPE_EWAYBILL);
		ewaybill.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.ewaybill"));
		comboModel.addElement(ewaybill);
		waybillTypeComboBox.setModel(comboModel);
		
	}
	/**
	 * <p>
	 * (初始化产品类型)运输性质
	 * </p>
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombProductType() {
		/* 附加信息 */
		ProductEntityVo entityVo = new ProductEntityVo();
		entityVo.setName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		entityVo.setCode("all");
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if(dept == null){
			return;
		}
		DefaultComboBoxModel productTypeModel = new DefaultComboBoxModel();
		waybillService = WaybillServiceFactory.getWaybillService();
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_EXPRESS, new Date());
		productTypeModel.addElement(entityVo);
		ProductEntityVo vo = null;
		if(CollectionUtils.isNotEmpty(list)){
			for(ProductEntity productEntity : list) {
				vo = new ProductEntityVo();
				ValueCopy.entityValueCopy(productEntity, vo);
				vo.setDestNetType(productEntity.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
		this.transTypeComboBox.setModel(productTypeModel);
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
		DataDictionaryValueVo DDValuevo = new DataDictionaryValueVo();
		DDValuevo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		DDValuevo.setValueCode("all");
		comboModel.addElement(DDValuevo);

		for (int i = list.size(); i > 0; i--) {
			DataDictionaryValueEntity dataDictionary = (DataDictionaryValueEntity) list.get(i - 1);
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			comboModel.addElement(vo);
		}
		
		//增加作废和中止
		DataDictionaryValueVo obsolete = new DataDictionaryValueVo();
		obsolete.setValueCode(WaybillRfcConstants.INVALID);
		obsolete.setValueName(WAYBILL_OBSOLETE);
		comboModel.addElement(obsolete);
		
		//增加作废和中止
		DataDictionaryValueVo abort = new DataDictionaryValueVo();
		abort.setValueCode(WaybillRfcConstants.ABORT);
		abort.setValueName(WAYBILL_ABORT);
		comboModel.addElement(abort);
		
		statusComboBox.setModel(comboModel);
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
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		DataDictionaryValueVo vo = new DataDictionaryValueVo();
		// 值名称
		vo.setValueName(i18n.get("foss.gui.creating.waybillEditUI.common.all"));
		// 值编码
		vo.setValueCode("all");
		// 增加下拉框
		comboModel.addElement(vo);
		
		// 收货部门为当前部门
		DataDictionaryValueVo vo2 = new DataDictionaryValueVo();
		// 部门ID
		vo2.setId(dept.getId());
		// 值名称
		vo2.setValueName(dept.getName());
		// 值编码
		vo2.setValueCode(dept.getCode());
		// 增加下拉框
		comboModel.addElement(vo2);
		
		// 值与控件绑定
		receiveOrgCodeComboBox.setModel(comboModel);
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
		createOrgCodeComboBox.setModel(comboModel);
		// 收货部门不可编辑
		createOrgCodeComboBox.setEnabled(false);
	}

	/**
	 * <p>
	 * (初始化运单状态 OFFLINE)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	private void initCombOfflineActive() {
		salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
		List<DataDictionaryValueEntity> list = salesDeptWaybillService.queryOfflineActive();
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			comboModel.addElement(vo);
		}
		statusComboBox.setModel(comboModel);
	}

	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		salesDeptWaybillBinder = BindingFactory.getIntsance().moderateBind(SalesDeptWaybillVo.class, this, true);

		salesDeptWaybillBinder.addValidationListener(new SalesDeptWaybillValidationListner());
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
	 * 
	 * @description：离线运单待提交监听
	 * @date 2012-7-19
	 * @author yangtong
	 */
	class OtherListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			Object obj = e.getSource();
			if (obj.equals(checkBox)) {
				if (checkBox.isSelected()) {
					initCombOfflineActive();

					if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
						// 只有在线的情况下可以删除
						delButton.setEnabled(true);
					} else {
						// 离线的情况下不能删除
						delButton.setEnabled(false);
					}

					inputButton.setEnabled(true);
					exportButton.setEnabled(true);
				} else {
					initCombPendingType();
					delButton.setEnabled(false);
					inputButton.setEnabled(false);
					exportButton.setEnabled(false);
				}
			}
		}
	};

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
	};

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
		empList = waybillService.queryEmployeeByCodeList(newCodes);
		return empList;
	}

//	/**
//	 * 根据工号获得名称
//	 * 
//	 * @author 026123-foss-lifengteng
//	 * @date 2013-3-26 下午9:12:00
//	 */
//	public static String getEmployeeName(String code, List<EmployeeEntity> empList) {
//		// 定义名称
//		String name = code;
//		// 遍历集合对象
//		if (empList != null) {
//			for (EmployeeEntity emp : empList) {
//				// 查找对应的名称
//				if (StringUtil.defaultIfNull(code).equals(emp.getEmpCode())) {
//					return StringUtil.defaultIfNull(emp.getEmpName());
//				}
//			}
//		} else {
//
//			return CommonUtils.getUserNameFromUserService(name);
//
//		}
//
//		return name;
//	}
	
	/**
	 * 根据工号获得名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午9:17:24
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
	 * getRowValue结果列表
	 * 
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object, int flag) {

		// 运单状态
		String active = "";
		// 运单号
		String waybillNo = "";
		// 订单号
		String orderNo = "";
		//发货客户
		String deliveryCustomer = "";
		//发货人
		String deliveryContact = "";
		// 发货客户所属部门
		String custDept = "";
		// 开单时间
		String billTime = "";
		// Foss提交时间
		String createTime = "";
		// 收货部门
		String receiveOrgCode = "";
		// 发货市
		String deliveryCityCode = "";
		// 收货市
		String receiveCityCode = "";
		// 运输性质
		String productCode = "";
		// 货物总重量
		String goodsWeightTotal = "";
		// 货物总体积
		String goodsVolumeTotal = "";
		// 货物总件数
		String goodsQtyTotal = "";
		// 开单付款方式
		String paidMethod = "";
		// 总费用
		String totalFee = "";
		// 开单组织
		String createOrgCode = "";
		// 开单人
		String createUserCode = "";
		//　开单人名
		String createUserName = "";
		// 货物类型
		String goodsTypeCode = "";
		// 主键
		String id = "";
		// 发货大客户标记
		String deliveryBigCustomer = "";
		//时效是否大于13小时
		String isBig13= "";

		// 运单类型
		String waybillType="";
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/**
		 * 待补录运单对象
		 */
		if (object instanceof WaybillPendingEntity) {
			// 待补录运单对象
			WaybillPendingEntity pending = (WaybillPendingEntity) object;
			// 获得运单状态类型
			String pendingType = StringUtil.defaultIfNull(pending.getPendingType());
			// 离线运单
			if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(pendingType)) {
				/**
				 * 由于数据字典中“离线运单未提交”对应的是“Y”，“已删除”对应是“N”，
				 * 所以如果为离线运单状态时，不能用pendingType字段来查询对应的名称 而应用“Y”和“N”来传参
				 */
				// active =
				// CommonUtils.getNameFromDict(StringUtil.defaultIfNull(pending.getActive()),
				// OFFLINEACTIVE);
				active = Common.getStatusByWaybillNo(pending.getWaybillNo(), pending.getActive(), OFFLINEACTIVE);
				if (active == null || "".equals(active)) {
					active = Common.getStatusByWaybillNo(pending.getWaybillNo(), pendingType, PENDINGTYPE);
				} else {

				}

			}
			// 待补录运单
			else {
				// 根据code拿到中文name
				if (flag == 1) {
					active = CommonUtils.getNameFromDict(pendingType, OFFLINEACTIVE);
				} else if (flag == 2) {
					active = CommonUtils.getNameFromDict(pendingType, PENDINGTYPE);
				}
			}

			// 运单号
			waybillNo = StringUtil.defaultIfNull(pending.getWaybillNo());
			// 订单号
			orderNo = StringUtil.defaultIfNull(pending.getOrderNo());
			//发货客户
			deliveryCustomer =  StringUtil.defaultIfNull(pending.getDeliveryCustomerName());
			// 发货大客户
			deliveryBigCustomer = StringUtil.defaultIfNull(pending.getDeliveryBigCustomer());
			//发货人
			deliveryContact =  StringUtil.defaultIfNull(pending.getDeliveryCustomerContact());
			// 发货客户所属部门
			custDept = StringUtil.defaultIfNull(CommonUtils.getDeptNameByCustCode(pending.getDeliveryCustomerCode()));
			// 开单时间
			billTime = dateFormat.format(pending.getBillTime());
			// 创建时间
			if (pending.getCreateTime() != null && !WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pending.getPendingType())) {
				createTime = dateFormat.format(pending.getCreateTime());
			}
			// 发货组织编码
			receiveOrgCode = CommonUtils.getDeptNameByCode(pending.getReceiveOrgCode());
			// 发货市
			deliveryCityCode = CommonUtils.getCityNameByOrgCode(pending.getReceiveOrgCode());
			// 收货市
			receiveCityCode = CommonUtils.getDestCityNameByCode(pending.getCustomerPickupOrgCode());
			// 产品类型
			productCode = CommonUtils.getNameByProductCode(pending.getProductCode());
			if(StringUtils.isEmpty(productCode)){
				ProductEntity productEntity = waybillService.getProductByCache(pending.getProductCode(), pending.getBillTime());
				if(productEntity == null){
					if(WaybillConstants.PACKAGE.equals(pending.getProductCode())){
						productCode = "标准快递";
					}
					//新增快递产品 3.60 特惠件
					if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(pending.getProductCode())){
						productCode = "3.60 特惠件";
					}
				}else{
					productCode = productEntity.getName();
				}
			}
			waybillType = pending.getWaybillType();
			if(waybillType==null){
				waybillType=i18n.get("foss.gui.creating.waybillEditUI.common.normal");
			}else if("EWAYBILL".equals(waybillType)){
				waybillType=i18n.get("foss.gui.creating.waybillEditUI.common.ewaybill");
			}
			// 货物总重量
			goodsWeightTotal = CommonUtils.defaultIfNull(pending.getGoodsWeightTotal()).toString();
			// 货物总体积
			goodsVolumeTotal = CommonUtils.defaultIfNull(pending.getGoodsVolumeTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			// 货物总件数
			goodsQtyTotal = CommonUtils.defaultIfNull(pending.getGoodsQtyTotal()).toString();
			// 开单付款方式
			paidMethod = CommonUtils.getNameFromDict(pending.getPaidMethod(), SETTLEMENTPAYMENTTYPE);
			// 总费用
			totalFee = CommonUtils.defaultIfNull(pending.getTotalFee()).toString();
			// 开单组织
			createOrgCode = CommonUtils.getDeptNameByCode(pending.getCreateOrgCode());
			// 开单人
			createUserCode = StringUtil.defaultIfNull(pending.getCreateUserCode());
			//　开单人名
			createUserName = getEmployeeName(createUserCode);
			// 货物类型
			goodsTypeCode = CommonUtils.getGoodsTypeByCode(pending.getTransportType(), StringUtil.defaultIfNull(pending.getGoodsTypeCode()), GOODSTYPE);
			// 主键
			id = pending.getId();
			//时效是否大于13小时
			isBig13=pending.getIsBig13();
		}
		/**
		 * 已处理运单对象
		 */
		else if (object instanceof WaybillEntity) {
			// 已处理运单对象
			WaybillEntity entity = (WaybillEntity) object;
			// 运单状态
			active = Common.getStatusByWaybillNo(entity.getWaybillNo(), StringUtil.defaultIfNull(entity.getPendingType()), PENDINGTYPE);
			// 运单号
			waybillNo = StringUtil.defaultIfNull(entity.getWaybillNo());
			// 订单号
			orderNo = StringUtil.defaultIfNull(entity.getOrderNo());
			//发货客户
			deliveryCustomer =  StringUtil.defaultIfNull(entity.getDeliveryCustomerName());
			// 发货大客户
			deliveryBigCustomer = StringUtil.defaultIfNull(entity.getDeliveryBigCustomer());
			//发货人
			deliveryContact =  StringUtil.defaultIfNull(entity.getDeliveryCustomerContact());
			// 发货客户所属部门
			custDept = StringUtil.defaultIfNull(CommonUtils.getDeptNameByCustCode(entity.getDeliveryCustomerCode()));
			// 开单时间
			billTime = dateFormat.format(entity.getBillTime());
			// 创建时间
			if (entity.getCreateTime() != null && !WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity.getPendingType())) {
				createTime = dateFormat.format(entity.getCreateTime());
			}
			// 发货组织编码
			receiveOrgCode = CommonUtils.getDeptNameByCode(entity.getReceiveOrgCode());
			// 发货市
			deliveryCityCode = CommonUtils.getCityNameByOrgCode(entity.getReceiveOrgCode());
			// 收货市
			receiveCityCode = CommonUtils.getDestCityNameByCode(entity.getCustomerPickupOrgCode());
			// 产品类型
			productCode = CommonUtils.getNameByProductCode(entity.getProductCode());
			if(StringUtils.isEmpty(productCode)){
				ProductEntity productEntity = waybillService.getProductByCache(entity.getProductCode(), entity.getBillTime());
				if(productEntity == null){
					if(WaybillConstants.PACKAGE.equals(entity.getProductCode())){
						productCode = "标准快递";
					}
					//新增快递产品 3.60 特惠件
					if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(entity.getProductCode())){
						productCode = "3.60 特惠件";
					}
				}else{
					productCode = productEntity.getName();
				}
			}
			waybillType = entity.getWaybillType();
			if(waybillType==null){
				waybillType=i18n.get("foss.gui.creating.waybillEditUI.common.normal");
			}else if("EWAYBILL".equals(waybillType)){
				waybillType=i18n.get("foss.gui.creating.waybillEditUI.common.ewaybill");
			}
			// 货物总重量
			goodsWeightTotal = CommonUtils.defaultIfNull(entity.getGoodsWeightTotal()).toString();
			// 货物总体积
			goodsVolumeTotal = CommonUtils.defaultIfNull(entity.getGoodsVolumeTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			// 货物总件数
			goodsQtyTotal = CommonUtils.defaultIfNull(entity.getGoodsQtyTotal()).toString();
			// 开单付款方式
			paidMethod = CommonUtils.getNameFromDict(entity.getPaidMethod(), SETTLEMENTPAYMENTTYPE);
			// 总费用
			totalFee = CommonUtils.defaultIfNull(entity.getTotalFee()).toString();
			// 开单组织
			createOrgCode = CommonUtils.getDeptNameByCode(entity.getCreateOrgCode());
			// 开单人
			createUserCode = StringUtil.defaultIfNull(entity.getCreateUserCode());
			//　开单人名
			createUserName = StringUtil.defaultIfNull(entity.getCreateUserName());
			// 货物类型
			//快递集中录单显示：如果是供应商补录的运单，则开单人显示供应商，制单部门显示供应商部门
			if(StringUtil.equals(createUserCode, "供应商")){
				createOrgCode = "供应商部门";
				createUserName = "供应商";
			}
			goodsTypeCode = CommonUtils.getGoodsTypeByCode(entity.getTransportType(), StringUtil.defaultIfNull(entity.getGoodsTypeCode()), GOODSTYPE);
			// 主键
			id = entity.getId();
		}

		// 获取对象成员保存至一维数组
		Object[] obj = { "", "", active, waybillNo, orderNo, billTime, createTime, deliveryCustomer,deliveryContact,custDept,receiveOrgCode, deliveryCityCode, receiveCityCode, productCode, goodsWeightTotal,
				goodsVolumeTotal, goodsQtyTotal, paidMethod, totalFee, createOrgCode, createUserName,waybillType, goodsTypeCode, id ,isBig13,deliveryBigCustomer};
		return obj;
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
	 * getTransTypeComboBox
	 * 
	 * @return
	 */
	public JComboBox getTransTypeComboBox() {
		return transTypeComboBox;
	}

	/**
	 * setTransTypeComboBox
	 * 
	 * @param transTypeComboBox
	 */
	public void setTransTypeComboBox(JComboBox transTypeComboBox) {
		this.transTypeComboBox = transTypeComboBox;
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
	 * getStatusComboBox
	 * 
	 * @return
	 */
	public JComboBox getStatusComboBox() {
		return statusComboBox;
	}

	/**
	 * setStatusComboBox
	 * 
	 * @param statusComboBox
	 */
	public void setStatusComboBox(JComboBox statusComboBox) {
		this.statusComboBox = statusComboBox;
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
	 * getCheckBox
	 * 
	 * @return
	 */
	public JCheckBox getCheckBox() {
		return checkBox;
	}

	/**
	 * @return the createUserCode
	 */
	public JTextField getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 *            the createUserCode to set
	 */
	public void setCreateUserCode(JTextField createUserCode) {
		this.createUserCode = createUserCode;
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
	 * @return the delButton
	 */
	public JButton getDelButton() {
		return delButton;
	}

	/**
	 * @param delButton
	 *            the delButton to set
	 */
	public void setDelButton(JButton delButton) {
		this.delButton = delButton;
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
	 * @param checkBox
	 *            the checkBox to set
	 */
	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
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
	 * @return the checkBoxColumn
	 */
	public ExpCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	/**
	 * @param checkBoxColumn
	 *            the checkBoxColumn to set
	 */
	public void setCheckBoxColumn(ExpCheckBoxColumn checkBoxColumn) {
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

	public JComboBox getWaybillTypeComboBox() {
		return waybillTypeComboBox;
	}

	public void setWaybillTypeComboBox(JComboBox waybillTypeComboBox) {
		this.waybillTypeComboBox = waybillTypeComboBox;
	}

}
