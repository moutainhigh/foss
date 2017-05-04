package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;

/**
 * 查询打包装信息
 * @author 218371-foss-zhaoyanjun
 * @date:2014-10-16下午19:42
 */
public class QueryPackingAction  implements IButtonActionListener<WaybillEditUI>{

		IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
		
		// 主界面
		WaybillEditUI ui;

		@Override
		public void actionPerformed(ActionEvent e) {
			ui.showPackingYokeDialog();
			Common.setSaveAndSubmitFalse(ui);
		}
		

		@Override
		public void setInjectUI(WaybillEditUI ui) {
			this.ui = ui;
		}

	 
}
