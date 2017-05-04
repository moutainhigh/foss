package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 073615 on 2015/11/18.
 */
public class CodAuditVo implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 查询结果
     */
    private List<CodAuditEntity> codAuditList;
    /**
     * 合计条数
     */
    private int totalCount;
    /**
     * 锁定条数
     */
    private int lockCount;
    /**
     * 未锁定条数
     */
    private int unlockCount;
    
    /**
     * 短期冻结
     */
    private int shortFreeze;
    /**
     * 长期冻结
     */
    private int longFreeze;
	/**
     * 查询条件
     */
    private CodAuditDto queryDto;


    /**
     * 根据单号查询
     */
    private List<String> queryWaybillNos;
    /**
     * 单据状态
     */
    private String auditStatus;

    /**
     *查询页签
     */
    private int activeTab;

    /**
     *是否全选
     * @return
     */

    private String isSelectAll;

    public String getIsSelectAll() {
        return isSelectAll;
    }

    public void setIsSelectAll(String isSelectAll) {
        this.isSelectAll = isSelectAll;
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public List<String> getQueryWaybillNos() {
        return queryWaybillNos;
    }

    public void setQueryWaybillNos(List<String> queryWaybillNos) {
        this.queryWaybillNos = queryWaybillNos;
    }

    public List<CodAuditEntity> getCodAuditList() {
        return codAuditList;
    }

    public void setCodAuditList(List<CodAuditEntity> codAuditList) {
        this.codAuditList = codAuditList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lockCount) {
        this.lockCount = lockCount;
    }

    public int getUnlockCount() {
        return unlockCount;
    }

    public void setUnlockCount(int unlockCount) {
        this.unlockCount = unlockCount;
    }

    public CodAuditDto getQueryDto() {
        return queryDto;
    }

    public void setQueryDto(CodAuditDto queryDto) {
        this.queryDto = queryDto;
    }
    public int getShortFreeze() {
		return shortFreeze;
	}

	public void setShortFreeze(int shortFreeze) {
		this.shortFreeze = shortFreeze;
	}

	public int getLongFreeze() {
		return longFreeze;
	}

	public void setLongFreeze(int longFreeze) {
		this.longFreeze = longFreeze;
	}
}
