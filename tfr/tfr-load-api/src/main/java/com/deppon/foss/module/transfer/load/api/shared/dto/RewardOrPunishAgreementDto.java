package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;

public class RewardOrPunishAgreementDto implements Serializable{
	private static final long serialVersionUID = -8891908806650095370L;
	/**是否奖罚线路（时效线路）*/
	private boolean beAgingLine;
	/**最高奖励上限*/
	private BigDecimal rewardMaxMoney;
	/**最高罚款上限*/
	private BigDecimal fineMaxMoney;
	
	/**奖罚详细信息List*/
	private List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity;

	public BigDecimal getRewardMaxMoney() {
		return rewardMaxMoney;
	}

	public void setRewardMaxMoney(BigDecimal rewardMaxMoney) {
		this.rewardMaxMoney = rewardMaxMoney;
	}

	public BigDecimal getFineMaxMoney() {
		return fineMaxMoney;
	}

	public void setFineMaxMoney(BigDecimal fineMaxMoney) {
		this.fineMaxMoney = fineMaxMoney;
	}

	public List<RewardOrPunishAgreementEntity> getRewardOrPunishAgreementEntity() {
		return rewardOrPunishAgreementEntity;
	}

	public void setRewardOrPunishAgreementEntity(
			List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity) {
		this.rewardOrPunishAgreementEntity = rewardOrPunishAgreementEntity;
	}

	public boolean getBeAgingLine() {
		return beAgingLine;
	}

	public void setBeAgingLine(boolean beAgingLine) {
		this.beAgingLine = beAgingLine;
	}

}
