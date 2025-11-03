# Java 17ベースの軽量イメージを使用
FROM openjdk:17-jdk-slim

# 作業ディレクトリを設定
WORKDIR /app

# プロジェクトをコンテナにコピー
COPY . .

# Maven Wrapperに実行権限を付与
RUN chmod +x ./mvnw

# アプリをビルド（テストはスキップ）
RUN ./mvnw clean package -DskipTests

# アプリを起動（jarファイル名を指定）
CMD ["java", "-jar", "target/bookmanager-0.0.1-SNAPSHOT.jar"]
