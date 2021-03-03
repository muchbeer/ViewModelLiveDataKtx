package raum.muchbeer.viewmodellivedataktx.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import raum.muchbeer.viewmodellivedataktx.R
import raum.muchbeer.viewmodellivedataktx.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var binding : FragmentTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title,container, false)

        binding.playGameButton.setOnClickListener {
            it.findNavController().navigate(TitleFragmentDirections.titleFragToGameFrag())
        }
        return binding.root
    }

}