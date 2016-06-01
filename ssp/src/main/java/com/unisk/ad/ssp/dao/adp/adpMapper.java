package com.unisk.ad.ssp.dao.adp;

import com.unisk.ad.ssp.model.InfoJsParameter;

/**
 * Created by CutePrince on 2016-05-31.
 */
public interface AdpMapper {

    public InfoJsParameter selectOneByAdidFromAd(String adid);

    public InfoJsParameter selectOneByAdidFromStuff(String adid);

}
