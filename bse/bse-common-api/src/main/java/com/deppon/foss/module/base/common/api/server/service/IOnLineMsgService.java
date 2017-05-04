package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;

public interface IOnLineMsgService {
	/**
	 * 新增一条在线通知消息
	 * 
	 * @author:	WangPeng
	 * @date:	2013-7-10上午8:54:07
	 * @param:	MsgOnlineEntity
	 * @return: void
	 */
	boolean addOnlineMsg(MsgOnlineDto msgOnlineDto);
	/**
	 * 修改一条在线通知消息，根据id
	 * 
	 * @author: WangPeng
	 * @date:	2013-7-10上午8:56:58
	 * @param:	String id
	 * @return:	void
	 */
	public boolean updateOnlineMsg(MsgOnlineEntity entity);
	/**
	 * 
	 *<p>批量添加通知记录</p>
	 *@author 130566-zengJunfan
	 *@date   2013-8-6上午10:57:16
	 * @param msgOnlineDtos
	 * @return
	 */
	public boolean addOnlineMsgList(List<MsgOnlineDto> msgOnlineDtos);
	/**
	 * 
	 *<p>批量导出通知记录</p>
	 *@author 130346-lifanghong
	 *@date   2013-8-16上午10:57:16
	 * @param msgOnlineDtos
	 * @return
	 */
	public ExportResource exportMsgList(MsgOnlineEntity entity);
	/**
	 * <p>ECS快递推送在线通知给FOSS，FOSS插入本地数据库的方法</p> 
	 * @author 232607 
	 * @date 2016-4-26 上午8:58:47
	 * @param msgOnlineEntity
	 * @return
	 * @see
	 */
	boolean addOnlineMsgByECS(MsgOnlineEntity entity);
}
