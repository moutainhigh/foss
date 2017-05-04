package com.deppon.foss.module.base.querying.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;


public interface IMsgOnlineService  extends IService {
	
	/**
	 * 根据条件查询消息
	 * 
	 * @author 130346-foss-lifanghong
     * @date 2013-07-08 
	 */
	List<MsgOnlineEntity> queryOnlineMsgByEntity(MsgOnlineEntity entity, int start, int limit);
	/**
	 * 根据条件查询消息总数
	 * 
	 * @author 130346-foss-lifanghong
     * @date 2013-07-08 
	 */
	Long countQueryOnlineMsgByEntity(MsgOnlineEntity entity);
	
	
	/**
	 * 根据运单号查询部门
	 * 
	 * @author: lifanghong
	 * @date:	2013-7-12上午8:56:58
	 * @param:	String id
	 * @return:	void
	 */
	public MsgOnlineDto queryOrgByBillNo(String billNo);
	/**
	 * 根据交接单和正单查询运单
	 * 
	 * @author: lifanghong
	 * @date:	2013-7-12上午8:56:58
	 * @param:	String id
	 * @return:	void
	 */
	public  List<MsgOnlineDto> queryBillNoByNo(MsgOnlineDto msgOnlineDto);
}

