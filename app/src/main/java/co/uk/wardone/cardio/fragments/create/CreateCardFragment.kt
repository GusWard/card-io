package co.uk.wardone.cardio.fragments.create

import android.view.View
import androidx.lifecycle.ViewModelProvider
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseFragment
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.fragment.create.CreateViewModel

class CreateCardFragment : BaseFragment<CreateViewModel>() {

    override fun createViewModel(): CreateViewModel = ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    ).get<CreateViewModel>(CreateViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_create

    override fun initViewModel(viewModel: CreateViewModel) {}

    override fun initViews(layout: View?) {

        getLayout()?.apply {


        }
    }

    override fun onViewData(viewData: BaseViewData) {


    }

    override fun onViewAction(viewAction: BaseViewAction) {


    }
}