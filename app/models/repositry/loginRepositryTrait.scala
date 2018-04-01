package models.repositry


import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.driver.JdbcProfile
//import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global


case class UserLoginPageInfo(username: String,password: String)
trait loginRepositryTrait extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  class UserLoginProfile(tag: Tag) extends Table[UserLoginPageInfo](tag, "user_profile") {


    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
      //def fname: Rep[String] = column[String]("fname")
    def username: Rep[String] = column[String]("username")

    def password: Rep[String] = column[String]("password")


    def * = (username, password) <>(UserLoginPageInfo.tupled, UserLoginPageInfo.unapply)

    // def * = (id,fname, lname,email) <>(UserInfo.tupled,UserInfo.unapply)
  }



  val userProfileQuery = TableQuery[UserLoginProfile]

}


trait UserLoginRepoTrait {
 // def store(userProfile: UserInfo): Future[Boolean]
  def fetchByEmail(username: String): Future[String]

}

trait UserLoginTraitImplementation extends UserRepoTrait {
  self: loginRepositryTrait =>

  import profile.api._

//  def store(userProfile: UserInfo): Future[Boolean] =
//  {
//    db.run(userProfileQuery += userProfile) map(_>0)
//
//    // Future(true)
//  }

//  def fetchByEmail(userId: String): Future[Option[String]] = {
//    db.run(userProfileQuery.filter(_.username === userId).result)
//  }
//
//  def findBfetchByEmailyEmail(email: String): Future[Option[Profile]] = {
//    val queryResult = userProfileQuery.filter(_.email.toLowerCase === email.toLowerCase).result.headOption
//    db.run(queryResult)
//  }


}

