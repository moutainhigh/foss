/**
 * Project Name:pkp-common
 * File Name:DragingFrame.java
 * Package Name:com.deppon.foss.module.pickup.common.client.ui
 * Date:2014-9-30上午11:09:23
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
 
/**
 * 图片展示组件
 * ClassName:DragingFrame <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * 
 * Date:     2014-9-30 上午11:09:23 <br/>
 * @author   157229-zxy
 * @version  
 * @since    JDK 1.6
 */
public class PictureViewComp extends JPanel {
	
	//设置获取图片超时时间为1分钟
	private final static int READ_TIME_OUT = 60000; 
	//滚动条
	ImageSlider imageSlider = null;
	//图片显示面板
	ImagePanel imagePanel = null;
	
	private PictureRotateComp pictureRotateComp = null;
	
	//放大倍数
	private int multiple = 2;
	// 日志
	private static final Log log = LogFactory.getLog(PictureViewComp.class);
	//远程服务
	IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
			.getService(IWaybillHessianRemoting.class);
	
	/**
	 * @需求：智能开单项目
	 * @功能：保存旋转图片时间（二期删除）
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19上午10:01
	 */
	WaybillPanelVo bean;
	
    public PictureViewComp(int width,int height) throws HeadlessException {
    	this.setLayout(null);
        this.setSize(width, height);
    	ImageSlider imageSlider = new ImageSlider(multiple);
    	imagePanel = new ImagePanel(imageSlider);
    	imagePanel.setBounds(2, 2, width - 2, height - 2);
    	this.setBorder(BorderFactory.createLoweredBevelBorder());
        imageSlider.setBounds(width - NumberConstants.NUMBER_100, NumberConstants.NUMBER_50, NumberConstants.NUMBER_50, NumberConstants.NUMBER_200);
        pictureRotateComp = new PictureRotateComp(imagePanel);
        pictureRotateComp.setBounds(width - NumberConstants.NUMBER_130, NumberConstants.NUMBER_30, NumberConstants.NUMBER_90, NumberConstants.NUMBER_18);
        add(pictureRotateComp);
        add(imageSlider);
        add(imagePanel);
    }
    
    public void loadImage(String filePath) throws Exception{
    	if(StringUtils.isBlank(filePath))
    		throw new Exception("文件路径不能为空");
    	imagePanel.openImage(filePath);
    }
    
    public void loadImage(Image img){
    	imagePanel.openImage(img);
    }
    
