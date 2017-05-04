package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgGisUrlEntity;

/**
 * 同步给移动开发GIS长地址Dao
 * 
 * @ClassName: IOrgGisUrlDao
 * @author 200664-yangjinheng
 * @date 2014年10月21日 上午10:17:50
 */
public interface IOrgGisUrlDao {

	/**
	 * 根据组织Code查询
	 * 
	 * @Title: queryGisUrlInfoByOrgCode
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:37:08
	 * @throws
	 */
	OrgGisUrlEntity queryGisUrlInfoByOrgCode(String code);

	/**
	 * 作废GIS短地址信息
	 * 
	 * @Title: deleteGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:37:40
	 * @throws
	 */
	OrgGisUrlEntity deleteGisUrlInfo(OrgAdministrativeInfoEntity entity);

	/**
	 * 添加新GIS短地址信息
	 * 
	 * @Title: addGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:38:10
	 * @throws
	 */
	void addGisUrlInfo(OrgAdministrativeInfoEntity entity);

	/**
	 * 根据移动端提供的短地址查询长地址
	 * 
	 * @Title: queryLongUrlByShortUrl
	 * @author 200664-yangjinheng
	 * @date 2014年10月23日 上午11:01:07
	 * @throws
	 */
	OrgGisUrlEntity queryLongUrlByShortUrl(String shortUrl);
	
	
	List<OrgAdministrativeInfoEntity> queryAll();

}
