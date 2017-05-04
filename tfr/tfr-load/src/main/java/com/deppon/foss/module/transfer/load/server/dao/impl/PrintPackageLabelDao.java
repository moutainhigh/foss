package com.deppon.foss.module.transfer.load.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IPrintPackageLabelDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.PackagePrintLogDto;

public class PrintPackageLabelDao  extends iBatis3DaoImpl implements IPrintPackageLabelDao {

	/**命名空间常量*/
	private static final String NAMESPACE = "foss.load.printPackage.";
	
	/**
	 * 打印指定包标签的时候
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	@Override
	public Long validatePackageNo(String packageNo) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"validatePackageNo",packageNo);
	}
	
	
	/**
	 * 添加包标签打印日志
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param printPackageVo 包号，打印人等信息addPackagePrintLog
	 */
	@Override
	public void addPackagePrintLog(PackagePrintLogDto  packagePrintLogDto){
		
		this.getSqlSession().insert(NAMESPACE+"addPackagePrintLog",packagePrintLogDto);
	}
	

}
