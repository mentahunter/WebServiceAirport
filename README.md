docker-compose up -d </br>
port: 5000 </br>

Airplane endpoint: </br>
api/airplanes GET - no params </br>
api/airplanes/id GET - in url - id </br>
api/airplanes POST - in body - model(string), capacity(int) </br>
api/airplanes/id PUT - in url - id - in body - model(string), capacity(int) </br>
api/airplanes/id DELETE - in url - id </br>

Schedules endpoint: </br>

api/schedules GET - no params </br>
api/schedules/id GET - in url - id </br>
api/schedules POST - in body - from(string), to(string), airplaneId(int) </br>
api/schedules/id PUT - in url - id - in body - from(string), to(string), airplaneId(int) </br>
api/schedules/id DELETE - in url - id </br>

