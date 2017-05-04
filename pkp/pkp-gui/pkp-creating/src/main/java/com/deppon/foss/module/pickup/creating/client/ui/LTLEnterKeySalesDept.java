package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * Enter键监控
 * @author 305082
 *
 */
public class LTLEnterKeySalesDept extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private LTLEWaybillManageUI salesDeptWaybillUI;

	/**
	 * 构造函数
	 */
	public LTLEnterKeySalesDept(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 */
	public LTLEnterKeySalesDept(LTLEWaybillManageUI salesDeptWaybillUI){
		this.salesDeptWaybillUI=salesDeptWaybillUI;
	}

	/**
	 * 
	 * 按钮触发快捷键
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k=e.getKeyCode();
		if(k == KeyEvent.VK_ENTER){
			if(btnQuery!=null){
				btnQuery.doClick();	
			}	
			if(salesDeptWaybillUI!=null){
				salesDeptWaybillUI.tableListenter();	
			}
		}
	}
}

