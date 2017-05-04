/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpNewAddAction extends AbstractButtonActionListener<ExpWaybillEditUI> {

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	ExpWaybillEditUI ui;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ExpNewAddAction.class);
	
	/**
	 * 
	 * <p>
	 * 运单暂存
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		IApplication application = ui.getApplication();
		IEditor editor = ui.getEditor();
		editor.close();
		
		if(WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType())){
			openFocusWaybill(application);
		}else{
			openSalesDepartmentWaybill(application);
		}
			

	}
	
	/**
	 * 
	 * 打开集中开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:19:28
	 */
	private void openFocusWaybill(IApplication application){
		EditorConfig editConfig = new EditorConfig();
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
		waybillEditUI.openUI();
	}
	
	
	/**
	 * 
	 * 打开营业部开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:20:02
	 */
	private void openSalesDepartmentWaybill(IApplication application){
		EditorConfig editConfig = new EditorConfig();
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();
	}
	
	/**
	 * setIInjectUI
	 */
	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
