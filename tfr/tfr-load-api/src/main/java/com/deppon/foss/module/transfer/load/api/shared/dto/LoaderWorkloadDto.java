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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoaderWorkloadDto.java
 *  
 *  FILE NAME          :LoaderWorkloadDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.util.DateUtils;

/**
 * 装卸车工作量Dto
 * @author ibm-zhangyixin
 * @date 2012-12-12 下午3:54:02
 */
public class LoaderWorkloadDto extends LoaderWorkloadEntity implements Serializable {
	private static final long serialVersionUID = 8063277676631847412L;

	/**任务开始时间, 如果操作类型为装车则为装车任务开始时间, 如果操作类型为卸车则为卸车任务开始时间**/
	private Date taskBeginTime;	

	/**任务结束时间, 同上.**/
	private Date taskEndTime;	

	/**日期.**/
	private String joinDate;
	
	
	/**操作类型.**/
	private String handleType;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**出勤人数.**/
	private Integer taskPersonCount;	

	/**装车重量.**/
	private BigDecimal loadWeight;		

	/**卸车重量.**/
	private BigDecimal unloadWeight;	

	/**总重量.**/
	private BigDecimal totWeight;	

	/**装车票数.**/
	private Integer loadWaybillQty;		

	/**卸车票数.**/
	private Integer unloadWaybillQty;	

	/**总票数.**/
	private Integer totWaybillQty;	

	/**装车件数.**/
	private Integer loadGoodsQty;		

	/**卸车件数.**/
	private Integer unloadGoodsQty;		

	/**总件数.**/
	private Integer totGoodsQty;		
	
	/**开始日期.**/
	private String beginDate;			

	/**结束日期.**/
	private String endDate;
	
	/**总重量-临时变量**/
	private BigDecimal totWeightTemp;

	/**总票数-临时变量**/
	private Integer totWaybillQtyTemp;
	
	/**当前部门(LoaderOrgCode)下所有子部门**/
	private List<String> loaderOrgCodes;
	
	/**
     * 设置 加入任务时间*.
     *
     * @param joinTime the new 加入任务时间*
     */
    public void setJoinTime(String joinTime) {
    	super.setJoinTime(DateUtils.convert(joinTime));
    }
    
    /**
     * 设置 离开任务时间*.
     *
     * @param leaveTime the new 离开任务时间*
     */
    public void setLeaveTime(String leaveTime) {
    	super.setJoinTime(DateUtils.convert(leaveTime));
    }
    
	/**
	 * 获取 日期.
	 *
	 * @return the 日期
	 */
	public String getJoinDate() {
		return joinDate;
	}
	/**
	 * 获取 操作类.
	 *
	 * @return the 日期
	 */
	
	public String getHandleType() {
		return handleType;
	}
	
	/**
	 * 设置 操作类.
	 *
	 * @return the 类型
	 */
	

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	
	/**
	 * 设置 日期.
	 *
	 * @param joinDate the new 日期
	 */
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	/**
	 * 获取 出勤人数.
	 *
	 * @return the 出勤人数
	 */
	public Integer getTaskPersonCount() {
		return taskPersonCount;
	}
	
	/**
	 * 设置 出勤人数.
	 *
	 * @param taskPersonCount the new 出勤人数
	 */
	public void setTaskPersonCount(Integer taskPersonCount) {
		this.taskPersonCount = taskPersonCount;
	}
	
	/**
	 * 获取 装车重量.
	 *
	 * @return the 装车重量
	 */
	public BigDecimal getLoadWeight() {
		return loadWeight;
	}
	
	/**
	 * 设置 装车重量.
	 *
	 * @param loadWeight the new 装车重量
	 */
	public void setLoadWeight(BigDecimal loadWeight) {
		this.loadWeight = loadWeight;
	}
	
	/**
	 * 获取 卸车重量.
	 *
	 * @return the 卸车重量
	 */
	public BigDecimal getUnloadWeight() {
		return unloadWeight;
	}
	
	/**
	 * 设置 卸车重量.
	 *
	 * @param unloadWeight the new 卸车重量
	 */
	public void setUnloadWeight(BigDecimal unloadWeight) {
		this.unloadWeight = unloadWeight;
	}
	
