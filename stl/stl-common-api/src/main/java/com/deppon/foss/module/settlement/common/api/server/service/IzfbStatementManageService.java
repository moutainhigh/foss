package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReturnGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;

public interface IzfbStatementManageService extends IService {

    ReturnGreenHandWrapEntity statementRepaymentwriteoff(VerificationEntity verificationEntity);

    List<PdaStatementManageDto> statementRepayment(List<PdaStatementManageDto> pdaStatementManageDtos,
            VerificationEntity verificationEntity);

    void repayment(PdaStatementManageDto dto, CurrentInfo cInfo);

}
