package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.vo.PackingYokeVo;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;

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
		WaybillRFCUI waybillRFCUI = yoke.getWaybillRFCUI();
		WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
		
		Common.unsetWaybillPanelVoForPackingPack(bean,yoke);
		Common.unsetPackingPackFee(bean);
	}

	@Override
	public void setIInjectUI(EnterPackingInfoDialog yoke) {
		// TODO Auto-generated method stub
		this.yoke=yoke;
	}
	
}
