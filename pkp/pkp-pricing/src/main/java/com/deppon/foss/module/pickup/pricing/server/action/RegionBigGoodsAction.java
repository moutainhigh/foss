package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionBigGoodsVo;

public class RegionBigGoodsAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  注入service
	 */
	private IRegionBigGoodsService regionBigGoodsService;
	
	/**
	 * 区域Vo页面传参对象
	 */
	private RegionBigGoodsVo regionBgVo = new RegionBigGoodsVo();
	
	/**
	 * 
	 * searchRegionByCondition:查询所有的区域. <br/>
	 * 
	 * Date:2014-6-13下午3:50:54
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String searchRegionBgByCondition() {
		try {
			PriceRegionBigGoodsEntity regionEntity = regionBgVo.getRegionEntity();
			List<PriceRegionBigGoodsEntity> regionEntityList = regionBigGoodsService.searchRegionByCondition(regionEntity, this.getStart(),this.getLimit());
			regionBgVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionBigGoodsService.countRegionByCondition(regionEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * activeRegions:激活区域. <br/>
	 * 
	 * Date:2014-6-13下午3:51:12
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String activeRegionsBg() {
		try {			   	
		    Date dd = new Date();
		    if(dd!= null && regionBgVo.getBeginTime()!= null && dd.compareTo(regionBgVo.getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
			regionBigGoodsService.immedietelyActiveRegion(regionBgVo.getRegionEntity().getId(), regionBgVo.getRegionNature(),regionBgVo.getBeginTime());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * deleteRegions:删除区域. <br/>
	 * 
	 * Date:2014-6-13下午3:51:59
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String deleteRegionsBg() {
		try {
			regionBigGoodsService.deleteRegion(regionBgVo.getRegionIds(),regionBgVo.getRegionNature());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * addRegion:新增区域. <br/>
	 * 
	 * Date:2014-6-13下午3:52:20
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String addRegionBg() {
		try {
			regionBigGoodsService.addRegion(regionBgVo.getRegionEntity(),regionBgVo.getAddPriceRegioOrgnEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * updateRegion:修改区域. <br/>
	 * 
	 * Date:2014-6-13下午3:53:02
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String updateRegionBg() {
		try {
			regionBigGoodsService.updateRegion(regionBgVo.getRegionEntity(),regionBgVo.getAddPriceRegioOrgnEntityList(),regionBgVo.getUpdatePriceRegioOrgnEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * immedietelyStopRegion:立即终止. <br/>
	 * 
	 * Date:2014-6-13下午3:53:52
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String immedietelyStopRegionBg() {
		try {
			//执行中止操作
			regionBigGoodsService.immedietelyStopRegion(regionBgVo.getRegionEntity().getId(), regionBgVo.getRegionNature(),regionBgVo.getEndTime());
			//返回状态值
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * searchRegionByID:查询区域. <br/>
	 * 
	 * Date:2014-6-13下午3:54:46
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	@JSON
	public String searchRegionBgByID() {
		try {
			PriceRegionBigGoodsEntity priceRegionBigGoodsEntity =regionBigGoodsService.searchRegionByID(regionBgVo.getRegionEntity().getId(), regionBgVo.getRegionEntity().getRegionNature());
			regionBgVo.setRegionEntity(priceRegionBigGoodsEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@JSON
	public String searchRegionBgDept() {
		try {
			List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntityList = regionBigGoodsService
					.searchRegionOrgByCondition(regionBgVo
							.getPriceRegioOrgnEntity());
			regionBgVo.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public RegionBigGoodsVo getRegionBgVo() {
		return regionBgVo;
	}

	public void setRegionBgVo(RegionBigGoodsVo regionBgVo) {
		this.regionBgVo = regionBgVo;
	}

	public void setRegionBigGoodsService(
			IRegionBigGoodsService regionBigGoodsService) {
		this.regionBigGoodsService = regionBigGoodsService;
	}
}
