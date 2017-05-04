package com.deppon.pda.bdm.module.foss.unload.server.service.impl.driverunload;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.unload.server.service.impl.express.KdRefreshUnloadTaskService;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.DownUnloadDetail;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.DownUnloadSerialNoModel;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.DownUnloadTaskEntity;
/**
 * 下拉接驳卸车明细
 * @ClassName DownTranUnloadDetailService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
 */
public class DownTranUnloadDetailService implements IBusinessService<List<DownUnloadDetail>, DownUnloadTaskEntity>{
	private ISCPDAUnloadTaskService scpdaUnloadTaskService;
	private static final Logger logger = Logger.getLogger(KdRefreshUnloadTaskService.class);

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public DownUnloadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DownUnloadTaskEntity dUnloadTaskEntity=JsonUtil.parseJsonToObject(DownUnloadTaskEntity.class, asyncMsg.getContent());
		return dUnloadTaskEntity;
	}

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public List<DownUnloadDetail> service(AsyncMsg asyncMsg, DownUnloadTaskEntity param) throws PdaBusiException {
		this.validate(param);
		List<UnloadGoodsDetailDto> res = null;
		try {
			res =scpdaUnloadTaskService.refrushUnloadTaskDetail(param.getTaskCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		List<DownUnloadDetail> downUnloadDetails = new ArrayList<DownUnloadDetail>();
		//存放运单实体MAP
		HashMap<String,DownUnloadDetail> map = new HashMap<String,DownUnloadDetail>();
		if(res!=null&&!res.isEmpty()){
			for (UnloadGoodsDetailDto dto : res) {
				DownUnloadDetail detail = new DownUnloadDetail();
				//是否分批配载
				if(map.containsKey(dto.getWayBillNo())){
					logger.info("分批配载或者多货："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
					detail = map.get(dto.getWayBillNo());
					List<DownUnloadSerialNoModel> serias = detail.getSerialNo();
					//标记不同流水号的个数
					//int differentSerNum = 0;
					if(dto.getSerialNos()!=null&&!dto.getSerialNos().isEmpty()){
						//将流水号放入map中
						HashMap<String,String> serMap = new HashMap<String,String>();
						for (DownUnloadSerialNoModel downUnloadSerialNoModel : serias) {
							serMap.put(downUnloadSerialNoModel.getSerialNo(), downUnloadSerialNoModel.getSerialNo());
						}
						for (PDAGoodsSerialNoDto seriaDto : dto.getSerialNos()) {
							//流水号是否已存在
							if(!serMap.containsKey(seriaDto.getSerialNo())){
								DownUnloadSerialNoModel ser = new DownUnloadSerialNoModel();
								if(seriaDto.getSerialNo()==null||seriaDto.getSerialNo().isEmpty()){
									continue;
								}
								ser.setIsWrap(seriaDto.getIsUnPacking());
								ser.setSerialNo(seriaDto.getSerialNo());
								ser.setIsChgLabel(seriaDto.getIsToDoList());
								serias.add(ser);
								//differentSerNum++;
							}
						}
					}
					detail.setSerialNo(serias);
					//是否多货卸车
					//if(!TransferPDADictConstants.UNLOAD_MORE_BILL_NO.equals(dto.getBillNo())){
					//	logger.info("分批配载："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
					//	detail.setRcptPieces(detail.getRcptPieces()+differentSerNum);
					//}
					detail.setUnldPieces(detail.getUnldPieces()+dto.getOperateQty());
					//是否分批配载
					//detail.setBePartial("Y");
					map.put(detail.getWblCode(), detail);
				}else{
					detail.setTaskCode(dto.getTaskNo()); //任务号
					detail.setWblCode(dto.getWayBillNo());//运单号
					//detail.setAcctDeptCode(dto.getReceiveOrgCode());
					//detail.setAcctDeptName(StringUtils.convert(dto.getReceiveOrgName()));
					//detail.setSumPieces(dto.getStationNumber());//总件数
					detail.setGoodsQtyTotal(dto.getWayBillQty());//开单件数
					detail.setArrDeptCode(dto.getReachOrgCode());//到达部门编号
					detail.setArrDeptName(StringUtils.convert(dto.getReachOrgName()));//到达部门
					List<DownUnloadSerialNoModel> serias = new ArrayList<DownUnloadSerialNoModel>();
					if(dto.getSerialNos()!=null&&!dto.getSerialNos().isEmpty()){
						for (PDAGoodsSerialNoDto seriaDto : dto.getSerialNos()) {
							DownUnloadSerialNoModel ser = new DownUnloadSerialNoModel();
							if(seriaDto.getSerialNo()==null||seriaDto.getSerialNo().isEmpty()){
								continue;
							}
							ser.setIsWrap(seriaDto.getIsUnPacking());
							ser.setSerialNo(seriaDto.getSerialNo());
							ser.setIsChgLabel(seriaDto.getIsToDoList());
							serias.add(ser);
						}
					}
					detail.setSerialNo(serias);
					//是否多货卸车
					//if(!TransferPDADictConstants.UNLOAD_MORE_BILL_NO.equals(dto.getBillNo())){
						//detail.setRcptPieces(detail.getSerialNo().size());
						//detail.setBePartial("N");
					//}else{
					//	detail.setBePartial("Y");
					//	logger.info("多货："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
					//}
					detail.setUnldPieces(dto.getOperateQty());
					map.put(detail.getWblCode(), detail);
				}
			}
		}
		//将map中的value转换成list
		Set<Entry<String, DownUnloadDetail>> set = map.entrySet();
		Iterator<Entry<String, DownUnloadDetail>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, DownUnloadDetail> entry = it.next();
			downUnloadDetails.add(entry.getValue());
		}
		logger.info(JsonUtil.encapsulateJsonObject(downUnloadDetails));
		//封装卸车流水号属性二进制
		for (DownUnloadDetail detail : downUnloadDetails) {
			List<DownUnloadSerialNoModel> sers = detail.getSerialNo();
			if(sers!=null&&!sers.isEmpty()){
				Collections.sort(sers, new Comparator<DownUnloadSerialNoModel>() {
					@Override
					public int compare(DownUnloadSerialNoModel o1, DownUnloadSerialNoModel o2) {
						if(Integer.parseInt(o1.getSerialNo())>Integer.parseInt(o2.getSerialNo())){
							return 1;
						}else if(Integer.parseInt(o1.getSerialNo())==Integer.parseInt(o2.getSerialNo())){
							return 0;
						}else{
							return -1;
						}
					}
				});
				//--生成流水号二进制
				//获得最大的流水号需要多少位二进制
				int maxSer = 9999;
				try {
					maxSer = Integer.parseInt(sers.get(sers.size()-1).getSerialNo());
				}catch (Exception e) {
					throw new FossInterfaceException(null, "运单号:"+detail.getWblCode()+"流水号："+sers.get(sers.size()-1).getSerialNo()+"不合法");
				}
				int max = maxSer%32==0?
						maxSer/32:maxSer/32+1;
				//流水号二进制数组
			    long[] serNos = new long[max];
			    //是否未打包装二进制数组
				long[] unPackingNos = new long[max];
				//是否未打包装二进制数组
				long[] modifyNos = new long[max];
				for(int i=0;i<sers.size();i++){
					if(StringUtil.isEmpty(sers.get(i).getSerialNo())){
						logger.warn("流水号为空");
						continue;
					}
					int serialNo = 0;
					try {
						serialNo = Integer.parseInt(sers.get(i).getSerialNo());
					}catch (Exception e) {
						throw new FossInterfaceException(null, "运单号:"+detail.getWblCode()+"流水号："+sers.get(i).getSerialNo()+"不合法");
					}
					if(serialNo==0){
						logger.warn("运单号"+detail.getWblCode()+ "存在为0000的流水号");
						continue;
					}
					boolean isUnPacking = "Y".equals(sers.get(i).getIsWrap());
					boolean isModify = "Y".equals(sers.get(i).getIsChgLabel());
					int index = serialNo%32==0?serialNo/32:serialNo/32+1;
					serNos[index-1] = BitUtil.setBit(serNos[index-1],serialNo%32==0?32:serialNo%32);
					if(isUnPacking){
						unPackingNos[index-1]=BitUtil.setBit(unPackingNos[index-1],serialNo%32==0?32:serialNo%32);
					}
					if(isModify){
						modifyNos[index-1]=BitUtil.setBit(modifyNos[index-1],serialNo%32==0?32:serialNo%32);
					}
				}
				StringBuffer serNoBuf = new StringBuffer("");
				StringBuffer unPackingNoBuf = new StringBuffer("");
				StringBuffer isModifyNoBuf = new StringBuffer("");
				for(int i=0;i<max;i++){
					serNoBuf.append(Long.toHexString(serNos[i])).append("|");
					unPackingNoBuf.append(Long.toHexString(unPackingNos[i])).append("|");
					isModifyNoBuf.append(Long.toHexString(modifyNos[i])).append("|");
				}
				detail.setSerialNoStr(serNoBuf.toString());
				detail.setUnPackingStr(unPackingNoBuf.toString());
				//detail.setIsModifyStr(isModifyNoBuf.toString());
				detail.setSerialNo(null);
			}
		}
		return downUnloadDetails;
	}
   /**
    * 操作类型
    * @description 
    * @return
    * @author 245955
    * @date 2015-4-14
    */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLOAD_TRAN_DOWN.VERSION;	
	}
	/**
	 * 参数验证
	 * @description 
	 * @param qryUnldInfo
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-14
	 */
	private void validate(DownUnloadTaskEntity downUnloadTaskEntity)
			throws ArgumentInvalidException {
		// 检验查询卸车信息非空
		Argument.notNull(downUnloadTaskEntity, "downUnloadTaskEntity");
		// 检验任务号非空
		Argument.hasText(downUnloadTaskEntity.getTaskCode(), "downUnloadTaskEntity.taskCode");
	}
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public boolean isAsync() {
		return false;
    }

	public void setScpdaUnloadTaskService(ISCPDAUnloadTaskService scpdaUnloadTaskService) {
		this.scpdaUnloadTaskService = scpdaUnloadTaskService;
	}


}
