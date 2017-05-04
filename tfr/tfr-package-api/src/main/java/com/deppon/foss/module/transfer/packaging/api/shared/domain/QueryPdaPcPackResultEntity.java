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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/WaybillPackEntity.java
 *  
 *  FILE NAME          :WaybillPackEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.domain
 * FILE    NAME: WaybillPackEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;


import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
 * 查询并汇总PDA与PC端包装金额 结果
 * @author ZhangXu
 *
 * 2014-5-7
 */

public class QueryPdaPcPackResultEntity extends BaseEntity {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	   * 运单号
	    */
	   private String waybillNo; 
	   /**
		 * 创建时间  包装时间
		 */
	   private Date createTime;    	
	   /**
		 * 开单部门code
		 */
	   private String	billOrgCode;    	
	   /**
		 * 开单部门名称
		 */
	   private String	billOrgName;    	
	   /**
		 * 	包装部门code
		 */
	   private String	packageOrgCode;    
	   /**
		 * 包装部门名称
		 */
	   private String packageOrgName;    	
	   /**
		 * 理论打木架体积
		 */
	   private BigDecimal  theoryFrameVolume;    	
	   /**
		 * 实际打木架体积
		 */
	   private BigDecimal  actualFrameVolume;    	
	   /**
		 * 理论打木箱体积
		 */
	   private BigDecimal  	theoryWoodenVolume;    	
	   /**
		 * 实际打木箱体积
		 */
	   private BigDecimal  actualWoodenVolume;    	
	   /**
		 * 理论打木托个数
		 */
	   private BigDecimal 	theoryMaskNumber;    	
	   /**
		 * 实际打木托个数
		 */
	   private BigDecimal  actualMaskNumber;    	
	   /**
		 * 木条长度
		 */
	   private BigDecimal  woodenBarLong;    	
	   /**
		 * 	气泡膜体积
		 */
	   private BigDecimal  	bubbVelamenVolume;    
	   /**
		 *  	缠绕膜体积
		 */
	   private BigDecimal  	bindVelamenVolume;   
	   /**
		 * 	包带根数
		 */
	   private BigDecimal  bagBeltNum;    
	   /**
		 * 包装供应商code
		 */
	   private String packageSupplierCode;    	
	   /**
		 * 	包装供应商
		 */
	   private String packageSupplierName;    
	   /**
		 * 创建部门code
		 */
	   private String createOrgCode;    	
	   /**
		 * 创建部门
		 */
	   private String createOrgName;    	
	   /**
		 * 创建人code
		 */
	   private String createUserCode;    	
	   /**
		 * 创建人
		 */
	   private String createUserName;    	
	   /**
		 * 修改人code
		 */
	   private String 	modifyUserCode;    	
	   /**
		 * 修改人
		 */
	   private String modifyUserName; 
	   /**
	    * 新增人code
	    */
	   private String newAddUserCode;
	   /**
	    * 新增人
	    */
	   private String newAddUserName;
	   /**
	    * 应付金额
	    */
	   private  BigDecimal packagePayableMoney;
	   /**
	    * 开单包装费
	    */
	   private BigDecimal waybillPackgeFee;
	   /**
	    * 是否盈利
	    */
	   private String  profitStatus;
	/**
	 * 获取运单号 waybillNo
	 * @return  waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 *设置运单号 waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 获取包装时间 createTime
	 * @return  createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 *设置 包装时间 createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 开单部门code billOrgCode
	 * @return  billOrgCode
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}
	/**
	 *设置开单部门code billOrgCode
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	/**
	 * 获取 开单部门   billOrgName
	 * @return  billOrgName
	 */
	public String getBillOrgName() {
		return billOrgName;
	}
	/**
	 *设置开单部门  billOrgName
	 */
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}
	/**
	 * 获取包装部门code packageOrgCode
	 * @return  packageOrgCode
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}
	/**
	 *设置 包装部门code packageOrgCode
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}
	/**
	 * 获取包装部门   packageOrgName
	 * @return  packageOrgName
	 */
	public String getPackageOrgName() {
		return packageOrgName;
	}
	/**
	 *设置包装部门  packageOrgName
	 */
	public void setPackageOrgName(String packageOrgName) {
		this.packageOrgName = packageOrgName;
	}
	/**
	 * 获取理论打木架体积 theoryFrameVolume
	 * @return  theoryFrameVolume
	 */
	public BigDecimal getTheoryFrameVolume() {
		return theoryFrameVolume;
	}
	/**
	 *设置理论打木架体积 theoryFrameVolume
	 */
	public void setTheoryFrameVolume(BigDecimal theoryFrameVolume) {
		this.theoryFrameVolume = theoryFrameVolume;
	}
	/**
	 * 获取实际打木架体积 actualFrameVolume
	 * @return  actualFrameVolume
	 */
	public BigDecimal getActualFrameVolume() {
		return actualFrameVolume;
	}
	/**
	 *设置实际打木架体积 actualFrameVolume
	 */
	public void setActualFrameVolume(BigDecimal actualFrameVolume) {
		this.actualFrameVolume = actualFrameVolume;
	}
	/**
	 * 获取理论打木箱体积theoryWoodenVolume
	 * @return  theoryWoodenVolume
	 */
	public BigDecimal getTheoryWoodenVolume() {
		return theoryWoodenVolume;
	}
	/**
	 *设置理论打木箱体积 theoryWoodenVolume
	 */
	public void setTheoryWoodenVolume(BigDecimal theoryWoodenVolume) {
		this.theoryWoodenVolume = theoryWoodenVolume;
	}
	/**
	 * 获取实际打木箱体积actualWoodenVolume
	 * @return  actualWoodenVolume
	 */
	public BigDecimal getActualWoodenVolume() {
		return actualWoodenVolume;
	}
	/**
	 *设置实际打木箱体积actualWoodenVolume
	 */
	public void setActualWoodenVolume(BigDecimal actualWoodenVolume) {
		this.actualWoodenVolume = actualWoodenVolume;
	}
	/**
	 * 获取理论打木托个数 theoryMaskNumber
	 * @return  theoryMaskNumber
	 */
	public BigDecimal getTheoryMaskNumber() {
		return theoryMaskNumber;
	}
	/**
	 *设置理论打木托个数 theoryMaskNumber
	 */
	public void setTheoryMaskNumber(BigDecimal theoryMaskNumber) {
		this.theoryMaskNumber = theoryMaskNumber;
	}
	/**
	 * 获取实际打木托个数 actualMaskNumber
	 * @return  actualMaskNumber
	 */
	public BigDecimal getActualMaskNumber() {
		return actualMaskNumber;
	}
	/**
	 *设置实际打木托个数 actualMaskNumber
	 */
	public void setActualMaskNumber(BigDecimal actualMaskNumber) {
		this.actualMaskNumber = actualMaskNumber;
	}
	/**
	 * 获取木条长度 woodenBarLong
	 * @return  woodenBarLong
	 */
	public BigDecimal getWoodenBarLong() {
		return woodenBarLong;
	}
	/**
	 *设置木条长度 woodenBarLong
	 */
	public void setWoodenBarLong(BigDecimal woodenBarLong) {
		this.woodenBarLong = woodenBarLong;
	}
	/**
	 * 获取	气泡膜体积 bubbVelamenVolume
	 * @return  bubbVelamenVolume
	 */
	public BigDecimal getBubbVelamenVolume() {
		return bubbVelamenVolume;
	}
	/**
	 *设置气泡膜体积 bubbVelamenVolume
	 */
	public void setBubbVelamenVolume(BigDecimal bubbVelamenVolume) {
		this.bubbVelamenVolume = bubbVelamenVolume;
	}
	/**
	 * 获取缠绕膜体积 bindVelamenVolume
	 * @return  bindVelamenVolume
	 */
	public BigDecimal getBindVelamenVolume() {
		return bindVelamenVolume;
	}
	/**
	 *设置缠绕膜体积 bindVelamenVolume
	 */
	public void setBindVelamenVolume(BigDecimal bindVelamenVolume) {
		this.bindVelamenVolume = bindVelamenVolume;
	}
	/**
	 * 获取	包带根数 bagBeltNum
	 * @return  bagBeltNum
	 */
	public BigDecimal getBagBeltNum() {
		return bagBeltNum;
	}
	/**
	 *设置包带根数bagBeltNum
	 */
	public void setBagBeltNum(BigDecimal bagBeltNum) {
		this.bagBeltNum = bagBeltNum;
	}
	/**
	 * 获取包装供应商code packageSupplierCode
	 * @return  packageSupplierCode
	 */
	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}
	/**
	 *设置包装供应商code packageSupplierCode
	 */
	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	/**
	 * 获取包装供应商 packageSupplierName
	 * @return  packageSupplierName
	 */
	public String getPackageSupplierName() {
		return packageSupplierName;
	}
	/**
	 *设置包装供应商  packageSupplierName
	 */
	public void setPackageSupplierName(String packageSupplierName) {
		this.packageSupplierName = packageSupplierName;
	}
	/**
	 * 获取创建部门code createOrgCode
	 * @return  createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 *设置创建部门code createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * 获取创建部门  createOrgName
	 * @return  createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	/**
	 *设置创建部门  createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	/**
	 * 获取创建人code createUserCode
	 * @return  createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 *设置创建人code createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * 获取创建人   createUserName
	 * @return  createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 *设置创建人        createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * 获取修改人  modifyUserCode
	 * @return  modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 *设置修改人     modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * 获取修改人     modifyUserName
	 * @return  modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 *设置修改人    modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * 获取新增人Code newAddUserCode
	 * @return  newAddUserCode
	 */
	public String getNewAddUserCode() {
		return newAddUserCode;
	}
	/**
	 *设置新增人Code  newAddUserCode
	 */
	public void setNewAddUserCode(String newAddUserCode) {
		this.newAddUserCode = newAddUserCode;
	}
	/**
	 * 获取新增人  newAddUserName
	 * @return  newAddUserName
	 */
	public String getNewAddUserName() {
		return newAddUserName;
	}
	/**
	 *设置新增人  newAddUserName
	 */
	public void setNewAddUserName(String newAddUserName) {
		this.newAddUserName = newAddUserName;
	}
	/**
	 * 获取 应付金额 packagePayableMoney
	 * @return  packagePayableMoney
	 */
	public BigDecimal getPackagePayableMoney() {
		return packagePayableMoney;
	}
	
	/**
	 *设置应付金额  packagePayableMoney
	 */
	public void setPackagePayableMoney(BigDecimal packagePayableMoney) {
		this.packagePayableMoney = packagePayableMoney;
	}
	
	/**
	 * 获取开单包装费waybillPackgeFee
	 * @return  waybillPackgeFee
	 */
	public BigDecimal getWaybillPackgeFee() {
		return waybillPackgeFee;
	}
	/**
	 *设置开单包装费waybillPackgeFee
	 */
	public void setWaybillPackgeFee(BigDecimal waybillPackgeFee) {
		this.waybillPackgeFee = waybillPackgeFee;
	}
	/**
	 * 获取是否盈利   profitStatus
	 * @return  profitStatus
	 */
	public String getProfitStatus() {
		return profitStatus;
	}
	
	/**
	 *设置是否盈利  profitStatus
	 */
	public void setProfitStatus(String profitStatus) {
		this.profitStatus = profitStatus;
	}
	   
	   
	   	
	   	
}