    /**
     * 根据运单号加载图片
     * @param waybillNo
     * @throws Exception 
     */
    public void loadImageByWaybillNo(String waybillNo) throws Exception{
    	if(StringUtils.isBlank(waybillNo))
    		throw new Exception("运单号不能为空");
		try {
			byte[] is = queryImage(waybillNo);
			if(is != null && is.length > 0){
				InputStream buffin = new ByteArrayInputStream(is); 
				BufferedImage img = ImageIO.read(buffin);
				loadImage(img);
			}else{
				throw new Exception("获取不到图片信息");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
  //更具运单号获取图片
  	public byte[] queryImage(String waybillNo) {
  		
  		WaybillPictureEntity waybillPictureEntity = waybillRemotingService.queryWaybillPictureByWaybillNo(waybillNo);
  		String filePath = waybillPictureEntity.getFilePath();
  		String pictureUrl = PropertiesUtil.getKeyValue(WaybillConstants.PICTURE_WAYBILL_ADDRESS);
  		ByteArrayOutputStream os = new ByteArrayOutputStream();
  		InputStream in = null;
  		try {  
  			log.error("图片路径："+filePath);
  			log.error("图片服务器地址"+pictureUrl);
  			String address = "图片全地址  file=" + pictureUrl+"filePath="+filePath;
  		    log.error(address);
  		    URL url = new URL(pictureUrl+"filePath="+filePath);  
  		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
  		    urlConnection.setConnectTimeout(READ_TIME_OUT);
  		    urlConnection.setReadTimeout(READ_TIME_OUT);
  		    urlConnection.connect();
  		    in = urlConnection.getInputStream();
  		    BufferedImage image = ImageIO.read(in);
  		    ImageIO.write(image, "png", os);
  		    os.flush();
  		    return os.toByteArray();
  	    } catch (Exception e) {  
  	        e.printStackTrace(); 
  	        log.error("图片开单异常错误信息：");
  	        log.info(e.getMessage(), e);
  	        return null;
  	    } finally {
  	    	 if (null != in) {
  		    	 try {
  					in.close();
  					in = null;
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
  	    	 }
  	    	 
  	    	 if (null != os) {
  	    		 try {
  					os.close();
  					os = null;
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
  	    		 
  	    	 }
  	    }
  	}
    
    
    /**
     * 根据运单Id加载图片
     * @param waybillId
     * @throws Exception 
     */ 
    public void loadImageByWaybillId(String waybillId) throws Exception{
    	if(StringUtils.isBlank(waybillId))
    		throw new Exception("运单号不能为空");
		try {
			byte[] is = queryImageByWaybillId(waybillId);
			if(is != null){
				InputStream buffin = new ByteArrayInputStream(is); 
				BufferedImage img = ImageIO.read(buffin);
				loadImage(img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
  //更具运单号id获取图片
  	public byte[] queryImageByWaybillId(String waybillId) {
  		
  		WaybillPictureEntity waybillPictureEntity = waybillRemotingService.queryWaybillPictureById(waybillId);
  		String filePath = waybillPictureEntity.getFilePath();
  		String pictureUrl = PropertiesUtil.getKeyValue(WaybillConstants.PICTURE_WAYBILL_ADDRESS);
  		ByteArrayOutputStream os = new ByteArrayOutputStream();
  		try {  
  		    URL url = new URL(pictureUrl+"filePath="+filePath);  
  		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  
  		    urlConnection.connect();
  		    InputStream in = urlConnection.getInputStream();
  		    BufferedImage image = ImageIO.read(in);
  		    ImageIO.write(image, "png", os);  
  	    } catch (Exception e) {  
  	        e.printStackTrace(); 
  	        return null;
  	    }  
  		return os.toByteArray();
  	}
    
    // 程序入口
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        PictureViewComp p = new PictureViewComp(NumberConstants.NUMBER_800, NumberConstants.NUMBER_500);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_800);
        frame.setResizable(false);
        frame.setTitle("双击打开图片，然后拖拽");
        frame.add(p);
        frame.setVisible(true);
     // 定义5个简单类型的边框  
//        Border blackline, etched, raisedbevel, loweredbevel, empty;
     // 创建凹陷边框  
        BorderFactory.createLoweredBevelBorder();  
        p.loadImage("F:\\waybillpicture\\a.jpg");
    }
    
	public PictureRotateComp getPictureRotateComp() {
		return pictureRotateComp;
	}

	public void setPictureRotateComp(PictureRotateComp pictureRotateComp) {
		this.pictureRotateComp = pictureRotateComp;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}

	public void setBean(WaybillPanelVo bean) {
		this.bean = bean;
		pictureRotateComp.setBean(bean);
	}
}

/**
 * 图片滑动条
 * @author 157229-zxy
 *
 */
class ImageSlider extends JSlider{
	private static final String ORI_ICON_PATH = "icon_ori.png";
	
	//放大倍数
//	private int multiple;
	private ImageSlider intance;
	//原点图片
	private Icon oriPointIcon;
	//原点值
	private int oriPoint;
	//放大单位值
	private BigDecimal largerStep;
	//缩小单位值
	private BigDecimal narrowStep;
	public ImageSlider(int multiple){
//		this.multiple = multiple;
		intance = this;
		//进行对称分布，50,100,150。其中100为起点
		oriPoint = NumberConstants.NUMBER_100;
		largerStep = new BigDecimal((multiple - 1) * oriPoint).divide(new BigDecimal(NumberConstants.NUMBER_50),2,BigDecimal.ROUND_HALF_UP);
		narrowStep = new BigDecimal(oriPoint).divide(new BigDecimal(multiple * NumberConstants.NUMBER_50));
		setMaximum(NumberConstants.NUMBER_150);
		setMinimum(NumberConstants.NUMBER_50);
		// 设定监听器
 		ChangeListener listener = new ChangeListener() {
 			public void stateChanged(ChangeEvent e) {
 				if (e.getSource() instanceof JSlider) {
 					/**
						 * @项目：智能开单项目
						 * @功能：点击放大缩小栏时记录时间点
						 * @author:218371-foss-zhaoyanjun
						 * @date:2016-05-27上午11:22
						 */
						PictureViewComp pvc=(PictureViewComp)intance.getParent();
						if(pvc.getBean()!=null&&pvc.getBean().getIbtg()!=null){
							pvc.getBean().getIbtg().setMagnificationPicture(new Date());
							if(pvc.getBean().getIbtg().getRotatePicture()!=null){
								double rotatePictureTime=pvc.getBean().getIbtg().getMagnificationPicture().getTime()-pvc.getBean().getIbtg().getRotatePicture().getTime();
								pvc.getBean().getIbtg().setRotatePictureTime((new BigDecimal(rotatePictureTime/NumberConstants.NUMBER_1000)).setScale(NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP).doubleValue());
							}
						}
 					if(((PictureViewComp)intance.getParent()).imagePanel.getImage() != null){
 						
 						int value = ((JSlider) e.getSource()).getValue();
 	 					if(intance.getParent() != null && ((PictureViewComp)intance.getParent()).imagePanel != null){
 	 						if(value > oriPoint){
 	 	 						BigDecimal percent = new BigDecimal((value - NumberConstants.NUMBER_100) * largerStep.doubleValue()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 	 	 						percent = percent.add(new BigDecimal("1"));
 	 	 						((PictureViewComp)intance.getParent()).imagePanel.zoomImage(percent.doubleValue());
 	 	 					}else if(value < oriPoint){
 	 	 						BigDecimal percent = new BigDecimal(value * narrowStep.doubleValue()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 	 	 						((PictureViewComp)intance.getParent()).imagePanel.zoomImage(percent.doubleValue());
 	 	 	 				}else{
 	 	 	 					((PictureViewComp)intance.getParent()).imagePanel.zoomImage(1);
 	 	 	 				}
 	 	 				}
 					}
 				}
 			}
 		};
        
        Hashtable labelTable = new Hashtable();  
//        Icon icon = new ImageIcon(ORI_ICON_PATH);
        Icon icon = ImageUtil.getImageIcon(this.getClass(), ORI_ICON_PATH);
        JLabel jl = new JLabel();
        jl.setIcon((Icon) icon);
		labelTable.put( new Integer( oriPoint ), jl );  
		setLabelTable(labelTable);
        // 主刻度
 		setMajorTickSpacing(NumberConstants.NUMBER_10);
 		// 次刻度
 		setMinorTickSpacing(NumberConstants.NUMBER_5);
 		// 设定为显示
 		setPaintTicks(true);
 		setPaintLabels(true);
 		// 监听slider1
 		addChangeListener(listener);
 		setOrientation( SwingConstants.VERTICAL);
 		setOpaque(false);
 		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if(getOrientation() == SwingConstants.HORIZONTAL){
					BigDecimal width = new BigDecimal(getWidth());
					BigDecimal mouseX = new BigDecimal(p.x);
//					int value = new BigDecimal(getMaximum()*(mouseX.divide(width).floatValue())).intValue();
					int value = (BigDecimal.valueOf(getMaximum()).multiply(mouseX.divide(width))).intValue();
					setValue(value);
				}else if(getOrientation() == SwingConstants.VERTICAL){
					BigDecimal height = new BigDecimal(getHeight());
					BigDecimal mouseY = new BigDecimal(p.y);
					//滑动条是上大 下小，需要用1减
					int value = new BigDecimal(NumberConstants.NUMBER_100 * (1 - mouseY.divide(height).floatValue())).intValue();
					setValue(value + getMinimum());
//					System.out.println(getMaximum()*(mouseY.divide(height).floatValue()) + "========" + value + "  " );
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
	}
	
	public Icon getOriPointIcon() {
		return oriPointIcon;
	}
	
	/**
	 * 设置原点图片
	 * @param oriPointIcon
	 */
	public void setOriPointIcon(Icon oriPointIcon) {
		this.oriPointIcon = oriPointIcon;
		Dictionary labelTable = getLabelTable();
		JLabel jl = new JLabel();
	    jl.setIcon(oriPointIcon);
		labelTable.put( new Integer( oriPoint ), jl );  
		setLabelTable(labelTable);
	}
	
	/**
	 * 通过百分比确定滚动条位置
	 * @param percent
	 */
	public void setPercent(double percent){
		BigDecimal step = new BigDecimal("1");
		BigDecimal bigValue = new BigDecimal("100");
		if(percent > 1){
			step = largerStep;
			bigValue = new BigDecimal((percent - 1) * NumberConstants.NUMBER_100).divide(step,2,BigDecimal.ROUND_HALF_UP);
			bigValue = bigValue.add(new BigDecimal(NumberConstants.NUMBER_100));
		}
		else if(percent < 1){
			step = narrowStep;
			bigValue = new BigDecimal(percent * NumberConstants.NUMBER_100).divide(step,2,BigDecimal.ROUND_HALF_UP);
		}
		setValue(bigValue.intValue());
//		System.out.println(bigValue.intValue());
	}
	
}
 
/**
 * 图片面板
 * @author 157229-zxy
 *
 */
class ImagePanel extends JPanel {
	//拖拽状态
    private DragStatus status = DragStatus.Ready;   // 拖拽状态
    //图片
    private Image image;                            // 要显示的图片
    
    //图片
//    private Image oriImage;                            // 原始图片
    
    private double theta;							//当前度数 
    
    private int ratoteType = 0;						//是否旋转
    
//    private boolean isRotate = false;						//是否旋转
 
    private Point imagePosition = new Point(0, 0);  // 图片的当前位置
 
    private Point imageStartposition = new Point(0, 0);   // 每次拖拽开始时图片的位置（也就是上次拖拽后的位置）
 
    private Point mouseStartposition;                     // 每次拖拽开始时鼠标的位置
    //当前图片尺寸
    Dimension curImgDim;	
    //原始图片尺寸
    Dimension oriImgDim;
    private ImagePanel ipSelf;
    private ImageSlider slider;
    
    /**
     * 
     * @param slider
     */
    ImagePanel(ImageSlider slider) {
    	ipSelf = this;
    	this.slider = slider;
        addMouseListener(new MouseListener() {
            // 双击鼠标时打开图片
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
//                    openImage();
                }
            }
            // 按下鼠标时，更改状态，并且记录拖拽起始位置。
            public void mousePressed(MouseEvent e) {
                if (status == DragStatus.Ready) {
                    status = DragStatus.Dragging;
                    mouseStartposition = e.getPoint();
                    imageStartposition.setLocation(imagePosition.getLocation());
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); 
            }
 
            // 松开鼠标时更改状态
            public void mouseReleased(MouseEvent e) {
                if (status == DragStatus.Dragging) {
                    status = DragStatus.Ready;
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
            }
 
            public void mouseEntered(MouseEvent e) {
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
            	
            }
            
        });
        
        //鼠标拖拽
        addMouseMotionListener(new MouseMotionListener() {
 
            // Java 有拖拽事件，在这个事件中移动图片位置
            public void mouseDragged(MouseEvent e) {
                if (status == DragStatus.Dragging) {
                    moveImage(e.getPoint());
                }
            }
 
            public void mouseMoved(MouseEvent e) {
            }
            
        });
        
        //鼠标滚动
        this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				if(null != curImgDim){
					if(e.getWheelRotation()== -1){
						curImgDim.setSize(curImgDim.getWidth() + NumberConstants.NUMBER_10, curImgDim.getHeight() + NumberConstants.NUMBER_10);
						if(ipSelf.slider != null){
							BigDecimal percent = getPercent();
							ipSelf.slider.setPercent(percent.doubleValue());
						}
						repaint();
					}
					
					if(e.getWheelRotation()== 1){
						curImgDim.setSize(curImgDim.getWidth() - NumberConstants.NUMBER_10, curImgDim.getHeight() - NumberConstants.NUMBER_10);
						if(ipSelf.slider != null){
							BigDecimal percent = getPercent();
							ipSelf.slider.setPercent(percent.doubleValue());
						}
						repaint();
					}
				}
            	ipSelf.getParent().repaint();
			}
		});
    }
    
    public void resetPositon(){
    	imagePosition.setLocation(0, 0);
    }
    
    /**
     * 按比例缩放
     * @param d
     */
    public void zoomImage(double d){
    	curImgDim.setSize(oriImgDim.getWidth() * d, oriImgDim.getHeight() * d);
    	repaint();
    	ipSelf.getParent().repaint();
    }
 
   /* @Override
    public void paintComponents(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paintComponent(g);
    	Dimension size=this.getParent().getSize();
    	  g.drawImage(image,0,0,size.width,size.height,null);
    }
    */
    /**
     * 移动图片。实际上画图工作在 paintComponent() 中进行，这里只是计算图片位置，然后调用该方法。
     *
     * @param point 当前的鼠标位置
     */
    public void moveImage(Point point) {
        // 图片的当前位置等于图片的起始位置加上鼠标位置的偏移量。
        imagePosition.setLocation(
                imageStartposition.getX() + (point.getX() - mouseStartposition.getX()),
                imageStartposition.getY() + (point.getY() - mouseStartposition.getY())
        );
        repaint();
        this.getParent().repaint();
    }
  
    /**
     * 通过选择器打开图片
     */
    private void openImage() {
        System.out.println("Opening image...");
        File file = createFileChooser().getSelectedFile();
        if (file != null) {
            image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
            if (image != null) {
            	curImgDim = getCurImgDimension(image);
            	oriImgDim = new Dimension(image.getWidth(this),image.getHeight(this));
                this.repaint();
            }
        }
    }
    
    /**
     * 打开图片
     * @param img
     */
    public void openImage(Image img){
    	image = img;
//    	oriImage = img;
    	curImgDim = getCurImgDimension(image);
		oriImgDim = new Dimension(image.getWidth(this),image.getHeight(this));
		BigDecimal percent = getPercent();
		ipSelf.slider.setPercent(percent.doubleValue());
		
		ipSelf.slider.setPercent(getPercent2().doubleValue());
	     
	     Dimension g=new Dimension(this.getWidth(),this.getHeight());
	     curImgDim.setSize(g.getWidth() , g.getHeight());
        repaint();
        ipSelf.getParent().repaint();
		
    	this.repaint();
    }
    
    /**
     * 打开图片
     * @param img
     */
    public void openImage(String filePath){
    	BufferedImage bufImg = null;
    	try {
    		bufImg = ImageIO.read(new File(filePath));
    		image = bufImg;
//    		oriImage = image;
    		curImgDim = getCurImgDimension(image);
    		oriImgDim = new Dimension(image.getWidth(this),image.getHeight(this));
    		//BigDecimal percent = getPercent();
    		//ipSelf.slider.setPercent(percent.doubleValue());
    		
    		ipSelf.slider.setPercent(getPercent2().doubleValue());
    	     
    	     Dimension g=new Dimension(this.getWidth(),this.getHeight());
    	     curImgDim.setSize(g.getWidth() , g.getHeight());
	         repaint();
	         ipSelf.getParent().repaint();
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.repaint();
    }
    
    public BigDecimal getPercent2(){
        BigDecimal percent = new BigDecimal(this.getHeight()).divide(new BigDecimal(this.getWidth()),2,BigDecimal.ROUND_HALF_UP);
        return percent;
       }
    
    
    public BigDecimal getPercent(){
    	BigDecimal percent = new BigDecimal(curImgDim.height).divide(new BigDecimal(oriImgDim.height),2,BigDecimal.ROUND_HALF_UP);
    	return percent;
    }
    
    /**
     * 取得当前图片尺寸
     * @param image
     * @return
     */
    private Dimension getCurImgDimension(Image image){
    	int imgWidth = image.getWidth(this);
    	int imgHeight = image.getHeight(this);
    	int width = this.getWidth();
    	int height = this.getHeight();
    	Dimension imgDim;
    	if(imgWidth < width && imgHeight < height){
    		imgDim = new Dimension(width,height);
    	}else{
    		imgDim = new Dimension(imgWidth,imgHeight);
    	}
    	return imgDim;
    } 
    
    // 创建打开文件对话框
    private JFileChooser createFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("请选择图片文件...");
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("常用图片格式", "jpg", "jpeg", "gif", "png"));
        chooser.showOpenDialog(this);
        return chooser;
    }
    
    /**
     * 图片旋转
     * ratoteImage: <br/>
     * 
     * Date:2014-10-27上午11:10:02
     * @author 157229-zxy
     * @param angle
     * @since JDK 1.6
     */
    public void ratoteImage(double angle, int angleType) {
        if (image == null)
            return; //如果bufImage为空则直接返回
        //重置起始位置
        resetPositon();
    	theta = angle;
    	ratoteType = angleType;
//    	isRotate = true;						//是否旋转
    	repaint(); //重绘组件
        this.getParent().repaint();
    }
    
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2d, h / 2d);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
    
    public void ratoteImage1(BufferedImage image, int degree){
    	int iw = image.getWidth();//原始图象的宽度   
        int ih = image.getHeight();//原始图象的高度  
        int w = 0;  
        int h = 0;  
        int x = 0;  
        int y = 0;  
        degree = degree % NumberConstants.NUMBER_360;  
        if (degree < 0)  
            degree = NumberConstants.NUMBER_360 + degree;//将角度转换到0-360度之间  
        double ang = Math.toRadians(degree);//将角度转为弧度  
  
        /** 
         *确定旋转后的图象的高度和宽度 
         */  
  
        if (degree == NumberConstants.NUMBER_180 || degree == 0 || degree == NumberConstants.NUMBER_360) {  
            w = iw;  
            h = ih;  
        } else if (degree == NumberConstants.NUMBER_90 || degree == NumberConstants.NUMBER_270) {  
            w = ih;  
            h = iw;  
        } else {  
            int d = iw + ih;  
            w = (int) (d * Math.abs(Math.cos(ang)));  
            h = (int) (d * Math.abs(Math.sin(ang)));  
        }  
  
        x = (w / 2) - (iw / 2);//确定原点坐标  
        y = (h / 2) - (ih / 2);  
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());  
        Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();  
         rotatedImage  = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);  
          
        AffineTransform at = new AffineTransform();  
        at.rotate(ang, w / 2d, h / 2d);//旋转图象  
        at.translate(x, y);  
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);  
        op.filter(image, rotatedImage);  
        image = rotatedImage; 
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
          	Graphics2D g2d = (Graphics2D)g;
          	g2d.rotate(theta, NumberConstants.NUMBER_400, NumberConstants.NUMBER_250);
            
            if(ratoteType == 0){
            	g.drawImage(image, (int) imagePosition.getX(), (int) imagePosition.getY(),curImgDim.width,curImgDim.height, this);
        	}else if(ratoteType == 1){
        		g.drawImage(image, (int) imagePosition.getY(), - (int) imagePosition.getX(),curImgDim.width,curImgDim.height, this);
        	}else if(ratoteType == 2 || ratoteType == NumberConstants.NUMBER_3){ //-180
        		g.drawImage(image, -(int) imagePosition.getX(), -(int) imagePosition.getY(),curImgDim.width,curImgDim.height, this);
        	}else if(ratoteType == NumberConstants.NUMBER_4){
        		g.drawImage(image, -(int) imagePosition.getY(), (int) imagePosition.getX(),curImgDim.width,curImgDim.height, this);
        	}
            
            
        }
    }
 
