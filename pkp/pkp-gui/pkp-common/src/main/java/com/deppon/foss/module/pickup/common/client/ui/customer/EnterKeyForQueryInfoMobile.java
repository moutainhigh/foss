package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EnterKeyForQueryInfoMobile extends KeyAdapter {
	// 窗口
	private QueryConsigneeInfoDialog queryConsigneeInfoDialog;
	
	
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForQueryInfoMobile(QueryConsigneeInfoDialog queryConsigneeInfoDialog){
		this.queryConsigneeInfoDialog=queryConsigneeInfoDialog;
	}
	
	/**
	 * 
	 * 按钮触发快捷键
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k=e.getKeyCode();
		if(k == KeyEvent.VK_ENTER){					
			if(queryConsigneeInfoDialog!=null){
				queryConsigneeInfoDialog.tableEnter();
			}
		}
	}
}
