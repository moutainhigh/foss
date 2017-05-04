/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/UploadVoucherPanel.java
 * 
 * FILE NAME        	: UploadVoucherPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.changingexp.client.ui.internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Decoder;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Encoder;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.JImageChooser;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.JImageViewDialog;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.ButtonColumn;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadVoucherDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 上传凭证面板
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-30 下午2:49:35
 */
@ContainerSeq(seq=11)
public class UploadVoucherPanel extends JPanel {

	private static final int TWO = 2;

	private static final int EIGHTY = 80;

	private static final String DEFAULTGROW = "default:grow";

	private static final int FILESIZE = 250;

	private static final int K = 1024;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 8842939049457465320L;
	
	/**
	 * 日志类
	 */
	private static final Logger LOG = Logger.getLogger(UploadVoucherPanel.class);

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(UploadVoucherPanel.class);

	/**
	 * 凭证显示表格
	 */
	private JXTable table;

	/**
	 * 凭证增加按钮
	 */
	@FocusSeq(seq=3)
	private JButton btnAdd = new JButton(i18n.get("pickup.changingexp.add"));

	/**
	 * 凭证删除按钮
	 */
	@FocusSeq(seq=4)
	private JButton btnDelete = new JButton(i18n.get("pickup.changingexp.delete"));
	
	/**
	 * 上传凭证列
	 */
	@FocusSeq(seq=1)
	private ButtonColumn uploadColumn;
	
	/**
	 * 查看凭证列
	 */
	@FocusSeq(seq=2)
	private ButtonColumn viewColumn;

	/**
	 * 
	 * 构造方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:50:50
	 */
	public UploadVoucherPanel() {
		init();
		createListener();
	}

