/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionValueAddVo;

/**
 * 增值区域管理ACTION
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
 * @Description: 增值区域管理ACTION
 * 
 * 区域是一个逻辑区域的概念类似于分组
 * 
 * FOSS价格模型区域一共有三种，分别为： 时效区域、价格区域、增值区域
 * 
 * 其中价格区域分： 汽运价格区域。增值价格区域
 * 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
public class RegionValueAddAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6726844792105378061L;
	
	/**
	 * sequence
	 */
	private String sequence;
	
	/**
	 *  注入增值service
	 */
	private IRegionValueAddService regionValueAddService;
	/**
	 * 
	 * <p>设置增值service</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午11:17:21
	 * 
	 * @param regionValueAddService
	 * 
	 * @see
	 */

	/**
	 * 增值区域页面VO对象
	 */
	private RegionValueAddVo regionValueAddVo  = new RegionValueAddVo();
	public void setRegionValueAddService(
			IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}

	public RegionValueAddVo getRegionValueAddVo() {
		return regionValueAddVo;
	}

	public void setRegionValueAddVo(RegionValueAddVo regionValueAddVo) {
		this.regionValueAddVo = regionValueAddVo;
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
	public String searchRegionValueAddByCondition() {
		try {
			PriceRegionValueAddEntity regionEntity = regionValueAddVo.getRegionEntity();
			List<PriceRegionValueAddEntity> regionEntityList = regionValueAddService.searchRegionByCondition(regionEntity, this.getStart(), this.getLimit());
			regionValueAddVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionValueAddService.countRegionByCondition(regionEntity);
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
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-20 下午3:54:13
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String searchRegionValueAddDept() {
		try {
			List<PriceRegionOrgValueAddEntity> priceRegionOrgValueAddEntityList = regionValueAddService.searchRegionOrgByCondition(regionValueAddVo.getPriceRegionOrgValueAddEntityEntity());
			regionValueAddVo.setPriceRegionOrgValueAddEntityList(priceRegionOrgValueAddEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 激活区域<br/>
	 * 方法名：activeRegions
	 * </p>
	 * 
	 * @author 043258-foss-zhaobin
	 * @时间 2012-10-10
	 * @since JDK1.6
	 */
	@JSON
	public String activeRegionValueAdd() {
		try {
			 Date nowDate= new Date();
		    if(nowDate!= null && regionValueAddVo.getBeginTime()!= null && nowDate.compareTo(regionValueAddVo.getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
			regionValueAddService.immedietelyActiveRegionValueAdd(regionValueAddVo.getRegionEntity().getId(),regionValueAddVo.getRegionNature(),regionValueAddVo.getBeginTime());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegions
	 * </p>
	 * 
	 * @author 043258-foss-zhaobin
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	@JSON
	public String deleteRegionValueAdd() {
		try {
			regionValueAddService.deleteRegion(regionValueAddVo.getRegionIds(),regionValueAddVo.getRegionNature());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * 新增区域<br/>
	 * 方法名：addRegion
	 * </p>
	 * 
	 * @author 043258-foss-zhaobin
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String addRegionValueAdd() {
		try {																	 
			regionValueAddService.addRegion(regionValueAddVo.getRegionEntity(), 
					regionValueAddVo.getAddPriceRegionOrgValueAddEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * 修改区域<br/>
	 * 方法名：updateRegion
	 * </p>
	 * 
	 * @author 043258-foss-zhaobin
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String updateRegionValueAdd() {
		try {
			regionValueAddService.updateRegion(regionValueAddVo.getRegionEntity(),
					regionValueAddVo.getAddPriceRegionOrgValueAddEntityList(),
					regionValueAddVo.getUpdatePriceRegionOrgValueAddEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 根据ID查询区域<br/>
	 * 方法名：searchRegionByID
	 * </p>
	 * 
	 * @author 043258-foss-zhaobin
	 * @时间 2012-12-07
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionValueAddByID() {
		try {
			PriceRegionValueAddEntity priceRegionValueAddEntity =regionValueAddService.searchRegionByID(regionValueAddVo.getRegionEntity().getId(), regionValueAddVo.getRegionEntity().getRegionNature());
			regionValueAddVo.setRegionEntity(priceRegionValueAddEntity);
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
	public String immedietelyStopRegionValueAdd() {
		try {
			//执行中止操作
			regionValueAddService.immedietelyStopRegionValueAdd(regionValueAddVo.getRegionEntity().getId(),regionValueAddVo.getRegionNature(),regionValueAddVo.getEndTime());
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
		sequence = regionValueAddService.querySequence();
		//返回成功
		return returnSuccess();
	}
}