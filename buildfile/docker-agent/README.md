#Create docker agent image
When running a pipeline in jenkins we an select an agent to run the pipeline on.
In this case we are creating an image with openssh-server, so that jenkins can run commands within the agent.

From the buildfile/docker-agent/ directory run:
```shell
docker build ./ -t localhost:5000/java-jenkins-agent 
```
The image will be created with latest tag.

## Docker Registry running on host
```shell
docker run -d -p 5000:5000 --name registry registry:2  
```
If running from default registry image, this will be an insecure registry.

## Push image to registry on host
docker push localhost:5000/java-jenkins-agent