	/**
	 * 
	 * 创建按钮监听
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:52:01
	 */
	private void createListener() {
		uploadColumn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 选中的凭证对象
				UploadVoucherDto dto = getSelectedDto();

				//弹出图片选择器
				JImageChooser dialog = new JImageChooser();
				int option = dialog.showOpenDialog(ApplicationContext.getApplication().getWorkbench().getFrame());
				if (JImageChooser.APPROVE_OPTION == option) {
					//获取选中的图片文件
					File selectedFile = dialog.getSelectedFile();
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(selectedFile);
						//读取文件大小
						int length = fis.available();
						//限制文件大小不能超过250K
						if (length > FILESIZE * K) {
							MsgBox.showError(i18n.get("pickup.changingexp.uploadLimit"));
						} else {
							byte[] bytes = new byte[length];
							fis.read(bytes);
							String fileName = selectedFile.getName();
							//设置文件类型 JPG PNG等
							int index = fileName.lastIndexOf(".");
							if(index >= 0){
								dto.setProofType(fileName.substring(index+1).toUpperCase());
							}else{
								dto.setProofType(null);
							}
							//默认文件不会随上传的文件覆盖文件名
							if (!dto.getIsDefault()) {
								if(index >= 0){
									dto.setProofName(fileName.substring(0, index));
								}else{
									dto.setProofName(fileName);
								}
							}
							//加密字节码
							BASE64Encoder encoder = new BASE64Encoder();
							dto.setProofBytes(encoder.encode(bytes));
							MsgBox.showInfo(i18n.get("pickup.changingexp.uploadSuccess", dto.getProofName()));
						}
					} catch (FileNotFoundException e1) {
						LOG.error(e1.getMessage(),e1);
					} catch (IOException e1) {
						LOG.error(e1.getMessage(),e1);
					} finally {
						if(fis != null){
							try {
								fis.close();
							} catch (IOException e1) {
								LOG.error(e1);
							}
						}
					}
				}
			}
		});
		
		
		
		viewColumn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 选中的凭证对象
				UploadVoucherDto dto = getSelectedDto();
				//图片尚未上传
				if (StringUtil.isEmpty(dto.getProofBytes())) {
					MsgBox.showInfo(i18n.get("pickup.changingexp.mustUploadVoucher"));
				} else {
					BASE64Decoder decoder = new BASE64Decoder();
					ByteArrayInputStream bais = null;
					try {
						//字符串解密
						byte[] bytes = decoder.decodeBuffer(dto.getProofBytes());
						bais = new ByteArrayInputStream(bytes);
						//在内存中构造缓冲图片
						BufferedImage bufferImg = ImageIO.read(bais);
						//通过预览对话框弹出显示
						JImageViewDialog dialog = new JImageViewDialog(bufferImg);
						WindowUtil.centerAndShow(dialog);
					} catch (IOException e1) {
						LOG.error(e1.getMessage(),e1);
					} finally{
						if(bais != null){
							try {
								bais.close();
							} catch (IOException e1) {
								LOG.error(e1.getMessage(),e1);
							}
						}
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取表格数据模型
				UploadVoucherTableModel uploadVoucherTableModel = ((UploadVoucherTableModel) table.getModel());
				List<UploadVoucherDto> uploadVoucherList = uploadVoucherTableModel.getData();
				//增加凭证对象
				uploadVoucherList.add(new UploadVoucherDto());
				uploadVoucherTableModel.fireTableDataChanged();
			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//获取表格数据模型
				UploadVoucherTableModel uploadVoucherTableModel = ((UploadVoucherTableModel) table.getModel());
				List<UploadVoucherDto> uploadVoucherList = uploadVoucherTableModel.getData();
				int row = table.getSelectedRow();
				if (row >= 0) {
					//获取选中的凭证对象
					UploadVoucherDto vo = uploadVoucherList.get(row);
					//默认凭证不允许删除
					if (vo.getIsDefault()) {
						MsgBox.showError(i18n.get("pickup.changingexp.voucherNoDelete"));
					} else {
						//删除选中行 
						uploadVoucherList.remove(vo);
						uploadVoucherTableModel.fireTableDataChanged();
					}
				}
			}
		});
	}

	/**
	 * 
	 * 布局初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:51:19
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changingexp.uploadVoucher")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("37dlu:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 1, 5, 1, fill, fill");

		table = new JXTable(){
			private static final long serialVersionUID = 6865880807055236003L;

			@Override
			public boolean isCellEditable(int row, int column) {
			    if(column == 0){
			    	return false;
			    }else{
			    	return getModel().isCellEditable(
			    			convertRowIndexToModel(row),
			    			convertColumnIndexToModel(column));
			    }
			}
		};
		table.setTableHeader(null);
		scrollPane.setViewportView(table);
		
		add(btnAdd, "4, 3");

		add(btnDelete, "6, 3");

		table.setModel(new UploadVoucherTableModel());
		
		// 添加Button样式
		uploadColumn = TableFactory.createButtonColumn(table, 1, EIGHTY);
		
		viewColumn = TableFactory.createButtonColumn(table, TWO, EIGHTY);
		
		TableFactory.createRowColumn(table);
	}

	/**
	 * 
	 * 获取表格选中行
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:52:32
	 */
	protected UploadVoucherDto getSelectedDto() {
		int row = table.getSelectedRow();
		return getTableData().get(row);
	}

	/**
	 * 
	 * 获取表格数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:52:45
	 */
	public List<UploadVoucherDto> getTableData() {
		UploadVoucherTableModel uploadVoucherTableModel = ((UploadVoucherTableModel) table.getModel());
		return uploadVoucherTableModel.getData();
	}

	/**
	 * 
	 * 表格设置数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:52:58
	 */
	public void setUploadVoucherList(List<UploadVoucherDto> uploadVoucherList) {
		UploadVoucherTableModel uploadVoucherTableModel = ((UploadVoucherTableModel) table.getModel());
		// 刷新表格数据
		uploadVoucherTableModel.setData(uploadVoucherList);
		uploadVoucherTableModel.fireTableDataChanged();
	}
	
	/**
	 * 
	 * 获取表格数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:53:25
	 */
	public List<UploadVoucherDto> getUploadVoucherList() {
		UploadVoucherTableModel uploadVoucherTableModel = ((UploadVoucherTableModel) table.getModel());
		return uploadVoucherTableModel.getData();
	}

	/**
	 * 
	 * 上传凭证数据模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:54:02
	 */
	private class UploadVoucherTableModel extends DefaultTableModel {

		private static final int ZERO = 0;

		private static final int ONE = 1;
		
		private static final int THREE = 3;
		
		/**
		 * 序列化
		 */
		private static final long serialVersionUID = 4758119136571312332L;

		/**
		 * 表格数据
		 */
		private List<UploadVoucherDto> uploadVoucherList;

		/**
		 * 
		 * 获取表格数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午8:55:19
		 */
		public List<UploadVoucherDto> getData() {
			return uploadVoucherList;
		}

		/**
		 * 
		 * 设置表格数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午8:55:32
		 */
		public void setData(List<UploadVoucherDto> uploadVoucherList) {
			this.uploadVoucherList = uploadVoucherList;
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			 if (column == 0) {
				 uploadVoucherList.get(row).setProofName((String) aValue);
			 }
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column > 0 || (column == 0 && !uploadVoucherList.get(row).getIsDefault());
		}

		@Override
		public int getColumnCount() {
			return THREE;
		}

		@Override
		public int getRowCount() {
			return uploadVoucherList == null ? 0 : uploadVoucherList.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return uploadVoucherList.get(row).getProofName();
			case ONE:
				return i18n.get("pickup.changingexp.upload");
			case TWO:
				return i18n.get("pickup.changingexp.view");
			default:
				return super.getValueAt(row, column);
			}
		}
	}

	/**
	 * 
	 * 添加按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:56:00
	 */
	public JButton getBtnAdd() {
		return btnAdd;
	}

	/**
	 * 
	 * 删除按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:56:07
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}

	/**
	 * 凭证表格
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:56:17
	 */
	public JXTable getTable() {
		return table;
	}

}