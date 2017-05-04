package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditOrgEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;

/**
 * 
 * 部门收支平衡表 DAO
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午3:56:08
 */
public class CreditOrgEntityDao extends iBatis3DaoImpl implements ICreditOrgEntityDao {

	private static final String NAMESPACE = "foss.stl.CreditOrgEntityDao.";
	
	

	/**
	 * 
	 * 按组织进行查询平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 下午2:58:40
	 */
	@Override
	public CreditOrgEntity queryByOrgCode(String orgCode) {
		return (CreditOrgEntity) this.getSqlSession().selectOne(NAMESPACE + "queryByOrgCode", orgCode);
	}
	
	
	/**
	 * 更新基础
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午11:12:57
	 */
	@Override
	public int updateCreditOrg(CreditOrgEntity item) {
		return this.getSqlSession().update(NAMESPACE + "updateCreditOrg",item);
	}
	

	/**
	 * 
	 * 根据客户编码查询组织架构信息
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午5:43:25
	 */
	@Override
	public CreditOrgDto queryDebitByOrgCode(String orgCode) {
		return (CreditOrgDto) this.getSqlSession().selectOne(NAMESPACE + "queryDebitByOrgCode", orgCode);
	}

	/**
	 * 
	 * 按组织编码进行更新已用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:44:45
	 */
	@Override
	public int updateUsedAmount(CreditOrgEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateUsedAmount",entity);
	}

	/**
	 * 新加
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午5:37:04
	 */
	@Override
	public int addCreditOrg(CreditOrgEntity item) {
		return this.getSqlSession().update(NAMESPACE + "addCreditOrg", item);
	}

	/**
	 * 更新超期欠款的标记
	 * 
	 * @author 000123-foss-huangxiaobo
	 */
	@Override
	public int updateOverdueState(CreditOrgEntity entity) {
		// 判断对象是否为空
		return this.getSqlSession().update(NAMESPACE + "updateOverdueState", entity);
	}

	/**
	 * 获取总的组织条数
	 * 
	 * @author 000123-foss-huangxiaobo
	 */
	@Override
	public int queryTotalRows() {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryTotalRows");
	}

	/**
	 * 
	 * 分页查询组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditOrgDto> queryOrgCodeByPage(int offset, int limit) {RowBounds rowBounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrgByPage",null, rowBounds);
	}

    @Override
    public List<CreditOrgDto> queryOrgDebitInfo(String orgCode) {
        return (List<CreditOrgDto>)this.getSqlSession().selectList(NAMESPACE + "queryOrgDebitInfo",orgCode);
    }

}
