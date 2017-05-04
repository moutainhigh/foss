package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 图片查询界面Enter键监控
 * @author WangQianJin
 * @date 2013-5-16 下午9:49:22
 */
public class EnterKeySearchPicture extends KeyAdapter {
	// 查询按钮
	private JButton btnQuery;
	
	// 窗口
	//private SearchPictureWaybillUI searchPictureWaybillUI;
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeySearchPicture(JButton btnQuery){
		this.btnQuery=btnQuery;
	}
	
	/**
	 * 构造函数
	 * EnterKeyForQueryPickup
	 * @author WangQianJin
	 * @date 2013-5-13 下午3:03:38
	 */
	public EnterKeySearchPicture(SearchPictureWaybillUI searchPictureWaybillUI){
//		this.searchPictureWaybillUI=searchPictureWaybillUI;
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
			/*if(searchPictureWaybillUI!=null){
				searchPictureWaybillUI.tableListenter();	
			}*/
		}
	}
}
