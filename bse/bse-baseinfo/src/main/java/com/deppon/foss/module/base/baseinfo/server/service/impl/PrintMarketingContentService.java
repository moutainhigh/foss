package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPrintMarketingContentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PrintMarketingContentException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 打印营销内容基础资料Service层实现
 * @author dujunhui-187862
 * @date 2014-08-26 下午1:59:23
 * 
*/
public class PrintMarketingContentService implements IPrintMarketingContentService {
	
    /**
     * 打印营销内容基础资料DAO层接口
     */
    private IPrintMarketingContentDao printMarketingContentDao;
    
	/**
	 * @param printMarketingContentDao the printMarketingContentDao to set
	 */
	public void setPrintMarketingContentDao(
			IPrintMarketingContentDao printMarketingContentDao) {
		this.printMarketingContentDao = printMarketingContentDao;
	}

	/** 
	 * <p>TODO(新增信息)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:49
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#addPrintMarketingContent(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
	 */
	@Override
	public int addPrintMarketingContent(PrintMarketingContentEntity entity) {
		if(entity.getContent().length()>NumberConstants.NUMBER_60){
			throw new PrintMarketingContentException("营销内容过长，允许输入汉字60字！","营销内容过长，允许输入汉字60字！");
			
		}
		int result = printMarketingContentDao.addPrintMarketingContent(entity);
		return result;
	}

	/** 
	 * <p>TODO(删除信息)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:51
	 * @param codeList
	 * @param modifyUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#deletePrintMarketingContents(java.lang.String[], java.lang.String)
	 */
	@Override
	public int deletePrintMarketingContents(String[] codeList, String modifyUser) {
		int result= printMarketingContentDao.deletePrintMarketingContents(codeList, modifyUser);
		return result;
	}

	/** 
	 * <p>TODO(修改信息)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:51
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#updatePrintMarketingContent(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
	 */
	@Override
	public int updatePrintMarketingContent(PrintMarketingContentEntity entity) {
		int result= printMarketingContentDao.updatePrintMarketingContent(entity);
		return result;
	}

	/** 
	 * <p>TODO(根据条件查询)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:51
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#queryPrintMarketingContentByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity, int, int)
	 */
	@Override
	public List<PrintMarketingContentEntity> queryPrintMarketingContentByCondition(
			PrintMarketingContentEntity entity, int limit, int start) {
		if(null==entity){
			entity =new PrintMarketingContentEntity();
		}
		List<PrintMarketingContentEntity> list= printMarketingContentDao.queryPrintMarketingContentByCondition(entity, limit, start);
		return list;
	}

	/** 
	 * <p>TODO(根据条件查询条数)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:51
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity)
	 */
	@Override
	public Long queryRecordCount(PrintMarketingContentEntity entity) {
		return printMarketingContentDao.queryRecordCount(entity);
	}

	/** 
	 * <p>TODO(根据ID查询实体)</p> 
	 * @author 187862
	 * @date 2014-8-26 下午2:00:51
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService#queryPrintMarketingContentEntityByID(java.lang.String)
	 */
	@Override
	public PrintMarketingContentEntity queryPrintMarketingContentEntityByID(
			String id) {
		return printMarketingContentDao.queryPrintMarketingContentEntityByID(id);
	}

	/** 
	 * <p>TODO(根据城市编码和类型查询基础资料实体营销内容)（唯一）</p> 
	 * @author 187862
	 * @date 2014-8-11下午2:47:14
	 * @param cityCode
	 * @param cityPattern(出发城市DictionaryValueConstants.DEPARTURE_CITY,到达城市DictionaryValueConstants.ARRIVAL_CITY)
	 * @return 
	 */
	@Override
	public String queryEntityByCodeAndPattern(String cityCode,String cityPattern) {
		if(StringUtil.isEmpty(cityCode)){
			return null;
		}
		if(StringUtil.isEmpty(cityPattern)){
			return null;
		}
		
		List<PrintMarketingContentEntity> list=printMarketingContentDao.queryEntityByCodeAndPattern(cityCode, cityPattern);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return printMarketingContentDao.queryEntityByCodeAndPattern(cityCode, cityPattern).get(0).getContent();
	}
}
