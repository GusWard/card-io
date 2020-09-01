package co.uk.wardone.cardio.fragments.home

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseFragment
import co.uk.wardone.cardio.core.CoreRecyclerViewAdapter
import co.uk.wardone.cardio.fragments.create.CreateCardFragment
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.fragment.home.HomeData
import co.uk.wardone.viewmodel.fragment.home.HomeViewActions
import co.uk.wardone.viewmodel.fragment.home.HomeViewModel
import co.uk.wardone.viewmodel.fragment.home.HomeViewModelActions
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseFragment<HomeViewModel>() {

    private val homeViewHolderFactory = HomeViewHolderFactory(
        ::viewModelAction,
        viewAction = { action, _, _ ->
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
            initScrollListener()

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
                    message = "This cannot be undone"
                ) {

                    viewModelAction(HomeViewModelActions.DeleteCard(viewAction.id))
                }
            }

            is HomeViewActions.OpenLink -> {

                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(viewAction.url)
                startActivity(i)
            }
        }
    }

    private fun View.initScrollListener() {

        /* https://stackoverflow.com/questions/29024058/recyclerview-scrolled-up-down-listener */
        fragmentHomeRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    fragmentHomeFab?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_48)
                    fragmentHomeFab?.setOnClickListener {

                        fragmentHomeRecyclerView?.smoothScrollToPosition(0)
                    }

                } else {

                    fragmentHomeFab?.setImageResource(R.drawable.ic_baseline_add_24)
                    fragmentHomeFab?.setOnClickListener {

                        loadFragment(CreateCardFragment(), true, "create-fragment")
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        })
    }
}