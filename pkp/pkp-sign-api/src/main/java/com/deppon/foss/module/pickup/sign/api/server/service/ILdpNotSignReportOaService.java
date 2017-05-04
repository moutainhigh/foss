package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * 快递代理理外发XX天未签收自动上报OA丢货Service接口
 * @ClassName: ILdpNotSignReportOaService 
 * @author 200664-yangjinheng
 * @date 2014年9月3日 上午8:24:06
 */
public interface ILdpNotSignReportOaService extends IService{


	/**
	 * 上报OA丢货	
	 * @Title: reportOALessGoods 
	 * @author 200664-yangjinheng
	 * @date 2014年9月12日 下午7:20:46
	 * @throws
	 */
	void reportOALessGoods();
}
