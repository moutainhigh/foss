/* Copyright ©2014 www.deppon.com. All rights reserved. */
package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOverdueClaimPaymentDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOverdueClaimPaymentService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueClaimPaymentDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送超时理赔付款到QMS
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 上午11:27:16,content: </p>
 * @author 105762
 * @date 2014-7-28 上午11:27:16
 * @since
 * @version
 */
public class OverdueClaimPaymentService implements IOverdueClaimPaymentService {
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OverdueClaimPaymentService.class);

    //调用地址
    private static String ESB_CODE = "/ESB_FOSS2ESB_ERROR_AUTO_REPORT_FOSS";

	/**
	 * dao
	 */
	private IOverdueClaimPaymentDao overdueClaimPaymentDao;

	/**
	 * Job记录Service
	 */
	private IJOBTimestampService jobTimestampService;

	/*job 名称*/
	private final static String JOB_NAME="OverdueClaimPaymentJob";
    private String esbRsAddr;

    /**
     * 部门信息
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    private IEmployeeService employeeService;



    /**
	 * <p>发送超时理赔付款到QMS</p>
	 * @author 105762
	 * @throws ESBException
	 * @date 2014-7-28 上午11:28:07
	 * @see
	 */
	public void process() throws ESBException {
		LOGGER.info("发送超时理赔付款到QMS 开始...");

		//1.查询上次Job执行时间
		Date lastExecuteTime = jobTimestampService.getJOBTimestamp(JOB_NAME);

		//当前执行时间 
		Date now = new Date();
		
		
		//结束时间
		Calendar   end   =   Calendar.getInstance();
		end.add(Calendar.DAY_OF_MONTH, -1);
		Date endTime=end.getTime();
		
		//开始时间
		Calendar   begin   =   Calendar.getInstance();   
		begin.add(Calendar.DAY_OF_MONTH, -1); 
		begin.add(Calendar.MINUTE ,-20);
		Date beginTime=begin.getTime();
		
		//当前时间current time
		Calendar   current   =   Calendar.getInstance();
		current.add(Calendar.MINUTE, -20);
		Date currentTime = current.getTime();
		
		
		//2.查询上次执行时间到当前时间的超时理赔付款数据
        //查询参数
        Map param = new HashMap();

        //设置时间范围
        param.put("beginTime",beginTime);
        param.put("endTime",endTime);
        param.put("currentTime", currentTime);
        //设置一些其它条件
        //单据需有效
        param.put("active", FossConstants.YES);
        //付款单没有支付完成的状态
        param.put("paymentStatusNotPaid", SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);//未汇款
        /*理赔付款差错上报，只有未支付的状态才上报*/
        param.put("paymentStatusInPayingProcess", SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);//汇款中
        //理赔单据类型
        param.put("claimTypeC",SettlementDictionaryConstants.BILL_CLAIM__TYPE__CLAIM);//理赔

        //快递类型的单据
        param.put("productCodeList", SettlementUtil.createPackageProductCodeList());
        //param.put("productCodeList", null);
		List<OverdueClaimPaymentDto> list = overdueClaimPaymentDao.queryOverdueClaimPaymentData(param);

		//3.发送数据
        sendDataToQMS(list);
		//4.更新当前时间到Job记录
		if(lastExecuteTime == null){
			//JOBTimestampEntity entity = new JOBTimestampEntity();
			jobTimestampService.addJOBTimestamp(JOB_NAME,now,"发送超时理赔付款到QMS");
		} else {
			jobTimestampService.updateJOBTimestamp(JOB_NAME,now);
		}

		LOGGER.info("发送超时理赔付款到QMS 结束...");
	}

    /*需要设置的表头*/
    private final static String[] KD_MAININFO_HEADERS = {"errorTypeId","errCategoty","mainInfo","kdsubHasInfo","returnResult"};
    private final static String[] LD_MAININFO_HEADERS = {"errorTypeId","errCategoty","mainInfo","ldsubHasInfo","returnResult"};
	/*零担（L） 快递（K）*/
	private final static String QMS_ERR_CATEGORY_PACKAGE = "K";
    private final static String QMS_ERR_CATEGORY_LTC = "L";
    private final static Long DOC_STANDAR_ID_PKG = 2055L;
    private final static Long DOC_STANDAR_ID_LTC = 1094L;

	/*差错编号为：零担理赔付款超时（K201503250024）*/
	private final static String QMS_ERR_TYPE_ID_OVERDUE_CLAIM_PKG = "K201503250024";
    private final static String QMS_ERR_TYPE_ID_OVERDUE_CLAIM_LTC = "L201503250045";

    private final static String CASHIER_JOB_NAME = "收银员";

    /**
     * 发送消息到QMS
     *
     * @param list
     */
    private void sendDataToQMS(List<OverdueClaimPaymentDto> list) {

        for (OverdueClaimPaymentDto dto : list) {
            Map param = new HashMap();
            //处理责任人
            String repEmpcode = null;
            String repEmpname = null;
            String repEmpJob = null;
            List<EmployeeEntity> cashiers = findCashierByOrgUnifiedCode(dto.getResponsibleDeptUnfiedCode());
            if(cashiers.size() == 1){
                EmployeeEntity entity = cashiers.get(0);
                repEmpcode = entity.getEmpCode();
                repEmpname = entity.getEmpName();
                repEmpJob = CASHIER_JOB_NAME;
            } else {
                OrgAdministrativeInfoEntity orgInfo = getResponsible(dto.getResponsibleDeptUnfiedCode());
                if(orgInfo != null && StringUtils.isNotBlank(orgInfo.getPrincipalNo())){
                    EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(orgInfo.getPrincipalNo());
                    repEmpcode = employeeEntity.getEmpCode();
                    repEmpname = employeeEntity.getEmpName();
                    repEmpJob = employeeEntity.getTitleName();
                }
            }
            if(StringUtils.isBlank(repEmpcode)) {
                repEmpcode = "000001";
                repEmpname = "未知责任人";
                repEmpJob = "未知责任人";
            }

            //快递
            if (SettlementUtil.isPackageProductCode(dto.getProductCode())) {
                //main info
                ErrorMainEntity mainInfo = new ErrorMainEntity();
                mainInfo.setRepEmpcode(repEmpcode);
                mainInfo.setRepEmpName(repEmpname);
                mainInfo.setDocStandarId(DOC_STANDAR_ID_PKG);
                mainInfo.setWayBillNum(dto.getWaybillNo());
                mainInfo.setRepDeptCode("FOSS");
                mainInfo.setRepDeptName("FOSS");
                mainInfo.setRepEmpJob(repEmpJob);

                //sub info
                KDErrSubHasWaybillEntity subInfo = new KDErrSubHasWaybillEntity();
                //上报人
                subInfo.setRespEmpCode(repEmpcode);
                subInfo.setRespEmpName(repEmpname);
                //上报部门
                subInfo.setRespDeptCode(dto.getResponsibleDeptCode());
                subInfo.setRespDeptName(dto.getResponsibleDeptName());
                //开单部门
                subInfo.setBillingDeptCode(dto.getBillingDeptCode());
                subInfo.setBillingDeptName(dto.getBillingDeptName());
                subInfo.setClaimAmount("" + dto.getAmount());
                subInfo.setClaimsDeptCode(dto.getResponsibleDeptUnfiedCode());
                subInfo.setClaimsDeptName(dto.getResponsibleDeptName());
                subInfo.setTransportProperties(dto.getProductCode());
                subInfo.setCrmWfsFinishTime((DateUtils.addDayToDate(dto.getClaimConfirmTime(), -1)).toString());
                int i = 0;
                param.put(KD_MAININFO_HEADERS[i++], QMS_ERR_TYPE_ID_OVERDUE_CLAIM_PKG);
                param.put(KD_MAININFO_HEADERS[i++], QMS_ERR_CATEGORY_PACKAGE);
                param.put(KD_MAININFO_HEADERS[i++], mainInfo); // mainInfo
                param.put(KD_MAININFO_HEADERS[i++], subInfo); // subInfo
                param.put(KD_MAININFO_HEADERS[i++], false); //无需返回
            } else {
                //零担
                //main info
                ErrorMainEntity mainInfo = new ErrorMainEntity();
                mainInfo.setRepEmpcode(repEmpcode);
                mainInfo.setRepEmpName(repEmpname);
                mainInfo.setDocStandarId(DOC_STANDAR_ID_LTC);
                mainInfo.setWayBillNum(dto.getWaybillNo());
                mainInfo.setRepDeptCode("FOSS");
                mainInfo.setRepDeptName("FOSS");
                mainInfo.setRepEmpJob(repEmpJob);

                //sub info
                LDErrSubHasWaybillEntity subInfo = new LDErrSubHasWaybillEntity();
                //上报人
                subInfo.setRespEmpCode(repEmpcode);
                subInfo.setRespEmpName(repEmpname);
                //上报部门
                subInfo.setRespDeptCode(dto.getResponsibleDeptCode());
                subInfo.setRespDeptName(dto.getResponsibleDeptName());
                //开单部门
                subInfo.setBillingDeptCode(dto.getBillingDeptCode());
                subInfo.setBillingDeptName(dto.getBillingDeptName());
                subInfo.setClaimAmount("" + dto.getAmount());
                subInfo.setClaimsDeptCode(dto.getResponsibleDeptUnfiedCode());
                subInfo.setClaimsDeptName(dto.getResponsibleDeptName());
                subInfo.setTransportProperties(dto.getProductCode());
                subInfo.setTransNature(dto.getProductCode());
                subInfo.setCrmWfsFinishTime((DateUtils.addDayToDate(dto.getClaimConfirmTime(), -1)).toString());
                int i = 0;
                param.put(LD_MAININFO_HEADERS[i++], QMS_ERR_TYPE_ID_OVERDUE_CLAIM_LTC);
                param.put(LD_MAININFO_HEADERS[i++], QMS_ERR_CATEGORY_LTC);
                param.put(LD_MAININFO_HEADERS[i++], mainInfo); // mainInfo
                param.put(LD_MAININFO_HEADERS[i++], subInfo); // subInfo
                param.put(LD_MAININFO_HEADERS[i++], false); //无需返回
            }
            try {
                StringRequestEntity entity = new StringRequestEntity(JSON.toJSONString(param), "application/json", "UTF-8");
                PostMethod post = new PostMethod(esbRsAddr + ESB_CODE);
                post.setRequestEntity(entity);
                new HttpClient().executeMethod(post);

            } catch (Exception e) {
                throw new SettlementException("发送消息到QMS失败:" + e.getMessage());
            }
        }
    }

    /**
     * 查询部门收银员
     */
    public List<EmployeeEntity> findCashierByOrgUnifiedCode(String orgUnifiedCode){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setUnifieldCode(orgUnifiedCode);
        employeeEntity.setTitle("收银员");
        List<EmployeeEntity> list = overdueClaimPaymentDao.queryEmployeeAndUserByEntity(employeeEntity);

        return list;
    }

    /**
     * 通过当前部门，查询负责人工号和名字
     */
    public OrgAdministrativeInfoEntity getResponsible(String orgUnifiedCode){
        int i = 0;
        OrgAdministrativeInfoEntity org = null;
        //OrgAdministrativeInfoEntity orgParent = null;

        org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(orgUnifiedCode);

        while (org != null && StringUtils.isBlank(org.getPrincipalNo())) {
            //根据上级组织标杆编码查询上级组织
            org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());

            i++;
        }

        return org;
    }
    public void setOverdueClaimPaymentDao(IOverdueClaimPaymentDao overdueClaimPaymentDao) {
        this.overdueClaimPaymentDao = overdueClaimPaymentDao;
    }
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public void setJobTimestampService(IJOBTimestampService jobTimestampService) {
        this.jobTimestampService = jobTimestampService;
    }

    public String getEsbRsAddr() {
        return esbRsAddr;
    }

    public void setEsbRsAddr(String esbRsAddr) {
        this.esbRsAddr = esbRsAddr;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
}

class ErrorMainEntity  {

    private static final long serialVersionUID = -9021959415392159800L;

    //差错编号
    private String errorId;
    //上报人工号
    private String repEmpcode;
    //上报人姓名
    private String repEmpName;
    //上报人职位
    private String repEmpJob;
    //上报人部门标杆编码
    private String repDeptCode;
    //上报人部门名称
    private String repDeptName;
    //差错类别（数据字典代码：errorCategory）
    private String errorCategory;
    private String errorCategoryVal;
    //差错类型id
    private String errorTypeId;
    //差错类型名称
    private String errorTypeName;
    //文件标准id
    private long docStandarId;
    //文件标准名称
    private String docStandarName;
    //差错状态（数据字典代码：errStatus）
    private String errorStatus;
    //上报时间
    private String repTime;
    //运单号
    private String wayBillNum;
    //上报人事业部标杆编码
    private String repDivisionCode;
    private String repDivisionName;
    //收货部门标杆编码
    private String receiveDeptCode;
    //收货部门名称
    private String receiveDeptName;
    //最后的反馈有效时间
    private String lastAvaibleFBTime;
    //是否已删除（数据字典代码：yesorno）
    private String del;

