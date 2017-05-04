/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.WebOrderVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.order.ExpWebOrderDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.exception.CrmOrderImportException;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWebOrderQueryAction implements
		IButtonActionListener<ExpWebOrderDialog> {

	// 日志
	private static Log log = LogFactory.getLog(ExpWebOrderQueryAction.class);
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpWebOrderQueryAction.class);

	private ExpWebOrderDialog ui;

	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 数据字典
	private IDataDictionaryValueService dataDictionaryValueService;

	// 组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public ExpWebOrderQueryAction() {
		dataDictionaryValueService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
		orgAdministrativeInfoService = GuiceContextFactroy.getInjector().getInstance(OrgAdministrativeInfoService.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getWaybillEditUI().getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		WebOrderVo queryWebOrderVo = ui.getBinder().getBean();
		int pagesize = ui.getNavigator().getPageSize();
		if (pagesize <= 0) {
			pagesize = NumberConstants.NUMBER_50;
		}
		int currentPage = 1;
		// 查询条件
		if (ui.getDatePickerStart().getDate() == null|| ui.getDatePickerEnd().getDate() == null) {
			//请输入起始时间段！
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nullDatePickerStart"));
			return;
		}
		
		//结束时间不能晚于今天！
		if (ui.getDatePickerEnd().getDate().after(new Date())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.afterDatePickerEnd"));
			return;
		}

		if (DateUtils.dateDiffInDays(ui.getDatePickerStart().getDate(), ui.getDatePickerEnd().getDate()) > 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
			return;
		}

		CrmOrderQueryDto queryVo = new CrmOrderQueryDto();
		queryVo.setAcceptStatus(queryWebOrderVo.getOrderStatus().getValueCode());
		queryVo.setOrderType(queryWebOrderVo.getOrderType().getValueCode());
		queryVo.setOrderNumber(queryWebOrderVo.getOrderNo());
		queryVo.setShipperCust(queryWebOrderVo.getDeliveryCustomerName());
		queryVo.setShipperLinkman(queryWebOrderVo.getDeliveryCustomerContact());
		queryVo.setShipperMobile(queryWebOrderVo.getDeliveryCustomerMobilephone());
		queryVo.setShipperPhone(queryWebOrderVo.getDeliveryCustomerPhone());
		//产品类型code
		if(null != queryWebOrderVo.getProductCode()){
			queryVo.setProductCode(queryWebOrderVo.getProductCode().getCode());
		}
		queryVo.setPageNum(currentPage);
		queryVo.setPageSize(pagesize);
		
		String orderNumber = ui.getTxtOrderNo().getText().trim();
		if(StringUtils.isEmpty(orderNumber)){
			queryVo.setBeginTime(ui.getDatePickerStart().getDate());
			Date endTime = null;
			if (ui.getDatePickerEnd().getDate() != null) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					endTime = sdf2.parse(sdf1.format(ui.getDatePickerEnd().getDate()) + " 23:59:59");
				} catch (ParseException e1) {
					log.error("ParseException", e1);
				}
			}
			queryVo.setEndTime(endTime);
		}else{
			//订单号
			queryVo.setOrderNumber(orderNumber);
		}

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if (dept != null) {
			queryVo.setSalesDept(dept.getUnifiedCode());
		}

		// 集中接货时取收货营业部的标杆编码
		if (null != dept && FossConstants.YES.equals(dept.getBillingGroup())) {
			OrgAdministrativeInfoEntity org = waybillService.queryByCode(bean.getReceiveOrgCode());
			if (org != null) {
				queryVo.setSalesDept(org.getUnifiedCode());
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nullOrgAdministrativeInfo"));
				return;
			}
		}

		// 查询数据
		CrmOrderInfoDto infoVos = null;
		try {
			infoVos = waybillService.queryCrmExpressOrder(queryVo);
			if(infoVos == null){
				MsgBox.showInfo("订单信息为空");
				return;
			}
			if (infoVos.getTotalCount() == 0) {				
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nulCrmOrderInfo"));
				
				ui.getWebOrderSortedTableDataUpdater().setNeedCount(false);
				// 按钮查询 不需要再次查询了
				ui.getWebOrderSortedTableDataUpdater().setNeedSearch(false);
				// 查询result
				ui.getWebOrderSortedTableDataUpdater().setResultDto(infoVos);
				ui.setTableData(infoVos);
				return;
			}
		} catch (CrmOrderImportException ex) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.failQueryCrmOrder")+ ex.getMessage());
		}

		// 将订单中的code转换为中文显示在界面上
		if(infoVos != null){
			convertName(infoVos);
		}
		// 若删除订单后还要去判断下集合是否为空
		if (infoVos != null && infoVos.getTotalCount() == 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nulCrmOrderInfo"));
			return;
		}

		// 按钮查询 不需要再次查询了
		ui.getWebOrderSortedTableDataUpdater().setNeedCount(false);
		// 按钮查询 不需要再次查询了
		ui.getWebOrderSortedTableDataUpdater().setNeedSearch(false);
		// 查询result
		if(infoVos != null){
			ui.getWebOrderSortedTableDataUpdater().setResultDto(infoVos);
			ui.setTableData(infoVos);
		}
		ui.getNavigator().setPageSize(pagesize);
		ui.getNavigator().setCurrentPage(currentPage);
		ui.getNavigator().refresh();
	}

	/**
	 * 将订单中的code转换为中文显示在界面上
	 * 
	 * @param infoVos
	 */
	private void convertName(CrmOrderInfoDto infoVos) {
		List<CrmOrderInfo> crmOrderInfoList = infoVos.getCrmOrderInfoList();
		if (crmOrderInfoList == null || crmOrderInfoList.isEmpty()) {
			return;
		}
		for (CrmOrderInfo crm : crmOrderInfoList) {
			String orderStatus = crm.getOrderStatus();
			if (StringUtils.isNotEmpty(orderStatus)) {
				DataDictionaryValueEntity e = dataDictionaryValueService.queryDataDictoryValueByCode(WaybillConstants.ORDER_STATUS, orderStatus);
				if (e != null) {
					crm.setOrderStatusName(e.getValueName());
				}
			}


			String orderType = crm.getResource();
			if (StringUtils.isNotEmpty(orderType)) {
				DataDictionaryValueEntity e = dataDictionaryValueService.queryDataDictoryValueByCode(WaybillConstants.ORDER_TYPE, orderType);
				if (e != null) {
					crm.setResourceName(e.getValueName());
				}
			}

			String tranportMode = crm.getTransportMode();
			if (StringUtils.isNotEmpty(tranportMode)) {
				for (CrmTransportTypeEnum tenum : CrmTransportTypeEnum.values()) {
					if (tranportMode.equals(tenum.getCode())) {
						crm.setTransportModeName(tenum.getName());
						crm.setTransportModeFossCode(tenum.getFossCode());
					}
				}
			}

			String orgUnifCode = crm.getStartStation();
			if (StringUtils.isNotEmpty(orgUnifCode)) {
				OrgAdministrativeInfoEntity e = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(orgUnifCode);
				if (e != null) {
					crm.setStartStationName(e.getName());
				}

			}
		}

	}

	@Override
	public void setInjectUI(ExpWebOrderDialog ui) {
		this.ui = ui;
	}

}
