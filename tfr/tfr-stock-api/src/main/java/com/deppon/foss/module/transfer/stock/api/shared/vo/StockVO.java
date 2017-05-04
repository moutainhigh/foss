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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/vo/StockVO.java
 *  
 *  FILE NAME          :StockVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.vo;


import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
/**
 * 封装了库存查询及必走货查询界面与Action之间传递的参数
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:48:20
 */
public class StockVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 8965034778954777236L;
	
	/**
	 * 库区编号变更主表ID
	 */
	private String changeGoodsAreaEntityId;
	/**
	 * 库区编号变更的部门code
	 */
	private String changeGoodsAreaOrgCode;
	
	/**
	 * 判断运单是不是快递运单
	 */
	private boolean ifIsExpressWaybill;
	
	public boolean isIfIsExpressWaybill() {
		return ifIsExpressWaybill;
	}
	public void setIfIsExpressWaybill(boolean ifIsExpressWaybill) {
		this.ifIsExpressWaybill = ifIsExpressWaybill;
	}




	/**
	* @description 获取要修改库区的部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午11:23:23
	*/
	public String getChangeGoodsAreaOrgCode() {
		return changeGoodsAreaOrgCode;
	}



	
	/**
	* @description 设置要修改库区的部门code
	* @param changeGoodsAreaOrgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午11:23:51
	*/
	public void setChangeGoodsAreaOrgCode(String changeGoodsAreaOrgCode) {
		this.changeGoodsAreaOrgCode = changeGoodsAreaOrgCode;
	}

	/**
	 * 库区编号变更,用来接收前端传过来的string类型数组
	 */
	private List<String> StringList;
	
	/**
	* @description 获取新旧库区对应实体类集合
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月10日 上午9:46:20
	*/
	public List<String> getStringList() {
		return StringList;
	}

	
	/**
	* @description 设置新旧库区对应实体类集合
	* @param stringList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月10日 上午9:46:28
	*/
	public void setStringList(List<String> stringList) {
		StringList = stringList;
	}

	/**
	 * 库区编号变更,新旧库区对应实体类集合
	 */
	private List<NewAndOldGoodsAreaEntity> newAndOldGoodsAreaEntityList;
	
	
	/**
	* @description 获取新旧库区的list集合
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:42:44
	*/
	public List<NewAndOldGoodsAreaEntity> getNewAndOldGoodsAreaEntityList() {
		return newAndOldGoodsAreaEntityList;
	}


	
	/**
	* @description 设置新旧库区的list集合
	* @param newAndOldGoodsAreaEntityList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:42:47
	*/
	public void setNewAndOldGoodsAreaEntityList(
			List<NewAndOldGoodsAreaEntity> newAndOldGoodsAreaEntityList) {
		this.newAndOldGoodsAreaEntityList = newAndOldGoodsAreaEntityList;
	}


	/**
	* @description 获取库区编号变更主表ID
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午2:42:54
	*/
	public String getChangeGoodsAreaEntityId() {
		return changeGoodsAreaEntityId;
	}

	
	/**
	* @description 设置库区编号变更主表ID
	* @param changeGoodsAreaEntityId
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午2:43:09
	*/
	public void setChangeGoodsAreaEntityId(String changeGoodsAreaEntityId) {
		this.changeGoodsAreaEntityId = changeGoodsAreaEntityId;
	}


	/**
	 * 库区编号修改查询Dto
	 */
	private ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto;
	
	/**
	* @description 获取库区编号修改查询Dto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:17:46
	*/
	public ChangeGoodsAreaQueryDto getChangeGoodsAreaQueryDto() {
		return changeGoodsAreaQueryDto;
	}

	/**
	* @description 设置库区编号修改查询Dto
	* @param changeGoodsAreaQueryDto
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:18:03
	*/
	public void setChangeGoodsAreaQueryDto(
			ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto) {
		this.changeGoodsAreaQueryDto = changeGoodsAreaQueryDto;
	}
	/**
	 * 库区编号修改页面传的主表信息
	 */
	private ChangeGoodsAreaEntity changeGoodsAreaEntity;

	
	/**
	* @description 获取库区编号修改页面传的主表信息
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午2:54:45
	*/
	public ChangeGoodsAreaEntity getChangeGoodsAreaEntity() {
		return changeGoodsAreaEntity;
	}

	
	/**
	* @description 设置库区编号修改页面传的主表信息
	* @param changeGoodsAreaEntity
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午2:54:53
	*/
	public void setChangeGoodsAreaEntity(ChangeGoodsAreaEntity changeGoodsAreaEntity) {
		this.changeGoodsAreaEntity = changeGoodsAreaEntity;
	}

	/**
	 * 库区编号修改查询结果集
	 */
	private List<ChangeGoodsAreaEntity> changeGoodsAreaEntityList;
	
	/**
	* @description 获取库区编号修改查询结果集
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:18:12
	*/
	public List<ChangeGoodsAreaEntity> getChangeGoodsAreaEntityList() {
		return changeGoodsAreaEntityList;
	}


	
	/**
	* @description 设置库区编号修改查询结果集
	* @param changeGoodsAreaEntityList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:18:31
	*/
	public void setChangeGoodsAreaEntityList(
			List<ChangeGoodsAreaEntity> changeGoodsAreaEntityList) {
		this.changeGoodsAreaEntityList = changeGoodsAreaEntityList;
	}


	/**
	 * 库存迁移主表ID
	 */
	private String moveGoodsEntityId;

	/**
	 * 
	* @description 获取库存迁移主表ID
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 上午11:20:19
	 */
	public String getMoveGoodsEntityId() {
		return moveGoodsEntityId;
	}


	/**
	* @description 设置库存迁移主表ID
	* @param moveGoodsEntityId
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 上午11:20:24
	*/
	public void setMoveGoodsEntityId(String moveGoodsEntityId) {
		this.moveGoodsEntityId = moveGoodsEntityId;
	}


	/** 库存迁移表 与 库存迁移部门表 关联查询结果数据集DTO */
	private List<MoveGoodsStockQueryDto> moveGoodsStockQueryDtoList;
	/**
	 * 库存迁移表 与 库存迁移部门表 关联查询结果数据DTO
	 */
	private MoveGoodsStockQueryDto moveGoodsStockQueryDto;
	
	
	/**
	* @description 获取库存迁移表 与 库存迁移部门表 关联查询结果数据DTO
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:27:56
	*/
	public MoveGoodsStockQueryDto getMoveGoodsStockQueryDto() {
		return moveGoodsStockQueryDto;
	}


	
	/**
	* @description 设置库存迁移表 与 库存迁移部门表 关联查询结果数据DTO
	* @param moveGoodsStockQueryDto
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:28:13
	*/
	public void setMoveGoodsStockQueryDto(
			MoveGoodsStockQueryDto moveGoodsStockQueryDto) {
		this.moveGoodsStockQueryDto = moveGoodsStockQueryDto;
	}


	/**
	* @description 获取库存迁移表 与 库存迁移部门表 关联查询结果数据集
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-16 上午9:45:32
	*/
	public List<MoveGoodsStockQueryDto> getMoveGoodsStockQueryDtoList() {
		return moveGoodsStockQueryDtoList;
	}

	
	/**
	* @description 设置库存迁移表 与 库存迁移部门表 关联查询结果数据集
	* @param moveGoodsEntityList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-16 上午9:45:35
	*/
	public void setMoveGoodsStockQueryDtoList(List<MoveGoodsStockQueryDto> moveGoodsStockQueryDtoList) {
		this.moveGoodsStockQueryDtoList = moveGoodsStockQueryDtoList;
	}
	
	
	/**库存迁移查询DTO*/
	private MoveGoodsStockDto moveGoodsStockDto;

	/**
	* @description  获取库存迁移查询DTO
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-13 下午2:24:39
	*/
	public MoveGoodsStockDto getMoveGoodsStockDto() {
		return moveGoodsStockDto;
	}

	
	/**
	* @description 设置库存迁移查询DTO
	* @param moveGoodsStockDto
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-13 下午2:24:42
	*/
	public void setMoveGoodsStockDto(MoveGoodsStockDto moveGoodsStockDto) {
		this.moveGoodsStockDto = moveGoodsStockDto;
	}
