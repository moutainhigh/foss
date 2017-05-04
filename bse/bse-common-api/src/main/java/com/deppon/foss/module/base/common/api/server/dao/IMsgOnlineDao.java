package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 在线消息明细Dao
 * @author 130346-foss-lifanghong
 * @date 2013-07-08 
 */
public interface IMsgOnlineDao {
	/**
	 * 根据条件查询出所有站内消息数据
	 * @author 130346-foss-lifanghong
	 * @param entity 查询条件实体
	 * @date 2013-07-08 
	 * @return 
	 */
	List<MsgOnlineEntity> queryOnlineMsgByEntity(MsgOnlineEntity entity, int start, int limit);
	/**
	 * 根据条件统计出所有站内消息总条数
	 * @author 130346-foss-lifanghong
	 * @param entity 查询条件实体
	 * @date 2013-07-08 
	 * @return 
	 */
	public long countQueryOnlineMsgByEntity(MsgOnlineEntity entity);
	
	/**
	 * 新增一条在线通知消息
	 * 
	 * @author:	WangPeng
	 * @date:	2013-7-10上午8:54:07
	 * @param:	MsgOnlineEntity
	 * @return: int 记录影像行数
	 */
	public int addOnlineMsg(MsgOnlineEntity entity,int status);
	
	
	/**
	 * 修改一条在线通知消息，根据id
	 * 
	 * @author: WangPeng
	 * @date:	2013-7-10上午8:56:58
	 * @param:	String id
	 * @return:	int 记录影像行数
	 */
	public int updateOnlineMsg(MsgOnlineEntity entity);
	
	/**
	 * 根据当前部门查询登陆用户部门未处理的全网消息
	 * 
	 * @author: foss-132599-shenweihua
	 * @date:	2013-7-22下午1:35:58
	 * @param:	String id
	 * @return:	void
	 */
	public Integer queryInstationMsgTotal(CurrentInfo currentInfo);
	/**
	 * 导出在线通知
	 * 
	 * @author: foss-130346-lifanghong
	 * @date:	2013-8-16下午1:35:58
	 * @param:	String id
	 * @return:	void
	 */
	List<LineEntity> queryMsgListForExport(MsgOnlineEntity entity, int start,
			int limit);
}
