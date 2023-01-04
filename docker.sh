sudo docker build -t myproject . --no-cache

sudo docker tag <dockerImage> kevin/myproject

sudo docker run -p 8080:8080 myproject