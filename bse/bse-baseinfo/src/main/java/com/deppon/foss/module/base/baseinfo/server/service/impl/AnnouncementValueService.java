package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAnnouncementValueDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAnnouncementValueService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AnnouncementDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AnnouncementException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公告信息Service接口的实现
 * @author zengjunfan
 * @date	2013-4-18下午3:06:14
 * 
 */
public class AnnouncementValueService implements IAnnouncementValueService {
	
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(AnnouncementValueService.class); 
	/**
	 * 系统公告的DAO 接口
	 */
	private IAnnouncementValueDao announcementValueDao;
	
	/**
	 * this is set  method
	 */
	public void setAnnouncementValueDao(IAnnouncementValueDao announcementValueDao) {
		this.announcementValueDao = announcementValueDao;
	}

	/**
	 * 新增公告信息
	 * @author zengjunfan
	 * @date	2013-4-18下午3:09:38
	 * @param announcementEntity
	 * @return
	 */
	@Override
	public int addAnnouncement(AnnouncementEntity announcementEntity) {
		//判断插入的是否为空
		if (announcementEntity ==null) {
			throw new AnnouncementException("传入的参数不能为空值");
		}
		announcementEntity.setId(UUIDUtils.getUUID());
		//设置为激活状态
		announcementEntity.setActive(FossConstants.ACTIVE);
		//设置时间
		announcementEntity.setCreateDate(new Date());
		announcementEntity.setModifyDate(new Date());
		//日志记录
		LOGGER.debug("id"+announcementEntity.getId());
		return announcementValueDao.addAnnouncement(announcementEntity);
	}
	/**
	 * 根据条件查询所有符合的公告信息
	 * @author zengjunfan
	 * @date	2013-4-18下午3:34:51
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 */
	@Override
	public List<AnnouncementEntity> queryAnnouncementEntity(
			AnnouncementDto dto, int limit, int start) {
		if(null == dto){
			throw new AnnouncementException("传入的参数值不能为空");
		}
		//判断传过来的状态是否为全部
		if(dto.getAnnouncementEntity().getActive().equals("ALL")){	
			dto.getAnnouncementEntity().setActive("");
		}
		return announcementValueDao.queryAnnouncementEntity(dto, limit, start);
	}
	/**
	 * 查询总的记录数
	 * 
	 * @author zengjunfan
	 * @date	2013-4-18下午3:39:49
	 * @param announcementEntity
	 * @return
	 */
	@Override
	public long queryRecordCount(AnnouncementDto dto) {
		if(null == dto){
			throw new AnnouncementException("传入的参数值不能为空");
		}
		//判断传过来的状态是否为全部
		if(dto.getAnnouncementEntity().getActive().equals("ALL")){	
			dto.getAnnouncementEntity().setActive("");
		}
		return announcementValueDao.queryRecordCount(dto);
	}
	/**
	 * 更新(修改)公告信息：
	 * 先废除这条信息之后再进行添加一条新的数据
	 * @author zengjunfan
	 * @date	2013-4-18下午3:41:50
	 * @param entity
	 * @return
	 */
	@Override
	public int upadteAnnouncement(AnnouncementEntity entity) {
		if(entity ==null){
			throw new AnnouncementException("传入的值不允许为空");	
		}
		List<String> list =new ArrayList<String>();
		list.add(entity.getId());
		int result =announcementValueDao.deleteAnnouncementById(list, entity.getModifyUser());
		//结果大于0 时 说明删除成功 ，
		if(result>0){
			
			//添加新的数据
			 entity.setActive(FossConstants.ACTIVE);
			 entity.setId(UUIDUtils.getUUID());
			 entity.setCreateDate(new Date());
			 entity.setModifyDate(new Date());
			 
			 return announcementValueDao.addAnnouncement(entity);
		}else{
			 return FossConstants.FAILURE;
		}
	}
	/**
	 * 废除公告信息
	 * @author zengjunfan
	 * @date	2013-4-18下午4:13:03
	 * @param idList
	 * @param modifyUser
	 * @return
	 */
	@Override
	public int deleteAnnouncementById(List<String> idList,
			String modifyUser) {
		if(CollectionUtils.isEmpty(idList)){
			throw new AnnouncementException("传入的参数不能为空");
		}
		return announcementValueDao.deleteAnnouncementById(idList, modifyUser);
	}
	/**
	 * 根据传过来的id 进行查询符合条件的公告信息实体
	 * @author zengjunfan
	 * @date	2013-4-19下午6:01:20
	 * @param id
	 * @return
	 */
	@Override
	public AnnouncementEntity queryAnnouncementEntityById(String id) {
		if(id ==null){
			throw new AnnouncementException("传入的参数不能为空");
		}
		return announcementValueDao.queryAnnouncementEntityById(id);
	}

}
