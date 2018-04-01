package models.repositry

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

class AssignmentRep {

}

case class AssignmentData(id: Int, title: String, description: String)

trait assignmentRepositryTrait extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class AssignmentProfile(tag: Tag) extends Table[AssignmentData](tag, "assignment_table") {


    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def title: Rep[String] = column[String]("title")


    def description: Rep[String] = column[String]("description")





    def * = {
      (id,title, description) <>
      (AssignmentData.tupled, AssignmentData.unapply)
    }

    // def * = (id,fname, lname,email) <>(UserInfo.tupled,UserInfo.unapply)
  }


  val assignmentQuery = TableQuery[AssignmentProfile]

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
    } yield user

    db.run(joinQuery.to[List].result)
  }

  def deleteAssignment(id : Int) : Future[Boolean] = {
    //db.run(userProfileQuery.filter(_.username.toLowerCase == user.toLowerCase).map(user => user.isEnabled)
    //  .update(!status)) map(_>0)


    db.run(assignmentQuery.filter(_.id === id).delete) map ( _ > 0)
     // .update(!status)) map (_ > 0)



  }



}