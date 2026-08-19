# Используем образ Gradle для сборки
FROM gradle:8.10-jdk17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем только необходимые файлы для кэширования зависимостей
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Скачиваем зависимости (этот слой будет закэширован)
RUN gradle build --no-daemon --stacktrace || return 0

# Копируем исходный код
COPY src ./src

# Собираем проект
RUN gradle build --no-daemon --stacktrace

# Используем минимальный образ для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Открываем порт (обычно 8080 для Spring Boot)
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
