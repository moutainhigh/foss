package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageExpressType;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionExpressVo;

public class RegionExpressAction extends AbstractAction {
/**
 * 序列化
 */
private static final long serialVersionUID = -3292660897095659643L;

/**
 *  注入service
 */
private IRegionExpressService regionExpressService;

/**
 * 
 * <p>设置注入service</p> 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @param regionExpressService
 * @see
 */

public void setRegionExpressService(IRegionExpressService regionExpressService) {
	this.regionExpressService = regionExpressService;
}

/**
 * 区域Vo页面传参对象
 */
private RegionExpressVo regionExpressVo = new RegionExpressVo();

/**
 * 
 * <p>获得区域Vo页面传参对象</p> 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @return
 * @see
 */
public RegionExpressVo getRegionExpressVo() {
	return regionExpressVo;
}

/**
 * 
 * <p>设置区域Vo页面传参对象</p> 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @param regionExpressVo
 * @see
 */
public void setRegionExpressVo(RegionExpressVo regionExpressVo) {
	this.regionExpressVo = regionExpressVo;
}

/**
 * .
 * <p>
 * 查询所有的区域<br/>
 * 方法名：searchAllRegion
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String searchRegionExpressByCondition() {
	try {
		PriceRegionExpressEntity regionEntity = regionExpressVo.getRegionEntity();
		List<PriceRegionExpressEntity> regionEntityList = regionExpressService.searchRegionByCondition(regionEntity, this.getStart(),this.getLimit());
		regionExpressVo.setRegionEntityList(regionEntityList);
		Long totalCount = regionExpressService.countRegionByCondition(regionEntity);
		this.setTotalCount(totalCount);
		return returnSuccess();
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 查询当前区域关联的部门<br/>
 * 方法名：searchRegionDeptExpress
 * </p>
 * 
 * @author 张斌
 * @时间 2012-10-10
 * @since JDK1.6
 */
@JSON
public String searchRegionDeptExpress() {
	try {
		List<PriceRegioOrgnExpressEntity> priceRegioOrgnEntityList = regionExpressService
				.searchRegionOrgByCondition(regionExpressVo
						.getPriceRegioOrgnEntity());
		regionExpressVo.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
		return returnSuccess();
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 激活区域<br/>
 * 方法名：activeRegionsExpress
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String activeRegionsExpress() {
	try {
		Date nowDate= new Date();
	    if(nowDate!= null && regionExpressVo.getBeginTime()!= null && nowDate.compareTo(regionExpressVo.getBeginTime())>0)
	    {
	    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
	    }
		regionExpressService.immedietelyActiveRegion(regionExpressVo.getRegionEntity().getId(), regionExpressVo.getRegionNature(),regionExpressVo.getBeginTime());
		return returnSuccess(MessageExpressType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 删除区域<br/>
 * 方法名：deleteRegionsExpress
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String deleteRegionsExpress() {
	try {
		regionExpressService.deleteRegion(regionExpressVo.getRegionIds(),regionExpressVo.getRegionNature());
		return returnSuccess(MessageExpressType.DELETE_SUCCESS);
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 新增区域<br/>
 * 方法名：addRegionExpress
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String addRegionExpress() {
	try {
		regionExpressService.addRegion(regionExpressVo.getRegionEntity(),regionExpressVo.getAddPriceRegioOrgnEntityList());
		return returnSuccess(MessageExpressType.SAVE_SUCCESS);
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 修改区域<br/>
 * 方法名：updateRegionExpress
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String updateRegionExpress() {
	try {
		regionExpressService.updateRegion(regionExpressVo.getRegionEntity(),regionExpressVo.getAddPriceRegioOrgnEntityList(),regionExpressVo.getUpdatePriceRegioOrgnEntityList());
		return returnSuccess(MessageExpressType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
		return returnError(e);
	}
}

/**
 * .
 * <p>
 * 根据ID查询区域<br/>
 * 方法名：searchRegionExpressByID
 * </p>
 * 
 * @author DP-Foss-xmm
 * @date 2013-7-16 上午11:12:49
 * @since JDK1.6
 */
@JSON
public String searchRegionExpressByID() {
	try {
		PriceRegionExpressEntity priceRegionEntity =regionExpressService.searchRegionByID(regionExpressVo.getRegionEntity().getId(), regionExpressVo.getRegionEntity().getRegionNature());
		regionExpressVo.setRegionEntity(priceRegionEntity);
		return returnSuccess();
	} catch (BusinessException e) {
		return returnError(e);
	}
}
/**
 * 
 * @Description: 立即中止
 * 
 * @author DP-Foss-xmm
 * 
 * @date 2013-7-16 上午11:12:49
 * 
 * @return
 * 
 * @version V1.0
 * 
 */
@JSON
public String immedietelyStopRegionExpress() {
	try {
		//执行中止操作
		regionExpressService.immedietelyStopRegion(regionExpressVo.getRegionEntity().getId(), regionExpressVo.getRegionNature(),regionExpressVo.getEndTime());
		//返回状态值
		return returnSuccess(MessageExpressType.STOP_SUCCESS);
	} catch (BusinessException e) {
		return returnError(e);
	}
}
}