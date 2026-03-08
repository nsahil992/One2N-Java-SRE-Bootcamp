# ---------- BUILD STAGE ----------

FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp dependency:go-offline

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp clean package -DskipTests

# ---------- RUNTIME STAGE ----------

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN useradd -r -u 1001 spring

COPY --from=builder /build/target/*.jar app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]