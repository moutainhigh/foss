package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.PictureViewComp;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.BackWaybillAction;
import com.deppon.foss.module.pickup.creating.client.action.RefreshPicTureWaybillAction;
import com.deppon.foss.module.pickup.creating.client.action.TopPicTureWaybillAction;
import com.deppon.foss.module.pickup.creating.client.common.AddWaybillUtils;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class PicturePanel extends JPanel{

	private static final long serialVersionUID = 6073181148630726542L;
	
	private static final int NUM_216 = 216;

	private static final int NUM_164 = 164;
	
	private static final int NUM_225 = 225;
	
	private static final int NUM_824 = 824;
	
	private static final int NUM_535 = 535;
	
	private static final int NUM_800 = 800;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(NumberPanel.class);
	
	public PictureWaybillEditUI ui;
	
	//图片控件面板
	public PictureViewComp pictureViewComp;
	
	//退回按钮
	public JButton backButton;
	
	//获取下一单按钮
	public JButton nextButton;
	
	public JLabel lable1;
	
	public JLabel lable2;
	
	//显示待录入统计框
	public JButton button;
	
	private JPanel panel;
    private JPanel panel_2;
	//显示置顶按钮
	public JButton topButton;
	
	/**
	 * @需求：智能开单项目
	 * @功能：保存旋转图片时间（二期删除）
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19上午10:01
	 */
	WaybillPanelVo bean;
	
	public PicturePanel (){
	}
	
	public PicturePanel (PictureWaybillEditUI ui){
		this.ui = ui;
		init();
	}

	/**
	 * 
	 * (初始化)
	 * 
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.picturePanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		pictureViewComp = new PictureViewComp(NUM_800,NumberConstants.NUMBER_500);
		pictureViewComp.setPreferredSize(new Dimension(NUM_824,NUM_535));
		add(pictureViewComp, "2, 2, fill, fill");
		
		lable1 = new JLabel(i18n.get("foss.gui.creating.picturePanel.label1"),JLabel.CENTER);
		lable1.setFont(new Font("Dialog",0,NumberConstants.NUMBER_36));
		lable1.setForeground(Color.red);
		lable1.setPreferredSize(new Dimension(NUM_824,NUM_535));
		add(lable1, "2, 2, fill, fill");
		lable1.setVisible(false);
		
		lable2 = new JLabel(i18n.get("foss.gui.creating.picturePanel.label2"),JLabel.CENTER);
		lable2.setFont(new Font("Dialog",0,NumberConstants.NUMBER_36));
		lable2.setForeground(Color.red);
		lable2.setPreferredSize(new Dimension(NUM_824,NUM_535));
		add(lable2, "2, 2, fill, fill");
		lable2.setVisible(false);

		
		nextButton = new JButton(i18n.get("foss.gui.creating.picturePanel.nextButten"));
		nextButton.setFont(new Font("Dialog",0,NumberConstants.NUMBER_36));
		nextButton.setForeground(Color.red);
		nextButton.setContentAreaFilled(false);
		nextButton.setPreferredSize(new Dimension(NUM_824,NUM_535));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIUtils.enableUI(ui);
				ui.waybillEdit.buttonPanel.getBtnPrint().setEnabled(false);
				ui.waybillEdit.buttonPanel.getBtnPreview().setEnabled(false);
				ui.waybillEdit.buttonPanel.getBtnPrintLabel().setEnabled(false);
				ui.waybillEdit.pictureTransferInfoPanel.getCombFreightMethod().setEnabled(false);
				ui.waybillEdit.pictureTransferInfoPanel.getCombFlightNumberType().setEnabled(false);
				ui.waybillEdit.pictureCargoInfoPanel.getChbCarThrough().setEnabled(false);
				ui.waybillEdit.pictureCargoInfoPanel.getChbBigTicket().setEnabled(false);
				//ui.waybillEdit.pictureCargoInfoPanel.getRbnA().setEnabled(false);
				//ui.waybillEdit.pictureCargoInfoPanel.getRbnB().setEnabled(false);
				ui.waybillEdit.pictureCargoInfoPanel.getBtnWood().setEnabled(false);
				ui.waybillEdit.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(false);
				ui.waybillEdit.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(false);
				ui.waybillEdit.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);
				ui.waybillEdit.billingPayPanel.getTxtIncrementCharge().setEnabled(false);
				ui.waybillEdit.billingPayPanel.getTxtPromotionTotal().setEditable(false);
				ui.waybillEdit.billingPayPanel.getTxtPromotionTotal().setEnabled(false);
				ui.waybillEdit.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
				ui.waybillEdit.billingPayPanel.getTxtArrivePayment().setEnabled(false);
				ui.waybillEdit.billingPayPanel.getTxtArrivePayment().setEditable(false);
				AddWaybillUtils utils = new AddWaybillUtils(ui);
				utils.newPictureWaybill();
			}
		});	
		add(nextButton, "2, 2, fill, fill");
		nextButton.setVisible(false);
		
		backButton = new JButton(i18n.get("foss.gui.creating.picturePanel.butten"));
		backButton.setPreferredSize(new Dimension(NUM_225,NumberConstants.NUMBER_100));
		backButton.addActionListener(new BackWaybillAction(ui));
		add(backButton, "4, 2");
		
		panel = new JPanel();
		add(panel, "6, 2, fill, bottom");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		button = new JButton();
		button.setPreferredSize(new Dimension(NUM_216,NumberConstants.NUMBER_100));
		button.addActionListener(new RefreshPicTureWaybillAction(ui));
		panel.add(button, "2, 4, fill, fill");
		
		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(NUM_216,NUM_164));
		panel.add(panel_2, "2, 6, fill, fill");
		
		topButton = new JButton(i18n.get("foss.gui.creating.picturePanel.topButton"));
		topButton.addActionListener(new TopPicTureWaybillAction(ui));
		topButton.setMnemonic(KeyEvent.VK_D);
		panel.add(topButton, "2, 8, fill, bottom");
		
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

	public JLabel getLable1() {
		return lable1;
	}

	public void setLable1(JLabel lable1) {
		this.lable1 = lable1;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public void setNextButton(JButton nextButton) {
		this.nextButton = nextButton;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}

	public void setBean(WaybillPanelVo bean) {
		this.bean = bean;
		pictureViewComp.setBean(bean);
	}
}
