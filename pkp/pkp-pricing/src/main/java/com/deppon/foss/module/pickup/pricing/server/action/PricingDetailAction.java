package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.pdf.PDFExport;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PDFPriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTableHeadEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PartnerPriceTableVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceTableVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.SettlementConstants;

public class PricingDetailAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 价格服务
	 */
	private IPriceTableService priceTableService;
	
	public void setPriceTableService(IPriceTableService priceTableService) {
		this.priceTableService = priceTableService;
	}

	/**
	 * 提示内容服务
	 */
	IPriceReportTitleService priceReportTitleService;
	
	public void setPriceReportTitleService(
			IPriceReportTitleService priceReportTitleService) {
		this.priceReportTitleService = priceReportTitleService;
	}

	/**
	 * 声明导出pdf名称
	 */
	private static final String PDFNAME = "汽运价格表";
	 /**
     * 汽运价格报表信息VO.
     */
    private PriceTableVo vo = new PriceTableVo();
    
	public PriceTableVo getVo() {
		return vo;
	}

	public void setVo(PriceTableVo vo) {
		this.vo = vo;
	}
	// ================优化内容:接收前段数据/时间:2016年9月29日上午11:10:22/LianHe/start================
	private PartnerPriceTableVo partnerPriceTableVo = new PartnerPriceTableVo();
	
	
	public PartnerPriceTableVo getPartnerPriceTableVo() {
		return partnerPriceTableVo;
	}

	public void setPartnerPriceTableVo(PartnerPriceTableVo partnerPriceTableVo) {
		this.partnerPriceTableVo = partnerPriceTableVo;
	}
	// ================优化内容:接收前段数据/时间:2016年9月29日上午11:11:01/LianHe/end================
	/** 
	 * 导出流
	 **/
	private String fileName;
	private InputStream inputStream;

	 public InputStream getInputStream() {
		return inputStream;
	}
	 
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	/**
	 * 导出价格表
	 * 
	 * @author 076234-foss-pgy
	 * @date 2014-01-18 下午4:10:12
	 */
	@SuppressWarnings( "unchecked" )
	public String exportPricing() {
		try{
			// 获取当前路径
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 后台查询获得的重要提示明细（调用涛哥的接口）
			List<PriceReportTitleEntity> entitys =getTitleEntitys("N");
			
			// 后台查询获得的价格明细（调用明明的接口）
			List<PDFPriceTableEntity> tableentiys=null;
			PriceTableHeadEntity tableHead=null;
			//[POP] modify foss-148246-sunjianbo 2014-11-10 start
			//分段重量体积范围
			Map<String,String> sectionScopeMap = null;
			//分段数
			int sectionID = this.vo.getSectionID();
			//[POP] modify foss-148246-sunjianbo 2014-11-10 end
		    //查询部门
		    String startDeptCode =this.vo.getStartDeptCode();
		    //产品类型
		    String productType =this.vo.getProductType();
		    //有效时间
		    Date effectiveDate =this.vo.getEffectiveDate();
			SimpleDateFormat from =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// 重要提示信息
		    Map<String, Object> map = new HashMap<String, Object>();
		    for(PriceReportTitleEntity entity:entitys){
		    	if("N".equals(entity.getActive())){
		    		continue;
		    	}
		    	if(1==entity.getSerialNo()){
		    		map.put("header1",entity.getHeader() );
			    	map.put("details1", entity.getDetails());
		    	}
		    	if(2==entity.getSerialNo()){
		    		map.put("header2", entity.getHeader());
			    	map.put("details2", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_3==entity.getSerialNo()){
		    		map.put("header3", entity.getHeader());
			    	map.put("details3", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_4==entity.getSerialNo()){
		    		map.put("header4", entity.getHeader());
			    	map.put("details4", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_5==entity.getSerialNo()){
		    		map.put("header5", entity.getHeader());
			    	map.put("details5", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_6==entity.getSerialNo()){
		    		map.put("header6", entity.getHeader());
			    	map.put("details6", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_7==entity.getSerialNo()){
		    		map.put("header7", entity.getHeader());
			    	map.put("details7", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_8==entity.getSerialNo()){
			    	map.put("details8", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_9==entity.getSerialNo()){
			    	map.put("details9", entity.getDetails());
		    	}
		    }
		    if(StringUtils.isNotEmpty(startDeptCode)
		    		&&
		       StringUtils.isNotEmpty(productType)
		    	    &&
		       effectiveDate!=null
		    	 ){
		    	tableHead= getTableHead(startDeptCode,productType,effectiveDate); 
		    } 
		    //城市名称
			String cityName="";
		    if(tableHead!=null){
		    	cityName=tableHead.getCityName();
		    	map.put("name", tableHead.getName());
		    	map.put("productType",tableHead.getProductType());
		    	map.put("effectiveDate", from.format(tableHead.getCurrentDateTime()));
		    }
		    //获取文件读取类
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			// 声明图片位置
			Resource resource1 = resolver.getResource("com/deppon/foss/module/pickup/pricing/server/META-INF/images/deppon-log.jpg");
			Resource resource2 = resolver.getResource("com/deppon/foss/module/pickup/pricing/server/META-INF/images/dh.jpg");
			map.put("depponLogoImg",resource1.getInputStream());
			map.put("depponPhoneImg",resource2.getInputStream());
		    if(StringUtils.isNotEmpty(startDeptCode)
		    		&&
		       StringUtils.isNotEmpty(productType)
		    	    &&
		       effectiveDate!=null
		    	 ){
		    	//原代码 start
		    	//tableentiys=getTableEntitys(startDeptCode,productType,effectiveDate);
		    	//原代码 end
		    	//[POP] modify foss-148246-sunjianbo 2014-11-10 start
		    	Object[] objArray = queryTableEntitys(startDeptCode,productType,effectiveDate,sectionID);
		    	if(null != objArray){
		    		tableentiys = (List<PDFPriceTableEntity>) objArray[0];
					sectionScopeMap = (Map<String, String>) objArray[1];
					if(null != sectionScopeMap && StringUtils.isNotEmpty(sectionScopeMap.get("section"+sectionID))){
						map.put("sectionScope", sectionScopeMap.get("section"+sectionID));
					}
				}
		    	//[POP] modify foss-148246-sunjianbo 2014-11-10 end
		    } 
		   if(CollectionUtils.isNotEmpty(tableentiys)){
			 for(PDFPriceTableEntity tableentiy:tableentiys){
				// 声明打印模板对应表格的一行数据
				 final Map<String, Object> listMap = new HashMap<String, Object>();
				    listMap.put("productName", tableentiy.getProductName());
					listMap.put("columnName1" , tableentiy.getProvinceName1());
					listMap.put("columnName2" , tableentiy.getDestinationName1());
					listMap.put("columnName3" , tableentiy.getRunTime1());
					listMap.put("columnName4" , tableentiy.getHeavyPrice1());
					listMap.put("columnName5" , tableentiy.getLightPrice1());
					
					listMap.put("columnName6" , tableentiy.getProvinceName2());
					listMap.put("columnName7" , tableentiy.getDestinationName2());
					listMap.put("columnName8" , tableentiy.getRunTime2());
					listMap.put("columnName9" , tableentiy.getHeavyPrice2());
					listMap.put("columnName10" , tableentiy.getLightPrice2());
					
					listMap.put("columnName11" , tableentiy.getProvinceName3());
					listMap.put("columnName12" , tableentiy.getDestinationName3());
					listMap.put("columnName13" , tableentiy.getRunTime3());
					listMap.put("columnName14" , tableentiy.getHeavyPrice3());
					listMap.put("columnName15" , tableentiy.getLightPrice3());
					
					listMap.put("columnName16" , tableentiy.getProvinceName4());
					listMap.put("columnName17" , tableentiy.getDestinationName4());
					listMap.put("columnName18" , tableentiy.getRunTime4());
					listMap.put("columnName19" , tableentiy.getHeavyPrice4());
					listMap.put("columnName20" , tableentiy.getLightPrice4());
					
			//}
			 //list.add(lMap);
			 list.add(listMap);
		  }	 
		}else{
			return ERROR;
//		    return returnError("不存在数据！");
		}
		Date date =new Date();
		SimpleDateFormat frm =new SimpleDateFormat("yyyyMMddHHmmss");
		// 声明文件名称
		fileName = new String((cityName+PDFNAME+"-"+frm.format(date)).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO);
		// 进行流导出操作
		inputStream = PDFExport.exportPDF("pickup/pricing","pricingIreport", map, list);
		//正常返回
		return returnSuccess();
		}catch (Exception e) {
			//异常返回
			return ERROR;
		}
		   
	}
	// ================优化内容:新增合伙人汽运价格表页面的导出功能/时间:2016年9月29日上午8:57:24/LianHe/start================
	/**
	 * <p>新增合伙人汽运价格表页面的导出功能</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 上午9:30:23
	 * @return
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportPartnerPricing(){
//		System.out.println("合伙人导出方法执行、、、、、、、、");
		try{
			// 获取当前路径
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 后台查询获得的重要提示明细（调用涛哥的接口）
			List<PriceReportTitleEntity> entitys =getTitleEntitys("Y");
			
			// 后台查询获得的价格明细（调用明明的接口）
			List<PDFPriceTableEntity> tableentiys=null;
			PriceTableHeadEntity tableHead=null;
			//分段重量体积范围
			Map<String,String> sectionScopeMap = null;
			//分段数
			int sectionID = this.partnerPriceTableVo.getSectionID();
		    //查询部门
		    String startDeptCode =this.partnerPriceTableVo.getStartDeptCode();
		    //产品类型
		    String productType =this.partnerPriceTableVo.getProductType();
		    //有效时间
		    Date effectiveDate =this.partnerPriceTableVo.getEffectiveDate();
			SimpleDateFormat from =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// 重要提示信息
		    Map<String, Object> map = new HashMap<String, Object>();
		    for(PriceReportTitleEntity entity:entitys){
		    	if("N".equals(entity.getActive())){
		    		continue;
		    	}
		    	if(NumberConstants.NUMBER_10==entity.getSerialNo()){
		    		map.put("header1",entity.getHeader() );
			    	map.put("details1", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_11==entity.getSerialNo()){
		    		map.put("header2", entity.getHeader());
			    	map.put("details2", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_12==entity.getSerialNo()){
		    		map.put("header3", entity.getHeader());
			    	map.put("details3", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_13==entity.getSerialNo()){
		    		map.put("header4", entity.getHeader());
			    	map.put("details4", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_14==entity.getSerialNo()){
		    		map.put("header5", entity.getHeader());
			    	map.put("details5", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_15==entity.getSerialNo()){
		    		map.put("header6", entity.getHeader());
			    	map.put("details6", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_16==entity.getSerialNo()){
		    		map.put("header7", entity.getHeader());
			    	map.put("details7", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_17==entity.getSerialNo()){
			    	map.put("details8", entity.getDetails());
		    	}
		    	if(NumberConstants.NUMBER_18==entity.getSerialNo()){
			    	map.put("details9", entity.getDetails());
		    	}
		    }
		    if(StringUtils.isNotEmpty(startDeptCode)
		    		&&
		       StringUtils.isNotEmpty(productType)
		    	    &&
		       effectiveDate!=null
		    	 ){
		    	tableHead= getTableHead(startDeptCode,productType,effectiveDate); 
		    } 
		    //城市名称
			String cityName="";
		    if(tableHead!=null){
		    	cityName=tableHead.getCityName();
		    	map.put("name", tableHead.getName());
		    	map.put("productType",tableHead.getProductType());
		    	map.put("effectiveDate", from.format(tableHead.getCurrentDateTime()));
		    }
		    //获取文件读取类
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			// 声明图片位置
			Resource resource1 = resolver.getResource("com/deppon/foss/module/pickup/pricing/server/META-INF/images/deppon-log.jpg");
			Resource resource2 = resolver.getResource("com/deppon/foss/module/pickup/pricing/server/META-INF/images/dh.jpg");
			map.put("depponLogoImg",resource1.getInputStream());
			map.put("depponPhoneImg",resource2.getInputStream());
		    if(StringUtils.isNotEmpty(startDeptCode)
		    		&&
		       StringUtils.isNotEmpty(productType)
		    	    &&
		       effectiveDate!=null
		    	 ){
		    	//原代码 start
		    	//tableentiys=getTableEntitys(startDeptCode,productType,effectiveDate);
		    	//原代码 end
		    	//[POP] modify foss-148246-sunjianbo 2014-11-10 start
		    	Object[] objArray = queryPartnerTableEntitys(startDeptCode,productType,effectiveDate,sectionID);
		    	if(null != objArray){
		    		tableentiys = (List<PDFPriceTableEntity>) objArray[0];
					sectionScopeMap = (Map<String, String>) objArray[1];
					if(null != sectionScopeMap && StringUtils.isNotEmpty(sectionScopeMap.get("section"+sectionID))){
						map.put("sectionScope", sectionScopeMap.get("section"+sectionID));
					}
				}
		    	//[POP] modify foss-148246-sunjianbo 2014-11-10 end
		    } 
		   if(CollectionUtils.isNotEmpty(tableentiys)){
			 for(PDFPriceTableEntity tableentiy:tableentiys){
				// 声明打印模板对应表格的一行数据
				 final Map<String, Object> listMap = new HashMap<String, Object>();
				    listMap.put("productName", tableentiy.getProductName());
					listMap.put("columnName1" , tableentiy.getProvinceName1());
					listMap.put("columnName2" , tableentiy.getDestinationName1());
					listMap.put("columnName3" , tableentiy.getRunTime1());
					listMap.put("columnName4" , tableentiy.getHeavyPrice1());
					listMap.put("columnName5" , tableentiy.getLightPrice1());
					
					listMap.put("columnName6" , tableentiy.getProvinceName2());
					listMap.put("columnName7" , tableentiy.getDestinationName2());
					listMap.put("columnName8" , tableentiy.getRunTime2());
					listMap.put("columnName9" , tableentiy.getHeavyPrice2());
					listMap.put("columnName10" , tableentiy.getLightPrice2());
					
					listMap.put("columnName11" , tableentiy.getProvinceName3());
					listMap.put("columnName12" , tableentiy.getDestinationName3());
					listMap.put("columnName13" , tableentiy.getRunTime3());
					listMap.put("columnName14" , tableentiy.getHeavyPrice3());
					listMap.put("columnName15" , tableentiy.getLightPrice3());
					
					listMap.put("columnName16" , tableentiy.getProvinceName4());
					listMap.put("columnName17" , tableentiy.getDestinationName4());
					listMap.put("columnName18" , tableentiy.getRunTime4());
					listMap.put("columnName19" , tableentiy.getHeavyPrice4());
					listMap.put("columnName20" , tableentiy.getLightPrice4());
					
			//}
			 //list.add(lMap);
			 list.add(listMap);
		  }	 
		}else{
			return ERROR;
//		    return returnError("不存在数据！");
		}
		Date date =new Date();
		SimpleDateFormat frm =new SimpleDateFormat("yyyyMMddHHmmss");
		// 声明文件名称
		fileName = new String((cityName+PDFNAME+"-"+frm.format(date)).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO);
		// 进行流导出操作
		
		try {
			inputStream = PDFExport.exportPDF("pickup/pricing","partnerPricingIreport", map, list);
		} catch (Exception e) {
			System.out.println("==========="+ReflectionToStringBuilder.toString(e));
			e.printStackTrace();
		}
		
		//正常返回
		return returnSuccess();
		}catch (Exception e) {
			//异常返回
			return ERROR;
		}
	}
	
	// ================优化内容:新增合伙人汽运价格表页面的导出功能/时间:2016年9月29日上午8:57:29/LianHe/end================
	
	/**
	 * 涛哥-表头
	 * @return
	 */
	public List<PriceReportTitleEntity>  getTitleEntitys(String isPartner) {
		List<PriceReportTitleEntity> entitys=null;
		//======================新增合伙人判定标记/20160920/lianhe/开始=======================
//		entitys =priceReportTitleService.queryAllInfos();
		//设置是否合伙人标记，并传入后台查询
		entitys =priceReportTitleService.queryAllInfos(isPartner);
		//======================新增合伙人判定标记/20160920/lianhe/截止=======================
		return entitys;
	}
	
	/**
	 * 价格明细
	 * @return
	 */
	public List<PDFPriceTableEntity>  getTableEntitys(String startDeptCode,
			String productType, Date currentDateTime) {
		List<PDFPriceTableEntity> tableEntitys =null;
		tableEntitys = priceTableService.expPDFPriceTableList(startDeptCode,productType,currentDateTime);
		return tableEntitys;
	}
	
	
	/**
	 * <p>[pop]查询价格明细信息用于导出</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-11 下午4:01:25
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @return objArray[0] List<PDFPriceTableEntity> && objArray[1] Map<String,String>
	 * @see
	 */
	public Object[] queryTableEntitys(String startDeptCode,
			String productType, Date currentDateTime, int sectionID) {
		return priceTableService.expPDFPriceTableList(startDeptCode,productType,currentDateTime,sectionID); 
	}
	// ================优化内容:新增合伙人判定标记--获取表全部信息/时间:2016年9月29日上午9:18:35/LianHe/start================
	/**
	 * <p>查询价格明细信息用于导出(合伙人汽运价格导出)</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 上午9:31:21
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @return
	 * @see
	 */
	public Object[] queryPartnerTableEntitys(String startDeptCode,
			String productType, Date currentDateTime, int sectionID) {
		return priceTableService.expPDFPartnerPriceTableList(startDeptCode,productType,currentDateTime,sectionID); 
	}
	// ================优化内容:新增合伙人判定标记--获取表全部信息/时间:2016年9月29日上午9:18:49/LianHe/end================
	/**
     * 获得动态表头信息
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @return
     */
	PriceTableHeadEntity getTableHead(String startDeptCode, String productType,
			Date currentDateTime){
		return  priceTableService.getTableHead(startDeptCode, productType, currentDateTime);
	
	}

}
