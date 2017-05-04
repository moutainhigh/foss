package com.deppon.pda.bdm.module.push.server.dao;

import com.deppon.pda.bdm.module.push.shared.domain.PushMessage;

public interface IPushDao {
	
	/**
	 * @author：xiaolongwu
	 * @description：消息推送之后，保存到表中
	 * @parameters：推送消息
	 * @return：void
	 * @time：2014-12-30 上午8:23:38
	 */
	void savePushMessage(PushMessage pushMessage);



}
