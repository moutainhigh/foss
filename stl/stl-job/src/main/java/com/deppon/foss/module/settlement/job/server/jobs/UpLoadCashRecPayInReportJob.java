package com.deppon.foss.module.settlement.job.server.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.finincomecash.CashIncomeInfo;
import com.deppon.esb.inteface.domain.finincomecash.UploadCashIncomeRequest;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 上传现金缴款收入缴款报表
 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
 * HAS CHECKED
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午2:38:44
 */
public class UpLoadCashRecPayInReportJob extends GridJob {

	/**
	 * 上传现金缴款收入报表logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(UpLoadCashRecPayInReportJob.class);

	/**
	 * 设置默认的的执行时间
	 */
	private static final int DATE_NUM = -1;


	/**
	 * 服务场景号
	 */
	private static final String SERVICE_CODE = "ESB_FOSS2ESB_UPLOAD_CASHINCOMERPT";

	/**
	 * 版本号
	 */
	private static final String VERSION = "1.0";

	/**
	 * 描叙
	 */
	private static final String BUSINESS_DESC = "上传部门前一天应缴款";

	/**
	 * 
	 * 每日定时上传前一天部门的现金、非现金缴款金额
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-19 上午10:29:08
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.info("执行上传前一天部门的现金、非现金缴款金额：begin......");
		try {
			// 获取当前时间、设置上传应缴开始日期、设置上传应缴结束日期
			Date currentDate = DateUtils.getStartDatetime(new Date());
			Date beginDate = DateUtils.addDayToDate(currentDate, DATE_NUM);
			Date endDate = currentDate;
			Date lastDate = null;
			
			// 定时任务时间戳服务 -记录上次job执行的时间
			 
			IJOBTimestampService jobTimestampService=getBean("jobTimestampService",IJOBTimestampService.class);
			
			// 记录下Job上一次执行的时间，用于（任何原因导致job未能执行）
			lastDate = jobTimestampService.getJOBTimestamp(SERVICE_CODE);
			LOGGER.info("执行上传前一天部门的现金、非现金缴款金额：begin job 上次执行时间......"+new SimpleDateFormat("yyyy-dd-mm").format(lastDate));
			//如果没有执行，新增当前时间为执行时间			
			if (null == lastDate) {
				jobTimestampService.addJOBTimestamp(SERVICE_CODE, currentDate,BUSINESS_DESC);
				getTimingCashIncome(beginDate, endDate);
			} else {
				// 先判断有几天没有执行
				// 获取上一次执行的时间和现在时间相差几天没有执行，然后手动执行几次
				Long invalD=DateUtils.getTimeDiff(lastDate, currentDate);
				LOGGER.info("执行上传前一天部门的现金、非现金缴款金额：上次执行距今天......"+ invalD);
				
				if (invalD > 1) {
					//间隔天数、上次执行的时间作为开始时间、结束时间、job执行次数					
					Long datediff=invalD;
					Date bDate=lastDate;
					Date eDate;
					Long datediff1=datediff;
					for (Long i = 0l; i <datediff; i++) {
						eDate=DateUtils.addDayToDate(bDate,1);
						getTimingCashIncome(bDate, eDate);
						LOGGER.info(String.format("本次JOB执行时间:%s", DateUtils.convert(eDate)));
						jobTimestampService.updateJOBTimestamp(SERVICE_CODE,eDate);
						bDate=eDate;
						LOGGER.info("执行上传前一天部门的现金应缴款总金额信息：上次执行距今天......"+datediff1--+"天(次)"+"JoB执行传入,开始日期"+
						DateUtils.convert(bDate)+"结束日期"+ DateUtils.convert(eDate));
					}
				} else {
					getTimingCashIncome(beginDate, endDate);
					jobTimestampService.updateJOBTimestamp(SERVICE_CODE,endDate);
				}
			}
			LOGGER.info("执行上传前一天部门的现金、非现金缴款金额：end......");
		} catch (Exception e) {
			LOGGER.error("执行上传前一天部门的现金、非现金缴款金额发生异常，异常信息：" + e.getMessage());
			throw new JobExecutionException("执行上传前一天部门的现金、非现金缴款金额信息发生异常，异常信息："+ e.getMessage(), e);
		}
	}

	/**
	 * 获取前一天的营业现金收入
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-5-9 下午4:20:31
	 * @param beginDate
	 * @param endDate
	 * @throws JobExecutionException
	 */
	@Transactional
	private void getTimingCashIncome(Date beginDate, Date endDate)
			throws Exception {
		// 现金收入缴款报表管理service
		IReportCashRecPayInService reportCashRecPayInService = getBean("reportCashRecPayInService",IReportCashRecPayInService.class);
		
		List<CashCollectionRptEntity> cashList = reportCashRecPayInService.queryUploadCashAllAmount(beginDate, endDate);

		// 如果现金收入报表信息不为空，调用财务自助接口，进行发送
		if (CollectionUtils.isNotEmpty(cashList)) {
			setCashIncomeBuildMessage(beginDate,cashList);
		}
	}

	
	
	/**
	 * 发送所有网点前一天的营业现金收入给财务自助
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * @author 095793-foss-LiQin
	 * @date 2013-5-9 下午4:28:53
	 * @param beginDate
	 * @param cashList
	 * @throws ESBException 
	 */
	private void setCashIncomeBuildMessage(Date beginDate,List <CashCollectionRptEntity> cashList) throws ESBException{
		// 构建现金收入缴款报表对象
		UploadCashIncomeRequest upCashIRequest = new UploadCashIncomeRequest();
		upCashIRequest.setPaymentDate(beginDate);
		for (CashCollectionRptEntity entity : cashList) {
			sendUploadReportCashRecPayInMessage(entity, upCashIRequest);
		}
		
		// 构建消息头
		AccessHeader header = buildHeader(new Date());
		// 发送消息
		ESBJMSAccessor.asynReqeust(header, upCashIRequest);
	}
	
	
	
	/**
	 * （构建发送到财务自助）现金收入(缴款)报表信息
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * @author 095793-foss-LiQin
	 * @date 2012-12-13 下午6:53:07
	 */
	private void sendUploadReportCashRecPayInMessage(
			CashCollectionRptEntity cashEntity,
			UploadCashIncomeRequest upCashIRequest) {
		CashIncomeInfo cashIncomeInfo = new CashIncomeInfo();
		// 财务自助标杆编码
		cashIncomeInfo.setDeptCode(cashEntity.getOrgCode());
		// 现金
		cashIncomeInfo.setPaymentAmount(cashEntity.getClerksAmount());
		// 非现金
		cashIncomeInfo.setPaymentCashAmount(cashEntity.getUnclerksAmount());
		// 日期
		cashIncomeInfo.setPaymentDate(upCashIRequest.getPaymentDate());
		// 流水号
		cashIncomeInfo.setSerialNum(UUIDUtils.getUUID());
		upCashIRequest.getCashIncomeInfo().add(cashIncomeInfo);
	}

	/**
	 * 接口头消息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-24 下午8:40:49
	 */
	private AccessHeader buildHeader(Date payInDate) {

		AccessHeader header = new AccessHeader();
		// 发送流水号
		header.setBusinessId(DateUtils.convert(payInDate));
		// 服务场景
		header.setEsbServiceCode(SERVICE_CODE);
		// 版本号
		header.setVersion(VERSION);
		// 场景描叙
		header.setBusinessDesc1(BUSINESS_DESC);
		return header;

	}
}
