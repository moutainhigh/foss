package com.deppon.foss.module.transfer.platform.server.action;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.StockSaturationConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.StockSaturationVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;


/**
* @description 仓库饱和度Action
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月28日 下午3:42:03
*/
public class StockSaturationAction extends AbstractAction  {

	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:43:04
	* @version V1.0
	*/
	private static final long serialVersionUID = 1L;
	
	
	/**
	* @fields stockSaturationVo
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:47:43
	* @version V1.0
	*/
	private StockSaturationVo stockSaturationVo = new StockSaturationVo();
	
	
	/**
	 * 接受页面传参(部门code)
	* @fields currentCode
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午2:49:28
	* @version V1.0
	*/
	private String currentCode;
	
	
	/**
	 * (部门Name)
	* @fields currentName
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午3:25:28
	* @version V1.0
	*/
	private String currentName;
	
	
	/**
	* @fields stockSaturationService
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:48:40
	* @version V1.0
	*/
	private IStockSaturationService stockSaturationService;
	
	/**
	 * 导出Excel 文件流
	* @fields excelStream
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:14:20
	* @version V1.0
	*/
	transient InputStream excelStream;
	
	/**
	 * 导出Excel 文件名
	* @fields fileName
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:14:24
	* @version V1.0
	*/
	private String fileName;
	
	private final Logger LOGGER = LoggerFactory.getLogger(StockSaturationAction.class);
	
	public String stockSaturationQueryIndex(){
		//返回服务器时间及上级转运场信息，其中上级外场信息可能为空
		String[] outfieldInfo = stockSaturationService.queryOutfieldInfo();
		if(outfieldInfo != null){
			stockSaturationVo.setOutfieldCode(outfieldInfo[0]);
			stockSaturationVo.setOutfieldName(outfieldInfo[1]);
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 仓库饱和度明细初始化页面
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午3:26:55
	*/
	public String stockSaturationDetailQueryIndex(){
		//返回服务器时间及上级转运场信息，其中上级外场信息可能为空
		String[] outfieldInfo = stockSaturationService.queryOutfieldInfo(currentCode);
		if(outfieldInfo != null){
			this.stockSaturationVo.setOutfieldCode(outfieldInfo[0]);
			this.stockSaturationVo.setOutfieldName(outfieldInfo[1]);
		}
		if(StringUtils.isNotBlank(currentName)){
			//将currentName（系统用户本身的code） 赋值给currentCode
			this.currentCode=this.currentName;
		}else{
			this.currentCode=null;
		}
		return SUCCESS;
	}
	
	
	
	/**
	* @description 仓库饱和度查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:19:06
	*/
	@JSON
	public String queryStockSaturation(){
		try {
			// 获取外场编码
			StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			List<StockSaturationEntity> list = stockSaturationService.queryStockSaturationList(stockSaturationVo.getStockSaturationEntity(), start, limit);
			stockSaturationVo.setStockSaturationList(list);
			this.setTotalCount(stockSaturationService.queryStockSaturationListCount(dto));
			
			return SUCCESS;
			
		} catch (TfrBusinessException e) {

			return returnError(e); 
		}
	}
	
	
	/**
	* @description 仓库饱和度明细查询(日)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:20:01
	*/
	@JSON
	public String queryStockSaturationDetailDay(){
		// 获取外场编码
		StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getOrgCode())){
				return returnError("请选择外场");
			}
			if(StringUtils.isEmpty(dto.getBeginDate())){
				return returnError("请输入开始日期");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			
			int checkFlag = checkDayTime(dto);
			if(checkFlag==0){
				return returnError("截至日期超出最大时间范围");
			}else if(checkFlag==1){
				return returnError("超出查询时间范围");
			}
			
		List<StockSaturationEntity> list = stockSaturationService.queryStockSaturationDayList(dto);
		stockSaturationVo.setStockSaturationList(list);
		return SUCCESS;
	}
	
	
	
	/**
	* @description 仓库饱和度明细查询(月)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:21:49
	*/
	@JSON
	public String queryStockSaturationDetailMonth(){
		// 获取外场编码
		StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getOrgCode())){
				return returnError("请选择外场");
			}
			if(StringUtils.isEmpty(dto.getBeginDate())){
				return returnError("请输入开始日期");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			int checkFlag = checkMothTime(dto);
			if(checkFlag==0){
				return returnError("截至日期超出最大时间范围");
			}else if(checkFlag==1){
				return returnError("超出查询时间范围");
			}
		List<StockSaturationEntity> list = stockSaturationService.queryStockSaturationMonthList(dto);
		
		stockSaturationVo.setStockSaturationList(list);
		
		return SUCCESS;
	}
	
	
	
