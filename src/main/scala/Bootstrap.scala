import javax.servlet._

import skinny.micro._

import scala_neo4j.controller.api.v1.TaskController

class Bootstrap extends LifeCycle {
  override def init(ctx: ServletContext){
    TaskController.mount(ctx)
  }
}

