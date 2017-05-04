package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.jdesktop.swingx.JXTable;

/**
 * 提货网点按键输入回车执行类
 * @author WangQianJin
 * @date 2013-5-13 下午2:58:50
 */
public class EnterKeyForQueryPickup extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	// 显示的table
	private JXTable tbltable;
	// 窗口
	private QueryPickupStationDialog queryPickupStationDialog;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForQueryPickup(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeyForQueryPickup(JXTable tbltable,QueryPickupStationDialog queryPickupStationDialog){
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
