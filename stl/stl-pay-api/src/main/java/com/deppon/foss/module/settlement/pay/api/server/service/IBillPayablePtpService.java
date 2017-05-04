/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/server/service/IBillPayablePtpService.java
 * 
 * FILE NAME        	: IBillPayablePtpService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.inteface.domain.payable.add.PayableBills;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableRequest;
import com.deppon.esb.inteface.domain.payable.update.BillPayableChangedRequest;
import com.deppon.esb.inteface.domain.payable.update.ChangePayableBills;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.pay.api.shared.domain.BillPayInfoEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillNewPayablePtpDto;

/**
 * 对接合伙人应付单service接口
 * @author foss-Jiang Xun
 * @date 2016-01-21 下午4:34:00
 */

public interface IBillPayablePtpService extends IService{

	
	/**
	 * 生成合伙人应付单
	 * @author foss-Jiang Xun
	 * @date 2016-01-21 下午4:39:00
	 */
	void generatPtpPayableBill(BillNewPayablePtpDto dto);

    /**
     * 异常红冲合伙人应付单.
     * 异常签收应付单红冲规则
     * @author hemingyu
     * @date 2016-01-22 16:41:00
     */
    public void writeBackExcePayable(BillPayableEntity waybill,CurrentInfo currentInfo,String expType);

    /**
     * 红冲合伙人应付单并生成新单.
     * 运单信息更改引起到付运费应付单红冲
     * @author hemingyu
     * @date 2016-01-22 16:41:00
     */
    public void writeBackAndAddPayable(BillPayableChangedRequest request,BillNewPayablePtpDto billPayablePtpDtoChange);

    /**
	 * 应收单是否已对账
	 * @author foss-Jiang Xun
	 * @date 2016-01-28 下午6:16:00
	 */
	void isStated(String wayBillNo);
	
	/**
	 * 封装参数转成foss内部DTO
	 * @author foss-Jiang Xun
	 * @date 2016-01-14 下午8:16:00
	 */
	public BillNewPayablePtpDto buildDto(PtpAddBillPayableRequest addRequest,BillPayableChangedRequest changedRequest) throws ESBBusinessException;

    /**
     * 处理代收货款应付
     * @author foss-hemingyu
     * @date 2016-03-08 下午5:58:00
     */
    public void handleAccountPayableCod(PayableBills payableBill);

    /**
     *  将代收货款设置为无效，然后判断金额是否为空
     * @author foss-hemingyu
     * @date 2016-03-08 下午5:58:00
     */
    public void changeAccountPayableCod(ChangePayableBills changePayableBill);
    
    /**
     *  查询合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-18 下午04:06:00
     */
    public List<PtpAutoPayPFCREntity> queryPFCPBills(String billType,String active,Date fromDate,Date toDate);
    
    /**
     *  根据应付单号，批量更新合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-20 下午04:00:00
     */
    public void updateBatchPFCPBills(PtpAutoPayPFCREntity payableEntity);
    
    /**
     *  根据付款单号集合,查询应付单和付款单信息 
     * @author foss-Jiang Xun
     * @date 2016-06-02 上午09:08:00
     * @return 应付单和付款单信息 集合
     */
    public List<BillPayInfoEntity> queryPayInfoByPayableNos(List<String> paymentNos);

    /**
     * ptp监控查询应付单各单据的总记录数和总金额数
     * 
     * @author gpz
     * @date 2016年8月6日
     * @param requestDto 请求参数
     */
	List<StlBillDetailDto> countPayableBills(BillingQueryRequestDto requestDto);
	
	/**
     *  查询委托派费
     * @author yanjunjie
     */
    public List<PtpAutoPayPFCREntity> queryPDDFBills(String billType,String active,Date fromDate,Date toDate);
    
    /**
     *  查询奖励差错应付
     * @author 355019
     */
    public List<PtpAutoPayPFCREntity> queryPBPLEBills(String billType,String active,Date fromDate,Date toDate);
}
