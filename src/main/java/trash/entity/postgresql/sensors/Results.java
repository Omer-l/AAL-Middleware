package trash.entity.postgresql.sensors;
//package entity.postgresql.sensors;
//
//import java.math.BigInteger;
//import java.util.Date;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "results")
//public class Results {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "iteration")
//    private BigInteger iteration;
//	 
//	@Column(name = "system_time_millis")
//	private BigInteger systemTimeMillis;
//	
//	@Column(name = "lbiglamp")
//	private boolean lBigLamp;
//	
//	@Column(name = "livingroommotion")
//	private boolean livingRoomMotion;
//	
//	@Column(name = "ginesinlivingroom")
//	private boolean ginesInLivingRoom;
//	
//	public BigInteger getIteration() {
//		return iteration;
//	}
//
//
//
//	public void setIteration(BigInteger iteration) {
//		this.iteration = iteration;
//	}
//
//
//
//	public BigInteger getSystemTimeMillis() {
//		return systemTimeMillis;
//	}
//
//
//
//	public void setSystemTimeMillis(BigInteger systemTimeMillis) {
//		this.systemTimeMillis = systemTimeMillis;
//	}
//
//
//
//	public boolean islBigLamp() {
//		return lBigLamp;
//	}
//
//
//
//	public void setlBigLamp(boolean lBigLamp) {
//		this.lBigLamp = lBigLamp;
//	}
//
//
//
//	public boolean isLivingRoomMotion() {
//		return livingRoomMotion;
//	}
//
//
//
//	public void setLivingRoomMotion(boolean livingRoomMotion) {
//		this.livingRoomMotion = livingRoomMotion;
//	}
//
//
//
//	public boolean isGinesInLivingRoom() {
//		return ginesInLivingRoom;
//	}
//
//
//
//	public void setGinesInLivingRoom(boolean ginesInLivingRoom) {
//		this.ginesInLivingRoom = ginesInLivingRoom;
//	}
//
//
//
//	@Override
//	public String toString() {
//		return "Results [iteration=" + iteration + ", systemTimeMillis=" + systemTimeMillis + ", lBigLamp=" + lBigLamp
//				+ ", livingRoomMotion=" + livingRoomMotion + ", ginesInLivingRoom=" + ginesInLivingRoom + "]";
//	}
//}