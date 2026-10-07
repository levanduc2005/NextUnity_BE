# Giai đoạn 1: biên dịch và đóng gói ứng dụng
FROM maven:3.9.9-eclipse-temurin-23-alpine AS build
WORKDIR /app

# Copy pom vào và tải các dependencies về trước
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy code, dọn dẹp sạch sẽ thư mục target, đóng gói thành file .jar, bỏ qua test
COPY src ./src
RUN mvn clean package -DskipTests -B

# =====================================================
# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:23-jre-alpine AS app
WORKDIR /app

# Cài đặt redis vào image alpine
RUN apk add --no-cache redis

# Tạo user để tránh dùng user root toàn quyền, bảo mật
RUN addgroup -S spring && adduser -S spring -G spring 
USER spring:spring

# copy file .jar đã biên dịch và đổi tên app.jar
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8000

# Khởi động redis-server (24MB RAM) và Java với các cờ tối ưu hóa nghiêm ngặt cho giới hạn 512MB RAM của Render
ENTRYPOINT ["sh", "-c", "redis-server --daemonize yes --dir /tmp --maxmemory 24mb --maxmemory-policy allkeys-lru && exec java -Xms128m -Xmx256m -Xss512k -XX:MaxMetaspaceSize=110m -XX:+UseSerialGC -jar app.jar"]
