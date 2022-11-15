package br.edu.fateczl.ConsumoDeAPISpringRest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BME280dto {
	private String dateAndtime;
	private Double pressure;
	private Double humidity;
	private Double temperature;
	private String codigoOP;
	private String saidaOP;
	public String getDateAndtime() {
		return dateAndtime;
	}
	public void setDateAndtime(String dateAndtime) {
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
	
	public BME280 toBME280(){
		BME280 s = new BME280();
		s.setCodigoOP(codigoOP);
		s.setHumidity(humidity);
		s.setPressure(pressure);
		s.setSaidaOP(saidaOP);
		s.setTemperature(temperature);
		s.setDateAndtime(obterData(dateAndtime));
		return s;
	}
	
	public static List<BME280> toListBME280(List<BME280dto> lista){
		List<BME280> sensors = new ArrayList<>();
		for(BME280dto se:lista) {
			BME280 s = new BME280();
			s.setCodigoOP(se.getCodigoOP());
			s.setHumidity(se.getHumidity());
			s.setPressure(se.getPressure());
			s.setSaidaOP(se.getSaidaOP());
			s.setTemperature(se.getTemperature());
			s.setDateAndtime(obterData(se.getDateAndtime()));
			sensors.add(s);
		}
		return sensors;
	}
	private static LocalDateTime obterData(String data) {
		data = data+".0000000";
		return BME280.parseDate(data);
	}
}
