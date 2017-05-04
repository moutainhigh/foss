package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundAdditionalDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *  (CodRefund的Dao层实现)
 * @author 187862-dujunhui
 * @date 2014-7-16 上午9:11:09
 * @since
 * @version v1.0
 */
public class CodRefundAdditionalDao extends SqlSessionDaoSupport implements ICodRefundAdditionalDao {
	
	private static final String NAMESPACEADDITIONAL = "foss.bse.bse-baseinfo.codRefundAdditional.";

	/** 
	 * <p> (新增代收货款打包退款基础信息实体)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:20
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#addCodRefundAdditional(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity)
	 */
	@Override
	public int addCodRefundAdditional(CodRefundAdditionalEntity entity) {
		//   Auto-generated method stub
		entity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACEADDITIONAL + "addCodRefundAdditional", entity);
	}

	/** 
	 * <p> (删除单条代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:20
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#deleteCodRefundAdditional(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity)
	 */
	@Override
	public int deleteCodRefundAdditional(CodRefundAdditionalEntity entity) {
		//   Auto-generated method stub
		Date date = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(date);
		entity.setVersion(date.getTime());
		return this.getSqlSession().delete(NAMESPACEADDITIONAL + "deleteCodRefundAdditional", entity);
	}

	/** 
	 * <p> (批量删除代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:22
	 * @param ids
	 * @param modifyUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#deleteCodRefundAdditionals(java.lang.String[], java.lang.String)
	 */
	@Override
	public int deleteCodRefundAdditionals(String[] customerCodes, String modifyUser) {
		//   Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = new Date();
		map.put("active", FossConstants.ACTIVE);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("modifyDate", date);
		map.put("modifyUser", modifyUser);
		map.put("version", date.getTime());
		map.put("customerCodes", customerCodes);
		return this.getSqlSession().delete(NAMESPACEADDITIONAL + "deleteCodRefundAdditionals", map);
	}

	/** 
	 * <p> (更新单条代收货款打包退款基础信息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:22
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#updateCodRefundAdditional(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity)
	 */
	@Override
	public int updateCodRefundAdditional(CodRefundAdditionalEntity entity) {
		//   Auto-generated method stub
		deleteCodRefundAdditional(entity);
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(entity.getModifyDate());
		entity.setCreateUser(entity.getModifyUser());
		entity.setModifyDate(new Date());          //NumberConstants.ENDTIME最大时间
		entity.setVersion(entity.getModifyDate().getTime());
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACEADDITIONAL + "addCodRefundAdditional", entity);
	}

	/** 
	 * <p> (根据ID查询单客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:22
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#queryCodRefundAdditionalById(java.lang.String)
	 */
	@Override
	public CodRefundAdditionalEntity queryCodRefundAdditionalById(String id) {
		//   Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("active", FossConstants.ACTIVE);
		map.put("id", id);
		return (CodRefundAdditionalEntity)this.getSqlSession().selectOne(NAMESPACEADDITIONAL + "queryCodRefundAdditionalById", map);
	}

	/** 
	 * <p> (根据客户编码查询代收货款打包退款附件List)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:22
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#queryCodRefundAdditionalByCustomerCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodRefundAdditionalEntity> queryCodRefundAdditionalByCustomerCode(
			CodRefundAdditionalEntity entity, int start, int limit) {
		//   Auto-generated method stub
		if(entity==null){
			entity=new CodRefundAdditionalEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds=new RowBounds(start, limit);
		return (List<CodRefundAdditionalEntity>)this.getSqlSession().selectList(
				NAMESPACEADDITIONAL + "queryCodRefundAdditionalListByCustomerCode", entity, rowBounds);
	}

	/** 
	 * <p> (根据客户编码查询代收货款打包退款附件条数)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:27:22
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.impl.ICodRefundAdditionalDao#queryCodRefundAdditionalCountByCustomerCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity)
	 */
	@Override
	public Long queryCodRefundAdditionalCountByCustomerCode(
			CodRefundAdditionalEntity entity) {
		//  Auto-generated method stub
		entity.setActive(FossConstants.ACTIVE);
		return (Long)getSqlSession().selectOne(NAMESPACEADDITIONAL + "countCodRefundAdditionalListByCustomerCode", entity);
	}

}
