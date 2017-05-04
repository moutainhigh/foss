/**
 * @author foss 257200
 * 2015-6-18
 * 257220
 */
package com.deppon.foss.module.transfer.unload.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrToQmsErrorService;
import com.deppon.foss.module.transfer.common.api.shared.define.QmsErrorConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrResponseParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrorMainEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsLDErrSubHasWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IBatchLoadingReportService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 257220
 */
public class BatchLoadingReportService implements IBatchLoadingReportService {
    /**
     * 记录日志
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 配置参数service
     */
    private IConfigurationParamsService configurationParamsService;

    private IBatchLoadingReportDao batchLoadingReportDao;
    private ITfrCommonService tfrCommonService;
    private IUnloadTaskService unloadTaskService;

    private IEmployeeService employeeService;
    /**
     * 配载单service
     */
    private IVehicleAssembleBillService vehicleAssembleBillService;
    /**
     * 综合管理 组织信息 Service
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 组织接口
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 交接单service
     */
    private IHandOverBillService handOverBillService;
    /**
     * 查询运单信息接口
     */
    private IWaybillManagerService waybillManagerService;
    /**
     * 数据字典接口
     */
    private IDataDictionaryValueService dataDictionaryValueService;

    /**
     * 产品接口
     */
    @Resource
    private IProductService productService4Dubbo;
    /**
     * 上报差错接口
     */
    private ITfrToQmsErrorService tfrToQmsErrorService;
    /**
     * 签收接口
     */
    private IWaybillSignResultService waybillSignResultService;
    private static final String remark = "生成分批配载差异报告时获取配置参数失败！";

