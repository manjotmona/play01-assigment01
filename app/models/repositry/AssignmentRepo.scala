package models.repositry

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape

case class AssignmentData(id: Int, title: String, description: String)

trait assignmentRepositryTrait extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val assignmentQuery = TableQuery[AssignmentProfile]

  class AssignmentProfile(tag: Tag) extends Table[AssignmentData](tag, "assignment_table") {


    def * : ProvenShape[AssignmentData] = {
      (id, title, description) <>
      (AssignmentData.tupled, AssignmentData.unapply)
    }

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title")

    def description: Rep[String] = column[String]("description")

  }

}


trait AssigmentRepoTrait {
  def add(assignmentDetail: AssignmentData): Future[Boolean]

}

trait AssgnmentTraitImplementation extends AssigmentRepoTrait {
  self: assignmentRepositryTrait =>

  import profile.api._

  def add(newAssignment: AssignmentData): Future[Boolean] = {
    db.run(assignmentQuery += newAssignment) map (_ > 0)

  }

  def getAllAssignment: Future[List[AssignmentData]] = {
    val joinQuery = for {
      user <- assignmentQuery
    } yield {
      user
    }

    db.run(joinQuery.to[List].result)
  }

  def deleteAssignment(id: Int): Future[Boolean] = {

    db.run(assignmentQuery.filter(_.id === id).delete) map (_ > 0)
  }
}
