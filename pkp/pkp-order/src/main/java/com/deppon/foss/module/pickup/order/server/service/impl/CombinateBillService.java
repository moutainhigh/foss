package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStoreCodeSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.dao.IBigCustomeDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ICombinateBillDao;
import com.deppon.foss.module.pickup.order.api.server.service.ICombinateBillService;
import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPrintEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * 
 * 大客客户合票功能
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:183272,date:2015年9月12日 下午2:43:41,content
 * </p>
 * 
 * @author 183272
 * @date 2015年9月12日 下午2:43:41
 * @since
 * @version
 */
public class CombinateBillService implements ICombinateBillService {
	// 星号体积分隔符
	// private final static String GOODSSIZESPLIT = "*";

	// 逗号分隔符
	private final static String COMMA_SYMBOL = ",";

	// 逗号分隔符
	private final static String PLUS_SYMBOL = "+";

	// 标签格式
	private final static String SERIALNO_FORMAT = "0000";

	// 提货方式名称
	public final static String DELIVER_UP_CN = "送货上楼";// DELIVER_UP
	// 开单付款方式名称
	public final static String MONTH_PAYMENT_CN = "月结";
	// 运输性质
	public final static String FLF_CN= "精准卡航";
	// 最大体积
	// private final static float MAXVOLUME = 2.5f;

	/**
	 * Dao
	 */
	private ICombinateBillDao combinateBillDao;
	private IBigCustomeDao iBigCustomeDao;
	private IWaybillPendingDao waybillPendingDao;
	
	IStoreCodeSalesDeptService  storeCodeSalesDeptService ;
	
	
	/**
	 * 获取ID使用的servcie
	 */
	
	IProductService productService;
	
	

	ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	private IWaybillPendingService waybillPendingService;
	
	private ILabelPrintInfoService labelPrintInfoService;
	
	
	public ILabelPrintInfoService getLabelPrintInfoService() {
		return labelPrintInfoService;
	}

	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public IWaybillPendingService getWaybillPendingService() {
		return waybillPendingService;
	}

	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	

	protected final static Logger LOG = LoggerFactory.getLogger(CombinateBillService.class.getName());

