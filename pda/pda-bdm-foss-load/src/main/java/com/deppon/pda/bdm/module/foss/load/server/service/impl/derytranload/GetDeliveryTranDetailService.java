package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryDeliveryTaskEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.GetDeliveryDetailResult;

/**
 * 下拉派件交接明细
 * @ClassName GetDeliveryTranDetailService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-16
 */
public class GetDeliveryTranDetailService implements IBusinessService<List<GetDeliveryDetailResult>, DeryDeliveryTaskEntity> {
	private static final Log LOG = LogFactory.getLog(GetDeliveryTranDetailService.class);
	
	private IPdaDeliverTaskService pdaDeliverTaskService;
	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public DeryDeliveryTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DeryDeliveryTaskEntity getDeryTask=new DeryDeliveryTaskEntity();
		getDeryTask=JsonUtil.parseJsonToObject(DeryDeliveryTaskEntity.class,asyncMsg.getContent());
		return getDeryTask;
	}

	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public List<GetDeliveryDetailResult> service(AsyncMsg asyncMsg, DeryDeliveryTaskEntity param) throws PdaBusiException {
		LOG.info(param);
		List<PdaDeliverHandTaskDto> pdaDeliverHandTaskDtos = null;
		List<GetDeliveryDetailResult> getDeliverDetailResult = null;
		// 参数验证
		this.validate(asyncMsg, param);
		try {
			if(param.getTallyerCode().equals(asyncMsg.getUserCode())){
				throw new BusinessException("不能获取自己的派送任务!");
			}else{	
				//根据司机工号,车牌号,任务号查询送货任务接口  
				pdaDeliverHandTaskDtos = pdaDeliverTaskService.getDeliverHandTaskList(param.getTaskCode(),param.getTallyerCode(),param.getTruckCode());
				//封装实体
				getDeliverDetailResult = wrapGetDeryTaskResLists(pdaDeliverHandTaskDtos);
				LOG.info(pdaDeliverHandTaskDtos);
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}	
		return getDeliverDetailResult;
	}
	
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param getCreateDeliverDetailRes
	 * @return
	 */
	private List<GetDeliveryDetailResult> wrapGetDeryTaskResLists(List<PdaDeliverHandTaskDto> pdaDeliverHandTaskDtos){
		List<GetDeliveryDetailResult> getDeryResultList = new ArrayList<GetDeliveryDetailResult>();
		GetDeliveryDetailResult  getTaskResult = null;
		if (pdaDeliverHandTaskDtos != null && !pdaDeliverHandTaskDtos.isEmpty()) {
			System.out.println("返回大小"+pdaDeliverHandTaskDtos.size());
			
			for (PdaDeliverHandTaskDto pdaDeliverHandTaskDto : pdaDeliverHandTaskDtos) {
				
				List<PdaDeliverHandTaskDetailsDto> deliverHandTaskDetailsDtos = pdaDeliverHandTaskDto.getDeliverHandTaskDetailsDtos();			
				
				if (deliverHandTaskDetailsDtos != null && !deliverHandTaskDetailsDtos.isEmpty()) {
									
					for (PdaDeliverHandTaskDetailsDto pdaDeliverHandTaskDetailsDto : deliverHandTaskDetailsDtos) {	
						
						getTaskResult=new GetDeliveryDetailResult();
						//派送单号
						getTaskResult.setDeryCode(pdaDeliverHandTaskDto.getDeliverbillNo());
						//运单号
						getTaskResult.setWblCode(pdaDeliverHandTaskDetailsDto.getWaybillNo());
						//重量
						getTaskResult.setWeight(pdaDeliverHandTaskDetailsDto.getWeight());
						
						List<String> serias = pdaDeliverHandTaskDetailsDto.getSerialNum();
						// 流水号及是否未打包装二进制
						// ------对流水号进行排序
						Collections.sort(serias, new Comparator<String>() {
							@Override
							public int compare(String o1, String o2) {
								if (Integer.parseInt(o1) > Integer.parseInt(o2)) {
									return 1;
								} else if (Integer.parseInt(o1) == Integer.parseInt(o2)) {
									return 0;
								} else {
									return -1;
								}
							}
						});
						if (serias.size() > 0) {
							// --生成流水号二进制
							// 获得最大的流水号需要多少位二进制
							int maxSer = 9999;
							try {
								maxSer = Integer.parseInt(serias.get(serias.size() - 1));
							} catch (Exception e) {
								throw new FossInterfaceException(null, "运单号:" + pdaDeliverHandTaskDetailsDto.getWaybillNo() + "流水号：" 
										+ serias.get(serias.size() - 1) + "不合法");
							}
							int max = maxSer % Constant.SERIALNUM == 0 ? maxSer / Constant.SERIALNUM : maxSer / Constant.SERIALNUM + 1;
							// 流水号二进制数组
							long[] serNos = new long[max];
							for (int i = 0; i < serias.size(); i++) {
								if (StringUtil.isEmpty(serias.get(i))) {
									LOG.warn("流水号为空");
									continue;
								}
								int serialNo = 0;
								try {
									serialNo = Integer.parseInt(serias.get(i));
								} catch (Exception e) {
									throw new FossInterfaceException(null, "运单号:" + pdaDeliverHandTaskDetailsDto.getWaybillNo() + "流水号：" 
											+ serias.get(i) + "不合法");
								}
								if (serialNo == 0) {
									LOG.warn("运单号" + pdaDeliverHandTaskDetailsDto.getWaybillNo() + "存在为0000的流水号");
									continue;
								}
								int index = serialNo % Constant.SERIALNUM == 0 ? serialNo / Constant.SERIALNUM : serialNo / Constant.SERIALNUM + 1;
								serNos[index - 1] = BitUtil.setBit(serNos[index - 1], serialNo % Constant.SERIALNUM == 0 ? Constant.SERIALNUM : serialNo % Constant.SERIALNUM);
							}
							StringBuffer serNoBuf = new StringBuffer("");
							for (int i = 0; i < max; i++) {
								serNoBuf.append(Long.toHexString(serNos[i])).append("|");
							}
							getTaskResult.setLabelCodes(serNoBuf.toString());
						}
						
						//体积
						getTaskResult.setVolume(pdaDeliverHandTaskDetailsDto.getVolume());
						//到达联件数
						getTaskResult.setSumPieces(pdaDeliverHandTaskDetailsDto.getArriveSheetGoodsQty());
						//类型
						getTaskResult.setType(pdaDeliverHandTaskDetailsDto.getProductName());
						//已扫件数
						getTaskResult.setScanQty(pdaDeliverHandTaskDetailsDto.getScanQty());
						//扫描状态
						getTaskResult.setScanState(pdaDeliverHandTaskDetailsDto.getScanState());
						getDeryResultList.add(getTaskResult);
					}
				}
			}
		}
		return getDeryResultList;
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY__TASK_DETAIL_GET.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	/**
	 * 参数验证
	 */
	private void validate(AsyncMsg asyncMsg, DeryDeliveryTaskEntity getDeryTask){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//车牌号
		Argument.hasText(getDeryTask.getTruckCode(), "DeryDeliveryTaskEntity.truckCode");
	}

	public void setPdaDeliverTaskService(
			IPdaDeliverTaskService pdaDeliverTaskService) {
		this.pdaDeliverTaskService = pdaDeliverTaskService;
	}
	
}

	

