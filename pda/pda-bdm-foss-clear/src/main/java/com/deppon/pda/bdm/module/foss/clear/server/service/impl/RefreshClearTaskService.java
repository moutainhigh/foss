package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearCrgDetail;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearSerialNo;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.RfshClearTask;

/**
 * 
  * @ClassName ClearScanService 
  * @Description 刷新清仓任务
  * @author xujun dpxj@deppon.com 
  * @date 2013-1-10 下午7:02:49
  * 
 */
public class RefreshClearTaskService implements IBusinessService<List<ClearCrgDetail>, RfshClearTask>{
	private static final Log LOG = LogFactory.getLog(RefreshClearTaskService.class);
	public final static int NUM = 32;
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Override
	public RfshClearTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		RfshClearTask clearTask = JsonUtil.parseJsonToObject(RfshClearTask.class, asyncMsg.getContent());
		return clearTask;
	}

	@Transactional
	@Override
	public List<ClearCrgDetail> service(AsyncMsg asyncMsg, RfshClearTask param)
			throws PdaBusiException {
		LOG.info("start refresh cleartask ...");
		List<PdaStTaskDto> dtos = null;
		try {
			dtos = pdaStockcheckingService.queryPdaStTaskDtoList(param.getTaskCode());
		} catch (BusinessException e) {
			LOG.error("刷新异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		List<ClearCrgDetail> clearCrgDetails = this.wrapPdaStTaskDtos(dtos);
		LOG.info("清仓明细字符串："+JsonUtil.encapsulateJsonObject(clearCrgDetails));
		for (ClearCrgDetail clearCrgDetail : clearCrgDetails) {
			clearCrgDetail.setSerialNos(null);
		}
		LOG.info("refresh cleartask end...");
		return clearCrgDetails;
	}

	private List<ClearCrgDetail> wrapPdaStTaskDtos(List<PdaStTaskDto> dtos) {
		List<ClearCrgDetail> clearCrgDetails = new ArrayList<ClearCrgDetail>();
		if(dtos!=null&&!dtos.isEmpty()){
			for(PdaStTaskDto dto:dtos){
				ClearCrgDetail clearCrgDetail = new ClearCrgDetail(); 
				clearCrgDetail.setCrgName(StringUtils.convert(dto.getGoodsName()));   //货物名
				clearCrgDetail.setInInvtTime(dto.getInStockTime()); //入库时间
				List<PDAGoodsSerialNoDto> goodsSerialNoDtos = dto.getSerialNos();
				List<ClearSerialNo> sers = new ArrayList<ClearSerialNo>();
				if(goodsSerialNoDtos!=null&&!goodsSerialNoDtos.isEmpty()){
					clearCrgDetail.setInvtPieces(goodsSerialNoDtos.size()); // 流水号的数量为库存件数
					for (PDAGoodsSerialNoDto sdto : goodsSerialNoDtos) {
						if(sdto.getSerialNo()==null||sdto.getSerialNo().isEmpty()){
							continue;
						}
						ClearSerialNo ser = new ClearSerialNo();
						ser.setIsUnPacking(sdto.getIsUnPacking());
						// 流水号中可能存在特殊字符，导致转整型失败，影响后面的业务操作，在此处进行提前处理。2013-08-08
						try{
							Integer.parseInt(sdto.getSerialNo());
						}catch (Exception e) {
							throw new FossInterfaceException(null, "运单号:"+dto.getWaybillNo()+"流水号："+sdto.getSerialNo()+"不合法");
						}
						ser.setSerialNo(sdto.getSerialNo());
						ser.setIsChgLabel(sdto.getIsToDoList());
						sers.add(ser);
					}
				}
				clearCrgDetail.setSerialNos(sers);
				//流水号及是否未打包装二进制
				//------对流水号进行排序
				Collections.sort(sers, new Comparator<ClearSerialNo>() {
					@Override
					public int compare(ClearSerialNo o1, ClearSerialNo o2) {
						if(Integer.parseInt(o1.getSerialNo())>Integer.parseInt(o2.getSerialNo())){
							return 1;
						}else if(Integer.parseInt(o1.getSerialNo())==Integer.parseInt(o2.getSerialNo())){
							return 0;
						}else{
							return -1;
						}
					}
				});
				if(sers.size()>0){
					//--生成流水号二进制
					//获得最大的流水号需要多少位二进制
					int maxSer = 9999;
					try {
						maxSer = Integer.parseInt(sers.get(sers.size()-1).getSerialNo());
					}catch (Exception e) {
						throw new FossInterfaceException(null, "运单号:"+dto.getWaybillNo()+"流水号："+sers.get(sers.size()-1).getSerialNo()+"不合法");
					}
					int max = maxSer%NUM==0?
							maxSer/NUM:maxSer/NUM+1;
					//流水号二进制数组
				    long[] serNos = new long[max];
				    //是否未打包装二进制数组
					long[] unPackingNos = new long[max];
					//是否有更改
					long[] modifyNos = new long[max];
					for(int i=0;i<sers.size();i++){
						if(StringUtil.isEmpty(sers.get(i).getSerialNo())){
							LOG.warn("流水号为空");
							continue;
						}
						int serialNo = 0;
						try {
							serialNo = Integer.parseInt(sers.get(i).getSerialNo());
						}catch (Exception e) {
							throw new FossInterfaceException(null, "运单号:"+dto.getWaybillNo()+"流水号："+sers.get(i).getSerialNo()+"不合法");
						}
						if(serialNo==0){
							LOG.warn("运单号"+dto.getWaybillNo()+ "存在为0000的流水号");
							continue;
						}
						boolean isUnPacking = "Y".equals(sers.get(i).getIsUnPacking());
						boolean isModify = "Y".equals(sers.get(i).getIsChgLabel());
						int index = serialNo%NUM==0?serialNo/NUM:serialNo/NUM+1;
						serNos[index-1] = BitUtil.setBit(serNos[index-1],serialNo%NUM==0?NUM:serialNo%NUM);
						if(isUnPacking){
							unPackingNos[index-1]=BitUtil.setBit(unPackingNos[index-1],serialNo%NUM==0?NUM:serialNo%NUM);
						}
						if(isModify){
							modifyNos[index-1]=BitUtil.setBit(modifyNos[index-1],serialNo%NUM==0?NUM:serialNo%NUM);
						}
					}
					StringBuffer serNoBuf = new StringBuffer("");
					StringBuffer unPackingNoBuf = new StringBuffer("");
					StringBuffer modifyNoBuf = new StringBuffer("");
					
					for(int i=0;i<max;i++){
						serNoBuf.append(Long.toHexString(serNos[i])).append("|");
						unPackingNoBuf.append(Long.toHexString(unPackingNos[i])).append("|");
						modifyNoBuf.append(Long.toHexString(modifyNos[i])).append("|");
					}
					clearCrgDetail.setSerialNoStr(serNoBuf.toString());
					clearCrgDetail.setUnPackingStr(unPackingNoBuf.toString());
					clearCrgDetail.setIsModifyStr(modifyNoBuf.toString());
				}
				clearCrgDetail.setIsLabelAbolish(dto.getLabelTrash());  // 是否标签作废
				clearCrgDetail.setTaskCode(dto.getStTaskNo());// 任务编号
				clearCrgDetail.setWblCode(dto.getWaybillNo());// 运单号
				clearCrgDetail.setTransType(dto.getProductCode());//类型   卡货、普货
				clearCrgDetail.setWrapType(dto.getPackageType());// 包装类型
				clearCrgDetail.setIsVal(dto.getPreciousGoods());// 是否贵重物品
				clearCrgDetail.setIsWrap(dto.getNeedWoodenPackage());//是否代大木架
				clearCrgDetail.setIsCtrabdGoods(dto.getContraband());//是否违禁物品
				clearCrgDetail.setVolume(dto.getGoodsVolume());// 体积
				
				clearCrgDetail.setArrDeptName(StringUtils.convert(dto.getTargetOrgName()));
				clearCrgDetail.setClearPieces(dto.getFinishedQty());
				clearCrgDetail.setIsChgLabel(dto.getLabelTrash()); // FOSS未提供是否更换标签
				clearCrgDetail.setStationNumber(dto.getStationNumber());//提货网点编码
				clearCrgDetail.setCustDistName(dto.getReceiveCustomerDistName());//清仓模块增加 行政区域 字段
				clearCrgDetails.add(clearCrgDetail);
			}
			
		}
		return clearCrgDetails;
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_TASK_RFSH.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

}
