package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: GetBushCardSuccessEntity
 * @Description: TODO(获得待刷卡成功数据接收实体)
 * @author &268974 wangzhili
 * @date 2016-1-27 下午4:27:07
 * 
 */
public class GetBushCardSuccessEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 多条刷卡数据

	private List<GetBushCardEntitys> getBushCardEntitys;

	public List<GetBushCardEntitys> getGetBushCardEntitys() {
		return getBushCardEntitys;
	}

	public void setGetBushCardEntitys(List<GetBushCardEntitys> getBushCardEntitys) {
		this.getBushCardEntitys = getBushCardEntitys;
	}

	

}
