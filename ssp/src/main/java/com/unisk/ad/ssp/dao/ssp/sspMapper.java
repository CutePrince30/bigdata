package com.unisk.ad.ssp.dao.ssp;

import com.unisk.ad.ssp.model.InfoJsParam;
import com.unisk.ad.ssp.model.Ssp2BidderPullParam;

public interface SspMapper {

	Ssp2BidderPullParam selectOneByZoneId(String id);

	Ssp2BidderPullParam selectAppInfoByMediaId(String id);

	Ssp2BidderPullParam selectSiteInfoByMediaId(String id);

	InfoJsParam selectAdInfoByStuffId(String id);
	
}
