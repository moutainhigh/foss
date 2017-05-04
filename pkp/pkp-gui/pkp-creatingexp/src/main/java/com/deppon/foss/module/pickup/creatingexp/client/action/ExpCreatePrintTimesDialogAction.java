/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.print.ExpBarcodePrintDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.print.ExpPrintTimesDialog;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpCreatePrintTimesDialogAction implements IButtonActionListener<ExpWaybillEditUI> {

	ExpWaybillEditUI ui;

	// 通过工厂类获得运单服务类
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ExpCreatePrintTimesDialogAction.class);

	/**
	 * <p>
	 * ( 触发 打印预览 或者 运单打印对话框)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-17 下午9:30:23
	 * @param arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		//打印预览
		if (i18n.get("foss.gui.creating.buttonPanel.preview.label").equals(event.getActionCommand())) {
			new ExpPrintTimesDialog(false, ui);
		//运单打印
		} else if (i18n.get("foss.gui.creating.buttonPanel.print.label").equals(event.getActionCommand())) {
			new ExpPrintTimesDialog(true, ui);
		//打印标签
		} else if (i18n.get("foss.gui.creating.buttonPanel.printLabel.label").equals(event.getActionCommand())) {
			WaybillPanelVo waybillPanelVo = ui.getBindersMap().get("waybillBinder").getBean();
			
			//是否为经济快递的运单号
			if(!CommonUtils.directDetermineIsExpressByProductCode(waybillPanelVo.getProductCode().getCode())){
				MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expPrintSignAction.MsgBox.notExpressProduct"));
				return;
			}
			/**
			 * 获取运单状态
			 */
			String waybillStatus = waybillPanelVo.getWaybillstatus();
			if (waybillPanelVo.getWaybillNo() != null&& !"".equals(waybillPanelVo.getWaybillNo())) {
				WaybillPendingDto dto = waybillService.queryPendingWaybill(waybillPanelVo.getWaybillNo());
				if (waybillStatus == null || "".equals(waybillStatus)) {
					if (dto != null && dto.getWaybillPending() != null) {
						waybillStatus = dto.getWaybillPending().getPendingType();
					}
				}
			}
			new ExpBarcodePrintDialog(waybillPanelVo.getWaybillNo(), waybillStatus);
		}
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}