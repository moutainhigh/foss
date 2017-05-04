package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.ISettlementPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PrepaymentEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;

/**
 * 
 * @ClassName: PrepaymentService
 * @Description: TODO(预存款刷卡--对接人ZYA)
 * @author &245955 HKB
 * @date 2016-1-27 下午4:35:19
 * 
 */
public class PrepaymentService implements
		IBusinessService<Void, PrepaymentEntity> {

	private static final Log LOG = LogFactory.getLog(PrepaymentService.class);
	private IPdaPosManageService pdaPosManageService;
	private IDeliveryPdaDao deliveryPdaDao;
	//boolean flag=true;
	private ISettlementPdaToCubcService settlementPdaToCubcService;
	private IPdaToCubcService pdaToCubcService;
	// 解析参数
	@Override
	public PrepaymentEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		PrepaymentEntity entity = JsonUtil.parseJsonToObject(
				PrepaymentEntity.class, asyncMsg.getContent());
		return entity;
	}

	// 方法入口
	@Override
	public Void service(AsyncMsg asyncMsg, PrepaymentEntity param)
			throws PdaBusiException {
		// 校验参数合法性
 		this.validate(asyncMsg, param);
		// 封装T+0报表参数
		List<PosCardEntity> entitys = wrapPosCardEntitys(asyncMsg, param);
		try {
			//封装调用灰度接口的参数
			RequestDO requestDo=new RequestDO();
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.PrepaymentService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			//根据运单号调用归属服务
			String gsService=pdaToCubcService.cubcHuiduQueryMethod(requestDo);
			if (!StringUtils.isBlank(gsService)) {
				if (gsService.equals(DeryConstant.GS_CUBC)) {
					// 封装到CUBC的参数
					PayInfoDO payInfoDO = settlementPdaToCubcService
							.wrapPosCardQueryPayInfoDO(asyncMsg, param);
					// 调用CUBC的接口
					String responseStr = pdaToCubcService
							.getAccountStatementSuccess(payInfoDO);
					PayInfoDO payInfo = JSON.parseObject(responseStr,
							PayInfoDO.class);
					String isSuccess = payInfo.getIsSuccess();
					if (isSuccess != null && isSuccess.equals("flase")) {
						// CUBC返回false,保存到异常表
						savePosCardDataCubc(param.getAccountStatementEntitys(),
								payInfo.getErrorMessage(),
								asyncMsg.getContent());
						throw new FossInterfaceException(null,
								"PDA预存款刷卡到CUBC失败!");
					}
				} else {// 默认走FOSS
						// 将数据插入到T+0报表
					LOG.info("调用foss接口开始----预存款刷卡调用T+0报表------");
					long startTime = System.currentTimeMillis();
					LOG.info("T+0报表参数" + entitys);
					pdaPosManageService.insertPosCardData(entitys);
					long endTime = System.currentTimeMillis();
					LOG.info("调用foss接口耗时:" + (endTime - startTime));
				}
			}
		} catch (BusinessException e) {
			// 保存刷卡数据异常信息
			savePosCardData(param.getAccountStatementEntitys(), e.getMessage());
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());

		} catch (Exception e) {
			// 保存刷卡数据异常信息
			savePosCardData(param.getAccountStatementEntitys(),LogUtil.logFormat(e));
			throw new FossInterfaceException(null, "出现未知异常");
		}
		return null;
	}

	// 封装参数
	private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg,
			PrepaymentEntity param) {
		List<PosCardEntity> dto = new ArrayList<PosCardEntity>();
		
		List<AccountStatementEntitys> entitys = param
				.getAccountStatementEntitys();
		// 所属模块
		for (int i = 0; i < entitys.size(); i++) {
			PosCardEntity posCardEntity = new PosCardEntity();
			if ("preDeposit".equals(entitys.get(i).getBelongModule())) {
				posCardEntity.setBelongModule("预存款");
			}
			// 流水号金额
			posCardEntity.setSerialAmount(entitys.get(i).getSerialAmount());
			// 流水号
			posCardEntity.setTradeSerialNo(entitys.get(i).getTradeSerialNo());
			// 刷卡部门编码
			posCardEntity.setCardDeptCode(entitys.get(i).getCardDeptCode());
			// 刷卡部门名称
			posCardEntity.setCardDeptName(entitys.get(i).getCardDeptName());
			// 刷卡时时间
			posCardEntity.setCardTime(entitys.get(i).getOperateTime());
			// 创建人名称
			posCardEntity.setCreateUser(entitys.get(i).getCreateUserName());
			// 创建人编码
			posCardEntity.setCreateUserCode(entitys.get(i).getCreateUserCode());
			if ("NCI_DRIVER".equals(asyncMsg.getUserType())) {
				// 是否司机
				posCardEntity.setIsDriver("true");
			} else if ("NCI_USER".equals(asyncMsg.getUserType())) {
				// 是否司机
				posCardEntity.setIsDriver("false");
			}
			// 是否快递
			posCardEntity.setIsKd("false");
			dto.add(posCardEntity);
		}

		return dto;

	}

	// 保存刷卡数据
	private void savePosCardData(List<AccountStatementEntitys> param,
			String errorCause) {
		try {
			for (int i = 0; i < param.size(); i++) {
				param.get(i).setErrorCause(errorCause);
				deliveryPdaDao.saveNCIPrepaymentCard(param.get(i));
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}

	}
	
	// 保存CUBC刷卡异常
	private void savePosCardDataCubc(List<AccountStatementEntitys> param,
			String errorCause, String content) {
		try {
			for (int i = 0; i < param.size(); i++) {
				param.get(i).setErrorCause(errorCause);
				param.get(i).setContent(content);
				deliveryPdaDao.saveNCIPrepaymentCard(param.get(i));
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	}

	// 操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_PRE_PAYMENT.VERSION;
	}

	// 异步
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 
	 * @Title: validate
	 * @Description: TODO(检验参数的合法性)
	 * @return void 返回类型
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974 wangzhili
	 */
	private void validate(AsyncMsg asyncMsg, PrepaymentEntity entity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		Argument.notNull(entity.getAccountStatementEntitys(),
				"entity.accountStatementEntitys");
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setSettlementPdaToCubcService(
			ISettlementPdaToCubcService settlementPdaToCubcService) {
		this.settlementPdaToCubcService = settlementPdaToCubcService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}
	
}
