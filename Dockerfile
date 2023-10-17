FROM docker/dev-environments-javascript:stable-1

# Update OS
RUN apt-get update --allow-unauthenticated --allow-insecure-repositories

# Downgrade nodejs to 12.x
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
RUN sudo apt -y install curl dirmngr apt-transport-https lsb-release ca-certificates
RUN wget -q -O - https://deb.nodesource.com/gpgkey/nodesource.gpg.key | sudo apt-key add -
RUN apt-get update --allow-unauthenticated --allow-insecure-repositories 
RUN sudo apt-key adv --refresh-keys --keyserver keyserver.ubuntu.com
RUN rm -rf /etc/apt/sources.list.d/yarn.list
RUN sudo apt-get remove nodejs npm
RUN sudo rm -rf /usr/local/bin/node*
RUN sudo rm -rf /usr/local/bin/npm*
RUN sudo rm -rf /etc/apt/sources.list.d/nodesource.list
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
RUN sudo apt -y install nodejs
RUN sudo apt -y install gcc g++ make
RUN node --version
RUN npm --version

# Install Java 17
RUN apt-get update --allow-unauthenticated --allow-insecure-repositories
RUN apt-get install -y openjdk-17-jdk
RUN sudo apt-get install -y maven

# Install Spring Boot
# RUN mkdir /opt/spring-boot
# WORKDIR /opt/spring-boot
# RUN wget https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.5.5/spring-boot-cli-2.5.5-bin.tar.gz
# RUN tar -xzf spring-boot-cli-2.5.5-bin.tar.gz
# RUN ln -s /opt/spring-boot/spring-2.5.5 /usr/local/bin/spring

# Install dependencies
RUN sudo apt install -y libcups2-dev

# Install package
RUN sudo apt-get update
RUN sudo apt-get -y install chromium

RUN echo " --------------------------------------- "
RUN echo "*** INITIALISATION TERMINER ***"
RUN echo " --------------------------------------- "

ENTRYPOINT ["sleep", "infinity"]