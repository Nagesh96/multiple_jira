// Capture and store the uploaded artifact's URL
            def uploadedArtifact = buildInfo.getDeployableArtifacts().get(0)
            def artifactUrl = uploadedArtifact.getDeployableArtifact().getRemotePath()
            env.ARTIFACTORY_URL = "${server.getUrl()}/${artifactUrl}"
        }
    }
}
