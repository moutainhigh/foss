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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/LabeledGoodHessianRemoting.java
 * 
 * FILE NAME        	: LabeledGoodHessianRemoting.java
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
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ILabeledGoodHessianRemoting;

/**
 * 通过运单号查询所有流水号
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-14 下午7:52:40,content:TODO
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-14 下午7:52:40
 * @since
 * @version
 */
@Remote
public class LabeledGoodHessianRemoting implements ILabeledGoodHessianRemoting{

    @Resource
    private ILabeledGoodService labeledGoodService;
    
    @Resource
    private ILabeledGoodPDAService labeledGoodPDAService;

    /**
     * 
     * <p>
     * 通过运单号查询所有流水号
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    @Override
    public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
       return labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
    }

	@Override
	public List<LabeledGoodPDAEntity> querySerialByByWaybillNo(String waybillNo) {
		return labeledGoodPDAService.queryByWaybillNo(waybillNo);
	}

	@Override
	public void deleteLabByWaybillNos(List<String> waybillNos) {
		labeledGoodService.deleteLabByWaybillNos(waybillNos);
		
	}
	
	@Override
	public void deleteLabByWaybillNo(String waybillNo) {
		labeledGoodService.deleteLabByWaybillNo(waybillNo);
		
	}
	
    
    
}