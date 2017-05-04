package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;
/**
 * @author niuly
 * @function 打印代理面单Vo
 * @date 2014年9月5日上午11:20:30
 */
public class PrintAgentWaybillVo {
	//交接单号
	private String handOverBillNos;
	//运单号
	private String waybillNos;
	//dto
	private PrintAgentWaybillDto dto;
	//list
	private List<PrintAgentWaybillEntity> list;
	
	private List<PrintAgentWaybillRecordEntity> listRecordEntities ;//获取营业部绑定运单信息
	//代理单号
	private String agentWaybillNos;
	//代理公司编号
	private String agentCompanyCodes;
	//外发费
	private String frightFees;
	//运单号list
	private List<String> waybillNoList;
		//插入总条数
	private int importTotalCount;
	public String getHandOverBillNos() {
		return handOverBillNos;
	}

	public void setHandOverBillNos(String handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}

	public String getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	public PrintAgentWaybillDto getDto() {
		return dto;
	}

	public void setDto(PrintAgentWaybillDto dto) {
		this.dto = dto;
	}

	public List<PrintAgentWaybillEntity> getList() {
		return list;
	}

	public void setList(List<PrintAgentWaybillEntity> list) {
		this.list = list;
	}

	public List<PrintAgentWaybillRecordEntity> getListRecordEntities() {
		return listRecordEntities;
	}

	public void setListRecordEntities(
			List<PrintAgentWaybillRecordEntity> listRecordEntities) {
		this.listRecordEntities = listRecordEntities;
	}

	public String getAgentWaybillNos() {
		return agentWaybillNos;
	}

	public void setAgentWaybillNos(String agentWaybillNos) {
		this.agentWaybillNos = agentWaybillNos;
	}

	public String getAgentCompanyCodes() {
		return agentCompanyCodes;
	}

	public void setAgentCompanyCodes(String agentCompanyCodes) {
		this.agentCompanyCodes = agentCompanyCodes;
	}

	public String getFrightFees() {
		return frightFees;
	}

	public void setFrightFees(String frightFees) {
		this.frightFees = frightFees;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	
	public int getImportTotalCount() {
		return importTotalCount;
	}

	public void setImportTotalCount(int importTotalCount) {
		this.importTotalCount = importTotalCount;
	}
}
