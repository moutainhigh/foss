package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
/**
 * 公共组件--权限查询.
 * 
 * @author lifanghong
 * @date 2013-05-29 
 */
public class CommonResourceConditionAction extends AbstractAction implements
IQueryAction {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The resource list. */
	private List<ResourceEntity> resourceList = new ArrayList<ResourceEntity>();
	/** The resource entity. */
	private ResourceEntity resourceEntity;
	
	private ICommonResourceService commonResourceService;
	
	@JSON
	public String query() {
		resourceList = commonResourceService.queryResourceExactByEntity(resourceEntity, start, limit);
		setTotalCount(commonResourceService.queryResourceExactByEntityCount(resourceEntity));
		return returnSuccess();
	}

	public List<ResourceEntity> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceEntity> resourceList) {
		this.resourceList = resourceList;
	}

	public ResourceEntity getResourceEntity() {
		return resourceEntity;
	}

	public void setResourceEntity(ResourceEntity resourceEntity) {
		this.resourceEntity = resourceEntity;
	}

	public void setCommonResourceService(
			ICommonResourceService commonResourceService) {
		this.commonResourceService = commonResourceService;
	}
	
	
	
}
