/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.Container;
import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpCloseDialogAction  implements IButtonActionListener {

	private Container ui;

	/**
	 * <p>
	 * (按钮监听事件)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 下午1:49:14
	 * @param arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		ui.setVisible(false);
	}

	/**
	 * <p>
	 * (属性注入)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-19 上午11:38:30
	 * @param ui
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(Container ui) {
		this.ui = ui;
	}

}
