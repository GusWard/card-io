package co.uk.wardone.cardio.fragments.create

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseFragment
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.fragment.create.CreateViewActions
import co.uk.wardone.viewmodel.fragment.create.CreateViewModel
import co.uk.wardone.viewmodel.fragment.create.CreateViewModelActions
import kotlinx.android.synthetic.main.fragment_create.view.*

class CreateCardFragment : BaseFragment<CreateViewModel>() {

    override fun createViewModel(): CreateViewModel = ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    ).get<CreateViewModel>(CreateViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_create

    override fun initViewModel(viewModel: CreateViewModel) {}

    override fun initViews(layout: View?) {

        getLayout()?.apply {

            createFragmentCreateButton?.setOnClickListener {

                viewModelAction(CreateViewModelActions.CreateCard(
                    title = createFragmentTitleInput?.text?.toString() ?: "",
                    description = createFragmentDescriptionInput?.text?.toString() ?: "",
                    link = createFragmentLinkInput?.text?.toString(),
                    image = createFragmentImageInput?.text?.toString()
                ))
            }

            createFragmentCancelButton?.setOnClickListener {

                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onViewData(viewData: BaseViewData) {


    }

    override fun onViewAction(viewAction: BaseViewAction) {

        when (viewAction) {

            is CreateViewActions.CreateFailed -> {

                Toast.makeText(requireContext(), viewAction.message, Toast.LENGTH_SHORT).show()
            }

            is CreateViewActions.CreateSuccess -> {

                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}