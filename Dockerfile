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

# Khởi động redis-server (15MB RAM) và Java tối ưu hóa cho trần 512MB RAM của Render:
# -Xms64m -Xmx160m: Giữ Heap an toàn dưới 160MB
# -Xss384k: Giảm thread stack footprint
# -XX:MaxMetaspaceSize=140m: Tránh Metaspace phình to
# -XX:TieredStopAtLevel=1: Biên dịch JIT cấp 1, boot nhanh gấp 2 lần, giảm CPU/RAM lúc boot
# -XX:+UseSerialGC: GC ít tốn RAM overhead nhất
ENTRYPOINT ["sh", "-c", "redis-server --daemonize yes --dir /tmp --logfile \"\" --maxmemory 15mb --maxmemory-policy allkeys-lru ; exec java -Xms64m -Xmx160m -Xss384k -XX:MaxMetaspaceSize=140m -XX:TieredStopAtLevel=1 -XX:+UseSerialGC -jar app.jar"]
