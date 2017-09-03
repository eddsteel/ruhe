maintainer in Docker := "Edd Steel <edward.steel@gmail.com>"
packageName in Docker := "eddsteel/ruhe"
packageSummary in Docker := "An animation"
packageDescription in ThisBuild := "An animation"
dockerExposedPorts in ThisBuild := List(8081)
dockerBaseImage in ThisBuild := "frolvlad/alpine-scala"
dockerUpdateLatest in ThisBuild := true
