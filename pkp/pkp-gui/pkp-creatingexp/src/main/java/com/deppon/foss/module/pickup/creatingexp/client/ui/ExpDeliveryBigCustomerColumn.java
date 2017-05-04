package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 发货客户列的渲染器
 * @author 026123-foss-lifengteng
 */
public class ExpDeliveryBigCustomerColumn implements TableCellRenderer {
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpDeliveryBigCustomerColumn.class);

	private static final int EIGHT = 8;
	
	//出发运单管理界面
	private ExpSalesDeptWaybillUI ui;
	
	//表格模型
	private ExpLinkTableMode tableModel; 
	
	//发货客户名称
	private Object[][] datas;
	
	
	/**
	 * 构造方法
	 * @param ui
	 * @param tableModel
	 */
	public ExpDeliveryBigCustomerColumn(TableColumn tc,ExpSalesDeptWaybillUI ui,ExpLinkTableMode tableModel,Object[][] datas) {
        tc.setCellRenderer(this);
		this.ui = ui;
		this.tableModel = tableModel;
		this.datas = datas;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int viewRow, int column) {
		//非空判断
    	if(tableModel == null){
    		tableModel = (ExpLinkTableMode)table.getModel(); 
    	}
    	
		//获得当前行号
		int row = table.convertRowIndexToModel(viewRow);
		//发货客户名称 
		String deliveryCustomerName = datas[row][EIGHT].toString();
		//非空判断
    	if(tableModel!=null){
    		//是否大客户标记
    		String isBigCustomer  =(String) tableModel.getValueAt(row, NumberConstants.NUMBER_25);
    		
    		//设置发货客户名称
    		JLabel iconLabel = new JLabel();
    		iconLabel.setText(deliveryCustomerName);
    		
    		//若为大客户，则加入标记
    		if(FossConstants.ACTIVE.equals(isBigCustomer)){
    			iconLabel.setIcon(CommonUtils.createIcon(ui.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
    			return iconLabel; 
    		}else{
    			iconLabel.setIcon(CommonUtils.createIcon(ui.getClass(), "", 1, 1));
    			return iconLabel;  
    		}
    		
    		
    	}else{
    		JLabel iconLabel = new JLabel();
    		iconLabel.setText(deliveryCustomerName);
    		return iconLabel ;  
    	}
	}
}
