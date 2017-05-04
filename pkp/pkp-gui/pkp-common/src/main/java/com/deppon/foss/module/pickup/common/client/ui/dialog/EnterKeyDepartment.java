package com.deppon.foss.module.pickup.common.client.ui.dialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 营业部Enter键监控
 * @author WangQianJin
 * @date 2013-5-16 下午9:20:32
 */
public class EnterKeyDepartment extends KeyAdapter {
	
	// 查询按钮
	private JButton btnQuery;
	// 窗口
	private SalesDepartmentDialog salesDepartmentDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyDepartment(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyDepartment(SalesDepartmentDialog salesDepartmentDialog){
		this.salesDepartmentDialog=salesDepartmentDialog;
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
			if(salesDepartmentDialog!=null){
				salesDepartmentDialog.tableEnter();
			}			
		}
	}
	
}
