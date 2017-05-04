package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AcctOrderEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.DispatchOrderEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.DispatchOrderEntityList;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryAcctOrderEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file AcctOrderService.java 
 * @description 接收接货订单服务类
 * @author ChenLiang
 * @created 2012-12-29 上午10:17:21    
 * @version 1.0
 */
public class KdAcctOrderService implements IBusinessService<List<AcctOrderEntity>, QryAcctOrderEntity> {

    private Logger log = Logger.getLogger(getClass());
    
    private IPDAToOMSService pdaToOMSService;
    
	public void setPdaToOMSService(IPDAToOMSService pdaToOMSService) {
		this.pdaToOMSService = pdaToOMSService;
	}

	/**
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public QryAcctOrderEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        //解析内容
        QryAcctOrderEntity acctOrder = JsonUtil.parseJsonToObject(QryAcctOrderEntity.class, asyncMsg.getContent());
        //用户编号
        acctOrder.setUserCode(asyncMsg.getUserCode());
        //pda编号
        acctOrder.setPdaCode(asyncMsg.getPdaCode());
        return acctOrder;
    }
    
    /**
     * @description 校验数据合法性
     * @param qryActOrder
     * @throws PdaBusiException 
     * @created 2012-12-26 下午9:25:05
     */
    private void validate(AsyncMsg asyncMsg, QryAcctOrderEntity qryActOrder) throws PdaBusiException {
        // pdaInfo信息校验
        Argument.notNull(asyncMsg, "AsyncMsg");
        //验证pda编号
        Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //验证用户编号
        Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
        //验证部门编号
        Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
        // 包体信息校验
        Argument.notNull(qryActOrder, "QryAcctOrderEntity");
        //验证车牌号
       // Argument.hasText(qryActOrder.getTruckCode(), "QryAcctOrderEntity.truckCode");
    }

    /**
     * @description 服务方法
     * @param asyncMsg
     * @param qryActOrder
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<AcctOrderEntity> service(AsyncMsg asyncMsg, QryAcctOrderEntity qryActOrder) throws PdaBusiException {
        this.validate(asyncMsg, qryActOrder);
        log.debug("---调用OMS接货接收订单接口开始---");
        List<DispatchOrderEntity>  dispatchOrderEntity=new ArrayList<DispatchOrderEntity>();
        try {
          //根据用户编号,和车牌号去OMS进行查询数据
          String responseStr = pdaToOMSService.queryAcctOrder(qryActOrder); 
          Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("acctResponseDetail", DispatchOrderEntity.class);
          DispatchOrderEntityList response = JSON.parseObject(responseStr, DispatchOrderEntityList.class);
          if(null != response.getAcctResponseDetail()){
        	  dispatchOrderEntity = response.getAcctResponseDetail();
          }
        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        log.debug("---调用OMS接货接收订单接口结束---");
        List<AcctOrderEntity> orderList = new ArrayList<AcctOrderEntity>();
        if (dispatchOrderEntity != null && !dispatchOrderEntity.isEmpty()) {
            AcctOrderEntity acctOrder = null;
            DispatchOrderEntity entity = null;
            for (int i = 0; i < dispatchOrderEntity.size(); i++) {
                acctOrder = new AcctOrderEntity();
                entity = dispatchOrderEntity.get(i);
                // 订单编号
                acctOrder.setOrderCode(entity.getOrderCode());
                // 订单状态  276198-duhao-增加订单状态201608031812
                acctOrder.setOrderStatus(entity.getOrderStatus());
                // 订单状态  276198-duhao-优先取货201608031812
                acctOrder.setIsThePriority(entity.getIsThePriority());
                // 订单状态  276198-duhao-异地调货201608031812
                acctOrder.setIsOffSiteTransfer(entity.getIsOffSiteTransfer());
                // 运单类型 314587-liulipeng
                acctOrder.setWaybillType(entity.getWaybillType());
                // 货物名称 author:huangkaibing  20161009
                acctOrder.setGoodsName(entity.getGoodsName());
            	// 收货联系人电话 author:huangkaibing  20161009
            	acctOrder.setReceivePhone(entity.getReceivePhone());
            	// 发货人电话电话 author:huangkaibing  20161009
            	acctOrder.setTel(entity.getTel());
                // 运单号
                acctOrder.setWaybillNo(entity.getWaybillNo());
                // 营业部编号
                acctOrder.setDeptCode(entity.getDeptCode());
                // 提货方式
                acctOrder.setTakeType(entity.getTakeType());
                // 运输性质
                acctOrder.setTransType(entity.getTransType());
                // 提货网点编号
                acctOrder.setCustomerPickupOrgCode(entity.getCustomerPickupOrgCode());
                // 接货省
                acctOrder.setPickupProvince(entity.getPickupProvince());
                // 接货市
                acctOrder.setPickupCity(entity.getPickupCity());
                // 接货区县
                acctOrder.setPickupCounty(entity.getPickupCounty());
                // 接货地址
                acctOrder.setAcctAddress(StringUtils.convert(entity.getAcctAddress()));
                // 客户电话
                String cusTel="";
                
                /***
                 * 客户手机号码和电话显示4中情况
                 *      1、手机
                 *      2、电话
                 *      3、手机/电话
                 *      4、
                 */                         
                if(entity.getMobile()==null
                		||"".equals(entity.getMobile().trim())){
                 //手机号码为空	
                	//电话不为空  情况2
                	if(entity.getTel()!=null
                    		&&!"".equals(entity.getTel().trim())){
                		cusTel=StringUtils.convert(entity.getTel());
                	} 
                	//手机和电话都为空，情况 4
                }else if(entity.getTel()==null
                		||"".equals(entity.getTel().trim())){
                //手机不为空；电话为空   情况 1	
                	cusTel= StringUtils.convert(entity.getMobile());
                }else{
                //手机不为空，电话不为空 情况 3	
                	cusTel=  StringUtils.convert(entity.getMobile())+"/"
                            +StringUtils.convert(entity.getTel());
                }
                
                
                acctOrder.setCustomerPhone(cusTel);
                // 客户姓名
                acctOrder.setCustomerName(StringUtils.convert(entity.getCustomerName()));
                // 最早接货时间
                acctOrder.setFirstAcctTime(entity.getFirstAcctTime());
                // 最晚接货时间
                acctOrder.setLastAcctTime(entity.getLastAcctTime());
                // 体积
                acctOrder.setVolume(entity.getVolume());
                // 重量
                acctOrder.setWeight(entity.getWeight());
                // 件数
                acctOrder.setPieces(entity.getPieces());
                // 包装类型
                acctOrder.setWrapType(StringUtils.convert(entity.getWrapType()));
                // 营业部联系电话
                acctOrder.setDeptPhone(StringUtils.convert(entity.getDeptPhone()));
                // 下单时间
                acctOrder.setOrderTime(entity.getOrderTime());
                // 订单类型
                acctOrder.setOrderType(entity.getOrderType());
                // 货物类型
                acctOrder.setCrgType(entity.getCrgType());
                // 请车专员姓名
                acctOrder.setCarCommissionerName(entity.getCarCommissionerName());
                // 受理人
                acctOrder.setAssignees(entity.getAssignees());
                // 车型需求
                acctOrder.setModelsDemand(entity.getModelsDemand());
                // 营业部联系人
                acctOrder.setDeptContactName(entity.getDeptContactName());
                // 备注
                acctOrder.setRemark(StringUtils.convert(entity.getRemark()));
                //收入部门编码
                acctOrder.setReceiveOrgCode(entity.getReceiveOrgCode());
                //收入部门名称
                acctOrder.setReceiveOrgName(entity.getReceiveOrgName());
                //订单渠道,QQ
                acctOrder.setChannelCode(entity.getChannelCode());
                //是否采集GPS地址
                acctOrder.setIsCollectGps(entity.getIsCollectGps());
               //收货地址（派送到货地址）
                acctOrder.setDeliverAddress(StringUtils.convert(entity.getDeliverAddress()));
               //转发订单到司机工号
                acctOrder.setFromDriverCode(entity.getFromDriverCode());
                //转发订单到司机姓名
                acctOrder.setFromDriverName(entity.getFromDriverName());
                
