package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybill4FIMSDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IMergeWaybillService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillDetailService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybill4FIMSService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.MergeWaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.TaxPayerDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.TaxPayerInfoResponse;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 发票合并运单优化需求
 * 包括查询运单，然后合并运单
 * Created by 322906 on 2016/6/29.
 */
public class Waybill4FIMSService implements IWaybill4FIMSService {

    /** 声明日志对象. */
    public static final Logger LOGGER = LogManager.getLogger(Waybill4FIMSService.class);


    private IWaybill4FIMSDao waybill4FIMSDao;

    /**
     * 已经合并的运单信息服务
     */
    private IWaybillDetailService waybillDetailService;
    /**
     * 合并运单服务
     */
    private IMergeWaybillService mergeWaybillService;

    /**
     * 纳税人信息获取
     */
    private TaxPayerService taxPayerService;
    /**
     * 结算公用Service 用来生成合并运单号
     */
    private ISettlementCommonService settlementCommonService;


    /**
     * 查询运单
     * 1.运单已经合并的，不允许查询出来
     * 2.收货部门（即出发部门） 和到达网点（即到达部门）中至少有一个和当前登入部门相同
     * 3.快递事后则的运单，大客户对账单必须确认
     * 4.统一结算客户，只有催款部门才能查询出应收单数据
     * @param waybillQueryDto 页面查询参数信息
     * @param currentInfo 登入账户信息
     * @return
     */
    @Override
    public List<WaybillDetailEntity> queryWaybillByConditions(WaybillQueryDto waybillQueryDto, CurrentInfo currentInfo, int start, int limit) {
        /**
         * 主要查询俩部分信息
         * 1.应收单
         * 2.现金收款单
         */
        waybillQueryDto.setOrgCode(currentInfo.getCurrentDeptCode());

        List<WaybillDetailEntity> waybillDetailEntityList = waybill4FIMSDao.queryWaybillByConditions(waybillQueryDto, start, limit);

        return waybillDetailEntityList;
    }

    /**
     * 查询条数
     * @param waybillQueryDto
     * @return
     */
    @Override
    public int queryWaybillByConditionsCount(WaybillQueryDto waybillQueryDto) {
        int count = waybill4FIMSDao.queryWaybillByConditionsCount(waybillQueryDto);
        return count;
    }

    /**
     * 合并运单
     * 首先得根据运单号查询各种信息（合并运单需要的信息，因为这里需要的信息不是都保存在运单中的）
     * 其他的信息这边应该是在 应收单 中、
     * 将这许多运单的信息 合并 （通过sql统计），设置合并运单实体的字段值
     * 组成 合并运单实体，然后insert到表中
     * @param waybillQueryDto
     */

