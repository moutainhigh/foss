package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(CodRefund的Dao层实现)
 * @author 187862-dujunhui
 * @date 2014-7-16 上午9:11:09
 * @since
 * @version v1.0
 */
public class CodRefundDao extends SqlSessionDaoSupport implements ICodRefundDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.codRefund.";

	/** 
	 * <p>TODO(新增代收货款打包退款基础信息实体)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:09
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#addCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public int addCodRefund(CodRefundEntity entity) {
		// TODO Auto-generated method stub
		entity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "addCodRefund", entity);
	}

	/** 
	 * <p>TODO(删除单条代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:23:16
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#deleteCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public int deleteCodRefund(CodRefundEntity entity) {
		// TODO Auto-generated method stub
		Date date = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(date);
		entity.setVersion(date.getTime());
		return this.getSqlSession().delete(NAMESPACE + "deleteCodRefund", entity);
	}

	/** 
	 * <p>TODO(批量删除代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:23:17
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#deleteCodRefunds(java.lang.String[])
	 */
	@Override
	public int deleteCodRefunds(String[] customerCodes,String modifyUser) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = new Date();
		map.put("active", FossConstants.ACTIVE);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("modifyDate", date);
		map.put("modifyUser", modifyUser);
		map.put("version", date.getTime());
		map.put("customerCodes", customerCodes);
		return this.getSqlSession().delete(NAMESPACE + "deleteCodRefunds", map);
	}
	
	/** 
	 * <p>TODO(更新单条代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:11
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#updateCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public int updateCodRefund(CodRefundEntity entity) {
		// TODO Auto-generated method stub
		//deleteCodRefund(entity);
		
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(entity.getModifyDate());
		entity.setCreateUser(entity.getModifyUser());
		entity.setModifyDate(new Date());          //NumberConstants.ENDTIME最大时间
		entity.setVersion(entity.getModifyDate().getTime());
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE + "addCodRefund", entity);
	}

	/** 
	 * <p>TODO(根据ID查询单条代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:11
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#queryCodRefundById(java.lang.String)
	 */
	@Override
	public CodRefundEntity queryCodRefundById(String id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("active", FossConstants.ACTIVE);
		map.put("id", id);
		return (CodRefundEntity)this.getSqlSession().selectOne(NAMESPACE + "queryCodRefundById", map);
	}

	/** 
	 * <p>TODO(根据条件查询代收货款打包退款基础信息List)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:11
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#queryCodRefundByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodRefundEntity> queryCodRefundByCondition(
			CodRefundEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		if(entity==null){
			entity=new CodRefundEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds=new RowBounds(start, limit);
		return (List<CodRefundEntity>)this.getSqlSession().selectList(
				NAMESPACE + "queryCodRefundListByCondition", entity, rowBounds);
	}

	/** 
	 * <p>TODO(根据条件查询代收货款打包退款基础信息条数)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:11
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#queryCodRefundCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public Long queryCodRefundCountByCondition(CodRefundEntity entity) {
		// TODO Auto-generated method stub
		entity.setActive(FossConstants.ACTIVE);
		return (Long)getSqlSession().selectOne(NAMESPACE + "countCodRefundListByCondition", entity);
	}

	/** 
	 * <p>TODO(导出代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 上午9:11:11
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao#queryCodRefundListForExport(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodRefundEntity> queryCodRefundListForExport(
			CodRefundEntity entity) {
		// TODO Auto-generated method stub
		return (List<CodRefundEntity>) getSqlSession().selectList(NAMESPACE + "queryCodRefundListForDownload", entity);
	}

}
