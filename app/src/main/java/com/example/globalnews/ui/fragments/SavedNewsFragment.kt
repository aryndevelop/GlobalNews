package com.example.globalnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalnews.R
import com.example.globalnews.adapters.NewsAdapter
import com.example.globalnews.databinding.FragmentSavedNewsBinding
import com.example.globalnews.ui.NewsActivity
import com.example.globalnews.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            if (findNavController().currentDestination?.id == R.id.savedNewsFragment) {
                findNavController().navigate(R.id.action_savedNewsNewsFragment_to_articleFragment, bundle)
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }

    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}