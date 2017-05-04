package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EnterKeyForQueryMobile extends KeyAdapter {
	// 窗口
	private QueryConsignerDialog queryConsignerDialog;
	private QueryConsigneeDialog queryConsigneeDialog;
	
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForQueryMobile(QueryConsignerDialog queryConsignerDialog){
		this.queryConsignerDialog=queryConsignerDialog;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForQueryMobile(QueryConsigneeDialog queryConsigneeDialog){
		this.queryConsigneeDialog=queryConsigneeDialog;
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
			if(queryConsignerDialog!=null){
				queryConsignerDialog.tableEnter();
			}	
			if(queryConsigneeDialog!=null){
				queryConsigneeDialog.tableEnter();
			}
		}
	}
}
