package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.ReceiveOrgDialog;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 查询收货部门
 * 
 * @author WangQianJin
 * @date 2013-7-17 下午2:51:25
 */
public class ExpQueryReceiveOrgAction implements IButtonActionListener<ExpCalculateCostsDialog> {

	// 主界面
	ExpCalculateCostsDialog ui;

	// 营业部服务类
	SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory.getSalesDepartmentService();

	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();

		try {

			// 弹出查询收货部门对话框
			ReceiveOrgDialog dialog = new ReceiveOrgDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			// 出发部门对象
			SaleDepartmentEntity sale = dialog.getSaleDepartmentEntity();
			if(null == sale){
				return ;
			}

			// 出发城市与营业部对应关系DTO
			SalesDepartmentCityDto queryDto = new SalesDepartmentCityDto();
			// 设置查询条件
			queryDto.setSalesDepartmentCode(sale.getCode());
			SalesDepartmentCityDto dto = salesDepartmentService.querySalesDepartmentCityInfo(queryDto);
			// 营业部是否可以快递接货，如果是的话 就是试点营业部
			if (sale != null && dto != null) {
				dto.setTestSalesDepartment(sale.getCanExpressPickupToDoor());
				bean.setCreateSalesDepartmentCityDto(dto);
			}
			// 出发部门编码
			bean.setCreateOrgCode(sale.getCode());
			// 根据运输性质的改变，改变提货方式
			ExpCommon.changePickUpModeForSimple(bean, ui);

		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		} catch (BusinessException ex) {
			if (!"".equals(ex.getMessage())) {
				MsgBox.showInfo(ex.getMessage());
			}
		}
	}

	@Override
	public void setInjectUI(ExpCalculateCostsDialog ui) {
		this.ui = ui;
	}
}
