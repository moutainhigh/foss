package com.deppon.foss.module.login.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.login.shared.domain.AnnouncementEntity;

/**
 * 
 * 公告处理业务逻辑层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-18 上午8:20:58,content:TODO </p>
 * @author ztjie
 * @date 2013-3-18 上午8:20:58
 * @since
 * @version
 */
public interface IAnnouncementService extends IService{
    
	/**
	 * 
	 * <p>增加公告信息</p> 
	 * @author ztjie
	 * @date 2013-3-15 下午1:52:15
	 * @param announcementEntity
	 * @return
	 * @see
	 */
	int save(AnnouncementEntity announcementEntity);
    
    /**
     * 
     * <p>删除公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午1:54:23
     * @param id
     * @return
     * @see
     */
    int remove(String id);

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
    AnnouncementEntity queryAnnouncementById(String id);;

    /**
     * 
     * <p>分页加载公告信息</p> 
     * @author ztjie
     * @date 2013-3-15 下午2:10:00
     * @return
     * @see
     */
    List<AnnouncementEntity> queryAnnouncementByCount(int start, int limit);

     /**
      * 
      * <p>统计公告条数</p> 
      * @author ztjie
      * @date 2013-3-18 下午2:20:18
      * @return
      * @see
      */
    Long queryAllAnnouncementCount();

}
