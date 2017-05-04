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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/NoLabelGoodsVo.java
 * 
 *  FILE NAME          :NoLabelGoodsVo.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
/**
 * 封装了无标签多货界面参数
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:10:15
 */
public class NoLabelGoodsVo implements java.io.Serializable {

	private static final long serialVersionUID = -4534603882452014527L;
	/** 无标签货物*/
	private NoLabelGoodsEntity noLabelGoodsEntity;
	/** 无标签货物List*/
	private List<NoLabelGoodsEntity> noLabelGoodsList;
	/** 货物标签打印操作信息*/
	private PrintLabelDto printLabelDto;
	/** 货物标签打印操作信息List*/
	private List<PrintLabelDto> printLabelDtoList;
	/** 流水号List*/
	private List<String> serialNoList;
	/** 货物标签打印内容信息*/
	private List<BarcodePrintLabelDto> barcodePrintDtoList;
	/** 上分拣和标签打印实体类 */
	private SortingAndPringLabelDto sortingAndPringLabelDto;
	/** 是否是第一外场*/
	private String isFirstTransfer = "N";
	/**上分拣和标签打印实体类List */
	private List<SortingAndPringLabelDto> SortingAndPringLabelList;
	/** id拼 串*/
	private String ids;
	
	
	/**
	* @description 获取ids拼串
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:39:56
	*/
	public String getIds() {
		return ids;
	}

	
	/**
	* @description 设置ids拼串
	* @param ids
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:40:10
	*/
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	* @description 获取上分拣和标签打印实体类List
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午9:33:10
	*/
	public List<SortingAndPringLabelDto> getSortingAndPringLabelList() {
		return SortingAndPringLabelList;
	}

	/**
	* @description 设置上分拣和标签打印实体类List
	* @param sortingAndPringLabelList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午9:33:41
	*/
	public void setSortingAndPringLabelList(
			List<SortingAndPringLabelDto> sortingAndPringLabelList) {
		SortingAndPringLabelList = sortingAndPringLabelList;
	}



	/**
	* @description 获取是否是第一外场
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月7日 下午5:01:03
	*/
	public String getIsFirstTransfer() {
		return isFirstTransfer;
	}


	
	/**
	* @description 设置是否是第一外场
	* @param isFirstTransfer
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月7日 下午5:01:23
	*/
	public void setIsFirstTransfer(String isFirstTransfer) {
		this.isFirstTransfer = isFirstTransfer;
	}


	/**
	* @description 获取上分拣和标签打印实体类 
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:49:00
	*/
	public SortingAndPringLabelDto getSortingAndPringLabelDto() {
		return sortingAndPringLabelDto;
	}

	
	/**
	* @description 设置上分拣和标签打印实体类 
	* @param sortingAndPringLabelDto
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:49:12
	*/
	public void setSortingAndPringLabelDto(
			SortingAndPringLabelDto sortingAndPringLabelDto) {
		this.sortingAndPringLabelDto = sortingAndPringLabelDto;
	}


	/**
	 * 电子运单
	* @fields wallbillElectronList
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午11:03:32
	* @version V1.0
	*/
	private List<EWaybillPrintDto> wallbillElectronList;
	/** 当前大部门编号*/
	private String bigOrgCode;
	/** 当前大部门名称*/
	private String bigOrgName;
	/** 用户工号*/
	private String userCode;
	/** 用户密码*/
	private String password;
	/** 用户校验是否通过*/
	private String isVerify;
	
	
	/**
	 * 是否电子运单
	* @fields wallbillElectron
	* @author 14022-foss-songjie
	* @update 2014年8月27日 上午9:28:35
	* @version V1.0
	*/
	private String wallbillElectron;
	
	
	/**
	 * 申请人
	 */
	private String userName;
	
	/**
	 * 申请弃货的事由
	 */
	private String toAbandonNotes;
	
	/**
	 * 上报oa的时长
	 */
	private String toOaTime;
	
	/**
	 * 获取上报oa的时长
	 * @return
	 */
	public String getToOaTime() {
		return toOaTime;
	}

	/**
	 * 设置上报oa的时长
	 * @param toOaTime
	 */
	public void setToOaTime(String toOaTime) {
		this.toOaTime = toOaTime;
	}

	/**
	 * 获取申请人
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置申请人
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取申请弃货的事由
	 * @return
	 */
	public String getToAbandonNotes() {
		return toAbandonNotes;
	}

	/**
	 * 设置申请弃货的事由
	 * @param toAbandonNotes
	 */
	public void setToAbandonNotes(String toAbandonNotes) {
		this.toAbandonNotes = toAbandonNotes;
	}


	/**
	 * 获取 无标签货物.
	 *
	 * @return the 无标签货物
	 */
	public NoLabelGoodsEntity getNoLabelGoodsEntity() {
		return noLabelGoodsEntity;
	}

