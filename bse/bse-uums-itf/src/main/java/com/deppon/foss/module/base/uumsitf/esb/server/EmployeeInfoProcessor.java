package com.deppon.foss.module.base.uumsitf.esb.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.json.SendEmpInfoResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.EmployeeException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MdmEmpInfo;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmEmpProcessReult;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmEmpRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmEmpResponse;
import com.eos.system.utility.StringUtil;
/**
 * 主数据项目--同步员工信息新接口
 * @author leo-zeng
 *
 */
public class EmployeeInfoProcessor implements IProcess{
	
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInfoProcessor.class);
	//用工信息Service
	private IEmployeeService employeeService;
	//并发锁
	private IBusinessLockService businessLockService;
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	//定义成功、失败次数
	int sucessCount =0,failCount =0;
	

	@Override
	public Object process(Object req) throws ESBBusinessException {
		SendMdmEmpRequest empRequest = (SendMdmEmpRequest) req;
		SendMdmEmpResponse empResponse = new SendMdmEmpResponse();
		LOGGER.info("------------------------------------同步主数据项目：同步员工信息新接口begin-----------------------------------");
		if (empRequest == null) {
			LOGGER.info("SendMdmEmpRequest员工信息为空：" + empRequest);
			return empResponse;
		}

		List<SendMdmEmpProcessReult> detailList = new ArrayList<SendMdmEmpProcessReult>();
		List<MdmEmpInfo> empInfos = empRequest.getEmployeeInfoList();
		// 非空校验
		if (CollectionUtils.isEmpty(empInfos)) {
			LOGGER.info("SendMdmEmpRequest员工信息employeeInfoList为空："
					+ empRequest.getEmployeeInfoList());
			return empResponse;
		}
		// 循环处理
		for (MdmEmpInfo mdmEmpInfo : empInfos) {
			addSendMdmEmpProcessReult(detailList, mdmEmpInfo);
		}
		empResponse.setFailedCount(failCount);
		empResponse.setSuccessCount(sucessCount);
		empResponse.setProcessResultList(detailList);
		empResponse.setSysName("FOSS");
		LOGGER.info("UUMS调用FOSS同步人员接口，返回报文:\n"
				+ new SendEmpInfoResponseTrans().fromMessage(empResponse));
		LOGGER.info("------------------------------------同步主数据项目：同步员工信息新接口END-----------------------------------");
		return empResponse;
	}

	private void addSendMdmEmpProcessReult(
			List<SendMdmEmpProcessReult> detailList, MdmEmpInfo mdmEmpInfo) {
		SendMdmEmpProcessReult detail = new SendMdmEmpProcessReult();
		try {
			if(null==mdmEmpInfo){
				throw new EmployeeException("UUMS同步过来的员工信息为空");
			}
			// 业务锁
			MutexElement mutex = new MutexElement(
					mdmEmpInfo.getEmpCode() + mdmEmpInfo.getBeginTime(), "UUMS_EMP_CODE_AND_CREATE_TIME",
					MutexElementType.UUMS_EMP_CODE_AND_CREATE_TIME);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.NUMBER_10);
			//成功加锁
			if(result){
				if(!mdmEmpInfo.isIsTempEmp()){
					if(StringUtils.isBlank(mdmEmpInfo.getEmpCode())
							||StringUtils.isBlank(mdmEmpInfo.getBeginTime())
							||StringUtils.isBlank(mdmEmpInfo.getEndTime())){
						throw new EmployeeException("UUMS同步过来的参数为空");
					}
				}
				
				//判断是否是进行新增、还是修改操作
				EmployeeEntity infoResult= transTofossForUpdateOrAdd(mdmEmpInfo);
				employeeService.updateEmployeeOfUU(infoResult);
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			}else{
				failCount++;
				detail.setEmployeeChangeId(mdmEmpInfo.getEmployeeChangeId());
				detail.setResult(false);
				detail.setReason("uums同步过来的数据，加锁失败");
				detail.setEmpCode(mdmEmpInfo.getEmpCode());
				detailList.add(detail);
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				return;
			}
			sucessCount++;
			detail.setEmployeeChangeId(mdmEmpInfo.getEmployeeChangeId());
			detail.setResult(true);
			detail.setEmpCode(mdmEmpInfo.getEmpCode());
			detailList.add(detail);
		} catch (Exception e) {
			failCount++;
			detail.setEmployeeChangeId(mdmEmpInfo.getEmployeeChangeId());
			detail.setResult(false);
			detail.setReason(e.getMessage());
			detail.setEmpCode(mdmEmpInfo.getEmpCode());
			detailList.add(detail);
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * 转换成foss中的实体对象
	 * @param mdmEmpInfo
	 * @param infoResult
	 * @param b
	 * @return
	 */
	private EmployeeEntity transTofossForUpdateOrAdd(MdmEmpInfo mdmEmpInfo) {
		
		//创建FOSS新实体
		EmployeeEntity	infoResult =new EmployeeEntity();
		//ACTIVE设置
		if(!mdmEmpInfo.isIsTempEmp()){//非临时人员
			if(mdmEmpInfo.getStatus()==0 || !StringUtil.equal(mdmEmpInfo.getEndTime(), DictionaryValueConstants.MAX_TIME_OF_UU)){
				infoResult.setActive(FossConstants.NO);
			}else if(mdmEmpInfo.getStatus()==1 && StringUtil.equal(mdmEmpInfo.getEndTime(), DictionaryValueConstants.MAX_TIME_OF_UU)){
				infoResult.setActive(FossConstants.YES);
			}
		}else{//临时人员
			infoResult.setActive(mdmEmpInfo.getStatus()==1?FossConstants.ACTIVE:FossConstants.INACTIVE);
		}
		//状态
		infoResult.setStatus(String.valueOf(mdmEmpInfo.getStatus()));
		//出生日期
		infoResult.setBirthdate(mdmEmpInfo.getBirthDate());
		//创建时间
		infoResult.setCreateTime(mdmEmpInfo.getBeginTime());
		if(mdmEmpInfo.isIsTempEmp()){//临时人员创建时间
			infoResult.setCreateTime(mdmEmpInfo.getModifyDate());
		}
		//修改时间
		infoResult.setModifyTime(mdmEmpInfo.getEndTime());
		if(mdmEmpInfo.isIsTempEmp()||StringUtil.equal(mdmEmpInfo.getEndTime(), DictionaryValueConstants.MAX_TIME_OF_UU)){//临时人员修改时间
			infoResult.setModifyTime(DictionaryValueConstants.MAX_TIME_OF_FOSS);
		}/*else if(StringUtil.equal(mdmEmpInfo.getEndTime(), DictionaryValueConstants.MAX_TIME_OF_UU)){//UU最大时间与FOSS最大时间转换
			infoResult.setModifyTime(DictionaryValueConstants.MAX_TIME_OF_FOSS);
		}*/
		//创建人
		infoResult.setCreateUser(mdmEmpInfo.getCreateUser());
		//修改人
		infoResult.setModifyUser(mdmEmpInfo.getModifyUser());
		//职等
		infoResult.setDegree(mdmEmpInfo.getPositionGrade());
		//职位
		infoResult.setTitle(mdmEmpInfo.getJobCode());
		//办公邮箱
		infoResult.setEmail(mdmEmpInfo.getOfficeEmail());
		//工号
		infoResult.setEmpCode(mdmEmpInfo.getEmpCode().trim());
		//员工姓名
		infoResult.setEmpName(mdmEmpInfo.getEmpName().trim());
		//入职时间
		infoResult.setEntryDate(mdmEmpInfo.getInDate());
		//离职时间
		infoResult.setLeaveDate(mdmEmpInfo.getOutDate());
		//性别
		infoResult.setGender((Integer)mdmEmpInfo.getGender()==null?null:String.valueOf(mdmEmpInfo.getGender()));
		//身份证号码
		infoResult.setIdentityCard(mdmEmpInfo.getDocNumber());
		//手机号码
		infoResult.setMobilePhone(mdmEmpInfo.getMobile());
		//所属部门
		infoResult.setOrgCode(mdmEmpInfo.getDeptCode());
		//所属部门名称
		infoResult.setOrgName(mdmEmpInfo.getDeptName());
		//办公电话
		infoResult.setPhone(mdmEmpInfo.getOfficeTel());
		//部门标杆编码
		infoResult.setUnifieldCode(mdmEmpInfo.getDeptStandCode());
		//是否临时人员
		infoResult.setIsTempEmp(mdmEmpInfo.isIsTempEmp()?FossConstants.YES:FossConstants.NO);
		
		return infoResult;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
