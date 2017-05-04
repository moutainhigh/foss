package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 仓库预警短信接收岗位基础资料Service层实现
 * @author dujunhui-187862
 * @date 2014-08-08 下午3:34:34
 */
public class TitleBaseInfoDao extends SqlSessionDaoSupport implements
		ITitleBaseInfoDao {

	    private static final String NAMESPACE = "foss.bse.bse-baseinfo.titleBaseInfo.";

		/** 
		 * <p>TODO(新增实体)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:35
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#addTitleBaseInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
		 */
		@Override
		public int addTitleBaseInfo(TitleBaseInfoEntity entity) {
			// TODO Auto-generated method stub
			entity.setId(UUIDUtils.getUUID());
			entity.setActive(FossConstants.ACTIVE);
			return this.getSqlSession().insert(NAMESPACE + "addTitleBaseInfo", entity);
		}

		/** 
		 * <p>TODO(根据ID批量作废实体信息)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:37
		 * @param codeList
		 * @param modifyUser
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#deleteTitleBaseInfo(java.lang.String[], java.lang.String)
		 */
		@Override
		public int deleteTitleBaseInfo(String[] codeList, String modifyUser) {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codeList", codeList);
			map.put("modifyUser", modifyUser);
			map.put("modifyDate", new Date());
			map.put("versionNo", new Date().getTime());
			map.put("inactive", FossConstants.INACTIVE);
			map.put("active", FossConstants.ACTIVE);

			return this.getSqlSession().update(NAMESPACE + "deleteTitleBaseInfos", map);
		}

		/** 
		 * <p>TODO(修改实体信息)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:37
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#updateTitleBaseInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
		 */
		@Override
		public int updateTitleBaseInfo(TitleBaseInfoEntity entity) {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionNo", new Date().getTime());
			map.put("modifyDate", new Date());
			map.put("inactive", FossConstants.INACTIVE);
			map.put("active", FossConstants.ACTIVE);
			map.put("modifyUser", entity.getModifyUser());
			map.put("id", entity.getId());
			
			return this.getSqlSession().update(NAMESPACE + "updateTitleBaseInfo", map);
		}

		/** 
		 * <p>TODO(根据输入的条件查询实体列表)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:37
		 * @param entity
		 * @param limit
		 * @param start
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#queryTitleBaseInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity, int, int)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<TitleBaseInfoEntity> queryTitleBaseInfoByCondition(
				TitleBaseInfoEntity entity, int limit, int start) {
			// TODO Auto-generated method stub
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryBaseInfoListByCondition",
				entity, rowBounds);
		}

		/** 
		 * <p>TODO(统计记录数)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:37
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
		 */
		@Override
		public Long queryRecordCount(TitleBaseInfoEntity entity) {
			// TODO Auto-generated method stub
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "countBaseInfoListByCondition",
					entity);
		}

		/** 
		 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 187862
		 * @date 2014-8-8 下午3:35:37
		 * @param id
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#queryTitleBaseInfoEntityByID(java.lang.String)
		 */
		@Override
		public TitleBaseInfoEntity queryTitleBaseInfoEntityByID(String id) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("active", FossConstants.ACTIVE);
			return (TitleBaseInfoEntity) this.getSqlSession().selectOne(
				NAMESPACE + "queryTitleBaseInfoEntityByID", map);
		}
		
		/** 
		 * <p>TODO(根据部门编码查询该部门下所有员工的电话号码)</p> 
		 * @author 187862
		 * @date 2014-8-11 下午3:10:42
		 * @param orgCode
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao#queryPhoneInfoByOrgCode
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<String> queryPhoneInfoByOrgCode(String orgCode) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgCode", orgCode);
			map.put("active", FossConstants.ACTIVE);
			return  this.getSqlSession().selectList(
				NAMESPACE + "queryPhoneInfoByOrgCode", map);
		}

}