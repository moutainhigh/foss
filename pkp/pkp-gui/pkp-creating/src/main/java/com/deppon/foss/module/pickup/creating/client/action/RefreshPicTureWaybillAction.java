package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;

public class RefreshPicTureWaybillAction  implements ActionListener{

	PictureWaybillEditUI ui;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillSubmitAction.class);

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	@Override
	public void actionPerformed(ActionEvent e) {
		//int pictureWaybillCount = waybillService.getPictureWaybillCount();
		//显示本地和全国待录入条数     by:352676
		//本地待补录运单数
		int pictureWaybillLocalCount = waybillService.getPictureWaybillLocalCount();
		//全国待补录运单数
		int pictureWaybillAllCount = waybillService.getPictureWaybillAllCount();
		Boolean falg = ui.picturePanel.lable1.isVisible();
		if(pictureWaybillLocalCount > 0 && falg){
			ui.picturePanel.pictureViewComp.setVisible(false);
			ui.picturePanel.lable1.setVisible(false);
			ui.picturePanel.nextButton.setVisible(true);
			ui.picturePanel.lable2.setVisible(false);
			//ui.picturePanel.button.setText(i18n.get("foss.gui.creating.picturePanel.label",pictureWaybillCount));
			//显示本地和全国待录入条数     by:352676
			ui.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					pictureWaybillLocalCount)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							pictureWaybillAllCount)+"</html>");
		}
		if(pictureWaybillLocalCount > 0 && !falg){
			//ui.picturePanel.button.setText(i18n.get("foss.gui.creating.picturePanel.label",pictureWaybillCount));
			//显示本地和全国待录入条数     by:352676
			ui.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					pictureWaybillLocalCount)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							pictureWaybillAllCount)+"</html>");
		}
		if(pictureWaybillLocalCount == 0 && !falg){
			/*if(StringUtils.isNotBlank(ui.getPictureOperateType()) && 
					"VIEWORMODIFY".equals(ui.getPictureOperateType())){
				ui.picturePanel.button.setText(i18n.get("foss.gui.creating.picturePanel.label",pictureWaybillCount));
			}
			AddWaybillUtils utils = new AddWaybillUtils(ui);
			utils.newPictureWaybill();*/
			//ui.picturePanel.button.setText(i18n.get("foss.gui.creating.picturePanel.label",pictureWaybillCount));
			//显示本地和全国待录入条数     by:352676
			ui.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					pictureWaybillLocalCount)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							pictureWaybillAllCount)+"</html>");
		}
	}

	public RefreshPicTureWaybillAction(PictureWaybillEditUI ui){
		this.ui = ui;
	}
}
