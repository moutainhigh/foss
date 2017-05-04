package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressVehiclesVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.util.CollectionUtils;

public class ExpressVehiclesAction  extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6487007798501092862L;
	
	//"快递车辆"参数和结果对象
	ExpressVehiclesVo expressVehiclesVo ;
	
	IExpressVehiclesService expressVehiclesService ;
	
	/**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
    /**
     * <p>删除快递车辆</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-1 上午9:47:40
     * @return
     * @see
     */
    @JSON
    public String deleteExpressVehicles(){
	List<String> ids = expressVehiclesVo.getVehListIds();
	if(CollectionUtils.isEmpty(ids)){
		throw new BusinessException("请选中要删除的记录！");
	}
		String modifyUser = getCurrentUser().getEmployee().getEmpCode();
		try {
			expressVehiclesService.deleteExpressVehicles(ids, modifyUser);
			 return returnSuccess("作废成功！");
		} catch (LeasedVehicleException e) {
			return returnError(e.getMessage());
		}
    }
    
	
    /**
     * <p>快递车辆新增</p> 
     * @author 100847-foss-
     * @date 2013-08-16 上午9:47:35
     * @return
     * @see
     */
    @JSON
    public String addExpressVehicles(){
		ExpressVehiclesEntity expressVehiclesEntity = expressVehiclesVo.getExpressVehiclesEntity();
		expressVehiclesEntity.setAreaCodeList(expressVehiclesVo.getAreaCodeList());
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		MutexElement mutex =null;
		try {
			if(StringUtil.isEmpty(expressVehiclesVo.
					getExpressVehiclesEntity().getVehicleNo())){
				return returnError("新增快递车辆编码为空！");
			}
			//优化加锁—232607，先加锁后判断车牌号重复、开单营业部以减轻数据库压力，抛异常时若已有锁对象则解锁。
			mutex = new MutexElement(String.valueOf(expressVehiclesVo.
					getExpressVehiclesEntity().getVehicleNo()), 
					"EXPRESS_VEHICLE_NO",MutexElementType.
					EXPRESS_VEHICLE_NO);
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				expressVehiclesService.addExpressVehicles(expressVehiclesEntity, createUser, false);
				businessLockService.unlock(mutex);
			} else {
				return returnError("车牌号["+expressVehiclesVo.getExpressVehiclesEntity().getVehicleNo()+"]的车辆信息正在新增中，请稍后。");
			}
		    return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			if(mutex!=null){
				businessLockService.unlock(mutex);				
			}
		    return returnError(e.getMessage());
		}
    }
    /**
     * <p>快递车辆更新</p> 
     * @author 100847-foss-
     * @date 2013-08-16 上午9:47:35
     * @return
     * @see
     */
    @JSON
    public String updateExpressVehicles(){
		ExpressVehiclesEntity expressVehiclesEntity = expressVehiclesVo.getExpressVehiclesEntity();
		expressVehiclesEntity.setAreaCodeList(expressVehiclesVo.getAreaCodeList());
		String modifyUser = getCurrentUser().getEmployee().getEmpCode();
		MutexElement mutex =null;
		try {
			//优化加锁—232607，先加锁后判断开单营业部以减轻数据库压力，抛异常时若已有锁对象则解锁。
			mutex = new MutexElement(
					expressVehiclesVo.getExpressVehiclesEntity().getVehicleNo(), 
					"EXPRESS_VEHICLE_NO",
					MutexElementType.EXPRESS_VEHICLE_NO);
			boolean result = businessLockService.lock(mutex,NumberConstants.ZERO);
			if (result) {
				expressVehiclesService.updateExpressVehicles(expressVehiclesEntity, modifyUser, false);
				businessLockService.unlock(mutex);
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			} else {
				return returnError("车牌号["+expressVehiclesVo.getExpressVehiclesEntity().getVehicleNo()+"]的车辆信息正在修改中，请稍后。");
			}
		} catch (BusinessException e) {
			if(mutex!=null){
				businessLockService.unlock(mutex);				
			}
		    return returnError(e.getMessage());
		}
    }
    
    @JSON
    public String queryExpressVehiclesList(){
		   try {
			   ExpressVehiclesEntity expressVehiclesEntity = getExpressVehiclesVo().getExpressVehiclesEntity();
			    //1获得List
			    List<ExpressVehiclesEntity> expressVehiclesList = expressVehiclesService.queryExpressVehiclesListToView(expressVehiclesEntity, start, limit);    	
			    //2获得总数
			    Long totalCount = expressVehiclesService.recordCountByQueryParamsToView(expressVehiclesEntity);    	
			    // 设置totalCount
			    getExpressVehiclesVo().setExpressVehiclesEntityList(expressVehiclesList);
			    this.setTotalCount(totalCount);
			    return returnSuccess();
		} catch (BusinessException e) {
			
			return returnError(e.getMessage());
		}
    }
    /**
     * <p>获取当前的登录用户</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:34:46
     * @return
     * @see
     */
    private UserEntity getCurrentUser(){
    	UserEntity user = FossUserContext.getCurrentUser();
    	return null == user ? new UserEntity() : user;
    }

	public ExpressVehiclesVo getExpressVehiclesVo() {
		if(null == expressVehiclesVo){
			expressVehiclesVo = new ExpressVehiclesVo();
		}
		return expressVehiclesVo;
	}

	public void setExpressVehiclesVo(ExpressVehiclesVo expressVehiclesVo) {
		this.expressVehiclesVo = expressVehiclesVo;
	}

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
    @JSON
    public String queryExpressVehiclesById(){
	    ExpressVehiclesEntity expressVehiclesEntity = expressVehiclesService.queryExpressVehiclesById(expressVehiclesVo.getExpressVehiclesEntity().getId());
	    expressVehiclesVo.setExpressVehiclesEntity(expressVehiclesEntity);
	    return returnSuccess();
    }
}
