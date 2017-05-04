package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;

/**
 * 零担服务范围Vo
 * 
 * @ClassName: LtlServiceScopeVo
 * @author 200664-yangjinheng
 * @date 2014年9月30日 下午12:25:35
 */
public class LtlServiceScopeVo {

	// 行政区域详情
	private LtlServiceScopeDto districtDto;

	// 行政区域包含的营业网点和派送区域
	private List<LtlServiceScopeDto> serviceScopeList;

	public List<LtlServiceScopeDto> getServiceScopeList() {
		return serviceScopeList;
	}

	public void setServiceScopeList(List<LtlServiceScopeDto> serviceScopeList) {
		this.serviceScopeList = serviceScopeList;
	}

	public LtlServiceScopeDto getDistrictDto() {
		return districtDto;
	}

	public void setDistrictDto(LtlServiceScopeDto districtDto) {
		this.districtDto = districtDto;
	}

}
