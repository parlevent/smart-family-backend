package com.yfzm.flawsweeper.services.impl;

import com.yfzm.flawsweeper.dao.ItemDao;
import com.yfzm.flawsweeper.dao.StatisticsDao;
import com.yfzm.flawsweeper.dao.TagDao;
import com.yfzm.flawsweeper.form.statistics.item.info.StaItemInfoEntry;
import com.yfzm.flawsweeper.form.statistics.item.info.StaItemInfoResponse;
import com.yfzm.flawsweeper.form.statistics.item.tag.StaItemTagEntry;
import com.yfzm.flawsweeper.form.statistics.item.tag.StaItemTagResponse;
import com.yfzm.flawsweeper.form.statistics.item.time.StaItemNumTimeResponse;
import com.yfzm.flawsweeper.services.StatisticsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.yfzm.flawsweeper.util.Constant.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final ItemDao itemDao;
    private final TagDao tagDao;

    @Autowired
    public StatisticsServiceImpl(ItemDao itemDao, TagDao tagDao) {
        this.itemDao = itemDao;
        this.tagDao = tagDao;
    }


    @Override
    public Long getItemTotalNum() {
        List list = itemDao.getItemTotalNum();
        if (list == null || list.size() != 1) {
            return null;
        }
        return ((BigInteger)list.get(0)).longValue();
    }

    @Override
    public StaItemInfoResponse getItemInfo() {
        StaItemInfoResponse response = new StaItemInfoResponse(false);
        List adminItemNumList = itemDao.getItemNum(PUBLIC_ITEM_MODE);
        List normalItemNumList = itemDao.getItemNum(PRIVATE_ITEM_MODE);

        List userItemNumList = itemDao.getTopItemInfo();
        if (adminItemNumList == null || adminItemNumList.size() != 1
                || normalItemNumList == null || normalItemNumList.size() != 1
                || userItemNumList == null) {
            return response;
        }
        response.setStatus(true);
        response.setAdminItemNum(((BigInteger)adminItemNumList.get(0)).longValue());
        response.setNormalItemNum(((BigInteger)normalItemNumList.get(0)).longValue());

        List<StaItemInfoEntry> infoEntries = new ArrayList<>();
        int count = 0;
        for (Object object: userItemNumList) {
            count++;
            StaItemInfoEntry entry = new StaItemInfoEntry();
            entry.setUsername((String)(((Object[])object)[0]));
            entry.setNum(((BigInteger)((Object[])object)[1]).intValue());
            infoEntries.add(entry);
            if (count >= 10) break;
        }
        response.setUserItemInfo(infoEntries);

        return response;
    }

    @Override
    public StaItemNumTimeResponse getItemTimeNum() {
        StaItemNumTimeResponse response = new StaItemNumTimeResponse(false);

        List a0 = itemDao.getItemNumBetween(0, 1);
        List a1 = itemDao.getItemNumBetween(1, 2);
        List a2 = itemDao.getItemNumBetween(2, 3);
        List a3 = itemDao.getItemNumBetween(3, 4);
        List a4 = itemDao.getItemNumBetween(4, 5);
        List a5 = itemDao.getItemNumBetween(5, 6);

        if (a0 == null || a1 == null || a2 == null
                || a3 == null || a4 == null || a5 == null) {
            return response;
        }

        response.setStatus(true);
        response.setN0(((BigInteger)a0.get(0)).intValue());
        response.setN1(((BigInteger)a1.get(0)).intValue());
        response.setN2(((BigInteger)a2.get(0)).intValue());
        response.setN3(((BigInteger)a3.get(0)).intValue());
        response.setN4(((BigInteger)a4.get(0)).intValue());
        response.setN5(((BigInteger)a5.get(0)).intValue());

        return response;

    }

    @Override
    public StaItemTagResponse getItemTagInfoNum() {
        StaItemTagResponse response = new StaItemTagResponse(false);
        List itemTagNumList = tagDao.getTopItemTag();

        List<StaItemTagEntry> tagEntries = new ArrayList<>();
        for (Object object: itemTagNumList) {
            StaItemTagEntry entry = new StaItemTagEntry();
            entry.setName((String)(((Object[])object)[0]));
            entry.setNum(((BigInteger)((Object[])object)[1]).intValue());
            tagEntries.add(entry);
        }
        response.setItemTagInfo(tagEntries);
        response.setStatus(true);
        return response;
    }
}
