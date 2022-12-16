import jenkins.*
import hudson.*
import hudson.model.*
import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*

// Required plugins:
// (none)
//

// SECURITY WARNING: Dirty way to get secret
// FOR DEVELOPMENT ONLY
// NOTE: For real use, there should be proper secret management.

// Variables
credentialId="jenkins-docker-credentials"
credentialDescription="Credentials for docker agent"
credentialUser="jenkins"
credentialPassword="jenkins"

def instance = Jenkins.getInstance()

global_domain = Domain.global()
credentials_store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

credentials = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        credentialId,
        credentialDescription,
        credentialUser,
        credentialPassword)

credentials_store.addCredentials(global_domain, credentials)
println "--> Added credential: '$credentialId'"
