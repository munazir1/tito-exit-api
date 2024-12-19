	package in.gw.TitoExitApi2;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="TITO_Details")
public class TitoDetails {

	
	@Id
   @Column(name="GateSlip")
    private String gateSlip;
	 @Column(name="LocationCode")
    private String locationCode;
	 @Column(name="TimeStamp")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp timeStamp;
	 @Column(name="processed")
    private Boolean processed;

    // Constructor
    public TitoDetails(String gateSlip, String locationCode, Timestamp timeStamp, Boolean processed) {
        this.gateSlip = gateSlip;
       
        this.locationCode = locationCode;
        this.timeStamp = timeStamp;
        this.processed = processed;
    }

    // Getters and setters
    public String getGateSlip() {
        return gateSlip;
    }

    public void setGateSlip(String gateSlip) {
        this.gateSlip = gateSlip;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

	public TitoDetails() {
		super();
	}
    
}