    private String namespace;
    private String needFeedback;
    /**
     * errorId <p>getter method</p>
     * @author 150976
     * @return  the errorId
     */
    public String getErrorId() {
        return errorId;
    }
    /**
     * errorId <p>setter method</p>
     * @author 150976
     * @param errorId the errorId to set
     */
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }
    /**
     * repEmpcode <p>getter method</p>
     * @author 150976
     * @return  the repEmpcode
     */
    public String getRepEmpcode() {
        return repEmpcode;
    }
    /**
     * repEmpcode <p>setter method</p>
     * @author 150976
     * @param repEmpcode the repEmpcode to set
     */
    public void setRepEmpcode(String repEmpcode) {
        this.repEmpcode = repEmpcode;
    }
    /**
     * repEmpName <p>getter method</p>
     * @author 150976
     * @return  the repEmpName
     */
    public String getRepEmpName() {
        return repEmpName;
    }
    /**
     * repEmpName <p>setter method</p>
     * @author 150976
     * @param repEmpName the repEmpName to set
     */
    public void setRepEmpName(String repEmpName) {
        this.repEmpName = repEmpName;
    }
    /**
     * repEmpJob <p>getter method</p>
     * @author 150976
     * @return  the repEmpJob
     */
    public String getRepEmpJob() {
        return repEmpJob;
    }
    /**
     * repEmpJob <p>setter method</p>
     * @author 150976
     * @param repEmpJob the repEmpJob to set
     */
    public void setRepEmpJob(String repEmpJob) {
        this.repEmpJob = repEmpJob;
    }
    /**
     * repDeptCode <p>getter method</p>
     * @author 150976
     * @return  the repDeptCode
     */
    public String getRepDeptCode() {
        return repDeptCode;
    }
    /**
     * repDeptCode <p>setter method</p>
     * @author 150976
     * @param repDeptCode the repDeptCode to set
     */
    public void setRepDeptCode(String repDeptCode) {
        this.repDeptCode = repDeptCode;
    }
    /**
     * repDeptName <p>getter method</p>
     * @author 150976
     * @return  the repDeptName
     */
    public String getRepDeptName() {
        return repDeptName;
    }
    /**
     * repDeptName <p>setter method</p>
     * @author 150976
     * @param repDeptName the repDeptName to set
     */
    public void setRepDeptName(String repDeptName) {
        this.repDeptName = repDeptName;
    }
    /**
     * errorCategory <p>getter method</p>
     * @author 150976
     * @return  the errorCategory
     */
    public String getErrorCategory() {
        return errorCategory;
    }
    /**
     * errorCategory <p>setter method</p>
     * @author 150976
     * @param errorCategory the errorCategory to set
     */
    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }
    /**
     * errorCategoryVal <p>getter method</p>
     * @author 150976
     * @return  the errorCategoryVal
     */
    public String getErrorCategoryVal() {
        return errorCategoryVal;
    }
    /**
     * errorCategoryVal <p>setter method</p>
     * @author 150976
     * @param errorCategoryVal the errorCategoryVal to set
     */
    public void setErrorCategoryVal(String errorCategoryVal) {
        this.errorCategoryVal = errorCategoryVal;
    }
    /**
     * errorTypeId <p>getter method</p>
     * @author 150976
     * @return  the errorTypeId
     */
    public String getErrorTypeId() {
        return errorTypeId;
    }
    /**
     * errorTypeId <p>setter method</p>
     * @author 150976
     * @param errorTypeId the errorTypeId to set
     */
    public void setErrorTypeId(String errorTypeId) {
        this.errorTypeId = errorTypeId;
    }
    /**
     * docStandarId <p>getter method</p>
     * @author 150976
     * @return  the docStandarId
     */
    public long getDocStandarId() {
        return docStandarId;
    }
    /**
     * docStandarId <p>setter method</p>
     * @author 150976
     * @param docStandarId the docStandarId to set
     */
    public void setDocStandarId(long docStandarId) {
        this.docStandarId = docStandarId;
    }
    /**
     * errorStatus <p>getter method</p>
     * @author 150976
     * @return  the errorStatus
     */
    public String getErrorStatus() {
        return errorStatus;
    }
    /**
     * errorStatus <p>setter method</p>
     * @author 150976
     * @param errorStatus the errorStatus to set
     */
    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }
    /**
     * repTime <p>getter method</p>
     * @author 150976
     * @return  the repTime
     */
    public String getRepTime() {
        return repTime;
    }
    /**
     * repTime <p>setter method</p>
     * @author 150976
     * @param repTime the repTime to set
     */
    public void setRepTime(String repTime) {
        this.repTime = repTime;
    }
    /**
     * wayBillNum <p>getter method</p>
     * @author 150976
     * @return  the wayBillNum
     */
    public String getWayBillNum() {
        return wayBillNum;
    }
    /**
     * wayBillNum <p>setter method</p>
     * @author 150976
     * @param wayBillNum the wayBillNum to set
     */
    public void setWayBillNum(String wayBillNum) {
        this.wayBillNum = wayBillNum;
    }
    /**
     * repDivisionCode <p>getter method</p>
     * @author 150976
     * @return  the repDivisionCode
     */
    public String getRepDivisionCode() {
        return repDivisionCode;
    }
    /**
     * repDivisionCode <p>setter method</p>
     * @author 150976
     * @param repDivisionCode the repDivisionCode to set
     */
    public void setRepDivisionCode(String repDivisionCode) {
        this.repDivisionCode = repDivisionCode;
    }
    /**
     * repDivisionName <p>getter method</p>
     * @author 150976
     * @return  the repDivisionName
     */
    public String getRepDivisionName() {
        return repDivisionName;
    }
    /**
     * repDivisionName <p>setter method</p>
     * @author 150976
     * @param repDivisionName the repDivisionName to set
     */
    public void setRepDivisionName(String repDivisionName) {
        this.repDivisionName = repDivisionName;
    }
    /**
     * receiveDeptCode <p>getter method</p>
     * @author 150976
     * @return  the receiveDeptCode
     */
    public String getReceiveDeptCode() {
        return receiveDeptCode;
    }
    /**
     * receiveDeptCode <p>setter method</p>
     * @author 150976
     * @param receiveDeptCode the receiveDeptCode to set
     */
    public void setReceiveDeptCode(String receiveDeptCode) {
        this.receiveDeptCode = receiveDeptCode;
    }
    /**
     * receiveDeptName <p>getter method</p>
     * @author 150976
     * @return  the receiveDeptName
     */
    public String getReceiveDeptName() {
        return receiveDeptName;
    }
    /**
     * receiveDeptName <p>setter method</p>
     * @author 150976
     * @param receiveDeptName the receiveDeptName to set
     */
    public void setReceiveDeptName(String receiveDeptName) {
        this.receiveDeptName = receiveDeptName;
    }
    /**
     * lastAvaibleFBTime <p>getter method</p>
     * @author 150976
     * @return  the lastAvaibleFBTime
     */
    public String getLastAvaibleFBTime() {
        return lastAvaibleFBTime;
    }
    /**
     * lastAvaibleFBTime <p>setter method</p>
     * @author 150976
     * @param lastAvaibleFBTime the lastAvaibleFBTime to set
     */
    public void setLastAvaibleFBTime(String lastAvaibleFBTime) {
        this.lastAvaibleFBTime = lastAvaibleFBTime;
    }
    /**
     * del <p>getter method</p>
     * @author 150976
     * @return  the del
     */
    public String getDel() {
        return del;
    }
    /**
     * del <p>setter method</p>
     * @author 150976
     * @param del the del to set
     */
    public void setDel(String del) {
        this.del = del;
    }
    /**
     * errorTypeName <p>getter method</p>
     * @author 150976
     * @return  the errorTypeName
     */
    public String getErrorTypeName() {
        return errorTypeName;
    }
    /**
     * errorTypeName <p>setter method</p>
     * @author 150976
     * @param errorTypeName the errorTypeName to set
     */
    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }
    /**
     * docStandarName <p>getter method</p>
     * @author 150976
     * @return  the docStandarName
     */
    public String getDocStandarName() {
        return docStandarName;
    }
    /**
     * docStandarName <p>setter method</p>
     * @author 150976
     * @param docStandarName the docStandarName to set
     */
    public void setDocStandarName(String docStandarName) {
        this.docStandarName = docStandarName;
    }
    /**
     * namespace <p>getter method</p>
     * @author 150976
     * @return  the namespace
     */
    public String getNamespace() {
        return namespace;
    }
    /**
     * namespace <p>setter method</p>
     * @author 150976
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    /**
     * needFeedback <p>getter method</p>
     * @author 150976
     * @return  the needFeedback
     */
    public String getNeedFeedback() {
        return needFeedback;
    }
    /**
     * needFeedback <p>setter method</p>
     * @author 150976
     * @param needFeedback the needFeedback to set
     */
    public void setNeedFeedback(String needFeedback) {
        this.needFeedback = needFeedback;
    }

}


/**
 *
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:150976,date:2015-4-7 下午7:31:28 </p>
 * @since
 * 项目名称：
 * 模块名称：qms-error
 * 类描述：
 * 创建人：张怡君（150976）
 * 创建时间：2015-4-7 下午7:31:28
 * 修改人：张怡君（150976）
 * 修改时间：2015-4-7 下午7:31:28
 * 修改备注：
 * @version V1.0
 * @Copyright 2015 德邦物流 Inc. All rights reserved.
 *
 * <p>该类是由办公系统开发组成员开发编写</p>
 * <p>任何问题请联系作者150976</p>
 *
 * <p>类参与规则：<p>
 * rule:
 */
class KDErrSubHasWaybillEntity extends ErrorSubEntity {

    private static final long serialVersionUID = 5590338260954176092L;

    private Long subHasWaybillId;

    //经手部门标杆编码
    private String handedDeptCode;

    //经手部门名称
    private String handedDeptName;

    //开单部门标杆编码
    private String billingDeptCode;

    //开单部门名称
    private String billingDeptName;

    //发货客户编码
    private String deliveryCustomerCode;

    //收货客户编码
    private String receivingCustomerCode;

    //是否集中接货
    private String centralizedPick;

    //是否驻地部门
    private String residentSector;

    //运输类型
    private String transportType;

    //托运人姓名
    private String consignor;

    //运输性质
    private String transportProperties;

    //收货人
    private String consignee;

    //储运事项
    private String storageItems;

    //收货人电话
    private String receiverPhone;

    //收货人地址
    private String consigneeAddress;

    //提货方式
    private String deliveryMethods;

    //重量
    private String weight;

    //体积
    private String volume;

    //尺寸
    private String fsize;

    //件数
    private String fnumber;

    //货物名称
    private String goodsName;

    //发货时间
    private String deliveryTime;

    //到达部门标杆编码
    private String arriveDeptCode;

    //到达部门名称
    private String arriveDeptName;

    //付款方式
    private String paymentMethod;

    //保险金额
    private String insuranceAmount;

    //货物包装
    private String goodsPackage;

    //运费总额
    private String totalFreight;

    //填开人工号
    private String fillopenEmpCode;

    //填开人姓名
    private String fillopenEmpName;

    //司机工号
    private String driverEmpCode;

    //司机姓名
    private String driverEmpName;

    //司机所在部门标杆编码
    private String driverDeptCode;

    //司机所在部门名称
    private String driverDeptName;

    //补录时间
    private String makeupTime;

    //运单经手上一部门标杆编码
    private String lastHandedDeptCode;

    //运单经手上一部门名称
    private String lastHandedDeptName;

    //运单补录部门标杆编码
    private String makeupDeptCode;

    //运单补录部门名称
    private String makeupDeptName;

    //是否有闹事倾向
    private String makeTrouble;

    //理赔所属部门标杆编码
    private String claimsDeptCode;

    //理赔所属部门名称
    private String claimsDeptName;

    //签收类型
    private String signType;

    //签收时间
    private String signTime;

    //索赔金额
    private String claimAmount;

    //CRM理赔工作流审批完成时间
    private String crmWfsFinishTime;

    //交接单/配载单编号
    private String eir;

    //车牌号
    private String licensePlateNumber;

    //返单类型
    private String backBillingType;

    //外包装是否完好
    private String packagingOk;

    //内物短少流水号
    private String innerShortSerialCode;

    //短少量
    private String shortAmount;

    //货物实际重量
    private String actualWeight;

    //实际尺寸
    private String actualSize;

    //实际体积
    private String actualVolume;

    //装车部门标杆编码
    private String loadingDeptCode;

    //装车部门名称
    private String loadingDeptName;

    //卸车部门标杆编码
    private String unloadingDeptCode;

    //卸车部门名称
    private String unloadingDeptName;

    //卸车任务编号
    private String unloadingTaskNumber;

    //有货无交接
    private String notransferGoods;

    //建包部门标杆编码
    private String builtPackDeptCode;

    //建包部门名称
    private String builtPackDeptName;

    //解包部门标杆编码
    private String unpackDeptCode;

    //解包部门名称
    private String unpackDeptName;

    //是否破损
    private String broken;

    //违规类型
    private String violationType;

    //多货类型
    private String moreCargoType;

    //多货件数
    private String moreShipments;

    //多货流水号
    private String moregoodsSerialNum;

    //上报方式
    private String reportingMethods;

    //丢货类型
    private String lostCargoType;

    //找到类型
    private String findType;

    //是否整票
    private String entireTicket;

    //发现方式
    private String findWay;

    //责任人直属上级工号
    private String respSupervisorCode;

    //责任人直属上级姓名
    private String respSupervisorName;

    //责任人的高级经理工号
    private String respSeniorManagerCode;

    //责任人的高级经理姓名
    private String respSeniorManagerName;

    //责任部门负责人工号
    private String respDeptResperCode;

    //责任部门负责人姓名
    private String respDeptResperName;

    //责任人部门负责人工号
    private String repserDeptResperCode;

    //责任人部门负责人姓名
    private String repserDeptResperName;

    //责任大区标杆编码
    private String respRegionCode;

    //责任大区名称
    private String respRegionName;

    //责任营业区标杆编码
    private String respBusinessDistrictCode;

    //责任营业区名称
    private String respBusinessDistrictName;

    //目的站
    private String targetStation;

    //发现人工号
    private  String findPersonCode;

    //发现人姓名
    private String findPersonName;

    //开单时间
    private String billingTime;

    //开单错误类型
    private String billingErrType;

    //是否签收出库
    private String isSignOutLib;

    //开单人工号
    private String billingEmpCode;

    //开单人姓名
    private String billingEmpName;


    /**
     * findPersonCode <p>getter method</p>
     * @author 150976
     * @return  the handedDeptCode
     */
    public String getFindPersonCode() {
        return findPersonCode;
    }
    /**
     * findPersonCode <p>setter method</p>
     * @author 150976
     * @param findPersonCode the findPersonCode to set
     */
    public void setFindPersonCode(String findPersonCode) {
        this.findPersonCode = findPersonCode;
    }
    /**
     * findPersonName <p>getter method</p>
     * @author 150976
     * @return  the findPersonName
     */
    public String getFindPersonName() {
        return findPersonName;
    }
    /**
     * findPersonName <p>getter method</p>
     * @author 150976
     * @param findPersonName the findPersonName to get
     */
    public void setFindPersonName(String findPersonName) {
        this.findPersonName = findPersonName;
    }

    /**
     * subHasWaybillId <p>getter method</p>
     * @author 150976
     * @return  the subHasWaybillId
     */
    public Long getSubHasWaybillId() {
        return subHasWaybillId;
    }

    /**
     * subHasWaybillId <p>setter method</p>
     * @author 150976
     * @param subHasWaybillId the subHasWaybillId to set
     */
    public void setSubHasWaybillId(Long subHasWaybillId) {
        this.subHasWaybillId = subHasWaybillId;
    }

    /**
     * handedDeptCode <p>getter method</p>
     * @author 150976
     * @return  the handedDeptCode
     */
    public String getHandedDeptCode() {
        return handedDeptCode;
    }

