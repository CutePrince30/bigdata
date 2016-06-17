package com.unisk.ad.ssp.dao.ssp;

import com.unisk.ad.ssp.model.Ssp2BidderPullParam;

public interface SspMapper {

	Ssp2BidderPullParam selectOneBySlotId(String slotId);
	
}
