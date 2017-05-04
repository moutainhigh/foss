/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.customer.ExpQueryOtherChargePanel;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpQueryOtherChargeAction extends
		AbstractButtonActionListener<ExpWaybillEditUI> {

	ExpWaybillEditUI ui;

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(ExpQueryOtherChargeAction.class);

	/**
	 * 400
	 */
	private static final int HEIGHT = 240;
	/**
	 * 800
	 */
	private static final int WIDTH = 800;

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
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		if (bean.getCustomerPickupOrgCode() == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.queryOtherChargeAction.MsgBox.nullCustomerPickupOrgCode"));
		} else {
			showOtherChargeDialog();
		}
	}

	/**
	 * 
	 * 计算计费重量
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午02:14:05
	 */
	private void showOtherChargeDialog() {
		ExpQueryOtherChargePanel dialog = new ExpQueryOtherChargePanel(ui);
		dialog.setSize(WIDTH, HEIGHT);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {

		this.ui = ui;

	}

}
