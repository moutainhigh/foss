package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpQueryCourierDialog;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;



public class ExpQueryCourierAction implements IButtonActionListener<ExpWaybillEditUI> {

	// 主界面
		ExpWaybillEditUI ui;
		//初始化运单服务对象
		IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
		@Override
		public void actionPerformed(ActionEvent e) {

			HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
			IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
			ExpWaybillPanelVo bean = waybillBinder.getBean();

			
			if(bean==null){
				return;
			}
			try {
				//弹出查询收货部门对话框
				ExpQueryCourierDialog dialog = new ExpQueryCourierDialog(bean.getExpressEmpCode());
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
				//获得员工信息
				EmployeeEntity entity = dialog.getEmployeeEntity();
				
				if(entity==null){
					return;
				}
				
				//设置快递员工号
				bean.setExpressEmpCode(entity.getEmpCode());
			} catch (WaybillValidateException w) {
				MsgBox.showInfo(w.getMessage());
			}
		}

		@Override
		public void setInjectUI(ExpWaybillEditUI ui) {
			this.ui = ui;
		}

}
