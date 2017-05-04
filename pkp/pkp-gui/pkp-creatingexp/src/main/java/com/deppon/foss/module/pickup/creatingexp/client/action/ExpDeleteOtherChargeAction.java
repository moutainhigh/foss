/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpDeleteOtherChargeAction extends
		AbstractButtonActionListener<ExpWaybillEditUI> {

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	ExpWaybillEditUI ui;

	IBinder<ExpWaybillPanelVo> waybillBinder;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager
			.getI18n(ExpDeleteOtherChargeAction.class);

	/**
	 * 
	 * <p>
	 * 删除其他费用
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		deleteTableRow(bean);
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 删除选中的其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午04:48:28
	 */
	private void deleteTableRow(WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		int i = table.getSelectedRow();
		if (i >= 0) {
			WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
			List<OtherChargeVo> data = model.getData();

			// 其他费用合计
			OtherChargeVo vo = data.get(i);
			BigDecimal money = new BigDecimal(vo.getMoney());

			// 是否可以删除
			Boolean isDelete = vo.getIsDelete();

			if (isDelete == null) {
				MsgBox.showInfo(vo.getChargeName()
						+ i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notSetIsDelete"));
				return;
			}
			/**
			 * 月结
			 */
			Boolean chargeMode = bean.getChargeMode();
			if (chargeMode == null) {
				// 没有填写的情况下 作为非月结处理
				chargeMode = Boolean.FALSE;
			}
			/**
			 * 燃油附加费不能删除, 月结客户不可以删除综合服务费
			 */
			if (WaybillConstants.OTHERFEE_ZHXX.equals(vo.getCode())
					&& chargeMode) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.month"));
				return;
			}

			if (!isDelete) {
				MsgBox.showInfo(vo.getChargeName()
						+ i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notBeDelete"));
				return;
			}

			BigDecimal otherFee = bean.getOtherFee().subtract(money);
			bean.setOtherFee(otherFee);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());

			data.remove(i);
			ui.incrementPanel.setChangeDetail(data);

		}

	}

	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
