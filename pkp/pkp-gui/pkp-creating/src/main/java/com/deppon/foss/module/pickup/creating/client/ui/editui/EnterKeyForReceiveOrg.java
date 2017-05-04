package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 收货部门输入框Enter键监控
 * @author WangQianJin
 * @date 2013-5-17 上午9:17:33
 */
public class EnterKeyForReceiveOrg extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForReceiveOrg(JButton btnQuery){
		this.btnQuery=btnQuery;
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
			if(btnQuery!=null){
				btnQuery.doClick();	
			}			
		}
	}
}
