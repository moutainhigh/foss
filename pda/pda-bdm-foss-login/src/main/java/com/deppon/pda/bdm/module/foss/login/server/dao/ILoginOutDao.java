package com.deppon.pda.bdm.module.foss.login.server.dao;


import java.util.List;

import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginOutInfo;
import com.deppon.pda.bdm.module.foss.login.shared.domain.TaskUserRelation;

/**
 * 
 * 登出服务接口
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-7 下午02:08:04
 */
public interface ILoginOutDao {
	/**
	 * 是否存在未完成的任务
	 * @param pdaLoginOutInfo
	 * @return
	 */
	public List<TaskUserRelation> getTaskRelation(PdaLoginOutInfo pdaLoginOutInfo);
	/**
	 * 
	 * <p>TODO(删除用户登录信息)</p> 
	 * @author Administrator
	 * @date 2012-11-28 下午6:19:43
	 * @param pdaLoginOutInfo
	 * @see
	 */
	public void deleteLogin(PdaLoginOutInfo pdaLoginOutInfo);

}
