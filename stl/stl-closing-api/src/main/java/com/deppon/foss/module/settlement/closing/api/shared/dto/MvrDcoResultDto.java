package com.deppon.foss.module.settlement.closing.api.shared.dto;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcoEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 折扣报表
 * 
 * @author 105762
 * @date 2015-02-04
 */
public class MvrDcoResultDto implements Serializable {
    private static final long serialVersionUID = -4166864369284787204L;

    private String userCode;

    private List<MvrDcoEntity> entityList;

    private Long count;

    /**
     * 签收时始发应收未核销预先折调整_公布价运费
     */
    private BigDecimal dcOrigPredctPodTransport;
    /**
     * 签收时到达应收未核销预先折调整_接货费
     */
    private BigDecimal dcOrigPredctPodPickup;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_送货费
     */
    private BigDecimal dcOrigPredctPodDelivery;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_包装费
     */
    private BigDecimal dcOrigPredctPodPackaging;
    /**
     * 签收时始发应收未核销实际折调整_保价费
     */
    private BigDecimal dcOrigPredctPodCod;
    /**
     * 签收时到达应收未核销实际折调整_代收货款手续费
     */
    private BigDecimal dcOrigPredctPodInsurance;
    /**
     * 签收时始发应收已核销实际折调整_其他费用
     */
    private BigDecimal dcOrigPredctPodOther;
    /**
     * 签收时到达应收已核销实际折调整_公布价运费
     */
    private BigDecimal dcDestPredctPodTransport;
    /**
     * 反签收时始发应收未核销预先折调整_接货费
     */
    private BigDecimal dcDestPredctPodPickup;
    /**
     * 反签收时到达应收未核销预先折调整_送货费
     */
    private BigDecimal dcDestPredctPodDelivery;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_包装费
     */
    private BigDecimal dcDestPredctPodPackaging;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_保价费
     */
    private BigDecimal dcDestPredctPodCod;
    /**
     * 反签收时始发应收未核销实际折调整_代收货款手续费
     */
    private BigDecimal dcDestPredctPodInsurance;
    /**
     * 反签收时到达应收未核销实际折调整_其他费用
     */
    private BigDecimal dcDestPredctPodOther;
    /**
     * 反签收时始发应收已核销实际折调整_公布价运费
     */
    private BigDecimal dcOrigDctDifPodTransport;
    /**
     * 反签收时到达应收已核销实际折调整_接货费
     */
    private BigDecimal dcOrigDctDifPodPickup;
    /**
     * 签收时始发应收未核销预先折调整_送货费
     */
    private BigDecimal dcOrigDctDifPodDelivery;
    /**
     * 签收时到达应收未核销预先折调整_包装费
     */
    private BigDecimal dcOrigDctDifPodPackaging;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_保价费
     */
    private BigDecimal dcOrigDctDifPodCod;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_代收货款手续费
     */
    private BigDecimal dcOrigDctDifPodInsurance;
    /**
     * 签收时始发应收未核销实际折调整_其他费用
     */
    private BigDecimal dcOrigDctDifPodOther;
    /**
     * 签收时到达应收未核销实际折调整_公布价运费
     */
    private BigDecimal dcDestDctDifPodTransport;
    /**
     * 签收时始发应收已核销实际折调整_接货费
     */
    private BigDecimal dcDestDctDifPodPickup;
    /**
     * 签收时到达应收已核销实际折调整_送货费
     */
    private BigDecimal dcDestDctDifPodDelivery;
    /**
     * 反签收时始发应收未核销预先折调整_包装费
     */
    private BigDecimal dcDestDctDifPodPackaging;
    /**
     * 反签收时到达应收未核销预先折调整_保价费
     */
    private BigDecimal dcDestDctDifPodCod;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_代收货款手续费
     */
    private BigDecimal dcDestDctDifPodInsurance;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_其他费用
     */
    private BigDecimal dcDestDctDifPodOther;
    /**
     * 反签收时始发应收未核销实际折调整_公布价运费
     */
    private BigDecimal dcOrigActdctPodTransport;
    /**
     * 反签收时到达应收未核销实际折调整_接货费
     */
    private BigDecimal dcOrigActdctPodPickup;
    /**
     * 反签收时始发应收已核销实际折调整_送货费
     */
    private BigDecimal dcOrigActdctPodDelivery;
    /**
     * 反签收时到达应收已核销实际折调整_包装费
     */
    private BigDecimal dcOrigActdctPodPackaging;
    /**
     * 签收时始发应收未核销预先折调整_保价费
     */
    private BigDecimal dcOrigActdctPodCod;
    /**
     * 签收时到达应收未核销预先折调整_代收货款手续费
     */
    private BigDecimal dcOrigActdctPodInsurance;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_其他费用
     */
    private BigDecimal dcOrigActdctPodOther;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_公布价运费
     */
    private BigDecimal dcDestActdctPodTransport;
    /**
     * 签收时始发应收未核销实际折调整_接货费
     */
    private BigDecimal dcDestActdctPodPickup;
    /**
     * 签收时到达应收未核销实际折调整_送货费
     */
    private BigDecimal dcDestActdctPodDelivery;
    /**
     * 签收时始发应收已核销实际折调整_包装费
     */
    private BigDecimal dcDestActdctPodPackaging;
    /**
     * 签收时到达应收已核销实际折调整_保价费
     */
    private BigDecimal dcDestActdctPodCod;
    /**
     * 反签收时始发应收未核销预先折调整_代收货款手续费
     */
    private BigDecimal dcDestActdctPodInsurance;
    /**
     * 反签收时到达应收未核销预先折调整_其他费用
     */
    private BigDecimal dcDestActdctPodOther;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_公布价运费
     */
    private BigDecimal dcOrigActdctNpodTransport;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_接货费
     */
    private BigDecimal dcOrigActdctNpodPickup;
    /**
     * 反签收时始发应收未核销实际折调整_送货费
     */
    private BigDecimal dcOrigActdctNpodDelivery;
    /**
     * 反签收时到达应收未核销实际折调整_包装费
     */
    private BigDecimal dcOrigActdctNpodPackaging;
    /**
     * 反签收时始发应收已核销实际折调整_保价费
     */
    private BigDecimal dcOrigActdctNpodCod;
    /**
     * 反签收时到达应收已核销实际折调整_代收货款手续费
     */
    private BigDecimal dcOrigActdctNpodInsurance;
    /**
     * 签收时始发应收未核销预先折调整_其他费用
     */
    private BigDecimal dcOrigActdctNpodOther;
    /**
     * 签收时到达应收未核销预先折调整_公布价运费
     */
    private BigDecimal dcDestActdctNpodTransport;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_接货费
     */
    private BigDecimal dcDestActdctNpodPickup;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_送货费
     */
    private BigDecimal dcDestActdctNpodDelivery;
    /**
     * 签收时始发应收未核销实际折调整_包装费
     */
    private BigDecimal dcDestActdctNpodPackaging;
    /**
     * 签收时到达应收未核销实际折调整_保价费
     */
    private BigDecimal dcDestActdctNpodCod;
    /**
     * 签收时始发应收已核销实际折调整_代收货款手续费
     */
    private BigDecimal dcDestActdctNpodInsurance;
    /**
     * 签收时到达应收已核销实际折调整_其他费用
     */
    private BigDecimal dcDestActdctNpodOther;
    /**
     * 反签收时始发应收未核销预先折调整_公布价运费
     */
    private BigDecimal dcOrigPredctRpodTransport;
    /**
     * 反签收时到达应收未核销预先折调整_接货费
     */
    private BigDecimal dcOrigPredctRpodPickup;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_送货费
     */
    private BigDecimal dcOrigPredctRpodDelivery;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_包装费
     */
    private BigDecimal dcOrigPredctRpodPackaging;
    /**
     * 反签收时始发应收未核销实际折调整_保价费
     */
    private BigDecimal dcOrigPredctRpodCod;
    /**
     * 反签收时到达应收未核销实际折调整_代收货款手续费
     */
    private BigDecimal dcOrigPredctRpodInsurance;
    /**
     * 反签收时始发应收已核销实际折调整_其他费用
     */
    private BigDecimal dcOrigPredctRpodOther;
    /**
     * 反签收时到达应收已核销实际折调整_公布价运费
     */
    private BigDecimal dcDestPredctRpodTransport;
    /**
     * 签收时始发应收未核销预先折调整_接货费
     */
    private BigDecimal dcDestPredctRpodPickup;
    /**
     * 签收时到达应收未核销预先折调整_送货费
     */
    private BigDecimal dcDestPredctRpodDelivery;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_包装费
     */
    private BigDecimal dcDestPredctRpodPackaging;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_保价费
     */
    private BigDecimal dcDestPredctRpodCod;
    /**
     * 签收时始发应收未核销实际折调整_代收货款手续费
     */
    private BigDecimal dcDestPredctRpodInsurance;
    /**
     * 签收时到达应收未核销实际折调整_其他费用
     */
    private BigDecimal dcDestPredctRpodOther;
    /**
     * 签收时始发应收已核销实际折调整_公布价运费
     */
    private BigDecimal dcOrigDctDifRpodTransport;
    /**
     * 签收时到达应收已核销实际折调整_接货费
     */
    private BigDecimal dcOrigDctDifRpodPickup;
    /**
     * 反签收时始发应收未核销预先折调整_送货费
     */
    private BigDecimal dcOrigDctDifRpodDelivery;
    /**
     * 反签收时到达应收未核销预先折调整_包装费
     */
    private BigDecimal dcOrigDctDifRpodPackaging;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_保价费
     */
    private BigDecimal dcOrigDctDifRpodCod;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_代收货款手续费
     */
    private BigDecimal dcOrigDctDifRpodInsurance;
    /**
     * 反签收时始发应收未核销实际折调整_其他费用
     */
    private BigDecimal dcOrigDctDifRpodOther;
    /**
     * 反签收时到达应收未核销实际折调整_公布价运费
     */
    private BigDecimal dcDestDctDifRpodTransport;
    /**
     * 反签收时始发应收已核销实际折调整_接货费
     */
    private BigDecimal dcDestDctDifRpodPickup;
    /**
     * 反签收时到达应收已核销实际折调整_送货费
     */
    private BigDecimal dcDestDctDifRpodDelivery;
    /**
     * 签收时始发应收未核销预先折调整_包装费
     */
    private BigDecimal dcDestDctDifRpodPackaging;
    /**
     * 签收时到达应收未核销预先折调整_保价费
     */
    private BigDecimal dcDestDctDifRpodCod;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_代收货款手续费
     */
    private BigDecimal dcDestDctDifRpodInsurance;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_其他费用
     */
    private BigDecimal dcDestDctDifRpodOther;
    /**
     * 签收时始发应收未核销实际折调整_公布价运费
     */
    private BigDecimal dcOrigActdctRpodTransport;
    /**
     * 签收时到达应收未核销实际折调整_接货费
     */
    private BigDecimal dcOrigActdctRpodPickup;
    /**
     * 签收时始发应收已核销实际折调整_送货费
     */
    private BigDecimal dcOrigActdctRpodDelivery;
    /**
     * 签收时到达应收已核销实际折调整_包装费
     */
    private BigDecimal dcOrigActdctRpodPackaging;
    /**
     * 反签收时始发应收未核销预先折调整_保价费
     */
    private BigDecimal dcOrigActdctRpodCod;
    /**
     * 反签收时到达应收未核销预先折调整_代收货款手续费
     */
    private BigDecimal dcOrigActdctRpodInsurance;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_其他费用
     */
    private BigDecimal dcOrigActdctRpodOther;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_公布价运费
     */
    private BigDecimal dcDestActdctRpodTransport;
    /**
     * 反签收时始发应收未核销实际折调整_接货费
     */
    private BigDecimal dcDestActdctRpodPickup;
    /**
     * 反签收时到达应收未核销实际折调整_送货费
     */
    private BigDecimal dcDestActdctRpodDelivery;
    /**
     * 反签收时始发应收已核销实际折调整_包装费
     */
    private BigDecimal dcDestActdctRpodPackaging;
    /**
     * 反签收时到达应收已核销实际折调整_保价费
     */
    private BigDecimal dcDestActdctRpodCod;
    /**
     * 签收时始发应收未核销预先折调整_代收货款手续费
     */
    private BigDecimal dcDestActdctRpodInsurance;
    /**
     * 签收时到达应收未核销预先折调整_其他费用
     */
    private BigDecimal dcDestActdctRpodOther;
    /**
     * 签收时始发应收未核销预先折与实际折差额调整_公布价运费
     */
    private BigDecimal dcOrigActdctRnpodTransport;
    /**
     * 签收时到达应收未核销预先折与实际折差额调整_接货费
     */
    private BigDecimal dcOrigActdctRnpodPickup;
    /**
     * 签收时始发应收未核销实际折调整_送货费
     */
    private BigDecimal dcOrigActdctRnpodDelivery;
    /**
     * 签收时到达应收未核销实际折调整_包装费
     */
    private BigDecimal dcOrigActdctRnpodPackaging;
    /**
     * 签收时始发应收已核销实际折调整_保价费
     */
    private BigDecimal dcOrigActdctRnpodCod;
    /**
     * 签收时到达应收已核销实际折调整_代收货款手续费
     */
    private BigDecimal dcOrigActdctRnpodInsurance;
    /**
     * 反签收时始发应收未核销预先折调整_其他费用
     */
    private BigDecimal dcOrigActdctRnpodOther;
    /**
     * 反签收时到达应收未核销预先折调整_公布价运费
     */
    private BigDecimal dcDestActdctRnpodTransport;
    /**
     * 反签收时始发应收未核销预先折与实际折差额调整_接货费
     */
    private BigDecimal dcDestActdctRnpodPickup;
    /**
     * 反签收时到达应收未核销预先折与实际折差额调整_送货费
     */
    private BigDecimal dcDestActdctRnpodDelivery;
    /**
     * 反签收时始发应收未核销实际折调整_包装费
     */
    private BigDecimal dcDestActdctRnpodPackaging;
    /**
     * 反签收时到达应收未核销实际折调整_保价费
     */
    private BigDecimal dcDestActdctRnpodCod;
    /**
     * 反签收时始发应收已核销实际折调整_代收货款手续费
     */
    private BigDecimal dcDestActdctRnpodInsurance;
    /**
     * 反签收时到达应收已核销实际折调整_其他费用
     */
    private BigDecimal dcDestActdctRnpodOther;
    /**
     * 理赔-出发部门申请-02理赔冲收入调整
     */
    private BigDecimal dcOrigIncomet;
    /**
     * 理赔-出发部门申请-02理赔入成本调整
     */
    private BigDecimal dcOrigCostt;
    /**
     * 理赔-到达部门申请
     */
    private BigDecimal dcDestApplyT;
    /**
     * 异常冲收入-02应收始发运费已签收调整
     */
    private BigDecimal dcExOrigRcvtPod;
    /**
     * 异常冲收入-02应收到付运费已签收调整
     */
    private BigDecimal dcExDestRcvtPod;
    
