/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.order;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.ButtonColumn;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.CommonSortedTableDataUpdater;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.CommonSortedTableModel;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.DefaultNavigator;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.SortedTable;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.WebOrderVo;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWebOrderQueryAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWebOrderQueryResetAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpWebOrderComponentListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmPaymentTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmRefundTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnBillTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.CrmOrderImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWebOrderDialog  extends JFrame {

	//private static final String DEFAULTGROW = "default:grow";

	private static final int FIVE = 5;

	private static final int TEN = 10;
	
	private IWaybillPriceExpressService waybillPriceExpressService;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpWebOrderDialog.class); 

	// 日志
	private static Log log = LogFactory.getLog(ExpWebOrderDialog.class);

	/**
	 * 30
	 */
	private static final int THIRDTY = 30;

	/**
	 * 50
	 */
	private static final int FIFTY = 50;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -2274421445583811673L;

	// 数据字典
	private IDataDictionaryValueService dataDictionaryValueService;
	
	@Bind("orderType")
	private JComboBox combOrderType;

	@Bind("orderNo")
	private JTextField txtOrderNo;

	@Bind("orderStatus")
	private JComboBox combOrderStatus;

	@Bind("deliveryCustomerName")
	private JTextField txtCustomerName;

	@Bind("deliveryCustomerContact")
	private JTextField txtCustomerContact;

	@Bind("deliveryCustomerMobilephone")
	private JTextField txtCustomerMobilephone;

	@Bind("deliveryCustomerPhone")
	private JTextField txtCustomerPhone;
	
	//运输性质（产品类型）
	@Bind("productCode")
	private JComboBox combProductType;

	//查询
	@ButtonAction(icon = "", shortcutKey = "", type = ExpWebOrderQueryAction.class)
	private JButton btnQuery;

	//重置
	@ButtonAction(icon = "", shortcutKey = "", type = ExpWebOrderQueryResetAction.class)
	private JButton btnReset;
	private SortedTable table;

	private WebOrderTableModel tableModel;

	// 绑定
	private IBinder<WebOrderVo> binder = null;

	private DefaultComboBoxModel orderTypeModel;

	private DefaultComboBoxModel orderStatusModel;
	
	//产品类型model
	private DefaultComboBoxModel productTypeModel;

	private ExpWebOrderComponentListener orderComponentListener;

	//
	private WebOrderSortedTableDataUpdater webOrderSortedTableDataUpdater;
	
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	//组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService=GuiceContextFactroy.getInjector().getInstance(OrgAdministrativeInfoService.class);

	// 运单开单UI
	private ExpWaybillEditUI ui;

	// dialog
	private ExpWebOrderDialog dialog = this;

	// 登录用户所属部门信息
	private OrgAdministrativeInfoEntity dept;


	// 客户服务接口
	private ICustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);

	
	/**
	 * LOGGER
	 */
	private Logger logger = Logger.getLogger(ExpWebOrderDialog.class);

	private JLabel label1;

	private JLabel label2;

	private JXDatePicker datePickerStart;

	private JXDatePicker datePickerEnd;

	private DefaultNavigator navigator;

	public ExpWebOrderDialog(ExpWaybillEditUI ui) {
		//super(ApplicationContext.getApplication().getWorkbench().getFrame());
		dataDictionaryValueService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
		orgAdministrativeInfoService = GuiceContextFactroy.getInjector().getInstance(OrgAdministrativeInfoService.class);
		waybillPriceExpressService= GuiceContextFactroy.getInjector().getInstance(WaybillPriceExpressService.class);
		this.ui = ui;
		init();
		initVo();
		bind();
		createListener();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		//网上订单
		setTitle(i18n.get("foss.gui.creating.webOrderDialog.title"));
		//setModal(true);
		setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_600);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(46dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("35dlu:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));

		//开始时间
		label1 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.datePickerStart.label")+"：");
		getContentPane().add(label1, "2, 2, right, default");

		datePickerStart = new JXDatePicker();
		Date stratDate = new Date();
		Date endDate = new Date();
		stratDate.setDate(stratDate.getDate() - NumberConstants.NUMBER_3);
		datePickerStart.setDate(stratDate);
		getContentPane().add(datePickerStart, "4, 2");

		//结束时间
		label2 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.datePickerEnd.label")+"：");
		getContentPane().add(label2, "6, 2, right, default");

		datePickerEnd = new JXDatePicker();
		datePickerEnd.setDate(endDate);
		getContentPane().add(datePickerEnd, "8, 2");

		//订单类型
		JLabel label = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.orderType.label")+"：");
		getContentPane().add(label, "10, 2, right, default");

		combOrderType = new JComboBox();
		orderTypeModel = (DefaultComboBoxModel) combOrderType.getModel();
		getContentPane().add(combOrderType, "12, 2, fill, default");

		//订单编号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.orderNo.label")+"：");
		getContentPane().add(label1, "14, 2, right, default");

		txtOrderNo = new JTextField();
		getContentPane().add(txtOrderNo, "16, 2, fill, default");
		txtOrderNo.setColumns(TEN);
		
		//受理状态
		JLabel label2_1 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.orderStatus.label")+"：");
		getContentPane().add(label2_1, "18, 2, right, default");

		combOrderStatus = new JComboBox();
		orderStatusModel = (DefaultComboBoxModel) combOrderStatus.getModel();
		getContentPane().add(combOrderStatus, "20, 2, fill, default");

		//查询
		btnQuery = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		Dimension dimension = new Dimension(FIFTY, THIRDTY);
		btnQuery.setMinimumSize(dimension);
		btnQuery.setMaximumSize(dimension);
		btnQuery.setPreferredSize(dimension);
		getContentPane().add(btnQuery, "22, 2");

		//发货客户
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.customerName.label")+"：");
		getContentPane().add(label3, "2, 4, right, default");

		txtCustomerName = new JTextField();
		getContentPane().add(txtCustomerName, "4, 4, fill, default");
		txtCustomerName.setColumns(TEN);

		//发货联系人
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.customerContact.label")+"：");
		getContentPane().add(label4, "6, 4, right, default");

		txtCustomerContact = new JTextField();
		getContentPane().add(txtCustomerContact, "8, 4, fill, default");
		txtCustomerContact.setColumns(TEN);

		//发货联系人手机
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.customerMobile.label")+"：");
		getContentPane().add(label5, "10, 4, right, default");

		txtCustomerMobilephone = new JTextField();
		getContentPane().add(txtCustomerMobilephone, "12, 4, fill, default");
		txtCustomerMobilephone.setColumns(TEN);

		//发货联系人电话
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.customerPhone.label")+"：");
		getContentPane().add(label6, "14, 4, right, default");

		txtCustomerPhone = new JTextField();
		getContentPane().add(txtCustomerPhone, "16, 4, fill, default");
		txtCustomerPhone.setColumns(TEN);
		
		//运输性质（产品类型）
		JLabel label_1 = new JLabel(i18n.get("foss.gui.creating.printSignUI.product.label")+"：");
		getContentPane().add(label_1, "18, 4, right, default");

		combProductType = new JComboBox();
		productTypeModel = (DefaultComboBoxModel) combProductType.getModel();
		getContentPane().add(combProductType, "20, 4, fill, default");

		//重置
		btnReset = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));
		getContentPane().add(btnReset, "22, 4");
		btnReset.setMinimumSize(dimension);
		btnReset.setMaximumSize(dimension);
		btnReset.setPreferredSize(dimension);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "2, 6, 21, 1, fill, fill");

		tableModel = new WebOrderTableModel();
		webOrderSortedTableDataUpdater = new WebOrderSortedTableDataUpdater();
		tableModel.setDataUpdater(webOrderSortedTableDataUpdater);

		table = new SortedTable();
		table.setAutoscrolls(true);
		table.setHorizontalScrollEnabled(true);
		table.setColumnControlVisible(true);
		table.setModel(tableModel);

		// 添加Button样式
		ButtonColumn buttonColumn = TableFactory.createButtonColumn(table, 0);
		buttonColumn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					importOrder();
				} catch (BusinessException e1) {
					e1.printStackTrace();
					String message = "";
					String code = StringUtil.defaultIfNull(e1.getErrorCode());
					String code2 = StringUtil.defaultIfNull(e1.getMessage());
					if(code.trim().equals(code2.trim())){
						message = code;
					}else{
						message = code+"\n"+code2;
					}
					if(StringUtils.isNotEmpty(message)){
						MsgBox.showError(message);
						return ;
					}
				}
			}
		});

		scrollPane.setViewportView(table);

		navigator = new DefaultNavigator(tableModel);

		// 显示第一页
		navigator.initPageStatus();

		getContentPane().add(navigator, "2, 8, 21, 1, fill, fill");

	}

	private void initVo() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		dept = user.getEmployee().getDepartment();
	}

	private void importOrder() { 
		ExpWaybillPanelVo panelVo = ui.getBindersMap().get("waybillBinder").getBean();
		int row = table.getSelectedRow();
		int modelRow = table.convertRowIndexToModel(row);
		CrmOrderInfo webOrder = tableModel.getData().get(modelRow);
		// 若导入的订单状态非受理状态，则提示订单不能导入,就是说只有受理中，已退回，接货中的订单能导入
		if (!WaybillConstants.CRM_ORDER_ACCEPT.equals(webOrder.getOrderStatus())
				&&!WaybillConstants.CRM_ORDER_GOBACK.equals(webOrder.getOrderStatus())
				&&!WaybillConstants.CRM_ORDER_RECEIPTING.equals(webOrder.getOrderStatus())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderStatus",webOrder.getOrderStatusName()));
			return;
		}
		//电子订单不能导入
		if(webOrder.getOrderNumber()!=null){
			String waybillType = waybillService.queryWaybillTypeByOrderNo(webOrder.getOrderNumber());
			if(WaybillConstants.EWAYBILL.equals(waybillType)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.ewaybill"));
				return;
			}
		}
		CrmOrderDetailDto orderDetailVo = null;
		try {
			orderDetailVo = waybillService.importOrder(webOrder.getOrderNumber());
		} catch (BusinessException e) {
			MsgBox.showInfo(MessageI18nUtil.getMessage(e, i18n));
			return;
		}
		// 按完整订单号查询，查询到的订单已开单，则在点击“查询按钮”时，提示订单已开单
		if (StringUtil.isNotEmpty(orderDetailVo.getWaybillNumber()) ) {
			if(!WaybillConstants.OBSOLETE.equals(orderDetailVo.getActualFreightStatus()) && 
				!WaybillConstants.ABORTED.equals(orderDetailVo.getActualFreightStatus())){
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderAccepted"));
				return;
			}
		}
		
		//订单受理部门
		String accecptDept = StringUtil.defaultIfNull(orderDetailVo.getAcceptDept());
		//部门标杆编码
		String unifiedCode = "";
		if(null != dept){
			unifiedCode = StringUtil.defaultIfNull(dept.getUnifiedCode());
		}
		
		//若为集中开单，取收货部门的标杆编码
		if(dept != null && FossConstants.YES.equals(dept.getBillingGroup())){
			OrgAdministrativeInfoEntity orgEntity = waybillService.queryByCode(panelVo.getReceiveOrgCode());
			unifiedCode = orgEntity.getUnifiedCode();
		}
		// 若导入的订单的始发部门非本部门时，系统提示营业员，营业员确认后，对应选择的订单的详细信息导入至运单开单界面中
		if (!accecptDept.equals(unifiedCode)) {
			if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(null, 
					i18n.get("foss.gui.creating.webOrderDialog.showdialog.label"), 
					i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.YES_NO_OPTION)) {
				return;
			} else {
				//该订单的受理部门为空，是否继续？
				if(StringUtils.isEmpty(accecptDept)){
					if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(null,
							i18n.get("foss.gui.creatingexp.expWebOrderDialog.msgbox.acceptDeptNull"), 
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.YES_NO_OPTION)) {
						return;
					} 
				}
				if (panelVo.getPickupCentralized()) {
					orderDetailVo.setAcceptDept(panelVo.getReceiveOrgCode());
				} else {
					if (dept != null) {
						orderDetailVo.setAcceptDept(dept.getCode());
					}
				}
			}
		} else {
			if (dept != null) {
				orderDetailVo.setAcceptDept(dept.getCode());
			}
		}
		//把crm的code转化为foss
		orderDetailVo.setTransportMode(webOrder.getTransportModeFossCode());
				
		setOrderToWaybillEditUI(orderDetailVo, panelVo);
		//来自官网的订单，导入成功后设置代收和保价不可编辑 
		if(WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(webOrder.getResource())){
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
		}		
		/**
		 * 如果代收货款大于0，则给予提示，选择目的站时会验证是否能开代收货款
		 */
		if(panelVo.getCodAmount()!=null && panelVo.getCodAmount().intValue()>0){
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.codAmount.alert"));
		}
		/**
		 * Dmana-9885通过订单来源判断如果是巨商网来源，付款方式不可更改
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-10上午09:29
		 */
		if(WaybillConstants.ALIBABA.equals(panelVo.getOrderChannel())){
			List<DataDictionaryValueEntity> list = waybillService.queryPaymentMode();
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				if (WaybillConstants.MONTH_PAYMENT.equals(vo.getValueCode()))
				{
					panelVo.setPaidMethod(vo);
				}
			}
			ui.incrementPanel.getCombPaymentMode().setEnabled(false);
		}
		//liding comment for NCI
		/**
		 * 通过付款方式判断“交易流水号”是否可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-26上午10:40
		 */
