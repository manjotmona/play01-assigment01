package models.form

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text, nonEmptyText,optional}
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.data.validation.Constraints._
import views.html.defaultpages.error


case class UserSignupData(fname: String,
    mname: Option[String],
    lname: String,
    username: String,
    password: String,
    confirmPassword: String,
    mobile: String,
    gender: String,
    age: Int,
    hobby: String)

class UserSignupForm {



  val allNumbers = """\d*""".r
  val allLetters = """[A-Za-z]*""".r

  val passwordCheckConstraint: Constraint[String] = Constraint("constraints.passwordcheck")({
    password =>
      val errors = password match {
        case allNumbers() => Seq(ValidationError("Password is all numbers"))
        case allLetters() => Seq(ValidationError("Password is all letters"))
        case _ => Nil
      }
      if (errors.isEmpty) {
        Valid
      } else {
        Invalid(errors)
      }
  })

  val mobileConstraint: Constraint[String] = Constraint("constraints.mobilecheck")({
    mobile =>
      val errors = mobile match {
        case allNumbers()
          if mobile.length == 10
          => Nil
        case _ => Seq(ValidationError("Number is not valid"))
      }
      if (errors.isEmpty) {
        Valid
      } else {
        Invalid(errors)
      }
  })



  val userSignupForm = Form(mapping(
    "fname" -> text.verifying("This should not be empty", _.nonEmpty),
    "mname" -> optional(text),


    "lname" -> text.verifying("This should not be empty", _.nonEmpty),
    "username" -> text.verifying("This should not be empty", _.nonEmpty),
    "password" -> nonEmptyText.verifying(passwordCheckConstraint),
    "confirmPassword" -> text.verifying("This should not be empty", _.nonEmpty),
    "mobile" -> nonEmptyText.verifying(mobileConstraint),
    "gender" -> nonEmptyText,
    "age" -> number.verifying(min(18), max(75)),

    "hobby" -> nonEmptyText


  )(UserSignupData.apply)(UserSignupData.unapply)
                            verifying("Password fields do not match ", user => user.password == user.confirmPassword)
  )

}

