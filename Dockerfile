# ===== ビルド用ステージ =====
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# プロジェクト全体をコピー
COPY . .

# Maven Wrapperを使ってビルド（テストはスキップ）
RUN ./mvnw clean package -DskipTests

# ===== 実行用ステージ =====
FROM eclipse-temurin:21-jdk

WORKDIR /app

# builderステージで作られたJARをコピー
COPY --from=builder /app/target/bookmanager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