    /**
     * handedDeptCode <p>setter method</p>
     * @author 150976
     * @param handedDeptCode the handedDeptCode to set
     */
    public void setHandedDeptCode(String handedDeptCode) {
        this.handedDeptCode = handedDeptCode;
    }

    /**
     * handedDeptName <p>getter method</p>
     * @author 150976
     * @return  the handedDeptName
     */
    public String getHandedDeptName() {
        return handedDeptName;
    }

    /**
     * handedDeptName <p>setter method</p>
     * @author 150976
     * @param handedDeptName the handedDeptName to set
     */
    public void setHandedDeptName(String handedDeptName) {
        this.handedDeptName = handedDeptName;
    }

    /**
     * billingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the billingDeptCode
     */
    public String getBillingDeptCode() {
        return billingDeptCode;
    }

    /**
     * billingDeptCode <p>setter method</p>
     * @author 150976
     * @param billingDeptCode the billingDeptCode to set
     */
    public void setBillingDeptCode(String billingDeptCode) {
        this.billingDeptCode = billingDeptCode;
    }

    /**
     * billingDeptName <p>getter method</p>
     * @author 150976
     * @return  the billingDeptName
     */
    public String getBillingDeptName() {
        return billingDeptName;
    }

    /**
     * billingDeptName <p>setter method</p>
     * @author 150976
     * @param billingDeptName the billingDeptName to set
     */
    public void setBillingDeptName(String billingDeptName) {
        this.billingDeptName = billingDeptName;
    }

    /**
     * deliveryCustomerCode <p>getter method</p>
     * @author 150976
     * @return  the deliveryCustomerCode
     */
    public String getDeliveryCustomerCode() {
        return deliveryCustomerCode;
    }

    /**
     * deliveryCustomerCode <p>setter method</p>
     * @author 150976
     * @param deliveryCustomerCode the deliveryCustomerCode to set
     */
    public void setDeliveryCustomerCode(String deliveryCustomerCode) {
        this.deliveryCustomerCode = deliveryCustomerCode;
    }

    /**
     * receivingCustomerCode <p>getter method</p>
     * @author 150976
     * @return  the receivingCustomerCode
     */
    public String getReceivingCustomerCode() {
        return receivingCustomerCode;
    }

    /**
     * receivingCustomerCode <p>setter method</p>
     * @author 150976
     * @param receivingCustomerCode the receivingCustomerCode to set
     */
    public void setReceivingCustomerCode(String receivingCustomerCode) {
        this.receivingCustomerCode = receivingCustomerCode;
    }

    /**
     * centralizedPick <p>getter method</p>
     * @author 150976
     * @return  the centralizedPick
     */
    public String getCentralizedPick() {
        return centralizedPick;
    }

    /**
     * centralizedPick <p>setter method</p>
     * @author 150976
     * @param centralizedPick the centralizedPick to set
     */
    public void setCentralizedPick(String centralizedPick) {
        this.centralizedPick = centralizedPick;
    }

    /**
     * residentSector <p>getter method</p>
     * @author 150976
     * @return  the residentSector
     */
    public String getResidentSector() {
        return residentSector;
    }

    /**
     * residentSector <p>setter method</p>
     * @author 150976
     * @param residentSector the residentSector to set
     */
    public void setResidentSector(String residentSector) {
        this.residentSector = residentSector;
    }

    /**
     * transportType <p>getter method</p>
     * @author 150976
     * @return  the transportType
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * transportType <p>setter method</p>
     * @author 150976
     * @param transportType the transportType to set
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * consignor <p>getter method</p>
     * @author 150976
     * @return  the consignor
     */
    public String getConsignor() {
        return consignor;
    }

    /**
     * consignor <p>setter method</p>
     * @author 150976
     * @param consignor the consignor to set
     */
    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    /**
     * transportProperties <p>getter method</p>
     * @author 150976
     * @return  the transportProperties
     */
    public String getTransportProperties() {
        return transportProperties;
    }


    /**
     * transportProperties <p>setter method</p>
     * @author 150976
     * @param transportProperties the transportProperties to set
     */
    public void setTransportProperties(String transportProperties) {
        this.transportProperties = transportProperties;
    }

    /**
     * billingErrType <p>getter method</p>
     * @author 150970
     * @return  the billingErrType
     */
    public String getBillingErrType() {
        return billingErrType;
    }
    /**
     * isSignOutLib <p>getter method</p>
     * @author 150970
     * @return  the isSignOutLib
     */
    public String getIsSignOutLib() {
        return isSignOutLib;
    }
    /**
     * billingEmpCode <p>getter method</p>
     * @author 150970
     * @return  the billingEmpCode
     */
    public String getBillingEmpCode() {
        return billingEmpCode;
    }
    /**
     * billingEmpName <p>getter method</p>
     * @author 150970
     * @return  the billingEmpName
     */
    public String getBillingEmpName() {
        return billingEmpName;
    }
    /**
     * billingErrType <p>setter method</p>
     * @author 150970
     * @param billingErrType the billingErrType to set
     */
    public void setBillingErrType(String billingErrType) {
        this.billingErrType = billingErrType;
    }
    /**
     * isSignOutLib <p>setter method</p>
     * @author 150970
     * @param isSignOutLib the isSignOutLib to set
     */
    public void setIsSignOutLib(String isSignOutLib) {
        this.isSignOutLib = isSignOutLib;
    }
    /**
     * billingEmpCode <p>setter method</p>
     * @author 150970
     * @param billingEmpCode the billingEmpCode to set
     */
    public void setBillingEmpCode(String billingEmpCode) {
        this.billingEmpCode = billingEmpCode;
    }
    /**
     * billingEmpName <p>setter method</p>
     * @author 150970
     * @param billingEmpName the billingEmpName to set
     */
    public void setBillingEmpName(String billingEmpName) {
        this.billingEmpName = billingEmpName;
    }
    /**
     * consignee <p>getter method</p>
     * @author 150976
     * @return  the consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * consignee <p>setter method</p>
     * @author 150976
     * @param consignee the consignee to set
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * storageItems <p>getter method</p>
     * @author 150976
     * @return  the storageItems
     */
    public String getStorageItems() {
        return storageItems;
    }

    /**
     * storageItems <p>setter method</p>
     * @author 150976
     * @param storageItems the storageItems to set
     */
    public void setStorageItems(String storageItems) {
        this.storageItems = storageItems;
    }

    /**
     * receiverPhone <p>getter method</p>
     * @author 150976
     * @return  the receiverPhone
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * receiverPhone <p>setter method</p>
     * @author 150976
     * @param receiverPhone the receiverPhone to set
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    /**
     * consigneeAddress <p>getter method</p>
     * @author 150976
     * @return  the consigneeAddress
     */
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    /**
     * consigneeAddress <p>setter method</p>
     * @author 150976
     * @param consigneeAddress the consigneeAddress to set
     */
    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    /**
     * deliveryMethods <p>getter method</p>
     * @author 150976
     * @return  the deliveryMethods
     */
    public String getDeliveryMethods() {
        return deliveryMethods;
    }

    /**
     * deliveryMethods <p>setter method</p>
     * @author 150976
     * @param deliveryMethods the deliveryMethods to set
     */
    public void setDeliveryMethods(String deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    /**
     * weight <p>getter method</p>
     * @author 150976
     * @return  the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * weight <p>setter method</p>
     * @author 150976
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * volume <p>getter method</p>
     * @author 150976
     * @return  the volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * volume <p>setter method</p>
     * @author 150976
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * fsize <p>getter method</p>
     * @author 150976
     * @return  the fsize
     */
    public String getFsize() {
        return fsize;
    }

    /**
     * fsize <p>setter method</p>
     * @author 150976
     * @param fsize the fsize to set
     */
    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    /**
     * fnumber <p>getter method</p>
     * @author 150976
     * @return  the fnumber
     */
    public String getFnumber() {
        return fnumber;
    }

    /**
     * fnumber <p>setter method</p>
     * @author 150976
     * @param fnumber the fnumber to set
     */
    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    /**
     * goodsName <p>getter method</p>
     * @author 150976
     * @return  the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * goodsName <p>setter method</p>
     * @author 150976
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * deliveryTime <p>getter method</p>
     * @author 150976
     * @return  the deliveryTime
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * deliveryTime <p>setter method</p>
     * @author 150976
     * @param deliveryTime the deliveryTime to set
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * arriveDeptCode <p>getter method</p>
     * @author 150976
     * @return  the arriveDeptCode
     */
    public String getArriveDeptCode() {
        return arriveDeptCode;
    }

    /**
     * arriveDeptCode <p>setter method</p>
     * @author 150976
     * @param arriveDeptCode the arriveDeptCode to set
     */
    public void setArriveDeptCode(String arriveDeptCode) {
        this.arriveDeptCode = arriveDeptCode;
    }

    /**
     * arriveDeptName <p>getter method</p>
     * @author 150976
     * @return  the arriveDeptName
     */
    public String getArriveDeptName() {
        return arriveDeptName;
    }

    /**
     * arriveDeptName <p>setter method</p>
     * @author 150976
     * @param arriveDeptName the arriveDeptName to set
     */
    public void setArriveDeptName(String arriveDeptName) {
        this.arriveDeptName = arriveDeptName;
    }

    /**
     * paymentMethod <p>getter method</p>
     * @author 150976
     * @return  the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * paymentMethod <p>setter method</p>
     * @author 150976
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * insuranceAmount <p>getter method</p>
     * @author 150976
     * @return  the insuranceAmount
     */
    public String getInsuranceAmount() {
        return insuranceAmount;
    }

    /**
     * insuranceAmount <p>setter method</p>
     * @author 150976
     * @param insuranceAmount the insuranceAmount to set
     */
    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    /**
     * goodsPackage <p>getter method</p>
     * @author 150976
     * @return  the goodsPackage
     */
    public String getGoodsPackage() {
        return goodsPackage;
    }

    /**
     * goodsPackage <p>setter method</p>
     * @author 150976
     * @param goodsPackage the goodsPackage to set
     */
    public void setGoodsPackage(String goodsPackage) {
        this.goodsPackage = goodsPackage;
    }

    /**
     * totalFreight <p>getter method</p>
     * @author 150976
     * @return  the totalFreight
     */
    public String getTotalFreight() {
        return totalFreight;
    }

    /**
     * totalFreight <p>setter method</p>
     * @author 150976
     * @param totalFreight the totalFreight to set
     */
    public void setTotalFreight(String totalFreight) {
        this.totalFreight = totalFreight;
    }

    /**
     * fillopenEmpCode <p>getter method</p>
     * @author 150976
     * @return  the fillopenEmpCode
     */
    public String getFillopenEmpCode() {
        return fillopenEmpCode;
    }

    /**
     * fillopenEmpCode <p>setter method</p>
     * @author 150976
     * @param fillopenEmpCode the fillopenEmpCode to set
     */
    public void setFillopenEmpCode(String fillopenEmpCode) {
        this.fillopenEmpCode = fillopenEmpCode;
    }

    /**
     * fillopenEmpName <p>getter method</p>
     * @author 150976
     * @return  the fillopenEmpName
     */
    public String getFillopenEmpName() {
        return fillopenEmpName;
    }

    /**
     * fillopenEmpName <p>setter method</p>
     * @author 150976
     * @param fillopenEmpName the fillopenEmpName to set
     */
    public void setFillopenEmpName(String fillopenEmpName) {
        this.fillopenEmpName = fillopenEmpName;
    }

    /**
     * driverEmpCode <p>getter method</p>
     * @author 150976
     * @return  the driverEmpCode
     */
    public String getDriverEmpCode() {
        return driverEmpCode;
    }

    /**
     * driverEmpCode <p>setter method</p>
     * @author 150976
     * @param driverEmpCode the driverEmpCode to set
     */
    public void setDriverEmpCode(String driverEmpCode) {
        this.driverEmpCode = driverEmpCode;
    }

    /**
     * driverEmpName <p>getter method</p>
     * @author 150976
     * @return  the driverEmpName
     */
    public String getDriverEmpName() {
        return driverEmpName;
    }

    /**
     * driverEmpName <p>setter method</p>
     * @author 150976
     * @param driverEmpName the driverEmpName to set
     */
    public void setDriverEmpName(String driverEmpName) {
        this.driverEmpName = driverEmpName;
    }

    /**
     * driverDeptCode <p>getter method</p>
     * @author 150976
     * @return  the driverDeptCode
     */
    public String getDriverDeptCode() {
        return driverDeptCode;
    }

    /**
     * driverDeptCode <p>setter method</p>
     * @author 150976
     * @param driverDeptCode the driverDeptCode to set
     */
    public void setDriverDeptCode(String driverDeptCode) {
        this.driverDeptCode = driverDeptCode;
    }

    /**
     * driverDeptName <p>getter method</p>
     * @author 150976
     * @return  the driverDeptName
     */
    public String getDriverDeptName() {
        return driverDeptName;
    }

    /**
     * driverDeptName <p>setter method</p>
     * @author 150976
     * @param driverDeptName the driverDeptName to set
     */
    public void setDriverDeptName(String driverDeptName) {
        this.driverDeptName = driverDeptName;
    }

    /**
     * makeupTime <p>getter method</p>
     * @author 150976
     * @return  the makeupTime
     */
    public String getMakeupTime() {
        return makeupTime;
    }

    /**
     * makeupTime <p>setter method</p>
     * @author 150976
     * @param makeupTime the makeupTime to set
     */
    public void setMakeupTime(String makeupTime) {
        this.makeupTime = makeupTime;
    }

    /**
     * lastHandedDeptCode <p>getter method</p>
     * @author 150976
     * @return  the lastHandedDeptCode
     */
    public String getLastHandedDeptCode() {
        return lastHandedDeptCode;
    }

    /**
     * lastHandedDeptCode <p>setter method</p>
     * @author 150976
     * @param lastHandedDeptCode the lastHandedDeptCode to set
     */
    public void setLastHandedDeptCode(String lastHandedDeptCode) {
        this.lastHandedDeptCode = lastHandedDeptCode;
    }

    /**
     * lastHandedDeptName <p>getter method</p>
     * @author 150976
     * @return  the lastHandedDeptName
     */
    public String getLastHandedDeptName() {
        return lastHandedDeptName;
    }

    /**
     * lastHandedDeptName <p>setter method</p>
     * @author 150976
     * @param lastHandedDeptName the lastHandedDeptName to set
     */
    public void setLastHandedDeptName(String lastHandedDeptName) {
        this.lastHandedDeptName = lastHandedDeptName;
    }

    /**
     * makeupDeptCode <p>getter method</p>
     * @author 150976
     * @return  the makeupDeptCode
     */
    public String getMakeupDeptCode() {
        return makeupDeptCode;
    }

    /**
     * makeupDeptCode <p>setter method</p>
     * @author 150976
     * @param makeupDeptCode the makeupDeptCode to set
     */
    public void setMakeupDeptCode(String makeupDeptCode) {
        this.makeupDeptCode = makeupDeptCode;
    }

    /**
     * makeupDeptName <p>getter method</p>
     * @author 150976
     * @return  the makeupDeptName
     */
    public String getMakeupDeptName() {
        return makeupDeptName;
    }

    /**
     * makeupDeptName <p>setter method</p>
     * @author 150976
     * @param makeupDeptName the makeupDeptName to set
     */
    public void setMakeupDeptName(String makeupDeptName) {
        this.makeupDeptName = makeupDeptName;
    }

    /**
     * makeTrouble <p>getter method</p>
     * @author 150976
     * @return  the makeTrouble
     */
    public String getMakeTrouble() {
        return makeTrouble;
    }

    /**
     * makeTrouble <p>setter method</p>
     * @author 150976
     * @param makeTrouble the makeTrouble to set
     */
    public void setMakeTrouble(String makeTrouble) {
        this.makeTrouble = makeTrouble;
    }

    /**
     * claimsDeptCode <p>getter method</p>
     * @author 150976
     * @return  the claimsDeptCode
     */
    public String getClaimsDeptCode() {
        return claimsDeptCode;
    }

    /**
     * claimsDeptCode <p>setter method</p>
     * @author 150976
     * @param claimsDeptCode the claimsDeptCode to set
     */
    public void setClaimsDeptCode(String claimsDeptCode) {
        this.claimsDeptCode = claimsDeptCode;
    }

    /**
     * claimsDeptName <p>getter method</p>
     * @author 150976
     * @return  the claimsDeptName
     */
    public String getClaimsDeptName() {
        return claimsDeptName;
    }

    /**
     * claimsDeptName <p>setter method</p>
     * @author 150976
     * @param claimsDeptName the claimsDeptName to set
     */
    public void setClaimsDeptName(String claimsDeptName) {
        this.claimsDeptName = claimsDeptName;
    }

    /**
     * signType <p>getter method</p>
     * @author 150976
     * @return  the signType
     */
    public String getSignType() {
        return signType;
    }

    /**
     * signType <p>setter method</p>
     * @author 150976
     * @param signType the signType to set
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * signTime <p>getter method</p>
     * @author 150976
     * @return  the signTime
     */
    public String getSignTime() {
        return signTime;
    }

    /**
     * signTime <p>setter method</p>
     * @author 150976
     * @param signTime the signTime to set
     */
    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    /**
     * claimAmount <p>getter method</p>
     * @author 150976
     * @return  the claimAmount
     */
    public String getClaimAmount() {
        return claimAmount;
    }

    /**
     * claimAmount <p>setter method</p>
     * @author 150976
     * @param claimAmount the claimAmount to set
     */
    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    /**
     * crmWfsFinishTime <p>getter method</p>
     * @author 150976
     * @return  the crmWfsFinishTime
     */
    public String getCrmWfsFinishTime() {
        return crmWfsFinishTime;
    }

    /**
     * crmWfsFinishTime <p>setter method</p>
     * @author 150976
     * @param crmWfsFinishTime the crmWfsFinishTime to set
     */
    public void setCrmWfsFinishTime(String crmWfsFinishTime) {
        this.crmWfsFinishTime = crmWfsFinishTime;
    }

    /**
     * eir <p>getter method</p>
     * @author 150976
     * @return  the eir
     */
    public String getEir() {
        return eir;
    }

    /**
     * eir <p>setter method</p>
     * @author 150976
     * @param eir the eir to set
     */
    public void setEir(String eir) {
        this.eir = eir;
    }

    /**
     * licensePlateNumber <p>getter method</p>
     * @author 150976
     * @return  the licensePlateNumber
     */
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    /**
     * licensePlateNumber <p>setter method</p>
     * @author 150976
     * @param licensePlateNumber the licensePlateNumber to set
     */
    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    /**
     * backBillingType <p>getter method</p>
     * @author 150976
     * @return  the backBillingType
     */
    public String getBackBillingType() {
        return backBillingType;
    }

    /**
     * backBillingType <p>setter method</p>
     * @author 150976
     * @param backBillingType the backBillingType to set
     */
    public void setBackBillingType(String backBillingType) {
        this.backBillingType = backBillingType;
    }

    /**
     * packagingOk <p>getter method</p>
     * @author 150976
     * @return  the packagingOk
     */
    public String getPackagingOk() {
        return packagingOk;
    }

    /**
     * packagingOk <p>setter method</p>
     * @author 150976
     * @param packagingOk the packagingOk to set
     */
    public void setPackagingOk(String packagingOk) {
        this.packagingOk = packagingOk;
    }

    /**
     * innerShortSerialCode <p>getter method</p>
     * @author 150976
     * @return  the innerShortSerialCode
     */
    public String getInnerShortSerialCode() {
        return innerShortSerialCode;
    }

    /**
     * innerShortSerialCode <p>setter method</p>
     * @author 150976
     * @param innerShortSerialCode the innerShortSerialCode to set
     */
    public void setInnerShortSerialCode(String innerShortSerialCode) {
        this.innerShortSerialCode = innerShortSerialCode;
    }

    /**
     * shortAmount <p>getter method</p>
     * @author 150976
     * @return  the shortAmount
     */
    public String getShortAmount() {
        return shortAmount;
    }

    /**
     * shortAmount <p>setter method</p>
     * @author 150976
     * @param shortAmount the shortAmount to set
     */
    public void setShortAmount(String shortAmount) {
        this.shortAmount = shortAmount;
    }

    /**
     * actualWeight <p>getter method</p>
     * @author 150976
     * @return  the actualWeight
     */
    public String getActualWeight() {
        return actualWeight;
    }

    /**
     * actualWeight <p>setter method</p>
     * @author 150976
     * @param actualWeight the actualWeight to set
     */
    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }

