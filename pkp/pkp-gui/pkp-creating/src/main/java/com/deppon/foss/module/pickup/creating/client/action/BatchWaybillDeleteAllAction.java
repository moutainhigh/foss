package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCreateWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ILabeledGoodHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting;
import com.deppon.foss.util.CollectionUtils;


/**
 * 批量提交
 * dp-foss-dongjialing
 * @author 225131
 *
 */
public class BatchWaybillDeleteAllAction implements IButtonActionListener<OpenBatchCreateWaybillUI>{
	//主界面
	OpenBatchCreateWaybillUI ui;
	IWaybillPendingHessianRemoting waybillPendingHessianRemoting;
	ILabeledGoodHessianRemoting labeledGoodHessianRemoting;
	// 获得远程HessianRemoting接口
	IWaybillHessianRemoting waybillRemotingService;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JTable table = ui.getTable();
		BatchCreateWaybillTableModel model = (BatchCreateWaybillTableModel) table.getModel();
		int[] indexs = table.getSelectedRows();
		if(indexs.length> NumberConstants.NUMBER_1000) {
			MsgBox.showInfo("运单删除不能超过1000条");
		}
		List<String> waybillNos = new ArrayList<String>();
		if(indexs.length<1) {
			MsgBox.showInfo("没有选择操作行");
			return;
		} else {
			//ui閲岀殑琛岃浆鍖栦负model閲岀殑琛�
			for (int i = 0; i < indexs.length; i++) {
				int index = indexs[i];
				if (index < 0) {
					return;
				}
				int row = table.convertRowIndexToModel(index);
				String waybillStaus = (String) model.getValueAt(row, NumberConstants.NUMBER_25);
				String waybillNo = (String) model.getValueAt(row, NumberConstants.NUMBER_4);
				if(!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillStaus)) {
					waybillNos.add(waybillNo);
				} 
			}
		}
		
		if(CollectionUtils.isNotEmpty(waybillNos)) {
			//删运单信息
			waybillPendingHessianRemoting =  DefaultRemoteServiceFactory.getService(IWaybillPendingHessianRemoting.class);
		    waybillPendingHessianRemoting.deleteByWaybillNos(waybillNos);
		   
		    // 删打木架信息
		    waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		    waybillRemotingService.deleteWoodByWaybillNos(waybillNos);
		    /**
		     * 解决批量开单无流水号信息bug
		     * dp-foss-dongjialing
		     * 225131
		     */
		    WaybillEntity entity = null;
			for (String waybillNo : waybillNos) {
				entity=waybillRemotingService.queryWaybillBasicByNo(waybillNo);
				if(entity != null && WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(entity.getPendingType())) {
					waybillNos.remove(waybillNo);
				}
			}
			 //删标签信息
		    labeledGoodHessianRemoting =  DefaultRemoteServiceFactory.getService(ILabeledGoodHessianRemoting.class);
		    labeledGoodHessianRemoting.deleteLabByWaybillNos(waybillNos);
			MsgBox.showInfo("删除成功");
		}else{
			MsgBox.showInfo("请选择要删的数据");
		}
	}
	@Override
	public void setInjectUI(OpenBatchCreateWaybillUI ui) {
		// TODO Auto-generated method stub
		this.ui = ui;
	}
}
