package br.edu.fateczl.ConsumoDeAPISpringRest.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.edu.fateczl.ConsumoDeAPISpringRest.model.BME280;
import br.edu.fateczl.ConsumoDeAPISpringRest.model.BME280dto;

public class SensorConsumer {
	private final String URL = "http://localhost:8080/SpringRest/api/sensor/";
	
	public String getSensoresJson(BME280dto sensor) throws Exception{
		if(sensor!=null) {
			String urlFinal = URL + sensor.getDateAndtime().toString();			
			return getJson(urlFinal);			
		}else {	
			return getJson(URL);	
		}
	}
	
	public BME280 findOne(BME280dto sensor) throws Exception{
		String json = getSensoresJson(sensor);
		BME280 s = convertJsonToSensor(json);
		return s;
	}
	
	public List<BME280> findAll() throws Exception{
		String json = getSensoresJson(null);
		List<BME280> s = convertJsonToListSensor(json);
		return s;
	}
	
	private String getJson(String urlFinal) throws Exception{
		Charset c = Charset.forName("UTF-8");
		URL u = new URL(urlFinal);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if(conn.getResponseCode()!=200) {
			throw new IOException("Erro : "+conn.getResponseCode());
		}
		
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,c);
		BufferedReader br = new BufferedReader(isr);
		String saida = br.readLine();
		StringBuffer sb = new StringBuffer();
		
		while(saida != null) {
			sb.append(saida);
			saida = br.readLine();
		}
		br.close();
		isr.close();
		
		return sb.toString();
	}

	public String sendSensor(BME280dto sensor,String method) throws Exception {
		URL u = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(20000);
		conn.setReadTimeout(20000);
		conn.setDoOutput(true);
		conn.setUseCaches(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("accept", "text/plain");
		
		OutputStream out = conn.getOutputStream();
		String json = convertBME280DtoToJson(sensor);
		byte[] bytes = json.getBytes("UTF-8");
		out.write(bytes);
		out.flush();
		out.close();
		
		InputStream in = conn.getInputStream();
		byte[] bytesResponse = new byte[2048];
		StringBuffer sbRes = new StringBuffer();
		int i = 0;
		while((i = in.read(bytesResponse)) != -1) {
			sbRes.append(new String(bytesResponse,0,i));
		}
		in.close();
		
		return sbRes.toString();
	}
	
	private String convertBME280DtoToJson(BME280dto sensor) {
		Gson gson = new Gson();
		String json = gson.toJson(sensor);
		return json;
	}
	
	private BME280 convertJsonToSensor(String json) throws Exception{
		Gson gson = createGson();
		
		BME280dto sensor = new BME280dto();
		sensor = gson.fromJson(json, BME280dto.class);
		return sensor.toBME280();
	}
	
	private List<BME280> convertJsonToListSensor(String json) throws Exception{
		Gson gson = createGson();
		List<BME280> sensores = new ArrayList<>();
		List<BME280dto> sensoresAux = new ArrayList<>();
		
		TypeToken<List<BME280dto>> token = new TypeToken<List<BME280dto>>(){};
		Type tipo = token.getType();
		
		sensoresAux = gson.fromJson(json, tipo);
		sensores = BME280dto.toListBME280(sensoresAux);
		return sensores;
	}
	
	private Gson createGson() {
		Gson gson = new Gson();
		return gson;
	}
}
