<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Registros do Sensor</title>
		<link rel="stylesheet" type="text/css" href='<c:url value="../resources/css/style.css" />'>
	</head>
	<body>
		<main>
			<div class="container">
				<h1>Dados dos Sensores</h1>
				<form action="sensor" method = "POST">
					<div>
						<p>Digite a data e hora (Deixe "dd/mm/aaaa --:--" para usar a data atual):&nbsp;</p>
						<input class="form-item" type = "datetime-local" name="txtDataHora" id="txtDataHora" value='<c:out value="${sensor.dateAndtime}"></c:out>'>
					</div>
					<div>
						<p>Digite a temperatura:&nbsp;</p>
						<input class="form-item" type = "number" name="txtTemp" id="txtTemp" step="0.01" value='<c:out value="${sensor.temperature}"></c:out>'>
					</div>
					<div>
						<p>Digite a humidade:&nbsp;</p>
						<input class="form-item" type = "number" min="0" name="txtUmidade" id="txtUmidade" value='<c:out value="${sensor.humidity}"></c:out>'>
					</div>
					<div>
						<p>Digite a pressão:&nbsp;</p>
						<input class="form-item" type = "number" name="txtPressao" id="txtPressao" value='<c:out value="${sensor.pressure}"></c:out>'>
					</div>
					<br/>
					<div class="btns">
						<input type="submit" id="btn" name="btn" value="Cadastrar">
						<input type="submit" id="btn" name="btn" value="Atualizar">
						<input type="submit" id="btn" name="btn" value="Excluir">
						<input type="submit" id="btn" name="btn" value="Buscar">
					</div>
				</form>
				<br/>
				<form action="sensor" method = "GET">
					<input type="submit" id="btn" name="btn" value="Listar Todos">
				</form>
			</div>
			<br/>
			<c:if test="${(not empty erro) || (not empty res)}">
				<div class="container">
					<c:if test="${not empty erro}">
						<p class="erro">${erro}</p>
					</c:if>
					<c:if test="${not empty res}">
						<p class="sucesso">${res}</p>
					</c:if>
				</div>
			</c:if>
			<div class="container">
				<c:if test="${not empty listaSensores}">
					<div class="">
						<table class = "tbProdutos" border = "1">
							<thead>
								<tr>
									<th>
										Data e hora
									</th>
									<th>
										Temperatura
									</th>
									<th>
										Humidade
									</th>
									<th>
										Pressão
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listaSensores}" var="s">
									<tr>
										<td>
											${s.dateAndtime}
										</td>
										<td>
											${s.temperature}
										</td>
										<td>
											${s.humidity}
										</td>
										<td>
											${s.pressure}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</main>
	</body>
</html>