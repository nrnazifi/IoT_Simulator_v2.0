package edu.campuswien.smartcity.tools;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeIdGenerator {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static final int TIME_MULTIPLIER = 128;
    private static final long MILLISEC_OFFSET = 1640991600000L; // Jan 01 00:00:00 CET 2022
    private static final long MIN_MILLISEC = MILLISEC_OFFSET;
    private static final long MAX_MILLISEC = 2524604400000L; // Jan 01 00:00:00 CET 2050
    private static long lastObjectTime = 0L;

    /**
     * Return a long encoding the time and the BNR for OIDs without duplicates
     */
    public synchronized static long getObjectId(int dbnr) {
        // Works until Sat Sep 06 16:47:35 CEST 2081
        lastObjectTime = Math.max((System.currentTimeMillis() - MILLISEC_OFFSET) * TIME_MULTIPLIER, lastObjectTime + 1);
        return lastObjectTime << 16 | dbnr;
    }

    /**
     * Calculate the time for a given OID.
     * Use new Date(long) to retrieve
     */
    public static long getTime(long id) {
        return (id >> 16) / TIME_MULTIPLIER + MILLISEC_OFFSET;
    }

    public static Date getDate(long id) {
        return new Date(getTime(id));
    }

    /**
     * get an OID for specific given time having the 2 lowest significant bytes set to 0x00.
     * This method can be used for queries using the time
     */
    public static long getId(long time) {
        if(time < MIN_MILLISEC) {
            time = MIN_MILLISEC;
        }
        if(time > MAX_MILLISEC) {
            time = MAX_MILLISEC;
        }
        return (time - MILLISEC_OFFSET) * TIME_MULTIPLIER << 16;
    }

    /**
     * Answer the dbnr of a given id
     */
    public static int getBnr(long id) {
        return (int)(0xFFFFL & id);
    }

}

