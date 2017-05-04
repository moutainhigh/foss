/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionEcGoodsArriveVo;

import java.util.Date;
import java.util.List;

/**
 * 到达增值区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有二种，分别为： 时效区域、价格区域、
 * 
 * 其中价格区域分： 汽运价格区域。增值价格区域
 * 
 * @author yangkang
 * @date 2014-06-26
 * @version 1.0
 */

/**
 * 
 * @Description: 到达区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有三种，分别为： 时效区域、价格区域、增值区域、到达区域
 * 
 * 其中价格区域分： 汽运价格区域。增值价格区域
 * 
 * Copyright (c) 2014 . All Rights Reserved
 * 
 * @version V1.0
 */
public class RegionEcGoodsArriveAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6726844792105378061L;
	/**
	 *  注入增值service
	 */
	private IRegionEcGoodsArriveService regionEcGoodsArriveService;
	/**
	 * sequence
	 */
	private String sequence;
	/**
	 * 增值区域页面VO对象
	 */
	private RegionEcGoodsArriveVo regionEcGoodsArriveVo  = new RegionEcGoodsArriveVo();
	



	public RegionEcGoodsArriveVo getRegionEcGoodsArriveVo() {
		return regionEcGoodsArriveVo;
	}

	public void setRegionEcGoodsArriveVo(
			RegionEcGoodsArriveVo regionEcGoodsArriveVo) {
		this.regionEcGoodsArriveVo = regionEcGoodsArriveVo;
	}

	public void setRegionEcGoodsArriveService(
			IRegionEcGoodsArriveService regionEcGoodsArriveService) {
		this.regionEcGoodsArriveService = regionEcGoodsArriveService;
	}

	public void setregionEcGoodsArriveService(IRegionEcGoodsArriveService regionEcGoodsArriveService) {
		this.regionEcGoodsArriveService = regionEcGoodsArriveService;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * 
	 * @Description: 查询所有的区域
	 * 
	 * @author yangkang
	 * 
	 * @date 2014-6-26 下午3:53:32
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String searchRegionArriveByCondition() {
		try {
			PriceRegionEcGoodsArriveEntity regionEntity = regionEcGoodsArriveVo.getRegionEntity();
			List<PriceRegionEcGoodsArriveEntity> regionEntityList = regionEcGoodsArriveService.searchRegionByCondition(regionEntity, this.getStart(), this.getLimit());
			regionEcGoodsArriveVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionEcGoodsArriveService.countRegionByCondition(regionEntity);
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
	 * @author yangkang
	 * 
	 * @date 2014-6-26 下午3:54:13
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String searchRegionArriveDept() {
		try {
			List<PriceRegionEcGoodsOrgArriveEntity> priceRegionOrgArriveEntityList = regionEcGoodsArriveService.searchRegionOrgByCondition(regionEcGoodsArriveVo.getPriceRegionOrgArriveEntity());
			regionEcGoodsArriveVo.setPriceRegionOrgArriveEntityList(priceRegionOrgArriveEntityList);
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
	 * @author yangkang
	 * @时间 2014-6-25
	 * @since JDK1.6
	 */
	@JSON
	public String activeRegionArrive() {
		try {
			 Date nowDate= new Date();
		    if(nowDate!= null && regionEcGoodsArriveVo.getBeginTime()!= null && nowDate.compareTo(regionEcGoodsArriveVo.getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
			regionEcGoodsArriveService.immedietelyActiveRegionArrive(regionEcGoodsArriveVo.getRegionEntity().getId(),regionEcGoodsArriveVo.getRegionNature(),regionEcGoodsArriveVo.getBeginTime());
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
	 * @author yangkang
	 * @时间 2014-6-25
	 * @since JDK1.6
	 */
	@JSON
	public String deleteRegionArrive() {
		try {
			regionEcGoodsArriveService.deleteRegion(regionEcGoodsArriveVo.getRegionIds(),regionEcGoodsArriveVo.getRegionNature());
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
	 * @author yangkang
	 * @时间 2014-6-26
	 * @since JDK1.6
	 */
	@JSON
	public String addRegionArrive() {
		try {
			regionEcGoodsArriveService.addRegion(regionEcGoodsArriveVo.getRegionEntity(),regionEcGoodsArriveVo.getAddPriceRegionOrgArriveEntityList());
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
	 * @author yangkang
	 * @时间 2014-6-25
	 * @since JDK1.6
	 */
	@JSON
	public String updateRegionArrive() {
		try {
			regionEcGoodsArriveService.updateRegion(regionEcGoodsArriveVo.getRegionEntity(),
					regionEcGoodsArriveVo.getAddPriceRegionOrgArriveEntityList(),
					regionEcGoodsArriveVo.getUpdatePriceRegionOrgArriveEntityList());
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
	 * @author yangkang
	 * @时间 2014-6-26
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionArriveByID() {
		try {
			PriceRegionEcGoodsArriveEntity priceRegionEcGoodsArriveEntity =regionEcGoodsArriveService.searchRegionByID(regionEcGoodsArriveVo.getRegionEntity().getId(), regionEcGoodsArriveVo.getRegionEntity().getRegionNature());
			regionEcGoodsArriveVo.setRegionEntity(priceRegionEcGoodsArriveEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 立即中止增值价格区域
	 * 
	 * @author yangkang
	 * 
	 * @date 2014-6-26 下午3:55:23
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String immedietelyStopRegionArrive() {
		try {
			//执行中止操作
			regionEcGoodsArriveService.immedietelyStopRegionArrive(regionEcGoodsArriveVo.getRegionEntity().getId(),regionEcGoodsArriveVo.getRegionNature(),regionEcGoodsArriveVo.getEndTime());
			//返回状态值
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 获得增值区域编号
	 * @author 130376-foss-yangkang
	 * @date 2014-6-26 上午8:27:27
	 */
	@JSON
	public String querySequence(){
		sequence = regionEcGoodsArriveService.querySequence();
		//返回成功
		return returnSuccess();
	}
}