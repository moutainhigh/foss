package com.deppon.foss.module.pickup.changing.client.ui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 查询优惠信息Enter键
 * @author WangQianJin
 * @date 2013-5-16 下午9:13:52
 */
public class EnterKeyQueryOtherCharge extends KeyAdapter{
	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private QueryOtherChargePanel queryOtherChargePanel;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQueryOtherCharge(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQueryOtherCharge(QueryOtherChargePanel queryOtherChargePanel){
		this.queryOtherChargePanel=queryOtherChargePanel;
	}
	
	
	public EnterKeyQueryOtherCharge(QueryInstallPanel queryInstallPanel) {
		 this.queryOtherChargePanel=queryOtherChargePanel;
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
			if(queryOtherChargePanel!=null){
				queryOtherChargePanel.tableListenter();	
			}
		}
	}
}