    private String invoiceMark;  /*发票标记*/
    private BigDecimal ldcExtOrNwo;  /*事后折扣【02】-特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付未核销*/
    private BigDecimal ldcExtOrWo;  /*事后折扣【02】-特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付已核销*/
    private BigDecimal ldcOroNwoActPodTransport;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-公布价运费*/
    private BigDecimal ldcOroNwoActPodPickup;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-接货费*/
    private BigDecimal ldcOroNwoActPodDelivery;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-送货费*/
    private BigDecimal ldcOroNwoActPodPackaging;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-包装费*/
    private BigDecimal ldcOroNwoActPodInsurance;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-保价费*/
    private BigDecimal ldcOroNwoActPodCod;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-代收货款手续费*/
    private BigDecimal ldcOroNwoActPodOther;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收未核销实际折调整-其他费用*/
    private BigDecimal ldcOroWoActPodTransport;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-公布价运费*/
    private BigDecimal ldcOroWoActPodPickup;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-接货费*/
    private BigDecimal ldcOroWoActPodDelivery;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-送货费*/
    private BigDecimal ldcOroWoActPodPackaging;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-包装费*/
    private BigDecimal ldcOroWoActPodInsurance;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-保价费*/
    private BigDecimal ldcOroWoActPodCod;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-代收货款手续费*/
    private BigDecimal ldcOroWoActPodOther;  /*事后折扣【01】-收入调整（签收单）-签收时始发应收已核销实际折调整-其他费用*/
    private BigDecimal ldcOroNwoActRpodTransport;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-公布价运费*/
    private BigDecimal ldcOroNwoActRpodPickup;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-接货费*/
    private BigDecimal ldcOroNwoActRpodDelivery;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-送货费*/
    private BigDecimal ldcOroNwoActRpodPackaging;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-包装费*/
    private BigDecimal ldcOroNwoActRpodInsurance;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-保价费*/
    private BigDecimal ldcOroNwoActRpodCod;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-代收货款手续费*/
    private BigDecimal ldcOroNwoActRpodOther;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收未核销实际折调整-其他费用*/
    private BigDecimal ldcOroWoActRpodTransport;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-公布价运费*/
    private BigDecimal ldcOroWoActRpodPickup;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-接货费*/
    private BigDecimal ldcOroWoActRpodDelivery;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-送货费*/
    private BigDecimal ldcOroWoActRpodPackaging;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-包装费*/
    private BigDecimal ldcOroWoActRpodInsurance;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-保价费*/
    private BigDecimal ldcOroWoActRpodCod;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-代收货款手续费*/
    private BigDecimal ldcOroWoActRpodOther;  /*事后折扣【01】-收入调整（反签收单）-反签收时始发应收已核销实际折调整-其他费用*/
    private BigDecimal ldcOrigIncomeo;  /*事后折扣【01】-特殊业务调整-理赔-出发部门申请-01理赔冲收入调整*/
    private BigDecimal ldcOrigCosto;  /*事后折扣【01】-特殊业务调整-理赔-出发部门申请-01理赔入成本调整*/
    private BigDecimal ldcDestIncomeo;  /*事后折扣【01】-特殊业务调整-理赔-到达部门申请-01理赔冲收入调整*/
    private BigDecimal ldcExoOrPod;  /*事后折扣【01】-特殊业务调整-异常冲收入-01应收始发运费已签收调整*/
    private BigDecimal ldcExoOrNwo;  /*事后折扣【01】-特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付未核销*/
    private BigDecimal ldcExoOrWo;  /*事后折扣【01】-特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付已核销*/


