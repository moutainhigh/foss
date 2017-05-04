package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SerialNoModel;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.LoadCrgDetail;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.QryLoadInfo;

/**
 * 刷新装车任务明细
 * 
 * @ClassName RfshLoadTaskService.java
 * @Description
 * @author 245955
 * @date 2015-4-24
 */
public class RfshLoadTaskService implements IBusinessService<List<LoadCrgDetail>, QryLoadInfo> {
	private IPDATransferLoadService pdaTransferLoadService;
	private static final Log LOG = LogFactory.getLog(RfshLoadTaskService.class);

	/**
	 * 解析包体
	 * 
	 * @description
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public QryLoadInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryLoadInfo model = JsonUtil.parseJsonToObject(QryLoadInfo.class, asyncMsg.getContent());
		return model;
	}

	/**
	 * 服务方法
	 * 
	 * @description
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public List<LoadCrgDetail> service(AsyncMsg asyncMsg, QryLoadInfo param) throws PdaBusiException {
		this.validate(param);
		List<LoadGoodsDetailDto> res = null;
		long start = System.currentTimeMillis();
		try {
			res = pdaTransferLoadService.refrushLoadTaskExpressDetail(param.getTaskCode());
			LOG.info("调用FOSS接口所需时间：" + (System.currentTimeMillis() - start));
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		start = System.currentTimeMillis();
		// Argument.notEmpty(res, "List<LoadGoodsDetailDto>");
		List<LoadCrgDetail> details = new ArrayList<LoadCrgDetail>();
		if (res != null && !res.isEmpty()) {
			String valueGoodsAreaCode = res.get(0).getValueGoodsAreaCode();
			String packGoodsAreaCode = res.get(0).getPackGoodsAreaCode();
			for (LoadGoodsDetailDto loadTaskDetailDto : res) {
				LoadCrgDetail detail = new LoadCrgDetail();
				detail.setAcctDeptCode(loadTaskDetailDto.getReceiveOrgCode());// 收货部门编号
				detail.setAcctDeptName(StringUtils.convert(loadTaskDetailDto.getReceiveOrgName()));// 收货部门名称
				detail.setArrDeptCode(loadTaskDetailDto.getReachOrgCode());// 到达部门编码
				detail.setArrDeptName(StringUtils.convert(loadTaskDetailDto.getReachOrgName()));// 到达部门名称
				detail.setCargoName(StringUtils.convert(loadTaskDetailDto.getGoodsName()));// 货物名称
				detail.setInInvtTime(loadTaskDetailDto.getStockTime());// 入库时间
				detail.setIsNessary(loadTaskDetailDto.getBePriorityGoods());// 是否必走货
				detail.setPacking(StringUtils.convert(loadTaskDetailDto.getPacking()));// 包装
				detail.setRemark(StringUtils.convert(loadTaskDetailDto.getNotes()));// 备注

				List<SerialNoModel> sers = new ArrayList<SerialNoModel>();
				// 封装流水号
				for (PDAGoodsSerialNoDto seriaDto : loadTaskDetailDto.getSerialNos()) {
					SerialNoModel ser = new SerialNoModel();
					if (seriaDto.getSerialNo() == null || seriaDto.getSerialNo().isEmpty()) {
						continue;
					}
					// 标签号
					ser.setSerialNo(seriaDto.getSerialNo());
					// 库区编号
					ser.setStockAreaCode(seriaDto.getStockAreaCode());
					// 是否未打包装
					ser.setIsWrap(seriaDto.getIsUnPacking());
					// 是否有更改
					ser.setIsChgLabel(seriaDto.getIsToDoList());
					if (valueGoodsAreaCode != null && !valueGoodsAreaCode.isEmpty()) {
						if (valueGoodsAreaCode.equals(seriaDto.getStockAreaCode())) {
							ser.setIsValArea("Y");
						} else {
							ser.setIsValArea("N");
						}
					}
					if (packGoodsAreaCode != null && !packGoodsAreaCode.isEmpty()) {
						if (packGoodsAreaCode.equals(seriaDto.getStockAreaCode())) {
							ser.setIsWrapArea("Y");
						} else {
							ser.setIsWrapArea("N");
						}
					}
					sers.add(ser);
				}
				detail.setSerialNo(sers);
				// 流水号及是否未打包装二进制
				// ------对流水号进行排序
				Collections.sort(sers, new Comparator<SerialNoModel>() {
					@Override
					public int compare(SerialNoModel o1, SerialNoModel o2) {
						if (Integer.parseInt(o1.getSerialNo()) > Integer.parseInt(o2.getSerialNo())) {
							return 1;
						} else if (Integer.parseInt(o1.getSerialNo()) == Integer.parseInt(o2.getSerialNo())) {
							return 0;
						} else {
							return -1;
						}
					}
				});
				if (sers.size() > 0) {
					// --生成流水号二进制
					// 获得最大的流水号需要多少位二进制
					int maxSer = 9999;
					try {
						maxSer = Integer.parseInt(sers.get(sers.size() - 1).getSerialNo());
					} catch (Exception e) {
						throw new FossInterfaceException(null, "运单号:" + loadTaskDetailDto.getWayBillNo() + "流水号：" + sers.get(sers.size() - 1).getSerialNo() + "不合法");
					}
					int max = maxSer % 32 == 0 ? maxSer / 32 : maxSer / 32 + 1;
					// 流水号二进制数组
					long[] serNos = new long[max];
					// 是否未打包装二进制数组
					long[] unPackingNos = new long[max];
					// 是否贵重物品货区
					long[] valNos = new long[max];
					// 是否包装货区
					long[] packNos = new long[max];
					// 是否有更改
					long[] modifyNos = new long[max];
					for (int i = 0; i < sers.size(); i++) {
						if (StringUtil.isEmpty(sers.get(i).getSerialNo())) {
							LOG.warn("流水号为空");
							continue;
						}
						int serialNo = 0;
						try {
							serialNo = Integer.parseInt(sers.get(i).getSerialNo());
						} catch (Exception e) {
							throw new FossInterfaceException(null, "运单号:" + loadTaskDetailDto.getWayBillNo() + "流水号：" + sers.get(i).getSerialNo() + "不合法");
						}
						if (serialNo == 0) {
							LOG.warn("运单号" + loadTaskDetailDto.getWayBillNo() + "存在为0000的流水号");
							continue;
						}
						// 是否未打包装
						boolean isUnPacking = "Y".equals(sers.get(i).getIsWrap());
						// 是否未出贵重物品区
						boolean isValArea = "Y".equals(sers.get(i).getIsValArea());
						// 是否未出包装货区
						boolean isPackArea = "Y".equals(sers.get(i).getIsWrapArea());
						// 是否有更改
						boolean isModify = "Y".equals(sers.get(i).getIsChgLabel());
						int index = serialNo % 32 == 0 ? serialNo / 32 : serialNo / 32 + 1;
						serNos[index - 1] = BitUtil.setBit(serNos[index - 1], serialNo % 32 == 0 ? 32 : serialNo % 32);
						if (isUnPacking) {
							unPackingNos[index - 1] = BitUtil.setBit(unPackingNos[index - 1], serialNo % 32 == 0 ? 32 : serialNo % 32);
						}
						if (isValArea) {
							valNos[index - 1] = BitUtil.setBit(valNos[index - 1], serialNo % 32 == 0 ? 32 : serialNo % 32);
						}
						if (isPackArea) {
							packNos[index - 1] = BitUtil.setBit(packNos[index - 1], serialNo % 32 == 0 ? 32 : serialNo % 32);
						}
						if (isModify) {
							modifyNos[index - 1] = BitUtil.setBit(modifyNos[index - 1], serialNo % 32 == 0 ? 32 : serialNo % 32);
						}
					}
					StringBuffer serNoBuf = new StringBuffer("");
					StringBuffer unPackingNoBuf = new StringBuffer("");
					StringBuffer packAreaSerNoBuf = new StringBuffer("");
					StringBuffer valAreaSerNoBuf = new StringBuffer("");
					StringBuffer modifySerNoBuf = new StringBuffer("");
					for (int i = 0; i < max; i++) {
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
				detail.setTaskCode(loadTaskDetailDto.getTaskNo());// 任务号
				detail.setVolume(loadTaskDetailDto.getVolume());// 体积
				detail.setTransType(loadTaskDetailDto.getTransportType());// 运输性质
				detail.setWblCode(loadTaskDetailDto.getWayBillNo());// 运单号
				detail.setWeight(loadTaskDetailDto.getWeight());// 重量
				//detail.setIsVal(loadTaskDetailDto.getIsValue());// 是否贵重物品
				// detail.setChgStatus(loadTaskDetailDto.getModifyContent());//更改单提示
				// detail.setIsModify(loadTaskDetailDto.getModifyState());//是否有更改
				detail.setStockQty(loadTaskDetailDto.getStockQty());// 库存件数
				detail.setPieces(loadTaskDetailDto.getWayBillQty());// 开单件数
				//detail.setBeJoinCar(loadTaskDetailDto.getBeJoinCar());// 是否合车
				detail.setLoadPieces(loadTaskDetailDto.getOperateQty());// 装车件数
				detail.setStationNumber(loadTaskDetailDto.getStationNumber());// 提货网点编码
				details.add(detail);
			}
		}
		LOG.info(JsonUtil.encapsulateJsonObject(details));
		for (LoadCrgDetail loadCrgDetail : details) {
			loadCrgDetail.setSerialNo(null);
		}
		LOG.info("BDM处理装车明细时间：" + (System.currentTimeMillis() - start));
		return details;
	}

	/**
	 * 操作类型
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public String getOperType() {
		 return LoadConstant.OPER_TYPE_LOAD_RFSH_TRAN.VERSION;
	}

	/**
	 * 是否异步
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 参数验证
	 * 
	 * @description
	 * @param qryLoadInfo
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-24
	 */
	private void validate(QryLoadInfo qryLoadInfo) throws ArgumentInvalidException {
		Argument.notNull(qryLoadInfo, "qryLoadInfo");
		// 任务号非空
		Argument.hasText(qryLoadInfo.getTaskCode(), "qryLoadInfo.taskCode");
	}

	public void setPdaTransferLoadService(IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
}
