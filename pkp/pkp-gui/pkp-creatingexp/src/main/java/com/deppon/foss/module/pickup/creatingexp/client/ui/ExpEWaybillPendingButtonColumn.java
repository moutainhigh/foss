package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待处理运单补录按钮
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,</p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
@SuppressWarnings("unused")
public class ExpEWaybillPendingButtonColumn extends AbstractCellEditor implements TableCellEditor, TableCellRenderer, ActionListener { 
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpEWaybillPendingButtonColumn.class);
  
    /**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpEWaybillPendingButtonColumn.class); 
	
	/**
	 * salesDeptWaybillService
	 */
	private transient ISalesDeptWaybillService salesDeptWaybillService;
	
	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * 运单状态列表
	 */
	private List<DataDictionaryValueEntity> list;
	
	/**
	 * ui
	 */
	private ExpSalesDepartEWaybillUI ui;
    
    /**
     * LinkTableMode
     */
    private ExpEWaybillTableMode dtm;  
    
    /**
     * 行
     */
    private int row;  
    
    /**
     * table mode
     */
    private ExpEWaybillTableMode tableModel; 
    
    //不需要点击的按钮的状态中文字
    private List<String> ignoreChieseName = new ArrayList<String>();
      
    /**
     * 构造方法
     * @param tc
     * @param ui
     */
    public ExpEWaybillPendingButtonColumn(TableColumn tc, ExpSalesDepartEWaybillUI ui, ExpEWaybillTableMode tableModel) {  
        this.tableModel = tableModel;
        tc.setCellEditor(this);
        tc.setCellRenderer(this);
        this.ui = ui;
        salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
        //作废
        ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillObsolete"));
        //中止
        ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillAbort"));
        //已开单
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPcActive"));
		//已补录
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPdaActive"));
		//已扫描
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsScanned"));
		//未扫描
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.IsUnScann"));
		//待激活
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillPreActive"));
		//已退回
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillOrderReturn"));
		//已下单
		ignoreChieseName.add(i18n.get("foss.gui.creating.expEWaybillTableMode.column.ewaybill.ewaybillOrdered"));
		//
		ignoreChieseName.add("");
    }

    /**
     * 输入panel
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int viewRow, int column) {  
    	int row = table.convertRowIndexToModel(viewRow);
        //  当单元格处于编辑状态时   
        if(tableModel!=null){
    		String statusChinese  =(String) tableModel.getValueAt(row, 2);
    		if(ignoreChieseName.contains(statusChinese)){
    			JLabel editorButton = new JLabel();
    			return editorButton; 
    		}else{
    			JButton editorButton = new JButton();
    			editorButton.setFocusable(false);  
    		    editorButton.addActionListener(this); 
    		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
    	        dtm = (ExpEWaybillTableMode)table.getModel();  
    	        this.row = row;
    	        return editorButton;  
    		}
    	}else{
    		JButton editorButton = new JButton();
			editorButton.setFocusable(false);  
		    editorButton.addActionListener(this); 
		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
	        dtm = (ExpEWaybillTableMode)table.getModel();  
	        this.row = row;  
	        return editorButton;   
    	}
    }
    
    /**
     * 结果列表 Component
     */
    @Override  
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
    		boolean hasFocus, int viewRow, int column) {  
    	int row = table.convertRowIndexToModel(viewRow);
        //当单元格处于展示状态时
    	if(tableModel!=null){
    		String statusChinese  =(String) tableModel.getValueAt(row, 2);
    		if(ignoreChieseName.contains(statusChinese)){
    			JLabel editorButton = new JLabel();
    			return editorButton; 
    		}else{
    			JButton editorButton = new JButton();
    			editorButton.setFocusable(false);  
    		    editorButton.addActionListener(this); 
    		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
    	        dtm = (ExpEWaybillTableMode)table.getModel();  
    	        this.row = row;  
    	        return editorButton;  
    		}
    	}else{
    		JButton editorButton = new JButton();
			editorButton.setFocusable(false);  
		    editorButton.addActionListener(this); 
		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
	        dtm = (ExpEWaybillTableMode)table.getModel();  
	        this.row = row;  
	        return editorButton;   
    	}    
    } 
    
    /**
	 * 补录按钮事件处理
	 */
    @Override  
    public void actionPerformed(ActionEvent arg0) {  
    	try {
    		//读取运单号
    		Object object = dtm.getValueAt(this.row, NumberConstants.NUMBER_3);
    		if(object == null){
    			//订单号
    			object = dtm.getValueAt(this.row, NumberConstants.NUMBER_4);
    			if(object == null){
    				MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.noExist"));
    			    return;
    			}
    		}
    		
    		//封装查询参数
			String mixNo = object.toString();
			WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
			waybillPendingDto.setMixNo(mixNo);
			waybillPendingDto.setWaybillType(WaybillConstants.EWAYBILL);
			waybillPendingDto.setActive(FossConstants.YES);
			
			//管理部出发运单业务操作服务
			salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
			//获得待处理运单基本信息对象 
			WaybillPendingEntity waybillPending = salesDeptWaybillService.queryBasicWaybillPendingData(waybillPendingDto);
			if(waybillPending != null){	
				// 校验运单号是否已存在
				validateWaybillNo(waybillPending.getPendingType(),waybillPending.getWaybillNo());
			}
			if(waybillPending==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillPending.getProductCode())){
				//进入零担的补录界面
				ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
				uiAction.importWaybillEditUI(waybillPending);
			}else{
				//否则进入快递补录界面
				ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
				uiAction.importWaybillEditUI(waybillPending);
			}
		} catch (BusinessException ex) {
			LOGGER.error("导入异常", ex);
			if (StringUtils.isNotEmpty(ex.getMessage())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
				return;
			}else if(StringUtils.isNotEmpty(ex.getErrorCode())){
			    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
			    return;
			}
		}
    }
    
    // 校验运单号是否已存在
    private void validateWaybillNo(String pendingType,String waybillNo){
    	if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType)){
			//PDA补录
			if (waybillService.checkWaybillNoExceptPDAPending(waybillNo) &&  waybillService.checkEWaybillNoExceptPDAPending(waybillNo)) {		    			
    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
    		}
		}else{
			//非PDA补录
    		if (waybillService.checkWaybillNo(waybillNo)) {		    			
    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
    		}
		}
    }

    /**
     * getSalesDeptWaybillService
     * @return ISalesDeptWaybillService
     */
	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}

	/**
	 * setSalesDeptWaybillService
	 * @param salesDeptWaybillService ISalesDeptWaybillService
	 */
	public void setSalesDeptWaybillService(
			ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	} 
  
    
    @Override  
    public Object getCellEditorValue() {  
        //  当单元格的编辑状态结束后调用此方法内的代码   
        return null;  
    }  
  
    @Override  
    public boolean shouldSelectCell(EventObject arg0) {  
        return super.shouldSelectCell(arg0);  
    }
}