package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装对账单Dao
 * 
 * @ClassName: IHomeStatementService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午7:24:37
 * 
 */
public interface IHomeStatementService {
	/**
	 * 家装去查询应付单应收单
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public HomeStatementDto queryHomeStatementD(HomeStatementDto dto,
			int start, int limit);
	
	/**
	 * 生成家装对账单
	 */
	public HomeStatementDto homeStatementSave(HomeStatementDto dto);
}
