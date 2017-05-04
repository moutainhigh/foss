package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ValueAddServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctPdaDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AllTimeNode;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ValueAddServiceEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WaybillExpressEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AccountStatementEntitys;

/** 
  * @ClassName KdBillingService 
  * @Description 快递快递开单接口
  * @author zhangzhenxian 
  * @date 2013-7-29 下午2:10:37 
*/ 
/**
 * @ClassName KdBillingService.java 
 * @Description 
 * @author 201638
 * @date 2015-3-19
 */
public class KdBillingService implements IBusinessService<Void, WaybillExpressEntity> {
    
    private Logger log = Logger.getLogger(getClass());

    private IAcctDao acctDao;
    
    private IAcctPdaDao acctPdaDao;
    
    private IPdaWaybillService pdaWaybillService;
    
    private UserCache userCache;
	
	private DeptCache deptCache;
	
	private IPdaPosManageService pdaPosManageService;
    
    public void setAcctDao(IAcctDao acctDao) {
        this.acctDao = acctDao;
    }

    public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
        this.pdaWaybillService = pdaWaybillService;
    }
    
    public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

    /**
     * 
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public WaybillExpressEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        //解析包体
        WaybillExpressEntity expBillingScan = JsonUtil.parseJsonToObject(WaybillExpressEntity.class, asyncMsg.getContent());
        //pda编号
        expBillingScan.setPdaCode(asyncMsg.getPdaCode());
        //部门编号
        expBillingScan.setDeptCode(asyncMsg.getDeptCode());
        //扫描类型
        expBillingScan.setScanType(asyncMsg.getOperType());
        //用户编号
        expBillingScan.setScanUser(asyncMsg.getUserCode());
        //异步测试改同步注释
//        expBillingScan.setUploadTime(asyncMsg.getUploadTime());
        expBillingScan.setUploadTime(new Date());
        return expBillingScan;
    }
    
    /**
     * 
     * @description 校验数据合法性 描述
     * @param expBillingScan
     * @throws PdaBusiException 
     * @created 2012-12-29 下午2:04:03
     */
    private void validate(AsyncMsg asyncMsg, WaybillExpressEntity expBillingScan) throws PdaBusiException {
        Argument.notNull(asyncMsg, "AsyncMsg");
        //验证pda编号
        Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //验证用户编号
        Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
        //验证部门编号
        Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
        
        // 判断快递开单实体
        Argument.notNull(expBillingScan, "expBillingScanEntity");
        Argument.hasText(expBillingScan.getId(), "expBillingScanEntity.id");
        // 订单号
        // Argument.hasText(expBillingScan.getOrderCode(), "expBillingScanEntity.orderCode");
        // 判断操作类型
        Argument.hasText(expBillingScan.getScanType(), "expBillingScanEntity.scanType");
        // 判断运单号
        Argument.hasText(expBillingScan.getWblCode(), "expBillingScanEntity.wblCode");
        // 判断出发部门
        Argument.hasText(expBillingScan.getDepartDeptCode(), "expBillingScanEntity.departDeptCode");
        // 判断目的地
        Argument.hasText(expBillingScan.getDestinationCode(), "expBillingScanEntity.destinationCode");
        // 判断提货方式
        Argument.hasText(expBillingScan.getTakeType(), "expBillingScanEntity.takeType");
        // 判断运输性质
        Argument.hasText(expBillingScan.getTransType(), "expBillingScanEntity.transType");
        // 判断件数
        Argument.isPositiveNum(expBillingScan.getPieces(), "expBillingScanEntity.pieces");
        // 判断重量
        //Argument.isPositiveNum(expBillingScan.getWeight(), "expBillingScanEntity.weight");
        // 判断体积
        //Argument.isPositiveNum(expBillingScan.getVolume(), "expBillingScanEntity.volume");
        // 判断代包装类型
        Argument.hasText(expBillingScan.getWrapType(), "expBillingScanEntity.wrapType");
        // 车牌号
       // Argument.hasText(expBillingScan.getTruckCode(), "expBillingScanEntity.truckCode");
        // 判断付款方式
        Argument.hasText(expBillingScan.getPaidType(), "expBillingScanEntity.paidType");
        // 司机编号
        Argument.hasText(expBillingScan.getScanUser(), "expBillingScanEntity.scanUser");
        // 扫描标识
        Argument.hasText(expBillingScan.getScanFlag(), "expBillingScanEntity.scanFlag");
        // 快递员code
        Argument.hasText(expBillingScan.getExpressEmpCode(), "expBillingScanEntity.expressEmpCode");
        // 快递员名称
        Argument.hasText(expBillingScan.getExpressEmpName(), "expBillingScanEntity.expressEmpName");
        // 收入部门code
        Argument.hasText(expBillingScan.getExpressOrgCode(), "expBillingScanEntity.expressOrgCode");
        // 收入部门名称
        Argument.hasText(expBillingScan.getExpressOrgName(), "expBillingScanEntity.expressOrgName");
      /* //快递工时节点
        Argument.hasText(expBillingScan.getTimeNode(), "expBillingScanEntity.getTimeNode");*/
        //是否内部带货  2013-11-06 新增
        //Argument.hasText( expBillingScan.getNeedDepponCustomerCode(), "expBillingScanEntity.needDepponCustomerCode");

        
        
        
    }
    //收入部门编号 :  刷卡部门编号
    private String expressOrgCode = "";
    //收入部门 : 刷卡部门
    private String expressOrgName = "";
    /**
     * 
     * @description 服务方法
     * @param asyncMsg
     * @param expBillingScan
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
     */
    @Override
    @Transactional
    public Void service(AsyncMsg asyncMsg, WaybillExpressEntity expBillingScan) throws PdaBusiException {
    	//如果expBillingScan.getIsReturnGoods()为空或者不是返货开单，校验validate，否则不校验
    	if(expBillingScan.getIsReturnGoods() == null || "N".equals(expBillingScan.getIsReturnGoods())){
    		this.validate(asyncMsg, expBillingScan);
    	}
    	//duhao-276198-foss-new开单时间
    	expBillingScan.setScanTime(new Date());
    	// 上传刷卡信息，如果是开单刷卡 314587
    	if("CD".equals(expBillingScan.getPaidType())){
    		try {
    			//收入部门编号
    			expressOrgCode = expBillingScan.getExpressOrgCode();
    			//收入部门
    			expressOrgName = expBillingScan.getExpressOrgName();
    			//FOSS接口开单
				transPosInfo(expBillingScan.getPosCardInfo(), asyncMsg);
			} catch (Exception e) {
				log.error(LogUtil.logFormat(e), e);
			}
    	}
    	
        expBillingScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
        //添加车牌号
        expBillingScan.setTruckCode("德"+expBillingScan.getExpressEmpCode());
        //若果前台传过来的timeNode不为空
        if(expBillingScan.getTimeNode() != null && !"".equals(expBillingScan.getTimeNode())){
        	log.debug("保存快递各工时节点");
//        	try{
        	   saveAllTimeNode(expBillingScan);
//        	}catch(Exception e){
//        		
//        	}
        }
        // 保存快递开单扫描数据
      
        //如果是内部带货 本地保存提货方式是在     原有提货方式+“_Y”      
        if(expBillingScan.getNeedDepponCustomerCode()!=null 
                && "Y".equals(expBillingScan.getNeedDepponCustomerCode())){    
            //临时保存 提货方式
            String takTpeTmp  = expBillingScan.getTakeType();
            //区分内部带货的提货方式 
            expBillingScan.setTakeType(expBillingScan.getTakeType()+"_Y");                
            // 保存快递开单信息
            log.debug("---保存快递开单数据开始(内部带货)---");            
            acctDao.saveBillingExpress(expBillingScan);
           
            //还原保存方式
            expBillingScan.setTakeType(takTpeTmp); 
            
        }else{
         
            // 保存快递开单信息
            log.debug("---保存快递开单数据开始(非内部带货)---");
            acctDao.saveBillingExpress(expBillingScan);
        }
        
        //如果发货人为空 或者 "" 直接赋值 "NULL"
        if(expBillingScan.getSendEmployeeCode()==null || 
                "".equals(expBillingScan.getSendEmployeeCode())){
            expBillingScan.setSendEmployeeCode("NULL");
        }
        
        log.debug("---保存快递开单扫描数据开始---");
        //保持快递扫描数据
        acctDao.saveScanBillingExpress(expBillingScan);
       
        log.debug("---保存快递开单分录数据开始---");
        
        if(expBillingScan.getMarketingCode()==null){
            expBillingScan.setMarketingCode("");
        }
        if(expBillingScan.getMarketingName()==null){
            expBillingScan.setMarketingName("");
        }
        
         //保持快递开单分录
         acctDao.saveBillingExpressDetail(expBillingScan);

        
        // 保存运单标签信息
        
        // 保存运单增值服务项
        List<ValueAddServiceEntity> appreciationService = expBillingScan.getAppreciationService();
        if (appreciationService != null && !appreciationService.isEmpty()) {
            log.debug("---保存快递运单增值服务项总条数：" + appreciationService.size() + "---");
            for (ValueAddServiceEntity valueAddService : appreciationService) {
                if (valueAddService != null) {
                    valueAddService.setId(UUIDUtils.getUUID());
                    valueAddService.setBillingID(expBillingScan.getId());                  
                    log.debug("---保存快递运单增值服务项，第"+ (appreciationService.indexOf(valueAddService) + 1 ) +"条开始---");
                    acctDao.saveBillVolAddService(valueAddService);
                }
            }
            log.debug("---保存快递运单增值服务项结束---");
        }
        
        WaybillExpressPdaDto waybillPdaDto = new WaybillExpressPdaDto();
        // 订单号
        waybillPdaDto.setOrderNo(expBillingScan.getOrderCode());
        // 运单号
        waybillPdaDto.setWaybillNo(expBillingScan.getWblCode());
        // 出发部门
        waybillPdaDto.setStartOrg(expBillingScan.getDepartDeptCode());
        // 提货方式
        waybillPdaDto.setReceiveMethod(expBillingScan.getTakeType());
        // 目的地编号
        waybillPdaDto.setTargetOrgCode(expBillingScan.getDestinationCode());
        // 运输性质
        waybillPdaDto.setProductCode(expBillingScan.getTransType());
        // 件数
        waybillPdaDto.setGoodsQty(expBillingScan.getPieces());
        // 重量（单位：千克）
        waybillPdaDto.setGoodsWeightTotal(BigDecimal.valueOf(expBillingScan.getWeight()));
        // 体积(单位：立方米)
        waybillPdaDto.setGoodsVolumeTotal(BigDecimal.valueOf(expBillingScan.getVolume()));
        // 代打木架体积(单位：立方米)
        waybillPdaDto.setWoodVolume(BigDecimal.valueOf(expBillingScan.getGallowsVolume()));
        // 代打木箱体积(单位：立方米)
        waybillPdaDto.setWoodBoxVolume(BigDecimal.valueOf(expBillingScan.getBoxVolume()));
        // 货物类型
        waybillPdaDto.setGoodsTypeCode(expBillingScan.getCrgType());
        // 车牌号
        waybillPdaDto.setLicensePlateNum(expBillingScan.getTruckCode());
        // 运费
        waybillPdaDto.setAmount(BigDecimal.valueOf(expBillingScan.getFreight()));
        // 实收运费
        waybillPdaDto.setActualFee(BigDecimal.valueOf(expBillingScan.getPaidFreight()));
        // 优惠券编号
        waybillPdaDto.setDiscountNo(expBillingScan.getCouponCode());
        // 优惠金额
        waybillPdaDto.setDiscountAmount(BigDecimal.valueOf(expBillingScan.getCouponMoney()));
        // 付款方式
        waybillPdaDto.setPaidMethod(expBillingScan.getPaidType());
        // 是否打木架
        waybillPdaDto.setIsWood(expBillingScan.getIsGallows());
        // 司机所在车队部门
        waybillPdaDto.setBillOrgCode(expBillingScan.getDeptCode());
        // 创建时间
        waybillPdaDto.setCreateTime(expBillingScan.getScanTime());
        // 创建人员
        waybillPdaDto.setCreateUserCode(expBillingScan.getScanUser());
        // 快递开单时间
        waybillPdaDto.setBillStart(expBillingScan.getScanTime());
        //用户编号
        waybillPdaDto.setBillUserNo(expBillingScan.getScanUser());        
        //返单类别
        waybillPdaDto.setReturnBillType(expBillingScan.getReturnBillType());
        
        //退款类型
        waybillPdaDto.setRefundType(expBillingScan.getRefundType());
        //快递员code 
        waybillPdaDto.setExpressEmpCode(expBillingScan.getExpressEmpCode());
        //快递员姓名
        waybillPdaDto.setExpressEmpName(expBillingScan.getExpressEmpName());
        //是否发送短信服务
        waybillPdaDto.setIsSMS(expBillingScan.getIsSMS());
        
        /**
         * FOSS那边这个字段是快递点部，ExpressOrgCode,ExpressOrgName快递点部有 FOSS计算获取。为了方便维护。
         * 和FOSS定义同名，意义不一样的字段 就给空
         * */
        //(收入部门)编码  
        // waybillPdaDto.setExpressOrgCode(expBillingScan.getExpressOrgCode());
        //(收入部门)名称
        // waybillPdaDto.setExpressOrgName(expBillingScan.getExpressOrgName());
        //PDA串号
        waybillPdaDto.setPdaSerial(expBillingScan.getPdaSerial());
        //银行交易流水号
        waybillPdaDto.setBankTradeSerail(expBillingScan.getBankTradeSerail());
        //发货快递员工号    2013-11-06 新增
        waybillPdaDto.setSendEmployeeCode(expBillingScan.getSendEmployeeCode());
        //是否内部带货         2013-11-06 新增
        waybillPdaDto.setNeedDepponCustomerCode(expBillingScan.getNeedDepponCustomerCode());
 
        
        
        //包装类型
       /* String[] wrapType = expBillingScan.getWrapType().split("\\|");
        waybillPdaDto.setPaper(Integer.parseInt(wrapType[0]));
        waybillPdaDto.setWood(Integer.parseInt(wrapType[1]));
        waybillPdaDto.setFibre(Integer.parseInt(wrapType[2]));
        waybillPdaDto.setSalver(Integer.parseInt(wrapType[3]));
        waybillPdaDto.setMembrane(Integer.parseInt(wrapType[4]));
        if(wrapType.length>5){
            waybillPdaDto.setOtherPackageType(wrapType[5]);
        }*/
        waybillPdaDto.setOtherPackageType(expBillingScan.getWrapType());
        //封装实体
        List<ValueAddServiceDto> valueAddServiceDtoList = new ArrayList<ValueAddServiceDto>();
        ValueAddServiceDto valueAddServiceDto = null;
        if (appreciationService != null && !appreciationService.isEmpty()) {
            for (ValueAddServiceEntity valueAddServiceVo : appreciationService) {
                valueAddServiceDto = new ValueAddServiceDto();
                valueAddServiceDto.setCode(valueAddServiceVo.getCode());
                valueAddServiceDto.setAmount(BigDecimal.valueOf(valueAddServiceVo.getAmount()));
                valueAddServiceDto.setSubType(valueAddServiceVo.getSubType());
//              valueAddServiceDto.set
                valueAddServiceDtoList.add(valueAddServiceDto);
            }
        }
        // 增值服务项
        waybillPdaDto.setValueAddServiceDtoList(valueAddServiceDtoList);
                
       //封装营销活动编码给foss          
      //  waybillPdaDto.setSpecialOffer(expBillingScan.getMarketingCode()); 
		
        //CRM 二期上传营销活动编码和名称
		MarkActivitiesQueryConditionDto markActivit = new MarkActivitiesQueryConditionDto();
		markActivit.setCode(expBillingScan.getMarketingCode());
		markActivit.setName(expBillingScan.getMarketingName());		
		waybillPdaDto.setActiveDto(markActivit);       
		//原单号
		waybillPdaDto.setOldWayBill(expBillingScan.getOldWayBill());
		//是否返货业务 Y/N
		waybillPdaDto.setIsReturnGoods(expBillingScan.getIsReturnGoods());
        //送货费
		waybillPdaDto.setDeliveryGoodsFee(BigDecimal.valueOf(expBillingScan.getAcctExpense()));
        //子母件新增返货方式 245955 
		waybillPdaDto.setReturnWay(expBillingScan.getReturnWay());
		
		
        log.debug("---调用FOSS提交快递订单接口开始---");
        //ResultDto resultDto= null;
        try {
            long startTime = System.currentTimeMillis();
            /**
             * FOSS接口第二个参数  ------ 收入部门
             * */
            pdaWaybillService.submitWaybillExpress(waybillPdaDto, expBillingScan.getExpressOrgCode());
            //log.debug("---调用FOSS提交订单接口返回结果："+resultDto.getCode()+"---");
            long endTime = System.currentTimeMillis();
            QueueMonitorInfo.addTotalFossTime(endTime-startTime);
            log.info("[asyncinfo]提交快递订单接口消耗时间:"+(endTime-startTime)+"ms");
        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        log.debug("---调用FOSS提交快递订单接口结束---");
        return null;
    }

    /**
	 * 保存快递开单各时间节点
	 */
	private void saveAllTimeNode(WaybillExpressEntity expBillingScan) {
		String timeNode = expBillingScan.getTimeNode();
		String[] timeNodes = timeNode.split("@");
		AllTimeNode allTimeNode = new AllTimeNode();
		/**运单号*/
		allTimeNode.setWaybillcode(expBillingScan.getWblCode());
		if(timeNodes.length == 13){
			/**订单接受时间*/
			allTimeNode.setOrderreceive(timeNodes[0]);
			/**查看明细时间*/
			allTimeNode.setSeedetail(timeNodes[1]);
			/**开单开始时间*/
			allTimeNode.setBillstart(timeNodes[2]);
			/**接货开单时间*/
			allTimeNode.setReceivebill(timeNodes[3]);
			/**开单结束时间*/
			allTimeNode.setBillend(timeNodes[4]);
			/**选择目的站开始时间*/
			allTimeNode.setSelectdestinationstart(timeNodes[5]);
			/**选择目的站结束时间*/
			allTimeNode.setSelectdestinationend(timeNodes[6]);
			/**增值服务开始时间*/
			allTimeNode.setValueaddstart(timeNodes[7]);
			/**增值服务结束时间*/
			allTimeNode.setValueaddend(timeNodes[8]);
			/**计算运费开始时间*/
			allTimeNode.setCalcarriagestart(timeNodes[9]);
			/**计算运费结束时间*/
			allTimeNode.setCalcarriageend(timeNodes[10]);
			/**刷卡支付开始时间*/
			allTimeNode.setCardstart(timeNodes[11]);
			/**刷卡支付结束时间*/
			if(" ".equals(timeNodes[12])){
				allTimeNode.setCardend(null);
			}else{
				allTimeNode.setCardend(timeNodes[12]);
			}
		}
		/**支付类型*/
		allTimeNode.setPaytype(expBillingScan.getPaidType());
		/**快递员对应部门*/
		UserEntity userEntity = userCache.getUser(expBillingScan.getScanUser());
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		allTimeNode.setDeptcode(deptEntity.getDeptCode());
		/**快递员工号*/
		allTimeNode.setEmpcode(expBillingScan.getScanUser());
		acctPdaDao.saveTimeNode(allTimeNode);		
	}
	
	// 封装参数
		private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg,AccountStatementEntitys accountStatement) {
			List<PosCardEntity> dto = new ArrayList<PosCardEntity>();
			PosCardEntity posCardEntity = new PosCardEntity();
		
			List<PosCardDetailEntity> details = new ArrayList<PosCardDetailEntity>();
			
			PosCardDetailEntity detail = new PosCardDetailEntity();
		
			// 所属模块  固定位快递
			posCardEntity.setBelongModule("快递");
			// 交易流水号
			posCardEntity.setTradeSerialNo(accountStatement.getTradeSerialNo());
			// 流水号金额
			posCardEntity.setSerialAmount(accountStatement.getSerialAmount());
			//刷卡部门
			posCardEntity.setCardDeptName(expressOrgName);
			//刷卡部门编号
			posCardEntity.setCardDeptCode(expressOrgCode);
			// 刷卡时时间
			posCardEntity.setCardTime(accountStatement.getOperateTime());
			// 创建人名称
			posCardEntity.setCreateUser(accountStatement.getCreateUserName());
			// 创建人编码
			posCardEntity.setCreateUserCode(accountStatement.getCreateUserCode());
			//是否开单 true 开单 false 签收
			posCardEntity.setIsbilling("true");
			
			if ("COURIER".equals(asyncMsg.getUserType())) {
				// 是否司机
				posCardEntity.setIsDriver("false"); 
			} else {
				// 是否司机
				posCardEntity.setIsDriver("true");
			}
			// 是否快递
			posCardEntity.setIsKd("true");
			//明细集合
			posCardEntity.setPosCardDetailEntitys(accountStatement.getPosCardDetailEntitys());
			
			for (int i = 0; i < accountStatement.getPosCardDetailEntitys().size(); i++) {
				detail = new PosCardDetailEntity();
				// 单据类型 固定位  运单
				detail.setInvoiceType("运单");
				//单据号
				detail.setInvoiceNo(accountStatement.getPosCardDetailEntitys().get(i).getInvoiceNo());
				//总金额
				detail.setAmount(accountStatement.getPosCardDetailEntitys().get(i).getAmount());
				//交易流水号
				detail.setTradeSerialNo(accountStatement.getPosCardDetailEntitys().get(i).getTradeSerialNo());
				//每次刷卡明细 数据 都加到 POS刷卡明细
				details.add(detail);
			}
			
			posCardEntity.setPosCardDetailEntitys(details);
			dto.add(posCardEntity);
			return dto;
		}
	
	private void transPosInfo(AccountStatementEntitys account,AsyncMsg asyncMsg){
		try {
			List<PosCardEntity> posList = wrapPosCardEntitys(asyncMsg, account);
			//FOSS接口
			pdaPosManageService.insertPosCardData(posList);
		} catch (BusinessException e) {
			account.setErrorCause(e.getErrorCode());
			acctPdaDao.saveNCIPaymentCard(account);
        } catch (Exception e) {
        	account.setErrorCause(LogUtil.logFormat(e));
        	acctPdaDao.saveNCIPaymentCard(account);
		} 
	}

	/**
     * 业务类型
     */
    @Override
    public String getOperType() {
        return AcceptConstant.OPER_TYPE_ACCT_KD_BILLING.VERSION;
    }

    /**
     * @description 异步
     * @return true
     * @author 201638
     * @date 2015-3-19 
     */
    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * 注入PDA的DAO
     */
	public void setAcctPdaDao(IAcctPdaDao acctPdaDao) {
		this.acctPdaDao = acctPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

}
