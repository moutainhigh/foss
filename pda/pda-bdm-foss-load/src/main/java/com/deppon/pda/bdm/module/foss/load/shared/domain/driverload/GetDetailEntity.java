package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.List;

import com.deppon.crm.module.client.sync.domain.dto.BaseEntity;

/**
 * 获取接货明细
 * @ClassName GetDetailEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-5-11
 */
public class GetDetailEntity  extends BaseEntity{
	
//接货明细
private List<GetDetailResult> getDetail;

public List<GetDetailResult> getGetDetail() {
	return getDetail;
}
public void setGetDetail(List<GetDetailResult> getDetail) {
	this.getDetail = getDetail;
}

}
