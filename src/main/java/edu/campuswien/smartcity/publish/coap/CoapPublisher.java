package edu.campuswien.smartcity.publish.coap;

import com.google.gson.JsonElement;
import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.tools.DataFormatUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;

public class CoapPublisher {
    private final CoapClient coapClient;

    public CoapPublisher(String serverUri) {
        coapClient = new CoapClient(serverUri);
    }

    public void sendParkingRecordToServer(ParkingRecord parkingRecord)  {
        JsonElement json = DataFormatUtil.convertParkingRecordToJson(parkingRecord);

        coapClient.post(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println(response.getResponseText());
            }
            @Override
            public void onError() {
                System.out.println("Bad Request");
            }
        }, json.toString(), 0);
    }
}
