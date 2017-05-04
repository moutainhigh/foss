package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisUrlInfoDto;

/**
 * 同步给移动开发GIS长地址接口
 * 
 * @ClassName: IOrgGisUrlService
 * @author 200664-yangjinheng
 * @date 2014年10月17日 下午1:49:09
 */

public interface IOrgGisUrlService extends Serializable {

	/**
	 * 根据移动端提供的短地址查询长地址
	 * 
	 * @Title: queryLongUrlByShortUrl
	 * @author 200664-yangjinheng
	 * @date 2014年10月20日 下午4:58:34
	 * @throws
	 */
	@POST
	@Path(value = "/queryLongUrlByShortUrl")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	OrgGisUrlInfoDto queryLongUrlByShortUrl(String jsonString);

	/**
	 * 当行政组织变化时，更新GIS短地址信息
	 * 
	 * @Title: updateGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午9:54:07
	 * @throws
	 */
	void updateGisUrlInfo(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity);
}