    /* getters & setters */

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<MvrDcoEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<MvrDcoEntity> entityList) {
        this.entityList = entityList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getDcOrigPredctPodTransport() {
        return dcOrigPredctPodTransport;
    }

    public void setDcOrigPredctPodTransport(BigDecimal dcOrigPredctPodTransport) {
        this.dcOrigPredctPodTransport = dcOrigPredctPodTransport;
    }

    public BigDecimal getDcOrigPredctPodPickup() {
        return dcOrigPredctPodPickup;
    }

    public void setDcOrigPredctPodPickup(BigDecimal dcOrigPredctPodPickup) {
        this.dcOrigPredctPodPickup = dcOrigPredctPodPickup;
    }

    public BigDecimal getDcOrigPredctPodDelivery() {
        return dcOrigPredctPodDelivery;
    }

    public void setDcOrigPredctPodDelivery(BigDecimal dcOrigPredctPodDelivery) {
        this.dcOrigPredctPodDelivery = dcOrigPredctPodDelivery;
    }

    public BigDecimal getDcOrigPredctPodPackaging() {
        return dcOrigPredctPodPackaging;
    }

    public void setDcOrigPredctPodPackaging(BigDecimal dcOrigPredctPodPackaging) {
        this.dcOrigPredctPodPackaging = dcOrigPredctPodPackaging;
    }

    public BigDecimal getDcOrigPredctPodCod() {
        return dcOrigPredctPodCod;
    }

    public void setDcOrigPredctPodCod(BigDecimal dcOrigPredctPodCod) {
        this.dcOrigPredctPodCod = dcOrigPredctPodCod;
    }

    public BigDecimal getDcOrigPredctPodInsurance() {
        return dcOrigPredctPodInsurance;
    }

    public void setDcOrigPredctPodInsurance(BigDecimal dcOrigPredctPodInsurance) {
        this.dcOrigPredctPodInsurance = dcOrigPredctPodInsurance;
    }

    public BigDecimal getDcOrigPredctPodOther() {
        return dcOrigPredctPodOther;
    }

    public void setDcOrigPredctPodOther(BigDecimal dcOrigPredctPodOther) {
        this.dcOrigPredctPodOther = dcOrigPredctPodOther;
    }

    public BigDecimal getDcDestPredctPodTransport() {
        return dcDestPredctPodTransport;
    }

    public void setDcDestPredctPodTransport(BigDecimal dcDestPredctPodTransport) {
        this.dcDestPredctPodTransport = dcDestPredctPodTransport;
    }

    public BigDecimal getDcDestPredctPodPickup() {
        return dcDestPredctPodPickup;
    }

    public void setDcDestPredctPodPickup(BigDecimal dcDestPredctPodPickup) {
        this.dcDestPredctPodPickup = dcDestPredctPodPickup;
    }

    public BigDecimal getDcDestPredctPodDelivery() {
        return dcDestPredctPodDelivery;
    }

    public void setDcDestPredctPodDelivery(BigDecimal dcDestPredctPodDelivery) {
        this.dcDestPredctPodDelivery = dcDestPredctPodDelivery;
    }

    public BigDecimal getDcDestPredctPodPackaging() {
        return dcDestPredctPodPackaging;
    }

    public void setDcDestPredctPodPackaging(BigDecimal dcDestPredctPodPackaging) {
        this.dcDestPredctPodPackaging = dcDestPredctPodPackaging;
    }

    public BigDecimal getDcDestPredctPodCod() {
        return dcDestPredctPodCod;
    }

    public void setDcDestPredctPodCod(BigDecimal dcDestPredctPodCod) {
        this.dcDestPredctPodCod = dcDestPredctPodCod;
    }

    public BigDecimal getDcDestPredctPodInsurance() {
        return dcDestPredctPodInsurance;
    }

    public void setDcDestPredctPodInsurance(BigDecimal dcDestPredctPodInsurance) {
        this.dcDestPredctPodInsurance = dcDestPredctPodInsurance;
    }

    public BigDecimal getDcDestPredctPodOther() {
        return dcDestPredctPodOther;
    }

    public void setDcDestPredctPodOther(BigDecimal dcDestPredctPodOther) {
        this.dcDestPredctPodOther = dcDestPredctPodOther;
    }

    public BigDecimal getDcOrigDctDifPodTransport() {
        return dcOrigDctDifPodTransport;
    }

    public void setDcOrigDctDifPodTransport(BigDecimal dcOrigDctDifPodTransport) {
        this.dcOrigDctDifPodTransport = dcOrigDctDifPodTransport;
    }

    public BigDecimal getDcOrigDctDifPodPickup() {
        return dcOrigDctDifPodPickup;
    }

    public void setDcOrigDctDifPodPickup(BigDecimal dcOrigDctDifPodPickup) {
        this.dcOrigDctDifPodPickup = dcOrigDctDifPodPickup;
    }

    public BigDecimal getDcOrigDctDifPodDelivery() {
        return dcOrigDctDifPodDelivery;
    }

    public void setDcOrigDctDifPodDelivery(BigDecimal dcOrigDctDifPodDelivery) {
        this.dcOrigDctDifPodDelivery = dcOrigDctDifPodDelivery;
    }

    public BigDecimal getDcOrigDctDifPodPackaging() {
        return dcOrigDctDifPodPackaging;
    }

    public void setDcOrigDctDifPodPackaging(BigDecimal dcOrigDctDifPodPackaging) {
        this.dcOrigDctDifPodPackaging = dcOrigDctDifPodPackaging;
    }

    public BigDecimal getDcOrigDctDifPodCod() {
        return dcOrigDctDifPodCod;
    }

    public void setDcOrigDctDifPodCod(BigDecimal dcOrigDctDifPodCod) {
        this.dcOrigDctDifPodCod = dcOrigDctDifPodCod;
    }

    public BigDecimal getDcOrigDctDifPodInsurance() {
        return dcOrigDctDifPodInsurance;
    }

    public void setDcOrigDctDifPodInsurance(BigDecimal dcOrigDctDifPodInsurance) {
        this.dcOrigDctDifPodInsurance = dcOrigDctDifPodInsurance;
    }

    public BigDecimal getDcOrigDctDifPodOther() {
        return dcOrigDctDifPodOther;
    }

    public void setDcOrigDctDifPodOther(BigDecimal dcOrigDctDifPodOther) {
        this.dcOrigDctDifPodOther = dcOrigDctDifPodOther;
    }

    public BigDecimal getDcDestDctDifPodTransport() {
        return dcDestDctDifPodTransport;
    }

    public void setDcDestDctDifPodTransport(BigDecimal dcDestDctDifPodTransport) {
        this.dcDestDctDifPodTransport = dcDestDctDifPodTransport;
    }

    public BigDecimal getDcDestDctDifPodPickup() {
        return dcDestDctDifPodPickup;
    }

    public void setDcDestDctDifPodPickup(BigDecimal dcDestDctDifPodPickup) {
        this.dcDestDctDifPodPickup = dcDestDctDifPodPickup;
    }

    public BigDecimal getDcDestDctDifPodDelivery() {
        return dcDestDctDifPodDelivery;
    }

    public void setDcDestDctDifPodDelivery(BigDecimal dcDestDctDifPodDelivery) {
        this.dcDestDctDifPodDelivery = dcDestDctDifPodDelivery;
    }

    public BigDecimal getDcDestDctDifPodPackaging() {
        return dcDestDctDifPodPackaging;
    }

    public void setDcDestDctDifPodPackaging(BigDecimal dcDestDctDifPodPackaging) {
        this.dcDestDctDifPodPackaging = dcDestDctDifPodPackaging;
    }

    public BigDecimal getDcDestDctDifPodCod() {
        return dcDestDctDifPodCod;
    }

    public void setDcDestDctDifPodCod(BigDecimal dcDestDctDifPodCod) {
        this.dcDestDctDifPodCod = dcDestDctDifPodCod;
    }

    public BigDecimal getDcDestDctDifPodInsurance() {
        return dcDestDctDifPodInsurance;
    }

    public void setDcDestDctDifPodInsurance(BigDecimal dcDestDctDifPodInsurance) {
        this.dcDestDctDifPodInsurance = dcDestDctDifPodInsurance;
    }

    public BigDecimal getDcDestDctDifPodOther() {
        return dcDestDctDifPodOther;
    }

    public void setDcDestDctDifPodOther(BigDecimal dcDestDctDifPodOther) {
        this.dcDestDctDifPodOther = dcDestDctDifPodOther;
    }

    public BigDecimal getDcOrigActdctPodTransport() {
        return dcOrigActdctPodTransport;
    }

    public void setDcOrigActdctPodTransport(BigDecimal dcOrigActdctPodTransport) {
        this.dcOrigActdctPodTransport = dcOrigActdctPodTransport;
    }

    public BigDecimal getDcOrigActdctPodPickup() {
        return dcOrigActdctPodPickup;
    }

    public void setDcOrigActdctPodPickup(BigDecimal dcOrigActdctPodPickup) {
        this.dcOrigActdctPodPickup = dcOrigActdctPodPickup;
    }

    public BigDecimal getDcOrigActdctPodDelivery() {
        return dcOrigActdctPodDelivery;
    }

    public void setDcOrigActdctPodDelivery(BigDecimal dcOrigActdctPodDelivery) {
        this.dcOrigActdctPodDelivery = dcOrigActdctPodDelivery;
    }

    public BigDecimal getDcOrigActdctPodPackaging() {
        return dcOrigActdctPodPackaging;
    }

    public void setDcOrigActdctPodPackaging(BigDecimal dcOrigActdctPodPackaging) {
        this.dcOrigActdctPodPackaging = dcOrigActdctPodPackaging;
    }

    public BigDecimal getDcOrigActdctPodCod() {
        return dcOrigActdctPodCod;
    }

    public void setDcOrigActdctPodCod(BigDecimal dcOrigActdctPodCod) {
        this.dcOrigActdctPodCod = dcOrigActdctPodCod;
    }

    public BigDecimal getDcOrigActdctPodInsurance() {
        return dcOrigActdctPodInsurance;
    }

    public void setDcOrigActdctPodInsurance(BigDecimal dcOrigActdctPodInsurance) {
        this.dcOrigActdctPodInsurance = dcOrigActdctPodInsurance;
    }

    public BigDecimal getDcOrigActdctPodOther() {
        return dcOrigActdctPodOther;
    }

    public void setDcOrigActdctPodOther(BigDecimal dcOrigActdctPodOther) {
        this.dcOrigActdctPodOther = dcOrigActdctPodOther;
    }

    public BigDecimal getDcDestActdctPodTransport() {
        return dcDestActdctPodTransport;
    }

    public void setDcDestActdctPodTransport(BigDecimal dcDestActdctPodTransport) {
        this.dcDestActdctPodTransport = dcDestActdctPodTransport;
    }

    public BigDecimal getDcDestActdctPodPickup() {
        return dcDestActdctPodPickup;
    }

    public void setDcDestActdctPodPickup(BigDecimal dcDestActdctPodPickup) {
        this.dcDestActdctPodPickup = dcDestActdctPodPickup;
    }

    public BigDecimal getDcDestActdctPodDelivery() {
        return dcDestActdctPodDelivery;
    }

    public void setDcDestActdctPodDelivery(BigDecimal dcDestActdctPodDelivery) {
        this.dcDestActdctPodDelivery = dcDestActdctPodDelivery;
    }

    public BigDecimal getDcDestActdctPodPackaging() {
        return dcDestActdctPodPackaging;
    }

    public void setDcDestActdctPodPackaging(BigDecimal dcDestActdctPodPackaging) {
        this.dcDestActdctPodPackaging = dcDestActdctPodPackaging;
    }

    public BigDecimal getDcDestActdctPodCod() {
        return dcDestActdctPodCod;
    }

    public void setDcDestActdctPodCod(BigDecimal dcDestActdctPodCod) {
        this.dcDestActdctPodCod = dcDestActdctPodCod;
    }

    public BigDecimal getDcDestActdctPodInsurance() {
        return dcDestActdctPodInsurance;
    }

    public void setDcDestActdctPodInsurance(BigDecimal dcDestActdctPodInsurance) {
        this.dcDestActdctPodInsurance = dcDestActdctPodInsurance;
    }

    public BigDecimal getDcDestActdctPodOther() {
        return dcDestActdctPodOther;
    }

    public void setDcDestActdctPodOther(BigDecimal dcDestActdctPodOther) {
        this.dcDestActdctPodOther = dcDestActdctPodOther;
    }

    public BigDecimal getDcOrigActdctNpodTransport() {
        return dcOrigActdctNpodTransport;
    }

    public void setDcOrigActdctNpodTransport(BigDecimal dcOrigActdctNpodTransport) {
        this.dcOrigActdctNpodTransport = dcOrigActdctNpodTransport;
    }

    public BigDecimal getDcOrigActdctNpodPickup() {
        return dcOrigActdctNpodPickup;
    }

    public void setDcOrigActdctNpodPickup(BigDecimal dcOrigActdctNpodPickup) {
        this.dcOrigActdctNpodPickup = dcOrigActdctNpodPickup;
    }

    public BigDecimal getDcOrigActdctNpodDelivery() {
        return dcOrigActdctNpodDelivery;
    }

    public void setDcOrigActdctNpodDelivery(BigDecimal dcOrigActdctNpodDelivery) {
        this.dcOrigActdctNpodDelivery = dcOrigActdctNpodDelivery;
    }

    public BigDecimal getDcOrigActdctNpodPackaging() {
        return dcOrigActdctNpodPackaging;
    }

    public void setDcOrigActdctNpodPackaging(BigDecimal dcOrigActdctNpodPackaging) {
        this.dcOrigActdctNpodPackaging = dcOrigActdctNpodPackaging;
    }

    public BigDecimal getDcOrigActdctNpodCod() {
        return dcOrigActdctNpodCod;
    }

    public void setDcOrigActdctNpodCod(BigDecimal dcOrigActdctNpodCod) {
        this.dcOrigActdctNpodCod = dcOrigActdctNpodCod;
    }

    public BigDecimal getDcOrigActdctNpodInsurance() {
        return dcOrigActdctNpodInsurance;
    }

    public void setDcOrigActdctNpodInsurance(BigDecimal dcOrigActdctNpodInsurance) {
        this.dcOrigActdctNpodInsurance = dcOrigActdctNpodInsurance;
    }

    public BigDecimal getDcOrigActdctNpodOther() {
        return dcOrigActdctNpodOther;
    }

    public void setDcOrigActdctNpodOther(BigDecimal dcOrigActdctNpodOther) {
        this.dcOrigActdctNpodOther = dcOrigActdctNpodOther;
    }

    public BigDecimal getDcDestActdctNpodTransport() {
        return dcDestActdctNpodTransport;
    }

    public void setDcDestActdctNpodTransport(BigDecimal dcDestActdctNpodTransport) {
        this.dcDestActdctNpodTransport = dcDestActdctNpodTransport;
    }

    public BigDecimal getDcDestActdctNpodPickup() {
        return dcDestActdctNpodPickup;
    }

    public void setDcDestActdctNpodPickup(BigDecimal dcDestActdctNpodPickup) {
        this.dcDestActdctNpodPickup = dcDestActdctNpodPickup;
    }

    public BigDecimal getDcDestActdctNpodDelivery() {
        return dcDestActdctNpodDelivery;
    }

    public void setDcDestActdctNpodDelivery(BigDecimal dcDestActdctNpodDelivery) {
        this.dcDestActdctNpodDelivery = dcDestActdctNpodDelivery;
    }

    public BigDecimal getDcDestActdctNpodPackaging() {
        return dcDestActdctNpodPackaging;
    }

    public void setDcDestActdctNpodPackaging(BigDecimal dcDestActdctNpodPackaging) {
        this.dcDestActdctNpodPackaging = dcDestActdctNpodPackaging;
    }

    public BigDecimal getDcDestActdctNpodCod() {
        return dcDestActdctNpodCod;
    }

    public void setDcDestActdctNpodCod(BigDecimal dcDestActdctNpodCod) {
        this.dcDestActdctNpodCod = dcDestActdctNpodCod;
    }

    public BigDecimal getDcDestActdctNpodInsurance() {
        return dcDestActdctNpodInsurance;
    }

    public void setDcDestActdctNpodInsurance(BigDecimal dcDestActdctNpodInsurance) {
        this.dcDestActdctNpodInsurance = dcDestActdctNpodInsurance;
    }

    public BigDecimal getDcDestActdctNpodOther() {
        return dcDestActdctNpodOther;
    }

    public void setDcDestActdctNpodOther(BigDecimal dcDestActdctNpodOther) {
        this.dcDestActdctNpodOther = dcDestActdctNpodOther;
    }

    public BigDecimal getDcOrigPredctRpodTransport() {
        return dcOrigPredctRpodTransport;
    }

    public void setDcOrigPredctRpodTransport(BigDecimal dcOrigPredctRpodTransport) {
        this.dcOrigPredctRpodTransport = dcOrigPredctRpodTransport;
    }

    public BigDecimal getDcOrigPredctRpodPickup() {
        return dcOrigPredctRpodPickup;
    }

    public void setDcOrigPredctRpodPickup(BigDecimal dcOrigPredctRpodPickup) {
        this.dcOrigPredctRpodPickup = dcOrigPredctRpodPickup;
    }

    public BigDecimal getDcOrigPredctRpodDelivery() {
        return dcOrigPredctRpodDelivery;
    }

    public void setDcOrigPredctRpodDelivery(BigDecimal dcOrigPredctRpodDelivery) {
        this.dcOrigPredctRpodDelivery = dcOrigPredctRpodDelivery;
    }

    public BigDecimal getDcOrigPredctRpodPackaging() {
        return dcOrigPredctRpodPackaging;
    }

    public void setDcOrigPredctRpodPackaging(BigDecimal dcOrigPredctRpodPackaging) {
        this.dcOrigPredctRpodPackaging = dcOrigPredctRpodPackaging;
    }

    public BigDecimal getDcOrigPredctRpodCod() {
        return dcOrigPredctRpodCod;
    }

    public void setDcOrigPredctRpodCod(BigDecimal dcOrigPredctRpodCod) {
        this.dcOrigPredctRpodCod = dcOrigPredctRpodCod;
    }

    public BigDecimal getDcOrigPredctRpodInsurance() {
        return dcOrigPredctRpodInsurance;
    }

    public void setDcOrigPredctRpodInsurance(BigDecimal dcOrigPredctRpodInsurance) {
        this.dcOrigPredctRpodInsurance = dcOrigPredctRpodInsurance;
    }

    public BigDecimal getDcOrigPredctRpodOther() {
        return dcOrigPredctRpodOther;
    }

    public void setDcOrigPredctRpodOther(BigDecimal dcOrigPredctRpodOther) {
        this.dcOrigPredctRpodOther = dcOrigPredctRpodOther;
    }

    public BigDecimal getDcDestPredctRpodTransport() {
        return dcDestPredctRpodTransport;
    }

    public void setDcDestPredctRpodTransport(BigDecimal dcDestPredctRpodTransport) {
        this.dcDestPredctRpodTransport = dcDestPredctRpodTransport;
    }

    public BigDecimal getDcDestPredctRpodPickup() {
        return dcDestPredctRpodPickup;
    }

    public void setDcDestPredctRpodPickup(BigDecimal dcDestPredctRpodPickup) {
        this.dcDestPredctRpodPickup = dcDestPredctRpodPickup;
    }

    public BigDecimal getDcDestPredctRpodDelivery() {
        return dcDestPredctRpodDelivery;
    }

    public void setDcDestPredctRpodDelivery(BigDecimal dcDestPredctRpodDelivery) {
        this.dcDestPredctRpodDelivery = dcDestPredctRpodDelivery;
    }

    public BigDecimal getDcDestPredctRpodPackaging() {
        return dcDestPredctRpodPackaging;
    }

    public void setDcDestPredctRpodPackaging(BigDecimal dcDestPredctRpodPackaging) {
        this.dcDestPredctRpodPackaging = dcDestPredctRpodPackaging;
    }

    public BigDecimal getDcDestPredctRpodCod() {
        return dcDestPredctRpodCod;
    }

    public void setDcDestPredctRpodCod(BigDecimal dcDestPredctRpodCod) {
        this.dcDestPredctRpodCod = dcDestPredctRpodCod;
    }

    public BigDecimal getDcDestPredctRpodInsurance() {
        return dcDestPredctRpodInsurance;
    }

    public void setDcDestPredctRpodInsurance(BigDecimal dcDestPredctRpodInsurance) {
        this.dcDestPredctRpodInsurance = dcDestPredctRpodInsurance;
    }

    public BigDecimal getDcDestPredctRpodOther() {
        return dcDestPredctRpodOther;
    }

    public void setDcDestPredctRpodOther(BigDecimal dcDestPredctRpodOther) {
        this.dcDestPredctRpodOther = dcDestPredctRpodOther;
    }

    public BigDecimal getDcOrigDctDifRpodTransport() {
        return dcOrigDctDifRpodTransport;
    }

    public void setDcOrigDctDifRpodTransport(BigDecimal dcOrigDctDifRpodTransport) {
        this.dcOrigDctDifRpodTransport = dcOrigDctDifRpodTransport;
    }

    public BigDecimal getDcOrigDctDifRpodPickup() {
        return dcOrigDctDifRpodPickup;
    }

    public void setDcOrigDctDifRpodPickup(BigDecimal dcOrigDctDifRpodPickup) {
        this.dcOrigDctDifRpodPickup = dcOrigDctDifRpodPickup;
    }

    public BigDecimal getDcOrigDctDifRpodDelivery() {
        return dcOrigDctDifRpodDelivery;
    }

    public void setDcOrigDctDifRpodDelivery(BigDecimal dcOrigDctDifRpodDelivery) {
        this.dcOrigDctDifRpodDelivery = dcOrigDctDifRpodDelivery;
    }

    public BigDecimal getDcOrigDctDifRpodPackaging() {
        return dcOrigDctDifRpodPackaging;
    }

    public void setDcOrigDctDifRpodPackaging(BigDecimal dcOrigDctDifRpodPackaging) {
        this.dcOrigDctDifRpodPackaging = dcOrigDctDifRpodPackaging;
    }

    public BigDecimal getDcOrigDctDifRpodCod() {
        return dcOrigDctDifRpodCod;
    }

    public void setDcOrigDctDifRpodCod(BigDecimal dcOrigDctDifRpodCod) {
        this.dcOrigDctDifRpodCod = dcOrigDctDifRpodCod;
    }

    public BigDecimal getDcOrigDctDifRpodInsurance() {
        return dcOrigDctDifRpodInsurance;
    }

    public void setDcOrigDctDifRpodInsurance(BigDecimal dcOrigDctDifRpodInsurance) {
        this.dcOrigDctDifRpodInsurance = dcOrigDctDifRpodInsurance;
    }

    public BigDecimal getDcOrigDctDifRpodOther() {
        return dcOrigDctDifRpodOther;
    }

    public void setDcOrigDctDifRpodOther(BigDecimal dcOrigDctDifRpodOther) {
        this.dcOrigDctDifRpodOther = dcOrigDctDifRpodOther;
    }

    public BigDecimal getDcDestDctDifRpodTransport() {
        return dcDestDctDifRpodTransport;
    }

    public void setDcDestDctDifRpodTransport(BigDecimal dcDestDctDifRpodTransport) {
        this.dcDestDctDifRpodTransport = dcDestDctDifRpodTransport;
    }

    public BigDecimal getDcDestDctDifRpodPickup() {
        return dcDestDctDifRpodPickup;
    }

    public void setDcDestDctDifRpodPickup(BigDecimal dcDestDctDifRpodPickup) {
        this.dcDestDctDifRpodPickup = dcDestDctDifRpodPickup;
    }

    public BigDecimal getDcDestDctDifRpodDelivery() {
        return dcDestDctDifRpodDelivery;
    }

    public void setDcDestDctDifRpodDelivery(BigDecimal dcDestDctDifRpodDelivery) {
        this.dcDestDctDifRpodDelivery = dcDestDctDifRpodDelivery;
    }

    public BigDecimal getDcDestDctDifRpodPackaging() {
        return dcDestDctDifRpodPackaging;
    }

    public void setDcDestDctDifRpodPackaging(BigDecimal dcDestDctDifRpodPackaging) {
        this.dcDestDctDifRpodPackaging = dcDestDctDifRpodPackaging;
    }

    public BigDecimal getDcDestDctDifRpodCod() {
        return dcDestDctDifRpodCod;
    }

    public void setDcDestDctDifRpodCod(BigDecimal dcDestDctDifRpodCod) {
        this.dcDestDctDifRpodCod = dcDestDctDifRpodCod;
    }

    public BigDecimal getDcDestDctDifRpodInsurance() {
        return dcDestDctDifRpodInsurance;
    }

    public void setDcDestDctDifRpodInsurance(BigDecimal dcDestDctDifRpodInsurance) {
        this.dcDestDctDifRpodInsurance = dcDestDctDifRpodInsurance;
    }

    public BigDecimal getDcDestDctDifRpodOther() {
        return dcDestDctDifRpodOther;
    }

    public void setDcDestDctDifRpodOther(BigDecimal dcDestDctDifRpodOther) {
        this.dcDestDctDifRpodOther = dcDestDctDifRpodOther;
    }

    public BigDecimal getDcOrigActdctRpodTransport() {
        return dcOrigActdctRpodTransport;
    }

    public void setDcOrigActdctRpodTransport(BigDecimal dcOrigActdctRpodTransport) {
        this.dcOrigActdctRpodTransport = dcOrigActdctRpodTransport;
    }

    public BigDecimal getDcOrigActdctRpodPickup() {
        return dcOrigActdctRpodPickup;
    }

    public void setDcOrigActdctRpodPickup(BigDecimal dcOrigActdctRpodPickup) {
        this.dcOrigActdctRpodPickup = dcOrigActdctRpodPickup;
    }

    public BigDecimal getDcOrigActdctRpodDelivery() {
        return dcOrigActdctRpodDelivery;
    }

    public void setDcOrigActdctRpodDelivery(BigDecimal dcOrigActdctRpodDelivery) {
        this.dcOrigActdctRpodDelivery = dcOrigActdctRpodDelivery;
    }

    public BigDecimal getDcOrigActdctRpodPackaging() {
        return dcOrigActdctRpodPackaging;
    }

    public void setDcOrigActdctRpodPackaging(BigDecimal dcOrigActdctRpodPackaging) {
        this.dcOrigActdctRpodPackaging = dcOrigActdctRpodPackaging;
    }

    public BigDecimal getDcOrigActdctRpodCod() {
        return dcOrigActdctRpodCod;
    }

    public void setDcOrigActdctRpodCod(BigDecimal dcOrigActdctRpodCod) {
        this.dcOrigActdctRpodCod = dcOrigActdctRpodCod;
    }

    public BigDecimal getDcOrigActdctRpodInsurance() {
        return dcOrigActdctRpodInsurance;
    }

    public void setDcOrigActdctRpodInsurance(BigDecimal dcOrigActdctRpodInsurance) {
        this.dcOrigActdctRpodInsurance = dcOrigActdctRpodInsurance;
    }

    public BigDecimal getDcOrigActdctRpodOther() {
        return dcOrigActdctRpodOther;
    }

    public void setDcOrigActdctRpodOther(BigDecimal dcOrigActdctRpodOther) {
        this.dcOrigActdctRpodOther = dcOrigActdctRpodOther;
    }

    public BigDecimal getDcDestActdctRpodTransport() {
        return dcDestActdctRpodTransport;
    }

    public void setDcDestActdctRpodTransport(BigDecimal dcDestActdctRpodTransport) {
        this.dcDestActdctRpodTransport = dcDestActdctRpodTransport;
    }

    public BigDecimal getDcDestActdctRpodPickup() {
        return dcDestActdctRpodPickup;
    }

    public void setDcDestActdctRpodPickup(BigDecimal dcDestActdctRpodPickup) {
        this.dcDestActdctRpodPickup = dcDestActdctRpodPickup;
    }

    public BigDecimal getDcDestActdctRpodDelivery() {
        return dcDestActdctRpodDelivery;
    }

    public void setDcDestActdctRpodDelivery(BigDecimal dcDestActdctRpodDelivery) {
        this.dcDestActdctRpodDelivery = dcDestActdctRpodDelivery;
    }

    public BigDecimal getDcDestActdctRpodPackaging() {
        return dcDestActdctRpodPackaging;
    }

    public void setDcDestActdctRpodPackaging(BigDecimal dcDestActdctRpodPackaging) {
        this.dcDestActdctRpodPackaging = dcDestActdctRpodPackaging;
    }

    public BigDecimal getDcDestActdctRpodCod() {
        return dcDestActdctRpodCod;
    }

    public void setDcDestActdctRpodCod(BigDecimal dcDestActdctRpodCod) {
        this.dcDestActdctRpodCod = dcDestActdctRpodCod;
    }

    public BigDecimal getDcDestActdctRpodInsurance() {
        return dcDestActdctRpodInsurance;
    }

    public void setDcDestActdctRpodInsurance(BigDecimal dcDestActdctRpodInsurance) {
        this.dcDestActdctRpodInsurance = dcDestActdctRpodInsurance;
    }

    public BigDecimal getDcDestActdctRpodOther() {
        return dcDestActdctRpodOther;
    }

    public void setDcDestActdctRpodOther(BigDecimal dcDestActdctRpodOther) {
        this.dcDestActdctRpodOther = dcDestActdctRpodOther;
    }

    public BigDecimal getDcOrigActdctRnpodTransport() {
        return dcOrigActdctRnpodTransport;
    }

    public void setDcOrigActdctRnpodTransport(BigDecimal dcOrigActdctRnpodTransport) {
        this.dcOrigActdctRnpodTransport = dcOrigActdctRnpodTransport;
    }

    public BigDecimal getDcOrigActdctRnpodPickup() {
        return dcOrigActdctRnpodPickup;
    }

    public void setDcOrigActdctRnpodPickup(BigDecimal dcOrigActdctRnpodPickup) {
        this.dcOrigActdctRnpodPickup = dcOrigActdctRnpodPickup;
    }

    public BigDecimal getDcOrigActdctRnpodDelivery() {
        return dcOrigActdctRnpodDelivery;
    }

    public void setDcOrigActdctRnpodDelivery(BigDecimal dcOrigActdctRnpodDelivery) {
        this.dcOrigActdctRnpodDelivery = dcOrigActdctRnpodDelivery;
    }

    public BigDecimal getDcOrigActdctRnpodPackaging() {
        return dcOrigActdctRnpodPackaging;
    }

    public void setDcOrigActdctRnpodPackaging(BigDecimal dcOrigActdctRnpodPackaging) {
        this.dcOrigActdctRnpodPackaging = dcOrigActdctRnpodPackaging;
    }

    public BigDecimal getDcOrigActdctRnpodCod() {
        return dcOrigActdctRnpodCod;
    }

    public void setDcOrigActdctRnpodCod(BigDecimal dcOrigActdctRnpodCod) {
        this.dcOrigActdctRnpodCod = dcOrigActdctRnpodCod;
    }

    public BigDecimal getDcOrigActdctRnpodInsurance() {
        return dcOrigActdctRnpodInsurance;
    }

    public void setDcOrigActdctRnpodInsurance(BigDecimal dcOrigActdctRnpodInsurance) {
        this.dcOrigActdctRnpodInsurance = dcOrigActdctRnpodInsurance;
    }

    public BigDecimal getDcOrigActdctRnpodOther() {
        return dcOrigActdctRnpodOther;
    }

    public void setDcOrigActdctRnpodOther(BigDecimal dcOrigActdctRnpodOther) {
        this.dcOrigActdctRnpodOther = dcOrigActdctRnpodOther;
    }

    public BigDecimal getDcDestActdctRnpodTransport() {
        return dcDestActdctRnpodTransport;
    }

    public void setDcDestActdctRnpodTransport(BigDecimal dcDestActdctRnpodTransport) {
        this.dcDestActdctRnpodTransport = dcDestActdctRnpodTransport;
    }

    public BigDecimal getDcDestActdctRnpodPickup() {
        return dcDestActdctRnpodPickup;
    }

    public void setDcDestActdctRnpodPickup(BigDecimal dcDestActdctRnpodPickup) {
        this.dcDestActdctRnpodPickup = dcDestActdctRnpodPickup;
    }

    public BigDecimal getDcDestActdctRnpodDelivery() {
        return dcDestActdctRnpodDelivery;
    }

    public void setDcDestActdctRnpodDelivery(BigDecimal dcDestActdctRnpodDelivery) {
        this.dcDestActdctRnpodDelivery = dcDestActdctRnpodDelivery;
    }

    public BigDecimal getDcDestActdctRnpodPackaging() {
        return dcDestActdctRnpodPackaging;
    }

    public void setDcDestActdctRnpodPackaging(BigDecimal dcDestActdctRnpodPackaging) {
        this.dcDestActdctRnpodPackaging = dcDestActdctRnpodPackaging;
    }

    public BigDecimal getDcDestActdctRnpodCod() {
        return dcDestActdctRnpodCod;
    }

    public void setDcDestActdctRnpodCod(BigDecimal dcDestActdctRnpodCod) {
        this.dcDestActdctRnpodCod = dcDestActdctRnpodCod;
    }

    public BigDecimal getDcDestActdctRnpodInsurance() {
        return dcDestActdctRnpodInsurance;
    }

    public void setDcDestActdctRnpodInsurance(BigDecimal dcDestActdctRnpodInsurance) {
        this.dcDestActdctRnpodInsurance = dcDestActdctRnpodInsurance;
    }

    public BigDecimal getDcDestActdctRnpodOther() {
        return dcDestActdctRnpodOther;
    }

    public void setDcDestActdctRnpodOther(BigDecimal dcDestActdctRnpodOther) {
        this.dcDestActdctRnpodOther = dcDestActdctRnpodOther;
    }

    public BigDecimal getDcOrigIncomet() {
        return dcOrigIncomet;
    }

    public void setDcOrigIncomet(BigDecimal dcOrigIncomet) {
        this.dcOrigIncomet = dcOrigIncomet;
    }

    public BigDecimal getDcOrigCostt() {
        return dcOrigCostt;
    }

    public void setDcOrigCostt(BigDecimal dcOrigCostt) {
        this.dcOrigCostt = dcOrigCostt;
    }

    public BigDecimal getDcDestApplyT() {
        return dcDestApplyT;
    }

    public void setDcDestApplyT(BigDecimal dcDestApplyT) {
        this.dcDestApplyT = dcDestApplyT;
    }

    public BigDecimal getDcExOrigRcvtPod() {
        return dcExOrigRcvtPod;
    }

    public void setDcExOrigRcvtPod(BigDecimal dcExOrigRcvtPod) {
        this.dcExOrigRcvtPod = dcExOrigRcvtPod;
    }

    public BigDecimal getDcExDestRcvtPod() {
        return dcExDestRcvtPod;
    }

    public void setDcExDestRcvtPod(BigDecimal dcExDestRcvtPod) {
        this.dcExDestRcvtPod = dcExDestRcvtPod;
    }

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public BigDecimal getLdcExtOrNwo() {
		return ldcExtOrNwo;
	}

	public void setLdcExtOrNwo(BigDecimal ldcExtOrNwo) {
		this.ldcExtOrNwo = ldcExtOrNwo;
	}

	public BigDecimal getLdcExtOrWo() {
		return ldcExtOrWo;
	}

	public void setLdcExtOrWo(BigDecimal ldcExtOrWo) {
		this.ldcExtOrWo = ldcExtOrWo;
	}

	public BigDecimal getLdcOroNwoActPodTransport() {
		return ldcOroNwoActPodTransport;
	}

	public void setLdcOroNwoActPodTransport(BigDecimal ldcOroNwoActPodTransport) {
		this.ldcOroNwoActPodTransport = ldcOroNwoActPodTransport;
	}

	public BigDecimal getLdcOroNwoActPodPickup() {
		return ldcOroNwoActPodPickup;
	}

	public void setLdcOroNwoActPodPickup(BigDecimal ldcOroNwoActPodPickup) {
		this.ldcOroNwoActPodPickup = ldcOroNwoActPodPickup;
	}

	public BigDecimal getLdcOroNwoActPodDelivery() {
		return ldcOroNwoActPodDelivery;
	}

	public void setLdcOroNwoActPodDelivery(BigDecimal ldcOroNwoActPodDelivery) {
		this.ldcOroNwoActPodDelivery = ldcOroNwoActPodDelivery;
	}

	public BigDecimal getLdcOroNwoActPodPackaging() {
		return ldcOroNwoActPodPackaging;
	}

	public void setLdcOroNwoActPodPackaging(BigDecimal ldcOroNwoActPodPackaging) {
		this.ldcOroNwoActPodPackaging = ldcOroNwoActPodPackaging;
	}

	public BigDecimal getLdcOroNwoActPodInsurance() {
		return ldcOroNwoActPodInsurance;
	}

	public void setLdcOroNwoActPodInsurance(BigDecimal ldcOroNwoActPodInsurance) {
		this.ldcOroNwoActPodInsurance = ldcOroNwoActPodInsurance;
	}

	public BigDecimal getLdcOroNwoActPodCod() {
		return ldcOroNwoActPodCod;
	}

	public void setLdcOroNwoActPodCod(BigDecimal ldcOroNwoActPodCod) {
		this.ldcOroNwoActPodCod = ldcOroNwoActPodCod;
	}

	public BigDecimal getLdcOroNwoActPodOther() {
		return ldcOroNwoActPodOther;
	}

	public void setLdcOroNwoActPodOther(BigDecimal ldcOroNwoActPodOther) {
		this.ldcOroNwoActPodOther = ldcOroNwoActPodOther;
	}

	public BigDecimal getLdcOroWoActPodTransport() {
		return ldcOroWoActPodTransport;
	}

	public void setLdcOroWoActPodTransport(BigDecimal ldcOroWoActPodTransport) {
		this.ldcOroWoActPodTransport = ldcOroWoActPodTransport;
	}

	public BigDecimal getLdcOroWoActPodPickup() {
		return ldcOroWoActPodPickup;
	}

	public void setLdcOroWoActPodPickup(BigDecimal ldcOroWoActPodPickup) {
		this.ldcOroWoActPodPickup = ldcOroWoActPodPickup;
	}

	public BigDecimal getLdcOroWoActPodDelivery() {
		return ldcOroWoActPodDelivery;
	}

	public void setLdcOroWoActPodDelivery(BigDecimal ldcOroWoActPodDelivery) {
		this.ldcOroWoActPodDelivery = ldcOroWoActPodDelivery;
	}

	public BigDecimal getLdcOroWoActPodPackaging() {
		return ldcOroWoActPodPackaging;
	}

	public void setLdcOroWoActPodPackaging(BigDecimal ldcOroWoActPodPackaging) {
		this.ldcOroWoActPodPackaging = ldcOroWoActPodPackaging;
	}

	public BigDecimal getLdcOroWoActPodInsurance() {
		return ldcOroWoActPodInsurance;
	}

	public void setLdcOroWoActPodInsurance(BigDecimal ldcOroWoActPodInsurance) {
		this.ldcOroWoActPodInsurance = ldcOroWoActPodInsurance;
	}

	public BigDecimal getLdcOroWoActPodCod() {
		return ldcOroWoActPodCod;
	}

	public void setLdcOroWoActPodCod(BigDecimal ldcOroWoActPodCod) {
		this.ldcOroWoActPodCod = ldcOroWoActPodCod;
	}

	public BigDecimal getLdcOroWoActPodOther() {
		return ldcOroWoActPodOther;
	}

	public void setLdcOroWoActPodOther(BigDecimal ldcOroWoActPodOther) {
		this.ldcOroWoActPodOther = ldcOroWoActPodOther;
	}

	public BigDecimal getLdcOroNwoActRpodTransport() {
		return ldcOroNwoActRpodTransport;
	}

	public void setLdcOroNwoActRpodTransport(BigDecimal ldcOroNwoActRpodTransport) {
		this.ldcOroNwoActRpodTransport = ldcOroNwoActRpodTransport;
	}

	public BigDecimal getLdcOroNwoActRpodPickup() {
		return ldcOroNwoActRpodPickup;
	}

	public void setLdcOroNwoActRpodPickup(BigDecimal ldcOroNwoActRpodPickup) {
		this.ldcOroNwoActRpodPickup = ldcOroNwoActRpodPickup;
	}

	public BigDecimal getLdcOroNwoActRpodDelivery() {
		return ldcOroNwoActRpodDelivery;
	}

	public void setLdcOroNwoActRpodDelivery(BigDecimal ldcOroNwoActRpodDelivery) {
		this.ldcOroNwoActRpodDelivery = ldcOroNwoActRpodDelivery;
	}

	public BigDecimal getLdcOroNwoActRpodPackaging() {
		return ldcOroNwoActRpodPackaging;
	}

	public void setLdcOroNwoActRpodPackaging(BigDecimal ldcOroNwoActRpodPackaging) {
		this.ldcOroNwoActRpodPackaging = ldcOroNwoActRpodPackaging;
	}

	public BigDecimal getLdcOroNwoActRpodInsurance() {
		return ldcOroNwoActRpodInsurance;
	}

	public void setLdcOroNwoActRpodInsurance(BigDecimal ldcOroNwoActRpodInsurance) {
		this.ldcOroNwoActRpodInsurance = ldcOroNwoActRpodInsurance;
	}

	public BigDecimal getLdcOroNwoActRpodCod() {
		return ldcOroNwoActRpodCod;
	}

	public void setLdcOroNwoActRpodCod(BigDecimal ldcOroNwoActRpodCod) {
		this.ldcOroNwoActRpodCod = ldcOroNwoActRpodCod;
	}

	public BigDecimal getLdcOroNwoActRpodOther() {
		return ldcOroNwoActRpodOther;
	}

	public void setLdcOroNwoActRpodOther(BigDecimal ldcOroNwoActRpodOther) {
		this.ldcOroNwoActRpodOther = ldcOroNwoActRpodOther;
	}

	public BigDecimal getLdcOroWoActRpodTransport() {
		return ldcOroWoActRpodTransport;
	}

	public void setLdcOroWoActRpodTransport(BigDecimal ldcOroWoActRpodTransport) {
		this.ldcOroWoActRpodTransport = ldcOroWoActRpodTransport;
	}

	public BigDecimal getLdcOroWoActRpodPickup() {
		return ldcOroWoActRpodPickup;
	}

	public void setLdcOroWoActRpodPickup(BigDecimal ldcOroWoActRpodPickup) {
		this.ldcOroWoActRpodPickup = ldcOroWoActRpodPickup;
	}

	public BigDecimal getLdcOroWoActRpodDelivery() {
		return ldcOroWoActRpodDelivery;
	}

	public void setLdcOroWoActRpodDelivery(BigDecimal ldcOroWoActRpodDelivery) {
		this.ldcOroWoActRpodDelivery = ldcOroWoActRpodDelivery;
	}

	public BigDecimal getLdcOroWoActRpodPackaging() {
		return ldcOroWoActRpodPackaging;
	}

	public void setLdcOroWoActRpodPackaging(BigDecimal ldcOroWoActRpodPackaging) {
		this.ldcOroWoActRpodPackaging = ldcOroWoActRpodPackaging;
	}

	public BigDecimal getLdcOroWoActRpodInsurance() {
		return ldcOroWoActRpodInsurance;
	}

	public void setLdcOroWoActRpodInsurance(BigDecimal ldcOroWoActRpodInsurance) {
		this.ldcOroWoActRpodInsurance = ldcOroWoActRpodInsurance;
	}

	public BigDecimal getLdcOroWoActRpodCod() {
		return ldcOroWoActRpodCod;
	}

	public void setLdcOroWoActRpodCod(BigDecimal ldcOroWoActRpodCod) {
		this.ldcOroWoActRpodCod = ldcOroWoActRpodCod;
	}

	public BigDecimal getLdcOroWoActRpodOther() {
		return ldcOroWoActRpodOther;
	}

	public void setLdcOroWoActRpodOther(BigDecimal ldcOroWoActRpodOther) {
		this.ldcOroWoActRpodOther = ldcOroWoActRpodOther;
	}

	public BigDecimal getLdcOrigIncomeo() {
		return ldcOrigIncomeo;
	}

	public void setLdcOrigIncomeo(BigDecimal ldcOrigIncomeo) {
		this.ldcOrigIncomeo = ldcOrigIncomeo;
	}

	public BigDecimal getLdcOrigCosto() {
		return ldcOrigCosto;
	}

	public void setLdcOrigCosto(BigDecimal ldcOrigCosto) {
		this.ldcOrigCosto = ldcOrigCosto;
	}

	public BigDecimal getLdcDestIncomeo() {
		return ldcDestIncomeo;
	}

	public void setLdcDestIncomeo(BigDecimal ldcDestIncomeo) {
		this.ldcDestIncomeo = ldcDestIncomeo;
	}

	public BigDecimal getLdcExoOrPod() {
		return ldcExoOrPod;
	}

	public void setLdcExoOrPod(BigDecimal ldcExoOrPod) {
		this.ldcExoOrPod = ldcExoOrPod;
	}

	public BigDecimal getLdcExoOrNwo() {
		return ldcExoOrNwo;
	}

	public void setLdcExoOrNwo(BigDecimal ldcExoOrNwo) {
		this.ldcExoOrNwo = ldcExoOrNwo;
	}

	public BigDecimal getLdcExoOrWo() {
		return ldcExoOrWo;
	}

	public void setLdcExoOrWo(BigDecimal ldcExoOrWo) {
		this.ldcExoOrWo = ldcExoOrWo;
	}
}
