package edu.campuswien.smartcity.tools;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TimeIdGeneratorTest {

    public static final long ID = 103376611641917440L;
    public static final long TIMESTAMP = 1653315052430L;
    public static final int DBNR = 0;

    @Test
    void getTime() {
        long mills = TimeIdGenerator.getTime(ID);
        assertEquals(TIMESTAMP, mills);
        assertEquals(ID, TimeIdGenerator.getId(TIMESTAMP));
    }

    @Test
    void getDate() {
        long id = TimeIdGenerator.getObjectId(DBNR);
        Date date = TimeIdGenerator.getDate(id);
        Date now = new Date(System.currentTimeMillis());
        assertTrue(now.after(date) || now.equals(date));
    }

    @Test
    void getId() {
        long id = TimeIdGenerator.getId(TIMESTAMP);
        assertEquals(ID, id);
        assertEquals(TIMESTAMP, TimeIdGenerator.getTime(id));
    }

    @Test
    void getBnr() {
        assertEquals(DBNR, TimeIdGenerator.getBnr(ID));
    }

}