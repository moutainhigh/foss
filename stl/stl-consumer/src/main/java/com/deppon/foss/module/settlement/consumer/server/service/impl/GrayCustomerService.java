package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ecs.inteface.domain.graycustomer.GrayCustomerRequest;
import com.deppon.ecs.inteface.domain.graycustomer.SyncUpdateGrayCustomerRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IGrayCustomerDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.GrayCustomerDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 更新灰名单
 * 
 *  @author 269044-zhurongrong
 *  @date 2016-04-12
 */
public class GrayCustomerService implements IGrayCustomerService {
	
	/**
	 * 更新灰名单记录日志
	 */
	public static final Logger logger = LogManager.getLogger(GrayCustomerService.class); 
	
	/**
	 * 服务编码
	 */
	private static final String ESC_GET_GRAYCUSTOEMR = "ESB_FOSS2ESB_GET_GREYLIST";
	
	/**
	 * 版本编号
	 */
	private static final String version = "1.0";
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerDao grayCustomerDao;
	
	/**
	 * 客户信用额度服务
	 */
	private ICreditCustomerService creditCustomerService;
	
	/**
	 * 客户合同信息，获取月结客户的信用额度
	 */
	private ICusBargainService cusBargainService;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	
	/**
	 * 调用悟空接口开关
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 根据客户编码查询灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	@Override
	public GrayCustomerEntity queryGrayCustomerByCustomerCode(String customerCode) {
		//根据客户编码查询灰名单信息
		GrayCustomerEntity grayCustomer = grayCustomerDao.queryGrayCustomerByCustomerCode(customerCode);
		return grayCustomer;
	}
	
	/**
	 * 根据客户编码集合查询灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 */
	@Override
	public List<GrayCustomerEntity> queryGrayCustomerListByCustomerCodes(
			List<String> customerCodes) {
		List<GrayCustomerEntity> grayCustomerList = grayCustomerDao.queryGrayCustomerListByCustomerCodes(customerCodes);
		return grayCustomerList;
	}
	
	/**
	 * 修改灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-21
	 */
	@Override
	@Transactional
	public void updateGrayCustomerToECS(List<String> customerCodes) {
		logger.info("更新灰名单信息开始，客户编码个数：" + customerCodes.size());
		//数据库操作结果
		int i = 0;
		//存放所有待传送的灰名单集合
		List<GrayCustomerRequest> grayCustomerRequests = new ArrayList<GrayCustomerRequest>();
		//判断对象是否为空
		if(CollectionUtils.isNotEmpty(customerCodes)) {
			try{
				//循环校验并封装对象
				for(String customerCode : customerCodes) {
					//创建灰名单请求对象
					GrayCustomerRequest request = new GrayCustomerRequest();
					//调用判断方法，验证该客户是进入还是移出灰名单
					GrayCustomerDto grayCustomerDto = IsGrayCustomer(customerCode);
					//判断dto是否为空
					if(StringUtils.isNotEmpty(grayCustomerDto.getStatus())) {
						if(SettlementDictionaryConstants.GRAYCUSTOMER_IN.equals(grayCustomerDto.getStatus())) {
							//添加到数据库
							i = addGrayCustomer(grayCustomerDto);
							//添加成功
							if(i > 0) {
								//设置客户编码
								request.setCustomerCode(customerCode);
								//设置状态值
								request.setStatus(grayCustomerDto.getStatus());
								//设置最久欠款日期
								request.setMinDate(grayCustomerDto.getMinDate());
								//设置最大欠款天数
								request.setOverDays(new BigDecimal(grayCustomerDto.getOverDays()));
								//添加到list中
								grayCustomerRequests.add(request);
							} else {
								//添加失败
								logger.info("添加灰名单客户失败，客户编码：" + grayCustomerDto.getCustomerCode());
								continue;
							}
						} else if (SettlementDictionaryConstants.GRAYCUSTOMER_OUT.equals(grayCustomerDto.getStatus())) {
							//作废该客户灰名单数据
							i = deleteGrayCustomerByCustomerCode(grayCustomerDto);
							//作废成功
							if(i > 0) {
								//设置客户编码
								request.setCustomerCode(customerCode);
								//设置状态值
								request.setStatus(grayCustomerDto.getStatus());
								//设置最久欠款日期
								request.setMinDate(grayCustomerDto.getMinDate());
								//设置最大欠款天数
								request.setOverDays(new BigDecimal(grayCustomerDto.getOverDays()));
								//添加到list中
								grayCustomerRequests.add(request);
							} else {
								//作废失败
								logger.info("删除灰名单客户失败，客户编码：" + grayCustomerDto.getCustomerCode());
								continue;
							}
						} else {
							logger.info("灰名单状态值有误，客户编码：" + grayCustomerDto.getCustomerCode() + 
									",状态值：" + grayCustomerDto.getStatus());
							continue;
						}	
					} else {
						logger.info("该客户没有月结合同，客户编码：" + grayCustomerDto.getCustomerCode());
						continue;
					}	
				}
			} catch (Exception e) {
				//打印异常
				logger.info("灰名单操作数据库异常！" + e.getMessage());
				//throw new SettlementException("灰名单操作数据库异常:" + e.getMessage());
			}	 
		}
		//将修改的数据传给ECS
		sendMessageToECS(grayCustomerRequests, SettlementDictionaryConstants.ISALLDELETE_N);
		logger.info("更新灰名单信息结束。");
		
	}
	
