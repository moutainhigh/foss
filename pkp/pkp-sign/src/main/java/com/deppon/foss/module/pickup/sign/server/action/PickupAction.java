package com.deppon.foss.module.pickup.sign.server.action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupResultVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupVo;

/**
 * 提货清单Action
 * @author foss-yuting
 * @date 2014-11-21 上午11:21:08
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupAction extends AbstractAction{
	
	/**
	 * 提货清单Vo
	 */
	private PickupVo pickupVo = new PickupVo();
	
	/**
	 * 提货清单结果Vo
	 */
	private PickupResultVo pickupResultVo = new PickupResultVo();
	
	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PickupAction.class);
	
	/**
	 * 分页查询清单列表
	 * @date 2014-11-27 下午3:58:10
	 * @param 
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.action.PickupAction#queryPickupList()
	 */
	@JSON
	public String queryPickupList() {
		LOGGER.info("queryPickupList() begin.......");
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			pickupVo.getPickupDto().setCurrentDeptCode(currentInfo.getCurrentDeptCode());//操作部门编码
			pickupResultVo=pickupService.queryPickupListByParams(pickupVo,this.getStart(), this.getLimit());
			this.totalCount=pickupResultVo.getTotalCount();
		} catch (BusinessException e) {
			return returnError(e);
		}
		LOGGER.info("queryPickupList() end.......");
		return returnSuccess();
	}
	

	/**
	 * 批量更新
	 * @date 2014-11-27 下午3:58:10
	 * @param 
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.action.PickupAction#updatePickupState()
	 */
	@JSON
	public String updatePickupState() {
		LOGGER.info("updatePickupState() begin.......");
		try {
			pickupService.updatePickupState(pickupVo.getPickupList());
		} catch (BusinessException e) {
			return returnError(e);
		}
		LOGGER.info("updatePickupState() end.......");
		return returnSuccess();
	}
	
	
	public PickupVo getPickupVo() {
		return pickupVo;
	}

	public void setPickupVo(PickupVo pickupVo) {
		this.pickupVo = pickupVo;
	}

	public PickupResultVo getPickupResultVo() {
		return pickupResultVo;
	}

	public void setPickupResultVo(PickupResultVo pickupResultVo) {
		this.pickupResultVo = pickupResultVo;
	}

	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}
}
