package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOneticketornotDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * CRM是否一票多件信息Dao接口实现
 * @author 273311
 * @date 2015-12-16
 * @since
 * @version
 */
public class OneticketornotDao extends SqlSessionDaoSupport implements IOneticketornotDao{
	/**
	 * 定义常量
	 */
	private static final String USER_CODE = "CRM";
	private static final String NAMESPACE_ONETICKETORNOT = "foss.bse.bse-baseinfo.oneticketornot.";
	/**
	 * 新增是否一票多件信息
	 * @author 273311
	 * @date 2015-12-16 
	 * @param entity 信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addOneticketornot(OneticketornotEntity entity) {
		if (entity == null) {
			return FossConstants.FAILURE;
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		return this.getSqlSession().
		insert(NAMESPACE_ONETICKETORNOT + "insertOneticketornot", entity);
	}
	/**
	 * 作废是否一票多件信息
	 * @author 273311
	 * @date 2015-12-16 
	 * @param entity 信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int deleteOneticketornot(OneticketornotEntity entity) {
		if (entity == null) {
			return FossConstants.FAILURE;
		}
		entity.setModifyUser(USER_CODE);
		entity.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE_ONETICKETORNOT + "deleteOneticketornot", entity);
	}

	/**
	 * <p>根据客户编码查询信息实体</p>
	 * @author 273311
	 * @date 2015-12-16
	 * @param 
	 * @return
	 * @see
	 */
	@Override
	public OneticketornotEntity queryOneticketornotBycode(String code) {
		OneticketornotEntity entity = new OneticketornotEntity();
        entity.setCode(code);
        List<OneticketornotEntity> list=this.getSqlSession().
				selectList(NAMESPACE_ONETICKETORNOT + "queryOneticketornotBycode",entity);
		return CollectionUtils.isEmpty(list) ? null:list.get(0);
	}
	
	
	@Override
	public boolean queryOneticketornotBycode1(String code) {
		OneticketornotEntity entity = new OneticketornotEntity();
        entity.setCode(code);
        List<OneticketornotEntity> list=this.getSqlSession().
				selectList(NAMESPACE_ONETICKETORNOT + "queryOneticketornotBycode",entity);
        boolean bool=false;
        if(list.size()>0){
        	bool=true;
        }
		return bool;
	}



}
