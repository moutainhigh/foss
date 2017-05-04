/**
* 
* 获取内部带货费用承担部门
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
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpInnerPickupFeeBurdenDeptDialog;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;


public class ExpQueryFeeBurdenDeptAction implements IButtonActionListener<ExpWaybillEditUI>{
    //主界面
    ExpWaybillEditUI ui;
    IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
    
    public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();

		try {
			// 创建弹出窗口
			ExpInnerPickupFeeBurdenDeptDialog dialog = new ExpInnerPickupFeeBurdenDeptDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			// 获得弹出窗口选择的组织
			OrgAdministrativeInfoEntity entity = dialog.getOrgAdministrativeInfoEntity();
			if (entity != null){
				//获得费用承担部门编码
				bean.setInnerPickupFeeBurdenDept(entity.getCode());
				
				//强制在TxtConsignerLinkMan中显示内部带货费用承担部门
				ui.consignerPanel.getTxtConsignerLinkMan().setText(entity.getName());
				
				//发货部门名称
				bean.setDeliveryCustomerContact(entity.getName());
				//部门CODE
				bean.setDeliveryCustomerContactId(entity.getCode());
				
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}


	/**
	 * 
	 * 注入UI界面
	 */
    @Override
    public void setInjectUI(ExpWaybillEditUI ui) {
    	this.ui = ui;
    }

}
