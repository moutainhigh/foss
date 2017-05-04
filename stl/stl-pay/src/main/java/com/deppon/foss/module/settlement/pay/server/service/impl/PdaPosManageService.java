package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPdaPosManageDao;

/**
 * POS机刷卡管理实现类
 * 
 * @ClassName: PdaPosManageService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-17 下午1:45:57
 */
public class PdaPosManageService implements IPdaPosManageService {
	// 返回默认值
	private static final String isSuccess = "1";
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(PdaPosManageService.class);

	/**
	 * 注入Dao
	 */
	private IPdaPosManageDao pdaPosManageDao;

	/**
	 * 复杂组织信息
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 提供公用接口给PDA插入数据
	 * 
	 * @param list
	 *            ：刷卡数据和明细数据一起
	 * @Title: insertPosCardData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * 
	 */
	@Override
	//@Transactional
	public String insertPosCardData(List<PosCardEntity> list) {
		logger.info("*****PDA封装数据到T+0报表开始********");
		if (list == null || list.size() == 0) {
			throw new SettlementException("数据错误：插入数据时传入参数为空！");
		}

		// 批量处理
		for (PosCardEntity posCardEntity : list) {
			// 获取单个实体类
			PosCardEntity entity = posCardEntity;
			// 插入T+0和明细数据(总的实现接口)
			this.insertPdaPosCardAndDetail(entity);
		}
		logger.info("*****PDA封装数据到T+0报表结束********");
		return isSuccess;
	}

	/**
	 * 插入T+0和明细数据(总的实现接口)
	 * 
	 * @Title: insertPosAndDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-12 下午5:19:36
	 */
	@Transactional
	@Override
	public String insertPdaPosCardAndDetail(PosCardEntity entity) {
		// 是否为司机字段和是否为快递字段不能为空
		if (StringUtil.isBlank(entity.getIsDriver())
				&& StringUtil.isBlank(entity.getIsKd())) {
			throw new SettlementException("数据错误：请设置isDriver或isKd字段值！");
		}

		/**
		 * 所属模块不能为空
		 */
		if (StringUtil.isBlank(entity.getBelongModule())) {
			throw new SettlementException("数据错误：所属模块不能为空！");
		}

		// 单条插入刷卡报表
		/**
		 * 获取明细中任意一个单据号，如果为司机的话要查询到达应收部门 如果是快递传入数据，也要查询到达应收部门
		 * 如果为预存款模块，司机和经营人员不用拿到单据号
		 */
		String invoiceNo = "";
		if (!SettlementDictionaryConstants.NCI_DEPOSIT.equals(entity
				.getBelongModule())) {
			if ("true".equals(entity.getIsDriver())
					|| "true".equals(entity.getIsKd())) {
				invoiceNo = entity.getPosCardDetailEntitys().get(0)
						.getInvoiceNo();
			}
		}

		/**
		 * 快递插入数据的话，假如存在了，不允许重新插入
		 */

		List<PosCardEntity> lists = new ArrayList<PosCardEntity>();
		// 构造查询参数
		PosCardManageDto dto = new PosCardManageDto();
		dto.setTradeSerialNo(entity.getTradeSerialNo());
		lists = pdaPosManageDao.queryPosCardData(dto);

		if (lists.size() == 0) {
			/**
			 * 插入T+0
			 */
			this.insertPosCardMessage(entity, invoiceNo);

			/**
			 * 假如是快递话，明细数据的单据未核销金额和已使用流水号金额与零担的金额是不同的
			 */
			String isKd = entity.getIsKd();

			/**
			 * 不同模块的话，明细数据中的单据未核销金额和已使用流水号金额也不一样
			 */
			String belongModule = entity.getBelongModule();

			// 插入明细表
			this.insertPosCardDetail(entity, isKd,
					belongModule);
		}
		return isSuccess;
	}

	/**
	 * POS 插入POS刷卡数据（单条）
	 * 
	 * @Title: insertPosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            :POS刷卡产生的数据
	 * @param invoiceNo
	 *            ： 结清货款模块的时候要查询到达应收，拿到明细中任意一个单据号
	 */
	@Override
	public String insertPosCardMessage(PosCardEntity entity, String invoiceNo) {
		logger.info("*************插入流水号信息开始***********");
		if (entity == null) {
			throw new SettlementException("参数错误：传入参数为空！");
		}
		// 设置参数
		setPosCardEntityParam(entity, invoiceNo);
		// 校验参数
		validateParam(entity);
		// 插入数据
		try {
			pdaPosManageDao.addPosCardMessage(entity);
		} catch (RuntimeException e) {
			throw new SettlementException("数据异常,数据库中已存在！");
		}
		logger.info("*************插入流水号信息结束***********");
		return isSuccess;
	}

