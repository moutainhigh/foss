package com.deppon.foss.module.pickup.creating.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManagerUI;

/**
 * 结算中心运单管理界面UI action
 * 
 *
 */
@SuppressWarnings("serial")
public class CUBCWaybillManagerUIAction extends AbstractOpenUIAction<CUBCWaybillManagerUI>{
	
	private IApplication application;

	private EditorConfig editConfig;
	
	// 日志
	private static final Log log = LogFactory
				.getLog(CUBCWaybillManagerUIAction.class);
	
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle("结算中心运单管理");
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManagerUI");

		CUBCWaybillManagerUI cubcWaybillManagerUI = (CUBCWaybillManagerUI) editor
				.getComponent();
		cubcWaybillManagerUI.openUI();

		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManagerUI"
				+ editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(cubcWaybillManagerUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<CUBCWaybillManagerUI> getOpenUIType() {
		return CUBCWaybillManagerUI.class;
	}
}
