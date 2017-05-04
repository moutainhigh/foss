package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEcGoodsPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.EcGoodsPriceManageMentVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @Description:首续重价格方案管理action
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public class EcGoodsPricePlanAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/**日志对象*/
	private static final Logger LOGGER = LoggerFactory.getLogger(EcGoodsPricePlanAction.class);
	/**PRD约定的最大读取目的区域列数：2000*/
	private static final int CELL_COUNT = 2000;
	/**替代空白字符串""*/
	private static final String BLANK="";
	/**导入文件*/
	private File uploadFile;
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
    /**导出Excel文件流*/
    private InputStream inputStream;
    public InputStream getInputStream() {
    	return inputStream;
    }
    /**导出文件名称*/
    private String downloadFileName;
    public String getDownloadFileName() {
    	return downloadFileName;
    }
    public void setDownloadFileName(String downloadFileName) {
    	this.downloadFileName = downloadFileName;
    }
    /**价格方案 VO*/
    private EcGoodsPriceManageMentVo ecGoodspriceManageMentVo = new EcGoodsPriceManageMentVo();
    public EcGoodsPriceManageMentVo getEcGoodspriceManageMentVo() {
		return ecGoodspriceManageMentVo;
	}
	public void setEcGoodspriceManageMentVo(EcGoodsPriceManageMentVo ecGoodspriceManageMentVo) {
		this.ecGoodspriceManageMentVo = ecGoodspriceManageMentVo;
	}
	/**产品信息service*/
	private IProductService productService;
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**价格方案service*/
	private IEcGoodsPricePlanService ecGoodsPricePlanService;
	public void setEcGoodsPricePlanService(IEcGoodsPricePlanService ecGoodsPricePlanService) {
		this.ecGoodsPricePlanService = ecGoodsPricePlanService;
	}
	/**出发区域service*/
	private IRegionEcGoodsService regionEcGoodsService;
	public void setRegionEcGoodsService(IRegionEcGoodsService regionEcGoodsService) {
		this.regionEcGoodsService = regionEcGoodsService;
	}
	/**到达区域service*/
	private IRegionEcGoodsArriveService regionEcGoodsArriveService;
	public void setRegionEcGoodsArriveService(IRegionEcGoodsArriveService regionEcGoodsArriveService) {
		this.regionEcGoodsArriveService = regionEcGoodsArriveService;
	}

