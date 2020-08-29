package co.uk.wardone.cardio.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.uk.wardone.cardio.R
import co.uk.wardone.viewmodel.base.BaseFragmentData
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    private lateinit var viewModel : T
    private var layout: View? = null

    /**
     * refresh the data only once when fragment is created, then provide way in view, or refresh periodically in the
     * view model
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        initViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(getLayoutRes(), container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initViews(layout)
    }

    /**
     * use [getViewLifecycleOwner] for binding fragment to viewModel so it binds at the correct time
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        viewModel.bindData(viewLifecycleOwner, Observer { onFragmentData(it) })
        viewModel.bindActions(viewLifecycleOwner, Observer { onFragmentAction(it) })
    }

    protected fun viewModelAction(viewAction: BaseViewModelAction) {

        viewModel.viewModelAction(viewAction, this)
    }

    abstract fun onFragmentData(fragmentData: BaseFragmentData)

    abstract fun onFragmentAction(viewAction: BaseViewAction)

    protected fun getLayout(): View? = layout

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun createViewModel() : T

    abstract fun initViewModel(viewModel: T)

    abstract fun initViews(layout: View?)

    private fun loadFragment(fragment: BaseFragment<*>, addToBackStack: Boolean = true, tag: String? = null) {

        val fragmentTransaction = fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            ?.replace(R.id.fragmentContainer, fragment)

        if (addToBackStack) {

            fragmentTransaction?.addToBackStack(tag)
        }

        fragmentTransaction?.commit()
    }

    private fun showCancelableDialog(title: String, message: String, positiveText: String = "OK", positiveAction: () -> Unit) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText) { _, _ ->

            positiveAction()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}