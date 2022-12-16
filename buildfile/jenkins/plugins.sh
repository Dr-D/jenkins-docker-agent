#!/bin/sh
jenkins-plugin-cli --plugins \
    git:4.14.1 \
    workflow-multibranch:716.vc692a_e52371b_ \
    blueocean:1.25.8 \
    docker-workflow:521.v1a_a_dd2073b_2e \
    docker-plugin:1.2.10

#workflow-aggregator
#pipeline-model-definition
#ldap
#email-ext
#credentials
#cloudbees-bitbucket-branch-source
#bitbucket-pullrequest-builder
#stash-pullrequest-builder
#stashNotifier
#openstack-cloud
