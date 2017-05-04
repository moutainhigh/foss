package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAttributeInfoDto;

/**
 * 查询部门属性的接口
 * @author leo-zeng 
 *
 */
public interface IQueryOrgAttitudeService  extends Serializable{
	
	@POST
	@Path(value="/queryOrgAttribute")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	OrgAttributeInfoDto queryOrgAttribute(String rertunString);
	//http://localhost:8080/bse-baseinfo-web/services/queryOrgAttributeServiceToApp/queryOrgAttribute 
}
