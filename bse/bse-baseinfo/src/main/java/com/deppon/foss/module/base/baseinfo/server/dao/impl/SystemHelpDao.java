package com.deppon.foss.module.base.baseinfo.server.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * AO接口的实现
 * @author zengjunfan
 * @date	2013-4-18下午2:32:47
 * 
 */
/**
 * 系统帮助DAO接口实现
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午4:14:01
 */
public class SystemHelpDao extends SqlSessionDaoSupport implements ISystemHelpDao{
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE ="foss.bse.bse-baseinfo.systemHelp.";
		
	/** 
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:01:03
	 * @param systemHelpEntity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#addSystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity)
	 */
	@Override
	public int addSystemHelpEntity(SystemHelpEntity systemHelpEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insert", systemHelpEntity);
	}
	
	/** 
	 * 分页查询
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:01:50
	 * @param dto
	 * @param limit
	 * @param start
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#querySystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemHelpEntity> querySystemHelpEntity(SystemHelpDto dto,
			int limit, int start) {
		RowBounds rowBounds =new RowBounds(start, limit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUser", dto.getSystemHelpEntity().getCreateUser());
		map.put("topic", dto.getSystemHelpEntity().getTopic());
		map.put("active", dto.getSystemHelpEntity().getActive());
		return this.getSqlSession().selectList(NAMESPACE+"queryAllSystemHelp",map,rowBounds);
	}
	
	/** 
	 * 查询所有记录数
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:03:27
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto)
	 */
	@Override
	public long queryRecordCount(SystemHelpDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUser", dto.getSystemHelpEntity().getCreateUser());
		map.put("topic", dto.getSystemHelpEntity().getTopic());
		map.put("active", dto.getSystemHelpEntity().getActive());
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", map);
	}
	
	/** 
	 * 修改
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:04:29
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#upadteSystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity)
	 */
	@Override
	public int upadteSystemHelpEntity(SystemHelpEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"update",entity);
	}
	
	/** 废除
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:05:05
	 * @param idList
	 * @param modifyUser
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#deleteSystemHelpEntityById(java.util.List, java.lang.String)
	 */
	@Override
	public int deleteSystemHelpEntityById(List<String> idList, String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("active", FossConstants.INACTIVE);
		map.put("activeY", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteById", map);
	}
	
	/** 
	 * 根据ID进行查询
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:05:48
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao#querySystemHelpEntityById(java.lang.String)
	 */
	@Override
	public SystemHelpEntity querySystemHelpEntityById(String id) {
		return (SystemHelpEntity) this.getSqlSession().selectOne(NAMESPACE+"queryById",id);
	}

}
