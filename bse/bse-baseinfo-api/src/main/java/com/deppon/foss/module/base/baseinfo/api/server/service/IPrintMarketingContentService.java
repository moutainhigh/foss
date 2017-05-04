package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;

/**
 * 打印营销内容基础资料Service层接口
 * @author dujunhui-187862
 * @date 2014-08-26 上午9:17:22
 */

public interface IPrintMarketingContentService {
	
	/**
	 * 新增实体
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午9:17:58
	 */
	int addPrintMarketingContent(PrintMarketingContentEntity entity);

	/**
	 * 根据ID批量作废实体信息
	 * @param codeList
	 * @param modifyUser
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午9:29:11
	 */
	int deletePrintMarketingContents(String[] codeList, String modifyUser);

	/**
	 * 修改实体信息
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午10:38:23
	 */
	int updatePrintMarketingContent(PrintMarketingContentEntity entity);

	/**
	 * 根据传入对象查询符合条件的实体信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午10:39:33
	 */
	List<PrintMarketingContentEntity> queryPrintMarketingContentByCondition(PrintMarketingContentEntity entity,
			int limit, int start);

	/**
	 * 统计记录数
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午10:40:26
	 */
	Long queryRecordCount(PrintMarketingContentEntity entity);

	/**
	 * 根据ID查询实体详细信息
	 * @param id
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 上午10:40:26
	 */
	PrintMarketingContentEntity queryPrintMarketingContentEntityByID(String id);

	/**
	 * <p>TODO(根据城市编码和类型查询基础资料实体营销内容)(唯一)</p> 
	 * @author 187862
	 * @date 2014-08-26 上午10:40:26
	 * @param cityCode
	 * @param cityPattern
	 * @return
	 * @see
	 */
	String queryEntityByCodeAndPattern(String cityCode,String cityPattern);

}
