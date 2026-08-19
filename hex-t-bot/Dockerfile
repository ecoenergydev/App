# Этап сборки
FROM gradle:8.10-jdk17 AS build

WORKDIR /app

# Копируем файлы из подпапки hex-t-bot
COPY hex-t-bot/build.gradle hex-t-bot/settings.gradle ./
COPY hex-t-bot/gradle ./gradle

# Загружаем зависимости
RUN gradle build --no-daemon --stacktrace || return 0

# Копируем исходный код
COPY hex-t-bot/src ./src

# Собираем приложение
RUN gradle build --no-daemon --stacktrace

# Этап запуска
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Копируем JAR из этапа сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
