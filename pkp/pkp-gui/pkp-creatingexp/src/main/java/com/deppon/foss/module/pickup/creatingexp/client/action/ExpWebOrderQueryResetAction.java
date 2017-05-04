/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.order.ExpWebOrderDialog;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWebOrderQueryResetAction implements
		IButtonActionListener<ExpWebOrderDialog> {

	private ExpWebOrderDialog ui;

	@Override
	public void actionPerformed(ActionEvent e) {
		ui.getOrderComponentListener().resetToDefaultValue();
	}

	@Override
	public void setInjectUI(ExpWebOrderDialog ui) {
		this.ui = ui;
	}

}
