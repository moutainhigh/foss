package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeryCrgDetailEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetDeryTaskEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetDeryTaskResEntity;

/**
 * 获取派送明细
 * @ClassName KdGetDeryTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-15
 */
public class KdGetDeryTaskService  implements IBusinessService<List<GetDeryTaskResEntity>, GetDeryTaskEntity> {
	private static final Log LOG = LogFactory.getLog(KdGetDeryTaskService.class);

	private IPdaDeliverTaskService pdaDeliverTaskService;

	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public GetDeryTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetDeryTaskEntity getDeryTask=new GetDeryTaskEntity();
		getDeryTask=JsonUtil.parseJsonToObject(GetDeryTaskEntity.class,asyncMsg.getContent());
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
	 * @date 2015-4-15
	 */
	@Override
	public List<GetDeryTaskResEntity> service(AsyncMsg asyncMsg, GetDeryTaskEntity param) throws PdaBusiException {
		LOG.info(param);
		List<PdaDeliverTaskDto> pdaDeliverTaskDtos = null;
		List<GetDeryTaskResEntity> getDeryDeliverDetail = null;
		try {
			// 参数验证
			this.validate(asyncMsg, param);
			//根据司机工号、车牌号查询送货任务接口
			pdaDeliverTaskDtos = pdaDeliverTaskService.getExpressDeliverTaskList(
					asyncMsg.getUserCode(), param.getTruckCode());
			//封装实体
			getDeryDeliverDetail = wrapGetDeryTaskResLists(pdaDeliverTaskDtos);
			LOG.info(pdaDeliverTaskDtos);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}	
		return getDeryDeliverDetail;
	}
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param getCreateDeliverDetailRes
	 * @return
	 */
	private List<GetDeryTaskResEntity> wrapGetDeryTaskResLists(List<PdaDeliverTaskDto> pdaDeliverTaskDtos){
		List<GetDeryTaskResEntity> getDeryTaskRess = new ArrayList<GetDeryTaskResEntity>();
		GetDeryTaskResEntity getDeryTaskResEntity = null;
		if (pdaDeliverTaskDtos != null && !pdaDeliverTaskDtos.isEmpty()) {
			System.out.println("返回大小"+pdaDeliverTaskDtos.size());
			
			for (PdaDeliverTaskDto pdaDeliverTaskDto : pdaDeliverTaskDtos) {
				List<PdaDeliverTaskDetailsDto> deliverTaskDetailsDtos = pdaDeliverTaskDto.getDeliverTaskDetailsDtos();
				getDeryTaskResEntity = new GetDeryTaskResEntity();
				if (deliverTaskDetailsDtos != null && !deliverTaskDetailsDtos.isEmpty()) {
					//派送单号
					getDeryTaskResEntity.setDeryCode(pdaDeliverTaskDto.getDeliverbillNo());
					System.out.println("派送单号： "+pdaDeliverTaskDto.getDeliverbillNo());
					
					List<DeryCrgDetailEntity> details = new ArrayList<DeryCrgDetailEntity>();
					DeryCrgDetailEntity deryCrgDetail = null;
					for (PdaDeliverTaskDetailsDto pdaDeliverTaskDetailsDto : deliverTaskDetailsDtos) {
						deryCrgDetail = new DeryCrgDetailEntity();
						//到付金额
						deryCrgDetail.setAmount(pdaDeliverTaskDetailsDto.getToPayAmount()!=null?pdaDeliverTaskDetailsDto.getToPayAmount().doubleValue():0);
						//客户地址
						deryCrgDetail.setCustomerAddress(StringUtils.convert(pdaDeliverTaskDetailsDto.getReceiveCustomerAddress()));
						//客户名称
						deryCrgDetail.setCustomerName(StringUtils.convert(pdaDeliverTaskDetailsDto.getReceiveCustomerName()));
						//客户手机号
						deryCrgDetail.setCustomerPhone(pdaDeliverTaskDetailsDto.getReceiveCustomerMobilephone());
						//日期
						deryCrgDetail.setDeryDate(pdaDeliverTaskDetailsDto.getSubmitTime());
						//是否贵重物品
						deryCrgDetail.setIsVal(pdaDeliverTaskDetailsDto.getPreciousGoods());
						//代收货款
						deryCrgDetail.setPaymentAmt(pdaDeliverTaskDetailsDto.getCodAmount()!=null?pdaDeliverTaskDetailsDto.getCodAmount().doubleValue():0);
						//件数
						deryCrgDetail.setPieces(pdaDeliverTaskDetailsDto.getArriveSheetGoodsQty());
						//备注
						deryCrgDetail.setRemark(StringUtils.convert(pdaDeliverTaskDetailsDto.getNotes()));
						// TOTO 无体积返回给客户端
						deryCrgDetail.setVolume(pdaDeliverTaskDetailsDto.getVolume()!=null?pdaDeliverTaskDetailsDto.getVolume().doubleValue():0); 
						//运单号
						deryCrgDetail.setWblCode(pdaDeliverTaskDetailsDto.getWaybillNo());
						//重量
						deryCrgDetail.setWeight(pdaDeliverTaskDetailsDto.getWeight()!=null?pdaDeliverTaskDetailsDto.getWeight().doubleValue():0);
						//包装
						deryCrgDetail.setWrapType(pdaDeliverTaskDetailsDto.getGoodsPackage());
						//流水号
						deryCrgDetail.setLabelCodes(pdaDeliverTaskDetailsDto.getSerialNum());
						//到达联
						deryCrgDetail.setArriveSheetNo(pdaDeliverTaskDetailsDto.getArriveSheetNo());
						//是否采集GPS地址
						deryCrgDetail.setIsCollectGps(pdaDeliverTaskDetailsDto.getIsCollect());
						//是否派送延时
						deryCrgDetail.setIsDeryOvertime(pdaDeliverTaskDetailsDto.getIsDeryOvertime());
						//投诉变更状态
						deryCrgDetail.setComplainStatus(pdaDeliverTaskDetailsDto.getComplainStatus());
						if(pdaDeliverTaskDetailsDto.getTotalMoney()!=null){
						    //总金额.
						    deryCrgDetail.setTotalCost(BigDecimal.valueOf(pdaDeliverTaskDetailsDto.getTotalMoney().doubleValue()).toString());
						}else{
							deryCrgDetail.setTotalCost("");
						}					
						//是否返货
						deryCrgDetail.setIsRetrunGoods(pdaDeliverTaskDetailsDto.getIsNewWaybillNo());
						if(pdaDeliverTaskDetailsDto.getOldReceiveablePayAmoout()!=null){
						//关联单号费用
						    deryCrgDetail.setRelBillFee(BigDecimal.valueOf(pdaDeliverTaskDetailsDto.getOldReceiveablePayAmoout().doubleValue()).toString());											
						}else{
							deryCrgDetail.setRelBillFee("");
						}
						//出发部门
						deryCrgDetail.setStartDeptCode(pdaDeliverTaskDetailsDto.getReceiveOrgCode());					
						//发货人地址
						deryCrgDetail.setSendAddress(pdaDeliverTaskDetailsDto.getDeliveryCustomerAddress());
						//签收单返单类型
						deryCrgDetail.setReceiptType(pdaDeliverTaskDetailsDto.getReturnBillType());
						//是否开定额发票的字段
						//author:245960 Date:2015-06-19 comment:需求编号：DN201503300026  加一个“是否开定额发票的字段”
						//deryCrgDetail.setArriveCentralizedSettlement(pdaDeliverTaskDetailsDto.getArriveCentralizedSettlement());
						//开单总件数  author:245955  Date:2015-09-17  加一个"开单总件数"
						deryCrgDetail.setGoodsQtyTotal(pdaDeliverTaskDetailsDto.getGoodsQtyTotal());
						//母件单号  author:245955 Date:2015-09-11
						deryCrgDetail.setParentWaybillNo(pdaDeliverTaskDetailsDto.getFemaleWaybillNo());	
						//子母件总件数  author:245955 Date:2015-09-11
						deryCrgDetail.setTwoInOneQty(pdaDeliverTaskDetailsDto.getTwoInOneQty());
						details.add(deryCrgDetail);
						}
					getDeryTaskResEntity.setDeryCrgDetails(details);
				}
				getDeryTaskRess.add(getDeryTaskResEntity);
			}
		}
		return getDeryTaskRess;
	}
	
	/**
	 * 验证数据有效性
	 * @param asyncMsg
	 * @param getDeryTask
	 */
	private void validate(AsyncMsg asyncMsg, GetDeryTaskEntity getDeryTask){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		Argument.notNull(getDeryTask, "GetDeryTaskEntity");
		//车牌号
		Argument.hasText(getDeryTask.getTruckCode(), "GetDeryTaskEntity.truckCode");
	}
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_KDDERY_TASK_GET.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDeliverTaskService(
			IPdaDeliverTaskService pdaDeliverTaskService) {
		this.pdaDeliverTaskService = pdaDeliverTaskService;
	}
	
}
