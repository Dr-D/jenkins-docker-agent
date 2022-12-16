#Jenkins running docker agents

Jenkins can run a pipeline in a docker agent.
Jenkins creates the container and removes it after the build.

##The setup
The docker agent is going to run in a local virtual machine which is started using [vagrant](https://www.vagrantup.com/).
The virutal machine has docker installed and we open a socket to allow jenkins to create the agent as a container in docker.

The host machine will run jenkins, we create up updated docker image of jenkins.
We use jenkins init.d.groovy scripts to automate the jenkins setup.
The host machine also runs docker registry that contains a image of the docker agent. 

Docker agent is a docker image that we create, you can see what is installed in the dockerfile.

You will need some experience of using docker and be able to install vagrant and virtualbox to run the virtual machine.

##Trouble Shooting
The most important things to get right are the host only ip addresses in the Vagrantfile and dockerCloud.groovy.  
You can use the jenkins logs:
- jenkins may fail to connect to the virtual machine  
This is can be fixed manually in jenkins by updating cloud settings. 
- docker on the virtual machine may have an issue connecting to the registry on the host machine.  
This can be fixed by editing the /etc/docker/daemon.json file on the virtual machine.

In Jenkins
**Unexpected exception encountered while provisioning agent Image of 192.168.56.1:5000/java-jenkins-agent:latest com.github.dockerjava.api.exception.InternalServerErrorException: Status 500: {"message":"Get \"https://192.168.56.1:5000/v2/\": dial tcp 192.168.56.1:5000: connect: connection refused"}**

On command line running:  
docker pull 192.168.56.1/docker-jenkins-agent:latest
**Error response from daemon: Get "https://192.168.56.1/v2/": x509: cannot validate certificate for 192.168.56.1 because it doesn't contain any IP SANs**
Not sure what was causing this insecure registers were set in docker correctly. Restarted registry and restarted docker on the host something seemed to get this working. The alternate was to set up the registry in the vm which can also work.

Virtualbox was not running ubuntu22.04 - virtualbox needed to be updated and then I had to update vagrant.
