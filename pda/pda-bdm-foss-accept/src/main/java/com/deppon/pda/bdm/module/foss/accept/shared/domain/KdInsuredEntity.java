package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(快递保价返回实体 //author:245960 Date:2015-08-22 comment:骆敏霞需求获取  快递保价申明价值上限  1104   快递外发保价申明价值 1105)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:245960,date:2015-8-22 下午4:48:43,content:TODO </p>
 * @author 245960 yangdm
 * @date 2015-8-22 下午4:48:43
 * @since
 * @version
 */
public class KdInsuredEntity implements Serializable{
	/**
	 * TODO（序列化  生成唯一太卡 先来个1L）
	 */
	private static final long serialVersionUID = 1L;

	//快递保价申明价值上限  名称
	private String confName;
	
	//快递保价申明价值上限  值
	private String confValue;

	/**
	 * @return  the confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * @return  the confValue
	 */
	public String getConfValue() {
		return confValue;
	}

	/**
	 * @param confValue the confValue to set
	 */
	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}
	
	
}