//////////////////////////////////////////////////////////////【导入】////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 导入方案和方案明细0
	 */
	public String importPrice() {
		// 初始化
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// Excel-2007之前版本
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// Excel-2007之后版本
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件！", "请选择导入文件！");
			}
			if (book != null) {
				// 默认获取工作薄中第一个表单
				Sheet sheet = book.getSheetAt(0);
				// 读取最终数据结果，key为始发区域名称 ，值为价格数据明细
				Map<String, List<PricePlanDetailDto>> detailMap = new HashMap<String, List<PricePlanDetailDto>>();
				// 收集excel文件中的价格出发区域map ，key为出发区域名称，值为出发区域名称
				Map<String, String> startRegionMap = new HashMap<String, String>();
				// 收集excel文件中的价格到达区域map ，key为到达区域名称，值为到达区域名称
				Map<String, String> arriveRegionMap = new HashMap<String, String>();
				// 收集excel文件中的产品信息map，key为产品名称，值为产品名称
				Map<String, String> productMap = new HashMap<String, String>();
				// 将Excel表格每行数据读入列表，开始收集信息，并做必要的检查
				this.readExcelValue(detailMap, startRegionMap,arriveRegionMap,productMap, sheet);
				if (detailMap == null || detailMap.size() < 1) {
					throw new FileException("导入数据为空,请检查！", "导入数据为空,请检查！");
				}
				// 查询出相关产品的实体
				Map<String, ProductEntity> productEntityMap = this.processProductInfo(productMap);
				// 查询出发区域的信息
				Map<String, PriceRegionEcGoodsEntity> startRegionEntityMap = this.processStartRegionInfo(startRegionMap);
				// 查询到达区域的信息
				Map<String, PriceRegionEcGoodsArriveEntity> arriveRegionEntityMap = this.processArriveRegionInfo(arriveRegionMap);
				// 调用业务层方法--批量插入价格方案
				ecGoodsPricePlanService.addPricePlanBatch(detailMap, startRegionEntityMap,arriveRegionEntityMap,productEntityMap);
			}
			return super.returnSuccess();
		} catch (FileException e) {
			return returnError(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件被破坏，请重新制作导入文件！"+e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件不符合规范，请重新制作导入文件！"+e);
		}finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					return returnError("文件关闭失败！");
				}
			}
		}
	}

	// 读取Excel表格数据进行校验后封装
	private void readExcelValue(Map<String, List<PricePlanDetailDto>> detailMap, Map<String, String> regionMap, Map<String, String> arriveRegionMap, Map<String, String> productMap, Sheet sheet) {
		StringBuilder builder = new StringBuilder();
		// 创建体积和重量区间值的校验
		Map<String, List<Double>> weightCheckMap = new HashMap<String, List<Double>>();
		Map<String,List<Double>> volumeCheckMap = new HashMap<String,List<Double>>();
		if (sheet != null && detailMap != null) {
			Map<Double,Double> leftWeight = new HashMap<Double,Double>();
			Map<Double,Double> rightWeight = new HashMap<Double,Double>();
			Map<Double,Double> leftVolume = new HashMap<Double,Double>();
			Map<Double,Double> rightVolume = new HashMap<Double,Double>();
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row row = sheet.getRow(0);
			int columnCount = row != null ? row.getPhysicalNumberOfCells():0;
			// 无数据的空表格以及目的地数大于2000的提示
			this.checkMinRowAndMaxColumn(rowCount , columnCount);
			// 获取目的地集合（包括校验重复性）
			Map<String, String> destRegionNameMap = this.checkRepeatArriveRegion(sheet);
			// 对于数据合格的表单计算一下总的目的地的数量
			int totalColume = destRegionNameMap.size();
			// 从第一行开始遍历行(一次遍历三行)【表格的行数索引是从0开始的】
			for (int rowNum = 0; rowNum < rowCount / NumberConstants.NUMBER_3; rowNum++) {
				// 先取出一组的三行
				Row row1 = sheet.getRow(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_1);
				Row row2 = sheet.getRow(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2);
				Row row3 = sheet.getRow(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3);
				// 每组的第一行第一列--始发区域名称
				String startRegionName = this.getStartRegionName(builder , rowNum , row1 , row2 , row3);
				// 限制出发地只能导入一个
				if (StringUtils.isBlank(startRegionName)) {
					continue;
				}
				regionMap.put(startRegionName, startRegionName);
				this.checkRepeatStartRegion(regionMap);
				// 每组的第一行第二列--是否含接货
				String reacheGoods = this.getReacheGoods(builder , rowNum , row1 , row2 , row3);
				// 每组的第一行第三列--是否含送货
				String sendGoods = this.getSendGoods(builder , rowNum , row1  , row2 , row3);
				// 每组的第一行第四列--产品类型
				String productNames = this.getProductNames(builder , rowNum , row1  , row2 , row3);
				// 每组的第一行第五列--计费类型
				this.getCaculateTypeNames(builder, rowNum, row1 ,row2 ,row3);
				// 每组的第一行第六列和第七列取出重量的上下限值做判断和每组的第二行第六列和第七列取出体积的上下限值做判断
				Cell weightLeft =  row1.getCell(NumberConstants.NUMBER_5);
				Cell weightRight = row1.getCell(NumberConstants.NUMBER_6);
				Cell volumeLeft = row2.getCell(NumberConstants.NUMBER_5);
				Cell volumeRight = row2.getCell(NumberConstants.NUMBER_6);
				// 先判断非空且类型不是数值型的单元格
				this.validateSection(builder, rowNum, weightLeft, weightRight, volumeLeft, volumeRight);
				// 判空处理
				if (null == weightLeft || null == weightRight || null == volumeLeft || null == volumeRight ) {
					continue;
				}
				// 对区间值合法性进行校验
				double weightLeftRange = weightLeft.getNumericCellValue();
				double weightRightRange = weightRight.getNumericCellValue();
				double volumeLeftRange = volumeLeft.getNumericCellValue();
				double volumeRightRange = volumeRight.getNumericCellValue();
				String weightCheckKey = "出发城市:"+startRegionName + ",产品类型:"+ productNames + ",重量区间";
				String volumeCheckKey = "出发城市:"+startRegionName + ",产品类型:"+ productNames + ",体积区间";
				this.checkRepeation(leftWeight ,weightLeftRange ,builder , weightCheckKey);
				this.checkRepeation(rightWeight ,weightRightRange ,builder , weightCheckKey);
				this.checkRepeation(leftVolume ,volumeLeftRange ,builder , volumeCheckKey);
				this.checkRepeation(rightVolume ,volumeRightRange ,builder ,volumeCheckKey);
				this.prepareForCheckRange(weightCheckMap, volumeCheckMap, weightLeftRange, weightRightRange, volumeLeftRange, volumeRightRange, weightCheckKey, volumeCheckKey);
				//从第八列开始遍历列
				for (int colNum = NumberConstants.NUMBER_7; colNum < totalColume + NumberConstants.NUMBER_7; colNum++) {
					Cell cell1 = row1.getCell(colNum);
					Cell cell2 = row2.getCell(colNum);
					Cell cell3 = row3.getCell(colNum);
					String str1 = ExcelHandleUtil.obtainStringVal(cell1);
					String str2 = ExcelHandleUtil.obtainStringVal(cell2);
					String str3 = ExcelHandleUtil.obtainStringVal(cell3);
					// 重量、体积费率要是都不存在 或者 固定价不存在-->就直接跳过
					if (!((StringUtils.isBlank(str3) && StringUtils.isNotBlank(str2) && StringUtils.isNotBlank(str1)) ||
							(StringUtils.isNotBlank(str3) && StringUtils.isBlank(str2) && StringUtils.isBlank(str1)))) {
						builder.append("第").
								append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).
								append("行至第").
								append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).
								append("行的第").
								append(colNum + NumberConstants.NUMBER_1).
								append("列数据不合法，请检查！");
						continue;
					}
					// 读出轻货价格和重货价格  （保留两位小数）
					BigDecimal weightRateTemp = new BigDecimal(cell1.getNumericCellValue());
					double weightRate = weightRateTemp.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					BigDecimal volumeRateTemp = new BigDecimal(cell2.getNumericCellValue());
					double volumeRate = volumeRateTemp.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					//	首重固定价格直接取整
					BigDecimal fixedCosts = new BigDecimal(cell3.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
					// 限制轻重价格以及固定费用不能为负数
					this.notAllowNegative(weightRateTemp , volumeRateTemp , fixedCosts , builder);
					// +++++++++++++++++++++++++++++++++【start  组装数据】++++++++++++++++++++++++++++++++++++++++++++++++++
					PricePlanDetailDto detailDto = new PricePlanDetailDto();
					// 设置目的区域名称
					detailDto.setArrvRegionName(destRegionNameMap.get(colNum + BLANK));
					// 设置是否接货
					detailDto.setCentralizePickup(reacheGoods);
					// 设置是否送货
					detailDto.setCentralizeDelivery(sendGoods);
					// 设置产品名称
					detailDto.setProductItemName(productNames);
					// 设置重量开始范围
					detailDto.setWeightLeftRange(new BigDecimal(weightLeftRange+BLANK));
					// 设置重量的结束范围
					detailDto.setWeightRightRange(new BigDecimal(weightRightRange+BLANK));
					// 设置体积的开始范围
					detailDto.setVolumeLeftRange(new BigDecimal(volumeLeftRange+BLANK));
					// 设置体积的结束范围
					detailDto.setVolumeRightRange(new BigDecimal(volumeRightRange+BLANK));
					// 设置重货价格
					detailDto.setHeavyPrice(new BigDecimal(weightRate + BLANK));
					// 设置体积价格
					detailDto.setLightPrice(new BigDecimal(volumeRate + BLANK));
					// 设置首重固定费用
					detailDto.setFixedCosts(fixedCosts);
					// 最终封装数据
					List<PricePlanDetailDto> alist = detailMap.get(startRegionName);
					if (CollectionUtils.isEmpty(alist)) {
						alist = new ArrayList<PricePlanDetailDto>();
						detailMap.put(startRegionName, alist);
					}
					alist.add(detailDto);
					arriveRegionMap.put(detailDto.getArrvRegionName(), detailDto.getArrvRegionName());
					regionMap.put(startRegionName, startRegionName);
					productMap.put(productNames, productNames);
					// +++++++++++++++++++++++++++++++++【end  组装数据】++++++++++++++++++++++++++++++++++++++++++++++++++
				}
			}
			// 校验重量和体积区间值的连贯性
			this.checkWeightAndVolumeRange(builder, weightCheckMap, volumeCheckMap);
			if (builder.length() > 0) {
				throw new FileException("方案导入时由于如下问题导致导入失败！：" + builder.toString(),"方案导入时由于如下问题导致导入失败！：" + builder.toString());
			}
		}
	}
	
	// 同一列中判断区间值不可以存在重复值
	private void checkRepeation(Map<Double, Double> checkMap,double d ,StringBuilder sb, String key) {
		Double put = checkMap.put(d, d);
		if (null!=put) {
			sb.append(key).append("设置值存在范围冲突，请检查！");
		}
	}
	// 限制导入方案中轻重货价格以及固定费用不能为负数
	private void notAllowNegative(BigDecimal weightRateTemp,BigDecimal volumeRateTemp, BigDecimal fixedCosts, StringBuilder builder) {
		BigDecimal zero = new BigDecimal(0);
		if (weightRateTemp.compareTo(zero)<0
		   ||volumeRateTemp.compareTo(zero)<0
		   ||fixedCosts.compareTo(zero)<0) {
			builder.append("您导入的价格方案中价格项存在负数，请检查！");
		}
	}
	// 无数据的空表格以及目的地数大于2000的提示
	private void checkMinRowAndMaxColumn(int rowCount, int columnCount) {
		if (rowCount < NumberConstants.NUMBER_2) {
			throw new FileException("Excel表中没有数据！","Excel表中没有数据！");
		}
		if (columnCount > CELL_COUNT+NumberConstants.NUMBER_7) {
			throw new FileException("您导入的目的地数量超过上限2000个，请检查！","您导入的目的地数量超过上限2000个，请检查！");
		}
	}
	
	// 校验重量和体积区间值的连贯性
	private void checkWeightAndVolumeRange(StringBuilder builder,
			Map<String, List<Double>> weightCheckMap,
			Map<String, List<Double>> volumeCheckMap) {
		Set<String> weightSet = weightCheckMap.keySet();
		if (CollectionUtils.isNotEmpty(weightSet)){
			for (String key : weightSet) {
				this.check(weightCheckMap.get(key), builder, key);
			}
		}
		Set<String> volumeSet = volumeCheckMap.keySet();
		if (CollectionUtils.isNotEmpty(volumeSet)){
			for (String key : volumeSet) {
				this.check(volumeCheckMap.get(key), builder, key);
			}
		}
	}

	// 准备区间值为了校验区间连贯性
	private void prepareForCheckRange(Map<String, List<Double>> weightCheckMap, Map<String, List<Double>> volumeCheckMap, double weightLeftRange, double weightRightRange, double volumeLeftRange, double volumeRightRange, String weightCheckKey, String volumeCheckKey) {
		if (weightCheckMap.get(weightCheckKey) != null) {
            List<Double> oldW = weightCheckMap.get(weightCheckKey);
            oldW.add(weightLeftRange);
            oldW.add(weightRightRange);
            weightCheckMap.put(weightCheckKey, oldW);
        }else{
            List<Double> newW = new ArrayList<Double>();
            newW.add(weightLeftRange);
            newW.add(weightRightRange);
            weightCheckMap.put(weightCheckKey, newW);
        }
		if (volumeCheckMap.get(volumeCheckKey) != null) {
            List<Double> oldV = volumeCheckMap.get(volumeCheckKey);
            oldV.add(volumeLeftRange);
            oldV.add(volumeRightRange);
            volumeCheckMap.put(volumeCheckKey, oldV);
        }else {
            List<Double> newV = new ArrayList<Double>();
            newV.add(volumeLeftRange);
            newV.add(volumeRightRange);
            volumeCheckMap.put(volumeCheckKey, newV);
        }
	}

	// 检验重量区间和体积区间值存在且类型是否合法
	private void validateSection(StringBuilder builder, int rowNum, Cell weightLeft, Cell weightRight, Cell volumeLeft, Cell volumeRight) {
		if (weightLeft != null && weightLeft.getCellType() != Cell.CELL_TYPE_NUMERIC) {
            builder.append("第").append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第6列数据类型不正确，请检查！");
        }
		if (weightRight != null && weightRight.getCellType() != Cell.CELL_TYPE_NUMERIC) {
            builder.append("第").append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第7列数据类型不正确，请检查！");
        }
		if (volumeLeft != null && volumeLeft.getCellType() != Cell.CELL_TYPE_NUMERIC) {
            builder.append("第").append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行第6列数据类型不正确，请检查！");
        }
		if (volumeRight != null && volumeRight.getCellType() != Cell.CELL_TYPE_NUMERIC) {
            builder.append("第").append(rowNum * NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行第7列数据类型不正确，请检查！");
        }
	}

	// 获取计费类型--校验人为的错误【将体积和重量颠倒】
	private void getCaculateTypeNames(StringBuilder builder, int rowNum, Row row1, Row row2, Row row3) {
		Cell cell = row1.getCell(NumberConstants.NUMBER_4);
		String caculateTypeNames = null;
		if (null == cell) {
            builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第5列没有数据，请检查！");
        } else {
            caculateTypeNames = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(caculateTypeNames)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第5列没有数据，请检查！");
            }else if(!caculateTypeNames.startsWith("重量")){
            	builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第5列数据不合法，请检查！");
            }
        }
		cell = row2.getCell(NumberConstants.NUMBER_4);
		if (null == cell) {
            builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行第5列没有数据，请检查！");
        } else {
            caculateTypeNames = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(caculateTypeNames)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行第5列没有数据，请检查！");
            }else if(!caculateTypeNames.startsWith("体积")){
            	builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行第5列数据不合法，请检查！");
            }
        }
		cell = row3.getCell(NumberConstants.NUMBER_4);
		if (null == cell) {
            builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行第5列没有数据，请检查！");
        } else {
            caculateTypeNames = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(caculateTypeNames)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行第5列没有数据，请检查！");
            }else if(!caculateTypeNames.startsWith("首重")){
            	builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行第5列数据不合法，请检查！");
            }
        }
	}

	// 获取产品类型
	private String getProductNames(StringBuilder builder, int rowNum, Row row1, Row row2, Row row3) {
		String productNames = null;
		String str2 = null;
		String str3 = null;
		Cell cell = row1.getCell(NumberConstants.NUMBER_3);
		if (null == cell) {
			builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第4列没有数据，请检查！");
        } else {
            productNames = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(productNames)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第4列没有数据，请检查！");
            }
        }
		cell = row2.getCell(NumberConstants.NUMBER_3);
		if (null != cell) {
			str2 = ExcelHandleUtil.obtainStringVal(cell);
			if (!StringUtil.equals(productNames, str2)) {
				builder.append("第4列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		cell = row3.getCell(NumberConstants.NUMBER_3);
		if (null != cell) {
			str3 = ExcelHandleUtil.obtainStringVal(cell);
			if (!StringUtil.equals(productNames, str3)) {
				builder.append("第4列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		return productNames;
	}

	// 获取是否包含送货
	private String getSendGoods(StringBuilder builder, int rowNum, Row row1, Row row2, Row row3) {
		String sendGoods = null;
		String str2 = null;
		String str3 = null;
		Cell cell = row1.getCell(NumberConstants.NUMBER_2);
		if (null == cell) {
			builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第3列没有数据，请检查！");
        }else{
            sendGoods = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(sendGoods)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第3列没有数据，请检查！");
            }else{
				if ("是".equalsIgnoreCase(sendGoods)) {
					sendGoods = FossConstants.YES;
				} else if("否".equalsIgnoreCase(sendGoods)){
					sendGoods = FossConstants.NO;
				}else{
					builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第3列数据不合法，请检查！");
				}
			}
        }
		cell = row2.getCell(NumberConstants.NUMBER_2);
		if (null != cell) {
			str2 = ExcelHandleUtil.obtainStringVal(cell);
			str2 = "是".equals(str2) ? FossConstants.YES : FossConstants.NO;
			if (!StringUtil.equals(sendGoods, str2)) {
				builder.append("第3列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		cell = row3.getCell(NumberConstants.NUMBER_2);
		if (null != cell) {
			str3 = ExcelHandleUtil.obtainStringVal(cell);
			str3 = "是".equals(str3) ? FossConstants.YES : FossConstants.NO;
			if (!StringUtil.equals(sendGoods, str3)) {
				builder.append("第3列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		return sendGoods;
	}

	// 获取是否包含接货
	private String getReacheGoods(StringBuilder builder, int rowNum, Row row1, Row row2, Row row3) {
		String reacheGoods = null;
		String str2 = null;
		String str3 = null;
		Cell cell = row1.getCell(NumberConstants.NUMBER_1);
		if (null == cell) {
			builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第2列没有数据，请检查！");
        } else {
            reacheGoods = ExcelHandleUtil.obtainStringVal(cell);
            if (StringUtils.isBlank(reacheGoods)) {
				builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第2列没有数据，请检查！");
            }else{
				if ("是".equalsIgnoreCase(reacheGoods)) {
					reacheGoods = FossConstants.YES;
				} else if("否".equalsIgnoreCase(reacheGoods)){
					reacheGoods = FossConstants.NO;
				}else{
					builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第2列数据不合法，请检查！");
				}
			}
        }
		cell = row2.getCell(NumberConstants.NUMBER_1);
		if (null != cell) {
			str2 = ExcelHandleUtil.obtainStringVal(cell);
			str2 = "是".equals(str2) ? FossConstants.YES : FossConstants.NO;
			if (!StringUtil.equals(reacheGoods, str2)) {
				builder.append("第2列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		cell = row3.getCell(NumberConstants.NUMBER_1);
		if (null != cell) {
			str3 = ExcelHandleUtil.obtainStringVal(cell);
			str3 = "是".equals(str3) ? FossConstants.YES : FossConstants.NO;
			if (!StringUtil.equals(reacheGoods, str3)) {
				builder.append("第2列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		return reacheGoods;
	}

	// 获取出发城市
	private String getStartRegionName(StringBuilder builder, int rowNum, Row row1, Row row2, Row row3) {
		String startRegionName = null;
		String str2 = null;
		String str3 = null;
		Cell cell = row1.getCell(0);
		if (null == cell) {
			builder.append("第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行第1列没有数据，请检查！");
        } else {
            startRegionName = ExcelHandleUtil.obtainStringVal(cell);
        }
		cell = row2.getCell(0);
		if (null != cell) {
			str2 = ExcelHandleUtil.obtainStringVal(cell);
			if (!StringUtil.equals(startRegionName, str2)) {
				builder.append("第1列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_3).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		cell = row3.getCell(0);
		if (null != cell) {
			str3 = ExcelHandleUtil.obtainStringVal(cell);
			if (!StringUtil.equals(startRegionName, str3)) {
				builder.append("第1列第").append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_4).append("行与第")
				.append(rowNum*NumberConstants.NUMBER_3 + NumberConstants.NUMBER_2).append("行数据不同，请检查！");
			}
        }
		return startRegionName;
	}
	
	// 检验出发地的重复性（出发地只允许配置一个）
	private void checkRepeatStartRegion(Map<String , String> startRegionNameMap){
		if (MapUtils.isNotEmpty(startRegionNameMap)) {
			Set<String> set = startRegionNameMap.keySet();
			if (set.size()>1) {
				throw new FileException("您导入的方案中出发城市数量大于一个，请检查！", "您导入的方案中出发城市数量大于一个，请检查！");
			}
		}
	}

	// 检验目的地的重复性
	private Map<String, String> checkRepeatArriveRegion(Sheet sheet) {
		Row row = sheet.getRow(0);
		// 看第一行到底有多少列数据，从第8列开始，是目的城市数据
		Map<String, String> destRegionNameMap = new HashMap<String, String>();
		Map<String, String> checkdestRegionNameMap = new HashMap<String, String>();
		// 下面的功能就是从第八列开始确定所有到达区域没有重复
		for (int colNum = NumberConstants.NUMBER_7; colNum < CELL_COUNT+NumberConstants.NUMBER_7; colNum++) {
            Cell cell = row.getCell(colNum);
            if (cell != null) {
                String cellVal = ExcelHandleUtil.obtainStringVal(cell);
                if (StringUtils.isBlank(cellVal)) {
                    break;
                } else {
                    destRegionNameMap.put(colNum + BLANK, cellVal);
                    if (null == checkdestRegionNameMap.get(cellVal) ) {
                        checkdestRegionNameMap.put(cellVal, cellVal);
                    } else {
                        throw new FileException("目的地" + cellVal + "存在重复值，请检查数据！","目的地" + cellVal + "存在重复值，请检查数据！");
                    }
                }
            }
        }
		return destRegionNameMap;
	}

	// 校验集合的区间数据是连贯的：如[0，1][1，10][10，100]这样的
	private void check(List<Double> list , StringBuilder sb , String  key ){
		if (CollectionUtils.isNotEmpty(list)) {
			if(list.size()<NumberConstants.NUMBER_3){
				return;
			}
			Collections.sort(list);
			List<Double> subList = list.subList(1, list.size()-1);
			List<Double> checkList = new ArrayList<Double>();
			for (Double d : subList) {
				boolean flag = checkList.contains(d);
				if (!flag) {
					checkList.add(d);
				}
			}
			int i = subList.size();
			int j = checkList.size();
			if (i > 0 && j > 0) {
				if (!(0 == i % j && NumberConstants.NUMBER_2 == i / j)) {
					sb.append(key).append("设置值存在范围冲突，请检查！");
				}
			}
		}
	}

	// 处理二级产品信息
	private Map<String, ProductEntity> processProductInfo(Map<String, String> nameMap) {
		// 非空判断
		if (null == nameMap || nameMap.size() < 1) {
			throw new FileException("导入数据中没有产品类型信息，请检查！", "导入数据中没有产品类型信息，请检查！");
		}
		// 调用业务层方法-->查询所有的二级产品信息
		List<ProductEntity> productInfoList = productService.queryLevel2ProductInfo();
		if (CollectionUtils.isEmpty(productInfoList)) {
			throw new FileException("数据库中没有相关二级产品信息！", "数据库中没有相关二级产品信息！");
		}
		Map<String, ProductEntity> nameEntityMap = new HashMap<String, ProductEntity>();
		Iterator<String> keyIt = nameMap.keySet().iterator();
		while (keyIt.hasNext()) {
			// 逐个比对是否导入的产品在数据库里存在
			String productname = keyIt.next();
			for (int loop = 0; loop < productInfoList.size(); loop++) {
				ProductEntity productEntity = productInfoList.get(loop);
				if (StringUtil.equals(productname, productEntity.getName())) {
					nameEntityMap.put(productname, productEntity);
					break;
				}
			}
			if (null == nameEntityMap.get(productname)) {
				// 最后都没找到，那就是出问题了
				throw new FileException("数据库中没有产品类型:" + productname + "，请检查！", "数据库中没有产品类型:" + productname + "，请检查！");
			}
		}
		return nameEntityMap;
	}

	// 处理出发区域信息
	private Map<String, PriceRegionEcGoodsEntity> processStartRegionInfo(Map<String, String> regionMap) {
		// 非空判断
		if (regionMap == null || regionMap.size() < 1) {
			throw new FileException("导入数据中没有出发区域信息，请检查！", "导入数据中没有出发区域信息，请检查！");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(regionMap.keySet());
		// 调用业务层方法-->批量查找出发区域信息
		Map<String, PriceRegionEcGoodsEntity> findResult = regionEcGoodsService.findRegionByNames(names, PricingConstants.PRICING_REGION, new Date());
		if (findResult == null || findResult.size() < 1) {
			throw new FileException("数据库中没有匹配到导入的任何出发区域数据，请检查！", "数据库中没有匹配到导入的任何出发区域数据，请检查！");
		}
		Iterator<String> it = names.iterator();
		while (it.hasNext()) {
			String object = it.next();
			if (null == findResult.get(object)) {
				throw new FileException("出发区域：" + object + "，在数据库中没有找到，请检查！", "出发区域：" + object + "，在数据库中没有找到，请检查！");
			}
		}
		return findResult;
	}

	// 处理到达区域信息
	private Map<String, PriceRegionEcGoodsArriveEntity> processArriveRegionInfo(Map<String, String> regionMap) {
		// 非空判断
		if (regionMap == null || regionMap.size() < 1) {
			throw new FileException("导入数据中没有到达区域信息，请检查！", "导入数据中没有到达区域信息，请检查！");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(regionMap.keySet());
		// 调用业务层方法-->批量查找到达区域信息
		Map<String, PriceRegionEcGoodsArriveEntity> findResult = regionEcGoodsArriveService.findRegionByNames(names, PricingConstants.PRICING_REGION, new Date());
		if (findResult == null || findResult.size() < 1) {
			throw new FileException("数据库中没有匹配到导入的任何到达区域数据，请检查！", "数据库中没有匹配到导入的任何到达区域数据，请检查！");
		}
		Iterator<String> it = regionMap.keySet().iterator();
		while (it.hasNext()) {
			String object = it.next();
			if (null == findResult.get(object)) {
				throw new FileException("到达区域：" + object + "，在数据库中没有找到，请检查！", "到达区域：" + object + "，在数据库中没有找到，请检查！");
			}
		}
		return findResult;
	}

//////////////////////////////////////////////////////////////【查询】////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 分页查询价格方案1
	 */
	@JSON
	public String queryEcGoodsPricePlanBatchInfo(){
		try {
			List<PricePlanEntity> pricePlanEntityList = ecGoodsPricePlanService.queryPricePlanBatchInfo(ecGoodspriceManageMentVo.getPricePlanEntity(),getStart(),getLimit());
			ecGoodspriceManageMentVo.setPricePlanEntityList(pricePlanEntityList);
			Long count = ecGoodsPricePlanService.queryPricePlanBatchInfoCount(ecGoodspriceManageMentVo.getPricePlanEntity());
			this.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("分页查询价格方案-->出现异常: "+e.getMessage());
			}
			return returnError(e);
		}
	}

	/**
	 * 分页查询价格方案规则明细2
	 */
	@JSON
	public String queryEcGoodsPricePlanDetailInfo(){
		try {
			List<PricePlanDetailDto> list = ecGoodsPricePlanService.queryPricePlanDetailInfo(ecGoodspriceManageMentVo.getQueryPricePlanDetailBean(), start,limit);
			ecGoodspriceManageMentVo.setPricePlanDetailDtoList(list);
			Long count = ecGoodsPricePlanService.queryPricePlanDetailInfoCount(ecGoodspriceManageMentVo.getQueryPricePlanDetailBean());
			this.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("查询价格方案规则明细-->出现异常: "+e.getMessage());
			}
			return returnError(e);
		}
	}

	/**
	 * 查询需要修改的价格方案3
	 */
	@JSON
	public String queryEcGoodsPricePlanAndDetailInfoById(){
		try {
			ecGoodspriceManageMentVo = ecGoodsPricePlanService.queryCopyPricePlanInfo(ecGoodspriceManageMentVo.getPricePlanId());
			return returnSuccess();
		} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("根据方案ID查询方案以及规则明细-->出现异常: "+e.getMessage());
			}
			return returnError(e);
		}
	}

////////////////////////////////////////////////////////////////【修改】////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改价格方案信息4
	 */
	@JSON
	public String updateEcGoodsPricePlan(){
		try {
			PricePlanEntity dbEntity = ecGoodsPricePlanService.modifyPricePlan(ecGoodspriceManageMentVo.getPricePlanEntity());
			ecGoodspriceManageMentVo.setPricePlanEntity(dbEntity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("修改保存价格方案信息-->出现异常: "+e.getMessage());
			}
			return returnError(e);
		}
	}

////////////////////////////////////////////////////////////////【删除】////////////////////////////////////////////////////////////////////////////////////
    /**
     * 删除价格方案5
     */
    @JSON
    public String deleteEcGoodsPricePlan(){
    	try {
    		ecGoodsPricePlanService.deletePricePlan(ecGoodspriceManageMentVo.getPricePlanIds());
    		return returnSuccess(MessageType.DELETE_SUCCESS);
    	} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("删除价格方案-->出现异常: "+e.getMessage());
			}
    		return returnError(e);
    	}
    }

//////////////////////////////////////////////////////////////【激活】////////////////////////////////////////////////////////////////////////////////////
	/**
     * 立即激活价格方案6
     */
    @JSON
    public String immediatelyActiveEcGoodsPricePlan(){
    	try {
    		List<String> activePricePlanIds = new ArrayList<String>();
    		activePricePlanIds = ecGoodspriceManageMentVo.getPricePlanIds();
    		if (activePricePlanIds.size()>NumberConstants.NUMBER_50) {
				return returnError("一次性激活方案的条数不能超过50条，请注意！");
			}
    		for(int i=0;i<activePricePlanIds.size();i++){
    			ecGoodspriceManageMentVo.getPricePlanEntity().setId(activePricePlanIds.get(i));
    			ecGoodsPricePlanService.immediatelyActivePricePlan(ecGoodspriceManageMentVo.getPricePlanEntity());
    		}
    		return returnSuccess(MessageType.ACTIVE_SUCCESS);
    	} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("立即激活价格方案-->出现异常: "+e.getMessage());
			}
    		return returnError(e);
    	}
    }

//////////////////////////////////////////////////////////////【中止】////////////////////////////////////////////////////////////////////////////////////
    /**
     * 批量中止价格方案7
     */
    @JSON
    public String stopEcGoodsPricePlans(){
    	try {
    		List<String> stopPricePlanIds = new ArrayList<String>();
    		stopPricePlanIds = ecGoodspriceManageMentVo.getPricePlanIds();
    		if (stopPricePlanIds.size()>NumberConstants.NUMBER_50) {
				return returnError("一次性中止方案的条数不能超过50条，请注意！");
			}
    		for(int i=0;i<stopPricePlanIds.size();i++){
    			ecGoodspriceManageMentVo.getPricePlanEntity().setId(stopPricePlanIds.get(i));
    			ecGoodsPricePlanService.stopPricePlan(ecGoodspriceManageMentVo.getPricePlanEntity());
    		}
    		return returnSuccess(MessageType.STOP_SUCCESS);
    	} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("批量中止价格方案-->出现异常: "+e.getMessage());
			}
    		return returnError(e);
    	}
    }

	/**
	 * 中止单条价格方案8
	 */
	@JSON
	public String stopEcGoodsPricePlan(){
		try {
			ecGoodsPricePlanService.stopPricePlan(ecGoodspriceManageMentVo.getPricePlanEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("中止价格方案-->出现异常: "+e.getMessage());
			}
			return returnError(e);
		}
	}

//////////////////////////////////////////////////////////////【导出】////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 导出价格方案10
	 */
	public String exportEcGoodsPricePlan() {
		try {
			String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN;
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			if (null == ecGoodspriceManageMentVo.getPricePlanEntity()) {
				ecGoodspriceManageMentVo.setPricePlanEntity(new PricePlanEntity());
			}
			// 导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			// 存储导出数据
			ExportResource exportResource = ecGoodsPricePlanService.exportPricePlan(ecGoodspriceManageMentVo.getPricePlanEntity());
			if(exportResource!=null){
				// 根据存储的数据调用导出类
				ExporterExecutor objExporterExecutor = new ExporterExecutor();
				inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			}else{
				inputStream =new ByteArrayInputStream(" ".getBytes());
			}
			return returnSuccess();
		} catch (UnsupportedEncodingException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("价格方案导出-->出现异常: " + e.getMessage());
			}
			return returnError("价格方案导出-->出现异常", e.getMessage());
		}
	}

	/**
	 * 导出规则明细11
	 */
	public String exportPricePlanDetail() {
		try {
			String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL ;
			downloadFileName =encodeFileName(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT) + fileName);
			//导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			//存储导出数据
			ExportResource exportResource = ecGoodsPricePlanService.exportPricePlanDetail(ecGoodspriceManageMentVo.getQueryPricePlanDetailBean());
			//根据存储的数据调用导出类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			return returnSuccess();
		} catch (UnsupportedEncodingException e) {
			if (LOGGER.isErrorEnabled()){
				LOGGER.error("价格方案规则明细导出-->出现异常: "+e.getMessage());
			}
			return returnError("价格方案规则明细导出-->出现异常",e.getMessage());
		}
	}

	// 转换导出文件的文件名
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
}
