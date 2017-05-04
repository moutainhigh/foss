package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.GetUnldPalletBingingReqEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.GetUnldPalletBingingResEntity;
/**
 * 
 * @description 获取托盘绑定任务接口
 * @version 1.0
 * @author wenwuneng 
 * @update 2013-8-12 下午6:12:57
 */
public class GetUnldPalletBoundService implements IBusinessService<List<GetUnldPalletBingingResEntity>, GetUnldPalletBingingReqEntity>{
	private IPDATrayScanService pdaTrayScanService;
	private static final Logger logger = Logger.getLogger(GetUnldPalletBoundService.class);
	@Override
	public GetUnldPalletBingingReqEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetUnldPalletBingingReqEntity model = JsonUtil.parseJsonToObject(GetUnldPalletBingingReqEntity.class, asyncMsg.getContent());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<GetUnldPalletBingingResEntity> service(AsyncMsg asyncMsg, GetUnldPalletBingingReqEntity param)
			throws PdaBusiException {
		try {
			//1校验参数
			this.validate(asyncMsg, param);
			//2 封装foss 起请求参数
			QueryTrayScanTaskEntity querytask=this.getFossReqEntity(asyncMsg,param);
			//3 调用foss 接口
			PDATrayScanTaskEntity scanList=pdaTrayScanService.queryTrayScanDetail(querytask);
			//4封装PDA返回参数
			List<GetUnldPalletBingingResEntity> returnList=this.getPdaResList(scanList);
			return returnList;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:49
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_GET_BOUND_TASK.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	private void validate(AsyncMsg asyncMsg, GetUnldPalletBingingReqEntity entity) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetUnldPalletBingingReqEntity");
		//运单号
		Argument.notNull(entity.getWblCode(), "GetUnldPalletBingingReqEntity.wblCode");
		//流水号
		Argument.hasText(entity.getSerialNo(), "GetUnldPalletBingingReqEntity.serialNo");
		//当前部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
	}
	/**
	 * 
	 * @param asyncMsg
	 * @param param
	 * @description 封装foss 起请求参数
	 * @version 1.0
	 * @author wenwuneng 
	 * @update 2013-8-13 上午10:21:57
	 */
	private QueryTrayScanTaskEntity getFossReqEntity(AsyncMsg asyncMsg,GetUnldPalletBingingReqEntity param){
		QueryTrayScanTaskEntity querytask=new QueryTrayScanTaskEntity();
		querytask.setWaybillNo(param.getWblCode());//运单号
		querytask.setSerialNo(param.getSerialNo());//流水号
		querytask.setOrgCode(asyncMsg.getDeptCode());//叉车司机当前部门
//		querytask.setBindingCode(asyncMsg.getUserCode());//叉车司机工号
		return querytask;
	}
	/**
	 * 
	 * @return
	 * @description 获取PDA返回参数
	 * @version 1.0
	 * @author wenwuneng 
	 * @update 2013-8-13 上午10:27:33
	 */
	private  List<GetUnldPalletBingingResEntity> getPdaResList(PDATrayScanTaskEntity scanList){
		List<GetUnldPalletBingingResEntity> returnList=new ArrayList<GetUnldPalletBingingResEntity>();
		//foss 绑定任务明细
		if(scanList==null){
			return returnList;
		}
		List<PDATrayScanDetailEntity> trayScanDetails=scanList.getTrayScanDetails();
		if(trayScanDetails==null||trayScanDetails.isEmpty()){
			return returnList;
		}
		Map<String,GetUnldPalletBingingResEntity> resMap=new HashMap<String,GetUnldPalletBingingResEntity>();
		for(PDATrayScanDetailEntity detailEntity:trayScanDetails){
			GetUnldPalletBingingResEntity scanEntity=null;
			String waybillNo=detailEntity.getWaybillNo();
			if(waybillNo==null ||waybillNo.equals("")){
				logger.info("-----------托盘扫描获取任务运单明细异常：叉车司机获取运单号为空---------------");
				continue;
			}
			//贵重物品还是代打木架标示位
			String remark=detailEntity.getWaybillInfo();
			if(remark==null ||remark.equals("")){
				remark="0";
			}
			scanEntity=resMap.get(waybillNo);
			if(scanEntity==null){//第一次录入集合
				scanEntity=new GetUnldPalletBingingResEntity();
				
				scanEntity.setWblCode(waybillNo);//运单号
				scanEntity.setBindingNum(1);//绑定件数
				String bindingNo=scanList.getTaskNo();
				if(bindingNo==null){
					logger.info("-----------托盘扫描获取任务运单明细业务异常：绑定唯一编号为空");
					bindingNo="";
				}
				scanEntity.setBindingNo(scanList.getTaskNo());//托盘绑定任务唯一编号
				scanEntity.setDeptDestName(detailEntity.getDestDeptName());//目的站
				scanEntity.setRemark(remark);//贵重物品类型
				scanEntity.setPieces(detailEntity.getSerialCount());//开单件数
				scanEntity.setGoodAreaCode(detailEntity.getGoodAreaCode());//库区编码
				scanEntity.setAdminiRegions(detailEntity.getAdminiRegions());//行政区域
				
				scanEntity.setCardNo(scanList.getForkliftVotes());//叉车票数
				if(scanList.getForkliftVotes()==0){
					logger.info("-----------托盘扫描获取任务运单明细业务异常：绑定叉车票数为0");
				}
				if(detailEntity.getTranProperty()==null || detailEntity.getTranProperty().equals("")){
					logger.info("-----------托盘扫描获取任务运单明细业务异常：绑定唯一编号："+bindingNo+"运单号："+waybillNo+" 运输性质为空"+"-----------");
				}
				scanEntity.setTransType(detailEntity.getTranProperty());//运输性质
				/**
				 * gaojia 2014-3-10
				 */
				scanEntity.setStock(detailEntity.getGoodAreaName());
				resMap.put(waybillNo, scanEntity);
			}else{//运单 件数加1
				int size=scanEntity.getBindingNum();
				scanEntity.setBindingNum(++size);
				//判断贵重物品
				String remarkOld=scanEntity.getRemark();
				if(!remark.equals("0")&& !remark.equals(remarkOld)){//不为空且不一样
					if(remarkOld.equals("0")){//之前不是没有
						scanEntity.setRemark(remark);
					}else{
						scanEntity.setRemark("3");
					}
				}
				/**
				 * gaojia 2014-3-10
				 */
				if(!StringUtils.isEmpty(detailEntity.getGoodAreaName())){
					scanEntity.setStock(detailEntity.getGoodAreaName());
				}
				resMap.put(waybillNo, scanEntity);
			}
		}
		
		Iterator <Entry<String,GetUnldPalletBingingResEntity>> it=resMap.entrySet().iterator();
		while(it.hasNext()){
			GetUnldPalletBingingResEntity scanEntity=it.next().getValue();
			String remark=scanEntity.getRemark();
			if(remark.equals("0")){
				scanEntity.setRemark("");//备注	
			}
			if(remark.equals("1")){
				scanEntity.setRemark("贵重物品");//备注	
			}
			if(remark.equals("2")){
				scanEntity.setRemark("待打木架");//备注	
			}
			if(remark.equals("3")){
				scanEntity.setRemark("1：待打木架 2：贵重物品");//备注	
			}
			returnList.add(scanEntity);
		}
		return returnList;
	}
	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}
	
		
//	public  static void main(String args[]){
//		GetUnldPalletBoundService p;
//		PDATrayScanTaskEntity scanEntity=new PDATrayScanTaskEntity();
//		List<PDATrayScanDetailEntity> trayScanDetails=new ArrayList<PDATrayScanDetailEntity>();
//		
//		for(int i=0;i<4;i++){
//			PDATrayScanDetailEntity en=new PDATrayScanDetailEntity();
//			en.setDestDeptName("上海");
//			en.setSerialNo("01"+i);
//			en.setWaybillNo("8");
//			if(i%3==1){
//				en.setWaybillInfo("3");	
//			}
////			else
////			if(i%3==2){
////				en.setWaybillInfo("2");	
////			}
////			else
////			if(i%3==3){
////				en.setWaybillInfo("3");	
////			}
//			else{
//				en.setWaybillInfo("0");	
//			}
//			trayScanDetails.add(en);
//		}
//		scanEntity.setTaskNo("11101");
//		scanEntity.setTrayScanDetails(trayScanDetails);
//		
////		List<GetUnldPalletBingingResEntity> returnList=GetUnldPalletBoundService.getPdaResList(scanEntity);
////		for(GetUnldPalletBingingResEntity en:returnList){
////			System.out.print(en.getWblCode()+" ");
////			System.out.print(en.getBindingNo()+" ");
////			System.out.print(en.getBindingNum()+" ");
////			System.out.print(en.getDeptDestName()+"");
////			System.out.print(en.getRemark()+" ");
////			System.out.println();
////			
////		}
//	}
}
