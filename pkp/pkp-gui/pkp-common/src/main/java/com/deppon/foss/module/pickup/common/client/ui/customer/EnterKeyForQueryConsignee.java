package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * Enter键查询收货客户信息
 * @author WangQianJin
 * @date 2013-5-16 下午8:31:57
 */
public class EnterKeyForQueryConsignee extends KeyAdapter  {
		// 查询按钮
		private JButton btnQuery;
		// 窗口
		private QueryMemberDialog queryMemberDialog;
		
		/**
		 * 构造函数
		 * EnterKeyForQueryPickup
		 * @author WangQianJin
		 * @date 2013-5-13 下午3:03:38
		 */
		public EnterKeyForQueryConsignee(JButton btnQuery){
			this.btnQuery=btnQuery;
		}
		
		/**
		 * 构造函数
		 * EnterKeyForQueryPickup
		 * @author WangQianJin
		 * @date 2013-5-13 下午3:03:38
		 */
		public EnterKeyForQueryConsignee(QueryMemberDialog queryMemberDialog){
			this.queryMemberDialog=queryMemberDialog;
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
				if(queryMemberDialog!=null){
					queryMemberDialog.tableEnter();
				}			
			}
		}
}
