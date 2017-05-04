/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.print.ExpChoosePrintTypeDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.print.ExpPrintTimesDialog;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpCreatePrintDialogAction implements
		IButtonActionListener<ExpPrintTimesDialog> {

	ExpPrintTimesDialog ui;

	/**
	 * <p>
	 * (按钮监听事件)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 下午1:49:14
	 * @param arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		ui.setVisible(false);
		if (ui.getIsPrintOrPrePrint()) {
			new ExpChoosePrintTypeDialog(true, ui.getWaybillEditUI());
		} else {
			new ExpChoosePrintTypeDialog(false, ui.getWaybillEditUI());
		}

	}

	@Override
	public void setInjectUI(ExpPrintTimesDialog ui) {
		this.ui = ui;
	}
}
