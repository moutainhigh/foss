/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.InnerPickupSalesDepartmentDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryConsigneeDeptAction  implements IButtonActionListener<ExpWaybillEditUI> {

	// 主界面
	ExpWaybillEditUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		try {
			InnerPickupSalesDepartmentDialog dialog = new InnerPickupSalesDepartmentDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

			OrgAdministrativeInfoEntity entity = dialog.getOrgAdministrativeInfoEntity();
			if (entity != null) {
				//收货部门名称
				bean.setReceiveCustomerContact(entity.getName());
				//部门CODE
				bean.setReceiveCustomerContactId(entity.getCode());
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
