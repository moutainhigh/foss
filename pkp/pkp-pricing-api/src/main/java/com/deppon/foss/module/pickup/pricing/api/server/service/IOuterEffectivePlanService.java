package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.OuterEffectiveVo;


public interface IOuterEffectivePlanService extends IService{

	Long queryOuterEffectivePlanVoBatchCount(OuterEffectivePlanConditionDto outerEffectivePlanConditionDto);

	List<OuterEffectivePlanDto> queryOuterPriceVoBatchInfo(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto,
			int start, int limit);

	void updateActiveToN(String outerEffectivePlanId);

	void immediatelyActiveOuterEffectivePlan(String outerEffectivePlanId,
			String yesOrNo, Date effectiveTime);

	void immediatelyStopOuterEffectivePlan(String outerEffectivePlanId,
			Date effectiveTime);

	void updateOuterEffectivePlanActiveById(
			List<OuterEffectivePlanDto> outerEffectivePlanDtoList,
			String yesOrNo);

	OuterEffectiveVo addOuterEffectivePlan(OuterEffectivePlanEntity outerEffectivePlanEntity);

	void updateOuterEffectivePlan(OuterEffectivePlanEntity record);

	OuterEffectiveVo copyOuterEffectivePlan(String outerEffectivePlanId, String copyName);

	void deleteByPrimaryKey(List<String> outerEffectivePlanIds);

	OuterEffectiveVo selectByPrimaryKey(String outerEffectivePlanId);

	ExportResource exportOuterEffectivePlan(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto);

	OuterEffectiveVo selectById(String outerEffectivePlanId);

	void importOuterEffectivePlan(Workbook book);

	OuterEffectivePlanEntity queryOuterEffectPlanByFieldAndBranch(String outFieldCode, String outerBranchCode, String productCode, Date billDate);

}
