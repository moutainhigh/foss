package com.deppon.pda.bdm.module.foss.accept.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctPdaDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AllTimeNode;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdBusinessAreasEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdInsuredEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdOneManyLimitEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdPartSalesDeptEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.kdPartSalesAreaEntitys;

public class AcctPdaDao extends SqlSessionDaoSupport implements IAcctPdaDao{

	/**
     * 
     * @description 获取快递点部营业部信息（即快递收入部门信息）
     * @param unBilling 
     * @created 2013-1-8 下午4:04:46
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<KdPartSalesDeptEntity> queryKdPartSalesDeptEntitys(String userCode) {
        
    	return getSqlSession().selectList(getClass().getName() + ".queryKdPartSalesDeptEntitys", userCode);
    }

	@Override
	public String queryDeptEntityIsOneMany(String userCode) {
		return (String) getSqlSession().selectOne(getClass().getName() + ".queryDeptEntitysIsOneMany", userCode);
	}

	@Override
	public String queryExpLimitWeight() {
		String d =  (String) getSqlSession().selectOne(getClass().getName() + ".queryExpLimitWeight");	
		
		return d;
	}
	@Override
	public void saveTimeNode(AllTimeNode allTimeNode) {
		getSqlSession().insert(getClass().getName() + ".saveTimeNode",allTimeNode);
	}

	/**
	 * 
	 * <p>TODO( 快递外发保价申明价值 获取 //author:245960 Date:2015-08-22 comment:骆敏霞需求获取  快递保价申明价值上限  1104   快递外发保价申明价值 1105)</p> 
	 * @author 245960
	 * @date 2015-8-22 下午4:58:40
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctPdaDao#queryKdInsuredEntity()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KdInsuredEntity> queryKdInsuredEntity() {
		
		return getSqlSession().selectList(getClass().getName() + ".queryKdInsuredEntity");
	}
	/**
	 * 一票多件重量、体积、件数限制
	 * @author 268974
	 * @date 2015-11-30 下午
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KdOneManyLimitEntity> queryOneManyLimit(){
		
		return getSqlSession().selectList(getClass().getName() + ".queryOneManyLimit");
	}
	/**
	 * 快递员当前城市的所有营业区
	 * @author 268974
	 * @date 2015-12-17 下午
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<kdPartSalesAreaEntitys> queryCourierAllSalesArea(String empCode) {
		return getSqlSession().selectList(getClass().getName() + ".queryCourierAllSalesArea", empCode);
	}

	/**
	 * 营业区对应的所有营业部
	 * @author 268974
	 * @date 2015-12-17 下午
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KdPartSalesDeptEntity> queryAllSalesDept(String areaCode) {
		return getSqlSession().selectList(getClass().getName() + ".queryAllSalesDept", areaCode);
	}

	/**
	 * 快递员默认营业区
	 * @author 268974
	 * @date 2015-12-18 下午
	 */
	@Override
	public KdBusinessAreasEntity queryDefaultAreaCode(String empCode) {
		return (KdBusinessAreasEntity) getSqlSession().selectOne(getClass().getName() + ".queryDefaultAreaCode", empCode);
	}

	@Override
	public void saveNCIPaymentCard(AccountStatementEntitys accoutAccountStatement) {
		getSqlSession().insert(getClass().getName() + ".saveNCIPaymentCard", accoutAccountStatement);
	}
	
}
