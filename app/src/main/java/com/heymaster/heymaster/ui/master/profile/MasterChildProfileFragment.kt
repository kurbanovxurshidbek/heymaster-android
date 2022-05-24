package com.heymaster.heymaster.ui.master.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.home.HomeServicesAdapter
import com.heymaster.heymaster.adapters.profile.PortfolioAdapter
import com.heymaster.heymaster.adapters.viewpagers.MasterProfilePagerAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterChildProfileBinding
import com.heymaster.heymaster.databinding.FragmentUserHomeBinding
import com.heymaster.heymaster.ui.user.home.UserHomeRepository
import com.heymaster.heymaster.ui.user.home.UserHomeViewModel
import com.heymaster.heymaster.ui.user.home.UserHomeViewModelFactory
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect

class MasterChildProfileFragment : Fragment() {


}