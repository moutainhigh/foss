package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装VO
 * @ClassName: HomeStatementVo
 * @Description: (这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午3:37:28
 * 
 */
public class HomeStatementVo {
	/**
	 * 对账单DTO
	 */
	private HomeStatementDto homeStatementDto;

	public HomeStatementDto getHomeStatementDto() {
		return homeStatementDto;
	}

	public void setHomeStatementDto(HomeStatementDto homeStatementDto) {
		this.homeStatementDto = homeStatementDto;
	}

	@Override
	public String toString() {
		return "HomeStatementVo [homeStatementDto=" + homeStatementDto + "]";
	}
	
}
