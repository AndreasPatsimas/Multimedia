package domain;

import java.io.Serializable;

public class Video implements Serializable {

	private Integer id;
	private String name;
	private String format;
	private double bitrates;
	
	public Video(Integer id, String name, String format, double bitrates) {
		super();
		this.id = id;
		this.name = name;
		this.format = format;
		this.bitrates = bitrates;
	}
	
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}



	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}



	/**
	 * @return the bitrates
	 */
	public double getBitrates() {
		return bitrates;
	}



	/**
	 * @param bitrates the bitrates to set
	 */
	public void setBitrates(double bitrates) {
		this.bitrates = bitrates;
	}



	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", format=" + format + ", bitrates=" + bitrates + "]";
	}
	
//	public String getData() {
//		return "[" + id + "][" + name + "][" + format + "][" + bitrates + "] ";
//	}
	
	
}
