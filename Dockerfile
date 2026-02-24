FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar -Dorg.gradle.dependency.verification=off


FROM eclipse-temurin:21-jre-alpine AS runner

ARG USER_NAME=adproshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} \
    && adduser -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}

WORKDIR /opt/app

COPY --from=builder --chown=${USER_UID}:${USER_GID} \
    /src/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]