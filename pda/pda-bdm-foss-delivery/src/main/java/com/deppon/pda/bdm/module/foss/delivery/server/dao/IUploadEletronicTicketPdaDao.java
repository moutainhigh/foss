package com.deppon.pda.bdm.module.foss.delivery.server.dao;

import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PDAElectronicTicketEntity;

public interface IUploadEletronicTicketPdaDao {
	void saveUploadResult(PDAElectronicTicketEntity pdaElectronicTicketEntity);
}
