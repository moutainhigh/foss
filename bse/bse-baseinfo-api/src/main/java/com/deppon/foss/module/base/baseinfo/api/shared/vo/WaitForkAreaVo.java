package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区vo
 * @Time 2014-4-25
 */
public class WaitForkAreaVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059508599145391589L;
	/**
	 * 待叉区实体
	 */
	private WaitForkAreaEntity waitForkAreaEntity;
	/**
	 * 待叉区list
	 */
	private List<WaitForkAreaEntity> waitForkAreaEntityList;
	
	/**
	 * 虚拟编码集合
	 */
	private List<String> waitForkAreaVirtualCodes;
	
	/**
	 * 货区List
	 */
	private List<GoodsAreaEntity> goodsAreaEntityList;
	
	
	
	public List<String> getWaitForkAreaVirtualCodes() {
		return waitForkAreaVirtualCodes;
	}
	public void setWaitForkAreaVirtualCodes(List<String> waitForkAreaVirtualCodes) {
		this.waitForkAreaVirtualCodes = waitForkAreaVirtualCodes;
	}
	public List<PlatformEntity> getPlatformEntityList() {
		return platformEntityList;
	}
	public void setPlatformEntityList(List<PlatformEntity> platformEntityList) {
		this.platformEntityList = platformEntityList;
	}
	private List<PlatformEntity> platformEntityList;
	public List<GoodsAreaEntity> getGoodsAreaEntityList() {
		return goodsAreaEntityList;
	}
	public void setGoodsAreaEntityList(List<GoodsAreaEntity> goodsAreaEntityList) {
		this.goodsAreaEntityList = goodsAreaEntityList;
	}
	public List<WaitForkAreaEntity> getWaitForkAreaEntityList() {
		return waitForkAreaEntityList;
	}
	public void setWaitForkAreaEntityList(
			List<WaitForkAreaEntity> waitForkAreaEntityList) {
		this.waitForkAreaEntityList = waitForkAreaEntityList;
	}
	public WaitForkAreaEntity getWaitForkAreaEntity() {
		return waitForkAreaEntity;
	}
	public void setWaitForkAreaEntity(WaitForkAreaEntity waitForkAreaEntity) {
		this.waitForkAreaEntity = waitForkAreaEntity;
	}
}
