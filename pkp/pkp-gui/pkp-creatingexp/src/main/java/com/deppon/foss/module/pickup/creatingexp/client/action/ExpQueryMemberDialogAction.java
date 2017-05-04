/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryMemberDialogAction implements IButtonActionListener<ExpWaybillEditUI>{
    //主界面
    ExpWaybillEditUI ui;
    IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
    
    /** 
     * 打开“查找会员界面”以及相关赋值处理
     * @author 026123-foss-lifengteng
     * @date 2012-10-20 下午5:48:43
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		//设置发货客户信息
		ExpCommon.setQueryDeliveryCustomer(ui);
	}

	/**
	 * 
	 * 注入UI界面
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午10:30:18
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
    @Override
    public void setInjectUI(ExpWaybillEditUI ui) {
    	this.ui = ui;
    }

}
