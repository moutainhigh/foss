package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 集中开单Enter键监控
 * @author WangQianJin
 * @date 2013-5-16 下午9:30:17
 */
public class EnterKeyQuerySalesDepartment extends KeyAdapter {

	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private InnerPickupSalesDepartmentDialog innerPickupSalesDepartmentDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQuerySalesDepartment(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQuerySalesDepartment(InnerPickupSalesDepartmentDialog innerPickupSalesDepartmentDialog){
		this.innerPickupSalesDepartmentDialog=innerPickupSalesDepartmentDialog;
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
			if(innerPickupSalesDepartmentDialog!=null){
				innerPickupSalesDepartmentDialog.tableListenter();
			}
		}
	}
}
