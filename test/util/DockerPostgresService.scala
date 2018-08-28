package util

import com.whisk.docker.{DockerContainer, DockerReadyChecker}

trait DockerPostgresService extends SpotifyDockerKit {

  def PostgresAdvertisedPort = 5432
  def PostgresExposedPort = 44444
  val PostgresUser = "quick-user"
  val PostgresPassword = "password-quick-user"

  val postgresContainer = DockerContainer("postgres:10.5")
    .withPorts((PostgresAdvertisedPort, Some(PostgresExposedPort)))
    .withReadyChecker(DockerReadyChecker.LogLineContains("database is ready to accept connections"))
    .withEnv(s"POSTGRES_USER=$PostgresUser", s"POSTGRES_PASSWORD=$PostgresPassword")


  abstract override def dockerContainers: List[DockerContainer] =
    postgresContainer :: super.dockerContainers

}
