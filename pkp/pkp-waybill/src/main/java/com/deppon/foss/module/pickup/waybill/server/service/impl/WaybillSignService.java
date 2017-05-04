package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillSignService;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;

@Transactional
@Service
public class WaybillSignService implements IWaybillSignService{
	 
	private static final int NUMBER_900 = 0;
	Log logger = LogFactory.getLog(WaybillSignService.class);
	/**
	 * 运单持久层
	 */
	private IWaybillDao waybillDao;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	@Override
	public List<WaybillSignDetailVo> queryWayBillSignDetail(
			WaybillSignDetailQueryVo waybillSignDetailQueryVo,int start,int limit,boolean flag) {
		if(waybillSignDetailQueryVo != null){
			logger.info("进入请求 查询数据信息 queryWayBillSignDetail ");
			if(waybillSignDetailQueryVo.getStartTime() == null || waybillSignDetailQueryVo.getEndTime() == null){
				throw new BusinessException("开始时间和结束时间均不能为空 ");
			}
			if(StringUtils.isEmpty(waybillSignDetailQueryVo.getDeliveryCustomerCode())){
				throw new BusinessException("客户编码不能为空");
			}
			return waybillDao.queryWayBillSignDetail(waybillSignDetailQueryVo,start,limit,flag);
		}else{
			return  null ;
		}
	}
	
	@Override
	public int countQueryWayBillSignDetail(
			WaybillSignDetailQueryVo waybillSignDetailQueryVo) {
		if(waybillSignDetailQueryVo != null){
			logger.debug("进入请求 查询记录总数 countQueryWayBillSignDetail");
			if(waybillSignDetailQueryVo.getStartTime() == null || waybillSignDetailQueryVo.getEndTime() == null){
				throw new BusinessException("开始时间和结束时间均不能为空 ");
			}
			if(StringUtils.isEmpty(waybillSignDetailQueryVo.getDeliveryCustomerCode())){
				throw new BusinessException("客户编码不能为空");
			}
			return waybillDao.countQueryWayBillSignDetail(waybillSignDetailQueryVo);
		}else{
			return  0 ;
		}
	}

	@Override
	public int queryWaybillInvalid(List<String> waybillNumList) {
		logger.debug("进入请求 查询 作废的票数  queryWaybillInvalid");
		if(waybillNumList != null && waybillNumList.size() > 0){
			List<String> waybillNoTmp = new ArrayList<String>() ;
			int size = waybillNumList.size();
			logger.info("根据运单号获取作废的票数 传入的参数 waybillNumList 的数据条数为： "+size) ;
			int resultInt = 0 ;
			if(size >= NumberConstants.NUMBER_1000){
				int pageCount = (size-1)/NumberConstants.NUMBER_900 +1 ;
				logger.info("根据运单号获取作废的票数 进行分页查询  pageCount："+pageCount) ;
				for(int i=0 ; i < pageCount ; i++ ){
					if(i== pageCount-1){
						waybillNoTmp = waybillNumList.subList(i * NumberConstants.NUMBER_900 , size);
					}else {
						waybillNoTmp = waybillNumList.subList(i * NumberConstants.NUMBER_900 , (i+1)*NumberConstants.NUMBER_900 );
					}
					resultInt += waybillDao.queryWaybillInvalid(waybillNoTmp) ;
				}
			}else {
				resultInt = waybillDao.queryWaybillInvalid(waybillNumList) ;
			}
			logger.info("根据运单号获取作废的票数 查询返回最终结果： "+resultInt) ;
			return resultInt ;
		}
		logger.info("传入的参数 waybillNumList 为 空 ") ;
		return 0;
	}

	@Override
	public int queryWaybillBack(List<String> waybillNumList) {
		logger.debug("进入请求 查询 退回的票数  queryWaybillBack");
		if(waybillNumList != null && waybillNumList.size() > 0){
			List<String> waybillNoTmp = new ArrayList<String>() ;
			int size = waybillNumList.size();
			logger.info("根据运单号获取退回的票数 传入的参数 waybillNumList的数据总条数为： "+size) ;
			int resultInt = 0 ;
			if(size >= NumberConstants.NUMBER_1000){
				int pageCount = (size-1)/ NUMBER_900 +1 ;
				logger.info("根据运单号获取退回的票数 进行分页查询  pageCount："+pageCount) ;
				for(int i=0 ; i < pageCount ; i++ ){
					if(i== pageCount-1){
						waybillNoTmp = waybillNumList.subList(i * NUMBER_900 , size);
					}else {
						waybillNoTmp = waybillNumList.subList(i * NUMBER_900 , (i+1) * NUMBER_900 );
					}//if end
					resultInt += waybillDao.queryWaybillBack(waybillNoTmp) ;
				}//for end
			}else {
				resultInt = waybillDao.queryWaybillBack(waybillNumList) ;
			}
			return resultInt ;
		}
		logger.info("传入的参数 waybillNumList 为 空 ") ;
		return 0;
	}

	

}