package com.unisk.ad.ssp.dao.ssp;

import com.unisk.ad.ssp.model.Ssp2BidderShowParameter;

public interface SspMapper {

	Ssp2BidderShowParameter selectOneBySlotId(String slotId);
	
}
