package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.spi.IIORegistry;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.utils.CustomerImpOperLogHandler;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.IntelligenceBillTimeGather;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.EnterKeyForScrollPanAction;
import com.deppon.foss.module.pickup.creating.client.action.FullScreenAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.PictureMouseWheelListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.editui.ButtonPanel;
import com.deppon.foss.module.pickup.creating.client.ui.editui.PicturePanel;
import com.deppon.foss.module.pickup.creating.client.ui.order.WebOrderDialog;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.BackPictureWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.luciad.imageio.webp.WebPImageReaderSpi;
import com.luciad.imageio.webp.WebPImageWriterSpi;
/**
 * 图片开单界面 
 * 
 * 
 * 图片开单：
 *  1.	在FOSS功能菜单-开单管理，新增“图片开单”，点击该菜单，系统跳转图片开单主界面。
	2.	图片开单界面包括三部分，最上边为开单界面功能键，上部为图片区域，下部为开单区域。
	3.	开单功能键：网上订单、运单打印、打印预览、标签打印、客户查询、临欠散客、查询网点、电子地图、公布价查询、简单报价（删掉新建、暂存、提交、补录运单）。
	4.	FOSS系统接收司机运单图片，系统对接运单号，司机工号且不可修改， FOSS校验运单号，当运单号异常（运单号已存在），系统自动退回次运单图片且退回原因备注为“运单号已被占用，请更换运单运单”。运单号无异常，则运单图片为待录入状态。
	5.	待录入运单生成任务池，待录入运单以司机最后一次上传图片时间为排序规则。现付、大票货运单置顶排序同时遵守时间排序规则，按序顺显示在图片开单界面，运单图片为待录入状态时，司机可以撤回重传。
	6.	待录入的运单，一张运单图片只能在一个FOSS系统显示且开单图片为不可选择，当图片运单在FOSS系统图片开单界面中，运单图片的状态为已分配，此时司机不能撤回。
	7.	在图片开单界面鼠标/光标超过15分钟未动，开单界面提醒“此单您已超时，请开下一单”，图片开单界面更新为提示“点击获取下一单”界面（“提交”后的界面）。开单超时运单图片回到任务池排序待录入。

 * 
 * 图片区域：
 *  1.	图片区域为包括，图片框、退回司机按钮、待录入统计框
	2.	图片框右侧有放大缩小选择条，鼠标点击图片运单，滑动鼠标滚轮图片在图片框中可放大、缩小，鼠标可拖拽图片位置。图片放大的最大是原图片尺寸的一倍。缩小的最小底限是默认打开的尺寸为最小
	3.	退回司机拍照：单击按钮，系统弹出退回对话框，退回原因为必填项，选择“确认”，系统自动分配下一张运单图片，系统向司机APP推送退回信息。
	4.	待录入数量统计，是统计部门待录入数量的统计，当“提交”、“提交并继续下一单”，待补录数量统计自动更新，鼠标点击待录入统计框，可手动刷新统计条数
	5.	当点击“提交”FOSS运单开单界面更新，图片区域显示“点击获取下一单”，点击自动获取下一张待录入图片
	6.	当点击“提交并继续下一单”FOSS刷新一个图片继续开单

 * 
 * 
 * 
 * 
 * 开单区域	
 * 1.	开单区域为开单录入区域，光标所在行显示开单区域的中间位置。
	2.	在“对内备注”后新增“包装备注”文本框；在“大票货”复选框下新增“装卸费”复选框；在“提交”按钮并排新增“提交并继续下一单”按钮。
	3.	查看图片运单信息是否完整.
	（1）当运单信息不完整、图片不清晰，则点击“退回司机拍照”按钮，跳转退回界面，退回原因备注为必填，选择“确认”，系统自动分配下一张运单图片
	（2）当运单信息完整、清晰，则继续信息录入
	4.	当司机传输运单图片标记为“现金”，运单信息必须填写完整。
	5.	当运单有打木架/木箱的货物时，打木架/木箱必须把打木包装的件数、包装要求在打木架编辑框填写完整。需要打木托的要在“包装费“中手动添加包装费。
	6.	包装备注：打木架编辑框的包装要求信息同步显示在包装备注内；其他包装的必须在“包装备注”文本框内填写具体、详细、完整的包装信息。所有需要加包装的要求信息必须在此文本框为填写完整，对接外场PDA.
	7.	当运单有客户预付费时，付款方式为选为现金，预付费在预付金额输入。
	8.	当运单有装卸费时，必须勾选装卸费。
	9.	计算总运费规则：当录入运单信息货物重量、体积都有时
	（1）	当打木架编辑框为空，此时默认货物没有打木架货物，或打木架编辑框包装要求、货物体积都填写完整时，则是运单信息录入完整，“计算总运费”可操作，点击“计算总运费”，校验所有计价规（如提货网点单件/单票重量体积校验、大票运输性质重量体积校验、打木架录入弹框重量体积校验、重量体积比校验、线路运费价格校验等）。
	（2）	当打木架编辑框有包装要求，打木架体积没有填写完整，则“计算总运费”为灰色（不可操作）
	（3）	当录入运单信息货物重量、体积都都没有，或有一个，则则“计算总运费”为灰色（不可操作）
	10.	包装费：
	（1）能作“计算总运费操作的运单，则运单包装费为手动添加的包装费与打木包装之和。
	（2）不可“计算总运费“操作，包装费不校验打包装计价规则，只为手动添加的包装费。
	11.	提交，提交本次运单信息。提交并继续下一单，提交本次运单信息，并继续下一运单信息录入。
	12.	提交操作：
	（1）	运单信息录入完整，提交运单，校验“提交”规则（提货网点单件/单票重量体积校验、大票运输性质重量体积校验、打木架录入弹框重量体积校验、重量体积比校验、线路运费价格校验等）。
	①校验无异常时，直接生成运单，运单状态更改为已开单，系统不打印运单，锁定FOSS把打印标签信息推送司机APP，生成代打标签待办事项提醒。
	②当运单“提交”规则校验有异常时，不能提交运单，开单界面提醒异常处并标记异常处规则，进行修改后再提交运单。
	（2）	运单信息录入不完整时，提交则不校验重量体积相关的所有规则，运单状态更改为已录入。且系统存储运单号不允许再使用。系统不打印运单，锁定FOSS把打印标签信息推送司机APP，生成代打标签待办事项提醒
	13.	此时运单状态对接“综合查询”，生成运单的为“提交运单”，存储运单的操作状态为“图片开单”。

 * 
 *
 */
public class PictureWaybillEditUI extends JPanel implements IApplicationAware {

	private static final long serialVersionUID = -2217139757335016528L;
	
	private static final int NUM_310 = 310;

	private static final int NUM_1265 = 1265;

	private static final int NUM_535 = 535;
    private static final Log LOG = LogFactory.getLog(PictureWaybillEditUI.class);

static{
		try {
			
			IIORegistry r = javax.imageio.spi.IIORegistry.getDefaultInstance();
			WebPImageReaderSpi s = new WebPImageReaderSpi();
			WebPImageWriterSpi s2 = new WebPImageWriterSpi();
			r.registerServiceProvider(s);
			r.registerServiceProvider(s2);
			//inilibWebp();
		}
		catch (NoClassDefFoundError e) {
			if(LOG.isErrorEnabled()){
				LOG.error(e);
			}
		}
		
	}
	
