package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.express;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;

/**
 * 快递正常签收 - 插入 T+0  刷卡数据
 * 作者 ： 314587	 LiuLiPeng
 * 更新时间： 2016-06-09 15:08:19
 */
public class KdSwipingCardDetailService implements IBusinessService<String, AccountStatementEntitys>{
	private static final Log log = LogFactory.getLog(KdSwipingCardDetailService.class);

	private IPdaPosManageService pdaPosManageService;
	
	
	@Override
	public AccountStatementEntitys parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		log.info("**************解析包体-开始*************");
		AccountStatementEntitys result = JSON.parseObject(asyncMsg.getContent(), AccountStatementEntitys.class);
		log.info("**************解析包体-结束*************");
		return result;
	}

	@Override
	public String service(AsyncMsg asyncMsg, AccountStatementEntitys param) throws PdaBusiException {
		log.info("**************开始执行服务方法*************");
		//校验参数
		this.validate(asyncMsg, param);
		//调用FOSS接口 插入T+0 开始
		List<PosCardEntity> posList = this.wrapPosCardEntitys(asyncMsg, param);
		String result = pdaPosManageService.insertPosCardData(posList);
		if(!result.equals("1")){
			throw new  RuntimeException("插入T+0失败！");
		}
		//调用FOSS接口 插入T+0 结束
		log.info("**************结束执行服务方法*************");
		return result;
	}

	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_SWIPING_CARD_DETAIL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	
	// 封装参数
	private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg,AccountStatementEntitys accountStatement) {
		List<PosCardEntity> dto = new ArrayList<PosCardEntity>();
		PosCardEntity posCardEntity = new PosCardEntity();
		
		List<PosCardDetailEntity> details=new ArrayList<PosCardDetailEntity>();
		PosCardDetailEntity detail = null;
		
		// 所属模块 固定位快递
		posCardEntity.setBelongModule("快递");
		// 流水号金额
		posCardEntity.setSerialAmount(accountStatement.getSerialAmount());
		// 流水号
		posCardEntity.setTradeSerialNo(accountStatement.getTradeSerialNo());
		// 刷卡部门编码 刷卡部门固定为空
		posCardEntity.setCardDeptCode("");
		// 刷卡部门名称 刷卡部门固定为空
		posCardEntity.setCardDeptName("");
		// 刷卡时时间
		posCardEntity.setCardTime(accountStatement.getOperateTime());
		// 创建人名称
		posCardEntity.setCreateUser(accountStatement.getCreateUserName());
		// 创建人编码
		posCardEntity.setCreateUserCode(accountStatement.getCreateUserCode());
		// 单据类型 固定位 运单
		posCardEntity.setInvoiceType("运单");
		//是否开单 true 开单 false 签收
		posCardEntity.setIsbilling("false");
		
		if ("COURIER".equals(asyncMsg.getUserType())) {
			// 是否司机
			posCardEntity.setIsDriver("false"); 
		} else {
			// 是否司机
			posCardEntity.setIsDriver("true");
		}
		// 是否快递
		posCardEntity.setIsKd("true");
		//明细集合
		posCardEntity.setPosCardDetailEntitys(accountStatement.getPosCardDetailEntitys());
		
		
		for (int i = 0; i < accountStatement.getPosCardDetailEntitys().size(); i++) {
			detail = new PosCardDetailEntity();
			// 单据类型 固定位  运单
			detail.setInvoiceType("运单");
			//单据号
			detail.setInvoiceNo(accountStatement.getPosCardDetailEntitys().get(i).getInvoiceNo());
			//总金额
			detail.setAmount(accountStatement.getPosCardDetailEntitys().get(i).getAmount());
			//交易流水号
			detail.setTradeSerialNo(accountStatement.getPosCardDetailEntitys().get(i).getTradeSerialNo());
			//每次刷卡明细 数据 都加到 POS刷卡明细
			details.add(detail);
		}
		
		posCardEntity.setPosCardDetailEntitys(details);
		dto.add(posCardEntity);
		return dto;
	}
	
	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param normPcSignScan
	 */
	private void validate(AsyncMsg asyncMsg, AccountStatementEntitys accountStatementEntitys){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//PDA编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		//所属模块
//		Argument.hasText(accountStatementEntitys.getBelongModule(),"accountStatementEntitys.belongModule");
		//刷卡部门编码
//		Argument.hasText(accountStatementEntitys.getCardDeptCode(),"accountStatementEntitys.cardDeptCode");
		//刷卡部门名称
//		Argument.hasText(accountStatementEntitys.getCardDeptName(),"accountStatementEntitys.cardDeptName");
		//创建人编码
		Argument.hasText(accountStatementEntitys.getCreateUserCode(),"accountStatementEntitys.createUserCode");
		//创建人名称
		Argument.hasText(accountStatementEntitys.getCreateUserName(),"accountStatementEntitys.createUserName");
		//明细集合
		Argument.notEmpty(accountStatementEntitys.getPosCardDetailEntitys(),"accountStatementEntitys.posCardDetailEntitys");
		
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

}
