package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog.BranchDataModel;

/**
 * 查询网点查询关键字action
 * @author 097972-foss-dengtingting
 * @date 2013-3-28 下午5:48:53
 */
public class QueryKeyAction implements IButtonActionListener<PickupGoodsBranchDialog>{

		// 主界面
		PickupGoodsBranchDialog ui;
		
		/**
		 * 国际化
		 */
		int index=0;
		int rowIndex=0;
		private static final II18n i18n = I18nManager.getI18n(CalculateAction.class);
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//判断选中的派送还是自提
		if(ui.getTabbedPane().getSelectedIndex()==0){
			deliverySetterText();
		}else{
			pickupSetterText();
		}
		
	}
	
	//派送区域
	private void deliverySetterText(){
		BranchQueryVo queryVo = ui.getBindersMap().get("branchQuery").getBean();
//		int index = queryVo.getIndex();
//		int rowIndex = queryVo.getRowIndex();
		StringBuffer buffer = new StringBuffer();
		String font = "<font color='red'>";
		String fontEnd ="</font>";
		String htmlbody ="<html><body>";
		String htmlbodyEnd ="</body></html>";
		BranchDataModel tableModel = (BranchDataModel)ui.getTable().getModel();
		List<BranchQueryVo> vo = tableModel.getData();
		StringBuffer findString = new StringBuffer();
		boolean flg = false;
		if (vo==null) {
			return;
		}else if (StringUtils.isEmpty(queryVo.getKey())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.QueryKeyAction.keyInput"));
			return;
		}
		for (BranchQueryVo branchQueryVo : vo) {
			if (StringUtils.isNotEmpty(branchQueryVo.getDeliveryAreaDesc())) {
				findString.append(branchQueryVo.getDeliveryAreaDesc());
			}
		}
		flg = findString.toString().contains(queryVo.getKey());
		if (!flg) {
			MsgBox.showError(i18n.get("foss.gui.creating.QueryKeyAction.notFoundKey"));
			return;
		}
		if (StringUtils.isNotEmpty(queryVo.getKey())&&CollectionUtils.isNotEmpty(vo)) {
			//如果该条内容为空则跳过本次循环
			for (int i = rowIndex; i < vo.size(); i++) {
				if (StringUtils.isEmpty(vo.get(i).getDeliveryAreaDesc())) {
					if(i==vo.size()-1){
						index=0;
						rowIndex=0;
					}
					continue;
				}
				index = vo.get(i).getDeliveryAreaDesc().indexOf(queryVo.getKey(),index);
				//如果没有找到 则跳过本次循环
				if (index == -1) {
					rowIndex=rowIndex+1;
					if(i==vo.size()-1){
						index=0;
						rowIndex=0;
					}
					continue;
				}	
				//设置下次要搜素 行号
				//rowIndex = i;
				//如果循环中找到 则设置字体颜色 
				buffer.append(vo.get(i).getDeliveryAreaDesc());
				int keyLeng  = queryVo.getKey().length();
				buffer.insert(index, font);
				buffer.insert(index+keyLeng+font.length(),fontEnd);	
				buffer.insert(0, htmlbody);
				buffer.append(htmlbodyEnd);
				
				
				
				
				//设置下次要搜索的 index 
				index = index+1;
				
					
				//设置让鼠标选中 表格中的这一行
				ui.getTable().setRowSelectionInterval(i, i);
				int row = ui.getTable().getSelectedRow();
				int modelRow =  ui.getTable().convertRowIndexToModel(row);
				BranchQueryVo selectVo = ((BranchDataModel) ui.getTable().getModel()).getData().get(modelRow);
				QueryBranchByNameAction.getRowDate(queryVo, selectVo,i);
				
				queryVo.setRowIndex(rowIndex);
				queryVo.setIndex(index);
				ui.getTextAreaDelivery().setText(buffer.toString());
				//zxy 20131230 DEFECT-945 start 新增:此句能定位滚动条到搜索位置
				ui.getTextAreaDelivery().select(index,index+1);
				//zxy 20131230 DEFECT-945 end 新增:此句能定位滚动条到搜索位置
				break;
			}
			 
			
			
		}
	}
	
	//自提区域
	private void pickupSetterText(){
		BranchQueryVo queryVo = ui.getBindersMap().get("branchQuery").getBean();
//		int index = queryVo.getIndex();
//		int rowIndex = queryVo.getRowIndex();
		StringBuffer buffer = new StringBuffer();
		String font = "<font color='red'>";
		String fontEnd ="</font>";
		String htmlbody ="<html><body>";
		String htmlbodyEnd ="</body></html>";
		BranchDataModel tableModel = (BranchDataModel)ui.getTable().getModel();
		List<BranchQueryVo> vo = tableModel.getData();
		StringBuffer findString = new StringBuffer();
		boolean flg = false;
		if (vo==null) {
			return;
		}else if (StringUtils.isEmpty(queryVo.getKey())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.QueryKeyAction.keyInput"));
			return;
		}
		for (BranchQueryVo branchQueryVo : vo) {
			if (StringUtils.isNotEmpty(branchQueryVo.getPickupAreaDesc())) {
				findString.append(branchQueryVo.getPickupAreaDesc());
			}
		}
		flg = findString.toString().contains(queryVo.getKey());
		if (!flg) {
			MsgBox.showError(i18n.get("foss.gui.creating.QueryKeyAction.notFoundKey"));
			return;
		}
		if (StringUtils.isNotEmpty(queryVo.getKey())&&CollectionUtils.isNotEmpty(vo)) {
			//如果该条内容为空则跳过本次循环
			for (int i = rowIndex; i < vo.size(); i++) {
				if (StringUtils.isEmpty(vo.get(i).getPickupAreaDesc())) {
					if(i==vo.size()-1){
						index=0;
						rowIndex=0;
					}
					continue;
				}
				index = vo.get(i).getPickupAreaDesc().indexOf(queryVo.getKey(),index);
				//如果没有找到 则跳过本次循环
				if (index == -1) {
					rowIndex=rowIndex+1;
					if(i==vo.size()-1){
						index=0;
						rowIndex=0;
					}
					continue;
				}	
				//设置下次要搜素 行号
				//rowIndex = i;
				//如果循环中找到 则设置字体颜色 
				buffer.append(vo.get(i).getPickupAreaDesc());
				int keyLeng  = queryVo.getKey().length();
				buffer.insert(index, font);
				buffer.insert(index+keyLeng+font.length(),fontEnd);	
				buffer.insert(0, htmlbody);
				buffer.append(htmlbodyEnd);
				
				
				
				
				//设置下次要搜索的 index 
				index = index+1;
				
					
				//设置让鼠标选中 表格中的这一行
				ui.getTable().setRowSelectionInterval(i, i);
				int row = ui.getTable().getSelectedRow();
				int modelRow =  ui.getTable().convertRowIndexToModel(row);
				BranchQueryVo selectVo = ((BranchDataModel) ui.getTable().getModel()).getData().get(modelRow);
				QueryBranchByNameAction.getRowDate(queryVo, selectVo,i);
				
				queryVo.setRowIndex(rowIndex);
				queryVo.setIndex(index);
				ui.getTextAreaPickup().setText(buffer.toString());
				
				break;
			}
			 
			
			
		}
	}

	@Override
	public void setInjectUI(PickupGoodsBranchDialog ui) {
		this.ui = ui;
	}

}
