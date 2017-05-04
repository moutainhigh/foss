/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpNumberPanel  extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpNumberPanel.class);

	/**
	 * 运单号
	 */
	private JLabel lblNumber;
	
	/**
	 * 网单
	 */
	private JLabel lblOrderLabel;
	
	/**
	 * 网单号
	 */
	private JLabel lblOrderNumber;

	/**
	 * Create the panel.
	 */
	public ExpNumberPanel() {
		init();

	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:41:32
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder());
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(223dlu;default)"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));

		//运单号
		JLabel lblWaybillLabel = new JLabel(i18n.get("foss.gui.creating.numberPanel.waybillNo.label")+"：");
		lblWaybillLabel.setForeground(Color.RED);
		lblWaybillLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_33));
		add(lblWaybillLabel, "4, 1");

		lblNumber = new JLabel("");
		lblNumber.setForeground(Color.RED);
		lblNumber.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_33));
		add(lblNumber, "6, 1");

		//订单号
		lblOrderLabel = new JLabel(i18n.get("foss.gui.creating.numberPanel.orderNo.label")+"：");
		lblOrderLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_30));
		lblOrderLabel.setVisible(false);
		add(lblOrderLabel, "12, 1");

		lblOrderNumber = new JLabel("");
		lblOrderNumber.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_30));
		add(lblOrderNumber, "14, 1");
	}

	public JLabel getLblNumber() {
		return lblNumber;
	}

	
	/**
	 * @return  the lblOrderNumber
	 */
	public JLabel getLblOrderNumber() {
		return lblOrderNumber;
	}

	
	/**
	 * @return  the lblOrderLabel
	 */
	public JLabel getLblOrderLabel() {
		return lblOrderLabel;
	}

	
	/**
	 * @param lblOrderLabel the lblOrderLabel to set
	 */
	public void setLblOrderLabel(JLabel lblOrderLabel) {
		this.lblOrderLabel = lblOrderLabel;
	}
	
	
}