	/**
	 * 
	 * <p>
	 * GXG合并接口
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午5:36:26
	 * @param bigCustomes
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ICombinateBillService#combine(java.util.ArrayList)
	 */
	@Override
	@Transactional
	public String combine(Date timeFrom, Date timeTo) {
		if(timeFrom==null){
			throw new BusinessException("开始时间为空","开始时间为空");
		}
		
		//如果结束时间为空，取当前时间
		if(timeTo==null){
			timeTo = new Date();
		}
		
		//时间比较
		if(compareDate(timeFrom, timeTo)){
			Log.warn("大客户数据合票：结束时间大于开始时间");
			 throw new BusinessException("大客户数据合票：结束时间大于开始时间","大客户数据合票：结束时间大于开始时间"); 
		}
		// 根据时间查询 待合票 列表
		ArrayList<BigCustomeEntity> bigCustomes = combinateBillDao.queryCombinateBigCustomeByTime(timeFrom,timeTo,0);
		
		// 判断传入列表是否为空
		if (CollectionUtils.isEmpty(bigCustomes)) {
			Log.warn("大客户数据合票：传入列表为空");
			// *TODO
			throw new BusinessException("查询不到合并明细","查询不到合并明细");
		}
		// 定义合并后的临时map列表
		Map<String, BigCustomeEntity> bccTempMap = new HashMap<String, BigCustomeEntity>();
		 
		Map<String,Object> validateoMap = new HashMap<String, Object>();
		for(BigCustomeEntity bigCustome : bigCustomes){
			// 判断是否为空
			if (bigCustome == null) {
				Log.warn("大客户数据合票：传入列表为空");
				throw new BusinessException("大客户数据合票：传入列表为空","大客户数据合票：传入列表为空");
			}

			// 客户到达门店编号
			String arrivestorenum = bigCustome.getArrivestorenum();

			if (StringUtils.isEmpty(arrivestorenum)) {
				Log.warn("大客户数据合票： 客户到达门店编号为空");
				// *TODO
				//throw new BusinessException("大客户数据合票： 客户到达门店编号为空","大客户数据合票： 客户到达门店编号为空");
				continue;
			}
			
			// 判断该 到达门店编号是否已经存在
			if (bccTempMap.get(arrivestorenum) == null) {
				//验证通过才分单号
				if(validate(bigCustome,validateoMap)){
					bigCustome.setWaybillno(generateWaybillno());
				}
				
				
				// 生成标签、次地返回应为0001
				bigCustome.setSerialno("0001");
				// 如果门店不存在
				// 放入合并后的临时map列表
				bccTempMap.put(arrivestorenum, bigCustome);
				
			} else {
				// 获取
				BigCustomeEntity bigCustomeCombinated = bccTempMap.get(arrivestorenum);

				// 生成流水号,将流水号更新到最新的一个，保证下一次标签号加1
				bigCustome.setSerialno(generateSerialno(bigCustomeCombinated.getSerialno()));

				bigCustome.setWaybillno(bigCustomeCombinated.getWaybillno());
				
				// 将本次数据合并到上一次中
				bigCustomeCombinated = mergeData(bigCustomeCombinated, bigCustome);
				//bigCustomeCombinated.setSerialno(bigCustome.getSerialno());
				
				// 将数据放入合并后的map列表
				bccTempMap.put(arrivestorenum, bigCustomeCombinated);
				
			}
			// 更新明细表 T_SRV_COMBINATEBILL_TOTAL
			iBigCustomeDao.updateWaybillnoSerialno(bigCustome);

		}
		
		StringBuffer errCnt = new StringBuffer();
		Map<String,Object> lableMap = new HashMap<String, Object>();
		// 遍历合并后的数据
		for (Map.Entry<String, BigCustomeEntity> bcc : bccTempMap.entrySet()) {
			BigCustomeEntity bigCustome = bcc.getValue();
			if (bigCustome == null || StringUtils.isBlank(bigCustome.getWaybillno())) {
				errCnt.append(bigCustome.getCustomerlablenum()+",");
			}
			WaybillPendingEntity waybillPending =new WaybillPendingEntity();
			
			CombinateBillEntity combinateBill =new CombinateBillEntity();
			
			//合并数据
			bigCustomeEntityToCombinateBillEntity(bigCustome,combinateBill,waybillPending,lableMap);

			// 添加到汇总表 T_SRV_COMBINATEBILL
			combinateBillDao.addCombinateBillEntity(combinateBill);

			waybillPending.setCreateTime(new Date());
			// 添加到待补录表 T_SRV_WAYBILL_PENDING
			if(StringUtils.isNotBlank(bigCustome.getWaybillno())){
				waybillPendingDao.insert(waybillPending);
			}
			
			
		}
		
		//合成标签信息
		
		
		if(StringUtils.isNotBlank(errCnt.toString())){
			// *TODO
			return "合并完成,有"+errCnt.toString()+"合并未成功，请联系相关部门处理";
		}else{
			// *TODO
			return "合并完成";
		}
		

	}
	
	
	private boolean validate(BigCustomeEntity bigCustome,Map validateoMap){
		
		
		String pickupOrgCode=null;
		Object object=null;
		String busin =bigCustome.getBusinessnetworkno();
		if(validateoMap.get(bigCustome.getArrivestorenum())!=null){
			 pickupOrgCode =(String) validateoMap.get(bigCustome.getArrivestorenum());
		}else{
			 pickupOrgCode =storeCodeSalesDeptService.querySalesDeptByStoreCode(bigCustome.getArrivestorenum());
			 validateoMap.put(bigCustome.getArrivestorenum(), pickupOrgCode);
		}
		if(validateoMap.get(busin+"u@u")!=null){
			object = validateoMap.get(busin+"u@u");
		}else{
			SaleDepartmentEntity dept=  saleDepartmentService.querySaleDepartmentByCode(busin);
			
			List<ProductEntity>  proucts =productService.getAllProductEntityByDeptCodeForCargoAndExpress(busin,WaybillConstants.TYPE_OF_CARGO,new Date());
		   if(dept!=null && CollectionUtils.isNotEmpty(proucts)){
			   object="you";
			   validateoMap.put(busin+"u@u", "you");
		   }
		}
		 
		// 生成运单号 , 根据到达门店编码进行匹配，匹配成功生成运单号，反之运单号为空
		if(StringUtils.isNotBlank(pickupOrgCode) && object!=null){
			return true;
		}
		return false;
		
	}

