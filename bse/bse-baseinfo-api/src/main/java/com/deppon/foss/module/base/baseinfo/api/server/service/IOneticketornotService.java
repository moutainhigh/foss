package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncResponse;
/**
 * CRM是否一票多件信息Service接口
 * @author 273311
 * @date 2015-12-16 
 * @since
 * @version
 */
public interface IOneticketornotService extends IService {
	/**
	 * 新增是否一票多件信息
	 * @author 273311
	 * @date 2015-12-16
	 * @param entity 是否一票多件信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addOneticketornot(OneticketornotEntity entity);

	/**
	 * <p>根据客户编码code查询信息实体</p>
	 * @author 273311
	 * @date 2015-12-16 
	 * @param code
	 * @return
	 * @see
	 */
	OneticketornotEntity queryOneticketornotBycode(String code);
	/**
	 * CRM一票多件信息进行操作的接口
	 * @author 273311
	 * @date   2015-12-21 
	 *
	 */
	public OneticketornotSyncResponse syncOneticketornotInfo(OneticketornotSyncRequest request)throws CommonException;
}
