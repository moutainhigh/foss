package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;

public interface IMarketingSchemeDAO {
	
	/**
	 * <p>根据主键Id删除记录<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午2:27:28
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(String id);

    /**
     * <p>新增记录（内容不为空的字段）<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-2 上午9:28:13
     * @param record
     * @return
     */
    int insertSelective(MarketingSchemeEntity record);

    /**
     * <p>Description:根据主键查询记录<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-2 下午3:44:46
     * @param id
     * @return
     */
    MarketingSchemeEntity selectByPrimaryKey(String id);

    /**
     * <p>Description:根据code查询记录<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-25 下午4:06:45
     * @param 
     * @return
     */
    List<MarketingSchemeEntity> selectByMarketCode(String code, Date billDate);
    
    /**
     * <p>根据主键更新内容不为空的字段<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-20 下午4:44:32
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MarketingSchemeEntity record);

   	/**
   	 * <p>查询发券方案总数<br/></p>
   	 * @author dujunhui-187862
   	 * @date 2014-10-20 下午3:23:32
   	 * @param marketintEvent
   	 * @return
   	 */
   	Long countMarketingScheme(MarketingSchemeEntity marketintEvent);
   	
   	/**
   	 * <p>查询价格折扣方案列表（分页）</p> 
   	 * @author dujunhui-187862
   	 * @date 2014-10-20 下午3:33:26
   	 * @param marketintEvent
   	 * @param start
   	 * @param limit
   	 * @return
   	 * @see
   	 */
   	List<MarketingSchemeEntity> queryMarketingSchemeList(MarketingSchemeEntity marketintEvent, int start, int limit);
   	
    /**
	 * <p>根据NAME查询降价发券方案<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-01 下午6:17:09
	 * @param marketintEventName
	 * @return
	 */
	List<MarketingSchemeEntity> queryMarketingEventByName(String marketintEventName);
	
	/**
	 * @Description: 获取折扣方案最大的编码
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:32:38
	 * @return
	 */
	String getMarketingEventMaxCode(String type);
}