package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.deppon.foss.module.pickup.creating.client.action.QueryPackingAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryWoodAction;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * (货物信息面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
@ContainerSeq(seq = 5)
public class CargoInfoPanel extends JPanel {

	private static final int EIGHT = 8;

	private static final int FOUR = 4;

	private static final int LEN66 = 66;

	private static final int TEN = 10;

	private static final long serialVersionUID = 1L;


	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(CargoInfoPanel.class);

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 货物名称
	 */
	@Bind("goodsName")
	@NotNull()
	@BindingArgs({ @BindingArg(name = "fieldName", value = "货物名称") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtGoodsName;

	/**
	 * 货物重量
	 */
	@Bind("goodsWeightTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = "fieldName", value = "货物重量") })
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtWeight;

	/**
	 * 总件数
	 */
	@Bind("goodsQtyTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = "fieldName", value = "总件数") })
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtTotalPiece;

	/**
	 * 货物尺寸
	 */
	@Bind("goodsSize")
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtSize;
	
	/**
	 * 体积
	 */
	@Bind("goodsVolumeTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = "fieldName", value = "体积") })
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtVolume;

	/**
	 * 货物类型 A类
	 */
	/*@Bind("goodsType#A")
	@FocusSeq(seq = 6)
	private JRadioButton rbnA;*/

	/**
	 * 货物类型 B类
	 */
	/*@Bind("goodsType#B")
	@FocusSeq(seq = 7)
	private JRadioButton rbnB;*/
	
	//货物类型
	@FocusSeq(seq = 7)
	private JLabel label15;

	/**
	 * 纸
	 */
	@Bind("paper")
	@FocusSeq(seq = 8)
	private JTextFieldValidate txtPaper;

	/**
	 * 木
	 */
	@Bind("wood")
	@FocusSeq(seq = 9)
	private JTextFieldValidate txtWood;

	/**
	 * 纤
	 */
	@Bind("fibre")
	@FocusSeq(seq = 10)
	private JTextFieldValidate txtFibre;

	/**
	 * 托
	 */
	@Bind("salver")
	@FocusSeq(seq = 11)
	private JTextFieldValidate txtSalver;

	/**
	 * 膜
	 */
	@Bind("membrane")
	@FocusSeq(seq = 12)
	private JTextFieldValidate txtMembrane;

	/**
	 * 其他
	 */
	@Bind("otherPackage")
	@FocusSeq(seq = 13)
	private JTextFieldValidate txtOther;

	/**
	 * 对外备注
	 */
//	@FocusSeq(seq = 14)
	private JComboCheckBox combOutSideRemark;// JComboBox

	/**
	 * 对内备注
	 */
	@Bind("innerNotes")
	@FocusSeq(seq = 14)
	private JTextFieldValidate txtInsideRemark;

	/**
	 * 贵重物品
	 */
	@Bind("preciousGoods")
	private JCheckBox chbValuable;

	/**
	 * 大车直送
	 */
	@Bind("carDirectDelivery")
	private JCheckBox chbCarThrough;
	private JLabel label14;
	
	/**
	 * 大票货
	 */
	@Bind("bigTicket")
	private JCheckBox chbBigTicket;
	//private JLabel label15;

	/**
	 * 储运事项
	 */
	@Bind("transportationRemark")
	private JTextFieldValidate txtTransportationRemark;

	private JPanel panel;

	/**
	 * 空运货物类型
	 */
	@Bind("airGoodsType")
	private JComboBox combGoodsType;
	
	/**
	 * 原为打木架--已更改为打包装/lianhe/2016年12月26日09:24:54
	 * 打包装
	 */
	@ButtonAction(icon = IconConstants.WOOD, shortcutKey = "", type = QueryWoodAction.class)
	private JButton btnWood;
	
	/**
	 * 打包装--已作废/lianhe/2016年12月26日09:23:52
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-16下午16:21
	 */
	@ButtonAction(icon = IconConstants.WOOD, shortcutKey = "", type = QueryPackingAction.class)
	private JButton btnPacking;

	/**
	 * Create the panel.
	 */
	public CargoInfoPanel() {

		init();

		//addRadioToGroup();
	}
	
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	/**
	 * 行业货源品类  
	 */
	@Bind("industrySourceCategory")
	private JComboBox txtIndustrySourceCategory;
	private JLabel lblIndustrySourceCategory;
	public JComboBox getTxtIndustrySourceCategory() {
		return txtIndustrySourceCategory;
	}
	
	public void setTxtIndustrySourceCategory(JComboBox txtIndustrySourceCategory) {
		this.txtIndustrySourceCategory = txtIndustrySourceCategory;
	}
	
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */


	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:26:35
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.cargoInfoPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"),

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode

		("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode

		("left:default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode

		("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(19dlu;default):grow"),

		FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(26dlu;default)"),

		FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,

		FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] { FormFactory.DEFAULT_ROWSPEC,

		FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,

		FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,

		FormFactory.DEFAULT_ROWSPEC, }));

		// 货物名称
		JLabel label1 = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.goodsName.label") + "：");
		add(label1, "2, 1");

		txtGoodsName = new JTextFieldValidate();
		add(txtGoodsName, "3, 1, 6, 1, fill, default");
		txtGoodsName.setColumns(TEN);
		LengthDocument goodsNameDocument = new LengthDocument(LEN66);
		txtGoodsName.setDocument(goodsNameDocument);
		
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
		lblIndustrySourceCategory = new JLabel(i18n.get("foss.querying.categoryOfGoods")+":");
		add(lblIndustrySourceCategory, "10, 1, 4, 1, right, default");
		txtIndustrySourceCategory = new JComboBox();
		add(txtIndustrySourceCategory, "14, 1, 5, 1, fill, default");
		txtIndustrySourceCategory.setEnabled(false);
		txtIndustrySourceCategory.setEditable(false);
		
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */

		// 总件数
		JLabel lblNewLabel = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.totalPiece.label") + "：");
		add(lblNewLabel, "19, 1, 3, 1, right, default");

		txtTotalPiece = new JTextFieldValidate();
		add(txtTotalPiece, "22, 1, 4, 1, fill, default");
		txtTotalPiece.setColumns(TEN);
		txtTotalPiece.setText("0");
		//zxy 20130909 修改：不允许输入负数  start
		PackageNumberDocument pieceDocument = new PackageNumberDocument(FOUR);
		txtTotalPiece.setDocument(pieceDocument);
		//zxy 20130909 修改 end
		
		// 货物重量
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.weight.label") + ":");
		add(label2, "27, 1, 4, 1, right, default");

		txtWeight = new JTextFieldValidate();
		add(txtWeight, "31, 1, 5, 1, fill, default");
		txtWeight.setColumns(TEN);
		FloatDocument weight = new FloatDocument(EIGHT,1);
		txtWeight.setDocument(weight);

		// 货物尺寸
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.size.label") + ":");
		add(label5, "36, 1, 3, 1, left, default");

		txtSize = new JTextFieldValidate();
		add(txtSize, "39, 1, 14, 1, fill, default");
		txtSize.setColumns(TEN);

		// 大车直送
		chbCarThrough = new JCheckBox(i18n.get

		("foss.gui.creating.waybillEditUI.carThrough.name"));
		add(chbCarThrough, "54, 1");
		chbCarThrough.setEnabled(false);

		// 货物包装
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.package.label")

		+ "：");
		add(label3, "2, 3, right, default");

		PackageNumberDocument paper = new PackageNumberDocument(FOUR);
		PackageNumberDocument wood = new PackageNumberDocument(FOUR);
		PackageNumberDocument fibre = new PackageNumberDocument(FOUR);
		PackageNumberDocument salver = new PackageNumberDocument(FOUR);
		PackageNumberDocument membrane = new PackageNumberDocument(FOUR);

		txtPaper = new JTextFieldValidate();
		//控件内容右对齐
		txtPaper.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		add(txtPaper, "3, 3, 3, 1, fill, default");
		txtPaper.setColumns(TEN);
		txtPaper.setDocument(paper);

		// 纸
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.paper.label"));
		add(label8, "6, 3, right, default");

		txtWood = new JTextFieldValidate();
		//控件内容右对齐
		txtWood.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		add(txtWood, "7, 3, 3, 1, fill, default");
		txtWood.setColumns(TEN);
		txtWood.setDocument(wood);

		// 木
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.wood.label"));
		add(label9, "10, 3, right, default");

		txtFibre = new JTextFieldValidate();
		//控件内容右对齐
		txtFibre.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		add(txtFibre, "11, 3, 3, 1, fill, default");
		txtFibre.setColumns(TEN);
		txtFibre.setDocument(fibre);

		// 纤
		JLabel label10 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.fiber.label"));
		add(label10, "14, 3, right, default");

		txtSalver = new JTextFieldValidate();
		//控件内容右对齐
		txtSalver.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		add(txtSalver, "15, 3, 3, 1, fill, default");
		txtSalver.setColumns(TEN);
		txtSalver.setDocument(salver);

		// 托
		JLabel label11 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.salver.label"));
		add(label11, "18, 3, right, default");

		txtMembrane = new JTextFieldValidate();
		//控件内容右对齐
		txtMembrane.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		add(txtMembrane, "19, 3, 3, 1, fill, default");
		txtMembrane.setColumns(TEN);
		txtMembrane.setDocument(membrane);

		// 膜
		JLabel label12 = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.membrane.label"));
		add(label12, "22, 3, right, default");

//		String string = i18n.get("foss.gui.creating.cargoInfoPanel.wood.button");
//		btnWood = new JButton(i18n.get("foss.gui.creating.cargoInfoPanel.wood.button"));
		btnWood = new JButton("打包装");
		add(btnWood, "24, 3");
		btnWood.setMargin(new Insets(-1, 1, -1, 1));
		btnWood.setEnabled(false);
		
		/**
		 * 增加打包装按钮
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-30下午14:10
		 */
		btnPacking = new JButton("打包装");
//		add(btnPacking, "26, 3");
		btnPacking.setMargin(new Insets(-1, 1, -1, 1));
		btnPacking.setEnabled(false);

		// 其他
		JLabel label13 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.other.label"));
		add(label13, "28, 3, right, default");

		txtOther = new JTextFieldValidate();
		add(txtOther, "30, 3, 1, 1, fill, default");
		LengthDocument docOther = new LengthDocument(NumberConstants.NUMBER_50);
		txtOther.setColumns(TEN);
		txtOther.setDocument(docOther);

		// 总体积
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.volume.label")

		+ ":");
		add(label7, "34, 3, left, default");

		txtVolume = new JTextFieldValidate();
		add(txtVolume, "36, 3, 9, 1, fill, default");
		txtVolume.setColumns(TEN);
		txtVolume.setText("0");
		FloatDocument volume = new FloatDocument(EIGHT,2);
		txtVolume.setDocument(volume);

		// 货物类型
	    label15 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.type.label") +"：");
		add(label15, "48, 3");
		label15.setVisible(false);

		panel = new JPanel();
		add(panel, "49, 3, 4, 1, fill, fill");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));

		/*rbnA = new JRadioButton("A");
		panel.add(rbnA);
		rbnA.setEnabled(false);

		rbnB = new JRadioButton("B");
		panel.add(rbnB);
		rbnB.setEnabled(false);*/

		combGoodsType = new JComboBox();
		panel.add(combGoodsType);
		combGoodsType.setVisible(false);

		// 贵重物品
		chbValuable = new JCheckBox(i18n.get

		("foss.gui.creating.cargoInfoPanel.valuable.label"));
		// ===========取消贵重物品选项/lianhe/2016年12月25日/start=======
//		add(chbValuable, "54, 3");
		// ===========取消贵重物品选项/lianhe/2016年12月25日/end=======
		// 大票货
		chbBigTicket = new JCheckBox(i18n.get("foss.gui.creating.cargoInfoPanel.bigTicket.label"));
		add(chbBigTicket ,"54, 5");
		chbBigTicket.setEnabled(false);
		// 对外备注
		JLabel label4 = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.outSideRemark.label") + "：");
		// ===========取消对外备注选项/lianhe/2016年12月25日/start=======
//		add(label4, "2, 5, right, default");

		combOutSideRemark = new JComboCheckBox();
//		add(combOutSideRemark, "3, 5, 11, 1, fill, default");
		// ===========取消对外备注选项/lianhe/2016年12月25日/end=======

		// 对内备注
		JLabel label6 = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.insideRemark.label") + "：");
		// ===========修改对内备注的布局/lianhe/2016年12月25日/start=======
//		add(label6, "14, 5, 4, 1, right, default");
		add(label6, "2, 5, right, default");
		txtInsideRemark = new JTextFieldValidate();
//		add(txtInsideRemark, "18, 5, 13, 1, fill, default");
		add(txtInsideRemark, "3, 5, 24, 1, fill, default");
		// ===========修改对内备注的布局/lianhe/2016年12月25日/end=======
		txtInsideRemark.setColumns(TEN);

		// 储运事项
		label14 = new JLabel(i18n.get

		("foss.gui.creating.cargoInfoPanel.transportationRemark.label") + "：");
		add(label14, "34, 5, right, default");

		txtTransportationRemark = new JTextFieldValidate();
		add(txtTransportationRemark, "36, 5, 17, 1, fill, default");
		txtTransportationRemark.setColumns(TEN);
		txtTransportationRemark.setEditable(false);

	}

	public JComboBox getCombOutSideRemark() {
		return combOutSideRemark;
	}

	public JCheckBox getChbCarThrough() {
		return chbCarThrough;
	}

	public JTextFieldValidate getTxtWeight() {
		return txtWeight;
	}

	public JTextFieldValidate getTxtTotalPiece() {
		return txtTotalPiece;
	}

	public JTextFieldValidate getTxtVolume() {
		return txtVolume;
	}

	/**
	 * @return the btnWood
	 */
	public JButton getBtnWood() {
		return btnWood;
	}

	/**
	 * @param btnWood
	 *            the btnWood to set
	 */
	public void setBtnWood(JButton btnWood) {
		this.btnWood = btnWood;
	}

	/**
	 * 
	 * <p>
	 * (将2个单选框合并到一个按钮组)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-19 下午02:11:49
	 * @see
	 */
	/*private void addRadioToGroup() {
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbnA);
		bg.add(rbnB);
	}

	public JRadioButton getRbnA() {
		return rbnA;
	}

	public JRadioButton getRbnB() {
		return rbnB;
	}*/

	public JCheckBox getChbValuable() {
		return chbValuable;
	}

	public JTextFieldValidate getTxtGoodsName() {
		return txtGoodsName;
	}

	public JComboBox getCombGoodsType() {
		return combGoodsType;
	}

	public JCheckBox getChbBigTicket() {
		return chbBigTicket;
	}

	public JTextFieldValidate getTxtTransportationRemark() {
		return txtTransportationRemark;
	}

	/**
	 * @return the txtInsideRemark
	 */
	public JTextFieldValidate getTxtInsideRemark() {
		return txtInsideRemark;
	}

	/**
	 * @param txtInsideRemark
	 *            the txtInsideRemark to set
	 */
	public void setTxtInsideRemark(JTextFieldValidate txtInsideRemark) {
		this.txtInsideRemark = txtInsideRemark;
	}
	
	public JButton getBtnPacking() {
		return btnPacking;
	}

	public void setBtnPacking(JButton btnPacking) {
		this.btnPacking = btnPacking;
	}

	/**
	 * 货物类型
	 * @return
	 */
	public JLabel getLabel15() {
		return label15;
	}

	public void setLabel15(JLabel label15) {
		this.label15 = label15;
	}
	
	

}