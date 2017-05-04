/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryConsigneeAction  extends AbstractButtonActionListener<ExpWaybillEditUI> {

	ExpWaybillEditUI ui;

	/**
	 * 
	 * <p>
	 * 查询发货客户
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//设置收货客户信息
		ExpCommon.setQueryReceiveCustomer(ui);
	}

	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
