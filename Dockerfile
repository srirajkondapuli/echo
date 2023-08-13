# FROM ghcr.io/graalvm/jdk:ol8-java17-22.3.1

# ARG EXECUTABLE_FILE=build/native/nativeCompile/echo
# WORKDIR /app
# COPY ${EXECUTABLE_FILE} echo
# ENTRYPOINT ["/app/echo"]
# EXPOSE 8000

# FROM ghcr.io/graalvm/native-image:ol8-java17-22 AS builder
# FROM ghcr.io/graalvm/jdk:ol8-java17-22.3.1 as builder
FROM ghcr.io/graalvm/graalvm-ce:22.3.1 as builder

# Install tar and gzip to extract the Maven binaries
# RUN microdnf update \
#  && microdnf upgrade \
#  && microdnf install --nodocs \
#     tar \
#     gzip \
#     unzip \
#     which \
#     findutils \
#  && microdnf clean all 



RUN microdnf -y install --nodocs \
    unzip \
    findutils \
 && microdnf clean all 

# Install Maven
# Source:
# 1) https://github.com/carlossg/docker-maven/blob/925e49a1d0986070208e3c06a11c41f8f2cada82/openjdk-17/Dockerfile
# 2) https://maven.apache.org/download.cgi
ARG USER_HOME_DIR="/root"

ARG GRADLE_DOWNLOAD_URL=https://services.gradle.org/distributions/gradle-8.0.2-bin.zip

RUN mkdir -p /opt/gradle \
  && curl -fsSL -o /tmp/gradle.zip ${GRADLE_DOWNLOAD_URL} \
  && unzip /tmp/gradle.zip -d /opt/gradle/ \
  && rm -f /tmp/gradle.zip


ENV GRADLE_HOME=/opt/gradle/gradle-8.0.2

#ENV JAVA_HOME=/opt/graalvm-ce-java17-22.3.1
ENV JAVA_HOME=/opt/graalvm-ce-java19-22.3.1

ENV PATH=/opt/gradle/gradle-8.0.2/bin:/opt/graalvm-ce-java19-22.3.1/bin:$PATH
#ENV PATH=/opt/gradle/gradle-8.0.2/bin:/opt/graalvm-ce-java17-22.3.1/bin:$PATH

# Set the working directory to /home/app
WORKDIR /app

# Copy the source code into the image for building
COPY . /app

RUN cd /app

# # Build
RUN /opt/gradle/gradle-8.0.2/bin/gradle clean nativeCompile



# # The deployment Image
# FROM ghcr.io/graalvm/native-image:ol8-java17-22
FROM ghcr.io/graalvm/graalvm-ce:22.3.1
EXPOSE 8000

# # # Copy the native executable into the containers
COPY --from=builder /app/build/native/nativeCompile/echo /app
# ENTRYPOINT ["/app/build/native/nativeCompile/echo"]
ENTRYPOINT ["/app/echo"]
# CMD ["--version"]

