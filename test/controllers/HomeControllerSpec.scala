package controllers

import models.form.{UserLoginForm, UserSignupData, UserSignupForm, forgetPasswordForm}
import models.repositry.{UserSignupClass, UserSignupInfo}
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.mockito.Mockito.when
import org.scalatestplus.play.guice._
import org.specs2.mock.Mockito
import play.api.mvc.{Action, AnyContent, ControllerComponents, Session}
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._
import play.libs.Scala


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with Mockito {

  //"should store a user" in {
 //   val controller = getMockedObject

   // val userInformation = UserInformation("ankit","barthwal","test@example.com")
 //  val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")


   // val userForm = new UserSignupForm {}.userSignupForm.fill(user)
    //val payload = controller.userrepoClass.


//    when(controller.userForm.userInfoForm) thenReturn userForm
//    when(controller.userInfoRepo.getUser("test@example.com")) thenReturn Future.successful(None)
//    when(controller.userInfoRepo.store(payload)) thenReturn Future.successful(Done)


//    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
//                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
//      "lname" -> "barthwal", "email" -> "test@example.com")
//      .withCSRFToken
//
//    val result = controller.homeController.storeData(request)
   // status(result) must equal(OK)
 // }

  "index should get a HTML page" in{
    val controller = getMockedObject
    val result = controller.homeController.index().apply(FakeRequest())
    status(result) must equal(OK)


  }
  "profile should get a HTML page" in{
    val controller = getMockedObject
    val result = controller.homeController.profile().apply(FakeRequest())
    status(result) must equal(OK)

  }

  "forgetPassword should get a HTML page" in{
    val controller = getMockedObject
    val userForm = new forgetPasswordForm {}.userLoginForm

    when(controller.userforgetForm.userLoginForm) thenReturn userForm

    val result = controller.homeController.forgetPassword().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }
  "showProfile should get auto fill form when user is connected" in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")

    val userForm = new UserSignupForm {}.userSignupForm.fill(user)
    val userFormforget = new forgetPasswordForm {}.userLoginForm
    val sessionCookie = Session.encodeAsCookie(Session(Map("key" -> "value")))

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget
   // when
  //  when(controller.homeController.onLogin()) thenReturn Action.g


    val result = controller.homeController.forgetPassword().apply(FakeRequest().withSession("name"->"test"))
    status(result) must equal(OK)

  }


  "login should get a HTML page" in{
    val controller = getMockedObject
    val userForm = new UserLoginForm {}.userLoginForm

    when(controller.userLoginForm.userLoginForm) thenReturn userForm


    val result = controller.homeController.login().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }
  "signup should get a HTML page" in{
    val controller = getMockedObject
    val userForm = new UserSignupForm {}.userSignupForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm


    val result = controller.homeController.signup().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }

  "logout should get a HTML page" in{
    val controller = getMockedObject
    val userForm = new UserSignupForm {}.userSignupForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm


    val result = controller.homeController.logout().apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }
  "adminNavBar should get a HTML page" in{
    val controller = getMockedObject

    val result = controller.homeController.adminNavBar().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }





  def getMockedObject: TestObjects = {
    val mockedUserLoginForm = mock[UserLoginForm]
    val mockedUserSignupForm= mock[UserSignupForm]
    val mockedUserRepo= mock[UserSignupClass]
    val mockedForgetForm= mock[forgetPasswordForm]

    val controller = new HomeController(stubControllerComponents(), mockedUserLoginForm, mockedUserSignupForm,mockedUserRepo, mockedForgetForm)

    TestObjects(stubControllerComponents(), mockedUserLoginForm, mockedUserSignupForm,mockedUserRepo,mockedForgetForm, controller)
  }

  case class TestObjects(controllerComponent: ControllerComponents,
      userLoginForm: UserLoginForm,
      userSignupForm: UserSignupForm,
      userrepoClass: UserSignupClass,
      userforgetForm:forgetPasswordForm,
      homeController: HomeController)
}


