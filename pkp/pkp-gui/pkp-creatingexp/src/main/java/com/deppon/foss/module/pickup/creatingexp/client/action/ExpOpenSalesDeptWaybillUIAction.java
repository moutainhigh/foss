/**
 * 
 */
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
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpOpenSalesDeptWaybillUIAction extends
		AbstractOpenUIAction<ExpSalesDeptWaybillUI> {

	/**
 * 
 */
	private static final long serialVersionUID = 1L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory
			.getLog(ExpOpenSalesDeptWaybillUIAction.class);

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(ExpOpenSalesDeptWaybillUIAction.class);

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

	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n
				.get("foss.gui.creating.salesDeptWaybillUI.title"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI");

		ExpSalesDeptWaybillUI salesDeptWaybillUI = (ExpSalesDeptWaybillUI) editor
				.getComponent();

		log.info("open UI :com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI"
				+ editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(salesDeptWaybillUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<ExpSalesDeptWaybillUI> getOpenUIType() {
		return ExpSalesDeptWaybillUI.class;
	}

}
