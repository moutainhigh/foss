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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/AbandonedGoodsQueryAction.java
 * 
 * FILE NAME        	: AbandonedGoodsQueryAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI;
import com.deppon.foss.module.pickup.creating.client.vo.AbandonedGoodsTypeVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * <p>
 * 弃货导入开单查询
 * </p>
 * @title AbandonedGoodsQueryAction.java
 * @package com.deppon.foss.module.pickup.creating.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class AbandonedGoodsQueryAction extends AbstractButtonActionListener<AbandonedGoodsHandleUI> {
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	private AbandonedGoodsHandleUI ui;
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(AbandonedGoodsQueryAction.class);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ui.getBtnQuery()){
			/**
			 *  查询按钮执操作
			 */
			List<AbandonedGoodsDto> abandonedGoodsDto = new ArrayList<AbandonedGoodsDto>() ;
			try{
				AbandonedGoodsCondition  condition = getCondition();
				abandonedGoodsDto = waybillService.queryAbandonedGoodsDtoList(condition);
				if(abandonedGoodsDto == null || abandonedGoodsDto.isEmpty()){
					MsgBox.showInfo(i18n.get("foss.gui.creating.abandonedGoodsQueryAction.messageDialog.noInfo"));
					//清空表格数据
					ui.getTableModel().setAbandonedGoodsList(new ArrayList());
					ui.getTableModel().fireTableDataChanged();
					return;
				}
			}catch(BusinessException ex){
				MsgBox.showInfo(MessageI18nUtil.getMessage(ex, i18n));
			}
			
			ui.getTableModel().setAbandonedGoodsList(abandonedGoodsDto);
			ui.getTableModel().fireTableDataChanged();
		}else if(e.getSource() == ui.getBtnReset()){
			/**
			 * 重置按钮操作
			 */
			ui.getBeginDate().setDate(new Date());
			ui.getEndDate().setDate(new Date());
			ui.getWaybillNo().setText("");
			ui.getExceptionType().setSelectedItem("");
			ui.getShipper().setText("");
			ui.getPreTreatPerson().setText("");
		}
	
	}
	/**
	 * 获得UI查询条件的值并封装
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @return AbandonedGoodsCondition
	 */
	public AbandonedGoodsCondition getCondition(){
		AbandonedGoodsCondition condition = new AbandonedGoodsCondition();
		condition.setWaybillNo(ui.getWaybillNo().getText());
		condition.setShipper(ui.getShipper().getText());
		condition.setExceptionType(((AbandonedGoodsTypeVo)ui.getExceptionType().getSelectedItem()).getExceptionCode());
		condition.setPreTreatPerson(ui.getPreTreatPerson().getText());
		/**
		 * 从开始时间的00:00:00到结束时间的23:59:59
		 */
		Date beginDate=DateUtils.getStartDatetime(ui.getBeginDate().getDate());
		Date endDate=DateUtils.getEndDatetime(ui.getEndDate().getDate());
		condition.setBeginDate(beginDate);
		condition.setEndDate(endDate);		
		return condition;
	}
	
	@Override
	public void setIInjectUI(AbandonedGoodsHandleUI ui) {
		this.ui= ui;
	}

}