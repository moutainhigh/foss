package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 收款人信息Enter键监控
 * @author WangQianJin
 * @date 2013-5-18 上午10:08:39
 */
public class EnterKeyForBankAccount extends KeyAdapter {
	// 窗口
	private BankAccountDialog bankAccountDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForBankAccount(BankAccountDialog bankAccountDialog){
		this.bankAccountDialog=bankAccountDialog;
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
			if(bankAccountDialog!=null){
				bankAccountDialog.tableEnter();
			}			
		}
	}
}
