package scala_neo4j.service

import org.neo4j.driver.v1._
import org.neo4j.driver.v1.Values.parameters
import org.neo4j.driver.v1.exceptions.NoSuchRecordException

import scala_neo4j.resource.TaskResource
import collection.JavaConverters._

object TaskService {

  val graphenedbURL: String = sys.env("GRAPHENEDB_BOLT_URL")
  val graphenedbUser: String = sys.env("GRAPHENEDB_BOLT_USER")
  val graphenedbPass: String = sys.env("GRAPHENEDB_BOLT_PASSWORD")
  val driver: Driver = GraphDatabase.driver(graphenedbURL, AuthTokens.basic(graphenedbUser, graphenedbPass))

  def create(title: String, description: String): TaskResource = {

    val session = driver.session
    try {
      val newRecord = session.run(s"CREATE (task:Task {title: {title}, description: {description} }) " +
        s"RETURN id(task) as nodeId, task.title as title, task.description as description",
        parameters("title", title, "description", description)
      ).single()
      TaskResource(newRecord.get("nodeId", 0), newRecord.get("title", ""), newRecord.get("description", ""))
    } finally {
      session.close()
    }
  }

  def findAll(): List[TaskResource] = {
    val session = driver.session(AccessMode.READ)
    try {
      session.run(s"MATCH (task:Task) RETURN id(task) as nodeId, task.title as title, task.description as description")
        .list(record => TaskResource(record.get("nodeId", 0), record.get("title", ""), record.get("description", "")))
        .asScala
        .toList
    }finally {
      session.close()
    }
  }

  def findById(id: Long): Option[TaskResource] = {
    val session = driver.session(AccessMode.READ)
    try{
      val record = session.run(s"MATCH (task:Task) WHERE id(task)=$id RETURN id(task) as nodeId, task.title as title, task.description as description")
          .single()
      Some(TaskResource(record.get("nodeId", 0), record.get("title", ""), record.get("description", "")))
    }catch {
      case _: NoSuchRecordException => None
    }finally {
      session.close()
    }
  }
}
