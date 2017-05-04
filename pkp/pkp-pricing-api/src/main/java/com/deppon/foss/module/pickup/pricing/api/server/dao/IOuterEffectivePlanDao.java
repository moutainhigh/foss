package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanDto;


public interface IOuterEffectivePlanDao {

	Long queryOuterEffectivePlanVoBatchCount(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto);

	List<OuterEffectivePlanDto> queryOuterPriceVoBatchInfo(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto,
			int start, int limit);

	int updateActiveToYesOrNo(String outerEffectivePlanId, String no);

	int updateOuterEffectivePlanEndTime(OuterEffectivePlanEntity record);

	int updateOuterEffectivePlanActiveById(List<String> ids, String yesOrNo);

	OuterEffectivePlanEntity selectByPrimaryKey(String outerEffectivePlanId);

	int updateByPrimaryKeySelective(OuterEffectivePlanEntity opt);

	List<OuterEffectivePlanEntity> queryOuterEffectivePlanByName(String name);

	int insertSelective(OuterEffectivePlanEntity outerEffectivePlanEntity);

	int checkIsExistName(OuterEffectivePlanEntity record);

	int copyOuterEffectivePlan(OuterEffectivePlanEntity record);

	OuterEffectivePlanDto selectById(String outerEffectivePlanId);

	int deleteByPrimaryKey(List<String> outerEffectivePlanIds);

	OuterEffectivePlanEntity queryOuterEffectPlanByFieldAndBranch(String outFieldCode, String outerBranchCode, String productCode, Date billDate);
	
}