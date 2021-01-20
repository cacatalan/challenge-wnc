# CHALLENGE-WNC by Agustin Catalan

La api en cuestion presenta y expone de manera publica los siguientes servicios:

GET/prices
-
	obtiene la lista de precios guardada en la base de datos temporal h2 en un array<Price>.
	METODO DE PRUEBA - NO PEDIDO EN CHALLENGE
	
GET/prices/{id}
-
	obtiene un objeto precio guardado en la base de datos temporal h2 con el id enviado como parametro en el path.
	METODO DE PRUEBA - NO PEDIDO EN CHALLENGE
	
POST/prices_by_timestamp
-
	obtiene un objeto precio guardado en la base de datos temporal h2 con el timestamp enviado como parametro en el body a travez del objeto LastPriceByTimestampRequest.
	
POST/timestamp_average
-
	obtiene un objeto de respuesta que contrendra 3 valores los cuales seran:
	- max_value: maximo precio guardado en la lista de precios.
	- average: promedio entre max_value y promedio de precios entre los 2 timestamp ingresados como parametros en el body a travez del objeto TimestampAverageRequest.
	- difference: diferencia porcentual entre max_value y average.
	
POST/load_prices
- 
	consume el cliente GET/https://cex.io/api/last_prices/BTC/USD cada 10 segundos, guarda en base de datos temporal h2 el objeto precio obtenido, incluyendo el timestamp actual en obtener el dato. (se repite 6 veces - 1 minutos aprox.)
	
	
# INSTRUCCIONES

Compilar y ejecutar tests
-
	cd challenge-wnc/
	mvn clean install

Correr aplicación local
-
	cd challenge-wnc/target/
	java -jar challengewnc-0.0.1-SNAPSHOT.jar
