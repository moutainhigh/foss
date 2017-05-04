package com.deppon.pda.bdm.module.core.server.service;
/**         
 * pda发送数据到vas系统  
 * @author 268974 wangzhili      
 * @created 2015-12-25     
 */
public interface IPDAToVASService {
	//发送客户清关条码到vas
	public String pdaSendDataToVAS(String customsCode);
	//public void PDASendDataToVAS(String logisticNo,String verifyCode,String remark );
}
