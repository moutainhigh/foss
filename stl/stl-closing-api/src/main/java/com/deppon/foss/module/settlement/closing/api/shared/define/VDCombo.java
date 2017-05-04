package com.deppon.foss.module.settlement.closing.api.shared.define;

/**
 * 
 * 凭证报表Combo
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-1 下午6:11:13
 */
public class VDCombo {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 
	 * 构造函数
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-1 下午6:12:08
	 */
	public VDCombo(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	/**
	 * The Getter Method
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The Setter Method
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The Getter Method
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * The Setter Method
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * 判断是否相等
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-1 下午6:12:51
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof VDCombo)) {
			return false;
		}

		if (this.code == null || this.name == null) {
			return false;
		}

		VDCombo combo = (VDCombo) obj;

		return this.code.equals(combo.getCode())
				&& this.name.equals(combo.getName());
	}

	@Override
	public int hashCode() {
		return this.code.hashCode() + this.name.hashCode();
	}
}
