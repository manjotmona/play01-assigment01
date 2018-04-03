package controllers

import scala.concurrent.Future

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
    val userReturn = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm.fill(user)
    val userFormforget = new forgetPasswordForm {}.userLoginForm
    //val sessionCookie = Session.encodeAsCookie(Session(Map("key" -> "value")))

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget
    when(controller.userrepoClass.findByUsername("test")) thenReturn Future.successful(Some(userReturn))



    val result = controller.homeController.showProfile().apply(FakeRequest().withSession("name"->"test").withCSRFToken)
    status(result) must equal(OK)

  }


  //see --- unauthorized

  "showProfile should not get auto fill form when user is not connected" in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("test",None,"test","test","test","test","1234567891","Female",29,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm.fill(user)
    val userFormforget = new forgetPasswordForm {}.userLoginForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget
   // when(controller.userrepoClass.findByUsername("test")) thenReturn Future.successful(None)

    val result = controller.homeController.showProfile().apply(FakeRequest().withCSRFToken)
    status(result) must equal(401)

  }
  "updateProfile should update user's profile when connected" in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm
   // val userFormforget = new forgetPasswordForm {}.userLoginForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.updateUserProfile(userReturn)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"m12","confirmPassword"->"m12","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.updateProfile(request)

    //val result =
   // val result = controller.homeController.updateProfile().apply(FakeRequest().withSession("name"->"test").withCSRFToken)
    status(result) must equal(303)

  }

  "updateProfile should not update user's profile when data is invalid" in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","mmm","mmm","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm
    // val userFormforget = new forgetPasswordForm {}.userLoginForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.updateUserProfile(userReturn)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"mmm","confirmPassword"->"mmm","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.updateProfile(request)

    //val result =
    // val result = controller.homeController.updateProfile().apply(FakeRequest().withSession("name"->"test").withCSRFToken)
    status(result) must equal(400)

  }

  "updateProfile should not update user's profile when there is database error(updateDb function returns negative) " in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm
    // val userFormforget = new forgetPasswordForm {}.userLoginForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.updateUserProfile(userReturn)) thenReturn Future.successful(false)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"m12","confirmPassword"->"m12","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.updateProfile(request)

    //val result =
    // val result = controller.homeController.updateProfile().apply(FakeRequest().withSession("name"->"test").withCSRFToken)
    status(result) must equal(303)

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

  "storeData should succesccfully store data to the database" in{
    val controller = getMockedObject
    val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm
    // val userFormforget = new forgetPasswordForm {}.userLoginForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.store(userReturn)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"m12","confirmPassword"->"m12","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.storeData(request)

    //val result =
    // val result = controller.homeController.updateProfile().apply(FakeRequest().withSession("name"->"test").withCSRFToken)
    status(result) must equal(303)

  }
  "storeData should not succesccfully store data to the database on database error" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.store(userReturn)) thenReturn Future.successful(false)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"m12","confirmPassword"->"m12","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.storeData(request)

    status(result) must equal(303)

  }
  "storeData should not succesccfully store data to the database on invalid data/ bad form request" in{
    val controller = getMockedObject
    //val user = UserSignupData("test",None,"test","test","test","test","1234567891","Female",29,"Singing")
    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","mmm","mmm","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm

    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userrepoClass.store(userReturn)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/store").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "fname" -> "ankit",
      "lname" -> "barthwal", "username" -> "test@example.com", "password"->"mmm","confirmPassword"->"mmm","mobile"->"1234567891","gender"->"Female" ,"age"->"23","hobby"->"Singing","isAdmin"->"false","isEnabled"->"true")
      .withCSRFToken
    val result = controller.homeController.storeData(request)


    status(result) must equal(400)

  }

  "onLogin should succesfully login on validation and if user is enabled" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)

    val userForm = new UserSignupForm {}.userSignupForm
    val userLoginForm = new UserLoginForm {}.userLoginForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("test@example.com")) thenReturn Future.successful(Some(userReturn))

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m12")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(303)

  }

  "onLogin should not allow user to login if input form does not adheres to the validations" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,true)


    val userLoginForm = new UserLoginForm {}.userLoginForm



    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("test@example.com")) thenReturn Future.successful(Some(userReturn))

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "", "password"->"m12")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(400)

  }

  "onLogin should not succesfully loginn validation and if user is not enabled" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)

    val userForm = new UserSignupForm {}.userSignupForm
    val userLoginForm = new UserLoginForm {}.userLoginForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("test@example.com")) thenReturn Future.successful(Some(userReturn))

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m12")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(303)

  }

  //ADMINN LOGINNN---------------------------

  "onLogin should succesfully login admin on validation " in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("admin",None,"admin","admin","admin12","admin12","1234567891","Female",30,"Singing",true,true)

    val userForm = new UserSignupForm {}.userSignupForm
    val userLoginForm = new UserLoginForm {}.userLoginForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("admin")) thenReturn Future.successful(Some(userReturn))

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "admin", "password"->"admin12")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(303)

  }

  "onLogin should not succesfully login if password is incorrect" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)

    val userForm = new UserSignupForm {}.userSignupForm
    val userLoginForm = new UserLoginForm {}.userLoginForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("test@example.com")) thenReturn Future.successful(Some(userReturn))

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m12345")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(303)

  }

  "onLogin should not login if user is not in Database" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)

    val userForm = new UserSignupForm {}.userSignupForm
    val userLoginForm = new UserLoginForm {}.userLoginForm


    when(controller.userSignupForm.userSignupForm) thenReturn userForm
    when(controller.userLoginForm.userLoginForm) thenReturn userLoginForm

    when(controller.userrepoClass.findByUsername("test@example.com")) thenReturn Future.successful(None)

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m12")
      .withCSRFToken
    val result = controller.homeController.onLogin(request)

    status(result) must equal(303)

  }


  "reset should not login if user is not in Database" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)



    val userFormforget = new forgetPasswordForm {}.userLoginForm


    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget


    when(controller.userrepoClass.updatePassword("test@example.com","m1234","m1234")) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m1234","confirmPassword" -> "m1234")
      .withCSRFToken
    val result = controller.homeController.reset(request)

    status(result) must equal(303)

  }
  "reset should not reset password if user is not in Database" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)



    val userFormforget = new forgetPasswordForm {}.userLoginForm


    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget


    when(controller.userrepoClass.updatePassword("test@example.com","m1234","m1234")) thenReturn Future.successful(false)

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "test@example.com", "password"->"m1234","confirmPassword" -> "m1234")
      .withCSRFToken
    val result = controller.homeController.reset(request)

    status(result) must equal(303)

  }
  "reset should not reset if form fields are not valid" in{
    val controller = getMockedObject

    val userReturn = UserSignupInfo("ankit",None,"barthwal","test@example.com","m12","m12","1234567891","Female",23,"Singing",false,false)



    val userFormforget = new forgetPasswordForm {}.userLoginForm


    when(controller.userforgetForm.userLoginForm) thenReturn userFormforget


    when(controller.userrepoClass.updatePassword("test@example.com","m1234","m1234")) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "username" -> "", "password"->"mmm","confirmPassword" -> "mmmm")
      .withCSRFToken
    val result = controller.homeController.reset(request)

    status(result) must equal(400)

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


