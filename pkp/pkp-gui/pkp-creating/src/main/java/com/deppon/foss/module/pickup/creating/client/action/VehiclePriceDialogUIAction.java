package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.CalculateCostsDialog;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.CalculateVehicleDialog;

/**
 * 整车报价
 * @author yangkang
 *
 */
public class VehiclePriceDialogUIAction implements IButtonActionListener<CalculateCostsDialog>{
	CalculateCostsDialog ui;
	/**
	 * @author yangkang
	 * 打开整车报价窗口
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CalculateVehicleDialog dialog = ui.getCalculateVehicleDialog();
		ModalFrameUtil.getInstance().showAsModal(dialog, ui);
		
	}

	@Override
	public void setInjectUI(CalculateCostsDialog ui) {
		this.ui = ui;
	}
}
