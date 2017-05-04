package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.NegativeNumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.PositiveNumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.creating.client.action.CalculateVehiclePriceAction;
import com.deppon.foss.module.pickup.creating.client.vo.InvoiceMarkVo;
import com.deppon.foss.module.pickup.creating.client.vo.VehiclePriceVo;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * @author DP-FOSS-YANGKANG 整车报价界面类，用于整车报价功能
 */
public class CalculateVehicleDialog extends JFrame {

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(CalculateVehicleDialog.class);

	/**
	 * 绑定接口
	 */
	private IBinder<VehiclePriceVo> vehiclePriceBinder;

	/**
	 * 绑定MAP
	 */
	private Map<String, IBinder<VehiclePriceVo>> bindersMap = new HashMap<String, IBinder<VehiclePriceVo>>();

	/**
	 * 限制输入数值的位数
	 */
	private static final int EIGHT = 8;
	
	private static final int FIVE = 5;
	
	private static final int FOUR = 4;

	private static final int TEN = 10;
	
	private static final int NUM_525 = 525;

	private static final int NUM_350 = 350;
	
	/**
	 * vo
	 */
	private VehiclePriceVo vo;
	// 序列化
	private static final long serialVersionUID = 1L;


	private final JPanel contentPanel = new JPanel();
	// 运输性质
	private DefaultComboBoxModel productTypeModel;
	// 运输性质
	@Bind("vehicleProductCode")
	@FocusSeq(seq = 1)
	private JComboBox combProductCodeType;
	// 发票标记
	private DefaultComboBoxModel invoiceMarkModel;
	// 发票标记
	@Bind("vehicleInvoiceMarkType")
	@FocusSeq(seq = 2)
	private JComboBox combInvoiceMarkType;
	// 包装费
	@Bind("vehiclePackageFee")
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtPackageFee;
	// 货物重量
	@Bind("vehicleProductWeight")
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtProductWeight;
	// 开单报价
	@Bind("vehicleBillFee")
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtBillFee;
	// 约车报价
	@Bind("vehicleReserveCarFee")
	@FocusSeq(seq = 6)
	private JTextFieldValidate txtReserveCarFee;
	// 总运费
	@Bind("vehicleTotalFee")
	@FocusSeq(seq = 7)
	private JTextFieldValidate txtTotalFee;
	// 整车运费冲减
	@Bind("vehicleChangeFee")
	@FocusSeq(seq = 9)
	private JTextFieldValidate txtVehicleChangeFee;
	//装卸费
	@Bind("handlingFee")
	@FocusSeq(seq = 8)
	private JTextField txtHandlingFee;
	
