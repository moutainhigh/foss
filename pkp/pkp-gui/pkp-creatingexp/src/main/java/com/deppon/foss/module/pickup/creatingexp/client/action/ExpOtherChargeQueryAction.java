/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.customer.ExpQueryOtherChargePanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.customer.ExpQueryOtherChargePanel.ChangeInfoDetailTableModel;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpOtherChargeQueryAction extends
		AbstractButtonActionListener<ExpQueryOtherChargePanel> {

	ExpQueryOtherChargePanel ui;

	/**
	 * 
	 * 根据费用名称查询其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午05:47:04
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		queryOtherCharge();
		// 默认选中查询结果的第一行
		if (ui.getTable() != null && ui.getTable().getRowCount() > 0) {
			ui.getTable().requestFocus();
			ui.getTable().setRowSelectionAllowed(true);
			ui.getTable().setRowSelectionInterval(0, 0);
		}
	}

	/**
	 * 
	 * 根据名称查询费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午07:37:35
	 */
	private void queryOtherCharge() {
		String chargeName = ui.getTxtPrivilegeName().getText();
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) ui
				.getTable().getModel();
		List<OtherChargeVo> bakData = model.getBakList();
		if(bakData !=null){
			List<OtherChargeVo> newData = new ArrayList<OtherChargeVo>();
			for (OtherChargeVo vo : bakData) {
				if (vo.getChargeName().indexOf(chargeName) >= 0) {
					newData.add(vo);
				}
			}
			ui.setChangeDetail(newData, bakData);
		}
	}

	@Override
	public void setIInjectUI(ExpQueryOtherChargePanel ui) {

		this.ui = ui;

	}

}
