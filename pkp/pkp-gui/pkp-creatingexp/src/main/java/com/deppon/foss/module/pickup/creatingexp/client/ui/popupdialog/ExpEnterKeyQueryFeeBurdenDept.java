/*
 * 费用承担部门选择弹出框回车事件
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class ExpEnterKeyQueryFeeBurdenDept extends KeyAdapter {

	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private ExpInnerPickupFeeBurdenDeptDialog innerPickupFeeBurdenDeptDialog;
	
	// 收货部门窗口
	//private ExpSalesDepartmentDialog salesDepartmentDialog;
	
	public ExpEnterKeyQueryFeeBurdenDept(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	public ExpEnterKeyQueryFeeBurdenDept(ExpInnerPickupFeeBurdenDeptDialog innerPickupFeeBurdenDeptDialog){
		this.innerPickupFeeBurdenDeptDialog=innerPickupFeeBurdenDeptDialog;
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
			if(innerPickupFeeBurdenDeptDialog!=null){
				innerPickupFeeBurdenDeptDialog.tableListenter();
			}
			//if(salesDepartmentDialog!=null){
			//salesDepartmentDialog.tableListenter();
			//}
		}
	}

	public ExpInnerPickupFeeBurdenDeptDialog getInnerPickupFeeBurdenDeptDialog() {
		return innerPickupFeeBurdenDeptDialog;
	}



	public void setInnerPickupFeeBurdenDeptDialog(
			ExpInnerPickupFeeBurdenDeptDialog innerPickupFeeBurdenDeptDialog) {
		this.innerPickupFeeBurdenDeptDialog = innerPickupFeeBurdenDeptDialog;
	}
}
