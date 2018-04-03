package models.form

import play.api.data.Form
import play.api.data.Forms.{mapping, text}

case class Assignment(title: String, description: String)

class AssignmentForm {
  val assignmentFormMapping = Form(mapping(
    "title" -> text.verifying("This should not be empty",_.nonEmpty),
    "description" -> text.verifying("This should not be empty",_.nonEmpty)
  )(Assignment.apply)(Assignment.unapply))
}
