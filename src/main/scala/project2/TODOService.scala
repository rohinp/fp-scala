package project2

trait TODOService[F[_], Repo, Title, Message, TaskID] {
  def createTaskList:Title => Repo => F[Either[TODOAppError,TaskList]]
  def addTask():TaskList => Task => Repo => F[Either[TODOAppError,TaskList]]
  def tagIt:TaskList => Task => List[Tag] => Repo => F[Either[TODOAppError,Task]]
  def completeTask:TaskList => Task =>  Repo => F[Either[TODOAppError,Task]]
  def onHoldTask:TaskList => Task => Message =>  Repo => F[Either[TODOAppError,TaskList]]
  def deleteTask():TaskList => TaskID =>  Repo => F[Either[TODOAppError,TaskList]]
  def listTasks:TaskList =>  Repo => F[Either[TODOAppError,List[Task]]]
  def listTaskList: Repo => F[Either[TODOAppError,List[TaskList]]]
}
