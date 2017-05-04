package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonGeneralTaxpayerInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
/**
 * 
 * 一般纳税人信息Dao实现 公共选择器
 * @author 308861 
 * @date 2016-2-28 下午2:36:20
 * @since
 * @version
 */
public class CommonGeneralTaxpayerInfoDao extends SqlSessionDaoSupport implements ICommonGeneralTaxpayerInfoDao{
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonGeneralTaxpayer.";
	
	/**
	 * 
	 * 查询一般纳税人信息 
	 * @author 308861 
	 * @date 2016-2-29 上午9:42:59
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonGeneralTaxpayerInfoDao#queryTaxpayerInfoList(com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GeneralTaxpayerInfoEntity> queryTaxpayerInfoList(
			GeneralTaxpayerInfoEntity entity, int start, int limit) {
		RowBounds bounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryTaxpayerInfo", entity,bounds);
	}

	/**
	 * 
	 * 统计一般纳税人信息 
	 * @author 308861 
	 * @date 2016-2-29 上午9:46:57
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonGeneralTaxpayerInfoDao#queryGeneralTaxpayerInfoCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity)
	 */
	@Override
	public long queryGeneralTaxpayerInfoCount(GeneralTaxpayerInfoEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryTaxpayerInfoCount",entity);
	}

}
