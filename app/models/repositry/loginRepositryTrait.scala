package models.repositry


import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape



case class UserLoginPageInfo(username: String, password: String)

trait loginRepositryTrait extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val userProfileQuery = TableQuery[UserLoginProfile]

  class UserLoginProfile(tag: Tag) extends Table[UserLoginPageInfo](tag, "user_profile") {


    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def * : ProvenShape[UserLoginPageInfo] = (username, password) <> (UserLoginPageInfo.tupled, UserLoginPageInfo.unapply)


    def username: Rep[String] = {
      column[String]("username")
    }

    def password: Rep[String] = column[String]("password")

  }

}


trait UserLoginRepoTrait {

  def fetchByEmail(username: String): Future[String]

}

trait UserLoginTraitImplementation extends UserRepoTrait {
  self: loginRepositryTrait =>

}

