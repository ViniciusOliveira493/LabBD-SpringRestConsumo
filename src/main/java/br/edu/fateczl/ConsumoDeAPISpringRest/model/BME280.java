package br.edu.fateczl.ConsumoDeAPISpringRest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BME280 {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSSS";
	private LocalDateTime dateAndtime;
	private Double pressure;
	private Double humidity;
	private Double temperature;
	private String codigoOP;
	private String saidaOP;
	
	public LocalDateTime getDateAndtime() {
		return dateAndtime;
	}

	public void setDateAndtime(LocalDateTime dateAndtime) {
		this.dateAndtime = dateAndtime;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getCodigoOP() {
		return codigoOP;
	}

	public void setCodigoOP(String codigoOP) {
		this.codigoOP = codigoOP;
	}

	public String getSaidaOP() {
		return saidaOP;
	}

	public void setSaidaOP(String saidaOP) {
		this.saidaOP = saidaOP;
	}

	@Override
	public String toString() {
		return dateAndtime + " | " + pressure + " | " + humidity  + " | " + temperature;
	}
	
	public static LocalDateTime parseDate(String dateAndtime) {
		DateTimeFormatter frm = DateTimeFormatter.ofPattern(BME280.DATE_FORMAT);
		return (LocalDateTime.parse(dateAndtime.replace("T", " "),frm));
	}
}
