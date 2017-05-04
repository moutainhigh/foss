package com.deppon.foss.module.pickup.creating.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
/**
 * <p>导入电子面单UI的action</p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-23 08:27:32
 */
public class OpenBatchCreateWaybillUIAction extends AbstractOpenUIAction<OpenBatchCreateWaybillUI> {

	private static final long serialVersionUID = 1L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory.getLog(OpenBatchCreateWaybillUIAction.class);

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		//大客户电子运单更改管理
		editConfig.setTitle("批量开单");
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI");
		OpenBatchCreateWaybillUI salesDeptEWaybillUI =  (OpenBatchCreateWaybillUI) editor.getComponent();
		salesDeptEWaybillUI.openUI();
		log.info("com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI" + editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(salesDeptEWaybillUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<OpenBatchCreateWaybillUI> getOpenUIType() {
		return OpenBatchCreateWaybillUI.class;
	}
}