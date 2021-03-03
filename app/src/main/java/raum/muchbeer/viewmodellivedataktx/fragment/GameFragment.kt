package raum.muchbeer.viewmodellivedataktx.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import raum.muchbeer.viewmodellivedataktx.R
import raum.muchbeer.viewmodellivedataktx.databinding.FragmentGameBinding
import raum.muchbeer.viewmodellivedataktx.viewmodel.GameViewModel


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: FragmentGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        // Get the viewmodel
        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        //the below binding code is used by livedata to update the view according with the binding
        binding.lifecycleOwner = this

 /*       binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            Log.i("GameFragment", "Clicked correct button")
        }*/
   /*     binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            Log.i("GameFragment", "Clicked onSkiped button")
                    }*/


        viewModel.score.observe(viewLifecycleOwner, Observer { scoreLiveDat->
            binding.scoreText.text = scoreLiveDat.toString()
        })
     /*   viewModel.word.observe(viewLifecycleOwner, Observer { wordLiveData->
            binding.wordText.text = wordLiveData

        })*/

     /*   viewModel.currentTime.observe(viewLifecycleOwner, Observer {newTime->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })
*/

        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { gameBuzzerType->
            if (gameBuzzerType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(gameBuzzerType.pattern)
                viewModel.onBuzzComplete()

        }

        })
        viewModel.setFinished.observe(viewLifecycleOwner, Observer { hasFinished->
            if(hasFinished) {
                gameFinished()
                //The below code set game finished and returned to the init function of the viewModel
                //where it protect the state due to configuration changes
                viewModel.gameFinishedCompleted()
            }
        })

        return binding.root
    }


    /**
     * Given a pattern, this method makes sure the device buzzes
     */
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                 //   it.vibrate(VibrationEffect.createWaveform(pattern, -1))
                it.vibrate(pattern, -1)
            }
        }
    }
    /**
     * Called when the game is finished
     */
    private fun gameFinished() {

        val action = GameFragmentDirections.gameFragToScoreFrag()
        action.setScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }


}