	/**
	 * 插入数据之前封装一下数据
	 * 
	 * @param invoincNo
	 *            单据号
	 * 
	 * @Title: setPosCardEntityParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void setPosCardEntityParam(PosCardEntity entity, String invoiceNo) {
		//
		logger.info("****************封装参数*********************");
		/**
		 * 如果不是司机,需要去判断刷卡部门编码是否为空！ 如果是司机，而且是预存款模块，也要去判断
		 */
		if ("false".equals(entity.getIsDriver())
				|| !SettlementDictionaryConstants.NCI_DEPOSIT.equals(entity
						.getBelongModule())) {
			/**
			 * 假如为快递模块，而且不是司机，那么不需要去判断刷卡部门
			 */
			if("false".equals(entity.getIsKd())){
				// 刷卡部门编码
				if (StringUtil.isBlank(entity.getCardDeptCode())) {
					throw new SettlementException("数据错误：刷卡部门编码不能为空！");
				}
			}
			
			/**
			 * 假如是快递，而且是开单模块，那么要校验刷卡部门编码
			 */
			if("true".equals(entity.getIsKd())&& "true".equals(entity.getIsbilling())){
				// 刷卡部门编码
				if (StringUtil.isBlank(entity.getCardDeptCode())) {
					throw new SettlementException("数据错误：刷卡部门编码不能为空！");
				}
			}
		}
		/**
		 * 如果是司机，而且是结清货款模块,需要去查询达到应收部门 假如为快递:如果对应单据为现付，查询出发部门名称和编码
		 * 如果对应单据为到付，查询到达部门名称和编码 快递的业务只有出发部门或者只有到达部门
		 */
		// 判断是司机还是经营部门
		if (("true".equals(entity.getIsDriver()) && SettlementDictionaryConstants.NCI_SETTLE
				.equals(entity.getBelongModule()))
				|| ("true".equals(entity.getIsKd()) && SettlementDictionaryConstants.NCI_KD
						.equals(entity.getBelongModule())&&"false".equals(entity.getIsbilling()))) {
			// 司机对应结清货款模块
			// 根据对应单据号获取到达应收部门
			PosCardDetailEntity detail = new PosCardDetailEntity();
			detail.setInvoiceNo(invoiceNo);

			/**
			 * 查询应收单，获取对应单据号的到达应收部门或始发部门 快递和零担统一sql
			 */
			PosCardEntity posCardEntity = pdaPosManageDao
					.queryDRORDeptByWaybillNo(detail);

			/**
			 * 假如数据为空，则抛出异常！
			 */
			if (posCardEntity == null) {
				throw new SettlementException("数据错误：传入单号不存在到达应收部门,单号为："
						+ invoiceNo);
			}
			// 设置刷卡部门名称
			entity.setCardDeptCode(posCardEntity.getCardDeptCode());
			// 设置刷卡部门编码
			entity.setCardDeptName(posCardEntity.getCardDeptName());
		}

		/**
		 * 设置类型List
		 */
		List<String> bizTypes = new ArrayList<String>();
		// 设置类型集合
		bizTypes.add("SALES_DEPARTMENT");// 营业部
		// 调用接口获取数据
		OrgAdministrativeInfoEntity entityInfo = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(entity.getCardDeptCode(),
						bizTypes);
		if (entityInfo == null) {
			throw new SettlementException("数据错误：部门信息不存在！");
		}
		// 刷卡部门名称
		String cardDeptName = entityInfo.getName();
		entity.setCardDeptName(cardDeptName);
		// 获取刷卡部门的组织标杆编码
		String cardDeptBMCode = entityInfo.getUnifiedCode();

		// 获取营业区名称：上级组织名称
		String businessAreaName = entityInfo.getParentOrgName();

		// 获取营业区编码：上级组织编码
		String businessAreaCode = entityInfo.getParentOrgCode();

		// 获取营业区标杆编码：上级组织标杆编码
		String businessAreaBMCode = entityInfo.getParentOrgUnifiedCode();

		// 再次调用
		/**
		 * 设置营业区的bizTypes
		 */
		bizTypes.clear();// 清空所有的值
		// 重新设置类型
		bizTypes.add("SMALL_REGION");
		// bizTypes.add("BIG_REGION");
		// 根据营业区去获取上级部门
		OrgAdministrativeInfoEntity entityInfo2 = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(businessAreaCode, bizTypes);

		// 获取大区名称：上级组织名称
		String belongRegionName = null;

		// 获取大区编码：上级组织编码
		String belongRegionCode = null;

		// 获取大区标杆编码：上级组织标杆编码
		String belongRegionBMCode = null;

		/**
		 * 假如刷卡部门对应的是派送中心，那么派送中心和营业区是一个性质的。他的上级就是大区，这里是和营业部不同的地方
		 * 那么如果在去获取上一级的名称和编码，则对应的大区则是事业部了，这样的数据显然不对，而且用小区去查询，获取不到
		 * 数据，所以加入获取不到数据的时候，那么就知道对应的刷卡部门为派送中心，则：
		 * 
		 * 那么这时候直接设置大区为对应派送中心的大区， 营业区为当前派送中心 营业部为当前派送中心
		 */

		if (entityInfo2 == null) {
			// 获取营业区名称：上级组织名称
			businessAreaName = entity.getCardDeptName();

			// 获取营业区编码：上级组织编码
			businessAreaCode = entity.getCardDeptCode();

			// 获取营业区标杆编码：上级组织标杆编码
			businessAreaBMCode = cardDeptBMCode;

			// 获取大区名称：上级组织名称
			belongRegionName = entityInfo.getParentOrgName();

			// 获取大区编码：上级组织编码
			belongRegionCode = entityInfo.getParentOrgCode();

			// 获取大区标杆编码：上级组织标杆编码
			belongRegionBMCode = entityInfo.getParentOrgUnifiedCode();
		} else {
			// 获取大区名称：上级组织名称
			belongRegionName = entityInfo2.getParentOrgName();

			// 获取大区编码：上级组织编码
			belongRegionCode = entityInfo2.getParentOrgCode();

			// 获取大区标杆编码：上级组织标杆编码
			belongRegionBMCode = entityInfo2.getParentOrgUnifiedCode();
		}

		/**
		 * 封装参数
		 */
		
		// 刷卡部门标杆编码
		entity.setCardDeptBMCode(cardDeptBMCode);
		// 所属营业区名称
		entity.setBusinessAreaName(businessAreaName);
		// 所属营业区编码
		entity.setBusinessAreaCode(businessAreaCode);
		// 所属营业区标杆编码
		entity.setBusinessAreaBMCode(businessAreaBMCode);
		// 所属大区名称
		entity.setBelongRegionName(belongRegionName);
		// 所属大区编码
		entity.setBelongRegionCode(belongRegionCode);
		// 所属大区标杆编码
		entity.setBelongRegionBMCode(belongRegionBMCode);
		// 所属营业部
		entity.setBusinessDeptName(entity.getCardDeptName());
		// 所属营业部编码
		entity.setBusinessDeptCode(entity.getCardDeptCode());
		// 所属营业部标杆编码
		entity.setBusinessDeptBMCode(cardDeptBMCode);

