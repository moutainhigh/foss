package com.deppon.foss.module.transfer.agent.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ICancelHireCarTagDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IASYRentCarCubcService;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.BudgetEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CarCancelRequestDO;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CarCancelResponseDO;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlRequestEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlResponseEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalTempMarkEntity;

/**
 * 配合CUBC传递租车编码，取消临时租车标记
 * @author liping
 * 
 * @date 2017-04-06 下午23:24:06
 */

@Transactional
@Controller
public class CancelHireCarTagService implements ICancelHireCarTagService {

	//调用dao层
	private ICancelHireCarTagDao cancelHireCarTagDao=null;
	//返回json字符串
	private CarCancelResponseDO  carCancelResponseDO = new CarCancelResponseDO();
	//保存日志
	private static Logger LOGGER = LogManager.getLogger(CancelHireCarTagService.class);
	//调用取消释放租金金额服务
	private IASYRentCarCubcService aSYRentCarCubcService;
	
	//创建Map对象保存每一条要取消的临时租车编码
	private HashMap<String, Object> hireCarTagMap = null;
	//将日期转换成字符串
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
	@Transactional
	@Override
	public @ResponseBody CarCancelResponseDO aSynCancelHireCarTagService(
			@RequestBody CarCancelRequestDO carCancelRequestDO) {

        //开始记录日志
      	LOGGER.info("---配合CUBC取消临时租车标记，获取租车编码等信息---");
        //校验租车记录
		if(carCancelRequestDO==null || CollectionUtils.isEmpty(carCancelRequestDO.getSourceBillNoList())){
			carCancelResponseDO.setStatus("0");
			carCancelResponseDO.setResultMsg("CUBC传入的租车编号不能为空！");
			return carCancelResponseDO;
		}
		//判断最大作废条数
		if(carCancelRequestDO.getSourceBillNoList().size()>1000){
			carCancelResponseDO.setStatus("0");
			carCancelResponseDO.setResultMsg("批量取消最大不能超过1000条");
			return carCancelResponseDO;
		}
		
		//获取修改人code
		String empCode = carCancelRequestDO.getEmpCode();
		//获取修改人姓名
		String empName = carCancelRequestDO.getEmpName();
		//定义报账系统请求实体
		CrmBudgetControlRequestEntity crmBudgetControlRequestEntity=null;
		BudgetEntity  budgetEntity = null;
		List<BudgetEntity> budgetEntityList=null;
		//获取申请人对应的组织标杆标码
		String applayEmpUnifyCode=null;
		//费用承担部门对应的标杆标码
		String feeDeptUnifyCode =null;
		//根据租车编码获取租车标记实体信息
		RentalTempMarkEntity rentalTempMarkEntity =null;
		
		//遍历租车编号
		for(String hireCarCode : carCancelRequestDO.getSourceBillNoList()){
			hireCarTagMap = new HashMap<String,Object>();
			hireCarTagMap.put("hireCarCode", hireCarCode);
			hireCarTagMap.put("empCode", empCode);
			hireCarTagMap.put("empName", empName);
			hireCarTagMap.put("date", new Date());
			
			/**
			 * 封装调用FSSC报账系统的实体信息
			 */
			crmBudgetControlRequestEntity = new CrmBudgetControlRequestEntity();
			budgetEntity = new BudgetEntity();
			budgetEntityList = new ArrayList<BudgetEntity>();
			
			//根据租车编码获取租车标记实体信息
			rentalTempMarkEntity = cancelHireCarTagDao.queryTempMarkEnt(hireCarCode);
			//判断租车编码是否存在
			if(rentalTempMarkEntity==null){
				carCancelResponseDO.setStatus("0");
				carCancelResponseDO.setResultMsg("租车编号：["+hireCarCode+"]执行失败！");
				LOGGER.error("租车编号："+hireCarCode+" 执行失败！");
				return carCancelResponseDO;
			}
			
			try {
				//调用dao层执行数据库操作
				cancelHireCarTagDao.doCancelHireCarDao(hireCarTagMap);
			} catch (Exception e) {
				carCancelResponseDO.setStatus("0");
				carCancelResponseDO.setResultMsg("租车编号：["+hireCarCode+"]执行失败！");
				LOGGER.error("租车编号："+hireCarCode+" 执行失败！"+e.getMessage());
				return carCancelResponseDO;
			}
			/**
			 * author:106162 date:2017-04-18 note:配合CUBC当取消租车标记,则调用FSSC报账系统释放租车金额占用
			 */
			//获取申请人对应的组织标杆标码
			applayEmpUnifyCode = cancelHireCarTagDao.queryUnifyCodeByEmpCode(empCode);
			//获取费用承担部门对应的标杆标码
			feeDeptUnifyCode = cancelHireCarTagDao.queryUnifyCodeByDeptCode(rentalTempMarkEntity.getFeesDeptCode());
			//封装实体信息
			crmBudgetControlRequestEntity.setApplyDate(df.format(new Date()));
			crmBudgetControlRequestEntity.setClaimID(hireCarCode);
			crmBudgetControlRequestEntity.setEmpCode(empCode);
			crmBudgetControlRequestEntity.setFlag("1");
			crmBudgetControlRequestEntity.setTotalAmount(rentalTempMarkEntity.getRentalAmount());
			crmBudgetControlRequestEntity.setApplyDeptStandCode(applayEmpUnifyCode);
			
			budgetEntity.setAmount(rentalTempMarkEntity.getRentalAmount());
			budgetEntity.setDeptStandCode(feeDeptUnifyCode);
			budgetEntity.setMonth(rentalTempMarkEntity.getUseCarDate());
			budgetEntityList.add(budgetEntity);
			
			crmBudgetControlRequestEntity.setBudgetEntitys(budgetEntityList);
			//CUBC本地接口调试
			//调用取消租车标记释放租金金额FSSC报账系统的ESB接口
			//CrmBudgetControlResponseEntity  responseEnt=aSYRentCarCubcService.callFSSCInterface(crmBudgetControlRequestEntity);
			try{
				//调用ESB接口
				CrmBudgetControlResponseEntity  responseEnt=aSYRentCarCubcService.pushTemptalMarkFeeInfoToFSSC(crmBudgetControlRequestEntity);
				
				if(responseEnt==null){
					throw new TfrBusinessException("租车编码："+ hireCarCode+"  调用FSSC租车金额接口返回为空，失败!");
				}
				if("0".equals(responseEnt.getIsSucceed())){
					throw new TfrBusinessException("租车编码："+ hireCarCode+"  租车金额费用不足!");
				}
			}catch(Exception e){
				throw new TfrBusinessException(e.getMessage());
			}
			
		}
		carCancelResponseDO.setStatus("1");
		carCancelResponseDO.setResultMsg("执行成功！");
		LOGGER.info("结束,执行全部成功！");
		return carCancelResponseDO;
	}
	
	/**
	 * set\get...
	 * @return
	 */
	public ICancelHireCarTagDao getCancelHireCarTagDao() {
		return cancelHireCarTagDao;
	}

	public void setCancelHireCarTagDao(ICancelHireCarTagDao cancelHireCarTagDao) {
		this.cancelHireCarTagDao = cancelHireCarTagDao;
	}

	public IASYRentCarCubcService getaSYRentCarCubcService() {
		return aSYRentCarCubcService;
	}

	public void setaSYRentCarCubcService(
			IASYRentCarCubcService aSYRentCarCubcService) {
		this.aSYRentCarCubcService = aSYRentCarCubcService;
	}

}