//	/**审核状态List*/
//	private String stateList;
//	
//	
//	/**
//	 * @return 审核状态
//	 */
//	public String getStateList() {
//		return stateList;
//	}
//
//
//	/**设置审核状态
//	 * @param stateList
//	 */
//	public void setStateList(String stateList) {
//		this.stateList = stateList;
//	}
	/** 流水号*/
	private String serialNOs;
	/** 运单号*/
	private String waybillNOs;
	/** 起始入库时间*/
	private Date beginInStockTime;
	/** 结束是否入库*/
	private Date endInStockTime;
	/** 库存实体*/
	private StockEntity stockEntity;
	/** 运单库存实体*/
	private WaybillStockEntity waybillStockEntity;
	/** 出入库实体*/
	private InOutStockEntity inOutStockEntity;
	/** 运单库存List*/
	private List<WaybillStockQueryDto> waybillStockList;
	/** 货件库存List*/
	private List<StockEntity> stockList;
	/** 出库实体List*/
	private List<InOutStockEntity> outStockList;
	/** id拼 串*/
	private String ids;
	/** 运单库存查询DTO*/
	private WaybillStockQueryDto waybillStockQueryDto;
	/** 确认是否修改走货路径*/
	private String confirmFlag;
	/** 确认是否入库*/
	private String inStockConfirmFlag;
	/** 产品List*/
	private List<BaseDataDictDto> productList;
	/** 特殊货区List*/
	private List<BaseDataDictDto> specialAreaList;
	/** 库位List*/
	private List<BaseDataDictDto> positionList;
	/** 外场对应的货区List*/
	private List<BaseDataDictDto> areaByOrgcodeList;
	/** 库存部门编号*/
	private String stockOrgCode;
	/** 库存部门名称*/
	private String stockOrgName;
	/** 是否是外场*/
	private String isTransferCenter;
	/** 是否是驻地派送部*/
	private String isStationDelivery;
	/** 是否是空运总调*/
	private String isAirDispatch;
	/** 货区编号*/
	private String goodsAreaCode;
	/** 货区名称*/
	private String goodsAreaName;
	/** 异常编码*/
	private String exceptionCode;
	/** 查询界面统计栏信息*/
	private WaybillStockStatisticsDto waybillStockStatisticsDto;
	/** 入库实体List*/
	private List<InOutStockEntity> inStockList;
	/**库位*/
	private String position;
	
	
	
	/**
	 * 获取 起始入库时间.
	 *
	 * @return the 起始入库时间
	 */
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}

	/**
	 * 设置 起始入库时间.
	 *
	 * @param beginInStockTime the new 起始入库时间
	 */
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	
	/**
	 * 获取 结束是否入库.
	 *
	 * @return the 结束是否入库
	 */
	public Date getEndInStockTime() {
		return endInStockTime;
	}

	/**
	 * 设置 结束是否入库.
	 *
	 * @param endInStockTime the new 结束是否入库
	 */
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}

	/**
	 * 获取 库存实体.
	 *
	 * @return the 库存实体
	 */
	public StockEntity getStockEntity() {
		return stockEntity;
	}

	/**
	 * 设置 库存实体.
	 *
	 * @param stockEntity the new 库存实体
	 */
	public void setStockEntity(StockEntity stockEntity) {
		this.stockEntity = stockEntity;
	}

	/**
	 * 获取 运单库存List.
	 *
	 * @return the 运单库存List
	 */
	public List<WaybillStockQueryDto> getWaybillStockList() {
		return waybillStockList;
	}

	/**
	 * 设置 运单库存List.
	 *
	 * @param waybillStockList the new 运单库存List
	 */
	public void setWaybillStockList(List<WaybillStockQueryDto> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

	/**
	 * 获取 货件库存List.
	 *
	 * @return the 货件库存List
	 */
	public List<StockEntity> getStockList() {
		return stockList;
	}

	/**
	 * 设置 货件库存List.
	 *
	 * @param stockList the new 货件库存List
	 */
	public void setStockList(List<StockEntity> stockList) {
		this.stockList = stockList;
	}

	/**
	 * 获取 运单库存实体.
	 *
	 * @return the 运单库存实体
	 */
	public WaybillStockEntity getWaybillStockEntity() {
		return waybillStockEntity;
	}

	/**
	 * 设置 运单库存实体.
	 *
	 * @param waybillStockEntity the new 运单库存实体
	 */
	public void setWaybillStockEntity(WaybillStockEntity waybillStockEntity) {
		this.waybillStockEntity = waybillStockEntity;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNOs() {
		return serialNOs;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNOs the new 流水号
	 */
	public void setSerialNOs(String serialNOs) {
		this.serialNOs = serialNOs;
	}

	/**
	 * 获取 出入库实体.
	 *
	 * @return the 出入库实体
	 */
	public InOutStockEntity getInOutStockEntity() {
		return inOutStockEntity;
	}

	/**
	 * 设置 出入库实体.
	 *
	 * @param inOutStockEntity the new 出入库实体
	 */
	public void setInOutStockEntity(InOutStockEntity inOutStockEntity) {
		this.inOutStockEntity = inOutStockEntity;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNOs() {
		return waybillNOs;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNOs the new 运单号
	 */
	public void setWaybillNOs(String waybillNOs) {
		this.waybillNOs = waybillNOs;
	}

	/**
	 * 获取 出库实体List.
	 *
	 * @return the 出库实体List
	 */
	public List<InOutStockEntity> getOutStockList() {
		return outStockList;
	}

	/**
	 * 设置 出库实体List.
	 *
	 * @param outStockList the new 出库实体List
	 */
	public void setOutStockList(List<InOutStockEntity> outStockList) {
		this.outStockList = outStockList;
	}

	/**
	 * 获取 id拼 串.
	 *
	 * @return the id拼 串
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * 设置 id拼 串.
	 *
	 * @param ids the new id拼 串
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 获取 运单库存查询DTO.
	 *
	 * @return the 运单库存查询DTO
	 */
	public WaybillStockQueryDto getWaybillStockQueryDto() {
		return waybillStockQueryDto;
	}

	/**
	 * 设置 运单库存查询DTO.
	 *
	 * @param waybillStockQueryDto the new 运单库存查询DTO
	 */
	public void setWaybillStockQueryDto(WaybillStockQueryDto waybillStockQueryDto) {
		this.waybillStockQueryDto = waybillStockQueryDto;
	}

	/**
	 * 获取 确认是否修改走货路径.
	 *
	 * @return the 确认是否修改走货路径
	 */
	public String getConfirmFlag() {
		return confirmFlag;
	}

	/**
	 * 设置 确认是否修改走货路径.
	 *
	 * @param confirmFlag the new 确认是否修改走货路径
	 */
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	
	/**
	 * 获取 产品List.
	 *
	 * @return the 产品List
	 */
	public List<BaseDataDictDto> getProductList() {
		return productList;
	}

	/**
	 * 设置 产品List.
	 *
	 * @param productList the new 产品List
	 */
	public void setProductList(List<BaseDataDictDto> productList) {
		this.productList = productList;
	}

	/**
	 * 获取 特殊货区List.
	 *
	 * @return the 特殊货区List
	 */
	public List<BaseDataDictDto> getSpecialAreaList() {
		return specialAreaList;
	}

	/**
	 * 设置 特殊货区List.
	 *
	 * @param specialAreaList the new 特殊货区List
	 */
	public void setSpecialAreaList(List<BaseDataDictDto> specialAreaList) {
		this.specialAreaList = specialAreaList;
	}

	/**
	 * 获取 库存部门编号.
	 *
	 * @return the 库存部门编号
	 */
	public String getStockOrgCode() {
		return stockOrgCode;
	}

	/**
	 * 设置 库存部门编号.
	 *
	 * @param stockOrgCode the new 库存部门编号
	 */
	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	/**
	 * 获取 是否是外场.
	 *
	 * @return the 是否是外场
	 */
	public String getIsTransferCenter() {
		return isTransferCenter;
	}

	/**
	 * 设置 是否是外场.
	 *
	 * @param isTransferCenter the new 是否是外场
	 */
	public void setIsTransferCenter(String isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
	}

	/**
	 * 获取 异常编码.
	 *
	 * @return the 异常编码
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * 设置 异常编码.
	 *
	 * @param exceptionCode the new 异常编码
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * 获取 确认是否入库.
	 *
	 * @return the 确认是否入库
	 */
	public String getInStockConfirmFlag() {
		return inStockConfirmFlag;
	}

	/**
	 * 设置 确认是否入库.
	 *
	 * @param inStockConfirmFlag the new 确认是否入库
	 */
	public void setInStockConfirmFlag(String inStockConfirmFlag) {
		this.inStockConfirmFlag = inStockConfirmFlag;
	}

	/**
	 * 获取 查询界面统计栏信息.
	 *
	 * @return the 查询界面统计栏信息
	 */
	public WaybillStockStatisticsDto getWaybillStockStatisticsDto() {
		return waybillStockStatisticsDto;
	}

	/**
	 * 设置 查询界面统计栏信息.
	 *
	 * @param waybillStockStatisticsDto the new 查询界面统计栏信息
	 */
	public void setWaybillStockStatisticsDto(
			WaybillStockStatisticsDto waybillStockStatisticsDto) {
		this.waybillStockStatisticsDto = waybillStockStatisticsDto;
	}

	/**
	 * 获取 库存部门名称.
	 *
	 * @return the 库存部门名称
	 */
	public String getStockOrgName() {
		return stockOrgName;
	}

	/**
	 * 设置 库存部门名称.
	 *
	 * @param stockOrgName the new 库存部门名称
	 */
	public void setStockOrgName(String stockOrgName) {
		this.stockOrgName = stockOrgName;
	}

	/**
	 * 获取 是否是驻地派送部.
	 *
	 * @return the 是否是驻地派送部
	 */
	public String getIsStationDelivery() {
		return isStationDelivery;
	}

	/**
	 * 设置 是否是驻地派送部.
	 *
	 * @param isStationDelivery the new 是否是驻地派送部
	 */
	public void setIsStationDelivery(String isStationDelivery) {
		this.isStationDelivery = isStationDelivery;
	}

	/**
	 * 获取 是否是空运总调.
	 *
	 * @return the 是否是空运总调
	 */
	public String getIsAirDispatch() {
		return isAirDispatch;
	}

	/**
	 * 设置 是否是空运总调.
	 *
	 * @param isAirDispatch the new 是否是空运总调
	 */
	public void setIsAirDispatch(String isAirDispatch) {
		this.isAirDispatch = isAirDispatch;
	}

	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * 获取 货区名称.
	 *
	 * @return the 货区名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}

	/**
	 * 设置 货区名称.
	 *
	 * @param goodsAreaName the new 货区名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	/**
	 * 获取 入库实体List.
	 *
	 * @return the 入库实体List
	 */
	public List<InOutStockEntity> getInStockList() {
		return inStockList;
	}

	/**
	 * 设置 入库实体List.
	 *
	 * @param inStockList the new 入库实体List
	 */
	public void setInStockList(List<InOutStockEntity> inStockList) {
		this.inStockList = inStockList;
	}

	/**
	* @return
	* @description  获取库位
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午9:56:37
	*/
	public String getPosition() {
		return position;
	}

	/**
	* @param position 设置库位
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午9:56:54
	*/
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	* @return
	* @description 获取库位集合
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:18:54
	*/
	public List<BaseDataDictDto> getPositionList() {
		return positionList;
	}

	/**
	* @param positionList
	* @description 设置库位集合
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:19:21
	*/
	public void setPositionList(List<BaseDataDictDto> positionList) {
		this.positionList = positionList;
	}

	/**
	* @return
	* @description  获取外场对应的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-9 上午11:10:08
	*/
	public List<BaseDataDictDto> getAreaByOrgcodeList() {
		return areaByOrgcodeList;
	}

	/**
	* @param areaByOrgcodeList
	* @description 设置外场对应的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-9 上午11:10:18
	*/
	public void setAreaByOrgcodeList(List<BaseDataDictDto> areaByOrgcodeList) {
		this.areaByOrgcodeList = areaByOrgcodeList;
	}
	
}