/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirSpaceDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceEntity;
/**
 * 舱位信息dto对象，用于查询返回或者查询参数
 * @author 038300-foss-pengzhen
 * @date 2012-10-18 上午9:37:16
 */
public class AirSpaceDto extends AirSpaceEntity{
	
	private static final long serialVersionUID = 87276437634637199L;
	/**
	 * 航班类型
	 */
    private String flightType;   
    /**
     * 总舱位
     */
    private BigDecimal spaceTotal;
    /**
     * 剩余舱位
     */
    private BigDecimal leftSpace;  
    /**
     * 营业部已订舱位
     */
    private BigDecimal bookingSpaceTotal;
    /**
     * 总调已受理订舱总量
     */
    private BigDecimal acceptedSpaceTotal;
    /**
     * 营业部已开单空运货量
     */
    private BigDecimal airWaybillTotal;
    /**
     * 出发日期开始
     */
    private Date takeOffDateFrom; 
    /**
     * 出发日期结束
     */
    private Date takeOffDateTo; 
    
	/**
	 * 早班预定舱位
	 * @author zwd 200968 2015-05-07
	 * 即日达预定舱位
	 */
	private BigDecimal morningShift; 
	/**
	 * 中班预定舱位
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 */
	private BigDecimal middleShift; 
	/**
	 * 晚班预定舱位
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 */
	private BigDecimal nightShift; 
	/**
	 * 中转预定舱位
	 * @author zwd 200968 2015-05-07
	 * 航空普运预定舱位
	 */
	private BigDecimal transitShift; 
 
    /**
     * 舱位明细
     */
    private List<AirSpaceDetailEntity> detailEntityList;
    /**
     * 受理状态
     */
    private String acceptStatus;
    /**
     * 活动状态Y
     */
    @SuppressWarnings("unused")
    private static final String active = "Y";
    
	/**
	 * 获取 航班类型.
	 *
	 * @return the 航班类型
	 */
	public String getFlightType() {
		return flightType;
	}

	/**
	 * 设置 航班类型.
	 *
	 * @param flightType the new 航班类型
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	/**
	 * 获取 总舱位.
	 *
	 * @return the 总舱位
	 */
	public BigDecimal getSpaceTotal() {
		return spaceTotal;
	}

	/**
	 * 设置 总舱位.
	 *
	 * @param spaceTotal the new 总舱位
	 */
	public void setSpaceTotal(BigDecimal spaceTotal) {
		this.spaceTotal = spaceTotal;
	}

	/**
	 * 获取 剩余舱位.
	 *
	 * @return the 剩余舱位
	 */
	public BigDecimal getLeftSpace() {
		return leftSpace;
	}

	/**
	 * 设置 剩余舱位.
	 *
	 * @param leftSpace the new 剩余舱位
	 */
	public void setLeftSpace(BigDecimal leftSpace) {
		this.leftSpace = leftSpace;
	}

	/**
	 * 获取 营业部已订舱位.
	 *
	 * @return the 营业部已订舱位
	 */
	public BigDecimal getBookingSpaceTotal() {
		return bookingSpaceTotal;
	}

	/**
	 * 设置 营业部已订舱位.
	 *
	 * @param bookingSpaceTotal the new 营业部已订舱位
	 */
	public void setBookingSpaceTotal(BigDecimal bookingSpaceTotal) {
		this.bookingSpaceTotal = bookingSpaceTotal;
	}

	/**
	 * 获取 总调已受理订舱总量.
	 *
	 * @return the 总调已受理订舱总量
	 */
	public BigDecimal getAcceptedSpaceTotal() {
		return acceptedSpaceTotal;
	}

	/**
	 * 设置 总调已受理订舱总量.
	 *
	 * @param acceptedSpaceTotal the new 总调已受理订舱总量
	 */
	public void setAcceptedSpaceTotal(BigDecimal acceptedSpaceTotal) {
		this.acceptedSpaceTotal = acceptedSpaceTotal;
	}

	/**
	 * 获取 营业部已开单空运货量.
	 *
	 * @return the 营业部已开单空运货量
	 */
	public BigDecimal getAirWaybillTotal() {
		return airWaybillTotal;
	}

	/**
	 * 设置 营业部已开单空运货量.
	 *
	 * @param airWaybillTotal the new 营业部已开单空运货量
	 */
	public void setAirWaybillTotal(BigDecimal airWaybillTotal) {
		this.airWaybillTotal = airWaybillTotal;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 出发日期开始.
	 *
	 * @return the 出发日期开始
	 */
	public Date getTakeOffDateFrom() {
		return takeOffDateFrom;
	}

	/**
	 * 设置 出发日期开始.
	 *
	 * @param takeOffDateFrom the new 出发日期开始
	 */
	public void setTakeOffDateFrom(Date takeOffDateFrom) {
		this.takeOffDateFrom = takeOffDateFrom;
	}

	/**
	 * 获取 出发日期结束.
	 *
	 * @return the 出发日期结束
	 */
	public Date getTakeOffDateTo() {
		return takeOffDateTo;
	}

	/**
	 * 设置 出发日期结束.
	 *
	 * @param takeOffDateTo the new 出发日期结束
	 */
	public void setTakeOffDateTo(Date takeOffDateTo) {
		this.takeOffDateTo = takeOffDateTo;
	}

	/**
	 * 获取 舱位明细.
	 *
	 * @return the 舱位明细
	 */
	public List<AirSpaceDetailEntity> getDetailEntityList() {
		return detailEntityList;
	}

	/**
	 * 设置 舱位明细.
	 *
	 * @param detailEntityList the new 舱位明细
	 */
	public void setDetailEntityList(List<AirSpaceDetailEntity> detailEntityList) {
		this.detailEntityList = detailEntityList;
	}
	
/*	*//**
	 * 获取 早班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 即日达预定舱位
	 * @return the 早班预定舱位
	 */
	public BigDecimal getMorningShift() {
		return morningShift;
	}

	/**
	 * 设置 早班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 即日达预定舱位
	 * @param morningShift the new 早班预定舱位
	 */
	public void setMorningShift(BigDecimal morningShift) {
		this.morningShift = morningShift;
	}

	/**
	 * 获取 中班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 * @return the 中班预定舱位
	 */
	public BigDecimal getMiddleShift() {
		return middleShift;
	}

	/**
	 * 设置 中班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 * @param middleShift the new 中班预定舱位
	 */
	public void setMiddleShift(BigDecimal middleShift) {
		this.middleShift = middleShift;
	}

	/**
	 * 获取 晚班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 * @return the 晚班预定舱位
	 */
	public BigDecimal getNightShift() {
		return nightShift;
	}

	/**
	 * 设置 晚班预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 次日达预定舱位
	 * @param nightShift the new 晚班预定舱位
	 */
	public void setNightShift(BigDecimal nightShift) {
		this.nightShift = nightShift;
	}
	
	/**
	 * 获取 中转预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 航空普运预定舱位
	 * @return the 中转预定舱位
	 */
	public BigDecimal getTransitShift() {
		return transitShift;
	}
	
	/**
	 * 设置 中转预定舱位.
	 * @author zwd 200968 2015-05-07
	 * 航空普运预定舱位
	 * @param transitShift the new 中转预定舱位
	 */
	public void setTransitShift(BigDecimal transitShift) {
		this.transitShift = transitShift;
	}

	/**
	 * 获取 受理状态.
	 *
	 * @return the 受理状态
	 */
	public String getAcceptStatus() {
		return acceptStatus;
	}


	/**
	 * 设置 受理状态.
	 *
	 * @param acceptStatus the new 受理状态
	 */
	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}
    
}