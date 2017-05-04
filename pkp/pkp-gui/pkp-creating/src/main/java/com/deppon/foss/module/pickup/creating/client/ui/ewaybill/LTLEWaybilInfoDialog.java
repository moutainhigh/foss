package com.deppon.foss.module.pickup.creating.client.ui.ewaybill;

import javax.swing.JDialog;
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
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
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
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.CommonSortedTableModel;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JScrollPane;

/**
 * 批量导入需要更改的电子面单数据的Dialog
 */
public class LTLEWaybilInfoDialog extends JDialog {
	
	//序列号
	private static final long serialVersionUID = 1L;

	//国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybilInfoDialog.class);
	
	//日志
	private static final Logger LOGGER = Logger.getLogger(LTLEWaybilInfoDialog.class);
	
	//定义常量
	private static final int ZERO = 0;
	
	private static final int ONE = 1;
	
	private static final int TWO = 2;
	
	private static final int THREE = 3;
	
	private static final int FIVE = 5;
	
	private static final int EIGHT = 8;
	
	private static final int TWELVE = 12;
	
	private static final int ONE_HUNDRED = 100;

	private static final int FOUR_HUNDRED_ONE = 401;
	
	private static final int FIVE_HUNDRED = 500;
	
	private static final int SEVEN_HUNDRED_FOURTEEN = 714;
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
	private LTLEWaybillImportWeightUI ui;

