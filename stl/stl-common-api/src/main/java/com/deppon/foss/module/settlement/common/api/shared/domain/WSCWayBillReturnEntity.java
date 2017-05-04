/**
 * company   : com.deppon
 * poroject   : foss结算
 * copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : 潘士奇(309613)
 * @date     : 2016年2月18日 下午2:23:23
 * @version  : v1.0
 */
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description: 待刷卡运单管理非查询接口返回实体
 * @className: WSCWayBillReturnEntity
 * 
 * @authorCode 309613
 * @date 2016年2月18日 下午2:24:33
 * 
 */
public class WSCWayBillReturnEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 接口调用结果码{0-失败 1-成功 }
	 */
	private String resultCode;

	/**
	 * 接口调用结果描述
	 */
	private String resultDesc;

	/**
	 * 查询到的单条待刷卡运单数据
	 */
	private WSCWayBillEntity wscWayBillInfo;

	/**
	 * 查询到的待刷卡运单数据集合
	 */
	private List<WSCWayBillEntity> wscWayBillInfoList;

	/**
	 * 刷卡结果记录失败的运单号集合
	 */
	private List<String> failRecordWayBillNoList;

	/**
	 * 刷卡结果记录失败的错误信息
	 */
	private Map<String, String> failRecordMsgMap;

	/**
	 * 刷卡结果记录成功的运单号集合
	 */
	private List<String> succeedRecordWayBillNoList;

	/**  
	 * 获取 接口调用结果码{0-失败1-成功}  
	 * @return resultCode 接口调用结果码{0-失败1-成功}  
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**  
	 * 设置 接口调用结果码{0-失败1-成功}  
	 * @param resultCode 接口调用结果码{0-失败1-成功}  
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**  
	 * 获取 接口调用结果描述  
	 * @return resultDesc 接口调用结果描述  
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**  
	 * 设置 接口调用结果描述  
	 * @param resultDesc 接口调用结果描述  
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	/**  
	 * 获取 查询到的待刷卡运单数据集合  
	 * @return wscWayBillInfoList 查询到的待刷卡运单数据集合  
	 */
	public List<WSCWayBillEntity> getWscWayBillInfoList() {
		return wscWayBillInfoList;
	}

	/**  
	 * 设置 查询到的待刷卡运单数据集合  
	 * @param wscWayBillInfoList 查询到的待刷卡运单数据集合  
	 */
	public void setWscWayBillInfoList(List<WSCWayBillEntity> wscWayBillInfoList) {
		this.wscWayBillInfoList = wscWayBillInfoList;
	}

	/**  
	 * 获取 查询到的待刷卡运单数据  
	 * @return wscWayBillInfo 查询到的待刷卡运单数据  
	 */
	public WSCWayBillEntity getWscWayBillInfo() {
		return wscWayBillInfo;
	}

	/**  
	 * 设置 查询到的待刷卡运单数据  
	 * @param wscWayBillInfo 查询到的待刷卡运单数据  
	 */
	public void setWscWayBillInfo(WSCWayBillEntity wscWayBillInfo) {
		this.wscWayBillInfo = wscWayBillInfo;
	}

	/**  
	 * 获取 刷卡结果记录失败的运单号  
	 * @return failRecordWayBillNoList 刷卡结果记录失败的运单号  
	 */
	public List<String> getFailRecordWayBillNoList() {
		return failRecordWayBillNoList;
	}

	/**  
	 * 设置 刷卡结果记录失败的运单号  
	 * @param failRecordWayBillNoList 刷卡结果记录失败的运单号  
	 */
	public void setFailRecordWayBillNoList(List<String> failRecordWayBillNoList) {
		this.failRecordWayBillNoList = failRecordWayBillNoList;
	}

	/**  
	 * 获取 刷卡结果记录失败的失败信息  
	 * @return failRecordMsgMap 刷卡结果记录失败的失败信息  
	 */
	public Map<String, String> getFailRecordMsgMap() {
		return failRecordMsgMap;
	}

	/**  
	 * 设置 刷卡结果记录失败的失败信息  
	 * @param failRecordMsgMap 刷卡结果记录失败的失败信息  
	 */
	public void setFailRecordMsgMap(Map<String, String> failRecordMsgMap) {
		this.failRecordMsgMap = failRecordMsgMap;
	}

	/**  
	 * 获取 刷卡结果记录成功的运单号集合  
	 * @return succeedRecordWayBillNoList 刷卡结果记录成功的运单号集合  
	 */
	public List<String> getSucceedRecordWayBillNoList() {
		return succeedRecordWayBillNoList;
	}

	/**  
	 * 设置 刷卡结果记录成功的运单号集合  
	 * @param succeedRecordWayBillNoList 刷卡结果记录成功的运单号集合  
	 */
	public void setSucceedRecordWayBillNoList(List<String> succeedRecordWayBillNoList) {
		this.succeedRecordWayBillNoList = succeedRecordWayBillNoList;
	}
}
