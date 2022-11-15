package br.edu.fateczl.ConsumoDeAPISpringRest.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fateczl.ConsumoDeAPISpringRest.consumer.SensorConsumer;
import br.edu.fateczl.ConsumoDeAPISpringRest.model.BME280;
import br.edu.fateczl.ConsumoDeAPISpringRest.model.BME280dto;

@WebServlet("/sensor")
public class SensorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SensorConsumer sc = new SensorConsumer();
	RequestDispatcher rd;
	private String erro = "";
	BME280 sensor;
	List<BME280> sensores;
	private String msg = "";

	public SensorController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		limparAtributos();
		try {
			sensores = sc.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			erro = e.getMessage();
		}
		resposta(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		limparAtributos();
		try {
			sensores = sc.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			erro = e.getMessage();
		}
		BME280dto s = getBME(request);
		switch (s.getCodigoOP()) {
		case "I":
			try {
				msg = sc.sendSensor(s,"POST");
			} catch (Exception e) {
				e.printStackTrace();
				erro = e.getMessage();
			}
			break;
		case "U":
			try {
				msg = sc.sendSensor(s,"PUT");
			} catch (Exception e) {
				erro = e.getMessage();
			}
			break;
		case "D":
			try {
				msg = sc.sendSensor(s,"DELETE");
			} catch (Exception e) {
				erro = e.getMessage();
			}
			break;
		default:
			try {
				sensor = sc.findOne(s);
			} catch (Exception e) {
				erro = e.getMessage();
			}
			break;
		}
		resposta(request, response);
	}

	private BME280dto getBME(HttpServletRequest request) {
		String data = request.getParameter("txtDataHora");
		String temp = request.getParameter("txtTemp");
		String umidade = request.getParameter("txtUmidade");
		String pressao = request.getParameter("txtPressao");
		String btn = request.getParameter("btn");
		
		if(temp==null || temp=="") {
			temp = 0+"";
		}
		
		if(umidade ==null || umidade =="") {
			umidade  = 0+"";
		}
		
		if(pressao ==null || pressao =="") {
			pressao = 0+"";
		}
		
		BME280dto s = new BME280dto();

		if(data==null || data=="") {
			data = getDataAtual();	
		}
		
		s.setDateAndtime(data+":00.0000000");
		s.setTemperature(Double.parseDouble(temp));
		s.setHumidity(Double.parseDouble(umidade));
		s.setPressure(Double.parseDouble(pressao));

		switch (btn) {
		case "Cadastrar":
			s.setCodigoOP("I");
			break;
		case "Atualizar":
			s.setCodigoOP("U");
			break;
		case "Excluir":
			s.setCodigoOP("D");
			break;
		default:
			s.setCodigoOP("F");
			break;
		}
		return s;
	}

	private String getDataAtual() {
		Date dataHoraAtual = new Date();
		String dt = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm").format(dataHoraAtual);
		return dt+" "+hora;		
	}

	private void limparAtributos() {
		erro = "";
		msg = "";
		sensor = new BME280();
		sensores = new ArrayList<>();
	}

	private void resposta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rd = request.getRequestDispatcher("index.jsp");
		request.setAttribute("erro", erro);
		request.setAttribute("res", msg);
		request.setAttribute("sensor", sensor);
		request.setAttribute("listaSensores", sensores);
		rd.forward(request, response);
	}

}
