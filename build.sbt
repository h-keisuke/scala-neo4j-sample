lazy val skinnyMicroVersion = "1.2.7"
lazy val jettyVersion = "9.4.5.v20170502"

lazy val root = (project in file("."))
    .settings(
      name := "scala-neo4j",
      version := "0.1",
      scalaVersion := "2.12.3",
      resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositorie/releases",
//      resolvers += "anormcypher" at "http://repo.anormcypher.org/",
      libraryDependencies ++= Seq(
        // micro Web framework
        "org.skinny-framework" %% "skinny-micro"             % skinnyMicroVersion,
        // json4s integration
        "org.skinny-framework" %% "skinny-micro-json4s"      % skinnyMicroVersion,
        // Standalone Web server (Jetty 9.3 / Servlet 3.1)
        "org.skinny-framework" %% "skinny-micro-server"      % skinnyMicroVersion,
        "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container",
        "org.eclipse.jetty" % "jetty-plus"   % jettyVersion % "container",
//        "org.anormcypher" %% "anormcypher" % "0.6.0"
      )
    )
    .settings(servletSettings)

