# README

# Testertool

#### Install

```shell
sudo yum install java-1.8.0-openjdk.x86_64
sudo yum install nginx
sudo yum install maven


```

#### Configuration

```shell
# /etc/systemd/system/testertool.service
# testertool.service
[Unit]
Description=My Service
After=syslog.target

[Service]
ExecStart=/usr/bin/java -jar /data/testertool/target/testertool.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

#### Nginx

```nginx
# nginx
   server {
        listen       80 default_server;
        listen       [::]:80 default_server;
        server_name  testertool.com www.testertool.com;
        root         /data/testertool;

        # Load configuration files for the default server block.
        # include /etc/nginx/default.d/*.conf;

        location / {
            proxy_pass http://127.0.0.1:8080;
        }

        error_page 404 /404.html;
            location = /40x.html {
        }

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
        }
    }

```

#### Run
```shell
mvn clean package
mv target/testertool-1.0-SNAPSHOT.jar target/testertool.jar

sudo systemctl start testertool
```

