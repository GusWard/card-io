package co.uk.wardone.cardio.fragments.home

import android.view.View
import androidx.lifecycle.ViewModelProvider
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseFragment
import co.uk.wardone.cardio.core.CoreRecyclerViewAdapter
import co.uk.wardone.viewmodel.base.BaseFragmentData
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.fragment.home.HomeViewModel


class HomeFragment : BaseFragment<HomeViewModel>() {

    private val homeViewHolderFactory = HomeViewHolderFactory()
    private val selectedResponseAdapter = CoreRecyclerViewAdapter(homeViewHolderFactory)

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun createViewModel(): HomeViewModel
            = ViewModelProvider(
        this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application
        )
    )
        .get<HomeViewModel>(HomeViewModel::class.java)

    override fun initViewModel(viewModel: HomeViewModel) {

    }

    override fun initViews(layout: View?) {

        getLayout()?.apply {


        }
    }

    override fun onFragmentData(fragmentData: BaseFragmentData) {

        when (fragmentData) {

        }
    }

    override fun onFragmentAction(viewAction: BaseViewAction) {

        when (viewAction) {

        }
    }

}