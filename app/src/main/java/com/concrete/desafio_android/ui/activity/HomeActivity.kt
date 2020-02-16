package com.concrete.desafio_android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.concrete.desafio_android.R
import com.concrete.desafio_android.RepositoriesContract
import com.concrete.desafio_android.RepositoriesPresenter
import com.concrete.desafio_android.domain.Repository
import com.concrete.desafio_android.ui.adapter.RepositoryListAdapter
import kotlinx.android.synthetic.main.activity_home.list_java_repositories


class HomeActivity: AppCompatActivity(), RepositoriesContract.View{

    private val presenter: RepositoriesContract.Presenter = RepositoriesPresenter(this)
    private val repositoryList = ArrayList<Repository>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.getRepositories()
        setListAdapter()
    }

    private fun setListAdapter() {
        list_java_repositories.adapter = RepositoryListAdapter(repositoryList, this) {
            startPullRequestListActivity(it)
        }
        val dividerItemDecoration = DividerItemDecoration(
            list_java_repositories.context,
            list_java_repositories.layoutManager!!.layoutDirection
        )
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.list_item_divider)!!)
        list_java_repositories.addItemDecoration(dividerItemDecoration)
    }

    private fun startPullRequestListActivity(repository: Repository) {
        val intent = Intent(this, PullRequestListActivity::class.java)
        intent.putExtra("repository", repository)
        startActivity(intent)
    }

    override fun showList(repositories: ArrayList<Repository>) {
        repositoryList.addAll(repositories)
        list_java_repositories.adapter?.notifyDataSetChanged()
    }

    override fun showFailureMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}