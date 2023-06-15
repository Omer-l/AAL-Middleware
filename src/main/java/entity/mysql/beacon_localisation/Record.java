package entity.mysql.beacon_localisation;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "record")
public class Record {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecord")
    private int idRecord;
	
	@Column(name = "MAC")
	private String mac;
	
	@Column(name = "RSSI")
	private int rssi;

	@Column(name = "dateTime")
	private Date dateTime;

	@Column(name = "x")
	private int x;
	
	@Column(name = "y")
	private int y;
	
/**	Foreign Keys Objects */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idUser")
	private User user;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idRoom")
	private Room room;
	
	public Record() {
		
	}

	public int getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(int idRecord) {
		this.idRecord = idRecord;
	}
	
	public String getMac() {
		return mac;
	}



	public void setMac(String mac) {
		this.mac = mac;
	}



	public int getRssi() {
		return rssi;
	}



	public void setRssi(int rssi) {
		this.rssi = rssi;
	}



	public Date getDateTime() {
		return dateTime;
	}



	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Record [idRecord=" + idRecord + ", mac=" + mac + ", rssi=" + rssi + ", dateTime=" + dateTime + ", x="
				+ x + ", y=" + y + ", user=" + user + ", room=" + room + "]";
	}
}
