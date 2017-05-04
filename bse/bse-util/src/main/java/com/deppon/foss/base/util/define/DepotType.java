package com.deppon.foss.base.util.define;


/**
 * 
 * @ClassName: DepotType 
 * @Description: 仓库类型枚举
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午2:40:58 
 *
 */
public enum DepotType {
	DEPOT_TYPE_ONLINE("电商仓", 1), DEPOT_TYPE_SUPER("商超仓", 2), DEPOT_TYPE_CIQ(
			"海关仓", 3), DEPOT_TYPE_EXHIBITION("会展仓", 4), DEPOT_TYPE_COMMON(
			"普通仓", 5);

	private String name;

	private int index;

	private DepotType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (DepotType c : DepotType.values()) {
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
