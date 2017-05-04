/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/ProductEntity.java
 * 
 * FILE NAME        	: ProductEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 
 * 产品实体
 * @author DP-Foss-YueHongJie
 * @date 2012-10-24 上午9:53:31
 */
public class ProductEntity extends BaseEntity {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 2292884027172271125L;
    /**
     * 产品CODE
     */
    private String code;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品激活状态
     */
    private String active;
    /**
     * 产品备注--------------------该名称现在无用 前台ext传值到后面时会过滤掉，所以该值无法赋值
     */
    private String description;
    /**
     * 产品备注
     */
    private String mark;
    /**
     * 产品版本号
     */
    private Long versionNo;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 产品性质(空运/汽运)
     */
    private String transportType;
    /**
     * 创建机构编码	
     */
    private String createOrgCode;
    /**
     * 修改机构编码
     */
    private String modifyOrgCode;
    /**
     * 产品层级
     */
    private Long levels;
    /**
     * 父级产品编号
     */
    private String parentCode;
    
    /**
     * 所属父级产品名称
     */
    private String parentName;

    /**
     * 父级产品ID
     */
    private String refId;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 优先级  	快-慢
     */
    private String priority;
    /**
     * 显示排序用
     */
    private int seq;
    /**
     * 修改人姓名
     */
    private String createUserName;
    /**
     * 创建人姓名
     */
    private String modifyUserName;
    
    /**
     * 提供公共选择器查询标志，该参数按照code或者name进行匹配所搜相关记录
     */
    private String queryParam;
    
    /**
     * 级别list 提供公共选择器所需要查询几级产品
     */
    private List<Long> levelsList;
    
    public String getQueryParam() {
        return queryParam;
    }

    /**
     * 
     * <p>获取级别集合</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-2-1 下午2:32:55
     * @return
     * @see
     */
    public List<Long> getLevelsList() {
        return levelsList;
    }

    /**
     * 
     * <p>设置级别集合</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-2-1 下午2:32:42
     * @param levelsList
     * @see
     */
    public void setLevelsList(List<Long> levelsList) {
        this.levelsList = levelsList;
    }


    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    /**
     * 目的地适用网点类型  参考变量  DictionaryValueConstants  OUTERBRANCH_TYPE_KY  OUTERBRANCH_TYPE_PX  DEPPON_OWN_ORG 该值通过#链接，可以有多个值
     */
    private String destNetType;
    
    
    
    /**
     * 获取 目的地适用网点类型  参考变量  DictionaryValueConstants  OUTERBRANCH_TYPE_KY  OUTERBRANCH_TYPE_PX  DEPPON_OWN_ORG 该值通过#链接，可以有多个值.
     *
     * @return the 目的地适用网点类型  参考变量  DictionaryValueConstants  OUTERBRANCH_TYPE_KY  OUTERBRANCH_TYPE_PX  DEPPON_OWN_ORG 该值通过#链接，可以有多个值
     */
    public String getDestNetType() {
        return destNetType;
    }



    
    /**
     * 设置 目的地适用网点类型  参考变量  DictionaryValueConstants  OUTERBRANCH_TYPE_KY  OUTERBRANCH_TYPE_PX  DEPPON_OWN_ORG 该值通过#链接，可以有多个值.
     *
     * @param destNetType the new 目的地适用网点类型  参考变量  DictionaryValueConstants  OUTERBRANCH_TYPE_KY  OUTERBRANCH_TYPE_PX  DEPPON_OWN_ORG 该值通过#链接，可以有多个值
     */
    public void setDestNetType(String destNetType) {
        this.destNetType = destNetType;
    }



    /**
     * 获取 显示排序用.
     *
     * @return the 显示排序用
     */
    public int getSeq() {
        return seq;
    }


    
    /**
     * 设置 显示排序用.
     *
     * @param seq the new 显示排序用
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }


    /**
     * 获取 产品层级.
     *
     * @return the 产品层级
     */
    public Long getLevels() {
        return levels;
    }

    
    /**
     * 设置 产品层级.
     *
     * @param levels the new 产品层级
     */
    public void setLevels(Long levels) {
        this.levels = levels;
    }

    
    /**
     * 获取 父级产品编号.
     *
     * @return the 父级产品编号
     */
    public String getParentCode() {
        return parentCode;
    }

    
    /**
     * 设置 父级产品编号.
     *
     * @param parentCode the new 父级产品编号
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    
    /**
     * 获取 父级产品ID.
     *
     * @return the 父级产品ID
     */
    public String getRefId() {
        return refId;
    }

    
    /**
     * 设置 父级产品ID.
     *
     * @param refId the new 父级产品ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    
    /**
     * 获取 简称.
     *
     * @return the 简称
     */
    public String getShortName() {
        return shortName;
    }

    
    /**
     * 设置 简称.
     *
     * @param shortName the new 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    
    /**
     * 获取 优先级  	快-慢.
     *
     * @return the 优先级  	快-慢
     */
    public String getPriority() {
        return priority;
    }

    
    /**
     * 设置 优先级  	快-慢.
     *
     * @param priority the new 优先级  	快-慢
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 获取 产品CODE.
     *
     * @return the 产品CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 产品CODE.
     *
     * @param code the new 产品CODE
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 产品名称.
     *
     * @return the 产品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 产品名称.
     *
     * @param name the new 产品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 产品激活状态.
     *
     * @return the 产品激活状态
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 产品激活状态.
     *
     * @param active the new 产品激活状态
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 产品备注.
     *
     * @return the 产品备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 产品备注.
     *
     * @param description the new 产品备注
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 产品版本号(目前没用到).
     *
     * @return the 产品版本号(目前没用到)
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 产品版本号(目前没用到).
     *
     * @param versionNo the new 产品版本号(目前没用到)
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 开始时间.
     *
     * @return the 开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 开始时间.
     *
     * @param beginTime the new 开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 结束时间.
     *
     * @return the 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 结束时间.
     *
     * @param endTime the new 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 产品性质(空运/汽运).
     *
     * @return the 产品性质(空运/汽运)
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * 设置 产品性质(空运/汽运).
     *
     * @param transportType the new 产品性质(空运/汽运)
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * 获取 创建机构编码.
     *
     * @return the 创建机构编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建机构编码.
     *
     * @param createOrgCode the new 创建机构编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改机构编码.
     *
     * @return the 修改机构编码
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改机构编码.
     *
     * @param modifyOrgCode the new 修改机构编码
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }




	/**
	 * 获取 修改人姓名.
	 *
	 * @return the 修改人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}




	/**
	 * 设置 修改人姓名.
	 *
	 * @param createUserName the new 修改人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}




	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置 创建人姓名.
	 *
	 * @param modifyUserName the new 创建人姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * 
	 * <p>获取父级产品名称</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-25 上午9:37:16
	 * @return
	 * @see
	 */
	public String getParentName() {
	        return parentName;
	}
	    
	/**
	 * 
	 * <p>设置父级产品名称</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-25 上午9:37:41
	 * @param parentName
	 * @see
	 */
	public void setParentName(String parentName) {
	     this.parentName = parentName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
    
}