package entity.postgresql.sensors;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensors")
public class Sensors {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device")
    private String device;
	 
	@Column(name = "implementation")
	private String implementation;
	 
	@Column(name = "state")
	private String state;

	@Override
	public String toString() {
		return "Sensors [device=" + device + ", implementation=" + implementation + ", state=" + state + "]";
	}
}