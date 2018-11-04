# Instructions

You can start a local jenkins server by running the following command:
```
docker run -u root --rm -p 8080:8080 -v jenkins-data:/var/jenkins_home  -v /var/run/docker.sock:/var/run/docker.sock  jenkinsci/blueocean
```

