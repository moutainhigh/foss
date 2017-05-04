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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/action/ChangeLabelGoodsAction.java
 *  
 *  FILE NAME          :ChangeLabelGoodsAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IChangeLabelGoodsService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ChangeLabelGoodsVo;

/**
 * 查询重贴标签货物Action
 * @author ibm-zhangyixin
 * @date 2012-11-26 下午7:56:20
 */
public class ChangeLabelGoodsAction extends AbstractAction {
	private static final long serialVersionUID = -102872969579795236L;

	/**
	 * 重贴标签货物vo
	 */
	private ChangeLabelGoodsVo changeLabelGoodsVo = new ChangeLabelGoodsVo();
	/**
	 * 查询重贴标签货物Service
	 */
	private IChangeLabelGoodsService changeLabelGoodsService;
	
	/**记录打印标签操作Service**/
	private IPrintLabelService printLabelService;

	/**
	 * 查询重贴标签货物-卸车
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午11:31:53
	 */
	@JSON
	public String queryChangeLabelGoodsUnload() {
		try {
			 //查询重贴标签货物-卸车查询tab中的查询
			List<ChangeLabelGoodsDto> changeLabelGoodsDtos = changeLabelGoodsService
					.queryChangeLabelGoodsUnload(changeLabelGoodsVo.getChangeLabelGoodsDto(),
							this.getLimit(), this.getStart());
			//查询的总记录数
			Long totCount = changeLabelGoodsService.getTotCountUnload(changeLabelGoodsVo.getChangeLabelGoodsDto());
			changeLabelGoodsVo.setChangeLabelGoodsList(changeLabelGoodsDtos);
			this.setTotalCount(totCount);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 查询重贴标签货物-清仓
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午11:31:31
	 */
	@JSON
	public String queryChangeLabelGoodsStock() {
		try {
			//查询重贴标签货物-清仓查询tab中的查询
			List<ChangeLabelGoodsDto> changeLabelGoodsDtos = changeLabelGoodsService
					.queryChangeLabelGoodsStock(changeLabelGoodsVo.getChangeLabelGoodsDto(),
							this.getLimit(), this.getStart());
			//查询的总记录数
			Long totCount = changeLabelGoodsService.getTotCountStock(changeLabelGoodsVo.getChangeLabelGoodsDto());
			changeLabelGoodsVo.setChangeLabelGoodsList(changeLabelGoodsDtos);
			this.setTotalCount(totCount);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 重新打印标签
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午11:31:14
	 */
	@JSON
	public String printChangeLabelGoodsAgain() {
		try {
			//获取打印标签需要的数据
			List<BarcodePrintLabelDto> barcodePrintLabelList = changeLabelGoodsService.printChangeLabelGoodsAgain(changeLabelGoodsVo.getChangeLabelGoodsDto());
			changeLabelGoodsVo.setBarcodePrintLabelList(barcodePrintLabelList);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 
	 * @Title: updateChangeLabelGoods 
	 * @Description: 打印完标签后更新数据库中的状态
	 * @return    
	 * @return String    返回类型 
	 * updateChangeLabelGoods
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-28上午10:22:55
	 */
	@JSON
	public String updateChangeLabelGoods() {
		try {
			ChangeLabelGoodsDto changeLabelGoodsDto = changeLabelGoodsVo.getChangeLabelGoodsDto();
			PrintLabelEntity printLabelEntity = new PrintLabelEntity();
			printLabelEntity.setWaybillNo(changeLabelGoodsDto.getWaybillNo());
			printLabelEntity.setSerialNo(changeLabelGoodsDto.getSerialNo());
			printLabelEntity.setPrintTime(new Date());
			printLabelEntity.setPrintUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			printLabelEntity.setPrintUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			printLabelService.addPrintLabel(printLabelEntity);
			changeLabelGoodsService.updateChangeLabelGoods(changeLabelGoodsDto);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 设置 重贴标签货物vo.
	 *
	 * @param changeLabelGoodsVo the new 重贴标签货物vo
	 */
	public void setChangeLabelGoodsVo(ChangeLabelGoodsVo changeLabelGoodsVo) {
		this.changeLabelGoodsVo = changeLabelGoodsVo;
	}

	/**
	 * 设置 查询重贴标签货物Service.
	 *
	 * @param changeLabelGoodsService the new 查询重贴标签货物Service
	 */
	public void setChangeLabelGoodsService(
			IChangeLabelGoodsService changeLabelGoodsService) {
		this.changeLabelGoodsService = changeLabelGoodsService;
	}

	/**
	 * 获取 重贴标签货物vo.
	 *
	 * @return the 重贴标签货物vo
	 */
	public ChangeLabelGoodsVo getChangeLabelGoodsVo() {
		return changeLabelGoodsVo;
	}

	/**   
	 * @param printLabelService the printLabelService to set
	 * Date:2013-6-29下午3:38:44
	 */
	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}
}