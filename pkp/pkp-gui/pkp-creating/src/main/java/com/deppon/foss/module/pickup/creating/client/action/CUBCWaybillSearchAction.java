package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillCheckBoxColumn;
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManagerUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.google.inject.Inject;

/**
 * 结算中心运单推送页面的查询按钮
 * 
 *
 */
public class CUBCWaybillSearchAction implements IButtonActionListener<CUBCWaybillManagerUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(CUBCWaybillSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(CUBCWaybillSearchAction.class);
	
	private static final Integer EIGHT = 8;
	
	private static final Integer FIFTY = 50;

	@Inject
	private IWaybillService wayBillService;

	private CUBCWaybillManagerUI ui;

	@Override
	public void setInjectUI(CUBCWaybillManagerUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//如果离线是无法进行查询的
			if(!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.notLoginyet"));
			}
			// 清空左边的选择check box框子
			ui.getSelectExportWaybillNoList().clear();
			//设置全选按钮
			ui.getAllSelectCheckBox().setSelected(false);
			//进行方法啥的进行注入
			wayBillService = WaybillServiceFactory.getWaybillService();
			//运单号
			String waybillNo = ui.getTxtMixNo().getText();
			//查询结果集
			List<WaybillLogEntity> cubcLogEntityList = null;
			//异常数据
			int exceptCount = 0;
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//判定当前登陆人信息是否为空
			if(user == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo"));
			}
			//查询参数运单号集合定义
			List<String> waybillNoList=null;
			//查询参数的封装
			WaybillLogEntityQueryDto logEntityQueryDto = new WaybillLogEntityQueryDto();
			//获取当前登录部门编码
			String code = user.getEmployee().getDepartment().getCode();
			//订单号 运单号精准查询
			if(StringUtils.isNotBlank(waybillNo)){
				String[] waybills = waybillNo.split(",");
				if(StringUtils.isNotBlank(waybillNo) && waybills != null && waybills.length >0){
					waybillNoList = validateWaybill(waybills);
				}
				DataDictionaryValueVo labelStatusVo = (DataDictionaryValueVo)ui.getOperationStatus().getSelectedItem();
				//封装具体参数
				if("all".equals(labelStatusVo.getValueCode())) {
					
				}else{
					logEntityQueryDto.setCode(labelStatusVo.getValueCode());
				}
				cubcLogEntityList= new ArrayList<WaybillLogEntity>();
				setCUBCLogEntityCondition(waybillNoList, null,
						logEntityQueryDto);
				exceptCount = searchByCondition(cubcLogEntityList,
						exceptCount, code, logEntityQueryDto);
			}else{
				//查询参数
				logEntityQueryDto=new WaybillLogEntityQueryDto();
				//开单状态
				DataDictionaryValueVo labelStatusVo = (DataDictionaryValueVo)ui.getOperationStatus().getSelectedItem();
				//封装具体参数
				if("all".equals(labelStatusVo.getValueCode())) {
					
				}else{
					logEntityQueryDto.setCode(labelStatusVo.getValueCode());
				}
				//查询结果集
				cubcLogEntityList = new ArrayList<WaybillLogEntity>();
				exceptCount = searchByCreateTime(cubcLogEntityList,
						exceptCount, logEntityQueryDto, code,
						null);
			}
			//判定是否查询到数据
			if(CollectionUtils.isEmpty(cubcLogEntityList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}else {
				ui.getWaybillPushButton().setEnabled(true);
			}
			//设置为具体数据
			ui.getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+exceptCount);
			final JXTable table = ui.getTable();
			Object[][] datas = ui.getArray(cubcLogEntityList, 0);
			// 刷新表格
			CUBCWaybillManageTableModel tableModel = new CUBCWaybillManageTableModel(datas);
			table.setModel(tableModel);

			CUBCWaybillCheckBoxColumn checkColumn = new CUBCWaybillCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.select")), ui);
			
			List<JCheckBox> list = checkColumn.getRenderButtonList();
			ui.setList(list);
			ui.setCheckBoxColumn(checkColumn);
			//隐藏相关的列
		    TableColumnModel tableColumnModel = table.getColumnModel();
		    //其实没有移除，仅仅隐藏而已  
		    TableColumn tc = tableColumnModel.getColumn(1);  
		    tableColumnModel.removeColumn(tc);
			ui.refreshTable(table);
			// 默认选中查询结果的第一行
			if (ui.getTable() != null && ui.getTable().getRowCount() > 0) {
				ui.getTable().requestFocus();
				ui.getTable().setRowSelectionAllowed(true);
				ui.getTable().setRowSelectionInterval(0, 0);
			}
		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}
	}

	private void setCUBCLogEntityCondition(List<String> waybillNoList,
			List<String> orderNoList,
			WaybillLogEntityQueryDto logEntityQueryDto) {
		if(waybillNoList!=null&&waybillNoList.size()>0){
			//运单号装载
			logEntityQueryDto.setWaybillNos(waybillNoList);
		}
	}

	private int searchByCreateTime(
			List<WaybillLogEntity> cubcLogEntityList, int exceptCount,
			WaybillLogEntityQueryDto  logEntityQueryDto, String code,
			List<String> deliverCustomerList) {
		//订单下单时间
		logEntityQueryDto.setStartTime(ui.getZdStartDate().getDate());
		logEntityQueryDto.setEndTime(ui.getZdEndDate().getDate());
		//结果列表集合
		cubcLogEntityList.addAll(wayBillService.queryLogEntityByCondition(logEntityQueryDto));
		return exceptCount;
	}

	private int searchByCondition(
			List<WaybillLogEntity> cubcLogEntityList, int exceptCount,
			String code, WaybillLogEntityQueryDto cubcLogEntityQueryDto) {
		List<WaybillLogEntity> list = new ArrayList<WaybillLogEntity>();
		list = wayBillService.queryLogEntityByCondition(cubcLogEntityQueryDto);
		cubcLogEntityList.addAll(list);
		return exceptCount;
	}

	private List<String> validateWaybill(String[] waybills) {
		List<String> waybillNoList;
		//超过50个数据需要进行提醒不能超过
		if(waybills.length > FIFTY){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.numberPanel.waybillNo.label")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
		}
		//判定每个数字是否超过50个字数限制
		waybillNoList = new ArrayList<String>();
		for(int i=0;i<waybills.length;i++){
			if(waybills[i].length() < EIGHT || waybills[i].length() > FIFTY){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
			}else{
				if(StringUtils.isNotEmpty(waybills[i])){
					waybillNoList.add(waybills[i]);
				}
			}
		}
		return waybillNoList;
	}

}