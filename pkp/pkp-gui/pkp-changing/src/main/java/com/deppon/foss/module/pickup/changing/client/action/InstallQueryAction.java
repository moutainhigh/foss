package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.changing.client.ui.controller.QueryInstallPanel;
import com.deppon.foss.module.pickup.changing.client.ui.controller.QueryInstallPanel.ChangeInfoDetailTableModel;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;

public class InstallQueryAction extends
		AbstractButtonActionListener<QueryInstallPanel> {

	QueryInstallPanel ui;

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
		List<OtherChargeVo> newData = new ArrayList<OtherChargeVo>();
		for (OtherChargeVo vo : bakData) {
			if (vo.getChargeName().indexOf(chargeName) >= 0) {
				newData.add(vo);
			}
		}
		ui.setChangeDetail(newData, bakData);
	}

	@Override
	public void setIInjectUI(QueryInstallPanel ui) {
		// TODO Auto-generated method stub
		this.ui = ui;
	}

}
