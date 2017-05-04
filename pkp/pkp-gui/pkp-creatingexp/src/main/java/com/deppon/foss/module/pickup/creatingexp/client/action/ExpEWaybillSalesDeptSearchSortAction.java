package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillSortTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.google.inject.Inject;

/**
 * <p>电子面单分拣查询Action</p>
 * @author Foss-305082-Caishangfei
 * @date 2016-02-25 17:14:00
 */
public class ExpEWaybillSalesDeptSearchSortAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpEWaybillSalesDeptSearchSortAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillSalesDeptSearchSortAction.class);
	
	@Inject
	private IWaybillService waybillService;

	private ExpSalesDepartEWaybillUI ui;

	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * @author Foss-305082-caishangfei
	 * @date 2016-03-09 13:57:18
	 * @param e
	 */
	@SuppressWarnings({ "static-access" })
	public void actionPerformed(ActionEvent e) {
		try {
			//如果离线是无法进行查询的
			if(!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.linkTableMode.column.notLoginyet"));
			}

			//进行方法啥的进行注入
			waybillService = WaybillServiceFactory.getWaybillService();
			//分几个部分进行查询，如果运单号、快递员工号、发货客户编码进行精确的查询
			//运单号
			String waybillNo = ui.getTxtMixNo1().getText();
			//快递员工号
			String createUserCode = ui.getTxtCreateUserCode1().getText();
			//发货客户编码
			String deliveryCustomerCode = ui.getTxtCustomerCode1().getText();
			//查询结果集
			List<SortingScanEntity> eWaybillList = null;
			//查询参数的封装
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//判定当前登陆人信息是否为空
			if(user == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
			}
			//查询参数的封装
			SortingScanDto sortingScanDto = new SortingScanDto();
			
			//运单号精准查询
			if(StringUtils.isNotBlank(waybillNo)){
				if(waybillNo.length()>10){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
				}
				//创建查询结果集合
				eWaybillList= new ArrayList<SortingScanEntity>();
				//封装运单号
				sortingScanDto.setWaybillNo(waybillNo);
				//远程调用中转查询接口
				eWaybillList = waybillService.queryEwayBillRecords(sortingScanDto);
			//快递员工号，发货客户编码精准查询
			}else if(StringUtils.isNotBlank(createUserCode)||StringUtils.isNotBlank(deliveryCustomerCode)){
				eWaybillList= new ArrayList<SortingScanEntity>();
				if (StringUtils.isNotBlank(createUserCode)) {
					//封装快递员工号
					sortingScanDto.setOperatorCode(createUserCode);
				}
				if (StringUtils.isNotBlank(deliveryCustomerCode)) {
					//封装客户编码
					sortingScanDto.setDeliveryCustomerCode(deliveryCustomerCode);
				}
				//扫描时间
				if (ui.getZdStartDate1().getDate() != null && ui.getZdEndDate1().getDate() != null) {
					// 制单时间天数
					long zdiff = ui.getZdEndDate1().getDate().getTime() - ui.getZdStartDate1().getDate().getTime();
					// 查询天数
					double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
					// 查询天数不能超过1天
					if (days > 1) {
						throw new WaybillValidateException("[起止时间]"+ i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.overOneDayTime"));
					}
					if(days < 0){
						throw new WaybillValidateException("[起止时间]"+i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
					}
				}else {
					// 要求提交时间完整，或全部为空
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.enterFullSubmitTime"));
				}
				//获取当前系统时间
				Calendar date = Calendar.getInstance();
				//保存当前系统时间的年月日
				int currentYear = date.get(Calendar.YEAR);
				//月份为0-11，因此，月份+1
				int currentMonth = date.get(Calendar.MONTH)+1;
				int currentDay = date.get(Calendar.DAY_OF_MONTH);
				//设置页面选择的时间
				date.setTime(ui.getZdEndDate1().getDate());
				//保存页面选择时间的年月日
				int year = date.get(Calendar.YEAR);
				//月份为0-11，因此，月份+1
				int month = date.get(Calendar.MONTH)+1;
				int day = date.get(Calendar.DAY_OF_MONTH);
				//判断年份
				if (year>currentYear) {
					//大于当前系统时间，不允许查询
					throw new WaybillValidateException("查询时间不能大于当前日期["+currentYear+"年"+currentMonth+"月"+currentDay+"日]");
				}else if(year==currentYear&&month>currentMonth){
					//当年的月份不能大于系统月份
					throw new WaybillValidateException("查询时间不能大于当前日期["+currentYear+"年"+currentMonth+"月"+currentDay+"日]");
				}else if (year==currentYear&&month==currentMonth&&day>currentDay) {
					//当年当月时不能大于当天
					throw new WaybillValidateException("查询时间不能大于当前日期["+currentYear+"年"+currentMonth+"月"+currentDay+"日]");
				}
				//订单下单时间
				sortingScanDto.setQueryTimeBegin(ui.getZdStartDate1().getDate());
				sortingScanDto.setQueryTimeEnd(ui.getZdEndDate1().getDate());
				
				eWaybillList = waybillService.queryEwayBillRecords(sortingScanDto);
			}else{
				throw new WaybillValidateException("请输入查询条件");
			}
			//判定是否查询到数据
			if(CollectionUtils.isEmpty(eWaybillList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}
			final JXTable table = ui.getSortTable();
			Object[][] datas = ui.getArray2(eWaybillList);
			// 刷新表格
			ExpEWaybillSortTableMode tableModel = new ExpEWaybillSortTableMode(datas);
			table.setModel(tableModel);
			ui.refreshTable(table);
			// 默认选中查询结果的第一行
//			if (ui.getSortTable() != null && ui.getSortTable().getRowCount() > 0) {
//				ui.getSortTable().requestFocus();
//				ui.getSortTable().setRowSelectionAllowed(true);
//				ui.getSortTable().setRowSelectionInterval(0, 0);
//			}
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