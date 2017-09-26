package scala_neo4j.controller.api.v1

import skinny.micro.WebApp
import skinny.micro.contrib.json4s.{JSONParamsAutoBinderSupport, JSONSupport}
import skinny.micro.response.{BadRequest, Created, NotFound, Ok}

import scala_neo4j.service.TaskService

object TaskController extends WebApp with JSONSupport with JSONParamsAutoBinderSupport{

  get("/api/v1/tasks/:id"){
    params.getAs[Long]("id") match {
      case Some(id) => TaskService.findById(id) match {
        case Some(t) => Ok(responseAsJSON(t), Map("Content-Type" -> "application/json"))
        case None => NotFound
      }
      case None => NotFound
    }
  }

  get("/api/v1/tasks"){
    Ok(
      responseAsJSON(TaskService.findAll()),
      Map("Content-Type" -> "application/json")
    )
  }

  post("/api/v1/tasks"){
    params.getAs[String]("title") match {
      case Some(t) => params.getAs[String]("description") match {
        case Some(d) => Created(responseAsJSON(TaskService.create(t, d)),Map("Content-Type" -> "application/json"))
        case None => BadRequest
      }
      case None => BadRequest
    }
  }

}
