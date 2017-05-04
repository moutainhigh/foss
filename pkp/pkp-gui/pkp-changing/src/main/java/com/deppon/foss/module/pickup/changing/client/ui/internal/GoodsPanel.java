/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/GoodsPanel.java
 * 
 * FILE NAME        	: GoodsPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.changing.client.ui.internal;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
import com.deppon.foss.module.pickup.changing.client.action.QueryPackingAction;
import com.deppon.foss.module.pickup.changing.client.action.ShowChangeGoodsQtyDialogAction;
import com.deppon.foss.module.pickup.changing.client.action.ShowEnterYokeInfoChangeDialogAction;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 货物信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 上午9:21:52,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:21:52
 * @since
 * @version
 */
@ContainerSeq(seq=6)
public class GoodsPanel extends JPanel {
	
	
	
	private static final int EIGHT = 8;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int TEN = 10;


	/**
	 * DEFAULT CSS
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * FIELD NAME
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7800203042362522488L;

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(GoodsPanel.class);

	/**
	 * 货物重量
	 */
	@Bind("goodsWeightTotal")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "货物重量") })
	@FocusSeq(seq=4)
	private JTextFieldValidate txtWeight;

	/**
	 * 货物件数
	 */
	@Bind("goodsQtyTotal")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "货物件数") })
	@FocusSeq(seq=2)
	private JTextFieldValidate txtPiece;
	
	/**
	 * 货物名称
	 */
	@Bind("goodsName")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "货物名称") })
	@FocusSeq(seq=1)
	private JTextFieldValidate txtName;

	/**
	 * 尺寸
	 */
	@Bind("goodsSize")
	@FocusSeq(seq=5)
	private JTextFieldValidate txtSize;

	/**
	 * 货物体积
	 */
	@Bind("goodsVolumeTotal")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "货物体积") })
	@FocusSeq(seq=6)
	private JTextFieldValidate txtVolume;

	/**
	 * 大车直送
	 */
	@Bind("carDirectDelivery")
	@FocusSeq(seq=16)
	private JCheckBox chbCarThrough;

	/**
	 * 贵重物品
	 */
	@Bind("preciousGoods")
	@FocusSeq(seq=17)
	private JCheckBox chbValuable;

	/**
	 * 特殊物品
	 */
	@Bind("specialShapedGoods")
	@FocusSeq(seq=18)
	private JCheckBox chbSpecialGoods;

	/**
	 * 大票货
	 */
	@Bind("bigTicket")
	private JCheckBox chbBigTicket;
	/**
	 * 
	 */
	/*@Bind("goodsType#A")
	@FocusSeq(seq=14)
	private JRadioButton rdoA;*/

	/**
	 * 
	 */
	/*@Bind("goodsType#B")
	@FocusSeq(seq=15)
	private JRadioButton rdoB;*/
	
	@FocusSeq(seq=15)
	private JLabel lblGoodsType;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	
	/**
	 * @return the buttonGroup
	 */
	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}


	/**
	 * 空运货物类型
	 */
	@Bind("airGoodsType")
	private JComboBox combGoodsType;

	/**
	 * 纸
	 */
	@Bind("paper")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "纸") })
	@FocusSeq(seq=7)
	private JTextFieldValidate txtPaper;

	/**
	 * 木
	 */
	@Bind("wood")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "木") })
	@FocusSeq(seq=8)
	private JTextFieldValidate txtWood;

	/**
	 * 纤
	 */
	@Bind("fibre")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "纤") })
	@FocusSeq(seq=9)
	private JTextFieldValidate txtFibre;

	/**
	 * 托
	 */
	@Bind("salver")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "托") })
	@FocusSeq(seq=10)
	private JTextFieldValidate txtSalver;

	/**
	 * 膜
	 */
	@Bind("membrane")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "膜") })
	@FocusSeq(seq=11)
	private JTextFieldValidate txtMembrane; 

	/**
	 * 其他
	 */
	@Bind("otherPackage")
	@FocusSeq(seq=13)
	private JTextFieldValidate txtOther;
	
	/**
	 * 代打木架
	 */
	@ButtonAction(type=ShowEnterYokeInfoChangeDialogAction.class)
	@FocusSeq(seq=12)
	private JButton button;
	
	/**
	 * 储运事项
	 */
	@Bind("transportationRemark")
	@FocusSeq(seq=19)
	private JTextFieldValidate txtTransportationRemark;
	
	private DefaultComboBoxModel combGoodsTypeModel;
	
	/**
	 * 详细信息
	 */
	@ButtonAction(type=ShowChangeGoodsQtyDialogAction.class)
	@FocusSeq(seq=3)
	private JButton buttonDetail;
	
	/**
	 * 增加打包装按钮
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-27下午14:13
	 */
	@ButtonAction(type=QueryPackingAction.class)
	private JButton packingButton;
	
	
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
	private DefaultComboBoxModel industrySourceCategoryModel;//行业货源品类
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	

	public GoodsPanel() {
		init();
	}


	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changing.goodsPanel")));
		setLayout(new FormLayout(new ColumnSpec[] {
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
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblName = new JLabel(i18n.get("pickup.changing.goodsName"));
		add(lblName, "2, 1");

		txtName = new JTextFieldValidate();
		add(txtName, "3, 1, 2, 1, left, default");
		txtName.setColumns(NumberConstants.NUMBER_12);
		
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
		lblIndustrySourceCategory = new JLabel("行业货源品类:");
		add(lblIndustrySourceCategory, "4, 1, right, default");
		txtIndustrySourceCategory = new JComboBox();
		add(txtIndustrySourceCategory, "5, 1, 4, 1, fill, default");
		industrySourceCategoryModel = (DefaultComboBoxModel) txtIndustrySourceCategory.getModel();
		txtIndustrySourceCategory.setEnabled(false);
		txtIndustrySourceCategory.setEditable(false);
		
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */

		//件数
		JLabel lblPiece = new JLabel(i18n.get("foss.gui.changing.goodsPanel.totalPiece.label")+"：");
		//add(lblPiece, "7, 1, 2, 1, left, default");

		
		txtPiece = new JTextFieldValidate();
		txtPiece.setHorizontalAlignment(SwingConstants.CENTER);
		//zxy 20130909 修改：不允许输入负数  start
		txtPiece.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数  end
		txtPiece.setColumns(FOUR);
		JPanel jp = new JPanel();
		add(jp, "10, 1, 3, 1, center, default");
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
		jp.add(lblPiece);
		jp.add(txtPiece);
		

		JLabel lblWeight = new JLabel(i18n.get("pickup.changing.goodsWeight"));
		add(lblWeight, "12, 1, right, default");

		txtWeight = new JTextFieldValidate();
		add(txtWeight, "14, 1, left, default");
		txtWeight.setColumns(EIGHT);
		FloatDocument weight = new FloatDocument(EIGHT,2);
		txtWeight.setDocument(weight);
		
		buttonDetail = new JButton(i18n.get("foss.gui.changing.goodsPanel.waybillDetail.label"));
		jp.add(buttonDetail);
		txtWeight.setColumns(TEN);

		chbCarThrough = new JCheckBox(i18n.get("pickup.changing.carThrough"));
		add(chbCarThrough, "14, 1, right, default");

		JLabel lblSize = new JLabel(i18n.get("pickup.changing.goodsSize"));
		add(lblSize, "2, 3, right, default");

		txtSize = new JTextFieldValidate();
		add(txtSize, "4, 3, 5, 1, fill, default");
		//txtSize.setDocument(new FloatDocument(EIGHT));
		txtSize.setColumns(TEN);

		JLabel lblVolume = new JLabel(i18n.get("pickup.changing.goodsVolume"));
		add(lblVolume, "10, 3, right, default");

		txtVolume = new JTextFieldValidate();
		add(txtVolume, "12, 3, fill, default");
		txtVolume.setColumns(TEN);
		txtVolume.setText("0");
		FloatDocument volume = new FloatDocument(EIGHT,2);
		txtVolume.setDocument(volume);

		chbValuable = new JCheckBox(i18n.get("pickup.changing.valuable"));
//		add(chbValuable, "14, 3");
		
		JLabel lblPaper = new JLabel(i18n.get("pickup.changing.goodsPaper"));
		add(lblPaper, "2, 5, right, default");

		JPanel panel = new JPanel();
		add(panel, "4, 5, 5, 1, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtPaper = new JTextFieldValidate();
		//控件内容右对齐
		txtPaper.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		panel.add(txtPaper);
		//zxy 20130909 修改：不允许输入负数  start
		txtPaper.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数 end
		txtPaper.setColumns(TEN);

		panel.add(Box.createHorizontalStrut(FIVE));

		//纸
		JLabel label1 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.paper.label"));
		panel.add(label1);

		panel.add(Box.createHorizontalStrut(FIVE));

		txtWood = new JTextFieldValidate();
		//控件内容右对齐
		txtWood.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		panel.add(txtWood);
		//zxy 20130909 修改：不允许输入负数  start
		txtWood.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数  end
		txtWood.setColumns(TEN);

		panel.add(Box.createHorizontalStrut(FIVE));

		//木
		JLabel label2 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.wood.label"));
		panel.add(label2);

		panel.add(Box.createHorizontalStrut(FIVE));

		txtFibre = new JTextFieldValidate();
		//控件内容右对齐
		txtFibre.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		panel.add(txtFibre);
		//zxy 20130909 修改：不允许输入负数  start
		txtFibre.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数  end
		txtFibre.setColumns(TEN);

		panel.add(Box.createHorizontalStrut(FIVE));

		//纤
		JLabel label3 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.fiber.label"));
		panel.add(label3);

		panel.add(Box.createHorizontalStrut(FIVE));

		txtSalver = new JTextFieldValidate();
		//控件内容右对齐
		txtSalver.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		panel.add(txtSalver);
		//zxy 20130909 修改：不允许输入负数  start
		txtSalver.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数  end
		txtSalver.setColumns(TEN);

		panel.add(Box.createHorizontalStrut(FIVE));

		//托
		JLabel label4 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.salver.label"));
		panel.add(label4);

		panel.add(Box.createHorizontalStrut(FIVE));

		txtMembrane = new JTextFieldValidate();
		//控件内容右对齐
		txtMembrane.setHorizontalAlignment(JTextFieldValidate.RIGHT);
		panel.add(txtMembrane);
		//zxy 20130909 修改：不允许输入负数  start
		txtMembrane.setDocument(new PackageNumberDocument(FOUR));
		//zxy 20130909 修改：不允许输入负数  end
		txtMembrane.setColumns(TEN);

		panel.add(Box.createHorizontalStrut(FIVE));

		//膜
		JLabel label5 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.membrane.label"));
		panel.add(label5);
		
		//代打木架---改为打包装
//		button = new JButton(i18n.get("foss.gui.changing.goodsPanel.wooden.label"));
		button = new JButton("打包裝");
		panel.add(button);
		button.setEnabled(false);
		
		/**
		 * 增加打包装按钮
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-27
		 */
		packingButton = new JButton("打包装");
//		panel.add(packingButton);

		//其他
		JLabel label = new JLabel(i18n.get("foss.gui.changing.goodsPanel.other.label")+"：");
		add(label, "10, 5, left, default");

		txtOther = new JTextFieldValidate();
		txtOther.setDocument(new LengthDocument(NumberConstants.NUMBER_30));
		add(txtOther, "12, 5, fill, default");
		txtOther.setColumns(TEN);

		chbSpecialGoods = new JCheckBox(
				i18n.get("pickup.changing.specialGoods"));
		add(chbSpecialGoods, "14, 5");

		chbBigTicket = new JCheckBox(i18n.get("pickup.changing.BigTicket"));
		chbBigTicket.setEnabled(false);
		add(chbBigTicket,"14, 7");
		
		 lblGoodsType = new JLabel(i18n.get("pickup.changing.goodsType"));
		add(lblGoodsType, "2, 7");
		lblGoodsType.setVisible(false);

		JPanel panel10 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel10.getLayout();
		flowLayout.setHgap(TEN);
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel10, "4, 7, fill, fill");

		/*rdoA = new JRadioButton("A");
		panel10.add(rdoA);
		buttonGroup.add(rdoA);

		rdoB = new JRadioButton("B");
		panel10.add(rdoB);
		buttonGroup.add(rdoB);*/
		
		combGoodsType = new JComboBox();
		panel10.add(combGoodsType);
		combGoodsTypeModel = (DefaultComboBoxModel)combGoodsType.getModel();
		combGoodsType.setVisible(false);
		
		label1 = new JLabel(i18n.get("foss.gui.changing.goodsPanel.outSideRemark.label")+"：");
		add(label1, "6, 7, right, default");
		
		txtTransportationRemark = new JTextFieldValidate();
		txtTransportationRemark.setEditable(false);
		add(txtTransportationRemark, "8, 7, 5, 1, fill, default");
		txtTransportationRemark.setColumns(TEN);

	}

	/**
	 * @return the chbCarThrough
	 */
	public JCheckBox getChbCarThrough() {
		return chbCarThrough;
	}

	/**
	 * @return the txtWeight
	 */
	public JTextFieldValidate getTxtWeight() {
		return txtWeight;
	}

	/**
	 * @return the txtPiece
	 */
	public JTextFieldValidate getTxtPiece() {
		return txtPiece;
	}

	/**
	 * @return the txtName
	 */
	public JTextFieldValidate getTxtName() {
		return txtName;
	}

	/**
	 * @return the txtVolume
	 */
	public JTextFieldValidate getTxtVolume() {
		return txtVolume;
	}

	public JCheckBox getChbValuable() {
		return chbValuable;
	}

	
	public JCheckBox getChbSpecialGoods() {
		return chbSpecialGoods;
	}

	public JCheckBox getChbBigTicket() {
		return chbBigTicket;
	}

	/*public JRadioButton getRdoA() {
		return rdoA;
	}

	
	public JRadioButton getRdoB() {
		return rdoB;
	}*/

	
	public JTextFieldValidate getTxtSize() {
		return txtSize;
	}

	
	/**
	 * @return the txtTransportationRemark
	 */
	public JTextFieldValidate getTxtTransportationRemark() {
		return txtTransportationRemark;
	}

	public JComboBox getCombGoodsType() {
		return combGoodsType;
	}

	public DefaultComboBoxModel getCombGoodsTypeModel() {
		return combGoodsTypeModel;
	}

	
	/**
	 * @return the eight
	 */
	public static int getEight() {
		return EIGHT;
	}

	
	/**
	 * @return the four
	 */
	public static int getFour() {
		return FOUR;
	}

	
	/**
	 * @return the five
	 */
	public static int getFive() {
		return FIVE;
	}

	
	/**
	 * @return the defaultgrow
	 */
	public static String getDefaultgrow() {
		return DEFAULTGROW;
	}

	
	/**
	 * @return the fieldname
	 */
	public static String getFieldname() {
		return FIELDNAME;
	}

	
	/**
	 * @return the txtPaper
	 */
	public JTextFieldValidate getTxtPaper() {
		return txtPaper;
	}

	
	/**
	 * @return the txtWood
	 */
	public JTextFieldValidate getTxtWood() {
		return txtWood;
	}

	
	/**
	 * @return the txtFibre
	 */
	public JTextFieldValidate getTxtFibre() {
		return txtFibre;
	}

	
	/**
	 * @return the txtSalver
	 */
	public JTextFieldValidate getTxtSalver() {
		return txtSalver;
	}

	
	/**
	 * @return the txtMembrane
	 */
	public JTextFieldValidate getTxtMembrane() {
		return txtMembrane;
	}

	
	/**
	 * @return the txtOther
	 */
	public JTextFieldValidate getTxtOther() {
		return txtOther;
	}

	/**
	 * 
	 * @return the txtPiece
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-12 下午5:27:12
	 */
	public JTextFieldValidate getTxtTotalPiece() {
		return txtPiece;
	}

	
	/**
	 * @return  the button
	 */
	public JButton getButton() {
		return button;
	}

	
	/**
	 * @param button the button to set
	 */
	public void setButton(JButton button) {
		this.button = button;
	}
	
	public JButton getPackingButton() {
		return packingButton;
	}

	public void setPackingButton(JButton packingButton) {
		this.packingButton = packingButton;

	}

	public DefaultComboBoxModel getIndustrySourceCategoryModel() {
		return industrySourceCategoryModel;
	}

	public void setIndustrySourceCategoryModel(
			DefaultComboBoxModel industrySourceCategoryModel) {
		this.industrySourceCategoryModel = industrySourceCategoryModel;
	}
	



	public JLabel getLblGoodsType() {
		return lblGoodsType;
	}

	public void setLblGoodsType(JLabel lblGoodsType) {
		this.lblGoodsType = lblGoodsType;
	}
	
	

}