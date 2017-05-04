package com.deppon.foss.module.pickup.creatingexp.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI;

public class ExpOpenReturnedGoodsWaybillUIAction extends
AbstractOpenUIAction<ExpReturnedGoodsWaybillUI>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory
			.getLog(ExpOpenReturnedGoodsWaybillUIAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpOpenReturnedGoodsWaybillUIAction.class);

	/**
	 * 功能：setApplication
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
		RepositoryCenter.getInstance().register("application", application);
	}
	
	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<ExpReturnedGoodsWaybillUI> getOpenUIType() {
		return ExpReturnedGoodsWaybillUI.class;
	}
	
	@Override
	public void openUIAction() {
		openReturnedGoodsWaybillUI();
	}

	public void openReturnedGoodsWaybillUI(){
		editConfig = new EditorConfig();
		editConfig.setTitle("转寄退回件管理");
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI");
		ExpReturnedGoodsWaybillUI expReturnedGoodsWaybillUI = (ExpReturnedGoodsWaybillUI) editor.getComponent();
		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.ExpReturnedGoodsWaybillUI"+editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(expReturnedGoodsWaybillUI);
	}
	
}
