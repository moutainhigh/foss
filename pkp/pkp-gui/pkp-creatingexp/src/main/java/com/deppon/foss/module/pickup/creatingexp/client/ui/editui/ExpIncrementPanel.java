/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Dimension;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpCalculateAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpDeleteOtherChargeAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryBankAccountAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryOtherChargeAction;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq = 6)
public class ExpIncrementPanel extends JPanel {

	private static final int FIVE = 5;

	private static final int TEN = 10;

	private static final int NIVE = 9;

	private static final int EIGHT = 8;
	
	private static final int SEVEN = 7;

	private static final String FIELDNAME = "fieldName";
//	private static final String _HK_PROV = "810000";

//	private static final String _HK_CITY = "810100";
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpIncrementPanel.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6683340393741097043L;

	/**
	 * 运单service
	 */
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	private WaybillPanelVo waybillPanelVo;
	/**
	 * 收款人
	 */
	@Bind("accountName")
	private JTextFieldValidate txtPayee;

	/**
	 * 保价声明价
	 */
	@Bind("insuranceAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价声明价") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtInsuranceValue;
	
	/**
	 * 代收货款
	 */
	@Bind("codAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收货款") })
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtCashOnDelivery;
	
	/**
	 * 查询代收货款退款银行账号
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpQueryBankAccountAction.class)
	private JButton btnQueryBankAccount;
	
	/**
	 * 退款类型
	 */
	@Bind("refundType")
	@FocusSeq(seq = 3)
	JComboBox combRefundType;
	
	/**
	 * 返单类型
	 */
	@Bind("returnBillType")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "返单类型") })
	@FocusSeq(seq = 4)
	JComboBox combReturnBillType;
	
	/**
	 * 装卸费
	 */
	@Bind("serviceFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "装卸费") })
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtServiceCharge;
	
	

	
	
	/**
	 * 优惠编码
	 */
	@Bind("promotionsCode")
	@FocusSeq(seq = 6)
	private JTextFieldValidate txtPromotionNumber;

	/**
	 * 开单付款方式
	 */
	@Bind("paidMethod")
	@FocusSeq(seq = 7)
	private JComboBox combPaymentMode;
	
	
	

	/**
	 * 预付费保密
	 */
	@Bind("secretPrepaid")
	@FocusSeq(seq = 8)
	JCheckBox chbSecrecy;
	/**
	 * 保费
	 */
	@Bind("insuranceFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保费") })
	@FocusSeq(seq = 9)
	private JTextFieldValidate txtInsurCharge;
	/**
	 * 计算
	 */
	@ButtonAction(icon = IconConstants.CALCULATE_PRICE, shortcutKey = "F11", type = ExpCalculateAction.class)
	@FocusSeq(seq = 10)
	private JButton btnCalculate;
	/**
	 * 银行账号
	 */
	@Bind("accountCode")
	private JTextFieldValidate txtBankAccount;



	/**
	 * 其他费用
	 */
	@Bind("otherFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "其他费用") })
	private JTextFieldValidate txtOtherCharge;

	/**
	 * 关联单号费用
	 */
	@Bind("connectionNumFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "关联单号费用") })
	private JTextFieldValidate txtConNumCharge;


	/**
	 * 滚动条面板
	 */
	JScrollPane scrollPane;



	/**
	 * 其他費用
	 */
	JXTable tblOther;
	/**
	 * 其他费用新增
	 */
	@ButtonAction(icon = IconConstants.Other_Charge_New, shortcutKey = "", type = ExpQueryOtherChargeAction.class)
	private JButton btnNew;

	/**
	 * 其他费用删除
	 */
	@ButtonAction(icon = IconConstants.DELETE, shortcutKey = "", type = ExpDeleteOtherChargeAction.class)
	private JButton btnDelete;
	/**
	 * 主界面
	 */
	private ExpWaybillEditUI ui;

	

	private JLabel label11;

	private JLabel label_1;

	private JLabel label_2;
	
	private JLabel labelActiveInfo;
	//市场营销活动
	@Bind("activeInfo")
	private JComboBox combActiveInfo;
	
	public JComboBox getCombActiveInfo() {
		return combActiveInfo;
	}

	public void setCombActiveInfo(JComboBox combActiveInfo) {
		this.combActiveInfo = combActiveInfo;
	}

	/**
	 * Create the panel.
	 */
	public ExpIncrementPanel() {
		// 初始化其他费用表格
		initTable();
		// 初始化界面
		init();
		// 初始化其他费用数据
		//初始化面板不需要加载其他增值费用计算总运费会重新覆盖去掉该初始化方法，151211yth,2016/3/1
		//initData();
	}

	/**
	 * 
	 * <p>
	 * 界面初始化
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:27:54
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.incrementPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(18dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(13dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(21dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		// 保险声明价值
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.insuranceValue.label") + "：");
		add(label1, "2, 1, left, default");

		NumberDocument InsuranceDocument = new NumberDocument(EIGHT);
		NumberDocument serviceCharge = new NumberDocument(FIVE);
		//NumberDocument InsuranceDocument2 = new NumberDocument(EIGHT);
		txtInsuranceValue = new JTextFieldValidate();
		add(txtInsuranceValue, "3, 1, 4, 1, fill, default");
		txtInsuranceValue.setColumns(TEN);
		txtInsuranceValue.setDocument(InsuranceDocument);

		// 代收货款
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label") + "：");
		add(label4, "8, 1, right, default");

		txtCashOnDelivery = new JTextFieldValidate();
		add(txtCashOnDelivery, "9, 1, 4, 1, fill, default");
//		NumberDocumentAbs cashOnDelivery = new NumberDocumentAbs(NIVE);
//		liyongfei 精确到角，设置代收货款可以输入小数
		FloatDocumentUtils cashOnDelivery = new FloatDocumentUtils(TEN, 2);
		txtCashOnDelivery.setColumns(TEN);
		txtCashOnDelivery.setDocument(cashOnDelivery);

		// 退款类型
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.refundType.label") + "：");
		add(label7, "14, 1, left, default");

		combRefundType = new JComboBox();
		add(combRefundType, "15, 1, 2, 1, fill, default");

		scrollPane = new JScrollPane(tblOther);
		add(scrollPane, "18, 1, 7, 5, fill, fill");

		// 收款人
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.payee.label") + "：");
		add(label2, "2, 3, left, default");

		txtPayee = new JTextFieldValidate();
		add(txtPayee, "3, 3, 2, 1, fill, default");
		txtPayee.setColumns(TEN);
		txtPayee.setEditable(false);

		// 查询
		btnQueryBankAccount = new JButton();
		add(btnQueryBankAccount, "5, 3, 2, 1");
		btnQueryBankAccount.setMargin(new Insets(-2, 1, -2, 1));

		// 收款账号
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.bankAccount.label") + "：");
		add(label5, "8, 3, right, default");

		txtBankAccount = new JTextFieldValidate();
		add(txtBankAccount, "9, 3, 4, 1, fill, default");
		txtBankAccount.setColumns(TEN);
		txtBankAccount.setEditable(false);

		// 返单类别
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.returnBillType.label") + "：");
		add(label8, "14, 3, left, default");

		combReturnBillType = new JComboBox();
		add(combReturnBillType, "15, 3, 2, 1, fill, default");
		

		// 装卸费
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.serviceCharge.label") + "：");
		add(label6, "2, 5, left, default");

		txtServiceCharge = new JTextFieldValidate();
		add(txtServiceCharge, "3, 5, 4, 1, fill, default");
		txtServiceCharge.setColumns(TEN);
		txtServiceCharge.setDocument(serviceCharge);
		txtServiceCharge.setEditable(true);
		txtServiceCharge.setEnabled(false);
		
		label_1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label1"));
		//如果是合伙人营业部，优惠编码出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.PRIVILEGE_CONDE_EXP){
			add(label_1, "8, 5, right, default");
		}
		txtPromotionNumber = new JTextFieldValidate();
		//如果是合伙人营业部，优惠编码出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.PRIVILEGE_CONDE_EXP){
			add(txtPromotionNumber, "9, 5, 4, 1, fill, default");
		}
		txtPromotionNumber.setColumns(10);

		label_2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label2"));
		add(label_2, "14, 5, right, default");

		combPaymentMode = new JComboBox();
		add(combPaymentMode, "15, 5, 2, 1, fill, default");

		// 预付费保密
		chbSecrecy = new JCheckBox(i18n.get("foss.gui.creating.incrementPanel.secrecy.label"));
		
		add(chbSecrecy, "2, 7, 2, 1");
		// 保费
		JLabel labelInsur = new JLabel(i18n.get("foss.gui.creating.incrementPanel.insurlabel") + "：");
		add(labelInsur, "8, 7, left, default");
		
		txtInsurCharge = new JTextFieldValidate();
		add(txtInsurCharge, "9, 7, 4, 1, fill, default");
		txtInsurCharge.setColumns(TEN);
		NumberDocument InsuranceCharge = new NumberDocument(SEVEN);
		txtInsurCharge.setDocument(InsuranceCharge);
		
		
		btnCalculate = new JButton(i18n.get("foss.gui.creating.incrementPanel.btnCalculate"));
		add(btnCalculate, "15, 7, 2, 1");

		// 其他费用合计
		label11 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.otherCharge.label") + "：");
		add(label11, "18, 7, right, default");

		txtOtherCharge = new JTextFieldValidate();
		add(txtOtherCharge, "20, 7, fill, default");
		txtOtherCharge.setColumns(TEN);
		txtOtherCharge.setEnabled(false);

		// 新增
		btnNew = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.new"));
		add(btnNew, "22, 7");

		// 删除
		btnDelete = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.delete"));
		add(btnDelete, "24, 7");
		
		//市场营销活动名称
		labelActiveInfo = new JLabel(i18n.get("foss.gui.creating.incrementpanel.activeinfo.discount.name")+"：");
		//如果是合伙人营业部，市场推广活动出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY_EXP){
			add(labelActiveInfo, "2, 9, right, default");
		}
		combActiveInfo = new JComboBox();
		//固定下拉框宽度，不随内容增加而增大
		combActiveInfo.setMaximumSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		combActiveInfo.setMinimumSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		combActiveInfo.setPreferredSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		//如果是合伙人营业部，市场推广活动出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY_EXP){
			add(combActiveInfo, "4, 9, 3, 1, fill, default");
		}
		
		//关联单号费用
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.conNumCharge.label") + "：");
		add(label9,"8,9,right,default");
		
		txtConNumCharge = new JTextFieldValidate();
		add(txtConNumCharge, "10, 9, 3, 1, fill, default");
		txtConNumCharge.setColumns(TEN);
		FloatDocumentUtils cashOnDelivery1 = new FloatDocumentUtils(TEN, 2);
		txtConNumCharge.setDocument(cashOnDelivery1);
		txtConNumCharge.setEnabled(false);
	}

	/**
	 * @return the ui
	 */
	public ExpWaybillEditUI getUi() {
		return ui;
	}

	/**
	 * @param ui
	 *            the ui to set
	 */
	public void setUi(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		// 初始化表格
		tblOther = new OtherFeeJXTable();
		// 设置表格数据模型
		tblOther.setModel(new WaybillOtherCharge());
		// 设置表格可以有滚动条
		tblOther.setAutoscrolls(true);
		// 禁止表格排序
		tblOther.setSortable(false);
		// 表格设置为不可编辑
		// 设置列可见状态
		tblOther.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tblOther.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) tblOther.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * 
	 * 初始化其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-6 下午06:40:00
	 */
	private void initData() {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryParam());

		if(list!=null && list.size()>0){
			
			List<ValueAddDto> list2 = new ArrayList<ValueAddDto>();
			
			for(int i=0;i<list.size();i++){
				ValueAddDto dto = list.get(i);
				if(
//						"SHJCF".equals(dto.getSubType())
//					||"QT".equals(dto.getSubType())
//					||"GGF".equals(dto.getSubType())
						ExpWaybillConstants.OTHERFEE_SHJCF.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_RYFJ.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_ZHXX.equals(dto.getSubType())){
					continue;
				}else{
					list2.add(dto);
				}
			}
		
			
			List<OtherChargeVo> voList = getOtherChargeList(list2);
			if (voList != null) {
				setChangeDetail(voList);
			}
		}
	}

	/**
	 * 
	 * 鑾峰彇鍏朵粬璐圭敤鏌ヨ鍙傛暟
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 涓婂崍11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(dept.getCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(null);
		// 产品CODE
		queryDto.setProductCode(null);
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setReceiveDate(null);
		// 重量
		queryDto.setWeight(BigDecimal.ZERO);
		// 体积
		queryDto.setVolume(BigDecimal.ZERO);
		// 原始费用
		queryDto.setOriginnalCost(BigDecimal.ZERO);
		// 航班号
		queryDto.setFlightShift(null);
		// 长途 还是短途
		queryDto.setLongOrShort(null);
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(null);
		// 币种
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
		// 计价条目CODE
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);
		// 计价名称
		queryDto.setPricingEntryName(null);
		return queryDto;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				OtherChargeVo vo = new OtherChargeVo();
				if (dto.getCandelete() != null && !BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete())) {
					// 费用编码
					vo.setCode(dto.getSubType());
					// 名称
					vo.setChargeName(dto.getSubTypeName());
					// 归集类别
					vo.setType(dto.getBelongToPriceEntityName());
					// 描述
					vo.setDescrition(dto.getPriceEntityCode());
					// 金额
					vo.setMoney(dto.getFee().toString());
					// 上限
					vo.setUpperLimit(dto.getMaxFee().toString());
					// 下限
					vo.setLowerLimit(dto.getMinFee().toString());
					// 是否可修改
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
    public void setChangeDetail(List<OtherChargeVo> changeDetailList2) {
    if(changeDetailList2==null){
    	changeDetailList2 = new ArrayList<OtherChargeVo>();
    }
	WaybillOtherCharge changeInfoDetailTableModel = ((WaybillOtherCharge) tblOther.getModel());
	
	List<OtherChargeVo> changeDetailList = new ArrayList<OtherChargeVo>();
	
	//TODO 这里是其他费用的添加入口
	for(int i =0 ; i <changeDetailList2.size();i++ ){
		OtherChargeVo v = changeDetailList2.get(i);
		if(ExpWaybillConstants.OTHERFEE_SHJCF.equals(v.getCode())
			||WaybillConstants.OTHERFEE_RYFJ.equals(v.getCode())
			||WaybillConstants.OTHERFEE_ZHXX.equals(v.getCode())){
			continue;
		}else{
			changeDetailList.add(v);
		}
	
	}
	
	
	
	
	
	changeInfoDetailTableModel.setData(changeDetailList);
	// 刷新表格数据
	changeInfoDetailTableModel.fireTableDataChanged();
	if (waybillPanelVo != null && WaybillConstants.YES.equals(waybillPanelVo.getFlagCalFee())) {
	    //Common.getYokeCharge(waybillPanelVo, ui);
	    // 加if判断是防止导入已开单的运单重新再加包装费，参见：BUG-10420
	    // 获取计算前的预付金额
	    BigDecimal prePayAmount = waybillPanelVo.getPrePayAmount();
	    // 获取计算前的到付金额
	    BigDecimal toPayAmount = waybillPanelVo.getToPayAmount();
	    // 计算运费
	    CalculateFeeTotalUtils.resetCalculateFee(waybillPanelVo);
	    // 重新设置预付金额
	    waybillPanelVo.setPrePayAmount(prePayAmount);
	    // 重新设置到付金额
	    waybillPanelVo.setToPayAmount(toPayAmount);
	}
    }

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class WaybillOtherCharge extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据
		 */
		private List<OtherChargeVo> changeDetailList;

		/**
		 * 获得表格数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		/**
		 * 设置表格数据
		 * 
		 * @param changeDetailList
		 */
		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		/**
		 * 设置单元格的头
		 * 
		 */
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.canvasContentPanel.otherFee.label");
			case 1:
				return i18n.get("foss.gui.creating.incrementPanel.gridValue.label");
			default:
				return "";
			}
		}

		/**
		 * 设置单元格的值
		 */
		public void setValueAt(Object aValue, int row, int column) {
			List<OtherChargeVo> data = this.getData();

			/**
			 * 表格第一列名字是不能修改的
			 */
			if (column == 0) {
				return;
			}

			OtherChargeVo vo = data.get(row);

			/**
			 * vo is null
			 */
			if (vo == null) {
				return;
			}

			// 是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			/**
			 * 修改not allowed
			 */
			if (isUpdate == null) {
				isUpdate = Boolean.FALSE;
			}
			
			
			
			double money = 0;
			try {
				money = Double.parseDouble((String) aValue);
				/**
				 * 金额<=0
				 */
				/*if (money <= 0) {
					throw new Exception(i18n.get("foss.gui.creating.incrementPanel.exception.label"));
				}*/
			} catch (Exception e) {
				/**
				 * 输入金额非法
				 */
				money = -1;
				MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label"));
				return;
			}
			/**
			 * 保留2位小数
			 */
			BigDecimal b = BigDecimal.valueOf(money);
			/**
			 * 四舍五入
			 */
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal valueCal=b.setScale(2, BigDecimal.ROUND_HALF_UP);
			String moneyStr = Double.toString(f1);
			aValue = moneyStr;
			// 非法
			if (!isUpdate) {
				return;
			} else {
				/**
				 * set up money
				 */
				try {
				String oldMoney = vo.getMoney();

				double oldvalue = 0;
				try {
					oldvalue = Double.parseDouble(oldMoney);
				} catch (Exception e) {
				}
				
				
				if(StringUtils.isNotEmpty(vo.getUpperLimit())&&StringUtils.isNotEmpty(vo.getLowerLimit()))
				{
					
					BigDecimal upperLimit=new BigDecimal(vo.getUpperLimit());
					BigDecimal lowerLimit=new BigDecimal(vo.getLowerLimit());
					if(valueCal.compareTo(upperLimit)==1)
					{
						MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.max.value"));
						return;
					}
					else if(valueCal.compareTo(lowerLimit)==-1)
					{
						MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.min.value"));
						return;
					}
					
				}
				if (waybillPanelVo != null) {
					waybillPanelVo.setOtherFee(waybillPanelVo.getOtherFee().subtract(BigDecimal.valueOf(oldvalue)).add(BigDecimal.valueOf(f1)));
					waybillPanelVo.setOtherFeeCanvas(waybillPanelVo.getOtherFee().toString());
				}
				vo.setMoney(moneyStr);
				
				} catch (Exception e) {
					/**
					 * 输入金额非法
					 */
					money = -1;
					MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label"));
					return;
				}
			}
			/**
			 * 正式提交修改的表格数据
			 */
			fireTableCellUpdated(row, column);

		}

		/**
		 * 获得列数
		 */
		public int getColumnCount() {
			return 2;
		}

		/**
		 * 获得行数
		 */
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		/**
		 * 获得数据
		 */
		public Object getValueAt(int row, int column) {
			
			try{
				switch (column) {
				case 0:
					return changeDetailList.get(row).getChargeName();
				case 1:
					return changeDetailList.get(row).getMoney();
				default:
					return super.getValueAt(row, column);
				}	
			}catch(Exception e){
				return null;
			}

		}
	}

	/**
	 * getCombReturnBillType
	 * 
	 * @return
	 */
	public JComboBox getCombReturnBillType() {
		return combReturnBillType;
	}

	/**
	 * getCombRefundType
	 * 
	 * @return
	 */
	public JComboBox getCombRefundType() {
		return combRefundType;
	}

	/**
	 * getTxtInsuranceValue
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtInsuranceValue() {
		return txtInsuranceValue;
	}



	/**
	 * getTblOther
	 * 
	 * @return
	 */
	public JXTable getTblOther() {
		return tblOther;
	}

	/**
	 * getTxtCashOnDelivery
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtCashOnDelivery() {
		return txtCashOnDelivery;
	}

	/**
	 * 
	 * 优惠编码
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午08:44:08
	 * @return
	 */
	public JTextFieldValidate getTxtPromotionNumber() {
		return txtPromotionNumber;
	}
	
	/**
	 * getChbSecrecy
	 * 
	 * @return
	 */
	public JCheckBox getChbSecrecy() {
		return chbSecrecy;
	}



	/**
	 * getTxtPayee
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPayee() {
		return txtPayee;
	}



	/**
	 * combPaymentMode getCombPaymentMode
	 * @return
	 */
	public JComboBox getCombPaymentMode() {
		return combPaymentMode;
	}
	
	/**
	 * getTxtBankAccount
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtBankAccount() {
		return txtBankAccount;
	}

	/**
	 * getTxtServiceCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtServiceCharge() {
		return txtServiceCharge;
	}

	/**
	 * getTxtOtherCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtOtherCharge() {
		return txtOtherCharge;
	}

	/**
	 * getBtnNew
	 * 
	 * @return
	 */
	public JButton getBtnNew() {
		return btnNew;
	}

	/**
	 * getBtnDelete
	 * 
	 * @return
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}

	/**
	 * 其他费用table
	 * 
	 * @author shixiaowei
	 * 
	 */
	public class OtherFeeJXTable extends JXTable {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 该单元格是否可以编辑
		 */
		public boolean isCellEditable(int row, int column) {

			WaybillOtherCharge model = (WaybillOtherCharge) this.getModel();
			List<OtherChargeVo> data = model.getData();

			/**
			 * 表格第一列名字是不能修改的
			 */
			if (column == 0) {
				return false;
			}

			OtherChargeVo vo = data.get(row);

			// 数据不存在 也不允许修改
			if (vo == null) {
				return false;
			}

			// 是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			/**
			 * 默认情况下 不允许修改金额
			 */
			if (isUpdate == null) {
				isUpdate = Boolean.FALSE;
			}

			return isUpdate;
		}

	}

	/**
	 * @return the waybillPanelVo
	 */
	public WaybillPanelVo getWaybillPanelVo() {
		return waybillPanelVo;
	}

	/**
	 * @param waybillPanelVo
	 *            the waybillPanelVo to set
	 */
	public void setWaybillPanelVo(WaybillPanelVo waybillPanelVo) {
		this.waybillPanelVo = waybillPanelVo;
	}

	public JTextFieldValidate getTxtInsurCharge() {
		return txtInsurCharge;
	}

	public JTextFieldValidate getTxtConNumCharge() {
		return txtConNumCharge;
	}


}
