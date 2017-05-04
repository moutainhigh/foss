package com.deppon.foss.module.pickup.creating.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI;
/**
 * 零担电子运单管理页面ui的action
 * @author 305082
 *
 */
@SuppressWarnings("serial")
public class LTLEWaybillManageUIAction extends AbstractOpenUIAction<LTLEWaybillManageUI>  {


	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory
			.getLog(LTLEWaybillManageUIAction.class);


	/**
	 * 功能：setApplication
	 */
	@Override
	public void setApplication(IApplication application) {
		this.application = application;

	}

	/**
	 * 功能：openUIAction
	 */
	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle("零担电子面单管理");
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI");

		LTLEWaybillManageUI salesDeptWaybillUI = (LTLEWaybillManageUI) editor
				.getComponent();
		salesDeptWaybillUI.openUI();

		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI"
				+ editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(salesDeptWaybillUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<LTLEWaybillManageUI> getOpenUIType() {
		return LTLEWaybillManageUI.class;
	}


}
