package com.simonigh.cutekitten.presentation

import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.simonigh.cutekitten.databinding.ActivityCatBinding
import com.simonigh.cutekitten.presentation.CatListViewModel.Companion.PAGE_SIZE
import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.Direction.Right
import com.yuyakaido.android.cardstackview.StackFrom.TopAndLeft
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber




class CatActivity : AppCompatActivity() {
    
    private lateinit var viewBinding: ActivityCatBinding
    
    private val viewModel by viewModel<CatListViewModel>()
    
    private val adapter = CatListAdapter()
    
    private lateinit var layoutManager: CardStackLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        initUI()
        observeVM()
        
    }
    
    private fun observeVM() {
        viewModel.state.observe(this) { state ->
            state.error?.let {
                //TODO show error
                return@observe
            }
            adapter.addItems(state.catList)
        }
    }
    
    private fun initUI() {
        layoutManager = CardStackLayoutManager(this, CardListener()).apply {
            setDirections(Direction.HORIZONTAL)
            setCanScrollVertical(false)
            setCanScrollHorizontal(true)
            setMaxDegree(-20.0f)
            setVisibleCount(5)
            setTranslationInterval(4.0f)
            setOverlayInterpolator(LinearInterpolator())
            setStackFrom(TopAndLeft)
        }
        viewBinding.mainActivityCardStack.layoutManager = layoutManager
        viewBinding.mainActivityCardStack.adapter = adapter
        viewBinding.mainActivityCardStack.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition: Int = layoutManager.topPosition
    
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                            viewModel.loadMoreCats()
                        }
                    }
            })
    }
    
    inner class CardListener : CardStackListener {
        
        override fun onCardDragging(direction: Direction?, ratio: Float) {
        
        }
        
        override fun onCardSwiped(direction: Direction?) {
            direction?.let {
                if (direction == Right) {
                    Timber.d("position ${layoutManager.topPosition}")
                    adapter.getIdAtPosition(layoutManager.topPosition).let { id ->
                        Timber.d("item to saved $id")
                    }
                }
            }
        }
        
        override fun onCardRewound() {
        
        }
        
        override fun onCardCanceled() {
        
        }
        
        override fun onCardAppeared(view: View?, position: Int) {
        
        }
        
        override fun onCardDisappeared(view: View?, position: Int) {
        
        }
        
    }
}