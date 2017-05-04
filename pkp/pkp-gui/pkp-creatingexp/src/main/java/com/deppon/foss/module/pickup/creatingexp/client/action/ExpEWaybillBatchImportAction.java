package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;

/**
 * <p>通过Excel进行数据的批量导入</p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-23 08:41:04
 */
public class ExpEWaybillBatchImportAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {
	
	/** 日志 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpEWaybillBatchImportAction.class);
	
	private ExpSalesDepartEWaybillUI ui;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillBatchImportAction.class);

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int ELEVEN = 11;

	private static final int TEN = 10;

	private static final int EIGHT = 8;

	private static final int SEVEN = 7;

	/**
	 * 进行有关数据的导入
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-26 15:04:17
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();// 文件选择器 
		File uploadFile = null;
		
		jfc.setDialogType(JFileChooser.OPEN_DIALOG);
		jfc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xlsx"));// 添加文本文件过滤
		int result = jfc.showOpenDialog(ui);
		if (result == JFileChooser.APPROVE_OPTION) {
			uploadFile = jfc.getSelectedFile();// f为选择到的文件  
		}
        Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 2003
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 2007
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.waibillImporter.messageDialog.fileNotExist"), 
						i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			//当数据不为空时，才进行数据的导入
			if (book != null) {
				importExcelToTable(book);
			}
		} catch (FileException e0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.waibillImporter.messageDialog.fileNotExist"), 
					e0.getMessage(), JOptionPane.WARNING_MESSAGE);
			return;
		} catch (IOException e1) {
			//数据文件被破坏，请重新制作导入文件
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.salesDeptWaybillUI.data.error"), 
					i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.WARNING_MESSAGE);
			return;
		} catch (BusinessException e2) {
			//"导入偏线时效方案: "+e2.getMessage()
			JOptionPane.showMessageDialog(null, "导入偏线时效方案: "+e2.getMessage(), 
					i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.WARNING_MESSAGE);
			return;
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e4) {
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.salesDeptWaybillUI.data.error"), 
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		}
	}
	
	/**
	 * <p>读取Excel详细数据</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-26 09:28:49
	 */
	private void importExcelToTable(Workbook book) {
		Integer index = 1;
		// 默认获取获取工作表1
		Sheet sheet = book.getSheetAt(0);// 默认第一个
        int col = 0;
        try {
        	Iterator rows = sheet.rowIterator();
			// 加个计数器 count
			rows.next();// 第一行标题不读
			if(rows.hasNext()){
				for (; rows.hasNext();) {
					Row row = (Row)rows.next();
					//OuterEffectivePlanEntity ope = new OuterEffectivePlanEntity();
					String temp = null;
					col = 0;
					SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
					for (Iterator cells = row.cellIterator(); cells.hasNext();) {
						Cell cell = (Cell)cells.next();
						//{序号,方案名称,目的站,外发外场	运输类型,承诺最小天数或时间,承诺最大天数或时间,到达代理网点承诺时点,派送承诺需加天数,派送承诺时点,生效时间,截止时间}
						switch (cell.getColumnIndex()) {
							case 0:
								//获得填写的序号
								col ++;
								break;
							case 1:
								//获得填写的方案名称
								col ++;
								/*temp = obtainStringVal(cell);
								if(StringUtils.isNotBlank(temp)) {
									List<OuterEffectivePlanEntity> outerPriceEntities = outerEffectivePlanDao.queryOuterEffectivePlanByName(temp.trim());
									if(CollectionUtils.isNotEmpty(outerPriceEntities)) {
										throw new BusinessException("该方案名称已经存在，不能再次使用！");
									}
								} else {
									throw new BusinessException("第"+index+"行，第"+col+"列未填写方案名称！");
								}
								ope.setName(temp);*/
								break;
							case 2:
								// 目的站
								col ++;
								/*temp = obtainStringVal(cell);
								LOGGER.info("目的站>>" + temp);
                                if(StringUtils.isNotBlank(temp)) {
                                	OuterBranchEntity entity = new OuterBranchEntity();
                                	entity.setAgentDeptName(temp.trim());
                                	List<OuterBranchEntity> obe = vehicleAgencyDeptService.queryVehicleAgencyDepts(entity, 10, 0);
                                	if (obe != null && obe.size() > 0 ) {
                                		ope.setPartialLineCode(obe.get(0).getAgentDeptCode());
                                		ope.setProvCode(obe.get(0).getProvCode());
                                		ope.setCityCode(obe.get(0).getCityCode());
                                		ope.setCountyCode(obe.get(0).getCountyCode());
									} else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列的目的站（“" + temp + "”）在系统中不存在！");
									}
                                } else {
                                	throw new BusinessException("第"+index+"行，第"+col+"列未填写目的站！");
								}*/
								break;
								
							case THREE:
								// 外发外场
								col ++;
								/*temp = obtainStringVal(cell);
								LOGGER.info("外发外场>>"+temp);
								if(StringUtils.isNotBlank(temp)) {
                                	OutfieldEntity entity = new OutfieldEntity();
                                	entity.setName(temp.trim());
                                	List<OutfieldEntity> oe = outfieldService.queryOutfieldByEntity(entity, 0, 10);
                                	if (oe != null && oe.size() > 0) {
										ope.setOutFieldCode(oe.get(0).getOrgCode());
									} else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列的外发外场（“" + temp + "”）在系统中不存在！");
									}
                                } else {
                                	throw new BusinessException("第"+index+"行，第"+col+"列未填写外发外场！");
								}*/
                                
							break;
								
							case FOUR:
								// 运输类型
								col ++;
								/*temp = obtainStringVal(cell);
								LOGGER.info("运输类型>>"+temp);
                                if(StringUtils.isNotBlank(temp)) {
                                	//默认为汽运
                                	if("空运".equals(temp.trim())) {
                                    	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
                                    } else {
                                    	//汽运
                                    	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
    								}
                                } else {
                                	//汽运
                                	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
								}*/
                                
								break;
							case FIVE:
								// 承诺最小天数
								col ++;
								/*String promiseMin = obtainStringVal(cell);
								LOGGER.info("承诺最小天数>>"+promiseMin);
                                if(StringUtils.isNotBlank(promiseMin)) {
                                	ope.setMinTime(Double.valueOf(promiseMin).intValue());
                                	ope.setMinTimeUnit(PricingConstants.TIME_DAY);
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最小天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }*/
								break;
							case SIX:
								// 承诺最大天数
								col ++;
								/*String promiseMax = obtainStringVal(cell);
								LOGGER.info("承诺最大天数或小时>>"+promiseMax);
                                if(StringUtils.isNotBlank(promiseMax)) {
                                	ope.setMaxTime(Double.valueOf(promiseMax).intValue());
                                	ope.setMaxTimeUnit(PricingConstants.TIME_DAY);
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最大天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }*/
								break;
							case SEVEN:
								// 到达营业部承诺时间
								col ++;
								/*String arrvDeptTime = obtainStringVal(cell);
								LOGGER.info("到达偏线承诺时间>>"+arrvDeptTime);
                                if(StringUtils.isNotBlank(arrvDeptTime)) {
                                	if(arrvDeptTime.indexOf(":") > -1) {
                                		String tempStr = arrvDeptTime.replaceAll(":", "");
                                		ope.setArriveOuterBranchTime(tempStr);
                                	} else {
                                		Date d = null;
                                		d = sdf1.parse(arrvDeptTime);
                                		ope.setArriveOuterBranchTime(sdf1.format(d));
                                	}
                                }*/
								break;
							case EIGHT:
								// 承诺派送需加天数
								col ++;
								/*String addDay = obtainStringVal(cell);
								LOGGER.info("承诺派送需加天数>>"+addDay);
                                if(StringUtils.isNotBlank(addDay)) {
                                	ope.setAddDay(Double.valueOf(addDay).intValue());
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写派送承诺需加天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }
								break;
							case 9:
								// 派送承诺时间
								col ++;
								String deleveryTime = obtainStringVal(cell);
								LOGGER.info("派送承诺时间>>"+deleveryTime);
                                if(StringUtils.isNotBlank(deleveryTime)) {
                                	if(deleveryTime.indexOf(":") > -1) {
                                		String tempStr = deleveryTime.replaceAll(":", "");
                                		ope.setDeliveryTime(tempStr);
                                	} else {
                                    	Date d = null;
                                		d = sdf1.parse(deleveryTime);
                                		ope.setDeliveryTime(sdf1.format(d));
                                	}
                                }*/
								break;
							case TEN:
								// 生效日期
								col ++;
								/*temp = obtainStringVal(cell);
								LOGGER.info("生效日期>>"+temp);
								if(StringUtils.isNotBlank(temp)) {
                                	try {
                                		ope.setBeginTime(DateUtils.convert(temp.trim()));
									} catch (Exception e) {
										throw new BusinessException("第"+index+"行，第"+col+"列填写的生效日期（“" + temp + "”）有误！");
									}
                                } else {
                                	throw new BusinessException("第"+index+"行，第"+col+"列未填写生效日期！");
								}*/
								break;
							case ELEVEN:
								// 截止日期
								col ++;
								/*temp = obtainStringVal(cell);
								LOGGER.info("截止日期>>"+temp);
								if(StringUtils.isNotBlank(temp)) {
                                	try {
                                		ope.setEndTime(DateUtils.convert(temp.trim()));
									} catch (Exception e) {
										throw new BusinessException("第"+index+"行，第"+col+"列填写的截止日期（“" + temp + "”）有误！");
									}
                                } else {
                            		ope.setEndTime(DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
								}*/
								break;
							default :
								LOGGER.info("数据不在预期范围");
						}
					}
					//设定主键
					/*temp = UUIDUtils.getUUID();
					ope.setId(temp);
					//获取当前登陆I人编码
					temp = getCurrentUserCode();
					ope.setCreateUserCode(temp);
					ope.setModifyUserCode(temp);
					temp = getCurrentOrgCode();
					ope.setCreateOrgCode(temp);
					ope.setModifyOrgCode(temp);
					
					Date date = new Date();
					ope.setCreateTime(date);
					ope.setModifyTime(date);
					ope.setVersionNo(date.getTime());
					ope.setActive(FossConstants.INACTIVE);
					
					if (outerEffectivePlanDao.insertSelective(ope) <= 0) {
						throw new BusinessException("操作失败，请联系技术支持人员！");
					}*/
					index ++;
				}
			}
		} catch (Exception e) {
			throw new BusinessException("第"+index+"行数据出现异常情况。" + e.getMessage());
		}
	}
	