    private enum DragStatus {
 
        Ready, Dragging
    }

	public Image getImage() {
		return image;
	}
    
}


/**
 * 旋转按钮组件
 * ClassName: PictureRotateComp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-27 下午7:42:12 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
/*class PictureRotateComp extends JPanel implements ActionListener{
	*//** *//*
	private static final long serialVersionUID = 1L;
	
	private static final String LEFT_ICON_PATH = "rleft.png";
	
	private static final String RIGHT_ICON_PATH = "rright.png";
	
	private static final String TOP_ICON_PATH = "rtop.png";
	
	*//**
	 * 当前角度
	 *//*
	private int curAngle = 0;
	
	private ImagePanel imagePanel;
	
	*//**
	 * 下旋
	 *//*
	private static final String COMMAND_DOWN = "down"; 
	
	*//**
	 * 上旋
	 *//*
	private static final String COMMAND_TOP = "top"; 
	
	*//**
	 * 左旋
	 *//*
	private static final String COMMAND_LEFT = "left"; 
	
	*//**
	 * 右旋
	 *//*
	private static final String COMMAND_RIGHT = "right"; 

	public PictureRotateComp(ImagePanel imagePanel) {
		// 设定布局器
//		super();
		this.imagePanel = imagePanel;
		// 设定窗体大小
		setPreferredSize(new Dimension(37, 37));
		
		this.setLayout(null);
		Icon iconLeft = ImageUtil.getImageIcon(this.getClass(), LEFT_ICON_PATH);
		Icon iconTop = ImageUtil.getImageIcon(this.getClass(), TOP_ICON_PATH);
		Icon iconRight = ImageUtil.getImageIcon(this.getClass(), RIGHT_ICON_PATH);
		
		JButton jbLeft = new JButton();
		jbLeft.setLocation(0, 0);
		jbLeft.setSize(23, 25);
		JButton jbTop = new JButton();
		jbTop.setLocation(23, 0);
		jbTop.setSize(23, 25);
		JButton jbRight = new JButton();
		jbRight.setLocation(46, 0);
		jbRight.setSize(23, 25);
		JButton jbDown = new JButton();
		jbDown.setLocation(0, 24);
		jbDown.setSize(36, 12);
		jbLeft.setIcon(iconLeft);
		jbLeft.addActionListener(this);
		jbLeft.setActionCommand(COMMAND_LEFT);
		jbTop.setIcon(iconTop);
		jbTop.addActionListener(this);
		jbTop.setActionCommand(COMMAND_TOP);
		jbRight.setIcon(iconRight);
		jbRight.addActionListener(this);
		jbRight.setActionCommand(COMMAND_RIGHT);
		
		//左旋按钮
		this.add(jbLeft);
		//上旋按钮
		this.add(jbTop);
		//右旋按钮
		this.add(jbRight);
		//下旋按钮
		this.add(jbDown);
		setOpaque(false);
		
	}

	*//**
	 * 事件处理
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 *//*
	@Override
	public void actionPerformed(ActionEvent e) {
		int angleType = 0;
		//重置为0
		if(curAngle%360 == 0)
			curAngle = 0;
		if(COMMAND_LEFT.equals(e.getActionCommand())){
			curAngle = curAngle - 90;
		}else if(COMMAND_TOP.equals(e.getActionCommand())){
			curAngle = curAngle + 180;
		}else if(COMMAND_RIGHT.equals(e.getActionCommand())){
			curAngle = curAngle + 90;
		}else if(COMMAND_DOWN.equals(e.getActionCommand())){
			curAngle = curAngle - 180;
		}
		if(curAngle == 90 || curAngle == -270){
			angleType = 1;
		}else if(curAngle == -90){
			angleType = 4;
		}else if(curAngle == 0){
			angleType = 0;
		}else if(curAngle == 180){
			angleType = 2;
		}else if(curAngle == -180){
			angleType = 3;
		}else if(curAngle == 270){
			angleType = 4;
		}
		double angle =(new BigDecimal(curAngle).divide(new BigDecimal(180)).floatValue())*Math.PI;
		imagePanel.ratoteImage(angle,angleType);
	}
}
*/