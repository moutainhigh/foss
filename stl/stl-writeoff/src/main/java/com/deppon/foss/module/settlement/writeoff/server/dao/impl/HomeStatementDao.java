package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IHomeStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装查询应收单应付单
 * 
 * @ClassName: HomeStatementDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午7:36:03
 * 
 */
public class HomeStatementDao extends iBatis3DaoImpl implements IHomeStatementDao {
	private static String NAMESPACES = "foss.stl.HomeStatementDao.";
	/**
	 * 家装按时间查询应收单应付单
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementDEntity> queryHomeStatementByDate(HomeStatementDto dto,
			int start, int limit) {
		//分页查询 
		RowBounds rb = new RowBounds(start, limit);
		//直接返回
		return this.getSqlSession().selectList(NAMESPACES+"queryHomeStatementByCust",dto,rb);
	}
	
	/**
	 * 家装按单号查询应付单应收单
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementDEntity> queryHomeStatementByNumbers(HomeStatementDto dto,
			int start, int limit) {
		RowBounds rb = new RowBounds(start,limit);
		List<HomeStatementDEntity> lists =  this.getSqlSession().selectList(NAMESPACES+"queryHomeStatementByNumbers",dto,rb);
		return lists;
	}
	
	/**
	 * 家装按时间查询应收单应付单(获取总行数)
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@Override
	public int getCountByDate(HomeStatementDto dto) {
		int result = (Integer) this.getSqlSession().selectOne(NAMESPACES+"getCountByCust",dto);
		return result;
	}
	
	/**
	 * 按时间去保存对账单明细
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@Override
	public int homeStatementDSaveByCustomer(HomeStatementDto dto) {
		int result = this.getSqlSession().insert(NAMESPACES+"homeStatementDSaveByCustomer",dto);
		return result;
	}
	
	/**
	 * 按应付单号或应收单号去保存对账单明细
	 */
	@Override
	public int homeStatementDSaveByNumber(HomeStatementDto dto) {
		int result = this.getSqlSession().insert(NAMESPACES+"homeStatementDSaveByNumbers",dto);
		return result;
	}
	
	/**
	 * 获取所有的对账单明细(更具对账单号去查询对账单表明细表)
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementDEntity> queryHomeDByStatementBillNo(
			HomeStatementDto homeStatementDto) {
		return this.getSqlSession().selectList(NAMESPACES+"queryHomeDByStatementBillNo", homeStatementDto);	
	}
	
	/**
	 * 生成对账单
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@Override
	public int homeStatementSaveByStatementBillNo(
			HomeStatementDto homeStatementDto) {
		int result = this.getSqlSession().insert(NAMESPACES+"homeStatementSaveByStatementBillNo",homeStatementDto);
		return result;
	}
	
	/**
	 * 更新应付单
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@Override
	public int homePayUpdateByStatementBillNo(HomeStatementDto homeStatementDto) {
		int result = this.getSqlSession().insert(NAMESPACES+"homePayUpdateByStatementBillNo",homeStatementDto);
		return result;
	}
	
	/**
	 * 更新应收单
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:36:03
	 */
	@Override
	public int homeRecUpdateByStatementBillNo(HomeStatementDto homeStatementDto) {
		int result = this.getSqlSession().insert(NAMESPACES+"homeRecUpdateByStatementBillNo",homeStatementDto);
		return result;
	}

}