	/**
	 * @Description: 单元格取值
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:41:24
	 * @param cell
	 * @return
	 * @version V1.0
	 */
	private String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
			// 数值型 
			case HSSFCell.CELL_TYPE_NUMERIC: 
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					cellVal = com.deppon.foss.util.DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
							com.deppon.foss.util.DateUtils.DATE_FORMAT);
					// 纯数字
				} else {
					cellVal = String.valueOf(cell.getNumericCellValue());
				}
				break;
			// 此行表示单元格的内容为string类型
			// 字符串型
			case HSSFCell.CELL_TYPE_STRING: 
				cellVal = cell.getRichStringCellValue().toString();
				break;
			// 公式型
			case HSSFCell.CELL_TYPE_FORMULA:
				// 读公式计算值
				cellVal = String.valueOf(cell.getNumericCellValue());
				if (cellVal.equals("NaN")) {
					// 如果获取的数据值为非法值,则转换为获取字符串
					cellVal = cell.getRichStringCellValue().toString();
				}
				break;
				// 布尔
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellVal = " " + cell.getBooleanCellValue();
				break;
			// 此行表示该单元格值为空
			// 空值
			case HSSFCell.CELL_TYPE_BLANK: 
				cellVal = "";
				break;
			// 故障
			case HSSFCell.CELL_TYPE_ERROR: 
				cellVal = "";
				break;
			default:
				cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal;
	}


	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}	
}