package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IrecordImportExcelFileInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * 保存各个模块右键导出EXCEL文件的相关信息
 * 用户查询界面查询且下载相关的文件信息
 * 
 * @author:WangPeng
 * @date:2013-06-20 2:25PM
 *
 */
public class RecordImportExcelFileInfoDao extends SqlSessionDaoSupport implements
		IrecordImportExcelFileInfoDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.RecordImportExcelFileInfoDao.";
	/**
	 * 
	 * @author:WangPeng
	 * @date:2013-6-20下午2:46:18
	 * @param:String[] info
	 * @return: void
	 * 
	 */
	@Override
	public int saveNeedImportExcelFileInfo(DownloadInfoEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insertDownLoadInfo", entity);
	}

}