	/**
	* @description 仓库饱和度导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:22:58
	*/
	@JSON
	public String stockSaturationExport(){
		try{
			// 获取外场编码
			StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			dto.setBeginDate(beginTimeByEndtime(dto.getEndDate()));
			try {
				fileName = this.encodeFileName(StockSaturationConstants.STOCK_SATURATION_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("StockSaturationAction.stockSaturationExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = stockSaturationService.exportStockSaturationList(dto);
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	
	/**
	* @description   日仓库饱和度导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:23:56
	*/
	@JSON
	public String stockSaturationDetailDayExport(){
		try{
			// 获取外场编码
			StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getOrgCode())){
				return returnError("请选择外场");
			}
			if(StringUtils.isEmpty(dto.getBeginDate())){
				return returnError("请输入开始日期");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			
			int checkFlag = checkDayTime(dto);
			if(checkFlag==0){
				return returnError("截至日期超出最大时间范围");
			}else if(checkFlag==1){
				return returnError("超出查询时间范围");
			}
			
			try {
				fileName = this.encodeFileName(StockSaturationConstants.STOCK_SATURATION_DAY_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("StockSaturationAction.stockSaturationDetailDayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = stockSaturationService.exportStockSaturationDayList(dto);
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	
	/**
	* @description 月仓库饱和度导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:24:09
	*/
	@JSON
	public String stockSaturationDetailMonthExport(){
		try{
			// 获取外场编码
			StockSaturationEntity dto = stockSaturationVo.getStockSaturationEntity();
			if (dto == null) {
				dto = new StockSaturationEntity();
				dto.setOrgCode("");
			}
			if(StringUtils.isEmpty(dto.getOrgCode())){
				return returnError("请选择外场");
			}
			if(StringUtils.isEmpty(dto.getBeginDate())){
				return returnError("请输入开始日期");
			}
			if(StringUtils.isEmpty(dto.getEndDate())){
				return returnError("请输入截至日期");
			}
			int checkFlag = checkMothTime(dto);
			if(checkFlag==0){
				return returnError("截至日期超出最大时间范围");
			}else if(checkFlag==1){
				return returnError("超出查询时间范围");
			}
			
			try {
				fileName = this.encodeFileName(StockSaturationConstants.STOCK_SATURATION_MONTH_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("StockSaturationAction.stockSaturationDetailMonthExport  报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = stockSaturationService.exportStockSaturationMonthList(dto);
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	/**
	* @description stockSaturationVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:49:11
	*/
	public StockSaturationVo getStockSaturationVo() {
		return stockSaturationVo;
	}


	
	/**
	* @description stockSaturationVo
	* @param stockSaturationVo
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:49:14
	*/
	public void setStockSaturationVo(StockSaturationVo stockSaturationVo) {
		this.stockSaturationVo = stockSaturationVo;
	}


	
	/**
	* @description 仓库饱和度Service
	* @param stockSaturationService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:49:21
	*/
	public void setStockSaturationService(
			IStockSaturationService stockSaturationService) {
		this.stockSaturationService = stockSaturationService;
	}
	
	/**
	* @description 根据截至时间获取开始时间
	* @param endTime
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午2:18:58
	*/
	public String beginTimeByEndtime(String endTime){
		if(endTime!=null && endTime.length()>=PlatformConstants.SONAR_NUMBER_10){
			String endTimeSub = endTime.substring(PlatformConstants.SONAR_NUMBER_0,PlatformConstants.SONAR_NUMBER_8); 
			String beginTime = endTimeSub+"01";
			return beginTime;
		}else{
			return "";
		}
	}

	
	/**
	* @description 接受页面传参
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午2:49:54
	*/
	public String getCurrentCode() {
		return currentCode;
	}

	
	/**
	* @description 接受页面传参
	* @param currentCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午2:49:59
	*/
	public void setCurrentCode(String currentCode) {
		this.currentCode = currentCode;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}
	/**
	 * @description 获取Excel文件名
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月4日 下午2:18:13
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @description 设置Excel文件名
	 * @param fileName
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月4日 下午2:18:13
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @description 获取导出工作流
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月4日 下午2:18:13
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	
	
	/**
	 * 转换导出文件的文件名
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description 转换导出文件的文件名
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年3月6日 下午4:10:40
	 */
	public String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}
	
	
	/**
	* @description 检验月 （页面只输入年月，并且时间范围是12个月，后台做校验，给用户提示）
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午5:26:33
	*/
	public int checkMothTime(StockSaturationEntity dto){
		int backStr = -1;
		//系统当前时间的年和月
		String sysTime = DateUtils.convert(new Date(),DateUtils.DATE_SHORT_FORMAT).substring(PlatformConstants.SONAR_NUMBER_0,PlatformConstants.SONAR_NUMBER_6);
		long sysMonthNumber = Long.parseLong(sysTime);
		String beginMonth = dto.getBeginDate();
		String beginMonthLong = beginMonth.replace("-", "");
		
		String endMonth = dto.getEndDate();
		String endMonthLong = endMonth.replace("-", "");
		long endMonthNumber = Long.parseLong(endMonthLong);
		
		/**
		 * 当前时间的年月 减去 用户选择的截至日期 的年月 (时间差)
		 */
		long diffMonth = endMonthNumber-sysMonthNumber;
		int diffMonthInt = Integer.parseInt(diffMonth+"");
		//截至日期超出范围
		if(diffMonthInt>PlatformConstants.SONAR_NUMBER_0){
			backStr = PlatformConstants.SONAR_NUMBER_0;
		}
		//用户选择的开始时间的 年
		int yearBegin=Integer.parseInt(beginMonthLong.substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_4));
		//用户选择的开始时间的 月
		int monthBegin =Integer.parseInt(beginMonthLong.substring(PlatformConstants.SONAR_NUMBER_4, PlatformConstants.SONAR_NUMBER_6));
		//用户选择的截至时间的 年
		int yearEnd= Integer.parseInt(endMonthLong.substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_4));
		//用户选择的截至时间的 月
		int monthEnd=Integer.parseInt(endMonthLong.substring(PlatformConstants.SONAR_NUMBER_4, PlatformConstants.SONAR_NUMBER_6));
		
		//年做比较
		int diffYear = yearEnd-yearBegin;
		//最大12个月 符合条件 不做处理
		if(diffYear!=PlatformConstants.SONAR_NUMBER_0){
			if(diffYear==1){
				//超过了12个月，给用户提示
				if(monthEnd>monthBegin){
					backStr = PlatformConstants.SONAR_NUMBER_1;
				}
			}else{
				//跨度超过12个月
				backStr = PlatformConstants.SONAR_NUMBER_1;
			}
		}
		return backStr;
	}
	
	
	/**
	* @description 检验日
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午5:33:20
	*/
	public int checkDayTime(StockSaturationEntity dto){
		int backStr = -1;
		Date tempBeginDate = DateUtils.convert(dto.getBeginDate(), DateUtils.DATE_FORMAT);
		Date tempEndDate = DateUtils.convert(dto.getEndDate(), DateUtils.DATE_FORMAT);
		Date currentDate = DateUtils.convert(DateUtils.convert(new Date()), DateUtils.DATE_FORMAT);
		long diffDay = DateUtils.getTimeDiff(currentDate, tempEndDate);
		int diffDayInt = Integer.parseInt(diffDay+"");
		
		long diffDay2 = DateUtils.getTimeDiff(tempBeginDate, tempEndDate);
		int diffDayInt2 = Integer.parseInt(diffDay2+"");
		 //判断当前时间是否大于12点
		//大于中午12点
		if(stockSaturationService.decideTimeTwelve()){
			//允许截至日期=当前日期+1天("截至日期超出最大时间范围");
			if(diffDayInt>PlatformConstants.SONAR_NUMBER_1){
				backStr = PlatformConstants.SONAR_NUMBER_0;
			}
		}else{
			//允许截至日期=当前日期("截至日期超出最大时间范围");
			if(diffDayInt>PlatformConstants.SONAR_NUMBER_0){
				backStr = PlatformConstants.SONAR_NUMBER_0;
			}
		}
		//returnError("超出查询时间范围")
		if(diffDayInt2>PlatformConstants.SONAR_NUMBER_31){
			backStr = PlatformConstants.SONAR_NUMBER_1;
		}
		return backStr;
	}

	
	
}
