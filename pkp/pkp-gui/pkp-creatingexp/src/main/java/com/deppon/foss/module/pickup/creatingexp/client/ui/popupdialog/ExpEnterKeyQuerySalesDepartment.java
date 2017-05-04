/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;


/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpEnterKeyQuerySalesDepartment extends KeyAdapter {

	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	private ExpInnerPickupSalesDepartmentDialog innerPickupSalesDepartmentDialog;
	
	// 收货部门窗口
	private ExpSalesDepartmentDialog salesDepartmentDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyQuerySalesDepartment(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyQuerySalesDepartment(ExpInnerPickupSalesDepartmentDialog innerPickupSalesDepartmentDialog){
		this.innerPickupSalesDepartmentDialog=innerPickupSalesDepartmentDialog;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyQuerySalesDepartment(ExpSalesDepartmentDialog salesDepartmentDialog){
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
			if(innerPickupSalesDepartmentDialog!=null){
				innerPickupSalesDepartmentDialog.tableListenter();
			}
			if(salesDepartmentDialog!=null){
				salesDepartmentDialog.tableListenter();
			}
		}
	}
}
