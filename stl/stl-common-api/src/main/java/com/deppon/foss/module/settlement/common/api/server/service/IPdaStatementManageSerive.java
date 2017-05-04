package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReturnGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;

/**
 * PDA对账单管理Service
 * 
 * @ClassName: IPdaStatementSerive
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-18 下午4:32:21
 */
public interface IPdaStatementManageSerive {
    /**
     * 根据对账单号或客户编码查询对账单信息 查询当前登录部门对账单信息
     * 
     * @param number 对账单号或客户编码
     * @param orgCode 部门编码
     * @Title: queryStatementByNo
     * @author： 269052 |zhouyuan008@deppon.com
     */
    PdaStatementManageDto queryStatementByNo(CommonQueryParamDto dto);

    /**
     * 对账单还款
     * 
     * @Title: statementtoRepayment
     * @author： 269052 |zhouyuan008@deppon.com
     */
    List<PdaStatementManageDto> statementRepayment(List<PdaStatementManageDto> pdaStatementManageDtos);

    /**
     * 重写对账单还款(事务处理)
     * 
     * @Title: repayment
     * @author： 269052 |zhouyuan008@deppon.com
     */
    void repayment(PdaStatementManageDto dto, CurrentInfo cinfo);

    // 供pda调用对账单方法
    ReturnGreenHandWrapEntity statementRepaymentwriteoff(VerificationEntity verificationEntity);

}
