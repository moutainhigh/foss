package com.deppon.foss.module.pickup.creating.client.action;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI;
/**
 * 电子运单导入重量体积UI的action
 * @author 305082
 */
public class LTLEWaybillImportWeightUIAction extends AbstractOpenUIAction<LTLEWaybillImportWeightUI>  {


	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7513459318603620373L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory.getLog(LTLEWaybillImportWeightUIAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillImportWeightUIAction.class);

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		//电子运单导入重量体积
		editConfig.setTitle(i18n.get("foss.gui.creating.LTLEWaybillImportWeightUI.title"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI");
		LTLEWaybillImportWeightUI salesDeptEWaybillUI = (LTLEWaybillImportWeightUI) editor.getComponent();
		salesDeptEWaybillUI.openUI();
		log.info("com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI" + editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(salesDeptEWaybillUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<LTLEWaybillImportWeightUI> getOpenUIType() {
		return LTLEWaybillImportWeightUI.class;
	}
}