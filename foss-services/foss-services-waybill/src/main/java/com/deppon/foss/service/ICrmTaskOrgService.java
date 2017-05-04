package com.deppon.foss.service;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.transfer.common.api.shared.dto.OrgDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TaskDeptRequest;



@Produces(MediaType.APPLICATION_JSON)
public interface ICrmTaskOrgService {

	
	@POST
	@Path(value = "/matchTaskOrg")
	List<OrgDto> matchTaskOrg(String taskDeptRequest);
	
}
