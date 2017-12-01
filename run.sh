docker-compose up -d
docker-compose exec eureka ./gradlew clean bootRun -d
docker-compose exec api ./gradlew clean bootRun -d
docker-compose exec auth ./gradlew clean bootRun -d
docker-compose exec judge ./gradlew clean bootRun -d
docker-compose exec problems ./gradlew clean bootRun -d
docker-compose exec submissions ./gradlew clean bootRun -d
docker-compose exec ui ./gradlew clean bootRun -d
