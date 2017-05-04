package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.FlowLayout;
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
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.CUBCWaybillPushAction;
import com.deppon.foss.module.pickup.creating.client.action.CUBCWaybillResetAction;
import com.deppon.foss.module.pickup.creating.client.action.CUBCWaybillSearchAction;
import com.deppon.foss.module.pickup.creating.client.common.CopyContent;
import com.deppon.foss.module.pickup.creating.client.listener.SalesDeptWaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class CUBCWaybillManagerUI extends JPanel implements IApplicationAware, IPluginAware,ActionListener {
	public CUBCWaybillManagerUI(){
		init();
		initComboBox();
	}
	
	/**
	 *  日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(CUBCWaybillManagerUI.class);
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(CUBCWaybillManagerUI.class);
	
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

	//运单号
	@Bind("mixNo")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "运单号") })
	private JXTextField txtMixNo;

	// 制单时间:开始时间
	@Bind(value = "billStartTime", property = DATE)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "下单时间") })
	private JXDateTimePicker zdStartDate;

	// 制单时间:结束时间
	@Bind(value = "billEndTime", property = DATE)
	private JXDateTimePicker zdEndDate;
	
	//业务状态
	private JComboBox operationStatus;
	
	//业务状态Model
	private DefaultComboBoxModel operationStatusModel;
	
	//选中的列
	private CUBCWaybillCheckBoxColumn checkBoxColumn;
	
	private Plugin plugin;

	// 全选
	private JCheckBox allSelectCheckBox;
	

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = CUBCWaybillSearchAction.class)
	private JButton searchButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
	
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = CUBCWaybillPushAction.class)
	private JButton waybillPushButton = new JButton(i18n.get("foss.gui.creating.CUBCWaybillManagerUI.common.pushAgain"));

	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = CUBCWaybillResetAction.class)
	private JButton resetButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));


	//选中的checkbox
	private List<JCheckBox> list;

	// 结果表
	private JXTable table = new JXTable();
	
	//tableModel
	private CUBCWaybillManageTableModel tableModel;

	// 绑定接口
	private IBinder<SalesDeptWaybillVo> salesDeptWaybillBinder;

	private Map<String, IBinder<SalesDeptWaybillVo>> bindersMap = new HashMap<String, IBinder<SalesDeptWaybillVo>>();

	// 窗口应用程序
	private IApplication application;
	
	/**
	 * 显示异常数据的标签
	 */
	JLabel lblExceptMsg;

	// 选中的运单号
	private List<String> selectExportWaybillNoList = new ArrayList<String>();
	
	private static final int TWO = 2;
	
	private static final int THREE = 3;
	
	private static final int FIVE = 5;
	
	private static final int SEVEN = 7;
	
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
		bind();
		registToRespository();
		// 监听Enter
		CUBCEnterKeySalesDept enterMixNo = new CUBCEnterKeySalesDept(searchButton);
		txtMixNo.addKeyListener(enterMixNo);
		CUBCEnterKeySalesDept enterOrder = new CUBCEnterKeySalesDept(searchButton);
		CUBCEnterKeySalesDept enterCreateUserCode = new CUBCEnterKeySalesDept(searchButton);
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
		 * 业务类型
		 */
		JLabel labelType = new JLabel("业务类型");
		panel.add(labelType,"9, 5, right, default");
		/**
		 * 业务类型的JComboBox下拉框
		 */
		operationStatus = new JComboBox();
		operationStatusModel = (DefaultComboBoxModel)operationStatus.getModel();
		panel.add(operationStatus,"11, 5, fill, default");

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
		 * 重新推送标签的按钮，当没有数据时，此按钮为灰，不可操作
		 */
		tablePanel.add(waybillPushButton, "2, 2, default, top");
		waybillPushButton.setEnabled(false);

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
		tableModel = new CUBCWaybillManageTableModel(getArray(list, TWO));
		table.setModel(tableModel);
		//隐藏相关的列
	    TableColumnModel tableColumnModel = table.getColumnModel();
	    //其实没有移除，仅仅隐藏而已  
	    TableColumn tc = tableColumnModel.getColumn(1);  
	    tableColumnModel.removeColumn(tc);         
		refreshTable(table);

		// 双击已开单记录
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
			}
		});
		
		 //初始化复制内容事件
	    new CopyContent(table,i18n.get("foss.gui.chaning.waybillRFCUI.common.copy"));
	}

	/**
	 * Enter键监听表格
	 */
	public void tableListenter() {
		
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
		CUBCWaybillManageTableModel.adjustColumnPreferredWidths(table);
		table.setSortable(true);
	}

	/**
	 * 初始化下拉框
	 */
	public void initComboBox() {
		// 运单状态(待处理类型)
		initCombPendingType();
		operationStatus.setSelectedIndex(0);
	}

	/**
	 * 初始化运单状态和标签推送状态
	 * @author fei 305082
	 * @date 2016-6-15下午5:12:48
	 */
	@SuppressWarnings({ "unchecked"})
	private void initCombPendingType() {
		//标签推送状态
		operationStatusModel = this.getOperationStatusModel();
		
		DataDictionaryValueVo creating = new DataDictionaryValueVo();
		creating.setValueName(i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.creating"));
		creating.setValueCode("ESB_FOSS2ESB_SYN_BILL_INFO");
		operationStatusModel.addElement(creating);
		
		DataDictionaryValueVo changing = new DataDictionaryValueVo();
		changing.setValueName(i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.changing"));
		changing.setValueCode("ESB_FOSS2ESB_FOSS_CUBC_MODIFY_SYN");
		operationStatusModel.addElement(changing);
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
		WaybillLogEntity dto = (WaybillLogEntity) object;
		//判定转换过来的Dto是否为空
		if(dto == null){
			return null;
		}
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//下单时间
		String billTime = null;
		if(dto.getCreateTime() != null){
			billTime = dateFormat.format(dto.getCreateTime());
		}
		String waybillStatus = null;
		if(WaybillConstants.FAIL.equalsIgnoreCase(dto.getStatu())){
			waybillStatus=i18n.get("foss.gui.creating.waibillImporter.messageDialog.failed");
		}else if(WaybillConstants.SYNC_PENDING.equalsIgnoreCase(dto.getStatu())){
			waybillStatus=i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.syncPending");
		}
		String operationType=null;
		if("ESB_FOSS2ESB_SYN_BILL_INFO".equals(dto.getCode())){
			operationType=i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.creating");
		}else if("ESB_FOSS2ESB_FOSS_CUBC_MODIFY_SYN".equals(dto.getCode())){
			operationType=i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.changing");
		}
		//重新设置运单状态
		dto.setStatu(waybillStatus);
		// 获取对象成员保存至一维数组
		Object[] obj = { "",
				dto.getId(),//ID
				dto.getWaybillNo(),//运单号
				dto.getRequestContent(),//请求体
				dto.getResponseContent(),//响应体
				dto.getVersionNo(),//版本号
				operationType,//操作类型
				billTime,//开单时间
				dto.getStatu(),//运单状态
				dto.getErrorMsg(),//错误信息
				dto.getDesc1(),//接口描述
				dto.getDesc2(),//其他信息
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


	

	public JButton getWaybillPushButton() {
		return waybillPushButton;
	}

	public void setWaybillPushButton(JButton waybillPushButton) {
		this.waybillPushButton = waybillPushButton;
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
	public void setCheckBoxColumn(CUBCWaybillCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}



	public CUBCWaybillCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
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
	
	public JComboBox getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(JComboBox operationStatus) {
		this.operationStatus = operationStatus;
	}

	public DefaultComboBoxModel getOperationStatusModel() {
		return operationStatusModel;
	}

	public void setOperationStatusModel(DefaultComboBoxModel operationStatusModel) {
		this.operationStatusModel = operationStatusModel;
	}

	public JLabel getLblExceptMsg() {
		return lblExceptMsg;
	}

	public void setLblExceptMsg(JLabel lblExceptMsg) {
		this.lblExceptMsg = lblExceptMsg;
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
