#Virtual box guest
An example of a groovy script for using docker containers as agents
https://devopscube.com/docker-containers-as-build-slaves-jenkins/

Here we are creating a virtual machine so that Jenkins Server has to connect to 
another machine to deploy the jenkins agents as containers. 
Create the virtual machine first, as we will need the ip address of this machine for jenkins.
You will need to install vagrant to use the Vagrantfile.

## VM provisioning
The vm is provisioned with docker, via shell script that adds user drd with docker access.  
Also adds an /etc/docker/daemon.json file with insecure-registries.
```json
"insecure-registries": ["192.168.33.3:5000"]
```
Make sure the ip address is correct, this needs to be the **host ip** or the machine that is running docker registry.
Needs to be a machine that the guest can connect to, to retrieve the image for the docker agent.

## Start vm in virtual box to get host ip
Use vagrant to start up vm with the Vagrantfile, should just be able to run this from the vm directory:
```shell
vagrant up
```
It is usually easier to have a bridged network for the vm so should have local ip e.g. ip 192.168.0.79
This sas not working on MacOs connected to a wireless network, so added host only network instead. e.g. 192.168.33.3
Check the ip address as MacOs host does not allow specific ip address for host only network.
The nat adapter created by vagrant gives internet access.
The host only network adapter gives access to host machine running jenkins.

## Remove VM and restart new VM if using host only network
The host ip address for the host only network will only be available after the virtual machine is started in vagrant for the first time.  
So you will need to start the machine, get the new host ip address for the host only network. Do a vagrant destroy. Update the shell script in the Vagrantfile and do a vagrant up to start and provision a new vm.
