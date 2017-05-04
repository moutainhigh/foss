package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IDopBillPayableReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.DopBillEntity;

/**
 * 家装应收单应付单Dao
 * 
 * @ClassName: DopPayableReceiveEntityDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-30 上午10:49:18
 * 
 */
public class DopBillPayableReceivableEntityDao extends iBatis3DaoImpl implements IDopBillPayableReceivableEntityDao {
	private static String NAMESPACE = "foss.stl.DopBillPayableReceivableEntityDao.";

	/**
	 * 插入应付单
	 */
	@Override
	public int insertDopPayableEntity(DopBillEntity dopBillEntity) {
		int result = this.getSqlSession().insert(NAMESPACE + "insertDopPayable", dopBillEntity);
		return result;
	}

	/**
	 * 插入应收单
	 */
	@Override
	public int insertDopReceiveEntity(DopBillEntity dopBillEntity) {
		int result = this.getSqlSession().insert(NAMESPACE + "insertDopReceive", dopBillEntity);
		return result;
	}

	/**
	 * 查询应收应付
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DopBillEntity> queryDopPayAndRec(List<DopBillEntity> list) {
		List<DopBillEntity> lists = this.getSqlSession().selectList(NAMESPACE+"queryDopPayRec",list);
		return lists;
	}
	
	/**
	 * 更新应付单
	 */
	@Override
	public int updateDopPayable(List<DopBillEntity> list) {
		return this.getSqlSession().update(NAMESPACE+"updateDopPayable",list);
	}

	/**
	 * 更新应收单
	 */
	@Override
	public int updateDopReceive(List<DopBillEntity> list) {
		return this.getSqlSession().update(NAMESPACE+"updateDopReceive",list);
	}
	
	/**
	 * 插入应付红单
	 */
	@Override
	public int insetDopRedPayable(List<DopBillEntity> list) {
		int result = this.getSqlSession().insert(NAMESPACE+"insetDopRedPayable",list);
		return result;
	}
	
	/**
	 * 插入应收红单
	 */
	@Override
	public int insetDopRedReceive(List<DopBillEntity> list) {
		return this.getSqlSession().insert(NAMESPACE+"insetDopRedReceive", list);
	}	

	/**
	 * 根据部门编码获取所属子公司名称和编码	
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DopBillEntity> getSubCompanyNameAndCode(List<DopBillEntity> list) {
		return this.getSqlSession().selectList(NAMESPACE+"getSubCompanyNameAndCode",list);
	}

	/**
	 * 判断是否生成应收单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DopBillEntity> judgeReceive(DopBillEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"judgeReceive", entity);
	}
	
	/**
	 * 判断是否生成应付单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DopBillEntity> judgePayable(DopBillEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"judgePayable", entity);
	}
}
