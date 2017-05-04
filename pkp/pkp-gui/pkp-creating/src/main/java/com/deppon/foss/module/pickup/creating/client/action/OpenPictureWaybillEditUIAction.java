package com.deppon.foss.module.pickup.creating.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;
import com.deppon.foss.module.pickup.creating.client.listener.PictureJPanelExitListener;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 图片开单
 * 
 */
public class OpenPictureWaybillEditUIAction extends
		AbstractOpenUIAction<PictureWaybillEditUI> {

	private static final long serialVersionUID = 7870447805127313426L;

	private IApplication application;

	private EditorConfig editConfig;
	
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(OpenPictureWaybillEditUIAction.class);

	// 日志
	private static final Log log = LogFactory.getLog(OpenPictureWaybillEditUIAction.class);
	
	
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
		RepositoryCenter.getInstance().register("application", application);
	}

	@Override
	public void openUIAction() {
		openPictureUIAction();
	}

	/**
	 * 新增圖片开单界面
	 */
	public PictureWaybillEditUI openPictureUIAction(){
		if(CommonContents.logToggle){
			log.info(i18n.get("foss.gui.creating.newAddAction.waybillPicture.label")+"开始加载...");
		}
		editConfig = new EditorConfig();
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
		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI");
		log.info(application.getWorkbench().getEditors().size());
		if(CommonContents.logToggle){
			log.info(i18n.get("foss.gui.creating.newAddAction.waybillPicture.label")+"加载成功.");
		}
		return prictrueWaybillEditUI;
	}
	
	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<PictureWaybillEditUI> getOpenUIType() {
		return PictureWaybillEditUI.class;
	}

}
