package models.form

import play.api.data.Form
import play.api.data.Forms.{mapping, text}


case class forgetPasswordData(username: String, password: String, confirmPassword: String)

class forgetPasswordForm {
  val userLoginForm = Form(mapping(
    "username" -> text.verifying("This should not be empty", _.nonEmpty),
    "password" -> text.verifying("This should not be empty", _.nonEmpty),
    "confirmPassword" -> text.verifying("This should not be empty", _.nonEmpty)
  )(forgetPasswordData.apply)(forgetPasswordData.unapply) verifying
                           ("Password fields do not match ", user => user.password ==
                                                                     user.confirmPassword))


}
