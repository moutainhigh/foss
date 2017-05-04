package com.deppon.foss.module.transfer.stock.server.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.stock.server.service.impl.StockService;
import com.deppon.foss.module.transfer.util.SpringTestHelper;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;


public class StockServiceTest{
	
	private JdbcTemplate jdbcTemp;
	private IStockService stockService;
	
	private IPDAStockService pdaStockService;
	
	@Before
    public void setUp() throws Exception {
		jdbcTemp = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
		stockService = (IStockService) SpringTestHelper.get().getBeanByClass(StockService.class);
		//pdaStockService = (IPDAStockService) SpringTestHelper.get().getBeanByClass(PDAStockService.class);
	}

	
    @After
    public void tearDown() throws Exception {
    }
	@Test
    public void testInStockPC(){
		
		
		/** 页面入库特殊货区*/
		
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		
		/** 开单入库*/
		inOutStockEntity.setWaybillNO("222222250");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("W011305090304");
		inOutStockEntity.setNextOrgCode("W040002060401");
		inOutStockEntity.setInOutStockType(StockConstants.WHOLE_VEHICLE_IN_STOCK_TYPE);
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setOperatorName("王五");
		
		stockService.inStockPC(inOutStockEntity);
		
		/*DecimalFormat df = new DecimalFormat();
		String style = ExceptionGoodsConstants.SERIAL_NO_STYLE;
		df.applyPattern(style);
		for(int i=11;i<1001;i++){
			inOutStockEntity.setWaybillNO("00006650");
			inOutStockEntity.setSerialNO(df.format(i));
			inOutStockEntity.setOrgCode("W040002060401");
			inOutStockEntity.setNextOrgCode("W060002070701");
			inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
			inOutStockEntity.setOperatorCode("test");
			inOutStockEntity.setOperatorName("test");
			stockService.inStockPC(inOutStockEntity);
		}*/
		
    }
	/**
	 * 界面单票入库
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-1 下午1:58:03
	 *//*
	@Test
	public void testInStockSerialNOs(){
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000101");
		inOutStockEntity.setOrgCode("W01060302");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setOperatorName("王五");
		inOutStockEntity.setInOutStockType(StockConstants.LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		//inOutStockEntity.setGoodsAreaCode("001");
		String serialNOs="0001,0002";
		stockService.inStockSerialNOs(inOutStockEntity, serialNOs, StockConstants.NOT_CONFIRM,StockConstants.NOT_CONFIRM);
	}
	
	@Test
	public void testOutStockPC(){
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000102");
		inOutStockEntity.setSerialNO("0002");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setOperatorName("王五");
		inOutStockEntity.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
		stockService.outStockPC(inOutStockEntity);
	}
	@Test
	public void testUniqueStock(){
		StockEntity stock = stockService.queryUniqueStock("2000100", "0001");
		Assert.assertEquals("2000100", stock.getWaybillNO());
	}
    
	
    @Test
	public void testQueryAbandonedGoodsInstockTime(){
		
		
		//List<WaybillStockEntity> waybillStockList = wbStockDao.queryWaybillStock(waybillStock);
		
		//Date date = stockService.queryAbandonedGoodsInstockTime("2012003", "888");
		
		
		//Assert.assertEquals(12, date.getMonth() + 1);
		//Assert.assertEquals(23, date.getDate());
		
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2012003");
		inOutStockEntity.setSerialNO("000001");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setOperatorName("王五");
		inOutStockEntity.setInOutStockType(StockConstants.AFTER_PACKAGE_OLD_GOODS_OUT_STOCK_TYPE);
		
		stockService.outStockPC(inOutStockEntity);
	}
   @Test
   public void testIsNotExistStock(){
	   boolean flag = stockService.isNotExistStock("888", "2000102", "0001","004");
	   Assert.assertEquals(false, flag);
   }
   *//**
	 * PDA入库代包装货区
	 *//*
   @Test 
   public void testInStockPackageAreaPDA(){
	   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000100");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("000000");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.inStockPackageAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   //PDA出库代包装货区
   @Test
   public void testOutStockPackageAreaPDA(){
	   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000102");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.outStockPackageAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   //PDA入库贵重物品货区
   @Test   
   public void testInStockValuableAreaPDA(){
	    InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000100");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("CD00001");
		inOutStockEntity.setOperatorCode("221100");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.inStockValuableAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   *//**
	 * PDA出库贵重物品货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 *//*
   
   @Test   
   public void testOutStockValuableAreaPDA(){
	   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000100");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("CD00001");
		inOutStockEntity.setOperatorCode("221100");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.outStockValuableAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   
   *//**
	 *PDA入库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:53
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockExceptionAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 *//*
   @Test   
   public void testInStockExceptionAreaPDA() {
	   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000102");
		inOutStockEntity.setSerialNO("0003");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.inStockExceptionAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   
   *//**
	 * PDA出库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号  
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:11:57
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockExceptionAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 *//*
   @Test   
   public void testOutStockExceptionAreaPDA() {
	    InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000102");
		inOutStockEntity.setSerialNO("0003");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		int i = pdaStockService.outStockExceptionAreaPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   
   *//**
	 * PDA单件入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.inOutStockType 入库类型  参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:12:07
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#singleInStockPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 *//*
   @Test   
   public void testSingleInStockPDA() {
	    InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("2000102");
		inOutStockEntity.setSerialNO("0003");
		inOutStockEntity.setOrgCode("888");
		inOutStockEntity.setOperatorCode("097457");
		inOutStockEntity.setScanTime(new Date());
		inOutStockEntity.setPdaDeviceNO("1314");
		//inOutStockEntity.setInOutStockType(StockConstants.LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		//inOutStockEntity.setInOutStockType(StockConstants.AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE);
		//inOutStockEntity.setInOutStockType(StockConstants.PARTIALLINE_RETURN_IN_STOCK_TYPE);
		//inOutStockEntity.setInOutStockType(StockConstants.SEND_RETURN_IN_STOCK_TYPE);
		inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_SIGN_IN_STOCK_TYPE);
		int i = pdaStockService.singleInStockPDA(inOutStockEntity);
		Assert.assertEquals(1, i);
   }
   @Test  
   public void testIsOutStock(){
	   
	   SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date createBillTime = null;
		try {
			createBillTime = sf.parse("2012-11-10 12:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(false, stockService.isOutStock("2000100", new Date())) ;
   }
   
   @Test
   public void testAdjustGoodsAreaStock(){
	  stockService.adjustGoodsAreaStock("2000102", "0001", "888", "000000", "gaga");
   }
   
   @Test
   public void testAdjustTogetherTruckStock(){
	   List<String> waybillNOList = new ArrayList<String>();
	   waybillNOList.add("2000100");
	   waybillNOList.add("2000102");
	   
	   List<String> originalGoodsAreaCodeList = new ArrayList<String>();
	   originalGoodsAreaCodeList.add("004");
	   originalGoodsAreaCodeList.add("005");
	   
	   stockService.adjustTogetherTruckStock(waybillNOList, originalGoodsAreaCodeList, "007", "888");
   }
   
   @Test
   public void testQuerySpecialList(){
	   List<BaseDataDictDto> list = stockService.querySpecialAreaList("888");
	   for(BaseDataDictDto dto : list){
		   System.out.println(dto.getValueCode() + "----" + dto.getValueName());
	   }
   }
   @Test
   public void testQueryStockOrgCode(){
	   //System.out.println(stockService.queryStockOrgCode("GS00002"));
	   
	   
   }
   
   @Test
   public void testQueryStockGoodsQty(){
	   System.out.println(stockService.queryStockGoodsQty(null));
   }
   
   @Test
   public void testQueryWaybillStockStatistics(){
	   WaybillStockEntity waybillStock = new WaybillStockEntity();
       waybillStock.setOrgCode("GS000");
       WaybillStockStatisticsDto waybillStockStatisticsDto = stockService.queryWaybillStockStatistics(waybillStock, null, null);
   }
   
   
   @Test
   public void testQueryInStockInfo(){
	   Date createBillTime = DateUtils.convert("2012-10-01 12:00:00", "yyyy-MM-dd HH:mm:ss");
	   List<InOutStockEntity> inStock = stockService.queryInStockInfo("50001676", "0001", "W040002060401", createBillTime);
	   inStock.get(0);
   }*/
   