	/**
	 * 分页查询灰名单进行推送
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-28
	 */
	@Override
	public void SyncAllGrayCustomersToECS() {
		//判断是否全部删除历史数据--将更新与夜晚同步的进行区分
		String isAllDelete = " ";
		//查询所有的灰名单信息的总条数
		int totalCount = grayCustomerDao.countQueryGrayCustomer();
		//定义每次传输的条数
		int limit = SettlementReportNumber.ONE_HUNDRED;
		//定义起始传输的值
		int start = 0;
		//定义页数
		int i = 0;
		try{
			//通过分页查询，循环将灰名单信息放入MQ队列中
			for(i = 1; i <= totalCount/limit + 1; i++ ) {
				//除了首次是全部删除，其他次数都不删除
				isAllDelete = SettlementDictionaryConstants.ISALLDELETE_N;
				//首次发送请求的时候，要通知数据全部删除
				if(i == 1) {
					isAllDelete = SettlementDictionaryConstants.ISALLDELETE_Y;
				}
				//创建请求的集合
				List<GrayCustomerRequest> list = new ArrayList<GrayCustomerRequest>();
				//分页进行查询,参数：每页的起始条数，每页显示的条数
				List<GrayCustomerEntity> grayCustomers = grayCustomerDao.queryGrayCustomerList(start, limit);
				//判空
				if(CollectionUtils.isNotEmpty(grayCustomers)) {
					//循环处理对象
					for(GrayCustomerEntity grayCustomerEntity: grayCustomers) {
						//创建对象
						GrayCustomerRequest request = new GrayCustomerRequest();
						//封装客户编码
						request.setCustomerCode(grayCustomerEntity.getCustomerCode());
						//封装状态值
						request.setStatus(SettlementDictionaryConstants.GRAYCUSTOMER_IN);
						//封装最大欠款天数
						request.setOverDays(new BigDecimal(grayCustomerEntity.getOverDays()));
						//封装最久欠款日期
						request.setMinDate(grayCustomerEntity.getMinDate());
						//添加到请求的集合中
						list.add(request);
					}
				} else {
					break;
				}
				//调用 发送消息到ECS的方法
				sendMessageToECS(list, isAllDelete);
				//每发送一页之后，其实页数需要变化
				start = start + limit;
			}
		} catch (Exception e) {
			//抛出异常
			throw new SettlementException("灰名单job分页推送数据到ecs异常：" + e.getMessage());
		}
		
	}

