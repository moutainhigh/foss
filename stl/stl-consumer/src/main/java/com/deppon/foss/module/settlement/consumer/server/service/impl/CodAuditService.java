package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeDtoToSTL;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditLogService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 073615 on 2015/11/30.
 */
public class CodAuditService implements ICodAuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodAuditService.class);
    /**
     * 待审核代收货款导出
     */
    ICodAuditDao codAuditDao;
    /**
     *待审核代收货款日志审核包
     */
    ICodAuditLogService codAuditLogService;
   /**
    * 综合接口,查询部门信息
    */
    IOrgAdministrativeInfoService	orgAdministrativeInfoService;
	/**
     * 综合接口,到达部门收银员姓名Cashier,到达部门电话TEL
     */
    IEmployeeService employeeService;
    /**
     * 综合接口,根据到达部门编号 ,查询出所属部门的经营本部
     */
    IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 综合接口,查询开户行的name
     */
    IBankService bankService;
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
     * 根据运单号进行更新
     * @param dto
     * @return
     */
    @Override
    public int codAuditByWaybillNo(CodAuditDto dto) {
        return 0;
    }
    /**
     * 根据条件查询是否存在对应的状态
     * @param list
     * @param status
     * @return
     */
    private Map<String,String> hasStatus(List<CodAuditEntity> list,List<String> status){
        Map<String,String> map = new HashMap<String, String>();
        //根据条件获取对应的状态明细
        if(CollectionUtils.isNotEmpty(list)
                &&CollectionUtils.isNotEmpty(status)){
        for(CodAuditEntity entity :list){
            if(status.contains(entity.getLockStatus())){
                map.put(entity.getWaybillNo(),getAllStauts().get(entity.getLockStatus()));
                break;
            }
        }
        }
        return map;

    }

    /**
     * 获取所有状态
     * @return
     */
    private Map<String,String> getAllStauts(){
        Map<String,String> map = new HashMap<String, String>();
        map.put(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT,"资金部待审核");
        map.put(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK,"资金部锁定");
        map.put(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT,"复核会计待审核");
        map.put(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK,"复核会计锁定");
        map.put(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWOVER,"复核会计审核通过");
        map.put(SettlementDictionaryConstants.SETTLE_SHORT_LOCK, "短期锁定");
        map.put(SettlementDictionaryConstants.SETTLE_LONG_LOCK, "长期锁定");
        return map;
    }
    @Override
	public CodAuditDto queryCodAudit(CodAuditDto dto) {
    	 List<CodAuditEntity> entityList = codAuditDao.selectCodAuditEntity(dto);
    	 /**
          * 310970 caoepng
          * 筛选经营本部,添加显示的字段
          * */
         String  bizType = "IS_MANAGE_DEPARTMENT";
     	List<String> bizTypes = new  ArrayList<String>();
     	bizTypes.add(bizType);
         //判断 从前台传来的经营本部,不为空 进行以下操作
         if(StringUtils.isNotEmpty(dto.getManageDepartmentList())){
         	//定义一个集合获取前台传过来的经营本部
         	String  department = dto.getManageDepartmentList();
         	List<String> manageDepartmentList = new  ArrayList<String>();
         	manageDepartmentList.add(department);
         	//遍历循环未经过经营本部筛选的实体
         	for(int i=0;i<entityList.size();i++ ){
         		CodAuditEntity entity = entityList.get(i);
         		//调用综合接口,根据到达部门的code, 查询经营本部
         		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity =
         				orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(entity.getDestOrgCode(),bizTypes);
         		if(StringUtils.isNotEmpty(orgAdministrativeInfoEntity.getName())){
         		//给实体类赋值经营本部,显示字段所用
         			entity.setManageDepartment(orgAdministrativeInfoEntity.getName());
         		}
         		//判断筛选经营本部的实体,遍历
         		for(String string:manageDepartmentList){
         			//调用综合接口,查询对应的经营本部
         			//前台页面上传过来的,经营本部  跟   到达部门的到达经营本部 做对比筛选
         			OrgAdministrativeInfoEntity customerOrgEntity =orgAdministrativeInfoComplexService.
         					queryOrgAdministrativeInfoByCode(string,bizTypes);
         			//筛选,若是跟前台的不一样  则移出该实体
         			if(!entity.getManageDepartment().equals(customerOrgEntity.getName())){
         				entityList.remove(entity);
         				i--;
         			}
         		}
         	}
         }
         CodAuditDto dtoa = new CodAuditDto();
         List<String>  waybills = new ArrayList<String>();
         for(CodAuditEntity ebtity : entityList){
         	waybills.add(ebtity.getWaybillNo());
         }
         dtoa.setWaybillNos(waybills);
         CodAuditDto dto2=queryTotalByConditon(dtoa);
		return dto2;
	}
    /**
     * 分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    @Override
    public List<CodAuditEntity> queryCodAuditByPage(CodAuditDto dto, int start, int limit) {
        if(limit ==0){
            throw new SettlementException("查询没页条数为0");
        }else if(dto == null){
            throw new SettlementException("查询条件不能为空");
        }
        
        List<CodAuditEntity> entityList = codAuditDao.selectByPage(dto,start,limit);
        
        
        /**
         * 310970 caoepng
         * 筛选经营本部,添加显示的字段
         * */
        /*String  bizType = "IS_MANAGE_DEPARTMENT";
    	List<String> bizTypes = new  ArrayList<String>();
    	bizTypes.add(bizType);
        //判断 从前台传来的经营本部,不为空 进行以下操作
        if(StringUtils.isNotEmpty(dto.getManageDepartmentList())){
        	//定义一个集合获取前台传过来的经营本部
        	String  department = dto.getManageDepartmentList();
        	List<String> manageDepartmentList = new  ArrayList<String>();
        	manageDepartmentList.add(department);
        	//遍历循环未经过经营本部筛选的实体
        	for(int i=0;i<entityList.size();i++ ){
        		CodAuditEntity entity = entityList.get(i);
        		//调用综合接口,根据到达部门的code, 查询经营本部
        		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity =
        				orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(entity.getDestOrgCode(),bizTypes);
        		if(StringUtils.isNotEmpty(orgAdministrativeInfoEntity.getName())){
        		//给实体类赋值经营本部,显示字段所用
        			entity.setManageDepartment(orgAdministrativeInfoEntity.getName());
        		}
        		//判断筛选经营本部的实体,遍历
        		for(String string:manageDepartmentList){
        			//调用综合接口,查询对应的经营本部
        			//前台页面上传过来的,经营本部  跟   到达部门的到达经营本部 做对比筛选
        			OrgAdministrativeInfoEntity customerOrgEntity =orgAdministrativeInfoComplexService.
        					queryOrgAdministrativeInfoByCode(string,bizTypes);
        			//筛选,若是跟前台的不一样  则移出该实体
        			if(!entity.getManageDepartment().equals(customerOrgEntity.getName())){
        				entityList.remove(entity);
        				i--;
        			}
        		}
        	}
        }else{//如果默认没有经营本部,则直接赋值,无须筛选
        	for(int i=0;i<entityList.size();i++ ){
    		CodAuditEntity entity = entityList.get(i);
    		//调用综合接口 查询经营本部
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity =
    				orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(entity.getDestOrgCode(),bizTypes);
    		if(StringUtils.isNotEmpty(orgAdministrativeInfoEntity.getName())){
    		//给实体类赋值经营本部,显示字段所用
    				entity.setManageDepartment(orgAdministrativeInfoEntity.getName());
    			}
        	}
        	
        }*/
		if(CollectionUtils.isNotEmpty(entityList)){
		
	        /**
	         * 增加显示字段:到达部门收银员姓名Cashier,到达部门电话TEL,到付金额FC,交易流水号
	         */ 
			//定义一个集合,取出实体类集合里的运单号
			List<String>  waybillNos = new ArrayList<String>();
	        for(CodAuditEntity  codAuditEntity :  entityList){
	        	EmployeeDtoToSTL employeeDto = new EmployeeDtoToSTL(); 
	        	/**
	        	 * 调用综合接口,查出到达部门电话TEL,到付金额FC
	        	 * */
	        	employeeDto = employeeService.queryCashierNameAndDepTelephone(codAuditEntity.getDestOrgCode());
	        	
	        	if(CollectionUtils.isNotEmpty(employeeDto.getCashierList())&& null!=employeeDto.getCashierList().get(0)){
		        	if(StringUtils.isNotEmpty(employeeDto.getCashierList().get(0).getEmpName())){
		        	//到达部门收银员姓名Cashier
		        		codAuditEntity.setDestCashierName(employeeDto.getCashierList().get(0).getEmpName());
		        	}
		        	//到达部门电话TEL
		        	if(StringUtils.isNotEmpty(employeeDto.getDepTelephone())){
		        		codAuditEntity.setDestMobilePhone(employeeDto.getDepTelephone());
		        	}
		        }
	        	//往集合里添加单号
	        	waybillNos.add(codAuditEntity.getWaybillNo());
	        }
	        /**
	         * 添加到付金额,to_pay_amount
	         */
	        CodAuditDto   codAuditDto = new CodAuditDto();
	        codAuditDto.setWaybillNos(waybillNos);
	        List<CodAuditEntity> codAuditEntityList = codAuditDao.selectCodFCAmountByWaybillNos(codAuditDto);
	        //遍历还没有赋值的集合
	        for(CodAuditEntity codAuditEntity : entityList){
	        	//遍历查询方法查出来的集合
	        	for(CodAuditEntity  entity :codAuditEntityList){
	        		//通过单号进行关联,拼接
	        		if(codAuditEntity.getWaybillNo().equals(entity.getWaybillNo())){
	        			codAuditEntity.setCodFCAmount(entity.getCodFCAmount());
	        		  }
	        	   }
	            }
	        }
       return entityList;
    }

    /**
     * 查询所有导出
     * @param dto
     * @return
     */

    @Override
    public List<CodAuditEntity> queryCodAuditByCondition(CodAuditDto dto) {

        return codAuditDao.selectByCondition(dto);
    }

    /**
     * 根据条件查询汇总
     * @param dto
     * @return
     */
    @Override
    public CodAuditDto queryTotalByConditon(CodAuditDto dto) {
    	if(CollectionUtils.isNotEmpty(dto.getBankList())){
    		List<String> bankNameList = new ArrayList<String>();
    		List<String> bankList=dto.getBankList();
    		for(String  bankCode : bankList){
    			//调用综合接口查询code,对应的name
    			BankEntity bankEntity = bankService.queryBankInfoByCode(bankCode);
    			//添加到dto的nameList里
    			bankNameList.add(bankEntity.getName());
    		}
    		dto.setBankNameList(bankNameList);
    	}
    	
    	
        return codAuditDao.queryTotalCount(dto);
    }
    @Transactional
    @Override
    public int updateCodAuditStatusBath(CodAuditDto dto) {
        LOGGER.debug("根据条件更新代收货款状态开始");
        int updateCount = 0;
        //根据条件更新
        if(dto != null &&
                CollectionUtils.isNotEmpty(dto.getWaybillNos())){
            CodAuditDto waybillNos = new CodAuditDto();
            waybillNos.setWaybillNos(dto.getWaybillNos());
            List<CodAuditEntity> list= codAuditDao.selectByCondition(waybillNos);
            //获取所有状态
            Map<String,String> allStatus = this.getAllStauts();
            List<String> status = null;
            List<CodAuditLogEntity> logList = new ArrayList<CodAuditLogEntity>();
            //String  auditSuggestion="";
            //String  fileuploadAdress="";
            /**
             * @author 310970
             * 新增的审核条件
             * */
            //如果操作为资金部审核，则所有数据为资金部待审核
            if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT.equals(dto.getLockStatus())){
            	
            	allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
            	/***
            	 * 如果操作为资金部审核,则开始上传附件.
            	 * 若没没有附件,则直接进入审核环节
            	 * 若是有,则上传附件,然后进入审核环节
            	 * 若在此时上传文件失败了,就不让它继续进行审核
            	 * 若是文件上传成功了,但是在修改审核状态是发生了异常,
            	 * 	  则把已经上传的附件清除
            	 * 满足6个筛选条件任意一条的资金安全控制组对数据进行审核后。需对资金安全控制组审核的数据再做判定。需求判定条件如下：
  				 *	1.1判定退款金额是否≥3000元，若判定为 “是”，则由资金复核小组进行审核。资金复核小组审核后，
  				 *		数据流到代收货款支付界面进行退款。
				 *	1.2判定退款金额是否≥3000.00元，若判断为“否”，则需进一步进行判断，判断条件如下：
   				 *	2.1判断付款方式是否为“现金”，若判断结果为“是”，则直接直接流至代收货款支付界面进行退款。
   				 *	2.2判断付款方式是否为“现金”，若判定结果为“否”，则流至资金复核小组进行审核，
   				 *		资金复核小组审核后再流至代收货款支付界面进行退款。
            	 */
            	//上传附件
               /* CodAuditSugEntity codAuditSugEntity = new CodAuditSugEntity();
                if(dto.getUploadFile()!=null){
                	try {
                		fileuploadAdress = this.fileUpload(dto,fileuploadAdress);
					} catch (Exception e) {
						throw new RuntimeException("保存文件失败");
					}
                }*/
                /*if(StringUtils.isNotEmpty(dto.getCodAuditSuggestion())){
                	//往表中插入数据审核的意见
                	auditSuggestion=dto.getCodAuditSuggestion();
                }
                if(StringUtils.isNotBlank(fileuploadAdress)||StringUtils.isNotBlank(auditSuggestion)){
                		for(String  waybillNo : dto.getWaybillNos()){
                			codAuditSugEntity.setWaybillNo(waybillNo);
                			codAuditSugEntity.setAuditFundsug(auditSuggestion);
                			codAuditSugEntity.setAuditReviewAuditsug("");
                			codAuditSugEntity.setActive(FossConstants.ACTIVE);
                			codAuditSugEntity.setFunAttachment(fileuploadAdress);
                			codAuditSugEntity.setReviewauditAttachment("");
                			codAuditSugEntity.setCreateUser(dto.getCreateUser());
                			codAuditSugEntity.setModifyUser("");
                			codAuditSugEntity.setCreateTime(dto.getCreateTime());
                			codAuditSugEntity.setModify_time(null);
                			codAuditDao.insert(codAuditSugEntity);
                		}
                }*/
            	//定义一个审核DTO，用来存放符合条件的审核实体
            	CodAuditDto codAuditDto = new CodAuditDto();
            	//定义一个集合，用来存放复合条件的单号
            	List<String> waybillNosList = new ArrayList<String>();
            	//定义一个审核的Entity集合,存放复合条件的实体
            	List<CodAuditEntity> codAuditList = new  ArrayList<CodAuditEntity>();
            	//定义一个金额为3000
            	BigDecimal amount = new BigDecimal(SettlementReportNumber.THREE_THOUSAND);
            	/**
            	 * 判定退款金额是否≥3000元，若判定为 “是”，则由资金复核小组进行审核。
            	 * 判断付款方式是否为“现金”，若判定结果为“否”，则流至资金复核小组进行审核
            	 * 便利循环找出符合条件的单号。
            	 * 判断条件（金额大于等于3000元   ||金额小于3000元并且付款方式不为现金的）
            	 * 符合条件的数据走原来的流程
           		 * 经过筛选后的数据则为金额不大于3000且付款方式为现金的，此时直接到支付界面
           		 */
            	//for(CodAuditEntity  codAuditEntity : list){
            		for(int i=0;i<list.size();i++){
            			CodAuditEntity codAuditEntity = list.get(i);
            		if(amount.compareTo(codAuditEntity.getCodAmount())<=0||
            				(amount.compareTo(codAuditEntity.getCodAmount())>0 &&
            				 !"CH".equals(codAuditEntity.getPaymentType()))){
            			//添加实体
            			codAuditList.add(codAuditEntity);
            			//添加单号
	            		waybillNosList.add(codAuditEntity.getWaybillNo());
	            		//清除符合条件的实体   
	            		list.remove(codAuditEntity);
	            		//清除符合条件的单号
	            		dto.getWaybillNos().remove(codAuditEntity.getWaybillNo());
	            		i--;
            		}
            	}
            	if(waybillNosList!=null&&waybillNosList.size()>0){
            		//设定单号集合
	            	codAuditDto.setWaybillNos(waybillNosList);
	            	//设定审核的锁定状态
	            	codAuditDto.setLockStatus(dto.getLockStatus());
	            	//批量更新
	            	updateCount = codAuditDao.updateAuditStatusBath(codAuditDto);
	            	buildCodAuditLogs(codAuditDto.getWaybillNos(),logList,"审核会计待审核");
	            	codAuditLogService.insertBath(logList);	
            	}else if(dto.getWaybillNos()!=null&&dto.getWaybillNos().size()>0){
	                if(!logList.isEmpty()){
	                	logList.clear();
	                }
	            	//批量更新
	                dto.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWOVER);
	            	updateCount = codAuditDao.updateAuditStatusBath(dto);
	            	buildCodAuditLogs(dto.getWaybillNos(),logList,"资金部审核通过，未经过复核组，直接到支付界面");
	            	codAuditLogService.insertBath(logList);
            	}
            	
                //TODO 更新代收货款状态
            }
            
            
            
            //如果操作为资金部锁定
            else if (SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK.equals(dto.getLockStatus())){
                allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
                //批量更新
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"资金部锁定");
                codAuditLogService.insertBath(logList);
                //TODO 更新代收货款状态
            }
            //资金部取消锁定
            else if(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDCANCELLOCK.equals(dto.getLockStatus())){
                allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
                dto.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);
                //批量更新
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"资金部取消锁定");
                codAuditLogService.insertBath(logList);
                //TODO 更新代收货款状态

            }
            
            /**
             * @218392 zhangyongxue
             * @date 2016-07-13 20:33:00
             * 取消短期冻结
             */
            else if("RSSL".equals(dto.getLockStatus())){//@218392 张永雪
            	
            	//做这个操作的前提，是这条数据只能是SSL短期锁定，其余的都不可以操作者
                allStatus.remove(SettlementDictionaryConstants.SETTLE_SHORT_LOCK);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
            	
                //批量更新
            	dto.setLockStatus("RO");
            	dto.setModifyUerName("sysJob");
            	dto.setModifyUserCode("sysJob");
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"审核会计审核");
                codAuditLogService.insertBath(logList);
            }
            
            
            //审核会计审核
            else if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWOVER.equals(dto.getLockStatus())){
                allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }

             /* //上传附件
                CodAuditSugEntity codAuditSugEntity = new CodAuditSugEntity();
                if(dto.getUploadFile()!=null){
                	try {
                		fileuploadAdress = this.fileUpload(dto,fileuploadAdress);
					} catch (Exception e) {
						throw new RuntimeException("保存文件失败");
					}
                }
                if(StringUtils.isNotEmpty(dto.getCodAuditSuggestion())){
                	//往表中插入数据审核的意见
                	auditSuggestion=dto.getCodAuditSuggestion();
                }
                if(StringUtils.isNotBlank(fileuploadAdress)||StringUtils.isNotBlank(auditSuggestion)){
                		for(String  waybillNo : dto.getWaybillNos()){
                			codAuditSugEntity.setWaybillNo(waybillNo);
                			codAuditSugEntity.setAuditFundsug("");
                			codAuditSugEntity.setAuditReviewAuditsug(auditSuggestion);
                			codAuditSugEntity.setActive(FossConstants.ACTIVE);
                			codAuditSugEntity.setFunAttachment("");
                			codAuditSugEntity.setReviewauditAttachment(fileuploadAdress);
                			codAuditSugEntity.setCreateUser(dto.getCreateUser());
                			codAuditSugEntity.setModifyUser("");
                			codAuditSugEntity.setCreateTime(dto.getCreateTime());
                			codAuditSugEntity.setModify_time(null);
                			codAuditDao.insert(codAuditSugEntity);
                		}
                }*/
                //批量更新
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"审核会计审核");
                codAuditLogService.insertBath(logList);
                //TODO 更新代收货款状态
            }
            //审核会计锁定
            else if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK.equals(dto.getLockStatus())){
                allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
                //批量更新
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"审核会计锁定");
                codAuditLogService.insertBath(logList);
                //TODO  更新代收货款状态
            }
            //审核会计取消锁定
            else if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWCANLELLOCK.equals(dto.getLockStatus())){
                allStatus.remove(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK);
                status= new ArrayList<String>(allStatus.keySet());
                Map<String,String> hasException = hasStatus(list,status);
                //判断是否存在异常状态
                if(!hasException.isEmpty()){
                    LOGGER.debug("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                    throw new SettlementException("单据:"+hasException.keySet()+"存在异常状态："+hasException.values());
                }
                dto.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
                //批量更新
                updateCount = codAuditDao.updateAuditStatusBath(dto);
                buildCodAuditLogs(dto.getWaybillNos(),logList,"审核会计取消锁定");
                codAuditLogService.insertBath(logList);
                //TODO 更新代收货款状态
            }

            LOGGER.debug("代收货款状态更新成功");

        }
        return updateCount;
    }
    /***
     * 上传附件,并且获取上传文件的地址
     */
    private String fileUpload(CodAuditDto dto,String fileuploadAdress) {
		File file = new File(dto.getUploadFile());
		String[] str = dto.getUploadFileFileName().split("\\.");
		String fileName = str[0]+"_"+new Date().getTime()+"."+str[1];
		
		try {
			FileUtils.copyFile(file, new File(dto.getFilePath()+"\\"+fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileuploadAdress = fileName;
		
    	return fileuploadAdress;
	}


	/**
     * 创建日志集合
     * @param waybillNos
     * @param logEntities
     * @param operatContext
     */
    public void buildCodAuditLogs(List<String> waybillNos ,
                                   List<CodAuditLogEntity> logEntities,
                                   String operatContext){
        UserEntity user =  FossUserContext.getCurrentUser();
        String userCode = user != null ?user.getUserName():"";
        String deptCode = FossUserContext.getCurrentDeptCode();
        String deptName = FossUserContext.getCurrentDeptName();
        //遍历运单号，进行封装日志信息
        if(CollectionUtils.isNotEmpty(waybillNos)){
            for(String waybillNo:waybillNos){
                CodAuditLogEntity entity = new CodAuditLogEntity();
                entity.setId(UUID.randomUUID().toString());
                entity.setWaybillNo(waybillNo);
                entity.setModifyUser(userCode);
                entity.setModifyDate(new Date());
                entity.setCreateUser(userCode);
                entity.setOperateContent(operatContext);
                entity.setOperateDeptcode(deptCode);
                entity.setOperateDeptname(deptName);
                entity.setOperateTime(new Date());
                entity.setCreateDate(new Date());
                logEntities.add(entity);
            }
        }
    }

    /**
     * 获取Excel对象
     * @param dto
     * @return
     */
    @Override
    public HSSFWorkbook codAuditExportEXCEL(CodAuditDto dto) {
        // 导出Excel
        ExcelExport export = new ExcelExport();
        List<CodAuditEntity> list = queryCodAuditByCondition(dto);
        // 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
        List<List<String>> rowList = getEexelData(list,this.getColumns());
        // 获取导出数据
        SheetData data = new SheetData();
        // 设置导出列头
        data.setRowHeads(this.getExcelHeader());
        data.setRowList(rowList);
        // 设置每次最多导出10万条数据
        HSSFWorkbook work = export.exportExcel(data,
                SettlementConstants.EXCEL_SHEET_NAME,
                SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
        return work;
    }

    /**
     * 新增审核信息
     * @param record
     * @return
     */
    @Override
    public int addCodAudit(CodAuditEntity record) {

        if(record == null){
            throw new SettlementException("传入对象不能为空");
        }else if(StringUtils.isBlank(record.getWaybillNo())){
            throw new SettlementException("运单号不能为空");
        }else if(record.getCodAmount() == null){
        	throw new SettlementException("应退金额不能为空");
        }else if(record.getComfirmTime()== null ||
                record.getSigTime() == null){
            throw new SettlementException("收银确认时间或者签收时间不能为空");
            //新增审核信息  改动了判断条件  多了一个条件复合会计组待审核状态
        }else if(!SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT.equals(record.getLockStatus())&&
        		!SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT.equals(record.getLockStatus())&&
        		!SettlementDictionaryConstants.SETTLE_SHORT_LOCK.equals(record.getLockStatus())&&
        		!SettlementDictionaryConstants.SETTLE_LONG_LOCK.equals(record.getLockStatus())){
            throw new SettlementException("插入状态不是资金部待审核状态或复合会计组待审核状态或短期冻结或不是长期冻结");
        }
        return codAuditDao.insert(record);
    }
    
    /**
     * 添加长期未退款代收货款短期冻结，长期冻结
     */
	@Override
	public int addShortOrLongLock(CodAuditEntity record) {
        if(record == null){
            throw new SettlementException("传入对象不能为空");
        }
        return codAuditDao.insert(record);
	}
    
    /**
     * @218392 zhangyongxue
     * @date 2016-07-08 11:51:00
     * 长期未退款代收货款 插入代收支付审核表中
     */
    @Override
    public int addCodAuditLock(CodAuditEntity record){
        if(record == null){
            throw new SettlementException("传入对象不能为空");
        }
    	 return codAuditDao.insert(record);
    }

	/**
	 * @author 218392 ZYX
	 * 长期未退款代收货款插入方法
	 */
	@Override
	public int addNonRunfundCodLock(CodAuditEntity record) {
        if(record == null){
            throw new SettlementException("传入对象不能为空");
        }
		return codAuditDao.insert(record);
		
	}
    
    /**
     * 作废审核代收货款信息
     * @param waybillNo
     * @return
     */
    @Transactional
    @Override
    public int cancelCodAudit(String waybillNo) {
        if(StringUtils.isBlank(waybillNo)){
            throw new SettlementException("传入的单号为空");
        }
        CodAuditEntity record = new CodAuditEntity();
        record.setWaybillNo(waybillNo);
        record.setActive(FossConstants.NO);
        UserEntity entity =  FossUserContext.getCurrentUser();
        if(entity != null){
            record.setModifyUser(entity.getEmpCode());
        }
        return codAuditDao.updateByWaybillNo(record);
    }

    /**
     * 获取Excel内容
     * @param list
     * @param colums
     * @return
     */
    private List<List<String>> getEexelData(List<CodAuditEntity> list,List<String> colums){
        List<List<String>> rowList = new ArrayList<List<String>>();
        //非空判断
        if(CollectionUtils.isNotEmpty(list)
                &&CollectionUtils.isNotEmpty(colums)){
            //循环便利
            for(CodAuditEntity entity :list){
                List<String> oneRow = new ArrayList<String>();
                for(String col :colums){
                    Field field = null;
                    if("origEqDest".equals(col)){
                        if(entity.getDestOrgCode().equals(entity.getOrigOrgCode())){
                            oneRow.add("是");
                        }else{
                            oneRow.add("否");
                        }
                    }else{
                     field = ReflectionUtils.findField(CodAuditEntity.class,col);
                    }
                    //判空
                    if(field != null){
                        //获取对应的值
                        ReflectionUtils.makeAccessible(field);
                        Object fieldValue=  ReflectionUtils.getField(field,entity);
                        if(fieldValue != null){
                            if(col.equals("hasTrack")&&"Y".equals(fieldValue.toString())){
                                fieldValue = "是";
                            }else if(col.equals("hasTrack")){
                                fieldValue = "否";

                            }else if(col.equals("lockStatus")){
                                fieldValue = getAllStauts().get(fieldValue.toString());
                            }else if(col.equals("paymentType")){
                                List<String> types=new ArrayList<String>();
                                types.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);// 付款方式
                                //获取全部待转换缓存
                                Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
                                Map paymentCache = map.get(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
                                fieldValue =paymentCache.get(fieldValue.toString());
                            }else if (fieldValue instanceof  Date){
                                fieldValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fieldValue);
                            }
                            oneRow.add(fieldValue.toString());
                        }else{
                            oneRow.add("");
                        }

                    }
                }
                rowList.add(oneRow);

            }

        }
        return rowList;
    }

    /**
     * 获取Excel 的表头
     * @return
     */
    private String[] getExcelHeader(){
        String[] header = { "运单号"
        ,"应退金额"
        ,"到达部门名称"
        ,"出发到达部门相同"
        ,"签收-开单时长"
        ,"代收更改金额"
        ,"有无货物轨迹"
        ,"付款方式"
        ,"锁定状态"
        ,"开单时间"
        ,"收银确认时间"
        ,"签收时间"
        ,"出发部门名称"
        ,"收款人"
        ,"手机号码"
        ,"银行账号"};
        return header;
    }

    /**
     * 获取所有的列
     * @return
     */
    private List<String> getColumns(){
        List<String> colums = new ArrayList<String>();
        colums.add("waybillNo");
        colums.add("codAmount");
        colums.add("destOrgName");
        colums.add("origEqDest");
        colums.add("billSignDiffer");
        colums.add("changeAmount");
        colums.add("hasTrack");
        colums.add("paymentType");
        colums.add("lockStatus");
        colums.add("billTime");
        colums.add("comfirmTime");
        colums.add("sigTime");
        colums.add("origOrgName");
        colums.add("customerName");
        colums.add("mobileNo");
        colums.add("accountNo");
        return colums;
    }

    public void setCodAuditDao(ICodAuditDao codAuditDao) {
        this.codAuditDao = codAuditDao;
    }
    public void setCodAuditLogService(ICodAuditLogService codAuditLogService) {
        this.codAuditLogService = codAuditLogService;
    }
    public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	@Override
	public List<CodAuditEntity> queryCodAuditByWaybillNo(String waybillNo) {
		
		 return codAuditDao.queryCodAuditByWaybillNo(waybillNo);
	}

	@Override
	public List<CodAuditEntity> queryCodDtoByWaybillNo(String waybillNo) {
		
		 return codAuditDao.queryCodDtoByWaybillNo(waybillNo);
	}

	@Override
	public List<CodAuditEntity> queryCodChangeTime(CodAuditDto waybillNo) {
		
		return (List<CodAuditEntity>) codAuditDao.queryCodChangeTime(waybillNo);
	}


}
