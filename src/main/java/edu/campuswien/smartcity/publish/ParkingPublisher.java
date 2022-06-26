package edu.campuswien.smartcity.publish;

import edu.campuswien.smartcity.data.entity.IRecord;
import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.data.entity.Simulation;
import edu.campuswien.smartcity.data.enums.DataFormatEnum;
import edu.campuswien.smartcity.publish.coap.CoapPublisher;
import edu.campuswien.smartcity.publish.http.HttpPublisher;


public class ParkingPublisher extends AbstractPublisher {

    private final Simulation simulation;
    private final HttpPublisher httpPublisher;
    private final CoapPublisher coapPublisher;

    public ParkingPublisher(Simulation simulation) {
        super();
        this.simulation = simulation;
        this.httpPublisher = new HttpPublisher(simulation.getEndpointUri());
        this.coapPublisher = new CoapPublisher(simulation.getEndpointUri());
    }

    public void publish(IRecord record) {
        if(!(record instanceof ParkingRecord)) {
            throw new IllegalArgumentException("The type of record is not ParkingRecord!");
        }
        ParkingRecord parkingRecord = (ParkingRecord) record;

        if(DataFormatEnum.JSON.equals(simulation.getDataFormat())) {
            switch(simulation.getProtocol()) {
                case COAP:
                    runner(createCoapTask(parkingRecord));
                    break;
                case MQTT:
                    //TODO not implemented
                    break;
                case HTTP:
                    runner(createHttpTask(parkingRecord));
                    break;
            }

        } else if(DataFormatEnum.CSV.equals(simulation.getDataFormat())) {
            //TODO not implemented
        } else if(DataFormatEnum.XML.equals(simulation.getDataFormat())) {
            //TODO not implemented
        }
    }

    private Runnable createHttpTask(ParkingRecord parkingRecord) {
        return () -> {
            try {
                String response = httpPublisher.sendParkingRecordToServer(parkingRecord);
                System.out.println(response);//TODO Logger or ...
            } catch(Exception e) {
                e.printStackTrace();//TODO Logger
            }
        };
    }

    private Runnable createCoapTask(ParkingRecord parkingRecord) {
        return () -> {
            try {
                coapPublisher.sendParkingRecordToServer(parkingRecord);
            } catch(Exception e) {
                e.printStackTrace();//TODO Logger
            }
        };
    }
}
