package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.order.BackPictureWaybillDialog;

//图片退回
public class BackWaybillAction implements ActionListener{

	PictureWaybillEditUI ui;

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BackPictureWaybillDialog dialog = new BackPictureWaybillDialog(ui);
		WindowUtil.centerAndShow(dialog);
	}
	
	public BackWaybillAction(PictureWaybillEditUI ui){
		this.ui = ui;
	}

}
