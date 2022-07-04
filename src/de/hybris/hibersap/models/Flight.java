package de.hybris.hibersap.models;

import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;
import org.hibersap.util.DateUtil;

@BapiStructure
public class Flight {

    @Parameter("CARRID")
    private String carrierId;

    @Parameter("CONNID")
    private String connectionId;

    @Parameter("AIRPFROM")
    private String airportFrom;

    @Parameter("AIRPTO")
    private String airportTo;

    @Parameter("FLDATE")
    private Date flightDate;

    @Parameter("DEPTIME")
    private Date departureTime;

    @Parameter("SEATSMAX")
    private int seatsMax;

    @Parameter("SEATSOCC")
    private int seatsOccupied;

    public String getAirportFrom() { return this.airportFrom; }

    public String getAirportTo() { return this.airportTo; }

    public String getCarrierId() { return this.carrierId; }

    public String getConnectionId() { return this.connectionId; }

    public Date getDepartureTime() {
      return DateUtil.joinDateAndTime( flightDate, departureTime );
    }

    public Date getFlightDate() { return flightDate; }

    public int getSeatsMax() { return this.seatsMax; }

    public int getSeatsOccupied() { return this.seatsOccupied; }
}