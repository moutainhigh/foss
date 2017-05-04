/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryBankAccountAction  implements IButtonActionListener<ExpWaybillEditUI> {

	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 主界面
	ExpWaybillEditUI ui;
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpQueryBankAccountAction.class);

	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		try
		{
			validate(bean);
			//设置银行信息
			ExpCommon.setBankInfo(bean,ui,waybillService);
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}
	
	/**
	 * 
	 * 验证是否有银行账号信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午05:36:20
	 */
	private void validate(WaybillPanelVo bean){
		if(bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.queryBankAccountAction.exception.nullDeliveryCustomerCode"));
		}
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}
	
}
