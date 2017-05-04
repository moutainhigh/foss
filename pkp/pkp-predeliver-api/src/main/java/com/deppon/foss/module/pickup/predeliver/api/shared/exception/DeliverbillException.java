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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/DeliverbillException.java
 * 
 * FILE NAME        	: DeliverbillException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 派送单业务异常
 * 
 * @author ibm-
 * 		wangxiexu
 * @date 2012-10-29 
 * 		下午3:18:34
 */
public class DeliverbillException extends BusinessException {

	/**
	 * 排单件数小于0
	 */
	public static final String ARRANGEGOODSQTY_LTZERO = "pkp.predeliver.deliverBill.arrangeGoodsQty.LtZero";
	/**
	 * 更新派送单明细异常
	 */
	public static final String UPDATEDELIVERBILL_ERROR = "pkp.predeliver.deliverBill.updateDeliverBill.error";
	/**
	 * 更新车载信息异常
	 */
	public static final String UPDATECAR_ERROR = "pkp.predeliver.deliverBill.updateCar.error";
	/**
	 * 更新车辆任务状态异常
	 */
	public static final String UPDATECARTASK_ERROR = "pkp.predeliver.deliverBill.updateCarTask.error";
	/**
	 * 更新车辆任务状态异常
	 */
	public static final String UPDATEDELIVERSTATE_ERROR = "pkp.predeliver.deliverBill.updateDeliverState.error";
	/**
	 * 更新车辆任务状态异常
	 */
	public static final String CANCELDELIVERBILL_ERROR = "pkp.predeliver.deliverBill.cancelDeliverBill.error";
	/**
	 * 无效的派送单状态
	 */
	public static final String DELIVERSTATE_INVALID = "pkp.predeliver.deliverBill.deliverState.invalid";
	/**
	 * 无法找到派送单
	 */
	public static final String DELIVERBILL_NOTFOUND = "pkp.predeliver.deliverBill.deliverBill.notFound";
	/**
	 * 当前运单操作中，请稍后再试
	 */
	public static final String WAYBILL_LOCKED = "pkp.predeliver.deliverBill.waybill.locked";
	/**
	 * 当前派送单操作中，请稍后再试
	 */
	public static final String DELIVERBILL_LOCKED = "pkp.predeliver.deliverBill.deliverbill.locked";
	/**
	 * 无法找到承运信息
	 */
	public static final String ACTUALFREIGHT_NOT_FOUND_ERROR = "pkp.predeliver.deliverBill.actualFreightNotFound.error";
	
	/**
	 * 无法找到派送单明细
	 */
	public static final String DELIVERBILLDELETE_NOTFOUND = "pkp.predeliver.deliverBill.deliverBilldelete.notFound";
	/**
	 * 不可删除全部派送单明细
	 */
	public static final String DELIVERBILLDETAL_CANT_DELETE = "pkp.predeliver.deliverBill.deliverBillDetail.cantDelete";
	/**
	 * 没有中转外场
	 */
	public static final String TRANSFERCENTER_ERROR = "pkp.predeliver.deliverBill.transferCenter.error";
	/**
	 * 当前运单号无法进行排单，请检查！	
	 */
	public static final String DELIVERBILL_CANT_OPERATE = "pkp.predeliver.editDeliverbillIndex.deliverbillcantoperate";
	/**
	 * 当前派送单不可进行修改 请关闭当前页面后再次查询进入！
	 */
	public static final String DELIVERBILLSTATE_CANT_OPERATE = "pkp.predeliver.editDeliverbillIndex.deliverbillstatecantoperate";
	/**
	 * 当前运单未补录，不能进行排单操作，请联系开单部门补录信息！
	 */
	public static final String WAYBILLSTATE_CANT_OPERATE = "pkp.predeliver.editDeliverbillIndex.waybillstatecantoperate";
	/**
	 * 运单还没有进行补录，请先剔除此运单，补录后再进行排单操作！
	 */
	public static final String WAYBILLNO_NOT_COMPLEMENT = "pkp.predeliver.confirmDeliverbill.waybillnoNotComplement";
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1213773709776707096L;
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:26
	 * @param errCode
	 */
	public DeliverbillException(String errCode) {
		super();
		this.errCode = errCode;
	}
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-16 上午10:49:57
	 * @param errCode
	 * @param msg
	 */
	public DeliverbillException(String errCode, String msg) {
		super(errCode, msg);
	}
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:45
	 * @param code
	 * @param cause
	 */
	public DeliverbillException(String code,  Throwable cause){
		  super(code, cause);
		  this.errCode = code;
	  }
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:45
	 * @param code
	 * @param cause
	 */
	public DeliverbillException(String code,Object... args) {
		super(code,args);
	}

}