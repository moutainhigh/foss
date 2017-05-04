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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/vo/QueryUnpackVo.java
 *  
 *  FILE NAME          :QueryUnpackVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.CurrentDeptDto;

/**
 * 查询营业部代打包装的VO
 * @author 046130-foss-xuduowei
 * @param <DataDictionaryValueEntity>
 * @date 2012-10-12 下午6:19:47
 */
public class QueryUnpackVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8222714293339241738L;
	
	/**
	 * 查询营业部代打包装查询条件
	 */
	private QueryUnpackConditionEntity queryUnpackConditionEntity;
	/**
	 * 查询包装录入信息的查询条件
	 */
	private QueryPackedConditionEntity queryPackedConditionEntity;
	/**
	 * 包装人员信息
	 */
	private PackedPersonEntity packedPersonEntity;
	/**
	 * 查询包装录入的返回结果集
	 */
	private List<QueryPackedResultEntity> queryPackedResultList;
	/**
	 * 查询营业部代打包装的返回结果集
	 */
	private List<QueryUnpackResultEntity> queryUnpackResultList;
	/**
	 * 查询包装明细状态结果集
	 */
	private List<QueryUnpackDetailsEntity> queryUnpackDetailsList;
	/**
	 * 新旧流水号关系，录入或修改操作时，从后台获取
	 */
	private List<SerialRelationEntity> serialRelationList;
	/**
	 * 新流水号集合，主要是在前台显示
	 */
	private List<SerialRelationEntity> newSerialList;
	/**
	 * 包装人员列表结果集
	 */
	private List<PackedPersonEntity> packedPersonList;
	/**
	 * 前端查询营业部代打包装货物状态下拉框的集合
	 */
	private List<DataDictionaryValueEntity> goodsStatusList;
	/**
	 * 前端获取包装材料下拉框
	 */
	private List<DataDictionaryValueEntity> packedMateList;
	/**
	 * 包装录入主信息
	 */
	private WaybillPackEntity waybillPackEntity;
	/**
	 * 标签打印数据
	 */
	private List<BarcodePrintLabelDto> barcodePrintLabelDtoList;
	/**
	 * 当前组织信息
	 */
	private CurrentDeptDto currentDeptDto;
	/**
	 * 每页显示限制数量
	 */
	private int limit;
	/**
	 * 开始数字
	 */
	private int start;
	/**
	 * 返回总个数
	 */
	private Long totalCount;
	/**
	 * 运单号
	 */
	private String waybillno;
	/**
	 * 包装类型
	 */
	private String packageType;
	/**
	 * 最大流水号
	 */
	private String maxSerialNo;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 当前登录人所在部门
	 */
	private String packedDept;
	/**
	 * 代包装部门
	 */
	private String packDept;
	/**
	 * 开单部门编码
	 */
	private String waybillCreateDeptCode;
	/**
	 * 包装人姓名
	 */
	private String empName;
	/**
	 * 包装人工号
	 */
	private String empCode;
	
	
	/**
	 * 获取 最大流水号.
	 *
	 * @return the 最大流水号
	 */
	public String getMaxSerialNo() {
		return maxSerialNo;
	}

	/**
	 * 设置 最大流水号.
	 *
	 * @param maxSerialNo the new 最大流水号
	 */
	public void setMaxSerialNo(String maxSerialNo) {
		this.maxSerialNo = maxSerialNo;
	}

	/**
	 * 获取 查询营业部代打包装查询条件.
	 *
	 * @return the 查询营业部代打包装查询条件
	 */
	public QueryUnpackConditionEntity getQueryUnpackConditionEntity() {
		return queryUnpackConditionEntity;
	}

	/**
	 * 设置 查询营业部代打包装查询条件.
	 *
	 * @param queryUnpackConditionEntity the new 查询营业部代打包装查询条件
	 */
	public void setQueryUnpackConditionEntity(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		this.queryUnpackConditionEntity = queryUnpackConditionEntity;
	}

	/**
	 * 获取 每页显示限制数量.
	 *
	 * @return the 每页显示限制数量
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 设置 每页显示限制数量.
	 *
	 * @param limit the new 每页显示限制数量
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 获取 开始数字.
	 *
	 * @return the 开始数字
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 设置 开始数字.
	 *
	 * @param start the new 开始数字
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取 返回总个数.
	 *
	 * @return the 返回总个数
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置 返回总个数.
	 *
	 * @param totalCount the new 返回总个数
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取 查询营业部代打包装的返回结果集.
	 *
	 * @return the 查询营业部代打包装的返回结果集
	 */
	public List<QueryUnpackResultEntity> getQueryUnpackResultList() {
		return queryUnpackResultList;
	}

	/**
	 * 设置 查询营业部代打包装的返回结果集.
	 *
	 * @param queryUnpackResultList the new 查询营业部代打包装的返回结果集
	 */
	public void setQueryUnpackResultList(
			List<QueryUnpackResultEntity> queryUnpackResultList) {
		this.queryUnpackResultList = queryUnpackResultList;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillno() {
		return waybillno;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillno the new 运单号
	 */
	public void setWaybillno(String waybillno) {
		this.waybillno = waybillno;
	}

	/**
	 * 获取 查询包装明细状态结果集.
	 *
	 * @return the 查询包装明细状态结果集
	 */
	public List<QueryUnpackDetailsEntity> getQueryUnpackDetailsList() {
		return queryUnpackDetailsList;
	}

	/**
	 * 设置 查询包装明细状态结果集.
	 *
	 * @param queryUnpackDetailsList the new 查询包装明细状态结果集
	 */
	public void setQueryUnpackDetailsList(
			List<QueryUnpackDetailsEntity> queryUnpackDetailsList) {
		this.queryUnpackDetailsList = queryUnpackDetailsList;
	}

	/**
	 * 获取 查询包装录入信息的查询条件.
	 *
	 * @return the 查询包装录入信息的查询条件
	 */
	public QueryPackedConditionEntity getQueryPackedConditionEntity() {
		return queryPackedConditionEntity;
	}

	/**
	 * 设置 查询包装录入信息的查询条件.
	 *
	 * @param queryPackedConditionEntity the new 查询包装录入信息的查询条件
	 */
	public void setQueryPackedConditionEntity(
			QueryPackedConditionEntity queryPackedConditionEntity) {
		this.queryPackedConditionEntity = queryPackedConditionEntity;
	}

	/**
	 * 获取 查询包装录入的返回结果集.
	 *
	 * @return the 查询包装录入的返回结果集
	 */
	public List<QueryPackedResultEntity> getQueryPackedResultList() {
		return queryPackedResultList;
	}

	/**
	 * 设置 查询包装录入的返回结果集.
	 *
	 * @param queryPackedResultList the new 查询包装录入的返回结果集
	 */
	public void setQueryPackedResultList(
			List<QueryPackedResultEntity> queryPackedResultList) {
		this.queryPackedResultList = queryPackedResultList;
	}

	/**
	 * 获取 新旧流水号关系，录入或修改操作时，从后台获取.
	 *
	 * @return the 新旧流水号关系，录入或修改操作时，从后台获取
	 */
	public List<SerialRelationEntity> getSerialRelationList() {
		return serialRelationList;
	}

	/**
	 * 设置 新旧流水号关系，录入或修改操作时，从后台获取.
	 *
	 * @param serialRelationList the new 新旧流水号关系，录入或修改操作时，从后台获取
	 */
	public void setSerialRelationList(List<SerialRelationEntity> serialRelationList) {
		this.serialRelationList = serialRelationList;
	}

	

	/**
	 * 获取 新流水号集合，主要是在前台显示.
	 *
	 * @return the 新流水号集合，主要是在前台显示
	 */
	public List<SerialRelationEntity> getNewSerialList() {
		return newSerialList;
	}

	/**
	 * 设置 新流水号集合，主要是在前台显示.
	 *
	 * @param newSerialList the new 新流水号集合，主要是在前台显示
	 */
	public void setNewSerialList(List<SerialRelationEntity> newSerialList) {
		this.newSerialList = newSerialList;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 包装录入主信息.
	 *
	 * @return the 包装录入主信息
	 */
	public WaybillPackEntity getWaybillPackEntity() {
		return waybillPackEntity;
	}

	/**
	 * 设置 包装录入主信息.
	 *
	 * @param waybillPackEntity the new 包装录入主信息
	 */
	public void setWaybillPackEntity(WaybillPackEntity waybillPackEntity) {
		this.waybillPackEntity = waybillPackEntity;
	}

	/**
	 * 获取 包装人员列表结果集.
	 *
	 * @return the 包装人员列表结果集
	 */
	public List<PackedPersonEntity> getPackedPersonList() {
		return packedPersonList;
	}

	/**
	 * 设置 包装人员列表结果集.
	 *
	 * @param packedPersonList the new 包装人员列表结果集
	 */
	public void setPackedPersonList(List<PackedPersonEntity> packedPersonList) {
		this.packedPersonList = packedPersonList;
	}

	/**
	 * 获取 当前登录人所在部门.
	 *
	 * @return the 当前登录人所在部门
	 */
	public String getPackedDept() {
		return packedDept;
	}

	/**
	 * 设置 当前登录人所在部门.
	 *
	 * @param packedDept the new 当前登录人所在部门
	 */
	public void setPackedDept(String packedDept) {
		this.packedDept = packedDept;
	}

	/**
	 * 获取 包装人姓名.
	 *
	 * @return the 包装人姓名
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * 设置 包装人姓名.
	 *
	 * @param empName the new 包装人姓名
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * 获取 包装人工号.
	 *
	 * @return the 包装人工号
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * 设置 包装人工号.
	 *
	 * @param empCode the new 包装人工号
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * 获取 包装人员信息.
	 *
	 * @return the 包装人员信息
	 */
	public PackedPersonEntity getPackedPersonEntity() {
		return packedPersonEntity;
	}

	/**
	 * 设置 包装人员信息.
	 *
	 * @param packedPersonEntity the new 包装人员信息
	 */
	public void setPackedPersonEntity(PackedPersonEntity packedPersonEntity) {
		this.packedPersonEntity = packedPersonEntity;
	}

	/**
	 * 获取 前端查询营业部代打包装货物状态下拉框的集合.
	 *
	 * @return the 前端查询营业部代打包装货物状态下拉框的集合
	 */
	public List<DataDictionaryValueEntity> getGoodsStatusList() {
		return goodsStatusList;
	}

	/**
	 * 设置 前端查询营业部代打包装货物状态下拉框的集合.
	 *
	 * @param goodsStatusList the new 前端查询营业部代打包装货物状态下拉框的集合
	 */
	public void setGoodsStatusList(List<DataDictionaryValueEntity> goodsStatusList) {
		this.goodsStatusList = goodsStatusList;
	}

	/**
	 * 获取 前端获取包装材料下拉框.
	 *
	 * @return the 前端获取包装材料下拉框
	 */
	public List<DataDictionaryValueEntity> getPackedMateList() {
		return packedMateList;
	}

	/**
	 * 设置 前端获取包装材料下拉框.
	 *
	 * @param packedMateList the new 前端获取包装材料下拉框
	 */
	public void setPackedMateList(List<DataDictionaryValueEntity> packedMateList) {
		this.packedMateList = packedMateList;
	}

	/**
	 * 获取 开单部门编码.
	 *
	 * @return the 开单部门编码
	 */
	public String getWaybillCreateDeptCode() {
		return waybillCreateDeptCode;
	}

	/**
	 * 设置 开单部门编码.
	 *
	 * @param waybillCreateDeptCode the new 开单部门编码
	 */
	public void setWaybillCreateDeptCode(String waybillCreateDeptCode) {
		this.waybillCreateDeptCode = waybillCreateDeptCode;
	}

	/**
	 * 获取 代包装部门.
	 *
	 * @return the 代包装部门
	 */
	public String getPackDept() {
		return packDept;
	}

	/**
	 * 设置 代包装部门.
	 *
	 * @param packDept the new 代包装部门
	 */
	public void setPackDept(String packDept) {
		this.packDept = packDept;
	}

	/**
	 * 获取 当前组织信息.
	 *
	 * @return the 当前组织信息
	 */
	public CurrentDeptDto getCurrentDeptDto() {
		return currentDeptDto;
	}
	/**
	 * 获取 标签打印数据.
	 *
	 * @return the 标签打印数据
	 */
	public List<BarcodePrintLabelDto> getBarcodePrintLabelDtoList() {
		return barcodePrintLabelDtoList;
	}
	/**
	 * 设置 标签打印数据.
	 *
	 * @param packLabelPrintDto the new 标签打印数据
	 */
	public void setBarcodePrintLabelDtoList(
			List<BarcodePrintLabelDto> barcodePrintLabelDtoList) {
		this.barcodePrintLabelDtoList = barcodePrintLabelDtoList;
	}

	/**
	 * 设置 当前组织信息.
	 *
	 * @param currentDeptDto the new 当前组织信息
	 */
	public void setCurrentDeptDto(CurrentDeptDto currentDeptDto) {
		this.currentDeptDto = currentDeptDto;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	
	
	

}