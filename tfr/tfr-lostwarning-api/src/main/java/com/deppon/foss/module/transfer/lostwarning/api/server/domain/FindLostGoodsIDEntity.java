package com.deppon.foss.module.transfer.lostwarning.api.server.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 丢货找到实体
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：FindLostGoodsEntity
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-8-7 下午3:00:00
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class FindLostGoodsIDEntity implements Serializable {
	
	private static final long serialVersionUID = 495178437201252784L;

	//上报的丢货的ID集合
	private List<String> idList;

	//丢货编号
	private String lostRepCode;
	
	//找到场景(数字)
	private String findScene;
	
	//丢货状态(1:未找到 2:已找到)
	private	String loseStatus;
	
	//操作时间
	private String dealTime;
	
	//操作内容
	private String dealContent;
	
	//FOSS系统自动跟进惟一ID
	private	String sysAutoTrackId;
	
	private List<FindLostGoodsSerialEntity> serialList;

	public String getLostRepCode() {
		return lostRepCode;
	}

	public void setLostRepCode(String lostRepCode) {
		this.lostRepCode = lostRepCode;
	}

	public String getFindScene() {
		return findScene;
	}

	public void setFindScene(String findScene) {
		this.findScene = findScene;
	}

	public String getLoseStatus() {
		return loseStatus;
	}

	public void setLoseStatus(String loseStatus) {
		this.loseStatus = loseStatus;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealContent() {
		return dealContent;
	}

	public void setDealContent(String dealContent) {
		this.dealContent = dealContent;
	}

	public String getSysAutoTrackId() {
		return sysAutoTrackId;
	}

	public void setSysAutoTrackId(String sysAutoTrackId) {
		this.sysAutoTrackId = sysAutoTrackId;
	}

	public List<FindLostGoodsSerialEntity> getSerialList() {
		return serialList;
	}

	public void setSerialList(List<FindLostGoodsSerialEntity> serialList) {
		this.serialList = serialList;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

}