                //author:wfy
                //代收货款类型
                acctOrder.setReciveLoanType(entity.getReciveLoanType());
                //代收货款金额
                acctOrder.setReviceMoneyAmount(entity.getReviceMoneyAmount()==null?"0":entity.getReviceMoneyAmount().toString());
                //保价声明价值
                acctOrder.setInsuredAmount(entity.getInsuredAmount()==null?"0":entity.getInsuredAmount().toString());
                //优惠券编码
                acctOrder.setCouponNumber(entity.getCouponNumber());
                //付款方式
                acctOrder.setPayType(entity.getPayType());
                //特安客户
                acctOrder.setTaCustomUpLimit(entity.getTaCustomUpLimit());
              //author:245960 Date:2015-06-04
                //采购单号
                acctOrder.setPurchaseCode(entity.getPurchaseCode());
                //渠道单号
                acctOrder.setChannelNumber(entity.getChannelNumber());
                //收货人:
                acctOrder.setReceiver(entity.getReceiver());
                //收货人联系方式:
                acctOrder.setReceiverPhone(entity.getReceiverPhone());
                //裹裹编码  245955 2015-12-22
                acctOrder.setServerType(entity.getServerType());
                //收货具体省份 268974 2015-01-07
                acctOrder.setConsigneeProvince(entity.getConsigneeProvince());
                //收货具体市份
                acctOrder.setConsigneeCity(entity.getConsigneeCity());
                //收货具体区份
                acctOrder.setConsigneeCounty(entity.getConsigneeCounty());
                //时间类型转换 2016-05-09 13:25:07 314587
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String inflowTime = "";
                if(entity.getInflowTime()!=null){
                	inflowTime = df.format(entity.getInflowTime());
                }else{
                	log.error("************“裹裹-流入时间为空！”************");
                }
                //流入时间
                acctOrder.setInflowTime(inflowTime);
                if(entity.getPickupManId()!=null){
                	 //取件员ID
                    acctOrder.setPickupManId(entity.getPickupManId());
                }else{
                	log.error("************“裹裹-取件员ID为空！”************");
                	acctOrder.setPickupManId("");
                }
                if(entity.getGgOrderType()!=null){
            	    //裹裹订单类型
            	    acctOrder.setGgOrderType(entity.getGgOrderType());
                }else{
                	log.error("************“裹裹-订单类型为空！”************");
            	    acctOrder.setGgOrderType("");
                }
                if(entity.getGotInTime()!=null){
            	   //上门揽件时效要求（120分钟、30分钟）
                   acctOrder.setGotInTime(entity.getGotInTime().toString()); 
                }else{
                	log.error("************“裹裹-上门揽件时效要求为空！”************");
                	acctOrder.setGotInTime("");
                }
                
                orderList.add(acctOrder);
            }
            log.debug("---返回订单信息成功---"); 
        }
        return orderList;
    }

    /**
     * 业务类型
     */
    @Override
    public String getOperType() {	
        return AcceptConstant.OPER_TYPE_ACCT_KD_ORDER_QRY.VERSION;
    }

    /**
     * 同步还是异步
     */
    @Override
    public boolean isAsync() {
        return false;
    }
    
}
