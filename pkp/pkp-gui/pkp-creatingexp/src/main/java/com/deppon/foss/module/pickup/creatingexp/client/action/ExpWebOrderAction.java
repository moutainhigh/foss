/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.order.ExpWebOrderDialog;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWebOrderAction  implements IButtonActionListener<ExpWaybillEditUI> {

	// 主界面
	ExpWaybillEditUI ui;
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpWebOrderAction.class);	
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillPanelVo panelVo = ui.getBindersMap().get("waybillBinder").getBean();
		if(panelVo.getPickupCentralized()){
			if(panelVo.getReceiveOrgCode()!=null&&!panelVo.getReceiveOrgCode().equals(getLoginDept().getCode())){
				ExpWebOrderDialog dialog = ui.getExpWebOrderDialog();
				dialog.setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_600);
				ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderAction.msgBox.nullReceiveOrgCode"));
			}
		}else{
			ExpWebOrderDialog dialog = ui.getExpWebOrderDialog();
			dialog.setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_600);
			ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
		}
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 
	 * <p>获取当前登录人员所在部门信息</p> 
	 * @author foss-sunrui
	 * @date 2013-1-8 下午1:13:34
	 * @return
	 * @see
	 */
	private OrgAdministrativeInfoEntity getLoginDept() {
		OrgAdministrativeInfoEntity org = null;
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		if(user!=null){
			EmployeeEntity emp = (EmployeeEntity)user.getEmployee();
			if(emp!=null){
				org = emp.getDepartment();
			}
		}
		return org;
	}

}
