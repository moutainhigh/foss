package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(快递返货工单上报  返回给pda实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:245960,date:2015-8-19 下午2:19:35,content:TODO </p>
 * @author 245960
 * @date 2015-8-19 下午2:19:35
 * @since
 * @version
 */
public class KdBackGoodsReportEntity implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	//请求返回状态 true：成功, false：失败
	private boolean responseFalg;

	/**
	 * @return  the responseFalg
	 */
	public boolean isResponseFalg() {
		return responseFalg;
	}

	/**
	 * @param responseFalg the responseFalg to set
	 */
	public void setResponseFalg(boolean responseFalg) {
		this.responseFalg = responseFalg;
	}
	
	


}
