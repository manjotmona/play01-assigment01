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
import play.api.test.Helpers.{OK, POST, status, stubControllerComponents}


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

  "deleteAssignment should not delete assignment in internal database error" in {
    val controller = getMockedObject


    when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(false)
    val result = controller.assignmentController.deleteAssignment(1)
      .apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }


  "addAssignment should not add assignment in bad form input" in {
    val controller = getMockedObject

    // val assignment1 = Assignment("title", "Description")
    val userForm = new AssignmentForm {}.assignmentFormMapping

    val payload = AssignmentData(0, "title", "Description")
    when(controller.assignmentForm.assignmentFormMapping) thenReturn userForm
    when(controller.assignmentClass.add(payload)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/addAss").withFormUrlEncodedBody("csrfToken"
                                                                      -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "title" -> "", "description"->"Description")
      .withCSRFToken
    val result = controller.assignmentController.addAssignment(request)


    // when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(true)
    // val result = controller.assignmentController.addAssignment().apply(FakeRequest().withCSRFToken)
    status(result) must equal(400)

  }

  "addAssignment should add assignment in repo" in {
    val controller = getMockedObject

   // val assignment1 = Assignment("title", "Description")
    val userForm = new AssignmentForm {}.assignmentFormMapping

    val payload = AssignmentData(0, "title", "Description")
    when(controller.assignmentForm.assignmentFormMapping) thenReturn userForm
    when(controller.assignmentClass.add(payload)) thenReturn Future.successful(true)

    val request = FakeRequest(POST, "/addAss").withFormUrlEncodedBody("csrfToken"
                                                                     -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "title" -> "title", "description"->"Description")
      .withCSRFToken
    val result = controller.assignmentController.addAssignment(request)


    // when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(true)
   // val result = controller.assignmentController.addAssignment().apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

  }


  "addAssignment should not add assignment in database error" in {
    val controller = getMockedObject

    // val assignment1 = Assignment("title", "Description")
    val userForm = new AssignmentForm {}.assignmentFormMapping

    val payload = AssignmentData(0, "title", "Description")
    when(controller.assignmentForm.assignmentFormMapping) thenReturn userForm
    when(controller.assignmentClass.add(payload)) thenReturn Future.successful(false)

    val request = FakeRequest(POST, "/addAss").withFormUrlEncodedBody("csrfToken"
                                                                      -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea",
      "title" -> "title", "description"->"Description")
      .withCSRFToken
    val result = controller.assignmentController.addAssignment(request)


    // when(controller.assignmentClass.deleteAssignment(1)) thenReturn Future.successful(true)
    // val result = controller.assignmentController.addAssignment().apply(FakeRequest().withCSRFToken)
    status(result) must equal(303)

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
