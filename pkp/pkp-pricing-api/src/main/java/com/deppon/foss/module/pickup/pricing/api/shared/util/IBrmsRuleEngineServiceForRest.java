package com.deppon.foss.module.pickup.pricing.api.shared.util;





public interface IBrmsRuleEngineServiceForRest {
	
	public String compute(
			 String ruleTypeCode,
			 String date,
			String json);
}
