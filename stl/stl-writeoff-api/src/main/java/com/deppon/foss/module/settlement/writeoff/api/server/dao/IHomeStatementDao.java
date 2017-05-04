package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装对账单service
 * 
 * @ClassName: IHomeStatementService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午7:24:37
 * 
 */
public interface IHomeStatementDao {
	/**
	 * 家装按时间查询应付单应收单
	 * 
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<HomeStatementDEntity> queryHomeStatementByDate(
			HomeStatementDto dto, int start, int limit);

	/**
	 * 家装按时间查询应付单应收单获取总行数
	 * 
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int getCountByDate(HomeStatementDto dto);

	/**
	 * 家装按单号去查询应付单应收单
	 * 
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<HomeStatementDEntity> queryHomeStatementByNumbers(
			HomeStatementDto dto, int start, int limit);

	/**
	 * 按客户去保存对账单明细
	 * 
	 * @Title: homeStatementDSaveByCustomer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param @param dto
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int homeStatementDSaveByCustomer(HomeStatementDto dto);

	/**
	 * 按单号去保存对账单明细
	 * 
	 * @Title: homeStatementDSaveByCustomer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param @param dto
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int homeStatementDSaveByNumber(HomeStatementDto dto);

	/**
	 * 按对账单号去获取对账单明细
	 * @Title: queryWoodenDByStatementBillNo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param homeStatementDto
	 * @param @return
	 * @return List<HomeStatementDEntity> 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<HomeStatementDEntity> queryHomeDByStatementBillNo(
			HomeStatementDto homeStatementDto);

	/**
	 * 生成对账单
	 * @Title: homeStatementSaveByStatementBillNo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param homeStatementDto
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 * 
	 */
	public int homeStatementSaveByStatementBillNo(
			HomeStatementDto homeStatementDto);
	
	/**
	 * 更新应付单
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int homePayUpdateByStatementBillNo(HomeStatementDto homeStatementDto);
	
	/**
	 * 更新应收单
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int homeRecUpdateByStatementBillNo(HomeStatementDto homeStatementDto);
	
}
