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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/LabelPrintInfoHessianRemoting.java
 * 
 * FILE NAME        	: LabelPrintInfoHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ILabelPrintInfoHessianRemoting;
@Remote
public class LabelPrintInfoHessianRemoting implements
	ILabelPrintInfoHessianRemoting {

    @Resource
    private ILabelPrintInfoService labelPrintInfoService;

    
    public ILabelPrintInfoService getLabelPrintInfoService() {
        return labelPrintInfoService;
    }

    
    public void setLabelPrintInfoService(
    	ILabelPrintInfoService labelPrintInfoService) {
        this.labelPrintInfoService = labelPrintInfoService;
    }

    @Override
    public BarcodePrintLabelDto getLabelPrintInfos(String waybillNo,String waybillStatus) {
	BarcodePrintLabelDto barcodePrintLabelDto = labelPrintInfoService.getLabelPrintInfos(waybillNo, null, waybillStatus);
	return barcodePrintLabelDto;
    }
    
    @Override
    public List<BarcodePrintLabelDto> getLabelPrintInfo(String waybillNo, List<String> serialNos) {
    	 List<BarcodePrintLabelDto> barcodePrintLabelDto = labelPrintInfoService
		.getLabelPrintInfoForDepart(waybillNo, serialNos);
	return barcodePrintLabelDto;
    }


	@Override
	public String isExistDestinationAndTodoRfc(WaybillEntity waybillEntity,
			List<String> serialNos) {
		return labelPrintInfoService.isExsitWaybillRfcDestinationAndHandlerNotTodo(waybillEntity, serialNos);
	}


	@Override
	public List<BarcodePrintLabelDto> getLabelPrintInfoReceAndStockAndArri(
			String waybillNo, List<String> serialNos) {
		return labelPrintInfoService.getLabelPrintInfoReceAndStockAndArri(waybillNo, serialNos);
	}


	/**
	 * 判断是否为快递单
	 * @author 张兴旺
	 * @date 2013-10-1 20:43:40
	 */
	@Override
	public boolean queryIsExpressBill(String waybillNo) {
		return labelPrintInfoService.queryIsExpressBill(waybillNo);
	}

	@Override
	public String getWaybillDocAd(String receiverOgCode, String cityPattern) {
		return labelPrintInfoService.getWaybillDocAd(receiverOgCode, cityPattern);
	}

	/**
	 * 加入走货路径需要进行相关数据的封装
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-30 20:03:30
	 * @param eWaybillBillDto
	 */
	@Override
	public List<EWaybillPrintDto> printEWaybillInfos(String waybillNo, List<String> serialNoList) {
		return labelPrintInfoService.printEWaybillInfos(waybillNo, serialNoList);
	}

	/**
	 * 判定是否电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-27 20:39:30
	 */
	@Override
	public boolean isEWaybillInfoByWaybillNo(String waybillNo) {
		return labelPrintInfoService.isEWaybillInfoByWaybillNo(waybillNo);
	}

	/**
	 * 获取快递标签打印优化信息远程接口
	 * @param waybillNo
	 * @param waybillstatus
	 * @author foss-218438
	 * @return
	 */
	@Override
	public BarcodePrintLabelDto getCommonLabelPrintInfoExpress(String waybillNo,String waybillstatus) {
		return labelPrintInfoService.getCommonLabelPrintInfoExpress(waybillNo,null,waybillstatus);
	}
}