package models.form

import play.api.data.Form
import play.api.data.validation.Constraints._
import play.api.data.Forms.{email, mapping, text}
import play.api.i18n.Messages

case class UserLoginData(username: String, password: String)

class UserLoginForm {
  val userLoginForm = Form(mapping(
    "username" -> text.verifying("This should not be empty",_.nonEmpty),
    "password" -> text.verifying("This should not be empty",_.nonEmpty)
  )(UserLoginData.apply)(UserLoginData.unapply))
}
