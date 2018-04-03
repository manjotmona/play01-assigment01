package models.repositry

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.reflect.ClassTag

import org.specs2.mutable.Specification
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder


//class ModelsTest[T: ClassTag]{
//  def fakeApp : Application = {
//    new GuiceApplicationBuilder().build()
//
//  }

//  lazy val app2do = Application.instanceCache[T]
//  lazy val repository : T = app2do(fakeApp)
//}

class AssignmentRepoTest extends Specification  {
  val repo = new ModelsTest[AssignmentClass]

  "add method " should {
    "store detail of a user" in {
      //val user = repo.repository.UserInfo("manjot","kaur","manjot@gmail.com")
      val assignment = AssignmentData(1,"title","test")
      val storeResult = Await.result(repo.repository.add(assignment),Duration.Inf)
      storeResult must equalTo(true)
    }

  }

  "getAllAssignment" should {
    "get detail of a user" in {
      //val user = repo.repository.UserInfo("manjot","kaur","manjot@gmail.com")
      val assignment = AssignmentData(1,"title","test")
     // val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
      val storeResult = Await.result(repo.repository.getAllAssignment,Duration.Inf)
      storeResult must equalTo(List(assignment))
    }

  }



//  "deleteAssignment" should {
//    "not update a user present inside" in {
//     // val user = UserSignupInfo("notest",None,"notest","notest","notest","notest","1234567891","Female",40,"Singing",false,true)
//
//      val storeResult = Await.result(repo.repository.deleteAssignment(1),Duration.Inf)
//      storeResult must equalTo(true)
//    }
//
//  }

}