    /**
     * 上报分批配载差异数据
     */
    public void reportForQMS() {
        LOGGER.info("reportForQMS");
        //获取时间约束参数
        List<DataDictionaryValueEntity> paramList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.AUTO_BATCHLOADING_REPORT_PARAMS);
        Map<String, Object> map = new HashMap<String, Object>();
        if (paramList != null) {
            for (DataDictionaryValueEntity dataDictionaryValueEntity : paramList) {
                String valueCode = dataDictionaryValueEntity.getValueCode();
                String valueName = dataDictionaryValueEntity.getValueName();
                map.put(valueCode, Double.parseDouble(valueName));
            }
        }
        //获取数据
        List<BatchLoadingReportDataEntity> unreportedBatchLoadingData = batchLoadingReportDao.queryUnreportedBatchLoadingData(map);
        //如果没有上报的数据，直接返回
        if (unreportedBatchLoadingData == null) {
            LOGGER.error("无分批配载上报差错数据");
            return;
        }
        //循环上报数据
        for (BatchLoadingReportDataEntity batchLoadingReportDataEntity : unreportedBatchLoadingData) {
            QmsErrResponseParam qmsErrResponseParam = null;
            BatchLoadingReportLogEntity batchLoadingReportLogEntity = new BatchLoadingReportLogEntity();
            boolean isCheck = true; //默认检查通过
            try {
                String[] checkResult = beforeReportCheck(batchLoadingReportDataEntity);
                if (checkResult != null) { //检查不通过
                    isCheck = false;
                    batchLoadingReportDataEntity.setNoReportReasonNo(checkResult[0]);
                    batchLoadingReportDataEntity.setNoReportReason(checkResult[1]);
                    batchLoadingReportDataEntity.setIsNeedReport(QmsErrorConstants.IS_NEED_REPORT_NONEED);
                } else {
                    qmsErrResponseParam = this.reportQMSBatchLoadingGoods(batchLoadingReportDataEntity);
                    if (qmsErrResponseParam != null) {
                        String errorID = qmsErrResponseParam.getErrorID();
                        //设置差错编号
                        batchLoadingReportDataEntity.setErrorNo(errorID);
                        //如果接口显示上报成功
                        if (StringUtils.equals(qmsErrResponseParam.getHandleCode(), QmsErrorConstants.HANDLECODE_SUCCESS)) {
                            batchLoadingReportDataEntity.setNoReportReasonNo(QmsErrorConstants.NO_NEED_REPORT_REASON_NO_REPORT_SUCCESS);
                            batchLoadingReportDataEntity.setNoReportReason(QmsErrorConstants.NO_NEED_REPORT_REASON_REPORT_SUCCESS);
                            batchLoadingReportDataEntity.setIsNeedReport(QmsErrorConstants.IS_NEED_REPORT_NONEED);
                        } else { //如果上报失败
                            /*以下场景无需上报，是由接口调用者判断，并返回信息*/
                            //一个单号只允许报一次分批配载差错。（QMS判断）
                            //3、若卸车部门已上报过该单的卸车有货无单差错，则不允许上报分批配载；（QMS判断）
                            //4、若卸车部门已上报过该单的卸车丢货，则不允许上报分批配载；（QMS判断）
                            if (StringUtils.equals(qmsErrResponseParam.getHandleCode(), QmsErrorConstants.HANDLECODE_REPORTED)
                                    || StringUtils.equals(qmsErrResponseParam.getHandleCode(), QmsErrorConstants.HANDLECODE_HASGOODS_NOBILLS)
                                    || StringUtils.equals(qmsErrResponseParam.getHandleCode(), QmsErrorConstants.HANDLECODE_UNLOADLESSGOODS_REPORTED)) {
                                batchLoadingReportDataEntity.setNoReportReasonNo(qmsErrResponseParam.getHandleCode());
                                batchLoadingReportDataEntity.setNoReportReason(qmsErrResponseParam.getMessage());
                                batchLoadingReportDataEntity.setIsNeedReport(QmsErrorConstants.IS_NEED_REPORT_NONEED);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("上报运单号为：" + batchLoadingReportDataEntity.getWayBillNo() + "的分批配载差错失败", e);
                //为方便排查问题，加入exception
                batchLoadingReportLogEntity.setException(e.getMessage());
            } finally {
                if (isCheck) {
                    //保存每一次上报日志信息
                    batchLoadingReportLogEntity.setId(UUIDUtils.getUUID());
                    batchLoadingReportLogEntity.setReportDataId(batchLoadingReportDataEntity.getId());
                    batchLoadingReportLogEntity.setReportTime(new Date());
                    batchLoadingReportLogEntity.setReportStatus(QmsErrorConstants.REPORT_STATUS_FAIL);
                    if (qmsErrResponseParam != null) {
                        if (StringUtils.equals(QmsErrorConstants.HANDLECODE_SUCCESS, qmsErrResponseParam.getHandleCode())) {
                            batchLoadingReportLogEntity.setReportStatus(QmsErrorConstants.REPORT_STATUS_SUCCESS);
                        }
                        batchLoadingReportLogEntity.setReturnMessage(qmsErrResponseParam.getMessage());
                        batchLoadingReportLogEntity.setReturnResult(qmsErrResponseParam.getHandleCode());
                    } else {
                        batchLoadingReportLogEntity.setException("上报出现异常。返回结果信息为空，或返回结果exceptionCode为：S000099");
                    }
                    batchLoadingReportDao.addBatchLoadingReportLog(batchLoadingReportLogEntity);
                }
                //只有无需上报的数据更新
                if (StringUtils.equals(batchLoadingReportDataEntity.getIsNeedReport(), QmsErrorConstants.IS_NEED_REPORT_NONEED)) {
                    //更新上报数据信息
                    batchLoadingReportDao.updateBatchLoadingReportData(batchLoadingReportDataEntity);
                }
            }
        }
    }

    /**
     * <ul>上报前的检查工作</ul>
     * <li>已经全部签收的无需上报</li>
     * <li>未通过检查，则返回不通过编号及原因</li>
     *
     * @author 257220
     * @date 2015-6-23下午2:29:20
     */
    private String[] beforeReportCheck(BatchLoadingReportDataEntity batchLoadingReportDataEntity) {
        String[] checkResult = new String[2];
        //调用签收接口
        WaybillSignResultEntity waybillSignResultEntity = new WaybillSignResultEntity();
        waybillSignResultEntity.setWaybillNo(batchLoadingReportDataEntity.getWayBillNo());
        waybillSignResultEntity.setActive(FossConstants.ACTIVE);
        waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntity);
        if (waybillSignResultEntity != null) {
            if (StringUtils.equals(SignConstants.SIGN_STATUS_ALL, waybillSignResultEntity.getSignStatus())) {
                checkResult[0] = QmsErrorConstants.NO_NEED_REPORT_REASON_NO_SIGN;
                checkResult[1] = QmsErrorConstants.NO_NEED_REPORT_REASON_SIGN;
                return checkResult;
            }
        }
        return null;
    }

    /*
     * 生成分批配载上报数据
     * (non-Javadoc)
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IBatchLoadingReportService#createBatchLoadReport(java.util.Date)
     */
    @Override
    public int createBatchLoadingReport(Date bizStartTime, Date bizEndTime) {
        LOGGER.error("createBatchLoadReport");
        LOGGER.info("---分批配载差异报告，生成开始---");
        //取得零担产品项
        //List<String> cargoProductCodes = this.queryLevel3CargoProduct();
        List<String> cargoProductCodes = productService4Dubbo.getAllLevels3CargoProductCode();
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("BatchLoadingReportService调用计价Dubbo接口成功，productService4Dubbo = {} " ,productService4Dubbo);
        }
        //查询参数map
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("upperLimit", bizEndTime);
        parameterMap.put("lowerLimit", bizStartTime);
        parameterMap.put("productCodeList", cargoProductCodes);
        parameterMap.put("taskState", UnloadConstants.UNLOAD_TASK_STATE_FINISHED); //卸车任务状态
        //配置的约束参数项 目前的约束有 重量与体积
        List<DataDictionaryValueEntity> paramList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.CREATE_BATCHLOADING_REPORT_PARAMS);
        if (paramList != null) {
            for (DataDictionaryValueEntity dataDictionaryValueEntity : paramList) {
                //将配置参数放入map中
                String code = dataDictionaryValueEntity.getValueCode();
                String value = dataDictionaryValueEntity.getValueName();
                parameterMap.put(code, Integer.parseInt(value));
            }
        }
		 /*获取差异*/
        List<BatchLoadingReportDataEntity> list = batchLoadingReportDao.queryUnresolvedBatchLoadingData(parameterMap);
        if (list == null || list.size() == 0) {
            LOGGER.error("在" + bizStartTime + "至" + bizEndTime + "时无差错！");
            return FossConstants.SUCCESS;
        }
        Map<String, List<BatchLoadingReportDataEntity>> map = new HashMap<String, List<BatchLoadingReportDataEntity>>();
		 /*插入分批配载数据 */
        for (BatchLoadingReportDataEntity batchLoadingReportDataEntity : list) {
            String unLoadTaskId = batchLoadingReportDataEntity.getUnloadTaskId();
            ArrayList<BatchLoadingReportDataEntity> listByUnloadTaskId = (ArrayList<BatchLoadingReportDataEntity>) map.get(unLoadTaskId);
            if (listByUnloadTaskId == null) {
                listByUnloadTaskId = new ArrayList<BatchLoadingReportDataEntity>();
                map.put(unLoadTaskId, listByUnloadTaskId);
            }
            batchLoadingReportDataEntity.setId(UUIDUtils.getUUID());//分批配载差错数据id
            batchLoadingReportDataEntity.setIsNeedReport(QmsErrorConstants.IS_NEED_REPORT_NEED);//是否需要上报
            batchLoadingReportDataEntity.setCreateTime(new Date());
            listByUnloadTaskId.add(batchLoadingReportDataEntity);
        }
        for (Map.Entry<String, List<BatchLoadingReportDataEntity>> entry : map.entrySet()) {
            try {
                //插入分批配载差错信息
                addBatchLoadingReport(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
                jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.CREATE_BATCHLOADING_REPORT_JOB.getBizName());
                jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.CREATE_BATCHLOADING_REPORT_JOB.getBizCode());
                jobProcessLogEntity.setRemark(remark);
                jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
                jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
                tfrCommonService.addJobProcessLog(jobProcessLogEntity);
            }
        }
        return FossConstants.SUCCESS;
    }

    /**
     * 循环插入分批配载差错信息
     */
    @Transactional
    private int addBatchLoadingReport(String unloadTaskId, List<BatchLoadingReportDataEntity> batchLoadingReportDetailList) {
        //先执行插入操作
        for (BatchLoadingReportDataEntity batchLoadingReportDataEntity : batchLoadingReportDetailList) {
            //开单件数大于1，交接件数大于0
            if (StringUtils.isNotBlank(batchLoadingReportDataEntity.getGoodsQtyTotal()) && Integer.parseInt(batchLoadingReportDataEntity.getGoodsQtyTotal()) > 1 && StringUtils.isNotBlank(batchLoadingReportDataEntity.getUnloadGoodsQty()) && Integer.parseInt(batchLoadingReportDataEntity.getUnloadGoodsQty()) > 0) {
                batchLoadingReportDao.addbatchLoadingReport(batchLoadingReportDataEntity);
            }
        }
        //更新卸车任务的“是否生成分批配载差异数据”字段为‘Y’
        UnloadTaskEntity task = new UnloadTaskEntity();
        task.setId(unloadTaskId);
        task.setBeCreatedBL(FossConstants.YES);
        unloadTaskService.updateUnloadTaskBasicInfo(task);
        return FossConstants.SUCCESS;
    }

    /**
     * 上报数据
     *
     * @param batchLoadingReportDataEntity
     * @return
     * @author 257220
     * @date 2015-6-19下午5:54:15
     */
    private QmsErrResponseParam reportQMSBatchLoadingGoods(BatchLoadingReportDataEntity batchLoadingReportDataEntity) throws Exception {
        QmsErrRequestParam request = buildBatchLoadingReportInfo(batchLoadingReportDataEntity);
        LOGGER.error("上报分批配载数据： " + JSONObject.toJSONString(request));
        QmsErrResponseParam qmsErrResponseParam = tfrToQmsErrorService.reportQmsError(request, TransferConstants.QMS_ESB_CODE_BATCHLOADING);
        return qmsErrResponseParam;
    }

    /**
     * @param batchLoadingReportDataEntity
     * @return
     * @author 257220
     * @date 2015-6-19下午4:25:22
     */
    private QmsErrRequestParam buildBatchLoadingReportInfo(
            BatchLoadingReportDataEntity batchLoadingReportDataEntity) throws Exception {
        QmsErrRequestParam qmsErrRequestParam = new QmsErrRequestParam();
        qmsErrRequestParam.setErrCategoty(QmsErrorConstants.QMS_L);
        qmsErrRequestParam.setErrorTypeId(QmsErrorConstants.BATCH_LOADING_CODE);//差错类型编号
        String unloadTaskId = batchLoadingReportDataEntity.getUnloadTaskId();
        WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(batchLoadingReportDataEntity.getWayBillNo());
        UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
        // 差错上报主信息
        QmsErrorMainEntity qmsErrorMainEntity = buildErrorMainEntity(batchLoadingReportDataEntity, waybillEntity, unloadTask);
        qmsErrRequestParam.setMainInfo(qmsErrorMainEntity);
        //差错上报子信息
        QmsLDErrSubHasWaybillEntity qmsLDErrSubHasWaybillEntity = buildLDErrSubHasWaybillEntity(batchLoadingReportDataEntity, waybillEntity, unloadTask);
        qmsErrRequestParam.setLdsubHasInfo(qmsLDErrSubHasWaybillEntity);
        // 是否立即返回信息 为否
        qmsErrRequestParam.setReturnResult(false);
        return qmsErrRequestParam;
    }

    /**
     * 差错上报住信息
     *
     * @param batchLoadingReportDataEntity
     * @return
     * @author 257220
     * @date 2015-6-19下午4:31:39
     */
    private QmsErrorMainEntity buildErrorMainEntity(
            BatchLoadingReportDataEntity batchLoadingReportDataEntity, WaybillEntity waybillEntity, UnloadTaskEntity unloadTask) {
        QmsErrorMainEntity qmsErrorMainEntity = new QmsErrorMainEntity();
        String unloadTaskId = batchLoadingReportDataEntity.getUnloadTaskId();
        List<LoaderParticipationEntity> loaderEntityList = this.unloadTaskService.queryTaskCreatorLoaderByTaskId(unloadTaskId);
        if (CollectionUtils.isNotEmpty(loaderEntityList)) {
            LoaderParticipationEntity loader = loaderEntityList.get(0);
            //上报人工号
            String loaderCode = loader.getLoaderCode();
            qmsErrorMainEntity.setRepEmpcode(loaderCode);
            //上报人姓名
            qmsErrorMainEntity.setRepEmpName(loader.getLoaderName());
            EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(loaderCode);
            //上报人职位
            if (employee != null) {
                qmsErrorMainEntity.setRepEmpJob(employee.getTitleName());
            }
        }
        //运单号
        String wayBillNum = waybillEntity.getWaybillNo();
        qmsErrorMainEntity.setWayBillNum(wayBillNum);
        //上报部门 ：卸车部门
        String reportDeptCode = unloadTask.getUnloadOrgCode(); //上报部门为卸车部门
        OrgAdministrativeInfoEntity reportDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(reportDeptCode);
        if (reportDept != null) {
            //上报人部门标杆编码
            qmsErrorMainEntity.setRepDeptCode(reportDept.getUnifiedCode());
            //上报人部门名称
            qmsErrorMainEntity.setRepDeptName(reportDept.getName());

        }
		/*上报人事业部 ：上报部门所在事业部*/
        List<String> bizTypesList = new ArrayList<String>();
        //事业部类型
        bizTypesList.add(BizTypeConstants.ORG_DIVISION);
        OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
                queryOrgAdministrativeInfoByCode(reportDeptCode, bizTypesList);
        if (orgAdministrativeInfoEntity != null) {
            //上报人事业部标杆编码
            qmsErrorMainEntity.setRepDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
            //上报人事业部
            qmsErrorMainEntity.setRepDeptName(orgAdministrativeInfoEntity.getName());
        }
        //收货部门
        OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
        if (recieveEntity != null) {
            //收货部门标杆编码
            qmsErrorMainEntity.setReceiveDeptCode(recieveEntity.getUnifiedCode());
            //收货部门名称
            qmsErrorMainEntity.setReceiveDeptName(recieveEntity.getName());
        }
        //测试使用
        qmsErrorMainEntity.setDocStandarId(QmsErrorConstants.FILE_STANDARD_ID_BATCHLOADING);
        qmsErrorMainEntity.setDocStandarName(QmsErrorConstants.FILE_STANDARD_NAME_BATCHLOADING);
        return qmsErrorMainEntity;
    }

    private QmsLDErrSubHasWaybillEntity buildLDErrSubHasWaybillEntity(BatchLoadingReportDataEntity batchLoadingReportDataEntity, WaybillEntity waybillEntity, UnloadTaskEntity unloadTask) throws Exception {
        QmsLDErrSubHasWaybillEntity qmsLDErrSubHasWaybillEntity = new QmsLDErrSubHasWaybillEntity();
        //事件经过:分批配载
        qmsLDErrSubHasWaybillEntity.setIncident(QmsErrorConstants.EVENT_BATCH_LOADING);
        //短信通知对象工号
        //短信通知对象名称
        OrgAdministrativeInfoEntity loadOrg = queryPreviousOrgForUnloadDiff(unloadTask);
        if (loadOrg != null) {
			/*责任人：责任部门负责人；若找不到负责人，就往上找至大区为止*/
            Map<String, String> principalMap = getPrincipal(loadOrg.getCode());
            if (principalMap != null) {
                //责任人工号 respEmpCode
                qmsLDErrSubHasWaybillEntity.setRespEmpCode(principalMap.get("userCode"));
                //责任人姓名 respEmpName
                qmsLDErrSubHasWaybillEntity.setRespEmpName(principalMap.get("userName"));
            }
			/*责任部门：装车部门**/
            //标杆编码
            qmsLDErrSubHasWaybillEntity.setRespDeptCode(loadOrg.getUnifiedCode());
            //责任部门名称
            qmsLDErrSubHasWaybillEntity.setRespDeptName(loadOrg.getName());
			/*责任部门事业部*/
            //设置查询参数
            List<String> bizTypesList = new ArrayList<String>();
            //事业部类型
            bizTypesList.add(BizTypeConstants.ORG_DIVISION);
            //查询上级部门
            OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
                    queryOrgAdministrativeInfoByCode(loadOrg.getCode(), bizTypesList);
            if (orgAdministrativeInfoEntity != null) {
                //责任事业部门标杆代码
                qmsLDErrSubHasWaybillEntity.setDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
                //责任事业部编号
                qmsLDErrSubHasWaybillEntity.setDivisionName(orgAdministrativeInfoEntity.getName());
            } else {
                LOGGER.warn("分批配载上报QMS：查询部门【" + loadOrg.getCode() + "】上一级事业部失败！");
            }
        }
        //到达部门
        String customerPickupOrgCode = waybillEntity.getCustomerPickupOrgCode();
        OrgAdministrativeInfoEntity customerPickupOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(customerPickupOrgCode);
        if (customerPickupOrgEntity != null) {
            //到达部门标杆编码
            qmsLDErrSubHasWaybillEntity.setArriveDeptCode(customerPickupOrgEntity.getUnifiedCode());
        }
        //到达部门名称
        qmsLDErrSubHasWaybillEntity.setArriveDeptName(waybillEntity.getCustomerPickupOrgName());
        //开单部门
        OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
        if (createEntity != null) {
            //开单部门标杆编码
            qmsLDErrSubHasWaybillEntity.setBillingDeptCode(createEntity.getUnifiedCode());
            //开单部门名称
            qmsLDErrSubHasWaybillEntity.setBillingDeptName(createEntity.getName());
        }
        String productCode = waybillEntity.getProductCode();
        //运输类型
        DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType());
        if (dictEntity1 == null) {
            //运输类型为空时，根据运输性质查询对应的一级产品
            ProductEntity entity = productService4Dubbo.getProductLele(productCode, null, 1);
            if (entity != null) {
                qmsLDErrSubHasWaybillEntity.setTransType(entity.getName());
            }
        } else {
            //运输类型
            qmsLDErrSubHasWaybillEntity.setTransType(dictEntity1.getValueName());
        }
        //托运人
        qmsLDErrSubHasWaybillEntity.setShipper(waybillEntity.getDeliveryCustomerContact());
        //运输性质
        qmsLDErrSubHasWaybillEntity.setTransNature(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());
        //收货人
        qmsLDErrSubHasWaybillEntity.setReceiverName(waybillEntity.getReceiveCustomerContact());
		/*收货人电话*/
        if (StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())) {
            //若收货人电话为空，则传入收货人手机
            qmsLDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerMobilephone());
        } else {
            //若收货人电话不为空，则传入收货人电话
            qmsLDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerPhone());
        }
		/*提货方式*/
        DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
        if (dictEntity3 == null) {
            //若数据字典为空，则传入常量
            qmsLDErrSubHasWaybillEntity.setPickUpType(QmsErrorConstants.UNKNOWN_TYPE);
        } else {
            //提货方式
            qmsLDErrSubHasWaybillEntity.setPickUpType(dictEntity3.getValueName());
        }
        //总重量
        qmsLDErrSubHasWaybillEntity.setSumWeight(waybillEntity.getGoodsWeightTotal() == null ? "" : waybillEntity.getGoodsWeightTotal().toString());
        //总体积
        qmsLDErrSubHasWaybillEntity.setSumVolume(waybillEntity.getGoodsVolumeTotal() == null ? "" : waybillEntity.getGoodsVolumeTotal().toString());
        //总件数
        qmsLDErrSubHasWaybillEntity.setSumNumber(waybillEntity.getGoodsQtyTotal() == null ? "" : waybillEntity.getGoodsQtyTotal().toString());
		/*//重量
		qmsLDErrSubHasWaybillEntity.setWeight(waybillEntity.getGoodsWeightTotal() == null?QmsErrorConstants.DEFAULT_VALUE_EMPTY:waybillEntity.getGoodsWeightTotal().toString());*/
        //储运事项
        qmsLDErrSubHasWaybillEntity.setStorageTransport(waybillEntity.getTransportationRemark());
		/*//体积
		qmsLDErrSubHasWaybillEntity.setVolume(waybillEntity.getGoodsVolumeTotal() == null?QmsErrorConstants.DEFAULT_VALUE_EMPTY:waybillEntity.getGoodsVolumeTotal().toString());*/
        //件数
        //qmsLDErrSubHasWaybillEntity.setNum(waybillEntity.getGoodsQtyTotal() == null?QmsErrorConstants.DEFAULT_VALUE_EMPTY:waybillEntity.getGoodsQtyTotal().toString());
        //货物名称
        qmsLDErrSubHasWaybillEntity.setGoodsName(waybillEntity.getGoodsName());
        //开单时间：文本（格式：X年-X月-X日）  "yyyy-MM-dd"
        qmsLDErrSubHasWaybillEntity.setBillingTime(DateUtils.convert(waybillEntity.getBillTime(), DateUtils.DATE_CH_FORMAT));
        //开单人工号
        qmsLDErrSubHasWaybillEntity.setFillopenEmpCode(waybillEntity.getCreateUserCode());
        //开单人姓名
        qmsLDErrSubHasWaybillEntity.setFillopenEmpName(waybillEntity.getCreateUser());
        //是否集中接送货
        String isConcentReceiving = waybillEntity.getPickupCentralized();
        if (StringUtils.equals(FossConstants.ACTIVE, isConcentReceiving)) {
            qmsLDErrSubHasWaybillEntity.setIsConcentReceiving("是");
        } else if (StringUtils.equals(FossConstants.INACTIVE, isConcentReceiving)) {
            qmsLDErrSubHasWaybillEntity.setIsConcentReceiving("否");
        }

        //收货部门
        OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
        if (recieveEntity != null) {
            //收货部门标杆编码
            qmsLDErrSubHasWaybillEntity.setTakeOverDeptCode(recieveEntity.getUnifiedCode());
            //收货部门名称
            qmsLDErrSubHasWaybillEntity.setTakeOverDeptName(recieveEntity.getName());
        }
		/*付款方式*/
        DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod());
        if (dictEntity4 == null) {
            //若数据字典为空，则传入常量
            qmsLDErrSubHasWaybillEntity.setPayType(QmsErrorConstants.UNKNOWN_TYPE);
        } else {
            //付款方式
            qmsLDErrSubHasWaybillEntity.setPayType(dictEntity4.getValueName());
        }
        //保险金额
        qmsLDErrSubHasWaybillEntity.setSafeMoney(waybillEntity.getInsuranceFee() == null ? QmsErrorConstants.DEFAULT_VALUE_EMPTY : waybillEntity.getInsuranceFee().toString());

        //货物包装
        qmsLDErrSubHasWaybillEntity.setGoodsPackage(waybillEntity.getGoodsPackage());
        //包装费
        qmsLDErrSubHasWaybillEntity.setPackageFee(waybillEntity.getPackageFee() + "");
        //送货费 DELIVERY_GOODS_FEE
        qmsLDErrSubHasWaybillEntity.setDeliveryFee(waybillEntity.getDeliveryGoodsFee() == null ? QmsErrorConstants.DEFAULT_VALUE_EMPTY : waybillEntity.getDeliveryGoodsFee().toString());
        //运费总额 TOTAL_FEE
        qmsLDErrSubHasWaybillEntity.setFreightSumFee(waybillEntity.getTotalFee() == null ? QmsErrorConstants.DEFAULT_VALUE_EMPTY : waybillEntity.getTotalFee().toString());
        //卸车任务编号
        qmsLDErrSubHasWaybillEntity.setUnloadBusiCode(unloadTask.getUnloadTaskNo());
        //卸车部门
        String reportDeptCode = unloadTask.getUnloadOrgName();
        qmsLDErrSubHasWaybillEntity.setUnloadingDeptName(reportDeptCode);
        //车牌号
        qmsLDErrSubHasWaybillEntity.setCarCode(unloadTask.getVehicleNo());
        //卸车人工号
        List<LoaderParticipationEntity> loaderEntityList = this.unloadTaskService.queryTaskCreatorLoaderByTaskId(unloadTask.getId());
        if (CollectionUtils.isNotEmpty(loaderEntityList)) {
            LoaderParticipationEntity loader = loaderEntityList.get(0);
            qmsLDErrSubHasWaybillEntity.setUnloadPersonCode(loader.getLoaderCode());
            qmsLDErrSubHasWaybillEntity.setUnloadPersonName(loader.getLoaderName());
        }
        Map<String, String> loader = getLoaderByUnloadTaskIdWaybillNo(unloadTask.getId(), waybillEntity.getWaybillNo());
        if (loader != null) {
            //装车人工号
            qmsLDErrSubHasWaybillEntity.setLoadPersonCode(loader.get("loaderCode"));
            //装车人名称
            qmsLDErrSubHasWaybillEntity.setLoadPersonName(loader.get("loaderName"));
        }
        //首次分批装车部门标杆编码
        OrgAdministrativeInfoEntity firstBatchLoadingDepart = getFirstBatchLoadingDepart(waybillEntity.getWaybillNo());
        if (firstBatchLoadingDepart != null) {
            //首次分批装车部门标杆编码
            qmsLDErrSubHasWaybillEntity.setFristLoadDeptCode(firstBatchLoadingDepart.getUnifiedCode());
            //首次分批装车部门
            qmsLDErrSubHasWaybillEntity.setFristLoadDeptName(firstBatchLoadingDepart.getName());
        }
        //分批配载流水号
        List<String> serialList = batchLoadingReportDao.getBatchLoadingSerials(unloadTask.getId(), waybillEntity.getWaybillNo());
        StringBuffer serials = new StringBuffer();
        if (serialList != null) {
            for (int i = 0; i < serialList.size(); i++) {
                if (i + 1 == serialList.size()) {
                    serials.append(serialList.get(i));
                } else {
                    serials.append(serialList.get(i)).append(QmsErrorConstants.REPORT_SEPARATOR);
                }
            }
        }
        qmsLDErrSubHasWaybillEntity.setBatchFlowcode(serials.toString());
        return qmsLDErrSubHasWaybillEntity;
    }

    /**
     * 调用该方法获取上一环节部门
     *
     * @author 045923-foss-shiwei
     * @date 2013-2-28 下午6:44:46
     */
    private OrgAdministrativeInfoEntity queryPreviousOrgForUnloadDiff(UnloadTaskEntity unloadTask) {
        if (unloadTask != null) {
            //卸车类型
            String unloadType = unloadTask.getUnloadType();
            String unloadTaskId = unloadTask.getId();
            //获取卸车任务下的单据
            List<UnloadBillDetailEntity> billList = unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
            //如果为长途卸车
            if (StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)) {
                if (CollectionUtils.isNotEmpty(billList)) {
                    //310248 防止由于查询数据库是get（0）不是配载单
                    String vehicleAssembleNo = null;
                    for (int i = 0; i < billList.size(); i++) {
                        //310248 获取卸车任务单据明细的单据类型，只要单据类型是配载单
                        if (UnloadConstants.UNLOAD_TASK_BILL_TYPE.equals(billList.get(i).getBillType())) {
                            vehicleAssembleNo = billList.get(i).getBillNo();
                            break;
                        }
                    }
                    //调用配载单服务，获取出发部门code
                    List<VehicleAssembleBillEntity> vehicleAssembleBillList = this.vehicleAssembleBillService.queryVehicleAssembleBillByNo(vehicleAssembleNo);
                    if (CollectionUtils.isNotEmpty(vehicleAssembleBillList)) {
                        //出发部门code
                        String departOrgCode = vehicleAssembleBillList.get(0).getOrigOrgCode();
                        //获取出发部门实体
                        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
                        return orgEntity;
                    } else {
                        LOGGER.error("###################根据配载单号" + vehicleAssembleNo + "获取到的配载单为空！");
                    }
                } else {
                    LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
                }//如果为短途卸车
            } else if (StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)
                    || StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_PARTIALLINE)) {
                if (CollectionUtils.isNotEmpty(billList)) {
                    //获取卸车任务单据明细
                    UnloadBillDetailEntity billEntity = billList.get(0);
                    //获取交接单号
                    String handOverBillNo = billEntity.getBillNo();
                    //调用交接单服务，获取出发部门code
                    HandOverBillEntity handOverBill = this.handOverBillService.queryHandOverBillByNo(handOverBillNo);
                    //出发部门code
                    String departOrgCode = handOverBill.getDepartDeptCode();
                    //获取出发部门实体
                    OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
                    return orgEntity;
                } else {
                    LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
                }
            } else {
                LOGGER.error("卸车任务（编号：" + unloadTask.getUnloadTaskNo() + unloadType + "）类型无法识别！");
            }
        }
        return null;
    }

    /**
     * @param deptCode
     * @return
     * @author nly
     * @date 2015年5月5日 上午10:39:21
     * @function 根据部门code查询部门负责人，向上查找，最高至大区
     */
    private Map<String, String> getPrincipal(String deptCode) {
        Map<String, String> map = new HashMap<String, String>();
        //是否已到大区
        String bigRegion = "N";
        for (int i = 0; i < ConstantsNumberSonar.SONAR_NUMBER_6; i++) {
            //最高判断至大区
            if (StringUtils.equals("Y", bigRegion)) {
                return null;
            }
            OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
            if (null == org) {
                return null;
            }
            bigRegion = org.getBigRegion();
            //负责人工号
            String userCode = org.getPrincipalNo();
            if (StringUtils.isNotEmpty(userCode)) {

                EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(userCode);
                if (null != employee) {
                    String userName = employee.getEmpName();
                    map.put("userCode", userCode);
                    map.put("userName", userName);
                    return map;
                } else {
                    //查找上一部门负责人
                    deptCode = org.getParentOrgCode();
                }
            } else {
                //查找上一部门负责人
                deptCode = org.getParentOrgCode();
            }
        }

        return null;
    }

    /**
     * 获取所有零担产品
     */
    private List<String> queryLevel3CargoProduct() {
        //获取所有三级产品
        List<ProductEntity> all3LevelProductList = productService4Dubbo.queryLevel3ProductInfo();
        List<String> list = new ArrayList<String>();
        Date now = new Date();
        //排除掉快递产品
        for (ProductEntity productEntity : all3LevelProductList) {
            String productCode = productEntity.getCode();
            if (!productService4Dubbo.onlineDetermineIsExpressByProductCode(productCode, now)) {
                list.add(productCode);
            }
        }
        return list;
    }

    /**
     * 根据装车任务和运单号获取首次分批配载装车部门
     *
     * @return
     * @author 257220
     * @date 2015-6-30上午8:08:38
     */
    private OrgAdministrativeInfoEntity getFirstBatchLoadingDepart(String waybillNo) {
        String unloadTaskId = batchLoadingReportDao.getFirstBatchLoadingTaskId(waybillNo);
        OrgAdministrativeInfoEntity loadOrg = null;
        if (unloadTaskId != null) {
            UnloadTaskEntity firstBatchUnloadTask = unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
            loadOrg = queryPreviousOrgForUnloadDiff(firstBatchUnloadTask);
        }
        return loadOrg;
    }

    /**
     * 根据卸车任务编号,运单号获取装车人
     *
     * @param unloadTaskId
     * @return
     * @author 257220
     * @date 2015-7-1下午5:17:47
     */
    private Map<String, String> getLoaderByUnloadTaskIdWaybillNo(String unloadTaskId, String waybillNo) throws Exception {
        if (StringUtils.isEmpty(unloadTaskId) || StringUtils.isEmpty(waybillNo)) {
            throw new Exception("卸车任务id或运单号为空！");
        }
        return batchLoadingReportDao.getLoaderByUnloadTaskIdWaybillNo(unloadTaskId, waybillNo);
    }

    public IConfigurationParamsService getConfigurationParamsService() {
        return configurationParamsService;
    }

    public void setConfigurationParamsService(
            IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    public IBatchLoadingReportDao getBatchLoadingReportDao() {
        return batchLoadingReportDao;
    }

    public void setBatchLoadingReportDao(
            IBatchLoadingReportDao batchLoadingReportDao) {
        this.batchLoadingReportDao = batchLoadingReportDao;
    }

    public ITfrCommonService getTfrCommonService() {
        return tfrCommonService;
    }

    public void setTfrCommonService(ITfrCommonService tfrCommonService) {
        this.tfrCommonService = tfrCommonService;
    }

    public IUnloadTaskService getUnloadTaskService() {
        return unloadTaskService;
    }

    public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
        this.unloadTaskService = unloadTaskService;
    }

    public IEmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public IVehicleAssembleBillService getVehicleAssembleBillService() {
        return vehicleAssembleBillService;
    }

    public void setVehicleAssembleBillService(
            IVehicleAssembleBillService vehicleAssembleBillService) {
        this.vehicleAssembleBillService = vehicleAssembleBillService;
    }

    public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
        return orgAdministrativeInfoComplexService;
    }

    public void setOrgAdministrativeInfoComplexService(
            IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
        return orgAdministrativeInfoService;
    }

    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public IHandOverBillService getHandOverBillService() {
        return handOverBillService;
    }

    public void setHandOverBillService(IHandOverBillService handOverBillService) {
        this.handOverBillService = handOverBillService;
    }

    public IWaybillManagerService getWaybillManagerService() {
        return waybillManagerService;
    }

    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    public IDataDictionaryValueService getDataDictionaryValueService() {
        return dataDictionaryValueService;
    }

    public void setDataDictionaryValueService(
            IDataDictionaryValueService dataDictionaryValueService) {
        this.dataDictionaryValueService = dataDictionaryValueService;
    }

	/*public IProductService getProductService() {
		return productService;
	}*/

	/*public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/

    public IWaybillSignResultService getWaybillSignResultService() {
        return waybillSignResultService;
    }

    public void setWaybillSignResultService(
            IWaybillSignResultService waybillSignResultService) {
        this.waybillSignResultService = waybillSignResultService;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public static String getRemark() {
        return remark;
    }

    public void setTfrToQmsErrorService(ITfrToQmsErrorService tfrToQmsErrorService) {
        this.tfrToQmsErrorService = tfrToQmsErrorService;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.convert(new Date(), DateUtils.DATE_CH_FORMAT));
    }
}