	/**
	 * 
	 * <p>
	 * 反合票
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午9:35:17
	 * @param bigCustomes
	 * @return
	 * @see
	 */
	@Transactional
	public String disCombine(Date timeFrom, Date timeTo) {

		if(timeFrom==null){
			return null;
		}
		
		//如果结束时间为空，取当前时间
		if(timeTo==null){
			timeTo = new Date();
		}
		 
		//时间比较
		if(compareDate(timeFrom, timeTo)){
			Log.warn("大客户数据合票：结束时间大于开始时间");
			throw new BusinessException("大客户数据合票：结束时间大于开始时间","大客户数据合票：结束时间大于开始时间");
		}
		ArrayList<BigCustomeEntity> bigCustomes = combinateBillDao.queryCombinateBigCustomeByTime(timeFrom,timeTo,1);
		if (CollectionUtils.isEmpty(bigCustomes)) {
			Log.warn("大客户数据反合票： 反合票数据为空");
			throw new BusinessException("大客户数据反合票： 反合票数据为空","大客户数据反合票： 反合票数据为空");
		}

		// 本次已处理过的运单 放在list中，供下面查询，已处理过的就不再次处理
		ArrayList<String> waybillnoes = new ArrayList<String>();

		// 通过明细查询是否已存在运单号
		for (int i = 0; i < bigCustomes.size(); i++) {
			BigCustomeEntity bigCustome = bigCustomes.get(i);
			String waybillno = bigCustome.getWaybillno();
			if (StringUtils.isEmpty(waybillno)) {
				// 如果为空，继续下一个
				continue;
			} else {
				// 标识该运单号是否被处理过
				boolean hasDispose = false;
				// 遍历处理过的运单列表
				for (int j = 0; j < waybillnoes.size(); j++) {
					//判断是否已处理
					if (waybillnoes.get(j).equals(waybillno)) {
						hasDispose = true;
						break;
					}
				}
				
				//如果没有处理过该运单
				if (!hasDispose) {
					WaybillEntity entity =waybillManagerService.queryWaybillBasicByNo(waybillno);
					//没有开单才能反合票
					if(entity==null){
						waybillPendingService.deleteByWaybillNo(waybillno);
						//删除明细表中的运单号和标签号
						iBigCustomeDao.updateWaybillnoSerialnoDisCombine(waybillno);
						combinateBillDao.disActiveCombinatebill(waybillno);
						//添加到处理过的List中
						waybillnoes.add(waybillno);
					}
					
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * * 进行合并
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月12日 下午2:30:45
	 * @param bigCustomeCombinated
	 *            之前合并的数据
	 * @param bigCustome
	 *            要合并的数据
	 * @return 之前合并的数据+要合并的数据
	 * @see
	 */
	private BigCustomeEntity mergeData(BigCustomeEntity bigCustomeCombinated, BigCustomeEntity bigCustome) {
		if (bigCustome == null || bigCustomeCombinated == null) {
			return null;
		}
		// ERP单号合并

		String erplogisticid = combinatErplogisticid(bigCustomeCombinated.getErplogisticid(),
				bigCustome.getErplogisticid());
		bigCustomeCombinated.setErplogisticid(erplogisticid);

		// 客户标签合并

		String customerlablenum = combinatCustomerlablenum(bigCustomeCombinated.getCustomerlablenum(),
				bigCustome.getCustomerlablenum());
		bigCustomeCombinated.setCustomerlablenum(customerlablenum);
		
		// 流水合并
		String serialno = combinatSerialno(bigCustomeCombinated.getSerialno(),
						bigCustome.getSerialno());
		bigCustomeCombinated.setSerialno(serialno);
		
		// 件数合并
		int a = bigCustomeCombinated.getTotalnumber()!=null?bigCustomeCombinated.getTotalnumber():0;
		int b =bigCustome.getTotalnumber()!=null?bigCustome.getTotalnumber():0;
		int totalNumber = combinateTotleNumber(a, b);
		bigCustomeCombinated.setTotalnumber(totalNumber);

		// 重量合并
		double c=bigCustomeCombinated.getWeight()!=null?bigCustomeCombinated.getWeight():0;
		double d=bigCustome.getWeight()!=null?bigCustome.getWeight():0;
		double weight = combinateWeight(c, d);
		bigCustomeCombinated.setWeight(weight);

		// 体积大小判断
		// float bigCustomeVolume = bigCustome.getVolume();
		// if (bigCustomeVolume > MAXVOLUME) {
		// // 体积超过规定MAXVOLUME
		// Log.warn("大客户数据合票：客户标签编号 " + bigCustome.getCustomerlablenum() +
		// "体积过大，体积=" + bigCustomeVolume);
		// return null;
		// }

		// 体积合并
		double E = bigCustomeCombinated.getVolume()!=null?bigCustomeCombinated.getVolume():0;
		double F = bigCustome.getVolume()!=null?bigCustome.getVolume():0;
		double volume = combinateVolume(E, F);
		bigCustomeCombinated.setVolume(volume);

		
		// 保价费合并
		double G = bigCustomeCombinated.getInsurancevalue()!=null?bigCustomeCombinated.getInsurancevalue():0; 
		double H = bigCustome.getInsurancevalue()!=null?bigCustome.getInsurancevalue():0;
		
		double volumeInsurancevalue = combinateInsurancevalue(G, H);
		bigCustomeCombinated.setInsurancevalue(volumeInsurancevalue);
		// 尺寸 格式 长*宽*高
		// String goodsSize = bigCustome.getGoodssize();
		// 将尺寸分离，转化为 长 宽 高 数组
		// float[] goodsSizexyz = goodsSizeSplit(goodsSize);

		// 尺寸合并
		String goodssize = combinateGoodssize(bigCustomeCombinated.getGoodssize(), bigCustome.getGoodssize());
		bigCustomeCombinated.setGoodssize(goodssize);

		return bigCustomeCombinated;
	}

	/**
	 * 
	 * <p>
	 * 尺寸合并 将1*2*1 合并成 1*2*1+1*2*3+3*4*3
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午12:26:43
	 * @param x
	 * @param y
	 * @return
	 * @see
	 */
	private String combinateGoodssize(String x, String y) {
		return combinatStringBySymbol(x, y, PLUS_SYMBOL);
	}

	/**
	 * 
	 * <p>
	 * 渠道订单号或erp单号 合并 ，每个逗号隔开
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午12:14:13
	 * @param x
	 * @param y
	 * @return
	 * @see
	 */
	private String combinatErplogisticid(String x, String y) {
		return combinatStringBySymbol(x, y, COMMA_SYMBOL);
	}

	/**
	 * 
	 * <p>
	 * 客户标签编号 合并 每个逗号隔开
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午12:19:29
	 * @param x
	 * @param y
	 * @return
	 * @see
	 */
	private String combinatCustomerlablenum(String x, String y) {
		return combinatStringBySymbol(x, y, COMMA_SYMBOL);
	}
	/**
	 * 
	 * <p>
	 * 流水号编号 合并 每个逗号隔开
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午12:19:29
	 * @param x
	 * @param y
	 * @return
	 * @see
	 */
	private String combinatSerialno(String x, String y) {
		return combinatStringBySymbol(x, y, COMMA_SYMBOL);
	}

	/**
	 * 
	 * <p>
	 * 件数合并 相加
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月12日 下午2:57:23
	 * @param i
	 * @param j
	 * @return
	 * @see
	 */
	private int combinateTotleNumber(int i, int j) {
		return i + j;
	}
	
	/**
	 * 
	 * <p>
	 * 件数合并 相加
	 * </p>
	 * 
	 * @author 245120
	 * @date 2015年9月21日 下午2:57:23
	 * @param i
	 * @param j
	 * @return
	 * @see
	 */
	private double combinateInsurancevalue(double i, double j) {
		return i + j;
	}

	/**
	 * 
	 * <p>
	 * 重量合并 相加
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月12日 下午2:56:56
	 * @param i
	 * @param j
	 * @return
	 * @see
	 */
	private double combinateWeight(double i, double j) {
		return i + j;
	}

	/**
	 * 
	 * <p>
	 * 体积合并 相加
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月12日 下午2:56:56
	 * @param i
	 * @param j
	 * @return
	 * @see
	 */
	private double combinateVolume(double i, double j) {
		return i + j;
	}

	/**
	 * 
	 * <p>
	 * 尺寸 格式 长*宽*高
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月12日 下午3:30:04
	 * @param goodsSize
	 * @see
	 */
	// private float[] goodsSizeSplit(String goodsSize) {
	// float goodsSizexyz[] = null;
	// // 如果尺寸不为空
	// if (StringUtils.isEmpty(goodsSize)) {
	// return goodsSizexyz;
	// }
	// // 长*宽*高 分离
	// String[] goodsSizeArray = goodsSize.split(GOODSSIZESPLIT);
	//
	// // 将长宽高转换为float数组
	// if (goodsSizeArray != null && goodsSizeArray.length == 3) {
	// String x = goodsSizeArray[0];
	// String y = goodsSizeArray[1];
	// String z = goodsSizeArray[2];
	// try {
	// goodsSizexyz = new float[3];
	// goodsSizexyz[0] = new Float(x);
	// goodsSizexyz[1] = new Float(y);
	// goodsSizexyz[2] = new Float(z);
	// } catch (NumberFormatException e) {
	// goodsSizexyz = null;
	// }
	//
	// }
	//
	// return goodsSizexyz;
	// }

	/**
	 * 
	 * <p>
	 * 拼接字符串，并将两个字符串中间加入特定字符
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午12:28:20
	 * @param x
	 * @param y
	 * @param symbol
	 *            特定字符
	 * @return
	 * @see
	 */
	private String combinatStringBySymbol(String x, String y, String symbol) {
		return x + symbol + y;
	}

	/**
	 * 
	 * <p>
	 * 生成标签
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午2:23:45
	 * @param lastSerialno
	 *            上一个标签号
	 * @return
	 * @see
	 */
	private String generateSerialno(String lastSerialno) {
		int serialnoInt;
		if (StringUtils.isEmpty(lastSerialno)) {
			// 如果标签为空，初始化为第1个
			serialnoInt = 1;
		} else {
			try {
				String[] lables = lastSerialno.split(",");
				String lno =lables[lables.length-1];
				// 标签号加1
				serialnoInt = Integer.parseInt(lno) + 1;
			} catch (Exception e) {
				// 初始化为第1个
				serialnoInt = 1;
			}
		}
		// 格式化为0001格式
		String serialno = new DecimalFormat(SERIALNO_FORMAT).format(serialnoInt);
		return serialno;
	}

	/**
	 * 
	 * <p>
	 * 生成运单号
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午2:49:00
	 * @return
	 * @see
	 */
	private String generateWaybillno() {
          
		int minNum = 1;
		int maxNum = 500000;
		DecimalFormat format = new DecimalFormat("0000000");
		Random random = new Random();
		int s = random.nextInt(maxNum - minNum + 1) + minNum;
		String waybillno = format.format(s);
		waybillno = "41" + waybillno;
		while (waybillManagerService.isWayBillExsits(waybillno)
				|| waybillManagerService.isWayBillPendingExsits(waybillno)) {
			s = random.nextInt(maxNum - minNum + 1) + minNum;
			waybillno = format.format(s);
			waybillno = "41" + waybillno;
		}
		
		return waybillno;
	}

	/**
	 * 
	 * <p>
	 * // 转换为合并订单
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午5:57:43
	 * @param bigCustome
	 * @return
	 * @see
	 */
	private void bigCustomeEntityToCombinateBillEntity(BigCustomeEntity bigCustome,CombinateBillEntity combinateBill,WaybillPendingEntity waybillPending,Map lableMap) {
		Date nowDate = new Date();
		// 运单号
		waybillPending.setWaybillNo(bigCustome.getWaybillno());
		// 订单付款方式
		waybillPending.setOrderPaidMethod(bigCustome.getPaytype());
		// 发货客户编码
		waybillPending.setDeliveryCustomerCode(bigCustome.getCustomercode());
		// 发货客户名称
		waybillPending.setDeliveryCustomerName(bigCustome.getSendername());
		// 发货客户手机
		waybillPending.setDeliveryCustomerMobilephone(bigCustome.getSendermobile());
		// 发货客户电话
		waybillPending.setDeliveryCustomerPhone(bigCustome.getSenderphone());
		// 发货客户联系人
		waybillPending.setDeliveryCustomerContact(bigCustome.getSendername());
		// 发货省份
		waybillPending.setDeliveryCustomerProvCode(bigCustome.getSenderprovincecode());
		// 发货市
		waybillPending.setDeliveryCustomerCityCode(bigCustome.getSendercitycode());
		// 发货区
		waybillPending.setDeliveryCustomerDistCode(bigCustome.getSendercountycode());
		// 发货具体地址
		waybillPending.setDeliveryCustomerAddress(bigCustome.getSenderstreet() + bigCustome.getSenderaddress());
		// 收货客户名称
		waybillPending.setReceiveCustomerName(bigCustome.getReceivername());
		// 收货客户手机
		waybillPending.setReceiveCustomerMobilephone(bigCustome.getReceivermobile());
		// 收货客户电话
		waybillPending.setReceiveCustomerPhone(bigCustome.getReceiverphone());
		// 收货客户联系人
		waybillPending.setReceiveCustomerContact(bigCustome.getReceivername());
		// 收货省份
		waybillPending.setReceiveCustomerProvCode(bigCustome.getReceiverprovincecode());
		// 收货市
		waybillPending.setReceiveCustomerCityCode(bigCustome.getReceivercitycode());
		// 收货区
		waybillPending.setReceiveCustomerDistCode(bigCustome.getReceivercountycode());
		// 收货乡镇(街道)
		waybillPending.setReceiveCustomerVillageCode(bigCustome.getReceiverstreetcode());
		// 收货具体地址
		waybillPending.setReceiveCustomerAddress(bigCustome.getReceiveraddress());
		
		// 提货方式
		String receiveMethodCode = bigCustome.getDeliverytype();
		waybillPending.setReceiveMethod(receiveMethodCode);
		// 提货方式名称
		waybillPending.setReceiveMethodName(DELIVER_UP_CN);

		// 出发网点
		waybillPending.setStartNode(bigCustome.getBusinessnetworkno());

		String pickupOrgCode =storeCodeSalesDeptService.querySalesDeptByStoreCode(bigCustome.getArrivestorenum());
		
		 //营业部信息
		 SaleDepartmentEntity dept=  saleDepartmentService.querySaleDepartmentByCode(pickupOrgCode);
		 
		// 提货网点
		waybillPending.setCustomerPickupOrgCode(pickupOrgCode);
	
		// 目的站
		waybillPending.setTargetOrgCode(pickupOrgCode);
		
		if(dept!=null){
			// 目的站名称 *
			waybillPending.setTargetOrgName(dept.getName());
		}
		
		if(WaybillConstants.DELIVER_UP.equals(receiveMethodCode) && dept!=null ){
			// 是否支持送货上门
			if (WaybillConstants.YES.equals(dept.getDelivery())) {
				waybillPending.setReceiveMethod(receiveMethodCode);
				// 提货方式名称
				waybillPending.setReceiveMethodName(DELIVER_UP_CN);
			}else{
				waybillPending.setReceiveMethod(WaybillConstants.SELF_PICKUP);
				// 提货方式名称
				waybillPending.setReceiveMethodName("自提");
			}
			 
		}
		// 是否上门接货
		waybillPending.setPickupToDoor(bigCustome.getVistreceive());

		// 货物名称
		waybillPending.setGoodsName(bigCustome.getCargoname());
		// 货物总件数
		waybillPending.setGoodsQtyTotal(bigCustome.getTotalnumber());
		if(bigCustome.getWeight()!=null){
		   // 货物总重量
		   waybillPending.setGoodsWeightTotal(BigDecimal.valueOf(bigCustome.getWeight()));
		}else{
		   // 货物总重量
		   waybillPending.setGoodsWeightTotal(BigDecimal.ZERO);
		}
		if(bigCustome.getVolume()!=null){
			// 货物总体积
			waybillPending.setGoodsVolumeTotal(BigDecimal.valueOf(bigCustome.getVolume()));
		}else{
			waybillPending.setGoodsVolumeTotal(BigDecimal.ZERO);
		}
		
		// 货物尺寸
		waybillPending.setGoodsSize(bigCustome.getGoodssize());
		// 货物包装
		waybillPending.setGoodsPackage(bigCustome.getPackageservice());
		// 保险声明价值是否合并 ，待确认
		if(bigCustome.getInsurancevalue()!=null){
			waybillPending.setInsuranceAmount(BigDecimal.valueOf(bigCustome.getInsurancevalue()));
		}else{
			waybillPending.setInsuranceAmount(BigDecimal.ZERO);
		}
		// 开单付款方式
		waybillPending.setPaidMethod(bigCustome.getPaytype());
		// 开单付款方式名称
		waybillPending.setPaidMethod(bigCustome.getPaytype());
		// 运单状态
		waybillPending.setActive(WaybillConstants.YES);
		Date date = new Date();
		// 创建时间
		waybillPending.setCreateDate(date);
		// 开单时间
		waybillPending.setBillTime(date);
		UserEntity user =  FossUserContext.getCurrentUser();
		if(user!=null&& user.getEmployee()!=null ){
			// 开单人
			waybillPending.setCreateUserCode(user.getEmployee().getEmpCode());
			// 更新人
			waybillPending.setModifyUserCode(user.getEmployee().getEmpCode());
		}else{
			// 开单人
			waybillPending.setCreateUserCode("084544");
			// 更新人
			waybillPending.setModifyUserCode("084544");
		}
		
		// 开单组织
		dept=  saleDepartmentService.querySaleDepartmentByCode(bigCustome.getBusinessnetworkno());
		if(dept!=null){
		    waybillPending.setCreateOrgCode(dept.getCode());
			waybillPending.setReceiveOrgCode(dept.getCode());
			waybillPending.setModifyOrgCode(dept.getCode());
		} 

	    List<ProductEntity>  proucts =productService.getAllProductEntityByDeptCodeForCargoAndExpress(bigCustome.getBusinessnetworkno(),WaybillConstants.TYPE_OF_CARGO,new Date());
	    if(CollectionUtils.isNotEmpty(proucts)){
	    	boolean ishash=true;
	    	for(ProductEntity p:proucts){
	    		if(p.getCode().equals(bigCustome.getTransporttype())){
	    			waybillPending.setProductId(p.getId());
	    			// 运输性质
	    			waybillPending.setProductCode(p.getCode());
	    			// 运输性质名称
	    			waybillPending.setProductName(p.getName());
	    			ishash=false;
	    			continue;
	    		}
	    	}
	    	if(ishash){
	    		waybillPending.setProductId(proucts.get(0).getId());
    			// 运输性质
    			waybillPending.setProductCode(proucts.get(0).getCode());
    			// 运输性质名称
    			waybillPending.setProductName(proucts.get(0).getName());
	    	}
	    	
	    } 
		// 待处理类型
		waybillPending.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);
		// 运输类型 汽运 写死
		waybillPending.setTransportType(WaybillConstants.TRANS_VEHICLE);
		// 储运事项
		waybillPending.setTransportationRemark(bigCustome.getRemark());
		// 短信标识
		waybillPending.setIsSMS(bigCustome.getSmsnotify());
		// 是否快递
		waybillPending.setIsExpress(WaybillConstants.NO);
		// 包装
		if(bigCustome.getPackageservice()!=null){
			String packa = bigCustome.getPackageservice();
			if(packa.equals("纸")){
				waybillPending.setPaperNum(1);
				waybillPending.setWoodNum(0);
				waybillPending.setFibreNum(0);
				waybillPending.setSalverNum(0);
				waybillPending.setMembraneNum(0);
			}else if(packa.equals("木")){
				waybillPending.setWoodNum(1);
				waybillPending.setPaperNum(0);
				waybillPending.setFibreNum(0);
				waybillPending.setSalverNum(0);
				waybillPending.setMembraneNum(0);
			}else if(packa.equals("纤")){
				waybillPending.setFibreNum(1);
				waybillPending.setPaperNum(0);
				waybillPending.setWoodNum(0);
				waybillPending.setSalverNum(0);
				waybillPending.setMembraneNum(0);
			}else if(packa.equals("托")){
				waybillPending.setSalverNum(1);
				waybillPending.setPaperNum(0);
				waybillPending.setWoodNum(0);
				waybillPending.setFibreNum(0);
				waybillPending.setMembraneNum(0);
			}else if(packa.equals("膜")){
				waybillPending.setMembraneNum(1);
				waybillPending.setPaperNum(0);
				waybillPending.setWoodNum(0);
				waybillPending.setFibreNum(0);
				waybillPending.setSalverNum(0);
			}
		}else{
			waybillPending.setPaperNum(0);
			waybillPending.setWoodNum(0);
			waybillPending.setFibreNum(0);
			waybillPending.setSalverNum(0);
			waybillPending.setMembraneNum(0);
		}


		// 发货客户名称
		combinateBill.setDeliveryCustomerName(bigCustome.getSendername());
		// 客户标签编号
		combinateBill.setCustomerLableNums(bigCustome.getCustomerlablenum());
		// 运单号
		combinateBill.setWaybillNo(bigCustome.getWaybillno());
		// ERP单号
		combinateBill.setErpOrderNo(bigCustome.getErplogisticid());
		// 流水号
		combinateBill.setSerialNo(bigCustome.getSerialno());
		// 订单状态
		combinateBill.setActive(WaybillConstants.YES);
		// 创建时间
		combinateBill.setCreateTime(nowDate);
		// 开单时间
		combinateBill.setBillTime(nowDate);
		// 开单人
		combinateBill.setCreateUserCode(waybillPending.getCreateUserCode());
		// 开单组织
		combinateBill.setCreateOrgCode(bigCustome.getBusinessnetworkno());
		// 始发城市名称
		combinateBill.setDepartmentCityName(bigCustome.getSendercity());
		// 收货地址
		combinateBill.setReceiveCustomerAddress(bigCustome.getReceiveraddress());
		// 收货门店编号
		combinateBill.setArriveStoreNUM(bigCustome.getArrivestorenum());
		combinateBill.setCustomerPickupOrgCode(waybillPending.getCustomerPickupOrgCode());
		// 收货人
		combinateBill.setReceiveCustomerContact(bigCustome.getReceivername());
		combinateBill.setReceiveMethod(bigCustome.getDeliverytype());
		
	    //运输性质
	    combinateBill.setProductCode(waybillPending.getProductCode());		 
	    // 运输性质名称
	    combinateBill.setProductName(waybillPending.getProductName());
		// 包装类型
	    combinateBill.setPackageService(bigCustome.getPackageservice());
		// 货物类型 留空
		combinateBill.setGoodsType("");
		// 付款方式
		combinateBill.setPaidMethod(bigCustome.getPaytype());
		// 总件数
		combinateBill.setPieces(bigCustome.getTotalnumber());
		// 重量
		combinateBill.setGoodsWeightTotal(waybillPending.getGoodsWeightTotal().doubleValue());
		// 体积
		combinateBill.setGoodsVolumeTotal(waybillPending.getGoodsVolumeTotal().doubleValue());

		combinateBill.setFoss_systime(bigCustome.getFoss_systime());
		combinateBill.setModifyTime(new Date());
		LabelPrintEntity lpe=null;
		if(lableMap.get(waybillPending.getWaybillNo())!=null){
			lpe =(LabelPrintEntity) lableMap.get(waybillPending.getWaybillNo());
		}else{
			lpe =labelPrintInfoService.findGxgLabelPrint(waybillPending);
			lableMap.put(waybillPending.getWaybillNo(), lpe);
		}
	    
	    combinateBill.setBarcode(lpe.getBarcode());
		combinateBill.setSend(lpe.getSend());
		combinateBill.setDestinationName(lpe.getDestinationName());
		//combinateBill.setUserCode(lpe.getUserCode());
		combinateBill.setDestTransCenterName(lpe.getDestTransCenterName());
		combinateBill.setDepartmentCityName(lpe.getDepartmentCityName());
		combinateBill.setDestStationNumber(lpe.getDestStationNumber());
		// lpd.setGoodsAreas(lpe.getGoodsAreas());
		String goodsAreas = null;
		for (GoodsAreaEntity ga : lpe.getGoodsAreas()) {
			if (goodsAreas == null)
				goodsAreas = ga.getTransferCode() + ":"
						+ ga.getGoodsAreaCode();
			else
				goodsAreas = goodsAreas + "|"
						+ ga.getTransferCode() + ":"
						+ ga.getGoodsAreaCode();
		}
		combinateBill.setGoodsAreas(goodsAreas);
		combinateBill.setDeliveryBigCustomer(lpe.getDeliveryBigCustomer());
		combinateBill.setReceiveBigCustomer(lpe.getReceiveBigCustomer());
		combinateBill.setIsExhibitCargo(lpe.getIsExhibitCargo());
	}

	
	/** 
	 * 比较两个日期之间的大小 
	 *  
	 * @param d1 
	 * @param d2 
	 * @return 前者大于后者返回true 反之false 
	 */  
	private boolean compareDate(Date d1, Date d2) {  
	    Calendar c1 = Calendar.getInstance();  
	    Calendar c2 = Calendar.getInstance();  
	    c1.setTime(d1);  
	    c2.setTime(d2);  
	  
	    int result = c1.compareTo(c2);  
	    if (result >= 0)  
	        return true;  
	    else  
	        return false;  
	}  
	public void setCombinateBillDao(ICombinateBillDao combinateBillDao) {
		this.combinateBillDao = combinateBillDao;
	}
	public void setiBigCustomeDao(IBigCustomeDao iBigCustomeDao) {
		this.iBigCustomeDao = iBigCustomeDao;
	}

	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	public IStoreCodeSalesDeptService getStoreCodeSalesDeptService() {
		return storeCodeSalesDeptService;
	}

	public void setStoreCodeSalesDeptService(
			IStoreCodeSalesDeptService storeCodeSalesDeptService) {
		this.storeCodeSalesDeptService = storeCodeSalesDeptService;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	


}
