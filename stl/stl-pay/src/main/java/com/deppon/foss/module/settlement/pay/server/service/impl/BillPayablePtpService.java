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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/server/service/impl/BillPayablePtpService.java
 *
 * FILE NAME        	: BillPayablePtpService.java
 *
 * AUTHOR			: FOSS结算系统开发组
 *
 * HOME PAGE		: http://www.deppon.com
 *
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ar.bamp.common.util.DateUtil;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.inteface.domain.payable.add.FeeDetails;
import com.deppon.esb.inteface.domain.payable.add.PayableBills;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableRequest;
import com.deppon.esb.inteface.domain.payable.update.BillPayableChangedRequest;
import com.deppon.esb.inteface.domain.payable.update.ChangeFeeDetails;
import com.deppon.esb.inteface.domain.payable.update.ChangePayableBills;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayablePtpDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.BillPayInfoEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.NewBillPayablePtpEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillNewPayablePtpDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对接合伙人应付单service接口实现
 * @author foss-Jiang Xun
 * @date 2016-01-21 下午4:41:00
 */
public class BillPayablePtpService implements IBillPayablePtpService{

	private static final Logger LOGGER = LogManager
			.getLogger(BillPayablePtpService.class);
	
	/**
	 * ptp应付单dao
	 */
	private IBillPayablePtpDao billPayablePtpDao;
	/**
	 * 应付单通用服务service
	 */
	private IBillPayableService billPayableService;
	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;
	/**
	 * 组织架构Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
    /**
     * 应付单明细服务service
     */
    private IBillPayableDService billPayableDService;
    /**
     * 代收货款服务
     */
    private IBillPayCODService billPayCODService;
    /**
     * 代收货款服务
     */
    private ICodCommonService codCommonService;
    /**
	 * 运单管理service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 运单签收结果信息
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * 生成合伙人应付单
	 * @author foss-Jiang Xun
	 * @date 2016-01-21 下午4:44:00
	 * 1.到达提成、委托派费应付单的应付部门为到达合伙人对接营业部；费用承担部门为到达合伙人营业部;
	 * 2.到付运费、代收货款应付单的应付部门为始发合伙人的对接营业部；费用承担部门为始发合伙人营业部。
	 * 3.合伙人奖励应付单（包括合伙人快递差错应付和合伙人奖励应付）的应付部门和催款部门为合伙人对接营业部；费用承担部门（当子类型是合伙人快递差错应付时且费用类型不是抢货时，为合伙人快递点部；
		当子类型是合伙人快递差错应付且费用类型是抢货（直营营业部抢合伙人）时，为责任直营营业部、营业部所在营业区、所在大区；当子类型是合伙人奖励应付是，为事业合伙人拓展部）
	 */
	@Transactional
	public void generatPtpPayableBill(BillNewPayablePtpDto dto){
		LOGGER.info("生成合伙人应付单开始....");
		
		//校验合伙人应付单是否重复
		checkPtpPayableBillDupli(dto);
		
		//应付单信息列表
		List<NewBillPayablePtpEntity> payList = dto.getNewPayablePtpEntityList();
        CurrentInfo currentInfo = dto.getCurrentInfo();
		for(int i=0;i<payList.size();i++){
            addBillPayable(payList, currentInfo, i);
		}
		LOGGER.info("生成合伙人应付单结束....");
	}

	
    /**
     * 校验合伙人应付单是否重复
     * @param dto 合伙人应付单生成Dto
     */
    private void checkPtpPayableBillDupli(BillNewPayablePtpDto dto) {
    	//应付单信息列表
    	List<NewBillPayablePtpEntity> payList = dto.getNewPayablePtpEntityList();
    	if(CollectionUtils.isNotEmpty(payList)){
    		for(NewBillPayablePtpEntity newPayEntity : payList){
    			//应付单entity
    			BillPayableEntity payEntity = newPayEntity.getBillPayableEntity();
    			//运单号
    			List<String> waybillList = new ArrayList<String>();
    			waybillList.add(payEntity.getWaybillNo());
    			
    			//单据类型
				List<String> billTypeList = new ArrayList<String>();
				billTypeList.add(payEntity.getBillType());
			    
			    //来源单号
			    List<String>sourceBillNoList=new ArrayList<String>();
			    sourceBillNoList.add(payEntity.getSourceBillNo());
			    //发更改生成到达提成单据类型的支线转运提成和受理更改单补贴明细
    		    if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE
    					.equals(payEntity.getBillType())){
 				   List<BillPayableEntity> payablesourceBillNoList = billPayableService.queryBysourceBillNoAndByBillTypes(waybillList,billTypeList,sourceBillNoList);
 				   //如果查询出合伙人应付单，说明有重复合伙人应付单
 				   if (CollectionUtils.isNotEmpty(payablesourceBillNoList)) {
 					LOGGER.error("合伙人应付单重复校验，存在重复的应付单。运单号："
 							+ payEntity.getWaybillNo() + ",单据子类型："
 							+ payEntity.getBillType()+",来源单号："+payEntity.getSourceBillNo());
 					throw new SettlementException("合伙人应付单重复校验，存在重复的应付单。运单号："
 							+ payEntity.getWaybillNo() + ",单据子类型："
 							+ payEntity.getBillType()+",来源单号："+payEntity.getSourceBillNo());
 				   }
    			}else{
    				List<BillPayableEntity> payableList = billPayableService
    						.queryByWaybillNosAndByBillTypes(waybillList,
    								billTypeList);
    				//如果查询出合伙人应付单，说明有重复合伙人应付单
    				if (CollectionUtils.isNotEmpty(payableList)) {
    					LOGGER.error("合伙人应付单重复校验，存在重复的应付单。运单号："
    							+ payEntity.getWaybillNo() + ",单据子类型："
    							+ payEntity.getBillType());
    					throw new SettlementException("合伙人应付单重复校验，存在重复的应付单。运单号："
    							+ payEntity.getWaybillNo() + ",单据子类型："
    							+ payEntity.getBillType());
    				}
				 }
			}
    	}
	}


    /**
     * 红冲合伙人应付单并生成新单.
     * 运单信息更改引起到付运费应付单红冲
     * @author hemingyu
     * @date 2016-01-22 16:41:00
     */
    @Transactional
    public void writeBackAndAddPayable(BillPayableChangedRequest request,BillNewPayablePtpDto billPayablePtpDtoChange){
        LOGGER.info("....红冲合伙人应付单并生成新单开始....");
        //应付单信息列表
        List<NewBillPayablePtpEntity> payList = billPayablePtpDtoChange.getNewPayablePtpEntityList();
        //应付单实体
        BillPayableEntity entity;
        CurrentInfo currentInfo = billPayablePtpDtoChange.getCurrentInfo();
        int j;
        for(int i=0;i<payList.size();i++){
            entity = payList.get(i).getBillPayableEntity();
            //根据来源单号与类型查询是否存在这条应付单
            List<String> waybillNos = new ArrayList<String>();
            waybillNos.add(entity.getWaybillNo());
            List<String> billTypes = new ArrayList<String>();
            billTypes.add(entity.getBillType());  
            List<String> destOrgCode = new ArrayList<String>();
            destOrgCode.add(entity.getDestOrgCode());  
            //得到数据库中有效的应付单，将其红冲
            List<BillPayableEntity> oldPayableEntity = billPayableService.queryByWaybillNosAndByBillTypes(waybillNos, billTypes);
            if (CollectionUtils.isNotEmpty(oldPayableEntity) && oldPayableEntity.size() > 1&&
            		!SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(entity.getBillType())) { //单据类型为到达提成应付不抛出此异常
                	 //抛出异常
                    throw new SettlementException("红冲合伙人应付单失败，数据库中有多条有效的这一类型的运单号为" + entity.getWaybillNo() + "的应付单");
            }else if(CollectionUtils.isNotEmpty(oldPayableEntity)) {
                //如果核销金额大于0则不生成此项应付单(部分核销和全部核销都不能发更改)
                for (int k= 0; k <oldPayableEntity.size(); k++) {
                j = oldPayableEntity.get(k).getVerifyAmount().compareTo(BigDecimal.ZERO);
                //j==1即大于0
                if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES.equals(oldPayableEntity.get(k).getPayStatus()) || j == 1) {
                    //抛出异常
                    throw new SettlementException("红冲合伙人应付单失败，支付状态为已支付或者核销金额大于0的运单号为" + entity.getWaybillNo() + "的应付单");
                }
                // 1、先作废原有的有效代收货款应付单；2、生成一条红单；
                // 调用合伙人公共红冲接口:作废原应付单生成红单
                    BillPayableEntity payableEntity = oldPayableEntity.get(k);
                    //红冲
                    billPayableService.writeBackBillPayable(payableEntity, billPayablePtpDtoChange.getCurrentInfo());
				}
            }
            //当数据库中没有效的应付单，也生成新单
            //传入的金额等于0不生成应付单
            j = entity.getAmount().compareTo(BigDecimal.ZERO);
            //k==1即大于0
            if (j == 1) {
                //传入的金额大于0生成应付单和应付单明细
                addBillPayable(payList, currentInfo, i);
            }
        }

        //将代收货款设置为无效，然后判断金额是否为空
        List<ChangePayableBills> changePayableBills = request.getChangePayableBills();
        for(int i=0;i<changePayableBills.size();i++) {
            //ptp应付信息
            ChangePayableBills payableBill = changePayableBills.get(i);
            //应付单子类型
            String billType = payableBill.getBillType();
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(billType)) {//合伙人代收货款应付
                //作废代收货款应付单并生成新单
                changeAccountPayableCod(changePayableBills.get(i));
            }
        }
        LOGGER.info("....红冲合伙人应付单并生成新单结束....");
    }

    /**
     * 生成应付单和应付单明细
     * @param payList
     * @param currentInfo
     * @param i
     * @author foss-Jiang Xun
      * @date 2016-01-21 下午4:44:00
     */
    public void addBillPayable(List<NewBillPayablePtpEntity> payList, CurrentInfo currentInfo, int i) {
        BillPayableEntity entity;
        List<BillPayableDEntity> dList;
        entity = payList.get(i).getBillPayableEntity();
        dList = payList.get(i).getBillPayableDList();
        if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(entity.getBillType())){
    		List<String> waybillNos = new ArrayList<String>();
    		waybillNos.add(entity.getWaybillNo());
            List<String> billTypes = new ArrayList<String>();
            billTypes.add(entity.getBillType());
            List<String>sourceBillNo=new ArrayList<String>();
            sourceBillNo.add(entity.getSourceBillNo());
			//根据运单号和单据子类型以及来源单号，来源单号取的是ptp应付流水id，查询应付单
	          List<BillPayableEntity> sourceBillNobillPayableEntity = billPayableService.queryBysourceBillNoAndByBillTypes(waybillNos,billTypes,sourceBillNo);
	          if(CollectionUtils.isNotEmpty(sourceBillNobillPayableEntity)&&sourceBillNobillPayableEntity.size()>0){
	  			LOGGER.error("合伙人应付单重复校验，存在重复的应付单。运单号："+entity.getWaybillNo()+",单据子类型："+entity.getBillType()+
	  					",来源单号："+entity.getSourceBillNo());
	  			throw new SettlementException("合伙人应付单重复校验，存在重复的应付单。运单号："+entity.getWaybillNo()+",单据子类型："+entity.getBillType()+
	  					",来源单号："+entity.getSourceBillNo());
	  		  }
        }
        
        //奖罚、和其他应付，不查询出发和到达部门
    	if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(entity.getBillType())
				||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE.equals(entity.getBillType())
				||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(entity.getBillType()))){
            //根据出发部门标杆编码 查出发部门编码、出发部门名称
            OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCode(entity.getOrigOrgCode());//出发部门信息
            if(null == origEntity){
                LOGGER.error("生成应付单失败，出发部门信息为空！");
                throw new SettlementException("生成应付单失败，出发部门信息为空！");
            }
            LOGGER.info(".......出发部门编码是："+origEntity.getCode());
            //出发部门编码
            entity.setOrigOrgCode(origEntity.getCode());
            //出发部门名称
            entity.setOrigOrgName(origEntity.getName());

            //到达部门信息
            OrgAdministrativeInfoEntity destEntity = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCode(entity.getDestOrgCode());
            if(null == destEntity){
                LOGGER.error("生成应付单失败，到达部门信息为空！");
                throw new SettlementException("生成应付单失败，到达部门信息为空！");
            }
            //到达部门编码
            entity.setDestOrgCode(destEntity.getCode());
            //到达部门名称
            entity.setDestOrgName(destEntity.getName());
            
            //运单为快递时，查询快递代理点部
			String prodectCode = entity.getProductCode();
			if(SettlementUtil.isPackageProductCode(prodectCode)){
				LOGGER.info("生成应付单，是快递！");
				//调用综合接口去查询出发部门快递代理点部
	            ExpressPartSalesDeptResultDto expressOrigOrg = expressPartSalesDeptService.
	                    queryExpressPartSalesDeptBySalesCodeAndTime(entity.getOrigOrgCode(),null);
	            //判断是否为空
	            if(expressOrigOrg==null){
	                throw new SettlementException("生成应付单失败，出发部门对应的快递代理点部为空！");
	            }else if(StringUtils.isNotBlank(expressOrigOrg.getUnifiedCode())){
	                //设置快递代理点部
					entity.setExpressOrigOrgCode(expressOrigOrg.getPartCode());
					entity.setExpressOrigOrgName(expressOrigOrg.getPartName());
	            }
	            
	            //虚拟网点，直接存快递点部(不查快递点部)
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByCode(entity.getDestOrgCode());
				if(org==null){//无部门信息
					entity.setExpressDestOrgCode(entity.getDestOrgCode());
					entity.setExpressDestOrgName(entity.getDestOrgName());
				}else if(org.checkExpressSalesDepartment()||org.checkTransferCenter()){//到达部门是虚拟营业部或外场，不校验快递点部
					entity.setExpressDestOrgCode(entity.getDestOrgCode());
					entity.setExpressDestOrgName(entity.getDestOrgName());
				}else{//有部门信息，且不是虚拟营业部
					//调用综合接口去查询到达部门快递代理点部
					ExpressPartSalesDeptResultDto expressDestOrg = expressPartSalesDeptService.
							queryExpressPartSalesDeptBySalesCodeAndTime(entity.getDestOrgCode(),null);
					if(expressDestOrg==null){
						throw new SettlementException("生成应付单失败，到达部门对应的快递代理点部为空！");
					}else if(StringUtils.isNotBlank(expressDestOrg.getUnifiedCode())){
						//设置快递代理点部
						entity.setExpressDestOrgCode(expressDestOrg.getPartCode());
						entity.setExpressDestOrgName(expressDestOrg.getPartName());
					}else{
						
					}
				}
			}else{
	        	LOGGER.info("生成应付单，不是快递！");
	        }
			
			//DN201609200015 若运单已签收，则应付单生效状态设置为“已生效”；若未签收，则生成时对应付单生效状态设置为“未生效”；by 243921
			WaybillSignResultEntity sign = new WaybillSignResultEntity();
			sign.setActive(FossConstants.YES);
			sign.setWaybillNo(entity.getWaybillNo());
			WaybillSignResultEntity result = waybillSignResultService.queryWaybillSignResultByWaybillNo(sign);
			if(null != result){
				//生效状态
				entity.setEffectiveStatus(FossConstants.YES);
				//生效时间
				entity.setEffectiveDate(result.getSignTime());
				//签收时间
				entity.setSignDate(result.getSignTime());
				//生效人
				entity.setEffectiveUserName(result.getCreator());
				//生效人编码
				entity.setEffectiveUserCode(result.getCreatorCode());
			}else{
				entity.setEffectiveStatus(FossConstants.NO);
			}
        }


        //应付部门信息（为合伙人对接营业部）
        OrgAdministrativeInfoEntity payableOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByUnifiedCode(entity.getPayableOrgCode());
        if(null == payableOrgEntity){
            LOGGER.error("生成应付单失败，应付部门信息为空！");
            throw new SettlementException("生成应付单失败，应付部门信息为空！");
        }
        entity.setPayableOrgCode(payableOrgEntity.getCode());
        entity.setPayableOrgName(payableOrgEntity.getName());
        //应付部门子公司代码
        entity.setPayableComCode(payableOrgEntity.getSubsidiaryCode());
        //应付部门子公司名称
        entity.setPayableComName(payableOrgEntity.getSubsidiaryName());
        
        //制单部门信息
		OrgAdministrativeInfoEntity createOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(entity.getCreateOrgCode());
		if(null == createOrgEntity){
			LOGGER.error("生成应付单失败，制单部门信息为空！");
			throw new SettlementException("生成应付单失败，制单部门信息为空！");
		}		
		entity.setCreateOrgCode(createOrgEntity.getCode());
		entity.setCreateOrgName(createOrgEntity.getName());
		
