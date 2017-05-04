package com.deppon.foss.module.login.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.login.server.dao.IAnnouncementDao;
import com.deppon.foss.module.login.shared.domain.AnnouncementEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 公告信息数据处理访问层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:ztjie,date:2013-3-15 下午4:03:56,content:TODO
 * </p>
 * 
 * @author ztjie
 * @date 2013-3-15 下午4:03:56
 * @since
 * @version
 */
public class AnnouncementDao extends SqlSessionDaoSupport implements
		IAnnouncementDao {

	private static final String NAMESPACE = "foss.login.announcement.";

	@Override
	public int insert(AnnouncementEntity announcementEntity) {
		return getSqlSession().insert(NAMESPACE + "insert", announcementEntity);
	}

	@Override
	public int delete(String id) {
		return getSqlSession().delete(NAMESPACE + "delete", id);
	}

	@Override
	public int update(AnnouncementEntity announcementEntity) {
		return getSqlSession().update(NAMESPACE + "update", announcementEntity);
	}

	@Override
	public AnnouncementEntity getAnnouncementById(String id) {
		return (AnnouncementEntity) getSqlSession().selectOne(
				NAMESPACE + "getAnnouncementById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnnouncementEntity> getAnnouncementByCount(int start, int limit) {
		RowBounds rowBound = new RowBounds(start, limit);
		String active = FossConstants.ACTIVE;
		return getSqlSession().selectList(NAMESPACE + "getAnnouncementByCount",
				active, rowBound);
	}

	@Override
	public Long getAnnouncementCount() {
		String active = FossConstants.ACTIVE;
		return (Long) getSqlSession().selectOne(NAMESPACE + "getAnnouncementCount",active);
	}

}
