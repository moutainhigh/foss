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
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.define.BZPartnersConstants;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersContextUtil;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;

import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpOpenWaybillEditUIAction extends
	AbstractOpenUIAction<ExpWaybillEditUI>  {
	private static final long serialVersionUID = 123L;

	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory
			.getLog(ExpOpenWaybillEditUIAction.class);

	// 国际化
		private static final II18n i18n = I18nManager.getI18n(ExpOpenWaybillEditUIAction.class);

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
	 * 营业部开单界面
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午10:51:59
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#openUIAction()
	 */
	public void openUIAction() {
		openUIActionAndReturn();
	}
	
	/**
	 * 
	 * 营业部开单界面
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午10:51:59
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#openUIAction()
	 */
	public ExpWaybillEditUI openUIActionAndReturn() {
		if(CommonContents.logToggle){
			log.info(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label")+"开始加载...");
		}
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
		//foss.gui.creating.buttonPanel.waybillSaleDepartment.label
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();

		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");
		log.info(application.getWorkbench().getEditors().size());
		if(CommonContents.logToggle){
			log.info(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label")+"加载完成.");
		}
		return waybillEditUI;
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<ExpWaybillEditUI> getOpenUIType() {
		return ExpWaybillEditUI.class;
	}
}