		/**
		 * 快递和零担的已使用流水号金额和未使用金额的设置不一样。要重新设置
		 */
		/**
		 * 假如是快递，而且是开单模块，那么要校验刷卡部门编码
		 */
	
//		if ("true".equals(entity.getIsKd())) {
//			// 设置已使用流水号金额:交易流水号金额
//			entity.setUsedAmount(entity.getSerialAmount());
//			// 设置未使用金额:0
//			entity.setUnUsedAmount(BigDecimal.ZERO);
//		} else {
			// 设置已使用流水号金额:0
			entity.setUsedAmount(BigDecimal.ZERO);
			// 设置未使用金额:交易流水号金额
			entity.setUnUsedAmount(entity.getSerialAmount());
		if("true".equals(entity.getIsKd())&& "true".equals(entity.getIsbilling())){
			// 设置已使用流水号金额:交易流水号金额
			entity.setUsedAmount(entity.getSerialAmount());
//				// 设置未使用金额:0
			entity.setUnUsedAmount(BigDecimal.ZERO);
		}
//		}
	}

	/**
	 * FOSS 单条插入POS刷卡明细数据
	 * 
	 * @Title: addPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public String addPosCardDetail(PosCardDetailEntity entity) {
		logger.info("*******单条插入明细数据开始*******");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 封装数据
		List<PosCardDetailEntity> list = new ArrayList<PosCardDetailEntity>();
		list.add(entity);
		// 校验数据
		validateDetailParam(list);
		// 插入明细
		pdaPosManageDao.addPosCardDetail(entity);
		logger.info("*******单条插入明细数据结束*******");
		return isSuccess;
	}

	/**
	 * POS 批量插入明细：提供给PDA
	 * 
	 * @ClassName: insertPosCardDetail
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-17 下午1:45:57
	 */
	@Override
	public String insertPosCardDetail(PosCardEntity entity,
			String isKd, String belongModule) {
		List<PosCardDetailEntity> list = entity.getPosCardDetailEntitys();
		logger.info("*************插入明细数据开始***********");
		if (list == null) {
			return "";
		}

		// 设置参数
		setDetailParam(entity, isKd, belongModule);
		
		// 校验参数
		validateDetailParam(list);
		// 批量插入数据
		pdaPosManageDao.addPosCardDetail(list);
		logger.info("*************插入明细数据结束***********");
		return isSuccess;
	}

	/**
	 * POS 批量插入明细：提供给FOSS
	 * 
	 * @ClassName: insertPosCardDetail
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-17 下午1:45:57
	 */
	@Override
	public String insertPosCardDetail(List<PosCardDetailEntity> list) {
		logger.info("*************插入明细数据开始***********");
		if (list == null) {
			return "";
		}

		// 设置参数
		// setDetailParam(list,isKd);
		// 校验参数
		validateDetailParam(list);
		pdaPosManageDao.addPosCardDetail(list);
		logger.info("*************插入明细数据结束***********");
		return isSuccess;
	}

	/**
	 * 封装明细数据
	 * 
	 * @param isKd
	 *            判断 是否是快递 true
	 * @Title: setDetailParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void setDetailParam(PosCardEntity entity, String isKd,
			String belongMoudle) {
		List<PosCardDetailEntity> list = entity.getPosCardDetailEntitys();
		// 批量设置数据
		for (PosCardDetailEntity detail : list) {
			if ("true".equals(isKd)) {
				// 设置已使用流水号金额
				detail.setOccupateAmount(BigDecimal.ZERO);
				// 设置单据未核销金额:0
				detail.setUnVerifyAmount(detail.getAmount());
				// 设置单据类型为W3
				detail.setInvoiceType("W3");
				if ("true".equals(entity.getIsKd()) && "true".equals(entity.getIsbilling())) {
					// 设置已使用流水号金额
					detail.setOccupateAmount(detail.getAmount());
					// 设置单据未核销金额:0
					detail.setUnVerifyAmount(BigDecimal.ZERO);
					// 设置单据类型为W3
					detail.setInvoiceType("W3");
				}
			} else if (SettlementDictionaryConstants.NCI_STATEMENT
					.equals(belongMoudle)) {
				/**
				 * 对账单模块根据单据号获取未核销金额
				 */
				PosCardEntity posCardEntity = pdaPosManageDao
						.queryUnpaidAmountByNo(detail.getInvoiceNo());
				if (posCardEntity == null) {
					throw new SettlementException("单号：" + detail.getInvoiceNo()
							+ ",已被核销完成或未确认！");
				}
				// 获取未还金额
				BigDecimal unpaidAmount = posCardEntity.getUnVerifyAmount();
				// 设置已使用流水号金额:0
				detail.setOccupateAmount(BigDecimal.ZERO);

				// 设置单据未核销金额:单据未还金额
				detail.setUnVerifyAmount(unpaidAmount);
				
				// 设置单据类型为DZ
				detail.setInvoiceType("DZ");
			} else if (SettlementDictionaryConstants.NCI_SETTLE
					.equals(belongMoudle)) {
				/**
				 * 结清货款模块根据单据号获取未核销金额
				 */
				PosCardEntity posCardEntity = pdaPosManageDao
						.queryUnverifyAmountByNo(detail.getInvoiceNo());
				if (posCardEntity == null) {
					throw new SettlementException("单号：" + detail.getInvoiceNo()
							+ ",已被核销完成或已作废！");
				}
				// 获取未还金额
				BigDecimal unpaidAmount = posCardEntity.getUnVerifyAmount();
				// 设置已使用流水号金额:0
				detail.setOccupateAmount(BigDecimal.ZERO);

				// 设置单据未核销金额:单据未核销金额
				detail.setUnVerifyAmount(unpaidAmount);
				
				// 设置单据类型为W3
				detail.setInvoiceType("W2");
			} else if(SettlementDictionaryConstants.NCI_WAYBILL.equals(belongMoudle)) {
				/**
				 * 代刷卡足额核销，不需要获取未核销金额
				 */
				// 设置已使用流水号金额:0
				detail.setOccupateAmount(BigDecimal.ZERO);
				// 设置单据未核销金额:单据总金额
				detail.setUnVerifyAmount(detail.getAmount());
				// 设置单据类型为W3
				detail.setInvoiceType("W1");
			}
		}
	}

	/**
	 * 单条更新POS刷卡数据 T+0 根据交流流水号去更新已使用流水号金额和未使用金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public String updatePosCardMessageAmount(PosCardEntity entity) {
		logger.info("******单条更新刷卡数据开始********");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 交易流水号不能为空
		if (StringUtil.isBlank(entity.getTradeSerialNo())) {
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		// 未使用金额不能为null
		if (entity.getUnUsedAmount() == null) {
			throw new SettlementException("数据错误：未使用金额不能为null");
		}
		// 已使用流水号金额不能为null
		if (entity.getUsedAmount() == null) {
			throw new SettlementException("数据错误：已使用流水号金额不能为null");
		}
		// 更新数据
		int result = pdaPosManageDao.updatePosCardMessageAmount(entity);
		if(result !=1){
			throw new SettlementException("更新条数不为1");
		}
		logger.info("******单条更新刷卡数据结束********");
		return isSuccess;
	}

	/**
	 * 根据交易流水号和部门编码去查询POS刷卡数据
	 * 
	 * @Title: queryPosCardData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param dto
	 *            :invoiceNo 和 tradeSerialNo
	 */
	@Override
	public PosCardManageDto queryPosCardData(PosCardManageDto dto) {
		logger.info("********根据单据号和交易流水号查询POS刷卡数据开始********");
		// 判断参数是否为空
		if (dto == null) {
			throw new SettlementException("数据错误：查询传入参数为空！");
		}
		if (StringUtil.isBlank(dto.getTradeSerialNo())) {
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		List<PosCardEntity> list = pdaPosManageDao.queryPosCardData(dto);
		dto.setPosCardEntitys(list);
		logger.info("********根据单据号和交易流水号查询POS刷卡数据结束********");
		return dto;
	}

	/**
	 * 单条更新明细(根据单据号和交易流水号更新明细中交易流水号金额和单据未核销金额)
	 * 
	 * @Title: updateSinglePosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */

	@Override
	public String updateSinglePosCardDetail(PosCardDetailEntity entity) {
		logger.info("******POS管理，更新明细数据开始********");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 单据号不能为空
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号不能为空！");
		}
		// 已使用流水号金额不能为null
		if (entity.getOccupateAmount() == null) {
			throw new SettlementException("数据错误：已使用流水号金额不能为null！");
		}
		// 未核销金额不能为null
		/*
		 * if(entity.getOccupateAmount() == null){ throw new
		 * SettlementException("数据错误：未核销金额不能为null！"); }
		 */
		// 更新数据
		pdaPosManageDao.updateSinglePosCardDetail(entity);
		logger.info("******POS管理，更新明细数据结束********");
		return isSuccess;
	}

	/**
	 * 根据交易流水号去更新T+0报表已使用流水号金额和未使用金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public String updatePosCardByNum(PosCardDetailEntity entity) {
		logger.info("*******根据单据号去更新T+0报表开始*********");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 单据号不能为空
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号不能为空！");
		}
		// 已使用流水号金额不能为null
		if (entity.getOccupateAmount() == null) {
			throw new SettlementException("数据错误：已使用流水号金额不能为null！");
		}
		// 更新数据
		pdaPosManageDao.updatePosCardByNumber(entity);
		logger.info("*******根据单据号去更新T+0报表结束*********");
		return isSuccess;
	}

	/**
	 * 校验明细数据
	 * 
	 * @Title: validateDetailParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void validateDetailParam(List<PosCardDetailEntity> list) {
		// 批量校验
		for (PosCardDetailEntity detailEntity : list) {
			// 交易流水号
			if (StringUtil.isBlank(detailEntity.getTradeSerialNo())) {
				throw new SettlementException("数据错误：交易流水号不能为空！");
			}
			// 单据类型
			if (StringUtil.isBlank(detailEntity.getInvoiceType())) {
				throw new SettlementException("数据错误：单据类型不能为空！");
			}
			// 单据号
			if (StringUtil.isBlank(detailEntity.getInvoiceNo())) {
				throw new SettlementException("数据错误：单据号不能为空！");
			}
		}
	}

	/**
	 * 校验参数
	 * 
	 * @Title: validateParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void validateParam(PosCardEntity entity) {
		// 交易流水号
		if (StringUtil.isBlank(entity.getTradeSerialNo())) {
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		// 流水号金额
		if (entity.getSerialAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
			throw new SettlementException("数据错误：流水号金额不能为0！");
		}

		// 刷卡部门名称
		if (StringUtil.isBlank(entity.getCardDeptName())) {
			throw new SettlementException("数据错误：刷卡部门名称不能为空！");
		}
		// 刷卡部门编码
		// if (StringUtil.isBlank(entity.getCardDeptCode())) {
		// throw new SettlementException("数据错误：刷卡部门编码不能为空！");
		// }
		// 刷卡部门标杆编码
		if (StringUtil.isBlank(entity.getCardDeptBMCode())) {
			throw new SettlementException("数据错误：刷卡部门标杆编码不能为空！");
		}
		// 刷卡时间
		if (entity.getCardTime() == null) {
			throw new SettlementException("数据错误：刷卡时间能不能为空！");
		}
		// 所属营业区
		if (StringUtil.isBlank(entity.getBusinessAreaName())) {
			throw new SettlementException("数据错误：所属营业区名称不能为空！");
		}
		// 所属营业区编码
		if (StringUtil.isBlank(entity.getBusinessAreaCode())) {
			throw new SettlementException("数据错误：所属营业区编码不能为空！");
		}
		// 所属营业区标杆编码
		if (StringUtil.isBlank(entity.getBusinessAreaCode())) {
			throw new SettlementException("数据错误：所属营业区标杆编码不能为空！");
		}
		validaExtracted(entity);
	}

	private void validaExtracted(PosCardEntity entity) {
		// 所属大区
		if (StringUtil.isBlank(entity.getBelongRegionName())) {
			throw new SettlementException("数据错误： 所属大区名称不能为空！");
		}
		// 所属大区编码
		if (StringUtil.isBlank(entity.getBelongRegionName())) {
			throw new SettlementException("数据错误： 所属大区编码不能为空！");
		}
		// 所属大区标杆编码
		if (StringUtil.isBlank(entity.getBelongRegionName())) {
			throw new SettlementException("数据错误： 所属大区标杆编码不能为空！");
		}
		// 所属营业部
		if (StringUtil.isBlank(entity.getBusinessDeptName())) {
			throw new SettlementException("数据错误： 所属营业部名称不能为空！");
		}
		// 所属营业部编码
		if (StringUtil.isBlank(entity.getBusinessDeptCode())) {
			throw new SettlementException("数据错误： 所属营业部编码不能为空！");
		}

		// 创建人名称
		if (StringUtil.isBlank(entity.getCreateUser())) {
			throw new SettlementException("数据错误： 创建人名称不能为空！");
		}
		// 创建人编码
		if (StringUtil.isBlank(entity.getCreateUserCode())) {
			throw new SettlementException("数据错误： 创建人编码不能为空！");
		}
		/*// 已使用流水号金额
		if (entity.getUsedAmount() == null) {
			throw new SettlementException("数据错误： 已使用流水号金额不能为空！");
		}
		// 未使用金额
		if (entity.getUnUsedAmount() == null) {
			throw new SettlementException("数据错误： 未使用金额不能为空！");
		}*/
	}

	/**
	 * 校验
	 * 
	 * @Title: validate
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public void validate(PosCardDetailEntity entity) {
		// 交易流水号
		if (StringUtil.isBlank(entity.getTradeSerialNo())) {
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		// 单据号
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号不能为空！");
		}
		// 金额必须大于零
		if (entity.getAmount().compareTo(BigDecimal.ZERO) > 1) {
			throw new SettlementException("数据错误：金额必须大于零！");
		}

	}

	/**
	 * 根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据
	 * 
	 */
	@Override
	public String insertOrUpdateDetail(PosCardDetailEntity detail) {
		logger.info("根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据开始");
		if (detail == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 校验
		validate(detail);
		// 插入或更新数据
		pdaPosManageDao.insertOrUpdateDetail(detail);
		logger.info("根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据结束");
		return isSuccess;
	}

	/******************** 业务异常处理 ************************/
	/**
	 * 作废还款单 作废小票 作废预收单 作废明细数据，释放T+0中的已使用流水号金额和未使用金额
	 *  参数：单据号,单据类型
	 * 
	 * @Title: updatePosAndDeleteDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public String updatePosAndDeleteDetail(PosCardDetailEntity entity) {
		logger.info("反结清 作废还款单 作废小票 作废预收单 作废明细数据");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号为空！");
		}
		/**
		 * 根据单据号去查询明细数据，多个明细
		 * 
		 * 获取交易流水号和已使用流水号金额(明细中的数据)， 循环去释放交易流水号中的已使用金额
		 */
		List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(entity);
		// 循环释放
		for (PosCardDetailEntity posCardDetailEntity : lists) {
			// 1、获取明细实体，得到交易流水号和已使用流水号金额
			PosCardDetailEntity detail = posCardDetailEntity;
			
			/**
			 * 设置更新人
			 */
			detail.setModifyUser(entity.getModifyUser());
			detail.setModifyUserCode(entity.getModifyUserCode());
			/**
			 * 已使用流水号金额大于0
			 */
			if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO)>0){
				/**
				 * 要释放的金额为明细中的已使用流水号金额
				 */
				posCardDetailEntity.setAmount(posCardDetailEntity
						.getOccupateAmount());
				 
				/**
				 * 2、根据交易流水号更新已使用流水号金额和未使用金额,释放的明细中的已使用流水号金额
				 */
				pdaPosManageDao.updatePosByNoForMoney(posCardDetailEntity);
				
				/**
				 * 3、根据单据号去释放明细已使用流水号金额，
				 * 明细中的已使用流水号金额全部被释放完成，那么修改已使用金额为0
				 */
				detail.setOccupateAmount(BigDecimal.ZERO);
				pdaPosManageDao.updateSinglePosCardDetail(detail);
			}
			 
			// 更新一个，释放一个 删除明细，将明细isDelete 置为Y 单号和交易流水号去删除
			pdaPosManageDao.deleteDetailByNo(detail);
		}
		logger.info("反结清 作废还款单 作废小票 作废预收单 作废明细数据");
		return isSuccess;
	}

	/**
	 * 异常处理 --根据单据号去查询明细数据
	 * 
	 * @ClassName: queryDetailsByNo
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-17 下午1:45:57
	 */
	@Override
	public List<PosCardDetailEntity> queryPosDetailsByNo(PosCardDetailEntity entity) {
		logger.info("***********根据单据号去查询明细开始************");
		/*if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号不存在！");
		}*/
		// 查询明细
		List<PosCardDetailEntity> list = pdaPosManageDao.queryDetailsByNo(entity);
		logger.info("***********根据单据号去查询明细结束************");
		return list;
	}

	/**
	 * 反核销---修改对账单总金额，释放已使用流水号金额
	 * 
	 * @Title: handleReverseBillWriterOff
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:00
	 */
	@Override
	public String handleReverseBillWriterOff(PosCardDetailEntity entity) {
		logger.info("反核销---修改对账单总金额，释放已使用流水号金额开始");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号为空！");
		}
		if (entity.getOccupateAmount().compareTo(BigDecimal.ZERO) > 1) {
			throw new SettlementException("数据错误：金额必须大于0！");
		}
		/**
		 * 根据单据号去查询明细数据，多个明细
		 * 
		 */
		List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(entity);
		//获取已还款金额
		BigDecimal repaymentAmount = entity.getOccupateAmount();
		// 循环释放
		for (PosCardDetailEntity posCardDetailEntity : lists) {
			// 获取明细实体，得到交易流水号和已使用流水号金额
			PosCardDetailEntity detail = posCardDetailEntity;
			/**
			 * 设置更新人
			 */
			detail.setModifyUser(entity.getModifyUser());
			detail.setModifyUserCode(entity.getModifyUserCode());
			
			/**
			 * 获取已使用流水号金额
			 */
			BigDecimal usedAmount = detail.getOccupateAmount();
			
			//已使用流水号金额大于0
			if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) >0 ){
				/**
				 * 获取当前的已使用流水号和反核销的金额作比较，假如还款金额大于当前已使用流水号金额，则释放当前已使用流水号金额
				 */
				 if(repaymentAmount.compareTo(detail.getOccupateAmount()) > 0 ){
					// 修改已还款金额
					repaymentAmount = repaymentAmount.subtract(detail.getOccupateAmount());
					
					// 根据交易流水号更新已使用流水号金额和未使用金额，释放的金额为已使用流水号金额
					pdaPosManageDao.updatePosByNoForMoney(detail);
					 
					/**
					 * 根据交易流水号和单据号 释放明细中的已使用流水号金额
					 */ 
					detail.setOccupateAmount(BigDecimal.ZERO);
					pdaPosManageDao.updateSinglePosCardDetail(detail);
					
					
					if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
						/**
						 * 作废明细
						 */
						pdaPosManageDao.deleteDetailByNo(detail);
					}
				} else
				/**
				 * 假如还款金额小于当前已使用流水号金额，则释放还款金额
				 */
				if(repaymentAmount.compareTo(detail.getOccupateAmount()) <= 0){
					//已使用流水号金额  = 当是已还款金额
					detail.setOccupateAmount(repaymentAmount);
					
					// 根据交易流水号更新已使用流水号金额和未使用金额
					pdaPosManageDao.updatePosByNoForMoney(detail);
					
					/**
					 * 根据交易流水号和单据号 释放明细中的已使用流水号金额
					 */ 
					detail.setOccupateAmount(usedAmount.subtract(detail.getOccupateAmount()));
					
					/**
					 * 更新明细已使用流水号金额
					 */
					pdaPosManageDao.updateSinglePosCardDetail(detail);
					
					/**
					 * 假如明细的已使用流水号金额为0 则删除明细数据
					 */
					if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
						/**
						 * 作废明细
						 */
						pdaPosManageDao.deleteDetailByNo(detail);
					}
					
					/**
					 * 反核销金额已经全部释放，则反核销金额等于0
					 */
					repaymentAmount = BigDecimal.ZERO;
				} 
				//假如等于0则退出循环
				if(repaymentAmount.compareTo(BigDecimal.ZERO) == 0){
					break;
				}
			}
		}
		logger.info("反核销---修改对账单总金额，释放已使用流水号金额结束");
		return isSuccess;
	}
	
	/**
	 * 作废还款单 
	 * 假如一个交易流水号对应的多个单号，要依次释放流水号金额
	 * 
	 * @Title: disableRepayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:00
	 */
	@Override
	public String disableRepayment(PosCardDetailEntity entity) {
		logger.info("作废还款单---释放已使用流水号金额开始");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		/*if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号为空！");
		}*/
		if (entity.getAmount().compareTo(BigDecimal.ZERO) > 1) {
			throw new SettlementException("数据错误：金额必须大于0！");
		}
		/**
		 * 根据单据号和交易流水号去查询明细数据，多个明细
		 */
		List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(entity);
		// 获取已还款金额
		BigDecimal repaymentAmount = entity.getAmount();
		// 循环释放
		for (PosCardDetailEntity posCardDetailEntity : lists) {
			// 获取明细实体，得到交易流水号和已使用流水号金额
			PosCardDetailEntity detail = posCardDetailEntity;
			/**
			 * 设置更新人
			 */
			detail.setModifyUser(entity.getModifyUser());
			detail.setModifyUserCode(entity.getModifyUserCode());
			
			/**
			 * 假如还款金额大于已使用流水号金额
			 */
			if(repaymentAmount.compareTo(detail.getOccupateAmount())>0){
				// 修改已还款金额
				repaymentAmount = repaymentAmount.subtract(detail.getOccupateAmount());
				
				// 根据交易流水号更新已使用流水号金额和未使用金额，释放的金额为已使用流水号金额
				pdaPosManageDao.updatePosByNoForMoney(detail);
				 
				/**
				 * 根据交易流水号和单据号 释放明细中的已使用流水号金额
				 */ 
				detail.setOccupateAmount(BigDecimal.ZERO);
				pdaPosManageDao.updateSinglePosCardDetail(detail);
				
				
				if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
					/**
					 * 作废明细
					 */
					pdaPosManageDao.deleteDetailByNo(detail);
				}
			}else 
			
			/**
			 * 假如还款金额小于当前已使用流水号金额，则释放还款金额
			 */
			if(repaymentAmount.compareTo(detail.getOccupateAmount()) <= 0){
				// 获取明细中的已使用流水号金额，暂存
				BigDecimal usedAmount = detail.getOccupateAmount();
				
				// 已使用流水号金额  = 当是已还款金额   
				detail.setOccupateAmount(repaymentAmount);
				
				// 根据交易流水号更新已使用流水号金额和未使用金额
				pdaPosManageDao.updatePosByNoForMoney(detail);
				
				/**
				 * 根据单据号和交易流水号去释放明细金额 
				 * 
				 * 明细的已使用流水号金额 = 已使用流水号  - 还款金额 
				 */
				detail.setOccupateAmount(usedAmount.subtract(repaymentAmount));
				
				pdaPosManageDao.updateSinglePosCardDetail(detail);
				
				/**
				 * 假如明细的已使用流水号金额为0 则删除明细数据
				 */
				if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
					/**
					 * 作废明细
					 */
					pdaPosManageDao.deleteDetailByNo(detail);
				}
				/**
				 * 金额已释放，设置还款金额为0
				 */
				repaymentAmount = BigDecimal.ZERO;
			}
			//假如等于0 则退出循环
			if(repaymentAmount.compareTo(BigDecimal.ZERO) == 0){
				break;
			}
		}
		logger.info("作废还款单---释放已使用流水号金额结束");
		return isSuccess;
	}
	
	/**
	 * 修改对账单总金额
	 * 
	 * @Title: updateStatementAmout
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:34:53
	 */
	@Override
	public String updateStatementAmout(PosCardDetailEntity entity) {
		logger.info("反核销---修改对账单总金额，释放已使用流水号金额开始");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号为空！");
		}
		if (entity.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new SettlementException("数据错误：金额必须大于0！");
		}
		
		/**
		 * 根据单据号修改对账单总金额
		 */
		pdaPosManageDao.updateStatementAmount(entity);
		
		/**
		 * 释放预收单，预付单，应付单，对账单总金额增加，则不会调用到下面的接口
		 * 
		 * 1、释放应收单数据之后，对账单总金额为0，则要修改T+0报表已使用流水号金额，
		 * 			释放明细金额，然后删除明细
		 * 2、释放应收单之后，对账单金额不为0，则要修改T+0报表的已使用流水号金额，
		 * 			释放已使用流水号金额
		 */
		if(entity.getAmount().compareTo(BigDecimal.ZERO) == 0){
			this.updatePosAndDeleteDetail(entity);
		}
		if("true".equals(entity.getIsflag())){
			/**
			 * 应收单释放，修改对账单总金额，对应明细金额要释放
			 * 根据单据号去查询明细数据，多个明细
			 * 
			 * 获取交易流水号和已使用流水号金额(明细中的数据)， 循环去释放交易流水号中的已使用金额
			 */
			/**
			 * 反核销了多少钱，这里就释放多少钱
			 */
			BigDecimal reverseAmount = entity.getOccupateAmount();
			
			List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(entity);
			
			// 循环释放
			for (PosCardDetailEntity posCardDetailEntity : lists) {
				// 获取明细实体，得到交易流水号和已使用流水号金额
				PosCardDetailEntity detail = posCardDetailEntity;
				/**
				 * 设置更新人
				 */
				detail.setModifyUser(entity.getModifyUser());
				detail.setModifyUserCode(entity.getModifyUserCode());
				
				/**
				 * 获取金额，然后下面要使用
				 */
				BigDecimal userdAmount = detail.getOccupateAmount();
				
				/**
				 * 假如反核销的钱大于已使用流水号金额
				 */
				/**
				 * 获取当前的已使用流水号和反核销的金额作比较，假如还款金额大于当前已使用流水号金额，则释放当前已使用流水号金额
				 */
				if(reverseAmount.compareTo(detail.getOccupateAmount()) > 0 ){
					// 修改反核销金额 = 反核销金额  - 当前明细的已使用流水号金额
					reverseAmount = reverseAmount.subtract(detail.getOccupateAmount());
					
					/**
					 *  根据交易流水号更新已使用流水号金额和未使用金额，
					 *  修改的金额为明细中的已使用流水号金额
					 */
					pdaPosManageDao.updatePosByNoForMoney(detail);
					 
					/**
					 * 根据交易流水号和单据号 释放明细中的已使用流水号金额
					 */ 
					detail.setOccupateAmount(userdAmount.subtract(detail.getOccupateAmount()));
					
					/**
					 * 释放明细中的金额
					 */
					pdaPosManageDao.updateSinglePosCardDetail(detail);
					
					/**
					 * 假如明细的已使用流水号金额为0 则删除明细数据
					 */
					if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
						//作废明细
						pdaPosManageDao.deleteDetailByNo(detail);
					}
				}else 
				
				/**
				 * 释放相等的金额
				 */
				if(reverseAmount.compareTo(detail.getOccupateAmount()) <= 0){
					//已使用流水号金额  = 当是已还款金额
					detail.setOccupateAmount(reverseAmount);
					// 根据交易流水号更新已使用流水号金额和未使用金额
					pdaPosManageDao.updatePosByNoForMoney(detail);
					/**
					 * 根据交易流水号和单据号 释放明细中的已使用流水号金额,
					 * 释放相等金额之后，已使用流水号金额 = 总的已使用流水号金额 - 当前释放的单据的已使用流水号金额
					 */
					detail.setOccupateAmount(userdAmount.subtract(detail.getOccupateAmount()));
					
					pdaPosManageDao.updateSinglePosCardDetail(detail);
					
					/**
					 * 假如明细的已使用流水号金额为0 则删除明细数据
					 */
					if(detail.getOccupateAmount().compareTo(BigDecimal.ZERO) == 0){
						//作废明细
						pdaPosManageDao.deleteDetailByNo(detail);
					}
					
					/**
					 * 释放完成之后，设置反核销金额为0
					 */
					reverseAmount = BigDecimal.ZERO;
				}
				
				//假如等于0 则退出循环
				if(reverseAmount.compareTo(BigDecimal.ZERO) == 0){
					break;
				}
				
			}
		}
		logger.info("反核销---修改对账单总金额，释放已使用流水号金额开始");
		return isSuccess;
	}
	
	/**
	 * 释放数据
	 * 
	 * @Title: updateExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午2:02:07
	 */
	@Override
	@Transactional
	public String updateExceptionData(PosCardDetailEntity detail){
		logger.info("修改异常数据开始");
		if(StringUtils.isEmpty(detail.getTradeSerialNo())){
			throw new SettlementException("交易流水号为空！");
		}
		if(StringUtils.isEmpty(detail.getInvoiceNo())){
			throw new SettlementException("单据号为空。");
		}
		if(detail.getAmount() == null){
			throw new SettlementException("金额不能为空！");
		}
		//设置已使用流水号金额 
		detail.setOccupateAmount(detail.getAmount());
		
		/**
		 * 释放流水号金额
		 */
		pdaPosManageDao.updateExceptionDataByNum(detail);
		
		/**
		 * 设置单据类型
		 */
		//detail.setInvoiceType("W3");
		List<PosCardDetailEntity> list = pdaPosManageDao.queryDetailsByNo(detail);
		if(list.size() == 0){
			throw new SettlementException("数据不存在,请检查交易流水号或者单号是否正确！");
		}
		/**
		 * 查询明细数据,根据交易流水号和单据号,定位一条明细数据
		 */
		PosCardDetailEntity pde = list.get(0);
		
		if(pde.getOccupateAmount().compareTo(detail.getAmount())<0){
			throw new SettlementException("已使用流水号金额小于释放金额，请检查数据！");
		}
		/**
		 * 假如释放的金额和明细已使用金额相等，则删除明细，否则修改明细金额
		 */
		if(detail.getAmount().compareTo(pde.getOccupateAmount()) == 0){
			//删除明细
			pdaPosManageDao.deleteDetailByNo(detail);
		}else{
			/**
			 * 已使用金额  = 已使用金额  - 释放的金额
			 */
			detail.setOccupateAmount(pde.getOccupateAmount().subtract(detail.getAmount()));
			pdaPosManageDao.updateSinglePosCardDetail(detail);
		}
		logger.info("修改异常数据结束");
		return isSuccess;
	}
	
	/**
	 * 删除手动导入的异常流水数据(此接口暫時只提供給POS机刷卡管理页面使用)
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午2:02:07
	 */
	@Override
	public String deleteExceptionData(PosCardEntity posCardEntity) {
		logger.info("********pda删除手动导入的异常流水数据开始!********");
		if(null == posCardEntity || null == posCardEntity.getTradeSerialNo()){
			throw new SettlementException("交易流水号为空!");
		}
		if(!SettlementDictionaryConstants.NCI_SETTLE.equals(posCardEntity.getBelongModule())){
			throw new SettlementException("需要删除的数据必须是通过[POS机刷卡异常管理]界面导入的!");
		}
		if(!posCardEntity.getUnUsedAmount().subtract(posCardEntity.getSerialAmount()).equals(new BigDecimal(0))){
			throw new SettlementException("被使用的交易流水号不可以删除!");
		}
		int count = pdaPosManageDao.deleteExceptionData(posCardEntity);
		int min_size = 1 ;
		if(count != min_size){
			throw new SettlementException("删除失败!");
		}
		logger.info("********删除手动导入的异常流水数据结束!********");
		return isSuccess;
	}
	
	/**
	 * POS机刷卡管理--更新冻结状态
	 *
	 * @Title: frozenPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	@Transactional
	public String updatePosCardEntitys(List<PosCardEntity> checkQueryResult) {
		if(CollectionUtils.isEmpty(checkQueryResult)){
			throw new SettlementException("需要冻结/取消冻结的参数不能为空!");
		}
		int count = 0 ;
		for (PosCardEntity posCardEntity : checkQueryResult) {
			int resoult = pdaPosManageDao.updatePosCardEntitys(posCardEntity);
			if(resoult != 1){
				throw new SettlementException("POS机刷卡管理交易流水号"+posCardEntity.getTradeSerialNo()+"冻结/取消冻结失败!");
			}
			count++; 
		}
		logger.info("冻结/取消冻结的数据条数:"+count+"条");
		if(checkQueryResult.size() != count || count == 0 ){
			throw new SettlementException("更新冻结/取消冻结的数据与需要更新的数据不一致,更新失败!");
		}
		return isSuccess;
	}
	
	/**
	 * 作废T+0
	 */
	@Override
	public String updatePosCardByParam(String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("tradeSerialNo", serialNo);
		map.put("notes", "FOSS灰度，将数据转移到cubc");
		map.put("modifyUser", "CUBC");
		map.put("modifyUserCode", "CUBC");
		int count = pdaPosManageDao.cancelPosCardbyParam(map);
		if(count != 1){
			throw new SettlementException("作废失败!");
		}
		return "true";
	}
	/************ getter/setter ************/
	public void setPdaPosManageDao(IPdaPosManageDao pdaPosManageDao) {
		this.pdaPosManageDao = pdaPosManageDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	

}
