package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadCrgDetail;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SerialNoModel;

public class ForceLoadScanService implements
		IBusinessService<LoadCrgDetail, LoadScanEntity> {
	//private static final Logger logger = Logger.getLogger(ForceLoadScanService.class);
	private IPDATransferLoadService pdaTransferLoadService;

	@Override
	public LoadScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LoadScanEntity entity = JsonUtil.parseJsonToObject(
				LoadScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Override
	public LoadCrgDetail service(AsyncMsg asyncMsg, LoadScanEntity param)
			throws PdaBusiException {
		// 校验数据合法性
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		// 保存装车扫描信息
		LoadScanDetailDto entity = new LoadScanDetailDto();
		entity.setTaskNo(param.getTaskCode());
		entity.setWayBillNo(param.getWblCode());
		entity.setSerialNo(param.getLabelCode());
		entity.setDeviceNo(asyncMsg.getPdaCode());
		entity.setScanTime(param.getScanTime());
		entity.setType(param.getScanStatus());
		entity.setScanState(param.getScanFlag());
		entity.setVolume(param.getVolume());
		entity.setWeight(param.getWeight());
		entity.setGoodsName(param.getCargoName());
		entity.setBeJoinCar(param.getBeJoinCar());
		entity.setStockQty(param.getStockQty());
		entity.setPack(param.getIsWrap());
		entity.setTransportType(param.getTransType());
		entity.setReceiveOrgName(param.getAcctDeptName());
		entity.setReachOrgName(param.getArrDeptName());
		try {
			// 调用FOSS接口
			LoadGoodsDetailDto loadTaskDetailDto = pdaTransferLoadService.moreGoodsLoadScan(entity);
			LoadCrgDetail detail = new LoadCrgDetail();
			if(loadTaskDetailDto != null){
				String valueGoodsAreaCode = loadTaskDetailDto.getValueGoodsAreaCode();
				String packGoodsAreaCode = loadTaskDetailDto.getPackGoodsAreaCode();
				detail.setAcctDeptCode(loadTaskDetailDto.getReceiveOrgCode());//收货部门编号
				/*
				 * wwn 3013-05-31 15:51
				 * */
				detail.setAcctDeptName(StringUtils.convert(loadTaskDetailDto.getReceiveOrgName()));//收货部门名称
				detail.setArrDeptCode(loadTaskDetailDto.getReachOrgCode());//到达部门编码
				/*
				 * wwn 3013-05-31 15:51
				 * */
				detail.setArrDeptName(StringUtils.convert(loadTaskDetailDto.getReachOrgName()));//到达部门名称
				detail.setCargoName(loadTaskDetailDto.getGoodsName());//货物名称
				detail.setInInvtTime(loadTaskDetailDto.getStockTime());//入库时间
				detail.setIsNessary(loadTaskDetailDto.getBePriorityGoods());//是否必走货
				/*
				 * wwn 3013-05-31 15:51
				 * */
				detail.setPacking(StringUtils.convert(loadTaskDetailDto.getPacking()));//包装
				/*
				 * wwn 3013-05-31 15:51
				 * */
				detail.setRemark(StringUtils.convert(loadTaskDetailDto.getNotes()));//备注
				List<SerialNoModel> sers = new ArrayList<SerialNoModel>();
				//封装流水号
				for (PDAGoodsSerialNoDto seriaDto : loadTaskDetailDto.getSerialNos()) {
					SerialNoModel ser = new SerialNoModel();
					ser.setIsWrap(seriaDto.getIsUnPacking());//是否未打包装
					ser.setSerialNo(seriaDto.getSerialNo());//流水号
					ser.setStockAreaCode(seriaDto.getStockAreaCode());//库区编号
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
					//ser.setIsChgLabel(seriaDto.getHasToDoList());
					sers.add(ser);
				}
				detail.setSerialNo(sers);
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
			}
			return detail;
		} catch (BusinessException e) {
			String errMsg = e.getErrorCode();
			if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPACKAGE.equals(errMsg)){
				errMsg = "该货物未包装";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_MODIFY.equals(errMsg)){
				errMsg = "该货物有更改";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_PACKAGE_UNOUT_STORAGE.equals(errMsg)){
				errMsg = "该货物未出包装货区";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_VALUABLES_UNOUT_STORAGE.equals(errMsg)){
				errMsg = "该货物未出贵重物品货区";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPREWIRED.equals(errMsg)){
				errMsg = "该货物未预备";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_INVALID.equals(errMsg)){
				errMsg = "该标签无效";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA.equals(errMsg)){
				errMsg = "该货物不允许装车";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED.equals(errMsg)){
				errMsg = "该货物为夹带货物";
			}else if(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED.equals(errMsg)){
				errMsg = "该货物为异地夹带货物";
			}
			throw new FossInterfaceException(e.getCause(),errMsg);
		}
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:12
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(LoadScanEntity entity)
			throws ArgumentInvalidException {
		// 检验扫描非空
		Argument.notNull(entity, "loadScanEntity");
		// 标签号非空
		Argument.hasText(entity.getLabelCode(), "loadScanEntity.labelCode");
		// 任务号非空
		Argument.hasText(entity.getTaskCode(), "loadScanEntity.taskCode");
		// 扫描时间非空
		Argument.notNull(entity.getScanTime(), "loadScanEntity.scanTime");
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_CHECK_LOAD.VERSION;
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
