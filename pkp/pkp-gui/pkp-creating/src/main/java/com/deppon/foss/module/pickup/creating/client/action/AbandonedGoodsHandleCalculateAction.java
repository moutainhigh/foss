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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/AbandonedGoodsHandleCalculateAction.java
 * 
 * FILE NAME        	: AbandonedGoodsHandleCalculateAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI;
import com.deppon.foss.module.pickup.creating.client.vo.AbandonedGoodsModel;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;

/**
 * 
 * <p>
 * 导入期货总重量、总体积、总件数计算<br />
 * </p>
 * @title AbandonedGoodsHandleCalculateAction.java
 * @package com.deppon.foss.module.pickup.creating.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class AbandonedGoodsHandleCalculateAction implements ChangeListener {
	/**
	 * 界面
	 */
	private	AbandonedGoodsHandleUI ui;
	/**
	 * 构造方法
	 * @param ui
	 */
	public AbandonedGoodsHandleCalculateAction(
			AbandonedGoodsHandleUI ui) {
		this.ui = ui;//设置ui
	}
	
	/**
	 * 总重量、总体积、总件数计算
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param abandonedGoodsDtos
	 * @return void
	 * @see
	 */
	public void calculateInfo(List<AbandonedGoodsDto> abandonedGoodsDtos){
		BigDecimal totalWeight= BigDecimal.ZERO;
		//Don't create instances of already existing BigInteger and BigDecimal ZERO
		BigDecimal totalVolume = BigDecimal.ZERO;
		//性能 - 方法调用低效的数字构造方法;使用静态valueOf代替   
		Integer totalPieces =  Integer.valueOf(0);
		for(AbandonedGoodsDto dto : abandonedGoodsDtos){
			totalWeight = totalWeight.add(dto.getWeight());
			totalVolume = totalVolume.add(dto.getVolume());
			totalPieces += dto.getPieces();
		}
		/**
		 * 将计算结果设置到标签
		 */
		ui.getTotalWeight().setText(totalWeight.toString());
		ui.getTotalVolume().setText(totalVolume.toString());
		ui.getTotalPieces().setText(totalPieces.toString());
	}

	/**
	 * 状态更改时事件
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param e
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		AbandonedGoodsModel tableModel = (AbandonedGoodsModel)e.getSource();
		List<AbandonedGoodsDto> abandonedGoodsDtos = tableModel.getSelectedValues();
		calculateInfo(abandonedGoodsDtos);
	}

}