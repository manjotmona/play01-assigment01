package controllers

import javax.inject._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import models.form.{UserLoginForm, UserSignupData, UserSignupForm, forgetPasswordForm}
import models.repositry.{UserSignupClass, UserSignupInfo}
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,
    userLoginForm: UserLoginForm,
    usersignupForm: UserSignupForm,
    userSignupRepo: UserSignupClass,
    forgetForm: forgetPasswordForm)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  /**
   * Create an Action to render an HTML page.
   */
  def index(): Action[AnyContent] = {
    Action { implicit request: Request[AnyContent] =>
      Ok(views.html.index())
    }
  }
  def profile(): Action[AnyContent] = {
    Action.async { implicit request: Request[AnyContent] =>
      Future.successful(Ok(views.html.profile()))
    }
  }
  def forgetPassword(): Action[AnyContent] = {
    Action.async { implicit request: Request[AnyContent] =>
      Future.successful(Ok(views.html.forgetPassword(forgetForm.userLoginForm)))
    }}
  def showProfile(): Action[AnyContent] = {
    Logger.info("inside method")
    Action.async { implicit request: Request[AnyContent] =>
      Logger.info("getting")
      request.session.get("name").map { username =>
        Logger.info(username)
        userSignupRepo.findByUsername(username).flatMap {
          case Some(someuser) =>
            val userPresentData = UserSignupData(someuser.fname,
              someuser.mname,
              someuser.lname,
              someuser.username,
              someuser.password,
              someuser.confirmPassword,
              someuser.mobile,
              someuser.gender,
              someuser.age,
              someuser.hobby)
            Future
              .successful(Ok(views.html
                .userProfile(usersignupForm.userSignupForm.fill(userPresentData))))
        }
      }.getOrElse {
        Future.successful(Unauthorized("Oops you are not connected")
        )}}
  }
  def updateProfile: Action[AnyContent] = {
    Action.async { implicit request =>
      usersignupForm.userSignupForm.bindFromRequest.fold(
        formWithErrors => {
          Logger.info("updated- error")
          Future.successful(BadRequest(views.html.userProfile(formWithErrors)))
        },
        userData => {
          Logger.info("updating")
          val user = UserSignupInfo(userData.fname,
            userData.mname,
            userData.lname,
            userData.username,
            userData.password,
            userData.confirmPassword,
            userData.mobile,
            userData.gender,
            userData.age,
            userData.hobby, false, true)
          userSignupRepo.updateUserProfile(user).flatMap {
            case true => Logger.info("updated")
              Future
                .successful(Redirect(routes.HomeController.login())
                  .flashing("status" -> "successfully updated"))
            case false => Logger.info("not updated")
              Future
                .successful(Redirect(routes.HomeController.login())
                  .flashing("status" -> "Could not update"))
          }})}
  }
  def login(): Action[AnyContent] = {
    Action { implicit request: Request[AnyContent] =>
      Ok(views.html.login(userLoginForm.userLoginForm))
    }
  }
  def signup(): Action[AnyContent] = {
    Action { implicit request: Request[AnyContent] =>
      //Ok(views.html.index()
      Ok(views.html.signup(usersignupForm.userSignupForm))
    }}
  def logout(): Action[AnyContent] = {
    Action { implicit request =>
      Logger.info("logged out")
      Redirect(routes.HomeController.index()).withNewSession
    }}
  def adminNavBar(): Action[AnyContent] = {
    Action { implicit request =>
      Logger.info("display admin nav bar")
      Ok(views.html.adminNavBar())
    }}
  def storeData: Action[AnyContent] = {
    Action.async { implicit request =>
      usersignupForm.userSignupForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest(views.html.signup(formWithErrors)))
        },
        userData => {
          val user = UserSignupInfo(userData.fname,
            userData.mname, userData.lname, userData.username,
            userData.password, userData.confirmPassword,
            userData.mobile, userData.gender, userData.age,
            userData.hobby, false, true)
          userSignupRepo.store(user).flatMap {
            case true => Logger.info("true")
              Future
                .successful(Redirect(routes.HomeController.profile())
                  .withSession("name" -> userData.username).flashing("status" -> "success"))
            case false => Logger.info("false")
              Future
                .successful(Redirect(routes.HomeController.index()).flashing("status" -> "Failure"))
          }})}
  }
  def onLogin: Action[AnyContent] = {
    Action.async { implicit request =>
      userLoginForm.userLoginForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest(views.html.login(formWithErrors)))
        },
        userLoginPageData => {
          val user = userSignupRepo.findByUsername(userLoginPageData.username)
          user.flatMap {
            case Some(loginUser) => Logger.info("Some user")
              if (loginUser.password == userLoginPageData.password) {
                if (!loginUser.isAdmin && loginUser.isEnabled) {
                  Future
                    .successful(Redirect(routes.HomeController.profile())
                      .withSession("name" -> loginUser.username))
                }
                else if (!loginUser.isEnabled) {
                  Future
                    .successful(Redirect(routes.HomeController.login())
                      .flashing("status" -> "You are in active. Contact Admin"))
                }
                else {
                  Logger.info("admin")
                  Future
                    .successful(Redirect(routes.HomeController.adminNavBar())
                      .withSession("name" -> loginUser.username))
                }}
              else {
                Logger.info("incorrect password")
                Future

                  .successful(Redirect(routes.HomeController.login())
                    .flashing("status" -> "Incorrect password"))
              }
            case None => Logger.info("no user")
              Future
                .successful(Redirect(routes.HomeController.login())
                  .flashing("status" -> "No such user exists"))
          }})}
  }
  def reset: Action[AnyContent] = {
    Action.async { implicit request =>
      forgetForm.userLoginForm.bindFromRequest.fold(
        formWithErrors => {
          Logger.info("error in reset")
          Future.successful(BadRequest(views.html.forgetPassword(formWithErrors)))
        },
        detail => {
          userSignupRepo.updatePassword(detail.username, detail.password, detail.confirmPassword)
            .flatMap {
              case true => Logger.info("pass changed")
                Future
                  .successful(Redirect(routes.HomeController.reset())
                    .flashing("status" -> "Password Successfully Changed"))
              case false => Logger.info("no user")
                Future
                  .successful(Redirect(routes.HomeController.reset())
                    .flashing("status" -> "User does not Exist"))
            }})}
  }
}
