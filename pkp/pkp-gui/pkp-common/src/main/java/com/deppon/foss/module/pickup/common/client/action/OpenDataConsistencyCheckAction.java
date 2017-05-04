package com.deppon.foss.module.pickup.common.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;

import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.jpf.IPluginIdAware;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.common.client.ui.DataConsistencyCheckUI;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;

public class OpenDataConsistencyCheckAction extends 
        AbstractOpenUIAction<DataConsistencyCheckUI> implements IPluginIdAware,IPluginAware{

	private static final long serialVersionUID = -69483217198126843L;

	private IApplication application;
	
	private EditorConfig editConfig;
	
	private Plugin plugins;
	
	// 日志
	private static final Log log = LogFactory
				.getLog(OpenDataConsistencyCheckAction.class);

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
		RepositoryCenter.getInstance().register("application", application);
	}

	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle("数据一致性检查");
		editConfig.setPluginId("com.deppon.foss.module.pkp-common");
		String url="com.deppon.foss.module.pickup.common.client.ui.DataConsistencyCheckUI";
		//application.openEditorAndRetrun(this.editConfig,URL);
		IEditor editor=this.application.openEditorAndRetrun(this.editConfig,url);
		DataConsistencyCheckUI dataConsistencyCheckUI=(DataConsistencyCheckUI)editor.getComponent();
		dataConsistencyCheckUI.setPlugin(plugins);
		log.info("open UI :com.deppon.foss.module.pickup.common.client.ui.DataConsistencyCheckUI");
	    log.info(Integer.valueOf(this.application.getWorkbench().getEditors().size()));
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<DataConsistencyCheckUI> getOpenUIType() {
		return DataConsistencyCheckUI.class;
	}

	@Override
	public void setPluginId(String pluginId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		plugins=plugin;
	}

}