	public LTLEWaybilInfoDialog() {
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
					List<LTLEWaybillChangeWeightDto> expBatchChangeWeightDtoList = tableModel.getData();
	    			//进行相关数据的提交
	    			if(CollectionUtils.isNotEmpty(expBatchChangeWeightDtoList) && table.getRowCount() > ZERO){
	    				waybillService.commitLTLBatchChangeWeight(expBatchChangeWeightDtoList);
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
	 * @param book
	 */
	private void importWaybillChange(Workbook book) {
        List<LTLEWaybillChangeWeightDto> data = new ArrayList<LTLEWaybillChangeWeightDto>();
        List<LTLEWaybillChangeWeightDto> newdata = new ArrayList<LTLEWaybillChangeWeightDto>();
        LTLEWaybillChangeWeightDto xlsDto = null;
        DecimalFormat df = new DecimalFormat("0");
        waybillService = WaybillServiceFactory.getWaybillService();
        try {
        	importValidate(book, data, newdata, xlsDto, df);
		} catch (Exception e) {
        	MsgBox.showInfo("导入模版或内容错误");
        	return;
		}
	}

	private void importValidate(Workbook book,
			List<LTLEWaybillChangeWeightDto> data,
			List<LTLEWaybillChangeWeightDto> newdata,
			LTLEWaybillChangeWeightDto xlsDto, DecimalFormat df)
			throws WaybillValidateException {
		String temp = null;
		// 循环工作表Sheet
		boolean flag = true;
		for (int numSheet = ZERO; numSheet < book.getNumberOfSheets(); numSheet++) {
			Sheet hssfSheet = book.getSheetAt(numSheet);
		    if (hssfSheet == null) {
		        continue;
		    }
		    // 循环行Row
		    for (int rowNum = ZERO; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		    	Row hssfRow = hssfSheet.getRow(rowNum);
		    	
		        if (hssfRow == null) {
		            continue;
		        }
		        //运单号
		        if (rowNum == ZERO) {
		        	if (!(hssfRow.getCell(ZERO).toString().equals("运单号")&&hssfRow.getCell(ONE).toString().equals("重量")&&hssfRow.getCell(TWO).toString().equals("体积"))) {
		        		throw new WaybillValidateException("导入模版或内容错误");
					}
		        	continue;
		        }
		        xlsDto = new LTLEWaybillChangeWeightDto();
		        if (newdata.size() < FIVE_HUNDRED) {
		        	// 导入数据有500条时，不继续添加数据，记录数据总数
		        	data.add(xlsDto);
				}else {
					data.add(xlsDto);
					continue;
				}
		        Cell cellWaybillNo = hssfRow.getCell(ZERO);
		        if (cellWaybillNo == null) {
		        	if (flag) {
		        		MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+ONE+"列单号不能为空！");
		        		flag = false;
					}
		        	 continue;
		        }
		        temp = df.format(cellWaybillNo.getNumericCellValue());
		        if(temp.length() < EIGHT || temp.length() > TWELVE){
		        	if (flag) {
		        		MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+ONE+"列单号长度不能小于8个或者大于12个字符！");
		        		flag = false;
					}
		       	 	continue;
		        }
		        xlsDto.setWaybillNo(temp.trim());
		        Cell cellWeight = hssfRow.getCell(ONE);
		        if (cellWeight == null) {
		        	if (flag) {
		        		MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+TWO+"列未填写重量！");
		        		flag = false;
					}
		        	continue;
		        }
		        //重量
		        try {
		        	temp = obtainStringVal(cellWeight);
		        	if(StringUtils.isNotBlank(temp)){
		        		//电子运单更改导入时待提交的信息与导入的信息匹配有问题且无法导入
						xlsDto.setWeightChanged(new BigDecimal(temp));
		        	}
				} catch (Exception e) {
					if (flag) {
						MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+TWO+"列重量数据转换异常！"+e.getMessage());
		        		flag = false;
					}
					continue;
				}
		        Cell cellVolume = hssfRow.getCell(TWO);
		        if (cellVolume == null) {
		        	if (flag) {
		        		MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+THREE+"列未填写体积！");
		        		flag = false;
					}
		        	continue;
				}
		        //体积
		        try {
		        	temp = obtainStringVal(cellVolume);
		        	if(StringUtils.isNotBlank(temp)){
						xlsDto.setVolumeChanged(new BigDecimal(temp));
		        	}
				} catch (Exception e) {
					if (flag) {
						MsgBox.showInfo("第"+(numSheet+ONE)+"页签，"+"第"+rowNum+"行，第"+THREE+"列体积数据转换异常！"+e.getMessage());
		        		flag = false;
					}
					continue;
				}
		        newdata.add(xlsDto);
		    }
		}
		//具体显示表格中有多少条数据，以及本次能识别导入的数据
		lblImportInfo.setText("本次导入"+data.size()+"条，实际导入"+newdata.size()+"条");
		//把识别出来的数据显示给前台页面
		tableModel.setData(newdata);
		tableModel.fireTableDataChanged();
	}
	
	/**
	 * 从单元格中取值
	 * @author fei 305082
	 * @date 2016-6-16下午1:52:06
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
				cellVal = " ";
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
	 * 初始化导入模板
	 * @author fei 305082
	 * @date 2016-6-16下午1:53:10
	 */
	public void init(){
		setBounds(ONE_HUNDRED, ONE_HUNDRED, SEVEN_HUNDRED_FOURTEEN, FOUR_HUNDRED_ONE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		btnImport = new JButton(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.LTLEWaybillChangeEWaybilInfoDialog.importTable"));
		panel.add(btnImport);
		
		JPanel panel2 = new JPanel();
		getContentPane().add(panel2, BorderLayout.SOUTH);
		panel2.setLayout(new FormLayout(new ColumnSpec[] {
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
		//初始导入时默认显示0条数据
		lblImportInfo = new JLabel("本次导入0条，实际导入0条");
		panel2.add(lblImportInfo, "1, 2, 23, 1, left, top");
		
		btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));
		panel2.add(btnSubmit, "28, 2");
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
	
	/**
	 * 内部类，设置导入的表格头和识别出来的具体数据
	 * @author 305082
	 *
	 */
	class ImportBatchChangeInfoTableModel extends CommonSortedTableModel {

		private static final long serialVersionUID = -2848946612679538112L;
		
		private List<LTLEWaybillChangeWeightDto> data;

		public void setData(List<LTLEWaybillChangeWeightDto> data) {
			this.data = data;
		}

		public List<LTLEWaybillChangeWeightDto> getData() {
			return data;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == 0;
		}
		
		//导入的序号，运单号，重量，体积的表格头
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.eight");//序号
			case ONE:
				return i18n.get("foss.gui.creating.numberPanel.waybillNo.label");//运单号
			case TWO:
				return i18n.get("foss.gui.creating.linkTableMode.column.eleven");//重量
			case THREE:
				return i18n.get("foss.gui.creating.linkTableMode.column.twelve");//体积
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

		//设置能识别出来批量导入的数据
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return row+ONE;
			case ONE:
				return data.get(row).getWaybillNo();//运单号
			case TWO:
				return data.get(row).getWeightChanged();//重量
			case THREE:
				return data.get(row).getVolumeChanged();//体积
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