package net.dev.weatherapp.viewmodels

import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.repo.WeatherRepository
import net.dev.weatherapp.repo.remote.NetworkService
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
@RunWith(MockitoJUnitRunner::class)
class HomeFragmentViewModelTest {


    @Mock
    private lateinit var newsRepository : NewsRepository

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var networkService : NetworkService

    private lateinit var viewModel : HomeFragmentViewModel

    @Before
    fun setup(){
//        viewModel = HomeFragmentViewModel(weatherRepository,newsRepository)
    }

    //    doNothing().when(mock).someVoidMethod();
    @Test
    fun givenServerResponse200ForCovid_whenRequestingData_shouldUpdateUI(){

    }

    @Test
    fun givenServerResponse200ForWeather_whenRequestingData_shouldUpdateUI(){

    }

    @Test
    fun givenServerResponse200ForNews_whenRequestingData_shouldUpdateUI(){

    }

    @Test
    fun givenServerResponse200ForNews_whenRefresh_shouldUpdateNewsAdapter(){

    }

    @Test
    fun givenServerResponse200ForCOVID_whenRefresh_shouldUpdateCovidData(){

    }

    @Test
    fun givenServerResponse200ForWeather_whenRefresh_shouldUpdateWeatherAdapter(){

    }

    @Test
    fun givenServerResponse404ForNews_whenRefresh_shouldShowSnackBar(){

    }

    @Test
    fun givenServerResponse404ForCovid_whenRefresh_shouldShowSnackBar(){

    }

    @Test
    fun givenServerResponse404ForWeather_whenRefresh_shouldShowSnackBar(){

    }

    @After
    fun cleanup(){

    }
}