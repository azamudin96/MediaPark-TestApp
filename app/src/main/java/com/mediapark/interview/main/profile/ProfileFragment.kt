package com.mediapark.interview.main.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mediapark.interview.base.BaseFragment
import com.mediapark.interview.databinding.FragmentProfileBinding
import com.mediapark.interview.main.MainActivityInteractor

class ProfileFragment : BaseFragment(), ProfileFragmentMvpView {

    companion object {
        const val ARGUMENT_TYPE = "position"
    }

    private val presenter = ProfileFragmentPresenter<ProfileFragmentMvpView>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        presenter.onAttach(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initContent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (baseActivity?.getPresenter() is MainActivityInteractor) {
            presenter.interactor = baseActivity?.getPresenter() as MainActivityInteractor
        }
    }

    override fun onDetach() {
        presenter.onDetach()
        super.onDetach()
    }
}