	public static void inilibWebp() {
		String currentVersion = System.getProperty("sun.arch.data.model");

		String url = com.luciad.imageio.webp.Temp.class.getResource("/")
				.getPath();
		url = url.substring(1, url.length());
		String fossHome = System.getProperty("foss_home");

		if (fossHome == null || "".equals(fossHome)) {
			if (currentVersion != null) {
				if ("64".equals(currentVersion)) {
					System.load(url + "windows-x64/webp-imageio.dll");
				} else {

					System.load(url + "windows-x86//webp-imageio.dll");
				}
			}

		} else {
			if (currentVersion != null) {
				if ("64".equals(currentVersion)) {
					System.load(fossHome
							+ "\\lib\\win32\\windows-x64\\webp-imageio.dll");
				} else {

					System.load(fossHome
							+ "\\lib\\win32\\windows-x86\\webp-imageio.dll");
				}
			}
		}

	}
	
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PictureWaybillEditUI.class);

	private IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();
	// 数据字典
    private IDataDictionaryValueService dataDictionaryValueService = GuiceContextFactroy.getInjector()
    		.getInstance(DataDictionaryValueService.class);
	/**
	 * 按钮面板
	 */
	public ButtonPanel buttonPanel;

	// 图片信息面板
	public PicturePanel picturePanel;

	// 开单面板
	public WaybillEditUI waybillEdit;
	//滚动
	public JScrollPane sp;
	
	private IEditor editor;
	// 运单开单类型（集中开单、营业部开单）
	private String waybillType;
	private IApplication application;// 窗口应用程序
	// 是否是图片开单
	private String pictureWaybillType;
	// 是否修改,查看
	private String pictureOperateType;
	//提交类型
	private String submitType;
	//离线改在线的基础方法
	IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	private String waybillNo;
	// 客户服务接口
	//private ICustomerService customerService = GuiceContextFactroy.getInjector()
			//.getInstance(CustomerService.class);
	//定义装卸费的费率
	private static final BigDecimal[] fontSize = {new BigDecimal(0),
												  new BigDecimal(1),
												  new BigDecimal(2),
											      new BigDecimal(3),
												  new BigDecimal(4),
												  new BigDecimal(5),
												  new BigDecimal(6),
												  new BigDecimal(7),
												  new BigDecimal(8),
												  new BigDecimal(9),
												  new BigDecimal(10),
												  new BigDecimal(11),
												  new BigDecimal(12),
												  new BigDecimal(13),
												  new BigDecimal(14),
												  new BigDecimal(15)};


	public PictureWaybillEditUI() {
	}
	/**
	 * 
	 * 打开开单界面
	 * 
	 */
	public void openUI() {
		if (SwingUtilities.isEventDispatchThread()) {
			initCommonWaybillEditUI();
		} else {
			// 由于是异步打开窗口 所以需要放在swing专用图形线程中，否则界面会出错
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					initCommonWaybillEditUI();
				}
			});
		}
	}
	/**
	 * 
	 * 初始化开单界面
	 * 
	 */
	private void initCommonWaybillEditUI() {
		init();
		// 初始化图片
		initPicture();
		
	}

	/**
	 * 
	 * 初始化界面中的图片 ，    如果有订单号    ，就先倒入订单中093515的数据填充到界面上
	 * 
	 */
	public void initPicture() {
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEdit
				.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		/**
		 * @项目：智能开单项目
		 * @功能：数据字典控制是否统计时间，其余，均由是否ibtg为null判断
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19上午10:19
		 */
		DataDictionaryValueEntity e = dataDictionaryValueService
				.queryDataDictoryValueByCode("INTELLIGENCE_BILLING","INTELLIGENCE_TIME_RECORD_STATE");
		if(e!=null&&e.getValueCode()!=null&&"是".equals(e.getValueName())){
			if(picturePanel!=null){
				IntelligenceBillTimeGather ibtg=new IntelligenceBillTimeGather();
				bean.setIbtg(ibtg);
				picturePanel.setBean(bean);
				waybillEdit.setPictureWaybillEditUI(this);
				waybillEdit.addListener();
				Date date=new Date();
				bean.getIbtg().setPictureBillStartTime(date);
			}
		}
		/**
		 * 
		 * 判断是 新增开单界面    还是修改或者查看  开单姐界面 
		 * 
		 */
		if (StringUtils.isNotBlank(this.pictureOperateType)
				&& "VIEWORMODIFY".equals(this.pictureOperateType)) {
			/**
			 * 
			 * 获取还有多少未补录的数据
			 * 
			 */
			/*int picturePending = waybillService.getPictureWaybillCount();
			this.picturePanel.button.setText(i18n.get("foss.gui.creating.picturePanel.label", picturePending));*/
			int picturePendingLocal = waybillService.getPictureWaybillLocalCount();
			//查询全国带补录运单
			int picturePendingAll = waybillService.getPictureWaybillAllCount();
			this.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					picturePendingLocal)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							picturePendingAll)+"</html>");
			
			waybillEdit.getBasicPanel().getTxtWaybillNO().setEnabled(false);
			//waybillEdit.getBasicPanel().getCboReceiveModel().setEnabled(false);
			waybillEdit.getBasicPanel().getTxtDriverNumber().setEnabled(false);
			/**
			 * 修改或者查看 过来的开单页面中的退回功能不能使用
			 */
			this.picturePanel.backButton.setEnabled(false);
		} else {
			Boolean falg = true;
			/**
			 * 
			 * 1，查询需要待补录的图片数据，如果查询出来的待补录数据中的运单号已经存在，自动退回该条数据，继续去查询数据
			 * 2. 当查询出来的数据已经的运单号不存在就打开图片开单界面，停止查询数据，并且把待补录的数据类型更改为已分配
			 * 
			 */
			while (falg) {
				/**
				 * 获取还有多少条数据未补录
				 */
				LOG.info("图片开单查询是否有待补录运单开始...");
//				int picturePending = waybillService.getPictureWaybillCount();
				//修改待录入显示本地和全国待录入条数     by:352676
				//查询本地待补录运单
				int picturePendingLocal = waybillService.getPictureWaybillLocalCount();
				//查询全国带补录运单
				int picturePendingAll = waybillService.getPictureWaybillAllCount();
				LOG.info("图片开单查询是否有本地待补录运单结束...单数:"+picturePendingLocal);
				LOG.info("图片开单查询是否有全国待补录运单结束...单数:"+picturePendingAll);
				/**
				 * 获取一条待补录的数据
				 */
				WaybillPictureEntity pictureWaybill = null;
				LOG.info("图片开单获取数据开始...");
				if(StringUtils.isEmpty(waybillNo)){
					 @SuppressWarnings("unchecked")
					 List<String> newWaybills = (List<String>)SessionContext.get(WaybillConstants.NEW_WAYBILL_NOS);
					 try {
						 pictureWaybill = waybillService.getPictureWaybill(newWaybills);
					} catch (Exception e2) {
						MsgBox.showInfo(e2.getMessage());
					}
					
				}else{
					WaybillPictureEntity pictureWaybillQuery = new WaybillPictureEntity();
					pictureWaybillQuery.setWaybillNo(waybillNo);
					pictureWaybillQuery.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
					pictureWaybillQuery.setActive(FossConstants.YES);
					pictureWaybill = waybillService.queryWaybillPictureByEntity(pictureWaybillQuery);
				}
				LOG.info("图片开单获取数据结束...");
				//TODO 
				/**
				 * 判断是否获取到待补录的数据
				 */
				if (pictureWaybill != null) {
					/**
					 * 判断该条待补录的数据的运单号是否存在
					 */
					WaybillDescriptor waybillDescriptor = new WaybillDescriptor();
					String waybillMessage = waybillDescriptor.validateWaybillNo(pictureWaybill.getWaybillNo(), bean);
					if (WaybillConstants.SUCCESS.equals(waybillMessage)) {
						/**
						 * 如果导入订单成功，打开图片开单界面 ，并显示图片，并填中数据
						 */
						try {
							LOG.info("图片开单加载图片开始...");
							this.picturePanel.pictureViewComp.loadImageByWaybillNo(pictureWaybill.getWaybillNo());
							LOG.info("图片开单加载图片结束...");
						} catch (Exception e1) {
							e1.printStackTrace();
							this.picturePanel.pictureViewComp.setVisible(false);
							this.picturePanel.lable1.setVisible(false);
							this.picturePanel.nextButton.setVisible(false);
							this.picturePanel.lable2.setVisible(true);
						}
						/**
						 * 判断改条数据的订单号是否为空
						 */
						if (StringUtils.isNotBlank(pictureWaybill.getOrderNo())) {
							/**
							 * 如果不为空则从crm到入该条运单数据
							 */
							
							SaleDepartmentEntity sale = waybillService.querySaleDeptByCode(pictureWaybill.getReceiveOrgCode());
							//设置收获部门信息
							Common.setSalesDepartmentForCentrial(sale, bean, waybillEdit);
							
							//设置运单号
							bean.setWaybillNo(pictureWaybill.getWaybillNo());
							String str = importOrder(pictureWaybill.getOrderNo(),bean);
							String serviceType = "";
							String orderNo = bean.getOrderNo();
							if(orderNo != null && !"".equals(orderNo)){
								serviceType	= Common.getServiceType(orderNo);
								if("".equals(serviceType) || serviceType == null){
									IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
									serviceType = waybillService.queryServiceType(bean.getOrderNo());
								}	
							}
							if(!"".equals(serviceType)&& serviceType != null){
								bean.setServerType(serviceType);
							}
							/**
							 * 判断改条订单是否导入成功
							 */
							if("SUCCESS".equals(str)){
								bean.setPictureWaybillNo(pictureWaybill.getId());
								bean.setWaybillNo(pictureWaybill.getWaybillNo());
								bean.setDriverCode(pictureWaybill.getDriverCode());
								bean.setCashPay(pictureWaybill.getCashPayFlag());
								bean.setGoodsVolumeTotal(null);
								bean.setGoodsWeightTotal(null);
								if(bean.getGoodsVolumeTotal() == null || BigDecimal.ZERO.compareTo(bean.getGoodsVolumeTotal()) == 0){
									bean.setGoodsVolumeTotal(BigDecimal.ZERO);
								}
								if(bean.getGoodsWeightTotal() == null || BigDecimal.ZERO.compareTo(bean.getGoodsWeightTotal()) == 0){
									bean.setGoodsWeightTotal(BigDecimal.ZERO);
								}
								//if(null != bean.getCodAmount()){
									//this.waybillEdit.incrementPanel.getTxtCashOnDelivery().setText(bean.getCodAmount().toString());
								//}
								//判断是否是现金如果是现金。付款方式只能是现金，并且不能修改
								if(WaybillConstants.YES.equals(pictureWaybill.getCashPayFlag())){
									setPaidMethod(waybillEdit,bean);						
								}
								bean.setBaiDuId(pictureWaybill.getBaiDuId());
								bean.setServiceFee(pictureWaybill.getServiceFee());
								bean.setPictureServiceFee(pictureWaybill.getServiceFee());
								if(pictureWaybill.getServiceRate() == null){
									bean.setServiceRate(BigDecimal.ZERO);
									waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(BigDecimal.ZERO);
								}else{
									bean.setServiceRate(pictureWaybill.getServiceRate());
									waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(pictureWaybill.getServiceRate());
								}
								if (WaybillConstants.YES.equals(pictureWaybill
										.getCashPayFlag())) {
									bean.setCashPayFlag(true);
								} else {
									bean.setCashPayFlag(false);
								}
								
								//判断是否是展会货 
								if (WaybillConstants.YES.equals(pictureWaybill
										.getIsExhibitCargo())) {
									bean.setIsExhibitCargo(true);
								} else {
									bean.setIsExhibitCargo(false);
								}
								//waybillEdit.getBasicPanel().getCboReceiveModel().setEnabled(false);
								waybillEdit.getBasicPanel().getTxtWaybillNO().setEnabled(false);
								waybillEdit.getBasicPanel().getTxtDriverNumber().setEnabled(false);
								waybillEdit.numberPanel.getLblNumber().setText(pictureWaybill.getWaybillNo());
								//修改待录入显示本地和全国待录入条数     by:352676
								/*this.picturePanel.button.setText(i18n.get(
										"foss.gui.creating.picturePanel.label",
										picturePending));*/
								this.picturePanel.button.setText("<html>"+i18n.get(
										"foss.gui.creating.picturePanel.labelLocal",
										picturePendingLocal)+"<br/>"+
										i18n.get(
												"foss.gui.creating.picturePanel.labelAll",
												picturePendingAll)+"</html>");
								falg = false;
							}else{
								/**
								 * 如果导入订单失败， 测按照运单号打开界面
								 */
								//pictureWabill(pictureWaybill,bean,picturePending);
								//修改传递参数为 本地待录入和全国待录入运单数   by  352676
								pictureWabill(pictureWaybill,bean,picturePendingLocal,picturePendingAll);
								falg = false;
							}

						} else {
							/**
							 * 如果订单号为空， 则按照运单号打开界面
							 */
							//pictureWabill(pictureWaybill,bean,picturePending);
							//修改传递参数为 本地待录入和全国待录入运单数   by  352676
							pictureWabill(pictureWaybill,bean,picturePendingLocal,picturePendingAll);
							falg = false;
						}
					} 
					else {
						/**
						 * 如果该运单号已存在 则自动退回改条数据
						 */
						pictureWaybill.setRemark(waybillMessage);
						waybillService.backPictureWaybill(pictureWaybill);
						/**
						 * 
						 * 将退回的原因及运单号，订单号 推送给app
						 */
						backPushMessage(pictureWaybill,waybillMessage);
					}
				} else {
					/**
					 * 如果没有获取待补录的数据     在界面上显示   暂时没有待补录的数据，并且退回按钮不可用
					 */
					falg = false;
					// 收货部门编号
					bean.setReceiveOrgCode(null);
					// 收货部门名称
					bean.setReceiveOrgName(null);
					// 设置创建时间
					bean.setReceiveOrgCreateTime(null);
					// 清空现有产品
					waybillEdit.getProductTypeModel().removeAllElements();
					this.picturePanel.pictureViewComp.setVisible(false);
					this.picturePanel.lable1.setVisible(true);
					this.picturePanel.nextButton.setVisible(false);
					this.picturePanel.backButton.setEnabled(false);
					//修改待录入显示本地和全国待录入条数     by:352676
					/*this.picturePanel.button.setText(i18n.get(
							"foss.gui.creating.picturePanel.label",
							picturePending));*/
					this.picturePanel.button.setText("<html>"+i18n.get(
							"foss.gui.creating.picturePanel.labelLocal",
							picturePendingLocal)+"<br/>"+
							i18n.get(
									"foss.gui.creating.picturePanel.labelAll",
									picturePendingAll)+"</html>");
					waybillEdit.numberPanel.getLblNumber().setText(null);
					waybillEdit.numberPanel.getLblOrderLabel().setVisible(false);
					waybillEdit.numberPanel.getLblOrderNumber().setText(null);
					waybillEdit.getBasicPanel().getTxtWaybillNO().setEnabled(false);
					waybillEdit.getBasicPanel().getTxtDriverNumber().setEnabled(false);
					//waybillEdit.getBasicPanel().getCboReceiveModel().setEnabled(false);
				}
			}
		}
		
		/***
		 * 设置当前开单时间
		 * @author Foss-278328-hujinyang
		 * @TIME 20160427
		 */
		bean.setBillTime(new Date());
		
		/**
		 * 初始化装卸费费率
		 * 因为要获取收货部门和产品类型，所以在此处初始化
		 * @author 何海粟
		 */
		initServiceRate(bean);
	}
	
	private void initServiceRate(WaybillPanelVo bean){
		ProductEntityVo productVo = bean.getProductCode();
		String receiveOrgCode = bean.getReceiveOrgCode();
		DefaultComboBoxModel serviceRateModel;
		BigDecimal serviceFeeRate = new BigDecimal(NumberConstants.NUMBER_15);
		if (StringUtils.isNotBlank(receiveOrgCode) && productVo != null) {
			// 调用接口读取装卸费费率
			ServiceFeeRateDto dto = null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(receiveOrgCode,ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			}else
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(receiveOrgCode,ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
			}
	
			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFeeCalculate="));
			} else {
				// 判断是否存在装卸费费率
				if (dto.getServiceFeeRate() == null) {
					throw new WaybillValidateException(dto.getMessage());
				} else {
					serviceFeeRate = dto.getServiceFeeRate();
				}
			}
			serviceRateModel = new DefaultComboBoxModel();
			if(!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				serviceFeeRate = serviceFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_100));
			}
			for(int i = 0 ; i <= serviceFeeRate.intValue(); i++){
				serviceRateModel.addElement(i);
			}
		} else {
			serviceRateModel = new DefaultComboBoxModel();
			for(int i = 0 ; i < PictureWaybillEditUI.getFontsize().length; i++){
				serviceRateModel.addElement(PictureWaybillEditUI.getFontsize()[i]);
			}

		}
		this.waybillEdit.incrementPanel.getCombServiceRate().setModel(serviceRateModel);
	}
	
	/**
	 *设置现金开单付款方式只能选择现金，并且不能修改
	 */
	private void setPaidMethod(WaybillEditUI ui,WaybillPanelVo bean){
		int num = ui.getCombPaymentModeModel().getSize();
		for(int i = 0 ; i < num ; i++){
			DataDictionaryValueVo vo = (DataDictionaryValueVo)ui.getCombPaymentModeModel().getElementAt(i);
			if(WaybillConstants.CASH_PAYMENT.equals(vo.getValueCode())){
				bean.setPaidMethod(vo);
			}
		}
		ui.incrementPanel.getCombPaymentMode().setEnabled(false);
	}
	
	
	/**
	 *按照运单号打开界面
	 */
	private void pictureWabill(WaybillPictureEntity pictureWaybill,WaybillPanelVo bean,int picturePendingLocal,int picturePendingAll){
		try {
			/**
			 * 打开图片开单界面 ，并显示图片，并填中数据
			 */
			/*try {
				this.picturePanel.pictureViewComp.loadImageByWaybillNo(pictureWaybill.getWaybillNo());
			} catch (Exception e) {
				e.printStackTrace();
				this.picturePanel.pictureViewComp.setVisible(false);
				this.picturePanel.lable1.setVisible(false);
				this.picturePanel.nextButton.setVisible(false);
				this.picturePanel.lable2.setVisible(true);
			}*/
			
			bean.setPictureWaybillNo(pictureWaybill.getId());
			bean.setWaybillNo(pictureWaybill.getWaybillNo());
			bean.setDriverCode(pictureWaybill.getDriverCode());
			bean.setCashPay(pictureWaybill.getCashPayFlag());
			bean.setGoodsVolumeTotal(BigDecimal.ZERO);
			bean.setGoodsWeightTotal(BigDecimal.ZERO);
			//判断是否是现金如果是现金。付款方式只能是现金，并且不能修改
			if(WaybillConstants.YES.equals(pictureWaybill.getCashPayFlag())){
				setPaidMethod(waybillEdit,bean);						
			}
			bean.setBaiDuId(pictureWaybill.getBaiDuId());
			bean.setServiceFee(pictureWaybill.getServiceFee());
			bean.setPictureServiceFee(pictureWaybill.getServiceFee());
			if(pictureWaybill.getServiceRate() == null){
				bean.setServiceRate(BigDecimal.ZERO);
				waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(BigDecimal.ZERO);
			}else{
				bean.setServiceRate(pictureWaybill.getServiceRate());
				waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(pictureWaybill.getServiceRate());
			}
			//判断是否是展会货 
						if (WaybillConstants.YES.equals(pictureWaybill
								.getIsExhibitCargo())) {
							bean.setIsExhibitCargo(true);
						} else {
							bean.setIsExhibitCargo(false);
						}
			SaleDepartmentEntity sale = waybillService.querySaleDeptByCode(pictureWaybill.getReceiveOrgCode());
			//设置收获部门信息
			Common.setSalesDepartmentForCentrial(sale, bean, waybillEdit);
			if (WaybillConstants.YES.equals(pictureWaybill
					.getCashPayFlag())) {
				bean.setCashPayFlag(true);
			} else {
				bean.setCashPayFlag(false);
			}
			waybillEdit.numberPanel.getLblNumber().setText(pictureWaybill.getWaybillNo());
			//waybillEdit.getBasicPanel().getCboReceiveModel().setEnabled(false);
			waybillEdit.getBasicPanel().getTxtWaybillNO()
					.setEnabled(false);
			waybillEdit.getBasicPanel().getTxtDriverNumber().setEnabled(false);
			//修改待录入显示本地和全国待录入条数     by:352676
			/*this.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					picturePending)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							picturePending)+"</html>");*/
			this.picturePanel.button.setText("<html>"+i18n.get(
					"foss.gui.creating.picturePanel.labelLocal",
					picturePendingLocal)+"<br/>"+
					i18n.get(
							"foss.gui.creating.picturePanel.labelAll",
							picturePendingAll)+"</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 退回图片运单   推送消息给app
	 */
	public void backPushMessage(WaybillPictureEntity pictureWaybill,String waybillMessage){
		/**
		 * 封装推送的消息
		 */
		BackPictureWaybillEntity entity = new BackPictureWaybillEntity();
		entity.setType(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
		entity.setOrderNo(pictureWaybill.getOrderNo());
		entity.setWaybillNo(pictureWaybill.getWaybillNo());
		entity.setMessage(waybillMessage);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		entity.setCreateTime(format.format(new Date()));
		PushMessageEntity message = new PushMessageEntity();
		message.setPushType(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
		message.setPushMessage(entity);
		
		/*
		 * 暂时注释
		 JSONObject json = new JSONObject(message);

		waybillService.pushMessage(WaybillConstants.PUSHTYPE_ONE,
								   WaybillConstants.MESSAGETYPE_ONE,
								   WaybillConstants.DEVICETYPE_THREE,
								   pictureWaybill.getBaiDuId(), 
								   i18n.get("foss.gui.creating.pushmessage.title"), 
								   i18n.get("foss.gui.creating.pushmessage.description.back"), 
								   json.toString());*/
		
		WaybillPictureEntity picture =new WaybillPictureEntity();
		picture.setId(pictureWaybill.getId());
		pictureWaybill =waybillService.queryWaybillPictureByEntity(picture);
		// 获得当前用户信息
		//获得当前用户信息
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		String empName = user.getEmployee().getEmpName();
		String mobilePhone = user.getEmployee().getMobilePhone();
		/*
		 * @author Foss-278328-hujinyang
		 * 需求编号：DN201606240001 短信格式修改
		 * 司机手机端接收实时短信：内容模板为（图片退回+单号+退回原因+时间+退回人+退回人手机号），不留空格。【例：图片退回11111111保价不能为零18:32张三18888888888】
		 * @date 20160708  
		 */
		
		waybillMessage="图片退回"+pictureWaybill.getWaybillNo()+waybillMessage+format.format(new Date())+empName+mobilePhone;
		/*waybillService.saveWaybillpushmessage(pictureWaybill.getWaybillNo(),
											  waybillMessage,
											  user.getEmployee().getEmpCode(),
				                              user.getEmployee().getDepartment().getUnifiedCode(),
				                              pictureWaybill.getMobilephone());*/
		//修改为实时发短信
		waybillService.saveWaybillpushmessageSendSms(pictureWaybill.getWaybillNo(),
													waybillMessage, 
													user.getEmployee().getEmpCode(),
													user.getEmployee().getDepartment().getUnifiedCode(),
													pictureWaybill.getMobilephone());
	}
	
	/**
	 * 根据订单号从crm获取订单数据，并填充数据
	 */
	private String importOrder(String orderNumber, WaybillPanelVo panelVo) {
		/*WaybillPictureEntity entity = new WaybillPictureEntity();
		entity.setActive(FossConstants.YES);
		entity.setOrderNo(OrderNumber);
		WaybillPictureEntity waybillPictureEntiry = waybillService.queryWaybillPictureByEntity(entity);		
		if(waybillPictureEntiry != null && 
				!WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillPictureEntiry.getPendgingType())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderRepeat"));
			return "ERROR";
		}*/
		
		if(waybillService.checkWaybillAndPendingOrderNo(orderNumber)){
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderRepeat"));
			return "ERROR";
		}
		
		CrmOrderDetailDto orderDetailVo = null;
		try {
			LOG.info("判断是否是crm导入运单数据开始...");
			if(CommonContents.logToggle){
				CustomerImpOperLogHandler.setLogger(panelVo.getWaybillNo()).setInfo("crmImportSerivceStart", "crm导入运单数据开始时间:");
			}
			long begin = System.currentTimeMillis() ;
			orderDetailVo = waybillService.importOrder(orderNumber);
			if(CommonContents.logToggle){
				CustomerImpOperLogHandler.setLogger(panelVo.getWaybillNo()).setInfo("crmImportSerivceEnd", "crm导入运单数据结束时间:");
			}
			LOG.info("判断是否是crm导入运单数据结束... 耗时："+(System.currentTimeMillis()-begin));
		} catch (BusinessException e) {
			MessageI18nUtil.getMessage(e, i18n);
			return "ERROR";
		}
		String orderStatus = orderDetailVo.getOrderStatus();
		// 若导入的订单状态非受理状态，则提示订单不能导入,就是说只有受理中，已退回，接货中的订单能导入
		if (!WaybillConstants.CRM_ORDER_ACCEPT.equals(orderStatus)
				&&!WaybillConstants.CRM_ORDER_GOBACK.equals(orderStatus)
				&&!WaybillConstants.CRM_ORDER_RECEIPTING.equals(orderStatus)) {
		
			DataDictionaryValueEntity e = dataDictionaryValueService
					.queryDataDictoryValueByCode(WaybillConstants.ORDER_STATUS, orderStatus);
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderStatus",e.getValueName()));
			return "ERROR";
		}

		// 按完整订单号查询，查询到的订单已开单，则在点击“查询按钮”时，提示订单已开单
		if (StringUtil.isNotEmpty(orderDetailVo.getWaybillNumber())
				&& StringUtil
						.isNotEmpty(orderDetailVo.getActualFreightStatus())) {
			if (!WaybillConstants.OBSOLETE.equals(orderDetailVo
					.getActualFreightStatus())
					&& !WaybillConstants.ABORTED.equals(orderDetailVo
							.getActualFreightStatus())) {
				return "ERROR";
			}
		}
		String accecptDept = orderDetailVo.getAcceptDept();
		String loginDept = orderDetailVo.getAcceptDept();
		OrgAdministrativeInfoEntity loginOrg = getLoginDept();
		if (loginOrg != null) {
			loginDept = loginOrg.getUnifiedCode();
		}
		// 集中开单取收货部门的标杆编码
		if (panelVo.getPickupCentralized()) {
			OrgAdministrativeInfoEntity orgEntity = waybillService
					.queryByCode(panelVo.getReceiveOrgCode());
			loginDept = orgEntity.getUnifiedCode();
		}
		// 若导入的订单的始发部门非本部门时，系统提示营业员，营业员确认后，对应选择的订单的详细信息导入至运单开单界面中
		if (!accecptDept.equals(loginDept)) {
			if (JOptionPane.YES_OPTION != JOptionPane
					.showConfirmDialog(
							null,
							i18n.get("foss.gui.creating.webOrderDialog.showdialog.label"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.YES_NO_OPTION)) {
				return "ERROR";
			} else {
				if (panelVo.getPickupCentralized()) {
					orderDetailVo.setAcceptDept(panelVo.getReceiveOrgCode());
				} else {
					if (loginOrg != null) {
						orderDetailVo.setAcceptDept(loginOrg.getCode());
					}
				}
			}
		} else {
			orderDetailVo.setAcceptDept(loginOrg.getCode());
		}
		// 把crm的code转化为foss
		String tranportMode = orderDetailVo.getTransportMode();
		if (StringUtils.isNotEmpty(tranportMode)) {
			for (CrmTransportTypeEnum tenum : CrmTransportTypeEnum.values()) {
				if (tranportMode.equals(tenum.getCode())) {
					orderDetailVo.setTransportMode(tenum.getFossCode());
				}
			}
		}
		try {
			ProductEntityVo product = getProductTypeByCode(orderDetailVo.getTransportMode());
			if(product != null){
				/**
				 * DEFECT-5165:059387登陆GUI，导入场到场订单Y141101123855开单，运输性质带入门到门，
				 * 且没有勾选精准大票
				 * 
				 * @author 200945-wutao 逻辑： 1.导入的时候，先判断精准大票是否选择，如果选择了 ；
				 *         2.判断：CRM传递过来的运输性质是否为精准大票
				 *         ，如果是精准大票的话的，什么都不需要做；如果不是，那么就需要就要取消精准大票的勾选框
				 *         3.如果没有选择，那么判断运输性质是否为精准大票，是的话就勾选上精准大票框！
				 * 
				 */
				boolean isSelectedisBigGoods = waybillEdit.basicPanel.getChbBigGoods()
						.isSelected();
				// TRUE
				if (isSelectedisBigGoods) {
					if (ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR
							.equals(product.getCode())
							|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD
									.equals(product.getCode())) {
						// 这里不需要做什么操作！
					} else {
						waybillEdit.basicPanel.getChbBigGoods().setSelected(false);
					}
				} else {
					if (ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR
							.equals(product.getCode())
							|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD
									.equals(product.getCode())) {
						waybillEdit.basicPanel.getChbBigGoods().setSelected(true);
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			WebOrderDialog dialog = new WebOrderDialog(waybillEdit,pictureWaybillType);
			dialog.setOrderToWaybillEditUI(orderDetailVo, panelVo);
			//setOrderToWaybillEditUI(orderDetailVo, panelVo);
			// 来自官网的订单，导入成功后设置代收和保不价可编辑
			if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(orderDetailVo
					.getResource())) {
				waybillEdit.incrementPanel.getTxtInsuranceValue().setEnabled(false);
				waybillEdit.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
			}
			
			/**
			 * 如果代收货款大于0，则给予提示，选择目的站时会验证是否能开代收货款
			 */
			if (panelVo.getCodAmount() != null
					&& panelVo.getCodAmount().intValue() > 0) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.webOrderDialog.msgbox.codAmount.alert"));
			}
			
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * 
	 * <p>
	 * 获取当前登录人员所在部门信息
	 * </p>
	 */
	private OrgAdministrativeInfoEntity getLoginDept() {
		OrgAdministrativeInfoEntity org = null;
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if (user != null) {
			EmployeeEntity emp = (EmployeeEntity) user.getEmployee();
			if (emp != null) {
				org = emp.getDepartment();
			}
		}
		return org;
	}
	/**
	 *初始化界面布局
	 */
	private void init() {
		this.setAutoscrolls(true);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), RowSpec.decode("1dlu"),
				RowSpec.decode("default:grow"), }));

		picturePanel = new PicturePanel(
				(PictureWaybillEditUI) this.editor.getComponent());
		picturePanel.setPreferredSize(new Dimension(NUM_1265, NUM_535));
		add(picturePanel, "2, 2, fill, fill");

		final PictureWaybillEditUI ui=(PictureWaybillEditUI) this.editor.getComponent();
		
		WaybillEditUI waybillEditUI3=application.getAttribute("WaybillEditUI");
		if(waybillEditUI3!=null){
			
			waybillEdit=waybillEditUI3;
		
		}else{
			
			waybillEdit = new WaybillEditUI(waybillType, pictureWaybillType);
		}
		
		waybillEdit.setPictureWaybillEditUI(ui);
		
		FullScreenAction fullScreenAction=new FullScreenAction();
		
		fullScreenAction.setInjectUI(waybillEdit);
		
		waybillEdit.getButtonPanel().getFullScreen().addActionListener(fullScreenAction);
		
		
		
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
//				WaybillEditUI waybillEdit2 = new WaybillEditUI(waybillType, pictureWaybillType);
//				application.setAttribute("WaybillEditUI", waybillEdit2);
//			}
//		});
		
		
		sp = new JScrollPane(waybillEdit,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(NUM_1265, NUM_310));
		sp.addMouseWheelListener(new PictureMouseWheelListener(sp));
		add(sp, "2, 4, fill, fill");
		addscrollpaneForEnter();
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public IApplication getApplication() {
		return application;
	}

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	public IEditor getEditor() {
		return editor;
	}

	public void setEditor(IEditor editor) {
		this.editor = editor;
	}

	public String getPictureWaybillType() {
		return pictureWaybillType;
	}

	public void setPictureWaybillType(String pictureWaybillType) {
		this.pictureWaybillType = pictureWaybillType;
	}

	public String getPictureOperateType() {
		return pictureOperateType;
	}

	public void setPictureOperateType(String pictureOperateType) {
		this.pictureOperateType = pictureOperateType;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}
	/**
	 * 
	 * 导入订单到开单界面
	 * 
	 * */
/*	private void setOrderToWaybillEditUI(CrmOrderDetailDto orderDetailVo,
			WaybillPanelVo panelVo) {
		// 如果单号不为空
		if (StringUtils.isNotEmpty(orderDetailVo.getWaybillNumber())) {
			waybillEdit.numberPanel.getLblNumber().setVisible(true);
			waybillEdit.basicPanel.getTxtWaybillNO().setText(
					orderDetailVo.getWaybillNumber());
		}
		// 设置订单号
		waybillEdit.numberPanel.getLblOrderLabel().setVisible(true);
		waybillEdit.numberPanel.getLblOrderNumber().setText(
				orderDetailVo.getOrderNumber());

		// 来自阿里巴巴的订单不可编辑装卸费，取Resource是由于跟CRM沟通过，channelType在官网，呼叫中心，营业部订单这些类型在这个字段里面是空的，需要去resource才能取到值
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(orderDetailVo
				.getResource())
				|| WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT
						.equals(orderDetailVo.getResource())) {
			waybillEdit.incrementPanel.getTxtServiceCharge().setEnabled(false);
		}
		// 来自官网的订单设置保价金额，代收货款金额不可编辑ISSUE-1452
		// ,取Resource是由于跟CRM沟通过，channelType在官网，呼叫中心，营业部订单这些类型在这个字段里面是空的，需要去resource才能取到值
		else if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(orderDetailVo
				.getResource())) {
			waybillEdit.incrementPanel.getTxtInsuranceValue().setEnabled(false);
			waybillEdit.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
		} else {
			waybillEdit.incrementPanel.getTxtInsuranceValue().setEnabled(true);
			waybillEdit.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
		}
		try {
			// 优惠券导入
			panelVo.setPromotionsCode(orderDetailVo.getCouponNumber());
			// 渠道客户ID：官网用户名
			panelVo.setChannelCustId(orderDetailVo.getChannelCustId());
			// 订单号
			panelVo.setOrderNo(orderDetailVo.getOrderNumber());
			// 订单来源
			panelVo.setOrderChannel(orderDetailVo.getResource());

			String paymentType = orderDetailVo.getPaymentType();
			for (CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(paymentType)) {
					panelVo.setOrderPayment(crm.getFossCode());
				}

			}
			// 发货客户id
			panelVo.setDeliveryCustomerId(orderDetailVo.getShipperId());
			// 发货客户编号

			if (StringUtils.isNotEmpty(orderDetailVo.getShipperNumber())) {
				panelVo.setDeliveryCustomerCode(orderDetailVo
						.getShipperNumber());
			} else {
				panelVo.setDeliveryCustomerCode(orderDetailVo.getShipperId());
			}
			// 发货客户姓名
			panelVo.setDeliveryCustomerName(orderDetailVo.getShipperName());
			// 发货联系人id
			panelVo.setDeliveryCustomerContactId(orderDetailVo
					.getContactManId());
			// 发货联系人名称
			panelVo.setDeliveryCustomerContact(orderDetailVo.getContactName());
			// 联系人手机
			panelVo.setDeliveryCustomerMobilephone(orderDetailVo
					.getContactMobile());
			// 联系人电话
			panelVo.setDeliveryCustomerPhone(orderDetailVo.getContactPhone());
			// 联系地址备注
			panelVo.setDeliveryCustomerAddressNote(orderDetailVo
					.getContactAddrRemark());
			// 收货人地址备注
			panelVo.setReceiveCustomerAddressNote(orderDetailVo
					.getReceiverCustAddrRemark());
			// 是否接货
			panelVo.setPickupToDoor(orderDetailVo.isReceiveGoods());
			// 现在司机工号不受是否接货的限制，根据ISSUE-3164修改
			waybillEdit.basicPanel.getTxtDriverNumber().setEditable(true);
			
			DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
			String proviceCode = null;
			if (orderDetailVo.getContactProvince() != null) {
				proviceCode = orderDetailVo.getContactProvinceCode();
			}
			// 联系人省份
			panelVo.setDeliveryCustomerProvCode(proviceCode);

			String cityCode = null;
			if (orderDetailVo.getContactCity() != null && proviceCode != null) {
				cityCode = orderDetailVo.getContactCityCode();
			}
			// 联系人城市
			panelVo.setDeliveryCustomerCityCode(cityCode);

			String areaCode = null;
			if (orderDetailVo.getContactArea() != null && cityCode != null) {
				areaCode = orderDetailVo.getContactAreaCode();
			}
			// 联系人区县
			panelVo.setDeliveryCustomerDistCode(areaCode);

			// 联系地址
			panelVo.setDeliveryCustomerAddress(orderDetailVo
					.getContactAddress());

			// 定义省市区县对象
			AddressFieldDto deliverAddressDto = Common
					.getAddressFieldInfoByCode(waybillEdit, proviceCode, cityCode,
							areaCode);
			waybillEdit.getConsignerPanel().getTxtConsignerArea()
					.setAddressFieldDto(deliverAddressDto);

			// 始发网点
			String orgCode = panelVo.getReceiveOrgCode();
			if (StringUtils.isEmpty(orgCode)) {
				orgCode = orderDetailVo.getAcceptDept();
			}
			panelVo.setReceiveOrgCode(orgCode);

			// 货部门省份编码
			if (StringUtil.isNotEmpty(orgCode)) {
				panelVo.setReceiveOrgProvCode(CommonUtils
						.getReceiveOrgProvCode(orgCode));
			}

			if (orgCode != null) {
				SaleDepartmentEntity org = waybillService
						.querySaleDeptByCode(orgCode);

				if (org != null) {
					// 始发网点 create time
					panelVo.setReceiveOrgName(org.getName());
					if (org.getOpeningDate() != null) {
						panelVo.setReceiveOrgCreateTime(org.getOpeningDate());
					}
				}
			}

			// 收货客户id
			panelVo.setReceiveCustomerId(orderDetailVo.getReceiverCustId());
			// 收货客户编号
			panelVo.setReceiveCustomerCode(orderDetailVo
					.getReceiverCustNumber());
			// 收获客户姓名
			panelVo.setReceiveCustomerName(orderDetailVo.getReceiverCustName());
			// 收货人联系姓名
			panelVo.setReceiveCustomerContact(orderDetailVo
					.getReceiverCustName());
			// 接货人联系手机
			panelVo.setReceiveCustomerMobilephone(orderDetailVo
					.getReceiverCustMobile());
			// 接货人联系电话
			panelVo.setReceiveCustomerPhone(orderDetailVo
					.getReceiverCustPhone());
			proviceCode = null;// 清空
			if (orderDetailVo.getReceiverCustProvince() != null) {
				proviceCode = orderDetailVo.getReceiverCustProvinceCode();
			}
			// 接货人省份
			panelVo.setReceiveCustomerProvCode(proviceCode);
			cityCode = null;// 清空
			if (orderDetailVo.getReceiverCustCity() != null
					&& proviceCode != null) {
				cityCode = orderDetailVo.getReceiverCustCityCode();
			}
			// 接货人城市
			panelVo.setReceiveCustomerCityCode(cityCode);
			areaCode = null;// 清空
			if (orderDetailVo.getReceiverCustArea() != null && cityCode != null) {
				areaCode = orderDetailVo.getReceiverCustAreaCode();
			}
			// 接货人区县
			panelVo.setReceiveCustomerDistCode(areaCode);

			// 接货人详细地址
			panelVo.setReceiveCustomerAddress(orderDetailVo
					.getReceiverCustAddress());

			// 定义省市区县对象
			AddressFieldDto receiverAddressDto = Common
					.getAddressFieldInfoByCode(waybillEdit, proviceCode, cityCode,
							areaCode);
			waybillEdit.getConsigneePanel().getTxtConsigneeArea()
					.setAddressFieldDto(receiverAddressDto);
			// zxy 20130924 BUG-55905 start:取出订单的产品，否则下面会使用界面的默认值
			// 货物运输方式
			ProductEntityVo productEntity = getProductTypeByCode(orderDetailVo
					.getTransportMode());
			panelVo.setProductCode(productEntity);
			// zxy 20130924 BUG-55905 end:取出订单的产品，否则下面会使用界面的默认值
			// 到达网点 DP00772
			String receivingToPointUnifiedCode = orderDetailVo
					.getReceivingToPoint();
			if (StringUtils.isNotEmpty(receivingToPointUnifiedCode)) {
				// 根据部门编码查询部门简称
				OrgAdministrativeInfoEntity orgEntity = baseDataService
						.queryOrgAdministrativeInfoEntityByUnifiedCode(receivingToPointUnifiedCode);
				if (orgEntity != null) {

					SaleDepartmentEntity saleDepartment = waybillService
							.querySaleDeptByCode(orgEntity.getCode());

					if (saleDepartment != null) {

						BranchVo branchVo = new BranchVo();
						branchVo.setCode(saleDepartment.getCode());
						branchVo.setName(saleDepartment.getName());
						branchVo.setCode(saleDepartment.getCode());
						branchVo.setName(saleDepartment.getName());
						branchVo.setSingleBillLimitkg(saleDepartment
								.getSingleBillLimitkg());
						branchVo.setSingleBillLimitvol(saleDepartment
								.getSingleBillLimitvol());
						branchVo.setSinglePieceLimitkg(saleDepartment
								.getSinglePieceLimitkg());
						branchVo.setSinglePieceLimitvol(saleDepartment
								.getSinglePieceLimitvol());
						// 是否可自提
						branchVo.setPickupSelf(saleDepartment.getPickupSelf());
						// 是否送货上门
						branchVo.setDelivery(saleDepartment.getDelivery());
						// 所属城市被临时存放在PICKUP_AREA_DESC列当中传来出来
						branchVo.setCityCode(saleDepartment.getPickupAreaDesc());
						// 取消到达日期
						branchVo.setCancelArrivalDate(saleDepartment
								.getCancelArrivalDate());
						// 转货部门
						branchVo.setTransferGoodDept(saleDepartment
								.getTransferGoodDept());
						// 是否支持货到付款
						branchVo.setArriveCharge(saleDepartment
								.getCanCashOnDelivery());
						// 是否支持返单签收
						branchVo.setCanReturnSignBill(saleDepartment
								.getCanReturnSignBill());
						// 是否支持到达
						branchVo.setArrive(saleDepartment.getArrive());
						// ------------------

						panelVo.setCustomerPickupOrgCode(branchVo);

						// 是否支持代收货款
						branchVo.setCanAgentCollected(saleDepartment
								.getCanAgentCollected());
						panelVo.setCanAgentCollected(saleDepartment
								.getCanAgentCollected());

						// 设置提货网点名称
						panelVo.setCustomerPickupOrgName(branchVo.getName());
						panelVo.setArriveCharge(branchVo.getArriveCharge());

						ShowPickupStationDialogAction showPickupStationDialogAction = new ShowPickupStationDialogAction();
						showPickupStationDialogAction.setInjectUI(waybillEdit);
						showPickupStationDialogAction.setDialogData(branchVo,
								panelVo);
						showPickupStationDialogAction.setLoadLine(panelVo);
						showPickupStationDialogAction
								.setAirDeptEnabled(panelVo);
					} else {
						// 清空目的站
						cleanTargetEmpty(panelVo);
					}
				} else {
					// 清空目的站
					cleanTargetEmpty(panelVo);
				}
			} else {
				// 清空目的站
				cleanTargetEmpty(panelVo);
			}
			//
			// 货物名称
			panelVo.setGoodsName(orderDetailVo.getGoodsName());
			// 托运货物总件数
			panelVo.setGoodsQtyTotal(orderDetailVo.getGoodsNumber());

			// zxy 20131118 ISSUE-4391 DEFECT-536 刷新打木托标签列表
			Common.refreshLabeledGood(panelVo, waybillEdit);
			// zxy 20131118 ISSUE-4391 DEFECT-536 刷新打木托标签列表
			// 托运货物总重量
			panelVo.setGoodsWeightTotal(BigDecimal.valueOf(orderDetailVo
					.getTotalWeight()));
			// 货物总体积
			panelVo.setGoodsVolumeTotal(BigDecimal.valueOf(orderDetailVo
					.getTotalVolume()));
			// 货物包装类型
			panelVo.setGoodsPackage(orderDetailVo.getPacking());
			panelVo.setOtherPackage(orderDetailVo.getPacking());
			// 货物类型
			panelVo.setGoodsType(orderDetailVo.getGoodsType());
			// 报价申明价值
			panelVo.setInsuranceAmount(orderDetailVo.getInsuredAmount());
			panelVo.setInsuranceAmountCanvas(orderDetailVo.getInsuredAmount()
					.toString());
			// 代收货款类型

			String reciveLoanType = orderDetailVo.getReciveLoanType();

			for (CrmRefundTypeEnum crm : CrmRefundTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(reciveLoanType)) {
					String fossCode = crm.getFossCode();
					if (StringUtils.isNotEmpty(fossCode)) {
						panelVo.setRefundType(dataDictionaryValueEntityToVo(
								WaybillConstants.REFUND_TYPE, crm.getFossCode()));
					}
				}

			}

			// 代收货款金额
			if (orderDetailVo.getReviceMoneyAmount() != null) {
				panelVo.setCodAmount(orderDetailVo.getReviceMoneyAmount());
				panelVo.setCodAmountCanvas(orderDetailVo.getReviceMoneyAmount()
						.toString());
			} else {
				panelVo.setCodAmount(BigDecimal.ZERO);
				panelVo.setCodAmountCanvas("0");
			}

			// 签收单

			String crmReturnBillType = orderDetailVo.getReturnBillType();

			for (CrmReturnBillTypeEnum crm : CrmReturnBillTypeEnum.values()) {
				// 订单返单方式
				if (crm.getCrmCode().equals(crmReturnBillType)) {
					DataDictionaryValueVo returnBillType = dataDictionaryValueEntityToVo(
							WaybillConstants.RETURN_BILL_TYPE,
							crm.getFossCode());
					if (returnBillType == null) {
						returnBillType = new DataDictionaryValueVo();
						returnBillType
								.setValueCode(WaybillConstants.NOT_RETURN_BILL);
					}
					panelVo.setReturnBillType(returnBillType);
				}

			}

			// 付款方式
			for (CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()) {
				// 订单付款方式
				if (crm.getCrmCode().equals(paymentType)) {
					panelVo.setPaidMethod(dataDictionaryValueEntityToVo(
							WaybillConstants.PAYMENT_MODE, crm.getFossCode()));
				}

			}
			// 储运事项
			if (StringUtil.isEmpty(orderDetailVo.getTransNote())) {
				panelVo.setTransportationRemark("");
				panelVo.setTransportationRemarkOfWebOrder(""); // 外部订单的储运事项
			} else {
				panelVo.setTransportationRemark(orderDetailVo.getTransNote());
				panelVo.setTransportationRemarkOfWebOrder(orderDetailVo
						.getTransNote());
			}

			// 发货人区域信息
			String deliveryProvinve = orderDetailVo.getContactProvince();
			String deliveryCity = orderDetailVo.getContactCity();
			String deliveryArea = orderDetailVo.getContactArea();

			if (deliveryProvinve != null && deliveryCity != null
					&& deliveryArea != null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve + "-"
						+ deliveryCity + "-" + deliveryArea);
			} else if (deliveryProvinve != null && deliveryCity != null
					&& deliveryArea == null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve + "-"
						+ deliveryCity);
			} else if (deliveryProvinve != null && deliveryCity == null
					&& deliveryArea == null) {
				panelVo.setDeliveryCustomerArea(deliveryProvinve);
			}

			// 收货人区域信息
			String receiveProvinve = orderDetailVo.getReceiverCustProvince();
			String receiveCity = orderDetailVo.getReceiverCustCity();
			String receiveArea = orderDetailVo.getReceiverCustArea();

			if (receiveProvinve != null && receiveCity != null
					&& receiveArea != null) {
				panelVo.setReceiveCustomerArea(receiveProvinve + "-"
						+ receiveCity + "-" + receiveArea);
			} else if (receiveProvinve != null && receiveCity != null
					&& receiveArea == null) {
				panelVo.setReceiveCustomerArea(receiveProvinve + "-"
						+ receiveCity);
			} else if (receiveProvinve != null && receiveCity == null
					&& receiveArea == null) {
				panelVo.setReceiveCustomerArea(receiveProvinve);
			}

			// 初始化运输性质
			Common.initCombProductType(panelVo, waybillEdit);
			String productCode = null;
			if (productEntity != null && productEntity.getCode() != null) {
				productCode = productEntity.getCode();
				// 根据运输性质改变提货方式
				Common.changePickUpMode(panelVo, waybillEdit);
				// 空运、偏线以及中转下线无法选择签收单返单
				Common.setReturnBill(panelVo, waybillEdit);
				// 偏线与空运不能选择预付费保密
				Common.setSecretPrepaid(panelVo, waybillEdit);

				// 获取返单类别
				DataDictionaryValueVo returnBillType = panelVo
						.getReturnBillType();

				// 重新设置返单类别(和网上订单相同)
				panelVo.setReturnBillType(returnBillType);

			}

			Common.setSaveAndSubmitFalse(waybillEdit);

			if (orderDetailVo.getShipperId() != null) {
				CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
				String shipperNumer = orderDetailVo.getShipperNumber();
				if (StringUtils.isEmpty(shipperNumer)) {
					shipperNumer = orderDetailVo.getShipperId();
				}
				condition.setCustCode(shipperNumer);
				List<CustomerQueryConditionDto> dtoList = customerService
						.queryCustomerByCondition(condition);
				if (dtoList == null || dtoList.size() == 0) {
					panelVo.setChargeMode(false);// 是否月结
					// 优惠类型
					panelVo.setPreferentialType("");
				} else {
					CustomerQueryConditionDto dtoCust = null;
					for (int i = 0; i < dtoList.size(); i++) {
						CustomerQueryConditionDto dtoCust2 = dtoList.get(i);
						if (dtoCust2 != null && dtoCust2.getCustCode() != null
								&& dtoCust2.getCustCode().equals(shipperNumer)) {
							dtoCust = dtoCust2;
							break;
						}
					}

					if (dtoCust != null) {
						panelVo.setChargeMode(DictionaryValueConstants.CLEARING_TYPE_MONTH
								.equals(dtoCust.getChargeType()) ? Boolean
								.valueOf(true) : Boolean.valueOf(false));
						panelVo.setPreferentialType(dtoCust
								.getPreferentialType());// 设置优惠类型
					}

				}
			} else {

				panelVo.setChargeMode(false);// 是否月结
				// 优惠类型
				panelVo.setPreferentialType("");
			}

			// 有来源
			panelVo.setOrderChannel(orderDetailVo.getResource());

			// 付款方式
			panelVo.setOrderPayment(orderDetailVo.getPaymentType());

			// CRM提货方式
			String crmDeliveryMode = orderDetailVo.getDeliveryMode();

			// 根据运输性质获取提货方式
			if (productCode != null) {
				String transType = CommonUtils
						.convertProductCodeToTransType(productCode);
				panelVo.setReceiveMethod(dataDictionaryValueEntityToVo(
						transType, CommonUtils.convertCrmReceiveMethod(
								productCode, crmDeliveryMode)));
			} else {
				// 根据汽运获取提货方式
				DataDictionaryValueVo vo = dataDictionaryValueEntityToVo(
						WaybillConstants.PICKUP_GOODS_HIGHWAYS,
						CommonUtils
								.convertCrmReceiveMethodByCrmCode(crmDeliveryMode));
				if (vo == null) {
					// 根据空运获取提货方式
					vo = dataDictionaryValueEntityToVo(
							WaybillConstants.PICKUP_GOODS_AIR,
							CommonUtils
									.convertCrmReceiveMethodByCrmCode(crmDeliveryMode));
				}
				// 设置提货方式
				panelVo.setReceiveMethod(vo);
			}

			// 提货方式
			if (panelVo.getReceiveMethod() != null) {
				// 内部带货
				Common.innerPickup(panelVo, waybillEdit);
				// 各种自提
				Common.selfPickup(panelVo, waybillEdit);

				// 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
				Common.validateCustomerPointBySelfPickup(panelVo, waybillEdit);
				
				Common.setSaveAndSubmitFalse(waybillEdit);

			}
			// 限保物品
			isInsurGoods(panelVo);
			waybillEdit.basicPanel.getTxtWaybillNO().requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	private void addscrollpaneForEnter()
	{
		
		JTextFieldValidate txtConsigneeAdressFile=this.waybillEdit.getConsigneePanel().getTxtConsigneeAddress();
		JTextField jtextField = this.waybillEdit.getConsignerPanel().getTxtConsignerAddress();
		//JTextFieldValidate txtInsideRemark=this.waybillEdit.getPictureCargoInfoPanel().getPackageRemark();
		JTextField txtSize = this.waybillEdit.getPictureCargoInfoPanel().getTxtSize();
		JTextFieldValidate txtCashOnDelivery=this.waybillEdit.incrementPanel.getTxtCashOnDelivery();
		int sit= NumberConstants.NUMBER_200;
		int sit2= NumberConstants.NUMBER_150;
		int sit3= NumberConstants.NUMBER_100;
		JScrollBar   sbar=sp.getVerticalScrollBar();   
		
		if(jtextField != null){
			jtextField.addKeyListener(new EnterKeyForScrollPanAction(sit3, sbar));
		}
		
		if(txtConsigneeAdressFile!=null)
		{
			txtConsigneeAdressFile.addKeyListener(new EnterKeyForScrollPanAction(sit, sbar));
			
		}
		
		if(txtSize!=null)
		{
			txtSize.addKeyListener(new EnterKeyForScrollPanAction(sit2, sbar));
			
		}
		
		if(txtCashOnDelivery!=null)
		{
			txtCashOnDelivery.addKeyListener(new EnterKeyForScrollPanAction(sit3, sbar));
		}
		
	}
	
	
	/**
	 * 数据字典Entity转换VO
	 * 
	 */
	private DataDictionaryValueVo dataDictionaryValueEntityToVo(
			String termsCode, String valueCode) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		String terms = termsCode;
		if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN.equals(terms)) {
			terms = WaybillConstants.PICKUP_GOODS_HIGHWAYS;
		} else if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN
				.equals(terms)) {
			terms = WaybillConstants.PICKUP_GOODS_AIR;
		}

		if (StringUtil.isNotEmpty(valueCode)) {
			DataDictionaryValueEntity entity = waybillService
					.queryDataDictoryValueEntity(terms, valueCode);
			if (entity == null)
				return null;
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			PropertyUtils.copyProperties(vo, entity);
			return vo;
		} else {
			return null;
		}
	}
	
    /**
	 * 
	 * 是否限保物品
	 * 
	 */
	private void isInsurGoods(WaybillPanelVo bean) {
		/**
		 * 判断是否限保物品 1. SUC-494-录入货物信息 规则： SR1： 3.
		 * 若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”；
		 */
		LimitedWarrantyItemsEntity entity = waybillService.isInsurGoods(bean.getGoodsName());
		if (entity != null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.insurGoods.one"));
			bean.setVirtualCode(entity.getVirtualCode());
			bean.setLimitedAmount(entity.getLimitedAmount());// 限制报价金额
			waybillEdit.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 设置为不可编辑
			/**
			 * 如果限保物品保价金额为空时：设置为0，不可编辑
			 */
			if (entity.getLimitedAmount() != null) {
				bean.setInsuranceAmount(entity.getLimitedAmount());
				bean.setInsuranceAmountCanvas(entity.getLimitedAmount().toString());
			} 
		}
	}
	
	/**
	 * 数据字典Entity转换VO
	 * 
	 */
	private ProductEntityVo getProductTypeByCode(String productCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (StringUtil.isNotEmpty(productCode)) {
			ProductEntity entity = waybillService.queryTransTypeById(productCode);
			//zxy 20130929 BUG-56426 start 新增：如果按Id查不到，则按Code再查询一次
			if(entity == null){
				ProductEntity productEntity = new ProductEntity();
				productEntity.setActive(FossConstants.YES);
				productEntity.setCode(productCode);
				List<ProductEntity> productEntityLst =  waybillService.findProducts(productEntity);
				if(productEntityLst != null && productEntityLst.size() > 0){
					entity = productEntityLst.get(0);
				}
			}
			if (entity == null){
				return null;
			}
			ProductEntityVo vo = new ProductEntityVo();
			PropertyUtils.copyProperties(vo, entity);
			return vo;
		} else {
			return null;
		}
	}
	
    /**
	 * 
	 * 清空目的站以及预配线路
	 * 
	 * 
	 */
	private void cleanTargetEmpty(WaybillPanelVo bean) {
		// 清空提货网点
		bean.setCustomerPickupOrgCode(null);
		// 清空提货网点名称
		bean.setCustomerPickupOrgName("");
		// 清空目的站
		bean.setTargetOrgCode("");
		// 清空长短途
		bean.setLongOrShort(null);
		// 清空预配线路
		bean.setLoadLineName("");
	}
	public static BigDecimal[] getFontsize() {
		return fontSize;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	
}
