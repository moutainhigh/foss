package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillCheckBoxColumn;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.google.inject.Inject;

/**
 * 零担电子面单管理页面的查询按钮
 * @author 305082
 *
 */
public class LTLEWaybillSalesDeptSearchAction implements IButtonActionListener<LTLEWaybillManageUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillSalesDeptSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillSalesDeptSearchAction.class);
	
	private static final Integer ONE = 1;
	
	private static final Integer EIGHT = 8;
	
	private static final Integer FIFTY = 50;
	
	private static final Double TWENTY_FOUR = 24.0;
	
	private static final Double SIXTY = 60.0;
	
	private static final Double ONE_THOUSAND = 1000.0;
	

	@Inject
	private IWaybillService wayBillService;

	private LTLEWaybillManageUI ui;

	@Override
	public void setInjectUI(LTLEWaybillManageUI ui) {
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
			
			//分几个部分进行查询，如果运单号、订单号、快递员工号、发货客户编码进行精确的查询
			//运单号
			String waybillNo = ui.getTxtMixNo().getText();
			//订单号
			String orderNo = ui.getTxtOrder().getText();
			//发货客户编码
			String deliveryCustomerCode = ui.getTxtCustomerCode().getText();
			//查询结果集
			List<LTLEWaybillQueryResultDto> eWaybillList = null;
			//异常数据
			int exceptCount = 0;
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//判定当前登陆人信息是否为空
			if(user == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo"));
			}
			//查询参数运单号集合定义
			List<String> waybillNoList=null;
			//查询参数订单号集合定义
			List<String> orderNoList=null;
			//查询参数的封装
			LTLEWaybillQueryResultDto ewaybillConditionDto = new LTLEWaybillQueryResultDto();
			//获取当前登录部门编码
			String code = user.getEmployee().getDepartment().getCode();
			//订单号 运单号精准查询
			if(StringUtils.isNotBlank(waybillNo) || StringUtils.isNotBlank(orderNo)){
				String[] waybills = waybillNo.split(",");
				if(StringUtils.isNotBlank(waybillNo) && waybills != null && waybills.length >0){
					waybillNoList = validateWaybill(waybills);
				}
				//订单号
				String[] orders = orderNo.split(",");
				if(StringUtils.isNotBlank(orderNo) && orders != null && orders.length >0){
					orderNoList = validateOrder(orders);
				}
				
				eWaybillList= new ArrayList<LTLEWaybillQueryResultDto>();
				//查询条件封装对象
				LTLEWaybillQueryResultDto ltleWaybillQueryResultDto = new LTLEWaybillQueryResultDto();
				setWaybillNoAndOrderNo(waybillNoList, orderNoList,
						ewaybillConditionDto, ltleWaybillQueryResultDto);
				exceptCount = searchByWaybillNoOROrderNo(eWaybillList,
						exceptCount, code, ltleWaybillQueryResultDto);
			}else{
				List<String> deliverCustomerList = validateDeliveryCustomerCode(deliveryCustomerCode);
				//查询参数
				ewaybillConditionDto=new LTLEWaybillQueryResultDto();
				//运单状态
				DataDictionaryValueVo waybillStatusVo = (DataDictionaryValueVo) ui.getStatusComboBox().getSelectedItem();
				//封装运单状态
					if("all".equals(waybillStatusVo.getValueCode())) {
						//全选时不封装参数，查询所有运单状态
					}else{
						//封装具体参数
						ewaybillConditionDto.setWaybillStatus(waybillStatusVo.getValueCode());
					}
				//标签推送状态
				DataDictionaryValueVo labelStatusVo = (DataDictionaryValueVo)ui.getLabelStatus().getSelectedItem();
				//标签推送状态
				if ("all".equals(labelStatusVo.getValueCode())) {
					//全选时不封装参数，查询所有标签推送状态
				}else{
					//封装具体参数
					ewaybillConditionDto.setLabelPushStatus(labelStatusVo.getValueCode());
				}
				//查询结果集
				eWaybillList = new ArrayList<LTLEWaybillQueryResultDto>();
				exceptCount = searchByDeliverCustomer(eWaybillList,
						exceptCount, ewaybillConditionDto, code,
						deliverCustomerList);
			}
			//判定是否查询到数据
			if(CollectionUtils.isEmpty(eWaybillList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}else {
				//有数据，设置可以导出数据
				ui.getExportButton().setEnabled(true);
				//设置可以重推 标签
				ui.getPushLabelButton().setEnabled(true);
			}
			//设置为具体数据
			ui.getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+exceptCount);
			final JXTable table = ui.getTable();
			Object[][] datas = ui.getArray(eWaybillList, 0);
			// 刷新表格
			LTLEWaybillManageTableModel tableModel = new LTLEWaybillManageTableModel(datas);
			table.setModel(tableModel);

			LTLEWaybillCheckBoxColumn checkColumn = new LTLEWaybillCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.select")), ui);
			
			List<JCheckBox> list = checkColumn.getRenderButtonList();
			ui.setList(list);
			ui.setCheckBoxColumn(checkColumn);
			
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

	private void setWaybillNoAndOrderNo(List<String> waybillNoList,
			List<String> orderNoList,
			LTLEWaybillQueryResultDto ewaybillConditionDto,
			LTLEWaybillQueryResultDto ltleWaybillQueryResultDto) {
		if(waybillNoList!=null&&waybillNoList.size()>0){
			//运单号装载
			ewaybillConditionDto.setWaybillNoList(waybillNoList);
			ltleWaybillQueryResultDto.setWaybillNoList(waybillNoList);
		}
		if(orderNoList!=null&&orderNoList.size()>0){
			//订单号装载
			ewaybillConditionDto.setOrderNoList(orderNoList);
			ltleWaybillQueryResultDto.setOrderNoList(orderNoList);
		}
	}

	private List<String> validateDeliveryCustomerCode(
			String deliveryCustomerCode) {
		//发货客户编码
		String[] deliveryCustomerCodes = deliveryCustomerCode.split(",");
		List<String> deliverCustomerList =null;
		if(StringUtils.isNotBlank(deliveryCustomerCode) && deliveryCustomerCodes != null && deliveryCustomerCodes.length >0){
			deliverCustomerList = validateDeliverCustomerList(deliveryCustomerCodes);
		}
		//下单时间
		if (ui.getZdStartDate().getDate() != null && ui.getZdEndDate().getDate() != null) {
			// 制单时间天数
			long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
			// 查询天数
			double days = zdiff / (ONE_THOUSAND * SIXTY * SIXTY * TWENTY_FOUR);
			// 查询天数不能超过7天
			if (days > ONE) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.overOneDayTime"));
			}
			if(days < 0){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
			}
		}else {
			// 要求提交时间完整，或全部为空
			throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.enterFullSubmitTime"));
		}
		return deliverCustomerList;
	}

	private int searchByDeliverCustomer(
			List<LTLEWaybillQueryResultDto> eWaybillList, int exceptCount,
			LTLEWaybillQueryResultDto ewaybillConditionDto, String code,
			List<String> deliverCustomerList) {
		//查询发货客户编码
		if(deliverCustomerList!=null&&deliverCustomerList.size()>0){
			//发货客户编
			ewaybillConditionDto.setCustomerCodeList(deliverCustomerList);
		}
			
		//订单下单时间
		ewaybillConditionDto.setStartTime(ui.getZdStartDate().getDate());
		ewaybillConditionDto.setEndTime(ui.getZdEndDate().getDate());
		//设置只查询登录部门的订单
		ewaybillConditionDto.setCreateOrgCode(code);
		//结果列表集合
		eWaybillList.addAll(wayBillService.queryOmsOrderOrLabelStatusByWaybillNo(ewaybillConditionDto));
		if(CollectionUtils.isNotEmpty(eWaybillList)){
			for(int i=0;i<eWaybillList.size();i++){
				//如果是退回或者激活失败的这种订单需要进行数据的处理
				if(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(eWaybillList.get(i).getWaybillStatus()) || WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(eWaybillList.get(i).getWaybillStatus())
						||WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(eWaybillList.get(i).getWaybillStatus())){
					exceptCount = exceptCount + ONE;
				}
			}
		}
		return exceptCount;
	}

	private int searchByWaybillNoOROrderNo(
			List<LTLEWaybillQueryResultDto> eWaybillList, int exceptCount,
			String code, LTLEWaybillQueryResultDto ltleWaybillQueryResultDto) {
		//设置只查询登录部门的订单
		ltleWaybillQueryResultDto.setCreateOrgCode(code);
		List<LTLEWaybillQueryResultDto> list = new ArrayList<LTLEWaybillQueryResultDto>();
		list = wayBillService.queryOmsOrderOrLabelStatusByWaybillNo(ltleWaybillQueryResultDto);
		eWaybillList.addAll(list);
		if(CollectionUtils.isNotEmpty(eWaybillList)){
			for(int i=0;i<eWaybillList.size();i++){
				//如果是退回或者激活失败的这种订单需要进行数据的处理
				if(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(eWaybillList.get(i).getWaybillStatus()) || WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(eWaybillList.get(i).getWaybillStatus())
						||WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(eWaybillList.get(i).getWaybillStatus())){
					exceptCount = exceptCount + ONE;
				}
			}
		}
		return exceptCount;
	}

	private List<String> validateDeliverCustomerList(
			String[] deliveryCustomerCodes) {
		List<String> deliverCustomerList;
		if(deliveryCustomerCodes.length > FIFTY){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")
					+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
		}
		deliverCustomerList = new ArrayList<String>();
		for(int i=0;i<deliveryCustomerCodes.length;i++){
			if(deliveryCustomerCodes[i].length() > FIFTY){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")+"["+(i+ONE)+"]"+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
			}else{
				if(StringUtils.isNotEmpty(deliveryCustomerCodes[i])){
					deliverCustomerList.add(deliveryCustomerCodes[i]);
				}
			}
		}
		return deliverCustomerList;
	}

	private List<String> validateOrder(String[] orders) {
		List<String> orderNoList;
		if(orders.length > FIFTY){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
		}
		orderNoList = new ArrayList<String>();
		for(int i=0;i<orders.length;i++){
			if(orders[i].length() > FIFTY){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
			}else{
				if(StringUtils.isNotEmpty(orders[i])){
					orderNoList.add(orders[i]);
				}
			}
		}
		return orderNoList;
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
	
	/**
	 * 获得下拉框中全部值的编码
	 */
	@SuppressWarnings("rawtypes")
	private List<String> getOrgCodes(JComboBox comboBox) {
		ComboBoxModel comb = comboBox.getModel();
		// 定义接收集合
		List<String> list = new ArrayList<String>();
		// 下拉框的值
		int count = comb.getSize();
		// 遍历下拉框
		for (int i = 0; i < count; i++) {
			// 获得下拉选项的code
			String code = ((DataDictionaryValueVo) comb.getElementAt(i)).getValueCode();
			// 过滤掉ALL
			if (!"all".equals(StringUtil.defaultIfNull(code))) {
				list.add(code);
			}
		}
		return list;
	}

}