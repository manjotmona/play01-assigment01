package models.repositry

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

/**
 * This class in injected in controllers.
 * @param dbConfigProvider
 */
class UserSignupClass @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends UserRepoTrait
    with UserTraitImplementation with signupRepositryTrait
