package com.deppon.foss.module.pickup.changing.client.ui.internal;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.controller.QueryInstallPanel;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;

/**
 * 安装费界面
 * @author MaBinliang
 *
 */
public class InstallAction extends AbstractButtonActionListener<WaybillRFCUI> {

	WaybillRFCUI ui;

	/**
	 * 430
	 */
	private static final int HEIGHT = 430;
	/**
	 * 1126
	 */
	private static final int WIDTH = 1126;

	/**
	 * 
	 * <p>
	 * (计算总费用)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
/*		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		if (bean.getCustomerPickupOrgCode() == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.queryOtherChargeAction.MsgBox.nullCustomerPickupOrgCode"));
		} else{
			showInstallDialog();
		}*/
		showInstallDialog();
	}

	/**
	 * 
	 * 计算计费重量
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午02:14:05
	 */
	private void showInstallDialog() {
		QueryInstallPanel dialog= new QueryInstallPanel(ui);
		dialog.setSize(WIDTH, HEIGHT);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}


	@Override
	public void setIInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
		
	}

}