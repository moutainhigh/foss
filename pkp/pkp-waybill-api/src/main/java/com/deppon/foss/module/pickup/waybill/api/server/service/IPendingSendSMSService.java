package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendSMSVo;

/**
 * 待处理发送短信服务接口
 * @author WangQianJin
 * @date 2013-4-11 上午11:08:45
 */
public interface IPendingSendSMSService extends IService {

	/**
	 * 添加待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午11:10:38
	 */
	void addPendingSendmail(PendingSendSMSEntity pendingSendmail);
	
	/**
	 * 定时任务处理待发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午11:10:38
	 */
	void batchjobs();
	
	/**
	 * 每次更新一定数量待处理短信JobId
	 * @author WangQianJin
	 * @date 2013-05-08
	 */
	PendingSendSMSVo updatePendingSendSMSForJobTopNum(String jobId,String num);
	
	/**
	 * 进行单个短信发送
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-12 11:16:38
	 * @param pendingSendmail
	 */
	void singleHandlePendingSendMail(PendingSendSMSEntity pendingSendmail);
}
