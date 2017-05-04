package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCostCenterDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRentCarReportDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IRentCarReportService;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityDetailService;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.RentCarReportVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.fssc.inteface.domain.accrued.CreateAccruedWorkflowRequest;
import com.deppon.fssc.inteface.domain.accrued.CreateAccruedWorkflowResponse;
import com.deppon.fssc.inteface.domain.accrued.ExpenseDetail;
import com.deppon.fssc.remote.foss.fossaccruedservice.CommonException;
import com.deppon.fssc.remote.foss.fossaccruedservice.FossAccruedService;

/**
 * @author 045738
 * 临时租车service
 */
public class RentCarReportService implements IRentCarReportService {
	
	private static final Logger logger = LogManager.getLogger(RentCarReportService.class);

	/**
	 * 注入临时租车Dao
	 */
	private IRentCarReportDao RentCarReportDao;
	
	/**
	 * 注入组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 注入部门信息
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 预提service
	 */
	private IWithholdingEntityService withholdingEntityService;
	
	/**
	 * 预提明细service
	 */
	private IWithholdingEntityDetailService withholdingEntityDetailService;
	
	/**
	 * 注入结算公共服务
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入中转作废临时租车接口
	 */
	private ITemprentalMarkService temprentalMarkService;
	
	/**
	 * 调用成本中心service
	 */
	private ICommonCostCenterDeptService commonCostCenterDeptService;
	
	/**
	 * 注入预提ws
	 */
	private FossAccruedService fossAccruedService;
	
	/**
	 * 注入service
	 */
	private IRentCarReportService rentCarReportService;
	
	private String formatType = "yyyy-MM";
	
	/**
	 * 传递给FSSC值
	 */
	private static String EMPTY_DESC = "无";
	/**
	 * 功能：查询临时租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-17
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReport(RentCarReportDto dto,
			CurrentInfo info ,int start, int limit) {
		//校验查询参数
		validateParams(dto);
		dto.setEmpCode(info.getEmpCode());//设置当前登陆人，数据权限使用
		//查询有效租车记录
		dto.setActive(FossConstants.ACTIVE);
		List<RentCarReportDto> list = RentCarReportDao.queryRentCarReport(dto, start, limit);
				
		//查询临时租车信息
		return getRegions(list);
	}
	/**
	 * 功能：查询临时租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-17
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForExport(RentCarReportDto dto,CurrentInfo info) {
		//校验查询参数
		validateParams(dto);
		dto.setEmpCode(info.getEmpCode());//设置当前登陆人，数据权限使用
		//查询有效租车记录
		dto.setActive(FossConstants.ACTIVE);
		List<RentCarReportDto> list = RentCarReportDao.queryRentCarReportForExport(dto);
		//查询临时租车信息
		return getRegions(list);
	}
	
	/**
	 * 功能：查询临时租车报表汇总记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	public RentCarReportDto queryRentCarReportCount(RentCarReportDto dto,CurrentInfo info) {
		//校验查询参数
		validateParams(dto);
		dto.setEmpCode(info.getEmpCode());//设置当前登陆人，数据权限使用
		//查询有效租车记录
		dto.setActive(FossConstants.ACTIVE);
		//查询临时租车信息
		return RentCarReportDao.queryRentCarReportCount(dto);
	}
	
	/**
	 * 取消租车标记
	 * 
	 * @author 045738-foss-maojianqiang
	 * @data 2014-07-14
	 */
	@Transactional
	public void disableRentCar(RentCarReportDto dto,CurrentInfo cInfo) {
		
		logger.info("取消租车标记    --start----");
		//校验租车记录
		if(dto==null || CollectionUtils.isEmpty(dto.getBillNos())){
			throw new SettlementException("传入的租车编号不能为空！");
		}
		//判断最大作废条数
		if(dto.getBillNos().size()>SettlementConstants.MAX_LIST_SIZE){
			throw new SettlementException("批量取消最大不能超过1000条！");
		}
		//预提中和已预提
		List<RentCarReportDto> withholdingList = this.RentCarReportDao.queryWithholdingedRentCar(dto);
		//预提中或已预提
		if(CollectionUtils.isNotEmpty(withholdingList)){
			RentCarReportDto entity = withholdingList.get(0);
			throw new SettlementException(entity.getRentCarNo()+"等已生成预提工作流，工作流审批不同意后才能取消标记，如果工作流已审批完成且通过，不允许取消标记");
		}
		//查询应付单
		List<BillPayableEntity> payList = this.billPayableService.queryBySourceBillNOs(dto.getBillNos(), SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__RENTCAR, FossConstants.ACTIVE);
		if(CollectionUtils.isEmpty(payList)){
			throw new SettlementException("未查询到对应的临时租车应付单！");
		}
		//临时租车应付单个数与租车记录个数不一致
		if(payList.size()!=dto.getBillNos().size()){
			throw new SettlementException("临时租车应付单个数与租车记录个数不一致");
		}
		//循环查询是否存在
		for(BillPayableEntity pay:payList){
			//如果应付单支付状态不是未支付，即已付款，则抛出异常
			if(!SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO.equals(pay.getPayStatus())){
				throw new SettlementException(pay.getSourceBillNo()+ "该租车编号已在应付单管理界面付款，请先作废付款单后再取消标记");
			}
		}
		try {
			temprentalMarkService.invalidRentalMark(dto.getBillNos(),cInfo);
		} catch (Exception e) {
			throw new SettlementException("调用中转作废临时租车接口报错！");
		}
		//循环红冲应付单
		for(BillPayableEntity pay:payList){
			//调用应付单红冲
			billPayableService.writeBackBillPayable(pay, cInfo);
		}
		logger.info("取消租车标记    --end----");
	}
	

