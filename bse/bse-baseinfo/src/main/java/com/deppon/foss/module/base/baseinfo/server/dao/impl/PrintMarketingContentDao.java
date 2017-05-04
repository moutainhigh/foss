package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 打印营销内容接收岗位基础资料Service层实现
 * @author dujunhui-187862
 * @date 2014-08-26 上午10:46:34
 */
public class PrintMarketingContentDao extends SqlSessionDaoSupport implements
		IPrintMarketingContentDao {

	    private static final String NAMESPACE = "foss.bse.bse-baseinfo.printMarketingContent.";

		/** 
		 * <p>TODO(新增实体)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#addPrintMarketingContent(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
		 */
		@Override
		public int addPrintMarketingContent(PrintMarketingContentEntity entity) {
			entity.setId(UUIDUtils.getUUID());
			entity.setActive(FossConstants.ACTIVE);
			return this.getSqlSession().insert(NAMESPACE + "addMarketingContent", entity);
		}

		/** 
		 * <p>TODO(批量删除)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param codeList
		 * @param modifyUser
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#deletePrintMarketingContents(java.lang.String[], java.lang.String)
		 */
		@Override
		public int deletePrintMarketingContents(String[] codeList,
				String modifyUser) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codeList", codeList);
			map.put("modifyUser", modifyUser);
			map.put("modifyDate", new Date());
			map.put("versionNo", new Date().getTime());
			map.put("inactive", FossConstants.INACTIVE);
			map.put("active", FossConstants.ACTIVE);

			return this.getSqlSession().update(NAMESPACE + "deleteMarketingContents", map);
		}

		/** 
		 * <p>TODO(修改实体)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#updatePrintMarketingContent(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
		 */
		@Override
		public int updatePrintMarketingContent(
				PrintMarketingContentEntity entity) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionNo", new Date().getTime());
			map.put("modifyDate", new Date());
			map.put("inactive", FossConstants.INACTIVE);
			map.put("active", FossConstants.ACTIVE);
			map.put("modifyUser", entity.getModifyUser());
			map.put("id", entity.getId());
			
			return this.getSqlSession().update(NAMESPACE + "updateMarketingContent", map);
		}

		/** 
		 * <p>TODO(分页查询)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param entity
		 * @param limit
		 * @param start
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#queryPrintMarketingContentByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity, int, int)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<PrintMarketingContentEntity> queryPrintMarketingContentByCondition(
				PrintMarketingContentEntity entity, int limit, int start) {
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryMarketingContentByCondition",
				entity, rowBounds);
		}

		/** 
		 * <p>TODO(统计查询记录数)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param entity
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
		 */
		@Override
		public Long queryRecordCount(PrintMarketingContentEntity entity) {
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "countMarketingContentByCondition",
					entity);
		}

		/** 
		 * <p>TODO(根据ID查询实体)</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param id
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao#queryPrintMarketingContentEntityByID(java.lang.String)
		 */
		@Override
		public PrintMarketingContentEntity queryPrintMarketingContentEntityByID(
				String id) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("active", FossConstants.ACTIVE);
			return (PrintMarketingContentEntity) this.getSqlSession().selectOne(
				NAMESPACE + "queryMarketingContentByID", map);
		}
		
		/** 
		 * <p>TODO(根据城市编码和类型查询基础资料实体营销内容 (唯一))</p> 
		 * @author 187862
		 * @date 2014-8-26 上午10:46:37
		 * @param cityCode
		 * @param cityPattern
		 * @return 
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<PrintMarketingContentEntity> queryEntityByCodeAndPattern(String cityCode,String cityPattern) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cityCode", cityCode);
			map.put("cityPattern", cityPattern);
			return  this.getSqlSession().selectList(
				NAMESPACE + "queryEntityByCodeAndPattern", map);
		}


}