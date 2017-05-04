package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;

public class RookieWaybillJBDVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6023335069651244719L;
	/**
	 * <p>集包地实体</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	private RookieWaybillJBDEntity rookieWaybillJBDEntity;
	/**
	 * <p>集包地集合</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	private List<RookieWaybillJBDEntity> rookieWaybillJBDEntityList;
	/**
	 * <p>id的集合</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	private List<String> ids;
	/**
	 * <p>get set方法</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	public RookieWaybillJBDEntity getRookieWaybillJBDEntity() {
		return rookieWaybillJBDEntity;
	}

	public void setRookieWaybillJBDEntity(
			RookieWaybillJBDEntity rookieWaybillJBDEntity) {
		this.rookieWaybillJBDEntity = rookieWaybillJBDEntity;
	}

	public List<RookieWaybillJBDEntity> getRookieWaybillJBDEntityList() {
		return rookieWaybillJBDEntityList;
	}

	public void setRookieWaybillJBDEntityList(
			List<RookieWaybillJBDEntity> rookieWaybillJBDEntityList) {
		this.rookieWaybillJBDEntityList = rookieWaybillJBDEntityList;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	

}
