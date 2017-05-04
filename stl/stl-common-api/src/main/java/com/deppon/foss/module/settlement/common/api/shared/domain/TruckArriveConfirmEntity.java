package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * @description:车辆到达确认实体
 * @author 073615
 *
 */
public class TruckArriveConfirmEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
     * 整车、外请车首款ID
     */
    private String payablefirstId;
    /**
     * 整车、外请车尾款ID
     */
    private String payablelastId;
    /**
     * 外发单号
     */
    private String handleNo;
    /**
     * 确认到达/反确认到达时间
     */
    private Date convertDate;
    /**
     * 确认类型 TAC 到达确认  UAC 反到达确认
     */
    private String conertType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建部门
     */
    private String createDept;


    public String getPayablefirstId() {
        return payablefirstId;
    }

    public void setPayablefirstId(String payablefirstId) {
        this.payablefirstId = payablefirstId;
    }

    public String getPayablelastId() {
        return payablelastId;
    }

    public void setPayablelastId(String payablelastId) {
        this.payablelastId = payablelastId;
    }

    public String getHandleNo() {
        return handleNo;
    }

    public void setHandleNo(String handleNo) {
        this.handleNo = handleNo;
    }

    public Date getConvertDate() {
        return convertDate;
    }

    public void setConvertDate(Date convertDate) {
        this.convertDate = convertDate;
    }

    public String getConertType() {
        return conertType;
    }

    public void setConertType(String conertType) {
        this.conertType = conertType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }
}