	/**
	 * 发生核销或者反核销的操作的时候，需判断客户是否还应该在灰名单中
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	private GrayCustomerDto IsGrayCustomer(String customerCode) {
		//创建对象
		GrayCustomerDto grayCustomerDto = new GrayCustomerDto();
		
		//客户应收单信息
		CreditCustomerDto customerDebitVo = creditCustomerService.queryDebitCustomerInfo(customerCode);
		if(customerDebitVo == null ){
			customerDebitVo = new CreditCustomerDto();
		}
		//客户月结合同信息
		CusBargainEntity  cubaEntity=cusBargainService.queryCusBargainByCustCode(customerCode);
		//客户应收单信息以及客户月结合同信息非空
		if(cubaEntity !=null && cubaEntity.getDebtDays() > 0){
			//当前时间
            Date currentDate = new Date();
            //判断应收单中的最久欠款时间是否为空
            if(customerDebitVo.getMinDebitDate() == null){
                customerDebitVo.setMinDebitDate(currentDate);
            }
            //判断相差天数
            long differDays = DateUtils.getTimeDiff( customerDebitVo.getMinDebitDate(),currentDate);
            //相差天数大于合同天数
            if(cubaEntity.getDebtDays() < differDays){
            	//进入灰名单
            	grayCustomerDto.setStatus(SettlementDictionaryConstants.GRAYCUSTOMER_IN);
            } else {
            	//移除灰名单
            	grayCustomerDto.setStatus(SettlementDictionaryConstants.GRAYCUSTOMER_OUT);      
            }
            //设置客户编码
            grayCustomerDto.setCustomerCode(customerCode);
            //设置最久欠款业务日期
            grayCustomerDto.setMinDate(customerDebitVo.getMinDebitDate());
            //设置最大欠款天数
            //将长整型转化成整型
            grayCustomerDto.setOverDays(cubaEntity.getDebtDays());
		}
		return grayCustomerDto;
	}

	/**
	 * 根据客户编码删除灰名单
	 *
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	private int deleteGrayCustomerByCustomerCode(GrayCustomerDto grayCustomerDto) {
		//调用dao层
		int i = grayCustomerDao.deleteGrayCustomerByCustomerCode(grayCustomerDto);
		return i;
	}

	/**
	 * 添加灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	private int addGrayCustomer(GrayCustomerDto grayCustomerDto) {
		//先判断该客户是否已经存在灰名单中
		GrayCustomerEntity grayCustomerEntity = grayCustomerService
				.queryGrayCustomerByCustomerCode(grayCustomerDto.getCustomerCode());
		//不为空
		if(grayCustomerEntity != null) {
			//作废之前的
			deleteGrayCustomerByCustomerCode(grayCustomerDto);
		}
		//创建灰名单实体
		GrayCustomerEntity entity = new GrayCustomerEntity();
		//设置id
		entity.setId(UUIDUtils.getUUID());
		//设置客户编码
		entity.setCustomerCode(grayCustomerDto.getCustomerCode());
		//设置最久欠款日期
		entity.setMinDate(grayCustomerDto.getMinDate());
		//设置最大欠款天数
		entity.setOverDays(grayCustomerDto.getOverDays());
		//设置是否有效
		entity.setActive(FossConstants.YES);
		//调用dao层方法插入数据库中
		int i = grayCustomerDao.addGrayCustomer(entity);
		return i;
	}
		
	/**
	 * 发送消息到ECS
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-21
	 * @param grayCustomerRequestList
	 */	
	private void sendMessageToECS(List<GrayCustomerRequest> grayCustomers, String isAllDelete) {
		try{
			//判空
			if(CollectionUtils.isNotEmpty(grayCustomers)) {
				// 设置服务编码
				AccessHeader accessHeader = new AccessHeader();
				//设置唯一标示
				accessHeader.setBusinessId(UUIDUtils.getUUID());
				//设置服务编码
				accessHeader.setEsbServiceCode(ESC_GET_GRAYCUSTOEMR);
				//设置版本号
				accessHeader.setVersion(version);
				accessHeader.setBusinessDesc1("同步灰名单");
				
				SyncUpdateGrayCustomerRequest request = new SyncUpdateGrayCustomerRequest();
				//将请求过来的值，封装到request中
				for(GrayCustomerRequest grayCustomerRequest: grayCustomers) {
					//添加对象
					request.getGrayCustomerRequestList().add(grayCustomerRequest);
				}
				//设置isAllDelete的值
				request.setIsAllDelete(isAllDelete);
				
				/*
				 * 添加条件，结算代码在悟空系统之前上线，对调悟空系统的接口，添加开关 2016-07-20
				 * true:发送请求  false：不发送
				 */
				boolean flag = configurationParamsService.queryStlSwitch4Ecs();
				
				if (flag) {
					//发送消息
					logger.info("syncGrayCustomerToECS:send info start.............");
					ESBJMSAccessor.asynReqeust(accessHeader, request);
					logger.info("syncGrayCustomerToECS:send info end.............");
				}	
			}
		} catch (Exception e) {
			throw new SettlementException("发送灰名单消息到ECS失败:" + e.getMessage());		
		}
	}


	public void setGrayCustomerDao(IGrayCustomerDao grayCustomerDao) {
		this.grayCustomerDao = grayCustomerDao;
	}
    
    public void setCreditCustomerService(
			ICreditCustomerService creditCustomerService) {
		this.creditCustomerService = creditCustomerService;
	}
    
    /**
	 * @param cusBargainService the cusBargainService to set
	 */
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
}
