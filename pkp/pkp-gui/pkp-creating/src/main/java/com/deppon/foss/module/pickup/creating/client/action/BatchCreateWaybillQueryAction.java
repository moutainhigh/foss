package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCheckBoxColumn;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCreateWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.BatchWaybillButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 批量开单查询 查询action
 * @author guohao
 *
 */
public class BatchCreateWaybillQueryAction extends AbstractButtonActionListener<OpenBatchCreateWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(BatchCreateWaybillQueryAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(BatchCreateWaybillQueryAction.class);
		
	//已打印
	private static final String alreadyPrint="已打印";
	
	//未打印
	private static final String alreadyNotPrint="未打印";
	
	/**
	 * 批量开单远程接口
	 */
	IWaybillHessianRemoting waybillHessianRemoting;

	private OpenBatchCreateWaybillUI ui;
	
	@Override
	public void setIInjectUI(OpenBatchCreateWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		try {
			waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			 //通过仓管组 查询对应开单营业部
			String code = waybillHessianRemoting.queryBillingByStore(dept.getCode());
			if(null==code){
				code=dept.getCode();
			}
			/**
			 * 查询条件
			 */
			//开单状态
			String pendingType = ((DataDictionaryValueVo) ui.getWaybillStatus().getSelectedItem()).getValueCode();
			//打印状态
			String printWaybillType = ((DataDictionaryValueVo) ui.getPrintWaybillStatus().getSelectedItem()).getValueName();
			if(StringUtils.isNotEmpty(printWaybillType) && alreadyPrint.equals(printWaybillType)){
				printWaybillType="Y";
			}else if(StringUtils.isNotEmpty(printWaybillType) && alreadyNotPrint.equals(printWaybillType)){
				printWaybillType=null;
			}
		    Date startDate = ui.getZdStartDate().getDate();
		    Date endDate = ui.getZdEndDate().getDate();
		    // 制单时间天数
			long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
			// 查询天数
			double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
			// 查询天数不能超过1天
			if (days > 1) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.fivetime")
						+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpanln"));
			}
		    List<Object> list = new ArrayList<Object>();
			if(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(pendingType)){
				if("Y".equals(printWaybillType)){
					MsgBox.showInfo(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.MsgBox.nullListQuery"));
					return;
				}
				WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
			    waybillPendingDto.setBillStartTime(startDate);
			    waybillPendingDto.setBillEndTime(endDate);
			    WaybillPendingEntity pending =new WaybillPendingEntity();
			    pending.setPendingType(pendingType);
			    pending.setIsBatchCreateWaybill(FossConstants.ACTIVE);
			    pending.setCreateOrgCode(code);
			    waybillPendingDto.setWaybillPending(pending);
				List<WaybillPendingEntity> plist =  waybillHessianRemoting.queryPending(waybillPendingDto);
				if(CollectionUtils.isNotEmpty(plist)){
					list.addAll(plist);
				}
				pending.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
				plist =  waybillHessianRemoting.queryPending(waybillPendingDto);
				if(CollectionUtils.isNotEmpty(plist)){
					list.addAll(plist);
				}
		    }else{
		    	WaybillDto dto = new WaybillDto();
		    	dto.setBillStartTime(startDate);
		    	dto.setBillEndTime(endDate);
		    	dto.setPrintWaybillType(printWaybillType);
		    	WaybillEntity entity = new WaybillEntity();
		    	entity.setPendingType(pendingType);
		    	entity.setActive(FossConstants.ACTIVE);
		    	entity.setCreateOrgCode(code);
		    	ActualFreightEntity aft = new ActualFreightEntity();
		    	aft.setIsBatchCreateWaybill(FossConstants.ACTIVE);
		    	dto.setWaybillEntity(entity);
		    	dto.setActualFreightEntity(aft);
		    	List<WaybillEntity>  wlist =waybillHessianRemoting.queryWaybill(dto);
		    	if(CollectionUtils.isNotEmpty(wlist)){
		    		list.addAll(wlist);
		    	}
		    	
		    }
			
			//如果结果集数据小于等于0，则提示查询为空对话框
			if(list.size() <= 0){
				MsgBox.showInfo(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.MsgBox.nullListQuery"));
			}
			final JXTable table = ui.getTable();
			BatchCreateWaybillTableModel tableModel;
			Object[][] datas = ui.getArray(list);
			// 刷新表格
			tableModel = new BatchCreateWaybillTableModel(datas);
			table.setModel(tableModel);
			
			//new BatchCreateWaybillSearchButtonColumn(table.getColumn(1), ui, tableModel);
			BatchCheckBoxColumn checkColumn = new BatchCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice")), ui);
			List<JCheckBox> lit = checkColumn.getRenderButtonList();
			// send all check box to ui for select all
			ui.setList(lit);
			ui.setCheckBoxColumn(checkColumn);
			
			new BatchWaybillButtonColumn(table.getColumn(i18n.get("foss.gui.creating.BatchWaybillButtonColumn.column")), ui, tableModel,list);
			
			ui.refreshTable(table);
			
			// 默认选中查询结果的第一行
			/*if (ui.getTable() != null && ui.getTable().getRowCount() > 0) {
				ui.getTable().requestFocus();
				ui.getTable().setRowSelectionAllowed(true);
				ui.getTable().setRowSelectionInterval(0, 0);
			}*/

		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}

	}
	
}