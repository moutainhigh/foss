package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.CancelPackageResultDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSaveConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;

/** 
 * @className: ExpressPackageVo
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包Vo类
 * @date: 2013-7-18 下午3:29:00
 * 
 */
public class ExpressPackageVo {
	
	/**
	 * 查询实体
	 */
	private ExpressPackageQueryDto queryPackageDto;
	
	/**
	 * 包查询结果
	 */
	private List<ExpressPackageEntity> packageList;
	
	/**
	 * 包号list
	 */
	private List<String> packageNoList;
	
	/**
	 * 批量撤销包处理结果list
	 */
	private List<CancelPackageResultDto> resultList;
	
	/**
	 * 包号
	 * */
	private String packageNo;
	
	/**
	 * 目的站
	 * */
	private String destOrgName;
	
	/**
	 * 目的站编码
	 * */
	private String destOrgCode;
	
	/**
	 * 查询包交接单 dto
	 **/
	private QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto;
	
	/**
	 * 库存运单
	 */
	private ExpressPackageWayBillDetailDto waybillStock;
	
	/**
	 * 库存运单list
	 */
	private List<ExpressPackageWayBillDetailDto> waybillStockList;
	
	/**
	 * 新增快递包Dto
	 */
	private ExpressPackageSaveConditionDto expressPackageSaveConditionDto;
	/**
	 * 运单库存流水号
	 */
	private List<ExpressPackageSerialNoStockDto> serialNoStockList;
	

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public List<CancelPackageResultDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<CancelPackageResultDto> resultList) {
		this.resultList = resultList;
	}

	public List<String> getPackageNoList() {
		return packageNoList;
	}

	public void setPackageNoList(List<String> packageNoList) {
		this.packageNoList = packageNoList;
	}

	public ExpressPackageQueryDto getQueryPackageDto() {
		return queryPackageDto;
	}

	public void setQueryPackageDto(ExpressPackageQueryDto queryPackageDto) {
		this.queryPackageDto = queryPackageDto;
	}

	public List<ExpressPackageEntity> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<ExpressPackageEntity> packageList) {
		this.packageList = packageList;
	}

	public QueryWaybillForExpressPackageConditionDto getQueryWaybillForExpressPackageDto() {
		return queryWaybillForExpressPackageDto;
	}

	public void setQueryWaybillForExpressPackageDto(
			QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto) {
		this.queryWaybillForExpressPackageDto = queryWaybillForExpressPackageDto;
	}

	public ExpressPackageWayBillDetailDto getWaybillStock() {
		return waybillStock;
	}

	public void setWaybillStock(ExpressPackageWayBillDetailDto waybillStock) {
		this.waybillStock = waybillStock;
	}

	public List<ExpressPackageSerialNoStockDto> getSerialNoStockList() {
		return serialNoStockList;
	}

	public void setSerialNoStockList(List<ExpressPackageSerialNoStockDto> serialNoStockList) {
		this.serialNoStockList = serialNoStockList;
	}

	public List<ExpressPackageWayBillDetailDto> getWaybillStockList() {
		return waybillStockList;
	}

	public void setWaybillStockList(
			List<ExpressPackageWayBillDetailDto> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

	public ExpressPackageSaveConditionDto getExpressPackageSaveConditionDto() {
		return expressPackageSaveConditionDto;
	}

	public void setExpressPackageSaveConditionDto(
			ExpressPackageSaveConditionDto expressPackageSaveConditionDto) {
		this.expressPackageSaveConditionDto = expressPackageSaveConditionDto;
	}

	
}
