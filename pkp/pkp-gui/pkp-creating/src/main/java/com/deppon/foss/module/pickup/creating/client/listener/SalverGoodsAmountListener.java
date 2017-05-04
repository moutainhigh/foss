package com.deppon.foss.module.pickup.creating.client.listener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterYokeInfoDialog;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

public final class SalverGoodsAmountListener extends FocusAdapter{

	private WaybillEditUI ui;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(EnterYokeInfoDialog.class); 
	
	public SalverGoodsAmountListener(WaybillEditUI ui){
		this.ui = ui;
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		EnterYokeInfoDialog dialog = this.ui.getDialog();
		JTextFieldValidate txtSalverGoodsPieces = dialog.getTxtSalverGoodsPieces();
		JTextFieldValidate txtSalverGoodsAmount = dialog.getTxtSalverGoodsAmount();
		
		try {
			WaybillPanelVo bean = getWaybillPanelVo(this.ui);
			if(bean.getGoodsVolumeTotal() == null){
				bean.setGoodsVolumeTotal(BigDecimal.ZERO);
			}
			if(bean.getGoodsWeightTotal() == null){
				bean.setGoodsWeightTotal(BigDecimal.ZERO);
			}
			if (StringUtils.isNotEmpty(txtSalverGoodsPieces.getText())) {
				if (StringUtils.isNotEmpty(txtSalverGoodsAmount
						.getText())) {
					if (!NumberValidate.checkIntOrFloat(txtSalverGoodsAmount.getText())) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						txtSalverGoodsAmount.setText(null);
					} else {
						if (!dialog.validSalverMinFee(
								txtSalverGoodsAmount.getText(),
								txtSalverGoodsPieces.getText())) {
							//确认录入的时候会提醒，这个会造成重复提醒
//							MsgBox.showInfo(i18n
//									.get("foss.gui.creating.woodYokeEnterAction.exception.minSalverGoodsAmount"));
//							txtSalverGoodsAmount.requestFocus();
						}
					}
				}
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
			txtSalverGoodsAmount.setText(null);
		}
	}
/**
 * 
 * 获取页面绑定的bean
 * @param ui
 * @return
 */
	private WaybillPanelVo getWaybillPanelVo(WaybillEditUI ui){
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		return bean;
	}
}
