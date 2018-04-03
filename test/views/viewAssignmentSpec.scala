package views


import models.repositry.AssignmentData
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}


class viewAssignmentSpec extends PlaySpec with Mockito {

  "viewAssignment page======" in new App {

    val request = FakeRequest(GET, "/userProfile")
    val mockedData = mock[AssignmentData]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]
    val mockedSession = mock[play.api.mvc.Session]


    val html = views.html.viewAssignment(List(mockedData))(request, mockedFlash, mockedSession)

    contentAsString(html) must include("Title")
  }


}
