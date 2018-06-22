package com.yfzm.flawsweeper.services;

import com.yfzm.flawsweeper.form.statistics.item.info.StaItemInfoResponse;
import com.yfzm.flawsweeper.form.statistics.item.tag.StaItemTagResponse;
import com.yfzm.flawsweeper.form.statistics.item.time.StaItemNumTimeResponse;

public interface StatisticsService {
    Long getItemTotalNum();

    StaItemInfoResponse getItemInfo();

    StaItemNumTimeResponse getItemTimeNum();

    StaItemTagResponse getItemTagInfoNum();
}
