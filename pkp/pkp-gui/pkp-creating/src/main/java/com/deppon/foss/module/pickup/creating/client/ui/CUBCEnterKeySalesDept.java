package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class CUBCEnterKeySalesDept extends KeyAdapter{
		// 查询按钮
		private JButton btnQuery;
		
		// 窗口
		private CUBCWaybillManagerUI cubcWaybillManagerUI;

		/**
		 * 构造函数
		 */
		public CUBCEnterKeySalesDept(JButton btnQuery){
			this.btnQuery=btnQuery;
		}
		
		/**
		 * 构造函数
		 */
		public CUBCEnterKeySalesDept(CUBCWaybillManagerUI cubcWaybillManagerUI){
			this.cubcWaybillManagerUI=cubcWaybillManagerUI;
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
				if(cubcWaybillManagerUI!=null){
					cubcWaybillManagerUI.tableListenter();	
				}
			}
		}
}
