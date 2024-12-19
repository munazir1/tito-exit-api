package in.gw.TitoExitApi2;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "TITO_Data")  // Your table name remains the same
public class TitoData {

    @Id
    @Column(name = "GateSlipNo")
    private String gateSlipNo;

    @Column(name = "VehicleNo")
    private String vehicleNo;


    @Column(name = "AssignTime")
    private LocalDateTime assignTime;  

    @Column(name = "Processed")
    private Boolean process;
    // Getters and Setters
    @Column(name = "Active")
    private Boolean active;
    


	public String getGateSlipNo() {
		return gateSlipNo;
	}

	public void setGateSlipNo(String gateSlipNo) {
		this.gateSlipNo = gateSlipNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public LocalDateTime getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(LocalDateTime assignTime) {
		this.assignTime = assignTime;
	}

	public Boolean getProcess() {
		return process;
	}

	public void setProcess(Boolean process) {
		this.process = process;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public TitoData(String gateSlipNo, String vehicleNo, LocalDateTime assignTime, Boolean process, Boolean active) {
		super();
		this.gateSlipNo = gateSlipNo;
		this.vehicleNo = vehicleNo;
		this.assignTime = assignTime;
		this.process = process;
		this.active = active;
	}

	public TitoData() {
		super();
	}


	
}