	/**
	 * 获取 装车票数.
	 *
	 * @return the 装车票数
	 */
	public Integer getLoadWaybillQty() {
		return loadWaybillQty;
	}
	
	/**
	 * 设置 装车票数.
	 *
	 * @param loadWaybillQty the new 装车票数
	 */
	public void setLoadWaybillQty(Integer loadWaybillQty) {
		this.loadWaybillQty = loadWaybillQty;
	}
	
	/**
	 * 获取 卸车票数.
	 *
	 * @return the 卸车票数
	 */
	public Integer getUnloadWaybillQty() {
		return unloadWaybillQty;
	}
	
	/**
	 * 设置 卸车票数.
	 *
	 * @param unloadWaybillQty the new 卸车票数
	 */
	public void setUnloadWaybillQty(Integer unloadWaybillQty) {
		this.unloadWaybillQty = unloadWaybillQty;
	}
	
	/**
	 * 获取 装车件数.
	 *
	 * @return the 装车件数
	 */
	public Integer getLoadGoodsQty() {
		return loadGoodsQty;
	}
	
	/**
	 * 设置 装车件数.
	 *
	 * @param loadGoodsQty the new 装车件数
	 */
	public void setLoadGoodsQty(Integer loadGoodsQty) {
		this.loadGoodsQty = loadGoodsQty;
	}
	
	/**
	 * 获取 卸车件数.
	 *
	 * @return the 卸车件数
	 */
	public Integer getUnloadGoodsQty() {
		return unloadGoodsQty;
	}
	
	/**
	 * 设置 卸车件数.
	 *
	 * @param unloadGoodsQty the new 卸车件数
	 */
	public void setUnloadGoodsQty(Integer unloadGoodsQty) {
		this.unloadGoodsQty = unloadGoodsQty;
	}
	
	/**
	 * 获取 任务开始时间, 如果操作类型为装车则为装车任务开始时间, 如果操作类型为卸车则为卸车任务开始时间*.
	 *
	 * @return the 任务开始时间, 如果操作类型为装车则为装车任务开始时间, 如果操作类型为卸车则为卸车任务开始时间*
	 */
	public Date getTaskBeginTime() {
		return taskBeginTime;
	}
	
