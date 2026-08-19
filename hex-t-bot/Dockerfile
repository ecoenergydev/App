# Этап сборки
FROM gradle:8.14-jdk21 AS build

WORKDIR /app

# Копируем файлы (уже внутри hex-t-bot)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Загружаем зависимости
RUN gradle build --no-daemon --stacktrace || return 0

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN gradle build --no-daemon --stacktrace -x test

# Этап запуска
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Копируем JAR из этапа сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
