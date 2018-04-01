package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models.form.{Assignment, AssignmentForm}
import models.repositry.{AssignmentClass, AssignmentData}
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}


/**
 * This controller creates an `Action` to handle all HTTP Admin Requests.
 */
@Singleton
class AssignmentController @Inject()(cc: ControllerComponents,
    assignmentForm: AssignmentForm, assignmentRepo: AssignmentClass)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def assGetAddReq() = {
    Action { implicit request: Request[AnyContent] =>
      Ok(views.html.addAssignment(assignmentForm.AssignmentFormMapping))
    }
  }


  def assGetViewReq() = {
    Action.async { implicit request: Request[AnyContent] =>
      assignmentRepo.getAllAssignment.flatMap {
        assList => Future.successful(Ok(views.html.viewAssignment(assList)))
      }
    }
  }

  def assGetViewReqUser() = {
    Action.async { implicit request: Request[AnyContent] =>
      assignmentRepo.getAllAssignment.flatMap {
        assList => Future.successful(Ok(views.html.AssList(assList)))
      }
    }
  }


//  def showAssignment = {
//    Action.async { implicit request: Request[AnyContent] =>
//      assignmentRepo.getAllAssignment.flatMap {
//        assList => Future.successful(Ok(views.html.displayAssignments(assList)))
//      }
//    }
//  }

  def deleteAssignment(id: Int) = {
    Action.async { implicit request: Request[AnyContent] =>
      assignmentRepo.deleteAssignment(id).flatMap {
        //assList=> Future.successful(Ok(views.html.viewAssignment(assList)))

        case true =>
          Future
            .successful(Redirect(routes.AssignmentController.assGetViewReq())
              .flashing("status" -> "Deleted Suucessfully."))
        case false =>
          Future
            .successful(Redirect(routes.AssignmentController.assGetViewReq())
              .flashing("status" -> "Could Not Delete Assignment."))


      }
    }
  }

  def addAssignment: Action[AnyContent] = {
    Action.async { implicit request =>
      assignmentForm.AssignmentFormMapping.bindFromRequest.fold(
        formsWithErrors => {
          Future.successful(BadRequest(views.html.addAssignment(formsWithErrors)))
        },
        assData => {
          val newAss = AssignmentData(0, assData.title, assData.description)
          assignmentRepo.add(newAss).flatMap {
            case true =>
              Logger.info("true")
              Future
                .successful(Redirect(routes.AssignmentController.assGetAddReq())
                  .flashing("AssStatus" -> "Successfully Added"))
            case false => Logger.info("false")
              Future
                .successful(Redirect(routes.AssignmentController.assGetAddReq())
                  .flashing("AssStatus" -> "CouldNot Add Assignment."))


          }

        }
      )


      //Future.successful(Ok("hello"))
    }
  }


}