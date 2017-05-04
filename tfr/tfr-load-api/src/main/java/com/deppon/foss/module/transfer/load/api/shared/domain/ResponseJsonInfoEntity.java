package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;


/**
 * 
 * 类描述：	作为服务端返回json结果信息给客户端
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-04-26 上午10:13:39
 * 
 */
public class ResponseJsonInfoEntity implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 成功状态："0"- 标示失败
	 */
	public static final String FAIL="0";
	public static final String ZERONUMBERTEXTINFO="奔溃,操作失败!";
	/**
	 * 成功状态："1"- 标示成功
	 */
	public static final String SUCCESS="1";
	public static final String ONENUMBERTEXTINFO="给力,操作成功!";
	/**
	 * 成功状态："2"-标示无效参数
	 */
	public static final String EXCEPTION="2";
	public static final String SECONDTEXTINFO="JSON无效参数,请核实!";
	/**
	 * 成功状态："3"-标示JSON解析异常
	 */
	public static final String JSONRESOLVEEXCEPTION="3";
	public static final String THREETEXTINFO="JSON解析异常,请核实!";
	/**
	 * 成功状态："4"-标示JSON格式异常
	 */
	public static final String JSONFORMATEXCEPTION="4";
	public static final String FOURTEXTINFO="JSON格式异常,请核实!";
	/**
	 * 成功状态："5"-标示判空
	 */
	public static final String NULLEXCEPTION="5";
	public static final String FIVETEXTINFO="JSON参数不能为空,请核实!";
	/**
	 * 成功状态："6"-标示封签号已校验
	 */
	public static final String SEALNUMCHECKED="6";
	public static final String SIXTEXTINFO="给力,封签号已校验!";
	/**
	 * 成功状态："7"-标示封签号不存在
	 */
	public static final String SEALNUMISNOTEXIST="7";
	public static final String SEVENTEXTINFO="奔溃,封签号不存在!";
	/**
	 * 成功状态："8"-标示车牌号不存在
	 */
	public static final String CARNUMISNOTEXIST="8";
	public static final String EIGHTTEXTINFO="奔溃,车牌号不存在!";
	/**
	 * 成功状态："9"-标示其他异常
	 */
	public static final String OTHEREXCEPTION="9";
	public static final String NINETEXTINFO="奔溃,其他异常!";
	
	
	/**
	 * 成功状态："0"- 标示成功,"1"- 标示失败,"2"-标示异常,"3"-牌号是否输入错误或车牌号不属于当前部门
	 */
	private String state;
	
	/**
	 * 返回给客户端的信息
	 */
	private String returnInfo;

	public ResponseJsonInfoEntity(String state, String returnInfo) {
		super();
		this.state = state;
		this.returnInfo = returnInfo;
	}

	
	@Override
	public String toString() {
		return "ResponseJsonInfoEntity [state=" + state + ", returnInfo="
				+ returnInfo + "]";
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	

}