//		ExpCommon.whetherBankCardPayment(panelVo, ui);
		dispose();
	}
	
	/**
	 * 校验订单来源是否可以导入订单开单 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-23 下午7:32:39
	 */
	private void validateOrderChannel(CrmOrderDetailDto orderDetailVo, ExpWaybillPanelVo panelVo) {
		if(null != dept){
			//营业部信息
			SalesDepartmentCityDto salesDto = null;
    		//若为集中开单，取收货部门编码;否则获取当前部门编码
    		if(FossConstants.YES.equals(dept.getBillingGroup())){
    			salesDto = CommonUtils.getSalesDepartmentCityDtoByCode(StringUtil.defaultIfNull(panelVo.getReceiveOrgCode()));
    		}else{
    			salesDto = CommonUtils.getSalesDepartmentCityDtoByCode(StringUtil.defaultIfNull(dept.getCode()));
    		}
    		
    		//非空判断
    		if(null != salesDto){
    			// 订单来源
    			String orderChannel = StringUtil.defaultIfNull(orderDetailVo.getResource());
    			// 非试点城市和其他城市之间只能开淘宝、阿里巴巴、淘宝商场订单
    			if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(salesDto.getCityType())) {
    				if (!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(orderChannel)//淘宝
    						&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(orderChannel)//阿里巴巴
    						&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG.equals(orderChannel)) {//淘宝商城
    					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoChannel"));
    				}
    			}
    			
    			SaleDepartmentEntity salesDept = null;
        		if(salesDto.getSalesDepartmentCode()!=null){
        			SaleDepartmentEntity org = waybillService.querySaleDeptByCode(salesDto.getSalesDepartmentCode());
        			salesDept = org;
        		}
        		
        		if(salesDept!=null){
        			//营业部是否可以快递接货，如果是的话 就是试点营业部
        			salesDto.setTestSalesDepartment(StringUtil.defaultIfNull(salesDept.getCanExpressPickupToDoor()));
            		panelVo.setCreateSalesDepartmentCityDto(salesDto);
        		}
    		}
    		
    		//合作伙伴开单付款方式为“网上支付”时
    		if(BZPartnersJudge.IS_PARTENER){
    			String paymentType = orderDetailVo.getPaymentType();
    			for (CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()) {
    				// 订单付款方式
    				if (crm.getCrmCode().equals(paymentType)) {
    					panelVo.setOrderPayment(crm.getFossCode());
    				}
    			}
    			if(WaybillConstants.ONLINE_PAYMENT.equals(panelVo.getOrderPayment())){
    				//该订单不支持网上支付，请选择其他付款方式
    				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateNSOrderPayment"));
    			}
    			//订单的付款方式为“月结”时
    			// 判断客户是否月结
    			if(WaybillConstants.MONTH_PAYMENT.equals(panelVo.getOrderPayment())){
    				CusBargainVo vo = new CusBargainVo();
    				vo.setExpayway(WaybillConstants.MONTH_END);
    				vo.setCustomerCode(panelVo.getDeliveryCustomerCode());
    				vo.setBillDate(new Date());
    				vo.setBillOrgCode(panelVo.getReceiveOrgCode());
    				boolean  isOk = waybillService.isCanPaidMethodExp(vo);
    				//校验该客户编码是否存在月结合同信息，若不存在则提示：请选择其他付款方式
    				if (!isOk) {
    				throw new WaybillValidateException(
    						i18n.get("foss.gui.creating.calculateAction.exception.notMonth"));
    			}

    			}
    		}
		}
	}

	/**
	 * 
	 * 导入订单到开单界面
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 下午4:46:16
	 */
	private void setOrderToWaybillEditUI(CrmOrderDetailDto orderDetailVo, ExpWaybillPanelVo panelVo) {
		log.info(ToStringBuilder.reflectionToString(orderDetailVo));
		//设置前进行检验
		validateOrderChannel(orderDetailVo,panelVo);
		// 设置订单号
		ui.numberPanel.getLblOrderLabel().setVisible(true);
		ui.numberPanel.getLblOrderNumber().setText(orderDetailVo.getOrderNumber());

		// 来自阿里巴巴的订单不可编辑装卸费，取Resource是由于跟CRM沟通过，channelType在官网，呼叫中心，营业部订单这些类型在这个字段里面是空的，需要去resource才能取到值
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(orderDetailVo.getResource()) || WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT.equals(orderDetailVo.getResource())) {
			ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
		}
		// 来自官网的订单设置保价金额，代收货款金额不可编辑ISSUE-1452
		// ,取Resource是由于跟CRM沟通过，channelType在官网，呼叫中心，营业部订单这些类型在这个字段里面是空的，需要去resource才能取到值
		else if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(orderDetailVo.getResource())) {
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
		}
		
		try {
			// 订单号
			panelVo.setOrderNo(orderDetailVo.getOrderNumber());
			// 订单来源
			panelVo.setOrderChannel(orderDetailVo.getResource());
			// 若为集中开单则收入部门为受理部门
//			if (null != dept) {
//				panelVo.setReceiveOrgCode(StringUtil.defaultIfNull(orderDetailVo.getAcceptDept()));
//				panelVo.setReceiveOrgName(StringUtil.defaultIfNull(orderDetailVo.getAcceptDeptName()));
//			}

			String paymentType = orderDetailVo.getPaymentType();
			for (CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(paymentType)) {
					panelVo.setOrderPayment(crm.getFossCode());
				}
			}
			// 发货客户id
			panelVo.setDeliveryCustomerId(orderDetailVo.getShipperId());
			// 发货客户姓名
			panelVo.setDeliveryCustomerName(orderDetailVo.getShipperName());
			// 发货联系人id
			panelVo.setDeliveryCustomerContactId(orderDetailVo.getContactManId());
			// 发货联系人名称
			panelVo.setDeliveryCustomerContact(orderDetailVo.getContactName());
			// 联系人手机
			panelVo.setDeliveryCustomerMobilephone(orderDetailVo.getContactMobile());
			// 联系人电话
			panelVo.setDeliveryCustomerPhone(orderDetailVo.getContactPhone());
			// 发货客户编号
			/**
			 * 将代码进行了修改，代表目的不变
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-11-19下午17:03
			 */
			String shipperNumber=orderDetailVo.getShipperNumber();
			if (StringUtils.isNotEmpty(shipperNumber)) {
				panelVo.setDeliveryCustomerCode(shipperNumber);
				/**
				 * 通过客户编码查询综合客户信息，取得特安上限值,并赋值给waybillpanelvo的特安上限保价和代收货款保价
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-11-19下午15:34
				 */
				CustomerDto customerDto = waybillService.queryCustInfoByCodeNew(shipperNumber);
				if(customerDto!=null&&customerDto.getTeanLimit()!=null){
					BigDecimal vipInsuranceAmount=new BigDecimal(customerDto.getTeanLimit());
					panelVo.setVipInsuranceAmount(vipInsuranceAmount);
					panelVo.setVipCollectionPaymentLimit(vipInsuranceAmount);
				}
			} else {
				List<CustomerQueryConditionDto> contactLis = deliveryCustomerMobilephoneListener(panelVo);
				if(contactLis.size()==0){
		    		panelVo.setDeliveryCustomerCode(null);
		    	}else{
		    	    for(CustomerQueryConditionDto contactLi : contactLis){
		    		   if(contactLi!=null){
		    		   panelVo.setDeliveryCustomerCode(contactLi.getCustCode());
		    		}
		    	 }
		    	}

			}
			//如果客户不为空，查询客户信息,设置是否统一结算wutao
			if(StringUtils.isNotBlank(panelVo.getDeliveryCustomerCode())){
				// 设置发货快递大客户
				List<CustomerQueryConditionDto> deliveryCustomerList = new ArrayList<CustomerQueryConditionDto>();
				CustomerQueryConditionDto customerQueryConditionDto = new CustomerQueryConditionDto();				
				customerQueryConditionDto.setCustCode(panelVo.getDeliveryCustomerCode());				
				deliveryCustomerList.add(customerQueryConditionDto);
				CustomerQueryConditionDto customerQueryConditionDtoDev = null;
				List<CustomerQueryConditionDto> customerQueryConditionDtoList = customerService.queryCustomerByConditionList(deliveryCustomerList);						
				if (customerQueryConditionDtoList.size() > 0) {
					customerQueryConditionDtoDev = customerQueryConditionDtoList.get(0);
					if(customerQueryConditionDtoDev!=null){
						if (FossConstants.ACTIVE.equals(customerQueryConditionDtoDev.getIsExpressBigCust())) {
							// 设置大客户标记
							panelVo.setDeliveryBigCustomer(FossConstants.ACTIVE);
							ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
						} else {
							// 取消大客户标记
							panelVo.setDeliveryBigCustomer(FossConstants.INACTIVE);
							ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
						}
					}					
				}
				CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
		    	contact.setCustCode(panelVo.getDeliveryCustomerCode());
		    	CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
		    	if(customerQueryConditionDtoDev!=null){
		    		ExpCommonUtils.setExpDevliveryCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, panelVo);	
		    	}		    	
			}			
			
			// 是否接货
			panelVo.setPickupToDoor(orderDetailVo.isReceiveGoods());

			String proviceCode = null;
			if (orderDetailVo.getContactProvince() != null) {
				//proviceCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_PROVINCE, orderDetailVo.getContactProvince(), null);
				proviceCode = orderDetailVo.getContactProvinceCode();
			}
			// 联系人省份
			panelVo.setDeliveryCustomerProvCode(proviceCode);

			String cityCode = null;
			if (orderDetailVo.getContactCity() != null && proviceCode != null) {
				//cityCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_CITY, orderDetailVo.getContactCity(), proviceCode);
				cityCode = orderDetailVo.getContactCityCode();
			}
			// 联系人城市
			panelVo.setDeliveryCustomerCityCode(cityCode);

			String areaCode = null;
			if (orderDetailVo.getContactArea() != null && cityCode != null) {
				//areaCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_COUNTY, orderDetailVo.getContactArea(), cityCode);
				areaCode = orderDetailVo.getContactAreaCode();
			}
			// 联系人区县
			panelVo.setDeliveryCustomerDistCode(areaCode);

			// 联系地址
			panelVo.setDeliveryCustomerAddress(orderDetailVo.getContactAddress());
			// 联系地址
			panelVo.setDeliveryCustomerAddressNote(orderDetailVo.getContactAddrRemark());
		    //收货人地址备注
		    panelVo.setReceiveCustomerAddressNote(orderDetailVo.getReceiverCustAddrRemark());
			// 定义省市区县对象
			AddressFieldDto deliverAddressDto = ExpCommon.getAddressFieldInfoByCode(ui, proviceCode, cityCode, areaCode);
			ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(deliverAddressDto);

			// 始发网点
			String orgCode = panelVo.getReceiveOrgCode();
			// if has received org code no need set again
			if (StringUtils.isEmpty(orgCode)) {
				orgCode = orderDetailVo.getAcceptDept();
			}
			panelVo.setReceiveOrgCode(orgCode);

			// 货部门省份编码
			if (StringUtil.isNotEmpty(orgCode)) {
				panelVo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(orgCode));
			}

			if (orgCode != null) {
				SaleDepartmentEntity org = waybillService.querySaleDeptByCode(orgCode);

				if (org != null) {
					// 始发网点 create time
					panelVo.setReceiveOrgName(org.getName());
					if (org.getOpeningDate() != null) {
						panelVo.setReceiveOrgCreateTime(org.getOpeningDate());
					}
				}
			}
			// 渠道客户ID：官网用户名
			panelVo.setChannelCustId(orderDetailVo.getChannelCustId());

			// 收货客户id
			panelVo.setReceiveCustomerId(orderDetailVo.getReceiverCustId());
			//　收货客户名称
			panelVo.setReceiveCustomerName(orderDetailVo.getReceiverCustName());
			// 收货人联系姓名
			panelVo.setReceiveCustomerContact(orderDetailVo.getReceiverCustName());
			// 接货人联系手机
			panelVo.setReceiveCustomerMobilephone(orderDetailVo.getReceiverCustMobile());
			// 接货人联系电话
			panelVo.setReceiveCustomerPhone(orderDetailVo.getReceiverCustPhone());
			 // 收货客户编号
		    if(orderDetailVo.getReceiverCustNumber()!=null){
		        panelVo.setReceiveCustomerCode(orderDetailVo.getReceiverCustNumber());
		    }else{
		    	List<CustomerQueryConditionDto> contactLis = receiveCustomerMobilephoneListener(panelVo);
		    	if(null==contactLis||contactLis.size()==0){
		    		panelVo.setReceiveCustomerCode(null);
		    	}else{
		    	   for(CustomerQueryConditionDto contactLi : contactLis){
		    		  if(contactLi!=null){
		    		   panelVo.setReceiveCustomerCode(contactLi.getCustCode());
		    	  }
		    	}
		      }
		    }

		    //如果客户不为空，查询客户信息,设置是否统一结算wutao
			if(StringUtils.isNotBlank(panelVo.getReceiveCustomerCode())){
				List<CustomerQueryConditionDto> receivercCustomerList = new ArrayList<CustomerQueryConditionDto>();    
				// 设置发货快递大客户
			    CustomerQueryConditionDto customerQueryConditionDtoReceiver = new CustomerQueryConditionDto();			    
			    customerQueryConditionDtoReceiver.setCustCode(panelVo.getReceiveCustomerCode());			    
			    receivercCustomerList.add(customerQueryConditionDtoReceiver);
			    CustomerQueryConditionDto customerQueryConditionDtoRev = null;
			    List<CustomerQueryConditionDto> customerQueryConditionDtoReceivercList = customerService.queryCustomerByConditionList(receivercCustomerList);				
				if (customerQueryConditionDtoReceivercList.size() > 0) {
					customerQueryConditionDtoRev= customerQueryConditionDtoReceivercList.get(0);
					if (FossConstants.ACTIVE.equals(customerQueryConditionDtoRev.getIsExpressBigCust())) {
						// 设置大客户标记
						panelVo.setReceiveBigCustomer(FossConstants.ACTIVE);
						ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
					} else {
						// 取消大客户标记
						panelVo.setReceiveBigCustomer(FossConstants.INACTIVE);
						ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
					}
				}				
				if(null!=customerQueryConditionDtoRev){
			    	CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
			    	contact.setCustCode(panelVo.getReceiveCustomerCode());
			    	CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
			    	ExpCommonUtils.setExpReciveCusomterSettler(customerQueryConditionDtoRev, cusBargainEntity, panelVo);
			    }
			}
			
			
			proviceCode = null;// 清空
			if (orderDetailVo.getReceiverCustProvince() != null) {
				//proviceCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_PROVINCE, orderDetailVo.getReceiverCustProvince(), null);
				proviceCode = orderDetailVo.getReceiverCustProvinceCode();
			}
			// 接货人省份
			panelVo.setReceiveCustomerProvCode(proviceCode);
			cityCode = null;// 清空
			if (orderDetailVo.getReceiverCustCity() != null && proviceCode != null) {
				//cityCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_CITY, orderDetailVo.getReceiverCustCity(), proviceCode);
				cityCode = orderDetailVo.getReceiverCustCityCode();
			}
			// 接货人城市
			panelVo.setReceiveCustomerCityCode(cityCode);
			areaCode = null;// 清空
			if (orderDetailVo.getReceiverCustArea() != null && cityCode != null) {
				//areaCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_COUNTY, orderDetailVo.getReceiverCustArea(), cityCode);
				areaCode = orderDetailVo.getReceiverCustAreaCode();
			}
			// 接货人区县
			panelVo.setReceiveCustomerDistCode(areaCode);

			// 接货人详细地址
			panelVo.setReceiveCustomerAddress(orderDetailVo.getReceiverCustAddress());
			// 接货人详细地址
			panelVo.setReceiveCustomerAddressNote(orderDetailVo.getReceiverCustAddrRemark());

			// 定义省市区县对象
			AddressFieldDto receiverAddressDto = ExpCommon.getAddressFieldInfoByCode(ui, proviceCode, cityCode, areaCode);
			ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(receiverAddressDto);

			// 到达网点 DP00772
			String receivingToPointUnifiedCode = orderDetailVo.getReceivingToPoint();
			if (StringUtils.isNotEmpty(receivingToPointUnifiedCode)) {
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(receivingToPointUnifiedCode);
				if (orgEntity != null) {

					SaleDepartmentEntity saleDepartment = waybillService.querySaleDeptByCode(orgEntity.getCode());

					if (saleDepartment != null) {

						BranchVo branchVo = new BranchVo();
						branchVo.setCode(saleDepartment.getCode());
						branchVo.setName(saleDepartment.getName());
						panelVo.setCustomerPickupOrgCode(branchVo);

						// 设置提货网点名称
						panelVo.setCustomerPickupOrgName(branchVo.getName());

						ExpShowPickupStationDialogAction showPickupStationDialogAction = new ExpShowPickupStationDialogAction();
						showPickupStationDialogAction.setInjectUI(ui);
						showPickupStationDialogAction.setDialogData(branchVo, panelVo);
						showPickupStationDialogAction.setLoadLine(panelVo);
						showPickupStationDialogAction.setAirDeptEnabled(panelVo);
					}

				}
			}
			//
			// 货物名称
			panelVo.setGoodsName(orderDetailVo.getGoodsName());
			// 托运货物总件数
			panelVo.setGoodsQtyTotal(orderDetailVo.getGoodsNumber());
			// 托运货物总重量
			panelVo.setGoodsWeightTotal(BigDecimal.valueOf(orderDetailVo.getTotalWeight()));
			// 货物总体积
			panelVo.setGoodsVolumeTotal(BigDecimal.valueOf(orderDetailVo.getTotalVolume()));
			// 货物包装类型
			panelVo.setGoodsPackage(orderDetailVo.getPacking());
			// 货物类型
			panelVo.setGoodsType(orderDetailVo.getGoodsType());
			// 报价申明价值
			panelVo.setInsuranceAmount(orderDetailVo.getInsuredAmount());
			panelVo.setInsuranceAmountCanvas(orderDetailVo.getInsuredAmount().toString());
			// 代收货款类型

			String reciveLoanType = orderDetailVo.getReciveLoanType();

			for (CrmRefundTypeEnum crm : CrmRefundTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(reciveLoanType)) {
					String fossCode = crm.getFossCode();
					if (StringUtils.isNotEmpty(fossCode)) {
						panelVo.setRefundType(dataDictionaryValueEntityToVo(WaybillConstants.REFUND_TYPE, crm.getFossCode()));
					}
				}

			}

			// 代收货款金额
			panelVo.setCodAmount(orderDetailVo.getReviceMoneyAmount());
			panelVo.setCodAmountCanvas(orderDetailVo.getReviceMoneyAmount().toString());
			// 签收单

			String crmReturnBillType = orderDetailVo.getReturnBillType();

			for (CrmReturnBillTypeEnum crm : CrmReturnBillTypeEnum.values()) {
				// 订单返单方式
				if (crm.getCrmCode().equals(crmReturnBillType)) {
					DataDictionaryValueVo returnBillType = dataDictionaryValueEntityToVo(WaybillConstants.RETURN_BILL_TYPE, crm.getFossCode());
					if (returnBillType == null) {
						returnBillType = new DataDictionaryValueVo();
						returnBillType.setValueCode(WaybillConstants.NOT_RETURN_BILL);
					}
					panelVo.setReturnBillType(returnBillType);
				}

			}

			// 付款方式
			for (CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(paymentType)) {
					panelVo.setPaidMethod(dataDictionaryValueEntityToVo(WaybillConstants.PAYMENT_MODE, crm.getFossCode()));
				}

			}

			// 储运事项
			panelVo.setTransportationRemark(orderDetailVo.getTransNote());

			// 发货人区域信息
			String deliveryProvinve = orderDetailVo.getContactProvince();
			String deliveryCity = orderDetailVo.getContactCity();
			String deliveryArea = orderDetailVo.getContactArea();

			if (deliveryProvinve != null && deliveryCity != null && deliveryArea != null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve + "-" + deliveryCity + "-" + deliveryArea);
			} else if (deliveryProvinve != null && deliveryCity != null && deliveryArea == null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve + "-" + deliveryCity);
			} else if (deliveryProvinve != null && deliveryCity == null && deliveryArea == null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve);
			}

			// 收货人区域信息
			String receiveProvinve = orderDetailVo.getReceiverCustProvince();
			String receiveCity = orderDetailVo.getReceiverCustCity();
			String receiveArea = orderDetailVo.getReceiverCustArea();

			if (receiveProvinve != null && receiveCity != null && receiveArea != null) {
				panelVo.setReceiveCustomerArea(receiveProvinve + "-" + receiveCity + "-" + receiveArea);
			} else if (receiveProvinve != null && receiveCity != null && receiveArea == null) {
				panelVo.setReceiveCustomerArea(receiveProvinve + "-" + receiveCity);
			} else if (receiveProvinve != null && receiveCity == null && receiveArea == null) {
				panelVo.setReceiveCustomerArea(receiveProvinve);
			}

			// 货物运输方式
			ProductEntityVo productEntity = getProductTypeByCode(orderDetailVo.getTransportMode());
			panelVo.setProductCode(productEntity);

			if (productEntity != null && productEntity.getCode() != null) {
				// 根据运输性质改变提货方式
				ExpCommon.changePickUpMode(panelVo, ui);
				// 空运、偏线以及中转下线无法选择签收单返单
				ExpCommon.setReturnBill(panelVo, ui);
				// 偏线与空运不能选择预付费保密
				ExpCommon.setSecretPrepaid(panelVo, ui);

				// 获取返单类别
				DataDictionaryValueVo returnBillType = panelVo.getReturnBillType();
				// 清空其他费用列表
				ExpCommon.cleanOtherCharge(panelVo, ui);
				// 重新设置返单类别(和网上订单相同)
				panelVo.setReturnBillType(returnBillType);
			}

			ExpCommon.setSaveAndSubmitFalse(ui);

			if (orderDetailVo.getShipperId() != null) {
				CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
				String shipperNumer = orderDetailVo.getShipperNumber();
				if (StringUtils.isEmpty(shipperNumer)) {
					shipperNumer = orderDetailVo.getShipperId();
				}
				condition.setCustCode(shipperNumer);
				List<CustomerQueryConditionDto> dtoList = waybillPriceExpressService.queryCustomerByCondition(condition);
				if (dtoList == null || dtoList.size() == 0) {
					panelVo.setChargeMode(false);// 是否月结
					// 优惠类型
					panelVo.setPreferentialType("");
				} else {
					CustomerQueryConditionDto dtoCust = null;
					for (int i = 0; i < dtoList.size(); i++) {
						CustomerQueryConditionDto dtoCust2 = dtoList.get(i);
						if (dtoCust2 != null && dtoCust2.getCustCode() != null && dtoCust2.getCustCode().equals(shipperNumer)) {
							dtoCust = dtoCust2;
							break;
						}
					}

					if (dtoCust != null) {
						/**
						 * DEFECT-1525快递开单，导入订单中带入的发货客户是月结客户，提示不是月结客户不能开单月结。
						 */
						panelVo.setChargeMode(DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(dtoCust.getExPayWay()) ? Boolean.valueOf(true) : Boolean.valueOf(false));
						panelVo.setPreferentialType(dtoCust.getPreferentialType());// 设置优惠类型
						panelVo.setIsElectricityToEnjoy(dtoCust.getIfElecEnjoy());
					}

				}
			} else {
				//巨商汇阿里商城默认月结
				if(WaybillConstants.ALIBABA.equals(orderDetailVo.getResource())){
					panelVo.setChargeMode(true);// 是否月结
				}else{
					panelVo.setChargeMode(false);// 是否月结	
				}				
				// 优惠类型
				panelVo.setPreferentialType("");
			}

			// 有来源
			panelVo.setOrderChannel(orderDetailVo.getResource());

			// 付款方式
			panelVo.setOrderPayment(orderDetailVo.getPaymentType());

			// CRM提货方式
