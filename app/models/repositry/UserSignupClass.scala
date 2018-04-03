package models.repositry

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider


class UserSignupClass @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends UserRepoTrait
    with UserTraitImplementation with signupRepositryTrait
