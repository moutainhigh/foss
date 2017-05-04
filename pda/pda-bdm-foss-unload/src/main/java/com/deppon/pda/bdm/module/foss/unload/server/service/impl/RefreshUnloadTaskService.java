package com.deppon.pda.bdm.module.foss.unload.server.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
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
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryUnldInfo;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldCrgDetail;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldSerialNoModel;


/**
 * 刷新卸车任务货物清单属性service实现类
 * 
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public class RefreshUnloadTaskService implements
		IBusinessService<List<UnldCrgDetail>, QryUnldInfo> {
	private IPDAUnloadTaskService pdaUnloadTaskService;
	private static final Logger logger = Logger.getLogger(RefreshUnloadTaskService.class);
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:00
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QryUnldInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryUnldInfo model = new QryUnldInfo();
		model = JsonUtil.parseJsonToObject(QryUnldInfo.class,
				asyncMsg.getContent());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:04
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<UnldCrgDetail> service(AsyncMsg asyncMsg, QryUnldInfo param)
			throws PdaBusiException {
		this.validate(param);
		List<UnloadGoodsDetailDto> res = null;
		try {
			res = pdaUnloadTaskService.refrushUnloadTaskDetail(param
					.getTaskCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		List<UnldCrgDetail> crgDetails = new ArrayList<UnldCrgDetail>();
		
		
		//存放运单实体MAP
		HashMap<String,UnldCrgDetail> map = new HashMap<String,UnldCrgDetail>();
		if(res!=null&&!res.isEmpty()){
			for (UnloadGoodsDetailDto dto : res) {
				UnldCrgDetail detail = new UnldCrgDetail();
				//是否分批配载
				if(map.containsKey(dto.getWayBillNo())){
					logger.info("分批配载或者多货："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
					detail = map.get(dto.getWayBillNo());
					List<UnldSerialNoModel> serias = detail.getSerialNo();
					//标记不同流水号的个数
					int differentSerNum = 0;
					if(dto.getSerialNos()!=null&&!dto.getSerialNos().isEmpty()){
						//将流水号放入map中
						HashMap<String,String> serMap = new HashMap<String,String>();
						for (UnldSerialNoModel unldSerialNoModel : serias) {
							serMap.put(unldSerialNoModel.getSerialNo(), unldSerialNoModel.getSerialNo());
						}
						for (PDAGoodsSerialNoDto seriaDto : dto.getSerialNos()) {
							//流水号是否已存在
							if(!serMap.containsKey(seriaDto.getSerialNo())){
								UnldSerialNoModel ser = new UnldSerialNoModel();
								if(seriaDto.getSerialNo()==null||seriaDto.getSerialNo().isEmpty()){
									continue;
								}
								ser.setIsWrap(seriaDto.getIsUnPacking());
								ser.setSerialNo(seriaDto.getSerialNo());
								ser.setIsChgLabel(seriaDto.getIsToDoList());
								serias.add(ser);
								differentSerNum++;
							}
						}
					}
					detail.setSerialNo(serias);
					//是否多货卸车
					if(!TransferPDADictConstants.UNLOAD_MORE_BILL_NO.equals(dto.getBillNo())){
						logger.info("分批配载："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
						detail.setRcptPieces(detail.getRcptPieces()+differentSerNum);
					}
					detail.setUnldPieces(detail.getUnldPieces()+dto.getOperateQty());
					//是否分批配载
					detail.setBePartial("Y");
					//是否电子面单 author:245960 Date:2015-06-29
					detail.setBeEWaybill(dto.getBeEWaybill());
					detail.setIsSevenDaysReturn(dto.getIsSevenDaysReturn());
					/**
					 * 是否转寄退回件  245955 2016-02-22
					 */
					detail.setIsHandle(dto.getIsHandle());
					map.put(detail.getWblCode(), detail);
				}else{
					/**
					 * 是否转寄退回件  245955 2016-02-22
					 */
					detail.setIsHandle(dto.getIsHandle());
					detail.setTaskCode(dto.getTaskNo());
					detail.setWblCode(dto.getWayBillNo());
					detail.setAcctDeptCode(dto.getReceiveOrgCode());
					/*
					 * wwn 3013-05-31 15:04
					 * */
					detail.setAcctDeptName(StringUtils.convert(dto.getReceiveOrgName()));
					detail.setArrDeptCode(dto.getReachOrgCode());
					/*
					 * wwn 3013-05-31 15:04
					 * */
					detail.setArrDeptName(StringUtils.convert(dto.getReachOrgName()));
					/*
					 * wwn 3013-05-31 15:04
					 * */
					detail.setCargoName(StringUtils.convert(dto.getGoodsName()));
					detail.setVolume(dto.getVolume());
					detail.setWeight(dto.getWeight());
					detail.setTransType(dto.getTransportType());
					detail.setIsNessary(dto.getBePriorityGoods());
					/*
					 * wwn 3013-05-31 15:04
					 * */
					detail.setPacking(StringUtils.convert(dto.getPacking()));
					detail.setIsVal(dto.getIsValue());
					//detail.setChgStatus(dto.getModifyContent());
					//detail.setIsModify(dto.getModifyState());
					detail.setBeContraband(dto.getBeContraband());
					detail.setOrigOrgCode(dto.getOrigOrgCode());
					detail.setDestOrgCode(dto.getDestOrgCode());
					detail.setBillNo(dto.getBillNo());
					//zwd 200968  运单生效状态 - YES NO
					detail.setWayBillStatus(dto.getWayBillStatus());
					List<UnldSerialNoModel> serias = new ArrayList<UnldSerialNoModel>();
					if(dto.getSerialNos()!=null&&!dto.getSerialNos().isEmpty()){
						for (PDAGoodsSerialNoDto seriaDto : dto.getSerialNos()) {
							UnldSerialNoModel ser = new UnldSerialNoModel();
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
					detail.setPieces(dto.getWayBillQty());
					//是否多货卸车
					if(!TransferPDADictConstants.UNLOAD_MORE_BILL_NO.equals(dto.getBillNo())){
						detail.setRcptPieces(detail.getSerialNo().size());
						detail.setBePartial("N");
					}else{
						detail.setBePartial("Y");
						logger.info("多货："+dto.getTaskNo()+" "+dto.getBillNo()+" "+dto.getWayBillNo());
					}
					/*
					 * wwn 3013-05-31 15:04
					 * */
					detail.setRemark(StringUtils.convert(dto.getNotes()));
					detail.setUnldPieces(dto.getOperateQty());
					detail.setBeContraband(dto.getBeContraband());
					detail.setStationNumber(dto.getStationNumber());
					//刷新卸车任务时增加  行政区域  字段
					detail.setCustDistName(dto.getAdminiRegions());
					detail.setPackageRemark(dto.getPackageRemark());
					//是否电子面单 author:245960 Date:2015-06-29
					detail.setBeEWaybill(dto.getBeEWaybill());
					detail.setIsSevenDaysReturn(dto.getIsSevenDaysReturn());
					map.put(detail.getWblCode(), detail);
				}
			}
		}
		//将map中的value转换成list
		Set<Entry<String, UnldCrgDetail>> set = map.entrySet();
		Iterator<Entry<String, UnldCrgDetail>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, UnldCrgDetail> entry = it.next();
			crgDetails.add(entry.getValue());
		}
		logger.info(JsonUtil.encapsulateJsonObject(crgDetails));
		//封装卸车流水号属性二进制
		for (UnldCrgDetail detail : crgDetails) {
			List<UnldSerialNoModel> sers = detail.getSerialNo();
			if(sers!=null&&!sers.isEmpty()){
				Collections.sort(sers, new Comparator<UnldSerialNoModel>() {
					@Override
					public int compare(UnldSerialNoModel o1, UnldSerialNoModel o2) {
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
				detail.setIsModifyStr(isModifyNoBuf.toString());
				detail.setSerialNo(null);
				
			}
		}
		return crgDetails;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_RFSH.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:11
	 * @param qryUnldInfo
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(QryUnldInfo qryUnldInfo)
			throws ArgumentInvalidException {
		// 检验查询卸车信息非空
		Argument.notNull(qryUnldInfo, "qryUnldInfo");
		// 检验任务号非空
		Argument.hasText(qryUnldInfo.getTaskCode(), "qryUnldInfo.taskCode");
	}

	public void setPdaUnloadTaskService(
			IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	
}


