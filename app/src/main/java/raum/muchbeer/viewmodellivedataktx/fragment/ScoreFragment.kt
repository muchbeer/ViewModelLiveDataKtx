package raum.muchbeer.viewmodellivedataktx.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import raum.muchbeer.viewmodellivedataktx.R
import raum.muchbeer.viewmodellivedataktx.databinding.ScoreFragmentBinding
import raum.muchbeer.viewmodellivedataktx.viewmodel.ScoreViewModel
import raum.muchbeer.viewmodellivedataktx.viewmodel.ScoreViewModelFactory

class ScoreFragment : Fragment() {
    private lateinit var viewModel:ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory


    private lateinit var binding: ScoreFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        viewModelFactory= ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)


        binding.playAgainButton.setOnClickListener {
           // onPlayAgain()
            viewModel.onPlayAgain()
        }

        // Add observer for score
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { isPlayAgain->
            if(isPlayAgain) {
                onPlayAgain()
                viewModel.onPlayAgainComplete()
            }

        })
        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.scoreFragToGameFrag())
    }
}