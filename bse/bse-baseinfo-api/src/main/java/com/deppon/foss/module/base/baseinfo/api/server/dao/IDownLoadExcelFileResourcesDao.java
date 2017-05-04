package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * 文件下载Dao
 * 
 * @author WangPeng
 * @date 2013-06-21 9:50AM
 *
 */
public interface IDownLoadExcelFileResourcesDao {

	/**
	 * 根据工号或者文件名查询下载的文件资源信息
	 * 若按文件名查询且工号为空时，则查询条件要加上
	 * 当前登录人的工号
	 * @author:WangPeng
	 * @date:2013-6-21上午9:51:43
	 * @param:Entity
	 * @return:List<DownloadInfoEntity>
	 */
	public List<DownloadInfoEntity> queryDownLoadableResource(DownloadInfoEntity entity );
	
	/**
	 * 根据选中记录的id删除某条记录
	 * @author:WangPeng
	 * @date:2013-6-21上午9:52:20
	 * @param:String id
	 * @return:Int
	 */
	public int deleteRecordById(String[] ids);
	
}
