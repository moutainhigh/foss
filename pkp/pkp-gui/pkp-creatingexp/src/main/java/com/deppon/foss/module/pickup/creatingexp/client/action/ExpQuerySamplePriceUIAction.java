package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpCalculateCostsDialog;

public class ExpQuerySamplePriceUIAction implements IButtonActionListener<ExpWaybillEditUI>{
	
	private static final int NUM_682 = 682;
	
	private static final int NUM_441 = 441;
	
	ExpWaybillEditUI ui;
	@Override
	public void actionPerformed(ActionEvent e) {
		ExpCalculateCostsDialog dialog = ui.getExpCalculateCostsDialog();
		dialog.setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_682, NUM_441);
		ModalFrameUtil.getInstance().showAsModal(ui.getExpCalculateCostsDialog(), ApplicationContext.getApplication().getWorkbench().getFrame());
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}
}
