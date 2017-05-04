package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;

/**
 * Excel文件下载VO
 * 
 * @author WangPeng
 * @date 2013-06-22 10:18AM
 * 
 */
public class DownloadInfoEntityVo {
	
	//Excel文件下载列表
	List<DownloadInfoEntity> downloadInfoEntityList;
	
	//Excel文件下载详情
	DownloadInfoEntity downloadInfoEntity;

	/**
	 *下面是get、set方法
	 * @author:WangPeng
	 * @date:2013-6-22上午10:22:52
	 * @return:List<DownloadInfoEntity>
	 * 
	 */
	public List<DownloadInfoEntity> getDownloadInfoEntityList() {
		return downloadInfoEntityList;
	}

	public void setDownloadInfoEntityList(
			List<DownloadInfoEntity> downloadInfoEntityList) {
		this.downloadInfoEntityList = downloadInfoEntityList;
	}

	public DownloadInfoEntity getDownloadInfoEntity() {
		return downloadInfoEntity;
	}

	public void setDownloadInfoEntity(DownloadInfoEntity downloadInfoEntity) {
		this.downloadInfoEntity = downloadInfoEntity;
	}
}
