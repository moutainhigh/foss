package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgCodeShortUrlDto;

public interface IOrgCodeShortUrlService extends Serializable{
	
	/**
	 * 提供给CC的接口
	 * 
	 * @Title: queryLongUrlByShortUrl
	 * @author 232608-wusuhua
	 * @date 2015年6月23日 下午4:58:34
	 * @throws
	 */
	
	@POST
	@Path(value="/queryShortUrlByOrgCode")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	OrgCodeShortUrlDto queryShortUrlByOrgCode(String jsonString);
}
