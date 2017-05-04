package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 查询收货部门
 * @author WangQianJin
 * @date 2013-7-17 下午2:51:25
 */
public class QueryReceiveOrgAction implements IButtonActionListener<CalculateCostsDialog>{
	
        // 主界面
        CalculateCostsDialog ui;
        
        @Override
        public void actionPerformed(ActionEvent e) {
        
        	HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
        	IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
        	WaybillPanelVo bean = waybillBinder.getBean();
        
        	try {
        		
        		//弹出查询收货部门对话框
        		ReceiveOrgDialog dialog = new ReceiveOrgDialog();
        		// 剧中显示弹出窗口
        		WindowUtil.centerAndShow(dialog);
        		//获得收获部门信息
        		SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();        		
        		//设置收获部门信息        		
        		Common.setSalesDepartmentForSimple(entity,bean,ui);
        		// 根据运输性质的改变，改变提货方式
        		Common.changePickUpModeForSimple(bean,ui);       		
        		
        	} catch (WaybillValidateException w) {
        		MsgBox.showInfo(w.getMessage());
        	} catch (BusinessException ex) {
        		if (!"".equals(ex.getMessage())) {
        			MsgBox.showInfo(ex.getMessage());
        		}
        	}
        }
        
        @Override
        public void setInjectUI(CalculateCostsDialog ui) {
        	this.ui = ui;
        }
        
}
