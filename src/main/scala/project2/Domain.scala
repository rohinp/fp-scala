package project2

import java.util.{Date, UUID}

sealed trait TODOAppError

case object InvalidTagError extends TODOAppError

sealed trait Status

case object Pending extends Status
case object Done extends Status
case class Hold(reason:String) extends Status

case class Tag private (tag: String)

object Tag {
  def create(tag: String): Either[TODOAppError, Tag] = {
    //not more than 20 chars
    // no space allowed
    // only alphabets and numbers allowed
    //must start with a alphabet
    ???
  }
}

case class Task(
    UUID: UUID,
    msg: String,
    tag: List[Tag],
    completed: Status,
    dueDate: Option[Date]
)

object Task {
  def create(
      msg: String,
      tag: List[Tag] = List(),
      completed: Status = Pending,
      dueDate: Option[Date] = None
  ): Either[TODOAppError, Task] = {
    //msg can't be empty, can contain any ASSCI chars
    //msg can't be more than 200 chars
    //dueDate cant be in past, while creating the task
    ???
  }
}

case class TaskList(UUID: UUID, title: String, tasks: List[Task])

object TaskList {
  def create(
      title: String,
      tasks: List[Task] = List()
  ): Either[TODOAppError, Task] = {
    //title needs to be validated for length of 50 chars
    //no special chars
    //Title are unique
    ???
  }
}
