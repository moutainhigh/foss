package com.deppon.foss.module.transfer.stock.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 库存迁移部门实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2014-12-22 下午2:30:28
*/
public class MoveGoodsDepartmentEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	* @fields id
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:24:50
	* @version V1.0
	*/
	private String id;
	/**
	 * 外键关联id
	 */
	private String T_OPT_MOVESTOCK_id;
	
	
	/**
	* @fields 货物类型
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:25:51
	* @version V1.0
	*/
	private Integer goods_type;
	
	
	/**
	* @fields 库存移出部门名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:07
	* @version V1.0
	*/
	private String moveout_name;
	
	
	/**
	* @fields 库存移出部门编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:23
	* @version V1.0
	*/
	private String moveout_code;
	
	/**
	* @fields 库存移出部门库区名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:27
	* @version V1.0
	*/
	private String moveout_areaname;
	
	/**
	* @fields 库存移出部门库区编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:30
	* @version V1.0
	*/
	private String moveout_areacode;
	
	/**
	* @fields movein_name
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:32
	* @version V1.0
	*/
	
	/**
	* @fields 库存移入部门名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:37
	* @version V1.0
	*/
	private String movein_name;
	
	/**
	* @fields 库存移入部门编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:41
	* @version V1.0
	*/
	private String movein_code;
	
	/**
	* @fields 库存移入部门库区名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:44
	* @version V1.0
	*/
	
	private String movein_areaname;
	
	/**
	* @fields 库存移入部门库区编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:48
	* @version V1.0
	*/
	private String movein_areacode;

	
	/**
	* @description 获取id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#getId()
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:52:19
	* @version V1.0
	*/
	public String getId() {
		return id;
	}

	
	/**
	* @description 设置id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:52:28
	* @version V1.0
	*/
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	* @description 获取外键关联id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:52:38
	*/
	public String getT_OPT_MOVESTOCK_id() {
		return T_OPT_MOVESTOCK_id;
	}

	
	/**
	* @description 设置外键关联id
	* @param t_OPT_MOVESTOCK_id
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:52:54
	*/
	public void setT_OPT_MOVESTOCK_id(String t_OPT_MOVESTOCK_id) {
		T_OPT_MOVESTOCK_id = t_OPT_MOVESTOCK_id;
	}

	
	/**
	* @description 获取货物类型
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:53:05
	*/
	public Integer getGoods_type() {
		return goods_type;
	}

	
	/**
	* @description 设置货物类型
	* @param goods_type
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:53:28
	*/
	public void setGoods_type(Integer goodsType) {
		this.goods_type = goodsType;
	}

	
	/**
	* @description 获取库存移出部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:53:38
	*/
	public String getMoveout_name() {
		return moveout_name;
	}

	
	/**
	* @description 设置库存移出部门名称
	* @param moveout_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:53:58
	*/
	public void setMoveout_name(String moveoutName) {
		this.moveout_name = moveoutName;
	}

	
	/**
	* @description 获取库存移出部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:54:07
	*/
	public String getMoveout_code() {
		return moveout_code;
	}

	
	/**
	* @description 设置库存移出部门code
	* @param moveout_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:54:17
	*/
	public void setMoveout_code(String moveoutCode) {
		this.moveout_code = moveoutCode;
	}

	
	/**
	* @description 获取库存移出部门库区名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:54:30
	*/
	public String getMoveout_areaname() {
		return moveout_areaname;
	}

	
	/**
	* @description 设置库存移出部门库区名称
	* @param moveout_areaname
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:55:25
	*/
	public void setMoveout_areaname(String moveoutAreaname) {
		this.moveout_areaname = moveoutAreaname;
	}

	
	/**
	* @description 获取库存移出部门库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:55:51
	*/
	public String getMoveout_areacode() {
		return moveout_areacode;
	}

	
	/**
	* @description 设置库存移出部门库区code
	* @param moveout_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:56:13
	*/
	public void setMoveout_areacode(String moveoutAreacode) {
		this.moveout_areacode = moveoutAreacode;
	}

	
	/**
	* @description 获取库存移入部门名字
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:56:39
	*/
	public String getMovein_name() {
		return movein_name;
	}

	
	/**
	* @description 设置库存移入部门名字
	* @param movein_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:56:58
	*/
	public void setMovein_name(String moveinName) {
		this.movein_name = moveinName;
	}

	
	/**
	* @description 获取库存移入部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:57:08
	*/
	public String getMovein_code() {
		return movein_code;
	}

	
	/**
	* @description 设置库存移入部门code
	* @param movein_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:57:18
	*/
	public void setMovein_code(String moveinCode) {
		this.movein_code = moveinCode;
	}

	
	/**
	* @description 获取库存移入部门库区名字
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:57:52
	*/
	public String getMovein_areaname() {
		return movein_areaname;
	}

	
	/**
	* @description 设置库存移入部门库区名字
	* @param movein_areaname
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:58:09
	*/
	public void setMovein_areaname(String moveinAreaname) {
		this.movein_areaname = moveinAreaname;
	}

	
	/**
	* @description 获取库存移入部门库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:58:26
	*/
	public String getMovein_areacode() {
		return movein_areacode;
	}

	
	/**
	* @description 设置库存移入部门库区code
	* @param movein_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:58:42
	*/
	public void setMovein_areacode(String moveinAreacode) {
		this.movein_areacode = moveinAreacode;
	}
	
	
}
