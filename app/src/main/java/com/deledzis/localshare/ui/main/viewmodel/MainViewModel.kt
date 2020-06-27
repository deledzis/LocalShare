package com.deledzis.localshare.ui.main

import com.deledzis.localshare.App
import com.deledzis.localshare.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    override val repository: MainRepository = MainRepository(App.injector.api())
}
