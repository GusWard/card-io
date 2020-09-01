package co.uk.wardone.viewmodel.fragment.create

fun validateCard(action: CreateViewModelActions.CreateCard): Boolean {

    val titleValid = action.title.isEmpty().not()
    val descriptionValid = action.description.isEmpty().not()
    return titleValid && descriptionValid
}