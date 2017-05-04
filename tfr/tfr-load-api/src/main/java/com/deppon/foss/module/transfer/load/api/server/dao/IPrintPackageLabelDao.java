package com.deppon.foss.module.transfer.load.api.server.dao;

import com.deppon.foss.module.transfer.load.api.shared.dto.PackagePrintLogDto;


public interface IPrintPackageLabelDao {

	
	public Long validatePackageNo(String packageNo);

	/**
	 * 添加包标签打印日志
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param printPackageVo 包号，打印人等信息addPackagePrintLog
	 */
	public void addPackagePrintLog(PackagePrintLogDto  packagePrintLogDto);
}
