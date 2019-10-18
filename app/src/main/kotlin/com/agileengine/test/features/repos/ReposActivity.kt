package com.agileengine.test.features.repos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agileengine.test.R
import com.agileengine.test.databinding.ActivityRepoListBinding
import com.agileengine.test.di.DI
import javax.inject.Inject


class ReposActivity : AppCompatActivity() {

    lateinit var binding: ActivityRepoListBinding

    @Inject
    lateinit var viewModel: ReposViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        val adapter = ReposAdapter()
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ReposActivity
            reposList.adapter = adapter
            subscribeUI(adapter)
            executePendingBindings()
        }
    }

    fun subscribeUI(adapter: ReposAdapter) {
        binding.vm?.repos?.observe(this, Observer { adapter.submitList(it) })
    }
}