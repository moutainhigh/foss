package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * TODO电子运单正反扫实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:200689,date:2015-1-27 下午6:04:06,content:TODO </p>
 * @author 200689
 * @date 2015-1-27 下午6:04:06
 * @since
 * @version
 */
public class ScanOrCaclScanList  implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;


	private List<ScanMsgEntity>  scanOrCaclScanList;

	private String userCode;

	


	public List<ScanMsgEntity> getScanOrCaclScanList() {
		return scanOrCaclScanList;
	}


	public void setScanOrCaclScanList(List<ScanMsgEntity> scanOrCaclScanList) {
		this.scanOrCaclScanList = scanOrCaclScanList;
	}


	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	

	
	
}
