package com.deppon.foss.module.pickup.common.client.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomerOperaLoggerDto implements Serializable{
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log LOG = LogFactory.getLog(CustomerOperaLoggerDto.class);
	
	//菜单名称
	private String menuName;
	
	private String waybillNo;
	
	//表头说明
	private String schameInfo;
//	private Boolean submitSuccess = false;
	
	//是提交类型or 提交并进行下一单
	private String submitBtnName;
	
	//通用提交方法
	private Map<String, String> mapInfos = new HashMap<String, String>();
	
	private Map<String, Long> mapTimes = new HashMap<String, Long>();
	
	private static final String START="Start";
	
	private static final String END="End";
	
	
	/****************************************************目的站*******************************/
	//加载目的站、提货网点、查询时效 、走货线路开始...
	private Long loadMdzDataStart;
	
	//加载目的站、提货网点、查询时效 、走货线路结束...
	private Long loadMdzDataEnd;
	
	//目的费率和其他计算开始...
	private Long mdzFeeCalculateStart;
	
	//目的费率和其他计算结束...
	private Long mdzFeeCalculateEnd;
	
	/****************************************************计算总运费*******************************/
	//计算总运费按钮开始...
	private Long jszyfBtnStart;
	
	//计算总运费按钮结束...
	private Long jszyfBtnEnd;
	
	/****************************************************提交按钮*******************************/
	//提交开始...
	private Long submitStart;
	
	//提交结束...
	private Long submitEnd;
	
	/****************************************************提交暂存*******************************/
	//暂存开始时间
//	private Long temSubmitSaveStart;
	
	//暂存结束时间
//	private Long temSubmitSaveEnd;
	
	/**
	 * 时间模式
	 */
	private static final String PATTERN="yyyy-MM-dd HH:mm:ss:SSS";
	
	private String converseCurrentTimeToDateString(Long currentTime){
		if(currentTime == null){
			return "";
		}
		SimpleDateFormat  f = new SimpleDateFormat(PATTERN);
		return f.format(new Date(currentTime));
	}
	
	//	获取当前时间
	private Long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public void setSubmitBtnName(String submitBtnName) {
		this.submitBtnName = submitBtnName;
	}
	
	private String getMenuName(){
		return menuName == null ? "" : menuName;
	}

	public void submitSuccess(){
//		submitSuccess = true;
		//所有开单都打印日志
		//if("图片开单".equals(menuName)){
			LOG.info( this.toString());
		//}
	}
	
	/****************************************************提交按钮*******************************/
	public void submitStart() {
		this.submitStart = getCurrentTimeMillis();
	}

	public void submitEnd() {
		this.submitEnd = getCurrentTimeMillis();
	}
	/****************************************************目的站*******************************/
	
	public void loadMdzDataStart(){
		this.loadMdzDataStart = getCurrentTimeMillis();
	}
	
	public void loadMdzDataEnd(){
		this.loadMdzDataEnd = getCurrentTimeMillis();
	}
	
	public void mdzFeeCalculateStart(){
		this.mdzFeeCalculateStart = getCurrentTimeMillis();
	}
	public void mdzFeeCalculateEnd(){
		this.mdzFeeCalculateEnd = getCurrentTimeMillis();
	}
	
	/****************************************************计算总运费*******************************/
	
	public void jszyfBtnStart(){
		this.jszyfBtnStart = getCurrentTimeMillis();
	}
	
	public void jszyfBtnEnd(){
		this.jszyfBtnEnd = getCurrentTimeMillis();
	}
	
	
	
	
	
	/****************************************************暂存提交*******************************/
//	public void temSubmitSaveStart(){
//		this.temSubmitSaveStart = getCurrentTimeMillis();
//	}
//	
//	public void temSubmitSaveEnd(){
//		this.temSubmitSaveEnd = getCurrentTimeMillis();
//	}
	
	/****************************************************通用设置方法*******************************/
	
	public String getSchameInfo() {
		return schameInfo;
	}

	public void setSchameInfo(String schameInfo) {
		this.schameInfo = schameInfo;
	}

	/**
	 * 
	 * @param key(已Start、End结束  否则不记录日志信息)       key只要唯一  
	 * @param textInfo  日志输入的信息
	 */
	public void setInfo(String key, String textInfo){
		if(key.endsWith("end") || key.endsWith("End") || key.endsWith("END")){
			String newKey  = key.substring(0, key.length()-END.length())+END;
			mapInfos.put(newKey, textInfo);
			mapTimes.put(newKey, getCurrentTimeMillis());
		}else if(key.endsWith("start") || key.endsWith("Start") || key.endsWith("START")){
			String newKey  = key.substring(0, key.length()-START.length())+START;
			mapInfos.put(newKey, textInfo);
			mapTimes.put(newKey, getCurrentTimeMillis());
		}
	}		
	
	/**
	 * 获取目的站信息
	 */
	private String gainMdzInfo(){
		StringBuffer sb = new StringBuffer();
		if(loadMdzDataStart !=null && loadMdzDataEnd !=null){
			sb.append("加载目的站、提货网点、查询时效 、走货线路开始时间:").append(converseCurrentTimeToDateString(loadMdzDataStart)).append(",");
			sb.append("加载目的站、提货网点、查询时效 、走货线路结束时间:").append(converseCurrentTimeToDateString(loadMdzDataEnd)).append(", 耗时:").append(loadMdzDataEnd-loadMdzDataStart).append("; \t");
		}
		if(mdzFeeCalculateStart!=null && mdzFeeCalculateEnd!=null){
			sb.append("目的费率和其他计算开始时间:").append(converseCurrentTimeToDateString(mdzFeeCalculateStart)).append(", ");
			sb.append("目的费率和其他计算结束时间:").append(converseCurrentTimeToDateString(mdzFeeCalculateEnd)).append(", 耗时:").append(mdzFeeCalculateEnd-mdzFeeCalculateStart).append("; \t");
		}
		return sb.toString();
	}
	
	/**
	 * 获取计算总运费信息
	 */
	private String gainJszyfInfo(){
		StringBuffer sb = new StringBuffer();
		if(jszyfBtnStart !=null && jszyfBtnEnd !=null){
			sb.append("点击计算总运费按钮开始时间:").append(converseCurrentTimeToDateString(jszyfBtnStart)).append(", ");
			sb.append("点击计算总运费按钮结束时间:").append(converseCurrentTimeToDateString(jszyfBtnEnd)).append(", 耗时:").append(jszyfBtnEnd-jszyfBtnStart).append("; \t");
		}
		return sb.toString();
	}

	/**
	 * 获取提交按钮信息
	 */
	private String gainSubmitInfo(){
		StringBuffer sb = new StringBuffer();
		if(submitStart !=null && submitEnd !=null){
			sb.append(""+submitBtnName).append("开始时间:").append(converseCurrentTimeToDateString(submitStart)).append(", ");
			sb.append(""+submitBtnName).append("结束时间:").append(converseCurrentTimeToDateString(submitEnd)).append(", 耗时:").append(submitEnd-submitStart).append("; \t");
		}
		return sb.toString();
	}
	
	/**
	 * 获取暂存信息
	 */
//	private String gainTemSaveInfo(){
//		StringBuffer sb = new StringBuffer();
//		if(temSubmitSaveStart !=null && temSubmitSaveEnd !=null){
//			sb.append("暂存开始时间:").append(converseCurrentTimeToDateString(temSubmitSaveStart)).append(", ");
//			sb.append("暂存结束时间:").append(converseCurrentTimeToDateString(temSubmitSaveEnd)).append(", 耗时:").append(temSubmitSaveEnd-temSubmitSaveStart).append("; \t");
//		}
//		return sb.toString();
//	}
	
	//获取通用提交方法信息
	private String getMapInfos(){
		StringBuffer buffer = new StringBuffer();
		Iterator<String> it =mapInfos.keySet().iterator();
		while(it.hasNext()){
			String endKey = it.next();
			if(endKey!=null && endKey.endsWith(END)){
				String startKey  = endKey.substring(0, endKey.length()-END.length())+START;
				String startInfo = mapInfos.get(startKey);
				String endInfo = mapInfos.get(endKey);
				Long startTime = mapTimes.get(startKey);
				Long endTime = mapTimes.get(endKey);
				if(endTime !=null && startTime!=null){
					buffer.append(startInfo).append(" ").append(converseCurrentTimeToDateString(startTime)).append(", ");
					buffer.append(endInfo).append(" ").append(converseCurrentTimeToDateString(endTime)).append(", 耗时:").append(endTime-startTime).append("; \t");
				}
			}
			
		}
		
		return buffer.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getMenuName());
		sb.append(" 开单成功,耗时记录{");
		sb.append(" 运单号：").append(getWaybillNo()).append("; ");
		sb.append(getSchameInfo()).append(" ");
		sb.append("[");
		
		//获取目的站信息
		sb.append(gainMdzInfo());
		
		//计算总运费信息
		sb.append(gainJszyfInfo());
		
		//提交信息
		sb.append(gainSubmitInfo());
		
		//获取暂存时间
//		sb.append(gainTemSaveInfo());
		
		//通用时间
		sb.append(getMapInfos());
		
//		sb.append("开始时间:").append(converseCurrentTimeToDateString(submitConfirmStart)).append(", ");
//		sb.append("提交确认结束时间:").append(converseCurrentTimeToDateString(submitConfirmEnd)).append(" 总耗时:").append(submitEnd-submitStart).append("; \t");
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
}
