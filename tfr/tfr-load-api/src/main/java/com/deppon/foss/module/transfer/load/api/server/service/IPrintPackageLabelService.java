package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.vo.PrintPackageVo;

public interface IPrintPackageLabelService {

	/**
	 * 校验包标签是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	public String validatePackageNo(String packageNo);
	
	/**
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	public String validation(PrintPackageVo printPackageVo);
	
	/**
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	public List<String>  creatPackageNo(PrintPackageVo printPackageVo);
	
	/**
	 * 添加包标签打印日志
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param printPackageVo 包号，打印人等信息addPackagePrintLog
	 */
	public void addPackagePrintLog(PrintPackageVo printPackageVo);
		
}
