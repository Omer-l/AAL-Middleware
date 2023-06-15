package entity.mysql.beacon_localisation;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "beacon")
public class Beacon {

	@Id
    @Column(name = "MAC")
    private String mac;
	 
	@Column(name = "deviceNumber")
	private int deviceNumber;
	
    @Column(name = "location")
    private String location;
    
	public Beacon() {
		
	}

	public Beacon(String idRecord, int deviceNumber, String location) {
		this.mac = idRecord;
		this.deviceNumber = deviceNumber;
		this.location = location;
	}

	public String getIdRecord() {
		return mac;
	}

	public void setIdRecord(String idRecord) {
		this.mac = idRecord;
	}

	public int getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(int deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Beacon [idRecord=" + mac + ", deviceNumber=" + deviceNumber + ", location=" + location + "]";
	}
}