package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.WorkerbillCountQueryDto;

public interface IExpressWorkerStatusDao {
    int deleteByPrimaryKey(Object id);

    int insert(ExpressWorkerStatusEntity record);

    int insertSelective(ExpressWorkerStatusEntity record);

    ExpressWorkerStatusEntity selectOneByEmpcode(String empCode);
    /**
	 * @author:gcl
	 * @Description：根据签到id查询员工状态
	 * @date:2014-4-29 下午2:23:05
	 */
	ExpressWorkerStatusEntity selectOneByPdaSignid(String pdaSignId);

    int updateByPrimaryKeySelective(ExpressWorkerStatusEntity record);

    int updateByPrimaryKey(ExpressWorkerStatusEntity record);
    
    List<ExpressWorkerStatusEntity> selectByEmployeeCodes(List<CourierSignDto> courierSignDtoList);

    /**
     * 
     * @param dto 查询条件
     * @description：根据工号更新
     */
    int updateByEmployeeCodes(ExpressWorkerStatusDto dto);
    /**
     * 查询送货运单的统计结果
     */
    List<ExpressWorkerBillCountDto> queryWaybillCount(WorkerbillCountQueryDto dto);
    /**
     * 查询接货订单的统计结果
     * */
    List<ExpressWorkerBillCountDto> queryOrderBillCount(WorkerbillCountQueryDto dto);
    /**
     * 查询无订单开单-运单表的统计结果
     * */
    List<ExpressWorkerBillCountDto> queryNoOrderBillCount(WorkerbillCountQueryDto dto);

    /**
     * 根据工号查询快递员工作状态
     * selectByEmployeeCodesByEntity: <br/>
     * 
     * Date:2014-7-8下午8:10:36
     * @author 157229-zxy
     * @param workerStatus
     * @param list
     * @return
     * @since JDK 1.6
     */
    List<ExpressWorkerStatusEntity> selectByEmployeeCodesByEntity(ExpressWorkerStatusEntity workerStatus,List<CourierSignDto> list);
    /**
	 * 
	 * @author:gaochunling
	 * @Description：根据工号查询快递员工作状态的最新一条数据
	 * @date:2014-7-9 
	 */
	List<ExpressWorkerStatusEntity> selectUpDateByEmployeeCodes(List<CourierSignDto> list) ;
	/**
	 * 零担自动分配 先 根据车牌号查询车辆工作状态 
	 * 14.7.17 gcl AUTO-170
	 */
	Long selectOneByVehicleNo(ExpressWorkerStatusEntity worker);
	 /**
     * 查询无订单开单-运单补录表的统计结果
     * */
    List<ExpressWorkerBillCountDto> queryNoOrderPendingCount(WorkerbillCountQueryDto dto);
    

	/**
     * 
     * @Title: queryOrderVehViewByCommon 
     * @Description: 查询司机、车牌对应的订单信息
     * @param @param vehicleNoList
     * @param @param entity
     * @param @return    设定文件 
     * @return List<ExpressWorkerStatusEntity>   返回类型 
     * @throws
     */
    public  List<ExpressWorkerStatusEntity> queryWorkStatusByVehicle(List<String> vehicleNoList,ExpressWorkerStatusEntity entity);
    
    
    /**
     * 根据车牌号查询车辆工作状态 
     * queryOneByVehicleNo: <br/>
     * 
     * Date:2014-7-17下午8:32:12
     * @author 157229-zxy
     * @param queryParam
     * @return
     * @since JDK 1.6
     */
    public ExpressWorkerStatusEntity queryOneByVehicleNo(ExpressWorkerStatusEntity queryParam);
    
    /**
     * 
     * @Title: updateByVehicleNos
     * @Description: 更新车牌集合对应的工作状态
     * @param @param dto
     * @param @return    设定文件 
     * @return int   返回类型 
     * @throws
     */
    public int updateByVehicleNos(ExpressWorkerStatusDto dto);

}