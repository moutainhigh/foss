package com.deppon.foss.module.base.baseinfo.server.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAnnouncementValueDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AnnouncementDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 系统公告DAO接口的实现
 * @author zengjunfan
 * @date	2013-4-18下午2:32:47
 * 
 */
public class AnnouncementValueDao extends SqlSessionDaoSupport implements IAnnouncementValueDao{
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE ="foss.bse.bse-baseinfo.announcement.";
	
	/**
	 * 新添加公告消息
	 * 
	 * @author zengjunfan
	 * @date	2013-4-18下午2:39:28
	 * @param announcementEntity 公告实体
	 * @return 
	 */
	@Override
	public int addAnnouncement(AnnouncementEntity announcementEntity) {
		
		return this.getSqlSession().insert(NAMESPACE+"insert", announcementEntity);
	}
	/**
	 * 
	 * 根据条件进行查询符合条件的所有的公告信息
	 * @author zengjunfan
	 * @date	2013-4-18下午2:41:33
	 * @param entity 公告实体
	 * @param limit 每页的最大记录数
	 * @param start 开始的记录数
	 * @return 所有符合的公告信息集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnnouncementEntity> queryAnnouncementEntity(
			AnnouncementDto dto, int limit, int start) {
		RowBounds rowBounds =new RowBounds(start, limit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUser", dto.getAnnouncementEntity().getCreateUser());
		map.put("topic", dto.getAnnouncementEntity().getTopic());
		map.put("active", dto.getAnnouncementEntity().getActive());
		map.put("startTime", dto.getStartTime());
		map.put("endTime", dto.getEndTime());
		return this.getSqlSession().selectList(NAMESPACE+"queryAllAnnouncement",map,rowBounds);
	}
	/**
	 * 
	 * 查询所有的记录数
	 * @author zengjunfan
	 * @date	2013-4-18下午2:48:13
	 * @param announcementEntity 公告查询实体
	 * @return
	 */
	@Override
	public long queryRecordCount(AnnouncementDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUser", dto.getAnnouncementEntity().getCreateUser());
		map.put("topic", dto.getAnnouncementEntity().getTopic());
		map.put("active", dto.getAnnouncementEntity().getActive());
		map.put("startTime", dto.getStartTime());
		map.put("endTime", dto.getEndTime());
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", map);
	}
	/**
	 * 
	 * <p>修改公告信息</p>
	 * @author zengjunfan
	 * @date	2013-4-18下午2:48:53
	 * @param entity
	 * @return
	 */
	@Override
	public int upadteAnnouncement(AnnouncementEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE+"update",entity);
	}
	/**
	 * 废除公告信息
	 * @author zengjunfan
	 * @date	2013-4-18下午2:53:43
	 * @param idList 根据id集合进行废除
	 * @param modifyUser 废除人的code
	 * @return
	 */
	@Override
	public int deleteAnnouncementById(List<String> idList,String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("active", FossConstants.INACTIVE);
		map.put("active0", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteById", map);
	}
	/**
	 * 根据传过来的id 进行查询公告信息
	 * 
	 * @author zengjunfan
	 * @date	2013-4-19下午6:04:21
	 * @param id
	 * @return
	 */
	@Override
	public AnnouncementEntity queryAnnouncementEntityById(String id) {
		
		return (AnnouncementEntity) this.getSqlSession().selectOne(NAMESPACE+"queryById",id);
	}

}
