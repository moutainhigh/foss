package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPriceCouponService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DetailExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PriceCouponException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceCouponVo;
import com.deppon.foss.module.base.baseinfo.server.util.XlsImpUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionVo;
import com.deppon.foss.util.DateUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 降价发券ACTION
 * @author dujunhui-187862
 * @date 2014-9-23 下午4:38:18
 * @version 1.0
 */
public class PriceCouponAction extends AbstractAction {
    /**
     * 序列化
     */
	private static final long serialVersionUID = 2883644272419312426L;
	/**
	 * 日志处理
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceCouponAction.class);
	/**
	 * 降价发券服务
	 */
	private IPriceCouponService priceCouponService;
	/**
	 * 降价发券方案VO
	 */
	private PriceCouponVo priceCouponVo  = new PriceCouponVo();
	/**
	 * 导入文件
	 */
	private String uploadFile;
	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	/**
	 *  区域管理服务
	 */
	private IRegionService regionService;
	/**
	 * 区域Vo页面传参对象
	 */
	private RegionVo regionVo = new RegionVo();
	
	/**
	 * 注入降价发券服务 
	 */
	public void setPriceCouponService(IPriceCouponService priceCouponService) {
		this.priceCouponService = priceCouponService;
	}
	/**
	 * 注入区域管理service 
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	/**
	 * priceCouponVo的get、set方法 
	 */
	public PriceCouponVo getPriceCouponVo() {
		return priceCouponVo;
	}
	public void setPriceCouponVo(PriceCouponVo priceCouponVo) {
		this.priceCouponVo = priceCouponVo;
	}
	/**
	 * 获得区域Vo页面传参对象
	 */
	public RegionVo getRegionVo() {
		return regionVo;
	}
	/**
	 * 设置区域Vo页面传参对象
	 */
	public void setRegionVo(RegionVo regionVo) {
		this.regionVo = regionVo;
	}
	
