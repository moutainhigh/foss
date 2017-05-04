package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class MsgOnlineDao extends iBatis3DaoImpl implements IMsgOnlineDao {
	
	private static final String NAMESPACE = "foss.bse.bse-common.MsgOnlineDao.";
	/**
	 * 在线消息明细Dao
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-08 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MsgOnlineEntity> queryOnlineMsgByEntity(MsgOnlineEntity entity,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryOnlineMsgByEntity", entity, rowBounds);
	}
	/**
	 * 根据条件统计出所有站内消息总条数
	 * @author 130346-foss-lifanghong
	 * @param entity 查询条件实体
	 * @date 2013-07-08 
	 * @return 
	 */
	@Override
	public long countQueryOnlineMsgByEntity(MsgOnlineEntity entity) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"countQueryOnlineMsgByEntity", entity);
		}
	
	/**
	 * 新增一条在线通知消息
	 * 
	 * @author:	WangPeng
	 * @date:	2013-7-10上午8:54:07
	 * @param:	MsgOnlineEntity
	 * @return: int 记录影像行数
	 */
	@Override
	public int addOnlineMsg(MsgOnlineEntity entity,int status) {
		int count = 0;
		//生成一个新的Id
		String newId = UUIDUtils.getUUID();
		if(status == 0){
			entity.setCreateTime(new Date());
			entity.setId(newId);
			//0 表示界面新增
			count = getSqlSession().insert(NAMESPACE+"addOnlineMsg", entity);
			
		}else if(status == 1){ //1 表示作废后新增
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("newId", newId);
			map.put("entity", entity);
			
			count = getSqlSession().insert(NAMESPACE+"addOnlineMsgAfterCancell", map);
		}
		return count;
	}
	
	/**
	 * 修改一条在线通知消息，根据id
	 * 
	 * @author: WangPeng
	 * @date:	2013-7-10上午8:56:58
	 * @param:	String id
	 * @return:	int 记录影像行数
	 */
	@Override
	public int updateOnlineMsg(MsgOnlineEntity entity) {
		int count = 0;
		entity.setModifyTime(new Date());
		count = getSqlSession().update(NAMESPACE+"updateOnlineMsg", entity);
		return count;
	}
	
	/**
	 * 根据当前部门查询登陆用户部门未处理的全网消息
	 * 
	 * @author: foss-132599-shenweihua
	 * @date:	2013-7-22下午1:35:58
	 * @param:	String id
	 * @return:	void
	 */
	@Override
	public Integer queryInstationMsgTotal(CurrentInfo currentInfo) {
		int count=0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveOrgCode", currentInfo.getCurrentDeptCode());
		map.put("active",FossConstants.ACTIVE);
		count  = (Integer) getSqlSession().selectOne(NAMESPACE+"queryInstationMsgTotal", map);
		return count;
	}
	/**
	 * 导出在线通知
	 * 
	 * @author: foss-130346-lifanghong
	 * @date:	2013-8-16下午1:35:58
	 * @param:	String id
	 * @return:	void
	 */
	@SuppressWarnings("unchecked")
    @Override
    public List<LineEntity> queryMsgListForExport(MsgOnlineEntity entity, int start, int limit) {
	entity.setActive(FossConstants.ACTIVE);
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryMsgListForExport", entity, new RowBounds(start, limit));
    }
}
