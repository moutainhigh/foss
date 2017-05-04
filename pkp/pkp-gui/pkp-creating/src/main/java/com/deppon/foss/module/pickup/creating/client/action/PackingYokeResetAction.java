package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.creating.client.vo.PackingYokeVo;

/**
 * 打包装界面重置按钮
 * @author 218371-foss-zhaoyanjun
 * @date:2014-10-17上午11:06
 */

public class PackingYokeResetAction extends
		AbstractButtonActionListener<EnterPackingInfoDialog>{
	
	EnterPackingInfoDialog yoke;
	
	IBinder<PackingYokeVo> PackingYokeBinder;
	
	public void actionPerformed(ActionEvent e) {
		WaybillEditUI waybillEditUI = yoke.getWaybillEditUI();
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		Common.unsetWaybillPanelVoForPackingPack(bean,waybillEditUI);
		Common.unsetPackingPackFee(bean);
	}

	@Override
	public void setIInjectUI(EnterPackingInfoDialog yoke) {
		// TODO Auto-generated method stub
		this.yoke=yoke;
	}
	
}
