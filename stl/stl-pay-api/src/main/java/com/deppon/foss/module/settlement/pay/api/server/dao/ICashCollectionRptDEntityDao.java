package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;

/**
 * 现金收入明细dao
 * @author 095793-foss-LiQin
 * @date 2012-12-24 下午8:06:26
 */
public interface ICashCollectionRptDEntityDao {
    /**
     * 查询现金收入缴款报表明细
     * @author 095793-foss-LiQin
     * @date 2012-12-12 下午4:29:06
     */
    List<CashCollectionRptDEntity> queryByCashRecPayInD(BillCashRecPayInDDto billCashRecDDto,int start ,int limit);
    
    /**
     * 查询现金收入报表明细总条数
     * @author 095793-foss-LiQin
     * @date 2013-3-28 上午11:11:02
     * @param billCashRecPayInDto
     * @return
     */
    BillCashRecPayInDResultDto queryCountByCashRecPayDIn(BillCashRecPayInDDto billCashRecPayInDDto);

    /**
     * 根据，现金收入部门和报表编号，查询需要更新缴款的现金收入缴款详细明细（财务自助处理）
     * @author 095793-foss-LiQin
     * @date 2012-12-14 下午4:47:45
     */
    List<CashCollectionRptDEntity> queryUpdateCashinDComerpt(BillCashRecPayInDDto billCashRecDDto);
    
    /**
     * 根据DTO查询部门的已交款金额
     *
     *
     * @author 092036-foss-bochenlong
     * @date 2014-2-17 下午3:39:53 
     * @param billCashRecDDto
     * @return
     */
    BigDecimal queryPaiAmount(BillCashRecPayInDDto billCashRecDDto);
    
    /**
     * 根据DTO查询部门的最早缴款时间
     *
     *
     * @author 092036-foss-bochenlong
     * @date 2014-2-17 下午3:39:53 
     * @param billCashRecDDto
     * @return
     */
    Date queryEarLiestDate(BillCashRecPayInDDto billCashRecDDto);
    
    /**
     * 更新现金收入报表明细已缴款金额和未缴款金额
     * @author 095793-foss-LiQin
     * @date 2012-12-15 上午9:15:29
     */
    int updateCashincomerptD(List<CashCollectionRptDEntity> perList ,BillCashRecPayInDDto billCashRecDDto);
    
    /**
     * 查询部门现金未缴款总金额
     * @author 095793-foss-LiQin
     * @date 2013-4-27 上午11:12:23
     * @param billCashRecDDto
     * @return
     */
    List<CashCollectionRptDEntity> queryDeptUncollectedAmount(String orgCode,String paymentType);
    
}