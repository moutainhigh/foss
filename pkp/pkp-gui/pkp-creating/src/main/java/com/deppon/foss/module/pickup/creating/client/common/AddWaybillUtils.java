package com.deppon.foss.module.pickup.creating.client.common;

import java.awt.Dimension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.PictureViewComp;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;

public class AddWaybillUtils {

	PictureWaybillEditUI ui;
	private static final Log LOG = LogFactory.getLog(AddWaybillUtils.class);
	
	private static final int NUM_824 = 824;
	
	private static final int NUM_535 = 535;
	
	public AddWaybillUtils(PictureWaybillEditUI ui){
		this.ui = ui;
	}
	/**
	 * 国际化
	 */
	//private static II18n i18n = I18nManager.getI18n(NewAddAction.class);
	
	/**
	 * 
	 */
	public void newPictureWaybill() {

		/*IApplication application = ui.getApplication();
		IEditor editor = ui.getEditor();
		editor.close();
		openSalesDepartmentWaybill(application);*/
		
		if(ui.getPictureOperateType() != null && 
				"VIEWORMODIFY".equals(ui.getPictureOperateType())){
			ui.setPictureOperateType(null);
		}
		setPicturePanel();
		LOG.info("开单初始化界面组件开始...");
		ui.waybillEdit.initPicture();
		LOG.info("开单初始化界面组建完成...");
		LOG.info("开单初始化数据开始...");
		ui.initPicture();
		LOG.info("开单初始化数据完成...");
	}
	
	private void setPicturePanel(){
		ui.picturePanel.remove(ui.picturePanel.pictureViewComp);
		PictureViewComp pictureViewComp = new PictureViewComp(NumberConstants.NUMBER_800,NumberConstants.NUMBER_500);
		pictureViewComp.setPreferredSize(new Dimension(NUM_824,NUM_535));
		ui.picturePanel.pictureViewComp = pictureViewComp;
		ui.picturePanel.add(pictureViewComp, "2, 2, fill, fill");
		ui.picturePanel.pictureViewComp.setVisible(true);
		ui.picturePanel.lable1.setVisible(false);
		ui.picturePanel.nextButton.setVisible(false);
		ui.picturePanel.lable2.setVisible(false);
		ui.picturePanel.backButton.setEnabled(true);
		/**
		 * 在初始化的时候必须先把先前的数据清空掉和界面做一些处理
		 * 
		 * 
		 */

		ui.waybillEdit.incrementPanel.getCombPaymentMode().setEnabled(true);
	}

	
	/**
	 * 
	 * 打开图片开单
	 */
/*	private void openSalesDepartmentWaybill(IApplication application)
	{
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.newAddAction.waybillPicture.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI");

		PictureWaybillEditUI prictrueWaybillEditUI = (PictureWaybillEditUI) editor.getComponent();
		prictrueWaybillEditUI.setEditor(editor);
		//圖片开单
		prictrueWaybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
		prictrueWaybillEditUI.setPictureWaybillType(WaybillConstants.WAYBILL_PICTURE);
		prictrueWaybillEditUI.openUI();
		prictrueWaybillEditUI.addAncestorListener(new PictureJPanelExitListener(prictrueWaybillEditUI));
	}*/
}
