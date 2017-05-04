/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;

import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryConsignerAction extends AbstractButtonActionListener<ExpWaybillEditUI> {

	ExpWaybillEditUI ui;
	
	//绑定对象
	IBinder<ExpWaybillPanelVo> waybillBinder;
	/**
	 * 
	 * <p>
	 * 查询发货客户
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//设置发货客户信息
		ExpCommon.setQueryDeliveryCustomer(ui);
		ExpCommon.setSaveAndSubmitFalse(ui);
		/**
		 * 新添加gyk
		 */
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
	    ui.consignerPanel.getTxtConsignerLinkMan().setEditable(false);
		bean.setPackageFee(BigDecimal.ZERO);
	}

	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {

		this.ui = ui;

	}

}
