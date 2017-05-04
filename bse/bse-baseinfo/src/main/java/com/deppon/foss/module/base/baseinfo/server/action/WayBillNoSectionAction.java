package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWayBillNoSectionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.WayBillNoSectionVo;
/**
 * 运单号段管理Action
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 下午2:00:16
 */
public class WayBillNoSectionAction extends AbstractAction{

    /**
     * serialVersionUID.
     */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WayBillNoSectionAction.class);
	
	/**
	 * 运单号段Service接口
	 */
	private IWayBillNoSectionService wayBillNoSectionService;
	
	/**
	 * 运单号段信息VO
	 */
	private WayBillNoSectionVo wayBillNoSectionVo = new WayBillNoSectionVo();
	
	/**
	 * @return String
	 * @description 分页查询运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015下午2:27:28
	 */
	@JSON
	public String queryWayBillNoSections(){
		try {
			List<WayBillNoSectionEntity> entityList = wayBillNoSectionService.queryWayBillNoSection(wayBillNoSectionVo.getEntity(), limit, start);
			wayBillNoSectionVo.setEntityList(entityList);
			//查询总记录数
			Long totalCount = wayBillNoSectionService.queryRecordCount(wayBillNoSectionVo.getEntity());
			//设置totalCount
			this.setTotalCount(totalCount);
			
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.info(e.getMessage(), e);
		    return returnError(e);
		}
	}
	
	/**
	 * @return String
	 * @description 新增运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:17:57
	 */
	@JSON
	public String addWayBillNoSection(){
		try {
		    WayBillNoSectionEntity entity = wayBillNoSectionVo.getEntity();
		    
		    entity = wayBillNoSectionService.addWayBillNoSection(entity);

		    if(entity.getState() != 1){
			    if(entity.getState() == -1){//申请数量的运单号已超过截止号段
			    	return returnError("申请数量的运单号已超过截止号段，请联系相关人员修改后台配置参数!");
			    }
			    if(entity.getState() == NumberConstants.NUMBER_THE_2){//参数为空
			    	return returnError("增加运单号段的参数不能为空!");
			    }
			    if(entity.getState() == NumberConstants.NUMBER_THE_3){//非电子运单大客户不允许分配号段
			    	return returnError("非电子运单大客户不允许分配号段!");
			    }
			    if(entity.getState() == NumberConstants.NUMBER_THE_10){//枷锁失败
			    	return returnError("加锁失败!");
			    }
		    }
		    return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
	}

	public WayBillNoSectionVo getWayBillNoSectionVo() {
		return wayBillNoSectionVo;
	}

	public void setWayBillNoSectionVo(WayBillNoSectionVo wayBillNoSectionVo) {
		this.wayBillNoSectionVo = wayBillNoSectionVo;
	}

	public void setWayBillNoSectionService(
			IWayBillNoSectionService wayBillNoSectionService) {
		this.wayBillNoSectionService = wayBillNoSectionService;
	}

}
