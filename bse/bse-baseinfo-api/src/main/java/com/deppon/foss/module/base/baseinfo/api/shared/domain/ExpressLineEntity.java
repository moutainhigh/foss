/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/EntityLineEntity.java
 * 
 * FILE NAME        	: EntityLineEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.util.define.FossConstants;

/**
 * 线路实体
 * 
 * @author foss-zhujunyong
 * @date Oct 18, 2012 6:49:03 PM
 * @version 1.0
 */
public class ExpressLineEntity extends DownloadableEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 4738847256900419868L;

    /**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 线路简码
     */
    private String simpleCode;
    
    /**
     * 线路名称(冗余)
     */
    private String lineName;
    
    /**
     * 管理部门（车队）编码
     */
    private String organizationCode;

    /**
     * 管理部门（车队）名称（扩展）
     */
    private String organizationName;
    
    /**
     * 出发部门编码
     */
    private String orginalOrganizationCode;

    /**
     * 到达部门编码
     */
    private String destinationOrganizationCode;
    
    /**
     * 出发部门名称（扩展）
     */
    private String orginalOrganizationName;

    /**
     * 到达部门名称（扩展）
     */
    private String destinationOrganizationName;
    
    /**
     * 出发城市(冗余)
     */
    private String orginalCityCode;
    
    /**
     * 到达城市(冗余)
     */
    private String destinationCityCode;
    
    /**
     * 出发城市名称
     */
    private String orginalCityName;

    /**
     * 到达城市名称
     */
    private String destinationCityName;
    
    /**
     * 运输类型（汽运，空运）-始发到达
     */
    private String transType;
    
    /**
     * 线路类型（专线，偏线，空运）-中转
     */
    private String lineType;

    /**
     * 线路类别 （始发，到达，中转到中转）
     */
    private String lineSort;
    
    /**
     * 是否默认线路 - 始发到达
     */
    private String isDefault;
 
    /**
     * 普车时效（千分之小时）
     */
    private Long commonAging;
    
    /**
     * 卡车时效（千分之小时）
     */
    private Long fastAging;
    
    /**
     * 偏线时效（千分之小时）
     */
    private Long otherAging;
    
    /**
     * 线路距离(公里)
     */
    private Long distance;
    
     /**
     * 是否有效
     */
    private String active;
    
    /**
    * 备注
    */
    private String notes;
    
    /**
     * 出发时间
     */
    private String leaveTime;
    /**
     * 到达时间
     */
    private String arriveTime;
    /**
     * 运行时长
     */
    private int runningTime;
    /**
     * 最终修改人
     */
    private String modifyUserCode;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 班次
     */
    private int frequencyNo;
    /**
     * 中准最晚到达时间
     */
    private String arriveDeadTime;
    /**
     * 准点到达时间的天数
     */
    private int arriveDay;
    private int arriveDeadDay;
    /**
    * 虚拟编码列表（扩展），批量作废用
    */
    private List<String> virtualCodes;
    
    /**
    * 发车标准列表
    */
    private List<ExpressDepartureStandardEntity> standardList;
    
    /**
    * 是否生效（走货路径和走货路径线路添加完成后，校验成功则生效）
    */
    private String valid;
    /**
     * 是否不奖线路（Y/N）.
     */
    private String isNorewardPunish;
    
    
    
    public int getArriveDeadDay() {
		return arriveDeadDay;
	}

	public void setArriveDeadDay(int arriveDeadDay) {
		this.arriveDeadDay = arriveDeadDay;
	}

	/**
     * 获取 是否不奖线路（Y/N）.
     *
     * @return  the isNorewardPunish
     */
    public String getIsNorewardPunish() {
        return isNorewardPunish;
    }
    
    /**
     * 设置 是否不奖线路（Y/N）.
     *
     * @param isNorewardPunish the isNorewardPunish to set
     */
    public void setIsNorewardPunish(String isNorewardPunish) {
        this.isNorewardPunish = isNorewardPunish;
    }

    /**
     * 
     * 检查该线路是否生效 
     * @author foss-zhujunyong
     * @date Jan 5, 2013 12:05:45 PM
     * @return
     * @see
     */
    public boolean checkValid() {
	return StringUtils.equals(valid, FossConstants.YES);
    }

    public String getLeaveTime() {
		return leaveTime;
	}

	public int getArriveDay() {
		return arriveDay;
	}

	public void setArriveDay(int arriveDay) {
		this.arriveDay = arriveDay;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getFrequencyNo() {
		return frequencyNo;
	}

	public void setFrequencyNo(int frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	public String getArriveDeadTime() {
		return arriveDeadTime;
	}

	public void setArriveDeadTime(String arriveDeadTime) {
		this.arriveDeadTime = arriveDeadTime;
	}

	/**
     * @return  the valid
     */
    public String getValid() {
        return valid;
    }
    
    /**
     * @param valid the valid to set
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    /**
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * @return  the simpleCode
     */
    public String getSimpleCode() {
        return simpleCode;
    }
    
    /**
     * @param simpleCode the simpleCode to set
     */
    public void setSimpleCode(String simpleCode) {
        this.simpleCode = simpleCode;
    }
    
    /**
     * @return  the lineName
     */
    public String getLineName() {
        return lineName;
    }
    
    /**
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    
    /**
     * @return  the organizationCode
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * @param organizationCode the organizationCode to set
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * @return  the orginalOrganizationCode
     */
    public String getOrginalOrganizationCode() {
        return orginalOrganizationCode;
    }
    
    /**
     * @param orginalOrganizationCode the orginalOrganizationCode to set
     */
    public void setOrginalOrganizationCode(String orginalOrganizationCode) {
        this.orginalOrganizationCode = orginalOrganizationCode;
    }
    
    /**
     * @return  the destinationOrganizationCode
     */
    public String getDestinationOrganizationCode() {
        return destinationOrganizationCode;
    }
    
    /**
     * @param destinationOrganizationCode the destinationOrganizationCode to set
     */
    public void setDestinationOrganizationCode(String destinationOrganizationCode) {
        this.destinationOrganizationCode = destinationOrganizationCode;
    }
    
    /**
     * @return  the orginalCityCode
     */
    public String getOrginalCityCode() {
        return orginalCityCode;
    }
    
    /**
     * @param orginalCityCode the orginalCityCode to set
     */
    public void setOrginalCityCode(String orginalCityCode) {
        this.orginalCityCode = orginalCityCode;
    }
    
    /**
     * @return  the destinationCityCode
     */
    public String getDestinationCityCode() {
        return destinationCityCode;
    }
    
    /**
     * @param destinationCityCode the destinationCityCode to set
     */
    public void setDestinationCityCode(String destinationCityCode) {
        this.destinationCityCode = destinationCityCode;
    }

    /**
     * @return  the transType
     */
    public String getTransType() {
        return transType;
    }
    
    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    /**
     * @return  the lineType
     */
    public String getLineType() {
        return lineType;
    }
    
    /**
     * @param lineType the lineType to set
     */
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
    
    /**
     * @return  the lineSort
     */
    public String getLineSort() {
        return lineSort;
    }
    
    /**
     * @param lineSort the lineSort to set
     */
    public void setLineSort(String lineSort) {
        this.lineSort = lineSort;
    }
    
    /**
     * @return  the isDefault
     */
    public String getIsDefault() {
        return isDefault;
    }
    
    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
    /**
     * @return  the commonAging
     */
    public Long getCommonAging() {
        return commonAging;
    }
    
    /**
     * @param commonAging the commonAging to set
     */
    public void setCommonAging(Long commonAging) {
        this.commonAging = commonAging;
    }

    /**
     * @return  the fastAging
     */
    public Long getFastAging() {
        return fastAging;
    }
    
    /**
     * @param fastAging the fastAging to set
     */
    public void setFastAging(Long fastAging) {
        this.fastAging = fastAging;
    }
    
    /**
     * @return  the otherAging
     */
    public Long getOtherAging() {
        return otherAging;
    }
    
    /**
     * @param otherAging the otherAging to set
     */
    public void setOtherAging(Long otherAging) {
        this.otherAging = otherAging;
    }
    
    /**
     * @return  the distance
     */
    public Long getDistance() {
        return distance == null ? 0 : distance;
    }
    
    /**
     * @param distance the distance to set
     */
    public void setDistance(Long distance) {
        this.distance = distance;
    }
    
    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * @return  the orginalOrganizationName
     */
    public String getOrginalOrganizationName() {
        return orginalOrganizationName;
    }
    
    /**
     * @param orginalOrganizationName the orginalOrganizationName to set
     */
    public void setOrginalOrganizationName(String orginalOrganizationName) {
        this.orginalOrganizationName = orginalOrganizationName;
    }
    
    /**
     * @return  the destinationOrganizationName
     */
    public String getDestinationOrganizationName() {
        return destinationOrganizationName;
    }

    /**
     * @param destinationOrganizationName the destinationOrganizationName to set
     */
    public void setDestinationOrganizationName(String destinationOrganizationName) {
        this.destinationOrganizationName = destinationOrganizationName;
    }

    /**
     * @return  the orginalCityName
     */
    public String getOrginalCityName() {
        return orginalCityName;
    }
    
    /**
     * @param orginalCityName the orginalCityName to set
     */
    public void setOrginalCityName(String orginalCityName) {
        this.orginalCityName = orginalCityName;
    }
    
    /**
     * @return  the destinationCityName
     */
    public String getDestinationCityName() {
        return destinationCityName;
    }

    /**
     * @param destinationCityName the destinationCityName to set
     */
    public void setDestinationCityName(String destinationCityName) {
        this.destinationCityName = destinationCityName;
    }

    /**
     * @return  the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }
    
    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return  the standardList
     */
    public List<ExpressDepartureStandardEntity> getStandardList() {
        return standardList;
    }
    
    /**
     * @param standardList the standardList to set
     */
    public void setStandardList(List<ExpressDepartureStandardEntity> standardList) {
        this.standardList = standardList;
    }

    /**
     * @return  the virtualCodes
     */
    public List<String> getVirtualCodes() {
        return virtualCodes;
    }
    
    /**
     * @param virtualCodes the virtualCodes to set
     */
    public void setVirtualCodes(List<String> virtualCodes) {
        this.virtualCodes = virtualCodes;
    }
}