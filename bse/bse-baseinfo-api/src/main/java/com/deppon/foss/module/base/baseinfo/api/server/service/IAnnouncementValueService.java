package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AnnouncementDto;
/**
 * 系统公告的service接口
 * @author zengjunfan
 * @date	2013-4-18 下午1:38:02
 *
 */
public interface IAnnouncementValueService extends IService {
	/**
	 * 添加公告 信息
	 * @author zengjunfan
	 * @date 2013-4-18
	 * @param announcementEntity 公告信息实体
	 * @return 
	 */
	int addAnnouncement(AnnouncementEntity announcementEntity);
	  /**
     * 根据传入对象查询符合条件所有公告信息
     * 
     * @author zengjunfan
     * @date 2013-4-18 
     * @param announcementDto
     *            公告查询实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	List<AnnouncementEntity> queryAnnouncementEntity(AnnouncementDto announcementDto,int limit,int start);
	/**
	 * 根据传入的对象查询记录总数
	 * @author zengjunfan
     * @date 2013-4-18 
	 * @param announcementDto  公告查询实体
	 * @return 总记录数
	 */
	long queryRecordCount(AnnouncementDto announcementDto);
	/**
	 * 通过传人的对象修改公告信息
	 * 
	 * @author zengjunfan
     * @date 2013-4-18 
	 * @param entity  公告信息实体
	 * @return
	 */
	int upadteAnnouncement(AnnouncementEntity entity);
	/**
	 * 根据传过来的id集合进行废除
	 * @author zengjunfan
     * @date 2013-4-18 
	 * @param indexList id集合
	 * @return
	 */
	int deleteAnnouncementById(List<String> idList,String modifyUser);
	/**
	 * 通过传过来的id 进行查询符合条件的公告信息
	 * @author zengjunfan
	 * @date	2013-4-19 下午5:59:52
	 * @param id 
	 * @return
	 */
	AnnouncementEntity queryAnnouncementEntityById(String id);

}