    /**
     * actualSize <p>getter method</p>
     * @author 150976
     * @return  the actualSize
     */
    public String getActualSize() {
        return actualSize;
    }

    /**
     * actualSize <p>setter method</p>
     * @author 150976
     * @param actualSize the actualSize to set
     */
    public void setActualSize(String actualSize) {
        this.actualSize = actualSize;
    }

    /**
     * actualVolume <p>getter method</p>
     * @author 150976
     * @return  the actualVolume
     */
    public String getActualVolume() {
        return actualVolume;
    }

    /**
     * actualVolume <p>setter method</p>
     * @author 150976
     * @param actualVolume the actualVolume to set
     */
    public void setActualVolume(String actualVolume) {
        this.actualVolume = actualVolume;
    }

    /**
     * loadingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the loadingDeptCode
     */
    public String getLoadingDeptCode() {
        return loadingDeptCode;
    }

    /**
     * loadingDeptCode <p>setter method</p>
     * @author 150976
     * @param loadingDeptCode the loadingDeptCode to set
     */
    public void setLoadingDeptCode(String loadingDeptCode) {
        this.loadingDeptCode = loadingDeptCode;
    }

    /**
     * loadingDeptName <p>getter method</p>
     * @author 150976
     * @return  the loadingDeptName
     */
    public String getLoadingDeptName() {
        return loadingDeptName;
    }

    /**
     * loadingDeptName <p>setter method</p>
     * @author 150976
     * @param loadingDeptName the loadingDeptName to set
     */
    public void setLoadingDeptName(String loadingDeptName) {
        this.loadingDeptName = loadingDeptName;
    }

    /**
     * unloadingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the unloadingDeptCode
     */
    public String getUnloadingDeptCode() {
        return unloadingDeptCode;
    }

    /**
     * unloadingDeptCode <p>setter method</p>
     * @author 150976
     * @param unloadingDeptCode the unloadingDeptCode to set
     */
    public void setUnloadingDeptCode(String unloadingDeptCode) {
        this.unloadingDeptCode = unloadingDeptCode;
    }

    /**
     * unloadingDeptName <p>getter method</p>
     * @author 150976
     * @return  the unloadingDeptName
     */
    public String getUnloadingDeptName() {
        return unloadingDeptName;
    }

    /**
     * unloadingDeptName <p>setter method</p>
     * @author 150976
     * @param unloadingDeptName the unloadingDeptName to set
     */
    public void setUnloadingDeptName(String unloadingDeptName) {
        this.unloadingDeptName = unloadingDeptName;
    }

    /**
     * unloadingTaskNumber <p>getter method</p>
     * @author 150976
     * @return  the unloadingTaskNumber
     */
    public String getUnloadingTaskNumber() {
        return unloadingTaskNumber;
    }

    /**
     * unloadingTaskNumber <p>setter method</p>
     * @author 150976
     * @param unloadingTaskNumber the unloadingTaskNumber to set
     */
    public void setUnloadingTaskNumber(String unloadingTaskNumber) {
        this.unloadingTaskNumber = unloadingTaskNumber;
    }

    /**
     * notransferGoods <p>getter method</p>
     * @author 150976
     * @return  the notransferGoods
     */
    public String getNotransferGoods() {
        return notransferGoods;
    }

    /**
     * notransferGoods <p>setter method</p>
     * @author 150976
     * @param notransferGoods the notransferGoods to set
     */
    public void setNotransferGoods(String notransferGoods) {
        this.notransferGoods = notransferGoods;
    }

    /**
     * builtPackDeptCode <p>getter method</p>
     * @author 150976
     * @return  the builtPackDeptCode
     */
    public String getBuiltPackDeptCode() {
        return builtPackDeptCode;
    }

    /**
     * builtPackDeptCode <p>setter method</p>
     * @author 150976
     * @param builtPackDeptCode the builtPackDeptCode to set
     */
    public void setBuiltPackDeptCode(String builtPackDeptCode) {
        this.builtPackDeptCode = builtPackDeptCode;
    }

    /**
     * builtPackDeptName <p>getter method</p>
     * @author 150976
     * @return  the builtPackDeptName
     */
    public String getBuiltPackDeptName() {
        return builtPackDeptName;
    }

    /**
     * builtPackDeptName <p>setter method</p>
     * @author 150976
     * @param builtPackDeptName the builtPackDeptName to set
     */
    public void setBuiltPackDeptName(String builtPackDeptName) {
        this.builtPackDeptName = builtPackDeptName;
    }

    /**
     * unpackDeptCode <p>getter method</p>
     * @author 150976
     * @return  the unpackDeptCode
     */
    public String getUnpackDeptCode() {
        return unpackDeptCode;
    }

    /**
     * unpackDeptCode <p>setter method</p>
     * @author 150976
     * @param unpackDeptCode the unpackDeptCode to set
     */
    public void setUnpackDeptCode(String unpackDeptCode) {
        this.unpackDeptCode = unpackDeptCode;
    }

    /**
     * unpackDeptName <p>getter method</p>
     * @author 150976
     * @return  the unpackDeptName
     */
    public String getUnpackDeptName() {
        return unpackDeptName;
    }

    /**
     * unpackDeptName <p>setter method</p>
     * @author 150976
     * @param unpackDeptName the unpackDeptName to set
     */
    public void setUnpackDeptName(String unpackDeptName) {
        this.unpackDeptName = unpackDeptName;
    }

    /**
     * broken <p>getter method</p>
     * @author 150976
     * @return  the broken
     */
    public String getBroken() {
        return broken;
    }

    /**
     * broken <p>setter method</p>
     * @author 150976
     * @param broken the broken to set
     */
    public void setBroken(String broken) {
        this.broken = broken;
    }

    /**
     * violationType <p>getter method</p>
     * @author 150976
     * @return  the violationType
     */
    public String getViolationType() {
        return violationType;
    }

    /**
     * violationType <p>setter method</p>
     * @author 150976
     * @param violationType the violationType to set
     */
    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    /**
     * moreCargoType <p>getter method</p>
     * @author 150976
     * @return  the moreCargoType
     */
    public String getMoreCargoType() {
        return moreCargoType;
    }

    /**
     * moreCargoType <p>setter method</p>
     * @author 150976
     * @param moreCargoType the moreCargoType to set
     */
    public void setMoreCargoType(String moreCargoType) {
        this.moreCargoType = moreCargoType;
    }

    /**
     * moreShipments <p>getter method</p>
     * @author 150976
     * @return  the moreShipments
     */
    public String getMoreShipments() {
        return moreShipments;
    }

    /**
     * moreShipments <p>setter method</p>
     * @author 150976
     * @param moreShipments the moreShipments to set
     */
    public void setMoreShipments(String moreShipments) {
        this.moreShipments = moreShipments;
    }

    /**
     * moregoodsSerialNum <p>getter method</p>
     * @author 150976
     * @return  the moregoodsSerialNum
     */
    public String getMoregoodsSerialNum() {
        return moregoodsSerialNum;
    }

    /**
     * moregoodsSerialNum <p>setter method</p>
     * @author 150976
     * @param moregoodsSerialNum the moregoodsSerialNum to set
     */
    public void setMoregoodsSerialNum(String moregoodsSerialNum) {
        this.moregoodsSerialNum = moregoodsSerialNum;
    }

    /**
     * reportingMethods <p>getter method</p>
     * @author 150976
     * @return  the reportingMethods
     */
    public String getReportingMethods() {
        return reportingMethods;
    }

    /**
     * reportingMethods <p>setter method</p>
     * @author 150976
     * @param reportingMethods the reportingMethods to set
     */
    public void setReportingMethods(String reportingMethods) {
        this.reportingMethods = reportingMethods;
    }

    /**
     * lostCargoType <p>getter method</p>
     * @author 150976
     * @return  the lostCargoType
     */
    public String getLostCargoType() {
        return lostCargoType;
    }

    /**
     * lostCargoType <p>setter method</p>
     * @author 150976
     * @param lostCargoType the lostCargoType to set
     */
    public void setLostCargoType(String lostCargoType) {
        this.lostCargoType = lostCargoType;
    }

    /**
     * findType <p>getter method</p>
     * @author 150976
     * @return  the findType
     */
    public String getFindType() {
        return findType;
    }

    /**
     * findType <p>setter method</p>
     * @author 150976
     * @param findType the findType to set
     */
    public void setFindType(String findType) {
        this.findType = findType;
    }

    /**
     * entireTicket <p>getter method</p>
     * @author 150976
     * @return  the entireTicket
     */
    public String getEntireTicket() {
        return entireTicket;
    }

    /**
     * entireTicket <p>setter method</p>
     * @author 150976
     * @param entireTicket the entireTicket to set
     */
    public void setEntireTicket(String entireTicket) {
        this.entireTicket = entireTicket;
    }