	/**
	 * 设置 任务开始时间, 如果操作类型为装车则为装车任务开始时间, 如果操作类型为卸车则为卸车任务开始时间*.
	 *
	 * @param taskBeginTime the new 任务开始时间, 如果操作类型为装车则为装车任务开始时间, 如果操作类型为卸车则为卸车任务开始时间*
	 */
	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}
	
	/**
	 * 获取 任务结束时间, 同上.
	 *
	 * @return the 任务结束时间, 同上
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	
	/**
	 * 设置 任务结束时间, 同上.
	 *
	 * @param taskEndTime the new 任务结束时间, 同上
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	
	/**
	 * 获取 开始日期.
	 *
	 * @return the 开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 开始日期.
	 *
	 * @param beginDate the new 开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束日期.
	 *
	 * @return the 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 结束日期.
	 *
	 * @param endDate the new 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 总重量
	 * 	为手动计算出来
	 * 	totWeight = loadWeight + unloadWeight;
	 * @author ibm-zhangyixin
	 * @date 2013-1-11 下午5:45:39
	 */
	public BigDecimal getTotWeight() {
		if(StringUtils.isEmpty(this.getGoodsType()) || StringUtils.equals(this.getGoodsType(), TransferPDADictConstants.LOAD_GOODS_TYPE_ALL)) {
			return BigDecimal.ZERO;
		}
		if(this.getLoadWeight() == null) {
			this.setLoadWeight(BigDecimal.ZERO);
		}
		if(this.getUnloadWeight() == null) {
			this.setUnloadWeight(BigDecimal.ZERO);
		}
		totWeight = getLoadWeight().add(getUnloadWeight());
		return totWeight;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param totWeight the new 总重量
	 */
	public void setTotWeight(BigDecimal totWeight) {
		this.totWeight = totWeight;
	}

	/**
	 * 总票数
	 * 	为手动计算出来
	 * totWaybillQty = loadWaybillQty + unloadWaybillQty;
	 * @author ibm-zhangyixin
	 * @date 2013-1-11 下午5:50:50
	 */
	public Integer getTotWaybillQty() {
		if(StringUtils.isEmpty(this.getGoodsType()) || StringUtils.equals(this.getGoodsType(), TransferPDADictConstants.LOAD_GOODS_TYPE_ALL)) {
			return 0;
		}
		Integer loadWaybillQty = Integer.valueOf(0);
		Integer unloadWaybillQty = Integer.valueOf(0);
		if(this.getLoadWaybillQty() != null) {
			loadWaybillQty = this.getLoadWaybillQty();
		}
		if(this.getUnloadWaybillQty() != null) {
			unloadWaybillQty = this.getUnloadWaybillQty();
		}
		totWaybillQty = loadWaybillQty + unloadWaybillQty;
		return totWaybillQty;
	}

	/**
	 * 设置 总票数.
	 *
	 * @param totWaybillQty the new 总票数
	 */
	public void setTotWaybillQty(Integer totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}

	/**
	 * 总件数
	 * 	为手动计算出来
	 * totGoodsQty = loadGoodsQty + unloadGoodsQty;
	 * @author ibm-zhangyixin
	 * @date 2013-1-11 下午5:51:35
	 */
	public Integer getTotGoodsQty() {
		if(StringUtils.isEmpty(this.getGoodsType()) || StringUtils.equals(this.getGoodsType(), TransferPDADictConstants.LOAD_GOODS_TYPE_ALL)) {
			return 0;
		}
		Integer loadGoodsQty = Integer.valueOf(0);
		Integer unloadGoodsQty = Integer.valueOf(0);
		if(this.getLoadGoodsQty() != null) {
			loadGoodsQty = this.getLoadGoodsQty();
		}
		if(this.getUnloadGoodsQty() != null) {
			unloadGoodsQty = this.getUnloadGoodsQty();
		}
		totGoodsQty = loadGoodsQty + unloadGoodsQty;
		return totGoodsQty;
	}

	/**
	 * 设置 总件数.
	 *
	 * @param totGoodsQty the new 总件数
	 */
	public void setTotGoodsQty(Integer totGoodsQty) {
		this.totGoodsQty = totGoodsQty;
	}

	/**
	 * 总重量-临时变量
	 * 	新增理货员货量时为了方便从前台传了个总重量, 以判断理货员重量是否大于总重量
	 * 	(因为totWaybillQty的get方法被重写)
	 * @author ibm-zhangyixin
	 * @date 2013-1-11 下午5:53:50
	 */
	public BigDecimal getTotWeightTemp() {
		return totWeightTemp;
	}

	/**
	 * 设置 总重量-临时变量*.
	 *
	 * @param totWeightTemp the new 总重量-临时变量*
	 */
	public void setTotWeightTemp(BigDecimal totWeightTemp) {
		this.totWeightTemp = totWeightTemp;
	}

	/**
	 * 总票数-临时变量
	 * 	新增理货员货量时为了方便从前台传了个总票数, 以判断理货员票数是否大于总票数
	 * 	(因为totWeight的get方法被重写)
	 * @author ibm-zhangyixin
	 * @date 2013-1-11 下午5:53:50
	 */
	public Integer getTotWaybillQtyTemp() {
		return totWaybillQtyTemp;
	}

	/**
	 * 设置 总票数-临时变量*.
	 *
	 * @param totWaybillQtyTemp the new 总票数-临时变量*
	 */
	public void setTotWaybillQtyTemp(Integer totWaybillQtyTemp) {
		this.totWaybillQtyTemp = totWaybillQtyTemp;
	}

	/**
	 * 获取 当前部门(LoaderOrgCode)下所有子部门*.
	 *
	 * @return the 当前部门(LoaderOrgCode)下所有子部门*
	 */
	public List<String> getLoaderOrgCodes() {
		return loaderOrgCodes;
	}

	/**
	 * 设置 当前部门(LoaderOrgCode)下所有子部门*.
	 *
	 * @param loaderOrgCodes the new 当前部门(LoaderOrgCode)下所有子部门*
	 */
	public void setLoaderOrgCodes(List<String> loaderOrgCodes) {
		this.loaderOrgCodes = loaderOrgCodes;
	}


}