	/**
	 * 功能：预提
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	public RentCarReportVo withholding(RentCarReportDto dto,CurrentInfo cInfo){
		//判断传入参数
		if(dto==null){
			throw new SettlementException("传入查询信息为空");
		}
		//校验查询单号集合
		if(CollectionUtils.isEmpty(dto.getBillNos())){
			throw new SettlementException("按单号查询时，查询单号不能为空！");
		}
		//
		dto.setEmpCode(cInfo.getEmpCode());//设置当前登陆人编号
		//查询
		List<RentCarReportDto> list = RentCarReportDao.queryRentCarReportForExport(dto);
		//校验数据是否发生变化
		if(list.size()!=dto.getBillNos().size()){
			throw new SettlementException("查询条数与界面预提个数不一致，数据可能发生变化，请重新刷新界面进行预提操作！");
		}
		
		//校验预提
		RentCarReportVo vo= vaildateWithholdingParams(list,cInfo);
		return vo;
	}
	
	/**
	 * 功能：保存预提记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	@Transactional
	public RentCarReportVo saveWithholding(RentCarReportVo vo,CurrentInfo cInfo){
		logger.info("保存预提记录    --start----");
		
		WithholdingEntity entity = validaExtracted(vo);
		validaExtracted(vo, entity);
		
		//应付单列表
		List<BillPayableEntity> payableList = new ArrayList<BillPayableEntity>();
		List<BillPayableEntity> lockPayablelist = new ArrayList<BillPayableEntity>();
		//调用预提校验方法进行校验
		List<RentCarReportDto> list = RentCarReportDao.queryRentCarReportForExport(vo.getDto());
		vaildateWithholdingParams(list,cInfo);
		
	
		//设置id
		entity.setId(UUIDUtils.getUUID());
		//获取预提单号
		String withholdingNo = this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YT);
		//设置预提单号
		entity.setWithholdingNo(withholdingNo);
		//默认为预提中
		entity.setWithholdingStatus(SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERRING);
		//是否有效
		entity.setActive(FossConstants.ACTIVE);
		
		//插入预提明细
		List<WithholdingEntityDetail> repeatList  = vo.getRepeatList();
		//声明预提明细
		List<ExpenseDetail> expenseDetailList  = new ArrayList<ExpenseDetail>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.RENTCAR_USE_TYPE);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> dicMap = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		//1、插入预提明细
		for(WithholdingEntityDetail wEntity : repeatList){
			//封装应付单
			BillPayableEntity payable = new BillPayableEntity();
			BillPayableEntity lockPayable = new BillPayableEntity();
			//增加锁住应付单
			lockPayable.setPayableNo(wEntity.getPayableNo());
			lockPayable.setVersionNo(wEntity.getVersion());
			
			//此处是因为应付单要先更新一次版本号，来防止并发调用预提接口
			payable.setPayableNo(wEntity.getPayableNo());
			payable.setVersionNo((short)(wEntity.getVersion()+1));
			
			payableList.add(payable);
			lockPayablelist.add(lockPayable);//封装要锁住的应付单列表
			//封装uuid
			wEntity.setId(UUIDUtils.getUUID());
			wEntity.setWithholdingNo(withholdingNo);
			
			//封装发送给FSSC预提明细
			ExpenseDetail detail = getWorkFlowRequestDetail(entity,wEntity,dicMap);
			expenseDetailList.add(detail);
			//插入预提明细
			withholdingEntityDetailService.add(wEntity); 
		}
		
		//先更新一次应付单，来防止并发
		this.rentCarReportService.updateWorkFlowNoByPayNo(lockPayablelist, null, cInfo);
		
		//2、调用生成工作流接口
		// 实例化ESBHeader，并设置到Holder对象中
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_GENERATE_ACCRUED_WORKFLOW);
		//与业务相关的字段
		header.setBusinessId(withholdingNo);
		header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		//消息格式
		header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUIDUtils.getUUID());
	    //请求系统
		header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		//声明request
		CreateAccruedWorkflowRequest requset = this.getWorkFlowRequest(vo.getWithholdingEntity(),cInfo,expenseDetailList);
		CreateAccruedWorkflowResponse response = new CreateAccruedWorkflowResponse();
		//调用接口生成工作流
		try {
			response = fossAccruedService.createAccruedWorkflow(requset, holder);
		} catch (CommonException e) {
			//版本号减少1
			this.rentCarReportService.updatePayableVersion(payableList, cInfo);
			throw new SettlementException("调用ESB接口异常", e.getMessage());
		}catch(Exception e){
			//版本号减少1
			this.rentCarReportService.updatePayableVersion(payableList, cInfo);
			throw new SettlementException("调用ESB接口异常", e.getMessage());
		}
		//生成工作流失败
		if(response!=null && !response.isIsSuccess()){
			this.rentCarReportService.updatePayableVersion(payableList, cInfo);
			throw new SettlementException("生成工作流失败："+response.getErrorDesc());
		}
		//获取报账工作流号
		String workFlowNo = response.getClaimNo();
		//3、插入预提信息
		//设置预提工作流号
		entity.setWorkflowNo(workFlowNo);
		//插入预提单据
		withholdingEntityService.add(vo.getWithholdingEntity(),cInfo);
		
		//4、更新应付单
		billPayableService.updateWorkFlowNoByPayNo(payableList, workFlowNo, cInfo);
		
		//5、更新租车记录
		RentalMarkDto rentalMarkDto = new RentalMarkDto();
		rentalMarkDto.setAccruedWorkNo(workFlowNo);//设置工作流号
		rentalMarkDto.setAccruedState(SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERRING);
		rentalMarkDto.setRentCarNos(vo.getDto().getBillNos());
		try{
			temprentalMarkService.updateTemprentalMarkAccrued(rentalMarkDto);
		}catch(BusinessException e){
			throw new SettlementException(e);
		}
		
		//重置预提工作流号返回给前台
		vo.getDto().setWithholdingWorkFlowNo(workFlowNo);
		logger.info("保存预提记录    --end----");
		return vo;
	}
	private WithholdingEntity validaExtracted(RentCarReportVo vo) {
		//判断传入参数
		if(vo==null || vo.getWithholdingEntity()==null || CollectionUtils.isEmpty(vo.getRepeatList())){
			throw new SettlementException("传入预提信息为空");
		}
		//声明预提实体
		WithholdingEntity entity = vo.getWithholdingEntity();
		
		//判断必选项
		if(StringUtils.isBlank(entity.getCostDeptCode())){
			throw new SettlementException("费用承担部门不能为空！");
		}
		if(StringUtils.isBlank(entity.getCostType())){
			throw new SettlementException("费用类型不能为空！");
		}
		if(StringUtils.isBlank(entity.getInvoiceCode())){
			throw new SettlementException("发票抬头不能为空！");
		}
		if(entity.getCostDate()==null){
			throw new SettlementException("费用所属月份不能为空！");
		}
		return entity;
	}
	private void validaExtracted(RentCarReportVo vo, WithholdingEntity entity) {
		//如果备注超过300字，则抛出异常
		if(StringUtils.isNotBlank(entity.getNotes())){
			entity.setNotes(StringUtils.trim(entity.getNotes()));
			if(entity.getNotes().length()>300){
				throw new SettlementException("申请事由不能超过300个字！");
			}
		}
		
		//应付单支付状态
		if(StringUtils.isEmpty(vo.getDto().getPayablePayStatus())){
			throw new SettlementException("应付单的支付状态不能为空！");
		}else{
			//如果应付单为未支付，则需要判断业务日期必须在本月或者上月
			if(!FossConstants.ACTIVE.equals(vo.getDto().getPayablePayStatus())){
				Date firstDayOfCurrentMonth = SettlementUtil.getFirstDayOfMonth(new Date());
				//获取上上个月的最后一天
				Date firstDayOfLastMonth = DateUtils.addDayToDate(SettlementUtil.addMonthToDate(firstDayOfCurrentMonth, -1), -1) ;
				Date firstDayOfNextMonth = SettlementUtil.addMonthToDate(firstDayOfCurrentMonth, 1);
				//日期必须在本月或者上月
				if(entity.getCostDate().before(firstDayOfLastMonth)|| entity.getCostDate().after(firstDayOfNextMonth)){
					throw new SettlementException("费用所属月份必须在本月或者上月！");
				}
			}
		}
		
		//校验发送给FSSC的必填项
		if(StringUtils.isBlank(entity.getNotes())){
			throw new SettlementException("费用说明不能为空！");
		}
		//校验发送给FSSC的必填项
		if(entity.getAmount()==null|| entity.getAmount().compareTo(BigDecimal.ZERO)<0){
			throw new SettlementException("预提金额必须大于0");
		}
	}
	
	/**
	 * 功能：校验查询参数
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	private void validateParams(RentCarReportDto dto){
		//判断传入参数
			if(dto==null){
			throw new SettlementException("传入查询信息为空");
		}
		//校验查询类型
		if(StringUtils.isBlank(dto.getQueryType())){
			throw new SettlementException("查询类型不能为空！");
		}
		//按日期查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryType())){
			//查询日期类型
			if(StringUtils.isBlank(dto.getQueryDateType())){
				throw new SettlementException("查询日期类型不能为空！");
			}
			//按日期查询
			if(dto.getStartDate()==null || dto.getEndDate()==null){
				throw new SettlementException("查询日期不能为空！");
			}
			//处理部门
			List<String> codeList = dealDepts(dto);
			dto.setOrgCodeList(codeList);
		//按各种单号查询	
		}else{
			//校验查询单号集合
			if(CollectionUtils.isEmpty(dto.getBillNos())){
				throw new SettlementException("按单号查询时，查询单号不能为空！");
			}
		}
	}
	
	/**
	 * 功能：校验预提参数
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-18
	 * @return
	 */
	private RentCarReportVo vaildateWithholdingParams(List<RentCarReportDto> list,CurrentInfo cInfo){
		//费用承担部门
		String costDeptCode  = null;
		//费用承担部门名称
		String costDeptName = null;
		//声明预提部门
		String withholdOrgcode = null;
		//业务发生日期
		String businessDate = null;
		//应付单的支付状态
		String payableStatus = null;
		//用车日期
		Date userCarDate = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		//声明预提金额
		BigDecimal amount = BigDecimal.ZERO;
		//预提明细
		List<WithholdingEntityDetail> repeatList = new ArrayList<WithholdingEntityDetail>();
		//预提实体
		WithholdingEntity withholdingEntity = new WithholdingEntity();
		
		//循环判断
		for(RentCarReportDto dto:list){
			//判断预提部门和应付部门是一个部门
			if(StringUtils.isBlank(withholdOrgcode)){
				withholdOrgcode = dto.getPayableOrgCode();
			}else{
				//判断应付单的应付部门和当前操作人所属部门必须是一个部门
				if(!withholdOrgcode.equals(dto.getPayableOrgCode())){
					throw new SettlementException("租车编号"+dto.getRentCarNo()+"的应付部门和其它单据应付部门不一致，不能合并预提！ ");
				}
			}
			
			//初始化支付状态
			if(StringUtils.isBlank(payableStatus)){
				//判断应付单的支付状态
				if(StringUtils.isNotBlank(dto.getPayablePayStatus())){
					payableStatus = dto.getPayablePayStatus();
				}
			}else{
				//判断应付单的支付状态
				if(StringUtils.isNotBlank(dto.getPayablePayStatus()) && !payableStatus.equals(dto.getPayablePayStatus())){
					throw new SettlementException("租车编号"+dto.getRentCarNo()+"已付款和未付款数据要分开预提！");
				}
			}
						
			//判断预提状态
			if(StringUtils.isNotBlank(dto.getWithholdingStatus()) 
					&& (SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERRING.equals(dto.getWithholdingStatus()))
						||SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED.equals(dto.getWithholdingStatus())){
				throw new SettlementException("租车编号"+dto.getRentCarNo()+"已预提，不能再次预提！");
			}
			//判断报销状态
			if(StringUtils.isNotBlank(dto.getReimbursement()) && FossConstants.ACTIVE.equals(dto.getReimbursement())){
				throw new SettlementException("租车编号"+dto.getRentCarNo()+"已报销，已报销！");
			}
			
			//判断付款状态  ： 如果为现金，付款中的话，上面报销状态是通不过的。故而能走到此处的，肯定都是电汇类付款中状态
			if(StringUtils.isNotBlank(dto.getPayStatus())
					&& SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING.equals(dto.getPayStatus())){
				throw new SettlementException("租车编号"+dto.getRentCarNo()+"付款工作流未审批完毕，请催批工作流审批完毕后再预提");
			}
			//判断费用承担部门是否一致，不一致则不能一起预提，为空的默认不参与判断
			if(StringUtils.isBlank(costDeptCode)){
				//初始化费用承担部门
				if(StringUtils.isNotBlank(dto.getCostDept())){
					costDeptCode = dto.getCostDept();
					costDeptName = dto.getCostDeptName();
				}
			//判断费用承担部门是否一致
			}else{
				//如果费用承担部门不一致，则抛出异常
				if(StringUtils.isNotBlank(dto.getCostDept()) && !costDeptCode.equals(dto.getCostDept())){
					throw new SettlementException(dto.getRentCarNo()+"和选中的租车记录费用承担部门不同，请根据不同的费用承担部门分开预提");
				}
			}
			//判断费用所属月份  --如果为已支付，则判断录入付款单地方的业务发生日期，如果不在同一个月份则抛出异常；
			//判断费用所属月份  --如果为未支付，则判断租车记录的用车日期所在月份，如果不在同一个月份则末日为空，让前台去选择业务发生日期。不抛出异常；	
			if(StringUtils.isNotBlank(payableStatus)){
				//已支付
				if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(payableStatus)){
					//业务发生日期
					if(dto.getBusinessDate()==null){
						throw new SettlementException(dto.getRentCarNo()+"的已经录入付款单，对应付款单的业务发生日期不能为空！");
					}else{
						//初始化业务发生日期
						if(StringUtils.isBlank(businessDate)){
							businessDate =format.format(dto.getBusinessDate());
							userCarDate = dto.getBusinessDate();
						}else{
							//获取转话后的日期比较，如果不在一个月份抛出异常
							String targetDate = format.format(dto.getBusinessDate());
							//判断是否相等
							if(!StringUtils.equals(businessDate, targetDate)){
								throw new SettlementException(dto.getRentCarNo()+"选中的租车记录已付款且业务发生月份不同，请根据不同的业务发生日期分开预提");
							}
						}
					}
				//未支付
				}else{
					if(dto.getUseCarDate()==null){
						throw new SettlementException(dto.getRentCarNo()+"用车日期不能为空！");
					}else{
						if(userCarDate!=null){
							//初始化业务发生日期
							if(StringUtils.isBlank(businessDate)){
								//初始化用车日期
								userCarDate = dto.getUseCarDate();
								businessDate =format.format(dto.getUseCarDate());
							}else{
								//获取转话后的日期比较，如果不在一个月份抛出异常
								String targetDate = format.format(dto.getUseCarDate());
								//判断是否相等
								if(!StringUtils.equals(businessDate, targetDate)){
									//如果用车日期为空，则表明租车记录的用车日期不在一个月份， 默认为空	
									userCarDate = null;
								}
							}
						}
					}	
				}
			}
			//封装金额
			amount = amount.add(dto.getRentCarAmount());
			//封装用车日期
			if(userCarDate!=null){
				userCarDate = SettlementUtil.getFirstDayOfMonth(userCarDate);
			}
			//声明
			WithholdingEntityDetail detail = new WithholdingEntityDetail();
			//租车编号
			detail.setRentCarNo(dto.getRentCarNo());
			//应付单号
			detail.setPayableNo(dto.getPayableNo());
			//租车用途
			detail.setRentCarUseType(dto.getRentCarUseType());
			//车牌号
			detail.setVehicleNo(dto.getVehicleNo());
			//司机
			detail.setDriverName(dto.getDriverName());
			//设置司机工号
			detail.setDriverCode(dto.getDriverCode());
			//租车金额
			detail.setRentCarAmount(dto.getRentCarAmount());
			//用车日期
			detail.setUseCarDate(dto.getUseCarDate());
			//设置应付单的版本号，控制并发用
			detail.setVersion(dto.getVersion());
			//是否重复标记
			detail.setRepeatTag(dto.getIsRepeateMark());
			//司机联系方式
			detail.setDriverPhone(dto.getDriverPhone());
			//备注
			detail.setNotes(null);
			repeatList.add(detail);
		}
		//设置预提金额
		withholdingEntity.setAmount(amount);
		//如果不为空，则取付款的费用承担部门，反之则默认为空//如果为营业部，且没有付款，那么预提需要默认为本营业部。
		if(StringUtils.isNotBlank(costDeptCode) || FossConstants.ACTIVE.equals(cInfo.getDept().getSalesDepartment())){
			CostCenterDeptEntity entity = new CostCenterDeptEntity();
			//如果为营业部，且没付款，则需要根据营业部查询对应成本中心，获取成本中心数据
			if(StringUtils.isBlank(costDeptCode)){
				entity.setSimpleCode(cInfo.getDept().getUnifiedCode());
			}else{
				entity.setDeptCode(costDeptCode);
			}
			entity.setStatus("0");//正常0，已封存1，已撤销2，待撤销3，待清理4
			entity.setIsFreeze("0");// 是否冻结 1是0否
			entity.setIsCostOrgUnit("1");//是否成本中心 1是 0否
			//需要判断当前登陆部门是否为成本中心
			List<CostCenterDeptEntity> costCenterList = commonCostCenterDeptService.queryCostDeptByCondition(entity, Integer.MAX_VALUE,0 );
			//判断费用承担部门
			if(!CollectionUtils.isEmpty(costCenterList)){
				//货物费用承担部门类型
				CostCenterDeptEntity costCenter = costCenterList.get(0);
				costDeptName = costCenter.getDeptName();
				costDeptCode = costCenter.getDeptCode();
				withholdingEntity.setCostDeptType(costCenter.getTypeCode());
			}else{
				costDeptCode = null;
			}
		}
		
