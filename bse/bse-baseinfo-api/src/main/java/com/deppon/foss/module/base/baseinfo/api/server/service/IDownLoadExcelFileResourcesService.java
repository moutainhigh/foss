package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * 文件下载Service
 * 
 * @author WangPeng
 * @date 2013-06-21 9:08AM
 *
 */
public interface IDownLoadExcelFileResourcesService extends IService {

	/**
	 * 根据工号或者文件名称查询要下载的资源信息
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午9:45:43
	 * @param:Entity
	 * @return:List<DownloadInfoEntity>
	 */
	public List<DownloadInfoEntity> queryDownLoadableResource(DownloadInfoEntity entity );
	
	/**
	 * 根据记录id删除该某条记录
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午9:46:20
	 * @param:
	 * @return:Int
	 */
	public int deleteRecordById(String[] ids);
	
	/**
	 * 根据选择的id下载Excel文件
	 * 
	 * @author:WangPeng:
	 * @date:2013-6-21上午9:46:45
	 * @param:
	 * @return: void
	 */
	public void downLoadExcelFile(String[] info,String filePath);
}
