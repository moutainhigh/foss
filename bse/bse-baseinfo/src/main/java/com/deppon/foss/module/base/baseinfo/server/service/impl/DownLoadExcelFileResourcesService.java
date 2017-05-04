package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDownLoadExcelFileResourcesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDownLoadExcelFileResourcesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DownLoadExcelFileException;

/**
 * 文件下载Service实现类
 * 
 * @author WangPeng
 * @date 2013-06-21 10:11AM
 * 
 */
public class DownLoadExcelFileResourcesService implements
		IDownLoadExcelFileResourcesService {
	/**
	 * 记录日志
	 */
	public static final Logger logger = LoggerFactory
			.getLogger(DownLoadExcelFileResourcesService.class);

	/**
	 * 注入下载的dao
	 */
	private IDownLoadExcelFileResourcesDao DownLoadExcelFileResourcesDao;

	public void setDownLoadExcelFileResourcesDao(
			IDownLoadExcelFileResourcesDao downLoadExcelFileResourcesDao) {
		DownLoadExcelFileResourcesDao = downLoadExcelFileResourcesDao;
	}

	/**
	 * 根据工号或者文件名称查询要下载的资源信息
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午10:25:43
	 * @param:Entity
	 * @return:List<DownloadInfoEntity>
	 */
	@Override
	public List<DownloadInfoEntity> queryDownLoadableResource(
			DownloadInfoEntity entity) {
	//	List<DownloadInfoEntity> resouceInfo = null;
		// 判断前台传递过来的对象是否为空
		if (null == entity) {
			throw new DownLoadExcelFileException("请在员工工号和文件名称中选择一项填写提交！");
		} else {
			return DownLoadExcelFileResourcesDao
					.queryDownLoadableResource(entity);
		}
	//	return resouceInfo;
	}

	/**
	 * 根据记录id删除该某条记录
	 * 
	 * @author:WangPeng
	 * @date:2013-6-21上午10:26:20
	 * @param:
	 * @return:Int
	 */
	@Override
	@Transactional
	public int deleteRecordById(String[] ids) {
		// 记录影响的行数
		int count = 0;
		if (ids.length>0) {

			count = DownLoadExcelFileResourcesDao.deleteRecordById(ids);
		}
		return count;
	}

	/**
	 * 根据选择的id下载Excel文件（此方法已经作废）
	 * 
	 * @author:WangPeng:
	 * @date:2013-6-21上午10:31:45
	 * @param:
	 * @return: void
	 */
	@Override
	public void downLoadExcelFile(String[] info, String filePath) {
		if (info.length == 0) {
			throw new DownLoadExcelFileException("没有选中要下载的资源信息");
		} else if (null == filePath || StringUtils.isBlank(filePath)) {

			throw new DownLoadExcelFileException("保存的路径为空");
		} 
		
		for (String fileDownLoadPath : info) {
			// 定义一个用来存储文件信息的缓冲区
			StringBuffer document = new StringBuffer();
			try {
				URL url = new URL(fileDownLoadPath);
				URLConnection conn = url.openConnection();
				// 缓冲输入流
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				// 向指定路径下的文件中写入数据
				FileOutputStream outer = new FileOutputStream(filePath
						+ fileDownLoadPath.lastIndexOf("/"));
				String line = null;
				// 向缓冲区读入数据
				while ((line = reader.readLine()) != null) {
					document.append(line + "\n");
				}
				// 向文件中写入下载的信息
				outer.write(document.toString().getBytes());

				reader.close();
				outer.close();
			} catch (MalformedURLException e) {
				logger.debug(e.getStackTrace().toString());
				throw new DownLoadExcelFileException(e.getStackTrace()
						.toString());
			} catch (IOException e) {
				logger.debug(e.getStackTrace().toString());
				throw new DownLoadExcelFileException(e.getStackTrace()
						.toString());
			}
		}

	}
}
