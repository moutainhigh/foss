/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/vo/ChangeLabelGoodsVo.java
 *  
 *  FILE NAME          :ChangeLabelGoodsVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsPrintDto;

/**
 * 重贴标签货物Vo
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:47:19
 */
public class ChangeLabelGoodsVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 631138377386846050L;
	
	/****
	 * 打印标签数据
	 */
	private ChangeLabelGoodsPrintDto changeLabelGoodsPrintDto = new ChangeLabelGoodsPrintDto();
	
	/****
	 * 重贴标签货物dto
	 */
	private ChangeLabelGoodsDto changeLabelGoodsDto = new ChangeLabelGoodsDto();
	
	/****
	 * 重贴标签货物dto集合
	 */
	private List<ChangeLabelGoodsDto> changeLabelGoodsList = new ArrayList<ChangeLabelGoodsDto>();
	
	/****
	 * 打印重贴标签dto集合
	 */
	private List<BarcodePrintLabelDto> barcodePrintLabelList = new ArrayList<BarcodePrintLabelDto>();
	
	/**
	 * 获取 ** 重贴标签货物dto.
	 *
	 * @return the ** 重贴标签货物dto
	 */
	public ChangeLabelGoodsDto getChangeLabelGoodsDto() {
		return changeLabelGoodsDto;
	}
	
	/**
	 * 设置 ** 重贴标签货物dto.
	 *
	 * @param changeLabelGoodsDto the new ** 重贴标签货物dto
	 */
	public void setChangeLabelGoodsDto(ChangeLabelGoodsDto changeLabelGoodsDto) {
		this.changeLabelGoodsDto = changeLabelGoodsDto;
	}
	
	/**
	 * 获取 ** 重贴标签货物dto集合.
	 *
	 * @return the ** 重贴标签货物dto集合
	 */
	public List<ChangeLabelGoodsDto> getChangeLabelGoodsList() {
		return changeLabelGoodsList;
	}
	
	/**
	 * 设置 ** 重贴标签货物dto集合.
	 *
	 * @param changeLabelGoodsList the new ** 重贴标签货物dto集合
	 */
	public void setChangeLabelGoodsList(
			List<ChangeLabelGoodsDto> changeLabelGoodsList) {
		this.changeLabelGoodsList = changeLabelGoodsList;
	}
	
	/**
	 * 获取 ** 打印标签数据.
	 *
	 * @return the ** 打印标签数据
	 */
	public ChangeLabelGoodsPrintDto getChangeLabelGoodsPrintDto() {
		return changeLabelGoodsPrintDto;
	}
	
	/**
	 * 设置 ** 打印标签数据.
	 *
	 * @param changeLabelGoodsPrintDto the new ** 打印标签数据
	 */
	public void setChangeLabelGoodsPrintDto(
			ChangeLabelGoodsPrintDto changeLabelGoodsPrintDto) {
		this.changeLabelGoodsPrintDto = changeLabelGoodsPrintDto;
	}
	
	/**
	 * 获取 ** 打印重贴标签dto集合.
	 *
	 * @return the ** 打印重贴标签dto集合
	 */
	public List<BarcodePrintLabelDto> getBarcodePrintLabelList() {
		return barcodePrintLabelList;
	}
	
	/**
	 * 设置 ** 打印重贴标签dto集合.
	 *
	 * @param barcodePrintLabelList the new ** 打印重贴标签dto集合
	 */
	public void setBarcodePrintLabelList(
			List<BarcodePrintLabelDto> barcodePrintLabelList) {
		this.barcodePrintLabelList = barcodePrintLabelList;
	}
}