	@ButtonAction(icon = IconConstants.CALCULATE_PRICE, shortcutKey = "", type = CalculateVehiclePriceAction.class)
	@FocusSeq(seq = 10)
	private JButton validationTotalFeeButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CalculateVehicleDialog dialog = new CalculateVehicleDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog. 界面初始化
	 */
	public CalculateVehicleDialog() {
		init();
		// 设置模态
		//setModal(true);
		// 初始化下拉框
		initComboBox();
		// 绑定Action
		bind();
		// 保存绑定对象
		registToRespository();
		// 设置默认值
		initComboBoxDefault();
		// 添加监听事件
		initAddListener();
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.init
	 * @Description:初始化界面
	 * 
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-13 下午4:35:18
	 * 
	 *                  Modification History: Date Author Version Description
	 *                  ----
	 *                  ------------------------------------------------------
	 *                  ------- 2014-11-13 DP-FOSS-YANGKANG v1.0.0 create
	 */
	public void init() {
		setTitle(i18n
				.get("foss.gui.creating.calculateVehicleDialog.lblVehiclePrice"));
		setBounds(NumberConstants.NUMBER_150, NumberConstants.NUMBER_150, NUM_525, NUM_350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(77dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(5dlu;default)"),
				ColumnSpec.decode("right:4dlu:grow"),
				ColumnSpec.decode("max(81dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(69dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(66dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblProductCode = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblProductCode"));
			contentPanel.add(lblProductCode, "2, 2, right, default");
		}
		{
			combProductCodeType = new JComboBox();
			combProductCodeType.setEditable(false);
			contentPanel.add(combProductCodeType, "4, 2, fill, default");
		}
		{
			JLabel lblInvoiceMark = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblInvoiceMark"));
			contentPanel.add(lblInvoiceMark, "8, 2, right, default");
		}
		{
			combInvoiceMarkType = new JComboBox();
			combInvoiceMarkType.setEditable(true);
			contentPanel.add(combInvoiceMarkType, "10, 2");
		}
		{
			JLabel lblProductWeight = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblProductWeight"));
			contentPanel.add(lblProductWeight, "2, 6, right, default");
		}
		{
			txtProductWeight = new JTextFieldValidate();
			// 货物重量只可以输入数值 且最多输入两位小数
			FloatDocumentUtils weight = new FloatDocumentUtils(EIGHT, 2);
			txtProductWeight.setDocument(weight);
			contentPanel.add(txtProductWeight, "4, 6, fill, default");
			txtProductWeight.setColumns(TEN);
		}
		{
			JLabel lblPackageFee = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblPackageFee"));
			contentPanel.add(lblPackageFee, "8, 6, right, default");
		}
		{
			txtPackageFee = new JTextFieldValidate();
			// 包装费只可以输入数值 且最多输入两位小数
			FloatDocumentUtils packageFee = new FloatDocumentUtils(EIGHT, 2);
			txtPackageFee.setDocument(packageFee);
			contentPanel.add(txtPackageFee, "10, 6");
			txtPackageFee.setColumns(TEN);
		}
		{
			JLabel lblReserveCarFee = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblReserveCarFee"));
			contentPanel.add(lblReserveCarFee, "2, 10, right, default");
		}
		{
			txtReserveCarFee = new JTextFieldValidate();
			// 约车报价只能输入数字字符，且必须为正数
			txtReserveCarFee.setDocument(new PositiveNumberDocument(EIGHT));
			contentPanel.add(txtReserveCarFee, "4, 10, fill, default");
			txtReserveCarFee.setColumns(TEN);
		}
		{
			JLabel lblBillFee = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblBillFee"));
			contentPanel.add(lblBillFee, "8, 10, right, default");
		}
		{	
			//开单报价
			txtBillFee =  new JTextFieldValidate();
			txtBillFee.setDocument(new PositiveNumberDocument(EIGHT));
			contentPanel.add(txtBillFee, "10, 10");
			txtBillFee.setColumns(TEN);
		}
		{
			JLabel lblVehicleChangeFee = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblVehicleChangeFee"));
			contentPanel.add(lblVehicleChangeFee, "2, 14, right, default");
		}
		{   
			//整车运费冲减
			txtVehicleChangeFee = new JTextFieldValidate();
			txtVehicleChangeFee.setDocument(new NegativeNumberDocument(EIGHT, 2));
			contentPanel.add(txtVehicleChangeFee, "4, 14, fill, default");
			txtVehicleChangeFee.setColumns(TEN);
		}
		{
			JLabel lblTotalFee = new JLabel(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblTotalFee"));
			contentPanel.add(lblTotalFee, "8, 14, right, default");
		}
		{
			txtTotalFee = new JTextFieldValidate();
			// 总运费只能输入数字字符，且必须为正数
			txtTotalFee.setDocument(new PositiveNumberDocument(EIGHT));
			contentPanel.add(txtTotalFee, "10, 14, fill, default");
			txtTotalFee.setColumns(TEN);
		}
		{
			JLabel lblHandlingFee = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.serviceFee.label"));
			contentPanel.add(lblHandlingFee, "2, 18, right, default");
		}
		{
			txtHandlingFee = new JTextFieldValidate();
			txtHandlingFee.setDocument(new FloatDocumentUtils(EIGHT, 2));
			contentPanel.add(txtHandlingFee, "4, 18, fill, default");
			txtHandlingFee.setColumns(TEN);
		}
		{
			validationTotalFeeButton = new JButton(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblValidationTotalFee"));
			contentPanel.add(validationTotalFeeButton, "8, 20");
			validationTotalFeeButton.setActionCommand("OK");
		}
		{
			JButton cancelButton = new JButton(
					i18n.get("foss.gui.creating.calculateVehicleDialog.lblCancelButton"));
			// 当点击关闭按钮时调用
			cancelButton.addActionListener(new MyActionListener(this));
			cancelButton.setActionCommand("Cancel");
			contentPanel.add(cancelButton, "10, 20");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}

	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.initComboBox
	 * @Description:初始化下拉框
	 * 
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午2:50:30
	 * 
	 *                  Modification History: Date Author Version Description
	 *                  ----
	 *                  ------------------------------------------------------
	 *                  ------- 2014-11-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	public void initComboBox() {
		initCombProductCodesType();
		initCombInvoiceMarkType();
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.initCombProductCodesType
	 * @Description:初始化运输性质下拉框
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午3:08:50
	 */
	public void initCombProductCodesType() {
		productTypeModel = new DefaultComboBoxModel();
		// 设置运输性质默认为整车
		ProductEntityVo vo = new ProductEntityVo();
		vo.setCode("WVH");
		vo.setName("整车(三级)");
		productTypeModel.addElement(vo);
		this.getCombProductCodeType().setModel(productTypeModel);
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.initCombInvoiceMarkType
	 * @Description:初始化发票标记下拉框
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午3:08:50
	 */
	public void initCombInvoiceMarkType() {

		invoiceMarkModel = new DefaultComboBoxModel();
		InvoiceMarkVo invoiceMarkOne = new InvoiceMarkVo();
		invoiceMarkOne
				.setCode(i18n
						.get("foss.gui.creating.calculateVehicleDialog.firstInvoiceMarkCode"));
		invoiceMarkOne
				.setName(i18n
						.get("foss.gui.creating.calculateVehicleDialog.firstInvoiceMarkName"));
		InvoiceMarkVo invoiceMarkTwo = new InvoiceMarkVo();
		invoiceMarkTwo
				.setCode(i18n
						.get("foss.gui.creating.calculateVehicleDialog.secondInvoiceMarkCode"));
		invoiceMarkTwo
				.setName(i18n
						.get("foss.gui.creating.calculateVehicleDialog.secondInvoiceMarkName"));

		invoiceMarkModel.addElement(invoiceMarkOne);
		invoiceMarkModel.addElement(invoiceMarkTwo);
		this.combInvoiceMarkType.setModel(invoiceMarkModel);

	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.bind
	 * @Description:数据绑定
	 * 
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午6:17:09
	 * 
	 *                  Modification History: Date Author Version Description
	 *                  ----
	 *                  ------------------------------------------------------
	 *                  ------- 2014-11-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	public void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		vehiclePriceBinder = BindingFactory.getIntsance().moderateBind(
				VehiclePriceVo.class, this, true);

		vo = vehiclePriceBinder.getBean();
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if (user == null || user.getEmployee() == null) {
			return;
		}
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if (dept != null) {
			vo.setLoginDeptCode(dept.getCode());
			vo.setLoginDeptName(dept.getName());
		}
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.registToRespository
	 * @Description:设置下拉框的默认值
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午3:08:50
	 */
	public void initComboBoxDefault() {
		// 获取bean
		VehiclePriceVo bean = vehiclePriceBinder.getBean();
		bean.setVehicleProductCode((ProductEntityVo) productTypeModel.getElementAt(0));
		//非集中开单默认设置整车报价面板中的整车运费冲减的值为0
		bean.setVehicleChangeFee(BigDecimal.ZERO);
		//非集中开单默认设置整车报价面板中的装卸费值为0
		bean.setHandlingFee(BigDecimal.ZERO);
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.initAddListener
	 * @Description:给控件添加监听事件
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午5:37:32
	 */
	public void initAddListener() {

		/*// 给发票标记下拉框添加监听器
		this.combInvoiceMarkType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				InvoiceMarkVo selectedVo = (InvoiceMarkVo) combInvoiceMarkType
						.getSelectedItem();
				// 弹出框提示是否选择
				if (selectedVo != null
						&& "INVOICE_TYPE_01".equals(selectedVo.getCode())) {
					if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(
							contentPanel,
							i18n.get("foss.gui.creating.calculateVehicleDialog.isFirstinvoiceMark"),
							i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"),
							JOptionPane.YES_NO_OPTION)) {
						combInvoiceMarkType.setSelectedItem(null);
					}
				}
				// 弹出框提示是否选择
				if (selectedVo != null
						&& "INVOICE_TYPE_02".equals(selectedVo.getCode())) {
					if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(
							contentPanel,
							i18n.get("foss.gui.creating.calculateVehicleDialog.isSecondinvoiceMarkOrNull"),
							i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"),
							JOptionPane.YES_NO_OPTION)) {
						combInvoiceMarkType.setSelectedItem(null);
					}
				}

			}
		});*/

		/**
		 * 开单报价监听事件 
		 * 1.输入开单报价之前，发票标记和约车报价不能为空。 
		 * 2.开单报价-约车报价/约车报价=车价利润率
		 * 3.车价利润率必须在[整车价格波动参数向下浮动值，整车价格波动参数向上浮动值]之间
		 */
		//添加键盘监听事件
		this.txtBillFee.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// 获取bean
				VehiclePriceVo bean = vehiclePriceBinder.getBean();

				if (bean.getVehicleInvoiceMarkType() == null
						|| bean.getVehicleReserveCarFee() == null) {
					// 填写开单报价之前 发票标记和约车报价不能为空
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.calculateVehicleDialog.isNotNullVehicleBillFee"));
					txtBillFee.setText(null);
					return;
				}
				
			}
		});
		this.txtBillFee.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// 获取bean
				VehiclePriceVo bean = vehiclePriceBinder.getBean();
				
				// 当约车报价不为0且开单报价不为空时，才校验，否则不进行校验
				if (bean.getVehicleReserveCarFee()!=null
						&&bean.getVehicleReserveCarFee().compareTo(BigDecimal.ZERO) > 0
						&&bean.getVehicleBillFee()!=null) {
					
					CarloadPricePlanDto entity = new CarloadPricePlanDto();
					List<CarloadPricePlanDto> list = new ArrayList<CarloadPricePlanDto>();
					
					//通过当前登录部门编码 查询本级部门以及所有上级部门信息
					List<OrgAdministrativeInfoEntity>  orgEntitys = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityAllUpNOCache(bean.getLoginDeptCode());
					
					if(CollectionUtils.isNotEmpty(orgEntitys)){
						//查询整车价格方案，从当前部门开始，以此往上类推，查询到价格方案则跳出循环，否则继续执行循环
						for(int i=0; i<orgEntitys.size();i++){
							//通过组织  发票标记查询对应的整车价格波动参数方案
							entity.setActive(FossConstants.YES);
							entity.setOrganizationCode(orgEntitys.get(i).getCode());
							entity.setInvoiceType(bean.getVehicleInvoiceMarkType().getCode());
							list = BaseDataServiceFactory.getBaseDataService().selectCarloadPricePlanDetailByOrganizationCode(entity);
							if(CollectionUtils.isNotEmpty(list)){
								break;
							}
						}
					}
	
					if (CollectionUtils.isNotEmpty(list)) {
						entity = list.get(0);
						// 开单报价-约车报价
						BigDecimal temp = (bean.getVehicleBillFee()).subtract(bean.getVehicleReserveCarFee());
						/**
						 * @author 200945
						 * DEFECT-5834:整车报价界面报错
						 * 1.错误信息：Non-terminating decimal expansion; no exact representable decimal result.
						 * 原因：例如：10/3= 3.3333333....
						 */
						//简单报价 整车开单报价接近临界值 不提示范围
						if (temp.divide(bean.getVehicleReserveCarFee(),2,BigDecimal.ROUND_DOWN).compareTo(entity.getFloatDown()) < 0
								|| temp.divide(bean.getVehicleReserveCarFee(),FOUR,BigDecimal.ROUND_DOWN).compareTo(entity.getFloatUp()) > 0) {
							// 开单报价的最小值
							BigDecimal minBillFee = (entity.getFloatDown()).multiply(bean.getVehicleReserveCarFee()).add(bean.getVehicleReserveCarFee());
							// 开单报价的最大值
							BigDecimal maxBillFee = (entity.getFloatUp()).multiply(bean.getVehicleReserveCarFee()).add(bean.getVehicleReserveCarFee());
							// 开单报价必须符合价格范围minBillFee—maxBillFee之间
							MsgBox.showInfo("开单报价必须在价格范围" + minBillFee
									+ "-" + maxBillFee + "之间");
							txtBillFee.setText(null);
							return;

						}
					} else {
						// 没有查询到有效的整车价格波动参数方案
						MsgBox.showInfo(i18n
								.get("foss.gui.creating.calculateVehicleDialog.isNoPricePlan"));
						txtBillFee.setText(null);
						return;

					}
				}
				
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.creating.client.ui.popupdialog.
	 *            CalculateVehicleDialog.registToRespository
	 * @Description:初始化发票标记下拉框
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-14 下午3:08:50
	 */
	private void registToRespository() {
		bindersMap.put("vehiclePriceBinder", vehiclePriceBinder);
	}

	/**
	 * 
	 * @author DP-FOSS-YANGKANG 关闭按钮的监听事件类
	 */
	class MyActionListener implements ActionListener {
		CalculateVehicleDialog dialog;

		public MyActionListener(CalculateVehicleDialog dialog) {
			this.dialog = dialog;
		}

		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
	}

	public IBinder<VehiclePriceVo> getVehiclePriceBinder() {
		return vehiclePriceBinder;
	}

	public void setVehiclePriceBinder(IBinder<VehiclePriceVo> vehiclePriceBinder) {
		this.vehiclePriceBinder = vehiclePriceBinder;
	}

	public Map<String, IBinder<VehiclePriceVo>> getBindersMap() {
		return bindersMap;
	}

	public void setBindersMap(Map<String, IBinder<VehiclePriceVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}

	public VehiclePriceVo getVo() {
		return vo;
	}

	public void setVo(VehiclePriceVo vo) {
		this.vo = vo;
	}

	public JComboBox getCombInvoiceMarkType() {
		return combInvoiceMarkType;
	}

	public void setCombInvoiceMarkType(
			JComboBox combInvoiceMarkType) {
		this.combInvoiceMarkType = combInvoiceMarkType;
	}

	public JTextFieldValidate getTxtPackageFee() {
		return txtPackageFee;
	}

	public void setTxtPackageFee(JTextFieldValidate txtPackageFee) {
		this.txtPackageFee = txtPackageFee;
	}

	public JTextFieldValidate getTxtProductWeight() {
		return txtProductWeight;
	}

	public void setTxtProductWeight(JTextFieldValidate txtProductWeight) {
		this.txtProductWeight = txtProductWeight;
	}

	public JTextFieldValidate getTxtBillFee() {
		return txtBillFee;
	}

	public void setTxtBillFee(JTextFieldValidate txtBillFee) {
		this.txtBillFee = txtBillFee;
	}

	public JTextFieldValidate getTxtReserveCarFee() {
		return txtReserveCarFee;
	}

	public void setTxtReserveCarFee(JTextFieldValidate txtReserveCarFee) {
		this.txtReserveCarFee = txtReserveCarFee;
	}

	public JTextFieldValidate getTxtTotalFee() {
		return txtTotalFee;
	}

	public void setTxtTotalFee(JTextFieldValidate txtTotalFee) {
		this.txtTotalFee = txtTotalFee;
	}

	public JTextFieldValidate getTxtVehicleChangeFee() {
		return txtVehicleChangeFee;
	}

	public void setTxtVehicleChangeFee(JTextFieldValidate txtVehicleChangeFee) {
		this.txtVehicleChangeFee = txtVehicleChangeFee;
	}

	public JButton getValidationTotalFeeButton() {
		return validationTotalFeeButton;
	}

	public void setValidationTotalFeeButton(JButton validationTotalFeeButton) {
		this.validationTotalFeeButton = validationTotalFeeButton;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}

	public void setProductTypeModel(
			DefaultComboBoxModel productTypeModel) {
		this.productTypeModel = productTypeModel;
	}

	public JComboBox getCombProductCodeType() {
		return combProductCodeType;
	}

	public void setCombProductCodeType(
			JComboBox combProductCodeType) {
		this.combProductCodeType = combProductCodeType;
	}

	public DefaultComboBoxModel getInvoiceMarkModel() {
		return invoiceMarkModel;
	}

	public void setInvoiceMarkModel(
			DefaultComboBoxModel invoiceMarkModel) {
		this.invoiceMarkModel = invoiceMarkModel;
	}

}
