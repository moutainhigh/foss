package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODMergeEntity;

/**
 * 代收货款合并DAO
 * 
 * @author 163576
 * @date 2014-10-11 10:33:56
 *
 */
public interface ICODMergeEntityDao {

	int insert(CODMergeEntity entity);

	int updateMergeCODInvalidByBatch(List<String> mergeCodeList);
    
	/**
	 * 获取生成合并编号
	 * @param rule
	 * @return
	 */
	String createMergeCode(SettlementNoRuleEnum rule);
}