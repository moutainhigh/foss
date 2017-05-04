package com.deppon.foss.module.login.server.dao;

import java.util.List;

import com.deppon.foss.module.login.shared.domain.AnnouncementEntity;

/**
 * 
 * 公告信息数据处理访问层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-15 下午1:48:19,content:TODO </p>
 * @author ztjie
 * @date 2013-3-15 下午1:48:19
 * @since
 * @version
 */
public interface IAnnouncementDao {
    
	/**
	 * 
	 * <p>增加公告信息</p> 
	 * @author ztjie
	 * @date 2013-3-15 下午1:52:15
	 * @param announcementEntity
	 * @return
	 * @see
	 */
	int insert(AnnouncementEntity announcementEntity);
    
    /**
     * 
     * <p>删除公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午1:54:23
     * @param id
     * @return
     * @see
     */
    int delete(String id);

    /**
     * 
     * <p>修改公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午1:55:01
     * @param announcementEntity
     * @return
     * @see
     */
    int update(AnnouncementEntity announcementEntity);
    
    /**
     * 
     * <p>通过公告ID得到公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午1:59:39
     * @param id
     * @return
     * @see
     */
    AnnouncementEntity getAnnouncementById(String id);;

    /**
     * 
     * <p>分页加载公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午2:10:00
     * @param offset
     * @param limit
     * @return
     * @see
     */
    List<AnnouncementEntity> getAnnouncementByCount(int start, int limit);

    /**
     * 
     * <p>统计条数</p> 
     * @author ztjie
     * @date 2013-3-18 下午2:40:30
     * @return
     * @see
     */
	Long getAnnouncementCount();

}