//        //费用承担部门
//        OrgAdministrativeInfoEntity expenseBearEntity = orgAdministrativeInfoService
//                .queryOrgAdministrativeInfoByUnifiedCode(entity.getExpenseBearCode());
//        if(null == expenseBearEntity){
//            LOGGER.error("生成应付单失败，费用承担部门信息为空！");
//            throw new SettlementException("生成应付单失败，费用承担部门信息为空！");
//        }
//        entity.setExpenseBearCode(expenseBearEntity.getCode());

        //设置ID
        entity.setId(UUIDUtils.getUUID());
        //生成应付单
        billPayableService.addBillPayable(entity, currentInfo);
        //生成应付单明细
        if(!CollectionUtils.isEmpty(dList)){
            billPayableDService.addList(dList);
        }
    }

    /**
     * 异常红冲合伙人应付单.
     * 异常签收应付单红冲规则
     * @author hemingyu
     * @date 2016-01-22 16:41:00
     * 应付单实体：
     *         BillPayableEntity
     *  操作人
     *       CurrentInfo
     * 异常出库类型
     *       expType
     */
    @Transactional
    public void writeBackExcePayable(BillPayableEntity waybill,CurrentInfo currentInfo,String expType){
        LOGGER.info("....异常签收应付单红冲合伙人应付单开始....");
        List<String> waybillNos = new ArrayList<String>();
        waybillNos.add(waybill.getWaybillNo());
        List<String> billTypes = new ArrayList<String>();
        billTypes.add(waybill.getBillType());
        //得到数据库中有效的应付单，将其红冲
        List<BillPayableEntity> codPayableEntity = billPayableService.queryByWaybillNosAndByBillTypes(waybillNos,billTypes);
        if(codPayableEntity ==null||codPayableEntity.size()>1){ //如果无有效的代收货款应收单
            //抛出异常
            throw new SettlementException("异常签收应付单红冲合伙人应付单失败，数据库中运单号为"+waybill.getWaybillNo()+"有多条有效的这一类型的"+waybill.getBillType()+"应付单");
        }
        // 1、先作废原有的有效代收货款应付单；2、生成一条红单；
        // 调用合伙人公共红冲接口:作废原应付单生成红单
        //根据传入参数expType判断哪种签收出库类型
        BillPayableEntity payableEntity = codPayableEntity.get(0);

        if(payableEntity.getBillType().equals
                (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE)){//合伙人到达提成应付单
            if(expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS)//判断是否是丢货
                    ||expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS)//判断是否是弃货
                    ||expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS)){//判断是否是违禁品
                if(SettlementUtil.isPackageProductCode(payableEntity.getProductCode())){//判断是否是快递
                    billPayableService.writeBackBillPayable(payableEntity, currentInfo);//生成红单
                }else{//零担
                    billPayableService.writeBackBillPayable(payableEntity, currentInfo);//生成红单
                    //得到应付单明细
                    List<String> sourceBillNos = new ArrayList<String>();
                    sourceBillNos.add(payableEntity.getWaybillNo());
                    List<BillPayableDEntity> billPayableDEntityList = billPayableDService.queryBySourceBillNOs(sourceBillNos, "Y");
                    for(BillPayableDEntity billPayableDEntity:billPayableDEntityList) {
                        if (billPayableDEntity.getPayableType().equals(SettlementDictionaryConstants.PAYABLE_FEE_TYPE__PARTLINE_TRANSFER_EXTRA)) {//应付类型为支线转运提成
                            //设置应付单金额
                            payableEntity.setAmount(billPayableDEntity.getAmount());
                            //设置ID
                            payableEntity.setId(UUIDUtils.getUUID());
                            //生成仅包含支线转运提成的到达提成应付单
                            billPayableService.addBillPayable(payableEntity, currentInfo);//生成仅包含支线转运提成的到达提成应付单
                            //生成仅包含支线转运提成的到达提成应付单明细
                            //billPayableDService.add(billPayableDEntity);
                        }
                    }
                }
            }
        }else if(payableEntity.getBillType().equals
                (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE)//合伙人委托派费应付单
                ||payableEntity.getBillType().equals
                (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE)){//合伙人到付运费应付单
            if(expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS)//判断是否是丢货
                    ||expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS)//判断是否是弃货
                    ||expType.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS)) {//判断是否是违禁品
                billPayableService.writeBackBillPayable(payableEntity, currentInfo);//生成红单
            }
        }else if(payableEntity.getBillType().equals
                (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD)) {//合伙人代收货款
            //使用老接口规则
            billPayableService.writeBackBillPayable(payableEntity, currentInfo);//生成红单
        }
        LOGGER.info("....异常签收应付单红冲合伙人应付单结束....");
    }

    /**
	 * 应收单是否已对账
	 * @author foss-Jiang Xun
	 * @date 2016-01-28 下午6:16:00
	 */
	public void isStated(String wayBillNo){
		 LOGGER.info("....验证运单号:"+wayBillNo+"是否已对账开始....");
		 int statedNumber = billPayablePtpDao.queryStatedBillPayableNumber(wayBillNo);
		 if(statedNumber==0){
			 LOGGER.error("可以发更改，运单号"+wayBillNo+"应付单无对账单！");
		 }else{
			 LOGGER.error("不能发更改，运单号"+wayBillNo+"应付单已进入对账单！");
			 throw new SettlementException("不能发更改，运单号"+wayBillNo+"应付单已进入对账单！");
		 }
		 LOGGER.info("....验证运单号:"+wayBillNo+"是否已对账结束....");
	}
	
	/**
	 * 封装参数转成foss内部DTO
	 * @author foss-Jiang Xun
	 * @date 2016-01-14 下午8:16:00
     * change by hemingyu 20160310
     * 增加了一个参数BillPayableChangedRequest changedRequest
	 */
	public BillNewPayablePtpDto buildDto(PtpAddBillPayableRequest addRequest,BillPayableChangedRequest changedRequest) throws ESBBusinessException {
        //Dto
        BillNewPayablePtpDto dto = new BillNewPayablePtpDto();
        //应付单信息列表
        List<NewBillPayablePtpEntity> payList = new ArrayList<NewBillPayablePtpEntity>();
        //应付信息实体
        NewBillPayablePtpEntity payInfo =null;
        CurrentInfo currentInfo = null;
        if (addRequest != null) {
            //应付信息列表
            List<PayableBills> payableBills = addRequest.getPayableBills();
            //转换应付列表
            LOGGER.info("\n应付单数量是：" + payableBills.size());
            for (int i = 0; i < payableBills.size(); i++) {
                //ptp应付信息
                PayableBills payableBill = payableBills.get(i);
                //验证应付单是否重复
                validPayableBillRepeat(payableBill);
                payInfo = getBillPayableDEntities(payableBill);
                if(payInfo==null){
                    continue;
                }
                payList.add(payInfo);
                if(i==0){
                    currentInfo = getPtpCurrentInfo(payableBill);
                }
            }
        } else {
            List<ChangePayableBills> changePayableBills = changedRequest.getChangePayableBills();
            //转换应付列表
            LOGGER.info("\n应付单数量是：" + changePayableBills.size());
            for (int i = 0; i < changePayableBills.size(); i++) {
                //ptp应付信息
                ChangePayableBills changePayableBill = changePayableBills.get(i);
                PayableBills payableBill = new PayableBills();
                BeanUtils.copyProperties(changePayableBill, payableBill);
                //因为list没有set方法不能拷贝所以用这个
                if (CollectionUtils.isNotEmpty(changePayableBill.getChangeFeeDetails())) {
                    for (ChangeFeeDetails details : changePayableBill.getChangeFeeDetails()) {
                        FeeDetails destFeeDetails = new FeeDetails();
                        BeanUtils.copyProperties(details, destFeeDetails);
                        payableBill.getFeeDetails().add(destFeeDetails);
                    }
                }
                payInfo = getBillPayableDEntities(payableBill);
                if(payInfo==null){
                    continue;
                }
                payList.add(payInfo);
                if(i==0){
                    currentInfo = getPtpCurrentInfo(payableBill);
                }
            }
        }
        //设置应付单、应付单明细
        dto.setNewPayablePtpEntityList(payList);
        //获取用户信息
        dto.setCurrentInfo(currentInfo);
        return dto;
    }

    /**
     * 验证合伙人应付单是否重复
     * @author gpz
     * @date 2016年11月24日
     */
    private void validPayableBillRepeat(PayableBills payableBill) {
    	//合伙人快递差错应付，合伙人业务奖励应付
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR
				.equals(payableBill.getBillType())
				|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS
						.equals(payableBill.getBillType())) {
			//查询合伙人奖罚应付单是否重复条件
			BillPayableEntity queryEntity = new BillPayableEntity();
			//来源单号
			queryEntity.setSourceBillNo(payableBill.getSourceBillNo());
			//客户编码（合伙人部门编码）
			queryEntity.setCustomerCode(payableBill.getCustomerCode());
			//单据子类型
			queryEntity.setBillType(payableBill.getBillType());
			//总金额
			BigDecimal amount = payableBill.getAmount()==null ? BigDecimal.ZERO : payableBill.getAmount().multiply(new BigDecimal(100));
			queryEntity.setAmount(amount);
			List<BillPayableEntity> payableList = billPayableService.checkPayableBillRepeated(queryEntity);
			if(CollectionUtils.isNotEmpty(payableList)){
				String errMsg = "校验合伙人应付单重复，已存在[来源单据号："
						+ payableBill.getSourceBillNo() + ",单据子类型："
						+ payableBill.getBillType() + ",合伙人编码："
						+ payableBill.getCustomerCode() + ",金额:"
						+ payableBill.getAmount() + "]一样的合付人应收单";
				LOGGER.error(errMsg);
				throw new SettlementException(errMsg);
			}
		} else {
			// 校验其他合伙人应付单是否重复
			payableBillDupliValid(payableBill);
		}
		
	}


	/**
     * 拼装dto实体
     * @param payableBill
     * @return List<NewBillPayablePtpEntity>
     * @author foss-hemingyu 20160310 16:27:58
     * @throws ESBBusinessException
     */
    public NewBillPayablePtpEntity getBillPayableDEntities(PayableBills payableBill) throws ESBBusinessException {
        //日志记录接口参数信息
        LOGGER.info("\n运单号是:" + payableBill.getWaybillNo() + "\n来源单据类型是：" + payableBill.getSourceBillType() + "" + "\n来源单据号是：" + payableBill.getSourceBillNo() + ""
                + "\n应付部门编码是：" + payableBill.getPayableOrgCode() + "\n出发部门编码是：" + payableBill.getOrigOrgCode() + ""
                + "\n到达部门编码是：" + payableBill.getDestOrgCode() + "" + "\n业务日期是：" + payableBill.getBusinessDate() + ""
                + "\n应付类型是：" + payableBill.getPayableType() + "" + "\n制单人编码是：" + payableBill.getCreateUserCode() + ""
                + "\n制单人部门编码是：" + payableBill.getCreateOrgCode()
                + "\n订单总金额是：" + payableBill.getAmount() + "\n单据子类型是：" + payableBill.getBillType()
                + "\n应付单费用承担部门是：" + payableBill.getExpenseBearCode());
        //应付单明细实体
        BillPayableDEntity dEntity;
        //ptp费用明细
        List<FeeDetails> feeDetails =payableBill.getFeeDetails();
        //应付信息实体
        NewBillPayablePtpEntity payInfo = new NewBillPayablePtpEntity();
        //应付单实体
        BillPayableEntity entity = new BillPayableEntity();

        //应付单明细列表
        List<BillPayableDEntity> payDList = null;

        //验证参数
        validateParams(payableBill);
        //应付单号
        String payableNo = "";
        //应付单子类型
        String billType = payableBill.getBillType();
        if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType)){//始发提成应付
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF63);
        }else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(billType)){//委派费应付
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF64);
        }else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(billType)){//合伙人到付
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF65);
        }else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(billType)){//合伙人代收货款应付（即原代收货款应付）
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF1);
        }else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(billType)){//合伙人快递差错应付
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF66);
        }else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(billType)){//合伙人奖励应付
            payableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF67);
        }else{
            throw new SettlementException("应付单据子类型不正确，无此类型定义");
        }

        LOGGER.info("\n应付单号是："+payableNo);

        //明细费用之和
        BigDecimal sum = BigDecimal.ZERO;
        if(CollectionUtils.isNotEmpty(feeDetails) && feeDetails.size()>0){
            payDList = new ArrayList<BillPayableDEntity>();
            for(int k=0;k<feeDetails.size();k++){
                dEntity = new BillPayableDEntity();
                dEntity.setActive(FossConstants.YES);//有效状态
                dEntity.setAmount(feeDetails.get(k).getAmount()==null?BigDecimal.ZERO:feeDetails.get(k).getAmount());//明细费用
                dEntity.setCreateTime(new Date());//创建日期
                dEntity.setCreateUserCode(payableBill.getCreateUserCode());//创建人编码
                dEntity.setCreateUserName(payableBill.getCreateUserName());//创建人
                dEntity.setPayableType(feeDetails.get(k).getPayableType());//费用类型
                dEntity.setSourceBillNo(payableBill.getSourceBillNo());//来源单据号
                dEntity.setPayableNo(payableNo);//应付单号

                LOGGER.info("\n应付单号是："+payableNo);
                sum = sum.add(dEntity.getAmount());

                payDList.add(dEntity);
            }
            //判断应付单金额和明细金额是否相等
            if(sum.compareTo(payableBill.getAmount()) != 0){
                throw new SettlementException("费用明细总和不等于总金额");
            }
            //判断应付单金额和为0，不生成此项应付单
            if(sum.compareTo(BigDecimal.ZERO)==0){
                return null;
            }
            LOGGER.info("\n费用明细数量是："+feeDetails.size());
        }

        //应付单(D:默认，R:必填，O:可选，S衍生值)
        entity.setPayableNo(payableNo);//R应付单号
        entity.setWaybillNo(payableBill.getWaybillNo());//R运单号
        entity.setWaybillId(payableBill.getWaybillId());//R运单id
        entity.setBillType(payableBill.getBillType());//R应付单据子类型
        entity.setSourceBillType(payableBill.getSourceBillType());//R来源单据类型
        entity.setSourceBillNo(payableBill.getSourceBillNo());//R来源单据号
        entity.setCodType(payableBill.getRefundType());//O代付货款类型
        entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);//D生成方式
        entity.setPayableOrgCode(payableBill.getPayableOrgCode());//R应付部门编码
        entity.setOrigOrgCode(payableBill.getOrigOrgCode());//R出发部门编码
        entity.setDestOrgCode(payableBill.getDestOrgCode());//R到达部门编码
        entity.setCustomerCode(payableBill.getCustomerCode());//R客户编码/应付代理编码
        entity.setCustomerName(payableBill.getCustomerName());//R客户名称/应付代理名称
        entity.setAmount(payableBill.getAmount());//R订单总金额
        entity.setVerifyAmount(BigDecimal.ZERO);//D已核销金额
        entity.setUnverifyAmount(payableBill.getAmount());//S未核销金额
        entity.setCurrencyCode("RMB");//R币种
        entity.setProductCode(payableBill.getProductCode());//R运输性质
        entity.setProductId(payableBill.getProductId());//O产品ID
        entity.setBusinessDate(payableBill.getBusinessDate());//R业务日期
        entity.setAccountDate(new Date());//D记账日期
        entity.setCreateDate(new Date());//D创建时间
        LOGGER.info("...........记账日期是：" + entity.getAccountDate());
        entity.setCreateUserCode(payableBill.getCreateUserCode());//R制单人编码
        entity.setCreateUserName(payableBill.getCreateUserName());//R制单人名称
        entity.setCreateOrgCode(payableBill.getCreateOrgCode());//R制单人部门编码
        entity.setCreateOrgName(payableBill.getCreateOrgName());//R制单人部门名称
        entity.setVersionNo(FossConstants.INIT_VERSION_NO);//D版本号
        entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);//D冻结状态
        entity.setActive(FossConstants.ACTIVE);//D是否有效
        
        //非运单相关的应付单，生成时自动生效。
        if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(payableBill.getBillType())||
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(payableBill.getBillType())||
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE.equals(payableBill.getBillType())){
        	entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);//D生效状态
        }else{
        	entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);//D未生效状态
        }
        
        entity.setIsRedBack(FossConstants.NO);//D是否红单
        entity.setIsInit(FossConstants.NO);//D是否初始化
        entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);//D审核状态
        entity.setAuditDate(new Date());
        entity.setAuditUserCode(payableBill.getCreateOrgCode());
        entity.setAuditUserName(payableBill.getCreateOrgName());
        entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);//D支付状态
        entity.setCustomerContact(payableBill.getCustomerContact());//O客户联系人编码
        entity.setCustomerName(payableBill.getCustomerName());//O客户联系人名称
        entity.setCustomerPhone(payableBill.getCustomerPhone());//O客户联系人电话
        entity.setWorkflowNo(payableBill.getWorkflowNo());//O工作流号
        entity.setPayerType(payableBill.getPayerType());//O付款方
        entity.setPayableType(payableBill.getPayableType());//O应付类型
        entity.setDeliverFee(payableBill.getDeliverFee());//O送货费
        entity.setOutgoingFee(payableBill.getOutgoingFee());//O外发运费
        entity.setCodAgencyFee(payableBill.getCodAgencyFee());//O代付货款手续费
        entity.setExternalInsuranceFee(payableBill.getExternalInsuranceFee());//O外发保价费
        entity.setNotes(payableBill.getNotes());//O备注
        entity.setPayeeName(payableBill.getPayeeName());//O开户人姓名
        entity.setBankHqCode(payableBill.getBankHqCode());//O开户行编码
        entity.setBankHqName(payableBill.getBankHqName());//O开户行名称
        entity.setAccountNo(payableBill.getAccountNo());//O银行帐号
        entity.setProvinceCode(payableBill.getProvinceCode());//O省份编码
        entity.setProvinceName(payableBill.getProvinceName());//O省份名称
        entity.setCityCode(payableBill.getCityCode());//O城市编码
        entity.setCityName(payableBill.getCityName());//O城市名称
        entity.setBankBranchCode(payableBill.getBankBranchCode());//O支行编码（行号）
        entity.setBankBranchName(payableBill.getBankBranchName());//O支行名称
        entity.setPaymentNotes(payableBill.getPaymentNotes());//O付款备注
        entity.setPaymentAmount(payableBill.getPaymentAmount());//O付款金额
        entity.setPaymentCategories(payableBill.getPaymentCategories());//O支付类别
        entity.setAccountType(payableBill.getAccountType());//O账户类型
        entity.setExpressOrigOrgCode(payableBill.getExpressOrigOrgCode());//O出发部门映射快递点部编码
        entity.setExpressOrigOrgName(payableBill.getExpressOrigOrgName());//O出发部门映射快递点部名称
        entity.setExpressDestOrgCode(payableBill.getExpressDestOrgCode());//O到达部门映射快递点部编码
        entity.setExpressDestOrgName(payableBill.getExpressDestOrgName());//O到达部门映射快递点部名称
        entity.setInvoiceMark(payableBill.getInvoiceMark());//O发票标记
        entity.setExpenseBearCode(payableBill.getExpenseBearCode());//R应付单费用承担部门
        //操作类型(值：1.add(新增) 2.update(修改))
        String operateType = StringUtils.isNotBlank(payableBill.getOperateType()) ? payableBill.getOperateType() : "add";
		entity.setNotes("[" + payableBill.getBillType() + "-"
				+ operateType + "-"
				+ DateUtil.getCurrentDate() + "]");// 备注

        payInfo.setBillPayableEntity(entity);
        payInfo.setBillPayableDList(payDList);

        return payInfo;
    }
    
    /**
	 * 验证是否已存在应付单
	 * @author foss-Jiang Xun
	 * @date 2016-05-11 下午5:22:00
	 */
	public void payableBillDupliValid(PayableBills payableBill) throws SettlementException {
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(payableBill.getWaybillNo());
        List<String> billTypes = new ArrayList<String>();
        billTypes.add(payableBill.getBillType());
        List<String>sourceBillNo=new ArrayList<String>();
        sourceBillNo.add(payableBill.getSourceBillNo());
		////发更改生成到达提成单据类型的支线转运提成和受理更改单补贴明细
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE
				.equals(payableBill.getBillType())) {
			//根据运单号和单据子类型以及来源单号，来源单号取的是ptp应付流水id，查询应付单
	          List<BillPayableEntity> sourceBillNobillPayableEntity = billPayableService.queryBysourceBillNoAndByBillTypes(waybillNos,billTypes,sourceBillNo);
	          if(CollectionUtils.isNotEmpty(sourceBillNobillPayableEntity)&&sourceBillNobillPayableEntity.size()>0){
	  			LOGGER.error("合伙人应付单重复校验，存在重复的应付单。运单号："+payableBill.getWaybillNo()+",单据子类型："+payableBill.getBillType()+
	  					",来源单号："+payableBill.getSourceBillNo());
	  			throw new SettlementException("合伙人应付单重复校验，存在重复的应付单。运单号："+payableBill.getWaybillNo()+",单据子类型："+payableBill.getBillType()+
	  					",来源单号："+payableBill.getSourceBillNo());
	  		  }
		}else{
		  //根据运单号和单据子类型，查询应付单
		  List<BillPayableEntity> billPayableEntity = billPayableService.queryByWaybillNosAndByBillTypes(waybillNos,billTypes);
		  if(CollectionUtils.isNotEmpty(billPayableEntity)&&billPayableEntity.size()>0){
			LOGGER.error("合伙人应付单重复校验，存在重复的应付单。运单号："+payableBill.getWaybillNo()+",单据子类型："+payableBill.getBillType());
			throw new SettlementException("合伙人应付单重复校验，存在重复的应付单。运单号："+payableBill.getWaybillNo()+",单据子类型："+payableBill.getBillType());
		  }
		}
	}
    
	/**
	 * 验证接口参数
	 * 规则：
	 * 1.非空
	 * 运单号、来源单据号、来源单据类型、单据子类型
	 * 应付部门编码、出发部门编码、到达部门编码、客户编码、业务日期、制单部门编码、应付单费用承担部门编码
	 * @author foss-Jiang Xun
	 * @date 2016-01-25 上午11:08:00
	 */
	public void validateParams(PayableBills payableBill) throws SettlementException {
		//合伙人快递差错应付和奖励应付、合伙人其他应付，不验证运单号和来源单据类型、运输性质
		if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(payableBill.getBillType())
				||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE.equals(payableBill.getBillType())
				||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(payableBill.getBillType()))){
			//运单号
			if(payableBill.getWaybillNo()==null){
				LOGGER.error("运单号为空...");
				throw new SettlementException("运单号为空...");
			}
			//来源单据类型
			if(payableBill.getSourceBillType()==null){
				LOGGER.error("来源单据类型为空...");
				throw new SettlementException("来源单据类型为空...");
			}
			//运输性质
			if(payableBill.getProductCode()==null){
				LOGGER.error("运输性质为空...");
				throw new SettlementException("运输性质为空...");
			}
		}
		//来源单据号
		if(payableBill.getSourceBillNo()==null){
			LOGGER.error("来源单据号为空...");
			throw new SettlementException("来源单据号为空...");
		}
		//单据子类型
		if(payableBill.getBillType()==null){
			LOGGER.error("单据子类型为空...");
			throw new SettlementException("单据子类型为空...");
		}
		//应付部门编码
		if(payableBill.getPayableOrgCode()==null){
			LOGGER.error("应付部门编码为空...");
			throw new SettlementException("应付部门编码为空...");
		}
		//合伙人快递差错应付、奖罚、和其他应付，不验证出发和到达部门
		if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(payableBill.getBillType())||
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(payableBill.getBillType())||
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE.equals(payableBill.getBillType()))){
			//出发部门编码
			if(payableBill.getOrigOrgCode()==null){
				LOGGER.error("出发部门编码为空...");
				throw new SettlementException("出发部门编码为空...");
			}
			//到达部门编码
			if(payableBill.getDestOrgCode()==null){
				LOGGER.error("到达部门编码为空...");
				throw new SettlementException("到达部门编码为空...");
			}
		}
		//客户编码
		if(payableBill.getCustomerCode()==null){
			LOGGER.error("客户编码为空...");
			throw new SettlementException("客户编码为空...");
		}
		//总金额
		if(payableBill.getAmount()==null){
			LOGGER.error("应付单总金额之和为空，不生成应付单...");
			throw new SettlementException("应付单总金额之和为空，不生成应付单...");
		}
		//业务日期
		if(payableBill.getBusinessDate()==null){
			LOGGER.error("业务日期为空...");
			throw new SettlementException("业务日期为空...");
		}		
		//制单部门编码
		if(payableBill.getCreateOrgCode()==null){
			LOGGER.error("制单部门编码为空...");
			throw new SettlementException("制单部门编码为空...");
		}
