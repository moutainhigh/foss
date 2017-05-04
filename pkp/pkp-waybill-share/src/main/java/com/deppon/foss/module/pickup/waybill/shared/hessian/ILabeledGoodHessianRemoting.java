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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/ILabeledGoodHessianRemoting.java
 * 
 * FILE NAME        	: ILabeledGoodHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;


/**
 * 通过运单号查询所有流水号
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-jiangfei,date:2012-11-14 下午7:47:19, </p>
 * @author foss-jiangfei
 * @date 2012-11-14 下午7:47:19
 * @since
 * @version
 */
public interface ILabeledGoodHessianRemoting extends IService{
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
	List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) ;
	/**
	 * 按照运单号查询暂存流水号
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-15 下午7:13:59
	 */
	List<LabeledGoodPDAEntity> querySerialByByWaybillNo(String waybillNo);
	void deleteLabByWaybillNos(List<String> waybillNos);
	void deleteLabByWaybillNo(String waybillNo);
}