package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;


/**
 * 晚到补差价
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-28 下午7:31:50,content:TODO </p>
 * @author 232607 
 * @date 2015-7-28 下午7:31:50
 * @since
 * @version
 */
public class LateCouponVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4230452754031715805L;
	
	/**
	 * 用于查询参数的传入
	 */
	private LateCouponEntity lateCouponEntity;
	/**
	 * 用于查询结果的返回
	 */
	private List<LateCouponEntity> lateCouponEntitys;
	/**
	 * 用于删除、激活、中止
	 */
    private List<String> lateCouponIds;
	/**
	 * 当前服务器时间，用于前台判断
	 */
	private Date nowTime;
	
	
	public List<LateCouponEntity> getLateCouponEntitys() {
		return lateCouponEntitys;
	}

	public void setLateCouponEntitys(List<LateCouponEntity> lateCouponEntitys) {
		this.lateCouponEntitys = lateCouponEntitys;
	}

	public List<String> getLateCouponIds() {
		return lateCouponIds;
	}

	public void setLateCouponIds(List<String> lateCouponIds) {
		this.lateCouponIds = lateCouponIds;
	}

	public LateCouponEntity getLateCouponEntity() {
		return lateCouponEntity;
	}

	public void setLateCouponEntity(LateCouponEntity lateCouponEntity) {
		this.lateCouponEntity = lateCouponEntity;
	}
	/**
	 * @return  the nowTime
	 */
	public Date getNowTime() {
		return nowTime;
	}

	/**
	 * @param nowTime the nowTime to set
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

}