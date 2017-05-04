package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaStatementManageSerive;
import com.deppon.foss.module.settlement.common.api.shared.domain.PdaStatementManageEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
/**
 * 
 * @ClassName: AccountStatementService 
 * @Description: TODO(通过单号查询对账单数据 --对接人明瑞深) 
 * @author HKB
 * @date 2016-1-26 上午10:57:56 
 *
 */
public class AccountStatementService implements IBusinessService<List<AccountStatementResult>, AccountStatementEntity>{

	private static final Log LOG = LogFactory.getLog(AccountStatementService.class);
	//保存归属系统是CUBC还是FOSS
	//String isSystemFoss="",isSystemCubc="";
	//FOSS结算API
	private IPdaStatementManageSerive pdaStatementManageService;
	//调用CUBC接口
	private IPdaToCubcService pdaToCubcService;
	//boolean flag=true;
	//解析参数
	@Override
	public AccountStatementEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		AccountStatementEntity entity = JsonUtil.parseJsonToObject(AccountStatementEntity.class,asyncMsg.getContent());
		return entity;
	}

	/*
	 * 
	 * @Description: TODO(方法入口)  
	 * @return Void    返回类型
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @author： 245955  HKB
	 */
	@Override
	public List<AccountStatementResult> service(AsyncMsg asyncMsg, AccountStatementEntity param)
			throws PdaBusiException {
		//校验参数合法性
		this.validate(asyncMsg, param);
		//封装请求参数
		CommonQueryParamDto dto = wrapCommonQueryParamDto(asyncMsg,param);
		PdaStatementManageDto pdaStatementManageFoss = null;
		PdaStatementManageDto pdaStatementManageCubc = null;
		List<AccountStatementResult> result = null;
		try{
			//封装调用灰度接口的参数
			RequestDO requestDo=new RequestDO();
			String[] versionNos = new String[1];
			versionNos[0]=param.getNumber();
			//运单号
			requestDo.setSourceBillNos(versionNos);
			//来源单据类型 --运单号
			requestDo.setSourceBillType("DZ");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.AccountStatementService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			if(!param.getNumber().equals("")){
			  //截取对账单号
			  String numberStr=param.getNumber().substring(0,2);
			  //如果为对账单号走灰度，反之走两边
			  if(numberStr.equals("DZ")){
				  //根据运单号调用归属服务
				  String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
				  if(!StringUtils.isBlank(gsService)){
				     VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
					 //由于返回的是个list,需要用到循环
					 List<VestBatchResult> vestBatchResult=vestResponse.getVestBatchResult();
					 for (int i = 0; i < vestBatchResult.size(); i++) {
					   // 归属系统编码
					   String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
					   if (cubcOrFoss.equals(DeryConstant.GS_CUBC)) {
							// 调用CUBC接口
							dto.setOrgCode(asyncMsg.getDeptCode());
							String responseStr = pdaToCubcService.pdaToCubcAccountStatement(dto);
							if (!StringUtils.isBlank(responseStr))
							pdaStatementManageCubc = JSON.parseObject(responseStr,PdaStatementManageDto.class);
							//isSystem = "Y";
					  }else{
					 		pdaStatementManageFoss = pdaStatementManageService.queryStatementByNo(dto);
					 }
				   }
				 }
			 }else if(!numberStr.equals("DZ")){
				 try {
					 String responseStr = pdaToCubcService.pdaToCubcAccountStatement(dto);
					 if (!StringUtils.isBlank(responseStr))
					 pdaStatementManageCubc = JSON.parseObject(responseStr,PdaStatementManageDto.class);
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
				try {
					//调用FOSS
					pdaStatementManageFoss = pdaStatementManageService.queryStatementByNo(dto); 
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
			 }
			}else{
				try {
					String responseStr = pdaToCubcService.pdaToCubcAccountStatement(dto);
					if (!StringUtils.isBlank(responseStr))
					pdaStatementManageCubc = JSON.parseObject(responseStr,PdaStatementManageDto.class);
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
				try {
					//调用FOSS
					pdaStatementManageFoss = pdaStatementManageService.queryStatementByNo(dto);
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
			}
			
			pdaStatementManageFoss = generatePdaStatementManageDto(pdaStatementManageFoss,pdaStatementManageCubc);
			
		  // 封装返回值参数
		  if (pdaStatementManageFoss != null) {
			 result = wrapAccountStatementResult(pdaStatementManageFoss);
		  }
		  LOG.info("查询对账单数据返回值封装结束");
		} catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} 
		return result;
	}
	//合并结果集
	private PdaStatementManageDto generatePdaStatementManageDto(PdaStatementManageDto foss, PdaStatementManageDto cubc){
		PdaStatementManageDto ret = new PdaStatementManageDto();
		List<PdaStatementManageEntity> statementList = new ArrayList<PdaStatementManageEntity>();
		ret.setStatementEntitys(statementList);
		if(foss!=null && !CollectionUtils.isEmpty(foss.getStatementEntitys())){
			statementList.addAll(foss.getStatementEntitys());
		}
		if(cubc!=null && !CollectionUtils.isEmpty(cubc.getStatementEntitys())){
			statementList.addAll(cubc.getStatementEntitys());
		}
		return ret;
	}
	//封装返回值
	private List<AccountStatementResult> wrapAccountStatementResult(PdaStatementManageDto pdaStatementManage){
		List<PdaStatementManageEntity> statementEntitys = pdaStatementManage.getStatementEntitys();
		List<AccountStatementResult> list = new ArrayList<AccountStatementResult>();
		AccountStatementResult result = null;
		if(CollectionUtils.isNotEmpty(statementEntitys)){
		  for(PdaStatementManageEntity entity : statementEntitys){
			result = new AccountStatementResult();
			//客户编码
			result.setCustomeCode(entity.getCustomeCode());
			//客户名称
			result.setCustomerName(entity.getCustomerName());
			//对账单号
			result.setStatementNo(entity.getStatementNo());
			//对账单金额
			result.setAmount(entity.getAmount());
			//未核销金额
			result.setUnVerifyAmount(entity.getUnVerifyAmount());
			//版本号
			result.setVersion(entity.getVersion());
			//归属系统
			if(entity.getSystemSour()!=null && entity.getSystemSour().equals(DeryConstant.GS_CUBC)){
				result.setAffiliation(DeryConstant.GS_CUBC);
			}else{ 
				result.setAffiliation(DeryConstant.GS_FOSS);
			}
			list.add(result);
		  }
		}
		return list;
	} 

	//操作类型
	@Override
	public String getOperType() {
		
		return DeliveryConstant.OPER_TYPE_ACCOUNT_STATEMENT.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 
	 * @Title: validate 
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg, AccountStatementEntity entity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		// 工号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		// 部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 用户类型
		Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
		//所属模块
		Argument.hasText(entity.getBelongModule(), "entity.belongModule");
		//单号
		//Argument.hasText(entity.getNumber(), "entity.number");
	}

	//封装参数
	private CommonQueryParamDto wrapCommonQueryParamDto(AsyncMsg asyncMsg, AccountStatementEntity entity){
		CommonQueryParamDto dto = new CommonQueryParamDto();
		//所属模块
		if("accountStatement".equals(entity.getBelongModule())){
			dto.setBelongModule("对账单");
		}
		//单号
		if(entity.getNumber().length()==0){
			List<String> numbers = new ArrayList<String>();
			dto.setNumbers(numbers);
			//部门编码
			dto.setOrgCode(asyncMsg.getDeptCode());
		}else{
			//dto.setNumber(entity.getNumber());
			List<String> numbers = new ArrayList<String>();
		    numbers.add(entity.getNumber());
			dto.setNumbers(numbers);
		}
		//操作时间
		dto.setOperateTime(entity.getOperateTime());
		//工号
		dto.setEmpCode(asyncMsg.getUserCode());
		//是否为司机		
		if("NCI_DRIVER".equals(asyncMsg.getUserType())){
			//是否司机
			dto.setIsDriver("true");
		} else if("NCI_USER".equals(asyncMsg.getUserType())){
			//是否司机
			dto.setIsDriver("false");
		}
		return dto;
		
	}


	public void setPdaStatementManageService(
			IPdaStatementManageSerive pdaStatementManageService) {
		this.pdaStatementManageService = pdaStatementManageService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}
	
	
}