//			String crmDeliveryMode = orderDetailVo.getDeliveryMode();
//
//			// 提货方式
//			panelVo.setReceiveMethod(Common.getCombBoxValue(ui.getPikcModeModel(), crmDeliveryMode));

			 // CRM提货方式
		    String crmDeliveryMode = orderDetailVo.getDeliveryMode();	    
		    
		    //根据运输性质获取提货方式
		  //根据汽运获取提货方式
	    	DataDictionaryValueVo vo=dataDictionaryValueEntityToVo(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN,CommonUtils.convertCrmReceiveMethodByCrmCode(crmDeliveryMode));
	    	if(vo==null){
	    		//根据空运获取提货方式
	    		vo=dataDictionaryValueEntityToVo(WaybillConstants.PICKUP_GOODS_AIR,CommonUtils.convertCrmReceiveMethodByCrmCode(crmDeliveryMode));
	    	}
			
	    	if(vo != null){
	    		//设置提货方式
	    		panelVo.setReceiveMethod(vo); 	  
	    	}
			// 提货方式
			if (panelVo.getReceiveMethod() != null) {
				// 内部带货
				ExpCommon.innerPickup(panelVo, ui);
				// 各种自提
				ExpCommon.selfPickup(panelVo, ui);

				// 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
				ExpCommon.validateCustomerPointBySelfPickup(panelVo, ui);

				// 送货进仓
				// Common.deliverStorge(panelVo, ui, waybillService);

				ExpCommon.setSaveAndSubmitFalse(ui);
			}
			/**
			 * 将orderDetailVo中的是否电子发票转移到panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28上午09:25
			 */
			panelVo.setIsElectronicInvoice(orderDetailVo.getIsElectronicInvoice());
			/**
			 * 将orderDetailVo中电子发票号码转移到panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28上午09:25
			 */
			panelVo.setInvoiceMobilePhone(orderDetailVo.getInvoiceMobilePhone());
			/**
			 * 将orderDetailVo中电子发票邮箱转移到panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28上午09:25
			 */
			panelVo.setEmail(orderDetailVo.getEmail());
			/**
			 * 根据Dmana-9885将CRM运费转入panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-03-10上午09:16
			 */
			panelVo.setCrmTransportFee(orderDetailVo.getCrmTransportFee());
			/**
			 * 根据Dmana-9885将CRM重量转入panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-03-10上午09:16
			 */
			panelVo.setCrmWeight(new BigDecimal(orderDetailVo.getTotalWeight()));
			/**
			 * 根据Dmana-9885将CRM体积转入panelVo中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-03-10上午09:16
			 */
			panelVo.setCrmVolume(new BigDecimal(orderDetailVo.getTotalVolume()));
			dispose();
		} catch (Exception e) {
			logger.error("订单导入失败", e);
			e.printStackTrace();
		}
	}
	
	 /**
		 * 
		 * （发货人手机号码监听）
		 * 
		 * @author FOSS-叶涛
		 * @date 2012-10-20 上午10:52:04
		 */
		public List<CustomerQueryConditionDto> deliveryCustomerMobilephoneListener(ExpWaybillPanelVo bean) {
			// 发货客户手机是否为空：true为空
		    boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone()));
			// 发货客户电话号码
			String phone = StringUtil.defaultIfNull(bean.getDeliveryCustomerPhone());
			// 发货客户电话是否为空：true为空
			boolean telePhone = StringUtil.isEmpty(phone);
			// 发货客户名称
			boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getDeliveryCustomerContact()));
			//客户信息列表
			List<CustomerQueryConditionDto> contactList=null;

			contactList = queryDeliveryCustomer(bean);

			return contactList;
		}
		
		 /**
		 * 
		 * （收货人手机号码监听）
		 * 
		 * @author FOSS-叶涛
		 * @date 2014-10-17 下午08:25:04
		 */
		public List<CustomerQueryConditionDto> 	receiveCustomerMobilephoneListener(ExpWaybillPanelVo bean) {
			// 发货客户手机是否为空：true为空
		    boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone()));
			// 发货客户电话号码
			String phone = StringUtil.defaultIfNull(bean.getReceiveCustomerPhone());
			// 发货客户电话是否为空：true为空
			boolean telePhone = StringUtil.isEmpty(phone);
			// 发货客户名称
			boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getReceiveCustomerContact()));
			
			List<CustomerQueryConditionDto> contactList=null;
			
			contactList = queryReceiveCustomer(bean);
			
		   return contactList;
		}
		/**
		 * 根据查询条件查询发货客户信息
		 * 
		 * @author foss-叶涛
		 * @date 2012-11-22 下午7:25:50
		 */
		public List<CustomerQueryConditionDto> queryDeliveryCustomer(ExpWaybillPanelVo bean) {
			// 查询条件
			List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
			// 手机
			String mobilePhone = StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone());
			// 电话
			String telePhone = StringUtil.defaultIfNull(bean.getDeliveryCustomerPhone());
			// 客户名称
			String custName = StringUtil.defaultIfNull(bean.getDeliveryCustomerContact());
			//crm客户信息
			List<CustomerQueryConditionDto> contactList = null;
			if(StringUtils.isNotEmpty(mobilePhone)){
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				dto.setMobilePhone(mobilePhone);
				dto.setExactQuery(true);
				dtoList.add(dto);
				// 根据条件查询客户信息
				contactList = waybillService.queryCustomerByConditionList(dtoList);
				if(contactList!=null){
				   return contactList;
				}
			}else if(contactList==null && StringUtils.isNotEmpty(custName) && StringUtils.isNotEmpty(telePhone)){
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				dto.setCustName(custName);
				dto.setContactPhone(telePhone);
				dto.setExactQuery(true);
				dtoList.add(dto);
				contactList = waybillService.queryCustomerByConditionList(dtoList);
				return contactList;
			}
			return null;
		}
		/**
		 * s
		 * 
		 * 查询收货客户
		 * 
		 * @author FOSS-叶涛
		 * @date 2012-12-10 下午05:08:02
		 */
		public List<CustomerQueryConditionDto> queryReceiveCustomer(ExpWaybillPanelVo bean) {
			// 查询条件
			List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
			// 手机
			String mobilePhone = StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone());
			// 电话
			String telePhone = StringUtil.defaultIfNull(bean.getReceiveCustomerPhone());
			// 客户名称
			String custName = StringUtil.defaultIfNull(bean.getReceiveCustomerContact());
			//crm客户相关信息
			List<CustomerQueryConditionDto> contactList = null;
			
			if(StringUtils.isNotEmpty(mobilePhone)){
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				dto.setMobilePhone(mobilePhone);
				dto.setExactQuery(true);
				dtoList.add(dto);
				// 根据条件查询客户信息
				contactList = waybillService.queryCustomerByConditionList(dtoList);
				if(contactList!=null){
					  return contactList;
				}
			}else if(contactList==null && StringUtils.isNotEmpty(custName) && StringUtils.isNotEmpty(telePhone)){
				CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
				dto.setCustName(custName);
				dto.setContactPhone(telePhone);
				dto.setExactQuery(true);
				dtoList.add(dto);
				contactList = waybillService.queryCustomerByConditionList(dtoList);
				return contactList;
			}
			return null;
		}
		
	/**
	 * 数据字典Entity转换VO
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-6 上午11:22:15
	 */
    private DataDictionaryValueVo dataDictionaryValueEntityToVo(String termsCode, String valueCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	String terms = termsCode;
	if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN.equals(terms)) {
	    terms = WaybillConstants.PICKUP_GOODS_HIGHWAYS;
	} else if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN.equals(terms)) {
	    terms = WaybillConstants.PICKUP_GOODS_AIR;
	}

	if (StringUtil.isNotEmpty(valueCode)) {
	    DataDictionaryValueEntity entity = waybillService.queryDataDictoryValueEntity(terms, valueCode);
	    if (entity == null)
		return null;
	    DataDictionaryValueVo vo = new DataDictionaryValueVo();
	    PropertyUtils.copyProperties(vo, entity);
	    return vo;
	} else {
	    return null;
	}
    }

	/**
	 * 数据字典Entity转换VO
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-6 上午11:22:15
	 */
	private ProductEntityVo getProductTypeByCode(String productCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (StringUtil.isNotEmpty(productCode)) {
			ProductEntity entity = waybillService.queryTransTypeById(productCode);
			if (entity == null){
				return null;
			}
			ProductEntityVo vo = new ProductEntityVo();
			PropertyUtils.copyProperties(vo, entity);
			return vo;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * UI与VO绑定
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:40:15
	 */
	private void bind() {
		// 按钮绑定
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 控件绑定
		binder = BindingFactory.getIntsance().moderateBind(WebOrderVo.class, this, null, true);
	}

	/**
	 * 
	 * 创建校验、加载、属性变化监听器
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:42:15
	 */
	private void createListener() {
		orderComponentListener = new ExpWebOrderComponentListener(this);
		// 画面初始化完毕加载数据
		addHierarchyListener(orderComponentListener);

		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					try {
						importOrder();
					} catch (BusinessException e1) {
						e1.printStackTrace();
						String message = "";
						String code = StringUtil.defaultIfNull(e1.getErrorCode());
						String code2 = StringUtil.defaultIfNull(e1.getMessage());
						if(code.trim().equals(code2.trim())){
							message = code;
						}else{
							message = code+"\n"+code2;
						}
						if(StringUtils.isNotEmpty(message)){
							MsgBox.showError(message);
							return ;
						}
					}
				}
			}
		});
	}

	public ExpWebOrderComponentListener getOrderComponentListener() {
		return orderComponentListener;
	}

	public IBinder<WebOrderVo> getBinder() {
		return binder;
	}

	public DefaultComboBoxModel getOrderTypeModel() {
		return orderTypeModel;
	}

	public DefaultComboBoxModel getOrderStatusModel() {
		return orderStatusModel;
	}

	/**
	 * 分页查询updater
	 * 
	 * @author shixw
	 * 
	 */
	public class WebOrderSortedTableDataUpdater implements CommonSortedTableDataUpdater<WebOrderTableModel> {

		/**
		 * 首次查询不需要
		 */
		private boolean needSearch = false;

		/**
		 * 首次查询不需要
		 */
		private boolean needCount = false;

		private CrmOrderInfoDto resultDto;

		/**
		 * 查询data
		 */
		public void updateData(int currentPage, int pageSize, int column, boolean asc, WebOrderTableModel model) {
			// String[][] values = model.getValues();
			// 根据currentPage，pageSize，column，asc对数据(values)进行排序分页处理。

			if (!needSearch) {
				needSearch = true;
				if (resultDto != null) {
					dialog.setTableData(resultDto);
				}
				return;
			}

			dialog.setTableData(resultDto);

		}

		/**
		 * 查询页数
		 */
		public int getTotalCount(WebOrderTableModel model, int currentPage, int pageSize, int column, boolean asc) {

			if (!needCount) {

				needCount = true;
				if (resultDto != null) {
					return resultDto.getTotalCount();
				}
				return 0;
			}

			WebOrderVo queryWebOrderVo = dialog.getBinder().getBean();

			CrmOrderQueryDto queryVo = new CrmOrderQueryDto();
			// 字典ID
			queryVo.setAcceptStatus(queryWebOrderVo.getOrderStatus().getId());
			// 订单编号
			queryVo.setOrderNumber(queryWebOrderVo.getOrderNo());
			// 发货客户名称
			queryVo.setShipperCust(queryWebOrderVo.getDeliveryCustomerName());
			// 发货联系人
			queryVo.setShipperLinkman(queryWebOrderVo.getDeliveryCustomerContact());
			// 发货人手机
			queryVo.setShipperMobile(queryWebOrderVo.getDeliveryCustomerMobilephone());
			// 发货人电话
			queryVo.setShipperPhone(queryWebOrderVo.getDeliveryCustomerPhone());
			// 发货部门
			if (dept != null) {
				queryVo.setSalesDept(dept.getUnifiedCode());
			} else {
				initVo();
				queryVo.setSalesDept(dept.getUnifiedCode());
			}

			queryVo.setPageNum(currentPage);
			queryVo.setPageSize(pageSize);
			queryVo.setBeginTime(dialog.getDatePickerStart().getDate());
			
			
			Date endTime = null;
			if(dialog.getDatePickerEnd().getDate()!=null){
	    		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	    		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		try {
					endTime = sdf2.parse(sdf1.format(dialog.getDatePickerEnd().getDate())+" 23:59:59");
				} catch (ParseException e1) {
					log.error("ParseException",e1);
				}
			}
			
			queryVo.setEndTime(endTime);

			// 查询数据
			CrmOrderInfoDto infoVos = null;
			try {
				infoVos = waybillService.queryCrmExpressOrder(queryVo);

				if (infoVos.getTotalCount() == 0) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.noOrder"));
				}
			} catch (CrmOrderImportException ex) {
				return 0;
			} finally {
				needCount = true;
			}

			//将订单中的code转换为中文显示在界面上
			convertName(infoVos);
			
			resultDto = infoVos;
			return infoVos.getTotalCount();
		}
		
		/**
		 * 将订单中的code转换为中文显示在界面上
		 * @param infoVos
		 */
		private void convertName(CrmOrderInfoDto infoVos) {
			List<CrmOrderInfo> crmOrderInfoList = infoVos.getCrmOrderInfoList();
			if(crmOrderInfoList==null || crmOrderInfoList.isEmpty()){
				return;
			}
			for(CrmOrderInfo crm : crmOrderInfoList){
				String orderStatus = crm.getOrderStatus();
				if(StringUtils.isNotEmpty(orderStatus)){
					DataDictionaryValueEntity e = dataDictionaryValueService
							.queryDataDictoryValueByCode(WaybillConstants.ORDER_STATUS, orderStatus);
					if(e!=null){
						crm.setOrderStatusName(e.getValueName());
					}
				}
				
				String orderType = crm.getResource();
				if(StringUtils.isNotEmpty(orderType)){
					DataDictionaryValueEntity e = dataDictionaryValueService
							.queryDataDictoryValueByCode(WaybillConstants.ORDER_TYPE, orderType);
					if(e!=null){
						crm.setResourceName(e.getValueName());
					}
				}
				
				String tranportMode = crm.getTransportMode();
				if(StringUtils.isNotEmpty(tranportMode)){
					for(CrmTransportTypeEnum tenum : CrmTransportTypeEnum.values()){
						if(tranportMode.equals(tenum.getCode())){
							crm.setTransportModeName(tenum.getName());
							crm.setTransportModeFossCode(tenum.getFossCode());
						}
					}
				}
				
				
				String orgUnifCode  = crm.getStartStation();
				if(StringUtils.isNotEmpty(orgUnifCode)){
					OrgAdministrativeInfoEntity e= 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(orgUnifCode);
					if(e!=null){
						crm.setStartStationName(e.getName());
					}
					
				}
			}
			
		}


		/**
		 * @return the needSearch
		 */
		public boolean isNeedSearch() {
			return needSearch;
		}

		/**
		 * @param needSearch
		 *            the needSearch to set
		 */
		public void setNeedSearch(boolean needSearch) {
			this.needSearch = needSearch;
		}

		/**
		 * @return the needCount
		 */
		public boolean isNeedCount() {
			return needCount;
		}

		/**
		 * @param needCount
		 *            the needCount to set
		 */
		public void setNeedCount(boolean needCount) {
			this.needCount = needCount;
		}

		/**
		 * @return the resultDto
		 */
		public CrmOrderInfoDto getResultDto() {
			return resultDto;
		}

		/**
		 * @param resultDto
		 *            the resultDto to set
		 */
		public void setResultDto(CrmOrderInfoDto resultDto) {
			this.resultDto = resultDto;
		}

	}

	class WebOrderTableModel extends CommonSortedTableModel {

		private static final int ZERO = 0;

		private static final int TWELVE = 12;

		private static final int ELEVEN = 11;

		private static final int NIVE = 9;

		private static final int EIGHT = 8;

		private static final int SEVEN = 7;

		private static final int SIX = 6;

		private static final int FOUR = 4;

		private static final int THREE = 3;

		private static final int TWO = 2;

		private static final int ONE = 1;

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		private List<CrmOrderInfo> data;

		public void setData(List<CrmOrderInfo> data) {
			this.data = data;
		}

		public List<CrmOrderInfo> getData() {
			return data;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == 0;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ONE:
				return i18n.get("foss.gui.creating.webOrderDialog.column.one");
			case TWO:
				return i18n.get("foss.gui.creating.webOrderDialog.column.two");
			case THREE:
				return i18n.get("foss.gui.creating.webOrderDialog.column.three");
			case FOUR:
				return i18n.get("foss.gui.creating.webOrderDialog.column.four");
			case FIVE:
				return i18n.get("foss.gui.creating.webOrderDialog.column.five");
			case SIX:
				return i18n.get("foss.gui.creating.webOrderDialog.column.six");
			case SEVEN:
				return i18n.get("foss.gui.creating.webOrderDialog.column.seven");
			case EIGHT:
				return i18n.get("foss.gui.creating.webOrderDialog.column.eight");
			case NIVE:
				return i18n.get("foss.gui.creating.webOrderDialog.column.nine");
			case TEN:
				return i18n.get("foss.gui.creating.webOrderDialog.column.ten");
			case ELEVEN:
				return i18n.get("foss.gui.creating.webOrderDialog.column.eleven");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return TWELVE;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.creating.webOrderDialog.column.zero");
			case ONE:
				return data.get(row).getOrderStatusName();
			case TWO:
				return data.get(row).getResourceName();
			case THREE:
				return data.get(row).getOrderNumber();
			case FOUR:
				return data.get(row).getShipperName();
			case FIVE:
				return data.get(row).getContactName();
			case SIX:
				return data.get(row).getContactMobile();
			case SEVEN:
				return data.get(row).getContactPhone();
			case EIGHT:
				return data.get(row).getContactAddress();
			case NIVE:
				return data.get(row).getGoodsName();
			case TEN:
				return data.get(row).getTransportModeName();
			case ELEVEN:
				return data.get(row).getStartStationName();
			default:
				return "";
			}
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {

		}

		@Override
		public boolean isSortedColumn(int column) {
			return true; // 所有的列都排序
		}
	}

	/**
	 * setTableData
	 * 
	 * @param webOrderTableVos
	 *            CrmOrderInfoDto
	 */
	public void setTableData(CrmOrderInfoDto webOrderTableVos) {
		tableModel.setData(webOrderTableVos.getCrmOrderInfoList());
		tableModel.fireTableDataChanged();
	}

	/**
	 * getCombOrderType
	 * 
	 * @return JComboBox
	 */
	public JComboBox getCombOrderType() {
		return combOrderType;
	}

	/**
	 * getCombOrderStatus
	 * 
	 * @return JComboBox
	 */
	public JComboBox getCombOrderStatus() {
		return combOrderStatus;
	}

	/**
	 * getWaybillEditUI
	 * 
	 * @return WaybillEditUI
	 */
	public ExpWaybillEditUI getWaybillEditUI() {
		return ui;
	}

	/**
	 * @return the datePickerStart
	 */
	public JXDatePicker getDatePickerStart() {
		return datePickerStart;
	}

	/**
	 * @return the webOrderSortedTableDataUpdater
	 */
	public WebOrderSortedTableDataUpdater getWebOrderSortedTableDataUpdater() {
		return webOrderSortedTableDataUpdater;
	}

	/**
	 * @param webOrderSortedTableDataUpdater
	 *            the webOrderSortedTableDataUpdater to set
	 */
	public void setWebOrderSortedTableDataUpdater(WebOrderSortedTableDataUpdater webOrderSortedTableDataUpdater) {
		this.webOrderSortedTableDataUpdater = webOrderSortedTableDataUpdater;
	}

	/**
	 * @return the datePickerEnd
	 */
	public JXDatePicker getDatePickerEnd() {
		return datePickerEnd;
	}

	/**
	 * @return the navigator
	 */
	public DefaultNavigator getNavigator() {
		return navigator;
	}

	/**
	 * @param navigator
	 *            the navigator to set
	 */
	public void setNavigator(DefaultNavigator navigator) {
		this.navigator = navigator;
	}

	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}
	
	
	
	public JComboBox getCombProductType() {
		return combProductType;
	}

	
	public void setCombProductType(JComboBox combProductType) {
		this.combProductType = combProductType;
	}

	
	public JTextField getTxtOrderNo() {
		return txtOrderNo;
	}

	
	public void setTxtOrderNo(JTextField txtOrderNo) {
		this.txtOrderNo = txtOrderNo;
	}
}