//		//应付类型
//		if(payableBill.getPayableType()==null){
//			LOGGER.error("应付类型为空...");
//			throw new SettlementException("应付类型为空...");
//		}
//		//开户人姓名
//		if(payableBill.getPayeeName()==null){
//			LOGGER.error("开户人姓名为空...");
//			throw new SettlementException("开户人姓名为空...");
//		}
//		//开户行编码
//		if(payableBill.getBankHqCode()==null){
//			LOGGER.error("开户行编码为空...");
//			throw new SettlementException("开户行编码为空...");
//		}
//		//开户行名称
//		if(payableBill.getBankHqName()==null){
//			LOGGER.error("开户行名称为空...");
//			throw new SettlementException("开户行名称为空...");
//		}
//		//银行帐号
//		if(payableBill.getAccountNo()==null){
//			LOGGER.error("银行帐号为空...");
//			throw new SettlementException("银行帐号为空...");
//		}
//		//支行编码（行号）
//		if(payableBill.getBankBranchCode()==null){
//			LOGGER.error("支行编码（行号）为空...");
//			throw new SettlementException("支行编码（行号）为空...");
//		}
//		//支行名称
//		if(payableBill.getBankBranchName()==null){
//			LOGGER.error("支行名称为空...");
//			throw new SettlementException("支行名称为空...");
//		}		
//		//合同部门编码
//		if(payableBill.getContractOrgCode()==null){
//			LOGGER.error("合同部门编码为空...");
//			throw new SettlementException("合同部门编码为空...");
//		}		
		//合伙人应付代收货款应付、和合伙人到付运费应付，不验证费用承担部门
		if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(payableBill.getBillType())||
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(payableBill.getBillType()))){
			//应付单费用承担部门编码
			if(payableBill.getExpenseBearCode()==null){
				LOGGER.error("应付单费用承担部门为空...");
				throw new SettlementException("应付单费用承担部门为空...");
			}	
		}
			
	}
	
	/**
	 * 设置用户信息
	 * @author foss-Jiang Xun
	 * @date 2016-01-19 下午4:14:00
	 */
	public CurrentInfo getPtpCurrentInfo(PayableBills payableBill) {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(payableBill.getCreateUserCode());
		employee.setEmpName(payableBill.getCreateUserName());
		
		user.setEmployee(employee);
		user.setUserName(payableBill.getCreateUserName());
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
        /*dept.setCode(payableBill.getCreateOrgCode());
        dept.setName(payableBill.getCreateOrgName());*/
        if(StringUtils.isNotBlank(payableBill.getCreateOrgCode())) {
            OrgAdministrativeInfoEntity deptOrgEntity = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCode(payableBill.getCreateOrgCode());
            dept.setCode(deptOrgEntity.getCode());
            dept.setName(deptOrgEntity.getName());
        }
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

    /**
     * 处理代收货款应付
     * @author foss-hemingyu
     * @date 2016-03-08 下午5:58:00
     */
    public void handleAccountPayableCod(PayableBills payableBill){
        LOGGER.info("....处理代收货款应付开始....");

        WaybillPickupInfoDto wayBill=getWaybillPickupInfoDto(payableBill);
        CurrentInfo currentInfo = getPtpCurrentInfo(payableBill);
        billPayCODService.addBillCOD(wayBill, currentInfo);

        LOGGER.info("....处理代收货款应付结束....");
    }

    /**
     * 生成WaybillPickupInfoDto实体
     * @author foss-hemingyu
     * @date 2016-03-08 下午5:58:00
     */
    public WaybillPickupInfoDto getWaybillPickupInfoDto(PayableBills payableBill){
        WaybillPickupInfoDto wayBill = new WaybillPickupInfoDto();
        // 开户行编码
        wayBill.setBankHQCode(payableBill.getBankHqCode());
        // 开户行名称
        wayBill.setBankHQName(payableBill.getBankHqName());
        // 省份编码
        wayBill.setProvinceCode(payableBill.getProvinceCode());
        // 省份名
        wayBill.setProvinceName(payableBill.getProvinceName());
        // 城市编码
        wayBill.setCityCode(payableBill.getCityCode());
        // 城市名
        wayBill.setCityName(payableBill.getCityName());
        // 支行编码（行号）
        wayBill.setBankBranchCode(payableBill.getBankBranchCode());
        // 支行名称
        wayBill.setBankBranchName(payableBill.getBankBranchName());
        // 对公对私标志
        if("PRIVATE_ACCOUNT".equals(payableBill.getPublicPrivateFlag())){
            wayBill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT);
        }else{
            wayBill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
        }

        // 收款人与发货人关系
        wayBill.setPayeeRelationship(payableBill.getPayeeRelationship());
        // 收款人手机号码
        wayBill.setPayeePhone(payableBill.getPayeePhone());
        //应付部门信息（为合伙人对接营业部）
        OrgAdministrativeInfoEntity payableOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByUnifiedCode(payableBill.getPayableOrgCode());
        if(null != payableOrgEntity){
            wayBill.setReceiveOrgCode(payableOrgEntity.getCode());
            wayBill.setReceiveOrgName(payableOrgEntity.getName());
            wayBill.setReceiveSubsidiaryCode(payableOrgEntity.getSubsidiaryCode());
            wayBill.setReceiveSubsidiaryName(payableOrgEntity.getSubsidiaryName());
        }

        OrgAdministrativeInfoEntity origOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(payableBill.getOrigOrgCode()); // 出发部门
        OrgAdministrativeInfoEntity destOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(payableBill.getDestOrgCode()); // 到达部门
        OrgAdministrativeInfoEntity collectionOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(payableBill.getCollectionOrgCode()); // 收款部门
        // 设置始发部门名称、子公司编码、子公司名称
        /*if(null != origOrgEntity) {
            wayBill.setReceiveOrgCode(origOrgEntity.getCode());
            wayBill.setReceiveOrgName(origOrgEntity.getName());
            wayBill.setReceiveSubsidiaryCode(origOrgEntity.getSubsidiaryCode());
            wayBill.setReceiveSubsidiaryName(origOrgEntity.getSubsidiaryName());
        }*/
        // 设置到达部门名称、子公司编码、子公司名称
        if(null != destOrgEntity) {
            wayBill.setLastLoadOrgCode(destOrgEntity.getCode());
            wayBill.setLastLoadOrgName(destOrgEntity.getName());
            wayBill.setLastLoadSubsidiaryCode(destOrgEntity.getSubsidiaryCode());
            wayBill.setLastLoadSubsidiaryName(destOrgEntity.getSubsidiaryName());
        }
        // 设置收款部门名称、子公司编码、子公司名称
        if(null != collectionOrgEntity) {
            wayBill.setCollectionOrgCode(collectionOrgEntity.getCode());
            wayBill.setCollectionOrgName(collectionOrgEntity.getName());
            wayBill.setCollectionCompanyCode(collectionOrgEntity.getSubsidiaryCode());
            wayBill.setCollectionCompanyName(collectionOrgEntity.getSubsidiaryName());
        }
        //运单号、运单ID
        wayBill.setWaybillNo(payableBill.getWaybillNo());
        wayBill.setId(payableBill.getWaybillId());
        // 代收货款类型、开户账户、开户名称、银行
        wayBill.setRefundType(payableBill.getRefundType());

        //xsd中的返款帐户开户账户和返款帐户开户名称是没有用的，只要开户人的账号和名称就可以
        wayBill.setAccountCode(payableBill.getAccountNo());
        wayBill.setAccountName(payableBill.getPayeeName());

        //wayBill.setAccountName(payableBill.getAccountName());
        wayBill.setAccountBank(payableBill.getBankHqName());

        // 客户编码、客户名称
        wayBill.setDeliveryCustomerName(payableBill.getCustomerName());
        wayBill.setDeliveryCustomerCode(payableBill.getCustomerCode());
        // 是否初始化、是否有效
        wayBill.setIsInit(FossConstants.NO);
        wayBill.setActive(FossConstants.ACTIVE);
        //运单开单时间
        wayBill.setBillTime(new Date());
        // 代收货款金额
        wayBill.setCodAmount(payableBill.getAmount());
        return wayBill;
    }

    /**
     *  将代收货款设置为无效，然后判断金额是否为空
     * @author foss-hemingyu
     * @date 2016-03-08 下午5:58:00
     */
    public void changeAccountPayableCod(ChangePayableBills changePayableBill){
        LOGGER.info("....更改代收货款应付开始....");
        // 作废代收货款单
        CODEntity codEntity = codCommonService.queryByWaybill(changePayableBill.getWaybillNo());
        PayableBills payableBill = new PayableBills();
        BeanUtils.copyProperties(changePayableBill,payableBill);
        //因为list没有set方法不能拷贝所以用这个
        if(CollectionUtils.isNotEmpty(changePayableBill.getChangeFeeDetails())){
            for(ChangeFeeDetails details : changePayableBill.getChangeFeeDetails()){
                FeeDetails destFeeDetails = new FeeDetails();
                BeanUtils.copyProperties(details,destFeeDetails);
                payableBill.getFeeDetails().add(destFeeDetails);
            }
        }

        if (codEntity != null
                && FossConstants.ACTIVE.equals(codEntity.getActive())) {
            CurrentInfo currentInfo = getPtpCurrentInfo(payableBill);
            // 作废代收货款单
            billPayCODService.cancelBillCOD(codEntity, currentInfo);
        }
        //金额是为0不生成新单,
        if(payableBill.getPaymentAmount().compareTo(BigDecimal.ZERO)!=0) {
            //检验是否生成新单
            handleAccountPayableCod(payableBill);
        }
        LOGGER.info("....更改代收货款应付结束....");
    }
    
    /**
     *  查询合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-18 下午04:06:00
     */
    public List<PtpAutoPayPFCREntity> queryPFCPBills(String billType,String active,Date fromDate,Date toDate){
    	LOGGER.info("...查询合伙人到付运费应付单开始。单据子类型:"+billType+" ，签收开始时间:"+fromDate+" ，签收接受时间:"+toDate);
    	List<PtpAutoPayPFCREntity> list = billPayablePtpDao.queryPFCPBills(billType, active, fromDate, toDate);
		LOGGER.info("...查询合伙人到付运费应付单结束....");
    	return list;
    }
    
    /**
     *
     * 
     **/
    /**
     *  根据应付单号，批量更新合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-20 下午04:00:00
     */
    public void updateBatchPFCPBills(PtpAutoPayPFCREntity payableEntity){
    	LOGGER.info("...批量修改合伙人到付运费应付单开始。单据子类型:"+payableEntity.getBillType()+" ，应付单号集合:"+payableEntity.getPayableNoList().toString());
    	int updateRows = billPayablePtpDao.updateBatchPFCPBills(payableEntity);
		if (updateRows != payableEntity.getPayableNoList().size()) {
			LOGGER.error("批量修改合伙人到付运费应付单失败。应付单数量："+payableEntity.getPayableNoList().size()+"和更新记录数:"+updateRows+"不一致！");
			throw new SettlementException("批量修改合伙人到付运费应付单失败。应付单数量："+payableEntity.getPayableNoList().size()+"和更新记录数:"+updateRows+"不一致！");
		}
		LOGGER.info("...批量修改合伙人到付运费应付单结束。更新记录数:"+updateRows);
    }
    
    /**
     *  根据付款单号集合,查询应付单和付款单信息 
     * @author foss-Jiang Xun
     * @date 2016-06-02 上午09:08:00
     * @return 应付单和付款单信息 集合
     */
    public List<BillPayInfoEntity> queryPayInfoByPayableNos(List<String> paymentNos){
    	List<BillPayInfoEntity> list = billPayablePtpDao.queryPayInfoByPayableNos(paymentNos);
    	return list;
    }
    
    /**
     * ptp监控查询应付单各单据的总记录数和总金额数
     */
    @Override
	public List<StlBillDetailDto> countPayableBills(
			BillingQueryRequestDto requestDto) {
		return billPayablePtpDao.countPayableBills(requestDto);
	}


	public IBillPayablePtpDao getBillPayablePtpDao() {
		return billPayablePtpDao;
	}

	public void setBillPayablePtpDao(IBillPayablePtpDao billPayablePtpDao) {
		this.billPayablePtpDao = billPayablePtpDao;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IBillPayableDService getBillPayableDService() {
		return billPayableDService;
	}


	public void setBillPayableDService(IBillPayableDService billPayableDService) {
		this.billPayableDService = billPayableDService;
	}

	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

    public IBillPayCODService getBillPayCODService() {
        return billPayCODService;
    }

    public void setBillPayCODService(IBillPayCODService billPayCODService) {
        this.billPayCODService = billPayCODService;
    }

    public ICodCommonService getCodCommonService() {
        return codCommonService;
    }

    public void setCodCommonService(ICodCommonService codCommonService) {
        this.codCommonService = codCommonService;
    }

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	

	@Override
	public List<PtpAutoPayPFCREntity> queryPDDFBills(String billType,
			String active, Date fromDate, Date toDate) {
	  	LOGGER.info("...查询合伙人委托派费应付单开始。单据子类型:"+billType+" ，签收开始时间:"+fromDate+" ，签收接受时间:"+toDate);
    	List<PtpAutoPayPFCREntity> list = billPayablePtpDao.queryPDDFBills(billType, active, fromDate, toDate);
		LOGGER.info("...查询合伙人委托派费应付单结束....");
    	return list;
	}
	
	/**
	 * 查询合伙人奖励差错应付单
	 * @author 355019
	 */
	@Override
	public List<PtpAutoPayPFCREntity> queryPBPLEBills(String billType,
			String active, Date fromDate, Date toDate) {
	  	LOGGER.info("...查询合伙人奖励差错应付单开始。单据子类型:"+billType+" ，签收开始时间:"+fromDate+" ，签收接受时间:"+toDate);
    	List<PtpAutoPayPFCREntity> list = billPayablePtpDao.queryPBPLEBills(billType, active, fromDate, toDate);
		LOGGER.info("...查询合伙人奖励差错应付单结束....");
    	return list;
	}
}
