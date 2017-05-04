package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;


/**
 * 运单面板监控键盘按键
 * @author WangQianJin
 * @date 2014-6-14 下午2:58:50
 */
public class KeyForWaybillPanel extends KeyAdapter {
	
	//运单VO
	private WaybillPanelVo waybillPanelVo;	
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2014-6-14 下午2:58:50
	 */
	public KeyForWaybillPanel(WaybillPanelVo waybillPanelVo){
		this.waybillPanelVo=waybillPanelVo;		
	}
	
	/**
	 * 
	 * 按钮触发快捷键
	 * @author WangQianJin
	 * @date 2014-6-14 下午2:58:50
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//判断开始开单时间是否为空
		if(waybillPanelVo.getStartBillTime()==null){
			waybillPanelVo.setStartBillTime(new Date());
		}
	}
}
