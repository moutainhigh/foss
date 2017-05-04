package com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.CommonSortedTableModel;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 批量导入需要更改的电子面单数据的Dialog
 * @author Foss-105888-Zhangxingwang
 * @date 2015-1-31 10:31:34
 */
public class BatchChangeEWaybilInfoDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	//国际化
	private static final II18n i18n = I18nManager.getI18n(BatchChangeEWaybilInfoDialog.class);
	
	//日志
	private static final Logger LOGGER = Logger.getLogger(BatchChangeEWaybilInfoDialog.class);

	private static final int FOUR = 4;
	
	private static final int THREE = 3;

	private static final int FIVE = 5;
	
	private static final int NUM_401 = 401;

	private static final int NUM_714 = 714;
	
	//导入
	private JButton btnImport;
	
	//提交
	private JButton btnSubmit;
	
	//表格
	private JXTable table;
	
	//导入详情
	private JLabel lblImportInfo;
	
	//TableModel
	private ImportBatchChangeInfoTableModel tableModel;
	
	//初始化运单服务对象
	private IWaybillService waybillService;
	
	//对应的UI
	private ExpWaybillBatchChangeWeightUI ui;

	public BatchChangeEWaybilInfoDialog() {
		init();
		addListener();
	}
	
	private void addListener() {
		//批量导入事件
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogType(JFileChooser.SAVE_DIALOG);
				fc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xls", "xlsx"));// 添加文本文件过滤
				int result = fc.showOpenDialog(ui);
				File uploadFile = null;
				if(result == JFileChooser.APPROVE_OPTION){
					uploadFile = fc.getSelectedFile();
				}
				Workbook book = null;
				FileInputStream inputStream = null;
				try {
					if (uploadFile != null) {
						inputStream = new FileInputStream(uploadFile);
						try {
							// 2007
							book = new XSSFWorkbook(inputStream);
						} catch (Exception ex) {
							// 2003
							book = new HSSFWorkbook(inputStream);
						}
					} else {
						MsgBox.showInfo("请选择导入文件");
						return;
					}
					if (book != null) {
						importWaybillChange(book);
					}
				} catch (FileException e0) {
					MsgBox.showInfo("导入批量更改重量: "+e0.getMessage());
					return;
				} catch (IOException e1) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptWaybillUI.data.error"));
					return;
				} catch (BusinessException e2) {
					MsgBox.showInfo("导入批量更改重量: "+e2.getMessage());
					return;
				} finally {
					if (book != null) {
						book = null;
					}
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e3) {
							MsgBox.showInfo("文件关闭失败");
							return;
						}
					}
				}
			}
		});
		
		//提交事件
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					waybillService = WaybillServiceFactory.getWaybillService();
					List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList = tableModel.getData();
	    			//进行相关数据的提交
	    			if(CollectionUtils.isNotEmpty(expBatchChangeWeightDtoList) && table.getRowCount() > 0){
	    				waybillService.commitBatchChangeWeight(expBatchChangeWeightDtoList);
	    				//提交 成功之后给出提醒
	    				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCommitAction.msgBox.waybillRfcBatchSubmit"));
	    			}else{
	    				MsgBox.showInfo(i18n.get("foss.waybillRfc.waybillRfc.except.submit.data.isNull"));
	    				return;
	    			}
				} catch (BusinessException e1) {
					LOGGER.error("运单号提交更改出现NullPointerException"+e1.getMessage(), e1);
					MsgBox.showInfo("运单号提交更改时出现业务异常："+e1.getMessage());
				}
				dispose();
			}
		});
	}

	/**
	 * 批量导入电子面单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-31 10:32:29
	 * @param book
	 */
	private void importWaybillChange(Workbook book) {
        List<ExpBatchChangeWeightDto> data = new ArrayList<ExpBatchChangeWeightDto>();
        ExpBatchChangeWeightDto xlsDto = null;
        DecimalFormat df = new DecimalFormat("0");
        waybillService = WaybillServiceFactory.getWaybillService();
        try {
        	String temp = null;
        	// 循环工作表Sheet
            for (int numSheet = 0; numSheet < book.getNumberOfSheets(); numSheet++) {
            	Sheet hssfSheet = book.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }
                    xlsDto = new ExpBatchChangeWeightDto();
                    // 循环列Cell

                    //运单号
                    Cell cellWaybillNo = hssfRow.getCell(0);
                    if (cellWaybillNo == null) {
                    	 MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+1+"列单号不能为空！");
                         return;
                    }
                    temp = df.format(cellWaybillNo.getNumericCellValue());
                    if(StringUtils.isEmpty(temp)){
                   	 	MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+1+"列单号长度不能小于8个或者大于12个字符！");
                        return;
                    }
                    xlsDto.setWaybillNo(temp.trim());
                    Cell cellWeight = hssfRow.getCell(1);
                    if (cellWeight == null) {
                    	MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+2+"列未填写重量！");
                    	return;
                    }
                    //重量
                    try {
                    	temp = obtainStringVal(cellWeight);
                    	if(StringUtils.isNotBlank(temp)){
                    		BigDecimal temp_weight = new BigDecimal(temp);
                    		String one_weight = waybillService.queryConfValueByCode(WaybillConstants.ONEGOODSQTYTOTAL_WEIGHT);
                    		if(null!=one_weight&&new BigDecimal(one_weight).compareTo(temp_weight)==-1){
                    	       MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+2+"列重量超过上限！上限为："+one_weight+"kg");
                    	       return;
                    		}
                    		//cn-199 大客户电子运单更改导入时待提交的信息与导入的信息匹配有问题且无法导入
    						xlsDto.setWeightChanged(new BigDecimal(temp));
                    	}
					} catch (Exception e) {
						MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+2+"列重量数据转换异常！"+e.getMessage());
                    	return;
					}
                    Cell cellVolume = hssfRow.getCell(2);
                    if (cellVolume != null) {
                        //体积
                        try {
                        	temp = obtainStringVal(cellVolume);
                        	if(StringUtils.isNotBlank(temp)){
                        		BigDecimal temp_volume = new BigDecimal(temp);
                        		String one_volume = waybillService.queryConfValueByCode(WaybillConstants.ONEGOODSQTYTOTAL_VOLUME);
                        		if(null!=one_volume&&new BigDecimal(one_volume).compareTo(temp_volume)==-1){
                        		   MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+3+"列体积超过上限！上限为："+one_volume+"m³");
                         	       return;
                        		}
        						xlsDto.setVolumeChanged(new BigDecimal(temp));
                        	}
    					} catch (Exception e) {
    						MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+3+"列体积数据转换异常！"+e.getMessage());
                        	return;
    					}
                    }
                    //start
                    //装卸费 
                   /* Cell cellzxf = hssfRow.getCell(3);
                    if(cellzxf==null){
                    	MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+4+"列装卸费不能为空！");
                    	return;
                    }else{
                        try {
                        	temp = obtainStringVal(cellzxf);
                        	if(StringUtils.isNotBlank(temp)){
        						xlsDto.setServicefee(new BigDecimal(temp));
                        	}
    					} catch (Exception e) {
    						MsgBox.showInfo("第"+(numSheet+1)+"页签，"+"第"+rowNum+"行，第"+3+"列装卸费数据转换异常！"+e.getMessage());
                        	return;
    					}
                    }*/
                    //end
                    Cell cellNote = hssfRow.getCell(FOUR);
                    if (cellNote != null) {
                        //备注
                        xlsDto.setChangeNote(obtainStringVal(cellNote));
                    }
                    data.add(xlsDto);
                }
            }
			//因为Excel的第一列不算，故需要减1
			lblImportInfo.setText("本次导入"+data.size()+"条，实际导入"+data.size()+"条");
			tableModel.setData(data);
			tableModel.fireTableDataChanged();
		} catch (Exception e) {
        	MsgBox.showInfo("数据出现异常情况:" + e.getMessage());
        	return;
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

	/**
	 * 初始化
	 */
	public void init(){
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_714, NUM_401);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		btnImport = new JButton(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.importTable"));
		panel.add(btnImport);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("326px"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblImportInfo = new JLabel("本次导入0条，实际导入0条");
		panel_1.add(lblImportInfo, "1, 2, 23, 1, left, top");
		
		btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));
		panel_1.add(btnSubmit, "28, 2");
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JXTable();
		table.setColumnControlVisible(true);
		tableModel = new ImportBatchChangeInfoTableModel();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		// 设置模态
		setModal(true);
	}
	
	
	class ImportBatchChangeInfoTableModel extends CommonSortedTableModel {
		private static final long serialVersionUID = 5883365603131625962L;

		private List<ExpBatchChangeWeightDto> data;

		public void setData(List<ExpBatchChangeWeightDto> data) {
			this.data = data;
		}

		public List<ExpBatchChangeWeightDto> getData() {
			return data;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == 0;
		}
		
		//操作列,更改状态,订单号,运单号,重量,体积,费用信息,产品性质,失败原因
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.rownum");//序号
			case 1:
				return i18n.get("foss.gui.creating.numberPanel.waybillNo.label");//运单号
			case 2:
				return i18n.get("foss.gui.creating.linkTableMode.column.eleven");//重量
			case THREE:
				return i18n.get("foss.gui.creating.linkTableMode.column.twelve");//体积
			case FOUR:
				/*return i18n.get("foss.gui.creating.waybillEditUI.ServiceFee.label");//装卸费
			case 5:*/
				return i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.remark");//备注
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return FIVE;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return row+1;
			case 1:
				return data.get(row).getWaybillNo();//运单号
			case 2:
				return data.get(row).getWeightChanged();//重量
			case THREE:
				return data.get(row).getVolumeChanged();//体积
			case FOUR:
				return data.get(row).getServicefee(); //装卸费
			case FIVE:
				return data.get(row).getChangeNote();//备注
			default:
				return "";
			}
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {

		}

		@Override
		public boolean isSortedColumn(int column) {
			return true; // 所有的列都排序
		}
	}
}