	/**
	 * 设置 无标签货物.
	 *
	 * @param noLabelGoodsEntity the new 无标签货物
	 */
	public void setNoLabelGoodsEntity(NoLabelGoodsEntity noLabelGoodsEntity) {
		this.noLabelGoodsEntity = noLabelGoodsEntity;
	}

	/**
	 * 获取 无标签货物List.
	 *
	 * @return the 无标签货物List
	 */
	public List<NoLabelGoodsEntity> getNoLabelGoodsList() {
		return noLabelGoodsList;
	}

	/**
	 * 设置 无标签货物List.
	 *
	 * @param noLabelGoodsList the new 无标签货物List
	 */
	public void setNoLabelGoodsList(List<NoLabelGoodsEntity> noLabelGoodsList) {
		this.noLabelGoodsList = noLabelGoodsList;
	}

	/**
	 * 获取 货物标签打印操作信息.
	 *
	 * @return the 货物标签打印操作信息
	 */
	public PrintLabelDto getPrintLabelDto() {
		return printLabelDto;
	}

	/**
	 * 设置 货物标签打印操作信息.
	 *
	 * @param printLabelDto the new 货物标签打印操作信息
	 */
	public void setPrintLabelDto(PrintLabelDto printLabelDto) {
		this.printLabelDto = printLabelDto;
	}

	/**
	 * 获取 货物标签打印操作信息List.
	 *
	 * @return the 货物标签打印操作信息List
	 */
	public List<PrintLabelDto> getPrintLabelDtoList() {
		return printLabelDtoList;
	}

	/**
	 * 设置 货物标签打印操作信息List.
	 *
	 * @param printLabelDtoList the new 货物标签打印操作信息List
	 */
	public void setPrintLabelDtoList(List<PrintLabelDto> printLabelDtoList) {
		this.printLabelDtoList = printLabelDtoList;
	}

	/**
	 * 获取 流水号List.
	 *
	 * @return the 流水号List
	 */
	public List<String> getSerialNoList() {
		return serialNoList;
	}

	/**
	 * 设置 流水号List.
	 *
	 * @param serialNoList the new 流水号List
	 */
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

	

	public List<BarcodePrintLabelDto> getBarcodePrintDtoList() {
		return barcodePrintDtoList;
	}

	public void setBarcodePrintDtoList(
			List<BarcodePrintLabelDto> barcodePrintDtoList) {
		this.barcodePrintDtoList = barcodePrintDtoList;
	}

	/**
	 * 获取 当前大部门编号.
	 *
	 * @return the 当前大部门编号
	 */
	public String getBigOrgCode() {
		return bigOrgCode;
	}

	/**
	 * 设置 当前大部门编号.
	 *
	 * @param bigOrgCode the new 当前大部门编号
	 */
	public void setBigOrgCode(String bigOrgCode) {
		this.bigOrgCode = bigOrgCode;
	}
	
	/**
	 * 获取 当前大部门名称.
	 *
	 * @return the 当前大部门名称
	 */
	public String getBigOrgName() {
		return bigOrgName;
	}
	
	/**
	 * 设置 当前大部门名称.
	 *
	 * @param bigOrgName the new 当前大部门名称
	 */
	public void setBigOrgName(String bigOrgName) {
		this.bigOrgName = bigOrgName;
	}
	
	/**
	 * 获取 用户工号.
	 *
	 * @return the 用户工号
	 */
	public String getUserCode() {
		return userCode;
	}
	
	/**
	 * 设置 用户工号.
	 *
	 * @param userCode the new 用户工号
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	/**
	 * 获取 用户密码.
	 *
	 * @return the 用户密码
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置 用户密码.
	 *
	 * @param password the new 用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取 用户校验是否通过.
	 *
	 * @return the 用户校验是否通过
	 */
	public String getIsVerify() {
		return isVerify;
	}

	/**
	 * 设置 用户校验是否通过.
	 *
	 * @param isVerify the new 用户校验是否通过
	 */
	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}

	
	/**
	* @description 是否电子运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年8月27日 上午9:29:31
	*/
	public String getWallbillElectron() {
		return wallbillElectron;
	}

	
	/**
	* @description 是否电子运单
	* @param wallbillElectron
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年8月27日 上午9:29:57
	*/
	public void setWallbillElectron(String wallbillElectron) {
		this.wallbillElectron = wallbillElectron;
	}

	
	/**
	* @description 电子运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午11:04:01
	*/
	public List<EWaybillPrintDto> getWallbillElectronList() {
		return wallbillElectronList;
	}

	
	/**
	* @description 电子运单
	* @param wallbillElectronList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午11:04:08
	*/
	public void setWallbillElectronList(List<EWaybillPrintDto> wallbillElectronList) {
		this.wallbillElectronList = wallbillElectronList;
	}
	
	
	
}