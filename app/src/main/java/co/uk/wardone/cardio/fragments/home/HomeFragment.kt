package co.uk.wardone.cardio.fragments.home

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseFragment
import co.uk.wardone.cardio.core.CoreRecyclerViewAdapter
import co.uk.wardone.cardio.fragments.create.CreateCardFragment
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.fragment.home.HomeData
import co.uk.wardone.viewmodel.fragment.home.HomeViewActions
import co.uk.wardone.viewmodel.fragment.home.HomeViewModel
import co.uk.wardone.viewmodel.fragment.home.HomeViewModelActions
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseFragment<HomeViewModel>() {

    private val homeViewHolderFactory = HomeViewHolderFactory(::viewModelAction, viewAction = { action, _, _ ->
        onViewAction(action)
    })
    private val homeAdapter = CoreRecyclerViewAdapter(homeViewHolderFactory)

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun createViewModel(): HomeViewModel = ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    ).get<HomeViewModel>(HomeViewModel::class.java)

    override fun initViewModel(viewModel: HomeViewModel) {}

    override fun initViews(layout: View?) {

        getLayout()?.apply {

            fragmentHomeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
            fragmentHomeRecyclerView?.adapter = homeAdapter

            fragmentHomeFab?.setOnClickListener {

                loadFragment(CreateCardFragment(), true, "create-fragment")
            }
        }
    }

    override fun onViewData(viewData: BaseViewData) {

        when (viewData) {

            is HomeData.HomeItems -> {

                homeAdapter.updateItems(viewData.items)
            }
        }
    }

    override fun onViewAction(viewAction: BaseViewAction) {

        when (viewAction) {

            is HomeViewActions.VerifyDeleteCard -> {

                showCancelableDialog(
                    title = "Delete card?",
                    message = "This cannot be undone") {

                    viewModelAction(HomeViewModelActions.DeleteCard(viewAction.id))
                }
            }
        }
    }
}