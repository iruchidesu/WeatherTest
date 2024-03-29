Тестовое задание Получение данных о погоде через API

REST API реализация получения данных о среднесуточной температуре за указанный период времени.

### Стек технологий

* Spring Boot
* Spring Data JPA(Hibernate)
* REST(Jackson)
* JUnit

### Ключевая логика работы

* Запрос к сервису pogoda.atpm-air.ru с указанием двух дат, начала и конца периода
* Результатом запроса является среднесуточная температура за указанный период
* Результат сохраняется в реляционной СУБД H2, БД хранится в оперативной памяти

### API documentation:

* POST http://localhost:8080/rest/weather?startDate={startDate}&endDate={endDate} (запрос к погодному сервису за
  данными. startDate - начало периода, endDate - конец периода (не включая))
* GET http://localhost:8080/rest/weather (возвращает все результаты предыдущих запросов)

### Curl комманды для тестирования API

#### Сделать запрос к сервису для получения данных за период c 23.08.2021 по 24.08.2021

`curl -s -i -X POST http://localhost:8080/rest/weather?startDate=2021-08-23&endDate=2021-08-25`

#### Получить результаты предыдущих запросов

`curl -s http://localhost:8080/rest/weather`