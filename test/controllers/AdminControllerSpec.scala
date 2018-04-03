package controllers

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.util.Timeout
import models.form.{UserLoginForm, UserSignupForm, forgetPasswordForm}
import models.repositry.{AssignmentData, UserSignupClass, UserSignupInfo}
import org.mockito.Mockito.when
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.mvc.ControllerComponents
import play.api.test.FakeRequest
import play.api.test.Helpers.{OK, status, stubControllerComponents}
import play.api.test.CSRFTokenHelper._

class AdminControllerSpec extends PlaySpec with Mockito{

  implicit val duration: Timeout = 20 seconds

  "getAllNormalUser should delete assignment" in {
    val controller = getMockedObject

    val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
   // val user1 = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
   // val assignment = AssignmentData(1, "title", "Description")
    when(controller.userrepoClass.getAllNormalUser) thenReturn Future.successful(List(user))
    val result = controller.adminController.getAllNormalUser
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }

  "changeUserStatus should delete assignment" in {
    val controller = getMockedObject

    //val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
    // val user1 = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    // val assignment = AssignmentData(1, "title", "Description")
    when(controller.userrepoClass.changeUserStatus("test",true)) thenReturn Future.successful(true)
    val result = controller.adminController.changeUserStatus("test",true)
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }
  "changeUserStatus should not delete assignment on database error" in {
    val controller = getMockedObject

    //val user = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)
    // val user1 = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    // val assignment = AssignmentData(1, "title", "Description")
    when(controller.userrepoClass.changeUserStatus("test",true)) thenReturn Future.successful(false)
    val result = controller.adminController.changeUserStatus("test",true)
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }





  def getMockedObject: TestObjects = {
    val mockedUserLoginForm = mock[UserLoginForm]
    val mockedUserSignupForm= mock[UserSignupForm]
    val mockedUserRepo= mock[UserSignupClass]

    val controller = new AdminController(stubControllerComponents(), mockedUserLoginForm, mockedUserSignupForm,mockedUserRepo)

    TestObjects(stubControllerComponents(), mockedUserLoginForm, mockedUserSignupForm,mockedUserRepo, controller)
  }

  case class TestObjects(controllerComponent: ControllerComponents,
      userLoginForm: UserLoginForm,
      userSignupForm: UserSignupForm,
      userrepoClass: UserSignupClass,
      adminController: AdminController)

}
