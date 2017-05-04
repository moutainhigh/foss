package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonBankAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;
/**
 * 公共选择器--账户查询(包括对公，对私)dao实现
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-08-22 下午2:35:52
 */
public class CommonBankAccountDao extends SqlSessionDaoSupport implements ICommonBankAccountDao {
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonBankAccount.";
	/**
	 * 查询组织对公账号信息
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
	@Override
	public List<CommonBankAccountEntity> queryBankAccountByDto(
			CommonBankAccountEntity commonBankAccountEntity, int start,
			int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPublicBankAccount",commonBankAccountEntity, bounds);
	}
	/**
	 * 查询组织对公账号信息 总数
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
	@Override
	public Long queryRecordCount(CommonBankAccountEntity commonBankAccountEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countQueryPublicBankAccount",commonBankAccountEntity);
		}

}
