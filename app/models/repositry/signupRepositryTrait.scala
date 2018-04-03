package models.repositry

import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

import slick.lifted.ProvenShape



case class UserSignupInfo(fname: String,
    mname: Option[String],
    lname: String,
    username: String,
    password: String,
    confirmPassword: String,
    mobile: String,
    gender: String,
    age: Int,
    hobby: String,
    isAdmin: Boolean,
    isEnabled: Boolean)

trait signupRepositryTrait extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class UserProfile(tag: Tag) extends Table[UserSignupInfo](tag, "user_profile") {


    // def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def fname: Rep[String] = {
      column[String]("fname")
    }

    def mname: Rep[Option[String]] = column[Option[String]]("mname")

    def lname: Rep[String] = column[String]("lname")

    def username: Rep[String] = column[String]("username")

    def password: Rep[String] = column[String]("password")

    def confirmPassword: Rep[String] = column[String]("confirmPassword")

    def mobile: Rep[String] = column[String]("mobile")

    def gender: Rep[String] = column[String]("gender")

    def age: Rep[Int] = column[Int]("age")

    def hobby: Rep[String] = column[String]("hobby")

    def isAdmin: Rep[Boolean] = column[Boolean]("isAdmin")
    def isEnabled: Rep[Boolean] = column[Boolean]("isEnabled")



    def * : ProvenShape[UserSignupInfo] = {
      (fname, mname, lname, username, password, confirmPassword, mobile, gender, age, hobby,isAdmin,isEnabled) <>
      (UserSignupInfo.tupled, UserSignupInfo.unapply)
    }

    // def * = (id,fname, lname,email) <>(UserInfo.tupled,UserInfo.unapply)
  }


  val userProfileQuery = TableQuery[UserProfile]

}


trait UserRepoTrait {
  def store(userProfile: UserSignupInfo): Future[Boolean]

}

trait UserTraitImplementation extends UserRepoTrait {
  self: signupRepositryTrait =>

  import profile.api._

  /**
   * This method stores the data in database.
   * @param userProfile User information we want to save.
   * @return Returns result in Boolean.
   */
  def store(userProfile: UserSignupInfo): Future[Boolean] = {
    db.run(userProfileQuery += userProfile) map (_ > 0)
  }

  /**
   * This method finds user by username.
   * @param userLogin username of user in string.
   * @return User in Optional Future.
   */
  def findByUsername(userLogin: String): Future[Option[UserSignupInfo]] = {
    val queryResult = userProfileQuery.filter(_.username.toLowerCase === userLogin.toLowerCase).result.headOption
    db.run(queryResult)
  }

  /**
   * Updates Personal details of user.
   * @param userInfo Takes user parameter.
   * @return Returns Boolean in Future.
   */

  def updateUserProfile(userInfo: UserSignupInfo): Future[Boolean] = {
    db.run(userProfileQuery.filter(_.username.toLowerCase === userInfo.username.toLowerCase)
      .map(user => (user.fname, user.mname, user.lname,user.mobile,user.gender,user.age,user.hobby))
      .update(userInfo.fname, userInfo.mname, userInfo.lname,userInfo.mobile,userInfo.gender,userInfo.age,userInfo.hobby)) map (_ > 0)
  }

  /**
   * Method to get all list of all normal users.
   * @return Returns List of Users in Future.
   */

  def getAllNormalUser: Future[List[UserSignupInfo]] = {
    val joinQuery = for {
      user <- userProfileQuery.filter(_.isAdmin === false)
    } yield user

    db.run(joinQuery.to[List].result)
  }

  /**
   * Method changes the status of user.
   * @param user The user whose status you want to change.
   * @param status Current status.
   * @return Returns Boolean result in Future.
   */

  def changeUserStatus(user: String,status: Boolean) : Future[Boolean] = {
    db.run(userProfileQuery.filter(_.username.toLowerCase === user.toLowerCase)
      .map(user => user.isEnabled)
      .update(!status)) map (_ > 0)
  }

  /**
   * This method updates password.
   * @param username User whose pssword we want to change.
   * @param password New Password.
   * @param confirmpassword Confirm Password.
   * @return
   */
  def updatePassword(username: String, password: String, confirmpassword: String) : Future[Boolean] = {

    db.run(userProfileQuery.filter(_.username.toLowerCase === username.toLowerCase)
      .map(user => (user.password,user.confirmPassword))
     .update(password,confirmpassword)) map (_ > 0)
  }
}


