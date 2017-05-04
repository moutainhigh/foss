package com.deppon.foss.base.util.define;

/**
 * 
* @ClassName: DepotState 
* @Description: 仓库状态枚举
* @author 310854-liuzhenhua
* @date 2016-8-17 下午4:36:29 
*
 */
public enum DepotState {
	DEPOT_STATE_SUBMIT("已提交", 1), DEPOT_STATE_CONFIRM("已确认", 2), DEPOT_STATE_BACK(
			"已退回", 3), DEPOT_STATE_DELETE("已作废", 4);

	private String name;

	private int index;

	private DepotState(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (DepotState c : DepotState.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