    @Override
    @Transactional
    public void mergeWaybill(WaybillQueryDto waybillQueryDto){

        //查询运单信息（这里是多有的运单，在不是统一结算的情况下，需要将出发客户，到达客户，付款方式不同的要分开合并）
        List<WaybillDetailEntity> fcList = new ArrayList<WaybillDetailEntity>();
        List<WaybillDetailEntity> nfCList = new ArrayList<WaybillDetailEntity>();
        /**
         * 到付和非到付不能 一起合并，而页面中可以一起选择，所以需要分开获取到付和非到付的运单
         */
        WaybillQueryDto fcDto = new WaybillQueryDto();
        WaybillQueryDto nfcDto = new WaybillQueryDto();
        fcDto.setWaybillNos(waybillQueryDto.getWaybillNos());
        nfcDto.setWaybillNos(waybillQueryDto.getWaybillNos());
        String [] paidMethods = waybillQueryDto.getPaidMethods();
        List paidMethodList = Arrays.asList(paidMethods);

        if("N".equals(waybillQueryDto.getUnifiedSettlement())){//非统一结算
            //如果有到付，到付和非到付分开合并
            if(paidMethodList.indexOf(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT)!=-1){
                String [] fc =new String[]{SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT};
                fcDto.setPaidMethods(fc);
                fcList = waybill4FIMSDao.queryWaybill(fcDto);

                if(fcList!=null && fcList.size()>0){
                    merge(waybillQueryDto,fcList);
                }
                String [] nfc = new String[paidMethods.length-1];
                for(int i=0,j=0;i<paidMethods.length;i++){
                    if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(paidMethods[i])){
                        nfc[j++]=paidMethods[i];
                    }
                }

                if(nfc.length>0){
                    nfcDto.setPaidMethods(nfc);
                    nfCList = waybill4FIMSDao.queryWaybill(nfcDto);
                    if(nfCList!=null && nfCList.size()>0){
                        merge(waybillQueryDto,nfCList);
                    }
                }
                String errorMessage = "";
                if(nfCList ==null || nfCList.size()<SettlementReportNumber.ONE_HUNDRED_AND_FIFTY){
                    errorMessage +="运单中包含的到付的运单少于150条，不能合并";
                }
                if(fcList==null || fcList.size()<SettlementReportNumber.ONE_HUNDRED_AND_FIFTY){
                    errorMessage +="运单中包含的非到付的运单少于150条，不能合并";
                }
				if (nfCList == null
						|| nfCList.size() < SettlementReportNumber.ONE_HUNDRED_AND_FIFTY
						|| fcList == null
						|| fcList.size() < SettlementReportNumber.ONE_HUNDRED_AND_FIFTY) {
					throw new SettlementException(errorMessage);
				}
            }else{
				if (waybillQueryDto.getWaybillNos().length < SettlementReportNumber.ONE_HUNDRED_AND_FIFTY
						|| waybillQueryDto.getWaybillNos().length > SettlementReportNumber.ONE_THOUSAND) {
					throw new SettlementException("运单号不能少于150，大于1000");
				}
                List<WaybillDetailEntity> waybillDetailEntities = waybill4FIMSDao.queryWaybill(waybillQueryDto);
                merge(waybillQueryDto,waybillDetailEntities);

            }
        }else{//统一结算 到付和非到付可以一起合并
            if(waybillQueryDto.getWaybillNos().length<SettlementReportNumber.ONE_HUNDRED_AND_FIFTY
                  || waybillQueryDto.getWaybillNos().length>SettlementReportNumber.ONE_THOUSAND){
                throw new SettlementException("运单号不能少于150，大于1000");
            }
            List<WaybillDetailEntity> waybillDetailEntities = waybill4FIMSDao.queryWaybill(waybillQueryDto);
            merge(waybillQueryDto,waybillDetailEntities);
        }
    }

    void merge(WaybillQueryDto waybillQueryDto, List<WaybillDetailEntity> waybillDetailEntityList){

        MergeWaybillEntity mergeWaybillEntity = new MergeWaybillEntity();
        String mergeWaybillNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HBW);
        mergeWaybillEntity.setId(UUIDUtils.getUUID());
        mergeWaybillEntity.setMergeWaybillNo(mergeWaybillNo);
        mergeWaybillEntity.setCreateDate(new Date());
        mergeWaybillEntity.setProduct(waybillQueryDto.getProduct());//业务类型
        mergeWaybillEntity.setInvoiceMark(waybillQueryDto.getInvoiceMark());//发票标记
        mergeWaybillEntity.setInvoiceHeadCode(waybillQueryDto.getInvoiceTitle());//发票抬头
        mergeWaybillEntity.setTaxId(waybillQueryDto.getTaxId());//税务登记号

        BigDecimal totalPrePayAmount = new BigDecimal(0);
        BigDecimal totalToPayAmount = new BigDecimal(0);

        String origDunningOrgCode = waybillDetailEntityList.get(0).getOrigDunningOrgCode();//出发催款部门
        String origDunningOrgName = waybillDetailEntityList.get(0).getOrigDunningOrgName();//出发催款部门
        String origContractOrgCode = waybillDetailEntityList.get(0).getOrigContractOrgCode();//出发合同部门
        String origContractOrgName = waybillDetailEntityList.get(0).getOrigContractOrgName();//出发合同部门
        String destDunningOrgCode = waybillDetailEntityList.get(0).getDestDunningOrgCode();//到达催款部门
        String destDunningOrgName = waybillDetailEntityList.get(0).getDestDunningOrgName();//到达催款部门
        String destContractOrgCode = waybillDetailEntityList.get(0).getDestContractOrgCode();//到达合同部门
        String destContractOrgName = waybillDetailEntityList.get(0).getDestContractOrgName();//到达合同部门

        Set<String> set1 = new HashSet();//保存出发催款部门的集合
        Set<String> set2 = new HashSet();
        Set<String> set3 = new HashSet();
        Set<String> set4 = new HashSet();

        String transLine ="";//开单时间最早的运输路线
        String receiveCustomerCode ="";//开单时间最早的收货客户编码
        String receiveCustomerName ="";
        String deliveryCustomerCode ="";
        String deliveryCustomerName ="";
        String receiveOrgCode ="";
        String receiveOrgName ="";
        String destOrgCode ="";
        String destOrgName ="";

        Long minTime = waybillDetailEntityList.get(0).getBillTime().getTime();//定义运单中开单时间最早的

        for(WaybillDetailEntity waybillDetailEntity : waybillDetailEntityList){

            totalPrePayAmount = totalPrePayAmount.add(waybillDetailEntity.getPrePayAmount());
            totalToPayAmount = totalToPayAmount.add(waybillDetailEntity.getToPayAmount());

            waybillDetailEntity.setMergeWaybillNo(mergeWaybillNo);//合并运单号

            long currentTime = waybillDetailEntity.getBillTime().getTime();
            set1.add(waybillDetailEntity.getOrigDunningOrgCode());
            set2.add(waybillDetailEntity.getOrigContractOrgCode());
            set3.add(waybillDetailEntity.getDestDunningOrgCode());
            set4.add(waybillDetailEntity.getDestContractOrgCode());

            if(currentTime<=minTime){
                minTime = currentTime;
                transLine = waybillDetailEntity.getTransferLine();//最早的运输路线
                receiveCustomerCode = waybillDetailEntity.getReceiveCustomerCode();
                receiveCustomerName = waybillDetailEntity.getReceiveCustomerName();
                deliveryCustomerCode = waybillDetailEntity.getDeliveryCustomerCode();
                deliveryCustomerName = waybillDetailEntity.getDeliveryCustomerName();
                receiveOrgCode = waybillDetailEntity.getReceiveOrgCode();
                receiveOrgName = waybillDetailEntity.getReceiveOrgName();
                destOrgCode = waybillDetailEntity.getDestOrgCode();
                destOrgName = waybillDetailEntity.getDestOrgName();

            }
            waybillDetailService.addWaybillDetailEntity(waybillDetailEntity);//运单详细入库
        }
        mergeWaybillEntity.setPrePayAmount(totalPrePayAmount);
        mergeWaybillEntity.setToPayAmount(totalToPayAmount);
        mergeWaybillEntity.setTransferLine(transLine);
        mergeWaybillEntity.setReceiveCustomerCode(receiveCustomerCode);
        mergeWaybillEntity.setReceiveCustomerName(receiveCustomerName);
        mergeWaybillEntity.setDeliveryCustomerCode(deliveryCustomerCode);
        mergeWaybillEntity.setDeliveryCustomerName(deliveryCustomerName);
        mergeWaybillEntity.setReceiveOrgCode(receiveOrgCode);
        mergeWaybillEntity.setReceiveOrgName(receiveOrgName);
        mergeWaybillEntity.setDescOrgCode(destOrgCode);
        mergeWaybillEntity.setDescOrgName(destOrgName);

        //设置出发催款部门，出发合同部门,到达催款部门，到达合同部门
        if(set1.size()==1){
            mergeWaybillEntity.setReceiveDunningDeptCode(origDunningOrgCode);
            mergeWaybillEntity.setReceiveDunningDeptName(origDunningOrgName);
        }
        if(set2.size()==1){
            mergeWaybillEntity.setReceiveContractDeptCode(origContractOrgCode);
            mergeWaybillEntity.setReceiveContractDeptName(origContractOrgName);

        }
        if(set3.size()==1){
            mergeWaybillEntity.setDescDunningDeptCode(destDunningOrgCode);
            mergeWaybillEntity.setDescDunningDeptName(destDunningOrgName);
        }
        if(set4.size()==1){
            mergeWaybillEntity.setDescContractDeptCode(destContractOrgCode);
            mergeWaybillEntity.setDescContractDeptName(destContractOrgName);
        }

        mergeWaybillService.addMergeWaybill(mergeWaybillEntity);//合并运单入库
    }
    /**
     * 根据客户编码，获取纳税人信息
     * 只有所有的发票抬头和税务登记号都一致才能同一纳税人
     * @param customerCodes 客户编码集合
     * @return
     */
    public Map<String,String> getTaxPayerInfo(List<String> customerCodes){
        TaxPayerInfoResponse taxPayerInfoResponse;//获取纳税人信息返回信息
        Map<String,String> map = new HashMap<String, String>();
        String invoiceTitle ="";
        String taxId = "";
        Set<String> invoiceTitleSet = new HashSet<String>();//保存发票抬头
        Set<String> taxIdSet = new HashSet<String>();//保存税务登记号

        //保存客户编码，如果客户编码相同，则取其中一个获取纳税人信息
        Set<String> customerCodeSet = new HashSet<String>();
        for(String customerCode:customerCodes){
            customerCodeSet.add(customerCode);
        }

        if(customerCodeSet.size()==1){
            List<String> sameCustomerCode =new ArrayList<String>();
            sameCustomerCode.add(customerCodeSet.iterator().next());
             taxPayerInfoResponse = taxPayerService.getTaxPayerByCustomerCode(sameCustomerCode);
            if(taxPayerInfoResponse==null){
                throw new SettlementException("获取纳税人信息为空");
            }
            if(taxPayerInfoResponse.getResult()==null || "0".equals(taxPayerInfoResponse.getResult())){
                throw new SettlementException(taxPayerInfoResponse.getMessage());
            }
            List<TaxPayerDto> taxPayerList = taxPayerInfoResponse.getTaxPayerList();
            invoiceTitle = taxPayerList.get(0).getFtitle();
            taxId = taxPayerList.get(0).getFtaxnumber();
        }else{
            taxPayerInfoResponse = taxPayerService.getTaxPayerByCustomerCode(customerCodes);
            if(taxPayerInfoResponse==null){
                throw new SettlementException("获取纳税人信息为空");
            }
            if(taxPayerInfoResponse.getResult()==null || "0".equals(taxPayerInfoResponse.getResult())){
                throw new SettlementException(taxPayerInfoResponse.getMessage());
            }
            List<TaxPayerDto> taxPayerList = taxPayerInfoResponse.getTaxPayerList();
            //请求有多少个客户编码，返回必须有相同个数的客户编码，才能做后续判断（是否是统一纳税人）
            if(taxPayerList==null || taxPayerList.size()!=customerCodes.size()){
                throw new SettlementException("纳税人信息不一致，请维护后重新查询");
            }
            if(CollectionUtils.isNotEmpty(taxPayerList)){
                for(TaxPayerDto dto:taxPayerList){
                    invoiceTitleSet.add(dto.getFtitle());
                    taxIdSet.add(dto.getFtaxnumber());
                }
            }
            //当集合值大小为1时，则说明只有一个值，说明发票抬头和税务登记号一致
            if(invoiceTitleSet.size()==1 && taxIdSet.size()==1){
                invoiceTitle = invoiceTitleSet.iterator().next();
                taxId = taxIdSet.iterator().next();
            }else{
                throw new SettlementException("纳税人信息不一致，请维护后重新查询");
            }
        }
        map.put("invoiceTitle",invoiceTitle);
        map.put("taxId",taxId);
        return map;
    }

    public void setWaybill4FIMSDao(IWaybill4FIMSDao waybill4FIMSDao) {
        this.waybill4FIMSDao = waybill4FIMSDao;
    }

    public void setWaybillDetailService(IWaybillDetailService waybillDetailService) {
        this.waybillDetailService = waybillDetailService;
    }

    public void setMergeWaybillService(IMergeWaybillService mergeWaybillService) {
        this.mergeWaybillService = mergeWaybillService;
    }

    public void setTaxPayerService(TaxPayerService taxPayerService) {
        this.taxPayerService = taxPayerService;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }
}
