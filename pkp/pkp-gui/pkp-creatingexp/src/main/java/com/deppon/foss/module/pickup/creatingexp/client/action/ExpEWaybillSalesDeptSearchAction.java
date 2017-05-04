package com.deppon.foss.module.pickup.creatingexp.client.action;

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

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillCheckBoxColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillPendingButtonColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.google.inject.Inject;

/**
 * <p>电子面单查询Action</p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-30 09:59:14
 */
public class ExpEWaybillSalesDeptSearchAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpEWaybillSalesDeptSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillSalesDeptSearchAction.class);
	
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	@Inject
	private IWaybillService wayBillService;

	private ExpSalesDepartEWaybillUI ui;

	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-30 09:58:30
	 * @param e
	 */
	@SuppressWarnings({ "static-access" })
	public void actionPerformed(ActionEvent e) {
		try {
			//如果离线是无法进行查询的
			if(!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.notLoginyet"));
			}
			
			// 清空左边的选择check box框子
			ui.getSelectExportWaybillNoList().clear();
			// 清空选中的导出id

			//进行方法啥的进行注入
			salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
			wayBillService = WaybillServiceFactory.getWaybillService();
			
			//分几个部分进行查询，如果运单号、订单号、快递员工号、发货客户编码进行精确的查询
			//运单号
			String waybillNo = ui.getTxtMixNo().getText();
			//快递员工号
			String createUserCode = ui.getTxtCreateUserCode().getText();
			//发货客户编码
			String deliveryCustomerCode = ui.getTxtCustomerCode().getText();
			//查询结果集
			List<EWaybillSalesDepartDto> eWaybillList = null;
			//异常数据
//			int exceptCount = 0;
			//查询参数的封装
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//判定当前登陆人信息是否为空
			if(user == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
			}
			//查询参数运单号集合定义
			List<String> waybillNoList=null;
			//查询参数的封装
			ClientEWaybillConditionDto ewaybillConditionDto ;
			
			//运单号精准查询
			if(StringUtils.isNotBlank(waybillNo)){
				String[] waybills = waybillNo.split(",");
				if(StringUtils.isNotBlank(waybillNo) && waybills != null && waybills.length >0){
					//超过50个数据需要进行提醒不能超过
					if(waybills.length > NumberConstants.NUMBER_50){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.numberPanel.waybillNo.label")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
					}
					//判定每个数字是否超过50个字数限制
					waybillNoList = new ArrayList<String>();
					for(int i=0;i<waybills.length;i++){
						if(waybills[i].length() < NumberConstants.NUMBER_8 || waybills[i].length() > NumberConstants.NUMBER_50){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
						}else{
							if(StringUtils.isNotEmpty(waybills[i])){
								waybillNoList.add(waybills[i]);
							}
						}
					}
				}
				List<String> queryWaybillStatus=new ArrayList<String>();
				queryWaybillStatus.add("UNLOAD");//
				queryWaybillStatus.add("SCAN");//已扫描未下单的数据的统计
				queryWaybillStatus.add("ORDERED");//已经下单
				queryWaybillStatus.add(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);//待激活
				queryWaybillStatus.add(WaybillConstants.EWAYBILL_ACTIVE_FAIL);//待补录
				queryWaybillStatus.add("RETURN");//已退回
				queryWaybillStatus.add("EFFECTIVE");//已下单
				eWaybillList= new ArrayList<EWaybillSalesDepartDto>();
			
				if(CollectionUtils.isNotEmpty(queryWaybillStatus)){
					for(int i=0;i<queryWaybillStatus.size();i++){
						//查询条件封装对象
						ewaybillConditionDto = new ClientEWaybillConditionDto();
						if(waybillNoList!=null&&waybillNoList.size()>0){
							//运单号装载
							ewaybillConditionDto.seteWaybillNoList(waybillNoList);
						}
						if("SCAN".equals(queryWaybillStatus.get(i))){
							ewaybillConditionDto.setCurrentDeptCode(user.getEmployee().getDepartment().getCode());
						}
						List<EWaybillSalesDepartDto> list = new ArrayList<EWaybillSalesDepartDto>();
						list=salesDeptWaybillService.queryEWaybillSalesDepart(ewaybillConditionDto, queryWaybillStatus.get(i));
						eWaybillList.addAll(list);
					}
//					if(CollectionUtils.isNotEmpty(eWaybillList)){
//						exceptCount = eWaybillList.size();
//					}
				}
//				if(CollectionUtils.isNotEmpty(eWaybillList)){
//					for(int i=0;i<eWaybillList.size();i++){
//						//如果是退回或者激活失败的这种订单需要进行数据的处理
//						if(DispatchOrderStatusConstants.STATUS_RETURN.equals(eWaybillList.get(i).getWaybillStatus()) || WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(eWaybillList.get(i).getWaybillStatus())){
//							exceptCount = exceptCount + 1;
//						}
//					}
//				}
			//快递员工号查询
			}else if(StringUtils.isNotBlank(createUserCode)){
				List<String> driverCodeList=null;
				
				//司机工号
				String[] driverCodes = createUserCode.split(",");
				if(StringUtils.isNotBlank(createUserCode) && driverCodes != null && driverCodes.length >0){
					if(driverCodes.length > NumberConstants.NUMBER_50){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptEWaybillUI.expressCode")+i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
					}
					driverCodeList = new ArrayList<String>();
					for(int i=0;i<driverCodes.length;i++){
						if(driverCodes[i].length() > NumberConstants.NUMBER_50){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.expEWaybillTableMode.column.seventeen")+"["+(i+1)+"]"+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
						}else{
							if(StringUtils.isNotEmpty(driverCodes[i])){
								driverCodeList.add(driverCodes[i]);
							}
						}
					}
				}
				
				//扫描时间
				if (ui.getZdStartDate().getDate() != null && ui.getZdEndDate().getDate() != null) {
					// 制单时间天数
					long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
					// 查询天数
					double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
					// 查询天数不能超过7天
					if (days > 1) {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.unActiveTime")
								+ i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.overOneDayTime"));
					}
					if(days < 0){
						throw new WaybillValidateException("[" + i18n.get("foss.gui.creating.linkTableMode.column.unActiveTime")
								+"]"+i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
					}
				}else {
					// 要求提交时间完整，或全部为空
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.enterFullSubmitTime"));
				}
				
				
				ewaybillConditionDto=new ClientEWaybillConditionDto();
				
				if(driverCodeList!=null&&driverCodeList.size()>0){
					//快递员装载
					ewaybillConditionDto.setDriverCodeList(driverCodeList);
				}
				//订单下单时间
				ewaybillConditionDto.setBillStartTime(ui.getZdStartDate().getDate());
				ewaybillConditionDto.setBillEndTime(ui.getZdEndDate().getDate());
				eWaybillList= new ArrayList<EWaybillSalesDepartDto>();
				
				eWaybillList.addAll(salesDeptWaybillService.queryEWaybillSalesDepart(ewaybillConditionDto,"DRIVER"));
//				if(CollectionUtils.isNotEmpty(eWaybillList)){
//					exceptCount = eWaybillList.size();
//				}
			}else{
				//发货客户编码
				String[] deliveryCustomerCodes = deliveryCustomerCode.split(",");
				List<String> deliverCustomerList =null;
				List<String> driverCodeList=null;
				if(StringUtils.isNotBlank(deliveryCustomerCode) && deliveryCustomerCodes != null && deliveryCustomerCodes.length >0){
					if(deliveryCustomerCodes.length > NumberConstants.NUMBER_50){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")
								+i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
					}
					deliverCustomerList = new ArrayList<String>();
					for(int i=0;i<deliveryCustomerCodes.length;i++){
						if(deliveryCustomerCodes[i].length() > NumberConstants.NUMBER_50){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven")+"["+(i+1)+"]"+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
						}else{
							if(StringUtils.isNotEmpty(deliveryCustomerCodes[i])){
								deliverCustomerList.add(deliveryCustomerCodes[i]);
							}
						}
					}
				}else{
					//按照订单状态查询时，客户编码为必填项
					//throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullDeliveryCustomerCode"));
				}
				//司机工号
				String[] driverCodes = createUserCode.split(",");
				if(StringUtils.isNotBlank(createUserCode) && driverCodes != null && driverCodes.length >0){
					if(driverCodes.length > NumberConstants.NUMBER_50){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptEWaybillUI.expressCode")+i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
					}
					driverCodeList = new ArrayList<String>();
					for(int i=0;i<driverCodes.length;i++){
						if(driverCodes[i].length() > NumberConstants.NUMBER_50){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.expEWaybillTableMode.column.seventeen")+"["+(i+1)+"]"+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
						}else{
							if(StringUtils.isNotEmpty(driverCodes[i])){
								driverCodeList.add(driverCodes[i]);
							}
						}
					}
				}
				//下单时间
				if (ui.getZdStartDate().getDate() != null && ui.getZdEndDate().getDate() != null) {
					// 制单时间天数
					long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
					// 查询天数
					double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
					// 查询天数不能超过7天
					if (days > 1) {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.unActiveTime")
								+ i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.overOneDayTime"));
					}
					if(days < 0){
						throw new WaybillValidateException("[" + i18n.get("foss.gui.creating.linkTableMode.column.unActiveTime")
								+"]"+i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
					}
				}else {
					// 要求提交时间完整，或全部为空
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.enterFullSubmitTime"));
				}
			
				
				List<String> queryWaybillStatus = null;
				DataDictionaryValueVo waybillStatusVo = (DataDictionaryValueVo) ui.getStatusComboBox().getSelectedItem();
				queryWaybillStatus=new ArrayList<String>();
				if("all".equals(waybillStatusVo.getValueCode())){
					queryWaybillStatus.add("UNLOAD");//
					queryWaybillStatus.add("SCAN");//已扫描未下单的数据的统计
					queryWaybillStatus.add("ORDERED");//已经下单
					queryWaybillStatus.add(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);//待激活
					queryWaybillStatus.add(WaybillConstants.EWAYBILL_ACTIVE_FAIL);//待补录
					queryWaybillStatus.add("RETURN");//已退回
					queryWaybillStatus.add("EFFECTIVE");
					//若勾选异常数据进行查询时，查询结果不包含已开单状态的运单/订单。
//					if(!ui.getChkExceptMsg().isSelected()){
//						//已开单
//						queryWaybillStatus.add("EFFECTIVE");
//					}
				}else if("ORDERED".equals(waybillStatusVo.getValueCode())){
					queryWaybillStatus.add("ORDERED");//已经下单
					queryWaybillStatus.add(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);//待激活
				}else if ("RETURN".equals(waybillStatusVo.getValueCode())) {
					queryWaybillStatus.add("UNLOAD");//
					queryWaybillStatus.add("SCAN");//已扫描未下单的数据的统计
					queryWaybillStatus.add("RETURN");//已退回
					
				}else {
					queryWaybillStatus.add(waybillStatusVo.getValueCode());
				}
				eWaybillList = new ArrayList<EWaybillSalesDepartDto>();
				if(CollectionUtils.isNotEmpty(queryWaybillStatus)){
					for(int i=0;i<queryWaybillStatus.size();i++){
						ewaybillConditionDto=new ClientEWaybillConditionDto();
						if(deliverCustomerList!=null&&deliverCustomerList.size()>0){
							//发货客户编
							ewaybillConditionDto.setCustomerCodeList(deliverCustomerList);
						}else{
							//客户编码不填时候为默认查询  需要匹配当前登陆部门
							ewaybillConditionDto.setCurrentDeptCode(user.getEmployee().getDepartment().getCode());
						}
						
						if(driverCodeList!=null&&driverCodeList.size()>0){
							//快递员编号
							ewaybillConditionDto.setDriverCodeList(driverCodeList);
						}
						//订单下单时间
						ewaybillConditionDto.setBillStartTime(ui.getZdStartDate().getDate());
						ewaybillConditionDto.setBillEndTime(ui.getZdEndDate().getDate());
						
						if("SCAN".equals(queryWaybillStatus.get(i))){
							//已扫描未下单状态的需要匹配当前登陆部门
							ewaybillConditionDto.setCurrentDeptCode(user.getEmployee().getDepartment().getCode());
						}
						eWaybillList.addAll(salesDeptWaybillService.queryEWaybillSalesDepart(ewaybillConditionDto, queryWaybillStatus.get(i)));
					}
//					if(CollectionUtils.isNotEmpty(eWaybillList)){
//						exceptCount = eWaybillList.size();
//					}
				}
			}
			//判定是否查询到数据
			if(CollectionUtils.isEmpty(eWaybillList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}
			//设置为具体数据
//			ui.getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult")+exceptCount);
			final JXTable table = ui.getTable();
			Object[][] datas = ui.getArray(eWaybillList, 0);
			// 刷新表格
			ExpEWaybillTableMode tableModel = new ExpEWaybillTableMode(datas);
			table.setModel(tableModel);

			ExpEWaybillCheckBoxColumn checkColumn = new ExpEWaybillCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.select")), ui);
			
			new ExpEWaybillPendingButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), ui, tableModel);
			List<JCheckBox> list = checkColumn.getRenderButtonList();
			ui.setList(list);
			ui.setCheckBoxColumn(checkColumn);
			
			//设置发货客户的大客户标记
			/*new ExpEWaybillDeliveryBigCustomerColumn(table.getColumn(i18n.get("foss.gui.creating.linkTableMode.column.deliveryCustomer")),ui,tableModel,datas);
			
			new ExpEWaybillPendingButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), ui, tableModel);*/
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
	
	/**
	 * 获得下拉框中全部值的编码
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-21 下午4:29:52
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