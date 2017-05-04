/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionArriveVo;

/**
 * 到达增值区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有二种，分别为： 时效区域、价格区域、
 * 
 * 其中价格区域分： 汽运价格区域。增值价格区域
 * 
 * @author 赵斌
 * @date 2013-08-12
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
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
public class RegionArriveAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6726844792105378061L;
	/**
	 *  注入增值service
	 */
	private IRegionArriveService regionArriveService;
	/**
	 * sequence
	 */
	private String sequence;
	/**
	 * 增值区域页面VO对象
	 */
	private RegionArriveVo regionArriveVo  = new RegionArriveVo();
	
	public RegionArriveVo getRegionArriveVo() {
		return regionArriveVo;
	}

	public void setRegionArriveVo(RegionArriveVo regionArriveVo) {
		this.regionArriveVo = regionArriveVo;
	}

	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
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
	public String searchRegionArriveByCondition() {
		try {
			PriceRegionArriveEntity regionEntity = regionArriveVo.getRegionEntity();
			List<PriceRegionArriveEntity> regionEntityList = regionArriveService.searchRegionByCondition(regionEntity, this.getStart(), this.getLimit());
			regionArriveVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionArriveService.countRegionByCondition(regionEntity);
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
	public String searchRegionArriveDept() {
		try {
			List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityList = regionArriveService.searchRegionOrgByCondition(regionArriveVo.getPriceRegionOrgArriveEntity());
			regionArriveVo.setPriceRegionOrgArriveEntityList(priceRegionOrgArriveEntityList);
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
	public String activeRegionArrive() {
		try {
			 Date nowDate= new Date();
		    if(nowDate!= null && regionArriveVo.getBeginTime()!= null && nowDate.compareTo(regionArriveVo.getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
			regionArriveService.immedietelyActiveRegionArrive(regionArriveVo.getRegionEntity().getId(),regionArriveVo.getRegionNature(),regionArriveVo.getBeginTime());
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
	public String deleteRegionArrive() {
		try {
			regionArriveService.deleteRegion(regionArriveVo.getRegionIds(),regionArriveVo.getRegionNature());
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
	public String addRegionArrive() {
		try {
			regionArriveService.addRegion(regionArriveVo.getRegionEntity(),regionArriveVo.getAddPriceRegionOrgArriveEntityList());
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
	public String updateRegionArrive() {
		try {
			regionArriveService.updateRegion(regionArriveVo.getRegionEntity(),
					regionArriveVo.getAddPriceRegionOrgArriveEntityList(),
					regionArriveVo.getUpdatePriceRegionOrgArriveEntityList());
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
	public String searchRegionArriveByID() {
		try {
			PriceRegionArriveEntity priceRegionArriveEntity =regionArriveService.searchRegionByID(regionArriveVo.getRegionEntity().getId(), regionArriveVo.getRegionEntity().getRegionNature());
			regionArriveVo.setRegionEntity(priceRegionArriveEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 立即中止增值价格区域
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
	public String immedietelyStopRegionArrive() {
		try {
			//执行中止操作
			regionArriveService.immedietelyStopRegionArrive(regionArriveVo.getRegionEntity().getId(),regionArriveVo.getRegionNature(),regionArriveVo.getEndTime());
			//返回状态值
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 获得增值区域编号
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-15 上午8:27:27
	 */
	@JSON
	public String querySequence(){
		sequence = regionArriveService.querySequence();
		//返回成功
		return returnSuccess();
	}
}