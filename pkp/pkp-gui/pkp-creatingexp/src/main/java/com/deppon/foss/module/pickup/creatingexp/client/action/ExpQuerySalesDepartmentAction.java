/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpSalesDepartmentDialog;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQuerySalesDepartmentAction  implements IButtonActionListener<ExpWaybillEditUI> {

	// 主界面
	ExpWaybillEditUI ui;
	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();

		
		if(bean==null){
			return;
		}
		try {
			//弹出查询收货部门对话框
			ExpSalesDepartmentDialog dialog = new ExpSalesDepartmentDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			//获得收获部门信息
			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			
			if(entity==null){
				return;
			}
			
			//收货部门发生改变，则设置是否手动修改为是。（参见DEFECT-543）
			if(!bean.getReceiveOrgName().equals(entity.getName())){
				bean.setHandInsuranceFee(false);
			}
			
			//根据DEFECT-2792修改，修改收入部门需要重新计算总运费
			if(entity.getCode()!=null && !entity.getCode().equals(bean.getReceiveOrgCode())){
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
	    		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
			}
			
			//设置收获部门信息
			//ExpCommon.setSalesDepartmentForCentrial(entity, bean, ui);
			bean.setReceiveOrgCode(entity.getCode());
			// 收货部门名称
			bean.setReceiveOrgName(entity.getName());
			// 设置创建时间
			bean.setReceiveOrgCreateTime(entity.getOpeningDate());
			//当前部门是否存在活动
			boolean flag = waybillService.isExistSpecialOffer(entity.getCode(),new Date());
//			ui.getBasicPanel().getCboSpecialOffer().setEnabled(flag);
			
			DefaultComboBoxModel combSpecialOffer = new DefaultComboBoxModel();//开单付款方式 =
			DataDictionaryValueVo emptyvo = new DataDictionaryValueVo();
			emptyvo.setId("");
			emptyvo.setValueCode("");
			emptyvo.setValueName("");
			if(null!=bean.getSpecialOffer()){
				bean.setSpecialOffer(emptyvo);
			}
			combSpecialOffer.addElement(emptyvo);
			//根据收货部门和当前时间查询并封装活动类型枚举
			Date date = waybillBinder.getBean().getBillTime();
			if(null == date){
				date = new Date();
			}
			List<CityMarketPlanEntity> cityMarketPlanEntityList = waybillService.searchCityMarketPlanEntityList(entity.getCode(), date);
			if(CollectionUtils.isNotEmpty(cityMarketPlanEntityList)){
				for(CityMarketPlanEntity cityMarketPlanEntity:cityMarketPlanEntityList){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					vo.setId(cityMarketPlanEntity.getId());
					vo.setValueCode(cityMarketPlanEntity.getCode());
					vo.setValueName(cityMarketPlanEntity.getName());
					combSpecialOffer.addElement(vo);
				}
			}

//			ui.basicPanel.getCboSpecialOffer()
//					.setModel(combSpecialOffer);
			//活动类型model改变后的监听
		    ItemListener itemListener = new ItemListener() {
		        public void itemStateChanged(ItemEvent itemEvent) {
		    		//修改完是否上门接货不能立即提交需要再次计算运费
		    		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		    		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
		        }
		      };
//		      ui.basicPanel.getCboSpecialOffer().addItemListener(itemListener);
			
			
			/**
			 * 当导入的PDA运单没有收货部门时，需要手工设置收货部门
			 * 当手动设置收货部门时，系统初始化产品类型，
			 * 设置产品类型时，同时需要自动设置提货方式
			 */
			// 根据运输性质的改变，改变提货方式
			//ExpCommon.changePickUpMode(bean, ui);
			//当收货部门 不为空，且走货线路不为空时，需要重新根据新的出发部门和到达部门设置走货线路
			if(entity != null && StringUtils.isNotEmpty(bean.getLoadLineName())){
				// 整车的提货网点不需要设置线路和空运配载及时效
				if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
					// 提货网点
					ExpShowPickupStationDialogAction pickupStationAction = new ExpShowPickupStationDialogAction();
					pickupStationAction.setInjectUI(ui);
					// 设置线路
					pickupStationAction.setLoadLine(bean);
					// 设置空运配载
					pickupStationAction.setAirDeptEnabled(bean);
				}
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
