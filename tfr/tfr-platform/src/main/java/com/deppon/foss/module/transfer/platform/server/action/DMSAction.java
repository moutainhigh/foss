/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * @desc 获取config中配置信息
 * 
 * @author 105795
 * @date 2015-07-03
 */
public class DMSAction extends AbstractAction {

	private String dmsAddress;
	
	private static String DMS_URL="dms.address.path";
	
	//查找配置文件中的dms地址
	@JSON
	public String findDmsAddress(){
		
		try {
			String address=PropertiesUtil.getKeyValue(DMS_URL);
			if(StringUtil.isEmpty(address)){
				throw new TfrBusinessException("DMS系统地址没配置！");
			}else{
				this.dmsAddress=address;
			}
			
		} catch (TfrBusinessException e) {

			return this.returnError(e); 
			 
		}
		return SUCCESS;
		
	}
	/**
	 * @return the dmsAddress
	 */
	public String getDmsAddress() {
		return dmsAddress;
	}
	/**
	 * @param dmsAddress the dmsAddress to set
	 */
	public void setDmsAddress(String dmsAddress) {
		this.dmsAddress = dmsAddress;
	}
	
	
	
}
