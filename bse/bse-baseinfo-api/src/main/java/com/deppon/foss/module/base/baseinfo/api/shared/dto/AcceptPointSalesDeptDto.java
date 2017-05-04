package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
/**
 * 接驳点对应营业部关系DTO
 * @author 132599 ShenWeiHua
 *
 * @date 2015-4-14 上午11:02:49
 */
public class AcceptPointSalesDeptDto  implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 接驳点主干类型返回结果集
	 */
	private List<AcceptPointSalesDeptEntity> acceptPointEntitys;
	
	/**
	 * 接驳点主干类型返回结果集
	 */
	private List<AcceptPointSalesChildrenDeptEntity> childAcceptPointEntitys;
	
	/**
	 * 接驳点主干
	 */
	private AcceptPointSalesDeptEntity acceptPointEntity;
	
	/**
     * 接驳点映射信息ID
     */
    private String id;
    
    /**
     * 接驳点映射信息ID集合
     */
    private List<String> idList;
    
    /**
     * 接驳点映射状态信息
     */
    private String status;
    
    /**
     * 营业区编码
     */
    private String smallRegion;
    
    /**
     * 大区编码
     */
    private String bigRegion;
    
    /**
     * 营业区code
     */
    private String smallRegionName;
    
    /**
     * 接驳点编码
     */
    private String acceptPointCode;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}

	public List<AcceptPointSalesDeptEntity> getAcceptPointEntitys() {
		return acceptPointEntitys;
	}

	public void setAcceptPointEntitys(
			List<AcceptPointSalesDeptEntity> acceptPointEntitys) {
		this.acceptPointEntitys = acceptPointEntitys;
	}

	public AcceptPointSalesDeptEntity getAcceptPointEntity() {
		return acceptPointEntity;
	}

	public void setAcceptPointEntity(AcceptPointSalesDeptEntity acceptPointEntity) {
		this.acceptPointEntity = acceptPointEntity;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public List<AcceptPointSalesChildrenDeptEntity> getChildAcceptPointEntitys() {
		return childAcceptPointEntitys;
	}

	public void setChildAcceptPointEntitys(
			List<AcceptPointSalesChildrenDeptEntity> childAcceptPointEntitys) {
		this.childAcceptPointEntitys = childAcceptPointEntitys;
	}

	public String getAcceptPointCode() {
		return acceptPointCode;
	}

	public void setAcceptPointCode(String acceptPointCode) {
		this.acceptPointCode = acceptPointCode;
	}
	
	
}
