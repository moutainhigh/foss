package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.load.shared.domain.QrySalesDepartmentInfo;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SalesDepartmentCrgDetail;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SerialNoModel;

/**   
 * @ClassName RfshExpLoadTaskService  
 * @Description  刷新中转快递装车任务货物明细 
 * @author  092038 张贞献  
 * @date 2014-12-9    
 */ 
public class RfshExpSalesDepartmentTaskService implements IBusinessService<List<SalesDepartmentCrgDetail>, QrySalesDepartmentInfo>{
	private IPDATransferLoadService pdaTransferLoadService;
	private static final Log LOG = LogFactory.getLog(RfshExpSalesDepartmentTaskService.class);
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:59
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QrySalesDepartmentInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QrySalesDepartmentInfo model = JsonUtil.parseJsonToObject(QrySalesDepartmentInfo.class, asyncMsg.getContent());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:05
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<SalesDepartmentCrgDetail> service(AsyncMsg asyncMsg, QrySalesDepartmentInfo param)
			throws PdaBusiException {
		this.validate(param);
		List<LoadSaleGoodsDetailDto> res = new ArrayList<LoadSaleGoodsDetailDto>();
		long start = System.currentTimeMillis();
		try {
		
		  res =  pdaTransferLoadService.refrushSaleLoadTaskExpressDetail(param.getTaskCode());
			
			LOG.info("调用FOSS接口所需时间："+(System.currentTimeMillis()-start));
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		start = System.currentTimeMillis();
		//Argument.notEmpty(res, "List<LoadGoodsDetailDto>");
		List<SalesDepartmentCrgDetail> details = new ArrayList<SalesDepartmentCrgDetail>();
		if(res!=null&&!res.isEmpty()){
			String valueGoodsAreaCode = res.get(0).getValueGoodsAreaCode();
			String packGoodsAreaCode = res.get(0).getPackGoodsAreaCode();
			for (LoadSaleGoodsDetailDto loadTaskDetailDto : res) {
				SalesDepartmentCrgDetail detail = new SalesDepartmentCrgDetail();
				detail.setAcctDeptCode(loadTaskDetailDto.getReceiveOrgCode());//收货部门编号
				/*
				 * wwn 3013-05-31 15:12
				 * */
				detail.setAcctDeptName(StringUtils.convert(loadTaskDetailDto.getReceiveOrgName()));//收货部门名称
				detail.setArrDeptCode(loadTaskDetailDto.getReachOrgCode());//到达部门编码
				/*
				 * wwn 3013-05-31 15:12
				 * */
				detail.setArrDeptName(StringUtils.convert(loadTaskDetailDto.getReachOrgName()));//到达部门名称
				/*
				 * wwn 3013-05-31 15:12
				 * */
				detail.setCargoName(StringUtils.convert(loadTaskDetailDto.getGoodsName()));//货物名称
				detail.setInInvtTime(loadTaskDetailDto.getStockTime());//入库时间
				detail.setIsNessary(loadTaskDetailDto.getBePriorityGoods());//是否必走货
				/*
				 * wwn 3013-05-31 15:12
				 * */
				detail.setPacking(StringUtils.convert(loadTaskDetailDto.getPacking()));//包装
				/*
				 * wwn 3013-05-31 15:12
				 * */
				detail.setRemark(StringUtils.convert(loadTaskDetailDto.getNotes()));//备注
				
				List<SerialNoModel> sers = new ArrayList<SerialNoModel>();
				//封装流水号
				for (PDAGoodsSerialNoDto seriaDto : loadTaskDetailDto.getSerialNos()) {
					SerialNoModel ser = new SerialNoModel();
					if(seriaDto.getSerialNo()==null||seriaDto.getSerialNo().isEmpty()){
						continue;
					}
					//标签号
					ser.setSerialNo(seriaDto.getSerialNo());
					//库区编号
					ser.setStockAreaCode(seriaDto.getStockAreaCode());
					//是否未打包装
					ser.setIsWrap(seriaDto.getIsUnPacking());
					//是否有更改
					ser.setIsChgLabel(seriaDto.getIsToDoList());
					if(valueGoodsAreaCode!=null&&!valueGoodsAreaCode.isEmpty()){
						if(valueGoodsAreaCode.equals(seriaDto.getStockAreaCode())){
							ser.setIsValArea("Y");
						}else{
							ser.setIsValArea("N");
						}
					}
					if(packGoodsAreaCode!=null&&!packGoodsAreaCode.isEmpty()){
						if(packGoodsAreaCode.equals(seriaDto.getStockAreaCode())){
							ser.setIsWrapArea("Y");
						}else{
							ser.setIsWrapArea("N");
						}
					}
					sers.add(ser);
				}
				detail.setSerialNo(sers);
				//流水号及是否未打包装二进制
				//------对流水号进行排序
				Collections.sort(sers, new Comparator<SerialNoModel>() {
					@Override
					public int compare(SerialNoModel o1, SerialNoModel o2) {
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
					} catch (Exception e) {
						throw new FossInterfaceException(null, "运单号:"+loadTaskDetailDto.getWayBillNo()+"流水号："+sers.get(sers.size()-1).getSerialNo()+"不合法");
					}
					int max = maxSer%Constant.SERIALNUM==0?
							maxSer/Constant.SERIALNUM:maxSer/Constant.SERIALNUM+1;
					//流水号二进制数组
				    long[] serNos = new long[max];
				    //是否未打包装二进制数组
					long[] unPackingNos = new long[max];
					//是否贵重物品货区
					long[] valNos = new long[max];
					//是否包装货区
					long[] packNos = new long[max];
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
							throw new FossInterfaceException(null, "运单号:"+loadTaskDetailDto.getWayBillNo()+"流水号："+sers.get(i).getSerialNo()+"不合法");
						}
						if(serialNo==0){
							LOG.warn("运单号"+loadTaskDetailDto.getWayBillNo()+ "存在为0000的流水号");
							continue;
						}
						//是否未打包装
						boolean isUnPacking = "Y".equals(sers.get(i).getIsWrap());
						//是否未出贵重物品区
						boolean isValArea = "Y".equals(sers.get(i).getIsValArea());
						//是否未出包装货区
						boolean isPackArea = "Y".equals(sers.get(i).getIsWrapArea());
						//是否有更改
						boolean isModify = "Y".equals(sers.get(i).getIsChgLabel());
						int index = serialNo%Constant.SERIALNUM==0?serialNo/Constant.SERIALNUM:serialNo/Constant.SERIALNUM+1;
						serNos[index-1] = BitUtil.setBit(serNos[index-1],serialNo%Constant.SERIALNUM==0?Constant.SERIALNUM:serialNo%Constant.SERIALNUM);
						if(isUnPacking){
							unPackingNos[index-1]=BitUtil.setBit(unPackingNos[index-1],serialNo%Constant.SERIALNUM==0?Constant.SERIALNUM:serialNo%Constant.SERIALNUM);
						}
						if(isValArea){
							valNos[index-1]=BitUtil.setBit(valNos[index-1],serialNo%Constant.SERIALNUM==0?Constant.SERIALNUM:serialNo%Constant.SERIALNUM);
						}
						if(isPackArea){
							packNos[index-1]=BitUtil.setBit(packNos[index-1],serialNo%Constant.SERIALNUM==0?Constant.SERIALNUM:serialNo%Constant.SERIALNUM);
						}
						if(isModify){
							modifyNos[index-1]=BitUtil.setBit(modifyNos[index-1],serialNo%Constant.SERIALNUM==0?Constant.SERIALNUM:serialNo%Constant.SERIALNUM);
						}
					}
					StringBuffer serNoBuf = new StringBuffer("");
					StringBuffer unPackingNoBuf = new StringBuffer("");
					StringBuffer packAreaSerNoBuf = new StringBuffer("");
					StringBuffer valAreaSerNoBuf = new StringBuffer("");
					StringBuffer modifySerNoBuf = new StringBuffer("");
					for(int i=0;i<max;i++){
						serNoBuf.append(Long.toHexString(serNos[i])).append("|");
						unPackingNoBuf.append(Long.toHexString(unPackingNos[i])).append("|");
						packAreaSerNoBuf.append(Long.toHexString(packNos[i])).append("|");
						valAreaSerNoBuf.append(Long.toHexString(valNos[i])).append("|");
						modifySerNoBuf.append(Long.toHexString(modifyNos[i])).append("|");
					}
					detail.setSerialNoStr(serNoBuf.toString());
					detail.setUnPackingStr(unPackingNoBuf.toString());
					detail.setPackAreaSerNoStr(packAreaSerNoBuf.toString());
					detail.setValAreaSerNoStr(valAreaSerNoBuf.toString());
					detail.setIsModifyStr(modifySerNoBuf.toString());
				}
				detail.setTaskCode(loadTaskDetailDto.getTaskNo());//任务号
				detail.setVolume(loadTaskDetailDto.getVolume());//体积
				detail.setTransType(loadTaskDetailDto.getTransportType());//运输性质
				detail.setWblCode(loadTaskDetailDto.getWayBillNo());//运单号
				detail.setWeight(loadTaskDetailDto.getWeight());//重量
				detail.setIsVal(loadTaskDetailDto.getIsValue());//是否贵重物品
				//detail.setChgStatus(loadTaskDetailDto.getModifyContent());//更改单提示
				//detail.setIsModify(loadTaskDetailDto.getModifyState());//是否有更改
				detail.setStockQty(loadTaskDetailDto.getStockQty());//库存件数
				detail.setPieces(loadTaskDetailDto.getWayBillQty());//开单件数
				detail.setBeJoinCar(loadTaskDetailDto.getBeJoinCar());//是否合车
				detail.setLoadPieces(loadTaskDetailDto.getOperateQty());//装车件数
				detail.setStationNumber(loadTaskDetailDto.getStationNumber());//提货网点编码
				//是否电子面单 author:245960 Date:2015-06-29
				detail.setBeEWaybill(loadTaskDetailDto.getBeEWaybill());
				details.add(detail);
			}
		}
		LOG.info(JsonUtil.encapsulateJsonObject(details));
		for (SalesDepartmentCrgDetail loadCrgDetail : details) {
			loadCrgDetail.setSerialNo(null);
		}
		LOG.info("BDM处理装车明细时间："+(System.currentTimeMillis()-start));
		return details;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:15
	 * @param qryLoadInfo
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(QrySalesDepartmentInfo qryLoadInfo) throws ArgumentInvalidException{
		Argument.notNull(qryLoadInfo, "qryLoadInfo");
		//任务号非空
		Argument.hasText(qryLoadInfo.getTaskCode(), "qryLoadInfo.taskCode");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_SALES_DEPARTMENT_RFSEXPDETAIL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
	
}