		//设置费用承担部门
		withholdingEntity.setCostDeptCode(costDeptCode);
		withholdingEntity.setCostDeptName(costDeptName);
		withholdingEntity.setCostDate(userCarDate);
		withholdingEntity.setCreateOrgCode(withholdOrgcode);
		
		//设置返回值
		RentCarReportVo vo = new RentCarReportVo();
		vo.setRepeatList(repeatList);
		vo.setWithholdingEntity(withholdingEntity);
		return vo;
	}
	
	/**
	 * 对事业部、大区、小区、部门进行处理
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 下午2:00:46
	 */
	private List<String> dealDepts(RentCarReportDto dto) {
		//声明目标部门集合
		List<String> targetList = new ArrayList<String>();
		
		//如果部门存在，则按部门查询
		if (StringUtils.isNotBlank(dto.getRentCarDeptCode())) {
			targetList.add(dto.getRentCarDeptCode());
		//如果部门不存在，小区存在，则获取小区地下所有部门
		}else{
			//声明查询编码
			String queryDeptCode = null;
			//小区存在，取小区
			if(StringUtils.isNotBlank(dto.getSmallArea())){
				queryDeptCode = dto.getSmallArea();
			//大区存在，取大区
			}else if(StringUtils.isNotBlank(dto.getBigArea())){
				queryDeptCode = dto.getBigArea();
			//否则取事业部
			}else{
				queryDeptCode = dto.getDivision();
			}
			//如果存在一个查询编码，则查询期对应的组织
			if(StringUtils.isNotBlank(queryDeptCode)){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDeptCode);
				//判断查询部门是否超过1000
				if(orgList.size()>SettlementConstants.MAX_LIST_SIZE){
					throw new SettlementException("选择事业部下超过1000个子部门,无法查询。请按大区查询！");
				}
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetList.add(en.getCode());
				}
			}
		} 
		//返回结果集合
		return targetList;
	}

	/**
	 * 功能：获取工作流request
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-26
	 * @return
	 */
	private CreateAccruedWorkflowRequest getWorkFlowRequest(WithholdingEntity entity,CurrentInfo cInfo,List<ExpenseDetail> expenseDetailList){
		//校验发送给FSSC的必填项
		if(StringUtils.isBlank(entity.getWithholdingNo())){
			throw new SettlementException("预提单号不能为空！");
		}
		//校验发送给FSSC的必填项
		if(CollectionUtils.isEmpty(expenseDetailList)){
			throw new SettlementException("预提明细不能为空！");
		}
		
		// 根据部门标杆编码查询部门信息
		OrgAdministrativeInfoEntity orgAdmin = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCreateOrgCode());
		//根据部门获取部门标杆编码
		if(orgAdmin==null || StringUtils.isBlank(orgAdmin.getUnifiedCode())){
			throw new SettlementException(entity.getCreateOrgCode()+"对应的部门信息不存在！");
		}			
		//预提部门标杆编码
		String unifiedCode = orgAdmin.getUnifiedCode();
		//声明request
		CreateAccruedWorkflowRequest request = new CreateAccruedWorkflowRequest();
		request.setAccruedBillNo(entity.getWithholdingNo());//预提流水号
		request.setAccruedBillAppType(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_WITHHOLDING);//工作流类型
		request.setAccruedDept(unifiedCode);//预提部门
		request.setAccruedBillComNo(entity.getInvoiceCode());//发票抬头
		request.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);//币种
		request.setExchangeRate(null);//汇率
		request.setCostBelongDate(new SimpleDateFormat(formatType).format(entity.getCostDate()));//费用所属月份
		request.setPeyBillDiscription(entity.getNotes());//事由说明
		request.setPayBillAppNo(cInfo.getEmpCode());//申请人工号
		request.setAccruedAmount(entity.getAmount());//预提金额
		request.getExpenseDetail();//明细行集合
		//添加明细
		request.getExpenseDetail().addAll(expenseDetailList);
		return request;
	}
	
	/**
	 * 功能：获取预提明细
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-28
	 * @return
	 */
	private ExpenseDetail getWorkFlowRequestDetail(WithholdingEntity entity,WithholdingEntityDetail wDetail,Map<String,Map<String,Object>> dicMap){
		//校验预提金额
		if(wDetail.getRentCarAmount()==null || wDetail.getRentCarAmount().compareTo(BigDecimal.ZERO)<0){
			throw new SettlementException(wDetail.getRentCarNo()+":预提金额必须大于0");
		}
		//校验应付单号
		if(StringUtils.isBlank(wDetail.getPayableNo())){
			throw new SettlementException(wDetail.getRentCarNo()+":应付单号不能为空！");
		}
		//校验租车用途
		if(StringUtils.isBlank(wDetail.getRentCarUseType())){
			throw new SettlementException(wDetail.getRentCarNo()+":租车用途不能为空！");
		}
		//校验车牌号
		if(StringUtils.isBlank(wDetail.getVehicleNo())){
			throw new SettlementException(wDetail.getRentCarNo()+":车牌号不能为空！");
		}
		//校验多次标记
		if(StringUtils.isBlank(wDetail.getRepeatTag())){
			throw new SettlementException(wDetail.getRentCarNo()+":多次标记不能为空！");
		}
		ExpenseDetail detail = new ExpenseDetail();
		try {
			//业务发生日期
			detail.setAccruedExpDate(SettlementUtil.dateToXmlDate(entity.getCostDate()));
		} catch (Exception e) {
			throw new SettlementException("业务发生日期转话发生异常！");
		}
		detail.setExpensesType(convertCostType(entity.getCostType()));//费用类型
		detail.setExpensesMoney(wDetail.getRentCarAmount());//租车金额
		detail.setBillNumber(wDetail.getPayableNo());//应付单号
		detail.setExpenseExplain(null);//备注
		detail.setExpenseCostCenter(entity.getCostDeptCode());//费用承担部门
		String rentCarUseType = SettlementUtil.getDataDictionaryByTermsName(dicMap,
							DictionaryConstants.RENTCAR_USE_TYPE,wDetail.getRentCarUseType());
		detail.setCarPurpose(rentCarUseType);//租车用途
		detail.setCarNumber(wDetail.getVehicleNo());//车牌号
		//司机姓名
		if(StringUtils.isBlank(wDetail.getDriverName())){
			detail.setDriverName(EMPTY_DESC);
		}else{
			detail.setDriverName(wDetail.getDriverName());
		}
		//司机联系方式
		if(StringUtils.isBlank(wDetail.getDriverPhone())){
			detail.setDriverName(EMPTY_DESC);
		}else{
			detail.setDriverTel(wDetail.getDriverPhone());
		}
		detail.setRepeatTag(wDetail.getRepeatTag());//是否重复标记
		return detail;
	}
	
	/**
	 * 因为预提费用类型和付款费用类型的值不一样，故而需要转话一下
	 * @return
	 */
	private String convertCostType(String costType){
		String targetCostType = null;
		//转话费用类型
		if(!StringUtils.isBlank(costType)){
			if(SettlementDictionaryConstants.RENTCAR_COST_TYPE_RENT_CAR_FEE.equals(costType)){
				targetCostType = SettlementESBDictionaryConstants.RENTCAR_COST_TYPE_RENT_CAR_FEE;
			}else if(SettlementDictionaryConstants.RENTCAR_COST_TYPE_OIL_FEE_LONG.equals(costType)){
				targetCostType = SettlementESBDictionaryConstants.RENTCAR_COST_TYPE_OIL_FEE_LONG;
			}else if(SettlementDictionaryConstants.RENTCAR_COST_TYPE_OIL_FEE_SHORT.equals(costType)){
				targetCostType = SettlementESBDictionaryConstants.RENTCAR_COST_TYPE_OIL_FEE_SHORT;
			}else if(SettlementDictionaryConstants.RENTCAR_COST_TYPE_ROAD_FEE_SHORT.equals(costType)){
				targetCostType = SettlementESBDictionaryConstants.RENTCAR_COST_TYPE_ROAD_FEE_SHORT;
			}else if(SettlementDictionaryConstants.RENTCAR_COST_TYPE_TRANS_FEE.equals(costType)){
				targetCostType = SettlementESBDictionaryConstants.RENTCAR_COST_TYPE_TRANS_FEE;
			}else {
				throw new SettlementException("传入的费用类型有误，预提只能选择：临时租车费、油费（长途）、油费（短途）、路桥费（短途）、运费");
			}
		}
		return targetCostType;
	}
	
	/**
	 * 功能：获取事业部、大区、小区
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-22
	 * @return
	 */
	private List<RentCarReportDto> getRegions(List<RentCarReportDto> list){
		//声明部门信息
		Map<String,Object> deptMap = new HashMap<String,Object>();
		//小区
		Map<String,Object> smallAreaMap = new HashMap<String,Object>();
		//大区
		Map<String,Object> largeMap = new HashMap<String,Object>();
		//事业部
		Map<String,Object> divisionMap = new HashMap<String,Object>();
		//设置成本中心查询条件
		CostCenterDeptEntity costEntity = new CostCenterDeptEntity();
		costEntity.setStatus("0");//正常0，已封存1，已撤销2，待撤销3，待清理4
		costEntity.setIsFreeze("0");// 是否冻结 1是0否
		costEntity.setIsCostOrgUnit("1");//是否成本中心 1是 0否
		String costDeptCodeUnfiedInFoss = null;//foss中的标杆编码
		String costDeptCodeInEas = null;//金蝶系统编码
		// 循环进行转化
		for (RentCarReportDto entity : list) {
			//如果费用承担部门不为空，则需要取大区小区事业部
			if(StringUtils.isNotBlank(entity.getCostDept())){
				costDeptCodeInEas = entity.getCostDept();//设置金蝶系统编码
				costEntity.setDeptCode(entity.getCostDept());
				//如果在部门map中不存在，则需要去查询，反之直接去拿即可
				if(deptMap.get(costDeptCodeInEas)!=null){
					entity.setSmallArea((String) smallAreaMap.get(costDeptCodeInEas));//获取小区
					entity.setBigArea((String) largeMap.get(costDeptCodeInEas));//获取大区
					entity.setDivision((String) divisionMap.get(costDeptCodeInEas));//获取事业部
				}else{
					List<CostCenterDeptEntity> costList =  this.commonCostCenterDeptService.queryCostDeptByCondition(costEntity, Integer.MAX_VALUE,0 );
					//判断费用承担部门
					if(!CollectionUtils.isEmpty(costList)){
						//货物费用承担部门类型
						CostCenterDeptEntity costCenter = costList.get(0);
						costDeptCodeUnfiedInFoss = costCenter.getSimpleCode();
					}else{
						continue;
					}
					//去查询部门信息
					if(StringUtils.isNotBlank(costDeptCodeUnfiedInFoss)){
						//调用综合接口，根据标杆查询该部门
						OrgAdministrativeInfoEntity deptEntity= orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(costDeptCodeUnfiedInFoss);
						//非空判断
						if(deptEntity!=null){
							//将部门名称放到部门map中
							deptMap.put(costDeptCodeInEas, deptEntity.getCode());
							
							//查询小区信息
							List<String> bizTypes = new ArrayList<String>();
							//声明
							bizTypes.add(BizTypeConstants.ORG_SMALL_REGION);
							//获取营业区
							OrgAdministrativeInfoEntity smallOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptEntity.getCode(), bizTypes);
							//非空判断
							if(smallOrg!=null){
								entity.setSmallArea(smallOrg.getName());
								smallAreaMap.put(costDeptCodeInEas,smallOrg.getName());
							}
							
							List<String> bigBizTypes = new ArrayList<String>();
							//声明
							bigBizTypes.add(BizTypeConstants.ORG_BIG_REGION);
							//获取营业区
							OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptEntity.getCode(), bigBizTypes);
							//非空判断
							if(bigOrg!=null){
								entity.setBigArea(bigOrg.getName());
								largeMap.put(costDeptCodeInEas,bigOrg.getName());
							}
							
							//获取事业部
							List<String> divTypes = new ArrayList<String>();
							divTypes.add(BizTypeConstants.ORG_DIVISION);
							//获取营业区
							OrgAdministrativeInfoEntity division = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptEntity.getCode(), divTypes);
							//大区非空判断
							if(division!=null){
								entity.setDivision(division.getName());
								divisionMap.put(costDeptCodeInEas, division.getName());
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 功能：更新应付单版本号--控制并发操作
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	 * @return
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateWorkFlowNoByPayNo(List<BillPayableEntity> list,
			String workFolwNo, CurrentInfo cInfo) {
		billPayableService.updateWorkFlowNoByPayNo(list, null, cInfo);
	}
	
	/**
	 * 功能：当更新报账预提不成功时，版本号-1
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	 * @return
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updatePayableVersion(List<BillPayableEntity> list,CurrentInfo cInfo) {
		billPayableService.updatePayableVersion(list, cInfo);
	}
	
	/**
	 * 功能：查询临时租车记录
	 * 需求DN201704100011
	 * @author 378375
	 * @date 2017年4月7日 16:52:59
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForCUBC(RentCarReportDto dto) {
		//查询有效租车记录
		dto.setActive(FossConstants.ACTIVE);
		List<RentCarReportDto> list = RentCarReportDao.queryRentCarReportForCUBC(dto);
		//查询临时租车信息
		return getRegions(list);
	}
	
	public IRentCarReportDao getRentCarReportDao() {
		return RentCarReportDao;
	}
	public void setRentCarReportDao(IRentCarReportDao rentCarReportDao) {
		RentCarReportDao = rentCarReportDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setWithholdingEntityService(IWithholdingEntityService withholdingEntityService) {
		this.withholdingEntityService = withholdingEntityService;
	}
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	public void setWithholdingEntityDetailService(
			IWithholdingEntityDetailService withholdingEntityDetailService) {
		this.withholdingEntityDetailService = withholdingEntityDetailService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setFossAccruedService(FossAccruedService fossAccruedService) {
		this.fossAccruedService = fossAccruedService;
	}
	public void setTemprentalMarkService(
			ITemprentalMarkService temprentalMarkService) {
		this.temprentalMarkService = temprentalMarkService;
	}
	public void setCommonCostCenterDeptService(
			ICommonCostCenterDeptService commonCostCenterDeptService) {
		this.commonCostCenterDeptService = commonCostCenterDeptService;
	}
	public void setRentCarReportService(IRentCarReportService rentCarReportService) {
		this.rentCarReportService = rentCarReportService;
	}
	
}
