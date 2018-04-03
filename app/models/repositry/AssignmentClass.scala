package models.repositry

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

/**
 *
 * @param dbConfigProvider.This class injects all the methods to controller.
 */

class AssignmentClass @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends AssigmentRepoTrait
    with AssgnmentTraitImplementation with assignmentRepositryTrait
