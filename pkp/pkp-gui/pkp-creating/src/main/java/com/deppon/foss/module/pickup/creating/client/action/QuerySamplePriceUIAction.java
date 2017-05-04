package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.CalculateCostsDialog;
/**
 * 简单报价查询Action
 * @author Foss-105888-Zhangxingwang
 * @date 2015-5-19 21:14:21
 */
public class QuerySamplePriceUIAction implements IButtonActionListener<WaybillEditUI>{
	
	private static final int NUM_826 = 826;
	
	private static final int NUM_537 = 537;
	
	WaybillEditUI ui;
	@Override
	public void actionPerformed(ActionEvent e) {
		CalculateCostsDialog dialog = ui.getCalculateCostsDialog();
		
		/**
		 * 优化性能 CalculateCostsDialog初始化放到点击按钮事件中
		 * @author Foss-278328-hujinyang
		 * @date 2015-12-15 09:34:45
		 */
		dialog = new CalculateCostsDialog();
		IBinder<WaybillPanelVo> binder = dialog.getWaybillBinder();
		if(binder !=null && binder.getBean() !=null){
			BigDecimal insuranceAmount = ui.queryInsuranceAmount();
			WaybillPanelVo vo = binder.getBean();
			vo.setInsuranceAmount(insuranceAmount);
		}
		
		dialog.setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_826, NUM_537);
		ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
}