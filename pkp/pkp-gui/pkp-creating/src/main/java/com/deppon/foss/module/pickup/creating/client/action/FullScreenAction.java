package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.action.LoadPickupStationAction;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;


public class FullScreenAction implements IButtonActionListener<WaybillEditUI>{

	 /**
	  * 主界面
	  */
	private WaybillEditUI ui;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(LoadPickupStationAction.class);

	private static final int NUM_1265 = 1265;

	private static final int NUM_310 = 310;

	private static final int NUM_1250 = 1250;

	private static final int NUM_845 = 845;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(i18n.get("foss.gui.creating.buttonPanel.fullScreen.label1").equals(e.getActionCommand())){
			ui.pictureWaybillEditUI.picturePanel.setVisible(true);
			ui.pictureWaybillEditUI.sp.setPreferredSize(new Dimension(NUM_1265, NUM_310));
			ui.buttonPanel.fullScreen.setText(i18n.get("foss.gui.creating.buttonPanel.fullScreen.label"));
		}else if(i18n.get("foss.gui.creating.buttonPanel.fullScreen.label").equals(e.getActionCommand())){
			ui.pictureWaybillEditUI.picturePanel.setVisible(false);
			ui.pictureWaybillEditUI.sp.setPreferredSize(new Dimension(NUM_1250,NUM_845));
			ui.buttonPanel.fullScreen.setText(i18n.get("foss.gui.creating.buttonPanel.fullScreen.label1"));
		}
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

}
