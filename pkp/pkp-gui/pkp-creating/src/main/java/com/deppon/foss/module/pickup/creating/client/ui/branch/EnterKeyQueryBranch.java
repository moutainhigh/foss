package com.deppon.foss.module.pickup.creating.client.ui.branch;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 查询网点Enter键
 * @author WangQianJin
 * @date 2013-5-16 下午9:03:03
 */
public class EnterKeyQueryBranch extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	// 窗口
	private PickupGoodsBranchDialog pickupGoodsBranchDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQueryBranch(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQueryBranch(PickupGoodsBranchDialog pickupGoodsBranchDialog){
		this.pickupGoodsBranchDialog=pickupGoodsBranchDialog;
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
			if(pickupGoodsBranchDialog!=null){
				pickupGoodsBranchDialog.tableEnterListener();	
			}
		}
	}
}
