package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDownLoadExcelFileResourcesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * 文件下载Dao层实现类
 * 
 * @author WangPeng
 * @date 2013-06-21 10:11AM
 *
 */
public class DownLoadExcelFileResourcesDao extends SqlSessionDaoSupport
		implements IDownLoadExcelFileResourcesDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.DownLoadExcelFileResourcesDao.";

	/**
	 * 根据工号或者文件名称查询要下载的资源信息
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午10:25:43
	 * @param:Entity
	 * @return:List<DownloadInfoEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DownloadInfoEntity> queryDownLoadableResource(
			DownloadInfoEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDownLoadableResource", entity);
	}

	/**
	 * 根据ID作废一些记录
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午10:25:43
	 * @param:String id
	 * @return:Int
	 */
	@Override
	public int deleteRecordById(String[] ids) {
		Map<String,Object> infos = new HashMap<String,Object>();
		infos.put("infos", Arrays.asList(ids));
		return this.getSqlSession().delete(NAMESPACE+"deleteRecordById", infos);
	}

}
