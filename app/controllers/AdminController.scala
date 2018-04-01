package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import models.form.{UserLoginForm, UserSignupData, UserSignupForm}
import models.repositry.UserSignupClass
import play.api.Logger
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * This controller creates an `Action` to handle all HTTP Admin Requests.
 */
@Singleton
class AdminController @Inject()(cc: ControllerComponents,
    userLoginForm: UserLoginForm,
    usersignupForm: UserSignupForm,
    userSignupRepo: UserSignupClass)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {


  def getAllNormalUser = {
    Logger.info("getting normal users")
    Action.async { implicit request: Request[AnyContent] =>
      Logger.info("getting")
      userSignupRepo.getAllNormalUser.flatMap {
        user => Future.successful(Ok(views.html.DisplayUser(user)))
      }
      // Future.successful(Ok())


    }

    }

    def changeUserStatus(user: String,status:Boolean ) = {
      Logger.info("Change user status")
      Action.async { implicit request: Request[AnyContent] =>
        Logger.info("changing")
        userSignupRepo.changeUserStatus(user,status).flatMap{
          case true =>
            Logger.info("changed")
            Future
              .successful(Redirect(routes.AdminController.getAllNormalUser()).flashing("changeStatus" -> "user status updated successfully"))
          case false => Logger.info("change unsuccessful")
            Future
              .successful(Redirect(routes.AdminController.getAllNormalUser()).flashing("status" -> "Could not update user status"))
        }
      }
        // Future.successful(Ok())




      }














}