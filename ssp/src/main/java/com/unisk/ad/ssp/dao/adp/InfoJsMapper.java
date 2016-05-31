package com.unisk.ad.ssp.dao.adp;

import com.unisk.ad.ssp.model.InfoJsParameter;
import org.springframework.stereotype.Component;

/**
 * Created by CutePrince on 2016-05-31.
 */
public interface InfoJsMapper {

    public InfoJsParameter selectOneByAdidFromAd(String adid);

    public InfoJsParameter selectOneByAdidFromStuff(String adid);

}
