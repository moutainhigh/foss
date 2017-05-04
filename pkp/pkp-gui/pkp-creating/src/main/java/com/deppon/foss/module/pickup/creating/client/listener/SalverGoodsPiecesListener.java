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

/**
 * 
 * 打木架监听器  
 * @author 
 *
 */
public class SalverGoodsPiecesListener extends FocusAdapter{
	
	private WaybillEditUI ui;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(EnterYokeInfoDialog.class); 
	
	public SalverGoodsPiecesListener(WaybillEditUI ui){
		this.ui = ui;
	}

	@Override
	public void focusLost(FocusEvent e) {
		EnterYokeInfoDialog dialog = this.ui.getDialog();
		JTextFieldValidate txtSalverGoodsPieces = dialog.getTxtSalverGoodsPieces();
		String curSalverGoodsPieces = dialog.getCurSalverGoodsPieces();
		JTextFieldValidate txtSalverGoodsAmount = dialog.getTxtSalverGoodsAmount();
		JTextFieldValidate txtSalverRequire = dialog.getTxtSalverRequire();
		try {
			WaybillPanelVo bean = getWaybillPanelVo(this.ui);
			if(bean.getGoodsVolumeTotal() == null){
				bean.setGoodsVolumeTotal(BigDecimal.ZERO);
			}
			if(bean.getGoodsWeightTotal() == null){
				bean.setGoodsWeightTotal(BigDecimal.ZERO);
			}
			if (StringUtils.isNotEmpty(txtSalverGoodsPieces.getText())) {
				if (!NumberValidate
						.checkIsAllNumber(txtSalverGoodsPieces
								.getText())) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
					txtSalverGoodsPieces.setText(null);
				} else {
					if(!curSalverGoodsPieces.equals(txtSalverGoodsPieces.getText())){ //防止不做修改但是焦点离开导致费用重新计算问题
						if(txtSalverGoodsPieces.getText().equals("0")){
							txtSalverGoodsAmount.setText("0");
						}else{
							BigDecimal salverGoodsAmount = dialog.calculateSalverFee(txtSalverGoodsPieces
									.getText());
							txtSalverGoodsAmount.setText(salverGoodsAmount
									.toString());
						}
						curSalverGoodsPieces = txtSalverGoodsPieces.getText();
					}
				}
			} else {
				txtSalverGoodsPieces.setText(null);
				txtSalverRequire.setText(null);
				txtSalverGoodsAmount.setText(null);
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
			txtSalverGoodsPieces.setText(null);
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
