package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;

/**
 * 快递分部营业部映射关系Vo
 * @author foss-WeiXing
 * @date 2014-9-22 上午9:50:12
 */
public class ExpressBranchSalesDeptVo {
	//快递分部和营业部映射实体
	private ExpressBranchSalesDeptEntity expressBranchSalesDeptEntity;
	//快递分部和营业部映射列表
	private List<ExpressBranchSalesDeptEntity> expressBranchSalesList;
	//idList,批量作废时用的
	private List<String> idList;
	//新增集合
	private List<ExpressBranchSalesDeptEntity> addExpressBranchSalesList;
	//作废集合
	private List<ExpressBranchSalesDeptEntity> deleteExpressBranchSalesList;
	
	public ExpressBranchSalesDeptEntity getExpressBranchSalesDeptEntity() {
		return expressBranchSalesDeptEntity;
	}
	public void setExpressBranchSalesDeptEntity(
			ExpressBranchSalesDeptEntity expressBranchSalesDeptEntity) {
		this.expressBranchSalesDeptEntity = expressBranchSalesDeptEntity;
	}
	public List<ExpressBranchSalesDeptEntity> getExpressBranchSalesList() {
		return expressBranchSalesList;
	}
	public void setExpressBranchSalesList(
			List<ExpressBranchSalesDeptEntity> expressBranchSalesList) {
		this.expressBranchSalesList = expressBranchSalesList;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public List<ExpressBranchSalesDeptEntity> getAddExpressBranchSalesList() {
		return addExpressBranchSalesList;
	}
	public void setAddExpressBranchSalesList(
			List<ExpressBranchSalesDeptEntity> addExpressBranchSalesList) {
		this.addExpressBranchSalesList = addExpressBranchSalesList;
	}
	public List<ExpressBranchSalesDeptEntity> getDeleteExpressBranchSalesList() {
		return deleteExpressBranchSalesList;
	}
	public void setDeleteExpressBranchSalesList(
			List<ExpressBranchSalesDeptEntity> deleteExpressBranchSalesList) {
		this.deleteExpressBranchSalesList = deleteExpressBranchSalesList;
	}
	
}
