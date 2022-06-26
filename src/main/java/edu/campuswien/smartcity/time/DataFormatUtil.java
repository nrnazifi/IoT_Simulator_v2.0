package edu.campuswien.smartcity.time;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import edu.campuswien.smartcity.config.Constants;
import edu.campuswien.smartcity.data.entity.ParkingRecord;

import java.time.format.DateTimeFormatter;

public class DataFormatUtil {

    public static JsonElement convertParkingRecordToJson(ParkingRecord parkingRecord) {
        Gson gson = new Gson();
        JsonElement json = gson.toJsonTree(parkingRecord);
        json.getAsJsonObject().addProperty(ParkingRecord.FIELD_NAME_ARRIVAL_TIME,
                parkingRecord.getArrivalTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
        json.getAsJsonObject().addProperty(ParkingRecord.FIELD_NAME_DEPARTURE_TIME,
                parkingRecord.getDepartureTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));

        return json;
    }
}
