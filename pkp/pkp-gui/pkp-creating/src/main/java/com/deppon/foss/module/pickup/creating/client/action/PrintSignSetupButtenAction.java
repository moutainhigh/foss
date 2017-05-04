package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI;
import com.deppon.foss.print.labelprint.gui.LblPrtSetupWindow;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

@SuppressWarnings("unused")
public class PrintSignSetupButtenAction implements IButtonActionListener<OpenBatchCreateWaybillUI> {
	
	// 日志
	private static final Log LOG = LogFactory.getLog(PrintSignSetupAction.class);
		
	private OpenBatchCreateWaybillUI ui;
	 
	/**
	 * 标签设置
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			PropertiesUtil.initProperties();
			LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
			setupwindow.openWindow();
		}catch (Exception exp) {
			LOG.error("打印设置异常:"+exp.toString(),exp);
		}
	}

 
	@Override
	public void setInjectUI(OpenBatchCreateWaybillUI ui) {
		this.ui = ui;
	}

}