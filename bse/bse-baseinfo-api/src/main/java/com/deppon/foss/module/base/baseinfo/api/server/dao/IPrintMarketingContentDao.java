package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;

/**
 * 打印营销内容基础资料DAO层接口
 * @author dujunhui-187862
 * @date 2014-08-26 上午9:09:11
 */
public interface IPrintMarketingContentDao {

	/**
	 * 新增实体
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午9:09:55
	 */
    int addPrintMarketingContent(PrintMarketingContentEntity entity);

    /**
     * 根据ID批量作废实体信息
     * @param codeList
     * @param modifyUser
     * @return
     * @author dujunhui-187862
     * @date 2014-08-26 上午9:10:23
     */
    int deletePrintMarketingContents(String[] codeList, String modifyUser);

    /**
     * 修改实体信息
     * @param entity
     * @return
     * @author dujunhui-187862
     * @date 2014-08-26 上午9:10:58
     */
    int updatePrintMarketingContent(PrintMarketingContentEntity entity);
    
	/**
	 * 根据传入对象查询符合条件的实体信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午9:11:34
	 */
    List<PrintMarketingContentEntity> queryPrintMarketingContentByCondition(PrintMarketingContentEntity entity,
	    int limit, int start);

    /**
     * 统计记录数
     * @param entity
     * @return
     * @author dujunhui-187862
     * @date 2014-08-26 上午9:11:58
     */
    Long queryRecordCount(PrintMarketingContentEntity entity);

    /**
     * 根据ID查询实体详细信息
     * @param id
     * @return
     * @author dujunhui-187862
     * @date 2014-08-26 上午9:12:30
     */
    PrintMarketingContentEntity queryPrintMarketingContentEntityByID(String id);

	/**
	 * <p>TODO(根据城市编码和类型查询基础资料实体 )（唯一）</p> 
	 * @author 187862
	 * @date 2014-08-26 上午9:13:38
	 * @param cityCode
	 * @param cityPattern
	 * @return
	 * @see
	 */
	List<PrintMarketingContentEntity> queryEntityByCodeAndPattern(String orgCode,String cityPattern);

}
