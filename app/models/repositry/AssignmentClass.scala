package models.repositry

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider



class AssignmentClass @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends AssigmentRepoTrait
    with AssgnmentTraitImplementation with assignmentRepositryTrait