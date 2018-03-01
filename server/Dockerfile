FROM openjdk:7

COPY src src

RUN mkdir build && javac -encoding utf8 -d . src/**/*.java

RUN mkdir logs

EXPOSE 6020

CMD ["java", "moteur.Appli"]
