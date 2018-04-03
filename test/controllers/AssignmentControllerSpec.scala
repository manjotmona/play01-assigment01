package controllers

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.util.Timeout
import models.form.{Assignment, AssignmentForm}
import models.repositry.{AssignmentClass, AssignmentData}
import org.mockito.Mockito.when
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.mvc.ControllerComponents
import play.api.test.CSRFTokenHelper._
import play.api.test.FakeRequest
import play.api.test.Helpers.{OK, status, stubControllerComponents}


class AssignmentControllerSpec extends PlaySpec with Mockito {

  implicit val duration: Timeout = 20 seconds

  "assGetAddReq should get a HTML page" in {
    val controller = getMockedObject

    val userForm = new AssignmentForm {}.assignmentFormMapping

    when(controller.assignmentForm.assignmentFormMapping) thenReturn userForm

    val result = controller.assignmentController.assGetAddReq().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }

  "assGetViewReq should get a HTML page" in {
    val controller = getMockedObject

    val assignment = AssignmentData(1, "title", "Description")
    when(controller.assignmentClass.getAllAssignment) thenReturn Future.successful(List(assignment))
    val result = controller.assignmentController.assGetViewReq().apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }
  "assGetViewReqUser should get a HTML page" in {
    val controller = getMockedObject

    val assignment = AssignmentData(1, "title", "Description")
    when(controller.assignmentClass.getAllAssignment) thenReturn Future.successful(List(assignment))
    val result = controller.assignmentController.assGetViewReqUser()
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(OK)

  }

  "deleteAssignment should delete assignment" in {
    val controller = getMockedObject

    val assignment = AssignmentData(1, "title", "Description")
    when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(true)
    val result = controller.assignmentController.deleteAssignment(1)
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }
  "addAssignment should add assignment in repo" in {
    val controller = getMockedObject

    val assignment1 = Assignment("title", "Description")
    val userForm = new AssignmentForm {}.assignmentFormMapping.fill(assignment1)

    val payload = AssignmentData(1, "title", "Description")
    when(controller.assignmentForm.assignmentFormMapping) thenReturn userForm
    when(controller.assignmentClass.add(payload)) thenReturn Future.successful(true)


    // when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(true)
    val result = controller.assignmentController.addAssignment().apply(FakeRequest().withCSRFToken)
    status(result) must equal(400)

  }


  def getMockedObject: TestObjects = {
    val mockedAssignmentForm = mock[AssignmentForm]
    val mockedAssignmentClass = mock[AssignmentClass]
    val controller = new AssignmentController(stubControllerComponents(),
      mockedAssignmentForm,
      mockedAssignmentClass)

    TestObjects(stubControllerComponents(), mockedAssignmentForm, mockedAssignmentClass, controller)
  }

  case class TestObjects(controllerComponent: ControllerComponents,
      assignmentForm: AssignmentForm,
      assignmentClass: AssignmentClass,
      assignmentController: AssignmentController)

}
