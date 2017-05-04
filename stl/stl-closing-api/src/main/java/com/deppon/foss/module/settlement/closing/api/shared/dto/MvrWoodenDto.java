package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;


public class MvrWoodenDto extends MvrWoodenEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4778110996377843977L;

	/** 总条数. */
	private int count;
	/**
	 * 列表
	 */
	private List<MvrWoodenEntity>  list = new ArrayList<MvrWoodenEntity>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<MvrWoodenEntity> getList() {
		return list;
	}

	public void setList(List<MvrWoodenEntity> list) {
		this.list = list;
	}

}
