package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;

/** 
 * @ClassName: DeliverGoodsAreaQueryVo 
 * @Description: 派送情况查询Vo类
 * @author shiwei shiwei@outlook.com
 * @date 2014年2月28日 下午4:05:17 
 *  
 */
public class DeliverGoodsAreaQueryVo implements java.io.Serializable{
	
	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午2:09:13
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用于获取服务端时间
	 */
	private String serverTimeString;
	
	/**
	 * 传回给前台的外场code
	 */
	private String outfieldCode;
	
	/**
	 * 是否营业分析员
	 */
	private String isAnalyst;
	
	/**
	 * 外场编码
	 */
	private String queryOrgCode;
	
	/**
	 * 查询日期
	 */
	private Date queryDate;
	
	/**
	 * 派送率实体
	* @fields sendRateEntity
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:36:32
	* @version V1.0
	*/
	private SendRateEntity sendRateEntity;
	
	
	/**
	 * 派送率结果集
	* @fields sendRateList
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:45:11
	* @version V1.0
	*/
	private List<SendRateEntity> sendRateList;
	
	
	/**
	 * 退单率实体
	* @fields returnRateEntity
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:36:34
	* @version V1.0
	*/
	private ReturnRateEntity returnRateEntity;
	
	
	/**
	 * 退单率实体结果集
	* @fields returnRateList
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:46:02
	* @version V1.0
	*/
	private List<ReturnRateEntity> returnRateList;
	
	
	/**
	 * 拉回率实体
	* @fields pullbackRateEntity
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:36:36
	* @version V1.0
	*/
	private PullbackRateEntity pullbackRateEntity;
	
	
	/**
	 * 拉回率实体结果集
	* @fields pullbackRateEntityList
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:46:57
	* @version V1.0
	*/
	private List<PullbackRateEntity> pullbackRateEntityList;
	
	/**
	 * 传回给前台的外场name
	 */
	private String outfieldName;
	
	/**
	 * 票均装车时长实体
	* @fields waybillAvgTimeEntity
	* @author 218427-foss-hongwy
	* @update 2015年3月23日 上午9:29:57
	* @version V1.0
	*/
	private WaybillAvgTimeEntity waybillAvgTimeEntity;
	
	/**
	 * 票均装车时长实体集合
	* @fields waybillAvgTimeEntityList
	* @author 218427-foss-hongwy
	* @update 2015年3月23日 上午9:40:57
	* @version V1.0
	*/
	private List<WaybillAvgTimeEntity> waybillAvgTimeEntityList;
	
	
	
	
	/** 
	 * @return outfieldCode 
	 */
	public String getOutfieldCode() {
		return outfieldCode;
	}

	/** 
	 * @param outfieldCode 要设置的 outfieldCode 
	 */
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	/** 
	 * @return outfieldName 
	 */
	public String getOutfieldName() {
		return outfieldName;
	}

	/** 
	 * @param outfieldName 要设置的 outfieldName 
	 */
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}

	/** 
	 * @return serverTimeString 
	 */
	public String getServerTimeString() {
		return serverTimeString;
	}

	/** 
	 * @param serverTimeString 要设置的 serverTimeString 
	 */
	public void setServerTimeString(String serverTimeString) {
		this.serverTimeString = serverTimeString;
	}

	
	/**
	* @description 获取 sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:18
	*/
	public SendRateEntity getSendRateEntity() {
		return sendRateEntity;
	}

	
	/**
	* @description 设置 sendRateEntity
	* @param sendRateEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:29
	*/
	public void setSendRateEntity(SendRateEntity sendRateEntity) {
		this.sendRateEntity = sendRateEntity;
	}

	
	/**
	* @description 获取 ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:34
	*/
	public ReturnRateEntity getReturnRateEntity() {
		return returnRateEntity;
	}

	
	/**
	* @description 设置 ReturnRateEntity
	* @param returnRateEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:38
	*/
	public void setReturnRateEntity(ReturnRateEntity returnRateEntity) {
		this.returnRateEntity = returnRateEntity;
	}

	
	/**
	* @description 获取PullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:40
	*/
	public PullbackRateEntity getPullbackRateEntity() {
		return pullbackRateEntity;
	}

	
	/**
	* @description 设置PullbackRateEntity
	* @param pullbackRateEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:37:42
	*/
	public void setPullbackRateEntity(PullbackRateEntity pullbackRateEntity) {
		this.pullbackRateEntity = pullbackRateEntity;
	}

	
	/**
	* @description 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:47:28
	*/
	public List<SendRateEntity> getSendRateList() {
		return sendRateList;
	}

	
	/**
	* @description 
	* @param sendRateList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:47:34
	*/
	public void setSendRateList(List<SendRateEntity> sendRateList) {
		this.sendRateList = sendRateList;
	}

	
	/**
	* @description 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:47:40
	*/
	public List<ReturnRateEntity> getReturnRateList() {
		return returnRateList;
	}

	
	/**
	* @description 
	* @param returnRateList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:47:47
	*/
	public void setReturnRateList(List<ReturnRateEntity> returnRateList) {
		this.returnRateList = returnRateList;
	}

	
	/**
	* @description 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:47:59
	*/
	public List<PullbackRateEntity> getPullbackRateEntityList() {
		return pullbackRateEntityList;
	}

	
	/**
	* @description 
	* @param pullbackRateEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:48:03
	*/
	public void setPullbackRateEntityList(
			List<PullbackRateEntity> pullbackRateEntityList) {
		this.pullbackRateEntityList = pullbackRateEntityList;
	}

	public WaybillAvgTimeEntity getWaybillAvgTimeEntity() {
		return waybillAvgTimeEntity;
	}

	public void setWaybillAvgTimeEntity(WaybillAvgTimeEntity waybillAvgTimeEntity) {
		this.waybillAvgTimeEntity = waybillAvgTimeEntity;
	}

	public List<WaybillAvgTimeEntity> getWaybillAvgTimeEntityList() {
		return waybillAvgTimeEntityList;
	}

	public void setWaybillAvgTimeEntityList(
			List<WaybillAvgTimeEntity> waybillAvgTimeEntityList) {
		this.waybillAvgTimeEntityList = waybillAvgTimeEntityList;
	}
   
	public String getIsAnalyst() {
		return isAnalyst;
	}

	public void setIsAnalyst(String isAnalyst) {
		this.isAnalyst = isAnalyst;
	}

	public String getQueryOrgCode() {
		return queryOrgCode;
	}

	public void setQueryOrgCode(String queryOrgCode) {
		this.queryOrgCode = queryOrgCode;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	
}
