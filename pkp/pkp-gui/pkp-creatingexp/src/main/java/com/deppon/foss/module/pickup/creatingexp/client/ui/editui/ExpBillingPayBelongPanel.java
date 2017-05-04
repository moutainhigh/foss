/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq = 7)
public class ExpBillingPayBelongPanel  extends JPanel {

	private static final long serialVersionUID = -7566811662764635962L;
	
	/**
	 * 10 for column count
	 */
	private static final int TEN = 10;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpBillingPayBelongPanel.class);
	
	/**
	 * 约车报价
	 */
	@Bind("wholeVehicleAppfee")
	private JTextFieldValidate txtWholeVehicleAppfee;
	/**
	 * 开单报价/公布价运费
	 */
	@Bind("transportFee")
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtPublicCharge;
	
	/**
	 * 整车约车报价标签
	 */
	private JLabel lblWholeVehicleAppfee;
	/**
	 * 开单报价标签
	 */
	private JLabel lblPublicCharge;

	/**
	 * Create the panel.
	 */
	public ExpBillingPayBelongPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		//约车报价
		lblWholeVehicleAppfee = new JLabel(i18n.get("foss.gui.creating.billingPayBelongPanel.wholeVehicleAppfee.label")+"：");
		add(lblWholeVehicleAppfee, "2, 1, right, default");
		lblWholeVehicleAppfee.setVisible(false);
		
		txtWholeVehicleAppfee = new JTextFieldValidate();
		add(txtWholeVehicleAppfee, "4, 1, fill, default");
		txtWholeVehicleAppfee.setColumns(TEN);
		txtWholeVehicleAppfee.setVisible(false);
		txtWholeVehicleAppfee.setEnabled(false);
		
		//公布价运费
		lblPublicCharge = new JLabel(i18n.get("foss.gui.creating.billingPayBelongPanel.publicCharge.label")+"：");
		add(lblPublicCharge, "8, 1, right, default");
		
		txtPublicCharge = new JTextFieldValidate();
		add(txtPublicCharge, "10, 1, fill, default");
		txtPublicCharge.setColumns(TEN);
//		NumberDocument publicCharge = new NumberDocument(TEN);
		FloatDocument publicCharge = new FloatDocument(TEN, 2);
		txtPublicCharge.setDocument(publicCharge);
		txtPublicCharge.setEnabled(false);
	}

	public JTextFieldValidate getTxtWholeVehicleAppfee() {
		return txtWholeVehicleAppfee;
	}

	public JTextFieldValidate getTxtPublicCharge() {
		return txtPublicCharge;
	}

	public JLabel getLblWholeVehicleAppfee() {
		return lblWholeVehicleAppfee;
	}

	public JLabel getLblPublicCharge() {
		return lblPublicCharge;
	}

}
