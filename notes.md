https://codewiz.info/blog/graphql-basics-java-spring/

# Pour lancer proprement depuis la racine du projet:

```shell
sdk use java 17.0.2-open
```

export WEATHER_API_KEY=ta_cle_openweather
mvn spring-boot:run
Si tu veux aussi surcharger l’URL météo, par exemple plus tard avec WireMock:

export WEATHER_API_BASE_URL=http://localhost:8089
La base PostgreSQL doit être disponible aussi. Comme le projet a compose.yaml / docker-compose.yml, tu peux d’abord lancer:

docker compose up -d
Puis:

sdk use java 17.0.2-open
export WEATHER_API_KEY=ta_cle_openweather
mvn spring-boot:run
Ensuite l’app devrait être accessible sur:

http://localhost:8080
http://localhost:8080/graphiql
Pour les tests:

sdk use java 17.0.2-open
mvn test
À noter: sans WEATHER_API_KEY, les requêtes GraphQL qui demandent venue.weather risquent d’échouer car elles appellent OpenWeather.