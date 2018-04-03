package models.repositry

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.reflect.ClassTag

import org.specs2.mutable.Specification
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder



class ModelsTest[T: ClassTag]{
  def fakeApp : Application = {
    new GuiceApplicationBuilder().build()

  }

  lazy val app2do = Application.instanceCache[T]
  lazy val repository : T = app2do(fakeApp)
}

class SignupRepoTest extends Specification  {
  val repo = new ModelsTest[UserSignupClass]

  "store method " should {
    "store detail of a user" in {
      //val user = repo.repository.UserInfo("manjot","kaur","manjot@gmail.com")
      val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
      val storeResult = Await.result(repo.repository.store(user),Duration.Inf)
      storeResult must equalTo(true)
    }

  }

  "fetchbByUsername" should {
    "get detail of a user" in {
      //val user = repo.repository.UserInfo("manjot","kaur","manjot@gmail.com")
      val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
      val storeResult = Await.result(repo.repository.findByUsername("test"),Duration.Inf)
      storeResult must beSome(user)
    }

  }
  "fetchbByUsername" should {
    "not get detail of a user not present" in {

      val storeResult = Await.result(repo.repository.findByUsername("man@gmail.com"),Duration.Inf)
      storeResult must beNone
    }

  }
  "updateUserProfile" should {
    "update a user present inside" in {
      val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",40,"Singing",false,true)

      val storeResult = Await.result(repo.repository.updateUserProfile(user),Duration.Inf)
      storeResult must equalTo(true)
    }

  }

  "updateUserProfile" should {
    "not update a user present inside" in {
      val user = UserSignupInfo("notest",None,"notest","notest","notest","notest","1234567891","Female",40,"Singing",false,true)

      val storeResult = Await.result(repo.repository.updateUserProfile(user),Duration.Inf)
      storeResult must equalTo(false)
    }

  }

  "getAllNormalUser" should {
    "return list of all users" in {
     val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",40,"Singing",false,true)

      val storeResult = Await.result(repo.repository.getAllNormalUser,Duration.Inf)
      storeResult must equalTo(List(user))
    }

  }

  "changeUserStatus" should {
    "change enable- disable status of user" in {
      //val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",40,"Singing",false,true)

      val storeResult = Await.result(repo.repository.changeUserStatus("test",true),Duration.Inf)
      storeResult must equalTo(true)
    }

  }

  "updatePassword" should {
    "update the password of a user present inside" in {
      val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",40,"Singing",false,true)

      val storeResult = Await.result(repo.repository.updatePassword("test","test1","test1"),Duration.Inf)
      storeResult must equalTo(true)
    }

  }

}
