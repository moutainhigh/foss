package com.deppon.foss.module.login.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.login.server.dao.IAnnouncementDao;
import com.deppon.foss.module.login.server.service.IAnnouncementService;
import com.deppon.foss.module.login.shared.domain.AnnouncementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 公告处理业务逻辑层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-18 上午8:23:31,content:TODO </p>
 * @author ztjie
 * @date 2013-3-18 上午8:23:31
 * @since
 * @version
 */
public class AnnouncementService implements IAnnouncementService {

	private IAnnouncementDao announcementDao;
	
	public void setAnnouncementDao(IAnnouncementDao announcementDao) {
		this.announcementDao = announcementDao;
	}

	@Transactional
	@Override
	public int save(AnnouncementEntity announcementEntity) {
		Date date = new Date();
		String userCode = UserContext.getCurrentUser().getUserName();
		announcementEntity.setId(UUIDUtils.getUUID());
		announcementEntity.setActive(FossConstants.ACTIVE);
		announcementEntity.setCreateDate(date);
		announcementEntity.setModifyDate(date);
		announcementEntity.setCreateUser(userCode);
		announcementEntity.setModifyUser(userCode);
		int result = announcementDao.insert(announcementEntity);
		return result;
	}

	@Transactional
	@Override
	public int remove(String id) {
		int result = announcementDao.delete(id);
		return result;
	}

	@Transactional
	@Override
	public int update(AnnouncementEntity announcementEntity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AnnouncementEntity queryAnnouncementById(String id) {
		AnnouncementEntity announcementEntity = announcementDao.getAnnouncementById(id);
		return announcementEntity;
	}

	@Override
	public List<AnnouncementEntity> queryAnnouncementByCount(int start, int limit) {
		List<AnnouncementEntity> announcementEntities = announcementDao.getAnnouncementByCount(start, limit);
		return announcementEntities;
	}

	@Override
	public Long queryAllAnnouncementCount() {
		Long count = announcementDao.getAnnouncementCount();
		return count;
	}

	
}