   @Test
   public void testAddStock(){
	   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		
	   DecimalFormat df = new DecimalFormat();
		String style = ExceptionGoodsConstants.SERIAL_NO_STYLE;
		df.applyPattern(style);
		for(int i=7;i<3001;i++){
			inOutStockEntity.setWaybillNO("795489789");
			inOutStockEntity.setSerialNO(df.format(i));
			//inOutStockEntity.setGoodsAreaCode("003");
			inOutStockEntity.setOrgCode("GS00002");
			inOutStockEntity.setNextOrgCode("WHOLE_GOODS_NEXT_ORG");
			inOutStockEntity.setPlanStartTime(new Date());
			inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
			inOutStockEntity.setOperatorCode("test");
			inOutStockEntity.setOperatorName("test");
			inOutStockEntity.setDeviceType("PC");
			inOutStockEntity.setScanTime(new Date());
			stockService.addStock(inOutStockEntity);
		}
   }
   
   
   
   @Test
   public void testOutStockBatchPC(){
	   DecimalFormat df = new DecimalFormat();
	   String style = ExceptionGoodsConstants.SERIAL_NO_STYLE;
	   df.applyPattern(style);
	   List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
	   for(int i=2;i<3;i++){
		   InOutStockEntity inOutStockEntity = new InOutStockEntity();
		   inOutStockEntity.setId(UUIDUtils.getUUID());
		   inOutStockEntity.setWaybillNO("00006651");
		   inOutStockEntity.setSerialNO(df.format(i));
		   inOutStockEntity.setOrgCode("W040002060401");
		   inOutStockEntity.setDeviceType("PC");
		   inOutStockEntity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
		   inOutStockEntity.setOperatorCode("Test");
		   inOutStockEntity.setOperatorName("Test");
		   inOutStockEntity.setInOutStockBillNO("222222");
		   inOutStockEntity.setScanTime(new Date());
		   inOutStockList.add(inOutStockEntity);
	   }
		Date date1 = new Date();   
		try{
			stockService.outStockBatchPC(inOutStockList);
		}catch(StockException e){
			e.printStackTrace();
		}
		Date date2 = new Date();
		
		System.out.println(DateUtils.convert(date1, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtils.convert(date2, "yyyy-MM-dd HH:mm:ss"));
   }
   
   @Test
   public void testInStockCreateBill(){
	   DecimalFormat df = new DecimalFormat();
	   String style = ExceptionGoodsConstants.SERIAL_NO_STYLE;
	   df.applyPattern(style);
	   List<InOutStockEntity> list = new ArrayList<InOutStockEntity>();
	   for(int i=1;i<3;i++){
		    InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setWaybillNO("00006651");
			inOutStockEntity.setSerialNO(df.format(i));
			inOutStockEntity.setGoodsAreaCode("003");
			inOutStockEntity.setOrgCode("W040002060401");
			inOutStockEntity.setNextOrgCode("W060002070701");
			inOutStockEntity.setPlanStartTime(new Date());
			inOutStockEntity.setInOutStockType(StockConstants.CREATE_WAYBILL_IN_STOCK_TYPE);
			inOutStockEntity.setOperatorCode("test");
			inOutStockEntity.setOperatorName("test");
			inOutStockEntity.setDeviceType("PC");
			inOutStockEntity.setScanTime(new Date());
			list.add(inOutStockEntity);
		}
	   stockService.inStockCreateBill(list);
   }
   
}
