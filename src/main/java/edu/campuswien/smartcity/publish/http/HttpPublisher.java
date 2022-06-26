package edu.campuswien.smartcity.publish.http;

import com.google.gson.JsonElement;
import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.time.DataFormatUtil;
import okhttp3.*;

public class HttpPublisher {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final String serverUri;

    public HttpPublisher(String serverUri) {
        this.serverUri = serverUri;
        client = new OkHttpClient();
    }

    public String sendParkingRecordToServer(ParkingRecord parkingRecord) {
        JsonElement json = DataFormatUtil.convertParkingRecordToJson(parkingRecord);

        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(serverUri)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch(Exception e) {
            e.printStackTrace();//TODO Logger
        }

        return "Bad Request";//TODO Logger & massage
    }

}
