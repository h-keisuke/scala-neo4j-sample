package scala_neo4j.controller.api.v1

import org.json4s.jackson.Serialization.write
import skinny.micro.WebApp
import skinny.micro.contrib.json4s.JSONSupport
import skinny.micro.response.{Created, Ok}

import scala_neo4j.resource.TaskResource

object TaskController extends WebApp with JSONSupport{

  get("/api/v1/tasks/:id"){
    val id = params.getAs[Long]("id")
    Ok(
      responseAsJSON(TaskResource(id.getOrElse(0), "たすく", "たすくのなかみ")),
      Map("Content-Type" -> "application/json")
    )
  }

  get("/api/v1/tasks"){
    val id = params.getAs[Long]("id")
    Ok(
      responseAsJSON(TaskResource(id.getOrElse(0), "たすく", "たすくのなかみ")),
      Map("Content-Type" -> "application/json")
    )
  }

  post("/api/v1/tasks"){
    val title = params.get("title")
    val description = params.get("description")
    Created(
      write(TaskResource(2, title.getOrElse(""), description.getOrElse(""))),
      Map("Content-Type" -> "application/json")
    )
  }

}
