/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionAirVo;

/**
 * 空运区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有二种，分别为： 时效区域、价格区域、
 * 
 * 其中价格区域分： 汽运价格区域。空运价格区域
 * 
 * @author 张斌
 * @date 2012-10-9
 * @version 1.0
 */
/**
 * 
 * @Description: 空运区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有二种，分别为： 时效区域、价格区域、
 * 
 * 其中价格区域分： 汽运价格区域。空运价格区域
 * 
 * RegionAirAction.java Create on 2013-3-20 下午3:52:10
 * 
 * Company:IBM
 * 
 * @author FOSSDP-sz
 * 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
public class RegionAirAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6726844792105378061L;
	/**
	 *  注入空运service
	 */
	private IRegionAirService regionAirService;
	/**
	 * 
	 * <p>设置空运service</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午11:17:21
	 * 
	 * @param regionAirService
	 * 
	 * @see
	 */
	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}
	/**
	 * 空运区域页面VO对象
	 */
	private RegionAirVo regionAirVo  = new RegionAirVo();
	/**
	 * 
	 * <p>获得空运区域页面VO对象</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午11:18:00
	 * 
	 * @return
	 * 
	 * @see
	 */
	public RegionAirVo getRegionAirVo() {
		return regionAirVo;
	}
	/**
	 * 
	 * <p>设置空运区域页面VO对象</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午11:18:21
	 * 
	 * @param regionAirVo
	 * 
	 * @see
	 */
	public void setRegionAirVo(RegionAirVo regionAirVo) {
		this.regionAirVo = regionAirVo;
	}
	/**
	 * 
	 * @Description: 查询所有的区域
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-20 下午3:53:32
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String searchRegionAirByCondition() {
		try {
			PriceRegionAirEntity regionEntity = regionAirVo.getRegionEntity();
			List<PriceRegionAirEntity> regionEntityList = regionAirService.searchRegionByCondition(regionEntity, this.getStart(), this.getLimit());
			regionAirVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionAirService.countRegionByCondition(regionEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 查询当前区域关联的部门
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-20 下午3:54:13
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String searchRegionAirDept() {
		try {
			List<PriceRegionOrgAirEntity> priceRegionOrgAirEntityList = regionAirService.searchRegionOrgByCondition(regionAirVo.getPriceRegionOrgAirEntity());
			regionAirVo.setPriceRegionOrgAirEntityList(priceRegionOrgAirEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 激活区域<br/>
	 * 方法名：activeRegions
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-10
	 * @since JDK1.6
	 */
	@JSON
	public String activeRegionAir() {
		try {
			 Date nowDate= new Date();
			    if(nowDate!= null && regionAirVo.getBeginTime()!= null && nowDate.compareTo(regionAirVo.getBeginTime())>0)
			    {
			    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			    }
			regionAirService.immedietelyActiveRegionAir(regionAirVo.getRegionEntity().getId(),regionAirVo.getRegionNature(),regionAirVo.getBeginTime());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegions
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	@JSON
	public String deleteRegionAir() {
		try {
			regionAirService.deleteRegion(regionAirVo.getRegionIds(),regionAirVo.getRegionNature());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 新增区域<br/>
	 * 方法名：addRegion
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String addRegionAir() {
		try {
			regionAirService.addRegion(regionAirVo.getRegionEntity(),regionAirVo.getAddPriceRegionOrgAirEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 修改区域<br/>
	 * 方法名：updateRegion
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String updateRegionAir() {
		try {
			regionAirService.updateRegion(regionAirVo.getRegionEntity(),
					regionAirVo.getAddPriceRegionOrgAirEntityList(),
					regionAirVo.getUpdatePriceRegionOrgAirEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 根据ID查询区域<br/>
	 * 方法名：searchRegionByID
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-07
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionAirByID() {
		try {
			PriceRegionAirEntity priceRegionAirEntity =regionAirService.searchRegionByID(regionAirVo.getRegionEntity().getId(), regionAirVo.getRegionEntity().getRegionNature());
			regionAirVo.setRegionEntity(priceRegionAirEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 立即中止空运价格区域
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-20 下午3:55:23
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String immedietelyStopRegionAir() {
		try {
			//执行中止操作
			regionAirService.immedietelyStopRegionAir(regionAirVo.getRegionEntity().getId(),regionAirVo.getRegionNature(),regionAirVo.getEndTime());
			//返回状态值
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}