package com.deppon.pda.bdm.module.foss.monitor.server.dao;

import java.util.Map;

import com.deppon.pda.bdm.module.core.shared.vos.MessgeVo;

/**
 * 
  * @ClassName IMonitorDao 
  * @Description TODO 监控接口
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-1 上午11:09:14
 */
public interface IMonitorDao {
	public void saveMonitorInfo(Map<String,MessgeVo> msgMap) throws Exception;
	public void delMonitorInfo() throws Exception;
}
