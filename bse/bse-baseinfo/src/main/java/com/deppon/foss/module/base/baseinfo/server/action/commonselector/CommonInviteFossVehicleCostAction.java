package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonInviteFossVehicleCostService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.InviteFossVehicleCostVo;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.VehicleDrivingVo;
public class CommonInviteFossVehicleCostAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 事业部大区信息action接口
	 * @author 332219
	 */
   
    private ICommonInviteFossVehicleCostService commonInviteFossVehicleCostService;

	private InviteFossVehicleCostVo inviteFossVehicleCostVo = new InviteFossVehicleCostVo();

	/**
	 * 查询事业部供选择器用
	 * @return
	 */
	@JSON
	public String queryBusinessDivision(){
		
		inviteFossVehicleCostVo.setInviteFossVehicleCostEntityList(commonInviteFossVehicleCostService
				.queryCommonInviteFossVehicleCostByBusiness(inviteFossVehicleCostVo.getInviteFossVehicleCostEntity(), start,
						limit));
		setTotalCount(commonInviteFossVehicleCostService
				.queryCommonBusinessCount(inviteFossVehicleCostVo.getInviteFossVehicleCostEntity()));
		return returnSuccess();
		
	}
	
	/**
	 * 查询大区供选择器用
	 * @return
	 */
	@JSON
	public String queryRegional(){
		
		inviteFossVehicleCostVo.setInviteFossVehicleCostEntityList(commonInviteFossVehicleCostService
				.queryCommonInviteFossVehicleCostByRegional(inviteFossVehicleCostVo.getInviteFossVehicleCostEntity(), start,
						limit));
		setTotalCount(commonInviteFossVehicleCostService
				.queryCommonRegionalCount(inviteFossVehicleCostVo.getInviteFossVehicleCostEntity()));
		return returnSuccess();
		
	}
	
	/**
	 * 查询快递点部和分部供选择器用
	 * @return
	 */
	@JSON
	public String queryExpressage(){
		
		inviteFossVehicleCostVo.setInviteCommonExpressageEntityList(commonInviteFossVehicleCostService
				.queryCommonInviteFossVehicleCostByExpressage(inviteFossVehicleCostVo.getInviteCommonExpressageEntity(), start,
						limit));
		setTotalCount(commonInviteFossVehicleCostService
				.queryCommonExpressageCount(inviteFossVehicleCostVo.getInviteCommonExpressageEntity()));
		return returnSuccess();
		
	}

	public void setCommonInviteFossVehicleCostService(
			ICommonInviteFossVehicleCostService commonInviteFossVehicleCostService) {
		this.commonInviteFossVehicleCostService = commonInviteFossVehicleCostService;
	}

	public InviteFossVehicleCostVo getInviteFossVehicleCostVo() {
		return inviteFossVehicleCostVo;
	}

	public void setInviteFossVehicleCostVo(
			InviteFossVehicleCostVo inviteFossVehicleCostVo) {
		this.inviteFossVehicleCostVo = inviteFossVehicleCostVo;
	}
}