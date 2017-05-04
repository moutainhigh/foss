package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * 保存各个模块右键导出EXCEL文件的相关信息
 * 用户查询界面查询且下载相关的文件信息
 * 
 * @author:WangPeng
 * @date:2013-06-20 2:25PM
 *
 */
public interface IrecordImportExcelFileInfoDao {

	public int saveNeedImportExcelFileInfo(DownloadInfoEntity entity); 
}
