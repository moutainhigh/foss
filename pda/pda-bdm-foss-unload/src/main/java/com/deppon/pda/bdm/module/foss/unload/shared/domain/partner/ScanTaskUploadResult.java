package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName: ScanTaskUploadResult 
 * @Description: TODO(扫描任务上传返回结果实体) 
 * @author &268974  wangzhili
 * @date 2016-2-22 上午8:56:46 
 *
 */
public class ScanTaskUploadResult implements Serializable{
	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	private List<String> scanTaskUpload;

	public List<String> getScanTaskUpload() {
		return scanTaskUpload;
	}

	public void setScanTaskUpload(List<String> scanTaskUpload) {
		this.scanTaskUpload = scanTaskUpload;
	}
	

}
