/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/controller/AbstractState.java
 * 
 * FILE NAME        	: AbstractState.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.ui.controller;

import java.awt.CardLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.MessagePanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.TransportPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.UploadVoucherPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.ITransterInject;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.ReturnRecordPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransferChangedInfoPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransferChangedRecordPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransferRecordPanel;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadVoucherDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 变更单页面状态抽象类
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午8:00:31
 */
public abstract class AbstractState implements IWaybillRfcState{
	/**
	 * 参数配置
	 */
	private IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);

	/**
	 *  状态控制类
	 */
	private WaybillRfcStateContext context;
	
	/**
	 * 布局接口
	 */
	private IWaybillRfcUILayout rfcUILayout;
	
	/**
	 * UI状态
	 */
	private IWaybillRfcUIState rfcUIState;
	
	// 更改单Service
	private IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	/**
	 * 无需更新可编辑状态的组件类型
	 */
	@SuppressWarnings("rawtypes")
	private Class[] clazzs = new Class[]{
			ReturnRecordPanel.class,
			TransferRecordPanel.class,
			TransferChangedInfoPanel.class,
			TransferChangedRecordPanel.class
	};

	/**
	 * 
	 * AbstractState
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:14:38
	 */
	public AbstractState(WaybillRfcStateContext context) {
		this.context = context;
	}

	/**
	 * 
	 * 更新UI可用状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 下午2:19:00
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.IWaybillRfcState#updateComponentEnable()
	 */
	@Override
	public void performComponentsState() {
		if(rfcUIState != null){
			rfcUIState.setComponentsState();
		}
		
		WaybillRFCUI rfcUI = getWaybillRfcUI();
		
		//提交、打印按钮
		getWaybillRfcUI().getButtonPanel().getBtnSubmit().setEnabled(false);
		getWaybillRfcUI().getButtonPanel().getBtnPrint().setEnabled(false);
		//上传凭证按钮
		getWaybillRfcUI().getUploadVoucherPanel().getBtnAdd().setEnabled(false);
		getWaybillRfcUI().getUploadVoucherPanel().getBtnDelete().setEnabled(false);
		getWaybillRfcUI().getUploadVoucherPanel().getTable().setEnabled(false);
		getWaybillRfcUI().getUploadVoucherPanel().getTable().updateUI();
		
		disableUI(rfcUI.getWaybillInfoPanel());
		disableUI(rfcUI.getMessagePanel());
		disableUI(rfcUI.getUploadVoucherPanel());
		IBinder<WaybillInfoVo>  binder=rfcUI.getBinder();
		
		 
		 
		 
		updateComponents(rfcUI);
		WaybillInfoVo   waybillInfoVo =binder.getBean();
		
		if(waybillInfoVo!=null&&waybillInfoVo.getCustomerPickupOrgCode()!=null&&waybillInfoVo.getCustomerPickupOrgCode().getCityCode()!=null&&waybillInfoVo.getReceiveOrgProvCode()!=null)
		{
			 String _HK_PROV = "810000";

			 String _HK_CITY = "810100";
			try{
			 ConfigurationParamsEntity cityHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_CITY_HK_CODE);//城市
			 ConfigurationParamsEntity proHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_PROV_HK_CODE);//省份
			 
			 if(cityHKCode!=null&&StringUtils.isNotEmpty(cityHKCode.getConfValue()))
			 {
				 _HK_CITY= cityHKCode.getConfValue();
			 }
			 
			 if(proHKCode!=null&&StringUtils.isNotEmpty(proHKCode.getConfValue()))
			 {
				 _HK_PROV= proHKCode.getConfValue();
			 }
			 
			}catch(Exception e)
			{
				// do Nothing
			}
			
			/**
			 * 获取目的站所在城市，如果是香港，设置代收代收货款和装卸费不可录入
			 */
			if (waybillInfoVo.getCustomerPickupOrgCode().getCityCode() != null && _HK_CITY.equals(waybillInfoVo.getCustomerPickupOrgCode().getCityCode())) {
				setCanAgentCollectedOrServiceFeeFalse(rfcUI,waybillInfoVo);

				/**
				 * 由于发货部门能取到省份，可以用省份来进行判断
				 */
			} else if (waybillInfoVo.getReceiveOrgProvCode() != null && _HK_PROV.equals(waybillInfoVo.getReceiveOrgProvCode())) {
				setCanAgentCollectedOrServiceFeeFalse(rfcUI,waybillInfoVo);
			}
			// 设置装卸费是否可编辑(此逻辑和计算完总运费设置装卸费逻辑一致)
			setServiceChargeState(rfcUI,waybillInfoVo);
		}
	}
	
	/**
	 * 设置是否允许修改装卸费
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 */
	private boolean setServiceChargeState(WaybillRFCUI rfcUI,WaybillPanelVo bean) {

		// 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
		// （只限制配载类型为专线的，包括月结）；
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10.如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
		 */

		boolean serviceChargeEnabled = true;
		
		
//		// 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
//		serviceChargeEnabled = departPropertyServiceFee(bean);
//
//		// 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
//		if (serviceChargeEnabled) {
//			serviceChargeEnabled = lowestServiceFee(bean);
//		}
//
//		// 2012年12月1日 (以后)开业的部门不能开装卸费
//
//		// 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
//		// 月发越送 == 月结
//		if (serviceChargeEnabled) {
//			serviceChargeEnabled = channelServiceFee(bean);
//		}
//		
//		if (serviceChargeEnabled) {
//		// 月发月送允许修改装卸费
//			if (StringUtils.isNotEmpty(bean.getPreferentialType())) {
//				if (bean.getPreferentialType()
//						.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
//					serviceChargeEnabled = true;
//				}
//
//			}
//		}

		//快递只允许对应部门的月结客户开装卸费
		CusBargainVo vo=new CusBargainVo();
		vo.setExpayway(WaybillConstants.MONTH_END);
		vo.setCustomerCode(bean.getDeliveryCustomerCode());
		vo.setBillDate(new Date());
		vo.setBillOrgCode(bean.getReceiveOrgCode());
		/*boolean  isOk = waybillService.isCanPaidMethodExp(vo);
		if(!isOk){
			serviceChargeEnabled = false ;
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas(String.valueOf('0'));
		}else{
			serviceChargeEnabled = true;
		}			
		
		if (!serviceChargeEnabled) {
			bean.setServiceFee(BigDecimal.ZERO);
		}*/

		rfcUI.incrementPanel.getTxtServiceCharge().setEnabled(false);
		return false;
	}
	
	/**
	 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {
	
		// 收货部门
		String orgCode = bean.getReceiveOrgCode();
		
		// org code is not null;
		if(StringUtils.isNotEmpty(orgCode)){
			// 根据编码查询
			SaleDepartmentEntity entity = waybillService.querySaleDeptByCode(orgCode);
			if(entity!=null){
				//不允许开装卸费
				if(!FossConstants.YES.equals(entity.getCanPayServiceFee())){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
	 * @author WangQianJin
	 * @date 2013-6-13 下午9:49:14
	 */
	private boolean lowestServiceFee(WaybillPanelVo bean) {
		BigDecimal minTransportFee = bean.getMinTransportFee();
		BigDecimal transportFee = bean.getTransportFee();
		boolean serviceChargeEnabled = true;
		if (!bean.getIsWholeVehicle() && minTransportFee != null) {

			// 最低一票 装卸费=0
			if (transportFee.compareTo(minTransportFee) == 0) {
				bean.setServiceFee(BigDecimal.ZERO);
				serviceChargeEnabled = false;
			}
		}

		return serviceChargeEnabled;
	}
	/**
	 * 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * @param bean
	 * @return
	 */
	private boolean channelServiceFee(WaybillPanelVo bean) {

		String channel = bean.getOrderChannel();
		// 阿里巴巴或者阿里巴巴诚信通
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(channel)||WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT.equals(channel)) {
			return false;
		}
		// 月发月结
		/*if (bean.getChargeMode() != null) {
			if (bean.getChargeMode()) {
				return false;
			}
		}*/
		return true;
	}
	
	/**
	 * 
	 * 限制代收货款和装卸费的录入
	 * @param bean
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午11:08:46
	 */
	private void setCanAgentCollectedOrServiceFeeFalse(WaybillRFCUI rfcUI ,WaybillPanelVo bean)
	{
		rfcUI.getWaybillInfoPanel().getIncrementPanel().getTxtCashOnDelivery().setEnabled(false);//收代收货款不可修改
		rfcUI.getWaybillInfoPanel().getIncrementPanel().getCombRefundType().setEnabled(false);//退款类型
		rfcUI.getWaybillInfoPanel().getIncrementPanel().getTxtServiceCharge().setEnabled(false);//装卸费不可修改
	}
	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	
	/**
	 * 
	 * 更新组件
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:00:03
	 */
	abstract protected void updateComponents(WaybillRFCUI rfcUI);



	/**
	 * 
	 * 设置组件不可编辑
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:59:13
	 */
	public void disableUI(Component comp) {
		UIUtils.disableUI(comp, clazzs);
	}

	/**
	 * 
	 * 设置组件可编辑
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:59:13
	 */
	public void enableUI(Component comp) {
		UIUtils.enableUI(comp, clazzs);
	}


	/**
	 * 
	 * 切换更改单布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 下午2:19:15
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.IWaybillRfcState#showRfcTypeLayout()
	 */
	@Override
	public void performLayout() {
		if(rfcUILayout != null){
			rfcUILayout.showLayout();
		}
		
		WaybillRFCUI rfcUI = getWaybillRfcUI();
		if(rfcUI == null)
			return;
		showTransferLayout(rfcUI.getWaybillInfoPanel().getTransferPanel());
		showMessageInfoLayout(rfcUI.getMessagePanel());
		showUploadVoucherLayout(rfcUI.getUploadVoucherPanel());
		
	}

	/**
	 * 
	 * 运输信息布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 上午10:03:56
	 */
	private void showTransferLayout(TransportPanel transferPanel){
		CardLayout cardLayout = (CardLayout) transferPanel.getLayout();
		ITransterInject transterInject = (ITransterInject)getTransferInjectPanel();
		if(transterInject == null)
			return;
		cardLayout.show(transferPanel, transterInject.getClass().getName());
		transterInject.injectTransportPanel(transferPanel.getTransportInfoPanel());
	}
	
	/**
	 * 
	 * 获取转运面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 下午2:19:29
	 */
	protected JComponent getTransferInjectPanel(){
		return null;
	}	
	
	/**
	 * 
	 * 获取消息面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 下午2:19:54
	 */
	protected JComponent getMessageInjectPanel(){
		return null;
	}
	
	/**
	 * 
	 * 变更信息布局
	 * @author 102246-foss-shaohongliang
	 * @param messagePanel 
	 * @date 2012-11-22 上午9:54:33
	 */
	private void showMessageInfoLayout(MessagePanel messagePanel){
		CardLayout cardLayout = (CardLayout) messagePanel.getLayout();
		JComponent injectPanel = getMessageInjectPanel();
		if(injectPanel == null)
			return;
		String currentLayoutName = injectPanel.getClass().getName();
		cardLayout.show(messagePanel, currentLayoutName);
		messagePanel.setCurrentLayoutName(currentLayoutName);
	}
	
	/**
	 * 
	 * 凭证布局
	 * @author 102246-foss-shaohongliang
	 * @param uploadVoucherPanel 
	 * @date 2012-11-22 上午9:54:59
	 */
	private void showUploadVoucherLayout(UploadVoucherPanel uploadVoucherPanel){
		List<UploadVoucherDto> uploadVoucherList = getUploadVoucherDtos();
		if(uploadVoucherList == null)
			return;
		uploadVoucherPanel.setUploadVoucherList(uploadVoucherList);
	
	}
	
	/**
	 * 
	 * 凭证集合
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 下午2:20:15
	 */
	protected List<UploadVoucherDto> getUploadVoucherDtos(){
		return null;
	}
	/**
	 * 
	 * 更改单UI
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-22 上午9:54:59
	 */
	protected WaybillRFCUI getWaybillRfcUI(){
		if(context == null)
			return null;
		return context.getWaybillRFCUI();
	};
	
}