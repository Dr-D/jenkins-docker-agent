//Docker cloud


import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase
import io.jenkins.docker.connector.DockerComputerSSHConnector
import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy

import jenkins.model.Jenkins

// Required plugins:
// docker-plugin
//

def dockerCloudParameters = [
        name:             'DockerVM',
//This is the virtual box vm ip address
        serverUrl:        'tcp://192.168.56.4:4243',
        credentialsId:    '',
        version:          '1.26',
        connectTimeout:   5,
        readTimeout:      15,
        dockerHostname:   '',
        containerCapStr:  '5'
]

def DockerTemplateParameters = [
        labelString:    'java-jenkins-agent',
        instanceCapStr: '5',
        remoteFs:       '/home/jenkins'
]

def dockerTemplateBaseParameters = [
//This is the ip address of the machine running docker registry
        image:              '192.168.56.1:5000/java-jenkins-agent:latest',
        bindAllPorts:       false,
        bindPorts:          '',
        cpuShares:          null,
        dnsString:          '',
        dockerCommand:      '',
        environmentsString: '',
        extraHostsString:   '',
        hostname:           '',
        macAddress:         '',
        memoryLimit:        null,
        memorySwap:         null,
        network:            '',
        privileged:         true,
        pullCredentialsId:  '',
        sharedMemorySize:   null,
        tty:                false,
        volumesFromString:  '',
        volumeString: '',
//        volumesString:      'media:/media /var/lib/jenkins/workspace/CCACHE/Linux/.ccache/:/usr/src/.ccache'
]

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplateBase.java
//https://javadoc.jenkins.io/plugin/docker-plugin/com/nirima/jenkins/plugins/docker/DockerTemplateBase.html
DockerTemplateBase dockerTemplateBase = new DockerTemplateBase(
        dockerTemplateBaseParameters.image,
        dockerTemplateBaseParameters.pullCredentialsId,
        dockerTemplateBaseParameters.dnsString,
        dockerTemplateBaseParameters.network,
        dockerTemplateBaseParameters.dockerCommand,
        dockerTemplateBaseParameters.volumesString,
        dockerTemplateBaseParameters.volumesFromString,
        dockerTemplateBaseParameters.environmentsString,
        dockerTemplateBaseParameters.hostname,
        "", //user
        "", //Extragroups
        dockerTemplateBaseParameters.memoryLimit,
        dockerTemplateBaseParameters.memorySwap,
        null, //cpuPeriod
        null, //cpuQuota
        dockerTemplateBaseParameters.cpuShares,
        dockerTemplateBaseParameters.sharedMemorySize,
        dockerTemplateBaseParameters.bindPorts,
        dockerTemplateBaseParameters.bindAllPorts,
        dockerTemplateBaseParameters.privileged,
        dockerTemplateBaseParameters.tty,
        dockerTemplateBaseParameters.macAddress,
        dockerTemplateBaseParameters.extraHostsString
)

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplate.java
NonVerifyingKeyVerificationStrategy verificationStrategy = new NonVerifyingKeyVerificationStrategy()
DockerComputerSSHConnector.ManuallyConfiguredSSHKey manConfigSSHKey =
        new DockerComputerSSHConnector.ManuallyConfiguredSSHKey('jenkins-docker-credentials', verificationStrategy)
DockerTemplate dockerTemplate = new DockerTemplate(
        dockerTemplateBase,
        new DockerComputerSSHConnector(manConfigSSHKey),
        DockerTemplateParameters.labelString,
        DockerTemplateParameters.remoteFs,
        DockerTemplateParameters.instanceCapStr
)

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerCloud.java
DockerCloud dockerCloud = new DockerCloud(
        dockerCloudParameters.name,
        [dockerTemplate],
        dockerCloudParameters.serverUrl,
        dockerCloudParameters.containerCapStr,
        dockerCloudParameters.connectTimeout,
        dockerCloudParameters.readTimeout,
        dockerCloudParameters.credentialsId,
        dockerCloudParameters.version,
        dockerCloudParameters.dockerHostname
)

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// add cloud configuration to Jenkins
jenkins.clouds.add(dockerCloud)

// save current Jenkins state to disk
jenkins.save()