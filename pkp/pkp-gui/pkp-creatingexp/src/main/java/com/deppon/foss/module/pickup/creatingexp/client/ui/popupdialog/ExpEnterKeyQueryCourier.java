package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class ExpEnterKeyQueryCourier extends KeyAdapter {

	// 查询按钮
	private JButton btnQuery;
	
	// 查询快递员窗口
	private ExpQueryCourierDialog expQueryCourierDialog;
	
	/**
	 * 构造函数
	 * ExpEnterKeyQueryCourier
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyQueryCourier(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyQueryCourier(ExpQueryCourierDialog expQueryCourierDialog){
		this.expQueryCourierDialog=expQueryCourierDialog;
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
			if(expQueryCourierDialog!=null){
				expQueryCourierDialog.tableListenter();
			}
		}
	}
}

