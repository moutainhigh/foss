package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISecurityTfrMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现ISecurityTfrMotorcade接口)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午11:06:05
 * @since
 * @version
 */
public class SecurityTfrMotorcadeDao extends SqlSessionDaoSupport implements
		ISecurityTfrMotorcadeDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.securityTfrMotorcade.";

	/** 
	 * <p>TODO(根据保安组编码查询信息)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午11:06:05
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#querySecurityTfrMotorcadeListBySecurityCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityTfrMotorcadeEntity> querySecurityTfrMotorcadeListBySecurityCode(
			SecurityTfrMotorcadeEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(limit, start);
		return this.getSqlSession().selectList(NAMESPACE + "select",entity, rowBounds);
	}

	/** 
	 * <p>TODO(统计所有保安组信息分页查询)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午11:06:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#querySecurityCodeCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public Long querySecurityCodeCount(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",entity);
	}

	/** 
	 * <p>TODO(统计同一保安组信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午11:06:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public Long queryRecordCount(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "querySecurityCodeCount",entity);
	}

	/** 
	 * <p>TODO(增加保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:00:15
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISecurityTfrMotorcadeDao#addSecurityTfrMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public int addSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity) {
		Date now = new Date();
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setActive(FossConstants.ACTIVE);
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}

	/** 
	 * <p>TODO(删除保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:00:17
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISecurityTfrMotorcadeDao#deleteSecurityTfrMotorcade(java.lang.String[])
	 */
	@Override
	public int deleteSecurityTfrMotorcade(String[] codeList) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		return this.getSqlSession().delete(NAMESPACE+"deleteByCode", map);
	}

	/** 
	 * <p>TODO(更改保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:00:17
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISecurityTfrMotorcadeDao#updateSecurityTfrMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public int updateSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(NAMESPACE+"update", entity);
	}

}
