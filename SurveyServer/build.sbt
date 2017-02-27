import java.nio.file.{Files, Path, Paths}

/*
This file is part of Intake24.

Copyright 2015, 2016, 2017 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

name := "survey-frontend-server"

organization := "uk.ac.ncl.openlab.intake24"

version := "3.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val copyGWTJavaScript = TaskKey[Unit]("Copy GWT compiled JavaScript files")

lazy val GWTJavaScriptSources = TaskKey[Path]("Path to GWT compiled JavaScript files")

lazy val root = (project in file(".")).enablePlugins(PlayScala, SystemdPlugin)

.settings(
  copyGWTJavaScript <<= target.map {
    target =>
      println("ajaja")
      println(target.getPath)
    //Files.copy(GWTJavaScriptSources, Paths.get(target.getPath,))
  },
  compile <<= (compile in Compile).dependsOn(copyGWTJavaScript)
)

//copyRes <<= (baseDirectory, target) map {
//  (base, trg) => new File(base, "src/html").listFiles().foreach(
//    file => Files.copy(file.toPath, new File(trg, file.name).toPath)
//  )

//)