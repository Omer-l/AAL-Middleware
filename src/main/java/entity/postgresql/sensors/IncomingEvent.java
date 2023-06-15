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
@Table(name = "incoming_events")
public class IncomingEvent {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
	@SequenceGenerator(name = "event_generator", sequenceName = "incoming_events_SEQ", allocationSize = 1)
    @Column(name = "id")
    private int id;
	 
	@Column(name = "state")
	private String state;
	
    @Column(name = "value")
    private boolean value;
	
    @Column(name = "iteration")
    private BigInteger iteration;
    
    @Column(name = "date_old")
    private Date dateOld;
	
    @Column(name = "time_old")
    private Date timeOld;

	public IncomingEvent() {
		
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public boolean isValue() {
		return value;
	}



	public void setValue(boolean value) {
		this.value = value;
	}



	public BigInteger getIteration() {
		return iteration;
	}



	public void setIteration(BigInteger iteration) {
		this.iteration = iteration;
	}



	public Date getDateOld() {
		return dateOld;
	}



	public void setDateOld(Date dateOld) {
		this.dateOld = dateOld;
	}



	public Date getTimeOld() {
		return timeOld;
	}



	public void setTimeOld(Date timeOld) {
		this.timeOld = timeOld;
	}



	@Override
	public String toString() {
		return "IncomingEvent [id=" + id + ", state=" + state + ", value=" + value + ", iteration=" + iteration
				+ ", dateOld=" + dateOld + ", timeOld=" + timeOld + "]";
	}
}