    /**
     * findWay <p>getter method</p>
     * @author 150976
     * @return  the findWay
     */
    public String getFindWay() {
        return findWay;
    }

    /**
     * findWay <p>setter method</p>
     * @author 150976
     * @param findWay the findWay to set
     */
    public void setFindWay(String findWay) {
        this.findWay = findWay;
    }

    /**
     * respSupervisorCode <p>getter method</p>
     * @author 150976
     * @return  the respSupervisorCode
     */
    public String getRespSupervisorCode() {
        return respSupervisorCode;
    }

    /**
     * respSupervisorCode <p>setter method</p>
     * @author 150976
     * @param respSupervisorCode the respSupervisorCode to set
     */
    public void setRespSupervisorCode(String respSupervisorCode) {
        this.respSupervisorCode = respSupervisorCode;
    }

    /**
     * respSupervisorName <p>getter method</p>
     * @author 150976
     * @return  the respSupervisorName
     */
    public String getRespSupervisorName() {
        return respSupervisorName;
    }

    /**
     * respSupervisorName <p>setter method</p>
     * @author 150976
     * @param respSupervisorName the respSupervisorName to set
     */
    public void setRespSupervisorName(String respSupervisorName) {
        this.respSupervisorName = respSupervisorName;
    }

    /**
     * respSeniorManagerCode <p>getter method</p>
     * @author 150976
     * @return  the respSeniorManagerCode
     */
    public String getRespSeniorManagerCode() {
        return respSeniorManagerCode;
    }

    /**
     * respSeniorManagerCode <p>setter method</p>
     * @author 150976
     * @param respSeniorManagerCode the respSeniorManagerCode to set
     */
    public void setRespSeniorManagerCode(String respSeniorManagerCode) {
        this.respSeniorManagerCode = respSeniorManagerCode;
    }

    /**
     * respSeniorManagerName <p>getter method</p>
     * @author 150976
     * @return  the respSeniorManagerName
     */
    public String getRespSeniorManagerName() {
        return respSeniorManagerName;
    }

    /**
     * respSeniorManagerName <p>setter method</p>
     * @author 150976
     * @param respSeniorManagerName the respSeniorManagerName to set
     */
    public void setRespSeniorManagerName(String respSeniorManagerName) {
        this.respSeniorManagerName = respSeniorManagerName;
    }

    /**
     * respDeptResperCode <p>getter method</p>
     * @author 150976
     * @return  the respDeptResperCode
     */
    public String getRespDeptResperCode() {
        return respDeptResperCode;
    }

    /**
     * respDeptResperCode <p>setter method</p>
     * @author 150976
     * @param respDeptResperCode the respDeptResperCode to set
     */
    public void setRespDeptResperCode(String respDeptResperCode) {
        this.respDeptResperCode = respDeptResperCode;
    }

    /**
     * respDeptResperName <p>getter method</p>
     * @author 150976
     * @return  the respDeptResperName
     */
    public String getRespDeptResperName() {
        return respDeptResperName;
    }

    /**
     * respDeptResperName <p>setter method</p>
     * @author 150976
     * @param respDeptResperName the respDeptResperName to set
     */
    public void setRespDeptResperName(String respDeptResperName) {
        this.respDeptResperName = respDeptResperName;
    }

    /**
     * repserDeptResperCode <p>getter method</p>
     * @author 150976
     * @return  the repserDeptResperCode
     */
    public String getRepserDeptResperCode() {
        return repserDeptResperCode;
    }

    /**
     * repserDeptResperCode <p>setter method</p>
     * @author 150976
     * @param repserDeptResperCode the repserDeptResperCode to set
     */
    public void setRepserDeptResperCode(String repserDeptResperCode) {
        this.repserDeptResperCode = repserDeptResperCode;
    }

    /**
     * repserDeptResperName <p>getter method</p>
     * @author 150976
     * @return  the repserDeptResperName
     */
    public String getRepserDeptResperName() {
        return repserDeptResperName;
    }

    /**
     * repserDeptResperName <p>setter method</p>
     * @author 150976
     * @param repserDeptResperName the repserDeptResperName to set
     */
    public void setRepserDeptResperName(String repserDeptResperName) {
        this.repserDeptResperName = repserDeptResperName;
    }

    /**
     * respRegionCode <p>getter method</p>
     * @author 150976
     * @return  the respRegionCode
     */
    public String getRespRegionCode() {
        return respRegionCode;
    }

    /**
     * respRegionCode <p>setter method</p>
     * @author 150976
     * @param respRegionCode the respRegionCode to set
     */
    public void setRespRegionCode(String respRegionCode) {
        this.respRegionCode = respRegionCode;
    }

    /**
     * respRegionName <p>getter method</p>
     * @author 150976
     * @return  the respRegionName
     */
    public String getRespRegionName() {
        return respRegionName;
    }

    /**
     * respRegionName <p>setter method</p>
     * @author 150976
     * @param respRegionName the respRegionName to set
     */
    public void setRespRegionName(String respRegionName) {
        this.respRegionName = respRegionName;
    }

    /**
     * respBusinessDistrictCode <p>getter method</p>
     * @author 150976
     * @return  the respBusinessDistrictCode
     */
    public String getRespBusinessDistrictCode() {
        return respBusinessDistrictCode;
    }

    /**
     * respBusinessDistrictCode <p>setter method</p>
     * @author 150976
     * @param respBusinessDistrictCode the respBusinessDistrictCode to set
     */
    public void setRespBusinessDistrictCode(String respBusinessDistrictCode) {
        this.respBusinessDistrictCode = respBusinessDistrictCode;
    }

    /**
     * respBusinessDistrictName <p>getter method</p>
     * @author 150976
     * @return  the respBusinessDistrictName
     */
    public String getRespBusinessDistrictName() {
        return respBusinessDistrictName;
    }

    /**
     * respBusinessDistrictName <p>setter method</p>
     * @author 150976
     * @param respBusinessDistrictName the respBusinessDistrictName to set
     */
    public void setRespBusinessDistrictName(String respBusinessDistrictName) {
        this.respBusinessDistrictName = respBusinessDistrictName;
    }

    /**
     * targetStation <p>getter method</p>
     * @author 150976
     * @return  the targetStation
     */
    public String getTargetStation() {
        return targetStation;
    }

    /**
     * targetStation <p>setter method</p>
     * @author 150976
     * @param targetStation the targetStation to set
     */
    public void setTargetStation(String targetStation) {
        this.targetStation = targetStation;
    }

    /**
     * @return  the billingTime
     */
    public String getBillingTime() {
        return billingTime;
    }

    /**
     * @param billingTime the billingTime to set
     */
    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }


}

class ErrorSubEntity  {

    private static final long serialVersionUID = 826759534405657894L;

    //差错编号
    protected String errorId;

    //事情经过
    protected String incident;
    //短信通知对象工号
    protected String shortMessageCodes;
    //短信通知对象名称
    protected String shortMessageNames;
    //责任人工号
    protected String respEmpCode;
    protected String respEmpName;
    //责任部门标杆编码
    protected String respDeptCode;
    protected String respDeptName;
    //责任事业部标杆编码
    protected String divisionCode;
    protected String divisionName;

