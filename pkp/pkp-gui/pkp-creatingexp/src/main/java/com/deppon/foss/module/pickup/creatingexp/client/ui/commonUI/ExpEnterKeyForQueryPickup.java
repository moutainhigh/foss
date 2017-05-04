package com.deppon.foss.module.pickup.creatingexp.client.ui.commonUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.jdesktop.swingx.JXTable;


public class ExpEnterKeyForQueryPickup  extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	// 显示的table
	private JXTable tbltable;
	// 窗口
	private ExpQueryPickupStationDialog queryPickupStationDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyForQueryPickup(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public ExpEnterKeyForQueryPickup(JXTable tbltable,ExpQueryPickupStationDialog queryPickupStationDialog){
		this.tbltable=tbltable;
		this.queryPickupStationDialog=queryPickupStationDialog;
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
			if(tbltable!=null){
				queryPickupStationDialog.tableListenerForEnter();
			}			
		}
	}
}