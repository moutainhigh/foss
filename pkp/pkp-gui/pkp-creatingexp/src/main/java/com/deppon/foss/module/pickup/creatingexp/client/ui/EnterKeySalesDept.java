package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 管理运单界面Enter键监控
 * @author WangQianJin
 * @date 2013-5-16 下午9:49:22
 */
public class EnterKeySalesDept extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private ExpReturnedGoodsWaybillUI expReturnedGoodsWaybillUI;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeySalesDept(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 */
	public EnterKeySalesDept(ExpReturnedGoodsWaybillUI expReturnedGoodsWaybillUI){
		this.expReturnedGoodsWaybillUI=expReturnedGoodsWaybillUI;
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
			if(expReturnedGoodsWaybillUI!=null){
				expReturnedGoodsWaybillUI.tableListenter();	
			}
		}
	}
}