    /**
     * errorId <p>getter method</p>
     * @author 150976
     * @return  the errorId
     */
    public String getErrorId() {
        return errorId;
    }
    /**
     * errorId <p>setter method</p>
     * @author 150976
     * @param errorId the errorId to set
     */
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }
    /**
     * incident <p>getter method</p>
     * @author 150976
     * @return  the incident
     */
    public String getIncident() {
        return incident;
    }
    /**
     * incident <p>setter method</p>
     * @author 150976
     * @param incident the incident to set
     */
    public void setIncident(String incident) {
        this.incident = incident;
    }
    /**
     * shortMessageCodes <p>getter method</p>
     * @author 150976
     * @return  the shortMessageCodes
     */
    public String getShortMessageCodes() {
        return shortMessageCodes;
    }
    /**
     * shortMessageCodes <p>setter method</p>
     * @author 150976
     * @param shortMessageCodes the shortMessageCodes to set
     */
    public void setShortMessageCodes(String shortMessageCodes) {
        this.shortMessageCodes = shortMessageCodes;
    }
    /**
     * shortMessageNames <p>getter method</p>
     * @author 150976
     * @return  the shortMessageNames
     */
    public String getShortMessageNames() {
        return shortMessageNames;
    }
    /**
     * shortMessageNames <p>setter method</p>
     * @author 150976
     * @param shortMessageNames the shortMessageNames to set
     */
    public void setShortMessageNames(String shortMessageNames) {
        this.shortMessageNames = shortMessageNames;
    }
    /**
     * respEmpCode <p>getter method</p>
     * @author 150976
     * @return  the respEmpCode
     */
    public String getRespEmpCode() {
        return respEmpCode;
    }
    /**
     * respEmpCode <p>setter method</p>
     * @author 150976
     * @param respEmpCode the respEmpCode to set
     */
    public void setRespEmpCode(String respEmpCode) {
        this.respEmpCode = respEmpCode;
    }
    /**
     * respEmpName <p>getter method</p>
     * @author 150976
     * @return  the respEmpName
     */
    public String getRespEmpName() {
        return respEmpName;
    }
    /**
     * respEmpName <p>setter method</p>
     * @author 150976
     * @param respEmpName the respEmpName to set
     */
    public void setRespEmpName(String respEmpName) {
        this.respEmpName = respEmpName;
    }
    /**
     * respDeptCode <p>getter method</p>
     * @author 150976
     * @return  the respDeptCode
     */
    public String getRespDeptCode() {
        return respDeptCode;
    }
    /**
     * respDeptCode <p>setter method</p>
     * @author 150976
     * @param respDeptCode the respDeptCode to set
     */
    public void setRespDeptCode(String respDeptCode) {
        this.respDeptCode = respDeptCode;
    }
    /**
     * respDeptName <p>getter method</p>
     * @author 150976
     * @return  the respDeptName
     */
    public String getRespDeptName() {
        return respDeptName;
    }
    /**
     * respDeptName <p>setter method</p>
     * @author 150976
     * @param respDeptName the respDeptName to set
     */
    public void setRespDeptName(String respDeptName) {
        this.respDeptName = respDeptName;
    }
    /**
     * divisionCode <p>getter method</p>
     * @author 150976
     * @return  the divisionCode
     */
    public String getDivisionCode() {
        return divisionCode;
    }
    /**
     * divisionCode <p>setter method</p>
     * @author 150976
     * @param divisionCode the divisionCode to set
     */
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }
    /**
     * divisionName <p>getter method</p>
     * @author 150976
     * @return  the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }
    /**
     * divisionName <p>setter method</p>
     * @author 150976
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

}

class LDErrSubHasWaybillEntity extends ErrorSubEntity {

    //表id
    private Long subHasWaybillId;

    //是否补录运单
    private String isMakeupWaybill;

    //责任人直接上级编码
    private String respSupervisorCode;

    //责任人直接上级姓名
    private String respSupervisorName;

    //责任经理编码
    private String respManagerCode;

    //责任经理姓名
    private String respManagerName;

    //责任经理直接上级编码
    private String respManagerSuperCode;

    //责任经理直接上级姓名
    private String respManagerSuperName;

    //开单部门标杆编码
    private String billingDeptCode;

    //开单部门名称
    private String billingDeptName;

    //发货客户编码
    private String sendClientCode;

    //收货客户编码
    private String takeOverClientCode;

    //是否集中接货
    private String isConcentReceiving;

    //运输类型
    private String transType;

    //托运人
    private String shipper;

    //运输性质
    private String transNature;

    //收货人
    private String receiverName;

    //实际重量
    private String actualWeight;

    //储运事项
    private String storageTransport;

    //收货人电话
    private String receiverPhone;

    //收货人地址
    private String receiverAddress;

    //提货方式
    private String pickUpType;

    //重量
    private String weight;

    //总重量
    private String sumWeight;

    //体积
    private String volume;

    //总体积
    private String sumVolume;

    //实际体积
    private String actualVolume;

    //件数
    private String num;

    //总件数
    private String sumNumber;

    //货物名称
    private String goodsName;

    //发货时间
    private String sendGoodsTime;

    //开单时间
    private String billingTime;

    //到达部门标杆编码
    private String arriveDeptCode;

    //到达部门名称
    private String arriveDeptName;

    //收货部门标杆编码
    private String takeOverDeptCode;

    //收货部门名称
    private String takeOverDeptName;

    //付款方式
    private String payType;

    //保险金额
    private String safeMoney;

    //保险价值
    private String safeValue;

    //货物包装
    private String goodsPackage;

    //装车部门标杆编码
    private String loadingDeptCode;

    //装车部门名称
    private String loadingDeptName;

    //卸车部门标杆编码
    private String unloadingDeptCode;

    //卸车部门名称
    private String unloadingDeptName;

    //交接单
    private String transferBill;

    //丢货类别
    private String loseGoodsType;

    //少货件数
    private String loseNum;

    //是否整票
    private String isWholeTicket;

    //包装费
    private String packageFee;

    //少货流水号
    private String loseFlowcode;

    //纯运费
    private String pureFee;

    //送货费
    private String deliveryFee;

    //运费总额
    private String freightSumFee;

    //填开人工号
    private String fillopenEmpCode;

    //填开人姓名
    private String fillopenEmpName;

    //填开人所在部门标杆编码
    private String fillBillDeptCode;

    //填开人所在部门名称
    private String fillBillDeptName;

    //司机工号
    private String driverCode;

    //司机姓名
    private String driverName;

    //司机所在部门标杆编码
    private String driverDeptCode;

    //司机所在部门名称
    private String driverDeptName;

    //车牌号
    private String carCode;

    //返单类型
    private String returnBillType;

    //外包装是否完好
    private String packageIsGood;

    //内物短少流水号
    private String goodsLoseFlowcode;

    //短少量
    private String shortNum;

    //卸车任务编号
    private String unloadBusiCode;

    //多货件数
    private String moreGoodsNum;

    //有货无交接
    private String goodsNoTransfer;

    //上一环节部门标杆编码
    private String lastLinkDeptCode;

    //上一环节部门名称
    private String lastLinkDeptName;

    //多货类别
    private String moreGoodsType;

    //多货流水号
    private String moreGoodsFlowcode;

    //损失金额
    private String loseMoney;

    //违规类型
    private String illegalType;

    //破损理赔金额
    private String damageClaimAmount;

    //短少类别
    private String loseType;

    //短少品名
    private String loseName;

    //接送货责任司机标杆编码
    private String receverDriverCode;

    //接送货责任司机姓名
    private String receverDriverName;

    //货物实际经手部门标杆编码
    private String goodsHandleDeptCode;

    //货物实际经手部门名称
    private String goodsHandleDeptName;

    //接送货司机所属部门标杆编码
    private String driverTakeDeptCode;

    //接送货司机所属部门名称
    private String driverTakeDeptName;

    //预付金额
    private String prepayMoney;

    //到付金额
    private String topatMoney;

    //找到件数
    private String findNum;

    //找到类型
    private String findType;

    //未找到件数
    private String unfindNum;

    //找到流水号
    private String findFlowcode;

    //首次分批装车部门标杆编码
    private String fristLoadDeptCode;

    //首次分批装车部门名称
    private String fristLoadDeptName;

    //卸车任务创建人标杆编码
    private String unloadCreatePersonCode;

    //卸车任务创建人名称
    private String unloadCreatePersonName;

    //装车人标杆编码
    private String loadPersonCode;

    //装车人名称
    private String loadPersonName;

    //分批配载流水号
    private String batchFlowcode;

    //卸车人工号
    private String unloadPersonCode;

    //卸车人名称
    private String unloadPersonName;

    //派送距离
    private String deliveryDistance;

    //实收超远派送费
    private String paidInDeliveryFee;

    //应收超远派送费
    private String receivableDeliveryFee;
    private String claimAmount;
    private String claimsDeptCode;
    private String claimsDeptName;
    private String transportProperties;
    private String crmWfsFinishTime;


    /**
     * subHasWaybillId <p>getter method</p>
     * @author 150976
     * @return  the subHasWaybillId
     */
    public Long getSubHasWaybillId() {
        return subHasWaybillId;
    }


    /**
     * subHasWaybillId <p>setter method</p>
     * @author 150976
     * @param subHasWaybillId the subHasWaybillId to set
     */
    public void setSubHasWaybillId(Long subHasWaybillId) {
        this.subHasWaybillId = subHasWaybillId;
    }


    /**
     * isMakeupWaybill <p>getter method</p>
     * @author 150976
     * @return  the isMakeupWaybill
     */
    public String getIsMakeupWaybill() {
        return isMakeupWaybill;
    }


    /**
     * isMakeupWaybill <p>setter method</p>
     * @author 150976
     * @param isMakeupWaybill the isMakeupWaybill to set
     */
    public void setIsMakeupWaybill(String isMakeupWaybill) {
        this.isMakeupWaybill = isMakeupWaybill;
    }


    /**
     * respSupervisorCode <p>getter method</p>
     * @author 150976
     * @return  the respSupervisorCode
     */
    public String getRespSupervisorCode() {
        return respSupervisorCode;
    }


    /**
     * respSupervisorCode <p>setter method</p>
     * @author 150976
     * @param respSupervisorCode the respSupervisorCode to set
     */
    public void setRespSupervisorCode(String respSupervisorCode) {
        this.respSupervisorCode = respSupervisorCode;
    }


    /**
     * respSupervisorName <p>getter method</p>
     * @author 150976
     * @return  the respSupervisorName
     */
    public String getRespSupervisorName() {
        return respSupervisorName;
    }


    /**
     * respSupervisorName <p>setter method</p>
     * @author 150976
     * @param respSupervisorName the respSupervisorName to set
     */
    public void setRespSupervisorName(String respSupervisorName) {
        this.respSupervisorName = respSupervisorName;
    }


    /**
     * respManagerCode <p>getter method</p>
     * @author 150976
     * @return  the respManagerCode
     */
    public String getRespManagerCode() {
        return respManagerCode;
    }


    /**
     * respManagerCode <p>setter method</p>
     * @author 150976
     * @param respManagerCode the respManagerCode to set
     */
    public void setRespManagerCode(String respManagerCode) {
        this.respManagerCode = respManagerCode;
    }


    /**
     * respManagerName <p>getter method</p>
     * @author 150976
     * @return  the respManagerName
     */
    public String getRespManagerName() {
        return respManagerName;
    }


    /**
     * respManagerName <p>setter method</p>
     * @author 150976
     * @param respManagerName the respManagerName to set
     */
    public void setRespManagerName(String respManagerName) {
        this.respManagerName = respManagerName;
    }


    /**
     * respManagerSuperCode <p>getter method</p>
     * @author 150976
     * @return  the respManagerSuperCode
     */
    public String getRespManagerSuperCode() {
        return respManagerSuperCode;
    }


    /**
     * respManagerSuperCode <p>setter method</p>
     * @author 150976
     * @param respManagerSuperCode the respManagerSuperCode to set
     */
    public void setRespManagerSuperCode(String respManagerSuperCode) {
        this.respManagerSuperCode = respManagerSuperCode;
    }


    /**
     * respManagerSuperName <p>getter method</p>
     * @author 150976
     * @return  the respManagerSuperName
     */
    public String getRespManagerSuperName() {
        return respManagerSuperName;
    }


    /**
     * respManagerSuperName <p>setter method</p>
     * @author 150976
     * @param respManagerSuperName the respManagerSuperName to set
     */
    public void setRespManagerSuperName(String respManagerSuperName) {
        this.respManagerSuperName = respManagerSuperName;
    }


    /**
     * billingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the billingDeptCode
     */
    public String getBillingDeptCode() {
        return billingDeptCode;
    }


    /**
     * billingDeptCode <p>setter method</p>
     * @author 150976
     * @param billingDeptCode the billingDeptCode to set
     */
    public void setBillingDeptCode(String billingDeptCode) {
        this.billingDeptCode = billingDeptCode;
    }


    /**
     * billingDeptName <p>getter method</p>
     * @author 150976
     * @return  the billingDeptName
     */
    public String getBillingDeptName() {
        return billingDeptName;
    }


    /**
     * billingDeptName <p>setter method</p>
     * @author 150976
     * @param billingDeptName the billingDeptName to set
     */
    public void setBillingDeptName(String billingDeptName) {
        this.billingDeptName = billingDeptName;
    }


    /**
     * sendClientCode <p>getter method</p>
     * @author 150976
     * @return  the sendClientCode
     */
    public String getSendClientCode() {
        return sendClientCode;
    }


    /**
     * sendClientCode <p>setter method</p>
     * @author 150976
     * @param sendClientCode the sendClientCode to set
     */
    public void setSendClientCode(String sendClientCode) {
        this.sendClientCode = sendClientCode;
    }


    /**
     * takeOverClientCode <p>getter method</p>
     * @author 150976
     * @return  the takeOverClientCode
     */
    public String getTakeOverClientCode() {
        return takeOverClientCode;
    }


    /**
     * takeOverClientCode <p>setter method</p>
     * @author 150976
     * @param takeOverClientCode the takeOverClientCode to set
     */
    public void setTakeOverClientCode(String takeOverClientCode) {
        this.takeOverClientCode = takeOverClientCode;
    }


    /**
     * isConcentReceiving <p>getter method</p>
     * @author 150976
     * @return  the isConcentReceiving
     */
    public String getIsConcentReceiving() {
        return isConcentReceiving;
    }


    /**
     * isConcentReceiving <p>setter method</p>
     * @author 150976
     * @param isConcentReceiving the isConcentReceiving to set
     */
    public void setIsConcentReceiving(String isConcentReceiving) {
        this.isConcentReceiving = isConcentReceiving;
    }


    /**
     * transType <p>getter method</p>
     * @author 150976
     * @return  the transType
     */
    public String getTransType() {
        return transType;
    }


    /**
     * transType <p>setter method</p>
     * @author 150976
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }


    /**
     * shipper <p>getter method</p>
     * @author 150976
     * @return  the shipper
     */
    public String getShipper() {
        return shipper;
    }


    /**
     * shipper <p>setter method</p>
     * @author 150976
     * @param shipper the shipper to set
     */
    public void setShipper(String shipper) {
        this.shipper = shipper;
    }


    /**
     * transNature <p>getter method</p>
     * @author 150976
     * @return  the transNature
     */
    public String getTransNature() {
        return transNature;
    }


    /**
     * transNature <p>setter method</p>
     * @author 150976
     * @param transNature the transNature to set
     */
    public void setTransNature(String transNature) {
        this.transNature = transNature;
    }


    /**
     * receiverName <p>getter method</p>
     * @author 150976
     * @return  the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }


    /**
     * receiverName <p>setter method</p>
     * @author 150976
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    /**
     * actualWeight <p>getter method</p>
     * @author 150976
     * @return  the actualWeight
     */
    public String getActualWeight() {
        return actualWeight;
    }


    /**
     * actualWeight <p>setter method</p>
     * @author 150976
     * @param actualWeight the actualWeight to set
     */
    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }


    /**
     * storageTransport <p>getter method</p>
     * @author 150976
     * @return  the storageTransport
     */
    public String getStorageTransport() {
        return storageTransport;
    }


    /**
     * storageTransport <p>setter method</p>
     * @author 150976
     * @param storageTransport the storageTransport to set
     */
    public void setStorageTransport(String storageTransport) {
        this.storageTransport = storageTransport;
    }


    /**
     * receiverPhone <p>getter method</p>
     * @author 150976
     * @return  the receiverPhone
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }


    /**
     * receiverPhone <p>setter method</p>
     * @author 150976
     * @param receiverPhone the receiverPhone to set
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }


    /**
     * receiverAddress <p>getter method</p>
     * @author 150976
     * @return  the receiverAddress
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }


    /**
     * receiverAddress <p>setter method</p>
     * @author 150976
     * @param receiverAddress the receiverAddress to set
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


    /**
     * pickUpType <p>getter method</p>
     * @author 150976
     * @return  the pickUpType
     */
    public String getPickUpType() {
        return pickUpType;
    }


    /**
     * pickUpType <p>setter method</p>
     * @author 150976
     * @param pickUpType the pickUpType to set
     */
    public void setPickUpType(String pickUpType) {
        this.pickUpType = pickUpType;
    }


    /**
     * weight <p>getter method</p>
     * @author 150976
     * @return  the weight
     */
    public String getWeight() {
        return weight;
    }


    /**
     * weight <p>setter method</p>
     * @author 150976
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }


    /**
     * sumWeight <p>getter method</p>
     * @author 150976
     * @return  the sumWeight
     */
    public String getSumWeight() {
        return sumWeight;
    }


    /**
     * sumWeight <p>setter method</p>
     * @author 150976
     * @param sumWeight the sumWeight to set
     */
    public void setSumWeight(String sumWeight) {
        this.sumWeight = sumWeight;
    }


    /**
     * volume <p>getter method</p>
     * @author 150976
     * @return  the volume
     */
    public String getVolume() {
        return volume;
    }


    /**
     * volume <p>setter method</p>
     * @author 150976
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }


    /**
     * sumVolume <p>getter method</p>
     * @author 150976
     * @return  the sumVolume
     */
    public String getSumVolume() {
        return sumVolume;
    }


    /**
     * sumVolume <p>setter method</p>
     * @author 150976
     * @param sumVolume the sumVolume to set
     */
    public void setSumVolume(String sumVolume) {
        this.sumVolume = sumVolume;
    }


    /**
     * actualVolume <p>getter method</p>
     * @author 150976
     * @return  the actualVolume
     */
    public String getActualVolume() {
        return actualVolume;
    }


    /**
     * actualVolume <p>setter method</p>
     * @author 150976
     * @param actualVolume the actualVolume to set
     */
    public void setActualVolume(String actualVolume) {
        this.actualVolume = actualVolume;
    }


    /**
     * num <p>getter method</p>
     * @author 150976
     * @return  the num
     */
    public String getNum() {
        return num;
    }


    /**
     * num <p>setter method</p>
     * @author 150976
     * @param num the num to set
     */
    public void setNum(String num) {
        this.num = num;
    }


    /**
     * sumNumber <p>getter method</p>
     * @author 150976
     * @return  the sumNumber
     */
    public String getSumNumber() {
        return sumNumber;
    }


    /**
     * sumNumber <p>setter method</p>
     * @author 150976
     * @param sumNumber the sumNumber to set
     */
    public void setSumNumber(String sumNumber) {
        this.sumNumber = sumNumber;
    }


    /**
     * goodsName <p>getter method</p>
     * @author 150976
     * @return  the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }


    /**
     * goodsName <p>setter method</p>
     * @author 150976
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    /**
     * sendGoodsTime <p>getter method</p>
     * @author 150976
     * @return  the sendGoodsTime
     */
    public String getSendGoodsTime() {
        return sendGoodsTime;
    }


    /**
     * sendGoodsTime <p>setter method</p>
     * @author 150976
     * @param sendGoodsTime the sendGoodsTime to set
     */
    public void setSendGoodsTime(String sendGoodsTime) {
        this.sendGoodsTime = sendGoodsTime;
    }


    /**
     * billingTime <p>getter method</p>
     * @author 150976
     * @return  the billingTime
     */
    public String getBillingTime() {
        return billingTime;
    }


    /**
     * billingTime <p>setter method</p>
     * @author 150976
     * @param billingTime the billingTime to set
     */
    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }


    /**
     * arriveDeptCode <p>getter method</p>
     * @author 150976
     * @return  the arriveDeptCode
     */
    public String getArriveDeptCode() {
        return arriveDeptCode;
    }


    /**
     * arriveDeptCode <p>setter method</p>
     * @author 150976
     * @param arriveDeptCode the arriveDeptCode to set
     */
    public void setArriveDeptCode(String arriveDeptCode) {
        this.arriveDeptCode = arriveDeptCode;
    }


    /**
     * arriveDeptName <p>getter method</p>
     * @author 150976
     * @return  the arriveDeptName
     */
    public String getArriveDeptName() {
        return arriveDeptName;
    }


    /**
     * arriveDeptName <p>setter method</p>
     * @author 150976
     * @param arriveDeptName the arriveDeptName to set
     */
    public void setArriveDeptName(String arriveDeptName) {
        this.arriveDeptName = arriveDeptName;
    }


    /**
     * takeOverDeptCode <p>getter method</p>
     * @author 150976
     * @return  the takeOverDeptCode
     */
    public String getTakeOverDeptCode() {
        return takeOverDeptCode;
    }


    /**
     * takeOverDeptCode <p>setter method</p>
     * @author 150976
     * @param takeOverDeptCode the takeOverDeptCode to set
     */
    public void setTakeOverDeptCode(String takeOverDeptCode) {
        this.takeOverDeptCode = takeOverDeptCode;
    }


    /**
     * takeOverDeptName <p>getter method</p>
     * @author 150976
     * @return  the takeOverDeptName
     */
    public String getTakeOverDeptName() {
        return takeOverDeptName;
    }


    /**
     * takeOverDeptName <p>setter method</p>
     * @author 150976
     * @param takeOverDeptName the takeOverDeptName to set
     */
    public void setTakeOverDeptName(String takeOverDeptName) {
        this.takeOverDeptName = takeOverDeptName;
    }


    /**
     * payType <p>getter method</p>
     * @author 150976
     * @return  the payType
     */
    public String getPayType() {
        return payType;
    }


    /**
     * payType <p>setter method</p>
     * @author 150976
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }


    /**
     * safeMoney <p>getter method</p>
     * @author 150976
     * @return  the safeMoney
     */
    public String getSafeMoney() {
        return safeMoney;
    }


    /**
     * safeMoney <p>setter method</p>
     * @author 150976
     * @param safeMoney the safeMoney to set
     */
    public void setSafeMoney(String safeMoney) {
        this.safeMoney = safeMoney;
    }


    /**
     * safeValue <p>getter method</p>
     * @author 150976
     * @return  the safeValue
     */
    public String getSafeValue() {
        return safeValue;
    }


    /**
     * safeValue <p>setter method</p>
     * @author 150976
     * @param safeValue the safeValue to set
     */
    public void setSafeValue(String safeValue) {
        this.safeValue = safeValue;
    }


    /**
     * goodsPackage <p>getter method</p>
     * @author 150976
     * @return  the goodsPackage
     */
    public String getGoodsPackage() {
        return goodsPackage;
    }


    /**
     * goodsPackage <p>setter method</p>
     * @author 150976
     * @param goodsPackage the goodsPackage to set
     */
    public void setGoodsPackage(String goodsPackage) {
        this.goodsPackage = goodsPackage;
    }


    /**
     * loadingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the loadingDeptCode
     */
    public String getLoadingDeptCode() {
        return loadingDeptCode;
    }


    /**
     * loadingDeptCode <p>setter method</p>
     * @author 150976
     * @param loadingDeptCode the loadingDeptCode to set
     */
    public void setLoadingDeptCode(String loadingDeptCode) {
        this.loadingDeptCode = loadingDeptCode;
    }


    /**
     * loadingDeptName <p>getter method</p>
     * @author 150976
     * @return  the loadingDeptName
     */
    public String getLoadingDeptName() {
        return loadingDeptName;
    }


    /**
     * loadingDeptName <p>setter method</p>
     * @author 150976
     * @param loadingDeptName the loadingDeptName to set
     */
    public void setLoadingDeptName(String loadingDeptName) {
        this.loadingDeptName = loadingDeptName;
    }


    /**
     * unloadingDeptCode <p>getter method</p>
     * @author 150976
     * @return  the unloadingDeptCode
     */
    public String getUnloadingDeptCode() {
        return unloadingDeptCode;
    }


    /**
     * unloadingDeptCode <p>setter method</p>
     * @author 150976
     * @param unloadingDeptCode the unloadingDeptCode to set
     */
    public void setUnloadingDeptCode(String unloadingDeptCode) {
        this.unloadingDeptCode = unloadingDeptCode;
    }


    /**
     * unloadingDeptName <p>getter method</p>
     * @author 150976
     * @return  the unloadingDeptName
     */
    public String getUnloadingDeptName() {
        return unloadingDeptName;
    }


    /**
     * unloadingDeptName <p>setter method</p>
     * @author 150976
     * @param unloadingDeptName the unloadingDeptName to set
     */
    public void setUnloadingDeptName(String unloadingDeptName) {
        this.unloadingDeptName = unloadingDeptName;
    }


    /**
     * transferBill <p>getter method</p>
     * @author 150976
     * @return  the transferBill
     */
    public String getTransferBill() {
        return transferBill;
    }


    /**
     * transferBill <p>setter method</p>
     * @author 150976
     * @param transferBill the transferBill to set
     */
    public void setTransferBill(String transferBill) {
        this.transferBill = transferBill;
    }


    /**
     * loseGoodsType <p>getter method</p>
     * @author 150976
     * @return  the loseGoodsType
     */
    public String getLoseGoodsType() {
        return loseGoodsType;
    }


    /**
     * loseGoodsType <p>setter method</p>
     * @author 150976
     * @param loseGoodsType the loseGoodsType to set
     */
    public void setLoseGoodsType(String loseGoodsType) {
        this.loseGoodsType = loseGoodsType;
    }


    /**
     * loseNum <p>getter method</p>
     * @author 150976
     * @return  the loseNum
     */
    public String getLoseNum() {
        return loseNum;
    }


    /**
     * loseNum <p>setter method</p>
     * @author 150976
     * @param loseNum the loseNum to set
     */
    public void setLoseNum(String loseNum) {
        this.loseNum = loseNum;
    }


    /**
     * isWholeTicket <p>getter method</p>
     * @author 150976
     * @return  the isWholeTicket
     */
    public String getIsWholeTicket() {
        return isWholeTicket;
    }


    /**
     * isWholeTicket <p>setter method</p>
     * @author 150976
     * @param isWholeTicket the isWholeTicket to set
     */
    public void setIsWholeTicket(String isWholeTicket) {
        this.isWholeTicket = isWholeTicket;
    }


    /**
     * packageFee <p>getter method</p>
     * @author 150976
     * @return  the packageFee
     */
    public String getPackageFee() {
        return packageFee;
    }


    /**
     * packageFee <p>setter method</p>
     * @author 150976
     * @param packageFee the packageFee to set
     */
    public void setPackageFee(String packageFee) {
        this.packageFee = packageFee;
    }


    /**
     * loseFlowcode <p>getter method</p>
     * @author 150976
     * @return  the loseFlowcode
     */
    public String getLoseFlowcode() {
        return loseFlowcode;
    }


    /**
     * loseFlowcode <p>setter method</p>
     * @author 150976
     * @param loseFlowcode the loseFlowcode to set
     */
    public void setLoseFlowcode(String loseFlowcode) {
        this.loseFlowcode = loseFlowcode;
    }


    /**
     * pureFee <p>getter method</p>
     * @author 150976
     * @return  the pureFee
     */
    public String getPureFee() {
        return pureFee;
    }


    /**
     * pureFee <p>setter method</p>
     * @author 150976
     * @param pureFee the pureFee to set
     */
    public void setPureFee(String pureFee) {
        this.pureFee = pureFee;
    }


    /**
     * deliveryFee <p>getter method</p>
     * @author 150976
     * @return  the deliveryFee
     */
    public String getDeliveryFee() {
        return deliveryFee;
    }


    /**
     * deliveryFee <p>setter method</p>
     * @author 150976
     * @param deliveryFee the deliveryFee to set
     */
    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }


    /**
     * freightSumFee <p>getter method</p>
     * @author 150976
     * @return  the freightSumFee
     */
    public String getFreightSumFee() {
        return freightSumFee;
    }


    /**
     * freightSumFee <p>setter method</p>
     * @author 150976
     * @param freightSumFee the freightSumFee to set
     */
    public void setFreightSumFee(String freightSumFee) {
        this.freightSumFee = freightSumFee;
    }


    /**
     * fillopenEmpCode <p>getter method</p>
     * @author 150976
     * @return  the fillopenEmpCode
     */
    public String getFillopenEmpCode() {
        return fillopenEmpCode;
    }


    /**
     * fillopenEmpCode <p>setter method</p>
     * @author 150976
     * @param fillopenEmpCode the fillopenEmpCode to set
     */
    public void setFillopenEmpCode(String fillopenEmpCode) {
        this.fillopenEmpCode = fillopenEmpCode;
    }


    /**
     * fillopenEmpName <p>getter method</p>
     * @author 150976
     * @return  the fillopenEmpName
     */
    public String getFillopenEmpName() {
        return fillopenEmpName;
    }


    /**
     * fillopenEmpName <p>setter method</p>
     * @author 150976
     * @param fillopenEmpName the fillopenEmpName to set
     */
    public void setFillopenEmpName(String fillopenEmpName) {
        this.fillopenEmpName = fillopenEmpName;
    }


    /**
     * fillBillDeptCode <p>getter method</p>
     * @author 150976
     * @return  the fillBillDeptCode
     */
    public String getFillBillDeptCode() {
        return fillBillDeptCode;
    }


    /**
     * fillBillDeptCode <p>setter method</p>
     * @author 150976
     * @param fillBillDeptCode the fillBillDeptCode to set
     */
    public void setFillBillDeptCode(String fillBillDeptCode) {
        this.fillBillDeptCode = fillBillDeptCode;
    }


    /**
     * fillBillDeptName <p>getter method</p>
     * @author 150976
     * @return  the fillBillDeptName
     */
    public String getFillBillDeptName() {
        return fillBillDeptName;
    }


    /**
     * fillBillDeptName <p>setter method</p>
     * @author 150976
     * @param fillBillDeptName the fillBillDeptName to set
     */
    public void setFillBillDeptName(String fillBillDeptName) {
        this.fillBillDeptName = fillBillDeptName;
    }


    /**
     * driverCode <p>getter method</p>
     * @author 150976
     * @return  the driverCode
     */
    public String getDriverCode() {
        return driverCode;
    }


    /**
     * driverCode <p>setter method</p>
     * @author 150976
     * @param driverCode the driverCode to set
     */
    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }


    /**
     * driverName <p>getter method</p>
     * @author 150976
     * @return  the driverName
     */
    public String getDriverName() {
        return driverName;
    }


    /**
     * driverName <p>setter method</p>
     * @author 150976
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


    /**
     * driverDeptCode <p>getter method</p>
     * @author 150976
     * @return  the driverDeptCode
     */
    public String getDriverDeptCode() {
        return driverDeptCode;
    }


    /**
     * driverDeptCode <p>setter method</p>
     * @author 150976
     * @param driverDeptCode the driverDeptCode to set
     */
    public void setDriverDeptCode(String driverDeptCode) {
        this.driverDeptCode = driverDeptCode;
    }


    /**
     * driverDeptName <p>getter method</p>
     * @author 150976
     * @return  the driverDeptName
     */
    public String getDriverDeptName() {
        return driverDeptName;
    }


    /**
     * driverDeptName <p>setter method</p>
     * @author 150976
     * @param driverDeptName the driverDeptName to set
     */
    public void setDriverDeptName(String driverDeptName) {
        this.driverDeptName = driverDeptName;
    }


    /**
     * carCode <p>getter method</p>
     * @author 150976
     * @return  the carCode
     */
    public String getCarCode() {
        return carCode;
    }


    /**
     * carCode <p>setter method</p>
     * @author 150976
     * @param carCode the carCode to set
     */
    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }


    /**
     * returnBillType <p>getter method</p>
     * @author 150976
     * @return  the returnBillType
     */
    public String getReturnBillType() {
        return returnBillType;
    }


    /**
     * returnBillType <p>setter method</p>
     * @author 150976
     * @param returnBillType the returnBillType to set
     */
    public void setReturnBillType(String returnBillType) {
        this.returnBillType = returnBillType;
    }


    /**
     * packageIsGood <p>getter method</p>
     * @author 150976
     * @return  the packageIsGood
     */
    public String getPackageIsGood() {
        return packageIsGood;
    }


    /**
     * packageIsGood <p>setter method</p>
     * @author 150976
     * @param packageIsGood the packageIsGood to set
     */
    public void setPackageIsGood(String packageIsGood) {
        this.packageIsGood = packageIsGood;
    }


    /**
     * goodsLoseFlowcode <p>getter method</p>
     * @author 150976
     * @return  the goodsLoseFlowcode
     */
    public String getGoodsLoseFlowcode() {
        return goodsLoseFlowcode;
    }


    /**
     * goodsLoseFlowcode <p>setter method</p>
     * @author 150976
     * @param goodsLoseFlowcode the goodsLoseFlowcode to set
     */
    public void setGoodsLoseFlowcode(String goodsLoseFlowcode) {
        this.goodsLoseFlowcode = goodsLoseFlowcode;
    }


    /**
     * shortNum <p>getter method</p>
     * @author 150976
     * @return  the shortNum
     */
    public String getShortNum() {
        return shortNum;
    }


    /**
     * shortNum <p>setter method</p>
     * @author 150976
     * @param shortNum the shortNum to set
     */
    public void setShortNum(String shortNum) {
        this.shortNum = shortNum;
    }


    /**
     * unloadBusiCode <p>getter method</p>
     * @author 150976
     * @return  the unloadBusiCode
     */
    public String getUnloadBusiCode() {
        return unloadBusiCode;
    }


    /**
     * unloadBusiCode <p>setter method</p>
     * @author 150976
     * @param unloadBusiCode the unloadBusiCode to set
     */
    public void setUnloadBusiCode(String unloadBusiCode) {
        this.unloadBusiCode = unloadBusiCode;
    }


    /**
     * moreGoodsNum <p>getter method</p>
     * @author 150976
     * @return  the moreGoodsNum
     */
    public String getMoreGoodsNum() {
        return moreGoodsNum;
    }


    /**
     * moreGoodsNum <p>setter method</p>
     * @author 150976
     * @param moreGoodsNum the moreGoodsNum to set
     */
    public void setMoreGoodsNum(String moreGoodsNum) {
        this.moreGoodsNum = moreGoodsNum;
    }


    /**
     * goodsNoTransfer <p>getter method</p>
     * @author 150976
     * @return  the goodsNoTransfer
     */
    public String getGoodsNoTransfer() {
        return goodsNoTransfer;
    }


    /**
     * goodsNoTransfer <p>setter method</p>
     * @author 150976
     * @param goodsNoTransfer the goodsNoTransfer to set
     */
    public void setGoodsNoTransfer(String goodsNoTransfer) {
        this.goodsNoTransfer = goodsNoTransfer;
    }


    /**
     * lastLinkDeptCode <p>getter method</p>
     * @author 150976
     * @return  the lastLinkDeptCode
     */
    public String getLastLinkDeptCode() {
        return lastLinkDeptCode;
    }


    /**
     * lastLinkDeptCode <p>setter method</p>
     * @author 150976
     * @param lastLinkDeptCode the lastLinkDeptCode to set
     */
    public void setLastLinkDeptCode(String lastLinkDeptCode) {
        this.lastLinkDeptCode = lastLinkDeptCode;
    }


    /**
     * lastLinkDeptName <p>getter method</p>
     * @author 150976
     * @return  the lastLinkDeptName
     */
    public String getLastLinkDeptName() {
        return lastLinkDeptName;
    }


    /**
     * lastLinkDeptName <p>setter method</p>
     * @author 150976
     * @param lastLinkDeptName the lastLinkDeptName to set
     */
    public void setLastLinkDeptName(String lastLinkDeptName) {
        this.lastLinkDeptName = lastLinkDeptName;
    }


    /**
     * moreGoodsType <p>getter method</p>
     * @author 150976
     * @return  the moreGoodsType
     */
    public String getMoreGoodsType() {
        return moreGoodsType;
    }


    /**
     * moreGoodsType <p>setter method</p>
     * @author 150976
     * @param moreGoodsType the moreGoodsType to set
     */
    public void setMoreGoodsType(String moreGoodsType) {
        this.moreGoodsType = moreGoodsType;
    }


    /**
     * moreGoodsFlowcode <p>getter method</p>
     * @author 150976
     * @return  the moreGoodsFlowcode
     */
    public String getMoreGoodsFlowcode() {
        return moreGoodsFlowcode;
    }


    /**
     * moreGoodsFlowcode <p>setter method</p>
     * @author 150976
     * @param moreGoodsFlowcode the moreGoodsFlowcode to set
     */
    public void setMoreGoodsFlowcode(String moreGoodsFlowcode) {
        this.moreGoodsFlowcode = moreGoodsFlowcode;
    }


    /**
     * loseMoney <p>getter method</p>
     * @author 150976
     * @return  the loseMoney
     */
    public String getLoseMoney() {
        return loseMoney;
    }


    /**
     * loseMoney <p>setter method</p>
     * @author 150976
     * @param loseMoney the loseMoney to set
     */
    public void setLoseMoney(String loseMoney) {
        this.loseMoney = loseMoney;
    }


    /**
     * illegalType <p>getter method</p>
     * @author 150976
     * @return  the illegalType
     */
    public String getIllegalType() {
        return illegalType;
    }


    /**
     * illegalType <p>setter method</p>
     * @author 150976
     * @param illegalType the illegalType to set
     */
    public void setIllegalType(String illegalType) {
        this.illegalType = illegalType;
    }


    /**
     * damageClaimAmount <p>getter method</p>
     * @author 150976
     * @return  the damageClaimAmount
     */
    public String getDamageClaimAmount() {
        return damageClaimAmount;
    }


    /**
     * damageClaimAmount <p>setter method</p>
     * @author 150976
     * @param damageClaimAmount the damageClaimAmount to set
     */
    public void setDamageClaimAmount(String damageClaimAmount) {
        this.damageClaimAmount = damageClaimAmount;
    }


    /**
     * loseType <p>getter method</p>
     * @author 150976
     * @return  the loseType
     */
    public String getLoseType() {
        return loseType;
    }


    /**
     * loseType <p>setter method</p>
     * @author 150976
     * @param loseType the loseType to set
     */
    public void setLoseType(String loseType) {
        this.loseType = loseType;
    }


    /**
     * loseName <p>getter method</p>
     * @author 150976
     * @return  the loseName
     */
    public String getLoseName() {
        return loseName;
    }


    /**
     * loseName <p>setter method</p>
     * @author 150976
     * @param loseName the loseName to set
     */
    public void setLoseName(String loseName) {
        this.loseName = loseName;
    }


    /**
     * receverDriverCode <p>getter method</p>
     * @author 150976
     * @return  the receverDriverCode
     */
    public String getReceverDriverCode() {
        return receverDriverCode;
    }


    /**
     * receverDriverCode <p>setter method</p>
     * @author 150976
     * @param receverDriverCode the receverDriverCode to set
     */
    public void setReceverDriverCode(String receverDriverCode) {
        this.receverDriverCode = receverDriverCode;
    }


    /**
     * receverDriverName <p>getter method</p>
     * @author 150976
     * @return  the receverDriverName
     */
    public String getReceverDriverName() {
        return receverDriverName;
    }


    /**
     * receverDriverName <p>setter method</p>
     * @author 150976
     * @param receverDriverName the receverDriverName to set
     */
    public void setReceverDriverName(String receverDriverName) {
        this.receverDriverName = receverDriverName;
    }


    /**
     * goodsHandleDeptCode <p>getter method</p>
     * @author 150976
     * @return  the goodsHandleDeptCode
     */
    public String getGoodsHandleDeptCode() {
        return goodsHandleDeptCode;
    }


    /**
     * goodsHandleDeptCode <p>setter method</p>
     * @author 150976
     * @param goodsHandleDeptCode the goodsHandleDeptCode to set
     */
    public void setGoodsHandleDeptCode(String goodsHandleDeptCode) {
        this.goodsHandleDeptCode = goodsHandleDeptCode;
    }


    /**
     * goodsHandleDeptName <p>getter method</p>
     * @author 150976
     * @return  the goodsHandleDeptName
     */
    public String getGoodsHandleDeptName() {
        return goodsHandleDeptName;
    }


    /**
     * goodsHandleDeptName <p>setter method</p>
     * @author 150976
     * @param goodsHandleDeptName the goodsHandleDeptName to set
     */
    public void setGoodsHandleDeptName(String goodsHandleDeptName) {
        this.goodsHandleDeptName = goodsHandleDeptName;
    }


    /**
     * driverTakeDeptCode <p>getter method</p>
     * @author 150976
     * @return  the driverTakeDeptCode
     */
    public String getDriverTakeDeptCode() {
        return driverTakeDeptCode;
    }


    /**
     * driverTakeDeptCode <p>setter method</p>
     * @author 150976
     * @param driverTakeDeptCode the driverTakeDeptCode to set
     */
    public void setDriverTakeDeptCode(String driverTakeDeptCode) {
        this.driverTakeDeptCode = driverTakeDeptCode;
    }


    /**
     * driverTakeDeptName <p>getter method</p>
     * @author 150976
     * @return  the driverTakeDeptName
     */
    public String getDriverTakeDeptName() {
        return driverTakeDeptName;
    }


    /**
     * driverTakeDeptName <p>setter method</p>
     * @author 150976
     * @param driverTakeDeptName the driverTakeDeptName to set
     */
    public void setDriverTakeDeptName(String driverTakeDeptName) {
        this.driverTakeDeptName = driverTakeDeptName;
    }


    /**
     * prepayMoney <p>getter method</p>
     * @author 150976
     * @return  the prepayMoney
     */
    public String getPrepayMoney() {
        return prepayMoney;
    }


    /**
     * prepayMoney <p>setter method</p>
     * @author 150976
     * @param prepayMoney the prepayMoney to set
     */
    public void setPrepayMoney(String prepayMoney) {
        this.prepayMoney = prepayMoney;
    }


    /**
     * topatMoney <p>getter method</p>
     * @author 150976
     * @return  the topatMoney
     */
    public String getTopatMoney() {
        return topatMoney;
    }


    /**
     * topatMoney <p>setter method</p>
     * @author 150976
     * @param topatMoney the topatMoney to set
     */
    public void setTopatMoney(String topatMoney) {
        this.topatMoney = topatMoney;
    }


    /**
     * findNum <p>getter method</p>
     * @author 150976
     * @return  the findNum
     */
    public String getFindNum() {
        return findNum;
    }


    /**
     * findNum <p>setter method</p>
     * @author 150976
     * @param findNum the findNum to set
     */
    public void setFindNum(String findNum) {
        this.findNum = findNum;
    }


    /**
     * findType <p>getter method</p>
     * @author 150976
     * @return  the findType
     */
    public String getFindType() {
        return findType;
    }


    /**
     * findType <p>setter method</p>
     * @author 150976
     * @param findType the findType to set
     */
    public void setFindType(String findType) {
        this.findType = findType;
    }


    /**
     * unfindNum <p>getter method</p>
     * @author 150976
     * @return  the unfindNum
     */
    public String getUnfindNum() {
        return unfindNum;
    }


    /**
     * unfindNum <p>setter method</p>
     * @author 150976
     * @param unfindNum the unfindNum to set
     */
    public void setUnfindNum(String unfindNum) {
        this.unfindNum = unfindNum;
    }


    /**
     * findFlowcode <p>getter method</p>
     * @author 150976
     * @return  the findFlowcode
     */
    public String getFindFlowcode() {
        return findFlowcode;
    }


    /**
     * findFlowcode <p>setter method</p>
     * @author 150976
     * @param findFlowcode the findFlowcode to set
     */
    public void setFindFlowcode(String findFlowcode) {
        this.findFlowcode = findFlowcode;
    }


    /**
     * fristLoadDeptCode <p>getter method</p>
     * @author 150976
     * @return  the fristLoadDeptCode
     */
    public String getFristLoadDeptCode() {
        return fristLoadDeptCode;
    }


    /**
     * fristLoadDeptCode <p>setter method</p>
     * @author 150976
     * @param fristLoadDeptCode the fristLoadDeptCode to set
     */
    public void setFristLoadDeptCode(String fristLoadDeptCode) {
        this.fristLoadDeptCode = fristLoadDeptCode;
    }


    /**
     * fristLoadDeptName <p>getter method</p>
     * @author 150976
     * @return  the fristLoadDeptName
     */
    public String getFristLoadDeptName() {
        return fristLoadDeptName;
    }


    /**
     * fristLoadDeptName <p>setter method</p>
     * @author 150976
     * @param fristLoadDeptName the fristLoadDeptName to set
     */
    public void setFristLoadDeptName(String fristLoadDeptName) {
        this.fristLoadDeptName = fristLoadDeptName;
    }


    /**
     * unloadCreatePersonCode <p>getter method</p>
     * @author 150976
     * @return  the unloadCreatePersonCode
     */
    public String getUnloadCreatePersonCode() {
        return unloadCreatePersonCode;
    }


    /**
     * unloadCreatePersonCode <p>setter method</p>
     * @author 150976
     * @param unloadCreatePersonCode the unloadCreatePersonCode to set
     */
    public void setUnloadCreatePersonCode(String unloadCreatePersonCode) {
        this.unloadCreatePersonCode = unloadCreatePersonCode;
    }


    /**
     * unloadCreatePersonName <p>getter method</p>
     * @author 150976
     * @return  the unloadCreatePersonName
     */
    public String getUnloadCreatePersonName() {
        return unloadCreatePersonName;
    }


    /**
     * unloadCreatePersonName <p>setter method</p>
     * @author 150976
     * @param unloadCreatePersonName the unloadCreatePersonName to set
     */
    public void setUnloadCreatePersonName(String unloadCreatePersonName) {
        this.unloadCreatePersonName = unloadCreatePersonName;
    }


    /**
     * loadPersonCode <p>getter method</p>
     * @author 150976
     * @return  the loadPersonCode
     */
    public String getLoadPersonCode() {
        return loadPersonCode;
    }


    /**
     * loadPersonCode <p>setter method</p>
     * @author 150976
     * @param loadPersonCode the loadPersonCode to set
     */
    public void setLoadPersonCode(String loadPersonCode) {
        this.loadPersonCode = loadPersonCode;
    }


    /**
     * loadPersonName <p>getter method</p>
     * @author 150976
     * @return  the loadPersonName
     */
    public String getLoadPersonName() {
        return loadPersonName;
    }


    /**
     * loadPersonName <p>setter method</p>
     * @author 150976
     * @param loadPersonName the loadPersonName to set
     */
    public void setLoadPersonName(String loadPersonName) {
        this.loadPersonName = loadPersonName;
    }


    /**
     * batchFlowcode <p>getter method</p>
     * @author 150976
     * @return  the batchFlowcode
     */
    public String getBatchFlowcode() {
        return batchFlowcode;
    }


    /**
     * batchFlowcode <p>setter method</p>
     * @author 150976
     * @param batchFlowcode the batchFlowcode to set
     */
    public void setBatchFlowcode(String batchFlowcode) {
        this.batchFlowcode = batchFlowcode;
    }


    /**
     * unloadPersonCode <p>getter method</p>
     * @author 150976
     * @return  the unloadPersonCode
     */
    public String getUnloadPersonCode() {
        return unloadPersonCode;
    }


    /**
     * unloadPersonCode <p>setter method</p>
     * @author 150976
     * @param unloadPersonCode the unloadPersonCode to set
     */
    public void setUnloadPersonCode(String unloadPersonCode) {
        this.unloadPersonCode = unloadPersonCode;
    }


    /**
     * unloadPersonName <p>getter method</p>
     * @author 150976
     * @return  the unloadPersonName
     */
    public String getUnloadPersonName() {
        return unloadPersonName;
    }


    /**
     * unloadPersonName <p>setter method</p>
     * @author 150976
     * @param unloadPersonName the unloadPersonName to set
     */
    public void setUnloadPersonName(String unloadPersonName) {
        this.unloadPersonName = unloadPersonName;
    }


    /**
     * deliveryDistance <p>getter method</p>
     * @author 150976
     * @return  the deliveryDistance
     */
    public String getDeliveryDistance() {
        return deliveryDistance;
    }


    /**
     * deliveryDistance <p>setter method</p>
     * @author 150976
     * @param deliveryDistance the deliveryDistance to set
     */
    public void setDeliveryDistance(String deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }


    /**
     * paidInDeliveryFee <p>getter method</p>
     * @author 150976
     * @return  the paidInDeliveryFee
     */
    public String getPaidInDeliveryFee() {
        return paidInDeliveryFee;
    }


    /**
     * paidInDeliveryFee <p>setter method</p>
     * @author 150976
     * @param paidInDeliveryFee the paidInDeliveryFee to set
     */
    public void setPaidInDeliveryFee(String paidInDeliveryFee) {
        this.paidInDeliveryFee = paidInDeliveryFee;
    }


    /**
     * receivableDeliveryFee <p>getter method</p>
     * @author 150976
     * @return  the receivableDeliveryFee
     */
    public String getReceivableDeliveryFee() {
        return receivableDeliveryFee;
    }


    /**
     * receivableDeliveryFee <p>setter method</p>
     * @author 150976
     * @param receivableDeliveryFee the receivableDeliveryFee to set
     */
    public void setReceivableDeliveryFee(String receivableDeliveryFee) {
        this.receivableDeliveryFee = receivableDeliveryFee;
    }


    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimAmount() {
        return claimAmount;
    }
    
    public void setClaimsDeptCode(String claimsDeptCode) {
        this.claimsDeptCode = claimsDeptCode;
    }

    public void setClaimsDeptName(String claimsDeptName) {
        this.claimsDeptName = claimsDeptName;
    }

    public String getClaimsDeptName() {
        return claimsDeptName;
    }

    public void setTransportProperties(String transportProperties) {
        this.transportProperties = transportProperties;
    }

    public String getTransportProperties() {
        return transportProperties;
    }

    public void setCrmWfsFinishTime(String crmWfsFinishTime) {
        this.crmWfsFinishTime = crmWfsFinishTime;
    }

    public String getCrmWfsFinishTime() {
        return crmWfsFinishTime;
    }
}