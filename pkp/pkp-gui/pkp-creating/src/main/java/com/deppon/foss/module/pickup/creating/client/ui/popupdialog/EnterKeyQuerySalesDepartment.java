package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

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
	
	// 收货部门窗口
	private SalesDepartmentDialog salesDepartmentDialog;
	
	// 收货部门窗口
	private ReceiveOrgDialog receiveOrgDialog;
	
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
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyQuerySalesDepartment(SalesDepartmentDialog salesDepartmentDialog){
		this.salesDepartmentDialog=salesDepartmentDialog;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-07-17 下午3:03:38
	 */
	public EnterKeyQuerySalesDepartment(ReceiveOrgDialog receiveOrgDialog){
		this.receiveOrgDialog=receiveOrgDialog;
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
			if(receiveOrgDialog!=null){
				receiveOrgDialog.tableListenter();
			}
		}
	}
}