	/**
	 * @return  the uploadFile
	 */
	public String getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * @return  the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	/**
	 * @param uploadFileFileName the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	/**
	 * 根据条件查询折扣方案明细<br/>
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午10:55:26
	 * @since
	 */
	@JSON
	public String selectPriceCouponByCodition() {
		try {
			List<PriceCouponDto> priceCouponDtoList = priceCouponService.
					selectPriceCouponByCodition(priceCouponVo.
							getPriceCouponDto(), start, limit);
			priceCouponVo.setPriceCouponDtoList(priceCouponDtoList);
			this.setTotalCount(priceCouponService.countPriceCouponByCodition(priceCouponVo.
					getPriceCouponDto()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("根据条件查询降价发券方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 根据条件查询降价发券方案<br/>
	 * @author dujunhui-187862
	 * @date 2014-9-23 下午5:20:12
	 * @since
	 */
	@JSON
	public String selectPriceProgramByCodition() {
		try {
			MarketingSchemeEntity querySchemeEntity = new MarketingSchemeEntity();
			if(priceCouponVo.getMarketingSchemeEntity()==null){
				querySchemeEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
			}else{
			querySchemeEntity = priceCouponVo.getMarketingSchemeEntity();
			querySchemeEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
			}
			List<MarketingSchemeEntity> marketingSchemeEntityList = priceCouponService.selectPriceProgramByCodition(querySchemeEntity, start, limit);
			priceCouponVo.setMarketingSchemeEntityList(marketingSchemeEntityList);
			this.setTotalCount(priceCouponService.countPriceProgramByCodition(querySchemeEntity));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("根据条件查询降价发券方案: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>新增降价发券方案 </p>
	 * @author dujunhui-187862
	 * @date 2014-10-1
	 * @since
	 */
	@JSON
	public String addPricingCoupon() {
		try {
			MarketingSchemeEntity marketingEventEntity = new MarketingSchemeEntity();
			if(priceCouponVo.getMarketingSchemeEntity()==null){
				marketingEventEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
			}else{
			marketingEventEntity = priceCouponVo.getMarketingSchemeEntity();
		    marketingEventEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
			}
			priceCouponVo = priceCouponService.addCouponProgram(marketingEventEntity);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("新增降价发券方案: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>作废降价发券方案<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午2:15:39
	 * @since 
	 */
	@JSON
	public String deletePricingCoupon() {
		try {
			priceCouponService.deleteCouponProgram(priceCouponVo.getPriceCouponIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("作废降价发券方案: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>根据条件查询所有的区域<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-9 上午10:21:36
	 * @since
	 */
	@JSON
	public String searchRegionByCondition() {
		try {
			PriceRegionEntity regionEntity = regionVo.getRegionEntity();
			List<PriceRegionEntity> regionEntityList = regionService.searchRegionByCondition(regionEntity, this.getStart(),this.getLimit());
			regionVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionService.countRegionByCondition(regionEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * <p>根据主键查询降价发券主方案信息<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午3:34:09
	 * @since
	 */
	@JSON
	public String selectMarketingByPrimaryKey() {
		try {
			priceCouponVo = priceCouponService.selectCouponProgram(priceCouponVo.
													getMarketingSchemeEntity().getId());
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询降价发券主方案信息: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>新增折扣方案明细<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-9 下午1:54:22
	 * @since 
	 */
	@JSON
	public String addCouponProgramItem() {
		try {
			priceCouponService.addCouponProgramItem(
					priceCouponVo.getStartCouponOrgEntityList(), priceCouponVo.
					getEndCouponOrgEntityList(), priceCouponVo.getPriceCouponDto());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增降价发券方案明细: " + e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * <p>修改折扣方案主<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-20 下午4:19:25
	 * @since
	 */
	@JSON
	public String updatePricingCoupon() {
		try {
			priceCouponVo = priceCouponService.updateCouponProgram(priceCouponVo.getMarketingSchemeEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("修改降价发券方案: "+e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * <p>单条作废折扣方案明细<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午9:26:34
	 * @since 
	 */
	@JSON
	public String deleteCouponProgramItem() {
		try {
			priceCouponService.deleteCouponProgramItem(priceCouponVo.getPriceCouponDto().getPriceValuationId());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("作废降价发券方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>单条修改折扣方案明细<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午8:45:58
	 * @since 
	 */
	@JSON
	public String updateCouponProgramItem() {
		try {
			priceCouponService.updateCouponProgramItem(priceCouponVo.getPriceCouponDto());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改降价发券方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * <p>查询折扣方案明细(一条)<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午4:13:47
	 * @since
	 */
	@JSON
	public String selectPriceValuation() {
		try {
			PriceCouponDto priceCouponDto = priceCouponService.selectPriceCouponItemByValuationId(priceCouponVo.getPriceCouponDto().getPriceValuationId());
			priceCouponVo.setPriceCouponDto(priceCouponDto);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询降价发券方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * <p>升级版本<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午8:58:38
	 * @since 
	 */
	@JSON
	public String copyCouponProgram() {
		try {
			priceCouponVo = priceCouponService.copyCouponProgram(priceCouponVo.getMarketingSchemeEntity().getId());
			return returnSuccess(MessageType.COPY_PRICECOUPON_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("升级版本: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * <p>立即修改价格折扣方案状态<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午3:59:28
	 * @since 
	 */
	@JSON
	public String activateImmediatelyCouponProgramPrice() {
		try {
			Date nowDate= new Date();
		    if(nowDate!= null && priceCouponVo.getMarketingSchemeEntity().getBeginTime()!= null && nowDate.compareTo(priceCouponVo.getMarketingSchemeEntity().getBeginTime())>0){
		    	return returnError("激活时间不能小于当前时间！","激活时间不能小于当前时间！");
		    }
			priceCouponService.activateImmediatelyCouponProgram(priceCouponVo.getMarketingSchemeEntity().getId()
					,priceCouponVo.getMarketingSchemeEntity().getBeginTime());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("立即修改降价发券方案状态: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * <p>立即中止折扣方案<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午11:37:45
	 * @since 
	 */
	@JSON
	public String terminateImmediatelyCouponProgramPrice() {
		try {
			priceCouponService.terminateImmediatelyCouponProgram(priceCouponVo.getMarketingSchemeEntity().getId()
					,priceCouponVo.getMarketingSchemeEntity().getEndTime());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("立即中止降价发券方案状态: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>获取服务器当前时间</p>
	 * @author 187862-dujunhui
	 * @date 2014-11-12 上午9:24:04
	 * @param
	 * @see
	 */
	@JSON
	public String haveServerNowTime() {
		try {
			Date begintime=DateUtils.convert(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);//当天的0点00分00秒
			priceCouponVo.setNowTime(begintime);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("获取服务器当前时间: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 *<p>导入明细信息</p>
	 *@author dujunhui-187862
	 *@date 2014-10-30 下午3:17:40
	 *@return
	 */
	public String importDetail(){
		// 文件
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		int importTotal = 0;
		try {
			if(StringUtils.isNotBlank(uploadFileFileName)){
				//验证导入的文件类型
				/*if(uploadFileFileName.endsWith(".XLS")||uploadFileFileName.endsWith(".xls")
						||uploadFileFileName.endsWith(".xlsx")||uploadFileFileName.endsWith(".XLSX")){
				}else{
					throw new PriceCouponException("系统只支持.xls或.xlsx的格式文件，请选择正确文件进行导入");
				}*/
				
				if(!uploadFileFileName.endsWith(".XLS")&&!uploadFileFileName.endsWith(".xls")
						&&!uploadFileFileName.endsWith(".xlsx")&&!uploadFileFileName.endsWith(".XLSX")){
					throw new PriceCouponException("系统只支持.xls或.xlsx的格式文件，请选择正确文件进行导入");
				}
			}
			//验证文件非空
			if(uploadFile !=null){
				try {
					//转换流
					inputStream =new FileInputStream(uploadFile);
					//解析excel流
					book =XlsImpUtil.create(inputStream);
				} catch (Exception e) {
					LOGGER.error("非2003格式文件，转2007格式");
					return returnError("数据异常:" + e.getMessage(), e);
				}
			}else{
				throw new PriceCouponException("请选择文件进行导入");
			}
			
			if(book ==null){
				throw new  PriceCouponException("book 为空");
			}
			// 默认获取获取工作表1
			Sheet sheet = book.getSheetAt(NumberConstants.ZERO);
			//创建导入降价发券明细的Dto
			List<DetailExcelDto> excelDtos =new ArrayList<DetailExcelDto>();
			//将Excel表格每行数据读入列表
			excelDtos=makeExcelDtos(sheet);
			//获取登陆人信息
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			//批量插入导入信息
			importTotal =priceCouponService.addDetailMore(excelDtos,currentInfo.getEmpCode());
			priceCouponVo.setImportTotal(importTotal);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage(),e);
		}finally {
			// 回收文件数据
			// 判空
			if (book != null) {
				book = null;
			}
			// 关闭流文件
			// 判空
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("关闭失败",e);
					return returnError("文件关闭失败");
				}
			}
		} 
	}
	/**
	 *<p>将excel表格每行数据读入列表</p>
	 *@author dujunhui-187862
	 *@date 2014-10-31 下午7:00:21
	 *@param sheet
	 *@return
	 */
	private List<DetailExcelDto> makeExcelDtos(Sheet sheet) {
		List<DetailExcelDto> excelDtos =new ArrayList<DetailExcelDto>();
		if(null !=sheet){
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			//最大列数
			int colCnt = 0;
			//行记录
			DetailExcelDto excelDto =null;
			
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				excelDto = new DetailExcelDto();
				// 设置行号
				excelDto.setRowNum(rowNum);
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				// 如果本行第一列为空，则不继续取值
				if(row==null ||row.getCell(0) == null
						||StringUtils.isBlank(obtainStringVal(row.getCell(0)))){
					continue;
				}else{
					//若列数小于1
					if(colCnt<1){
						//设置最大列数，一共10列
						colCnt=NumberConstants.NUMBER_10;
					}
					PriceCriteriaDetailEntity entity =new PriceCriteriaDetailEntity();
					//获取值
					for (int colNum = 0; colNum < colCnt; colNum++) {
						obtainCellValue(row, colNum, entity);
					}
					//设置当前行的明细信息
					excelDto.setPriceCriteriaDetailEntity(entity);
				}
				excelDtos.add(excelDto);
			}
			return excelDtos;
		}
		return null;
	}
	/**
	 *<p>单元格取值转换</p>
	 *@author dujunhui-187862
	 *@date 2014-10-31 下午5:58:43
	 *@param cell
	 *@return
	 */
	private String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
			// 判空
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
						DateUtils.DATE_FORMAT);
			} else {// 纯数字
				cellVal = String.valueOf(cell.getNumericCellValue());
			}
			break;
		// 此行表示单元格的内容为string类型
		case HSSFCell.CELL_TYPE_STRING: // 字符串型
			cellVal = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型
			try {
				// 读公式计算值
				cellVal = String.valueOf(cell.getNumericCellValue());
			} catch (Exception e) {
				cellVal = String.valueOf(cell.getStringCellValue());
			}
			// 判空
			if ("NaN".equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			//cellVal = "";
			//break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal.trim();
	}
	
	/**
	 *<p>获取单元格的值</p>
	 *@author dujunhui-187862
	 *@date 2014-10-31 下午5:56:43
	 *@param row 行值
	 *@param i 列数
	 *@param excelDto
	 */
	private void obtainCellValue(Row row, int colNum,
				PriceCriteriaDetailEntity entity) {
			
			if(null !=row){
				//获取单元格
				Cell cell =row.getCell(colNum);
				if(null !=cell){
					String cellValue =obtainStringVal(cell);
					if(!sonarSplitOne(entity, colNum, cellValue)){
						sonarSplitTwo(entity, colNum, cellValue);
					}
			}
		}	
	}
	
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private boolean sonarSplitOne(PriceCriteriaDetailEntity entity, int colNum,
			String cellValue) {
		switch (colNum) {
		case NumberConstants.NUMERAL_ZORE:// 出发部门名称
			entity.setDeptOrgName(cellValue);
			return true;
		case NumberConstants.NUMBER_1: // 到达部门名称
			entity.setAvvrOrgName(cellValue);
			return true;
		case NumberConstants.NUMBER_2: // 产品类型名称
			entity.setProductName(cellValue);
			return true;
		case NumberConstants.NUMBER_3:// 重量起点
			entity.setWeightLeftRange(new BigDecimal(cellValue));
			return true;
		case NumberConstants.NUMBER_4:// 重量终点
			entity.setWeightRightRange(new BigDecimal(cellValue));
			return true;
		case NumberConstants.NUMBER_5:// 体积起点
			entity.setVolumeLeftRange(new BigDecimal(cellValue));
			return true;
		default:
			return false;
		}
	}

	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(PriceCriteriaDetailEntity entity, int colNum,
			String cellValue) {
		switch (colNum) {
		case NumberConstants.NUMBER_6:// 体积终点
			entity.setVolumeRightRange(new BigDecimal(cellValue));
			return;
		case NumberConstants.NUMBER_7:// 返券系数
			entity.setCouponRate(new BigDecimal(cellValue));
			return;
		case NumberConstants.NUMBER_8:// 是否接货
			if ("是".equals(cellValue)) {
				entity.setIsPickUp("Y");
			} else if ("否".equals(cellValue)) {
				entity.setIsPickUp("N");
			}
			return;
		case NumberConstants.NUMBER_9:// 方案编码
			entity.setSchemeCode(cellValue);
			return;
		default:
			return;
		}
	}
}