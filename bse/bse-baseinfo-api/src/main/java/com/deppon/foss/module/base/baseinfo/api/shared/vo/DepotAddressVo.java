package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;

/**
 * 
 * @ClassName: DepotAddressVo
 * @Description: 用来响应“进仓地址”的Action类的封装对象VO
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午2:44:22
 * 
 */
public class DepotAddressVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -3467090179709838830L;

	private DepotAddressEntity depotAddress;

	private List<DepotAddressEntity> DepotAddressEntitys;

	public List<DepotAddressEntity> getDepotAddressEntitys() {
		return DepotAddressEntitys;
	}

	public void setDepotAddressEntitys(
			List<DepotAddressEntity> depotAddressEntitys) {
		DepotAddressEntitys = depotAddressEntitys;
	}

	public DepotAddressEntity getDepotAddress() {
		return depotAddress;
	}

	public void setDepotAddress(DepotAddressEntity depotAddress) {
		this.depotAddress = depotAddress;
	}


}
