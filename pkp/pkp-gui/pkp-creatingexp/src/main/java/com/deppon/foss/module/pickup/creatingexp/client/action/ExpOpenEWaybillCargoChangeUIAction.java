package com.deppon.foss.module.pickup.creatingexp.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;
/**
 * <p>导入电子面单UI的action</p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-23 08:27:32
 */
public class ExpOpenEWaybillCargoChangeUIAction extends AbstractOpenUIAction<ExpWaybillBatchChangeWeightUI> {

	private static final long serialVersionUID = 1L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory.getLog(ExpOpenEWaybillCargoChangeUIAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpOpenEWaybillCargoChangeUIAction.class);

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		//大客户电子运单更改管理
		editConfig.setTitle(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.query.title"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI");
		ExpWaybillBatchChangeWeightUI salesDeptEWaybillUI = (ExpWaybillBatchChangeWeightUI) editor.getComponent();
		salesDeptEWaybillUI.openUI();
		log.info("com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI" + editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(salesDeptEWaybillUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<ExpWaybillBatchChangeWeightUI> getOpenUIType() {
		return ExpWaybillBatchChangeWeightUI.class;
	}
}