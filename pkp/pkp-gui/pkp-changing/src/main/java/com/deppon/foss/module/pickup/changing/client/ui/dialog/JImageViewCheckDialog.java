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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/JImageViewCheckDialog.java
 * 
 * FILE NAME        	: JImageViewCheckDialog.java
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
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Decoder;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.internal.UploadVoucherPanel;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;

/**
 * 
 * 图片批量查看对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午8:00:44
 */
public class JImageViewCheckDialog extends JDialog {

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(JImageViewCheckDialog.class); 

	/**
	 * 500
	 */
	private static final int FIVEHUNDRED = 500;
	/**
	 * 700
	 */
	private static final int SEVENHUNDRED = 700;
	/**
	 * 日志类
	 */
	private static final Logger LOG = Logger
			.getLogger(UploadVoucherPanel.class);
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -8400588573233915794L;

	/**
	 * service
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();
	
	
	/**
	 * 图片显示面板
	 */
	private ZPanel imagePanel;
	
	/**
	 * 文件名
	 */
	private JLabel fileNameLbl;
	
	/**
	 * 上一张
	 */
	private JButton beforeBtn;
	
	/**
	 * 下一张
	 */
	private JButton nextBtn;

	/**
	 * 记录已经下载的图片
	 *  <filepath,picBufferInfo>
	 */
	private Map<String, String> picDownLoadList = new HashMap<String, String>();
	
	/**
	 *  接收来的下载凭证路径 的集合
	 */
	List<WaybillRfcProofEntity> waybillRfcProofInfoList;

	/**
	 * 图片索引位置
	 */
	private int imageIndex;

	/**
	 * 
	 * 实例化图片查看器Dialog
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:05:07
	 */
	public JImageViewCheckDialog(List<WaybillRfcProofEntity> waybillRfcProofList) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		waybillRfcProofInfoList = waybillRfcProofList;
		init();
		createListener();
		showDefaultImage();
	}

	/**
	 * 
	 * 显示默认图片
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:05:43
	 */
	private void showDefaultImage() {
		if (waybillRfcProofInfoList.size() > 0) {
			showImage(0);
		}
	}

	/**
	 * 
	 * 根据index显示相应的图片
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:05:53
	 */
	private void showImage(int imageIndex) {
		if (imageIndex < 0 || imageIndex >= waybillRfcProofInfoList.size())
			return;
		BufferedImage bufferImage = getBufferImage(imageIndex);
		imagePanel.drawImage(bufferImage);
		this.imageIndex = imageIndex;
	}

	/**
	 * 
	 * 获取图片对象
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:06:12
	 */
	private BufferedImage getBufferImage(int imageIndex) {
		BufferedImage bufferImg = null;
		String image = getImage(imageIndex);
		if (image != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			ByteArrayInputStream bais = null;
			try {
				byte[] bytes = decoder.decodeBuffer(image);
				bais = new ByteArrayInputStream(bytes);
				bufferImg = ImageIO.read(bais);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			} finally {
				if (bais != null) {
					try {
						bais.close();
					} catch (IOException e1) {
						LOG.error(e1.getMessage(),e1);
					}
				}
			}
		}
		return bufferImg;
	}

	/**
	 * 
	 * 访问服务器获取图片资源
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:06:29
	 */
	private String getImage(int imageIndex) {
		String picPath = waybillRfcProofInfoList.get(imageIndex)
				.getRfcProofPath();
		String proofName = waybillRfcProofInfoList.get(imageIndex)
				.getRfcProofName();
		fileNameLbl.setText(proofName);
		String picBufferInfo = null;
		if (isPicPathLoad(picPath)) {
			picBufferInfo = picDownLoadList.get(picPath);
		} else {
			picBufferInfo = waybillRfcVarificationService
					.queryWaybillRfcProofByFilePath(picPath);
			picDownLoadList.put(picPath, picBufferInfo);
		}
		return picBufferInfo;
	}

	/**
	 * 
	 * 图片是否已加载过
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:06:57
	 */
	private boolean isPicPathLoad(String picPath) {
		return picDownLoadList.containsKey(picPath);
	}
	
	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {

		setLayout(new BorderLayout());
		//凭证图片查看
        setTitle(i18n.get("foss.gui.changing.imageViewCheckDialog.title"));
		JPanel leftPanel = new JPanel();
		//上张
		beforeBtn = new JButton(i18n.get("foss.gui.changing.imageViewCheckDialog.prev"));

		JPanel rightPanel = new JPanel();
		//下张
		nextBtn = new JButton(i18n.get("foss.gui.changing.imageViewCheckDialog.next"));

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		rightPanel.add(nextBtn, BorderLayout.SOUTH);
		leftPanel.add(beforeBtn, BorderLayout.SOUTH);

		fileNameLbl = new JLabel();

		this.add(fileNameLbl, BorderLayout.SOUTH);
		imagePanel = new ZPanel();
		JScrollPane scrollPane = new JScrollPane(imagePanel);
		this.add(scrollPane, BorderLayout.CENTER);

	

		setSize(SEVENHUNDRED, FIVEHUNDRED);

		setModal(true);

	}

	/**
	 * 
	 * 添加按钮监听
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:07:13
	 */
	private void createListener() {
		beforeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = getPrevImageIndex();
				showImage(index);
			}

		});
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = getNextImageIndex();
				showImage(index);
			}
		});
	}

	/**
	 * 
	 * 获取下一张图片位置
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:07:23
	 */
	protected int getNextImageIndex() {
		return imageIndex + 1;
	}

	/**
	 * 
	 * 获取上一张图片位置
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:07:23
	 */
	private int getPrevImageIndex() {
		return imageIndex - 1;
	}

	/**
	 * 
	 * 图片面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:07:42
	 */
	private class ZPanel extends JPanel {
		/**
		 * 序列化对象
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * 图片对象
		 */
		private BufferedImage image;
		
		/**
		 * 最大的图片的高度
		 */
		private static final int MAXHEIGHT=2000;
		
		/**
		 * 最大的图片的宽度
		 */
		private static final int MAXWIDTH=2000;


		public void drawImage(BufferedImage image) {
			this.image = image;
			repaint();
		}

		@Override
		public void paintComponent(Graphics g1) {
			int x = 0;
			int y = 0;
			//清空画布的最大区域
			g1.clearRect(x, y,MAXWIDTH, MAXHEIGHT);
			
			if (null == image) {
				return;
			}
		
			//绘制图片
			g1.drawImage(image, x, y, image.getWidth(this),
					image.getHeight(this), this);
		}
	}

}